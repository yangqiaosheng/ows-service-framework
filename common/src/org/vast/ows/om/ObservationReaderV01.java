/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are subject to the Mozilla Public License Version
 1.1 (the "License"); you may not use this file except in compliance with
 the License. You may obtain a copy of the License at
 http://www.mozilla.org/MPL/MPL-1.1.html
 
 Software distributed under the License is distributed on an "AS IS" basis,
 WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 for the specific language governing rights and limitations under the License.
 
 The Original Code is the "SensorML DataProcessing Engine".
 
 The Initial Developer of the Original Code is the
 University of Alabama in Huntsville (UAH).
 Portions created by the Initial Developer are Copyright (C) 2006
 the Initial Developer. All Rights Reserved.
 
 Contributor(s): 
    Alexandre Robin <robin@nsstc.uah.edu>
 
******************************* END LICENSE BLOCK ***************************/

package org.vast.ows.om;

import java.io.IOException;
import java.io.InputStream;
import org.vast.cdm.common.CDMException;
import org.vast.cdm.common.DataComponent;
import org.vast.cdm.common.DataEncoding;
import org.vast.cdm.common.DataSource;
import org.vast.math.Vector3d;
import org.vast.ows.OWSException;
import org.vast.ows.OWSExceptionReader;
import org.vast.ows.gml.Feature;
import org.vast.ows.gml.GMLException;
import org.vast.ows.gml.GMLGeometryReader;
import org.vast.ows.gml.GMLTimeReader;
import org.vast.ows.util.TimeInfo;
import org.vast.sweCommon.DataSourceInline;
import org.vast.sweCommon.DataSourceURI;
import org.vast.sweCommon.SWECommonUtils;
import org.vast.sweCommon.SWEFilter;
import org.vast.xml.*;
import org.w3c.dom.*;


/**
 * <p><b>Title:</b>
 * Observation Reader v0.0
 * </p>
 *
 * <p><b>Description:</b><br/>
 * Reader for O&M observations v0.0
 * </p>
 *
 * <p>Copyright (c) 2007</p>
 * @author Alexandre Robin
 * @date Feb 23, 2007
 * @version 1.0
 */
public class ObservationReaderV01 implements ObservationReader
{
	protected SWEFilter streamFilter;
    protected GMLTimeReader timeReader;
    
    
    public ObservationReaderV01()
    {
        timeReader = new GMLTimeReader();
    }
    
	
    public AbstractObservation readObservation(InputStream inputStream) throws OMException
    {
        try
        {
            // install SWE Filter to skip inline data
            streamFilter = new SWEFilter(inputStream);
            streamFilter.setDataElementName("result");
            
            // parse xml header using DOMReader
            DOMHelper dom = new DOMHelper(streamFilter, false);
            OWSExceptionReader.checkException(dom);
            
            return this.readObservation(dom, dom.getRootElement());
        }
        catch (DOMHelperException e)
        {
            throw new OMException("Error while parsing XML document", e);
        }
        catch (OWSException e)
        {
            throw new OMException(e.getMessage());
        }
    }
    
    
    public AbstractObservation readObservation(DOMHelper dom, Element obsElt) throws OMException
    {
        AbstractObservation observation = null;
        
        // case of ObservationCollection
        if (obsElt.getLocalName().equals("ObservationCollection"))
            observation = readObsCollection(dom, obsElt);
        
        // case of Observation
        else if (obsElt.getLocalName().endsWith("Observation"))
            observation = readObsStream(dom, obsElt);
        
        else
            throw new OMException("Unrecognized Observation Type");
        
        // read observation name
        String name = dom.getElementValue(obsElt, "name");
        observation.setName(name);
        
        // read time
        try
        {
            Element timeElt = dom.getElement(obsElt, "samplingTime/*");
            if (timeElt != null)
            {
                TimeInfo time = timeReader.readTimePrimitive(dom, timeElt);
                observation.setTime(time);
            }
        }
        catch (GMLException e)
        {
            throw new OMException(e.getMessage());
        }
        
        // read procedure ID
        String procedure = dom.getAttributeValue(obsElt, "procedure/@href");
        observation.setProcedure(procedure);
        
        // read foi
        Element foiElt = dom.getElement(obsElt, "featureOfInterest/*");
        if (foiElt != null)
        {
            Feature foi = readFOI(dom, foiElt);
            observation.setFeatureOfInterest(foi);
        }
        
        // if collection has only one member return the member
        if (observation instanceof ObservationCollection)
        {
            ObservationCollection collection = (ObservationCollection)observation;
            if (collection.getMembers().size() == 1)
                observation = collection.getMembers().get(0);            
            if (observation.getName() == null)
                observation.setName(collection.getName());
        }
        
        return observation;
    }
    
    
    public void readResult()
    {
        
    }
    
    
    /**
     * Reads a collection and all its member recursively
     * @param dom
     * @param obsElt
     * @return
     * @throws OMException
     */
    protected ObservationCollection readObsCollection(DOMHelper dom, Element obsElt) throws OMException
    {
        ObservationCollection collection = new ObservationCollection();
        
        // read members
        NodeList memberList = dom.getElements(obsElt, "member");
        for (int i=0; i<memberList.getLength(); i++)
        {
            Element memberElt = (Element)memberList.item(i);
            Element memberObsElt = dom.getFirstChildElement(memberElt);
            AbstractObservation obs = this.readObservation(dom, memberObsElt);
            collection.getMembers().add(obs);
        }
        
        return collection;
    }
    
    
    /**
     * Reads an Observation with a stream of values as a result
     * @param dom
     * @param obsElt
     * @return
     * @throws OMException
     */
    protected ObservationStream readObsStream(DOMHelper dom, Element obsElt) throws OMException
    {
        ObservationStream observation = new ObservationStream();
        
        // read resultDefinition
        try
        {
            Element defElt = dom.getElement(obsElt, "resultDefinition/DataBlockDefinition");
            Element dataElt = dom.getElement(defElt, "components");
            Element encElt = dom.getElement(defElt, "encoding");        
            SWECommonUtils sweUtils = new SWECommonUtils();
            DataComponent dataComponents = sweUtils.readComponentProperty(dom, dataElt);
            DataEncoding dataEncoding = sweUtils.readEncodingProperty(dom, encElt);
            observation.getResult().setDataComponents(dataComponents);
            observation.getResult().setDataEncoding(dataEncoding);
        }
        catch (CDMException e)
        {
            throw new OMException("Error while parsing data definition", e);
        }
        
        // read result
        Element resultElt = dom.getElement(obsElt, "result");
        DataSource dataSrc = readResultSource(dom, resultElt);
        observation.getResult().setDataSource(dataSrc);        
        
        return observation;
    }
    
    
    /**
     * Reads the feature of interest XML
     * @param dom
     * @param foiElt
     * @return
     * @throws OMException
     */
    protected Feature readFOI(DOMHelper dom, Element foiElt) throws OMException
    {
        Feature feature = new Feature();
        
        // read name
        String name = dom.getElementValue(foiElt, "name");
        feature.setName(name);
        
        // read location
        Element pointElt = dom.getElement(foiElt, "location/Point");
        
        if (pointElt != null)
        {
            try
            {
                GMLGeometryReader geometryReader = new GMLGeometryReader();
                Vector3d location = geometryReader.readPoint(dom, pointElt);
                feature.setLocation(location);
            }
            catch (GMLException e)
            {
                throw new OMException("Error while parsing FOI location", e); 
            }            
        }
        
        return feature;
    }
    
    
    /**
     * Creates a DataSource depending on the type of result obtained
     * Allows support of inline, out-of-band and mime packaged results
     * @param dom
     * @param resultElt
     * @return
     */
    protected DataSource readResultSource(DOMHelper dom, Element resultElt)
    {
        String resultUri = dom.getAttributeValue(resultElt, "@externalLink");
        
        // case of inline data
        if (resultUri == null)
        {
            streamFilter.startReadingData();
            return new DataSourceInline(streamFilter);
        }
        
        // case of URI
        else
        {
            streamFilter.startReadingData();
            try { streamFilter.close(); }
            catch(IOException e) { e.printStackTrace(); };
            return new DataSourceURI(resultUri);
        }
    }
    
}
