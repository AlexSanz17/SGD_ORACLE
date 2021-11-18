<?xml version="1.0" encoding="UTF-8" ?>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://ajaxtags.org/tags/ajax" prefix="ajax"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es">
<head>   
<title>Tramite Documentario</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style type="text/css">
   @import "js/dojo/dijit/themes/soria/soria.css";
   @import "css/estilo.css";
</style>
<script type='text/javascript' src='./dwr/engine.js'> </script>
<script type='text/javascript' src='./dwr/util.js'> </script>
<script type='text/javascript' src='./dwr/interface/toolDwr.js'> </script>
<!-- Begin DOJO -->
<script type="text/javascript" src="js/dojo/dojo/dojo.js" djConfig="parseOnLoad:true, isDebug: false"></script>
<script type="text/javascript">
   dojo.require("dijit.form.FilteringSelect" );
   dojo.require("dojo.data.ItemFileReadStore");
   dojo.require("dijit.Dialog");
   dojo.require("dijit.ProgressBar");

   var storeRevTec = new dojo.data.ItemFileReadStore({url: "autocompletarResponsableTecnico.action?idDependencia1="+<s:property value="idsala" />});
   var storeRevLeg = new dojo.data.ItemFileReadStore({url: "obtenerRevisorLegalStor.action?idDependencia1="+<s:property value="idsala" />});
   
</script>
<!-- End DOJO -->

<!-- Begin Scrips STOR-->
<script language="JavaScript">

   function analisisExpediente(){
      if(validarCamposObligatorios()){
         var aceptar = document.getElementById('botonAceptar');
         aceptar.disabled=true;
         document.forms[0].action = '/siged/doStor_analisisExpediente.action';
         dijit.byId("idrevisortecnico").attr('disabled', false);
         dijit.byId("idrevisorlegal").attr('disabled', false);
         dijit.byId("dlgProgresBar").show();
         document.forms[0].submit();
      }else{
         //alert("Formulario Invalido - No pasar al Action");
      }
      
   }

   function enabledRevisionTecnica(){
      divRevTec = document.getElementById('idrevisortecnico');
      chkRevTec = document.getElementById('revisiontecnica');
      divRevLeg = document.getElementById('idrevisorlegal');
      chkRevLeg = document.getElementById('revisionlegal');

      if(divRevTec.disabled){
         dijit.byId("idrevisortecnico").attr('disabled', false);
         chkRevLeg.disabled=true;         
      }else{         
         dwr.util.setValue("idrevisortecnico","");         
         dijit.byId("idrevisortecnico").attr('disabled', true);
         chkRevLeg.disabled=false;         
      }      
   }

   function enabledRevisionLegal(){
      estado = document.getElementById('estadoexpedientestor');
      divRevLeg = document.getElementById('idrevisorlegal');
      chkRevLeg = document.getElementById('revisionlegal');
      chkRevTec = document.getElementById('revisiontecnica');

      if(estado.value=='rleg'){
         if(!chkRevLeg.checked){            
            dwr.util.setValue("idrevisorlegal","");
            chkRevTec.disabled=false;
         }else{
            var defIdRL = dwr.util.getValue("defaultIdRevLeg");
            //var defRL = dwr.util.getValue("defaultRevLeg");
            //alert(defIdRL+" "+defRL);
            dijit.byId("idrevisorlegal").setValue(defIdRL);
            
            chkRevTec.disabled=true;
         }
      }else{
         if(divRevLeg.disabled){            
            dijit.byId("idrevisorlegal").attr('disabled', false);
            chkRevTec.disabled=true;
         }else{
            dwr.util.setValue("idrevisorlegal","");
            dijit.byId("idrevisorlegal").attr('disabled', true);
            chkRevTec.disabled=false;
         }
      }

   }

   function validarRechazoLegal(){
      estado = document.getElementById('estadoexpedientestor');
      if(estado.value=='rleg'){
         chkRevTec = document.getElementById('revisiontecnica');
         //alert(chkRevTec.checked);
         dojo.byId("revisiontecnica").disabled=true;
      }
   }

   function validarCamposObligatorios(){
      var validarRevisor;
      chkRevLeg = document.getElementById('revisionlegal');
      chkRevTec = document.getElementById('revisiontecnica');
      if(chkRevTec.checked){
         var revTec = dijit.byId("idrevisortecnico");
         if(revTec.isValid() && revTec.getValue().length != 0){
            validarRevisor = true;
         }else{
            revTec.setDisplayedValue("Campo Obligatorio");
            revTec.focus();
         }
      } else if(chkRevLeg.checked){
         var revLeg = dijit.byId("idrevisorlegal");
         if(revLeg.isValid() && revLeg.getValue().length != 0){
            validarRevisor = true;
         }else{
            revLeg.setDisplayedValue("Campo Obligatorio");
            revLeg.focus();
         }
      }

      return validarRevisor;
   }

   function clearCamposObligatorios(idCampo){
      var campoDojo = dijit.byId(idCampo);
      campoDojo.setValue("");
      campoDojo.setDisplayedValue("");
   }

</script>
<!-- End Scrips STOR-->

</head>

<body topmargin="0" leftmargin="0" rigthmargin="0" class="soria" onload="validarRechazoLegal()">
<form>
   <s:hidden name="idDocumento"/>
   <!--Variables Quejas - Medida Cautelar-->
   <s:hidden id="estadoexpedientestor" name="estadoexpedientestor" />
   <s:if test="estadoexpedientestor.equalsIgnoreCase('rleg')">
      <input type="hidden" id="defaultIdRevLeg" value="<s:property value="idrevisorlegal"/>"/>
      <input type="hidden" id="defaultRevLeg" value="<s:property value="revisorlegal"/>"/>         
   </s:if>
   <!--Variables Quejas - Medida Cautelar-->
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
				<tr><td colspan="3"><s:hidden name="idsala" /></td></tr>
   		   		<tr>                    
                    <td align="left" height="15" >Revision Tecnica</td>
                    <td><s:checkbox id="revisiontecnica" name="revisiontecnica" onclick="enabledRevisionTecnica()"/></td>
                    <td>
                       <select dojoType="dijit.form.FilteringSelect"
                           store="storeRevTec"
                           idAttr="id"
                           labelAttr="label"
                           searchAttr="label"
                           name="idrevisortecnico"
                           id="idrevisortecnico"
                           size="1"                           
                           disabled></select>
                    </td>                    
             </tr>
             <tr>                    
                    <td align="left" height="15" >Revision Legal</td>
                    <td s><s:checkbox id="revisionlegal" name="revisionlegal" onclick="enabledRevisionLegal()"/></td>
                    <td>
                       <select dojoType="dijit.form.FilteringSelect"
                           store="storeRevLeg"
                           idAttr="id"
                           labelAttr="label"
                           searchAttr="label"
                           name="idrevisorlegal"
                           id="idrevisorlegal"
                           value="<s:property value="idrevisorlegal" />"
                           size="1"                           
                           disabled></select>
                    </td>                     
            </tr>
            <tr><td colspan="3" align="center">OBSERVACION</td></tr>
            <tr>
               <td colspan="3" align="center">
                  <s:textarea id="observacionaprobar" name="observacionaprobar" cols="40" rows="7"/>
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
      <input id="botonAceptar" type="button" name="button" value="Aceptar" class="button" onClick="analisisExpediente()"   />        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
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
