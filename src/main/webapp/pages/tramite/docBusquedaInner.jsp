<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="java.text.*"%>
<%@page import="java.util.*"%>
<%@page import="org.ositran.utils.Expedienfindadvance"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>B&uacute;squeda de Datos</title>
<style type="text/css">
@import "js/dojo/dijit/themes/soria/soria.css"; 
@import "css/grid.css";
@import "css/estilo.css";
.empty {
	color: #A0A0A0;
}
</style>
<script type="text/javascript" src="js/dojo/dojo/dojo.js" djConfig="parseOnLoad:true, isDebug: false"></script>
		<script type="text/javascript" src="js/dojo/dojox/widget/PlaceholderMenuItem.js"></script>
<script type="text/javascript">
		  dojo.require("dijit.Dialog");
		  dojo.require("dijit.ProgressBar");
		</script>
<!--  
<link rel="stylesheet" type="text/css" href="css/estilo.css" />-->
<script type="text/javascript" src="js/switchMenu.js"></script>
<script type="text/javascript" src="js/form.js"></script>
<script type="text/javascript" src="js/general.js"></script>
<link rel="stylesheet" type="text/css" media="all"	href="css/calendar-green.css" />
<script type="text/javascript" src="js/calendar.js"></script>
<script type="text/javascript" src="js/calendar-es.js"></script>
<script type="text/javascript" src="js/calendar-setup.js"></script>
<script type="text/javascript" src="js/si.files.js"></script>
<script type="text/javascript" src="js/sorttable.js"></script>
<script type="text/javascript" src="./runtime/lib/aw.js"></script>
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/jquery-defaultvalue.js"></script>
<link rel="stylesheet" href="./runtime/styles/system/aw.css" />
<%
            List data =(List)request.getAttribute("Lstinner");
        	int idProceso=0;
        %>
<style type="text/css">
.aw-quirks * {
	box-sizing: border-box;
	-moz-box-sizing: border-box;
	-webkit-box-sizing: border-box;
}

	body {font: 12px Tahoma}
</style>

<style type="text/css">
             
        
#myGrid {
	height: 250px;
	width: 99% !important;
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
	width: 120px;
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

#myGrid .aw-column-14 {
	width: 50px;
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
		var mydata=[<%
        Expedienfindadvance edl;
            Date datFecha;
            DateFormat formatFecha = new  SimpleDateFormat("dd/MM/yyyy") ;
            
            DateFormat formatHora = DateFormat.getTimeInstance(DateFormat.SHORT);
            String Strfecha;
            String strFechaBD;
            String Strfechor;
            for(int i=0;i<data.size();i++){

                edl=(Expedienfindadvance)data.get(i);
                datFecha=edl.getDoc_fchcreacio();
                Strfecha=formatFecha.format(datFecha);
                strFechaBD = formatHora.format(datFecha);
                
                Strfechor=Strfecha+" "+strFechaBD;
                
                %>["<%=edl.getIddocumento()%>","<%=edl.getExp_referencia()%>","<%=edl.getTd_nombre()%>","<%=edl.getDoc_nrdoc()%>","<%=Strfechor%>","<%=edl.getPro_nombre()%>","<%=edl.getU_nombre()%>","<%= (edl.getCn_rzsocial()!=null?edl.getCn_rzsocial().replace("\n"," "):"") %>","<%= (edl.getCli_rzsocial()!=null?edl.getCli_rzsocial().replace("\n"," "):edl.getCli_nombres()+" "+edl.getCli_apePaterno()) %>","<%=edl.getIdproceso()%>"],<%
                

            }
        %>
            myColumns=["id","Nro Expediente","Tipo Doc.","Nro Doc.","Fch. Creacion","Proceso","Area","Concesionario","Cliente","id"]
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
	obj.setRowCount(<%=data.size()%>);
	obj.setColumnCount(5);

   //Definiendo que columnas es visible

    obj.setSelectionMode("multi-row-marker");

    obj.onSelectedRowsChanged = function(arrayOfRowIndices){ window.status = arrayOfRowIndices;}

    obj.setColumnIndices([1,2,3,4,5,6,7,8,9]);
   
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
      var idoc=this.getCellText(9, row);      
      A('#e1f3ff',id,'dopermisosbusqueda.action',idoc)

	};

function nuevoDoc () { 
    // alert("idDocumento: "+idDocumento) ;

    //if(idDocumento!=0){
          // aca mostrar formulario  
          parent.location.href="doPlantilla_inicio.action"   ;

      //  }else {
          // alert("Por favor seleccione un Documento");
      //  } 
    }
</script>
<!-- ----------------------------------------- --------------------- -->


<script type="text/javascript">
       function Abrir_ventana (pagina) {
          var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=900, height=280, top=180, left=200";
          window.open(pagina,"",opciones);
       }

       function Abrir_pagina (pagina) {
          var opciones="location=mainFrame";
          window.open(pagina,"",opciones);
       }

       var ObjAnt="row";

       function A(c,i,page,iddoc)
       {
           	
		   page='doViewDoc.action';
           parent.frames["secondFrame"].location.href=page+'?iIdDoc='+i+'&iIdPro='+iddoc+'&avisopermiso=1';
        
       }

       function B(c,i,page)
       {
          Abrir_ventana(page+'?iIdDoc='+i);
       }

    </script>



<script type="text/javascript">
       var showMode = 'table-cell';
       if (document.all) showMode='block';

       function toggleVis(btn,valor)
       {
          alert(btn+"-"+valor);
       }

    </script>

<script type="text/javascript"> 
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
<script type="text/javascript">
          
        function CargarPopup()
        {
           var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=50, height=200, top=400, left=700";
           window.open("./pages/tramite/popupOcultarCol.jsp", "", opciones);

        }
        function regresar(){ 
        	   history.back();
        	}
        $(document).ready(function(){
			$("input[name='sParametroBusqueda']").defaultValue("Buscar...");
			$("#doSearchinner").submit(function(){
				 var val = $(".empty").length<=0;
				 if(val){
					 $("#dlgProgresBar").show();
				 }				 
				return val; 
			});
		});
		</script>
<style type="text/css">
<!--
body {
	background-color: #ffffff;
}
.empty {
	color: #A0A0A0;
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
<style type="text/css">
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
<body class="soria" >
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

<s:hidden value="qq" name="busqueda" />

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
				Informacion de Busqueda</td>
			</tr>
		</table>       
                          
		<s:form action="doSearchinner" method="post">
			<fieldset style="border: 0; float: left; padding: 0; margin: 0;">
			<s:textfield name="sParametroBusqueda" size="12" />
			<button type="submit" title="Buscar"><img
				src="images/buscar1.png" alt="Buscar" title="Buscar" /></button>
			</fieldset>
		</s:form> <img alt="B&uacute;squeda Avanzada" title="B&uacute;squeda Avanzada"
			onclick="javascript:location.href='pages/tramite/docRecibidosBusq.jsp';"
			src="images/search.gif"
			style="cursor: pointer; float: left; margin-top: 1px;" /></td>
	</tr>
	<tr>
		<td colspan="6"><script type="text/javascript">
              // Escribimos la grid del active W.
              document.write(obj);
           </script></td>
	</tr>
</table>
<%--@ include file="../util/progressBar.jsp" --%>  
</body>
</html>