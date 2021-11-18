var showBusquedaDocumentoReferenciado = function() {
   if (dijit.byId("dlgBusquedaDocumentoReferenciado")) {
	   resetBusquedaDocumentoReferenciado();
	   dijit.byId("dlgBusquedaDocumentoReferenciado").show();
   }
};

var resetBusquedaDocumentoReferenciado = function() {
	   if (dijit.byId("sTipoDocumentoReferenciado")) {
	      dijit.byId("sTipoDocumentoReferenciado").attr("value", "");
	   }

	   if (dijit.byId("sDocumentoReferenciado")) {
	      dijit.byId("sDocumentoReferenciado").attr("value", "")
	   }


	   if (dijit.byId("grdBusquedaDocumentoReferenciado")) {
	      dijit.byId("grdBusquedaDocumentoReferenciado").setStore(null);
	   }
	};