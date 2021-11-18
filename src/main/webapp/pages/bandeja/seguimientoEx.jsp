<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
   <head>
      <title>Tramite Documentario</title>
      <link rel="stylesheet" type="text/css" href="css/estilo.css">
      <script language="JavaScript"> 
         function Abrir_ventana (pagina) {
            var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=800, height=700, top=50, left=200";
            window.open(pagina,"",opciones);
         }

         function Abrir_pagina (pagina) {
            var opciones="location=mainFrame";
            window.open(pagina,"",opciones);
         }

         function regresar(){
            window.close()
         }
      </script>
   </head>
   <body topmargin="0" leftmargin="0" rigthmargin="0" >
      <table width="90%" align="center">
         <tr>
            <td height="14" colspan="2"></td>
         </tr>
         <tr>
            <td height="13" colspan="2" class="header">
               <div align="center">
                  Trazabilidad del Expediente Nro: <s:property value="objDocumento.expediente.nroexpediente" />
               </div>
            </td>
         </tr>
         <tr>          
            <td height="13" colspan="2" class="header" ></td>
         </tr>
         <tr>
            <td colspan="2">
               
               <table cellpadding="1" cellspacing="1" class="tableForm" align="center">
                  <tr class="header">
                     <td  class="data">Fecha Env&iacute;o</td>
                     <td  class="data">De</td>
                     <td  class="data">Para</td>
                     <td  width="15%" class="data">Proceso</td>
                     <td  class="data">Etapa</td>                    
                     <td  class="data">Acci&oacute;n</td>
                     <td  class="data"></td>
                  </tr>
                  <s:if test="lstTrazabilidadExpediente.size() > 0">
                     <s:iterator value="lstTrazabilidadExpediente">
                        <tr  class="altRow2" > 
                           <td  align="left"><s:date name="fechacreacion" format="dd/MM/yyyy HH:mm" />&nbsp;</td>
                           <td  align="left"><s:property value="remitente.nombres" /> <s:property value="remitente.apellidos" />&nbsp;&nbsp;</td>
                           <td  align="left"><s:property value="destinatario.nombres" /> <s:property value="destinatario.apellidos" />&nbsp;&nbsp;</td>
                          <td  align="left"><s:property value="idproceso.nombre" />&nbsp;&nbsp;</td>
                          <td  align="left"><s:property value="etapa.descripcion" />&nbsp;&nbsp;</td>
                           <td  align="left"><s:property value="accion.descripcion" />&nbsp;&nbsp;</td>
                           <s:if test="accion.nombre.equals('derivar')">
                              <td  align="left"><img src="images/aprobarrr.gif" border="0" alt=""/>&nbsp;&nbsp;</td>
                           </s:if>
                           <s:elseif test="accion.nombre.equals('registrar')">
                              <td  align="left"><img src="images/aprobarrr.gif" border="0" alt=""/>&nbsp;&nbsp;</td>
                           </s:elseif>
                           <s:elseif test="accion.nombre.equals('aprobar')">
                              <td  align="left"><img src="images/aprobarrr.gif" border="0" alt=""/>&nbsp;&nbsp;</td>
                           </s:elseif>
                           <s:elseif test="accion.nombre.equals('rechazar')">
                              <td  align="left"><img src="images/desaprobar.gif" border="0" alt=""/>&nbsp;&nbsp;</td>
                           </s:elseif>
                           <s:elseif test="accion.nombre.equals('pendiente')">
                              <td  align="left"><img src="images/pendiente.gif" border="0" alt=""/>&nbsp;&nbsp;</td>
                           </s:elseif>
                           <s:elseif test="accion.nombre.equals('reformulacion')">
                              <td  align="left"><img src="images/desaprobar.gif" border="0" alt=""/>&nbsp;&nbsp;</td>
                           </s:elseif>
                           <s:elseif test="accion.nombre.equals('solicitar cambio sala')">
                              <td  align="left"><img src="images/desaprobar.gif" border="0" alt=""/>&nbsp;&nbsp;</td>
                           </s:elseif>
                           <s:else>
                              <td  align="left"></td>
                           </s:else>
                        </tr>
                     </s:iterator>
                  </s:if> 
               </table>
                
            </td>
         </tr>
         <tr align="center">
            <td colspan="2"  align="center">&nbsp;</td>
         </tr>
         <tr>
            <td colspan="2" align="center">
               <input type="button" name="selecciona" value="Regresar" class="button" onClick="regresar()"/>  &nbsp;&nbsp;
            </td>
         </tr>
      </table>
   </body>
</html>
     