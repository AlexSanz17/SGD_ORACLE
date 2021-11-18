<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
   <head>
      <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
      <title>Tramite Documentario</title>
      <style type="text/css">
         @import "js/dojo/dijit/themes/soria/soria.css";
         @import "js/dojo/dojox/grid/resources/soriaGrid.css";
         @import "css/estilo.css";
      </style>
      <script type="text/javascript" src="js/dojo/dojo/dojo.js" djConfig="parseOnLoad:true, isDebug: false"></script>
      <script type="text/javascript">
         dojo.require("dijit.form.DateTextBox");
         dojo.require("dojo.data.ItemFileReadStore");
         dojo.require("dojox.grid.DataGrid");
         
         var storeAuditoria; 
         
      </script>
      <script type="text/javascript">
         function filtrarAuditoria() {
            var parameters = '';
            var nroexpediente = dojo.byId('nroexpediente').value;
            var usuario = dojo.byId('usuario').value;
            var modulo = dojo.byId('modulo').value;
            var documento = dojo.byId('documento').value;
            var fechadesde = dojo.byId('fechadesde').value;
            var fechahasta = dojo.byId('fechahasta').value;

            console.log("Nro Expediente [" + nroexpediente + "]");
            console.log("Usuario [" + usuario + "]");
            console.log("Modulo [" + modulo + "]");
            console.log("Documento [" + documento + "]");
            console.log("Fecha Desde [" + fechadesde + "]");
            console.log("Fecha Hasta [" + fechahasta + "]");

            parameters += "sNroExpediente=" + nroexpediente + "&";
            parameters += "sUsuario=" + usuario + "&";
            parameters += "sModulo=" + modulo + "&";
            parameters += "sNroDocumento=" + documento + "&";
            parameters += "sFechaDesde=" + fechadesde + "&";
            parameters += "sFechaHasta=" + fechahasta;

            storeAuditoria = new dojo.data.ItemFileReadStore({url: "enviarGridauditoria.action?" + parameters});

            dijit.byId('grdAuditoria').setStore(storeAuditoria);
         }

         function cleanFields() {
            dojo.byId('nroexpediente').value = "";
            dojo.byId('usuario').value = "";
            dojo.byId('modulo').value = "";
            dojo.byId('documento').value = "";
            dojo.byId('fechadesde').value = "";
            dojo.byId('fechahasta').value = "";
         }
      </script>
   </head>
   <body class="soria">
      <table width="100%">
         <tr>
            <td height="4"  colspan="6"></td>
         </tr>
         <tr>
            <td height="20" colspan="6" width="99%">
               <table width="99%" border="0" align="center">
                  <td width="0%" height="14" rowspan="2"> </td>
                  <td width="29%" align="left">
                     <font color="669BCD">Bienvenido </font><font color="0099FF"><s:property value="#session.nombres" /></font>
                  </td>
                  <td width="19%" rowspan="2"></td>
                  <td width="34%" rowspan="2" align="right"></td>
                  <td width="16%" rowspan="2" align="right"></td>
                  <td width="2%" rowspan="2"></td>
                  <tr>
                  <td align="left">
                     <font color="0099FF"><a href="<s:url action="doLogout" />" target="_parent">Cerrar Sesi&oacute;n</a></font>
                  </td>
               </table>
            </td>
         </tr>
         <tr>
            <td height="4" colspan="6"></td>
         </tr>
         <tr>
            <td height="20"colspan="6" class="titulo" width="99%">
               <table width="70%" border="0" height="20" align="left">
                  <tr>
                     <td bgcolor="#4481B8" width="30%" align="center" class="tituloRojo">Mantenimiento de Auditor&iacute;a</td>
                     <td></td>
                  </tr>
               </table>
            </td>
         </tr>
         <tr>
            <td>
               <table width="100%">
                  <tr>
                     <td height="14" colspan="6">
                        <table width="100%" align="left">
                           <tr>
                              <td align="left">
                                 <img src="images/buscar.bmp" border="0" onClick="filtrarAuditoria()" alt="Buscar" style="cursor:pointer" />
                                 <img src="images/Limpiar.bmp" border="0" onClick="cleanFields()" alt="Limpiar" style="cursor:pointer" />
                              </td>
                           </tr>
                        </table>
                     </td>
                  </tr>
                  <tr>
                     <td height="4" colspan="6" class="header"></td>
                  </tr>
                  <tr>
                     <td height="14" colspan="6">
                        <table width="95%"  border="1" align="left" bordercolor="#669BCD" bgcolor="#BFD9F1">
                           <tr>
                              <td height="100">
                                 <table width="100%" height="97" align="center">
                                    <tr>
                                       <td width="1%"></td>
                                       <td width="19%" height="5"></td>
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
                                       <td colspan="5"><s:textfield cssClass="dijitReset" id="nroexpediente" name="nroexpediente" size="20" /></td>
                                       <td></td>
                                    </tr>
                                    <tr>
                                       <td></td>
                                       <td height="16" align="left">Usuario</td>
                                       <td colspan="5"><s:textfield cssClass="dijitReset" id="usuario" name="usuario" size="20" /></td>
                                       <td></td>
                                    </tr>
                                    <tr>
                                       <td></td>
                                       <td height="16" align="left">M&oacute;dulo</td>
                                       <td colspan="5"><s:textfield cssClass="dijitReset" id="modulo" name="modulo" size="20" /></td>
                                       <td></td>
                                    </tr>
                                    <tr>
                                       <td></td>
                                       <td height="16" align="left">Documento</td>
                                       <td colspan="5"><s:textfield cssClass="dijitReset" id="documento" name="documento" size="20" /></td>
                                       <td></td>
                                    </tr>
                                    <tr>
                                       <td height="5"></td>
                                    </tr>
                                    <tr>
                                       <td></td>
                                       <td height="16" align="left" class="titulo">Intervalo de Fechas</td>
                                       <td colspan="5"></td>
                                       <td></td>
                                    </tr>
                                    <tr>
                                       <td></td>
                                       <td height="16" align="left">Desde</td>
                                       <td>
                                          <input id="fechadesde"
                                                 name="fechadesde"
                                                 type="text"
                                                 dojoType="dijit.form.DateTextBox"
                                                 required="false"
                                                 promptMessage="dd/MM/yyyy"
                                                 size="20"
                                                 constraints="{datePattern:'dd/MM/yyyy'}"
                                                 onChange="dijit.byId('fechahasta').constraints.min = this.getValue();" />
                                       </td>
                                       <td height="16" align="left">Hasta</td>
                                       <td colspan="2">
                                          <input id="fechahasta"
                                                 name="fechahasta"
                                                 type="text"
                                                 dojoType="dijit.form.DateTextBox"
                                                 required="false"
                                                 promptMessage="dd/MM/yyyy"
                                                 size="20"
                                                 constraints="{datePattern:'dd/MM/yyyy'}"
                                                 onChange="dijit.byId('fechadesde').constraints.max = this.getValue();" />
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
                     </td>
                  </tr>
                  <tr>
                     <td height="4" colspan="6" class="header"></td>
                  </tr>
               </table>
            </td>
         </tr>
         <tr>
            <td colspan="6">
               <table dojoType='dojox.grid.DataGrid' id='grdAuditoria' jsid='grdAuditoria' store='storeAuditoria' style='width:100%;height:700px;'>
                  <thead>
                     <tr>
                        <th width='5px' styles='text-align:center;'></th>
                        <th field='id' width='30px' editable='false'>Nro</th>
                        <th field='label' width='80px' editable='false'>Tipo</th>
                        <th field='modulo' width='80px' editable='false'>M&oacute;dulo</th>
                        <th field='opcion' width='60px' editable='false'>Opci&oacute;n</th>
                        <th field='campo' width='70px' editable='false'>Campo</th>
                        <th field='antiguo' width="90px" hidden="false">Valor Antiguo</th>
                        <th field='actual' width="90px" hidden="false">Valor Actual</th>
                        <th field='fecha' width='80px' editable='false' align="center">Fecha</th>
                        <th field='usuario' width='50px' editable='false'>Usuario</th>
                        <th field='expediente' width='80px' editable='false'>Expediente</th>
                        <th field='documento' width='80px' editable='false'>Documento</th>
                        <th field='proceso' width='80px' editable='false'>Proceso</th>
                     </tr>
                  </thead>
               </table>
            </td>
         </tr>
      </table>
   </body>
</html>
