<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="java.text.*"%>
<%@page import="java.util.*"%>
<%@page import="org.ositran.utils.DocumentoList"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Tr&aacute;mite Documentario</title>
<link rel="stylesheet" type="text/css" href="css/estilo.css" />
<script language="javascript" src="js/switchMenu.js"></script>
<script language="javascript" src="js/form.js"> </script>
<script language="Javascript" src="js/general.js"></script>
<link rel="stylesheet" type="text/css" media="all"
	href="css/calendar-green.css" />
<script type="text/javascript" src="js/calendar.js"></script>
<script type="text/javascript" src="js/calendar-es.js"></script>
<script type="text/javascript" src="js/calendar-setup.js"></script>
<script type="text/javascript" src="js/si.files.js"></script>
<script type="text/javascript" src="js/sorttable.js"></script>
<script type="text/javascript" src="./runtime/lib/aw.js"></script>
<link rel="stylesheet" href="./runtime/styles/system/aw.css"
	type="text/css" />

<!-- ----------------------------------------- Librerias y configuracion de Grid --------------------- -->
<%
    List<DocumentoList> data = (List<DocumentoList>) request.getAttribute("lstDL");
    String rol = (String) request.getSession().getAttribute("rol");
    String col = (String) request.getSession().getAttribute("columnas");
    Map<String,Integer> mapRecurso = (Map<String,Integer>) request.getSession().getAttribute("_RECURSO");
   %>

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
<s:if test="#session._RECURSO.UsuFinGrd">
	<style type="text/css">
#myGrid {
	height: 350px;
	width: 1000px;
}

#myGrid .aw-row-selector {
	text-align: center
}

#myGrid .aw-column-0 {
	width: 10px;
}

#myGrid .aw-column-1 {
	width: 20px;
}

#myGrid .aw-column-2 {
	width: 10px;
	text-align: center;
}

#myGrid .aw-column-3 {
	width: 20px;
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
</s:if>
<s:else>
	<style type="text/css">
#myGrid {
	height: 350px;
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

#myGrid .aw-column-6 {
	width: 50px;
	text-align: center;
}

#myGrid .aw-column-7 {
	width: 100px;
	text-align: center;
}

#myGrid .aw-column-8 {
	width: 190px;
	text-align: center;
}

#myGrid .aw-column-9 {
	width: 100px;
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
</s:else>


<!-- Llenado de la grid data -->
<script type="text/javascript">


         var myColumns;
         var mydata=[<%

    Date datFecha;
    Date datHoy = Calendar.getInstance().getTime();
    DateFormat formatFecha = DateFormat.getDateInstance(DateFormat.MEDIUM);
    DateFormat formatHora = DateFormat.getTimeInstance(DateFormat.SHORT);
    String strFechaBD;

    for (DocumentoList dl:data) {
       datFecha = dl.getStrFecha();

       if (formatFecha.format(datFecha).equals(formatFecha.format(datHoy))) {
          strFechaBD = formatHora.format(datFecha);
       } else {
    	   //Por Germán Enríquez, para que se muestre la hora con la fecha
          strFechaBD = formatFecha.format(datFecha)+" "+formatHora.format(datFecha);
       }

       if (mapRecurso.get("MPGrd") == 1 || mapRecurso.get("QASGrd") == 1) {
          %>["<%=dl.getIIdDocumento()%>","<%=dl.getStrRemitente()%>","<%=dl.getStrAsunto()%>","<%=strFechaBD%>" ],<%
       } else if (mapRecurso.get("DigGrd") == 1) {
          %>["<%=dl.getIIdDocumento()%>","<%=dl.getStrRemitente()%>","<%=dl.getStrNroDocumento()%>","<%=dl.getStrAsunto()%>","<%=strFechaBD%>"],<%
       } else if (mapRecurso.get("UsuFinGrd") == 1) {
          %>["<%=dl.getIIdDocumento()%>","","<img src='images/bolita.gif' border='0'/>","<img src='images/alta.bmp' border='0'/>","<%=dl.getStrRemitente()%>","<%= dl.getStrAsunto()%>","<%= dl.getStrCorrentista()%>","<%= dl.getStrNroDocumento()%>","<%=strFechaBD%>","<img src='images/clic.gif'/>","<%=dl.getExpediente().getProceso().getTipoproceso().getNombre()%> "],<%
       } else if (mapRecurso.get("UsuSasGrd") == 1) {
          %>["<%=dl.getIIdDocumento()%>","","<img src='images/bolita.gif' border='0'/>","<img src='images/alta.bmp' border='0'/>","<%=dl.getStrRemitente()%>","<%= dl.getExpediente().getIdetapa().getDescripcion()%>","<%=dl.getSAccion()%>","<%= dl.getStrCorrentista()%>","<%= dl.getStrNroDocumento()%>","<%=dl.getStrFecha()%>","<img src='images/clic.gif'/>","<%=dl.getExpediente().getProceso().getTipoproceso().getNombre()%> "],<%
       }
    }
    %>];

    myColumns=[<%=col%>];
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

            obj.setCellFormat([num,str,str,str,str,dat]);

            //	provide cells and headers text

            obj.setCellText(mydata);
            obj.setHeaderText(myColumns);

            //	set number of rows/columns
            obj.setRowCount(<%=data.size()%>);
            obj.setColumnCount(5);

            //Definiendo que columnas es visible
   <%
    if (mapRecurso.get("UsuFinGrd") == 1 || mapRecurso.get("UsuSasGrd") == 1) {
      %>
            var chk = new AW.Templates.Checkbox;

            //chk.setEvent("onclick", myClickHandler);

            obj.setCellTemplate(chk, 1); // column-1 as checkbox
            obj.setColumnIndices([1,2,3,4,5,6,7,8,9]);
      <%   } else if (mapRecurso.get("MPGrd") == 1 || mapRecurso.get("QASGrd") == 1) {
      %>
            obj.setColumnIndices([1,2,3]);
      <%   } else if (mapRecurso.get("DigGrd") == 1) {
      %>
            obj.setColumnIndices([1,2,3,4]);
      <%   }
 %>
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
          //Sirve para atrapar el evento de un templete de una columna
          //var  t=this.getCellTemplate(1,row).getControlProperty("value");

          <s:if test="#session._RECURSO.UsuFinGrd || #session._RECURSO.UsuSasGrd">
          if (col != 1) {
             idDocumento = id;
             A('#e1f3ff',id,'/siged/doViewDoc.action');
          }
          else {
             var estadochk=this.getCellTemplate(1,row).getControlProperty("value");
             var tipoProceso=trim(this.getCellText(10, row));

             if(estadochk==true && tipoProceso=="<s:property value='@org.ositran.utils.Constantes@TIPO_PROCESO_ANTIFLUJO' />" ) {
                this.getCellTemplate(1,row).setControlProperty("value",false);
             }

             idDocumento = 0 ;
             parent.frames["secondFrame"].location.href="secondFrame.jsp";
          }
          </s:if>
          <s:else>
             idDocumento = id;
             A('#e1f3ff',id,'/siged/doViewDoc.action');
          </s:else>
          
       };

       //   function myClickHandler(event){
       //        alert(row);
       //    }

       function trim(cadena)
       {
          for(i=0; i<cadena.length; )
          {
             if(cadena.charAt(i)==" ")
                cadena=cadena.substring(i+1, cadena.length);
             else
                break;
          }

          for(i=cadena.length-1; i>=0; i=cadena.length-1)
          {
             if(cadena.charAt(i)==" ")
                cadena=cadena.substring(0,i);
             else
                break;
          }

          return cadena;
       }

       function GuardarMasivamente()
       {
          var total=<%=data.size()%>;
          var paramt="";
          var i=0;
          var estado;
          var bandera=false;
          for(i=0;i<total;i++)
          {
             var id=obj.getCellText(0,i);

             estado=obj.getCellTemplate(1,i).getControlProperty("value")

             if((trim(obj.getCellText(10,i))=="Flujo" && estado!=false)||(trim(obj.getCellText(10,i))=="Flujo" && estado!=""))
             {
                paramt=paramt+"id"+i+"="+id+"|";
                bandera=true;
             }
          }
          //alert("/siged/doAprobarUSERMasivo.action?ids="+paramt+"&total="+i);
          if(i!=0 && bandera==true)
          {
             if (confirm('¿Estas seguro de Aprobar Masivamente?'))
             {
                document.location="/siged/doAprobarUSERMasivo.action?ids="+paramt+"&total="+i;
             }

          }
          else{
             alert("Debe Seleccionar al menos uno");
          }
       }

    function nuevoDoc () { parent.location.href="/siged/NuevoDocumento_inicio.action"   ; }

    function verSeguimiento () { parent.location.href="/siged/Seguimiento_mostrarInicio.action"   ; }

    function nuevoDocSas(){ parent.location.href="/siged/CargarframeSAS.action"}

    function SeleccionarTodo()
    {
          var total=<%=data.size()%>;

          for(j=0;j<total;j++)
          {
             if(trim(obj.getCellText(10,j))=="Flujo")
             {
                obj.getCellTemplate(1,j).setControlProperty("value",true);
             }
          }
    }

    function DeseleccionarTodo()
    {
          var total=<%=data.size()%>;
          for(i=0;i<total;i++){  obj.getCellTemplate(1,i).setControlProperty("value",false); }
    }


		</script>
<!-- ----------------------------------------- --------------------- -->


<script type="text/javaScript">
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
               /*
          cells = document.getElementsByName('t'+btn);
          mode = valor ? showMode : 'none';
          for(j = 0; j < cells.length; j++) cells[j].style.display = mode;
                */
               alert(btn+"-"+valor);
        <%
    int ncol = ((Integer) request.getSession().getAttribute("nrocolumnas")).intValue();
    String salida = "";

            %>
                     var tempCol={}

         <%
    for (int i = 1; i < ncol; i++) {
       if (rol.equals("user")) {
       } else {
           %>
                    if('<%=i%>'!=btn)
                    {
                       tempCol[<%=i%>]=<%=i%>;
                    }
           <%
               }

               %>obj.setColumnIndices(tempCol);<%
    }
        %>
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
            //document.onmouseup = termina;
            //document.onmousemove = moviendo;
            //document.onmousedown=comienza;
		</script>
<script>

            function CargarPopup()
            {
               var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=50, height=200, top=400, left=700";
               window.open("./pages/tramite/popupOcultarCol.jsp", "", opciones);

            }
		</script>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<style type="text/css">
body {
	background-color: #ffffff;
}
</style>
<style type="text/css">
.barra {
	scrollbar-3dlight-color: #CCCCCC;
	scrollbar-arrow-color: #568BBF;
	scrollbar-base-color: #C3D5E9;
	scrollbar-darkshadow-color: #666666;
	scrollbar-face-color: ;
	scrollbar-highlight-color: #669BCD;
	scrollbar-shadow-color: #999999;
}
</style>
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
	filter: progid : DXImageTransform.Microsoft.Alpha ( opacity = 0 );
}
/* ]]> */
</style>
<style type="text/css">
.style1 {
	color: #FFFFFF;
}

.arrastrable {
	width: 100px;
	overflow: hidden;
}
</style>
</head>
<body class="barra">
<table width="100%">
	<tr>
		<td height="4" colspan="6"></td>
	</tr>
	<tr>
		<td height="20" colspan="6" width="99%"><s:form
			action="doSearchinner" method="post">
			<table width="98%" border="0" align="center">
				<tr>
					<td width="0%" height="14" rowspan="2"></td>
					<td width="29%" align="left"><font color="669BCD">Bienvenido
					</font> <font color="0099FF"><s:property value="#session.nombres" /></font>
					</td>
					<td width="10%" rowspan="2"></td>
					<td width="40%" rowspan="2" align="right"><s:if
						test="#session._RECURSO.UsuFinBtnNueExp == 1">
						<a
							href="<s:url action="inicio"><s:param name="sTipoFrame">nuevoexp</s:param></s:url>"
							target="_parent"><img alt="Crear Expediente" border="0"
							src="images/botonNuevoExp.gif" /></a>
					</s:if> <s:if test="#session._RECURSO.UsuFinBtnLis == 1">
						<a href="<s:url action="goFrameLoadListaArray" />"
							target="_parent"><img alt="Listas" border="0"
							src="images/lista.gif" /></a>
					</s:if> <s:if test="#session._RECURSO.UsuFinBtnSeg == 1">
						<img onclick="verSeguimiento()" alt="Seguimiento" name="date"
							id="campo_fecha1" src="images/calendario.bmp"
							style="cursor: pointer" />&nbsp;&nbsp;
									</s:if> <s:if test="#session._RECURSO.UsuFinBtnNueDocPri == 1">
						<img onclick="nuevoDoc()" src="images/botonNuevo.gif"
							style="cursor: pointer" alt="Nuevo Documento" />
					</s:if> <s:if test="#session._RECURSO.nvodocsas == 1">
						<img onclick="nuevoDocSas()" src="images/botonNuevo.gif"
							style="cursor: pointer" alt="Nuevo Documento" />
					</s:if></td>
					<td width="18%" rowspan="2" align="right"><s:if
						test="#session._RECURSO.UsuFinBtnBus == 1">
						<s:textfield name="objEfa.exp_referencia" size="12" />
						<s:submit value="Buscar" cssClass="button" />
					</s:if></td>
					<td width="18%" rowspan="2" align="right"><s:if
						test="#session._RECURSO.UsuFinBtnBus == 1">
							Busqueda Avanzada...<img alt="Busqueda Avanzada"
							align="absmiddle"
							onclick="location.href='pages/tramite/docRecibidosBusq.jsp'"
							src="images/search.gif" border="0" style="cursor: hand" />
					</s:if></td>
					<td width="15%" rowspan="2" align="right"><s:if
						test="#session._RECURSO.MPBtnNueDocPri == 1">
						<a
							href="<s:url action="inicio"><s:param name="sTipoFrame">nuevodoc</s:param></s:url>"
							target="_parent"><img alt="Crear Documento" border="0"
							src="images/botonNuevo.gif" /></a>
					</s:if></td>
					<td width="2%" rowspan="2"></td>
				</tr>
				<tr>
					<td align="left"><font color="0099FF"> <a
						href="<s:url action="doLogout" />" target="_parent">Cerrar
					Sesi&oacute;n</a> </font></td>
				</tr>
			</table>
		</s:form></td>
	</tr>
	<tr>
		<td height="4" colspan="6"></td>
	</tr>
	<tr>
		<td height="20" colspan="6" class="titulo" width="99%">
		<table width="70%" border="0" align="left">
			<tr>
				<td bgcolor="#4481B8" width="30%" align="center" class="tituloRojo">
				<s:if test="#session._RECURSO.UsuFinGrd == 1">Documentos Recibidos</s:if>
				<s:else>Documentos Registrados</s:else></td>
				<td><!--
                &nbsp;&nbsp;<input type="button" value="Control de columnas" onclick="CargarPopup()" class="button">
                        --></td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td colspan="6">
		<%if ((Integer)mapRecurso.get("UsuFinGrd") == 1) {%> <input
			name="button" type="button" class="button"
			onclick="GuardarMasivamente();" value="Aprobar" /> <input
			name="button" type="button" class="button"
			onclick="SeleccionarTodo();" value="Marcar Todos" /> <input
			name="button" type="button" class="button"
			onclick="DeseleccionarTodo();" value="Desmarcar Todos" /> <br />
		<%}%> <script type="text/javascript">document.write(obj);</script></td>
	</tr>
</table>
</body>
</html>
