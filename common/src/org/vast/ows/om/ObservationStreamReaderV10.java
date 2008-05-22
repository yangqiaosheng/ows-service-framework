/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are subject to the Mozilla Public License Version
 1.1 (the "License"); you may not use this file except in compliance with
 the License. You may obtain a copy of the License at
 http://www.mozilla.org/MPL/MPL-1.1.html
 
 Software distributed under the License is distributed on an "AS IS" basis,
 WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 for the specific language governing rights and limitations under the License.
 
 The Original Code is the "OGC Service Framework".
 
 The Initial Developer of the Original Code is the VAST team at the University of Alabama in Huntsville (UAH). <http://vast.uah.edu> Portions created by the Initial Developer are Copyright (C) 2007 the Initial Developer. All Rights Reserved. Please Contact Mike Botts <mike.botts@uah.edu> for more information.
 
 Contributor(s): 
    Alexandre Robin <robin@nsstc.uah.edu>
 
******************************* END LICENSE BLOCK ***************************/

package org.vast.ows.om;

import java.io.*;
import org.vast.cdm.common.CDMException;
import org.vast.cdm.common.DataHandler;
import org.vast.math.Vector3d;
import org.vast.ows.OWSException;
import org.vast.ows.OWSExceptionReader;
import org.vast.ows.gml.GMLGeometryReader;
import org.vast.sweCommon.SWEFilter;
import org.vast.sweCommon.SWECommonUtils;
import org.vast.sweCommon.URIStreamHandler;
import org.vast.xml.*;
import org.w3c.dom.*;


/**
 * <p><b>Title:</b>
 * SOSResponseReaderV10
 * </p>
 *
 * <p><b>Description:</b><br/>
 * Streaming reader for OM V1.0 responses with a result expressed in SWE Common.
 * This won't deal with Observation Collections properly.
 * </p>
 *
 * <p>Copyright (c) 2008</p>
 * @author Alexandre Robin
 * @date May 22, 2008
 * @version 1.0
 */
public class ObservationStreamReaderV10 extends ObservationStreamReader
{
	protected SWEFilter streamFilter;
	protected Vector3d foiLocation;
    protected String procedure;
    protected String observationName;
       
    
    public ObservationStreamReaderV10()
    {
        foiLocation = new Vector3d();
    }
    
    
    @Override
    public void parse(InputStream inputStream, DataHandler handler) throws CDMException
    {
		try
		{
		    dataHandler = handler;
		    streamFilter = new SWEFilter(inputStream);
			streamFilter.setDataElementName("values");
			
			// parse xml header using DOMReader
			DOMHelper dom = new DOMHelper(streamFilter, false);
			OWSExceptionReader.checkException(dom);
			
			// find first Observation element
			Element rootElement = dom.getRootElement();
			NodeList elts = rootElement.getOwnerDocument().getElementsByTagNameNS("http://www.opengis.net/om/1.0", "Observation");
			Element obsElt = (Element)elts.item(0);	
			if (obsElt == null)
				throw new CDMException("XML Response doesn't contain any Observation");
			
            // read FOI location
            GMLGeometryReader geometryReader = new GMLGeometryReader();
            Element pointElt = dom.getElement(obsElt, "featureOfInterest/*/location/Point");
            if (pointElt != null)
                foiLocation = geometryReader.readPoint(dom, pointElt);
            
            // read procedure ID and observation name
            procedure = dom.getAttributeValue(obsElt, "procedure/@href");
            observationName = dom.getElementValue(obsElt, "featureOfInterest/*/name");
            
            // read result
            Element resultObjElt = dom.getElement(obsElt, "result/*");
            SWECommonUtils utils = new SWECommonUtils();
            
            // case of DataArray (DataStream)
            if (resultObjElt.getLocalName().equals("DataArray"))
            {
                Element defElt = dom.getElement(resultObjElt, "elementType");
    			Element encElt = dom.getElement(resultObjElt, "encoding");
    			
    			// read structure and encoding                
                this.dataComponents = utils.readComponentProperty(dom, defElt);
                this.dataEncoding = utils.readEncodingProperty(dom, encElt);
    			this.dataParser = createDataParser();
    			
    			// read external link if present
    			valuesUri = dom.getAttributeValue(obsElt, "values/externalLink");
    			
    			// launch data stream parser if handler was provided
    			if (dataHandler != null)
    			{
    			    dataParser.setDataHandler(dataHandler);
    		        dataParser.parse(getDataStream());
    			}
            }
            
            // case of data embedded in the DataComponent
            else
            {
                this.dataComponents = utils.readComponentProperty(dom, resultObjElt);
                this.dataEncoding = null;
            }
		}
        catch (IllegalStateException e)
        {
            throw new CDMException("No reader found for SWECommon", e);
        }
		catch (DOMHelperException e)
		{
			throw new CDMException("Error while parsing Observation XML", e);
		}		
		catch (OWSException e)
		{
			throw new CDMException(e.getMessage());
		}
	}
	
	
	public InputStream getDataStream() throws CDMException
	{
		if (valuesUri != null)
		{
			try
			{
				streamFilter.startReadingData();
				streamFilter.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			
			return URIStreamHandler.openStream(valuesUri);
		}
		else
		{
			streamFilter.startReadingData();
			return streamFilter;
		}
	}


    public Vector3d getFoiLocation()
    {
        return foiLocation;
    }


    public String getObservationName()
    {
        return observationName;
    }


    public String getProcedure()
    {
        return procedure;
    }
}
