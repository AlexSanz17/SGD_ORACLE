<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%//@ page language="java" contentType="application/vnd.ms-excel; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="com.btg.ositran.siged.domain.FilaRResumenProceso"%>
<%@page import="com.btg.ositran.siged.domain.FilaRDetalleProceso"%>
<%@page import="org.ositran.utils.utilReporte"%>
<%@page import="org.ositran.utils.usuariosReporte"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Date"%>
<%@page import="org.ositran.utils.formatoHora"%>
<%@page import="java.util.List"%>
<html xmlns="http://www.w3.org/1999/xhtml">

<%
		//Tamaño de la tabla px
		int pixelesTabla;
		//Fecha util
		Date fechaActual = new Date(System.currentTimeMillis());
		//Proceso o grupo de proceso?
		String proceso = (String)request.getAttribute("procesoClasificacion");
		//Resultado del reporte resumido
		ArrayList resultadoR =(ArrayList)request.getAttribute("resultado");
		//ArrayList<ArrayList<FilaRResumenProceso>> resultadoResumen=(ArrayList<ArrayList<FilaRResumenProceso>>)request.getAttribute("resultado");
		//Resultado del reporte detallado
		ArrayList resultadoD = (ArrayList)request.getAttribute("resultadoDetalle");
		//ArrayList<ArrayList<FilaRDetalleProceso>> resultadoDetalle=(ArrayList<ArrayList<FilaRDetalleProceso>>)request.getAttribute("resultadoDetalle");
		//Lista de usuarios por cada cuadro del reporte
		ArrayList storeUsuarios =(ArrayList)request.getAttribute("storeUsuarios");
		//ArrayList<utilReporte> storeUsuarios=(ArrayList<utilReporte>)request.getAttribute("storeUsuarios");
		//Lista que almacena los tiempos por usuario
		ArrayList totalesUsuarios = (ArrayList)request.getAttribute("tiemposXProceso");
		//ArrayList<ArrayList<usuariosReporte>> totalesUsuarios=(ArrayList<ArrayList<usuariosReporte>>)request.getAttribute("tiemposXProceso");
		//Lista que genera la cabecera de usuarios en el reporte detallado
		ArrayList usuariosColumnas = (ArrayList)request.getAttribute("usuariosDistintos");
		//ArrayList<String> usuariosColumnas = (ArrayList<String>)request.getAttribute("usuariosDistintos");
		//Lista de todos los procesos que se han revisado, sirve para poner encabezados a las tablas
		/*PUEDE SER REEMPLAZADO POR UN ENTERO QUE CUENTE EL NUMERO DE PROCESOS*/
		ArrayList procesos = (ArrayList)request.getAttribute("procesosRevisados");
		//ArrayList<String> procesos = (ArrayList<String>)request.getAttribute("procesosRevisados");
		//Funcion para poner el formato de hora a "Dias Horas:minutos y segundos de ser necesario"
		formatoHora FormatoHora = new formatoHora();
		//String temporal para colocar los tiempos
		String tiempoT="";
		//Contador de las tablas, cuando llegue al máximo debe poner el gran total
		int contadorProcesos=0;
		//Gran total de tiempos
		ArrayList totalTiempo = new ArrayList();
		//Gran total de promedios
		ArrayList promediosVeces = new ArrayList();
		//Gran total de expedientes
		ArrayList totalExpedientes = new ArrayList();
		//Gran total de veces llegadas
		ArrayList totalVeces = new ArrayList();
		//Contador de Expedientes
		long contadorExpedientes=0;	
		//Solo para formatear el título
		tiempoT="Reporte por ";
		if(proceso.equals("grupoproceso")){
			tiempoT+="grupo de procesos ";
		}else{
			tiempoT+="procesos independientes ";
		}
		tiempoT+="a las "+fechaActual.getHours()+":";
		if(fechaActual.getMinutes()<10){
			tiempoT+="0";
		}
		tiempoT+=fechaActual.getMinutes()+" del "+fechaActual.getDate()+"-"+Integer.toString(fechaActual.getMonth()+1)+"-"+Integer.toString(1900+fechaActual.getYear());
%>
<head>
<style type="text/css">
	@import "css/estilo.css";
</style>
<script type="text/javascript">
	function exportar(){
		//Tablas es un div que contiene todo el bucle de tablas del reporte.
		var tablas=document.getElementById("tablas");
		//alert(tablas.innerHTML);
		document.getElementById("html").value=tablas.innerHTML;
		//El form contiene todo el html de la pagina. Al hacer submit llama al evento ExportarExcel del Action y le pasa el parámetro "html"
		document.getElementById("form1").submit();
	}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title><%=tiempoT%></title>
</head>
<body style="background-color:#3E7AAE">
	<% boolean noHay = false;
	if(resultadoR==null||resultadoR.isEmpty()){
		if(resultadoD==null||resultadoD.isEmpty()){
			noHay=true;
		}
	}
	if(noHay){%>
			<table width="100%" border="0">
	         	<tr>
	            	<td height="100" align="center" class="label">NO HAY COINCIDENCIAS</td>
	         	</tr>
	        </table>
	<%}else{ %>
	<form id="form1" name="form1" action="ReporteSiged_exportarExcel.action" method="post">
		<div id="tablas">
		<h1 class="TxtTitulo" style="font-size:20px"><%=tiempoT%></h1>
		<input id="html" jsId="html" name="html" type="hidden"/>
		<input type="button" onclick="exportar()" value="Exportar a Excel"/>
		<br/><br/>
		<%if(resultadoR!=null){
			for(int a=0; a<resultadoR.size();a++){%>
				<table class="tableForm" align="center" border="1" style="width:97%">
					<%
						ArrayList filaR= (ArrayList)resultadoR.get(a);
						if(proceso.equals("grupoproceso")){%>
							<tr class="header"><th colspan="6", style="text-align:left"><%="Grupo de Procesos: "+((FilaRResumenProceso)filaR.get(0)).getGrupoproceso()%></th></tr>
						<%}else{%>
							<tr class="header"><th colspan="6", style="text-align:left"><%="Proceso: "+((FilaRResumenProceso)filaR.get(0)).getProceso()%></td></th>
						<%}
					%>
					<tr class="header">
						<th style="width:10%">Expediente</th>
						<th style="width:9%">Fecha de Creaci&oacute;n</th> 
						<th style="width:35%">Cliente</th> 
						<th style="width:35%">Asunto</th> 
						<th style="width:5%">Estado</th> 
						<th style="width:6%">Propietario</th>
					</tr>
					<% for(int b=0; b<filaR.size();b++){%>
						<tr class="altRow2">
							<td style="text-align:center; border:#A4C0DD"><%=((FilaRResumenProceso)filaR.get(b)).getExpediente() %></td>
							<td style="text-align:center; border:#A4C0DD"><%=((FilaRResumenProceso)filaR.get(b)).getFechaexpediente() %></td>
							<td style="border:#A4C0DD"><%=((FilaRResumenProceso)filaR.get(b)).getCliente() %></td>
							<td style="border:#A4C0DD"><%=((FilaRResumenProceso)filaR.get(b)).getAsunto() %></td>
							<td style="text-align:center; border:#A4C0DD"><%=((FilaRResumenProceso)filaR.get(b)).getEstado() %></td>
							<td style="text-align:center; border:#A4C0DD"><%=((FilaRResumenProceso)filaR.get(b)).getPropietario() %></td>
						</tr>
					<%} %>
				</table><br/>
				<%} 
			}
		else if(resultadoD!=null){
			//Calculamos el tamaño de la tabla
			pixelesTabla=usuariosColumnas.size()*70+1100;
			//Seteamos los totales a 0 para poder sumarlos solamente;
			for(int x=0; x<usuariosColumnas.size();x++){
				totalTiempo.add(new Long (0l));
				promediosVeces.add(new Long(0l));
				totalExpedientes.add(new Long(0l));
				totalVeces.add(new Long(0l));  
			}%>
			<table class="tableForm" border="1" style="width:<%=Integer.toString(pixelesTabla)%>px">
				<%for(int c=0; c<resultadoD.size();c++){%>
					<%
						ArrayList filaD= (ArrayList)resultadoD.get(c);
						if(proceso.equals("grupoproceso")){%>
							<tr class="header"><th colspan="<%=Integer.toString(usuariosColumnas.size()+7)%>", style="text-align:left"><%="Grupo de Procesos: "+((FilaRDetalleProceso)filaD.get(0)).getGrupoproceso()%></th></tr>
						<%}else{%>
							<tr class="header"><th colspan="<%=Integer.toString(usuariosColumnas.size()+7)%>", style="text-align:left"><%="Proceso: "+ ((FilaRDetalleProceso)filaD.get(0)).getProceso()%></th></tr>
						<%}
					%>
					<tr class="header">
						<th style="width:100px">Expediente</th>
						<th style="width:100px">Fecha de Creaci&oacute;n</th> 
						<th style="width:380px">Cliente</th> 
						<th style="width:300px">Asunto</th>
							<%for (int e=0; e<usuariosColumnas.size();e++){
								String usuario = (String)usuariosColumnas.get(e);%>						
								<th style="text-align:center; width:70px"><%=usuario%></th> 
							<% }%>
						<th style="width:70px">Total</th>	
						<th style="width:50px">Estado</th> 
						<th style="width:100px">Propietario</th>
					</tr>
					<%for(int d=0; d<filaD.size();d++){
						tiempoT=""; contadorExpedientes++;%>
						<tr class="altRow2">
							<td style="text-align:center; border:#A4C0DD"><%=((FilaRDetalleProceso)filaD.get(d)).getExpediente() %></td>
							<td style="text-align:center; border:#A4C0DD"><%=((FilaRDetalleProceso)filaD.get(d)).getFechaexpediente() %></td>
							<td style="border:#A4C0DD"><%=((FilaRDetalleProceso)filaD.get(d)).getCliente() %></td>
							<td style="border:#A4C0DD"><%=((FilaRDetalleProceso)filaD.get(d)).getAsunto() %></td>
							<% 
								for(int f=0; f<storeUsuarios.size();f++){
									utilReporte stu = (utilReporte)storeUsuarios.get(f);
									if(((FilaRDetalleProceso)filaD.get(d)).getExpediente().equals(stu.getExpediente())){
										int i=0;
										boolean llenado = false;
										for(int g=0; g<usuariosColumnas.size();g++){
											llenado = false;
											for(int h=0; h<stu.getUsuarios().size(); h++){
											i++;
											String usuario = (String)usuariosColumnas.get(g);
											/*********************************************************************************/
											if(usuario.equals(((usuariosReporte)stu.getUsuarios().get(h)).getUsuario())){
											/*********************************************************************************/
											%>
												<td style="border:#A4C0DD"><%=((usuariosReporte)stu.getUsuarios().get(h)).getTiempoatencion()%>
												<%if(((usuariosReporte)stu.getUsuarios().get(h)).getVeces()>1){
												/*****************************************************************************/%>
													<%=" ("+Integer.toString(((usuariosReporte)stu.getUsuarios().get(h)).getVeces())+")"%>
												<%/*****************************************************************************/
												}%>
												</td>
											<%
											llenado = true;
											}
											}
											if(!llenado){
												%>
												<td style="border:#A4C0DD">&nbsp</td>
											<%
											}
										}
										tiempoT = new String (stu.getTiempoTotal());
									}
								}%>
							<td style="text-align:center; border:#A4C0DD"><%=tiempoT%></td>
							<td style="text-align:center; border:#A4C0DD"><%=((FilaRDetalleProceso)filaD.get(d)).getEstado() %></td>
							<td style="text-align:center; border:#A4C0DD"><%=((FilaRDetalleProceso)filaD.get(d)).getPropietario() %></td>
						</tr>
					<%} %>
					<tr style="background-color:#87ADD2">
						<td style="border-width:0"></td><td style="border-width:0"></td><td style="border-width:0"></td>
						<td style="text-align:right; border-width:0; font-weight:bold">Subtotal:</td>
						<% 	//Para el total obtenido
							/*******************/
							ArrayList filaUsuarios;
							/*******************/
							if(contadorProcesos<procesos.size()){
								//Cuenta en que columna estamos
								int j=0;
								filaUsuarios = (ArrayList)totalesUsuarios.get(contadorProcesos);
								for(int k=0; k<filaUsuarios.size();k++){
									if(((usuariosReporte)filaUsuarios.get(k)).getVeces()!=0){
										long totalT = ((Long)(totalTiempo.get(j))).longValue();
										totalTiempo.set(j, new Long (totalT+((usuariosReporte)filaUsuarios.get(k)).getTiempo()));%>
										<td><%=((usuariosReporte)filaUsuarios.get(k)).getTiempoatencion() %></td>
									<%}else{%>
										<td style="text-align:center">-</td>
									<%}
									j++;
								}
							}
						%>
						<td style="border-width:0"></td><td style="border-width:0"></td><td style="border-width:0"></td><td style="border-width:0"></td>
					</tr>
					<tr style="background-color:#87ADD2">
						<td style="border-width:0"></td><td style="border-width:0"></td><td style="border-width:0"></td>
						<td style="text-align:right; border-width:0; font-weight:bold">Promedio por Expediente:</td>
						<% 	//Para el total obtenido
							if(contadorProcesos<procesos.size()){
								//Cuenta en que columna estamos
								int j=0;
								filaUsuarios = (ArrayList)totalesUsuarios.get(contadorProcesos);
								for(int l=0; l<filaUsuarios.size(); l++){
									if(((usuariosReporte)filaUsuarios.get(l)).getVeces()!=0){
										long totalE = ((Long)totalExpedientes.get(j)).longValue();
										totalExpedientes.set(j, new Long(totalE+((usuariosReporte)filaUsuarios.get(l)).getCantidadExpedientes()));%>
										<td><%=FormatoHora.toString(((usuariosReporte)filaUsuarios.get(l)).getTiempo()/(long)(((usuariosReporte)filaUsuarios.get(l)).getCantidadExpedientes()))%></td>
									<%}else{%>
										<td style="text-align:center">-</td>
									<%}
									j++;
								}
							}
						%>
						<td style="border-width:0"></td><td style="border-width:0"></td><td style="border-width:0"></td><td style="border-width:0"></td>
					</tr>
					<tr style="background-color:#87ADD2">
						</td><td style="border-width:0"></td><td style="border-width:0"></td><td style="border-width:0"></td>
						<td style="text-align:right; border-width:0; font-weight:bold">Promedio por Atenci&oacute;n:</td>
						<%if(contadorProcesos<procesos.size()){
								//Cuenta en que columna estamos
								int j=0;
								filaUsuarios = (ArrayList)totalesUsuarios.get(contadorProcesos);
								for(int m=0; m<filaUsuarios.size(); m++){
									if(((usuariosReporte)filaUsuarios.get(m)).getVeces()!=0){
										long totalV = ((Long)totalVeces.get(j)).longValue();
										totalVeces.set(j, new Long( totalV+(long)(((usuariosReporte)filaUsuarios.get(m)).getVeces())));%>
										<td><%=FormatoHora.toString(((usuariosReporte)filaUsuarios.get(m)).getTiempo()/(long)(((usuariosReporte)filaUsuarios.get(m)).getVeces()))%></td>
									<%}else{%>
										<td style="text-align:center">-</td>
									<%} 
									j++;
								}
						}
						contadorProcesos++;%>
						<td style="border-width:0"></td><td style="border-width:0"></td><td style="border-width:0"></td><td style="border-width:0"></td>
					</tr>
					<%if(contadorProcesos==procesos.size()){%>
						<tr style="background-color:#E8F0F7; border-width:0">
							<td style="border-width:0"></td><td style="border-width:0"></td><td style="border-width:0"></td>
							<td style="text-align:right; border-width:0; font-weight:bold">Total:</td>
							<%for(int x=0; x<usuariosColumnas.size();x++){%>
								<td  style="border-width:0"><%=FormatoHora.toString(((Long)totalTiempo.get(x)).longValue())%></td>
							<%}%>
							<td style="border-width:0"></td><td style="border-width:0"></td><td style="border-width:0"></td><td style="border-width:0"></td>
						</tr>
						<tr style="background-color:#E8F0F7; border-width:0">
							<td style="border-width:0"></td><td style="border-width:0"></td><td style="border-width:0"></td>
							<td style="text-align:right; border-width:0; font-weight:bold">Promedio por Expediente:</td>
							<%for(int x=0; x<usuariosColumnas.size();x++){%>
								<td  style="border-width:0"><%=FormatoHora.toString(((Long)totalTiempo.get(x)).longValue()/((Long)totalExpedientes.get(x)).longValue()) %></td>
							<%}%>
							<td style="border-width:0"></td><td style="border-width:0"></td><td style="border-width:0"></td><td style="border-width:0"></td>
						</tr>
						<tr style="background-color:#E8F0F7; border-width:0">
							</td><td style="border-width:0"></td><td style="border-width:0"></td><td style="border-width:0"></td>
							<td style="text-align:right; border-width:0; font-weight:bold">Promedio por Atenci&oacute;n:</td>
							<%for(int x=0; x<usuariosColumnas.size();x++){%>
								<td style="border-width:0"><%=FormatoHora.toString(((Long)totalTiempo.get(x)).longValue()/((Long)totalVeces.get(x)).longValue()) %></td>
							<%}%>
							<td style="border-width:0"></td><td style="border-width:0"></td><td style="border-width:0"></td><td style="border-width:0"></td>
						</tr>
					<%}%>
				<%}%> 
			</table><br/>
		<%}%>
		</div>
	</form>
	<%}%>
</body>
</html>