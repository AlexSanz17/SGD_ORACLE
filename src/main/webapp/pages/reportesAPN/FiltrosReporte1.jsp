<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Documentos Emitidos</title>
<%
//carga el el idAreaOrigen por Defecto
String idAreaOrigen = (String)request.getAttribute("idAreaOrigen");
String idAreaDestino = (String)request.getAttribute("idAreaDestino");
%>
<script type="text/javascript">
var completarFechaHastaRep1 = function() {
	dijit.byId('divFechaHastaREP1').constraints.min = dijit.byId(
			'divFechaHastaREP1').getValue();
};
var completarFechaDesdeRep1 = function() {
	dijit.byId('divFechaDesdeREP1').constraints.max = dijit.byId(
			'divFechaDesdeREP1').getValue();
};

	dojo.addOnLoad(function() {

		new dijit.form.FilteringSelect({
			jsId : "area",
			name : "area",
			store : new dojo.data.ItemFileReadStore({
				url : "autocompletarAreaTodos.action"
			}),
			searchAttr : "label",
			labelAttr : "label",
			value: <%=idAreaOrigen%>,
			autoComplete : false,
			hasDownArrow : true,
			highlightMatch : "all",
			invalidMessage : "Seleccione el Area"
		}, "REP1_area");




		new dijit.form.FilteringSelect({
			jsId : "areaDestino",
			name : "areaDestino",
			store : new dojo.data.ItemFileReadStore({
				url : "autocompletarAreaTodos.action"
			}),
			searchAttr : "label",
			labelAttr : "label",
			value: <%=idAreaDestino%>,
			autoComplete : false,
			hasDownArrow : true,
			highlightMatch : "all",
			invalidMessage : "Seleccione el Area"
		}, "REP1_areaDestino");

		new dijit.form.FilteringSelect({
			jsId : "tipodocumento",
			name : "tipodocumento",
			store : new dojo.data.ItemFileReadStore({
				url : "autocompletarTipoDocumentoTodos.action"
			}),
			searchAttr : "label",
			labelAttr : "label",
			value: 0,
			autoComplete : false,
			hasDownArrow : true,
			highlightMatch : "all",
			invalidMessage : "Seleccione el tipo de documento"
		}, "REP1_tipodocumento");

		new dijit.form.FilteringSelect({
			jsId : "prioridad",
			name : "prioridad",
			store : new dojo.data.ItemFileReadStore({
				url : "autocompletarPrioridadTodos.action"
			}),
			searchAttr : "label",
			labelAttr : "label",
			value: -1,
			autoComplete : false,
			hasDownArrow : true,
			highlightMatch : "all",
			invalidMessage : "Seleccione prioridad"
		}, "REP1_prioridad");

		new dijit.form.DateTextBox( {
			id : "divFechaDesdeREP1",
			jsId : "divFechaDesdeREP1",
			name : "divFechaDesdeREP1",
			constraints : {
				datePattern : 'dd/MM/yyyy'
			},
			invalidMessage : "Ingrese fecha de Inicio dd/MM/yyyy",
			required : false,
			onChange : completarFechaDesdeRep1,
			trim : "true",
			value : new Date(),
			width : "10px"
		}, "divFechaDesdeREP1");

		new dijit.form.DateTextBox( {
			id : "divFechaHastaREP1",
			jsId : "divFechaHastaREP1",
			name : "divFechaHastaREP1",
			constraints : {
				datePattern : 'dd/MM/yyyy'
			},
			invalidMessage : "Ingrese fecha Fin dd/MM/yyyy",
			required : false,
			onChange : completarFechaHastaRep1,
			trim : "true",
			value : new Date()
		}, "divFechaHastaREP1");

	});

	function buscar() {
        //variables
		var correctoRep1 = true;
		var varinnerhtml = "";
		var cont=0;
		var sWhereToPrint ="showErrorRep1";
		var area = dijit.byId('REP1_area').getValue();
		var areaDestino = dijit.byId('REP1_areaDestino').getValue();
		var tipodocumento = dijit.byId('REP1_tipodocumento').getValue();
		var prioridad = dijit.byId('REP1_prioridad').getValue();
		var fechaDesdeRep1 = dijit.byId("divFechaDesdeREP1").getDisplayedValue();
		var fechaHastaRep1 = dijit.byId("divFechaHastaREP1").getDisplayedValue();

		//Parametros para busqueda Todos
		if (area == "0" ) {
			area = "Todos";
		}

		if (areaDestino == "0" ) {
			areaDestino = "Todos";
		}

		if (tipodocumento == "0") {
			tipodocumento = "Todos";
		}
		if (prioridad == "-1") {
			prioridad = "Todos";
		}

		//Validacion
		if(area==null||area==""){
			correctoRep1 = false;
			cont+=1;
			varinnerhtml+="Error : " +cont + " Filtro Area Incorrecto <BR>";
		}
		if(tipodocumento==null||tipodocumento==""){
			correctoRep1 = false;
			cont+=1;
			varinnerhtml+="Error : " +cont + " Filtro Tipo Documento Incorrecto <BR>";
		}
		if(prioridad==null||prioridad==""){
			correctoRep1 = false;
			cont+=1;
			varinnerhtml+="Error : "+cont+" Filtro Prioridad Incorrecto <BR>";
		}
		if ((fechaDesdeRep1 == "" || fechaDesdeRep1==null) || (fechaHastaRep1 == ""||fechaHastaRep1==null)) {
			cont+=1;
			varinnerhtml+="Error : "+cont+" Ingresar las dos fechas para generar el reporte <BR>";
			correctoRep1 = false;
		}

		//Imprime lista de Errores
		dojo.byId(sWhereToPrint).innerHTML=varinnerhtml;

		//Mostrar pagina
		if (correctoRep1) {
			var paginaIng = "ReporteAPN_listarReporte1.action?area="
					+ area
					+ "&tipodocumento="
					+ tipodocumento
					+ "&prioridad="
					+ prioridad
					+ "&fechaDesde="
					+ fechaDesdeRep1
					+ "&fechaHasta=" + fechaHastaRep1
					+ "&areaDestino=" + areaDestino;
			Abrir_ventanaIng(paginaIng);
		}
	}

	var resetFormReporte1 = function(frmToReset) {
		var sWhereToPrint ="showErrorRep1";
	    dijit.byId(frmToReset).reset();
	    dojo.byId(sWhereToPrint).innerHTML = "";
	};

	function Abrir_ventanaIng(paginaIng) {
		var opcionesIng = "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=1000, height=700, top=50, left=200";
		var ventanaIng = window.open(paginaIng, "", opcionesIng);
		ventanaIng.moveTo(60, 50);
	};
</script>
</head>
<body>

<div dojoType="dijit.form.Form" id="frmReporte1" jsId="frmReporte1">

        <table BORDER="0">
			 <tr>
				<td width="20%" >&Aacute;rea </td>
				<td width="25%" ><input type="text" id="REP1_area" style="width:200px"/></td>
				<td width="25%">Tipo Documento </td>
				<td width="30%"><input type="text" id="REP1_tipodocumento" width="30%" /></td>
			 </tr>
			 <tr>
			    <td>Fecha Creaci&oacute;n Documento</td>
			    <td colspan="3">
			    Desde:
			    <div id="divFechaDesdeREP1"></div>
			    Hasta:
			    <div id="divFechaHastaREP1"></div>
			    </td>
             </tr>
             <tr>
			    <td width="20%">Prioridad:</td>
			    <td width="25%" colspan="1"><input type="text" id="REP1_prioridad" /></td>

			    <td width="20%" >&Aacute;rea Destino</td>
				<td width="25%" ><input type="text" id="REP1_areaDestino" style="width:200px"/></td>

			 </tr>
			 <tr>
				<td width="20%" colspan="4">
					<button dojoType="dijit.form.Button" type="Button" id="btnRep1"
						jsId="btnRep1" iconClass="siged16 sigedSearch16" onClick="buscar"
						showLabel="true">Ver Reporte</button>
				    <button dojoType=dijit.form.Button type="button" onClick="resetFormReporte1('frmReporte1')" iconClass="siged16 sigedEraser16" showLabel="true">Limpiar</button>
				</td>
			 </tr>
			 <tr>
                <td colspan="4"><div id="showErrorRep1" style="color:red;font-weight:bold;">&nbsp;</div></td>
             </tr>
		</table>
</div>
</body>
</html>