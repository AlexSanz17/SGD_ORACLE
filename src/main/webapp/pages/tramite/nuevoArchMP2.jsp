<?xml version="1.0" encoding="UTF-8" ?>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://ajaxtags.org/tags/ajax" prefix="ajax"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es">
<head>
<title>Tramite Documentario</title>
 <META HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE" >
  <META HTTP-EQUIV="Expires" CONTENT="-1">
<!--<script type='text/javascript' src='http://getfirebug.com/releases/lite/1.2/firebug-lite-compressed.js'></script>
       <script type="text/javascript">
          firebug.env.height = 150;
       </script>-->

<style type="text/css">
@import "js/dojo/dijit/themes/soria/soria.css";

@import "css/estilo.css";
</style>

<script type="text/javascript" src="js/dojo/dojo/dojo.js"
	djConfig="parseOnLoad:true, isDebug:  false"></script>

<s:url id="autoUrl" action="autocompletarProceso" includeParams="none" />
<s:url id="autoTipoDocumentoUrl" action="autocompletarAllTipoDocumento"
	includeParams="none" />
<s:url id="retrieveConcesionario" action="retrieveDataConcesionario"
	includeParams="none" />
<s:url id="retrieveCliente" action="retrieveDataCliente"
	includeParams="none" />
<s:url id="listaDepartamentos" action="listaDepartamentos"
	includeParams="none" />
<s:url id="listaProvincias" action="listaProvincias"
	includeParams="none" />
<s:url id="listaDistritos" action="listaDistritos" includeParams="none" />
<s:url id="listaMotivos" action="listaMotivos" includeParams="none" />
<s:url id="listaSubMotivos" action="listaSubMotivos"
	includeParams="none" />

<script type="text/javascript">
         dojo.require("dijit.dijit");
         dojo.require("dijit.form.FilteringSelect" );
         dojo.require("dojo.data.ItemFileReadStore");
         dojo.require("dijit.form.DateTextBox");
         dojo.require("dijit.form.ValidationTextBox");

        // var store = new dojo.data.ItemFileReadStore({url: "<s:property value='autoUrl' />"' />);

         dojo.addOnLoad(function() {
            //primer combo
            var store = new dojo.data.ItemFileReadStore({url: "<s:property value='autoUrl' />" ' />);
            var combo = new dijit.form.FilteringSelect({
               store: store,
               labelAttr: "label",
               searchAttr: "label",
               idAttr:"id",
               readOnly:"true"
            }, dojo.byId("dojoidproceso")
         );

            combo.setValue(document.getElementById("idproceso").value);
            combo.setDisplayedValue(document.getElementById("proceso").value);
            //combo.setDisabled(true);

            if(document.getElementById("idtipoidentificacion")!=null){

               gIdNroIdentif = document.getElementById("idtipoidentificacion").value;
               storeClientes = new dojo.data.ItemFileReadStore({url:  "<s:property value='retrieveCliente' />?tipoId="+gIdNroIdentif});
               dijit.byId("objDD.strNroIdentificacion").store=storeClientes;
               dijit.byId("objDD.strNroIdentificacion").setValue(document.getElementById("nroidentificacion").value);
               // dijit.byId("objDD.strNroIdentificacion").setDiplayedValue(document.getElementById("nroidentificacion").value);


               console.log(document.getElementById("nroidentificacion"));
               UpdateClienteSinValidacion(document.getElementById("nroidentificacion").value);

               console.log(document.getElementById("objDD.strAbreviado"));
               if(document.getElementById("objDD.strAbreviado")!=null&&document.getElementById("objDD.strAbreviado").value=='stor'){

                  console.log(document.getElementById("strRUC"));

                  dijit.byId("objDD.strRUC").setValue(document.getElementById("strRUC").value);
               }
            }
            console.log(document.getElementById("idproceso"));
            console.log(document.getElementById("idtipoidentificacion"));
            console.log(document.getElementById("nroidentificacion"));

            dijit.byId("objDD.strFechaDocumento").setValue(new Date());

            console.log("fecha documento "+dijit.byId("objDD.strFechaDocumento").getValue());
         });

         //dijit.byId("idproceso").store=store;

         var storeTipoDocumento = new dojo.data.ItemFileReadStore({url: "<s:property value='autoTipoDocumentoUrl' />"});
         var storeConcesionarios = new dojo.data.ItemFileReadStore({url: "<s:property value='retrieveConcesionario' />"});
         var storeClientes;

         var storeMotivos = new dojo.data.ItemFileReadStore({url: "<s:property value='listaMotivos' />"});
         var storeSubMotivos;

      </script>

<script>
         function Abrir_ventanaBuscar (pagina) {
            var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no,  scrollbars=yes, resizable=yes, width=760, height=530, top=50, left=200";
            window.open(pagina,"",opciones);
         }

         function pasar(i) {
            document.location.href="/siged/doLoadExpediente.action?iIdExp="+i;
         }

         var gIdNroIdentif;

         function openBoxNroIdentif(id) {
            gIdNroIdentif = id;

            document.getElementById("nroidentificacion").value = "";

            //   alert(id)
            storeClientes = new dojo.data.ItemFileReadStore({url: "<s:property value='retrieveCliente' />?tipoId="+id});
            dijit.byId("objDD.strNroIdentificacion").store=storeClientes;
            dijit.byId("objDD.strNroIdentificacion").setDisplayedValue("");
            dijit.byId("objDD.strNroIdentificacion").setValue("");

            document.getElementById("cliente1").style.display = "none";
            document.getElementById("cliente2").style.display = "none";
            document.getElementById("cliente3").style.display = "none";
            document.getElementById("cliente4").style.display = "none";
            document.getElementById("cliente5").style.display = "none";
            document.getElementById("cliente6").style.display = "none";
            document.getElementById("cliente7").style.display = "none";
         }

         function UpdateInfoConcesionarioSinValidacion(strRUC) {
            var s= String(strRUC);

            storeConcesionarios.fetchItemByIdentity({
               identity: s,
               onItem: function(item){
                  document.getElementById("idcorrentista").value =  storeConcesionarios.getValue(item,"idcorrentista");
                  document.getElementById("correntista").value =  storeConcesionarios.getValue(item,"correntista");
                  document.getElementById("strDireccion").value =  storeConcesionarios.getValue(item,"strDireccion");
                  document.getElementById("strCorreoConcesionario").value =  storeConcesionarios.getValue(item,"strCorreoConcesionario");

               }
            });
         }

         function UpdateInfoConcesionario(strRUC) {
            if(!dijit.byId("objDD.strRUC").state && dijit.byId("objDD.strRUC").value!="")
            {
               UpdateInfoConcesionarioSinValidacion(strRUC);
            }
            else
            {
               document.getElementById("idcorrentista").value =  "";
               document.getElementById("correntista").value =  "";
               document.getElementById("strDireccion").value =  "";
               document.getElementById("strCorreoConcesionario").value = "";
            }

         }

         function UpdateClienteSinValidacion(s){

            storeClientes.fetchItemByIdentity({
               identity: s,

               onItem: function(item){
                  console.log(item);
                  document.getElementById("idcliente").value = storeClientes.getValue(item,"idcliente");
                  document.getElementById("strRazonSocial").value = storeClientes.getValue(item,"razonsocial");
                  document.getElementById("strRepresentanteLegal").value = storeClientes.getValue(item,"representantelegal");
                  document.getElementById("strDireccionPrincipal").value = storeClientes.getValue(item,"direccionp");
                  document.getElementById("strDireccionAlternativa").value = storeClientes.getValue(item,"direcciona");
                  document.getElementById("departamento").value = storeClientes.getValue(item, "departamento");
                  document.getElementById("provincia").value = storeClientes.getValue(item, "provincia");
                  document.getElementById("iddistrito").value = storeClientes.getValue(item, "iddistrito");
                  document.getElementById("distrito").value = storeClientes.getValue(item, "distrito");
                  document.getElementById("strTelefonoCliente").value =  storeClientes.getValue(item,"telefono");
                  document.getElementById("strCorreoCliente").value = storeClientes.getValue(item,"correo");
               }
            });

            document.getElementById("cliente1").style.display = "";
            if (gIdNroIdentif == 1)
               document.getElementById("cliente2").style.display = "";
            document.getElementById("cliente3").style.display = "";
            document.getElementById("cliente4").style.display = "";
            document.getElementById("cliente5").style.display = "";
            document.getElementById("cliente6").style.display = "";
            document.getElementById("cliente7").style.display = "";

         }

         function UpdateInfoCliente(numeroIdentificacion){
            var s= String(numeroIdentificacion);

            if(!dijit.byId("objDD.strNroIdentificacion").state && dijit.byId("objDD.strNroIdentificacion").value!="")
            {
               UpdateClienteSinValidacion(s);
            }
            else
            {
               openBoxNroIdentif(gIdNroIdentif);
            }

         }

         function limpiarSubmot(uhmm) {

            var motivo = dijit.byId("dwr_idmotivo");
            document.getElementById("nmotivo").value=motivo.getDisplayedValue();
            document.getElementById("idmotivo").value=motivo.getValue();

            //limpiar combo submotivo
            //document.getElementById("nsubmotivo").value="";
            //document.getElementById("idsubmotivo").value="";
            var subMotivo = dijit.byId("dwr_idsubmotivo");
            subMotivo.setValue("");
            subMotivo.setDisplayedValue("");

            document.getElementById("nsubmotivo").value="";
            document.getElementById("idsubmotivo").value="";

            var s= String(uhmm);
            storeMotivos.fetchItemByIdentity({
               identity: s,

               onItem: function(item){
                  storeSubMotivos = new dojo.data.ItemFileReadStore({url:  "<s:property value='listaSubMotivos' />?idDependencia1="+storeMotivos.getValue(item,"id")});
                  var subMotivo = dijit.byId("dwr_idsubmotivo");
                  subMotivo.store=storeSubMotivos;

               }
            });
            console.log(document.getElementById("idmotivo"));
            console.log(document.getElementById("nmotivo"));

         }

         function setearNombreMotivo() {

            //limpiar combo submotivo
            //document.getElementById("nsubmotivo").value="";
            //document.getElementById("idsubmotivo").value="";
            var subMotivo = dijit.byId("dwr_idsubmotivo");
            document.getElementById("nsubmotivo").value=subMotivo.getDisplayedValue();
            document.getElementById("idsubmotivo").value=subMotivo.getValue();


            console.log(document.getElementById("idsubmotivo"));
            console.log(document.getElementById("nsubmotivo"));
         }

         function submitForm2(){


            if(validarForm()){
               document.forms["frmNewDoc"].submit();
            }



         }


         function addSubMotivoDojo(){

            if(!dijit.byId("dwr_idsubmotivo").state && !dijit.byId("dwr_idmotivo").state && dijit.byId("dwr_idsubmotivo").getValue()!="" && dijit.byId("dwr_idmotivo").getValue()!=""){
               addSubMotivo();
               clearSubMotivoDojo();
            }

         }

         function addSuministroCorregido(){
            if(document.getElementById("ssuministro").value!="")
               addSuministro();
         }

         function clearSubMotivoDojo(){
            dijit.byId("dwr_idsubmotivo").setDisplayedValue("");
            dijit.byId("dwr_idmotivo").setDisplayedValue("");
            dijit.byId("dwr_idsubmotivo").setValue("");
            dijit.byId("dwr_idmotivo").setValue("");
            dijit.byId("dwr_idsubmotivo").store= new dojo.data.ItemFileReadStore();

            clearSubMotivo();
         }

         function validarForm(){

            //Validaciones del formulario caso general
            if(dijit.byId("idtipodocumento").state||dijit.byId("idtipodocumento").value==""){
               alert("Debe especificar un tipo de documento valido");
               dijit.byId("idtipodocumento").focus();
               return false;
            }
            if(document.getElementById("doRegistrar_objDD_strNroDocumento").value==""){
               alert("Debe especificar un numero de documento");
               document.getElementById("doRegistrar_objDD_strNroDocumento").focus();
               return false;
            }
            if(document.getElementById("doRegistrar_objDD_iNroFolios").value==""){
               alert("Debe especificar un numero de folios");
               document.getElementById("doRegistrar_objDD_iNroFolios").focus();
               return false;
            }
            var nroFolios=document.getElementById('doRegistrar_objDD_iNroFolios').value;
            var patternNumber=/^\d+$/;
            if(nroFolios.search(patternNumber)==-1 ){
               alert("El numero de folios debe ser numerico");
               document.getElementById("doRegistrar_objDD_iNroFolios").focus();
               return false;
            }
            if(nroFolios<1 || nroFolios>1000){
               alert("El numero de folios debe estar entre 1 y 1000.");
               document.getElementById("doRegistrar_objDD_iNroFolios").focus();
               return false;
            }
            if(document.getElementById("doRegistrar_objDD_strAsunto").value==""){
               alert("Debe especificar un asunto");
               document.getElementById("doRegistrar_objDD_strAsunto").focus();
               return false;
            }
            if(dijit.byId("objDD.strFechaDocumento").state||dijit.byId("objDD.strFechaDocumento").getDisplayedValue()==""){
               alert("Debe especificar una fecha valida");
               dijit.byId("objDD.strFechaDocumento").focus();
               return false;
            }
            if(dijit.byId("objDD.strNroIdentificacion").state||dijit.byId("objDD.strNroIdentificacion").value==""){
               alert("Debe especificar un documento valido");
               dijit.byId("objDD.strNroIdentificacion").focus();
               return false;
            }



            //Validaciones del formulario cuando el proceso es stor
            if(document.getElementById("objDD.strAbreviado")!=null&&document.getElementById("objDD.strAbreviado").value=='stor'){
               if(document.getElementById("objDD.strRUC").value==""){
                  alert("Debe especificar un RUC");
                  document.getElementById("objDD.strRUC").focus();
                  return false;
               }
               if(document.getElementById("ssuministro").value=""){
                  alert("Debe especificar un numero de suministro.");
                  document.getElementById("ssuministro").focus();
                  return false;
               }
               if(document.getElementById("objDD.strMontoReclamo").value==""){
                  alert("Debe especificar un monto");
                  document.getElementById("objDD.strMontoReclamo").focus();
                  return false;
               }
               var valMonto=document.getElementById('objDD.strMontoReclamo').value;
               var patternCurrency=/^[0-9]{1,9}\.{0,1}[0-9]{1,9}$/;
               if(valMonto.search(patternCurrency)==-1 ){
                  alert("El monto debe ser numerico");
                  document.getElementById("objDD.strMontoReclamo").focus();
                  return false;
               }

            }

            return true;

         }

         function regresar(){
            history.back();
         }

         function nuevoCliente() {
            var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=no, width=900, height=300, top=20, left=70";
            var pagina = "goOpenNuevoCliente.action";

            window.open(pagina, "", opciones);
         }

         function setCheckedValue(radioObj, newValue) {
            if(!radioObj)
               return;

            var radioLength = radioObj.length;

            if(radioLength == undefined) {
               radioObj.checked = (radioObj.value == newValue.toString());
               return;
            }

            for(var i = 0; i < radioLength; i++) {
               radioObj[i].checked = false;
               if(radioObj[i].value == newValue.toString()) {
                  radioObj[i].checked = true;
               }
            }
         }


         function pasarCliente(idtipoidentificacion, nroidentificacion) {
            console.log("[pasarCliente] - idtipoidentificacion [" + idtipoidentificacion + "] nroidentificacion [" + nroidentificacion + "]");
            storeClientes = new dojo.data.ItemFileReadStore({url: "retrieveDataCliente.action?tipoId=" + idtipoidentificacion});
            dijit.byId("objDD.strNroIdentificacion").store=storeClientes;
            dijit.byId("objDD.strNroIdentificacion").setValue("");
            dijit.byId("objDD.strNroIdentificacion").setDisplayedValue("");
            dijit.byId("objDD.strNroIdentificacion").setValue(nroidentificacion);

            setCheckedValue(document.getElementsByName("idtipoidentificacion"), idtipoidentificacion);
            //UpdateClienteSinValidacion(String(nroidentificacion));
         }
      </script>

<script type='text/javascript' src='./dwr/engine.js'> </script>
<script type='text/javascript' src='./dwr/util.js'> </script>
<script type='text/javascript' src='./dwr/interface/toolDwr.js'></script>
<script type='text/javascript' src='./dwr/interface/Motivo.js'> </script>
<script type='text/javascript' src='js/submotivo.js'></script>
<script type='text/javascript' src='./dwr/interface/Suministro.js'> </script>
<script type='text/javascript' src='js/suministro.js'></script>

</head>

<body class="soria">

<s:form name="frmNewDoc" action="doRegistrar" method="POST">

	<s:hidden id="idcliente" name="idcliente" />
	<s:hidden id="nmotivo" name="nmotivo" />
	<s:hidden id="nsubmotivo" name="nsubmotivo" />
	<s:hidden id="idmotivo" name="idmotivo" />
	<s:hidden id="idsubmotivo" name="idsubmotivo" />
	<s:hidden id="idproceso" name="idproceso" />
	<s:hidden id="proceso" name="proceso" />
	<s:hidden id="nroidentificacion" name="nroidentificacion" />
	<s:hidden id="strRUC" name="strRUC" />
	<s:hidden name="objDD.iIdExpediente" id="objDD.iIdExpediente" />
	<s:hidden name="objDD.iIdResponsable" id="objDD.iIdResponsable" />
	<s:hidden name="objDD.strAbreviado" id="objDD.strAbreviado" />

	<s:if test="objDD.iIdExpediente != null">
		<s:hidden id="idtipoidentificacion" name="idtipoidentificacion" />
	</s:if>

	<table width="100%">
		<tr>
			<td height="14" colspan="3"></td>
		</tr>
		<tr>
			<td height="20" colspan="3" class="titulo">
			<table>
				<tr>
					<td><font color="669BCD">Bienvenido </font><font
						color="0099FF"><s:property value="#session.nombres" /></font></td>
				</tr>
				<tr>
					<td><font color="0099FF"><a
						href="<s:url action="doLogout" />" target="_parent">Cerrar
					Sesi&oacute;n</a></font></td>
				</tr>
			</table>
			<table width="99%" border="0" height="20" align="center"
				bgcolor="#A2C0DC">
				<tr>
					<td align="left" class="titulo">Nuevo Documento</td>
				</tr>
			</table>
			</td>
		</tr>
		<tr align="center">
			<td width="1%" align="center">&nbsp;</td>
			<td width="99%" colspan="2" align="left"><img
				src="images//regresar.bmp" border="0" onClick="regresar()"
				alt="Regresar" /> <img src="images/xx.bmp" border="0"
				onClick="javascript:submitForm2()" alt="Registrar" /></td>
		</tr>
		<tr>
			<td height="14" colspan="3">
			<table width="95%" border="1" align="center" bordercolor="#669BCD"
				bgcolor="#BFD9F1">
				<tr>
					<td height="75">
					<table width="98%" height="97" align="center">
						<tr>
							<td width="8"></td>
							<td width="250" height="5"></td>
							<td width="17%"></td>
							<td width="15%"></td>
							<td width="45%"></td>
							<td width="3%"></td>
						</tr>
						<tr>
							<td></td>
							<td class="titulo">Datos del Expediente</td>
							<td colspan="4"></td>
						</tr>
						<tr>
							<td></td>
							<td align="left">Nro Expediente</td>
							<td colspan="3" class="label">&nbsp;<s:textfield
								name="objDD.strReferencia" cssClass="dijitReset" size="20"
								readonly="true" /> <img
								onclick="javascript:Abrir_ventanaBuscar('doLoadSearchExpediente.action')"
								src="images/53.png" height="13" width="23" border="0" /></td>
							<td></td>
						</tr>
						<tr>
							<td></td>
							<td align="left">Proceso</td>
							<td colspan="3" class="label">
							<div id="dojoidproceso" name="dojoidproceso" />
							</td>
							<td></td>
						</tr>
						<tr>
							<td></td>
							<td height="16" align="left">Area Destino</td>
							<td colspan="3" align="left" class="label">&nbsp;<s:textfield
								name="strUnidad" cssClass="dijitReset" size="50" readonly="true" />
							</td>
							<td align="left"></td>
						</tr>
						<tr>
							<td></td>
							<td height="16" align="left">Destinatario</td>
							<td colspan="3" class="label">&nbsp;<s:textfield
								name="strResponsable" cssClass="dijitReset" size="35"
								readonly="true" /></td>
							<td></td>
						</tr>
						<tr>
							<td colspan="6" height="15">
							<hr>
							</td>
						</tr>
						<tr>
							<td colspan="6" height="15" class="label">DOCUMENTO</td>
						</tr>
						<tr>
							<td></td>
							<td height="16" align="left">Tipo Documento</td>
							<td align="left" width="17%" class="label">
							<div dojoType="dijit.form.FilteringSelect"
								store="storeTipoDocumento" idAttr="id" labelAttr="label"
								searchAttr="label" name="idtipodocumento" id="idtipodocumento"
								size="80" />
							</td>
							<td align="left" class="label"></td>
							<td align="left" class="label"></td>
							<td></td>
						</tr>
						<tr>
							<td></td>
							<td height="16" align="left">Nro. Documento</td>
							<td align="left" width="17%" class="label">&nbsp;<s:textfield
								name="objDD.strNroDocumento" cssClass="dijitReset" size="20" />
							</td>
							<td align="left" class="label"></td>
							<td align="left" class="label"></td>
							<td></td>
						</tr>
						<tr>
							<td></td>
							<td height="16" align="left">Nro Folios</td>
							<td align="left" width="17%" class="label">&nbsp;<s:textfield
								name="objDD.iNroFolios" cssClass="dijitReset" size="20" /></td>
							<td align="left"></td>
							<td align="left" width="45%" class="label"></td>
							<td></td>
						</tr>
						<tr>
							<td></td>
							<td height="16" align="left">Asunto</td>
							<td colspan="3" align="left" class="label">&nbsp;<s:textfield
								name="objDD.strAsunto" cssClass="dijitReset" size="60" /></td>
							<td></td>
						</tr>
						<tr>
							<td></td>
							<td height="16" align="left">Fecha Documento</td>
							<td colspan="3" align="left" class="label">
							<div dojoType="dijit.form.DateTextBox"
								id="objDD.strFechaDocumento" jsId="objDD.strFechaDocumento"
								name="objDD.strFechaDocumento"
								constraints="{datePattern:'dd/MM/yyyy'}"
								invalidMessage="Ingrese fecha de Documento dd/MM/yyyy"
								required="true" trim="true" />
							</td>
							<td></td>
						</tr>
						<tr>
							<td colspan="6" height="15">
							<hr>
							</td>
						</tr>
						<tr>
							<td colspan="6" height="15" class="label">CLIENTE</td>
						</tr>
						<tr>
							<td></td>
							<td height="16" align="left" valign="top">Tipo Doc Identidad</td>
							<td align="left" class="label" colspan="5"><s:if
								test="objDD.iIdExpediente != null">
								<s:textfield id="tipoidentificacion" name="tipoidentificacion"
									cssClass="dijitReset" readonly="true" size="50" />
								<s:textfield name="idtipoidentificacion" cssStyle="display:none" />
							</s:if> <s:else>
								<s:iterator value="lstRadio">
									<s:if test='nombre.equals("RUC")'>
										<input type="radio"
											value="<s:property  value="idtipoidentificacion"/>"
											name="idtipoidentificacion"
											onclick="openBoxNroIdentif(this.value)" checked="checked" />
										<s:property value="nombre" />
									</s:if>
									<s:else>
										<input type="radio"
											value="<s:property  value="idtipoidentificacion"/>"
											name="idtipoidentificacion"
											onclick="openBoxNroIdentif(this.value)" />
										<s:property value="nombre" />
									</s:else>
								</s:iterator>
							</s:else> <s:if test="objDD.iIdExpediente == null">
                                       &nbsp;
                                       <img
									src="images/nuevoCliente.bmp" alt="Nuevo Cliente" border="0"
									onclick="nuevoCliente();" width="100" height="27" />
							</s:if></td>
							<td></td>
						</tr>
						<tr>
							<td></td>
							<td height="16" align="left" valign="top">Nro Doc Identidad</td>
							<td id="nroidentif" colspan="3" align="left" class="label">
							<div dojoType="dijit.form.FilteringSelect" idAttr="id"
								labelAttr="label" searchAttr="label"
								name="objDD.strNroIdentificacion"
								id="objDD.strNroIdentificacion"
								invalidMessage="Seleccione un cliente" required="true"
								onChange="UpdateInfoCliente" />
							</td>
							<td></td>
						</tr>
						<s:if test="objDD.iIdExpediente != null">
							<tr id="cliente1">
						</s:if>
						<s:else>
							<tr id="cliente1" style="display: none">
						</s:else>
						<td></td>
						<td height="16" align="left">Nombre/Raz&oacute;n Social</td>
						<td colspan="3" align="left" class="label"><s:textfield
							id="strRazonSocial" name="strRazonSocial" cssClass="dijitReset"
							readonly="true" size="50" /></td>
						<td></td>
						</tr>
						<s:if test="objDD.iIdExpediente != null">
							<tr id="cliente2">
						</s:if>
						<s:else>
							<tr id="cliente2" style="display: none">
						</s:else>
						<td></td>
						<td height="16" align="left">Representante Legal</td>
						<td colspan="3" align="left" class="label"><s:textfield
							id="strRepresentanteLegal" name="strRepresentanteLegal"
							cssClass="dijitReset" readonly="true" size="50" /></td>
						<td></td>
						</tr>
						<s:if test="objDD.iIdExpediente != null">
							<tr id="cliente3">
						</s:if>
						<s:else>
							<tr id="cliente3" style="display: none">
						</s:else>
						<td></td>
						<td height="16" align="left">Direcci&oacute;n</td>
						<td colspan="3" align="left" class="label"><s:textfield
							id="strDireccionPrincipal" name="strDireccionPrincipal"
							cssClass="dijitReset" readonly="true" size="50" /></td>
						<td></td>
						</tr>
						<s:if test="objDD.iIdExpediente != null">
							<tr id="cliente4">
						</s:if>
						<s:else>
							<tr id="cliente4" style="display: none">
						</s:else>
						<td></td>
						<td height="16" align="left">Ubigeo</td>
						<td colspan="3" align="left" class="label">Departamento: <s:textfield
							id="departamento" name="departamento" cssClass="dijitReset"
							readonly="true" size="20" /> Provincia: <s:textfield
							id="provincia" name="provincia" cssClass="dijitReset"
							readonly="true" size="20" /> Distrito: <s:textfield
							id="iddistrito" name="iddistrito" cssStyle="display:none" /> <s:textfield
							id="distrito" name="distrito" cssClass="dijitReset"
							readonly="true" size="20" /></td>
						<td></td>
						</tr>
						<s:if test="objDD.iIdExpediente != null">
							<tr id="cliente5">
						</s:if>
						<s:else>
							<tr id="cliente5" style="display: none">
						</s:else>
						<td></td>
						<td height="16" align="left">Direcci&oacute;n Procesal</td>
						<td colspan="3" align="left" class="label"><s:textfield
							id="strDireccionAlternativa" name="strDireccionAlternativa"
							cssClass="dijitReset" readonly="true" size="50" /></td>
						<td></td>
						</tr>
						<s:if test="objDD.iIdExpediente != null">
							<tr id="cliente6">
						</s:if>
						<s:else>
							<tr id="cliente6" style="display: none">
						</s:else>
						<td></td>
						<td height="16" align="left">Teléfono</td>
						<td colspan="3" align="left" class="label"><s:textfield
							id="strTelefonoCliente" name="strTelefonoCliente"
							cssClass="dijitReset" readonly="true" size="20" /></td>
						<td></td>
						</tr>
						<s:if test="objDD.iIdExpediente != null">
							<tr id="cliente7">
						</s:if>
						<s:else>
							<tr id="cliente7" style="display: none">
						</s:else>
						<td></td>
						<td height="16" align="left">Correo Electrónico</td>
						<td colspan="3" align="left" class="label"><s:textfield
							id="strCorreoCliente" name="strCorreoCliente"
							cssClass="dijitReset" readonly="true" size="50" /></td>
						<td></td>
						</tr>
						<tr>
							<td colspan="6" height="15">
							<hr>
							</td>
						</tr>
						<tr>
							<td></td>
							<td height="16" align="left" valign="top">Observaciones</td>
							<td colspan="3" align="left" class="label"><s:textarea
								name="objDD.strObservacion" cssClass="dijitReset" rows="6"
								cols="75" /></td>
							<td></td>
						</tr>
						<s:if test="objDD.strAbreviado == 'stor'">
							<tr>
								<td colspan="6" height="15">
								<hr>
								</td>
							</tr>
							<tr>
								<td colspan="6" height="15" class="label">CONCESIONARIO</td>
							</tr>
							<tr>
								<td></td>
								<td height="16" align="left">RUC</td>
								<td colspan="3" align="left" class="label">

								<div dojoType="dijit.form.FilteringSelect"
									store="storeConcesionarios" idAttr="id" labelAttr="id"
									searchAttr="id" name="objDD.strRUC" id="objDD.strRUC" size="20"
									onChange="UpdateInfoConcesionario" />
								</td>
								<td></td>
							</tr>
							<tr>
								<td></td>
								<td height="16" align="left">Razón Social</td>
								<td colspan="3" class="label">&nbsp;<s:textfield
									id="correntista" name="correntista" cssClass="dijitReset"
									size="20" readonly="true" /> <s:textfield id="idcorrentista"
									name="idcorrentista" cssStyle="display:none" /></td>
								<td></td>
							</tr>
							<tr>
								<td></td>
								<td height="16" align="left">Dirección</td>
								<td colspan="3" align="left" class="label">&nbsp;<s:textfield
									id="strDireccion" name="strDireccion" cssClass="dijitReset"
									size="50" readonly="true" /></td>
								<td></td>
							</tr>
							<tr>
								<td></td>
								<td height="16" align="left">Correo Electrónico</td>
								<td colspan="3" align="left" class="label">&nbsp;<s:textfield
									id="strCorreoConcesionario" name="strCorreoConcesionario"
									cssClass="dijitReset" size="50" readonly="true" /></td>
								<td></td>
							</tr>
							<tr>
								<td colspan="6" height="15">
								<hr>
								</td>
							</tr>
							<tr>
								<td colspan="6" height="15" class="label">DATOS ADICIONALES</td>
							</tr>
							<tr>
								<td></td>
								<td height="16" align="left" valign="top">Nro Suministro</td>
								<td align="left" class="label" valign="top">
								<table class="plain">
									<tr>
										<td><s:textfield id="ssuministro" name="ssuministro"
											cssClass="dijitReset" size="30" /></td>
									</tr>
									<tr>
										<td colspan="2" align="right"><input class="button"
											type="button" value="Añadir"
											onclick="addSuministroCorregido()" /> <input class="button"
											type="button" value="Limpiar" onclick="clearSuministro()" />
										</td>
									</tr>
								</table>
								</td>
								<td align="left" valign="top" colspan="2" class="label">
								<table border="1" bordercolor="#669BCD" bgcolor="#BFD9F1">
									<thead>
										<tr>
											<th>Suministro</th>
											<th>&nbsp;</th>
										</tr>
									</thead>
									<tbody id="suministrobody">
										<tr id="patternsuministro" style="display: none;">
											<td><span id="tableSuministro">Suministro</span> <input
												type="text" name="storsuministro" id="storsuministro"
												style="display: none" /></td>
											<td><input id="deletesum" type="button" value="Eliminar"
												class="button" onclick="deleteSuministro(this.id)" /></td>
										</tr>
									</tbody>
								</table>
								</td>
								<td></td>
							</tr>
							<tr>
								<td></td>
								<td height="16" align="left" valign="top">Motivo</td>
								<td align="left" class="label" valign="top">
								<table class="plain">
									<tr>
										<td>Motivo</td>
										<td><input dojoType="dijit.form.FilteringSelect"
											store="storeMotivos" idAttr="id" labelAttr="label"
											searchAttr="label" name="dwr_idmotivo" id="dwr_idmotivo"
											size="30" required="false" onchange="limpiarSubmot" /></td>
									</tr>
									<tr>
										<td>SubMotivo</td>
										<td><input dojoType="dijit.form.FilteringSelect"
											idAttr="id" labelAttr="label" searchAttr="label"
											name="dwr_idsubmotivo" id="dwr_idsubmotivo" size="30"
											required="false" onchange="setearNombreMotivo" /></td>
									</tr>
									<tr>
										<td colspan="2" align="right"><input class="button"
											type="button" value="Añadir" onclick="addSubMotivoDojo()" />
										<input class="button" type="button" value="Limpiar"
											onclick="clearSubMotivoDojo()" /></td>
									</tr>
								</table>
								</td>
								<td align="left" valign="top" colspan="2" class="label">
								<table border="1" bordercolor="#669BCD" bgcolor="#BFD9F1">
									<thead>
										<tr>
											<th>Motivo</th>
											<th>SubMotivo</th>
											<th>&nbsp;</th>
										</tr>
									</thead>
									<tbody id="motivobody">
										<tr id="patternsubmotivo" style="display: none;">
											<td><span id="tableMotivo">Motivo</span></td>
											<td><span id="tableSubMotivo">SubMotivo</span> <input
												type="text" name="storidsubmotivo" id="storidsubmotivo"
												style="display: none" /></td>
											<td><input id="deletesub" type="button" value="Eliminar"
												class="button" onclick="deleteSubMotivo(this.id)" /></td>
										</tr>
									</tbody>
								</table>
								</td>
								<td></td>
							</tr>
							<tr>
								<td></td>
								<td height="16" align="left">Monto de Reclamo (S/.)</td>
								<td colspan="3" align="left" class="label"><s:textfield
									id="objDD.strMontoReclamo" name="objDD.strMontoReclamo"
									cssClass="dijitReset" size="30" /></td>
								<td></td>
							</tr>
						</s:if>
						<tr>
							<td></td>
							<td class="titulo">&nbsp;</td>
							<td colspan="4"></td>
						</tr>
					</table>
					</td>
				</tr>
			</table>
			</td>
		</tr>
		<tr align="center">
			<td width="1%" align="center">&nbsp;</td>
			<td width="99%" colspan="2" align="left"><img
				src="images/xx.bmp" border="0" onClick="javascript:submitForm2()"
				alt="Registrar" /></td>
		</tr>
		<tr>
			<td height="14" colspan="3"></td>
		</tr>
	</table>
</s:form>
</body>
</html>