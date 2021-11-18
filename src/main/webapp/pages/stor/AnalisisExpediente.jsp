<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
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

         var storeRevTec = new dojo.data.ItemFileReadStore({url: "autocompletarResponsableTecnico.action?idDependencia1="+<s:property value="idsala" />});
      </script>
      <!-- Begin Scrips STOR-->
      <script language="JavaScript">
         function analisisExpediente(){
            //console.log("revtec :"+dojo.byId("idrevisortecnico"));
            estado = document.getElementById('estadoexpedientestor');
            if(estado.value=='rtec'){
               var aceptar = document.getElementById('botonAceptar');
               aceptar.disabled=true;
               document.forms[0].action = '/siged/doStor_analisisExpediente.action' ;
               dijit.byId("dlgProgresBar").show();
               document.forms[0].submit();
            }else{
               if(validarCamposObligatorios()){
                  var aceptar = document.getElementById('botonAceptar');
                  aceptar.disabled=true;
                  document.forms[0].action = '/siged/doStor_analisisExpediente.action' ;
                  dijit.byId("dlgProgresBar").show();
                  document.forms[0].submit();
               }else{
                  //alert("Formulario Invalido - No pasar al Action");
               }
            }
            
         }

         function validarCamposObligatorios(){
            var revisorTecnico = dijit.byId("idrevisortecnico");
            var validarRevTec;

            if(revisorTecnico.isValid() && revisorTecnico.getValue().length != 0){
               validarRevTec = true;
            }else{
               revisorTecnico.setDisplayedValue("Campo Obligatorio");
               revisorTecnico.focus();
            }

            return validarRevTec;
         }

         function clearCamposObligatorios(idCampo){
            var campoDojo = dijit.byId(idCampo);
            campoDojo.setValue("");
            campoDojo.setDisplayedValue("");
         }

         function refrescar(){
            window.opener.parent.location.href ='/siged/inicio.action?sTipoFrame=grid';
            window.opener.parent.frames["mainFrame"].location.href = "/siged/doBody.action";
            window.close();
         }
      </script>
      <!-- End Scrips STOR-->
   </head>
   <body topmargin="0" leftmargin="0" rigthmargin="0" class="soria"
         <s:if test="refrescar!=null">
            onload="refrescar()"
         </s:if>
         >
      <s:form name="frmAnalizarExpediente" method="POST">
         <s:hidden name="idDocumento" />
         <s:hidden name="idsala"/>
         <!--Variables Auxiliares-->
         <s:hidden id="estadoexpedientestor" name="estadoexpedientestor" />
         <!--Variables Auxiliares-->
         <!--Variables Quejas y MC-->
         <s:hidden name="revisiontecnica" />
         <s:hidden name="revisionlegal" />
         <!--Variables Quejas y MC-->
         <table width="103%">
            <tr>
               <td height="14" colspan="2"> </td>
            </tr>
            <tr>
               <td height="13" colspan="2" class="header" ><div align="center">El expediente será aprobado</div></td>
            </tr>
            <tr>
               <td height="13" colspan="2" class="header" ></td>
            </tr>
            <tr>
               <td height="14" colspan="2">
                  <table width="58%" height="73"  border="1"  style="border-style:solid" align="center" bordercolor="#669BCD" bgcolor="#BFD9F1">
                     <tr class="altRow2" style="">
                        <td width="11%" height="9"><center></center></td>
                     </tr>
                     <tr class="altRow2" style="">
                     <td width="11%" height="68">
                        <table align="center" border="0" width="100%">
                           <tr>
                              <td align="center" width="250">
                                 <s:label>Responsable Técnico</s:label>                                 
                              </td>
                              <td width="250">
                                 <s:if test="estadoexpedientestor.equalsIgnoreCase('rtec')">
                                    &nbsp;&nbsp;&nbsp;<s:textfield name="revisortecnico" cssClass="cajaMontoTotal" size="15" readonly="true" disabled="true"/>
                                    <s:textfield name="idrevisortecnico" cssStyle="display:none" />
                                 </s:if>
                                 <s:else>
                                 <select dojoType="dijit.form.FilteringSelect"
                                            store="storeRevTec"
                                            idAttr="id"
                                            labelAttr="label"
                                            searchAttr="label"
                                            name="idrevisortecnico"
                                            id="idrevisortecnico"
                                            value="<s:property value="idrevisortecnico" />"
                                            size="80"
                                            required="true"
                                            invalidMessage="Seleccione un Revisor Tecnico Válido"></select>
                                  </s:else>
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
                  </table>
               </td>
            </tr>
            <tr>
               <td colspan="2"></td>
            </tr>
            <tr>
               <td height="14" colspan="2"></td>
            </tr>
            <tr>
               <td colspan="2" align="center">
                  <input id="botonAceptar" type="button" name="button" value="Aceptar" class="button" onClick="analisisExpediente()" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                  <input type="button" name="button2" value="Cancelar" class="button" onClick="window.close();" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
               </td>
            </tr>
            <tr align="center">
               <td colspan="2" align="center">&nbsp;</td>
            </tr>
         </table>
      </s:form>
      <%@ include file="../util/progressBar.jsp" %>
   </body>
</html>
