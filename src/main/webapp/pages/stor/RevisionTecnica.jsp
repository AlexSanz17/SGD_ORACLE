<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<html>
<head>    
<title>Tramite Documentario</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
      <style type="text/css">
         @import "js/dojo/dijit/themes/soria/soria.css";
         @import "js/dojo/dojox/grid/resources/soriaGrid.css";
         @import "css/grid.css";
      </style>

      <script type="text/javascript" src="js/dojo/dojo/dojo.js" djConfig="parseOnLoad:true, isDebug: false"></script>
      <script type="text/javascript" src="js/dojo/dijit/dijit.js" djConfig="parseOnLoad:true, isDebug: false"></script>


      <s:url id="autoUrl2" action="obtenerRevisorLegalStor" />


      <script type="text/javascript">
          dojo.require("dijit.form.FilteringSelect" );
          dojo.require("dojo.data.ItemFileReadStore");
          dojo.require("dojo.rpc.JsonService");
          dojo.require("dijit.Dialog");
          dojo.require("dijit.ProgressBar");

         var store2 = new dojo.data.ItemFileReadStore({url: "<s:property value='autoUrl2' />?idDependencia1="+<s:property value="idsala" />});
      </script>
<link href="css/estilo.css" rel="stylesheet" type="text/css">
<script language="javascript" src="js/form.js"> </script>
<script language="Javascript" src="js/general.js"></script>
<link rel="stylesheet" type="text/css" media="all" href="css/calendar-green.css">
<script type="text/javascript" src="js/calendar.js"></script>
<script type="text/javascript" src="js/calendar-es.js"></script>
<script type="text/javascript" src="js/calendar-setup.js"></script>

<script type='text/javascript' src='./dwr/engine.js'> </script>
<script type='text/javascript' src='./dwr/util.js'> </script>
<script type='text/javascript' src='./dwr/interface/toolDwr.js'> </script>

<!-- Begin Scrips STOR-->
<script language="JavaScript">

    function aprobar(){       
       estado = document.getElementById('estadoexpedientestor');
       proceso = document.getElementById('proceso');
       if(proceso.value=='apelacion' && estado.value=='rleg'){
          var aceptar = document.getElementById('botonAceptar');
          aceptar.disabled=true;
          document.forms[0].action = '/siged/doStor_analisisExpediente.action';
          dijit.byId("dlgProgresBar").show();
          document.forms[0].submit();
       }else{
          if(validarCamposObligatorios()){
             var aceptar = document.getElementById('botonAceptar');
             aceptar.disabled=true;
             document.forms[0].action = '/siged/doStor_revisionTecnica.action';
             dijit.byId("dlgProgresBar").show();
             document.forms[0].submit();
          }else{
             //alert("Formulario Invalido - No pasar al Action");
          }
       }      
    }
    
    function validarCamposObligatorios(){
       var revisorLegal = dijit.byId("idrevisorlegal");
       var validarRevLeg;

       <s:if test="estadoexpedientestor.equalsIgnoreCase('rleg')">
             validarRevLeg = true;
       </s:if>
       <s:else>
          if(revisorLegal.isValid() && revisorLegal.getValue().length != 0){
             validarRevLeg = true;
          }else{
             revisorLegal.setDisplayedValue("Campo Obligatorio");
             revisorLegal.focus();
          }
       </s:else>
      return validarRevLeg;
    }

    function clearCamposObligatorios(idCampo){
       var campoDojo = dijit.byId(idCampo);
       campoDojo.setValue("");
       campoDojo.setDisplayedValue("");
    }
    
    function refrescar() {
       window.opener.parent.location.href ='/siged/inicio.action?sTipoFrame=grid';
       window.opener.parent.frames["mainFrame"].location.href = "/siged/doBody.action";
       window.close();
    }

</script>
<!-- End Scrips STOR-->

<script language="JavaScript">
function Abrir_ventana (pagina) {
var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=800, height=700, top=50, left=200";
window.open(pagina,"",opciones);
}


</script>

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"><style type="text/css">
<!--
body {
	background-color: #DDEDFF;
}
-->
</style></head>

<body topmargin="0" leftmargin="0" rigthmargin="0" class="soria"
   <s:if test="refrescar!=null">
      onload="refrescar()"
   </s:if>
> 
<form>
    <s:hidden name="idDocumento" />
    <!--Variables Auxiliares-->
    <s:hidden id="estadoexpedientestor" name="estadoexpedientestor" />
    <!--Variables Auxiliares-->
    <!--Variables Apelaciones-->
    <s:hidden name="idrevisortecnico" />
    <s:hidden id="proceso" name="proceso" />
    <!--Variables Apelaciones-->
    <!--Variables Quejas y MC-->
    <!--<s:hidden name="revisiontecnica" />-->
    <!--<s:hidden name="revisionlegal" />-->
    <!--<s:hidden name="idrevisorlegal" />-->
    <!--Variables Quejas y MC-->
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
	  <table width="58%" height="73"  border="1"  style="border-style:solid" align="center" bordercolor="#669BCD" bgcolor="#BFD9F1">
	     <tr class="altRow2" style="">
           <td width="11%" height="9"><center></center></td>
         </tr>
		 <tr class="altRow2" style="">
         	<td width="11%" height="68">
		   		<table align="center" border="0" width="100%">
				   <tr>
					   
                  <td width="136" align="right">
                     <s:label> Responsable Legal </s:label>
                  </td>
					   <td width="303" >
                           <s:hidden name="idsala" />                           
                           <s:if test="estadoexpedientestor.equalsIgnoreCase('rleg')">
                              <s:hidden name="idrevisorlegal"/>
                              &nbsp;&nbsp;&nbsp;<s:textfield name="revisorlegal" cssClass="cajaMontoTotal" size="15" readonly="true" disabled="true"/>
                           </s:if>
                           <s:else>
                              <div dojoType="dijit.form.FilteringSelect"
                                 store="store2"
                                 idAttr="id"
                                 labelAttr="label"
                                 searchAttr="label"
                                 name="idrevisorlegal"
                                 id="idrevisorlegal"
                                 size="80"
                                 required="true"
                                 invalidMessage="Seleccione un Revisor Legal Válido"></div>
                           </s:else>
                           
                       </td>
		   		   </tr>
                  <tr><td colspan="2" align="center">OBSERVACION</td></tr>
                  <tr>
                     <td colspan="2" align="center">
                        <s:textarea id="observacionaprobar" name="observacionaprobar" cols="40" rows="7" />
                     </td>
                  </tr>
   		   		  
		   		</table>
		 	</td>
         </tr>
        
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
         <input id="botonAceptar" type="button" name="button" value="Aceptar" class="button" onClick="aprobar()"   />        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
         <input type="button" name="button2" value="Cancelar" class="button" onClick="window.close()" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      </td>
    </tr>


    <tr align="center"> 
      <td colspan="2"  align="center">&nbsp;</td>
    </tr>
</table>
</form>
<%@ include file="../util/progressBar.jsp" %>
</body>
</html>
