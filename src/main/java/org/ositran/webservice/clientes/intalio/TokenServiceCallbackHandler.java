
/**
 * TokenServiceCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5.1  Built on : Oct 19, 2009 (10:59:00 EDT)
 */

    package org.ositran.webservice.clientes.intalio;

    /**
     *  TokenServiceCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class TokenServiceCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public TokenServiceCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public TokenServiceCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for authenticateUser method
            * override this method for handling normal response from authenticateUser operation
            */
           public void receiveResultauthenticateUser(
                    org.ositran.webservice.clientes.intalio.TokenServiceStub.AuthenticateUserResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from authenticateUser operation
           */
            public void receiveErrorauthenticateUser(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for authenticateUserWithCredentials method
            * override this method for handling normal response from authenticateUserWithCredentials operation
            */
           public void receiveResultauthenticateUserWithCredentials(
                    org.ositran.webservice.clientes.intalio.TokenServiceStub.AuthenticateUserResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from authenticateUserWithCredentials operation
           */
            public void receiveErrorauthenticateUserWithCredentials(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getTokenProperties method
            * override this method for handling normal response from getTokenProperties operation
            */
           public void receiveResultgetTokenProperties(
                    org.ositran.webservice.clientes.intalio.TokenServiceStub.GetTokenPropertiesResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getTokenProperties operation
           */
            public void receiveErrorgetTokenProperties(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getTokenFromTicket method
            * override this method for handling normal response from getTokenFromTicket operation
            */
           public void receiveResultgetTokenFromTicket(
                    org.ositran.webservice.clientes.intalio.TokenServiceStub.AuthenticateUserResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getTokenFromTicket operation
           */
            public void receiveErrorgetTokenFromTicket(java.lang.Exception e) {
            }
                


    }
    