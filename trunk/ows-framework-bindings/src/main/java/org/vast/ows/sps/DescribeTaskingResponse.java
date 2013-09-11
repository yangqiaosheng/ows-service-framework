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

package org.vast.ows.sps;

import java.util.ArrayList;
import org.vast.cdm.common.DataComponent;
import org.vast.data.AbstractDataComponent;
import org.vast.data.DataIterator;
import org.vast.ows.OWSResponse;
import org.vast.sweCommon.SweConstants;


/**
 * <p><b>Title:</b>
 * DescribeTasking Response
 * </p>
 *
 * <p><b>Description:</b><br/>
 * TODO DescribeTaskingResponse type description
 * </p>
 *
 * <p>Copyright (c) 2008</p>
 * @author Alexandre Robin <alexandre.robin@spotimage.fr>
 * @date Feb, 25 2008
 * @version 1.0
 */
public class DescribeTaskingResponse extends OWSResponse
{
	protected DataComponent taskingParameters;
	protected DataComponent updatableParameters;
	

	public DescribeTaskingResponse()
	{
		this.service = SPSUtils.SPS;
        this.messageType = "DescribeTaskingResponse";
	}


	public DataComponent getTaskingParameters()
	{
		return taskingParameters;
	}


	public void setTaskingParameters(DataComponent taskingParameters)
	{
		this.taskingParameters = taskingParameters;
	}


	public DataComponent getUpdatableParameters()
	{
		if (updatableParameters != null)
			return updatableParameters;
		
		updatableParameters = taskingParameters.copy();
		updatableParameters.clearData();
		
		DataIterator it = new DataIterator(updatableParameters);
		ArrayList<DataComponent> componentsToDelete = new ArrayList<DataComponent>();
		while (it.hasNext())
		{
			DataComponent nextComp = it.next();
			Boolean updatable = (Boolean)nextComp.getProperty(SweConstants.UPDATABLE);
			if (updatable != null && updatable == false)
			{
				componentsToDelete.add(nextComp);
				if (nextComp.getComponentCount() > 0)
					it.skipChildren();
			}
		}
		
		// remove non-unupdatable parameters from tree
		for (DataComponent component: componentsToDelete)
		{
			AbstractDataComponent parent = ((AbstractDataComponent)component.getParent());
			
			if (parent == null)
				return null;
			
			// problem if it's the only child
			if (parent.getComponentCount() <= 1)
				throw new IllegalStateException("Component " + component.getName() +
						" should be updatable since its parent is updatable");
			
			int compIndex = parent.getComponentIndex(component.getName());
			parent.removeComponent(compIndex);
		}
		
		return updatableParameters;
	}


	public void setUpdatableParameters(DataComponent updatableParameters)
	{
		this.updatableParameters = updatableParameters;
	}
}
