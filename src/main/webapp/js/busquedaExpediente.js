//var strBusquedaExpediente;
var tipoBusqueda;
var strBusquedaExpediente;

var seleccionarFila=function(e) {
   var control = '';
   
   if (e.rowIndex == undefined) {
      return;
   }
   
   if (TRAMITE=="1")
       control = "Tramite";
   

   var idserie = dijit.byId("grdBusquedaExpediente").getItem(e.rowIndex).serie;
   var tipoidentificacion = dijit.byId("grdBusquedaExpediente").getItem(e.rowIndex).tipoidentificacion;
   var asuntoExp = new String(dijit.byId("grdBusquedaExpediente").getItem(e.rowIndex).asunto);
   var observacion = new String(dijit.byId("grdBusquedaExpediente").getItem(e.rowIndex).observacion);
   dijit.byId("sNroExpediente"+control).attr("value", dijit.byId("grdBusquedaExpediente").getItem(e.rowIndex).nroexpediente);
   
   if(dijit.byId("objDocumento.expediente.idexpediente" + control)){
      dijit.byId("objDocumento.expediente.idexpediente").setValue(dijit.byId("grdBusquedaExpediente").getItem(e.rowIndex).id);
   }
   else if(dijit.byId("objDD.iIdExpediente"+ control)){
      dijit.byId("objDD.iIdExpediente"+ control).setValue(dijit.byId("grdBusquedaExpediente").getItem(e.rowIndex).id);
   }

   fsProcesoFocused = 0;
   expedienteNuevo = false;
   idPropietario = dijit.byId("grdBusquedaExpediente").getItem(e.rowIndex).idpropietario;
  
   dijit.byId("objDD.asuntoExpediente"+ control).setValue(asuntoExp);
   dijit.byId("objDD.observacionExpediente"+ control).setValue(observacion);
   dijit.byId("objDD.iIdSerie"+ control).setValue(idserie);
    
   if (TIPO_TRANSACCION == "N"){
        dijit.byId("objDD.asuntoExpediente"+ control).attr("readOnly", true);
        dijit.byId("objDD.observacionExpediente"+ control).attr("readOnly", true);
        dijit.byId("objDD.iIdSerie"+ control).attr("readOnly", true);
   }
  
   dojo.query("input[name='sTipoIdentificacion']").forEach(function(node) {
      console.log("Nodo [" + node.name + "] ID [" + node.id + "] VALUE [" + node.value + "] CHECKED [" + node.checked + "]");
      dijit.byId(node.id).attr("checked", false);
      dijit.byId(node.id).attr("readOnly", true);

      if (node.value == tipoidentificacion) {
         dijit.byId(node.id).attr("checked", true);
      }
   });
   
    dijit.byId("dlgBusquedaExpediente").hide();
};


var busquedaExpediente = function(llave) {
        
        var numeroExpediente=sNroExpedienteBE.getValue();
        var asunto=sAsuntoBE.getValue();
	var nt   = dijit.byId("sNTBE").getDisplayedValue();
        var numeroIdentificacion= "";//dijit.byId("sNroIdentificaBE").attr("value");
	
        if(numeroExpediente=="" && asunto=="" && nt==""){
		alert("Debe ingresar al menos un campo para la busqueda");
	}else{
             try{
                var parameters = "";
                //
                dijit.byId(llave).attr("disabled", true);
          	parameters+="sNroIdentificacion="+numeroIdentificacion+"&";
		parameters+="sNroExpediente="+numeroExpediente+"&";
		parameters+="sAsunto="+asunto+"&";
	        parameters+="sNroHT="+nt+"&";
		parameters+="tipoBusqueda="+(typeof tipoBusqueda == "undefined" ? "" : tipoBusqueda);
                strBusquedaExpediente = new dojo.data.ItemFileWriteStore({
		  url: "buscarExpediente.action?" + parameters, clearOnClose: true, urlPreventCache:true
		});
                 strBusquedaExpediente.close();
	         var sortAttributes = [{ attribute: "id", descending: true}];
                 strBusquedaExpediente.fetch({onComplete: completed, onError: error, sort: sortAttributes});
                 
                 function completed(items, findResult){ 
                    var grid = dijit.byId("grdBusquedaExpediente");
                    grid.setStore(strBusquedaExpediente);
                    grid._refresh();
                    dijit.byId(llave).attr("disabled", false);
                 }
			
	         function error(errData, request){ 
	           console.log(errData+" ... Failed cargo."+request);
	         }
            }catch(error){
                dijit.byId(llave).attr("disabled", false);
                console.log(error) ;
            } 
            
	}
};

/*
var destroyWidgetsFromBusquedaExpediente = function() {
    
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
*/
var resetBusquedaExpediente = function() {
   if (dijit.byId("sRazonSocialBE")) {
      dijit.byId("sRazonSocialBE").attr("value", "")
   }

   if (dijit.byId("sNroExpedienteBE")) {
      dijit.byId("sNroExpedienteBE").attr("value", "");
   }

   if (dijit.byId("sAsuntoBE")) {
      dijit.byId("sAsuntoBE").attr("value", "");
   }

/*
   if (dijit.byId("procesoBE")) {
      dijit.byId("procesoBE").attr("value", "");
   }*/

   if (dijit.byId("fechaBE")) {
      dijit.byId("fechaBE").attr("value", "");
      dijit.byId("fechaBE").attr("displayedValue", "");
   }
    
   if (dijit.byId("sNroExpedienteBE")) {
      dijit.byId("sNroExpedienteBE").attr("value", "");
   }
   
   if (dijit.byId("sNTBE")) {
      dijit.byId("sNTBE").attr("value", "");
   }
   
  
   if (dijit.byId("grdBusquedaExpediente")) {
      dijit.byId("grdBusquedaExpediente").setStore(null);
   } 
   
      
};

var showBusquedaExpediente = function() {
   if (dijit.byId("dlgBusquedaExpediente")) {
      resetBusquedaExpediente();
      dijit.byId("dlgBusquedaExpediente").show();
   }
};

dojo.addOnLoad(function(){
   if (dojo.isFF) {
      console.debug("Eliminando en navegador Firefox");
     // destroyWidgetsFromBusquedaExpediente();
   }

/*
	try {
	new dijit.form.FilteringSelect({
		id:		"procesoBE",
		jsId:		"procesoBE",
		name:		"procesoBE",
		store:		new dojo.data.ItemFileReadStore({
						url: "autocompletarProceso.action"
					}),
		searchAttr:	"label",
		queryExpr:	"*${0}*",
		autoComplete:	false,
		hasDownArrow:	true,
		highlightMatch:	"all",
		required:	false
	},"procesoBE");
	}catch(error) {
		console.log(error+" error registering : procesoBE") ;
	}
*/
      /*
         try {
	new dijit.form.TextBox({
		id:	"sNTBE",
		jsId:	"sNTBE",
		name:	"sNTBE",
		trim: true
	},"sNTBE");
	}catch(error) {
		console.log(error+" error registering :sNTBE") ;
	}

	try {
	new dijit.form.DateTextBox({
		id:	"fechaBE",
		jsId:	"fechaBE",
		name:	"fehcaBE",
		constraints:	{datePattern:'dd/MM/yyyy'}
	},"fechaBE");
	}catch(error) {
		console.log(error+" error registering :fechaBE") ;
	}
  
	try {
	new dijit.form.TextBox({
		id:		"sRazonSocialBE",
		jsId:	"sRazonSocialBE",
		name:	"sRazonSocialBE",
		trim: true,
                 style     : "width:300px"
	},"sRazonSocialBE");
	}catch(error) {
		console.log(error+" error registering : sRazonSocialBEE") ;
	}
      */
	
});