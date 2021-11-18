<%@page import="com.btg.ositran.siged.domain.ReporteAPN10"%>
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
		<title>Documentos registrados en Mesa de Parte</title>
		<%
		    List<ReporteAPN10> listaReporteAPN10 = (List<ReporteAPN10>)request.getAttribute("listaReporteAPN10");
		    ReporteAPN10 itemlistaReporteAPN10;
			String TituloRepAPN10 = "Reporte de documentos";
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
		            "<h1 class=\"TxtTitulo\"><%=TituloRepAPN10%></h1>" +
		            document.getElementById("filasImpReporte10").innerHTML + "</body></html>";
		        ventana.document.open();
		        ventana.document.write(contenido);
		        ventana.document.close();
			}
			function exportar(){

			}
		</script>
	</head>
		<body>
            <%if(!listaReporteAPN10.isEmpty()) {%>
          <form id="formRepAPN10" name="formRepADN2" action=ReporteAPN_exportarExcelAPN10 method=post>

			<h1 class="TxtTitulo"><%=TituloRepAPN10%></h1>
			<input id="html" jsId="html" name="html" type="hidden"/>
			<p style="text-align: right;">
				<input type="button" onclick="imprimir()" value="Imprimir"/>
				<input type="button" onclick="location.href='<%=path%>'" value="Exportar a Excel"/>

			</p>
			<div id="filasImpReporte10">
			<table class="tableForm" align="center" style="width:1400px" border="1">
			<tr class="cabecera">
				<th style="width:50px">Nro</th>
				<th style="width:50px">Expediente</th>
				<th style="width:50px">Tipo</th>
                <th style="width:50px">Documento</th>
                <th style="width:50px">Asunto</th>
                <th style="width:50px">Administrado</th>
                <th style="width:50px">Fecha</th>
                 <th style="width:50px">Areas Transferidas </th>
                 <th style="width:50px">Tipo</th>
                <th style="width:50px">Documento</th>
                <th style="width:50px">Asunto</th>
			</tr>
			<% 	for (int i=0; i<listaReporteAPN10.size(); i++){
					itemlistaReporteAPN10 = (ReporteAPN10)listaReporteAPN10.get(i);%>
					<tr class="fila">
					    <td class="cent" style="width:50px"><%=i+1 %></td>
						<td class="cent" style="width:50px"><%=itemlistaReporteAPN10.getNroExpediente() %></td>
						<td class="cent" style="width:50px"><%=itemlistaReporteAPN10.getTipoDocumento() %></td>
						<td class="cent" style="width:50px"><%=itemlistaReporteAPN10.getNroDocumento() %></td>
						<td class="cent" style="width:50px"><%=itemlistaReporteAPN10.getAsunto() %></td>
						<td class="cent" style="width:50px"><%=itemlistaReporteAPN10.getCliente() %></td>
						<td class="cent" style="width:100px"><%=formato.format(itemlistaReporteAPN10.getFechaCreacion()) %></td>
					    <td class="cent" style="width:50px"><%=itemlistaReporteAPN10.getAreas()==null?"":itemlistaReporteAPN10.getAreas() %></td>
					    <td class="cent" style="width:50px"><%=itemlistaReporteAPN10.getTipoDocumentoAt()==null?"": itemlistaReporteAPN10.getTipoDocumentoAt()%></td>
						<td class="cent" style="width:50px"><%=itemlistaReporteAPN10.getNroDocumentoAt()==null?"":itemlistaReporteAPN10.getNroDocumentoAt() %></td>
						<td class="cent" style="width:50px"><%=itemlistaReporteAPN10.getAsuntoAt()==null? "": itemlistaReporteAPN10.getAsuntoAt()%></td>
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