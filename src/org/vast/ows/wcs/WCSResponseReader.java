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

package org.vast.ows.wcs;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.vast.cdm.common.CDMException;
import org.vast.cdm.common.DataHandler;
import org.vast.data.DataArray;
import org.vast.ogc.OGCException;
import org.vast.ogc.OGCExceptionReader;
import org.vast.sweCommon.SWECommonUtils;
import org.vast.sweCommon.SWEFilter;
import org.vast.sweCommon.SWEReader;
import org.vast.sweCommon.URIStreamHandler;
import org.vast.xml.DOMHelper;
import org.vast.xml.DOMHelperException;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


/**
 *  * <p><b>Title:</b>
 * WCSResponseReader
 * </p>
 *
 * <p><b>Description:</b><br/>
 * Read WCS response in SWE format. The actual format is plain O&M
 * with a fixed size N-dimensional DataArray containing coverage data as the result.
 * </p>
 *
 * <p>Copyright (c) 2008</p>
 * @author Alexandre Robin
 * @date Jan 16, 2010
 * @version 1.0
 */
public class WCSResponseReader extends SWEReader
{
    protected SWEFilter streamFilter;
    protected DataArray coverageArray;
    
    
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
            OGCExceptionReader.checkException(dom);
            
            // find first Observation element
            Element rootElement = dom.getRootElement();
            NodeList elts = rootElement.getOwnerDocument().getElementsByTagNameNS("http://www.opengis.net/om/1.0", "Observation");
            Element obsElt = (Element)elts.item(0); 
            if (obsElt == null)
                throw new CDMException("XML Response doesn't contain any Observation");
            
            // read result
            SWECommonUtils utils = new SWECommonUtils();
            Element resultArrayElt = dom.getElement(obsElt, "result/*");
            
            // read array
            Element countElt = dom.getElement(resultArrayElt, "elementCount/Count");
            String countValue = dom.getElementValue(countElt, "value");
            int arraySize = Integer.parseInt(countValue);
            coverageArray = new DataArray(arraySize);
                        
            // read array component structure and encoding
            Element eltTypeElt = dom.getElement(resultArrayElt, "elementType");
            Element encElt = dom.getElement(resultArrayElt, "encoding");
            this.dataComponents = utils.readComponentProperty(dom, eltTypeElt);
            this.dataEncoding = utils.readEncodingProperty(dom, encElt);
            this.dataParser = createDataParser();
            coverageArray.addComponent(this.dataComponents);
            this.dataParser.setParentArray(coverageArray);
            
            // read external link if present
            Element valElt = dom.getElement(resultArrayElt, "values");
            valuesUri = valElt.getAttribute("xlink:href");
            dataParser.parse(new BufferedInputStream(getDataStream()));
        }
        catch (IllegalStateException e)
        {
            throw new CDMException("No reader found for SWECommon", e);
        }
        catch (DOMHelperException e)
        {
            throw new CDMException("Error while parsing Observation XML", e);
        }       
        catch (OGCException e)
        {
            throw new CDMException(e.getMessage());
        }
    }
    
    
    public DataArray getCoverageArray()
    {
        return coverageArray;
    }
    
    
    public InputStream getDataStream() throws CDMException
    {
        if (valuesUri != null && valuesUri.length()>0)
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
}
