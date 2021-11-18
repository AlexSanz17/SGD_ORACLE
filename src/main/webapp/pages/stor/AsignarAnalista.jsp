<?xml version="1.0" encoding="UTF-8" ?>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es">
   <head>
      <title>Tramite Documentario</title>
      <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
      <style type="text/css">
         @import "js/dojo/dijit/themes/soria/soria.css";
         @import "css/estilo.css";
      </style>
      <script type="text/javascript" src="js/dojo/dojo/dojo.js" djConfig="parseOnLoad:true, isDebug: false"></script>
      <script type="text/javascript">
         dojo.require("dijit.form.FilteringSelect" );
         dojo.require("dojo.data.ItemFileReadStore");
         dojo.require("dijit.Dialog");
         dojo.require("dijit.ProgressBar");
         var storeAnalista = new dojo.data.ItemFileReadStore({url: "autocompletarAnalistaStor.action?idDependencia1=" + <s:property value="idsala" />});
      </script>
      <!-- Begin Scrips STOR-->
      <script language="JavaScript">
         function asignarAnalista() {
            if(validarCamposObligatorios()){
               var aceptar = document.getElementById('botonAceptar');
               aceptar.disabled=true;
               dijit.byId("dlgProgresBar").show();
               document.forms["frmAsignarAnalista"].submit();
            }else{
               //alert("Formulario Invalido - No pasar al Action");
            }
            
         }

         function setValueOtraSala() {
            var chk = document.getElementById('otrasala');

            if (chk.checked) {
               chk.value = 'si';
            } else {
               chk.value = 'no';
            }

            storeAnalista = new dojo.data.ItemFileReadStore({url: "autocompletarAnalistaStor.action?idDependencia1="  + <s:property value="idsala" /> + "&idDependencia2=" + chk.value});
            dijit.byId("idanalista").store = storeAnalista;
            dijit.byId("idanalista").setDisplayedValue("");
            dijit.byId("idanalista").setValue("");
         }

         function refrescar() {
            window.opener.parent.location.href ='/siged/inicio.action?sTipoFrame=grid';
            window.opener.parent.frames["mainFrame"].location.href = "/siged/doBody.action";
            window.close();
         }
         function validarCamposObligatorios(){
            var analista = dijit.byId("idanalista");
            var validarAnalista;
            if(analista.isValid() && analista.getValue().length != 0){
               validarAnalista = true;
            }else{
               analista.setDisplayedValue("Campo Obligatorio");
               analista.focus();
            }

            return validarAnalista;
         }

         function clearCamposObligatorios(idCampo){
            var campoDojo = dijit.byId(idCampo);
            campoDojo.setValue("");
            campoDojo.setDisplayedValue("");
         }
      </script>
      <!-- End Scrips STOR-->
   </head>

   <body topmargin="0" leftmargin="0" rigthmargin="0" class="soria"
         <s:if test="refrescar!=null">
            onload="refrescar()"
         </s:if>
         >
      <s:form name="frmAsignarAnalista" action="doStor_asignarAnalista" method="POST">
         <input type="hidden" name="idDocumento" value="<s:property value='idDocumento'/>"/>
         <table width="103%">
            <tr><td height="14" colspan="2"></td></tr>
            <tr>
               <td height="13" colspan="2" class="header" ><div align="center">El expediente será aprobado</div></td>
            </tr>
            <tr>
               <td height="13" colspan="2" class="header"></td>
            </tr>
            <tr>
            <td height="14" colspan="2">
               <table width="58%" height="113" border="1" style="border-style:solid" align="center" bordercolor="#669BCD" bgcolor="#BFD9F1">
                  <tr class="altRow2" style="">
                     <td width="11%" height="9"></td>
                  </tr>
                  <tr class="altRow2" style="">
                     <td width="11%" height="68">
                        <table align="center" border="0" width="100%">
                           <tr>
                              <td align="center" width="136">Sala</td>
                              <td width="250">
                                 <s:hidden name="idsala" />
                                 <s:textfield id="sala" name="sala" size="15" readonly="true" disabled="true" />
                              </td>
                           </tr>
                           <tr>
                              <td align="center" width="136">Otra Sala</td>
                              <td width="250">
                                 <s:checkbox id="otrasala" name="otrasala" value="false" onclick="setValueOtraSala()"/>
                              </td>
                           </tr>
                           <tr>
                           <td align="center">Analista</td>
                           <td>
                              <div dojoType="dijit.form.FilteringSelect"
                                      store="storeAnalista"
                                      idAttr="id"
                                      labelAttr="label"
                                      searchAttr="label"
                                      name="idanalista"
                                      id="idanalista"
                                      size="1"></div>
                           </td>
                           </tr>
                           <tr><td colspan="2" align="center">OBSERVACION</td></tr>
                           <tr>
                              <td colspan="2" align="center">
                                 <s:textarea id="observacionaprobar" name="observacionaprobar" cols="40" rows="7"/>
                              </td>
                           </tr>
                        </table>
                     </td>
                  </tr>
               </table>
            </td>
            <tr>
               <td colspan="2"></td>
            </tr>
            <tr>
               <td height="14" colspan="2"></td>
            </tr>
            <tr>
               <td colspan="2" align="center">
                  <input id="botonAceptar" type="button" name="button" value="Aceptar" class="button" onClick="asignarAnalista()" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                  <input type="button" name="button2" value="Cancelar" class="button" onClick="window.close()" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                  </td>
            </tr>
            <tr align="center">
               <td colspan="2"  align="center">&nbsp;</td>
            </tr>
         </table>
      </s:form>
      <%@ include file="../util/progressBar.jsp" %>
   </body>
</html>