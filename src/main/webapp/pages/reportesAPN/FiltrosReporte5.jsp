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
%>
<script type="text/javascript">


	dojo.addOnLoad(function() {

		new dijit.form.FilteringSelect({
			jsId : "area",
			name : "area",
			store : new dojo.data.ItemFileReadStore({
				url : "autocompletarAreaDocumentosPendientes.action"
			}),
			searchAttr : "label",
			labelAttr : "label",
			value: <%=idAreaOrigen%>,
			autoComplete : false,
			hasDownArrow : true,
			highlightMatch : "all",
			invalidMessage : "Seleccione el Area"
		}, "REP5_area");



	});

	function buscar() {
        //variables

		var correctoRep1 = true;
		var varinnerhtml = "";
		var cont=0;
		var sWhereToPrint ="showErrorRep5";
		var area = dijit.byId('REP5_area').getValue();
		var consolidado = dojo.byId("chkConsolidadoReporte4").checked;

		if (consolidado ==true){
			consolidado = "1";
		}else{
			consolidado = "0";
		}


		//Parametros para busqueda Todos
		if (area == "0" ) {
			area = "Todos";
		}
		if(area==null||area==""){
			correctoRep1 = false;
			cont+=1;
			varinnerhtml+="Error : " +cont + " Filtro Area Incorrecto <BR>";
		}

		//Imprime lista de Errores
		dojo.byId(sWhereToPrint).innerHTML=varinnerhtml;
		//Mostrar pagina
		if (correctoRep1) {
			var paginaIng = "ReporteAPN_listarReporte5.action?area=" + area + "&consolidado=" + consolidado;
			Abrir_ventanaIng(paginaIng);
		}
	}

	var resetFormReporte5 = function(frmToReset) {
		var sWhereToPrint ="showErrorRep5";
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
				<td width="2%" >&nbsp;&nbsp;&Aacute;rea </td>
				<td width="25%" ><input type="text" id="REP5_area" style="width:300px"/>&nbsp;&nbsp;&nbsp;
				<input type='checkbox' id='chkConsolidadoReporte4' jsid='chkConsolidadoReporte4' name='chkConsolidadoReporte4' />&nbsp;&nbsp;Consolidado</td>
			 </tr>
			  <tr>
                <td colspan="2"><div id="showErrorRep5" style="color:red;font-weight:bold;">&nbsp;</div></td>
             </tr>
			 <tr>
				<td width="20%" colspan="2">
					<button dojoType="dijit.form.Button" type="Button" id="btnRep1"
						jsId="btnRep1" iconClass="siged16 sigedSearch16" onClick="buscar"
						showLabel="true">Ver Reporte</button>
				    <button dojoType=dijit.form.Button type="button" onClick="resetFormReporte1('frmReporte5')" iconClass="siged16 sigedEraser16" showLabel="true">Limpiar</button>
				</td>
			 </tr>
			 <tr>
                <td colspan="2"><div id="showErrorRep5" style="color:red;font-weight:bold;">&nbsp;</div></td>
             </tr>
		</table>
</div>
</body>
</html>