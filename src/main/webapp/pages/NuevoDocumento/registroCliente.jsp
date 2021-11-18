<div dojoType="dijit.Dialog"
     id="dlgNuevoCliente"
     jsId="dlgNuevoCliente"
     draggable="false"
     style="display:none;width:700px;"
     title="Registro de Nuevo Clientex">
   <div dojoType="dijit.form.Form" id="frmNuevoCliente" jsId="frmNuevoCliente">
      <table>
         <tr>
            <td colspan="4"><div id="showErrorCliente" style="color:red;font-weight:bold;">&nbsp;</div></td>
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
                      checked
                      onClick="changeSomeFields(this.value)"
                      value="RUC" />RUCx
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
                <input type="text"
                      dojoType="dijit.form.ValidationTextBox"
                      id="objCliente.nroidentificacion"
                      name="objCliente.nroidentificacion"
                      invalidMessage="Ingrese n&uacute;mero de identificaci&oacute;n. 8 d&iacute;gitos para DNI, 11 para RUC"
                      regExpGen="validateNroIdentificacion"
                      required="true"
                      style="width:100px;" />
            </td>
         </tr>
         <tbody id="tbDataNCRUC" style="display:none;">
            <tr>
                <td>Raz&oacute;n Social <font color="red">(*)</font> </td>
               <td colspan="3">
                  <input type="text"
                         dojoType="dijit.form.ValidationTextBox"
                         id="objCliente.razonsocial"
                         name="objCliente.razonsocial"
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
                         id="objCliente.representantelegal"
                         name="objCliente.representantelegal"
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
                         id="objCliente.apellidopaterno"
                         name="objCliente.apellidopaterno"
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
                         id="objCliente.apellidomaterno"
                         name="objCliente.apellidomaterno"
                         invalidMessage="Ingrese apellido materno"
                         required="false"
                         style="width:300px;" />
               </td>
            </tr>
         </tbody>
         <tr>
            <td>Direcci&oacute;n:</td>
            <td colspan="3">
               <input type="text"
                      dojoType="dijit.form.ValidationTextBox"
                      id="objCliente.direccionprincipal"
                      name="objCliente.direccionprincipal"
                      invalidMessage="Ingrese direcci&oacute;n principal"
                      required="false"
                      style="width:300px;" />
            </td>
         </tr>
         <tr>
            <td>Ubigeo:</td>
            <td>
               Departamento<br />
               <div id="fsDepartamentoNC1" name="fsDepartamentoNC1" />
            </td>
            <td>
               Provincia<br />
               <div id="fsProvinciaNC1" name="fsProvinciaNC1" />
            </td>
            <td>
               Distrito<br />
               <div id="fsDistritoNC1" name="fsDistritoNC1" />
            </td>
         </tr>
         <tr>
            <td>Direcci&oacute;n Procesal</td>
            <td colspan="3">
               <input type="text"
                      dojoType="dijit.form.ValidationTextBox"
                      id="objCliente.direccionalternativa"
                      name="objCliente.direccionalternativa"
                      invalidMessage="Ingrese direcci&oacute;n procesal"
                      required="false"
                      style="width:300px;" />
            </td>
         </tr>
         <tr>
            <td>Ubigeo:</td>
            <td>
               Departamento<br />
               <div id="fsDepartamentoNC2" name="fsDepartamentoNC2" />
            </td>
            <td>
               Provincia<br />
               <div id="fsProvinciaNC2" name="fsProvinciaNC2" />
            </td>
            <td>
               Distrito<br />
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
                      invalidMessage="Ingrese tel&eacute;fono"
                      regExp="[-\*#\+\d]{0,49}"
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
                      invalidMessage="Ingrese correo"
                      regExp="[\.a-zA-Z0-9_-]{2,}@[a-zA-Z0-9_-]{2,}\.[a-zA-Z]{2,4}(\.[a-zA-Z]{2,4}){0,1}"
                      required="false"
                      style="width:300px;" />
            </td>
         </tr>
         <tr>
            <td colspan="4">&nbsp;</td>
         </tr>
         <tr>
            <td colspan="4">
               <button dojoType=dijit.form.Button
                       type="button"
                       id="btnRegistroCliente"
                       jsId="btnRegistroCliente"
                       iconClass="siged16 sigedSave16"
                       onClick="createCliente"
                       showLabel="true">Registrar</button>
               <button dojoType=dijit.form.Button
                       type="button"
                       onClick="resetRegistroCliente"
                       iconClass="siged16 sigedEraser16"
                       showLabel="true">Limpiar</button>
            </td>
         </tr>
      </table>
   </div>
</div>
