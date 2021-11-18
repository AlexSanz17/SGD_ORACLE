<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="java.util.*" %>
<%@page import="com.btg.ositran.siged.domain.Numeracion" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
   <head>
      <title>Mantenimiento de Grupo de Procesos</title>
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
         List lstNumeracion = (List) request.getAttribute("lstNumeracion");
         String rol = (String) request.getSession().getAttribute("rol");
         String col = (String) request.getSession().getAttribute("columnas");
      %>

       <script language="JavaScript">
          //  var storeUsuario1 = new dojo.data.ItemFileReadStore({url: "autocompletarUsuarioFinal.action"});
            var storeAreaFinal = new dojo.data.ItemFileReadStore({url: "autocompletarAreaDatosFinal.action"});
        </script>

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
      <link href="./runtime/styles/system/aw.css" rel="stylesheet">

      <!-- grid format -->
      <style type="text/css">
         #myGrid {height: 350px; width: 1000px;}
         #myGrid .aw-row-selector {text-align: center}

         #myGrid .aw-column-1 {width: 150px;text-align: center;}
         #myGrid .aw-column-2 {width: 150px;text-align: center;}
         #myGrid .aw-column-3 {width: 200px;text-align: center;}
         #myGrid .aw-column-4 {width: 200px;text-align: center;}

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
            Numeracion objNum;

            for (int i = 0; i < lstNumeracion.size(); i++) {
               objNum = (Numeracion) lstNumeracion.get(i);
               %>["<%=objNum.getUnidad().getIdunidad()%>","<%=objNum.getTipodocumento().getIdtipodocumento()%>","<%=objNum.getUnidad().getNombre()%>","<%=objNum.getTipodocumento().getNombre()%>"],<%
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

         obj.setCellFormat([num, num, str, str]);
         //	provide cells and headers text
         obj.setCellText(mydata);
         obj.setHeaderText(myColumns);
         //	set number of rows/columns
         obj.setRowCount(<%=lstNumeracion.size()%>);
         obj.setColumnCount(4);

         //Definiendo que columnas es visible
         obj.setColumnIndices([2,3]);
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
            var idunidad=this.getCellText(0, row);
            var idtipodoc=this.getCellText(1, row);
            //alert("idunidad:"+idunidad+" idtipodoc:"+idtipodoc);
            if (idunidad!="" && idtipodoc != "") {
               A('#e1f3ff',idunidad,idtipodoc,'doViewNumeracion.action')
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

            function setArea(){
			  var area_ = dijit.byId("idArea_").getValue().substring(0,dijit.byId("idArea_").getValue().indexOf("-"));
			  var tipodoc_ = dijit.byId("idArea_").getValue().substring(dijit.byId("idArea_").getValue().indexOf("-") + 1, dijit.byId("idArea_").getValue().length);
			  var page_ = 'doViewNumeracion.action';
			  parent.frames["secondFrame"].location.href=page_+'?iIdUnidad='+area_+'&iIdTipodocumento='+tipodoc_;
            }

            function A(c,idunidad,idtipodoc,page)
            {
               //alert("valores=" + idunidad + "-" + idtipodoc);
               //alert(c + " " + i + " " + page);
               // if (ObjAnt == "row")
               //{ObjAnt = i;}
               // document.getElementById(ObjAnt).style.backgroundColor="#ffffff";
               //document.getElementById(i).style.backgroundColor=c;
               // ObjAnt=i;
               //interno.location.href=page+'?iIdDoc='+i;
               parent.frames["secondFrame"].location.href=page+'?iIdUnidad='+idunidad+'&iIdTipodocumento='+idtipodoc;
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
                      <table><tr><td align="right">  <a href="<s:url action="doViewNumeracion" />" target="secondFrame"><img alt="Crear Numeracion" border="0" src="images/nuevo2.GIF"/></a> </td>  <td align="left"> <a href="<s:url action="doViewNumeracion" />" target="secondFrame">Crear Numeracion</td></tr></table>
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
            <td height="20"colspan="4" class="titulo" width="100%">
               <table width="100%" border="0" height="20" align="left">
                  <tr>
                     <td bgcolor="#4481B8" width="100%" align="center" class="tituloRojo">Mantenimiento de Numeracion</td>
                     <td></td>
                  </tr>
               </table>
            </td>


            <td height="20" align="left" width="69px" colspan="1"> </td>
            <td height="20" align="left" colspan="1"> Area 	<select dojoType="dijit.form.FilteringSelect"
			                id="idArea_"
			                name="idArea_"
			                value="<s:property value='valArea_' />"
			                idAttr="id"
			                labelAttr="label"
			                style="width:280px;"
			                required="false"
							hasDownArrow="false"
			                searchAttr="label"
							onChange= "setArea"
			                store="storeAreaFinal"></select>
			</td>

         </tr>
         <tr>
            <td colspan="6">
               <script type="text/javascript">
                     //write grid html to the page
                     document.write(obj);
               </script>
            </td>
         </tr>
      </table>
   </body>
</html>
