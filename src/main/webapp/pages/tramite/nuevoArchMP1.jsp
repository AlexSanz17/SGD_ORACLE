<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Tramite Documentario</title>
<!-- Dojo vive en el SIGED!!! - Inicio -->
<style type="text/css">
@import "js/dojo/dijit/themes/soria/soria.css";

@import "js/dojo/dojox/grid/resources/soriaGrid.css";

@import "css/grid.css";
</style>

<script type="text/javascript" src="js/dojo/dojo/dojo.js"
	djConfig="parseOnLoad:true, isDebug: false"></script>
<script type="text/javascript" src="js/dojo/dijit/dijit.js"
	djConfig="parseOnLoad:true, isDebug: false"></script>

<s:url id="autoUrl" action="autocompletarProceso" />

<script type="text/javascript">
	dojo.require("dijit.form.FilteringSelect");
	dojo.require("dojo.data.ItemFileReadStore");
	dojo.require("dojo.rpc.JsonService");

        var store = new dojo.data.ItemFileReadStore({url: "<s:property value='autoUrl' />"});
</script>

<script>
	function Abrir_ventanaBuscar(pagina) {
		var opciones = "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=760, height=530, top=50, left=200";
		window.open(pagina, "", opciones);
	}

	function pasar(i) {
		document.location.href = "/siged/doLoadExpediente.action?iIdExp=" + i;
	}

	function submitForm() {
          if(!dijit.byId("idproceso").state && dijit.byId("idproceso").value!=""){
			document.forms["frmNewDoc"].submit();
		} else {
			alert("Debe seleccionar un proceso");
		}
	}
	function regresar() {
		history.back();
	}
</script>
<!--
      <script type="text/javascript" src="js/siged/filteringselect.js"></script> 
      Dojo vive en el SIGED!!! - Fin -->

<link rel="stylesheet" type="text/css" href="css/estilo.css">

</head>

<body class="soria" topmargin="0" leftmargin="0" rigthmargin="0">
<s:form name="frmNewDoc" action="doNewDoc" method="POST">

	<table>
		<tr>
			<td width="29%" align="left"><font color="669BCD">Bienvenido
			</font><font color="0099FF"><s:property value="#session.nombres" /></font>
			</td>
		</tr>
		<tr>
			<td><font color="0099FF"><a
				href="<s:url action="doLogout" />" target="_parent">Cerrar
			Sesi&oacute;n</a></font></td>
		</tr>
	</table>
	<table width="100%">
		<tr>
			<td height="14" colspan="3"></td>
		</tr>
		<tr>
			<td height="20" colspan="3" class="titulo">
			<table width="99%" border="0" height="20" align="center"
				bgcolor="#A2C0DC">
				<tr>
					<td align="left" class="titulo">Nuevo Documento</td>
				</tr>
			</table>
			</td>
		</tr>
		<tr align="center">
			<td width="1%" align="center">&nbsp;</td>
			<td width="99%" colspan="2" align="left"><img
				src="images//regresar.bmp" border="0" onClick="regresar()"
alt="Regresar" /> <img src="images/xx.bmp" border="0"
				onClick="javascript:Abrir_ventanaCargo()" alt="Registrar" /></td>
		</tr>
		<tr>
			<td height="14" colspan="3">
			<table width="95%" border="1" align="center" bordercolor="#669BCD"
				bgcolor="#BFD9F1">
				<tr>
					<td height="75">
					<table width="98%" height="97" align="center">
						<tr>
							<td width="2%"></td>
							<td width="18%" height="5"></td>
							<td width="17%"></td>
							<td width="15%"></td>
							<td width="45%"></td>
							<td width="3%"></td>
						</tr>
						<tr>
							<td></td>
							<td class="titulo">Datos del Expediente</td>
							<td colspan="4"></td>
						</tr>
						<tr>
							<td></td>
							<td align="left">Nro Expediente</td>
							<td colspan="3" class="label">&nbsp;<s:textfield
								name="objDD.strReferencia" cssClass="dijitReset" size="20" /> <img
								onClick="javascript:Abrir_ventanaBuscar('doLoadSearchExpediente.action')"
src="images/53.png" height="13" width="23" /></td>
							<td></td>
						</tr>
						<tr>
							<td></td>
							<td align="left">Proceso</td>
							<td colspan="3" class="label">
							<div dojoType="dijit.form.FilteringSelect" store="store"
								idAttr="id" labelAttr="label" searchAttr="label"
								name="idproceso" id="idproceso" size="80"
								invalidMessage="Seleccione un proceso" required="true"
								onChange="submitForm" />
							</td>
							<td></td>
						</tr>
						<tr>
							<td></td>
							<td class="titulo">&nbsp;</td>
							<td colspan="4"></td>
						</tr>
					</table>
					</td>
				</tr>
			</table>
			</td>
		</tr>
		<tr>
			<td height="14" colspan="3"></td>
		</tr>
	</table>
</s:form>
</body>
</html>
