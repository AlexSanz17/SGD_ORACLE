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
      <s:form name="frmDepartamento" action="doSaveDepartamento" method="POST">
         <table width="100%">
            <tr>
               <td></td>
               <td height="16" colspan="4" align="left" class="plomo"></td>
            </tr>
            <tr align="center">
               <td width="1%" align="center">&nbsp;</td>
               <td width="99%" colspan="2" align="left">
                  <s:submit src="images/guardar.bmp" type="image" value="Guardar Departamento" />
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
                                    <%--Es editable? [<s:property value="iIdDepartamento" />]--%>
                                    <s:hidden name="iIdDepartamento" />
                                 </td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">C&oacute;digo</td>
                                 <td colspan="3" align="left" class="label">
                                    <s:textfield name="objDepartamento.iddepartamento" cssClass="cajaMontoTotal" size="25" />
                                 </td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Nombre</td>
                                 <td colspan="3" class="label">
                                    <s:textfield name="objDepartamento.nombre" cssClass="cajaMontoTotal" size="25" />
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
