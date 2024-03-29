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

package org.vast.ows.sas;

import org.vast.ows.OWSException;


/**
 * <p><b>Title:</b><br/>
 * SAS Exception
 * </p>
 *
 * <p><b>Description:</b><br/>
 * Exception object to carry SAS error messages.
 * </p>
 *
 * <p>Copyright (c) 2007</p>
 * @author Gregoire Berthiau <berthiau@nsstc.uah.edu>
 * @date 07 jul. 06
 * @version 1.0
 */
public class SASException extends OWSException
{
	static final long serialVersionUID = 0x59DB55493721FA3CL;
		
	
	public SASException(String message)
	{
		super(message);
	}
	
	
	public SASException(Exception e)
	{
		super(e);
	}
	
	
	public SASException(String message, Exception e)
	{
		super(message, e);
	}
	
	
	public SASException(String code, String locator, String badValue, String message)
	{
		super(code, locator, badValue, message);
	}
}
