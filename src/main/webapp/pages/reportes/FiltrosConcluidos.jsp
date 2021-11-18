<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Insert title here</title>
<script type="text/javascript">
	var completarFechaHastaCon = function(){
		dijit.byId('fechaHastaCon').constraints.min = dijit.byId('fechaDesdeCon').getValue();
	}
	var completarFechaDesdeCon = function(){
		dijit.byId('fechaDesdeCon').constraints.max = dijit.byId('fechaHastaCon').getValue();
	}
	dojo.addOnLoad(function(){
		new dijit.form.DateTextBox({ 
			name			: "fechaDesdeCon",
			constraints		: {datePattern:"dd/MM/yyyy"},
			invalidMessage	: "Ingrese una fecha con el formato dd/mm/yyyy",
			required		: false,
			onChange		: completarFechaHastaCon
		}, "fechaDesdeCon");
		new dijit.form.DateTextBox({
			name			: "fechaHastaCon",
			constraints		: {datePattern:"dd/MM/yyyy"},
			invalidMessage	: "Ingrese una fecha con el formato dd/mm/yyyy",
			required		: false,
			onChange		: completarFechaDesdeCon
		}, "fechaHastaCon")
	});
	function Abrir_ventanaCon (paginaCon) {
		var opcionesCon="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=1000, height=700, top=50, left=200";
		var ventanaCon = window.open(paginaCon,"",opcionesCon);
		ventanaCon.moveTo(60,50);
	}
	function buscar(){
		var correctoCon = true;
		var fechaDesdeCon = dijit.byId('fechaDesdeCon').getDisplayedValue();
		var fechaHastaCon = dijit.byId('fechaHastaCon').getDisplayedValue();
		if((fechaDesdeCon=="")||(fechaHastaCon=="")){
			alert("Debe ingresar las dos fechas para generar el reporte");
			correctoCon=false;
		}
		if(correctoCon){
			var paginaCon = "SMDReporteSigedSTOR_listarConcluidos.action?fechaDesde="+fechaDesdeCon+"&fechaHasta="+fechaHastaCon;
			Abrir_ventanaCon(paginaCon);
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
			<input id="fechaDesdeCon" type="text" />
		</td>
		<td>Fecha Fin:</td>
		<td>
			<input id="fechaHastaCon" type="text" />
		</td>
	</tr>
	<tr>
		<td>
			<button dojoType="dijit.form.Button"
			        type="button"
			        id="btnBuscarCon"
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