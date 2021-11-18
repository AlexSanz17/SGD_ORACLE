<?xml version="1.0" encoding="UTF-8" ?>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es">
   <head>
      <title>Edici&oacute;n del Proceso</title>
      <meta http-equiv="Pragma" content="no-cache" />
      <link rel="stylesheet" type="text/css" href="css/estilo.css" />
      <script type="text/javascript" src="js/dojo/dojo/dojo.js" djConfig="parseOnLoad:true, isDebug: false"></script>
      <script type="text/javascript">
         var idGrupoProceso = "<s:property value='objGrupoProceso.idgrupoproceso' />";

         function eliminarGrupoProceso(){
            if (confirm("Seguro que desea eliminar el grupo de proceso?")) {
               dojo.xhrPost({
                  url: "doEliminarGrupoProceso.action",
                  content: {
                     iIdGrupoProceso: idGrupoProceso
                  },
                  mimetype: "text/html",
                  load: function() {
                     self.parent.location.href = "inicio.action?sTipoFrame=grid&sTipoMantenimiento=MantMnuGPr";
                  }
               });
            }
         }
      </script>
   </head>
   <body>
      <s:form id="frmGrupoProceso" name="frmGrupoProceso" action="doSaveGrupoProceso" method="post">
         <table width="100%">
            <tr>
               <td></td>
               <td height="16" colspan="4" align="left" class="plomo"></td>
            </tr>
            <tr align="center">
               <td width="1%" align="center">&nbsp;</td>
               <td width="99%" align="left">
                  <s:submit src="images/guardar.bmp" type="image" value="Guardar Grupo de Proceso" />
               </td>
               <td>
                  <s:if test="objGrupoProceso.idgrupoproceso != null">
                     <a href="#" onclick="eliminarGrupoProceso()">Eliminar</a>
                  </s:if>
               </td>
            </tr>
            <tr>
               <td></td>
               <td height="16" colspan="4" align="left" class="plomo"></td>
            </tr>
            <tr>
               <td height="14" colspan="3">
                  <table width="95%" border="1" align="center" bordercolor="#669BCD" bgcolor="#BFD9F1">
                     <tr>
                        <td height="75">
                           <table width="98%" align="center" >
                              <tr>
                                 <td></td>
                                 <td colspan="5" height="16" align="left" class="label">
                                    <s:textfield name="objGrupoProceso.idgrupoproceso" cssStyle="display:none" />
                                 </td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Nombre</td>
                                 <td colspan="3" align="left" class="label">
                                    <s:textfield name="objGrupoProceso.nombre" cssClass="cajaMontoTotal" size="25" maxlength="50" />
                                 </td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">C&oacute;digo</td>
                                 <td colspan="3" align="left" class="label">
                                    <s:textfield name="objGrupoProceso.codigo" cssClass="cajaMontoTotal" size="25" maxlength="50" />
                                 </td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Descripci&oacute;n</td>
                                 <td colspan="3" align="left" class="label">
                                    <s:textarea name="objGrupoProceso.descripcion" cssClass="cajaMontoTotal" rows="10" cols="70"/>
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
               </td>
            </tr>
            <tr>
               <td height="14"  colspan="3"></td>
            </tr>
         </table>
      </s:form>
   </body>
</html>
