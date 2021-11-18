<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
   <head>
      <title>Sistema de Control de Asistencia</title>
      <link href="css/estilo.css" rel="stylesheet" type="text/css">
      <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
      <style type="text/css">
         <!--
         body {
            background-color: #ffffff;
         }
         -->
      </style>
      <script type="text/javascript">
         function refrescarBandejaLista() {
            parent.frames["mainFrame"].location.href = "/siged/doLoadListaArray.action";
         }
      </script>
   </head>
   <body onload="refrescarBandejaLista()">
      <table width="100%" border="0">
         <tr>
            <td height="100" align="center" class="label">Para ver una de las listas, s&oacute;lo tienes que pulsar en el</td>
         </tr>
      </table>
   </body>
</html>
