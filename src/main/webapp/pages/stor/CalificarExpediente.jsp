<?xml version="1.0" encoding="UTF-8" ?>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es">
	<head>      
		<title>Calificar Expediente</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link href="css/estilo.css" rel="stylesheet" type="text/css" />
		<style type="text/css">
			@import "js/dojo/dijit/themes/soria/soria.css";
		</style>
		<script type="text/javascript" src="js/dojo/dojo/dojo.js" djConfig="parseOnLoad:true, isDebug: false"></script>
		<s:url id="autoUrl" action="autocompletarSala" />
		<script type="text/javascript">
      	dojo.require("dijit.form.FilteringSelect" );
      	dojo.require("dojo.data.ItemFileReadStore");
        dojo.require("dojo.parser");
        dojo.require("dijit.Dialog");
        dojo.require("dijit.ProgressBar");

         var store = new dojo.data.ItemFileReadStore({url: "<s:property value='autoUrl' />"});

         var tipoAnalisisItems = {
            identifier: 'id',
            label: 'label',
            items: [
               {id: 'interno', label: 'Interno'},
               {id: 'externo', label: 'Externo'}
            ]
         };
         var storeTipoAnalisis = new dojo.data.ItemFileReadStore({data: tipoAnalisisItems});
		</script>		
		<script type="text/javascript">
         function calificarExpediente(){
            if(validarCamposObligatorios()){               
               var aceptar = document.getElementById('botonAceptar');
               aceptar.disabled=true;
               document.forms[0].action = '/siged/doStor_calificarExpediente.action' ;               
               dijit.byId("dlgProgresBar").show();
               document.forms[0].submit();               
               
            }else{
               //alert("Formulario Invalido - No pasar al Action");
            }
            
         }
         /*function refrescar(){
            window.opener.parent.location.href ='/siged/inicio.action?sTipoFrame=grid';
            window.opener.parent.frames["mainFrame"].location.href = "/siged/doBody.action";
            window.close();
         }*/

         function validarCamposObligatorios(){
            var sala = dijit.byId("idsala");
            var tipoAnalisis = dijit.byId("idtipoanalisis");
            var validarSala;
            var validarTipoAnalisis;

            if(tipoAnalisis.isValid() && tipoAnalisis.getValue().length != 0){
               //alert("Ok Seleccion TipoAnalisis");
               validarTipoAnalisis = true;
            }else {
               //alert("demo");
               tipoAnalisis.setDisplayedValue("Campo Obligatorio");
               tipoAnalisis.focus();             
            }

            if(sala.isValid() && sala.getValue().length != 0){
               //alert("Ok Seleccion Sala");
               validarSala = true;
            }else{               
               sala.setDisplayedValue("Campo Obligatorio");
               sala.focus();               
            }

            return (validarSala && validarTipoAnalisis);
         }

         function clearCamposObligatorios(idCampo){
            alert("clear campos");
            var campoDojo = dijit.byId(idCampo);
            campoDojo.setValue("");
            campoDojo.setDisplayedValue("");
            campoDojo.focus();            
         }

		</script>        
	</head>
	<body class="soria">
		<s:form action="doStor_calificarExpediente.action" method="post" name="frmResoluciones" id="frmResoluciones">
			<s:hidden name="idDocumento" />
			<table width="103%">
				<tr>
					<td height="14" colspan="2"> </td>
				</tr>
				<tr>
					<td height="13" colspan="2" class="header">
						<div align="center">El expediente ser&aacute; aprobado</div>
					</td>
				</tr>
				<tr>
					<td height="13" colspan="2" class="header"></td>
				</tr>
				<tr>
					<td height="14" colspan="2">
						<table width="58%" height="83"  border="1"  style="border-style:solid" align="center" bordercolor="#669BCD" bgcolor="#BFD9F1">
							<tr class="altRow2">
								<td width="11%" height="9"></td>
							</tr>
							<tr class="altRow2" style="">
								<td width="11%" height="68">
									<table align="center" border="0" width="100%">
										<tr align="center">
											<td>Sala</td>
											<td>
												<select dojoType="dijit.form.FilteringSelect"
													store="store"
													idAttr="id"
													labelAttr="label"
													searchAttr="label"
													name="idsala"
													id="idsala"
													size="80"
													invalidMessage="Seleccione una Sala V&aacute;lida"
													required="true"></select>
											</td>
										</tr>
										<tr align="center">
											<td>Tipo de An&aacute;lisis</td>
											<td>
												<select
													dojoType="dijit.form.FilteringSelect"
													store="storeTipoAnalisis"
													idAttr="id"
													labelAttr="label"
													searchAttr="label"
													name="tipoanalisis"
													id="idtipoanalisis"
													invalidMessage="Seleccione Tipo Análisis Válido"
													required="true"></select>
											</td>
										</tr>                           
										<tr>
											<td colspan="2" align="center">Observaci&oacute;n</td>
										</tr>
										<tr>                              
											<td colspan="2" align="center">
												<s:textarea id="observacionaprobar" name="observacionaprobar" cols="40" rows="7"/>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td colspan="2"></td>
				</tr>
				<tr>
					<td height="14"  colspan="2"></td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<input id="botonAceptar" type="button" name="button" value="Aceptar" class="button" onclick="calificarExpediente()" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" name="button2" value="Cancelar" class="button" onClick="window.close()" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
				</tr>
				<tr align="center">
					<td colspan="2"  align="center">&nbsp;</td>
				</tr>
			</table>
		</s:form>
		<%@ include file="../util/progressBar.jsp" %>
	</body>
</html>