<?xml version="1.0" encoding="UTF-8"?>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es">
<head>
<title>Trazabilidad del Expediente</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="pragma" content="no-cache">
	<link rel="stylesheet" type="text/css" href="css/estilo.css" />
	<script type="text/javascript" src="js/form.js"> </script>
	<script type="text/javascript" src="js/general.js"></script>
	<link rel="stylesheet" type="text/css" media="all"
		href="css/calendar-green.css" />
	<script type="text/javascript" src="js/calendar.js"></script>
	<script type="text/javascript" src="js/calendar-es.js"></script>
	<script type="text/javascript" src="js/calendar-setup.js"></script>

	<script type="text/javascript">
            function regresar(){
                window.close();
            }
            
            function imprimirTraza(){
            	var opciones = "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=no, width=600, height=400, top=50, left=120";
            	var pagina = "verTrazabilidadExpediente.action?mode=imprimir&iIdDocumento=" + <s:property value='objDocumento.idDocumento' />;
            	window.open(pagina, "", opciones);
            }
        </script>
</head>
<body>
	<table width="100%">
		<tr align="center">
			<td colspan="2" align="center">&nbsp;</td>
		</tr>
		<tr height="18%">
			<td valign="center" align="center"
				style="FONT-WEIGHT: bold; FONT-SIZE: 10pt; COLOR: #31619c; FONT-FAMILY: verdana, arial, helvetica, sans-serif">

				Trazabilidad del Expediente <s:property value="objDocumento.expediente.nroexpediente" /></td>
		</tr>
	</table>

	<table cellpadding="1" cellspacing="1" class="tableForm" width="90%"
		align="center">
		<tr class="header">
			<td class="data" width="20%">Nro. Documento</td>
			<td width="15%" class="data">Fecha Env&iacute;o</td>
			<td width="20%" class="data">De</td>
			<td width="20%" class="data">Para</td>
			<td width="15%" class="data">Acci&oacute;n</td>
			<td class="data" width="10%">Estado</td>
		</tr>
		<s:if test="lstTrazabilidadDocumento.size() > 0">
			<s:iterator value="lstTrazabilidadDocumento">
				<tr class="altRow2"
					<s:if test="conCopias">
                        	style="color:blue;"
                        </s:if>
					<s:if test="conApoyo">
                        	style="color:red;"
                        </s:if>>
					
                    <td align="center"><s:property value="documento.tipoDocumento.nombre"/> - <s:property value="documento.numeroDocumento"/></td>
					<td align="center"><s:date name="fechacreacion" format="dd/MM/yyyy HH:mm" />
					</td>
					<td align="center"><s:property value="remitente.nombres" /> <s:property
							value="remitente.apellidos" /> Área:<s:property
							value="remitente.unidad.nombre" /></td>
					<td align="center"><s:if test="destinatario != NULL">
							<s:property value="destinatario.nombres" />
							<s:property value="destinatario.apellidos" /> 
                        		Área:<s:property
								value="destinatario.unidad.nombre" />
						</s:if> <s:else>
                        	-
                        	</s:else></td>
					<td align="center"><s:property value="accion.descripcion" />
					</td>
					<td align="center"><s:property value="estado" />
					</td>
				</tr>
			</s:iterator>
		</s:if>
	</table>
	<%
        	String sinRol = request.getParameter("sinRol");
            if (sinRol == null) {
            	sinRol = "0";
			}
        %>
	<%
        	if(sinRol.equals("0")) {
        %>
	<table width="100%">
		<tr align="center">
			<td colspan="2" align="center">&nbsp;</td>
		</tr>
		<tr>
			<td colspan="2" align="center"><input type="button"
				name="selecciona" value="Regresar" class="button"
				onclick="regresar();" /> &nbsp;&nbsp;<input type="button"
				value="Imprimir" class="button" onclick="imprimirTraza();" /></td>
		</tr>
		<tr align="center">
			<td colspan="2" align="center">&nbsp;</td>
		</tr>
	</table>
	<%
            }
        %>
</body>
</html>