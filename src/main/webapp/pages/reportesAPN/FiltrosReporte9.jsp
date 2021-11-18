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

	function buscarR9(){
		var ano = dijit.byId("IG_ano_mtc").attr("value");
		var mes = dijit.byId("IG_mes_mtc").attr("value");
		var tipodocumento = dijit.byId("REP1_tipodocumento_mtc").attr("value");
		var area = dijit.byId("IG_area_mtc").attr("value");
        var bandera = true;

		if (ano=='' || mes == '' || tipodocumento=='' || area==''){
		  alert("Debe seleccionar todos los campos.");
		  bandera = false;
		}

		if (bandera){
			var paginaIng = "ReporteAPN_listarReporte9.action?area=" + area
				+ "&tipodocumento=" + tipodocumento
				+ "&ano=" + ano
				+ "&mes="  + mes ;
		       Abrir_ventanaIng(paginaIng);
		}
	}



	new dijit.form.FilteringSelect({
		jsId : "tipodocumento",
		name : "tipodocumento",
		store : new dojo.data.ItemFileReadStore({
			url : "autocompletarTipoDocumentoMTC.action"
		}),
		searchAttr : "label",
		labelAttr : "label",
		value: 0,
		autoComplete : false,
		hasDownArrow : true,
		highlightMatch : "all",
		invalidMessage : "Seleccione el tipo de documento"
	}, "REP1_tipodocumento_mtc");


	new dijit.form.FilteringSelect({
		jsId : "area",
		name : "area",
		store : new dojo.data.ItemFileReadStore({
			url : "autocompletarAreaMTC.action"
		}),
		searchAttr : "label",
		labelAttr : "label",
		value: 0,
		autoComplete : true,
		hasDownArrow : true,
		style          : "width:10em;",
		highlightMatch : "all",
		invalidMessage : "Seleccione el Area"
	}, "IG_area_mtc");


	new dijit.form.FilteringSelect({
		jsId : "ano",
		name : "ano",
		store : new dojo.data.ItemFileReadStore({
			url : "autocompletarAno.action"
		}),
		searchAttr : "label",
		labelAttr : "label",
		value: 0,
		autoComplete : true,
		hasDownArrow : true,
		style          : "width:10em;",
		highlightMatch : "all",
		invalidMessage : "Seleccione el Año"
	}, "IG_ano_mtc");

	new dijit.form.FilteringSelect({
		jsId : "mes",
		name : "mes",
		store : new dojo.data.ItemFileReadStore({
			url : "autocompletarMes.action"
		}),
		searchAttr : "label",
		labelAttr : "label",
		value: 0,
		autoComplete : true,
		hasDownArrow : true,
		style          : "width:10em;",
		highlightMatch : "all",
		invalidMessage : "Seleccione el Mes"
	}, "IG_mes_mtc");
    /*
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
	}, "IG_area_destino"); */

</script>
</head>
<body>
<form id="frmReporte9" action="ReporteAPN_listarReporte9.action">
	<table style="width:100%;">
		<!--  <tr>
			<td class="filaR3">Proceso</td>
			<td colspan="3" class="filaR3"><div id="procesoR8"></div></td>
		</tr> -->
		<tr>
			<td class="filaR3">Area</td>
			<td colspan="3" class="filaR3"><div id="IG_area_mtc"></div></td>

			<td class="filaR3">Tipo Documento</td>
			<td colspan="3" class="filaR3"><div id="REP1_tipodocumento_mtc"></div></td>
		</tr>

		<tr>
			<td class="filaR3">Año</td>
			<td colspan="3" class="filaR3"><div id="IG_ano_mtc"></div></td>

			<td class="filaR3">Mes</td>
			<td colspan="3" class="filaR3"><div id="IG_mes_mtc"></div></td>


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
		<input dojoType="dijit.form.Button" iconClass="siged16 sigedSearch16" onClick="buscarR9" showLabel="true" label="Buscar"/>
	</p>
</form>
</body>
</html>