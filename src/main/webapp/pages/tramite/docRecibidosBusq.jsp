<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="java.text.*"%>
<%@page import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="org.ositran.utils.Expedienfindadvance"%>
<%@ taglib uri="http://ajaxtags.org/tags/ajax" prefix="ajax"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es">
<head>
<title>B&uacute;squeda Avanzada - Datos B&aacute;sicos</title>
<style type="text/css">
@import "js/dojo/dijit/themes/soria/soria.css"; 
@import "css/estilo.css";
.empty {
	color: #A0A0A0;
}
</style>
<script type="text/javascript" src="js/dojo/dojo/dojo.js" djConfig="parseOnLoad:true, isDebug: false"></script>
		<script type="text/javascript" src="js/dojo/dojox/widget/PlaceholderMenuItem.js"></script>
<script type="text/javascript">
		  dojo.require("dijit.Dialog");
		  dojo.require("dijit.ProgressBar");
		</script> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="/siged/css/estilo.css" />
<link rel="stylesheet" type="text/css" media="all"
	href="./css/calendar-green.css" />
<link rel="stylesheet" type="text/css"
	href="/siged/runtime/styles/system/aw.css" />
<link rel="stylesheet" type="text/css" href="/siged/css/ajaxtags.css" />
<link rel="stylesheet" type="text/css" href="/siged/css/displaytag.css" />
<script type="text/javascript" src="/siged/js/switchMenu.js"></script>
<script type="text/javascript" src="/siged/js/form.js"></script>
<script type="text/javascript" src="/siged/js/general.js"></script>
<script type="text/javascript" src="/siged/js/calendar.js"></script>
<script type="text/javascript" src="/siged/js/calendar-es.js"></script>
<script type="text/javascript" src="/siged/js/calendar-setup.js"></script>
<script type="text/javascript" src="/siged/js/si.files.js"></script>
<script type="text/javascript" src="/siged/js/sorttable.js"></script>
<script type="text/javascript" src="/siged/runtime/lib/aw.js"></script>
<%--script type="text/javascript" src="/siged/js/prototype-1.4.0.js"></script>
		<script type="text/javascript" src="/siged/js/scriptaculous.js"></script>
		<script type="text/javascript" src="/siged/js/overlibmws.js"></script>
		<script type="text/javascript" src="/siged/js/ajaxtags-1.2-beta2.js"></script>		
		<script type='text/javascript' src='/siged/dwr/engine.js'></script>
		<script type='text/javascript' src='/siged/dwr/util.js'></script>
		<script type='text/javascript' src='/siged/dwr/interface/toolDwr.js'></script>
		<script type='text/javascript' src='/siged/dwr/interface/Tipoenvio.js'></script>
		<script type='text/javascript' src='/siged/dwr/interface/Empresadestino.js'></script--%>
<script type="text/javascript" src="/siged/js/jquery.js"></script>


<style type="text/css">
.aw-quirks * {
	box-sizing: border-box;
	-moz-box-sizing: border-box;
	-webkit-box-sizing: border-box;
}

body {
	font: 12px Tahoma
}
</style>
<style type="text/css">
#myGrid {
	height: 300px;
	width: 100% !important;
}

#myGrid .aw-row-selector {
	text-align: center
}

#myGrid .aw-column-0 {
	width: 50px;
}

#myGrid .aw-column-1 {
	width: 90px;
}

#myGrid .aw-column-2 {
	width: 120px;
	text-align: center;
}

#myGrid .aw-column-3 {
	width: 100px;
	text-align: center;
}

#myGrid .aw-column-4 {
	width: 110px;
	text-align: center;
}

#myGrid .aw-column-5 {
	width: 100px;
	text-align: center;
}

#myGrid .aw-column-6 {
	width: 120px;
	text-align: center;
}

#myGrid .aw-column-7 {
	width: 100px;
	text-align: center;
}

#myGrid .aw-column-8 {
	width: 150px;
	text-align: center;
}

#myGrid .aw-column-9 {
	width: 50px;
	text-align: center;
}

#myGrid .aw-column-10 {
	width: 50px;
	text-align: center;
}

#myGrid .aw-column-11 {
	width: 100px;
	text-align: center;
}

#myGrid .aw-column-12 {
	width: 150px;
	text-align: center;
}

#myGrid .aw-column-13 {
	width: 150px;
	text-align: center;
}

#myGrid .aw-grid-cell {
	border-right: 1px solid threedlightshadow;
}

#myGrid .aw-grid-row {
	border-bottom: 1px solid threedlightshadow;
}

/* box model fix for strict doctypes, safari */
.aw-strict #myGrid .aw-grid-cell {
	padding-right: 3px;
}

.aw-strict #myGrid .aw-grid-row {
	padding-bottom: 3px;
}

label {
	display: inline;
}

#error {
	color: red;
	font-weight: bold;
	display: none;
}
</style>

<script type="text/javascript">
function trim(cadena)
	{
		for(i=0; i<cadena.length; )
		{
			if(cadena.charAt(i)==" ")
				cadena=cadena.substring(i+1, cadena.length);
			else
				break;
		}

		for(i=cadena.length-1; i>=0; i=cadena.length-1)
		{
			if(cadena.charAt(i)==" ")
				cadena=cadena.substring(0,i);
			else
				break;
		}
		
		return cadena ;
	}

        <%List data = (List) request.getAttribute("Lstinner");%>

      	var myColumns;
        myColumns=["id","Nro. Expediente","Tipo Documento","Nro. Documento","Fecha Creaci&oacute;n","Proceso","&Aacute;rea","Concesionario","Cliente","id"];

        var mydata=[<%Expedienfindadvance edl;
       if (data!=null){
            Date datFecha;
				DateFormat formatFecha = DateFormat
						.getDateInstance(DateFormat.SHORT);
				DateFormat formatHora = DateFormat
						.getTimeInstance(DateFormat.SHORT);
            String Strfecha;
            String strFechaBD;
            String Strfechor;
				for (int i = 0; i < data.size(); i++) {
                //edl.getDoc_fchcreacio()
                edl=(Expedienfindadvance)data.get(i);
                datFecha=edl.getDoc_fchcreacio();
                Strfecha=formatFecha.format(datFecha);
                strFechaBD = formatHora.format(datFecha);
					Strfechor = Strfecha + " " + strFechaBD;%>["<%=edl.getIddocumento()%>","<%=edl.getExp_referencia()%>","<%=edl.getTd_nombre()%>","<%=edl.getDoc_nrdoc()%>","<%=Strfechor%>","<%=edl.getPro_nombre()%>","<%=edl.getU_nombre()%>","<%=(edl.getCn_rzsocial() != null ? edl
									.getCn_rzsocial().replace("\n", " ") : "")%>","<%=(edl.getCli_rzsocial() != null ? edl
									.getCli_rzsocial().replace("\n", " ") : edl
									.getCli_nombres()
									+ " " + edl.getCli_apePaterno())%>","<%=edl.getIdproceso()%>"],<%}
			} else {%>["","","","","","","","","",""],<%}%>
        ]
	


    	 
 
       	
		</script>

<script type="text/javascript">

   var idDocumento =0;
	//	create ActiveWidgets Grid javascript object
	var obj = new AW.UI.Grid;
	obj.setId("myGrid");

	//	define data formats
	var str = new AW.Formats.String;
	var num = new AW.Formats.Number;
    var dat = new AW.Formats.Date;

	obj.setCellFormat([num, str, str,str, str, str]);

	//	provide cells and headers text

	obj.setCellText(mydata);
	obj.setHeaderText(myColumns);

	//	set number of rows/columns
    <% if(data!=null){%>
            obj.setRowCount(<%=data.size() %>);
    <%}else{ %>
            obj.setRowCount(1);
    <%}%>


	obj.setColumnCount(5);

   //Definiendo que columnas es visible

    obj.setSelectionMode("multi-row-marker");

    obj.onSelectedRowsChanged = function(arrayOfRowIndices){ window.status = arrayOfRowIndices;}

    obj.setColumnIndices([1,2,3,4,5,6,7,8,9]);

	//	enable row selectors
	obj.setSelectorVisible(true);
	obj.setSelectorText(function(i){return this.getRowPosition(i)+1});

	//	set headers width/height
	obj.setSelectorWidth(28);
	obj.setHeaderHeight(20);

	//	set row selection
	obj.setSelectionMode("single-row");

	//	set click action handler
	obj.onCellClicked = function(event, col, row)
	{
      var id=this.getCellText(0, row);
      idDocumento = id;
      var idoc=this.getCellText(9, row);
      A('#e1f3ff',id,'/siged/dopermisosbusqueda.action',idoc)

	};

		</script>

<script type="text/javascript">
       function A(c,i,page,iddoc)
       {
    	   page='/siged/doViewDoc.action'	
               parent.frames["secondFrame"].location.href=page+'?iIdDoc='+i+'&iIdPro='+iddoc+'&avisopermiso=1';
       }

       var ObjAnt="row";
       
       function regresar(){ 
    	   history.back();
    	}

      	function seleccionarRadio(){
			radios=document.getElementsByName("tipoBusqueda");
			radios[0].checked=true;
        }

        	$(document).ready(function(){


        		

	        	        
				$("input:radio").get(0).checked=true;

				$("form").submit(function(){

					 var numeroExpediente =   document.getElementById('numeroExpediente' ) ;
		        	 numeroExpediente.value = trim( numeroExpediente.value) ; 

		        	 var numeroDocumento =   document.getElementById('numeroDocumento' ) ;
		        	 numeroDocumento.value = trim( numeroDocumento.value) ;

		        	 var numeroMesaPartes =   document.getElementById('numeroMesaPartes' ) ;
		        	 numeroMesaPartes.value = trim( numeroMesaPartes.value) ;

		        	 var tipoDocumento =   document.getElementById('tipoDocumento' ) ;
		        	 tipoDocumento.value = trim( tipoDocumento.value) ;

		        	
		        	 var concesionario =   document.getElementById('concesionario' ) ;
		        	 concesionario.value = trim( concesionario.value) ;

		        	 var cliente =   document.getElementById('cliente' ) ;
		        	 cliente.value = trim( cliente.value) ;

		        	 var areaDestino =   document.getElementById('areaDestino' ) ;
		        	 areaDestino.value = trim( areaDestino.value) ;	
		              
		        	 var propietario =   document.getElementById('propietario' ) ;
		        	 propietario.value = trim( propietario.value) ;			
		        		
		        	 var proceso =   document.getElementById('proceso' ) ;
		        	 proceso.value = trim( proceso.value) ;		 		
		        		
		        	 var contenido =   document.getElementById('contenido' ) ;
		        	 contenido.value = trim( contenido.value) ;	                    

					
					hayDatos=false;
					$("input:text").each(function(){
						if($(this).val()!=""){
							hayDatos=true;
							
						}
					});
					if(!hayDatos){
						$("#error").text("Debe ingresar al menos un campo a buscar").show().fadeOut(5000);
						return false;
					}
					 
					dijit.byId('dlgProgresBar').show() ;
					return true;
				});
        	});
       
    	</script>



</head>
<body class="soria">
<table>
	<tr>
		<td width="29%" align="left"><font color="669BCD">Bienvenido
		</font><font color="0099FF"><s:property value="#session.nombres" /></font></td>
	</tr>
	<tr>
		<td align="left"><font color="0099FF"><a
			href="<s:url action="doLogout" />" target="_parent">Cerrar
		Sesi&oacute;n</a></font></td>
	</tr>
</table>
<h3>B&uacute;squeda Avanzada</h3>

<table width="100%">
	<tr>
	</tr>

	<tr>
		<td height="2" colspan="6"></td>
	</tr>

	<tr>
		<td height="20" colspan="6" class="titulo" width="99%">
		<table width="52%" border="0" height="20" align="left">
			<tr>
				<td bgcolor="#4481B8" width="53%" align="center" class="tituloRojo">Datos
				Basicos</td>
				<td bgcolor="#fffff" width="1%" align="center" class="tituloRojo">&nbsp;&nbsp;</td>
				<td bgcolor="#BFD9F1" width="53%" align="center" class="tituloRojo"><a
					href="/siged/pages/tramite/docRecibidosBusq_2.jsp">Datos
				Adicionales</a></td>
		</table>
		</td>
	</tr>


	<!--////////////////////////////////////////-->
	<tr id="repHeader1">
		<td height="10" colspan="6"></td>
	</tr>


	<tr>
		<td height="14" colspan="6"><s:form name="frmbusqueda" action="doSearchSuperad"
			method="post">
			<table width="100%" border="0" align="center" bordercolor="#669BCD"
				bgcolor="#BFD9F1">
				<tr>
					<td>
					<table width="748" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="125">Nro. Expediente:</td>
							<td width="173"><s:textfield name="numeroExpediente"
								cssClass="cajaMontoTotal" /></td>
							<td width="55">&nbsp;</td>
							<td width="110">Nro. Documento:</td>
							<td width="151"><s:textfield name="numeroDocumento"
								cssClass="cajaMontoTotal" /></td>
							<td width="22">&nbsp;</td>
							<td width="112">&nbsp;</td>
						</tr>
						<tr>
							<td>Nro. M. Partes:</td>
							<td><s:textfield name="numeroMesaPartes"
								cssClass="cajaMontoTotal" /></td>
							<td>&nbsp;</td>
							<td>Tipo Documento:</td>
							<td><s:textfield name="tipoDocumento"
								cssClass="cajaMontoTotal" /> <span id="indicatortipdoc"
								style="display: none;"><img
								src="/siged/images/indicator.gif" alt="" /></span> <%--s:textfield name="idtipodocumento" cssClass="cajaMontoTotal" /--%>
							<%--ajax:autocomplete
    source="tipodocumento"
    target="idtipodocumento"
    baseUrl="/siged/autocompletar.view?metodo=ObtenerTipoDoc"
    className="autocomplete"
    indicator="indicatortipdoc"
    minimumCharacters="1"
    parser="new ResponseXmlToHtmlListParser()" /--%></td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td>Concesionario:</td>
							<td><s:textfield name="concesionario"
								cssClass="cajaMontoTotal" /></td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td>Cliente:</td>
							<td><s:textfield name="cliente" cssClass="cajaMontoTotal" /></td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td>Area Destino :</td>
							<td><s:textfield name="areaDestino"
								cssClass="cajaMontoTotal" /></td>
							<td>&nbsp;</td>
							<td colspan="2">&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td>Propietario:</td>
							<td><s:textfield name="propietario"
								cssClass="cajaMontoTotal" /></td>
							<td colspan="5"><s:radio theme="simple" name="tipoBusqueda"
								list="#{'AND':'Buscar con todas las coincidencias'}" /> <s:radio
								theme="simple" name="tipoBusqueda"
								list="#{'OR':'Buscar con al menos un criterio'}" /></td>
						</tr>
						<tr>
							<td>Proceso:</td>
							<td><s:textfield name="proceso" cssClass="cajaMontoTotal" />
							<span id="indicatorPro" style="display: none;"><img
								src="/siged/images/indicator.gif" alt="" /></span> <%-- s:textfield id="idproceso" name="idproceso" cssStyle="display:none" / --%>
							<%--ajax:autocomplete
    source="proceso"
    target="idproceso"
    baseUrl="/siged/autocompletar.view?metodo=ObtenerProceso"
    className="autocomplete"
    indicator="indicatorPro"
    minimumCharacters="1"
    parser="new ResponseXmlToHtmlListParser()" /--%></td>
							<td>&nbsp;</td>
							<td colspan="2"><%--s:radio name="tipoBusqueda" list="busquedas" />Buscar con al menos un criterio--%></td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td>Contenido</td>
							<td><s:textfield name="contenido" cssClass="cajaMontoTotal" /></td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td colspan="7" style="text-align: right;"><input
								type="submit"  class="botonGerman" name="Submit" value="Buscar"  />
							</td>
						</tr>
						<tr>
							<td colspan="7"><span id="error"></span></td>
						</tr>
						<tr>
							<td height="14">&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
						</tr>
					</table>
					</td>
				</tr>
			</table>
		</s:form></td>
	</tr>
	<tr>
		<td colspan="6"><script type="text/javascript">
              // Escribimos la grid del active W.
              document.write(obj);
           </script></td>
	</tr>
</table>
<%--@ include file="../util/progressBar.jsp" --%>  
</body>
</html>