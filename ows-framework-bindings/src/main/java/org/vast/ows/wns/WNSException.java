/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are subject to the Mozilla Public License Version
 1.1 (the "License"); you may not use this file except in compliance with
 the License. You may obtain a copy of the License at
 http://www.mozilla.org/MPL/MPL-1.1.html
 
 Software distributed under the License is distributed on an "AS IS" basis,
 WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 for the specific language governing rights and limitations under the License.
 
 The Original Code part of the "OGC Service Framework".
 
 The Initial Developer of the Original Code is Spotimage S.A.
 Portions created by the Initial Developer are Copyright (C) 2007
 the Initial Developer. All Rights Reserved.
 
 Contributor(s): 
    Alexandre Robin <alexandre.robin@spotimage.fr>
 
******************************* END LICENSE BLOCK ***************************/

package org.vast.ows.wns;

import org.vast.ows.OWSException;


/**
 * <p><b>Title:</b><br/>
 * WNS Exception
 * </p>
 *
 * <p><b>Description:</b><br/>
 * Exception object to carry WNS error messages.
 * </p>
 *
 * <p>Copyright (c) 2007</p>
 * @author Alexandre Robin <alexandre.robin@spotimage.fr>
 * @date Jan 16, 2008
 * @version 1.0
 */
public class WNSException extends OWSException
{
	static final long serialVersionUID = -7139393367380917111L;


	public WNSException(String message)
	{
		super(message);
	}
	
	
	public WNSException(Exception e)
	{
		super(e);
	}
	
	
	public WNSException(String message, Exception e)
	{
		super(message, e);
	}
	
	
	public WNSException(String code, String locator)
	{
		super(code, locator);
	}
	
	
	public WNSException(String code, String locator, String badValue, String message)
	{
		super(code, locator, badValue, message);
	}
}
