<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="java.util.*" %>
<%@page import="com.btg.ositran.siged.domain.Distrito" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
   <head>
      <title>Mantenimiento de Distritos</title>
      <link rel="stylesheet" type="text/css" href="css/estilo.css" />
      <script language="javascript" src="js/switchMenu.js"></script>
      <script language="javascript" src="js/form.js"> </script>
      <script language="Javascript" src="js/general.js"></script>
      <link rel="stylesheet" type="text/css" media="all" href="css/calendar-green.css" />
      <script type="text/javascript" src="js/calendar.js"></script>
      <script type="text/javascript" src="js/calendar-es.js"></script>
      <script type="text/javascript" src="js/calendar-setup.js"></script>
      <script type="text/javascript" src="js/si.files.js"></script>
      <script type="text/javascript" src="js/sorttable.js"></script>

      <!-- ----------------------------------------- Librerias y configuracion de Grid --------------------- -->
      <%
    List lstDistrito = (List) request.getAttribute("lstDistrito");
    String col = (String) request.getSession().getAttribute("columnas");
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

      <!-- include links to the script and stylesheet files -->
      <script src="./runtime/lib/aw.js" type="text/javascript"></script>
      <link href="./runtime/styles/system/aw.css" rel="stylesheet" />

      <!-- grid format -->
      <style type="text/css">
         #myGrid {height: 350px; width: 1000px;}
         #myGrid .aw-row-selector {text-align: center}

         #myGrid .aw-column-1 {width: 150px;text-align: center;}
         #myGrid .aw-column-2 {width: 150px;text-align: center;}

         #myGrid .aw-grid-cell {border-right: 1px solid threedlightshadow;}
         #myGrid .aw-grid-row {border-bottom: 1px solid threedlightshadow;}

         /* box model fix for strict doctypes, safari */
         .aw-strict #myGrid .aw-grid-cell {padding-right: 3px;}
         .aw-strict #myGrid .aw-grid-row {padding-bottom: 3px;}
      </style>
      <!-- Llenedo de la grid data -->
      <script type="text/javascript">
         var myColumns;
         var mydata=[<%
         Distrito objDistrito;

    for (int i = 0; i < lstDistrito.size(); i++) {
       objDistrito = (Distrito) lstDistrito.get(i);
               %>["<%=objDistrito.getIddistrito()%>","<%=objDistrito.getNombre()%>"],<%
    }
         %>]

               myColumns=[<%=col%>]
      </script>

      <!-- Construtyendo el objeto de la datagrid -->
      <script type="text/javascript">
            //Create ActiveWidgets Grid javascript object
            var obj = new AW.UI.Grid;
            obj.setId("myGrid");

            //	define data formats
            var str = new AW.Formats.String;
            var num = new AW.Formats.Number;

            obj.setCellFormat([num, str]);
            //	provide cells and headers text
            obj.setCellText(mydata);
            obj.setHeaderText(myColumns);
            //	set number of rows/columns
            obj.setRowCount(<%=lstDistrito.size()%>);
            obj.setColumnCount(2);

            //Definiendo que columnas es visible
            obj.setColumnIndices([1]);
            //Enable row selectors
            obj.setSelectorVisible(true);
            obj.setSelectorText(function(i){return this.getRowPosition(i)+1});
            //Set headers width/height
            obj.setSelectorWidth(28);
            obj.setHeaderHeight(20);

            //Set row selection
            obj.setSelectionMode("single-row");

            //Set click action handler
            obj.onCellClicked = function(event, col, row)
            {
               var id=this.getCellText(0, row);
               //alert("valor:"+this.getCellText(0, row));
               if (id!="")
               {
                  A('#e1f3ff',id,'doViewDistrito.action')
               }
               else
               {
                  parent.frames["secondFrame"].location.href="secondFrame.jsp";
               }
            };
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
               //alert(c + " " + i + " " + page);
               // if (ObjAnt == "row")
               //{ObjAnt = i;}
               // document.getElementById(ObjAnt).style.backgroundColor="#ffffff";
               //document.getElementById(i).style.backgroundColor=c;
               // ObjAnt=i;
               //interno.location.href=page+'?iIdDoc='+i;
               parent.frames["secondFrame"].location.href=page+'?iIdDistrito='+i;
            }

            function B(c,i,page)
            {
               //alert(c + " " + i + " " + page);
               //if (ObjAnt == "row")
               //{ObjAnt = i;}
               //document.getElementById(ObjAnt).style.backgroundColor="#ffffff";
               //document.getElementById(i).style.backgroundColor=c;
               //ObjAnt=i;
               //interno.location.href=page+'?iIdDoc='+i;
               Abrir_ventana(page+'?iIdDoc='+i);
            }


            /*function A(c,i)
{
document.getElementById(i).style.backgroundColor=c;
}

function overmouse()
{
color: #D3E1EE;
font-family: Arial, Helvetica, sans-serif;
font-size: 11px;
TEXT-INDENT: 0px;
}
function outMouse()
{
color: #FFFFFF;
font-family: Arial, Helvetica, sans-serif;
font-size: 11px;
TEXT-INDENT: 0px;

}*/

      </script>

      <%--script type="text/javascript">

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
      </script>--%>

      <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
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
            scrollbar-3dlight-color:#CCCCCC;
            scrollbar-arrow-color:#568BBF;
            scrollbar-base-color:#C3D5E9;
            scrollbar-darkshadow-color:#666666;
            scrollbar-face-color:;
            scrollbar-highlight-color:#669BCD;
            scrollbar-shadow-color:#999999;
         }

         -->

      </STYLE>
      <style type="text/css" title="text/css">
         /* <![CDATA[ */

         .SI-FILES-STYLIZED label.cabinet
         {
            width: 16px;
            height: 16px;
            background: url(images/adjuntos.bmp) 0 0 no-repeat;

            display: block;
            overflow: hidden;
            cursor: pointer;
         }

         .SI-FILES-STYLIZED label.cabinet input.file
         {
            position: relative;
            height: 100%;
            width: auto;
            opacity: 0;
            -moz-opacity: 0;
            filter:progid:DXImageTransform.Microsoft.Alpha(opacity=0);
         }

         /* ]]> */
      </style>

      <style type="text/css">
         <!--
         .style1 {color: #FFFFFF}
         .arrastrable{
            width:100px;
            overflow:hidden;
         }
         -->
      </style>

   </head>
   <body class="barra">
      <table width="100%">
         <tr>
            <td height="4"  colspan="6"></td>
         </tr>
         <tr>
            <td height="20" colspan="6" width="99%">
               <table width="99%" border="0" align="center">

                  <td width="29%" align="left">
                     <font color="669BCD">Bienvenido </font><font color="0099FF"><s:property value="#session._USUARIO.nombres" /></font>
                  </td>
                  <td width="15%" rowspan="2"></td>
                  <td width="14%" rowspan="2" align="right">
                      <table><tr><td align="right">  <a href="<s:url action="doViewDistrito" />" target="secondFrame"><img alt="Crear Distrito" border="0" src="images/nuevo2.GIF"/></a> </td>  <td align="left"> <a href="<s:url action="doViewDistrito" />" target="secondFrame">Crear Distrito</td></tr></table>
                  </td>
                  <td width="15%" rowspan="2" align="right">
                                  </td>
                  <td width="2%" rowspan="2"></td>
                  <tr>
                  <td align="left">
                     <font color="0099FF"><a href="<s:url action="doLogout" />" target="_parent">Cerrar Sesi&oacute;n</a></font>
                  </td>
               </table>
            </td>
         </tr>
         <tr>
            <td height="4"  colspan="6"></td>
         </tr>
         <tr>
            <td height="20"colspan="6" class="titulo" width="99%">
               <table width="70%" border="0" height="20" align="left">
                  <tr>
                     <td bgcolor="#4481B8" width="30%" align="center" class="tituloRojo">Mantenimiento de Distritos</td>
                     <td></td>
                  </tr>
               </table>
            </td>
         </tr>
         <tr>
            <td colspan="6">
               <script type="text/javascript">
                  document.write(obj);
               </script>
            </td>
         </tr>
      </table>
   </body>
</html>
