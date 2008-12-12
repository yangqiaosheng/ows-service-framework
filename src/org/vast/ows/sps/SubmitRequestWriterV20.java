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

import org.vast.ows.OWSException;


/**
* <p><b>Title:</b><br/>
* SPS Submit Request Writer v2.0
* </p>
*
* <p><b>Description:</b><br/>
* Provides methods to generate a SPS Submit request based
* on values contained in a Submit object for version 2.0
* </p>
*
* <p>Copyright (c) 2008</p>
* @author Alexandre Robin <alexandre.robin@spotimage.fr>
* @date Feb, 28 2008
* @version 1.0
*/
public class SubmitRequestWriterV20 extends TaskingRequestWriterV20<SubmitRequest>
{

	/**
	 * KVP Request
	 */
	@Override
	public String buildURLQuery(SubmitRequest request) throws OWSException
	{
		throw new SPSException(noKVP + "SPS 2.0 Submit");
	}
}