package pages.tramite; package pages.tramite; package pages.tramite;

package pages.tramite;

<%@page import="java.util.*"%>
<%@page import="org.ositran.utils.ExpedientebusStor"%>

<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Busqueda de Datos</title>
<link rel="stylesheet" type="text/css" href="/siged/css/estilo.css">
<script language="javascript" src="/siged/js/switchMenu.js"></script>
<script language="javascript" src="/siged/js/form.js"> </script>
<script language="Javascript" src="/siged/js/general.js"></script>
<link rel="stylesheet" type="text/css" media="all"
	href="/siged/css/calendar-green.css">
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
<!-- Llenedo de la grid data -->
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

function nuevoDoc () { 
    // alert("idDocumento: "+idDocumento) ;

    //if(idDocumento!=0){
          // aca mostrar formulario  
          parent.location.href="/siged/doPlantilla_inicio.action"   ;

      //  }else {
          // alert("Por favor seleccione un Documento");
      //  } 
    }
</script>
<!-- ----------------------------------------- --------------------- -->


<script language="JavaScript">
       function Abrir_ventana (pagina) {
          var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=900, height=280, top=180, left=200";
          window.open(pagina,"",opciones);
       }

       function Abrir_pagina (pagina) {
          var opciones="location=mainFrame";
          window.open(pagina,"",opciones);
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

       function tabdataInit() {
          var tables = document.getElementsByTagName('table');
          for (var t=0; t<tables.length; t++) {
             if ('tabclass'==tables[t].className) {
                var rows = document.getElementsByTagName('tr');
                for (var i = 0; i < rows.length; i++) {
                   rows[i].onmouseover = function() {
                      this.className += ' hilite';
                   }
                   rows[i].onmouseout = function() {
                      this.className = this.className.replace('hilite', '');
                   }
                }
             }
          }
       }
       window.onload=tabdataInit;

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
    var firefox = document.getElementById&&!document.all; 
    var isdrag = false; 
    var x0 = 0,y0=0,tx=0,ty=0; 
    var anchoc=0;anchot=0; 
    var objon = null;tabla = null; 

    getTopPos = function(inputObj) 
    {         
        var returnValue = inputObj.offsetTop; 
        while((inputObj = inputObj.offsetParent) != null){ 
            if(inputObj.tagName!='HTML')returnValue += inputObj.offsetTop; 
        } 
        return returnValue; 
    }     
    getLeftPos = function(inputObj) 
    { 
        var returnValue = inputObj.offsetLeft; 
        while((inputObj = inputObj.offsetParent) != null){ 
            if(inputObj.tagName!='HTML')returnValue += inputObj.offsetLeft; 
        } 
        return returnValue; 
    } 
    function comienza(e){ 
        if(!firefox)e = event; 
        objon = (!firefox)?e.srcElement:e.target; 
        
      if(objon.id == "tcol1"||objon.id == "tcol2"||objon.id == "tcol3"||
         objon.id == "tcol4"||objon.id == "tcol5"||objon.id == "tcol6"){
            isdrag = true; 
            tabla = document.getElementById("tb1"); 
            x0 = e.clientX; y0 = e.clientY; 
            tx = getLeftPos(objon); ty = getTopPos(objon); 
            anchoc = objon.offsetWidth; 
            anchot = tabla.offsetWidth; 
        } 
    } 
    function moviendo(e){ 
        if(!firefox)e = event; 
        if(isdrag){ 
            ic=e.clientX-x0+anchoc; 
            it=e.clientX-x0+anchot; 
            if(ic >= 5){ 
                objon.style.width = ic + "px"; 
                window.status = ic + "-" + it; 
                tabla.style.width = it + "px"; 
            }else{ 

            } 
        } 
    } 
    function termina(e){ 
        isdrag = false; 
    } 
    </script>
<script>
          
        function CargarPopup()
        {
           var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=50, height=200, top=400, left=700";
           window.open("./pages/tramite/popupOcultarCol.jsp", "", opciones);

        }
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
/* ]]> */
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
<s:form action="dobusimplestor" method="post">
	<table width="100%">
		<tr>
			<td height="4" colspan="6"></td>
		</tr>
		<tr>
			<td height="4" colspan="6"></td>
		</tr>
		<tr>
			<td height="20" colspan="6" class="titulo" width="99%">
			<table width="25%" border="0" height="28" align="left">
				<tr>
					<td bgcolor="#4481B8" width="25%" align="center" class="tituloRojo">
					Busqueda Expediente Stor</td>
				</tr>
			</table>
			<table width="500" border="0" height="28" align="left">
				<tr>
					<td width="250"><s:textfield name="objEbs.exp_referencia" /><s:submit
						name="Submit" value="Buscar" cssClass="button" /></td>
					<td width="200">Busqueda Avanzada Stor<img align="absmiddle"
						onClick="location.href='./docBusqadvanstor.jsp'"
						src="/siged/images/search.gif" border="0" style="cursor: hand" />
					</td>
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
</s:form>
</body>
</html>
