<?xml version="1.0" encoding="UTF-8"?>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es">
<head>
<title>Trazabilidad del Documento</title>
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
            function Abrir_ventana (pagina) {
                var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=800, height=700, top=50, left=200";
                window.open(pagina,"",opciones);
            }

            function Abrir_ventanaCopia (pagina) {
                var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=650, height=315, top=50, left=200";
                window.open(pagina,"",opciones);
            }
         
            function Abrir_pagina (pagina) {
                var opciones="location=mainFrame";
                window.open(pagina,"",opciones);
            }

            function regresar(){
                window.close();
            }

            function verDetalleCopias(idTrazabilidad){
                var pagina = "detalleCopias.action?idTrazabilidad="+idTrazabilidad+"&tipoOrigen=D";
                Abrir_ventanaCopia(pagina);
            }
         
            function verDetalleApoyo(idTrazabilidad){
            	var pagina = "detalleApoyos.action?idTrazabilidad="+idTrazabilidad;
                Abrir_ventanaCopia(pagina);
            }
            
            function imprimirTraza(){
            	var pagina = "goViewSeguimiento.action?mode=imprimir&iIdDocumento=" + <s:property value='objDocumento.idDocumento' />;
                Abrir_ventanaCopia(pagina);
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

				Trazabilidad del Documento <s:property
					value="objDocumento.numeroDocumento" /></td>
		</tr>
		<s:if test="fechaLimite!=null">
			<tr height="12%">
				<td valign="center" align="center"
					style="text-align: center; margin-bottom: 10px;"><span>Fecha
						l&iacute;mite de Atenci&oacute;n:</span> <span
					style='padding: 6px 8px; font-weight: bold'> <%--<s:date name="fechaLimite" format="dd 'de' MMMM 'del' yyyy 'a las' HH:mm 'horas'" />--%>
						<s:property value="fechaEnTexto" /> </span></td>
			</tr>
		</s:if>
		<s:if test="apoyosPendientes > 0">
			<tr height="12%">
				<td valign="center" align="center"
					style="text-align: center; margin-bottom: 10px;"><s:if
						test="apoyosPendientes > 1">
							Actualmente se encuentran en curso <s:property
							value="apoyosPendientes" /> apoyos
						</s:if> <s:else>
							Actualmente se encuentra en curso <s:property
							value="apoyosPendientes" /> apoyo
						</s:else></td>
			</tr>
		</s:if>
		<tr height="18%">
			<td valign="center" align="center"
				style="FONT-WEIGHT: bold; FONT-SIZE: 10pt; COLOR: #31619c; FONT-FAMILY: verdana, arial, helvetica, sans-serif">


			</td>
		</tr>
	</table>

	<table cellpadding="1" cellspacing="1" class="tableForm" width="90%"
		align="center">
		<tr class="header">
			<td width="25%" class="data">Fecha Env&iacute;o</td>
			<td width="27%" class="data">De</td>
			<td width="27%" class="data">Para</td>
			<s:if test="mostrarEtapa != null && mostrarEtapa == true">
				<td width="18%" class="data">Etapa</td>
			</s:if>
			<s:if test="mostrarActividad != null && mostrarActividad == true">
				<td width="18%" class="data">Actividad</td>
			</s:if>
			<td width="18%" class="data">Tr&aacute;mite</td>
			<td class="data">Estado</td>
			<%--
				<td width="3%" class="data"></td>
                <td width="3%" class="data">Copias</td>
                <td width="5%" class="data">Copias Apoyo</td>
                --%>
		</tr>
		<s:if test="trazabilidadUnificada.size() > 0">
			<s:iterator value="trazabilidadUnificada">
				<tr class="altRow2"
					<s:if test="pk.tipo.equals('Copia')">
                        	style="color:blue;"
                    </s:if>
					<s:if test="pk.tipo.equals('Transferencia')">
                        	style="color:black;"
                    </s:if>
                    <s:else>
                    	style="color:red;"
                    </s:else>>
					<td align="center"><s:date name="fechaCreacion" format="dd/MM/yyyy HH:mm" /></td>
					<td align="center"><s:property value="remitente" escape="false"/></td>
					<td align="center"><s:property value="destinatario" escape="false"/></td>
					<td align="center"><s:property value="accion" /></td>
					<td align="center"><s:property value="estado" /></td>
				</tr>
			</s:iterator>
			<%-- 
			<s:iterator value="lstTrazabilidadDocumento">
				<tr class="altRow2"
					<s:if test="conCopias">
                        	style="color:blue;"
                        </s:if>
					<s:if test="conApoyo">
                        	style="color:red;"
                        </s:if>>
					<td align="center"><s:date name="fechacreacion"
							format="dd/MM/yyyy HH:mm" />
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
					<s:if test="mostrarEtapa != null && mostrarEtapa == true">
						<td align="center"><s:property value="idetapa.descripcion" />
						</td>
					</s:if>
					<s:if test="mostrarActividad != null && mostrarActividad == true">
						<td align="center"><s:property value="actividad" />
						</td>
					</s:if>
					<td align="center"><s:property value="accion.descripcion" />
					</td>
					<td align="center"><s:property value="estado" />
					</td>
					<%--
                        <s:if test="accion.nombre.equals('derivar')">
                            <td align="center"><img src="images/aprobarrr.gif" border="0" alt=""/> </td>
                        </s:if>
                        <s:elseif test="accion.nombre.equals('registrar')">
                            <td  align="center"><img src="images/aprobarrr.gif" border="0" alt=""/></td>
                        </s:elseif>
                            <s:elseif test="accion.nombre.equals('aprobar')">
                            <td  align="center"><img src="images/aprobarrr.gif" border="0" alt=""/></td>
                        </s:elseif>
                        <s:elseif test="accion.nombre.equals('rechazar')">
                            <td align="center"> <img src="images/desaprobar.gif" border="0" alt=""/></td>
                        </s:elseif>
                        <s:elseif test="accion.nombre.equals('pendiente')">
                            <td  align="center"><img src="images/pendiente.gif" border="0" alt=""/></td>
                        </s:elseif>
                        <s:elseif test="accion.nombre.equals('aprobarqas')">
                            <td align="center"><img src="images/aprobarrr.gif" border="0" alt=""/></td>
                        </s:elseif>
                        <s:else>
                        	<td  align="center"></td>
                        </s:else>
                     --%>
					<%--
                        <td align="center">
                            <s:if test="conCopias">
                                <img src="images/xx.gif" border="0" alt="" onClick="verDetalleCopias('<s:property value="idtrazabilidaddocumento" />')"/>
                            </s:if>
                        </td>
                        <td align="center">
                            <s:if test="conApoyo">
                                <img src="images/xx.gif" border="0" alt="" onClick="verDetalleApoyo('<s:property value="idtrazabilidaddocumento" />')"/>
                            </s:if>
                        </td>
                         --%
				</tr>

			</s:iterator>
			--%>
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