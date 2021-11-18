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
        <script type="text/javascript">
        
        </script>
        <%String contenido ="verDocumento.png?idArchivo=" + request.getAttribute("idArchivo") + "&url="  + request.getAttribute("url") + "&objectId=" + request.getAttribute("objectId") + "&accion=abrirDocumento&fecha=" + new Date();%>
        <%String bandeja="verDocumento.png?idArchivo=" + request.getAttribute("idArchivo") + "&url="  + request.getAttribute("url") + "&objectId=" + request.getAttribute("objectId") + "&accion=abrirBandeja&fecha=" + new Date();%>
         <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Documento</title>
        
        
    </head>
    <frameset cols="28%,72%">
	<frame src="<%=bandeja%>" name="bandeja" noresize>
	<frame src="<%=contenido%>"  name="detalle" noresize>
     </frameset> 
</html>
