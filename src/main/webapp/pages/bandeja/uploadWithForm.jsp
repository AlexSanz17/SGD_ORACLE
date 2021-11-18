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
         dojo.require("dijit.form.DateTextBox");
         dojo.require("dijit.form.FilteringSelect" );
         dojo.require("dojo.data.ItemFileReadStore");
         dojo.require("dojo.io.iframe");

         var storeTipoDocumento = new dojo.data.ItemFileReadStore({url: "autocompletarAllTipoDocumento.action"});
      </script>
      <script type="text/javascript">
       
         function refrescar() {
            window.opener.location.href = "go_bandeja_nuevoExpediente.action";
            this.window.close();
         }

         function submitForm() {
            document.forms["frmUploadWithForm"].submit();
         }
      </script>
      <style type="text/css">
         <!--
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
   <body class="soria" topmargin="0" leftmargin="0" rigthmargin="0" <s:if test="cerrar!=null">onload="refrescar();"</s:if>>
      <table width="100%">
         <tr>
            <td height="14" colspan="3"></td>
         </tr>
         <tr>
            <td height="20"colspan="3" class="titulo">
               <table width="99%" border="0" height="20" align="center" bgcolor="#A2C0DC">
                  <tr>
                     <td align="left" class="titulo">Nuevo Documento</td>
                  </tr>
               </table>
            </td>
         </tr>
         <tr align="center">
            <td width="1%" align="center">&nbsp;</td>
            <td width="99%" colspan="2" align="left">
               <img src="images/xx.bmp" border="0" onClick="submitForm()" alt="Crear Documento"/>
               <s:if test="#session._RECURSO.UsuFinBtnUpl">
                  &nbsp;&nbsp;
                  <s:form id="frmUploadFile1" action="doUploadFile" method="POST" enctype="multipart/form-data">
                     <div id="divUpload" style="position:relative;">
                        <input name="upload" type="file" class="file" size="1" onchange="uploadIt(1)" />
                        <div class="falso">
                           <img src="images/adjunto.bmp" align="left" border="0" alt="Adjuntar Archivo">
                        </div>
                     </div>
                  </s:form>
               </s:if>
            </td>
         </tr>
         <tr>
            <td></td>
            <td height="16" colspan="2" align="left" class="plomo">
               <div id="divShowFile1"></div>
            </td>
         </tr>
         <tr>
            <td height="14" colspan="3">
               <s:form name="frmUploadWithForm" action="doUploadWithForm" method="POST">
                  <table width="95%" border="1" align="center" bordercolor="#669BCD" bgcolor="#BFD9F1">
                     <tr>
                        <td height="75">
                           <table width="98%" height="97" align="center">
                              <tr>
                                 <td width="1%"></td>
                                 <td width="20%" height="5"></td>
                                 <td width="17%"></td>
                                 <td width="15%"></td>
                                 <td width="45%"></td>
                                 <td width="3%"></td>
                              </tr>
                              <tr>
                                 <td colspan="6" height="15" class="label">DOCUMENTO</td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Tipo Documento</td>
                                 <td align="left" width="17%" class="label">
                                    <div dojoType="dijit.form.FilteringSelect"
                                         store="storeTipoDocumento"
                                         idAttr="id"
                                         labelAttr="label"
                                         searchAttr="label"
                                         name="objDocumento.tipodocumento.idtipodocumento"
                                         id="objDocumento.tipodocumento.idtipodocumento"
                                         size="80" />
                                 </td>
                                 <td align="left" class="label"></td>
                                 <td align="left" class="label"></td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Nro. Documento</td>
                                 <td align="left" width="17%" class="label">
                                    &nbsp;<s:textfield name="objDocumento.nrodocumento" cssClass="dijitReset" size="20" />
                                 </td>
                                 <td align="left" class="label"></td>
                                 <td align="left" class="label"></td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Nro Folios</td>
                                 <td align="left" width="17%" class="label">
                                    &nbsp;<s:textfield name="objDocumento.nrofolios" cssClass="dijitReset" size="20" />
                                 </td>
                                 <td align="left" ></td>
                                 <td align="left" width="45%" class="label"></td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Fecha Documento</td>
                                 <td colspan="3" align="left" class="label">
                                    <input id="objDocumento.fechadocumento"
                                           type="text"
                                           name="objDocumento.fechadocumento"
                                           value=""
                                           dojoType="dijit.form.DateTextBox"
                                           required="true"
                                           promptMessage="dd/MM/aaaa"
                                           size="25"
                                           constraints="{max: new Date() ,datePattern:'dd/MM/yyyy'}"
                                           invalidMessage="Fecha no valida.  Usar el formato dd/MM/aaaa." />
                                 </td>
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
               </s:form>
            </td>
         </tr>
         <tr>
            <td height="14" colspan="3"></td>
         </tr>
      </table>
   </body>
</html>
