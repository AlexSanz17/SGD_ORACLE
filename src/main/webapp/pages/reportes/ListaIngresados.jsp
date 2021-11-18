<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import="com.btg.ositran.siged.domain.ExpedientesIngresadosStor" %>
<%@ page import="java.util.ArrayList" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<script type="text/javascript">
	function exportar(){
		var tablas = document.getElementById("reporte");
		document.getElementById("html").value=tablas.innerHTML;
		document.getElementById("form1").submit();
	}
</script>
<style type="text/css">
	@import "css/estilo-reportes.css";
</style>
<%
	ExpedientesIngresadosStor itemIngresado;
	ArrayList listaIngresados = (ArrayList)request.getAttribute("listaIngresados");
	String fechaDesde = (String)request.getAttribute("fechaDesde");
	String fechaHasta = (String)request.getAttribute("fechaHasta");
	String tiempoT="Reporte de Expedientes Ingresados desde el "+fechaDesde+" al "+fechaHasta;
%>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>Lista de documentos ingresados</title>
</head>
<body>
	<%if(!listaIngresados.isEmpty()) {%>
	<form id="form1" name="form1" action="ReporteSigedSTOR_exportarExcel.action" method="post">
		<div id="reporte">
			<h1 class="TxtTitulo"><%=tiempoT%></h1>
			<input id="html" jsId="html" name="html" type="hidden"/>
			<input type="button" onclick="exportar()" value="Exportar a Excel"/>
			<br/><br/>
			<table class="tableForm" align="center" style="width:1970px" border="1">
			<tr class="cabecera">
				<th style="width:50px">Año</th>
				<th style="width:70px">Mes Ingreso</th>
				<th style="width:100px">Nº Expediente</th>
				<th style="width:100px">Fecha Ingreso</th>
				<th style="width:100px">Fecha Documento</th>
				<th style="width:100px">Etapa</th>
				<th style="width:150px">Cliente</th>
				<th style="width:150px">Concesionaria</th>
				<th style="width:100px">Estado</th>
				<th style="width:70px">Sala</th>
				<th style="width:90px">Usuario</th>
				<th style="width:150px">Motivo</th>
				<th style="width:70px">Dias Transcurridos</th>
				<th style="width:50px">Monto</th>
				<th style="width:100px">Nº Resoluci&oacute;n</th>
				<th style="width:70px">Sala Resoluci&oacute;n</th>
				<th style="width:100px">Fecha Aprobaci&oacute;n</th>
				<th style="width:100px">Fecha Notificaci&oacute;n al Concesionario</th>
				<th style="width:100px">Fecha Cumplimiento</th>
				<th style="width:150px">Resultado</th>
			</tr>
			<% 	for (int i=0; i<listaIngresados.size(); i++){
					itemIngresado = (ExpedientesIngresadosStor)listaIngresados.get(i);%>
					<tr class="fila">
						<td class="cent" style="width:50px"><%=itemIngresado.getAnio() %></td>
						<td class="cent" style="width:70px"><%=itemIngresado.getMesIngreso() %></td>
						<td class="cent" style="width:100px"><%=itemIngresado.getExpediente() %></td>
						<td class="cent" style="width:100px"><%=itemIngresado.getFechaCreacionExpediente() %></td>
						<td class="cent" style="width:100px"><%=itemIngresado.getFechaCreacionExpediente() %></td>
						<td style="width:100px"><%=itemIngresado.getEtapa() %></td>
						<td style="width:150px"><%=itemIngresado.getCliente() %></td>
						<td style="width:150px"><%=itemIngresado.getConcesionario() %></td>
						<td style="width:100px"><%=itemIngresado.getEstado() %></td>
						<td class="cent" style="width:70px"><%=itemIngresado.getSala() %></td>
						<td class="cent" style="width:90px"><%=itemIngresado.getPropietario() %></td>
						<td style="width:150px"><%=itemIngresado.getMotivo() %></td>
						<td class="num" style="width:70px"><%=itemIngresado.getDiasTranscurridos()%></td>
						<td class="num" style="width:50px"><%=itemIngresado.getMonto() %></td>
						<td style="width:100px"><%=itemIngresado.getNroResolucion() %></td>
						<td style="width:70px"><%=itemIngresado.getSalaResolucion() %></td>
						<td class="cent" style="width:100px"><%=itemIngresado.getFechaAprobacion() %></td>
						<td class="cent" style="width:100px"><%=itemIngresado.getFechaNotificacionConcesionario() %></td>
						<td class="cent" style="width:100px"><%=itemIngresado.getFechaCumplimiento() %></td>
						<td style="width:150px"><%=itemIngresado.getResultado() %></td>
					</tr>
				<%}
			}else{%>
				<table width="100%" border="0">
		         	<tr>
		            	<td height="100" align="center" class="label">NO HAY COINCIDENCIAS</td>
		         	</tr>
		        </table>
			<%} %>
			</table>
		</div>
	</form>
</body>
</html>