<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="java.util.*" %>
<%@page import="com.btg.ositran.siged.domain.Region" %>
<%@page import="com.btg.ositran.siged.domain.DiaFestivo" %>
<%@ taglib uri="http://ajaxtags.org/tags/ajax" prefix="ajax"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>Administraci&oacute;n de d&iacute;as festivos</title>
		<link rel="stylesheet" type="text/css" href="css/estilo.css" />
		<%--link rel="stylesheet" type="text/css" media="all" href="css/calendar-green.css"> --%>
		<link rel="stylesheet" type="text/css" href="css/ajaxtags.css" />
		<link rel="stylesheet" type="text/css" href="css/displaytag.css" />
		<link rel="stylesheet" type="text/css" href="css/jquery-ui.css" />
		<script type="text/javascript" src="js/form.js"> </script>
		<script type="text/javascript" src="js/general.js"></script>
		<%--<script type="text/javascript" src="js/dtreeCarpeta.js"></script>
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/calendar-es.js"></script>
		<script type="text/javascript" src="js/calendar-setup.js"></script>--%>		
		<script type="text/javascript" src="js/prototype-1.4.0.js"></script>
		<script type="text/javascript" src="js/scriptaculous.js"></script>
		<script type="text/javascript" src="js/overlibmws.js"></script>
		<script type="text/javascript" src="js/ajaxtags-1.2-beta2.js"></script>	
		<%--<script type="text/javascript" src="js/jquery.js"></script>	
		<script type="text/javascript" src="js/jquery-ui.js"></script>
		<script type="text/javascript">
			$(document).ready(function(){
				$("input[name='fecha']").datepicker({
					showOn: 'button',
					buttonImage: 'images/calendario/calendario.png',
					buttonText: 'Click para seleccionar una fecha',
					buttonImageOnly: true,
					dateFormat: 'dd/mm/yy',
					dayNames: ['Domingo','Lunes','Martes','Miercoles','Jueves','Viernes','Sabado'],
					dayNamesMin: ['Do','Lu','Ma','Mi','Ju','Vi','Sa'],
					monthNames: ['Enero','Febrero','Marzo','Abril','Mayo','Junio','Julio','Agosto','Septiembre','Octubre','Noviembre','Diciembre']
				});
			});
		</script>--%>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<style type="text/css">
			body {
				background-color: #ffffff;
			}		
			a, a:link, a:visited, a:active{
				color: #000000;
				text-decoration: none;
				font-family: Tahoma, Verdana;
				font-size: 11px;
			}
			a:hover{
				color: #ff0000;
				text-decoration: none;
				font-family: Tahoma, Verdana;
				font-size: 11px;
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
			a.menuitem{
				font-size: 0.7em;
				font-family: Arial, Serif;
				text-decoration: none;
			}
			a.menuitem:link{
				color: #000000;
			}
			a.menuitem:hover{
				color: #ffffff;
				background: #0A246A;
			}
			a.menuitem:visited{
				color: #868686;
			}

			.Estilo1{color: #FFFFFF;}
		</style>
	</head>
	<body> 
		<s:form name="Frmsdf" action="doSaveDiafestivo" method="POST">
			<table>
				<tr>
					<td></td>
				</tr>
				<tr>
					<td width="29%" align="left">
						<span style="color: #669BCD;">Bienvenido</span>
						<span style="color: #0099FF;">
							<s:property value="#session.nombres" />
						</span>
					</td>
				</tr>
				<tr>
					<td align="left">
						<a href="<s:url action="doLogout" />" target="_parent" style="color: #0099FF;">Cerrar Sesi&oacute;n</a>
					</td>
				</tr>
			</table>
			<table  align="center" width="300">
				<tr>
					<td colspan="2" class="header" >MANTENIMIENTO DE FERIADOS</td>
				</tr>	
			</table>
			<table  align="center" width="300" border="1" bgcolor="#BFD9F1">
				<tr>
					<td>REGION</td>
					<td>DIA</td>
					<td>MES</td>
					<td>AÑO</td>
					<td>MOTIVO</td>
					<td></td>
				</tr>
				<tr>
					<td class="label">
                  <s:textfield id="region" name="region" cssClass="cajaMontoTotal" value="TODAS" size="20" />
      					<span id="indicatorregion" style="display:none;">
      						<img src="images/indicator.gif" alt="" />
      					</span>
	  					<s:textfield id="idregion" name="idregion" cssStyle="display:none" />
	  					<ajax:autocomplete
	  						source="region"
	  						target="idregion"
	  						baseUrl="autocompletar.view?metodo=ObtenerRegion"
	  						className="autocomplete"
	  						indicator="indicatorregion"
	  						minimumCharacters="1"
	  						parser="new ResponseXmlToHtmlListParser()" />
					</td>
					<td class="label">
						<s:textfield id="fechainicial" name="fechainicial" cssClass="cajaMontoTotal" maxLength="10" size="10" />
					</td>
					<td class="label">
	  					<s:textfield id="mes" name="mes" cssClass="cajaMontoTotal" size="13" />
	  					<span id="indicatormes" style="display:none;"><img src="images/indicator.gif" /></span>
	  					<s:textfield id="idmes" name="idmes" cssStyle="display:none" />
	  					<ajax:autocomplete
	  						source="mes"
	  						target="idmes"
	  						baseUrl="autocompletar.view?metodo=ObtenerMes"
	  						className="autocomplete"
	  						indicator="indicatormes"
	  						minimumCharacters="1"
	  						parser="new ResponseXmlToHtmlListParser()" />	
					</td>
					<td class="label">
	  					<s:textfield id="anio" name="anio" cssClass="cajaMontoTotal" size="10"/>
      					<span id="indicatorani" style="display:none;">
      						<img src="images/indicator.gif" alt="" />
      					</span>
	  					<s:textfield id="idanio" name="idanio" cssStyle="display:none"/>
	  					<ajax:autocomplete
	  						source="anio"
	  						target="idanio"
	  						baseUrl="autocompletar.view?metodo=ObtenerAnio"
	  						className="autocomplete"
	  						indicator="indicatorani"
	  						minimumCharacters="1"
	  						parser="new ResponseXmlToHtmlListParser()" />
					</td>
					<td class="label">
	  					<s:textfield id="motivo" name="motivo" cssClass="cajaMontoTotal" maxLength="100" size="30" />
					</td>
					<td></td>
				</tr>
			</table>
			<table  align="center" width="300">
				<tr>
					<td align="left" width="150"></td>
					<td align="left" width="150"></td>
				</tr>
				<tr>
					<td align="left" width="150">
						<s:submit name="button1" value="Guardar" cssClass="button"/>
					</td>
					<td align="left" width="150">
						<strong>
						<s:if test='aviso=="si"'>
							Ingreso Correcto
						</s:if>
						<s:if test='aviso=="no"'>
							Fecha Incorrecta
						</s:if>
						</strong>
					</td>
				</tr>	
			</table>
			<%-- %><div id="calendario">
				<input type="text" name="fecha" />
			</div>--%>
		</s:form>		
	</body>
</html>