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

package org.vast.ows.sld;


/**
 * <p><b>Title:</b><br/>
 * Symbolizer
 * </p>
 *
 * <p><b>Description:</b><br/>
 * Abstract Base Symbolizer
 * </p>
 *
 * <p>Copyright (c) 2005</p>
 * @author Alexandre Robin
 * @date Nov 11, 2005
 * @version 1.0
 */
public abstract class Symbolizer
{
	protected String name;
    protected boolean enabled;
    protected Geometry geometry;

    
    public Symbolizer()
    {
        geometry = new Geometry();
    }
    
    
	public Geometry getGeometry()
	{
		return geometry;
	}


	public void setGeometry(Geometry geometry)
	{
		this.geometry = geometry;
	}


    public boolean isEnabled()
    {
        return enabled;
    }


    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }


    public String getName()
    {
        if (name == null)
            return this.getClass().getSimpleName();
        else
            return name;
    }


    public void setName(String name)
    {
        this.name = name;
    }
}
