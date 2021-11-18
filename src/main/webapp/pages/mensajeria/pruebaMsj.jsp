<%@page  import="java.text.*" %>
<%@page  import="java.util.*" %>
<%@page  import="com.btg.ositran.siged.domain.Mensajeria" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %> 
 <%--<%@ taglib uri="http://ajaxtags.org/tags/ajax" prefix="ajax"%> --%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="org.ositran.utils.MensajeriaDatos"%>
<html>
   <head>
     <title>Tramite Documentario</title> 
      <%--<style type="text/css">
         @import "js/dojo/dijit/themes/soria/soria.css";
         @import "css/estilo.css";
      </style>        --%>
      <link rel="stylesheet" type="text/css" href="./css/estilo.css">


      <%
            MensajeriaDatos msj = (MensajeriaDatos) request.getAttribute("objmjda");
      %>

      <script type="text/javascript">
      var serviceMsg = new dojo.rpc.JsonService("SMDAction.action");
        var tipoEnvio = function(){
            var codigo = document.forms[0].codigo.value;
            if(codigo != ""){
               Tipoenvio.viewcodMensajeria(codigo,tipoEnviocallBack);
            }
         };
         
        var storeAmbito = new dojo.data.ItemFileReadStore({url: "autocompletarAmbitoenvio.action"});
        var store2=new dojo.data.ItemFileReadStore({url: "autocompletarempresadestino.action"});
        
        /*var UpdateInfoTipoEnvio = function(codigo) {
            console.log("Codigo de tipo de envio [" + codigo + "]");

            store.fetchItemByIdentity({
               identity: codigo,
               onItem: function(item) {
                  dojo.byId("tipoEnvio1").value = store.getValue(item, "tipoenvio1");
                  dojo.byId("tipoEnvio2").value = store.getValue(item, "tipoenvio2");
               }
            });
         };*/
         /*var UpdateInfoEmpresaDestino = function(codigo) {
             console.log("Codigo de Empresa Destino [" + codigo + "]");

             store2.fetchItemByIdentity({
                identity: codigo,
                onItem: function(item) {
                   dojo.byId("empresa").value = store2.getValue(item, "nombre");
                }
             });
          };*/
        var empresaDestino = function(codigodes){

            if(codigodes != ""){
               Empresadestino.Viewcod(codigodes,empresaDestinocallBack);
            }
         };  
         function empresaDestinocallBack(data){
             if(data!=null){
                DWRUtil.setValue("empresa",data.nombre);
             }else{
                DWRUtil.setValue("empresa","");
             }

          }  

		var validarForm = function(){
			var ambito = dojo.byId("codigo").value;
			var empdestino = dojo.byId("codigodes").value;
			var referencia = document.getElementsByName("referencia")[0].value;
			console.debug("ambito "+ambito+" empdestino "+empdestino+" referencia "+referencia);
			//if(ambito=="" || empdestino=="" || referencia==""){
			if(ambito=="" || empdestino==""){
				return false;
			}
			return true;
		}; 
         
         function guardar(){
             console.debug("guardar()");
             if(validarForm()== true){
            	 console.debug("exito");
                 dojo.xhrPost({
                    form: dojo.byId("mifor2"),
                    load: function(data) {
	                 	resetDetail();
	              	    buildTabDocumentosAdicionales("dofinddatos.action?Idmen=" + <%=msj.getIdmen()%>, "contentPaneDatosDocumento", "Datos de Documento");
	             	    buildTabDocumentosAdicionales("dofindkey.action?Idmen=" + <%=msj.getIdmen()%>, "contentPaneDatosEnvio", "Datos de Envio");
	             	    buildTabDocumentosAdicionales("dofindkeycargo.action?Idmen=" + <%=msj.getIdmen()%>, "contentPaneDatosCargo", "Datos del Cargo");
	             	   
	                 	dijit.byId("tabContainerDetail").selectChild(dijit.byId("contentPaneDatosEnvio"));
                    }
                  });
             }
             else alert("Debe completar todos los datos");
         }	
         
         function verArchivo(url) {
             var service = new dojo.rpc.JsonService("SMDAction.action");
             var defered = service.verArchivoAlfresco(url);

             defered.addCallback(function(url) {
                 console.log("URL Alfresco [" + url + "]");
                 alert(url);
                 window.open(url);
             });
         }
      </script>


      <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
      <style type="text/css">

         body {
            background-color: #ffffff;
         }

         .barra {
            scrollbar-3dlight-color:#CCCCCC;
            scrollbar-arrow-color:#568BBF;
            scrollbar-base-color:#C3D5E9;
            scrollbar-darkshadow-color:#666666;
            scrollbar-face-color:#666666;
            scrollbar-highlight-color:#669BCD;
            scrollbar-shadow-color:#999999;
         }

      </style>

   </head>

   <body  class="barra" topmargin="0" leftmargin="0" rigthmargin="0" >
	<s:form  action="doingdatos" name="mifor2" id="mifor2" method="post">
      <input type="hidden" name="Idmen" value="<%=msj.getIdmen()%>"/>
      <input type="hidden" name="idmensajeria" value="<%=msj.getIdmen()%>"/>
      <input type="hidden" name="fecha" value="<%=msj.getFechaderiva()%>"/>
      <input type="hidden" name="asunt" value="<%=msj.getAsunto()%>"/>
      <input type="hidden" name="tpdoc" value="<%=msj.getTipocumento()%>"/>
      <input type="hidden" name="nmdoc" value="<%=msj.getNumerodoc()%>"/>
      
      <input type="hidden" name="numinterno" value="<%=msj.getNuminterno()%>"/>
      <input type="hidden" name="usudes" value="<%=msj.getDestinatario()%>"/>
      <input type="hidden" name="dirdes" value="<%=msj.getDirec()%>"/>
      <input type="hidden" name="departamento" value="<%=msj.getDepartamento()%>"/>
      <input type="hidden" name="provincia" value="<%=msj.getProvincia()%>"/>
      <input type="hidden" name="distrito" value="<%=msj.getDistrito()%>"/>


      <table width="100%">
         <tr align="center">
            <td colspan="3" align="left">
            	<div dojoType="dijit.Toolbar">
                  <s:if test="#session._RECURSO.UsuEnviarMsg">
                  <div>
                    <button dojoType="dijit.form.Button"
                       type="button"
                       iconClass="siged16 sigedSave16"
                       onClick="guardar();"
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
            <td height="14" colspan="3"> <table width="95%"  border="1" align="center" bordercolor="#669BCD" bgcolor="#BFD9F1">
                  <tr>
                     <td height="75"><table width="98%" height="97" align="center" >
                           <tr>
                              <td width="1%" ></td>
                              <td width="10%" height="5" ></td>
                              <td width="14%" ></td>
                              <td width="9%" ></td>
                              <td width="18%" ></td>
                              <td width="15%" ></td>
                              <td width="29%" ></td>
                              <td width="3%" ></td>
                           </tr>
                           <tr>
                              <td></td>
                              <!-- <td height="16" align="left">N&uacute;mero Interno</td> -->
                              <!-- <td align="left" width="14%" class="label"> -->
                                 <%--<s:textfield name="numinterno" cssClass="cajaMontoTotal" value="" size="20" /> --%>
                                 <%-- <input name="numinterno" type="text" class="cajaMontoTotal" value="<%=msj.getNuminterno()%>" size="20" disabled></td> --%>
                                 <td align="left" height="16" >Tipo Documento</td>
                              <%if (msj != null) {%>
                              <td align="left" width="29%" class="label">
                                 <input name="tipdoc" type="text" class="cajaMontoTotal" value="<%=msj.getTipocumento()%>" size="23" disabled> </td>
                              <%}%> 
                              <td align="left" >N&uacute;mero</td>
                              <td align="left" width="18%" class="label">
                                 <input name="numdoc" type="text" class="cajaMontoTotal" value="<%=msj.getNumerodoc()%>" size="20"  disabled> </td>

                              <td></td>
                           </tr>
                           <tr>
                                 <td></td>
                                 <td height="16" align="left">Tipo Env&iacute;o</td>
                                 <td>
                                    <input name="envio2x" type="text" class="cajaMontoTotal" value="<%=msj.getTiev2()%>" size="20" disabled>
                                 </td>
                                 <td>Ambito Env&iacute;o</td>
                                 <td>
                                    <div dojoType="dijit.form.FilteringSelect"
                                         store="storeAmbito"
                                         idAttr="id"
                                         labelAttr="label"
                                         searchAttr="label"
                                         name="codigo"
                                         id="codigo"
                                         editable="false"
                                         required="true"
                                         promptMessage="Seleccione un Ambito"
                                         size="20" ></div>

                               <!--      <%if(msj.getNomtipoambito()!=null){%>


                                     <input name="dirdes" type="text" class="cajaMontoTotal" value="<%=msj.getNomtipoambito()%>" size="88" disabled>
                                     <%}else{%>
                                     <input name="dirdes" type="text" class="cajaMontoTotal" value=" " size="88" style="width:35% " disabled>
                                     <%}%>  -->

                                 </td>
                                 
                                 <td></td>
                                 <td width="1%">

                                 </td>
                           </tr>
                           <tr>
                                 <td></td>
                                 <td height="16" align="left">Emp. Destino</td>
                                 <td>
                                    <div dojoType="dijit.form.FilteringSelect"
                                         store="store2"
                                         idAttr="id"
                                         labelAttr="label"
                                         searchAttr="label"
                                         name="codigodes"
                                         id="codigodes"
                                         required="true"
                                         promptMessage="Seleccione una Emp. Destino"
                                         size="50"></div>
                                 </td>
<!--                                 <td colspan="4"><input id="empresa" name="empresa" type="text" class="cajaMontoTotal" value="" size="65" disabled></td>-->
                           </tr>                          

                           <tr>
                              <td></td>
                              <td height="16" align="left">Destinatario</td>
                              <td colspan="5" align="left" class="label">
                                 <input name="usudes" type="text" class="cajaMontoTotal" value="<%=msj.getDestinatario()%>" size="88" disabled>
                              </td>
                              <td></td>
                           </tr>

                           <tr>
                              <td></td>
                              <td height="16" align="left">Direcci&oacute;n</td>
                              <td colspan="5" align="left" class="label">
                                 <input name="dirdes" type="text" class="cajaMontoTotal" value="<%=msj.getDirec()%>" size="88" disabled>
                              </td>
                              <td></td>
                           </tr>
                           <tr>
                              <td></td>
                              <td height="16" align="left">Ubigeo</td>
                              <td colspan="5" align="left" class="label">
                                 <input name="provincia" type="text" class="cajaMontoTotal" value="<%=msj.getDepartamento()%>" size="20" disabled>
                                 &nbsp;&nbsp;
                                 <input name="departamento" type="text" class="cajaMontoTotal" value="<%=msj.getProvincia()%>" size="20" disabled>
                                 &nbsp;&nbsp;
                                 <input name="distrito" type="text" class="cajaMontoTotal" value="<%=msj.getDistrito()%>" size="20" disabled>
                              </td>
                              <td></td>
                           </tr>
                           <tr>
                              <td></td>
                              <td height="16" align="left">Referencia</td>
                              <td colspan="5" align="left" class="label">
                                  <input name="referencia" type="text" class="cajaMontoTotal" value="<%=msj.getReferenciaDireccionCliente()%>" size="88" >
                              </td>
                              <td></td>
                           </tr>
                           <tr>
                              <td height="5" > </td>
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
