<?xml version="1.0" encoding="UTF-8" ?>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!--REN Cargo generado al registrar un documento desde Usuario Final ---------------------------------------------------------->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es">
    <head>
        <title>Tramite Documentario</title>
        <%--<link rel="stylesheet" type="text/css" href="css/estilo.css" />
		<script type="text/javascript" src="js/form.js"> </script>
		<script type="text/javascript" src="js/general.js"></script>
		<link rel="stylesheet" type="text/css" media="all "href="css/calendar-green.css" />
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/calendar-es.js"></script>
		<script type="text/javascript" src="js/calendar-setup.js"></script>--%>

        <style type="text/css">
            @media print {
                a {
                    display: none
                }
                div {
                    display: block
                }
                .ver {
                    display: block
                }
                .nover {
                    display: none
                }
            }

            .negrita {
                font-family: Arial, Helvetica, Geneva, SunSans-Regular, sans-serif;
                font-weight: bold;
                font-size: 9pt;
                text-align: right;
            }

            .normal {
                font-family: Arial, Helvetica, Geneva, SunSans-Regular, sans-serif;
                font-size: 9pt;
                text-align: left;
            }

            .titulotext {
                font-family: Arial, Helvetica, Geneva, SunSans-Regular, sans-serif;
                font-weight: bold;
                font-size: 15pt;
                text-align: left;
                padding-top: 40mm;
            }

            #cargo {
                height: 297mm;
                width: 210mm;
                background-color: white;
                color: #333333;
                line-height: 130%;
                padding-top: 0mm;
            }

            .fecha {
                margin: 0px;
                float: left;
                text-align: right;
                padding-left: 0mm;
                width: 148mm;
            }

            .fechadata {
                margin: 0px;
                text-align: left;
                padding-left: 0mm;
                width: 52mm;
            }

            .titulodiv {
                width: 210mm;
                text-align: center;
                padding-top: 5mm;
                padding-bottom: 8mm;
            }

            .titulodiv2 {
                width: 210mm;
                text-align: center;
                padding-bottom: 9px;
                padding-top: 55mm;
            }

            .titulodiv3 {
                width: 210mm;
                text-align: center;
                padding-bottom: 2mm;
                padding-top: 1mm;
            }

            .titulosub {
                width: 210mm;
                text-align: center;
                text-decoration: underline;
            }

            .subdiv1 {
                margin: 0px;
                float: left;
                text-align: left;
                padding-left: 0mm;
                width: 52mm;
            }

            .subdiv2 {
                padding: 0px;
                float: left;
                width: 20mm;
                margin-left: 2mm;
            }

            .subdiv3 {
                padding: 0px;
                margin-left: 60mm;
            }

            .subdiv4 {
                margin: 0px;
                float: left;
                width: 30mm;
                text-align: right;
            }

            .subdiv6 {
                padding: 0px;
                float: left;
                width: 148mm;
                margin-left: 2mm;
            }

            .subdiv8 {
                padding: 0px;
                float: left;
                width: 162mm;
                margin-left: 2mm;
                margin-right: 0mm;
            }
        </style>

        <script type="text/javascript">

            function Abrir_ventanaBuscar (pagina) {
                var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=760, height=530, top=50, left=200";
                window.open(pagina,"",opciones);
            }

            function Abrir_ventanaCargo (pagina) {
                var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=760, height=530, top=50, left=200";
                window.open(pagina,"",opciones);
            }

            function Abrir_pagina (pagina) {
                var opciones="location=mainFrame";
                window.open(pagina,"",opciones);
            }

            function putTextOnTextBox(textToPut){
                document.all.reciveTheText.value = textToPut;
            }
            function regresar(){
                history.back();
            }
        </script>

        <script>
            function impre() {
                muestra_oculta("cargo")  ;
                muestra_oculta("uno")   ;
                print();

            }
            function restore() {
                muestra_oculta("cargo")  ;
                muestra_oculta("uno")   ;
            }

            function muestra_oculta(id){
                if (document.getElementById(id)) { //se obtiene el id
                    var el = document.getElementById(id); //se define la variable "el" igual a nuestro div
                    el.style.display = (el.style.display == 'none') ? 'block' : 'none'; //damos un atributo display:none que oculta el div
                }
            }
            function imprimir(que) {
                var ventana = window.open("", "", "");
                var contenido = "<html> <head>  "+
                    " <style type='text/css'> " +
                    "    @media print { "+
                    "	a {display:none}"+
                    "	div{display:block} "+
                    "	.ver {display:block}"+
                    "	.nover {display:none}"+
                    "	}"+
                    "	.negrita {"+
                    "	font-family: Arial, Helvetica, Geneva, SunSans-Regular, sans-serif ;   "+
                    "	font-weight: bold;"+
                    "	font-size: 9pt;"+
                    "	text-align: right;"+
                    "	}"+
                    "	.normal {"+
                    "	font-family: Arial, Helvetica, Geneva, SunSans-Regular, sans-serif ; "+
                    "	font-size: 9pt;"+
                    "	text-align: left;"+
                    "	}"+
                    "	.titulotext {"+
                    "		font-family: Arial, Helvetica, Geneva, SunSans-Regular, sans-serif ; "+
                    "	font-weight: bold;"+
                    "	font-size: 15pt;"+
                    "	text-align: left;"+
                    "	padding-top: 35mm;"+
                    "	}"+
                    "	#cargo { "+

                    "	height: 230mm;"+
                    "	width: 210mm;"+
                    "	background-color: white;"+
                    "	color: #333333;"+
                    "	line-height: 130%;"+
                    "	padding-top: 0mm; "+
                    "	}"+
                    "	.fecha {"+

                    "	margin: 0px;"+
                    "	float: left;"+
                    "	text-align: right;"+
                    "	padding-left: 0mm;"+
                    "	width: 148mm; "+
                    "	}"+
                    "	.fechadata {"+

                    "	margin: 0px;"+

                    "	text-align: left;"+
                    "	padding-left: 0mm;"+
                    "	width: 52mm; "+
                    "	}"+
                    "	.titulodiv {"+

                    "	width: 180mm;"+
                    "	text-align: center;"+

                    "	padding-top: 5mm;"+
                    "	padding-bottom: 5mm;"+
                    "	}"+
                    "	.titulodiv2 {"+

                    "	width: 180mm;"+
                    "	text-align: center;"+
                    "	padding-bottom: 8px;"+
                    "	padding-top: 50mm;"+
                    "	}"+
                    "	.titulodiv3 {"+

                    "	width: 180mm;"+
                    "	text-align: center;"+
                    "	padding-bottom: 0mm;"+
                    "	padding-top: 0mm;"+
                    "	}"+
                    "	.titulosub {"+

                    "	width: 180mm;"+
                    "	text-align: center;"+
                    "	text-decoration: underline;"+
                    "	}"+
                    "	.subdiv1 {"+

                    "	margin: 0px;"+
                    "	float: left;"+
                    "	text-align: left;"+
                    "	padding-left: 0mm;"+
                    "	width: 42mm; "+
                    "	}"+
                    "	.subdiv2 {"+

                    "	padding: 0px;"+
                    "	float: left;"+
                    "	width: 20mm;"+
                    "	margin-left: 2mm;"+
                    "	}"+
                    "	.subdiv3 {"+

                    "	padding: 0px;"+
                    "	margin-left: 60mm;"+
                    "	}"+
                    "	.subdiv4 {"+

                    "	margin: 0px;"+
                    "	float: left;"+
                    "	width: 30mm;"+
                    "	text-align: right;"+
                    "	}"+

                    "	.subdiv6 {"+

                    "	padding: 0px;"+
                    "	float: left;"+
                    "	width: 125mm;"+
                    "	margin-left: 2mm;"+
                    "	}"+

                    "	.subdiv8 {"+

                    "	padding: 0px;"+
                    "	float: left;"+
                    "	width:  162mm;"+
                    "	margin-left: 2mm; "+
                    "	margin-right: 0mm; "+
                    "	}    "+

                    "	 </head>  </style>  "
                    +"<body onload='window.print();' ><div id='cargo'>" + document.getElementById(que).innerHTML + " </div></body></html>";
                ventana.document.open();
                ventana.document.write(contenido);
                ventana.document.close();
            }
        </script>

        <script>
            function impre(num,num2) {
                document.getElementById(num).className="ver";
                document.getElementById(num2).className="ver";
                document.getElementById(num2).style["display"] ="block";
                print();
                document.getElementById(num).className="nover";
                document.getElementById(num2).className="nover";
                document.getElementById(num2).style["display"] ="none";
            }

            //onafterprint="restore()"
            dojo.addOnLoad(function() {
                dijit.byId('dlgProgresBar').hide() ;
            });
        </script>
        <script type="text/javascript">
         var showCargo = function() {
            var id = window.open("","","width=600,height=490");
            id.location.href = "generateCargo.action?idDocumento=<s:property value='objDD.iIdDocumento' />";
            /*if (dojo.isIE) {
               console.info("Trabajando con Internet Explorer");
               var id = window.open("","","width=600,height=490");
               id.location.href = "generateCargo.action?idDocumento=<s:property value='objDD.iIdDocumento' />";
            } else {
               console.info("Trabajando con Firefox");
               window.location.href = "generateCargo.action?idDocumento=<s:property value='objDD.iIdDocumento' />";
            }*/
         };
         var showCargoTicket = function(){
            if (<s:property value='objDD.idExterno' /> == '1') 
               window.open("generarCargoTicket.action?idDocumento=<s:property value='objDD.iIdDocumento' />", "", "width=600,height=500");
               
            if (<s:property value='objDD.idExterno' /> == '0') 
               window.open("generarCargo.action?idDocumento=<s:property value='objDD.iIdDocumento' />", "", "width=600,height=500");   
         };
      </script>
    </head>
    <body class="barra" topmargin="0" onload=" muestra_oculta('cargo'); "
          leftmargin="0" rigthmargin="0">
        <div id="uno">
            <form>
                <table width="100%">
                    <tr>
                        <td height="14" colspan="3"></td>
                    </tr>
                    <tr>
                        <td height="20" colspan="3" class="titulo">
                            <table width="99%" border="0" height="20" align="center"
                                   bgcolor="#A2C0DC">
                                <tr>
                                    <td align="left" class="titulo">Cargo</td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <s:if test="objDD.idExterno==1">
                        <tr align="center">
                            <td width="1%" align="center">&nbsp;</td>
                            <td width="99%" colspan="2" align="left">
                                <%--<a href="<s:url action="NuevoDocumento_inicio"></s:url>" target="_parent"><img border="0" src="images/aceptar.bmp" alt="Aceptar" /></a>--%>
                                <%-- <a href="#" onclick="imprimir('cargo');return false;"><img border="0" src="images/imprimir.jpg" alt="Imprimir" /></a>--%>
                                <button dojoType="dijit.form.Button"
                                   type="button"
                                   iconClass="siged16 sigedSave16"
                                   onClick="showCargoTicket"
                                   showLabel="true">Imprimir</button>
                            </td>
                        </tr>
                    </s:if>            
                    <tr>
                        <td colspan="3"><br></td>            
                    </tr>        
                    <tr>
                        <td height="14" colspan="3">
                            <table width="95%" border="1" align="center" bordercolor="#669BCD" bgcolor="#BFD9F1">
                                <tr>
                                    <td height="75">
                                        <table width="98%" height="250" align="center">
                                            <tr>
                                                <td width="2%"></td>
                                                <td width="18%" height="5"></td>
                                                <td width="17%"></td>
                                                <td width="15%"></td>
                                                <td width="45%"></td>
                                                <td width="3%"></td>
                                            </tr>
                                            <tr>
                                                <td></td>
                                                <td height="24" align="left">Nro Tr&aacute;mite</td>
                                                <td colspan="3" align="left" class="label"><s:property
                                                        value="objDD.nroTramite" /></td>
                                                <td></td>
                                            </tr>
                                         
                                            
                                            <tr>
                                                <td colspan="6" height="15">
                                                    <hr>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td colspan="6" height="15" class="label">DOCUMENTO</td>
                                            </tr>
                                            <tr>
                                                <td></td>
                                                <td height="24" align="left">Documento</td>
                                                <td colspan="3" align="left" class="label">
                                                    <s:property	value="objDD.strTipoDocumento" /> - <s:property	value="objDD.strNroDocumento" />
                                                </td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td></td>
                                                <td height="25" align="left">Asunto</td>
                                                <td colspan="3" align="left" class="label"><s:property
                                                        value="objDD.strAsunto" /></td>
                                                <td></td>
                                            </tr>
                                            
                                            <tr>
                                                <td></td>
                                                <td height="23" align="left">Fecha Documento</td>
                                                <td align="left" width="17%" class="label"><s:property
                                                        value="objDD.strFechaDocumento" /></td>
                                                <td align="left"></td>
                                                <td align="left" width="45%" class="label"></td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td></td>
                                                <td height="25" align="left">Observaciones</td>
                                                <td colspan="3" align="left" class="label"><s:property
                                                        value="objDD.strObservacion" /></td>
                                                <td></td>
                                            </tr>
                                            
                                            <tr>
                                                <td></td>
                                                <td class="titulo">&nbsp;</td>
                                                <td colspan="4"></td>
                                            </tr>
                                        </table>



                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td height="14" colspan="3"></td>
                    </tr>
                </table>
            </form>
        </div>
        <!-- aca lo que se imprimira -->

        
    </body>
</html>
