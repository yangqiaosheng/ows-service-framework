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
    Alexandre Robin <alexandre.robin@spotimage.fr>
 
******************************* END LICENSE BLOCK ***************************/

package org.vast.ows.sps;

import org.vast.cdm.common.CDMException;
import org.vast.ogc.OGCRegistry;
import org.vast.ows.AbstractResponseWriter;
import org.vast.ows.OWSException;
import org.w3c.dom.*;
import org.vast.util.DateTime;
import org.vast.util.DateTimeFormat;
import org.vast.xml.DOMHelper;


/**
 * <p><b>Title:</b><br/>
 * GetFeasibility Response Writer v1.1
 * </p>
 *
 * <p><b>Description:</b><br/>
 * Writer to generate an XML GetFeasibility response based
 * on values contained in a GetFeasibilityResponse object for SPS v1.1
 * </p>
 *
 * <p>Copyright (c) 2008</p>
 * @author Alexandre Robin
 * @date Mar 04, 2008
 * @version 1.0
 */
public class GetFeasibilityResponseWriterV11 extends AbstractResponseWriter<GetFeasibilityResponse>
{
	protected SPSCommonWriterV11 commonWriter = new SPSCommonWriterV11();
	
	
	public Element buildXMLResponse(DOMHelper dom, GetFeasibilityResponse response, String version) throws OWSException
	{
		try
		{
			dom.addUserPrefix("sps", OGCRegistry.getNamespaceURI("SPS", response.getVersion()));
			
			// root element
			Element rootElt = dom.createElement("sps:" + response.getMessageType());
			
			// feasibility study
			Element studyElt = commonWriter.writeFeasibilityStudy(dom, response.getFeasibilityStudy());
			rootElt.appendChild(studyElt);
			
			// alternatives
			// TODO write alternatives
			
			// latest response time
			DateTime latestResp = response.getLatestResponseTime();
			if (latestResp != null)
				dom.setElementValue(rootElt, "sps:LatestResponseTime",
						DateTimeFormat.formatIso(latestResp.getJulianTime(), 0));
			
			return rootElt;
		}
		catch (CDMException e)
		{
			throw new SPSException(e);
		}
	}
	
}