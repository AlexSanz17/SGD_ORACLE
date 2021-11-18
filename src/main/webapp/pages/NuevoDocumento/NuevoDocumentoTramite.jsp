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
          
       <style type="text/css">
         @import "js/dojo/dijit/themes/soria/soria.css";
         @import "js/dojo/dojox/grid/resources/soriaGrid.css";
         @import "css/grid.css";
         @import "css/estilo.css";

         body{
         	padding: 2px;
         }

         #campos{
         	width: 98%;
         	margin: 5px auto;
         	border: 2px solid #669BCD;
         	background-color: #BFD9F1;
         	color: #1B1B1B;
         	font-size: 0.8em;
         	height: 260px;
         }

         #docRef{
         	float: left;
         	width: 170px;
                
         }
         
        

      </style>         
       
      <script type="text/javascript">
        
          
         var TIPO_TRANSACCION       = "<s:property value='tipoTransaccion' />"; 
         var PROPIETARIO_EXPEDIENTE  = "<s:property value='expediente.idpropietario.idusuario' />";
         var AUTOR_DEFAULTID        = "<s:property  value='#session._USUARIO.nombres' escape='false' /> <s:property  value='#session._USUARIO.apellidos' escape='false' />";
         var DOCUMENTO_ID           = "<s:property value='idDocumento' />";
         var ND_NUMERODOCUMENTO     = "<s:property value='documento.tipoDocumento.nombre' escape='false' /> - <s:property value='documento.numeroDocumento' escape='false' />";
         var UNIDAD_USUARIO     = "<s:property value='#session._USUARIO.idUnidadPerfil' />";
         var UNIDAD_TRAMITE     = "<s:property value='@org.ositran.utils.Constantes@UNIDAD_TRAMITE' />";
         var TIPO_INSTITUCION_PERSONA = "<s:property value='@org.ositran.utils.Constantes@TIPO_INSTITUCION_PERSONA' />";
         var TIPO_INFORME_CONJUNTO = "<s:property value='@org.ositran.utils.Constantes@TIPO_INFORME_CONJUNTO' />";
         var CLIENTE_OSINERGMIN_ID  = "<s:property value='@org.ositran.utils.Constantes@CLIENTE_OSINERGMIN_ID' />";
         var USUARIOLOGEADO          = "<s:property value='#session._USUARIO.idUsuarioPerfil' />";
         var EXPEDIENTE_REFERENCIA = "<s:property value='idExpediente' />";
         var ORIGEN_EXPEDIENTE = "<s:property value='origenExpediente' />";
         var DOCUMENTO_REFERENCIA = "<s:property value='documento.tipoDocumento.nombre' escape='false'/> - <s:property value='documento.numeroDocumento' escape='false'/>";
         var fsProcesoFocused = 0;
         var strBusquedaExpedienteAbierto;
         var iExpedientesAbiertos = 0;
         var bIsPosting = false;
         var nombreProceso = "";
         var serviceAdjunto = new dojo.rpc.JsonService("SMDAction.action"); 
         var jsonStoreAdjuntos = null;
         var COD_PERSONA_JURIDICA_INSTITUCION = "<s:property value='@org.ositran.utils.Constantes@COD_PERSONA_JURIDICA_INSTITUCION' />";
         var COD_PERSONA_JURIDICA_EMPRESA =     "<s:property value='@org.ositran.utils.Constantes@COD_PERSONA_JURIDICA_EMPRESA' />";
         var TRAMITE = "<s:property value='@org.ositran.utils.Constantes@COD_TRAMITE_EXTERNO'/>";
         var PRIORIDAD_NORMAL = "<s:property value='@org.ositran.utils.Constantes@PRIORIDAD_NORMAL' />";
         
         
         if ("<s:property value='documento.idDocumento' />" == ""){
             jsonStoreAdjuntos = new dojo.data.ItemFileWriteStore({url: "setearAdjuntos.action"});    
         }else{
             serviceAdjunto.leerAdjuntos("<s:property value='documento.idDocumento' />").addCallback(function(objJSON) {
                                    dijit.byId("edGridAdjunto").setStore(new dojo.data.ItemFileWriteStore({
                                        data : objJSON
                                    }));
                                });   
         }
          
        var estructura = [
                        {
				field: 'idId',
				name: 'idId',
                                hidden: true
			},
			{
				field: 'tipoAdjunto',
				name: 'Tipo',
			        hidden: true
			},
                        {
				field: 'desTipo',
				name: 'Tipo',
				width: "110px"
			},
                        {
			    field: 'desOrigCop',
			    name: 'Orig/Cop',
                            width: "85px"
			},
                        
                        {
			    field: 'origcop',
			    name: 'Orig/Cop',
                             hidden: true
			},
                        
                        {
			    field: 'nro',
			    name: 'Nro',
                            width: "55px"
			}
                        
			];
                    
                    
                        new dojox.grid.DataGrid({
				         id               : "edGridAdjunto",
				         jsId             : "edGridAdjunto",
				         store            : jsonStoreAdjuntos,
				         structure        : estructura,
				         style	           : "width:274px; height:85px;"
				      }, "fsGrid").startup();          
        
         
         
      </script>
      <script type="text/javascript" src="js/busquedaExpediente.js"></script>
      <script type="text/javascript" src="js/siged/upload.js"></script>
      <script type="text/javascript" src="js/siged/siged.string.js"></script>
      <script type="text/javascript" src="js/siged/NuevoDocumentoTramite.js"></script> 
      <script type="text/javascript" src="js/siged/busquedaExpedienteAbierto.js"></script> 
      <script type="text/javascript" src="js/destruirObjetos.js"></script>
    
      <script type="text/javascript">
        destroyWidgetsFromBusquedaExpediente();
        destroyControlesTramite();
         
        function descargarPlantillaCargo(){
            var fecha = new Date();
            window.location.href = "<%=request.getContextPath()%>/verDocumento.png?accion=descargarCargo&fecha=" + fecha;
        } 
         
        function activarControlPlazo(flag){
            if (flag == '0'){ 
                dojo.byId("tbPlazoDiaTramite").style.display = "";
                dojo.byId("tbPlazoFechaTramite").style.display = "none";
            }else{  
                dojo.byId("tbPlazoDiaTramite").style.display = "none";
                dojo.byId("tbPlazoFechaTramite").style.display = "";
            }
        }
         
        function agregarPara(){
           var posicionInicial = dijit.byId('objDD.idDestinatarioTramite').attr('displayedValue').trim().indexOf('[') + 1;
           var posicionFinal = dijit.byId('objDD.idDestinatarioTramite').attr('displayedValue').trim().length - posicionInicial - 1;
           
           if (dijit.byId('objDD.idDestinatarioTramite').attr("value").trim()== ""){
               alert("Debe seleccionar un destinatario");
               return;
           }
           
           var datos = document.getElementById("idListaPara");
           
           if (datos.options.length == 1){
               alert("Solo puede agregar un destinatario.");
               return;
           }
            
           for (var i=0; i< datos.options.length;i++){
               if (datos.options[i].value == dijit.byId('objDD.idDestinatarioTramite').attr("value").trim().toUpperCase()){
                  alert("El destinatario ya se encuentra agregado.");
                  return;
               }
	   }
           
           
           $("#idListaPara").append($("<option></option>")
                  .attr("value", dijit.byId('objDD.idDestinatarioTramite').attr("value"))
                  .text(dijit.byId('objDD.idDestinatarioTramite').attr('displayedValue').trim().substr(posicionInicial, posicionFinal)));
        
           
        }
        
        function agregarCC(){
           var posicionInicial = dijit.byId('objDD.idDestinatarioTramite').attr('displayedValue').trim().indexOf('[') + 1;
           var posicionFinal = dijit.byId('objDD.idDestinatarioTramite').attr('displayedValue').trim().length - posicionInicial - 1;
           
           if (dijit.byId('objDD.idDestinatarioTramite').attr("value").trim()== ""){
               alert("Debe seleccionar un destinatario");
               return;
           }
           
           var datos = document.getElementById("idListaCC");
           for (var i=0; i< datos.options.length;i++){
               if (datos.options[i].value == dijit.byId('objDD.idDestinatarioTramite').attr("value").trim().toUpperCase()){
                  alert("El destinatario ya se encuentra agregado.");
                  return;
               }
	   }
           $("#idListaCC").append($("<option></option>")
                  .attr("value", dijit.byId('objDD.idDestinatarioTramite').attr("value"))
                  .text(dijit.byId('objDD.idDestinatarioTramite').attr('displayedValue').trim().substr(posicionInicial, posicionFinal)));
        
        }
        
        function borrarPara(){
            var bandera = false;
            
            $("#idListaPara option:selected").each(function(){
              bandera = true;     
            });
            
            if (!bandera){
              alert("Debe seleccionar un destinatario.");
              return;
            }
            
            $("#idListaPara option:selected").each(function() {
                   $(this).remove();
            }); 
        }
        
        function borrarCC(){
            var bandera = false;
            
            $("#idListaCC option:selected").each(function(){
              bandera = true;     
            });
            
            if (!bandera){
              alert("Debe seleccionar un destinatario.");
              return;
            }
            
            $("#idListaCC option:selected").each(function() {
                   $(this).remove();
            }); 
        }
         
     
          function agregar_(){
            dijit.byId("btn15").attr('disabled', true);
            var valor = "";
            
           
            if (dijit.byId("idTipoAdjuntoTramite").value.trim()==""){
                alert("Debe seleccionar el tipo de documento adjunto.");
                dijit.byId("btn15").attr('disabled', false);
                return;
            }
            
            if (dijit.byId("nroTramite").value.trim()==""){
               alert("Debe ingresar el nro de documentos adjuntos");
               dijit.byId("btn15").attr('disabled', false);
               return;
            }
            
                    
            if (Siged.Forms.radiobuttonGetCheckedByName("idTipoCopiaOriginal") == "0"){
                valor = "0";
            }else{
                valor = "1";
            }
                 
            serviceAdjunto.agregarAdjunto(dijit.byId("idTipoAdjuntoTramite").value, valor, dijit.byId("nroTramite").value).addCallback(function(objJSON) {
                                    dijit.byId("edGridAdjunto").setStore(new dojo.data.ItemFileWriteStore({
                                        data : objJSON
                                    }));
                                });
                                
            dijit.byId("nroTramite").attr("value","");                    
            dijit.byId("btn15").attr('disabled', false);
           
          }
         
         function eliminar_(){
             dijit.byId("btn16").attr('disabled', true);
             var item = null;
             var grid = dijit.byId("edGridAdjunto");
             var items = grid.selection.getSelected();
            
             if (items.length>0 && items[0]){
                 item = items[0];
               
                  serviceAdjunto.eliminarAdjunto(item.idId+"").addCallback(function(objJSON) {
                                    dijit.byId("edGridAdjunto").setStore(new dojo.data.ItemFileWriteStore({
                                        data : objJSON
                                    }));
                    });
             }else{
                 alert("Tiene que seleccionar un elemento");
                 dijit.byId("btn16").attr('disabled', false);
                 return false;
             }
             
             dijit.byId("btn16").attr('disabled', false);
             
         }
         
         function llenarCamposRecepcionVirtual(){
             tipoClienteRequerido("0", true);
             tipoClienteRequerido("1", false);
             tipoClienteRequerido("2", false);
             
             dojo.byId("tbNroFoliosPIDETramite").style.display = "";  
             dijit.byId("objDD.iNroFoliosPIDETramite").attr("required", true);
             dojo.byId("tbDetalleAdjuntosTramite").style.display = "none";
             dojo.byId("tbAdjuntosGrilla").style.display = "none";
             
             dijit.byId("objDD.iNroFoliosTramite").attr("required", false);
             dijit.byId("objDD.iNroFoliosOriginalesTramite").attr("required", false);
             
             dijit.byId("objDD.strAsuntoTramite").setValue("<s:property value='recepcionVirtual.vasu' escape='false'/>");
             dijit.byId("objDD.strAsuntoTramite").attr("readOnly", true);
               
             dijit.byId("objDD.strNroDocumentoTramite").setValue("<s:property value='recepcionVirtual.vnumdoc' escape='false'/>");
             dijit.byId("objDD.strNroDocumentoTramite").attr("readOnly", true);
             
             var jsonStoreInstitucion = new dojo.data.ItemFileWriteStore({url: 'obtenerCliente.action?iIdCliente=' + '<s:property value='objDD.iIdCliente'/>'}); 
             jsonStoreInstitucion.close();
             jsonStoreInstitucion.fetch();
             
             dijit.byId('objDD.iIdInstitucionTramite').store=jsonStoreInstitucion; 
             dijit.byId("objDD.iIdInstitucionTramite").attr("value","<s:property value='objDD.iIdCliente' />");
             dijit.byId("objDD.iIdInstitucionTramite").attr("readOnly", true);
             
             dijit.byId("objDocumento.tipoDocumento.idtipodocumentoTramite").attr("value", "<s:property value='objDD.strTipoDocumento' />");
             dijit.byId("objDocumento.tipoDocumento.idtipodocumentoTramite").attr("readOnly", true);
             
             /*
             dijit.byId("objDD.iNroFoliosTramite").attr("value", "s:property value='recepcionVirtual.snumfol' />");
             dijit.byId("objDD.iNroFoliosOriginalesTramite").attr("value", "s:property value='recepcionVirtual.snumfol' />"); 
             dijit.byId("objDD.iNroFoliosTramite").attr("readOnly", true);
             dijit.byId("objDD.iNroFoliosOriginalesTramite").attr("readOnly", true);
             */
                     
             dijit.byId("objDD.iNroFoliosPIDETramite").attr("value", "<s:property value='recepcionVirtual.snumfol' />"); 
             dijit.byId("objDD.iNroFoliosPIDETramite").attr("readOnly", true);
             
             dijit.byId("objDD.desUnidadOrganicaTramite").setValue("<s:property value='recepcionVirtual.sidrecext.vuniorgrem' escape='false' />"); 
             dijit.byId("objDD.desUnidadOrganicaTramite").attr("readOnly", true);
             
             var remitente = "<s:property value='documento.desRemitente'/>";
             var resultado = "";
             var valor = "";
             
             if (remitente.substring(0,4) == "0000"){
                dijit.byId("objDD.idPersonaInstitucionTramite").setValue("<s:property value='documento.desRemitente' escape='false'/>".substr(4,remitente.lenght));
             }else{
                resultado = "[" + remitente + "]";  
             }    
             
             if ("<s:property value='documento.desRemitente'/>" != ""){
                 if (remitente.substring(0,4) == "0000"){
                   dijit.byId("objDD.idPersonaInstitucionTramite").attr("readOnly", true);
                 }  
             }
            
             if ("<s:property value='recepcionVirtual.sidrecext.ctipdociderem' />" == '1'){
               valor = "DNI";
             } 
             if ("<s:property value='recepcionVirtual.sidrecext.ctipdociderem' />" == '2'){
               valor = "Carnet de Extranjeria";
             }
             
             document.getElementById("datosRemitente").innerHTML = "["  + "<s:property value='recepcionVirtual.vuniorgdst' />" + "]" + "["  + "<s:property value='recepcionVirtual.vnomdst' />" + "]" + "[" + "<s:property value='recepcionVirtual.vnomcardst' />"  +"]";		
             document.getElementById("datosRUC").innerHTML = "[" + "<s:property value='recepcionVirtual.sidrecext.vrucentrem' />"  + "]" + "[" + "<s:property value='recepcionVirtual.vnomentemi' />"  + "]";
             document.getElementById("datosDocumento").innerHTML = "[" + valor + "]" + "[" + "<s:property value='recepcionVirtual.sidrecext.vnumdociderem' />"  + "]";
             document.getElementById("resultado").innerHTML = resultado;
         }
          
         function llenarCamposModificarRecepcionVirtual(){ 
            llenarCampos();
            dojo.byId("tbNroFoliosPIDETramite").style.display = "";  
            dijit.byId("objDD.iNroFoliosPIDETramite").attr("value", "<s:property value='recepcionVirtual.snumfol' />");
            dijit.byId("objDD.iNroFoliosPIDETramite").attr("required", true);
            
            dijit.byId("objDD.iNroFoliosTramite").attr("required", false);
            dijit.byId("objDD.iNroFoliosOriginalesTramite").attr("required", false);
            
            dijit.byId("objDD.desUnidadOrganicaTramite").attr("readOnly", true);
            dijit.byId("objDD.iNroFoliosPIDETramite").attr("readOnly", true);
          //  dijit.byId("objDD.iNroFoliosOriginalesTramite").attr("readOnly", true);
          //  dijit.byId("objDD.iNroFoliosTramite").attr("readOnly", true);
            dijit.byId("objDocumento.tipoDocumento.idtipodocumentoTramite").attr("readOnly", true);
            dijit.byId("objDD.iIdInstitucionTramite").attr("readOnly", true);
            dijit.byId("objDD.strAsuntoTramite").attr("readOnly", true);
            dijit.byId("objDD.strNroDocumentoTramite").attr("readOnly", true);
            if ("<s:property value='recepcionVirtual.sidrecext.cflgest' />" == "R" || "<s:property value='recepcionVirtual.sidrecext.cflgest' />" == "O"){
               dijit.byId("objDD.esTipoRecepcionTramite").attr("readOnly", true);
               dijit.byId("objDD.strObservacionTramite").attr("readOnly", true);
            }
            
            var valor = "";
            
            if ("<s:property value='recepcionVirtual.sidrecext.ctipdociderem' />" == '1'){
               valor = "DNI";
            } 
            if ("<s:property value='recepcionVirtual.sidrecext.ctipdociderem' />" == '2'){
               valor = "Carnet de Extranjeria";
            }
            document.getElementById("datosRemitente").innerHTML = "["  + "<s:property value='recepcionVirtual.vuniorgdst' />" + "]" + "["  + "<s:property value='recepcionVirtual.vnomdst' />" + "]" + "[" + "<s:property value='recepcionVirtual.vnomcardst' />"  +"]";		
            document.getElementById("datosRUC").innerHTML = "[" + "<s:property value='recepcionVirtual.sidrecext.vrucentrem' />"  + "]" + "[" + "<s:property value='recepcionVirtual.vnomentemi' />"  + "]";
            document.getElementById("datosDocumento").innerHTML = "[" + valor + "]" + "[" + "<s:property value='recepcionVirtual.sidrecext.vnumdociderem' />"  + "]" ;
         }    
 
         function llenarCampos(){ //MODIFICAR
                document.getElementById("mostrarExpedientesAbiertos").value = "HIDE";
                dijit.byId("objDD.strNroDocumentoTramite").setValue("<s:property value='documento.numeroDocumento' escape='false' />");
                dijit.byId("objDD.esTipoRecepcionTramite").setValue("<s:property value='documento.recepcionado' />");
                document.getElementById("objDD.iIdDocumento").value = "<s:property value='idDocumento' />";
                dijit.byId("objDD.iIdSerieTramite").attr("value", "<s:property value='expediente.serie.idserie' />");
                
                if(dijit.byId("objDocumento.expediente.idexpedienteTramite")){
        	   dijit.byId("objDocumento.expediente.idexpedienteTramite").setValue("<s:property value='expediente.id' />");
		}
        	else if(dijit.byId("objDD.iIdExpedienteTramite")){
        	   dijit.byId("objDD.iIdExpedienteTramite").setValue("<s:property value='expediente.id' />");
        	}
                
                fsProcesoFocused = 0;
        	expedienteNuevo = false;
                dijit.byId("objDD.asuntoExpedienteTramite").setValue("<s:property value='expediente.getAsuntoHTML()' escape='false'/>");
	        dijit.byId("objDD.observacionExpedienteTramite").setValue("<s:property value='expediente.getObservacionHTML()' escape='false'/>");
                dijit.byId("sNroExpedienteTramite").attr("value", "<s:property value='expediente.nroexpediente' />");
              
                dijit.byId("objDD.prioridadTramite").attr("value","<s:property value='documento.prioridad' />");
                dijit.byId("objDD.strReferenciaTramite").setValue("<s:property value='documento.referenciados'  escape='false' />");
                dijit.byId("objDocumento.tipoDocumento.idtipodocumentoTramite").attr("value", "<s:property value='documento.tipoDocumento.idtipodocumento' />");
                dijit.byId("objDD.confidencialTramite").attr("value","<s:property value='documento.confidencial' />");
                dijit.byId("objDD.strObservacionTramite").setValue("<s:property value='documento.getObservacionHTML()' escape='false'/>");
                dijit.byId("objDD.strAsuntoTramite").setValue("<s:property value='documento.getAsuntoHTML()' escape='false'/>");
                dijit.byId("objDD.iNroFoliosTramite").attr("value","<s:property value='documento.numeroFolios'/>");
               
                dijit.byId("objDD.iNroFoliosOriginalesTramite").attr("value","<s:property value='documento.numeroFoliosOriginales'/>");
                dijit.byId("objDD.iNroFoliosCopiasTramite").attr("value","<s:property value='documento.numeroFoliosCopias'/>"==null?"":"<s:property value='documento.numeroFoliosCopias'/>");
                dijit.byId("objDD.iNroFoliosDigitalizadosTramite").attr("value","<s:property value='documento.imagenesDigitalizadas'/>"==null?"":"<s:property value='documento.imagenesDigitalizadas'/>");
                
                dijit.byId("objDD.iPlazoDiaTramite").attr("value", "<s:property value='documento.plazo'/>"==null?"":"<s:property value='documento.plazo'/>");
                dijit.byId("objDD.asuntoExpedienteTramite").attr("readOnly", true);
                dijit.byId("objDD.observacionExpedienteTramite").attr("readOnly", true);
                dijit.byId("objDD.iIdSerieTramite").attr("readOnly", true);
               
                if (dijit.byId("objDD.iPlazoDiaTramite").value!=''){
                    service.getFechaLimite(dijit.byId("objDD.iPlazoDiaTramite").value).addCallback(function(valor){
                        dijit.byId('sFechaPlazoTramite').attr("value", valor);    
                    });
                }else{
                    dijit.byId('sFechaPlazoTramite').attr("value", "");
                }
                
                if ("<s:property value='documento.codTipoInstitucion'/>"!=null && "<s:property value='documento.codTipoInstitucion'/>"!="" &&
                    ("<s:property value='documento.codTipoInstitucion'/>"=="1" || "<s:property value='documento.codTipoInstitucion'/>"=="2")){
                        if ("<s:property value='documento.codTipoInstitucion' />"== COD_PERSONA_JURIDICA_INSTITUCION){
                           dojo.query("input[name='objDD.idTipoCliente']").forEach(function(node) {
                             if (node.value=="0"){
                                dijit.byId(node.id).attr("checked", true);
                                mostrarTipoCliente(node.value);
                             }
                           }); 
                           
                           var jsonStoreInstitucion = new dojo.data.ItemFileWriteStore({url: 'obtenerCliente.action?iIdCliente=' + '<s:property value='documento.ID_CLIENTE'/>'}); 
                      
                           jsonStoreInstitucion.close();
                           jsonStoreInstitucion.fetch();
                           
                           dijit.byId('objDD.iIdInstitucionTramite').store=jsonStoreInstitucion; 
                           dijit.byId("objDD.iIdInstitucionTramite").attr("value","<s:property value='documento.ID_CLIENTE' />");
                           
                           dijit.byId("objDD.codCargoPersonaInstitucionTramite").setValue("<s:property value='documento.desCargoRemitente' escape='false'/>");    
                           dijit.byId("objDD.idPersonaInstitucionTramite").setValue("<s:property value='documento.desRemitente' escape='false'/>");
                           dijit.byId("objDD.desUnidadOrganicaTramite").setValue("<s:property value='documento.desUnidadRemitente' escape='false' />"); 
                           dijit.byId("objDD.esTipoRecepcionTramite").attr("value", "<s:property value='documento.recepcionado' />")
                        }
                        
                        if ("<s:property value='documento.codTipoInstitucion' />"== COD_PERSONA_JURIDICA_EMPRESA){
                           dojo.query("input[name='objDD.idTipoCliente']").forEach(function(node) {
                             if (node.value=="2"){
                                dijit.byId(node.id).attr("checked", true);
                                mostrarTipoCliente(node.value);
                             }
                           });
                           
                           var jsonStoreEmpresa = new dojo.data.ItemFileWriteStore({url: 'obtenerCliente.action?iIdCliente=' + '<s:property value='documento.ID_CLIENTE'/>'}); 
                      
                           jsonStoreEmpresa.close();
                           jsonStoreEmpresa.fetch();
                           
                           dijit.byId('objDD.iIdEmpresaTramite').store=jsonStoreEmpresa; 
                           dijit.byId("objDD.iIdEmpresaTramite").attr("value","<s:property value='documento.ID_CLIENTE' />");
                           dijit.byId("objDD.codCargoPersonaEmpresaTramite").setValue("<s:property value='documento.desCargoRemitente' escape='false'/>");   
                           dijit.byId("objDD.idPersonaEmpresaTramite").setValue("<s:property value='documento.desRemitente' escape='false' />"); 
                        }
                    
                }else{
                        dojo.query("input[name='objDD.idTipoCliente']").forEach(function(node) {
                             if (node.value=="1"){
                                dijit.byId(node.id).attr("checked", true);
                                mostrarTipoCliente(node.value);
                             }
                        });
                        
                        var jsonStorePersona = new dojo.data.ItemFileWriteStore({url: 'obtenerCliente.action?iIdCliente=' + '<s:property value='documento.ID_CLIENTE'/>'}); 
                        jsonStorePersona.close();
                        jsonStorePersona.fetch();
                       // document.getElementById("cargoPersona").value = "<s:property value='documento.codCargoRemitente' />";
                        dijit.byId("objDD.codCargoPersonaNaturalTramite").setValue("<s:property value='documento.desCargoRemitente' escape='false'/>");
                        dijit.byId('objDD.idPersonaNaturalTramite').store=jsonStorePersona; 
                        dijit.byId("objDD.idPersonaNaturalTramite").attr("value","<s:property value='documento.ID_CLIENTE' />");
                }
        
               //  dojo.byId("tbAdjuntosTramite").style.display = "";
               //  dojo.byId("tbAdjuntosGrilla").style.display = "";  
               
         }
         
         dojo.addOnLoad(function() {    
              crearBotonAdjuntar();
         });
            
        
      </script>
   </head>
   <body>
      <div class="margen5PX">
     <!--     <div id="btnTramite" > </div> --> 
         <button dojoType="dijit.form.Button"
                 type="button"
                 id="btnRegistrarDocumentoTramiteTop"
                 jsId="btnRegistrarDocumentoTramiteTop"
                 iconClass="siged16 sigedSave16"
                 onClick="submitForm"
                 showLabel="true">Registrar Documento</button>
         <!-- <div id="divShowFile2"></div> --> 
      </div>
      <form dojoType="dijit.form.Form" id="frmNuevoDocumentoTramite" jsId="frmNuevoDocumentoTramite" action="NuevoDocumentoEnviarArchivo.action" method="post">
           <input type="hidden" id="listReferenciados" name="listReferenciados"/>
           <input type="hidden" id="raiz" name="raiz"/>
           <input type="hidden" id="persona" value="" name="persona"/>
            <input type="hidden" id="cargoPersona" value="" name="cargoPersona"/>
           <input type="hidden" id="entidad" value="" name="entidad"/>
           <input type="hidden" id="mostrarExpedientesAbiertos" value="SHOW" name="mostrarExpedientesAbiertos"/>
           <input type="hidden" id="documentoEnumerado" value="" name="documentoEnumerado"/>
           <input type="hidden" id="objDD.iIdDocumento" value="" name="objDD.iIdDocumento" />
           <input type="hidden"  name="objDD.tipoTransaccion"  value="<s:property value='tipoTransaccion' />"/>
           <input type="hidden"  name="objDD.codigoVirtual"  value="<s:property value='codigoVirtual' />"/>
           <input type="hidden"  name="objDD.opcion" value="<s:property value='@org.ositran.utils.Constantes@COD_TRAMITE_EXTERNO' />"/>
           <input type="hidden" id="objDD.listaDerivacionPara" name="objDD.listaDerivacionPara" value=""/>
           <input type="hidden" id="objDD.listaDerivacionCC" name="objDD.listaDerivacionCC" value=""/>
           
            <table width="100%" border="0">
               
                 <tr>
                   <td>
                      <div class="marcoForm margen5PX">
                         <table width="100%" border="0">
                             <tbody id="tbClienteTramite">
                              
                               <tr>
                                  <td colspan="3" class="titulo">REMITENTE</td>
                               </tr>
                                 
                                <tr> 
                                    <td>Tipo</td>
                                    <td>
                                         <table>
                                           <tr> 
                                               <td class="label">
                                                   <div id="id1b"> 
                                                      <input id="fsClienteInstitucionTramite"/> Entidades
                                                    <!--  <label for="fsClienteInstitucionTramite">Entidades</label> -->
                                                   </div>

                                             </td>
                                             <td class="label">
                                                   <div id="id1bE"> 
                                                      <input id="fsClienteEmpresaTramite"/> Empresa
                                                    <!--  <label for="fsClienteEmpresaTramite">Empresa</label> -->
                                                   </div>

                                             </td>  

                                             <td class="label">
                                                 <div id="id1cc"> 
                                                    <input id="fsClientePersonaTramite"/> Persona Natural
                                                  <!--  <label for="fsClientePersonaTramite">Persona Natural</label> -->
                                                 </div>
                                             </td>  

                                             <td class="label">
                                                  &nbsp;&nbsp;&nbsp;
                                                  <button dojoType="dijit.form.Button" type="button" jsId="btnNuevoCliente" id="btnNuevoCliente" onClick="openNuevoCliente()"><img src="images/sigedIconos/Agregar.png" width="10px;" height="10px;" border="0"/></button>
                                                  <button dojoType="dijit.form.Button"  type="button" jsId="btnModificarCliente" id="btnModificarCliente" onClick="openModificarCliente()"><img src="images/sigedIconos/HojaRuta.png" width="10px;" height="10px;" border="0"/></button>
                                               </td>  

                                           </tr>
                                       </table>                  
                                    </td>  
                                </tr>    
                            </tbody>
                             
                           <tbody id="tbInstitucionTramite">    
                            <tr>
                              <td>Institución</td>
                              <td class="label" colspan="2">
                                 <div id="fsInstitucionTramite" name="fsInstitucionTramite" style="width:200px;"></div>
                                 <span id="datosRUC" style="color:blue"></span>
                              </td>
                              <td colspan="1"> </td>
                           </tr>
                                       
                            <tr> 
                                <td> 
                                    <div>Persona Remitente </div>
                                    
                                </td>
                             <td class="label" colspan="2" >  
                                  <div id="fsPersonaInstitucionTramite" name="fsPersonaInstitucionTramite" style="width:200px;"></div>
                                  <span id="datosDocumento" style="color:blue"></span>
                                  <span id="resultado" style="color:red"></span>
                             </td>
                           </tr> 
                            
                             <tr> 
                              <td>Cargo</td>
                             <td class="label" colspan="2">
                                  <div id="fsCargoPersonaInstitucionTramite" name="fsCargoPersonaInstitucionTramite" style="width:200px;"></div>
                                  
                              </td>
                           </tr> 	
                               
                           <tr> 
                              <td>Unidad Orgánica</td>
                             <td class="label" colspan="2"> 
                                  <div id="fsUnidadOrganicaTramite" name="fsUnidadOrganicaTramite" style="width:200px;"></div>
                              </td>
                           </tr>    
                               
                        </tbody>
                                        
                        <tbody id="tbEmpresaTramite" style="display:none;" >  
                            
                            <tr>
                              <td>Empresa</td>
                              <td class="label" colspan="2">
                                 <div id="fsEmpresaTramite" name="fsEmpresaTramite" style="width:200px;"></div>
                              </td>
                              <td colspan="1"> </td>
                           </tr>
                                       
                            <tr> 
                                <td> 
                                   Persona Remitente
                                </td>
                             <td class="label" colspan="2">
                                  <div id="fsPersonaEmpresaTramite" name="fsPersonaEmpresaTramite" style="width:200px;"></div>
                              </td>
                           </tr> 
                            
                             <tr> 
                              <td>Cargo</td>
                             <td class="label" colspan="2">
                                  <div id="fsCargoPersonaEmpresaTramite" name="fsCargoPersonaEmpresaTramite" style="width:200px;"></div>
                              </td>
                           </tr> 				
                        </tbody>                
                                        
                         <tbody id="tbPersonaNaturalTramite" style="display:none;" >      
                             <tr> 
                                <td> 
                                    <div>Persona Remitente </div>
                                    
                                </td>
                             <td class="label" colspan="2">
                                  <div id="fsPersonaNaturalTramite" name="fsPersonaNaturalTramite" style="width:200px;"></div>
                              </td>
                           </tr> 
                            
                             <tr> 
                              <td>Cargo</td>
                             <td class="label" colspan="2">
                                  <div id="fsCargoPersonaNaturalTramite" name="fsCargoPersonaNaturalTramite" style="width:200px;"></div>
                              </td>
                           </tr>
                             
                         </tbody> 
                              <tr>
                                  <td colspan="3"><hr /></td>
                               </tr>
                            <tr>
                               <td width="20%" class="titulo">DATOS DE LA CARPETA</td>
                               <td width="80%" colspan="2"></td>
                            </tr>
                            <tr>
                               <td colspan="3">
                                  <input id="objDD.iIdExpedienteTramite" style="display:none;" />
                               </td>
                            </tr>
                            <tr>
                               <td width="20%">Nro Carpeta</td>
                               <td class="label" colspan="2" width="80%">
                                  <input id="sNroExpedienteTramite" />
                                  <button dojoType="dijit.form.Button"
                                          id="btnBusquedaExpedienteTramite"
                                          jsId="btnBusquedaExpedienteTramite"
                                          iconClass="siged16 sigedSearch16"
                                          onClick="showBusquedaExpediente"
                                          showLabel="false">B&uacute;squeda de la Carpeta</button>
                                  
                                   <button dojoType="dijit.form.Button"
                                      id="btnBusquedaExpedienteRemitenteTramite"
                                      jsId="btnBusquedaExpedienteRemitenteTramite"
                                      iconClass="siged16 sigedFilter16"
                                      onClick="showBusquedaExpedienteRemitente"
                                      showLabel="false">B&uacute;squeda de Remitente</button>
                               </td>
                            </tr>
                             
                           <tbody id="tbProcesoTramite">
                                
                                <tr>
                                   <td>Asunto de la Carpeta</td>
                                   <td class="label" colspan="2">
                                      <div id="objDD.asuntoExpedienteTramite"/>
                                   </td>
                                </tr>

                                <tr>
                                   <td>Observaciones de la Carpeta</td>
                                   <td class="label" colspan="2">
                                      <div id="objDD.observacionExpedienteTramite"/>
                                   </td>
                                </tr>
                               
                                <tr>
                                 <td>Serie Documental</td>
                                 <td class="label" colspan="2">
                                   <input id="idserieTramite" style="display:none;" />
                                   <div id="fsSerieTramite"></div>
                                 </td>
                               </tr>
                          </tbody>
                                  
                   <!--     <tbody id="tbDocumentoTramite"> -->
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
                                        id="idtipodocumentoTramite"
                                        jsId="idtipodocumentoTramite"
                                        name="idtipodocumento"
                                        style="display:none; " /> 
                                 <div id="fsTipoDocumentoTramite" name="fsTipoDocumento" style="width:300px;"></div>
                              </td>
                           </tr>
                            
                            <tr id="trNroDocumento">
                              <td>Nro. Documento</td>
                              <td class="label" colspan="2">
                                 
                                 <div id="divNroDocumento">
                                  
                                          <input dojoType="dijit.form.TextBox"
                                           id="objDD.tipoNumeracionTramite"
                                           jsId="objDD.tipoNumeracionTramite"
                                           name="objDD.tipoNumeracion"
                                           style="display:none;" />
                                    <div id="fsNroDocumentoTramite" />
                                    
                                 </div>
                              </td>
                           </tr>  
                            
                           <tr>
                              <td>Asunto del Documento</td>
                              <td class="label" colspan="2">
                               <div id="objDD.strAsuntoTramite"/>
                              </td>
                           </tr>
                            
                           <tr>
                              <td>Prioridad</td>
                              <td class="label" colspan="2">
                               <div id="fsPrioridadTramite"/>
                              </td>
                           </tr> 
                            
                           <tr>
                              <td>Fecha Documento</td>
                              <td width = "200px" class="label" colspan="1">
                                 <div dojoType="dijit.form.DateTextBox"
                                      id="objDD.strFechaDocumentoTramite"
                                      jsId="objDD.strFechaDocumentoTramite"
                                      name="objDD.strFechaDocumento"
                                      constraints="{datePattern:'dd/MM/yyyy'}"
                                      invalidMessage="Ingrese fecha de Documento dd/MM/yyyy"
                                      required="true"
                                      trim="true">
                                    <script type="dojo/method" event="postCreate">
                                       var fechadocumento_year;
                                       var fechadocumento_month;
                                       var fechadocumento_day;
                                       
                                       if ("<s:date name='documento.fechaDocumento' format='dd' />"== null ||
                                           "<s:date name='documento.fechaDocumento' format='dd' />"== ""){ 
                                           fechadocumento_year = parseInt("<s:date name='fecha' format='yyyy' />", 10);
                                           fechadocumento_month = parseInt("<s:date name='fecha' format='MM' />", 10);
                                           fechadocumento_day = parseInt("<s:date name='fecha' format='dd' />", 10);
                                       }else{
                                           fechadocumento_year = parseInt("<s:date name='documento.fechaDocumento' format='yyyy' />", 10);
                                           fechadocumento_month = parseInt("<s:date name='documento.fechaDocumento' format='MM' />", 10);
                                           fechadocumento_day = parseInt("<s:date name='documento.fechaDocumento' format='dd' />", 10);  
                                        }    
                                       
                                       console.debug("fechadocumento year [%d] month [%d] day [%d]", fechadocumento_year, fechadocumento_month, fechadocumento_day);

                                       if (!isNaN(fechadocumento_year) && !isNaN(fechadocumento_month) && !isNaN(fechadocumento_day)) {
                                         this.attr("value", new Date(fechadocumento_year, fechadocumento_month - 1, fechadocumento_day));
                                       }
                                       
                                       if (TIPO_TRANSACCION == "MR"){
                                           dijit.byId("objDD.strFechaDocumentoTramite").attr("readOnly", true);
                                       }
                                    </script>
                                 </div>  <span id="datosFechaDocumento" style="color:blue"></span>
                              </td>
                                            
                           </tr>           
                           <tr>
                               <td>Plazo</td>
                               <td class="label" colspan="1">
                                  <input dojoType="dijit.form.RadioButton" type="radio" id="sPlazoDiaTramite" jsId="sPlazoDiaTramite" name="objDD.plazo" value="0" onClick="activarControlPlazo('0');" checked/>Dia &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                  <input dojoType="dijit.form.RadioButton" type="radio" id="sPlazoFechaTramite" jsId="sPlazoFechaTramite" name="objDD.plazo" value="1" onClick="activarControlPlazo('1');"/>Fecha Limite&nbsp;&nbsp;&nbsp;
                               </td>  
                          </tr>
                          <tbody id="tbPlazoDiaTramite">              
                            <tr>
                                  <td></td>  
                                 <td>
                                     <table>
                                         <tr><td><div id="fsPlazoTramite"/></td>
                                         <td><div id="sFechaPlazoTramite"/></td></tr>
                                     </table>
                                 </td>   
                            </tr>  
                          </tbody>   
                          <tbody id="tbPlazoFechaTramite" style="display:none;"> 
                              
                            <tr>        
                                <td></td>
                                <td class="label">    

                                   <div dojoType="dijit.form.DateTextBox" 
                                        id="objDD.strFechaLimiteAtencionTramite"
                                        jsId="objDD.strFechaLimiteAtencionTramite"
                                        name="objDD.strFechaLimiteAtencion"
                                        constraints="{datePattern:'dd/MM/yyyy'}"
                                        invalidMessage="Ingrese fecha limite del Documento dd/MM/yyyy"
                                        required="false" 
                                        trim="true">
                                      <script type="dojo/method" event="postCreate">
                                         var fechadocumento_year;
                                         var fechadocumento_month;
                                         var fechadocumento_day;

                                         if ("<s:date name='documento.fechaLimiteAtencion' format='dd' />"== null ||
                                             "<s:date name='documento.fechaLimiteAtencion' format='dd' />"== ""){ 
                                              this.attr("value", "");
                                         }else{
                                             fechadocumento_year = parseInt("<s:date name='documento.fechaLimiteAtencion' format='yyyy' />", 10);
                                             fechadocumento_month = parseInt("<s:date name='documento.fechaLimiteAtencion' format='MM' />", 10);
                                             fechadocumento_day = parseInt("<s:date name='documento.fechaLimiteAtencion' format='dd' />", 10);  
                                             this.attr("value", new Date(fechadocumento_year, fechadocumento_month - 1, fechadocumento_day));
                                          }    
                                      </script>
                                   </div>
                                   
                                </td>
                                      
                             </tr>              
                          </tbody>             
                           <tr>
                              <td>Documentos Vinculados</td>
                              <td class="label" colspan="2">
                                 <div id="objDD.strReferenciaTramite" />
                              </td>
                           </tr>
                       
                           <tr>
                              <td style="color:red;">Documento Confidencial</td>
                              <td class="label" colspan="2" style="color:red;">
                                 <div id="fsConfidencialTramite" style="width:300px" />
                              </td>
                           </tr>
                                      
                            <tr>
                              <td>Observaciones del Documento</td>
                              <td class="label" colspan="2">
                                  <div id="fsObservacionesDocumentoTramite" style="width:300px;height:5px"/>
                              </td>
                           </tr>
                                             
                           <tr>
                              <td>Recepción</td>
                              <td class="label" colspan="2">
                                 <div id="fsRecepcionTramite" name="fsRecepcionTramite" style="width:200px;"></div>
                              </td>
                              <td colspan="1"> </td>
                           </tr>                  
	                   
                    <!--    </tbody> -->
                                       
                        <tbody id="tbNroIdentificacionTramite" style="display:none;">            
                            <tr>
                              <td width="20%">Cliente</td>
                              <td class="label" width="50%">
                                 <input dojoType="dijit.form.TextBox"
                                        id="idclienteTramite"
                                        jsId="idclienteTramite"
                                        name="idcliente"
                                        style="display:none;" />
                                
                              </td>
                              
                             </tr>
                        </tbody>    
                                           
                       <tr>
                          <td colspan="3"><hr /></td>
                       </tr>
                       <tr>
                          <td colspan="3" class="titulo">DESTINATARIOS</td>
                       </tr>
                                           
                        <tr>
                              <td>Unidad</td>
                              <td class="label" colspan="1" width="50px"> 
                                 <div id="fsDerivacionTramite" name="fsDerivacion" style="width:100px;"></div>
                              </td>
                              <td width="500px">
                                <span id="datosRemitente" style="color:blue"></span>
                              </td>
                             
                       </tr>
                                           
                       
                           <tr>
                              <td>Para</td>
                              <td class="label" colspan="1">
                                <div id="grupoPara">  
                                    <div class="divReferencia">  
                                      <fieldset id="docRef">
                                          <s:select id="idListaPara" cssClass="docRef" list="listaDerivacionPara" required="true" listKey="identificador" listValue="nombreUsuario" multiple="true" size="2" >
                                          </s:select>
                                      </fieldset>&nbsp;
                                      <button dojoType="dijit.form.Button"  type="button" jsId="btnAgregarPara" id="btnAgregarPara" onClick="agregarPara"> <img src="images/sigedIconos/Agregar.png" width="10px;" height="10px;" border="0"/> </button>
                                      <button dojoType="dijit.form.Button"  type="button" jsId="btnRemoverPara" id="btnRemoverPara" onClick="borrarPara"> <img src="images/sigedIconos/Remover.png" width="10px;" height="10px;" border="0"/>  </button>
                                    </div>
                                </div>      
                              </td>
                           </tr>        
                            
                       <tr>
                              <td>CC</td>
                              <td class="label" colspan="1">
                                <div id="grupoCC">  
                                    <div class="divReferencia">  
                                      <fieldset id="docRef">
                                         <s:select id="idListaCC" cssClass="docRef" list="listaDerivacionCC" listKey="identificador" listValue="nombreUsuario" multiple="true" size="2" >
                                         </s:select>
                                      </fieldset>&nbsp;
                                      <button dojoType="dijit.form.Button"  type="button" jsId="btnAgregarCC" id="btnAgregarCC" onClick="agregarCC"> <img src="images/sigedIconos/Agregar.png" width="10px;" height="10px;" border="0"/> </button>
                                      <button dojoType="dijit.form.Button"  type="button" jsId="btnRemoverCC" id="btnRemoverCC" onClick="borrarCC"> <img src="images/sigedIconos/Remover.png" width="10px;" height="10px;" border="0"/>  </button>
                                    </div>
                                </div>      
                              </td>
                           </tr>                     
                                        
                        <tbody id="tbAdjuntosTramite"  border="0" >
                           <tr>
                              <td colspan="3"><hr /></td>
                           </tr>
                           <tr>
                              <td colspan="3" class="titulo">DETALLE</td>
                           </tr>
                           <tbody id="tbNroFoliosPIDETramite" style="display:none;" >
                               <tr>
                                   <td colspan="1">Nro Folios PIDE</td>
                                   <td class="label" colspan="2">
                                      <div id="fsNroFoliosPIDETramite"/>
                                   </td>
                               </tr>
                           </tbody>    
                                
                           <tr>
                                 <td colspan="1">Nro Folios Totales</td>
                                 <td>
                                     <table>
                                         <tr>
                                             <td width="80px" class="label" colspan="1">
                                                    <div id="fsNroFoliosTramite"/>  
                                            </td>
                                             
                                             <td width="58px"> Originales</td>
                                             <td class="label" colspan="1">
                                               <div id="fsNroFoliosOriginalesTramite"/>
                                            </td>
                                             
                                             <td width="40px"> Copias</td>
                                              <td class="label" colspan="1">
                                               <div id="fsNroFoliosCopiasTramite"/>
                                            </td>  
                                         </tr>
                                         
                                     </table>
                                     
                                 </td>
                           </tr> 
                           
                           <tr>
                              <td>Imagenes Digitalizadas</td>
                              <td class="label" colspan="2">
                                 <div id="fsNroImagenesDigitalizadasTramite"/>
                              </td>
                           </tr> 
                            
                        </tbody>
                        
                        <tbody id="tbDetalleAdjuntosTramite"  border="0" >
                           <tr>
                              <td>Tipo</td>
                              <td class="label" colspan="2">
                                 <div id="fsTipoAdjuntoTramite" name="fsTipoAdjuntoTramite" style="width:200px;"></div>
                              </td>
                              <td colspan="1"> </td>
                           </tr>
         
                           <tr>
                               <td> Orig/Copia  
                               <td class="label" colspan="2">    
                                       <input type="radio"
                                                dojoType="dijit.form.RadioButton"  
                                                id="radio11"
                                                name="idTipoCopiaOriginal"
                                                onClick="changeSomeFields(this.value)"
                                                value="0" />Copia &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                         <input type="radio"
                                                id="radio12"
                                                dojoType="dijit.form.RadioButton"
                                                name="idTipoCopiaOriginal" checked
                                                onClick="changeSomeFields(this.value)"
                                                value="1" />Original
                                 </td>
                              <td colspan="1"> </td> 
                           </tr>
                            <tr> 
                              <td>Nro</td>
                              <td class="label" colspan="2">
                                 <div dojoType="dijit.form.ValidationTextBox"
                                      id="nroTramite"
                                      jsId="nroTramite"
                                      name="nro"
                                      invalidMessage="Ingrese el nro de copias/originales válido"
                                      regExp="[0-9]{1,4}"
                                      maxLength="4"
                                      required="false"
                                      style="width:70px"
                                      trim="true" />
                                     
                              </td>
                              <td colspan="1"> </td>    
                           </tr>    
                             <tr>
                                <td></td>
                                <td class="label" colspan="2">
                                    <table>
                                        <tr>
                                            <td class="label"> 
                                              <button dojoType="dijit.form.Button"  type="button" jsId="btn1" id="btn15" onClick="agregar_()">Agregar</button>
                                            </td>
                                            <td class="label">
                                               <button dojoType="dijit.form.Button"  type="button" jsId="btn2" id="btn16" onClick="eliminar_()">Eliminar</button>
                                            </td>
                                        </tr>
                                    </table>   
                            </tr>
                           
                        </tbody>   
                         <tbody id="tbAdjuntosGrilla">   
                            <tr>
                                  <td> </td>
                                  <td>
                                      <div id="fsGrid" name="fsGrid" style="width:200px;"></div>
                                        
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
                             id="btnRegistrarDocumentoTramiteBottom"
                             jsId="btnRegistrarDocumentoTramiteBottom"
                             iconClass="siged16 sigedSave16"
                             onClick="submitForm"
                             showLabel="true">Registrar Documento</button>
                  </div>
               </td>
            </tr>                                                   
                                                                           
                              
                                  
                                                              
       
         </table>
                                 
                              
                  
        </form>
          <script type="text/javascript" src="js/siged/registroClienteMP.js"></script> 
          <%@include file="../tramite/registroCliente.jsp" %>   
          <%@ include file="/pages/tramite/busquedaExpediente.jsp" %> 
          <%@ include file="busquedaExpedienteAbierto.jsp" %> 
   </body>
</html>
