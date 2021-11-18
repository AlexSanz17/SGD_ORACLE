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

<script type="text/javascript" src="js/dojo/dojo/dojo.js" djConfig="parseOnLoad:true, isDebug: false"></script>
<script type="text/javascript" src="js/dojo/dijit/dijit.js" djConfig="parseOnLoad:true, isDebug: false"></script>

<!-- Begin Scrips STOR-->
<script type="text/javascript">
   dojo.require("dijit.form.FilteringSelect" );
   dojo.require("dojo.data.ItemFileReadStore");
   dojo.require("dojo.parser");
   dojo.require("dijit.Dialog");
   dojo.require("dijit.ProgressBar");

   var storeSala = new dojo.data.ItemFileReadStore({url: "autocompletarSala.action"});
</script>

<script language="JavaScript">

   function calificarExpediente(){
      var aceptar = document.getElementById('botonAceptar');
      aceptar.disabled=true;
      document.forms[0].action = '/siged/doStor_calificarAnalisisExterno.action' ;
      dijit.byId("dlgProgresBar").show();
      document.forms[0].submit();
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
</style>
</head>

<body topmargin="0" leftmargin="0" rigthmargin="0" class="soria"
   <s:if test="refrescar!=null">
      onload="refrescar()"
   </s:if>
> 
<form action="" method="post" name="frmResoluciones" id="frmResoluciones">
  <s:hidden name="idDocumento" />
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
	  <table width="58%" height="83"  border="1"  style="border-style:solid" align="center" bordercolor="#669BCD" bgcolor="#BFD9F1">
	     <tr class="altRow2" style="">
           <td width="11%" height="9"><center></center></td>
         </tr>
		 <tr class="altRow2" style="">
         	<td width="11%" height="68">
		   		<table align="center" border="0" width="100%">				    		   		   
		   		   <tr>
					   <td>Sala</td>

					   <td>
                     <select dojoType="dijit.form.FilteringSelect"
                        store="storeSala"
                        idAttr="id"
                        labelAttr="label"
                        searchAttr="label"
                        name="idsala"
                        id="idsala"
                        value="<s:property value="idsala" />"
                        invalidMessage="Seleccione una Sala Válida"
                        required="true"
                     />
                  </td>
		   		   </tr>
                   <tr>
					   <td>Tipo de Analisis</td>
					   <td>
                     <!--<input name="para" type="text" class="cajaMontoTotal" value="" size="15">-->
                     <s:textfield name="tipoanalisis" cssClass="cajaMontoTotal" size="15" disabled="true" readonly="true"/>
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
         <input id="botonAceptar" type="button" name="button" value="Aceptar" class="button" onClick="calificarExpediente()"   />        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
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
