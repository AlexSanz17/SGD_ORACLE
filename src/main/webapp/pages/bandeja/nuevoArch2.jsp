<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://ajaxtags.org/tags/ajax" prefix="ajax"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Tramite Documentario</title>
<link rel="stylesheet" type="text/css" href="css/estilo.css">
<script language="javascript" src="js/form.js"> </script>
<script language="Javascript" src="js/general.js"></script>
<link rel="stylesheet" type="text/css" media="all" href="css/calendar-green.css">
<script type="text/javascript" src="js/calendar.js"></script>
<script type="text/javascript" src="js/calendar-es.js"></script>
<script type="text/javascript" src="js/calendar-setup.js"></script>

<script language="JavaScript">
function Abrir_ventana (pagina) {
var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=650, height=400, top=50, left=200";
window.open(pagina,"",opciones);
}

function Abrir_ventanaObservacion (pagina) {
var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=no, width=700, height=500, top=50, left=200";
window.open(pagina,"",opciones);
}

function Abrir_pagina (pagina) {
var opciones="location=mainFrame";
window.open(pagina,"",opciones);
}

function putTextOnTextBox(textToPut){
document.all.reciveTheText.value = textToPut;
}
function regresar(){ 
   history.back();
}

function cambiar(_valor){
  if(_valor=='T'){
	obj=document.getElementById("otrosDatos");
	obj.style.display = "block";
   }
	else{
		obj=document.getElementById("otrosDatos1");
		obj.style.display = "none";
		}

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

function enviar() {
   document.forms["frmNuevoDocUser"].submit();
}

function SubmitForm() {
   document.forms["frmNuevoDocUser"].submit();
}
</script>


<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<style type="text/css">
<!--
body {
	background-color: #ffffff;
}

.barra {
           scrollbar-3dlight-color:#CCCCCC;
           scrollbar-arrow-color:#568BBF;
           scrollbar-base-color:#C3D5E9;
           scrollbar-darkshadow-color:#666666;
           scrollbar-face-color:;
           scrollbar-highlight-color:#669BCD;
           scrollbar-shadow-color:#999999;
		   }
		   
 div.falso {
	position: absolute;
	top: -29px;
	left: 365px;
	z-index: 0;
	width: 14px;
	height: 3px;
 }
 
input.file {
 position: relative; 
 text-align: left;
 filter:alpha(opacity: 0); 
 opacity: 0; 
 z-index: 1; 
 top: -29px;
	left: 335px;
 }
		   
-->

</style>

</head>

<body  class="barra" topmargin="0" leftmargin="0" rigthmargin="0" > 
  <s:form name="frmNuevoDocUser" action="doNewDocUser" method="POST">
  
<table>
<tr>
<td width="29%" align="left">
<font color="669BCD">Bienvenido </font><font color="0099FF"><s:property value="#session.nombres" /></font>
</td>
</tr>
<tr>
<td align="left">

</td>
</tr>
</table>

<table width="100%">
  <tr> 
    <td height="14" colspan="3"> </td>
  </tr>

  <tr>
		<td height="20"colspan="3" class="titulo">
				<table width="99%" border="0" height="20" align="center" bgcolor="#A2C0DC">
					<tr>
					   
          <td align="left" class="titulo">Nuevo Documento</td>
					</tr>
				</table>
		</td>
	</tr>
  
  
  
  <tr align="center"> 
    <td width="1%" align="center">&nbsp;</td>
    <td width="99%" colspan="2" align="left">
    
	  <img src="images/enviar.bmp" border="0" onClick="enviar()" alt="Enviar"/>
	  <!--<img onClick="javascript:Abrir_ventanaObservacion('/siged/doAbrirVentanaObs')" src="images/enviar.bmp" border="0" alt="Enviar"/>-->
	  &nbsp;&nbsp;   <img src="images/impAlta.bmp" border="0" onClick="location.href='pages/bandeja/nuevoArch.html'" alt="Importancia Alta"/> 
	  &nbsp;&nbsp;  <img src="images/impBaja.bmp" border="0" onClick="location.href='pages/bandeja/nuevoArch.html'" alt="Importancia Baja"/> 
      &nbsp;&nbsp;
	  <img src="images/nuevoDoc.bmp" border="0" onClick="location.href='pages/bandeja/nuevoArch1.html'" alt="Nuevo Documento"/> 
      &nbsp;&nbsp;
	<div style="position:relative;"> 
          <input name="file" type="file" class="file" onChange="document.all().value = this.value" size="1">
          <div class="falso"> <img src="images/adjunto.bmp" align="left" border="0" alt="Adjunto"> 
          </div>
        </div>
    &nbsp;&nbsp; </tr>
  <tr> 
    <td height="14" colspan="3"> <table width="95%"  border="1" align="center" bordercolor="#669BCD" bgcolor="#BFD9F1">
        <tr> 
          <td height="75"> <table width="98%" height="97" align="center" >
              <tr> 
                <td width="2%" ></td>
                <td width="17%" height="5" ></td>
                <td width="21%" ></td>
                <td width="25%" ></td>
                <td width="33%" ></td>
                <td width="2%" ></td>
              </tr>
              <tr>
                  <td></td>
                  <td class="titulo">Datos del Expediente</td>
                  <td colspan="4"></td>
                </tr>
              <tr>
                  <td></td>
                  <td align="left">Nro Expediente</td>
                  <td colspan="3" class="label">
                     <s:textfield name="objDD.strReferencia" cssClass="cajaMontoTotal" size="20" />
                     <img onClick="javascript:Abrir_ventanaBuscar('/siged/doLoadSearchExpediente.action')" src="images/memos.gif" border="0"/>
                  </td>
                  <td></td>
                </tr>
                <tr>
                  <td ></td>
                  <td align="left">Proceso</td>
                  <td colspan="3" class="label">
                     <s:textfield id="proceso" name="proceso" cssClass="cajaMontoTotal" size="80" onblur="javascript:SubmitForm()" />
                     <span id="indicatorDep" style="display:none;"><img src="./images/indicator.gif"></span>
                     <s:textfield id="idproceso" name="idproceso" cssStyle="display:none" />
                      <ajax:autocomplete
                       source="proceso"
                       target="idproceso"
                       baseUrl="./autocompletar.view?metodo=ObtenerProceso"
                       className="autocomplete"
                       indicator="indicatorDep"
                       minimumCharacters="1"
                       parser="new ResponseXmlToHtmlListParser()" />
                  </td>
                  <td></td>
                </tr>
              <tr> 
                <td></td>
                <td height="16" align="left">Tipo Doc</td>
                <td align="left" width="21%" class="label">
				 <s:textfield id="tipodocumento" name="tipodocumento" cssClass="cajaMontoTotal" size="20" />
                                    <span id="indicatortipoDoc" style="display:none;"><img src="./images/indicator.gif" /></span>
                                    <s:textfield id="idtipodocumento" name="idtipodocumento" cssStyle="display:none" />
                                    <ajax:autocomplete
                                       source="tipodocumento"
                                       target="idtipodocumento"
                                       baseUrl="./autocompletar.view?metodo=ObtenerTipoDoc"
                                       className="autocomplete"
                                       indicator="indicatortipoDoc"
                                       minimumCharacters="1"
                                       parser="new ResponseXmlToHtmlListParser()" />
				 <img onClick="location.href=''" src="images/xx.gif" border="0"/>
				 </td>
                <td align="left" >&nbsp;</td>
                <td align="left" width="33%" class="label"></td>
                <td></td>
              </tr>
			  <tr> 
                <td></td>
                <td height="16" align="left">Proceso</td>
                <td align="left" width="21%" class="label">
                   <s:textfield id="proceso" name="proceso" cssClass="cajaMontoTotal" size="80" />
                     <span id="indicatorDep" style="display:none;"><img src="./images/indicator.gif"></span>
                     <s:textfield id="idproceso" name="idproceso" cssStyle="display:none" />
                      <ajax:autocomplete
                       source="proceso"
                       target="idproceso"
                       baseUrl="./autocompletar.view?metodo=ObtenerProceso"
                       className="autocomplete"
                       indicator="indicatorDep"
                       minimumCharacters="1"
                       parser="new ResponseXmlToHtmlListParser()" />
				  </td>
                <td align="left" >&nbsp;</td>
                <td align="left" width="33%" class="label"></td>
                <td></td>
              </tr>
			  <tr> 
                <td></td>
                <td height="16" align="left">Remitente</td>
                <td align="left" width="21%" class="label">
				 <input name="nombre" type="text" class="cajaMontoTotal" value="" size="20">
				  </td>
                <td align="left" >&nbsp;</td>
                <td align="left" width="33%" class="label"></td>
                <td></td>
              </tr>
              <tr> 
                <td></td>
                <td height="16" align="left">Destinatarios &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                <td colspan="3" align="left" class="label">
                   <s:textfield id="destinatario" name="destinatario" cssClass="cajaMontoTotal" size="50"/>
                  <span id="indicatorDest" style="display:none;"><img src="./images/indicator.gif"/></span>
                  <s:textfield id="iddestinatario" name="iddestinatario" cssStyle="display:none"/>
                  <ajax:autocomplete
                     source="destinatario"
                     target="iddestinatario"
                     baseUrl="./autocompletar.view?metodo=ObtenerDestinatario"
                     parameters="proceso={idproceso}"
                     className="autocomplete"
                     indicator="indicatorDest"
                     minimumCharacters="1"
                     parser="new ResponseXmlToHtmlListParser()" />
				<!--<img onClick="location.href=''" src="images/memos.gif" border="0"/>-->
            </td>
				<td align="left"></td>
              </tr>
			 
			  <tr> 
                <td ></td>
                <td height="16" align="left">Asunto</td>
                <td colspan="3" class="label">
                   <s:textfield name="objDD.strAsunto" cssClass="cajaMontoTotal" size="60" />
                      </td>
                <td></td>
              </tr>
              <tr> 
                <td ></td>
                  <td height="16" align="left">Contenido</td>
                <td colspan="3" class="label">
                   <s:textarea id="ta" name="ta" rows="32" cols="143" />
                </td>
                <td></td>
              </tr>
			  
			  <tr> 
                <td ></td>
                <td height="16" align="left">Atender antes de</td>
                <td colspan="3" class="label">
                   <s:textfield name="objDD.strFechaAtender" id="fechaatender" cssClass="cajaMontoTotal" size="25" />
                   <input alt="Calendario" class="cajaFecha" id="calfechaatender" value="..." type="button">
                </td>
                <td></td>
              </tr>
			   <tr> 
                <td ></td>
                  <td height="16" align="left">Requiere Firma </td>
                <td colspan="3" class="label">
				    <input name="chbfirma" type="checkbox" value="T" onClick="location.href='pages/bandeja/nuevoArchCaja.html'">
				</td>
                <td></td>
              </tr>
			  
			  <tr> 
                <td height="5" ></td>
              </tr>
            </table></td>
        </tr>
      </table></td>
  </tr>
  <tr> 
    <td height="14"  colspan="3"></td>
  </tr>
  
  
</table>
</s:form>
<script type="text/javascript">
         Calendar.setup({
            inputField     :    "fechaatender", //id del campo de texto
            ifFormat       :    "%Y-%m-%d",    //formato de la fecha, cuando se escriba en el campo de texto
            button         :    "calfechaatender"    //el id del botón que lanzará el calendario
         });
</script>
</body>
</html>
