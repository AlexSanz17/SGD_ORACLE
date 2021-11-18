

<div dojoType="dijit.Dialog"
     id="dlgNuevoRemitente"
     jsId="dlgNuevoRemitente"
     draggable="false"
     style="display:none; width:370px;"
     title="Registro de Nuevo Remitente">
    
   <div dojoType="dijit.form.Form"
        id="frmNuevoRemitente"
        jsId="frmNuevoRemitente">
       <input type="hidden" id ="objRemitente.idDetalleCliente" value="" name="objRemitente.idDetalleCliente"/>
       <table>
         <tr>
            <td colspan="4">
               <div id="showErrorRemitente" style="color:red; font-weight:bold;"></div>
            </td>
         </tr>
         <tr>
            <td colspan="4"></td>
         </tr>
          
          
         <tr>
            <td width="28%">Nombres</td>
            <td
                <input dojoType="dijit.form.ValidationTextBox"  size="15" maxlength="30"
                             type="text"
                             id="objRemitente.nombres"
                             name="objRemitente.nombres"
                             required="true"  
                             uppercase="true"
                            trim ="true"
                             style="width: 200px;" />
            </td>   
         </tr>
         
         <tr>
            <td width="28%">Paterno</td>
            <td
                <input dojoType="dijit.form.ValidationTextBox" size="15" maxlength="30"
                             type="text"
                             id="objRemitente.apellidoPaterno"
                             name="objRemitente.apellidoPaterno"
                             required="true"
                             trim ="true"
                             uppercase="true"
                             style="width: 200px;" />
            </td>   
         </tr>
         
          <tr>
            <td width="28%">Materno</td>
            <td
                <input dojoType="dijit.form.ValidationTextBox" size="15" maxlength="30"
                             type="text"
                             id="objRemitente.apellidoMaterno"
                             name="objRemitente.apellidoMaterno"
                             required="false"
                             uppercase="true"
                             trim ="true"
                             style="width: 200px;" />
            </td>   
         </tr>
         
         <tr>
            <td width="28%">Cargo</td>
            <td
               <div id="fsCargoAdministrado"></div>
            </td>   
         </tr>
         
         <tr>
            <td colspan="4">&nbsp;</td>
         </tr>
         <tr>
            <td colspan="4">
               <button dojoType="dijit.form.Button"
                       type="button"
                       id="btnRegistroRemitente"
                       jsId="btnRegistroRemitente"
                       iconClass="siged16 sigedSave16"
                       onClick="createRemitente"
                       showLabel="true">Registrar</button>
         
               <button dojoType="dijit.form.Button"
                       type="button"
                       onClick="cancelarRemitente"
                       showLabel="true">Cancelar</button>
            </td>
         </tr>
      </table>
   </div>
    <!--
   <div id="seleccionClientes" style="display:none;">
      <strong>Seleccione un cliente de la lista de resultados:</strong><br />
      <select id="clientes" size="8"></select><br />
      <button id="seleccionarx">Seleccionar</button>
      <button id="cancelarx">Cancelar</button>
   </div> -->
</div>
