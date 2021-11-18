<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<script type="text/javascript">
	var storeTipoExpediente = new dojo.data.ItemFileReadStore({url: "Parametro_tipoExpediente.action"});
	var storeSala = new dojo.data.ItemFileReadStore({url: "Parametro_salaAYQ.action"});
	var storeResponsable = new dojo.data.ItemFileReadStore({url: "Parametro_responsableAYQ.action"});
	var storeAnalista = new dojo.data.ItemFileReadStore({url: "Parametro_analistaAYQ.action"});
	var completarFechaHastaAYQ = function() {
		dijit.byId('fechaHastaAYQ').constraints.min = dijit.byId('fechaDesdeAYQ').getValue();
	}
	var completarFechaDesdeAYQ = function() {
		dijit.byId('fechaDesdeAYQ').constraints.max = dijit.byId('fechaHastaAYQ').getValue();
	}
	var completarVencimientoDesdeAYQ = function(){
		dijit.byId('vencimientoDesdeAYQ').constraints.max = dijit.byId('vencimientoHastaAYQ').getValue();
	}	
	var completarVencimientoHastaAYQ = function (){
		dijit.byId('vencimientoHastaAYQ').constraints.min = dijit.byId('vencimientoDesdeAYQ').getValue();
	}
	dojo.addOnLoad(function(){
		new dijit.form.DateTextBox({
			name			: "fechaDesdeAYQ",
			constraints		: {datePattern:"dd/MM/yyyy"},
			invalidMessage	: "Ingrese una fecha con el formato dd/mm/yyyy",
			required		: false,
			onChange		: completarFechaHastaAYQ
		},"fechaDesdeAYQ");
		new dijit.form.DateTextBox({
			name			: "fechaHastaAYQ",
			constraints		: {datePattern:"dd/MM/yyyy"},
			invalidMessage	: "Ingrese una fecha con el formato dd/mm/yyyy",
			required		: false,
            onChange		: completarFechaDesdeAYQ
		},"fechaHastaAYQ");
		new dijit.form.DateTextBox({
			name			: "vencimientoDesdeAYQ",
			constraints		: {datePattern:"dd/MM/yyyy"},
			invalidMessage	: "Ingrese una fecha con el formato dd/mm/yyyy",
			required		: false,
			onChange		: completarVencimientoHastaAYQ
		},"vencimientoDesdeAYQ");
		new dijit.form.DateTextBox({
			name			: "vencimientoHastaAYQ",
			constraints		: {datePattern:"dd/MM/yyyy"},
			invalidMessage	: "Ingrese una fecha con el formato dd/mm/yyyy",
			required		: false,
            onChange		: completarVencimientoDesdeAYQ
		},"vencimientoHastaAYQ");
		new dijit.form.FilteringSelect({
			  jsId 			  :"tipoExpediente",
		      name           : "tipoExpediente",
		      store          : storeTipoExpediente,
		      searchAttr     : "label",
		      autoComplete   : false,
		      hasDownArrow   : true,
		      highlightMatch : "all",
		      invalidMessage : "Seleccione el tipo de expediente a encontrar",
		      required       : true,
		      value			 : "Seleccione"
		   }, "tipoExpediente");
		new dijit.form.FilteringSelect({
			  jsId 			  :"salaAYQ",
		      name           : "salaAYQ",
		      store          : storeSala,
		      searchAttr     : "label",
		      autoComplete   : false,
		      hasDownArrow   : true,
		      highlightMatch : "all",
		      invalidMessage : "Seleccione la sala del expediente a encontrar",
		      required       : true,
		      value			 : "Seleccione"
		   }, "salaAYQ");
		new dijit.form.FilteringSelect({
			  jsId 			  :"responsableAYQ",
		      name           : "responsableAYQ",
		      store          : storeResponsable,
		      searchAttr     : "label",
		      autoComplete   : false,
		      hasDownArrow   : true,
		      highlightMatch : "all",
		      invalidMessage : "Seleccione el responsable del expediente a encontrar",
		      required       : true,
		      value			 : "Seleccione"
		   }, "responsableAYQ");
		new dijit.form.FilteringSelect({
			  jsId 			  :"analistaAYQ",
		      name           : "salaAYQ",
		      store          : storeAnalista,
		      searchAttr     : "label",
		      autoComplete   : false,
		      hasDownArrow   : true,
		      highlightMatch : "all",
		      invalidMessage : "Seleccione el analista del expediente a encontrar",
		      required       : true,
		      value			 : "Seleccione"
		   }, "analistaAYQ");
	}); 

	function buscar(){
		var correcto = true;
		var tipoExpediente = dijit.byId('tipoExpediente').getValue();
		var fechaDesdeAYQ = dijit.byId('fechaDesdeAYQ').getDisplayedValue();
		var fechaHastaAYQ = dijit.byId('fechaHastaAYQ').getDisplayedValue();
		var salaAYQ = dijit.byId('salaAYQ');
		var responsableAYQ = dijit.byId('responsableAYQ').getValue();
		var analistaAYQ = dijit.byId('analistaAYQ').getValue();
		var vencimientoDesdeAYQ = dijit.byId('vencimientoDesdeAYQ').getDisplayedValue();
		var vencimientoHastaAYQ = dijit.byId('vencimientoHastaAYQ').getDisplayedValue();
		if(tipoExpediente==""){
			alert("Debe seleccionar un tipo de expediente para el reporte");
			correcto = false;
		}
		if((fechaHastaAYQ!=""&&fechaDesdeAYQ=="")||(fechaDesdeAYQ!=""&&fechaHastaAYQ=="")){
			alert("Debe ingresar las dos fechas para poder filtrar por fecha de ingreso");
			correcto = false;
		} 
		if((vencimientoHastaAYQ!=""&&vencimientoDesdeAYQ=="")||(vencimientoDesdeAYQ!=""&&vencimientoHastaAYQ=="")){
			alert("Debe ingresar las dos fechas para poder filtrar por fecha de vencimiento");
			correcto = false;
		}
		if(correcto){
			document.location = "SMDReporteSigedUsuarios_listarAYQ.action?tipoExpediente="+tipoExpediente
								+"&fechaDesdeAYQ="+fechaDesdeAYQ+"&fechaHastaAYQ="+fechaHastaAYQ
								+"&salaAYQ="+salaAYQ+"&responsableAYQ="+responsableAYQ
								+"&analistaAYQ="+analistaAYQ+"&vencimientoDesdeAYQ="+vencimientoDesdeAYQ
								+"&vencimientoHastaAYQ="+vencimientoHastaAYQ;
		}
	}
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Insert title here</title>
</head>
<body>

	<table>
	<tr>
		<td>Tipo de Expediente</td>
		<td><select id="tipoExpediente" > </select></td>
	</tr>
	<tr>
		<td style="font-weight:bold">Intervalo de fechas de ingreso</td>
	</tr>
	<tr>
		<td>Ingresados Desde:</td>
		<td>
			<input 	id="fechaDesdeAYQ" type="text" />
		</td>
		<td>Ingresados Hasta:</td>
		<td>
			<input 	id="fechaHastaAYQ" type="text" />
		</td>
	</tr>
	<tr>
		<td style="font-weight:bold">Sala Asignada</td>
	</tr>
	<tr>
		<td>Sala: </td>
		<td><select id="salaAYQ"></select></td>
	</tr>
	<tr>
		<td style="font-weight:bold">Responsable y Analista Asignado</td>
	</tr>
	<tr>
		<td>Responsable: </td>
		<td><select id="responsableAYQ"></select></td>
		<td>Analista: </td>
		<td><select id="analistaAYQ"></select></td>
	</tr>
	<tr>
		<td style="font-weight:bold">Intervalo de fechas de vencimiento</td>
	</tr>
	<tr>
		<td>Vencimiento Desde:</td>
		<td>
			<input 	id="vencimientoDesdeAYQ" type="text" />
		</td>
		<td>Vencimiento Hasta:</td>
		<td>
			<input 	id="vencimientoHastaAYQ" type="text" />
		</td>
	</tr>
	</table>
	<button dojoType="dijit.form.Button"
			type="Button"
			id="btnBuscarAYQ"
			jsId="btnBuscar"
			iconClass="siged16 sigedSearch16"
			onClick="buscar"
			showLabel="true">Ver Reporte</button>
</body>
</html>