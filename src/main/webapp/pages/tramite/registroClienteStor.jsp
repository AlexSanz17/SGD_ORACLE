<div dojoType="dijit.Dialog"
     id="dlgNuevoClienteStor"
     jsId="dlgNuevoClienteStor"
     draggable="false"
     style="display:none; width:700px;"
     title="Registro de Nuevo Cliente">
   <div dojoType="dijit.form.Form"
        id="frmNuevoClienteStor"
        jsId="frmNuevoClienteStor">
      <table>
         <tr>
            <td colspan="4">
               <div id="showErrorClienteStormp" style="color:red; font-weight:bold;"></div>
            </td>
         </tr>
         <tr>
            <td colspan="4"></td>
         </tr>
         <tr>
            <td width="20%">Tipo Doc Identidad</td>
            <td width="80%" colspan="3">
               <input dojoType="dijit.form.RadioButton" name="sTipoIdentificacionNC"
                      type="radio"
                      checked
                      onClick="changeSomeFields(this.value)"
                      value="RUC" />RUCx1
               <input dojoType="dijit.form.RadioButton"
                      type="radio"
                      name="sTipoIdentificacionNC"
                      onClick="changeSomeFields(this.value)"
                      value="DNI" />DNI
               <input dojoType="dijit.form.RadioButton"
                      type="radio"
                      name="sTipoIdentificacionNC"
                      onClick="changeSomeFields(this.value)"
                      value="Otro" />Otro
            </td>
         </tr>
         <tr>
            <td width="20%">Nro Identificaci&oacute;n</td>
            <td width="20%">
               <div id="txtNroIdentificacion"></div>
            </td>
            <td colspan="2" width="60%">
               <div id="autoGenerarNroIdentificacion" style="float:left;">
                  <input dojoType="dijit.form.CheckBox"
                         id="chkAutoGenerarNroIdentificacionStor"
                         jsId="chkAutoGenerarNroIdentificacionStor"
                         onClick="dijit.byId('objClienteStor.numeroIdentificacion').attr('value', '');" />
                  <label for="chkAutoGenerarNroIdentificacionStor">Auto generar nro de identifaci&oacute;n</label>
               </div>
               <button dojoType="dijit.form.Button"
                       type="button"
                       id="btnBuscarRucStor"
                       jsId="btnBuscarRucStor"
                       onClick="buscarPorRUCStor"
                       showLabel="true">Buscar RUC</button>
            </td>
         </tr>
         <tbody id="tbDataNCRUC" style="display: none;">
            <tr>
               <td width="20%">Raz&oacute;n Social</td>
               <td colspan="3" width="80%">
                  <input dojoType="dijit.form.ValidationTextBox"
                         type="text"
                         id="objClienteStor.razonSocial"
                         name="objClienteStor.razonSocial"
                         invalidMessage="Ingrese raz&oacute;n social"
                         required="false"
                         style="width: 300px;" />
                  <button id="buscarRazonSocialStor">Buscar Raz&oacute;n Social</button>
               </td>
            </tr>
            <tr>
               <td>Representante Legal</td>
               <td colspan="3">
                  <input dojoType="dijit.form.ValidationTextBox"
                         type="text"
                         id="objClienteStor.representanteLegal"
                         name="objClienteStor.representanteLegal"
                         invalidMessage="Ingrese representante legal"
                         required="false"
                         style="width: 300px;" />
               </td>
            </tr>
         </tbody>
         <tbody id="tbDataNCDNI" style="display: none;">
            <tr>
               <td>Nombres</td>
               <td colspan="3">
                  <input dojoType="dijit.form.ValidationTextBox"
                         type="text"
                         id="objClienteStor.nombres"
                         name="objClienteStor.nombres"
                         invalidMessage="Ingrese nombres"
                         required="false"
                         style="width: 300px;" />
               </td>
            </tr>
            <tr>
               <td>Apellido Paterno</td>
               <td colspan="3">
                  <input dojoType="dijit.form.ValidationTextBox"
                         type="text"
                         id="objClienteStor.apellidoPaterno"
                         name="objClienteStor.apellidoPaterno"
                         invalidMessage="Ingrese apellido paterno"
                         required="false"
                         style="width: 300px;" />
               </td>
            </tr>
            <tr>
               <td>Apellido Materno</td>
               <td colspan="3">
                  <input dojoType="dijit.form.ValidationTextBox"
                         type="text"
                         id="objClienteStor.apellidoMaterno"
                         name="objClienteStor.apellidoMaterno"
                         invalidMessage="Ingrese apellido materno"
                         required="false"
                         style="width: 300px;" />
               </td>
            </tr>
         </tbody>
         <tr>
            <td>Direcci&oacute;n:</td>
            <td colspan="3">
               <div dojoType="dijit.form.ValidationTextBox"
                    id="objClienteStor.direccionPrincipal"
                    jsId="objClienteStor.direccionPrincipal"
                    name="objClienteStor.direccionPrincipal"
                    invalidMessage="Ingrese direcci&oacute;n principal"
                    required="false"
                    style="width: 300px;"></div>
            </td>
         </tr>
         <tr>
            <td>Ubigeo:</td>
            <td>
               Departamento<br />
               <div id="fsDepartamentoNC1Stor" name="fsDepartamentoNC1Stor"></div>
            </td>
            <td>
               Provincia<br />
               <div id="fsProvinciaNC1Stor" name="fsProvinciaNC1Stor"></div>
            </td>
            <td>
               Distrito<br />
               <div id="fsDistritoNC1Stor" name="fsDistritoNC1Stor"></div>
            </td>
         </tr>
         <tr>
            <td>Direcci&oacute;n Procesal</td>
            <td colspan="3">
               <div dojoType="dijit.form.ValidationTextBox"
                    id="objClienteStor.direccionAlternativa"
                    jsId="objClienteStor.direccionAlternativa"
                    name="objClienteStor.direccionAlternativa"
                    invalidMessage="Ingrese direcci&oacute;n procesal"
                    required="false"
                    style="width: 300px;"></div>
            </td>
         </tr>
         <tr>
            <td>Ubigeo:</td>
            <td>
               Departamento<br />
               <div id="fsDepartamentoNC2Stor" name="fsDepartamentoNC2Stor"></div>
            </td>
            <td>
               Provincia<br />
               <div id="fsProvinciaNC2Stor" name="fsProvinciaNC2Stor"></div>
            </td>
            <td>
               Distrito<br />
               <div id="fsDistritoNC2Stor" name="fsDistritoNC2Stor"></div>
            </td>
         </tr>
         <tr>
            <td>Tel&eacute;fono</td>
            <td colspan="3">
               <input dojoType="dijit.form.ValidationTextBox"
                      type="text"
                      id="objClienteStor.telefono"
                      name="objClienteStor.telefono"
                      invalidMessage="Ingrese tel&eacute;fono"
                      regExp="[-\*#\+\d]{0,49}"
                      required="false"
                      style="width: 200px;" />
            </td>
         </tr>
         <tr>
            <td>Correo</td>
            <td colspan="3">
               <input dojoType="dijit.form.ValidationTextBox"
                      type="text"
                      id="objClienteStor.correo"
                      name="objClienteStor.correo"
                      invalidMessage="Ingrese correo"
                      regExp="[\.a-zA-Z0-9_-]{2,}@[a-zA-Z0-9_-]{2,}\.[a-zA-Z]{2,4}(\.[a-zA-Z]{2,4}){0,1}"
                      required="false"
                      style="width: 300px;" />
            </td>
         </tr>
         <tr>
            <td colspan="4">&nbsp;</td>
         </tr>
         <tr>
            <td colspan="4">
               <button dojoType="dijit.form.Button"
                       type="button"
                       id="btnRegistroClienteStor"
                       jsId="btnRegistroClienteStor"
                       iconClass="siged16 sigedSave16"
                       onClick="createClienteStor"
                       showLabel="true">Registrar</button>
               <button dojoType="dijit.form.Button"
                       type="button"
                       onClick="resetRegistroClienteStor"
                       iconClass="siged16 sigedEraser16"
                       showLabel="true">Limpiar</button>
               <button dojoType="dijit.form.Button"
                       type="button"
                       onClick="cancelarRegistro"
                       showLabel="true">Cancelar</button>
            </td>
         </tr>
      </table>
   </div>
   <div id="seleccionClientesStor" style="display:none;">
      <strong>Seleccione un cliente de la lista de resultados:</strong><br />
      <select id="clientesStor" size="8"></select><br />
      <button id="seleccionarStor">Seleccionar</button>
      <button id="cancelarStor">Cancelar</button>
   </div>
</div>

<div dojoType="dijit.Dialog"
     id="dlgBusquedaClienteStor"
     jsId="dlgBusquedaClienteStor"
     draggable="true"
     style="width:800px;height:500px;"
     title="B&uacute;squeda de Clientes">
   <label for="txtFiltroBusquedaClienteStor">Filtro :</label>
   <input dojoType="dijit.form.TextBox"
          id="txtFiltroBusquedaClienteStor"
          jsId="txtFiltroBusquedaClienteStor"
          style="width:300px;"
          trim="true"/>
   <br />
   <button dojoType="dijit.form.Button"
           type="button"
           iconClass="siged16 sigedSearch16"
           onClick="buscarClienteStor"
           showLabel="true"
           label="Filtrar"></button>
   <button dojoType="dijit.form.Button"
           type="button"
           iconClass="siged16 sigedEraser16"
           onClick="resetBusquedaClienteStor"
           showLabel="true"
           label="Limpiar"></button>
   <button dojoType="dijit.form.Button"
           type="button"
           iconClass="siged16 sigedNew16"
           onClick="dijit.byId('dlgBusquedaClienteStor').hide();showRegistroClienteStor();"
           showLabel="true"
           label="Nuevo Cliente"></button>
   <br />
   <strong>Resultado de B&uacute;squeda</strong>
   <table dojoType="dojox.grid.DataGrid"
          id="grdBusquedaClienteStor"
          jsId="grdBusquedaClienteStor"
          loadingMessage="<h2>Buscando clientes, espere un momento porfavor...</h2>"
          errorMessage="<h2>Ocurrio un error en el sistema</h2>"
          noDataMessage="<h2>No se encontraron clientes</h2>"
          onRowClick="selectClienteStorFromGrdBusquedaCliente"
          rowSelector="10px"
          style="height:400px;">
      <thead>
         <tr>
            <th field="id" hidden="true">Id Cliente</th>
            <th field="tipoidentificacion" width="150" editable="false">Tipo Identificacion</th>
            <th field="numeroidentificacion" width="200" editable="false">Nro Identificacion</th>
            <th field="label" width="300" editable="false">Razon Social</th>
         </tr>
      </thead>
   </table>
</div>
