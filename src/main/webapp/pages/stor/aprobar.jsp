<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
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
      <!--Begin Script STOR-->
      <script type="text/javascript">
         function jsRefrescarBandeja() {
            parent.frames["mainFrame"].location.href = "/siged/doBody.action";            
            window.close();
         }

         function refrescar(){            
            //window.opener.parent.location.href ='/siged/inicio.action?sTipoFrame=grid';
            window.opener.showGridInbox(0);
            //window.opener.parent.frames["mainFrame"].location.href = "/siged/doBody.action";            
            window.close();
         }

         function abrirVentanaAlerta(){
            var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=no, width=650, height=320, top=50, left=200";
            window.open("/siged/pages/stor/aprobar.jsp","",opciones);
         }

      </script>
      <!--End Script STOR-->
   </head>
   <body
      <s:if test="refrescar!=null">
            onload="refrescar()"
      </s:if>
      <s:else>
         onload="jsRefrescarBandeja()"
      </s:else>

   >
      <table width="100%" border="0">
         <tr>
            <td height="100" align="center" class="label">
               El expediente <s:property value='objDD.strReferencia'/> se aprob&oacute; satisfactoriamente
               <%//<s:label cssStyle="cajaMontoTotal" name="valormensaje" />%>

            </td>
         </tr>
         <tr>
            <td align="center">
               <%//<s:if test="tipomensaje.equalsIgnoreCase('alerta')">%>
                  <!--<input type="submit"  name="button2" value="Cancelar" class="button" onClick="window.close()" />-->
               <%//</s:if>%>
            </td>
         </tr>
      </table>
   </body>
</html>
