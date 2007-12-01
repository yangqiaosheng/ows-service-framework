/***************************** BEGIN LICENSE BLOCK ***************************

 The contents of this file are subject to the Mozilla Public License Version
 1.1 (the "License"); you may not use this file except in compliance with
 the License. You may obtain a copy of the License at
 http://www.mozilla.org/MPL/MPL-1.1.html
 
 Software distributed under the License is distributed on an "AS IS" basis,
 WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 for the specific language governing rights and limitations under the License.
 
 The Original Code is the "OGC Service Framework".
 
 The Initial Developer of the Original Code is the VAST team at the
 University of Alabama in Huntsville (UAH). <http://vast.uah.edu>
 Portions created by the Initial Developer are Copyright (C) 2007
 the Initial Developer. All Rights Reserved.

 Please Contact Mike Botts <mike.botts@uah.edu> for more information.
 
 Contributor(s): 
    Johannes Echterhoff <echterhoff@uni-muenster.de>
 
******************************* END LICENSE BLOCK ***************************/

package org.vast.ows.sps;

/**
 * Serves as a wrapper for all exception that are catched and have to be
 * reported as the operation where the exception occurred can not be finished.
 * 
 * @author Johannes Echterhoff
 */
public class SPSException extends Exception {

   private static final long serialVersionUID = 8834638750689652806L;

   public SPSException() {

      super();
   }

   public SPSException(String message) {

      super(message);
   }

   public SPSException(String message, Throwable cause) {

      super(message, cause);
   }

   public SPSException(Throwable cause) {

      super(cause);
   }

}
