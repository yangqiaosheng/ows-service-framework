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

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.vast.ogc.OGCRegistry;
import org.vast.xml.QName;


/**
 * <p>
 * Base class for all OWS style request objects
 * </p>
 *
 * <p>Copyright (c) 2007</p>
 * @author Alexandre Robin
 * @since Oct 27, 2005
 * @version 1.0
 */
public class OWSRequest
{
	protected final static String EXCEPTION_MIME_TYPE = "application/vnd.ogc.se_xml";
	
	protected transient HttpServletRequest httpRequest;
	protected transient HttpServletResponse httpResponse;
	protected String getServer;
	protected String postServer;
	protected String service;
	protected String version;
	protected String operation;
    protected String exceptionType;
    protected Map<QName, Object> extensions;
    
	
    public OWSRequest()
    {
    	exceptionType = EXCEPTION_MIME_TYPE;
    	extensions = new HashMap<QName, Object>();
    }
    
    
    /**
	 * Method used to normalize server KVP endpoint (always add ? or &) 
	 * @param url
	 * @return
	 */
	public static String checkServer(String url)
    {
    	if (url == null) return null;
		
		// Sometimes the server name already contains the ?
        // It can also contain a first argument and the final &
        String firstChar = "?";
        if (url.endsWith("&") || url.endsWith("?"))
            firstChar = "";
        else if (url.indexOf("?") != -1)
            firstChar = "&";
        
        return url + firstChar;
    }
	
	
	public OutputStream getResponseStream()
	{
		try {
			return httpResponse.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	
	public String getGetServer()
	{
		return getServer;
	}


	public void setGetServer(String getServer)
	{
		this.getServer = checkServer(getServer);
	}


	public String getPostServer()
	{
		return postServer;
	}


	public void setPostServer(String postServer)
	{
		this.postServer = checkServer(postServer);
	}


	public String getOperation()
	{
		return operation;
	}


	public void setOperation(String operation)
	{
		this.operation = operation;
	}


	public String getService()
	{
		return service;
	}


	public void setService(String service)
	{
		this.service = service;
	}


	public String getVersion()
	{
		return version;
	}
	
	
	public String getNormalizedVersion()
	{
		return OGCRegistry.normalizeVersionString(version);
	}


	public void setVersion(String version)
	{
		this.version = version;
	}


    public String getExceptionType()
    {
        return exceptionType;
    }


    public void setExceptionType(String exceptionType)
    {
        this.exceptionType = exceptionType;
    }
    
    
    public Map<QName, Object> getExtensions()
	{
		return extensions;
	}
    

    public HttpServletRequest getHttpRequest()
	{
		return httpRequest;
	}


	public void setHttpRequest(HttpServletRequest httpRequest)
	{
		this.httpRequest = httpRequest;
	}
	
	
	public HttpServletResponse getHttpResponse()
	{
		return httpResponse;
	}


	public void setHttpResponse(HttpServletResponse httpResponse)
	{
		this.httpResponse = httpResponse;
	}
}
