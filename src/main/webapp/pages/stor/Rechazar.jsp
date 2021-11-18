<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<html>
<head>
  <title>Tramite Documentario</title>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
  <style type="text/css">
     @import "js/dojo/dijit/themes/soria/soria.css";
     @import "css/estilo.css";
  </style>


  

  <script language="javascript" src="js/form.js"> </script>
  <script language="Javascript" src="js/general.js"></script>
<!-- <link rel="stylesheet" type="text/css" media="all"
href="../../css/calendar-green.css">
<script type="text/javascript" src="../../js/calendar.js"></script>
<script type="text/javascript" src="../../js/calendar-es.js"></script>
<script type="text/javascript"
src="../../js/calendar-setup.js"></script> -->
<!-- Begin DOJO -->
<script type="text/javascript" src="js/dojo/dojo/dojo.js" djConfig="parseOnLoad:true, isDebug: false"></script>
<script type="text/javascript">
   dojo.require("dijit.Dialog");
   dojo.require("dijit.ProgressBar");
</script>
<!-- End DOJO -->
<!-- Begin Scrips STOR-->
<script language="JavaScript">
   function rechazarExpedienteStor(){
      var observacion = document.getElementById('observacionrechazar');
      if(observacion.value.length == 0){
         var alerta = document.getElementById('requiredObservacion');
         alerta.style.visibility = "visible";
      }else{
         var aceptar = document.getElementById('botonAceptar');
         aceptar.disabled=true;
         document.forms[0].action = '/siged/doStor_rechazarExpediente.action' ;
         dijit.byId("dlgProgresBar").show();
         document.forms[0].submit();
      }      
   }

   function ocultarEtiqueta(){
      var alerta = document.getElementById('requiredObservacion');
         alerta.style.visibility = "hidden";
   }

   function refrescar() {
      window.opener.parent.location.href ='/siged/inicio.action?sTipoFrame=grid';
      frames["mainFrame"].location.href = "/siged/doBody.action";
      window.close();
   }
</script>
<!-- End Scrips STOR-->

<script language="JavaScript">
function Abrir_ventana (pagina) {
var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=800, height=700, top=50, left=200";
window.open(pagina,"",opciones);
}
  </script>
  

  <style type="text/css">
<!--
body {
background-color: #DDEDFF;
}
-->
  </style>
</head>


<body class="soria" rigthmargin="0" leftmargin="0" topmargin="0"
   <s:if test="refrescar!=null">
            onload="refrescar()"
   </s:if>
>

<form>
    <s:hidden name="idDocumento" />
    <s:hidden name="aprobarcambiosala" />
  <table width="100%">
    <tbody>
      <tr>
        <td colspan="2" height="14"> </td>
      </tr>
      <tr>
        <td colspan="2" class="header" height="13">
        <div align="center">El expediente será rechazado</div>
        </td>
      </tr>
      <tr>
        <td colspan="2" class="header" height="13"></td>
      </tr>
      <tr>
        <td colspan="2" height="14">
        <table style="border-style: solid;" align="center" bgcolor="#bfd9f1" border="1" bordercolor="#669bcd" height="283" width="58%">

          <tbody>

            <tr class="altRow2" style="">

              <td height="9" width="11%">
              <center> Destinatario </center>

              </td>

            </tr>

            <tr class="altRow2" style="">

              <td height="68" width="11%">
              
              <table align="center" border="0">

                <tbody>

                  <tr>

                    <td width="60">Para: </td>

                    <td>
                        <!--<input name="para" class="cajaMontoTotal" value="Francisco Roda" size="40" disabled="disabled" type="text">-->
                        <s:textfield name="destinatario" cssClass="cajaMontoTotal" size="40" readonly="true" disabled="true" />
                    </td>

                  </tr>

                  <tr>

                    <td width="60">Asunto: </td>
                    <td>
                        <!--<input name="asunto" class="cajaMontoTotal" value="Re Carta dirigida a Gerente de Sistemas/Coordinaci&oacute;n" size="80" type="text">-->
                        <s:textfield name="asunto" cssClass="cajaMontoTotal" size="80" readonly="true" disabled="true"/>
                    </td>

                  </tr>

                </tbody>
              </table>

              

              </td>

            </tr>

            <tr class="altRow2" style="">

              <td height="9" width="11%">
              <center>Observaciones</center>

              </td>

            </tr>
            <tr>
               <td align="center" class="textoalerta">
                  <!--<input type="text" id="requiredObservacion" name="requiredObservacion" value="Campo Obligatorio" class="normal">-->
                  <label id="requiredObservacion" style="visibility:hidden">Campo Obligatorio</label>
               </td>
            </tr>

            <tr class="altRow2" style="">

              <td height="59" width="11%">
              <center>
                  <!--<textarea name="comentario" cols="80" rows="12"></textarea>-->
                  <s:textarea name="observacionrechazar" cols="80" rows="12" onkeydown="ocultarEtiqueta()" />
              </center>

              </td>

            </tr>

          </tbody>
        </table>

        </td>

      </tr>

      <tr>

        <td colspan="2"> </td>

      </tr>

      <tr>

        <td colspan="2" height="14"></td>

      </tr>

      <tr>

        <td colspan="2" align="center">
           <input id="botonAceptar" type="button" name="button" value="Aceptar" class="button" onclick="rechazarExpedienteStor()">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
           <input name="button2" value="Cancelar" class="button" onclick="window.close()" type="button">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        </td>
      </tr>

      <tr align="center">

        <td colspan="2" align="center">&nbsp;</td>

      </tr>

    </tbody>
  </table>

</form>
<%@ include file="../util/progressBar.jsp" %>

</body>
</html>
