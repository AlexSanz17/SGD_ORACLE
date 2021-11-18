var UpdateInfoCliente=function(sNroIdentificacion){
	
	console.debug("Dentro del metodo UpdateInfoCliente y el sNroIdentificacion:"+sNroIdentificacion);
	
	if(sNroIdentificacion==undefined || sNroIdentificacion==""){
		hideTBODY("tbDataRUC");
		hideTBODY("tbDataDNI");
		hideTBODY("tbDataCliente");
		console.debug("Saliendo del Metodo");
		return;
	}
	/*
	var strCliente = dijit.byId("objDD.strNroIdentificacion").store;
	strCliente.fetchItemByIdentity({
		identity: sNroIdentificacion,
		onItem: function(item){*/
			service.obtenerClientexTI(null,sNroIdentificacion).addCallback(function(ClienteBeanJson) {
				/*
				console.debug("Dentro del metodo obtenerClientexTI Datos Encontrados:"); 
				console.debug("idcliente:"+ClienteBeanJson.idcliente);	
				console.debug("tipoidentificacion:"+ClienteBeanJson.tipoidentificacion);
				console.debug("razonsocial:"+ClienteBeanJson.razonsocial);
				console.debug("representantelegal:"+ClienteBeanJson.representantelegal);
				console.debug("razonsocial:"+ClienteBeanJson.razonsocial);
				console.debug("nombres:"+ClienteBeanJson.nombres);
				console.debug("apellidopaterno:"+ClienteBeanJson.apellidopaterno);
				console.debug("apellidomaterno:"+ClienteBeanJson.apellidomaterno);
				console.debug("direccionp:"+ClienteBeanJson.direccionp);
				console.debug("iddepartamento:"+ClienteBeanJson.iddepartamento);
				console.debug("idprovincia:"+ClienteBeanJson.idprovincia);
				console.debug("iddistrito:"+ClienteBeanJson.iddistrito);
				console.debug("direcciona:"+ClienteBeanJson.direcciona);
				console.debug("iddepartamentoa:"+ClienteBeanJson.iddepartamentoa);
				console.debug("idprovinciaa:"+ClienteBeanJson.idprovinciaa);
				console.debug("iddistritoa:"+ClienteBeanJson.iddistritoa);
				console.debug("telefono:"+ClienteBeanJson.telefono);
				console.debug("correo:"+ClienteBeanJson.correo);*/
				
				
		         dijit.byId("objDD.iIdCliente").setValue(ClienteBeanJson.idcliente);
		         var tipoIdentificacion=ClienteBeanJson.tipoidentificacion;
			         if (tipoIdentificacion=="RUC") {
			            hideTBODY("tbDataDNI");
			            dijit.byId("objDocumento.expediente.cliente.razonSocial").attr("value", ClienteBeanJson.razonsocial);
			            dijit.byId("objDocumento.expediente.cliente.representanteLegal").attr("value", ClienteBeanJson.representantelegal);
			            //dojo.byId("sCliente").innerHTML = ClienteBeanJson.razonsocial;
			            showTBODY("tbDataRUC");
			         } else if (tipoIdentificacion=="DNI" || tipoIdentificacion=="Otro") {
			            hideTBODY("tbDataRUC");
			            dijit.byId("objDocumento.expediente.cliente.nombres").attr("value", ClienteBeanJson.nombres);
			            dijit.byId("objDocumento.expediente.cliente.apellidoPaterno").attr("value", ClienteBeanJson.apellidopaterno);
			            dijit.byId("objDocumento.expediente.cliente.apellidoMaterno").attr("value", ClienteBeanJson.apellidomaterno);
			            //dojo.byId("sCliente").innerHTML = ClienteBeanJson.nombres + " " + ClienteBeanJson.apellidopaterno;
			            showTBODY("tbDataDNI");
			         }

                  showTBODY("tbDataCliente");
			         dijit.byId("objDocumento.expediente.cliente.direccionPrincipal").attr("value", ClienteBeanJson.direccionp);
			         sPrv1 = ClienteBeanJson.idprovincia;
			         sDst1 = ClienteBeanJson.iddistrito;
			         
			         console.debug("procesando 2");
			         
			         dijit.byId("objDocumento.expediente.cliente.ubigeoPrincipal.provincia.departamento.iddepartamento").attr("value", ClienteBeanJson.iddepartamento);
			         console.debug("LEVANTANDO DATA sPrv1 "+ sPrv1 + " sDst1 " + sDst1);
			         dijit.byId("objDocumento.expediente.cliente.direccionAlternativa").attr("value", ClienteBeanJson.direcciona);
			         sPrv2 = ClienteBeanJson.idprovinciaa;
			         sDst2 = ClienteBeanJson.iddistritoa;
			                     
					 dijit.byId("objDocumento.expediente.cliente.ubigeoAlternativo.provincia.departamento.iddepartamento").attr("value", Siged.String.isEmpty(ClienteBeanJson.iddepartamentoa)?"":ClienteBeanJson.iddepartamentoa);
					 dijit.byId("objDocumento.expediente.cliente.ubigeoAlternativo.provincia.idprovincia").attr("value", Siged.String.isEmpty(sPrv2)?"":sPrv2);
					 dijit.byId("objDocumento.expediente.cliente.ubigeoAlternativo.iddistrito").attr("value", Siged.String.isEmpty(sDst2)?"":sDst2);
			         dijit.byId("objDocumento.expediente.cliente.telefono").attr("value", ClienteBeanJson.telefono);
			         dijit.byId("objDocumento.expediente.cliente.correo").attr("value", ClienteBeanJson.correo);
			         
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
		         });   
		//}
	//});

};

var UpdateInfoConcesionario=function(sConcesionario){
	console.debug("(UpdateInfoConcesionario) - Concesionario seleccionado con ID [%d]", sConcesionario);
	strConcesionario.fetchItemByIdentity({
		identity: sConcesionario,
		onItem: function(item){
			dijit.byId("objDD.iIdCorrentista").attr("value", strConcesionario.getValue(item, "idcorrentista"));
			dijit.byId("objDD.strCorrentista").attr("value", strConcesionario.getValue(item, "correntista"));
			dijit.byId("objDD.strDireccionConcesionario").attr("value", strConcesionario.getValue(item, "strDireccion"));
			dijit.byId("objDD.strCorreoConcesionario").attr("value", strConcesionario.getValue(item, "strCorreoConcesionario"));
		}
	});
};

var UpdateInfoProceso=function(idProceso){
	console.debug("(UpdateInfoProceso) - Proceso seleccionado con ID [%d]", idProceso);
	strProceso.fetchItemByIdentity({
		identity: idProceso,
		onItem: function(item){
			dijit.byId("objDD.strUnidad").attr("value", strProceso.getValue(item,"areadestino"));
			dijit.byId("objDD.strResponsable").attr("value", strProceso.getValue(item,"destinatario"));
			dijit.byId("objDD.iIdResponsable").attr("value", strProceso.getValue(item,"idresponsable"));
			if(strProceso.getValue(item,"tipoproceso")=="stor"){
				dijit.byId("objDD.iIdTipoDocumento").attr("value",strProceso.getValue(item,"idtipodocumento"));
				dijit.byId("objDD.iIdTipoDocumento").attr("readOnly",true);
			}else{
				dijit.byId("objDD.iIdTipoDocumento").attr("readOnly", false);
			}
		}
	});
};

var viewDocumentoQASOnLoad=function(){
	
	dojo.query("input[name='sTipoIdentificacion']").forEach(function(node){
		dijit.byId(node.id).attr("checked", false);
		if(node.value==QAS_TIPOIDENTIFICACION){
			dijit.byId(node.id).attr("checked", true);
		}
	});
	fillDataCliente(QAS_NROIDENTIFCACION);
};

function rechazarDocumento() {
	var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes, width=500, height=290, top=50, left=200";
	var pagina="goRechazarQAS.action?iIdDoc="+document.getElementById("idDocumento").value;
   window.open(pagina,"",opciones);
}

function abrirArchivo(pagina) {
	var opciones="location=mainFrame";
	window.open(pagina,"",opciones);
}

function validarFormulario(){
	if(document.getElementsByName("objDD.strNroCaja")[0].value=="")
		return confirm("¿Desea aprobar el documento sin un numero de caja?");
	return true;
}
 
var rechazando=false;
var aprobando=false;
$(document).ready(function(){
    /*var tipoIdentificacion="<s:property value='objDD.strTipoIdentificacion' />";
    if(tipoIdentificacion=="RUC")
       $("#representantelegal").show();
    else
       $("#representantelegal").hide();
*/
    // valida que un numero tenga 'n' cifras decimales
	$.validator.addMethod("decimales",function(value,element,params){
		return this.optional(element) || value.indexOf(".")<0 || value.length-value.indexOf(".")<=parseInt(params)+1;
	},"");

    // valida que un número no empiece con ceros
	$.validator.addMethod("nocero",function(value,element){
		var punto=value.indexOf(".");
		//var valido=this.optional(element);
		var valido=true;
		if(punto>-1){
			value=value.substring(0,punto);
		}
		if(value.length>1){
			valido=value.charAt(0)!='0';
		}
		return valido;
	},"");
		  
	var validator=$("form[name='frmQAS']").validate({
		rules:{
    		"objDD.strNroCaja":{
    			digits: false 
			},
			"objDD.strNroIdentificacion":{
				required: true
			},
			"objDD.strRUC":{
				required: true
			},
			"objDD.strMontoReclamo":{
				number: true,
				max: 10000,
				min: 0,
				decimales: 2,
				nocero: true
			}
		},
		messages:{
			"objDD.strNroCaja":{
				digits: "El n&uacute;mero de caja debe contener s&oacute;lo d&iacute;gitos.",
				min: jQuery.format("El n&uacute;mero de caja debe ser al menos {0}")
			},
			"objDD.strNroIdentificacion":{
				required: "Debe ingresar un cliente."
			},
			"objDD.strRUC":{
				required: "Debe ingresar un concesionario."
			},
			"objDD.strMontoReclamo":{
				number: "El monto debe ser un n&uacute;mero.",
				max: jQuery.format("El valor del monto no debe ser mayor a {0}."),
				min: jQuery.format("El valor del monto debe ser mayor o igual a {0}."),
				decimales: jQuery.format("El monto debe tener como m&aacute;ximo {0} decimales."),
				nocero: "El n&uacute;mero no debe empezar con ceros."
			}
		},
		errorPlacement: function(error, element) {
			error.appendTo( element.parent().next() );
		}
	});

    $("form[name='frmQAS']").submit(function(){
    	if(validator.numberOfInvalids()==0){
    		//if(validarFormulario()){
    			var nrodoc=$(this).find("input[name='objDD.strNroDocumento']").val();
    			if(confirm("¿Desea aprobar el documento "+nrodoc+"?")) {
    				$.ajax({
    					type: 'POST',
    					url: $(this).attr('action'),
    					data: $(this).serialize(),
    					success: function(data) {
    						dijit.byId("contentPaneDetail").attr("content", data);
    						showGridInbox(TIPO_GRID_QAS_DIGITALIZADOS);
    					}
    				});
    				return false;
    			}
                         //return true;
    			activarBotones();
    			return false;
    		//}
    	}
    	$("input[name='objDD.strNroCaja']").get(0).focus();
    	activarBotones();
    	return false;
	});

	$(".aprobar").click(function(){
		if(!aprobando){
			desactivarBotones();
			$("form[name='frmQAS']").submit();
		}
	});

	$(".rechazar").click(function(){
		if(!rechazando){
			desactivarBotones();
			rechazarDocumento();
		}
	});
	
	$(".eliminar").click(function(){
		if(confirm("¿Esta seguro que desea eliminar este submotivo?")){
			var set=$(this).parent().parent();
			$(this).parent().remove();
			if(set.find(".fila").length==1){
				set.hide();
			}
		}
	});

});

function desactivarBotones(){
	rechazando=true;
	$(".rechazar").fadeTo(0, 0.45).css("cursor","wait");
	aprobando=true;
	$(".aprobar").fadeTo(0, 0.45).css("cursor","wait");
}

function activarBotones(){
	rechazando=false;
	$(".rechazar").fadeTo("slow",1).css("cursor","pointer");
	aprobando=false;
	$(".aprobar").fadeTo("slow", 1).css("cursor","pointer");
}

function completarComboCliente(){
	
	//var strCliente       = new dojo.data.ItemFileReadStore({url: "obtenerDataClienteCxb.action?sTipoIdentificacion=" + ""});
	
    Siged.Forms.combobox("fsNroIdentificacion",{
        id              : "objDD.strNroIdentificacion",
        jsId            : "objDD.strNroIdentificacion",
        name            : "objDD.strNroIdentificacion",
        storeUrl        : 'obtenerDataClienteCxb.action',
        searchAttr      : "label",        
        queryExpr       : "*${0}*",
        autoComplete    : false,
        searchDelay     : 650,
        hasDownArrow    : false,
        highlightMatch  : "all",
        invalidMessage  : "Seleccione un Nro de Identificacion",
        onChange        : UpdateInfoCliente,
//        onChange        : fillDataCliente,
        required        : true,
        style           : "width:300px"
    });	
	
	
}

dojo.addOnLoad(function() {
	
	completarComboCliente();
	
});