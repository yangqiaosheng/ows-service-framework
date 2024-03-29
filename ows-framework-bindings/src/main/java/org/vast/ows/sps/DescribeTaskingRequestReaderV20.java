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
   Philippe Merigot <philippe.merigot@spotimage.fr>   

******************************* END LICENSE BLOCK ***************************/

package org.vast.ows.sps;

import org.w3c.dom.Element;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.vast.ows.OWSException;
import org.vast.ows.OWSExceptionReport;
import org.vast.ows.swe.SWERequestReader;
import org.vast.xml.DOMHelper;


/**
* <p><b>Title:</b><br/>
* SPS DescribeTasking Request Reader v2.0
* </p>
*
* <p><b>Description:</b><br/>
* Provides methods to parse a KVP or XML SPS DescribeTasking
* request and create a DescribeTasking object for version 2.0
* </p>
*
* <p>Copyright (c) 2008</p>
* @author Alexandre Robin <alexandre.robin@spotimage.fr>
* @date Feb, 25 2008
* @version 1.0
*/
public class DescribeTaskingRequestReaderV20 extends SWERequestReader<DescribeTaskingRequest>
{

	@Override
	public DescribeTaskingRequest readURLParameters(Map<String, String> queryParameters) throws OWSException
	{
		DescribeTaskingRequest request = new DescribeTaskingRequest();
		Iterator<Entry<String, String>> it = queryParameters.entrySet().iterator();
        
        while (it.hasNext())
        {
            Entry<String, String> item = it.next();
            String argName = item.getKey();
            String argValue = item.getValue();

			// service type
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

			// sensor ID
			else if (argName.equalsIgnoreCase("procedure"))
			{
				request.setProcedureID(argValue);
			}

			else
				throw new OWSException(invalidKVP + ": Unknown Argument " + argName);
		}

		checkParameters(request, new OWSExceptionReport());
		return request;
	}


	@Override
	public DescribeTaskingRequest readXMLQuery(DOMHelper dom, Element requestElt) throws OWSException
	{
		DescribeTaskingRequest request = new DescribeTaskingRequest();

		// do common stuffs like version, request name and service type
		readCommonXML(dom, requestElt, request);

		// Procedure ID
		String procedureID = dom.getElementValue(requestElt, "procedure");
		request.setProcedureID(procedureID);

		checkParameters(request, new OWSExceptionReport());
		return request;
	}


	/**
	 * Checks that DescribeTasking mandatory parameters are present
	 * @param request
	 * @throws OWSException
	 */
	protected void checkParameters(DescribeTaskingRequest request, OWSExceptionReport report) throws OWSException
	{
		// check common params + generate exception
		checkParameters(request, report, SPSUtils.SPS);

		// Check Procedure ID
		if (request.getProcedureID() == null)
		{
			report.add(new OWSException(OWSException.missing_param_code, "procedure"));
		}

		report.process();
	}
}