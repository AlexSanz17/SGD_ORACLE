
/**
 * UnavailableAttachmentFaultException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5.1  Built on : Oct 19, 2009 (10:59:00 EDT)
 */

package org.ositran.webservice.clientes.intalio;

public class UnavailableAttachmentFaultException extends java.lang.Exception{
    
    private org.ositran.webservice.clientes.intalio.TaskManagementServicesStub.UnavailableAttachmentFault faultMessage;

    
        public UnavailableAttachmentFaultException() {
            super("UnavailableAttachmentFaultException");
        }

        public UnavailableAttachmentFaultException(java.lang.String s) {
           super(s);
        }

        public UnavailableAttachmentFaultException(java.lang.String s, java.lang.Throwable ex) {
          super(s, ex);
        }

        public UnavailableAttachmentFaultException(java.lang.Throwable cause) {
            super(cause);
        }
    

    public void setFaultMessage(org.ositran.webservice.clientes.intalio.TaskManagementServicesStub.UnavailableAttachmentFault msg){
       faultMessage = msg;
    }
    
    public org.ositran.webservice.clientes.intalio.TaskManagementServicesStub.UnavailableAttachmentFault getFaultMessage(){
       return faultMessage;
    }
}
    