<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<style type="text/css">
	td.filaR3{
    	padding-left:0.3em;
        padding-top: 0.3em;
        padding-bottom: 0.3em;
    }
</style>
<script type="text/javascript">
	/*var completarFechaHastaR3 = function() {
		dijit.byId('fechaHastaR8').constraints.min = dijit.byId('fechaDesdeR8').getValue();
	};
	var completarFechaDesdeR3 = function() {
		dijit.byId('fechaDesdeR8').constraints.max = dijit.byId('fechaHastaR8').getValue();
	};*/

	function Abrir_ventanaIng(paginaIng) {
		var opcionesIng = "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=1000, height=700, top=50, left=200";
		var ventanaIng = window.open(paginaIng, "", opcionesIng);
		ventanaIng.moveTo(60, 50);
	};

	function buscarR10(){
		var area = dijit.byId("IG_area_mp").attr("value");
		var fechaDesdeRep10 = dijit.byId("divFechaDesdeREP10").getDisplayedValue();
		var fechaHastaRep10 = dijit.byId("divFechaHastaREP10").getDisplayedValue();

		var area = dijit.byId("IG_area_mp").attr("value");

		if (area==''){
			  alert("Debe seleccionar todos los campos.");
			  return;
		}

		var paginaIng = "ReporteAPN_listarReporte10.action?area=" + area
				+ "&fechaDesde="
				+ fechaDesdeRep10
				+ "&fechaHasta=" + fechaHastaRep10;
		       Abrir_ventanaIng(paginaIng);

	}

	var completarFechaHastaRep10 = function() {
		dijit.byId('divFechaHastaREP10').constraints.min = dijit.byId(
				'divFechaHastaREP10').getValue();
	};
	var completarFechaDesdeRep10 = function() {
		dijit.byId('divFechaDesdeREP10').constraints.max = dijit.byId(
				'divFechaDesdeREP10').getValue();
	};





	new dijit.form.FilteringSelect({
		jsId : "area",
		name : "area",
		store : new dojo.data.ItemFileReadStore({
			url : "autocompletarAreaMesaParte.action"
		}),
		searchAttr : "label",
		labelAttr : "label",
		value: 0,
		autoComplete : true,
		hasDownArrow : true,
		style          : "width:18em;",
		highlightMatch : "all",
		invalidMessage : "Seleccione el Area"
	}, "IG_area_mp");


	new dijit.form.DateTextBox( {
		id : "divFechaDesdeREP10",
		jsId : "divFechaDesdeREP10",
		name : "divFechaDesdeREP10",
		constraints : {
			datePattern : 'dd/MM/yyyy'
		},
		invalidMessage : "Ingrese fecha de Inicio dd/MM/yyyy",
		required : false,
		onChange : completarFechaDesdeRep10,
		trim : "true",
		value : new Date(),
		width : "10px"
	}, "divFechaDesdeREP10");

	new dijit.form.DateTextBox( {
		id : "divFechaHastaREP10",
		jsId : "divFechaHastaREP10",
		name : "divFechaHastaREP10",
		constraints : {
			datePattern : 'dd/MM/yyyy'
		},
		invalidMessage : "Ingrese fecha Fin dd/MM/yyyy",
		required : false,
		onChange : completarFechaHastaRep10,
		trim : "true",
		value : new Date()
	}, "divFechaHastaREP10");







</script>
</head>
<body>
<form id="frmReporte9" action="ReporteAPN_listarReporte10.action">
	<table border="0" style="width:100%;">
		<!--  <tr>
			<td class="filaR3">Proceso</td>
			<td colspan="3" class="filaR3"><div id="procesoR8"></div></td>
		</tr> -->
		<tr>
			<td class="filaR3">Area</td>
			<td colspan="3" class="filaR3"><div id="IG_area_mp"></div></td>

			<td class="filaR3">&nbsp;</td>
			<td colspan="3" class="filaR3">&nbsp;</td>
		</tr>

		 <tr>
			    <td>Fecha Desde</td>
			    <td colspan="3"> <div id="divFechaDesdeREP10"></div> </td>

			    <td>Fecha Hasta</td>
			    <td colspan="3"> <div id="divFechaHastaREP10"></div> </td>
         </tr>

<!--
		<tr>
			<td class="filaR3">&Aacute;rea Origen</td>
			<td class="filaR3"><input type="text" id="IG_area_origen" style="width:20em;" /></td>
			<td class="filaR3">&Aacute;rea Destino</td>
			<td class="filaR3"><input type="text" id="IG_area_destino" style="width:20em;" /></td>
			</td>
		</tr>  -->
<!--
		<tr>
			<td class="filaR3">Tipo de Documento</td>
			<td  class="filaR3">
				<select dojoType="dijit.form.FilteringSelect" name="objFiltro.tipoDocumento">
					<option value="1" >Internos</option>
					<option value="2" selected>Externos</option>
				</select>
			</td>
		</tr> -->

	</table>
	<p>
		<input dojoType="dijit.form.Button" iconClass="siged16 sigedSearch16" onClick="buscarR10" showLabel="true" label="Buscar"/>
	</p>
</form>
</body>
</html>