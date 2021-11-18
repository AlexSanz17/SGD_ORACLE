<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@page import="org.ositran.utils.DocumentoDetail" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
   <title>Tramite Documentario</title>
   <link href="css/estilo.css" rel="stylesheet" type="text/css">
   <script language="javascript" src="js/form.js"> </script>
   <script language="Javascript" src="js/general.js"></script>
   <link rel="stylesheet" type="text/css" media="all" href="css/calendar-green.css">
   <script type="text/javascript" src="js/calendar.js"></script>
   <script type="text/javascript" src="js/calendar-es.js"></script>
   <script type="text/javascript" src="js/calendar-setup.js"></script>

   <script language="JavaScript">
      function Abrir_ventana (pagina) {
         var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=800, height=700, top=50, left=200";
         window.open(pagina,"",opciones);
      }
      function selectChange() {
         if (document.frmResoluciones.verSala.value == '1')
         {
            location.href = "observacionAprobarCombo.html";
         }
         if (document.frmResoluciones.verSala.value == '2')
         {
            location.href = "observacionAprobarCombo.html";
         }
         if (document.frmResoluciones.verSala.value == '3')
         {
            location.href = "observacionAprobarCombo.html";
         }
      }

      function anular() {
         document.forms["frmAnular"].submit();
      }

      function refrescar() {
         window.opener.parent.location.href = "/siged/inicio.action";
         this.window.close();
      }

   </script>
   <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"><style type="text/css">
      <!--
      body {
         background-color: #DDEDFF;
      }
      -->
   </style>
</head>

<body <s:if test="bCerrarVentana != null">onload="refrescar();"</s:if>>
   <s:form action="doAnular" method="post" name="frmAnular">
      <table width="103%">
         <tr>
            <td height="14" colspan="2"></td>
         </tr>
         <tr>
            <td height="13" colspan="2" class="header">
               <div align="center">El expediente Nro <s:property value="objExpediente.nroexpediente" /> sera anulado</div>
            </td>
         </tr>
         <tr>
            <td height="13" colspan="2" class="header"></td>
         </tr>
         <tr>
            <td height="14" colspan="2">
               <table width="58%" height="83" border="1" style="border-style:solid" align="center" bordercolor="#669BCD" bgcolor="#BFD9F1">
                  <tr class="altRow2" style="cursor:pointer">
                     <td width="11%" height="9"><center></center></td>
                  </tr>
                  <tr class="altRow2" style="cursor:pointer">
                  <td width="11%" height="68">
                     <table align="center" border="0" width="100%">
                        <tr>
                           <td width="29%">Observaci&oacute;n</td>
                           <td width="71%">
                              <s:textfield value="objExpediente.idexpediente" cssStyle="display:none" />
                              <s:textarea name="objExpediente.observacion" cssClass="cajaMontoTotal" rows="5" cols="50"/>
                           </td>
                        </tr>
                     </table>
                  </td>
               </table>
            </td>
         </tr>
         <tr>
            <td colspan="2"></td>
         </tr>
         <tr>
            <td height="14"  colspan="2"></td>
         </tr>
         <tr>
            <td colspan="2" align="center">
               <input type="button" name="button" value="Anular" class="button" onClick="anular()" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
               <input type="button" name="button2" value="Cancelar" class="button" onClick="window.close()" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            </td>
         </tr>
         <tr align="center">
            <td colspan="2"  align="center">&nbsp;</td>
         </tr>
      </table>
   </s:form>
</body>
</html>
