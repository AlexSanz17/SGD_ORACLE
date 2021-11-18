<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<title>Tramite Documentario</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style type="text/css">
         @import "js/dojo/dijit/themes/soria/soria.css";
         @import "js/dojo/dojox/grid/resources/soriaGrid.css";
         @import "css/grid.css";
         @import "css/estilo.css";
</style>
   
<script type="text/javascript" src="js/dojo/dojo/dojo.js" djConfig="parseOnLoad:true, isDebug: false"></script>
<script type="text/javascript" src="js/dojo/dijit/dijit.js" djConfig="parseOnLoad:true, isDebug: false"></script>

<script type="text/javascript">
   dojo.require("dojo.parser");
   dojo.require("dijit.form.ValidationTextBox");
   dojo.require("dijit.form.Button");
   dojo.require("dijit.form.Form");
   dojo.require("dijit.Dialog");
   dojo.require("dijit.ProgressBar");
</script>

<!-- Begin Scrips STOR-->
<script language="JavaScript">
   function cambioSala(){
      var form = dijit.byId("formCambioSala");
      if(form.validate()){
         
         var aceptar = document.getElementById('botonAceptar');
         aceptar.disabled=true;
         //form.action = 'doStor_cambioSala.action';
         //alert(form.action);
         dijit.byId("dlgProgresBar").show();
         form.submit();
      }else {
         //alert("Form Invalido");
      }
   }

   function refrescar() {
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

<body class="soria" topmargin="1" leftmargin="1" rigthmargin="1"
   <s:if test="refrescar!=null">
            onload="refrescar()"
   </s:if>
> 

<div dojoType="dijit.form.Form" id="formCambioSala" action="doStor_cambioSala.action" method="POST">
   <s:hidden name="idDocumento" />
  <table width="103%">
    <tr> 
      <td height="14" colspan="2"> </td>
    </tr>
    <tr> 
      <td height="13" colspan="2" class="header" ><div align="center">Solicitar Cambio Sala</div></td>
    </tr>

    <tr> 
      <td height="13" colspan="2" class="header" ></td>
    </tr>


    <tr> 
      <td height="14" colspan="2">
	  <table width="40%" height="113"  border="1"  style="border-style:solid" align="center" bordercolor="#669BCD" bgcolor="#BFD9F1">
	     <tr class="altRow2" style="cursor:hand">
           <td width="11%" height="9"><center></center></td>
         </tr>
		 <tr class="altRow2" style="cursor:hand">
         	<td width="11%" height="68">
		   		<table align="center" border="0" width="100%">
				   <tr>
					   <td width="136">Sala</td>
					   <td width="700">
                     <!--<input name="para" type="text" class="cajaMontoTotal" value="SU1" size="7" disabled>-->
                     <s:textfield id="sala" name="sala" cssClass="cajaMontoTotal" size="7" readonly="true" disabled="true" />
                  </td>
		   		</tr>				   
				   <tr>
					   <td>Motivo</td>
					   <td class="label">
                     <input type="text"
                        dojoType="dijit.form.ValidationTextBox"
                        name="motivocambiosala"
                        id="motivocambiosala"                        
                        required="true"                        
                        invalidMessage="Debe Ingresar un Motivo"
                        style="width:200px; height:18px;"
                        class="cajaMontoTotal"
                     >                     
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
         <input id="botonAceptar" type="button" name="button" value="Aceptar" class="button" onClick="cambioSala()"/>        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
         <input type="button" name="button2" value="Cancelar" class="button" onClick="window.close()" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;         
      </td>
</tr>


    <tr align="center"> 
      <td colspan="2"  align="center">&nbsp;</td>
    </tr>
</table>
</div>
<%@ include file="../util/progressBar.jsp" %>
</body>
</html>
