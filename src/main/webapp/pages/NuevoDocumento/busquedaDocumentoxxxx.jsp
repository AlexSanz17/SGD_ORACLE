<style>
	.soria .dojoxGridContent .dojoxGridCell {
		cursor: pointer;
	}
	.dojoxGrid-row-odd td {
		background:#e8f2ff;
	}
</style>

<script type="text/javascript">
    function resetBusquedaDocumento(){
    	dijit.byId("sTipoDocumentoFedatear").attr("value", "");
    	dijit.byId("sAreaDocumentoFedatear").attr("value", "");
    	dijit.byId("sDocumentoFedatear").attr("value", "");
    }

    function sinDocumento(){
       showTBODY("tbDocumentoFedatear");
       showTBODY("tbformularioFedatear");

       dijit.byId("objDF.nroDocumentoArea").attr("value", "");
       dijit.byId("objDF.idTipoDocumentoFedateado").attr("value", "");
       dijit.byId("objDF.nroDocumento").attr("value", "");
       dijit.byId("objDF.asunto").attr("value", "");
       dijit.byId("objDF.fechaDocumentoRegistro").attr("value", "");
       dlgBusquedaDocumentoFedatear.hide();
    }

    function fillDataDocumento(e) {
	   if (e.rowIndex == undefined) {
	      return;
	   }

	   showTBODY("tbformularioFedatear");
	   dojo.byId("objDF.idDocumentoFedateado").value = grdBusquedaDocumentoFedateado.getItem(e.rowIndex).id;
	   dijit.byId("objDF.nroDocumentoArea").attr("value", grdBusquedaDocumentoFedateado.getItem(e.rowIndex).nrodocumento);
	   hideTBODY("tbDocumentoFedatear");
	   dlgBusquedaDocumentoFedatear.hide();
	}

	busquedaDocumento = function() {
	   var parameters = "";

	   if (dijit.byId("sTipoDocumentoFedatear").getValue().trim()=='' || dijit.byId("sAreaDocumentoFedatear").getValue().trim()=='' || dijit.byId("sDocumentoFedatear").getValue().trim()=='' ){
	     alert("Debe ingresar todos los campos correctamente para la busqueda");
	     return;
	   }

	   parameters += "tipoDocumento=" + dijit.byId("sTipoDocumentoFedatear").getValue() + "&";
	   parameters += "idUnidad=" + dijit.byId("sAreaDocumentoFedatear").getValue() + "&";
	   parameters += "sNroDocumento=" +     dijit.byId("sDocumentoFedatear").getValue();

	   strBusquedaDocumento = new dojo.data.ItemFileReadStore({
	      url: "buscarDocumentoFedatario.action?" + parameters
	   });

	   grdBusquedaDocumentoFedateado.setStore(strBusquedaDocumento);
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

     var storeTipoDocumento = new dojo.data.ItemFileReadStore({url: "autocompletarTipoDocumentoFedatario.action"});
     var storeAreaDocumento = new dojo.data.ItemFileReadStore({url: "autocompletarAreaDestino.action"});
 </script>


<div dojoType="dijit.Dialog" id="dlgBusquedaDocumentoFedatear" jsId="dlgBusquedaDocumentoFedatear" draggable="true" style="width:590px;" title="B&uacute;squeda de Documentos">
	<div id="camposBusquedaExpediente">
		<fieldset>
			<legend>Campos a buscar</legend>
			<div id="camposIzquierda">
				  <label style="display:inline-block; width:90px; height:22px;" for="sTipoDocumentoFedatear">Tipo</label>

				   <select dojoType="dijit.form.FilteringSelect"
			                id="sTipoDocumentoFedatear"
			                name="sTipoDocumentoFedatear"
			                value="<s:property value='sTipoDocumentoFedatear_' />"
			                idAttr="id"
			                labelAttr="label"
			                style="width:179px;"
			                required="true"
					hasDownArrow="false"
			                searchAttr="label"
			                store="storeTipoDocumento"></select>

				<br />

				<label style="display:inline-block; width:90px; height:22px;" for="sDocumentoFedatear">Nro</label>
				<input dojoType="dijit.form.TextBox"  id="sDocumentoFedatear" maxlength="30" required="true" jsId="sDocumentoFedatear" name="sDocumentoFedatear" trim="true" />
				<br />

				<label style="display:inline-block; width:90px; height:22px;" for="sAreaDocumentoFedatear">Area Autor</label>
				 <select dojoType="dijit.form.FilteringSelect"
			                id="sAreaDocumentoFedatear"
			                name="sAreaDocumentoFedatear"
			                value="<s:property value='sAreaDocumentoFedatear_' />"
			                idAttr="id"
			                labelAttr="label"
			                style="width:179px;"
			                required="true"
							hasDownArrow="false"
			                searchAttr="label"
			                store="storeAreaDocumento"></select>

				<br/><br/>

				<font color="blue"><b>Observacion: </b>En caso de no encontrar el documento, </font><br/>
				 <font color="blue"> presione </font> <a href="javascript:sinDocumento();"><font color="blue"> <b>aqui </b> </font> </a>
			</div>
			<div id="camposDerecha">
				<input type="button" dojoType="dijit.form.Button" iconClass="dijitEditorIcon dijitEditorIconTabIndent" onClick="busquedaDocumento" showLabel="true" label="Filtrar"  />
				<input type="button" dojoType="dijit.form.Button" iconClass="dijitEditorIcon dijitEditorIconRemoveFormat" onClick="resetBusquedaDocumento" showLabel="true" label="Limpiar"  />

			</div>
		</fieldset>
	</div>
	<div id="resultadosBusquedaDocumento" style="height:190px;">
		<strong>Relaci&oacute;n de Documentos</strong>
		<table dojoType="dojox.grid.DataGrid"
             id="grdBusquedaDocumentoFedateado"
             jsId="grdBusquedaDocumentoFedateado"
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