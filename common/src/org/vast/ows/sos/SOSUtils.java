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

package org.vast.ows.sos;

import org.vast.ows.OWSException;
import org.vast.ows.OWSUtils;
import org.vast.xml.DOMHelper;
import org.w3c.dom.Element;


/**
 * <p><b>Title:</b>
 * SOS Utils
 * </p>
 *
 * <p><b>Description:</b><br/>
 * Collection of helper methods and constants to simplify
 * implementation of SOS clients and servers.
 * </p>
 *
 * <p>Copyright (c) 2007</p>
 * @author Alexandre Robin
 * @date Feb 1, 2007
 * @version 1.0
 */
public class SOSUtils
{
    OWSUtils owsUtils = new OWSUtils();
    
    
    public SOSQuery readXMLQuery(DOMHelper dom, Element queryElt) throws SOSException
    {
        try
        {
            
            return (SOSQuery)owsUtils.readXMLQuery(dom, queryElt);
        }
        catch (OWSException e)
        {
            throw new SOSException(e.getMessage());
        }
    }
    
    
    public SOSQuery readURLQuery(String queryString) throws SOSException
    {
        try
        {
            return (SOSQuery)owsUtils.readURLQuery(queryString);
        }
        catch (OWSException e)
        {
            throw new SOSException(e.getMessage());
        }
    }

}