<%-- 
    Document   : principal
    Created on : 12/04/2019, 01:43:15 PM
    Author     : jbengoa
--%>

<%@page import="java.util.Date"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%String contenido ="verDocumento.png?accion=abrirDocumentoNavegador&fecha=" + new Date();%>
        <%String bandeja="verDocumento.png?accion=cargarBandejaTipoDocumentosNavegador&fecha=" + new Date();%>
         <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Navegador</title>
    </head>
    <frameset cols="32%,68%">
	<frame src="<%=bandeja%>" name="bandejaNavegador" noresize>
	<frame src="<%=contenido%>"  name="detalleNavegador" noresize>
     </frameset> 
</html>
