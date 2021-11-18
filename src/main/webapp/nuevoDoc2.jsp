<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
   <head>
      <title>Sistema de Gesti&oacute;n Documentaria</title>
      <meta http-equiv="cache-control" content="no-cache">
      <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
   </head>
   <frameset rows="*" cols="190,*" id="centro">
      <frame src="<s:url action="doMenu" />" name="leftFrame" scrolling="no" frameborder="no" noresize />
      <frameset rows="*" cols="*">
         <frame src="<s:url action="doPlantilla_verNuevo" />" />  
      </frameset>
   </frameset>
   <noframes></noframes>
</html>