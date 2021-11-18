<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="org.ositran.dojo.grid.Item" %>
<%@page import="java.util.List" %>
<%@page import="java.text.SimpleDateFormat" %>
<%@ page import="org.ositran.dojo.grid.ItemUF" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>

		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Documentos Pendientes</title>
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

			List<ItemUF> list = (List<ItemUF>)session.getAttribute("listRecibidos");

		%>
	</head>
	<body>
	<%if(list != null && !list.isEmpty()){ %>
	    <img alt="Logo OSITRAN" src="./images/logo.jpg"  width="85px" height="55px" />
		<h1>Documentos Pendientes </h1>
		<table>
			<tr>
			    <th>Item</th>
				<th>Documento</th>
				<th>Acci&oacute;n</th>
				<th>Expediente</th>
				<th>Asunto</th>
				<th>Fecha</th>
				<th>Area Remitente</th>
				<th>Remitente</th>
                <th>Proceso</th>
			</tr>
        <% int i = 1; %>
		<%for(ItemUF fila : list){ %>
			<tr>
			    <td class="filaR"><%=i++ %></td>
				<td class="center"><%=fila.getDocumento() %></td>
				<td class="center"><%=fila.getAccion() %></td>
				<td class="center"><%=fila.getExpediente() %></td>
				<td class="center"><%=fila.getAsunto() %></td>
				<td><%=new SimpleDateFormat("dd/MM/yyyy HH:mm").format(fila.getFechaAccion()) %></td>
				<td class="center"><%=fila.getArea() %></td>
				<td class="center"><%=fila.getRemitente() %></td>
				<td class="center"><%=fila.getProceso() %></td>
			</tr>
		<%} %>
	</table>
		<script type="text/javascript">
			window.print();
		</script>
	<%}else{ %>
		No se encontraron resultados de Documentos Pendientes
	<%} %>
	</body>
</html>