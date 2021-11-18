<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://ajaxtags.org/tags/ajax" prefix="ajax"%>

<html>
<head>
<title>Tramite Documentario</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style type="text/css">
    @import "js/dojo/dijit/themes/soria/soria.css";
    @import "css/estilo.css";
</style>
<script language="javascript" src="js/form.js"> </script>
<script language="Javascript" src="js/general.js"></script>
<link rel="stylesheet" type="text/css" media="all" href="css/calendar-green.css">
<script type="text/javascript" src="js/calendar.js"></script>
<script type="text/javascript" src="js/calendar-es.js"></script>
<script type="text/javascript" src="js/calendar-setup.js"></script>
<!-- Begin DOJO -->
<script type="text/javascript" src="js/dojo/dojo/dojo.js" djConfig="parseOnLoad:true, isDebug: false"></script>
<script type="text/javascript">
   dojo.require("dijit.Dialog");
   dojo.require("dijit.ProgressBar");
</script>
<!-- End DOJO -->
<!-- Begin Scrips STOR-->
<script language="JavaScript">

   function vbResolucionST(){
      var aceptar = document.getElementById('botonAceptar');
      aceptar.disabled=true;
      document.forms[0].action = '/siged/doStor_vbResolucionST.action';
      dijit.byId("dlgProgresBar").show();
      document.forms[0].submit();
   }

   function refrescar(){
      window.opener.parent.location.href ='/siged/inicio.action?sTipoFrame=grid';
      window.opener.parent.frames["mainFrame"].location.href = "/siged/doBody.action";
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

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"><style type="text/css">
<!--
body {
	background-color: #DDEDFF;
}
-->
</style></head>

<body topmargin="0" leftmargin="0" rigthmargin="0" class="soria"
   <s:if test="refrescar!=null">
      onload="refrescar()"
   </s:if>
> 
<form>
    <s:hidden name="idDocumento" />
    <s:hidden name="cumplerequisitosst" value="si" />
  <table width="103%">
    <tr> 
      <td height="14" colspan="2"> </td>
    </tr>
    <tr> 
      <td height="13" colspan="2" class="header" ><div align="center">El expediente 
          será aprobado</div></td>
    </tr>

    <tr> 
      <td height="13" colspan="2" class="header" ></td>
    </tr>


    <tr> 
      <td height="14" colspan="2">
	  <table width="78%" height="123"  border="1"  style="border-style:solid" align="center" bordercolor="#669BCD" bgcolor="#BFD9F1">
	     <tr class="altRow2" style="">
           <td width="11%" height="9"><center></center></td>
         </tr>
		 <tr class="altRow2" style="">
         	<td width="11%" height="68">
		   		<table align="center" border="0" width="100%">
				   <tr>
                      <!--
					   <td width="220">Fecha de Notificaci&oacute;n a reclamante</td>
					   <td width="219"><input class="cajaMontoTotal" name="date" type="text" id="campo_fecha1" size="14" maxlength="20">
                 <input alt="Calendario" class="cajaFecha" id="lanzador1" value="..." type="button">
                       </td>
                       -->
		   		   </tr>
				    <tr>
                       <!--
					   <td width="220">Fecha de Notificaci&oacute;n a Concesionario</td>
					   <td width="219"><input class="cajaMontoTotal" name="date" type="text" id="campo_fecha2" size="14" maxlength="20">
                 <input alt="Calendario" class="cajaFecha" id="lanzador2" value="..." type="button">
                       </td>
                       -->
		   		   </tr>
                   <!--
                   <tr>
					   <td width="220">Fecha de Cumplimiento de Resoluci&oacute;n</td>
					   <td width="219"><input class="cajaMontoTotal" name="date" type="text" id="campo_fecha3" size="14" maxlength="20">
                 <input alt="Calendario" class="cajaFecha" id="lanzador3" value="..." type="button">
                       </td>
		   		   </tr
                   -->
				   <tr>
					   <td width="253">
                           <s:hidden name="idsala" />
                           Derivaci&oacute;n
                       </td>
                       
					   <td width="347">Archivo &nbsp;&nbsp;<input name="derivarvbrst" type="radio" value="archivar" checked>
					   Verificaci&oacute;n &nbsp;&nbsp;<input name="derivarvbrst" type="radio" value="verificarcumplimiento">
                       </td>
		   		   </tr>   		   		  
                  <tr><td colspan="2" align="center">OBSERVACION</td></tr>
                  <tr>
                     <td colspan="2" align="center">
                        <s:textarea id="observacionaprobar" name="observacionaprobar" cols="40" rows="5"/>
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
      <input id="botonAceptar" type="button" name="button" value="Aceptar" class="button" onClick="vbResolucionST()"   />        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <input type="button" name="button2" value="Cancelar" class="button" onClick="window.close()" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
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
