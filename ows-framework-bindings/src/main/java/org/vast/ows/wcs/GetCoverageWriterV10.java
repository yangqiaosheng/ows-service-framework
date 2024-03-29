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

import java.text.NumberFormat;
import org.vast.ogc.OGCRegistry;
import org.vast.ogc.gml.GMLEnvelopeWriter;
import org.vast.ogc.gml.GMLUtils;
import org.vast.ows.*;
import org.w3c.dom.*;
import org.vast.util.DateTimeFormat;
import org.vast.util.Interval;
import org.vast.util.TimeExtent;
import org.vast.xml.DOMHelper;
import org.vast.xml.QName;


/**
 * <p><b>Title:</b><br/>
 * GetCoverage Request Builder v1.0
 * </p>
 *
 * <p><b>Description:</b><br/>
 * Provides methods to generate a KVP or XML GetCoverage request based
 * on values contained in a GetCoverageRequest object for version 1.0
 * </p>
 *
 * <p>Copyright (c) 2007</p>
 * @author Alexandre Robin
 * @date Sep 21, 2007
 * @version 1.0
 */
public class GetCoverageWriterV10 extends AbstractRequestWriter<GetCoverageRequest>
{
	protected GMLEnvelopeWriter envelopeWriter = new GMLEnvelopeWriter("3.1");
	
	
	@Override
	public String buildURLQuery(GetCoverageRequest request) throws OWSException
	{
	    StringBuilder urlBuff = new StringBuilder(request.getGetServer());
        addCommonArgs(urlBuff, request);
        
        // COVERAGE
        urlBuff.append("&COVERAGE=" + request.getCoverage());
        
        // TIME
        if (request.getTime() != null)
        {
            urlBuff.append("&TIME=");
            this.writeTimeArgument(urlBuff, request.getTime());
        }
        
        // BBOX and CRS if specified
        if (request.getBbox() != null)
        {
        	urlBuff.append("&CRS=" + request.getBbox().getCrs());
        	urlBuff.append("&BBOX=");
	        this.writeBboxArgument(urlBuff, request.getBbox());
	        
	        // add either RESX/RESY or WIDTH/HEIGHT
	        if (request.isUseResolution())
	        {	        
	        	urlBuff.append("&RESX=" + request.getResX());
	        	urlBuff.append("&RESY=" + request.getResY());
	        	if (request.getResZ() > 0)
	        		urlBuff.append("&RESZ=" + request.getResZ());
	        }
	        else
	        {
	        	urlBuff.append("&WIDTH=" + request.getWidth());
	        	urlBuff.append("&HEIGHT=" + request.getHeight());
	        	if (request.getDepth() > 0)
	        		urlBuff.append("&DEPTH=" + request.getDepth());
	        }
        }
        
        // {PARAMETERS}
        if (!request.getFieldSubsets().isEmpty())
        {
	        FieldSubset field = request.getFieldSubsets().get(0);
	        for (int i=0; i<field.getAxisSubsets().size(); i++)
	        {
		        AxisSubset param = field.getAxisSubsets().get(i);
		        urlBuff.append("&" + param.getIdentifier().toUpperCase() + "=");
		        
	        	if (!param.getRangeIntervals().isEmpty()) // case of interval
		        {
	        		// only one interval is supportted in KVP
	        		Interval interval = param.getRangeIntervals().get(0);
	        		urlBuff.append(interval.getMin());
		        	urlBuff.append("/" + interval.getMax());
		        	if (interval.getResolution() > 0)
		        		urlBuff.append("/" + interval.getResolution());
		        }
		        else if (!param.getKeys().isEmpty())
		        {
		        	int listSize = param.getKeys().size();
		        	for (int v=0; v<listSize; v++)
		        	{
		        		urlBuff.append(param.getKeys().get(v));
		        		if (v < listSize-1)
		        			urlBuff.append(",");
		        	}
		        }
	        }
        }
        
        // RESPONSE_CRS
        if (request.getGridCrs() != null)
        	urlBuff.append("&RESPONSE_CRS=" + request.getGridCrs().getBaseCrs());
        
        // FORMAT
        urlBuff.append("&FORMAT=" + request.getFormat());
        
        // EXCEPTIONS
        if (request.getExceptionType() != null)
        	urlBuff.append("&EXCEPTIONS=" + request.getExceptionType());
        
        // vendor parameters
        writeKVPExtensions(urlBuff, request);
        
        // replace spaces
        String url = urlBuff.toString();
        url = url.replaceAll(" ","%20");
		return url;
	}
	
	
	@Override
	public Element buildXMLQuery(DOMHelper dom, GetCoverageRequest request) throws OWSException
	{
		dom.addUserPrefix(QName.DEFAULT_PREFIX, OGCRegistry.getNamespaceURI(OWSUtils.WCS));
		dom.addUserPrefix("gml", OGCRegistry.getNamespaceURI(GMLUtils.GML));
		
		// root element
		Element rootElt = dom.createElement("GetCoverage");
		addCommonXML(dom, rootElt, request);
		
		// source coverage
		dom.setElementValue(rootElt, "sourceCoverage", request.getCoverage());
		
		////// domain subset //////
		Element domainElt = dom.addElement(rootElt, "domainSubset");
		
		// spatial subset
		if (request.getBbox() != null)
		{
			Element spatialElement = dom.addElement(domainElt, "spatialSubset");
			
			// envelope
			Element envelopeElt = envelopeWriter.writeEnvelope(dom, request.getBbox());
			spatialElement.appendChild(envelopeElt);
			
			// grid definition
			if (!request.isUseResolution()) // grid width/height
			{
				Element gridElt = dom.addElement(spatialElement, "gml:Grid");
				dom.setAttributeValue(gridElt, "@dimension", "2");				
				
				Element gridEnvElt = dom.addElement(gridElt, "gml:limits/gml:GridEnvelope");
				dom.setElementValue(gridEnvElt, "gml:low", "0 0");
				dom.setElementValue(gridEnvElt, "gml:high", request.getWidth() + " " + request.getHeight());
				
				dom.setElementValue(gridElt, "+gml:axisName", "u");
				dom.setElementValue(gridElt, "+gml:axisName", "v");
			}		
			else // grid resolution
			{
				
			}
		}
		
		// temporal subset
		if (request.getTime() != null)
		{
			Element temporalElt = dom.addElement(domainElt, "temporalSubset");
			
			for (int i=0; i<request.getTimes().size(); i++)
			{
				TimeExtent timeInfo = request.getTimes().get(i);
				if (timeInfo.isTimeInstant())
				{
					String time = DateTimeFormat.formatIso(timeInfo.getBaseTime(), timeInfo.getTimeZone());
					dom.setElementValue(temporalElt, "+gml:timePosition", time);
				}
				else
				{
					Element periodElt = dom.addElement(temporalElt, "+timePeriod");
					String begin = DateTimeFormat.formatIso(timeInfo.getStartTime(), timeInfo.getTimeZone());
					String end = DateTimeFormat.formatIso(timeInfo.getStopTime(), timeInfo.getTimeZone());
					dom.setElementValue(periodElt, "beginTime", begin);
					dom.setElementValue(periodElt, "endTime", end);
					
					if (timeInfo.getTimeStep() != 0)
					{
						NumberFormat.getNumberInstance().setMaximumFractionDigits(2);
						String step = NumberFormat.getNumberInstance().format(timeInfo.getTimeStep());
						dom.setElementValue(periodElt, "timeResolution", step);
					}
				}
			}
		}
		
		////// range subset //////
		FieldSubset field = request.getFieldSubsets().get(0);
		for (int i=0; i<request.getFieldSubsets().size(); i++)
        {
	        AxisSubset param = field.getAxisSubsets().get(i);
			Element axisElt = dom.addElement(rootElt, "rangeSubset/+axisSubset");
			dom.setAttributeValue(axisElt, "@name", param.getIdentifier());
			
			// add all intervals
			for (int j=0; j<param.getRangeIntervals().size(); j++)
			{
				Interval interval = param.getRangeIntervals().get(j);
				Element intervalElt = dom.addElement(axisElt, "+interval");
				dom.setElementValue(intervalElt, "min", Double.toString(interval.getMin()));
				dom.setElementValue(intervalElt, "max", Double.toString(interval.getMax()));
				if (interval.getResolution() > 0)
					dom.setElementValue(intervalElt, "res", Double.toString(interval.getResolution()));
			}
			
			// add all single values
			for (int j=0; j<param.getKeys().size(); j++)
				dom.setElementValue(axisElt, "+singleValue", param.getKeys().get(j));
		}
				
		////// interpolation method //////
		if (field.getInterpolationMethod() != null)
			dom.setElementValue(rootElt, "interpolationMethod", field.getInterpolationMethod());
		
		////// output parameters //////
		Element outputElt = dom.addElement(rootElt, "output");
		
		if (request.getGridCrs() != null)
			dom.setElementValue(outputElt, "crs", request.getGridCrs().getBaseCrs());
		
		dom.setElementValue(outputElt, "format", request.getFormat());
		
		return rootElt;
	}
}