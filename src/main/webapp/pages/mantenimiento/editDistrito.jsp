<?xml version="1.0" encoding="UTF-8" ?>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
   <head>
      <title>Tramite Documentario</title>
      <link rel="stylesheet" type="text/css" href="css/estilo.css" />
   </head>
   <body class="barra">
      <s:form name="frmDistrito" action="doSaveDistrito" method="POST">
         <table width="100%">
            <tr>
               <td></td>
               <td height="16" colspan="4" align="left" class="plomo"></td>
            </tr>
            <tr align="center">
               <td width="1%" align="center">&nbsp;</td>
               <td width="99%" colspan="2" align="left">
                  <s:submit src="images/guardar.bmp" type="image" value="Guardar Distrito" />
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
                                    <%--Es editable? [<s:property value="iIdDistrito" />]--%>
                                    <s:hidden name="iIdDistrito" />
                                 </td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Provincia</td>
                                 <td colspan="3" align="left" class="label">
                                    <s:select cssClass="cajaMontoTotal" name="objDistrito.provincia.idprovincia" headerKey="0" headerValue="-- Seleccione una Provincia --" list="mapProvincia" value="objDistrito.provincia.idprovincia" />
                                 </td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">C&oacute;digo</td>
                                 <td colspan="3" align="left" class="label">
                                    <s:textfield name="objDistrito.iddistrito" cssClass="cajaMontoTotal" size="25" />
                                 </td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Nombre</td>
                                 <td colspan="3" class="label">
                                    <s:textfield name="objDistrito.nombre" cssClass="cajaMontoTotal" size="25" />
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
