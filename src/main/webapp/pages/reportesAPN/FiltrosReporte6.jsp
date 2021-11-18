<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Documentos Fedateados</title>


<script type="text/javascript">
var completarFechaHastaRep6 = function() {
	dijit.byId('divFechaHastaREP6').constraints.min = dijit.byId(
			'divFechaHastaREP6').getValue();
};
var completarFechaDesdeRep6 = function() {
	dijit.byId('divFechaDesdeREP6').constraints.max = dijit.byId(
			'divFechaDesdeREP6').getValue();
};

	dojo.addOnLoad(function() {

		new dijit.form.DateTextBox( {
			id : "divFechaDesdeREP6",
			jsId : "divFechaDesdeREP6",
			name : "divFechaDesdeREP6",
			constraints : {
				datePattern : 'dd/MM/yyyy'
			},
			invalidMessage : "Ingrese fecha de Inicio dd/MM/yyyy",
			required : false,
			onChange : completarFechaDesdeRep6,
			trim : "true",
			value : new Date(),
			width : "10px"
		}, "divFechaDesdeREP6");

		new dijit.form.DateTextBox( {
			id : "divFechaHastaREP6",
			jsId : "divFechaHastaREP6",
			name : "divFechaHastaREP6",
			constraints : {
				datePattern : 'dd/MM/yyyy'
			},
			invalidMessage : "Ingrese fecha Fin dd/MM/yyyy",
			required : false,
			onChange : completarFechaHastaRep6,
			trim : "true",
			value : new Date()
		}, "divFechaHastaREP6");

	});

	function buscar() {
        //variables
        var varinnerhtml = "";
        var correctoRep1 = true;
        var cont=0;
		var sWhereToPrint ="showErrorRep6";
		var fechaDesdeRep6 = dijit.byId("divFechaDesdeREP6").getDisplayedValue();
		var fechaHastaRep6 = dijit.byId("divFechaHastaREP6").getDisplayedValue();
		var servicio = dijit.byId("objDFR.servicio").attr("value");


		if ((fechaDesdeRep6 == "" || fechaDesdeRep6==null) || (fechaHastaRep6 == ""||fechaHastaRep6==null)) {
			cont+=1;
			varinnerhtml+="Error : "+cont+" Ingresar las dos fechas para generar el reporte <BR>";
			correctoRep1 = false;
		}


		//Imprime lista de Errores
		dojo.byId(sWhereToPrint).innerHTML=varinnerhtml;
		//Mostrar pagina
		if (correctoRep1) {
			var paginaIng = "ReporteAPN_listarReporte7.action?area="
					+ "&fechaDesde="
					+ fechaDesdeRep6
					+ "&fechaHasta=" + fechaHastaRep6
					+ "&servicio="  + servicio ;
			Abrir_ventanaIng(paginaIng);
		}
	}

	var resetFormReporte6 = function(frmToReset) {
		var sWhereToPrint ="showErrorRep6";
	    dijit.byId(frmToReset).reset();
	    dojo.byId(sWhereToPrint).innerHTML = "";
	};

	function Abrir_ventanaIng(paginaIng) {
		var opcionesIng = "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=1000, height=700, top=50, left=200";
		var ventanaIng = window.open(paginaIng, "", opcionesIng);
		ventanaIng.moveTo(60, 50);
	};

	 var storeServicioFedatario__ = new dojo.data.ItemFileReadStore({url: "autocompletarServicioFedatario.action?idOperacion=1"});
</script>
</head>
<body>

<div dojoType="dijit.form.Form" id="frmReporte6" jsId="frmReporte6">

        <table BORDER="0">

			 <tr>
			    <td colspan="3">
			    Desde:&nbsp;&nbsp;
			    <div id="divFechaDesdeREP6"></div> &nbsp;&nbsp;&nbsp;&nbsp;
			    Hasta:
			    <div id="divFechaHastaREP6"></div>
			    </td>
             </tr>

              <tr>
                <td colspan="3">&nbsp;</td>
             </tr>


             <tr>
               <td colspan="3">
                  Servicio:<select dojoType="dijit.form.FilteringSelect"
						                id="objDFR.servicio"
						                name="objDFR.servicio"
						                value="<s:property value='storeServicioFedatario__' />"
						                idAttr="id"
						                labelAttr="label"
						                style="width:200px;"
						                required="true"
										hasDownArrow="true"
						                searchAttr="label"
						                store="storeServicioFedatario__"></select>
				</td>
             </tr>

             <tr>
                <td colspan="3">&nbsp;</td>
             </tr>


			 <tr>
				<td width="20%" colspan="3">
					<button dojoType="dijit.form.Button" type="Button" id="btnRep6"
						jsId="btnRep6" iconClass="siged16 sigedSearch16" onClick="buscar"
						showLabel="true">Ver Reporte</button>
				    <button dojoType=dijit.form.Button type="button" onClick="resetFormReporte6('frmReporte6')" iconClass="siged16 sigedEraser16" showLabel="true">Limpiar</button>
				</td>
			 </tr>
			 <tr>
                <td colspan="3"><div id="showErrorRep6" style="color:red;font-weight:bold;">&nbsp;</div></td>
             </tr>
		</table>
</div>
</body>
</html>