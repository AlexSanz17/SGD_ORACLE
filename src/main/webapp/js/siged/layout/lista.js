
var lytGridLista = [
{
   field : 'nombre',
   name : 'Nombre',
   width : '200px'
}, {
   field : 'fechacreacion',
   name : 'Fecha Creaci&oacute;n',
   formatter : formatterDateLista,
   width : '150px'
}, {
   field : 'id',
   name : 'ID',
   hidden : true
}];

var guardarLista = function() {
   dijit.byId("btnGuardarLista").attr("disabled", true);

   var arrAdministrador = new Array(dojo.byId("lstAdministradorListaRight").length);

   for (var i = 0; i < arrAdministrador.length; i++) {
      arrAdministrador[i] = dojo.byId("lstAdministradorListaRight").options[i].value;
   }

   var arrParticipante = new Array(dojo.byId("lstParticipanteListaRight").length);

   for (var j = 0; j < arrParticipante.length; j++) {
      arrParticipante[j] = dojo.byId("lstParticipanteListaRight").options[j].value;
   }

   console.debug("Lista de administradores [%s]", dojo.toJson(arrAdministrador));
   console.debug("Lista de participantes [%s]", dojo.toJson(arrParticipante));

   dojo.xhrPost({
      form : dojo.byId("frmLista"),
      handleAs : "text",
      content : {
         lstAdministradorListaRight : arrAdministrador,
         lstParticipanteListaRight : arrParticipante
      },
      load : function(data) {
         service.getLista().addCallback(
            function(objJSON) {
               dijit.byId("gridLista").setStore(
                  new dojo.data.ItemFileWriteStore({
                     data : objJSON
                  }));
            });

         dijit.byId("contentPaneDetailLista").attr("content", data);
      }
   });
};

var viewDetailLista = function(e) {
   console.debug("(viewDetailLista) Grid [%s]. ID seleccionado [%d]", this.id, this.getItem(e.rowIndex).id);

   dojo.xhrPost({
      url : "doViewLista.action",
      content : {
         iIdLista : this.getItem(e.rowIndex).id
      },
      mimetype : "text/html",
      load : function(data) {
         dijit.byId("contentPaneDetailLista").attr("content", data);
      }
   });
};

dojo.addOnLoad(function() {
   service.getLista().addCallback(function(objJSON) {
      dijit.byId("gridLista").attr("structure", lytGridLista);
      dijit.byId("gridLista").setStore(new dojo.data.ItemFileWriteStore({
         data : objJSON
      }));
   });
});
