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
	var completarFechaHastaR3 = function() {
		dijit.byId('fechaHastaR3').constraints.min = dijit.byId('fechaDesdeR3').getValue();
	};
	var completarFechaDesdeR3 = function() {
		dijit.byId('fechaDesdeR3').constraints.max = dijit.byId('fechaHastaR3').getValue();
	};
	function buscarR3(){
		if(dijit.byId("dlgProgresBar")!=null){
        	dijit.byId("dlgProgresBar").show() ;
        }
		dojo.xhrPost({
			form : dojo.byId("frmReporte3"),
			content : {cliente : dijit.byId("clienteR3").attr("displayedValue")},
			load : function(data){
				var opcionesR3 = "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=1000, height=700, top=50, left=200";
				var ventanaIng = window.open("", "", opcionesR3);
				ventanaIng.document.write(data);
				ventanaIng.moveTo(60, 50);
				if(dijit.byId("dlgProgresBar")!=null){
		        	dijit.byId("dlgProgresBar").hide() ;
		        }
			}
		});
	}

	new dijit.form.FilteringSelect({
	      id             : "procesoR3",
	      jsId           : "procesoR3",
	      name           : "objFiltro.proceso",
	      searchAttr     : "label",
	      autoComplete   : false,
	      hasDownArrow   : true,
	      highlightMatch : "all",
	      required       : true,
	      style          : "width:95%;",
	      store : new dojo.data.ItemFileReadStore({url : "autocompletarProcesoVigente.action?mode=todos"}),
	      value : 0
	}, "procesoR3");

	new dijit.form.FilteringSelect({
	      id             : "clienteR3",
	      jsId           : "clienteR3",
	      name           : "objFiltro.cliente",
	      searchAttr     : "label",
	      autoComplete   : false,
	      hasDownArrow   : false,
	      highlightMatch : "all",
	      required       : false,
	      style          : "width:95%;",
	      searchDelay    : 500,
	      _onBlur		 : function(){this.value=dijit.byId("clienteR3");},
	      store		     : new dojox.data.QueryReadStore({url: "QRS_autocompletarQRSCliente.action"})
	}, "clienteR3");


	new dijit.form.FilteringSelect({
		jsId : "areaOrigen",
		name : "objFiltro.areaOrigen",
		store : new dojo.data.ItemFileReadStore({
			url : "autocompletarAreaTodos.action"
		}),
		searchAttr : "label",
		labelAttr : "label",
		value: 0,
		autoComplete : true,
		hasDownArrow : true,
		style          : "width:10em;",
		highlightMatch : "all",
		invalidMessage : "Seleccione el Area"
	}, "IG_area_origen");

	new dijit.form.FilteringSelect({
		jsId : "areaDestino",
		name : "objFiltro.areaDestino",
		store : new dojo.data.ItemFileReadStore({
			url : "autocompletarAreaTodos.action"
		}),
		searchAttr : "label",
		labelAttr : "label",
		value: 0,
		autoComplete : true,
		hasDownArrow : true,
		style          : "width:10em;",
		highlightMatch : "all",
		invalidMessage : "Seleccione el Area"
	}, "IG_area_destino");
</script>
</head>
<body>
<form id="frmReporte3" action="ReporteAPN_listarReporte3.action">
	<table style="width:100%;">
		<tr>
			<td class="filaR3">Proceso</td>
			<td colspan="3" class="filaR3"><div id="procesoR3"></div></td>
		</tr>
		<tr>
			<td class="filaR3">Cliente 1</td>
			<td colspan="3" class="filaR3"><div id="clienteR3"></div></td>
		</tr>
		<tr>
			<td class="filaR3">Registrado Desde</td>
			<td class="filaR3">
				<input id="fechaDesdeR3" name="objFiltro.fechaDesde" dojoType="dijit.form.DateTextBox" constraints="{datePattern:'dd/MM/yyyy'}" onChange="completarFechaHastaR3()" />
			</td>
			<td class="filaR3">Registrado Hasta</td>
			<td class="filaR3">
				<input id="fechaHastaR3" name="objFiltro.fechaHasta" dojoType="dijit.form.DateTextBox" constraints="{datePattern:'dd/MM/yyyy'}" onChange="completarFechaDesdeR3()" />
			</td>
		</tr>
		<tr>
			<td class="filaR3">Plazo</td>
			<td  class="filaR3">
				<select dojoType="dijit.form.FilteringSelect" name="filtroPlazo">
					<option value="T" selected>Todos</option>
					<option value="D">Dentro del plazo</option>
					<option value="F">Fuera del plazo</option>
				</select>
			</td>

			<td class="filaR3">Prioridad</td>
			<td  class="filaR3">
				<select dojoType="dijit.form.FilteringSelect" name="objFiltro.prioridad">
					<option value="0" selected>Todos</option>
					<option value="1">Urgente</option>
					<option value="2">Normal</option>
				</select>
			</td>
		</tr>

		<tr>
			<td class="filaR3">&Aacute;rea Origen</td>
			<td class="filaR3"><input type="text" id="IG_area_origen" style="width:20em;" /></td>
			<td class="filaR3">&Aacute;rea Destino</td>
			<td class="filaR3"><input type="text" id="IG_area_destino" style="width:20em;" /></td>
			</td>
		</tr>

		<tr>
			<td class="filaR3">Tipo de Documento</td>
			<td  class="filaR3">
				<select dojoType="dijit.form.FilteringSelect" name="objFiltro.tipoDocumento">
					<option value="1" >Internos</option>
					<option value="2" selected>Externos</option>
				</select>
			</td>
		</tr>

	</table>
	<p>
		<input dojoType="dijit.form.Button" iconClass="siged16 sigedSearch16" onClick="buscarR3" showLabel="true" label="Buscar"/>
	</p>
</form>
</body>
</html>