<?xml version="1.0" encoding="UTF-8"?>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@page import="gob.ositran.siged.config.SigedProperties"%>
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
                   
                   if (dijit.byId("sTipoDocumentoLegajo")) {
                     dijit.byId("sTipoDocumentoLegajo").attr("value", "");
                   }
                   
                   if (dijit.byId("sNroDocumentoLegajo")) {
                     dijit.byId("sNroDocumentoLegajo").attr("value", "");
                   }

                   if (dijit.byId("grdBusquedaLegajo")) {
                     dijit.byId("grdBusquedaLegajo").setStore(null);
                   }  
                   
                   if (dijit.byId("sTipoExpediente_")) {
                     dijit.byId("sTipoExpediente_").attr("value", "");
                   }
                   document.getElementById("idDocumentoExpediente").value = "<s:property value='documento.idDocumento' />"
                   dijit.byId("dlgBusquedaLegajo").show();
               }
           } 
        
           function abrirVentanaHojaReclamacion(){
                var nroTramite = "<s:property value='documento.ID_CODIGO' />";
                var opciones = "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes, width=770, height=485, top=20, left=70";       
                var pagina = "<%=SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.SIGED_LIBRORECLAMACION)%>" + "?a=" + nroTramite + "&b=" + nroTramite.substring(0,4);
                window.open(pagina, "Publico", opciones); 
            }
            
            function abrirVentanaSolicitudInformacion(){
                var nroTramite = "<s:property value='documento.ID_CODIGO' />";
                var opciones = "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes, width=770, height=485, top=20, left=70";       
                var pagina = "<%=SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.SIGED_INFOPUBLICA)%>" + "?a=" + nroTramite + "&b=" + nroTramite.substring(0,4);
                window.open(pagina, "Publico", opciones); 
            }
            
            function verArchivo(idArchivo, url, objectId){
              var fecha = new Date();
              window.open("<%=request.getContextPath()%>/verDocumento.png?idArchivo=" +idArchivo + "&url=" + url + "&objectId="+ objectId  + "&accion=abrirDocumento&fecha=" + fecha.getTime());
            }    
            
            function openDialog(varPagina,varTitulo){
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
                var principal = false;
                var rol = "<s:property value='#session._USUARIO.idRolPerfil' />" + "";
                
                /////////////////////////////////INICIO///////////////////////////
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
                /////////////////////////////////////////FIN//////////////////////////////////////////////
                
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
                        
                         var paraAprobar=<s:property value='paraAprobar' />;
                         var opciones = "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes, width=770, height=485, top=20, left=70";
                         var pagina = "goDerivarUSER.action?arrIdDoc=" + <s:property value='documento.idDocumento' /> + "&sTipoDerivacion=normal&sOpcion="+opcion+"&paraAprobar="+paraAprobar;
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
                            
                            var opciones = "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes, width=770, height=485, top=20, left=70";
                            var pagina = "goDerivarUSER.action?arrIdDoc=" + <s:property value='documento.idDocumento' /> + "&sTipoDerivacion=normal&sOpcion="+opcion;
                            window.open(pagina, "Ventana", opciones);  
                        });            
                }    
           }
            
            
           function imprimirCargo(){
             if (<s:property value='objDD.idExterno' /> == '1'){
               window.open("generarCargoTicket.action?idDocumento=<s:property value='documento.idDocumento' />", "", "width=600,height=500");
             }
            
             if (<s:property value='objDD.idExterno' /> == '0'){
               window.open("generarCargo.action?idDocumento=<s:property value='documento.idDocumento' />", "", "width=600,height=500");
             }
            }
            
            
           
            function abrirVentanaDerivarApoyo(){
                var remitente="<s:property value='objDD.iIdRemitente' />";
                var opciones = "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=700, height=600, top=20, left=70";
                var pagina = "goDerivarApoyo.action?arrIdDoc=" + <s:property value='documento.idDocumento' /> + "";
                pagina+="&objDD.iIdRemitente="+remitente;
                window.open(pagina, "", opciones);
            }
            
            function moverFirma(){
                
                if (confirm("¿Desea mover el documento a la bandeja para firmar?")){
                    var service = new dojo.rpc.JsonService("SMDAction.action");
                    var defered = service.moverFirma(<s:property value='documento.idDocumento' />);
                    defered.addCallback(function(respuesta){
                      if (respuesta == '0'){
                        alert("El documento ha sido movido a la bandeja de Documentos Para Firmar.");
                        showGridInbox(TIPO_GRID_RECIBIDOS);
                      }
                      if (respuesta == '1'){
                         alert("El documento ya tiene un pendiente por firmar.");
                      }
                      if (respuesta == '2'){
                         alert("Vuelva a procesar la opción Mover para Firmar.");
                      }
                      
                    });    
                }    
            }
            
            
            function abrirVentanaCopiarApoyo(){
                var principal = false;
                var rol = "<s:property value='#session._USUARIO.idRolPerfil' />" + "";
                
                
                ////////
                if ("<s:property value='documento.tipoDocumento.idtipodocumento' />" != "<s:property value='@org.ositran.utils.Constantes@SOLICITUD_INFORMACION_PUBLICA'/>" && 
                    "<s:property value='documento.tipoDocumento.idtipodocumento' />" != "<s:property value='@org.ositran.utils.Constantes@HOJA_RECLAMACIONES'/>"){
                        if ("<s:property value='documento.enumerado' />"=="N" && "<s:property value='documento.proyecto' />"!="<s:property value='@org.ositran.utils.Constantes@DOCUMENTO_PROYECTO'/>"){
                          alert("Debe enumerar el documento antes de derivarlo.");
                          return;
                        }

                        if ("<s:property value='documento.proyecto' />"=="<s:property value='@org.ositran.utils.Constantes@DOCUMENTO_PROYECTO'/>" && "<s:property value='documento.enumerado' />"=="N"){
                             <s:if test="destinatarioIgualRemitente == false">
                               if (!confirm("El documento no se encuentra enumerado. ¿Desea continuar con la Derivación?")){      
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
                //////////////////
                
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
                        
                        var opciones = "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes, width=810, height=520, top=20, left=70";
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
                           
                            var opciones = "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes, width=810, height=520, top=20, left=70";
                            var pagina = "goCopiarApoyo.action?arrIdDoc=" + <s:property value='documento.idDocumento' />;
                            window.open(pagina, "Ventana", opciones);   
                        });            
                }
            }

            function loadVirtualDocumentSearch() {
                var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=800, height=450, top=20, left=70";
                var pagina = "goOpenDocumentSearch.action?iIdDoc=" + <s:property value='documento.idDocumento' />;
                window.open(pagina, "", opciones);
            }

            function abrirVentanaSeguimiento() {
                var opciones = "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=no, width=650, height=505, top=50, left=200";
                window.open("goViewSeguimiento.action?iIdDocumento=" + <s:property value='documento.idDocumento' />, "", opciones);
            }

            function abrirVentanaRechazar() {
                if("<s:property value='puedeRechazar'/>"=="S" ) {
                    var opciones = "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes, width=770, height=390, top=20, left=70";
                    var pagina = "goDerivarUSER.action?arrIdDoc=" + <s:property value='documento.idDocumento' /> + "&sTipoDerivacion=normal&sOpcion=rechazar";
                    window.open(pagina, "Ventana", opciones);
                }else {
                    alert("No puede Rechazar este Documento por que no ha sido Previamente Derivado ") ;
                }
            }

            function Abrir_ventanaId() {
                var opciones = "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes, width=600, height=550, top=50,left=200";
                var pagina = "doPlantilla_verNuevo.action?idDocumento=" + <s:property value='documento.idDocumento' /> ;
                window.open(pagina,"",opciones);
            }

            function abrirVentanaAnularDocumento(tipoArchivar) {
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
                     var pagina = "AnularPrevioDocumento_inicioAnular.action?idDocumentoAnular=" + <s:property value='documento.idDocumento' />+ "&tipoArchivar="+tipoArchivar;
                     window.open(pagina, "Ventana", opciones);
                 });
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
            }

            function agregarDocumentoRespuesta() {
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
                	href : "NuevoDocumento_mostrarVista.action?idExpediente="+<s:property value='documento.expediente.id' />+"&idDocumento=" + <s:property value='documento.idDocumento' />+"&tipoTransaccion=A",
                	title: "Nuevo Documento",
                	onClose: function(){
                                accion(2);
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
                if ("<s:property value='documento.enumerado' />"=="S"){
                  alert("El documento ya se encuentra enumerado.");
                  return;
                }
                
                var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=500, height=290, top=50, left=200";
                var pagina = "Numeracion_verVentanaNumeracion.action?idDocumento=" + "<s:property value='documento.idDocumento' />";
                //openDialog(pagina,"Enumerar");
                window.open(pagina,"Numerar Documento",opciones);
            }
            function abrirVentanaFirmar() {
                var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes, width=500, height=290, top=50, left=200";
                var pagina = "Numeracion_verVentanaFirmar.action?idDocumento=" + "<s:property value='documento.idDocumento' />";
                window.open(pagina,"",opciones);
            }

            function abrirHojaRuta(){
            	var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=900, height=700, top=50, left=200";
                var pagina = "ReporteAPN_verHojaRuta.action?idDocumento=" + "<s:property value='iIdDoc' />";
                window.open(pagina,"HojaRuta",opciones);
            }
            
            function verDocumento(){
               
            	var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=900, height=700, top=50, left=200";
                var pagina = "verDocumento.action?idDocumento=" + "<s:property value='iIdDoc' />";
                window.open(pagina,"Metadata",opciones);
            }

            function openMensajeriaWindow(){
                var url = "Mensajeria_mensajeria_winEnvio_abrirEnvio.action?iIdExpediente=" + "<s:property value='objDD.iIdExpediente' />";

                window.open(url, "", "width=600px,height=500px,resizable=1,scrollbars=0");
            }
            
             function abrirHojaFirma(){
            	var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=900, height=650, top=50, left=200";
                var pagina = "ReporteAPN_verHojaFirma.action?idDocumento=" + "<s:property value='iIdDoc' />";
                window.open(pagina,"HojaFirma",opciones);
            }

            function imprimirProveido(){
            	var url = "imprimirProveido.action?iIdDoc=" + "<s:property value='iIdDoc' />";
            	var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=900, height=700, top=50, left=200";
                window.open(url,"",opciones);
            }

           
            function abrirVentanaModificar(){
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
                    
                        var url = "";
                        if(dijit.byId("tabNuevoDocumento")){
                            dijit.byId("tabContainerInbox").removeChild(dijit.byId("tabNuevoDocumento"));
                            dijit.byId("tabNuevoDocumento").destroyDescendants();
                            dijit.byId("tabNuevoDocumento").destroy();
                        }

                        if ("<s:property value="documento.ID_CODIGO"/>"!=null && "<s:property value="documento.ID_EXTERNO"/>"== "1"){
                            url = "NuevoDocumento_mostrarVistaTramite.action";
                        }else{    
                           url = "NuevoDocumento_mostrarVista.action";
                        }

                        var dialog = dijit.byId("dlgProgresBar");
                        dialog.show() ;
                        var newTab = new dojox.layout.ContentPane({
                                id : "tabNuevoDocumento",
                                jsId : "tabNuevoDocumento",
                                closable: true,
                                href : url + "?idExpediente="+<s:property value='documento.expediente.id' />+"&idDocumento=" + <s:property value='documento.idDocumento' />+"&tipoTransaccion=M",
                                title: "Modificar Documento",
                                onClose: function(){
                                        showGridInbox("<s:property value='idGrid' />");
                                        return true;
                                }
                        });
                        dijit.byId("tabContainerInbox").addChild(newTab);
                        dijit.byId("tabContainerInbox").selectChild(newTab);
                        dialog.hide();
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
        				if(!dijit.byId("dlgAnexar")){
	        				new dijit.Dialog({
                                                    id: "dlgAnexar",
                                                    draggable:"true",
                                                    style:"height:" + amplitud +";width:427px;display:none;",
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

            function mostrarSetPrincipalArchivo(idDoc){
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

            function abrirVentanaArchivar(tipoArchivar) {
                var opciones = "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes, width=650, height=440, top=20, left=70";
                var pagina = "Archivar_inicio.action?idDocumento=" + <s:property value='objDD.iIdDocumento' />+ "&tipoArchivar="+tipoArchivar;
                //openDialog(pagina,"Archivar");
                window.open(pagina, "", opciones); 
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
                     var pagina = "Archivar_inicio1.action?idDocumento=" + <s:property value='documento.idDocumento' /> + "&tipoArchivar="+tipoArchivar;
                     window.open(pagina, "Ventana", opciones); 
                 });
            }

            
            function moverSIDECO(){
                if(confirm("¿Desea crear una Consulta en el SIDECO?")){
                    var service = new dojo.rpc.JsonService("SMDAction.action");
                    var defered = service.moverSIDECO(<s:property value='documento.idDocumento' />);
                    defered.addCallback(function(respuesta){
                        if (respuesta == '0'){
                            alert("El documento ha sido creado en el SIDECO.");
                            showGridInbox(TIPO_GRID_RECIBIDOS);
                          }
                    });
                }
            }
            
            function abrirVentanaNotificar() {
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
                if(confirm("¿Desea "+mensaje+" el Documento "+"<s:property value='objDD.strNroDocumento' escape='false' />"+" a su lista de seguimiento?")){
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
        <table width="100%" border="0">
            <tr>
                <td colspan="6" align="left">
                    <div dojoType="dijit.Toolbar">
                        
                        <s:if test="avisopermiso || objDD.historico=='S' || documento.estado == 'C' || documento.expediente.estado == 'N' || idGrid == '22' || (documento.estado == 'T' && documento.flagatendido != null) || flagBusqueda == 'B'"> 
                           
                            <s:if test="#session._RECURSO.HojaRuta">
                                <div dojoType="dijit.form.Button"  onClick="abrirHojaRuta()" iconClass="siged16 iconoHojaRuta">Hoja de Ruta</div>
                                <div dojoType="dijit.form.Button"  onClick="abrirHojaFirma()" iconClass="siged16 iconoHojaRuta">Hoja de Firmas</div> 
                            </s:if>
                              
                            <s:if test="idGrid!=24">    
                                <s:if test="agregar">
                                    <div dojoType="dijit.form.Button"  onClick="guardarSeguimiento(true)" iconClass="siged16 sigedSearch16">Marcar para Seguimiento</div>
                                </s:if>
                                <s:else>
                                    <div dojoType="dijit.form.Button"  onClick="guardarSeguimiento(false)" iconClass="siged16 sigedDelete16">Retirar del Seguimiento</div>
                                </s:else>
                            </s:if>    
                            <s:if test="documento.tipoDocumento.idtipodocumento==@org.ositran.utils.Constantes@TIPO_DOCUMENTO_HOJA_RECLAMACIONES">
                                 <div dojoType="dijit.form.Button" onClick="abrirVentanaHojaReclamacion('<s:property value="documento.tipoDocumento.idtipodocumento" />')" iconClass="siged16 sigedNew16">SAIP/LR</div>
                            </s:if>  
                                 
                             <s:if test="documento.tipoDocumento.idtipodocumento==@org.ositran.utils.Constantes@TIPO_DOCUMENTO_SOLICITUD_INFORMACION">
                                 <div dojoType="dijit.form.Button" onClick="abrirVentanaSolicitudInformacion('<s:property value="documento.tipoDocumento.idtipodocumento" />')" iconClass="siged16 sigedNew16">SAIP/LR</div>
                            </s:if>     
                        </s:if>
                        <s:else>                           
                        	<!-- Cuando se va a aprobar y archivar ------------------------------------------------------------->
                            <s:if test="paraAprobar">
                                <s:if test="#session._RECURSO.UsuFinBtnDer && documento.documentoreferencia == null && (objDD.flagCodigoVirtual == '2' || objDD.flagCodigoVirtual == '0')">
                                    <div dojoType="dijit.form.Button" onClick="abrirVentanaDerivar('paraaprobar')" iconClass="dijitEditorIcon dijitEditorIconRedo">Aprobar</div>
                                </s:if>
                            </s:if>
                            <s:else>
                                <s:if test="#session._RECURSO.UsuFinBtnDer && documento.documentoreferencia == null && #session._USUARIO.idRolPerfil!=6 && (objDD.flagCodigoVirtual == '2' || objDD.flagCodigoVirtual == '0')">
                                    <div dojoType="dijit.form.Button" onClick="abrirVentanaDerivar('reenviar')" iconClass="dijitEditorIcon dijitEditorIconRedo">Derivar</div>
                                </s:if>
                            </s:else>
                            <s:if test="controlDevolver == false">
	                            <s:if test="#session._RECURSO.UsuFinBtnRec && documento.documentoreferencia == null">
	                                <div dojoType="dijit.form.Button" onClick="abrirVentanaRechazar()" iconClass="dijitEditorIcon dijitEditorIconUndo">Devolver</div>
	                            </s:if>
                            </s:if>

                            <!-- Controles para reenviar y devolver ------------------------------------------------------------>

                            <s:if test="documento.documentoreferencia != null && #session._USUARIO.idRolPerfil!=6 && (objDD.flagCodigoVirtual == '2' || objDD.flagCodigoVirtual == '0')">
                            	<div dojoType="dijit.form.Button" onClick="agregarDocumentoRespuesta()" iconClass="siged16 sigedNuevoDoc16">Crear Documento</div>
                            	<div dojoType="dijit.form.Button" onClick="abrirVentanaCopiarApoyo()" iconClass="dijitEditorIcon dijitEditorIconIndent">Derivar M&uacute;ltiple</div>
                            </s:if>

                            <s:if test="#session._RECURSO.UsuFinBtnUpl">
	                            <s:if test="documento.documentoreferencia == null && #session._USUARIO.idRolPerfil!=6 && (objDD.flagCodigoVirtual == '2' || objDD.flagCodigoVirtual == '0')">
		                            <div dojoType="dijit.form.Button" onClick="agregarDocumentoRespuesta()" iconClass="siged16 sigedNuevoDoc16">Crear Documento</div>
		                            <s:if test="documento.documentoreferencia == null && (objDD.flagCodigoVirtual == '2' || objDD.flagCodigoVirtual == '0')">
			                        	<div dojoType="dijit.form.Button" onClick="abrirVentanaCopiarApoyo()" iconClass="dijitEditorIcon dijitEditorIconIndent">Derivar M&uacute;ltiple</div>
			                        </s:if>
	                            </s:if>
	                            <s:else>
	                            	<s:if test="documento.documentoreferencia == null && #session._USUARIO.idRolPerfil!=6 && (objDD.flagCodigoVirtual == '2' || objDD.flagCodigoVirtual == '0')">
		                                <div dojoType="dijit.form.Button" onClick="abrirVentanaCopiarApoyo()" iconClass="dijitEditorIcon dijitEditorIconIndent">Derivar M&uacute;ltiple</div>
		                         </s:if>
	                            </s:else>
                            </s:if>

                            <!-- Trazabilidad ---------------------------------------------------------------------------------->
                            <!--
                            <s:if test="#session._RECURSO.UsuFinBtnTraExp">
                                <div dojoType="dijit.form.Button" onClick="abrirVentanaSeguimiento()" iconClass="dijitEditorIcon dijitEditorIconInsertTable">Trazabilidad</div>
                            </s:if> -->

                            <s:if test="#session._RECURSO.HojaRuta">
                                <div dojoType="dijit.form.Button"  onClick="abrirHojaRuta()" iconClass="siged16 iconoHojaRuta">Hoja de Ruta</div>
                            </s:if>

                            <s:if test="#session._RECURSO.UsuFinBtnUpl && documento.documentoreferencia == null && documento.enumerado == 'N' && documento.proyecto!= null && documento.proyecto!=@org.ositran.utils.Constantes@DOCUMENTO_PROYECTO && destinatarioIgualRemitente == true">
                                <div dojoType="dijit.form.Button"  onClick="abrirVentanaEnumerar()" iconClass="dijitEditorIcon dijitEditorIconInsertOrderedList">Enumerar</div>
                            </s:if>
                                
                            <s:if test="#session._RECURSO.UsuFinBtnUpl && documento.documentoreferencia == null && documento.enumerado == 'N' && documento.proyecto!= null && documento.proyecto==@org.ositran.utils.Constantes@DOCUMENTO_PROYECTO && destinatarioIgualRemitente == false">
                                <div dojoType="dijit.form.Button"  onClick="abrirVentanaEnumerar()" iconClass="dijitEditorIcon dijitEditorIconInsertOrderedList">Enumerar</div>
                            </s:if>   
                                
                            <s:if test="#session._RECURSO.modExpediente  && destinatarioIgualRemitente == true && (objDD.flagCodigoVirtual == '2' || objDD.flagCodigoVirtual == '0')">    
                               <div dojoType="dijit.form.Button"  onClick="abrirVentanaAnularDocumento('anular')" iconClass="siged16 iconoAnular">Anular</div>
                            </s:if>    
                               
                            <s:if test="#session._RECURSO.modExpediente && documento.documentoreferencia == null && destinatarioIgualRemitente == true && (objDD.flagCodigoVirtual == '2' || objDD.flagCodigoVirtual == '0' || objDD.flagCodigoVirtual == '3')">
                                <div dojoType="dijit.form.Button"  onClick="abrirVentanaModificar()" iconClass="dijitEditorIcon dijitEditorIconSave">Modificar</div>
                               <!-- <div dojoType="dijit.form.Button"  onClick="abrirVentanaAnularDocumento('anular')" iconClass="siged16 iconoAnular">Anular</div> -->
                            </s:if>

                            <s:if test="#session._RECURSO.BtnAdjuntarArchivo && documento.documentoreferencia == null ">
                                 <div dojoType="dijit.form.Button"  onClick="mostrarAdjuntarArchivo()" iconClass="siged16 iconoAgregar">Agregar Archivo</div>
                            </s:if>

                            <s:if test="#session._RECURSO.BtnEliminarArchivo && documento.documentoreferencia == null ">
                                  <div dojoType="dijit.form.Button"  onClick="mostrarEliminarArchivo()" iconClass="siged16 iconoRemover">Eliminar Archivo</div>
                            </s:if>

                            <s:if test="#session._RECURSO.BtnAdjuntarArchivo && documento.documentoreferencia != null ">
                                  <div dojoType="dijit.form.Button"  onClick="mostrarAdjuntarArchivo()" iconClass="siged16 iconoAgregar">Agregar Archivo</div>
                            </s:if>

                            <s:if test="#session._RECURSO.BtnEliminarArchivo && documento.documentoreferencia != null ">
                                   <div dojoType="dijit.form.Button"  onClick="mostrarEliminarArchivo()" iconClass="siged16 iconoRemover">Eliminar Archivo</div>
                            </s:if>


                            <s:if test="agregar">
				<div dojoType="dijit.form.Button"  onClick="guardarSeguimiento(true)" iconClass="siged16 sigedSearch16">Marcar para Seguimiento</div>
                            </s:if>
                            <s:else>
                            	<div dojoType="dijit.form.Button"  onClick="guardarSeguimiento(false)" iconClass="siged16 sigedDelete16">Retirar del Seguimiento</div>
                            </s:else>

                            <s:if test="idGrid!=null && (idGrid == 6 || idGrid == 0) && documento.ID_EXTERNO==1 && #session._USUARIO.idRolPerfil==3">
                              <div dojoType="dijit.form.Button"  onClick="imprimirCargo()" iconClass="siged16 iconoImprimir">Imprimir Cargo</div>
                            </s:if>
                                
                            <s:if test="objDD.flagCodigoVirtual == '2' || objDD.flagCodigoVirtual == '0' ">
                              <div dojoType="dijit.form.Button"  onClick="atenderDocumento('atender')" iconClass="siged16 iconoOk">Atender</div>    
	                    </s:if>
                            <div dojoType="dijit.form.Button"  onClick="abrirHojaFirma()" iconClass="siged16 iconoHojaRuta">Hoja de Firmas</div>
                            <s:if test="idGrid!=null && idGrid == 0 && documento.ID_EXTERNO==0 && #session._USUARIO.idRolPerfil!=3 && #session._USUARIO.idRolPerfil!=5 && #session._USUARIO.idRolPerfil!=6">
                                <div dojoType="dijit.form.Button"  onClick="moverFirma()" iconClass="dijitEditorIcon dijitEditorIconUndo">Mover para Firma</div> 
                            </s:if>
                                    
                            <s:if test="documento.tipoDocumento.idtipodocumento==@org.ositran.utils.Constantes@TIPO_DOCUMENTO_HOJA_RECLAMACIONES">
                                 <div dojoType="dijit.form.Button" onClick="abrirVentanaHojaReclamacion('<s:property value="documento.tipoDocumento.idtipodocumento" />')" iconClass="siged16 sigedNew16">Libro de Reclamaciones</div>
                            </s:if>  
                                 
                             <s:if test="documento.tipoDocumento.idtipodocumento==@org.ositran.utils.Constantes@TIPO_DOCUMENTO_SOLICITUD_INFORMACION">
                                 <div dojoType="dijit.form.Button" onClick="abrirVentanaSolicitudInformacion('<s:property value="documento.tipoDocumento.idtipodocumento" />')" iconClass="siged16 sigedNew16">Solicitud de Información Pública</div>
                            </s:if>     
                                 
                            <s:if test="#session._USUARIO.idRolPerfil==4 && #session._USUARIO.idUnidadPerfil==@org.ositran.utils.Constantes@CODIGO_AREA_GAU && #session._USUARIO.idFuncionPerfil==3 && objDD.flagsideco!=1">
                                <div dojoType="dijit.form.Button"  onClick="moverSIDECO()" iconClass="dijitEditorIcon dijitEditorIconUndo">Mover al SIDECO</div>
                            </s:if>     
                                 
                        </s:else>
                             
                        <s:if test="idGrid!=24">         
                            <s:if test="objDD.flagExpediente=='A' || objDD.flagExpediente=='T'">         
                               <div dojoType="dijit.form.Button"  iconClass="siged16 iconoAgregar"   onClick="abrirVentanaLegajo()">Agregar a Expediente</div>       
                            </s:if>
                            <s:if test="objDD.flagExpediente=='C' || objDD.flagExpediente=='T'">   
                              <div dojoType="dijit.form.Button"  iconClass="siged16 sigedNuevoDoc16" onClick="crearLegajo()">Crear Expediente</div>         
                           </s:if>
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
                    <br />
                    <b>Asunto:</b> <s:property value="documento.asunto" />
                    <s:if test="objDD.prioridad != null && objDD.prioridad!=''">
                        <br />  <b>Prioridad:</b> <img src="images/Prioridad_<s:property value="objDD.prioridad" />.png" />
                    </s:if>
                    <s:if test="objDD.strFechaLimiteAtencion != null && objDD.strFechaLimiteAtencion!=''">
                        <br /><b>Fecha Limite:</b> <s:property value="objDD.strFechaLimiteAtencion" />
                        <s:textfield name="strAcc" value="aprobar" cssStyle="display:none" />
                    </s:if>      
                    <s:if test="objDD.strAccion != null && objDD.strAccion!=''">
                       <br /> <b>  Remitente:</b> <s:property value='objDD.strRemitente'/>
                    </s:if>           
                </td>
                <td colspan="2" style="width: 25%;" valign="top">
                    
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
                                               <a onclick="verArchivo('<s:property value='idArchivo' />','<s:property value='rutaAlfresco' />', '<s:property value='objectId' />');" alt="Ver Archivo"> <b><font color="<s:property value="@org.ositran.utils.Constantes@COLOR_DOCUMENTO_PRINCIPAL"/>"><s:property value="nombreReal"/> </font></b> [<s:property value="usuarioCarga.usuario"/>] <br />
                                           </s:if> 
                                    </s:iterator>
                                                   
                                     <s:iterator value="#session._UPLOADLIST.upload1">               
                                           <s:if test="principal.equals('N')">
                                               <a onclick="verArchivo('<s:property value='idArchivo' />','<s:property value='rutaAlfresco' />', '<s:property value='objectId' />');" alt="Ver Archivo"><b><font color="<s:property value="@org.ositran.utils.Constantes@COLOR_DOCUMENTO_ANEXO"/>"><s:property value="nombreReal"/></font></b> [<s:property value="usuarioCarga.usuario"/>] <br />
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
                                               <a onclick="verArchivo('<s:property value='idArchivo' />','<s:property value='rutaAlfresco' />', '<s:property value='objectId' />');" alt="Ver Archivo"><s:property value="nombreReal"/> [<s:property value="usuarioCarga.usuario"/>]<br />
                                           </s:if>
                                    </s:iterator> 
                                                   
                                                    
                                </td>
                            </tr>
                        </table>
                    </s:if>
                </td>
              
                <td style="width: 25%" align="right" valign="top">
                   <!-- <div id="tabla12" style="width: 100%;text-align:left;"> -->
                        <table cellpadding="1" cellspacing="1" border="0" class="tableForm" >
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
                            <tr>
                                <td width="108px;" style="font-size: 10px;font-weight:bold;background-color:#DA1217;color:#ffffff;vertical-align:center;text-align:center;border: 1px #000 solid;" colspan="2">
						Documento Confidencial
                                </td>
                             </tr>
                            </s:if>
                            <tr style="height:20px;">
                                <td width="108px;" style="font-size: 11px;font-weight:normal;background-color:#669BCD;color:#ffffff;vertical-align:center;text-align:center;border: 1px #000 solid;">
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
			               		<s:property value="" />
			               </td>
                                  </tr>      
	                       </s:else>         

                              <s:if test="objDD.desDocumentoOrigen!=null && objDD.desDocumentoOrigen!=''">   
                                 <tr style="height:20px;">
                                    <td style="font-size: 11px;font-weight:normal;background-color:#669BCD;color:#ffffff;vertical-align:center;text-align:center;border: 1px #000 solid;">
								Origen
                                    </td>
                                    <td style="background-color:#ffffff;border: 1px #000 solid;width: 250px;">
                                        <s:property value="objDD.desDocumentoOrigen" />
                                    </td>
                                </tr>
                              </s:if>


                        </table>
                  <!--  </div> -->
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