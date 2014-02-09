/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are subject to the Mozilla Public License Version
 1.1 (the "License"); you may not use this file except in compliance with
 the License. You may obtain a copy of the License at
 http://www.mozilla.org/MPL/MPL-1.1.html
 
 Software distributed under the License is distributed on an "AS IS" basis,
 WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 for the specific language governing rights and limitations under the License.
 
 The Original Code is the "OGC Service Framework".
 
 The Initial Developer of the Original Code is Sensia Software LLC.
 Portions created by the Initial Developer are Copyright (C) 2014
 the Initial Developer. All Rights Reserved.
 
 Please Contact Alexandre Robin <alex.robin@sensiasoftware.com> or
 Mike Botts <mike.botts@botts-inc.net> for more information.
 
 Contributor(s): 
    Alexandre Robin <alex.robin@sensiasoftware.com>
 
******************************* END LICENSE BLOCK ***************************/

package org.vast.ows.sos;

import org.vast.ogc.om.IObservation;
import org.vast.ows.OWSRequest;


/**
 * <p><b>Title:</b><br/>
 * InsertObservation Request
 * </p>
 *
 * <p><b>Description:</b><br/>
 * Container for SOS InsertObservation request parameters
 * </p>
 *
 * <p>Copyright (c) 2014</p>
 * @author Alexandre Robin
 * @date Feb 02, 2014
 * @version 1.0
 */
public class InsertObservationRequest extends OWSRequest
{
    protected String offering;
    protected IObservation observation;
    
	
	public InsertObservationRequest()
	{
		service = "SOS";
		operation = "InsertObservation";
	}


    public String getOffering()
    {
        return offering;
    }


    public void setOffering(String offering)
    {
        this.offering = offering;
    }


    public IObservation getObservation()
    {
        return observation;
    }


    public void setObservation(IObservation observation)
    {
        this.observation = observation;
    }    
}
