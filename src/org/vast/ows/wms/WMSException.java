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

package org.vast.ows.wms;

import org.vast.ows.OWSException;


/**
 * <p><b>Title:</b><br/>
 * WMS Exception
 * </p>
 *
 * <p><b>Description:</b><br/>
 * Exception object to carry WMS error messages.
 * </p>
 *
 * <p>Copyright (c) 2007</p>
 * @author Alexandre Robin <robin@nsstc.uah.edu>
 * @date 07 jul. 06
 * @version 1.0
 */
public class WMSException extends OWSException
{
	static final long serialVersionUID = 0x0D57D045A3588AE34L;
		
	
	public WMSException(String message)
	{
		super(message);
	}
	
	
	public WMSException(Exception e)
	{
		super(e);
	}
	
	
	public WMSException(String message, Exception e)
	{
		super(message, e);
	}
	
	
	public WMSException(String code, String locator, String badValue, String message)
	{
		super(code, locator, badValue, message);
	}
}