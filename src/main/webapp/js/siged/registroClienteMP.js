var provincia;
var distrito;
var operacion;

var changeSomeFields = function(sTipoIdentificacion) {
   console.log("Tipo de Identificacion [" + sTipoIdentificacion + "]");

   if (sTipoIdentificacion == undefined || sTipoIdentificacion == "" || sTipoIdentificacion == "RUC") {
      prepareData("DNI", "hide");
      prepareData("RUC", "show");
   } else {
      prepareData("RUC", "hide");
      prepareData("DNI", "show");
   }
};

function openModificarCliente(){
    var persona = "";
    var tipoPersonaJuridica = "";
    operacion = "MODIFICARCLIENTE";
    var control = "";
               
    if (TRAMITE=="1")
      control = "Tramite";
        
    
    resetRegistroCliente();
    
           if (Siged.Forms.radiobuttonGetCheckedByName("objDD.idTipoCliente")=="0"){
               alert("No puede ingresar Entidades o Empresas, comuniquese con el área de OGD");
               return;
               
               tipoPersonaJuridica = "0"; 
               persona = dijit.byId('objDD.iIdInstitucion' + control).value;
               if (persona==""){
                 alert("Debe seleccionar la entidad a modificar");
                 return;
               }    
              /* var storeTipoInstitucion = new dojo.data.ItemFileWriteStore({url: "autocompletarTipoInstitucion.action?tipoPersonaJuridica=02"});
               storeTipoInstitucion.close();
               storeTipoInstitucion.fetch();
               dijit.byId('objCliente.codTipoInstitucion').store=storeTipoInstitucion;*/
           }
           if (Siged.Forms.radiobuttonGetCheckedByName("objDD.idTipoCliente")=="1"){
               tipoPersonaJuridica = "1";
               persona = dijit.byId('objDD.idPersonaNatural' + control).value;
               if (persona==""){
                 alert("Debe ingresar los datos de la persona natural a modificar");
                 return;
              }
           }
           if (Siged.Forms.radiobuttonGetCheckedByName("objDD.idTipoCliente")=="2"){
               alert("No puede ingresar Entidades o Empresas, comuniquese con el área de OGD");
               return;
               
               tipoPersonaJuridica = "2";
               persona = dijit.byId('objDD.iIdEmpresa' + control).value;
               if (persona==""){
                 alert("Debe seleccionar la empresa a modificar");
                 return;
               }
              /* var storeTipoInstitucion = new dojo.data.ItemFileWriteStore({url: "autocompletarTipoInstitucion.action?tipoPersonaJuridica=01"});
               storeTipoInstitucion.close();
               storeTipoInstitucion.fetch();
               dijit.byId('objCliente.codTipoInstitucion').store=storeTipoInstitucion;*/
           }

           var service = new dojo.rpc.JsonService("SMDAction.action");
           service.getCliente(parseInt(persona)).addCallback(function(cliente){
             if (cliente.codRespuesta == "ERROR"){
                alert("Ha ocurrido un error inesperado, vuelva a intentar.");
                return;
             }else{
                  /*if (cliente.nroIdentificacion!="" && cliente.nroIdentificacion.length>1 && cliente.nroIdentificacion.substr(0,2)=="G-"){
                     dijit.byId("objCliente.numeroIdentificacion").attr("value", ""); 
                  }else{
                     dijit.byId("objCliente.numeroIdentificacion").attr("value", cliente.nroIdentificacion); 
                  }*/
                   
                  if (cliente.nroIdentificacion == null || (cliente.nroIdentificacion=="" && cliente.nroIdentificacion.length==0)){
                     dijit.byId("objCliente.numeroIdentificacion").attr("value", ""); 
                  }else{
                     dijit.byId("objCliente.numeroIdentificacion").attr("value", cliente.nroIdentificacion); 
                  } 
                  
                  dijit.byId("objCliente.direccionPrincipal").attr("value", cliente.direccion);
                  dijit.byId("objCliente.telefono").attr("value", cliente.telefono);
                  dijit.byId("objCliente.correo").attr("value", cliente.correo);
                  provincia=cliente.provincia;
	          distrito=cliente.distrito;
		  dijit.byId("objCliente.ubigeoPrincipal.provincia.departamento.iddepartamento").setValue(cliente.departamento);  
                  
                  dojo.query("input[name='sTipoIdentificacionNC']").forEach(function(node) {
                     if (node.value==cliente.identificacion){
                        dijit.byId(node.id).attr("checked", true);
                     }
                  }); 

                  if (tipoPersonaJuridica == "0" || tipoPersonaJuridica == "2"){   
                     //dijit.byId("objCliente.codTipoInstitucion").attr("value", cliente.codTipoInstitucion);     
                     dijit.byId("objCliente.razonSocial").attr("value", cliente.razonSocial);
                  }else{
                     dijit.byId("objCliente.nombres").attr("value",cliente.nombre);
                     dijit.byId("objCliente.apellidoPaterno").attr("value",cliente.apellidoPaterno);
                     dijit.byId("objCliente.apellidoMaterno").attr("value",cliente.apellidoMaterno);
                    
                     //var jsonStoreCargo = new dojo.data.ItemFileWriteStore({url: 'autocompletarFiltroCargos.action?desCargo=' + cliente.desCargo}); 
                     /*var jsonStoreCargo = new dojo.data.ItemFileWriteStore({url: 'autocompletarCargoAdministrado.action?codCargo=' + cliente.codCargo}); 
                     jsonStoreCargo.close();
                     jsonStoreCargo.fetch();
                     dijit.byId("objCliente.codCargoCliente").store = jsonStoreCargo;
                     dijit.byId("objCliente.codCargoCliente").attr("value", cliente.codCargo);*/
                    
                  }    
                  
                  dijit.byId("dlgNuevoCliente").show();
             }    
         }); 
}

 function openNuevoCliente(){
    var bandera = false;
    operacion = "NUEVOCLIENTE";
    resetRegistroCliente();
    dojo.query("input[name='objDD.idTipoCliente']").forEach(function(node) {
               if (dijit.byId(node.id).attr("checked")){
                    if (dijit.byId(node.id).attr("value")=="0"){
                         bandera = true;
                    }if (dijit.byId(node.id).attr("value")=="2"){
                         bandera = true;          
                    }
                }   
    }); 
    
    if (bandera){
       alert("No puede ingresar Entidades o Empresas, comuniquese con el área de OGD");
       return;
       
       dojo.query("input[name='sTipoIdentificacionNC']").forEach(function(node){
         if (node.value=="RUC"){
           dijit.byId(node.id).attr("checked", true);
         }
       }); 
    }else{
        dojo.query("input[name='sTipoIdentificacionNC']").forEach(function(node){
         if (node.value=="DNI"){
           dijit.byId(node.id).attr("checked", true);
         }
       });
    }
    
    dijit.byId("dlgNuevoCliente").show();
   
}

var showMe = function() {
	   resetRegistroCliente();
	   dijit.byId("dlgNuevoCliente").show();
};

var buscarCliente = function() {
   var sFiltroBusqueda = dijit.byId("txtFiltroBusquedaCliente").attr("value");

   console.debug("(buscarCliente) Filtro de busqueda [%s]", sFiltroBusqueda);

   if (!Siged.String.isEmpty(sFiltroBusqueda)) {
      dijit.byId("grdBusquedaCliente").setStore(new dojo.data.ItemFileReadStore({url: "buscarCliente.action?sFiltroBusqueda=" + sFiltroBusqueda}));
   }
   dijit.byId("btnBuscar").disabled=true;
   setTimeout("esperar()", 3000);
};

function esperar(){
	dijit.byId("btnBuscar").disabled=false;
}
var createCliente = function() {
         var objclienteId = "";
         var data = dijit.byId("dlgNuevoCliente").attr("value");
	 var msg ="";
         var myForm = dijit.byId("frmNuevoCliente");
         var tipo = "";
         var control = "";
               
        if (TRAMITE=="1")
           control = "Tramite";
        
        if (!myForm.validate()) {
              return;
         }
         
         if (Siged.Forms.radiobuttonGetCheckedByName("objDD.idTipoCliente")=="0")
             tipo = "1";
         if (Siged.Forms.radiobuttonGetCheckedByName("objDD.idTipoCliente")=="2")
             tipo = "2";
         if (Siged.Forms.radiobuttonGetCheckedByName("objDD.idTipoCliente")=="1")
             tipo = "3";
        
         if (operacion == "MODIFICARCLIENTE"){
           if (Siged.Forms.radiobuttonGetCheckedByName("objDD.idTipoCliente")=="0"){
              objclienteId =  dijit.byId("objDD.iIdInstitucion" + control).value;   
           }
           if (Siged.Forms.radiobuttonGetCheckedByName("objDD.idTipoCliente")=="2"){
               objclienteId =  dijit.byId("objDD.iIdEmpresa" + control ).value;   
           }
           if (Siged.Forms.radiobuttonGetCheckedByName("objDD.idTipoCliente")=="1"){
              objclienteId =  dijit.byId("objDD.idPersonaNatural" + control).value; 
           }   
         }
 
         dojo.query("input[name='objDD.idTipoCliente']").forEach(function(node) {
                if (dijit.byId(node.id).attr("checked")){
                    if (dijit.byId(node.id).attr("value")=="0" || dijit.byId(node.id).attr("value")=="2"){
                       data.sTipoIdentificacionNC = "RUC";  
                    }else{
                       data.objCliente.codTipoInstitucion = "";
                    }
                }   
	 }); 
        
        
	 if (!dijit.byId("frmNuevoCliente").validate()) {
	    msg =  "Data inv&aacute;lida... Corregir campos <br>" ;	
         }
         
         if(data.objCliente.ubigeoPrincipal.iddistrito != "" ||
		     data.objCliente.ubigeoPrincipal.provincia.idprovincia != "" ||
		     data.objCliente.ubigeoPrincipal.provincia.departamento.iddepartamento != ""){                        
			if (data.objCliente.ubigeoPrincipal.iddistrito=="" || data.objCliente.ubigeoPrincipal.provincia.idprovincia ==""
                            || data.objCliente.ubigeoPrincipal.provincia.departamento.iddepartamento==""){
		            msg = msg+ "Debe completar los datos de ubigeo " +"<br>";
		  }
	 }
  
         if(data.objCliente.ubigeoPrincipal.iddistrito != "" ||
             data.objCliente.ubigeoPrincipal.provincia.idprovincia != "" ||
             data.objCliente.ubigeoPrincipal.provincia.departamento.iddepartamento != ""){
                 if(data.objCliente.direccionPrincipal == ""){
                      msg =  msg+  "Debe ingresar el campo Direccion Principal" +"<br>";
                 }    
         }
	
        if(msg!=""){
	   var mensaje = document.getElementById("showErrorClientemp");
	   console.log("mensaje : "+mensaje.innerHTML );
	   mensaje.innerHTML = msg ;

	   return;
        }

   dijit.byId("btnRegistroCliente").attr("disabled", true);
   dojo.byId("showErrorClientemp").innerHTML = "&nbsp;";
   
   var identificacion = "";
   
   dojo.query("input[name='sTipoIdentificacionNC']").forEach(function(node) {
      if (node.checked) {
         identificacion = node.value;
     }
   }); 
   
   if (confirm("Desea registrar el Cliente?")) {
      var service = new dojo.rpc.JsonService("SMDAction.action");
      var defered = service.saveCliente(objclienteId,
                                        identificacion, 
                                        data.objCliente.numeroIdentificacion,
                                        data.objCliente.nombres,
                                        data.objCliente.apellidoPaterno,
                                        data.objCliente.apellidoMaterno,
                                        "",
                                        data.objCliente.direccionPrincipal,
                                        data.objCliente.telefono,
                                        data.objCliente.correo,
                                        data.objCliente.ubigeoPrincipal.iddistrito,
                                        data.objCliente.razonSocial,
                                        tipo
                                        );
      
      dijit.byId("dlgProgresBar").show() ;
      defered.addCallback(function(sResult) {
         dijit.byId("dlgProgresBar").hide() ;
         if (sResult == "ERROR"){
             alert("No se pudo registrar la información, vuelva a intentarlo");
             return;
         }
         else if (sResult == "EXISTS") {
            alert("El cliente ya existe con los datos ingresados.");
            return;
         }
         else if (sResult == "NotCreated") {
            console.log("[createCliente] - No se pudo registrar el cliente");
            dojo.byId("showErrorClientemp").innerHTML = "El Nro de Identificacion ya existe en la Base de Datos";
            alert("El Nro de Identificacion ya existe en la Base de Datos");
            dijit.byId("btnRegistroCliente").attr("disabled", false);
            dijit.byId("dlgNuevoCliente").show();
         } else {
             alert("La información se registró satisfactoriamente.");
             
             if (Siged.Forms.radiobuttonGetCheckedByName("objDD.idTipoCliente")=="0"){   
                 var jsonStoreInstitucion = new dojo.data.ItemFileWriteStore({url: 'obtenerCliente.action?iIdCliente=' + sResult}); 
                 jsonStoreInstitucion.close();
                 jsonStoreInstitucion.fetch();
                 
               
                 dijit.byId('objDD.iIdInstitucion' + control).store=jsonStoreInstitucion;  
                 dijit.byId('objDD.iIdInstitucion' + control).attr("value", sResult);
                 
                 if (operacion == "NUEVOCLIENTE"){
                   document.getElementById("entidad").value = sResult;  
                 }
                 
             }
             if (Siged.Forms.radiobuttonGetCheckedByName("objDD.idTipoCliente")=="1"){
                 var nombres = data.objCliente.nombres.trim() + " " + data.objCliente.apellidoPaterno.trim() + " " + data.objCliente.apellidoMaterno.trim();
                 nombres = nombres.trim();
                 var jsonStorePersona = new dojo.data.ItemFileWriteStore({url: 'autocompletarFiltroPersonas.action?desPersona=' + nombres}); 
                     
                 jsonStorePersona.close();
                 jsonStorePersona.fetch();
                 dijit.byId('objDD.idPersonaNatural' + control).store=jsonStorePersona; 
                 dijit.byId("objDD.idPersonaNatural" + control).attr("value", sResult);
                 /*
                 var jsonStoreCargo = new dojo.data.ItemFileWriteStore({url: 'autocompletarFiltroCargos.action?desCargo=' + dijit.byId("objCliente.codCargoCliente").attr("displayedValue")}); 
                 jsonStoreCargo.close();
                 jsonStoreCargo.fetch();
                 dijit.byId("objDD.codCargoPersonaNaturalTramite").store = jsonStoreCargo;
                 dijit.byId("objDD.codCargoPersonaNaturalTramite").attr("value", data.objCliente.codCargoCliente); */
             }
             
             if (Siged.Forms.radiobuttonGetCheckedByName("objDD.idTipoCliente")=="2"){
                 var jsonStoreEmpresa = new dojo.data.ItemFileWriteStore({url: 'obtenerCliente.action?iIdCliente=' + sResult}); 
                 jsonStoreEmpresa.close();
                 jsonStoreEmpresa.fetch();
                 dijit.byId('objDD.iIdEmpresa' + control).store=jsonStoreEmpresa;  
                 dijit.byId('objDD.iIdEmpresa' + control).attr("value", sResult);
                 
                 if (operacion == "NUEVOCLIENTE"){
                   document.getElementById("entidad").value = sResult;  
                 }
                 
                 
             }
             
             dijit.byId("dlgNuevoCliente").hide();
         }
      });
   } else {
      dijit.byId("btnRegistroCliente").attr("disabled", false);
   }
   
      dijit.byId("btnRegistroCliente").attr("disabled", false);

};

var prepareData = function(sTipo, sMode) {
       if(sTipo=="RUC"){
                dijit.byId("objCliente.razonSocial").setValue("");
                if (sMode == "show") {
                	dijit.byId("objCliente.razonSocial").attr("required", true);
                       // dijit.byId("objCliente.codTipoInstitucion").attr("required", true);
                	showTBODY("tbDataNCRUC");
                } else {
                	dijit.byId("objCliente.razonSocial").attr("required", false);
                        //dijit.byId("objCliente.codTipoInstitucion").attr("required", false);
		        hideTBODY("tbDataNCRUC");
		}
                
                
	} else {
                dijit.byId("objCliente.nombres").setValue("");
                dijit.byId("objCliente.apellidoPaterno").setValue("");
	        dijit.byId("objCliente.apellidoMaterno").setValue("");
                if (sMode == "show") {
                	dijit.byId("objCliente.nombres").attr("required", true);
			dijit.byId("objCliente.apellidoPaterno").attr("required", true);
                       // dijit.byId("objCliente.codCargoCliente").attr("required", true);
                	showTBODY("tbDataNCDNI");
                       // showTBODY("tbDataCargo");
                } else {
                        //dijit.byId("objCliente.codCargoCliente").attr("required", false);
                	dijit.byId("objCliente.nombres").attr("required", false);
                	dijit.byId("objCliente.apellidoPaterno").attr("required", false);
		        hideTBODY("tbDataNCDNI");    
                       // hideTBODY("tbDataCargo");
                }               
	}
};

var resetBusquedaCliente = function() {
   dijit.byId("txtFiltroBusquedaCliente").attr("value", "");
   dijit.byId("grdBusquedaCliente").showMessage("");
   dijit.byId("grdBusquedaCliente").setStore(null);
};

var resetRegistroCliente = function() {
       dojo.byId("showErrorClientemp").innerHTML = "&nbsp;";
       dlgNuevoCliente.reset();
       dojo.query("input[name='objDD.idTipoCliente']").forEach(function(node) { 
               if (dijit.byId(node.id).attr("checked")){
                    if (dijit.byId(node.id).attr("value")=="0" || dijit.byId(node.id).attr("value")=="2"){
                        prepareData("DNI", "hide");
        		prepareData("RUC", "show");
                        showTBODY("tbOtro");
                    }else{
                        showTBODY("tbOtro");
                        prepareData("DNI", "show");
        		prepareData("RUC", "hide");
                    }
                }   
	}); 
        
        dojo.query("input[name='sTipoIdentificacionNC']").forEach(function(node){
         if (node.value=="RUC"){
           dijit.byId(node.id).attr("checked", true);
         }
       });
};

var selectClienteFromGrdBusquedaCliente = function(e) {
   if (e.rowIndex == undefined) {
      return;
   }

   var numeroIdentificacion = dijit.byId("grdBusquedaCliente").getItem(e.rowIndex).numeroidentificacion;
   var tipoIdentificacion = dijit.byId("grdBusquedaCliente").getItem(e.rowIndex).tipoidentificacion;

   console.debug("(selectClienteFromGrdBusquedaCliente) rowIndex [%s] tipoIdentificacion [%s] numeroIdentificacion [%s]", e.rowIndex, tipoIdentificacion, numeroIdentificacion);
   Siged.Forms.radiobuttonResetByName("sTipoIdentificacion", tipoIdentificacion);

   //FIXME estandarizar la definicion de los componentes - para el caso de QAS
   if (dijit.byId("objDocumento.expediente.cliente.numeroIdentificacion")) {
      Siged.Forms.comboboxChangeStore("objDocumento.expediente.cliente.numeroIdentificacion", "obtenerClientePorTipoIdentificacion.action?sTipoIdentificacion=" + "", numeroIdentificacion);
   } else if (dijit.byId("objDD.strNroIdentificacion")) {
      Siged.Forms.comboboxChangeStore("objDD.strNroIdentificacion", "obtenerClientePorTipoIdentificacion.action?sTipoIdentificacion=" + "", numeroIdentificacion);
   }

   dijit.byId("dlgBusquedaCliente").hide();
};

var showGrdBusquedaCliente = function() {

   dijit.byId("txtFiltroBusquedaCliente").attr("value", "");
   dijit.byId("grdBusquedaCliente").showMessage("");
   dijit.byId("grdBusquedaCliente").setStore(null);
   dijit.byId("dlgBusquedaCliente").show();

};

var showGrdBusquedaCliente_ = function() {

	   //dijit.byId("txtFiltroBusquedaCliente").attr("value", "");
	   dijit.byId("grdBusquedaCliente").showMessage("");
	   dijit.byId("grdBusquedaCliente").setStore(null);
	   dijit.byId("dlgBusquedaCliente").show();

	};

var showRegistroClienteQAS = function() {
   resetRegistroCliente();
   dijit.byId("dlgNuevoCliente").show();
};

var showRegistroCliente = function() {
	resetRegistroCliente();

   //FIXME estandarizar la definicion de los componentes - para el caso de QAS
   if (dijit.byId("objDocumento.expediente.cliente.numeroIdentificacion")) {
      // ponemos el texto ingresado en el autocomplete y lo pasamos al formulario de nuevo cliente
      var texto=document.getElementById("objDocumento.expediente.cliente.numeroIdentificacion").value;
      var numero=!isNaN(texto);
      if(numero){
         dijit.byId("objCliente.numeroIdentificacion").setValue(texto);
      }
      else{
         var seleccionado;
         dojo.query("input[name='sTipoIdentificacion']").forEach(function(radio){
            if(radio.checked){
               seleccionado=radio.value;
            }
         });
         if(seleccionado=="RUC"){
            dijit.byId("objCliente.razonSocial").setValue(texto);
         }
         else{
            //TODO los campos para la reniec
         }
      }
   } else if (dijit.byId("objDD.strNroIdentificacion")) {

   }

	dijit.byId("dlgNuevoCliente").show();
};

var cancelarRegistro=function(){
	dijit.byId("dlgNuevoCliente").hide();
	resetRegistroCliente();
};

var validateNroIdentificacion = function(constraints) {
   var sRegExp = ".{1,20}";

   dojo.query("input[name='sTipoIdentificacionNC']").forEach(function(node) {
      if (node.checked) {
         if (node.value == "RUC") {
            sRegExp = "\\d{11}";
         } else if (node.value == "DNI") {
            sRegExp = "\\d{8}";
         }
      }
   });

   return sRegExp;
};

var buscarPorRUC=function(){
	var ruc=document.getElementById("objCliente.numeroIdentificacion").value;
	if(ruc!=null && ruc!="" && ruc.lenght!=11){
		buscarRuc(ruc);
	}
};

function buscarRuc(ruc){
	dijit.byId("dlgProgresBar").show();
	service.getClientePorRUC(ruc).addCallback(function(cliente){
		dijit.byId("dlgProgresBar").hide();
		if(cliente.identificacion==ruc){
			resetRegistroCliente();
			dijit.byId("objCliente.numeroIdentificacion").setValue(ruc);
			dijit.byId("objCliente.razonSocial").setValue(cliente.razonSocial);
			dijit.byId("objCliente.direccionPrincipal").setValue(cliente.direccion);
			provincia=cliente.provincia;
			distrito=cliente.distrito;
			dijit.byId("objCliente.ubigeoPrincipal.provincia.departamento.iddepartamento").setValue(cliente.departamento);
			dijit.byId("objCliente.telefono").setValue(cliente.telefono);
			dijit.byId("objCliente.correo").setValue(cliente.correo);
		}
		else{
			error(cliente.identificacion);
		}
	});
}

var buscarPorRazonSocial=function(){
	error("");
	var razonSocial=dijit.byId("objCliente.razonSocial").getValue();
	if(razonSocial!=null && razonSocial!=""){
		if(razonSocial.length>5){
			dijit.byId("dlgProgresBar").show();
			service.getClientesPorRazonSocial(razonSocial).addCallback(function(clientes){
				var l=clientes.length;
				if(l>0){
					resetRegistroCliente();
					//var opciones="";
					var select=document.getElementById("clientes");
					select.innerHTML="";
					for(var i=0;i<l;i++){
						select[select.length]=new Option(clientes[i].identificacion+" - "+clientes[i].razonSocial,clientes[i].identificacion);
						//var opcion="<option value=\""+clientes[i].identificacion+"\">"+clientes[i].razonSocial+"</option>";
						//opciones+=opcion;
					}
					//document.getElementById("clientes").innerHTML=opciones;
					document.getElementById("seleccionClientes").style.display="block";
				}
				else{
					error("La raz&oacute;n social buscada no retorn&oacute; ninguna coincidencia.");
				}
				dijit.byId("dlgProgresBar").hide();
			});
		}
		else{
			error("El texto a buscar debe tener m&aacute;s de 5 caracteres.");
		}
	}
};

var seleccionarCliente=function(){
	var ruc=document.getElementById("clientes").value;
	if(ruc!=null && ruc!=""){
		buscarRuc(ruc);
		document.getElementById("seleccionClientes").style.display="none";
	}
};

var cancelar=function(){
	document.getElementById("seleccionClientes").style.display="none";
};

function error(texto){
	document.getElementById("showErrorClientemp").innerHTML=texto;
}

dojo.addOnLoad(function() {
    
 try {
      new dijit.form.FilteringSelect({
      id:   "objCliente.codCargoCliente",
      jsId: "objCliente.codCargoCliente",
      name: "objCliente.codCargoCliente",
      autoComplete: false,
      queryExpr: "*${0}*",
      searchAttr: "label",
      hasDownArrow: false,
      highlightMatch : "all",
      required       : true,
      style          : "width:300px;",
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
    },"fsCargoCliente");
  }catch(err){

}  
    
    Siged.Forms.combobox("fsDepartamentoNC1", {
      id             : "objCliente.ubigeoPrincipal.provincia.departamento.iddepartamento",
      jsId           : "objCliente.ubigeoPrincipal.provincia.departamento.iddepartamento",
      name           : "objCliente.ubigeoPrincipal.provincia.departamento.iddepartamento",
      searchAttr     : "label",
      searchDelay    : 600,
      queryExpr      : "*${0}*",
      autoComplete   : false,
      hasDownArrow   : true,
      highlightMatch : "all",
      required       : false,
      style          : "width:200px;",
      invalidMessage : "Seleccione un departamento",
      onChange       : function(){
			updateStore(this.value, 'provinciaNC1');
			dijit.byId("objCliente.ubigeoPrincipal.provincia.idprovincia").attr("value",provincia);
			},
      storeUrl       : "listaDepartamentos.action"
   }); 
   
   
try {
	new dijit.form.FilteringSelect({
		id             : "objCliente.ubigeoPrincipal.provincia.idprovincia",
		jsId           : "objCliente.ubigeoPrincipal.provincia.idprovincia",
		name           : "objCliente.ubigeoPrincipal.provincia.idprovincia",
		/*store          : new dojo.data.ItemFileReadStore({
			url: "listaProvincias.action"
		}),*/
		searchAttr     : "label",
		queryExpr      : "*${0}*",
		autoComplete   : false,
                style          : "width:150px;",
		hasDownArrow   : true,
		highlightMatch : "all",
		invalidMessage : "Seleccione una provincia",
		onChange       : function(){
			updateStore(this.value, 'distritoNC1');
			dijit.byId("objCliente.ubigeoPrincipal.iddistrito").attr("value",distrito);
		},
		required       : false
	},"fsProvinciaNC1");
}catch(err){
	   console.debug("1error regisetring objCliente.ubigeoPrincipal.provincia.idprovincia");
}

try {

   new dijit.form.FilteringSelect({
      id             : "objCliente.ubigeoPrincipal.iddistrito",
      jsId           : "objCliente.ubigeoPrincipal.iddistrito",
      name           : "objCliente.ubigeoPrincipal.iddistrito",
      /*store          : new dojo.data.ItemFileReadStore({
         url: "listaDistritos.action"
      }),*/
      searchAttr     : "label",
      queryExpr      : "*${0}*",
      autoComplete   : false,
      hasDownArrow   : true,
      highlightMatch : "all",
      style          : "width:150px;",
      invalidMessage : "Seleccione un distrito",
      required       : false
   }, "fsDistritoNC1");

}catch(err){
	   console.debug("1error regisetring objCliente.ubigeoPrincipal.iddistrito");
}

    /*  JCCCCCC

    Siged.Forms.combobox("fsTipoInstitucionCliente", {
      id             : "objCliente.codTipoInstitucion",
      jsId           : "objCliente.codTipoInstitucion",
      name           : "objCliente.codTipoInstitucion",
      searchAttr     : "label",
      searchDelay    : 600,
      queryExpr      : "*${0}*",
      autoComplete   : false,
      hasDownArrow   : true,
      highlightMatch : "all",
      required       : false,
      style          : "width:200px;",
      invalidMessage : "Seleccione un tipo de Institucion",
      storeUrl       : "autocompletarTipoInstitucion.action"
   });

   */
   new dijit.form.Button({
      onClick:	buscarPorRazonSocial
   },"buscarRazonSocial");

   new dijit.form.Button({
      onClick:	seleccionarCliente
   },"seleccionar");

   new dijit.form.Button({
      onClick:	cancelar
   },"cancelar");

});
