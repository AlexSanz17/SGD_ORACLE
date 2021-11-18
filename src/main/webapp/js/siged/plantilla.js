var arrCampoToDelete = new Array();
var iCount = 0;
var service = new dojo.rpc.JsonService("SMDAction.action");
var storeTipoDocumento = new dojo.data.ItemFileReadStore({
   url: "autocompleteTipoDocumentoWithoutPlantilla.action?iIdPlantilla=" + iIdPlantilla
});

var lytGridCampo = [
{
   field  : "id",
   name   : "ID",
   hidden : true
},
{
   field    : "codigo",
   name     : "Codigo",
   editable : true,
   width    : "150px"
},
{
   field    : "descripcion",
   name     : "Descripcion",
   editable : true,
   width    : "200px"
},
{
   field    : "tipo",
   name     : "Tipo",
   editable : true,
   options  : ["Caja de Texto", "Texto Ampliado"],
   type     : dojox.grid.cells.Select,
   width    : "100px"
},
{
   field    : "valor",
   name     : "Valor por Defecto",
   editable : true,
   options  : ["Propietario del Documento", "Area del Propietario del Documento", "Cliente", "Concesionario", "Proceso", "Etapa", "Actividad", "Nro Expediente", "Descripcion", "Asunto del Expediente", "Contenido", "Observacion", "Fecha Creacion", "Estado", "Observacion de Rechazo", "Nro Interno", "Responsable del Expediente", "Area del Responsable del Expediente", "Responsable del Proceso", "Area del Responsable del Proceso", "Tipo de Documento", "Firmante", "Fecha del Documento", "Asunto del Documento", "Autor"],
   type     : dojox.grid.cells.Select,
   width    : "200px"
}
];

var addCampo = function() {
   var gridCampo = dijit.byId("gridCampo");

   gridCampo.store.newItem({
      id          : 0,
      codigo      : "Ingrese Codigo",
      descripcion : "Ingrese Descripcion",
      tipo        : "Ingrese Tipo",
      valor       : "Ingrese Valor por Defecto"
   });
}

var createPlantilla = function() {
   dojo.byId("objPlantilla.campos").value = dijit.byId("gridCampo").store._getNewFileContentString();

   var data = dijit.byId("frmNuevoPlantilla").attr("value");

   data.objPlantilla.idplantilla = (data.objPlantilla.idplantilla == "" ? "0" : data.objPlantilla.idplantilla);

   console.debug("Data a enviar [%s]", dojo.toJson(data, true));

   service.savePlantilla(data.objPlantilla, arrCampoToDelete).addCallback(function() {
      self.parent.location.href = "inicio.action?sTipoFrame=grid&sTipoMantenimiento=MantMnuPlt";
   });
}

var deleteCampo = function() {
   var gridCampo = dijit.byId("gridCampo");
   var items = gridCampo.selection.getSelected();

   if (items.length) {
      dojo.forEach(items, function(selectedItem) {
         if (selectedItem !== null) {
            console.debug("ID seleccionado [%d]", gridCampo.store.getValue(selectedItem, "id"));
            arrCampoToDelete[iCount++] = gridCampo.store.getValue(selectedItem, "id");
         }
      });
   }

   gridCampo.removeSelectedRows();
}

var deletePlantilla = function() {
   console.debug("Plantilla a eliminar con ID [%d]", iIdPlantilla);

   service.deletePlantilla(parseInt(iIdPlantilla)).addCallback(function() {
      self.parent.location.href = "inicio.action?sTipoFrame=grid&sTipoMantenimiento=MantMnuPlt";
   });
}

dojo.addOnLoad(function() {
   var gridCampo = dijit.byId("gridCampo");

   iTipoPlantilla = (iTipoPlantilla == "" ? "0" : iTipoPlantilla);

   console.debug("Tipo de Plantilla [%d]", iTipoPlantilla);

   service.getCamposPorPlantilla(parseInt(iTipoPlantilla)).addCallback(function(objJSON) {
      gridCampo.setStructure(lytGridCampo);
      gridCampo.setStore(new dojo.data.ItemFileWriteStore({
         data : objJSON
      }));
   });
});
