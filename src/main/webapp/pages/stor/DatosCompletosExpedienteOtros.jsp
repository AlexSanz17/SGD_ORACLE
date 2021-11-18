<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://ajaxtags.org/tags/ajax" prefix="ajax"%>
<%@page import="com.btg.ositran.siged.domain.Documento"%>
<%@page import="com.btg.ositran.siged.domain.Expediente"%>
<html>
   <head>
      <s:head theme="ajax" />
      <title>Tramite Documentario</title>
      <link rel="stylesheet" type="text/css" href="css/estilo.css">
      <script language="javascript" src="js/form.js"> </script>
      <script language="Javascript" src="js/general.js"></script>
      <link rel="stylesheet" type="text/css" media="all" href="css/calendar-green.css">
      <script type="text/javascript" src="js/calendar.js"></script>
      <script type="text/javascript" src="js/calendar-es.js"></script>
      <script type="text/javascript" src="js/calendar-setup.js"></script>

      <script type="text/javascript" src="./js/prototype-1.4.0.js"></script>
      <script type="text/javascript" src="./js/scriptaculous.js"></script>
      <script type="text/javascript" src="./js/overlibmws.js"></script>
      <script type="text/javascript" src="./js/ajaxtags-1.2-beta2.js"></script>
      <link rel="stylesheet" type="text/css" href="./css/ajaxtags.css" />
      <link rel="stylesheet" type="text/css" href="./css/displaytag.css" />

      <script type='text/javascript' src='./dwr/engine.js'> </script>
      <script type='text/javascript' src='./dwr/util.js'> </script>
      <script type='text/javascript' src='./dwr/interface/toolDwr.js'> </script>

      <!--Begin Script STOR-->
      <script language="JavaScript">
         function updateExpediente(){
            document.form1.action="/siged/doStor_updateExpediente.action"
            document.form1.submit();
         }

         function editarMotivoSubmotivo(idsubmotivo){            
            var motivo = dwr.util.getValue("motivo"+idsubmotivo);
            var submotivo = dwr.util.getValue("submotivo"+idsubmotivo);            
            dwr.util.setValue("editnmotivo",motivo);
            dwr.util.setValue("editnsubmotivo",submotivo);
            dwr.util.setValue("idUpdate",idsubmotivo);
         }

         function updateMotivoSubmotivo(){
            var idUpdate = dwr.util.getValue("idUpdate");           
            if(idUpdate.valueOf().length==0){
               alert("Tiene que seleccionar la fila a editar");
            }else{
               var motivo = dwr.util.getValue("editnmotivo");
               var submotivo = dwr.util.getValue("editnsubmotivo");
               var newIdSubmotivo = dwr.util.getValue("idsubmotivo");
               dwr.util.setValue("motivo"+idUpdate,motivo);
               dwr.util.setValue("submotivo"+idUpdate,submotivo);
               dwr.util.setValue("id"+idUpdate,newIdSubmotivo);
               dwr.util.setValue("idUpdate","");
            }            
         }

         function clearEdit(){
            dwr.util.setValue("editnmotivo","");
            dwr.util.setValue("editnsubmotivo","");
            dwr.util.setValue("idUpdate","");
         }

         function refrescar(){
            window.opener.parent.location.href ='/siged/inicio.action?sTipoFrame=grid';
            window.opener.parent.frames["mainFrame"].location.href = "/siged/doBody.action";
            window.close();
         }
      </script>
      <!--End Script STOR-->

      <script language="JavaScript">
         function Abrir_ventanaBuscar (pagina) {
            var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=760, height=530, top=50, left=200";
            window.open(pagina,"",opciones);
         }

         function Abrir_ventanaCargo (pagina) {
            location.href="../../pages/tramite/cargo.html";
         }

         function Abrir_pagina (pagina) {
            var opciones="location=mainFrame";
            window.open(pagina,"",opciones);
         }

         function putTextOnTextBox(textToPut){
            document.all.reciveTheText.value = textToPut;
         }
         function regresar(){
            history.back();
         }
      </script>
   </head>
   <body  class="barra" topmargin="0" leftmargin="0" rigthmargin="0" <s:if test="refrescar!=null">onload="refrescar()"</s:if>>
      <form name="form1" action="" method="POST">
         <s:hidden name="idDocumento" />
         <table width="100%">
            <tr>
               <td height="14" colspan="3"> </td>
            </tr>
            <tr>
               <td height="20"colspan="3" class="titulo">
                  <table width="99%" height="20" align="center" bgcolor="#A2C0DC">
                     <tr>
                        <td align="left" class="titulo">Datos del Expediente</td>
                     </tr>
                  </table>
               </td>
            </tr>
            <tr align="center">
               <td width="1%" align="center">&nbsp;</td>
               <td width="99%" colspan="2" align="left">
                  <!--<img src="images/xx.bmp" onClick="updateExpediente()" alt="Registrar"/>-->
               </td>
            </tr>
            <tr>
               <td height="14" colspan="3">
                  <table width="95%" align="center" bordercolor="#669BCD" bgcolor="#BFD9F1">
                     <tr>
                        <td height="75">
                           <table width="100%" align="center" border="5">
                              <tr>
                                 <td width="2%" ></td>
                                 <td width="20%" height="5" ></td>
                                 <td width="20%" ></td>
                                 <td width="15%" ></td>
                                 <td width="40%" ></td>
                                 <td width="3%" ></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td width="30%" height="16" align="left">Referencia</td>
                                 <td colspan="3" align="left" class="label">
                                    <input name="nroexpediente" type="text" class="cajaMontoTotal" value="<s:property value="expediente.getNroexpediente()"/>" size="50" disabled>
                                    <!--<s:textfield name="nroexpediente" cssClass="cajaMontoTotal" size="50" readonly="true"/>-->
                                 </td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td ></td>
                                 <td height="16" align="left">Proceso</td>
                                 <td colspan="3" class="label">
                                    <input name="proceso" type="text" class="cajaMontoTotal" value="<s:property value="expediente.getProceso().getNombre()"/>" size="75" disabled>
                                    <%//<s:textfield name="proceso" cssClass="cajaMontoTotal" size="75" />%>
                                 </td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td colspan="6" height="15"><hr></td>
                              </tr>
                              <tr>
                                 <td colspan="6" height="15" class="label">DOCUMENTO</td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Tipo Documento</td>
                                 <td align="left" width="17%" class="label">
                                    <input name="tipodocumento" type="text" class="cajaMontoTotal" value="<s:property value="documento.getTipodocumento().getNombre()"/>" size="20" disabled>
                                    
                                 </td>
                                 <td align="left" >&nbsp;</td>
                                 <td align="left" width="45%" class="label"></td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Nro. Documento</td>
                                 <td align="left" width="17%" class="label">
                                    <input name="nrodocumento" type="text" class="cajaMontoTotal" value="<s:property value="documento.getNrodocumento()" />" size="20" disabled>
                                    <%//<s:textfield name="nrodocumento" cssClass="cajaMontoTotal" size="20"/>%>
                                 </td>
                                 <td align="left" >&nbsp;</td>
                                 <td align="left" width="45%" class="label"></td>
                                 <td></td>
                              </tr>
                              <!--
							  <tr>
                                 <td></td>
                                 <td height="16" align="left">Nro Folios</td>
                                 <td align="left" width="17%" class="label">
                                    <input name="nrofolios" type="text" class="cajaMontoTotal" value="<s:property value="documento.getNrofolios()" />" size="20">
                                    <%//<s:textfield name="nrofolios" cssClass="cajaMontoTotal" size="20" />%>
                                 </td>
                                 <td align="left" >&nbsp;</td>
                                 <td align="left" width="45%" class="label"></td>
                                 <td></td>
                              </tr>-->
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Asunto</td>
                                 <td colspan="3" align="left" class="label">
                                    <input name="asunto" type="text" class="cajaMontoTotal" value="<s:property value="documento.getAsunto()" />" size="60" disabled>
                                    <%//<s:textfield name="asunto" cssClass="cajaMontoTotal" size="60" />%>
                                 </td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td colspan="6" height="15"><hr></td>
                              </tr>
                              <tr>
                                 <td colspan="6" height="15" class="label">CLIENTE</td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td>Doc Identidad</td>
                                 <td align="left" width="30%" class="label"  colspan="3">
                                 	<s:property value="tipodocidentidad"/>:&nbsp;<s:property value="nrodocidentidad"/>
                                 </td>
                                 <td></td> 
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Nombre/Raz&oacute;n Social</td>
                                 <td colspan="3" align="left" class="label">
                                    <!--<input name="nombrecliente" type="text" class="cajaMontoTotal" value="<s:property value="expediente.getCliente().getRazonsocial()" />" size="50" disabled>-->
                                    <%//<s:textfield name="nombrecliente" cssClass="cajaMontoTotal" size="50" disabled="true" readonly="true" />%>
                                    <s:if test="expediente.getCliente().getTipoidentificacion().getNombre().equalsIgnoreCase('RUC')">
                                       <s:property value="expediente.getCliente().getRazonsocial()" />
                                    </s:if>
                                    <s:else>
                                       <s:property value="expediente.getCliente().getNombres()" /> <s:property value="expediente.getCliente().getApellidopaterno()" /> <s:property value="expediente.getCliente().getApellidomaterno()" />
                                    </s:else>
                                 </td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td  height="16" align="left">Representante Legal</td>
                                 <td colspan="3" align="left" class="label">
                                    <input name="representantelegal" type="text" class="cajaMontoTotal" value="<s:property value="expediente.getCliente().getRepresentantelegal()" />" size="50" disabled>
                                    <%//<s:textfield name="representantelegal" cssClass="cajaMontoTotal" value="" size="50" disabled="true" readonly="true" />%>
                                 </td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Direcci&oacute;n</td>
                                 <td colspan="3" align="left" class="label">
                                    <input name="direccion" type="text" class="cajaMontoTotal" value="<s:property value="expediente.getCliente().getDireccionprincipal()" />" size="50" disabled>
                                    <%//<s:textfield name="direccion" cssClass="cajaMontoTotal" size="50" disabled="true" />%>
                                 </td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Direcci&oacute;n Procesal</td>
                                 <td colspan="3" align="left" class="label">
                                    <input name="direccionprocesal" type="text" class="cajaMontoTotal" value="<s:property value="expediente.getCliente().getDireccionalternativa()" />" size="50" disabled>
                                    <%//<s:textfield name="direccionprocesal" cssClass="cajaMontoTotal" size="50" disabled="true" readonly="true" />%>
                                 </td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Tel&eacute;fono</td>
                                 <td colspan="3" align="left" class="label">
                                    <input name="telefono" type="text" class="cajaMontoTotal" value="<s:property value="expediente.getCliente().getTelefono()" />" size="20" disabled>
                                    <%//<s:textfield name="telefono" cssClass="cajaMontoTotal" size="20" disabled="true" readonly="true" />%>
                                 </td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Correo Electr&oacute;nico</td>
                                 <td colspan="3" align="left" class="label">
                                    <input name="correo" type="text" class="cajaMontoTotal" value="<s:property value="expediente.getCliente().getCorreo()" />" size="50" disabled>
                                    <%//<s:textfield name="correo" cssClass="cajaMontoTotal" size="50" disabled="true" readonly="true" />%>
                                 </td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td colspan="6" height="15"><hr></td>
                              </tr>
                              <tr>
                                 <td colspan="6" height="15" class="label">CONCESIONARIO</td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Nro RUC</td>
                                 <td colspan="3" align="left" class="label">
                                    <input name="nrorucconcesionario" type="text" class="cajaMontoTotal" value="<s:property value="expediente.getConcesionario().getRuc()" />" disabled size="30" onKeyPress="location.href='nuevoArchMPKey4.html'" >
                                    <%//<s:textfield name="nrorucconcesionario" cssClass="cajaMontoTotal" size="30" disabled="true" readonly="true" />%>
                                 </td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Nombre/Raz&oacute;n Social</td>
                                 <td colspan="3" align="left" class="label">
                                    <input name="nombreconcesionario" type="text" class="cajaMontoTotal" value="<s:property value="expediente.getConcesionario().getRazonsocial()" />" size="30" disabled>
                                    <%//<s:textfield name="nombreconcesionario" cssClass="cajaMontoTotal" size="30" disabled="true" readonly="true" />%>
                                 </td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Direcci&oacute;n</td>
                                 <td colspan="3" align="left" class="label">
                                    <input name="direccionconcesionario" type="text" class="cajaMontoTotal" value="<s:property value="expediente.getConcesionario().getDireccion()" />" size="30" disabled>
                                    <%//<s:textfield name="direccionconcesionario" cssClass="cajaMontoTotal" size="30" disabled="true" readonly="true" />%>
                                 </td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Correo Electr&oacute;nico</td>
                                 <td colspan="3" align="left" class="label">
                                    <input name="correoconcesionario" type="text" class="cajaMontoTotal" value="<s:property value="expediente.getConcesionario().getCorreo()" />" size="30" disabled>
                                    <%//<s:textfield name="correoconcesionario" cssClass="cajaMontoTotal" size="30" disabled="true" readonly="true" />%>
                                 </td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td colspan="6" height="15"><hr></td>
                              </tr>
                              <tr>
                                 <td colspan="6" height="15" class="label">DATOS ADICIONALES</td>
                              </tr>
                              <tr><td colspan="6" height="24" align="left">Lista de Nro Suministros</td></tr>
                              <tr>
                                 <td></td>
                                 <td height="24" align="left"></td>
                                 <td colspan="3" align="left" class="label">
                                    <!--<s:textfield name="nrosuministro" cssClass="cajaMontoTotal" size="30"  readonly="true"/>-->
                                 <table>
                        <tr>
                           <!--<td>Id Suministro</td>
                           <td>Suministro</td>-->
                        </tr>

                        <s:iterator value="documento.getDocumentostor().getSuministroList()">
                        <tr>
                           <!--<td>><s:property value="getIdsuministro()"/></td>
                           <td><s:property value="getNrosuministro()"/></td>-->
                           <td>
                              <input type="text" value="<s:property value="getNrosuministro()" />" class="cajaMontoTotal" readonly>
                           </td>
                        </tr>
                        </s:iterator>
                                 </table>
                                 <div>
                                    <select name="listsuministros" style="display:none" >
                                       <s:iterator value="documento.getDocumentostor().getSuministroList()">
                                          <option value="<s:property value="getIdsuministro()" />" ></option>
                                       </s:iterator>
                                    </select>
                                 </div>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left"></td>
                                 <td colspan="3" align="left" class="label"><td>
                                 </td>
                              </tr>
                              
                              <tr><td colspan="6">Lista Motivos SubMotivos</td></tr>
                              <tr>

                                 <td></td>
                                 <td height="16" align="left"></td>
                                 <td>
                                    <table border="">
                                       <s:iterator value="listaMotivos">
                                          <tr>
                                             <td style="display:none;"><input type="text" class="cajaMontoTotal" name="<s:property value="getIdmotivo()" />" value="<s:property value="getIdmotivo()" />"></td>
                                             <td style="display:none;"><input type="text" class="cajaMontoTotal" id="motivox<s:property value="getIdmotivo()" />" name="motivo" value="<s:property value="getDescripcion()" />"></td>

                                          </tr>
                                          <tr>
                                             <td colspan="3">
                                                <table>
                                                   <s:iterator value="documento.getDocumentostor().getSubmotivoList()">
                                                      <s:if test="getMotivo().getIdmotivo()==idmotivo">
                                                         <tr>
                                                            <td style="display:none;"><input type="text" class="cajaMontoTotal" id="id<s:property value="getIdsubmotivo()" />" name="listidsubmotivos" value="<s:property value="getIdsubmotivo()" />"></td>
                                                            <td><input type="text" class="cajaMontoTotal" id="motivo<s:property value="getIdsubmotivo()" />" name="motivo" value="<s:property value="getMotivo().getDescripcion()" />" readonly></td>
                                                            <td><input type="text" class="cajaMontoTotal" id="submotivo<s:property value="getIdsubmotivo()" />" name="submotivo" value="<s:property value="getDescripcion()" />" readonly></td>
                                                            <!--<td><img id="<s:property value="getIdsubmotivo()" />"src="images/edit.bmp" border="0" onClick="editarMotivoSubmotivo(this.id)" alt="Editar"/></td>-->
                                                         </tr>
                                                      </s:if>
                                                   </s:iterator>
                                                </table>
                                             </td>
                                          </tr>
                                       </s:iterator>
                                    </table>
                                    <table border="">
                                       <tr>
                                          <!--<td>Motivo: </td>
                                          <td>
                                             <s:textfield id="editnmotivo" name="nmotivo" cssClass="cajaMontoTotal" size="30" />
                                             <span id="indicatorMotivo" style="display:none;"><img src="./images/indicator.gif" /></span>
                                             <s:textfield id="editidmotivo" name="idmotivo" cssStyle="display:none"/>

                                             <ajax:autocomplete
                                                source="nmotivo"
                                                target="idmotivo"
                                                baseUrl="./autocompletar.view?metodo=ObtenerMotivo"
                                                className="autocomplete"
                                                indicator="indicatorMotivo"
                                                minimumCharacters="1"
                                                parser="new ResponseXmlToHtmlListParser()" />
                                          </td>-->
                                       </tr>
                                       <tr>
                                          <!--<td>SubMotivo: </td>
                                          <td>
                                             <s:textfield id="editnsubmotivo" name="nsubmotivo" cssClass="cajaMontoTotal" size="30" />
                                             <span id="indicatorSubMotivo" style="display:none;"><img src="./images/indicator.gif" /></span>
                                             <s:textfield id="editidsubmotivo" name="idsubmotivo" cssStyle="display:none"/>

                                             <ajax:autocomplete
                                                source="nsubmotivo"
                                                target="idsubmotivo"
                                                baseUrl="./autocompletar.view?metodo=ObtenerSubmotivo"
                                                className="autocomplete"
                                                indicator="indicatorSubMotivo"
                                                minimumCharacters="1"
                                                parameters="idmotivo={idmotivo}"
                                                parser="new ResponseXmlToHtmlListParser()" />
                                          </td>-->
                                       </tr>
                                       <tr>
                                          <!--<input type="hidden" id="idUpdate">
                                          <td align="center"><img src="images/guardar.bmp" border="0" onClick="updateMotivoSubmotivo()" alt="Actualizar"/></td>
                                          <td align="center"><img src="images/Limpiar.bmp" border="0" onClick="clearEdit()" alt="Limpiar"/></td>-->
                                       </tr>
                                    </table>
                                 </td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Monto de Reclamo</td>
                                 <td colspan="3" align="left" class="label">
                                    <!--<input name="nombre" type="text" class="cajaMontoTotal" value="" size="30">-->
                                    <!--<s:textfield name="monto" cssClass="cajaMontoTotal" size="30" readonly="true"/>-->
                                    <s:property value="monto"/>
                                 </td>
                                 <td></td>
                              </tr>
                              <tr>
                              		<td></td>
                              		<td></td>
                              		<td>
                                       <s:url id="viewexpe" action="doStor_ventanaEditarResumenExpediente" >
                                 		<s:param name="idDocumento"><s:property value="idDocumento"/></s:param>
                                       </s:url>
                                       <s:a href="%{viewexpe}">Ver Resumen Expediente</s:a>
                                 	</td>
                              </tr>
                           </table>
                        </td>
                     </tr>
                  </table>
               </td>
            </tr>
            <tr>
               <td width="99%" colspan="2" align="left">
                  <!--<img src="images/xx.bmp" border="0" onClick="updateExpediente()" alt="Registrar"/>-->
               </td>
            </tr>
         </table>
      </form>
   </body>
</html>
