
<%@page  import="java.text.*" %>
<%@page  import="java.util.*" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@page  import="org.ositran.utils.Expedienfindadvance" %>
<%@ taglib uri="http://ajaxtags.org/tags/ajax" prefix="ajax"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Tramite Documentario</title>

<link rel="stylesheet" type="text/css" href="/siged/css/estilo.css">
<script language="javascript" src="/siged/js/switchMenu.js"></script>
<script language="javascript" src="/siged/js/form.js"> </script>
<script language="Javascript" src="/siged/js/general.js"></script>
<link rel="stylesheet" type="text/css" media="all" href="./css/calendar-green.css">
<script type="text/javascript" src="/siged/js/calendar.js"></script>
<script type="text/javascript" src="/siged/js/calendar-es.js"></script>
<script type="text/javascript" src="/siged/js/calendar-setup.js"></script>
<script type="text/javascript" src="/siged/js/si.files.js"></script>
<script type="text/javascript" src="/siged/js/sorttable.js"></script>
<script type="text/javascript" src="/siged/runtime/lib/aw.js"></script>
<link   rel="stylesheet" href="/siged/runtime/styles/system/aw.css">

<script type="text/javascript" src="/siged/js/prototype-1.4.0.js"></script>
<script type="text/javascript" src="/siged/js/scriptaculous.js"></script>
<script type="text/javascript" src="/siged/js/overlibmws.js"></script>
<script type="text/javascript" src="/siged/js/ajaxtags-1.2-beta2.js"></script>
<link rel="stylesheet" type="text/css" href="/siged/css/ajaxtags.css" />
<link rel="stylesheet" type="text/css" href="/siged/css/displaytag.css" />

<script type='text/javascript' src='/siged/dwr/engine.js'> </script>
<script type='text/javascript' src='/siged/dwr/util.js'> </script>
<script type='text/javascript' src='/siged/dwr/interface/toolDwr.js'></script>
<script type='text/javascript' src='/siged/dwr/interface/Tipoenvio.js'></script>
<script type='text/javascript' src='/siged/dwr/interface/Empresadestino.js'></script>
    
	<style type="text/css">
		.aw-quirks * {
		      box-sizing: border-box;
		   		-moz-box-sizing: border-box;
		-webkit-box-sizing: border-box;
		}
		body {font: 12px Tahoma}
	</style>
	<script type='text/javascript'>
		function regresar(){ 
		   history.back();
		}
	</script>

	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body>
<table>
	<tr>
	<td width="29%" align="left">
	<font color="669BCD">Bienvenido </font><font color="0099FF"><s:property value="#session.nombres" /></font>
	</td>
	</tr>
	<tr>
	<td align="left">
	<font color="0099FF"><a href="<s:url action="doLogout" />" target="_parent">Cerrar Sesi&oacute;n</a></font>
	</td>
	</tr>
</table>
<table>
	<tr>
		<td>
			<table width="750" border="0" height="20" align="left">
				<tr>
			    	<td> <img src="images//regresar.bmp" border="0" onClick="regresar()" alt="Regresar"/></td>
					<td bgcolor="#4481B8" " align="center" class="tituloRojo">Ingreso Carpeta de Busqueda</td>
			    	<td bgcolor="#fffff" align="center" class="tituloRojo">&nbsp;</td>
				</tr>
 			</table>
		</td>
	</tr>
	<tr>
		<td>
			<table width="750" border="0" cellspacing="0" cellpadding="0" bordercolor="#669BCD" bgcolor="#BFD9F1" >		  			
			  <tr>
			  	<td width="200">&nbsp;Nro. Expediente: </td>
			  	<td width="170"><input type="text" name="txtnroexpediente" size="15" class="cajaMontoTotal"></td>
			  	<td width="30">&nbsp;</td>
			  	<td width="200">&nbsp;Nro. Documento: </td>
			  	<td width="170"><input type="text" name="txtnrodocumento" size="15" class="cajaMontoTotal"></td>
			  	<td width="30">&nbsp;</td>
			  	<td width="150">&nbsp;</td>
			  </tr>
			  <tr>
			  	<td>&nbsp;Nro. M. Partes: </td>
			  	<td><input type="text" name="txtnrompartes" class="cajaMontoTotal" ></td>
			  	<td>&nbsp;</td>
			  	<td>&nbsp;Tipo Documento: </td>
			  	<td><s:textfield id="tipodocumento" name="tipodocumento" cssClass="cajaMontoTotal"/>
					<span id="indicatortipdoc" style="display:none;"><img src="/siged/images/indicator.gif" /></span>
					<s:textfield id="idtipodocumento" name="idtipodocumento" cssStyle="display:none" />
						<ajax:autocomplete
			  		source="tipodocumento"
			  		target="idtipodocumento"
			  		baseUrl="/siged/autocompletar.view?metodo=ObtenerTipoDoc"
			  		className="autocomplete"
			  		indicator="indicatortipdoc"
			  		minimumCharacters="1"
			  		parser="new ResponseXmlToHtmlListParser()" />
					</td>
			  	<td>&nbsp;</td>
			  	<td>&nbsp;</td>
			  </tr>
			  <tr>
			  	<td>&nbsp;Consecionario:</td>
			  	<td><input type="text" name="txtcorrentista" class="cajaMontoTotal"></td>
			  	<td>&nbsp;</td>
			  	<td>&nbsp;</td>
			  	<td>&nbsp;</td>
			  	<td>&nbsp;</td>
			  	<td>&nbsp;</td>
			  </tr>
			  <tr>
			  	<td>&nbsp;Cliente:</td>
			  	<td><input type="text" name="txtcliente" class="cajaMontoTotal"></td>
			  	<td>&nbsp;</td>
			  	<td>&nbsp;</td>
			  	<td>&nbsp;</td>
			  	<td>&nbsp;</td>
			  	<td>&nbsp;</td>
			  </tr>
			  <tr>
			  	<td>&nbsp;Area  Destino : </td>
			  	<td><input type="text" name="txtaredes" class="cajaMontoTotal"></td>
			  	<td>&nbsp;</td>
			  	<td>&nbsp;</td>
			  	<td>&nbsp;</td>
			  	<td>&nbsp;</td>
			  	<td>&nbsp;</td>
			  </tr>
			  <tr>
			    <td>&nbsp;Propietario:</td>
			    <td><input type="text" name="txtpropietario" class="cajaMontoTotal"></td>
			    <td>&nbsp;</td>
			   	<td>&nbsp;</td>
			    <td>&nbsp;</td>
			    <td>&nbsp;</td>
			    <td>&nbsp;</td>
			  </tr>
			  <tr>
			  	<td>&nbsp;Proceso:</td>
					<td><s:textfield id="proceso" name="proceso" cssClass="cajaMontoTotal"/>
					<span id="indicatorPro" style="display:none;"><img src="/siged/images/indicator.gif" /></span>
					<s:textfield id="idproceso" name="idproceso" cssStyle="display:none" />
						<ajax:autocomplete
			  			source="proceso"
			  			target="idproceso"
			  			baseUrl="/siged/autocompletar.view?metodo=ObtenerProceso"
			  			className="autocomplete"
			  			indicator="indicatorPro"
			  			minimumCharacters="1"
			  			parser="new ResponseXmlToHtmlListParser()" />
					</td>
			  	<td>&nbsp;</td>
			  	<td>&nbsp;</td>
			  	<td>&nbsp;</td>
			  	<td>&nbsp;</td>
			  	<td>&nbsp;</td>
			  </tr>
			  <tr>
			  	<td>&nbsp;</td>
			  	<td>&nbsp;</td>
			  	<td>&nbsp;</td>
			  	<td>&nbsp;</td>
			  	<td>&nbsp;</td>
			  	<td>&nbsp;</td>
			  	<td>&nbsp;</td>
			  </tr>
			  <tr>
			  	<td>&nbsp;<input type="submit" name="Submit" value="Guardar..." class="button"></td>
			  	<td>&nbsp;</td>
			  	<td>&nbsp;</td>
			  	<td>&nbsp;</td>
			  	<td>&nbsp;</td>
			  	<td>&nbsp;</td>
			  	<td>&nbsp;</td>
			  </tr>
			  <tr>
			  	<td>&nbsp;</td>
			  	<td>&nbsp;</td>
			  	<td>&nbsp;</td>
			  	<td>&nbsp;</td>
			  	<td>&nbsp;</td>
			  	<td>&nbsp;</td>
			  	<td>&nbsp;</td>
			  </tr>
			</table>
		</td>
	</tr>
</table>	
    	
</body>
</html>

