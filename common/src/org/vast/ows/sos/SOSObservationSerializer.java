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

package org.vast.ows.sos;

import java.io.*;
import org.w3c.dom.*;
import org.vast.io.xml.*;
import org.vast.math.Vector3d;
import org.vast.ows.SweResponseSerializer;
import org.vast.ows.gml.GMLTimeWriter;
import org.vast.ows.util.TimeInfo;


/**
 * <p><b>Title:</b><br/>
 * SOS Observation Serializer
 * </p>
 *
 * <p><b>Description:</b><br/>
 * This class is a specific serializer used to output
 * Common Observations. It provides an additional setTime
 * method to easily change the time in the XML response and
 * overrides the <swe:result> element serialization by calling
 * the attached DataWriter to handle the job. 
 * </p>
 *
 * <p>Copyright (c) 2005</p>
 * @author Alexandre Robin
 * @date Feb 10, 2006
 * @version 1.0
 */
public class SOSObservationSerializer extends SweResponseSerializer
{
		
	public SOSObservationSerializer()
	{		
	}
    
    
    /**
     * Changes Id attribute in the Observation XML
     * @param id
     */
    public void setID(String id)
    {
        DOMWriter domWriter = new DOMWriter(xmlDocument);
        Element obsElt = xmlDocument.getDocumentElement();
        domWriter.setAttributeValue(obsElt, "gml:id", id);
    }
	
	
	/**
	 * Changes eventTime element in the DOM to contain the request times
	 * @param time
	 */
	public void setTime(TimeInfo time, int zone)
	{
        try
        {
            time.setTimeZone(zone);
            time.setBeginNow(false);
            time.setEndNow(false);
            time.setBaseAtNow(false);
            DOMWriter domWriter = new DOMWriter(xmlDocument);
            Element obsTimeElt = domWriter.addElement("om:eventTime");
            GMLTimeWriter timeWriter = new GMLTimeWriter();
            Element timeElt = timeWriter.writeTime(domWriter, time);
            obsTimeElt.appendChild(timeElt);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
	}
    
    
    public void setFoiLocation(Vector3d location)
    {
        try
        {
            DOMWriter domWriter = new DOMWriter(xmlDocument);
            //domWriter.addNS("http://www.opengeospatial.net/sos", "sos");
            Element pointElt = domWriter.addElement("om:featureOfInterest/swe:GeoReferenceableFeature/gml:location/gml:Point");
            domWriter.setAttributeValue(pointElt, "srs", "urn:ogc:def:crs:EPSG:6.1:4329");
            domWriter.setElementValue(pointElt, "", location.x + " " + location.y + " " + location.z);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
	
	
	/**
	 * Adds a hook to override serialization of the result element
     * Uses the DataWriter to write the content of the element.
	 */
	protected void serializeElement(Element elt) throws IOException
	{
		if (elt.getLocalName().equals("result"))
		{
			this._format.setIndenting(false);
			this._printer.printText("\n<om:result>");
			this._printer.flush();
			
            dataWriter.write();
			
			this._printer.printText("\n</om:result>");
			this._printer.flush();
			this._format.setIndenting(true);
		}
		else
		{
			super.serializeElement(elt);
		}
	}	
}
