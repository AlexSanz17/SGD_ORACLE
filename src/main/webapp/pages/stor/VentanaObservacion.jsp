<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<title>Tramite Documentario</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style type="text/css">
    @import "js/dojo/dijit/themes/soria/soria.css";
    @import "css/estilo.css";
</style>
<!-- Begin DOJO -->
<script type="text/javascript" src="js/dojo/dojo/dojo.js" djConfig="parseOnLoad:true, isDebug: false"></script>
<script type="text/javascript">
   dojo.require("dijit.Dialog");
   dojo.require("dijit.ProgressBar");
</script>
<!-- End DOJO -->
<!-- Begin Scrips STOR-->
<script language="JavaScript">

   function aprobarExpediente(){
      /*var observacion = document.getElementById('observacionanular');
      if(observacion.value.length == 0){         
         var alerta = document.getElementById('requiredObservacion');
         alerta.style.visibility = "visible";
      }else{*/
         var aceptar = document.getElementById('botonAceptar');
         aceptar.disabled=true;
         document.forms[0].action = '/siged/doStor_confirmaAprobar.action';
         dijit.byId("dlgProgresBar").show();
         document.forms[0].submit();
      //}
   }

   function ocultarEtiqueta(){
      var alerta = document.getElementById('requiredObservacion');
         alerta.style.visibility = "hidden";
   }

    function refrescar(){
       window.opener.parent.location.href ='/siged/inicio.action?sTipoFrame=grid';
       window.opener.parent.frames["mainFrame"].location.href = "/siged/doBody.action";
       window.close();
    }

</script>
<!-- End Scrips STOR-->



<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"><style type="text/css">
<!--
body {
	background-color: #DDEDFF;
}
-->
</style></head>

<body topmargin="0" leftmargin="0" rigthmargin="0" class="soria">
<form action="" method="post" name="frmResoluciones" id="frmResoluciones">
   <s:hidden name="idDocumento" />
  <table width="103%">
    <tr> 
      <td height="14" colspan="2"> </td>
    </tr>
    <tr> 
      <td height="13" colspan="2" class="header" >
         <div align="center">El <s:property value="nroexpediente" /> será Aprobado</div>
      </td>
    </tr>

    <tr> 
      <td height="13" colspan="2" class="header" ></td>
    </tr>


    <tr> 
      <td height="14" colspan="2">
	  <table width="58%" height="83"  border="1"  style="border-style:solid" align="center" bordercolor="#669BCD" bgcolor="#BFD9F1">
	     <tr class="altRow2" >
           <td width="11%" height="9"><center></center></td>
         </tr>
		 <tr class="altRow2" >
         	<td width="11%" height="68">
		   		<table align="center" border="0" width="100%">

                  <tr>
                     <td align="center" colspan="2" class="textoalerta">
                        <!--<input type="text" id="requiredObservacion" name="requiredObservacion" value="Campo Obligatorio" class="normal">-->
                        <label id="requiredObservacion" style="visibility:hidden">Campo Obligatorio</label>
                     </td>
                  </tr>
                  <tr><td colspan="2" align="center">OBSERVACION</td></tr>
                  <tr>
                     <td colspan="2" align="center">
                        <s:textarea id="observacionaprobar" name="observacionaprobar" cols="40" rows="7"/>
                     </td>
                  </tr>
		   		</table>
		 	</td>
        
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
         <input id="botonAceptar" type="button" name="button" value="Aprobar" class="button" onClick="aprobarExpediente()"   />        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
         <input type="submit" name="button2" value="Cancelar" class="button" onClick="window.close()" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      </td>
</tr>


    <tr align="center"> 
      <td colspan="2"  align="center">&nbsp;</td>
    </tr>
</table>
</form>
<%@ include file="../util/progressBar.jsp" %>
</body>
</html>
