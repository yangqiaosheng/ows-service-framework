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

package org.vast.ows.sld;

/**
 * <p><b>Title:</b><br/>
 * Font
 * </p>
 *
 * <p><b>Description:</b><br/>
 * SLD Font object
 * </p>
 *
 * <p>Copyright (c) 2005</p>
 * @author Alexandre Robin
 * @date Nov 11, 2005
 * @version 1.0
 */
public class Font
{
	protected ScalarParameter family;
	protected ScalarParameter style;
	protected ScalarParameter size;
	protected ScalarParameter weight;


	public ScalarParameter getFamily()
	{
		return family;
	}


	public void setFamily(ScalarParameter family)
	{
		this.family = family;
	}


	public ScalarParameter getSize()
	{
		return size;
	}


	public void setSize(ScalarParameter size)
	{
		this.size = size;
	}


	public ScalarParameter getStyle()
	{
		return style;
	}


	public void setStyle(ScalarParameter style)
	{
		this.style = style;
	}


	public ScalarParameter getWeight()
	{
		return weight;
	}


	public void setWeight(ScalarParameter weight)
	{
		this.weight = weight;
	}
}
