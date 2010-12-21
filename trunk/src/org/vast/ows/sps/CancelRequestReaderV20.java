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
import org.vast.ows.OWSException;
import org.vast.ows.OWSExceptionReport;
import org.vast.ows.swe.SWERequestReader;
import org.vast.xml.DOMHelper;


/**
* <p><b>Title:</b><br/>
* SPS Cancel Request Reader v2.0
* </p>
*
* <p><b>Description:</b><br/>
* Provides methods to parse a KVP or XML SPS Cancel
* request and create a CancelRequest object for version 2.0
* </p>
*
* <p>Copyright (c) 2008</p>
* @author Alexandre Robin <alexandre.robin@spotimage.fr>
* @date Dec, 18 2008
* @version 1.0
*/
public class CancelRequestReaderV20 extends SWERequestReader<CancelRequest>
{

	@Override
	public CancelRequest readURLQuery(String queryString) throws OWSException
	{
		throw new SPSException(noKVP + "SPS 2.0 Cancel");
	}


	@Override
	public CancelRequest readXMLQuery(DOMHelper dom, Element requestElt) throws OWSException
	{
	    CancelRequest request = new CancelRequest();

		// do common stuffs like version, request name and service type
		readCommonXML(dom, requestElt, request);

		// taskID
		String taskID = dom.getElementValue(requestElt, "task");
		request.setTaskID(taskID);

		checkParameters(request, new OWSExceptionReport());
		return request;
	}


	/**
	 * Checks that GetStatus mandatory parameters are present
	 * @param request
	 * @throws OWSException
	 */
	protected void checkParameters(CancelRequest request, OWSExceptionReport report) throws OWSException
	{
		// check common params + generate exception
		checkParameters(request, report, SPSUtils.SPS);

		// Check that taskID is present
		if (request.getTaskID() == null)
			report.add(new OWSException(OWSException.missing_param_code, "task"));

		report.process();
	}
}