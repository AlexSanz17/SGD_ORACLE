
/**
 * AccessDeniedFaultException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5.1  Built on : Oct 19, 2009 (10:59:00 EDT)
 */

package org.ositran.webservice.clientes.intalio;

public class AccessDeniedFaultException extends java.lang.Exception{
    
    private org.ositran.webservice.clientes.intalio.TaskManagementServicesStub.AccessDeniedFault faultMessage;

    
        public AccessDeniedFaultException() {
            super("AccessDeniedFaultException");
        }

        public AccessDeniedFaultException(java.lang.String s) {
           super(s);
        }

        public AccessDeniedFaultException(java.lang.String s, java.lang.Throwable ex) {
          super(s, ex);
        }

        public AccessDeniedFaultException(java.lang.Throwable cause) {
            super(cause);
        }
    

    public void setFaultMessage(org.ositran.webservice.clientes.intalio.TaskManagementServicesStub.AccessDeniedFault msg){
       faultMessage = msg;
    }
    
    public org.ositran.webservice.clientes.intalio.TaskManagementServicesStub.AccessDeniedFault getFaultMessage(){
       return faultMessage;
    }
}
    