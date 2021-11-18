<%@page import="java.text.*"%>
<%@page import="java.util.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.btg.ositran.siged.domain.Auditoria"%>
<%@page import="org.ositran.utils.UsuarioMensajeria"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<title>Tramite Documentario</title>
<link rel="stylesheet" type="text/css" href="css/estilo.css">


<%
    
    List data=(List)request.getAttribute("listauditor");
   
 %>

<s:url id="urlGrid" action="enviarGridauditoria" />

<style type="text/css">
@import "js/dojo/dijit/themes/soria/soria.css";

@import "js/dojo/dojox/grid/resources/soriaGrid.css";

@import "css/grid.css";
</style>
<script type="text/javascript" src="js/dojo/dojo/dojo.js"
	djConfig="parseOnLoad:true, isDebug: false"></script>
<script type="text/javascript" src="js/dojo/dijit/dijit.js"
	djConfig="parseOnLoad:true, isDebug: false"></script>
<script type="text/javascript">
      	dojo.require("dojo.rpc.JsonService");
      	dojo.require("dijit.Dialog");
      	dojo.require("dijit.form.TextBox");
      	dojo.require("dijit.form.Button");
      	dojo.require("dijit.form.DateTextBox");
          
    	dojo.require("dijit.form.FilteringSelect" );
      	dojo.require("dojo.data.ItemFileWriteStore");
      	dojo.require("dojo.data.ItemFileReadStore");
        dojo.require("dojox.grid.DataGrid");
    </script>




<script language="JavaScript">

var jsonStore = new dojo.data.ItemFileWriteStore({url: "<s:property value='urlGrid' />"});



var ObjAnt="f0";

function A(c,i,page)
{
document.getElementById(ObjAnt).style.backgroundColor="#ffffff";
document.getElementById(i).style.backgroundColor=c;
ObjAnt=i;
interno.location.href=page;
}

</script>

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">





<script language="JavaScript">
      
       function A(c,i,per,page)
       { 	
          parent.frames["secondFrame"].location.href=page+'?Idmen='+i;
       }

       function B(c,i,page)
       {
          Abrir_ventana(page+'?Idmen='+i);
       }

</script>


</head>
<body class="soria">
<form>
<table>
	<tr>
		<td width="29%" align="left"><font color="669BCD">Bienvenido
		</font><font color="0099FF"><s:property value="#session.nombres" /></font></td>
	</tr>
	<tr>
		<td align="left"><font color="0099FF"><a
			href="<s:url action="doLogout" />" target="_parent">Cerrar
		Sesi&oacute;n</a></font></td>
	</tr>
</table>
<table width="100%">
	<tr>
		<td height="4" colspan="6"></td>
	</tr>
	<tr>
		<td height="20" colspan="6" width="99%"></td>
	</tr>
	<tr>
		<td height="4" colspan="6"></td>
	</tr>
	<tr>
		<td height="20" colspan="6" class="titulo" width="99%">
		<table width="20%" border="0" height="20" align="left">
			<tr>
				<td bgcolor="#4481B8" width="100%" align="center" class="tituloRojo">Auditoria</td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td colspan="6">
		<table id="tablaMenuExpandido" width="100%" border="1" height="100"
			cellpadding="0" cellspacing="0">
			<tr>
				<td width="100%" style="height: auto" valign="top">


				<div style="width: 1000px; height: 400px; overflow: auto;">

				<table id='edGrid' jsId='edGrid' dojoType='dojox.grid.DataGrid'
					store='jsonStore' style='height: 250px; width: 1000px'>
					<thead>
						<tr>
							<th width='20px' cellType='dojox.grid.cells.RowIndex'
								styles='text-align: center;'></th>
							<th field='id' width='20px' editable='false' hidden="true">id</th>
							<th field='label' width='130px' editable='false'>TIPO</th>
							<th field='modulo' width='130px' editable='false'>MODULO</th>
							<th field='opcion' width='130px' editable='false'>OPCION</th>
							<th field='campo' width='130px' editable='false' hidden="true">CAMPO</th>
							<th field='valor' width="250">VALOR</th>
							<th field='old' width="250" hidden="true">VALOR2</th>
							<th field='fecha' width='85px' editable='false' align="center">FECHAS</th>
							<th field='usuario' width='150px' editable='false'>USUARIO</th>
						</tr>
					</thead>

				</table>

				</div>

				</td>
			</tr>
		</table>
		</td>
	</tr>


</table>
</form>


</body>
</html>
