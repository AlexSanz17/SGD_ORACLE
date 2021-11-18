<%@page import="java.util.*" %>
<%@page import="com.btg.ositran.siged.domain.Usuario" %>
<%@page import="org.ositran.utils.Expedienfindadvance" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
   <head>
      <title>Tramite Documentario</title>
      <link rel="stylesheet" type="text/css" href="css/estilo.css">
      <script language="javascript" src="js/switchMenu.js"></script>
      <script language="javascript" src="js/form.js"> </script>
      <script language="Javascript" src="js/general.js"></script>
      <link rel="stylesheet" type="text/css" media="all" href="css/calendar-green.css">
      <script type="text/javascript" src="js/calendar.js"></script>
      <script type="text/javascript" src="js/calendar-es.js"></script>
      <script type="text/javascript" src="js/calendar-setup.js"></script>
      <script type="text/javascript" src="js/si.files.js"></script>
      <script type="text/javascript" src="js/sorttable.js"></script>
      <script type="text/javascript" src="runtime/lib/aw.js"></script>
      <link rel="stylesheet" href="runtime/styles/system/aw.css">

      <%
      String rol = (String) request.getSession().getAttribute("rol");
      String col = (String) request.getSession().getAttribute("columnas");
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

         #myGrid {height:300px; width:1000px;}
         #myGrid .aw-row-selector {text-align: center}

         #myGrid .aw-column-0 {width: 50px; }
         #myGrid .aw-column-1 {width: 100px; }
         #myGrid .aw-column-2 {width: 70px;text-align: center;}
         #myGrid .aw-column-3 {width: 120px;text-align: center;}
         #myGrid .aw-column-4 {width: 150px;text-align: center;}
         #myGrid .aw-column-5 {width: 100px;text-align: center;}
         #myGrid .aw-column-6 {width: 200px;text-align: center;}
         #myGrid .aw-column-7 {width: 100px;text-align: center;}
         #myGrid .aw-column-8 {width: 150px;text-align: center;}
         #myGrid .aw-column-9 {width: 50px;text-align: center;}
         #myGrid .aw-column-10 {width: 50px;text-align: center;}
         #myGrid .aw-column-11 {width: 100px;text-align: center;}
         #myGrid .aw-column-12 {width: 150px;text-align: center;}
         #myGrid .aw-column-13 {width: 150px;text-align: center;}



         #myGrid .aw-grid-cell {border-right: 1px solid threedlightshadow;}
         #myGrid .aw-grid-row {border-bottom: 1px solid threedlightshadow;}

         /* box model fix for strict doctypes, safari */
         .aw-strict #myGrid .aw-grid-cell {padding-right: 3px;}
         .aw-strict #myGrid .aw-grid-row {padding-bottom: 3px;}
      </style>
      <!-- Llenedo de la grid data -->
      <script type="text/javascript">
         <%
      List data = (List) request.getAttribute("Lisbc");
         %>

            var myColumns;
            myColumns=["id","Nro Expediente","Tipo Doc.","Nro Doc.","Fch. Creacion","Proceso","Area","Concesionario","Cliente"];
        
            var mydata=[<%
   Expedienfindadvance edl;
   if (data != null) {
      for (int i = 0; i < data.size(); i++) {
         edl = (Expedienfindadvance) data.get(i);
         %>["<%=edl.getIddocumento()%>","<%=edl.getExp_referencia()%>","<%=edl.getTd_nombre()%>","<%=edl.getDoc_nrdoc()%>","<%=edl.getDoc_fchcreacio()%>","<%=edl.getPro_nombre()%>","<%=edl.getU_nombre()%>","<%= (edl.getCn_rzsocial() != null ? edl.getCn_rzsocial().replace("\n", " ") : "")%>","<%= (edl.getCli_rzsocial() != null ? edl.getCli_rzsocial().replace("\n", " ") : edl.getCli_nombres() + " " + edl.getCli_apePaterno())%>","<%=edl.getIdproceso()%>"],<%

            }
         }%>
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

         obj.setCellFormat([num, str, str,str, str, str, str,str,str,str]);

         //	provide cells and headers text

         obj.setCellText(mydata);
         obj.setHeaderText(myColumns);

         //	set number of rows/columns
         <% if (data != null) {%>
            obj.setRowCount(<%=data.size()%>);
         <%} else {%>
            obj.setRowCount(0);
         <%}%>

        
            obj.setColumnCount(7);

            //Definiendo que columnas es visible

            obj.setSelectionMode("multi-row-marker");

            obj.onSelectedRowsChanged = function(arrayOfRowIndices){ window.status = arrayOfRowIndices;}

            obj.setColumnIndices([1,2,3,4,5,6,7,8,9,10]);

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
               A('#e1f3ff',id,'/siged/doViewDoc.action',idoc)

            };

      </script>

      <script language="JavaScript">
         function Abrir_ventana (pagina) {
            var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=620, height=280, top=180, left=200";
            window.open(pagina,"",opciones);
         }

         function Abrir_pagina (pagina) {
            var opciones="location=mainFrame";
            window.open(pagina,"",opciones);
         }

         var ObjAnt="f0";

      </script>
      <script language="JavaScript">
         function A(c,i,page,iddoc)
         {
            parent.frames["secondFrame"].location.href=page+'?iIdDoc='+i+'&iIdPro='+iddoc+'&avisopermiso=1';
         }

         function B(c,i,page)
         {
            Abrir_ventana(page+'?iIdDoc='+i);
         }
         var ObjAnt="row";

       
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
            poorman_changeimage('repHeader2_Img', '../../images/signo_menos.gif', '../../images/signo_mas.gif');
            poorman_toggle('row1');
            poorman_toggle('row2');
            poorman_toggle('row3');
         }

         function Toggle_repHeader1()
         {
            poorman_changeimage('repHeader1_Img', '../../images/signo_menos.gif', '../../images/signo_mas.gif');
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

      <script type="text/javascript">
         function nuevoDoc() {
            parent.location.href="NuevoDocumento_inicio.action";
         }

         function verSeguimiento() {
            parent.location.href="Seguimiento_mostrarInicio.action";
         }

         function nuevoDocSas() {
            parent.location.href = "CargarframeSAS.action";
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


      </STYLE>
   </head>
   <body >
      <table width="100%">
         <tr>
            <td height="4"  colspan="6"></td>
         </tr>

         <tr>
            <td height="20" colspan="6" width="99%">
               <s:form action="doSearchinner" method="post">
                  <table width="99%" border="0" align="center">
                     <td width="0%" height="14" rowspan="2"> </td>
                     <td width="29%" align="left"><font color="669BCD">Bienvenido</font><font color="0099FF">
                        <s:property value="#session.nombres" /></font></td>
                     <td width="14%" rowspan="2"></td>

                     <td width="39%" rowspan="2" align="right">

                        <s:if test="#session._RECURSO.UsuFinBtnLis == 1">
                           <a href="<s:url action="goFrameLoadListaArray" />" target="_parent"><img alt="Listas" border="0" src="images/lista.gif" /></a>
                           </s:if>
                           <s:if test="#session._RECURSO.UsuFinBtnSeg == 1">
                           <img onclick="verSeguimiento();" alt="Seguimiento" name="date" id="campo_fecha1" src="images/calendario.bmp" style="cursor:pointer" />&nbsp;&nbsp;
                        </s:if>

                        <s:if test="#session._RECURSO.UsuFinBtnNueDocPri == 1">
                           <img onclick="nuevoDoc();" src="images/botonNuevo.gif" style="cursor:pointer" alt="Nuevo Documento" />
                        </s:if>
                     </td>
                     <td width="18%" rowspan="2" align="right">
                     <s:textfield name="objEfa.exp_referencia" size="12"/><s:submit value="Buscar" cssClass="button"/></td>
                     <td width="2%" rowspan="2"></td>
                     <tr>
                        <td align="left">
                           <font color="0099FF">
                              <a href="<s:url action="doLogout" />" target="_parent">Cerrar Sesi&oacute;n</a>
                           </font>
                        </td>
                  </table>
               </s:form>
            </td>
         </tr>

         <tr>
            <td colspan="6">

            </td>
         </tr>

         <tr>
            <td height="20"colspan="6" class="titulo" width="99%">
               <table width="33%" border="0" height="20" align="left">
                  <tr>
                     <td bgcolor="#4481B8" width="100%" align="center" class="tituloRojo">Busquedas de Carpetas</td>
                  </tr>

               </table>
            </td>
         </tr>
         <tr>
            <td colspan="6">
               <script type="text/javascript">
                  // Escribimos la grid del active W.
                  document.write(obj);
               </script>
            </td>
         </tr>

      </table>
   </body>
</html>
