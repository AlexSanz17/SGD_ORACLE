<?xml version="1.0" encoding="UTF-8" ?>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es">
	<head>
		<s:url id="autoUrl" action="autocompletarProceso1">
			<s:param name="sTipoDerivacion" value="sTipoDerivacion" />
		</s:url>
		<title>Responder sin documento</title>
      <link type="text/css" rel="stylesheet" href="css/styleSiged.css" />
      <link type="text/css" rel="stylesheet" href="js/dojo/css/styleDojo.css" />
		<style type="text/css">
         div.falso {
            position: absolute;
            /*top: -48px;*/
            left: 80px;
            z-index: 0;
            /*width: 14px;
            height: 3px;*/
         }

         input.file {
            position: relative;            
            filter:alpha(opacity: 0);
            opacity: 0;
            z-index: 1;
            /*top: -49px; 
            text-align: left;
            left: 80px;*/
            width: 50px;
            left: 10px;
         }
         .copia{
         	margin-right: 5px;
         }
		</style>
		<script type="text/javascript">
			var djConfig = {
				isDebug: false,
				parseOnLoad: true
			};
		</script>
		<script type="text/javascript" src="js/dojo/dojo/dojo.js"></script>
		<script type="text/javascript" src="js/siged/upload.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/siged/siged.js"></script>
      	<script type="text/javascript" src="js/siged/siged.forms.js"></script>
		<script type="text/javascript" src="js/siged/siged.string.js"></script>
      	
		<script type="text/javascript">
		dojo.require("dijit.Editor");
	        dojo.require("dijit._editor.plugins.FontChoice");  
	        dojo.require("dijit._editor.plugins.TextColor");  
	        dojo.require("dijit._editor.plugins.LinkDialog");
	        dojo.require("dojo.io.iframe");
	        dojo.require("dojo.rpc.JsonService");
	        dojo.require("dijit.form.TextBox");
	        dojo.require("dijit.form.Button");	
			
         	var enviando=false;
         	
			function enviar(){
				if(!enviando){
					enviando=true;
					document.getElementById("objDD.strTexto").value = dijit.byId("contenid").getValue(false);
					dijit.byId("dlgProgresBar").show();
					document.getElementById("enviar").submit();
				}
			}
			
			function refrescar() {
	        	try {
					window.opener.filtrarSeguimiento(); 
	          	}catch(err){ 
	     			this.window.close();
	           	}
				if (provieneDeMail == "true") {
	               	window.opener.close();
	            }
	           	this.window.close();
	     	}
		</script>
	</head>
	<body class="soria" <s:if test='cerrar!=null'>onload="refrescar();"</s:if>>
		<s:if test="cerrar==null">
			<table width="100%">
				<tr>
					<td height="14" colspan="6" style='padding:6px 8px;font-weight:bold'><s:property value="fechaEnTexto"/></td>
				</tr>
				<tr align="center">
					<td width="1%" align="center"></td>
					<td colspan="4" align="left">
						<img src="images/enviar.bmp" alt="Enviar" style="float: left;" onclick="javascript:enviar();" />
				</tr>
				<tr>
					<td height="16" colspan="5" align="left" class="plomo">
						<form id="enviar" action="finalizarApoyo.action" method="post">
							<s:hidden name="iIdDoc" />
							<s:hidden name="sOpcion" />
	                  		<s:hidden name="objDD.iIdRemitente" />
							<table width="100%">
								<tr>
									<td></td>
									<td width="27%" height="16" align="left" class="plomo">Para : </td>
									<td colspan="2">
	                              		<input type="hidden" id="iddestinatario" name="iddestinatario" value="<s:property value='iddestinatario' />" />
	                              		<input type="text" dojoType="dijit.form.TextBox" value="<s:property value='sNombres' />" readonly="true" />
									</td>
								</tr>
								<tr>
									<td></td>
									<td height="16" align="left" class="plomo">Asunto : </td>
									<td colspan="4">
										<input type="text" dojoType="dijit.form.TextBox" id="asunto" name="objDD.strAsunto" style="width:400px;" maxlength="300" value="<s:property value='objDD.strAsunto'/>" readonly/>
									</td>
								</tr>
								<tr><td>&nbsp;</td></tr>
								<tr>
									<td>&nbsp;</td>
									<td colspan="5" class="normal">
										<s:hidden id="objDD.strTexto" name="objDD.strTexto" />
	                          			<input type="text" name="ta" id="ta" value="<s:property value='ta' escape='false' />" style="display:none;">
										<div dojoType="dijit.Editor" jsid="contenid"  id="contenid" style="background-color: #FFF;" >
										</div>
										<script type="text/javascript">
										   var cadena  = document.getElementById("ta").value;
										   cadena = cadena.replace(/<P><\/P>/g,"");
										   document.getElementById("contenid").innerHTML = cadena.substring(0,3999);
										</script> 
									</td>
								</tr>
							</table>
						</form>
					</td>
				</tr>
				<tr>
					<td height="14" colspan="6"></td>
				</tr>
			</table>
		</s:if>
		<s:else>
			<p style="text-align: center;">Operaci&oacute;n realizada satisfactoriamente</p>
		</s:else>
		<%@ include file="../pages/util/progressBar.jsp"%>
	</body>
</html>