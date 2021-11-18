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
      <style type="text/css">
         @import "js/dojo/dijit/themes/soria/soria.css";
         @import "css/estilo.css";
      </style>


<%
	Mensajeria msj=(Mensajeria)request.getAttribute("obMsj");
%>

<script type="text/javascript">

	function mostrarcargo() {
		dijit.byId("contentPaneDetail").setHref( "/siged/dofindkeycargouser.action?Idmen="+<%=msj.getIdmensajeria()%>); 
    	//xdr  
		parent.frames["secondFrame"].location.href = "/siged/dofindkeycargouser.action?Idmen="+<%=msj.getIdmensajeria()%>;
	}
	function mostrardatos() {
		dijit.byId("contentPaneDetail").setHref("/siged/dofinddatosUser.action?Idmen="+<%=msj.getIdmensajeria()%>); 
    	//xdr
		// parent.frames["secondFrame"].location.href = "/siged/dofinddatosUser.action?Idmen="+<%=msj.getIdmensajeria()%>;
	}
	
</script>
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
          <td height="75"> <table width="98%" height="97" align="center" >
                <tr> 
                  <td width="1%" ></td>
                  <td width="13%" height="5" ></td>
                  <td width="15%" ></td>
                  <td width="12%" ></td>
                  <td width="17%" ></td>
                  <td width="12%" ></td>
                  <td width="16%" ></td>
                  <td width="1%" ></td>
                </tr>
                <tr> 
                  <td></td>
                  <td height="16" align="left">N&uacute;mero Gu&iacute;a</td>
                  <td align="left" width="19%" class="label"> 
                  	<s:textfield name="obEnv.numeroguia" cssClass="cajaMontoTotal" size="20" disabled="true" />   
                  </td>
                  <td align="left" >Courier</td>
                  <td colspan="3" align="left" class="label">
<!--                  	<input name="codcourier" type="text" class="cajaMontoTotal" value="" size="15" onblur="javascript:tipoCourier()" disabled="disabled"> -->
                  	<input name="nomcourier" type="text" class="cajaMontoTotal" value="" size="48" disabled="disabled" > 
                  </td>
                  <td></td>
                </tr>
                <tr>  
                  <td></td>
                  <td height="16" align="left">Fecha Salida</td>
                  <td align="left" width="19%" class="label">
					<input name="fchsalidad" type="text" class="cajaMontoTotal" value="" size="20" disabled="disabled" > 
                  </td>
                  <td align="left" >Tipo de Env&iacute;o</td>
                  <td colspan="1" align="left" class="label">
                   	<input name="tipoenviox" type="text" class="cajaMontoTotal" value="<s:property value="obMsj.idtipoenvio.tipoenvio2"/>" size="15"  disabled>
                   	<input type="hidden" name="envio2" value="<s:property value="obMsj.idtipoenvio.tipoenvio2"/>" >
                  </td>
                  <td colspan="1">
					Ambito de Env&iacute;o</td> <td>
					<input name="ambitoenviox" type="text" class="cajaMontoTotal" value="<s:property value="obDatos.idambitoenvio.nomambitoenvio"/>" size="15"  disabled>
                    <input type="hidden" name="ambitoenvio" value="<s:property value="obDatos.idambitoenvio.nomambitoenvio"/>" >
                  </td>
                </tr>
                <tr> 
                  <td></td>
                  <td height="16" align="left">Dias Distribuci&oacute;n</td>
                  <td  align="left" class="label">
					<input name="xxx" type="text" class="cajaMontoTotal" value="" size="8" disabled="disabled" >   
                  </td>
				   
                  <td height="16" align="left">Dias Dev Cargo</td>
                  <td  align="left" class="label">
				    <input name="xxx2" type="text" class="cajaMontoTotal" value="" size="8" disabled="disabled" >   
                  </td>
				   
                  <td height="16" align="left">Dias Dev Devoluci&oacute;n</td>
                  <td  align="left" class="label"> 
                  	<input name="xxx3" type="text" class="cajaMontoTotal" value="" size="8" disabled="disabled" >   
                  </td>
                  <td></td>
                </tr>
					<tr>
						<td></td>
						<td height="16" align="left">Peso Dcmnto</td>
						<td align="left" class="label">
							<input name="pesodcmto" type="text" class="cajaMontoTotal" value="<s:property value="obEnv.pesodcmto" />" size="8" disabled="disabled"/></td>
						<td height="16" align="left">Unidad Peso</td>
						<td align="left" class="label">
							<input name="unidadPeso" type="text" class="cajaMontoTotal" value="<s:property value="obEnv.unidadpeso" />" size="15" disabled >
						</td>

						<td colspan="3"></td>
					</tr>                
                <tr> 
                  <td height="5" ></td>
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


</body>
</html>
