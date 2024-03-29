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
 
******************************* END LICENSE BLOCK ***************************/

package org.vast.ows.swe;

import java.util.Map;
import org.vast.xml.DOMHelper;
import org.vast.xml.QName;
import org.vast.ows.AbstractRequestReader;
import org.vast.ows.OWSRequest;
import org.w3c.dom.Element;


/**
 * <p><b>Title:</b><br/>
 * SWES Request Reader
 * </p>
 *
 * <p><b>Description:</b><br/>
 * Provides methods to parse a GET or POST SWES request and
 * create an OWSRequest object
 * </p>
 *
 * <p>Copyright (c) 2007</p>
 * @author Alexandre Robin
 * @date Nov 4, 2005
 * @version 1.0
 */
public abstract class SWERequestReader<RequestType extends OWSRequest> extends AbstractRequestReader<RequestType>
{
		
	/**
	 * Reads common XML in all SWES requests and fill up the OWSRequest accordingly
	 * @param dom
	 * @param requestElt
	 * @param request
	 */
	public static void readCommonXML(DOMHelper dom, Element requestElt, OWSRequest request)
	{
		AbstractRequestReader.readCommonXML(dom, requestElt, request);
		
		// read extensions
    	Map<QName, Object> extObjs = SWESUtils.readXMLExtensions(dom, requestElt);
    	request.getExtensions().putAll(extObjs);
	}
}