<?xml version="1.0" encoding="UTF-8" ?>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es">
   <head>
      <title>SIGED - Nuevo Documento Usuario Final</title>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
      <meta http-equiv="cache-control" content="no-cache" />
       <META HTTP-EQUIV="Expires" CONTENT="-1">
      <script type="text/javascript">
         var AUTOR_DEFAULTID        = "<s:property  value='#session._USUARIO.nombres' escape='false' /> <s:property  value='#session._USUARIO.apellidos' escape='false' />";
         var DOCUMENTO_ID           = "<s:property value='idDocumento' />";
         var ND_NUMERODOCUMENTO     = "<s:property value='documento.tipoDocumento.nombre' escape='false' /> - <s:property value='documento.numeroDocumento' escape='false' />";
         var FIRMANTE_DEFAULTID     = "<s:property value='#session._USUARIO.idusuario' />";
         var CODIGO_CI  			= "<s:property value='@org.ositran.utils.Constantes@CODIGO_COMUNICACIONES_INT' />";
         var CODIGO_COMUNICACIONES_DE  			= "<s:property value='@org.ositran.utils.Constantes@CODIGO_COMUNICACIONES_DE' />";
         var CODIGO_COMUNICACIONES_TUPA			= "<s:property value='@org.ositran.utils.Constantes@CODIGO_COMUNICACIONES_TUPA' />";
         var TIPO_DOCUMENTO_CARTA_CLIENTE   =  "<s:property value='@org.ositran.utils.Constantes@TIPO_DOCUMENTO_CARTA_CLIENTE' />";
         var TIPO_DOCUMENTO_OFICIO_CLIENTE   = "<s:property value='@org.ositran.utils.Constantes@TIPO_DOCUMENTO_OFICIO_CLIENTE' />";

         var ID_PROCESO_DOCUMENTOS_EXTERNOS    = "<s:property value='@org.ositran.utils.Constantes@ID_PROCESO_DOCUMENTOS_EXTERNOS ' />";

         var JEFE_USUARIOLOGEADO_ID = "<s:property value='#session._USUARIO.jefe.idusuario' />";
         var CLIENTE_OSINERGMIN_ID  = "<s:property value='@org.ositran.utils.Constantes@CLIENTE_OSINERGMIN_ID' />";
         var ROL_USUARIOLOGEADO_ID  = "<s:property value='#session._USUARIO.rol.idrol' />";
         var USUARIOLOGEADO_ID      = "<s:property value='#session._USUARIO.idusuario' />";
		 var EXPEDIENTE_REFERENCIA = "<s:property value='idExpediente' />";
		 var ORIGEN_EXPEDIENTE = "<s:property value='origenExpediente' />";
		 var DOCUMENTO_REFERENCIA = "<s:property value='documento.tipoDocumento.nombre' escape='false'/> - <s:property value='documento.numeroDocumento' escape='false'/>";
		 var fsProcesoFocused = 0;
		 var strBusquedaExpedienteAbierto;
		 var iExpedientesAbiertos = 0;
		 var bIsPosting = false;
		 var defIdProceso="";
		 var defIdUsuarioFinal=0;
		 var g_sumilla = "";
		 var nombreProceso = "";
		 var unidadActual = null;
		 var storeDestinatariosClientes =null;
		 var storeDestinatariosUsuarios =null;
		 var storeCopias = null;
		 var storeFirmante=null;
		 //var idPropietario = "";

         console.debug("(NuevoDocumento.jsp) AUTOR_DEFAULTID [%s]", AUTOR_DEFAULTID);
         console.debug("(NuevoDocumento.jsp) DOCUMENTO_ID [%s]", DOCUMENTO_ID);
         console.debug("(NuevoDocumento.jsp) FIRMANTE_DEFAULTID [%s]", FIRMANTE_DEFAULTID);
         console.debug("(NuevoDocumento.jsp) JEFE_USUARIOLOGEADO_ID [%s]", JEFE_USUARIOLOGEADO_ID);
         console.debug("(NuevoDocumento.jsp) CLIENTE_OSINERGMIN_ID [%s]", CLIENTE_OSINERGMIN_ID);
         console.debug("(NuevoDocumento.jsp) ROL_USUARIOLOGEADO_ID [%s]", ROL_USUARIOLOGEADO_ID);
         console.debug("(NuevoDocumento.jsp) USUARIOLOGEADO_ID [%s]", USUARIOLOGEADO_ID);

         function llenarCamposReferencia(){

         	dijit.byId("sNroExpediente").attr("value", "<s:property value='expediente.nroexpediente' />");
        	if(dijit.byId("objDocumento.expediente.idexpediente")){
        		dijit.byId("objDocumento.expediente.idexpediente").setValue("<s:property value='expediente.id' />");
			}
        	else if(dijit.byId("objDD.iIdExpediente")){
        		dijit.byId("objDD.iIdExpediente").setValue("<s:property value='expediente.id' />");
        	}

        	fsProcesoFocused = 0;
        	expedienteNuevo = false;
        	//idPropietario = "<s:property value='expediente.id' />";
        	dijit.byId("objDocumento.expediente.proceso.idproceso").setValue("<s:property value='expediente.proceso.idproceso' />");
        	dijit.byId("objDocumento.expediente.proceso.idproceso").attr("readOnly", true);
        	dijit.byId("objDD.asuntoExpediente").setValue("<s:property value='expediente.getAsuntoHTML()' escape='false'/>");
			dijit.byId("objDD.asuntoExpediente").attr("readOnly", true);

			dijit.byId("objDD.observacionExpediente").setValue("<s:property value='expediente.getObservacionHTML()' escape='false'/>");
			dijit.byId("objDD.observacionExpediente").attr("readOnly", true);

        	dojo.query("input[name='sTipoIdentificacion']").forEach(function(node) {
        	      console.log("Nodo [" + node.name + "] ID [" + node.id + "] VALUE [" + node.value + "] CHECKED [" + node.checked + "]");
        	      dijit.byId(node.id).attr("checked", false);
        	      dijit.byId(node.id).attr("readOnly", true);

        	      if (node.value == "<s:property value='expediente.cliente.tipoIdentificacion.nombre' />") {
        	         dijit.byId(node.id).attr("checked", true);
        	      }
        	   });

        	if (!Siged.String.isEmpty(ROL_USUARIOLOGEADO_ID) || ("<s:property value='expediente.proceso.codigo' />" != CODIGO_CI && Siged.String.isEmpty(ROL_USUARIOLOGEADO_ID))) {
        		hideTBODY("tbNuevoCliente");
        	    updateStore("<s:property value='expediente.cliente.tipoIdentificacion.nombre' />", "cliente");
        	    dijit.byId("objDocumento.expediente.cliente.numeroIdentificacion").setValue("<s:property value='expediente.cliente.numeroIdentificacion' />");
        	    dijit.byId("objDocumento.expediente.cliente.numeroIdentificacion").attr("readOnly", true);
        	    fillDataCliente("<s:property value='expediente.cliente.numeroIdentificacion' />");
			}
         }
      </script>
      <script type="text/javascript" src="js/busquedaExpediente.js"></script>
      <script type="text/javascript" src="js/siged/upload.js"></script>
      <script type="text/javascript" src="js/siged/NuevoDocumento.js"></script>
      <script type="text/javascript">
         if (dojo.isIE) {
            console.debug("Eliminando en navegador Internet Explorer");
            destroyWidgetsFromBusquedaExpediente();
         }
      </script>
   </head>
   <body>
      <div class="margen5PX">
         <button id="btn">Adjuntar</button>
         <button dojoType="dijit.form.Button"
                 type="button"
                 id="btnRegistrarDocumentoTop"
                 jsId="btnRegistrarDocumentoTop"
                 iconClass="siged16 sigedSave16"
                 onClick="submitForm"
                 showLabel="true">Registrar Documento</button>
         <div id="divShowFile2"></div>
      </div>
      <form dojoType="dijit.form.Form" id="frmNuevoDocumento" jsId="frmNuevoDocumento" action="NuevoDocumentoEnviarArchivo.action" method="post">
         <table width="100%">
            <tr>
               <td>
                  <div id="showErrorDocumento" class="margen5PX" style="color:red;font-weight:bold;">&nbsp;</div>
               </td>
            </tr>
            <tr>
               <td>
                  <div class="marcoForm margen5PX">
                     <table width="100%">
                        <tr>
                           <td width="20%" class="titulo">DATOS DEL EXPEDIENTE</td>
                           <td width="80%" colspan="2"></td>
                        </tr>
                        <tr>
                           <td colspan="3">
                              <input id="objDD.iIdExpediente" style="display:none;" />
                              <s:hidden id="objDD.iIdResponsable" name="objDD.iIdResponsable" />
                              <input type="hidden" id="objDocumento.expediente.sumilla" />
                           </td>
                        </tr>
                        <tr>
                           <td width="20%">Nro Expediente</td>
                           <td class="label" colspan="2" width="80%">
                              <input id="sNroExpediente" />
                              <button dojoType="dijit.form.Button"
                                      id="btnBusquedaExpediente"
                                      jsId="btnBusquedaExpediente"
                                      iconClass="siged16 sigedSearch16"
                                      onClick="showBusquedaExpediente"
                                      showLabel="false">B&uacute;squeda de Expediente</button>
                           </td>
                        </tr>
                        <tr>
                           <td>Proceso</td>
                           <td class="label" colspan="2">
                              <input id="idproceso" style="display:none;" />
                              <div id="fsProceso"></div>
                              <span id="ambienteProceso" style="font-weight:bold;">&nbsp;</span>
                           </td>
                        </tr>
                        <tbody id="tbProceso" style="display:none;">
                           <%--<tr>
                              <td>Area Destino</td>
                              <td class="label" colspan="2">
                                 <div dojoType="dijit.form.TextBox"
                                      id="sUnidad"
                                      jsId="sUnidad"
                                      name="sUnidad"
                                      readOnly="true"
                                      style="width:300px"
                                      trim="true" />
                              </td>
                           </tr>--%>
                           <tr id="trEnviarA">
                              <td><!-- Enviar a -->&nbsp;</td>
                              <td class="label" colspan="2">
                                 <input id="fsUsuarioFinal" name="objDD.iIdDestinatario" type="hidden"/>
                              </td>
                           </tr>

                           <tr>
                              <td>Asunto del Expediente</td>
                              <td class="label" colspan="2">
                                 <div id="objDD.asuntoExpediente"/>
                              </td>
                           </tr>

  						   <tr>
                              <td>Observaciones del Expediente</td>
                              <td class="label" colspan="2">
                                 <div id="objDD.observacionExpediente"/>
                              </td>
                           </tr>



                           <tr id="trApoyo" style="display:none;">
                           	  <td>Apoyo</td>
                           	  <td><button dojoType="dijit.form.Button" onClick="mostrarUsuariosProceso()">Agregar</button></td>
                           </tr>
                           <tr>
                           	  <td>&nbsp;</td>
                           	  <td><div id="apoyoNuevoDocUF" name="apoyoNuevoDocUF"></div></td>
                           </tr>
                           <tr>
                              <td colspan="3">&nbsp;</td>
                           </tr>
                        </tbody>
                        <tbody id="tbDocumento" style="display:none;">
                           <tr>
                              <td colspan="3"><hr /></td>
                           </tr>
                           <tr>
                              <td colspan="3" class="titulo">DOCUMENTO</td>
                           </tr>
                           <tr>
                              <td>Tipo Documento</td>
                              <td class="label" colspan="2">
                                 <input dojoType="dijit.form.TextBox"
                                        id="idtipodocumento"
                                        jsId="idtipodocumento"
                                        name="idtipodocumento"
                                        style="display:none; " />
                                 <div id="fsTipoDocumento" name="fsTipoDocumento" style="width:300px;"></div>
                              </td>
                           </tr>
                           <tr>
                              <td>Asunto del Documento</td>
                              <td class="label" colspan="2">
                                 <div dojoType="dijit.form.ValidationTextBox"
                                      id="objDD.strAsunto"
                                      jsId="objDD.strAsunto"
                                      name="objDD.strAsunto"
                                      invalidMessage="Ingrese un Asunto"
                                      regExp=".{1,300}"
                                      required="true"
                                      style="width:500px"
                                      trim="true" />
                              </td>
                           </tr>
                     <script  type="dojo/method" event="onBlur">
                     if (dijit.byId("objDD.iIdExpediente").attr("value") == "") {
                        dijit.byId("objDD.sSumilla").attr("value", this.attr("value"));
                     }
                     </script>
                           <tr id="trSumilla" style="display: none;">
                              <td>Sumilla</td>
                              <td class="label" colspan="2">
                                 <div dojoType="dijit.form.TextBox"
                                      id="objDD.sSumilla"
                                      jsId="objDD.sSumilla"
                                      name="objDD.sSumilla" style="width: 300px;"
                                      required="false" invalidMessage="Ingrese una Sumilla"
                                      regExp=".{1,1999}" />
                              </td>
                           </tr>
                           <tr>
                              <td>Fecha Documento</td>
                              <td class="label" colspan="2">
                                 <div dojoType="dijit.form.DateTextBox"
                                      id="objDD.strFechaDocumento"
                                      jsId="objDD.strFechaDocumento"
                                      name="objDD.strFechaDocumento"
                                      constraints="{datePattern:'dd/MM/yyyy'}"
                                      invalidMessage="Ingrese fecha de Documento dd/MM/yyyy"
                                      required="true"
                                      trim="true">
                                    <script type="dojo/method" event="postCreate">
                                       var fechadocumento_year = parseInt("<s:date name='fecha' format='yyyy' />", 10);
                                       var fechadocumento_month = parseInt("<s:date name='fecha' format='MM' />", 10);
                                       var fechadocumento_day = parseInt("<s:date name='fecha' format='dd' />", 10);

                                       console.debug("fechadocumento year [%d] month [%d] day [%d]", fechadocumento_year, fechadocumento_month, fechadocumento_day);

                                       if (!isNaN(fechadocumento_year) && !isNaN(fechadocumento_month) && !isNaN(fechadocumento_day)) {
                                       this.attr("value", new Date(fechadocumento_year, fechadocumento_month - 1, fechadocumento_day));
                                       }
                                    </script>
                                 </div>
                              </td>
                           </tr>
                           <tr>
                              <td>Documentos referenciados</td>
                              <td class="label" colspan="2">
                                 <!--    <div id="objDD.strReferencia" />-->
                             <!--      <s:select id="referenciaDoc" name="referenciaDoc" list="listReferencias" listKey="idReferencias" listValue="numeroDocumento" multiple="true" size="3"></s:select> -->
                              </td>
                           </tr>
                           <tr>
                              <td style="color:red;">Documento Confidencial 22</td>
                              <td class="label" colspan="2" style="color:red;">
                                 <select name="objDD.confidencial" dojoType="dijit.form.FilteringSelect">
                                 	<option value="N" selected style="color:black;">No Confidencial</option>
                                 	<option value="S">Confidencial</option>
                                 </select>
                              </td>
                           </tr>
                           <%--<tr>
                              <td>Autor</td>
                              <td class="label" colspan="2">
                                 <input dojoType="dijit.form.TextBox"
                                        id="objDD.autor"
                                        jsId="objDD.autor"
                                        name="objDD.autor"
                                        style="width:300px;"
                                        trim="true" />
                              </td>
                           </tr>--%>
                        </tbody>
                        <tbody id="tbNumeracionDocumento" style="display:none;">
                           <tr>
                              <td colspan="3"><hr /></td>
                           </tr>
                           <tr>
                              <td colspan="3" class="titulo">NUMERACION DEL DOCUMENTO DEL AREA</td>
                           </tr>
                           <tr id="trFirmante">
                              <td>Firmante</td>
                              <td class="label" colspan="2">
                                 <div id="fsFirmante"></div>
                              </td>
                           </tr>
                           <tr id="trNroDocumento">
                              <td>Nro. Documento</td>
                              <td class="label" colspan="2">
                                 <div id="divChkEnumerarDocumento" style="float:left;display: none">
                                    <input id="chkEnumerarDocumento"/>
                                    <label for="chkEnumerarDocumento">Enumerar</label>
                                 </div>
                                 <div id="divNroDocumento">
                                    <div dojoType="dijit.form.ValidationTextBox"
                                         id="objDD.strNroDocumento"
                                         jsId="objDD.strNroDocumento"
                                         name="objDD.strNroDocumento"
                                         invalidMessage="Ingrese un Nro de Documento"
                                         regExp=".{1,40}"
                                         required="false"
                                         trim="true"></div>
                                    <input dojoType="dijit.form.TextBox"
                                           id="objDD.tipoNumeracion"
                                           jsId="objDD.tipoNumeracion"
                                           name="objDD.tipoNumeracion"
                                           style="display:none;" />
                                 </div>
                              </td>
                           </tr>
                           <tr id="trDestinatario1">
                              <td><%--A --%></td>
                              <td class="label" colspan="2">
                                 <div id="fsDestinatario"></div>
                              </td>
                           </tr>
						   <tr id="trDestinatario2">
								<td><%-- Destinatarios --%></td>
								<td><div id="fsNroIdentificacion1" name="fsNroIdentificacion1" /></td>
							</tr>
                           <tr id="trDestinatario3">
                              <td></td>
                              <td class="label" colspan="2">
                                 <div id="destinatarios"></div>
                              </td>
                           </tr>
                           <tr id="trConCopia1">
                              <td><%-- Otros destinatarios Cc --%></td>
                              <td class="label" colspan="2">
                                 <div id="fsConCopia"></div>
                              </td>
                           </tr>
							<tr id="trConCopia2">
								<td><%--Con Copia A --%></td>
								<td><div id="fsNroIdentificacion2" name="fsNroIdentificacion2" /></td>
							</tr>
                           <tr id="trConCopia3">
                              <td></td>
                              <td class="label" colspan="2">
                                 <div id="copias"></div>
                              </td>
                           </tr>
                        </tbody>
                        <tbody id="tbPlantillaDocumento" style="display:none;">
                           <tr>
                              <td colspan="3"><hr /></td>
                           </tr>
                           <tr>
                              <td colspan="3" class="titulo">PLANTILLA DEL DOCUMENTO</td>
                           </tr>
                           <%--<s:if test="archivopendiente!=null">
                           <tbody id="tabla">
                              <s:iterator value="archivopendiente.valorcampoList" status=""  >
                                 <tr style="" >
                              <div id="descripcion"> <s:property value="idcampo.descripcion" /></div>
                              <td id="valor"  class="titulo" width="15%">
                                 <s:if test="idcampo.tipo==TX" >
                                    <input type='text' id="valor<s:property value="idcampo.idCampo" />" value="<s:property value="valor" />" />
                                 </s:if>
                                 <s:elseif test="idcampo.tipo==TA">
                                    <textarea id="valor<s:property value="idcampo.idCampo" />"  cols='50' rows='10' wrap='on'>
                                       <s:property value="valor" />
                                    </textarea>
                                 </s:elseif>
                              </td>
                              <td colspan="2"> </td>
                              </tr>
                           </s:iterator>
                           </tbody>
                        </s:if>--%>
                        </tbody>
                        <tbody id="tabla" style="display:none;"></tbody>
                        <tbody id="tbCliente" style="display:none;">
                           <tr>
                              <td colspan="3"><hr /></td>
                           </tr>
                           <tr>
                              <td colspan="3" class="titulo">CLIENTE</td>
                           </tr>
                           <tr>
                              <td>Tipo Doc Identidad</td>
                              <td class="label" colspan="2">
                                 <input type="radio" id="sTipoIdentificacionRUC" />RUC
                                 <input type="radio" id="sTipoIdentificacionDNI"/>DNI
                                 <input type="radio" id="sTipoIdentificacionOtros"/>Otro
                                 <input id="idtipoidentificacion" />
                                 <span id="tbNuevoCliente" style="display:none;margin-left:30px;">
                                    <button dojoType="dijit.form.Button"
                                            type="button"
                                            id="btnBusquedaCliente"
                                            jsId="btnBusquedaCliente"
                                            iconClass="siged16 sigedSearch16"
                                            onClick="showGrdBusquedaCliente"
                                            showLabel="true">Buscar Cliente</button>
                                    <button dojoType="dijit.form.Button"
                                            type="button"
                                            id="btnNuevoCliente"
                                            jsId="btnNuevoCliente"
                                            iconClass="siged16 sigedNew16"
                                            onClick="showRegistroClienteFinal"
                                            showLabel="true">Nuevo Cliente</button>
                                 </span>
                              </td>
                           </tr>
                           <tr>
                              <td width="20%">Cliente</td>
                              <td class="label" width="50%">
                                 <input dojoType="dijit.form.TextBox"
                                        id="idcliente"
                                        jsId="idcliente"
                                        name="idcliente"
                                        style="display:none;" />
                                 <div id="fsNroIdentificacion"></div>
                              </td>
                              <td class="label" width="30%">
                                 <div id="showExpedienteAbierto" style="color:blue;font-weight:bold;">&nbsp;</div>
                              </td>
                           </tr>


                           <tr>
							<td width="20%">Derivado A </td>
					         <td class="label" width="50%">
                                 <input dojoType="dijit.form.TextBox"
                                        id="idAreaDerivada1"
                                        jsId="idAreaDerivada1"
                                        name="idAreaDerivada1"
                                        style="display:none;" />
                                 <div id="fsAreaDerivada"></div>
                              </td>
					          <!--   <select dojoType="dijit.form.FilteringSelect"
					                id="idAreaDerivada"
					                name="idAreaDerivada"
					                value="<s:property value='idAreaDerivada' />"
					                idAttr="id"
					                labelAttr="label"
					                style="width:240px;background-color:white;"
					                required="false"
									hasDownArrow="false"
					                searchAttr="label"
					                store="new dojo.data.ItemFileReadStore({url: 'autocompletarAreaDestino.action'})"></select>
					          -->
					         </td>
						</tr>

                        </tbody>
                        <tbody id="tbDataRUC" style="display:none;">
                           <tr>
                              <td>Raz&oacute;n Social</td>
                              <td class="label" colspan="2">
                                 <div dojoType="dijit.form.TextBox"
                                      id="strRazonSocial"
                                      jsId="strRazonSocial"
                                      name="objDD.clienterazonsocial"
                                      readOnly="true"
                                      style="width:300px"
                                      trim="true" />
                              </td>
                           </tr>
                           <tr>
                              <td>Representante Legal</td>
                              <td class="label" colspan="2">
                                 <div dojoType="dijit.form.TextBox"
                                      id="strRepresentanteLegal"
                                      jsId="strRepresentanteLegal"
                                      name="objDD.clienterepresentantelegal"
                                      readOnly="true"
                                      style="width:300px"
                                      trim="true" />
                              </td>
                           </tr>
                        </tbody>
                        <tbody id="tbDataDNI" style="display:none;">
                           <tr>
                              <td>Nombres</td>
                              <td class="label" colspan="2">
                                 <div dojoType="dijit.form.TextBox"
                                      id="sNombres"
                                      jsId="sNombres"
                                      name="objDD.clientenombres"
                                      readOnly="true"
                                      style="width:300px"
                                      trim="true" />
                              </td>
                           </tr>
                           <tr>
                              <td>Apellido Paterno</td>
                              <td class="label" colspan="2">
                                 <div dojoType="dijit.form.TextBox"
                                      id="sApellidoPaterno"
                                      jsId="sApellidoPaterno"
                                      name="objDD.clienteapellidopaterno"
                                      readOnly="true"
                                      style="width:300px"
                                      trim="true" />
                              </td>
                           </tr>
                           <tr>
                              <td>Apellido Materno</td>
                              <td class="label" colspan="2">
                                 <div dojoType="dijit.form.TextBox"
                                      id="sApellidoMaterno"
                                      jsId="sApellidoMaterno"
                                      name="objDD.clienteapellidomaterno"
                                      readOnly="true"
                                      style="width:300px"
                                      trim="true" />
                              </td>
                           </tr>
                        </tbody>
                        <tbody id="tbDataCliente" style="display:none;">
                           <tr>
                              <td>Direcci&oacute;n</td>
                              <td class="label" colspan="2">
                                 <div dojoType="dijit.form.TextBox"
                                      id="sDireccion1"
                                      jsId="sDireccion1"
                                      name="objDD.clientedireccionprincipal"
                                      readOnly="true"
                                      style="width:300px"
                                      trim="true" />
                              </td>
                           </tr>
                           <tr>
                              <td>Ubigeo</td>
                              <td class="label" colspan="2">
		                                 Departamento
                                 <input dojoType="dijit.form.TextBox"
                                        id="sDepartamento1"
                                        jsId="sDepartamento1"
                                        name="sDepartamento1"
                                        readOnly="true"
                                        trim="true" />
		                                 Provincia
                                 <input dojoType="dijit.form.TextBox"
                                        id="sProvincia1"
                                        jsId="sProvincia1"
                                        name="sProvincia1"
                                        readOnly="true"
                                        trim="true" />
		                                 Distrito
                                 <input dojoType="dijit.form.TextBox"
                                        id="sDistrito1"
                                        jsId="sDistrito1"
                                        name="sDistrito1"
                                        readOnly="true"
                                        trim="true" />
                              </td>
                           </tr>
                           <tr>
                              <td>Direcci&oacute;n Procesal</td>
                              <td class="label" colspan="2">
                                 <input dojoType="dijit.form.TextBox"
                                        id="sDireccion2"
                                        jsId="sDireccion2"
                                        name="objDD.clientedireccionalternativa"
                                        readOnly="true"
                                        style="width:300px"
                                        trim="true" />
                              </td>
                           </tr>
                           <tr>
                              <td>Ubigeo Procesal</td>
                              <td class="label" colspan="2">
		                                 Departamento
                                 <input dojoType="dijit.form.TextBox"
                                        id="sDepartamento2"
                                        jsId="sDepartamento2"
                                        name="sDepartamento2"
                                        readOnly="true"
                                        trim="true" />
		                                 Provincia
                                 <input dojoType="dijit.form.TextBox"
                                        id="sProvincia2"
                                        jsId="sProvincia2"
                                        name="sProvincia2"
                                        readOnly="true"
                                        trim="true" />
		                                 Distrito
                                 <input dojoType="dijit.form.TextBox"
                                        id="sDistrito2"
                                        jsId="sDistrito2"
                                        name="sDistrito2"
                                        readOnly="true"
                                        trim="true" />
                              </td>
                           </tr>
                           <tr>
                              <td>Tel&eacute;fono</td>
                              <td class="label" colspan="2">
                                 <div dojoType="dijit.form.TextBox"
                                      id="sTelefono"
                                      jsId="sTelefono"
                                      name="objDD.clientetelefono"
                                      readOnly="true"
                                      trim="true" />
                              </td>
                           </tr>
                           <tr>
                              <td>Correo Electr&oacute;nico</td>
                              <td class="label" colspan="2">
                                 <div dojoType="dijit.form.TextBox"
                                      id="sCorreoCliente"
                                      jsId="sCorreoCliente"
                                      name="objDD.clientecorreo"
                                      readOnly="true"
                                      style="width:300px"
                                      trim="true" />
                              </td>
                           </tr>
                           <tr>
                              <td colspan="3">&nbsp;</td>
                           </tr>
                        </tbody>
                        <%-- <tbody id="tbParaAprobar" style="display:none;">
                           <tr>
                              <td>Para Aprobar</td>
                              <td colspan="2">
                                 <input dojoType="dijit.form.CheckBox"
                                        id="chkParaAprobar"
                                        jsId="chkParaAprobar"
                                        name="objDD.paraAprobar"
                                        value="S" />
                              </td>
                           </tr>
                        </tbody>--%>
                        <tbody id="tbObservacion" style="display:none;">
                           <tr>
                              <td colspan="3"><hr /></td>
                           </tr>
                           <tr>
                              <td>Observaciones del Documento</td>
                              <td class="label" colspan="2">
                                 <div dojoType="dijit.form.Textarea"
                                      id="objDocumento.observacion"
                                      jsId="objDocumento.observacion"
                                      name="objDD.strObservacion"
                                      style="width:400px;" />
                              </td>
                           </tr>
                        </tbody>
                        <tbody id="tbConcesionario" style="display:none;">
                           <tr>
                              <td colspan="3"><hr /></td>
                           </tr>
                           <tr>
                              <td colspan="3" class="titulo">CONCESIONARIO</td>
                           </tr>
                           <tr>
                              <td>RUC</td>
                              <td class="label" colspan="2">
                                 <div id="fsRUC" name="fsRUC" />
                              </td>
                           </tr>
                        </tbody>
                        <tbody id="tbDataConcesionario" style="display:none;">
                           <tr>
                              <td>Raz&oacute;n Social</td>
                              <td class="label" colspan="2">
                                 <input type="text"
                                        dojoType="dijit.form.TextBox"
                                        id="objDocumento.expediente.concesionario.idConcesionario"
                                        jsId="objDocumento.expediente.concesionario.idConcesionario"
                                        name="objDocumento.expediente.concesionario.idConcesionario"
                                        style="display:none;" />
                                 <div dojoType="dijit.form.TextBox"
                                      id="sRazonSocialCo"
                                      jsId="sRazonSocialCo"
                                      name="sRazonSocialCo"
                                      readOnly="true"
                                      style="width:300px;" />
                              </td>
                           </tr>
                           <tr>
                              <td>Direcci&oacute;n</td>
                              <td class="label" colspan="2">
                                 <div dojoType="dijit.form.TextBox"
                                      id="sDireccionCo"
                                      jsId="sDireccionCo"
                                      name="sDireccionCo"
                                      readOnly="true"
                                      style="width:300px;" />
                              </td>
                           </tr>
                           <tr>
                              <td>Correo Electr&oacute;nico</td>
                              <td class="label" colspan="2">
                                 <div dojoType="dijit.form.TextBox"
                                      id="sCorreoCo"
                                      jsId="sCorreoCo"
                                      name="sCorreoCo"
                                      readOnly="true"
                                      style="width:300px;" />
                              </td>
                           </tr>
                        </tbody>
                     </table>
                  </div>
               </td>
            </tr>
            <tr>
               <td align="left">
                  <div class="margen5PX">
                     <button dojoType="dijit.form.Button"
                             type="button"
                             id="btnRegistrarDocumentoBottom"
                             jsId="btnRegistrarDocumentoBottom"
                             iconClass="siged16 sigedSave16"
                             onClick="submitForm"
                             showLabel="true">Registrar Documento</button>
                  </div>
               </td>
            </tr>
         </table>
      </form>
      <script type="text/javascript" src="js/siged/busquedaExpedienteMP.js"></script>
      <%@ include file="/pages/tramite/busquedaExpediente.jsp" %>
      <%@ include file="/examples/derivar-dialogs.jsp" %>
      <script type="text/javascript" src="js/siged/registroClienteMP.js"></script>
      <%@ include file="../tramite/registroCliente.jsp" %>
      <script type="text/javascript" src="js/siged/busquedaExpedienteAbiertoMP.js"></script>
      <%@ include file="busquedaExpedienteAbierto.jsp" %>
   </body>
</html>
