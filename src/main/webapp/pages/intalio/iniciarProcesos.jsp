<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Lista de procesos</title>
<SCRIPT type="text/javascript">
	//dojo.require("dojox.rpc.Service");
	//dojo.require("dojox.rpc.JsonRPC");

	var intalioService = new dojo.rpc.JsonService("SMDIntalioAction.action");

	function Abrir_ventana (pagina) {
	    var opciones="toolbar=no, location=yes, directories=no, status=no, menubar=no, scrollbars=no, resizable=no,  top=50, left=200";
	    window.open(pagina,"",opciones);
	}

	function ejecutar(url,proceso){

		console.debug("proceso [%s]", proceso);
		
		if(confirm("Se creara un expediente?")){
			
			intalioService.CrearExpediente(proceso).addCallback(function(data){
				console.debug("data resultado [%s]=", data);
				if(data!="" || data!="Error"){
					Abrir_ventana(url+"&idexpediente="+data);
				}
				else{
					alert("El expediente no se puedo crear.");
				}				
				
			});

		}
	}

</SCRIPT>
</head>
<body>
     <div align="left">
		<table border="0">
		<tr>
			<td bgcolor="#E1EBFB" align="center" width="30px"><font color="black">Nº</font></td>
			<td bgcolor="#E1EBFB" align="left"><font color="black">Procesos</font></td>
		</tr>
		<% int i=1; %>
		<s:iterator value="data">
		<tr>
			<td align="center" width="30px">
				<%=i%>
			</td>
			<td align="left">
				<a href="javascript:ejecutar('<s:property value="url"/>','<s:property value="Proceso"/>');"><s:property value="Proceso"/></a>
			</td>
		</tr>
		<%i++; %>
		</s:iterator>
		</table>
	</div>
</body>
</html>