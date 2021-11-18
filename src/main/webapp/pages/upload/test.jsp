<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
   <head>
      <title>Tramite Documentario</title>
      <script type="text/javascript" src="js/dojo/dojo/dojo.js" djConfig="parseOnLoad:true, isDebug: false"></script>
      <link rel="stylesheet" type="text/css" href="/siged/css/estilo.css">
      <script language="javascript" src="/siged/js/form.js"> </script>
      <script language="Javascript" src="/siged/js/general.js"></script>
      <link rel="stylesheet" type="text/css" media="all" href="/siged/css/calendar-green.css">
      <script type="text/javascript" src="/siged/js/calendar.js"></script>
      <script type="text/javascript" src="/siged/js/calendar-es.js"></script>
      <script type="text/javascript" src="/siged/js/calendar-setup.js"></script>

      <script type="text/javascript">
         dojo.require("dojo.io.iframe");
         dojo.require("siged.upload");

         function subir() {
            location.href = "/siged/doSubirTest.action";
         }
      </script>

      <script language="JavaScript">
         function Abrir_ventana (pagina) {
            var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=no, width=440, height=350, top=50, left=200";
            window.open(pagina,"",opciones);
         }
         function abrirVentanaDerivar (i) {
            var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes, width=950, height=740, top=20, left=70";
            var pagina = "/siged/goDerivarUSER.action?iIdDoc="+i
            //location.href="/siged/goDerivarUSER.action?iIdDoc="+i
            window.open(pagina,"",opciones);
         }

         function abrirVentanaSeguimiento(iIdDoc) {
            var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=no, width=650, height=320, top=50, left=200";
            window.open("/siged/goViewSeguimiento.action?iIdDocumento=" + iIdDoc,"",opciones);
         }

         function abrirVentanaRechazar (i) { 
            var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes, width=500, height=290, top=50, left=200";
            var pagina = "/siged/goRechazarUSER.action?iIdDoc="+i
            window.open(pagina,"",opciones);
         }

         function Abrir_ventanaId ( pagina ) {
            var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes, width=600, height=550, top=50, left=200";
            window.open(pagina,"",opciones);
         }

         function Abrir_pagina (pagina) {
            var opciones="location=mainFrame";
            window.open(pagina,"",opciones);
         }
         function ejecAprob(i,exp){
            if (confirm("Desea aprobar el expediente "+exp)){
               location.href="/siged/doAprobarUSER.action?iIdDoc="+i
            }
         }
         function ejecRech(){
            if (confirm("Desea rechazar el expediente EXP001")){
               location.href="rechazar.html"
            }
         }

         function abrirVentanaArchivar (i) {
            var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes, width=650, height=440, top=20, left=70";
            var pagina = "/siged/Archivar_inicio.action?idDocumento="+i
            //location.href="/siged/goDerivarUSER.action?iIdDoc="+i
            window.open(pagina,"",opciones);
         }
         function abrirConfirmacionAprobar(i,exp,idetapa){
            if (confirm("Desea aprobar el expediente "+exp+" con idetapa "+idetapa)){
               location.href="/siged/doAprobarStor.action?iIdDoc="+i
            }
         }
         function abrirVentanaAprobar(idDoc) {
            var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes, width=500, height=290, top=50, left=200";
            var pagina = "/siged/goAprobarStor.action?iIdDoc=" + idDoc
            window.open(pagina,"",opciones);
         }

         function abrirVentanaAnular(iIdDocumento) {
            var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=no, width=650, height=300, top=50, left=200";
            var pagina = "/siged/goAnular.action?iIdDocumento=" + iIdDocumento;
            window.open(pagina, "", opciones);
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
         function Toggle_repHeader3()
         {
            poorman_changeimage('repHeader3_Img', 'images/signo_menos.gif', 'images/signo_mas.gif');
            poorman_toggle('row1');
            poorman_toggle('row2');
            poorman_toggle('row3');
         }

         function Toggle_repHeader3()
         {
            poorman_changeimage('repHeader3_Img', 'images/signo_menos.gif', 'images/signo_mas.gif');
            poorman_toggle('trRow3');
         }
         function Toggle_repHeader4()
         {
            poorman_changeimage('repHeader4_Img', 'images/signo_menos.gif', 'images/signo_mas.gif');
            poorman_toggle('row1');
            poorman_toggle('row2');
            poorman_toggle('row3');
         }

         function Toggle_repHeader4()
         {
            poorman_changeimage('repHeader4_Img', 'images/signo_menos.gif', 'images/signo_mas.gif');
            poorman_toggle('trRow4');
         }
         function Toggle_repHeader5()
         {
            poorman_changeimage('repHeader5_Img', 'images/signo_menos.gif', 'images/signo_mas.gif');
            poorman_toggle('row1');
            poorman_toggle('row2');
            poorman_toggle('row3');
         }

         function Toggle_repHeader5()
         {
            poorman_changeimage('repHeader5_Img', 'images/signo_menos.gif', 'images/signo_mas.gif');
            poorman_toggle('trRow5');
         }
         function Toggle_repHeader6()
         {
            poorman_changeimage('repHeader6_Img', 'images/signo_menos.gif', 'images/signo_mas.gif');
            poorman_toggle('row1');
            poorman_toggle('row2');
            poorman_toggle('row3');
         }

         function Toggle_repHeader6()
         {
            poorman_changeimage('repHeader6_Img', 'images/signo_menos.gif', 'images/signo_mas.gif');
            poorman_toggle('trRow6');
         }

         function subir() {
            document.forms[0].action = '/siged/doUploadUser.action' ;
            document.forms[0].submit();
         }
      </script>


      <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
      <style type="text/css">
         <!--
         body {
            background-color: #ffffff;
         }

         .barra {
            scrollbar-3dlight-color:#CCCCCC;
            scrollbar-arrow-color:#568BBF;
            scrollbar-base-color:#C3D5E9;
            scrollbar-darkshadow-color:#666666;
            scrollbar-face-color:;
            scrollbar-highlight-color:#669BCD;
            scrollbar-shadow-color:#999999;
         }

         div.falso {
            position: absolute;
            top: -29px;
            left: 470px;
            z-index: 0;
            width: 14px;
            height: 3px;
         }

         input.file {
            position: relative;
            text-align: left;
            filter:alpha(opacity: 0);
            opacity: 0;
            z-index: 1;
            top: -29px;
            left: 470px;
         }
         -->

      </style>
   </head>

   <body  class="barra" topmargin="0" leftmargin="0" rigthmargin="0">
      <table width="100%">
         <tr>
            <td height="14" colspan="3"></td>
         </tr>
         <tr>
            <td height="20"colspan="6" class="titulo">
               <table width="37%" border="0" height="20" align="left">
                  <tr>
                     <td bgcolor="#4481B8" width="43%" align="center" class="tituloRojo" >General</td>
                     <td width="2%" align="center"></td>
                     <td bgcolor="#BFD9F1" width="55%" align="center" class="titulo" onclick="location.href='/siged/doViewDocAdicionales.action?iIdDoc=<s:property value='objDD.iIdDocumento'/>'">Documentos Adicionales</td>
                  </tr>
               </table>
            </td>
         </tr>
         <tr align="center">
            <td width="2%" align="center">&nbsp;</td>
            <td width="98%" colspan="2" align="left">
               <img onClick="subir()" src="images/archivar.bmp" border="0" alt="Archivar"/>
               &nbsp;&nbsp;
               <s:form id="frmUploadFile1" action="doUploadFile" method="POST" enctype="multipart/form-data">
                  <div style="position:relative;">
                     <input name="upload" type="file" class="file" size="1" onchange="uploadIt(1)" />
                     <div class="falso">
                        <img src="images/adjunto.bmp" align="left" border="0" alt="Adjuntar Archivo">
                     </div>
                  </div>
               </s:form>
            </td>
         </tr>
         <tr>
            <td></td>
            <td height="16" colspan="4" align="left" class="plomo">Adjuntos:</td>
         </tr>
         <tr>
            <td></td>
            <td height="16" colspan="4" align="left" class="plomo">
               <div id="divShowFile1"></div>
            </td>
         </tr>
         <tr>
         <tr>
            <td height="50" colspan="3">
               <s:form id="frmUploadFile2" action="doUploadFile" method="POST" enctype="multipart/form-data">
                  <div style="position:relative;">
                     <input name="upload" type="file" class="file" size="1" onchange="uploadIt(2)" />
                     <div class="falso">
                        <img src="images/adjunto.bmp" align="left" border="0" alt="Adjuntar Archivo">
                     </div>
                  </div>
               </s:form>
            </td>
         </tr>
         <tr>
            <td></td>
            <td height="16" colspan="4" align="left" class="plomo">
               <div id="divShowFile2"></div>
            </td>
         </tr>
         <tr>
            <td height="14"  colspan="3"></td>
         </tr>
      </table>
   </body>
</html>
