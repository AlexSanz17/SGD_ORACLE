showBusquedaExpedienteRemitente = function(){
        var persona="";
        if (Siged.Forms.radiobuttonGetCheckedByName("objDD.idTipoCliente")=="0"){
            persona = dijit.byId('objDD.iIdInstitucionTramite').value;
            if (persona==""){
                alert("Debe seleccionar una entidad para visualizar sus expedientes creados.");
                return;
            }
        }
        if (Siged.Forms.radiobuttonGetCheckedByName("objDD.idTipoCliente")=="1"){
            persona = dijit.byId('objDD.idPersonaNaturalTramite').value;
            if (persona==""){
                alert("Debe seleccionar una persona natural para visualizar sus expedientes creados.");
                return;
            }
        }
        if (Siged.Forms.radiobuttonGetCheckedByName("objDD.idTipoCliente")=="2"){
            persona = dijit.byId('objDD.iIdEmpresaTramite').value;
            if (persona==""){
                alert("Debe seleccionar una empresa para visualizar sus expedientes creados.");
                return;
            }
        }
        if (persona!=""){
            var strBusquedaExpedienteAbierto = new dojo.data.ItemFileReadStore({
                url: "buscarExpedienteAbierto.action?iIdCliente=" + parseInt(persona)
            });
        
            grdBusquedaExpedienteAbierto.setStore(strBusquedaExpedienteAbierto);
            iExpedientesAbiertos = 0;
            strBusquedaExpedienteAbierto.fetch({
                query: {
                        id: "*"
                },
                onComplete: function(items) {
                   if (items.length>0){
                       dlgBusquedaExpedienteAbierto.show();   
                   }else{
                       alert("No existen expedientes asociados al cliente.");
                   }
                }
            });
            
        }else{
            
        }    
};


var seleccionarFilaExpedienteAbierto=function(e) {
    
   var control = '';
   
   if (e.rowIndex == undefined) {
      return;
   }
  
   if (TRAMITE=="1")
       control = "Tramite";
   
  // var idproceso = dijit.byId("grdBusquedaExpedienteAbierto").getItem(e.rowIndex).idProceso;
   var asuntoExp = new String(dijit.byId("grdBusquedaExpedienteAbierto").getItem(e.rowIndex).asunto);
   var observacion = new String(dijit.byId("grdBusquedaExpedienteAbierto").getItem(e.rowIndex).observaciones);
   var idserie = dijit.byId("grdBusquedaExpedienteAbierto").getItem(e.rowIndex).serie;
   
   //dijit.byId("objDocumento.expediente.proceso.idproceso" + control).setValue(idproceso);
   dijit.byId("objDD.asuntoExpediente" + control).setValue(asuntoExp);
   dijit.byId("objDD.iIdSerie"+ control).setValue(idserie);
   dijit.byId("objDD.observacionExpediente" + control).setValue(observacion);
   dijit.byId("sNroExpediente" + control).attr("value", dijit.byId("grdBusquedaExpedienteAbierto").getItem(e.rowIndex).nroexpediente);
   dijit.byId("objDD.iIdExpediente" + control).setValue(dijit.byId("grdBusquedaExpedienteAbierto").getItem(e.rowIndex).id);
   
   if (TIPO_TRANSACCION == "N"){
        dijit.byId("objDD.asuntoExpediente"+ control).attr("readOnly", true);
        dijit.byId("objDD.observacionExpediente"+ control).attr("readOnly", true);
        dijit.byId("objDD.iIdSerie"+ control).attr("readOnly", true);
   }
    
   dijit.byId("dlgBusquedaExpedienteAbierto").hide();
};



