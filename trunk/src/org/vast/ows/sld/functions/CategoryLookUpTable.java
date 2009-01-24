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

package org.vast.ows.sld.functions;

import java.util.Hashtable;


/**
 * <p><b>Title:</b><br/>
 * Category LookUp Table
 * </p>
 *
 * <p><b>Description:</b><br/>
 * This uses a String input value and maps it to a numerical value.
 * This is used to map categorical values
 * </p>
 *
 * <p>Copyright (c) 2005</p>
 * @author Alexandre Robin
 * @date Apr 3, 2006
 * @version 1.0
 */
public class CategoryLookUpTable extends AbstractMappingFunction
{
    protected Hashtable<String, Double> mappingTable;
    protected double defaultValue = Double.NaN;
    
    
    public CategoryLookUpTable()
    {
        this.mappingTable = new Hashtable<String, Double>();
    }
    
    
    @Override
    public double compute(String indexVal)
    {
        Double val = mappingTable.get(indexVal);
        if (val != null)
            return val;
        else
            return defaultValue;
    }
    
    
    @Override
    public boolean hasCategoricalInput()
    {
        return true;
    }


    public Hashtable<String, Double> getMappingTable()
    {
        return mappingTable;
    }


    public void addMapping(String text, double val)
    {
        mappingTable.put(text, val);
    }


    public double getDefaultValue()
    {
        return defaultValue;
    }


    public void setDefaultValue(double defaultValue)
    {
        this.defaultValue = defaultValue;
    }
}
