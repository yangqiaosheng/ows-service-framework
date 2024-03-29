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
 * Graphic Mark
 * </p>
 *
 * <p><b>Description:</b><br/>
 * SLD Mark object.
 * Allows to specify mark fill and color.
 * </p>
 *
 * <p>Copyright (c) 2005</p>
 * @author Alexandre Robin
 * @date Nov 11, 2005
 * @version 1.0
 */
public class GraphicMark implements GraphicSource
{
	protected ScalarParameter shape;
	protected Stroke stroke;
	protected Fill fill;


	public Fill getFill()
	{
		return fill;
	}


	public void setFill(Fill fill)
	{
		this.fill = fill;
	}


	public ScalarParameter getShape()
	{
		return shape;
	}


	public void setShape(ScalarParameter shape)
	{
		this.shape = shape;
	}


	public Stroke getStroke()
	{
		return stroke;
	}


	public void setStroke(Stroke stroke)
	{
		this.stroke = stroke;
	}
}
