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
		<title>Hoja de Ruta del Expediente</title>
		<link rel="stylesheet" type="text/css" href="css/hojaRuta.css" />
		<%
			//Trazabilidaddocumento traza = (Trazabilidaddocumento)request.getAttribute("trazabilidad");
			Documento documento = (Documento)request.getAttribute("documento");
			Expediente expediente = documento.getExpediente();
			//Map<String, String> hojaRuta = (Map<String, String>)request.getAttribute("hojaRuta");
			//List<Unidad> areas = (List<Unidad>)request.getAttribute("areas");
			List<FilaHojaRuta> hojaRuta = (List<FilaHojaRuta>)request.getAttribute("hojaRuta");
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
		 <img alt="Logo APN" src="./images/logo.jpg" width="72px" height="40px" />
		<h1 class="titulo">HOJA DE RUTA</h1>
		<p>
		<table class="resume" >
			<tr>
				<td class="datos_cajetin">N&ordm; de Carpeta:</td>
				<td><%=expediente.getNroexpediente() %></td>
				<td class="datos_cajetin" style="width:8em;">Fecha Registro:</td>
				<td><%=formato.format(expediente.getFechacreacion()) %></td>
			</tr>
			<tr>
				<td class="datos_cajetin">Serie Documental:</td>
				<td colspan="3"><%=expediente.getSerie().getNombre() %></td>
			</tr>
			<tr>
				<td class="datos_cajetin">Asunto:</td>
				<td colspan="3"><%=expediente.getAsunto() %></td>
			</tr>
			
			<%-- 
			<tr>
				<td><strong>Fecha Registro: </strong><%=formato.format(documento.getFechaCreacion()) %></td>
				<td><strong>Remitente: </strong><%=traza.getRemitente().getNombreCompleto() %></td>
			</tr>
			<tr>
				<td><strong>N&ordm; de Expediente: </strong><%=expediente.getNroexpediente() %></td>
				<td><strong>Cargo: </strong><%=traza.getRemitente().getRoles().get(0).getNombre() %></td>
			</tr>
			<tr>
				<td><strong>Tipo de Documento: </strong><%=documento.getTipoDocumento().getNombre() %></td>
				<td><strong>Cliente: </strong>
					<%if(expediente.getCliente() != null && expediente.getCliente().getIdCliente() != null){
						out.println(expediente.getCliente().getNombreRazon());
					}else{
						out.println("-");
					}%>
				</td>
			</tr>
			<tr>
				<td><strong>N&ordm; de Documento: </strong><%=documento.getNumeroDocumento() %></td>
				<td><strong>Proceso: </strong><%=expediente.getProceso().getNombre() %></td>
			</tr>
			<tr>
				<td colspan="2"><strong>Asunto: </strong><%=traza.getAsunto() %></td>
			</tr>
			--%>
		</table>
		</p>
		<%--TODO Borrar cuando ya no sea util
		<p>
		<%if(areas != null && !areas.isEmpty()){ %>
			<table class="datos">
				<tr>
					<th class="datos_titulo"colspan="2">TR&Aacute;MITE DE DOCUMENTOS DOCUMENTARIO</th>
				</tr>
				<tr>
					<td class="datos_subtitulo">TRANSFERIR/REMITIR A</td>
					<td class="datos_subtitulo">ACCI&Oacute;N</td>
				</tr>
			<%for(Unidad area : areas){ 
				boolean conContenido = (hojaRuta.get(area.getDescripcion()) != null);
				if(conContenido){
				String ruta = hojaRuta.get(area.getDescripcion()); 
			%>
				<tr>
					<td class="datos_contenido"><%=area.getDescripcion() %></td>
					<td class="datos_contenido"><%=ruta %></td>
				</tr>
			<%}
			} %>
			</table>		
		<%}else{ %>
			No pueden encontrarse &aacute;reas funcionales en el sistema
		<%} %>
		</p>
		 --%>
		<p>
		<%if(hojaRuta != null && !hojaRuta.isEmpty()){ %>
			<table class="datos">
				<tr>
					<td class="datos_subtitulo" style="width:10em;">Documento</td>
					<td class="datos_subtitulo" style="width:5em;">Fecha/Hora</td>
					<td class="datos_subtitulo" style="width:15em;">Remitente (Area)</td>
					<td class="datos_subtitulo" style="width:15em;">Destinatario (Area)</td>
					<td class="datos_subtitulo" style="width:5em;">Movimiento</td>
                                        <td class="datos_subtitulo" style="width:5em;">Acci&oacute;n</td>
					<td class="datos_subtitulo" style="width:12em;">Prove&iacute;do</td>
					
					
				</tr>
				<%for(FilaHojaRuta fila : hojaRuta){ %>
				<tr>
					<td class="datos_contenido"><%=fila.getNumeroDocumento() %></td>
					<td class="datos_contenido"><%=new SimpleDateFormat("dd-MM-yyyy HH:mm").format(fila.getFechaCreacion())%></td>
					<td class="datos_contenido"><%=fila.getRemitente() %></td>
					<td class="datos_contenido"><%=fila.getDestinatario() %></td>
					<td class="datos_contenido"><%=fila.getAccion() %></td>
                                        <td class="datos_contenido"><%=fila.getProveido() %></td>
                                        <td class="datos_contenido" style="text-align:left;"><%=fila.getContenido() %></td>
					
					
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