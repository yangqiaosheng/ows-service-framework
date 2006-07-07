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
 * Stroke
 * </p>
 *
 * <p><b>Description:</b><br/>
 * SLD Stroke object.
 * Allows to specify stroke color, opacity, width,
 * line pattern and join/cap styles.
 * </p>
 *
 * <p>Copyright (c) 2005</p>
 * @author Alexandre Robin
 * @date Nov 11, 2005
 * @version 1.0
 */
public class Stroke
{
	protected Graphic graphicStroke;
	protected Graphic graphicFill;
	protected Color color = new Color();
	protected ScalarParameter opacity;
	protected ScalarParameter width;
	protected ScalarParameter linejoin;
	protected ScalarParameter linecap;
	protected ScalarParameter dasharray;
	protected ScalarParameter dashoffset;


	public Color getColor()
	{
		return color;
	}


	public void setColor(Color color)
	{
		this.color = color;
	}


	public ScalarParameter getDasharray()
	{
		return dasharray;
	}


	public void setDasharray(ScalarParameter dasharray)
	{
		this.dasharray = dasharray;
	}


	public ScalarParameter getDashoffset()
	{
		return dashoffset;
	}


	public void setDashoffset(ScalarParameter dashoffset)
	{
		this.dashoffset = dashoffset;
	}


	public Graphic getGraphicFill()
	{
		return graphicFill;
	}


	public void setGraphicFill(Graphic graphicFill)
	{
		this.graphicFill = graphicFill;
	}


	public Graphic getGraphicStroke()
	{
		return graphicStroke;
	}


	public void setGraphicStroke(Graphic graphicStroke)
	{
		this.graphicStroke = graphicStroke;
	}


	public ScalarParameter getLinecap()
	{
		return linecap;
	}


	public void setLinecap(ScalarParameter linecap)
	{
		this.linecap = linecap;
	}


	public ScalarParameter getLinejoin()
	{
		return linejoin;
	}


	public void setLinejoin(ScalarParameter linejoin)
	{
		this.linejoin = linejoin;
	}


	public ScalarParameter getOpacity()
	{
		return opacity;
	}


	public void setOpacity(ScalarParameter opacity)
	{
		this.opacity = opacity;
	}


	public ScalarParameter getWidth()
	{
		return width;
	}


	public void setWidth(ScalarParameter width)
	{
		this.width = width;
	}

}
