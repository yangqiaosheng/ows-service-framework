/**************************************************************

This software is the property of Spot Image S.A.

This software is distributed under the License and on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied. See the License for the
specific language governing permissions and limitations
under the License.

Copyright 2002-2007, Spot Image S.A. ALL RIGHTS RESERVED

***************************************************************/

package org.vast.ows.wcs;

import java.util.ArrayList;
import java.util.List;
import org.vast.ows.OWSIdentification;
import org.vast.unit.Unit;
import org.vast.cdm.common.DataType;


/**
 * <p><b>Title:</b><br/>
 * Range Axis
 * </p>
 *
 * <p><b>Description:</b><br/>
 * Information on a range axis
 * (ex: name=xs1, uom=W.sr-1.m-2.um, type=short, min=1, max=65500, null=0) 
 * </p>
 *
 * <p>Copyright (c) 2007</p>
 * @author Alexandre Robin <alexandre.robin@spotimage.fr>
 * @date 6 nov. 07
 * @version 1.0
 */
public class RangeAxis extends OWSIdentification
{
	protected Object nullValue;
	protected DataType dataType;
	protected String definition;
	protected Unit unit;
	protected int min;
	protected int max;
	protected List<String> keys;
	

	public RangeAxis()
	{
		keys = new ArrayList<String>(5);
	}
	
	
	public Object getNullValue()
	{
		return nullValue;
	}


	public void setNullValue(Object nullValue)
	{
		this.nullValue = nullValue;
	}


	public DataType getDataType()
	{
		return dataType;
	}


	public void setDataType(DataType dataType)
	{
		this.dataType = dataType;
	}
	
	
	public Unit getUnit()
	{
		return this.unit;
	}
	
	
	public void setUnit(Unit unit)
	{
		this.unit = unit;
	}


	public int getMin()
	{
		return min;
	}


	public void setMin(int min)
	{
		this.min = min;
	}


	public int getMax()
	{
		return max;
	}


	public void setMax(int max)
	{
		this.max = max;
	}


	public List<String> getKeys()
	{
		return keys;
	}


	public void setKeys(List<String> keys)
	{
		this.keys = keys;
	}


	public String getDefinition()
	{
		return definition;
	}


	public void setDefinition(String definition)
	{
		this.definition = definition;
	}
}