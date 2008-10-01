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

import org.w3c.dom.Element;
import org.vast.ogc.OGCRegistry;
import org.vast.ows.AbstractRequestWriter;
import org.vast.ows.OWSException;
import org.vast.xml.DOMHelper;


/**
* <p><b>Title:</b><br/>
* SPS DescribeTasking Request Writer v1.1
* </p>
*
* <p><b>Description:</b><br/>
* Provides methods to generate a KVP or XML SPS DescribeTasking
* request based on values contained in a DescribeTaskingRequest
* object for version 1.1
* </p>
*
* <p>Copyright (c) 2008</p>
* @author Alexandre Robin <alexandre.robin@spotimage.fr>
* @date Feb, 28 2008
* @version 1.0
*/
public class DescribeTaskingRequestWriterV20 extends AbstractRequestWriter<DescribeTaskingRequest>
{

	/**
	 * KVP Request
	 */
	@Override
	public String buildURLQuery(DescribeTaskingRequest request) throws OWSException
	{
		StringBuffer urlBuff;

		urlBuff = new StringBuffer(request.getGetServer());
		urlBuff.append("service=SPS");
		urlBuff.append("&version=" + request.getVersion());
		urlBuff.append("&request=" + request.getOperation());

		// sensorID
		urlBuff.append("&sensorID=" + request.getSensorID());

		String url = urlBuff.toString();
		url = url.replaceAll(" ", "%20");
		return url;
	}


	/**
	 * XML Request
	 */
	@Override
	public Element buildXMLQuery(DOMHelper dom, DescribeTaskingRequest request) throws OWSException
	{
		dom.addUserPrefix("sps", OGCRegistry.getNamespaceURI("SPS", request.getVersion()));

		// root element
		Element rootElt = dom.createElement("sps:" + request.getOperation());
		addCommonXML(dom, rootElt, request);

		// sensorID
		if (request.getSensorID() != null)
			dom.setElementValue(rootElt, "sps:sensorID", request.getSensorID());

		return rootElt;
	}
}