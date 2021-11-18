
var lytGridMantReemplazo = [
{
   editable : true,
   field : "selected",
   name : "<input type='checkbox' id='chkHeaderMantReemplazo' name='chkHeaderMantReemplazo' onclick='checkAllMantReemplazo()'>",
   noresize : true,
   type : dojox.grid.cells.Bool,
   width : "20px"
},
{
   field : 'reemplazado',
   name : 'Reemplazado',
   width : '200px'
},
{
   field : 'reemplazante',
   name : 'Reemplazante',
   width : '200px'
},
{
   field : 'proceso',
   name : 'Proceso',
   width : '200px'
},
{
   field : 'id',
   name : 'ID',
   hidden : true
}
];

var checkAllMantReemplazo = function() {
   var grid = dijit.byId("gridMantReemplazo");

   dojo.forEach(grid.store._arrayOfAllItems, function(item) {
      grid.store.setValue(item, "selected", dojo.byId("chkHeaderMantReemplazo").checked);
   });
};

var eliminarMantReemplazo = function() {
   var grid = dijit.byId("gridMantReemplazo");
   var parameters = new Array();

   dojo.forEach(grid.store._arrayOfAllItems, function(item) {
      if (grid.store.getValue(item, "selected")) {
         parameters.push(grid.store.getValue(item, "id"));
      }
   });

   console.debug("[%d] Reemplazo(s) a eliminar [%s]", parameters.length, parameters);

   if (parameters.length) {
      if (confirm("Desea eliminar los reemplazos seleccionados?")) {
         dojo.xhrPost({
            url : "doEliminarReemplazoUSERFINAL.action",
            content : {
               iIdParameters : parameters
            },
            load : function() {
               service.getReemplazo(true).addCallback(function(objJSON) {
                  dijit.byId("gridMantReemplazo").setStore(new dojo.data.ItemFileWriteStore({
                     data : objJSON
                  }));
               });

               grid.selection.deselectAll();
               dijit.byId("contentPaneDetailMantReemplazo").attr("content", "");
            }
         });
      }
   } else {
      alert("Primero selecccionar reemplazos");
   }
};

var guardarMantReemplazo = function() {
   if (!dijit.byId("frmMantReemplazo").validate()) {
      dojo.byId("showErrorMantReemplazo").innerHTML = "Data inv&aacute;lida... Corregir campos";

      return;
   }

   dijit.byId("btnGuardarMantReemplazo").attr("disabled", true);
   dojo.byId("showErrorMantReemplazo").innerHTML = "&nbsp;";

   var data = dijit.byId("frmMantReemplazo").attr("value");
   console.debug("(guardarMantReemplazo) Data de registro de reemplazo [%s]", dojo.toJson(data, true));

   dojo.xhrPost({
      form : dojo.byId("frmMantReemplazo"),
      load : function(data) {
         service.getReemplazo(true).addCallback(function(objJSON) {
            dijit.byId("gridMantReemplazo").setStore(new dojo.data.ItemFileWriteStore({
               data : objJSON
            }));
         });

         dijit.byId("contentPaneDetailMantReemplazo").attr("content", data);
      }
   });
};

var sortMe = function(iColumn) {
   if (iColumn == 1) {
      return false;
   } else {
      return true;
   }
};

var viewDetailMantReemplazo = function(e) {
   console.debug("(viewDetailMantReemplazo) Grid [%s]. ID seleccionado [%d]", this.id, this.getItem(e.rowIndex).id);

   if (e.cellNode.cellIndex == 0) {
      return;
   }

   dojo.xhrPost({
      url : "doViewMantReemplazoUSERFINAL.action",
      content : {
         iIdReemplazo : this.getItem(e.rowIndex).id
      },
      load : function(data) {
         dijit.byId("contentPaneDetailMantReemplazo").attr("content", data);
      }
   });
};

dojo.addOnLoad(function() {
   service.getReemplazo(true).addCallback(function(objJSON) {
      dijit.byId("gridMantReemplazo").attr("structure", lytGridMantReemplazo);
      dijit.byId("gridMantReemplazo").setStore(new dojo.data.ItemFileWriteStore({
         data : objJSON
      }));
   });
});
