<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Cargo</title>
		<style type="text/css">
			body{
				font-family: arial;
			}
		</style>
	</head>
	<body onload='window.print();'">
		<table align="center" style="width:98%;">
			<tr>
				<td colspan="2" style="font-size: 1em; text-align: center;">
					x ORGANISMO SUPERVISOR DE LA INVERSI&Oacute;N EN INFRAESTRUCTURA DE TRANSPORTE DE USO P&Uacute;BLICO
				</td>
			</tr>
			<tr>
				<td colspan="2" style="font-size: 0.8em; text-align: center;">
					CARGO DE RECEPCI&Oacute;N - TR&Aacute;MITE DOCUMENTARIO
				</td>
			</tr>
			<tr>
				<td colspan="2" style="font-size: 0.6em; text-align: center; padding-bottom: 1em;">
					La recepci&oacute;n del presente documento no significa <br/>
					la conformidad o aprobaci&oacute;n del mismo
				</td>
			</tr>
			<tr>
				<td colspan="2">
				<table style="width:98%;" align="center">
					<tr>
						<td style="font-size: 0.7em; width:70%;">
							<strong>Fecha de ingreso: </strong> <label style="font-size:1.3em;"><s:date name="cargo.fechacreacion" format="dd/MM/yyyy" /></label>
						</td>
						<td style="font-size: 0.7em; width:30%;">
							<strong>Hora: </strong> <label style="font-size:1.3em;"><s:date name="cargo.fechacreacion" format="HH:mm" /></label>
						</td>
					</tr>
				</table>
				</td>
			</tr>
			<tr>
				<td style="font-size: 0.7em;">
					<strong>N&uacute;mero de Tr&aacute;mite: </strong>
				</td>
				<td style="font-size: 2em;">
					<s:property value="cargo.nroTramite" />
				</td>
			</tr>
			<tr>
				<td style="font-size: 0.7em;">
					<strong>Tipo de Documento: </strong>
				</td>
				<td style="font-size: 0.7em;">
					<s:property value="cargo.tipodocumento" />
				</td>
			</tr>
			<tr>
				<td style="font-size: 0.7em;">
					<strong>N&uacute;mero de Documento: </strong>
				</td>
				<td style="font-size: 0.7em;">
					<s:property value="cargo.nrodocumento" />
				</td>
			</tr>
			
			<tr>
				<td style="font-size: 0.7em;">
					<strong>Asunto: </strong>
				</td>
				<td style="font-size: 0.7em;">
					<s:property value="cargo.asunto" />
				</td>
			</tr>
			<tr>
				<td style="font-size: 0.7em;">
					<strong>Observaciones:</strong>
				</td>
				<td style="font-size: 0.7em;">
					<s:property value="cargo.observacion" />
				</td>
			</tr>
			<tr>
				<td colspan="2" style="font-size: 0.7em; text-align: center; padding-top: 1em;">
					Conservar este documento para cualquier atenci&oacute;n 
					o consulta, indicando el n&uacute;mero de tr&aacute;mite.
				</td>
			</tr>
			<tr>
				<td colspan="2" style="font-size: 0.65em; text-align: center;">
				Calle Los Negocios N° 182, Piso 2 Urb. Limatambo - Surquillo
				</td>
			</tr>
			<tr>
				<td colspan="2" style="font-size: 0.65em; text-align: center;">
				https://www.ositran.gob.pe
				</td>
			</tr>
		</table>
	</body>
</html>