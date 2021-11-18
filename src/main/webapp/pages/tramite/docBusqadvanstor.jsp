
<%@page import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="org.ositran.utils.Expedienfindadvance"%>
<%@page import="org.ositran.utils.ExpedientebusStor"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Tramite Documentario</title>
<link rel="stylesheet" type="text/css" href="/siged/css/estilo.css">
<script language="javascript" src="/siged/js/switchMenu.js"></script>
<script language="javascript" src="/siged/js/form.js"> </script>
<script language="Javascript" src="/siged/js/general.js"></script>
<link rel="stylesheet" type="text/css" media="all"
	href="./css/calendar-green.css">
<script type="text/javascript" src="/siged/js/calendar.js"></script>
<script type="text/javascript" src="/siged/js/calendar-es.js"></script>
<script type="text/javascript" src="/siged/js/calendar-setup.js"></script>
<script type="text/javascript" src="/siged/js/si.files.js"></script>
<script type="text/javascript" src="/siged/js/sorttable.js"></script>
<script type="text/javascript" src="/siged/runtime/lib/aw.js"></script>
<link rel="stylesheet" href="/siged/runtime/styles/system/aw.css">

<style type="text/css">
.aw-quirks * {
	box-sizing: border-box;
	-moz-box-sizing: border-box;
	-webkit-box-sizing: border-box;
}

body {
	font: 12px Tahoma
}
</style>
<style type="text/css">
#myGrid {
	height: 300px;
	width: 1000px;
}

#myGrid .aw-row-selector {
	text-align: center
}

#myGrid .aw-column-0 {
	width: 50px;
}

#myGrid .aw-column-1 {
	width: 100px;
}

#myGrid .aw-column-2 {
	width: 70px;
	text-align: center;
}

#myGrid .aw-column-3 {
	width: 50px;
	text-align: center;
}

#myGrid .aw-column-4 {
	width: 150px;
	text-align: center;
}

#myGrid .aw-column-5 {
	width: 100px;
	text-align: center;
}

#myGrid .aw-column-6 {
	width: 200px;
	text-align: center;
}

#myGrid .aw-column-7 {
	width: 100px;
	text-align: center;
}

#myGrid .aw-column-8 {
	width: 150px;
	text-align: center;
}

#myGrid .aw-column-9 {
	width: 50px;
	text-align: center;
}

#myGrid .aw-column-10 {
	width: 50px;
	text-align: center;
}

#myGrid .aw-column-11 {
	width: 100px;
	text-align: center;
}

#myGrid .aw-column-12 {
	width: 150px;
	text-align: center;
}

#myGrid .aw-column-13 {
	width: 150px;
	text-align: center;
}

#myGrid .aw-grid-cell {
	border-right: 1px solid threedlightshadow;
}

#myGrid .aw-grid-row {
	border-bottom: 1px solid threedlightshadow;
}

/* box model fix for strict doctypes, safari */
.aw-strict #myGrid .aw-grid-cell {
	padding-right: 3px;
}

.aw-strict #myGrid .aw-grid-row {
	padding-bottom: 3px;
}
</style>

<script type="text/javascript">
        
       <%
            List data =(List)request.getAttribute("Lisbc");

            ExpedientebusStor edl;

        %>

      	var myColumns;
		var mydata=[<%
                if (data!=null){
                for(int i=0;i<data.size();i++){

                edl=(ExpedientebusStor)data.get(i);

                %>["<%=edl.getIddocumento() %>","<%=edl.getExp_referencia() %>","<%=edl.getSuministro() %>","<%=edl.getPro_nombre() %>","<%=edl.getCn_rzsocial()%>"],<%

            }}
        %>
                myColumns=["id","N Expediente","Nro Suministro","Nro.Folios","Proceso","Concesionario"]
        ]


</script>

<script type="text/javascript">

   var idDocumento =0;
	//	create ActiveWidgets Grid javascript object
	var obj = new AW.UI.Grid;
	obj.setId("myGrid");

	//	define data formats
	var str = new AW.Formats.String;
	var num = new AW.Formats.Number;
    var dat = new AW.Formats.Date;

	obj.setCellFormat([num, str, str,str, str, str]);

	//	provide cells and headers text

	obj.setCellText(mydata);
	obj.setHeaderText(myColumns);

	//	set number of rows/columns
   <%if (data!=null){%>
	obj.setRowCount(<%=data.size()%>);
    <%}else{%>
    obj.setRowCount(0);
    <%}%>
	obj.setColumnCount(5);

   //Definiendo que columnas es visible

    obj.setSelectionMode("multi-row-marker");

    obj.onSelectedRowsChanged = function(arrayOfRowIndices){ window.status = arrayOfRowIndices;}

    obj.setColumnIndices([1,2,3,4,5]);

	//	enable row selectors
	obj.setSelectorVisible(true);
	obj.setSelectorText(function(i){return this.getRowPosition(i)+1});

	//	set headers width/height
	obj.setSelectorWidth(28);
	obj.setHeaderHeight(20);

	//	set row selection
	obj.setSelectionMode("single-row");

	//	set click action handler
	obj.onCellClicked = function(event, col, row)
	{
      var id=this.getCellText(0, row);
      idDocumento = id;
      A('#e1f3ff',id,'/siged/doViewDoc.action')

	};

</script>

<script language="JavaScript">
       function A(c,i,page)
       {
          parent.frames["secondFrame"].location.href=page+'?iIdDoc='+i;
       }

       function B(c,i,page)
       {
          Abrir_ventana(page+'?iIdDoc='+i);
       }
       var ObjAnt="row";

       function A(c,i,page)
       {
          parent.frames["secondFrame"].location.href=page+'?iIdDoc='+i;
       }

       function B(c,i,page)
       {
          Abrir_ventana(page+'?iIdDoc='+i);
       }
       function regresar(){ 
    	   history.back();
    	}
    </script>

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

</head>
<body>
<table>
	<tr>
		<td width="29%" align="left"><img src="images//regresar.bmp"
			border="0" onClick="regresar()" alt="Regresar" /></td>
	</tr>
</table>

<table width="100%">
	<tr>
	</tr>

	<tr>
		<td height="2" colspan="6"></td>
	</tr>

	<tr>
		<td height="20" colspan="6" class="titulo" width="99%">
		<table width="37%" border="0" height="20" align="left">
			<tr>
				<td bgcolor="#BFD9F1" width="49%" align="center" class="titulo">Busqueda
				Avanzada Stor</td>
				<td width="2%" align="center"></td>
		</table>
		</td>
	</tr>


	<!--////////////////////////////////////////-->
	<tr id="repHeader1">
		<td height="10" colspan="6"></td>
	</tr>


	<tr>
		<td height="14" colspan="6">
		<table width="100%" border="1" align="center" bordercolor="#669BCD"
			bgcolor="#BFD9F1">
			<tr>
				<td height="100"><s:form action="dobusavanstor" method="post">
					<table width="150%" align="center">
						<tr>
							<td width="0%"></td>
							<td width="10%" height="5"></td>
							<td width="75%"></td>
							<td width="1%"></td>
							<td width="1%"></td>
							<td width="1%"></td>
							<td width="11%"></td>

						</tr>
						<tr>
							<td></td>
							<td height="34" align="left">N&ordm; Expediente</td>
							<td>
							<table width="748">
								<tr>
									<td width="133" height="26"><input name="expediente"
										size="20" value=""></td>
									<td width="10"></td>
									<td width="74"><select name="select1">
										<option value="AND" selected>AND</option>
										<option value="OR">OR</option>
									</select></td>
									<td width="511">&nbsp;</td>
								</tr>
							</table>
							<td>
						</tr>
						<tr>
							<td></td>
							<td height="16" align="left">N&ordm; Sumnistro</td>
							<td colspan="5">
							<table width="216">
								<tr>
									<td width="120"><input name="sumnistro" size="20" value=""></td>
									<td width="19"></td>
									<td width="74"><select name="select4">
										<option value="AND" selected>AND</option>
										<option value="OR">OR</option>
									</select>
								</tr>
							</table>
							</td>
							<td width="0"></td>
						</tr>
						<tr>
							<td></td>
							<td height="16" align="left">Proceso</td>
							<td colspan="5">
							<table width="288">
								<tr>
									<td width="163"><input name="proceso" size="35" value=""></td>
									<td width="33"></td>
									<td width="76"><select name="select5">
										<option value="AND" selected>AND</option>
										<option value="OR">OR</option>
									</select></td>
								</tr>
							</table>
							</td>
							<td></td>
						</tr>
						<tr>
							<td></td>
							<td height="16" align="left">Concesionario&nbsp;&nbsp;</td>
							<td colspan="5">
							<table width="216">
								<tr>
									<td width="120"><input name="concesionaria" size="35"
										value=""></td>
									<td width="19"></td>
									<td width="74">&nbsp;</td>
								</tr>

							</table>
							<td><s:submit type="submit" value="Buscar..."
								cssClass="button" /></td>
						</tr>
						<tr>
							<td></td>
							<td colspan="2"></td>
						</tr>
						<tr>
							<td height="5"></td>
						</tr>
					</table>
				</s:form></td>
			</tr>
		</table>

		</td>
	</tr>
	<tr>
		<td colspan="6"><script type="text/javascript">
              // Escribimos la grid del active W.
              document.write(obj);
           </script></td>
	</tr>
</table>

</body>
</html>

