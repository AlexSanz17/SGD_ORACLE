<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
   <head>
      <title>Tramite Documentario</title>
       <META HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE" >
      <link rel="stylesheet" type="text/css" href="css/estilo.css">

      <script language="JavaScript">
         function Abrir_ventanaBuscar (pagina) {
            var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=760, height=530, top=50, left=200";
            window.open(pagina,"",opciones);
         }

         function Abrir_ventanaCargo (pagina) {
            var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=760, height=530, top=50, left=200";
            window.open(pagina,"",opciones);
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

   <body class="barra" topmargin="0" leftmargin="0" rigthmargin="0">
      <table width="100%">
         <tr>
            <td height="14" colspan="3"></td>
         </tr>
         <tr>
            <td height="20"colspan="3" class="titulo">
               <table width="99%" border="0" height="20" align="center" bgcolor="#A2C0DC">
                  <tr>
                     <td align="left" class="titulo">Expediente Creado</td>
                  </tr>
               </table>
            </td>
         </tr>
         <tr align="center">
            <td width="1%" align="center">&nbsp;</td>
            <td width="99%" colspan="2" align="left">
               <a href="<s:url action="inicio" ><s:param name="sTipoFrame">grid</s:param></s:url>" target="_parent"><img border="0" src="images/aceptar.bmp" alt="Aceptar" /></a>
            </td>
         </tr>
         <tr>
            <td height="14" colspan="3">
               <table width="95%"  border="1" align="center" bordercolor="#669BCD" bgcolor="#BFD9F1">
                  <tr>
                     <td height="75">
                        <table width="98%" height="150" align="center">
                           <tr>
                              <td width="2%" ></td>
                              <td width="18%" height="5" ></td>
                              <td width="17%" ></td>
                              <td width="15%" ></td>
                              <td width="45%" ></td>
                              <td width="3%" ></td>
                           </tr>
                           <tr>
                              <td></td>
                              <td height="12" align="left">Nro Expediente</td>
                              <td colspan="3" align="left" class="label">
                                 <s:property value="objExpediente.nroexpediente" />
                              </td>
                              <td></td>
                           </tr>
                          
                           <tr>
                              <td></td>
                              <td height="12" align="left">Concesionario</td>
                              <td colspan="3" align="left" class="label">
                                 <s:property value="objExpediente.concesionario.razonsocial" />
                              </td>
                              <td></td>
                           </tr>
                           <tr>
                              <td></td>
                              <td height="12" align="left">Area Destino</td>
                              <td colspan="3" align="left" class="label">
                                 <s:property value="objExpediente.proceso.responsable.rol.idunidad.nombre" />
                              </td>
                              <td></td>
                           </tr>
                           <tr>
                              <td></td>
                              <td height="12" align="left">Destinatario</td>
                              <td colspan="3" align="left" class="label">
                                 <s:property value="objExpediente.proceso.responsable.nombres" />
                                 <s:property value="objExpediente.proceso.responsable.apellidos" />
                              </td>
                              <td></td>
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
            <td height="14" colspan="3"></td>
         </tr>
      </table>
   </body>
</html>
