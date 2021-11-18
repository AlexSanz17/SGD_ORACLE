<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@page import="java.util.Enumeration"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
   <head>
      <title>Sistema de Gesti&oacute;n Documentaria</title>
      <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
   </head>
   <%
      String parameters ="?";
      Enumeration names = request.getParameterNames();
       while (names.hasMoreElements()){
    	   String name = (String)names.nextElement();
    	   parameters+= name+"="+request.getParameter(name);
    	   if(names.hasMoreElements()){
    		   parameters+="&";
    	   }
         System.out.println("parameters: "+parameters);
       }

      %>
   <frameset rows="*" cols="190,*" id="centro">
      <frame src="<s:url action="doMenu" />" name="leftFrame" scrolling="no" frameborder="no" noresize />
      <frameset rows="200,*" cols="*">
         <frame src="<s:url action="doBody" />" name="mainFrame" />  
         <frame src="<s:url action="doViewDoc" /><%=parameters%>" name="secondFrame"/>
      </frameset>
   </frameset>

   <noframes></noframes>
</html>

