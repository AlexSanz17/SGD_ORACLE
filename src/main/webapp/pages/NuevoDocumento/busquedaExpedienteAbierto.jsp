<div dojoType="dijit.Dialog" id="dlgBusquedaExpedienteAbierto"
     jsId="dlgBusquedaExpedienteAbierto" draggable="false"
     style="display: none; width: 495px;" title="Carpetas">
   <table width="100%" class="tundra">
      <tr>
         <td colspan="2">Carpetas existentes del registro seleecionado. Seleccione uno para asociar el documento</td>
      </tr>
      <tr class="marcoForm">
         <td colspan="2">
            <div class="partsContainer" style='background:#2e2e2e;'>
               <div class="gridContainer" style="background:#fff;height:200px;">
                  <table dojoType="dojox.grid.DataGrid"
                         id="grdBusquedaExpedienteAbierto"
                         jsId="grdBusquedaExpedienteAbierto"
                         onRowClick="seleccionarFilaExpedienteAbierto"
                         rowSelector="10px">
                         <!--store="strBusquedaExpedienteAbierto"> -->
                     <thead>
                        <tr>
                           <th field="id" editable="false" hidden="true">Id</th> 
                           <th field="nroexpediente" width="95px" editable="false">Nro Carpeta</th>
                           <th field="asunto" width="280px" editable="false">Asunto</th>
                        </tr>
                     </thead>
                  </table>
               </div>
            </div>
         </td>
      </tr>
      <tr>
         <td colspan="2"></td>
      </tr>
      <tr>
         <td colspan="2">
            <button dojoType="dijit.form.Button" id="btnWithoutExpedienteAbierto"
                    jsId="btnWithoutExpedienteAbierto" iconClass="siged16 sigedSave16"
                    onClick="dlgBusquedaExpedienteAbierto.hide()" showLabel="true">Carpeta nueva</button>
      
         </td>
      </tr>
   </table>
</div>
