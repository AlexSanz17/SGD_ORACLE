<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
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
      function rechazar(){
         document.forms["frmObservacion"].submit();
         this.window.close();
      }
      function Abrir_ventana (pagina) {
         var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=800, height=700, top=50, left=200";
         window.open(pagina,"",opciones);
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

<body topmargin="0" leftmargin="0" rigthmargin="0" >
   <s:form name="frmObservacion" action="doRechazarUSER" method="POST">
      <table width="103%">
         <tr>
            <td height="14" colspan="2">
               <s:textfield name="objDD.iIdDocumento" cssStyle="display:none" />
            </td>
         </tr>
         <tr>
            <td height="13" colspan="2" class="header" >
               <div align="center">El expediente <s:property value='objDD.strReferencia'/> sera rechazado</div>
            </td>
         </tr>
         <tr>
            <td height="13" colspan="2" class="header" ></td>
         </tr>
         <tr>
            <td height="14" colspan="2">
               <table height="148"  border="1"  style="border-style:solid" align="center" bordercolor="#669BCD" bgcolor="#BFD9F1">
                  <tr class="altRow2" style="cursor:hand">
                     <td height="15">Observaciones</td>
                  </tr>
                  <tr class="altRow2" style="cursor:hand">
                     <td height="59">
                        <s:textarea name="objDD.strObservacion" cssClass="cajaMontoTotal" rows="10" cols="70"/>
                     </td>
                  </tr>
               </table>
            </td>
         </tr>
         <tr>
            <td colspan="2">	  </td>
         </tr>
         <tr>
            <td height="14"  colspan="2"></td>
         </tr>
         <tr>
            <td colspan="2" align="center">
               <input type="button" name="button" value="Aceptar" class="button" onClick="javascript:rechazar()" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
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
