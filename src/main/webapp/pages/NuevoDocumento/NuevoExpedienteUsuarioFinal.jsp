<?xml version="1.0" encoding="UTF-8" ?>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es">

<head>
<title>Nuevo Documento Usuario Final</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="cache-control" content="no-cache">
<script type="text/javascript">
			var djConfig = {
				isDebug: false,
				parseOnLoad: true
			};
		</script>
<script type="text/javascript" src="js/dojo/dojo/dojo.js"></script>
<script type='text/javascript' src='./dwr/engine.js'></script>
<script type='text/javascript' src='./dwr/util.js'></script>
<script type='text/javascript' src='./dwr/interface/toolDwr.js'></script>

<style type="text/css">
@import "css/siged.css";
@import "js/dojo/dijit/themes/soria/soria.css";
@import "js/dojo/dojox/form/resources/CheckedMultiSelect.css";
@import "css/estilo.css";
@import "css/sigedIconos.css";
</style>



<script type="text/javascript">


 var defIdUsuarioFinal  ="<s:property  value="#session._USUARIO.idusuario" escape="false" />" ;
 var defIdProceso  ="<s:property  value="objDD.iIdProceso" escape="false" />" ;
 dojo.require("dojox.form.CheckedMultiSelect");
 function changeTipoIdentificacion(id){
	 
     dijit.byId("idtipoidentificacion").setValue(id);     
     
  }


 function enviarArchivo(){
 
	 console.log("idcliente .. :" +dijit.byId("idcliente").getValue() ) ;
	getDocsAndPrincipalDoc();
		
     
     dojo.xhrPost({
        form: dojo.byId("frmNuevoDocumentoUF"),
        mimetype: "text/html",
        load: function(data) {
           //dijit.byId("tabNuevoDocumento").attr("content", data);
        }       
     });
   
  }

function getDocsAndPrincipalDoc(){

	var jsRolDerecha=dijit.byId("rolDerecha");
	var elements = jsRolDerecha.getOptions();
	var optn = document.createElement("OPTION");

	dojo.forEach(
			elements,
		    function(item){		    	    	   
			    	
			    	optn.value=item.value;
			    	optn.text=item.label;
			    	document.getElementById("lstDocumentoPorExSeleccionados").options.add(optn);    		        
			}
		);

	
}
 

function moveToRight(btn){  
	
	var jsRolDerecha=dijit.byId("rolDerecha");
	var jsRolIzquierda=document.getElementById("rolIzquierda");	
	
	
	var len1 = jsRolIzquierda.options.length;
	for (i=0;i<len1 ;i++ )
	 {
	  if (jsRolIzquierda.options[i].selected==true)
	  {
		  var opt=jsRolIzquierda.options[i];
			jsRolDerecha.addOption({
				disabled:false,
				label:opt.text,
				selected:false,
				value:opt.value
				});	
		  
                  jsRolIzquierda.options[i] = null;
	 
	  }
   }

}

function moveToLeft(btn){
	
var jsRolDerecha=dijit.byId("rolDerecha");
var elements = jsRolDerecha.getOptions();
var optn = document.createElement("OPTION");


for (i=0;i<elements.length ;i++ )
{
 if (elements[i].selected)
 {
 	optn.value=elements[i].value;
	optn.text=elements[i].label;
	document.getElementById("rolIzquierda").options.add(optn);    
    jsRolDerecha.removeOption(elements[i]);
    break;
    //dojo.some();
 }
}
/*
dojo.forEach(
		elements,
	    function(item){
		    if(item.selected){	    	   
		    	
		    	optn.value=item.value;
		    	optn.text=item.label;
		    	document.getElementById("rolIzquierda").options.add(optn);    
		        jsRolDerecha.removeOption(item);
		        dojo.some();
		        		    	
			    }
		           
	    }
	);
*/


}




</script>

<script type="text/javascript"
	src="js/siged/NuevoExpedienteUsuarioFinal.js"></script>

</head>

<body class="soria">


<form dojoType="dijit.form.Form" id="frmNuevoDocumentoUF"
	jsId="frmNuevoDocumentoUF" method="post"
	action="saveNuevoExpedienteUF.action">

<table width="100%">
	<tr>
		<td width="95%" align="left">
		<div class="margen5PX">

		<button dojoType="dijit.form.Button" type="button"
			id="btnRegistrarDocumentoTop" jsId="btnRegistrarDocumentoTop"
			iconClass="siged16 sigedSave16" onClick="submitForm" showLabel="true">Registrar
		Documento</button>

		</div>
		</td>
	</tr>


</table>

<div class="marcoForm margen5PX">
<table width="100%">

	<tr>
		<td>Proceso</td>
		<td class="label" colspan="2"><input
			dojoType="dijit.form.TextBox" id="idproceso" jsId="idproceso"
			name="idproceso" style="display: none;" />
		<div id="fsProceso"></div>
		</td>
	</tr>
	<tbody id="tbProceso">
		<tr>
			<td>Destinatario</td>
			<td class="label" colspan="2">
			<div id="fsUsuarioFinal"></div>	
			</td>
		</tr>
		<tr>
			<td>Documentos</td>
			<td class="label" colspan="2">

			<fieldset><legend>Documentos Disponibles</legend>


			<table>
				<tr>
					<td><s:select id="rolIzquierda" list="lstDocumento"
						cssStyle="width:200px;height:120px;" listKey="idDocumento"
						listValue="tipoDocumento.nombre+' - '+numeroDocumento" multiple="true" size="15"></s:select></td>
					<td>
					<div id="botones"><input onclick="moveToRight(this);"
						type="button" id="der" value="-&gt;"  /><br />
					<input type="button" onclick="moveToLeft(this);" id="izq"
						value="&lt;-" /></div>
					</td>
					<td><select id="rolDerecha" jsId="rolDerecha"  required="true" invalidMessage="hola"
						style="width: 200px; height: 120px; text-align: left;"
						dojoType="dojox.form.CheckedMultiSelect" size="15">					
					</select>					
					<s:hidden id="idsDocumentoPorExSeleccionados" name="idsDocumentoPorExSeleccionados" />
					<s:hidden id="idDocPrincipalExpediente" name="idDocPrincipalExpediente" />
									
						
										
					<br />
					</td>
				</tr>
			</table>



			</fieldset>





			</td>
		</tr>
	</tbody>
	<tbody id="tbCliente">
		<tr>
			<td colspan="3"></td>
		</tr>
		<tr>
			<td colspan="3" class="titulo">CLIENTE</td>
		</tr>
		<tr>
			<td>Tipo Doc Identidad</td>
			<td class="label" colspan="2"><input
				dojoType="dijit.form.RadioButton" type="radio"
				id="sTipoIdentificacionRUC" name="sTipoIdentificacion" checked
				onClick="changeTipoIdentificacion(1);updateStore(this.value, 'cliente');hideTBODY('tbDataCliente');"
				value="RUC" />RUC <input dojoType="dijit.form.RadioButton"
				type="radio" id="sTipoIdentificacionDNI" name="sTipoIdentificacion"
				onClick="changeTipoIdentificacion(2);updateStore(this.value, 'cliente');hideTBODY('tbDataCliente');"
				value="DNI" />DNI <input dojoType="dijit.form.RadioButton"
				type="radio" id="sTipoIdentificacionOtros"
				name="sTipoIdentificacion"
				onClick="changeTipoIdentificacion(3);updateStore(this.value, 'cliente');hideTBODY('tbDataCliente');"
				value="Otro" />Otro <input dojoType="dijit.form.TextBox"
				id="idtipoidentificacion" jsId="idtipoidentificacion"
				name="idtipoidentificacion" style="display: none;" /></td>
		</tr>
		<tr>
			<td width="12%">Nro Doc Identidad</td>
			<td class="label" width="20%"><input
				dojoType="dijit.form.TextBox" id="idcliente" jsId="idcliente"
				name="idcliente" style="display: none;" />
			<div id="fsNroIdentificacion" name="fsNroIdentificacion" />

			</td>
			<td width="68%">
			<div id="showExpedienteAbierto"
				style="color: blue; font-weight: bold;">&nbsp;</div>
			</td>
		</tr>
	</tbody>

	<tbody id="tbDataRUC" style="display: none;">
		<tr>
			<td>Raz&oacute;n Social</td>
			<td class="label" colspan="2">
			<div dojoType="dijit.form.TextBox" id="strRazonSocial"
				jsId="strRazonSocial" name="objDD.clienterazonsocial"
				readOnly="true" style="width: 300px" trim="true" />
			</td>
		</tr>
		<tr>
			<td>Representante Legal</td>
			<td class="label" colspan="2">
			<div dojoType="dijit.form.TextBox" id="strRepresentanteLegal"
				jsId="strRepresentanteLegal" name="objDD.clienterepresentantelegal"
				readOnly="true" style="width: 300px" trim="true" />
			</td>
		</tr>
	</tbody>
	<tbody id="tbDataDNI" style="display: none;">
		<tr>
			<td>Nombres</td>
			<td class="label" colspan="2">
			<div dojoType="dijit.form.TextBox" id="sNombres" jsId="sNombres"
				name="objDD.clientenombres" readOnly="true" style="width: 300px"
				trim="true" />
			</td>
		</tr>
		<tr>
			<td>Apellido Paterno</td>
			<td class="label" colspan="2">
			<div dojoType="dijit.form.TextBox" id="sApellidoPaterno"
				jsId="sApellidoPaterno" name="objDD.clienteapellidopaterno"
				readOnly="true" style="width: 300px" trim="true" />
			</td>
		</tr>
		<tr>
			<td>Apellido Materno</td>
			<td class="label" colspan="2">
			<div dojoType="dijit.form.TextBox" id="sApellidoMaterno"
				jsId="sApellidoMaterno" name="objDD.clienteapellidomaterno"
				readOnly="true" style="width: 300px" trim="true" />
			</td>
		</tr>
	</tbody>

	<tbody id="tbDataCliente" style="display: none;">
		<tr>
			<td>Direcci&oacute;n</td>
			<td class="label" colspan="2">
			<div dojoType="dijit.form.TextBox" id="sDireccion1"
				jsId="sDireccion1" name="objDD.clientedireccionprincipal"
				readOnly="true" style="width: 300px" trim="true" />
			</td>
		</tr>
		<tr>
			<td>Ubigeo</td>
			<td class="label" colspan="2">Departamento <input
				dojoType="dijit.form.TextBox" id="sDepartamento1"
				jsId="sDepartamento1" name="sDepartamento1" readOnly="true"
				trim="true" /> Provincia <input dojoType="dijit.form.TextBox"
				id="sProvincia1" jsId="sProvincia1" name="sProvincia1"
				readOnly="true" trim="true" /> Distrito <input
				dojoType="dijit.form.TextBox" id="sDistrito1" jsId="sDistrito1"
				name="sDistrito1" readOnly="true" trim="true" /></td>
		</tr>
		<tr>
			<td>Direcci&oacute;n Procesal</td>
			<td class="label" colspan="2"><input
				dojoType="dijit.form.TextBox" id="sDireccion2" jsId="sDireccion2"
				name="objDD.clientedireccionalternativa" readOnly="true"
				style="width: 300px" trim="true" /></td>
		</tr>
		<tr>
			<td>Ubigeo Procesal</td>
			<td class="label" colspan="2">Departamento <input
				dojoType="dijit.form.TextBox" id="sDepartamento2"
				jsId="sDepartamento2" name="sDepartamento2" readOnly="true"
				trim="true" /> Provincia <input dojoType="dijit.form.TextBox"
				id="sProvincia2" jsId="sProvincia2" name="sProvincia2"
				readOnly="true" trim="true" /> Distrito <input
				dojoType="dijit.form.TextBox" id="sDistrito2" jsId="sDistrito2"
				name="sDistrito2" readOnly="true" trim="true" /></td>
		</tr>
		<tr>
			<td>Tel&eacute;fono</td>
			<td class="label" colspan="2">
			<div dojoType="dijit.form.TextBox" id="sTelefono" jsId="sTelefono"
				name="objDD.clientetelefono" readOnly="true" trim="true" />
			</td>
		</tr>
		<tr>
			<td>Correo Electr&oacute;nico</td>
			<td class="label" colspan="2">
			<div dojoType="dijit.form.TextBox" id="sCorreoCliente"
				jsId="sCorreoCliente" name="objDD.clientecorreo" readOnly="true"
				style="width: 300px" trim="true" />
			</td>
		</tr>
	</tbody>

</table>
</div>



</form>
<div class="margen5PX">
<button dojoType="dijit.form.Button" type="button"
	id="btnRegistrarDocumentoBottom" jsId="btnRegistrarDocumentoBottom"
	iconClass="siged16 sigedSave16" onClick="submitForm" showLabel="true">Registrar
Documento</button>
</div>

</body>

</html>