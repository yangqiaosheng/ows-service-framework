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
 * CssParameter
 * </p>
 *
 * <p><b>Description:</b><br/>
 * SLD/CSS Parameter object extended to support mapping functions
 * such as color maps and other look up tables.
 * </p>
 *
 * <p>Copyright (c) 2005</p>
 * @author Alexandre Robin
 * @date Nov 11, 2005
 * @version 1.0
 */
public class ScalarParameter
{
	protected String propertyName;
	protected Object constantValue;
	protected MappingFunction mappingFunction;
    protected boolean constant = true;
	
	
	public Object getValue()
	{
		return getConstantValue();
	}
    
    
    public boolean isConstant()
    {
        return constant;
    }
    
    
    public void setConstant(boolean constant)
    {
        this.constant = constant;
    }
	
	
	public Object getMappedValue(Object inputValue)
	{
		return getConstantValue();
	}


	public Object getConstantValue()
	{
		return constantValue;
	}
	
	
	public void setConstantValue(Object constantValue)
	{
		this.constantValue = constantValue;
        this.constant = true;
	}


	public MappingFunction getMappingFunction()
	{
		return mappingFunction;
	}


	public void setMappingFunction(MappingFunction mappingFunction)
	{
		this.mappingFunction = mappingFunction;
        this.constant = false;
	}


	public String getPropertyName()
	{
		return propertyName;
	}


	public void setPropertyName(String propertyName)
	{
		this.propertyName = propertyName;
        this.constant = false;
	}
}
