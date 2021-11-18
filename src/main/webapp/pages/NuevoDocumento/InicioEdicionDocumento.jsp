<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.Enumeration"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Sistema de Gesti&oacute;n Documentaria</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="cache-control" content="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="-1">
<%
      String parameters ="?";
      Enumeration names = request.getParameterNames();
       while (names.hasMoreElements()){
    	   String name = (String)names.nextElement();
    	   parameters+= name+"="+request.getParameter(name);
    	   if(names.hasMoreElements()){
    		   parameters+="&";
    	   }
       }

      %>
</head>
<frameset rows="*" cols="190,*" id="centro">
	<frame src="<s:url action="doMenu" />" name="leftFrame" scrolling="no"
		frameborder="no" noresize />
	<frameset rows="*" cols="*">
		<frame
			src="<s:url action="NuevoDocumento_mostrarVistaEdicion" /><%=parameters%>" />
	</frameset>
</frameset>
<noframes></noframes>
</html>