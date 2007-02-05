/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are subject to the Mozilla Public License Version
 1.1 (the "License"); you may not use this file except in compliance with
 the License. You may obtain a copy of the License at
 http://www.mozilla.org/MPL/MPL-1.1.html
 
 Software distributed under the License is distributed on an "AS IS" basis,
 WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 for the specific language governing rights and limitations under the License.
 
 The Original Code is the "SensorML DataProcessing Engine".
 
 The Initial Developer of the Original Code is the
 University of Alabama in Huntsville (UAH).
 Portions created by the Initial Developer are Copyright (C) 2006
 the Initial Developer. All Rights Reserved.
 
 Contributor(s): 
 Alexandre Robin <robin@nsstc.uah.edu>
 
 ******************************* END LICENSE BLOCK ***************************/

package org.vast.ows;

import java.io.OutputStream;

import org.vast.xml.DOMHelper;
import org.w3c.dom.Element;


/**
 * <p><b>Title:</b>
 * OWS Capabilities Writer
 * </p>
 *
 * <p><b>Description:</b><br/>
 * Base interface for all OWS capabilities writers
 * </p>
 *
 * <p>Copyright (c) 2005</p>
 * @author Alexandre Robin
 * @date Jan 16, 2007
 * @version 1.0
 */
public interface OWSCapabilitiesWriter<CapsType extends OWSLayerCapabilities>
{
   
    /**
     * Builds a DOM element containing the full service capabilities
     * @param dom
     * @param caps
     * @return
     * @throws OWSException
     */
    public Element buildServiceCapabilities(DOMHelper dom, OWSServiceCapabilities caps) throws OWSException;
    
    
    /**
     * Writes the full service capabilities XML to the specified output stream 
     * @param os
     * @param caps
     * @throws OWSException
     */
    public void writeServiceCapabilities(OutputStream os, OWSServiceCapabilities caps) throws OWSException;
    
    
    /**
     * Builds a DOM element containing the capabilities for one layer/offering
     * @param dom
     * @param caps
     * @return
     * @throws OWSException
     */
    public Element buildLayerCapabilities(DOMHelper dom, CapsType caps) throws OWSException;
    
    
    /**
     * Writes the given layer capabilities to the specified output stream
     * @param os
     * @param caps
     * @throws OWSException
     */
    public void writeLayerCapabilities(OutputStream os, CapsType caps) throws OWSException;

}