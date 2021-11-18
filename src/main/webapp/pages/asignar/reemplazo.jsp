<?xml version="1.0" encoding="UTF-8" ?>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es">
   <head>
      <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
      <title>Tramite Documentario - Asignar Reemplazo</title>
      <link rel="stylesheet" type="text/css" href="css/estilo.css" />
      <%--<script type="text/javascript" src="js/dojo/dojo/dojo.js" djConfig="parseOnLoad:true, isDebug: false"></script>
      <script type="text/javascript" src="js/dojo/dijit/dijit.js" djConfig="parseOnLoad:true, isDebug: false"></script>--%>

      <%--<script type="text/javascript" src='js/siged/date-es-PE.js'></script>--%>
      <script type="text/javascript" src='js/valida.js'></script>
      <s:url id="autoUrl" action="autocompletarProcesoParticipante" />
      <s:url id="listaUsuarios" action="autocompletarUsuarioxProceso" />



      <script type="text/javascript">
         /*dojo.require("dojo.rpc.JsonService");
         dojo.require("dijit.Dialog");
         dojo.require("dijit.form.TextBox");
         dojo.require("dijit.form.Button");
         dojo.require("dijit.form.DateTextBox");
          
         dojo.require("dijit.form.FilteringSelect" );
         dojo.require("dojo.data.ItemFileWriteStore");
         dojo.require("dojo.data.ItemFileReadStore");
         dojo.require("dojox.grid.DataGrid");*/
         dojo.require("dijit.Dialog");
		  dojo.require("dijit.ProgressBar");
  
        
         var jsonStore = new dojo.data.ItemFileWriteStore({url: "<s:property value='autoUrl' />"});
         var storeUsuarios;

         var idproceso;
       
      </script>


      <script type="text/javascript">

         function formaterEditar() {
    	  

    	
            return "<img src='images/clic.gif' />";
    	
         }

         function dialogAlert(txtTitle, txtContent)
         { 
            var thisdialog = new dijit.Dialog({ title: txtTitle, content: txtContent });
            dojo.body().appendChild(thisdialog.domNode);
            thisdialog.startup();
            thisdialog.show();
         }
    
    
         function refreshUserList(e){
          
            if(e.cellIndex==8){
               console.log(e);
               idproceso=edGrid.getItem(e.rowIndex).id;
               jsonStore.fetchItemByIdentity({
                  identity: idproceso,
    		  
                  onItem: function(item){
                     storeUsuarios = new dojo.data.ItemFileReadStore({url:  "<s:property value='listaUsuarios' />?idDependencia1="+jsonStore.getValue(item,"id")});
                     var usuario = dijit.byId("dialogUsuarioReemplazante");
                     usuario.store=storeUsuarios;
                     //alert("${listaUsuarios}?idDependencia1="+jsonStore.getValue(item,"id"));
                     dijit.byId("dialogProceso").setValue(jsonStore.getValue(item,"label"));
                     dijit.byId("dialogFechaInicio").setValue("");
                     dijit.byId("dialogFechaFin").setValue("");
                     dijit.byId("dialogUsuarioReemplazante").setValue("");
                     dijit.byId("dialogUsuarioReemplazante").setDisplayedValue("");
                  }
               });

    	  

               dijit.byId('dialog1').show();
            }
          
         }

         function salvaFila(){
            //  alert('submitted w/args:\n' + dojo.toJson(arguments[0], true));
    	
            if(dijit.byId("dialogFechaInicio").getDisplayedValue()!=""&&!dijit.byId("dialogFechaInicio").state
               &&dijit.byId("dialogFechaFin").getDisplayedValue()!=""&&!dijit.byId("dialogFechaFin").state
               &&dijit.byId("dialogUsuarioReemplazante").getDisplayedValue()!=""&&!dijit.byId("dialogUsuarioReemplazante").state){
               dojo.byId("showErrorReemplazo").innerHTML = "&nbsp;";
               jsonStore.fetchItemByIdentity({
                  identity: idproceso,
    		  
                  onItem: function(item){
    		  

                     jsonStore.setValue(item,"fechainicio",dijit.byId("dialogFechaInicio").getDisplayedValue());
                     jsonStore.setValue(item,"fechafin",dijit.byId("dialogFechaFin").getDisplayedValue());
                     jsonStore.setValue(item,"idreemplazo",dijit.byId("dialogUsuarioReemplazante").getValue());
                     jsonStore.setValue(item,"reemplazo",dijit.byId("dialogUsuarioReemplazante").getDisplayedValue());

                     //jsonStore.getValue(item,"label")

                  }
               });

               document.getElementById("idDependencia1").value=jsonStore._getNewFileContentString();
            }
            else{
               dojo.byId("showErrorReemplazo").innerHTML = "Error en los campos ingresados";
               dijit.byId("dialog1").show();
               //dialogAlert("Error de Validacion", "Los Campos no pueden ser nulos");
            }
         }

         function enviar(){

            document.getElementById("idDependencia1").value=jsonStore._getNewFileContentString();
            //execute remote method
            //create service object(proxy) using SMD (generated by the json result)
        
		
        
            //function called when remote method returns
            var callback = function(str) {     
            	dijit.byId("dlgProgresBar").hide() ;         
            	//alert(" prueba") ; 
               alert(str);
               
               //dialogAlert("Informe de Estado",str);
            };

            var service = new dojo.rpc.JsonService("SMDAction.action");
            //service.processSmd(service.smd);
            console.log(service);
            console.log(service.smd);
            console.log(service.smd.methods);
            var xd = document.getElementById("idDependencia1").value ;
            dijit.byId("dlgProgresBar").show() ; 
            var defered = service.enviaGridReemplazo(xd);

            //attach callback to defered object
            defered.addCallback(callback);
    	          
            //document.forms["frmAsignaReemplazo"].submit();
         }

         function borrar(){
            jsonStore.fetch({
               query: {
                  id:"*"
               },
               onComplete: onCompleteFetch
            });
         }

         var onCompleteFetch = function(items, request) {
            //Define the save callbacks to use
            var onSave = function(){
               // alert("Save done.");
            };
            var onSaveError = function(error) {
               alert("Error occurred: " + error);
            };

            // Process the items and update attribute 'foo'
            for (var i = 0; i < items.length; i++){
               var item = items[i];
    	       
               //var ran_unrounded=Math.random()*3;
               //var ran_number=Math.floor(ran_unrounded);
               console.log(edGrid.store.getValue(item, "selected"));
               if(edGrid.store.getValue(item, "selected")){
                  jsonStore.unsetAttribute(item, "fechafin");
                  jsonStore.unsetAttribute(item, "fechainicio", "");
                  jsonStore.unsetAttribute(item, "idreemplazo", "");
                  jsonStore.unsetAttribute(item, "reemplazo", "");
                  jsonStore.unsetAttribute(item, "selected", "false");
               }
    	      
               //console.log("Fecha Documento: ["+dataStore.getValue(item,'fecharecepcion')+"] Fecha Limite: ["+dataStore.getValue(item,'fechalimite')+"]");
               //console.log(objFechaActual);
            }
    	    
            // If the store has modified items (it should), call save with the handlers above.
            if (jsonStore.isDirty()){
               jsonStore.save({
                  onComplete: onSave,
                  onError: onSaveError
               });
            }
            enviar();
         };
  	
         var chkAll = true;

         noSort = function(inSortInfo) {
            if (inSortInfo == 8) {
               dojo.forEach(edGrid.store._arrayOfAllItems, function(item) {
                  edGrid.store.setValue(item, 'selected', chkAll);
               });

               edGrid.store.save();
               chkAll = !chkAll;

               return false;
            } else {
               return true;
            }
         };

         function validafechas( lugar){
            var fechainicio  = document.getElementById("dialogFechaInicio").value ;
            var fechafin  = document.getElementById("dialogFechaFin").value ;
        	
            if(fechainicio!=""&&fechafin!=""){

               if(compararFechaMayor(fechafin ,fechainicio)){
 					
                  if(lugar==1){
                     alert("la fecha de inicio debe de ser menor a la fecha de fin ");
                     document.getElementById("dialogFechaInicio").value = "" ;
                  }else{
                     alert("la fecha de fin debe de ser mayor a la fecha de inicio ");
                     document.getElementById("dialogFechaFin").value = "" ;
                  }
 					
               }
            }
        	
         }

    	
      
      </script>
      <%
     Date fecha = new Date();
     SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
     String strfecha = sdf.format(fecha);
      %>
   </head>
   <body class="soria">
      <table width="100%">        
         <tr>
            <td colspan="6"></td>
         </tr>
         <tr>
            <td bgcolor="#4481B8" width="30%" align="center" class="tituloRojo" height="25">Asignar Reemplazo para participación</td>
            <td colspan="5"></td>
         </tr>
         <tr>
            <td colspan="6"></td>
         </tr>

      </table>
      <s:form name="frmAsignaReemplazo" action="enviaGridReemplazo" method="POST" target="frmAsignaReemplazo">
         <s:hidden id="idDependencia1" name="idDependencia1" />
         	<button id="eliminar" dojoType="dijit.form.Button" onClick="borrar();">Eliminar</button>
            <div id="procesosParticipacion" >


               <table id='edGrid' jsId='edGrid' dojoType='dojox.grid.DataGrid' store='jsonStore' style='height:250px;' onRowClick="refreshUserList" canSort="noSort" rowsPerPage="50">
                  <thead>
                     <tr>
                     	<th field='selected' width='20px' editable='true' cellType='dojox.grid.cells.Bool' ><input type='checkbox' id='chkHeader' jsid='chkHeader' name='chkHeader' /></th>
                        <th width='20px' cellType='dojox.grid.cells.RowIndex' styles='text-align: center;' ></th>
                        <th field='id' width='20px' editable='false' hidden="true" >id</th>
                        <th field='label' width='180px' editable='false'>Proceso</th>
                        <th field='fechainicio' width='120px' editable='false'>Fecha Inicio</th>
                        <th field='fechafin' width='120px' editable='false'>Fecha Fin</th>
                        <th field='idreemplazo' editable='false' hidden="true">idReemplazo</th>
                        <th field='reemplazo' width='180px' editable='false'>Usuario Reemplazante</th>                        
                        <th width='20px' editable='false' formatter='formaterEditar' >Editar</th>
                     </tr>
                  </thead>
               </table>


            </div>

            <%--img onClick="enviar()" src="/siged/images/enviar.bmp" border="0" alt="Enviar"/>
            <img onClick="borrar()" src="/siged/images/eliminar.bmp" border="0" alt="Eliminar"/--%>

         
      </s:form>

      <div dojoType="dijit.Dialog"
           id="dialog1"
           jsId="dialog1"
           title="Editar Reemplazo"
           execute="salvaFila">
         <table>
            <tr>
               <td colspan="2"><div id="showErrorReemplazo" style="color:red;font-weight:bold;">&nbsp;</div></td>
            </tr>
            <tr>
               <td><label for="dialogProceso">Proceso: </label></td>
               <td><input dojoType="dijit.form.TextBox" type="text" name="dialogProceso" id="dialogProceso" readonly="true"></td>
            </tr>

            <tr>
               <td><label for="dialogFechaInicio">Fecha Inicio: </label></td>
               <td><input dojoType="dijit.form.DateTextBox" value="<%=strfecha%>" onchange="validafechas(1)"  type="text" name="dialogFechaInicio" id="dialogFechaInicio" constraints="{datePattern:'dd/MM/yyyy'}" ></td>
            </tr>
            <tr>
               <td><label for="dialogFechaFin">Fecha Fin: </label></td>
               <td><input dojoType="dijit.form.DateTextBox" type="text" onchange="validafechas(2)" value="<%=strfecha%>" name="dialogFechaFin" id="dialogFechaFin" constraints="{datePattern:'dd/MM/yyyy'}" ></td>
            </tr>
            <tr>
               <td><label for="desc">Usuario Reemplazante: </label></td>
               <td>
                  <input dojoType="dijit.form.FilteringSelect"
                         store="storeUsuarios"
                         idAttr="id"
                         labelAttr="label"
                         searchAttr="label"
                         name="dialogUsuarioReemplazante"
                         id="dialogUsuarioReemplazante"
                         size="80" />
               </td>
            </tr>
            <tr>
               <td colspan="2" align="center">
                  <button dojoType="dijit.form.Button" onClick="enviar();">OK</button></td>
            </tr>
         </table>
      </div>
<%--@ include file="../util/progressBar.jsp" --%> 
   </body>
</html>