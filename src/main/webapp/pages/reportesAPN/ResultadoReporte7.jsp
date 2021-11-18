<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.List" %>
<%@page import= "java.text.SimpleDateFormat" %>
<%@page import= "java.math.*"%>
<%@page import="java.util.ArrayList" %>
<%@page import="com.btg.ositran.siged.domain.Documentofedateado" %>
<%@page import="org.ositran.utils.DateUtil" %>
<%@page import="org.ositran.utils.Constantes" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
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
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Documentos Fedateados</title>
		<%
		    List<Documentofedateado> list = (List<Documentofedateado>)request.getAttribute("listaDocReporteAPN7");
			String TituloRepAPN7 = "Reporte de documentos Fedateados";
		%>
		<script type="text/javascript">
			function imprimir(){
				var ventana = window.open("", "", "");
		        var contenido = "<html><head>"+
		            "<link rel=\"stylesheet\" type=\"text/css\" href=\"css/reporteImprimir.css\" />" +
		            "</head>    " +
		            "<body onload='window.print();' >" +
		            "<h1 class=\"TxtTitulo\"><%=TituloRepAPN7%></h1>" +
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
			<%
			 int i=1;
			 if(list != null && !list.isEmpty() && list.size()>0){ %>
			<form id="form1" name="form1" action="ReporteSiged_exportarExcel.action" method="post">
			<div id="tablas">
				<h1 class="TxtTitulo" style="font-size:20px"><%=TituloRepAPN7 %></h1>
				<input id="html" jsId="html" name="html" type="hidden"/>
				<p style="text-align: right;">
					<input type="button" onclick="imprimir()" value="Imprimir"/>
					<input type="button" onclick="exportar()" value="Exportar a Excel"/>
				</p>
				<div id="filasImp">
				<table class="tableForm" align="center" border="1">
					<tr class="cabecera">
						<th style="width:50px; border: solid #000000 0.02em;" >Item</th>
						<th style="width:100px; border: solid #000000 0.02em;" >Nro Documento</th>
						<th style="width:100px; border: solid #000000 0.02em;" >Asunto</th>
						<th style="width:60px; border: solid #000000 0.02em;" >Servicio</th>
						<th style="width:60px; border: solid #000000 0.02em;" >Nro Servicio</th>
						<th style="width:100px; border: solid #000000 0.02em;" >Nro Folios del Documento</th>
						<th style="width:100px; border: solid #000000 0.02em;" >Nro Copias Fedateadas</th>
						<th style="width:100px; border: solid #000000 0.02em;" >Unidad Solicitante</th>
                        <th style="width:100px; border: solid #000000 0.02em;" >Fecha Fedateo</th>
					<% for(Documentofedateado fila : list){ %>
							<tr class="fila">
								<td class="filaR"><%=i%></td>
								<td class="filaR" align="left"><%=fila.getNroDocumento() %></td>
								<td class="filaR" align="left"><%=fila.getAsunto() %></td>
								<td class="filaR" align="right"><%=fila.getServicio() %></td>
								<td class="filaR" align="right"><%=fila.getIdCertFirmas() ==  null ? "": fila.getIdCertFirmas()%></td>
								<td class="filaR" align="right"><%=fila.getNroFoliosDocumento().toString() %></td>
								<td class="filaR" align="right"><%=fila.getNroCopiasFedateadas().toString() %></td>
								<td class="filaR" align="right"><%=fila.getDesUnidad() %></td>
								<td class="filaR" align="right"><%=fila.getFechaDocumentoRegistro() %></td>
							</tr>
					<% i++;} %>
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