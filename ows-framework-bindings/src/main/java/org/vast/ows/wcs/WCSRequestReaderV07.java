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
    Tony Cook <tcook@nsstc.uah.edu>
 
******************************* END LICENSE BLOCK ***************************/

package org.vast.ows.wcs;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.vast.util.Bbox;
import org.vast.util.TimeExtent;
import org.vast.xml.DOMHelper;
import org.w3c.dom.*;
import org.vast.ows.*;


/**
 * <p><b>Title:</b><br/>
 * WCS Request Reader v0.7
 * </p>
 *
 * <p><b>Description:</b><br/>
 * Provides methods to parse a KVP or XML GetCoverage request and
 * create a GetCoverage object for version 0.7
 * </p>
 *
 * <p>Copyright (c) 2005</p>
 * @author Alexandre Robin, Tony Cook
 * @date Nov 17, 2005
 * @version 1.0
 */
public class WCSRequestReaderV07 extends AbstractRequestReader<GetCoverageRequest>
{
	
    public WCSRequestReaderV07()
	{	
	}

	
	@Override
	public GetCoverageRequest readURLParameters(Map<String, String> queryParameters) throws OWSException
	{
		GetCoverageRequest request = new GetCoverageRequest();
		Iterator<Entry<String, String>> it = queryParameters.entrySet().iterator();
		String crs = null;  
		
        while (it.hasNext())
        {
            Entry<String, String> item = it.next();
            String argName = item.getKey();
            String argValue = item.getValue();
            
            // service ID
            if (argName.equalsIgnoreCase("service"))
            {
                request.setService(argValue);
            }
            
            // service version
            else if (argName.equalsIgnoreCase("version"))
            {
                request.setVersion(argValue);
            }

            // request argument
            else if (argName.equalsIgnoreCase("request"))
            {
                request.setOperation(argValue);
            }

            // format argument
            else if (argName.equalsIgnoreCase("format"))
            {
                request.setFormat(argValue);
            }
            
            // time
            else if (argName.equalsIgnoreCase("time"))
            {
            	TimeExtent time = parseTimeArg(argValue);
            	request.setTime(time);
            }
            
            // bbox
            else if (argName.equalsIgnoreCase("bbox"))
            {
            	Bbox bbox = parseBboxArg(argValue);
                request.setBbox(bbox);
                if (crs != null)
                	bbox.setCrs(crs);
            }

            else if (argName.equalsIgnoreCase("layers")) {
            	request.setCoverage(argValue);
            }
            
         // CRS
            else if (argName.equalsIgnoreCase("CRS"))
            {
            	Bbox bbox = request.getBbox();
            	if (bbox != null)
            		bbox.setCrs(argValue);
            	else
            		crs = argValue;
            }
            
         // RESY
            else if (argName.equalsIgnoreCase("skipx"))
            {
                try
				{
					int skipX = Integer.parseInt(argValue);
					request.setSkipX(skipX);
				}
				catch (NumberFormatException e)
				{
					throw new WCSException(invalidKVP + ": Invalid value for SkipX: " + argValue);
				}
            }
            
            // RESZ
            else if (argName.equalsIgnoreCase("skipy"))
            {
                try
				{
					int skipY = Integer.parseInt(argValue);
					request.setSkipX(skipY);
				}
				catch (NumberFormatException e)
				{
					throw new WCSException(invalidKVP + ": Invalid value for SkipY: " + argValue);
				}
            }
            
            
            else
                throw new WCSException(invalidKVP + ": Unknown Argument " + argName);
        }

        return request;
	}
	
	
	@Override
	public GetCoverageRequest readXMLQuery(DOMHelper dom, Element requestElt) throws OWSException
	{
		GetCoverageRequest request = new GetCoverageRequest();
		
		// TODO readXMLQuery
		
		// do common stuffs like version, request name and service type
		readCommonXML(dom, requestElt, request);		
		
		return request;
	}
}