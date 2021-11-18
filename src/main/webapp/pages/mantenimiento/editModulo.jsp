<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
   <head>
      <title>Tramite Documentario</title>
      <link rel="stylesheet" type="text/css" href="css/estilo.css">
		<script type="text/javascript" src="js/dojo/dojo/dojo.js" djConfig="parseOnLoad:true, isDebug: false"></script>
      <script language="javascript" src="js/form.js"> </script>
      <script language="Javascript" src="js/general.js"></script>
      <link rel="stylesheet" type="text/css" media="all" href="css/calendar-green.css">
      <script type="text/javascript" src="js/calendar.js"></script>
      <script type="text/javascript" src="js/calendar-es.js"></script>
      <script type="text/javascript" src="js/calendar-setup.js"></script>

      <script language="JavaScript">
         function Abrir_ventanaBuscar (pagina) {
            var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=760, height=530, top=50, left=200";
            window.open(pagina,"",opciones);
         }

         function Abrir_ventanaCargo (pagina) {
            var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=760, height=380, top=50, left=200";
            window.open(pagina,"",opciones);
         }

         function Abrir_ventanaObservacion (i) {
            var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes, width=500, height=290, top=50, left=200";
            var pagina = "/siged/goRechazarQAS.action?iIdDoc="+i
            window.open(pagina,"",opciones);
         }

         function Abrir_pagina (pagina) {
            var opciones="location=mainFrame";
            window.open(pagina,"",opciones);
         }

         function putTextOnTextBox(textToPut){
            document.all.reciveTheText.value = textToPut;
         }
         function regresar(){
            history.back();
         }

         function ejecRech(i,docu){
            if (confirm("Desea rechazar el documento "+docu))
               location.href="/siged/doRechazarQAS.action?iIdDoc="+i
         }

         function eliminarModulo(id){
     		if(confirm("Seguro que desea eliminar el modulo")){
     		 dojo.xhrPost({
                  url: "doEliminarModulo.action",
                  content: {
     			 iIdModulo: id
                  },
                  mimetype: "text/html",
                  load: function() {
                     self.parent.frames["mainFrame"].location.href = "doBody.action";
                     self.parent.frames["secondFrame"].location.href = "secondFrame.jsp";
                  }
               });
     		}
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
            scrollbar-face-color:#FFFFFF;
            scrollbar-highlight-color:#669BCD;
            scrollbar-shadow-color:#999999;
         }
         -->
      </style>

   </head>

   <body class="barra" topmargin="0" leftmargin="0" rigthmargin="0" >
      <s:form name="frmModulo" action="doSaveModulo" method="POST">
         <table width="100%">
            <tr>
               <td></td>
               <td height="16" colspan="4" align="left" class="plomo"></td>
            </tr>
            <tr align="center">
               <td width="1%" align="center">&nbsp;</td>
               <td width="99%" colspan="2" align="left">
                  <s:submit src="images/guardar.bmp" type="image" value="Guardar Modulo" />
               </td>
				<td>
					<s:if test="iIdModulo!=null">
						<a href="#" onClick="eliminarModulo(<%=request.getAttribute("iIdModulo")%>)">eliminar</a>
					</s:if>
				</td>	
            </tr>
            <tr>
               <td></td>
               <td height="16" colspan="4" align="left" class="plomo"></td>
            </tr>
            <tr>
               <td height="14" colspan="3">
                  <table width="95%" border="1" align="center" bordercolor="#669BCD" bgcolor="#BFD9F1">
                     <tr>
                        <td height="75">
                           <table width="98%" height="97" align="center" >
                              <tr>
                                 <td></td>
                                 <td colspan="5" height="16" align="left" class="label">
                                    <s:textfield name="objModulo.idmodulo" cssStyle="display:none" />
                                 </td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Nombre</td>
                                 <td colspan="3" align="left" class="label"><s:textfield name="objModulo.nombre" cssClass="cajaMontoTotal" size="25" /></td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td class="titulo">&nbsp;</td>
                                 <td colspan="4"></td>
                              </tr>
                           </table>
                        </td>
                     </tr>
                  </table>
               </td>
            </tr>
            <tr>
               <td height="14"  colspan="3"></td>
            </tr>
         </table>
      </s:form>
   </body>
</html>
