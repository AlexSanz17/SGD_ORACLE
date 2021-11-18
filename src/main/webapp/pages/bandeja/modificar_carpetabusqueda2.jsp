<?xml version="1.0" encoding="UTF-8" ?>	
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es">
	<head>
		<title>Modificar Carpeta de B&uacute;squeda</title>
		<%--<link rel="stylesheet" type="text/css" href="css/estilo.css" />      --%>
		<link rel="stylesheet" type="text/css" media="all" href="css/calendar-green.css" />      
		<link rel="stylesheet" href="runtime/styles/system/aw.css" />
		<link rel="stylesheet" type="text/css" href="js/dojo/dijit/themes/soria/soria.css" />
		<script type="text/javascript" src="js/calendar.js"></script>
		<script type="text/javascript" src="js/calendar-es.js"></script>
		<script type="text/javascript" src="js/calendar-setup.js"></script>
		<%--<script type="text/javascript" src="js/dojo/dojo/dojo.js"></script>--%>
		<script type="text/javascript" src="js/carpetaBusqueda.js"></script>

      <style type="text/css">
         .aw-quirks * {
            box-sizing: border-box;
            -moz-box-sizing: border-box;
            -webkit-box-sizing: border-box;
         }
         body {font: 12px Tahoma}
      </style>

      <meta http-equiv="Content-Type" content="text/html; utf-8" />
   </head>
   <body>
      <table width="100%">
         <tr>
            <td height="2"  colspan="6"></td>
         </tr>
         <tr>
            <td height="20"colspan="6" class="titulo" width="99%">
               <table width="52%" border="0" height="20" align="left">
                  <tr>
                     <td bgcolor="#BFD9F1" align="center" class="tituloRojo">Modificacion Carpeta de Busqueda: <s:property value="objCB.nombrecarpeta"/></td>
                     <td bgcolor="#fffff" align="center" class="tituloRojo">&nbsp;&nbsp;</td>
                  </tr>
               </table>
            </td>
         </tr>


         <!--////////////////////////////////////////-->
         <tr id="repHeader1">
            <td height="10" colspan="6">
            </td>
         </tr>


         <tr >
            <td height="14" colspan="6">
               <s:form id="frmModificarCarpetaBusqueda" name="foringcarbusqueda" action="doSaveCarpetaBusqueda"  method="Post">
                  <table width="100%"  border="1" align="center" bordercolor="#669BCD" bgcolor="#BFD9F1">
                     <s:textfield name="objCB.idcarpetabusqueda" value="%{objCB.idcarpetabusqueda}" cssStyle="display:none" />
                     <tr>
                        <td height="335" >
                           <table width="750" border="0" cellpadding="0" cellspacing="0">
                              <tr>
                                 <td>Nombre de Carpeta:<s:property value="objCB.nombrecarpeta" /></td>
                                 <td>&nbsp;</td>
                                 <td><s:textfield id="nombrecarpeta" name="objCB.nombrecarpeta" value="%{objCB.nombrecarpeta}" cssClass="cajaMontoTotal"/></td>
                                 <td>&nbsp;</td>
                                 <td>&nbsp;</td>
                                 <td>&nbsp;</td>
                                 <td>&nbsp;</td>
                                 <td>&nbsp;</td>
                                 <td>&nbsp;</td>
                              </tr>
                              <tr>
                                 <td width="150">Nro Expediente: </td>
                                 <td width="20">&nbsp;</td>
                                 <td width="1"><s:textfield id="nroexpediente" name="objCB.nroexpediente" value="%{objCB.nroexpediente}" cssClass="cajaMontoTotal"/></td>
                                 <td width="120">&nbsp;</td>
                                 <td width="150">Tipo Documento</td>
                                 <td width="55">&nbsp;</td>
                                 <td width="100">
                                    <%--<select id="objCB.tipodocumento"></select>--%>
                                    <div id="fsTipoDoc" />
                                    <input type="hidden" id="tipo" value="${objCB.tipodocumento}" />
                                 </td>
                                 <td>&nbsp;</td>
                                 <td>&nbsp;</td>
                              </tr>
                              <tr>
                                 <td>Nro Documento : </td>
                                 <td>&nbsp;</td>
                                 <td><s:textfield id="nrodocumento"  name="objCB.nrodocumento" value="%{objCB.nrodocumento}" cssClass="cajaMontoTotal"/></td>
                                 <td>&nbsp;</td>
                                 <td>Doc. Ident. Cliente </td>
                                 <td>&nbsp;</td>
                                 <td><s:textfield id="documentoidentidad" name="objCB.documentoidentidad" value="%{objCB.documentoidentidad}" cssClass="cajaMontoTotal"/></td>
                                 <td>&nbsp;</td>
                                 <td>&nbsp;</td>
                              </tr>
                              <tr>
                                 <td>Nro M. Partes:</td>
                                 <td>&nbsp;</td>
                                 <td><s:textfield id="nromesapartes"  name="objCB.nromesapartes" value="%{objCB.nromesapartes}" cssClass="cajaMontoTotal"/></td>
                                 <td>&nbsp;</td>
                                 <td>Fch. Crea. Documento </td>
                                 <td><div align="right">Desde:</div></td>
                                 <td>
                                    <s:date name="objCB.fechadocumentoinicio" id="idFechaDocumentoInicio" format="dd/MM/yyyy" />
                                    <s:textfield name="objCB.strfechadocumentoinicio" value="%{idFechaDocumentoInicio}" id="finidoc" cssClass="cajaMontoTotal"/>
                                    <input alt="Calendario" class="cajaFecha" id="lanzador3" value="..." type="button">
                                 </td>
                                 <td></td>
                                 <td>
                                    Hasta:
                                    <s:date name="objCB.fechadocumentofinal" id="idFechaDocumentoFinal" format="dd/MM/yyyy" />
                                    <s:textfield name="objCB.strfechadocumentofinal" value="%{idFechaDocumentoFinal}" id="ffindoc" cssClass="cajaMontoTotal"/>
                                    <input alt="Calendario" class="cajaFecha" id="lanzador4" value="..." type="button">
                                 </td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td>Consecionario:</td>
                                 <td>&nbsp;</td>
                                 <td><s:textfield  id="consecionario"  name="objCB.consecionario" cssClass="cajaMontoTotal"/></td>
                                 <td>&nbsp;</td>
                                 <td>Fch. Crea. Expediente </td>
                                 <td><div align="right">Desde:</div></td>
                                 <td>
                                    <s:date name="objCB.fechaexpedienteinicio" id="idFechaExpedienteInicio" format="dd/MM/yyyy" />
                                    <s:textfield name="objCB.strfechaexpedienteinicio" value="%{idFechaExpedienteInicio}" id="finiexp" cssClass="cajaMontoTotal"/>
                                    <input alt="Calendario" class="cajaFecha" id="lanzador1" value="..." type="button">
                                 </td>
                                 <td>&nbsp;</td>
                                 <td>
                                    Hasta:
                                    <s:date name="objCB.fechaexpedientefinal" id="idFechaExpedienteFinal" format="dd/MM/yyyy" />
                                    <s:textfield name="objCB.strfechaexpedientefinal" value="%{idFechaExpedienteFinal}" id="ffinexp" cssClass="cajaMontoTotal" />
                                    <input alt="Calendario" class="cajaFecha" id="lanzador2" value="..." type="button">
                                 </td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td>Cliente:</td>
                                 <td>&nbsp;</td>
                                 <td><s:textfield id="cliente" name="objCB.cliente" value="%{objCB.cliente}" cssClass="cajaMontoTotal"/></td>
                                 <td>&nbsp;</td>
                                 <td>Direccion Principal</td>
                                 <td>&nbsp;</td>
                                 <td colspan="3"><s:textfield id="direccioncliente" name="objCB.direccioncliente" value="%{objCB.direccioncliente}" cssClass="cajaMontoTotal"/></td>
                                 <td>&nbsp;</td>
                                 <td>&nbsp;</td>

                              </tr>
                              <tr>
                                 <td>Area Origen:</td>
                                 <td>&nbsp;</td>
                                 <td><s:textfield  id="areaorigen"  name="objCB.areaorigen" value="%{objCB.areaorigen}" cssClass="cajaMontoTotal"/></td>
                                 <td>&nbsp;</td>
                                 <td>Area Destino:</td>
                                 <td>&nbsp;</td>
                                 <td><s:textfield  id="areadestino" name="objCB.areadestino" value="%{objCB.areadestino}" cssClass="cajaMontoTotal"/></td>
                                 <td>&nbsp;</td>
                                 <td>&nbsp;</td>
                              </tr>
                              <tr>
                                 <td>Propietario:</td>
                                 <td>&nbsp;</td>
                                 <td><s:textfield   id="propietario" name="objCB.propietario" value="%{objCB.propietario}" cssClass="cajaMontoTotal"/></td>
                                 <td>&nbsp;</td>
                                 <td>&nbsp;</td>
                                 <td>&nbsp;</td>
                                 <td>&nbsp;</td>
                                 <td>&nbsp;</td>
                                 <td>&nbsp;</td>
                              </tr>
                              <tr>
                                 <td>Proceso:</td>
                                 <td>&nbsp;</td>
                                 <td>
                                    <s:textfield  id="proceso" name="objCB.proceso" value="%{objCB.proceso}" cssClass="cajaMontoTotal"/>
                                 </td>
                                 <td>&nbsp;</td>
                                 <td>&nbsp;</td>
                                 <td >&nbsp;</td>
                                 <td>&nbsp;</td>
                                 <td>&nbsp;</td>
                                 <td>&nbsp;</td>
                              </tr>
                              <tr>
                                 <td>&nbsp;</td>
                                 <td>&nbsp;</td>
                                 <td>&nbsp;</td>
                                 <td>&nbsp;</td>
                                 <td>&nbsp;</td>
                                 <td>&nbsp;</td>
                                 <td>&nbsp;</td>
                                 <td>&nbsp;</td>
                                 <td>&nbsp;</td>
                              </tr>
                              <tr>
                                 <td>&nbsp;</td>
                                 <td>&nbsp;</td>
                                 <td>&nbsp;</td>
                                 <td>&nbsp;</td>
                                 <td>&nbsp;</td>
                                 <td>&nbsp;</td>
                                 <td>&nbsp;</td>
                                 <td>&nbsp;</td>
                                 <td>&nbsp;</td>
                              </tr>
                              <tr>
                                 <td><input type="button" onclick="enviar('frmModificarCarpetaBusqueda');" value="Guardar.." class="button"></td>
                                 <td>&nbsp;</td>
                                 <td>&nbsp;</td>
                                 <td>&nbsp;</td>
                                 <td>&nbsp;</td>
                                 <td>&nbsp;</td>
                                 <td>&nbsp;</td>
                                 <td>&nbsp;</td>
                                 <td>&nbsp;</td>
                              </tr>
                           </table>
                        </td>
                     </tr>
                  </table>
               </s:form>
            </td>
         </tr>
      </table>

      <script type="text/javascript">
         Calendar.setup({
            inputField     :    "finiexp",      // id del campo de texto
            ifFormat       :    "%d/%m/%Y",       // formato de la fecha, cuando se escriba en el campo de texto
            button         :    "lanzador1"   // el id del bot?n que lanzar? el calendario
         });

         Calendar.setup({
            inputField     :    "ffinexp",      // id del campo de texto
            ifFormat       :    "%d/%m/%Y",       // formato de la fecha, cuando se escriba en el campo de texto
            button         :    "lanzador2"   // el id del bot?n que lanzar? el calendario
         });

      </script>
      <script type="text/javascript">
         Calendar.setup({
            inputField     :    "finidoc",      // id del campo de texto
            ifFormat       :    "%d/%m/%Y",       // formato de la fecha, cuando se escriba en el campo de texto
            button         :    "lanzador3"   // el id del bot?n que lanzar? el calendario
         });

         Calendar.setup({
            inputField     :    "ffindoc",      // id del campo de texto
            ifFormat       :    "%d/%m/%Y",       // formato de la fecha, cuando se escriba en el campo de texto
            button         :    "lanzador4"   // el id del bot?n que lanzar? el calendario
         });

      </script>
   </body>
</html>


