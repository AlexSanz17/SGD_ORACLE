<%@page import="com.btg.ositran.siged.domain.ReporteAPN9"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.List" %>
<%@page import= "java.text.SimpleDateFormat" %>
<%@page import= "java.math.*"%>
<%@page import="java.util.ArrayList" %>
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
		<title>Documentos registrados en el mes</title>
		<%
			List<ReporteAPN9> listaReporteAPN9 = (List<ReporteAPN9>)request.getAttribute("listaReporteAPN9");
		    ReporteAPN9 itemlistaReporteAPN9;
			String TituloRepAPN3 = "Reporte de documentos";
			String url  = (String)request.getAttribute("url");
			String path = request.getContextPath() + url;
			SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy hh:mm");
		%>
		<script type="text/javascript">



			function imprimir(){
				var ventana = window.open("", "", "");
		        var contenido = "<html><head>"+
		            "<link rel=\"stylesheet\" type=\"text/css\" href=\"css/reporteImprimir.css\" />" +
		            "</head>    " +
		            "<body onload='window.print();' >" +
		            "<h1 class=\"TxtTitulo\"><%=TituloRepAPN3%></h1>" +
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
			<%if(listaReporteAPN9 != null && !listaReporteAPN9.isEmpty()){ %>
			<form id="form9" name="form9" action="ReporteSiged_exportarExcel.action" method="post">
			<div id="tablas">
				<h1 class="TxtTitulo" style="font-size:20px"><%=TituloRepAPN3 %></h1>
				<input id="html" jsId="html" name="html" type="hidden"/>
				<p style="text-align: right;">
					<input type="button" onclick="imprimir()" value="Imprimir"/>
                    <input type="button" onclick="window.open('<%=path%>');" value="Exportar a PDF"/>
				</p>
				<div id="filasImp">
				<table class="tableForm" align="center" border="1">
					<tr class="cabecera">
						<th style="width:50px; border: solid #000000 0.02em;">Item</th>
						<th style="width:100px; border: solid #000000 0.02em;">Tipo Documento</th>
						<th style="width:100px; border: solid #000000 0.02em;">Documento</th>
						<th style="width:200px; border: solid #000000 0.02em;">Asunto</th>
						<th style="width:135px; border: solid #000000 0.02em;">Fecha de Creaci&oacute;n del Documento</th>
						<th style="width:100px; border: solid #000000 0.02em;">Prioridad</th>
						<th style="width:150px; border: solid #000000 0.02em;">Administrado</th>
						<th style="width:100px; border: solid #000000 0.02em;">&Aacute;rea</th>
					</tr>
							<% 	for (int i=0; i<listaReporteAPN9.size(); i++){
								itemlistaReporteAPN9 = (ReporteAPN9)listaReporteAPN9.get(i);%>
								<tr class="fila">
								    <td class="cent" style="width:50px"><%=i+1 %></td>
									<td class="cent" style="width:50px"><%=itemlistaReporteAPN9.getTipoDocumento() %></td>
									<td class="cent" style="width:50px"><%=itemlistaReporteAPN9.getNroDocumento() %></td>
									<td class="cent" style="width:50px"><%=itemlistaReporteAPN9.getAsuntoDocumento() %></td>
									<td class="cent" style="width:50px"><%=formato.format(itemlistaReporteAPN9.getFechaCreacion()) %></td>
									<td class="cent" style="width:50px"><%=itemlistaReporteAPN9.getPrioridad() %></td>
									<td class="cent" style="width:50px"><%=itemlistaReporteAPN9.getCliente() %></td>
									<td class="cent" style="width:50px"><%=itemlistaReporteAPN9.getAreaDestino() %></td>
								</tr>
				          <%} %>
				     </table>
				    </div>
				   </div>
				</form>
			 <%}else{%>
						<table width="100%" border="0">
							   <tr>
							          <td height="100" align="center" class="label">NO HAY COINCIDENCIAS</td>
							    </tr>
						</table>
			 <%} %>

		</body>
</html>