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

import java.util.*;
import org.vast.util.ResponsibleParty;


/**
 * <p>
 * Representation of an OWS service capabilities document.
 * </p>
 *
 * <p>Copyright (c) 2007</p>
 * @author Alexandre Robin
 * @since Oct 27, 2005
 * @version 1.0
 */
public class OWSServiceCapabilities extends OWSResponse
{
	// updateSequence
	protected String updateSequence = "0";
	
	// service identification
	protected OWSIdentification identification;
	
	// additional service options
	protected List<String> supportedVersions;
	protected List<String> profiles;
	protected String fees;
	protected String accessConstraints;
		
	// service provider
	protected ResponsibleParty serviceProvider;
	
	// notification capabilities
	protected OWSNotificationService notificationCapabilities;
	
	// list of layers
	protected List<OWSLayerCapabilities> layers;
	
	// list of supported exception types
	protected List<String> exceptionTypes;
	
	// tables of operation->url
	protected Hashtable<String, String> getServers;
	protected Hashtable<String, String> postServers;
	
	
    public OWSServiceCapabilities()
    {
    	messageType = "Capabilities";
    	identification = new OWSIdentification();
    	serviceProvider = new ResponsibleParty();
    	supportedVersions = new ArrayList<String>(1);
    	profiles = new ArrayList<String>(5);
    	layers = new ArrayList<OWSLayerCapabilities>(10);
    	exceptionTypes = new ArrayList<String>(1);
    	getServers = new Hashtable<String, String>();
    	postServers = new Hashtable<String, String>();    	
    }
	
	
	public String getUpdateSequence()
	{
		return updateSequence;
	}


	public void setUpdateSequence(String updateSequence)
	{
		this.updateSequence = updateSequence;
	}
	
	
	public void increaseUpdateSequence()
	{
		try
		{
			int seq = Integer.parseInt(this.updateSequence);
			seq++;
			this.updateSequence = Integer.toString(seq);
		}
		catch (Exception e)
		{
			this.updateSequence = "1";
		}
	}


	public OWSIdentification getIdentification()
	{
		return identification;
	}


	public void setIdentification(OWSIdentification identification)
	{
		this.identification = identification;
	}
	
	
	public String getFees()
	{
		return fees;
	}


	public void setFees(String fees)
	{
		this.fees = fees;
	}


	public String getAccessConstraints()
	{
		return accessConstraints;
	}


	public void setAccessConstraints(String accessConstraints)
	{
		this.accessConstraints = accessConstraints;
	}
	
	
	public List<String> getSupportedVersions()
	{
		return supportedVersions;
	}


	public List<String> getProfiles()
    {
        return profiles;
    }


    public void setProfiles(List<String> profiles)
    {
        this.profiles = profiles;
    }


    public ResponsibleParty getServiceProvider()
	{
		return serviceProvider;
	}


	public void setServiceProvider(ResponsibleParty serviceProvider)
	{
		this.serviceProvider = serviceProvider;
	}
    
    
    public OWSNotificationService getNotificationCapabilities()
	{
		return notificationCapabilities;
	}


	public void setNotificationCapabilities(OWSNotificationService notificationCapabilities)
	{
		this.notificationCapabilities = notificationCapabilities;
	}


	public Hashtable<String, String> getGetServers()
	{
		return getServers;
	}
    
    
	public void setGetServers(Hashtable<String, String> getServers)
	{
		this.getServers = getServers;
	}


	public Hashtable<String, String> getPostServers()
	{
		return postServers;
	}
	
	
	public void setPostServers(Hashtable<String, String> postServers)
	{
		this.postServers = postServers;
	}
	
	
	public List<OWSLayerCapabilities> getLayers()
	{
		return layers;
	}
	
	
	public void setLayers(List<OWSLayerCapabilities> layers)
	{
		this.layers = layers;
	}
	
	
	/**
	 * Retrieves LayerCapabilities object corresponding to
	 * this layerId or null if not found 
	 * @param layerId
	 * @return
	 */
	public OWSLayerCapabilities getLayer(String layerId)
	{
		for (int i=0; i<layers.size(); i++)
		{
			OWSLayerCapabilities nextLayer = layers.get(i);
			String nextId = nextLayer.getIdentifier();
			if (nextId.equals(layerId))
				return nextLayer;
		}
		
		return null;
	}


	public List<String> getExceptionTypes()
	{
		return exceptionTypes;
	}


	public void setExceptionTypes(List<String> exceptionTypes)
	{
		this.exceptionTypes = exceptionTypes;
	}
	
	
	public String toString()
    {
    	StringBuffer text = new StringBuffer(service + " Service Capabilities: ");
    	
    	text.append(identification.getTitle());
    			
    	String desc = identification.getDescription();
    	if (desc != null)
    	{
    		text.append("\n");
        	text.append("Description: ");
    		text.append(desc);
    	}
    	
    	text.append("\n");
    	text.append("Layer list: \n");
    	
    	for (int i=0; i<layers.size(); i++)
    	{
    		text.append("   ");
    		text.append(layers.get(i).toString());
    		text.append("\n");
    	}
    	
    	return text.toString();
    }
}
