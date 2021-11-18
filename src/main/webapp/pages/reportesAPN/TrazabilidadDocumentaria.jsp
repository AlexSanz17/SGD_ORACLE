<%@page import="com.btg.ositran.siged.domain.TrazabilidadDocumentaria"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>
<%@ page import="com.btg.ositran.siged.domain.Unidad" %>
<%@ page import="com.btg.ositran.siged.domain.Expediente" %>
<%@ page import="com.btg.ositran.siged.domain.Documento" %>
<%@ page import="com.btg.ositran.siged.domain.Trazabilidaddocumento" %>
<%@ page import="com.btg.ositran.siged.domain.FilaHojaRuta" %>
<%@ page import="java.text.SimpleDateFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Hoja de Ruta del Documento</title>
		<link rel="stylesheet" type="text/css" href="css/hojaRuta.css" />
		<%
			List<TrazabilidadDocumentaria> traza = (List<TrazabilidadDocumentaria>)request.getAttribute("hojaTrazabilidadDocumentaria");
			SimpleDateFormat formato = new SimpleDateFormat("dd - MM - yyyy");
		%>
		<script type="text/javascript">
		function imprimirHR() {
            var ventana = window.open("", "", "");
            var contenido = "<html><head>"+
                "<link rel=\"stylesheet\" type=\"text/css\" href=\"css/hojaRuta.css\" />" +
                "</head>    " +
                "<body onload='window.print();' >" +
                document.getElementById("hojaRuta").innerHTML + "</body></html>";
            ventana.document.open();
            ventana.document.write(contenido);
            ventana.document.close();
        }
		</script>
	</head>
	<body>
		<div id="hojaRuta">
		<img alt="Logo OSITRAN" src="./images/logo.jpg" />
		<h1 class="titulo">TR&Aacute;MITE DOCUMENTARIO</h1>

		<p>
		<%if(traza != null && !traza.isEmpty()){ %>
			<table class="datos">
				<tr>

					<td class="datos_subtitulo" style="width:15em;">Remitente (Documento)</td>
					<td class="datos_subtitulo" style="width:15em;">Destinatario (Documento)</td>
					<td class="datos_subtitulo" style="width:5em;">Acci&oacute;n</td>

				</tr>
				<%for(TrazabilidadDocumentaria fila : traza){ %>
				<tr>

					<td class="datos_contenido"><%=fila.getOrigen() %></td>
					<td class="datos_contenido"><%=fila.getDestino() %></td>
					<td class="datos_contenido"><%=fila.getAccion()%></td>


				</tr>
				<%} %>
			</table>
		<%}else{ %>
			No existen registros en la trazabilidad para este documento.
		<%} %>
		</p>
		</div>
		<p class="botones">
			<input type="button" onClick="imprimirHR()" value="Imprimir" />
		</p>
	</body>
</html>