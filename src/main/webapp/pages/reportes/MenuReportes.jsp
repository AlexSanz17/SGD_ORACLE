<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>Insert title here</title>
	<style type="text/css">
		table.menuReportes{
        	width:100%;
        	font-family:sans-serif;
        	font-size:1.1em;
        }
        tr.filaReporte{
        	padding-left:2em;
        	padding-top: 0.3em;
        	padding-bottom: 0.3em;
        	cursor: pointer;
        	background-color:#FFFFFF;
        }
        tr.filaReporte:HOVER{
        	background-color:#D4E3FB;
        	color:#0066D1;
        }
        tr.filaReporte:FOCUS{
        	background-color:#D4E3FB;
        }
        td.filaReporteIco{
        	padding-left:1em;
        	padding-top: 0.3em;
        	padding-bottom: 0.3em;
        	width:1em;
        }
        td.filaReporte{
        	padding-left:1em;
        	padding-top: 0.3em;
        	padding-bottom: 0.3em;
        }
	</style>
</head>
<body>
	<h1>Lista de reportes disponibles</h1>
		<!--
		<a href="javascript:buildTabsFromToolBarTop('UsuFinRepPro',null)"><img alt="Reportes" src="images/xx.gif" />Reporte de Procesos</a><br/>
		<a href="javascript:buildTabsFromToolBarTop('UsuFinPendientes',null)"><img alt="ListaPendientes" src="images/xx.gif" />ListaPendientes</a><br/>
		<a href="javascript:buildTabsFromToolBarTop('UsuFinIngresados',null)"><img alt="ListaIngresados" src="images/xx.gif" />ListaIngresados</a><br/>
		<a href="javascript:buildTabsFromToolBarTop('UsuFinConcluidos',null)"><img alt="ListaIngresados" src="images/xx.gif" />ListaConcluidos</a><br/>
		<a href="javascript:buildTabsFromToolBarTop('UsuFinRepAYQ',null)"><img alt="ListaAYQ" src="images/xx.gif" />Expedientes Pendientes AYQ</a><br/>
		<a href="javascript:buildTabsFromToolBarTop('UsuFinRepMen',null)"><img alt="Reportes" src="images/xx.gif" />Reporte de Mensajeria</a><br/>
		<a href="javascript:buildTabsFromToolBarTop('reportePentaho',null)"><img alt="Reportes" src="images/xx.gif" />Reporte de Pentaho</a><br/>
	    -->
	 <br/>
	    <table class="menuReportes">
	    	<tr class="filaReporte">
	    		<td class="filaReporteIco"><img alt="Reportes" src="images/xx.gif" /></td>
	    		<td class="filaReporte" onclick="buildTabsFromToolBarTop('RepAPN1',null)">Documentos emitidos por &aacute;rea</td>
	    	</tr>
	    	<tr class="filaReporte">
	    		<td class="filaReporteIco"><img alt="Reportes" src="images/xx.gif" /></td>
	    		<td class="filaReporte" onclick="buildTabsFromToolBarTop('RepAPN2',null)">Tupas ingresados en el mes</td>
	    	</tr>
	    	<tr class="filaReporte">
	    		<td class="filaReporteIco"><img alt="Reportes" src="images/xx.gif" /></td>
	    		<td class="filaReporte" onclick="buildTabsFromToolBarTop('RepAPN3',null)">Documentos registrados en el mes</td>
	    	</tr>
	    	<tr class="filaReporte">
	    		<td class="filaReporteIco"><img alt="Reportes" src="images/xx.gif" /></td>
	    		<td class="filaReporte" onclick="buildTabsFromToolBarTop('RepAPN4',null)">Seguimiento de Documentos</td>
	    	</tr>

	    	<tr class="filaReporte">
	    		<td class="filaReporteIco"><img alt="Reportes" src="images/xx.gif" /></td>
	    		<td class="filaReporte" onclick="buildTabsFromToolBarTop('RepAPN5',null)">Documentos Pendientes</td>
	    	</tr>

		   <tr class="filaReporte">
		    		<td class="filaReporteIco"><img alt="Reportes" src="images/xx.gif" /></td>
		    		<td class="filaReporte" onclick="buildTabsFromToolBarTop('RepAPN6',null)">Documentos de Fedatarios</td>
		    </tr>

		      <tr class="filaReporte">
		    		<td class="filaReporteIco"><img alt="Reportes" src="images/xx.gif" /></td>
		    		<td class="filaReporte" onclick="buildTabsFromToolBarTop('RepAPN8',null)">Documentos Recibidos por Mesa de Parte</td>
		    </tr>


		     <tr class="filaReporte">
		    		<td class="filaReporteIco"><img alt="Reportes" src="images/xx.gif" /></td>
		    		<td class="filaReporte" onclick="buildTabsFromToolBarTop('RepAPN9',null)">Registro de Documentos</td>
		    </tr>

		    <tr class="filaReporte">
		    		<td class="filaReporteIco"><img alt="Reportes" src="images/xx.gif" /></td>
		    		<td class="filaReporte" onclick="buildTabsFromToolBarTop('RepAPN10',null)">Documentos de Mesa de Parte</td>
		    </tr>

	    </table>
</body>
</html>