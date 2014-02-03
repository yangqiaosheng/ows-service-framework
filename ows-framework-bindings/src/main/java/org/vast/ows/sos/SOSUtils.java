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
 University of Alabama in Huntsville (UAH). <http://vast.uah.edu>
 Portions created by the Initial Developer are Copyright (C) 2007
 the Initial Developer. All Rights Reserved.

 Please Contact Mike Botts <mike.botts@uah.edu>
 or Alexandre Robin <alex.robin@sensiasoftware.com> for more information.
 
 Contributor(s): 
    Alexandre Robin <alex.robin@sensiasoftware.com>
 
******************************* END LICENSE BLOCK ***************************/

package org.vast.ows.sos;

import org.vast.cdm.common.DataComponent;
import org.vast.cdm.common.DataEncoding;
import org.vast.ogc.OGCRegistry;
import org.vast.ows.AbstractRequestReader;
import org.vast.ows.OWSException;
import org.vast.ows.OWSRequest;
import org.vast.ows.OWSRequestReader;
import org.vast.ows.OWSUtils;
import org.vast.ows.SweEncodedMessageProcessor;
import org.vast.xml.DOMHelper;
import org.w3c.dom.Element;


public class SOSUtils extends OWSUtils
{
    
    public OWSRequest readSweEncodedRequest(DOMHelper dom, Element requestElt, DataComponent structure, DataEncoding encoding) throws OWSException
    {
        // skip SOAP envelope if present
        if (requestElt.getNamespaceURI().equals(soap12Uri))
            requestElt = dom.getElement(requestElt, "Body/*");
        
        OWSRequest tempReq = new OWSRequest();
        AbstractRequestReader.readCommonXML(dom, requestElt, tempReq);
                
        OWSRequestReader<?> reader = (OWSRequestReader<?>)OGCRegistry.createReader(SOSUtils.SOS, tempReq.getOperation(), tempReq.getVersion());
        ((SweEncodedMessageProcessor)reader).setSweCommonStructure(structure, encoding);
        return reader.readXMLQuery(dom, requestElt);
    }
    
}
