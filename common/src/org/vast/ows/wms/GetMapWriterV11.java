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

package org.vast.ows.wms;

import org.vast.ows.AbstractRequestWriter;
import org.vast.ows.OWSException;
import org.vast.xml.DOMHelper;
import org.w3c.dom.Element;


/**
 * <p><b>Title:</b><br/>
 * GetMap Request Builder v1.1
 * </p>
 *
 * <p><b>Description:</b><br/>
 * Provides methods to generate a KVP GetMap request based
 * on values contained in a GetMapRequest object for version 1.1
 * </p>
 *
 * <p>Copyright (c) 2007</p>
 * @author Alexandre Robin
 * @date Oct 10, 2007
 * @version 1.0
 */
public class GetMapWriterV11 extends AbstractRequestWriter<GetMapRequest>
{
    
	public GetMapWriterV11()
	{	
	}

	
	@Override
	public String buildURLQuery(GetMapRequest request) throws OWSException
	{
		StringBuffer urlBuff = new StringBuffer(request.getGetServer());
		
        urlBuff.append("service=WMS");
        urlBuff.append("&version=" + request.getVersion());
        urlBuff.append("&request=GetMap");
		
        urlBuff.append("&layers=" + createLayerList(request));
        
        urlBuff.append("&styles=" + createStyleList(request));
        
		urlBuff.append("&srs=" + request.getSrs());
        
		urlBuff.append("&bbox=");
        this.writeBboxArgument(urlBuff, request.getBbox());
        
		urlBuff.append("&width=" + request.getWidth());
        
		urlBuff.append("&height=" + request.getHeight());
        
		urlBuff.append("&format=" + request.getFormat());
        
		urlBuff.append("&transparent=" + (request.isTransparent() ? "TRUE" : "FALSE"));
        
		urlBuff.append("&exceptions=" + request.getExceptionType());
				
		String url = urlBuff.toString();
		url.replaceAll(" ","%20");
		return url;
	}


    @Override
    public Element buildXMLQuery(DOMHelper dom, GetMapRequest request) throws OWSException
    {
        dom.addUserPrefix("ows", "http://www.opengis.net/ows");
        dom.addUserPrefix("ogc", "http://www.opengis.net/ogc");
        dom.addUserPrefix("gml", "http://www.opengis.net/gml");
        dom.addUserPrefix("sld", "http://www.opengis.net/sld");
        
        // root element
        Element rootElt = dom.createElement("ows:GetMap");
        dom.setAttributeValue(rootElt, "version", request.getVersion());
        dom.setAttributeValue(rootElt, "service", request.getService());
        
        // layers and styles
        for (int i=0; i<request.getLayers().size(); i++)
        {
            Element layerdElt = dom.addElement(rootElt, "sld:StyledLayerDescriptor/+sld:NamedLayer");
            dom.setElementValue(layerdElt, "sld:Name", request.getLayers().get(i));
            if (!request.getStyles().isEmpty())
                dom.setElementValue(layerdElt, "sld:NamedStyle/sld:Name", request.getStyles().get(i));
        }
        
        // bounding box
        dom.setElementValue(rootElt, "ows:BoundingBox/gml:coordinates", getGmlBboxCoordsList(request.getBbox()));
        dom.setAttributeValue(rootElt, "ows:BoundingBox/@srsName", request.getSrs());
        
        // output parameters
        dom.setElementValue(rootElt, "ows:Output/ows:Format", request.getFormat());
        dom.setElementValue(rootElt, "ows:Output/ows:Transparent", request.isTransparent() ? "TRUE" : "FALSE");
        dom.setElementValue(rootElt, "ows:Output/ows:Size/ows:Width", Integer.toString(request.getWidth()));
        dom.setElementValue(rootElt, "ows:Output/ows:Size/ows:Height", Integer.toString(request.getHeight()));
        dom.setElementValue(rootElt, "ows:Exceptions", request.getExceptionType());
        
        return rootElt;
    }
    
    
    /**
     * Create comma separated layer list for GET request
     * @param request
     * @return
     */
    protected String createLayerList(GetMapRequest request)
    {
        StringBuffer buff = new StringBuffer();
        
        int layerCount = request.getLayers().size();
        for (int i=0; i<layerCount; i++)
        {
            buff.append(request.getLayers().get(i));
            
            if (i != layerCount-1)
                buff.append(',');               
        }
        
        return buff.toString();
    }
    
    
    /**
     * Create comma separated style list for GET request
     * @param request
     * @return
     */
    protected String createStyleList(GetMapRequest request)
    {
        StringBuffer buff = new StringBuffer();
        
        int styleCount = request.getStyles().size();
        for (int i=0; i<styleCount; i++)
        {
            buff.append(request.getStyles().get(i));
            
            if (i != styleCount-1)
                buff.append(',');               
        }
        
        return buff.toString();
    }
}