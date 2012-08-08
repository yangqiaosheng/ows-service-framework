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

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.vast.xml.DOMHelper;
import org.w3c.dom.*;
import org.vast.ows.*;


/**
 * <p><b>Title:</b><br/>
 * DescribeCoverage Request Reader v1.0
 * </p>
 *
 * <p><b>Description:</b><br/>
 * Provides methods to parse a KVP or XML DescribeCoverage request and
 * create a DescribeCoverageRequest object for version 1.0
 * </p>
 *
 * <p>Copyright (c) 2007</p>
 * @author Alexandre Robin
 * @date Sep 21, 2007
 * @version 1.0
 */
public class DescribeCoverageReaderV10 extends AbstractRequestReader<DescribeCoverageRequest>
{
	
	@Override
	public DescribeCoverageRequest readURLParameters(Map<String, String> queryParameters) throws OWSException
	{
		OWSExceptionReport report = new OWSExceptionReport(OWSException.VERSION_10);
		DescribeCoverageRequest request = new DescribeCoverageRequest();
		Iterator<Entry<String, String>> it = queryParameters.entrySet().iterator();
        
        while (it.hasNext())
        {
            Entry<String, String> item = it.next();
            String argName = item.getKey();
            String argValue = item.getValue();
            
            try
            {
	            // SERVICE
	            if (argName.equalsIgnoreCase("SERVICE"))
	            {
	                request.setService(argValue);
	            }
	            
	            // VERSION
	            else if (argName.equalsIgnoreCase("VERSION"))
	            {
	                request.setVersion(argValue);
	            }
	
	            // REQUEST
	            else if (argName.equalsIgnoreCase("REQUEST"))
	            {
	                request.setOperation(argValue);
	            }
	            
	            // COVERAGE list
	            else if (argName.equalsIgnoreCase("COVERAGE"))
	            {
	                String[] coverageList = argValue.split(",");
	                for (int i=0; i<coverageList.length; i++)
	                	request.getCoverages().add(coverageList[i]);
	            }
	        }
	        catch (Exception e)
			{
				report.add(new WCSException(OWSException.invalid_param_code, argName.toUpperCase(), argValue, null));
			}
	    }
		
	    report.process();
	    this.checkParameters(request, report); 
        return request;
	}
	
	
	@Override
	public DescribeCoverageRequest readXMLQuery(DOMHelper dom, Element requestElt) throws OWSException
	{
		OWSExceptionReport report = new OWSExceptionReport(OWSException.VERSION_10);
		DescribeCoverageRequest request = new DescribeCoverageRequest();
		
		// do common stuffs like version, request name and service type
		readCommonXML(dom, requestElt, request);
		
		// get all Coverage ids
		NodeList covElts = dom.getElements(requestElt, "Coverage");
		for (int i=0; i<covElts.getLength(); i++)
		{
			String val = dom.getElementValue((Element)covElts.item(i));
			request.getCoverages().add(val);
		}
				
		this.checkParameters(request, report);
		report.process();
		
		return request;
	}
	
	
	/**
     * Checks that DescribeCoverage mandatory parameters are present
     * @param request
     * @throws OWSException
     */
	protected void checkParameters(DescribeCoverageRequest request, OWSExceptionReport report) throws OWSException
    {
		// check common params
		super.checkParameters(request, report, OWSUtils.WCS);
		
		// need at least one coverage ID
		if (request.getCoverages().isEmpty())
			report.add(new OWSException(OWSException.missing_param_code, "COVERAGE"));
		
		report.process();
	}
}