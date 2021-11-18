var provincia;
var distrito;

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

var showMe = function() {
	   resetRegistroClienteStor();
	   dijit.byId("dlgNuevoClienteStor").show();
};

var buscarClienteStor = function() {
   var sFiltroBusqueda = dijit.byId("txtFiltroBusquedaClienteStor").attr("value");

   console.debug("(buscarClienteStor) Filtro de busqueda [%s]", sFiltroBusqueda);

   if (!Siged.String.isEmpty(sFiltroBusqueda)) {
      dijit.byId("grdBusquedaClienteStor").setStore(new dojo.data.ItemFileReadStore({url: "buscarCliente.action?sFiltroBusqueda=" + sFiltroBusqueda}));
   }
};

var createClienteStor = function() {
	 var data = dijit.byId("dlgNuevoClienteStor").attr("value");
	 var msg ="";
	 console.log("registry start here");
	   dijit.registry.forEach(function(widget, index, hash){
		   console.log(widget.id, " w.id ");
		 });

	   console.log("registry end here");

	 if (!dijit.byId("frmNuevoClienteStor").validate()) {
			  msg =  "Data inv&aacute;lida... Corregir campos <br>" ;
			  //alert("1") ;
		   }
	  if(data.objClienteStor.direccionPrincipal != ""){
		  if(data.objClienteStor.ubigeoPrincipal.iddistrito == ""||
				  data.objClienteStor.ubigeoPrincipal.provincia.idprovincia == "" ||
					  data.objClienteStor.ubigeoPrincipal.provincia.departamento.iddepartamento == "")

		  {
			 // alert("2") ;
				   msg = msg+ "Debe completar los datos de ubigeo Principal" +"<br>";
		  }
	  }
	  if(data.objClienteStor.direccionAlternativa != ""){
			  if(data.objClienteStor.ubigeoAlternativo.iddistrito == ""||
					  data.objClienteStor.ubigeoAlternativo.provincia.idprovincia == "" ||
						  data.objClienteStor.ubigeoAlternativo.provincia.departamento.iddepartamento == "")

			  {

				  //alert("["+data.objClienteStor.ubigeoAlternativo+"]");
				    msg =  msg+ "Debe completar los datos de ubigeo Procesal" +"<br>";
			  }
	  }
	   if(data.objClienteStor.ubigeoPrincipal.iddistrito != ""||
			   data.objClienteStor.ubigeoPrincipal.provincia.idprovincia != ""||
				  data.objClienteStor.ubigeoPrincipal.provincia.departamento.iddepartamento != "")
		{
				   if(data.objClienteStor.direccionPrincipal == ""){
					  // alert("4") ;
				      msg =  msg+  "Debe ingresar el campo Direccion Principal" +"<br>";
				   }


		}
	   if(data.objClienteStor.ubigeoAlternativo.iddistrito != ""||
			   data.objClienteStor.ubigeoAlternativo.provincia.idprovincia != ""||
					  data.objClienteStor.ubigeoAlternativo.provincia.departamento.iddepartamento != "")
	   {
				   if(data.objClienteStor.direccionAlternativa == ""){
					   ///alert("6") ;
					    msg =  msg+ "Debe ingresar el campo Direccion Procesal" +"<br>";
				   }

	   }

   if ("Otro" == Siged.Forms.radiobuttonGetCheckedByName("sTipoIdentificacionNC")) {
      var nroIdentificacion = dijit.byId("objClienteStor.numeroIdentificacion").attr("value");
      var autoMode = dijit.byId("chkAutoGenerarNroIdentificacionStor").attr("checked");

      if (Siged.String.isEmpty(nroIdentificacion) && autoMode == false) {
         msg =  msg+ "Debe seleccionar el modo de auto-generacion" +"<br>";
      }
   }

   if(msg!=""){
	  // console.log("mensaje : "+dojo.byId("showErrorClienteStormp").innerHTML  );
	   var mensaje = document.getElementById("showErrorClienteStormp");
	   console.log("mensaje : "+mensaje.innerHTML );
	   mensaje.innerHTML = msg ;

	   return;
   }

   dijit.byId("btnRegistroClienteStor").attr("disabled", true);
   dojo.byId("showErrorClienteStormp").innerHTML = "&nbsp;";



   if (data.objClienteStor.direccionPrincipal == "") {
      data.objClienteStor.ubigeoPrincipal = null;
   }

   if (data.objClienteStor.direccionAlternativa == "") {
      data.objClienteStor.ubigeoAlternativo = null;
   }

   console.log("[createClienteStor] - Intentando enviar [" + dojo.toJson(data, true) + "]");

   if (confirm("Desea registrar el ClienteStor?")) {
      var service = new dojo.rpc.JsonService("SMDAction.action");
      var defered = service.saveCliente(data.sTipoIdentificacionNC, data.objClienteStor);
      // dijit.byId("btnRegistroClienteStor").attr("disabled", true);
      dijit.byId("dlgProgresBar").show() ;
      //  alert('beforedataback1') ;
      defered.addCallback(function(sResult) {
         //alert('databack1') ;
         // dijit.byId("btnRegistroClienteStor").attr("disabled", false);
         dijit.byId("dlgProgresBar").hide() ;
         // alert('databack2') ;
         if (sResult == "NotCreated") {
            console.log("[createClienteStor] - No se pudo registrar el ClienteStor");
            dojo.byId("showErrorClienteStormp").innerHTML = "El Nro de Identificacion ya existe en la Base de Datos";
            alert("El Nro de Identificacion ya existe en la Base de Datos");
            dijit.byId("btnRegistroClienteStor").attr("disabled", false);
            dijit.byId("dlgNuevoClienteStor").show();
         } else {
            console.log("[createClienteStor] - ClienteStor creado con Nro de Identificacion [" + sResult + "]");

            dojo.query("input[name='sTipoIdentificacion']").forEach(function(node) {
               dijit.byId(node.id).attr("checked", false);

               if (node.value == data.sTipoIdentificacionNC) {
                  dijit.byId(node.id).attr("checked", true);
                  console.log("[createClienteStor] - ID [" + node.id + "] CHECKED [" + node.checked + "]");
               }
            });



            //temporal - por el tema de qas
            if (dijit.byId("objDocumento.expediente.ClienteStor.numeroIdentificacion")) {
               updateStore("", "ClienteStor");
               dijit.byId("objDocumento.expediente.ClienteStor.numeroIdentificacion").setValue(sResult);
            } else if (dijit.byId("objDD.strNroIdentificacion")) {
               updateStore("", "ClienteStor_qas");
               dijit.byId("objDD.strNroIdentificacion").setValue(sResult);
            } else if (dijit.byId("expediente.expedientestor.reclamante.numeroIdentificacion")){
            	console.log(sResult);
            	dijit.byId("expediente.expedientestor.reclamante.numeroIdentificacion").attr("displayedValue",sResult);
            }

            dijit.byId("dlgNuevoClienteStor").hide();
         }
      });
   } else {
      dijit.byId("btnRegistroClienteStor").attr("disabled", false);
   }

};

var prepareData = function(sTipo, sMode) {
	if(sTipo=="RUC"){
		dijit.byId("objClienteStor.razonSocial").setValue("");
		dijit.byId("objClienteStor.representanteLegal").setValue("");
      document.getElementById("btnBuscarRucStor").parentNode.style.display="block";
		//dijit.byId("buscarRuc").show();
		if (sMode == "show") {
			dijit.byId("objClienteStor.razonSocial").attr("required", true);
			showTBODY("tbDataNCRUC");
		} else {
			dijit.byId("objClienteStor.razonSocial").attr("required", false);
			hideTBODY("tbDataNCRUC");
		}
	} else {
		dijit.byId("objClienteStor.nombres").setValue("");
		dijit.byId("objClienteStor.apellidoPaterno").setValue("");
		dijit.byId("objClienteStor.apellidoMaterno").setValue("");
      document.getElementById("btnBuscarRucStor").parentNode.style.display="none";
		//dijit.byId("buscarRuc").hide();
		if (sMode == "show") {
			dijit.byId("objClienteStor.nombres").attr("required", true);
			dijit.byId("objClienteStor.apellidoPaterno").attr("required", true);
			showTBODY("tbDataNCDNI");
		} else {
			dijit.byId("objClienteStor.nombres").attr("required", false);
			dijit.byId("objClienteStor.apellidoPaterno").attr("required", false);
			hideTBODY("tbDataNCDNI");
		}

      if ("Otro" == Siged.Forms.radiobuttonGetCheckedByName("sTipoIdentificacionNC")) {
         dijit.byId("objClienteStor.numeroIdentificacion").attr("required", false);
         dojo.byId("autoGenerarNroIdentificacion").style.display = "block";
         dijit.byId("objClienteStor.apellidoPaterno").attr("required", false);
      } else {
         dijit.byId("objClienteStor.numeroIdentificacion").attr("required", true);
         dojo.byId("autoGenerarNroIdentificacion").style.display = "none";
      }
	}
};

var resetBusquedaClienteStor = function() {
   dijit.byId("txtFiltroBusquedaClienteStor").attr("value", "");
   dijit.byId("grdBusquedaClienteStor").showMessage("");
   dijit.byId("grdBusquedaClienteStor").setStore(null);
};

var resetRegistroClienteStor = function() {
	dojo.byId("showErrorClienteStormp").innerHTML = "&nbsp;";
	dlgNuevoClienteStor.reset();

	dojo.query("input[name='sTipoIdentificacionNC']").forEach(function(node) {
		dijit.byId(node.id).attr("checked", false);

		if (node.value == "RUC") {
			dijit.byId(node.id).attr("checked", true);
			prepareData("DNI", "hide");
			prepareData("RUC", "show");
		}

		console.log("Nodo [" + node.name + "] ID [" + node.id + "] VALUE [" + node.value + "] CHECKED [" + node.checked + "]");
	});

	provincia=null;
	distito=null;

	dijit.byId("objClienteStor.ubigeoPrincipal.provincia.departamento.iddepartamento").setValue("");
	dijit.byId("objClienteStor.ubigeoPrincipal.provincia.departamento.iddepartamento").setDisplayedValue("");

	dijit.byId("objClienteStor.ubigeoPrincipal.provincia.idprovincia").setValue("");
	dijit.byId("objClienteStor.ubigeoPrincipal.provincia.idprovincia").setDisplayedValue("");

	dijit.byId("objClienteStor.ubigeoPrincipal.iddistrito").setValue("");
	dijit.byId("objClienteStor.ubigeoPrincipal.iddistrito").setDisplayedValue("");

	dijit.byId("objClienteStor.ubigeoAlternativo.provincia.departamento.iddepartamento").setValue("");
	dijit.byId("objClienteStor.ubigeoAlternativo.provincia.departamento.iddepartamento").setDisplayedValue("");

	dijit.byId("objClienteStor.ubigeoAlternativo.provincia.idprovincia").setValue("");
	dijit.byId("objClienteStor.ubigeoAlternativo.provincia.idprovincia").setDisplayedValue("");

	dijit.byId("objClienteStor.ubigeoAlternativo.iddistrito").setValue("");
	dijit.byId("objClienteStor.ubigeoAlternativo.iddistrito").setDisplayedValue("");
	dijit.byId("btnRegistroClienteStor").attr("disabled", false);
	document.getElementById("seleccionClientesStor").style.display="none";

};

var selectClienteStorFromGrdBusquedaClienteStor = function(e) {
   if (e.rowIndex == undefined) {
      return;
   }

   var numeroIdentificacion = dijit.byId("grdBusquedaClienteStor").getItem(e.rowIndex).numeroidentificacion;
   var tipoIdentificacion = dijit.byId("grdBusquedaClienteStor").getItem(e.rowIndex).tipoidentificacion;

   console.debug("(selectClienteStorFromGrdBusquedaClienteStor) rowIndex [%s] tipoIdentificacion [%s] numeroIdentificacion [%s]", e.rowIndex, tipoIdentificacion, numeroIdentificacion);
   Siged.Forms.radiobuttonResetByName("sTipoIdentificacion", tipoIdentificacion);

   //FIXME estandarizar la definicion de los componentes - para el caso de QAS
   if (dijit.byId("objDocumento.expediente.ClienteStor.numeroIdentificacion")) {
      Siged.Forms.comboboxChangeStore("objDocumento.expediente.ClienteStor.numeroIdentificacion", "obtenerClienteStorPorTipoIdentificacion.action?sTipoIdentificacion=" + "", numeroIdentificacion);
   } else if (dijit.byId("objDD.strNroIdentificacion")) {
      Siged.Forms.comboboxChangeStore("objDD.strNroIdentificacion", "obtenerClienteStorPorTipoIdentificacion.action?sTipoIdentificacion=" + "", numeroIdentificacion);
   }

   dijit.byId("dlgBusquedaClienteStor").hide();
};

var showGrdBusquedaClienteStor = function() {
   dijit.byId("txtFiltroBusquedaClienteStor").attr("value", "");
   dijit.byId("grdBusquedaClienteStor").showMessage("");
   dijit.byId("grdBusquedaClienteStor").setStore(null);
   dijit.byId("dlgBusquedaClienteStor").show();
};

var showRegistroClienteStorQAS = function() {
   resetRegistroClienteStor();
   dijit.byId("dlgNuevoClienteStor").show();
};

var showRegistroClienteStor = function() {
	resetRegistroClienteStor();

   //FIXME estandarizar la definicion de los componentes - para el caso de QAS
   if (dijit.byId("objDocumento.expediente.ClienteStor.numeroIdentificacion")) {
      // ponemos el texto ingresado en el autocomplete y lo pasamos al formulario de nuevo ClienteStor
      var texto=document.getElementById("objDocumento.expediente.ClienteStor.numeroIdentificacion").value;
      var numero=!isNaN(texto);
      if(numero){
         dijit.byId("objClienteStor.numeroIdentificacion").setValue(texto);
      }
      else{
         var seleccionado;
         dojo.query("input[name='sTipoIdentificacion']").forEach(function(radio){
            if(radio.checked){
               seleccionado=radio.value;
            }
         });
         if(seleccionado=="RUC"){
            dijit.byId("objClienteStor.razonSocial").setValue(texto);
         }
         else{
            //TODO los campos para la reniec
         }
      }
   } else if (dijit.byId("objDD.strNroIdentificacion")) {

   }

	dijit.byId("dlgNuevoClienteStor").show();
};

var cancelarRegistro=function(){
	dijit.byId("dlgNuevoClienteStor").hide();
	resetRegistroClienteStor();
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

var buscarPorRUCStor=function(){
	var ruc=document.getElementById("objClienteStor.numeroIdentificacion").value;
	if(ruc!=null && ruc!="" && ruc.lenght!=11){
		buscarRuc(ruc);
	}
};

function buscarRuc(ruc){
	dijit.byId("dlgProgresBar").show();
	service.getClientePorRUC(ruc).addCallback(function(ClienteStor){
		dijit.byId("dlgProgresBar").hide();
		if(ClienteStor.identificacion==ruc){
			resetRegistroClienteStor();
			dijit.byId("objClienteStor.numeroIdentificacion").setValue(ruc);
			dijit.byId("objClienteStor.razonSocial").setValue(ClienteStor.razonSocial);
			dijit.byId("objClienteStor.direccionPrincipal").setValue(ClienteStor.direccion);
			provincia=ClienteStor.provincia;
			distrito=ClienteStor.distrito;
			dijit.byId("objClienteStor.ubigeoPrincipal.provincia.departamento.iddepartamento").setValue(ClienteStor.departamento);
			dijit.byId("objClienteStor.telefono").setValue(ClienteStor.telefono);
			dijit.byId("objClienteStor.correo").setValue(ClienteStor.correo);
		}
		else{
			error(ClienteStor.identificacion);
		}
	});
}

var buscarPorRazonSocial=function(){
	error("");
	var razonSocial=dijit.byId("objClienteStor.razonSocial").getValue();
	if(razonSocial!=null && razonSocial!=""){
		if(razonSocial.length>5){
			dijit.byId("dlgProgresBar").show();
			service.getClientePorRazonSocial(razonSocial).addCallback(function(clientes){
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

var seleccionarClienteStor=function(){
	var ruc=document.getElementById("ClienteStors").value;
	if(ruc!=null && ruc!=""){
		buscarRuc(ruc);
		document.getElementById("seleccionClientes").style.display="none";
	}
};

var cancelar=function(){
	document.getElementById("seleccionClientesStor").style.display="none";
};

function error(texto){
	document.getElementById("showErrorClienteStormp").innerHTML=texto;
}

dojo.addOnLoad(function() {

    // alert("mmm "+dijit.byId("objClienteStor.ubigeoPrincipal.provincia.departamento.iddepartamento"))    ;
	/*
   if ((dijit.byId("objClienteStor.ubigeoPrincipal.provincia.departamento.iddepartamento")!=null)) {
	   dijit.byId("objClienteStor.ubigeoPrincipal.provincia.departamento.iddepartamento").destroy();
   }
   if ((dijit.byId("objClienteStor.ubigeoPrincipal.provincia.idprovincia")!=null)) {
	   dijit.byId("objClienteStor.ubigeoPrincipal.provincia.idprovincia").destroy();
   }
   if ((dijit.byId("objClienteStor.ubigeoPrincipal.iddistrito")!=null)) {
	   dijit.byId("objClienteStor.ubigeoPrincipal.iddistrito").destroy();
   }
   if ((dijit.byId("objClienteStor.ubigeoAlternativo.provincia.departamento.iddepartamento")!=null)) {
	   dijit.byId("objClienteStor.ubigeoAlternativo.provincia.departamento.iddepartamento").destroy();
   }
   if ((dijit.byId("objClienteStor.ubigeoAlternativo.provincia.idprovincia")!=null)) {
	   dijit.byId("objClienteStor.ubigeoAlternativo.provincia.idprovincia").destroy();
   }
   if ((dijit.byId("objClienteStor.ubigeoAlternativo.iddistrito")!=null)) {
	   dijit.byId("objClienteStor.ubigeoAlternativo.iddistrito").destroy();

   }*/


try {

   new dijit.form.ValidationTextBox({
      id             : "objClienteStor.numeroIdentificacion",
      jsId           : "objClienteStor.numeroIdentificacion",
      name           : "objClienteStor.numeroIdentificacion",
      invalidMessage : "Ingrese n&uacute;mero de identificaci&oacute;n. 8 d&iacute;gitos para DNI, 11 para RUC",
      regExpGen      : validateNroIdentificacion,
      required       : true,
      style          : "width:140px"
   }, "txtNroIdentificacion");

}catch(err){
	   console.debug("1error regisetring objClienteStor.nroidentificacion");
}
try {
	new dijit.form.FilteringSelect({
		id             : "objClienteStor.ubigeoPrincipal.provincia.departamento.iddepartamento",
		jsId           : "objClienteStor.ubigeoPrincipal.provincia.departamento.iddepartamento",
		name           : "objClienteStor.ubigeoPrincipal.provincia.departamento.iddepartamento",
		store          : new dojo.data.ItemFileReadStore({
			url: "listaDepartamentos.action"
		}),
		searchAttr     : "label",
		queryExpr      : "*${0}*",
		autoComplete   : false,
		hasDownArrow   : true,
		highlightMatch : "all",
		invalidMessage : "Seleccione un departamento",
		onChange       : function(){
			updateStore(this.value, 'provinciaNC1Stor');
			dijit.byId("objClienteStor.ubigeoPrincipal.provincia.idprovincia").attr("value",provincia);
			},
		required       : false
	},"fsDepartamentoNC1Stor");
}catch(err){
	   console.debug("1error regisetring objClienteStor.ubigeoPrincipal.provincia.departamento.iddepartamento");
}
try {
	new dijit.form.FilteringSelect({
		id             : "objClienteStor.ubigeoPrincipal.provincia.idprovincia",
		jsId           : "objClienteStor.ubigeoPrincipal.provincia.idprovincia",
		name           : "objClienteStor.ubigeoPrincipal.provincia.idprovincia",
		/*store          : new dojo.data.ItemFileReadStore({
			url: "listaProvincias.action"
		}),*/
		searchAttr     : "label",
		queryExpr      : "*${0}*",
		autoComplete   : false,
		hasDownArrow   : true,
		highlightMatch : "all",
		invalidMessage : "Seleccione una provincia",
		onChange       : function(){
			updateStore(this.value, 'distritoNC1Stor');
			dijit.byId("objClienteStor.ubigeoPrincipal.iddistrito").attr("value",distrito);
		},
		required       : false
	},"fsProvinciaNC1Stor");
}catch(err){
	   console.debug("1error regisetring objClienteStor.ubigeoPrincipal.provincia.idprovincia");
}

try {

   new dijit.form.FilteringSelect({
      id             : "objClienteStor.ubigeoPrincipal.iddistrito",
      jsId           : "objClienteStor.ubigeoPrincipal.iddistrito",
      name           : "objClienteStor.ubigeoPrincipal.iddistrito",
      /*store          : new dojo.data.ItemFileReadStore({
         url: "listaDistritos.action"
      }),*/
      searchAttr     : "label",
      queryExpr      : "*${0}*",
      autoComplete   : false,
      hasDownArrow   : true,
      highlightMatch : "all",
      invalidMessage : "Seleccione un distrito",
      required       : false
   }, "fsDistritoNC1Stor");

}catch(err){
	   console.debug("1error regisetring objClienteStor.ubigeoPrincipal.iddistrito");
}
try {

   new dijit.form.FilteringSelect({
      id             : "objClienteStor.ubigeoAlternativo.provincia.departamento.iddepartamento",
      jsId           : "objClienteStor.ubigeoAlternativo.provincia.departamento.iddepartamento",
      name           : "objClienteStor.ubigeoAlternativo.provincia.departamento.iddepartamento",
      store          : new dojo.data.ItemFileReadStore({
         url: "listaDepartamentos.action"
      }),
      searchAttr     : "label",
      queryExpr      : "*${0}*",
      autoComplete   : false,
      hasDownArrow   : true,
      highlightMatch : "all",
      invalidMessage : "Seleccione un departamento",
      onChange       : new Function("updateStore(this.value, 'provinciaNC2Stor')"),
      required       : false
   }, "fsDepartamentoNC2Stor");


}catch(err){
	   console.debug("1error regisetring objClienteStor.ubigeoAlternativo.provincia.departamento.iddepartamento");
}
try {

   new dijit.form.FilteringSelect({
      id             : "objClienteStor.ubigeoAlternativo.provincia.idprovincia",
      jsId           : "objClienteStor.ubigeoAlternativo.provincia.idprovincia",
      name           : "objClienteStor.ubigeoAlternativo.provincia.idprovincia",
      /*store          : new dojo.data.ItemFileReadStore({
         url: "listaProvincias.action"
      }),*/
      searchAttr     : "label",
      queryExpr      : "*${0}*",
      autoComplete   : false,
      hasDownArrow   : true,
      highlightMatch : "all",
      invalidMessage : "Seleccione una provincia",
      onChange       : new Function("updateStore(this.value, 'distritoNC2Stor')"),
      required       : false
   }, "fsProvinciaNC2Stor");

}catch(err){
	   console.debug("1error regisetring objClienteStor.ubigeoAlternativo.provincia.idprovincia");
}
try {

   new dijit.form.FilteringSelect({
      id             : "objClienteStor.ubigeoAlternativo.iddistrito",
      jsId           : "objClienteStor.ubigeoAlternativo.iddistrito",
      name           : "objClienteStor.ubigeoAlternativo.iddistrito",
      /*store          : new dojo.data.ItemFileReadStore({
         url: "listaDistritos.action"
      }),*/
      searchAttr     : "label",
      queryExpr      : "*${0}*",
      autoComplete   : false,
      hasDownArrow   : true,
      highlightMatch : "all",
      invalidMessage : "Seleccione un distrito",
      required       : false
   }, "fsDistritoNC2Stor");
}catch(err){
	   console.debug("1error regisetring objClienteStor.ubigeoAlternativo.iddistrito");
}

   /** Para la busqueda de ClienteStors a la SUNAT **/
   /*new dijit.form.Button({
      id:		"buscarRuc",
      onClick:	buscarPorRUC
   },"buscarRuc");*/

   new dijit.form.Button({
      onClick:	buscarPorRazonSocial
   },"buscarRazonSocial");

   new dijit.form.Button({
      onClick:	seleccionarClienteStor
   },"seleccionarStor");

   new dijit.form.Button({
      onClick:	cancelar
   },"cancelarStor");

});
