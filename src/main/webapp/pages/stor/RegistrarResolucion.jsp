<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Tramite Documentario</title>

<style type="text/css">
   @import "js/dojo/dijit/themes/soria/soria.css";
   @import "css/estilo.css";
</style>

<script type="text/javascript" src="js/dojo/dojo/dojo.js" djConfig="parseOnLoad:true, isDebug: false"></script>


<!-- Begin Scrips STOR-->
<script type="text/javascript">
   dojo.require("dojo.parser");   
   dojo.require("dijit.form.DateTextBox");
   dojo.require("dijit.form.ValidationTextBox");
   dojo.require("dijit.form.Form");
   dojo.require("dijit.form.FilteringSelect");
   dojo.require("dojo.data.ItemFileReadStore");
   dojo.require("dijit.form.Button");
   dojo.require("dijit.Dialog");
   dojo.require("dijit.ProgressBar");

   var storeResultado = new dojo.data.ItemFileReadStore({url: "obtenerTipoResultadoResolucionJaru.action"});

</script>


<script language="JavaScript">

   function registrarResolucion(){
      var form = dijit.byId("formRegistrarResolucion");
      if(form.validate()){
         //alert("Form valido");
         var aceptar = document.getElementById('botonAceptar');
         aceptar.disabled=true;
         dijit.byId("dlgProgresBar").show();
         form.submit();
         //document.forms[0].action = '/siged/doStor_registrarResolucion.action' ;
         //document.forms[0].submit();      
      }else{
         //alert("Form Invalido");
      }      
   }

   function refrescar(){
      window.opener.parent.location.href ='/siged/inicio.action?sTipoFrame=grid';
      window.opener.parent.frames["mainFrame"].location.href = "/siged/doBody.action";
      window.close();
   }

</script>
<!-- End Scrips STOR-->


<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

</head>

<body class="soria" topmargin="0" leftmargin="0" rigthmargin="0"
   <s:if test="refrescar!=null">
      onload="refrescar()"
   </s:if>
> 
<div dojoType="dijit.form.Form" id="formRegistrarResolucion" action="doStor_registrarResolucion.action" method="POST">
    <s:hidden name="idDocumento" />
    
  <table width="103%">
    <tr> 
      <td height="14" colspan="2"> </td>
    </tr>
    <tr> 
      <td height="13" colspan="2" class="header" ><div align="center">El expediente 
          será aprobado</div></td>
    </tr>

    <tr> 
      <td height="13" colspan="2" class="header" ></td>
    </tr>


    <tr> 
      <td height="14" colspan="2">
	  <table width="78%" height="63"  border="1"  style="border-style:solid" align="center" bordercolor="#669BCD" bgcolor="#BFD9F1">
	     <tr class="altRow2" style="">
           <td width="11%" height="9"><center></center></td>
         </tr>
		 <tr class="altRow2" style="">
         	<td width="11%" height="68">
		   		<table align="center" border="0" width="100%">
				  
				   <tr>
					   <!--<td width="253">Fecha de Aprobaci&oacute;n</td>
					   <td width="347">-->
                  <td width="10%"></td>
                  <td width="40%">Fecha de Aprobaci&oacute;n</td>
					   <td width="40%">
                          <!--<input class="cajaMontoTotal" name="date" type="text" id="campo_fecha3" size="14" maxlength="20">-->
                          <!--<s:textfield id = "fechaaprobacion" name="fechaaprobacion"  cssClass="cajaMontoTotal" size="14" />-->
                          <input type="text"  
                                 dojoType="dijit.form.DateTextBox"
                                 id="fechaaprobacion"
                                 name="fechaaprobacion"
                                 required="true"                                 
                                 promptMessage="dd/MM/aaaa"
                                 invalidMessage="Ingresar una Fecha Valida"
                          />
                          <!--<input alt="Calendario" class="cajaFecha" id="selectfecha" value="..." type="button">-->
                  </td>
                  <td width="10%"></td>
		   		  </tr>
				  
				    <tr>
                  <td width="10%"></td>
					   <td width="40%">Numero de Resoluci&oacute;n</td>
					   <td width="40%">
                          <!--<input name="para" type="text" class="cajaMontoTotal" value="" size="15">-->
                          <!--<s:textfield id="nroresolucion" name="nroresolucion" cssClass="cajaMontoTotal" value="" size="14" />-->
                          <input type="text"
                                 dojoType="dijit.form.ValidationTextBox"
                                 name="nroresolucion"
                                 id="nroresolucion"
                                 required="true"
                                 regExp=".{1,50}"
                                 invalidMessage="Debe Ingresar un Nro de Resolución Válido"
                                 class="cajaMontoTotal"
                          >
                  </td>
                  <td width="10%"></td>
		   		  </tr>				                      
                  <tr>
                  <td width="10%"></td>
					   <td width="40%">Resultado </td>
					   <td width="40%">
                          <input type="text"
                                 dojoType="dijit.form.FilteringSelect"
                                 store="storeResultado"
                                 idAttr="id"
                                 labelAttr="label"
                                 searchAttr="label"
                                 name="idresultadoresolucion"
                                 id="idresultadoresolucion"
                                 required="true"
                                 invalidMessage="Debe Seleccionar un Resultado Valido"
                                 class="cajaMontoTotal"                                 
                          >
                   </td>
                   <td width="10%"></td>
		   		   </tr>
   		   		  
		   		</table>
		 	</td>
        
       </table>
	  </td>
    </tr>
	
	<tr> 
      <td colspan="2">	  </td>
    </tr>
    <tr> 
      <td height="14"  colspan="2"></td>
    </tr>
<tr> 
      <td colspan="2" align="center">
         <input id="botonAceptar" type="button" name="button" value="Aceptar" class="button" onclick="registrarResolucion()"  />        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
         <input type="button" name="button2" value="Cancelar" class="button" onClick="window.close()" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      </td>
</tr>


    <tr align="center"> 
      <td colspan="2"  align="center">&nbsp;</td>
    </tr>
</table>
</div>
<%@ include file="../util/progressBar.jsp" %>
</body>
</html>
