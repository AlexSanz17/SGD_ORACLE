<%@page  import="java.text.*" %>
<%@page  import="java.util.*" %>
<%@page  import="com.btg.ositran.siged.domain.Cargo" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://ajaxtags.org/tags/ajax" prefix="ajax"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<title>Tramite Documentario</title>
<%
	Cargo Objcargo=(Cargo)request.getAttribute("ObjCg");
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
                  <input name="costenv" type="text" class="cajaMontoTotal" value="<s:property value="ObjCg.costoenvio" />" size="15" disabled > 
                  </td>
                  <td align="left" colspan="2"></td>
                  <!--<td align="left" >Grupo</td> -->
                  <!--<td align="left" width="28%" class="label"> -->
                      <!--<s:property value="ObjCg.grupo "/>-->
                  </td>
                  <td></td>
                </tr>
                <tr> 
                  <td></td>
                  <td height="16" align="left">Fecha Entrega</td>
                  <td align="left" width="13%" class="label"> 
				  <input name="fchentrega" type="text" class="cajaMontoTotal" value="<s:date name="ObjCg.fechaentrega" format="dd/MM/yyyy"/>" size="15" disabled> 
                  </td>
                  <td align="left" ></td>
                  <td align="left" width="28%" class="label">
<!--					<s:property value="ObjCg.grupo "/>-->
                  </td>
                  <td align="left" >Estado</td>
                  <td align="left" width="28%" class="label">
					
                  <input type="text" name="estado" maxlength="15" class="cajaMontoTotal" value="<s:property value="ObjCg.estado" />" disabled/>
                  </td>
                  <td></td>
                </tr>
                <tr> 
                  <td></td>
                  <td height="16" align="left">Recibido por</td>
                  <td colspan="3" align="left" width="13%" class="label">
                  <input name="recibidox" type="text" class="cajaMontoTotal" value="<s:property value="ObjCg.recibido" />" size="40" disabled > 
                  </td>

                  <td align="left" >Fecha Cargo</td>
                  <td align="left" width="28%" class="label">
				  <input name="fechacargo" type="text" class="cajaMontoTotal" value="<s:date name="ObjCg.fechacargo" format="dd/MM/yyyy"/>" size="15" disabled> 
                  </td>
                  <td></td>
                </tr>
                <tr> 
                  <td></td>
                  <td height="16" align="left">Fecha Dev de Cargo</td>
                  <td colspan="3" align="left" width="13%" class="label"> 					
                  <input name="fechadevcargo" type="text" class="cajaMontoTotal" value="<s:date name="ObjCg.fechadevcargo" format="dd/MM/yyyy"/>" size="40" disabled > 
                  </td>
                  <td align="left" >Fecha Dev de Doc</td>
                  <td align="left" width="28%" class="label">
                  <input name="fechadevdoc" type="text" class="cajaMontoTotal" value="<s:date name="ObjCg.fechadevdoc" format="dd/MM/yyyy"/>" size="20" disabled > 
                  </td>
                  <td></td>
                </tr>
                <tr> 
                  <td></td>
                  <td height="16" align="left">Primera Visita</td>
                  <td align="left" class="label"> 
                  	<input name="primeravisita" type="text" class="cajaMontoTotal" value="<s:date name="ObjCg.primeravisita" format="dd/MM/yyyy"/>" size="15" disabled>
                  </td>
                  <td align="left">Obs</td>
                  <td colspan="3" align="left" width="28%" class="label">
                  <input name="obs1" type="text" class="cajaMontoTotal" value="<s:property value="ObjCg.obs1"/>" size="61" disabled> 
                  </td>
                  <td></td>
                </tr>
                <tr> 
                  <td></td>
                  <td height="16" align="left">Segunda Visita</td>
                  <td align="left" class="label">
				  	 
                  <input name="segundavisita" type="text" class="cajaMontoTotal" value="<s:date name="ObjCg.segundavisita" format="dd/MM/yyyy" />" size="15" disabled></td>
                  <td align="left">Obs</td>
                  <td colspan="3" align="left" width="28%" class="label">
                  <input name="obs2" type="text" class="cajaMontoTotal" value="<s:property value="ObjCg.obs2" />" size="61" disabled> 
                  </td>
                  <td></td>
                </tr>
                <tr> 
                  <td></td>
                  <td height="16" align="left">Observaciones</td>
                  <td colspan="5" align="left" class="label"> 
                  <input name="observaciones" type="text" class="cajaMontoTotal" value="<s:property value="ObjCg.observaciones" />" size="88" disabled >
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
