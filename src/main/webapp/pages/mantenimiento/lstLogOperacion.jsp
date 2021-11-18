<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.btg.ositran.siged.domain.LogOperacion"%>
<%@ page import="java.util.*" %>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="com.btg.ositran.siged.domain.Usuario" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
   <head>
      <title>Consulta de Log Operaci&oacute;n</title>
      <link rel="stylesheet" type="text/css" href="css/estilo.css" />



      <link type="text/css" rel="stylesheet" href="css/styleSiged.css" />
  	  <link type="text/css" rel="stylesheet" href="js/dojobuild/css/styleDojo.css" />
      <script type="text/javascript" src="js/dojo/dojo/dojo.js" djConfig="parseOnLoad:true, isDebug: false"></script>
      <script type="text/javascript">
         dojo.require("dijit.Dialog");
         dojo.require("dijit.form.Button");
         dojo.require("dijit.form.DateTextBox");
         dojo.require("dijit.form.FilteringSelect");
         dojo.require("dijit.form.Form");
         dojo.require("dijit.form.TextBox");
         dojo.require("dijit.form.ValidationTextBox");
         dojo.require("dojo.data.ItemFileWriteStore");
         dojo.require("dojo.data.ItemFileReadStore");
         dojo.require("dojo.rpc.JsonService");
         dojo.require("dojox.grid.DataGrid");
      </script>
      <script type="text/javascript" src="js/jquery.js"></script>
      <script type="text/javascript" src="js/dojo/js/commons.js"></script>
      <script type="text/javascript" src="js/siged/siged.js"></script>
      <script type="text/javascript" src="js/siged/siged.array.js"></script>
      <script type="text/javascript" src="js/siged/siged.commons.js"></script>
      <script type="text/javascript" src="js/siged/siged.string.js"></script>
      <script type="text/javascript" src="js/jquery.js"></script>
      <script type="text/javascript" src="js/jquery-validate.js"></script>




      <script language="javascript" src="js/switchMenu.js"></script>
      <script language="javascript" src="js/form.js"> </script>
      <script language="Javascript" src="js/general.js"></script>
      <script type="text/javascript" src="js/jquery.js"></script>
      <script type="text/javascript" src="js/jquery-blockUI.js"></script>
      <script type="text/javascript" src="js/sincronizar.js"></script>


      <!-- ----------------------------------------- Librerias y configuracion de Grid --------------------- -->
      <%
         List data = (List) request.getAttribute("lstLogOperacion");
         String col = (String) request.getSession().getAttribute("columnas");
		  int contador = 0;
      %>

	     <script language="JavaScript">
            var storeUsuarioFinal = new dojo.data.ItemFileReadStore({url: "autocompletarUsuarioDatosFinal.action"});
            var storeTipoDocumentoFinal = new dojo.data.ItemFileReadStore({url: "autocompletarAllTipoDocumento.action"});
			function SearchSubmit() {
				if(dijit.byId("idUsuario_").getValue().trim()=='' && dijit.byId("idTipoDocumento_").getValue()=='' && document.getElementById("numeroDocumento").value==''){
				   alert("Debe ingresar un criterio de busqueda");
				   return;
				}else{
				   document.forms["FrmLogOperacion"].submit();
				}
            }
         </script>


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
<s:form name="FrmLogOperacion" action="doBuscarLogOperacion" method="POST">
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
<div style="overflow:auto;max-height:800px;">
  <table width="990"  border="1" align="center" bordercolor="#669BCD" bgcolor="#BFD9F1">
    <tr>
	  <td colspan="3" align="center">
	     <strong>CONSULTA DE LOG</strong>
	  </td>
	</tr>
  	<tr>

		<td height="20" width="350px" align="left" colspan="1"><strong> Tipo de Documento:</strong>	<select dojoType="dijit.form.FilteringSelect"
			                id="idTipoDocumento_"
			                name="idTipoDocumento_"
			                value="<s:property value='valTipoDocumento_' />"
			                idAttr="id"
			                labelAttr="label"
			                style="width:240px;background-color:white;"
			                required="false"
							hasDownArrow="false"
			                searchAttr="label"
			                store="storeTipoDocumentoFinal"></select> </td>

			<td width="250px"><strong>Nro. Documento:</strong> <s:textfield id="numeroDocumento" name="numeroDocumento"
								cssClass="cajaMontoTotal" /></td>
		<td width="100">  <button dojoType="dijit.form.Button" type="button" name="button" onClick="SearchSubmit();" style="font-family:verdana,arial,helvetica sans-serif; font-size:8pt;  background:#4271AD; color:#fff; border-width:1px; border-style:solid; CURSOR:hand" > Buscar</button> </td>
	</tr>
	<tr>

	    <td height="20" align="left" colspan="1" width="200">
          <strong> Usuario: </strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<select dojoType="dijit.form.FilteringSelect"
			                id="idUsuario_"
			                name="idUsuario_"
			                value="<s:property value='valUsuario_' />"
			                idAttr="id"
			                labelAttr="label"
			                style="width:240px;background-color:white;"
			                required="false"
							hasDownArrow="false"
			                searchAttr="label"
			                store="storeUsuarioFinal"></select> </td>


	</tr>

  </table>
  <table width="990"  border="1" align="center" bordercolor="#669BCD" bgcolor="#BFD9F1">
	<tr>
		<td width="25" align="center"><strong>Nro</strong></td>
		<td width="200" align="center"><strong>Documento</strong></td>
		<td width="150" align="center"><strong>Archivo</strong></td>
		<td width="160" align="center"><strong>Opci&oacute;n</strong></td>
		<td width="170" align="center"><strong>Usuario</strong></td>
        <td width="120" align="center"><strong>Fecha</strong></td>
	</tr>
	<%

        if (data!=null){
           LogOperacion l = null;
		   contador = data.size();
    %>
    <% for(int i=0;i<data.size();i++){
           l = (LogOperacion)data.get(i);
    	%>
    <tr>

		<td align="center"><%=i+1%></td>
		<td align="left"> <%=l.getDocumento()%> </td>
		<td align="left"> <%=l.getNombrefile()%> </td>
		<td align="center"> <%=l.getDesOpcion()%> </td>
        <td align="center"><%=l.getUsuario()%></td>
        <td align="center"><%=new SimpleDateFormat("dd-MM-yyyy HH:mm").format(l.getFechaoperacion()) %></td>
	</tr>
	<%}%>
    <%} %>


</table>
</div>
</s:form>

<div style="overflow:auto;max-height:990px;">

<table width="990"   align="center" >
	<tr>
		<td width="25" align="center"><strong>Registros (<%=contador%>)</strong></td>
	</tr>
</table>
</div>
</body>
</html>
