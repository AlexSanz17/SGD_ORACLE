<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>TUPAs Mes</title>
<%
	String idAreaOrigen = (String)request.getAttribute("idAreaOrigen");
%>
<style type="text/css">
	td.filaR2{
    	padding-left:0.3em;
        padding-top: 0.3em;
        padding-bottom: 0.3em;
    }
</style>
<script type="text/javascript">
	var completarFechaHastaR2 = function() {
		dijit.byId('fechaHastaR2').constraints.min = dijit.byId('fechaDesdeR2').getValue();
	};
	var completarFechaDesdeR2 = function() {
		dijit.byId('fechaDesdeR2').constraints.max = dijit.byId('fechaHastaR2').getValue();
	};
	function buscarR2(){
		if(dijit.byId("dlgProgresBar")!=null){
        	dijit.byId("dlgProgresBar").show() ;
        }
		dojo.xhrPost({
			form : dojo.byId("frmReporte2"),
			load : function(data){
				var opcionesR2 = "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=1000, height=700, top=50, left=200";
				var ventanaIng = window.open("", "", opcionesR2);
				ventanaIng.document.write(data);
				ventanaIng.moveTo(60, 50);
				if(dijit.byId("dlgProgresBar")!=null){
		        	dijit.byId("dlgProgresBar").hide() ;
		        }
			}
		});
	}
	new dijit.form.FilteringSelect({
	      id             : "areaOrigenR2",
	      jsId           : "areaOrigenR2",
	      name           : "objFiltro.areaOrigen",
		  value			 : <%=idAreaOrigen%>,
	      searchAttr     : "label",
	      autoComplete   : false,
	      hasDownArrow   : true,
	      highlightMatch : "all",
	      required       : true,
	      style          : "width:90%;",
	      store : new dojo.data.ItemFileReadStore({url : "autocompletarAreaTodos.action"})
	}, "areaOrigenR2");
	new dijit.form.FilteringSelect({
	      id             : "areaDestinoR2",
	      jsId           : "areaDestinoR2",
	      name           : "objFiltro.areaDestino",
	      searchAttr     : "label",
	      autoComplete   : false,
	      hasDownArrow   : true,
	      highlightMatch : "all",
	      required       : true,
	      style          : "width:90%;",
	      store : new dojo.data.ItemFileReadStore({url : "autocompletarAreaTodos.action"}),
	      value : 0
	}, "areaDestinoR2");
	new dijit.form.FilteringSelect({
	      id             : "tipoDocumentoR2",
	      jsId           : "tipoDocumentoR2",
	      name           : "objFiltro.tipoDocumento",
	      searchAttr     : "label",
	      autoComplete   : false,
	      hasDownArrow   : true,
	      highlightMatch : "all",
	      required       : true,
	      style          : "width:90%;",
	      store : new dojo.data.ItemFileReadStore({url : "autocompletarTipoDocumentoTodos.action"}),
	      value : 0
	}, "tipoDocumentoR2");
	new dijit.form.FilteringSelect({
	      id             : "procesoR2",
	      jsId           : "procesoR2",
	      name           : "objFiltro.proceso",
	      searchAttr     : "label",
	      autoComplete   : false,
	      hasDownArrow   : true,
	      highlightMatch : "all",
	      required       : true,
	      style          : "width:95%;",
	      store : new dojo.data.ItemFileReadStore({url : "autocompletarProcesoVigente.action"}),
	      value : 0
	}, "procesoR2");
	new dijit.form.FilteringSelect({
	      id             : "grupoProcesoR2",
	      jsId           : "grupoProcesoR2",
	      name           : "objFiltro.grupoProceso",
	      searchAttr     : "label",
	      autoComplete   : false,
	      hasDownArrow   : true,
	      highlightMatch : "all",
	      required       : true,
	      style          : "width:90%;",
	      store : new dojo.data.ItemFileReadStore({url : "Parametro_GrupoProcesos.action"}),
	      value : 58
	}, "grupoProcesoR2");
</script>
</head>
<body>

<form id="frmReporte2" action="ReporteAPN_listarReporte2.action">
	<table style="width:100%;">
		<tr>
			<td style="width:10em;" class="filaR2">&Aacute;rea Registro</td>
			<td class="filaR2"><div id="areaOrigenR2"></div></td>
			<td style="width:10em;" class="filaR2">&Aacute;rea Destino</td>
			<td class="filaR2"><div id="areaDestinoR2"></div></td>
		</tr>
		<tr>
			<td class="filaR2">Tipo Documento</td>
			<td class="filaR2">
				<div id="tipoDocumentoR2"></div>
			</td>
			<td class="filaR2">Grupo de proceso</td>
			<td class="filaR2"><div id="grupoProcesoR2"></div></td>
		</tr>
		<tr>
			<td class="filaR2">Proceso</td>
			<td colspan="3" class="filaR2"><div id="procesoR2"></div></td>
		</tr>
		<tr>
			<td class="filaR2">Registrado Desde</td>
			<td class="filaR2">
				<input id="fechaDesdeR2" name="objFiltro.fechaDesde" dojoType="dijit.form.DateTextBox" constraints="{datePattern:'dd/MM/yyyy'}" onChange="completarFechaHastaR2()" />
			</td>
			<td class="filaR2">Registrado Hasta</td>
			<td class="filaR2">
				<input id="fechaHastaR2" name="objFiltro.fechaHasta" dojoType="dijit.form.DateTextBox" constraints="{datePattern:'dd/MM/yyyy'}" onChange="completarFechaDesdeR2()" />
			</td>
		</tr>
	</table>
	<p>
		<input dojoType="dijit.form.Button" iconClass="siged16 sigedSearch16" onClick="buscarR2" showLabel="true" label="Buscar"/>
	</p>
</form>
</body>
</html>