/*dojo.require("dojo.data.ItemFileReadStore");
  dojo.require("dijit.form.FilteringSelect");*/
function regresar(){
   history.back();
}
function trim(cadena)
{
   for(i=0; i<cadena.length; )
   {
      if(cadena.charAt(i)==" ")
         cadena=cadena.substring(i+1, cadena.length);
      else
         break;
   }

   for(i=cadena.length-1; i>=0; i=cadena.length-1)
   {
      if(cadena.charAt(i)==" ")
         cadena=cadena.substring(0,i);
      else
         break;
   }

   return cadena ;
}

function enviar(formulario) {

   var nombrecarpeta =   document.getElementById('nombrecarpeta' ) ;
   nombrecarpeta.value = trim( nombrecarpeta.value) ;

   var nroexpediente =   document.getElementById('nroexpediente' ) ;
   nroexpediente.value = trim( nroexpediente.value) ;

   var documentoidentidad =   document.getElementById('documentoidentidad' ) ;
   documentoidentidad.value = trim( documentoidentidad.value) ;

   var nrodocumento =   document.getElementById('nrodocumento' ) ;
   nrodocumento.value = trim( nrodocumento.value) ;

   var nromesapartes =   document.getElementById('nromesapartes' ) ;
   nromesapartes.value = trim( nromesapartes.value) ;

   var consecionario =   document.getElementById('consecionario' ) ;
   consecionario.value = trim( consecionario.value) ;

   var cliente =   document.getElementById('cliente' ) ;
   cliente.value = trim( cliente.value) ;

   var direccioncliente =   document.getElementById('direccioncliente' ) ;
   direccioncliente.value = trim( direccioncliente.value) ;

   var areaorigen =   document.getElementById('areaorigen' ) ;
   areaorigen.value = trim( areaorigen.value) ;

   var areadestino =   document.getElementById('areadestino' ) ;
   areadestino.value = trim( areadestino.value) ;

   var propietario =   document.getElementById('propietario' ) ;
   propietario.value = trim( propietario.value) ;

   var proceso =   document.getElementById('proceso' ) ;
   proceso.value = trim( proceso.value) ;




   if (document.getElementById("nombrecarpeta").value == "") {
      alert("Debe ingresar un nombre para la carpeta de busqueda");
      document.getElementById("nombrecarpeta").focus();

      return false;
   }

   var expRegFecha = /^(0[1-9]|1\d|2\d|3[0-1])\/(0[1-9]|1[0-2])\/(19[6-9][6-9]|20\d{2})$/;
   var fechaDocumentoInicio = document.getElementById("finidoc").value;
   var fechaDocumentoFinal = document.getElementById("ffindoc").value;
   var fechaExpedienteInicio = document.getElementById("finiexp").value;
   var fechaExpedienteFinal = document.getElementById("ffinexp").value;

   if (fechaDocumentoInicio != "" && fechaDocumentoInicio.search(expRegFecha) == -1) {
      alert("El formato de la fecha debe ser dd/mm/yyyy");
      document.getElementById("finidoc").focus();

      return false;
   }

   if (fechaDocumentoFinal != "" && fechaDocumentoFinal.search(expRegFecha) == -1) {
      alert("El formato de la fecha debe ser dd/mm/yyyy");
      document.getElementById("ffindoc").focus();

      return false;
   }

   if (fechaExpedienteInicio != "" && fechaExpedienteInicio.search(expRegFecha) == -1) {
      alert("El formato de la fecha debe ser dd/mm/yyyy");
      document.getElementById("finiexp").focus();

      return false;
   }

   if (fechaExpedienteFinal != "" && fechaExpedienteFinal.search(expRegFecha) == -1) {
      alert("El formato de la fecha debe ser dd/mm/yyyy");
      document.getElementById("ffinexp").focus();

      return false;
   }

   if (fechaDocumentoInicio != "" && fechaDocumentoFinal == "") {
      alert("Debe ingresar una fecha fin");
      document.getElementById("ffindoc").focus();

      return false;
   }

   if (fechaDocumentoInicio == "" && fechaDocumentoFinal != "") {
      alert("Debe ingresar una fecha inicio");
      document.getElementById("finidoc").focus();

      return false;
   }

   if (fechaExpedienteInicio != "" && fechaExpedienteFinal == "") {
      alert("Debe ingresar una fecha fin");
      document.getElementById("ffinexp").focus();

      return false;
   }

   if (fechaExpedienteInicio == "" && fechaExpedienteFinal != "") {
      alert("Debe ingresar una fecha inicio");
      document.getElementById("finiexp").focus();

      return false;
   }

   if (fechaDocumentoInicio != "" && fechaDocumentoFinal != "") {
      var fecha1 = Date.parse(fechaDocumentoInicio);
      var fecha2 = Date.parse(fechaDocumentoFinal);

      if (fecha2 < fecha1) {
         alert("Verificar rango de fechas");
         document.getElementById("finidoc").focus();

         return false;
      }
   }

   if (fechaExpedienteInicio != "" && fechaExpedienteFinal != "") {
      var fecha1 = Date.parse(fechaExpedienteInicio);
      var fecha2 = Date.parse(fechaExpedienteFinal);

      if (fecha2 < fecha1) {
         alert("Verificar rango de fechas");
         document.getElementById("finiexp").focus();

         return false;
      }
   }

   //NUEVA INTERFAZ - DOJO 1.3
   //Antes
   //document.forms["foringcarbusqueda"].submit();
   //Despues
   dojo.xhrPost({
      form: dojo.byId(formulario),
      mimetype: "text/html",
      load: function() {
         if (dijit.byId("treeCarpetasBusqueda")) {
            console.log("destrozando treeCarpetasBusqueda");
            dojo.disconnect(ctxMenu);
            dijit.byId("treeCarpetasBusqueda").destroyRecursive();
         }

         createTreeCarpetasBusqueda(dijit.byId("borderContainerCarpetasBusqueda"), dijit.byId("contextMenuCarpetasBusqueda"));
      }
   });
   
   console.log("vamos a destrozar dlgNuevoCarpetaBusqueda");
   if (formulario=="frmNuevaCarpetaBusqueda" && dijit.byId("dlgNuevoCarpetaBusqueda")) {
      console.log("dlgNuevoCarpetaBusqueda destrozada");
      dijit.byId("dlgNuevoCarpetaBusqueda").destroy();
      //dijit.byId("dlgNuevoCarpetaBusqueda").hide();
   }

console.log("vamos a destrozar dlgModificarCarpetaBusqueda");
   if (formulario=="frmModificarCarpetaBusqueda" && dijit.byId("dlgModificarCarpetaBusqueda")) {
      console.log("dlgModificarCarpetaBusqueda destrozada");
      dijit.byId("dlgModificarCarpetaBusqueda").destroy();
      //dijit.byId("dlgModificarCarpetaBusqueda").hide();
   }

   return true;
   //NUEVA INTERFAZ - DOJO 1.3
}
dojo.addOnLoad(function() {
   new dijit.form.FilteringSelect({
      name           : "objCB.tipodocumento",
      store          : new dojo.data.ItemFileReadStore({
         url: "autocompletarAllTipoDocumento.action"
      }),
      searchAttr     : "label",
      queryExpr      : "*${0}*",
      autoComplete   : false,
      hasDownArrow   : true,
      highlightMatch : "all",
      //invalidMessage : "Seleccione un tipo de Documento",
      value			: document.getElementById("tipo").value,
      required       : false
   }, "fsTipoDoc");
});