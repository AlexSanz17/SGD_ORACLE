<?xml version="1.0" encoding="UTF-8" ?>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@page import="java.util.Enumeration"%> 
<%	String parameters ="?";
	Enumeration names = request.getParameterNames();
	while (names.hasMoreElements()){    
		String name=(String)names.nextElement();
		parameters+=name+"="+request.getParameter(name); 
		if(names.hasMoreElements()){
			parameters+="&";
		}
	}%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es">
	<head>
		<title>Vista previa del documento generado</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" type="text/css" href="css/estilo.css" />
	</head>
	<body>
		<table border="0"  width="100%"  >
			<tr> 
				<td height="600px">  
  					<iframe frameborder="1" width="100%" height="100%" name="prueba2" src="<s:url action="doPlantilla_verDocumentoPrevioN"/><%=parameters%>"></iframe> 
				</td>
         
			</tr>
			<tr>
				<td align="center" height="10%"> 
					<input  value="Aceptar"  type="button" onclick="window.opener.enviarArchivo();window.close()" />
					<input  value="Cancelar"  type="button" onclick="window.opener.cancelarVistaPrevia();window.close()" />
				</td>
			</tr>
		</table>  
	</body>
</html>