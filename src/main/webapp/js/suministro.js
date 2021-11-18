var suministroCache = { };
var viewedSum = -1;

function fillTableSuministro() {
   Suministro.getAllSuministro(function(setsuministro) {
      // Delete all the rows except for the "pattern" row
      dwr.util.removeAllRows("suministrobody", {
         filter:function(tr) {
            return (tr.id != "patternsuministro");
         }
      });
      // Create a new set cloned from the pattern row
      var objsuministro, id;
      for (var i = 0; i < setsuministro.length; i++) {
         objsuministro = setsuministro[i];
         id = objsuministro.id;
         dwr.util.cloneNode("patternsuministro", {
            idSuffix:id
         });
         dwr.util.setValue("tableSuministro" + id, objsuministro.ssuministro);
         dwr.util.setValue("storsuministro" + id, objsuministro.ssuministro);
         document.getElementById("patternsuministro"+id).style.display = ""; // officially we should use table-row, but IE prefers "" for some reason
         suministroCache[id] = objsuministro;
      }
   });
}

function deleteSuministro(eleid) {
   // we were an id of the form "delete{id}", eg "delete42". We lookup the "42"

   var objsuministro = suministroCache[eleid.substring(9)];
   dwr.util.useLoadingMessage();

   dwr.engine.beginBatch();
   Suministro.deleteSuministro({
      id:objsuministro.id
   });
   fillTableSuministro();
   dwr.engine.endBatch();
}

function addSuministro() {
   var objsuministro = {
      id:viewedSum,
      ssuministro:null
   };
   dwr.util.getValues(objsuministro);

   dwr.engine.beginBatch();
   Suministro.addSuministro(objsuministro);
   fillTableSuministro();
   clearSuministro();
   dwr.engine.endBatch();
}

function clearSuministro() {
   viewedSum = -1;
   dwr.util.setValues({
      id:-1,
      ssuministro:null
   });
}
