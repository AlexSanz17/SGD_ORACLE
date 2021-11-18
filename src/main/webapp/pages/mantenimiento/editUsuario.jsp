<?xml version="1.0" encoding="UTF-8" ?>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
   <head>
      <title>Tramite Documentario</title>
      <s:url id="autoUrl" action="autocompletarProcesoParticipantexParametro" />
      <s:url id="listaUsuarios" action="autocompletarUsuarioxProcesoSinUsuarioParametro" />
      <style type="text/css">
         @import "js/dojo/dijit/themes/soria/soria.css";
         @import "js/dojo/dojox/grid/resources/soriaGrid.css";
         @import "css/grid.css";
         @import "css/estilo.css";

         body{
         	padding: 2px;
         }

         #campos{
         	width: 98%;
         	margin: 5px auto;
         	border: 2px solid #669BCD;
         	background-color: #BFD9F1;
         	color: #1B1B1B;
         	font-size: 0.8em;
         	height: 260px;
         }

         #izquierda{
         	float: left;
         	width: 45%;
         }

         #campos fieldset input,#campos fieldset select{
         	width: 120px;
         }

         #campos fieldset legend{
         	font-weight: bold;
         }

         #campos fieldset label{
         	width: 100px;
         	display: inline-block;
         }

         #roles{
         	height: 90%;
         	float: right;
         	width: 45%;
         }

         #roles select{
         	width: 210px !important;
         	float: left;
         }

         input[type="button"]{
         	width: 50px !important;
         }

         #botones{
         	width: 50px;
         	display: inline-block;
         	float: left;
         	margin-top: 70px;
         }

      </style>
      <script type="text/javascript" src="js/dojo/dojo/dojo.js" djConfig="parseOnLoad:true, isDebug: false"></script>
      <script type="text/javascript">
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
      </script>
      <script type="text/javascript" src="js/jquery.js"></script>
      <script type="text/javascript">
         //var jsonStore = new dojo.data.ItemFileWriteStore({url: "autocompletarProcesoParticipantexParametro.action?idDependencia1=<s:property value='objUsuario.idusuario' />"});
         //var storeRol = new dojo.data.ItemFileReadStore({url: "autocompletarRol.action"});
			var storeUsuarioFinal = new dojo.data.ItemFileReadStore({url: "autocompletarUsuarioFinal.action"});
			var storeUnidad=new dojo.data.ItemFileReadStore({url: "autocompletarUnidad.action"});
			var storeSala=new dojo.data.ItemFileReadStore({url: "autocompletarSala.action"});
			$(document).ready(function(){
				$("#der").click(function(){
					$("#rolIzquierda option:selected").each(function(){
						$("#rolDerecha").append($(this).clone(true));
						$(this).remove();
					});
				});

				$("#izq").click(function(){
					$("#rolDerecha option:selected").each(function(){
						$("#rolIzquierda").append($(this).clone(true));
						$(this).remove();
					});
				});

				$("form").submit(function(){
					$("#rolDerecha option").attr("selected","selected");
					//alert($("#rolDerecha option:selected").length);
					//return false;
				});
				new dijit.form.Button({
					type	: "submit"
				},"guardar");
			});
      </script>
      <%--script type="text/javascript">
         // var jsonStore;

         var storeUsuarios;
         var idproceso;

         function formaterEditar() {
    	      return "<img src='images/clic.gif' />";
   		}


         function editReemplazos(){

            if(jsonStore._arrayOfAllItems.length>0){
               console.log(jsonStore._getNewFileContentString());
               dijit.byId('dialog2').show();
            }
            else{
               alert("El usuario especificado no participa de proceso alguno");
            }
         }

         function refreshUserList(e){

     		if(e.cellIndex==8){
     		console.log(e)
         	  idproceso=edGrid.getItem(e.rowIndex).id;
         	  jsonStore.fetchItemByIdentity({
         		  identity: idproceso,

         		  onItem: function(item){
         		  storeUsuarios = new dojo.data.ItemFileReadStore({url:  "<s:property value='listaUsuarios}?idDependencia1="+jsonStore.getValue(item,"id")+"&idDependencia2="+<s:property value="objUsuario.idusuario" />});
              	  var usuario = dijit.byId("dialogUsuarioReemplazante")
               	  usuario.store=storeUsuarios;

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

               // document.getElementById("idDependencia1").value=jsonStore._getNewFileContentString();
            }
            else{
               alert("Error en los campos ingresados");
               //dialogAlert("Error de Validacion", "Los Campos no pueden ser nulos");
            }
         }
         function enviar(){

            //document.getElementById("idDependencia1").value=jsonStore._getNewFileContentString();
            //execute remote method
            //create service object(proxy) using SMD (generated by the json result)



            //function called when remote method returns
            var callback = function(str) {
               alert(str);
               dijit.byId('dialog2').hide();
               //dialogAlert("Informe de Estado",str);
            };

            var service = new dojo.rpc.JsonService("SMDAction.action");
            //service.processSmd(service.smd);
            console.log(service);
            console.log(service.smd);
            console.log(service.smd.methods);
            var defered = service.enviaGridReemplazoParametro(jsonStore._getNewFileContentString(),<s:property value="objUsuario.idusuario" />);

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
      	   }
      	   var onSaveError = function(error) {
      	      alert("Error occurred: " + error);
      	   }

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
      	}

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
      	}

      </script--%>
      <%--script type="dojo/method" event="onSubmit">
            if (this.validate()) {
               console.debug('Intentando enviar los sgtes datos:\n', dojo.toJson(this.getValues(), true));

               return true;
            } else {
               dojo.byId('showError').innerHTML='Data invalida... Por favor corrija';

               return false;
            }
         </script--%>
	</head>

	<body class="soria">
		<s:form action="doSaveUsuario.action" method="post">
			<%--s:submit src="images/guardar.bmp" type="image" value="Guardar Usuario" /--%>
			<button id="guardar">Guardar Usuario</button>
			<div id="campos">
				<div id="izquierda">
					<fieldset>
						<legend>Datos de Usuario</legend>
						<label for="objUsuario.usuario">Usuario</label>
						<input type="text"
							dojoType="dijit.form.ValidationTextBox"
		                    id="objUsuario.usuario"
		                    name="objUsuario.usuario"
		                    value="<s:property value='objUsuario.usuario' />"
		                    invalidMessage="Ingrese usuario"
		                    required="true"
		                    style="width:100px;" />
		                <br />
						<label for="objUsuario.clave">Clave</label>
						<input type="password"
			                dojoType="dijit.form.ValidationTextBox"
			                id="objUsuario.claveSiged"
			                name="objUsuario.claveSiged"
			                value="<s:property value='objUsuario.claveSiged' />"
			                invalidMessage="Ingrese clave"
			                required="true"
			                style="width:100px;" />
			            <br />
			            <label for="objUsuario.nombres">Nombres</label>
			            <input type="text"
			                dojoType="dijit.form.ValidationTextBox"
			                id="objUsuario.nombres"
			                name="objUsuario.nombres"
			                value="<s:property value='objUsuario.nombres' />"
			                invalidMessage="Ingrese nombres"
			                required="true"
			                style="width:200px;" />
			            <br />
			            <label for="objUsuario.apellidos">Apellidos</label>
			            <input type="text"
			                dojoType="dijit.form.ValidationTextBox"
			                id="objUsuario.apellidos"
			                name="objUsuario.apellidos"
			                value="<s:property value='objUsuario.apellidos' />"
			                style="width:200px;" />
			            <br />
			            <label for="objUsuario.correo">Correo</label>
			            <input type="text"
			                dojoType="dijit.form.ValidationTextBox"
			                id="objUsuario.correo"
			                name="objUsuario.correo"
			                value="<s:property value='objUsuario.correo' />"
			                style="width:200px;" />
			            <br />
					</fieldset>
					<fieldset>
						<legend>Datos de Unidad</legend>
						<label for="objUsuario.unidad.idunidad">&Aacute;rea</label>
						<select dojoType="dijit.form.FilteringSelect"
			                id="objUsuario.unidad.idunidad"
			                name="objUsuario.unidad.idunidad"
			                value="<s:property value='objUsuario.unidad.idunidad' />"
			                idAttr="id"
			                invalidMessage="Seleccione un &aacute;rea"
			                labelAttr="label"
			                required="true"
			                searchAttr="label"
			                store="storeUnidad"></select>
			            <br />
			            <label for="objUsuario.jefe.idusuario">Jefe</label>
			            <select dojoType="dijit.form.FilteringSelect"
			                id="objUsuario.jefe.idusuario"
			                name="objUsuario.jefe.idusuario"
			                value="<s:property value='objUsuario.jefe.idusuario' />"
			                idAttr="id"
			                labelAttr="label"
			                required="false"
			                searchAttr="label"
			                store="storeUsuarioFinal"></select>
			            <br />
			            <label for="bUsuarioFinal">Usuario Final</label>
			            <s:checkbox name="bUsuarioFinal" value="bUsuarioFinal" />
			            <br />
			            <label for="bEnvioCorreo">Recibir Correo</label>
			            <s:checkbox name="bEnvioCorreo" value="bEnvioCorreo" />
			            <br />
			            <label for="idSala">Sala</label>
			            <select dojoType="dijit.form.FilteringSelect"
			                id="idSala"
			                name="idSala"
			                value="<s:property value='idSala' />"
			                idAttr="id"
			                labelAttr="label"
			                required="false"
			                searchAttr="label"
			                store="storeSala"></select>
					</fieldset>
				</div>
				<fieldset id="roles">
					<legend>Roles</legend>
                                        <s:select id="rolIzquierda" cssClass="izquierda" list="rolesTotales" listKey="idrol" listValue="descripcion" multiple="true" size="15" ></s:select>
					<div id="botones">
						<input type="button" id="der" value="-&gt;" />
						<input type="button" id="izq" value="&lt;-" />
					</div>
					<s:select id="rolDerecha"  cssClass="derecha" name="rolesUsuario" list="roles" listKey="idrol" listValue="descripcion" multiple="true" size="15" ></s:select>
					<%--s:optiontransferselect id="rolSelect"
											leftTitle="Disponibles" list="rolesTotales" listKey="idrol" listValue="descripcion" headerKey="0"
											rightTitle="Del Usuario" doubleList="roles" doubleListKey="idrol" doubleListValue="descripcion" doubleName="rolesUsuario" doubleHeaderKey="0"
											allowUpDownOnLeft="false" allowUpDownOnRight="false" /--%>
				</fieldset>
				<s:hidden name="objUsuario.idusuario" />
				<s:hidden name="usuarioAnterior" />
			</div>
         <%--table width="100%">
            <tr>
               <td></td>
               <td height="16" colspan="4" align="left" class="plomo"></td>
            </tr>
            <tr align="center">
               <td width="1%" align="center">&nbsp;</td>
               <td width="99%" colspan="2" align="left">
                  <s:submit src="images/guardar.bmp" type="image" value="Guardar Usuario" />
                  <!--
				  <img onClick="editReemplazos()" src="/siged/images/enviar.bmp" border="0" alt="Enviar"/>
				  -->
               </td>
            </tr>
            <tr>
               <td></td>
               <td height="16" colspan="4" align="left" class="plomo"></td>
            </tr>
            <tr>
               <td height="14" colspan="3">
                  <table width="95%" border="1" align="center" bordercolor="#669BCD" bgcolor="#BFD9F1">
                     <tr>
                        <td height="75">
                           <table width="98%" height="97" align="center" >
                              <tr>
                                 <td></td>
                                 <td colspan="5" height="16" align="left" class="label">
                                    <div id="showError" style="color:red;" />
                                 </td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td colspan="5" height="16" align="left" class="label">
                                    <s:hidden name="objUsuario.idusuario" />
                                 </td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Usuario</td>
                                 <td colspan="3" align="left" class="label">
                                    <input type="text"
                                           dojoType="dijit.form.ValidationTextBox"
                                           id="objUsuario.usuario"
                                           name="objUsuario.usuario"
                                           value="<s:property value='objUsuario.usuario' />"
                                           invalidMessage="Ingrese usuario"
                                           required="true"
                                           style="width:100px;" />
                                 </td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td ></td>
                                 <td height="16" align="left">Clave</td>
                                 <td colspan="3" class="label">
                                    <input type="password"
                                           dojoType="dijit.form.ValidationTextBox"
                                           id="objUsuario.clave"
                                           name="objUsuario.clave"
                                           value="<s:property value='objUsuario.clave' />"
                                           invalidMessage="Ingrese clave"
                                           required="true"
                                           style="width:100px;" />
                                 </td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Nombres</td>
                                 <td colspan="3" align="left" class="label">
                                    <input type="text"
                                           dojoType="dijit.form.ValidationTextBox"
                                           id="objUsuario.nombres"
                                           name="objUsuario.nombres"
                                           value="<s:property value='objUsuario.nombres' />"
                                           invalidMessage="Ingrese nombres"
                                           required="true"
                                           style="width:200px;" />
                                 </td>
                                 <td align="left"></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Apellidos</td>
                                 <td colspan="3" class="label">
                                    <input type="text"
                                           dojoType="dijit.form.ValidationTextBox"
                                           id="objUsuario.apellidos"
                                           name="objUsuario.apellidos"
                                           value="<s:property value='objUsuario.apellidos' />"
                                           style="width:200px;" />
                                 </td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Usuario Final</td>
                                 <td colspan="3" class="label">
                                    <s:checkbox name="bUsuarioFinal" value="bUsuarioFinal" />
                                 </td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Recibir Correo</td>
                                 <td colspan="3" class="label">
                                    <s:checkbox name="bEnvioCorreo" value="bEnvioCorreo" />
                                 </td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Correo</td>
                                 <td colspan="3" class="label">
                                    <input type="text"
                                           dojoType="dijit.form.ValidationTextBox"
                                           id="objUsuario.correo"
                                           name="objUsuario.correo"
                                           value="<s:property value='objUsuario.correo' />"
                                           style="width:200px;" />
                                 </td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">&Aacute;rea</td>
                                 <td colspan="3" class="label">
                                    <select dojoType="dijit.form.FilteringSelect"
                                            id="objUsuario.unidad.idunidad"
                                            name="objUsuario.unidad.idunidad"
                                            value="<s:property value='objUsuario.unidad.idunidad' />"
                                            idAttr="id"
                                            invalidMessage="Seleccione un &aacute;rea"
                                            labelAttr="label"
                                            required="true"
                                            searchAttr="label"
                                            store="storeUnidad" />
                                 </td>
                                 <td></td>
                              </tr>
                              <%--tr>
                                 <td></td>
                                 <td height="16" align="left">Rol</td>
                                 <td colspan="3" class="label">
                                    <select dojoType="dijit.form.FilteringSelect"
                                            id="objUsuario.rol.idrol"
                                            name="objUsuario.rol.idrol"
                                            value="<s:property value='objUsuario.rol.idrol' />"
                                            idAttr="id"
                                            invalidMessage="Seleccione un rol"
                                            labelAttr="label"
                                            required="true"
                                            searchAttr="label"
                                            store="storeRol" />
                                 </td>
                                 <td></td>
                              </tr--%><%--
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Jefe</td>
                                 <td colspan="3" class="label">
                                    <select dojoType="dijit.form.FilteringSelect"
                                            id="objUsuario.idjefe.idusuario"
                                            name="objUsuario.idjefe.idusuario"
                                            value="<s:property value='objUsuario.idjefe.idusuario' />"
                                            idAttr="id"
                                            labelAttr="label"
                                            required="false"
                                            searchAttr="label"
                                            store="storeUsuarioFinal" />
                                 </td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td class="titulo">&nbsp;</td>
                                 <td colspan="4"></td>
                              </tr>
                           </table>
                        </td>
                     </tr>
                  </table>
               </td>
            </tr>
            <tr>
               <td height="14"  colspan="3"></td>
            </tr>
         </table>
         <s:hidden name="usuarioAnterior" />
      </div>

      <!-- Inicio Modificaciones Victor Soria -->

      <div dojoType="dijit.Dialog" id="dialog2" title="Editar Reemplazos"
           execute="salvaFila">
         <div style="width: 1000px; height: 400px; overflow: auto;">

            <table id='edGrid' jsId='edGrid' dojoType='dojox.grid.DataGrid' store='jsonStore' style='height:250px;' onRowClick="refreshUserList" canSort="noSort" >
               <thead>
                  <tr>
                     <th width='15px' cellType='dojox.grid.cells.RowIndex' styles='text-align: center;'></th>
                     <th field='id' width='15px' editable='false' hidden="true">id</th>
                     <th field='label' width='170px' editable='false'>Proceso</th>
                     <th field='fechainicio' width='90px' editable='false'>Fecha Inicio</th>
                     <th field='fechafin' width='90px' editable='false'>Fecha Fin</th>
                     <th field='idreemplazo' editable='false' hidden="true">idReemplazo</th>
                     <th field='reemplazo' width='150px' editable='false'>Usuario Reemplazante</th>
                     <th field='selected' width='20px' editable='true' cellType='dojox.grid.cells.Bool' ><input type='checkbox' id='chkHeader' jsid='chkHeader' name='chkHeader' /></th>
		             <th width='20px' editable='false' formatter='formaterEditar' >Editar</th>


                  </tr>
               </thead>

            </table>
            <img onClick="enviar()" src="/siged/images/enviar.bmp" border="0" alt="Enviar"/>
            <img onClick="borrar()" src="/siged/images/eliminar.bmp" border="0" alt="Eliminar"/>
         </div>
      </div>

      <div dojoType="dijit.Dialog" id="dialog1" title="Editar Reemplazo"
           execute="salvaFila">
         <table>
            <tr>
               <td><label for="dialogProceso">Proceso: </label></td>
               <td><input dojoType="dijit.form.TextBox" type="text" name="dialogProceso" id="dialogProceso" readonly="true"></td>
            </tr>

            <tr>
               <td><label for="dialogFechaInicio">Fecha Inicio: </label></td>
               <td><input dojoType="dijit.form.DateTextBox" type="text" name="dialogFechaInicio" id="dialogFechaInicio" constraints="{datePattern:'dd/MM/yyyy'}" ></td>
            </tr>
            <tr>
               <td><label for="dialogFechaFin">Fecha Fin: </label></td>
               <td><input dojoType="dijit.form.DateTextBox" type="text" name="dialogFechaFin" id="dialogFechaFin" constraints="{datePattern:'dd/MM/yyyy'}" ></td>
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
                         size="80"

                         />
               </td>
            </tr>
            <tr>
               <td colspan="2" align="center">
               <button dojoType="dijit.form.Button" type="submit">OK</button></td>
            </tr>
         </table--%>
		</s:form>
	</body>
</html>