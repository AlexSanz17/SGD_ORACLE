<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="com.btg.ositran.siged.domain.ReporteAPN1" %>
<%@page import="java.util.ArrayList" %>
<%@page import="java.text.SimpleDateFormat" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<style type="text/css">
	@import "css/estilo-reportes.css";
</style>
<%
    ReporteAPN1 itemlistaReporteAPN1;
	ArrayList listaReporteAPN1 = (ArrayList)request.getAttribute("listaReporteAPN1");
	String fechaDesde = (String)request.getAttribute("fechaDesde");
	String fechaHasta = (String)request.getAttribute("fechaHasta");
	String url  = (String)request.getAttribute("url");
	String areaOrigen = (String)request.getAttribute("areaOrigen");
	String path = request.getContextPath() + url;
	String idPrioridad = (String)request.getAttribute("idPrioridad");
	String idTipoDocumento = (String)request.getAttribute("idTipoDocumento");
	String idAreaOrigen = (String)request.getAttribute("idAreaOrigen");
	String idAreaDestino = (String)request.getAttribute("idAreaDestino");

	String TituloRepAPN1="REPORTE DE DOCUMENTOS EMITIDOS POR : "+ areaOrigen+" ENTRE EL  "+fechaDesde+" Y EL "+fechaHasta;
	SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy hh:mm");
%>
<script type="text/javascript">
	function imprimir(){
		var ventana = window.open("", "", "");
        var contenido = "<html><head>"+
            "<link rel=\"stylesheet\" type=\"text/css\" href=\"css/reporteImprimir.css\" />" +
            "</head>    " +
            "<body onload='window.print();' >" +
            "<h1 class=\"TxtTitulo\"><%=TituloRepAPN1%></h1>" +
            document.getElementById("filasImp").innerHTML + "</body></html>";
        ventana.document.open();
        ventana.document.write(contenido);
        ventana.document.close();
	}

	function exportar(){
		document.getElementById("formRepADN2").submit();
	}
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Lista de documentos </title>
</head>
<body>
	<%if(!listaReporteAPN1.isEmpty()) {%>
	<form id="formRepADN2" name="formRepADN2" action=ReporteAPN_exportarExcelAPN1 method=post>
		<div id="reporte1">
			<h1 class="TxtTitulo"><%=TituloRepAPN1%></h1>
			<input id="html" jsId="html" name="html" type="hidden"/>
			<p style="text-align: right;">
				<input type="button" onclick="imprimir()" value="Imprimir"/>
				<input type="button" onclick="location.href='<%=path%>'" value="Exportar a Excel"/>
				<input id="fechaDesdeH"  jsId="fechaDesdeH" name="fechaDesdeH" type="hidden" value="<%=fechaDesde%>"/>
				<input id="fechaHastaH"  jsId="fechaHastaH" name="fechaHastaH" type="hidden" value="<%=fechaHasta%>"/>
				<input id="areaOrigenH"  jsId="areaOrigenH" name="areaOrigenH" type="hidden" value="<%=areaOrigen%>"/>
				<input id="idAreaOrigenH"  jsId="idAreaOrigenH" name="idAreaOrigenH" type="hidden" value="<%=idAreaOrigen%>"/>
				<input id="idPrioridadH"  jsId="idPrioridadH" name="idPrioridadH" type="hidden" value="<%=idPrioridad%>"/>
				<input id="idTipoDocumentoH"  jsId="idTipoDocumentoH" name="idTipoDocumentoH" type="hidden" value="<%=idTipoDocumento%>"/>
				<input id="idAreaDestinoH"  jsId="idAreaDestinoH" name="idAreaDestinoH" type="hidden" value="<%=idAreaDestino%>"/>
			</p>
			<div id="filasImp">
			<table class="tableForm" align="center" style="width:1400px" border="1">
			<tr class="cabecera">
				<th style="width:50px">Nro Expediente</th>
				<th style="width:50px">Proceso</th>
				<th style="width:50px">Fecha Creaci&oacute;n</th>
				<th style="width:50px">Tipo Documento</th>
				<th style="width:50px">Nro Documento</th>
				<th style="width:50px">Fecha Documento</th>
				<th style="width:70px">Cliente</th>
				<th style="width:100px">Destinatario</th>
				<th style="width:100px">Asunto Documento</th>
				<th style="width:100px">Prioridad</th>
				<th style="width:100px">Area Destino</th>
			</tr>
			<% 	for (int i=0; i<listaReporteAPN1.size(); i++){
					itemlistaReporteAPN1 = (ReporteAPN1)listaReporteAPN1.get(i);%>
					<tr class="fila">
						<td class="cent" style="width:50px"><%=itemlistaReporteAPN1.getNroExpediente() %></td>
						<td class="cent" style="width:50px"><%=itemlistaReporteAPN1.getProceso() %></td>
						<td class="cent" style="width:100px"><%=formato.format(itemlistaReporteAPN1.getFechaCreacion()) %></td>
						<td class="cent" style="width:50px"><%=itemlistaReporteAPN1.getTipoDocumento() %></td>
						<td class="cent" style="width:50px"><%=itemlistaReporteAPN1.getNroDocumento() %></td>
						<td class="cent" style="width:100px"><%=formato.format(itemlistaReporteAPN1.getFechaDocumento()) %></td>
						<td class="cent" style="width:50px"><%=itemlistaReporteAPN1.getCliente() %></td>
						<td class="cent" style="width:50px"><%=itemlistaReporteAPN1.getDestinatario() %></td>
						<td class="cent" style="width:50px"><%=itemlistaReporteAPN1.getAsuntoDocumento() %></td>
						<td class="cent" style="width:50px"><%=itemlistaReporteAPN1.getPrioridad() %></td>
						<td class="cent" style="width:50px"><%=itemlistaReporteAPN1.getAreaDestino() %></td>
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
		</div>
	</form>
</body>
</html>