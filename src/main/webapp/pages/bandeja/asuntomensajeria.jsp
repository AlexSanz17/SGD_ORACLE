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
     	 alert("El Documento Fue Ingreso Correctamente a Mensajeria");
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
 <s:form name="FrmIngmen" action="doingMensajeria" method="POST" >
 <input type="hidden" name="Idcoc" value="<s:property value='objdocusu.iddocumento'/>"/>
 <input type="hidden" name="Idusuario" value="<s:property value='objdocusu.idusuario'/>"/>
      <table width="103%">
         <tr>
            <td height="14" colspan="2">
               
            </td>
         </tr>
         <tr>
            <td height="13" colspan="2" class="header" >
               <div align="center">Ingrese el Asunto para Mensajeria </div>
            </td>
         </tr>
         <tr>
            <td height="13" colspan="2" class="header" >  </td>
         </tr>
         <tr>
            <td height="14" colspan="2"><div align="center"><input type="text" name="asunto" class="ajaMontoTotal"/> </div></td>
         </tr>
         <tr>
            <td colspan="2">	  </td>
         </tr>
         <tr>
            <td height="14"  colspan="2">  </td>
         </tr>
         <tr>
            <td colspan="2" align="center">
               <input type="button" name="button" value="Guardar" class="button" onClick="javascript:ejecAprob()" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
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
