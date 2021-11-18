<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
   <head>
      <s:url id="autoUrl" action="autocompletarProceso1">
         <s:param name="sTipoDerivacion" value="sTipoDerivacion" />
      </s:url>
      <s:property value="autoUrl" />
      <title>Tramite Documentario</title>
      <style type="text/css">
         @import "js/dojo/dijit/themes/soria/soria.css";
      </style>
      <link href="css/estilo.css" rel="stylesheet" type="text/css">
      <style type="text/css">
         div.falso {
            position: absolute;
            top: -26px;
            left: 80px;
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
            left: 50px;
         }
      </style>
      <script type="text/javascript" src="js/dojo/dojo/dojo.js" djConfig="parseOnLoad:true, isDebug: false"></script>
      <script type="text/javascript" src="js/dojo/dijit/dijit.js" djConfig="parseOnLoad:true, isDebug: false"></script>
      <script type="text/javascript" src="js/siged/upload.js"></script>
      <script type="text/javascript">
         dojo.require("dijit.form.FilteringSelect" );
         dojo.require("dojo.data.ItemFileReadStore");
         dojo.require("dojo.io.iframe");
         dojo.require("dojo.rpc.JsonService");
         dojo.require("dijit.form.NumberTextBox");
         dojo.require("dijit.form.TextBox");
         dojo.require("dijit.form.TimeTextBox");
         dojo.require("dijit.form.DateTextBox");
         dojo.require("dijit.Dialog");
		  dojo.require("dijit.ProgressBar");
         /*dojo.require("dijit.form.ValidationTextBox");
        dojo.require("dojox.validate.regexp");
        dojo.require("dojo.parser");
        dojo.require("dojo.parser");
        dojo.require("dijit.Editor");
        dojo.require("dojo.widget.Editor");*/

         var store = new dojo.data.ItemFileReadStore({url: "<s:property value='autoUrl' />"});
         function derivar(){
            document.forms["frmDerivManual"].action = 'doDerivarUSER.action' ;
            dijit.byId("dlgProgresBar").show() ;  
            document.forms["frmDerivManual"].submit();
         }

         function subir() {
            document.forms[0].action = 'doUploadUserManual.action' ;
            document.forms[0].submit();
         }

         function refrescar() {
            window.opener.parent.location.href = "inicio.action?sTipoFrame=grid";
            this.window.close();
         }

         function desabilitar(){
            var valor = document.getElementById("dialimiteatencion").value;
            var valor1 = document.getElementById("fechalimiteatencion").value ;
            if(valor!="") {
               form.fechalimiteatencion.disabled  = true;
            }else{
               form.fechalimiteatencion.disabled  = false;
            }
         }
      </script>
   </head>

   <body class="soria" <s:if test="cerrar!=null">onload="refrescar();"</s:if>>
      <table width="100%">
         <tr>
            <td height="14" colspan="6"></td>
         </tr>
         <tr align="center">
            <td width="1%" align="center">&nbsp;</td>
            <td colspan="5" align="left">
               <img onClick="derivar()" src="images/enviar.bmp" border="0" alt="Enviar"/>
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
            <td height="16" colspan="4" align="left" class="plomo">
               <div id="divShowFile1"></div>
            </td>
         </tr>
         <tr>
            <td></td>
            <td height="16" colspan="4" align="left" class="plomo">
               <s:form name="frmDerivManual" action="doDerivarUSER" method="POST">
                  <s:hidden name="objDD.iIdDocumento" />
                  <s:hidden name="objDD.iIdProceso" />
                  <s:hidden name="sTipoDerivacion" />
                  <table width="100%">
                     <tr>
                        <td></td>
                        <td width="27%" height="16" align="left" class="plomo">Para:</td>
                        <td colspan="2">
                           <div dojoType="dijit.form.FilteringSelect"
                                store="store"
                                id="iddestinatario"
                                idAttr="id"
                                labelAttr="label"
                                searchAttr="label"
                                name="iddestinatario"
                                size="25"/>
                        </td>
                        <td width="55%">
                           día:<input dojoType="dijit.form.NumberTextBox"
                                      id="objDD.iPlazoDia" name="objDD.iPlazoDia"
                                      style="width:35px;"
                                      maxlength="2"
                                      type="text"
                                      value="" />

                           hora:<input dojoType="dijit.form.NumberTextBox"
                                       id="objDD.iPlazoHora" name="objDD.iPlazoHora"
                                       style="width:35px;"
                                       maxlength="2"
                                       type="text"
                                       value="" />
                           <!-- hora:<input id="objDD.strHorasLimetesAtension" type="text" name="objDD.strHorasLimetesAtension" class="medium" size="3"
                     dojoType="dijit.form.TimeTextBox"
                     constraints="{timePattern:'h:mm:ss a'}"
                     required="true"
                     invalidMessage="Invalid time." />-->

                           <input  type="text" id="objDD.strFechaLimiteAtencion" name="objDD.strFechaLimiteAtencion" size="10"
                                   dojoType="dijit.form.DateTextBox"
                                   required="true"
                                   promptMessage="dd/mm/aaaa"
                                   constraints="{datePattern:'dd/MM/yyyy'}"
                                   invalidMessage="Fecha no valida.  Usar el formato dd/mm/yyyy." />


                        </td>
                        <td width="29%">
                           <!--<img onClick="location.href='derivar1.html'" src="/siged/images/signo_mas.gif" width="15" height="15" border="0" alt="Agregar"/>-->
                        </td>
                     </tr>
                     <tr>
                        <td></td>
                        <td height="16" align="left" class="plomo">Cc: </td>
                        <td colspan="4">
                           <!--s:textfield id="ccdestinatario" name="ccdestinatario" cssClass="cajaMontoTotal" size="50"/>
                  <span id="indicatorCCDest" style="display:none;"><img src="./images/indicator.gif"/></span-->
                           <s:textfield id="idccdestinatario" name="idccdestinatario" cssStyle="display:none"/>
                        </td>
                     </tr>
                     <tr>
                        <td></td>
                        <td height="16" align="left" class="plomo">Asunto: </td>
                        <td colspan="4">
                           <s:textfield name="objDD.strAsunto" cssClass="cajaMontoTotal" size="80" />
                        </td>
                     </tr>
                     <tr>
                        <td></td>
                        <td height="16" align="left" class="plomo">Adjuntos</td>
                        <td colspan="4">
                           <s:if test="#session.uploaded_list != null">
                              <s:iterator value="#session.uploaded_list">
                                 <a href="<s:property value="sURL" />" target="_blank"><s:property value="sNombreReal" /></a><br>
                              </s:iterator>
                           </s:if>
                        </td>
                     </tr>
                     <tr>
                        <td></td>
                        <td height="16" align="left" class="plomo">Mantener en mi bandeja</td>
                        <td colspan="4">
                           <s:checkbox name="bBandeja" />
                        </td>
                     </tr>
                     <s:if test="%{ownerproceso}">
                        <tr>
                           <td></td>
                           <td height="16" align="left" class="plomo">Asignar Responsable</td>
                           <td colspan="4">
                              <s:checkbox name="bResponsable" />
                           </td>
                        </tr>
                     </s:if>
                     <tr>
                        <td></td>
                        <td colspan="5" class="normal">
                           <s:textarea id="ta" name="ta" rows="10" cols="100" />
                        </td>
                     </tr>
                  </table>
               </s:form>
            </td>
         </tr>
         <tr>
            <td height="14"  colspan="6"></td>
         </tr>
      </table>
      	<%@ include file="../pages/util/progressBar.jsp" %>   
   </body>
</html>
