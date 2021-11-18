
/**
 * TaskManagementServicesCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5.1  Built on : Oct 19, 2009 (10:59:00 EDT)
 */

    package org.ositran.webservice.clientes.intalio;

    /**
     *  TaskManagementServicesCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class TaskManagementServicesCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public TaskManagementServicesCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public TaskManagementServicesCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for deletePipa method
            * override this method for handling normal response from deletePipa operation
            */
           public void receiveResultdeletePipa(
                    org.ositran.webservice.clientes.intalio.TaskManagementServicesStub.OkResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from deletePipa operation
           */
            public void receiveErrordeletePipa(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getTask method
            * override this method for handling normal response from getTask operation
            */
           public void receiveResultgetTask(
                    org.ositran.webservice.clientes.intalio.TaskManagementServicesStub.GetTaskResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getTask operation
           */
            public void receiveErrorgetTask(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for removeAttachment method
            * override this method for handling normal response from removeAttachment operation
            */
           public void receiveResultremoveAttachment(
                    org.ositran.webservice.clientes.intalio.TaskManagementServicesStub.OkResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from removeAttachment operation
           */
            public void receiveErrorremoveAttachment(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getAvailableTasks method
            * override this method for handling normal response from getAvailableTasks operation
            */
           public void receiveResultgetAvailableTasks(
                    org.ositran.webservice.clientes.intalio.TaskManagementServicesStub.GetAvailableTasksResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getAvailableTasks operation
           */
            public void receiveErrorgetAvailableTasks(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for create method
            * override this method for handling normal response from create operation
            */
           public void receiveResultcreate(
                    org.ositran.webservice.clientes.intalio.TaskManagementServicesStub.OkResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from create operation
           */
            public void receiveErrorcreate(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for initProcess method
            * override this method for handling normal response from initProcess operation
            */
           public void receiveResultinitProcess(
                    org.ositran.webservice.clientes.intalio.TaskManagementServicesStub.OkResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from initProcess operation
           */
            public void receiveErrorinitProcess(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for setOutput method
            * override this method for handling normal response from setOutput operation
            */
           public void receiveResultsetOutput(
                    org.ositran.webservice.clientes.intalio.TaskManagementServicesStub.OkResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from setOutput operation
           */
            public void receiveErrorsetOutput(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for reassign method
            * override this method for handling normal response from reassign operation
            */
           public void receiveResultreassign(
                    org.ositran.webservice.clientes.intalio.TaskManagementServicesStub.OkResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from reassign operation
           */
            public void receiveErrorreassign(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for complete method
            * override this method for handling normal response from complete operation
            */
           public void receiveResultcomplete(
                    org.ositran.webservice.clientes.intalio.TaskManagementServicesStub.OkResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from complete operation
           */
            public void receiveErrorcomplete(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for setOutputAndComplete method
            * override this method for handling normal response from setOutputAndComplete operation
            */
           public void receiveResultsetOutputAndComplete(
                    org.ositran.webservice.clientes.intalio.TaskManagementServicesStub.OkResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from setOutputAndComplete operation
           */
            public void receiveErrorsetOutputAndComplete(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getAttachments method
            * override this method for handling normal response from getAttachments operation
            */
           public void receiveResultgetAttachments(
                    org.ositran.webservice.clientes.intalio.TaskManagementServicesStub.GetAttachmentsResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getAttachments operation
           */
            public void receiveErrorgetAttachments(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getPipa method
            * override this method for handling normal response from getPipa operation
            */
           public void receiveResultgetPipa(
                    org.ositran.webservice.clientes.intalio.TaskManagementServicesStub.GetPipaResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getPipa operation
           */
            public void receiveErrorgetPipa(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for deleteAll method
            * override this method for handling normal response from deleteAll operation
            */
           public void receiveResultdeleteAll(
                    org.ositran.webservice.clientes.intalio.TaskManagementServicesStub.OkResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from deleteAll operation
           */
            public void receiveErrordeleteAll(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for addAttachment method
            * override this method for handling normal response from addAttachment operation
            */
           public void receiveResultaddAttachment(
                    org.ositran.webservice.clientes.intalio.TaskManagementServicesStub.OkResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addAttachment operation
           */
            public void receiveErroraddAttachment(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for storePipa method
            * override this method for handling normal response from storePipa operation
            */
           public void receiveResultstorePipa(
                    org.ositran.webservice.clientes.intalio.TaskManagementServicesStub.OkResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from storePipa operation
           */
            public void receiveErrorstorePipa(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getTaskList method
            * override this method for handling normal response from getTaskList operation
            */
           public void receiveResultgetTaskList(
                    org.ositran.webservice.clientes.intalio.TaskManagementServicesStub.GetTaskListResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getTaskList operation
           */
            public void receiveErrorgetTaskList(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for delete method
            * override this method for handling normal response from delete operation
            */
           public void receiveResultdelete(
                    org.ositran.webservice.clientes.intalio.TaskManagementServicesStub.OkResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from delete operation
           */
            public void receiveErrordelete(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for fail method
            * override this method for handling normal response from fail operation
            */
           public void receiveResultfail(
                    org.ositran.webservice.clientes.intalio.TaskManagementServicesStub.OkResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from fail operation
           */
            public void receiveErrorfail(java.lang.Exception e) {
            }
                


    }
    