var lstClienteCustom = [];
var g_ProvinciaValue;
var g_DistritoValue;

var clienteCustomAdd = function() {
   var razonsocial =  dijit.byId("razonSocialClienteCustom").attr("value");
   var direccion = dijit.byId("direccionClienteCustom").attr("value");
   var departamento = dijit.byId("ubigeo.departamento").attr("displayedValue");
   var provincia = dijit.byId("ubigeo.provincia").attr("displayedValue");
   var distrito = dijit.byId("ubigeo.distrito").attr("displayedValue");
   var clienteSeleccionado = dijit.byId("objCliente.idCliente").attr("displayedValue");
   var referencia = dijit.byId("referenciaClienteCustom").attr("value");
   if ( !Siged.String.isEmpty(razonsocial) && !Siged.String.isEmpty(direccion) && !Siged.String.isEmpty(razonsocial) &&
		   !Siged.String.isEmpty(provincia) && !Siged.String.isEmpty(distrito)&& !Siged.String.isEmpty(referencia)){
	   
	   if (!Siged.Forms.selectHasOption("msClienteCustom", dijit.byId("idClienteCustom").attr("value"))) {
		      lstClienteCustom.push({
		         identificacion : dijit.byId("idClienteCustom").attr("value"),
		         razonsocial    : razonsocial,
		         direccion      : direccion,
		         departamento   : departamento,
		         provincia      : provincia,
		         distrito       : distrito,
                 referencia : referencia
		      });

		      Siged.Forms.selectAddOption("msClienteCustom", dijit.byId("idClienteCustom").attr("value"), clienteSeleccionado);
		   }/* else {
		      var i = Siged.Array.indexOf(lstClienteCustom, function(item) {
		         return item.identificacion == dijit.byId("idClienteCustom").attr("value");
		      });

		      if (i >= 0) {
		         lstClienteCustom[i].razonsocial = dijit.byId("razonSocialClienteCustom").attr("value");
		         lstClienteCustom[i].direccion = dijit.byId("direccionClienteCustom").attr("value");
		      }

		      Siged.Forms.selectUpdateOption("msClienteCustom", null, null);
		   }*/

/*		   dojo.forEach(lstClienteCustom, function(item) {
		      console.debug("(clienteCustomAdd) identificacion [%s] razonsocial [%s] direccion [%s]", item.identificacion, item.razonsocial, item.direccion);
		   });*/
	   
		   Siged.Forms.comboboxCleanDisplayedValue("objCliente.idCliente");
		   dijit.byId("dlgClienteCustom").hide();
   } else {
	   alert("Debe registrar todos los datos del cliente");  
   }
};

/*var clienteCustomEdit = function() {
   dijit.byId("msClienteCustom").getSelected().forEach(function(option) {
      var i = Siged.Array.indexOf(lstClienteCustom, function(item) {
         return item.identificacion == option.value;
      });

      if (i >= 0) {
         dijit.byId("idClienteCustom").attr("value", lstClienteCustom[i].identificacion);
         dijit.byId("razonSocialClienteCustom").attr("value", lstClienteCustom[i].razonsocial);
         dijit.byId("direccionClienteCustom").attr("value", lstClienteCustom[i].direccion);
         dijit.byId("dlgClienteCustom").show();
      }
   });
};*/


var clienteCustomRemove = function() {
   dijit.byId("msClienteCustom").getSelected().forEach(function(option) {
      var i = Siged.Array.indexOf(lstClienteCustom, function(item) {
         return item.identificacion == option.value;
      });
      if (i >= 0) {
          //console.debug("(clienteCustomRemove) Eliminando indice [%d]", i);
          lstClienteCustom.splice(i, 1);
          //dojo.forEach(lstClienteCustom, function(item) {
          //   console.debug("(clienteCustomRemove) identificacion [%s] razonsocial [%s] direccion [%s]", item.identificacion, item.razonsocial, item.direccion);
          //});
          dojo.byId("msClienteCustom").removeChild(option);
       } 
   });
};

var showDlgClienteCustom = function() {

	var nroIdentificacion = dijit.byId("objCliente.idCliente").attr("value");
   if (Siged.String.isEmpty(nroIdentificacion)) {
      return;
   }
   
   var strCliente = dijit.byId("objCliente.idCliente").store;
   serviceMsg.obtenerClientexTI(null,nroIdentificacion).addCallback(function(ClienteBeanJson) {
	     var tipoIdentificacion = ClienteBeanJson.tipoidentificacion;
         var razonSocial = "";

         if (tipoIdentificacion.toLowerCase() == TIPO_IDENTIFICACION_RUC.toLowerCase()) {
            razonSocial = ClienteBeanJson.razonsocial;
         } else {
            razonSocial = ClienteBeanJson.nombres + " " + ClienteBeanJson.apellidopaterno + " " + ClienteBeanJson.apellidomaterno;
         }
          dijit.byId("idClienteCustom").attr("value", ClienteBeanJson.idcliente);
          dijit.byId("razonSocialClienteCustom").attr("value", razonSocial);
          dijit.byId("direccionClienteCustom").attr("value",  ClienteBeanJson.direccionp);
          if (ClienteBeanJson.iddepartamento != undefined) {
             g_ProvinciaValue = ClienteBeanJson.idprovincia;
             g_DistritoValue = ClienteBeanJson.iddistrito;
             dijit.byId("ubigeo.departamento").attr("value", ClienteBeanJson.iddepartamento);
          } else {
             g_ProvinciaValue = "";
             g_DistritoValue = "";
             dijit.byId("ubigeo.departamento").attr("value", "");
          }	   
   });
   dijit.byId("dlgClienteCustom").show();
};

var saveMensajeria = function() {
   var frmMensajeria = dijit.byId("frmEnvioMensajeria").attr("value");

   var datitos = new Array();
   var error = false;
	var documentos = 0;
	 dojo.forEach(dijit.byId("rolDerecha").getOptions(), function(item){		 
		 if(item.selected){
			 dojo.byId("idDocPrincipalExpediente").value = item.value;		
			 documentos++;
		 }else{			
			 datitos.push(item.value);
			 documentos++;
		 }				 
	 });	
	 if (!frmEnvioMensajeria.validate() ) {
		 dojo.byId("showErrorMensajeria").innerHTML = "Data inv&aacute;lida... Completar el campo Tipo Env&iacute;o";
		 return;
	 }
	 if (documentos==0) {
		 dojo.byId("showErrorMensajeria").innerHTML = "Data inv&aacute;lida... Seleccionar documentos";
		 return;
	 }
	 if (lstClienteCustom==null || lstClienteCustom=="") {
		 dojo.byId("showErrorMensajeria").innerHTML = "Data inv&aacute;lida... Seleccionar clientes";
		 return;
	 }	 
	 
     dojo.forEach(lstClienteCustom, function(item) {
    		if(	 item.direccion=="" || item.departamento=="" || item.provincia=="" || item.distrito=="" ||
    		item.direccion==undefined || item.departamento==undefined || item.provincia==undefined || item.distrito==undefined){
    			dojo.byId("showErrorMensajeria").innerHTML = "Data inv&aacute;lida... La informaci&oacute;n de los clientes est&aacute; incompleta";
    			error = true;		    		 
    	 }	 	
      }); 
	 
	 if(error){
		 return;
	 }
	 
	 dojo.byId("idsDocumentoPorExSeleccionados").value = datitos;
	 
	   dojo.byId("showErrorMensajeria").innerHTML = "&nbsp;";
	   Siged.Forms.selectSelectAll("msDocumentosRight");
	   //console.debug("Intentando enviar los sgtes datos:\n", dojo.toJson(frmMensajeria));
	   //console.debug("lstClienteCustom [%s]", dojo.toJson(lstClienteCustom));   
	   //console.debug("frmMensajeria.tipoEnvio [%s] frmMensajeria.idsDocumentoPorExSeleccionados [%d] frmMensajeria.idDocPrincipalExpediente [%d]",frmMensajeria.tipoEnvio, dojo.byId("idsDocumentoPorExSeleccionados").value,dojo.byId("idDocPrincipalExpediente").value);	   
	     
	   if (confirm("Desea enviar a Mensajeria?")) {
		   serviceMsg.saveMensajeria(
				   frmMensajeria.tipoEnvio, 
				   lstClienteCustom, 
				   dojo.byId("idsDocumentoPorExSeleccionados").value, 
				   dojo.byId("idDocPrincipalExpediente").value )
				   .addCallback(function(result) {
                                   window.close();
	      });
	   }
};

var moveToRight = function(btn){  
	var jsRolDerecha=dijit.byId("rolDerecha");	
	var elementosIniciales = jsRolDerecha.getOptions().length;	
	var jsRolIzquierda=document.getElementById("rolIzquierda");	
	var len1 = jsRolIzquierda.options.length;
	for (i=len1-1; i>=0 ;i-- )
	 {
	  var opcion = jsRolIzquierda.options[i];
	  if ( jsRolIzquierda!=undefined && opcion!=undefined && opcion.selected==true)
	  {
	    var opt=jsRolIzquierda.options[i];
	    //console.debug("idDocumento agregado [%s] ", opt.value);
			jsRolDerecha.addOption({
				disabled:false,
				label:opt.text,
				selected:false,
				value:opt.value
				});			  
			jsRolIzquierda.options[i] = null; 
		agregarCliente(opt.value);
	  }
   }
	/*var elementosFinales = jsRolDerecha.getOptions().length;
	if(elementosIniciales==0 && elementosFinales>0){
		actualizarCliente();
	}*/
};



var moveToLeft = function(btn){
	
	var jsRolDerecha=dijit.byId("rolDerecha");
	var elements = jsRolDerecha.getOptions();
	var optn = document.createElement("OPTION");	
	
	for (i=0;i<elements.length ;i++ )
	{
		 if (elements[i].selected)
		 {
		 	optn.value=elements[i].value;
			optn.text=elements[i].label;
			document.getElementById("rolIzquierda").options.add(optn);    
		    jsRolDerecha.removeOption(elements[i]);
		    break;
		 }
	}
/*	var data;
	serviceMsg.findClientesbyIdDocumento(parseInt(optn.value)).addCallback(function(json) {
		data = json;
		console.debug("tamanho "+json.items.length);
		var tamanho=json.items.length;
		
		for (j=0;j<= tamanho; j++){
			console.debug("tamanho "+tamanho);
			console.debug("j "+j);
	
			var idClienteRemover = json.items[j].identificacion;
			console.debug("contiene? "+Siged.Forms.selectHasOption("msClienteCustom", idClienteRemover));
			if (Siged.Forms.selectHasOption("msClienteCustom", idClienteRemover)) {
			    var k = Siged.Array.indexOf(lstClienteCustom, function(item1) {
			        return item1.identificacion == idClienteRemover;
			     }); 
			    console.debug("k "+k);     
			          
			    var option = dojo.doc.createElement("option");
			    option.value = idClienteRemover;
			    
			   // if (k >= 0) {
			        console.debug("(clienteCustomRemove) Eliminando indice [%d]", k);
			        lstClienteCustom.splice(k, 1);
			        dojo.byId("msClienteCustom").removeChild(option);
			        console.debug("despues de eliminar ");
			    // } 			    
			}
			
		}
		console.debug("tamanho "+json.items.length);
	});*/
};

var actualizarCliente = function(){
	var jsRolDerecha=dijit.byId("rolDerecha");
	var elements = jsRolDerecha.getOptions();
	//var optn = document.createElement("OPTION");
	//console.debug("opciones derecha [%s]"+elements.length);
	for (i=0;i<elements.length ;i++ )
	{
	 if (elements[i].selected)
	 {
		 //console.debug("iddocumento "+elements[i].value);
		 serviceMsg.findClientesbyIdDocumento(parseInt(elements[i].value)).addCallback(function(json) {

				dojo.forEach(json.items, function(item) {
					
					if (!Siged.Forms.selectHasOption("msClienteCustom", item.identificacion)) {
				      lstClienteCustom.push({
					         identificacion : item.identificacion,
					         razonsocial    : item.razonsocial,
					         direccion      : item.direccion,
					         departamento   : item.departamento,
					         provincia      : item.provincia,
					         distrito       : item.distrito
					      });	
				      Siged.Forms.selectAddOption("msClienteCustom", item.identificacion, item.tipo+" - "+item.identificacion+" - "+item.razonsocial);
					}
			});
		});
	    break;
	 }
	}
};

var agregarCliente = function (idDocumento){
	 serviceMsg.findClientesbyIdDocumento(parseInt(idDocumento)).addCallback(function(json) {

			dojo.forEach(json.items, function(item) {
				
				if (!Siged.Forms.selectHasOption("msClienteCustom", item.identificacion)) {
			      lstClienteCustom.push({
				         identificacion : item.identificacion,
				         razonsocial    : item.razonsocial,
				         direccion      : item.direccion,
				         departamento   : item.departamento,
				         provincia      : item.provincia,
				         distrito       : item.distrito
				      });	
			      Siged.Forms.selectAddOption("msClienteCustom", item.identificacion, item.tipo+" - "+item.identificacion+" - "+item.razonsocial);
				}
		});
	});	
	
};
dojo.addOnLoad(function() {
   Siged.Forms.radiobuttonResetByName("sTipoIdentificacion", CLIENTE_TIPOIDENTIFICACION);

   /*Siged.Forms.combobox("fsCliente", {
      id              : "objCliente.idCliente",
      jsId            : "objCliente.idCliente",
      storeUrl        : "obtenerClientePorTipoIdentificacion.action?sTipoIdentificacion=" + CLIENTE_TIPOIDENTIFICACION,
      searchAttr      : "label",
      searchDelay     : 650,
      queryExpr       : "*${0}*",
      autoComplete    : false,
      hasDownArrow    : true,
      highlightMatch  : "all",
      promptMessage   : "Seleccione un cliente",
      required        : false,
      style           : "width:400px;"
   });*/

   Siged.Forms.combobox("fsDepartamento", {
      id             : "ubigeo.departamento",
      jsId           : "ubigeo.departamento",
      storeUrl       : "listaDepartamentos.action",
      searchAttr     : "label",
      searchDelay    : 650,
      queryExpr      : "*${0}*",
      autoComplete   : false,
      hasDownArrow   : true,
      highlightMatch : "all",
      promptMessage  : "Seleccione un departamento",
      required       : false,      
      onChange       : function() {
         Siged.Forms.comboboxChangeStore("ubigeo.provincia", "listaProvincias.action?idDepartamento=" + this.value, g_ProvinciaValue);
      }
   });

   Siged.Forms.combobox("fsProvincia", {
      id             : "ubigeo.provincia",
      jsId           : "ubigeo.provincia",
      searchAttr     : "label",
      searchDelay    : 650,
      queryExpr      : "*${0}*",
      autoComplete   : false,
      hasDownArrow   : true,
      highlightMatch : "all",
      promptMessage  : "Seleccione una provincia",
      required       : false,
      onChange       : function() {
         Siged.Forms.comboboxChangeStore("ubigeo.distrito", "listaDistritos.action?idProvincia=" + this.value, g_DistritoValue);
      }
   });

   Siged.Forms.combobox("fsDistrito", {
      id             : "ubigeo.distrito",
      jsId           : "ubigeo.distrito",
      searchAttr     : "label",
      searchDelay    : 650,
      queryExpr      : "*${0}*",
      autoComplete   : false,
      hasDownArrow   : true,
      highlightMatch : "all",
      promptMessage  : "Seleccione un distrito",
      required       : false
   });

   Siged.Forms.combobox("fsTipoenvio", {
	      id             : "tipoEnvio",
	      jsId           : "tipoEnvio",
	      name           : "tipoEnvio",
	      searchAttr     : "label",
	      searchDelay    : 650,
	      queryExpr      : "*${0}*",
	      autoComplete   : false,
	      hasDownArrow   : true,
	      highlightMatch : "all",
	      required       : true,
	      style          : "width:250px;",
	      invalidMessage : "Seleccione un tipo de envio",
	      //onChange       : fillDataProceso,
	      /*onFocus        : function() {
	         fsProcesoFocused = 1;
	      },*/
	      storeUrl       : "autocompletarTipoEnvio.action"
	   });   
   
   Siged.Forms.combobox("fsNroIdentificacion",{
       id              : "objCliente.idCliente",
       jsId            : "objCliente.idCliente",
       name            : "objCliente.idCliente",
       //storeUrl		: "obtenerClientePorTipoIdentificacion.action",
       searchAttr      : "label",
       labelAttr		: "label",
       queryExpr       : "*${0}*",
       autoComplete    : false,
       hasDownArrow    : false,
       highlightMatch  : "all",
       invalidMessage  : "Seleccione un Nro de Identificacion",
       //onChange        : agregarDestinatarioExterno,
       required        : false,
       style           : "width:300px"
   });
   serviceMsg.getDocumentos(parseInt(IDEXPEDIENTE)).addCallback(function(objJSON) {
      Siged.Forms.selectCleanIt("msDocumentosLeft");

      dojo.forEach(objJSON.items, function(item) {
         if (!Siged.Forms.selectHasOption("msDocumentosLeft", item.id)) {
            Siged.Forms.selectAddOption("msDocumentosLeft", item.id, item.documento);
         }
      });
   });

   //dijit.byId("objCliente.idCliente").attr("value", CLIENTE_NROIDENTIFICACION);
   
   var storeDestinatarios = new dojo.data.ItemFileReadStore({url: "obtenerDataClienteCxb.action"});
   var componente = dijit.byId("objCliente.idCliente");
   if (!Siged.String.isEmpty(componente))
	   dijit.byId("objCliente.idCliente").store = storeDestinatarios;
});
