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

package org.vast.ows;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import org.vast.ogc.OGCRegistry;
import org.vast.xml.DOMHelper;
import org.w3c.dom.Element;


/**
 * <p>
 * Utility class to serialize an OWS exception to XML
 * </p>
 *
 * <p>Copyright (c) 2007</p>
 * @author Alexandre Robin <alexandre.robin@spotimage.fr>
 * @since Oct 23, 2007
 * @version 1.0
 */
public class OWSExceptionWriter
{
	protected DOMHelper dom = new DOMHelper();
	
	
	/**
	 * Creates an ExceptionReport XML element containing the list
	 * of exception as specified in OWS Common
	 * @param report
	 * @return
	 */
	public Element buildXML(OWSException e)
	{
		if (e instanceof OWSExceptionReport)
		{
			OWSExceptionReport report = (OWSExceptionReport)e;
						
			// use right namespace depending on version
			String version = report.getVersion();
			if (version == null || version.equals(OWSException.VERSION_10))
				dom.addUserPrefix("ows", OGCRegistry.getNamespaceURI(OWSUtils.OWS));
			else
				dom.addUserPrefix("ows", OGCRegistry.getNamespaceURI(OWSUtils.OWS, version));
			
			Element reportElt = dom.createElement("ows:ExceptionReport");
			dom.setAttributeValue(reportElt, "@version", version);
			
			List<OWSException> exceptionList = report.getExceptionList();
			for (int i=0; i<exceptionList.size(); i++)
			{
				OWSException exc = exceptionList.get(i);
				String code = exc.getCode();
				String locator = exc.getLocator();
				String text = exc.getMessage();
				
				Element excElt = dom.addElement(reportElt, "+ows:Exception");
				
				if (code != null)
					dom.setAttributeValue(excElt, "@exceptionCode", code);
				
				if (locator != null)
					dom.setAttributeValue(excElt, "@locator", locator);
				
				if (text != null)
					dom.setElementValue(excElt, "ows:ExceptionText", text);
			}
			
			return reportElt;
		}
		else
			return buildXML(new OWSExceptionReport(e));
	}
	
	
	/**
	 * Creates an OWS XML exception and serializes it to the output stream
	 * @param os
	 * @param e
	 */
	public void writeException(OutputStream os, OWSException e)
	{
		try
		{
			Element reportElt = (new OWSExceptionWriter()).buildXML(e);
			dom.serialize(reportElt, os, true);
		}
		catch (IOException e1)
		{
			e1.printStackTrace();
		}
	}
}