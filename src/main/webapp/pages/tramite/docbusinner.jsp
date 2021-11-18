
<%@page import="java.util.*"%>
<%@page import="org.ositran.utils.DocumentoList"%>

<%@ taglib prefix="s" uri="/struts-tags"%>

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

<!-- ----------------------------------------- Librerias y configuracion de Grid --------------------- -->

<!-- fix box model in firefox/safari/opera -->
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
	height: 250px;
	width: 1000px;
}

#myGrid .aw-row-selector {
	text-align: center
}

#myGrid .aw-column-1 {
	width: 100px;
}

#myGrid .aw-column-2 {
	width: 100px;
	text-align: center;
}

#myGrid .aw-column-3 {
	width: 150px;
	text-align: center;
}

#myGrid .aw-column-4 {
	width: 300px;
	text-align: center;
}

#myGrid .aw-column-5 {
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


<!-- Llenedo de la grid data -->
<script type="text/javascript">


      var myColumns;
      var mydata=[];

</script>

<!-- Construtyendo el objeto de la datagrid -->

<script type="text/javascript">

   var idDocumento =0;
	//	create ActiveWidgets Grid javascript object
	var obj = new AW.UI.Grid;
	obj.setId("myGrid");

	//	define data formats
	var str = new AW.Formats.String;
	var num = new AW.Formats.Number;
    var dat = new AW.Formats.Date;

	obj.setCellFormat([num, str, str,str, str, dat]);

	//	provide cells and headers text

	obj.setCellText(mydata);
	obj.setHeaderText(myColumns);

	//	set number of rows/columns
	obj.setRowCount(10);
	obj.setColumnCount(5);

   //Definiendo que columnas es visible
    obj.setColumnIndices([0,1,2,3,4,5,6]);
	//	enable row selectors
	obj.setSelectorVisible(true);
	obj.setSelectorText(function(i){return this.getRowPosition(i)+1});

	//	set headers width/height
	obj.setSelectorWidth(28);
	obj.setHeaderHeight(20);

    
	//	set click action handler
	obj.onCellClicked = function(event, col, row)
	{
      var id=this.getCellText(0, row);
        //Sirve para atrapar el evento de un templete de una columna
       //var  t=this.getCellTemplate(1,row).getControlProperty("value");
       // alert(t);

      if(id!="" && col!=1)
       {    idDocumento = id;
            A('#e1f3ff',id,'/siged/doViewDoc.action')
       }
       else
       {
             idDocumento = 0 ;  
             parent.frames["secondFrame"].location.href="secondFrame.jsp";
       }
	};

    
</script>

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<style type="text/css">
<!--
body {
	background-color: #ffffff;
}
-->
</style>
<STYLE TYPE="text/css">
<!--
.barra {
	scrollbar-3dlight-color: #CCCCCC;
	scrollbar-arrow-color: #568BBF;
	scrollbar-base-color: #C3D5E9;
	scrollbar-darkshadow-color: #666666;
	scrollbar-highlight-color: #669BCD;
	scrollbar-shadow-color: #999999;
}
-->
</STYLE>
<style type="text/css" title="text/css">
/* <![CDATA[ */
.SI-FILES-STYLIZED label.cabinet {
	width: 16px;
	height: 16px;
	background: url(images/adjuntos.bmp) 0 0 no-repeat;
	display: block;
	overflow: hidden;
	cursor: pointer;
}

.SI-FILES-STYLIZED label.cabinet input.file {
	position: relative;
	height: 100%;
	width: auto;
	opacity: 0;
	-moz-opacity: 0;
}
</style>

<style type="text/css">
<!--
.style1 {
	color: #FFFFFF
}

.arrastrable {
	width: 100px;
	overflow: hidden;
}
-->
</style>

</head>
<body class="barra">
<script type="text/javascript">
              // Escribimos la grid del active W.
              document.write(obj);
           </script>
</body>
</html>
