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

import org.vast.cdm.common.CDMException;
import org.vast.cdm.common.DataComponent;
import org.vast.cdm.common.DataEncoding;
import org.vast.ows.OWSException;
import org.vast.sweCommon.DataSourceDOM;
import org.vast.sweCommon.SWEData;
import org.vast.sweCommon.SweEncodingReaderV20;
import org.vast.util.DateTime;
import org.vast.util.DateTimeFormat;
import org.vast.xml.DOMHelper;
import org.w3c.dom.Element;


/**
 * <p><b>Title:</b><br/>
 * SPS Common Reader v2.0
 * </p>
 *
 * <p><b>Description:</b><br/>
 * Helper routines for parsing common info present
 * in several SPS requests/responses (i.e. SWE Data)
 * </p>
 *
 * <p>Copyright (c) 2007</p>
 * @author Alexandre Robin <alexandre.robin@spotimage.fr>
 * @date Feb, 29 2008
 * @version 1.0
 */
public class SPSCommonReaderV20
{
	protected SweEncodingReaderV20 encodingReader = new SweEncodingReaderV20();
		
	
	/**
	 * USed to read SWE Data stream such as inside taskingParameters or reportParameters
	 * @param dom
	 * @param paramsElt
	 * @param paramStructure
	 * @return
	 */
	public SWEData readSWEData(DOMHelper dom, Element paramsElt, DataComponent paramStructure) throws CDMException
	{
		// read encoding
		Element encodingElt = dom.getElement(paramsElt, "encoding");
		DataEncoding dataEncoding = encodingReader.readEncodingProperty(dom, encodingElt);
		
		// create SWEData object
		SWEData paramsData = new SWEData();
		paramsData.setDataComponents(paramStructure.copy()); // important to copy here in case we parse two things using the same params !!
		paramsData.setDataEncoding(dataEncoding);
		
		// data source is the content of an XML element!
		Element valuesElt = dom.getElement(paramsElt, "values");
		DataSourceDOM dataSource = new DataSourceDOM(dom, valuesElt);
				
		// launch parser to fill up the DataList inside the SWEData object
		paramsData.parseData(dataSource);
		
		return paramsData;
	}
	
	
	/**
	 * Reads common report attributes and add them to
	 * the given StatusReport object
	 * @param dom
	 * @param reportElt
	 * @param report
	 * @param report
	 * @param paramStructure
	 * @throws OWSException
	 */
	protected void readReportXML(DOMHelper dom, Element reportElt, StatusReport report, DataComponent paramStructure) throws OWSException
	{
		try
		{
			// title
			String title = dom.getElementValue(reportElt, "title");
			report.setTitle(title);
			
			// abstract
			String description = dom.getElementValue(reportElt, "abstract");
			report.setDescription(description);
			
			// taskID
			String id = dom.getElementValue(reportElt, "taskID");
			report.setTaskID(id);
			
			// sensorID
			String sensorID = dom.getElementValue(reportElt, "sensorID");
			report.setSensorID(sensorID);
			
			// update time
			String isoDate = dom.getElementValue(reportElt, "updateTime");
			if (isoDate != null)
			{
				DateTime lastUpdate = new DateTime(DateTimeFormat.parseIso(isoDate));
				report.setLastUpdate(lastUpdate);
			}
			
			// statusCode
			String statusCode = dom.getElementValue(reportElt, "statusCode");
			report.setStatusCode(statusCode);
			
			// estimatedToC
			isoDate = dom.getElementValue(reportElt, "estimatedToC");
			if (isoDate != null)
			{
				DateTime estimatedToC = new DateTime(DateTimeFormat.parseIso(isoDate));
				report.setEstimatedToC(estimatedToC);
			}
			
			// extended data
			Element extDataElt = dom.getElement(reportElt, "extendedData/*");
			if (extDataElt != null && paramStructure != null)
			{
				// parse data into a SWEData object
				SWEData extData = readSWEData(dom, extDataElt, paramStructure);
				report.setExtendedData(extData);
			}
		}
		catch (Exception e)
		{
			throw new SPSException(e);
		}
	}
	
	
	public AbstractReport readReport(DOMHelper dom, Element reportElt, DataComponent paramStructure) throws OWSException
	{
		AbstractReport report = null;
		
		if (dom.hasQName(reportElt, "sps:StatusNotification"))
			report = readStatusReport(dom, reportElt, paramStructure);
		else if (dom.hasQName(reportElt, "sps:StatusReport"))
			report = readStatusReport(dom, reportElt, paramStructure);
		else if (dom.hasQName(reportElt, "sps:ReservationReport"))
			report = readReservationReport(dom, reportElt, paramStructure);
		else if (dom.hasQName(reportElt, "sps:FeasibilityReport"))
			report = readFeasibilityReport(dom, reportElt, paramStructure);
		else
			throw new SPSException(SPSException.invalid_request_code, null, null, "Invalid Report Type");
			
		return report;
	}
	
	
	/**
	 * Reads a StatusReport using the provided structure for report parameters
	 * @param dom
	 * @param reportElt
	 * @param paramStructure
	 * @return
	 * @throws OWSException
	 */
	public StatusReport readStatusReport(DOMHelper dom, Element reportElt, DataComponent paramStructure) throws OWSException
	{
		StatusReport report = new StatusReport();
		readReportXML(dom, reportElt, report, paramStructure);
		return report;
	}
	
	
	/**
	 * Reads a ReservationReport using the provided structure for report parameters
	 * @param dom
	 * @param reportElt
	 * @param paramStructure
	 * @return
	 * @throws OWSException
	 */
	public ReservationReport readReservationReport(DOMHelper dom, Element reportElt, DataComponent paramStructure) throws OWSException
	{
		try
		{
			ReservationReport report = new ReservationReport();
			readReportXML(dom, reportElt, report, paramStructure);
			
			// expiration time
			String isoDate = dom.getElementValue(reportElt, "reservationExpiration");
			if (isoDate != null)
			{
				DateTime expiration = new DateTime(DateTimeFormat.parseIso(isoDate));
				report.setReservationExpiration(expiration);
			}
			
			return report;
		}
		catch (Exception e)
		{
			throw new SPSException(e);
		}
	}
	
	
	/**
	 * Reads a FeasibilityReport using the provided structure for study parameters
	 * @param dom
	 * @param reportElt
	 * @param paramStructure
	 * @return
	 * @throws OWSException
	 */
	public FeasibilityReport readFeasibilityReport(DOMHelper dom, Element reportElt, DataComponent paramStructure) throws OWSException
	{
		try
		{
			FeasibilityReport study = new FeasibilityReport();
			readReportXML(dom, reportElt, study, paramStructure);
			
			// TODO read alternatives
			
			return study;
		}
		catch (Exception e)
		{
			throw new SPSException(e);
		}
	}
}
