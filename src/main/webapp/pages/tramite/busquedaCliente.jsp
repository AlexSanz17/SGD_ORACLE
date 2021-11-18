<div dojoType="dijit.Dialog"
     id="dlgBusquedaCliente"
     jsId="dlgBusquedaCliente"
     draggable="true"
     style="width:800px;height:500px;"
     title="B&uacute;squeda de Clientes">
   <label for="txtFiltroBusquedaCliente">Filtro:</label>
   <input dojoType="dijit.form.TextBox"
          id="txtFiltroBusquedaCliente"
          jsId="txtFiltroBusquedaCliente"
          style="width:300px;"
          trim="true"/>
   <br />
   <button dojoType="dijit.form.Button"
           type="button"
           iconClass="siged16 sigedSearch16"
           onClick="buscarCliente"
           showLabel="true"
           id="btnBuscar"
           label="Filtrar" ></button>
   <button dojoType="dijit.form.Button"
           type="button"
           iconClass="siged16 sigedEraser16"
           onClick="resetBusquedaCliente"
           showLabel="true"
           label="LimpiarR"></button>
 <!--    <button dojoType="dijit.form.Button"
           type="button"
           iconClass="siged16 sigedNew16"
           onClick="dijit.byId('dlgBusquedaCliente').hide();showRegistroCliente();"
           showLabel="true"
           label="Nuevo Cliente"></button> -->
   <br />
   <strong>Resultado de B&uacute;squeda</strong>
   <table dojoType="dojox.grid.DataGrid"
          id="grdBusquedaCliente"
          jsId="grdBusquedaCliente"
          loadingMessage="<h2>Buscando clientes, espere un momento porfavor...</h2>"
          errorMessage="<h2>Ocurrio un error en el sistema</h2>"
          noDataMessage="<h2>No se encontraron clientes</h2>"
          onRowClick="selectClienteFromGrdBusquedaCliente"
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
