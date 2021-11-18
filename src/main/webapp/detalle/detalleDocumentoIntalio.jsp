<?xml version="1.0" encoding="UTF-8" ?>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<meta http-equiv="cache-control" content="no-cache">
		<title>Detalle Documento Intalio</title>
		<link rel="stylesheet" type="text/css" href="css/detalle/intalio.css" />
		<script type="text/javascript" src="js/detalleIntalio.js"></script>
	</head>
	<body>
		<div style="display: none;">
			<s:hidden id="urlIntalio" name="urlIntalio" />
			<s:hidden id="idDocumento" name="documento.idDocumento" />
			<s:hidden id="idExpediente" name="documento.expediente.idexpediente" />
			<s:hidden id="idProceso" name="documento.expediente.proceso.idproceso" />
			<s:hidden id="rol" name="#session._USUARIO.rol" />
			<s:hidden id="tipo" name="@org.ositran.utils.Constantes@TIPO_PROCESO_INTALIO" />
		</div>
		<div id="toolbar">
			<button id="proceso">Proceso</button>
			<s:if test="#session._RECURSO.UsuFinBtnTraExp">
			<button id="trazabilidad">Trazabilidad</button>
			</s:if>
			<s:if test="#session._RECURSO.UsuFinBtnNueDocSec">
			<button id="plantilla">Plantilla</button>
			</s:if>
			<s:if test="#session._RECURSO.UsuFinBtnUpl">
			<button id="adjuntar">Adjuntar</button>
			</s:if>
			<s:if test="#session._RECURSO.UsuFinBtnUpl">
			<button id="versionar">Versionar</button>
			</s:if>
			<s:if test="#session._RECURSO.UsuFinBtnUpl">
			<button id="enumerar">Enumerar</button>
			</s:if>
			<s:if test="#session._RECURSO.UsuFinBtnUpl">
			<button id="nuevo">Nuevo Expediente</button>
			</s:if>
			<div id="otro">
				<div id="menuOtro">
					<s:if test="#session._RECURSO.UsuFinBtnArc">
					<div id="archivar">Archivar</div>
					</s:if>
					<s:if test="#session._RECURSO.UsuFinBtnDocVir">
					<div id="otros">Otros Expedientes</div>
					</s:if>
					<div id="ayuda">Ayuda en L&iacute;nea</div>
				</div>
			</div>
		</div>
		<div id="datos">
			<div id="archivos">
				<s:if test="archivos!=null && archivos.size()>0">
				<p>Adjuntos:</p>
				<ul>
					<s:iterator value="archivos">
						<li><a href="<s:property value="sURL" />" target="_blank"><s:property value="nombreReal" /></a></li>
					</s:iterator>
				</ul>
				</s:if>
			</div>
			<div id="sumilla">
				<div id="tabla">
					<div class="fila">
						<strong>N&uacute;mero de Expediente</strong>
						<span><s:property value="documento.expediente.nroexpediente" /></span>
					</div>
					<div class="fila">
						<strong>Proceso</strong>
						<span><s:property value="documento.expediente.proceso.nombre" /></span>
					</div>
					<div class="fila">
						<strong>Cliente</strong>
						<span><s:property value="nombreCliente" /></span>
					</div>
					<s:if test="documento.expediente.concesionario!=null">
					<div class="fila">
						<strong>Concesionario</strong>
						<span><s:property value="documento.expediente.concesionario.razonSocial" /></span>
					</div>
					</s:if>
				</div>
			</div>
		</div>
		<div id="contenido">
			<strong class="asunto"><s:property value="documento.expediente.asunto" /></strong>
			<div>
				<span>Para:</span>
				<span><s:property value="documento.propietario.nombres" /> <s:property value="documento.propietario.apellidos" /></span>
				<hr />
				<p>
					<s:if test="documento.expediente.contenido!=null">
					<s:property value="documento.expediente.contenido" />
					</s:if>
					<s:else>
					Usted ha recibido un(a) <strong><s:property value="documento.tipoDocumento.nombre" /></strong> con numeral <strong><s:property value="documento.numeroDocumento" /></strong> conteniendo <strong><s:property value="documento.numeroFolios" /> folios</strong>, el d&iacute;a <strong><s:date name="documento.fechaAccion" format="dd/MM/yyyy" /></strong> a las <strong><s:date name="documento.fechaAccion" format="HH:mm" /></strong>; enviado por <strong><s:property value="documento.remitente" /></strong>.
					<br />
					<s:if test='documento.expediente.nrointerno!=null && documento.expediente.nrointerno!=" "'>
                    Se gener&oacute; el expediente interno con n&uacute;mero <strong><s:property value="documento.expediente.nrointerno"/></strong>.
                    <br /> 
                    </s:if>  
                    Este documento hace referencia al proceso <strong><s:property value="documento.expediente.proceso.nombre" /></strong>.
					</s:else>
				</p>
				<p>
					El cliente <strong><s:property value="nombreCliente" /></strong> se identifica con <strong><s:property value="documento.expediente.cliente.tipoIdentificacion.nombre" />: <s:property value="documento.expediente.cliente.numeroIdentificacion" /></strong> y direcci&oacute;n domiciliaria <strong><s:property value="documento.expediente.cliente.direccionPrincipal" /></strong>.
				</p>
				<s:if test="documento.expediente.concesionario!=null">
				<p>
					La entidad <strong><s:property value="documento.expediente.concesionario.razonSocial" /></strong> se identifica con <strong>RUC: <s:property value="documento.expediente.concesionario.ruc" /></strong> y direcci&oacute;n <strong><s:property value="documento.expediente.concesionario.direccion" /></strong>.
				</p>
				</s:if>
			</div>
		</div>
	</body>
</html>