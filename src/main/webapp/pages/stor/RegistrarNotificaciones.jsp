<?xml version="1.0" encoding="UTF-8" ?>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es">
	<head>
		<title>Registrar fechas de notificaciones</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<style type="text/css">
			@import "js/dojo/dijit/themes/soria/soria.css";
			@import "css/estilo.css";
		</style>
		<script type="text/javascript">
			var djConfig = {
				isDebug: false,
	            parseOnLoad: true
	         };
		</script>
		<script type="text/javascript" src="js/dojo/dojo/dojo.js"></script>
		<script type="text/javascript" src="js/dojo/dijit/dijit.js"></script>
		<script type="text/javascript">
			dojo.require("dijit.form.Form");
			dojo.require("dojo.parser");
			dojo.require("dijit.form.DateTextBox");
			dojo.require("dijit.form.ValidationTextBox");
			dojo.require("dijit.form.NumberSpinner");
			dojo.require("dijit.Dialog");
			dojo.require("dijit.ProgressBar");
		</script>
		<script type="text/javascript">
		function registrarNotificaciones(){
			var fecha1=dijit.byId("fnconcesionario").getValue();
			var fecha2=dijit.byId("fnusuario").getValue();
			if((fecha1!=null && fecha1!="") || (fecha2!=null && fecha2!="")){
				var form = dijit.byId("formRegistrarResolucion");
				if(form.validate()){
					var fnconcesionario = dijit.byId("fnconcesionario");
					var verificacion = "<s:property value="expediente.getExpedientestor().getRequiereverificacion().toString()"/>";
					if(verificacion=="S"){
						var plazo = dijit.byId("plazo");
						if(plazo.toString().length!=0 && fnconcesionario.toString().length==0){
							fnconcesionario.attr('promptMessage','Dato Requerido');
							fnconcesionario.focus();
						}else if(fnconcesionario.toString().length!=0 && plazo.toString().length==0){
							plazo.attr('promptMessage','Dato Requerido');
							plazo.focus();
						}else{
							var aceptar = document.getElementById('botonAceptar');
							aceptar.disabled=true;
							dijit.byId("dlgProgresBar").show();
							form.submit();
						}
					}else{
						var aceptar = document.getElementById('botonAceptar');
						aceptar.disabled=true;
						dijit.byId("dlgProgresBar").show();
						form.submit();
					}
				}
  			}
			else{
				alert("Debe seleccionar al menos una fecha");
			}      
		}

   function refrescar(){
      window.opener.parent.location.href ='/siged/inicio.action?sTipoFrame=grid';
      window.opener.parent.frames["mainFrame"].location.href = "/siged/doBody.action";
      window.close();
   }
		</script>
		<style type="text/css">
			body {
				background-color: #DDEDFF;
			}
		</style>
	</head>
	<body class="soria"
   <s:if test="refrescar!=null">
      onload="refrescar()"
   </s:if>
>
<div dojoType="dijit.form.Form" id="formRegistrarResolucion" action="doStor_registrarNotificaciones.action" method="POST">
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
	  <table width="78%" height="123"  border="1"  style="border-style:solid" align="center" bordercolor="#669BCD" bgcolor="#BFD9F1">
	     <tr class="altRow2" style="">
           <td width="11%" height="9"></td>
         </tr>
		 <tr class="altRow2" style="">
         	<td width="11%" height="68">
		   		<table align="center" border="0" width="100%">
				   <tr>
					   <td width="50%">Fecha de Notificaci&oacute;n a reclamante</td>
					   <td width="50%">
                          <!--<input class="cajaMontoTotal" name="date" type="text" id="campo_fecha1" size="14" maxlength="20">-->
                          <!--<s:textfield id="fnusuario" name="fnusuario" cssClass="cajaMontoTotal" size="14" maxLength="20"/>
                          <input alt="Calendario" class="cajaFecha" id="selectfnusuario" value="..." type="button">-->
                          <input type="text" id="fnusuario" name="fnusuario"
                                 dojoType="dijit.form.DateTextBox"
                                 promptMessage="dd/MM/aaaa"
                                 required="false"
                                 constraints="{min:'<s:property value="fechaaprobacion"/>'}"
                                 size="25"
                                 invalidMessage="Ingresar una Fecha Valida"
                                 value="<s:property value="fnusuario"/>"
                          >
                       </td>
		   		   </tr>
				    <tr>
					   <td width="50%">Fecha de Notificaci&oacute;n a Concesionario</td>
					   <td width="50%">
                          <!--<input class="cajaMontoTotal" name="date" type="text" id="campo_fecha2" size="14" maxlength="20">-->
                          <!--<s:textfield id="fnconcesionario" name="fnconcesionario" cssClass="cajaMontoTotal" size="14" maxLength="20"/>
                          <input alt="Calendario" class="cajaFecha" id="selectfnconcesionario" value="..." type="button">-->
                          <input type="text" id="fnconcesionario" name="fnconcesionario"
                                 dojoType="dijit.form.DateTextBox"
                                 promptMessage="dd/MM/aaaa"
                                 required="false"
                                 constraints="{min:'<s:property value="fechaaprobacion"/>'}"
                                 size="25"
                                 invalidMessage="Ingresar una Fecha Valida"
                                 value="<s:property value="fnconcesionario"/>"
                          >
                       </td>
		   		   </tr>
                  <s:if test="expediente.getExpedientestor().getRequiereverificacion().toString().equalsIgnoreCase('S')">
                  <tr>
					   <!--<td width="220">Fecha de Cumplimiento de Resoluci&oacute;n</td>
					   <td width="219">
                          <input class="cajaMontoTotal" name="date" type="text" id="campo_fecha3" size="14" maxlength="20">
                          <input alt="Calendario" class="cajaFecha" id="lanzador3" value="..." type="button">
                       </td>-->

                  <td width="50%">Plazo para el Cumplimiento de Resoluci&oacute;n</td>
					   <td width="50%">
                          <!--<input class="cajaMontoTotal" name="date" type="text" id="campo_fecha3" size="14" maxlength="20">-->
                          <!--<s:textfield id="plazo" name="plazo" cssClass="cajaMontoTotal" size="15" maxLength="20"/>-->                          
                          <input 
                                 dojoType="dijit.form.NumberSpinner"
                                 name="plazo"
                                 id="plazo"
                                 value="<s:property value="plazo"/>"
                                 constraints="{min:1,max:99}"
                                 maxlength="2"
                                 required="false"
                                 invalidMessage="Ingresar un Plazo Valido"                                 
                                 class="cajaMontoTotal"
                          >
                       </td>
		   		   </tr>
                  </s:if>
				   <!--<tr>
					   <td width="253">Derivaci&oacute;n</td>
					   <td width="347">Archivo &nbsp;&nbsp;<input name="deriva" type="radio" value="">
					   Verificaci&oacute;n &nbsp;&nbsp;<input name="deriva" type="radio" value="">
                       </td>
		   		   </tr>-->
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
         <input id="botonAceptar" type="button" name="button" value="Aceptar" class="button" onclick="registrarNotificaciones()"   />        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
         <input type="button" name="button2" value="Cancelar" class="button" onclick="window.close()" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
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
