dojo.provide("js.carpetasBusqueda");

var iIdCarpetaBusqueda = 0;
var ctxMenu = null;

var buildCarpetasBusqueda = function(parent) {
	console.debug("carpetasBusqueda.js -->buildCarpetasBusqueda()");
	buildToolBarCarpetasBusqueda(parent);
	var borderContainerCarpetasBusqueda = new dijit.layout.BorderContainer({
      id: "borderContainerCarpetasBusqueda",
      jsId: "borderContainerCarpetasBusqueda",
      style: "width:100%;height:100%;"
   }).placeAt(parent);



   var contextMenuCarpetasBusqueda = new dijit.Menu({
      id: "contextMenuCarpetasBusqueda",
      jsId: "contextMenuCarpetasBusqueda",
      style: "display:none;"
   });

   contextMenuCarpetasBusqueda.addChild(new dijit.MenuItem({
      iconClass: "siged16 sigedSave16",
      label: "Modificar",
      onClick: function() {
         console.debug("Carpeta de busqueda a modificar con ID [%d]", iIdCarpetaBusqueda);

         dojo.xhrPost({
            url: "dobuscarpmodificar.action",
            content: {
               Idcarperta: iIdCarpetaBusqueda
            },
            mimetype: "text/html",
            load: function(data) {
               dijit.byId("dlgCarpetasBusqueda").attr("content", data);
               dijit.byId("dlgCarpetasBusqueda").show();
            }
         });
      }
   }));

   contextMenuCarpetasBusqueda.addChild(new dijit.MenuItem({
      iconClass: "siged16 sigedSave16",
      label: "Eliminar",
      onClick: function() {
         console.debug("Carpeta de busqueda a eliminar con ID [%d]", iIdCarpetaBusqueda);

         service.eliminarCarpetaBusqueda(iIdCarpetaBusqueda).addCallback(function(objJSON) {
            if (objJSON != undefined && objJSON != null && objJSON.items != "") {
               if (dijit.byId("treeCarpetasBusqueda")) {
                  dojo.disconnect(ctxMenu);
                  dijit.byId("treeCarpetasBusqueda").destroyRecursive();
               }

               createTreeCarpetasBusqueda(dijit.byId("borderContainerCarpetasBusqueda"), dijit.byId("contextMenuCarpetasBusqueda"));
            }
         });
      }
   }));

   contextMenuCarpetasBusqueda.startup();
   createTreeCarpetasBusqueda(borderContainerCarpetasBusqueda, contextMenuCarpetasBusqueda);
   borderContainerCarpetasBusqueda.startup();
};

var buildToolBarCarpetasBusqueda = function(parent) {
   var toolBarCarpetaBusqueda = new dijit.Toolbar({
      region: "top"
   }).placeAt(parent);

   toolBarCarpetaBusqueda.addChild(new dijit.form.Button({
      iconClass: "dijitEditorIcon dijitEditorIconInsertImage",
      label: "Crear carpeta",
      onClick: function() {
         resetFormCarpetasBusqueda();
         dijit.byId("dlgCarpetasBusqueda").show();
      },
      showLabel: true
   }));
   toolBarCarpetaBusqueda.startup();
};

var createTreeCarpetasBusqueda = function(parent, context) {
	   console.debug("carpetasBusqueda.js -->createTreeCarpetasBusqueda()");
   service.getCarpetasBusqueda().addCallback(function(objJSON) {
      if (objJSON != undefined && objJSON != null && objJSON.items != "") {
         var treeCarpetasBusqueda = new dijit.Tree({
            id: "treeCarpetasBusqueda",
            jsId: "treeCarpetasBusqueda",
            model: new dijit.tree.ForestStoreModel({
               store: new dojo.data.ItemFileReadStore({
                  data: objJSON
               }),
               query: {
                  top: true
               },
               rootId: "0",
               rootLabel: "carpetaBusqueda"
            }),
            region: "center",
            showRoot: false,
            style: "overflow:auto;",
            onClick: showContentCarpetaBusqueda
         }).placeAt(parent);

         context.bindDomNode(treeCarpetasBusqueda.domNode);

         ctxMenu = dojo.connect(context, "_openMyself", treeCarpetasBusqueda, function(e) {
            var tn = dijit.getEnclosingWidget(e.target);

            console.debug(tn);
            console.debug(tn.item.name);

            iIdCarpetaBusqueda = parseInt(tn.item.id);
         });
      }
   });
};

var doSaveCarpetasBusqueda = function() {
	var arrFecha = new Array("","","","");

	if (!dijit.byId("frmCarpetasBusqueda").validate()) {
		dojo.byId("showErrorCarpetasBusqueda").innerHTML = "Data inv&aacute;lida... Corregir campos";
		return;
	}

	if(fechasInvalidas()){
		dojo.byId("showErrorCarpetasBusqueda").innerHTML="Si ingresa una fecha de inicio, debe ingresar una de fin. O viceversa.";
		return;
	}

   dijit.byId("btnCrearCarpetasBusqueda").attr("disabled", true);
   dojo.byId("showErrorCarpetasBusqueda").innerHTML = "&nbsp;";

   var data = dijit.byId("frmCarpetasBusqueda").attr("value");
   console.debug("(doSaveCarpetasBusqueda) Data de creacion de carpeta de busqueda [%s]", dojo.toJson(data, true));

   //ESTO NO DEBERIA SER ASI, LAS FECHAS DEBEN ENVIARSE DENTRO DEL OBJCARPETABUSQUEDA... MEJORAR ESTO!!!
   arrFecha[0] = dojo.byId("objCarpetaBusqueda.fechaDocumentoDesde").value;
   arrFecha[1] = dojo.byId("objCarpetaBusqueda.fechaDocumentoHasta").value;
   arrFecha[2] = dojo.byId("objCarpetaBusqueda.fechaExpedienteDesde").value;
   arrFecha[3] = dojo.byId("objCarpetaBusqueda.fechaExpedienteHasta").value;

   if (data.objCarpetaBusqueda.idCarpetaBusqueda == "") {
      data.objCarpetaBusqueda.idCarpetaBusqueda = 0;
   }


   if (dojo.byId("pendiente_").checked== true){
	   data.objCarpetaBusqueda.pendiente = "S";
   }else{
	   data.objCarpetaBusqueda.pendiente = "N";
   }

   service.saveCarpetaBusqueda(data.objCarpetaBusqueda, arrFecha).addCallback(function(sResultado) {
      if (sResultado == "NotCreated") {
         console.info("(doSaveCarpetasBusqueda) No se pudo registrar la carpeta de busqueda");

         dojo.byId("showErrorCarpetasBusqueda").innerHTML = "No se pudo registrar la carpeta de b&uacute;squeda";
         dijit.byId("btnCrearCarpetasBusqueda").attr("disabled", false);
         dijit.byId("dlgCarpetasBusqueda").show();
      } else {
         if (dijit.byId("treeCarpetasBusqueda")) {
            console.log("destrozando treeCarpetasBusqueda");
            dojo.disconnect(ctxMenu);
            dijit.byId("treeCarpetasBusqueda").destroyRecursive();
         }

         createTreeCarpetasBusqueda(dijit.byId("borderContainerCarpetasBusqueda"), dijit.byId("contextMenuCarpetasBusqueda"));
         dijit.byId("dlgCarpetasBusqueda").hide();
      }
   });
};

var resetFormCarpetasBusqueda = function() {
   console.debug("(resetFormCarpetasBusqueda) Limpiando los campos del formulario frmCarpetasBusqueda");
   //MEJORAR ESTO!!!
   dijit.byId("objCarpetaBusqueda.idCarpetaBusqueda").attr("value", "");
   dijit.byId("objCarpetaBusqueda.nombreCarpeta").attr("value", "");
   dijit.byId("objCarpetaBusqueda.numeroExpediente").attr("value", "");
   dijit.byId("objCarpetaBusqueda.numeroMesaPartes").attr("value", "");
   dijit.byId("objCarpetaBusqueda.numeroDocumento").attr("value", "");
   dijit.byId("objCarpetaBusqueda.tipoDocumento").attr("value", "");
   dijit.byId("objCarpetaBusqueda.fechaDocumentoDesde").attr("value", "");
   dijit.byId("objCarpetaBusqueda.fechaDocumentoHasta").attr("value", "");
   dijit.byId("objCarpetaBusqueda.fechaExpedienteDesde").attr("value", "");
   dijit.byId("objCarpetaBusqueda.fechaExpedienteHasta").attr("value", "");
   dijit.byId("objCarpetaBusqueda.consecionario").attr("value", "");
   dijit.byId("objCarpetaBusqueda.documentoIdentidad").attr("value", "");
   dijit.byId("objCarpetaBusqueda.cliente").attr("value", "");
   dijit.byId("objCarpetaBusqueda.direccionCliente").attr("value", "");
   dijit.byId("objCarpetaBusqueda.areaOrigen").attr("value", "");
   dijit.byId("objCarpetaBusqueda.areaDestino").attr("value", "");
   dijit.byId("objCarpetaBusqueda.propietario").attr("value", "");
   dijit.byId("objCarpetaBusqueda.proceso").attr("value", "");

   dijit.byId("btnCrearCarpetasBusqueda").attr("disabled", false);
   dojo.byId("showErrorCarpetasBusqueda").innerHTML = "&nbsp;";
};

var showContentCarpetaBusqueda = function(objNodo) {

   console.log("Datos del nodo seleccionado id[" + objNodo.id + "] name[" + objNodo.name + "]");

   iIdCarpetaBusqueda = objNodo.id;

   dijit.byId('dlgProgresBar').show() ;
   resetDetail();
   console.log("despues de resetdetail ");

   sTipoGridActual = TIPO_GRID_CARPETA_BUSQUEDA;
   console.log("seteando  sTipoGridActual : "+sTipoGridActual);

   //visorGerencial
   //destruye borderContainerVisor, para un grid distinto de TIPO_GRID_RECIBIDOS
   console.debug("sTipoGridActual:  "+sTipoGridActual);
   if(recursoIG==1 && dijit.byId("borderContainerVisor")){
		      dijit.byId("borderContainerVisor").removeChild(dijit.byId("contentPaneVisor"));
			  dijit.byId("contentPaneVisor").destroyDescendants();
			  dijit.byId("contentPaneVisor").destroy();
			  dijit.byId("borderContainerVisor").destroy();

    		  //dijit.byId("contentPaneGrid").removeChild(dijit.byId("cargando"));
			  //dijit.byId("cargando").destroy();

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

   if (dijit.byId("gridInbox")) {
       dijit.byId("gridInbox").destroy();
   }

   var columnaOrdenada=-1;
   var ascendente = null;
   var storeGridCB = null;
   var estructuraGridCB = null;
   service.getDataGrid(sTipoGridActual).addCallback(function(objJSON){
	   estructuraGridCB = objJSON.structure;
   });

   service.buildGridCarpetaBusqueda(parseInt(objNodo.id)).addCallback(function(objJSON) {
	   storeGridCB = new dojo.data.ItemFileWriteStore({
	       data : objJSON
	   });

	   var grdSiged = new dojox.grid.DataGrid({
	       id               : "gridInbox",
	       jsId             : "gridInbox",
	       canSort          : noSort,
	       columnReordering : true,
	       headerMenu       : dijit.byId("gridMenu"),
	       onRowClick       : viewDetail,
	       rowsPerPage      : 50,
	       store            : storeGridCB,
	       structure        : estructuraGridCB
	    }, document.createElement("div"));

	   for (var i in grdSiged.layout.cells) {
	       if (grdSiged.layout.cells[i].formato == "formatterDate") {
	    	   grdSiged.layout.cells[i].formatter = formatterDate;
	       }
	       else if (grdSiged.layout.cells[i].formato == "formatterImg") {
	    	   grdSiged.layout.cells[i].formatter = formatterImg;
	       }
	       if(grdSiged.layout.cells[i].ordenado){
	          columnaOrdenada=parseInt(i);
	          ascendente=grdSiged.layout.cells[i].ascendente;
	       }
	    }

	   removeToolBarInbox();
	   dojo.byId("contentPaneGrid").appendChild(grdSiged.domNode);
	   grdSiged.startup();
	   if(columnaOrdenada>=0){
		   grdSiged.setSortIndex(columnaOrdenada,ascendente);
	   }
	   dijit.byId("toolBarInbox").addChild(dijit.byId("btnConsultaImprimir"));
	   dijit.byId("toolBarInbox").addChild(dijit.byId("btnConsultaExportarExcel"));
	   dijit.byId("borderContainerGeneral").setTitle("Carpeta de Busqueda [" + objNodo.name + "] contiene " + grdSiged.store._arrayOfAllItems.length + " documento(s)");
	   dijit.byId('dlgProgresBar').hide() ;

   });

};

function fechasInvalidas(){
	var doc=false;
	var exp=false;
	var documentoDesde=dojo.byId("objCarpetaBusqueda.fechaDocumentoDesde").value;
	var documentoHasta=dojo.byId("objCarpetaBusqueda.fechaDocumentoHasta").value;
	if(documentoDesde!=null && documentoDesde!=""){
		doc=documentoHasta==null || documentoHasta=="";
	}
	else if(documentoHasta!=null && documentoHasta!=""){
		doc=true;
	}
	var expedienteDesde=dojo.byId("objCarpetaBusqueda.fechaExpedienteDesde").value;
	var expedienteHasta=dojo.byId("objCarpetaBusqueda.fechaExpedienteHasta").value;
	if(expedienteDesde!=null && expedienteDesde!=""){
		exp=expedienteHasta==null || expedienteHasta=="";
	}
	else if(expedienteHasta!=null && expedienteHasta!=""){
		exp=true;
	}
	return doc || exp;
}
