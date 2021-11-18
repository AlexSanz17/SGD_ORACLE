<?xml version="1.0" encoding="UTF-8" ?>
<?xml version="1.0" encoding="UTF-8"?>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es">
   <head>
      <title>Tramite Documentario</title>
      <script type="text/javascript">
         var showCargo = function() {
            var id = window.open("","","width=600,height=500");
            id.location.href = "generateCargo.action?idDocumento=<s:property value='objDD.iIdDocumento' />";
            /*if (dojo.isIE) {
               console.info("Trabajando con Internet Explorer");
               var id = window.open("","","width=600,height=500");
               id.location.href = "generateCargo.action?idDocumento=<s:property value='objDD.iIdDocumento' />";
            } else {
               console.info("Trabajando con Firefox");
               window.location.href = "generateCargo.action?idDocumento=<s:property value='objDD.iIdDocumento' />";
            }*/
         };
      </script>
   </head>
   <body>
      <table width="100%">
         <tr align="center" class="nover">
            <td width="1%" align="center">&nbsp;</td>
            <td width="99%" colspan="2" align="left">
               <button dojoType="dijit.form.Button"
                       type="button"
                       iconClass="siged16 sigedSave16"
                       onClick="showCargo"
                       showLabel="true">Imprimir</button>
            </td>
         </tr>
         <tr>
            <td height="50" colspan="3">
               <table width="100%"  border="1" align="center" bordercolor="#669BCD" bgcolor="#ffffff">
                  <tr>
                     <td>

                        <div id="uno" class="ver">
                           <table width="100%" height="100" align="center">
                              <tr>
                                 <td width="2%"></td>
                                 <td width="10%" height="5"></td>
                                 <td width="1%"></td>
                                 <td width="4%"></td>
                                 <td width="1%"></td>
                                 <td width="82%"></td>
                              </tr>
                              <tr class="nover">
                                 <td width="2%"></td>
                                 <td height="16" colspan="5" align="left" class="asunto"><s:property value="objDD.strAsunto" /></td>
                              </tr>
                              <tr>
                                 <td height="23" colspan="6"></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" colspan="4" align="left" class="plomo">Nro Expediente</td>
                                 <td><s:property value="objDD.strReferencia" /></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" colspan="4" align="left" class="plomo">Proceso</td>
                                 <td><s:property value="objDD.strProceso" /></td>
                              </tr>
                              <s:if test="!objDD.strProceso.startsWith('OSINERGMIN')">
                                 <tr>
                                    <td></td>
                                    <td height="16" colspan="4" align="left" class="plomo">Area Destino</td>
                                    <td><s:property value="objDD.strUnidad" /></td>
                                 </tr>
                              </s:if>
                              <tr>
                                 <td></td>
                                 <td height="16" colspan="4" align="left" class="plomo">Destinatario</td>
                                 <td><s:property value="objDD.strResponsable" /></td>
                              </tr>
                              <tr>
                                 <td colspan="6" height="15"><hr></td>
                              </tr>
                              <tr>
                                 <td colspan="6" height="15" class="label">DOCUMENTO</td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" colspan="4" align="left" class="plomo">Tipo de Documento</td>
                                 <td><s:property value="objDD.strTipoDocumento" /></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" colspan="4" align="left" class="plomo">Nro Documento</td>
                                 <td><s:property value="objDD.strNroDocumento" /></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" colspan="4" align="left" class="plomo">Nro Folios</td>
                                 <td><s:property value="objDD.iNroFolios" /></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" colspan="4" align="left" class="plomo">Fecha Documento</td>
                                 <td><s:property value="objDD.strFechaDocumento" /></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" colspan="4" align="left" class="plomo">Observaciones</td>
                                 <td><s:property value="objDD.strObservacion" /></td>
                              </tr>
                              <tr>
                                 <td colspan="6" height="15"><hr></td>
                              </tr>
                              <tr>
                                 <td colspan="6" height="15" class="label">CLIENTE</td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" colspan="4" align="left" class="plomo">Tipo Doc Identidad</td>
                                 <td><s:property value="objDD.strTipoIdentificacion" /></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" colspan="4" align="left" class="plomo">Nro. Doc Identidad</td>
                                 <td><s:property value="objDD.strNroIdentificacion" /></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" colspan="4" align="left" class="plomo">Nombre/Razón Social</td>
                                 <td><s:property value="objDD.strRazonSocial" /></td>
                              </tr>
                              <s:if test="objDD.strTipoIdentificacion.equals('RUC')">
                                 <tr>
                                    <td></td>
                                    <td height="16" colspan="4" align="left" class="plomo">Representante Legal</td>
                                    <td><s:property value="documento.expediente.cliente.representantelegal" /></td>
                                 </tr>
                              </s:if>
                              <tr>
                                 <td></td>
                                 <td height="16" colspan="4" align="left" class="plomo">Direccion</td>
                                 <td><s:property value="objDD.strDireccionPrincipal" /></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" colspan="4" align="left" class="plomo">Otra Direccion</td>
                                 <td><s:property value="objDD.strDireccionAlternativa" /></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" colspan="4" align="left" class="plomo">Teléfono</td>
                                 <td><s:property value="objDD.strTelefonoCliente" /></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" colspan="4" align="left" class="plomo">Correo Electrónico</td>
                                 <td><s:property value="objDD.strCorreoCliente" /></td>
                              </tr>
                              <s:if test="objDD.strAbreviado == 'stor'">
                                 <tr>
                                    <td colspan="6" height="15"><hr></td>
                                 </tr>
                                 <tr>
                                    <td colspan="6" height="15" class="label">CONCESIONARIO</td>
                                 </tr>
                                 <tr>
                                    <td></td>
                                    <td height="16" colspan="4" align="left" class="plomo">RUC</td>
                                    <td><s:property value="objDD.strRUC" /></td>
                                 </tr>
                                 <tr>
                                    <td></td>
                                    <td height="16" colspan="4" align="left" class="plomo">Razón Social</td>
                                    <td><s:property value="objDD.strCorrentista" /></td>
                                 </tr>
                                 <tr>
                                    <td></td>
                                    <td height="16" colspan="4" align="left" class="plomo">Dirección</td>
                                    <td><s:property value="objDD.strDireccionConcesionario" /></td>
                                 </tr>
                                 <tr>
                                    <td></td>
                                    <td height="16" colspan="4" align="left" class="plomo">Correo Electrónico</td>
                                    <td><s:property value="objDD.strCorreoConcesionario" /></td>
                                 </tr>
                              </s:if>
                              <tr>
                                 <td></td>
                                 <td colspan="5">&nbsp;</td>
                              </tr>
                           </table>
                        </div>
                     </td>
                  </tr>
               </table>
            </td>
         </tr>
         <tr>
            <td height="14" colspan="3"></td>
         </tr>
      </table>
   </body>
</html>
