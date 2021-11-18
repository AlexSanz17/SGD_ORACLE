<?xml version="1.0" encoding="UTF-8"?>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es">
   <head>
      <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
      <title>Modificar Expediente</title>
      <s:if test="#session._USUARIO.rol!=null">
         <style type="text/css">
         @import "css/estilo.css";
         </style>
         <script type="text/javascript" src="js/dojo/dojo/dojo.js" djConfig="parseOnLoad:true, isDebug: false"></script>
         <link rel="stylesheet" type="text/css" href="js/dojo/dijit/themes/soria/soria.css" />
         <script type="text/javascript">
            dojo.require("dijit.form.FilteringSelect" );
            dojo.require("dojo.data.ItemFileReadStore");
            dojo.require("dijit.Dialog");
            dojo.require("dijit.ProgressBar");
         </script>
      </s:if>
      <script type="text/javascript">
         var CLIENTE_TIPOIDENTIFICACION = "<s:property value='objExpediente.cliente.tipoIdentificacion.nombre' />";
         var strProceso = new dojo.data.ItemFileReadStore({url: "autocompletarProceso.action"});
         var strCliente = new dojo.data.ItemFileReadStore({url: "obtenerClientePorTipoIdentificacion.action?sTipoIdentificacion=" + CLIENTE_TIPOIDENTIFICACION});
         var iPrv1;
         var iDst1;
         var iPrv2;
         var iDst2;
      </script>
      <script type="text/javascript">
         var changeStore = function(objFS, sURL) {
            console.log("(changeStore) - Cambiando store del objeto [%s] usando action [%s]", objFS, sURL);

            dijit.byId(objFS).setDisplayedValue("Cargando Data...");
            dijit.byId(objFS).setDisabled(true);
            dijit.byId(objFS).store = new dojo.data.ItemFileReadStore({
               url: sURL
            });
            dijit.byId(objFS).setValue("");
            dijit.byId(objFS).setDisplayedValue("");
            dijit.byId(objFS).setDisabled(false);
         }

         var resetCliente = function() {
            var sTipoIdentificacion = "<s:property value='objExpediente.cliente.tipoIdentificacion.nombre' />";

            dijit.byId("objExpediente.cliente.idCliente").attr("value", "");

            if (sTipoIdentificacion == "RUC") {
               dijit.byId("objExpediente.cliente.razonSocial").attr("value", "");
               dijit.byId("objExpediente.cliente.representanteLegal").attr("value", "");
            } else {
               dijit.byId("objExpediente.cliente.nombres").attr("value", "");
               dijit.byId("objExpediente.cliente.apellidoPaterno").attr("value", "");
               dijit.byId("objExpediente.cliente.apellidoMaterno").attr("value", "");
            }

            dijit.byId("objExpediente.cliente.direccionPrincipal").attr("value", "");
            dijit.byId("objExpediente.cliente.ubigeoPrincipal.provincia.departamento.iddepartamento").attr("value", "");
            dijit.byId("objExpediente.cliente.ubigeoPrincipal.provincia.idprovincia").attr("value", "");
            dijit.byId("objExpediente.cliente.ubigeoPrincipal.iddistrito").attr("value", "");
            dijit.byId("objExpediente.cliente.direccionAlternativa").attr("value", "");
            dijit.byId("objExpediente.cliente.ubigeoAlternativo.provincia.departamento.iddepartamento").attr("value", "");
            dijit.byId("objExpediente.cliente.ubigeoAlternativo.provincia.idprovincia").attr("value", "");
            dijit.byId("objExpediente.cliente.ubigeoAlternativo.iddistrito").attr("value", "");
            dijit.byId("objExpediente.cliente.telefono").attr("value", "");
            dijit.byId("objExpediente.cliente.correo").attr("value", "");
         }

         var updateInfoCliente = function(sNroIdentificacion) {
            console.debug("(updateInfoCliente) - Nro Identificacion [%s]", sNroIdentificacion);
            resetCliente();

            strCliente.fetchItemByIdentity({
               identity: sNroIdentificacion,
               onItem: function(item) {
                  dijit.byId("objExpediente.cliente.idCliente").attr("value", strCliente.getValue(item, "idcliente"));

                  if (strCliente.getValue(item, "tipoidentificacion") == "RUC") {
                     dijit.byId("objExpediente.cliente.razonSocial").attr("value", strCliente.getValue(item, "razonsocial"));
                     dijit.byId("objExpediente.cliente.representanteLegal").attr("value", strCliente.getValue(item, "representantelegal"));
                  } else {
                     dijit.byId("objExpediente.cliente.nombres").attr("value", strCliente.getValue(item, "nombres"));
                     dijit.byId("objExpediente.cliente.apellidoPaterno").attr("value", strCliente.getValue(item, "apellidopaterno"));
                     dijit.byId("objExpediente.cliente.apellidoMaterno").attr("value", strCliente.getValue(item, "apellidomaterno"));
                  }

                  dijit.byId("objExpediente.cliente.direccionPrincipal").attr("value", strCliente.getValue(item, "direccionp"));
                  iPrv1 = strCliente.getValue(item, "idprovincia");
                  iDst1 = strCliente.getValue(item, "iddistrito");
                  dijit.byId("objExpediente.cliente.ubigeoPrincipal.provincia.departamento.iddepartamento").attr("value", strCliente.getValue(item, "iddepartamento"));

                  dijit.byId("objExpediente.cliente.direccionAlternativa").attr("value", strCliente.getValue(item, "direcciona"));
                  iPrv2 = strCliente.getValue(item, "idprovinciaa");
                  iDst2 = strCliente.getValue(item, "iddistritoa");
                  dijit.byId("objExpediente.cliente.ubigeoAlternativo.provincia.departamento.iddepartamento").attr("value", strCliente.getValue(item, "iddepartamentoa"));

                  console.debug("[%d] [%d] [%d]", iPrv2, iDst2, strCliente.getValue(item, "iddepartamentoa"));

                  dijit.byId("objExpediente.cliente.telefono").attr("value", strCliente.getValue(item, "telefono"));
                  dijit.byId("objExpediente.cliente.correo").attr("value", strCliente.getValue(item, "correo"));
               }
            });
         }

         function showProgress(){
            dijit.byId("dlgProgresBar").show() ;
         }

         function UpdateInfoProceso(sProceso){
            strProceso.fetchItemByIdentity({
               identity: sProceso,
               onItem: function(item) {
                  document.getElementById("unidad").value = strProceso.getValue(item, "areadestino");
                  document.getElementById("responsable").value = strProceso.getValue(item, "destinatario");
                  document.getElementById("idresponsable").value = strProceso.getValue(item, "idresponsable");
               }
            });
         }

         updateStore = function(sParametro, sObjeto) {
            console.debug("(updateStore) - sParametro [%s] sObjeto [%s]", sParametro, sObjeto);

            if (sParametro == undefined || sParametro == "") {
               return;
            }

            if (sObjeto == "cliente") {
               resetCliente();
               changeStore("objDocumento.expediente.cliente.numeroIdentificacion", "obtenerClientePorTipoIdentificacion.action?sTipoIdentificacion=" + sParametro);
            } else if (sObjeto == "provincia1") {
               changeStore("objExpediente.cliente.ubigeoPrincipal.provincia.idprovincia", "listaProvincias.action?idDepartamento=" + sParametro);
            } else if (sObjeto == "distrito1") {
               changeStore("objExpediente.cliente.ubigeoPrincipal.iddistrito", "listaDistritos.action?idProvincia=" + sParametro);
            } else if (sObjeto == "provincia2") {
               changeStore("objExpediente.cliente.ubigeoAlternativo.provincia.idprovincia", "listaProvincias.action?idDepartamento=" + sParametro);
            } else if (sObjeto == "distrito2") {
               changeStore("objExpediente.cliente.ubigeoAlternativo.iddistrito", "listaDistritos.action?idProvincia=" + sParametro);
            }
         }

         function mostrarTrazabilidad(){
            var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=no, width=650, height=320, top=50, left=200";
            window.open("goViewSeguimiento.action?iIdDocumento=" + <s:property value='documentoPrincipal' />, "", opciones);
         }

         function  enviarData( url ) {
            var formexp  = document.getElementById("frmExpediente");
            formexp.action  = url+"?k="+Math.random();

            dojo.xhrPost({
               form: dojo.byId("frmExpediente"),
               mimetype: "text/html",
               sync: true,
               preventCache: true,
               load: function(data) {
                  showGridInbox(6);
                  dijit.byId("contentPaneDetail").setContent(data);
               }
            });

         }

         function archivar() {
            //enviarData('doSaveExpedienteInactivar.action')
            var opciones = "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes, width=650, height=440, top=20, left=70";
            var pagina = "Archivar_inicio2.action?idExpediente=" + <s:property value='objExpediente.idexpediente' /> + "&tipoArchivar=archivar";

            window.open(pagina, "", opciones);
         }

         dojo.addOnLoad(function() {
            new dijit.form.FilteringSelect({
               id             : "objExpediente.cliente.ubigeoPrincipal.provincia.departamento.iddepartamento",
               jsId           : "objExpediente.cliente.ubigeoPrincipal.provincia.departamento.iddepartamento",
               name           : "objExpediente.cliente.ubigeoPrincipal.provincia.departamento.iddepartamento",
               store          : new dojo.data.ItemFileReadStore({
                  url: "listaDepartamentos.action"
               }),
               searchAttr     : "label",
               invalidMessage : "Seleccione un departamento",
               onChange       : function() {
                  if (this.value != "") {
                     updateStore(this.value, "provincia1");
                     console.debug("En fsDepartamento1 con valor seteado a [" + this.value + "] iPrv1 "+ iPrv1 + " iDst1 " + iDst1);
                     dijit.byId("objExpediente.cliente.ubigeoPrincipal.provincia.idprovincia").attr("value", iPrv1);
                  }
               },
               required       : false,
               value          : "<s:property value='objExpediente.cliente.ubigeoPrincipal.provincia.departamento.iddepartamento' />"
            }, "fsDepartamento1");

            new dijit.form.FilteringSelect({
               id             : "objExpediente.cliente.ubigeoPrincipal.provincia.idprovincia",
               jsId           : "objExpediente.cliente.ubigeoPrincipal.provincia.idprovincia",
               name           : "objExpediente.cliente.ubigeoPrincipal.provincia.idprovincia",
               store          : new dojo.data.ItemFileReadStore({
                  url: "listaProvincias.action?idDepartamento=" + "<s:property value='objExpediente.cliente.ubigeoPrincipal.provincia.departamento.iddepartamento' />"
               }),
               searchAttr     : "label",
               invalidMessage : "Seleccione una provincia",
               onChange       : function() {
                  if (this.value != "") {
                     updateStore(this.value, "distrito1");
                     console.debug("En fsProvincia1 con valor seteado a [" + this.value + "] iPrv1 "+ iPrv1 + " iDst1 " + iDst1);
                     dijit.byId("objExpediente.cliente.ubigeoPrincipal.iddistrito").attr("value", iDst1);
                  }
               },
               required       : false,
               value          : "<s:property value='objExpediente.cliente.ubigeoPrincipal.provincia.idprovincia' />"
            }, "fsProvincia1");

            new dijit.form.FilteringSelect({
               id             : "objExpediente.cliente.ubigeoPrincipal.iddistrito",
               jsId           : "objExpediente.cliente.ubigeoPrincipal.iddistrito",
               name           : "objExpediente.cliente.ubigeoPrincipal.iddistrito",
               store          : new dojo.data.ItemFileReadStore({
                  url: "listaDistritos.action?idProvincia=" + "<s:property value='objExpediente.cliente.ubigeoPrincipal.provincia.idprovincia' />"
               }),
               searchAttr     : "label",
               invalidMessage : "Seleccione un distrito",
               required       : false,
               value          : "<s:property value='objExpediente.cliente.ubigeoPrincipal.iddistrito' />"
            }, "fsDistrito1");

            new dijit.form.FilteringSelect({
               id             : "objExpediente.cliente.ubigeoAlternativo.provincia.departamento.iddepartamento",
               jsId           : "objExpediente.cliente.ubigeoAlternativo.provincia.departamento.iddepartamento",
               name           : "objExpediente.cliente.ubigeoAlternativo.provincia.departamento.iddepartamento",
               store          : new dojo.data.ItemFileReadStore({
                  url: "listaDepartamentos.action"
               }),
               searchAttr     : "label",
               invalidMessage : "Seleccione un departamento",
               onChange       : function() {
                  if (this.value != "") {
                     updateStore(this.value, "provincia2");
                     console.debug("En fsDepartamento2 con valor seteado a [" + this.value + "] iPrv2 "+ iPrv2 + " iDst2 " + iDst2);
                     dijit.byId("objExpediente.cliente.ubigeoAlternativo.provincia.idprovincia").attr("value", iPrv2);
                  }
               },
               required       : false,
               value          : "<s:property value='objExpediente.cliente.ubigeoAlternativo.provincia.departamento.iddepartamento' />"
            }, "fsDepartamento2");

            new dijit.form.FilteringSelect({
               id             : "objExpediente.cliente.ubigeoAlternativo.provincia.idprovincia",
               jsId           : "objExpediente.cliente.ubigeoAlternativo.provincia.idprovincia",
               name           : "objExpediente.cliente.ubigeoAlternativo.provincia.idprovincia",
               store          : new dojo.data.ItemFileReadStore({
                  url: "listaProvincias.action?idDepartamento=" + "<s:property value='objExpediente.cliente.ubigeoAlternativo.provincia.departamento.iddepartamento' />"
               }),
               searchAttr     : "label",
               invalidMessage : "Seleccione una provincia",
               onChange       : function() {
                  if (this.value != "") {
                     updateStore(this.value, "distrito2");
                     console.debug("En fsProvincia2 con valor seteado a [" + this.value + "] iPrv2 "+ iPrv2 + " iDst2 " + iDst2);
                     dijit.byId("objExpediente.cliente.ubigeoAlternativo.iddistrito").attr("value", iDst2);
                  }
               },
               required       : false,
               value          : "<s:property value='objExpediente.cliente.ubigeoAlternativo.provincia.idprovincia' />"
            }, "fsProvincia2");

            new dijit.form.FilteringSelect({
               id             : "objExpediente.cliente.ubigeoAlternativo.iddistrito",
               jsId           : "objExpediente.cliente.ubigeoAlternativo.iddistrito",
               name           : "objExpediente.cliente.ubigeoAlternativo.iddistrito",
               store          : new dojo.data.ItemFileReadStore({
                  url: "listaDistritos.action?idProvincia=" + "<s:property value='objExpediente.cliente.ubigeoAlternativo.provincia.idprovincia' />"
               }),
               searchAttr     : "label",
               invalidMessage : "Seleccione un distrito",
               required       : false,
               value          : "<s:property value='objExpediente.cliente.ubigeoAlternativo.iddistrito' />"
            }, "fsDistrito2");
         });
      </script>
   </head>
   <body class="soria">
      <s:form id="frmExpediente" name="frmExpediente" action="doSaveExpediente" method="post">
         <table width="100%">
            <tr>
               <td></td>
               <td height="16" colspan="4" align="left" class="plomo"></td>
            </tr>
            <tr align="center">
               <td width="1%" align="center">&nbsp;</td>
               <td width="99%" colspan="2" align="left">
                  <img src="images/guardar.bmp" onclick="enviarData('doSaveExpediente.action')" alt="Guardar Expediente" style="cursor:pointer" />
                  <s:if test="objExpediente.estado=='A'">
                     <img src="images/archivar.jpg" onclick="archivar()" alt="Archivar Expediente" style="cursor:pointer" />
                  </s:if>
                  <s:elseif test="objExpediente.estado=='I'">
                     <img src="images/reabrir.jpg" onclick="enviarData('doSaveExpedienteReabrir.action')" alt="Reabrir Expediente" style="cursor:pointer" />
                  </s:elseif>
               </td>
            </tr>
            <tr>
               <td></td>
               <td height="16" colspan="4" align="left" class="plomo">
                  <a href="#" onclick="mostrarTrazabilidad();">Ver Trazabilidad</a>
               </td>
            </tr>
            <tr>
               <td height="14" colspan="3">
                  <table width="95%" border="1" align="center" bordercolor="#669BCD" bgcolor="#BFD9F1">
                     <tr>
                        <td height="75">
                           <table width="98%" height="97" align="center" >
                              <tr>
                                 <td></td>
                                 <td colspan="5" height="16" align="left" class="label">
                                    <s:hidden name="objExpediente.idexpediente" />
                                 </td>
                              </tr>
                              <tr>
                                 <td colspan="6" height="15" class="label">EXPEDIENTE</td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left" width="20%">Nro Expediente</td>
                                 <td colspan="3" align="left" class="label" width="78%">
                                    <s:textfield name="objExpediente.nroexpediente" cssClass="cajaMontoTotal" readonly="true" size="20" />
                                 </td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Proceso</td>
                                 <td colspan="3" class="label">
                                    <select dojoType="dijit.form.FilteringSelect"
                                            store="strProceso"
                                            idAttr="id"
                                            labelAttr="label"
                                            searchAttr="label"
                                            name="objExpediente.proceso.idproceso"
                                            id="objExpediente.proceso.idproceso"
                                            readOnly="true"
                                            value="<s:property value='objExpediente.proceso.idproceso' />"
                                            onchange="UpdateInfoProceso" />
                                 </td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Area Destino</td>
                                 <td colspan="3" class="label">
                                    <s:textfield id="unidad" name="objExpediente.proceso.responsable.unidad.nombre" cssClass="cajaMontoTotal" readonly="true" size="35" />
                                 </td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Destinatario</td>
                                 <td colspan="3" class="label">
                                    <s:textfield id="idresponsable" name="objExpediente.proceso.responsable.idusuario" cssStyle="display:none" />
                                    <s:textfield id="responsable" name="objExpediente.proceso.responsable.nombres+' '+objExpediente.proceso.responsable.apellidos" cssClass="cajaMontoTotal" readonly="true" size="35" />
                                 </td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Estado</td>
                                 <td colspan="3" class="label">
                                    <s:if test="objExpediente.estado=='A'">
                                       <input type="text" disabled="disabled" size="35" id="estado" value="Activo" class="cajaMontoTotal"/>
                                    </s:if>
                                    <s:elseif test="objExpediente.estado=='I'">
                                       <input type="text" disabled="disabled" size="35" id="estado" value="Cerrado" class="cajaMontoTotal"/>
                                    </s:elseif>
                                    <s:elseif test="objExpediente.estado=='N'">
                                       <input type="text" disabled="disabled" size="35" id="estado" value="Anulado" class="cajaMontoTotal"/>
                                    </s:elseif>
                                 </td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Vencimiento</td>
                                 <td colspan="3" class="label">
                                    <input type="text" disabled="disabled" size="35" id="estado" value="<s:property value='vencimiento' />" class="cajaMontoTotal"/>
                                 </td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Asunto</td>
                                 <td colspan="3" class="label">
                                    <s:textfield name="objExpediente.asunto" cssClass="cajaMontoTotal" size="40" />
                                 </td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Sumilla</td>
                                 <td colspan="3" class="label">
                                    <s:textfield name="objExpediente.sumilla" cssClass="cajaMontoTotal" size="40" />
                                 </td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td colspan="6" height="15"><hr /></td>
                              </tr>
                              <tr>
                                 <td colspan="6" height="15" class="label">CLIENTE</td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="25" align="left">Tipo Doc Identidad</td>
                                 <td align="left" width="17%" class="label">
                                    <input dojoType="dijit.form.TextBox"
                                           type="text"
                                           id="objExpediente.cliente.idCliente"
                                           jsId="objExpediente.cliente.idCliente"
                                           name="objExpediente.cliente.idCliente"
                                           value="<s:property value='objExpediente.cliente.idCliente' />"
                                           style="display:none;" />
                                    <s:textfield name="objExpediente.cliente.tipoIdentificacion.idtipoidentificacion" cssStyle="display:none" />
                                    <s:textfield id="tipoidentificacion" name="objExpediente.cliente.tipoIdentificacion.nombre" cssClass="cajaMontoTotal" readonly="true" size="20" />
                                 </td>
                                 <td align="left" class="label"></td>
                                 <td align="left" class="label"></td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="25" align="left">Nro Doc Identidad</td>
                                 <td align="left" width="17%" class="label">
                                    <div dojoType="dijit.form.FilteringSelect"
                                         id="objExpediente.cliente.numeroIdentificacion"
                                         jsId="objExpediente.cliente.numeroIdentificacion"
                                         name="objExpediente.cliente.numeroIdentificacion"
                                         onChange="updateInfoCliente"
                                         searchAttr="label"
                                         store="strCliente"
                                         style="width:200px"
                                         value="<s:property value='objExpediente.cliente.numeroIdentificacion' />"></div>
                                 </td>
                                 <td align="left" ></td>
                                 <td align="left" width="45%" class="label"></td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="25" align="left">Nombre/Raz&oacute;n Social</td>
                                 <td colspan="3" align="left" class="label">
                                    <s:if test="objExpediente.cliente.tipoIdentificacion.nombre=='RUC'">
                                       <div dojoType="dijit.form.TextBox"
                                            id="objExpediente.cliente.razonSocial"
                                            jsId="objExpediente.cliente.razonSocial"
                                            name="objExpediente.cliente.razonSocial"
                                            style="width:200px"
                                            trim="true"
                                            value="<s:property value='objExpediente.cliente.razonSocial' />"></div>
                                    </s:if>
                                    <s:else>
                                       <div dojoType="dijit.form.TextBox"
                                            id="objExpediente.cliente.nombres"
                                            jsId="objExpediente.cliente.nombres"
                                            name="objExpediente.cliente.nombres"
                                            style="width:200px"
                                            trim="true"
                                            value="<s:property value='objExpediente.cliente.nombres' />"></div>
                                       <div dojoType="dijit.form.TextBox"
                                            id="objExpediente.cliente.apellidoPaterno"
                                            jsId="objExpediente.cliente.apellidoPaterno"
                                            name="objExpediente.cliente.apellidoPaterno"
                                            style="width:200px"
                                            trim="true"
                                            value="<s:property value='objExpediente.cliente.apellidoPaterno' />"></div>
                                       <div dojoType="dijit.form.TextBox"
                                            id="objExpediente.cliente.apellidoMaterno"
                                            jsId="objExpediente.cliente.apellidoMaterno"
                                            name="objExpediente.cliente.apellidoMaterno"
                                            style="width:200px"
                                            trim="true"
                                            value="<s:property value='objExpediente.cliente.apellidoMaterno' />"></div>
                                    </s:else>
                                 </td>
                                 <td></td>
                              </tr>
                              <s:if test="objExpediente.cliente.tipoIdentificacion.nombre=='RUC'">
                                 <tr>
                                    <td></td>
                                    <td height="25" align="left">Representante Legal</td>
                                    <td colspan="3" align="left" class="label">
                                       <div dojoType="dijit.form.TextBox"
                                            id="objExpediente.cliente.representanteLegal"
                                            jsId="objExpediente.cliente.representanteLegal"
                                            name="objExpediente.cliente.representanteLegal"
                                            style="width:200px"
                                            trim="true"
                                            value="<s:property value='objExpediente.cliente.representanteLegal' />"></div>
                                    </td>
                                    <td></td>
                                 </tr>
                              </s:if>
                              <tr>
                                 <td></td>
                                 <td height="25" align="left">Direcci&oacute;n</td>
                                 <td colspan="3" align="left" class="label">
                                    <div dojoType="dijit.form.TextBox"
                                         id="objExpediente.cliente.direccionPrincipal"
                                         jsId="objExpediente.cliente.direccionPrincipal"
                                         name="objExpediente.cliente.direccionPrincipal"
                                         style="width:200px"
                                         trim="true"
                                         value="<s:property value='objExpediente.cliente.direccionPrincipal' />"></div>
                                 </td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="25" align="left">Ubigeo</td>
                                 <td colspan="3" align="left" class="label">
                                    Departamento
                                    <div id="fsDepartamento1"></div>
                                    Provincia
                                    <div id="fsProvincia1"></div>
                                    Distrito
                                    <div id="fsDistrito1"></div>
                                 </td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="25" align="left">Otra Direcci&oacute;n</td>
                                 <td colspan="3" align="left" class="label">
                                    <div dojoType="dijit.form.TextBox"
                                         id="objExpediente.cliente.direccionAlternativa"
                                         jsId="objExpediente.cliente.direccionAlternativa"
                                         name="objExpediente.cliente.direccionAlternativa"
                                         style="width:200px"
                                         trim="true"
                                         value="<s:property value='objExpediente.cliente.direccionAlternativa' />"></div>
                                 </td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="25" align="left">Ubigeo</td>
                                 <td colspan="3" align="left" class="label">
                                    Departamento
                                    <div id="fsDepartamento2"></div>
                                    Provincia
                                    <div id="fsProvincia2"></div>
                                    Distrito
                                    <div id="fsDistrito2"></div>
                                 </td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="25" align="left">Teléfono</td>
                                 <td colspan="3" align="left" class="label">
                                    <div dojoType="dijit.form.TextBox"
                                         id="objExpediente.cliente.telefono"
                                         jsId="objExpediente.cliente.telefono"
                                         name="objExpediente.cliente.telefono"
                                         style="width:200px"
                                         trim="true"
                                         value="<s:property value='objExpediente.cliente.telefono' />"></div>
                                 </td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="25" align="left">Correo Electrónico</td>
                                 <td colspan="3" align="left" class="label">
                                    <div dojoType="dijit.form.TextBox"
                                         id="objExpediente.cliente.correo"
                                         jsId="objExpediente.cliente.correo"
                                         name="objExpediente.cliente.correo"
                                         style="width:200px"
                                         trim="true"
                                         value="<s:property value='objExpediente.cliente.correo' />"></div>
                                 </td>
                                 <td></td>
                              </tr>
                              <s:if test="objExpediente.documentoList.size() > 0">
                                 <tr>
                                    <td colspan="6"><hr /></td>
                                 </tr>
                                 <tr>
                                    <td colspan="6" height="15" class="label">DOCUMENTOS</td>
                                 </tr>
                                 <tr>
                                    <td colspan="6">
                                       <ul>
                                          <s:iterator value="objExpediente.documentoList">
                                             <li>
                                                <span><s:property value="tipoDocumento.nombre" /></span>
                                                <span>-</span>
                                                <span><s:property value="numeroDocumento" /></span>
                                             </li>
                                          </s:iterator>
                                       </ul>
                                    </td>
                                 </tr>
                              </s:if>
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
      </s:form>
      <s:if test="#session._USUARIO.rol!=null">
         <%@ include file="../util/progressBar.jsp" %>
      </s:if>
   </body>
</html>
