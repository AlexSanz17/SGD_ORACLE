<!-- REN detalle del documento que va debajo de la grilla, se carga cuando se presiona una fila de la grilla ------------------->

<?xml version="1.0" encoding="UTF-8"?>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es">
    <head>
        <title>Detalle del Documento</title>

        <META HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE" >
        <s:if test="deMail||enVentana">
            <style type="text/css">
                @import "css/estilo.css";
            </style>
            <link rel="stylesheet" type="text/css" href="css/estilo.css" />
            <link type="text/css" rel="stylesheet" href="css/busquedaExpediente.css" />
            <style type="text/css">
                @import "css/siged.css";
                @import "css/sigedIconos.css";
                @import "css/transferSelect.css";
                @import "js/dojo/dojo/resources/dojo.css";
                @import "js/dojo/dijit/themes/nihilo/nihilo.css";
                @import "js/dojo/dojox/grid/resources/nihiloGrid.css";
                @import "js/dojo/dijit/themes/soria/soria.css";
                @import "js/dojo/dojox/grid/resources/soriaGrid.css";
                @import "js/dojo/dijit/themes/tundra/tundra.css";
                @import "js/dojo/dojox/grid/resources/tundraGrid.css";
                @import "js/dojo/dojox/layout/resources/ExpandoPane.css";
                @import "js/dojo/dojox/layout/resources/ToggleSplitter.css";
            </style>
        </s:if>
        <s:if test="deMail||(#session._USUARIO.rol != null && #session._USUARIO.rol.nombre != 'Siged - Mesa de Partes')||enVentana">
            <link rel="stylesheet" href="js/dojo/dijit/themes/soria/soria.css" />
            <script type="text/javascript">
                var djConfig = {
                    isDebug: false,
                    parseOnLoad: true
                };

                function verArchivo(url) {
                    var service = new dojo.rpc.JsonService("SMDAction.action");
                    var defered = service.verArchivoAlfresco(url);

                    defered.addCallback(function(url) {
                        console.log("URL Alfresco [" + url + "]");
                        alert(url);
                        window.open(url);
                    });

                }
            </script>
            <script type="text/javascript" src="js/dojo/dojo/dojo.js"></script>
        </s:if>
        <script type="text/javascript">

            function openDialog(varPagina,varTitulo){
                //alert(varPagina);
                var popupExternal=dijit.byId('external');
                popupExternal.attr("title",varTitulo);
                popupExternal.attr("href",varPagina);
                popupExternal.show();
            }

            function Abrir_ventana (pagina) {
                var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=no, width=440, height=350, top=50, left=200";
                window.open(pagina,"",opciones);
            }

            function abrirVentanaDerivar(opcion) {
                var remitente="<s:property value='objDD.iIdRemitente' />";

                var paraAprobar=<s:property value='paraAprobar' />;
                var opciones = "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes, width=800, height=600, top=20, left=70";
                var pagina = "goDerivarUSER.action?arrIdDoc=" + <s:property value='objDD.iIdDocumento' /> + "&sTipoDerivacion=normal&sOpcion="+opcion+"&paraAprobar="+paraAprobar;
                if(paraAprobar){

                    pagina+="&objDD.iIdRemitente="+remitente;
                }


                window.open(pagina, "", opciones);
            }

            function abrirVentanaDerivarApoyo(){
            	var remitente="<s:property value='objDD.iIdRemitente' />";
                var opciones = "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=800, height=600, top=20, left=70";
                var pagina = "goDerivarApoyo.action?arrIdDoc=" + <s:property value='objDD.iIdDocumento' /> + "";
                pagina+="&objDD.iIdRemitente="+remitente;
                window.open(pagina, "", opciones);
            }
            /*
            function abrirVentanaDelegarApoyo(){
            	var remitente="<s:property value='objDD.iIdRemitente' />";
                var opciones = "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes, width=800, height=600, top=20, left=70";
                var pagina = "goDelegarApoyo.action?arrIdDoc=" + <s:property value='objDD.iIdDocumento' />;
                pagina+="&objDD.iIdRemitente="+remitente;
                window.open(pagina, "", opciones);
            }
            */
            function abrirVentanaCopiarApoyo(){
            	var remitente="<s:property value='objDD.iIdRemitente' />";
                var opciones = "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes, width=810, height=600, top=20, left=70";
                var pagina = "goCopiarApoyo.action?arrIdDoc=" + <s:property value='objDD.iIdDocumento' />;
                window.open(pagina, "", opciones);
            }

            function loadVirtualDocumentSearch() {
                var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=800, height=450, top=20, left=70";
                var pagina = "goOpenDocumentSearch.action?iIdDoc=" + <s:property value='objDD.iIdDocumento' />;
                window.open(pagina, "", opciones);
            }

            function abrirVentanaSeguimiento() {
                var opciones = "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=no, width=650, height=500, top=50, left=200";
                var pagina ="goViewSeguimiento.action?iIdDocumento=" + <s:property value='objDD.iIdDocumento' />;
                window.open("goViewSeguimiento.action?iIdDocumento=" + <s:property value='objDD.iIdDocumento' />, "", opciones);
            }

            function abrirVentanaRechazar() {

                if("<s:property value='puedeRechazar'/>"=="S" ) {

                    var opciones = "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes, width=800, height=600, top=20, left=70";
                    var pagina = "goDerivarUSER.action?arrIdDoc=" + <s:property value='objDD.iIdDocumento' /> + "&sTipoDerivacion=normal&sOpcion=rechazar";

                    //openDialog(pagina,"Rechazar Documento");
                    window.open(pagina, "", opciones);

                }else {
                    alert("No puede Rechazar este Documento por que no ha sido Previamente Derivado ") ;
                }
            }

            function Abrir_ventanaId() {
                var opciones = "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes, width=600, height=550, top=50,left=200";
                var pagina = "doPlantilla_verNuevo.action?idDocumento=" + <s:property value='objDD.iIdDocumento' /> ;
                //openDialog(pagina,"Plantilla");
                window.open(pagina,"",opciones);
            }

            function abrirVentanaArchivar(tipoArchivar) {
                var opciones = "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes, width=650, height=440, top=20, left=70";
                var pagina = "Archivar_inicio.action?idDocumento=" + <s:property value='objDD.iIdDocumento' />+ "&tipoArchivar="+tipoArchivar;
                //openDialog(pagina,"Archivar");
                window.open(pagina, "", opciones);
            }


            function abrirVentanaAnularDocumento(tipoArchivar) {
                var opciones = "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes, width=650, height=440, top=20, left=70";
                var pagina = "AnularPrevioDocumento_inicioAnular.action?idDocumentoAnular=" + <s:property value='objDD.iIdDocumento' />+ "&tipoArchivar="+tipoArchivar;
                //openDialog(pagina,"Archivar");
                window.open(pagina, "", opciones);
            }

            function abrirConfirmacionAprobar(i,exp,idetapa){
                if (confirm("Desea aprobar el expediente "+exp+" con idetapa "+idetapa)){
                    location.href="doAprobarStor.action?iIdDoc="+i;
                }
            }
            function abrirVentanaAprobar(idDoc) {
                var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes, width=500, height=290, top=50, left=200";
                var pagina = "goAprobarStor.action?iIdDoc=" + idDoc;
                //openDialog(pagina,"Aprobar");
                window.open(pagina,"",opciones);
            }


            function subir() {
                document.forms[0].action = 'doUploadUser.action' ;
                document.forms[0].submit();
            }

            function subirAlfresco() {
                parent.frames["secondFrame"].location.href="goSubirRepositorio.action?iIdDocumento=" + <s:property value='objDD.iIdDocumento' />+"&modulo=subExpUsuFinal" ;
                // document.forms[0].action = "/siged/goSubirRepositorio.action?iIdDocumento=" + iIdDocumento;
                //alert(document.forms[0].action) ;
                // document.forms[0].submit();
            }

            function agregarDocumento() {
                //var opciones = "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=si, resizable=yes, width=730, height=400, top=50, left=200";
                //var pagina = "SubirConMetadata_verSubirConMetadata.action?idDocumento=" + <s:property value='objDD.iIdDocumento' />;
                //openDialog(pagina,"Nuevo Documento");
                //window.open(pagina,"",opciones);
                if(dijit.byId("tabNuevoDocumento")){
                	dijit.byId("tabContainerInbox").removeChild(dijit.byId("tabNuevoDocumento"));
                	dijit.byId("tabNuevoDocumento").destroyDescendants();
                	dijit.byId("tabNuevoDocumento").destroy();
                }
				var dialog = dijit.byId("dlgProgresBar");
                dialog.show() ;
                var newTab = new dojox.layout.ContentPane({
                	id : "tabNuevoDocumento",
                	jsId : "tabNuevoDocumento",
                	closable: true,
                	href : "NuevoDocumento_mostrarVista.action?idExpediente="+<s:property value='objDD.iIdExpediente' />+"&idDocumento=" + <s:property value='objDD.iIdDocumento' />,
                	title: "Nuevo Documento",
                	onClose: function(){
                		showGridInbox(sTipoGridActual);
                		return true;
                	}
               	});
                dijit.byId("tabContainerInbox").addChild(newTab);
                dijit.byId("tabContainerInbox").selectChild(newTab);
                dialog.hide();
            }
            function versionar() {
                var opciones = "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes, width=730, height=350, top=50, left=200";
                var pagina = "SubirConMetadata_verVersionar.action?idDocumento=" + <s:property value='objDD.iIdDocumento' />;
                //openDialog(pagina,"versionar");
                window.open(pagina,"",opciones);
            }
            function abrirVentanaEnumerar() {
                var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=500, height=290, top=50, left=200";
                var pagina = "Numeracion_verVentanaNumeracion.action?idDocumento=" + "<s:property value='objDD.iIdDocumento' />";
                //openDialog(pagina,"Enumerar");
                window.open(pagina,"",opciones);
            }
            function abrirVentanaFirmar() {
                var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes, width=500, height=290, top=50, left=200";
                var pagina = "Numeracion_verVentanaFirmar.action?idDocumento=" + "<s:property value='objDD.iIdDocumento' />";
                //openDialog(pagina,"Enumerar");
                window.open(pagina,"",opciones);
            }

            //ABRIR VENTANA EXPEDIENTE
            function abrirVentanaExpediente() {
                var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes, width=600, height=600, top=50, left=200";
                var pagina = "nuevoExpedienteUF.action?idExpediente=" + "<s:property value='objDD.iIdExpediente' />&idProceso="+"<s:property value='objDD.iIdProceso' />";
                //openDialog(pagina,"Nuevo Expediente");
                window.open(pagina,"",opciones);
            }

            function abrirVentanaCambiarPlazo() {
                var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes, width=500, height=290, top=50, left=200";
                var pagina = "Seguimiento_verCambiarPlazo.action?idtrazabilidaddocumento=" + "<s:property value='idtrazabilidaddocumento' />";
                window.open(pagina,"",opciones);
            }

            function abrirHojaRuta(){
            	var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=900, height=700, top=50, left=200";
                var pagina = "ReporteAPN_verHojaRuta.action?idDocumento=" + "<s:property value='iIdDoc' />";
                window.open(pagina,"",opciones);
            }

            function openMensajeriaWindow(){
                var url = "Mensajeria_mensajeria_winEnvio_abrirEnvio.action?iIdExpediente=" + "<s:property value='objDD.iIdExpediente' />";

                window.open(url, "", "width=600px,height=500px,resizable=1,scrollbars=0");
            }

            function imprimirProveido(){
            	var url = "imprimirProveido.action?iIdDoc=" + "<s:property value='iIdDoc' />";
            	var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=900, height=700, top=50, left=200";
                window.open(url,"",opciones);
            }

            function reabrirExpediente(){
                var idDoc = "<s:property value='iIdDoc' />";
                var nroDoc = "<s:property value='objDD.strNroDocumento' escape='false'/>";
                console.debug("(reabrirDocumento) nroDocumento->" + nroDoc);
                if (!confirm("Desea reabrir el documento " + nroDoc)) {
                    return false;
                }
                dojo.xhrPost({
                    url: "Archivar_reabrirExpediente.action",
                    content: {
                        idDocumento : idDoc
                    },
                    //                form: dojo.byId("frmAgregarSum"),
                    mimetype: "text/html",
                    load: function() {
                        alert("El documento ha sido reabierto");
                        //window.location.reload();
                        showGridInbox();
                    }
                });
                //            window.open(url, "", "width=600px,height=500px,resizable=1,scrollbars=0");
            }

            var showCargo = function() {
                var id = window.open("","","width=600,height=500");
                id.location.href = "generateCargo.action?idDocumento=<s:property value='objDD.iIdDocumento' />";
            };

            var showCargoTicket = function(){
            	window.open("generarCargoTicket.action?idDocumento=<s:property value='objDD.iIdDocumento' />", "", "width=600,height=500");
            };

            function abrirVentanaModificar(){
            	dojo.xhrPost({
        			url: "goModificarDocumento.action",
        			content:{
        				iIdDoc : <s:property value='iIdDoc' />
        			},
        			load: function(data){
        				if(!dijit.byId("dlgModificarDoc")){
        					new dijit.Dialog({
        						id: "dlgModificarDoc",
        	        			draggable:"true",
        	        			style:"height:290px;width:610px;display:none;",
        	        			title:"Modificar Documento",
        			            onClose: dojo.hitch(this, function(){
        			            	dijit.byId("dlgModificarDoc").hide();
        			        		dijit.byId("dlgModificarDoc").destroyRecursive();
        			            })
        	        		});
        				}
        				dijit.byId("dlgModificarDoc").attr("content", data);
        				dijit.byId("dlgModificarDoc").show();
        			}
        		});
            }

            function mostrarAdjuntarArchivo(){
        		dojo.xhrPost({
        			url: "goAdjuntarArchivo.action",
        			content:{
        				iIdDoc : "<s:property value='objDD.iIdDocumento' />"
        			},
        			load: function(data){
        				console.debug("data");
        				if(!dijit.byId("dlgAnexar")){
	        				new dijit.Dialog({
	        					id: "dlgAnexar",
	                			draggable:"true",
	                			style:"height:220px;width:400px;display:none;",
	                			title:"Adjuntar Archivo",
	        		            onClose: dojo.hitch(this, function(){
	        		            	dijit.byId("dlgAnexar").hide();
	        		        		dijit.byId("dlgAnexar").destroyRecursive();
	        		            })
	                		});
        				}
        				console.debug(dijit.byId("dlgAnexar"));
        				dijit.byId("dlgAnexar").attr("content", data);
        				dijit.byId("dlgAnexar").show();
        			}
        		});
        	}

            function mostrarEliminarArchivo(){
        		dojo.xhrPost({
        			url: "goEliminarArchivo.action",
        			content:{
        				iIdDoc : "<s:property value='objDD.iIdDocumento' />"
        			},
        			load: function(data){
        				console.debug("data");
        				if(!dijit.byId("dlgEliminar")){
	        				new dijit.Dialog({
	        					id: "dlgEliminar",
	                			draggable:"true",
	                			style:"height:220px;width:400px;display:none;",
	                			title:"Eliminar Archivo",
	        		            onClose: dojo.hitch(this, function(){
	        		            	dijit.byId("dlgEliminar").hide();
	        		        		dijit.byId("dlgEliminar").destroyRecursive();
	        		            })
	                		});
        				}
        				console.debug(dijit.byId("dlgEliminar"));
        				dijit.byId("dlgEliminar").attr("content", data);
        				dijit.byId("dlgEliminar").show();
        			}
        		});
        	}

            function mostrarSetPrincipalArchivo(idDoc){
            	console.debug("ESTE METODO voy usar  USANDO ....");
        		dojo.xhrPost({
        			url: "goSetPrincipalArchivo.action",
        			content:{
        				iIdDoc : idDoc
        			},
        			load: function(data){
        				console.debug("data");
        				if(!dijit.byId("dlgAnexar")){
	        				new dijit.Dialog({
	        					id: "dlgAnexar",
	                			draggable:"true",
	                			style:"height:220px;width:410px;display:none;",
	                			title:"Archivo Principal",
	        		            onClose: dojo.hitch(this, function(){
	        		            	dijit.byId("dlgAnexar").hide();
	        		        		dijit.byId("dlgAnexar").destroyRecursive();
	        		            })
	                		});
        				}
        				console.debug(dijit.byId("dlgAnexar"));
        				dijit.byId("dlgAnexar").attr("content", data);
        				dijit.byId("dlgAnexar").show();
        			}
        		});
        	}

            function anularDocumento(){
            	if(confirm("¿Desea anular el Documento "+"<s:property value='objDD.strNroDocumento' escape='false' />"+"?")){
            		dojo.xhrPost({
            			url : "anularDocumento.action",
            			content : {
            				iIdDoc : "<s:property value='iIdDoc' />"
            			},
            			load : function(){
            				alert("El documento "+"<s:property value='objDD.strNroDocumento' escape='false' />"+" ha sido anulado");
            				showGridInbox(sTipoGridActual);
            				if(dijit.byId("tabDXE")){
            					dijit.byId("tabDXE").attr("href", dijit.byId("tabDXE").attr("href"));
            				}
            			}
            		});
            	}
            }
            function atenderDocumento(){
            	if(confirm("¿Desea atender el Documento "+"<s:property value='objDD.strNroDocumento' escape='false' />"+"?")){
            		dojo.xhrPost({
            			url : "atenderDocumento.action",
            			content : {
            				iIdDoc : "<s:property value='iIdDoc' />"
            			},
            			load : function(){
            				alert("El documento "+"<s:property value='objDD.strNroDocumento' escape='false' />"+" ha sido atendido");
            				showGridInbox(sTipoGridActual);
            				if(dijit.byId("tabDXE")){
								dijit.byId("tabDXE").attr("href", dijit.byId("tabDXE").attr("href"));
							}
            			}
            		});
            	}
            }

            function reabrirAtendido(){
            	if(confirm("¿Desea reabrir el documento atendido?")){
            		dojo.xhrPost({
            			url : "reabrirDocumentoAtendido.action",
            			content : {
            				iIdDoc : "<s:property value='iIdDoc' />"
            			},
            			load : function(){
            				alert("El documento "+"<s:property value='objDD.strNroDocumento' escape='false' />"+" ha sido reabierto");
            				showGridInbox(sTipoGridActual);
            				if(dijit.byId("tabDXE")){
								dijit.byId("tabDXE").attr("href", dijit.byId("tabDXE").attr("href"));
							}
            			}
            		});
            	}
            }

            function abrirVentanaNotificar() {
            	alert("noti");
                var opciones = "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes, width=800, height=600, top=20, left=70";
                var pagina = "goNotificarUSER.action?&arrIdDoc=" + "<s:property value='iIdDoc' />" + "&iIdDocumentoEnviado";

                window.open(pagina, "", opciones);
             }

            function guardarSeguimiento(agregar){
            	var mensaje = "";
            	if(agregar){
            		mensaje="agregar";
            	}else{
            		mensaje="retirar";
            	}
            	if(confirm("¿Desea "+mensaje+" el Documento"+"<s:property value='objDD.strNroDocumento' escape='false' />"+" a su lista de seguimiento?")){
            		if(dijit.byId("dlgProgresBar")!=null){
    					dijit.byId("dlgProgresBar").show() ;
    				}
            		dojo.xhrPost({
            			url : "guardarSeguimientoUsuario.action",
            			content : {
            				iIdDoc : "<s:property value='iIdDoc' />",
            				agregar: agregar
            			},
            			load : function(){
            				if(agregar){
                				mensaje="agregado a";
            				}else{
                				mensaje="retirado de";
            				}
            				if(dijit.byId("dlgProgresBar")!=null){
    				        	dijit.byId("dlgProgresBar").hide() ;
    				        }
            				alert("El documento "+"<s:property value='objDD.strNroDocumento' escape='false'/>"+" ha sido "+mensaje+" su lista de seguimiento");
            				//showGridInbox(sTipoGridActual);
            				if(sTipoGridActual == TIPO_GRID_SEGUIMIENTO){
            					eliminarRegistro();
            					//filtrarSeguimiento();
            				}
            				refrescarDetalle();
            			}
            		});
            	}
            }

        </script>
    </head>
    <body class="soria">
        <table width="100%">
            <tr>
                <td></td>
                <td colspan="5" align="left">
                    <div dojoType="dijit.Toolbar">
                        <s:if test="avisopermiso || objDD.historico=='S' || documento.estado == 'C' || documento.expediente.estado == 'N' || documento.estado == 'T' || #session.busquedaAvanzada == 1">
                            <s:if test="documento.estado == 'C'">
	                            <div dojoType="dijit.form.Button" onClick="abrirVentanaNotificar()" iconClass="siged16 iconoNotificar">Notificar</div>
                            </s:if>
                            <s:if test="#session._RECURSO.UsuFinBtnTraExp">
                                <div dojoType="dijit.form.Button" onClick="abrirVentanaSeguimiento()" iconClass="dijitEditorIcon dijitEditorIconInsertTable" label="Trazabilidad">
                                </div>
                            </s:if>
                            <s:if test="#session._RECURSO.HojaRuta">
                                <div dojoType="dijit.form.Button"  onClick="abrirHojaRuta()" iconClass="siged16 iconoHojaRuta">Hoja de Ruta</div>
                            </s:if>
                            <s:if test="documento.estado != 'T'">
	                            <s:if test="#session.flagBtnReAbrir">
	                                <div dojoType="dijit.form.Button" onClick="reabrirExpediente()" iconClass="dijitEditorIcon dijitEditorIconInsertTable" label="Reabrir">
	                                </div>
	                            </s:if>
                            </s:if>
                            <s:if test="agregar">
                            	<div dojoType="dijit.form.Button"  onClick="guardarSeguimiento(true)" iconClass="siged16 sigedSearch16">Marcar para Seguimiento</div>
                            </s:if>
                            <s:else>
                            	<div dojoType="dijit.form.Button"  onClick="guardarSeguimiento(false)" iconClass="siged16 sigedDelete16">Retirar del Seguimiento</div>
                            </s:else>
                            <s:if test="#session._RECURSO.BtnImprimirProveido">
                            	<div dojoType="dijit.form.Button"  onClick="imprimirProveido()" iconClass="siged16 iconoImprimir">Imprimir prove&iacute;do</div>
                            </s:if>
                           
                            <%-- <s:if test="#session._RECURSO.MPImpCargo">--
                                <button dojoType="dijit.form.Button"
                                        type="button"
                                        iconClass="siged16 sigedSave16"
                                        onClick="showCargo"
                                        showLabel="true">Imprimir</button>
                            </s:if>--%>
                        </s:if>
                        <s:else>
                        	<!-- Cuando se va a aprobar y archivar ------------------------------------------------------------->
                            <s:if test="paraAprobar">
                                <s:if test="#session._RECURSO.UsuFinBtnDer && documento.documentoreferencia == null">
                                    <div dojoType="dijit.form.Button" onClick="abrirVentanaDerivar('paraaprobar')" iconClass="dijitEditorIcon dijitEditorIconRedo">Aprobar</div>
                                </s:if>
                            </s:if>
                            <s:else>
                                <s:if test="#session._RECURSO.UsuFinBtnDer && documento.documentoreferencia == null">
                                    <div dojoType="dijit.form.Button" onClick="abrirVentanaDerivar('reenviar')" iconClass="dijitEditorIcon dijitEditorIconRedo">Transferir</div>
                                </s:if>
                            </s:else>
                            <s:if test="destinatarioIgualRemitente == false">
	                            <s:if test="#session._RECURSO.UsuFinBtnRec && documento.documentoreferencia == null">
	                                <div dojoType="dijit.form.Button" onClick="abrirVentanaRechazar()" iconClass="dijitEditorIcon dijitEditorIconUndo">Rechazar</div>
	                            </s:if>
                            </s:if>

                            <!-- Controles para reenviar y devolver ------------------------------------------------------------>

                            <s:if test="documento.documentoreferencia != null">
                            	<div dojoType="dijit.form.Button" onClick="abrirVentanaDerivarApoyo()" iconClass="dijitEditorIcon dijitEditorIconUndo">Responder sin Documento</div>
                            	<div dojoType="dijit.form.Button" onClick="agregarDocumento()" iconClass="siged16 sigedNuevoDoc16">Agregar Documento</div>
                            <!-- Control para delegar Apoyo -------------------------------------------------------------------->

                            <!-- 	<div dojoType="dijit.form.Button" onClick="abrirVentanaDelegarApoyo()" iconClass="dijitEditorIcon dijitEditorIconIndent">Enviar M&uacute;ltiple</div> -->
                            	<div dojoType="dijit.form.Button" onClick="abrirVentanaCopiarApoyo()" iconClass="dijitEditorIcon dijitEditorIconIndent">Enviar M&uacute;ltiple</div>
                            </s:if>

                            <s:if test="#session._RECURSO.UsuFinBtnUpl">
	                            <s:if test="destinatarioIgualRemitente == false && documento.documentoreferencia == null">
		                            <div dojoType="dijit.form.Button" onClick="agregarDocumento()" iconClass="siged16 sigedNuevoDoc16">Agregar Documento</div>
		                            <s:if test="documento.documentoreferencia == null">
			                        	<div dojoType="dijit.form.Button" onClick="abrirVentanaCopiarApoyo()" iconClass="dijitEditorIcon dijitEditorIconIndent">Enviar M&uacute;ltiple</div>
			                        </s:if>
	                            </s:if>
	                            <s:else>
	                            	<s:if test="documento.documentoreferencia == null">
		                                <div dojoType="dijit.form.Button" onClick="abrirVentanaCopiarApoyo()" iconClass="dijitEditorIcon dijitEditorIconIndent">Enviar M&uacute;ltiple</div>
		                                <div dojoType="dijit.form.Button" onClick="agregarDocumento()" iconClass="siged16 sigedNuevoDoc16">Agregar Documento</div>
		                            </s:if>
	                            </s:else>
                            </s:if>

                            <!-- Trazabilidad ---------------------------------------------------------------------------------->

                            <s:if test="#session._RECURSO.UsuFinBtnTraExp">
                                <div dojoType="dijit.form.Button" onClick="abrirVentanaSeguimiento()" iconClass="dijitEditorIcon dijitEditorIconInsertTable">Trazabilidad</div>
                            </s:if>

                            <s:if test="#session._RECURSO.HojaRuta">
                                <div dojoType="dijit.form.Button"  onClick="abrirHojaRuta()" iconClass="siged16 iconoHojaRuta">Hoja de Ruta</div>
                            </s:if>

                            <s:if test="#session._RECURSO.UsuFinBtnUpl">
                                <div dojoType="dijit.form.Button"  onClick="abrirVentanaEnumerar()" iconClass="dijitEditorIcon dijitEditorIconInsertOrderedList">Enumerar</div>
                                <%--<div dojoType="dijit.form.Button"  onClick="abrirVentanaFirmar()" iconClass="dijitEditorIcon dijitEditorIconInsertOrderedList">Firmar</div>--%>
                            </s:if>

                            <s:if test="#session._RECURSO.modExpediente && documento.documentoreferencia == null && destinatarioIgualRemitente == true">
                                <div dojoType="dijit.form.Button"  onClick="abrirVentanaModificar()" iconClass="dijitEditorIcon dijitEditorIconSave">Modificar</div>
                            </s:if>

                            <s:if test="#session._RECURSO.BtnAdjuntarArchivo && documento.documentoreferencia == null ">
                                <div dojoType="dijit.form.Button"  onClick="mostrarAdjuntarArchivo()" iconClass="siged16 iconoAgregar">Adjuntar Archivo</div>
                            </s:if>
                            <s:if test="#session._RECURSO.BtnEliminarArchivo && documento.documentoreferencia == null ">
                                <div dojoType="dijit.form.Button"  onClick="mostrarEliminarArchivo()" iconClass="siged16 iconoRemover">Eliminar Archivo</div>
                            </s:if>
                            <s:if test="agregar">
								<div dojoType="dijit.form.Button"  onClick="guardarSeguimiento(true)" iconClass="siged16 sigedSearch16">Marcar para Seguimiento</div>
                            </s:if>
                            <s:else>
                            	<div dojoType="dijit.form.Button"  onClick="guardarSeguimiento(false)" iconClass="siged16 sigedDelete16">Retirar del Seguimiento</div>
                            </s:else>

                            <%--<s:if test="#session._RECURSO.UsuFinBtnNueDocSec">
                               <div dojoType="dijit.form.Button" iconClass="dijitEditorIcon dijitEditorIconBackColor" onClick="Abrir_ventanaId()">Plantilla</div>
                            </s:if>--%>

                            <%-- <s:if test="#session._RECURSO.UsuFinBtnUpl">
                                <div dojoType="dijit.form.Button" onClick="versionar()" iconClass="dijitEditorIcon dijitEditorIconInsertUnorderedList">Versionar</div>
                            </s:if>--%>

                            <%--
                            <s:if test="#session._RECURSO.UsuFinBtnUpl && documento.documentoreferencia == null">
                                <div dojoType="dijit.form.Button"  onClick="abrirVentanaExpediente()" iconClass="dijitEditorIcon dijitEditorIconSelectAll">Nuevo Expediente</div>
                            </s:if>
                             --%>
                            <s:if test="#session._RECURSO.BtnImprimirProveido">
                            	<div dojoType="dijit.form.Button"  onClick="imprimirProveido()" iconClass="siged16 iconoImprimir">Imprimir prove&iacute;do</div>
                            </s:if>
	                        <div dojoType="dijit.form.DropDownButton" label="Otros" class="tundra">
	                            <div dojoType="dijit.Menu" style="display: none;">
	                                <s:if test="#session._RECURSO.UsuFinBtnArc">
	                                    <div dojoType="dijit.MenuItem" iconClass="siged16 iconoArchivar" onClick="abrirVentanaArchivar('archivar')">Archivar</div>
	                                    <div dojoType="dijit.MenuItem" iconClass="siged16 iconoAnular" onClick="abrirVentanaAnularDocumento('anular')">Anular</div>
	                                    <div dojoType="dijit.MenuItem" iconClass="siged16 iconoOk" onClick="atenderDocumento()">Atender</div>
	                                </s:if>
	                                <%--
	                                <s:if test="#session._RECURSO.UsuFinBtnDocVir">
	                                    <div dojoType="dijit.MenuItem" iconClass="dijitEditorIcon dijitEditorIconSelectAll" onClick="loadVirtualDocumentSearch()">Otros Expedientes</div>
	                                </s:if>
	                                --%>
	                              <!--  <div dojoType="dijit.MenuItem" iconClass="dijitEditorIcon dijitEditorIconPaste" onClick="openMensajeriaWindow()">Mensajer&iacute;a</div> -->
	                                <s:if test="#session._RECURSO.MPImpCargo">
		                                <div dojoType="dijit.MenuItem"
		                                        iconClass="siged16 sigedSave16"
		                                        onClick="showCargoTicket">Imprimir</div>
		                            </s:if>
	                                <%--<div dojoType="dijit.MenuItem" iconClass="dijitEditorIcon dijitEditorIconPaste" onClick="abrirVentanaArchivar('oefa')">Enviar a OEFA</div> --%>
	                                <%--<div dojoType="dijit.MenuItem" iconClass="dijitEditorIcon dijitEditorIconCut" onClick="subirAlfresco()">Grabar</div>--%>
	                                <%--<div dojoType="dijit.MenuItem" iconClass="dijitEditorIcon dijitEditorIconToggleDir" onClick="showOnlineHelp('<s:property value='#session._USUARIO.rol' />', '<s:property value='@org.ositran.utils.Constantes@TIPO_PROCESO_ANTIFLUJO' />')">Ayuda en L&iacute;nea</div> --%>
	                        </div>
                            </div>
                        </s:else>
                        <!-- para cambiar el plazo del seguimiento -->
                        <%-- <s:if test="idtrazabilidaddocumento!=null">
                            <div dojoType="dijit.form.Button"  onClick="abrirVentanaCambiarPlazo()" iconClass="dijitEditorIcon dijitEditorIconSave" >Cambiar Plazo</div>
                        </s:if>--%>
                        <!--<div dojoType="dijit.form.DropDownButton" id="toolbar1.forecolor" iconClass="dijitEditorIcon dijitEditorIconForeColor" showLabel="false">
                           <span>Foreground</span>
                           <div dojoType="dijit.ColorPalette" id="toolbar1.colorPalette2" style="display: none" palette="3x4" onChange="console.log(this.value);"></div>
                        </div>
                        <div id="dropdown" dojoType="dijit.form.DropDownButton" label="Mas Opciones">
                           <span>Permite derivar asuntos</span>
                           <div dojoType="dijit.Menu" id="saveMenu" style="display: none;">
                              <div dojoType="dijit.MenuItem"  iconClass="dijitEditorIcon dijitEditorIconSave"
                                   onClick="abrirVentanaDerivar(100)" label="Derivar">
                              </div>
                              <div dojoType="dijit.MenuItem" iconClass="dijitEditorIcon dijitEditorIconCut"
                                   onClick="falseconsole.log('not actually saving anything, just a test!')">Save As</div>
                           </div>
                        </div>-->
                    </div>
                </td>
            </tr>
            <tr>
                <td style="width: 1%;"></td>
                <td colspan="4" style="width: 60%;">
                    <s:if test="#session._UPLOADLIST.upload1 != null">
                        <table>
                            <tr>
                                <td></td>
                                <td height="16" colspan="5" align="left" class="plomo">Adjuntos:</td>
                            </tr>
                            <tr>
                                <td></td>

                                <td height="16" colspan="5" align="left" class="plomo">
                                    <s:iterator value="#session._UPLOADLIST.upload1">
                                        <%--<a href="<s:property value="sURL" />" target="_blank"><s:property value="nombreReal" /></a><br />--%>
                                         <s:if test="boVisor">
                                           <s:if test="principal.equals('S')">
	                                         <a onclick="mostrarPDFVisor('<s:property value='rutaAlfresco' />','<s:property value='#session.busquedaAvanzada'/>');" alt="Ver Archivo">  <s:if test="principal.equals('S')"><b> <font color="blue"><s:property value="nombreReal"/> </font></b></s:if><s:else> <s:property value="nombreReal"/> </s:else></a>
	                                         &nbsp;&nbsp;<a onclick="verArchivo('<s:property value='rutaAlfresco' />');" alt="Ampliar"> [+] </a><br />
                                           </s:if>
                                         </s:if>
                                         <s:else>
                                           <s:if test="principal.equals('S')">
                            	         		<a onclick="verArchivo('<s:property value='rutaAlfresco' />');" alt="Ver Archivo"><s:if test="principal.equals('S')"> <b><font color="blue"><s:property value="nombreReal"/> </font></b></s:if><s:else> <s:property value="nombreReal"/> </s:else></a><br />
                                           </s:if>
                                         </s:else>
                                    </s:iterator>

                                    <s:iterator value="#session._UPLOADLIST.upload1">
                                         <s:if test="boVisor">
                                             <s:if test="!principal.equals('S')">
		                                         <a onclick="mostrarPDFVisor('<s:property value='rutaAlfresco' />','<s:property value='#session.busquedaAvanzada'/>');" alt="Ver Archivo">  <s:if test="principal.equals('S')"><b> <font color="blue"><s:property value="nombreReal"/> </font></b></s:if><s:else> <s:property value="nombreReal"/> </s:else></a>
		                                         &nbsp;&nbsp;<a onclick="verArchivo('<s:property value='rutaAlfresco' />');" alt="Ampliar"> [+] </a><br />
                                             </s:if>
                                         </s:if>
                                         <s:else>
                                             <s:if test="!principal.equals('S')">
                            	         		<a onclick="verArchivo('<s:property value='rutaAlfresco' />');" alt="Ver Archivo"><s:if test="principal.equals('S')"> <b><font color="blue"><s:property value="nombreReal"/> </font></b></s:if><s:else> <s:property value="nombreReal"/> </s:else></a><br />
                                            </s:if>
                                         </s:else>
                                    </s:iterator>

                                </td>
                            </tr>
                        </table>
                    </s:if>
                </td>
                <td style="width: 39%;" align="right">
                    <div id="tabla12" style="width: 100%;text-align:left;">
                        <table cellpadding="1" cellspacing="1" class="tableForm">
                        	<s:if test="documento.confidencial.equals('S')">
                                <tr>
                                    <td style="font-size: 14px;font-weight:bold;background-color:#DA1217;color:#ffffff;vertical-align:center;text-align:center;border: 1px #000 solid;" colspan="2">
									DOCUMENTO CONFIDENCIAL
                                    </td>
                                </tr>
                            </s:if>
                            <tr>
                                <td style="font-size: 10px;font-weight:normal;background-color:#669BCD;color:#ffffff;vertical-align:center;text-align:center;border: 1px #000 solid;">
								Nro. Documento
                                </td>
                                <td style="background-color:#ffffff;border: 1px #000 solid;width: 250px;">
                                    <s:property value="documento.numeroDocumento" />
                                </td>
                            </tr>
                            <tr>
                                <td style="font-size: 10px;font-weight:normal;background-color:#669BCD;color:#ffffff;vertical-align:center;text-align:center;border: 1px #000 solid;">
								Nro. Expediente
                                </td>
                                <td style="background-color:#ffffff;border: 1px #000 solid;width: 250px;">
                                    <s:property value="objDD.strReferencia" />
                                </td>
                            </tr>
                            <tr>
                                <td style="font-size: 10px;font-weight:normal;background-color:#669BCD;color:#ffffff;vertical-align:center;text-align:center;border: 1px #000 solid;">
								Proceso
                                </td>
                                <td style="background-color:#ffffff;border: 1px #000 solid;width: 250px;">
                                    <s:property value="objDD.strProceso" />
                                </td>
                            </tr>
                            <s:if test="objDD.strRazonSocial!=null && objDD.codProceso!=@org.ositran.utils.Constantes@CODIGO_COMUNICACIONES_INT">
                                <tr>
                                    <td style="font-size: 10px;font-weight:normal;background-color:#669BCD;color:#ffffff;vertical-align:center;text-align:center;border: 1px #000 solid;">
								Cliente
                                    </td>
                                    <td style="background-color:#ffffff;border: 1px #000 solid;width: 250px;">
                                        <s:property value="objDD.strRazonSocial" />
                                    </td>
                                </tr>
                            </s:if>
                            <s:if test="documento.referenciados!=null && !documento.referenciados.equals(\"-\")">
                                <tr>
                                    <td style="font-size: 10px;font-weight:normal;background-color:#669BCD;color:#ffffff;vertical-align:center;text-align:center;border: 1px #000 solid;">
									Documentos <br/>Referenciados
                                    </td>
                                    <td style="background-color:#ffffff;border: 1px #000 solid;width: 250px;">
                                        <s:property value="documento.referenciados" />
                                    </td>
                                </tr>
                            </s:if>
                            <s:elseif test="objDD.strCorrentista!=null && objDD.codProceso!=@org.ositran.utils.Constantes@CODIGO_COMUNICACIONES_INT">
                                <tr>
                                    <td style="font-size: 10px;font-weight:normal;background-color:#669BCD;color:#ffffff;vertical-align:center;text-align:center;border: 1px #000 solid;">
								Concesionario
                                    </td>
                                    <td style="background-color:#ffffff;border: 1px #000 solid;width: 250px;">
                                        <s:property value="objDD.strCorrentista" />
                                    </td>
                                </tr>
                            </s:elseif>
                        </table>
                    </div>
                </td>
            </tr>
            <tr>
                <td height="50" colspan="6">
                    <table width="100%"  border="1" align="center" bordercolor="#669BCD" bgcolor="#ffffff">
                        <tr>
                            <td>
                                <table width="100%" height="100" align="center" >
                                    <tr>
                                        <td width="2%"></td>
                                        <td width="1%" height="5"></td>
                                        <td width="1%"></td>
                                        <td width="4%"></td>
                                        <td width="1%"></td>
                                        <td width="91%"></td>
                                    </tr>
                                    <tr>
                                        <td width="2%"></td>
                                        <td height="16" colspan="5" align="left" class="asunto">
                                            <s:textfield name="iIdDoc" cssStyle="display:none" />
                                            <s:textfield name="objDD.cEstado" cssStyle="display:none" />
                                            <s:textfield name="objDD.iIdCliente" cssStyle="display:none" />
                                            <s:property value="objDD.strAsunto" />
                                            <s:textfield name="strAcc" value="aprobar" cssStyle="display:none" />
                                        </td>
                                    </tr>
                                    <tr>
                                        <td height="23" colspan="6"></td>
                                    </tr>
                                    <tr>
                                        <td></td>
                                        <td height="16" colspan="4" align="left" class="plomo" >Para:</td>
                                        <td><s:property value="objDD.strDestinatario" /></td>
                                    </tr>
                                    <tr>
                                        <td></td>
                                        <td height="16" colspan="5" align="left" class="plomo" ><hr style="width:100%;color:#9199AC;background-color:#9199AC;border-color:#9199AC;"/></td>
                                    </tr>
                                    <tr>
                                        <td></td>
                                        <td height="16" colspan="4" align="left" class="plomo">Cc: </td>
                                         <td><s:property value="objDD.cadenaCC" /></td>

                                    </tr>
                                    <tr>
                                        <td height="5" colspan="6" ><hr></td>
                                    </tr>
                                    <tr>
                                        <td colspan="6">
                                            <s:if test="objDD.strContenido != null">
                                                <%-- <s:hidden id="objDD.strContenido" name="objDD.strContenido" />--%>
                                                <div id="contenido"><s:property value='objDD.strContenido' escape='false'/></div>
                                                <script type="text/javascript">
                                                    //document.getElementById("contenido").innerHTML = document.getElementById("objDD.strContenido").value;
                                                </script>
                                            </s:if>
                                            <s:else>
                                                <p>
                                                    Ud. ha registrado un <font class="negrita"><s:property value="objDD.strTipoDocumento" /></font>
                                                    Nro. <font class="negrita"><s:property value="objDD.strNroDocumento" /></font>
                                                    el dia <font class="negrita"><s:date name="objDD.strFecha" format="dd/MM/yyyy" /></font>
                                                    a las <font class="negrita"><s:date name="objDD.strFecha" format="HH:mm" /></font>
                                                    <s:if test='document.expediente.nrointerno != null && document.expediente.nrointerno != " "'>
                                                        se generó el expediente interno <font class="negrita">Nro. <s:property value="documento.expediente.nrointerno"/> </font>,
                                                    </s:if>
                                                    referente al proceso <font class="negrita"><s:property value="objDD.strProceso" /></font>.
                                                </p>
                                                <s:if test="objDD.strRazonSocial!=null && objDD.codProceso!=@org.ositran.utils.Constantes@CODIGO_COMUNICACIONES_INT">
                                                    <p>
                                                        El cliente <font class="negrita"><s:property value="objDD.strRazonSocial" /></font>
                                                        se identifica con <font class="negrita"><s:property value="objDD.strTipoIdentificacion" /></font>:
                                                        <font class="negrita"><s:property value="objDD.strNroIdentificacion" /></font>
                                                        y dirección domiciliaria <font class="negrita"><s:property value="objDD.strDireccionPrincipal" /></font>.
                                                    </p>
                                                </s:if>
                                                <s:elseif test="objDD.strCorrentista!=null">
                                                    <p>
                                                        La entidad <font class="negrita"><s:property value="objDD.strCorrentista" /></font>
                                                        se identifica con <font class="negrita">RUC </font>:
                                                        <font class="negrita"><s:property value="objDD.strRUC" /></font>
                                                        y dirección <font class="negrita"><s:property value="objDD.strDireccionConcesionario" /></font>.
                                                    </p>
                                                </s:elseif>
                                                <s:if test="objDD.observacionDigitalizador!=null">
                                                    <p>
                                                        Observacion Digitalizador: <font class="negrita"><s:property value="objDD.observacionDigitalizador" /></font>.
                                                    </p>
                                                </s:if>
                                            </s:else>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td height="25" colspan="6" ></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td height="14" colspan="6"></td>
            </tr>
        </table>
        <%--
              <div id="external" dojoType="dijit.Dialog" title="My external dialog"
                  href="http://docs.dojocampus.org/HelpContents" style="width: 800px; height: 500px;">
             </div>--%>
    </body>
</html>