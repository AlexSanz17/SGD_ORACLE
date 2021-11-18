<?xml version="1.0" encoding="UTF-8" ?>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es">
   <head>
      <title>Edici&oacute;n de Numeracion</title>
      <meta http-equiv="Pragma" content="no-cache" />
      <style type="text/css">
         @import "js/dojo/dijit/themes/soria/soria.css";
         @import "css/estilo.css";
         @import "css/sigedIconos.css";
      </style>
      <script type="text/javascript" src="js/dojo/dojo/dojo.js" djConfig="parseOnLoad:true, isDebug:false"></script>
      <script type="text/javascript" src="js/dojo/dijit/dijit.js"></script>
      <script type="text/javascript">
         dojo.require("dijit.form.Button");
         dojo.require("dijit.form.FilteringSelect");
         dojo.require("dijit.form.Form");
         dojo.require("dijit.form.TextBox");
         dojo.require("dojo.data.ItemFileReadStore");
         dojo.require("dojo.rpc.JsonService");

         var iIdUnidad = "<s:property value='objNumeracion.idNumeracion.idunidad' />";
         var iIdTipoDocumento = "<s:property value='objNumeracion.idNumeracion.idtipodocumento' />";
         var NUMERACION_TIPO = "<s:property value='objNumeracion.tiponumeracion' />";
         var NUMERACION_FIRMANTE = "<s:property value='objNumeracion.firmante' />";
         var NUMERACION_DESTINATARIO = "<s:property value='objNumeracion.destinatario' />";
         var storeUnidad = new dojo.data.ItemFileReadStore({url: "autocompletarUnidad.action"});
         var storeTipoDocumento = new dojo.data.ItemFileReadStore({url: "autocompletarAllTipoDocumento.action"});

         console.debug("(editNumeracion.jsp) NUMERACION_TIPO [%s]", NUMERACION_TIPO);
         console.debug("(editNumeracion.jsp) NUMERACION_FIRMANTE [%s]", NUMERACION_FIRMANTE);
         console.debug("(editNumeracion.jsp) NUMERACION_DESTINATARIO [%s]", NUMERACION_DESTINATARIO);
      </script>
      <script type="text/javascript">
         var service = new dojo.rpc.JsonService("SMDAction.action");

         var createNumeracion = function() {
            var data = dijit.byId("frmNumeracion").attr("value");

            dojo.byId("showErrorNumeracion").innerHTML = "&nbsp;";
            console.debug("Data a enviar [%s]", dojo.toJson(data, true));

            service.saveNumeracion(data.objNumeracion, iIdUnidad, iIdTipoDocumento).addCallback(function(sResultado) {
               if (sResultado == "NotCreated") {
                  dojo.byId("showErrorNumeracion").innerHTML = "Esta Numeracion ya ha sido creada, cambie los datos...";
               } else if (sResultado == "error"){
                  dojo.byId("showErrorNumeracion").innerHTML = "Ocurrio un error en el sistema...";
               } else {
                  self.parent.location.href = "inicio.action?sTipoFrame=grid&sTipoMantenimiento=MantMnuNum";
               }
            });
         }

         var validateTipoNumeracion = function() {
            if (NUMERACION_TIPO != "") {
               dojo.query("input[name='objNumeracion.tiponumeracion']").forEach(function(node) {
                  dijit.byId(node.id).attr("checked", false);

                  if (node.value == NUMERACION_TIPO) {
                     dijit.byId(node.id).attr("checked", true);
                     console.info("(validateTipoNumeracion) node [%s] is checked [%s]", node.id, node.checked);
                  }
               });
            }
         };

         var validateFirmante = function() {
            if (NUMERACION_FIRMANTE != "") {
               dojo.query("input[name='objNumeracion.firmante']").forEach(function(node) {
                  dijit.byId(node.id).attr("checked", false);

                  if (node.value == NUMERACION_FIRMANTE) {
                     dijit.byId(node.id).attr("checked", true);
                     console.info("(validateFirmante) node [%s] is checked [%s]", node.id, node.checked);
                  }
               });
            }
         };

         var validateDestinatario = function() {
            if (NUMERACION_DESTINATARIO != "") {
               dojo.query("input[name='objNumeracion.destinatario']").forEach(function(node) {
                  dijit.byId(node.id).attr("checked", false);

                  if (node.value == NUMERACION_DESTINATARIO) {
                     dijit.byId(node.id).attr("checked", true);
                     console.info("(validateDestinatario) node [%s] is checked [%s]", node.id, node.checked);
                  }
               });
            }
         };

         dojo.addOnLoad(function() {
            console.debug("iIdUnidad [%s] iIdTipoDocumento [%s]", iIdUnidad, iIdTipoDocumento);
         });
      </script>
   </head>
   <body class="soria">
      <div dojoType="dijit.form.Form" id="frmNumeracion" jsId="frmNumeracion">
         <table width="100%">
            <tr>
               <td></td>
               <td height="16" colspan="4" align="left" class="plomo"></td>
            </tr>
            <tr align="center">
               <td width="1%" align="center">&nbsp;</td>
               <td width="99%" align="left" colspan="4">
                  <button dojoType=dijit.form.Button
                          iconClass="siged16 sigedSave16"
                          onClick="createNumeracion"
                          showLabel="true">Registrar</button>
               </td>
            </tr>
            <tr>
               <td></td>
               <td height="16" colspan="4" align="left" class="plomo"></td>
            </tr>
            <tr>
               <td height="14" colspan="5">
                  <table width="95%" border="1" align="center" bordercolor="#669BCD" bgcolor="#BFD9F1">
                     <tr>
                        <td height="75">
                           <table width="98%" align="center" >
                              <tr>
                                 <td></td>
                                 <td colspan="5" height="16" align="left" class="label">
                                    <div id="showErrorNumeracion" style="color:red;font-weight:bold;">&nbsp;</div>
                                 </td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left"  style="width: 18%">Area</td>
                                 <td colspan="3" align="left" class="label">
                                    <div dojoType="dijit.form.FilteringSelect"
                                         id="objNumeracion.idNumeracion.idunidad"
                                         jsId="objNumeracion.idNumeracion.idunidad"
                                         name="objNumeracion.idNumeracion.idunidad"
                                         required="false"
                                         searchAttr="label"
                                         store="storeUnidad"
                                         style="width: 60%"
                                         value="<s:property value='objNumeracion.idNumeracion.idunidad' />"></div>
                                 </td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Tipo de Documento</td>
                                 <td colspan="3" align="left" class="label">
                                    <div dojoType="dijit.form.FilteringSelect"
                                         id="objNumeracion.idNumeracion.idtipodocumento"
                                         jsId="objNumeracion.idNumeracion.idtipodocumento"
                                         name="objNumeracion.idNumeracion.idtipodocumento"
                                         required="false"
                                         searchAttr="label"
                                         store="storeTipoDocumento"
                                         style="width: 60%"
                                         value="<s:property value='objNumeracion.idNumeracion.idtipodocumento' />"></div>
                                 </td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">N&uacute;mero Actual</td>
                                 <td colspan="3" align="left" class="label">
                                    <div dojoType="dijit.form.ValidationTextBox"
                                         id="objNumeracion.numeroactual"
                                         jsId="objNumeracion.numeroactual"
                                         name="objNumeracion.numeroactual"
                                         invalidMessage="Ingrese numero actual"
                                         regExp="\d{1,10}"
                                         trim="true"
                                         value="<s:property value='objNumeracion.numeroactual' />"></div>
                                 </td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Formato</td>
                                 <td colspan="3" align="left" class="label">
                                    <div dojoType="dijit.form.TextBox"
                                         type="text"
                                         id="objNumeracion.formatoleft"
                                         jsId="objNumeracion.formatoleft"
                                         name="objNumeracion.formatoleft"
                                         value="<s:property value='objNumeracion.formatoleft' />"></div>
                                    $NUMERO
                                    <div dojoType="dijit.form.TextBox"
                                         type="text"
                                         id="objNumeracion.formatoright"
                                         jsId="objNumeracion.formatoright"
                                         name="objNumeracion.formatoright"
                                         value="<s:property value='objNumeracion.formatoright' />"></div>
                                 </td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Tipo de Numeraci&oacute;n</td>
                                 <td colspan="3" align="left" class="label">
                                    <input dojoType="dijit.form.RadioButton"
                                           type="radio"
                                           id="sNumeracionAutomatica"
                                           name="objNumeracion.tiponumeracion"
                                           value="A" />Autom&aacute;tica
                                    <input dojoType="dijit.form.RadioButton"
                                           type="radio"
                                           id="sNumeracionManual"
                                           name="objNumeracion.tiponumeracion"
                                           value="M" />Manual
                                    <input dojoType="dijit.form.RadioButton"
                                           type="radio"
                                           id="sNumeracionSin"
                                           name="objNumeracion.tiponumeracion"
                                           postCreate="validateTipoNumeracion"
                                           value="S" />Sin
                                 </td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Firmante</td>
                                 <td colspan="3" align="left" class="label">
                                    <input dojoType="dijit.form.RadioButton"
                                           type="radio"
                                           id="sFirmanteCon"
                                           name="objNumeracion.firmante"
                                           value="C" />Con
                                    <input dojoType="dijit.form.RadioButton"
                                           type="radio"
                                           id="sFirmanteSin"
                                           name="objNumeracion.firmante"
                                           postCreate="validateFirmante"
                                           value="S" />Sin
                                 </td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Destinatario</td>
                                 <td colspan="3" align="left" class="label">
                                 	<input dojoType="dijit.form.RadioButton"
                                           type="radio"
                                           id="sDestinatarioExterno"
                                           name="objNumeracion.destinatario"
                                           value="E" />Externo
                                    <input dojoType="dijit.form.RadioButton"
                                           type="radio"
                                           id="sDestinatarioCon"
                                           name="objNumeracion.destinatario"
                                           value="I" />Interno
                                    <input dojoType="dijit.form.RadioButton"
                                           type="radio"
                                           id="sDestinatarioSin"
                                           name="objNumeracion.destinatario"
                                           postCreate="validateDestinatario"
                                           value="S" />Sin Destinatario
                                 </td>
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
               <td height="14"  colspan="3"></td>
            </tr>
         </table>
      </div>
   </body>
</html>
