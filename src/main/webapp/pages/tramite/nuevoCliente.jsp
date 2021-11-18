<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Tramite Documentario</title>
<meta http-equiv="cache-control" content="no-cache">
<style type="text/css">
@import "js/dojo/dijit/themes/soria/soria.css";
@import "css/estilo.css";
</style>
      <script type="text/javascript" src="js/dojo/dojo/dojo.js" djConfig="parseOnLoad:true, isDebug: false"></script>

<script type="text/javascript">
         dojo.require("dijit.form.FilteringSelect");
         dojo.require("dijit.form.Form");
         dojo.require("dijit.form.ValidationTextBox");
         dojo.require("dojo.data.ItemFileReadStore");

         var storeDepartamento = new dojo.data.ItemFileReadStore({url: "listaDepartamentos.action"});
         var storeProvincia = null;
         var storeDistrito = null;

         dojo.addOnLoad(function(){
            setRepresentanteLegal('DNI');
         });

         function setRepresentanteLegal(sTipoIdentificacion) {
            var _idR=document.getElementById("idRazon");
            if (sTipoIdentificacion == 'RUC'){
               _idR.innerHTML="Raz&oacute;n Social";
               document.getElementById("representantelegal").style.display = "";
            }
            else{
               _idR.innerHTML="Nombre";
               document.getElementById("representantelegal").style.display = "none";
            }
         }

         function UpdateStoreProvincia(iIdDepartamento) {
            console.log("Departamento seleccionado con ID [" + iIdDepartamento + "]");
            dojo.byId("objCliente.ubigeoprincipal.provincia.idprovincia").value = "";
            dojo.byId("objCliente.ubigeoprincipal.iddistrito").value = "";

            storeProvincia = new dojo.data.ItemFileReadStore({url: "listaProvincias.action?idDepartamento=" + iIdDepartamento});

            dijit.byId("objCliente.ubigeoprincipal.provincia.idprovincia").store = storeProvincia;
         }

         function UpdateStoreDistrito(iIdProvincia) {
            console.log("Provincia seleccionada con ID [" + iIdProvincia + "]");
            dojo.byId("objCliente.ubigeoprincipal.iddistrito").value = "";

            storeDistrito = new dojo.data.ItemFileReadStore({url: "listaDistritos.action?idProvincia=" + iIdProvincia});

            dijit.byId("objCliente.ubigeoprincipal.iddistrito").store = storeDistrito;
         }

         function checkCliente(idtipoidentificacion) {
            if (idtipoidentificacion != null) {
               window.opener.pasarCliente(idtipoidentificacion,'<s:property value='objCliente.nroidentificacion' />');
               window.close();
            }
         }

         validateNroIdentificacion = function(constraints) {
            var sRegExp = "\\d+";

            dojo.query("input[name='objCliente.tipoidentificacion.idtipoidentificacion']")
            .forEach(function(node) {
               //console.log("Nodo [" + node.name + "] ID [" + node.id + "] VALUE [" + node.value + "] CHECKED [" + node.checked + "]");

               if (node.checked) {
                  if (node.value == 1) {

                     sRegExp = "\\d{11}";
                  } else if (node.value == 2) {
                     sRegExp = "\\d{8}";
                  }
               }
            });

            //console.log("sRegExp [" + sRegExp + "]");

            return sRegExp;
         }
      </script>
</head>

   <body class="soria" topmargin="0" leftmargin="0" rigthmargin="0" onload="checkCliente(<s:property value='objCliente.tipoidentificacion.idtipoidentificacion' />)">
      <div dojoType="dijit.form.Form" id="frmCliente" name="frmCliente" action="doSaveCliente.action" method="POST">
         <script type="dojo/method" event="onSubmit">
            if (this.validate()) {
               console.debug('Intentando enviar los sgtes datos:\n', dojo.toJson(this.getValues(), true));

               return true;
            } else {
               dojo.byId('showError').innerHTML='Data invalida... Por favor corriga';

               return false;
            }
         </script>

<table width="100%">
	<tr>
		<td></td>
		<td height="16" colspan="4" align="left" class="plomo"></td>
	</tr>
	<tr align="center">
		<td width="1%" align="center">&nbsp;</td>
               <td width="99%" colspan="2" align="left">
                  <s:submit src="images/guardar.bmp" type="image" />
               </td>
	</tr>
	<tr>
		<td></td>
		<td height="16" colspan="4" align="left" class="plomo"></td>
	</tr>
	<tr>
		<td height="14" colspan="3">
                  <table width="95%" border="1" align="center" bordercolor="#669BCD" bgcolor="#BFD9F1">
			<tr>
				<td height="75">
				<table width="98%" height="97" align="center">
					<tr>
						<td></td>
						<td colspan="5" height="16" align="left" class="label">
						<div id="showError" style="color: red;" />
						</td>
					</tr>
					<tr>
						<td></td>
						<td colspan="5" height="16" align="left" class="label"></td>
					</tr>
					<tr>
						<td></td>
						<td height="16" align="left">Tipo Doc Identidad</td>
                                 <td colspan="3" align="left" class="label">
                                    <s:iterator value="lstRadio">
							<s:if test="nombre.equals('DNI')">
                                          <input type="radio" checked value="<s:property value='idtipoidentificacion' />" name="objCliente.tipoidentificacion.idtipoidentificacion" onclick="setRepresentanteLegal('<s:property value='nombre' />')" /><s:property value='nombre' />
							</s:if>
							<s:else>
                                          <input type="radio" value="<s:property value='idtipoidentificacion' />" name="objCliente.tipoidentificacion.idtipoidentificacion" onclick="setRepresentanteLegal('<s:property value='nombre' />')" /><s:property value='nombre' />
							</s:else>
                                    </s:iterator>
                                 </td>
						<td></td>
					</tr>
					<tr>
						<td></td>
						<td height="16" align="left">Nro Identificacion</td>
                                 <td colspan="3" align="left" class="label">
                                    <input type="text"
							dojoType="dijit.form.ValidationTextBox"
							id="objCliente.nroidentificacion"
							name="objCliente.nroidentificacion"
							invalidMessage="Ingrese n&uacute;mero de identificaci&oacute;n. 8 d&iacute;gitos para DNI, 11 para RUC"
                                           regExpGen="validateNroIdentificacion"
                                           required="true"
                                           style="width:100px;" />
                                 </td>
						<td></td>
					</tr>
					<tr>
						<td></td>
						<td height="16" align="left" id="idRazon">Raz&oacute;n Social</td>
                                 <td colspan="3" class="label">
                                    <input type="text"
							dojoType="dijit.form.ValidationTextBox"
                                           id="objCliente.razonsocial"
                                           name="objCliente.razonsocial"
                                           invalidMessage="Ingrese raz&oacute;n social"
                                           required="true"
                                           style="width:100px;" />
                                 </td>
						<td></td>
					</tr>
					<tr id="representantelegal">
						<td></td>
						<td height="16" align="left">Representante Legal</td>
                                 <td colspan="3" align="left" class="label">
                                    <input type="text"
							dojoType="dijit.form.ValidationTextBox"
							id="objCliente.representantelegal"
							name="objCliente.representantelegal"
                                           invalidMessage="Ingrese representante legal"
                                           required="false"
                                           style="width:100px;" />
                                 </td>
						<td align="left"></td>
					</tr>
					<tr>
						<td></td>
						<td height="16" align="left">Direcci&oacute;n</td>
                                 <td colspan="3" align="left" class="label">
                                    <input type="text"
							dojoType="dijit.form.ValidationTextBox"
							id="objCliente.direccionprincipal"
							name="objCliente.direccionprincipal"
							invalidMessage="Ingrese direcci&oacute;n principal"
                                           required="true"
                                           style="width:200px;" />
                                 </td>
						<td align="left"></td>
					</tr>
					<tr>
						<td></td>
						<td height="16" align="left">Ubigeo</td>
                                 <td align="left" class="label">
                                    Departamento:
						<div dojoType="dijit.form.FilteringSelect"
                                         store="storeDepartamento"
                                         idAttr="id"
                                         labelAttr="label"
							searchAttr="label"
							name="objCliente.ubigeoprincipal.provincia.departamento.iddepartamento"
							id="objCliente.ubigeoprincipal.provincia.departamento.iddepartamento"
                                         invalidMessage="Seleccione un departamento"
                                         required="true"
                                         value=""
                                         onChange="UpdateStoreProvincia" />
						</td>
                                 <td align="left" class="label">
                                    Provincia:
                                    <div dojoType="dijit.form.FilteringSelect"
                                         idAttr="id"
                                         labelAttr="label"
                                         searchAttr="label"
							name="objCliente.ubigeoprincipal.provincia.idprovincia"
							id="objCliente.ubigeoprincipal.provincia.idprovincia"
                                         invalidMessage="Seleccione una provincia"
                                         required="true"
                                         value=""
                                         onChange="UpdateStoreDistrito" />
						</td>
                                 <td align="left" class="label">
                                    Distrito:
                                    <div dojoType="dijit.form.FilteringSelect"
                                         idAttr="id"
                                         labelAttr="label"
                                         searchAttr="label"
							name="objCliente.ubigeoprincipal.iddistrito"
                                         invalidMessage="Seleccione un distrito"
                                         required="true"
                                         value=""
							id="objCliente.ubigeoprincipal.iddistrito" />
						</td>
						<td align="left"></td>
					</tr>
					<tr>
						<td></td>
						<td height="16" align="left">Direcci&oacute;n Procesal</td>
                                 <td colspan="3" align="left" class="label">
                                    <input type="text"
							dojoType="dijit.form.ValidationTextBox"
							id="objCliente.direccionalternativa"
							name="objCliente.direccionalternativa"
							invalidMessage="Ingrese direcci&oacute;n procesal"
                                           required="false"
                                           style="width:200px;" />
                                 </td>
						<td align="left"></td>
					</tr>
					<tr>
						<td></td>
						<td height="16" align="left">Tel&eacute;fono</td>
                                 <td colspan="3" align="left" class="label">
                                    <input type="text"
                                           dojoType="dijit.form.ValidationTextBox"
                                           id="objCliente.telefono"
							name="objCliente.telefono"
                                           invalidMessage="Ingrese tel&eacute;fono"
                                           required="false"
                                           style="width:200px;" />
                                 </td>
						<td align="left"></td>
					</tr>
					<tr>
						<td></td>
						<td height="16" align="left">Correo</td>
                                 <td colspan="3" align="left" class="label">
                                    <input type="text"
                                           dojoType="dijit.form.ValidationTextBox"
                                           id="objCliente.correo"
                                           name="objCliente.correo"
                                           invalidMessage="Ingrese correo"
                                           required="false"
                                           style="width:200px;" />
                                 </td>
						<td align="left"></td>
					</tr>
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
	<tr>
		<td height="14" colspan="3"></td>
	</tr>
</table>
</div>
</body>
</html>
