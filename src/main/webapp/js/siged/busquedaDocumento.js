/*var agregarDocumentoReferenciado=function(e){
   if (e.rowIndex == undefined) {
      alert("Debe seleccionar un registro.");
      return;
   }
};*/

var showBusquedaDocumento = function() {
   if (dijit.byId("dlgBusquedaDocumento")) {
      resetBusquedaDocumento();
      dijit.byId("dlgBusquedaDocumento").show();
   }
};

var resetBusquedaDocumento = function() {
   if (dijit.byId("grdBusquedaDocumento")) {
      dijit.byId("grdBusquedaDocumento").setStore(null);
   }
   
   if (dijit.byId("sNroDocumento")) {
      dijit.byId("sNroDocumento").attr("value", "");
   }

   if (dijit.byId("sTipoDocumento")) {
      dijit.byId("sTipoDocumento").attr("value", "")
   }
   
   if (dijit.byId("sNroRS")) {
      dijit.byId("sNroRS").attr("value", "")
   }
   
   if (dijit.byId("sNroHT")) {
      dijit.byId("sNroHT").attr("value", "")
   }
   
   if (dijit.byId("sNroLegajo")) {
      dijit.byId("sNroLegajo").attr("value", "")
   }
   
   if (dijit.byId("sTipoExpediente")) {
      dijit.byId("sTipoExpediente").attr("value", "")
   }
   if (dijit.byId("sExpediente")) {
      dijit.byId("sExpediente").attr("value", "")
   }
};
