<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://ajaxtags.org/tags/ajax" prefix="ajax"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
   <head>
      <title>Tramite Documentario</title>
      <link href="css/estilo.css" rel="stylesheet" type="text/css">
      <script language="javascript" src="js/form.js"> </script>
      <script language="Javascript" src="js/general.js"></script>
      <link rel="stylesheet" type="text/css" media="all" href="css/calendar-green.css">
      <script type="text/javascript" src="js/calendar.js"></script>
      <script type="text/javascript" src="js/calendar-es.js"></script>
      <script type="text/javascript" src="js/calendar-setup.js"></script>

      <script type="text/javascript" src="./js/prototype-1.4.0.js"></script>
      <script type="text/javascript" src="./js/scriptaculous.js"></script>
      <script type="text/javascript" src="./js/overlibmws.js"></script>
      <script type="text/javascript" src="./js/ajaxtags-1.2-beta2.js"></script>
      <link rel="stylesheet" type="text/css" href="./css/ajaxtags.css" />
      <link rel="stylesheet" type="text/css" href="./css/displaytag.css" />

      <script language="JavaScript">
         function aprobar(){
            document.forms["frmObsStor"].submit();
            self.parent.opener.parent.frames["mainFrame"].location.href = "/siged/doBody.action";
            this.window.close();
         }
      </script>
      <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"><style type="text/css">
         <!--
         body {
            background-color: #DDEDFF;
         }
         -->
      </style>
   </head>

   <body topmargin="0" leftmargin="0" rigthmargin="0"


   >
      <s:form name="frmObsStor" action="doAprobarStor" method="POST">
         <table width="103%">
            <tr>
               <td height="14" colspan="2">
                  <s:textfield name="objDD.iIdDocumento" cssStyle="display:none" />
               </td>
            </tr>
            <tr>
               <td height="13" colspan="2" class="header"><div align="center">El expediente sera aprobado</div></td>
            </tr>
            <tr>
               <td height="13" colspan="2" class="header"></td>
            </tr>
            <s:if test="#session.usuario.equals('scalificador')">
               <tr>
                  <td height="14" colspan="2">
                     <table width="58%" height="73" border="1" style="border-style:solid" align="center" bordercolor="#669BCD" bgcolor="#BFD9F1">
                        <tr class="altRow2">
                           <td width="11%" height="9"></td>
                        </tr>
                        <tr class="altRow2">
                        <td width="11%" height="68">
                           <table align="center" border="0" width="100%">
                              <tr>
                                 <td width="136">Sala</td>
                                 <td width="303">
                                    <s:textfield id="sala" name="sala" cssClass="cajaMontoTotal" size="25" />
                                    <span id="indicatorSal" style="display:none;"><img src="./images/indicator.gif"/></span>
                                    <s:textfield id="idsala" name="idsala" cssStyle="display:none"/>

                                    <ajax:autocomplete
                                       source="sala"
                                       target="idsala"
                                       baseUrl="./autocompletar.view?metodo=ObtenerSala"
                                       className="autocomplete"
                                       indicator="indicatorSal"
                                       minimumCharacters="1"
                                       parser="new ResponseXmlToHtmlListParser()" />
                                 </td>
                              </tr>
                              <tr>
                                 <td width="136">Tipo de Analisis</td>
                                 <td width="303">
                                    <s:textfield name="objDD.strTipoAnalisis" cssClass="cajaMontoTotal" size="25" />
                                 </td>
                              </tr>
                           </table>
                        </td>
                     </table>
                  </td>
               </tr>
            </s:if>
            <s:elseif test="#session.usuario.equals('salau1') && objDD.iIdEtapa == 2">
               <tr>
                  <td height="14" colspan="2">
                     <table width="58%" height="73" border="1" style="border-style:solid" align="center" bordercolor="#669BCD" bgcolor="#BFD9F1">
                        <tr class="altRow2">
                           <td width="11%" height="9"></td>
                        </tr>
                        <tr class="altRow2">
                        <td width="11%" height="68">
                           <table align="center" border="0" width="100%">
                              <tr>
                                 <td width="136">Sala</td>
                                 <td width="303">
                                    <s:property value="objDD.strSala" />
                                 </td>
                              </tr>
                              <tr>
                                 <td width="136">Otra Sala</td>
                                 <td width="303">
                                    <input name="sala" type="checkbox" value="">
                                 </td>
                              </tr>
                              <tr>
                                 <td width="136">Analista</td>
                                 <td width="303">
                                    <s:textfield name="objDD.strAnalista" cssClass="cajaMontoTotal" size="25" />
                                 </td>
                              </tr>
                           </table>
                        </td>
                     </table>
                  </td>
               </tr>
            </s:elseif>
            <s:elseif test="#session.usuario.equals('salau1') && objDD.iIdEtapa == 6">
               <tr>
                  <td height="14" colspan="2">
                     <table width="58%" height="73" border="1" style="border-style:solid" align="center" bordercolor="#669BCD" bgcolor="#BFD9F1">
                        <tr class="altRow2">
                           <td width="11%" height="9"></td>
                        </tr>
                        <tr class="altRow2">
                        <td width="11%" height="68">
                           <table align="center" border="0" width="100%">
                              <tr>
                                 <td width="136">Cumple Requisitos?</td>
                                 <td width="303">
                                    <s:radio list="mapCumpleRequisito" name="objDD.iIdCumpleRequisito" />
                                 </td>
                              </tr>
                           </table>
                        </td>
                     </table>
                  </td>
               </tr>
            </s:elseif>
            <s:elseif test="#session.usuario.equals('analista1')">
               <tr>
                  <td height="14" colspan="2">
                     <table width="58%" height="73" border="1" style="border-style:solid" align="center" bordercolor="#669BCD" bgcolor="#BFD9F1">
                        <tr class="altRow2">
                           <td width="11%" height="9"></td>
                        </tr>
                        <tr class="altRow2">
                        <td width="11%" height="68">
                           <table align="center" border="0" width="100%">
                              <tr>
                                 <td width="136">Revisor Tecnico</td>
                                 <td width="303">
                                    <s:textfield name="objDD.strRevisorTecnico" cssClass="cajaMontoTotal" size="25" />
                                 </td>
                              </tr>
                           </table>
                        </td>
                     </table>
                  </td>
               </tr>
            </s:elseif>
            <s:elseif test="#session.usuario.equals('revtec1')">
               <tr>
                  <td height="14" colspan="2">
                     <table width="58%" height="73" border="1" style="border-style:solid" align="center" bordercolor="#669BCD" bgcolor="#BFD9F1">
                        <tr class="altRow2">
                           <td width="11%" height="9"></td>
                        </tr>
                        <tr class="altRow2">
                        <td width="11%" height="68">
                           <table align="center" border="0" width="100%">
                              <tr>
                                 <td width="136">Revisor Legal</td>
                                 <td width="303">
                                    <s:textfield name="objDD.strRevisorLegal" cssClass="cajaMontoTotal" size="25" />
                                 </td>
                              </tr>
                           </table>
                        </td>
                     </table>
                  </td>
               </tr>
            </s:elseif>
            <s:elseif test="#session.usuario.equals('asala1') && objDD.iIdEtapa == 6">
               <tr>
                  <td height="14" colspan="2">
                     <table width="58%" height="73" border="1" style="border-style:solid" align="center" bordercolor="#669BCD" bgcolor="#BFD9F1">
                        <tr class="altRow2">
                           <td width="11%" height="9"></td>
                        </tr>
                        <tr class="altRow2">
                        <td width="11%" height="68">
                           <table align="center" border="0" width="100%">
                              <tr>
                                 <td width="136">Fecha de Aprobaci&oacute;n</td>
                                 <td width="303">
                                    <s:textfield name="objDD.strFechaAprobacion" id="fechaaprobacion" cssClass="cajaMontoTotal" size="25" />
                                    <input alt="Calendario" class="cajaFecha" id="calfechaaprobacion" value="..." type="button">
                                 </td>
                              </tr>
                              <tr>
                                 <td width="136">Numero de Resoluci&oacute;n</td>
                                 <td width="303">
                                    <s:textfield name="objDD.iNroResolucion" cssClass="cajaMontoTotal" size="25" />
                                 </td>
                              </tr>
                              <tr>
                                 <td width="136">Resultado</td>
                                 <td width="303">
                                    <s:textfield name="objDD.strResultado" cssClass="cajaMontoTotal" size="25" />
                                 </td>
                              </tr>
                           </table>
                        </td>
                     </table>
                  </td>
               </tr>
            </s:elseif>
            <s:elseif test="#session.usuario.equals('asala1') && objDD.iIdEtapa == 7">
               <tr>
                  <td height="14" colspan="2">
                     <table width="58%" height="73" border="1" style="border-style:solid" align="center" bordercolor="#669BCD" bgcolor="#BFD9F1">
                        <tr class="altRow2">
                           <td width="11%" height="9"></td>
                        </tr>
                        <tr class="altRow2">
                        <td width="11%" height="68">
                           <table align="center" border="0" width="100%">
                              <tr>
                                 <td width="136">Fecha de Notificaci&oacute;n a reclamante</td>
                                 <td width="303">
                                    <s:textfield name="objDD.strFechaReclamante" id="fechareclamante" cssClass="cajaMontoTotal" size="25" />
                                    <input alt="Calendario" class="cajaFecha" id="calfechareclamante" value="..." type="button">
                                 </td>
                              </tr>
                              <tr>
                                 <td width="136">Fecha de Notificaci&oacute;n a Concesionario</td>
                                 <td width="303">
                                    <s:textfield name="objDD.strFechaConcesionario" id="fechaconcesionario" cssClass="cajaMontoTotal" size="25" />
                                    <input alt="Calendario" class="cajaFecha" id="calfechaconcesionario" value="..." type="button">
                                 </td>
                              </tr>
                              <tr>
                                 <td width="136">Fecha de Cumplimiento de Resoluci&oacute;n</td>
                                 <td width="303">
                                    <s:textfield name="objDD.strFechaResolucion" id="fecharesolucion" cssClass="cajaMontoTotal" size="25" />
                                    <input alt="Calendario" class="cajaFecha" id="calfecharesolucion" value="..." type="button">
                                 </td>
                              </tr>
                           </table>
                        </td>
                     </table>
                  </td>
               </tr>
            </s:elseif>
            <s:elseif test="#session.usuario.equals('atramdoc')">
               <tr>
                  <td height="14" colspan="2">
                     <table width="58%" height="73" border="1" style="border-style:solid" align="center" bordercolor="#669BCD" bgcolor="#BFD9F1">
                        <tr class="altRow2">
                           <td width="11%" height="9"></td>
                        </tr>
                        <tr class="altRow2">
                        <td width="11%" height="68">
                           <table align="center" border="0" width="100%">
                              <tr>
                                 <td width="136">Derivaci&oacute;n</td>
                                 <td width="303">
                                    Archivo &nbsp;&nbsp;<input name="deriva" type="radio" value="">
                                    Verificaci&oacute;n &nbsp;&nbsp;<input name="deriva" type="radio" value="">
                                 </td>
                              </tr>
                           </table>
                        </td>
                     </table>
                  </td>
               </tr>
            </s:elseif>
            <tr>
               <td colspan="2" align="center">
                  <input type="button" name="button" value="Aceptar" class="button" onClick="aprobar()" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                  <input type="button" name="button2" value="Cancelar" class="button" onClick="window.close()" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
               </td>
            </tr>
            <tr align="center">
               <td colspan="2" align="center">&nbsp;</td>
            </tr>
         </table>
      </s:form>
      <s:if test="#session.usuario.equals('asala1') && objDD.iIdEtapa == 6">
      <script type="text/javascript">
         Calendar.setup({
            inputField     :    "fechaaprobacion", //id del campo de texto
            ifFormat       :    "%Y-%m-%d",    //formato de la fecha, cuando se escriba en el campo de texto
            button         :    "calfechaaprobacion"    //el id del botón que lanzará el calendario
         });
      </script>
      </s:if>
      <s:elseif test="#session.usuario.equals('asala1') && objDD.iIdEtapa == 7">
      <script type="text/javascript">
         Calendar.setup({
            inputField     :    "fechareclamante",
            ifFormat       :    "%Y-%m-%d",
            button         :    "calfechareclamante"
         });
         Calendar.setup({
            inputField     :    "fechaconcesionario",
            ifFormat       :    "%Y-%m-%d",
            button         :    "calfechaconcesionario"
         });
         Calendar.setup({
            inputField     :    "fecharesolucion",
            ifFormat       :    "%Y-%m-%d",
            button         :    "calfecharesolucion"
         });
      </script>
      </s:elseif>
   </body>
</html>
