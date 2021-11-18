

<div dojoType="dijit.Dialog"
     id="dlgNuevoCliente"
     jsId="dlgNuevoCliente"
     draggable="false"
     style="display:none; width:680px;"
     title="Registro de Nuevo Item">
   <div dojoType="dijit.form.Form"
        id="frmNuevoCliente"
        jsId="frmNuevoCliente">

       <table>
         <tr>
            <td colspan="4">
               <div id="showErrorClientemp" style="color:red; font-weight:bold;"></div>
            </td>
         </tr>
         <tr>
            <td colspan="4"></td>
         </tr>
          
          <tbody id="tbOtro" style="display:none;">
              <tr>
                 <td width="30%">Tipo Doc Identidad</td>
                 <td>    
                    <input dojoType="dijit.form.RadioButton"
                           type="radio"
                           name="sTipoIdentificacionNC" 
                           value="RUC" />RUC
                    <input dojoType="dijit.form.RadioButton"
                           type="radio"
                           name="sTipoIdentificacionNC" 
                           value="DNI" />DNI
                    <input dojoType="dijit.form.RadioButton"
                           type="radio"
                           name="sTipoIdentificacionNC"
                           value="CE" />CE
                    <input dojoType="dijit.form.RadioButton"
                           type="radio"
                           name="sTipoIdentificacionNC"
                           value="Otro" />Otro
                 </td>  
              </tr>
         </tbody>
         <tr>
            <td width="30%">Nro Identificaci&oacute;n</td>
            <td
                <input dojoType="dijit.form.ValidationTextBox" size="15" maxlength="15"
                             type="text"
                             id="objCliente.numeroIdentificacion"
                             name="objCliente.numeroIdentificacion"
                             required="false"
                             style="width: 200px;" />
            </td>  
         </tr>
         <!--
         <tbody id="tbDataCargo" style="display: none;">
             <tr>
                 <td>Cargo<font color="red">(*)</font> </td>
                 <td colspan="3" >
                     <div id ="fsCargoCliente" name="fsCargoCliente"></div>
                 
                 
               </td>
             </tr>
         </tbody> -->
         
         <tbody id="tbDataNCRUC" style="display: none;">
             <!--
               <tr>
                <td >Tipo <font color="red">(*)</font></td>
                <td    colspan="3">
                    <div id="fsTipoInstitucionCliente" name="fsTipoInstitucionCliente" style="width:250px;"></div>
                </td>
            </tr>    
		-->	
            <tr>
               <td width="30%">Raz&oacute;n Social <font color="red">(*)</font></td>
               <td colspan="3" width="80%">
                  <input dojoType="dijit.form.ValidationTextBox"
                         type="text"
                         id="objCliente.razonSocial"
                         uppercase="true"
                         name="objCliente.razonSocial"
                         invalidMessage="Ingrese raz&oacute;n social"
                         required="false"
                         style="width: 300px;" />
                 
               </td>
            </tr>
            
         </tbody>
         <tbody id="tbDataNCDNI" style="display: none;">
            <tr>
               <td>Nombres <font color="red">(*)</font></td>
               <td colspan="3">
                  <input dojoType="dijit.form.ValidationTextBox"
                         type="text"
                         id="objCliente.nombres"
                         name="objCliente.nombres"
                         uppercase="true"
                         invalidMessage="Ingrese nombres"
                         required="false"
                         style="width: 300px;" />
               </td>
            </tr>
            <tr>
               <td>Apellido Paterno <font color="red">(*)</font></td>
               <td colspan="3">
                  <input dojoType="dijit.form.ValidationTextBox"
                         type="text"
                         id="objCliente.apellidoPaterno"
                         name="objCliente.apellidoPaterno"
                         uppercase="true"
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
                         id="objCliente.apellidoMaterno"
                         name="objCliente.apellidoMaterno"
                         invalidMessage="Ingrese apellido materno"
                         uppercase="true"
                         required="false"
                         style="width: 300px;" />
               </td>
            </tr>
         </tbody>
         <tr>
            <td>Direcci&oacute;n:</td>
            <td colspan="3">
               <div dojoType="dijit.form.ValidationTextBox"
                    id="objCliente.direccionPrincipal"
                    jsId="objCliente.direccionPrincipal"
                    name="objCliente.direccionPrincipal"
                    uppercase="true"
                    invalidMessage="Ingrese direcci&oacute;n principal"
                    required="false"
                    style="width: 300px;"></div>
            </td>
         </tr>
         <tr>
             <td>Ubigeo</td> 
            <td>
               Departamento<br />
               <div id="fsDepartamentoNC1" name="fsDepartamentoNC1" style="width:150px;"></div>
            </td>
            <td>
               Provincia<br />
               <div id="fsProvinciaNC1" name="fsProvinciaNC1"></div>
            </td>
            <td>
               Distrito<br />
               <div id="fsDistritoNC1" name="fsDistritoNC1"></div>
            </td> 
         </tr>
         
         <tr>
            <td>Tel&eacute;fono</td>
            <td colspan="3">
               <input dojoType="dijit.form.ValidationTextBox"
                      type="text"
                      id="objCliente.telefono"
                      name="objCliente.telefono"
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
                      id="objCliente.correo"
                      name="objCliente.correo"
                      invalidMessage="Ingrese correo"
                      regExp="[\.a-zA-Z0-9_-]{2,}@[a-zA-Z0-9_-]{2,}\.[a-zA-Z]{2,4}(\.[a-zA-Z]{2,4}){0,1}"
                      required="false"
                      style="width: 200px;" />
            </td>
         </tr>
         <tr>
            <td colspan="4">&nbsp;</td>
         </tr>
         <tr>
            <td colspan="4">
               <button dojoType="dijit.form.Button"
                       type="button"
                       id="btnRegistroCliente"
                       jsId="btnRegistroCliente"
                       iconClass="siged16 sigedSave16"
                       onClick="createCliente"
                       showLabel="true">Registrar</button>
               <button dojoType="dijit.form.Button"
                       type="button"
                       onClick="resetRegistroCliente"
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
   <div id="seleccionClientes" style="display:none;">
      <strong>Seleccione un cliente de la lista de resultados:</strong><br />
      <select id="clientes" size="8"></select><br />
      <button id="seleccionarx">Seleccionar</button>
      <button id="cancelarx">Cancelar</button>
   </div>
</div>
