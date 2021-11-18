<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="java.text.*"%>
<%@page import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="org.ositran.utils.Expedienfindadvance"%>
<%--@ taglib uri="http://ajaxtags.org/tags/ajax" prefix="ajax"--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es">
<head>
<title>B&uacute;squeda Avanzada</title>
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
<%--script language="javascript" src="/siged/js/switchMenu.js"></script>
        <script language="javascript" src="/siged/js/form.js"> </script>
        <script language="Javascript" src="/siged/js/general.js"></script>
        <script language="JavaScript" src="/siged/js/dtreeCarpeta.js"></script--%>
<link rel="stylesheet" type="text/css" media="all"
	href="/siged/css/calendar-green.css" />
<link rel="stylesheet" href="/siged/runtime/styles/system/aw.css" />
<script type="text/javascript" src="/siged/js/calendar.js"></script>
<script type="text/javascript" src="/siged/js/calendar-es.js"></script>
<script type="text/javascript" src="/siged/js/calendar-setup.js"></script>
<script type="text/javascript" src="/siged/runtime/lib/aw.js"></script>
<%--script type="text/javascript" src="/siged/js/si.files.js"></script>
        <script type="text/javascript" src="/siged/js/sorttable.js"></script--%>
<%--script type="text/javascript" src="/siged/js/prototype-1.4.0.js"></script>
        <script type="text/javascript" src="/siged/js/scriptaculous.js"></script>
        <script type="text/javascript" src="/siged/js/overlibmws.js"></script>
        <script type="text/javascript" src="/siged/js/ajaxtags-1.2-beta2.js"></script>
        <link rel="stylesheet" type="text/css" href="/siged/css/ajaxtags.css" />
        <link rel="stylesheet" type="text/css" href="/siged/css/displaytag.css" /--%>
<%--script type="text/javascript" src="/siged/js/dtreeCarpeta.js"></script>
        <script type='text/javascript' src='/siged/dwr/engine.js'> </script>
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

#myGrid {
	height: 160px;     
	width: 1000 !important; 
}

#myGrid .aw-row-selector {
	text-align: center
}

#myGrid .aw-column-0 {
	width: 50px;
}

#myGrid .aw-column-1 {
	width: 100px;
}

#myGrid .aw-column-2 {
	width: 70px;
	text-align: center;
}

#myGrid .aw-column-3 {
	width: 120px;
	text-align: center;
}

#myGrid .aw-column-4 {
	width: 150px;
	text-align: center;
}

#myGrid .aw-column-5 {
	width: 100px;
	text-align: center;
}

#myGrid .aw-column-6 {
	width: 200px;
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
        <%List data = (List) request.getAttribute("Lstinner");%>

            var myColumns;
            myColumns=["id","Nro Expediente","Tipo Doc.","Nro Doc.","Fch. Creacion","Proceso","Area","Concesionario","Cliente","id"];

            var mydata=[<%Expedienfindadvance edl;
			if (data != null) {
				Date datFecha;
				DateFormat formatFecha = DateFormat
						.getDateInstance(DateFormat.SHORT);
				DateFormat formatHora = DateFormat
						.getTimeInstance(DateFormat.SHORT);
				String Strfecha;
				String strFechaBD;
				String Strfechor;
				for (int i = 0; i < data.size(); i++) {
					edl = (Expedienfindadvance) data.get(i);
					datFecha = edl.getDoc_fchcreacio();
					Strfecha = formatFecha.format(datFecha);
					strFechaBD = formatHora.format(datFecha);
					Strfechor = Strfecha + " " + strFechaBD;%>["<%=edl.getIddocumento()%>","<%=edl.getExp_referencia()%>","<%=edl.getTd_nombre()%>","<%=edl.getDoc_nrdoc()%>","<%=Strfechor%>","<%=edl.getPro_nombre()%>","<%=edl.getU_nombre()%>","<%=(edl.getCn_rzsocial() != null ? edl
									.getCn_rzsocial().replace("\n", " ") : "")%>","<%=(edl.getCli_rzsocial() != null ? edl
									.getCli_rzsocial().replace("\n", " ") : edl
									.getCli_nombres()
									+ " "
									+ edl.getCli_apePaterno()
									+ " "
									+ edl.getCli_apePaterno())%>","<%=edl.getIdproceso()%>"],<%}
			} else {%>["","","","","","","","","",""],<%}%>
            ]


       

            var idDocumento =0;
            //	create ActiveWidgets Grid javascript object
            var obj = new AW.UI.Grid;
            obj.setId("myGrid");

            //	define data formats
            var str = new AW.Formats.String;
            var num = new AW.Formats.Number;
            var dat = new AW.Formats.Date;

            obj.setCellFormat([num, str, str,str, str, str, str, str, str, str, str]);

            //	provide cells and headers text

            obj.setCellText(mydata);
            obj.setHeaderText(myColumns);

            //	set number of rows/columns
    <%if (data != null) {%>
        obj.setRowCount(<%=data.size()%>);
    <%} else {%>
        obj.setRowCount(1);
    <%}%>


        obj.setColumnCount(10);

        //Definiendo que columnas es visible

        //obj.setSelectionMode("multi-row-marker");

        obj.onSelectedRowsChanged = function(arrayOfRowIndices){ window.status = arrayOfRowIndices;}

        obj.setColumnIndices([1,2,3,4,5,6,7,8,9]);

        //	enable row selectors
        obj.setSelectorVisible(true);
        obj.setSelectorText(function(i){return this.getRowPosition(i)+1;});

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
            A('#e1f3ff',id,'/siged/dopermisosbusqueda.action',idoc);

        }

        
            function A(c,i,page,iddoc)
            {
                page='/siged/doViewDoc.action';
                parent.frames["secondFrame"].location.href=page+'?iIdDoc='+i+'&iIdPro='+iddoc+'&avisopermiso=1';
            }

            $(document).ready(function(){
				$("input:radio").get(0).checked=true;

				Calendar.setup({
	                inputField     :    "finiexp",      // id del campo de texto
	                ifFormat       :    "%d/%m/%Y",       // formato de la fecha, cuando se escriba en el campo de texto
	                button         :    "lanzador1"   // el id del bot?n que lanzar? el calendario
	            });

	            Calendar.setup({
	                inputField     :    "ffinexp",      // id del campo de texto
	                ifFormat       :    "%d/%m/%Y",       // formato de la fecha, cuando se escriba en el campo de texto
	                button         :    "lanzador2"   // el id del bot?n que lanzar? el calendario
	            });

	        	Calendar.setup({
	                inputField     :    "finidoc",      // id del campo de texto
	                ifFormat       :    "%d/%m/%Y",       // formato de la fecha, cuando se escriba en el campo de texto
	                button         :    "lanzador3"   // el id del bot?n que lanzar? el calendario
	            });

	            Calendar.setup({
	                inputField     :    "ffindoc",      // id del campo de texto
	                ifFormat       :    "%d/%m/%Y",       // formato de la fecha, cuando se escriba en el campo de texto
	                button         :    "lanzador4"   // el id del bot?n que lanzar? el calendario
	            });

				$("form").submit(function(){

					
					 var cliente =   document.getElementById('cliente' ) ;
					 cliente.value = trim( cliente.value) ;    
		        	 
		        	 var numeroExpediente =   document.getElementById('numeroExpediente' ) ;
		        	 numeroExpediente.value = trim( numeroExpediente.value) ; 

		        	 var numeroDocumento =   document.getElementById('numeroDocumento' ) ;
		        	 numeroDocumento.value = trim( numeroDocumento.value) ;

		        	 var numeroMesaPartes =   document.getElementById('numeroMesaPartes' ) ;
		        	 numeroMesaPartes.value = trim( numeroMesaPartes.value) ;

		        	 var tipoDocumento =   document.getElementById('tipoDocumento' ) ;
		        	 tipoDocumento.value = trim( tipoDocumento.value) ;

		        	 var fechaInicioDocumento =   document.getElementById('fechaInicioDocumento' ) ;
		        	 fechaInicioDocumento.value = trim( fechaInicioDocumento.value) ;

		        	 var fechaFinDocumento =   document.getElementById('fechaFinDocumento' ) ;
		        	 fechaFinDocumento.value = trim( fechaFinDocumento.value) ;
		        		
		        	 var fechaInicioExpediente =   document.getElementById('fechaInicioExpediente' ) ;
		        	 fechaInicioExpediente.value = trim( fechaInicioExpediente.value) ;

		        	 var fechaFinExpediente =   document.getElementById('fechaFinExpediente' ) ;
		        	 fechaFinExpediente.value = trim( fechaFinExpediente.value) ;

		        	 var concesionario =   document.getElementById('concesionario' ) ;
		        	 concesionario.value = trim( concesionario.value) ; 

		        	 var direccionCliente =   document.getElementById('direccionCliente' ) ;
		        	 direccionCliente.value = trim( direccionCliente.value) ;

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
						$("#error").text("Debe ingresar al menos un campo a buscar").show().fadeOut(7000);
						return false;
					}

					var inicioDoc=$("#finidoc").val();
					var finDoc=$("#ffindoc").val();
					//var inicioDoc=document.getElementById("finidoc").value;
					//var finDoc=document.getElementById("ffindoc").value;
					if(finDoc!=""){
						if(inicioDoc==""){
							$("#error").text("Si ingresa una fecha de fin debe haber ingresado una de inicio").show().fadeOut(7000);
							return false;
						}
					}
					if(inicioDoc!=""){
						if(finDoc==""){
							$("#error").text("Si ingresa una fecha de inicio debe ingresar una de fin").show().fadeOut(7000);
							return false;
						}
						if(!/^(0[1-9]|1\d|2\d|3[0-1])\/(0[1-9]|1[0-2])\/(19[6-9][6-9]|20\d{2})$/.test(inicioDoc)){
							$("#error").text("Ingrese una fecha válida (dd/mm/aaaa)").show().fadeOut(7000);
							$("#finidoc").focus();
							$("#finidoc").select();
							return false;
						}
						if(!/^(0[1-9]|1\d|2\d|3[0-1])\/(0[1-9]|1[0-2])\/(19[6-9][6-9]|20\d{2})$/.test(finDoc)){
							$("#error").text("Ingrese una fecha válida (dd/mm/aaaa)").show().fadeOut(7000);
							$("#ffindoc").focus();
							$("#ffindoc").select();
							return false;
						}
						var fechaInicioDoc=Date.parse(inicioDoc);
						var fechaFinDoc=Date.parse(finDoc);
						if(fechaInicioDoc>fechaFinDoc){
							$("#error").text("La fecha de inicio debe ser menor que la de fin.").show().fadeOut(7000);
							$("#ffindoc").focus();
							$("#ffindoc").select();
							return false;
						}						
						/*var anoIni=parseInt(inicioDoc.substring(6));
						var anoFin=parseInt(finDoc.substring(6));						
						if(anoIni>anoFin){
							$("#error").text("La fecha de inicio debe ser menor que la de fin.").show().fadeOut(7000);
							$("#ffindoc").focus();
							$("#ffindoc").select();
							return false;
						}
						else{														
							if(anoIni==anoFin){
								var i=3;
								if(inicioDoc.charAt(3)=='0')
									i=4;
								var k=3;
								if(finDoc.charAt(3)=='0')
									k=4;
								var mesIni=parseInt(inicioDoc.substring(i,5));
								var mesFin=parseInt(finDoc.substring(k,5));
								if(mesIni>mesFin){
									$("#error").text("La fecha de inicio debe ser menor que la de fin.").show().fadeOut(7000);
									$("#ffindoc").focus();
									$("#ffindoc").select();
									return false;
								}
								else{									
									if(mesIni==mesfin){
										var i=0;
										if(inicioDoc.charAt(0)=='0')
											i=1;
										var k=0;
										if(finDoc.charAt(0)=='0')
											k=1;
										var diaIni=parseInt(inicioDoc.substring(i,2));
										var diaFin=parseInt(finDoc.substring(k,2));
										if(diaIni>diaFin){
											$("#error").text("La fecha de inicio debe ser menor que la de fin.").show().fadeOut(7000);
											$("#ffindoc").focus();
											$("#ffindoc").select();
											return false;
										}
									}
								}
							}
						}*/						 
					}

					var inicioExp=$("#finiexp").val();
					var finExp=$("#ffinexp").val();
					//var inicioExp=document.getElementById("finiexp").value;
					//var finExp=document.getElementById("ffinexp").value;
					if(finExp!=""){
						if(inicioExp==""){
							$("#error").text("Si ingresa una fecha de fin debe haber ingresado una de inicio").show().fadeOut(7000);
							return false;
						}
					}
					if(inicioExp!=""){
						if(finExp==""){
							$("#error").text("Si ingresa una fecha de inicio debe ingresar una de fin").show().fadeOut(7000);
							return false;
						}
						if(!/^(0[1-9]|1\d|2\d|3[0-1])\/(0[1-9]|1[0-2])\/(19[6-9][6-9]|20\d{2})$/.test(inicioExp)){
							$("#error").text("Ingrese una fecha válida (dd/mm/aaaa)").show().fadeOut(7000);
							$("#finiexp").focus();
							$("#finiexp").select();
							return false;
						}
						if(!/^(0[1-9]|1\d|2\d|3[0-1])\/(0[1-9]|1[0-2])\/(19[6-9][6-9]|20\d{2})$/.test(finExp)){
							$("#error").text("Ingrese una fecha válida (dd/mm/aaaa)").show().fadeOut(7000);
							$("#ffinexp").focus();
							$("#ffinexp").select();
							return false;
						}
						var fechaInicioExp=Date.parse(inicioExp);
						var fechaFinExp=Date.parse(finExp);
						if(fechaInicioExp>fechaFinExp){
							$("#error").text("La fecha de inicio debe ser menor que la de fin.").show().fadeOut(7000);
							$("#ffinexp").focus();
							$("#ffinexp").select();
							return false;
						}
						/*var anoIni=parseInt(inicioExp.substring(6));
						var anoFin=parseInt(finExp.substring(6));						
						if(anoIni>anoFin){
							$("#error").text("La fecha de inicio debe ser menor que la de fin.").show().fadeOut(7000);
							$("#ffinexp").focus();
							$("#ffinexp").select();
							return false;
						}
						else{														
							if(anoIni==anoFin){
								var i=3;
								if(inicioExp.charAt(3)=='0')
									i=4;
								var k=3;
								if(finExp.charAt(3)=='0')
									k=4;
								var mesIni=parseInt(inicioExp.substring(i,5));
								var mesFin=parseInt(finExp.substring(k,5));
								if(mesIni>mesFin){
									$("#error").text("La fecha de inicio debe ser menor que la de fin.").show().fadeOut(7000);
									$("#ffinexp").focus();
									$("#ffinexp").select();
									return false;
								}
								else{									
									if(mesIni==mesfin){
										var i=0;
										if(inicioExp.charAt(0)=='0')
											i=1;
										var k=0;
										if(finExp.charAt(0)=='0')
											k=1;
										var diaIni=parseInt(inicioExp.substring(i,2));
										var diaFin=parseInt(finExp.substring(k,2));
										if(diaIni>diaFin){
											$("#error").text("La fecha de inicio debe ser menor que la de fin.").show().fadeOut(7000);
											$("#ffinexp").focus();
											$("#ffinexp").select();
											return false;
										}
									}
								}
							}
						}*/
					}

					dijit.byId('dlgProgresBar').show() ;    
					return true;
				});

				/*$("#finidoc").change(function(){
					alert(/^(0[1-9]|1\d|2\d|3[0-1])\/(0[1-9]|1[0-2])\/(19[6-9][6-9]|20\d{2})$/.test($(this).val()));
					alert($(this).val());
				});*/

				
				
        	});            


        	function  buscar(){

 		
        				

        	//document.forms['frmbusqueda'].submit();      
				
           }   

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
        	        	        

        </script>



</head>
<%--body>
        <table>
            <tr>
                <td width="29%" align="left">
                    <font color="669BCD">Bienvenido </font><font color="0099FF"><s:property value="#session.nombres" /></font>
                </td>
            </tr>
            <tr>
                <td align="left">
                    <font color="0099FF"><a href="<s:url action="doLogout" />" target="_parent">Cerrar Sesi&oacute;n</a></font>
                </td>
            </tr>
        </table>
        <table width="100%">
            <tr>
            </tr>

            <tr>
                <td height="2"  colspan="6"></td>
            </tr>

            <tr>
                <td height="20"colspan="6" class="titulo" width="99%">
                    <table width="52%" border="0" height="20" align="left">
                        <tr>
                        <td bgcolor="#BFD9F1" width="53%" align="center" class="tituloRojo"><a href="/siged/pages/tramite/docRecibidosBusq.jsp">Datos Basicos</a></td>
                        <td bgcolor="#fffff" width="1%" align="center" class="tituloRojo">&nbsp;&nbsp;</td>
                        <td bgcolor="#4481B8" width="53%" align="center" class="tituloRojo">Datos Adicionales</td>
                    </table>
                </td>
            </tr>


            <!--////////////////////////////////////////-->
            <tr id="repHeader1">
                <td height="10" colspan="6">
                </td>
            </tr>


            <tr >
                <td height="14" colspan="6">
                    <s:form action="doSearchSuperad2" method="post">
                        <table width="100%"  border="1" align="center" bordercolor="#669BCD" bgcolor="#BFD9F1">
                            <tr>
                                <td height="335" ><table width="748" height="251" border="0" cellpadding="0" cellspacing="0">
                                        <tr>
                                            <td width="115">Nro Expediente: </td>
                                            <td width="14">&nbsp;</td>
                                            <td width="182"><input type="text" name="txtnroexpediente" size="20" class="cajaMontoTotal"></td>
                                            <!-- <td width="149">Tip. doc. Correntista</td> -->
                                            <td colspan="3"><table width="288" border="0" cellspacing="0" cellpadding="0">
                                                    <tr>
                                                        <td width="115">Tipo Documento</td>
                                                        <td width="14">&nbsp;</td>
                                                        <td width="180">
                                                            <s:textfield id="tipodocumento" name="tipodocumento" cssClass="cajaMontoTotal"/>
                                                            <span id="indicatortipdoc" style="display:none;"><img src="/siged/images/indicator.gif" /></span>
                                                            <s:textfield id="idtipodocumento" name="idtipodocumento" cssStyle="display:none" />
                                                            <ajax:autocomplete
                                                                source="tipodocumento"
                                                                target="idtipodocumento"
                                                                baseUrl="/siged/autocompletar.view?metodo=ObtenerTipoDoc"
                                                                className="autocomplete"
                                                                indicator="indicatortipdoc"
                                                                minimumCharacters="1"
                                                                parser="new ResponseXmlToHtmlListParser()" />
                                                        </td>
                                                    </tr>
                                            </table></td>
                                        </tr>
                                        <tr>
                                            <td>Nro Documento : </td>
                                            <td>&nbsp;</td>
                                            <td><input type="text" name="txtnrodocumento" size="20" class="cajaMontoTotal" /></td>
                                            <td>Doc. Ident. Concesionario</td>
                                            <td width="131"><input type="text" name="txtdocidecorr" class="cajaMontoTotal" /></td>
                                            <td width="13">&nbsp;</td>
                                            <td width="144">&nbsp;</td>
                                        </tr>
                                        <tr>
                                            <td>Nro M. Partes:</td>
                                            <td>&nbsp;</td>
                                            <td><input type="text" name="txtnromesapartes" size="20" class="cajaMontoTotal" /></td>
                                            <td>Fch. Crea. Documento </td>
                                            <td colspan="3"><table width="288" border="0" cellspacing="0" cellpadding="0">
                                                    <tr>
                                                        <td width="45"><div align="right">desde:</div></td>
                                                        <td width="53"><input type="text" id="finidoc" name="finidoc" maxlength="" size="8" class="cajaMontoTotal"></td>
                                                        <td width="32"><input alt="Calendario" class="cajaFecha" id="lanzador3" value="..." type="button"></td>
                                                        <td width="45"><div align="right">hasta:</div></td>
                                                        <td width="53"><input type="text" id="ffindoc" name="ffindoc" maxlength="" size="8" class="cajaMontoTotal"></td>
                                                        <td width="32"><input alt="Calendario" class="cajaFecha" id="lanzador4" value="..." type="button"></td>
                                                        <td width="28">&nbsp;</td>
                                                    </tr>
                                            </table></td>
                                        </tr>
                                        <tr>
                                            <td>Concesionario:</td>
                                            <td>&nbsp;</td>
                                            <td><input type="text" name="txtcorrentista" class="cajaMontoTotal"></td>
                                            <td>Fch. Crea. Expediente </td>
                                            <td colspan="3"><table width="288" border="0" cellspacing="0" cellpadding="0">
                                                    <tr>
                                                        <td width="45"><div align="right">desde:</div></td>
                                                        <td width="53"><input type="text" id="finiexp" name="finiexp" maxlength="" size="8" class="cajaMontoTotal"></td>
                                                        <td width="32"><input alt="Calendario" class="cajaFecha" id="lanzador1" value="..." type="button"></td>
                                                        <td width="45"><div align="right">hasta:</div></td>
                                                        <td width="53"><input type="text" id="ffinexp" name="ffinexp" maxlength="" size="8" class="cajaMontoTotal"></td>
                                                        <td width="32"><input alt="Calendario" class="cajaFecha" id="lanzador2" value="..." type="button"></td>
                                                        <td width="28">&nbsp;</td>
                                                    </tr>
                                            </table></td>
                                        </tr>
                                        <tr>
                                            <td>Cliente:</td>
                                            <td>&nbsp;</td>
                                            <td><input type="text" name="txtcliente" class="cajaMontoTotal"></td>
                                            <td>Direccion Principal Cliente</td>
                                            <td colspan="3"><input type="text" name="txtdireprinci" size="45" class="cajaMontoTotal"></td>
                                        </tr>
                                        <tr>
                                            <td>Area Destino:</td>
                                            <td>&nbsp;</td>
                                            <td><input type="text" name="txtareadestino" class="cajaMontoTotal"></td>
                                            <td>&nbsp;</td>
                                            <td>&nbsp;</td>
                                            <td>&nbsp;</td>
                                            <td>&nbsp;</td>
                                        </tr>
                                        <tr>
                                            <td>Propietario:</td>
                                            <td>&nbsp;</td>
                                            <td><input type="text" name="txtpropietario" class="cajaMontoTotal"></td>
                                            <td>&nbsp;</td>
                                            <td colspan="3"><input name="tipbus" type="radio" value="And" checked>Buscar Con todas las Coincidencias</td>
                                        </tr>
                                        <tr>
                                            <td>Proceso:</td>
                                            <td>&nbsp;</td>
                                            <td><input type="text" name="txtproceso" class="cajaMontoTotal"></td>
                                            <td>&nbsp;</td>
                                            <td colspan="3"><input name="tipbus" type="radio" value="Or">Buscar con al Menos Un criterio</td>
                                        </tr>
                                        <tr>
                                            <td>Contenido:</td>
                                            <td>&nbsp;</td>
                                            <td><input type="text" name="txtcontenido" class="cajaMontoTotal" width="25"></td>
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
                                            <td><input type="submit" name="Submit" value="Buscar.." class="button"></td>
                                            <td>&nbsp;</td>
                                            <td>&nbsp;</td>
                                            <td>&nbsp;</td>
                                            <td>&nbsp;</td>
                                            <td>&nbsp;</td>
                                            <td>&nbsp;</td>
                                        </tr>
                                </table></td>
                            </tr>
                        </table>
                    </s:form>
                </td>
            </tr>
            <tr>
                <td colspan="6">
                    <script type="text/javascript">
                        // Escribimos la grid del active W.
                        document.write(obj);
                    </script>
                </td>
            </tr>
        </table>
        <script type="text/javascript">
            Calendar.setup({
                inputField     :    "finiexp",      // id del campo de texto
                ifFormat       :    "%d/%m/%Y",       // formato de la fecha, cuando se escriba en el campo de texto
                button         :    "lanzador1"   // el id del bot?n que lanzar? el calendario
            });

            Calendar.setup({
                inputField     :    "ffinexp",      // id del campo de texto
                ifFormat       :    "%d/%m/%Y",       // formato de la fecha, cuando se escriba en el campo de texto
                button         :    "lanzador2"   // el id del bot?n que lanzar? el calendario
            });

        </script>
        <script type="text/javascript">
            Calendar.setup({
                inputField     :    "finidoc",      // id del campo de texto
                ifFormat       :    "%d/%m/%Y",       // formato de la fecha, cuando se escriba en el campo de texto
                button         :    "lanzador3"   // el id del bot?n que lanzar? el calendario
            });

            Calendar.setup({
                inputField     :    "ffindoc",      // id del campo de texto
                ifFormat       :    "%d/%m/%Y",       // formato de la fecha, cuando se escriba en el campo de texto
                button         :    "lanzador4"   // el id del bot?n que lanzar? el calendario
            });

        </script>
    </body--%>
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
				<td bgcolor="#BFD9F1" width="53%" align="center" class="tituloRojo"><a
					href="/siged/pages/tramite/docRecibidosBusq.jsp">Datos
				B&aacute;sicos</a></td>
				<td bgcolor="#fffff" width="1%" align="center" class="tituloRojo">&nbsp;&nbsp;</td>
				<td bgcolor="#4481B8" width="53%" align="center" class="tituloRojo">Datos
				Adicionales</td>
		</table>
		</td>
	</tr>


	<!--////////////////////////////////////////-->
	<tr id="repHeader1">
		<td height="10" colspan="6"></td>
	</tr>


	<tr>
		<td height="14" colspan="6"><s:form name="frmbusqueda" action="doSearchSuperad2"
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
								cssClass="cajaMontoTotal" /> <%--span id="indicatortipdoc" style="display:none;"><img src="/siged/images/indicator.gif" alt="" /></span>
	<s:textfield name="idtipodocumento" cssClass="cajaMontoTotal" /--%> <%--ajax:autocomplete
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
							<td>Fch. Crea. Documento</td>
							<td colspan="6">
							<table width="468" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td style="width: 6%;">
									<div align="right">desde:</div>
									</td>
									<td style="width: 10%;"><s:textfield id="finidoc"
										name="fechaInicioDocumento" size="10"
										cssClass="cajaMontoTotal" /></td>
									<td><input alt="Calendario" class="cajaFecha"
										id="lanzador3" value="&curren;" type="button" /></td>
									<td style="width: 55%;">
									<div align="right">hasta:</div>
									</td>
									<td style="width: 10%;"><s:textfield id="ffindoc"
										name="fechaFinDocumento" size="10" cssClass="cajaMontoTotal" /></td>
									<td><input alt="Calendario" class="cajaFecha"
										id="lanzador4" value="&curren;" type="button" /></td>
								</tr>
							</table>
							</td>
						</tr>
						<tr>
							<td>Fch. Crea. Expediente</td>
							<td colspan="6">
							<table width="468" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td style="width: 6%;">
									<div align="right">desde:</div>
									</td>
									<td style="width: 10%;"><s:textfield id="finiexp"
										name="fechaInicioExpediente" size="10"
										cssClass="cajaMontoTotal" /></td>
									<td><input alt="Calendario" class="cajaFecha"
										id="lanzador1" value="&curren;" type="button" /></td>
									<td style="width: 55%;">
									<div align="right">hasta:</div>
									</td>
									<td style="width: 10%;"><s:textfield id="ffinexp"
										name="fechaFinExpediente" size="10" cssClass="cajaMontoTotal" /></td>
									<td><input alt="Calendario" class="cajaFecha"
										id="lanzador2" value="&curren;" type="button" /></td>
								</tr>
							</table>
							</td>
						</tr>
						<tr>
							<td>Concesionario:</td>
							<td><s:textfield name="concesionario"
								cssClass="cajaMontoTotal" /></td>
							<td>&nbsp;</td>
							<td>Doc. Ident. Concesionario</td>
							<td width="131"><s:textfield name="identidadConcesionario"
								cssClass="cajaMontoTotal" /></td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td>Cliente:</td>
							<td><s:textfield name="cliente" cssClass="cajaMontoTotal" /></td>
							<td>&nbsp;</td>
							<td>Direcci&oacute;n Principal Cliente</td>
							<td colspan="3"><s:textfield name="direccionCliente"
								size="45" cssClass="cajaMontoTotal" /></td>
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
							<%--span id="indicatorPro" style="display:none;"><img src="/siged/images/indicator.gif" alt="" /></span>
	<s:textfield id="idproceso" name="idproceso" cssStyle="display:none" / --%>
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
								type="submit" value="Buscar" class="botonGerman" onclick=""/></td>
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