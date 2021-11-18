<?xml version="1.0" encoding="UTF-8" ?>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es">
	<head>
		<title>Documentos</title>
		
		
      <script type="text/javascript">
         var myidGrid = "<s:property value='idGrid' />";
         var myidExpediente = "<s:property value='objDD.iIdExpediente' />";
         var mydocumentoStor = "<s:property value='documentoStor' />";
         var myidDocumento = "<s:property value='objDD.iIdDocumento' />";
         var origen = '<s:property value="origenDetalleDoc"/>';
         
      </script>
		<%--<script type="text/javascript" src="js/jquery.js"></script>--%>
		<script type="text/javascript" src="js/documentosArchivos.js"></script>
      
	</head>
	<body class="soria">

		<div id="divTreeArchivo<s:property value='idGrid' />" class="arbolArchivo"></div>
		<div id="displayIdArchivo<s:property value='idGrid' />" class="detalleArbolArchivo"></div>
	<!--	<div id="divTreeDocumentos:property value='idGrid' />" class="arbol"></div>
		<div id="displayIdDocumentos:property value='idGrid' />" class="detalleArbol"></div> -->
		<%--<div id="ocultos">
			<s:hidden name="idGrid" id="idGrid"/>
			<s:hidden name="objDD.iIdExpediente" id="idExpediente" />
			<s:hidden name="documentoStor" id="documentoStor" />
			<s:hidden name="objDD.iIdDocumento" id="idDocumento" />
		</div>--%>
		
	</body>
</html>