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
 Alexandre Robin <robin@nsstc.uah.edu>
 
 ******************************* END LICENSE BLOCK ***************************/

package org.vast.ows;

import java.util.Map;
import org.vast.xml.DOMHelper;
import org.w3c.dom.Element;


/**
 * <p>
 * Base interface for all OWS request readers
 * </p>
 *
 * <p>Copyright (c) 2007</p>
 * @author Alexandre Robin
 * @since Jan 16, 2007
 * @version 1.0
 */
public interface OWSRequestReader<QueryType extends OWSRequest>
{

    /**
     * Reads URL request arguments from the given query string 
     * @param queryString
     * @return
     * @throws OWSException
     */
    public QueryType readURLQuery(String queryString) throws OWSException;
    
    
    /**
     * Decodes URL request parameters from the given map 
     * @param queryParameters map containing a key value pair for each query parameter
     * @return
     * @throws OWSException
     */
    public QueryType readURLParameters(Map<String, String> queryParameters) throws OWSException;


    /**
     * Reads XML request parameters from the given element and using the given DOMHelper.
     * @param domHelper
     * @param requestElt
     * @return
     * @throws OWSException
     */
    public QueryType readXMLQuery(DOMHelper domHelper, Element requestElt) throws OWSException;

}