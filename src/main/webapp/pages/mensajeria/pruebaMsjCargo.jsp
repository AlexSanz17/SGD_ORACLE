<%@page  import="java.text.*" %>
<%@page  import="java.util.*" %>
<%@page  import="com.btg.ositran.siged.domain.Mensajeria" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%--<%@ taglib uri="http://ajaxtags.org/tags/ajax" prefix="ajax"%>--%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
   <head>
      <title>Tramite Documentario</title>
      <style type="text/css">
         @import "js/dojo/dijit/themes/soria/soria.css";
         @import "css/estilo.css";
      </style>      


      <%
            Mensajeria msj = (Mensajeria) request.getAttribute("obMsj");
      %>
      <script type="text/javascript" src="js/siged/mensajeriaCargo.js"></script>
      <script language="JavaScript">

         var store2 = new dojo.data.ItemFileReadStore({url: "autocompletarEstadoCargo.action"});

         var validarForm = function (){
        	 var costoenvio = document.getElementsByName("objCg.costoenvio")[0].value;
	         if (!validaNumero("objCg.costoenvio")) {
		         return false;
		     }

        	 var estado = dojo.byId("estadocargo").value;
        	 var fechaentrega= dojo.byId("sFechaEntrega").value;
        	 var fechacargo= dojo.byId("sFechaCargo").value;
        	 var fechadevcargo= dojo.byId("sFechaDevCargo").value;
        	 var fechadevdoc= dojo.byId("sFechaDevDoc").value;
        	 var fecha1visita= dojo.byId("sFechaPrimeraVisita").value;
        	 var fecha2visita= dojo.byId("sFechaSegundaVisita").value;
        	 
			console.debug("costoenvio ["+costoenvio+"] estado ["+estado+"] fechaentrega ["+fechaentrega+"] fechacargo ["+fechacargo+"] fechadevcargo ["+fechadevcargo+"] fechadevdoc ["+fechadevdoc+"] fecha1visita ["+fecha1visita+"] fecha2visita ["+fecha2visita+"]");
             if(costoenvio=="" || estado=="" || fechaentrega=="" || fechacargo=="" || fechadevcargo=="" || fechadevdoc=="" || fecha1visita=="" || fecha2visita=="" ){
            	 alert("Debe completar todos los datos");
            	 return false;
             }

             var recibido= document.getElementsByName("objCg.recibido")[0].value;
             var observacion1= document.getElementsByName("objCg.obs1")[0].value;
             var observacion2= document.getElementsByName("objCg.obs2")[0].value;
             var observaciones= document.getElementsByName("objCg.observaciones")[0].value;
             console.debug("recibido ["+recibido+"] observacion1 ["+observacion1+"] observacion2 ["+observacion2+"] observaciones ["+observaciones+"]");
            // if(recibido=="" || observacion1=="" || observacion2=="" || observaciones==""){
             if(recibido=="" ){
            	 alert("Debe completar todos los datos");
            	 return false;
             }
             console.debug("todo ok formulario cargo");
             return true;	
         };
         
         function submitForm() {
            if(validarForm() == true){
           	 if (confirm("Desea guardar el cargo? ")==true){  
         		dojo.xhrPost( {
         			form : dojo.byId("mifor4"),
         			load : function(data) {

    	            	resetDetail();
    	            	showGridInbox(TIPO_GRID_MENSAJERIA_RECIBIDOS);
         			}
         		});
             }	
            } 
    	 }     
         
         function mostrarAdjuntarArchivo(){
     		dojo.xhrPost({
     			url: "goAdjuntarArchivo.action",
     			content:{
     				iIdDoc : "<s:property value='idDocumento' />"
     			},
     			load: function(data){
     				console.debug("data");
     				if(!dijit.byId("dlgAnexar")){
	        				new dijit.Dialog({
	        					id: "dlgAnexar",
	                			draggable:"true",
	                			style:"height:220px;width:400px;display:none;",
	                			title:"Adjuntar Archivo",
	        		            onClose: dojo.hitch(this, function(){
	        		            	dijit.byId("dlgAnexar").hide();
	        		        		dijit.byId("dlgAnexar").destroyRecursive();
	        		            })									        			    
	                		});
     				}
     				console.debug(dijit.byId("dlgAnexar"));
     				dijit.byId("dlgAnexar").attr("content", data);
     				dijit.byId("dlgAnexar").show();
     			}
     		});   
     	}
        
      </script>
	
      <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">


   </head>

   <body  class="soria">
	<s:form action="doingcargo" name="mifor4"  id="mifor4" method="post">
      <input type="hidden" name="idmensajeria" value="<%=msj.getIdmensajeria()%>"/>
      <table width="100%">
         <tr align="center">
            <td colspan="3" align="left">
            	<div dojoType="dijit.Toolbar">
                  <s:if test="#session._RECURSO.UsuEnviarMsg">
	                    <button dojoType="dijit.form.Button"
	                       type="button"
	                       iconClass="siged16 sigedSave16"
	                       onClick="submitForm();"
	                       showLabel="true">Guardar</button>
                  </s:if>
                  <s:if test="#session._RECURSO.BtnAdjuntarArchivo">
                  	<div dojoType="dijit.form.Button"  onClick="mostrarAdjuntarArchivo()" iconClass="siged16 iconoAgregar">Adjuntar Archivo</div>
                  </s:if>
                </div>            
            </td>
         </tr>
         <tr>
         	<td class="plomo" style="height:3em; width:6em;">Archivos: </td>
            <td colspan="2" class="plomo">
            	 <s:iterator value="#session._UPLOADLIST.upload1">
                 	<a onclick="verArchivo('<s:property value='rutaAlfresco' />')" alt="Ver Archivo"><s:property value="nombreReal" /></a><br />
                 </s:iterator>
            </td>
         </tr>   
         <tr>
            <td height="14" colspan="3">
               <table style="width: 95%" border="1" align="center" bordercolor="#669BCD" bgcolor="#BFD9F1">
                  <tr>
                     <td height="75">
                        <table style="width: 98%" height="219" align="center" >
                           <tr>
                                 <td width="1%" ></td>
                                 <td width="13%" height="5" ></td>
                                 <td width="15%" ></td>
                                 <td width="5%" ></td>
                                 <td width="10%" ></td>
                                 <td width="14%" ></td>
                                 <td width="14%" ></td>
                                 <td width="1%" ></td>
                           </tr>
                           <tr>
                              <td></td>
                              <td height="16" align="left">Costo Env&iacute;o</td>
                              <td align="left" class="label">
                                 <s:textfield id="objCg.costoenvio" name="objCg.costoenvio" cssClass="cajaMontoTotal" size="23" onkeypress="if (event.keyCode < 45 || event.keyCode > 57) event.returnValue = false;"/>
                              </td>
                              <td align="left" ></td>
                              <td align="left" class="label">
                              </td>                              
                             <!-- <td align="left" >Grupo</td>
                              <td align="left" class="label" colspan="2">
                                 <input name="grupo" type="radio" value="Mañana" checked>&nbsp;Mañana&nbsp;
                                    <input name="grupo" type="radio" value="Tarde" >&nbsp;Tarde
                              </td> -->
                           </tr>
                           <tr>
                              <td></td>
                              <td height="16" align="left">Fecha Entrega</td>
                              <td align="left" class="label">
                                 <div id="divFechaEntrega"></div>
                              </td>
                              <td align="left" ></td>
                              <td align="left" class="label">
                              </td>
                              <td align="left" >Estado</td>
                              <td align="left" class="label">
                                 <div dojoType="dijit.form.FilteringSelect"
                                         store="store2"
                                         searchAttr="label"
                                         name="estadocargo"
                                         value="Pendiente"
                                         id="estadocargo" ></div>
                              </td>
                              <td></td>
                           </tr>
                           <tr>
                              <td></td>
                              <td height="16" align="left">Recibido por</td>
                              <td colspan="3" align="left" class="label">
                                 <s:textfield  id="objCg.recibido" name="objCg.recibido" cssClass="cajaMontoTotal" size="40" onkeypress = " return ( (event.keyCode ? event.keyCode : event.which ? event.which : event.charCode)!= 13);" />
                              </td>
                              <td align="left" >Fecha Cargo</td>
                              <td align="left" class="label">
                                 <div id="divFechaCargo"></div>                                 
                              </td>
                              <td></td>
                           </tr>
                           <tr>
                              <td></td>
                              <td height="16" align="left">Fecha Devoluci&oacute;n de Cargo</td>
                              <td colspan="3" align="left" class="label">
                                 <div id="divFechaDevCargo"></div> 
                              </td>
                              <td align="left" >Fecha Devoluci&oacute;n de Documento</td>
                              <td align="left" class="label">
                                <div id="divFechaDevDoc"></div> 
                              </td>
                              <td></td>
                           </tr>
                           <tr>
                              <td></td>
                              <td height="16" align="left">Primera Visita</td>
                              <td align="left" class="label">
                                 <div id="divFechaPrimeraVisita"></div>
							  </td>                                         
                              <td align="left">Obs</td>
                              <td colspan="3" align="left"class="label">
								<s:textfield name="objCg.obs1" cssClass="cajaMontoTotal" size="50" maxlength="100" onkeypress = " return ( (event.keyCode ? event.keyCode : event.which ? event.which : event.charCode)!= 13);" />
                              </td>
                              <td></td>
                           </tr>
                           <tr>
                              <td></td>
                              <td height="16" align="left">Segunda Visita</td>
                              <td align="left" class="label">
                                 <div id="divFechaSegundaVisita"></div> 
                              </td>
                              <td align="left">Obs</td>
                              <td colspan="3" align="left" class="label">
                                 <s:textfield name="objCg.obs2" cssClass="cajaMontoTotal" size="50" maxlength="100" onkeypress = " return ( (event.keyCode ? event.keyCode : event.which ? event.which : event.charCode)!= 13);"/>
                              </td>
                              <td></td>
                           </tr>
                           <tr>
                              <td></td>
                              <td height="16" align="left">Observaciones</td>
                              <td colspan="5" align="left" class="label">
                                 <s:textfield name="objCg.observaciones" cssClass="cajaMontoTotal" size="90" maxlength="100" onkeypress = " return ( (event.keyCode ? event.keyCode : event.which ? event.which : event.charCode)!= 13);" />
                              </td>
                              <td></td>
                           </tr>
                           <tr>
                              <td height="5" ></td>
                           </tr>
                        </table></td>
                  </tr>
               </table></td>
         </tr>
         <tr>
            <td height="14"  colspan="3"></td>
         </tr>
      </table>
	</s:form>
   </body>   
</html>
