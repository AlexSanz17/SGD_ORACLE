<?xml version="1.0" encoding="UTF-8" ?>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
   <head>
      <s:head/>
      <title>Tramite Documentario</title>
      <link rel="stylesheet" type="text/css" href="css/estilo.css" />
	<script type="text/javascript">
	 function validarForm(){
             var idNombre=document.getElementById("doSaveUnidad_objUnidad_nombre");
             if(idNombre.value==""){
            	 alert("Debe ingresar el nombre del Area.");
            	 idNombre.focus();
            	 return false;    
             }
             return true;
             }
    </script>
      
   </head>

   <body class="barra">
      <s:form name="frmUnidad" action="doSaveUnidad" method="POST">
         <table width="100%">
            <tr>
               <td></td>
               <td height="16" colspan="4" align="left" class="plomo"></td>
            </tr>
            <tr align="center">
               <td width="1%" align="center">&nbsp;</td>
               <td width="99%" colspan="2" align="left">
                  <s:submit src="images/guardar.bmp" type="image" onclick="return validarForm()" value="Guardar Unidad" />
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
                                    <s:textfield name="objUnidad.idunidad" cssStyle="display:none" />
                                 </td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Nombre</td>
                                 <td colspan="3" align="left" class="label">
                                    <s:textfield name="objUnidad.nombre" id="doSaveUnidad_objUnidad_nombre" cssClass="cajaMontoTotal" size="25" />
                                 </td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Dependencia</td>
                                 <td colspan="3" class="label">
                                    <s:select cssClass="cajaMontoTotal" name="objUnidad.dependencia.idunidad" headerKey="0" headerValue="-- Seleccione una Dependencia --" list="mapDependencia" value="objUnidad.dependencia.idunidad" />
                                 </td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Sede</td>
                                 <td colspan="3" class="label">
                                    <s:select cssClass="cajaMontoTotal" name="objUnidad.sede.idsede" headerKey="0" headerValue="-- Seleccione una Sede --" list="mapSede" value="objUnidad.sede.idsede" />
                                 </td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Hora Inicio Envio</td>
                                 <td colspan="3" align="left" class="label">
                                    <s:textfield name="objUnidad.inicioEnvio" id="doSaveUnidad_objUnidad_inicioEnvio" cssClass="cajaMontoTotal" size="25" />
                                 </td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Hora Fin Envio</td>
                                 <td colspan="3" align="left" class="label">
                                    <s:textfield name="objUnidad.finEnvio" id="doSaveUnidad_objUnidad_finEnvio" cssClass="cajaMontoTotal" size="25" />
                                 </td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Descripci&oacute;n</td>
                                 <td colspan="3" align="left" class="label">
                                    <s:textarea cssClass="cajaMontoTotal" name="objUnidad.descripcion" rows="10" cols="70" />
                                 </td>
                                 <td align="left"></td>
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
               <td height="14" colspan="3"></td>
            </tr>
         </table>
      </s:form>
   </body>
</html>
