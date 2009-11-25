/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are subject to the Mozilla Public License Version
 1.1 (the "License"); you may not use this file except in compliance with
 the License. You may obtain a copy of the License at
 http://www.mozilla.org/MPL/MPL-1.1.html
 
 Software distributed under the License is distributed on an "AS IS" basis,
 WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 for the specific language governing rights and limitations under the License.
 
 The Original Code is the "OGC Service Framework".
 
 The Initial Developer of the Original Code is the VAST team at the
 
 Contributor(s): 
    Alexandre Robin <robin@nsstc.uah.edu>
 
******************************* END LICENSE BLOCK ***************************/

package org.vast.ows;

import org.w3c.dom.*;
import org.vast.xml.DOMHelper;


/**
 * <p><b>Title:</b><br/>
 * OWS Capabilities Reader
 * </p>
 *
 * <p><b>Description:</b><br/>
 * Base abstract class for reading an OWS server capabilities document.
 * This class instantiates a OWSServiceCapabilities object.
 * Descendants should add their own specific layer capabilities object 
 * to the list in OWSServiceCapablities.
 * </p>
 *
 * <p>Copyright (c) 2007</p>
 * @author Alexandre Robin
 * @date Oct 30, 2005
 * @version 1.0
 */
public abstract class AbstractCapabilitiesReader implements OWSResponseReader<OWSServiceCapabilities>
{
    protected final static String parsingError = "Error while parsing capabilities document";
    protected OWSServiceCapabilities serviceCaps;
    

    public AbstractCapabilitiesReader()
    {
    }
    
    
    /**
     * Read Capabilities from the given DOM document
     * @param dom
     * @param capabilitiesElt
     * @return
     * @throws OWSException
     */
    public abstract OWSServiceCapabilities readXMLResponse(DOMHelper dom, Element capabilitiesElt) throws OWSException;
    
    protected abstract void readContents(DOMHelper dom, Element capsElt) throws OWSException;
    
        
    /**
     * Read server GET and POST urls for each operation
     * @param operationMetadataElt
     * @param capabilities
     */
    protected void readOperationsMetadata(DOMHelper dom, Element capabilitiesElt) throws OWSException
    {
    	NodeList operationList = dom.getElements(capabilitiesElt, "OperationsMetadata/Operation");    	
    	for (int i=0; i<operationList.getLength(); i++)
        {
    		Element operationElt = (Element)operationList.item(i);
    		String name = dom.getAttributeValue(operationElt, "name");
    		
    		String getServer = dom.getAttributeValue(operationElt, "DCP/HTTP/Get/href");
    		if (getServer != null)
    			serviceCaps.getGetServers().put(name, getServer);
    		
    		String postServer = dom.getAttributeValue(operationElt, "DCP/HTTP/Post/href");
    		if (postServer != null)
    			serviceCaps.getPostServers().put(name, postServer);
        }
    }
}