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

     var flag = true;
     var flagAlterno = true;
     destroyWidgetsFromLegajo();
     var storeTipoDocumento = new dojo.data.ItemFileReadStore({url: "autocompletarTipoDocumentoTodos.action?iWithoutStor=1"});
     var storeTipoLegajo = new dojo.data.ItemFileReadStore({url: "autocompletarTipoLegajoBusqueda.action"});
      
     function agregarLegajo(e) { 
         if (flag){
                flag = false;
             
                if (e.rowIndex == undefined) {
                  flag = true;  
                  return;
                }

                if (document.getElementById("idDocumentoExpediente").value==null ||  document.getElementById("idDocumentoExpediente").value==""){ 
                   alert("Ha ocurrido un error inesperado. Vuelva a intentarlo en un momento");
                   flag = true;
                   return;
                }

                if (confirm("¿Desea agregar el documento al expediente?")){
                    if (flagAlterno){
                      var valorsito = document.getElementById("idDocumentoExpediente").value;  
                      flagAlterno = false;  
                      var service = new dojo.rpc.JsonService("SMDAction.action");
                      var defered = service.agregarDocumentoToExpediente(parseInt(dijit.byId("grdBusquedaLegajo").getItem(e.rowIndex).id), parseInt(document.getElementById("idDocumentoExpediente").value));
                      defered.addCallback(function(respuesta){ 

                          if (respuesta == "1"){
                             alert("El documento ha sido agregado satisfactoriamente al expediente");
                             flag = true;
                             flagAlterno = true;
                             dijit.byId("dlgBusquedaLegajo").hide(); 
                             dijit.byId("dlgBusquedaLegajo").destroyRecursive();
                          }

                          if (respuesta == "0"){
                            alert("Ha ocurrido un error inesperado. Vuelva a intentarlo en un momento");
                            flag = true;
                            flagAlterno = true;
                            return;
                          }

                          if (respuesta == "2"){
                            alert("El documento ya se encuentra agregado en el expediente.");
                            flag = true;
                            flagAlterno = true;
                            return;
                          }

                          if (respuesta == "3"){
                            alert("No puede agregar un documento, el expediente ya se encuentra cerrado");
                            flag = true;
                            flagAlterno = true;
                            return;
                          }
                          
                          if (sTipoGridActual== TIPO_GRID_RECIBIDOS){
                              viewDetailPersonalizado(parseInt(valorsito));
                          }    
                          if (sTipoGridActual==TIPO_GRID_EXPEDIENTES){
                              viewDetailPersonalizado(parseInt(valorsito));
                          }    
                          if (sTipoGridActual==TIPO_GRID_FIRMAR){
                              viewDetailPersonalizado(parseInt(valorsito));
                          }    
                      });
                   }  
                }else{
                    flag = true;
                }  
         }   
     }
        
     function resetBusquedaLegajo(){
           if (dijit.byId("sLegajo")) {
             dijit.byId("sLegajo").attr("value", "");
           }
           if (dijit.byId("sNroDocumentoLegajo")) {
             dijit.byId("sNroDocumentoLegajo").attr("value", "");
           }
           if (dijit.byId("sNroHTLegajo")) {
             dijit.byId("sNroHTLegajo").attr("value", "");
           }
           if (dijit.byId("sTipoDocumentoLegajo")) {
             dijit.byId("sTipoDocumentoLegajo").attr("value", "");
           }
            if (dijit.byId("sTipoExpediente_")) {
             dijit.byId("sTipoExpediente_").attr("value", "");
           }
       } 

      function busquedaLegajo(llave) {
           var parameters = "";
           
           if (dijit.byId("sTipoDocumentoLegajo").getValue().trim()=='' && dijit.byId("sNroDocumentoLegajo").getValue().trim()=='' &&
               dijit.byId("sNroHTLegajo").getValue().trim()=='' && dijit.byId("sLegajo").getValue().trim()=='' && dijit.byId("sTipoExpediente_").getValue().trim()==''){
               alert("Debe ingresar por lo menos un dato.");
               return;
           }

	   if ((dijit.byId("sTipoDocumentoLegajo").getValue().trim()!='' && dijit.byId("sNroDocumentoLegajo").getValue().trim()=='') ||
               (dijit.byId("sTipoDocumentoLegajo").getValue().trim()=='' && dijit.byId("sNroDocumentoLegajo").getValue().trim()!='')){
	       alert("Debe ingresar el campo tipo y número de documento");
	       return;
	   }
  
	   parameters += "tipoDocumento=" + dijit.byId("sTipoDocumentoLegajo").getValue() + "&";
	   parameters += "sNroDocumento=" +     dijit.byId("sNroDocumentoLegajo").getValue() + "&";
           parameters += "sNroHT=" + dijit.byId("sNroHTLegajo").getValue() + "&";
           parameters += "sNroExpediente=" + dijit.byId("sLegajo").getValue() + "&";
           parameters += "sTipoLegajo=" + dijit.byId("sTipoExpediente_").getValue() + "&";

           dijit.byId(llave).attr("disabled", true); 
           var strBusquedaLegajo = new dojo.data.ItemFileReadStore({
	      url: "buscarLegajo.action?" + parameters, clearOnClose: true, urlPreventCache:true
	   });
           
            strBusquedaLegajo.close();
            
            var sortAttributes = [{ attribute: "id", descending: true}];
            strBusquedaLegajo.fetch({onComplete: completed, onError: error, sort: sortAttributes});
                 
            function completed(items, findResult){ 
               var grid = dijit.byId("grdBusquedaLegajo");
               grid.setStore(strBusquedaLegajo);
               grid._refresh();
               dijit.byId(llave).attr("disabled", false);
            }
			
	    function error(errData, request){ 
	       console.log(errData+" ... Failed cargo."+request);
	    }
            
	}
 </script>
 
<div dojoType="dijit.Dialog" id="dlgBusquedaLegajo" jsId="dlgBusquedaLegajo" draggable="true" style="width:595px;height:348px" title="B&uacute;squeda de Expedientes">
	<div id="camposBusquedaLegajo">
		<fieldset>
			
			<div id="camposIzquierda">
                                   <input type="hidden" id="idDocumentoExpediente" name="idDocumentoExpediente" value=""/>
                                   <table>
                                       <tr>
                                           <td width="180px">
                                               N&uacute;mero de Expediente
                                           </td>
                                           <td>
                                                <input dojoType="dijit.form.TextBox" id="sLegajo" jsId="sLegajo" name="sLegajo" trim="true" required="false" maxlength="12" style="width:140px"/>
                                           </td>
                                            <td width="13px"></td>
                                           <td width="40px">
                                               Tipo
                                           </td>
                                           <td>
                                                <select dojoType="dijit.form.FilteringSelect"
                                                id="sTipoExpediente_"
                                                name="sTipoExpediente_"
                                                idAttr="id"
                                                labelAttr="label"
                                                style="width:200px;"
                                                required="false"
                                                hasDownArrow="false"
                                                searchAttr="label"
                                                store="storeTipoLegajo"></select>
                                           </td>
                                       </tr>
                                       
                                        <tr>
                                            <td colspan="5" style="height:1px"> <hr/> </td>
                                         </tr>  
                                       
                                        <tr>
                                           <td>
                                               N&uacute;mero de Documento
                                           </td>
                                           <td>
                                               <input dojoType="dijit.form.TextBox" id="sNroDocumentoLegajo" jsId="sNroDocumentoLegajo" name="sNroDocumento" trim="true" required="false" maxlength="60" style="width:140px"/>
                                           </td> 
                                            <td width="13px"></td>
                                           <td width="40px">
                                               Tipo
                                           </td>
                                           <td>
                                                <select dojoType="dijit.form.FilteringSelect"
                                                    id="sTipoDocumentoLegajo"
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
                                           <td>
                                               Nro. Tr&aacute;mite
                                           </td>
                                           <td>
                                                <input dojoType="dijit.form.TextBox" id="sNroHTLegajo" jsId="sNroHTLegajo" name="sNroHT" trim="true" required="false" maxlength="10" style="width:140px"/>
                                           </td>
                                       </tr>
                                       <tr>
                                            <td colspan="2" height="6px"></td>
                                       </tr>
                                       <tr>
                                            <td> 
                                               <input type="button" dojoType="dijit.form.Button" iconClass="dijitEditorIcon dijitEditorIconTabIndent" onClick="busquedaLegajo(this.id)" showLabel="true" label="Filtrar"  />
                                               <input type="button" dojoType="dijit.form.Button" iconClass="dijitEditorIcon dijitEditorIconRemoveFormat" onClick="resetBusquedaLegajo()" showLabel="true" label="Limpiar"/>   
                                            </td>
                                            <td></td>
                                      </tr>  
                                   </table>
				
                        </div> 
		</fieldset>
	</div>
   
	<div id="resultadosBusquedaLegajo">
		<strong>Relaci&oacute;n de Expedientes</strong>
		
		<table height="180px" dojoType="dojox.grid.DataGrid" 
                        id="grdBusquedaLegajo"
                        jsId="grdBusquedaLegajo"
                        onRowClick="agregarLegajo"
                        loadingMessage="<h2>Buscando expedientes, espere un momento porfavor...</h2>"
                        errorMessage="<h2>Ocurrio un error en el sistema</h2>"
                        noDataMessage="<h2>No se encontraron expedientes</h2>"
                        rowSelector="10px">
			<thead>
				<tr>
					<th field="id" width="38">Id</th>
					<th field="nroLegajo" width="90" editable="false">Expediente</th>
                                        <th field="tipoLegajo" width="78">Tipo</th>
                                        <th field="unidadLegajo" width="78">Unidad</th>
                                        <th field="asuntoLegajo" width="285">Asunto</th>				
				</tr>
			</thead>
		</table>

	</div>  
</div> 