var submotivoCache = { };
var viewedSub = -1;

function fillTableSubMotivo() {
   Motivo.getAllMotivo(function(setmotivo) {
      // Delete all the rows except for the "pattern" row
      dwr.util.removeAllRows("motivobody", {
         filter:function(tr) {
            return (tr.id != "patternsubmotivo");
         }
      });
      // Create a new set cloned from the pattern row
      var objsubmotivo, id;
      /*setmotivo.sort(function(p1, p2) {
         return p1.nmotivo.localeCompare(p2.nmotivo);
      });*/
      for (var i = 0; i < setmotivo.length; i++) {
         objsubmotivo = setmotivo[i];
         id = objsubmotivo.id;
         dwr.util.cloneNode("patternsubmotivo", {
            idSuffix:id
         });
         dwr.util.setValue("tableMotivo" + id, objsubmotivo.nmotivo);
         dwr.util.setValue("tableSubMotivo" + id, objsubmotivo.nsubmotivo);
         dwr.util.setValue("storidsubmotivo" + id, objsubmotivo.idsubmotivo);
         document.getElementById("patternsubmotivo" + id).style.display = ""; // officially we should use table-row, but IE prefers "" for some reason
         submotivoCache[id] = objsubmotivo;
      }
   });
}

function deleteSubMotivo(eleid) {
   // we were an id of the form "delete{id}", eg "delete42". We lookup the "42"

   var objsubmotivo = submotivoCache[eleid.substring(9)];
   dwr.util.useLoadingMessage();

   dwr.engine.beginBatch();
   Motivo.deleteSubMotivo({
      id:objsubmotivo.id
   });
   fillTableSubMotivo();
   dwr.engine.endBatch();
}

function addSubMotivo() {
   var objsubmotivo = {
      id:viewedSub,
      idmotivo:null,
      nmotivo:null,
      idsubmotivo:null,
      nsubmotivo:null
   };
   dwr.util.getValues(objsubmotivo);

   dwr.engine.beginBatch();
   Motivo.addSubMotivo(objsubmotivo);
   fillTableSubMotivo();
   clearSubMotivo();
   dwr.engine.endBatch();
}

function clearSubMotivo() {
   viewedSub = -1;
   dwr.util.setValues({
      id:-1,
      idmotivo:null,
      nmotivo:null,
      idsubmotivo:null,
      nsubmotivo:null
   });
}
