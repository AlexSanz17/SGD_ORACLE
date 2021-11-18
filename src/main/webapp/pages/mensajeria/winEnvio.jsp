<?xml version="1.0" encoding="UTF-8" ?>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es">
   <head>
      <title>SIGED - Mensajeria</title>
      <script type="text/javascript">
         var IDEXPEDIENTE               = "<s:property value='iIdExpediente' />";
         var CLIENTE_ID                 = "<s:property value='objDocumento.expediente.cliente.idCliente' />";
         var CLIENTE_TIPOIDENTIFICACION = "<s:property value='objDocumento.expediente.cliente.tipoIdentificacion.nombre' />";
         var CLIENTE_NROIDENTIFICACION  = "<s:property value='objDocumento.expediente.cliente.numeroIdentificacion' />";
         var TIPO_IDENTIFICACION_RUC    = "<s:property value='@org.ositran.utils.Constantes@TIPO_IDENTIFICACION_RUC' />";

         //console.debug("(winEnvio.jsp) IDEXPEDIENTE [%s]", IDEXPEDIENTE);
         //console.debug("(winEnvio.jsp) CLIENTE_ID [%s]", CLIENTE_ID);
         //console.debug("(winEnvio.jsp) CLIENTE_TIPOIDENTIFICACION [%s]", CLIENTE_TIPOIDENTIFICACION);
         //console.debug("(winEnvio.jsp) CLIENTE_NROIDENTIFICACION [%s]", CLIENTE_NROIDENTIFICACION);
         //console.debug("(winEnvio.jsp) TIPO_IDENTIFICACION_RUC [%s]", TIPO_IDENTIFICACION_RUC);
      </script>
      <style type="text/css">
         @import "js/dojo/dijit/themes/soria/soria.css";
         @import "css/estilo.css";
         @import "css/sigedIconos.css";
         @import "js/dojo/dojox/form/resources/CheckedMultiSelect.css";
      </style>
      <script type="text/javascript" src="js/dojo/dojo/dojo.js" djConfig="parseOnLoad:true, isDebug:false"></script>
      <script type="text/javascript" src="js/siged/siged.js"></script>
      <script type="text/javascript" src="js/siged/siged.array.js"></script>
      <script type="text/javascript" src="js/siged/siged.forms.js"></script>
      <script type="text/javascript" src="js/siged/siged.string.js"></script>
      <script type="text/javascript" src="js/dojo/js/commons.js"></script>
      <script type="text/javascript" src="js/siged/mensajeria.js"></script>
      <script type="text/javascript">
         dojo.require("dijit.form.Button");
         dojo.require("dijit.form.Form");
         dojo.require("dijit.form.MultiSelect");
         dojo.require("dijit.form.TextBox");
         dojo.require("dijit.form.ValidationTextBox");
         dojo.require("dojox.form.CheckedMultiSelect");
         var serviceMsg = new dojo.rpc.JsonService("SMDAction.action");
      </script>

   </head>

   <body class="soria">
      <div dojoType="dijit.form.Form" id="frmEnvioMensajeria" jsId="frmEnvioMensajeria">
         <table width="100%">
            <tr>
               <td colspan="2"><div class="tituloPagina margen5PX">Env&iacute;o a Mensajer&iacute;a</div></td>
            </tr>
            <tr>
               <td colspan="2">
                  <div id="showErrorMensajeria" style="color:red;font-weight:bold;margin-left:5px;">&nbsp;</div>
               </td>
            </tr>
			<tr>
				<td class="titulo" width="25%">
				<div class="margen5PX">Tipo de envío:</div>
				</td>
				<td class="label" colspan="2">
					<div id="fsTipoenvio"></div>
				</td>
			</tr>
			<tr>
                <td colspan="6" height="5"><hr></td>
            </tr>
 			<tr>
				
				<td class="label" colspan="3" align="left" style="padding-left: 50px">
				<table>
					<tr>
						<td>
							<s:select id="rolIzquierda" list="lstDocumento"
								cssStyle="width:200px;height:120px;" listKey="idDocumento"
								listValue="tipoDocumento.nombre+' - '+numeroDocumento" multiple="true" size="15">
							</s:select></td>
						<td>
							<div id="botones">
								<input onclick="moveToRight(this);"	type="button" id="der" value="-&gt;" /><br />
								<input type="button" onclick="moveToLeft(this);" id="izq" value="&lt;-" />
							</div>
						</td>
						<td>
							<select id="rolDerecha" 
								jsId="rolDerecha"  							
								required="true" 
								invalidMessage="hola"
								style="width: 200px; height: 120px; text-align: left;"								
								dojoType="dojox.form.CheckedMultiSelect" size="15">					
							</select>		
							<!--						onClick="actualizarCliente"-->			
						<s:hidden id="idsDocumentoPorExSeleccionados" name="idsDocumentoPorExSeleccionados" />
						<s:hidden id="idDocPrincipalExpediente" name="idDocPrincipalExpediente" />
						<br />
						</td>
					</tr>
				</table>
				</td>
		</tr>           
          
            
            <tr>
                <td colspan="6" height="5"><hr></td>
            </tr>            
            <tr>
               <td class="titulo"><div class="margen5PX">Destinatarios:</div></td>
               <td>
                  <div id="fsNroIdentificacion" name="fsNroIdentificacion" /></div>
                  <div dojoType="dijit.Dialog"
                       id="dlgClienteCustom"
                       jsId="dlgClienteCustom"
                       draggable="false"
                       style="display:none;font-size: 12px; width: 400px"
                       title="Datos personalizados del Cliente">
                       
               
                     
                     <div dojoType="dijit.form.TextBox"
                          id="idClienteCustom"
                          jsId="idClienteCustom"
                          style="display:none;"></div>
                     <label for="direccionClienteCustom" >Raz&oacute;n Social :</label>
                     <input dojoType="dijit.form.TextBox"
                            id="razonSocialClienteCustom"
                            jsId="razonSocialClienteCustom" 
                            style="width: 275px"  readonly="readonly"/>
                     <br />
                     <label for="direccionClienteCustom">Direcci&oacute;n Destino :</label>
                     <input dojoType="dijit.form.TextBox"
                            id="direccionClienteCustom"
                            jsId="direccionClienteCustom" 
                            style="width: 245px"/>
                     <br />
                     Departamento
                     <div id="fsDepartamento" name="fsDepartamento" style="width: 200px"></div>
                     <br />
                     Provincia
                     <div id="fsProvincia" name="fsProvincia" style="width: 200px"></div>
                     <br />
                     Distrito
                     <div id="fsDistrito" name="fsDistrito" style="width: 200px"></div>
                     <br />

                     <label for="referenciaClienteCustom">Referencia :</label>
                     <input dojoType="dijit.form.TextBox" id="referenciaClienteCustom" jsId="referenciaClienteCustom" style="width: 245px"/>
<br />
                     <button dojoType="dijit.form.Button"
                             type="button"
                             onClick="clienteCustomAdd"
                             showLabel="true">
                        Guardar
                     </button>
                  </div>
                  <button dojoType="dijit.form.Button"
                          type="button"
                          onClick="showDlgClienteCustom"
                          showLabel="true"
                          title="Agregar cliente seleccionado">
                     Agregar cliente
                  </button>
               </td>
            </tr>
            <tr>
               <td class="titulo"><div class="margen5PX">&nbsp;</div></td>
               <td>
                  <label for="msClienteCustom">
                     <button dojoType="dijit.form.Button"
                             type="button"
                             <%--iconClass="siged16 sigedDelete16"--%>
                             onClick="clienteCustomRemove"
                             showLabel="true"
                             title="Eliminar documentos seleccionados">
                        Eliminar
                     </button>
                  </label>
                  <br />
                  <select dojoType="dijit.form.MultiSelect"
                          id="msClienteCustom"
                          jsId="msClienteCustom"
                          style="width: 400px; height: 120px;">
                  </select>
               </td>
            </tr>
            <tr>
               <td colspan="2" height="10">&nbsp;</td>
            </tr>
            <tr>
               <td colspan="2">
                  <div class="margen5PX">
                     <button dojoType="dijit.form.Button"
                             iconClass="siged16 sigedSave16"
                             onClick="window.close();"
                             showLabel="true">Cancelar</button>
                     <button dojoType="dijit.form.Button"
                             iconClass="siged16 sigedSave16"
                             onClick="saveMensajeria"
                             showLabel="true">Enviar</button>
                  </div>
               </td>
            </tr>
         </table>
      </div>
   </body>
</html>
