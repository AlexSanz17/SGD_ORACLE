var fsProcesoFocused = 0;
var strBusquedaExpedienteAbierto;
var iExpedientesAbiertos = 0;
var sPrv1;
var sDst1;
var sPrv2;
var sDst2;
var bIsPosting = false;
var g_sumilla = "";
var nombreProceso = "";
var expedienteNuevo = true;
var idPropietario = "";

var activarExpedienteHistorico = function() {
   console.debug("(activarExpedienteHistorico) checked [%s]", arguments[0]);

   if (arguments[0]) {
      dijit.byId("btnBusquedaExpediente").domNode.style.display = "none";
      dijit.byId("btnValidarExpedienteHistorico").domNode.style.display = "";
      dijit.byId("sNroExpediente").attr("readOnly", false);
   } else {
      dijit.byId("btnBusquedaExpediente").domNode.style.display = "";
      dijit.byId("btnValidarExpedienteHistorico").domNode.style.display = "none";
      dijit.byId("sNroExpediente").attr("value", "");
      dijit.byId("sNroExpediente").attr("readOnly", true);
      dojo.byId("showValidacionExpedienteHistorico").innerHTML = "&nbsp;";
   }
};

/*var closeIt = function() {
   if (dijit.byId("tabNuevoDocumentoMP")) {
      dijit.byId("tabContainerInbox").removeChild(dijit.byId("tabNuevoDocumentoMP"));
   }

   showGridInbox(7);
}*/

fillDataCliente = function(sNroIdentificacion) {
   console.log("[fillDataCliente] - Cliente selecccionado con Nro de Identificacion [" + sNroIdentificacion + "]");

   if (sNroIdentificacion == undefined || sNroIdentificacion == "") {
      hideTBODY("tbDataRUC");
      hideTBODY("tbDataDNI");
      hideTBODY("tbDataCliente");

      return;
   }
   resetCliente();

   var strCliente = dijit.byId("objDocumento.expediente.cliente.numeroIdentificacion").store;

   strCliente.fetchItemByIdentity({
      identity: sNroIdentificacion,
      onItem: function(item) {
          //------------------------------
          //  Obteniendo data del cliente
          //------------------------------
          service.obtenerClientexTI(null,sNroIdentificacion).addCallback(function(ClienteBeanJson) {
 
         dijit.byId("objDocumento.expediente.cliente.idCliente").setValue(ClienteBeanJson.idcliente);
         dijit.byId("btnRegistrarDocumentoTop").attr("disabled", true);
         dijit.byId("btnRegistrarDocumentoBottom").attr("disabled", true);
         dojo.byId("showExpedienteAbierto").innerHTML = "<img src='images/cargando.gif' alt='' />Buscando expedientes abiertos del cliente...";

         strBusquedaExpedienteAbierto = new dojo.data.ItemFileReadStore({
            url: "buscarExpedienteAbierto.action?iIdCliente=" + ClienteBeanJson.idcliente
         });

         grdBusquedaExpedienteAbierto.setStore(strBusquedaExpedienteAbierto);

         strBusquedaExpedienteAbierto.fetch({
            query: {
               id: "*"
            },
            onComplete: function(items) {
               iExpedientesAbiertos = items.length;
               console.log("Numero de Expedientes abiertos [" + iExpedientesAbiertos + "]");
               dojo.byId("sLengthExpediente").innerHTML = iExpedientesAbiertos;
               dijit.byId("btnRegistrarDocumentoTop").attr("disabled", false);
               dijit.byId("btnRegistrarDocumentoBottom").attr("disabled", false);

               
               if (iExpedientesAbiertos > 0) {
                  dojo.byId("showExpedienteAbierto").innerHTML = "";// "El cliente tiene " + iExpedientesAbiertos + " expediente(s) abierto(s)";
               } else {
                  dojo.byId("showExpedienteAbierto").innerHTML =  "";//"El cliente no tiene expedientes abiertos";
               } 
            }
         });
         
         var tipoIdentificacion=ClienteBeanJson.tipoidentificacion;
         
         dojo.query("input[name='sTipoIdentificacion']").forEach(function(node) {
   	      dijit.byId(node.id).attr("checked", false);
   	      dijit.byId(node.id).attr("readOnly", true);

   	      if (node.value == "RUC" && tipoIdentificacion=="RUC") {
   	         dijit.byId(node.id).attr("checked", true);
   	      }	   
   	      if (node.value == "DNI" && tipoIdentificacion=="DNI") {
    	     dijit.byId(node.id).attr("checked", true);
    	  }	  
   	      if (node.value == "Otro" && tipoIdentificacion=="Otro") {
    	     dijit.byId(node.id).attr("checked", true);
    	  }	     	      
   	   	 });  

        	 
	         if (tipoIdentificacion=="RUC") {
	            hideTBODY("tbDataDNI");
	            dijit.byId("objDocumento.expediente.cliente.razonSocial").attr("value", ClienteBeanJson.razonsocial);
	            dijit.byId("objDocumento.expediente.cliente.representanteLegal").attr("value", ClienteBeanJson.representantelegal);
	            dojo.byId("sCliente").innerHTML = ClienteBeanJson.razonsocial;
	            showTBODY("tbDataRUC");
	         } else if (tipoIdentificacion=="DNI" || tipoIdentificacion=="Otro") {
	            hideTBODY("tbDataRUC");
	            dijit.byId("objDocumento.expediente.cliente.nombres").attr("value", ClienteBeanJson.nombres);
	            dijit.byId("objDocumento.expediente.cliente.apellidoPaterno").attr("value", ClienteBeanJson.apellidopaterno);
	            dijit.byId("objDocumento.expediente.cliente.apellidoMaterno").attr("value", ClienteBeanJson.apellidomaterno);
	            dojo.byId("sCliente").innerHTML = ClienteBeanJson.nombres + " " + ClienteBeanJson.apellidopaterno;
	            showTBODY("tbDataDNI");
	         }
	         dijit.byId("objDocumento.expediente.cliente.direccionPrincipal").attr("value", ClienteBeanJson.direccionp);
	         sPrv1 = ClienteBeanJson.idprovincia;
	         sDst1 = ClienteBeanJson.iddistrito;
	         dijit.byId("objDocumento.expediente.cliente.ubigeoPrincipal.provincia.departamento.iddepartamento").attr("value", ClienteBeanJson.iddepartamento);
	         console.debug("LEVANTANDO DATA sPrv1 "+ sPrv1 + " sDst1 " + sDst1);
	         dijit.byId("objDocumento.expediente.cliente.direccionAlternativa").attr("value", ClienteBeanJson.direcciona);
	         sPrv2 = ClienteBeanJson.idprovinciaa;
	         sDst2 = ClienteBeanJson.iddistritoa;
	         dijit.byId("objDocumento.expediente.cliente.ubigeoAlternativo.provincia.departamento.iddepartamento").attr("value", ClienteBeanJson.iddepartamentoa);
	         dijit.byId("objDocumento.expediente.cliente.telefono").attr("value", ClienteBeanJson.telefono);
	         dijit.byId("objDocumento.expediente.cliente.correo").attr("value", ClienteBeanJson.correo);
	         showTBODY("tbDataCliente");
         });         
      }
   });
};

function resetCliente(){
	dijit.byId("objDocumento.expediente.cliente.idCliente").setValue("");
	dijit.byId("objDocumento.expediente.cliente.razonSocial").attr("value","");
	dijit.byId("objDocumento.expediente.cliente.representanteLegal").attr("value","");
	dojo.byId("sCliente").innerHTML="";
	dijit.byId("objDocumento.expediente.cliente.nombres").attr("value","");
	dijit.byId("objDocumento.expediente.cliente.apellidoPaterno").attr("value","");
	dijit.byId("objDocumento.expediente.cliente.apellidoMaterno").attr("value","");
	dijit.byId("objDocumento.expediente.cliente.direccionPrincipal").attr("value","");
	dijit.byId("objDocumento.expediente.cliente.ubigeoPrincipal.provincia.departamento.iddepartamento").attr("value","");
	dijit.byId("objDocumento.expediente.cliente.ubigeoPrincipal.provincia.idprovincia").attr("value","");
	dijit.byId("objDocumento.expediente.cliente.ubigeoPrincipal.iddistrito").attr("value","");
	dijit.byId("objDocumento.expediente.cliente.direccionAlternativa").attr("value","");
	dijit.byId("objDocumento.expediente.cliente.ubigeoAlternativo.provincia.departamento.iddepartamento").attr("value","");
	dijit.byId("objDocumento.expediente.cliente.ubigeoAlternativo.provincia.idprovincia").attr("value","");
	dijit.byId("objDocumento.expediente.cliente.ubigeoAlternativo.iddistrito").attr("value","");
	dijit.byId("objDocumento.expediente.cliente.telefono").attr("value","");
	dijit.byId("objDocumento.expediente.cliente.correo").attr("value","");
	dojo.byId("showExpedienteAbierto").innerHTML="";
}

fillDataConcesionario = function(sRUC) {
   console.log("[fillDataConcesionario] - RUC selecccionado  [" + sRUC + "]");

   if (sRUC == undefined || sRUC == "") {
      hideTBODY("tbDataConcesionario");

      return;
   }

   showTBODY("tbDataConcesionario");

   var strConcesionario = dijit.byId("objDocumento.expediente.concesionario.ruc").store;

   strConcesionario.fetchItemByIdentity({
      identity: sRUC,
      onItem: function(item) {
         dijit.byId("objDocumento.expediente.concesionario.idConcesionario").setValue(strConcesionario.getValue(item, "idcorrentista"));
         sRazonSocialCo.setValue(strConcesionario.getValue(item, "correntista"));
         sDireccionCo.setValue(strConcesionario.getValue(item, "strDireccion"));
         sCorreoCo.setValue(strConcesionario.getValue(item, "strCorreoConcesionario"));
      }
   });
};

fillDataProceso = function(iIdProceso) {
	   var store = new dojo.data.ItemFileReadStore({url: "autocompletarUsuarioxProcesoConUsuario.action?idProceso=" + iIdProceso+"&multiple=si"});
	   var iStor = 0;
	   var permiteSumilla = "N";

	   console.log("[fillDataProceso] - fsProcesoFocused [" + fsProcesoFocused + "]");
	   console.log("[fillDataProceso] - Proceso selecccionado con ID [" + iIdProceso + "]");
	   dojo.byId("showErrorDocumento").innerHTML = "&nbsp;";

	   if (iIdProceso == undefined || iIdProceso == "") {
	      hideTBODY("tbProceso");
	      hideTBODY("tbDestinatario");
	      hideTBODY("tbDocumento");
	      dojo.byId("trSumilla").style.display = "none";
	      hideTBODY("tbCliente");
	      hideTBODY("tbDataRUC");
	      hideTBODY("tbDataDNI");
	      hideTBODY("tbDataCliente");
	      hideTBODY("tbNuevoCliente");
	      hideTBODY("tbObservacion");
	      hideTBODY("tbConcesionario");
	      hideTBODY("tbDataConcesionario");
	      hideTBODY("tbDataStor");
	      dojo.byId("ambienteProceso").innerHTML = "&nbsp;";

	      return;
	   }

	   if (fsProcesoFocused) {
	      resetfrmNuevoDocumento();
	      showTBODY("tbNuevoCliente");
	      hideTBODY("tbDataRUC");
	      hideTBODY("tbDataDNI");
	      hideTBODY("tbDataCliente");
	      hideTBODY("tbDataConcesionario");
	      hideTBODY("tbDataStor");
	      fsProcesoFocused = 0;
	   }

	   dijit.byId("iddestinatarios").store = store;
	   dijit.byId("idcopias").store = store;
	   var strProceso = dijit.byId("objDocumento.expediente.proceso.idproceso").store;

	   strProceso.fetchItemByIdentity({
	      identity: iIdProceso,
	      onItem: function(item) {
	         nombreProceso = new String(strProceso.getValue(item, "proceso"));
	         console.debug("(fillDataProceso) - nombreProceso [%s]", nombreProceso);

	         if (nombreProceso.match(/^OSINERGMIN/) != null) {
	            hideTBODY("tbProceso");
	            showTBODY("tbDestinatario");

	            if (expedienteNuevo) {
	               dijit.byId("objDocumento.propietario.idusuario").attr("required", true);
	               dijit.byId("objDocumento.propietario.idusuario").attr("readOnly", false);
	            } else {
	               dijit.byId("objDocumento.propietario.idusuario").attr("required", false);
	               dijit.byId("objDocumento.propietario.idusuario").attr("value", idPropietario);
	               dijit.byId("objDocumento.propietario.idusuario").attr("readOnly", true);
	            }
	         } else {
	            hideTBODY("tbDestinatario");
	            showTBODY("tbProceso");
	            dijit.byId("objDocumento.propietario.idusuario").attr("required", false);
	            dijit.byId("objDocumento.propietario.idusuario").attr("value", "");
	            sUnidad.setValue(strProceso.getValue(item, "areadestino"));
	            sResponsable.setValue(strProceso.getValue(item, "destinatario"));
	         }

	         permiteSumilla = strProceso.getValue(item, "permitesumilla");
	         console.debug("(fillDataProceso) Permite sumilla [%s] sumilla [%s]", permiteSumilla, g_sumilla);
	         console.debug("ambiente [%s]", strProceso.getValue(item, "ambiente"));
	         //dojo.byId("ambienteProceso").innerHTML = strProceso.getValue(item, "ambiente")  == "S" ? "* Ambiente Producci&oacute;n" : "* Ambiente Piloto";
	         dojo.byId("ambienteProceso").innerHTML = strProceso.getValue(item, "nombreambiente");
	         
	         if (strProceso.getValue(item, "ambiente")  == "S") {
	            dojo.style("ambienteProceso", "color", "blue");
	         } else {
	            dojo.style("ambienteProceso", "color", "red");
	         }

	         if (strProceso.getValue(item, "tipoproceso") == "stor") {
	            iStor = 1;
	            dijit.byId("objDocumento.tipoDocumento.idtipodocumento").setValue(strProceso.getValue(item, "idtipodocumento"));
	            dijit.byId("objDocumento.tipoDocumento.idtipodocumento").attr("readOnly", true);
	            dijit.byId("dwr_idmotivo").store=new dojo.data.ItemFileReadStore({url: "listaMotivos.action?idProceso="+strProceso.getValue(item,"id")});
	         }
	      }
	   });

	   showTBODY("tbDocumento");
	   showTBODY("tbCliente");
	   showTBODY("tbObservacion");

	   if (iStor) {
	      showTBODY("tbConcesionario");
	      dijit.byId("objDocumento.expediente.concesionario.ruc").attr("required", true);
	      showTBODY("tbDataStor");
	   } else {
	      hideTBODY("tbConcesionario");
	      hideTBODY("tbDataConcesionario");
	      dijit.byId("objDocumento.expediente.concesionario.ruc").attr("required", false);
	      hideTBODY("tbDataStor");
	   }

	   if (permiteSumilla == "S") {
	      dojo.byId("trSumilla").style.display = "";
	      dijit.byId("objDocumento.expediente.sumilla").attr("value", "");
	      dijit.byId("objDocumento.expediente.sumilla").attr("readOnly", false);

	      if (!Siged.String.isEmpty(g_sumilla)) {
	         dijit.byId("objDocumento.expediente.sumilla").attr("value", g_sumilla);
	         dijit.byId("objDocumento.expediente.sumilla").attr("readOnly", true);
	      }
	   } else {
	      dijit.byId("objDocumento.expediente.sumilla").attr("value", "");
	      dijit.byId("objDocumento.expediente.sumilla").attr("readOnly", false);
	      dojo.byId("trSumilla").style.display = "none";
	   }

	   g_sumilla = "";
	};

var prepareToSubmitForm = function() {
   var textoMonto = dijit.byId("sMontoReclamo").getValue();
   var monto = 0;

   dijit.byId("btnRegistrarDocumentoTop").attr("disabled", true);
   dijit.byId("btnRegistrarDocumentoBottom").attr("disabled", true);

   if (textoMonto != null && textoMonto != "") {
      monto = parseFloat(textoMonto);
   }

   if (monto < 10000) {
      var iIdExpediente = dijit.byId("objDocumento.expediente.idexpediente").getValue();
      var sNroExpediente = dijit.byId("sNroExpediente").attr("value");

      var myForm = dijit.byId("frmNuevoDocumento");

      if (!myForm.validate()) {
         dojo.byId("showErrorDocumento").innerHTML = "&nbsp;";
         dijit.byId("btnRegistrarDocumentoTop").attr("disabled", false);
         dijit.byId("btnRegistrarDocumentoBottom").attr("disabled", false);

         return;
      }

      console.log("(prepareToSubmitForm) Expediente asociado con ID [" + iIdExpediente + "] y Nro [" + sNroExpediente + "]");
      console.log("(prepareToSubmitForm) Numero de expedientes abiertos [" + iExpedientesAbiertos + "]");

      if (dijit.byId("chkExpedienteHistorico") != null && dijit.byId("chkExpedienteHistorico").attr("checked")) {
         if (sNroExpediente == undefined || sNroExpediente == "") {
            dojo.byId("showErrorDocumento").innerHTML = "Ingrese un Nro de Expediente para validar...";
            dijit.byId("btnRegistrarDocumentoTop").attr("disabled", false);
            dijit.byId("btnRegistrarDocumentoBottom").attr("disabled", false);

            return;
         }

         service.validarExpedienteHistorico(sNroExpediente).addCallback(function(iResultado) {
            if (iResultado) {
               console.debug("El Nro de Expediente [%s] ya existe en el sistema", sNroExpediente);
               dojo.byId("showErrorDocumento").innerHTML = "El Nro de Expediente ya existe en el sistema. Por favor, ingrese otro...";
               dijit.byId("btnRegistrarDocumentoTop").attr("disabled", false);
               dijit.byId("btnRegistrarDocumentoBottom").attr("disabled", false);

               return;
            } else {
               dojo.byId("showErrorDocumento").innerHTML = "&nbsp;";
               dijit.byId("objDocumento.expediente.nroexpediente").attr("value", sNroExpediente);
               console.debug("El Nro de Expediente [%s] NO existe en el sistema", sNroExpediente);
               console.log("(prepareToSubmitForm) Intentando enviar los sgtes datos:\n", dojo.toJson(myForm.attr("value"), true));
               submitForm();
            }
         })
      } else {
         dojo.byId("showErrorDocumento").innerHTML = "&nbsp;";
         console.log("(prepareToSubmitForm) Intentando enviar los sgtes datos:\n", dojo.toJson(myForm.attr("value"), true));

         if (iExpedientesAbiertos > 0 && (iIdExpediente == undefined || iIdExpediente == '')) {
            dlgBusquedaExpedienteAbierto.show();
         } else {
            submitForm();
         }
      }
   } else {
      //FIXME mostrar algun error de dojo
      alert("El monto debe ser menor a S/. 10000");
      dlgBusquedaExpedienteAbierto.hide();
      dijit.byId("btnWithoutExpedienteAbierto").attr("disabled", false);
      dijit.byId("btnRegresarAlForm").attr("disabled", false);
      dijit.byId('dlgProgresBar').hide();
      dijit.byId("btnRegistrarDocumentoTop").attr("disabled", false);
      dijit.byId("btnRegistrarDocumentoBottom").attr("disabled", false);
   }
};

var returnToForm = function() {
   dijit.byId("dlgBusquedaExpedienteAbierto").hide();
   dijit.byId("btnRegistrarDocumentoTop").attr("disabled", false);
   dijit.byId("btnRegistrarDocumentoBottom").attr("disabled", false);
}

var resetfrmNuevoDocumento = function() {
   if (dijit.byId("chkExpedienteHistorico") != null && !dijit.byId("chkExpedienteHistorico").attr("checked")) {
      dijit.byId("sNroExpediente").attr("value", "");
   }

   dijit.byId("objDocumento.expediente.idexpediente").setValue("");
   sUnidad.setValue("");
   sResponsable.setValue("");

   dijit.byId("objDocumento.tipoDocumento.idtipodocumento").setValue("");
   dijit.byId("objDocumento.tipoDocumento.idtipodocumento").setDisplayedValue("");
   dijit.byId("objDocumento.tipoDocumento.idtipodocumento").attr("readOnly", false);

   dojo.query("input[name='sTipoIdentificacion']").forEach(function(node) {
      dijit.byId(node.id).attr("checked", false);
      dijit.byId(node.id).attr("readOnly", false);

  /*    if (node.value == "RUC") {
         dijit.byId(node.id).attr("checked", true);
      }*/

      console.log("[resetfrmNuevoDocumento] - Nodo [" + node.name + "] ID [" + node.id + "] VALUE [" + node.value + "] CHECKED [" + node.checked + "]");
   });

   dijit.byId("objDocumento.expediente.cliente.idCliente").setValue("");
   resetCliente();
   updateStore("", "cliente");
   dijit.byId("objDocumento.expediente.cliente.numeroIdentificacion").setValue("");
   dijit.byId("objDocumento.expediente.cliente.numeroIdentificacion").attr("readOnly", false);

   dojo.byId("showExpedienteAbierto").innerHTML = "&nbsp;";

   dijit.byId("objDocumento.observacion").setValue("");

   dijit.byId("objDocumento.expediente.concesionario.idConcesionario").setValue("");
   dijit.byId("objDocumento.expediente.concesionario.ruc").setValue("");
   dijit.byId("objDocumento.expediente.concesionario.ruc").setDisplayedValue("");
   dijit.byId("objDocumento.expediente.concesionario.ruc").attr("readOnly", false);
};

var submitForm = function() {
   if (!bIsPosting) {
      bIsPosting = true;
      dojo.xhrPost({
         form: dojo.byId("frmNuevoDocumento"),
         mimetype: "text/html",
         load: function(data) {
            dijit.byId('dlgProgresBar').hide();
            dijit.byId("tabNuevoDocumentoMP").attr("content", data);
         }
      });
   }
};

var submitFormWithExpedienteAbierto = function(e) {
   if (e.rowIndex == undefined) {
      console.log("(submitFormWithExpedienteAbierto) UPSSS no se selecciono ningun Expediente abierto");

      return;
   }

   console.log("(submitFormWithExpedienteAbierto) Fila seleccionada [" + e.rowIndex + "]");

   var idexpediente = grdBusquedaExpedienteAbierto.getItem(e.rowIndex).id;

   console.log("(submitFormWithExpedienteAbierto) Expediente abierto seleccionado con ID [" + idexpediente + "]");
   dijit.byId("objDocumento.expediente.idexpediente").setValue(idexpediente);
   dlgBusquedaExpedienteAbierto.hide();
   dijit.byId('dlgProgresBar').show() ;
   //frmNuevoDocumento.submit();
   submitForm();
};

var submitFormWithoutExpedienteAbierto = function() {
   console.log("(submitFormWithoutExpedienteAbierto) Enviando data sin anexar expediente abierto");
   dijit.byId("btnWithoutExpedienteAbierto").attr("disabled", true);
   dijit.byId("btnRegresarAlForm").attr("disabled", true);
   dijit.byId('dlgProgresBar').show() ;
   //frmNuevoDocumento.submit();
   submitForm();
};

var validarExpedienteHistorico = function() {
   var sNroExpediente = dijit.byId("sNroExpediente").attr("value");

   if (sNroExpediente == undefined || sNroExpediente == "") {
      dojo.byId("showValidacionExpedienteHistorico").innerHTML = "Ingrese un Nro de Expediente para validar...";

      return;
   }

   console.debug("(validarExpedienteHistorico) Validando si existe el numero de Expediente [%s]", sNroExpediente);
   dojo.byId("showValidacionExpedienteHistorico").innerHTML = "<img src='images/cargando.gif' alt='' />Validando si el Nro de Expediente ya existe...";
   dojo.byId("showErrorDocumento").innerHTML = "&nbsp;";

   service.validarExpedienteHistorico(sNroExpediente).addCallback(function(iResultado){
      if (iResultado) {
         dojo.byId("showValidacionExpedienteHistorico").innerHTML = "<img src='images/sigedIconos/Delete16.gif' alt='' />El Nro de Expediente ya existe en el sistema. Por favor, ingrese otro...";
      } else {
         dojo.byId("showValidacionExpedienteHistorico").innerHTML = "<img src='images/sigedIconos/New16.gif' alt='' />El Nro de Expediente no existe en el sistema...";
      }
   })
};

function onFocusProceso() {
    fsProcesoFocused = 1;
}

function onChangeDepartamento(value) {
    if (value != "") {
        updateStore(value, "provincia1");
        console.debug("EN FSDEPARTAMENTO con valor seteado a [" + value + "] sPrv1 "+ sPrv1 + " sDst1 " + sDst1);
        dijit.byId("objDocumento.expediente.cliente.ubigeoPrincipal.provincia.idprovincia").attr("value", sPrv1);
    }
}

//Dado que el dojo utiliza funcion(value) como parametro de funcion, nos vemos forzados a repetir esta funcion 2 veces
function onChangeDepartamento2(value) {
    if (value != "") {
        updateStore(value, "provincia2");
        console.debug("EN FSDEPARTAMENTO con valor seteado a [" + value + "] sPrv2 "+ sPrv2 + " sDst2 " + sDst2);
        dijit.byId("objDocumento.expediente.cliente.ubigeoAlternativo.provincia.idprovincia").attr("value", sPrv2);
    }
}

function onChangeProvincia(value) {
    if (value != "") {
        updateStore(value, "distrito1");
        console.debug("EN FSPROVINCIA con valor seteado a [" + value + "] sPrv1 "+ sPrv1 + " sDst1 " + sDst1);
        dijit.byId("objDocumento.expediente.cliente.ubigeoPrincipal.iddistrito").attr("value", sDst1);
    }
}

function onChangeProvincia2(value) {
    if (value != "") {
        updateStore(value, "distrito2");
        console.debug("EN FSPROVINCIA con valor seteado a [" + value + "] sPrv2 "+ sPrv2 + " sDst2 " + sDst2);
        dijit.byId("objDocumento.expediente.cliente.ubigeoAlternativo.iddistrito").attr("value", sDst2);
    }
}

dojo.addOnLoad(function() {
    Siged.Forms.combobox("fsProceso",{
        id              : 'objDocumento.expediente.proceso.idproceso',
        jsId            : 'objDocumento.expediente.proceso.idproceso',
        name            : 'objDocumento.expediente.proceso.idproceso',
        storeUrl        : 'autocompletarProceso.action',
        searchAttr      : 'label',
        searchDelay     : 650,
        queryExpr       : '*${0}*',
        autoComplete    : false,
        hasDownArrow    : false,
        highlightMatch  : "all",
        invalidMessage  : "Seleccione un proceso",
        onChange        : fillDataProceso,
        onFocus         : onFocusProceso,
        required        : true,
        style           : "width:300px",
        value           : ""
    });

    Siged.Forms.combobox("fsDestinatario",{
        id              : 'objDocumento.propietario.idusuario',
        jsId            : 'objDocumento.propietario.idusuario',
        name            : 'objDocumento.propietario.idusuario',
        storeUrl        : 'autocompletarUsuarioFinal.action',
        searchAttr      : 'label',
        searchDelay     : 650,
        queryExpr       : '*${0}*',
        autoComplete    : false,
        hasDownArrow    : true,
        highlightMatch  : "all",
        invalidMessage  : "Seleccione un destinatario",
        required        : false,
        style           : "width:300px",
        value           : ""
    });

    Siged.Forms.combobox("fsTipoDocumento",{
        id              : "objDocumento.tipoDocumento.idtipodocumento",
        jsId            : "objDocumento.tipoDocumento.idtipodocumento",
        name            : "objDocumento.tipoDocumento.idtipodocumento",
        storeUrl        : "autocompletarAllTipoDocumento.action",
        searchAttr      : "label",
        searchDelay     : 650,
        queryExpr       : "*${0}*",
        autoComplete    : false,
        hasDownArrow    : true,
        highlightMatch  : "all",
        invalidMessage  : "Seleccione un tipo de Documento",
        required        : true
    });

    Siged.Forms.combobox("fsNroIdentificacion",{
        id              : "objDocumento.expediente.cliente.numeroIdentificacion",
        jsId            : "objDocumento.expediente.cliente.numeroIdentificacion",
        name            : "objDocumento.expediente.cliente.numeroIdentificacion",
        searchAttr      : "label",
        queryExpr       : "*${0}*",
        autoComplete    : false,
        searchDelay     : 650,
        hasDownArrow    : false,
        highlightMatch  : "all",
        invalidMessage  : "Seleccione un Nro de Identificacion",
        onChange        : fillDataCliente,
        required        : true,
        style           : "width:300px"
    });

    Siged.Forms.combobox("fsDepartamento1", {
        id              : "objDocumento.expediente.cliente.ubigeoPrincipal.provincia.departamento.iddepartamento",
        jsId            : "objDocumento.expediente.cliente.ubigeoPrincipal.provincia.departamento.iddepartamento",
        name            : "objDocumento.expediente.cliente.ubigeoPrincipal.provincia.departamento.iddepartamento",
        storeUrl        : "listaDepartamentos.action",
        searchAttr      : "label",
        queryExpr       : "*${0}*",
        autoComplete    : false,
        searchDelay     : 650,
        hasDownArrow    : true,
        highlightMatch  : "all",
        invalidMessage  : "Seleccione un departamento",
        onChange        : onChangeDepartamento,
        required        : false
    });

    Siged.Forms.combobox("fsProvincia1",{
        id              : "objDocumento.expediente.cliente.ubigeoPrincipal.provincia.idprovincia",
        jsId            : "objDocumento.expediente.cliente.ubigeoPrincipal.provincia.idprovincia",
        name            : "objDocumento.expediente.cliente.ubigeoPrincipal.provincia.idprovincia",
        searchAttr      : "label",
        queryExpr       : "*${0}*",
        autoComplete    : false,
        hasDownArrow    : true,
        searchDelay     : 650,
        highlightMatch  : "all",
        invalidMessage  : "Seleccione una provincia",
        onChange        : onChangeProvincia,
        required        : false
    });

    Siged.Forms.combobox("fsDistrito1",{
        id              : "objDocumento.expediente.cliente.ubigeoPrincipal.iddistrito",
        jsId            : "objDocumento.expediente.cliente.ubigeoPrincipal.iddistrito",
        name            : "objDocumento.expediente.cliente.ubigeoPrincipal.iddistrito",
        searchAttr      : "label",
        queryExpr       : "*${0}*",
        autoComplete    : false,
        searchDelay     : 650,
        hasDownArrow    : true,
        highlightMatch  : "all",
        invalidMessage  : "Seleccione un distrito",
        required        : false
    });

    Siged.Forms.combobox("fsDepartamento2",{
        id              : "objDocumento.expediente.cliente.ubigeoAlternativo.provincia.departamento.iddepartamento",
        jsId            : "objDocumento.expediente.cliente.ubigeoAlternativo.provincia.departamento.iddepartamento",
        name            : "objDocumento.expediente.cliente.ubigeoAlternativo.provincia.departamento.iddepartamento",
        storeUrl        : "listaDepartamentos.action",
        searchAttr      : "label",
        queryExpr       : "*${0}*",
        searchDelay     : 650,
        autoComplete    : false,
        hasDownArrow    : true,
        highlightMatch  : "all",
        invalidMessage  : "Seleccione un departamento",
        onChange        : onChangeDepartamento2,
        required        : false
    });

    Siged.Forms.combobox("fsProvincia2",{
        id              : "objDocumento.expediente.cliente.ubigeoAlternativo.provincia.idprovincia",
        jsId            : "objDocumento.expediente.cliente.ubigeoAlternativo.provincia.idprovincia",
        name            : "objDocumento.expediente.cliente.ubigeoAlternativo.provincia.idprovincia",
        searchAttr      : "label",
        queryExpr       : "*${0}*",
        searchDelay     : 650,
        autoComplete    : false,
        hasDownArrow    : true,
        highlightMatch  : "all",
        invalidMessage  : "Seleccione una provincia",
        onChange        : onChangeProvincia2,
        required        : false
    });

    Siged.Forms.combobox("fsDistrito2", {
        id              : "objDocumento.expediente.cliente.ubigeoAlternativo.iddistrito",
        jsId            : "objDocumento.expediente.cliente.ubigeoAlternativo.iddistrito",
        name            : "objDocumento.expediente.cliente.ubigeoAlternativo.iddistrito",
        searchAttr      : "label",
        queryExpr       : "*${0}*",
        searchDelay     : 650,
        autoComplete    : false,
        hasDownArrow    : true,
        highlightMatch  : "all",
        invalidMessage  : "Seleccione un distrito",
        required        : false
    });

    Siged.Forms.combobox("fsRUC", {
        id              : "objDocumento.expediente.concesionario.ruc",
        jsId            : "objDocumento.expediente.concesionario.ruc",
        name            : "objDocumento.expediente.concesionario.ruc",
        storeUrl        : "retrieveDataConcesionario.action",
        searchAttr      : "label",
        queryExpr       : "*${0}*",
        autoComplete    : false,
        hasDownArrow    : true,
        highlightMatch  : "all",
        invalidMessage  : "Seleccione un RUC",
        onChange        : fillDataConcesionario,
        required        : false,
        style           : "width:300px"
    });
   
//dojo.connect(dijit.byId("btnBusquedaExpediente"), "onClick", dijit.byId("dlgBusquedaExpediente"), showBusquedaExpediente);
//dojo.connect(dijit.byId("grdBusquedaExpediente"), "onRowClick", fillDataExpediente);
//dojo.connect(dijit.byId("grdBusquedaExpedienteAbierto"), "onRowClick", submitFormWithExpedienteAbierto);
//dojo.connect(dijit.byId("btnNuevoCliente"), "onClick", dijit.byId("dlgNuevoCliente"), showMe);
//dijit.byId("sFechaDocumento").attr("value", new Date());

/*dojo.declare("OracleDateTextBox", dijit.form.DateTextBox, {
      oracleFormat: {
         selector: 'date',
         datePattern: 'MM/dd/yyyy',
         locale: 'en-us'
      },
      value: "", // prevent parser from trying to convert to Date object
      postMixInProperties: function() { // change value string to Date object
         this.inherited(arguments);
         // convert value to Date object
         console.log("postMixInProperties ["+this.value+"]");
         this.value = dojo.date.locale.parse(this.value, this.oracleFormat);
      },
      // To write back to the server in Oracle format, override the serialize method:
      serialize: function(dateObject, options) {
         console.log("serialize ["+dateObject+"]");
         return dojo.date.locale.format(dateObject, this.oracleFormat).toUpperCase();
      }
   });

   new OracleDateTextBox({
      id: "fecha",
      jsId: "fecha",
      name: "fecha"
   }, "oracle");

   dijit.byId("fecha").setValue(new Date());*/



});

