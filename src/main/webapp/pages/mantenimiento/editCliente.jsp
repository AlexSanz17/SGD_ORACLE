<?xml version="1.0" encoding="UTF-8" ?>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
   <head>
      <title>Edici&oacute;n de datos de Cliente</title>
      <style type="text/css">
         @import "js/dojo/dijit/themes/soria/soria.css";
         @import "js/dojo/dijit/themes/tundra/tundra.css";
         @import "css/estilo.css";
         @import "css/sigedIconos.css";
      </style>
      <script type="text/javascript" src="js/dojo/dojo/dojo.js" djConfig="parseOnLoad:true, isDebug: false"></script>
      <script type="text/javascript">
         dojo.require("dijit.dijit");
         dojo.require("dijit.form.Button");
         dojo.require("dijit.form.CheckBox");
         dojo.require("dijit.form.FilteringSelect");
         dojo.require("dijit.form.Form");
         dojo.require("dijit.form.TextBox");
         dojo.require("dijit.form.ValidationTextBox");
         dojo.require("dojo.data.ItemFileReadStore");
         dojo.require("dojo.rpc.JsonService");
      </script>
      <script type="text/javascript">
         dojo.addOnLoad(function() {
            new dijit.form.FilteringSelect({
               id             : "objCliente.ubigeoPrincipal.provincia.departamento.iddepartamento",
               jsId           : "objCliente.ubigeoPrincipal.provincia.departamento.iddepartamento",
               name           : "objCliente.ubigeoPrincipal.provincia.departamento.iddepartamento",
               store          : new dojo.data.ItemFileReadStore({
                  url: "listaDepartamentos.action"
               }),
               searchAttr     : "label",
               invalidMessage : "Seleccione un departamento",
               onChange       : new Function("updateStore(this.value, 'provinciaNC1')"),
               required       : true
            }, "fsDepartamentoNC1");

            new dijit.form.FilteringSelect({
               id             : "objCliente.ubigeoPrincipal.provincia.idprovincia",
               jsId           : "objCliente.ubigeoPrincipal.provincia.idprovincia",
               name           : "objCliente.ubigeoPrincipal.provincia.idprovincia",
               store          : new dojo.data.ItemFileReadStore({
                  url: "listaProvincias.action"
               }),
               searchAttr     : "label",
               invalidMessage : "Seleccione una provincia",
               onChange       : new Function("updateStore(this.value, 'distritoNC1')"),
               required       : true
            }, "fsProvinciaNC1");

            new dijit.form.FilteringSelect({
               id             : "objCliente.ubigeoPrincipal.iddistrito",
               jsId           : "objCliente.ubigeoPrincipal.iddistrito",
               name           : "objCliente.ubigeoPrincipal.iddistrito",
               store          : new dojo.data.ItemFileReadStore({
                  url: "listaDistritos.action"
               }),
               searchAttr     : "label",
               invalidMessage : "Seleccione un distrito",
               required       : true
            }, "fsDistritoNC1");

            new dijit.form.FilteringSelect({
               id             : "objCliente.ubigeoAlternativo.provincia.departamento.iddepartamento",
               jsId           : "objCliente.ubigeoAlternativo.provincia.departamento.iddepartamento",
               name           : "objCliente.ubigeoAlternativo.provincia.departamento.iddepartamento",
               store          : new dojo.data.ItemFileReadStore({
                  url: "listaDepartamentos.action"
               }),
               searchAttr     : "label",
               invalidMessage : "Seleccione un departamento",
               onChange       : new Function("updateStore(this.value, 'provinciaNC2')"),
               required       : false
            }, "fsDepartamentoNC2");

            new dijit.form.FilteringSelect({
               id             : "objCliente.ubigeoAlternativo.provincia.idprovincia",
               jsId           : "objCliente.ubigeoAlternativo.provincia.idprovincia",
               name           : "objCliente.ubigeoAlternativo.provincia.idprovincia",
               store          : new dojo.data.ItemFileReadStore({
                  url: "listaProvincias.action"
               }),
               searchAttr     : "label",
               invalidMessage : "Seleccione una provincia",
               onChange       : new Function("updateStore(this.value, 'distritoNC2')"),
               required       : false
            }, "fsProvinciaNC2");

            new dijit.form.FilteringSelect({
               id             : "objCliente.ubigeoAlternativo.iddistrito",
               jsId           : "objCliente.ubigeoAlternativo.iddistrito",
               name           : "objCliente.ubigeoAlternativo.iddistrito",
               store          : new dojo.data.ItemFileReadStore({
                  url: "listaDistritos.action"
               }),
               searchAttr     : "label",
               invalidMessage : "Seleccione un distrito",
               required       : false
            }, "fsDistritoNC2");

            var tipoIdentificacion = "<s:property value='objCliente.tipoIdentificacion.nombre' />";

            if (tipoIdentificacion == undefined || tipoIdentificacion == "") {
               tipoIdentificacion = "RUC";
            }

            dojo.query("input[name='sTipoIdentificacionNC']").forEach(function(node) {
               dijit.byId(node.id).attr("checked", false);

               if (node.value == tipoIdentificacion) {
                  dijit.byId(node.id).attr("checked", true);
                  prepareData(tipoIdentificacion, "show");
               }

               console.log("Nodo [" + node.name + "] ID [" + node.id + "] VALUE [" + node.value + "] CHECKED [" + node.checked + "]");
            });

            var iIdCliente = "<s:property value='objCliente.idCliente' />";

            if (iIdCliente != "") {
               dijit.byId("objCliente.ubigeoPrincipal.provincia.departamento.iddepartamento").attr("required", false);
               dijit.byId("objCliente.ubigeoPrincipal.provincia.idprovincia").attr("required", false);
               dijit.byId("objCliente.ubigeoPrincipal.iddistrito").attr("required", false);
            }
         });

         hideTBODY = function(tbToHide) {
            console.log("[hideTBODY] - tbody a ocultar [" + tbToHide + "]");

            if (dojo.byId(tbToHide)) {
               dojo.byId(tbToHide).style.display = "none";
            } else {
               console.log("[hideTBODY] - [" + tbToHide + "] NO EXISTE");
            }
         }

         showTBODY = function(tbToShow) {
            console.log("[showTBODY] - tbody a mostrar [" + tbToShow + "]");

            if (dojo.byId(tbToShow)) {
               //dojo.byId(tbToShow).style.display = "table-row-group";
               dojo.byId(tbToShow).style.display = "";
            } else {
               console.log("[showTBODY] - [" + tbToShow + "] NO EXISTE");
            }
         }

         createCliente = function() {
            if (!dijit.byId("frmNuevoCliente").validate()) {
               dojo.byId("showErrorCliente").innerHTML="Data inv&aacute;lida... Corregir campos";

               return;
            }

            dojo.byId("showErrorCliente").innerHTML = "&nbsp;";

            var data = dijit.byId("frmNuevoCliente").attr("value");

            if (data.objCliente.idCliente == "") {
               data.objCliente.idCliente = null;
            }

            if (data.objCliente.direccionPrincipal == "") {
               data.objCliente.ubigeoPrincipal = null;
            } else {
               if (data.objCliente.ubigeoPrincipal.iddistrito == "") {
                  data.objCliente.ubigeoPrincipal.provincia.departamento.iddepartamento = "<s:property value='objCliente.ubigeoPrincipal.provincia.departamento.iddepartamento' />";
                  data.objCliente.ubigeoPrincipal.provincia.idprovincia = "<s:property value='objCliente.ubigeoPrincipal.provincia.idprovincia' />";
                  data.objCliente.ubigeoPrincipal.iddistrito = "<s:property value='objCliente.ubigeoPrincipal.iddistrito' />";
               }
            }

            if (data.objCliente.direccionAlternativa == "") {
               data.objCliente.ubigeoAlternativo = null;
            } else {
               if (data.objCliente.ubigeoAlternativo.iddistrito == "") {
                  data.objCliente.ubigeoAlternativo.provincia.departamento.iddepartamento = "<s:property value='objCliente.ubigeoAlternativo.provincia.departamento.iddepartamento' />";
                  data.objCliente.ubigeoAlternativo.provincia.idprovincia = "<s:property value='objCliente.ubigeoAlternativo.provincia.idprovincia' />";
                  data.objCliente.ubigeoAlternativo.iddistrito = "<s:property value='objCliente.ubigeoAlternativo.iddistrito' />";
               }
            }

            console.log("[createCliente] - Intentando enviar [" + dojo.toJson(data, true) + "]");

            var service = new dojo.rpc.JsonService("SMDAction.action");
            var defered = service.saveCliente(data.sTipoIdentificacionNC, data.objCliente);

            defered.addCallback(function(sResult) {
               if (sResult == "NotCreated") {
                  console.log("[createCliente] - No se pudo registrar el cliente");
                  dojo.byId("showErrorCliente").innerHTML="El Nro de Identificacion ya existe en la Base de Datos";
               } else {
                  console.log("[createCliente] - Cliente creado con Nro de Identificacion [" + sResult + "]");
                  self.parent.frames["secondFrame"].location.href = "blank.html";
               }
            });
         }

         deleteCliente = function() {
            var data = dijit.byId("frmNuevoCliente").attr("value");

            if (data.objCliente.idCliente == "") {
               data.objCliente.idCliente = null;
            }

            if (data.objCliente.direccionPrincipal == "") {
               data.objCliente.ubigeoPrincipal = null;
            } else {
               if (data.objCliente.ubigeoPrincipal.iddistrito == "") {
                  data.objCliente.ubigeoPrincipal.provincia.departamento.iddepartamento = "<s:property value='objCliente.ubigeoPrincipal.provincia.departamento.iddepartamento' />";
                  data.objCliente.ubigeoPrincipal.provincia.idprovincia = "<s:property value='objCliente.ubigeoPrincipal.provincia.idprovincia' />";
                  data.objCliente.ubigeoPrincipal.iddistrito = "<s:property value='objCliente.ubigeoPrincipal.iddistrito' />";
               }
            }

            if (data.objCliente.direccionAlternativa == "") {
               data.objCliente.ubigeoAlternativo = null;
            } else {
               if (data.objCliente.ubigeoAlternativo.iddistrito == "") {
                  data.objCliente.ubigeoAlternativo.provincia.departamento.iddepartamento = "<s:property value='objCliente.ubigeoAlternativo.provincia.departamento.iddepartamento' />";
                  data.objCliente.ubigeoAlternativo.provincia.idprovincia = "<s:property value='objCliente.ubigeoAlternativo.provincia.idprovincia' />";
                  data.objCliente.ubigeoAlternativo.iddistrito = "<s:property value='objCliente.ubigeoAlternativo.iddistrito' />";
               }
            }
            
            console.log("[createCliente] - Intentando enviar [" + dojo.toJson(data, true) + "]");

            var service = new dojo.rpc.JsonService("SMDAction.action");
            var defered = service.deleteCliente(data.objCliente);

            defered.addCallback(function(sResult) {
               self.parent.frames["secondFrame"].location.href = "blank.html";
            });
         }

         changeSomeFields = function(sTipoIdentificacion) {
            console.log("Tipo de Identificacion [" + sTipoIdentificacion + "]");

            if (sTipoIdentificacion == undefined || sTipoIdentificacion == "" || sTipoIdentificacion == "RUC") {
               prepareData("DNI", "hide");
               prepareData("RUC", "show");
            } else {
               prepareData("RUC", "hide");
               prepareData("DNI", "show");
            }
         }

         changeStore = function(objFS, sURL) {
            console.log("[changeStore] - Cambiando store del objeto [" + objFS + "]");
            console.log("[changeStore] - Usando action [" + sURL + "]");

            dijit.byId(objFS).setDisplayedValue("Cargando Data...");
            dijit.byId(objFS).setDisabled(true);
            dijit.byId(objFS).store = new dojo.data.ItemFileReadStore({
               url: sURL
            });
            dijit.byId(objFS).setValue("");
            dijit.byId(objFS).setDisplayedValue("");
            dijit.byId(objFS).setDisabled(false);
         }

         prepareData = function(sTipo, sMode) {
            if (sTipo == "RUC") {
               dijit.byId("objCliente.razonSocial").setValue("<s:property value='objCliente.razonSocial' escape='false'/>");
               dijit.byId("objCliente.representanteLegal").setValue("<s:property value='objCliente.representanteLegal' escape='false'/>");

               if (sMode == "show") {
                  dijit.byId("objCliente.razonSocial").attr("required", true);
                  showTBODY("tbDataNCRUC");
               } else {
                  dijit.byId("objCliente.razonSocial").attr("required", false);
                  hideTBODY("tbDataNCRUC");
               }
            } else {
               dijit.byId("objCliente.nombres").setValue("<s:property value='objCliente.nombres' escape='false'/>");
               dijit.byId("objCliente.apellidoPaterno").setValue("<s:property value='objCliente.apellidoPaterno' escape='false'/>");
               dijit.byId("objCliente.apellidoMaterno").setValue("<s:property value='objCliente.apellidoMaterno' escape='false'/>");

               if (sMode == "show") {
                  dijit.byId("objCliente.nombres").attr("required", true);
                  dijit.byId("objCliente.apellidoPaterno").attr("required", true);
                  showTBODY("tbDataNCDNI");
               } else {
                  dijit.byId("objCliente.nombres").attr("required", false);
                  dijit.byId("objCliente.apellidoPaterno").attr("required", false);
                  hideTBODY("tbDataNCDNI");
               }
            }
         }

         updateStore = function(sParametro, sObjeto) {
            console.log("[updateStore] - sParametro [" + sParametro + "] sObjeto [" + sObjeto + "]");

            if (sParametro == undefined || sParametro == "") {
               return;
            }

            if (sObjeto == "provinciaNC1") {
               changeStore("objCliente.ubigeoPrincipal.provincia.idprovincia", "listaProvincias.action?idDepartamento=" + sParametro);
            } else if (sObjeto == "distritoNC1") {
               changeStore("objCliente.ubigeoPrincipal.iddistrito", "listaDistritos.action?idProvincia=" + sParametro);
            } else if (sObjeto == "provinciaNC2") {
               changeStore("objCliente.ubigeoAlternativo.provincia.idprovincia", "listaProvincias.action?idDepartamento=" + sParametro);
            } else if (sObjeto == "distritoNC2") {
               changeStore("objCliente.ubigeoAlternativo.iddistrito", "listaDistritos.action?idProvincia=" + sParametro);
            }
         }

         validateNroIdentificacion = function(constraints) {
            var sRegExp = "\\d+";

            dojo.query("input[name='sTipoIdentificacionNC']").forEach(function(node) {
               if (node.checked) {
                  if (node.value == "RUC") {
                     sRegExp = "\\d{11}";
                  } else if (node.value == "DNI") {
                     sRegExp = "\\d{8}";
                  }
               }
            });

            return sRegExp;
         }
      </script>
   </head>

   <body class="soria">
      <div dojoType="dijit.form.Form" id="frmNuevoCliente" jsId="frmNuevoCliente">
         <table>
            <tr>
               <td colspan="4"><div id="showErrorCliente" style="color:red;font-weight:bold;">&nbsp;</div></td>
            </tr>
            <tr>
               <td colspan="4"></td>
            </tr>
            <tr>
               <td colspan="4">
                  <button dojoType=dijit.form.Button
                          iconClass="siged16 sigedSave16"
                          onClick="createCliente"
                          showLabel="true">Registrar</button>
                  <s:if test="objCliente.idCliente != null">
                     <button dojoType=dijit.form.Button
                             iconClass="siged16 sigedSave16"
                             onClick="deleteCliente"
                             showLabel="true">Eliminar</button>
                  </s:if>
               </td>
            </tr>
            <tr>
               <td colspan="4"></td>
            </tr>
            <tr>
               <td width="20%">Tipo Doc Identidad</td>
               <td width="80%" colspan="3">
                  <input type="radio"
                         dojoType="dijit.form.RadioButton"
                         name="sTipoIdentificacionNC"
                         onClick="changeSomeFields(this.value)"
                         value="RUC" />RUC
                  <input type="radio"
                         dojoType="dijit.form.RadioButton"
                         name="sTipoIdentificacionNC"
                         onClick="changeSomeFields(this.value)"
                         value="DNI" />DNI
                  <input type="radio"
                         dojoType="dijit.form.RadioButton"
                         name="sTipoIdentificacionNC"
                         onClick="changeSomeFields(this.value)"
                         value="Otro" />Otro
               </td>
            </tr>
            <tr>
               <td>Nro Identificaci&oacute;n</td>
               <td colspan="3">
                  <input dojoType="dijit.form.TextBox"
                         type="text"
                         id="objCliente.idCliente"
                         jsId="objCliente.idCliente"
                         name="objCliente.idCliente"
                         value="<s:property value='objCliente.idCliente' />"
                         style="display:none;" />
                  <input type="text"
                         dojoType="dijit.form.ValidationTextBox"
                         id="objCliente.numeroIdentificacionx"
                         name="objCliente.numeroIdentificacionx"
                         value="<s:property value='objCliente.numeroIdentificacionx' />"
                         invalidMessage="Ingrese n&uacute;mero de identificaci&oacute;n. 8 d&iacute;gitos para DNI, 11 para RUC"
                         regExpGen="validateNroIdentificacion"
                         required="true"
                         style="width:100px;" />
               </td>
            </tr>
            <tbody id="tbDataNCRUC" style="display:none;">
               <tr>
                  <td>Raz&oacute;n Social</td>
                  <td colspan="3">
                     <input type="text"
                            dojoType="dijit.form.ValidationTextBox"
                            id="objCliente.razonSocial"
                            name="objCliente.razonSocial"
                            value="<s:property value='objCliente.razonSocial' escape='false'/>"
                            invalidMessage="Ingrese raz&oacute;n social"
                            required="false"
                            style="width:300px;" />
                  </td>
               </tr>
               <tr>
                  <td>Representante Legal</td>
                  <td colspan="3">
                     <input type="text"
                            dojoType="dijit.form.ValidationTextBox"
                            id="objCliente.representanteLegal"
                            name="objCliente.representanteLegal"
                            value="<s:property value='objCliente.representanteLegal' escape='false'/>"
                            invalidMessage="Ingrese representante legal"
                            required="false"
                            style="width:300px;" />
                  </td>
               </tr>
            </tbody>
            <tbody id="tbDataNCDNI" style="display:none;">
               <tr>
                  <td>Nombres</td>
                  <td colspan="3">
                     <input type="text"
                            dojoType="dijit.form.ValidationTextBox"
                            id="objCliente.nombres"
                            name="objCliente.nombres"
                            value="<s:property value='objCliente.nombres' escape='false'/>"
                            invalidMessage="Ingrese nombres"
                            required="false"
                            style="width:300px;" />
                  </td>
               </tr>
               <tr>
                  <td>Apellido Paterno</td>
                  <td colspan="3">
                     <input type="text"
                            dojoType="dijit.form.ValidationTextBox"
                            id="objCliente.apellidoPaterno"
                            name="objCliente.apellidoPaterno"
                            value="<s:property value='objCliente.apellidoPaterno' escape='false'/>"
                            invalidMessage="Ingrese apellido paterno"
                            required="false"
                            style="width:300px;" />
                  </td>
               </tr>
               <tr>
                  <td>Apellido Materno</td>
                  <td colspan="3">
                     <input type="text"
                            dojoType="dijit.form.ValidationTextBox"
                            id="objCliente.apellidoMaterno"
                            name="objCliente.apellidoMaterno"
                            value="<s:property value='objCliente.apellidoMaterno' escape='false'/>"
                            invalidMessage="Ingrese apellido materno"
                            required="false"
                            style="width:300px;" />
                  </td>
               </tr>
            </tbody>
            <tr>
               <td>Direcci&oacute;n:</td>
               <td colspan="3">
                  <div dojoType="dijit.form.ValidationTextBox"
                       id="objCliente.direccionPrincipal"
                       name="objCliente.direccionPrincipal"
                       value="<s:property value='objCliente.direccionPrincipal' escape='false'/>"
                       invalidMessage="Ingrese direcci&oacute;n principal"
                       required="true"
                       style="width:300px;">
                        <script type="dojo/method" event="onBlur">
                           if (dijit.byId("objCliente.direccionAlternativa").attr("value") == "") {
                              dijit.byId("objCliente.direccionAlternativa").attr("value", this.attr("value"));
                           }
                        </script>
                  </div>
               </td>
            </tr>
            <tr>
               <td>Ubigeo:</td>
               <td>
                  Departamento<br />
                  <s:property value="objCliente.ubigeoPrincipal.provincia.departamento.nombre" />
               </td>
               <td>
                  Provincia<br />
                  <s:property value="objCliente.ubigeoPrincipal.provincia.nombre" />
               </td>
               <td>
                  Distrito<br />
                  <s:property value="objCliente.ubigeoPrincipal.nombre" />
               </td>
            </tr>
            <tr>
               <td>&nbsp;</td>
               <td>
                  <div id="fsDepartamentoNC1" name="fsDepartamentoNC1" />
               </td>
               <td>
                  <div id="fsProvinciaNC1" name="fsProvinciaNC1" />
               </td>
               <td>
                  <div id="fsDistritoNC1" name="fsDistritoNC1" />
               </td>
            </tr>
            <tr>
               <td>Direcci&oacute;n Procesal</td>
               <td colspan="3">
                  <div dojoType="dijit.form.ValidationTextBox"
                       id="objCliente.direccionAlternativa"
                       name="objCliente.direccionAlternativa"
                       value="<s:property value='objCliente.direccionAlternativa' />"
                       invalidMessage="Ingrese direcci&oacute;n procesal"
                       required="false"
                       style="width:300px;"></div>
               </td>
            </tr>
            <tr>
               <td>Ubigeo:</td>
               <td>
                  Departamento<br />
                  <s:property value="objCliente.ubigeoAlternativo.provincia.departamento.nombre" />
               </td>
               <td>
                  Provincia<br />
                  <s:property value="objCliente.ubigeoAlternativo.provincia.nombre" />
               </td>
               <td>
                  Distrito<br />
                  <s:property value="objCliente.ubigeoAlternativo.nombre" />
               </td>
            </tr>
            <tr>
               <td>&nbsp;</td>
               <td>
                  <div id="fsDepartamentoNC2" name="fsDepartamentoNC2" />
               </td>
               <td>
                  <div id="fsProvinciaNC2" name="fsProvinciaNC2" />
               </td>
               <td>
                  <div id="fsDistritoNC2" name="fsDistritoNC2" />
               </td>
            </tr>
            <tr>
               <td>Tel&eacute;fono</td>
               <td colspan="3">
                  <input type="text"
                         dojoType="dijit.form.ValidationTextBox"
                         id="objCliente.telefono"
                         name="objCliente.telefono"
                         value="<s:property value='objCliente.telefono' />"
                         invalidMessage="Ingrese tel&eacute;fono"
                         required="false"
                         style="width:200px;" />
               </td>
            </tr>
            <tr>
               <td>Correo</td>
               <td colspan="3">
                  <input type="text"
                         dojoType="dijit.form.ValidationTextBox"
                         id="objCliente.correo"
                         name="objCliente.correo"
                         value="<s:property value='objCliente.correo' />"
                         invalidMessage="Ingrese correo"
                         required="false"
                         style="width:300px;" />
               </td>
            </tr>
         </table>
      </div>
   </body>
</html>