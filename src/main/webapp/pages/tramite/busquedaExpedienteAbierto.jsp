
<style>
   <!--
   .soria .dojoxGridContent .dojoxGridCell {
      cursor: pointer;
   }
   .partsContainer {
      background:#2e2e2e;

   }
   .partsContainer .gridContainer {
      background:#fff;
      height:200px;
   }
   .dojoxGrid-row-odd td {
      background:#e8f2ff;
   }
   -->
</style>

<div dojoType="dijit.Dialog"
     id="dlgBusquedaExpedienteAbierto"
     jsId="dlgBusquedaExpedienteAbierto"
     draggable="false"
     onCancel="returnToForm"
     style="display:none;width:400px;"
     title="Expedientes Abiertos">
   <table width="100%">
      <tr>
         <td colspan="2">
            El Cliente <span id="sCliente" class="titulo"></span> tiene <span id="sLengthExpediente" class="titulo"></span> expediente(s) abierto(s). Seleccione uno para asociar el documento
         </td>
      </tr>
      <tr class="marcoForm">
         <td colspan="2">
            <div class="partsContainer">
               <div class="gridContainer">
                  <table dojoType="dojox.grid.DataGrid"
                         id="grdBusquedaExpedienteAbierto"
                         jsId="grdBusquedaExpedienteAbierto"
                         onRowClick="submitFormWithExpedienteAbierto"
                         rowSelector="10px"
                         store="strBusquedaExpedienteAbierto">
                     <thead>
                        <tr>
                           <th field="nroexpediente" width="110px" editable="false">Nro Expediente</th>
                           <th field="asunto" width="200px" editable="false" align="center">Asunto</th>
                           <th field="id" editable="false" hidden="true">Id</th>
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
            <button dojoType="dijit.form.Button"
                    id="btnWithoutExpedienteAbierto"
                    jsId="btnWithoutExpedienteAbierto"
                    iconClass="siged16 sigedSave16"
                    onClick="submitFormWithoutExpedienteAbierto"
                    showLabel="true">No gracias!, crear un expediente nuevo</button>
            <button dojoType="dijit.form.Button"
                    id="btnRegresarAlForm"
                    jsId="btnRegresarAlForm"
                    iconClass="siged16 sigedSave16"
                    onClick="returnToForm"
                    showLabel="true">Regresar al formulario</button>
         </td>
      </tr>
   </table>
</div>
