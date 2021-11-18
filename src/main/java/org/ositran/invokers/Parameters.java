/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.invokers;

import gob.ositran.siged.config.SigedProperties;

public class Parameters {
   //Url Web Services Intalio

   static String sHost = SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.INTALIO_HOST);

   public static String urlTokenService = "http://"+sHost+":8080/axis2/services/TokenService";
   public static String urlcompleteTask = "http://"+sHost+":8080/ode/processes/completeTask";
   public static String urlTaskManagementService = "http://"+sHost+":8080/axis2/services/TaskManagementServices";
   //Web Service Intalio
   public static String tokenService = "TokenService";
   public static String completeTask = "completeTask";
   public static String taskManagementService = "TaskManagementServices";
   //Namespace intalio
   public static String nsToken = "http://tempo.intalio.org/security/tokenService/";
   public static String nsGetTaskListResponse = "http://www.intalio.com/BPMS/Workflow/TaskManagementServices-20051109/";
   public static String nsGetTaskResponse = "http://www.intalio.com/BPMS/Workflow/TaskManagementServices-20051109/";
}
