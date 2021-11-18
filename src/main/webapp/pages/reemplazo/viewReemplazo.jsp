<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
   <head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
      <title>Sistema de Gesti&oacute;n Documentaria - Vista de Reemplazos</title>
      <script type="text/javaascript">
         dojo.addOnLoad(function() {
            new dijit.form.FilteringSelect({
               //id             : "objReemplazo.idusuario.idusuario",
               //jsId           : "objReemplazo.idusuario.idusuario",
               name           : "objReemplazo.idusuario.idusuario",
               store          : new dojo.data.ItemFileReadStore({
                  url: "autocompletarUsuarioxProceso.action?idDependencia1=" + <s:property value='objProceso.idproceso' />
               }),
               searchAttr     : "label",
               invalidMessage : "Seleccione un usuario",
               required       : true,
               value          : "<s:property value='objReemplazo.idusuario.idusuario' />",
               style          : "width:300px"
            }, "fsUsuarioReemplazante");
         });
      </script>
   </head>
   <body>
      <div dojoType="dijit.Toolbar">
         <div dojoType="dijit.form.Button"
              id="btnGuardarReemplazo"
              jsId="btnGuardarReemplazo"
              iconClass="siged16 sigedBack16"
              label="Guardar"
              onClick="guardarReemplazo"
              showLabel="true"></div>
      </div>
      <div dojoType="dijit.form.Form" id="frmReemplazo" jsId="frmReemplazo" action="doSaveReemplazoUSERFINAL.action" method="POST">
         <table width="100%">
            <tr>
               <td colspan="2" style="padding-bottom:10px;padding-top:10px;"><div id="showErrorReemplazo" style="color:red;font-weight:bold;">&nbsp;</div></td>
            </tr>
            <tr>
               <td width="20%" class="margen5PX">Proceso :</td>
               <td width="80%">
                  <div dojoType="dijit.form.TextBox"
                       type="text"
                       <%--id="objReemplazo.idreemplazo"
                       jsId="objReemplazo.idreemplazo"--%>
                       name="objReemplazo.idreemplazo"
                       style="display:none;"
                       value="<s:property value='objReemplazo.idreemplazo' />"></div>
                  <div dojoType="dijit.form.TextBox"
                       type="text"
                       <%--id="objReemplazo.idproceso"
                       jsId="objReemplazo.idproceso"--%>
                       name="objReemplazo.idproceso"
                       style="display:none;"
                       value="<s:property value='objProceso.idproceso' />"></div>
                  <div dojoType="dijit.form.TextBox"
                       type="text"
                       <%--id="objProceso.nombre"
                       jsId="objProceso.nombre"--%>
                       name="objProceso.nombre"
                       readOnly="true"
                       style="width:300px;"
                       trim="true"
                       value="<s:property value='objProceso.nombre' />"></div>
               </td>
            </tr>
            <tr>
               <td>Usuario Reemplazante :</td>
               <td>
                  <div id="fsUsuarioReemplazante"></div>
               </td>
            </tr>
            <tr>
               <td>Fecha de Inicio :</td>
               <td>
                  <div dojoType="dijit.form.DateTextBox"
                       id="fechainicialreemplazo1"
                       jsId="fechainicialreemplazo1"
                       name="fechainicialreemplazo"
                       constraints="{datePattern:'dd/MM/yyyy'}"
                       invalidMessage="Ingrese fecha inicial de Reemplazo (dd/MM/yyyy)"
                       onChange="dijit.byId('fechafinalreemplazo1').constraints.min = this.getValue();"
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
                       id="horainicialreemplazo1"
                       jsId="horainicialreemplazo1"
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
                       id="fechafinalreemplazo1"
                       jsId="fechafinalreemplazo1"
                       name="fechafinalreemplazo"
                       constraints="{datePattern:'dd/MM/yyyy'}"
                       invalidMessage="Ingrese fecha final de Reemplazo (dd/MM/yyyy)"
                       onChange="dijit.byId('fechainicialreemplazo1').constraints.max = this.getValue();"
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
                       id="horafinalreemplazo1"
                       jsId="horafinalreemplazo1"
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
