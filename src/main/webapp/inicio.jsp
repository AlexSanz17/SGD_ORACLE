<?xml version="1.0" encoding="UTF-8"?>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es">
	<head>
		<title>Sistema de Gesti&oacute;n Documentaria</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link type="image/x-icon" href="favicon.ico" rel="shortcut icon"/>
	</head>
	<frameset cols="190,*" id="centro">
		<frame src="<s:url action="doMenu" />" name="leftFrame" scrolling="no" frameborder="0" noresize="noresize" />
		<s:if test="sTipoFrame.equals('grid')">
		<frameset rows="300,*" cols="*" >
			<frame src="<s:url action="doBody" />" name="mainFrame" scrolling="no" />
			<frame src="secondFrame.jsp" name="secondFrame"/>
		</frameset>
		</s:if>
		<s:else>
		<frameset rows="*" cols="*">
			<s:if test="sTipoFrame.equals('nuevodoc')">
				<frame name="mainFrame" src="<s:url action="go_tramite_nuevoDocumento" />" />
			</s:if>
			<s:elseif test="sTipoFrame.equals('nuevoexp')">
				<frame name="mainFrame" src="<s:url action="gobandejanuevoExpediente"><s:param name="sFromBandeja">si</s:param></s:url>" />
			</s:elseif>
			<s:elseif test="sTipoFrame.equals('jerarquia')">
				<frame name="mainFrame" src="<s:url action="go_mantenimiento_jerarquia" />" />
			</s:elseif>
			<s:elseif test="sTipoFrame.equals('auditoria')">
				<frame name="mainFrame" src="<s:url action="go_mantenimiento_Auditoria" />" />
			</s:elseif>
			<s:elseif test="sTipoFrame.equals('reporte')">
				<frame name="mainFrame" src="<s:url action="go_mantenimiento_reporte" />" />
			</s:elseif>
			<s:elseif test="sTipoFrame.equals('nuevacarpeta')">
				<frame name="mainFrame" src="<s:url action="go_bandeja_nuevaCarpeta" />" />
			</s:elseif>
			<s:elseif test="sTipoFrame.equals('asignarReemplazo')">
				<frame name="mainFrame" src="<s:url action="go_asignar_reemplazo" />" />
			</s:elseif>
			<s:elseif test="sTipoFrame.equals('tramitebandejaNotificaciones')">
				<frame name="mainFrame" src="<s:url action="go_tramite_bandejaNotificaciones" />" />
			</s:elseif>
			<s:elseif test="sTipoFrame.equals('feriado_ingreso')">
				<frame name="mainFrame" src="<s:url action="go_ingreso_fecha_feriado" />" />
			</s:elseif>
			<s:elseif test="sTipoFrame.equals('feriado_listado')">
				<frame name="mainFrame" src="<s:url action="go_ingreso_fecha_reporte" />" />
			</s:elseif>
			<s:elseif test="sTipoFrame.equals('super')">
				<frame name="mainFrame" src="<s:url action="go_bandeja_solicitarSupervisor" />" />
			</s:elseif>
			<s:elseif test="sTipoFrame.equals('carpetaBusqueda')">
				<frame name="mainFrame" src="<s:url action="go_bandeja_ingreso_carpetabusqueda2" />" />
			</s:elseif>
			<s:elseif test="sTipoFrame.equals('modificarCarpetaBusqueda')">
				<frame name="mainFrame" src="<s:url action="dobuscarpmodificar"><s:param name="Idcarperta"><s:property value='idCarpeta' /></s:param></s:url>" />
			</s:elseif>

			<s:elseif test="sTipoFrame.equals('LogOperacion')">
				<frame name="mainFrame" src="<s:url action="go_mantenimiento_lstLogOperacion" />" />
			</s:elseif>
		</frameset>
		</s:else>
	</frameset>
</html>
