<%@page  import="java.text.*" %>
<%@page  import="java.util.*" %>
<%@page  import="com.btg.ositran.siged.domain.Envio" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://ajaxtags.org/tags/ajax" prefix="ajax"%>
<html>
<head>
<title>Tramite Documentario</title>
<link rel="stylesheet" type="text/css" href="./css/estilo.css">
<%
	Envio Objenvio=(Envio)request.getAttribute("obEnv");
%>

<script language="JavaScript">
var storeUnidadPeso = new dojo.data.ItemFileReadStore({url: "autocompletarUnidadPeso.action"});
function Abrir_ventanaBuscar (pagina) {
var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=710, height=530, top=50, left=200";
window.open(pagina,"",opciones);
}

function Abrir_ventanaCargo (pagina) {
var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=760, height=380, top=50, left=200";
window.open(pagina,"",opciones);
}

function Abrir_pagina (pagina) {
var opciones="location=mainFrame";
window.open(pagina,"",opciones);
}

function putTextOnTextBox(textToPut){
document.all.reciveTheText.value = textToPut;
}
function regresar(){ 
   history.back();
}
function ejecAprob(){
if (confirm("Desea enviar el documento DOC 001")){
location.href="../../enviar2.html"
}
}
	
</script>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<style type="text/css">
<!--
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
	
	   
-->

</style>

</head>

<body  class="barra" topmargin="0" leftmargin="0" rigthmargin="0" > 
<input type="hidden" name="idmensajeria" value=""/> 
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
    <td height="14" colspan="3"> <table width="95%"  border="1" align="center" bordercolor="#669BCD" bgcolor="#BFD9F1">
        <tr> 
          <td height="75"> <table width="98%" height="97" align="center" >
                <tr> 
                  <td width="1%" ></td>
                  <td width="13%" height="5" ></td>
                  <td width="15%" ></td>
                  <td width="15%" ></td>
                  <td width="14%" ></td>
                  <td width="14%" ></td>
                  <td width="14%" ></td>
                  <td width="1%" ></td>
                </tr>
                <tr> 
                  <td></td>
                  <td height="16" align="left">N&uacute;mero Gu&iacute;a</td>
                  <td align="left" width="19%" class="label"> 
					
                  <input name="numguia" type="text" class="cajaMontoTotal" value="<s:property value="obEnv.numeroguia" />" size="20" disabled > 
                  </td>
                  <td align="left" >Courier</td>
                  <td colspan="4" align="left" class="label">
					
<!--                  <input name="codcourier" type="text" class="cajaMontoTotal" value="<s:property value="obEnv.numerocourier" />" size="15" disabled > -->
                  <input name="nomcourier" type="text" class="cajaMontoTotal" value="<s:property value="obEnv.nombrecourier" />" size="49" disabled > 
                  </td>
                </tr>
                <tr> 
                  <td></td>
                  <td height="16" align="left">Fecha Salida</td>
                  <td align="left" width="19%" class="label"> 
                  <input name="fchsalida" type="text" class="cajaMontoTotal" value="<s:date name="obEnv.fechasalida"  format="dd/MM/yyyy"/>" size="20" disabled > 
                  
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
                  <td height="16" align="left">Fecha de Entrega</td>
                  <td  align="left" class="label"> 
					
                      <input name="diadistribucion" type="text" class="cajaMontoTotal" value="<s:date name="obEnv.diasdistribuicion" format="dd/MM/yyyy" />" size="20" disabled>
                  </td>
				   
                  <td height="16" align="left">Fecha de Devoluci&oacute;n de Cargo</td>
                  <td  align="left" class="label"> 
					
                  <input name="diasdevcargo" type="text" class="cajaMontoTotal" value="<s:date name="obEnv.diasdevcargo"  format="dd/MM/yyyy" />" size="8" disabled >
                   </td>
				   
                  <td height="16" align="left">Fecha de Devolucion - &Aacute;rea</td>
                  <td  align="left" class="label"> 
					
                  <input name="diasdevdevolucion" type="text" class="cajaMontoTotal" value="<s:date name="obEnv.diasdevolucion"   format="dd/MM/yyyy" />" size="8" disabled >
                   </td>
                  <td></td>
                </tr>
                <tr> 
                  <td></td>
                  <td height="16" align="left">Peso Documento</td>
                  <td  align="left" class="label"> 					
                  		<input name="pesodcmto" type="text" Class="cajaMontoTotal" value="<s:property value="obEnv.pesodcmto" />"  size="8" disabled/> 
                  </td>
				   
                  <td height="16" align="left">Unidad Peso</td>
                  <td  align="left" class="label"> 
					  <input name="unidadPeso" type="text" class="cajaMontoTotal" value="<s:property value="obEnv.unidadpeso" />" size="15" disabled >        
                  </td>
				   
                  <td height="16" align="left"></td>
                  <td></td>
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
