<%@page  import="java.text.*" %>
<%@page  import="java.util.*" %>
<%@page  import="com.btg.ositran.siged.domain.Mensajeria" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%--<%@ taglib uri="http://ajaxtags.org/tags/ajax" prefix="ajax"%>--%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
   <head>
      <title>Tramite Documentario</title>

      <%
            Mensajeria msj = (Mensajeria) request.getAttribute("obMsj");
      %>

      <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
      <style type="text/css">

         body {
            background-color: #ffffff;
         }

         .barra {
            scrollbar-3dlight-color:#CCCCCC;
            scrollbar-arrow-color:#568BBF;
            scrollbar-base-color:#C3D5E9;
            scrollbar-darkshadow-color:#666666;
            scrollbar-face-color:;
            scrollbar-highlight-color:#669BCD;
            scrollbar-shadow-color:#999999;
         }
      </style>

   </head>

   <body  class="barra" topmargin="0" leftmargin="0" rigthmargin="0" >

      <input type="hidden" name="idmensajeria" value="<%=msj.getIdmensajeria()%>"/>
      <table width="100%">
         <tr>
            <td height="14" colspan="3"> </td>
         </tr>


         <tr align="center">
            <td width="1%" align="center">&nbsp;</td>
            <td width="99%" colspan="2" align="left">
            </td>
         </tr>
         <tr>
            <td height="14" colspan="3">
               <table width="95%"  border="1" align="center" bordercolor="#669BCD" bgcolor="#BFD9F1">
                  <tr>
                     <td height="75">
                        <table width="98%" height="219" align="center" >
                           <tr>
                              <td width="0%" ></td>
                              <td width="11%" height="5" ></td>
                              <td width="13%" ></td>
                              <td width="8%" ></td>
                              <td width="28%" ></td>
                              <td width="28%" ></td>
                              <td width="28%" ></td>
                              <td width="2%" ></td>
                           </tr>
                           <tr>
                              <td></td>
                              <td height="16" align="left">Costo Env&iacute;o</td>
                              <td align="left" width="13%" class="label">
                                 <input name="xxx2" type="text" class="cajaMontoTotal" value="" size="20" disabled >
                              </td>
                              <td align="left" colspan="2"></td>
                             <!-- <td align="left" >Grupo</td>
                              <td align="left" width="28%" class="label">
                                 <input name="grupo" type="radio" value="Mañana" checked disabled>&nbsp;Mañana&nbsp;
                                 <input name="grupo" type="radio" value="Tarde" checked disabled>&nbsp;Tarde
                             -->
                              </td>
                              <td></td>
                           </tr>
                           <tr>
                              <td></td>
                              <td height="16" align="left">Fecha Entrega</td>
                              <td align="left" width="13%" class="label">
                                 <input name="xxx3" type="text" class="cajaMontoTotal" value="" size="20" disabled >
                              </td>
                              <td align="left" ></td>
                              <td align="left" width="28%" class="label">
<!--                                 <input name="grupo" type="radio" value="Mañana" checked disabled>&nbsp;Mañana&nbsp;-->
<!--                                 <input name="grupo" type="radio" value="Tarde" checked disabled>&nbsp;Tarde-->
                              </td>
                              <td align="left" >Estado</td>
                              <td align="left" width="28%" class="label">
                                 <input name="xxx4" type="text" class="cajaMontoTotal" value="" size="20" disabled >
                              </td>
                              <td></td>
                           </tr>
                           <tr>
                              <td></td>
                              <td height="16" align="left">Recibido por</td>
                              <td colspan="3" align="left" width="13%" class="label">
                                 <input name="xxx5" type="text" class="cajaMontoTotal" value="" size="40" disabled >
                              </td>
                              <td align="left" >Fecha Cargo</td>
                              <td align="left" width="28%" class="label">
                                 <input name="xxx6" type="text" class="cajaMontoTotal" value="" size="20" disabled >
                              </td>
                              <td></td>
                           </tr>
                           <tr>
                              <td></td>
                              <td height="16" align="left">Fecha Dev de Cargo</td>
                              <td colspan="3" align="left" width="13%" class="label">
                                 <input name="xxx7" type="text" class="cajaMontoTotal" value="" size="20" disabled >
                              </td>
                              <td colspan="1" align="left" >Fecha Dev de Doc</td>
                              <td align="left" width="28%" class="label">
                                 <input name="xxx8" type="text" class="cajaMontoTotal" value="" size="20" disabled >
                              </td>
                              <td></td>
                           </tr>
                           <tr>
                              <td></td>
                              <td height="16" align="left">Primera Visita</td>
                              <td colspan="2" align="left" class="label">
                                 <input name="xxx9" type="text" class="cajaMontoTotal" value="" size="20" disabled >
                              <td colspan="3">Obs
                                 <input name="xxx10" type="text" class="cajaMontoTotal" value="" size="59" disabled >
                              </td>
                              <td></td>
                           </tr>
                           <tr>
                              <td></td>
                              <td height="16" align="left">Segunda Visita</td>
                              <td colspan="2" align="left" class="label">
                                 <input name="xxx11" type="text" class="cajaMontoTotal" value="" size="20" disabled >
                              <td colspan="3">Obs
                                 <input name="xxx12" type="text" class="cajaMontoTotal" value="" size="59" disabled >
                              </td>
                              <td></td>
                           </tr>
                           <tr>
                              <td></td>
                              <td height="16" align="left">Observaciones</td>
                              <td colspan="5" align="left" class="label">
                                 <input name="xxx13" type="text" class="cajaMontoTotal" value="" size="90" disabled >
                              </td>
                              <td></td>
                           </tr>
                           <tr>
                              <td height="5" ></td>
                           </tr>
                        </table></td>
                  </tr>
               </table></td>
         </tr>
         <tr>
            <td height="14"  colspan="3"></td>
         </tr>
      </table>
   </body>
</html>
