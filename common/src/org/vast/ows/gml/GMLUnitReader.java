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

package org.vast.ows.gml;

import org.vast.xml.DOMHelper;
import org.vast.unit.Unit;
import org.w3c.dom.Element;


/**
 * <p><b>Title:</b>
 * GML Unit Reader
 * </p>
 *
 * <p><b>Description:</b><br/>
 * TODO GMLUnitReader type description
 * </p>
 *
 * <p>Copyright (c) 2007</p>
 * @author Alexandre Robin
 * @date Feb 9, 2007
 * @version 1.0
 */
public class GMLUnitReader
{
    protected final static String invalidISO = "Invalid Unit: ";
    
    
    public GMLUnitReader()
    {
    }
    
    
    public Unit readUnit(DOMHelper dom, Element timeElt) throws GMLException
    {
        String eltName = timeElt.getLocalName();
        
        if (eltName.equals("UnitDefinition"))
            return readUnitDefinition(dom, timeElt);
        else if (eltName.equals("BaseUnit"))
            return readBaseUnit(dom, timeElt);
        else if (eltName.equals("DerivedUnit"))
            return readDerivedUnit(dom, timeElt);
        else if (eltName.equals("ConventionalUnit"))
            return readDerivedUnit(dom, timeElt);
        
        throw new GMLException("Unsupported Unit Type: " + eltName);
    }
    
    
    public Unit readUnitDefinition(DOMHelper dom, Element timeElt) throws GMLException
    {
        readCommons(null, dom, timeElt);
        return null;
    }
    
    
    public Unit readBaseUnit(DOMHelper dom, Element timeElt) throws GMLException
    {
        readCommons(null, dom, timeElt);
        return null;
    }
    
    
    public Unit readDerivedUnit(DOMHelper dom, Element timeElt) throws GMLException
    {
        readCommons(null, dom, timeElt);
        return null;
    }
    
    
    public Unit readConventionalUnit(DOMHelper dom, Element timeElt) throws GMLException
    {
        readCommons(null, dom, timeElt);
        return null;
    }
    
    
    private void readCommons(Unit unit, DOMHelper dom, Element timeElt) throws GMLException
    {
        
    }
}
