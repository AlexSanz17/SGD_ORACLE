
var lytGridReemplazo = [
{
   editable : true,
   field : "selected",
   name : "<input type='checkbox' id='chkHeaderReemplazo' name='chkHeaderReemplazo' onclick='checkAllReemplazo()'>",
   noresize : true,
   type : dojox.grid.cells.Bool,
   width : "20px"
},
{
   field : 'proceso',
   name : 'Proceso',
   width : '200px'
},
{
   field : 'fechainicio',
   name : 'Fecha Inicio',
   formatter : formatterDateReemplazo,
   width : '100px'
},
{
   field : 'fechainicio',
   name : 'Hora Inicio',
   formatter : formatterHourReemplazo,
   width : '100px'
},
{
   field : 'fechafin',
   name : 'Fecha Fin',
   formatter : formatterDateReemplazo,
   width : '100px'
},
{
   field : 'fechafin',
   name : 'Hora Fin',
   formatter : formatterHourReemplazo,
   width : '100px'
},
{
   field : 'usuarioreemplazante',
   name : 'Usuario Reemplazante',
   width : '200px'
},
{
   field : 'id',
   name : 'ID',
   hidden : true
},
{
   field : 'idproceso',
   name : 'ID PROCESO',
   hidden : true
}
];

var checkAllReemplazo = function() {
   var grid = dijit.byId("gridReemplazo");

   dojo.forEach(grid.store._arrayOfAllItems, function(item) {
      grid.store.setValue(item, "selected", dojo.byId("chkHeaderReemplazo").checked);
   });
};

var eliminarReemplazo = function() {
   var grid = dijit.byId("gridReemplazo");
   var parameters = new Array();

   dojo.forEach(grid.store._arrayOfAllItems, function(item) {
      if (grid.store.getValue(item, "selected") && grid.store.getValue(item, "usuarioreemplazante") != undefined) {
         parameters.push(grid.store.getValue(item, "id"));
      }
   });

   console.debug("Reemplazos a eliminar [%s] [%d]", parameters, parameters.length);

   if (parameters.length) {
      if (confirm("Desea eliminar los reemplazos seleccionados?")) {
         dojo.xhrPost( {
            url : "doEliminarReemplazoUSERFINAL.action",
            content : {
               iIdParameters : parameters
            },
            mimetype : "text/html",
            load : function() {
               service.getReemplazo(false).addCallback(function(objJSON) {
                  dijit.byId("gridReemplazo").setStore(new dojo.data.ItemFileWriteStore({
                     data : objJSON
                  }));
               });

               grid.selection.deselectAll();
               dijit.byId("contentPaneDetailReemplazo").attr("content", "");
            }
         });
      }
   } else {
      alert("Primero selecccionar reemplazos");
   }
};

var guardarReemplazo = function() {
   if (!dijit.byId("frmReemplazo").validate()) {
      dojo.byId("showErrorReemplazo").innerHTML = "Data inv&aacute;lida... Corregir campos";

      return;
   }

   dijit.byId("btnGuardarReemplazo").attr("disabled", true);
   dojo.byId("showErrorReemplazo").innerHTML = "&nbsp;";

   var data = dijit.byId("frmReemplazo").attr("value");

   console.debug("(guardarReemplazo) Data de registro de reemplazo [%s]", dojo.toJson(data, true));

   dojo.xhrPost({
      form : dojo.byId("frmReemplazo"),
      mimetype : "text/html",
      load : function(data) {
         service.getReemplazo(false).addCallback(function(objJSON) {
            dijit.byId("gridReemplazo").setStore(new dojo.data.ItemFileWriteStore({
               data : objJSON
            }));
         });

         dijit.byId("contentPaneDetailReemplazo").attr("content", data);
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

var viewDetailReemplazo = function(e) {
   console.debug("(viewDetailReemplazo) Grid [%s]. ID seleccionadoooool [%d]", this.id, this.getItem(e.rowIndex).id);

   if (e.cellNode.cellIndex == 0) {
      return;
   }

   dojo.xhrPost({
      url : "doViewReemplazoUSERFINAL.action",
      content : {
         iIdReemplazo : this.getItem(e.rowIndex).id,
         iIdProceso : this.getItem(e.rowIndex).idproceso
      },
      mimetype : "text/html",
      load : function(data) {
         dijit.byId("contentPaneDetailReemplazo").attr("content", data);
      }
   });
};

dojo.addOnLoad(function() {
   service.getReemplazo(false).addCallback(function(objJSON) {
      dijit.byId("gridReemplazo").attr("structure", lytGridReemplazo);
      dijit.byId("gridReemplazo").setStore(new dojo.data.ItemFileWriteStore({
         data : objJSON
      }));
   });
});
