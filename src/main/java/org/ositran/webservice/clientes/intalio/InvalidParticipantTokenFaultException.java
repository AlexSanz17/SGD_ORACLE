
/**
 * InvalidParticipantTokenFaultException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5.1  Built on : Oct 19, 2009 (10:59:00 EDT)
 */

package org.ositran.webservice.clientes.intalio;

public class InvalidParticipantTokenFaultException extends java.lang.Exception{
    
    private org.ositran.webservice.clientes.intalio.TaskManagementServicesStub.InvalidParticipantTokenFault faultMessage;

    
        public InvalidParticipantTokenFaultException() {
            super("InvalidParticipantTokenFaultException");
        }

        public InvalidParticipantTokenFaultException(java.lang.String s) {
           super(s);
        }

        public InvalidParticipantTokenFaultException(java.lang.String s, java.lang.Throwable ex) {
          super(s, ex);
        }

        public InvalidParticipantTokenFaultException(java.lang.Throwable cause) {
            super(cause);
        }
    

    public void setFaultMessage(org.ositran.webservice.clientes.intalio.TaskManagementServicesStub.InvalidParticipantTokenFault msg){
       faultMessage = msg;
    }
    
    public org.ositran.webservice.clientes.intalio.TaskManagementServicesStub.InvalidParticipantTokenFault getFaultMessage(){
       return faultMessage;
    }
}
    