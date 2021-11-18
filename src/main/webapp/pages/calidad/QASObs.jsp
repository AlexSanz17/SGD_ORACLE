<?xml version="1.0" encoding="UTF-8" ?>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es">
<head>
<title>Rechazar Documento</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/estilo.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
			function rechazar() {
				document.forms["frmQASObs"].submit();
			}

			function refrescar() {
				self.parent.opener.parent.location.href = "inicio.action?sTipoFrame=grid";
				this.window.close();
			}

			function reestablecer(){
				opener.activarBotones();
			}
		</script>
</head>
<body <s:if test="cerrar!=null">onload="refrescar()"</s:if>
	onunload="reestablecer();">
<s:form name="frmQASObs" action="doRechazarQAS" method="post">
	<table width="103%">
		<tr>
			<td height="14" colspan="2"><s:textfield
				name="objDD.iIdDocumento" cssStyle="display:none" /></td>
		</tr>
		<tr>
			<td height="13" colspan="2" class="header">
			<div align="center">El expediente <s:property
				value='objDD.strReferencia' /> ser&aacute; rechazado</div>
			</td>
		</tr>
		<tr>
			<td height="13" colspan="2" class="header"></td>
		</tr>
		<tr>
			<td height="14" colspan="2">
			<table height="148" border="1" style="border-style: solid"
				align="center" bordercolor="#669BCD" bgcolor="#BFD9F1">
				<tr class="altRow2">
					<td height="15">Observaciones</td>
				</tr>
				<tr class="altRow2">
					<td height="59"><s:textarea name="objDD.sObservacionRechazo"
						cssClass="cajaMontoTotal" rows="10" cols="70" /></td>
				</tr>
			</table>
			</td>
		</tr>
		<tr>
			<td colspan="2"></td>
		</tr>
		<tr>
			<td height="14" colspan="2"></td>
		</tr>
		<tr>
			<td colspan="2" align="center"><input type="button"
				name="button" value="Aceptar" class="button" onclick="rechazar();" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" name="button2" value="Cancelar" class="button"
				onclick="window.close();" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		</tr>
		<tr align="center">
			<td colspan="2" align="center">&nbsp;</td>
		</tr>
	</table>
</s:form>
</body>
</html>