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

package org.vast.ows.gml;

import java.text.ParseException;
import org.vast.io.xml.DOMReader;
import org.vast.ows.util.TimeInfo;
import org.vast.util.DateTimeFormat;
import org.w3c.dom.Element;


/**
 * <p><b>Title:</b><br/>
 * GML Time Reader
 * </p>
 *
 * <p><b>Description:</b><br/>
 * Reads GML Time Primitive structures.
 * </p>
 *
 * <p>Copyright (c) 2005</p>
 * @author Alexandre Robin
 * @date Sep 01, 2006
 * @version 1.0
 */
public class GMLTimeReader
{
    protected final static String invalidISO = "Invalid ISO time: ";
    public GMLTimeReader()
    {
    }
    
    
    /**
     * Reads any supported GML time primitive
     * @param dom
     * @param timeElt
     * @return
     * @throws GMLException
     */
    public TimeInfo readTimePrimitive(DOMReader dom, Element timeElt) throws GMLException
    {
        String eltName = timeElt.getLocalName();
        
        if (eltName.equals("TimeInstant"))
            return readTimeInstant(dom, timeElt);
        else if (eltName.equals("TimePeriod"))
            return readTimePeriod(dom, timeElt);
        else if (eltName.equals("TimeGrid"))
            return readTimeGrid(dom, timeElt);
        
        throw new GMLException("Unsupported Time Primitive: " + eltName);
    }
    
    
    /**
     * Reads a gml:TimeInstant
     * @param dom
     * @param timeElt
     * @return
     * @throws GMLException
     */
    public TimeInfo readTimeInstant(DOMReader dom, Element timeElt) throws GMLException
    {
        TimeInfo time = new TimeInfo();
        
        String att = dom.getAttributeValue(timeElt, "timePosition/indeterminatePosition");
        String isoTime = dom.getElementValue(timeElt, "timePosition");
        
        if (att.equals("now"))
        {
            time.setBaseAtNow(true);
        }
        else
        {
            try
            {
                time.setBaseTime(DateTimeFormat.parseIso(isoTime));
            }
            catch (ParseException e)
            {
                throw new GMLException(invalidISO + isoTime, e);
            }
        }
        
        return time;
    }
    
    
    /**
     * Reads a gml:TimePeriod
     * @param dom
     * @param timePeriodElt
     * @return
     * @throws GMLException
     */
    public TimeInfo readTimePeriod(DOMReader dom, Element timePeriodElt) throws GMLException
    {
        TimeInfo timeInfo = new TimeInfo();
        
        String startAtt = dom.getAttributeValue(timePeriodElt, "beginPosition/indeterminatePosition");
        String isoStartTime = dom.getElementValue(timePeriodElt, "beginPosition");
        String stopAtt = dom.getAttributeValue(timePeriodElt, "endPosition/indeterminatePosition");
        String isoStopTime = dom.getElementValue(timePeriodElt, "endPosition");
        String duration = dom.getElementValue(timePeriodElt, "timeInterval");
        
        double startTime = 0.0;
        double stopTime = 0.0;
        boolean startUnknown = false;
        boolean stopUnknown = false;
        
        try
        {
            // read beginPosition intederminatePosition attribute
            if (startAtt != null)
            {
                if (startAtt.equals("now"))
                {
                    timeInfo.setBeginNow(true);
                }
                else if (startAtt.equals("unknown"))
                    startUnknown = true;
            }
            else
                timeInfo.setStartTime(DateTimeFormat.parseIso(isoStartTime));
            
            // read endPosition intederminatePosition attribute
            if (stopAtt != null)
            {
                if (stopAtt.equals("now"))
                {
                    timeInfo.setEndNow(true);
                }
                else if (startAtt.equals("unknown"))
                    stopUnknown = true;
            }
            else
                timeInfo.setStopTime(DateTimeFormat.parseIso(isoStopTime));
            
            // handle case of period specified with unknown start or stop time
            if (startUnknown || stopUnknown)
            {
                double dT = DateTimeFormat.parseIsoPeriod(duration);
                
                if (startUnknown)
                {
                    startTime = stopTime - dT;
                    isoStartTime = DateTimeFormat.formatIso(startTime, 0);
                }
                else
                {
                    stopTime = startTime + dT;
                    isoStopTime = DateTimeFormat.formatIso(stopTime, 0);
                }
            }
        }
        catch (ParseException e)
        {
            String start = (startAtt != null) ? startAtt : isoStartTime;
            String stop = (stopAtt != null) ? stopAtt : isoStopTime;
            String message = "Invalid Time Period: " + start + "/" + stop;
            if (duration != null)
                message = message + "/" + duration;
            throw new GMLException(message);
        }
        
        return timeInfo;
    }
    
    
    /**
     * Reads a gml:TimeGrid with step size
     * @param dom
     * @param timePeriodElt
     * @return
     * @throws GMLException
     */
    public TimeInfo readTimeGrid(DOMReader dom, Element timePeriodElt) throws GMLException
    {
        return null;
    }
}
