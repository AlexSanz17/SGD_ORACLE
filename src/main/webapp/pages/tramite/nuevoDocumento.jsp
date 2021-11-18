<?xml version="1.0" encoding="UTF-8" ?>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es">
   <head>
      <title>Nuevo Documento</title>
      <meta http-equiv="cache-control" content="no-cache">
      <style type="text/css">
         @import "css/grid.css";
      </style>
      <script type="text/javascript">
         var ROL_USUARIOLOGEADO_ID  = "<s:property value='#session._USUARIO.rol.idrol' />";

         console.debug("(nuevoDocumento.jsp) ROL_USUARIOLOGEADO_ID [%s]", ROL_USUARIOLOGEADO_ID);
      </script>
      <script type="text/javascript" src="js/siged/busquedaExpedienteMP.js"></script>
      <script type="text/javascript" src="js/busquedaExpediente.js"></script>
      <script type="text/javascript" src="js/siged/registroClienteMP.js"></script>
      <script type="text/javascript" src="js/siged/busquedaExpedienteAbiertoMP.js"></script>
      <script type="text/javascript" src="js/siged/nuevoDocumentoMP.js"></script>

      <script type='text/javascript' src='./dwr/engine.js'></script>
      <script type='text/javascript' src='./dwr/util.js'></script>
      <script type='text/javascript' src='./dwr/interface/toolDwr.js'></script>
      <script type='text/javascript' src='./dwr/interface/Motivo.js'></script>
      <script type='text/javascript' src='js/submotivo.js'></script>
      <script type='text/javascript' src='./dwr/interface/Suministro.js'></script>
      <script type='text/javascript' src='js/suministro.js'></script>

      <script type="text/javascript">
         function addSuministroCorregido() {
            var data = document.getElementById("ssuministro").value;
            var suministros=document.getElementsByName("storsuministro");
            for(var i=0;i<suministros.length;i++){
               if(data==suministros[i].value){
                  alert("Ingrese un suministro que no se encuentre en la lista de ingresados.");
                  return;
               }
            }

            var patternSuministro = /^[-_0-9A-Za-z]{1,9}$/;

            if (data.search(patternSuministro) == -1) {
               alert("Ingrese un suministro válido");

               return false;
            }

            if (data != "" && data.length < 10) {
               addSuministro();
            } else {
               alert("Ingrese un suministro válido");
            }
         }

         function addSubMotivoDojo(){
            var submotivos=document.getElementsByName("storidsubmotivo");
            var seleccionado=dijit.byId("dwr_idsubmotivo").getValue();
            if(seleccionado!=""){
               for(var i=0;i<submotivos.length;i++){
                  if(submotivos[i].value==seleccionado){
                     alert("Seleccione un submotivo diferente a uno de los ya ingresados.");
                     return;
                  }
               }
               if(!dijit.byId("dwr_idsubmotivo").state && !dijit.byId("dwr_idmotivo").state && dijit.byId("dwr_idsubmotivo").getValue()!="" && dijit.byId("dwr_idmotivo").getValue()!=""){
                  addSubMotivo();
                  clearSubMotivoDojo();
               }
            }
         }

         function clearSubMotivoDojo(){
            dijit.byId("dwr_idsubmotivo").setDisplayedValue("");
            dijit.byId("dwr_idmotivo").setDisplayedValue("");
            dijit.byId("dwr_idsubmotivo").setValue("");
            dijit.byId("dwr_idmotivo").setValue("");

            clearSubMotivo();
         }

         function limpiarSubmot(uhmm) {
            if (uhmm == undefined || uhmm == '')
               return;

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

            var storeMotivos=motivo.store;

            storeMotivos.fetchItemByIdentity({
               identity: s,

               onItem: function(item) {
                  storeSubMotivos = new dojo.data.ItemFileReadStore({url:"listaSubMotivos.action?idDependencia1="+storeMotivos.getValue(item,"id")});
                  var subMotivo = dijit.byId("dwr_idsubmotivo");
                  subMotivo.store=storeSubMotivos;
               }
            });

            //console.log(document.getElementById("idmotivo"));
            //console.log(document.getElementById("nmotivo"));
         }

         function setearNombreMotivo() {
            var subMotivo = dijit.byId("dwr_idsubmotivo");
            document.getElementById("nsubmotivo").value=subMotivo.getDisplayedValue();
            document.getElementById("idsubmotivo").value=subMotivo.getValue();
            //console.log(document.getElementById("idsubmotivo"));
            //console.log(document.getElementById("nsubmotivo"));
         }


         var agregar=function(tipo, compartido){
            var nombre=document.getElementById("id"+tipo).value;
            var id=dijit.byId("id"+tipo).getValue();
				
				
            if(nombre!=null && nombre!="" && id!=null && id!=""){
               var existe=false;
					
               var conCopia=document.getElementsByName("objDocumento.con"+tipo);

               for (var i = 0; i < conCopia.length; i++) {
                  if(conCopia[i].value==id){
                     existe=true;
                  }
               }
						
               if(compartido!=""){
						
                  var conCopia2=document.getElementsByName("objDocumento.con"+compartido);
                  for (var i = 0; i < conCopia2.length; i++) {
                     if(conCopia2[i].value==id){
                        existe=true;
                     }
                  }
               }
				
               if(!existe){
						
                  var copia="<span class=\"copia\">";
                  copia+="<span>"+nombre+"</span>";
                  copia+="<input type=\"hidden\" name=\"objDocumento.con"+tipo+"\" value=\""+id+"\" />";
                  copia+="<a href=\"#\" onclick=\"removeItemTipo(this)\" class=\"quitar\"><img src=\"images/eliminar.gif\" alt=\"X\" /></a></span>";
                  document.getElementById(tipo).innerHTML+=copia;
               }
            }
         };

         function removeItemTipo(item){
            var itemFather=item.parentNode;
            itemFather.parentNode.removeChild(itemFather);
         }
		 
         var agregarDestinatario = function(){
            agregar("destinatarios","copias");
         };

         var agregarCopia = function(){
            agregar("copias","destinatarios");
         };


         dojo.addOnLoad(function() {

            new dijit.form.FilteringSelect({
               searchAttr		: "label",
               labelAttr		: "label",
               idAttr			: "ids",
               required		: false,
               hasDownArrow	: true,
               highlightMatch	: "all",
               onChange		: agregarDestinatario
            },"iddestinatarios");
					
            new dijit.form.FilteringSelect({
               //store			: strDerivarCC,
               searchAttr		: "label",
               labelAttr		: "label",
               idAttr			: "ids",
               required		: false,
               hasDownArrow	: true,
               highlightMatch	: "all",
               onChange		: agregarCopia
            },"idcopias");

         });

	     
         //var strDerivarCC = new dojo.data.ItemFileReadStore({url: "derivarCC.action"});
	 	
	
         
      </script>
   </head>

   <body class="soria">
      <form dojoType="dijit.form.Form" id="frmNuevoDocumento"
            jsId="frmNuevoDocumento" action="doRegistrarDocumento.action"
            method="post">
         <table width="100%">
            <%--
                     <tr>
                        <td colspan="2">
                           <div class="margen5PX">
                              Bienvenido <span style="color:blue;font-weight:bold;"><s:property value="#session.nombres" /></span><br />
                              <span style="color:#0099FF;font-weight:bold;"><a href="doLogout.action" target="_parent">Cerrar Sesi&oacute;n</a></span>
                           </div>
                        </td>
                     </tr>
                     <tr>
                        <td colspan="2">
                           <div class="tituloPagina margen5PX">Nuevo Documento</div>
                        </td>
                     </tr>
            --%>
            <tr>
               <td width="95%" align="left">
                  <div class="margen5PX">
                     <button dojoType="dijit.form.Button" type="button"
                             id="btnRegistrarDocumentoTop" jsId="btnRegistrarDocumentoTop"
                             iconClass="siged16 sigedSave16" onClick="prepareToSubmitForm"
                             showLabel="true">Registrar Documento</button>
                  </div>
               </td>
               <td width="5%" align="right"><%--<div class="margen5PX">
                              <button dojoType="dijit.form.Button"
                                      type="button"
                                      iconClass="siged16 sigedBack16"
                                      onClick="history.back();">Regresar</button>
                           </div>--%></td>
            </tr>
            <tr>
               <td colspan="2">
                  <div id="showErrorDocumento" class="margen5PX"
                       style="color: red; font-weight: bold;">&nbsp;</div>
               </td>
            </tr>
            <tr>
               <td colspan="2">
                  <div class="marcoForm margen5PX">
                     <table width="100%">
                        <tr>
                           <td width="12%" class="titulo">Datos del Expediente</td>
                           <td width="88%" colspan="2"></td>
                        </tr>
                        <tr>
                           <td>&nbsp;</td>
                           <td class="label" colspan="2"><s:if
                                 test="#session._RECURSO.ExpHisReg">
                                 <input dojoType="dijit.form.CheckBox" id="chkExpedienteHistorico"
                                        jsId="chkExpedienteHistorico"
                                        onChange="activarExpedienteHistorico" />
                                 <label for="chkExpedienteHistorico"> Deseo registrar un
					Expediente Hist&oacute;rico </label>
                              </s:if></td>
                        </tr>
                        <tr>
                           <td>Nro Expediente</td>
                           <td class="label" colspan="2"><input
                                 dojoType="dijit.form.TextBox" id="sNroExpediente"
                                 jsId="sNroExpediente" name="sNroExpediente" readOnly="true"
                                 trim="true" /> <input dojoType="dijit.form.TextBox"
                                 id="objDocumento.expediente.nroexpediente"
                                 jsId="objDocumento.expediente.nroexpediente"
                                 name="objDocumento.expediente.nroexpediente" style="display: none;" />
                              <input dojoType="dijit.form.TextBox"
                                     id="objDocumento.expediente.idexpediente"
                                     jsId="objDocumento.expediente.idexpediente"
                                     name="objDocumento.expediente.idexpediente" style="display: none;" />
                              <button dojoType="dijit.form.Button" id="btnBusquedaExpediente"
                                      jsId="btnBusquedaExpediente" iconClass="siged16 sigedSearch16"
                                      onClick="showBusquedaExpediente" showLabel="false">B&uacute;squeda de Expediente</button>
                              <button dojoType="dijit.form.Button"
                                      id="btnValidarExpedienteHistorico"
                                      jsId="btnValidarExpedienteHistorico"
                                      iconClass="siged16 sigedEraser16"
                                      onClick="validarExpedienteHistorico" showLabel="false"
                                      style="display: none;">Validar Expediente Hist&oacute;rico</button>
                              <span id="showValidacionExpedienteHistorico"
                                    style="color: blue; font-weight: bold;">&nbsp;</span></td>
                        </tr>
                        <tr>
                           <td>Proceso</td>
                           <td class="label" colspan="2">
                              <div id="fsProceso" name="fsProceso"></div>
                              <span id="ambienteProceso" style="font-weight:bold;">&nbsp;</span>
                           </td>
                        </tr>
                        <tbody id="tbProceso" style="display: none;">
                           <tr>
                              <td>Area Destino</td>
                              <td class="label" colspan="2">
                                 <div dojoType="dijit.form.TextBox" id="sUnidad" jsId="sUnidad"
                                      name="sUnidad" readOnly="true" style="width: 300px" trim="true" />
                              </td>
                           </tr>
                           <tr>
                              <td>Destinatario</td>
                              <td class="label" colspan="2">
                                 <div dojoType="dijit.form.TextBox" id="sResponsable"
                                      jsId="sResponsable" name="sResponsable" readOnly="true"
                                      style="width: 300px" trim="true" />
                              </td>
                           </tr>
                        </tbody>
                        <tbody id="tbDestinatario" style="display:none;">
                           <tr>
                              <td>Destinatario</td>
                              <td class="label" colspan="2">
                                 <div id="fsDestinatario"></div>
                              </td>
                           </tr>
                        </tbody>
                        <tbody id="tbDocumento" style="display: none;">
                           <tr>
                              <td colspan="3">
                                 <hr>
                              </td>
                           </tr>
                           <tr>
                              <td colspan="3" class="titulo">DOCUMENTO</td>
                           </tr>
                           <tr>
                              <td>Tipo Documento</td>
                              <td class="label" colspan="2">
                                 <div id="fsTipoDocumento" name="fsTipoDocumento" />
                              </td>
                           </tr>
                           <tr>
                              <td>Nro. Documento</td>
                              <td class="label" colspan="2">
                                 <div dojoType="dijit.form.ValidationTextBox"
                                      id="objDocumento.numeroDocumento"
                                      jsId="objDocumento.numeroDocumento"
                                      name="objDocumento.numeroDocumento"
                                      invalidMessage="Ingrese un Nro de Documento" regExp=".{1,40}"
                                      required="true" trim="true" />
                              </td>
                           </tr>
                           <tr>
                              <td>Nro Folios</td>
                              <td class="label" colspan="2">
                                 <div dojoType="dijit.form.ValidationTextBox"
                                      id="objDocumento.numeroFolios" jsId="objDocumento.numeroFolios"
                                      name="objDocumento.numeroFolios"
                                      invalidMessage="Ingrese Nro de Folios" regExp="\d{1,6}"
                                      required="true" trim="true" />
                              </td>
                           </tr>
                           <tr>
                              <td>Asunto</td>
                              <td class="label" colspan="2">
                                 <div dojoType="dijit.form.ValidationTextBox"
                                      id="objDocumento.expediente.asunto"
                                      jsId="objDocumento.expediente.asunto"
                                      name="objDocumento.expediente.asunto"
                                      invalidMessage="Ingrese un Asunto" regExp=".{1,300}"
                                 required="true" style="width: 300px" trim="true"><script
                                       type="dojo/method" event="onBlur">
                     if (dijit.byId("objDocumento.expediente.idexpediente").attr("value") == "") {
                        dijit.byId("objDocumento.expediente.sumilla").attr("value", this.attr("value"));
                     }
                                    </script></div>
                              </td>
                           </tr>
                           <tr id="trSumilla" style="display: none;">
                              <td>Sumilla</td>
                              <td class="label" colspan="2">
                                 <div dojoType="dijit.form.TextBox"
                                      id="objDocumento.expediente.sumilla"
                                      jsId="objDocumento.expediente.sumilla"
                                      name="objDocumento.expediente.sumilla" style="width: 300px;"
                                      required="false" invalidMessage="Ingrese una Sumilla"
                                      regExp=".{1,1999}" />
                              </td>
                           </tr>
                           <tr>
                              <td>Fecha Documento</td>
                              <td class="label" colspan="2">
                                 <div dojoType="dijit.form.DateTextBox" id="sFechaDocumento"
                                      jsId="sFechaDocumento" name="sFechaDocumento"
                                      constraints="{datePattern:'dd/MM/yyyy'}"
                                      invalidMessage="Ingrese fecha de Documento dd/MM/yyyy"
                                 required="true" trim="true"><script type="dojo/method"
                                                                     event="postCreate">
                     var fechadocumento_year = parseInt("<s:date name='fecha' format='yyyy' />", 10);
                     var fechadocumento_month = parseInt("<s:date name='fecha' format='MM' />", 10);
                     var fechadocumento_day = parseInt("<s:date name='fecha' format='dd' />", 10);

                     ////console.debug("fechadocumento year [%d] month [%d] day [%d]", fechadocumento_year, fechadocumento_month, fechadocumento_day);

                     if (!isNaN(fechadocumento_year) && !isNaN(fechadocumento_month) && !isNaN(fechadocumento_day)) {
                        this.attr("value", new Date(fechadocumento_year, fechadocumento_month - 1, fechadocumento_day));
                     }
                                    </script></div>
                                 <!--prueba--> <%-- <input id="oracle" /> --%></td>
                           </tr>
                           <tr>
                              <td>Destinatarios (opcional):</td>
                              <td class="label" colspan="2">
                                 <input id="iddestinatarios" />	
                                 <div id="destinatarios"></div>
                              </td>
                           </tr>
                           <tr>
                              <td>Con Copia A(opcional)</td>
                              <td class="label" colspan="2">
                                 <input id="idcopias" />
                                 <div id="copias"></div>	
                              </td>
                           </tr>
                        </tbody>
                        <tbody id="tbCliente" style="display: none;">
                           <tr>
                              <td colspan="3">
                                 <hr>
                              </td>
                           </tr>
                           <tr>
                              <td colspan="3" class="titulo">CLIENTE</td>
                           </tr>
                           <tr>
                              <td>Tipo Doc Identidad</td>
                              <td class="label" colspan="2"><input
                                    dojoType="dijit.form.RadioButton" type="radio"
                                    id="sTipoIdentificacionRUC" name="sTipoIdentificacion"
                                    readonly="readonly" 
                                    value="RUC" />RUC <input dojoType="dijit.form.RadioButton"
                                    type="radio" id="sTipoIdentificacionDNI"
                                    name="sTipoIdentificacion"
                                    readonly="readonly" 
                                    value="DNI" />DNI <input dojoType="dijit.form.RadioButton"
                                    type="radio" id="sTipoIdentificacionOtros"
                                    name="sTipoIdentificacion"
                                    readonly="readonly" 
                                    value="Otro" />Otro <input dojoType="dijit.form.TextBox"
                                    type="text" id="objDocumento.expediente.cliente.idCliente"
                                    jsId="objDocumento.expediente.cliente.idCliente"
                                    name="objDocumento.expediente.cliente.idCliente"
                                    style="display: none;" /> <span id="tbNuevoCliente"
                                    style="display: none; margin-left: 30px;">
                                    <button dojoType="dijit.form.Button"
                                            type="button"
                                            id="btnBusquedaCliente"
                                            jsId="btnBusquedaCliente"
                                            iconClass="siged16 sigedSearch16"
                                            onClick="showGrdBusquedaCliente"
                                            showLabel="true">Buscar Cliente</button>
                                    <button dojoType="dijit.form.Button" type="button"
                                            id="btnNuevoCliente" jsId="btnNuevoCliente"
                                            iconClass="siged16 sigedNew16" onClick="showRegistroCliente"
                                            showLabel="true">Nuevo Cliente</button>
                                 </span></td>
                           </tr>
                           <tr>
                              <td width="12%">Nro Doc Identidad</td>
                              <td class="label" width="20%">
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
                                 <div dojoType="dijit.form.TextBox"
                                      id="objDocumento.expediente.cliente.razonSocial"
                                      jsId="objDocumento.expediente.cliente.razonSocial"
                                      name="objDocumento.expediente.cliente.razonSocial"
                                      style="width: 300px" trim="true" />
                              </td>
                           </tr>
                           <tr>
                              <td>Representante Legal</td>
                              <td class="label" colspan="2">
                                 <div dojoType="dijit.form.TextBox"
                                      id="objDocumento.expediente.cliente.representanteLegal"
                                      jsId="objDocumento.expediente.cliente.representanteLegal"
                                      name="objDocumento.expediente.cliente.representanteLegal"
                                      style="width: 300px" trim="true" />
                              </td>
                           </tr>
                        </tbody>
                        <tbody id="tbDataDNI" style="display: none;">
                           <tr>
                              <td>Nombres</td>
                              <td class="label" colspan="2">
                                 <div dojoType="dijit.form.TextBox"
                                      id="objDocumento.expediente.cliente.nombres"
                                      jsId="objDocumento.expediente.cliente.nombres"
                                      name="objDocumento.expediente.cliente.nombres"
                                      style="width: 300px" trim="true" />
                              </td>
                           </tr>
                           <tr>
                              <td>Apellido Paterno</td>
                              <td class="label" colspan="2">
                                 <div dojoType="dijit.form.TextBox"
                                      id="objDocumento.expediente.cliente.apellidoPaterno"
                                      jsId="objDocumento.expediente.cliente.apellidoPaterno"
                                      name="objDocumento.expediente.cliente.apellidoPaterno"
                                      style="width: 300px" trim="true" />
                              </td>
                           </tr>
                           <tr>
                              <td>Apellido Materno</td>
                              <td class="label" colspan="2">
                                 <div dojoType="dijit.form.TextBox"
                                      id="objDocumento.expediente.cliente.apellidoMaterno"
                                      jsId="objDocumento.expediente.cliente.apellidoMaterno"
                                      name="objDocumento.expediente.cliente.apellidoMaterno"
                                      style="width: 300px" trim="true" />
                              </td>
                           </tr>
                        </tbody>
                        <tbody id="tbDataCliente" style="display: none;">
                           <tr>
                              <td>Direcci&oacute;n</td>
                              <td class="label" colspan="2">
                                 <div dojoType="dijit.form.TextBox"
                                      id="objDocumento.expediente.cliente.direccionPrincipal"
                                      jsId="objDocumento.expediente.cliente.direccionPrincipal"
                                      name="objDocumento.expediente.cliente.direccionPrincipal"
                                      style="width: 300px" trim="true" />
                              </td>
                           </tr>
                           <tr>
                              <td>Ubigeo</td>
                              <td class="label" colspan="2">Departamento <%--<input dojoType="dijit.form.TextBox"
                                                       id="sDepartamento1"
                                                       jsId="sDepartamento1"
                                                       name="sDepartamento1"
                                                       readOnly="true"
                                                       trim="true" />--%>
                                 <div id="fsDepartamento1"></div>
					Provincia <%--<input dojoType="dijit.form.TextBox"
                                                          id="sProvincia1"
                                                          jsId="sProvincia1"
                                                          name="sProvincia1"
                                                          readOnly="true"
                                                          trim="true" />--%>
                                 <div id="fsProvincia1"></div>
					Distrito <%--<input dojoType="dijit.form.TextBox"
                                                          id="sDistrito1"
                                                          jsId="sDistrito1"
                                                          name="sDistrito1"
                                                          readOnly="true"
                                                          trim="true" />--%>
                                 <div id="fsDistrito1"></div>
                              </td>
                           </tr>
                           <tr>
                              <td>Direcci&oacute;n Procesal</td>
                              <td class="label" colspan="2">
                                 <div dojoType="dijit.form.TextBox"
                                      id="objDocumento.expediente.cliente.direccionAlternativa"
                                      jsId="objDocumento.expediente.cliente.direccionAlternativa"
                                      name="objDocumento.expediente.cliente.direccionAlternativa"
                                      style="width: 300px" trim="true" />
                              </td>
                           </tr>
                           <tr>
                              <td>Ubigeo Procesal</td>
                              <td class="label" colspan="2">Departamento <%--<input dojoType="dijit.form.TextBox"
                                                       id="sDepartamento2"
                                                       jsId="sDepartamento2"
                                                       name="sDepartamento2"
                                                       readOnly="true"
                                                       trim="true" />--%>
                                 <div id="fsDepartamento2"></div>
					Provincia <%--<input dojoType="dijit.form.TextBox"
                                                          id="sProvincia2"
                                                          jsId="sProvincia2"
                                                          name="sProvincia2"
                                                          readOnly="true"
                                                          trim="true" />--%>
                                 <div id="fsProvincia2"></div>
					Distrito <%--<input dojoType="dijit.form.TextBox"
                                                          id="sDistrito2"
                                                          jsId="sDistrito2"
                                                          name="sDistrito2"
                                                          readOnly="true"
                                                          trim="true" />--%>
                                 <div id="fsDistrito2"></div>
                              </td>
                           </tr>
                           <tr>
                              <td>Tel&eacute;fono</td>
                              <td class="label" colspan="2">
                                 <div dojoType="dijit.form.TextBox"
                                      id="objDocumento.expediente.cliente.telefono"
                                      jsId="objDocumento.expediente.cliente.telefono"
                                      name="objDocumento.expediente.cliente.telefono"
                                      style="width: 300px" trim="true" />
                              </td>
                           </tr>
                           <tr>
                              <td>Correo Electr&oacute;nico</td>
                              <td class="label" colspan="2">
                                 <div dojoType="dijit.form.TextBox"
                                      id="objDocumento.expediente.cliente.correo"
                                      jsId="objDocumento.expediente.cliente.correo"
                                      name="objDocumento.expediente.cliente.correo" style="width: 300px"
                                      trim="true" />
                              </td>
                           </tr>
                        </tbody>
                        <tbody id="tbObservacion" style="display: none;">
                           <tr>
                              <td colspan="3">
                                 <hr>
                              </td>
                           </tr>
                           <tr>
                              <td>Observaciones</td>
                              <td class="label" colspan="2">
                                 <div dojoType="dijit.form.Textarea" id="objDocumento.observacion"
                                      jsId="objDocumento.observacion" name="objDocumento.observacion"
                                      style="width: 400px;" required="false"
                                      invalidMessage="Demasiados caracteres para la observacion."
                                      regExp=".{0,3990}" />
                              </td>
                           </tr>
                        </tbody>
                        <tbody id="tbConcesionario" style="display: none;">
                           <tr>
                              <td colspan="3">
                                 <hr>
                              </td>
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
                        <tbody id="tbDataConcesionario" style="display: none;">
                           <tr>
                              <td>Raz&oacute;n Social</td>
                              <td class="label" colspan="2"><input type="text"
                                                                   dojoType="dijit.form.TextBox"
                                                                   id="objDocumento.expediente.concesionario.idConcesionario"
                                                                   jsId="objDocumento.expediente.concesionario.idConcesionario"
                                                                   name="objDocumento.expediente.concesionario.idConcesionario"
                                                                   style="display: none;" />
                                 <div dojoType="dijit.form.TextBox" id="sRazonSocialCo"
                                      jsId="sRazonSocialCo" name="sRazonSocialCo" readOnly="true"
                                      style="width: 390px;" />
                              </td>
                           </tr>
                           <tr>
                              <td>Direcci&oacute;n</td>
                              <td class="label" colspan="2">
                                 <div dojoType="dijit.form.TextBox" id="sDireccionCo"
                                      jsId="sDireccionCo" name="sDireccionCo" readOnly="true"
                                      style="width: 300px;" />
                              </td>
                           </tr>
                           <tr>
                              <td>Correo Electr&oacute;nico</td>
                              <td class="label" colspan="2">
                                 <div dojoType="dijit.form.TextBox" id="sCorreoCo" jsId="sCorreoCo"
                                      name="sCorreoCo" readOnly="true" style="width: 300px;" />
                              </td>
                           </tr>
                        </tbody>
                        <tbody id="tbDataStor" style="display: none;">
                           <tr>
                              <td colspan="3">
                                 <hr>
                              </td>
                           </tr>
                           <tr>
                              <td colspan="3" class="titulo">Datos Adicionales</td>
                           </tr>
                           <tr>
                              <td width="12%">Nro Suministro</td>
                              <td class="label" width="16%" align="left"><input type="text"
                                                                                dojoType="dijit.form.ValidationTextBox" id="ssuministro"
                                                                                name="ssuministro" regExp=".{1,9}" required="false" trim="true" />
                                 <br />
                                 <input class="button" type="button" value="Añadir"
                                        onclick="addSuministroCorregido()" /> <input class="button"
                                        type="button" value="Limpiar" onclick="clearSuministro()" /></td>
                              <td width="62%" align="left">
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
                                                style="display: none;" /></td>
                                          <td><input id="deletesum" type="button" value="Eliminar"
                                                     class="button" onclick="deleteSuministro(this.id)" /></td>
                                       </tr>
                                    </tbody>
                                 </table>
                              </td>
                           </tr>
                           <tr>
                              <td width="12%">Motivo</td>
                              <td class="label" width="16%" align="left"><s:hidden
                                    id="nmotivo" name="nmotivo" /> <s:hidden id="nsubmotivo"
                                    name="nsubmotivo" /> <s:hidden id="idmotivo" name="idmotivo" />
                                 <s:hidden id="idsubmotivo" name="idsubmotivo" /> Motivo <input
                                    dojoType="dijit.form.FilteringSelect" idAttr="id"
                                    labelAttr="label" searchAttr="label" name="dwr_idmotivo"
                                    id="dwr_idmotivo" size="85" required="false" style="width: 230px;"
                                    onchange="limpiarSubmot" /><br />
					SubMotivo <input dojoType="dijit.form.FilteringSelect" idAttr="id"
                                                  labelAttr="label" searchAttr="label" name="dwr_idsubmotivo"
                                                  id="dwr_idsubmotivo" size="85" style="width: 230px;"
                                                  required="false" onchange="setearNombreMotivo" /> <br />
                                 <input class="button" type="button" value="Añadir"
                                        onclick="addSubMotivoDojo()" /> <input class="button"
                                        type="button" value="Limpiar" onclick="clearSubMotivoDojo()" /></td>
                              <td width="62%" align="left">
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
                                                type="text" name="storidsubmotivo" size="70"
                                                id="storidsubmotivo" style="display: none;" /></td>
                                          <td><input id="deletesub" type="button" value="Eliminar"
                                                     class="button" onclick="deleteSubMotivo(this.id)" /></td>
                                       </tr>
                                    </tbody>
                                 </table>
                              </td>
                           </tr>
                           <tr>
                              <td>Monto de Reclamo (S/.)</td>
                              <td class="label" colspan="2"><input
                                    dojoType="dijit.form.ValidationTextBox" type="text"
                                    id="sMontoReclamo" name="sMontoReclamo" maxlength="15"
                                    invalidMessage="Ingrese un Monto Valido - Utilize punto como separador decimal - Maximo 2 Decimales"
                                    regExp="[0-9]{0,9}\.{0,1}[0-9]{1,2}" required="false"
                                    style="width: 100px;" /></td>
                           </tr>
                        </tbody>
                     </table>
                  </div>
               </td>
            </tr>
            <tr>
               <td colspan="2" align="left">
                  <div class="margen5PX">
                     <button dojoType="dijit.form.Button" type="button"
                             id="btnRegistrarDocumentoBottom" jsId="btnRegistrarDocumentoBottom"
                             iconClass="siged16 sigedSave16" onClick="prepareToSubmitForm"
                             showLabel="true">Registrar Documento</button>

                  </div>
               </td>
            </tr>
         </table>
      </form>

      <%@ include file="busquedaExpediente.jsp"%>
      <%@ include file="registroCliente.jsp"%>
      <%@ include file="busquedaExpedienteAbierto.jsp"%>
      <%--@ include file="../util/progressBar.jsp" --%>
   </body>
</html>