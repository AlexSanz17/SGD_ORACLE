<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://ajaxtags.org/tags/ajax" prefix="ajax"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<html>
   <head>
      <title>Tramite Documentario</title>
      <style type="text/css"> 
         @import "js/dojo/dijit/themes/soria/soria.css";
      </style>   
      <script type="text/javascript" src="js/dojo/dojo/dojo.js" djConfig="parseOnLoad:true, isDebug: false"></script>
      <script type="text/javascript">
         dojo.require("dojo.io.iframe");
         dojo.require("dijit.Dialog");
		  dojo.require("dijit.ProgressBar");
      </script>
      
      <link rel="stylesheet" type="text/css" href="css/estilo.css">
      <script language="javascript" src="js/form.js"> </script>
      <script language="Javascript" src="js/general.js"></script>
      <script language="Javascript" src="js/valida.js"></script>
 <%--  
      <script type="text/javascript" src="./js/prototype-1.4.0.js"></script>
      <script type="text/javascript" src="./js/scriptaculous.js"></script>
      <script type="text/javascript" src="./js/overlibmws.js"></script>
      <script type="text/javascript" src="./js/ajaxtags-1.2-beta2.js"></script>
      <link rel="stylesheet" type="text/css" href="./css/ajaxtags.css" />
      <link rel="stylesheet" type="text/css" href="./css/displaytag.css" />
 --%>                  
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Nueva Carpeta</title>

   <script language="JavaScript" type="text/javascript">

     function refrescar () {  
       //  alert("refrecar "); 
        window.opener.refrescar(); 
        window.close();    
     } 
     function guardar () { 
         var msg = "";
         var nombrecarpeta =   document.getElementById("nombrecarpeta");
        if(!esVacio(nombrecarpeta.value)) {
        	 document.forms["frmCarpeta"].action = "/siged/Carpeta_guardar.action"  ;
        	 dijit.byId("dlgProgresBar").show() ;       
             document.forms["frmCarpeta"].submit();   
        }else{
        	 var mensaje =   document.getElementById("mensaje");
        	 mensaje.innerHTML ="Debe llenar el campo Nombre" ; 
             mensaje.className = "textoalerta" ; 
        }
         
      
     }
	</script>
</head>
<body class="soria"   onload=" 
   <s:if test="cerrar=='ok'">
      refrescar();
   </s:if>

"
>
 

     
<s:form action="Carpeta_verNueva" method="POST" name="frmCarpeta"  enctype="multipart/form-data"  >
   <s:textfield id="idDocumento" name="idDocumento" cssStyle="display:none"/>
    <s:textfield  name="carpeta.idexpediente.idexpediente" cssStyle="display:none"/>  

         <table width="100%" border="0" >
            <tr> 
               <td height="14" colspan="3"> </td>
            </tr>
            <tr> 
               <td height="20"colspan="3" class="titulo">
                  <table width="99%" border="0" height="20" align="center" bgcolor="#A2C0DC">
                     <tr>
                        <td align="left" class="titulo"> Nuevo Carpeta</td> 
                     </tr>
                  </table>
               </td>
            </tr>    

		</table>
		<table   > 
            <tr>
				<td> &nbsp; 
				</td>
					<td> &nbsp;  &nbsp; &nbsp;<div id="mensaje"> <s:property value="mensaje"/> </div> 
				</td>
			</tr>
		</table>
		<table   width="95%"  border="1" align="center" bordercolor="#669BCD" bgcolor="#BFD9F1"> 
            <tr>
				<td> 
				<table> 
		
		            <tr>
						<td> &nbsp;
						</td>
						<td> &nbsp;
						</td>
						<td> &nbsp;
					
						</td>
					</tr>
		 			<tr>
						<td> &nbsp;
						</td>       
						<td class="titulo"> Nombre Carpeta:
						</td>
						<td class="titulo" ><s:textfield id="nombrecarpeta" name="carpeta.nombre" cssClass="cajaMontoTotal" > </s:textfield> 
						</td>
						
					</tr> 
					<tr>
					<td> &nbsp;
						</td>  
					<td class="titulo" > Carpeta Superior:
						</td> 
					<td  class="label" >    
                       <s:textfield id="nombre" name="nombre" cssClass="cajaMontoTotal" size="35" disabled="true" />
                       <span id="indicatorProc" style="display:none;"><img src="./images/indicator.gif"/></span>
                        <s:textfield id="idcarpeta" name="carpeta.carpetapadre.idcarpeta"  cssStyle="display:none"  /> <!--  cssStyle="display:none" -->
						<s:textfield id="tipo" name="tipo"  cssStyle="display:none"  /> 
						<%-- 
                        <ajax:autocomplete
                                       source="nombre"
                                       target="idcarpeta"
                                       baseUrl="./autocompletar.view?metodo=ObtenerCarpetasPadre"
                                       className="autocomplete"
                                       indicator="indicatorProc"
                                       parameters="idDocumento={idDocumento}"
                                       minimumCharacters="1" 
                                       parser="new ResponseXmlToHtmlListParser()" />
                                       
                          --%> 
                       </td>
					</tr>
					
					<tr>
					<td> &nbsp;
						</td>   
					<td class="label" > 
						</td> 
					<td  class="label" >   
                        
                       </td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		<table   > 
            <tr>
				<td>  
            
			       &nbsp;&nbsp;  <img  onClick="guardar()" src="images/guardar.bmp" border="0" alt="Guardar"/>
	
				</td>
				<td>  
             
			   		 <img src="images//regresar.bmp" border="0" onClick="window.close();" alt="Regresar"/>

				</td>
				</tr>
		</table>

    </s:form>
    
     	<%--@ include file="../util/progressBar.jsp" --%> 
</body>
</html>