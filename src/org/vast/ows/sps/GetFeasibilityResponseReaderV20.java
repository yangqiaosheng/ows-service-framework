/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are subject to the Mozilla Public License Version
 1.1 (the "License"); you may not use this file except in compliance with
 the License. You may obtain a copy of the License at
 http://www.mozilla.org/MPL/MPL-1.1.html
 
 Software distributed under the License is distributed on an "AS IS" basis,
 WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 for the specific language governing rights and limitations under the License.
 
 The Original Code is the "OGC Service Framework".
 
 The Initial Developer of the Original Code is Spotimage S.A.
 Portions created by the Initial Developer are Copyright (C) 2007
 the Initial Developer. All Rights Reserved.
 
 Contributor(s): 
    Alexandre Robin <alexandre.robin@spotimage.fr>
 
******************************* END LICENSE BLOCK ***************************/

package org.vast.ows.sps;

import org.vast.cdm.common.DataComponent;
import org.vast.ows.OWSException;
import org.vast.util.DateTime;
import org.vast.util.DateTimeFormat;
import org.vast.xml.DOMHelper;
import org.w3c.dom.Element;


/**
 * <p><b>Title:</b>
 * GetFeasibility Response Reader v2.0
 * </p>
 *
 * <p><b>Description:</b><br/>
 * Used to read a version 2.0 SPS GetFeasibility Response into
 * a GetFeasibilityResponse object
 * </p>
 *
 * <p>Copyright (c) 2008</p>
 * @author Alexandre Robin <alexandre.robin@spotimage.fr>
 * @date Feb, 29 2008
 * @version 1.0
 */
public class GetFeasibilityResponseReaderV20 extends ParameterizedResponseReader<GetFeasibilityResponse>
{
	protected SPSCommonReaderV20 commonReader = new SPSCommonReaderV20();
	protected DataComponent paramStructure;
	
	
	public void setParamStructure(DataComponent paramStructure)
	{
		this.paramStructure = paramStructure;
	}
	
	
	public GetFeasibilityResponse readXMLResponse(DOMHelper dom, Element responseElt) throws OWSException
	{
		try
		{
			assert(paramStructure != null);
			
			GetFeasibilityResponse response = new GetFeasibilityResponse();
			response.setVersion("2.0");
			
			// feasibility study
			Element studyElt = dom.getElement(responseElt, "FeasibilityStudy");
			if (studyElt != null)
			{
				FeasibilityStudy study = commonReader.readFeasibilityStudy(dom, studyElt, paramStructure);
				response.setFeasibilityStudy(study);
			}
			
			// alternatives
			// TODO read alternatives
			
			// latest response time
			String isoDate = dom.getElementValue(responseElt, "LatestResponseTime");
			if (isoDate != null)
			{
				DateTime latestResponseTime = new DateTime(DateTimeFormat.parseIso(isoDate));
				response.setLatestResponseTime(latestResponseTime);
			}
			
			return response;
		}
		catch (Exception e)
		{
			throw new SPSException(e);
		}
	}
	
}
