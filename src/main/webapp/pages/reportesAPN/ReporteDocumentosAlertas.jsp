<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="org.ositran.dojo.grid.Item" %>
<%@page import="java.util.List" %>
<%@page import="com.btg.ositran.siged.domain.Alerta" %>
<%@page import="java.text.SimpleDateFormat" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>

		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Alertas</title>
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
		    List<Alerta> list = (List<Alerta>)session.getAttribute("listAlert");

		%>
	</head>
	<body>
	<%if(list != null && !list.isEmpty()){ %>
	    <img alt="Logo APN" src="./images/logo.jpg" />
		<h1>Alertas </h1>
		<table>
			<tr>
			    <th>Item</th>
				<th>Fecha L&iacute;mite</th>
				<th>Documento</th>
				<th>Asunto</th>
				<th>&Aacute;rea Destino</th>
                <th>Tiempo</th>
			</tr>
        <% int i = 1; %>
		<%for(Alerta fila : list){ %>
			<tr>
			    <td class="filaR"><%=i++ %></td>
				<td class="center"><%=new SimpleDateFormat("dd/MM/yyyy HH:mm").format(fila.getFechalimiteatencion()) %></td>
				<td class="center"><%=fila.getDocumento()%></td>
				<td class="center"><%=fila.getAsunto() %></td>
				<td><%=fila.getAreadestino() %></td>
				<td class="center"><%=fila.getDesfase() + " " + (fila.getDesplazo().equals('F')? "Fuera de Plazo": "Dentro del Plazo")%></td>
			</tr>
		<%} %>
	</table>
		<script type="text/javascript">
			window.print();
		</script>
	<%}else{ %>
		No se encontraron resultados de Documentos Informativos
	<%} %>
	</body>
</html>