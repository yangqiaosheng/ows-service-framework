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

import java.io.OutputStream;
import org.vast.xml.DOMHelper;
import org.w3c.dom.Element;


/**
 * 
 * <p>
 * 
 * </p>
 *
 * <p>Copyright (c) 2007</p>
 * @author Alexandre Robin <alexandre.robin@spotimage.fr>
 * @since 21 nov. 07
 * @version 1.0
 */
public interface OWSResponseWriter<ResponseType extends OWSResponse>
{
		
    /**
     * Builds a DOM element containing the response XML
     * Note that the element is not yet appended to any parent.
     * @param dom
     * @param response
     * @return
     */
    public Element buildXMLResponse(DOMHelper dom, ResponseType response) throws OWSException;
    
    
    /**
     * Builds a DOM element containing the response XML for given version
     * Note that the element is not yet appended to any parent.
     * @param dom
     * @param response
     * @param version
     * @return
     */
    public Element buildXMLResponse(DOMHelper dom, ResponseType response, String version) throws OWSException;
    
    
    /**
     * Writes the XML response directly to the output stream
     * @param os
     * @param response
     * @throws OWSException
     */
    public void writeXMLResponse(OutputStream os, ResponseType response) throws OWSException;
    
    
    /**
     * Writes the XML response for desired version directly to the output stream
     * @param os
     * @param response
     * @param version
     * @throws OWSException
     */
    public void writeXMLResponse(OutputStream os, ResponseType response, String version) throws OWSException;
}