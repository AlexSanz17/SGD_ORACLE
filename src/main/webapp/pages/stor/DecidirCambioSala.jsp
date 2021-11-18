<?xml version="1.0" encoding="UTF-8" ?>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://ajaxtags.org/tags/ajax" prefix="ajax"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es">
<head>
<title>Tramite Documentario</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style type="text/css">
   @import "js/dojo/dijit/themes/soria/soria.css";
   @import "css/estilo.css";
</style>

<script type="text/javascript" src="js/dojo/dojo/dojo.js" djConfig="parseOnLoad:true, isDebug: false"></script>

<s:url id="autoUrl" action="autocompletarSala" />

<script type="text/javascript">
   dojo.require("dijit.form.FilteringSelect" );
   dojo.require("dojo.data.ItemFileReadStore");
   dojo.require("dijit.Dialog");
   dojo.require("dijit.ProgressBar");
   var store = new dojo.data.ItemFileReadStore({url: "<s:property value='autoUrl' />"});
</script>

<!--<link href="css/estilo.css" rel="stylesheet" type="text/css">-->


<!-- Begin Scrips STOR-->
<script language="JavaScript">

   function decidirCambioSala(){
      if(validarCamposObligatorios()){
         //alert("enviar al action");
         var aceptar = document.getElementById('botonAceptar');
         aceptar.disabled=true;
         document.forms[0].action = '/siged/doStor_cambioSala.action' ;
         dijit.byId("dlgProgresBar").show();
         document.forms[0].submit();
      }else{
         //alert("Formulario Invalido - No pasar al Action");
      }      
   }
   
   function validarCamposObligatorios(){
      var sala = dijit.byId("idsala");      
      var validarSala;      
      if(sala.isValid() && sala.getValue().length != 0){         
         validarSala = true;
      }else{
         sala.setDisplayedValue("Campo Obligatorio");
         sala.focus();
      }
      return validarSala;      
   }

   function clearCamposObligatorios(idCampo){
      var campoDojo = dijit.byId(idCampo);
      campoDojo.setValue("");
      campoDojo.setDisplayedValue("");
   }

   function refrescar() {
      window.opener.parent.location.href ='/siged/inicio.action?sTipoFrame=grid';
      window.opener.parent.frames["mainFrame"].location.href = "/siged/doBody.action";
      window.close();
   }
</script>
<!-- End Scrips STOR-->

</head>

<body topmargin="0" leftmargin="0" rigthmargin="0" class="soria"
   <s:if test="refrescar!=null">
            onload="refrescar()"
   </s:if>
> 
<form>
   <s:hidden name="idDocumento" />
   <s:hidden name="aprobarcambiosala" />
  <table width="103%">
    <tr> 
      <td height="14" colspan="2"> </td>
    </tr>
    <tr> 
      <td height="13" colspan="2" class="header" ><div align="center">Autorizacion Cambio de Sala</div></td>
    </tr>

    <tr> 
      <td height="13" colspan="2" class="header" ></td>
    </tr>


    <tr> 
      <td height="14" colspan="2">
	  <table width="58%" height="113"  border="1"  style="border-style:solid" align="center" bordercolor="#669BCD" bgcolor="#BFD9F1">
	     <tr class="altRow2" style="cursor:hand">
           <td width="11%" height="9"><center></center></td>
         </tr>
		 <tr class="altRow2" style="cursor:hand">
         	<td width="11%" height="68">
		   		<table align="center" border="0" width="100%">
				   <tr>
					   <td width="136">Sala</td>
					   <td width="303">
                     <!--<input name="para" type="text" class="cajaMontoTotal" value="SU1" size="15" disabled>-->
                     <s:textfield name="salaanterior" cssClass="cajaMontoTotal" size="15" readonly="true" disabled="true" />
                  </td>
		   		</tr>
				   <tr>		   		   
		   		   <td>Nueva Sala</td>
					   <td>
                     <!--<input name="para" type="text" class="cajaMontoTotal" value="" size="15">-->
                     <div dojoType="dijit.form.FilteringSelect"
                        store="store"
                        idAttr="id"
                        labelAttr="label"
                        searchAttr="label"
                        name="idsala"
                        id="idsala"
                        size="80"
                        required="true"></div>

                  </td>		   		   
				   </tr>
					<tr>
                  <td>Observacion</td>
					   <td>
                     <!--<input name="para" type="text" class="cajaMontoTotal" value="" size="40">-->
                     <s:textfield name="observacioncambiosala" cssClass="cajaMontoTotal" size="40" />
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
         <input id="botonAceptar" type="button" name="button" value="Aceptar" class="button" onClick="decidirCambioSala()"   />        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
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