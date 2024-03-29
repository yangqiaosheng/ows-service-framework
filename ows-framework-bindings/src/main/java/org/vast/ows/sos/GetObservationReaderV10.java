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
    Alexandre Robin <alexandre.robin@spotimage.fr>
 
******************************* END LICENSE BLOCK ***************************/

package org.vast.ows.sos;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.vast.util.Bbox;
import org.vast.util.TimeExtent;
import org.vast.xml.DOMHelper;
import org.vast.xml.XMLReaderException;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.vast.ogc.gml.GMLEnvelopeReader;
import org.vast.ogc.gml.GMLTimeReader;
import org.vast.ows.*;
import org.vast.ows.sos.GetObservationRequest.ResponseMode;


/**
 * <p><b>Title:</b><br/>
 * SOS GetObservation Request Reader v1.0
 * </p>
 *
 * <p><b>Description:</b><br/>
 * Provides methods to parse a KVP or XML SOS GetObservation
 * request and create a GetObservationRequest object for version 1.0
 * </p>
 *
 * <p>Copyright (c) 2007</p>
 * @author Alexandre Robin
 * @date Oct 10, 2007
 * @version 1.0
 */
public class GetObservationReaderV10 extends AbstractRequestReader<GetObservationRequest>
{
    protected GMLTimeReader timeReader;
    protected GMLEnvelopeReader bboxReader;
    
    
    public GetObservationReaderV10()
	{
        timeReader = new GMLTimeReader();
        bboxReader = new GMLEnvelopeReader();
	}

	
	@Override
	public GetObservationRequest readURLParameters(Map<String, String> queryParameters) throws OWSException
	{
		OWSExceptionReport report = new OWSExceptionReport(OWSException.VERSION_11);
		GetObservationRequest request = new GetObservationRequest();
		Iterator<Entry<String, String>> it = queryParameters.entrySet().iterator();
        
        while (it.hasNext())
        {
            Entry<String, String> item = it.next();
            String argName = item.getKey();
            String argValue = item.getValue();
			
			// service ID
			if (argName.equalsIgnoreCase("service"))
			{
				request.setService(argValue);
			}
			
			// service version
			else if (argName.equalsIgnoreCase("version"))
			{
				request.setVersion(argValue);
			}

			// request argument
			else if (argName.equalsIgnoreCase("request"))
			{
				request.setOperation(argValue);
			}

			// offering argument
			else if (argName.equalsIgnoreCase("offering"))
			{
				request.setOffering(argValue);
			}
			
			// time
			else if (argName.equalsIgnoreCase("time") || argName.equalsIgnoreCase("eventTime"))
			{
				TimeExtent timeInfo = parseTimeArg(argValue);
				request.setTime(timeInfo);
			}
			
			// procedures
			else if (argName.equalsIgnoreCase("procedures"))
			{
				String[] sensorList = argValue.split(",");
				request.getProcedures().clear();					
				for (int i=0; i<sensorList.length; i++)
					request.getProcedures().add(sensorList[i]);
			}
			
			// observables
			else if (argName.equalsIgnoreCase("observables") || argName.equalsIgnoreCase("observedProperty"))
			{
				String[] obsList = argValue.split(",");
				request.getObservables().clear();					
				for (int i=0; i<obsList.length; i++)
					request.getObservables().add(obsList[i]);
			}
			
			// bbox
            else if (argName.equalsIgnoreCase("bbox"))
            {
                Bbox bbox = parseBboxArg(argValue);
                request.setBbox(bbox);
            }

			// format argument
			else if (argName.equalsIgnoreCase("format") || argName.equalsIgnoreCase("responseFormat"))
			{
				request.setFormat(argValue);
			}
            
			// responseMode argument
            else if (argName.equalsIgnoreCase("responseMode"))
            {
                parseResponseMode(argValue, request);
            }
            
			// resultModel argument
            else if (argName.equalsIgnoreCase("resultModel"))
            {
                request.setResultModel(argValue);
            }

			else
				throw new SOSException(invalidKVP + ": Unknown Argument " + argName);
		}

		this.checkParameters(request, report);
		return request;
	}
	
	
	@Override
	public GetObservationRequest readXMLQuery(DOMHelper dom, Element requestElt) throws OWSException
	{
		OWSExceptionReport report = new OWSExceptionReport(OWSException.VERSION_11);
		GetObservationRequest request = new GetObservationRequest();
		
		// do common stuffs like version, request name and service type
		readCommonXML(dom, requestElt, request);
		
		// offering
		String offering = dom.getElementValue(requestElt, "offering");
		request.setOffering(offering);
		
		// event time
		try
        {
            readTemporalOps(dom, requestElt, request);
        }
        catch (XMLReaderException e)
        {
            throw new SOSException(invalidXML + ": " + e.getMessage());
        }
        
        // procedures
		NodeList procList = dom.getElements(requestElt, "procedure");
		for (int i = 0; i < procList.getLength(); i++)
		{
			String val = dom.getElementValue((Element)procList.item(i), "");
			request.getProcedures().add(val);
		}
		
        // observables
		NodeList obsList = dom.getElements(requestElt, "observedProperty");
		for (int i = 0; i < obsList.getLength(); i++)
		{
			String val = dom.getElementValue((Element)obsList.item(i), "");
			request.getObservables().add(val);
		}
		
		// feature of interest
        try
        {
        	readFOI(dom, requestElt, request);
        }
        catch (XMLReaderException e)
        {
            throw new SOSException(invalidXML + ": " + e.getMessage());
        }
		
		// response format
		String resFormat = dom.getElementValue(requestElt, "responseFormat");
		request.setFormat(resFormat);
		
		// result model
		String resultModel = dom.getElementValue(requestElt, "resultModel");
        request.setResultModel(resultModel);
        
        // response mode
        String mode = dom.getElementValue(requestElt, "responseMode");
        parseResponseMode(mode, request);

        this.checkParameters(request, report);
        return request;
	}
    
    
    protected void parseResponseMode(String mode, GetObservationRequest query) throws SOSException
    {
        if (mode == null)
            query.setResponseMode(null);
        else if (mode.equalsIgnoreCase("inline"))
            query.setResponseMode(ResponseMode.INLINE);
        else if (mode.equalsIgnoreCase("attached"))
            query.setResponseMode(ResponseMode.ATTACHED);
        else if (mode.equalsIgnoreCase("out-of-band"))
            query.setResponseMode(ResponseMode.OUT_OF_BAND);
        else if (mode.equalsIgnoreCase("resultTemplate"))
            query.setResponseMode(ResponseMode.RESULT_TEMPLATE);
        else if (mode.equalsIgnoreCase("resultOnly"))
            query.setResponseMode(ResponseMode.RESULT_ONLY);
        else
            throw new SOSException(invalidReq + "Invalid response mode: " + mode);
    }
	
	
    /**
     * Reads the temporalOps section
     * TODO support other time operators... (currently supporting only During)
     * @param dom
     * @param requestElt
     * @param query
     * @throws GMLException
     */
	protected void readTemporalOps(DOMHelper dom, Element requestElt, GetObservationRequest query) throws SOSException, XMLReaderException
	{
		Element timeOpElt = dom.getElement(requestElt, "eventTime/*");
		if (timeOpElt == null)
			return;
		
		if (timeOpElt.getLocalName().equals("TM_During"))
        {
            Element propertyElt = dom.getElement(timeOpElt, "PropertyName");
            if (propertyElt == null)
                throw new SOSException("Invalid Time Operator: Missing PropertyName Element");
            
            NodeList childElts = dom.getElements(timeOpElt, "*");
            Element timePrimitiveElt = (Element)childElts.item(1);
            TimeExtent time = timeReader.readTimePrimitive(dom, timePrimitiveElt);
            query.setTime(time);
        }
		else
			throw new SOSException("Unsupported Time Operator: " + timeOpElt.getNodeName());
	}
    
    
	/**
	 * Reads the Feature of Interest section
	 * @param dom
	 * @param requestElt
	 * @param query
	 * @throws GMLException
	 */
	protected void readFOI(DOMHelper dom, Element requestElt, GetObservationRequest query) throws XMLReaderException
    {
		readSpatialOps(dom, requestElt, query);
    }
	
	
    /**
     * Reads the spatialOps section
     * TODO support more spatial operators... (currently supporting only BBOX)
     * @param dom
     * @param requestElt
     * @param query
     * @throws GMLException
     */
    protected void readSpatialOps(DOMHelper dom, Element requestElt, GetObservationRequest query) throws XMLReaderException
    {
        Element envelopeElt = dom.getElement(requestElt, "featureOfInterest/BBOX/Envelope");
        
        if (envelopeElt != null)
        {
            Bbox bbox = bboxReader.readEnvelope(dom, envelopeElt);
            query.setBbox(bbox);
        }
    }
    
    
    /**
     * Checks that GetObservation mandatory parameters are present
     * @param request
     * @throws OWSException
     */
    protected void checkParameters(GetObservationRequest request, OWSExceptionReport report) throws OWSException
    {
    	// check common params
		super.checkParameters(request, report, OWSUtils.SOS);
    	
    	// need offering
		if (request.getOffering() == null)
			report.add(new OWSException(OWSException.missing_param_code, "OFFERING"));
		
		// need at least BBOX or TIME
		if (request.getBbox() == null && request.getTime() == null)
			report.add(new OWSException(OWSException.missing_param_code, "FOI/TIME"));
		
		// need format
		if (request.getFormat() == null)
			report.add(new OWSException(OWSException.missing_param_code, "RESPONSE_FORMAT"));
		
		report.process();
    }
}