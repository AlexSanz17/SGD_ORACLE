<%@page  import="java.text.*" %>
<%@page  import="java.util.*" %>
<%@page  import="com.btg.ositran.siged.domain.Mensajeria" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://ajaxtags.org/tags/ajax" prefix="ajax"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<%@page import="org.ositran.utils.MensajeriaDatos"%><html>
<head>
<title>Tramite Documentario</title>
<link rel="stylesheet" type="text/css" href="./css/estilo.css">
<script language="javascript" src="./js/form.js"> </script>
<script language="Javascript" src="./js/general.js"></script>

<!--<script type="text/javascript" src="./js/prototype-1.4.0.js"></script>
-->
<script type='text/javascript' src='./dwr/engine.js'> </script>
<script type='text/javascript' src='./dwr/util.js'> </script>
<script type='text/javascript' src='./dwr/interface/toolDwr.js'></script>
<script type="text/javascript" src="./js/ajaxtags-1.2-beta2.js"></script>
<link rel="stylesheet" type="text/css" href="./css/ajaxtags.css" />
<link rel="stylesheet" type="text/css" href="./css/displaytag.css" />

<script type='text/javascript' src='./dwr/interface/Tipoenvio.js'></script>
<script type='text/javascript' src='./dwr/interface/Empresadestino.js'></script> 


<%
 	MensajeriaDatos msj = (MensajeriaDatos) request
 			.getAttribute("objmjda");
 %>

<style type="text/css">
     @import "js/dojo/dijit/themes/soria/soria.css";
     @import "css/estilo.css";
</style>

<script type="text/javascript" src="js/dojo/dojo/dojo.js" djConfig="parseOnLoad:true, isDebug: false"></script>
<script type="text/javascript" src="js/dojo/dijit/dijit.js" djConfig="parseOnLoad:true, isDebug: false"></script>

<s:url id="autoUrl" action="autocompletartipoenvio" />
<s:url id="autoUrl2" action="autocompletarempresadestino" />

<script type="text/javascript">
      	dojo.require("dijit.form.FilteringSelect" );
      	dojo.require("dojo.data.ItemFileReadStore");
         dojo.require("dojo.parser");

         var store = new dojo.data.ItemFileReadStore({url: "<s:property value='autoUrl' />"});
         var store2=new dojo.data.ItemFileReadStore({url: "<s:property value='autoUrl2' />"});
         
</script>

<script type="text/javascript">



	function tipoEnvio(codigo){
	
		if(codigo != ""){
			Tipoenvio.viewcodMensajeria(codigo,tipoEnviocallBack);
		}
	}

	function tipoEnviocallBack(data){
		if(data!=null){
		DWRUtil.setValue("tipoEnvio1",data.tipoenvio1);
		DWRUtil.setValue("tipoEnvio2",data.tipoenvio2);}
		
				
	}
	function empresaDestino(codigodes){
		
		if(codigodes != ""){
			Empresadestino.Viewcod(codigodes,empresaDestinocallBack);
		}
	}

	function empresaDestinocallBack(data){
		if(data!=null){
		DWRUtil.setValue("empresa",data.nombre);
		}else{
			DWRUtil.setValue("empresa","");
			}
		
	}

	function guardar(){
		
		document.forms[0].action = 'doingdatos.action' ;
		document.forms[0].submit();
	 	alert("paso");
		

	}
	function checkKey(){
		if(event.keyCode==13){
			alert("hola");
			}
	}
	
	
</script>
<script type="text/javascript">

	function killEnter(evt)
	{
		if(evt.keyCode == 13 || evt.which == 13)
		{
			return false;
		}	
			return true;
	}
	function validar(e) {
		  tecla = (document.all) ? e.keyCode : e.which;
		  if (tecla==13) {
			  var num1=dijit.byId('codigo');
			  var num=num1+"0";
			  var num=num.charAt(0)+num.charAt(1)+num.charAt(2); 
			  //alert(num);
			  this.tipoEnvio(num);
			  }
		  
		  
	}
	function validar2(e) {
		  tecla = (document.all) ? e.keyCode : e.which;
		  if (tecla==13) {
			  var num1=dijit.byId('codigodes');
			  var num=num1+"0";
			  var num=num.charAt(0)+num.charAt(1)+num.charAt(2);
			  //alert(num);
			  this.empresaDestino(num);
			  }
		  
		  
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

<body  topmargin="0" leftmargin="0" rigthmargin="0" class="soria" > 
<s:form  action="doingdatos" name="mifor2" method="post">
<input type="hidden" name="Idmen" value="<%=msj.getIdmen()%>"/>
<input type="hidden" name="idmensajeria" value="<%=msj.getIdmen()%>"/>
<input type="hidden" name="fecha" value="<%=msj.getFechaderiva()%>"/>
<input type="hidden" name="asunt" value="<%=msj.getAsunto()%>"/>
<input type="hidden" name="tpdoc" value="<%=msj.getTipocumento()%>"/>
<input type="hidden" name="nmdoc" value="<%=msj.getNumerodoc()%>"/>


<table width="100%">
  <tr> 
    <td height="14" colspan="3"> </td>
  </tr>  
  <tr align="center"> 
    <td width="1%" align="center">&nbsp;</td>
    <td width="99%" colspan="2" align="left">
		<s:if test="#session._RECURSO.UsuEnviarMsg">
	 		<s:submit name="sumit" cssClass="button" value="Guardar"/>
		</s:if>
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
                  <td height="16" align="left">N&uacute;mero Interno</td>
                  <td >
                  
                  <input name="numinternox" type="text" class="cajaMontoTotal" value="<%=msj.getNuminterno()%>" size="20" disabled>
                  <input type="hidden" name="numinterno" value="<%=msj.getNuminterno()%>">
                  </td>
				  <td align="left" >Tpo Doc</td>
                  <td align="left" width="29%" class="label">
                  <input name="tipdoc" type="text" class="cajaMontoTotal" value="<%=msj.getTipocumento()%>" size="20" disabled> </td>                  
                  <td align="left" >N&uacute;mero
                  <input name="numdoc" type="text" class="cajaMontoTotal" value="<%=msj.getNumerodoc()%>" size="20" disabled ></td>				  
                  <td></td>
                </tr>
				<tr>
					<td></td>
					<td height="16" align="left">Tipo Env&iacute;o</td>
					<td align="left" class="label">
						<input name="tipoEnvio2" type="text" class="cajaMontoTotal" value="<%=msj.getTiev2()%>"	size="20" disabled></td>
					<td height="16" align="left">Ambito Env&iacute;o</td>
					<td><input name="nombreAmbito" type="text"	class="cajaMontoTotal" size="20" value="<%=msj.getNomtipoambito()%>" disabled></td>
					<td width="1%"></td>
				</tr>
				<tr> 
                  <td></td>
                  <td height="16" align="left">Emp. Destino</td>
<!--                  <td> -->
<!--                  <div dojoType="dijit.form.FilteringSelect"-->
<!--                       store="store2"-->
<!--                       idAttr="id"-->
<!--                       labelAttr="label"-->
<!--                       searchAttr="label"-->
<!--                       name="codigodes"-->
<!--                       id="codigodes"-->
<!--                       size="20"-->
<!--                       onkeypress="validar2(event)"-->
<!--                  ></div>  -->
<!--                  </td>-->
                  <td colspan="3"><input name="empresa" type="text" class="cajaMontoTotal" value="" size="50" disabled></td>
                </tr>
                <tr> 
                  <td></td>
                  <td height="16" align="left">Concesionario</td>
                  <td colspan="5" align="left" class="label"> 
                  <input name="usudesx" type="text" class="cajaMontoTotal" value="<%=msj.getDestinatario()%> " size="88" disabled>
                  <input type="hidden" name="usudes" value="<%=msj.getDestinatario()%>" > 
                  </td>
                  <td></td>
                </tr>
               
                <tr> 
                  <td></td>
                  <td height="16" align="left">Direcci&oacute;n</td>
                  <td colspan="5" align="left" class="label">
                  <input name="dirdesx" type="text" class="cajaMontoTotal" value="<%=msj.getDirec()%>" size="88" disabled>
                  <input type="hidden" name="dirdes" value="<%=msj.getDirec()%>"> 
                  </td>
                   <td></td>
                </tr>
                <tr> 
                  <td></td>
                  <td height="16" align="left">Referencia</td>
                  <td colspan="5" align="left" class="label">
                  <input name="referencia" type="text" class="cajaMontoTotal" value="" size="88"> 
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
</s:form>

</body>
</html>
