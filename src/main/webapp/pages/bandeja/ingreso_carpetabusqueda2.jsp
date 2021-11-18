<?xml version="1.0" encoding="UTF-8" ?>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es">

   <head>
      <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
      <title>Nueva Carpeta de B&uacute;squeda</title>
      <link rel="stylesheet" type="text/css" href="css/estilo.css" />
      <link rel="stylesheet" type="text/css" media="all" href="css/calendar-green.css" />      
      <link rel="stylesheet" href="runtime/styles/system/aw.css" />
      <%--<link rel="stylesheet" type="text/css" href="js/dojo/dijit/themes/soria/soria.css" />--%>
      <%--link rel="stylesheet" type="text/css" href="css/displaytag.css" />
      <script language="javascript" src="js/switchMenu.js"></script>
      <script language="javascript" src="js/form.js"> </script>
      <script language="javascript" src="js/valida.js"> </script>
      <script language="Javascript" src="js/general.js"></script>
      <script language="JavaScript" src="js/dtreeCarpeta.js"></script--%>
      <script type="text/javascript" src="js/calendar.js"></script>
      <script type="text/javascript" src="js/calendar-es.js"></script>
      <script type="text/javascript" src="js/calendar-setup.js"></script>
      <%--script type="text/javascript" src="js/si.files.js"></script>
      <script type="text/javascript" src="js/sorttable.js"></script>
      <script type="text/javascript" src="runtime/lib/aw.js"></script>
      <script type="text/javascript" src="js/prototype-1.4.0.js"></script>
      <script type="text/javascript" src="js/scriptaculous.js"></script>
      <script type="text/javascript" src="js/overlibmws.js"></script>
      <script type="text/javascript" src="js/ajaxtags-1.2-beta2.js"></script>      
      <script language="JavaScript" src="js/dtreeCarpeta.js"></script>
      <script type='text/javascript' src='dwr/engine.js'> </script>
      <script type='text/javascript' src='dwr/util.js'> </script>
      <script type='text/javascript' src='dwr/interface/toolDwr.js'></script>
      <script type='text/javascript' src='dwr/interface/Tipoenvio.js'></script>
      <script type='text/javascript' src='dwr/interface/Empresadestino.js'></script--%>
		<%--<script type="text/javascript" src="js/dojo/dojo/dojo.js"></script>--%>

      <style type="text/css">
         .aw-quirks * {
            box-sizing: border-box;
            -moz-box-sizing: border-box;
            -webkit-box-sizing: border-box;
         }
         body {font: 12px Tahoma}
      </style>

      <script type="text/javascript" src="js/carpetaBusqueda.js"></script>

      

   </head>
   <body>
      <table>
         <tr>
            <td width="29%" align="left">
               <font color="669BCD">Bienvenido </font><font color="0099FF"><s:property value="#session.nombres" /></font>
            </td>
         </tr>
         <tr>
            <td align="left">
               <font color="0099FF"><a href="<s:url action="doLogout" />" target="_parent">Cerrar Sesi&oacute;n</a></font>
            </td>
         </tr>
      </table>

      <table width="100%">
         <tr>
            <td height="2"  colspan="6"></td>
         </tr>

         <tr>
            <td height="20"colspan="6" class="titulo" width="99%">
               <table width="52%" border="0" height="20" align="left">
                  <tr>
                     <td> <img src="images//regresar.bmp" border="0" onclick="regresar()" alt="Regresar"/></td>
                     <td bgcolor="#BFD9F1" align="center" class="tituloRojo">Ingresos Carpeta de Busqueda</td>
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
               <s:form id="frmNuevaCarpetaBusqueda" name="foringcarbusqueda" action="doSaveCarpetaBusqueda" method="post">
                  <table width="100%"  border="1" align="center" bordercolor="#669BCD" bgcolor="#BFD9F1">
                     <tr>
                        <td height="335" >
                           <table width="750" border="0" cellpadding="0" cellspacing="0">
                              <tr>
                                 <td>Nombre de Carpeta:</td>
                                 <td>&nbsp;</td>
                                 <td><s:textfield  id="nombrecarpeta" name="objCB.nombrecarpeta" value="" cssClass="cajaMontoTotal"/></td>
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
                                 <td width="1"><s:textfield id="nroexpediente" name="objCB.nroexpediente" cssClass="cajaMontoTotal"/></td>
                                 <td width="120">&nbsp;</td>
                                 <td width="150">Tipo Documento</td>
                                 <td width="55">&nbsp;</td>
                                 <td width="100">
                                    <%--s:textfield name="objCB.tipodocumento" cssClass="cajaMontoTotal"/--%>
                                    <%--<select id="objCB.tipodocumento"></select>--%>
                                    <div id="fsTipoDoc" />
                                    <input type="hidden" id="tipo" />
                                 </td>
                                 <td>&nbsp;</td>
                                 <td>&nbsp;</td>
                              </tr>
                              <tr>
                                 <td>Nro Documento : </td>
                                 <td>&nbsp;</td>
                                 <td><s:textfield id="nrodocumento" name="objCB.nrodocumento" cssClass="cajaMontoTotal"/></td>
                                 <td>&nbsp;</td>
                                 <td>Doc. Ident. Cliente </td>  
                                 <td>&nbsp;</td>
                                 <td><s:textfield id="documentoidentidad" name="objCB.documentoidentidad" cssClass="cajaMontoTotal"/></td>
                                 <td>&nbsp;</td>
                                 <td>&nbsp;</td>
                              </tr>
                              <tr>
                                 <td>Nro M. Partes:</td>
                                 <td>&nbsp;</td>
                                 <td><s:textfield id="nromesapartes" name="objCB.nromesapartes" cssClass="cajaMontoTotal"/></td>
                                 <td>&nbsp;</td>
                                 <td>Fch. Crea. Documento </td>
                                 <td><div align="right">Desde:</div></td>
                                 <td><s:textfield name="objCB.strfechadocumentoinicio" id="finidoc" cssClass="cajaMontoTotal" value="" /><input alt="Calendario" class="cajaFecha" id="lanzador3" value="..." type="button"></td>
                                 <td></td>
                                 <td>Hasta:<s:textfield name="objCB.strfechadocumentofinal" id="ffindoc" cssClass="cajaMontoTotal" value="" /><input alt="Calendario" class="cajaFecha" id="lanzador4" value="..." type="button"></td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td>Concesionario:</td>
                                 <td>&nbsp;</td>
                                 <td><s:textfield id="consecionario" name="objCB.consecionario" cssClass="cajaMontoTotal"/></td>
                                 <td>&nbsp;</td>
                                 <td>Fch. Crea. Expediente </td>
                                 <td ><div align="right">Desde:</div></td>
                                 <td ><s:textfield name="objCB.strfechaexpedienteinicio" id="finiexp" cssClass="cajaMontoTotal" value="" /><input alt="Calendario" class="cajaFecha" id="lanzador1" value="..." type="button"></td>
                                 <td >&nbsp;</td>
                                 <td >Hasta:<s:textfield name="objCB.strfechaexpedientefinal" id="ffinexp" cssClass="cajaMontoTotal" value="" /><input alt="Calendario" class="cajaFecha" id="lanzador2" value="..." type="button"></td>
                                 <td ></td>
                              </tr>
                              <tr>
                                 <td>Cliente:</td>
                                 <td>&nbsp;</td>
                                 <td><s:textfield id="cliente" name="objCB.cliente" cssClass="cajaMontoTotal"/></td>
                                 <td>&nbsp;</td>
                                 <td>Direccion Principal</td>
                                 <td>&nbsp;</td>
                                 <td colspan="3"><s:textfield id="direccioncliente"    name="objCB.direccioncliente" cssClass="cajaMontoTotal"/></td>
                                 <td>&nbsp;</td>
                                 <td>&nbsp;</td>

                              </tr>
                              <tr>
                                 <td>Area Origen:</td>
                                 <td>&nbsp;</td>
                                 <td><s:textfield id="areaorigen" name="objCB.areaorigen" cssClass="cajaMontoTotal"/></td>
                                 <td>&nbsp;</td>
                                 <td>Area Destino:</td>
                                 <td>&nbsp;</td>
                                 <td><s:textfield id="areadestino" name="objCB.areadestino" cssClass="cajaMontoTotal"/></td>
                                 <td>&nbsp;</td>
                                 <td>&nbsp;</td>
                              </tr>
                              <tr>
                                 <td>Propietario:</td>
                                 <td>&nbsp;</td>
                                 <td><s:textfield id="propietario" name="objCB.propietario" cssClass="cajaMontoTotal"/></td>
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
                                    <s:textfield id="proceso" name="objCB.proceso" cssClass="cajaMontoTotal"/>
                                 </td>
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
                                 <td><input type="button" onclick="enviar('frmNuevaCarpetaBusqueda');" value="Guardar" class="botonGerman" /></td>
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


