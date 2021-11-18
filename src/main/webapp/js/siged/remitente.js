/*function openNuevoRemitente(personaJuridica, opcion){
            
    if (personaJuridica==""){
       if (opcion == "0"){
          alert("Debe seleccionar una entidad");
       }else{
          alert("Debe seleccionar una empresa");  
       }
       return;
    }
            
    resetRegistroNuevoRemitente();
    dijit.byId("dlgNuevoRemitente").show();
}
         
function openModificarRemitente(personaJuridica, idRemitente, opcion){
          
    if (idRemitente == ""){
        alert("Debe seleccionar la persona remitente");
        return;
    }      
    
    resetRegistroNuevoRemitente();
   
    var service = new dojo.rpc.JsonService("SMDAction.action");
    service.getRemitente(personaJuridica, idRemitente).addCallback(function(remitente){
        if (remitente.codRespuesta == "OK"){
            dijit.byId("objRemitente.nombres").attr("value", remitente.nombres);
            dijit.byId("objRemitente.apellidoPaterno").attr("value", remitente.apellidoPaterno);
            dijit.byId("objRemitente.apellidoMaterno").attr("value", remitente.apellidoMaterno);
            //dijit.byId("objRemitente.idCargoAdministrado").attr("value", remitente.idCargoAdministrado);
            
            //var jsonStoreCargo = new dojo.data.ItemFileWriteStore({url: 'autocompletarFiltroCargos.action?desCargo=' + remitente.desCargoAdministrado}); 
             var jsonStoreCargo = new dojo.data.ItemFileWriteStore({url: 'autocompletarCargoAdministrado.action?codCargo=' + remitente.idCargoAdministrado}); 
            jsonStoreCargo.close();
            jsonStoreCargo.fetch();
            dijit.byId("objRemitente.idCargoAdministrado").store = jsonStoreCargo;
            dijit.byId("objRemitente.idCargoAdministrado").attr("value", remitente.idCargoAdministrado);
            document.getElementById("objRemitente.idDetalleCliente").value = remitente.idDetalleCliente;
            dijit.byId("dlgNuevoRemitente").show();
        }else{
            alert("Ha ocurrido un error inesperado, vuelva a intentar.");
            return;
        }    
    }); 
}

function resetRegistroNuevoRemitente(){
    dijit.byId("btnRegistroRemitente").attr("disabled", false);
    dlgNuevoRemitente.reset();
}

var cancelarRemitente = function(){
	dijit.byId("dlgNuevoRemitente").hide();
	dlgNuevoRemitente.reset();
};

var createRemitente = function() {
     dijit.byId("btnRegistroRemitente").attr("disabled", true);

     var personaJuridica = "";
     var data = dijit.byId("dlgNuevoRemitente").attr("value");
     var myForm = dijit.byId("frmNuevoRemitente");
    
     if (!myForm.validate()) {
         dijit.byId("btnRegistroRemitente").attr("disabled", false);
         return;
     }
     
     if (confirm("Desea registrar el Remitente?")) {
        var service = new dojo.rpc.JsonService("SMDAction.action");
        var defered = "";
        
        if (Siged.Forms.radiobuttonGetCheckedByName("objDD.idTipoCliente")== "0"){
            personaJuridica = parseInt(dijit.byId("objDD.iIdInstitucionTramite").value);
            defered = service.saveRemitente(data.objRemitente, personaJuridica, document.getElementById("objRemitente.idDetalleCliente").value);
        }else{
            personaJuridica = parseInt(dijit.byId("objDD.iIdEmpresaTramite").value);
            defered = service.saveRemitente(data.objRemitente, personaJuridica, document.getElementById("objRemitente.idDetalleCliente").value); 
        }
        
        dijit.byId("dlgProgresBar").show() ;
        defered.addCallback(function(sResult) {
           dijit.byId("dlgProgresBar").hide() ;
           if (sResult == "ERROR") {
              alert("No se pudo registrar el remitente, vuelva a intentarlo");
              dijit.byId("btnRegistroRemitente").attr("disabled", false);
              dijit.byId("dlgNuevoRemitente").show();
           } else {
              alert("La información se registró satisfactoriamente.");
              var storePersona = new dojo.data.ItemFileWriteStore({url: "autocompletarRemitente.action?iIdCliente=" + personaJuridica}); 
              storePersona.close();
              storePersona.fetch();
              
              if (Siged.Forms.radiobuttonGetCheckedByName("objDD.idTipoCliente")== "0"){
                 dijit.byId('objDD.idPersonaInstitucionTramite').store=storePersona;
                 dijit.byId('objDD.idPersonaInstitucionTramite').attr("value",sResult);                
                 
                if (document.getElementById("objRemitente.idDetalleCliente").value!=null &&
                  document.getElementById("objRemitente.idDetalleCliente").value!=""){
                  var storeCargo = new dojo.data.ItemFileWriteStore({url: "autocompletarCargoRemitente.action?iIdCliente=" + parseInt(personaJuridica) + "&idPersona=" + parseInt(sResult)}); 
                  storeCargo.close();
                  storeCargo.fetch();
                  dijit.byId('objDD.codCargoPersonaInstitucionTramite').store = storeCargo;
                  dijit.byId('objDD.codCargoPersonaInstitucionTramite').attr("value", data.objRemitente.idCargoAdministrado);
                }else{
                  document.getElementById("cargoPersona").value = data.objRemitente.idCargoAdministrado;   
                }
              }else{
                 dijit.byId('objDD.idPersonaEmpresaTramite').store=storePersona;
                 dijit.byId('objDD.idPersonaEmpresaTramite').attr("value",sResult);
                 
                 if (document.getElementById("objRemitente.idDetalleCliente").value!=null &&
                   document.getElementById("objRemitente.idDetalleCliente").value!=""){
                   var storeCargo = new dojo.data.ItemFileWriteStore({url: "autocompletarCargoRemitente.action?iIdCliente=" + parseInt(personaJuridica) + "&idPersona=" + parseInt(sResult)}); 
                   storeCargo.close();
                   storeCargo.fetch();
                   dijit.byId('objDD.codCargoPersonaEmpresaTramite').store = storeCargo;
                   dijit.byId('objDD.codCargoPersonaEmpresaTramite').attr("value", data.objRemitente.idCargoAdministrado);
                 }else{
                   document.getElementById("cargoPersona").value = data.objRemitente.idCargoAdministrado;   
                 }
              }
              
              
              
              dijit.byId("dlgNuevoRemitente").hide();
           }
        });
     } 
     
     dijit.byId("btnRegistroRemitente").attr("disabled", false);
}

dojo.addOnLoad(function() {
    new dijit.form.FilteringSelect({
               id             : "objRemitente.idCargoAdministrado",
               jsId           : "objRemitente.idCargoAdministrado",
               name           : "objRemitente.idCargoAdministrado",
               searchAttr     : "label",
               style : "width:200px;",
	       required : true,
               queryExpr: "*${0}*",
               hasDownArrow: false,
               highlightMatch : "all",
               autoComplete : false,
               postCreate: function() {
                             this.connect(this, "onKeyUp", "_onSuggest");
                             this._timeKey = null;
                           },
               _onSuggest: function() {
                    if(this.attr('displayedValue').length > 2) {
                        if(this.store) {
                           var url = 'autocompletarFiltroCargos.action?desCargo=' + this.attr('displayedValue');
                           this.store= new dojo.data.ItemFileWriteStore({url: url}); 
                           this.store.close();
                           this.store.fetch();
                           this._startSearch(dojo.trim(this.attr('displayedValue')));
                        }
                    }
                }
            }, "fsCargoAdministrado");
});*/