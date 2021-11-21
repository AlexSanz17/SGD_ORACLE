dojo.provide("js.gridInbox");

var arrStructures = new Array();
var sTipoGridActual = TIPO_GRID_RECIBIDOS;
var storeGrid = null;
var first = true ;
var handlerQAS;
var rowGridIndex = null;
var rowGridDXEIndex = null;
var idProcesoView;

var idUser;

var cambiarNoLeido=function(){
    var grid = dijit.byId("gridInbox");
    var auxGrid="";
    dojo.forEach(grid.store._arrayOfAllItems, function(item) {
      var selected = grid.store.getValue(item, "selected");
      var iIdDoc = grid.store.getValue(item, "id");

      if (selected) {
        auxGrid="1";
        service.updateNoLeido(parseInt(iIdDoc)).addCallback(function(result) {  
            accion(1);
        });
      }
   });
   if(auxGrid=="")
   {
       alert("Debe seleccionar al menos un documento.");
   }
};



var derivarMasivamente = function() {
   var grid = dijit.byId("gridInbox");
   var opciones = "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes, width=650, height=469, top=20, left=70";
   var parameters = "";
   var pagina = "goDerivarMasivo.action?sTipoDerivacion=masivo&sOpcion=nose&";

   dojo.forEach(grid.store._arrayOfAllItems, function(item) {
       if (grid.store.getValue(item, "selected")) {
         parameters += "arrIdDoc=" + grid.store.getValue(item, "id") + "&";
      }
   });

   if (parameters != "") {
      pagina += parameters;
      console.debug("URL Derivacion Masiva [%s]", pagina);
      window.open(pagina, "Ventana", opciones);
   } else {
      alert("Debe seleccionar al menos un documento.");
   }
};

var refrescarPagina = function(tipoRefrescar) {
    dojo.byId("filamenu"+tipoRefrescar).setAttribute("class", "filamenusel");
    accion(tipoRefrescar);
};


var derivarMasivamenteDoc = function() {
   var grid = dijit.byId("gridInbox");
   var opciones = "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes, width=650, height=469, top=20, left=70";
   var parameters = "";
   var pagina = "goDerivarMasivoDoc.action?sTipoDerivacion=masivoDoc&sOpcion=nose&";

   dojo.forEach(grid.store._arrayOfAllItems, function(item) {
      if (grid.store.getValue(item, "selected")) {
         parameters += "arrIdDoc=" + grid.store.getValue(item, "id") + "&";
      }
   });

   if (parameters != "") {
      pagina += parameters;
      console.debug("URL Derivacion Masiva [%s]", pagina);
      window.open(pagina, "Ventana", opciones);
   } else {
      alert("Debe seleccionar al menos un documento.");
   }
};


var imprimirDocumentosRecibidos = function() {
	   var opciones = "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=1000, height=600, top=20, left=70";
	   var pagina = "ReporteAPN_reporteDocumentosRecibidos.action";
	   window.open(pagina, "", opciones);
};

var imprimirDocumentosInformativos = function() {
	var opciones = "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=1000, height=600, top=20, left=70";
	   var pagina = "ReporteAPN_reporteDocumentosInformativos.action";
	   window.open(pagina, "", opciones);
};

var archivarMasivamente = function() {
   var grid = dijit.byId("gridInbox");
   var opciones = "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=1000, height=600, top=20, left=70";
   var parameters = "";
   var pagina = "Archivar_inicioMasivo.action?";

   dojo.forEach(grid.store._arrayOfAllItems, function(item) {
      if (grid.store.getValue(item, "selected")) {
         parameters += grid.store.getValue(item, "id") + ";";
      }
   });
   //    alert("parametrosssssss-->" + parameters);
   if (parameters != "") {
      service.setAttributeInSession('lista', parameters);
      console.debug("URL Archivamiento Masivo [%s]", pagina);
      window.open(pagina, "", opciones);
   } else {
      alert("Primero selecccionar documentos");
   }
};

var reabrirMasivamente = function(){
	var grid = dijit.byId("gridInbox");
	var arrIdDoc = new Array();

	dojo.forEach(grid.store._arrayOfAllItems, function(item) {
		if (grid.store.getValue(item, "selected")) {
			arrIdDoc.push(grid.store.getValue(item, "id"));
		}
	});

	if(arrIdDoc.length){
		if(confirm("Desea reabrir los documentos seleccionados?")){
			dojo.xhrPost({
	            url : "Archivar_reabrirMasivo.action",
	            content : {
	            	arrIdDoc : arrIdDoc
	            },
	            mimetype : "text/html",
	            load : function() {
	               showGridInbox(TIPO_GRID_DOCUMENTOS_ARCHIVADOS);
	            }
	         });
		}
	}else{
		alert("Debe seleccionar documentos a reabrir");
	}
};

var eliminarEnviados = function() {
   var grid = dijit.byId("gridInbox");
   var parameters = new Array();

   dojo.forEach(grid.store._arrayOfAllItems, function(item) {
      if (grid.store.getValue(item, "selected")) {
         parameters.push(grid.store.getValue(item, "id"));
      }
   });

   console.debug("Se eliminaran [%d] enviados [%s] ", parameters.length, parameters);

   if (parameters.length) {
      if (confirm("Desea eliminar los documentos seleccionados?")) {
         dojo.xhrPost({
            url : "DocumentoEnviado_eliminar.action",
            content : {
               id : parameters,
               total : parameters.length
            },
            mimetype : "text/html",
            load : function() {
               showGridInbox(TIPO_GRID_ENVIADOS);
            }
         });
      }
   } else {
      alert("Primero selecccionar documentos");
   }
};

var eliminarNotificaciones = function() {
   var grid = dijit.byId("gridInbox");
   var parameters = new Array();

   dojo.forEach(grid.store._arrayOfAllItems, function(item) {
      if (grid.store.getValue(item, "selected")) {
         var id  = grid.store.getValue(item, "id");
         parameters.push(grid.store.getValue(item, "id"));
      }
   });

   console.debug("Se eliminaran [%d] notificaciones [%s] ", parameters.length, parameters);

   if (parameters.length) {
      if (confirm("Desea eliminar las notificaciones seleccionados?")) {
         dojo.forEach(grid.store._arrayOfAllItems, function(item) {
            if (grid.store.getValue(item, "selected")) {
               updateLeido2(item) ;

            }
         });

         dojo.xhrPost({
            url : "doEliminarNotificaciones.action",
            content : {
               id : parameters,
               total : parameters.length
            },
            mimetype : "text/html",
            load : function() {
               showGridInbox(TIPO_GRID_NOTIFICACIONES);
            }
         });
      }
   } else {
      alert("Primero selecccionar notificaciones");
   }
};

var eliminarInformativo = function() {
   var grid = dijit.byId("gridInbox");
   var parameters = new Array();

   dojo.forEach(grid.store._arrayOfAllItems, function(item) {
      if (grid.store.getValue(item, "selected")) {
         parameters.push(grid.store.getValue(item, "id"));
      }
   });

   console.debug("Se eliminaran [%d] informativos [%s] ", parameters.length, parameters);

   if (parameters.length) {
      if (confirm("Desea eliminar los informativos seleccionados?")) {
         dojo.xhrPost({
            url : "doEliminarNotificaciones.action",
            content : {
               id : parameters
            },
            mimetype : "text/html",
            load : function() {
               showGridInbox(TIPO_GRID_INFORMATIVOS);
            }
         });
      }
   } else {
      alert("Primero selecccionar Informativos");
   }
};

var cambiarAgrupacionBandeja = function(){
	dojo.xhrPost({
		url : "cambiarAgrupacionUsuario.action",
		content: {
			agrupacion : bandejaAgrupadaUsuario
		},
		load : function(){
			showGridInbox(sTipoGridActual);
			if(bandejaAgrupadaUsuario == "S"){
				bandejaAgrupadaUsuario = "N";
			}else{
				bandejaAgrupadaUsuario = "S";
			}
		}
	});
};

 var enviarInformacionFirma = function(){
      document.getElementById("respuesta").value = "";
      firmarDocumento(recibirRespuestaFirma);
      dijit.byId("dlgProgresBar").show() ;
      //dojo.style(dijit.byId("dlgProgresBar").closeButtonNode,"display","none");
}
  
 var enviarMensaje = function() {
    var respuesta = obtenerRespuestaFirma(document.getElementById("respuesta").value);
    
    if (respuesta[0] == "CANCELADO") {
        //alert("El usuario ha cancelado el procesode firma. Nยบ procesamiento: " + respuesta[1]);
        dijit.byId("dlgProgresBar").hide() ;
    } else {
        //alert(UNIDAD_USUARIO);
        //alert("Se ha iniciado el proceso de firma digital con el numero de procesamiento: " + respuesta[1]); 
        var servicio = new dojo.rpc.JsonService("SMDAction.action");
        var defered = servicio.respuestaFirmar(respuesta[2], respuesta[0], respuesta[3], document.getElementById("tipoFirma").value);
        defered.addCallback(function(){
            showGridInbox(TIPO_GRID_FIRMAR);
            dijit.byId("dlgProgresBar").hide() ;
        });
        
        //dijit.byId("dlgProgresBar").hide() ;
    }
    
}

var visarDocumentos = function(){
    procesarFirmas("V");
}

var firmarDocumentos = function(){
    procesarFirmas("F");
}

var activarExportarPendientes = function() {
    service.exportarExcelPendientes().addCallback(function(result) {
        window.open(result,"_blank");
    });
};

var verNavegador = function (){
    var servicio = new dojo.rpc.JsonService("SMDAction.action");
        var defered = servicio.validarDataNavegador();
        defered.addCallback(function(respuesta){
            if (respuesta == '1'){
              window.open("/SGD/verDocumento.png?accion=abrirNavegador&fecha" + new Date(),"_blank");
            }else{
              alert("No se encontraron documentos con archivos principales");
              return;
            }
        });
}

var activarExportarRecibidos = function() {
    service.exportarExcelRecibidos().addCallback(function(result) {
        window.open(result,"_blank");
    });
};

var activarExportarAtendidos = function() {
    service.exportarExcelAtendidos().addCallback(function(result) {
       window.open(result,"_blank"); 
    });
};

var activarExportarMisDocumentos = function() {
    service.exportarExcelMisDocumentos().addCallback(function(result) {
       window.open(result,"_blank"); 
    });
};

var activarExportarEnviados = function() {
    service.exportarExcelEnviados().addCallback(function(result) {
       window.open(result,"_blank"); 
    });
};

var mostrarDocumentosFirmar = function(valores, accionEjecutar){
    var cabecera = "";
   
    if (accionEjecutar== 'F'){
       cabecera = "Firmar"; 
    }else{
       cabecera = "Visar"; 
    }
    
    dojo.xhrPost({
        	   url: "goFirmarArchivo.action",
        	   content:{
        		    arrFileFirmar : valores,
                            accion: accionEjecutar
        	   },
        	   load: function(data){
                      if(!dijit.byId("dlgFirmar")){
                               new dijit.Dialog({
	        	  	    id: "dlgFirmar",
	                	    draggable:"true",
	                	    style:"height:305px;width:513px;display:none;",
	                	    title:"Documentos a "  + cabecera,  
	        		    onClose: dojo.hitch(this, function(){
                                        dijit.byId("dlgFirmar").hide();
	        		        dijit.byId("dlgFirmar").destroyRecursive();
	        		    })
	                        });
        		}
                        
        	       dijit.byId("dlgFirmar").attr("title", "Documentos a " + cabecera);	
                       dijit.byId("dlgFirmar").attr("content", data);
        	       dijit.byId("dlgFirmar").show();
        	    }
     });
}

var procesarFirmas = function(accionEjecutar){
   var grid = dijit.byId("gridInbox");
   var parameters = new Array();
   var valores = "";
   
   dojo.forEach(grid.store._arrayOfAllItems, function(item) {
      if (grid.store.getValue(item, "selected")) {
         parameters.push(grid.store.getValue(item, "id"));
      }
   });
    
   for (var indice in parameters) {
      if (indice == parameters.length -1){
        valores = valores + parameters[indice];
      }else{
        valores = valores + parameters[indice] + ",";
      }    
   }
   
   var accion = "";
   if (accionEjecutar == 'F'){
     accion = "Firmar";
   }else{
     accion = "Visar";
   }
   
   if (parameters.length>1 && unidadSeleccionada==unidadTramiteVirtual){
        alert("Sólo puede Firmar un documento a la vez.");
        return;
   }
  
   if (parameters.length>0) {
        var servicio = new dojo.rpc.JsonService("SMDAction.action");
        var defered = servicio.getArchivosFirmar(valores);
        defered.addCallback(function(objJSON){
            if (objJSON.items.length>0){
                mostrarDocumentosFirmar(valores, accionEjecutar);        
            }else{
                alert("No se encontraron archivos (PDF) pendientes por " + accion + ".");
                return;
            }
        });
           
   } else {
       alert("Primero debe selecccionar los documentos a " + accion);
   }
};



var mostrarFiltrosBandeja = function(){
        dojo.xhrPost({
		url: "mostrarFiltrosBandeja.action",
		content:{
			idTipoGrid : sTipoGridActual
		},
		load: function(data){
			if(!dijit.byId("dlgFiltrosBandeja")){
				new dijit.Dialog({
					id: "dlgFiltrosBandeja",
					jsId: "dlgFiltrosBandeja",
        			draggable:"true",
        			style:"height:18.4em;width:75em;display:none;",
        			title:"Filtrar Bandeja",
		            onClose: dojo.hitch(this, function(){
                                
		            	dijit.byId("dlgFiltrosBandeja").hide();
		        		dijit.byId("dlgFiltrosBandeja").destroyRecursive();
		            })
        		});
			}
			console.debug(dijit.byId("dlgFiltrosBandeja"));
			dijit.byId("dlgFiltrosBandeja").attr("content", data);
			dijit.byId("dlgFiltrosBandeja").show();
		}
	});
};

var getAllStructures = function() {
   
   service.getAllStructures().addCallback(function(arrResultado) {
       
      if (arrResultado == undefined || arrResultado == "") {
         console.error("(getAllStructures) No se recibio ninguna estructura. ESTO ES GRAVE!");

         return;
      }

      if (arrResultado[TIPO_GRID_RECIBIDOS] != undefined) {
         arrResultado[TIPO_GRID_RECIBIDOS].unshift({
            editable: true,
            field: "selected",
            name: "<input type='checkbox' id='chkHeader' name='chkHeader' onclick='selectAll()'>",
            noresize: true,
            type: dojox.grid.cells.Bool,
            width: "20px"
         });
      }

      if (arrResultado[TIPO_GRID_ENVIADOS] != undefined) {
         arrResultado[TIPO_GRID_ENVIADOS].unshift({
            editable: true,
            field: "selected",
            name: "<input type='checkbox' id='chkHeader' name='chkHeader' onclick='selectAll()'>",
            noresize: true,
            type: dojox.grid.cells.Bool,
            width: "20px"
         });
      }

      if (arrResultado[TIPO_GRID_NOTIFICACIONES] != undefined) {
         arrResultado[TIPO_GRID_NOTIFICACIONES].unshift({
            editable: true,
            field: "selected",
            name: "<input type='checkbox' id='chkHeader' name='chkHeader' onclick='selectAll()'>",
            noresize: true,
            type: dojox.grid.cells.Bool,
            width: "20px"
         });
      }

      if (arrResultado[TIPO_GRID_INFORMATIVOS] != undefined) {
         arrResultado[TIPO_GRID_INFORMATIVOS].unshift({
            editable: true,
            field: "selected",
            name: "<input type='checkbox' id='chkHeader' name='chkHeader' onclick='selectAll()'>",
            noresize: true,
            type: dojox.grid.cells.Bool,
            width: "20px"
         });
      }
      
      if (arrResultado[TIPO_GRID_FIRMAR] != undefined) {
         arrResultado[TIPO_GRID_FIRMAR].unshift({
            editable: true,
            field: "selected",
            name: "<input type='checkbox' id='chkHeader' name='chkHeader' onclick='selectAll()'>",
            noresize: true,
            type: dojox.grid.cells.Bool,
            width: "20px"
         });
      }


      arrStructures = arrResultado;
   });
};

var getTabTitle = function(sTipoGrid) {
   var sTitle = "";
   
    if (sTipoGrid == TIPO_GRID_RECIBIDOS) {
      sTitle = "Documentos Recibidos";
   } else if (sTipoGrid == TIPO_GRID_ENVIADOS) {
      sTitle = "Enviados";
   }else if (sTipoGrid == TIPO_GRID_NOTIFICACIONES) {
      sTitle = "Notificaciones";
   }else if (sTipoGrid == TIPO_GRID_EXPEDIENTES) {
      sTitle = "Mis Documentos";
   }else if (sTipoGrid == TIPO_GRID_INFORMATIVOS) {
      sTitle = "Con Copia";
   }else if (sTipoGrid == TIPO_GRID_DOCUMENTOS_ARCHIVADOS) {
      sTitle = "Documentos Atendidos";
   } else if (sTipoGrid == TIPO_GRID_SEGUIMIENTO) {
      sTitle = "Documentos en Seguimiento";
   }else if (sTipoGrid == TIPO_GRID_PENDIENTES){
      sTitle = "Documentos Pendientes"; 
   }else if (sTipoGrid == TIPO_GRID_ANULADOS){
      sTitle = "Documentos Anulados"; 
   }else if (sTipoGrid == TIPO_GRID_FIRMAR){
      sTitle = "Documentos Para Firmar"; 
   }else if (sTipoGrid == TIPO_GRID_RECEPCION_VIRTUAL){
      sTitle = "Documentos Recepcionados"; 
   }else if (sTipoGrid == TIPO_GRID_DESPACHO_VIRTUAL){
      sTitle = "Documentos Despachados"; 
   }else if (sTipoGrid == TIPO_GRID_MI_LEGAJO){
      sTitle = "Expedientes"; 
   }else if (sTipoGrid == TIPO_GRID_LEGAJO_COMPARTIDO){
      sTitle = "Compartidos"; 
   }else if (sTipoGrid == TIPO_GRID_RECEPCION_VIRTUAL_OBSERVADOS){
      sTitle = "Documentos Observados"; 
   }

   return sTitle;
};

var noSort=function(inSortInfo){
   console.debug("inSortInfo [%d]", inSortInfo);

   if((sTipoGridActual == TIPO_GRID_RECIBIDOS || sTipoGridActual == TIPO_GRID_NOTIFICACIONES
		   || sTipoGridActual == TIPO_GRID_INFORMATIVOS)  && inSortInfo == 1) {
      return false;
   }else if(sTipoGridActual == TIPO_GRID_RECIBIDOS && inSortInfo == 2){
	   return false;
   }else{
      return true;
   }
};

var removeAndAddToolBarInbox = function(sTipoGrid) {

   dojo.forEach(dijit.byId("toolBarInbox").getChildren(), function(item) {
      console.debug("(removeAndAddToolBarInbox) Removiendo del toolBarInbox [%s]", item.id);
      dijit.byId("toolBarInbox").removeChild(item);
   });

   console.debug("sTipoGrid:"+sTipoGrid+" and "+TIPO_GRID_NOTIFICACIONES);
  
   if (sTipoGrid == TIPO_GRID_RECIBIDOS || sTipoGrid == TIPO_GRID_EXPEDIENTES) {
     // console.debug("(removeAndAddToolBarInbox) Agregando al toolBarInbox [btnDerivarMasivamente]");
      dijit.byId("toolBarInbox").addChild(dijit.byId("btnFiltrarBandeja")); //wcarrasco 17-02-2012
      dijit.byId("toolBarInbox").addChild(dijit.byId("btnDerivarMasivamente"));
   } else if (sTipoGrid == TIPO_GRID_ENVIADOS) {
     dijit.byId("toolBarInbox").addChild(dijit.byId("btnFiltrarBandeja"));
   } else if (sTipoGrid == TIPO_GRID_NOTIFICACIONES) {
      console.debug("(removeAndAddToolBarInbox) Agregando al toolBarInbox [btnEliminarN]");
      dijit.byId("toolBarInbox").addChild(dijit.byId("btnFiltrarBandeja"));
      dijit.byId("toolBarInbox").addChild(dijit.byId("btnEliminarN"));
   } else if (sTipoGrid == TIPO_GRID_QAS_DIGITALIZADOS) { //QAs
      dijit.byId("toolBarInbox").addChild(dijit.byId("txtFiltrarBandeja"));
      dijit.byId("toolBarInbox").addChild(dijit.byId("lblFiltrarBandeja"));
   } else if (sTipoGrid == TIPO_GRID_DIG_DOC_INGRESADOS) { //Documentos Ingresados
      dijit.byId("toolBarInbox").addChild(dijit.byId("txtFiltrarBandeja"));
      dijit.byId("toolBarInbox").addChild(dijit.byId("lblFiltrarBandeja"));
   }else if (sTipoGrid == TIPO_GRID_MENSAJERIA_RECIBIDOS) {//Mensajeria Ingresado
      //dijit.byId("toolBarInbox").addChild(dijit.byId("txtFiltrarMensaje"));
      dijit.byId("toolBarInbox").addChild(dijit.byId("lblFiltrarBandeja"));
   }else if (sTipoGrid == TIPO_GRID_MENSAJERIA_CERRADOS) {//Mensajeria Cerrrado
      //dijit.byId("toolBarInbox").addChild(dijit.byId("txtFiltrarMensaje"));
      dijit.byId("toolBarInbox").addChild(dijit.byId("lblFiltrarBandeja"));
   } else if (sTipoGrid == TIPO_GRID_MENSAJERIA) {//Mensajeria usuario Final
      //dijit.byId("toolBarInbox").addChild(dijit.byId("txtFiltrarMensaje"));
      dijit.byId("toolBarInbox").addChild(dijit.byId("lblFiltrarBandeja"));
   } else if (sTipoGrid == TIPO_GRID_DOCUMENTOS_ARCHIVADOS) {//Expedientes terminados usuario Final
       dijit.byId("toolBarInbox").addChild(dijit.byId("btnFiltrarBandeja"));
       //dijit.byId("toolBarInbox").addChild(dijit.byId("btnReabrirMasivamente"));
       //dijit.byId("toolBarInbox").addChild(dijit.byId("txtFiltrarMensaje"));
       dijit.byId("toolBarInbox").addChild(dijit.byId("lblFiltrarBandeja"));
   }else if (sTipoGrid == TIPO_GRID_PENDIENTES) {//Expedientes terminados usuario Final
      dijit.byId("toolBarInbox").addChild(dijit.byId("btnFiltrarBandeja"));
      //dijit.byId("toolBarInbox").addChild(dijit.byId("txtFiltrarMensaje"));
      dijit.byId("toolBarInbox").addChild(dijit.byId("lblFiltrarBandeja"));
   }else if (sTipoGrid == TIPO_GRID_ANULADOS) {//Expedientes terminados usuario Final
      dijit.byId("toolBarInbox").addChild(dijit.byId("btnFiltrarBandeja"));
      //dijit.byId("toolBarInbox").addChild(dijit.byId("txtFiltrarMensaje"));
      dijit.byId("toolBarInbox").addChild(dijit.byId("lblFiltrarBandeja"));
   }
};

//German: me parece que es mejor primero ocultar el toolbar y luego mostrarlo una vez que haya cargado el grid
var removeToolBarInbox=function(){

   dojo.forEach(dijit.byId("toolBarInbox").getChildren(), function(item) {
      console.debug("(removeAndAddToolBarInbox) Removiendo del toolBarInbox [%s]", item.id);
      dijit.byId("toolBarInbox").removeChild(item);
   });
};

//*****
var addToolBarInbox = function(tipoGrid) {
    
   if (tipoGrid == TIPO_GRID_RECIBIDOS || tipoGrid == TIPO_GRID_EXPEDIENTES) {
    
      dijit.byId("toolBarInbox").addChild(dijit.byId("btnFiltrarBandeja")); //wcarrasco 17-02-2012
      // JC dijit.byId("toolBarInbox").addChild(dijit.byId("btnArchivarMasivamente"));
      if(tipoGrid == TIPO_GRID_RECIBIDOS){
          dijit.byId("toolBarInbox").addChild(dijit.byId("btnRefrescar"));
          document.getElementById("btnRefrescar").onclick = function() {refrescarPagina(1)};
          
          if (bandejaJefe=="4"){
             dijit.byId("toolBarInbox").addChild(dijit.byId("btnDerivarMasivamente"));
          }  
          
          dijit.byId("toolBarInbox").addChild(dijit.byId("btnDerivarDocMasivamente"));
          dijit.byId("toolBarInbox").addChild(dijit.byId("idFiltroTipo"));
          dijit.byId("idFiltroTipo").attr("value","-1");
          dijit.byId("toolBarInbox").addChild(dijit.byId("lblFiltrarTipo"));
         
          ////////////jc  
          dijit.byId("toolBarInbox").addChild(dijit.byId("idFiltroTipoDocumento"));
          dijit.byId("idFiltroTipoDocumento").attr("value","-1");
          dijit.byId("toolBarInbox").addChild(dijit.byId("lblFiltrarTipoDocumento"));
          dijit.byId("toolBarInbox").addChild(dijit.byId("btnCambiarNoLeido"));
          ///////////////
         
          dijit.byId("toolBarInbox").addChild(dijit.byId("btnExportarRecibidos"));
          dijit.byId("toolBarInbox").addChild(dijit.byId("btnNavegadorDocumentos"));
      }
      if (tipoGrid == TIPO_GRID_EXPEDIENTES){
          dijit.byId("toolBarInbox").addChild(dijit.byId("btnRefrescar"));
         document.getElementById("btnRefrescar").onclick = function() {refrescarPagina(2)};
         dijit.byId("toolBarInbox").addChild(dijit.byId("btnExportarMisDocumentos"));
      }
     
   } else if (tipoGrid == TIPO_GRID_ENVIADOS) {
      dijit.byId("toolBarInbox").addChild(dijit.byId("btnRefrescar"));
      document.getElementById("btnRefrescar").onclick = function() {refrescarPagina(6)};
      
      dijit.byId("toolBarInbox").addChild(dijit.byId("btnFiltrarBandeja"));
      dijit.byId("toolBarInbox").addChild(dijit.byId("btnExportarEnviados"));
      
      /////////////////////
      
      dijit.byId("toolBarInbox").addChild(dijit.byId("idFiltroMesesEnviado"));
      dijit.byId("idFiltroMesesEnviado").attr("value","3");
      dijit.byId("toolBarInbox").addChild(dijit.byId("lblFiltrarMesesEnviado"));
      
      dijit.byId("toolBarInbox").addChild(dijit.byId("idFiltroUsuarioEnviado")); 
      dijit.byId("idFiltroUsuarioEnviado").attr("value","-1");
      dijit.byId("toolBarInbox").addChild(dijit.byId("lblFiltrarIdUsuario"));
      
      dijit.byId("toolBarInbox").addChild(dijit.byId("idFiltroTipoDocumentoEnviado"));
      dijit.byId("idFiltroTipoDocumentoEnviado").attr("value","-1");
      dijit.byId("toolBarInbox").addChild(dijit.byId("lblFiltrarTipoDocumento"));
      ///////////////////
      
   } else if (tipoGrid == TIPO_GRID_NOTIFICACIONES) {
      dijit.byId("toolBarInbox").addChild(dijit.byId("btnRefrescar"));
      document.getElementById("btnRefrescar").onclick = function() {refrescarPagina(8)};
      
      console.debug("(addToolBarInbox) Agregando al toolBarInbox [btnEliminarN]");
      dijit.byId("toolBarInbox").addChild(dijit.byId("btnEliminarN"));
   } else if (tipoGrid == TIPO_GRID_INFORMATIVOS) {
      dijit.byId("toolBarInbox").addChild(dijit.byId("btnRefrescar"));
      document.getElementById("btnRefrescar").onclick = function() {refrescarPagina(4)};
      
      console.debug("(addToolBarInbox) Agregando al toolBarInbox [btnEliminarInformativo]");
      dijit.byId("toolBarInbox").addChild(dijit.byId("btnFiltrarBandeja"));
      dijit.byId("toolBarInbox").addChild(dijit.byId("btnEliminarInformativo"));
      dijit.byId("toolBarInbox").addChild(dijit.byId("btnImprimirBandejaInformativos"));
   } else if (tipoGrid == TIPO_GRID_QAS_DIGITALIZADOS) { //QAs
      dijit.byId("toolBarInbox").addChild(dijit.byId("txtFiltrarBandeja"));
      dijit.byId("toolBarInbox").addChild(dijit.byId("lblFiltrarBandeja"));
   } else if (tipoGrid == TIPO_GRID_DIG_DOC_INGRESADOS) { //Documentos Ingresados
      dijit.byId("toolBarInbox").addChild(dijit.byId("txtFiltrarBandeja"));
      dijit.byId("toolBarInbox").addChild(dijit.byId("lblFiltrarBandeja"));
   } else if (tipoGrid == TIPO_GRID_MENSAJERIA_RECIBIDOS) {//mensaje recibido
      dijit.byId("toolBarInbox").addChild(dijit.byId("txtFiltrarMensaje"));
      dijit.byId("toolBarInbox").addChild(dijit.byId("lblFiltrarBandeja"));
   } else if (tipoGrid == TIPO_GRID_MENSAJERIA_CERRADOS) {//Mensajeria cerrado
      dijit.byId("toolBarInbox").addChild(dijit.byId("txtFiltrarMensaje"));
      dijit.byId("toolBarInbox").addChild(dijit.byId("lblFiltrarBandeja"));
   } else if (tipoGrid == TIPO_GRID_MENSAJERIA) {//Mensajeria usuario Final
      dijit.byId("toolBarInbox").addChild(dijit.byId("txtFiltrarMensaje"));
      dijit.byId("toolBarInbox").addChild(dijit.byId("lblFiltrarBandeja"));
   } else if (tipoGrid == TIPO_GRID_DOCUMENTOS_ARCHIVADOS) {//Expedientes terminados usuario Final
      dijit.byId("toolBarInbox").addChild(dijit.byId("btnRefrescar"));
      document.getElementById("btnRefrescar").onclick = function() {refrescarPagina(3)}; 
       
      dijit.byId("toolBarInbox").addChild(dijit.byId("btnFiltrarBandeja"));
      dijit.byId("toolBarInbox").addChild(dijit.byId("btnExportarAtendidos"));
      
      if (bandejaJefe=="4"){
        dijit.byId("toolBarInbox").addChild(dijit.byId("idFiltroUsuarioAtendido")); 
        dijit.byId("idFiltroUsuarioAtendido").attr("value","-1");
        dijit.byId("toolBarInbox").addChild(dijit.byId("lblFiltrarIdUsuario")); 
      }
      
   }else if (tipoGrid == TIPO_GRID_PENDIENTES) {
      dijit.byId("toolBarInbox").addChild(dijit.byId("btnRefrescar"));
      document.getElementById("btnRefrescar").onclick = function() {refrescarPagina(5)}; 
      dijit.byId("toolBarInbox").addChild(dijit.byId("btnFiltrarBandeja"));
      dijit.byId("toolBarInbox").addChild(dijit.byId("btnExportarPendientes"));
      
      if (bandejaJefe=="4"){
        dijit.byId("toolBarInbox").addChild(dijit.byId("idFiltroUsuarioPendiente")); 
        dijit.byId("idFiltroUsuarioPendiente").attr("value","-1");
        dijit.byId("toolBarInbox").addChild(dijit.byId("lblFiltrarIdUsuario")); 
      }
   }else if (tipoGrid == TIPO_GRID_ANULADOS) {
      dijit.byId("toolBarInbox").addChild(dijit.byId("btnRefrescar"));
      document.getElementById("btnRefrescar").onclick = function() {refrescarPagina(22)};  
      dijit.byId("toolBarInbox").addChild(dijit.byId("btnFiltrarBandeja"));
   }else if (tipoGrid == TIPO_GRID_FIRMAR) {
       dijit.byId("toolBarInbox").addChild(dijit.byId("btnFiltrarBandeja"));
       dijit.byId("toolBarInbox").addChild(dijit.byId("btnRefrescar"));
       document.getElementById("btnRefrescar").onclick = function() {refrescarPagina(23)};
       
       dijit.byId("toolBarInbox").addChild(dijit.byId("btnFirmar"));
       if (unidadSeleccionada!=unidadTramiteVirtual){
         dijit.byId("toolBarInbox").addChild(dijit.byId("btnVisar"));
       }
       
       if (bandejaJefe=="4"){
          dijit.byId("toolBarInbox").addChild(dijit.byId("btnDerivarMasivamente"));
       } 
      
       dijit.byId("toolBarInbox").addChild(dijit.byId("btnDerivarDocMasivamente")); 
       dijit.byId("toolBarInbox").addChild(dijit.byId("idFiltroTipoDocumento"));
       dijit.byId("idFiltroTipoDocumento").attr("value","-1");
       dijit.byId("toolBarInbox").addChild(dijit.byId("lblFiltrarTipoDocumento"));
   }else if (tipoGrid == TIPO_GRID_SEGUIMIENTO){
       dijit.byId("toolBarInbox").addChild(dijit.byId("btnRefrescar"));
       document.getElementById("btnRefrescar").onclick = function() {refrescarPagina(21)};
       
       dijit.byId("toolBarInbox").addChild(dijit.byId("idFiltroTipoDocumento"));
       dijit.byId("idFiltroTipoDocumento").attr("value","-1");
       dijit.byId("toolBarInbox").addChild(dijit.byId("lblFiltrarTipoDocumento"));
   }else if (tipoGrid == TIPO_GRID_RECEPCION_VIRTUAL) {
        dijit.byId("toolBarInbox").addChild(dijit.byId("lblFiltrarContador"));
        dijit.byId("toolBarInbox").addChild(dijit.byId("idFiltroEstadoRecepcionVirtual"));
        dijit.byId("idFiltroEstadoRecepcionVirtual").attr("value","P");
        dijit.byId("toolBarInbox").addChild(dijit.byId("lblFiltrarEstado"));
        
        dijit.byId("toolBarInbox").addChild(dijit.byId("idFiltrarCUORecepcion"));
        dijit.byId("toolBarInbox").addChild(dijit.byId("lblFiltrarCUO"));
        dijit.byId("idFiltrarCUORecepcion").attr("value","");
   }else if (tipoGrid == TIPO_GRID_DESPACHO_VIRTUAL) {
        dijit.byId("toolBarInbox").addChild(dijit.byId("lblFiltrarContador"));
        dijit.byId("toolBarInbox").addChild(dijit.byId("idFiltroEstadoDespachoVirtual"));
        dijit.byId("idFiltroEstadoDespachoVirtual").attr("value","P");
        dijit.byId("toolBarInbox").addChild(dijit.byId("lblFiltrarEstado"));
        
        dijit.byId("toolBarInbox").addChild(dijit.byId("idFiltrarCUODespacho"));
        dijit.byId("toolBarInbox").addChild(dijit.byId("lblFiltrarCUO"));
        dijit.byId("idFiltrarCUODespacho").attr("value","");
   }else if (tipoGrid == TIPO_GRID_LEGAJO_COMPARTIDO){
        dijit.byId("toolBarInbox").addChild(dijit.byId("idFiltrarAsuntoLegajo"));
        dijit.byId("toolBarInbox").addChild(dijit.byId("lblFiltrarAsunto"));
        dijit.byId("idFiltrarAsuntoLegajo").attr("value","");
        
        dijit.byId("toolBarInbox").addChild(dijit.byId("idFiltroProcedimiento")); 
        dijit.byId("idFiltroProcedimiento").attr("value","-1");
        dijit.byId("toolBarInbox").addChild(dijit.byId("lblFiltrarProcedimiento")); 
        
        dijit.byId("toolBarInbox").addChild(dijit.byId("idFiltroMetodo")); 
        dijit.byId("idFiltroMetodo").attr("value","-1");
        dijit.byId("toolBarInbox").addChild(dijit.byId("lblFiltrarMetodo")); 
   }else if (tipoGrid == TIPO_GRID_MI_LEGAJO){
        dijit.byId("toolBarInbox").addChild(dijit.byId("idFiltrarAsuntoLegajo"));
        dijit.byId("toolBarInbox").addChild(dijit.byId("lblFiltrarAsunto"));
        dijit.byId("idFiltrarAsuntoLegajo").attr("value","");
        
        dijit.byId("toolBarInbox").addChild(dijit.byId("idFiltroProcedimiento")); 
        dijit.byId("idFiltroProcedimiento").attr("value","-1");
        dijit.byId("toolBarInbox").addChild(dijit.byId("lblFiltrarProcedimiento"));
        
        dijit.byId("toolBarInbox").addChild(dijit.byId("idFiltroMetodo")); 
        dijit.byId("idFiltroMetodo").attr("value","-1");
        dijit.byId("toolBarInbox").addChild(dijit.byId("lblFiltrarMetodo")); 
   }
  
};

var selectAll = function() {
   var grid = dijit.byId("gridInbox");

   dojo.byId("chkHeader").checked = !dojo.byId("chkHeader").checked;

   dojo.forEach(grid.store._arrayOfAllItems, function(item) {
      grid.store.setValue(item, "selected", dojo.byId("chkHeader").checked);
   });
};

var selectAllByUser = function(idUsuario) {
   var grid = dijit.byId("gridInbox"+idUsuario);

   dojo.byId("chkHeader"+idUsuario).checked = !dojo.byId("chkHeader"+idUsuario).checked;

   dojo.forEach(grid.store._arrayOfAllItems, function(item) {
      grid.store.setValue(item, "selected", dojo.byId("chkHeader"+idUsuario).checked);
   });
};

function pausecomp(millis)
{
   var date = new Date();
   var curDate = null;

   do {
      curDate = new Date();
   }
   while(curDate-date < millis);
}

function contarExpedientes(JSONobj) {
   var memo_2 = 0, oficio_3 = 0, informe_86=0;
   for (var key1 in JSONobj)
   {
      var value1 = JSONobj[key1]; //items
      var doc = value1.documento.toString();
      //        console.info("key1=" + key1 + "----------------value1=" + doc);
      if (doc.indexOf('MEMORANDUM')>-1) {
         memo_2 = memo_2 + 1;
         continue;
      }
      if (doc.indexOf('OFICIO')>-1) {
         oficio_3 = oficio_3 + 1;
         continue;
      }
      if (doc.indexOf('INFORME')>-1) {
         informe_86 = informe_86 +1;
         continue;
      }
   }
   //    alert('memo->' + memo_2 + ', ofic->' + oficio_3 + ', informe->' + informe_86);
   dojo.byId("resumenExpMem").innerHTML = memo_2 + " MEM., ";
   dojo.byId("resumenExpOf").innerHTML = oficio_3 + " OF., ";
   dojo.byId("resumenExpInf").innerHTML= informe_86 + " INF.; ";
}

function respuesta(){
    
}

//aka se forma todo
var showGridInbox = function(sTipoGrid) {
   var objTipoDocumento = dijit.byId("idFiltroTipoDocumento").value;
   var objGridActual = sTipoGridActual;
   
   if(sTipoGrid == null || sTipoGrid == ""){
	sTipoGrid = sTipoGridActual;
   }

   if (sTipoGrid == TIPO_GRID_FIRMAR){
       document.getElementById("filamenu1").onclick = function() {respuesta()};
       document.getElementById("filamenu4").onclick = function() {respuesta()};
       document.getElementById("filamenu2").onclick = function() {respuesta()};
       document.getElementById("filamenu6").onclick = function() {respuesta()};
       if (bandejaJefe!=null && bandejaJefe!=undefined && bandejaJefe=="4"){
          document.getElementById("filamenu5").onclick = function() {respuesta()};
       }   
       document.getElementById("filamenu3").onclick = function() {respuesta()};
       document.getElementById("filamenu21").onclick = function() {respuesta()};
       document.getElementById("filamenu22").onclick = function() {respuesta()};
       if (document.getElementById("filamenu24")!=null){
         document.getElementById("filamenu24").onclick = function() {respuesta()};
       }  
   }
    
   if (sTipoGrid == TIPO_GRID_RECIBIDOS){
       try{
            document.getElementById("filamenu23").onclick = function() {respuesta()};
            document.getElementById("filamenu4").onclick = function() {respuesta()};
            document.getElementById("filamenu2").onclick = function() {respuesta()};
            document.getElementById("filamenu6").onclick = function() {respuesta()};
            if (bandejaJefe!=null && bandejaJefe!=undefined && bandejaJefe=="4"){
               document.getElementById("filamenu5").onclick = function() {respuesta()};
           }   
            document.getElementById("filamenu3").onclick = function() {respuesta()};
            document.getElementById("filamenu21").onclick = function() {respuesta()};
            document.getElementById("filamenu22").onclick = function() {respuesta()};
       }catch(error){
           
       }
   }
   
   if (sTipoGrid == TIPO_GRID_EXPEDIENTES){
       document.getElementById("filamenu23").onclick = function() {respuesta()};
       document.getElementById("filamenu4").onclick = function() {respuesta()};
       document.getElementById("filamenu1").onclick = function() {respuesta()};
       document.getElementById("filamenu6").onclick = function() {respuesta()};
       if (bandejaJefe!=null && bandejaJefe!=undefined && bandejaJefe=="4"){
         document.getElementById("filamenu5").onclick = function() {respuesta()};
       }  
       document.getElementById("filamenu3").onclick = function() {respuesta()};
       document.getElementById("filamenu21").onclick = function() {respuesta()};
       document.getElementById("filamenu22").onclick = function() {respuesta()};
   }
   
   if (sTipoGrid == TIPO_GRID_PENDIENTES){
       document.getElementById("filamenu23").onclick = function() {respuesta()};
       document.getElementById("filamenu4").onclick = function() {respuesta()};
       document.getElementById("filamenu2").onclick = function() {respuesta()};
       document.getElementById("filamenu1").onclick = function() {respuesta()};
       document.getElementById("filamenu6").onclick = function() {respuesta()};
       document.getElementById("filamenu3").onclick = function() {respuesta()};
       document.getElementById("filamenu21").onclick = function() {respuesta()};
       document.getElementById("filamenu22").onclick = function() {respuesta()};
   }
   
   if (sTipoGrid == TIPO_GRID_ENVIADOS){
       document.getElementById("filamenu23").onclick = function() {respuesta()};
       document.getElementById("filamenu4").onclick = function() {respuesta()};
       if (document.getElementById("filamenu24")!=null){
           document.getElementById("filamenu24").onclick = function() {respuesta()};
       }
       document.getElementById("filamenu1").onclick = function() {respuesta()};
       document.getElementById("filamenu2").onclick = function() {respuesta()};
       document.getElementById("filamenu3").onclick = function() {respuesta()};
       document.getElementById("filamenu21").onclick = function() {respuesta()};
       document.getElementById("filamenu22").onclick = function() {respuesta()};
   }
   
   console.info("(showGridInbox) Tipo de grid1 a pintar [%d]", sTipoGrid);

   console.debug("sTipoGridActual:  "+sTipoGridActual);
   if(!(sTipoGrid==TIPO_GRID_RECIBIDOS) && recursoIG==1 && dijit.byId("borderContainerVisor")){
                      dijit.byId("borderContainerVisor").removeChild(dijit.byId("contentPaneVisor"));
		      dijit.byId("contentPaneVisor").destroyDescendants();
	              dijit.byId("contentPaneVisor").destroy();
		      dijit.byId("borderContainerVisor").destroy();

	              dijit.byId("borderContainerInbox").removeChild(dijit.byId("contentPaneGrid"));
		      dijit.byId("contentPaneGrid").destroyDescendants();
		      dijit.byId("contentPaneGrid").destroy();

		      dijit.byId("toolBarInbox").destroy();

		      dijit.byId("tabContainerDetail").removeChild(dijit.byId("contentPaneDetail"));
		      dijit.byId("contentPaneDetail").destroyDescendants();
		      dijit.byId("contentPaneDetail").destroy();
		      dijit.byId("tabContainerDetail").destroy();

		      dijit.byId("borderContainerInbox").destroy();
		      dijit.byId("borderContainerGeneral").destroy();
		      dijit.byId("tabContainerInbox").destroy();
		      buildInboxTemp(dijit.byId("borderContainerMain"));
	   }
   //fin

   //visorGerencial
   //Reconstruye borderContainerVisor, para un grid TIPO_GRID_RECIBIDOS, luego de que destruido
   if((sTipoGrid==TIPO_GRID_RECIBIDOS) && recursoIG==1 && vistaIG==true){
               dijit.byId("borderContainerInbox").removeChild(dijit.byId("contentPaneGrid"));
	       dijit.byId("contentPaneGrid").destroyDescendants();
	       dijit.byId("contentPaneGrid").destroy();

  	       dijit.byId("toolBarInbox").destroy();
               dijit.byId("tabContainerDetail").removeChild(dijit.byId("contentPaneDetail"));
	       dijit.byId("contentPaneDetail").destroyDescendants();
	       dijit.byId("contentPaneDetail").destroy();
	       dijit.byId("tabContainerDetail").destroy();

	       dijit.byId("borderContainerInbox").destroy();
	       dijit.byId("borderContainerGeneral").destroy();
	       dijit.byId("tabContainerInbox").destroy();
	       buildInboxIGTemp(dijit.byId("borderContainerMain"));
	       vistaIG=false;

	   }
   //fin

   //Agregado por German para que pinte un feedback mientras carga el grid
   removeToolBarInbox();

   var gridx=document.getElementById("gridInbox");
   if(gridx!=null){
      gridx.style.display="none";
   }
   var cargando=document.getElementById("cargando");
   if (cargando) {
      cargando.style.display="";
   }
   sTipoGridActual = sTipoGrid;

   //removeAndAddToolBarInbox(sTipoGrid);
   resetDetail();

   if (sTipoGrid == TIPO_GRID_RECIBIDOS) {
      console.info("(showGridInbox) Tipo de grid = TIPO_GRID_RECIBIDOS");
     // prepareAlertaData();
   }

   var columnaOrdenada=-1;
   var ascendente;
   dijit.byId("borderContainerGeneral").setTitle(getTabTitle(sTipoGrid));
  
   service.getDataGrid(sTipoGrid).addCallback(function(objJSON){
      if (objJSON.structure == undefined) {
         if (cargando) {
            dojo.byId("cargando").innerHTML = "NO HAY ESTRUCTURA PARA EL GRID SELECCIONADO";
         }
         
         window.location.href = "login.action" ;
         return;
      }

      if (dijit.byId("gridInbox")) {
         dijit.byId("gridInbox").destroy();
      }
      
      storeGrid = new dojo.data.ItemFileWriteStore({
         data : objJSON
      });
    
      
      if(tieneCheck()) {
     	  objJSON.structure.unshift({
            editable: true,
            field: "selected",
            name: "<input type='checkbox' id='chkHeader' name='chkHeader' onclick='selectAll()'>",
            noresize: true,
            type: dojox.grid.cells.Bool,
            width: "20px"
         });
      }
      
      var grid = new dojox.grid.DataGrid({
         id               : "gridInbox",
         jsId             : "gridInbox",
         canSort          : noSort,
         columnReordering : true,
         headerMenu       : dijit.byId("gridMenu"),
         onRowClick       : viewDetail,
         rowsPerPage      : 50,
         store            : storeGrid ,
         structure        : objJSON.structure
      }, document.createElement("div"));


      //ESTO DEBERIA IR EN GETALLSTRUCTURES(), MEJORAR ESTO!!!tipoalerta

      for (var i in grid.layout.cells) {
         if (grid.layout.cells[i].formato == "formatterDate") {
            grid.layout.cells[i].formatter = formatterDate;
         }
         else if (grid.layout.cells[i].formato == "formatterImg") {
            grid.layout.cells[i].formatter = formatterImg;
         }
         else if (grid.layout.cells[i].formato == "formatterLeido") {
            grid.layout.cells[i].formatter = formatterLeido;
         }
         else if (grid.layout.cells[i].formato == "formatterAreas") {
            grid.layout.cells[i].formatter = formatterAreas;
         }
         else if (grid.layout.cells[i].formato == "formatterDocumentos") {
            grid.layout.cells[i].formatter = formatterDocumentos;
         }
         else if (grid.layout.cells[i].formato == "formatterTipoNumeracion") {
            grid.layout.cells[i].formatter = formatterTipoNumeracion;
         }
         else if (grid.layout.cells[i].formato == "formatterTipoDocumento") {
            grid.layout.cells[i].formatter = formatterTipoDocumento;
         }
         else if (grid.layout.cells[i].formato == "formatterVirtual") {
            grid.layout.cells[i].formatter = formatterVirtual;
         }else if (grid.layout.cells[i].formato == "formatterCargo") {
            grid.layout.cells[i].formatter = formatterCargo;
         }else if (grid.layout.cells[i].formato == "formatterDerivar") {
            grid.layout.cells[i].formatter = formatterDerivar;
         }else if (grid.layout.cells[i].formato == "formatterIntentos") {
            grid.layout.cells[i].formatter = formatterIntentos;
         } 
         if(grid.layout.cells[i].ordenado){
            columnaOrdenada=parseInt(i);
            ascendente=grid.layout.cells[i].ascendente;
         }
      }

      var cargando=document.getElementById("cargando");
      
       if (cargando) {
         cargando.style.display="none";
      }
      dojo.byId("contentPaneGrid").appendChild(grid.domNode);
      grid.startup();
      addToolBarInbox(sTipoGrid);

      dijit.byId("tabContainerInbox").selectChild(dijit.byId("borderContainerGeneral"));
      
      if(sTipoGrid == TIPO_GRID_ENVIADOS || sTipoGrid == TIPO_GRID_DOCUMENTOS_ARCHIVADOS || sTipoGrid == TIPO_GRID_SEGUIMIENTO || columnaOrdenada>0){
         grid.setSortIndex(columnaOrdenada,ascendente);
      }
     
      if(sTipoGrid == TIPO_GRID_INFORMATIVOS){
        dijit.byId("borderContainerGeneral").setTitle(dijit.byId("borderContainerGeneral").attr("title")+" [" + grid.store._arrayOfAllItems.length + " informativo(s)]");
      }else{ 
         var dato = dijit.byId("borderContainerGeneral").attr("title");
         var posicion = dato.indexOf("[");
         
         if (posicion!=-1){
          dato = dato.substring(0, posicion);    
         }
         
         var dato_ = "";
         
         if (sTipoGrid == TIPO_GRID_MI_LEGAJO || sTipoGrid == TIPO_GRID_LEGAJO_COMPARTIDO){
            dato_ = " [" + grid.store._arrayOfAllItems.length + " expediente(s)]"; 
         }else{
            if (sTipoGrid == TIPO_GRID_RECEPCION_VIRTUAL){
                dijit.byId("gridInbox").setQuery(
			{
		   	  idEstado: "P" 
			},
			{
			  ignoreCase: true
		        });
                var grid = dijit.byId("gridInbox");
                var contador = 0;
                grid.store.fetch({query : { idEstado: "P"},
                  onItem : function (item ) {
                    contador ++;
                  }
                });   
                
                dato_ = " [" + contador + " documento(s)]";
            }else{
                     if (sTipoGrid == TIPO_GRID_DESPACHO_VIRTUAL){
                        dijit.byId("gridInbox").setQuery(
                                {
                                  idEstado: "P" 
                                },
                                {
                                  ignoreCase: true
                                });

                        var grid = dijit.byId("gridInbox");
                        var contador = 0;
                        grid.store.fetch({query : { idEstado: "P"},
                          onItem : function (item ) {
                            contador ++;
                          }
                      });
                        dato_ = " [" + contador + " documento(s)]"; 
                 }else{
                    
                    if(objGridActual == TIPO_GRID_FIRMAR && sTipoGrid == TIPO_GRID_FIRMAR)
                    {
                        dijit.byId("idFiltroTipoDocumento").attr("value",objTipoDocumento);
                        dijit.byId("gridInbox").setQuery(
                            {
                                tipodocumento: objTipoDocumento == '-1'? "*" :objTipoDocumento
                            },
                            {
                                ignoreCase: true
                        });
                        var grid = dijit.byId("gridInbox");
                        var contador = 0;
                        grid.store.fetch({query : { tipodocumento: objTipoDocumento == '-1'? "*" :objTipoDocumento},
                          onItem : function (item ) {
                            contador ++;
                          }
                        });   
                        dato_ = " [" + contador + " documento(s)]"; 
                    }else
                    {
                        dato_ = " [" + grid.store._arrayOfAllItems.length + " documentos(s)]"; 
                    } 
                }      
            }    
         }
         
         dijit.byId("borderContainerGeneral").setTitle(dato + dato_);
      }

      if(recursoIG==1 && dijit.byId("dlgProgresBar")){
   	   dijit.byId("dlgProgresBar").hide() ;
         }
         
       if (sTipoGrid == TIPO_GRID_FIRMAR){
         document.getElementById("filamenu1").onclick = function() {accion(1)};
         document.getElementById("filamenu4").onclick = function() {accion(4)};
         document.getElementById("filamenu2").onclick = function() {accion(2)};
         document.getElementById("filamenu6").onclick = function() {accion(6)};
         if (bandejaJefe!=null && bandejaJefe!=undefined && bandejaJefe=="4"){
           document.getElementById("filamenu5").onclick = function() {accion(5)};
         }
         document.getElementById("filamenu3").onclick = function() {accion(3)};
         document.getElementById("filamenu21").onclick = function() {accion(21)};
         document.getElementById("filamenu22").onclick = function() {accion(22)}; 
         if (document.getElementById("filamenu24")!=null){
           document.getElementById("filamenu24").onclick = function() {accion(24)};
         }
       }
       
       if (sTipoGrid == TIPO_GRID_RECIBIDOS){
         document.getElementById("filamenu23").onclick = function() {accion(23)};
         document.getElementById("filamenu4").onclick = function() {accion(4)};
         document.getElementById("filamenu2").onclick = function() {accion(2)};
         document.getElementById("filamenu6").onclick = function() {accion(6)};
         if (bandejaJefe!=null && bandejaJefe!=undefined && bandejaJefe=="4"){
           document.getElementById("filamenu5").onclick = function() {accion(5)};
         }  
         document.getElementById("filamenu3").onclick = function() {accion(3)};
         document.getElementById("filamenu21").onclick = function() {accion(21)};
         document.getElementById("filamenu22").onclick = function() {accion(22)};         
       }
       
       if (sTipoGrid == TIPO_GRID_EXPEDIENTES){
         document.getElementById("filamenu1").onclick = function() {accion(1)};
         document.getElementById("filamenu4").onclick = function() {accion(4)};
         if (document.getElementById("filamenu23")!=null){
           document.getElementById("filamenu23").onclick = function() {accion(23)};
         }  
         document.getElementById("filamenu6").onclick = function() {accion(6)};
         if (bandejaJefe!=null && bandejaJefe!=undefined && bandejaJefe=="4"){
            document.getElementById("filamenu5").onclick = function() {accion(5)};
         }
         document.getElementById("filamenu3").onclick = function() {accion(3)};
         document.getElementById("filamenu21").onclick = function() {accion(21)};
         document.getElementById("filamenu22").onclick = function() {accion(22)};  
        // document.getElementById("filamenu2").onclick = function() {accion(2)};
       }   
       
       if (sTipoGrid == TIPO_GRID_PENDIENTES){
         document.getElementById("filamenu1").onclick = function() {accion(1)};
         document.getElementById("filamenu4").onclick = function() {accion(4)};
         document.getElementById("filamenu23").onclick = function() {accion(23)};
         document.getElementById("filamenu6").onclick = function() {accion(6)};
         document.getElementById("filamenu2").onclick = function() {accion(2)};
         document.getElementById("filamenu3").onclick = function() {accion(3)};
         document.getElementById("filamenu21").onclick = function() {accion(21)};
         document.getElementById("filamenu22").onclick = function() {accion(22)};         
       }   
       
       if (sTipoGrid == TIPO_GRID_ENVIADOS){
            document.getElementById("filamenu23").onclick = function() {accion(23)};
            document.getElementById("filamenu4").onclick = function() {accion(4)};
            if (document.getElementById("filamenu24")!=null){
              document.getElementById("filamenu24").onclick = function() {accion(24)};
            }
            document.getElementById("filamenu1").onclick = function() {accion(1)};
            document.getElementById("filamenu2").onclick = function() {accion(2)};
            document.getElementById("filamenu3").onclick = function() {accion(3)};
            document.getElementById("filamenu21").onclick = function() {accion(21)};
            document.getElementById("filamenu22").onclick = function() {accion(22)};
        }
       
       service.getCantidadDocumentosVirtuales(sTipoGrid).addCallback(function(respuesta){
            if (sTipoGrid == TIPO_GRID_RECEPCION_VIRTUAL){
                dijit.byId("lblFiltrarContador").attr("label", respuesta);                                                     
            }

            if (sTipoGrid == TIPO_GRID_DESPACHO_VIRTUAL){
                dijit.byId("lblFiltrarContador").attr("label", respuesta);
            }
       });     
   });
};

var showGridInboxByUser = function(idUsuario, nombreUsuario) {
    buildSharedInbox(idUsuario, nombreUsuario);

   // console.debug("(showGridInboxByUser) contentPaneGrid->" + dojo.byId("contentPaneGrid"+idUsuario).id);
    var sTipoGrid = TIPO_GRID_RECIBIDOS;
    console.info("(showGridInboxByUser) Tipo de grid a pintar [%d], idUsuario=%d", sTipoGrid, idUsuario);

    //Agregado por German para que pinte un feedback mientras carga el grid
    //   removeToolBarInbox();
    //   var gridx=document.getElementById("gridInbox"+idUsuario);
    //   console.debug("(showGridInboxByUser) gridx->" + gridx);
    //   if(gridx!=null){
    //      gridx.style.display="none";
    //   }
    var cargando=document.getElementById("cargando");
    if (cargando) {
        cargando.style.display="";
    }
    sTipoGridActual = sTipoGrid;

    var columnaOrdenada=-1;
    var ascendente;
    //    dijit.byId("borderContainerInbox").setTitle(getTabTitle(sTipoGrid));
    service.getDataGridByUser(sTipoGrid, idUsuario).addCallback(function(objJSON){
        if (objJSON.structure == undefined) {
            if (cargando) {
                dojo.byId("cargando").innerHTML = "NO HAY ESTRUCTURA PARA EL GRID SELECCIONADO";
            }
            return;
        }


        console.debug("(showGridInboxByUser) Numero de registros recibidos [%d]", objJSON.items.length);

        storeGrid = new dojo.data.ItemFileWriteStore({
            data : objJSON
        });

        objJSON.structure.unshift({
            editable: true,
            field: "expBtn",
            name: "Ver",
            formatter: formatterButton,
            width: "40px"
        });

        if(tieneCheck()) {
            var check = "<input type='checkbox' id='chkHeader" + idUsuario + "' name='chkHeader" + idUsuario + "' onclick='selectAllByUser("+ idUsuario + ")'>";
            console.debug("(showGridInboxByUser) check->" + check);
            objJSON.structure.unshift({
                editable: true,
                field: "selected",
                name: check,
                noresize: true,
                type: dojox.grid.cells.Bool,
                width: "20px"
            });
        }

        if (dijit.byId("gridInbox" + idUsuario)) {
            dijit.byId("gridInbox" + idUsuario).destroy(true);
            console.debug("(showGridInboxByUser) gridInbox" + idUsuario + " destruido");
        }

        var grid = new dojox.grid.DataGrid({
            id               : "gridInbox" + idUsuario,
            jsId             : "gridInbox" + idUsuario,
            canSort          : noSort,
            columnReordering : true,
            headerMenu       : dijit.byId("gridMenu"),
            onRowClick       : viewDetailByUser,
            onRowMouseOver      : function(e){
                idUser = idUsuario;
            },
            rowsPerPage      : 50,
            store            : storeGrid,
            structure        : objJSON.structure
        }, document.createElement("div"));


        //ESTO DEBERIA IR EN GETALLSTRUCTURES(), MEJORAR ESTO!!!tipoalerta

        for (var i in grid.layout.cells) {
            if (grid.layout.cells[i].formato == "formatterDate") {
                grid.layout.cells[i].formatter = formatterDate;
            } else if (grid.layout.cells[i].formato == "formatterImg") {
                grid.layout.cells[i].formatter = formatterImg;
            }
            else if (grid.layout.cells[i].formato == "formatterLeido") {
                grid.layout.cells[i].formatter = formatterLeido;
            }
            else if (grid.layout.cells[i].formato == "formatterTipoNumeracion") {
                grid.layout.cells[i].formatter = formatterTipoNumeracion;
            }
            else if (grid.layout.cells[i].formato == "formatterTipoDocumento") {
                grid.layout.cells[i].formatter = formatterTipoDocumento;
            }
            if(grid.layout.cells[i].ordenado){
                columnaOrdenada=parseInt(i);
                ascendente=grid.layout.cells[i].ascendente;
            }
        }
        //      dijit.byId("borderContainerMain").startup();
        var cargando=document.getElementById("cargando");
        if (cargando) {
            cargando.style.display="none";
        }

        dojo.byId("contentPaneGrid"+idUsuario).appendChild(grid.domNode);
        grid.startup();
        console.debug("(showGridInboxByUser) Grid agregado e iniciado");
        //      dojo.byId("contentPaneGrid"+idUsuario)        addToolBarInbox(sTipoGrid);
        dijit.byId("tabContainerInbox").selectChild(dijit.byId("borderContainerInbox"+idUsuario));
        console.debug("(showGridInboxByUser) Tab seleccionado");
       // dijit.byId("toolBarInbox").addChild(dijit.byId("btnImprimirBandejaCompartidos"));
        dijit.byId("toolBarInbox").addChild(dijit.byId("btnDerivarMasivamente"));
        buildSharedToolbar(idUsuario);

    //        console.debug("%s %s",columnaOrdenada,ascendente);
    //        if(columnaOrdenada>0){
    //            grid.setSortIndex(columnaOrdenada,ascendente);
    //        }
    //        if (sTipoGrid == TIPO_GRID_RECIBIDOS) {
    //            contarExpedientes(objJSON.items);
    //        }
    });
};

var showGridInbox3 = function(sTipoGrid) {
   console.info("(showGridInbox3) Tipo de grid a pintar [%d]", sTipoGrid);

   //Agregado por German para que pinte un feedback mientras carga el grid
   removeToolBarInbox();
   var gridx=document.getElementById("gridInbox");
   if(gridx!=null){
      gridx.style.display="none";
   }
   var cargando=document.getElementById("cargando");
   cargando.style.display="";

   sTipoGridActual = sTipoGrid;

   //removeAndAddToolBarInbox(sTipoGrid);
   resetDetail();

   if (sTipoGrid == TIPO_GRID_RECIBIDOS) {
      prepareAlertaData();
   }

   dijit.byId("borderContainerGeneral").setTitle(getTabTitle(sTipoGrid));
   service.getDataGrid(sTipoGrid).addCallback(function(objJSON) {
      if (dijit.byId("gridInbox")) {
         dijit.byId("gridInbox").destroy();
      }

      console.debug("(showGridInbox) Numero de registros recibidos [%d]", objJSON.items.length);

      storeGrid = new dojo.data.ItemFileWriteStore({
         data : objJSON
      });

      if (tieneCheck()){
         objJSON.structure.unshift({
            editable: true,
            field: "selected",
            name: "<input type='checkbox' id='chkHeader' name='chkHeader' onclick='selectAll()'>",
            noresize: true,
            type: dojox.grid.cells.Bool,
            width: "20px"
         });
      }

      var grid = new dojox.grid.DataGrid({
         id               : "gridInbox",
         jsId             : "gridInbox",
         canSort          : noSort,
         columnReordering : true,
         headerMenu       : dijit.byId("gridMenu"),
         onRowClick       : viewDetail,
         rowsPerPage      : 50,
         store            : storeGrid,
         //structure      : arrStructures[sTipoGrid]
         structure        : objJSON.structure
      }, document.createElement("div"));


      //ESTO DEBERIA IR EN GETALLSTRUCTURES(), MEJORAR ESTO!!!
      for (var i in grid.layout.cells) {
         if (grid.layout.cells[i].formato == "formatterDate") {
            grid.layout.cells[i].formatter = formatterDate;
         } //else if (grid.layout.cells[i].formato == "formatterLink") {
         //grid.layout.cells[i].formatter = formatterLink;
         //}
         else if (grid.layout.cells[i].formato == "formatterImg") {
            grid.layout.cells[i].formatter = formatterImg;
         } else if (grid.layout.cells[i].formato == "formatterLeido") {
            grid.layout.cells[i].formatter = formatterLeido;
         } else if (grid.layout.cells[i].formato == "formatterTipoNumeracion") {
            grid.layout.cells[i].formatter = formatterTipoNumeracion;
         }
      }
      var cargando=document.getElementById("cargando");
      cargando.style.display="none";
      dojo.byId("contentPaneGrid").appendChild(grid.domNode);
      grid.startup();
      addToolBarInbox(sTipoGrid);
   //dijit.byId("tabContainerInbox").selectChild(dijit.byId("borderContainerInbox"));


   });
   try {
      getUnread();
   }catch (err) {
      console.debug("error getUnread "+err.name +" : "+err.message);
   }
//cargargrid =true ;

};

var eliminarRegistro = function() {
       if(dijit.byId("tabDXE")){
          dijit.byId("tabDXE").attr("href", dijit.byId("tabDXE").attr("href"));
	}
        
        var grilleta  =dijit.byId("gridInbox");
        var items = grilleta.selection.getSelected();
        

        if (sTipoGridActual == TIPO_GRID_RECEPCION_VIRTUAL ||  sTipoGridActual == TIPO_GRID_DESPACHO_VIRTUAL){
             showGridInbox(sTipoGridActual);
        }else{
                if (items.length) {
                    dojo.forEach(items, function(selectedItem) {
                       if (selectedItem !== null) {
                          dijit.byId("contentPaneDetail").setHref("blank.action?v=" + new Date());

                          if (dijit.byId("contentPaneDocumentosAdicionales")) {
                                dijit.byId("tabContainerDetail").removeChild(dijit.byId("contentPaneDocumentosAdicionales"));
                                dijit.byId("contentPaneDocumentosAdicionales").destroy();
                          }

                           if (dijit.byId("contentPaneDocumentosLegajo")) {
                                dijit.byId("tabContainerDetail").removeChild(dijit.byId("contentPaneDocumentosLegajo"));
                                dijit.byId("contentPaneDocumentosLegajo").destroy();
                            }

                            if (dijit.byId("contentPaneDocumentosReferenciales")) {
                                dijit.byId("tabContainerDetail").removeChild(dijit.byId("contentPaneDocumentosReferenciales"));
                                dijit.byId("contentPaneDocumentosReferenciales").destroy();
                            }

                            if (dijit.byId("contentPaneDocumentosExternos")) {
                                dijit.byId("tabContainerDetail").removeChild(dijit.byId("contentPaneDocumentosExternos"));
                                dijit.byId("contentPaneDocumentosExternos").destroy();
                            }

                            storeGrid.deleteItem(selectedItem);

                       } 
                    }); // end forEach


                    var titulo = dijit.byId("borderContainerGeneral").attr("title");
                    var numero = new Number(titulo.substring(titulo.indexOf("[")+1, titulo.indexOf(" ", titulo.indexOf("[")+2)));
                    titulo = titulo.substring(0, titulo.indexOf("[")) + " [" + (numero-items.length);

                    if(sTipoGridActual == TIPO_GRID_INFORMATIVOS){
                       titulo += " informativo(s)]";
                    }else{
                       titulo += " documento(s)]";
                    }

                    dijit.byId("borderContainerGeneral").setTitle(titulo);

                    if(sTipoGridActual == TIPO_GRID_FIRMAR){
                         showGridInbox(sTipoGridActual);
                    }else{
                        grilleta.selection.deselectAll();
                    }
                } 
        }        
};


var showGridInbox2 = function(sTipoGrid) {

   console.info("(showGridInbox2) Tipo de grid a pintar [%d]", sTipoGrid);

   //Agregado por German para que pinte un feedback mientras carga el grid
   removeToolBarInbox();
   var grid=document.getElementById("gridInbox");
   if(grid!=null){
      grid.style.display="none";
   }
   var cargando=document.getElementById("cargando");
   cargando.style.display="";

   sTipoGridActual = sTipoGrid;

   //removeAndAddToolBarInbox(sTipoGrid);
   resetDetail();

   if (sTipoGrid == TIPO_GRID_RECIBIDOS) {
      prepareAlertaData();
   }

   dijit.byId("borderContainerGeneral").setTitle(getTabTitle(sTipoGrid));

   service.getDataGrid(sTipoGrid).addCallback(function(objJSON) {
      if (dijit.byId("gridInbox")) {
         dijit.byId("gridInbox").destroy();
      }

      console.debug("(showGridInbox) Numero de registros recibidos [%d]", objJSON.items.length);

      storeGrid = new dojo.data.ItemFileWriteStore({
         data : objJSON
      });

      var grid = new dojox.grid.DataGrid({
         id            : "gridInbox",
         jsId          : "gridInbox",
         canSort       : noSort,
         headerMenu    : dijit.byId("gridMenu"),
         //onRowClick    : viewDetail,
         rowsPerPage   : 50,
         store         : storeGrid,
         structure     : arrStructures[sTipoGrid]
      }, document.createElement("div"));

      //ESTO DEBERIA IR EN GETALLSTRUCTURES(), MEJORAR ESTO!!!
      for (var i in grid.layout.cells) {
         if (grid.layout.cells[i].formato == "formatterDate") {
            grid.layout.cells[i].formatter = formatterDate;
         } else if (grid.layout.cells[i].formato == "formatterLink") {
            grid.layout.cells[i].formatter = formatterLink;
         } else if (grid.layout.cells[i].formato == "formatterImg") {
            grid.layout.cells[i].formatter = formatterImg;
         } else if (grid.layout.cells[i].formato == "formatterLeido") {
            grid.layout.cells[i].formatter = formatterLeido;
         } else if (grid.layout.cells[i].formato == "formatterTipoNumeracion") {
            grid.layout.cells[i].formatter = formatterTipoNumeracion;
         }
      }

      var cargando=document.getElementById("cargando");
      cargando.style.display="none";
      dojo.byId("contentPaneGrid").appendChild(grid.domNode);
      grid.startup();
      addToolBarInbox(sTipoGrid);
   ///  dijit.byId("tabContainerInbox").selectChild(dijit.byId("borderContainerInbox"));
   });
};
var resetTabs = function(iTipoGrid) {
   var grid = dijit.byId("gridInbox");
   try{
      console.info("(showGridInbox) resetTabs Tipo de grid a pintar [%d]. Grid [%s]", iTipoGrid, grid.id);
   }catch(err){
      console.info("erro on resetTabs:"+err.name + " : "+err.message);
   }
   sTipoGridActual = iTipoGrid;

   removeAndAddToolBarInbox(iTipoGrid);
   resetDetail();

   service.getDataGrid(iTipoGrid).addCallback(function(objJSON) {
      grid.attr("structure", arrStructures[iTipoGrid]);

      //ESTO DEBERIA IR EN GETALLSTRUCTURES(), MEJORAR ESTO!!!
      for(var i in grid.layout.cells){
         if (grid.layout.cells[i].formato == 'formatterDate') {
            grid.layout.cells[i].formatter = formatterDate;
         }
         else if (grid.layout.cells[i].formato == 'formatterLink') {
            grid.layout.cells[i].formatter = formatterLink;
         }
         else if(grid.layout.cells[i].formato=="formatterImg") {
            grid.layout.cells[i].formatter = formatterImg;
         }
      }

      storeGrid = new dojo.data.ItemFileWriteStore({
         data : objJSON
      });

      grid.setStore(storeGrid);
      grid.selection.deselectAll();

   //dijit.byId("tabContainerInbox").selectChild(dijit.byId("borderContainerInbox"));
   });
};

var updateLeido = function(rowIndex) {
   var grid = dijit.byId("gridInbox");
   var row = grid.getItem(rowIndex);
   var sTipoGrid = null;
   var valor = row.id;
   
   if (row.leido == 'N' || row.leido == '' || row.leido == null ) {
      grid.store.setValue(grid.getItem(rowIndex), "leido", 'S');
      grid.store.save();

      //MEJORAR ESTO!!!
      if (sTipoGridActual == TIPO_GRID_RECIBIDOS || sTipoGridActual == TIPO_GRID_EXPEDIENTES) {
         sTipoGrid = "UsuFinMnuDocRec";
      } else if (sTipoGridActual == TIPO_GRID_NOTIFICACIONES) {
         sTipoGrid = "UsuFinMnuNotif";
      } else if (sTipoGridActual == TIPO_GRID_INFORMATIVOS) {
         sTipoGrid = "UsuFinMnuInfo";
      }else if (sTipoGridActual == TIPO_GRID_FIRMAR) {
         sTipoGrid = "UsuFinMnuFirma";
      }else if (sTipoGridActual == TIPO_GRID_MI_RECEPCION_VIRTUAL) {
         sTipoGrid = "UsuFinMnuMisRecVirtual";
      }else if (sTipoGridActual == TIPO_GRID_RECEPCION_VIRTUAL_OBSERVADOS){
         sTipoGrid = "UsuFinMnuRecObs"; 
      } 

      service.updateRead(parseInt(valor), sTipoGrid).addCallback(function(result) {
         //if (result == "done") {
         getUnread();
      //}
      });
   }
};

var updateNumero = function(numero){
	var grid = dijit.byId("gridInbox");
	var item;
	if(dijit.byId("gridDXE")){
		grid = dijit.byId("gridDXE");
		item = grid.getItem(rowGridDXEIndex);
	}else{
		item = grid.getItem(rowGridIndex);
	}
	grid.store.setValue(item, "documento", numero);
    grid.store.save();
};

var updateLeidoByUser = function(rowIndex, idUsuario) {

	return false;

   var grid = dijit.byId("gridInbox"+idUsuario);
   var row = grid.getItem(rowIndex);
   var sTipoGrid = null;

   console.debug("(updateLeidoByUser) Leido [%s]", row.leido);

   if (row.leido == 'N' || row.leido == '' || row.leido == null ) {
      grid.store.setValue(grid.getItem(rowIndex), "leido", 'S');
      grid.store.save();

      //MEJORAR ESTO!!!
      if (sTipoGridActual == TIPO_GRID_RECIBIDOS) {
         sTipoGrid = "UsuFinMnuDocRec";
      } else if (sTipoGridActual == TIPO_GRID_NOTIFICACIONES) {
         sTipoGrid = "UsuFinMnuNotif";
      } else if (sTipoGridActual == TIPO_GRID_INFORMATIVOS) {
         sTipoGrid = "UsuFinMnuInfo";
      }

      service.updateRead(parseInt(row.id), sTipoGrid).addCallback(function(result) {
         //if (result == "done") {
         getUnread();
      //}
      });
   }
};

var updateLeido2 = function(rowItem) {

   var grid = dijit.byId("gridInbox");
   var row = rowItem ;
   var sTipoGrid = null;

   console.debug("(updateLeido) Leido [%s]", row.leido);

   if (row.leido == 'N' || row.leido == '') {

      grid.store.setValue(rowItem, "leido", 'S');
      grid.store.save();

      //MEJORAR ESTO!!!
      if (sTipoGridActual == TIPO_GRID_RECIBIDOS) {
         sTipoGrid = "UsuFinMnuDocRec";
      } else if (sTipoGridActual == TIPO_GRID_NOTIFICACIONES) {
         sTipoGrid = "UsuFinMnuNotif";
      }

      service.updateRead(parseInt(row.id), sTipoGrid).addCallback(function(result) {
         if (result == "done") {
            getUnread();
         }
      });
   }
};

var viewDetailPruebita=function(e){
  
}

var viewDetailPersonalizado = function(valor){
     resetDetail();
    
     if(sTipoGridActual==TIPO_GRID_RECIBIDOS || sTipoGridActual==TIPO_GRID_EXPEDIENTES){
            var idDocumento = valor;
            var sURL="doViewDocUsuarioFinal.action?iIdDoc=";
            buildTabDocumentosAdicionales("doViewDocReferenciales.action?iIdDoc=" + idDocumento +"&regresar=enviados&idGrid="+sTipoGridActual,"contentPaneDocumentosReferenciales","Documentos Vinculados");
            buildTabDocumentosAdicionales("doViewDocAdicionales.action?iIdDoc=" + idDocumento +"&regresar=enviados&idGrid="+sTipoGridActual,"contentPaneDocumentosAdicionales","Carpeta ");
            service.verificarDocumentoPublicar(parseInt(idDocumento), "R").addCallback(function(result) {
                if (result.substr(0,1) == "S") {  
                    buildTabDocumentosAdicionales("doViewDocArchivos.action?iIdDoc=" + idDocumento +"&regresar=enviados&idGrid="+sTipoGridActual,"contentPaneDocumentosExternos","Documentos Publicar"); 
                }
                if (result.substr(1,2) == "S") {
                    buildTabDocumentosAdicionales("doViewDocLegajo.action?iIdDoc=" + idDocumento +"&regresar=enviados&idGrid="+sTipoGridActual,"contentPaneDocumentosLegajo","Expediente"); 
                }
                dijit.byId("contentPaneDetail").setHref(sURL + idDocumento + "&idGrid=" + sTipoGridActual);
            });     
      }else if(sTipoGridActual==TIPO_GRID_FIRMAR){
            var idDocumento = valor;
            var sURL="doViewDocFirmado.action?idfirmados="; 
            buildTabDocumentosAdicionales("doViewDocReferenciales.action?idfirmados=" + idDocumento+"&regresar=enviados&idGrid="+sTipoGridActual,"contentPaneDocumentosReferenciales","Documentos Vinculados");
            buildTabDocumentosAdicionales("doViewDocAdicionales.action?idfirmados=" + idDocumento+"&regresar=enviados&idGrid="+sTipoGridActual,"contentPaneDocumentosAdicionales","Carpeta");
            service.verificarDocumentoPublicar(parseInt(idDocumento), "F").addCallback(function(result) {
                if (result.substr(0,1) == "S") {  
                    buildTabDocumentosAdicionales("doViewDocArchivos.action?iIdDoc=" + idDocumento +"&regresar=enviados&idGrid="+sTipoGridActual,"contentPaneDocumentosExternos","Documentos Publicar"); 
                }

                if (result.substr(1,2) == "S") {
                    buildTabDocumentosAdicionales("doViewDocLegajo.action?iIdDoc=" + idDocumento+"&regresar=enviados&idGrid="+sTipoGridActual,"contentPaneDocumentosLegajo","Expediente"); 
                }
                
                dijit.byId("contentPaneDetail").setHref(sURL + idDocumento + "&idGrid=" + sTipoGridActual);
            });
      }else if (sTipoGridActual==TIPO_GRID_MI_LEGAJO){
           sURL="doViewExpUsuarioFinal.action?iIdLegajo=";   
           dijit.byId("contentPaneDetail").setHref(sURL + valor + "&idGrid=" + sTipoGridActual);
      }else if (sTipoGridActual==TIPO_GRID_LEGAJO_COMPARTIDO){
           sURL="doViewExpUsuarioFinal.action?iIdLegajo=";   
           dijit.byId("contentPaneDetail").setHref(sURL + valor + "&idGrid=" + sTipoGridActual);
      }
}      

var viewDetail=function(e){
   var sURL=null;
   var idDocumento = "";
   var idEnviado = "";
   var r = 0;
   var barra = true;
   rowGridIndex = e.rowIndex;
   
    if(tieneCheck() && (e.cellNode.cellIndex<=0 || e.cellNode.cellIndex>100))
      return; 
    
    if(sTipoGridActual==TIPO_GRID_RECIBIDOS || sTipoGridActual==TIPO_GRID_NOTIFICACIONES || sTipoGridActual==TIPO_GRID_INFORMATIVOS || sTipoGridActual==TIPO_GRID_RECIBIDOS || sTipoGridActual==TIPO_GRID_EXPEDIENTES || sTipoGridActual==TIPO_GRID_FIRMAR || sTipoGridActual==TIPO_GRID_MI_RECEPCION_VIRTUAL || sTipoGridActual==TIPO_GRID_RECEPCION_VIRTUAL_OBSERVADOS)
      updateLeido(e.rowIndex);
   
    resetDetail();

   if(sTipoGridActual==TIPO_GRID_CARPETA_BUSQUEDA){
      sURL="doViewDoc.action?iIdDoc="+ this.getItem(e.rowIndex).id+"&iIdPro=" + this.getItem(e.rowIndex).idproceso + "&avisopermiso=1&x=";
      buildTabDocumentosAdicionales("doViewDocAdicionales.action?iIdDoc=" + this.getItem(e.rowIndex).id + "&iIdPro=" + this.getItem(e.rowIndex).idproceso + "&avisopermiso=1&idGrid="+sTipoGridActual, "contentPaneDocumentosAdicionales", "Carpeta ");
   }else if(sTipoGridActual==TIPO_GRID_RECIBIDOS || sTipoGridActual==TIPO_GRID_EXPEDIENTES){
      idDocumento = this.getItem(e.rowIndex).id;
      sURL="doViewDocUsuarioFinal.action?iIdDoc=";
      buildTabDocumentosAdicionales("doViewDocReferenciales.action?iIdDoc=" + this.getItem(e.rowIndex).id+"&regresar=enviados&idGrid="+sTipoGridActual,"contentPaneDocumentosReferenciales","Documentos Vinculados");
      buildTabDocumentosAdicionales("doViewDocAdicionales.action?iIdDoc=" + this.getItem(e.rowIndex).id+"&regresar=enviados&idGrid="+sTipoGridActual,"contentPaneDocumentosAdicionales","Carpeta ");
      service.verificarDocumentoPublicar(parseInt(this.getItem(e.rowIndex).id), "R").addCallback(function(result) {
          if (result.substr(0,1) == "S") {  
              buildTabDocumentosAdicionales("doViewDocArchivos.action?iIdDoc=" + idDocumento +"&regresar=enviados&idGrid="+sTipoGridActual,"contentPaneDocumentosExternos","Documentos Publicar"); 
          }
          
          if (result.substr(1,2) == "S") {
              buildTabDocumentosAdicionales("doViewDocLegajo.action?iIdDoc=" + idDocumento +"&regresar=enviados&idGrid="+sTipoGridActual,"contentPaneDocumentosLegajo","Expediente "); 
          }
      }); 
   }else if(sTipoGridActual==TIPO_GRID_SEGUIMIENTO){
      sURL="doViewDocSeguimiento.action?idseguimientos=";
      buildTabDocumentosAdicionales("doViewDocReferenciales.action?idseguimientos=" + this.getItem(e.rowIndex).id+"&regresar=enviados&idGrid="+sTipoGridActual,"contentPaneDocumentosReferenciales","Documentos Vinculados"); 
      buildTabDocumentosAdicionales("doViewDocAdicionales.action?idseguimientos=" + this.getItem(e.rowIndex).id+"&regresar=enviados&idGrid="+sTipoGridActual,"contentPaneDocumentosAdicionales","Carpeta "); 
   }else if(sTipoGridActual==TIPO_GRID_DOCUMENTOS_ARCHIVADOS){ //ATENDIDOS.
      idDocumento = this.getItem(e.rowIndex).id;
      sURL="doViewDocAtendido.action?idatendidos="; 
      buildTabDocumentosAdicionales("doViewDocReferenciales.action?idatendidos=" + this.getItem(e.rowIndex).id+"&regresar=enviados&idGrid="+sTipoGridActual,"contentPaneDocumentosReferenciales","Documentos Vinculados");
      buildTabDocumentosAdicionales("doViewDocAdicionales.action?idatendidos=" + this.getItem(e.rowIndex).id+"&regresar=enviados&idGrid="+sTipoGridActual,"contentPaneDocumentosAdicionales","Carpeta ");
      service.verificarDocumentoPublicar(parseInt(idDocumento) , "A").addCallback(function(result) {
          if (result.substr(1,2) == "S") {
              buildTabDocumentosAdicionales("doViewDocLegajo.action?idatendidos=" + idDocumento+"&regresar=enviados&idGrid="+sTipoGridActual,"contentPaneDocumentosLegajo","Expediente "); 
          }
      });
   } else if(sTipoGridActual==TIPO_GRID_PENDIENTES){
      idDocumento = this.getItem(e.rowIndex).id;  
      sURL="doViewDocUsuarioFinal.action?iIdDoc=";
      buildTabDocumentosAdicionales("doViewDocReferenciales.action?iIdDoc=" + this.getItem(e.rowIndex).id+"&regresar=enviados&idGrid="+sTipoGridActual,"contentPaneDocumentosReferenciales","Documentos Vinculados");
      buildTabDocumentosAdicionales("doViewDocAdicionales.action?iIdDoc=" + this.getItem(e.rowIndex).id+"&regresar=enviados&idGrid="+sTipoGridActual,"contentPaneDocumentosAdicionales","Carpeta ");
      service.verificarDocumentoPublicar(parseInt(this.getItem(e.rowIndex).id), "R").addCallback(function(result) {
          if (result.substr(1,2) == "S") {
              buildTabDocumentosAdicionales("doViewDocLegajo.action?iIdDoc=" + idDocumento +"&regresar=enviados&idGrid="+sTipoGridActual,"contentPaneDocumentosLegajo","Expediente "); 
          }
      }); 
      /*sURL="doViewDocPendiente.action?idpendientes="; 
      buildTabDocumentosAdicionales("doViewDocReferenciales.action?idpendientes=" + this.getItem(e.rowIndex).id+"&regresar=enviados&idGrid="+sTipoGridActual,"contentPaneDocumentosReferenciales","Documentos Vinculados");
      buildTabDocumentosAdicionales("doViewDocAdicionales.action?idpendientes=" + this.getItem(e.rowIndex).id+"&regresar=enviados&idGrid="+sTipoGridActual,"contentPaneDocumentosAdicionales","Carpeta ");
      service.verificarDocumentoPublicar(parseInt(idDocumento) , "P").addCallback(function(result) {
          if (result.substr(1,2) == "S") {
              buildTabDocumentosAdicionales("doViewDocLegajo.action?idpendientes=" + idDocumento +"&regresar=enviados&idGrid="+sTipoGridActual,"contentPaneDocumentosLegajo","Expediente "); 
          }
      });*/
    }else if(sTipoGridActual==TIPO_GRID_ENVIADOS){
      idEnviado = this.getItem(e.rowIndex).id;  
      sURL="DocumentoEnviado_verDetalle.action?idDocumentoEnviado=";
      buildTabDocumentosAdicionales("doViewDocReferenciales.action?idenv=" + this.getItem(e.rowIndex).id+"&regresar=enviados&idGrid="+sTipoGridActual,"contentPaneDocumentosReferenciales","Documentos Vinculados");
      buildTabDocumentosAdicionales("doViewDocAdicionales.action?idenv=" + this.getItem(e.rowIndex).id+"&regresar=enviados&idGrid="+sTipoGridActual,"contentPaneDocumentosAdicionales","Carpeta ");    
      service.verificarDocumentoPublicar(parseInt(idEnviado) , "E").addCallback(function(result) {
          if (result.substr(1,2) == "S") {
              buildTabDocumentosAdicionales("doViewDocLegajo.action?idenv=" + idEnviado +"&regresar=enviados&idGrid="+sTipoGridActual,"contentPaneDocumentosLegajo","Expediente "); 
          }
      });
    }else if(sTipoGridActual==TIPO_GRID_INFORMATIVOS){
      sURL="doGetNotificacionDetail.action?iIdNotificacion=";
      buildTabDocumentosAdicionales("doViewDocReferenciales.action?iIdDoc=" + this.getItem(e.rowIndex).iddocumento+"&idGrid="+sTipoGridActual,"contentPaneDocumentosReferenciales","Documentos Vinculados");
      buildTabDocumentosAdicionales("doViewDocAdicionales.action?iIdDoc=" + this.getItem(e.rowIndex).iddocumento+"&idGrid="+sTipoGridActual, "contentPaneDocumentosAdicionales", "Carpeta ");
   }else if(sTipoGridActual==TIPO_GRID_ANULADOS){
      sURL="doViewDocAnulado.action?idanulados="; 
      buildTabDocumentosAdicionales("doViewDocReferenciales.action?idanulados=" + this.getItem(e.rowIndex).id+"&regresar=enviados&idGrid="+sTipoGridActual,"contentPaneDocumentosReferenciales","Documentos Vinculados");
      buildTabDocumentosAdicionales("doViewDocAdicionales.action?idanulados=" + this.getItem(e.rowIndex).id+"&regresar=enviados&idGrid="+sTipoGridActual,"contentPaneDocumentosAdicionales","Carpeta ");
    }else if(sTipoGridActual==TIPO_GRID_FIRMAR){
      idDocumento = this.getItem(e.rowIndex).id;
      sURL="doViewDocFirmado.action?idfirmados="; 
      buildTabDocumentosAdicionales("doViewDocReferenciales.action?idfirmados=" + this.getItem(e.rowIndex).id+"&regresar=enviados&idGrid="+sTipoGridActual,"contentPaneDocumentosReferenciales","Documentos Vinculados");
      buildTabDocumentosAdicionales("doViewDocAdicionales.action?idfirmados=" + this.getItem(e.rowIndex).id+"&regresar=enviados&idGrid="+sTipoGridActual,"contentPaneDocumentosAdicionales","Carpeta ");
      service.verificarDocumentoPublicar(parseInt(this.getItem(e.rowIndex).id), "F").addCallback(function(result) {
          if (result.substr(0,1) == "S") {  
              buildTabDocumentosAdicionales("doViewDocArchivos.action?iIdDoc=" + idDocumento +"&regresar=enviados&idGrid="+sTipoGridActual,"contentPaneDocumentosExternos","Documentos Publicar"); 
          }
          
          if (result.substr(1,2) == "S") {
              buildTabDocumentosAdicionales("doViewDocLegajo.action?iIdDoc=" + idDocumento + "&regresar=enviados&idGrid="+sTipoGridActual,"contentPaneDocumentosLegajo","Expediente "); 
          }
      });
   }else if (sTipoGridActual==TIPO_GRID_RECEPCION_VIRTUAL){
      sURL="doViewDocRecepcionVirtual.action?idRecepcion=";
   }else if (sTipoGridActual==TIPO_GRID_DESPACHO_VIRTUAL){
      sURL="doViewDocDespachoVirtual.action?idDespacho=";
   }else if(sTipoGridActual==TIPO_GRID_MI_RECEPCION_VIRTUAL){
      sURL="doViewDocUsuarioFinalRecepcion.action?iIdDoc="; 
      buildTabDocumentosAdicionales("doViewDocReferenciales.action?iIdDoc=" + this.getItem(e.rowIndex).id+"&regresar=enviados&idGrid="+sTipoGridActual,"contentPaneDocumentosReferenciales","Documentos Vinculados");
      buildTabDocumentosAdicionales("doViewDocAdicionales.action?iIdDoc=" + this.getItem(e.rowIndex).id+"&regresar=enviados&idGrid="+sTipoGridActual,"contentPaneDocumentosAdicionales","Carpeta ");
     
    }else if (sTipoGridActual==TIPO_GRID_MI_LEGAJO){
      barra = false;
      dijit.byId("dlgProgresBar").show() ;  
      sURL="doViewExpUsuarioFinal.action?iIdLegajo="; 
    }else if (sTipoGridActual==TIPO_GRID_LEGAJO_COMPARTIDO){
      barra = false;
      dijit.byId("dlgProgresBar").show() ;    
      sURL="doViewExpUsuarioFinal.action?iIdLegajo=";   
    }else if (sTipoGridActual==TIPO_GRID_RECEPCION_VIRTUAL_OBSERVADOS){
      sURL="doViewDocUsuarioFinal.action?iIdDoc=";
      buildTabDocumentosAdicionales("doViewDocReferenciales.action?iIdDoc=" + this.getItem(e.rowIndex).id+"&regresar=enviados&idGrid="+sTipoGridActual,"contentPaneDocumentosReferenciales","Documentos Vinculados");
      buildTabDocumentosAdicionales("doViewDocAdicionales.action?iIdDoc=" + this.getItem(e.rowIndex).id+"&regresar=enviados&idGrid="+sTipoGridActual,"contentPaneDocumentosAdicionales","Carpeta ");
    }
    
    for(r=0;r<100000;r++);

   if(sURL!=null){
      console.debug("URL Detail [%s]", sURL + this.getItem(e.rowIndex).id + "&idGrid=" + sTipoGridActual);
      dijit.byId("contentPaneDetail").setHref(sURL + this.getItem(e.rowIndex).id + "&idGrid=" + sTipoGridActual + "&fechaDetalle=" + new Date());
   }
   
   for(r=0;r<100000;r++);
   this.edit.rowClick(e);
   this.selection.clickSelectEvent(e);
   
   if (!barra){
        dijit.byId("dlgProgresBar").hide() ;  
   }
};

var viewDetailByUser=function(e){
    console.debug("(viewDetailByUser) idUser->" + idUser);
    var sURL=null;
    if(tieneCheck() && (e.cellNode.cellIndex<=0 || e.cellNode.cellIndex>100))
        return;

    if(sTipoGridActual==TIPO_GRID_RECIBIDOS || sTipoGridActual==TIPO_GRID_NOTIFICACIONES || sTipoGridActual==TIPO_GRID_INFORMATIVOS || sTipoGridActual==TIPO_GRID_RECIBIDOS || sTipoGridActual==TIPO_GRID_EXPEDIENTES)
        updateLeidoByUser(e.rowIndex, idUser);

    console.debug("(viewDetail) Grid [%s]. Tipo de grid actual [%d]. ID seleccionado [%d]", this.id, sTipoGridActual, this.getItem(e.rowIndex).id);
    resetDetail();
    if(sTipoGridActual==TIPO_GRID_CARPETA_BUSQUEDA){
        sURL="doViewDoc.action?iIdDoc="+ this.getItem(e.rowIndex).id+"&iIdPro=" + this.getItem(e.rowIndex).idproceso + "&avisopermiso=1&x=";
        buildTabDocumentosAdicionales("doViewDocAdicionales.action?iIdDoc=" + this.getItem(e.rowIndex).id + "&iIdPro=" + this.getItem(e.rowIndex).idproceso + "&avisopermiso=1&idGrid="+sTipoGridActual, "contentPaneDocumentosAdicionales", "Documentos ");
    }else if(sTipoGridActual==TIPO_GRID_RECIBIDOS){
        sURL="doViewDocUsuarioFinalBandejaCompartida.action?iIdDoc=";
        buildTabDocumentosAdicionalesByUser("doViewDocAdicionales.action?iIdDoc=" + this.getItem(e.rowIndex).id+"&regresar=enviados&idGrid="+sTipoGridActual,"contentPaneDocumentosAdicionales","Documentos ", idUser);

        console.debug("idproceso.. "+this.getItem(e.rowIndex).idproceso);
        idProcesoView = this.getItem(e.rowIndex).idproceso;
        idDocumento = this.getItem(e.rowIndex).id;
        service.verificarProceso(parseInt(this.getItem(e.rowIndex).idproceso)).addCallback(function(result) {
            if (result == "stor") {
                console.debug("dentro de stor antes de construir los otros tabs");
                buildTabDocumentosAdicionales("doViewInfoPrincipal.action?iIdDoc=" + idDocumento, "contentPaneDatosEnvio", "Informacion Principal");
                buildTabDocumentosAdicionales("doViewInfoComplementaria.action?iIdDoc=" + idDocumento, "contentPaneDatosCargo", "Informacion Complementaria");
            }
        });

    } else if(sTipoGridActual==TIPO_GRID_DOCUMENTOS_ARCHIVADOS){
        sURL="doViewDocUsuarioFinal.action?iIdDoc=";
        buildTabDocumentosAdicionales("doViewDocAdicionales.action?iIdDoc=" + this.getItem(e.rowIndex).id+"&regresar=enviados&idGrid="+sTipoGridActual,"contentPaneDocumentosAdicionales","Documentos ");
        console.debug("IdExpediente->"+this.getItem(e.rowIndex).id);
        console.debug("idproceso.. "+this.getItem(e.rowIndex).idproceso);
        idProcesoView = this.getItem(e.rowIndex).idproceso;
    } else if(sTipoGridActual==TIPO_GRID_PENDIENTES){
        if(this.getItem(e.rowIndex).estado=="ND"){
            sURL="NuevoDocumento_mostrarEdicion.action?idArchivoPendiente=";
        }else if(this.getItem(e.rowIndex).estado=="ED"){
            sURL="doPlantilla_verEdicion.action?idArchivoPendiente=";
        }
    }else if(sTipoGridActual==TIPO_GRID_ENVIADOS){
        sURL="DocumentoEnviado_verDetalle.action?idDocumentoEnviado=";
        buildTabDocumentosAdicionales("doViewDocAdicionales.action?idenv=" + this.getItem(e.rowIndex).id+"&regresar=enviados&idGrid="+sTipoGridActual,"contentPaneDocumentosAdicionales","Documentos ");
    }else if(sTipoGridActual==TIPO_GRID_MENSAJERIA){
        console.debug("tipo mensajeria e "+e.rowIndex);
        sURL="dofinddatosUser.action?Idmen=";
        buildTabDocumentosAdicionales("dofindkeyuser.action?Idmen=" + this.getItem(e.rowIndex).id, "contentPaneDatosEnvio", "Datos de Envio");
        buildTabDocumentosAdicionales("dofindkeycargouser.action?Idmen=" + this.getItem(e.rowIndex).id, "contentPaneDatosCargo", "Datos del Cargo");
        console.debug("tipo mensajeria e "+e.rowIndex+"despues");
    }else if(sTipoGridActual==TIPO_GRID_NOTIFICACIONES){
        sURL="doGetNotificacionDetail.action?iIdNotificacion=";
        buildTabDocumentosAdicionales("doViewDocAdicionales.action?iIdDoc=" + this.getItem(e.rowIndex).documento+"&idGrid="+sTipoGridActual, "contentPaneDocumentosAdicionales", "Documentos ");
    }else if(sTipoGridActual==TIPO_GRID_PROCESOS){
        sURL="doViewProceso.action?iIdProceso=";
    }else if(sTipoGridActual==TIPO_GRID_EXPEDIENTES){
        sURL="doViewExpediente.action?iIdExpediente=";
    }else if(sTipoGridActual==TIPO_GRID_REGISTRADOS_MP){
        //   alert("bbbbbbbbbbb") ;

        sURL="doViewDoc.action?iIdDoc=";
    }else if(sTipoGridActual==TIPO_GRID_INFORMATIVOS){
        sURL="doGetNotificacionDetail.action?iIdNotificacion=";
        buildTabDocumentosAdicionales("doViewDocAdicionales.action?iIdDoc=" + this.getItem(e.rowIndex).iddocumento+"&idGrid="+sTipoGridActual, "contentPaneDocumentosAdicionales", "Documento ");
    }else if (sTipoGridActual == TIPO_GRID_QAS_DIGITALIZADOS) {
        sURL = "doViewDoc.action?iIdDoc=";
    }else if (sTipoGridActual == TIPO_GRID_DIG_DOC_INGRESADOS) {
        sURL="doViewDoc.action?iIdDoc=";
    }else if (sTipoGridActual == TIPO_GRID_MENSAJERIA_RECIBIDOS) {
        buildTabDocumentosAdicionales("dofinddatos.action?Idmen=" + this.getItem(e.rowIndex).id, "contentPaneDatosDocumento", "Datos de Documento");
        buildTabDocumentosAdicionales("dofindkey.action?Idmen=" + this.getItem(e.rowIndex).id, "contentPaneDatosEnvio", "Datos de Envio");
        buildTabDocumentosAdicionales("dofindkeycargo.action?Idmen=" + this.getItem(e.rowIndex).id, "contentPaneDatosCargo", "Datos del Cargo");

        dijit.byId("tabContainerDetail").selectChild(dijit.byId("contentPaneDatosCargo"));
        dijit.byId("tabContainerDetail").selectChild(dijit.byId("contentPaneDatosEnvio"));
        dijit.byId("tabContainerDetail").selectChild(dijit.byId("contentPaneDatosDocumento"));

    }else if (sTipoGridActual == TIPO_GRID_MENSAJERIA_CERRADOS) {
        //alert("ooooo") ;
        buildTabDocumentosAdicionales("dofinddatos.action?Idmen=" + this.getItem(e.rowIndex).id, "contentPaneDatosDocumento", "Datos de Documento");
        buildTabDocumentosAdicionales("dofindkey.action?Idmen=" + this.getItem(e.rowIndex).id, "contentPaneDatosEnvio", "Datos de Envio");
        buildTabDocumentosAdicionales("dofindkeycargo.action?Idmen=" + this.getItem(e.rowIndex).id, "contentPaneDatosCargo", "Datos del Cargo");

        dijit.byId("tabContainerDetail").selectChild(dijit.byId("contentPaneDatosCargo"));
        dijit.byId("tabContainerDetail").selectChild(dijit.byId("contentPaneDatosEnvio"));
        dijit.byId("tabContainerDetail").selectChild(dijit.byId("contentPaneDatosDocumento"));
    }


    if(sURL!=null){
        console.debug("URL Detail [%s]", sURL + this.getItem(e.rowIndex).id);
        dijit.byId("contentPaneDetail"+idUser).setHref(sURL + this.getItem(e.rowIndex).id);

        console.debug("antes de la seleccion de hijos");

    }
    this.edit.rowClick(e);
    this.selection.clickSelectEvent(e);
};

var viewDetailCustom = function(url, id, creaTab) {
   dijit.byId("contentPaneDetail").setHref(url + id);

   if (creaTab) {
      //alert(" doc id : "+id) ;
      buildTabDocumentosAdicionales("doViewDocAdicionales.action?iIdDoc=" + id+"&o="+Math.ramdom(), "contentPaneDocumentosAdicionales", "Documentos ");
   }
};

var viewDetailCustom2 = function(id) {

//@Danna inic 136
    sURL = "doViewDocUsuarioFinal.action?iIdDoc=";
    // alert("in viewDetailCustom2 :id:  "+id) ;

   dijit.byId("contentPaneDetail").setHref(sURL +id);

   dijit.byId("contentPaneDocumentosAdicionales").setHref("doViewDocAdicionales.action?iIdDoc=" +id);

//  this.edit.rowClick(    ); // <- from default onRowClick method
// this.selection.clickSelectEvent(    ); // <- from default onRowClick method
};

var viewDetailInformativo = function(id,documento) {

   sURL = "doGetNotificacionDetail.action?iIdNotificacion=";
   // alert("in viewDetailCustom2 :id:  "+id) ;

   dijit.byId("contentPaneDetail").setHref(sURL +id);

   dijit.byId("contentPaneDocumentosAdicionales").setHref("doViewDocAdicionales.action?iIdDoc=" +documento);


};



var guardarGrid = function() {
   console.debug("sTipoGridActual "+sTipoGridActual);
   var gridActual = dijit.byId("gridInbox");
   var grid=new Array();
   var gridAux;
   for(var k in gridActual.layout.cells){
      var index=gridActual.layout.cells[k].index;
      var campo=gridActual.layout.cells[k].field;
      var id=gridActual.layout.cells[k].idGridColumnaPorGrid;
      var orden=gridActual.getSortIndex();
      var ascendente=gridActual.getSortAsc();
      var ordenado="N";
      console.debug("index "+index+" -campo "+campo+" -id "+id+" -orden "+orden+" -ascendente "+ascendente);
      if(index==orden){
         if(ascendente){
            ordenado="A";
         }
         else{
            ordenado="D";
         }
      }
      if(campo=="selected" || campo=="expBtn"){
         if(gridAux!=null){
        	 console.debug("no nulo");
        	 console.debug(gridAux.field);
        	 console.debug(gridAux.position);
            if(gridAux.field!="selected" && gridAux.field!="expBtn" && gridAux.position==0){
               gridAux.position=index;
               grid[index-1]=gridAux;
            }
         }
      }
      else{
         if(sTipoGridActual == TIPO_GRID_MENSAJERIA_RECIBIDOS ||
            sTipoGridActual == TIPO_GRID_MENSAJERIA_CERRADOS ||
            sTipoGridActual == TIPO_GRID_ENVIADOS ||
            sTipoGridActual == TIPO_GRID_MENSAJERIA ||
            sTipoGridActual == TIPO_GRID_CARPETA_BUSQUEDA ||
            sTipoGridActual == TIPO_GRID_SEGUIMIENTO ){
        	 index++;
         }
         var oculto=gridActual.layout.cells[k].hidden;
         if(oculto==false)
            oculto=0;
         else if(oculto==true)
            oculto=1;
         if (campo!=null && campo!="") {
            //alert("Indice:"+index+" Campo:"+campo+" Ancho:"+grdSiged.layout.cells[k].unitWidth+" Oculto: "+oculto);
            console.debug("Indice:"+index+" Campo:"+campo+" Ancho:"+gridActual.layout.cells[k].unitWidth+" Oculto: "+oculto);
            if(index==0){
               gridAux={
                  idGridXGridColumna: id,
                  field: campo,
                  width: gridActual.layout.cells[k].unitWidth,
                  hidden: oculto,
                  position: index,
                  ordenado:	ordenado
               };
            }
            else{
            	if(sTipoGridActual == TIPO_GRID_RECIBIDOS){
            		index--;
            	}
                grid[index-1]={
                  idGridXGridColumna: id,
                  field:		campo,
                  width:		gridActual.layout.cells[k].unitWidth,
                  hidden:		oculto,
                  position:	index,
                  ordenado:	ordenado
               };
            }
         }
      }
   }
   service.guardarGridUsuario(grid);
   getAllStructures();
};

function tieneCheck(){
   return sTipoGridActual == TIPO_GRID_RECIBIDOS || sTipoGridActual == TIPO_GRID_EXPEDIENTES || sTipoGridActual == TIPO_GRID_NOTIFICACIONES || sTipoGridActual == TIPO_GRID_INFORMATIVOS || sTipoGridActual == TIPO_GRID_DOCUMENTOS_ARCHIVADOS || sTipoGridActual == TIPO_GRID_FIRMAR;
}

function tieneDocumentosXExpediente(){
	return sTipoGridActual == TIPO_GRID_RECIBIDOS;
}

function showGridInbox(){
	showGridInbox(sTipoGridActual);
}

function refreshTabDXE(){
	if(dijit.byId("tabDXE")){
		dijit.byId("tabDXE").attr("href", dijit.byId("tabDXE").attr("href"));
	}
}
//visorGerencial
 function mostrarVisorPDF(e){
	    var objRecurso = new Recurso("","","","","");
	  	objRecurso.id = "contentPaneVisor";
	  	objRecurso.href = "interfazGerente.action?idDoc="+e;
	  	objRecurso.title = "Bandeja Entrada";
	  	objRecurso.style = "width:97%; overflow:auto;";
	  	objRecurso.region = "center";
	  dijit.byId("borderContainerVisor").removeChild(dijit.byId("contentPaneVisor"));
	  dijit.byId("contentPaneVisor").destroyDescendants();
	  dijit.byId("contentPaneVisor").destroy();
	   new dojox.layout.ContentPane({
	          id : objRecurso.id,
	          jsId : objRecurso.id,
	          href : objRecurso.href,
	          style : objRecurso.style,
	          region : objRecurso.region,
	          title: objRecurso.title
	       }).placeAt(dijit.byId("borderContainerVisor"));

	}



function mostrarVisorPDFError(){
	//alert("VER");
	/* var objRecurso = new Recurso("","","","","");
   	objRecurso.id = "contentPaneVisor";
   	objRecurso.href = "pages/layout/errorVisor.jsp";
   	objRecurso.title = "Bandeja Entrada";
   	objRecurso.style = "width:97%; height:100%;overflow:auto;";
   	objRecurso.region = "center"; */
     dijit.byId("borderContainerVisor").removeChild(dijit.byId("contentPaneVisor"));
     dijit.byId("contentPaneVisor").destroyDescendants();
     dijit.byId("contentPaneVisor").destroy();
      new dojox.layout.ContentPane({
	            id : "contentPaneVisor",
	            jsId :"contentPaneVisor",
	            href : "pages/layout/errorVisor.jsp",
	            style : "width:97%; height:100%;overflow:auto;",
	            region : "center",
	            title: "Bandeja Entrada"
	         }).placeAt(dijit.byId("borderContainerVisor"));

}

function mostrarVisorPDFXEx(e){
	dijit.byId("bcDXEIG").removeChild(dijit.byId("contentPaneVisorXex"));
    dijit.byId("contentPaneVisorXex").destroyDescendants();
    dijit.byId("contentPaneVisorXex").destroy();
	 var objRecurso = new Recurso("","","","","");
   	objRecurso.id = "contentPaneVisorXex";
   	objRecurso.href = "interfazGerenteXEx.action?idDoc="+e,
   	objRecurso.title = "Bandeja Entrada";
   	objRecurso.style = "width:97%; overflow:auto;";
   	objRecurso.region = "center";
      new dojox.layout.ContentPane({
	            id : objRecurso.id,
	            jsId : objRecurso.id,
	            href : objRecurso.href,
	            style : objRecurso.style,
	            region : objRecurso.region,
	            title: objRecurso.title
	         }).placeAt(dijit.byId("bcDXEIG"));
}

function mostrarVisorPDFXExError(){
	var divVisorDXE = new dojox.layout.ContentPane({
        id: "contentPaneVisorXex",
        href:  "pages/layout/errorVisor.jsp",
        jsId: "contentPaneVisorXex",
        style: "width:97%; overflow:auto;",
        region:"center"
    },"divVisorDXE");
	divVisorDXE.startup();
}
//fin
