<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<%@ page import="org.ositran.utils.Constantes" %>
<%@ page import="java.text.SimpleDateFormat"%> 
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html>
    <head>
        <title>Detalle del Documento Firmado</title>

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

                
            </script>
            <script type="text/javascript" src="js/dojo/dojo/dojo.js"></script>
            
        </s:if>
         <script type="text/javascript" src="js/destruirObjetos.js"></script>         
        <script type="text/javascript">
            destroyWidgetsFromLegajo();  
            
            function crearLegajo(){
               if (dijit.byId("dlgCrearLegajo")){
                  document.getElementById("idDocumentoExpediente").value = "<s:property value='documento.idDocumento' />" 
                  dijit.byId("dlgCrearLegajo").show();
               }
             } 
            
             function abrirVentanaLegajo(){
               if (dijit.byId("dlgBusquedaLegajo")) {
                   if (dijit.byId("sLegajo")) {
                     dijit.byId("sLegajo").attr("value", "");
                   }
                   
                   if (dijit.byId("sNroHTLegajo")) {
                     dijit.byId("sNroHTLegajo").attr("value", "");
                   }
                   
                   if (dijit.byId("sNroDocumentoLegajo")) {
                     dijit.byId("sNroDocumentoLegajo").attr("value", "");
                   }
                   
                   if (dijit.byId("sTipoDocumentoLegajo")) {
                     dijit.byId("sTipoDocumentoLegajo").attr("value", "");
                   }
                   
                   if (dijit.byId("sTipoExpediente_")) {
                     dijit.byId("sTipoExpediente_").attr("value", "");
                   }

                   if (dijit.byId("grdBusquedaLegajo")) {
                     dijit.byId("grdBusquedaLegajo").setStore(null);
                   }  
                   
                   document.getElementById("idDocumentoExpediente").value = "<s:property value='documento.idDocumento' />"
                   dijit.byId("dlgBusquedaLegajo").show();
               }
             } 
        
             function verArchivo(idArchivo, url, objectId){
                var fecha = new Date();
                window.open("<%=request.getContextPath()%>/verDocumento.png?idArchivo=" +idArchivo + "&url=" + url + "&objectId="+ objectId  + "&accion=abrirDocumento&fecha=" + fecha.getTime());
             }    
            
             function abrirVentanaCopiarApoyo(){
                var principal = false;
                var rol = "<s:property value='#session._USUARIO.idRolPerfil' />" + "";
                
                if ("<s:property value='documento.tipoDocumento.idtipodocumento' />" != "<s:property value='@org.ositran.utils.Constantes@SOLICITUD_INFORMACION_PUBLICA'/>" && 
                    "<s:property value='documento.tipoDocumento.idtipodocumento' />" != "<s:property value='@org.ositran.utils.Constantes@HOJA_RECLAMACIONES'/>"){
                        if ("<s:property value='documento.enumerado' />"=="N" && "<s:property value='documento.proyecto' />"!="<s:property value='@org.ositran.utils.Constantes@DOCUMENTO_PROYECTO'/>"){
                          alert("Debe enumerar el documento antes de derivarlo.");
                          return;
                        }

                        if ("<s:property value='documento.proyecto' />"=="<s:property value='@org.ositran.utils.Constantes@DOCUMENTO_PROYECTO'/>" && "<s:property value='documento.enumerado' />"=="N"){
                             <s:if test="destinatarioIgualRemitente == false">
                              if (!confirm("El documento no se encuentra enumerado. Desea continuar con la Derivación?")){      
                                 return;
                               }   
                             </s:if>    
                        }    

                        <s:if test="#session._UPLOADLIST.upload1 == null || #session._UPLOADLIST.upload1.size()==0">
                           if ("<s:property value='documento.proyecto' />"!="<s:property value='@org.ositran.utils.Constantes@DOCUMENTO_PROYECTO'/>"){ 
                             alert("El documento debe contener un archivo principal.");
                             return;
                           }
                           if ("<s:property value='documento.proyecto' />"=="<s:property value='@org.ositran.utils.Constantes@DOCUMENTO_PROYECTO'/>"){
                             alert("El documento debe contener un archivo proyecto.");
                             return;  
                           }    
                        </s:if>

                        if ("<s:property value='documento.proyecto' />"!="<s:property value='@org.ositran.utils.Constantes@DOCUMENTO_PROYECTO'/>"){ 
                            <s:iterator value="#session._UPLOADLIST.upload1">
                                 <s:if test="principal.equals('S')">
                                      principal = true;
                                 </s:if>           
                             </s:iterator>

                            if (!principal){
                               alert("El documento debe contener un archivo principal.");
                               return; 
                            }
                        }   
                }
                
                if ("<s:property value='documento.proyecto' />"=="<s:property value='@org.ositran.utils.Constantes@DOCUMENTO_PROYECTO'/>"){
                    <s:if test="destinatarioIgualRemitente == false">
                        <s:iterator value="#session._UPLOADLIST.upload1">
                             <s:if test="principal.equals('S')">
                                 principal = true;
                             </s:if>           
                        </s:iterator>

                        if (!principal &&  "<s:property value='documento.enumerado' />"=="S"){
                           alert("El documento debe contener un archivo principal.");
                           return; 
                        }
                    </s:if>    
                }
                
                if (rol!=null && rol!='' && rol!="<s:property value='@org.ositran.utils.Constantes@COD_ROL_TRAMITE'/>" && rol!="<s:property value='@org.ositran.utils.Constantes@COD_ROL_LOCADOR'/>" && rol!="<s:property value='@org.ositran.utils.Constantes@COD_ROL_MENSAJERIA'/>" && "<s:property value='documento.proyecto' />"!="<s:property value='@org.ositran.utils.Constantes@DOCUMENTO_PROYECTO'/>"){
                    var service = new dojo.rpc.JsonService("SMDAction.action");
                    var defered = service.validarTieneFirmaPrincipal(<s:property value='documento.idDocumento' />);
                    defered.addCallback(function(respuesta){
                        
                        if (respuesta == '-1'){
                            alert("El documento ya fue derivado");   
                            showGridInbox(sTipoGridActual);
                            return;
                        }
                        
                        if (respuesta == '-2'){
                           alert("El documento ya fue atendido");    
                           showGridInbox(sTipoGridActual);
                           return;
                         }
                         
                         if (respuesta == '-3'){
                           alert("El documento ya fue anulado");    
                           showGridInbox(sTipoGridActual);
                           return;
                         }
                         
                         if (respuesta == '-4'){
                             if (!confirm("El número total de anexos virtuales no han sido cargados al Sistema SGD. ¿Desea Derivarlo?")){ 
                                return;
                            }
                         }
                        
                        if (respuesta == '0'){ 
                            if (!confirm("El documento Principal no tiene una Firma Digital. ¿Desea Derivarlo?")){ 
                                return;
                            }     
                        }

                        var opciones = "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes, width=810, height=516, top=20, left=70";
                        var pagina = "goCopiarApoyo.action?arrIdDoc=" + <s:property value='documento.idDocumento' />;
                        window.open(pagina, "Ventana", opciones);   
                    });
                }else{
                        var service = new dojo.rpc.JsonService("SMDAction.action");
                        var defered = service.validarTieneFirmaPrincipal(<s:property value='documento.idDocumento' />);
                        defered.addCallback(function(respuesta){

                            if (respuesta == '-1'){
                                alert("El documento ya fue derivado");   
                                showGridInbox(sTipoGridActual);
                                return;
                            }

                            if (respuesta == '-2'){
                                alert("El documento ya fue atendido");    
                                showGridInbox(sTipoGridActual);
                                return;
                            }

                            if (respuesta == '-3'){
                                alert("El documento ya fue anulado");    
                                showGridInbox(sTipoGridActual);
                                return;
                            }

                            if (respuesta == '-4'){
                                if (!confirm("El número total de anexos virtuales no han sido cargados al Sistema SGD. ¿Desea Derivarlo?")){ 
                                   return;
                                }
                             }

                            if (respuesta == '0'){ 
                                if (!confirm("El documento Principal no tiene una Firma Digital. ¿Desea Derivarlo?")){ 
                                    return;
                                }     
                            }

                            var opciones = "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes, width=790, height=516, top=20, left=70";
                            var pagina = "goCopiarApoyo.action?arrIdDoc=" + <s:property value='documento.idDocumento' />;
                            window.open(pagina, "Ventana", opciones);   
                    });            
                              
                }    
            }
            
            function abrirVentanaRechazar() {
                    var opciones = "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes, width=770, height=390, top=20, left=70";
                    var pagina = "goDerivarUSER.action?arrIdDoc=" + <s:property value='documento.idDocumento' /> + "&sTipoDerivacion=normal&sOpcion=rechazar";
                    window.open(pagina, "Ventana", opciones);
            }
            
            function abrirVentanaDerivar(opcion) {
                var principal = false;
                var rol = "<s:property value='#session._USUARIO.idRolPerfil' />" + "";
                
                if ("<s:property value='documento.tipoDocumento.idtipodocumento' />" != "<s:property value='@org.ositran.utils.Constantes@SOLICITUD_INFORMACION_PUBLICA'/>" && 
                    "<s:property value='documento.tipoDocumento.idtipodocumento' />" != "<s:property value='@org.ositran.utils.Constantes@HOJA_RECLAMACIONES'/>"){
                        if ("<s:property value='documento.enumerado' />"=="N" && "<s:property value='documento.proyecto' />"!="<s:property value='@org.ositran.utils.Constantes@DOCUMENTO_PROYECTO'/>"){
                          alert("Debe enumerar el documento antes de derivarlo.");
                          return;
                        }

                        if ("<s:property value='documento.proyecto' />"=="<s:property value='@org.ositran.utils.Constantes@DOCUMENTO_PROYECTO'/>" && "<s:property value='documento.enumerado' />"=="N"){
                             <s:if test="destinatarioIgualRemitente == false">
                               if (!confirm("El documento no se encuentra enumerado. Desea continuar con la Derivación?")){      
                                 return;
                               }   
                             </s:if>    
                        }    

                        <s:if test="#session._UPLOADLIST.upload1 == null || #session._UPLOADLIST.upload1.size()==0">
                           if ("<s:property value='documento.proyecto' />"!="<s:property value='@org.ositran.utils.Constantes@DOCUMENTO_PROYECTO'/>"){ 
                             alert("El documento debe contener un archivo principal.");
                             return;
                           }
                           if ("<s:property value='documento.proyecto' />"=="<s:property value='@org.ositran.utils.Constantes@DOCUMENTO_PROYECTO'/>"){
                             alert("El documento debe contener un archivo proyecto.");
                             return;  
                           }    
                        </s:if>

                        if ("<s:property value='documento.proyecto' />"!="<s:property value='@org.ositran.utils.Constantes@DOCUMENTO_PROYECTO'/>"){ 
                            <s:iterator value="#session._UPLOADLIST.upload1">
                                 <s:if test="principal.equals('S')">
                                     principal = true;
                                 </s:if>           
                            </s:iterator>

                            if (!principal){
                               alert("El documento debe contener un archivo principal.");
                               return; 
                            }
                        }
                }
                
                if ("<s:property value='documento.proyecto' />"=="<s:property value='@org.ositran.utils.Constantes@DOCUMENTO_PROYECTO'/>"){
                    <s:if test="destinatarioIgualRemitente == false">
                        <s:iterator value="#session._UPLOADLIST.upload1">
                             <s:if test="principal.equals('S')">
                                 principal = true;
                             </s:if>           
                        </s:iterator>

                        if (!principal && "<s:property value='documento.enumerado' />"=="S"){
                           alert("El documento debe contener un archivo principal.");
                           return; 
                        }
                    </s:if>    
                }
                
                if (rol!=null && rol!='' && rol!="<s:property value='@org.ositran.utils.Constantes@COD_ROL_TRAMITE'/>" && rol!="<s:property value='@org.ositran.utils.Constantes@COD_ROL_LOCADOR'/>" && rol!="<s:property value='@org.ositran.utils.Constantes@COD_ROL_MENSAJERIA'/>" && "<s:property value='documento.proyecto' />"!="<s:property value='@org.ositran.utils.Constantes@DOCUMENTO_PROYECTO'/>"){
                    var service = new dojo.rpc.JsonService("SMDAction.action");
                    var defered = service.validarTieneFirmaPrincipal("<s:property value='documento.idDocumento' />");
                    defered.addCallback(function(respuesta){
                         if (respuesta == '-1'){
                            alert("El documento ya fue derivado");   
                            showGridInbox(sTipoGridActual);
                            return;
                         }
                         
                         if (respuesta == '-2'){
                           alert("El documento ya fue atendido");    
                           showGridInbox(sTipoGridActual);
                           return;
                         }
                         
                         if (respuesta == '-3'){
                           alert("El documento ya fue anulado");    
                           showGridInbox(sTipoGridActual);
                           return;
                         }
                         
                         if (respuesta == '-4'){
                             if (!confirm("El número total de anexos virtuales no han sido cargados al Sistema SGD. ¿Desea Derivarlo?")){ 
                                return;
                            }
                         }
                         
                         if (respuesta == '0'){ 
                            if (!confirm("El documento Principal no tiene una Firma Digital. ¿Desea Derivarlo?")){ 
                                return;
                            }     
                         }
                         
                         var opciones = "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes, width=770, height=485, top=20, left=70";
                         var pagina = "goDerivarUSER.action?arrIdDoc=" + <s:property value='documento.idDocumento' /> + "&sTipoDerivacion=normal&sOpcion="+opcion;
                         window.open(pagina, "Ventana", opciones);
                    });
                }else{
                        var service = new dojo.rpc.JsonService("SMDAction.action");
                        var defered = service.validarTieneFirmaPrincipal(<s:property value='documento.idDocumento' />);
                        defered.addCallback(function(respuesta){

                            if (respuesta == '-1'){
                                alert("El documento ya fue derivado");   
                                showGridInbox(sTipoGridActual);
                                return;
                            }

                            if (respuesta == '-2'){
                                alert("El documento ya fue atendido");    
                                showGridInbox(sTipoGridActual);
                                return;
                             }

                             if (respuesta == '-3'){
                                alert("El documento ya fue anulado");    
                                showGridInbox(sTipoGridActual);
                                return;
                              }

                              if (respuesta == '-4'){
                                    if (!confirm("El número total de anexos virtuales no han sido cargados al Sistema SGD. ¿Desea Derivarlo?")){ 
                                       return;
                                   }
                                }

                                if (respuesta == '0'){ 
                                    if (!confirm("El documento Principal no tiene una Firma Digital. ¿Desea Derivarlo?")){ 
                                        return;
                                    }     
                                }

                                var opciones = "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes, width=770, height=485, top=20, left=70";
                                var pagina = "goDerivarUSER.action?arrIdDoc=" + <s:property value='documento.idDocumento' /> + "&sTipoDerivacion=normal&sOpcion="+opcion;
                                window.open(pagina, "Ventana", opciones);  
                        });            
                            
                }
            }

            function abrirVentanaSeguimiento() {
                var opciones = "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=no, width=650, height=500, top=50, left=200";
                window.open("goViewSeguimiento.action?iIdDocumento=" + <s:property value='documento.idDocumento' />, "", opciones);
            }
        
            function abrirHojaRuta(){
            	var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=900, height=700, top=50, left=200";
                var pagina = "ReporteAPN_verHojaRuta.action?idDocumento=" + "<s:property value='documento.idDocumento' />";
                window.open(pagina,"HojaRuta",opciones);
            }
            
            function abrirHojaFirma(){
            	var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=900, height=650, top=50, left=200";
                var pagina = "ReporteAPN_verHojaFirma.action?idDocumento=" + "<s:property value='documento.idDocumento' />";
                window.open(pagina,"HojaFirma",opciones);
            }
            
            function abrirVentanaEnumerar() {
                if ("<s:property value='documento.enumerado' />"=="S"){
                  alert("El documento ya se encuentra enumerado.");
                  return;
                }
                
                var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=500, height=290, top=50, left=200";
                var pagina = "Numeracion_verVentanaNumeracion.action?idDocumento=" + "<s:property value='documento.idDocumento' />";
                //openDialog(pagina,"Enumerar");
                window.open(pagina,"Numerar Documento",opciones);
            }
            
             function verDocumento(){
               
            	var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=900, height=700, top=50, left=200";
                var pagina = "verDocumento.action?idDocumento=" + "<s:property value='documento.idDocumento' />";
                window.open(pagina,"Metadata",opciones);
            }
            
            function atenderDocumento(tipoArchivar){
                var service = new dojo.rpc.JsonService("SMDAction.action");
                var defered = service.validarEnvio(<s:property value='documento.idDocumento' />, "", "", "");
                defered.addCallback(function(respuesta){
                                     
                     if (respuesta == '-1'){
                        alert("El documento ya fue derivado");   
                        showGridInbox(sTipoGridActual);
                        return;
                     }
                     
                     if (respuesta == '-2'){
                        alert("El documento ya fue atendido");    
                        showGridInbox(sTipoGridActual);
                        return;
                     }

                    if (respuesta == '-3'){
                        alert("El documento ya fue anulado");    
                        showGridInbox(sTipoGridActual);
                        return;
                    }
                    
                     if (respuesta == "-5"){
                        alert("Se ha producido un error inesperado. Intente procesar su solicitud nuevamente."); 
                        return;
                     }
                                        
                     var opciones = "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes, width=750, height=440, top=20, left=70";
                     var pagina = "Archivar_inicio1.action?idDocumento=" + <s:property value='documento.idDocumento' />+ "&tipoArchivar="+tipoArchivar;
                     window.open(pagina, "Ventana", opciones); 
                 });
                
            }
            
            function mostrarAdjuntarArchivo(){
                   if ("<s:property value='documento.enumerado' />"=="N" && "<s:property value='documento.proyecto' />"!="<s:property value='@org.ositran.utils.Constantes@DOCUMENTO_PROYECTO'/>"){
                      alert("Para adjuntar un archivo debe enumerar el documento.");
                      return;
                   }
                   
                   var service = new dojo.rpc.JsonService("SMDAction.action");
                   var defered = service.validarEnvio(<s:property value='documento.idDocumento' />, "", "", "");
                   defered.addCallback(function(respuesta){

                         if (respuesta == '-1'){
                            alert("El documento ya fue derivado");   
                            showGridInbox(sTipoGridActual);
                            return;
                         }

                         if (respuesta == '-2'){
                            alert("El documento ya fue atendido");    
                            showGridInbox(sTipoGridActual);
                            return;
                         }

                         if (respuesta == '-3'){
                            alert("El documento ya fue anulado");    
                            showGridInbox(sTipoGridActual);
                            return;
                         }
                         
                         if (respuesta == "-5"){
                            alert("Se ha producido un error inesperado. Intente procesar su solicitud nuevamente."); 
                            return;
                         }

                         var amplitud = "255px"; 
                         if ('<s:property value='#session._USUARIO.permisoCargo' />' == '1'){
                           amplitud = "268px";    
                         }    
                         
                         dojo.xhrPost({
        			url: "goAdjuntarArchivo.action",
        			content:{
        				iIdDoc : "<s:property value='documento.idDocumento' />"
        			},
        			load: function(data){
                        		console.debug("data");
        				if(!dijit.byId("dlgAnexar")){
	        				new dijit.Dialog({
	        					id: "dlgAnexar",
	                			draggable:"true",
	                			style:"height:" + amplitud + ";width:427px;display:none;",
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
                        
                    });        
            }
                
            function mostrarEliminarArchivo(){
                     var service = new dojo.rpc.JsonService("SMDAction.action");
                     var defered = service.validarTieneFirmaPrincipal("<s:property value='documento.idDocumento' />");
                     defered.addCallback(function(respuesta){
                     
                        if (respuesta == '-1'){
                          alert("El documento ya fue derivado");    
                          showGridInbox(sTipoGridActual);
                          return;
                        }

                        if (respuesta == '-2'){
                          alert("El documento ya fue atendido");    
                          showGridInbox(sTipoGridActual);
                          return;
                        }

                        if (respuesta == '-3'){
                          alert("El documento ya fue anulado");    
                          showGridInbox(sTipoGridActual);
                          return;
                        }

        		dojo.xhrPost({
        			url: "goEliminarArchivo.action",
        			content:{
        				iIdDoc : "<s:property value='documento.idDocumento' />"
        			},
        			load: function(data){
        				console.debug("data");
        				if(!dijit.byId("dlgEliminar")){
	        				new dijit.Dialog({
	        					id: "dlgEliminar",
	                			draggable:"true",
	                			style:"height:220px;width:440px;display:none;",
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
                     });            
            }
            
        </script>
    </head>
    <body class="soria">
        <table width="100%">
            <tr>
                
                <td colspan="6" align="left">
                    <div dojoType="dijit.Toolbar"> 
                     <!-- <div dojoType="dijit.form.Button"  onClick="abrirVentanaSeguimiento()" iconClass="dijitEditorIcon dijitEditorIconInsertTable">Trazabilidad</div> -->
                      <s:if test="documento.documentoreferencia == null">
                         <s:if test="objDD.flagCodigoVirtual == '2' || objDD.flagCodigoVirtual == '0'">
                            <div dojoType="dijit.form.Button" onClick="abrirVentanaDerivar('reenviar')" iconClass="dijitEditorIcon dijitEditorIconRedo">Derivar</div>
                         </s:if> 
                         <s:if test="controlDevolver == false && (objDD.flagCodigoVirtual == '2' || objDD.flagCodigoVirtual == '0')">
                             <div dojoType="dijit.form.Button" onClick="abrirVentanaRechazar()" iconClass="dijitEditorIcon dijitEditorIconUndo">Devolver</div>
                         </s:if> 
                      </s:if> 
                      <s:if test="objDD.flagCodigoVirtual == '2' || objDD.flagCodigoVirtual == '0'">       
                        <div dojoType="dijit.form.Button" onClick="abrirVentanaCopiarApoyo()" iconClass="dijitEditorIcon dijitEditorIconIndent">Derivar M&uacute;ltiple</div>
                      </s:if>
                      <s:if test="documento.documentoreferencia == null && documento.enumerado == 'N' && documento.proyecto!= null && documento.proyecto==@org.ositran.utils.Constantes@DOCUMENTO_PROYECTO && destinatarioIgualRemitente == false">
                           <div dojoType="dijit.form.Button"  onClick="abrirVentanaEnumerar()" iconClass="dijitEditorIcon dijitEditorIconInsertOrderedList">Enumerar</div>
                      </s:if>  
                      <div dojoType="dijit.form.Button"  onClick="abrirHojaRuta()" iconClass="siged16 iconoHojaRuta">Hoja de Ruta</div>
                      <div dojoType="dijit.form.Button"  onClick="abrirHojaFirma()" iconClass="siged16 iconoHojaRuta">Hoja de Firmas</div>  
                      <div dojoType="dijit.form.Button"  onClick="mostrarAdjuntarArchivo()" iconClass="siged16 iconoAgregar">Agregar Archivo</div>
                      <div dojoType="dijit.form.Button"  onClick="mostrarEliminarArchivo()" iconClass="siged16 iconoRemover">Eliminar Archivo</div>
                      <s:if test="objDD.flagCodigoVirtual == '2' || objDD.flagCodigoVirtual == '0'">
                        <div dojoType="dijit.form.Button"  onClick="atenderDocumento('atender')" iconClass="siged16 iconoOk">Atender</div>    
                     </s:if>
                      <s:if test="objDD.flagExpediente=='A' || objDD.flagExpediente=='T'">         
                          <div dojoType="dijit.form.Button"  iconClass="siged16 iconoAgregar"   onClick="abrirVentanaLegajo()">Agregar a Expediente</div>       
                      </s:if>
                      <s:if test="objDD.flagExpediente=='C' || objDD.flagExpediente=='T'">   
                          <div dojoType="dijit.form.Button"  iconClass="siged16 sigedNuevoDoc16" onClick="crearLegajo()">Crear Expediente</div>         
                      </s:if>      
                    </div>
                </td>
            </tr>
            <tr>
                <td style="width: 2%;"></td>
                <td colspan="2" style="width: 35%;font-size:12px;" valign="top"> 
                    <s:textfield name="iIdDoc" cssStyle="display:none" />
                    <s:textfield name="objDD.cEstado" cssStyle="display:none" />
                    <s:textfield name="objDD.iIdCliente" cssStyle="display:none" />
                    <br/><b>Asunto: </b><s:property value="documento.asunto" />
                    <s:if test="objDD.prioridad != null && objDD.prioridad!=''">
                        <br />  <b>Prioridad:</b> <img src="images/Prioridad_<s:property value="objDD.prioridad" />.png" />
                    </s:if>
                    <s:if test="objDD.strFechaLimiteAtencion != null && objDD.strFechaLimiteAtencion!=''">
                        <br /><b>Fecha Limite:</b> <s:property value="objDD.strFechaLimiteAtencion" />
                        <s:textfield name="strAcc" value="aprobar" cssStyle="display:none" />
                    </s:if>      
                        
                </td>
                <td colspan="2" style="width: 35%;" valign="top">
                    <s:if test="#session._UPLOADLIST.upload1 != null">
                        <table>
                            <tr>
                                <td></td>
                                <td height="16" colspan="5" align="left" class="plomo"><br/>Adjuntos:</td>
                            </tr>
                            <tr>
                                <td></td>

                                <td height="16" colspan="5" align="left" class="plomo">
                                    <s:iterator value="#session._UPLOADLIST.upload1">
                                           <s:if test="principal.equals('S')">
                                               <a onclick="verArchivo('<s:property value='idArchivo' />','<s:property value='rutaAlfresco' />', '<s:property value='objectId' />');" alt="Ver Archivo"> <b><font color="<s:property value="@org.ositran.utils.Constantes@COLOR_DOCUMENTO_PRINCIPAL"/>"><s:property value="nombreReal"/> </font></b> [<s:property value="usuarioCarga.usuario"/>]<br />
                                           </s:if>
                                    </s:iterator>
                                                   
                                    <s:iterator value="#session._UPLOADLIST.upload1">               
                                           <s:if test="principal.equals('N')">
                                                       <a onclick="verArchivo('<s:property value='idArchivo' />','<s:property value='rutaAlfresco' />', '<s:property value='objectId' />');" alt="Ver Archivo"> <b><font color="<s:property value="@org.ositran.utils.Constantes@COLOR_DOCUMENTO_ANEXO"/>"> <s:property value="nombreReal"/> </font></b> [<s:property value="usuarioCarga.usuario"/>]<br />
                                           </s:if>          
                                    </s:iterator>
                                                           
                                     <s:iterator value="#session._UPLOADLIST.upload1">               
                                           <s:if test="principal.equals('M')">
                                               <a onclick="verArchivo('<s:property value='idArchivo' />','<s:property value='rutaAlfresco' />', '<s:property value='objectId' />');" alt="Ver Archivo"><b><font color="<s:property value="@org.ositran.utils.Constantes@COLOR_DOCUMENTO_MP_CARGO"/>"><s:property value="nombreReal"/> </font></b> [<s:property value="usuarioCarga.usuario"/>]<br />
                                           </s:if>
                                    </s:iterator>                       
                                                           
                                    <s:iterator value="#session._UPLOADLIST.upload1">               
                                           <s:if test="principal.equals('C')">
                                               <a onclick="verArchivo('<s:property value='idArchivo' />','<s:property value='rutaAlfresco' />', '<s:property value='objectId' />');" alt="Ver Archivo"><b><font color="<s:property value="@org.ositran.utils.Constantes@COLOR_DOCUMENTO_CARGO"/>"><s:property value="nombreReal"/> </font></b> [<s:property value="usuarioCarga.usuario"/>]<br />
                                           </s:if>
                                    </s:iterator>               
                                    
                                    <s:iterator value="#session._UPLOADLIST.upload1">               
                                           <s:if test="principal.equals('Y')">
                                               <a onclick="verArchivo('<s:property value='idArchivo' />','<s:property value='rutaAlfresco' />', '<s:property value='objectId' />');" alt="Ver Archivo"><s:property value="nombreReal"/> [<s:property value="usuarioCarga.usuario"/>] <br />
                                           </s:if>
                                    </s:iterator>  
                                                   
                                                     
                                </td>
                            </tr>
                        </table>
                    </s:if>
                </td>
                <td style="width: 27%;" align="right" valign="top">
                    <!--<div id="tabla12" style="width: 100%;text-align:left;"> -->
                        <table cellpadding="1" cellspacing="1" class="tableForm">
                            
                            <s:if test="documento.ID_CODIGO!=null && documento.ID_EXTERNO==1">
                                <tr style="height:20px;">
                                    <td style="font-size: 11px;font-weight:normal;background-color:#669BCD;color:#ffffff;vertical-align:center;text-align:center;border: 1px #000 solid;">
									   Nro. Tr&aacute;mite
                                    </td>
                                    <td style="background-color:#ffffff;border: 1px #000 solid;width: 250px;">
                                      <b>  <font color="blue"> <s:property value="documento.ID_CODIGO" /> </font> </b>
                                    </td>
                                </tr>
                            </s:if>
                                
                             <s:if test="documento.ID_CODIGO!=null && documento.ID_EXTERNO==0">
                                <tr style="height:20px;">
                                    <td style="font-size: 11px;font-weight:normal;background-color:#669BCD;color:#ffffff;vertical-align:center;text-align:center;border: 1px #000 solid;">
									    Nro. Tr&aacute;mite
                                    </td>
                                    <td style="background-color:#ffffff;border: 1px #000 solid;width: 250px;">
                                      <b>  <font color="blue"> <s:property value="documento.ID_CODIGO" /> </font> </b>
                                    </td>
                                </tr>
                            </s:if>     
                            
                            <s:if test="documento.confidencial.equals('S')">
                                <tr style="height:20px;">
                                    <td width="100px" style="font-size: 11px;font-weight:bold;background-color:#DA1217;color:#ffffff;vertical-align:center;text-align:center;border: 1px #000 solid;" colspan="2">
									Documento Confidencial
                                    </td>
                                </tr>
                            </s:if>
                            <tr style="height:20px;">
                                <td width="100px" style="font-size: 11px;font-weight:normal;background-color:#669BCD;color:#ffffff;vertical-align:center;text-align:center;border: 1px #000 solid;">
								Nro. Documento
                                </td>
                                <td style="background-color:#ffffff;border: 1px #000 solid;width: 250px;">
                                    <s:property value="documento.tipoDocumento.nombre" /> - <s:property value="documento.numeroDocumento" />
                                </td>
                            </tr>
                            <tr style="height:20px;">
                                <td style="font-size: 11px;font-weight:normal;background-color:#669BCD;color:#ffffff;vertical-align:center;text-align:center;border: 1px #000 solid;">
								Nro. Carpeta
                                </td>
                                <td style="background-color:#ffffff;border: 1px #000 solid;width: 250px;">
                                    <s:property value="documento.expediente.nroexpediente" />
                                </td>
                            </tr>
                           
                            <s:if test="objDD.strRazonSocial!=''">
                                <tr style="height:20px;">
                                    <td style="font-size: 11px;font-weight:normal;background-color:#669BCD;color:#ffffff;vertical-align:center;text-align:center;border: 1px #000 solid;">
								Cliente
                                    </td>
                                    <td style="background-color:#ffffff;border: 1px #000 solid;width: 250px;">
                                        <s:property value="objDD.strRazonSocial" />
                                    </td>
                                </tr>
                            </s:if>
                                    <s:else>
                                      <tr style="height:20px;">  
	                                 <td style="font-size: 11px;font-weight:normal;background-color:#669BCD;color:#ffffff;vertical-align:center;text-align:center;border: 1px #000 solid;">
		               		   Cliente
			                 </td>
			                 <td style="background-color:#ffffff;border: 1px #000 solid;width: 250px;">
			               		
			                 </td>
                                      </tr>  
	                       </s:else>     
                        </table>
                   <!-- </div> -->
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
                                          
                                        </td>
                                    </tr>
                                    
                                    
                                    <s:if test="objDD.cEstado == 'P'">  
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
                                    </s:if>   
                                        
                                        
                                    <tr>
                                        <td width="3px"></td>
                                        <td colspan="5">
                                            <s:if test="objDD.strAccion != null && objDD.strAccion!=''">
                                                <b> Acciones:</b> <s:property value='objDD.strAccion'/> <br/>
                                            </s:if>
                                            <s:if test="objDD.strContenido != null && objDD.strContenido!=''">   
                                                <div id="contenido"><b> Proveido:</b> <s:property value='objDD.strContenido' escape='false'/></div>
                                            </s:if>
                                               
                                            <script type="text/javascript">
                                            </script> 
                                            <s:if test="objDD.cEstado == 'A'">  
                                                <div>  
                                                    <p>
                                                        Ud. ha registrado un(a) <font class="negrita"><s:property value="objDD.strTipoDocumento" /></font>
                                                        Nro. <font class="negrita"><s:property value="objDD.strNroDocumento" /></font>
                                                        el dia <font class="negrita"><s:date name="objDD.strFecha" format="dd/MM/yyyy" /></font>
                                                        a las <font class="negrita"><s:date name="objDD.strFecha" format="HH:mm" /></font>
                                                    </p>
                                                </div>  
                                            </s:if>
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
         <%@include file="/pages/NuevoDocumento/busquedaLegajo.jsp" %>     
         <%@include file="/detalle/editarExpediente.jsp" %>  
    </body>
</html>