<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Trazabilidad</title>
		<style type="text/css">
			table.tableForm{
				border-collapse:collapse;
			}
			td.filaRespuesta{
				border-bottom: 0.02em solid #000000;
			}
			td.filaRespuestaCent{
				border-bottom: 0.02em solid #000000;
				
				text-align:center;
			}
			td.filaCabecera{
				border-bottom: 0.02em solid #000000;
				border-left: 0.02em solid #000000;
				border-right: 0.02em solid #000000;
				border-top: 0.02em solid #000000;
				
				text-align:center;
			}
			body{
				font-family:verdana;
				font-size:12px;
				padding-left: 0.5em;
			}
			p.titulo{
				text-align:center;
				font-weight:bold;
			}
		</style>
	</head>
	<body>
		<p class="titulo">Trazabilidad del Documento <s:property value="objDocumento.numeroDocumento" /></p>
		<p>
			<strong>Expediente:</strong> <s:property value="objDocumento.expediente.nroexpediente" /> <br/>
			<strong>Asunto:</strong> <s:property value="objDocumento.asunto" /> <br/>
			<strong>Fecha de Creaci&oacute;n:</strong> <s:date name="objDocumento.fechaCreacion" format="dd/MM/yyyy" /> <br/>
			<s:if test="fechaLimite!=null">
				<strong>Fecha l&iacute;mite de Atenci&oacute;n:</strong> <s:property value="fechaEnTexto" />
			</s:if>		
		</p>
		<table align="center" style="width:95%;" class="tableForm" border="0">
			<tr class="header">
                <td class="filaCabecera" style="width:12em;">Fecha Env&iacute;o</td>
                <td class="filaCabecera">De</td>
                <td class="filaCabecera">Para</td>
                <s:if test="mostrarEtapa != null && mostrarEtapa == true">
                    <td class="filaCabecera" width="18%">Etapa</td>
                </s:if>
                <s:if test="mostrarActividad != null && mostrarActividad == true">
                    <td class="filaCabecera" width="18%">Actividad</td>
                </s:if>
                <td class="filaCabecera" style="width:7em;">Acci&oacute;n</td>
                <td class="filaCabecera" style="width:7em;">Estado</td>
                <%--<td width="3%"></td> --%>
            </tr>
            <s:if test="trazabilidadUnificada.size() > 0">
				<s:iterator value="trazabilidadUnificada">
					<tr class="altRow2">
						<td align="center"><s:date name="fechaCreacion" format="dd/MM/yyyy HH:mm" /></td>
						<td align="center"><s:property value="remitente" escape="false"/></td>
						<td align="center"><s:property value="destinatario" escape="false"/></td>
						<td align="center"><s:property value="accion" /></td>
						<td align="center"><s:property value="estado" /></td>
					</tr>
				</s:iterator>
            </s:if>
		</table>
		<script type="text/javascript">
			window.print();
		</script>
	</body>
</html>