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
      function ejecAprob(){

     	 document.forms["FrmIngmen"].submit();
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

<body topmargin="0" leftmargin="0" rigthmargin="0" >

        <table width="103%">
         <tr>
            <td height="14" colspan="2">
               
            </td>
         </tr>
         <tr>
            <td height="13" colspan="2" class="header" >
               <div align="center">Solicitar al Supervisor</div>
            </td>
         </tr>
         <tr>
            <td height="13" colspan="2" class="header" >  </td>
         </tr>
         <tr>
            <td height="14" colspan="2">
<!--            <input type="text" name="asunto" class="ajaMontoTotal"/> </div></td>-->
         </tr>
         <tr>
            <td colspan="2">	  </td>
         </tr>
         <tr>
            <td height="14"  colspan="2">  </td>
         </tr>
         <tr>
            <td colspan="2" align="center">
               El Documento fue solicitado al Supervisor
            </td>
         </tr>
         <tr align="center">
            <td colspan="2"  align="center">&nbsp;</td>
         </tr>
      </table>
 </body>
</html>