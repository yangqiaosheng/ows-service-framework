/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are subject to the Mozilla Public License Version
 1.1 (the "License"); you may not use this file except in compliance with
 the License. You may obtain a copy of the License at
 http://www.mozilla.org/MPL/MPL-1.1.html
 
 Software distributed under the License is distributed on an "AS IS" basis,
 WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 for the specific language governing rights and limitations under the License.
 
 The Original Code is the "OGC Service Framework".
 
 The Initial Developer of the Original Code is Sensia Software LLC.
 Portions created by the Initial Developer are Copyright (C) 2014
 the Initial Developer. All Rights Reserved.
 
 Please Contact Alexandre Robin <alex.robin@sensiasoftware.com> or
 Mike Botts <mike.botts@botts-inc.net> for more information.
 
 Contributor(s): 
    Alexandre Robin <alex.robin@sensiasoftware.com>
 
******************************* END LICENSE BLOCK ***************************/

package org.vast.ows.swe;

import org.vast.ows.OWSException;
import org.vast.ows.OWSUtils;
import org.vast.xml.DOMHelper;
import org.w3c.dom.Element;


/**
 * <p><b>Title:</b>
 * InsertSensor Response Reader v2.0
 * </p>
 *
 * <p><b>Description:</b><br/>
 * Reader for XML InsertSensor response for SOS v2.0 
 * </p>
 *
 * <p>Copyright (c) 2014</p>
 * @author Alexandre Robin
 * @date Feb, 19 2014
 * @version 1.0
 */
public class InsertSensorResponseReaderV20 extends SWEResponseReader<InsertSensorResponse>
{
		
	public InsertSensorResponse readXMLResponse(DOMHelper dom, Element responseElt) throws OWSException
	{
	    InsertSensorResponse response = new InsertSensorResponse(OWSUtils.SOS);
		response.setVersion("2.0");		
		
		// read extensions
		readXMLExtensions(dom, responseElt, response);
		
		// assigned procedure
		String val = dom.getElementValue(responseElt, "assignedProcedure");
		response.setAssignedProcedureId(val);
		
		// assigned offering
		val = dom.getElementValue(responseElt, "assignedOffering");
        response.setAssignedOffering(val);
        
		return response;
	}	
}