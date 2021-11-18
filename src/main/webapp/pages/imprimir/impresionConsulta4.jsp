<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="org.ositran.dojo.grid.Item" %>
<%@page import="java.util.List" %>
<%@page import="java.text.SimpleDateFormat" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Documentos Emitidos Pendientes de Respuesta</title>
		<style type="text/css">
			body{
				font-family:verdana;
			}
			h1{
				text-align:center;
				font-size:1.5em;
			}
			table{
				border: 0.02em solid;
				border-collapse: collapse;
				font-size: 0.83em
			}
			th{
				border: 0.02em solid;
				text-align:center;
			}
			td{
				border: 0.02em solid;
				padding: 0.3em;
			}
			td.center{
				text-align:center;
			}
		</style>
		<%
			List<Item> filas = (List<Item>)request.getAttribute("lstConsultaAPN4Grid");
			
		%>
	</head>
	<body>
	<%if(filas != null && !filas.isEmpty()){ %>
		<h1>Documentos Pendientes de Respuesta </h1>
		<table>
			<tr>
				<th>Nro Expediente</th>
				<th>Nro Documento</th>
				<th>Tipo Documento</th>
				<th>Fecha Envio</th>
				<th>Proceso</th>
				<th>&Aacute;rea Destino</th>
				<th>Asunto Expediente</th>
				<th>Asunto Documento</th>
				<th>Estado Expediente</th>
				<th>Cliente</th>
				<th>Prioridad</th>
				
			</tr>
		<%for(Item fila : filas){ %>
			<tr>
				<td class="center"><%=fila.getExpediente() %></td>
				<td class="center"><%=fila.getDocumento() %></td>
				<td class="center"><%=fila.getTipodocumento() %></td>
				<td class="center"><%=new SimpleDateFormat("dd-MM-yyyy hh:mm").format(fila.getFecharecepcion()) %></td>
				<td><%=fila.getProceso() %></td>
				<td class="center"><%=fila.getAreadestino() %></td>
				<td><%=fila.getAsuntoExpediente() %></td>
				<td><%=fila.getAsunto() %></td>
				<td><%=fila.getEstado() %></td>
				<td><%=fila.getCliente() %></td>
				<td><%=fila.getPrioridad() %></td>
				
			</tr>
		<%} %>
		</table>
		<script type="text/javascript">
			window.print();
		</script>
	<%}else{ %>
		No se encontraron resultados para la b&uacute;squeda
	<%} %>
	</body>
</html>