<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<style type="text/css">
	td.filaR4{
    	padding-left:0.3em;
        padding-top: 0.3em;
        padding-bottom: 0.3em;
    }
</style>
<script type="text/javascript">
	var completarFechaHastaR4 = function() {
		dijit.byId('fechaHastaR4').constraints.min = dijit.byId('fechaDesdeR4').getValue();
	};
	var completarFechaDesdeR4 = function() {
		dijit.byId('fechaDesdeR4').constraints.max = dijit.byId('fechaHastaR4').getValue();
	};
	function buscarR4(){
		if(dijit.byId("dlgProgresBar")!=null){
        	dijit.byId("dlgProgresBar").show() ;
        }
		dojo.xhrPost({
			form : dojo.byId("frmReporte4"),
			content : {cliente : dijit.byId("usuarioR4").attr("displayedValue")},
			load : function(data){
				var opcionesR4 = "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=1000, height=700, top=50, left=200";
				var ventanaIng = window.open("", "", opcionesR4);
				ventanaIng.document.write(data);
				ventanaIng.moveTo(60, 50);
				if(dijit.byId("dlgProgresBar")!=null){
		        	dijit.byId("dlgProgresBar").hide() ;
		        }
			}
		});
	}

	new dijit.form.FilteringSelect({
	      id             : "usuarioR4",
	      jsId           : "usuarioR4",
	      name           : "objFiltro.usuarioDestinatario",
	      searchAttr     : "label",
	      autoComplete   : false,
	      hasDownArrow   : true,
	      highlightMatch : "all",
	      required       : true,
	      style          : "width:95%;",
	      store : new dojo.data.ItemFileReadStore({url : "autocompletarUsuario.action"}),
	      value : 0
	}, "usuarioR4");


</script>
</head>
<body>
<form id="frmReporte4" action="ReporteAPN_listarReporte4.action">
	<table style="width:100%;">
		<tr>
			<td class="filaR4">Usuario</td>
			<td colspan="3" class="filaR4"><div id="usuarioR4"></div></td>
		</tr>
		<tr>
			<td class="filaR4">Registrado Desde</td>
			<td class="filaR4">
				<input id="fechaDesdeR4" name="objFiltro.fechaDesde" dojoType="dijit.form.DateTextBox" constraints="{datePattern:'dd/MM/yyyy'}" onChange="completarFechaHastaR4()" />
			</td>
			<td class="filaR4">Registrado Hasta</td>
			<td class="filaR4">
				<input id="fechaHastaR4" name="objFiltro.fechaHasta" dojoType="dijit.form.DateTextBox" constraints="{datePattern:'dd/MM/yyyy'}" onChange="completarFechaDesdeR4()" />
			</td>
		</tr>

	</table>
	<p>
		<input dojoType="dijit.form.Button" iconClass="siged16 sigedSearch16" onClick="buscarR4" showLabel="true" label="Buscar"/>
	</p>
</form>
</body>
</html>