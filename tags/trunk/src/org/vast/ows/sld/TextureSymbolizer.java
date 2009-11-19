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
 * Texture Mapping Symbolizer
 * </p>
 *
 * <p><b>Description:</b><br/>
 * SLD-X Texture Mapping Symbolizer object.
 * Allows to use data as a 3D mapped texture.
 * </p>
 *
 * <p>Copyright (c) 2005</p>
 * @author Alexandre Robin
 * @date Nov 10, 2005
 * @version 1.0
 */
public class TextureSymbolizer extends RasterSymbolizer
{
    protected Dimensions gridDimensions;
    protected Geometry textureCoordinates;
    

    public TextureSymbolizer()
    {
        textureCoordinates = new Geometry();
    }
    
    
    public Dimensions getGridDimensions()
    {
        return gridDimensions;
    }

    
    public void setGridDimensions(Dimensions gridDimensions)
    {
        this.gridDimensions = gridDimensions;
    }
    
    
    public Geometry getTextureCoordinates()
    {
        return textureCoordinates;
    }


    public void setTextureCoordinates(Geometry textureCoordinates)
    {
        this.textureCoordinates = textureCoordinates;
    }
}
