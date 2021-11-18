<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.List" %>
<%@page import="java.util.ArrayList" %>
<%@page import="java.text.SimpleDateFormat" %>
<%@page import="com.btg.ositran.siged.domain.FilaReporteAPN2" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<style type="text/css">
			@import "css/estilo-reportes.css";
			td.filaR{
		    	padding-right:0.3em;
		    	padding-left:0.3em;
		        padding-top: 0.3em;
		        padding-bottom: 0.3em;
		        border-color: #00000;
		        border: solid 0.02em;
		    }
		</style>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>Expedientes ingresados en el mes</title>
		<%
			List<FilaReporteAPN2> listaReporteAPN2 = (List<FilaReporteAPN2>)request.getAttribute("listaReporteAPN2");
			String grupoProceso = (String)request.getAttribute("grupoProceso");
		    SimpleDateFormat fechita = new SimpleDateFormat("dd-MM-yyyy hh:mm");
		    String TituloRepAPN2 = "Reporte de procesos ";
		    TituloRepAPN2 += grupoProceso != null ? grupoProceso : "";
		    TituloRepAPN2 += " por mes";
		%>
		<script type="text/javascript">
		function imprimir(){
			var ventana = window.open("", "", "");
	        var contenido = "<html><head>"+
	            "<link rel=\"stylesheet\" type=\"text/css\" href=\"css/reporteImprimir.css\" />" +
	            "</head>    " +
	            "<body onload='window.print();' >" +
	            "<h1 class=\"TxtTitulo\"><%=TituloRepAPN2%></h1>" +
	            document.getElementById("filasImp").innerHTML + "</body></html>";
	        ventana.document.open();
	        ventana.document.write(contenido);
	        ventana.document.close();
		}
		function exportar(){
			//Tablas es un div que contiene todo el bucle de tablas del reporte.
			var tablas=document.getElementById("tablas");
			//alert(tablas.innerHTML);
			document.getElementById("html").value=tablas.innerHTML;
			//El form contiene todo el html de la pagina. Al hacer submit llama al evento ExportarExcel del Action y le pasa el parámetro "html"
			document.getElementById("form1").submit();
		}
		</script>
	</head>
	<body>
		<%if(listaReporteAPN2 != null && !listaReporteAPN2.isEmpty()){ %>
		<form id="form1" name="form1" action="ReporteSiged_exportarExcel.action" method="post">
		<div id="tablas">
			<h1 class="TxtTitulo" style="font-size:20px"><%=TituloRepAPN2 %></h1>
			<input id="html" jsId="html" name="html" type="hidden"/>
			<p style="text-align: right;">
				<input type="button" onclick="imprimir()" value="Imprimir"/>
				<input type="button" onclick="exportar()" value="Exportar a Excel"/>
			</p>
			<div id="filasImp">
			<table class="tableForm" align="center" border="1">
				<tr class="cabecera">
					<th>Item</th>
					<th>Nro Expediente</th>
					<th>Proceso</th>
					<th>Fecha Creaci&oacute;n</th>
					<th>Documento</th>
					<th>Cliente</th>
					<th>Destinatario</th>
					<th>Asunto Documento</th>
					<th>Prioridad</th>
				</tr>
				<%int i=1;
				for(FilaReporteAPN2 fila : listaReporteAPN2){ %>
					<tr class="fila">
						<td class="filaR"><%=i %></td>
						<td class="filaR"><%=fila.getNumeroExpediente()+"&nbsp;" %></td>
						<td class="filaR"><%=fila.getProceso() %></td>
						<td class="filaR"><%=fechita.format(fila.getFechaCreacion()) %></td>
						<td class="filaR"><%=(fila.getTipoDocumento() + " " + fila.getNumeroDocumento())%></td>
						<td class="filaR"><%=fila.getCliente() %></td>
						<td class="filaR"><%=fila.getDestinatario() %></td>
						<td class="filaR"><%=fila.getAsunto() %></td>
						<td class="filaR"><%=fila.getPrioridad() %></td>
					</tr>
				<%i++;
				} %>
			</table>
			</div>
		</div>
		</form>
		<%}else{ %>
			<table width="100%" border="0">
	         	<tr>
	            	<td height="100" align="center" class="label">NO HAY COINCIDENCIAS</td>
	         	</tr>
	        </table>
		<%} %>
	</body>
</html>