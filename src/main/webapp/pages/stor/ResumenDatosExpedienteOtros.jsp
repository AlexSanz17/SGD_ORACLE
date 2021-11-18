<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://ajaxtags.org/tags/ajax" prefix="ajax"%>
<%@page import="com.btg.ositran.siged.domain.Documento"%>
<%@page import="com.btg.ositran.siged.domain.Expediente"%>
<%@page import="com.btg.ositran.siged.domain.Documentostor"%>
<html>
   <head>
      <s:head theme="ajax" />
      <title>Tramite Documentario</title>
      <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
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
               <td width="99%" colspan="2" align="left">
                  <!--<img src="images/xx.bmp" onClick="updateExpediente()" alt="Registrar"/>-->
                  <img src="images/regresar.bmp" onClick="window.close()" alt="Cancelar"/>
               </td>
               <td width="1%" align="center">&nbsp;</td>
            </tr>
            <tr>
               <td height="14" colspan="3">
                  <table width="95%" align="center" bgcolor="#BFD9F1" border="0">
                     <tr>
                        <td height="75">
                           <table width="100%" align="center" border="0">
                              <tr>
                                 
                                 <td width="18%" height="5" ></td>
                                 <td width="80%" ></td>
                              </tr>
                              <tr>
                                 
                                 <td height="16" align="left">Referencia</td>
                                 <td colspan="3" align="left" class="label">
                                    <!--<input name="nroexpediente" type="text" class="cajaMontoTotal" value="<s:property value="expediente.getNroexpediente()"/>" size="35" readonly>-->
                                    <s:property value="expediente.getNroexpediente()"/>
                                 </td>
                              </tr>
                              <tr>
                                 
                                 <td height="16" align="left">Proceso</td>
                                 <td colspan="3" class="label">
                                    <!--<input name="proceso" type="text" class="cajaMontoTotal" value="<s:property value="expediente.getProceso().getNombre()"/>" size="40" readonly>-->
                                    <s:property value="expediente.getProceso().getNombre()"/>
                                 </td>
                              </tr>
                              <tr>
                                 <td colspan="6" height="15"><hr></td>
                              </tr>
                              <tr>
                                 <td colspan="6" height="15" class="label">DOCUMENTO</td>
                              </tr>
                              <tr>
                                 
                                 <td height="16" align="left">Tipo Documento</td>
                                 <td align="left" width="17%" class="label">
                                    <!--<input name="tipodocumento" type="text" class="cajaMontoTotal" value="<s:property value="documentoStor.getDocumento().getTipodocumento().getNombre()"/>" size="20" readonly>-->
                                    <s:property value="documentoStor.documento.tipoDocumento.nombre"/>
                                 </td>
                              </tr>
                              <tr>
                                 
                                 <td height="16" align="left">Nro. Documento</td>
                                 <td align="left" width="17%" class="label">
                                    <!--<input name="nrodocumento" type="text" class="cajaMontoTotal" value="<s:property value="documentoStor.getDocumento().getNrodocumento()" />" size="12" readonly>-->
                                    <s:property value="documentoStor.getDocumento().getNrodocumento()" />
                                 </td>
                              </tr>
                              <tr>
                                 
                                 <td height="16" align="left">Asunto</td>
                                 <td colspan="3" align="left" class="label">
                                    <!--<input name="asunto" type="text" class="cajaMontoTotal" value="<s:property value="documentoStor.getDocumento().getAsunto()" />" size="40" readonly>-->
                                    <s:property value="documentoStor.getDocumento().getAsunto()" />
                                 </td>
                              </tr>
                              <tr>
                                 <td colspan="6" height="15"><hr></td>
                              </tr>
                              <tr>
                                 <td colspan="6" height="15" class="label">CLIENTE</td>
                              </tr>
                              <tr>
                                 
                                 <td>Doc Identidad</td>
                                 <td align="left" class="label"  colspan="3">
                                    <s:property value="tipodocidentidad"/>:&nbsp;<s:property value="nrodocidentidad"/>
                                 </td>
                              </tr>
                              <tr>
                                 
                                 <td height="16" align="left">Nombre/Raz&oacute;n Social</td>
                                 <td colspan="3" align="left" class="label">
                                    <!--<input name="nombrecliente" type="text" class="cajaMontoTotal" value="<s:property value="expediente.getCliente().getRazonsocial()" />" size="40" readonly>-->
                                    <!--<s:property value="expediente.getCliente().getRazonsocial()" />-->
                                    <s:if test="expediente.getCliente().getTipoidentificacion().getNombre().equalsIgnoreCase('RUC')">
                                       <!--<s:property value="expediente.getCliente().getRazonsocial()" />-->
                                       <s:property value="expediente.getClienterazonsocial()" />
                                    </s:if>
                                    <s:else>
                                       <!--<s:property value="expediente.getCliente().getNombres()" /> <s:property value="expediente.getCliente().getApellidopaterno()" /> <s:property value="expediente.getCliente().getApellidomaterno()" />-->
                                       <s:property value="expediente.getClientenombres()" /> <s:property value="expediente.getClienteapellidopaterno()" /> <s:property value="expediente.getClienteapellidomaterno()" />
                                    </s:else>
                                 </td>
                                 
                              </tr>
                              <tr>
                                 <td colspan="6" height="15"><hr></td>
                              </tr>
                              <tr>
                                 <td colspan="6" height="15" class="label">CONCESIONARIO</td>
                              </tr>
                              <tr>
                                 
                                 <td height="16" align="left">Nro RUC:</td>
                                 <td colspan="3" align="left" class="label">
                                 <s:property value="expediente.getConcesionario().getRuc()"/>
                                 </td>
                              </tr>
                              <tr>
                                 
                                 <td height="16" align="left">Nombre/Raz&oacute;n Social</td>
                                 <td colspan="3" align="left" class="label">
                                    <!--<input name="nombreconcesionario" type="text" class="cajaMontoTotal" value="<s:property value="expediente.getConcesionario().getRazonsocial()" />" size="30" readonly>-->
                                    <s:property value="expediente.getConcesionario().getRazonsocial()" />
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
                                 
                                 <td height="24" align="left"></td>
                                 <td colspan="3" align="left" class="label">
                                    <!--<s:textfield name="nrosuministro" cssClass="cajaMontoTotal" size="30"  readonly="true"/>-->
                                 <table>
                        <tr>
                           <!--<td>Id Suministro</td>
                           <td>Suministro</td>-->
                        </tr>

                        <s:iterator value="documentoStor.getSuministroList()">
                        <tr>
                           <!--<td>><s:property value="getIdsuministro()"/></td>
                           <td><s:property value="getNrosuministro()"/></td>-->
                           <td>
                              <!--<input type="text" class="cajaMontoTotal" value="<s:property value="getNrosuministro()" />" readonly>-->
                              <s:property value="getNrosuministro()"/>
                           </td>
                        </tr>
                        </s:iterator>
                                 </table>
                                 <div>
                                    <select name="listsuministros" style="display:none" >
                                       <s:iterator value="documentoStor.getSuministroList()">
                                          <option value="<s:property value="getIdsuministro()" />" ></option>
                                       </s:iterator>
                                    </select>
                                 </div>
                                 <td></td>
                              </tr>                              
                              <tr><td colspan="6">Lista Motivos SubMotivos</td></tr>
                              <tr>
                                 
                                 <td height="16" align="left"></td>
                                 <td>
                                    <table border="0">
                                       <s:iterator value="listaMotivos">
                                          <tr>
                                             <td style="display:none;"><input type="text" class="cajaMontoTotal" name="<s:property value="getIdmotivo()" />" value="<s:property value="getIdmotivo()" />"></td>
                                             <td style="display:none;"><input type="text" class="cajaMontoTotal" id="motivox<s:property value="getIdmotivo()" />" name="motivo" value="<s:property value="getDescripcion()" />"></td>

                                          </tr>
                                          <tr>
                                             <td colspan="3">
                                                <table border="0">
                                                   <s:iterator value="documentoStor.getSubmotivoList()">
                                                      <s:if test="getMotivo().getIdmotivo()==idmotivo">
                                                         <tr>
                                                            <td width="100" class="label"><s:property value="getMotivo().getDescripcion()" /></td>
                                                            <td></td>
                                                            <td width="100" class="label"><s:property value="getDescripcion()" /></td>
                                                            <!--<td><img id="<s:property value="getIdsubmotivo()" />"src="images/edit.bmp" border="0" onClick="editarMotivoSubmotivo(this.id)" alt="Editar"/></td>-->
                                                         </tr>
                                                      </s:if>
                                                   </s:iterator>
                                                </table>
                                             </td>
                                          </tr>
                                       </s:iterator>
                                    </table>
                                    
                                 </td>
                                 <td></td>
                                 <td></td>
                              </tr>
                              <tr>
                                 
                                 <td height="16" align="left">Monto de Reclamo S/.</td>
                                 <td colspan="3" align="left" class="label">
                                    <!--<s:textfield name="monto" cssClass="cajaMontoTotal" size="30" readonly="true"/>-->
                                    <s:property value="monto"/>
                                 </td>
                                 <td></td>
                                 <td></td>
                              </tr>
                              	
                              	<td></td>
                              	<td>
                                 	<s:url id="viewexpe" action="doStor_ventanaDatosExpediente" >
                                 		<s:param name="idDocumento"><s:property value="idDocumento"/></s:param>
                                       <s:param name="tipoVentanaDetalle">completo</s:param>
                                    </s:url>
                                    <s:a href="%{viewexpe}">Ver Datos Expediente - Completo</s:a>
                                 </td>
                                 <td></td>
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
               <td colspan="2" align="center">
               </td>
            </tr>
            <tr>
               <td colspan="2" align="left">
                  <img src="images/regresar.bmp" onClick="window.close()" alt="Cancelar"/>
               </td>
            </tr>
         </table>
      </form>
   </body>
</html>
