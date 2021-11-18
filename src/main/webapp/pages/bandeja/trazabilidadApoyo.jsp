<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Trazabilidad de Copias</title>
<link rel="stylesheet" type="text/css" href="css/estilo.css" />
		<script type="text/javascript" src="js/form.js"> </script>
		<script type="text/javascript" src="js/general.js"></script>
		<link rel="stylesheet" type="text/css" media="all" href="css/calendar-green.css" />
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/calendar-es.js"></script>
		<script type="text/javascript" src="js/calendar-setup.js"></script>
		<script type="text/javascript">

	         function Abrir_ventanaCopia (pagina) {
	             var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=650, height=315, top=50, left=200";
	             window.open(pagina,"",opciones);
	          }

	         function verDetalleCopias(idTrazabilidad){
	        	 var pagina = "detalleCopias.action?idTrazabilidad="+idTrazabilidad+"&tipoOrigen=C";
	        	 Abrir_ventanaCopia(pagina);
	         }
	         
	         function regresar(){
	             window.close();
	          }
		</script>
		
<%@page import="com.btg.ositran.siged.domain.Trazabilidadapoyo"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.text.SimpleDateFormat"%>
<%
	ArrayList resultadoT =(ArrayList)request.getAttribute("lstTrazabilidadapoyo");
	//Character tipoOrigen =(Character)request.getAttribute("tipoOrigen");
	String idOrigen = (String)request.getAttribute("idOrigen");
	SimpleDateFormat fechita = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	String titulo="";
	titulo += "Copias de apoyo correspondientes al Expediente: "+idOrigen+" el "+fechita.format(((Trazabilidadapoyo)resultadoT.get(0)).getFechacreacion());
%>

</head>
<body>
	<table width="100%">
		<tr>
			<td valign="center" align="center" style="FONT-WEIGHT: bold; FONT-SIZE: 10pt; COLOR: #31619c; FONT-FAMILY: verdana, arial, helvetica, sans-serif">
				<%=titulo %>
			</td>		
		</tr>
	</table>
	<br />
	<table cellpadding="1" cellspacing="1" class="tableForm" width="90%" align="center">
		<tr class="header">
			
			<td class="data">Fecha Env&iacute;o</td>
			<td class="data">De</td>
			<td class="data">Para</td>
			<td class="data">Acci&oacute;n</td>
			<td class="data">Estado</td>
		</tr>
	<%
		if(!resultadoT.isEmpty()){
	    	for(int i=0; i<resultadoT.size(); i++){
	        	Trazabilidadapoyo traza = (Trazabilidadapoyo)resultadoT.get(i);%>
				<tr  class="altRow2" >
			   	<td align="center"><%=fechita.format(traza.getFechacreacion()) %></td>
			        <td align="center"><%=traza.getRemitente().getNombres() %> <%=traza.getRemitente().getApellidos() %></td>
			        <td align="center"><%=traza.getDestinatario().getNombres() %> <%=traza.getDestinatario().getApellidos() %></td>
			        <td align="center"><%=traza.getAccion().getDescripcion() %></td>
			        <td align="center"><%=traza.getEstado().getDescripcion() %></td>
	      		</tr>
	      	<%}
		}%>
	</table>
	<table width="100%" >		
 		<tr align="center">
			<td colspan="2"  align="center">&nbsp;</td>
		</tr>
		<tr>
			<td colspan="2" align="center" >
				<input type="button" name="selecciona" value="Regresar" class="button" onclick="regresar();"/>
			</td>
		</tr>
		<tr align="center">
			<td colspan="2"  align="center">&nbsp;</td>
		</tr>
	</table>
</body>
</html>