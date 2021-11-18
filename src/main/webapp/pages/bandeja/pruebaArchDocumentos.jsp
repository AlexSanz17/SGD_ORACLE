<?xml version="1.0" encoding="UTF-8" ?>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es">
	<head>
		<title>Documentos</title>
		
		
      <script type="text/javascript">
         var myidGrid = "<s:property value='idGrid' />";
         var myidExpediente = "<s:property value='objDD.iIdExpediente' />";
         var mydocumentoStor = "<s:property value='documentoStor' />";
         var myidDocumento = "<s:property value='objDD.iIdDocumento' />";
         var opcionMenu = "<s:property value='objDD.opcionMenu' />";
         var origen = '<s:property value="origenDetalleDoc"/>';
         console.debug("(pruebaArchDocumentos.jsp) origen->" + origen);
      </script>
      <script type="text/javascript" src="js/busquedaExpedienteArbol.js"></script>
      <script type="text/javascript" src="js/documentosAdicionales.js"></script>
      <script type="text/javascript" src="js/destruirObjetos.js"></script>          
      
      <script type="text/javascript">         
         destroyWidgetsFromBusquedaExpediente();
         
         function abrirVentanaModificarExp(){
                dojo.xhrPost({
     			url: "goModificarExpediente.action",
     			content:{
     				iIdDoc : "<s:property value='objDD.iIdDocumento' />"
     			},
     			load: function(data){
     				if(!dijit.byId("dlgModificarExp")){
     				     new dijit.Dialog({
     			   	     id: "dlgModificarExp",
             			     draggable:"true",
             			     style:"height:160px;width:600px;display:none;",
             			     title:"Modificar Datos",
     		              	     onClose: dojo.hitch(this, function(){
     		              		dijit.byId("dlgModificarExp").hide();        		        		
     		              		dijit.byId("dlgModificarExp").destroyRecursive();
				     })									        			    
             			});
     				}
     			
                                dijit.byId("dlgModificarExp").attr("content", data);
     				dijit.byId("dlgModificarExp").show();
     			}
     		});
         }
         function abrirVentanaTrazabilidadExpediente(){
        	 var opciones = "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=no, width=850, height=600, top=50, left=120";
             var pagina ="verTrazabilidadExpediente.action?iIdDocumento=" + <s:property value='objDD.iIdDocumento' />;
             window.open(pagina, "", opciones);
         }
         function abrirVentanaHojaRutaExpediente(){
        	 var opciones = "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=no, width=850, height=600, top=50, left=120";
        	 var pagina ="ReporteAPN_verHojaRutaExpediente.action?idDocumento=" + <s:property value='objDD.iIdDocumento' />;
        	 window.open(pagina, "", opciones);
         }
      </script>
	</head>
	<body class="soria">
                <div dojoType="dijit.Toolbar">
                        <s:if test="desactivado == false">
                                <div dojoType="dijit.form.Button" onClick="abrirVentanaModificarExp()" iconClass="dijitEditorIcon dijitEditorIconSave">Modificar Carpeta</div>
                        </s:if>
                        <div dojoType="dijit.form.Button" onClick="abrirVentanaHojaRutaExpediente()" iconClass="siged16 iconoHojaRuta">Hoja de Ruta de la Carpeta</div>
                </div>
	  	<div id="divTreeExpediente<s:property value='idGrid' />" class="arbol"></div>
		<div id="displayIdExpediente<s:property value='idGrid' />" class="detalleArbol"></div>
		<%@ include file="/pages/tramite/busquedaExpediente.jsp" %>
	</body>
</html>