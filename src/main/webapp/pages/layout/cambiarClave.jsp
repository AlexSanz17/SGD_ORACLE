<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Cambiar clave de acceso</title>
		<script type="text/javascript">
			function ccl_cambiarClave(){
				if(dijit.byId("ccl_ClaveAntiguaVal").attr("value") == "" || dijit.byId("ccl_ClaveNueva").attr("value") == "" 
				   || dijit.byId("ccl_ClaveNuevaVal").attr("value") == ""){
					alert("Por favor llene todos los campos");
					return;
				}
				if(dojo.byId("ccl_ClaveAntigua").value != dijit.byId("ccl_ClaveAntiguaVal").attr("value")
				   || dijit.byId("ccl_ClaveNueva").attr("value") != dijit.byId("ccl_ClaveNuevaVal").attr("value")){
					alert("La información proporcionada no es correcta");
					return;
				}
				if((dojo.byId("ccl_ClaveAntigua").value == dijit.byId("ccl_ClaveNueva").attr("value")) 
						&& (dojo.byId("ccl_ClaveAntigua").value == dijit.byId("ccl_ClaveNueva").attr("value"))){
							alert("La contraseña nueva debe ser diferente de la antigua");
							return;
				}				
				dojo.xhrPost({
					form: dojo.byId("ccl_Form"),
					load: function(){
						alert("La contraseña fue cambiada exitosamente");
						ccl_cancelar();
					}
				});
			}
			
			function ccl_cancelar(){
				dijit.byId("dlgCambiarClave").hide();
        		dijit.byId("dlgCambiarClave").destroyRecursive();
			}
		</script>
	</head>
	<body>
		<form id="ccl_Form" action="actualizarClave.action">
			<input id="ccl_ClaveAntigua"  type="hidden" value="<s:property value="#session._USUARIO.claveSiged"/>" />
			<p style="text-align:center;font-weight:bold;">
				CAMBIAR CONTRASEÑA
			</p>
			<table align="center">
				<tr>
					<td style="padding-top: 0.2em;padding-bottom: 0.2em;">Escriba su contraseña actual: </td>
					<td style="padding-top: 0.2em;padding-bottom: 0.2em;"><input id="ccl_ClaveAntiguaVal" dojoType="dijit.form.TextBox" type="password" maxLength=50 required="true"/></td>
				</tr>
				<tr>
					<td style="padding-top: 0.2em;padding-bottom: 0.2em;">Escriba su nueva contraseña: </td>
					<td style="padding-top: 0.2em;padding-bottom: 0.2em;"><input id="ccl_ClaveNueva" name="claveUsuario" dojoType="dijit.form.TextBox" type="password" maxLength=50 required="true"/></td>
				</tr>
				<tr>
					<td style="padding-top: 0.2em;padding-bottom: 0.2em;">Escriba nuevamente su nueva contraseña: </td>
					<td style="padding-top: 0.2em;padding-bottom: 0.2em;"><input id="ccl_ClaveNuevaVal" dojoType="dijit.form.TextBox" type="password" maxLength=50 required="true"/></td>
				</tr>
				<tr>
					<td colspan="2" style="text-align: right;">
						<input dojoType="dijit.form.Button" onClick="ccl_cambiarClave()" showLabel="true" label="Guardar"/>
						<input dojoType="dijit.form.Button" onClick="ccl_cancelar()" showLabel="true" label="Cancelar"/>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>