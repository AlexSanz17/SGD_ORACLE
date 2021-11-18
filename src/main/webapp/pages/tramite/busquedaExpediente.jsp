<style>
	.soria .dojoxGridContent .dojoxGridCell {
		cursor: pointer;
	}
	.dojoxGrid-row-odd td {
		background:#e8f2ff;
	}
</style>
<div dojoType="dijit.Dialog" id="dlgBusquedaExpediente" jsId="dlgBusquedaExpediente" draggable="true" style="width:540px;height:360px" title="B&uacute;squeda de Carpetas">
	<div id="camposBusquedaExpediente">
		<fieldset>
			<legend></legend>
			<div id="camposIzquierda">
				<label for="sNroExpedienteBE">N&uacute;mero Carpeta</label>
                                <input dojoType="dijit.form.TextBox" id="sNroExpedienteBE" jsId="sNroExpedienteBE" name="sNroExpedienteBE" trim="true" maxlength="12"/>
				
				<br />
				<label for="sNTBE">Nro. Tr&aacute;mite</label>
				<input dojoType="dijit.form.TextBox" name="sNTBE" id= "sNTBE" maxlength="10"/>
                                
                                
                                <br />
				<label for="sAsuntoBE">Asunto</label>
				<input dojoType="dijit.form.TextBox" id="sAsuntoBE" jsId="sAsuntoBE" name="sAsuntoBE" trim="true" maxlength="50"/>
				<!--<br />
                               
				<label for="sRazonSocialBE">Nombre / Raz&oacute;n Social</label>
                                <input id="sRazonSocialBE" style="width:300px"/>
                                
                                 -->
				<br />
			        <input type="button" dojoType="dijit.form.Button" iconClass="dijitEditorIcon dijitEditorIconTabIndent" onClick="busquedaExpediente(this.id)"  showLabel="true" label="Filtrar"  /> 
                             
				 <input  type="button" dojoType="dijit.form.Button" iconClass="dijitEditorIcon dijitEditorIconRemoveFormat" onClick="resetBusquedaExpediente" showLabel="true" label="Limpiar"  />

			</div>
		</fieldset>
	</div>
	<div id="resultadosBusquedaExpediente">
		<strong>Relaci&oacute;n de Carpetas</strong>
		<table height="195px" dojoType="dojox.grid.DataGrid"
             id="grdBusquedaExpediente"
             loadingMessage="<h2>Buscando expedientes, espere un momento porfavor...</h2>"
             errorMessage="<h2>Ocurrio un error en el sistema</h2>"
             noDataMessage="<h2>No se encontraron carpetas</h2>"
             onRowClick="seleccionarFila"
             rowSelector="10px">
			<thead>
				<tr>
					<th field="id" hidden="true" width="66">Id</th>
					<th field="nroexpediente" width="96" editable="false">N&uacute;mero</th>
                                        <th field="fecha" editable="false" align="center">Fecha Creaci&oacute;n</th>
					<th field="asunto" width="260" editable="false">Asunto</th>
					
				</tr>
			</thead>
		</table>

	</div>
</div>