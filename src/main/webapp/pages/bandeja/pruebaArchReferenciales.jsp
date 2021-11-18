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
         var opcionMenu = "<s:property value='objDD.opcionMenu' />";
         
      </script>
		<%--<script type="text/javascript" src="js/jquery.js"></script>--%>
		<script type="text/javascript" src="js/documentosReferenciales.js"></script>
      
	</head>
	<body class="soria">

		<div id="divTreeDocumento<s:property value='idGrid' />" class="arbol"></div>
		<div id="displayIdDocumento<s:property value='idGrid' />" class="detalleArbol"></div>
		
	</body>
</html>