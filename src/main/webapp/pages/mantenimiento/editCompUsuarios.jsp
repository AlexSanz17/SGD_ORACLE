<?xml version="1.0" encoding="UTF-8" ?>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
   <head>
      <title>Tramite Documentario</title>
      <link rel="stylesheet" type="text/css" href="css/estilo.css" />
      <link rel="stylesheet" type="text/css" href="css/transferSelect.css" />
      <s:if test="#session._USUARIO.rol != null">
         <script type="text/javascript" src="js/jquery.js"></script>
      </s:if>
      <script type="text/javascript">
         $(document).ready(function() {
            $("#administradores .der").click(function() {
               $("#administradores .izquierda option:selected").each(function() {
                  $("#administradores .derecha").append($(this).clone(true));
                  $(this).remove();
               });
            });

            $("#administradores .izq").click(function() {
               $("#administradores .derecha option:selected").each(function() {
                  $("#administradores .izquierda").append($(this).clone(true));
                  $(this).remove();
               });
            });

            $("form").submit(function() {
               $("#administradores .derecha option").attr("selected","selected");
            });

            $("#participantes .der").click(function() {
               $("#participantes .izquierda option:selected").each(function() {
                  $("#participantes .derecha").append($(this).clone(true));
                  $(this).remove();
               });
            });

            $("#participantes .izq").click(function() {
               $("#participantes .derecha option:selected").each(function() {
                  $("#participantes .izquierda").append($(this).clone(true));
                  $(this).remove();
               });
            });

            $("form").submit(function() {
               $("#participantes .derecha option").attr("selected","selected");
            });
         });
      </script>
   </head>
   <body class="barra">
      <s:form id="frmLista" name="frmLista" action="doSaveCompartidos" method="POST">
         <table width="100%">
            <tr>
               <td><s:hidden name="iIdUsuario" /></td>
               <td height="16" colspan="4" align="left" class="plomo"></td>
            </tr>
            <tr align="center">
               <td width="1%" align="center">&nbsp;</td>
               <td width="99%" colspan="2" align="left">
                  <s:if test="#session._USUARIO.rol != null">
                     <s:submit src="images/guardar.bmp" type="image" value="Guardar Lista" />
                  </s:if>
                  <s:else>
                     <div dojoType="dijit.form.Button"
                          id="btnGuardarLista"
                          jsId="btnGuardarLista"
                          iconClass="siged16 sigedBack16"
                          label="Guardar"
                          onClick=""
                          showLabel="true"></div>
                  </s:else>
               </td>
            </tr>
            <tr>
               <td></td>
               <td height="16" colspan="4" align="left">
                  <div style="color:red;font-weight:bold;"><s:property value="sMensaje" /></div>
               </td>
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
                                    <s:hidden name="objLista.idlista" />
                                 </td>
                              </tr>
                              <s:if test="#session._USUARIO.rol != null">
                                 <tr>
                                    <td></td>
                                    <td height="16" align="left">Administradores</td>
                                    <td colspan="3" align="left" class="label">
                                       <div id="administradores">
                                          <div class="divIzquierda">
                                             <span>Usuarios Disponibles</span>
                                             <s:select cssClass="izquierda" list="mapAdministradorListaLeft" multiple="true" size="15"></s:select>
                                          </div>
                                          <div class="botones">
                                             <input type="button" class="der" value="-&gt;" />
                                             <input type="button" class="izq" value="&lt;-" />
                                          </div>
                                          <div class="divDerecha">
                                             <span>Administradores</span>
                                             <s:select cssClass="derecha" name="lstAdministradorListaRight" list="mapAdministradorListaRight" multiple="true" size="15"></s:select>
                                          </div>
                                       </div>
                                    </td>
                                    <td></td>
                                 </tr>
                              </s:if>
                              <s:else>
                                 <s:select cssClass="derecha" id="lstAdministradorListaRight" name="lstAdministradorListaRight" list="mapAdministradorListaRight" multiple="true" size="15" cssStyle="display:none;"></s:select>
                              </s:else>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Participantes</td>
                                 <td colspan="3" align="left" class="label">
                                    <div id="participantes">
                                       <div class="divIzquierda">
                                          <span>Usuarios Disponibles</span>
                                          <s:select cssClass="izquierda" list="mapParticipanteListaLeft" multiple="true" size="15"></s:select>
                                       </div>
                                       <div class="botones">
                                          <input type="button" class="der" value="-&gt;" />
                                          <input type="button" class="izq" value="&lt;-" />
                                       </div>
                                       <div class="divDerecha">
                                          <span>Participantes</span>
                                          <s:select cssClass="derecha" id="lstParticipanteListaRight" name="lstParticipanteListaRight" list="mapParticipanteListaRight" multiple="true" size="15"></s:select>
                                       </div>
                                    </div>
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
               <td height="14" colspan="3"></td>
            </tr>
         </table>
      </s:form>
   </body>
</html>
