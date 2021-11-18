<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
   <head>
      <s:head/>
      <title>Tramite Documentario</title>
      <link rel="stylesheet" type="text/css" href="css/estilo.css">
      <script language="javascript" src="js/form.js"> </script>
      <script language="Javascript" src="js/general.js"></script>
      <link rel="stylesheet" type="text/css" media="all" href="css/calendar-green.css">
      <script type="text/javascript" src="js/calendar.js"></script>
      <script type="text/javascript" src="js/calendar-es.js"></script>
      <script type="text/javascript" src="js/calendar-setup.js"></script>

      <script language="JavaScript">
         function Abrir_ventana (pagina) {
            var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=650, height=400, top=50, left=200";
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

         function grabar(){
            document.forms["frmLista"].submit();
         }
         function eliminarLista(iIdLista){
            location.href = "/siged/doDeleteLista.action?iIdLista=" + iIdLista;
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

         -->

      </style>

   </head>

   <body  class="barra" topmargin="0" leftmargin="0" rigthmargin="0" >
      <s:form name="frmLista" action="doSaveLista" method="POST">
         <table width="100%">
            <tr>
               <td height="14" colspan="3"> </td>
            </tr>
            <tr>
               <td height="20"colspan="3" class="titulo">
                  <table width="99%" border="0" height="20" align="center" bgcolor="#A2C0DC">
                     <tr>

                        <td align="left" class="titulo">Crear Lista de Usuarios</td>
                     </tr>
                  </table>
               </td>
            </tr>
            <tr align="center">
               <td width="1%" align="center">&nbsp;</td>
               <td width="99%" colspan="2" align="left">
               <s:submit src="images/guardar.bmp" type="image" value="Guardar" />
               &nbsp;&nbsp;
               <s:if test="objLista.idlista != null">
                  <img id="<s:property value="objLista.idlista" />" src="images/eliminar.bmp" border="0" onClick="eliminarLista(this.id)" alt="Eliminar"/>  </tr>
               </s:if>
            <tr>
               <td height="14" colspan="3"> <table width="95%"  border="1" align="center" bordercolor="#669BCD" bgcolor="#BFD9F1">
                     <tr>
                        <td height="75"> <table width="98%" height="97" align="center" >
                              <tr>
                                 <td width="3%" ></td>
                                 <td width="15%" height="5" ></td>
                                 <td width="25%" ></td>
                                 <td width="26%" ></td>
                                 <td width="27%" ></td>
                                 <td width="4%" ></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Nombre Lista</td>
                                 <td align="left" width="25%" class="label">
                                    <s:textfield name="objLista.idlista" cssStyle="display:none" />
                                    <s:textfield name="objLista.nombre" />
                                 </td>
                                 <td align="left" >&nbsp;</td>
                                 <td align="left" width="27%" class="label"></td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Destinatarios &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                 <td colspan="3" align="left" class="label">
                                    <s:optiontransferselect
                                       allowUpDownOnLeft="false"
                                       allowUpDownOnRight="false"
                                       doubleHeaderKey="0"
                                       doubleList="mapUsuarioRight"
                                       doubleName="lstUsuarioRight"
                                       leftTitle="Usuarios Disponibles"
                                       headerKey="0"
                                       list="mapUsuarioLeft"
                                       rightTitle="Contactos" />
                                 </td>
                                 <td align="left"></td>
                              </tr>

                              <tr>
                                 <td height="5" ></td>
                              </tr>
                        </table></td>
                     </tr>
               </table></td>
            </tr>
            <tr>
               <td height="14"  colspan="3"></td>
            </tr>


         </table>
      </s:form>

   </body>
</html>
