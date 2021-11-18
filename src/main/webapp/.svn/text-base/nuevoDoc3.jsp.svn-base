<%--
    Document   : plantilla.jsp
    Created on : 09/12/2008, 11:23:17 AM
    Author     : Himizu
--%>
<?xml version="1.0" encoding="UTF-8" ?>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://ajaxtags.org/tags/ajax" prefix="ajax"%>
<%@page import="java.util.Enumeration"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es">
	<head>
		<title>Vista previa del documento generado por Plantilla</title>
		<link rel="stylesheet" type="text/css" href="css/estilo.css" />
	</head>
	<body>
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
		<table border="0" width="100%" >
			<tr> 
				<td height="600px">  
					<iframe frameborder="1" width="100%" height="100%" name="prueba2" src="<s:url action="doPlantilla_verDocumentoPrevio"/><%=parameters%>"></iframe> 
				</td>  
			</tr>
			<tr>
				<td align="center" height="10%">  
					<input  value="Aceptar"  type="button" onclick="window.opener.enviarArchivo();window.close();" /> 
					<input  value="Cancelar"  type="button" onclick=" window.close();" />
				</td>
			</tr>
		</table>  
	</body>
</html>