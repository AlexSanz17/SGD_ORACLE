<%@ page contentType="text/html" pageEncoding="UTF-8" %>
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
      <script type="text/javascript" src="js/siged/upload.js"></script>
      <script type="text/javascript">
         dojo.require("dijit.form.FilteringSelect" );
         dojo.require("dojo.data.ItemFileReadStore");
         dojo.require("dojo.io.iframe");

         var storeConcesionario = new dojo.data.ItemFileReadStore({url: "autocompletarConcesionario.action"});
         var storeProceso = new dojo.data.ItemFileReadStore({url: "autocompletarProceso.action"});
      </script>
      <script type="text/javascript">
         function UpdateInfoProceso(sProceso){
            var sProceso = String(sProceso);

            storeProceso.fetchItemByIdentity({
               identity: sProceso,

               onItem: function(item) {
                  document.getElementById("unidad").value = storeProceso.getValue(item, "areadestino");
                  document.getElementById("responsable").value = storeProceso.getValue(item, "destinatario");
                  document.getElementById("idresponsable").value = storeProceso.getValue(item, "idresponsable");
               }
            });
         }

         function saveExpUser() {
            document.forms["frmSaveExpUser"].submit();
         }

         function uploadWithForm() {
            var opciones = "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes, width=600, height=300, top=50, left=200";
            var pagina = "goUploadWithForm.action";

            window.open(pagina, "", opciones);
         }
         function regresar(){ 
        	   history.back();
        	}
      </script>
      <style type="text/css">
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
         div.falso {
            position: absolute;
            top: -29px;
            left: 105px;
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
            left: 75px;
         }
         -->
      </style>
   </head>
   <body class="soria" topmargin="0" leftmargin="0" rigthmargin="0" >
	<table>
	<tr>
	<td width="29%" align="left">
	<font color="669BCD">Bienvenido </font><font color="0099FF"><s:property value="#session.nombres" /></font>
	</td>
	</tr>
	<tr>
	<td align="left">
	
	</td>
	</tr>
	</table>   
      <table width="100%">
         <tr>
            <td height="14" colspan="3"></td>
         </tr>
         <tr>
            <td height="20"colspan="3" class="titulo">
               <table width="99%" border="0" height="20" align="center" bgcolor="#A2C0DC">
                  <tr>
                     <td align="left" class="titulo">Nuevo Expediente</td>
                  </tr>
               </table>
            </td>
         </tr>
         <tr align="center">
            <td width="1%" align="center">&nbsp;</td>
            <td width="99%" colspan="2" align="left">
            <img src="images//regresar.bmp" border="0" onClick="regresar()" alt="Regresar"/>
               <img src="images/xx.bmp" border="0" onClick="saveExpUser()" alt="Registrar"/>
               <img onClick="uploadWithForm()" src="images/adjunto.bmp" border="0" alt="Adjuntar Documento"/>
            </td>
         </tr>
         <tr>
            <td height="14" colspan="3"></td>
         </tr>
         <tr>
            <td></td>
            <td height="16" colspan="2" align="left" class="plomo">
               <s:if test="#session.documentotemporal != null">
                  <s:iterator value="#session.documentotemporal">
                     Documento: <s:property value="sNroDocumento" />
                     &nbsp;Archivos Adjuntos:
                     <s:if test="lstArchivo != null">
                        <s:property value="lstArchivo.size()" />
                     </s:if>
                     <s:else>
                        0
                     </s:else>
                     <br>
                  </s:iterator>
               </s:if>
            </td>
         </tr>
         <tr>
            <td height="14" colspan="3"></td>
         </tr>
         <tr>
            <td height="14" colspan="3">
               <s:form name="frmSaveExpUser" action="doSaveExpUser" method="POST">
                  <table width="95%" border="1" align="center" bordercolor="#669BCD" bgcolor="#BFD9F1">
                     <tr>
                        <td height="55">
                           <table width="98%" height="132" align="center">
                              <tr>
                                 <td width="2%"></td>
                                 <td width="18%" height="5"></td>
                                 <td width="17%"></td>
                                 <td width="15%"></td>
                                 <td width="45%"></td>
                                 <td width="3%"></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left" class="label">Documentos</td>
                                 <td align="left" width="17%" class="label"></td>
                                 <td align="left">&nbsp;</td>
                                 <td align="left" width="45%" class="label"></td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td ></td>
                                 <td height="16" align="left">Proceso</td>
                                 <td colspan="3" class="label">
                                    <select dojoType="dijit.form.FilteringSelect"
                                            store="storeProceso"
                                            idAttr="id"
                                            labelAttr="label"
                                            searchAttr="label"
                                            name="objExpediente.proceso.idproceso"
                                            id="objExpediente.proceso.idproceso"
                                            size="80"
                                            onChange="UpdateInfoProceso" />
                                 </td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="22" align="left">Area Destino</td>
                                 <td colspan="3" align="left" class="label">
                                    <s:textfield id="unidad" name="objExpediente.proceso.responsable.rol.idunidad.nombre" cssClass="cajaMontoTotal" readonly="true" size="35" />
                                 </td>
                                 <td align="left"></td>
                              </tr>
                              <tr>
                                 <td ></td>
                                 <td height="22" align="left">Destinatario</td>
                                 <td colspan="3" class="label">
                                    <s:textfield id="idresponsable" name="objExpediente.proceso.responsable.idusuario" cssStyle="display:none" />
                                    <s:textfield id="responsable" name="objExpediente.proceso.responsable.nombres+' '+objExpediente.proceso.responsable.apellidos" cssClass="cajaMontoTotal" readonly="true" size="35" />
                                 </td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Asunto</td>
                                 <td colspan="3" align="left" class="label">
                                    &nbsp;<s:textfield name="objExpediente.asunto" cssClass="dijitReset" size="60" />
                                 </td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td ></td>
                                 <td height="16" align="left">Concesionario</td>
                                 <td colspan="3" class="label">
                                    <select dojoType="dijit.form.FilteringSelect"
                                            store="storeConcesionario"
                                            idAttr="id"
                                            labelAttr="label"
                                            searchAttr="label"
                                            name="objExpediente.concesionario.idconcesionario"
                                            id="objExpediente.concesionario.idconcesionario"
                                            size="80" />
                                 </td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td height="5"></td>
                              </tr>
                           </table>
                        </td>
                     </tr>
                  </table>
               </s:form>
            </td>
         </tr>
         <tr>
            <td height="14" colspan="3"></td>
         </tr>
      </table>
   </body>
</html>
