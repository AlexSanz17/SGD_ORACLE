<div dojoType="dijit.Dialog"
     id="dlgParaM"
     jsId="dlgParaM"
     draggable="true"
     style="width:580px;height:400px;"
     title="B&uacute;squeda de Contactos">
    <table>
         <tr>
             <td><div id="divstrUOLabel"><label for="strUO">Unidad Orgánica:</label></div></td>
            <td>
                <div id="divstrUO">
               <select dojoType="dijit.form.FilteringSelect" 
                       required="false"
                                                    id="strUO"
                                                    name="strUO"
                                                    idAttr="id"
                                                    labelAttr="label"
                                                    style="width:300px;"
                                                    hasDownArrow="true"
                                                    searchAttr="label"
                                                    store="autocompletarUnidadOrgSession"
                                                    onChange="agregarDestinatarios(2)"></select><br/>
                </div>


            </td>

        </tr>

         
        
        
        
        <tr>
            <td><label for="txtFiltroParaM">Usuario:</label></td>
            <td>
                <input dojoType="dijit.form.TextBox"
                       id="txtFiltroParaM"
                       jsId="txtFiltroParaM"
                       onKeyUp="filterParaM"
                       style="width:300px;"
                       trim="true"/><br/>


            </td>
            <td>
                  <button dojoType="dijit.form.Button"
                        type="button"
                        iconClass="siged16 sigedEraser16"
                        onClick="resetParaM"
                        showLabel="true"
                        label="Limpiar"></button>
                
            </td>
        </tr>   
       
    </table>




    <br />
    <strong>Resultado de B&uacute;squeda</strong>
    <table dojoType="dojox.grid.DataGrid"
           id="grdParaM"
           jsId="grdParaM"
           loadingMessage="<h2>Buscando contactos, espere un momento porfavor...</h2>"
           errorMessage="<h2>Ocurrio un error en el sistema</h2>"
           noDataMessage="<h2>No se encontraron contactos</h2>"
           onRowClick="selectContactoFromGrdParaM"
           rowSelector="10px"
           rowsPerPage="50"
           style="height:300px;">
        <thead>
            <tr>

                <th field="nombres" width="189" editable="false">Nombres</th>
                <th field="cargo" width="80" editable="false">Cargo</th>
                <th field="area" width="217" editable="false">Area</th>
            </tr>
        </thead>
    </table>
</div>
<div dojoType="dijit.Dialog"
     id="dlgPara"
     jsId="dlgPara"
     draggable="true"
     style="width:580px;height:400px;"
     title="B&uacute;squeda de Contactos">
    <table>
        <tr>
            <td><label for="txtFiltroPara">Filtro:</label></td>
            <td>
                <input dojoType="dijit.form.TextBox"
                       id="txtFiltroPara"
                       jsId="txtFiltroPara"
                       onKeyUp="filterPara"
                       style="width:300px;"
                       trim="true"/><br/>


            </td>
            <td>
                  <button dojoType="dijit.form.Button"
                        type="button"
                        iconClass="siged16 sigedEraser16"
                        onClick="resetPara"
                        showLabel="true"
                        label="Limpiar"></button>
                
            </td>
        </tr>
       
    </table>




    <br />
    <strong>Resultado de B&uacute;squeda</strong>
    <table dojoType="dojox.grid.DataGrid"
           id="grdPara"
           jsId="grdPara"
           loadingMessage="<h2>Buscando contactos, espere un momento porfavor...</h2>"
           errorMessage="<h2>Ocurrio un error en el sistema</h2>"
           noDataMessage="<h2>No se encontraron contactos</h2>"
           onRowClick="selectContactoFromGrdPara"
           rowSelector="10px"
           rowsPerPage="50"
           style="height:300px;">
        <thead>
            <tr>

                <th field="nombres" width="189" editable="false">Nombres</th>
                <th field="cargo" width="80" editable="false">Cargo</th>
                <th field="area" width="217" editable="false">Area</th>
            </tr>
        </thead>
    </table>
</div>