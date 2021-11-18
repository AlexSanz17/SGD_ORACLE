<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!--<%@ taglib uri="http://ajaxtags.org/tags/ajax" prefix="ajax"%>-->

<html>
<head>
   <!--<s:head theme="ajax" />-->
<title>Tramite Documentario</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style type="text/css">
    @import "js/dojo/dijit/themes/soria/soria.css";
    @import "css/estilo.css";
</style>
<script language="javascript" src="js/form.js"> </script>
<script language="Javascript" src="js/general.js"></script>
<link rel="stylesheet" type="text/css" media="all" href="css/calendar-green.css">
<script type="text/javascript" src="js/calendar.js"></script>
<script type="text/javascript" src="js/calendar-es.js"></script>
<script type="text/javascript" src="js/calendar-setup.js"></script>

<!--<script type="text/javascript" src="./js/prototype-1.4.0.js"></script>
<script type="text/javascript" src="./js/scriptaculous.js"></script>
<script type="text/javascript" src="./js/overlibmws.js"></script>
<script type="text/javascript" src="./js/ajaxtags-1.2-beta2.js"></script>
<link rel="stylesheet" type="text/css" href="./css/ajaxtags.css" />
<link rel="stylesheet" type="text/css" href="./css/displaytag.css" />-->

<script type='text/javascript' src='./dwr/engine.js'> </script>
<script type='text/javascript' src='./dwr/util.js'> </script>
<script type='text/javascript' src='./dwr/interface/toolDwr.js'> </script>
<!-- Begin DOJO -->
<script type="text/javascript" src="js/dojo/dojo/dojo.js" djConfig="parseOnLoad:true, isDebug: false"></script>
<script type="text/javascript">
   dojo.require("dijit.Dialog");
   dojo.require("dijit.ProgressBar");
</script>
<!-- End DOJO -->
<!-- Begin Scrips STOR-->
<script language="JavaScript">

   function reformulacionTecnicaLegal(){
      var observacion = document.getElementById('observacionreformular');
      if(observacion.value.length == 0){
         var alerta = document.getElementById('requiredObservacion');
         alerta.style.visibility = "visible";
      }else{
         var aceptar = document.getElementById('botonAceptar');
         aceptar.disabled=true;
         document.forms[0].action = '/siged/doStor_reformulacionTecnicaLegal.action' ;
         //alert("antes de mostrar progressbar");
         dijit.byId("dlgProgresBar").show();
         document.forms[0].submit();
      }
   }

   function enabledReformulacionTecnica(){
      txtRefTec = document.getElementById('revisortecnico');
      chkRefTec = document.getElementById('reformulaciontecnica');
      if(txtRefTec.disabled){
         txtRefTec.disabled=false;
         //txtRefTec.readonly=false;
      }else{
         txtRefTec.disabled=true;
         //txtRefTec.readonly=true;
      }
      /*if(chkRefTec.checked){
    chkRefTec.value='si';
 }else{
    chkRefTec.value='no';
 }*/
   }

   function enabledReformulacionLegal(){
      txtRefLeg = document.getElementById('revisorlegal');
      chkRefLeg = document.getElementById('reformulacionlegal');
      if(txtRefLeg.disabled){
         txtRefLeg.disabled=false;
         txtRefLeg.readonly=false;
      }else{
         txtRefLeg.disabled=true;
         txtRefLeg.readonly=true;
      }
      /*if(chkRefLeg.checked){
      chkRefLeg.value='si';
      }else{
      chkRefLeg.value='no';
      }*/
   }

</script>
<!-- End Scrips STOR-->

</head>

<body topmargin="0" leftmargin="0" rigthmargin="0" class="soria"
   <s:if test="refrescar!=null">
      onload="refrescar1()"
   </s:if>
> 
<form>
   <s:hidden name="idDocumento"/>
  <table width="103%">
    <tr> 
      <td height="14" colspan="2"> </td>
    </tr>
    <tr> 
      <td height="13" colspan="2" class="header" ><div align="center">El expediente 
          será Reformulado</div></td>
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
            <s:if test="existeRevisorTecnico">
                  <tr>
                    <td align="left" height="15" >Reformulacion Tecnica</td>
                    <td><s:checkbox id="reformulaciontecnica" name="reformulaciontecnica" onclick="enabledReformulacionTecnica()"/></td>
                    <td>
                       <s:textfield id="revisortecnico" name="revisortecnico" cssClass="cajaMontoTotal" size="15" readonly="true" disabled="true"/>
                        <span id="indicatorRevTec" style="display:none;"><img src="./images/indicator.gif" /></span>
                        <s:textfield id="idrevisortecnico" name="idrevisortecnico" cssStyle="display:none" />

                          <!--<ajax:autocomplete
                    source="revisortecnico"
                    target="idrevisortecnico"
                    baseUrl="./autocompletar.view?metodo=obtenerRevisorTecnicoStor"
                    className="autocomplete"
                    indicator="indicatorRevTec"
                    minimumCharacters="1"
                    parameters="idsala={idsala}"
                    parser="new ResponseXmlToHtmlListParser()" />-->

                    </td>
                  </tr>
               </s:if>
               
               <s:if test="existeRevisorLegal">
                  <tr>                    
                    <td align="left" height="15" >Reformulacion Legal</td>
                    <td><s:checkbox id="reformulacionlegal" name="reformulacionlegal" onclick="enabledReformulacionLegal()"/></td>
                    <td>
                       <s:textfield id="revisorlegal" name="revisorlegal" cssClass="cajaMontoTotal" size="15" readonly="true" disabled="true" />
                        <span id="indicatorRevLeg" style="display:none;"><img src="./images/indicator.gif" /></span>
                        <s:textfield id="idrevisorlegal" name="idrevisorlegal" cssStyle="display:none" />

                          <!--<ajax:autocomplete
                    source="revisorlegal"
                    target="idrevisorlegal"
                    baseUrl="./autocompletar.view?metodo=obtenerRevisorLegalStor"
                    className="autocomplete"
                    indicator="indicatorRevLeg"
                    minimumCharacters="1"
                    parameters="idsala={idsala}"
                    parser="new ResponseXmlToHtmlListParser()" />-->
                    </td>                     
                  </tr>
            </s:if>
            <tr>
               <td colspan="3" align="center">
                  <table>
                     <tr>
                        <td align="center" class="textoalerta">
                           <!--<input type="text" id="requiredObservacion" name="requiredObservacion" value="Campo Obligatorio" class="normal">-->
                           <label id="requiredObservacion" style="visibility:hidden">Campo Obligatorio</label>
                        </td>
                     </tr>
                     <tr>
                        <td><s:textarea name="observacionreformular" cols="50" rows="7" /></td>
                     </tr>
                  </table>
                  
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
      <input id="botonAceptar" type="button" name="button" value="Aceptar" class="button" onClick="reformulacionTecnicaLegal()"   />        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
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
