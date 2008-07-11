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
    Alexandre Robin <alexandre.robin@spotimage.fr>
 
******************************* END LICENSE BLOCK ***************************/

package org.vast.ows.sas;

import java.util.*;
import org.vast.ows.OWSRequest;
import org.vast.ows.util.Bbox;
import org.vast.ows.util.TimeInfo;


/**
 * <p><b>Title:</b><br/>
 * SAS Subscribe Response
 * </p>
 *
 * <p><b>Description:</b><br/>
 * Container for SAS Subscribe request parameters
 * </p>
 *
 * <p>Copyright (c) 2007</p>
 * @author Gregoire Berthiau
 * @date Jul 09, 2008
 * @version 1.0
 */
public class SASSubscribeResponse
{
	protected String service = "SAS";
	protected String operation = "Subscribe";
    protected String subscriptionOfferingID = null;
	protected String expiration = null;
	protected String XMPPURI = null;
	
	public SASSubscribeResponse()
	{
	}

	public String getSubscriptionOfferingID()
	{
		return subscriptionOfferingID;
	}

	public void setSubscriptionOfferingID(String subscriptionOfferingID)
	{
		this.subscriptionOfferingID = subscriptionOfferingID;
	}
	
	public String getExpiration()
	{
		return expiration;
	}

	public void setExpiration(String expiration)
	{
		this.expiration = expiration;
	}

	public String getXMPPURI()
	{
		return XMPPURI;
	}

	public void setXMPPURI(String XMPPURI)
	{
		this.XMPPURI = XMPPURI;
	}
	
}
