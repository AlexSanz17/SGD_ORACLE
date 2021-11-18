<%@page import="java.util.*" %>
<%@page import="com.btg.ositran.siged.domain.Region" %>
<%@page import="com.btg.ositran.siged.domain.DiaFestivo" %>
<%@page  import="org.ositran.utils.Busdiafestivo" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://ajaxtags.org/tags/ajax" prefix="ajax"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Tramite Documentario</title>
<link rel="stylesheet" type="text/css" href="css/estilo.css">
<script language="javascript" src="js/form.js"> </script>
<script language="Javascript" src="js/general.js"></script>
<script language="JavaScript" src="js/dtreeCarpeta.js"></script>
<link rel="stylesheet" type="text/css" media="all" href="css/calendar-green.css">
<script type="text/javascript" src="js/calendar.js"></script>
<script type="text/javascript" src="js/calendar-es.js"></script>
<script type="text/javascript" src="js/calendar-setup.js"></script>

<script type="text/javascript" src="js/prototype-1.4.0.js"></script>
<script type="text/javascript" src="js/scriptaculous.js"></script>
<script type="text/javascript" src="js/overlibmws.js"></script>
<script type="text/javascript" src="js/ajaxtags-1.2-beta2.js"></script>
<link rel="stylesheet" type="text/css" href="css/ajaxtags.css" />
<link rel="stylesheet" type="text/css" href="css/displaytag.css" />

<script type="text/javascript">

</script>

<script type="text/javascript">
         
</script>


      <script language="JavaScript">
         
      </script>

      <script type="text/javascript">

               </script>

<%
     List data =(List)request.getAttribute("LisBDf");
    //LisBDf
    String nombreregion="";
    if (data!=null){
    	if(data.size()>0){
    	Busdiafestivo bdf;
    	bdf=(Busdiafestivo)data.get(0);
    	nombreregion=bdf.getNom_region();
    	}
    }	
%>
<script language="JavaScript">

function Abrir_ventana (pagina) {
var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=800, height=700, top=50, left=200";
window.open(pagina,"",opciones);
}

function Abrir_pagina (pagina) {
var opciones="location=mainFrame";
window.open(pagina,"",opciones);
}

function aceptar(){
   document.forms["Frmsdf"].submit();

}
function regresar(){ 
   window.close() 
}
function eliminar(id,nom){ 
	 	valor=confirm("Desea Eliminar esta Fecha")
		
		if (valor==true){
						
			location.href="/siged/dodeleteDiafestivo.action"+"?iddiafestivo="+id+"&nomregion="+nom;
		}
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

.Estilo1 {color: #FFFFFF}


</style>
</head>


<body topmargin="0" leftmargin="0" rigthmargin="0" > 
<s:form name="Frmsdf" action="dobusDiafestivo" method="POST">
<table>
<tr><td>&nbsp;</td></tr>
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
<div style="overflow:auto;max-height:600px;">
  <table width="650"  border="1" align="center" bordercolor="#669BCD" bgcolor="#BFD9F1">
  	<tr>
		<td width="50">Region:</td>
		<td width="150"><s:textfield id="region" name="region" cssClass="cajaMontoTotal" size="20" />
                <span id="indicatorregion" style="display:none;"><img src="images/indicator.gif" /></span>
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
		<td width="400"><s:submit name="Submit" value="Buscar" cssClass="button"/></td>
	</tr>
  </table>
  <table width="650"  border="1" align="center" bordercolor="#669BCD" bgcolor="#BFD9F1">
	<tr>
		<td width="50" align="center"><strong><img src="images/bolitaRoja.gif" border="0"/></strong></td>
		<td width="150" align="center"><strong>Region</strong></td>
		<td width="150" align="center"><strong>Fecha</strong></td>
        <td width="300" align="center"><strong>Motivo</strong></td>
	</tr>
	<%
        if (data!=null){
        	if(data.size()>0){
        Busdiafestivo edl;
        String fecha;
    %>
    <% for(int i=0;i<data.size();i++){
         edl=(Busdiafestivo)data.get(i);
         fecha=edl.getDia_festiva()+"/"+edl.getMes_festiva()+"/"+edl.getAnio_festiva(); %>
    <tr>
		<input type="hidden" value="<%=edl.getNom_region()%>" name="nomregion" />
		<td align="center"><img src="images/ed_delete.gif" border="0" onclick="eliminar('<%=edl.getIddiafestivo()%>','<%=edl.getNom_region() %>')" /></td>
		<td align="center"><%=edl.getNom_region()%></td>
        <td align="center"><strong><%=fecha%></strong></td>
        <td align="center"><%=edl.getMotivo_festivo()%></td>
	</tr>
	<%}%>
    <%} }%>
</table>
    </div>
</s:form>
</body>
</html>
