<%@page  import="java.text.*" %>
<%@page  import="java.util.*" %>
<%@page  import="com.btg.ositran.siged.domain.Mensajeria" %>
<%@page  import="org.ositran.utils.MensajeriaDatos" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://ajaxtags.org/tags/ajax" prefix="ajax"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<%@page import="org.ositran.utils.MensajeriaDatos"%><html>
<head>
<title>Tramite Documentario</title>
      <style type="text/css">
         @import "js/dojo/dijit/themes/soria/soria.css";
         @import "css/estilo.css";
      </style>


<%
	MensajeriaDatos msj=(MensajeriaDatos)request.getAttribute("Objmjda");
%>

<script type="text/javascript">

	function mostrarenvio() {
		 dijit.byId("contentPaneDetail").setHref("/siged/dofindkeyuser.action?Idmen="+<%=msj.getIdmen()%>); 
			
    	//parent.frames["secondFrame"].location.href = "/siged/dofindkeyuser.action?Idmen="+<%=msj.getIdmen()%>;
 	}
	function mostrarcargo() { 
		//xdr
		 dijit.byId("contentPaneDetail").setHref("/siged/dofindkeycargouser.action?Idmen="+<%=msj.getIdmen()%>); 

		// parent.frames["secondFrame"].location.href = "/siged/dofindkeycargouser.action?Idmen="+<%=msj.getIdmen()%>;
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


<table width="100%">
  <tr align="center"> 
    <td width="1%" align="center">&nbsp;</td>
    <td width="99%" colspan="2" align="left">
	 
     </td>
	</tr>
  <tr> 
    <td height="14" colspan="3"> <table width="95%"  border="1" align="center" bordercolor="#669BCD" bgcolor="#BFD9F1">
        <tr> 
          <td height="75"><table width="98%" height="97" align="center" >
                <tr> 
                  <td width="1%" ></td>
                  <td width="10%" height="5" ></td>
                  <td width="14%" ></td>
                  <td width="9%" ></td>
                  <td width="18%" ></td>
                  <td width="15%" ></td>
				   <td width="29%" ></td>
				    <td width="3%" ></td>
                </tr>
                <tr> 
                  <td></td>
                 <!-- <td height="16" align="left">N&uacute;mero Interno</td> -->
                  <!-- <td align="left" width="14%" class="label">  -->
                  <%-- <input name="numinterno" type="text" class="cajaMontoTotal" value="<s:property value="Objmjda.numinterno" />" size="20" disabled ></td> --%>
                  <td align="left" >Tipo Documento</td>
                  <td align="left" width="29%" class="label"> 
                  <input name="tipdoc" type="text" class="cajaMontoTotal" value="<s:property value="Objmjda.tipocumento" />" size="20" disabled> </td>
                  <td align="left" >N&uacute;mero</td>
                  <td align="left" width="18%" class="label">
                  <input name="numdoc" type="text" class="cajaMontoTotal" value="<s:property value="Objmjda.numerodoc" />" size="20" disabled > </td>				  
                  <td></td>
                </tr>
                <tr> 
                  <td></td>
                  <td height="16" align="left">Tipo Env&iacute;o</td>
                  <td align="left" class="label">
					 <input name="tipoEnvio2" type="text" class="cajaMontoTotal" value="<s:property value="Objmjda.tiev2" />" size="20" disabled>
				  </td>
				  <td>Ambito Env&iacute;o</td>
				  <td width="1%">
				  <input name="nombreAmbito" type="text" class="cajaMontoTotal" value="<s:property value="Objmjda.nomtipoambito" />" size="20" disabled>
				  </td>
				  </tr>
                <tr> 
                  <td></td>
                  <td height="16" align="left">Emp. Destino</td>
                  <td colspan="5" align="left" class="label"> 
					
<!--                  <input name="codigodes" type="text" class="cajaMontoTotal" value="<s:property value="Objmjda.empdescod" />" size="20" disabled> -->
				  <input name="empresa" type="text" class="cajaMontoTotal" value="<s:property value="Objmjda.empdesnom" />" size="50" disabled> 
                  </td>
                  <td></td>
                </tr>
                <tr> 
                  <td></td>
                  <td height="16" align="left">Destinatario</td>
                  <td colspan="5" align="left" class="label"> 
					
                  <input name="usudes" type="text" class="cajaMontoTotal" value="<s:property value="Objmjda.usudestino" />" size="88" disabled> 
                  </td>
                  <td></td>
                </tr>
               
                <tr> 
                  <td></td>
                  <td height="16" align="left">Direcci&oacute;n</td>
                  <td colspan="5" align="left" class="label">
					
                  <input name="dirdes" type="text" class="cajaMontoTotal" value="<s:property value="Objmjda.direc" />" size="88" disabled> 
                  </td>
                   <td></td>
                </tr>
				<tr> 
                  <td></td>
                  <td height="16" align="left">Ubigeo</td>
                  <td colspan="5" align="left" class="label">
				   <input id="departamento" name="departamento" Class="cajaMontoTotal" value="<s:property value="Objmjda.departamento" />" size="20" disabled />
                   &nbsp;&nbsp;
				   <input id="provincia" name="provincia" value="<s:property value="Objmjda.provincia" />" Class="cajaMontoTotal" size="20" disabled />
				   &nbsp;&nbsp;
				   <input id="distrito" name="distrito" Class="cajaMontoTotal" value="<s:property value="Objmjda.distrito" />" size="20" disabled />
				   </td>
                  <td></td>
                </tr>
                <tr> 
                  <td></td>
                  <td height="16" align="left">Referencia</td>
                  <td colspan="5" align="left" class="label">	
                  <input name="referencia" type="text" class="cajaMontoTotal" value="<s:property value="Objmjda.referencia" />" size="88" disabled> 
                  </td>
                   <td></td>
                </tr>
               <tr> 
                  <td height="5" > </td>
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
