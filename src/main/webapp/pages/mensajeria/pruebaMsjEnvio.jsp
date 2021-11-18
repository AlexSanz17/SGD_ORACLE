<%@page  import="java.text.*" %>
<%@page  import="java.util.*" %>
<%@page  import="com.btg.ositran.siged.domain.Mensajeria" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://ajaxtags.org/tags/ajax" prefix="ajax"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
    Mensajeria msj = (Mensajeria) request.getAttribute("obMsj");
%>
<html>
   <head>
      <title>Tramite Documentario</title>

      <style type="text/css">
         @import "js/dojo/dijit/themes/soria/soria.css";
         @import "css/estilo.css";
      </style>
      <script type="text/javascript">
      var storeUnidadPeso = new dojo.data.ItemFileReadStore({url: "autocompletarUnidadPeso.action"});
      var   UpdateInfoCourier = function(codigo) {
            console.log("Codigo de tipo de envio [" + codigo + "]");
            var store = new dojo.data.ItemFileReadStore({url: "autocompletarcourrier.action"});
            store.fetchItemByIdentity({
               identity: codigo,
               onItem: function(item) {
                  dojo.byId("nomcourierx").value = store.getValue(item, "nombre");
                  dojo.byId("codcourier").value = codigo;
                  //alert("nomcourier "+dojo.byId("nomcourierx").value+" obEnv.numerocourier "+dojo.byId("obEnv.numerocourier").value);
               }
            });
         };
      </script>

    
	<script type="text/javascript" src="js/siged/mensajeriaEnvio.js"></script>
      <script type="text/javascript">
      handler = dojo.connect(dijit.byId("contentPaneDetail"), "onLoad", viewEnvioOnLoad);
         function mostrarcargo() {
            <s:if test="#session._USUARIO.rol != null">
                  parent.frames["secondFrame"].location.href = "/siged/dofindkeycargo.action?Idmen="+<%=msj.getIdmensajeria()%>;
            </s:if>
         <s:else>
          dijit.byId("contentPaneDetail").setHref("/siged/dofindkeycargo.action?Idmen="+<%=msj.getIdmensajeria()%>);
         </s:else>
        	 
 	        
           // 
         }
         function mostrardatos() {
            <s:if test="#session._USUARIO.rol != null">
                  parent.frames["secondFrame"].location.href = "/siged/dofinddatos.action?Idmen="+<%=msj.getIdmensajeria()%>;
                  </s:if>
         <s:else>
          dijit.byId("contentPaneDetail").setHref("/siged/dofinddatos.action?Idmen="+<%=msj.getIdmensajeria()%>);
         </s:else>
        	 
        	//xdr
            // 
         }

         function submitForm() {
             if (validarForm()==true) {
             	//alert("nomcourier "+dojo.byId("nomcourierx").value+" codcourierr "+dojo.byId("codcourierr").value);
                 dojo.xhrPost({
                     form: dojo.byId("mifor3"),
                     load: function(data) {
                 	
                 	resetDetail();
              	    buildTabDocumentosAdicionales("dofinddatos.action?Idmen=" + <%=msj.getIdmensajeria()%>, "contentPaneDatosDocumento", "Datos de Documento");
             	    buildTabDocumentosAdicionales("dofindkey.action?Idmen=" + <%=msj.getIdmensajeria()%>, "contentPaneDatosEnvio", "Datos de Envio");
             	    buildTabDocumentosAdicionales("dofindkeycargo.action?Idmen=" + <%=msj.getIdmensajeria()%>, "contentPaneDatosCargo", "Datos del Cargo");
             	    
                 	dijit.byId("tabContainerDetail").selectChild(dijit.byId("contentPaneDatosCargo"));
                     }
                  });            	
             }
          }
      </script>
   </head>

   <body class="soria">
      <s:form action="doingenvio" name="mifor3" method="post" id="mifor3">
         <input type="hidden" name="idmensajeria" value="<%=msj.getIdmensajeria()%>"/>
         <table width="100%">
            <tr align="center">
               <td colspan="3" align="left">
               	<div dojoType="dijit.Toolbar">
                  <s:if test="#session._RECURSO.UsuEnviarMsg">
	                  <div>
	                    <button dojoType="dijit.form.Button"
	                       type="button"
	                       iconClass="siged16 sigedSave16"
	                       onClick="submitForm();"
	                       showLabel="true">Guardar</button>
	                  </div>                   
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
                  <table width="95%"  border="1" align="center" bordercolor="#669BCD" bgcolor="#BFD9F1">
                     <tr>
                        <td height="75"> <table width="98%" height="97" align="center" >
                              <tr>
                                 <td width="1%" ></td>
                                 <td width="13%" height="5" ></td>
                                 <td width="15%" ></td>
                                 <td width="15%" ></td>
                                 <td width="14%" ></td>
                                 <td width="14%" ></td>
                                 <td width="14%" ></td>
                                 <td width="1%" ></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">N&uacute;mero Gu&iacute;a</td>
                                 <td>
                                    <s:textfield id="obEnv.numeroguia" name="obEnv.numeroguia" cssClass="cajaMontoTotal" size="15"  />
                                    <!-- onkeypress="if (event.keyCode < 45 || event.keyCode > 57) event.returnValue = false;"-->
                                 </td>
                                 <td align="left" >Courier</td>
                                 <td>
                                    <div id="fsCourier" name="fsCourier"></div>
                                 </td>
                                 <td colspan="2">
                                 <input type="hidden" id="nomcourierx" name="nomcourierx" type="text" class="cajaMontoTotal" value="" size="31" >
                                 <input type="hidden" name="codcourier" id="codcourier" >
                                 </td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Fecha Salida</td>
                                 <td align="left" width="19%" class="label">
                                    <div id="divFechaSalida"></div>                                       
                                 </td>
                                 
                            
                                 <td align="left" >Tipo de Env&iacute;o</td>
                                 <td colspan="1" align="left" class="label">
                                 	<input name="tipoenviox" type="text" class="cajaMontoTotal" value="<s:property value="obMsj.idtipoenvio.tipoenvio2"/>" size="15"  disabled>
                                 	<input type="hidden" name="envio2" value="<s:property value="obMsj.idtipoenvio.tipoenvio2"/>" >
                                 </td>
                                 <td colspan="1">
									Ambito de Env&iacute;o</td> <td>
									<input name="ambitoenviox" type="text" class="cajaMontoTotal" value="<s:property value="obDatos.idambitoenvio.nomambitoenvio"/>" size="15"  disabled>
                                    <input type="hidden" name="ambitoenvio" value="<s:property value="obDatos.idambitoenvio.nomambitoenvio"/>" >
                                 </td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Fecha de Entrega</td>
                                 <td  align="left" width="19%" class="label">
                                    <%--<s:textfield id="obEnv.diasdistribuicion" name="obEnv.diasdistribuicion" cssClass="cajaMontoTotal" size="8" onkeypress="if (event.keyCode < 48 || event.keyCode > 57) event.returnValue = false;"/>--%>
                                       <div id="divFechaEntrega"></div>                                       
                                 </td>

                                 <td height="16" align="left">Fecha de Devoluci&oacute;n de Cargo</td>
                                 <td  align="left" width="19%" class="label">
                                     <%--  <s:textfield id="obEnv.diasdevcargo" name="obEnv.diasdevcargo" cssClass="cajaMontoTotal" size="8" onkeypress="if (event.keyCode < 48 || event.keyCode > 57) event.returnValue = false;"/> --%>
                                      <div id="divFechaDevCargo"></div>                                       
                                 </td>

                                 <td height="16" align="left">Fecha de Devoluci&oacute;n - &Aacute;rea</td>
                                <td  align="left" width="19%" class="label">
                                    <%-- <s:textfield id="obEnv.diasdevolucion" name="obEnv.diasdevolucion" cssClass="cajaMontoTotal" size="8" onkeypress="if (event.keyCode < 48 || event.keyCode > 57) event.returnValue = false;"/> --%>
                                     <div id="divFechaDevArea"></div>                                       
                                 </td>


                             <!--
                                 <td align="left" width="19%" class="label">
                                    <div id="divFechaSalida"></div>
                              </td>  -->


                                 <td></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Peso Documento</td>
                                 <td  align="left" class="label">
<!--                                    <s:textfield id="obEnv.diasdistribuicion" name="obEnv.diasdistribuicion" cssClass="cajaMontoTotal" size="8" />-->
                                    <s:textfield id="obEnv.pesodcmto" name="obEnv.pesodcmto"  cssClass="cajaMontoTotal" size="8" onkeypress="if (event.keyCode < 48 || event.keyCode > 57) event.returnValue = false;"/>
                                 </td>

                                 <td height="16" align="left">Unidad Peso</td>
                                 <td  align="left" class="label">
<!--                                    <s:textfield id="obEnv.diasdevcargo" name="obEnv.diasdevcargo" cssClass="cajaMontoTotal" size="8" />-->
	                                 <div dojoType="dijit.form.FilteringSelect"
	                                         store="storeUnidadPeso"
	                                         searchAttr="label"
	                                         name="unidadpeso"
	                                         value="Kilogramos"
	                                         id="unidadpeso" ></div>                                    
                                 </td>

                                 <td colspan="3"></td>
                              </tr>                              
                              <tr>
                                 <td height="5" ></td>
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
