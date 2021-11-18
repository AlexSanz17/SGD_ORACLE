
var fsProcesoFocused = 0;
hideTBODY = function(tbToHide) {
	console.log("[hideTBODY] - tbody a ocultar [" + tbToHide + "]");

	if (dojo.byId(tbToHide)) {
		dojo.byId(tbToHide).style.display = "none";
	} else {
		console.log("[hideTBODY] - [" + tbToHide + "] NO EXISTE");
	}
};

showTBODY = function(tbToShow) {
	console.log("[showTBODY] - tbody a mostrar [" + tbToShow + "]");

	if (dojo.byId(tbToShow)) {
		// dojo.byId(tbToShow).style.display = "table-row-group";
		dojo.byId(tbToShow).style.display = "";
	} else {
		console.log("[showTBODY] - [" + tbToShow + "] NO EXISTE");
	}
};

var fillDataProceso = function(iIdProceso) {

    changeStore("objDD.iIdDestinatario", "autocompletarUsuarioxProcesoConUsuario.action?idProceso=" + iIdProceso);


};

var changeStore = function(objFS, sURL) {
	   console.log("[changeStore] - Cambiando store del objeto [" + objFS + "]");
	   console.log("[changeStore] - Usando action [" + sURL + "]");

	   dijit.byId(objFS).setDisplayedValue("Cargando Data...");
	   dijit.byId(objFS).setDisabled(true);
	   dijit.byId(objFS).store = new dojo.data.ItemFileReadStore({
	      url: sURL
	   });
	   dijit.byId(objFS).setValue("");
	   dijit.byId(objFS).setDisplayedValue("");
	   dijit.byId(objFS).setDisabled(false);
	};

var fillDataCliente = function(sNroIdentificacion) {
	   console.log("[fillDataCliente] - Cliente selecccionado con Nro de Identificacion [" + sNroIdentificacion + "]");
	   if (sNroIdentificacion == undefined || sNroIdentificacion == "") {
	      hideTBODY("tbDataRUC");
	      hideTBODY("tbDataDNI");
	      hideTBODY("tbDataCliente");
	      return;
	   }
	   var strCliente = dijit.byId("objDD.strNroIdentificacion").store;
	   strCliente.fetchItemByIdentity({
	      identity: sNroIdentificacion,
	      onItem: function(item) {

	         dijit.byId("idcliente").setValue(strCliente.getValue(item, "idcliente"));

	         if (strCliente.getValue(item, "tipoidentificacion") == "RUC") {
	            hideTBODY("tbDataDNI");
	            strRazonSocial.setValue(strCliente.getValue(item, "razonsocial"));
	            strRepresentanteLegal.setValue(strCliente.getValue(item, "representantelegal"));
	            //dojo.byId("sCliente").innerHTML = strCliente.getValue(item, "razonsocial");
	            showTBODY("tbDataRUC");
	         } else if (strCliente.getValue(item, "tipoidentificacion") == "DNI"||strCliente.getValue(item, "tipoidentificacion") == "Otro") {
	            hideTBODY("tbDataRUC");
	            sNombres.setValue(strCliente.getValue(item, "nombres"));
	            sApellidoPaterno.setValue(strCliente.getValue(item, "apellidopaterno"));
	            sApellidoMaterno.setValue(strCliente.getValue(item, "apellidomaterno"));
	            //dojo.byId("sCliente").innerHTML = strCliente.getValue(item, "nombres") + " " + strCliente.getValue(item, "apellidopaterno");
	            showTBODY("tbDataDNI");
	         }
	         sDireccion1.setValue(strCliente.getValue(item, "direccionp"));
	         sDepartamento1.setValue(strCliente.getValue(item, "departamento"));
	         sProvincia1.setValue(strCliente.getValue(item, "provincia"));
	         sDistrito1.setValue(strCliente.getValue(item, "distrito"));
	         sDireccion2.setValue(strCliente.getValue(item, "direcciona"));
	         sDepartamento2.setValue(strCliente.getValue(item, "departamentoa"));
	         sProvincia2.setValue(strCliente.getValue(item, "provinciaa"));
	         sDistrito2.setValue(strCliente.getValue(item, "distritoa"));
	         sTelefono.setValue(strCliente.getValue(item, "telefono"));
	         sDireccion1.setValue(strCliente.getValue(item, "direccionp"));
	         sCorreoCliente.setValue(strCliente.getValue(item, "correo"));
	         showTBODY("tbDataCliente");
	      }
	   });
	};


var updateStore = function(sParametro, sObjeto) {
	   console.log("[updateStore] - sParametro [" + sParametro + "] sObjeto [" + sObjeto + "]");

	   if (sParametro == undefined || sParametro == "") {
	      return;
	   }

	   if (sObjeto == "cliente") {
	      changeStore("objDD.strNroIdentificacion", "obtenerClientePorTipoIdentificacion.action?sTipoIdentificacion=" + sParametro);
	   } else if (sObjeto == "provinciaNC1") {
	      changeStore("objCliente.ubigeoPrincipal.provincia.idprovincia", "listaProvincias.action?idDepartamento=" + sParametro);
	   } else if (sObjeto == "distritoNC1") {
	      changeStore("objCliente.ubigeoPrincipal.iddistrito", "listaDistritos.action?idProvincia=" + sParametro);
	   } else if (sObjeto == "provinciaNC2") {
	      changeStore("objCliente.ubigeoAlternativo.provincia.idprovincia", "listaProvincias.action?idDepartamento=" + sParametro);
	   } else if (sObjeto == "distritoNC2") {
	      changeStore("objCliente.ubigeoAlternativo.iddistrito", "listaDistritos.action?idProvincia=" + sParametro);
	   } else if (sObjeto == "tipodocumentosinstor") {
	      changeStore("objDocumento.tipoDocumento.idtipodocumento", "autocompletarAllTipoDocumento.action?iWithoutStor=" + sParametro);
	   }
	};



var submitForm = function() {
	//dojo.byId("showErrorDocumento").innerHTML = "&nbsp;";

		   var myForm = dijit.byId("frmNuevoDocumentoUF");
		   if (!myForm.validate()) {

		      dojo.byId("showErrorDocumento").innerHTML = "&nbsp;";
		      return;
		   }

		var datitos = new Array();

		 dojo.forEach(dijit.byId("rolDerecha").getOptions(), function(item){

			 if(item.selected){
				 dojo.byId("idDocPrincipalExpediente").value = item.value;

			 }else{

				 datitos.push(item.value);
			 }

		 });


		 dojo.byId("idsDocumentoPorExSeleccionados").value = datitos;



		 if(dojo.byId("idDocPrincipalExpediente").value){

			 dijit.byId("btnRegistrarDocumentoTop").setAttribute('disabled',true);
			 dijit.byId("btnRegistrarDocumentoBottom").setAttribute('disabled',true);

			 dojo.xhrPost({
			        form: dojo.byId("frmNuevoDocumentoUF"),
			        load: function(data) {


				    dijit.byId("btnRegistrarDocumentoTop").setAttribute('disabled',false);
					dijit.byId("btnRegistrarDocumentoBottom").setAttribute('disabled',false);

				    window.close();
			        }
			     });

		 }else{

			 alert("Debe agregar almenos un documento");
		 }



};


dojo.addOnLoad(function() {

			try {
				new dijit.form.FilteringSelect( {
					id : "objDD.iIdProceso",
					jsId : "objDD.iIdProceso",
					name : "objDD.iIdProceso",
					store : new dojo.data.ItemFileReadStore( {
						url : "autocompletarProcesoN2.action"
					}),
					searchAttr : "label",
					queryExpr : "*${0}*",
					autoComplete : false,
					hasDownArrow : true,
					highlightMatch : "all",
					invalidMessage : "Seleccione un proceso",
					onChange : fillDataProceso,
					value : defIdProceso,
					required : true,
					style : "width:300px"
				}, "fsProceso");

			} catch (err) {
				console.debug("error registering :objDD.iIdProceso");
			}

			try {
				new dijit.form.FilteringSelect( {
					id : "objDD.iIdDestinatario",
					jsId : "objDD.iIdDestinatario",
					name : "objDD.iIdDestinatario",
					store : new dojo.data.ItemFileReadStore( {
						url : "autocompletarUsuarioxProcesoConUsuario.action?idProceso=" + defIdProceso
					}),
					searchAttr : "label",
					queryExpr : "*${0}*",
					autoComplete : false,
					hasDownArrow : true,
					highlightMatch : "all",
					invalidMessage : "Seleccione un usuario final",
					required : true,
					value : defIdUsuarioFinal,
					style : "width:300px"
				}, "fsUsuarioFinal");

			} catch (err) {
				console
						.debug("error registering :objDocumento.expediente.usuario.idUsuarioFinal");
			}


			   try {
				   new dijit.form.FilteringSelect({
				      id             : "objDD.strNroIdentificacion",
				      jsId           : "objDD.strNroIdentificacion",
				      name           : "objDD.strNroIdentificacion",
				      /*store          : new dojo.data.ItemFileReadStore({
				         url: "obtenerClientePorTipoIdentificacion.action"
				      }),*/
				      searchAttr     : "label",
				      queryExpr      : "*${0}*",
				      autoComplete   : false,
				      hasDownArrow   : true,
				      highlightMatch : "all",
				      invalidMessage : "Seleccione un Nro de Identificacion",
				      onChange       : fillDataCliente,
				      required       : true,
				      style          : "width:300px"
				   }, "fsNroIdentificacion");
				   }catch(err){
					   console.debug("error regisetring objDD.strNroIdentificacion");
				   }

				   updateStore("RUC", "cliente");
		});