<%@page import="java.text.*"%>
<%@page import="java.util.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.btg.ositran.siged.domain.Mensajeria"%>
<%@page import="org.ositran.utils.UsuarioMensajeria"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Tramite Documentario</title>
<link rel="stylesheet" type="text/css" href="css/estilo.css">
<script language="javascript" src="js/switchMenu.js"></script>
<script language="javascript" src="js/form.js"> </script>
<script language="Javascript" src="js/general.js"></script>
<link rel="stylesheet" type="text/css" media="all"
	href="css/calendar-green.css">
<script type="text/javascript" src="js/calendar.js"></script>
<script type="text/javascript" src="js/calendar-es.js"></script>
<script type="text/javascript" src="js/calendar-setup.js"></script>
<script type="text/javascript" src="js/si.files.js"></script>
<script type="text/javascript" src="js/sorttable.js"></script>
<script type="text/javascript" src="./runtime/lib/aw.js"></script>
<link rel="stylesheet" href="./runtime/styles/system/aw.css">

<!--
body {
	background-color: #ffffff;
}
-->

<STYLE TYPE="text/css">
<!--
.barra {
	scrollbar-3dlight-color: #CCCCCC;
	scrollbar-arrow-color: #568BBF;
	scrollbar-base-color: #C3D5E9;
	scrollbar-darkshadow-color: #666666;
	scrollbar-face-color: ;
	scrollbar-highlight-color: #669BCD;
	scrollbar-shadow-color: #999999;
}
-->
</STYLE>
<style type="text/css">
</style>
<s:if test="estado!=null">
	<s:url id="urlGrid" action="enviarGridmensajeria">
		<s:param name="estado" value="%{estado}" />
	</s:url>
</s:if>
<s:else>
	<s:url id="urlGrid" action="enviarGridmensajeria">
		<s:param name="estado" value="'A'" />
	</s:url>
</s:else>

<s:url id="urldoc" action="dofinddatos" />

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
        dojo.require("dojox.grid.cells.dijit");
        
    </script>

<script type="text/javascript">


	var jsonStore = new dojo.data.ItemFileWriteStore({url: "<s:property value='urlGrid' />"});
	var storeUsuarios;
	
	function seleccion(){
		var items = edGrid.selection.selectedIndex;
		alert("hola:"+ items);
		}


	function abrirsolicitud(){
        
		var item = edGrid.selection.selectedIndex;
		
		if (item!=null){
    		var val=edGrid.getItem(item).id;
    		A('#e1f3ff',val,'dofinddatos.action');		
		}

      }

	parent.frames["secondFrame"].location.href="blankmsg.html";
</script>

<script language="JavaScript">
       function A(c,i,page)
       { 
        	parent.frames["secondFrame"].location.href=page+'?Idmen='+i;
       }


</script>



<script language="javascript">
       var showMode = 'table-cell';
       if (document.all) showMode='block';

       function toggleVis(btn,valor)
       {
          alert(btn+"-"+valor);
       }

    </script>

<script language="javascript"> 

</script>
<script>
          
</script>

</head>
<body class="barra">
<form>

<table width="100%">
	<tr>
		<td height="4" colspan="6"></td>
	</tr>
	<tr>
		<td height="20" colspan="6" width="99%">
		<table width="99%" border="0" align="center">
			<td width="1%" height="14" rowspan="2"></td>
			<td width="44%" align="left"><font color="669BCD">Bienvenido
			:</font><font color="0099FF"><s:property value="#session.nombres" /></font></td>
			<td width="14%" rowspan="2"></td>
			<td width="39%" rowspan="2" align="right"></td>
			<td width="2%" rowspan="2"></td>
			<tr>
				<td align="left"><font color="0099FF"><a
					href="<s:url action="doLogout" />" target="_parent">Cerrar
				Sesi&oacute;n</a></font>
		</table>
		</td>
	</tr>

	<tr>
		<td height="4" colspan="6"></td>
	</tr>

	<tr>
		<td height="20" colspan="6" class="titulo" width="99%">
		<table width="20%" border="0" height="20" align="left">
			<tr>
				<s:set name="estadox" value="%{estado}" />
				<s:if test="%{#estadox!=null}">
					<s:if test='%{#estadox=="A"}'>
						<td bgcolor="#4481B8" width="100%" align="center"
							class="tituloRojo">Documentos Recibidos</td>
					</s:if>
					<s:if test='%{#estadox=="C"}'>
						<td bgcolor="#4481B8" width="100%" align="center"
							class="tituloRojo">Documentos Cerrados</td>
					</s:if>
				</s:if>
				<s:if test="%{#estadox==null}">
					<td bgcolor="#4481B8" width="100%" align="center"
						class="tituloRojo">Documentos Recibidos</td>
				</s:if>
			</tr>

		</table>
		</td>
	</tr>
	<tr>
		<td colspan="6">

		<table id="tb1" cellpadding="1" cellspacing="1" width="99%"
			align="center" class="tableForm" border="0">
			<tr>
				<td colspan="6">
				<div id="mensajeria">
				<table id='edGrid' jsId='edGrid' dojoType='dojox.grid.DataGrid'
					store='jsonStore' style='height: 150px; width: 1000px'
					onclick="abrirsolicitud()" canSort="noSort">
					<thead>
						<tr>
							<th width='20px' cellType='dojox.grid.cells.RowIndex'
								styles='text-align: center;'></th>
							<th field='id' width='20px' editable='false' hidden="true">id</th>
							<th field='label' width='130px' editable='false'>USUARIO</th>
							<th field='numinterno' width='120px' editable='false'>NRO.
							INTERNO</th>
							<th field='tipodoc' width='200px' editable='false'>TIPO DOC.</th>
							<th field='numdoc' width='120px' editable='false' hidden="true">NRO.
							DOC.</th>
							<th field='destinatario' width="250px">DESTINATARIO</th>
							<th field='direcciondestino' width="300px" hidden="true">DIRECCION</th>
							<th field='asunto' width='150px' editable='false' align="center">ASUNTO</th>
							<th field='estado' width='120px' editable='false' align="center">ESTADO</th>
							<th field='etapa' width='120px' editable='false' align="center">ETAPA</th>
							<th field='fecha' width='130px' editable='false' align="center">FECHA</th>
							<th field='idusu' width='100px' editable='false' hidden="true">idusu</th>
						</tr>
					</thead>

				</table>

				</div>
				</td>
			</tr>
		</table>
		</div>


		</td>
	</tr>


</table>
</form>


</body>

</html>
