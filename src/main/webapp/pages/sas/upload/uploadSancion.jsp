<?xml version="1.0" encoding="UTF-8" ?>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"></meta>
	<script type="text/javascript" src="pages/sas/regInformeTecnico.js"></script>
    <script type="text/javascript" src="js/siged/upload.js"></script>
    
    <script type="text/javascript">
		function cerrar(){
	       var dlgMensaje=dijit.byId("dlgMensaje");
	       dlgMensaje.hide();	    
		}
    </script>
    
</head>
<body>
<table width="100%" style="height:10px;">
	<tr>
         <td><button id="upload2">Inf.Tecnico</button></td>
         <td><button id="upload3">Memo</button></td>
	</tr>
	<tr>
		<td colspan="2" align="center"><input type="button" value="cerrar" onclick="cerrar()"/></td>
	</tr>
	<tr>
		<td colspan="2"><br/></td>
	</tr>
</table>
</body>
</html>
