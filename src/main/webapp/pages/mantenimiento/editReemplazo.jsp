<?xml version="1.0" encoding="UTF-8" ?>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
   <head>
      <title>Edici&oacute;n de Reemplazo</title>
      <style type="text/css">
         @import "js/dojo/dijit/themes/soria/soria.css";
         @import "css/estilo.css";
         @import "css/siged.css";
         @import "css/sigedIconos.css";
      </style>
      <script type="text/javascript" src="js/dojo/dojo/dojo.js" djConfig="parseOnLoad:true, isDebug: false"></script>
      <script type="text/javascript">
         dojo.require("dijit.form.TimeTextBox");
      </script>
      <script type="text/javascript">
         var idReemplazo = "<s:property value='objReemplazo.idreemplazo' />";
         console.info((idReemplazo == "" ? "Creacion de Reemplazo" : "Edicion de Reemplazo [" + idReemplazo + "]"));

         var eliminarReemplazo = function() {
            dijit.byId("btnGuardarReemplazo").attr("disabled", true);
            dijit.byId("btnEliminarReemplazo").attr("disabled", true);
            dijit.byId("objReemplazo.estado").attr("value", "I");
            dojo.byId("frmReemplazo").submit();
         };

         var guardarReemplazo = function() {
            if (!dijit.byId("frmReemplazo").validate()) {
               dojo.byId("showErrorReemplazo").innerHTML = "Data inv&aacute;lida... Corregir campos";

               return;
            }

            dijit.byId("btnGuardarReemplazo").attr("disabled", true);
            dojo.byId("showErrorReemplazo").innerHTML = "&nbsp;";

            var data = dijit.byId("frmReemplazo").attr("value");
            console.debug("(guardarReemplazo) Data de registro de reemplazo [%s]", dojo.toJson(data, true));

            dojo.byId("frmReemplazo").submit();
         };

         dojo.addOnLoad(function() {
            new dijit.form.FilteringSelect({
               id             : "objReemplazo.idreemplazado",
               jsId           : "objReemplazo.idreemplazado",
               name           : "objReemplazo.idreemplazado",
               store          : new dojo.data.ItemFileReadStore({
                  url: "autocompletarUsuarioFinal.action"
               }),
               searchAttr     : "label",
               invalidMessage : "Seleccione un usuario a ser reemplazado",
               onChange       : function() {
                  if (this.value != "") {
                     console.debug("Obteniendo procesos en los cuales participa el usuario con ID [%s]", this.value);
                     dijit.byId("objReemplazo.idproceso").store = new dojo.data.ItemFileReadStore({url: "autocompletarProcesoPorUsuario.action?iIdUsuario=" + this.value});
                  }
               },
               postCreate   : function() {
                  this.readOnly = (idReemplazo == "" ? false : true);
               },
               required       : true,
               style          : "width:300px",
               value          : "<s:property value='objReemplazo.idreemplazado' />"
            }, "fsReemplazado");

            new dijit.form.FilteringSelect({
               id             : "objReemplazo.idproceso",
               jsId           : "objReemplazo.idproceso",
               name           : "objReemplazo.idproceso",
               searchAttr     : "label",
               invalidMessage : "Seleccione un proceso",
               onChange       : function() {
                  if (this.value != "" && idReemplazo == "") {
                     console.debug("Obteniendo participantes del proceso con ID [%s]", this.value);
                     dijit.byId("objReemplazo.idusuario.idusuario").store = new dojo.data.ItemFileReadStore({url: "autocompletarUsuarioxProceso.action?iIdUsuarioToOmit=" + dijit.byId("objReemplazo.idreemplazado").attr("value") + "&idDependencia1=" + this.value});
                  }
               },
               postCreate   : function() {
                  this.readOnly = (idReemplazo == "" ? false : true);
               },
               required       : true,
               style          : "width:300px"
            }, "fsProceso");

            new dijit.form.FilteringSelect({
               id             : "objReemplazo.idusuario.idusuario",
               jsId           : "objReemplazo.idusuario.idusuario",
               name           : "objReemplazo.idusuario.idusuario",
               searchAttr     : "label",
               invalidMessage : "Seleccione un usuario que reemplazara",
               postCreate   : function() {
                  if (idReemplazo == "") {
                     this.readOnly = false;
                  } else {
                     this.readOnly = true;
                     dijit.byId("objReemplazo.idproceso").store = new dojo.data.ItemFileReadStore({url: "autocompletarProcesoPorUsuario.action?iIdUsuario=" + "<s:property value='objReemplazo.idreemplazado' />"});
                     dijit.byId("objReemplazo.idproceso").attr("value", "<s:property value='objReemplazo.idproceso' />");
                     dijit.byId("objReemplazo.idusuario.idusuario").store = new dojo.data.ItemFileReadStore({url: "autocompletarUsuarioxProceso.action?iIdUsuarioToOmit=" + "<s:property value='objReemplazo.idreemplazado' />" + "&idDependencia1=" + "<s:property value='objReemplazo.idproceso' />"});
                     dijit.byId("objReemplazo.idusuario.idusuario").attr("value", "<s:property value='objReemplazo.idusuario.idusuario' />");
                  }
               },
               required       : true,
               style          : "width:300px"
            }, "fsReemplazante");
         });
      </script>
   </head>
   <body class="soria">
      <div dojoType="dijit.form.Button"
           id="btnGuardarReemplazo"
           jsId="btnGuardarReemplazo"
           iconClass="siged16 sigedBack16"
           label="Guardar"
           onClick="guardarReemplazo"
           showLabel="true"></div>
      <s:if test="objReemplazo.idreemplazo != null">
         <div dojoType="dijit.form.Button"
              id="btnEliminarReemplazo"
              jsId="btnEliminarReemplazo"
              iconClass="siged16 sigedBack16"
              label="Eliminar"
              onClick="eliminarReemplazo"
              showLabel="true"></div>
      </s:if>
      <div dojoType="dijit.form.Form" id="frmReemplazo" jsId="frmReemplazo" action="doSaveReemplazo.action" method="POST">
         <table width="100%">
            <tr>
               <td colspan="2" style="padding-bottom:10px;padding-top:10px;"><div id="showErrorReemplazo" style="color:red;font-weight:bold;">&nbsp;</div></td>
            </tr>
            <tr>
               <td width="20%" class="margen5PX">Usuario a ser reemplazado :</td>
               <td width="80%">
                  <div dojoType="dijit.form.TextBox"
                       type="text"
                       id="objReemplazo.estado"
                       jsId="objReemplazo.estado"
                       name="objReemplazo.estado"
                       style="display:none;"
                       value="<s:property value='objReemplazo.estado' />"></div>
                  <div dojoType="dijit.form.TextBox"
                       type="text"
                       id="objReemplazo.idreemplazo"
                       jsId="objReemplazo.idreemplazo"
                       name="objReemplazo.idreemplazo"
                       style="display:none;"
                       value="<s:property value='objReemplazo.idreemplazo' />"></div>
                  <div id="fsReemplazado"></div>
               </td>
            </tr>
            <tr>
               <td>Proceso :</td>
               <td>
                  <div id="fsProceso"></div>
               </td>
            </tr>
            <tr>
               <td>Usuario Reemplazante :</td>
               <td>
                  <div id="fsReemplazante"></div>
               </td>
            </tr>
            <tr>
               <td>Fecha de Inicio :</td>
               <td>
                  <div dojoType="dijit.form.DateTextBox"
                       id="fechainicialreemplazo"
                       jsId="fechainicialreemplazo"
                       name="fechainicialreemplazo"
                       constraints="{datePattern:'dd/MM/yyyy'}"
                       invalidMessage="Ingrese fecha inicial de Reemplazo (dd/MM/yyyy)"
                       onChange="dijit.byId('fechafinalreemplazo').constraints.min = this.getValue();"
                       required="true"
                       trim="true">
                     <script type="dojo/method" event="postCreate">
                        var fechainicio_year = parseInt("<s:date name='objReemplazo.fechainicialreemplazo' format='yyyy' />", 10);
                        var fechainicio_month = parseInt("<s:date name='objReemplazo.fechainicialreemplazo' format='MM' />", 10);
                        var fechainicio_day = parseInt("<s:date name='objReemplazo.fechainicialreemplazo' format='dd' />", 10);

                        console.debug("fechainicio year [%d] month [%d] day [%d]", fechainicio_year, fechainicio_month, fechainicio_day);

                        if (!isNaN(fechainicio_year) && !isNaN(fechainicio_month) && !isNaN(fechainicio_day)) {
                           this.attr("value", new Date(fechainicio_year, fechainicio_month - 1, fechainicio_day));
                        }
                     </script>
                  </div>
               </td>
            </tr>
            <tr>
               <td>Hora de Inicio :</td>
               <td>
                  <div dojoType="dijit.form.TimeTextBox"
                       id="horainicialreemplazo"
                       jsId="horainicialreemplazo"
                       name="horainicialreemplazo"
                       required="true"
                       trim="true">
                     <script type="dojo/method" event="postCreate">
                        var fechainicio_hour = parseInt("<s:date name='objReemplazo.fechainicialreemplazo' format='HH' />", 10);
                        var fechainicio_minute = parseInt("<s:date name='objReemplazo.fechainicialreemplazo' format='mm' />", 10);

                        console.debug("fechainicio hour [%d] minute [%d]", fechainicio_hour, fechainicio_minute);

                        if (!isNaN(fechainicio_hour) && !isNaN(fechainicio_minute)) {
                           this.attr("value", new Date(0, 0, 0, fechainicio_hour, fechainicio_minute));
                        }
                     </script>
                  </div>
               </td>
            </tr>
            <tr>
               <td>Fecha de Fin :</td>
               <td>
                  <div dojoType="dijit.form.DateTextBox"
                       id="fechafinalreemplazo"
                       jsId="fechafinalreemplazo"
                       name="fechafinalreemplazo"
                       constraints="{datePattern:'dd/MM/yyyy'}"
                       invalidMessage="Ingrese fecha final de Reemplazo (dd/MM/yyyy)"
                       onChange="dijit.byId('fechainicialreemplazo').constraints.max = this.getValue();"
                       required="true"
                       trim="true">
                     <script type="dojo/method" event="postCreate">
                        var fechafin_year = parseInt("<s:date name='objReemplazo.fechafinalreemplazo' format='yyyy' />", 10);
                        var fechafin_month = parseInt("<s:date name='objReemplazo.fechafinalreemplazo' format='MM' />", 10);
                        var fechafin_day = parseInt("<s:date name='objReemplazo.fechafinalreemplazo' format='dd' />", 10);

                        console.debug("fechafin year [%d] month [%d] day [%d]", fechafin_year, fechafin_month, fechafin_day);

                        if (!isNaN(fechafin_year) && !isNaN(fechafin_month) && !isNaN(fechafin_day)) {
                           this.attr("value", new Date(fechafin_year, fechafin_month - 1, fechafin_day));
                        }
                     </script>
                  </div>
               </td>
            </tr>
            <tr>
               <td>Hora de Fin :</td>
               <td>
                  <div dojoType="dijit.form.TimeTextBox"
                       id="horafinalreemplazo"
                       jsId="horafinalreemplazo"
                       name="horafinalreemplazo"
                       required="true"
                       trim="true">
                     <script type="dojo/method" event="postCreate">
                        var fechafin_hour = parseInt("<s:date name='objReemplazo.fechafinalreemplazo' format='HH' />", 10);
                        var fechafin_minute = parseInt("<s:date name='objReemplazo.fechafinalreemplazo' format='mm' />", 10);

                        console.debug("fechafin hour [%d] minute [%d]", fechafin_hour, fechafin_minute);

                        if (!isNaN(fechafin_hour) && !isNaN(fechafin_minute)) {
                           this.attr("value", new Date(0, 0, 0, fechafin_hour, fechafin_minute));
                        }
                     </script>
                  </div>
               </td>
            </tr>
         </table>
      </div>
   </body>
</html>
