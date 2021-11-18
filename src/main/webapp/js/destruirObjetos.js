var destroyControles = function() {
    if (dijit.byId("btnRegistrarDocumentoTop")) {
      dijit.byId("btnRegistrarDocumentoTop").destroy();
   }
   
    if (dijit.byId("frmNuevoDocumento ")) {
      dijit.byId("frmNuevoDocumento ").destroy();
   }
};

var destroyWidgetsFromBusquedaExpediente = function() {
    /*if (dijit.byId("sTipoLegajo")) {
      dijit.byId("sTipoLegajo").destroy();
   }  
   
   if (dijit.byId("sTipoExpediente")) {
      dijit.byId("sTipoExpediente").destroy();
   }  */
    
   if (dijit.byId("sNroExpedienteBE")) {
      dijit.byId("sNroExpedienteBE").destroy();
   }

   if (dijit.byId("sAsuntoBE")) {
      dijit.byId("sAsuntoBE").destroy();
   }

   if (dijit.byId("sRazonSocialBE")) {
      dijit.byId("sRazonSocialBE").destroy();
   }

   if (dijit.byId("sNTBE")) {
      dijit.byId("sNTBE").destroy();
   }
   
   if (dijit.byId("fechaBE")) {
      dijit.byId("fechaBE").destroy();
   }

   if (dijit.byId("grdBusquedaExpediente")) {
      dijit.byId("grdBusquedaExpediente").destroy();
   }

   if (dijit.byId("dlgBusquedaExpediente")) {
      dijit.byId("dlgBusquedaExpediente").destroy();
   } 
};

var destroyWidgetsFromLegajo = function (){
   if (dijit.byId("dlgCrearLegajo")) {
      dijit.byId("dlgCrearLegajo").destroy();
   } 
   
   if (dijit.byId("sTipoLegajo")) {
      dijit.byId("sTipoLegajo").destroy();
   } 
   
   if (dijit.byId("sTipoProcedimiento")) {
      dijit.byId("sTipoProcedimiento").destroy();
   } 
   
   if (dijit.byId("sTipoMetodo")) {
      dijit.byId("sTipoMetodo").destroy();
   } 
   
   if (dijit.byId("sNroTipoLegajo")) {
      dijit.byId("sNroTipoLegajo").destroy();
   }
   
   if (dijit.byId("sAsuntoLegajo")) {
      dijit.byId("sAsuntoLegajo").destroy();
   } 
   
   if (dijit.byId("sObservacionLegajo")) {
      dijit.byId("sObservacionLegajo").destroy();
   } 
   
   if (dijit.byId("registrarLegajo")) {
      dijit.byId("registrarLegajo").destroy();
   } 
   
   if (dijit.byId("sNota1Legajo")) {
      dijit.byId("sNota1Legajo").destroy();
   } 
   
   if (dijit.byId("sNota2Legajo")) {
      dijit.byId("sNota2Legajo").destroy();
   } 
   
   if (dijit.byId("sNota3Legajo")) {
      dijit.byId("sNota3Legajo").destroy();
   } 
   
   if (dijit.byId("sNota4Legajo")) {
      dijit.byId("sNota4Legajo").destroy();
   } 
};

var destroyWidgetsFromBusquedaDocumento = function (){
   if (dijit.byId("dlgBusquedaDocumento")) {
      dijit.byId("dlgBusquedaDocumento").destroy();
   } 
   
    if (dijit.byId("sTipoExpediente")) {
      dijit.byId("sTipoExpediente").destroy();
    }  
   
   if (dijit.byId("sNroLegajo")) {
      dijit.byId("sNroLegajo").destroy();
   } 
   
   if (dijit.byId("sExpediente")) {
      dijit.byId("sExpediente").destroy();
   } 
   
  /* if (dijit.byId("sTipoLegajo")) {
      dijit.byId("sTipoLegajo").destroy();
   }*/ 
   
   if (dijit.byId("sTipoDocumento")) {
      dijit.byId("sTipoDocumento").destroy();
   } 
   
   if (dijit.byId("sNroDocumento")) {
      dijit.byId("sNroDocumento").destroy();
   } 
   
   if (dijit.byId("sNroHT")) {
      dijit.byId("sNroHT").destroy();
   }
   
   if (dijit.byId("grdBusquedaDocumento")) {
      dijit.byId("grdBusquedaDocumento").destroy();
   }
};

var destroyControlesTramite = function(){
     if (dijit.byId("btnRegistrarDocumentoTramiteTop")) {
      dijit.byId("btnRegistrarDocumentoTramiteTop").destroy();
   } 
};