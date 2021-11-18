<?xml version="1.0" encoding="UTF-8" ?>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es">
   <head>
      <title>Edici&oacute;n del Proceso</title>
      <meta http-equiv="Pragma" content="no-cache" />
      <link rel="stylesheet" type="text/css" href="css/estilo.css" />
      <link rel="stylesheet" type="text/css" href="css/transferSelect.css" />
      
	<s:if test="#session._USUARIO.rol!=null">
	
	<script type="text/javascript" src="js/dojo/dojo/dojo.js" djConfig="parseOnLoad:true, isDebug: false"></script>
	<script type="text/javascript" src="js/jquery.js"></script>
	
	<script type="text/javascript">
	
	dojo.addOnLoad(function() {
		   var gridEtapaProceso = dijit.byId("gridEtapaProceso");
		   console.debug("Idproceso [%d]", idProceso);            	 
		   gridEtapaProceso.setStructure(lytGridCampo);
	
	       	var url="autocompletarGridEtapaProceso.action";
	    	$.getJSON(url,
	    		{
	    		    iIdProceso: $("input[name='objProceso.idproceso']").val()
	             },
	    			function(data){
	      		   
	      		   gridEtapaProceso.setStore(new dojo.data.ItemFileWriteStore({
	      	         data : data
	      	      }));
	    			}
	    	);
	
	 	   var gridEstadoProceso = dijit.byId("gridEstadoProceso");
		   console.debug("Idproceso [%d]", idProceso);            	 
		   gridEstadoProceso.setStructure(lytGridEstado);
	
	       	var url="autocompletarGridEstadoProceso.action";
	    	$.getJSON(url,
	    		{
	    		    iIdProceso: $("input[name='objProceso.idproceso']").val()
	             },
	    			function(data){
	      		   
	            	 gridEstadoProceso.setStore(new dojo.data.ItemFileWriteStore({
	      	         data : data
	      	      }));
	    			}
	    	);                	
		});
	</script>
	</s:if>

	<style type="text/css">
		@import "js/dojo/dijit/themes/soria/soria.css";	
		@import "js/dojo/dojox/grid/resources/soriaGrid.css";	
		@import "css/estilo.css";	
		@import "css/sigedIconos.css";
	</style>
	
	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript" src="js/dojo/dijit/dijit.js"></script>
	<script type="text/javascript">
	      dojo.require("dijit.form.Button");
	      dojo.require("dijit.form.FilteringSelect");
	      dojo.require("dijit.form.Form");
	      dojo.require("dijit.form.TextBox");
	      dojo.require("dojo.data.ItemFileWriteStore");
	      dojo.require("dojo.rpc.JsonService");
	      dojo.require("dojox.grid.DataGrid");
	         var idProceso = "<s:property value='objProceso.idproceso' />";
	         var tipoAcceso = "<s:property value='objProceso.idtipoacceso.idtipoacceso' />";
	         var tipoNumeracion = ("<s:property value='objProceso.tiponumeracion' />" == "") ? "N" : "<s:property value='objProceso.tiponumeracion' />";
	         var radioObj = document.getElementsByName("objProceso.tiponumeracion");
	         var ambiente = ("<s:property value='objProceso.produccion' />" == "") ? "N" : "<s:property value='objProceso.produccion' />";
	         
	         var service = new dojo.rpc.JsonService("SMDAction.action");
	         
	         var arrEtapaToDelete = new Array();
	         var iCount = 0;
	         var indice = 0;
	         var lytGridCampo = [
				{
				   field  : "id",
				   name   : "ID",
				   hidden : true
				},
				{
					field    : "orden",
					name     : "Orden",
					editable : true,
					width    : "50px"
				},			
				{
				   field    : "descripcion",
				   name     : "Descripcion",
				   editable : true,
				   width    : "400px"
				}
				];
	
	         var arrEstadoToDelete = new Array();
	         var iCountEstado = 0;
	         var indiceEstado = 0;         
	         var lytGridEstado = [
				{
				   field  : "id",
				   name   : "ID",
				   hidden : true
				},
				{
					field    : "descripcion",
					name     : "Descripcion",
					editable : true,
					width    : "150px"
				}
				];
	        
	         
	</script>
	<s:if test="#session._USUARIO.rol==null">
		<script type="text/javascript">
			if (!dojo.isIE) {
				verTipo();
				refreshTipoAcceso(tipoAcceso);	
			}
		</script>
	</s:if>
	<script type="text/javascript" src="js/siged/editProceso.js"></script>
		
	<script type="text/javascript">
	      handler = dojo.connect(dijit.byId("contentPaneDetail"), "onLoad", viewProcesoOnLoad);

	         function validarForm(){
	
	          var grid = dijit.byId("gridEtapaProceso");	
	        	 
	          dojo.byId("objProceso.etapas").value = grid.store._getNewFileContentString();
			  if(iCount>0){	
	          	dojo.byId("objProceso.arrEtapaToDelete").value = arrEtapaToDelete;
			  }
	
	          var gridEstado = dijit.byId("gridEstadoProceso");	
	     	 
	          dojo.byId("objProceso.estados").value = gridEstado.store._getNewFileContentString();
			  if(iCountEstado>0){	
	          	dojo.byId("objProceso.arrEstadoToDelete").value = arrEstadoToDelete;
			  }
			  		  
			 var gridEtapaOk= true;
			  dojo.forEach(grid.store._arrayOfAllItems, function(item) {
				if (isNaN(grid.store.getValue(item, "orden"))){				
					gridEtapaOk = false; 
				}
			  });		  
			  if(!gridEtapaOk){
				  alert("Ingrese un valor válido en el Orden "); 
				  return false;
			  }
	         <s:if test="#session._USUARIO.rol!=null">
	         
	         var tipoProceso=document.getElementById("doSaveProceso_objProceso_tipo");
	         if(tipoProceso[0].selected == true){
	            alert("Debe seleccionar un Tipo de Proceso.");
	            tipoProceso.focus();
	            return false;
	         }
	
	         var tiempoAtencion=document.getElementById("doSaveProceso_objProceso_tiempo");
	         if(tiempoAtencion.value ==""){
	            alert("Debe ingresar un Tiempo de Atencion.");
	            tiempoAtencion.focus();
	            return false;
	         }
	         
	         var porcentajealerta1=document.getElementById("doSaveProceso_objProceso_porcentaje1");
	         var porcentajealerta2=document.getElementById("doSaveProceso_objProceso_porcentaje2");
	         
	         if(porcentajealerta1.value =="" ){
	            alert("Debe ingresar un Porcentaje para la Alerta Amarilla.");            
	            porcentajealerta1.focus();
	            return false;
	         } 
	         if(porcentajealerta2.value ==""){
	             alert("Debe ingresar un Porcentaje para la Alerta Roja.");
	             porcentajealerta2.focus();
	             return false;
	          }                
	         if(porcentajealerta1.value!=null && porcentajealerta2.value!=null){
	 			if(!validaFloat(porcentajealerta1.value) || (porcentajealerta1.value>1 || porcentajealerta1.value<=0)){
					alert("Debe ingresar un valor valido para el Porcentaje de la Alerta Amarilla.");
		            porcentajealerta1.focus();
		            return false;
				}
				if(!validaFloat(porcentajealerta2.value) || (porcentajealerta2.value>1 || porcentajealerta2.value<=0)){
					alert("Debe ingresar un valor valido para el Porcentaje de la Alerta Roja.");
		             porcentajealerta2.focus();
		             return false;				
				}
				if(porcentajealerta1.value==porcentajealerta2.value || porcentajealerta1.value>porcentajealerta2.value){
					alert("El Porcentaje de la Alerta Amarilla debe ser menor que la Alerta Roja");
		            porcentajealerta1.focus();
		            return false;
				}				
	         }
	         var idNivelAcceso=document.getElementById("doSaveProceso_objProceso_nivel_acceso");
	         if(idNivelAcceso[0].selected == true){
	            alert("Debe seleccionar un Nivel de Acceso.");
	            idNivelAcceso.focus();
	            return false;
	         }
	         
	               var idNombre=document.getElementById("doSaveProceso_objProceso_nombre");
	               if(idNombre.value==""){
	                  alert("Debe ingresar el nombre del Proceso.");
	                  idNombre.focus();
	                  return false;
	               }
	
	               var idResponsable=document.getElementById("objProceso_responsable_idusuario");
	               if(idResponsable.value=="0"){
	                  alert("Debe ingresar un responsable al Proceso.");
	                  idResponsable.focus();
	                  return false;
	               }
	               return true;
	         </s:if>
	         <s:else>
	               $("#participantes .derecha option").attr("selected","selected");
	               $("#participantesConfidenciales .derecha option").attr("selected","selected");
	               $("#notificados .derecha option").attr("selected","selected");
	               $.post("doSaveProceso.action", $("#formulario").serialize(),function(){
	                  showGridInbox(5);
	               });
	               return false;
	         </s:else>
	            }
 	     	        	
	      </script>

</head>

   <body class="soria" onload="refreshTipoAcceso(<s:property value='objProceso.idtipoacceso.idtipoacceso' />);verTipo();">
      <s:form id="formulario" name="frmProceso" action="doSaveProceso" method="post">
         <table width="100%">
            <tr>
               <td></td>
               <td height="16" colspan="4" align="left" class="plomo"></td>
            </tr>
            <tr align="center">
               <td width="1%" align="center">&nbsp;</td>
               <td width="99%" align="left">
                  <s:submit src="images/guardar.bmp" type="image" onclick="return validarForm()" value="Guardar Proceso" />
               </td>
               <td>
                  <s:if test="iIdProceso!=null">
                     <a href="#" onclick="eliminarProceso(<%=request.getAttribute("iIdProceso")%>)">eliminar</a>
                  </s:if>
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
                           <table width="98%" align="center" >
                              <tr>
                                 <td></td>
                                 <td colspan="5" height="16" align="left" class="label">
                                    <s:textfield name="objProceso.idproceso" cssStyle="display:none" />
                                 </td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Nombre</td>
                                 <td colspan="3" align="left" class="label"><s:textfield name="objProceso.nombre" id="doSaveProceso_objProceso_nombre" cssClass="cajaMontoTotal" size="125"   maxlength="250" /></td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Tipo</td>
                                 <td colspan="3" class="label">
                                    <s:select cssClass="cajaMontoTotal"  id="doSaveProceso_objProceso_tipo"  name="objProceso.tipoproceso.idtipoproceso" headerKey="0" headerValue="-- Seleccione un Tipo --" list="mapTipoProceso" value="objProceso.tipoproceso.idtipoproceso" />
                                 </td>
                                 <td></td>
                              </tr>
                              <tr style="display: none;">
                                 <td></td>
                                 <td height="16" align="left">Nombre del proceso en Intalio</td>
                                 <td colspan="3" align="left" class="label"><s:textfield name="objProceso.nombreIntalio" id="procesoIntalio" cssClass="cajaMontoTotal" size="25"   maxlength="50" /></td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Nivel de Acceso</td>
                                 <td colspan="3" class="label">
                                    <s:select cssClass="cajaMontoTotal" name="objProceso.idtipoacceso.idtipoacceso"  id="doSaveProceso_objProceso_nivel_acceso"  headerKey="0" headerValue="-- Seleccione un Nivel de Acceso --" list="mapTipoAcceso" value="objProceso.idtipoacceso.idtipoacceso" onchange="refreshTipoAcceso(this.value)" />
                                 </td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Grupo de Procesos</td>
                                 <td colspan="3" class="label">
                                    <s:select cssClass="cajaMontoTotal" name="objProceso.idgrupoproceso.idgrupoproceso" headerKey="0" headerValue="-- Seleccione un Grupo de Proceso --" list="mapGrupoProceso" value="objProceso.idgrupoproceso.idgrupoproceso" />
                                 </td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Tipo de Numeraci&oacute;n</td>
                                 <td colspan="3" class="label">
                                    <input type="radio" id="tipoNumeracionN" name="objProceso.tiponumeracion" value="N" />Ninguna
                                    <input type="radio" id="tipoNumeracionG" name="objProceso.tiponumeracion" value="G" />Por Grupo
                                    <input type="radio" id="tipoNumeracionP" name="objProceso.tiponumeracion" value="P" />Por Proceso
                                 </td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Permite adjuntar con Metadata</td>
                                 <td colspan="3" class="label">
                                    <s:checkbox name="bPermiteMetaData" value="bPermiteMetaData" />
                                 </td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Permite ingreso de Sumilla</td>
                                 <td colspan="3" class="label">
                                    <s:checkbox name="bPermiteSumilla" value="bPermiteSumilla" />
                                 </td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Ambiente</td>
                                 <td colspan="3" class="label">
                                    <input type="radio" name="objProceso.produccion" value="N" />Piloto
                                    <input type="radio" name="objProceso.produccion" value="S" checked />Producci&oacute;n
                                 </td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Responsable</td>
                                 <td colspan="3" class="label">
                                    <s:if test="#session.opcionmenu.equals('MantMnuPro')">
                                       <s:select cssClass="cajaMontoTotal" id="objProceso_responsable_idusuario" name="objProceso.responsable.idusuario" headerKey="0" headerValue="-- Seleccione un Responsable --" list="mapResponsable" value="objProceso.responsable.idusuario" />
                                    </s:if>
                                    <s:else>
                                       <s:textfield name="objProceso.responsable.idusuario" cssStyle="display:none" />
                                       <s:textfield name="objProceso.responsable.nombres+' '+objProceso.responsable.apellidos" disabled="true" cssClass="cajaMontoTotal" />
                                    </s:else>
                                 </td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Suplente</td>
                                 <td colspan="3" class="label">
                                    <s:select cssClass="cajaMontoTotal" name="objProceso.idasistente.idusuario" headerKey="0" headerValue="-- Seleccione un Suplente --" list="mapResponsable" value="objProceso.idasistente.idusuario" />
                                 </td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Tiempo Atencion</td>
                                 <td colspan="3" class="label"><s:textfield name="objProceso.tiempoatencion" id="doSaveProceso_objProceso_tiempo"  cssClass="cajaMontoTotal" size="15" /> días</td>
                                 <td></td>
                              </tr>
							  <tr>
                                 <td></td>
                                 <td height="16" align="left">Porcentaje Alerta Amarilla</td>
                                 <td colspan="3" class="label"><s:textfield name="objProceso.porcentajealertaA" id="doSaveProceso_objProceso_porcentaje1"  cssClass="cajaMontoTotal" size="15" /></td>
                                 <td></td>
                              </tr>
							  <tr>
                                 <td></td>
                                 <td height="16" align="left">Porcentaje Alerta Roja</td>
                                 <td colspan="3" class="label"><s:textfield name="objProceso.porcentajealertaR" id="doSaveProceso_objProceso_porcentaje2"  cssClass="cajaMontoTotal" size="15" /></td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Descripción</td>
                                 <td colspan="3" align="left" class="label"><s:textarea cssClass="cajaMontoTotal" name="objProceso.descripcion" rows="10" cols="70"/></td>
                                 <td align="left"></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Participantes</td>
                                 <td colspan="3" align="left" class="label">
                                    <div id="participantes">
                                       <div class="divIzquierda">
                                          <span>Usuarios Disponibles</span>
                                          <s:select cssClass="izquierda" list="mapUsuarioLeft" multiple="true" size="15"></s:select>
                                       </div>
                                       <div class="botones">
                                          <input type="button" class="der" value="-&gt;" />
                                          <input type="button" class="izq" value="&lt;-" />
                                       </div>
                                       <div class="divDerecha">
                                          <span>Participantes</span>
                                          <s:select cssClass="derecha" name="lstUsuarioRight" list="mapUsuarioRight" multiple="true" size="15"></s:select>
                                       </div>
                                    </div>
                                    <%--s:optiontransferselect
                                       id="Participantes"
                                       allowUpDownOnLeft="false"
                                       allowUpDownOnRight="false"
                                       doubleHeaderKey="0"
                                       doubleList="mapUsuarioRight"
                                       doubleName="lstUsuarioRight"
                                       leftTitle="Usuarios Disponibles"
                                       headerKey="0"
                                       list="mapUsuarioLeft"
                                       rightTitle="Participantes" /--%>
                                 </td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Participantes por Lista</td>
                                 <td colspan="3" align="left" class="label">
                                    <div id="listas">
                                       <div class="divIzquierda">
                                          <span>Listas Disponibles</span>
                                          <s:select cssClass="izquierda" list="mapListaLeft" multiple="true" size="15"></s:select>
                                       </div>
                                       <div class="botones">
                                          <input type="button" class="der" value="-&gt;" />
                                          <input type="button" class="izq" value="&lt;-" />
                                       </div>
                                       <div class="divDerecha">
                                          <span>Listas Participante</span>
                                          <s:select cssClass="derecha" name="lstListaRight" list="mapListaRight" multiple="true" size="15"></s:select>
                                       </div>
                                    </div>
                                 </td>
                              </tr>
                              <tr id="pconfidencial" style="display:none">
                                 <td></td>
                                 <td height="16" align="left">Participantes Confidenciales</td>
                                 <td colspan="3" align="left" class="label">
                                    <div id="participantesConfidenciales">
                                       <div class="divIzquierda">
                                          <span>Usuarios Disponibles</span>
                                          <s:select cssClass="izquierda" list="mapUsuarioConfidencialLeft" multiple="true" size="15"></s:select>
                                       </div>
                                       <div class="botones">
                                          <input type="button" class="der" value="-&gt;" />
                                          <input type="button" class="izq" value="&lt;-" />
                                       </div>
                                       <div class="divDerecha">
                                          <span>Participantes Confidenciales</span>
                                          <s:select cssClass="derecha" name="lstUsuarioConfidencialRight" list="mapUsuarioConfidencialRight" multiple="true" size="15"></s:select>
                                       </div>
                                    </div>
                                    <%--s:optiontransferselect
                                       id="ParticipantesConfindeciales"
                                       allowUpDownOnLeft="false"
                                       allowUpDownOnRight="false"
                                       doubleHeaderKey="0"
                                       doubleList="mapUsuarioConfidencialRight"
                                       doubleName="lstUsuarioConfidencialRight"
                                       leftTitle="Usuarios Disponibles"
                                       headerKey="0"
                                       list="mapUsuarioConfidencialLeft"
                                       rightTitle="Participantes Confidenciales" /--%>
                                 </td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left" colspan="5">Configuraci&oacute;n de Correo Electr&oacute;nico</td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Aprobaci&oacute;n / Creaci&oacute;n de Expedientes</td>
                                 <td colspan="4" class="label">
                                    <table cellpadding="6" frame="box" rules="groups">
                                       <thead>
                                          <tr>
                                             <th>Enviar a</th>
                                             <th>Correo Electr&oacute;nico</th>
                                          </tr>
                                       </thead>
                                       <tbody>
                                          <tr>
                                             <td style="width: 10%">Responsable del Proceso</td>
                                             <td><s:checkbox name="arrConfigNotifMail" fieldValue="%{arrConfigNotifMail[0]}" value="arrConfigNotifMailChecked[0]" /></td>
                                          </tr>
                                          <tr>
                                             <td>Notificados</td>
                                             <td colspan="2">
                                                <div id="notificados">
                                                   <div class="divIzquierda">
                                                      <span>Usuarios Disponibles</span>
                                                      <s:select cssClass="izquierda" list="mapNotificadoLeft" multiple="true" size="15"></s:select>
                                                   </div>
                                                   <div class="botones">
                                                      <input type="button" class="der" value="-&gt;" />
                                                      <input type="button" class="izq" value="&lt;-" />
                                                   </div>
                                                   <div class="divDerecha">
                                                      <span>Notificados</span>
                                                      <s:select cssClass="derecha" name="lstNotificadoRight" list="mapNotificadoRight" multiple="true" size="15"></s:select>
                                                   </div>
                                                </div>
                                             </td>
                                          </tr>
                                       </tbody>
                                    </table>
                                 </td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Expedientes con plazo pr&oacute;ximo a vencer (Alerta Amarilla)</td>
                                 <td colspan="4" class="label">
                                    <table cellpadding="6" frame="box" rules="groups">
                                       <thead>
                                          <tr>
                                             <th>Enviar a</th>
                                             <th>Correo Electr&oacute;nico</th>
                                          </tr>
                                       </thead>
                                       <tbody>
                                          <tr>
                                             <td>Remitente</td>
                                             <td><s:checkbox name="arrConfigNotifMail" fieldValue="%{arrConfigNotifMail[1]}" value="arrConfigNotifMailChecked[1]" /></td>
                                          </tr>
                                          <tr>
                                             <td>Destinatario</td>
                                             <td><s:checkbox name="arrConfigNotifMail" fieldValue="%{arrConfigNotifMail[2]}" value="arrConfigNotifMailChecked[2]" /></td>
                                          </tr>
                                       </tbody>
                                    </table>
                                 </td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Expedientes con plazo vencido (Alerta Roja)</td>
                                 <td colspan="4" class="label">
                                    <table cellpadding="6" frame="box" rules="groups">
                                       <thead>
                                          <tr>
                                             <th>Enviar a</th>
                                             <th>Correo Electr&oacute;nico</th>
                                          </tr>
                                       </thead>
                                       <tbody>
                                          <tr>
                                             <td>Remitente</td>
                                             <td><s:checkbox name="arrConfigNotifMail" fieldValue="%{arrConfigNotifMail[3]}" value="arrConfigNotifMailChecked[3]" /></td>
                                          </tr>
                                          <tr>
                                             <td>Destinatario</td>
                                             <td><s:checkbox name="arrConfigNotifMail" fieldValue="%{arrConfigNotifMail[4]}" value="arrConfigNotifMailChecked[4]" /></td>
                                          </tr>
                                       </tbody>
                                    </table>
                                 </td>
                              </tr>  
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Lista de Estados</td>
                                 <td colspan="4" class="label">
                                    <table width="250px" frame="box">
                                          <tr>
                                             <td><button dojoType=dijit.form.Button
						                          iconClass="siged16 sigedSave16"
						                          onClick="addCampoEstado"
						                          showLabel="true">Agregar</button>
                                            <button dojoType=dijit.form.Button
						                          iconClass="siged16 sigedSave16"
						                          onClick="deleteCampoEstado"
						                          showLabel="true">Eliminar</button></td>                                                                               
                                          </tr>
                                          <tr>
                                             <td colspan="20">
	                                             <div dojoType="dojox.grid.DataGrid"
								                       id="gridEstadoProceso"
								                       jsId="gridEstadoProceso"
								                       rowsPerPage="20"
								                       rowSelector="10px"
								                       style="width:250%;height:150px;"></div>
								                  <div dojoType="dijit.form.TextBox"
								                       type="text"
								                       id="objProceso.estados"
								                       jsId="objProceso.estados"
								                       name="objProceso.estados"
								                       style="display:none;"></div>
								                  <div dojoType="dijit.form.TextBox"
								                       type="text"
								                       id="objProceso.arrEstadoToDelete"
								                       jsId="objProceso.arrEstadoToDelete"
								                       name="objProceso.arrEstadoToDelete"
								                       style="display:none;"></div>									                       							                       
							                </td>                                             
                                          </tr>
                                    </table>
                                 </td>
                              </tr>                                                         
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Lista de Etapas</td>
                                 <td colspan="4" class="label">
                                    <table width="500px" frame="box">
                                          <tr>
                                             <td><button dojoType=dijit.form.Button
						                          iconClass="siged16 sigedSave16"
						                          onClick="addCampo"
						                          showLabel="true">Agregar</button>
                                            <button dojoType=dijit.form.Button
						                          iconClass="siged16 sigedSave16"
						                          onClick="deleteCampo"
						                          showLabel="true">Eliminar</button></td>                                                                               
                                          </tr>
                                          <tr>
                                             <td colspan="20">
	                                             <div dojoType="dojox.grid.DataGrid"
								                       id="gridEtapaProceso"
								                       jsId="gridEtapaProceso"
								                       rowsPerPage="20"
								                       rowSelector="10px"
								                       style="width:100%;height:300px;"></div>
								                  <div dojoType="dijit.form.TextBox"
								                       type="text"
								                       id="objProceso.etapas"
								                       jsId="objProceso.etapas"
								                       name="objProceso.etapas"
								                       style="display:none;"></div>
								                  <div dojoType="dijit.form.TextBox"
								                       type="text"
								                       id="objProceso.arrEtapaToDelete"
								                       jsId="objProceso.arrEtapaToDelete"
								                       name="objProceso.arrEtapaToDelete"
								                       style="display:none;"></div>									                       							                       
							                </td>                                             
                                          </tr>
                                    </table>
                                 </td>
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
      
      </s:form>
      
   </body>
   
</html>
