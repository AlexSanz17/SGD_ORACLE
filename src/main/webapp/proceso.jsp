<?xml version="1.0" encoding="UTF-8" ?>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>Proceso Intalio</title>
		<script type="text/javascript" src="js/procesoIntalio.js"></script>
	</head>
	<body>
		<div style="display: none;">
			<input type="hidden" id="idDocumento2" value="<%=request.getParameter("idDocumento")%>" />
			<input type="hidden" id="idExpediente2" value="<%=request.getParameter("idExpediente")%>" />
			<input type="hidden" id="idProceso2" value="<%=request.getParameter("idProceso")%>" />
			<input type="hidden" id="rol2" value="<%=request.getParameter("rol")%>" />
			<input type="hidden" id="tipo2" value="<%=request.getParameter("tipo")%>" />
		</div>
		<div id="toolbar2">
			<s:if test="#session._RECURSO.UsuFinBtnTraExp">
			<button id="trazabilidad2">Trazabilidad</button>
			</s:if>
			<s:if test="#session._RECURSO.UsuFinBtnNueDocSec">
			<button id="plantilla2">Plantilla</button>
			</s:if>
			<s:if test="#session._RECURSO.UsuFinBtnUpl">
			<button id="adjuntar2">Adjuntar</button>
			</s:if>
			<s:if test="#session._RECURSO.UsuFinBtnUpl">
			<button id="versionar2">Versionar</button>
			</s:if>
			<s:if test="#session._RECURSO.UsuFinBtnUpl">
			<button id="enumerar2">Enumerar</button>
			</s:if>
			<div id="otro2">
				<div id="menuOtro2">
					<s:if test="#session._RECURSO.UsuFinBtnArc">
					<div id="archivar2">Archivar</div>
					</s:if>
					<s:if test="#session._RECURSO.UsuFinBtnDocVir">
					<div id="otros2">Otros Expedientes</div>
					</s:if>
					<div id="ayuda2">Ayuda en L&iacute;nea</div>
				</div>
			</div>
		</div>
		<iframe src="<%=request.getParameter("data") %>&token=<%=request.getParameter("token") %>&url=<%=request.getParameter("url") %>&user=<%=request.getParameter("user") %>" style="width: 100%;height: 100%;border: 0;" ></iframe>
	</body>
</html>