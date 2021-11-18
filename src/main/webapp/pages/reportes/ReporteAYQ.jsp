<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="application/vnd.ms-excel; charset=UTF-8"
    pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import="com.btg.ositran.siged.domain.ExpedientesPendientesAYQ"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Calendar" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<style type="text/css">
	@import "css/estilo.css";
</style>
<%
	response.setHeader("Pragma", "no-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Content-Type", "application/vnd.ms-excel");
	response.addHeader("Content-Disposition", "inline; filename=\"ReportePendientesAYQ.xls\"");
	ExpedientesPendientesAYQ expedienteAYQ;
	ArrayList listaAYQ = (ArrayList)request.getAttribute("listaAYQ");
	String cabecera = "Reporte de Pendientes AYQ a las ";
	SimpleDateFormat fechita = new SimpleDateFormat("dd/MM/yyyy");
	Date fechaActual = new Date(System.currentTimeMillis());
	cabecera +=+fechaActual.getHours()+":";
	if(fechaActual.getMinutes()<10){
		cabecera+="0";
	}
	cabecera+=fechaActual.getMinutes()+" del "+fechita.format(fechaActual);
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Reporte de Expedientes Pendientes A&Q</title>
</head>
<body style="background-color:#3E7AAE">
	<%if (!listaAYQ.isEmpty()){ %>
	<h1 style="background-color: #789bbf; font-family:  Arial, Verdana; font-style: normal;FONT-WEIGHT: normal; color: #FFFFFF; font-size:20px"><%=cabecera%></h1>
	<table class="tableForm" align="center" border="1">
		<tr style="background-color:#669BCD; color:#ffffff">
			<th style="text-align:center; border:#789bbf">&nbsp</th>
			<th style="text-align:center; border:#789bbf">No. Expediente</th>
			<th style="text-align:center; border:#789bbf">Fecha Ingreso</th>
			<th style="text-align:center; border:#789bbf">Analista</th>
			<th style="text-align:center; border:#789bbf">Reclamante</th>
			<th style="text-align:center; border:#789bbf">Concesionario</th>
			<th style="text-align:center; border:#789bbf">Suministros</th>
			<th style="text-align:center; border:#789bbf">Etapa</th>
			<th style="text-align:center; border:#789bbf">Tipo de Documento</th>
			<th style="text-align:center; border:#789bbf">Sala</th>
			<th style="text-align:center; border:#789bbf">Fecha Doc.</th>
			<th style="text-align:center; border:#789bbf">Fecha Vencimiento</th>
			<th style="text-align:center; border:#789bbf">Días</th>
			<th style="text-align:center; border:#789bbf">Fecha Último Movimiento</th>
			<th style="text-align:center; border:#789bbf">Responsable Actual</th>
		</tr>
		<%for (int i=0; i<listaAYQ.size();i++){ 
		  	expedienteAYQ = (ExpedientesPendientesAYQ)listaAYQ.get(i);%>
			<tr style="background-color:#ffffff">
				<td style="text-align:center; border:#A4C0DD"><%=Integer.toString(i+1)%></td>
				<td style="text-align:center; border:#A4C0DD"><%=expedienteAYQ.getExpediente() %></td>
				<td style="text-align:center; border:#A4C0DD"><%=expedienteAYQ.getFechacreacion() %></td>
				<td style="border:#A4C0DD"><%=expedienteAYQ.getAnalista() %></td>
				<td style="border:#A4C0DD"><%=expedienteAYQ.getCliente() %></td>
				<td style="border:#A4C0DD"><%=expedienteAYQ.getConcesionario() %></td>
				<td style="border:#A4C0DD"><%=expedienteAYQ.getSuministros() %></td>
				<td style="border:#A4C0DD"><%=expedienteAYQ.getEtapa() %></td>
				<td style="border:#A4C0DD"><%=expedienteAYQ.getTipodocumento() %></td>
				<td style="text-align:center; border:#A4C0DD"><%=expedienteAYQ.getSala() %></td>
				<td style="text-align:center; border:#A4C0DD"><%=expedienteAYQ.getFechadocumento() %></td>
				<td style="text-align:center; border:#A4C0DD"><%=expedienteAYQ.getFechavencimiento() %></td>
				<td style="text-align:left; border:#A4C0DD"><%=expedienteAYQ.getDias() %></td>
				<td style="text-align:center; border:#A4C0DD"><%=expedienteAYQ.getFechaultimo() %></td>
				<td style="text-align:center; border:#A4C0DD"><%=expedienteAYQ.getPropietario() %></td>
			</tr>
		<%}
		}else{%>
			<table width="100%" border="0">
	         	<tr>
	            	<td height="100" align="center" class="label">NO HAY COINCIDENCIAS</td>
	         	</tr>
	        </table>
		<%}%>
	</table>
</body>
</html>