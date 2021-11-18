<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div dojoType="dijit.form.Form" id="frmCarpetasBusqueda" jsId="frmCarpetasBusqueda" action="" method="">
   <table>
      <tr>
         <td width="20%">Nombre de Carpeta :</td>
         <td width="80%" colspan="3">
            <div dojoType="dijit.form.TextBox"
                 id="objCarpetaBusqueda.idCarpetaBusqueda"
                 jsId="objCarpetaBusqueda.idCarpetaBusqueda"
                 name="objCarpetaBusqueda.idCarpetaBusqueda"
                 style="display:none;width:100px;"
                 trim="true"
                 value="<s:property value='objCarpetaBusqueda.idCarpetaBusqueda' />"></div>
            <div dojoType="dijit.form.ValidationTextBox"
                 id="objCarpetaBusqueda.nombreCarpeta"
                 jsId="objCarpetaBusqueda.nombreCarpeta"
                 name="objCarpetaBusqueda.nombreCarpeta"
                 invalidMessage="Ingrese nombre de la carpeta de b&uacute;squeda"
                 required="true"
                 style="width:200px;"
                 trim="true"
                 value="<s:property value='objCarpetaBusqueda.nombreCarpeta' />"></div>
         </td>
      </tr>
      <tr>
         <td width="20%">Nro. de Expediente :</td>
         <td width="30%">
            <input dojoType="dijit.form.TextBox"
                   id="objCarpetaBusqueda.numeroExpediente"
                   jsId="objCarpetaBusqueda.numeroExpediente"
                   name="objCarpetaBusqueda.numeroExpediente"
                   style="width:200px;"
                   trim="true"
                   value="<s:property value='objCarpetaBusqueda.numeroExpediente' />">
         </td>
         <td width="20%">Nro. de Mesa Partes :</td>
         <td width="30%">
            <input dojoType="dijit.form.TextBox"
                   type="text"
                   id="objCarpetaBusqueda.numeroMesaPartes"
                   jsId="objCarpetaBusqueda.numeroMesaPartes"
                   name="objCarpetaBusqueda.numeroMesaPartes"
                   style="width:200px;"
                   trim="true"
                   value="<s:property value='objCarpetaBusqueda.numeroMesaPartes' />">
         </td>
      </tr>
      <tr>
         <td>Nro. de Documento :</td>
         <td>
            <input dojoType="dijit.form.TextBox"
                   type="text"
                   id="objCarpetaBusqueda.numeroDocumento"
                   jsId="objCarpetaBusqueda.numeroDocumento"
                   name="objCarpetaBusqueda.numeroDocumento"
                   style="width:200px;"
                   trim="true"
                   value="<s:property value='objCarpetaBusqueda.numeroDocumento' />">
         </td>
         <td>Tipo de Documento :</td>
         <td>
            <input dojoType="dijit.form.FilteringSelect"
                   type="text"
                   id="objCarpetaBusqueda.tipoDocumento"
                   jsId="objCarpetaBusqueda.tipoDocumento"
                   name="objCarpetaBusqueda.tipoDocumento"
                   hasDownArrow="true"
                   store="new dojo.data.ItemFileReadStore({url: 'autocompletarAllTipoDocumento.action'})"
                   searchAttr="label"
                   required="false"
                   style="width:200px;"
                   value="<s:property value='objCarpetaBusqueda.tipoDocumento' />">
         </td>
      </tr>
      <tr>
         <td width="20%">Fecha Creaci&oacute;n Documento :</td>
         <td width="80%" colspan="3">
            Desde :
            <div dojoType="dijit.form.DateTextBox"
                 id="objCarpetaBusqueda.fechaDocumentoDesde"
                 jsId="objCarpetaBusqueda.fechaDocumentoDesde"
                 name="objCarpetaBusqueda.fechaDocumentoDesde"
                 constraints="{datePattern:'dd/MM/yyyy'}"
                 invalidMessage="Ingrese fecha de creaci&o&oacute;n de Documento (dd/MM/yyyy)"
                 onChange="dijit.byId('objCarpetaBusqueda.fechaDocumentoHasta').constraints.min = this.getValue();"
                 required="false"
                 trim="true">
               <script type="dojo/method" event="postCreate">
                  var dateDocumentoDesde_year = parseInt("<s:date name='objCarpetaBusqueda.fechaDocumentoInicio' format='yyyy' />", 10);
                  var dateDocumentoDesde_month = parseInt("<s:date name='objCarpetaBusqueda.fechaDocumentoInicio' format='MM' />", 10);
                  var dateDocumentoDesde_day = parseInt("<s:date name='objCarpetaBusqueda.fechaDocumentoInicio' format='dd' />", 10);

                  console.debug("dateDocumentoDesde year [%d] month [%d] day [%d]", dateDocumentoDesde_year, dateDocumentoDesde_month, dateDocumentoDesde_day);

                  if (!isNaN(dateDocumentoDesde_year) && !isNaN(dateDocumentoDesde_month) && !isNaN(dateDocumentoDesde_day)) {
                     dijit.byId("objCarpetaBusqueda.fechaDocumentoDesde").attr("value", new Date(dateDocumentoDesde_year, dateDocumentoDesde_month - 1, dateDocumentoDesde_day));
                  }
               </script>
            </div>
            Hasta :
            <div dojoType="dijit.form.DateTextBox"
                 id="objCarpetaBusqueda.fechaDocumentoHasta"
                 jsId="objCarpetaBusqueda.fechaDocumentoHasta"
                 name="objCarpetaBusqueda.fechaDocumentoHasta"
                 constraints="{datePattern:'dd/MM/yyyy'}"
                 invalidMessage="Ingrese fecha de creaci&o&oacute;n de Documento (dd/MM/yyyy)"
                 onChange="dijit.byId('objCarpetaBusqueda.fechaDocumentoDesde').constraints.max = this.getValue();"
                 required="false"
                 trim="true">
               <script type="dojo/method" event="postCreate">
                  var dateDocumentoHasta_year = parseInt("<s:date name='objCarpetaBusqueda.fechaDocumentoFinal' format='yyyy' />", 10);
                  var dateDocumentoHasta_month = parseInt("<s:date name='objCarpetaBusqueda.fechaDocumentoFinal' format='MM' />", 10);
                  var dateDocumentoHasta_day = parseInt("<s:date name='objCarpetaBusqueda.fechaDocumentoFinal' format='dd' />", 10);

                  console.debug("dateDocumentoHasta year [%d] month [%d] day [%d]", dateDocumentoHasta_year, dateDocumentoHasta_month, dateDocumentoHasta_day);

                  if (!isNaN(dateDocumentoHasta_year) && !isNaN(dateDocumentoHasta_month) && !isNaN(dateDocumentoHasta_day)) {
                     dijit.byId("objCarpetaBusqueda.fechaDocumentoHasta").attr("value", new Date(dateDocumentoHasta_year, dateDocumentoHasta_month - 1, dateDocumentoHasta_day));
                  }
               </script>
            </div>
         </td>
      </tr>
      <tr>
         <td width="20%">Fecha Creaci&oacute;n Expediente :</td>
         <td width="80%" colspan="3">
            Desde :
            <div dojoType="dijit.form.DateTextBox"
                 id="objCarpetaBusqueda.fechaExpedienteDesde"
                 jsId="objCarpetaBusqueda.fechaExpedienteDesde"
                 name="objCarpetaBusqueda.fechaExpedienteDesde"
                 constraints="{datePattern:'dd/MM/yyyy'}"
                 invalidMessage="Ingrese fecha de creaci&o&oacute;n de Expediente (dd/MM/yyyy)"
                 onChange="dijit.byId('objCarpetaBusqueda.fechaExpedienteHasta').constraints.min = this.getValue();"
                 required="false"
                 trim="true">
               <script type="dojo/method" event="postCreate">
                  var dateExpedienteDesde_year = parseInt("<s:date name='objCarpetaBusqueda.fechaExpedienteInicio' format='yyyy' />", 10);
                  var dateExpedienteDesde_month = parseInt("<s:date name='objCarpetaBusqueda.fechaExpedienteInicio' format='MM' />", 10);
                  var dateExpedienteDesde_day = parseInt("<s:date name='objCarpetaBusqueda.fechaExpedienteInicio' format='dd' />", 10);

                  console.debug("dateExpedienteDesde year [%d] month [%d] day [%d]", dateExpedienteDesde_year, dateExpedienteDesde_month, dateExpedienteDesde_day);

                  if (!isNaN(dateExpedienteDesde_year) && !isNaN(dateExpedienteDesde_month) && !isNaN(dateExpedienteDesde_day)) {
                     dijit.byId("objCarpetaBusqueda.fechaExpedienteDesde").attr("value", new Date(dateExpedienteDesde_year, dateExpedienteDesde_month - 1, dateExpedienteDesde_day));
                  }
               </script>
            </div>
            Hasta :
            <div dojoType="dijit.form.DateTextBox"
                 id="objCarpetaBusqueda.fechaExpedienteHasta"
                 jsId="objCarpetaBusqueda.fechaExpedienteHasta"
                 name="objCarpetaBusqueda.fechaExpedienteHasta"
                 constraints="{datePattern:'dd/MM/yyyy'}"
                 invalidMessage="Ingrese fecha de creaci&o&oacute;n de Expediente (dd/MM/yyyy)"
                 onChange="dijit.byId('objCarpetaBusqueda.fechaExpedienteDesde').constraints.max = this.getValue();"
                 required="false"
                 trim="true">
               <script type="dojo/method" event="postCreate">
                  var dateExpedienteHasta_year = parseInt("<s:date name='objCarpetaBusqueda.fechaExpedienteFinal' format='yyyy' />", 10);
                  var dateExpedienteHasta_month = parseInt("<s:date name='objCarpetaBusqueda.fechaExpedienteFinal' format='MM' />", 10);
                  var dateExpedienteHasta_day = parseInt("<s:date name='objCarpetaBusqueda.fechaExpedienteFinal' format='dd' />", 10);

                  console.debug("dateExpedienteHasta year [%d] month [%d] day [%d]", dateExpedienteHasta_year, dateExpedienteHasta_month, dateExpedienteHasta_day);

                  if (!isNaN(dateExpedienteHasta_year) && !isNaN(dateExpedienteHasta_month) && !isNaN(dateExpedienteHasta_day)) {
                     dijit.byId("objCarpetaBusqueda.fechaExpedienteHasta").attr("value", new Date(dateExpedienteHasta_year, dateExpedienteHasta_month - 1, dateExpedienteHasta_day));
                  }
               </script>
            </div>
         </td>
      </tr>
      <tr>
         <td width="20%">Concesionario :</td>
         <td width="30%">
            <input dojoType="dijit.form.TextBox"
                   type="text"
                   id="objCarpetaBusqueda.consecionario"
                   jsId="objCarpetaBusqueda.consecionario"
                   name="objCarpetaBusqueda.consecionario"
                   style="width:200px;"
                   trim="true"
                   value="<s:property value='objCarpetaBusqueda.consecionario' />">
         </td>
         <td width="20%">Documento Identidad Concesionario :</td>
         <td width="30%">
            <input dojoType="dijit.form.TextBox"
                   type="text"
                   id="objCarpetaBusqueda.documentoIdentidad"
                   jsId="objCarpetaBusqueda.documentoIdentidad"
                   name="objCarpetaBusqueda.documentoIdentidad"
                   style="width:200px;"
                   trim="true"
                   value="<s:property value='objCarpetaBusqueda.documentoIdentidad' />">
         </td>
      </tr>
      <tr>
         <td>Cliente :</td>
         <td>
            <input dojoType="dijit.form.TextBox"
                   type="text"
                   id="objCarpetaBusqueda.cliente"
                   jsId="objCarpetaBusqueda.cliente"
                   name="objCarpetaBusqueda.cliente"
                   style="width:200px;"
                   trim="true"
                   value="<s:property value='objCarpetaBusqueda.cliente' />">
         </td>
         <td>Direcci&oacute;n Principal del Cliente :</td>
         <td>
            <input dojoType="dijit.form.TextBox"
                   type="text"
                   id="objCarpetaBusqueda.direccionCliente"
                   jsId="objCarpetaBusqueda.direccionCliente"
                   name="objCarpetaBusqueda.direccionCliente"
                   style="width:200px;"
                   trim="true"
                   value="<s:property value='objCarpetaBusqueda.direccionCliente' />">
         </td>
      </tr>
      <tr>
         <td>Area Origen :</td>
         <td>
            <input dojoType="dijit.form.FilteringSelect"
                   type="text"
                   id="objCarpetaBusqueda.areaOrigen"
                   jsId="objCarpetaBusqueda.areaOrigen"
                   name="objCarpetaBusqueda.areaOrigen"
                   hasDownArrow="true"
                   store="new dojo.data.ItemFileReadStore({url: 'autocompletarAreaDestino.action'})"
                   searchAttr="label"
                   style="width:200px;"
                   required="false"
                   value="<s:property value='objCarpetaBusqueda.areaOrigen' />">
         </td>
         <td>Area Destino :</td>
         <td>
            <input dojoType="dijit.form.FilteringSelect"
                   type="text"
                   id="objCarpetaBusqueda.areaDestino"
                   jsId="objCarpetaBusqueda.areaDestino"
                   name="objCarpetaBusqueda.areaDestino"
                   hasDownArrow="true"
                   store="new dojo.data.ItemFileReadStore({url: 'autocompletarAreaDestino.action'})"
                   searchAttr="label"
                   style="width:200px;"
                   required="false"
                   value="<s:property value='objCarpetaBusqueda.areaDestino' />">
         </td>
      </tr>
      <tr>
         <td>Propietario :</td>
         <td>
            <input dojoType="dijit.form.TextBox"
                   type="text"
                   id="objCarpetaBusqueda.propietario"
                   jsId="objCarpetaBusqueda.propietario"
                   name="objCarpetaBusqueda.propietario"
                   style="width:200px;"
                   trim="true"
                   value="<s:property value='objCarpetaBusqueda.propietario' />">
         </td>
         <td>Remitente :</td>
         <td>
         	<input dojoType="dijit.form.TextBox"
                   type="text"
                   id="objCarpetaBusqueda.remitente"
                   jsId="objCarpetaBusqueda.remitente"
                   name="objCarpetaBusqueda.remitente"
                   style="width:200px;"
                   trim="true"
                   value="<s:property value='objCarpetaBusqueda.remitente' />">
         </td>
      </tr>
      <tr>
         <td width="20%">Proceso :</td>
         <td width="80%" colspan="3">
            <input dojoType="dijit.form.FilteringSelect"
                   type="text"
                   id="objCarpetaBusqueda.proceso"
                   jsId="objCarpetaBusqueda.proceso"
                   name="objCarpetaBusqueda.proceso"
                   hasDownArrow="true"
                   store="new dojo.data.ItemFileReadStore({url: 'autocompletarProcesoMP.action'})"
                   searchAttr="label"
                   required="false"
                   style="width:400px;"
                   value="<s:property value='objCarpetaBusqueda.proceso' />">
         </td>
      </tr>
      <tr>
         <td>Asunto del Expediente :</td>
         <td>
            <input dojoType="dijit.form.TextBox"
                   type="text"
                   id="objCarpetaBusqueda.asuntoExpediente"
                   jsId="objCarpetaBusqueda.asuntoExpediente"
                   name="objCarpetaBusqueda.asuntoExpediente"
                   style="width:200px;"
                   trim="true"
                   value="<s:property value='objCarpetaBusqueda.asuntoExpediente' />">
         </td>

         <td>Documentos Pendientes : </td>
         <td>
         	<input    dojoType="dijit.form.CheckBox" type="checkbox"
                         id="pendiente_"
                         name="pendiente_"
                         <c:if test="${objCarpetaBusqueda.pendiente=='S'}" > checked </c:if> />


         </td>
      </tr>
      <tr>
         <td width="20%">Asunto del Documento :</td>
         <td width="80%" colspan="3">
            <input dojoType="dijit.form.TextBox"
                   type="text"
                   id="objCarpetaBusqueda.asuntoDocumento"
                   jsId="objCarpetaBusqueda.asuntoDocumento"
                   name="objCarpetaBusqueda.asuntoDocumento"
                   style="width:200px;"
                   trim="true"
                   value="<s:property value='objCarpetaBusqueda.asuntoDocumento' />">
         </td>
      </tr>
      <tr>
         <td width="20%">Estado del Expediente :</td>
         <td width="80%" colspan="3">
            <input dojoType="dijit.form.TextBox"
                   type="text"
                   id="objCarpetaBusqueda.estadoExpediente"
                   jsId="objCarpetaBusqueda.estadoExpediente"
                   name="objCarpetaBusqueda.estadoExpediente"
                   style="width:200px;"
                   trim="true"
                   value="<s:property value='objCarpetaBusqueda.estadoExpediente' />">
         </td>
      </tr>
      <tr>
         <td colspan="4">&nbsp;</td>
      </tr>
      <tr>
         <td colspan="4" style="padding-top:10px;">
            <button dojoType=dijit.form.Button
                    type="button"
                    id="btnCrearCarpetasBusqueda"
                    iconClass="dijitEditorIcon dijitEditorIconSave"
                    onClick="doSaveCarpetasBusqueda"
                    showLabel="true">Crear</button>
            <button dojoType=dijit.form.Button
                    type="button"
                    onClick="resetFormCarpetasBusqueda"
                    iconClass="dijitEditorIcon dijitEditorIconRemoveFormat"
                    showLabel="true">Limpiar</button>
         </td>
      </tr>
      <tr>
         <td colspan="4"><div id="showErrorCarpetasBusqueda" style="color:red;font-weight:bold;">&nbsp;</div></td>
      </tr>
   </table>
</div>
