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

package org.vast.ows.wcs;

import org.vast.ogc.OGCRegistry;
import org.vast.ows.*;
import org.w3c.dom.*;
import org.vast.xml.DOMHelper;


/**
 * <p><b>Title:</b><br/>
 * DescribeCoverage Request Builder v1.1
 * </p>
 *
 * <p><b>Description:</b><br/>
 * Provides methods to generate a KVP or XML DescribeCoverage request based
 * on values contained in a DescribeCoverage object for version 1.1.0/1.1.1
 * </p>
 *
 * <p>Copyright (c) 2007</p>
 * @author Alexandre Robin
 * @date Oct 08, 2007
 * @version 1.0
 */
public class DescribeCoverageWriterV11 extends AbstractRequestWriter<DescribeCoverageRequest>
{
	
	@Override
	public String buildURLQuery(DescribeCoverageRequest request) throws OWSException
	{
		StringBuffer urlBuff = new StringBuffer(request.getGetServer());
		
        urlBuff.append("SERVICE=WCS");
        urlBuff.append("&VERSION=" + request.getVersion());
        urlBuff.append("&REQUEST=" + request.getOperation());
        
        // add all requested coverage names
        if (!request.getCoverages().isEmpty())
        {
			urlBuff.append("&IDENTIFIERS=");
			int listSize = request.getCoverages().size();
        	for (int i=0; i<listSize; i++)
        	{
				urlBuff.append(request.getCoverages().get(i));
				if (i < listSize-1)
					urlBuff.append(",");
        	}
        }        
		
        // replace spaces
		String url = urlBuff.toString();
		url = url.replaceAll(" ","%20");		
		return url;
	}
	
	
	@Override
	public Element buildXMLQuery(DOMHelper dom, DescribeCoverageRequest request) throws OWSException
	{
		dom.addUserPrefix("wcs", OGCRegistry.getNamespaceURI("WCS", request.getVersion()));
		
		// root element
		Element rootElt = dom.createElement("wcs:DescribeCoverage");
		addCommonXML(dom, rootElt, request);
		
		// add all requested coverage names
		for (int i=0; i<request.getCoverages().size(); i++)
			dom.setElementValue(rootElt, "wcs:Identifier", request.getCoverages().get(i));
		
		return rootElt;
	}
}