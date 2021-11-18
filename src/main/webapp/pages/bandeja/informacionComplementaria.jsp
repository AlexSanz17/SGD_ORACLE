<?xml version="1.0" encoding="UTF-8" ?>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="java.util.List"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>Informacion Principal</title>

        <s:if test="enVentana">
            <%@ include file="/pages/util/progressBar.jsp"%>
            <%-- Desarrollo - Estilos Siged --%>
            <link type="text/css" rel="stylesheet" href="css/styleSiged.css" />
            <link type="text/css" rel="stylesheet" href="js/dojo/css/styleDojo.css" />

            <script type="text/javascript">
                var djConfig = {
                    isDebug: false,
                    parseOnLoad: true
                };
            </script>
            <script type="text/javascript" src="js/dojobuild/dojo/dojo.js"></script>
            <%-- Desarrollo - API Siged --%>
            <script type="text/javascript" src="js/dojobuild/js/requires.js"></script>
            <script type="text/javascript" src="js/dojo/js/commons.js"></script>
        </s:if>

        <link rel="stylesheet" type="text/css" href="css/detalle/qas.css" />
        <style type="text/css">
            @import "css/estilo.css";
        </style>
        <link rel="stylesheet" type="text/css" href="css/estilo.css" />
        <script type="text/javascript" src="js/siged/informacionComplementaria.js"></script>
        <script type="text/javascript">

            var validarDatos = function(){

                if(!validar("codSala")) return false;
                if(!validar("codEstado")) return false;
                if(!validar("fechaSesion")) return false;
                if(!validar("codVocal")) return false;
                if(!validar("codResultado")) return false;
                if(!validar("numeroResolucion")) return false;
                if(!validar("fechaNotiReclamante")) return false;
                if(!validar("fechaNotiConcesionario")) return false;
	
                return true;

            };

            var validar = function(nombre){
                var valor = dojo.byId(nombre).value;
                if(valor==""){
                    alert("Debe completar todos los datos");
                    return false;
                }
                return true;
            };

            var registrarComplementaria = function(){
                //			if(validarDatos()==true){
				
                dojo.xhrPost({
                    form: dojo.byId("frmComplementaria"),
                    mimetype: "text/html",
                    load: function(data) {
                        alert("Se actualizaron correctamente los datos");
                        dijit.byId("tabContainerDetail").selectChild(dijit.byId("contentPaneDatosCargo"));
                    }
                });
                //			}
            };
		
            dojo.addOnLoad(function() {
			
	
                var a=parseInt(<s:date name='documento.expediente.fechacreacion' format='yyyy' />);
                        var m=parseInt(<s:date name='documento.expediente.fechacreacion' format='MM' />);
                var d=parseInt(<s:date name='documento.expediente.fechacreacion' format='dd' />);
                var fecha=new Date(a,m-1,d);
                dijit.byId("documento.expediente.fechacreacion").attr("value",fecha);

                a=parseInt(<s:date name='documento.fechaLimiteAtencion' format='yyyy' />);
                        m=parseInt(<s:date name='documento.fechaLimiteAtencion' format='MM' />);
                d=parseInt(<s:date name='documento.fechaLimiteAtencion' format='dd' />);
                fecha=new Date(a,m-1,d);
                if(!isNaN(a) && !isNaN(m) && !isNaN(d)){
                    dijit.byId("documento.fechaLimiteAtencion").attr("value",fecha);
                }

                a=parseInt(<s:date name='fechaDerivacion' format='yyyy' />);
                        m=parseInt(<s:date name='fechaDerivacion' format='MM' />);
                d=parseInt(<s:date name='fechaDerivacion' format='dd' />);
                fecha=new Date(a,m-1,d);
                if(!isNaN(a) && !isNaN(m) && !isNaN(d)){
                    dijit.byId("fechaDerivacion").attr("value",fecha);
                }

                a=parseInt(<s:date name='fechaSesion' format='yyyy' />);
                        m=parseInt(<s:date name='fechaSesion' format='MM' />);
                d=parseInt(<s:date name='fechaSesion' format='dd' />);
                fecha=new Date(a,m-1,d);
                if(!isNaN(a) && !isNaN(m) && !isNaN(d)){
                    dijit.byId("fechaSesion").attr("value",fecha);
                }

                a=parseInt(<s:date name='fechaNotiReclamante' format='yyyy' />);
                        m=parseInt(<s:date name='fechaNotiReclamante' format='MM' />);
                d=parseInt(<s:date name='fechaNotiReclamante' format='dd' />);
                fecha=new Date(a,m-1,d);
                if(!isNaN(a) && !isNaN(m) && !isNaN(d)){
                    dijit.byId("fechaNotiReclamante").attr("value",fecha);
                }

                a=parseInt(<s:date name='fechaNotiConcesionario' format='yyyy' />);
                        m=parseInt(<s:date name='fechaNotiConcesionario' format='MM' />);
                d=parseInt(<s:date name='fechaNotiConcesionario' format='dd' />);
                fecha=new Date(a,m-1,d);
                if(!isNaN(a) && !isNaN(m) && !isNaN(d)){
                    dijit.byId("fechaNotiConcesionario").attr("value",fecha);
                }

	      						
            });
            var storeSala = new dojo.data.ItemFileReadStore({url: "autocompletarSalaStor.action"});
            var storeEstado = new dojo.data.ItemFileReadStore({url: "autocompletarEstadoStor.action"});
            var storeResultado = new dojo.data.ItemFileReadStore({url: "autocompletarResultadoStor.action"});
            var storeVocal = new dojo.data.ItemFileReadStore({url: "autocompletarVocalStor.action"});


			
        </script>
    </head>
    <body class="soria" style="overflow:auto">
        <s:form  action="doStor_registrarComplementaria" name="frmComplementaria" id="frmComplementaria" method="post">

            <table width="100%">


                <tr>
                    <td height="14" colspan="8">
                        <button dojoType="dijit.form.Button" type="button"
                                id="btnRegistrarTop" jsId="btnRegistrarTop"
                                iconClass="siged16 sigedSave16" showLabel="true"
                                onClick="registrarComplementaria">
						Actualizar Datos</button>
                    </td>
                </tr>
                <tr>
                    <td height="14" colspan="8">
                        <table border="1" align="center" bordercolor="#669BCD" bgcolor="#BFD9F1" width="98%">
                            <tr>
                                <td>
                                    <table>
                                        <tr>
                                            <td style="width: 2%"></td>
                                            <td style="width: 12%"></td>
                                            <td style="width: 18%"></td>
                                            <td style="width: 12%"></td>
                                            <td style="width: 18%"></td>
                                            <td style="width: 12%"></td>
                                            <td style="width: 18%"></td>
                                            <td style="width: 2%"></td>
                                        </tr>
                                        <tr>
                                            <td colspan="8" class="label">DATOS PRINCIPALES</td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td height="25" align="left">Proceso</td>
                                            <td colspan="3">
                                                <div dojoType="dijit.form.TextBox" id="documento.expediente.proceso.descripcion" jsId="documento.expediente.proceso.descripcion" name="documento.expediente.proceso.descripcion" readOnly="true" style="width:420px" trim="true" value="<s:property value='documento.expediente.proceso.descripcion' />"></div>
                                            </td>
                                            <td height="25" align="left">Nro Expediente</td>
                                            <td align="left" class="label">
                                                <div dojoType="dijit.form.TextBox" id="documento.expediente.nroexpediente" jsId="documento.expediente.nroexpediente" name="documento.expediente.nroexpediente" readOnly="true" style="width:150px" trim="true" value="<s:property value='documento.expediente.nroexpediente' />"></div>
                                                <input type="hidden"  id="documento.expediente.idexpediente" name="documento.expediente.idexpediente" value="<s:property value="documento.expediente.idexpediente"/>" />
                                            </td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td height="25" align="left">Remitente</td>
                                            <td colspan="6" class="label">
                                                <div dojoType="dijit.form.TextBox" id="remitenteObservacion" jsId="remitenteObservacion" name="remitenteObservacion" readOnly="true" style="width:420px" trim="true" value="<s:property value='remitenteObservacion'/>"></div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td height="25" align="left">Usuario Registro</td>
                                            <td align="left">
                                                <div dojoType="dijit.form.TextBox" id="usuarioRegistro" jsId="usuarioRegistro" name="usuarioRegistro" readOnly="true" style="width:150px" trim="true" value="<s:property value='usuarioRegistro'/>"></div>
                                            </td>
                                            <td height="25" align="left">Fecha Registro</td>
                                            <td align="left">
                                                <div id="divFechaRegistro"></div>
                                                <s:date id="documento.expediente.fechacreacion" name="documento.expediente.fechacreacion" format="dd/MM/yyyy HH:mm" />
                                            </td>
                                            <td height="25" align="left">Fecha Vencimiento</td>
                                            <td align="left">
                                                <div id="divFechaVencimiento"></div>
                                            </td>
                                            <td></td>
                                        </tr>
                                        <tr>
                                            <td colspan="8" height="15">
                                                <hr />
                                            </td>
                                        </tr>
                                        <tr>
                                            <td colspan="6" height="15" class="label">SITUACION ACTUAL</td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td height="25" align="left">Etapa Actual</td>
                                            <td align="left">
                                                <div dojoType="dijit.form.TextBox" id="etapaActual" jsId="etapaActual" name="etapaActual" readOnly="true" style="width:150px" trim="true" value="<s:property value='etapaActual'/>"></div>
                                            </td>
                                            <td height="25" align="left">Usuario Actual</td>
                                            <td align="left">
                                                <div dojoType="dijit.form.TextBox" id="usuarioActual" jsId="usuarioActual" name="usuarioActual" readOnly="true" style="width:150px" trim="true" value="<s:property value='usuarioLogueado'/>"></div>
                                            </td>
                                            <td height="25" align="left">Fecha Derivaci&oacute;n</td>
                                            <td align="left">
                                                <div id="divFechaDerivacion"></div>
                                            </td>
                                            <td></td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td height="25" align="left">Sala Asignada</td>
                                            <td align="left">
                                                <div dojoType="dijit.form.FilteringSelect"
                                                     store="storeSala"
                                                     idAttr="id"
                                                     labelAttr="label"
                                                     searchAttr="label"
                                                     name="codSala"
                                                     id="codSala"
                                                     editable="false"
                                                     required="true"
                                                     promptMessage="Seleccione una Sala"
                                                     value= "<s:property value="codSala" />"
                                                     size="20" ></div>
                                            </td>
                                            <td height="25" align="left">Estado</td>
                                            <td colspan="4" align="left">
                                                <div dojoType="dijit.form.FilteringSelect"
                                                     store="storeEstado"
                                                     idAttr="id"
                                                     labelAttr="label"
                                                     searchAttr="label"
                                                     name="codEstado"
                                                     id="codEstado"
                                                     editable="false"
                                                     required="true"
                                                     promptMessage="Seleccione un Estado"
                                                     value= "<s:property value="codEstado" />"
                                                     size="20" ></div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td colspan="8" height="15">
                                                <hr />
                                            </td>
                                        </tr>
                                        <tr>
                                            <td colspan="6" height="15" class="label">OTROS DATOS</td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td height="25" align="left">Fecha Sesión</td>
                                            <td colspan="2" align="left" style="padding-left: 82px">
                                                <div id="divFechaSesion"></div>
                                            </td>
                                            <td height="25" align="left">Vocal Asignado</td>
                                            <td colspan="2" align="left">
                                                <div dojoType="dijit.form.FilteringSelect"
                                                     store="storeVocal"
                                                     idAttr="id"
                                                     labelAttr="label"
                                                     searchAttr="label"
                                                     name="codVocal"
                                                     id="codVocal"
                                                     editable="false"
                                                     required="true"
                                                     promptMessage="Seleccione un Vocal"
                                                     value= "<s:property value="codVocal" />"
                                                     size="20" ></div>
                                            </td>
                                            <td></td>
                                        </tr>

                                        <tr>
                                            <td></td>
                                            <td colspan="3" height="25" align="left">Resultado Proyecto Resolución
                                                <div dojoType="dijit.form.FilteringSelect"
                                                     store="storeResultado"
                                                     idAttr="id"
                                                     labelAttr="label"
                                                     searchAttr="label"
                                                     name="codResultado"
                                                     id="codResultado"
                                                     editable="false"
                                                     required="true"
                                                     promptMessage="Seleccione un Resultado"
                                                     value= "<s:property value="codResultado" />"
                                                     size="20" ></div>
                                            </td>

                                            <td height="25" align="left">Nro. Resolucion</td>
                                            <td colspan="2" align="left">
                                                <div dojoType="dijit.form.TextBox" id="numeroResolucion" jsId="numeroResolucion" name="numeroResolucion" style="width:160px" trim="true" value="<s:property value='numeroResolucion'/>"></div>
                                            </td>
                                            <td></td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td colspan="3" height="25" align="left">Fecha Notificacion Reclamante
                                                <div id="divFechaNotiReclamante"></div>
                                            </td>

                                            <td colspan="3" height="25" align="left">Fecha Notificacion Concesionario
                                                <div id="divFechaNotiConcesionario" ></div>
                                            </td>

                                            <td></td>
                                        </tr>


                                    </table>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td width="1%">&nbsp;</td>
                    <td width="99%" colspan="5" align="left">
                        <button dojoType="dijit.form.Button" type="button"
                                id="btnRegistrarBottom" jsId="btnRegistrarBottom"
                                iconClass="siged16 sigedSave16" onClick="registrarComplementaria"
                                showLabel="true">Actualizar Datos</button>
                    </td>
                </tr>
            </table>
        </s:form>




    </body>
</html>