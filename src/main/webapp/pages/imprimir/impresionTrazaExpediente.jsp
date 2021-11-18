<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>    
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
		<p class="titulo">Trazabilidad del Expediente <s:property value="objDocumento.expediente.nroexpediente" /></p>
		<p>
			<strong>Asunto:</strong> <s:property value="objDocumento.expediente.asunto" /> <br/>
			<strong>Fecha de Creaci&oacute;n:</strong> <s:date name="objDocumento.expediente.fechacreacion" format="dd/MM/yyyy" /> <br/>
			<strong>Cliente:</strong> <s:property value="objDocumento.expediente.cliente.getNombreRazon()" />
			<strong>Proceso:</strong> <s:property value="objDocumento.expediente.proceso.nombre" />
		</p>
		<table align="center" style="width:95%;" class="tableForm" border="0">
			<tr class="header">
				<td class="filaCabecera" style="width:17em;">Nro. Documento</td>
                <td class="filaCabecera" style="width:10em;">Fecha Env&iacute;o</td>
                <td class="filaCabecera">De</td>
                <td class="filaCabecera">Para</td>
                <td class="filaCabecera" style="width:5em;">Acci&oacute;n</td>
                <td class="filaCabecera" style="width:5em;">Estado</td>
                <%--<td width="3%"></td> --%>
            </tr>
            <s:if test="lstTrazabilidadDocumento.size() > 0">
                <s:iterator value="lstTrazabilidadDocumento">
                    <tr>
                    	<td class="filaRespuesta"><s:property value="documento.numeroDocumento" /></td>
                        <td class="filaRespuestaCent"><s:date name="fechacreacion" format="dd/MM/yyyy HH:mm" /></td>
                        <td class="filaRespuesta">
                        		<s:property value="remitente.nombres" /> <s:property value="remitente.apellidos" /> 
                        		Área: <s:property value="remitente.unidad.nombre" />
                        </td>
                        <td class="filaRespuesta">
                        	<s:if test="destinatario != NULL">
                        		<s:property value="destinatario.nombres" /> <s:property value="destinatario.apellidos" /> 
                        		Área: <s:property value="destinatario.unidad.nombre" />
                        	</s:if>
                        	<s:else>
                        	-
                        	</s:else>
                        </td>
                        <td class="filaRespuestaCent"><s:property value="accion.descripcion" /></td>
                        <td class="filaRespuestaCent"><s:property value="estado" /></td>
                       <%--  <s:if test="accion.nombre.equals('derivar')">
                            <td><img src="images/aprobarrr.gif" border="0" alt=""/> </td>
                            </s:if>
                            <s:elseif test="accion.nombre.equals('registrar')">
                            <td><img src="images/aprobarrr.gif" border="0" alt=""/></td>
                            </s:elseif>
                            <s:elseif test="accion.nombre.equals('aprobar')">
                            <td><img src="images/aprobarrr.gif" border="0" alt=""/></td>
                            </s:elseif>
                            <s:elseif test="accion.nombre.equals('rechazar')">
                            <td> <img src="images/desaprobar.gif" border="0" alt=""/></td>
                            </s:elseif>
                            <s:elseif test="accion.nombre.equals('pendiente')">
                            <td><img src="images/pendiente.gif" border="0" alt=""/></td>
                            </s:elseif>
                            <s:elseif test="accion.nombre.equals('aprobarqas')">
                            <td><img src="images/aprobarrr.gif" border="0" alt=""/></td>
                            </s:elseif>
                            <s:else>
                            	<td>&nbsp;</td>
                        	</s:else>
                        --%>
                    </tr>
                </s:iterator>
            </s:if>
		</table>
		<script type="text/javascript">
			window.print();
		</script>
	</body>
</html>