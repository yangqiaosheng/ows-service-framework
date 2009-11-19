/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are subject to the Mozilla Public License Version
 1.1 (the "License"); you may not use this file except in compliance with
 the License. You may obtain a copy of the License at
 http://www.mozilla.org/MPL/MPL-1.1.html
 
 Software distributed under the License is distributed on an "AS IS" basis,
 WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 for the specific language governing rights and limitations under the License.
 
 The Original Code is the "OGC Service Framework".
 
 The Initial Developer of the Original Code is Spotimage S.A.
 Portions created by the Initial Developer are Copyright (C) 2007
 the Initial Developer. All Rights Reserved.
 
 Contributor(s): 
    Alexandre Robin <alexandre.robin@spotimage.fr>
 
******************************* END LICENSE BLOCK ***************************/

package org.vast.ows.wcs;

import org.vast.ows.AbstractRequestReader;
import org.vast.ows.OWSException;
import org.vast.xml.DOMHelper;
import org.w3c.dom.Element;


/**
 * <p><b>Title:</b><br/>
 * WCS Common Reader V1.1.1
 * </p>
 *
 * <p><b>Description:</b><br/>
 * Util method to read XML common to several WCS documents
 * </p>
 *
 * <p>Copyright (c) 2007</p>
 * @author Alexandre Robin <alexandre.robin@spotimage.fr>
 * @date 4 d�c. 07
 * @version 1.0
 */
public class WCSCommonReaderV11
{
	
	public void readGridCRS(DOMHelper dom, Element gridElt, WCSRectifiedGridCrs gridCrs) throws OWSException
    {
		// base CRS
		String responseCrs = dom.getElementValue(gridElt, "GridBaseCRS");
		gridCrs.setBaseCrs(responseCrs);
		
		// type
		String gridType = dom.getElementValue(gridElt, "GridType");
		if (gridType != null)
			gridCrs.setGridType(gridType);
		
		// CS
		String gridCs = dom.getElementValue(gridElt, "GridCS");
		if (gridCs != null)
			gridCrs.setGridCs(gridCs);
		
		// origin
		String gridOrigin = dom.getElementValue(gridElt, "GridOrigin");
		if (gridOrigin != null)
		{
			double[] vecOrig = AbstractRequestReader.parseVector(gridOrigin);
			gridCrs.setGridDimension(vecOrig.length);
			gridCrs.setGridOrigin(vecOrig);
		}
		
		// offsets
		String gridOffsets = dom.getElementValue(gridElt, "GridOffsets");
		if (gridOffsets != null)
		{
			double[][] vectors = WCSCommonReaderV11.parseGridOffsets(gridOffsets);
			gridCrs.setGridOffsets(vectors);
		}
    }
	
	
	/**
     * Utility method to parse a list of vectors from flat list of comma/space separated decimal values
     * @param argValue
     * @return
     */
    protected static double[][] parseGridOffsets(String vectorText) throws OWSException
    {
    	try
        {
    		String[] elts = vectorText.trim().split("[ ,]");
    		int gridDim = (int)Math.sqrt(elts.length);
    		double[][] vec = new double[gridDim][gridDim];
	    	
    		int index = 0;
    		for (int i=0; i<gridDim; i++)
    			for (int j=0; j<gridDim; j++)
    				vec[i][j] = Double.parseDouble(elts[index++]);
    			
	    	return vec;
        }
        catch (NumberFormatException e)
        {
            throw new OWSException("Invalid Grid Offsets: " + vectorText, e);
        }
    }
}
