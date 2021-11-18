<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Insert title here</title>
<script type="text/javascript">
	var completarFechaHastaPen = function() {
		dijit.byId('fechaHastaPen').constraints.min = dijit.byId('fechaDesdePen').getValue();
	}
	var completarFechaDesdePen = function() {
		dijit.byId('fechaDesdePen').constraints.max = dijit.byId('fechaHastaPen').getValue();
	}
	dojo.addOnLoad(function(){
		new dijit.form.DateTextBox({
			name			: "fechaDesdePen",
			constraints		: {datePattern:"dd/MM/yyyy"},
			invalidMessage	: "Ingrese una fecha con el formato dd/mm/yyyy",
			required		: false,
			onChange		: completarFechaHastaPen
		},"fechaDesdePen");
		new dijit.form.DateTextBox({
			name			: "fechaHastaPen",
			constraints		: {datePattern:"dd/MM/yyyy"},
			invalidMessage	: "Ingrese una fecha con el formato dd/mm/yyyy",
			required		: false,
            onChange		: completarFechaDesdePen
		},"fechaHastaPen");
	}); 
	function Abrir_ventanaPen (paginaPen) {
		var opcionesPen="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=1000, height=700, top=50, left=200";
		var ventanaPen = window.open(paginaPen,"",opcionesPen);
		ventanaPen.moveTo(60,50);
	}
	function buscar(){
		var correctoPen = true;
		var fechaDesdePen=dijit.byId("fechaDesdePen").getDisplayedValue(); 
		var fechaHastaPen=dijit.byId("fechaHastaPen").getDisplayedValue();
		if((fechaDesdePen=="")||(fechaHastaPen=="")){
			alert("Debe ingresar las dos fechas para generar el reporte");
			correctoPen=false;
		}
		if(correctoPen){
			var paginaPen = "SMDReporteSigedSTOR_listarPendientes.action?fechaDesde="+fechaDesdePen+"&fechaHasta="+fechaHastaPen;
			Abrir_ventanaPen(paginaPen);
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
			<input 	id="fechaDesdePen" type="text" />
		</td>
		<td>Fecha Fin:</td>
		<td>
			<input 	id="fechaHastaPen" type="text" />
		</td>
	</tr>
	<tr>	
		<td>
			<button dojoType="dijit.form.Button"
	                type="button"
	                id="btnBuscarPen"
	                jsId="btnBuscar"
	                iconClass="siged16 sigedSearch16"
	                onClick="buscar"
	                showLabel="true">Ver reporte
	        </button>
		</td>
	</tr>
	</tr>
</table>

</body>
</html>