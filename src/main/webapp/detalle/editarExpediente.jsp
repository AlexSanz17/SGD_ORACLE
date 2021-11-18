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
      
       //destroyWidgetsFromBusquedaDocumento();  JC VERIFICAR
       destroyWidgetsFromLegajo();

       var storeTipo = new dojo.data.ItemFileReadStore({url: "autocompletarTipoLegajo.action?iWithoutStor=0"});  
       var storeProcedimiento = new dojo.data.ItemFileReadStore({url: "autocompletarParametroActivo.action?codParametro=PROCEDIMIENTO_LEGAJO"});
       var storeMetodo = new dojo.data.ItemFileReadStore({url: "autocompletarParametroActivo.action?codParametro=METODO_LEGAJO"});
       
       function validarTipoExpediente(){
          if (dijit.byId("sTipoLegajo").attr("value")== "1"){
              document.getElementById("tbTipoProcedimiento").style.display = 'none';
              document.getElementById("tbTipoMetodo").style.display = 'none';
              document.getElementById("tbNroLegajo").style.display = 'none';
          }
          if (dijit.byId("sTipoLegajo").attr("value")== "2"){
              document.getElementById("tbNroLegajo").style.display = '';
              document.getElementById("tbTipoProcedimiento").style.display = '';
              document.getElementById("tbTipoMetodo").style.display = 'none';
          }
          if (dijit.byId("sTipoLegajo").attr("value")== "3"){
              document.getElementById("tbNroLegajo").style.display = '';
              document.getElementById("tbTipoMetodo").style.display = '';
              document.getElementById("tbTipoProcedimiento").style.display = 'none';
          }
       }
    
       function operacionActualizarLegajo(){
           
           if (dijit.byId("sTipoLegajo").attr("value") == ""){
               alert("El tipo de expediente es obligatorio.");
               return;
           }
           
           if (dijit.byId("sAsuntoLegajo").attr("value") == ""){
               alert("El asunto del expediente es obligatorio.");
               return;
           }
           
           if (dijit.byId("sTipoLegajo").attr("value") == "2"){
               if (dijit.byId("sTipoProcedimiento").attr("value") == ""){
                   alert("El procedimiento del expediente es obligatorio.");
                   return;
               }
           }
           
           if (dijit.byId("sTipoLegajo").attr("value") == "3"){
               if (dijit.byId("sTipoMetodo").attr("value") == ""){
                   alert("El metodo del expediente es obligatorio.");
                   return;
               }
           }
           
           if (dijit.byId("sTipoLegajo").attr("value") == "2"  ||  dijit.byId("sTipoLegajo").attr("value") == "3"){
              if (dijit.byId("sNroTipoLegajo").attr("value") == ""){ 
                 alert("El Nro de Expediente es obligatorio.");
                 return;
              } 
           }
           
           if (dijit.byId("sTipoLegajo").attr("value") == "2"  ||  dijit.byId("sTipoLegajo").attr("value") == "3"){
               if (dijit.byId("sNroTipoLegajo").attr("value") == "" || dijit.byId("sNroTipoLegajo").attr("value").length<6 || dijit.byId("sNroTipoLegajo").attr("value").length>10 || dijit.byId("sNroTipoLegajo").attr("value").substring(dijit.byId("sNroTipoLegajo").attr("value").length-5,dijit.byId("sNroTipoLegajo").attr("value").length-4)!="-"){
                   alert("El Formato del Nro de Expediente debe ser el siguiente : Nro-Año (Ej. 5-2019)");
                   return;
               }
               
               if (isNaN(dijit.byId("sNroTipoLegajo").attr("value").substring(dijit.byId("sNroTipoLegajo").attr("value").length -4,dijit.byId("sNroTipoLegajo").attr("value").length))){
                  alert("El Formato del Nro de Expediente debe ser el siguiente : Nro-Año (Ej. 5-2019)");
                  return; 
               }

               var valor = dijit.byId("sNroTipoLegajo").attr("value").substring(0, dijit.byId("sNroTipoLegajo").attr("value").indexOf("-"));
          
               if (isNaN(valor)){
                  alert("El Formato del Nro de Expediente debe ser el siguiente : Nro-Año (Ej. 5-2019)");
                  return;
               }
           }
           
            dijit.byId("registrarLegajo").attr("disabled", true); 
            var service = new dojo.rpc.JsonService("SMDAction.action");
            var defered = service.actualizarLegajo(parseInt(myidLegajo),
                                                   dijit.byId("sTipoLegajo").attr("value"),
                                                   dijit.byId("sTipoProcedimiento").attr("value"),
                                                   dijit.byId("sTipoMetodo").attr("value"),
                                                   dijit.byId("sNroTipoLegajo").attr("value"),
                                                   dijit.byId("sAsuntoLegajo").attr("value"),
                                                   dijit.byId("sObservacionLegajo").attr("value"),
                                                   dijit.byId("sNota1Legajo").attr("value"),
                                                   dijit.byId("sNota2Legajo").attr("value"),
                                                   dijit.byId("sNota3Legajo").attr("value"),
                                                   dijit.byId("sNota4Legajo").attr("value"));
            defered.addCallback(function(respuesta){ 
                if (respuesta == "0"){
                    alert("Ha ocurrido un error inesperado. Vuelva a intentarlo en un momento");
                    return;
                }
                if (respuesta == "1"){
                    alert("El expediente ha sido modificado satisfactoriamente.");
                    dijit.byId("registrarLegajo").attr("disabled", false); 
                    dijit.byId("dlgCrearLegajo").hide(); 
                    dijit.byId("dlgCrearLegajo").destroyRecursive();
                    showGridInbox(sTipoGridActual);
                }
                
                if (respuesta == "2"){
                    dijit.byId("registrarLegajo").attr("disabled", false); 
                    alert("El nro de expediente ya existe.");
                    return;
                }

                if (respuesta == "3"){
                    dijit.byId("registrarLegajo").attr("disabled", false); 
                    alert("El expediente no puede ser modificado debido a que se encuentra cerrado.");
                    return;
                }

                if (sTipoGridActual==TIPO_GRID_MI_LEGAJO){
                    viewDetailPersonalizado(myidLegajo);
                }
                if (sTipoGridActual==TIPO_GRID_LEGAJO_COMPARTIDO){
                    viewDetailPersonalizado(myidLegajo);
                }
           });
       }
    
      function operacionGrabarLegajo(){
          if (document.getElementById("idDocumentoExpediente").value==null ||  document.getElementById("idDocumentoExpediente").value==""){
              alert("Ha ocurrido un error inesperado. Vuelva a intentarlo en un momento");
              return;
           }
          
           if (dijit.byId("sTipoLegajo").attr("value") == ""){
               alert("El tipo de expediente es obligatorio.");
               return;
           }
           
           if (dijit.byId("sAsuntoLegajo").attr("value") == ""){
               alert("El asunto del expediente es obligatorio.");
               return;
           }
           
           if (dijit.byId("sTipoLegajo").attr("value") == "2"){
               if (dijit.byId("sTipoProcedimiento").attr("value") == ""){
                   alert("El procedimiento del expediente es obligatorio.");
                   return;
               }
           }
           
           if (dijit.byId("sTipoLegajo").attr("value") == "3"){
               if (dijit.byId("sTipoMetodo").attr("value") == ""){
                   alert("El metodo del expediente es obligatorio.");
                   return;
               }
           }
           
           if (dijit.byId("sTipoLegajo").attr("value") == "2"  ||  dijit.byId("sTipoLegajo").attr("value") == "3"){
               if (dijit.byId("sNroTipoLegajo").attr("value") == ""){ 
                 alert("El Nro de Expediente es obligatorio.");
                 return;
              } 
           }
           
           if (dijit.byId("sTipoLegajo").attr("value") == "2"  ||  dijit.byId("sTipoLegajo").attr("value") == "3"){
               if (dijit.byId("sNroTipoLegajo").attr("value") == "" || dijit.byId("sNroTipoLegajo").attr("value").length<6 || dijit.byId("sNroTipoLegajo").attr("value").length>10 || dijit.byId("sNroTipoLegajo").attr("value").substring(dijit.byId("sNroTipoLegajo").attr("value").length-5,dijit.byId("sNroTipoLegajo").attr("value").length-4)!="-"){
                   alert("El Formato del Nro de Expediente debe ser el siguiente : Nro-Año (Ej. 5-2019)");
                   return;
               }
               
               if (isNaN(dijit.byId("sNroTipoLegajo").attr("value").substring(dijit.byId("sNroTipoLegajo").attr("value").length -4,dijit.byId("sNroTipoLegajo").attr("value").length))){
                  alert("El Formato del Nro de Expediente debe ser el siguiente : Nro-Año (Ej. 5-2019)");
                  return; 
               }

               var valor = dijit.byId("sNroTipoLegajo").attr("value").substring(0, dijit.byId("sNroTipoLegajo").attr("value").indexOf("-"));
          
               if (isNaN(valor)){
                  alert("El Formato del Nro de Expediente debe ser el siguiente : Nro-Año (Ej. 5-2019)");
                  return;
               }
           }
           
           dijit.byId("registrarLegajo").attr("disabled", true); 
           var valorsito = document.getElementById("idDocumentoExpediente").value;
           var service = new dojo.rpc.JsonService("SMDAction.action");
           var defered = service.saveLegajo(dijit.byId("sTipoLegajo").attr("value"),
                                            dijit.byId("sTipoProcedimiento").attr("value"),
                                            dijit.byId("sTipoMetodo").attr("value"),
                                            dijit.byId("sNroTipoLegajo").attr("value"),
                                            document.getElementById("idDocumentoExpediente").value,
                                            dijit.byId("sAsuntoLegajo").attr("value"),
                                            dijit.byId("sObservacionLegajo").attr("value"),
                                            dijit.byId("sNota1Legajo").attr("value"),
                                            dijit.byId("sNota2Legajo").attr("value"),
                                            dijit.byId("sNota3Legajo").attr("value"),
                                            dijit.byId("sNota4Legajo").attr("value"));
                defered.addCallback(function(respuesta){ 
                if (respuesta == "0"){
                    alert("Ha ocurrido un error inesperado. Vuelva a intentarlo en un momento");
                    dijit.byId("registrarLegajo").attr("disabled", false); 
                    return;
                }
                
                if (respuesta == "1"){
                    alert("El expediente ha sido creado satisfactoriamente.");
                    dijit.byId("registrarLegajo").attr("disabled", false); 
                    dijit.byId("dlgCrearLegajo").hide(); 
                    dijit.byId("dlgCrearLegajo").destroyRecursive();
                }
                
                 if (respuesta == "2"){
                    alert("El nro de expediente ya existe.");
                    dijit.byId("registrarLegajo").attr("disabled", false); 
                    return;
                }
               
                if (sTipoGridActual== TIPO_GRID_RECIBIDOS){
                    viewDetailPersonalizado(valorsito);
                }    
                if (sTipoGridActual==TIPO_GRID_EXPEDIENTES){
                    viewDetailPersonalizado(valorsito);
                }    
                if (sTipoGridActual==TIPO_GRID_FIRMAR){
                    viewDetailPersonalizado(valorsito);
                }    
                if (sTipoGridActual==TIPO_GRID_MI_LEGAJO){
                    viewDetailPersonalizado(valorsito);
                }
           });
      }
    
      function grabarLegajo(){
           if (document.getElementById("accionLegajo").value=="A"){
              operacionActualizarLegajo(); 
           }else{
              operacionGrabarLegajo(); 
           }
      }
 </script>
 
<div dojoType="dijit.Dialog" id="dlgCrearLegajo" jsId="dlgCrearLegajo" draggable="true" style="width:580px;height:255px" title="Crear Expediente">
	<div id="camposBusquedaLegajo">
                <input type="hidden" id="idDocumentoExpediente" name="idDocumentoExpediente" value=""/>
                <input type="hidden" id="accionLegajo" name="accionLegajo" value=""/>
		<fieldset>
			<div id="camposIzquierda">
                           <table>
                               <tr>
                                 <td width="100px">Tipo</td>
                                 <td><select dojoType="dijit.form.FilteringSelect"
			                id="sTipoLegajo"
			                name="sTipoLegajo"
			                idAttr="id"
			                labelAttr="label"
			                style="width:425px;"
                                        onChange="validarTipoExpediente"
			                required="false"
                                        hasDownArrow="true"
			                searchAttr="label"
			                store="storeTipo"></select>
                                  </td>
                               </tr>
                               <tbody id="tbTipoProcedimiento" style="display:none;">
                                    <tr>
                                      <td width="100px">Procedimiento</td>
                                      <td><select dojoType="dijit.form.FilteringSelect"
                                             id="sTipoProcedimiento"
                                             name="sTipoProcedimiento"
                                             idAttr="id"
                                             labelAttr="label"
                                             style="width:425px;"
                                             required="false"
                                             hasDownArrow="true"
                                             searchAttr="label"
                                             store="storeProcedimiento"></select>
                                       </td>
                                    </tr>
                               </tbody>
                               <tbody id="tbTipoMetodo" style="display:none;">
                                    <tr>
                                      <td width="100px">Metodo</td>
                                      <td><select dojoType="dijit.form.FilteringSelect"
                                             id="sTipoMetodo"
                                             name="sTipoMetodo"
                                             idAttr="id"
                                             labelAttr="label"
                                             style="width:425px;"
                                             required="false"
                                             hasDownArrow="true"
                                             searchAttr="label"
                                             store="storeMetodo"></select>
                                       </td>
                                    </tr>
                               </tbody>
                               <tbody id="tbNroLegajo" style="display:none;">
                                    <tr>
                                        <td width="100px">Nro</td>
                                        <td><input dojoType="dijit.form.TextBox" id="sNroTipoLegajo" jsId="sNroTipoLegajo" uppercase="true" name="sNroTipoLegajo" trim="true" required="true" maxlength="800" style="width: 425px;"/></td>
                                    </tr>
                               </tbody>
                               <tr>
				   <td width="100px">Asunto</td>
                                   <td><input dojoType="dijit.form.TextBox" id="sAsuntoLegajo" jsId="sAsuntoLegajo" uppercase="true" name="sAsuntoLegajo" trim="true" required="true" maxlength="800" style="width: 425px;"/></td>
                               </tr>
                               <tr>
                                   <td width="100px">Observaci&oacute;n</td>
                                   <td><input dojoType="dijit.form.TextBox" id="sObservacionLegajo" jsId="sObservacionLegajo" uppercase="true" name="sObservacionLegajo" style="width: 425px;" trim="true" required="false" maxlength="800"/></td>
                               </tr>	
                               
                               <tr>
                                    <td colspan="2" style="height:1px"> <hr/> </td>
                                 </tr>  
                               
                               <tr>
                                   <td width="100px">Nota 1</td>
                                   <td><input dojoType="dijit.form.TextBox" id="sNota1Legajo" jsId="sNota1Legajo" uppercase="true" name="sNota1Legajo" style="width: 425px;" trim="true" required="false" maxlength="800"/></td>
                               </tr>	
                               
                               <tr>
                                   <td width="100px">Nota 2</td>
                                   <td><input dojoType="dijit.form.TextBox" id="sNota2Legajo" jsId="sNota2Legajo" uppercase="true" name="sNota2Legajo" style="width: 425px;" trim="true" required="false" maxlength="800"/></td>
                               </tr>	
                               
                               <tr>
                                   <td width="100px">Nota 3</td>
                                   <td><input dojoType="dijit.form.TextBox" id="sNota3Legajo" jsId="sNota3Legajo" uppercase="true" name="sNota3Legajo" style="width: 425px;" trim="true" required="false" maxlength="800"/></td>
                               </tr>	
                               
                               <tr>
                                   <td width="100px">Nota 4</td>
                                   <td><input dojoType="dijit.form.TextBox" id="sNota4Legajo" jsId="sNota4Legajo" uppercase="true" name="sNota4Legajo" style="width: 425px;" trim="true" required="false" maxlength="800"/></td>
                               </tr>	
                               
                               <tr>
                                   <td colspan="2" height="6px"></td>
                               </tr>
                               
                               <tr>
                                   <td colspan="2"><input type="button" id="registrarLegajo" dojoType="dijit.form.Button" iconClass="siged16 sigedSave16" onClick="grabarLegajo()" showLabel="true" label="Registrar"  /></td> 
                               </tr>
                        
                                
                           </table>
                        </div> 
		</fieldset>
	</div>
   
	
</div> 