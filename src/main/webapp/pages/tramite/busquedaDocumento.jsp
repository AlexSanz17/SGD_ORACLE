<style>
	.soria .dojoxGridContent .dojoxGridCell {
		cursor: pointer;
	}
	.dojoxGrid-row-odd td {
		background:#e8f2ff;
	}
</style>
<script type="text/javascript" src="js/destruirObjetos.js"></script>
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
    
      destroyWidgetsFromBusquedaDocumento();
     
      var storeTipoDocumento = new dojo.data.ItemFileReadStore({url: "autocompletarTipoDocumentoTodos.action?iWithoutStor=1"});
      var storeTipoLegajo = new dojo.data.ItemFileReadStore({url: "autocompletarTipoLegajoBusqueda.action"});
      var bandera = true;
      var banderaAlterno = true;
     
      function agregarDocumentoPorReferencia(e){
           var x = document.getElementById("listaDocReferencias");
           
           for (var i=0; i<x.options.length;i++){
                   if (x.options[i].value==grdBusquedaDocumento.getItem(e.rowIndex).id){
		     alert("El documento ya fue agregado");
      		     return;
		   }
	   }

          var texto = grdBusquedaDocumento.getItem(e.rowIndex).nroDocumento + "";
          var valor = grdBusquedaDocumento.getItem(e.rowIndex).id;
          $("#listaDocReferencias").append($("<option></option>")
                  .attr("value", valor)
                  .text(texto));
           
          dijit.byId("dlgBusquedaDocumento").hide();
          //dijit.byId("dlgBusquedaDocumento").destroyRecursive();
     }
     
     function agregarDocumentoPorExpediente(e){
         if (bandera){
                bandera = false; 
           
                if (confirm("¿Desea agregar el documento al expediente?")){ 
                    if (banderaAlterno){
                        banderaAlterno = false;
                        var service = new dojo.rpc.JsonService("SMDAction.action");
                        var defered = service.agregarDocumentoToExpediente(idLegajoDinamico, parseInt(dijit.byId("grdBusquedaDocumento").getItem(e.rowIndex).id));
                        defered.addCallback(function(respuesta){ 

                           if (respuesta == "1"){ 
                              alert("El documento ha sido agregado satisfactoriamente al expediente");
                              bandera = true;
                              banderaAlterno = true;
                              dijit.byId("dlgBusquedaDocumento").hide(); 
                              dijit.byId("dlgBusquedaDocumento").destroyRecursive();
                           }

                           if (respuesta == "0"){  
                              alert("Ha ocurrido un error inesperado. Vuelva a intentarlo en un momento");
                              bandera = true;
                              banderaAlterno = true;
                              return;
                           }

                           if (respuesta == "2"){ 
                             alert("El documento ya se encuentra agregado en el expediente.");
                             bandera = true; 
                             banderaAlterno = true;
                             return;
                           }

                           if (respuesta == "3"){ 
                              alert("No puede agregar un documento, el expediente ya se encuentra cerrado");
                              bandera = true;
                              banderaAlterno = true;
                              return;
                           }

                           if (sTipoGridActual== TIPO_GRID_RECIBIDOS){
                               viewDetailPersonalizado(myidDocumento);
                           }    
                           if (sTipoGridActual==TIPO_GRID_EXPEDIENTES){
                               viewDetailPersonalizado(myidDocumento);
                           }    
                           if (sTipoGridActual==TIPO_GRID_FIRMAR){
                               viewDetailPersonalizado(myidDocumento);
                           }    
                           if (sTipoGridActual==TIPO_GRID_MI_LEGAJO){
                               viewDetailPersonalizado(myidLegajo);
                           }
                           if (sTipoGridActual==TIPO_GRID_LEGAJO_COMPARTIDO){
                               viewDetailPersonalizado(myidLegajo);
                           }
                       });
                   }   
               }else{
                   bandera = true;
               }   
          }    
     }
     
     function agregarDocumento(e) {
           if (e.rowIndex == undefined) {
              alert("Debe seleccionar el documento a agregar."); 
	      return;
	   }
           
           if (document.getElementById("accionBusquedaDocumento").value == "R"){
               agregarDocumentoPorReferencia(e);
           }
           
           if (document.getElementById("accionBusquedaDocumento").value == "E"){
               agregarDocumentoPorExpediente(e);
           }
     }
        
     /*   
     function resetBusquedaDocumento(){
           if (dijit.byId("sNroDocumento")) {
             dijit.byId("sNroDocumento").attr("value", "");
           }
           if (dijit.byId("sNroHT")) {
             dijit.byId("sNroHT").attr("value", "");
           }
           if (dijit.byId("sTipoDocumento")) {
             dijit.byId("sTipoDocumento").attr("value", "");
           }
            if (dijit.byId("sTipoExpediente")) {
             dijit.byId("sTipoExpediente").attr("value", "");
           }
           if (dijit.byId("sNroLegajo")) {
             dijit.byId("sNroLegajo").attr("value", "");
           }    
      } */

     
      function busquedaDocumento(llave) {
           var parameters = "";
            
           if (dijit.byId("sTipoDocumento").getValue().trim()=='' && dijit.byId("sNroDocumento").getValue().trim()=='' && dijit.byId("sTipoExpediente").getValue().trim()=='' &&
               dijit.byId("sNroHT").getValue().trim()=='' && dijit.byId("sExpediente").getValue().trim()=='' && dijit.byId("sNroLegajo").getValue().trim()==''){
               alert("Debe ingresar por lo menos un dato.");
               return;
           }

	   if ((dijit.byId("sTipoDocumento").getValue().trim()!='' && dijit.byId("sNroDocumento").getValue().trim()=='') ||
               (dijit.byId("sTipoDocumento").getValue().trim()=='' && dijit.byId("sNroDocumento").getValue().trim()!='')){
	       alert("Debe ingresar el campo tipo y número de documento");
	       return;
	   }
  
	   parameters += "tipoDocumento=" + dijit.byId("sTipoDocumento").getValue() + "&";
	   parameters += "sNroDocumento=" +     dijit.byId("sNroDocumento").getValue() + "&";
           parameters += "sNroHT=" + dijit.byId("sNroHT").getValue() + "&";
           parameters += "sNroExpediente=" + dijit.byId("sExpediente").getValue() + "&";
           parameters += "sNroLegajo=" + dijit.byId("sNroLegajo").getValue() + "&";
           parameters += "sTipoLegajo=" + dijit.byId("sTipoExpediente").getValue();

           dijit.byId(llave).attr("disabled", true); 
           var strBusquedaDocumento = new dojo.data.ItemFileReadStore({
	      url: "buscarDocumentoReferenciado.action?" + parameters, clearOnClose: true, urlPreventCache:true
	   });
           
            strBusquedaDocumento.close();
            
            var sortAttributes = [{ attribute: "id", descending: true}];
            strBusquedaDocumento.fetch({onComplete: completed, onError: error, sort: sortAttributes});
                 
            function completed(items, findResult){ 
               var grid = dijit.byId("grdBusquedaDocumento");
               grid.setStore(strBusquedaDocumento);
               grid._refresh();
               dijit.byId(llave).attr("disabled", false);
            }
			
	    function error(errData, request){ 
	       console.log(errData+" ... Failed cargo."+request);
	    }

      }
 </script>
<div dojoType="dijit.Dialog" id="dlgBusquedaDocumento" jsId="dlgBusquedaDocumento" draggable="true" style="width:590px;height:362px" title="B&uacute;squeda de Documentos">
	<div id="camposBusquedaDocumento">
                <input type="hidden" id="accionBusquedaDocumento" name="accionBusquedaDocumento" value=""/>
	 	<fieldset> 
			<div id="camposIzquierda">
                            <table>
                                <tr>
                                    <td width="180px"> N&uacute;mero de Expediente</td> 
                                    <td><input dojoType="dijit.form.TextBox" id="sNroLegajo" jsId="sNroLegajo" name="sNroLegajo" trim="true" maxlength="12" required="false" style="width:140px"/></td>    
                                    <td width="13px"></td>
                                    <td width="40px"> Tipo</td> 
                                    <td><select dojoType="dijit.form.FilteringSelect"
			                id="sTipoExpediente"
			                name="sTipoExpediente"
			                idAttr="id"
			                labelAttr="label"
			                style="width:200px;"
			                required="false"
					hasDownArrow="false"
			                searchAttr="label"
			                store="storeTipoLegajo"></select></td>    
                               </tr>
                               
                                <tr>
                                    <td colspan="5" style="height:1px"> <hr/> </td>
                                 </tr>  
                               
                               <tr>
                                   <td>N&uacute;mero de Documento</td>
                                   <td><input dojoType="dijit.form.TextBox" id="sNroDocumento" jsId="sNroDocumento" name="sNroDocumento" trim="true" required="false" maxlength="60" style="width:140px"/></td>
                                   <td width="13px"></td>
                                   <td width="40px">Tipo</td>
                                   <td> <select dojoType="dijit.form.FilteringSelect"
			                id="sTipoDocumento"
			                name="sTipoDocumento"
			                idAttr="id"
			                labelAttr="label"
			                style="width:200px;"
			                required="false"
					hasDownArrow="false"
			                searchAttr="label"
			                store="storeTipoDocumento"></select>
                                    </td>
                               </tr>
                               
                                <tr>
                                    <td width="180px"> N&uacute;mero de Carpeta</td> 
                                    <td><input dojoType="dijit.form.TextBox" id="sExpediente" jsId="sExpediente" name="sExpediente" trim="true" maxlength="12" required="false" style="width:140px"/></td>    
                               </tr>
                               
                               <tr> 
                                   <td>Nro. Tr&aacute;mite</td>
                                   <td> <input dojoType="dijit.form.TextBox" id="sNroHT" jsId="sNroHT" name="sNroHT" trim="true" required="false" maxlength="10" style="width:140px"/></td>
                               </tr>
                               <tr>
                                   <td colspan="2" height="6px"></td>
                               </tr>
                               <tr>
                                   <td> 
                                       <input type="button" dojoType="dijit.form.Button" iconClass="dijitEditorIcon dijitEditorIconTabIndent" onClick="busquedaDocumento(this.id)" showLabel="true" label="Filtrar"  />
				         <input type="button" dojoType="dijit.form.Button" iconClass="dijitEditorIcon dijitEditorIconRemoveFormat" onClick="resetBusquedaDocumento()" showLabel="true" label="Limpiar"/>  
                                   </td>
                                   <td></td>
                               </tr>  
                             </table>    
                        </div>
	  	</fieldset> 
	</div>
   
	<div id="resultadosBusquedaDocumento">
		<strong>Relaci&oacute;n de Documentos</strong>
		
		<table height="175px" dojoType="dojox.grid.DataGrid" 
                        id="grdBusquedaDocumento"
                        jsId="grdBusquedaDocumento"
                        onRowClick="agregarDocumento"
                        loadingMessage="<h2>Buscando documentos, espere un momento porfavor...</h2>"
                        errorMessage="<h2>Ocurrio un error en el sistema</h2>"
                        noDataMessage="<h2>No se encontraron documentos</h2>"
                        rowSelector="10px">
			<thead>
				<tr>
					<th field="id" width="38">Id</th>
					<th field="nroDocumento" width="210" editable="false">N&uacute;mero</th>
                                        <th field="asuntoDocumento" width="293">Asunto</th>
					
				</tr>
			</thead>
		</table>

	</div>
</div>