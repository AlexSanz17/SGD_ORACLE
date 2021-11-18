<style>
	.soria .dojoxGridContent .dojoxGridCell {
		cursor: pointer;
	}
	.dojoxGrid-row-odd td {
		background:#e8f2ff;
	}
</style>

<script type="text/javascript">
    function resetBusquedaDocumentoReferenciado(){
    	dijit.byId("sTipoDocumentoReferenciado").attr("value", "");
    	dijit.byId("sAreaDocumentoReferenciado").attr("value", "");
    	dijit.byId("sDocumentoReferenciado").attr("value", "");
    }

    function sinDocumento(){
       showTBODY("tbDocumentoReferenciado");
       showTBODY("tbformularioReferenciado");


       dijit.byId("objDF.idTipoDocumentoReferenciado").attr("value", "");
       dijit.byId("objDF.nroDocumento").attr("value", "");
       dijit.byId("objDF.asunto").attr("value", "");
       dijit.byId("objDF.fechaDocumentoRegistro").attr("value", "");
       dlgBusquedaDocumentoReferenciado.hide();
    }

    function fillDataDocumento(e) {
	   if (e.rowIndex == undefined) {
	      return;
	   }

	   var x = document.getElementById("referenciaDoc");

	   for (var i=0; i<x.options.length;i++){
		   if (x.options[i].value==grdBusquedaDocumentoReferenciado.getItem(e.rowIndex).id){
			   alert("El documento ya fue agregado");
      		   return;
		   }
	   }

       var texto = grdBusquedaDocumentoReferenciado.getItem(e.rowIndex).nrodocumento + "";
       var valor = grdBusquedaDocumentoReferenciado.getItem(e.rowIndex).id;

	   $("#referenciaDoc").append($("<option></option>")
                  .attr("value", valor)
                  .text(texto));

	}

    busquedaDocumentoReferenciado = function() {
	   var parameters = "";

	   if (dijit.byId("sExpedienteReferenciado").getValue().trim()=='' && dijit.byId("sDocumentoReferenciado").getValue().trim()=='' && dijit.byId("sTipoDocumentoReferenciado").getValue().trim()!='' ){
	     alert("Debe ingresar al menos un campo para la búsqueda");
	     return;
	   }


	   parameters += "tipoDocumento=" + dijit.byId("sTipoDocumentoReferenciado").getValue() + "&";
	   parameters += "sNroExpediente=" + dijit.byId("sExpedienteReferenciado").getValue() + "&";
	   parameters += "sNroDocumento=" +     dijit.byId("sDocumentoReferenciado").getValue();

	   strBusquedaDocumentoReferenciado = new dojo.data.ItemFileReadStore({
	      url: "buscarDocumentoReferenciado.action?" + parameters
	   });

	   grdBusquedaDocumentoReferenciado.setStore(strBusquedaDocumentoReferenciado);
	}

</script>

 <script language="JavaScript">
	 dojo.require("dijit.Dialog");
	 dojo.require("dijit.form.Button");
	 dojo.require("dijit.form.DateTextBox");
	 dojo.require("dijit.form.FilteringSelect");
	 dojo.require("dijit.form.Form");
	 dojo.require("dijit.form.TextBox");
	 dojo.require("dijit.form.ValidationTextBox");
	 dojo.require("dojo.data.ItemFileWriteStore");
	 dojo.require("dojo.data.ItemFileReadStore");
	 dojo.require("dojo.rpc.JsonService");
	 dojo.require("dojox.grid.DataGrid");

     var storeTipoDocumento = new dojo.data.ItemFileReadStore({url: "autocompletarAllTipoDocumento.action"});
 </script>


<div dojoType="dijit.Dialog" id="dlgBusquedaDocumentoReferenciado" jsId="dlgBusquedaDocumentoReferenciado" draggable="true" style="width:530px;" title="B&uacute;squeda de Documentos">
	<div id="camposBusquedaExpediente">
		<fieldset>
			<legend>Campos a buscar</legend> <br />
			<div id="camposIzquierda">
			     <label style="display:inline-block; width:112px; height:22px;" for="sExpedienteReferenciado">Nro. de Expediente</label>
				<input dojoType="dijit.form.TextBox"  id="sExpedienteReferenciado" maxlength="15" required="true" jsId="sExpedienteReferenciado" name="sExpedienteReferenciado" trim="true" />
				<br />
				  <label style="display:inline-block; width:112px; height:22px;" for="sTipoDocumentoReferenciado">Tipo</label>

				   <select dojoType="dijit.form.FilteringSelect"
			                id="sTipoDocumentoReferenciado"
			                name="sTipoDocumentoReferenciado"
			                value="<s:property value='sTipoDocumentoReferenciado_' />"
			                idAttr="id"
			                labelAttr="label"
			                style="width:179px;"
			                required="true"
							hasDownArrow="false"
			                searchAttr="label"
			                store="storeTipoDocumento"></select>

				<br />

				<label style="display:inline-block; width:112px; height:22px;" for="sDocumentoReferenciado">Nro</label>
				<input dojoType="dijit.form.TextBox"  id="sDocumentoReferenciado" maxlength="30" required="true" jsId="sDocumentoReferenciado" name="sDocumentoReferenciado" trim="true" />

			</div>
			<div id="camposDerecha">
				<input type="button" dojoType="dijit.form.Button" iconClass="dijitEditorIcon dijitEditorIconTabIndent" onClick="busquedaDocumentoReferenciado" showLabel="true" label="Filtrar"  />
				<input type="button" dojoType="dijit.form.Button" iconClass="dijitEditorIcon dijitEditorIconRemoveFormat" onClick="resetBusquedaDocumento" showLabel="true" label="Limpiar"  />

			</div>
		</fieldset>
	</div>
	<div id="resultadosBusquedaDocumento" style="height:190px;">
		<strong>Relaci&oacute;n de Documentos</strong>
		<table dojoType="dojox.grid.DataGrid"
             id="grdBusquedaDocumentoReferenciado"
             jsId="grdBusquedaDocumentoReferenciado"
             loadingMessage="<h2>Buscando documentos, espere un momento porfavor...</h2>"
             errorMessage="<h2>Ocurrio un error en el sistema</h2>"
             noDataMessage="<h2>No se encontraron Documentos</h2>"
             onRowClick="fillDataDocumento"
             rowSelector="10px">
			<thead>
				<tr>
				    <th field="id" hidden="true">Id</th>
					<th field="nrodocumento" width="225" editable="false">Documento</th>
					<th field="asunto" width="250" editable="false">Asunto</th>
					<th field="fecha" editable="false" align="center" formatter="formatterDate">Fecha Creaci&oacute;n</th>
				</tr>
			</thead>
		</table>

	</div>
</div>