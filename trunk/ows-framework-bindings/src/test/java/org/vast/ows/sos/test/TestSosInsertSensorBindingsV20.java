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

package org.vast.ows.sos.test;

import org.vast.ows.OWSUtils;
import org.vast.ows.test.OWSTestCase;


public class TestSosInsertSensorBindingsV20 extends OWSTestCase
{
    
    public void testReadXmlSensor() throws Exception
    {
        readXmlRequest("examples_v20/transactional/InsertSensor1.xml");
    }
    
    
    public void testReadWriteXmlInsertSensor() throws Exception
    {
        readWriteCompareXmlRequest("examples_v20/transactional/InsertSensor1.xml");
    }
    
    
    public void testReadWriteXmlInsertSensorResponse() throws Exception
    {
        readWriteCompareXmlResponse("examples_v20/transactional/InsertSensor1_response.xml", OWSUtils.SOS);
    }
}
