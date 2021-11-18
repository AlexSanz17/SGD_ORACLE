<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
   <head>
      <title>SIGED - Vista de Usuario</title>
      <link rel="stylesheet" type="text/css" href="css/estilo.css">
   </head>
   <body class="barra" topmargin="0" leftmargin="0" rigthmargin="0">
      <table width="100%" border="1" bordercolor="#669BCD" bgcolor="#BFD9F1">
         <tr>
            <td>
               <table width="100%">
                  <tr>
                     <td></td>
                     <td class="titulo" colspan="4" height="30">Informaci&oacute;n del Usuario</td>
                     <td></td>
                  </tr>
                  <tr>
                     <td></td>
                     <td height="16" width="30%">Nombres</td>
                     <td colspan="3" class="label" width="70%"><s:property value="objUsuario.nombres" /></td>
                     <td></td>
                  </tr>
                  <tr>
                     <td></td>
                     <td height="16">Apellidos</td>
                     <td colspan="3" class="label"><s:property value="objUsuario.apellidos" /></td>
                     <td></td>
                  </tr>
                  <tr>
                     <td></td>
                     <td height="16">Rol</td>
                     <td colspan="3" class="label"><s:property value="objUsuario.rol.nombre" /></td>
                     <td></td>
                  </tr>
                  <tr>
                     <td></td>
                     <td height="16">&Aacute;rea de Trabajo</td>
                     <td colspan="3" class="label"><s:property value="objUsuario.rol.idunidad.nombre" /></td>
                     <td></td>
                  </tr>
                  <tr>
                     <td></td>
                     <td height="16">Correo</td>
                     <td colspan="3" class="label"><s:property value="objUsuario.correo" /></td>
                     <td></td>
                  </tr>
                  <s:if test="objUsuario.procesoList1 != null && objUsuario.procesoList1.size() > 0">
                     <tr>
                        <td></td>
                        <td class="titulo" colspan="4" height="30">Procesos a su Cargo</td>
                        <td></td>
                     </tr>
                     <s:iterator value="objUsuario.procesoList1">
                        <tr>
                           <td></td>
                           <td colspan="5" class="label"><s:property value="nombre" /></td>
                        </tr>
                     </s:iterator>
                  </s:if>
               </table>
            </td>
         </tr>
      </table>
   </body>
</html>
