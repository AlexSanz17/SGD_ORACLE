<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Insert title here</title>
<script type="text/javascript">
	var completarFechaHastaIng = function(){
		dijit.byId('fechaHastaIng').constraints.min = dijit.byId('fechaDesdeIng').getValue();
	}
	var completarFechaDesdeIng = function(){
		dijit.byId('fechaDesdeIng').constraints.max = dijit.byId('fechaHastaIng').getValue();
	}				
	dojo.addOnLoad(function(){
		new dijit.form.DateTextBox({
			name			: "fechaDesdeIng",
			constraints		: {datePattern:"dd/MM/yyyy"},
			invalidMessage	: "Ingrese una fecha con el formato dd/mm/yyyy",
			required		: false,
			onChange		: completarFechaHastaIng
		}, "fechaDesdeIng");
		new dijit.form.DateTextBox({
			name			: "fechaHastaIng",
			constraints		: {datePattern:"dd/MM/yyyy"},
			invalidMessage	: "Ingrese una fecha con el formato dd/mm/yyyy",
			required		: false,
			onChange		: completarFechaDesdeIng
		}, "fechaHastaIng");
	});
	function Abrir_ventanaIng (paginaIng) {
    	var opcionesIng="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=1000, height=700, top=50, left=200";
    	var ventanaIng = window.open(paginaIng,"",opcionesIng);
    	ventanaIng.moveTo(60,50);
	}
	function buscar(){
		var correctoIng = true;
		var fechaDesdeIng = dijit.byId('fechaDesdeIng').getDisplayedValue();
		var fechaHastaIng = dijit.byId('fechaHastaIng').getDisplayedValue();
		if((fechaDesdeIng=="")||(fechaHastaIng=="")){
			alert("Debe ingresar las dos fechas para generar el reporte");
			correctoIng=false;
		}
		if(correctoIng){
			var paginaIng = "SMDReporteSigedSTOR_listarIngresados.action?fechaDesde="+fechaDesdeIng+"&fechaHasta="+fechaHastaIng;
			Abrir_ventanaIng(paginaIng);
		}			
	}
</script>
</head>
<body>
<table>
	<tr>
		<th>Filtrar Fechas</th>
	</tr>
	<tr>
		<td>Fecha Inicio:</td>
		<td>
			<input 	id="fechaDesdeIng" type="text" />
		</td>
		<td>Fecha Fin:</td>
		<td>
			<input 	id="fechaHastaIng" type="text" />
		</td>
	</tr>
	<tr>	
		<td>
			<button dojoType="dijit.form.Button"
	                type="button"
	                id="btnBuscarIng"
	                jsId="btnBuscar"
	                iconClass="siged16 sigedSearch16"
	                onClick="buscar"
	                showLabel="true">Ver reporte
	        </button>
		</td>
	</tr>
</table>
</body>
</html>