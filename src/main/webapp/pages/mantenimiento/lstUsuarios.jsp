<?xml version="1.0" encoding="UTF-8" ?>
<%@ page import="java.util.*" %>
<%@ page import="com.btg.ositran.siged.domain.Usuario" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
   <head>
      <title>Mantenimiento de Usuarios</title>
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
      <%--link rel="stylesheet" type="text/css" media="all" href="css/calendar-green.css" />
      <script type="text/javascript" src="js/calendar.js"></script>
      <script type="text/javascript" src="js/calendar-es.js"></script>
      <script type="text/javascript" src="js/calendar-setup.js"></script>
      <script type="text/javascript" src="js/si.files.js"></script>
      <script type="text/javascript" src="js/sorttable.js"></script--%>

      <!-- ----------------------------------------- Librerias y configuracion de Grid --------------------- -->
      <%
         List lstUsuario = (List) request.getAttribute("lstUL");
         String rol = (String) request.getSession().getAttribute("rol");
         String col = (String) request.getSession().getAttribute("columnas");
         String mant = (String) request.getSession().getAttribute("mantenimiento");
      %>

	     <script language="JavaScript">
          //  var storeUsuario1 = new dojo.data.ItemFileReadStore({url: "autocompletarUsuarioFinal.action"});
            var storeUsuarioFinal = new dojo.data.ItemFileReadStore({url: "autocompletarUsuarioDatosFinal.action"});
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
      <link href="./runtime/styles/system/aw.css" rel="stylesheet" />

      <!-- grid format -->
      <style type="text/css">
         #myGrid {height: 350px; width: 600px;}
         #myGrid .aw-row-selector {text-align: center}

         #myGrid .aw-column-1 {width: 150px;text-align: center;}
         #myGrid .aw-column-2 {width: 150px;text-align: center;}
         #myGrid .aw-column-3 {width: 200px;text-align: center;}

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
            Usuario objU;

            for (int i = 0; i < lstUsuario.size(); i++) {
               objU = (Usuario)lstUsuario.get(i);
               %>["<%=objU.getIdusuario()%>","<%=objU.getNombres()%>","<%=objU.getApellidos()%>","<%=objU.getUsuario()%>"],<%
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

         obj.setCellFormat([str, str, str]);
         //	provide cells and headers text
         obj.setCellText(mydata);
         obj.setHeaderText(myColumns);
         //	set number of rows/columns
         obj.setRowCount(<%=lstUsuario.size()%>);
         obj.setColumnCount(3);

         //Definiendo que columnas es visible
         obj.setColumnIndices([1,2,3]);
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
               A('#e1f3ff',id,'doViewUsuario.action')
            }
            else
            {
               parent.frames["secondFrame"].location.href="secondFrame.jsp";
            }
         };
      </script>
      <!-- ----------------------------------------- --------------------- -->


      <script language="JavaScript">
	        function setUsuario(){
			   A('#e1f3ff',dijit.byId("idUsuario_").getValue(),'doViewUsuario.action')

			}
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
               parent.frames["secondFrame"].location.href=page+'?iIdUsuario='+i;
            }

            function B(c,i,page)
            {
               Abrir_ventana(page+'?iIdDoc='+i);
            }

      </script>



      <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
      <style type="text/css">
         <!--
         body {
            background-color: #ffffff;
         }
         -->

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
         <!--
         .style1 {color: #FFFFFF}
         .arrastrable{
            width:100px;
            overflow:hidden;
         }
         .blockOverlay{
			background-color:	#A1A1A1 !important;
			opacity:			0.6 !important;
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
                  <td width="0%" height="14" rowspan="2"> </td>
                  <td width="29%" align="left">
                     <font color="669BCD">Bienvenido </font><font color="0099FF"><s:property value="#session._USUARIO.nombres" /></font>
                  </td>
                  <td width="19%" rowspan="2"></td>
                  <td width="34%" rowspan="2" align="right">
                                     <table><tr><td align="right">
                               <a href="<s:url action="doViewUsuario" />" target="secondFrame"><img alt="Crear Usuario" border="0" src="images/nuevo2.GIF"/></a>

                      </td>  <td align="left">
                                  <a href="<s:url action="doViewUsuario" />" target="secondFrame">Crear Usuario</a>

              		 </td></tr></table>
                  </td>
                  <td width="16%" rowspan="2" align="right">
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
            <td height="20" colspan="4" class="titulo" width="100%">
               <table width="100%" border="0" height="20" align="left">
                  <tr>
                     <td bgcolor="#4481B8" width="100%" align="center" class="tituloRojo">Mantenimiento de Usuarios</td>
                     <td></td>
                  </tr>
               </table>
            </td>

			<td height="20" align="left" width="69px" colspan="1"> </td>
            <td height="20" align="left" colspan="1"> Usuario 	<select dojoType="dijit.form.FilteringSelect"
			                id="idUsuario_"
			                name="idUsuario_"
			                value="<s:property value='valUsuario_' />"
			                idAttr="id"
			                labelAttr="label"
			                style="width:280px;"
			                required="false"
							hasDownArrow="false"
			                searchAttr="label"
							onChange= "setUsuario"
			                store="storeUsuarioFinal"></select> </td>

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
		<a id="sincronizar" href="#">Sincronizar Usuarios con el Sistema de Seguridad</a>
		<div id="mensaje" style="display:none;">
			<h4>Se est&aacute;n sincronizando los usuarios del Sistema de Seguridad con el Siged</h4>
			<p>Por favor, espere</p>
			<img src="images/cargando.gif" alt="Cargando..." />
		</div>
		<br />
		<a id="guardar" href="#">Guardar usuarios en el LDAP</a>
		<div id="guardando" style="display:none;">
			<h4>Se est&aacute;n guardando los usuarios en el LDAP</h4>
			<p>Por favor, espere</p>
			<img src="images/cargando.gif" alt="Cargando..." />
		</div>
	</body>
</html>
