<?xml version="1.0" encoding="UTF-8"?>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@page  import="java.util.*" %>
<%@page  import="com.btg.ositran.siged.domain.CarpetaBusqueda" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>Men&uacute; Principal</title>
		<meta http-equiv="Pragma" content="no-cache">
		<script type="text/javascript" src="./js/switchMenu.js"></script>
		<script type="text/javascript" src="./js/dtree.js"></script>
		<script type="text/javascript" src="./js/rightcontext.js"></script>
		<link href="./css/rightcontext.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" href="./css/dtree.css" type="text/css" />
		<style type="text/css">
         body {
            background-color: #FFFFFF;
         }

         .tipo0{
            padding: 6px;
            position: absolute;
            width: 200px;
            border: 2px solid black;
            background-color: menu;
            font-family: Verdana;
            line-height: 20px;
            cursor: default;
            visibility: hidden;
         }
         .tipo1{
            padding: 6px;
            cursor: default;
            position: absolute;
            width: 165px;
            background-color: Menu;
            color: MenuText;
            border: 0 solid white;
            visibility: hidden;
            border: 2 outset ButtonHighlight;
         }
         a.menuitem {font-size: 0.7em; font-family: Arial, Serif; text-decoration: none;}
         a.menuitem:link {color: #000000; }
         a.menuitem:hover {color: #ffffff; background: #0A246A;}
         a.menuitem:visited {color: #868686;}
         a, a:link, a:visited, a:active
         {color: #000000;text-decoration: none; font-family: Tahoma, Verdana; font-size: 11px}
         a:hover
         {color: #ff0000; text-decoration: none; font-family: Tahoma, Verdana; font-size: 11px}

			img{
				border: 0;
			}
		</style>
	</head>
	<body>
		<!--form-->
		<!--table width="180" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td style="width:90px;height:20px;" valign="top"-->
				<div style="height: 20px;">
					<img src="./images/MenuPrincipal4_r3_c2.gif" alt="Opciones" />
				</div>
				<!--/td>
				<td style="width:161px;" valign="top">&nbsp;</td>
			</tr>
		</table-->
		<table id="tablaMenuExpandido" width="100%" border="1" cellpadding="0" cellspacing="0">
            <!--DWLayoutTable-->
            <tr>
               <td style="height:auto;width:100%;background-color:#FFF;" valign="top">

                  <script type="text/javascript">
                     var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=no, width=600, height=420, top=120, left=250";
                     d = new dTree('d');
                     d.add(0,-1,'Gesti&oacute;n Documentaria');

                        <s:if test="#session._RECURSO.menutitmant">
                           d.add(0,-1,'Mantenimiento');
                        </s:if>
                        <s:if test="#session._RECURSO.MantMnuUsu">
                           d.add(1,0,'Usuarios','<s:url action="inicio"><s:param name="sTipoFrame">grid</s:param><s:param name="sTipoMantenimiento">MantMnuUsu</s:param></s:url>','','_parent','images/recibido.gif');
                        </s:if>
                        <s:if test="#session._RECURSO.MantMnuPro">
                           d.add(2,0,'Procesos','<s:url action="inicio"><s:param name="sTipoFrame">grid</s:param><s:param name="sTipoMantenimiento">MantMnuPro</s:param></s:url>','','_parent','images/page2.gif');
                        </s:if>
                        <s:if test="#session._RECURSO.MantMnuMod">
                           d.add(3,0,'Modulos','<s:url action="inicio"><s:param name="sTipoFrame">grid</s:param><s:param name="sTipoMantenimiento">MantMnuMod</s:param></s:url>','','_parent','images/page2.gif');
                        </s:if>
                        <s:if test="#session._RECURSO.MantMnuRec">
                           d.add(4,0,'Recursos','<s:url action="inicio"><s:param name="sTipoFrame">grid</s:param><s:param name="sTipoMantenimiento">MantMnuRec</s:param></s:url>','','_parent','images/page2.gif');
                        </s:if>
                        <s:if test="#session._RECURSO.MantMnuPer">
                           d.add(5,0,'Perfiles','<s:url action="inicio"><s:param name="sTipoFrame">grid</s:param><s:param name="sTipoMantenimiento">MantMnuPer</s:param></s:url>','','_parent','images/page2.gif');
                        </s:if>
                        <s:if test="#session._RECURSO.MantMnuExp">
                           d.add(6,0,'Expedientes','<s:url action="inicio"><s:param name="sTipoFrame">grid</s:param><s:param name="sTipoMantenimiento">MantMnuExp</s:param></s:url>','','_parent','images/page2.gif');
                        </s:if>
                        <s:if test="#session._RECURSO.UsuFinMnuMisExp">
                        d.add(29,0,'Mis Expedientes','<s:url action="inicio"><s:param name="sTipoFrame">grid</s:param><s:param name="sOpcion">UsuFinMnuMisExp</s:param></s:url>','','_parent','images/recibido.gif');
                     	</s:if>
                        <s:if test="#session._RECURSO.MantMnuFer">
                           d.add(7,0,'Feriados','','','mainFrame','images/page2.gif');
                           d.add(8,7,'Ingresos','<s:url action="inicio"><s:param name="sTipoFrame">feriado_ingreso</s:param><s:param name="sTipoMantenimiento">MantMnuFerIng</s:param></s:url>','','_parent','images/page2.gif');
                           d.add(9,7,'Listado','<s:url action="inicio"><s:param name="sTipoFrame">feriado_listado</s:param><s:param name="sTipoMantenimiento">MantMnuFerLis</s:param></s:url>','','_parent','images/page2.gif');
                        </s:if>
                        <s:if test="#session._RECURSO.MantMnuSed">
                           d.add(10,0,'Sedes','<s:url action="inicio"><s:param name="sTipoFrame">grid</s:param><s:param name="sTipoMantenimiento">MantMnuSed</s:param></s:url>','','_parent','images/page2.gif');
                        </s:if>
                        <s:if test="#session._RECURSO.MantMnuUni">
                           d.add(11,0,'Areas','<s:url action="inicio"><s:param name="sTipoFrame">grid</s:param><s:param name="sTipoMantenimiento">MantMnuUni</s:param></s:url>','','_parent','images/page2.gif');
                        </s:if>
                        <s:if test="#session._RECURSO.MantMnuRol">
                           d.add(12,0,'Roles','<s:url action="inicio"><s:param name="sTipoFrame">grid</s:param><s:param name="sTipoMantenimiento">MantMnuRol</s:param></s:url>','','_parent','images/page2.gif');
                        </s:if>
                        <s:if test="#session._RECURSO.MPMnuDocReg">
                           d.add(13,0,'Doc Registrados','<s:url action="inicio"><s:param name="sTipoFrame">grid</s:param><s:param name="sOpcion">MPMnuDocReg</s:param></s:url>','','_parent','images/recibido.gif');
                        </s:if>
                        <s:if test="#session._RECURSO.DigMnuDocIng">
                           d.add(14,0,'Doc Ingresados ('+<%=request.getAttribute("nroDocDigitalizados")%>+')','<s:url action="inicio"><s:param name="sTipoFrame">grid</s:param><s:param name="sOpcion">DigMnuDocIng</s:param></s:url>','','_parent','images/recibido.gif');
                        </s:if>
                        <s:if test="#session._RECURSO.QASMnuDocDig">
                           d.add(15,0,'Doc Digitalizados','<s:url action="inicio"><s:param name="sTipoFrame">grid</s:param><s:param name="sOpcion">QASMnuDocDig</s:param></s:url>','','_parent','images/recibido.gif');
                        </s:if>
                        <s:if test="#session._RECURSO.UsuFinMnuDocRec">
                           d.add(16,0,'Doc Recibidos','<s:url action="inicio"><s:param name="sTipoFrame">grid</s:param><s:param name="sOpcion">UsuFinMnuDocRec</s:param></s:url>','','_parent','images/recibido.gif');
                        </s:if>
                        <s:if test="#session._RECURSO.UsuFinMnuDocXEnv">
                           d.add(17,0,'Doc por Enviar','<s:url action="pendientes" />','','_parent','images/page2.gif');
                        </s:if>
                        <s:if test="#session._RECURSO.UsuFinMnuDocEnv">
                           d.add(18,0,'Doc Enviados','<s:url action="enviados" />','','_parent','images/porEnviar.gif');
                        </s:if>
                        <s:if test="#session._RECURSO.UsuFinMnuDocMsg">
                           d.add(19,0,'Doc en Mensajer&iacute;a','<s:url action="dobuscarmensajeriausuario"><s:param name="idusuario"><s:property  value="#session._USUARIO.idusuario" /></s:param></s:url>','','mainFrame','images/porEnviar.gif');
                        </s:if>
                        <s:if test="#session._RECURSO.UsuFinMnuNotif">
                        	d.add(20,0,'Mis Notificaciones('+<%=request.getAttribute("nroNotificacionesNL")%>+')','<s:url action="inicio"><s:param name="sTipoFrame">tramitebandejaNotificaciones</s:param><s:param name="sTipoMantenimiento">UsuFinMnuNotif</s:param></s:url>','','_parent','images/recibido.gif');
                     	</s:if>
                        <s:if test="#session._RECURSO.UsuFinMnuCarBus">
                           d.add(21,0,'Carpetas de B&uacute;squeda','', 'Carpetas de B&uacute;squeda', '');
                           <!-- d.add(6,5,'Nueva Carpeta','pages/bandeja/nuevaCarpeta.jsp','','mainFrame','images/recibido.gif'); -->
                           d.add(22,21,'Nueva Carpeta','<s:url action="inicio" ><s:param name="sTipoFrame">carpetaBusqueda</s:param></s:url>','','_parent','images/recibido.gif');
                           <%
							    List data = (List) request.getAttribute("lisbc");
							    CarpetaBusqueda edl;
							    int i;
							    int cons;
							    if (data != null) {

							       for (i = 0; i < data.size(); i++) {
							          edl = (CarpetaBusqueda) data.get(i);
							          cons = 23 + i;
							          
                           %>

                              d.add(<%=cons%>,21,'<span context="actions" a="<%=edl.getNombreCarpeta()%>" b="DandaveX@dxpro.es" c="<%=edl.getIdCarpetaBusqueda()%>"><%=edl.getNombreCarpeta()%></span>','<s:url action="inicioCarpetaBusqueda"><s:param name="strid"><%=edl.getIdCarpetaBusqueda()%></s:param></s:url>','','_parent','images/page2.gif');
                             
                           <%}
                        }%>
                        </s:if>
                        <s:if test="#session._RECURSO.msggrid" >
                           d.add(0,-1,'Gesti&oacute;n Documentaria');
                           d.add(23,0,'Doc. Recibidos','<s:url action="dobuscarmensajeria"><s:param name="estado">A</s:param></s:url>','','mainFrame','images/recibido.gif');
                           d.add(24,0,'Doc. Cerrados','<s:url action="dobuscarmensajeria"><s:param name="estado">C</s:param></s:url>','','mainFrame','images/recibido.gif');
                        </s:if>
                        <s:if test="#session._RECURSO.UsuFinMnuMisPro">
                           d.add(25,0,'Mis Procesos','<s:url action="inicio"><s:param name="sTipoFrame">grid</s:param><s:param name="sOpcion">UsuFinMnuMisPro</s:param></s:url>','','_parent','images/recibido.gif');
                        </s:if>
                        <s:if test="#session._RECURSO.MantMnuJer">
                           d.add(26,0,'Jerarquia','<s:url action="inicio"><s:param name="sTipoFrame">jerarquia</s:param><s:param name="sTipoMantenimiento">MantMnuJer</s:param></s:url>','','_parent','images/recibido.gif');
                        </s:if>
                        <s:if test="#session._RECURSO.MantMnuRep">
             		   	   d.add(68,0,'Reporte Pruebaa','<s:url action="inicio"><s:param name="sTipoFrame">reporte</s:param><s:param name="sTipoMantenimiento">reporte</s:param></s:url>','','_parent','images/recibido.gif');
         		    	</s:if>
                        <s:if test="#session._RECURSO.UsuFinMnuAsiRem">
                           d.add(27,0,'Asignar Reemplazo','<s:url action="inicio"><s:param name="sTipoFrame">asignarReemplazo</s:param><s:param name="sTipoMantenimiento">UsuFinMnuAsiRem</s:param></s:url>','','_parent','images/recibido.gif');
                         </s:if>
                        <s:if test="#session._RECURSO.MantMnuAud">
                           d.add(28,0,'Auditoria','<s:url action="inicio"><s:param name="sTipoFrame">auditoria</s:param><s:param name="sTipoMantenimiento">MantMnuAud</s:param></s:url>','','_parent','images/recibido.gif');
                        </s:if>


                        <s:if test="#session._RECURSO.MantMnuAud">
                           d.add(30,0,'Supervisor','<s:url action="inicio"><s:param name="sTipoFrame">super</s:param><s:param name="sOpcion">UsuFinMnuSupervisor</s:param></s:url>','','_parent','images/recibido.gif');
                     	</s:if>
                        <s:if test="#session._RECURSO.MantMnuTdo">
                           d.add(31,0,'Tipo de Documentos','<s:url action="inicio"><s:param name="sTipoFrame">grid</s:param><s:param name="sTipoMantenimiento">MantMnuTdo</s:param></s:url>','','_parent','images/page2.gif');
                        </s:if>
                        <s:if test="#session._RECURSO.MantMnuCli">
                           d.add(32,0,'Clientes','<s:url action="inicio"><s:param name="sTipoFrame">grid</s:param><s:param name="sTipoMantenimiento">MantMnuCli</s:param></s:url>','','_parent','images/page2.gif');
                        </s:if>

                        <s:if test="#session._RECURSO.MantMnuRte">
                        d.add(32,0,'Reportes SAS','','','','images/page2.gif');
               		 		d.add(33,32,'Oficios','', 'Oficios', '');
         		    			d.add(34,33,'Cantidad de oficios por empresa','<s:url action="reporteMain"><s:param name="reporte">CantOfixEmp</s:param><s:param name="titulo">Cantidad Oficios por Empresa</s:param></s:url>','','_parent','images/page2.gif');
         			  			d.add(35,33,'Cantidad de oficios por procedimiento','<s:url action="reporteMain"><s:param name="reporte">CantidadOficiosxProcedimiento</s:param><s:param name="titulo">Cantidad Oficios por Procedimiento</s:param></s:url>','','_parent','images/page2.gif');
         			  			d.add(36,33,'Cantidad de oficios por analista','<s:url action="reporteMain"><s:param name="reporte">CantOficioxAnalista</s:param><s:param name="titulo">Cantidad Oficios por Analista</s:param></s:url>','','_parent','images/page2.gif');
         			  			d.add(37,33,'Cantidad de oficios dentro y fuera de plazo ','<s:url action="reporteMain"><s:param name="reporte">CantidadOficiosDentroFueraPlazo</s:param><s:param name="titulo">Cantidad Oficios DentroFuera Plazo</s:param></s:url>','','_parent','images/page2.gif');
         		    		d.add(38,32,'Descargos','', 'Descargos', '');
		         			  d.add(39,38,'Cantidad de Descargos recibidos dentro y fuera de plazo','<s:url action="reporteMain"><s:param name="reporte">CantidadDesReciFueraPlazo</s:param><s:param name="titulo">Cantidad de Descargos recibidos dentro y fuera de plazo</s:param></s:url>','','_parent','images/page2.gif');
		         			  d.add(40,38,'Cantidad de Informes de evaluacion de Descargos x UT','<s:url action="reporteMain"><s:param name="reporte">CantidadInformesEvaluacionDescargosxUT</s:param><s:param name="titulo">Cantidad de Informes Evaluacion de Descargos x UT</s:param></s:url>','','_parent','images/page2.gif');
		         		    d.add(41,32,'Resoluci&oacute;n','', 'Resoluci&oacute;n', '');
		         			  d.add(42,41,'Cantidad Proyectos de Resoluci&oacute;n (GFE-GL)','<s:url action="reporteMain"><s:param name="reporte">CantidadProyectoResolucionGFE-GL</s:param><s:param name="titulo">Cantidad Proyecto Resolucion GFE-GL</s:param></s:url>','','_parent','images/page2.gif');
			         		d.add(43,32,'Reconsideraci&oacute;n','', 'Reconsideraci&oacute;n', '');
			         		  d.add(44,43,'Cantidad de Recursos de Reconsideraci&oacute;n dentro y fuera de plazo','<s:url action="reporteMain"><s:param name="reporte">CantidadRecuRecDentroFueraPlazo</s:param><s:param name="titulo">Cantidad Recurso Reconsideracion Dentro y Fuera del Plazo</s:param></s:url>','','_parent','images/page2.gif');
			                  d.add(45,43,'Cantidad Elaboraci&oacute;n Reconsideraci&oacute;n x UT ','<s:url action="reporteMain"><s:param name="reporte">CantidadElaboracionReconsideracionxProcedimiento</s:param><s:param name="titulo">Cantidad de Elaboracion Reconsideracion x Procedimiento</s:param></s:url>','','_parent','images/page2.gif');
			         		  d.add(46,43,'Cantidad de Proy de Resolucion de Reconsideracion pendientes y por remitir','<s:url action="reporteMain"><s:param name="reporte">CantidadProyResRecPenRemi</s:param><s:param name="titulo">Cantidad Proyectos Resolucion Reconsidereacion Pendiente Remitidos</s:param></s:url>','','_parent','images/page2.gif');
			                  d.add(47,43,'Cantidad de recursos de Reconsideraci&oacute;n x Empresa','<s:url action="reporteMain"><s:param name="reporte">CantrecuRecxEmp</s:param><s:param name="titulo">Cantidad de Recurso Reconsideracion x Empresa</s:param></s:url>','','_parent','images/page2.gif');
			         		d.add(48,32,'Apelaci&oacute;n','', 'Apelaci&oacute;n', '');
			         		  d.add(49,48,'Cantidad de Recursos de Apelaci&oacute;n dentro y fuera de plazo','<s:url action="reporteMain"><s:param name="reporte">CantidadRecApelReciDenFueraPlazo</s:param><s:param name="titulo">Cantidad Recurso de Apelacion Recibidos Dentro o Fuera del Plazo</s:param></s:url>','','_parent','images/page2.gif');
			                  d.add(50,48,'Cantidad de Expedientes en Apelacion x Pendientes x Remitir x Procedimiento','<s:url action="reporteMain"><s:param name="reporte">CantidadExpedientesApelacion</s:param><s:param name="titulo">Cantidad de Expedientes Apelacion</s:param></s:url>','','_parent','images/page2.gif');
			         		  d.add(51,19,'Cantidad Apelaciones por Empresa','','','_parent','images/page2.gif');
			         		d.add(52,32,'Recursos','', 'Recursos', '');
		         			  d.add(53,52,'Cantidad de Analistas por procedimiento','<s:url action="reporteMain"><s:param name="reporte">CantAnalistaxProc</s:param><s:param name="titulo">Cantidad de Procedimientos por Analista</s:param></s:url>','','_parent','images/page2.gif');
		         			  d.add(54,52,'Cantidad de recursos por procedimiento','<s:url action="reporteMain"><s:param name="reporte">CantidadRecursosxProcedimiento</s:param><s:param name="titulo">Cantidad Recursos por Procedimientos</s:param></s:url>','','_parent','images/page2.gif');
		         			  d.add(55,52,'Cantidad Resoluciones x Archivo Amonestaci&oacute;n Multa','<s:url action="reporteMain"><s:param name="reporte">CantidadResolucionesxProcedimiento</s:param><s:param name="titulo">Cantidad Resolucion por Procedimientos</s:param></s:url>','','_parent','images/page2.gif');
		         			  d.add(56,52,'Cantidad Resoluciones x Procedimiento','<s:url action="reporteMain"><s:param name="reporte">CantidadResolucionesxArchivoAmonestacionMulta</s:param><s:param name="titulo">Cantidad de Resoluciones x Archivo - Amonestacion - Multa</s:param></s:url>','','_parent','images/page2.gif');
		         			  d.add(57,52,'Cantidad de Resueltos por Amonestacion Multa y Archivo','<s:url action="reporteMain"><s:param name="reporte">cantResxAmonest</s:param><s:param name="titulo">Cantidad de Resueltos por Amonestacion Multa y Archivo</s:param></s:url>','','_parent','images/page2.gif');
		         			  d.add(58,52,'Cantidad Recursos x Fundado Infundado Improcedente','<s:url action="reporteMain"><s:param name="reporte">cantidadRecursosxFundadoInfundadoImprocedente</s:param><s:param name="titulo">CantidadRecursos x FundadoInfundado Improcedente</s:param></s:url>','','_parent','images/page2.gif');
                     </s:if>
                        <s:if test="#session._RECURSO.MantMnuPlt">
                           d.add(59,0,'Plantillas','<s:url action="inicio"><s:param name="sTipoFrame">grid</s:param><s:param name="sTipoMantenimiento">MantMnuPlt</s:param></s:url>','','_parent','images/page2.gif');
                        </s:if>
                        <s:if test="#session._RECURSO.MantMnuGPr">
                           d.add(60,0,'Grupo de Procesos','<s:url action="inicio"><s:param name="sTipoFrame">grid</s:param><s:param name="sTipoMantenimiento">MantMnuGPr</s:param></s:url>','','_parent','images/page2.gif');
                        </s:if>
                        <s:if test="#session._RECURSO.MantMnuNum">
                           d.add(61,0,'Numeracion','<s:url action="inicio"><s:param name="sTipoFrame">grid</s:param><s:param name="sTipoMantenimiento">MantMnuNum</s:param></s:url>','','_parent','images/page2.gif');
                        </s:if>
                        <s:if test="#session._RECURSO.MantMnuPar">
                           d.add(62,0,'Parametro','<s:url action="inicio"><s:param name="sTipoFrame">grid</s:param><s:param name="sTipoMantenimiento">MantMnuPar</s:param></s:url>','','_parent','images/page2.gif');
                        </s:if>
                        <s:if test="#session._RECURSO.MantMnuDep">
                           d.add(63,0,'Departamento','<s:url action="inicio"><s:param name="sTipoFrame">grid</s:param><s:param name="sTipoMantenimiento">MantMnuDep</s:param></s:url>','','_parent','images/page2.gif');
                        </s:if>
                           <s:if test="#session._RECURSO.MantMnuProv">
                           d.add(64,0,'Provincia','<s:url action="inicio"><s:param name="sTipoFrame">grid</s:param><s:param name="sTipoMantenimiento">MantMnuProv</s:param></s:url>','','_parent','images/page2.gif');
                        </s:if>
                        <s:if test="#session._RECURSO.MantMnuDis">
                           d.add(65,0,'Distrito','<s:url action="inicio"><s:param name="sTipoFrame">grid</s:param><s:param name="sTipoMantenimiento">MantMnuDis</s:param></s:url>','','_parent','images/page2.gif');
                        </s:if>
                        <s:if test="#session._RECURSO.MantMnuLis">
                           d.add(66,0,'Lista','<s:url action="inicio"><s:param name="sTipoFrame">grid</s:param><s:param name="sTipoMantenimiento">MantMnuLis</s:param></s:url>','','_parent','images/page2.gif');
                        </s:if>
                        <s:if test="#session._RECURSO.MantMnuRee">
                           d.add(67,0,'Reemplazo','<s:url action="inicio"><s:param name="sTipoFrame">grid</s:param><s:param name="sTipoMantenimiento">MantMnuRee</s:param></s:url>','','_parent','images/page2.gif');
                        </s:if>
                        <s:if test="#session._RECURSO.MnuCompartido">
                        	d.add(68,0,'Compartido','<s:url action="inicio"><s:param name="sTipoFrame">grid</s:param><s:param name="sTipoMantenimiento">MantMnuUsuComp</s:param></s:url>','','_parent','images/page2.gif');
	                    </s:if>

	                    <s:if test="#session._RECURSO.MantMnuLogDoc">
                    	d.add(69,0,'Log Documentos','<s:url action="inicio"><s:param name="sTipoFrame">LogOperacion</s:param><s:param name="sTipoMantenimiento">MantMnuLogDoc</s:param></s:url>','','_parent','images/page2.gif');

                    </s:if>

                        document.write(d);
                  </script>
               </td>
               <td style="width: 6px;text-align: right;height: 100%;" valign="middle">
                  <a href="#" style="background-color: transparent">
                     <img src="images/separador.gif" width="6" height="338" onclick="switchMenu()" alt="" />
                  </a>
               </td>
               <td style="height: 100%; width: 1px; background-color: #1B20B5"></td>
            </tr>
         </table>
		<table id="tablaMenuColapsado" style="display: none; width: 7px; height: 600px;border: 0;" cellpadding="0" cellspacing="0">
			<tr>
				<td style="width: 6px; height: 100%" valign="middle">
					<a href="#" style="background-color: transparent">
						<img src="images/separador2.gif" width="6" height="338" onclick="switchMenu()" alt="" />
					</a>
				</td>
				<td style="height: 100%; width: 1px; background-color: #1B20B5"></td>
			</tr>
		</table>
      <!--/form-->
      <!-- Capa que construye el menu
      <div id="cepilomenu">
         <a class="menuitem" href="javascript:principal()">Abrir Carpeta</a><br>
         <a class="menuitem" href="javascript:principal()">Eliminar Carpeta</a><br>
         <hr>
         <a class="menuitem" href="javascript:principal()">Modifica Carpeta</a><br>
      </div>
      Inicializacion de estilos -->
		<!-- capa menu -->
		<div style="text-align: center;" id="cepilomenu" >
			<script type="text/javascript">
         /*Creando el Menu*/
         menu3 = { attributes: "a,b,c",
            items:  [
               {type:RightContext.TYPE_MENU,
                  text:"Modificar: [a]",
                  onclick:function Modificar() {
                     parent.location.href="inicio.action?sTipoFrame=modificarCarpetaBusqueda&idCarpeta=[c]";
                  },
                  image: "images/bolitaRoja.gif", align:"right"
               },
               {type: RightContext.TYPE_SEPERATOR
               },
               {type:RightContext.TYPE_MENU,
                  text:"Eliminar: [a]",
                  onclick:function ejecAprob(){
                     valor=confirm("Esta Seguro que Desea Eliminar la Carpeta Busqueda?");
                     if (valor==true){
                        location.href="./doeliminarcarbusqueda.action?Idcarperta=[c]";
                     }
                  },
                  //function() {alert('Funcion para eliminar a este empleado')},
                  image: "./images/ed_rmformat.gif", align:"right"
               }
            ]
         };
         RightContext.addMenu("actions", menu3);

         RightContext.initialize();
			</script>
		</div>
	</body>
</html>