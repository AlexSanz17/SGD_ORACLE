<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>Requerimientos</title>
                <style type="text/css">
                    @import "js/dojo/dijit/themes/soria/soria.css";
                    @import "css/estilo.css";
               </style>
		<script type="text/javascript" src="js/dojo/dojo/dojo.js" djConfig="parseOnLoad:true, isDebug: false"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript">
			dojo.require("dijit.form.FilteringSelect" );
			dojo.require("dojo.data.ItemFileReadStore");
			var storeRevTec = new dojo.data.ItemFileReadStore({url: "autocompletarResponsableTecnico.action?idDependencia1="+<s:property value="idsala" />});

			$(document).ready(function(){
				$("form").submit(function(){
					$("#error").hide();
					if($("input:checked").length==0){
						$("#error").text("Debe seleccionar al menos un tipo de notificacion.").show().fadeOut(5000);
						return false;
					}
					else if($("#idrevisortecnico").get(0).value==""){
						$("#error").text("Debe seleccionar un revisor tecnico.").show().fadeOut(5000);
						return false;
					}
					return true;
				});
			});
		</script>				
		
		<style type="text/css">
			body{
				font-size: 10pt;
				color: #555;
			}
			fieldset{
				margin: 10px auto;
				width: 320px;
				padding-top: 10px;
			}
			legend{
				font-weight: bold;
			}
			label span{
				width: 285px;
			}
			label span.revisor{
				width: 100px;
			}
			label{
				display: block;
				height: 25px;
			}
			label *{
				display: block;
				float: left;
			}
			#botones{
				text-align: center;
				margin-top: 10px;
				margin-right: 14px;
			}
			#botones input{
				margin-left: 10px;
			}
		</style>
	</head>
	<body class="soria">
		<form method="post" action="/siged/doStor_enviarNotificaciones.action">
			<fieldset>
				<legend>Enviar Notificaciones</legend>
				<label>
					<span>Requerimiento de Informaci&oacute;n Adicional</span>
					<s:checkbox name="infoAdicional" />
				</label>
				<label>
					<span>Inspecci&oacute;n de Campo</span>
					<s:checkbox name="inspeccionCampo" />
				</label>
				<label>
					<span>Audiencia de Conciliaci&oacute;n</span>
					<s:checkbox name="audienciaConciliacion" />
				</label>
				<!--<label>-->
					<span class="revisor">Revisor T&eacute;cnico</span>
					<select dojoType="dijit.form.FilteringSelect"
							store="storeRevTec"
							idAttr="id"
							labelAttr="label"
							searchAttr="label"
							name="idrevisortecnico"
							id="idrevisortecnico"
							size="80"
							required="true"
							invalidMessage="Seleccione un Revisor Tecnico Válido" />
 				<!--</label>-->
 				
 				<s:hidden name="idDocumento" /> 				
			</fieldset>
                        <div id="botones" align="center" >   
                            <input align="center" id="botonAceptar" type="submit" name="button" value="Aceptar" class="button" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <input align="center" type="button" name="button2" value="Cancelar" class="button" onClick="window.close()" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <!--<input type="button" value="Cancelar" onclick="window.close();" />
                            <input type="submit" value="Aceptar"/>-->
 			</div>
                        
		</form>
		<span id="error" style="display:none;color:red;font-weight:bold;"></span>
	</body>
</html>