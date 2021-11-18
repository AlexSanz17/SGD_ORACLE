<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Tramite Documentario</title>
<link rel="stylesheet" type="text/css" href="./css/estilo.css">
<script language="javascript" src="./js/form.js"> </script>
<script language="Javascript" src="./js/general.js"></script>
<script language="JavaScript" src="./js/dtreeCarpeta.js"></script>
<link rel="stylesheet" type="text/css" media="all" href="./css/calendar-green.css">
<script type="text/javascript" src="./js/calendar.js"></script>
<script type="text/javascript" src="./js/calendar-es.js"></script>
<script type="text/javascript" src="./js/calendar-setup.js"></script>

<script language="JavaScript">
function Abrir_ventana (pagina) {
var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=800, height=700, top=50, left=200";
window.open(pagina,"",opciones);
}

function Abrir_pagina (pagina) {
var opciones="location=mainFrame";
window.open(pagina,"",opciones);
}

function regresar(){ 
   window.close() 
}
 function aceptar(){
    document.forms["frmCB"].submit();
    //window.close()
}
</script>

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"><style type="text/css">
<!--
body {
	background-color: #ffffff;
}
-->
</style>
<style>
	a, A:link, a:visited, a:active
		{color: #000000;text-decoration: none; font-family: Tahoma, Verdana; font-size: 11px}
	A:hover
		{color: #ff0000; text-decoration: none; font-family: Tahoma, Verdana; font-size: 11px}
</style>
<style TYPE="text/css">
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
</style>
</head>

<body topmargin="0" leftmargin="0" rigthmargin="0" >
  <s:form name="frmCB" action="doSaveBusqueda" method="POST">
  <table width="103%">
    <tr> 
      <td height="14" colspan="2"> </td>
    </tr>
    <tr> 
      <td height="13" colspan="2" class="header" ><div align="left">Modificar 
        Carpeta Expediente : <s:property value="objCB.nombrecarpeta"/></div></td>
    </tr>

    <tr> 
      <td height="13" colspan="2" class="header" ></td>
    </tr>


    <tr> 
      <td height="14" colspan="2">
	  <table width="85%"  border="1" align="center" bordercolor="#669BCD" bgcolor="#BFD9F1">
	   <tr>
       <td height="70">
	  <table width="98%" height="50" align="center" >
              <tr> 
                <td width="7%" ></td>
                <td height="5" colspan="3" ></td>
                <td width="11%" ></td>
                <td width="8%" ></td>
              </tr>
			  <tr> 
                <td ></td>
                <td height="24" colspan="4" align="center">
				</td>
                <td></td>
              </tr>
			  <tr> 
                <td ></td>
                <td height="18" colspan="3" align="left">Nombre Carpeta :</td>
				<s:textfield name="objCB.idcarpetabusqueda" value="%{objCB.idcarpetabusqueda}" cssStyle="display:none" />
				<s:textfield name="objCB.aviso" value="%{objCB.aviso}" cssStyle="display:none" />
                <td></td>
                <td></td>
              </tr>
              <tr> 
                <td ></td>
                <td height="24" colspan="4" align="left"><s:textfield name="objCB.nombrecarpeta" value="%{objCB.nombrecarpeta}" cssClass="cajaMontoTotal" size="40" /></td>
                <td></td>
              </tr>
              <tr>
			  <tr> 
                <td ></td>
                <td height="18" colspan="3" align="left">Tipo Documento :</td>
                <td></td>
                <td></td>
              </tr>
              <tr> 
                <td ></td>
                <td height="24" colspan="4" align="left"><s:textfield name="objCB.parametro1" value="%{objCB.parametro1}" cssClass="cajaMontoTotal" size="15" /></td>
                <td></td>
              </tr>
              <tr> 
                <td ></td>
                <td height="18" colspan="3" align="left">Nro Documento:</td>
                <td></td>
                <td></td>
              </tr>
              <tr> 
                <td ></td>
                <td height="24" colspan="4" align="left"><s:textfield name="objCB.parametro2" value="%{objCB.parametro2}" cssClass="cajaMontoTotal" maxLength="80" size="40" /></td>
                <td></td>
              </tr>
              <tr> 
                <td ></td>
                <td height="18" colspan="3" align="left">Asunto :</td>
                <td></td>
                <td></td>
              </tr>
              <tr> 
                <td ></td>
                <td height="24" colspan="4" align="left"><s:textfield name="objCB.parametro3" value="%{objCB.parametro3}" cssClass="cajaMontoTotal" maxLength="80" size="40" /></td>
                <td></td>
              </tr>
              <tr> 
                <td ></td>
                <td height="18" colspan="3" align="left">Destinatario :</td>
                <td></td>
                <td></td>
              </tr>
              <tr> 
                <td ></td>
                <td height="24" colspan="4" align="left"><s:textfield name="objCB.parametro4" value="%{objCB.parametro4}" cssClass="cajaMontoTotal" maxLength="80" size="40" /></td>
                <td></td>
              </tr>
			   <tr> 
                <td ></td>
                <td height="18" colspan="3" align="left">Propietario :</td>
                <td></td>
                <td></td>
              </tr>
              <tr> 
                <td ></td>
                <td height="24" colspan="4" align="left"><s:textfield name="objCB.parametro5" value="%{objCB.parametro5}" cssClass="cajaMontoTotal" maxLength="80" size="40" /></td>
                <td></td>
              </tr>
              <tr> 
                <td ></td>
                <td width="30%" height="18" align="left">De :</td>
                <td align="left">Hasta</td>
                <td align="left">&nbsp;</td>
                <td></td>
                <td></td>
              </tr>
              <tr> 
                <td ></td>
                <td height="24"  align="left">
                 <s:textfield id="fechainicial" name="objCB.parametrof1" value="%{objCB.parametrof1}" cssClass="cajaMontoTotal" maxLength="20" size="14" />
                 <input alt="Calendario" class="cajaFecha" id="lanzador1" value="..." type="button"></td>
                <td width="30%" height="24" align="left">
                 <s:textfield id="fechafinal" name="objCB.parametrof2" value="%{objCB.parametrof2}" cssClass="cajaMontoTotal" maxLength="20" size="14" />
                 <input alt="Calendario" class="cajaFecha" id="lanzador2" value="..." type="button"></td>
                <td width="14%" height="24" align="left">&nbsp;</td>
                <td height="24" align="left">&nbsp;</td>
                <td></td>
              </tr>
              <tr> 
                <td height="1" ></td>
              </tr>
            </table>
	</td>
      </tr>
  </table>
 </td>
 </tr>
	
<tr> 
   <td width="44%" height="14"  colspan="2"></td>
</tr>


  
	<tr> 
      <td colspan="2" align="center">
         <input type="button" name="busca" value="Guardar" class="button" onclick="aceptar()"/>  &nbsp;&nbsp;
		   
		 </td>
</tr>
	
</table>
</s:form>

<script type="text/javascript">
    Calendar.setup({
        inputField     :    "fechainicial",      // id del campo de texto
        ifFormat       :    "%Y-%m-%d",       // formato de la fecha, cuando se escriba en el campo de texto
        button         :    "lanzador1"   // el id del botón que lanzará el calendario
    });
	
	 Calendar.setup({
        inputField     :    "fechafinal",      // id del campo de texto
        ifFormat       :    "%Y-%m-%d",       // formato de la fecha, cuando se escriba en el campo de texto
        button         :    "lanzador2"   // el id del botón que lanzará el calendario
    });
	 
</script>

</body>
</html>
