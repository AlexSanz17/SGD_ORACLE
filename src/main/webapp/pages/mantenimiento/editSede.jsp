<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
   <head>
      <title>Tramite Documentario</title>
      <style type="text/css">
         @import "js/dojo/dijit/themes/soria/soria.css";
         @import "css/estilo.css";
      </style>
      <script type="text/javascript" src="js/dojo/dojo/dojo.js" djConfig="parseOnLoad:true, isDebug: false"></script>
      <script type="text/javascript">
         dojo.require("dijit.dijit");
         dojo.require("dijit.form.FilteringSelect");
         dojo.require("dojo.data.ItemFileReadStore");

         var strDepartamento = new dojo.data.ItemFileReadStore({url: "listaDepartamentos.action"});

         <s:if test="objSede != null">
            var strProvincia = new dojo.data.ItemFileReadStore({url: "listaProvincias.action?idDepartamento=" + <s:property value='objSede.ubigeo.provincia.departamento.iddepartamento' />});
            var strDistrito = new dojo.data.ItemFileReadStore({url: "listaDistritos.action?idProvincia=" + <s:property value='objSede.ubigeo.provincia.idprovincia' />});
         </s:if>
         <s:else>
            var strProvincia = new dojo.data.ItemFileReadStore({url: "listaProvincias.action"});
            var strDistrito = new dojo.data.ItemFileReadStore({url: "listaDistritos.action"});
         </s:else>

            changeStore = function(objFS, sURL) {
               console.log("[changeStore] - Cambiando store del objeto [" + objFS + "]");
               console.log("[changeStore] - Usando action [" + sURL + "]");

               dijit.byId(objFS).setDisplayedValue("Cargando Data...");
               dijit.byId(objFS).setDisabled(true);
               dijit.byId(objFS).store = new dojo.data.ItemFileReadStore({url: sURL});
               dijit.byId(objFS).setValue("");
               dijit.byId(objFS).setDisplayedValue("");
               dijit.byId(objFS).setDisabled(false);
            }

            updateStore = function(sParametro, sObjeto) {
               console.log("[updateStore] - sParametro [" + sParametro + "] sObjeto [" + sObjeto + "]");

               if (sParametro == undefined || sParametro == "") {
                  return;
               }

               if (sObjeto == "provincia") {
                  changeStore("objSede.ubigeo.provincia.idprovincia", "listaProvincias.action?idDepartamento=" + sParametro);
                  dijit.byId("objSede.ubigeo.iddistrito").setValue("");
                  dijit.byId("objSede.ubigeo.iddistrito").setDisplayedValue("");
                  dijit.byId("objSede.ubigeo.iddistrito").store = null;
               } else if (sObjeto == "distrito") {
                  changeStore("objSede.ubigeo.iddistrito", "listaDistritos.action?idProvincia=" + sParametro);
               }
            }
      </script>

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
      </script>

      <script type="text/javascript">

      function validarForm(){
          var idNombre=document.getElementById("doSaveSede_objSede_nombre");
          if(idNombre.value==""){
         	 alert("Debe ingresar el nombre del Sede.");
         	 idNombre.focus();
         	 return false;    
          }
          return true;
          }
		
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

   <body class="soria" topmargin="0" leftmargin="0" rigthmargin="0" >
      <s:form name="frmSede" action="doSaveSede" method="POST">
         <table width="100%">
            <tr>
               <td></td>
               <td height="16" colspan="4" align="left" class="plomo"></td>
            </tr>
            <tr align="center">
               <td width="1%" align="center">&nbsp;</td>
               <td width="99%" colspan="2" align="left">
                  <s:submit src="images/guardar.bmp" onclick="return validarForm()" type="image" value="Guardar Sede" />
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
                                    <s:textfield name="objSede.idsede" cssStyle="display:none" />
                                 </td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Nombre</td>
                                 <td colspan="3" align="left" class="label">
                                    <s:textfield name="objSede.nombre" id="doSaveSede_objSede_nombre" cssClass="cajaMontoTotal" size="25" />
                                 </td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Descripci&oacute;n</td>
                                 <td colspan="3" align="left" class="label">
                                    <s:textarea cssClass="cajaMontoTotal" name="objSede.descripcion" rows="10" cols="70"/>
                                 </td>
                                 <td align="left"></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Direcci&oacute;n</td>
                                 <td colspan="3" align="left" class="label">
                                    <s:textfield name="objSede.direccion" cssClass="cajaMontoTotal" size="25" />
                                 </td>
                                 <td align="left"></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Ubigeo</td>
                                 <td align="left">
                                    <div dojoType="dijit.form.FilteringSelect"
                                         id="objSede.ubigeo.provincia.departamento.iddepartamento"
                                         jsId="objSede.ubigeo.provincia.departamento.iddepartamento"
                                         name="objSede.ubigeo.provincia.departamento.iddepartamento"
                                         searchAttr="label"
                                         invalidMessage="Seleccione un departemento"
                                         onChange="updateStore(this.value, 'provincia')"
                                         required="false"
                                         store="strDepartamento"
                                         value="<s:property value='objSede.ubigeo.provincia.departamento.iddepartamento' />" />
                                 </td>
                                 <td align="left">
                                    <div dojoType="dijit.form.FilteringSelect"
                                         id="objSede.ubigeo.provincia.idprovincia"
                                         jsId="objSede.ubigeo.provincia.idprovincia"
                                         name="objSede.ubigeo.provincia.idprovincia"
                                         searchAttr="label"
                                         invalidMessage="Seleccione una provincia"
                                         onChange="updateStore(this.value, 'distrito')"
                                         required="false"
                                         store="strProvincia"
                                         value="<s:property value='objSede.ubigeo.provincia.idprovincia' />" />
                                 </td>
                                 <td align="left">
                                    <div dojoType="dijit.form.FilteringSelect"
                                         id="objSede.ubigeo.iddistrito"
                                         jsId="objSede.ubigeo.iddistrito"
                                         name="objSede.ubigeo.iddistrito"
                                         searchAttr="label"
                                         invalidMessage="Seleccione un distrito"
                                         required="false"
                                         store="strDistrito"
                                         value="<s:property value='objSede.ubigeo.iddistrito' />" />
                                 </td>
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
