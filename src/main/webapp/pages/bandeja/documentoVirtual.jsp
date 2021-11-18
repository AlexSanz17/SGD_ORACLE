<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
   <head>
      <title>Tramite Documentario</title>
      <style type="text/css">
         @import "js/dojo/dijit/themes/soria/soria.css";
         @import "css/estilo.css";
         .empty {
            color: #A0A0A0;
         }
      </style>
      <script type="text/javascript" src="js/dojo/dojo/dojo.js" djConfig="parseOnLoad:true, isDebug: false"></script>
      <script type="text/javascript" src="js/dojo/dojox/widget/PlaceholderMenuItem.js"></script>
      <script type="text/javascript">
         dojo.require("dijit.Dialog");
         dojo.require("dijit.ProgressBar");
      </script>
      <link rel="stylesheet" type="text/css" href="css/estilo.css"> 
      <script language="javascript" src="js/form.js"> </script>
      <script language="Javascript" src="js/general.js"></script>
      <link rel="stylesheet" type="text/css" media="all" href="css/calendar-green.css">
      <script type="text/javascript" src="js/calendar.js"></script>
      <script type="text/javascript" src="js/calendar-es.js"></script>
      <script type="text/javascript" src="js/calendar-setup.js"></script>

      <script language="JavaScript">
         function anexarDocumento() {
            var objeto = document.getElementsByName("arrIdDoc");
            var haychekadito = false;

            for (var i = 0; i < objeto.length; i++) {
               if (objeto[i].checked) {
                  haychekadito = true;

                  break;
               }
            }

            if (!haychekadito) {
               alert("Debe seleccionar al menos un expediente");

               return;
            }

            document.forms["frmAnexarDocumento"].submit();
           // window.opener.showGridInbox(0); 
            window.opener.viewDetailCustom2("<s:property value="#session.iIdDocumento" />");
            window.close(); 
         }

         function cleanFields() {
            document.getElementById("objExpedienteSearch.sNroExpediente").value = "";
            document.getElementById("objExpedienteSearch.sNroDocumento").value = "";
            document.getElementById("objExpedienteSearch.iIdTipoDocumento").value = 0;
            document.getElementById("objExpedienteSearch.sNroCaja").value = "";
         }

         function showLoading() {
            var snroexp = document.getElementById("objExpedienteSearch.sNroExpediente").value;
            var snrodoc = document.getElementById("objExpedienteSearch.sNroDocumento").value;
            var idtipodoc = document.getElementById("objExpedienteSearch.iIdTipoDocumento").value;
            var snrocaja = document.getElementById("objExpedienteSearch.sNroCaja").value;

            if (snroexp == "" && snrodoc == "" && idtipodoc <=0 && snrocaja == "") {
               alert("Debe ingresar al menos un filtro de busqueda");

               return;
            }

             dijit.byId("dlgProgresBar").show();       
            document.forms["frmSearchDocumento"].submit();
            //document.getElementById("divLoading").style.display = "";
         }
      </script>
   </head>

   <body class="soria" topmargin="0" leftmargin="0" rigthmargin="0">
      <table width="100%">
         <tr>
            <td height="14" colspan="6">
               <table width="100%" align="center" bordercolor="#000000" bgcolor="#568BBF">
                  <td width="97%" height="13" align="left" class="titulo" >B&uacute;squeda de Documentos</td>
                  <td width="3%" align="right"></td>
               </table>
            </td>
         </tr>
         <tr>
            <td height="4" colspan="6" class="header"></td>
         </tr>
         <tr>
            <td colspan="6">
               <table width="95%" border="1" align="center" bordercolor="#669BCD" bgcolor="#BFD9F1">
                  <tr>
                     <td height="100">
                        <s:form id="frmSearchDocumento" name="frmSearchDocumento" action="doSearchDocumento" method="POST">
                           <table width="100%" height="97" align="center" >
                              <tr>
                                 <td align="left" colspan="8" class="header">
                                    <%--<s:submit type="image" src="images/buscar.bmp" value="Buscar" cssStyle="cursor:pointer" onclick="showLoading();" />--%>
                                    <img src="images/buscar.bmp" border="0" onClick="javascript:showLoading();" alt="Buscar" style="cursor:pointer" />
                                    <img src="images/Limpiar.bmp" border="0" onClick="javascript:cleanFields();" alt="Limpiar" style="cursor:pointer" />
                                 </td>
                              </tr>
                              <tr>
                                 <td width="1%"></td>
                                 <td width="19%"></td>
                                 <td width="15%"></td>
                                 <td width="12%"></td>
                                 <td width="19%"></td>
                                 <td width="14%"></td>
                                 <td width="18%"></td>
                                 <td width="2%"></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Nro Expediente</td>
                                 <td colspan="5">
                                    <s:textfield cssClass="cajaMontoTotal" id="objExpedienteSearch.sNroExpediente" name="objExpedienteSearch.sNroExpediente" size="40" maxlength="80" />
                                 </td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Nro Documento</td>
                                 <td colspan="5">
                                    <s:textfield cssClass="cajaMontoTotal" id="objExpedienteSearch.sNroDocumento" name="objExpedienteSearch.sNroDocumento" size="12" maxlength="20" />
                                 </td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td ></td>
                                 <td height="16" align="left">Tipo Documento</td>
                                 <td colspan="5">
                                    <s:select cssClass="cajaMontoTotal" id="objExpedienteSearch.iIdTipoDocumento" name="objExpedienteSearch.iIdTipoDocumento" headerKey="0" headerValue="-- Seleccione un Tipo --" list="mapTipoDocumento" />
                                 </td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td ></td>
                                 <td height="16" align="left">Nro Caja</td>
                                 <td colspan="5">  
                                    <s:textfield cssClass="cajaMontoTotal" id="objExpedienteSearch.sNroCaja" name="objExpedienteSearch.sNroCaja" size="60" maxlength="60" />
                                 </td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td height="5"></td>
                              </tr>
                           </table>
                        </s:form>
                     </td>
                  </tr>
               </table>
            </td>
         </tr>
         <tr align="left">
            <td width="1%">&nbsp;</td>
            <td height="14" colspan="5">
               <div id="divLoading" style="display:none;">
                  <img src="images/cargando.gif" border="0" alt="Buscando Data..." />
                  Buscando data. Espere por favor...
               </div>
            </td>
         </tr>
         <s:if test="lstDocumento.size > 0">
            <tr>
               <td width="1%">&nbsp;</td>
               <td height="13" colspan="5" class="header" align="left">Resultado de B&uacute;squeda</td>
            </tr>
            <tr align="center">
               <td colspan="6" align="center">&nbsp;</td>
            </tr>

            <tr>
               <td width="1%">&nbsp;</td>
               <td height="13" colspan="5" class="header" align="left" width="99%">
                  <img src="images/anexar.bmp" border="0" onClick="anexarDocumento()" alt="Anexar Documentos" style="cursor:pointer" />
               </td>
            </tr>
            <tr>
               <td colspan="6">
                  <s:form name="frmAnexarDocumento" action="doAnexarDocumento" method="POST">
                                     
                        <table cellpadding="1" cellspacing="1" border="1" class="tableForm" width="98%" align="center">
                           <tr class="header">
                              <th width="10%" class="data">&nbsp;</th>
                              <th width="20%" class="data">Nro Expediente</th>
                              <th width="20%" class="data">Nro Documento</th>
                              <th width="20%" class="data">Tipo</th>
                              <th width="20%" class="data">Nro Caja</th>
                              <th width="13%" class="data">Fecha Creaci&oacute;n</th>
                              <th width="13%" class="data"><img src='images/clic.gif' border="0" /></th>
                           </tr>
                           <s:iterator value="lstDocumento">
                              <tr id="<s:property value="idDocumento"/>" onMouseOver="overMouse(this)" onMouseOut="outMouse(this)" class="altRow2" style="cursor:pointer">
                                 <td><div align="center"><s:checkbox name="arrIdDoc" fieldValue="%{idDocumento}" /></div></td>
                                 <td><div align="center"><s:property value="expediente.nroexpediente" /></div></td>
                                 <td><div align="center"><s:property value="numeroDocumento" /></div></td>
                                 <td><div align="center"><s:property value="tipoDocumento.nombre" /></div></td>
                                 <td><div align="center"><s:property value="numeroCaja" /></div></td>
                                 <td><div align="center"><s:date name="fechaCreacion" format="dd/MM/yyyy" /></div></td>
                                 <td>
                                    <s:if test="archivoList != null && archivoList.size() > 0">
                                       <s:iterator value="archivoList">
                                          <s:if test="sURL != null && !sURL.equals('')">
                                             <div align="center"><a alt="Ver Documento" border="0" href="<s:property value='sURL' />" target="_blank"><img src="images/clic.gif" /></a></div>
                                                </s:if>
                                             </s:iterator>
                                          </s:if>
                                 </td>
                              </tr>
                           </s:iterator>
                        </table>
                     
                  </s:form>
               </td>
            </tr>
         </s:if>
         <tr>
            <td colspan="6" align="center"></td>
         </tr>
      </table>
      
       <%-- descomentado , es un popup ... u.u --%>   
      <%@include file="../util/progressBar.jsp" %> 
        
   </body>
</html>
