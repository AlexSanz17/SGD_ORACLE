<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://ajaxtags.org/tags/ajax" prefix="ajax"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Tramite Documentario</title>
<link rel="stylesheet" type="text/css" href="css/estilo.css">
<script language="javascript" src="js/form.js"> </script>
<script language="Javascript" src="js/general.js"></script>
<link rel="stylesheet" type="text/css" media="all"
	href="css/calendar-green.css">
<script type="text/javascript" src="js/calendar.js"></script>
<script type="text/javascript" src="js/calendar-es.js"></script>
<script type="text/javascript" src="js/calendar-setup.js"></script>

<script type="text/javascript" src="./js/prototype-1.4.0.js"></script>
<script type="text/javascript" src="./js/scriptaculous.js"></script>
<script type="text/javascript" src="./js/overlibmws.js"></script>
<script type="text/javascript" src="./js/ajaxtags-1.2-beta2.js"></script>
<link rel="stylesheet" type="text/css" href="./css/ajaxtags.css" />
<link rel="stylesheet" type="text/css" href="./css/displaytag.css" />

<script type='text/javascript' src='./dwr/engine.js'> </script>
<script type='text/javascript' src='./dwr/util.js'> </script>
<script type='text/javascript' src='./dwr/interface/toolDwr.js'> </script>

<script type="text/javascript">

	var gIdTipoIdentif;

	function saveTipoIdentificacion() {
		gIdTipoIdentif = document.getElementById("idtipoidentificacion").value;
	}

         function UpdateInfoProcess()
         {
		var idproceso = dwr.util.getValue("idproceso");

            toolDwr.ObtenerInfoProcess(idproceso, function(data)
            {
			dwr.util.setValue("strResponsable", data.resposable);
			dwr.util.setValue("strUnidad", data.unidad);
            }
         );
	}

         function UpdateInfoCliente()
         {
		var nroidentificacion = dwr.util.getValue("nroidentificacion");

            toolDwr.ObtenerInfoCliente(nroidentificacion, gIdTipoIdentif, function(data)
            {
			dwr.util.setValue("strRazonSocial", data.razonsocial);
			dwr.util.setValue("strDireccionPrincipal", data.direccionp);

			dwr.util.setValue("iddepartamento", data.iddepartmento);
			dwr.util.setValue("departamento", data.departmento);

			dwr.util.setValue("idprovincia", data.idprovincia);
			dwr.util.setValue("provincia", data.provincia);

			dwr.util.setValue("iddistrito", data.iddistrito);
			dwr.util.setValue("distrito", data.distrito);
            }
         );
	}

         function UpdateInfoConcesionario()
         {
		var ruc = dwr.util.getValue("strRUC");

            toolDwr.ObtenerInfoConcesionario(ruc, function(data)
            {
			dwr.util.setValue("idcorrentista", data.idconcesionario);
			dwr.util.setValue("correntista", data.razonsocial);
			dwr.util.setValue("strDireccion", data.direccion);
			dwr.util.setValue("strCorreo", data.correo);
            }
         );
	}
</script>


<script type="text/javascript">
         function limpiarProvDist()
         {
		document.getElementById("provincia").value = "";
		document.getElementById("distrito").value = "";

		document.getElementById("idprovincia").value = "";
		document.getElementById("iddistrito").value = "";

	}

         function limpiarDist()
         {
		document.getElementById("distrito").value = "";
		document.getElementById("iddistrito").value = "";
	}
</script>


<script language="JavaScript">
	function Abrir_ventanaBuscar(pagina) {
		var opciones = "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=760, height=530, top=50, left=200";
		window.open(pagina, "", opciones);
	}

	function Abrir_ventanaCargo(pagina) {
		/*
		var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=760, height=380, top=50, left=200";
		window.open(pagina,"",opciones);
		 */
		//location.href="pages/tramite/cargo.html";
		document.forms["formnewDoc"].submit();
	}

	function Abrir_pagina(pagina) {
		var opciones = "location=mainFrame";
		window.open(pagina, "", opciones);
	}

	function putTextOnTextBox(textToPut) {
		document.all.reciveTheText.value = textToPut;
	}
	function regresar() {
		history.back();
	}
</script>

<script type="text/javascript">

         function poorman_toggle(id)
         {
		var tr = document.getElementById(id);
            if (tr==null) { return; }
		var bExpand = tr.style.display == '';
		tr.style.display = (bExpand ? 'none' : '');
	}
         function poorman_changeimage(id, sMinus, sPlus)
         {
		var img = document.getElementById(id);
            if (img!=null)
            {
			var bExpand = img.src.indexOf(sPlus) >= 0;
			if (!bExpand)
				img.src = sPlus;
			else
				img.src = sMinus;
		}
	}

         function Toggle_repHeader2()
         {
            poorman_changeimage('repHeader2_Img', 'images/signo_menos.gif', 'images/signo_mas.gif');
		poorman_toggle('row1');
		poorman_toggle('row2');
		poorman_toggle('row3');
	}

         function Toggle_repHeader1()
         {
            poorman_changeimage('repHeader1_Img', 'images/signo_menos.gif', 'images/signo_mas.gif');
		poorman_toggle('trRow1');
	}
         function Toggle_repHeader3()
         {
            poorman_changeimage('repHeader3_Img', 'images/signo_menos.gif', 'images/signo_mas.gif');
		poorman_toggle('row1');
		poorman_toggle('row2');
		poorman_toggle('row3');
	}

         function Toggle_repHeader3()
         {
            poorman_changeimage('repHeader3_Img', 'images/signo_menos.gif', 'images/signo_mas.gif');
		poorman_toggle('trRow3');
	}
         function Toggle_repHeader4()
         {
            poorman_changeimage('repHeader4_Img', 'images/signo_menos.gif', 'images/signo_mas.gif');
		poorman_toggle('row1');
		poorman_toggle('row2');
		poorman_toggle('row3');
	}

         function Toggle_repHeader4()
         {
            poorman_changeimage('repHeader4_Img', 'images/signo_menos.gif', 'images/signo_mas.gif');
		poorman_toggle('trRow4');
	}
         function Toggle_repHeader5()
         {
            poorman_changeimage('repHeader5_Img', 'images/signo_menos.gif', 'images/signo_mas.gif');
		poorman_toggle('row1');
		poorman_toggle('row2');
		poorman_toggle('row3');
	}

         function Toggle_repHeader5()
         {
            poorman_changeimage('repHeader5_Img', 'images/signo_menos.gif', 'images/signo_mas.gif');
		poorman_toggle('trRow5');
	}
         function Toggle_repHeader6()
         {
            poorman_changeimage('repHeader6_Img', 'images/signo_menos.gif', 'images/signo_mas.gif');
		poorman_toggle('row1');
		poorman_toggle('row2');
		poorman_toggle('row3');
	}

         function Toggle_repHeader6()
         {
            poorman_changeimage('repHeader6_Img', 'images/signo_menos.gif', 'images/signo_mas.gif');
		poorman_toggle('trRow6');
	}
</script>


<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<style type="text/css">
<!--
body {
	background-color: #ffffff;
}

.barra {
	scrollbar-3dlight-color: #CCCCCC;
	scrollbar-arrow-color: #568BBF;
	scrollbar-base-color: #C3D5E9;
	scrollbar-darkshadow-color: #666666;
	scrollbar-highlight-color: #669BCD;
	scrollbar-shadow-color: #999999;
}
-->
</style>

</head>

<body class="barra" topmargin="0" leftmargin="0" rigthmargin="0">
<s:form name="formnewDoc" action="doRegistrar" method="POST">
	<table width="100%">
		<tr>
			<td height="14" colspan="3"></td>
		</tr>
		<tr>
			<td height="20" colspan="3" class="titulo">
			<table width="99%" border="0" height="20" align="center"
				bgcolor="#A2C0DC">
				<tr>
					<td align="left" class="titulo">Nuevo Documento</td>
				</tr>
			</table>
			</td>
		</tr>
		<tr align="center">
			<td width="1%" align="center">&nbsp;</td>
			<td width="99%" colspan="2" align="left"><img
				src="images/xx.bmp" border="0"
				onClick="javascript:Abrir_ventanaCargo()" alt="Registrar" /></td>
		</tr>
		<tr>
			<td height="14" colspan="3">
			<table width="95%" border="1" align="center" bordercolor="#669BCD"
				bgcolor="#BFD9F1">
				<tr>
					<td height="75">
					<table width="98%" height="97" align="center">
						<tr>
							<td width="2%"><s:textfield name="objDD.iIdExpediente"
								cssStyle="display:none" /> <s:textfield
								name="objDD.iIdResponsable" cssStyle="display:none" /></td>
							<td width="18%" height="5"></td>
							<td width="17%"></td>
							<td width="15%"></td>
							<td width="45%"></td>
							<td width="3%"></td>
						</tr>
						<tr>
							<td></td>
							<td align="left">Referencia</td>
							<td><s:textfield name="objDD.strReferencia"
								cssClass="cajaMontoTotal" size="50" /></td>
							<td><img
								onClick="javascript:Abrir_ventanaBuscar('/siged/doLoadSearchExpediente.action')"
src="images/memos.gif" border="0" /></td>
							<td></td>
						</tr>
						<tr>
							<td></td>
							<td height="16" align="left">Proceso</td>
							<td colspan="3" class="label"><s:textfield id="proceso"
								name="proceso" cssClass="cajaMontoTotal" size="40"
								onblur="UpdateInfoProcess()" /> <span id="indicatorDep"
								style="display: none;"><img src="./images/indicator.gif" /></span>
							<s:textfield id="idproceso" name="idproceso"
								cssStyle="display:none" /> <ajax:autocomplete source="proceso"
								target="idproceso"
								baseUrl="./autocompletar.view?metodo=ObtenerProceso"
								className="autocomplete" indicator="indicatorDep"
								minimumCharacters="1" parser="new ResponseXmlToHtmlListParser()" />

							</td>
							<td></td>
						</tr>
						<tr>
							<td></td>
							<td height="16" align="left">Area Destino</td>
							<td colspan="3" align="left" class="label"><s:textfield
								name="strUnidad" cssClass="cajaMontoTotal" size="50" /></td>
							<td align="left"></td>
						</tr>
						<tr>
							<td></td>
							<td height="16" align="left">Destinatario</td>
							<td colspan="3" class="label"><s:textfield
								name="strResponsable" cssClass="cajaMontoTotal" size="35" /></td>
							<td></td>
						</tr>
						<tr>
							<td colspan="6" height="15">
							<hr>
							</td>
						</tr>
						<tr>
							<td colspan="6" height="15" class="label">DOCUMENTO</td>
						</tr>
						<tr>
							<td></td>
							<td height="16" align="left">Tipo Documento</td>
							<td align="left" width="17%" class="label"><s:textfield
								id="tipodocumento" name="tipodocumento"
								cssClass="cajaMontoTotal" size="20" /> <span
								id="indicatortipoDoc" style="display: none;"><img
								src="./images/indicator.gif" /></span> <s:textfield
								id="idtipodocumento" name="idtipodocumento"
								cssStyle="display:none" /> <ajax:autocomplete
								source="tipodocumento" target="idtipodocumento"
								baseUrl="./autocompletar.view?metodo=ObtenerTipoDoc"
								className="autocomplete" indicator="indicatortipoDoc"
								minimumCharacters="1" parser="new ResponseXmlToHtmlListParser()" />
							</td>
							<td align="left" class="label"></td>
							<td align="left" class="label"></td>
							<td></td>
						</tr>
						<tr>
							<td></td>
							<td height="16" align="left">Nro. Documento</td>
							<td align="left" width="17%" class="label"><s:textfield
								name="objDD.strNroDocumento" cssClass="cajaMontoTotal" size="20" />
							</td>
							<td align="left" class="label"></td>
							<td align="left" class="label"></td>
							<td></td>
						</tr>
						<tr>
							<td></td>
							<td height="16" align="left">Nro Folios</td>
							<td align="left" width="17%" class="label"><s:textfield
								name="objDD.iNroFolios" cssClass="cajaMontoTotal" size="20" />
							</td>
							<td align="left"></td>
							<td align="left" width="45%" class="label"></td>
							<td></td>
						</tr>
						<tr>
							<td></td>
							<td height="16" align="left">Asunto</td>
							<td colspan="3" align="left" class="label"><s:textfield
								name="objDD.strAsunto" cssClass="cajaMontoTotal" size="60" /></td>
							<td></td>
						</tr>
						<tr>
							<td colspan="6" height="15">
							<hr>
							</td>
						</tr>
						<tr>
							<td colspan="6" height="15" class="label">CLIENTE</td>
						</tr>
						<tr>
							<td></td>
							<td height="16" align="left">Tipo Doc Identidad</td>
							<td align="left" width="17%" class="label"><s:textfield
								id="tipoidentificacion" name="tipoidentificacion"
								cssClass="cajaMontoTotal" size="20"
								onblur="saveTipoIdentificacion()" /> <span
								id="indicatortipoDocIdent" style="display: none;"><img
								src="./images/indicator.gif" /></span> <s:textfield
								id="idtipoidentificacion" name="idtipoidentificacion"
								cssStyle="display:none" /> <ajax:autocomplete
								source="tipoidentificacion" target="idtipoidentificacion"
								baseUrl="./autocompletar.view?metodo=ObtenerTipoDocIdent"
								className="autocomplete" indicator="indicatortipoDocIdent"
								minimumCharacters="1" parser="new ResponseXmlToHtmlListParser()" />

							</td>
							<td align="left" class="label"></td>
							<td align="left" class="label"></td>
							<td></td>
						</tr>
						<tr>
							<td></td>
							<td height="16" align="left">Nro Doc Identidad</td>
							<td align="left" width="17%" class="label"><s:textfield
								name="nroidentificacion" cssClass="cajaMontoTotal" size="20"
								onblur="UpdateInfoCliente()" /> <span id="indicatorNumIdent"
								style="display: none;"><img src="./images/indicator.gif" /></span>
							<s:textfield id="idnroidentificacion" name="idnroidentificacion"
								cssStyle="display:none" /> <ajax:autocomplete
								source="nroidentificacion" target="idnroidentificacion"
								baseUrl="./autocompletar.view?metodo=ObtenerNroIdent"
								className="autocomplete" indicator="indicatorNumIdent"
								minimumCharacters="1"
								parameters="idtipoidentificacion={idtipoidentificacion}"
								parser="new ResponseXmlToHtmlListParser()" /></td>
							<td align="left"></td>
							<td align="left" width="45%" class="label"></td>
							<td></td>
						</tr>
						<tr>
							<td></td>
							<td height="16" align="left">Nombre/Raz&oacute;n Social</td>
							<td colspan="3" align="left" class="label"><s:textfield
								name="strRazonSocial" cssClass="cajaMontoTotal" size="20" /></td>
							<td></td>
						</tr>
						<tr>
							<td></td>
							<td height="16" align="left">Direcci&oacute;n</td>
							<td colspan="3" align="left" class="label"><s:textfield
								name="strDireccionPrincipal" cssClass="cajaMontoTotal" size="50" />
							</td>
							<td></td>
						</tr>
						<tr>
							<td></td>
							<td height="16" align="left">Ubigeo</td>
							<td colspan="3" align="left" class="label">
							<form action=".">Departamento: <s:textfield
								id="departamento" name="departamento" cssClass="cajaMontoTotal"
								size="20" onkeypress="limpiarProvDist()" /> <span
								id="indicatorDep" style="display: none;"><img
								src="./images/indicator.gif" /></span> <s:textfield
								id="iddepartamento" name="iddepartamento"
								cssStyle="display:none" /> <ajax:autocomplete
								source="departamento" target="iddepartamento"
								baseUrl="./autocompletar.view?metodo=ObtenerDepartamento"
								className="autocomplete" indicator="indicatorDep"
								minimumCharacters="1" parser="new ResponseXmlToHtmlListParser()" />

							Provincia: <s:textfield id="provincia" name="provincia"
								cssClass="cajaMontoTotal" size="20" onkeypress="limpiarDist()" />
							<span id="indicatorProv" style="display: none;"><img
								src="./images/indicator.gif" /></span> <s:textfield id="idprovincia"
								name="idprovincia" cssStyle="display:none" /> <ajax:autocomplete
								source="provincia" parameters="iddepartamento={iddepartamento}"
								target="idprovincia"
								baseUrl="./autocompletar.view?metodo=ObtenerProvincias"
								className="autocomplete" indicator="indicatorProv"
								minimumCharacters="1" parser="new ResponseXmlToHtmlListParser()" />

							Distrito: <s:textfield id="distrito" name="distrito"
								cssClass="cajaMontoTotal" size="20" /> <span id="indicatorDist"
								style="display: none;"><img src="./images/indicator.gif" /></span>
							<s:textfield id="iddistrito" name="idprovincia"
								cssStyle="display:none" /> <ajax:autocomplete source="distrito"
								parameters="iddepartamento={iddepartamento},idprovincia={idprovincia}"
								target="iddistrito"
								baseUrl="./autocompletar.view?metodo=ObtenerDistrito"
								className="autocomplete" indicator="indicatorDist"
								minimumCharacters="1" parser="new ResponseXmlToHtmlListParser()" />
							</form>
							</td>
							<td></td>
						</tr>
						<tr>
							<td></td>
							<td height="16" align="left">Otra Direcci&oacute;n</td>
							<td colspan="3" align="left" class="label"><s:textfield
								name="objDD.strDireccionAlternativa" cssClass="cajaMontoTotal"
								size="50" /></td>
							<td></td>
						</tr>
						<tr>
							<td></td>
							<td height="16" align="left">Teléfono</td>
							<td colspan="3" align="left" class="label"><s:textfield
								name="objDD.strTelefono" cssClass="cajaMontoTotal" size="20" />
							</td>
							<td></td>
						</tr>
						<tr>
							<td></td>
							<td height="16" align="left">Correo Electrónico</td>
							<td colspan="3" align="left" class="label"><s:textfield
								name="objDD.strCorreo" cssClass="cajaMontoTotal" size="50" /></td>
							<td></td>
						</tr>
						<tr>
							<td colspan="6" height="15">
							<hr>
							</td>
						</tr>
						<tr>
							<td colspan="6" height="15" class="label">CONCESIONARIO</td>
						</tr>
						<tr>
							<td></td>
							<td height="16" align="left">RUC</td>
							<td colspan="3" align="left" class="label"><s:textfield
								name="strRUC" cssClass="cajaMontoTotal" size="20"
								onblur="UpdateInfoConcesionario()" /></td>
							<td></td>
						</tr>
						<tr>
							<td></td>
							<td height="16" align="left">Razón Social</td>
							<td colspan="3" class="label"><s:textfield id="correntista"
								name="correntista" cssClass="cajaMontoTotal" size="20" /> <s:textfield
								id="idcorrentista" name="idcorrentista" cssStyle="display:none" />
							</td>
							<td></td>
						</tr>
						<tr>
							<td></td>
							<td height="16" align="left">Dirección</td>
							<td colspan="3" align="left" class="label"><s:textfield
								name="strDireccion" cssClass="cajaMontoTotal" size="50" /></td>
							<td></td>
						</tr>
						<tr>
							<td></td>
							<td height="16" align="left">Correo Electrónico</td>
							<td colspan="3" align="left" class="label"><s:textfield
								name="strCorreo" cssClass="cajaMontoTotal" size="50" /></td>
							<td></td>
						</tr>
						<tr>
							<td></td>
							<td class="titulo">&nbsp;</td>
							<td colspan="4"></td>
						</tr>
					</table>
					</td>
				</tr>
			</table>
			</td>
		</tr>
		<tr>
			<td height="14" colspan="3"></td>
		</tr>
	</table>
</s:form>
</body>
</html>
