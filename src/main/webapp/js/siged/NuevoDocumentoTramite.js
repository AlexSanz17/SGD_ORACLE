
var campos;

var tipoDocumentoHasChanged = function(idTipoDocumento) {
   dijit.byId("idtipodocumentoTramite").attr("value", idTipoDocumento);
   
   dijit.byId("objDD.strNroDocumentoTramite").attr("required", true);
    //dijit.byId("idprocesoTramite").setValue(iIdProceso);
    dijit.byId("objDD.tipoNumeracionTramite").setValue("");
};

 function tipoClienteRequerido(tipo , requerido){
  
    if (tipo == '0'){
      /*  dijit.byId("objDD.idPersonaInstitucionTramite").attr("required", requerido);  jc
        dijit.byId("objDD.codCargoPersonaInstitucionTramite").attr("required", requerido);  */
        dijit.byId("objDD.idPersonaInstitucionTramite").attr("required", false);
        dijit.byId("objDD.codCargoPersonaInstitucionTramite").attr("required", false); 
        dijit.byId("objDD.iIdInstitucionTramite").attr("required", requerido);
        dijit.byId("objDD.desUnidadOrganicaTramite").attr("required", false);
    }
    if (tipo == '1'){
        dijit.byId("objDD.idPersonaNaturalTramite").attr("required", requerido);
        dijit.byId("objDD.codCargoPersonaNaturalTramite").attr("required", false);
    }
    if (tipo == '2'){
        dijit.byId("objDD.codCargoPersonaEmpresaTramite").attr("required", false);
        dijit.byId("objDD.iIdEmpresaTramite").attr("required", requerido); 
        dijit.byId("objDD.idPersonaEmpresaTramite").attr("required", false)
    }
}


 mostrarTipoCliente = function(valor){
             if (valor == "0"){
                 dojo.byId("tbClienteTramite").style.display = "";
                 dojo.byId("tbInstitucionTramite").style.display = "";
                 dojo.byId("tbPersonaNaturalTramite").style.display = "none";
                 dojo.byId("tbEmpresaTramite").style.display = "none";
                 
                 tipoClienteRequerido('0', true);
                 tipoClienteRequerido('1', false);
                 tipoClienteRequerido('2', false);
                 
                 dijit.byId("objDD.iIdEmpresaTramite").attr("value", "");
                 dijit.byId("objDD.idPersonaNaturalTramite").attr("value", "");
                 
                 dijit.byId("objDD.codCargoPersonaNaturalTramite").attr("value", "");
                 dijit.byId("objDD.idPersonaEmpresaTramite").attr("value", "");
                 dijit.byId("objDD.codCargoPersonaEmpresaTramite").attr("value", "");
                 
                 dijit.byId("objDD.desUnidadOrganicaTramite").attr("value", "");
             }
             if (valor == "1"){
                 dojo.byId("tbInstitucionTramite").style.display = "none";
                 dojo.byId("tbEmpresaTramite").style.display = "none";
                 dojo.byId("tbPersonaNaturalTramite").style.display = "";
                 dojo.byId("tbClienteTramite").style.display = "";
                 
                 tipoClienteRequerido('0', false);
                 tipoClienteRequerido('1', true);
                 tipoClienteRequerido('2', false);
                
                 dijit.byId("objDD.iIdInstitucionTramite").attr("value", "");
                 dijit.byId("objDD.iIdEmpresaTramite").attr("value", "");
                 
                 dijit.byId("objDD.idPersonaInstitucionTramite").attr("value", "");
                 dijit.byId("objDD.codCargoPersonaInstitucionTramite").attr("value", "");
                 dijit.byId("objDD.idPersonaEmpresaTramite").attr("value", "");
                 dijit.byId("objDD.codCargoPersonaEmpresaTramite").attr("value", "");
            }
            if (valor == "2"){
                 dojo.byId("tbEmpresaTramite").style.display = "";
                 dojo.byId("tbInstitucionTramite").style.display = "none";
                 dojo.byId("tbPersonaNaturalTramite").style.display = "none";
                 dojo.byId("tbClienteTramite").style.display = "";
                 
                 tipoClienteRequerido('0', false);
                 tipoClienteRequerido('1', false);
                 tipoClienteRequerido('2', true);
                
                 dijit.byId("objDD.iIdInstitucionTramite").attr("value", "");
                 dijit.byId("objDD.idPersonaNaturalTramite").attr("value", "");
                 
                 dijit.byId("objDD.codCargoPersonaNaturalTramite").attr("value", "");
                 dijit.byId("objDD.idPersonaInstitucionTramite").attr("value", "");
                 dijit.byId("objDD.codCargoPersonaInstitucionTramite").attr("value", "");
            }
 }

fillDataCargoPersona = function(iIdRemitente){
    if (iIdRemitente!=""){
       if (Siged.Forms.radiobuttonGetCheckedByName("objDD.idTipoCliente")=="0"){
            dijit.byId('objDD.codCargoPersonaInstitucionTramite').attr("value","");
            
            if (document.getElementById("cargoPersona").value==""){
              var storeCargo = new dojo.data.ItemFileWriteStore({url: "autocompletarCargoRemitente.action?iIdCliente=" + parseInt(dijit.byId('objDD.iIdInstitucionTramite').value) + "&idPersona=" + parseInt(iIdRemitente)}); 
              storeCargo.close();
              var sortAttributes = [{ attribute: "id", descending: true}];
              dijit.byId('objDD.codCargoPersonaInstitucionTramite').store = storeCargo;
              function completed(items, findResult){ 
                 dijit.byId('objDD.codCargoPersonaInstitucionTramite').attr("value", storeCargo.getValue(items[0], "id"));
              }
			
	      function error(errData, request){ 
	         console.log(errData+" ... Failed cargo."+request);
	      }
		
	      storeCargo.fetch({onComplete: completed, onError: error, sort: sortAttributes});
              
            }else{
              var storeCargo = new dojo.data.ItemFileWriteStore({url: "autocompletarCargoAdministrado.action?codCargo=" + parseInt(document.getElementById("cargoPersona").value)}); 
              storeCargo.close();
              storeCargo.fetch();
              dijit.byId('objDD.codCargoPersonaInstitucionTramite').store = storeCargo;
              dijit.byId('objDD.codCargoPersonaInstitucionTramite').attr("value", document.getElementById("cargoPersona").value);
            }
        }else{
            dijit.byId('objDD.codCargoPersonaEmpresaTramite').attr("value","");
            
            if (document.getElementById("cargoPersona").value==""){
              var storeCargo = new dojo.data.ItemFileWriteStore({url: "autocompletarCargoRemitente.action?iIdCliente=" + parseInt(dijit.byId('objDD.iIdEmpresaTramite').value) + "&idPersona=" + parseInt(iIdRemitente)}); 
              storeCargo.close();
              var sortAttributes = [{ attribute: "id", descending: true}];   
	      dijit.byId('objDD.codCargoPersonaEmpresaTramite').store = storeCargo;
                 
              function completed(items, findResult){ 
                 dijit.byId('objDD.codCargoPersonaEmpresaTramite').attr("value", storeCargo.getValue(items[0], "id"));
              }
			
	      function error(errData, request){ 
	         console.log(errData+" ... Failed cargo."+request);
	      }
		
	      storeCargo.fetch({onComplete: completed, onError: error, sort: sortAttributes});
              
            }else{
              var storeCargo = new dojo.data.ItemFileWriteStore({url: "autocompletarCargoAdministrado.action?codCargo=" + parseInt(document.getElementById("cargoPersona").value)}); 
              storeCargo.close();
              storeCargo.fetch();
              dijit.byId('objDD.codCargoPersonaEmpresaTramite').store = storeCargo;
              dijit.byId('objDD.codCargoPersonaEmpresaTramite').attr("value", document.getElementById("cargoPersona").value);
            }
        }
        
         document.getElementById("cargoPersona").value = "";
   }   
}
/*
fillDataCargoPersonaNatural = function(idPersona){
     if (idPersona!=''){
        if (document.getElementById("cargoPersona").value==""){
            var storeCargo = new dojo.data.ItemFileWriteStore({url: "autocompletarCargoPersonaNatural.action?iIdCliente=" + parseInt(idPersona)}); 
            storeCargo.close();
            var sortAttributes = [{ attribute: "id", descending: true}];
            dijit.byId('objDD.codCargoPersonaNaturalTramite').store = storeCargo;
              
            function completed(items, findResult){ 
              dijit.byId('objDD.codCargoPersonaNaturalTramite').attr("value", storeCargo.getValue(items[0], "id"));
            }
			
	    function error(errData, request){ 
	      console.log(errData+" ... Failed cargo."+request);
	    }
		
	    storeCargo.fetch({onComplete: completed, onError: error, sort: sortAttributes});
        }else{
            var storeCargo = new dojo.data.ItemFileWriteStore({url: "autocompletarCargoAdministrado.action?codCargo=" + parseInt(document.getElementById("cargoPersona").value)}); 
            storeCargo.close();
            storeCargo.fetch();
            dijit.byId('objDD.codCargoPersonaNaturalTramite').store = storeCargo;
            dijit.byId('objDD.codCargoPersonaNaturalTramite').attr("value", document.getElementById("cargoPersona").value);  
        }
        
        
        /* aime
        if (document.getElementById("mostrarExpedientesAbiertos").value=="SHOW"){
           var strBusquedaExpedienteAbierto = new dojo.data.ItemFileReadStore({
                url: "buscarExpedienteAbierto.action?iIdCliente=" + parseInt(idPersona)
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
                   }
                }
            });
        }else{
             document.getElementById("mostrarExpedientesAbiertos").value = "SHOW";
        }  */
        
     /*   document.getElementById("cargoPersona").value = "";
   }  
};*/


/*
fillDataTipoInstitucion = function(iIdTipo){
     if (iIdTipo!=""){
        var storeInstitucion = new dojo.data.ItemFileWriteStore({url: "obtenerInstituciones.action?idTipoCliente=" + iIdTipo}); 
        storeInstitucion.close();
        storeInstitucion.fetch();
       
        if (Siged.Forms.radiobuttonGetCheckedByName("objDD.idTipoCliente")=="0"){
            dijit.byId('objDD.iIdInstitucionTramite').store=storeInstitucion;
            dijit.byId('objDD.iIdInstitucionTramite').attr("value", "");
            dijit.byId("objDD.idPersonaInstitucionTramite").attr("value", "");
            dijit.byId('objDD.codCargoPersonaInstitucionTramite').attr("value","");
            dijit.byId("objDD.iIdInstitucionTramite").attr("value", document.getElementById("entidad").value);
            document.getElementById("entidad").value = "";
        }else{
            dijit.byId('objDD.iIdEmpresaTramite').store=storeInstitucion;
            dijit.byId('objDD.iIdEmpresaTramite').attr("value", "");
            dijit.byId("objDD.idPersonaEmpresaTramite").attr("value", "");
            dijit.byId('objDD.codCargoPersonaEmpresaTramite').attr("value","");
            dijit.byId("objDD.iIdEmpresaTramite").attr("value", document.getElementById("entidad").value);
            document.getElementById("entidad").value = "";     
        }    
   } 
};*/
/*
fillDataPersona = function(iIdInstitucion){ 
    
    if (iIdInstitucion!=""){
       //JC 
       var storePersona = new dojo.data.ItemFileWriteStore({url: "autocompletarRemitente.action?iIdCliente=" + parseInt(iIdInstitucion)}); 
       storePersona.close();
       storePersona.fetch();
       
       if (Siged.Forms.radiobuttonGetCheckedByName("objDD.idTipoCliente")=="0"){
            dijit.byId('objDD.idPersonaInstitucionTramite').store=storePersona;
            dijit.byId("objDD.idPersonaInstitucionTramite").attr("value", "");
            dijit.byId('objDD.codCargoPersonaInstitucionTramite').attr("value","");
            dijit.byId("objDD.idPersonaInstitucionTramite").attr("value", document.getElementById("persona").value);
            document.getElementById("persona").value = "";
       }
       if (Siged.Forms.radiobuttonGetCheckedByName("objDD.idTipoCliente")=="2"){
            dijit.byId('objDD.idPersonaEmpresaTramite').store=storePersona;
            dijit.byId("objDD.idPersonaEmpresaTramite").attr("value", "");
            dijit.byId('objDD.codCargoPersonaEmpresaTramite').attr("value","");
            dijit.byId("objDD.idPersonaEmpresaTramite").attr("value", document.getElementById("persona").value);
            document.getElementById("persona").value = "";
       }
       
       /* aime
       if (document.getElementById("mostrarExpedientesAbiertos").value=="SHOW"){
            strBusquedaExpedienteAbierto = new dojo.data.ItemFileReadStore({
                 url: "buscarExpedienteAbierto.action?iIdCliente=" + parseInt(iIdInstitucion)
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
                    }
                 }
              }); 
      }else{
          document.getElementById("mostrarExpedientesAbiertos").value = "SHOW";
      }  */
  //  } 
//};


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
      dojo.byId(tbToShow).style.display = "";
   } else {
      console.log("[showTBODY] - [" + tbToShow + "] NO EXISTE");
   }
};


/*fillDataProceso = function(iIdProceso) {
   
   if (fsProcesoFocused) {
       fsProcesoFocused = 0;
   }
   
    dijit.byId("objDD.strNroDocumentoTramite").attr("required", true);
    //dijit.byId("idprocesoTramite").setValue(iIdProceso);
    dijit.byId("objDD.tipoNumeracionTramite").setValue("");
};*/

submitForm = function() {
        if(dijit.byId("dlgProgresBar")!=null){
           dijit.byId("dlgProgresBar").show() ;
        }
      
        if (sTipoGridActual!=TIPO_GRID_RECEPCION_VIRTUAL){
          sTipoGridActual = TIPO_GRID_EXPEDIENTES;
        }
        
        var myForm = dijit.byId("frmNuevoDocumentoTramite");
        if (!myForm.validate()) {
             if(dijit.byId("dlgProgresBar")!=null){
                 dijit.byId("dlgProgresBar").hide() ;
                 }
             return;
        }
        
        var x = document.getElementById("idListaPara");
        document.getElementById("objDD.listaDerivacionPara").value = "";
        
        if (x!=null && x!= "" && x.options.length>0){
           for (var i=0; i<x.options.length;i++){
             if (document.getElementById("objDD.listaDerivacionPara").value == ""){
               document.getElementById("objDD.listaDerivacionPara").value = x.options[i].value;     
             }else{
               document.getElementById("objDD.listaDerivacionPara").value = document.getElementById("objDD.listaDerivacionPara").value + "|" + x.options[i].value;       
             }          
           }
        }else{
            alert("Debe ingresar un usuario destinatario en la derivaciòn");
             if(dijit.byId("dlgProgresBar")!=null){
                 dijit.byId("dlgProgresBar").hide() ;
             }
            return;
        }   
        
        if (dijit.byId("objDD.esTipoRecepcionTramite").attr("value") == "O"){
           if  (dijit.byId("objDD.strObservacionTramite").attr("value")==''){
                alert("Debe ingresar la observación del documento");
                if(dijit.byId("dlgProgresBar")!=null){
                     dijit.byId("dlgProgresBar").hide() ;
                }
                return;
            }
        }
        
        x = document.getElementById("idListaCC");
        document.getElementById("objDD.listaDerivacionCC").value = "";
        if (x!=null && x!= "" && x.options.length>0){
           for (var i=0; i<x.options.length;i++){
             if (document.getElementById("objDD.listaDerivacionCC").value == ""){
               document.getElementById("objDD.listaDerivacionCC").value = x.options[i].value;     
             }else{
               document.getElementById("objDD.listaDerivacionCC").value = document.getElementById("objDD.listaDerivacionCC").value + "|" + x.options[i].value;       
             }          
           }
        }
    
        if(campos==undefined || campos==""){
             enviarArchivo();
             dijit.byId("btnRegistrarDocumentoTramiteTop").attr("disabled", true);
             dijit.byId("btnRegistrarDocumentoTramiteBottom").attr("disabled", true);
        }
};

function enviarArchivo(){
    if (!bIsPosting) {
      bIsPosting = true;
      dojo.xhrPost({
         
         form: dojo.byId("frmNuevoDocumentoTramite"),
         mimetype: "text/html",
         load: function(data) {
            dijit.byId("tabNuevoDocumento").attr("content", data);
            if(dijit.byId("dlgProgresBar")!=null){
   		dijit.byId("dlgProgresBar").hide() ;
	    }
         }
      });
   }
   
}
   
var crearBotonAdjuntar = function() {
   dojox.embed.Flash.available = 0;
   
  // if (TIPO_TRANSACCION!="M"){
        var boton=new dijit.form.Button({
          // type: "button",
           label: "  Adjuntar  ",
           style: "height: 20px; width: 90px;"
        },"btnTramite");

        var upload= new dojox.form.FileUploader({
           button:	boton,
           uploadUrl: "doUploadFile.action",
           uploadOnChange: true,
           postData: {
              iIdUpload: "2",
              tipo: "C",
              cargoTramite : "S"
           },
           selectMultipleFiles: false,
           htmlFieldName:"uploadCargo"
        });
      
       // dijit.byId("btnTramite").attr("iconClass", "siged16 sigedSave16" );

        dojo.connect(upload, "onComplete", function(){
           dojo.xhrPost({

              url: "showUploadFilesDojo2.action",
              content: {
                 iIdUpload: 2,
                 tipo: "C"
              },
              mimetype: "text/html",
              handle: function(data) {
                 dojo.byId('divShowFile2').innerHTML = data;
              }
           });

           upload.value = "";

        });
   // }
};

dojo.addOnLoad(function() {
        
        new dijit.form.TextBox({
	    id : "sNroExpedienteTramite",
	    jsId : "sNroExpedienteTramite",
	    name : "sNroExpediente",
	    readOnly : true,
	    trim : true
	},"sNroExpedienteTramite");

/*
	new dijit.form.TextBox({
		id : "idprocesoTramite",
	    jsId : "idprocesoTramite",
	    name : "idproceso",
	    style : "display:none;"
	},"idprocesoTramite");
*/
	new dijit.form.TextBox({
		id : "objDD.iIdExpedienteTramite",
		jsId : "objDD.iIdExpedienteTramite",
		name : "objDD.iIdExpediente",
		style : "display:none;"
	}, "objDD.iIdExpedienteTramite");
        

     Siged.Forms.combobox("fsSerieTramite", {
        id             : "objDD.iIdSerieTramite",
        jsId           : "objDD.iIdSerieTramite",
        name           : "objDD.iIdSerie",
        searchAttr     : "label",
        searchDelay    : 650,
        queryExpr      : "*${0}*",
        autoComplete   : false,
        hasDownArrow   : true,
        highlightMatch : "all",
        required       : true,
        style          : "width:500px;",
        invalidMessage : "Seleccione un proceso",
        storeUrl       : "autocompletarSerie.action"
     });
     
     new dijit.form.ValidationTextBox({
	    id : "objDD.asuntoExpedienteTramite",
	    jsId : "objDD.asuntoExpedienteTramite",
	    name : "objDD.asuntoExpediente",
	    invalidMessage : "Ingrese un Asunto",
	    regExp : ".{1,1500}",
	    required : true,
            maxLength : "1500",
            uppercase : true,
	    style : "width:500px",
	    trim : true
	}, "objDD.asuntoExpedienteTramite");
        
        new dijit.form.ValidationTextBox({
	    id : "objDD.observacionExpedienteTramite",
	    jsId : "objDD.observacionExpedienteTramite",
	    name : "objDD.observacionExpediente",
            uppercase : true,
	    regExp : ".{1,300}",
	    required : false,
            style : "width:500px",
            maxLength : "150",
	    trim : true
	}, "objDD.observacionExpedienteTramite");
        
         new dijit.form.ValidationTextBox({
             id : "objDD.strAsuntoTramite",
             jsId :"objDD.strAsuntoTramite",
             name:  "objDD.strAsunto",
             invalidMessage:"Ingrese un Asunto",
             uppercase : true,
             regExp: ".{1,1500}", 
             required: "true",
             style: "width:500px",
             maxLength : "1500",
             trim:"true"
	}, "objDD.strAsuntoTramite");
        
        Siged.Forms.combobox("fsPrioridadTramite", {
            id             : "objDD.prioridadTramite",
            jsId           : "objDD.prioridadTramite",
            name           : "objDD.prioridad",
            searchAttr     : "label",
            searchDelay    : 650,
            queryExpr      : "*${0}*",
            autoComplete   : false,
            hasDownArrow   : true,
            highlightMatch : "all",
            required       : true,
            style          : "width:165px;",
            invalidMessage : "Seleccione una prioridad",
            storeUrl       : "autocompletarParametroActivo.action?codParametro=prioridad"
         });
        
         Siged.Forms.combobox("fsTipoDocumentoTramite", {
            id             : "objDocumento.tipoDocumento.idtipodocumentoTramite",
            jsId           : "objDocumento.tipoDocumento.idtipodocumentoTramite",
            name           : "objDocumento.tipoDocumento.idtipodocumento",
            searchAttr     : "label",
            searchDelay    : 650,
            queryExpr      : "*${0}*",
            autoComplete   : false,
            hasDownArrow   : true,
            highlightMatch : "all",
            required       : true,
            style          : "width:430px;",
            invalidMessage : "Seleccione un tipo de Documento",
            onChange       : tipoDocumentoHasChanged,
            storeUrl       : "autocompletarAllTipoDocumento.action?iWithoutStor=0"
         });

    
          Siged.Forms.combobox("fsDerivacionTramite", {
            id             : "objDD.idDestinatarioTramite",
            jsId           : "objDD.idDestinatarioTramite",
            name           : "objDD.idDestinatario",
            searchAttr     : "label",
            searchDelay    : 650,
            queryExpr      : "*${0}*",
            autoComplete   : false,
            hasDownArrow   : true,
            highlightMatch : "all",
            required       : false,
            style          : "width:470px;",
            invalidMessage : "Seleccione un destinatario",
            storeUrl       : "autocompletarAreaDerivacion.action"
         });
         
         
         new dijit.form.ValidationTextBox({
            id : "objDD.strReferenciaTramite",
            jsId : "objDD.strReferenciaTramite",
            name : "objDD.strReferencia",
            invalidMessage : "Revise el dato ingresado",
            regExp : ".{1,300}",
            required : false,
            uppercase : true,
            maxLength : "80",
            style : "width:500px",
            trim : true
            }, "objDD.strReferenciaTramite");

        var dataConfidencial = {
          identifier: 'value',
          items:[
             {value:'S', label:'Confidencial'},
             {value:'N', label:'No Confidencial'}
          ]
        };
  
        Siged.Forms.combobox("fsConfidencialTramite", {
           id             : "objDD.confidencialTramite",
           jsId           : "objDD.confidencialTramite",
           name           : "objDD.confidencial",
           searchAttr     : "label",
           searchDelay    : 650,
           queryExpr      : "*${0}*",
           autoComplete   : false,
           hasDownArrow   : true,
           highlightMatch : "all"
        });

         var storeConfidencialTramite = new dojo.data.ItemFileWriteStore({data: dataConfidencial});
         dijit.byId("objDD.confidencialTramite").store = storeConfidencialTramite;
         dijit.byId("objDD.confidencialTramite").attr('value', 'N');
         
          new dijit.form.ValidationTextBox({
             id : "objDD.strObservacionTramite",
             jsId :"objDD.strObservacionTramite",
             name:  "objDD.strObservacion",
             regExp: ".{1,300}",
             uppercase : true,
             required: false,
             maxLength : "250",
             style: "width:500px",
             trim:"true"
	}, "fsObservacionesDocumentoTramite");
        
        new dijit.form.ValidationTextBox({
             id : "objDD.iPlazoDiaTramite",
             jsId :"objDD.iPlazoDiaTramite",
             name:  "objDD.iPlazoDia",
             maxLength : "2",
             required: false,
             invalidMessage:"Ingrese un plazo valido",
             regExp:"[0-9]{1,4}",
             style: "width:165px",
             trim:"true",
             onBlur: function(){
                if (dojo.trim(this.value)!=''){
                  service.getFechaLimite(this.value).addCallback(function(valor){
                    dijit.byId('sFechaPlazoTramite').attr("value", valor);    
                  });
                }else{
                    dijit.byId('sFechaPlazoTramite').attr("value", "");
                }
             }
	}, "fsPlazoTramite");
        
         new dijit.form.TextBox({
	    id : "sFechaPlazoTramite",
	    jsId : "sFechaPlazoTramite",
	    name : "sFechaPlazo",
	    readOnly : true,
	    trim : true
	},"sFechaPlazoTramite");
        
        new dijit.form.ValidationTextBox({
             id : "objDD.iNroFoliosTramite",
             jsId :"objDD.iNroFoliosTramite",
             name:  "objDD.iNroFolios",
             maxLength : "7",
             required: true,
             invalidMessage:"Ingrese nro de folios totales del documento",
             regExp:"[0-9]{1,7}",
             style: "width:70px",
             trim:"true"
	}, "fsNroFoliosTramite");
        
        
        new dijit.form.ValidationTextBox({
             id : "objDD.iNroFoliosPIDETramite",
             jsId :"objDD.iNroFoliosPIDETramite",
             name:  "objDD.iNroFoliosPIDE",
             maxLength : "7",
             required: false,
             invalidMessage:"Ingrese nro de folios totales del documento",
             regExp:"[0-9]{1,7}",
             style: "width:70px",
             trim:"true"
	}, "fsNroFoliosPIDETramite");
        
        /////
        new dijit.form.ValidationTextBox({
             id : "objDD.iNroFoliosOriginalesTramite",
             jsId :"objDD.iNroFoliosOriginalesTramite",
             name:  "objDD.iNroFoliosOriginales",
             maxLength : "7",
             required: true,
             invalidMessage:"Ingrese nro de folios originales del documento",
             regExp:"[0-9]{1,7}",
             style: "width:70px",
             trim:"true"
	}, "fsNroFoliosOriginalesTramite");
        
        new dijit.form.ValidationTextBox({
             id : "objDD.iNroFoliosCopiasTramite",
             jsId :"objDD.iNroFoliosCopiasTramite",
             name:  "objDD.iNroFoliosCopias",
             maxLength : "7",
             required: false,
             invalidMessage:"Ingrese nro de folios copias del documento",
             regExp:"[0-9]{1,7}",
             style: "width:70px",
             trim:"true"
	}, "fsNroFoliosCopiasTramite");
        
        new dijit.form.ValidationTextBox({
             id : "objDD.iNroFoliosDigitalizadosTramite",
             jsId :"objDD.iNroFoliosDigitalizadosTramite",
             name:  "objDD.iNroFoliosDigitalizados",
             maxLength : "7",
             required: false,
             invalidMessage:"Ingrese nro de folios digitalizados del documento",
             regExp:"[0-9]{1,7}",
             style: "width:70px",
             trim:"true"
	}, "fsNroImagenesDigitalizadasTramite");
        
        /////////
         new dijit.form.ValidationTextBox({
        id             : "objDD.desUnidadOrganicaTramite",
        jsId           : "objDD.desUnidadOrganicaTramite",
        name           : "objDD.desUnidadOrganica",
        maxLength : "70",
        uppercase : true,      
        required       : true,
        style          : "width:300px;",
        invalidMessage : "Ingrese la únidad orgánica" 
     }, "fsUnidadOrganicaTramite");
        
        Siged.Forms.combobox("fsTipoAdjuntoTramite", {
            id             : "idTipoAdjuntoTramite",
            jsId           : "idTipoAdjuntoTramite",
            name           : "idTipoAdjunto",
            searchAttr     : "label",
            searchDelay    : 600,
            queryExpr      : "*${0}*",
            autoComplete   : false,
            hasDownArrow   : true,
            highlightMatch : "all",
            required       : false,
            style          : "width:300px;",
            invalidMessage : "Seleccione el tipo de adjunto",
            storeUrl       : "autocompletarTipoAdjunto.action"
         });
         
         Siged.Forms.combobox("fsRecepcionTramite", {
            id             : "objDD.esTipoRecepcionTramite",
            jsId           : "objDD.esTipoRecepcionTramite",
            name           : "objDD.esTipoRecepcion",
            searchAttr     : "label",
            searchDelay    : 600,
            queryExpr      : "*${0}*",
            autoComplete   : false,
            hasDownArrow   : true,
            highlightMatch : "all",
            required       : true,
            style          : "width:165px;",
            invalidMessage : "Seleccione el tipo de recepción",
            storeUrl       : "autocompletarParametroActivo.action?codParametro=ESTADOS_RECEPCION"
         });
         
           new dijit.form.ValidationTextBox({
             id : "objDD.strNroDocumentoTramite",
             jsId :"objDD.strNroDocumentoTramite",
             maxLength : "35",
             name:  "objDD.strNroDocumento",
             invalidMessage:"Ingrese un Nro de Documento",
             regExp: ".{1,45}",
             required: false,
             style: "width:165px",
             trim:"true"
	}, "fsNroDocumentoTramite");
        
      

        new dijit.form.RadioButton({ 
           checked: true,   
           value: "0",
           name: "objDD.idTipoCliente",
           onClick: function(){mostrarTipoCliente(this.value);}
         }, "fsClienteInstitucionTramite");
      
        new dijit.form.RadioButton({   
           value: "2",
           name: "objDD.idTipoCliente",
           onClick: function(){mostrarTipoCliente(this.value);}
         }, "fsClienteEmpresaTramite");
      
        new dijit.form.RadioButton({
          value: "1",
          name: "objDD.idTipoCliente",
          onClick: function(){mostrarTipoCliente(this.value);}
        },"fsClientePersonaTramite");
        
        
    
    new dijit.form.FilteringSelect({
            id:   "objDD.iIdEmpresaTramite",
            jsId: "objDD.iIdEmpresaTramite",
            name: "objDD.iIdEmpresa",
            autoComplete: false,
            queryExpr: "*${0}*",
            searchAttr: "label",
            hasDownArrow: false,
            highlightMatch : "all",
            required       : true,
            style          : "width:300px;",
           // onChange    : fillDataPersona,
            postCreate: function() {
                      this.connect(this, "onKeyUp", "_onSuggest");
                      this._timeKey = null;
            },
            _onSuggest: function() {
                        if(this.attr('displayedValue').length > 2) {
                                      if(this.store) {
                                              //var url = 'autocompletarFiltroPersonas.action?desPersona=' + this.attr('displayedValue');
                                              var url = 'autocompletarFiltroInstitucionesxTipo.action?desInstitucion=' + this.attr('displayedValue') + '&idTipoCliente=2';
                                              this.store= new dojo.data.ItemFileWriteStore({url: url}); 
                                              this.store.close();
                                              this.store.fetch();
                                              this._startSearch(dojo.trim(this.attr('displayedValue')));
                                      }
                      }
              }
          },"fsEmpresaTramite");
    
   
        
       new dijit.form.ValidationTextBox({
       //Siged.Forms.combobox("fsPersonaEmpresaTramite", {
           id             : "objDD.idPersonaEmpresaTramite",
           jsId           : "objDD.idPersonaEmpresaTramite",
           name           : "objDD.idPersonaEmpresa",
           maxLength : "80",
           required       : true,
           uppercase : true,
           style          : "width:300px;",
          // onChange       : fillDataCargoPersona ,                                                                                    
           invalidMessage : "Ingrese los nombres de la persona"
           //storeUrl       : "autocompletarPersona.action"
        }, "fsPersonaEmpresaTramite");
   
         new dijit.form.ValidationTextBox({
         //Siged.Forms.combobox("fsCargoPersonaEmpresaTramite", {
            id             : "objDD.codCargoPersonaEmpresaTramite",
            jsId           : "objDD.codCargoPersonaEmpresaTramite",
            name           : "objDD.codCargoPersonaEmpresa",
            maxLength : "80",
            uppercase : true,
            required       : true,
            style          : "width:300px;",
            invalidMessage : "Ingrese el cargo del remitente" //,
            //storeUrl       : "autocompletarCargo.action"
         }, "fsCargoPersonaEmpresaTramite");
       
       new dijit.form.FilteringSelect({
            id:   "objDD.iIdInstitucionTramite",
            jsId: "objDD.iIdInstitucionTramite",
            name: "objDD.iIdInstitucion",
            autoComplete: false,
            queryExpr: "*${0}*",
            searchAttr: "label",
            hasDownArrow: false,
            highlightMatch : "all",
            required       : true,
            style          : "width:300px;",
           // onChange    : fillDataPersona,
            postCreate: function() {
                      this.connect(this, "onKeyUp", "_onSuggest");
                      this._timeKey = null;
            },
            _onSuggest: function() {
                        if(this.attr('displayedValue').length > 2) {
                                      if(this.store) {
                                              //var url = 'autocompletarFiltroPersonas.action?desPersona=' + this.attr('displayedValue');
                                              var url = 'autocompletarFiltroInstitucionesxTipo.action?desInstitucion=' + this.attr('displayedValue') + '&idTipoCliente=1';
                                              this.store= new dojo.data.ItemFileWriteStore({url: url}); 
                                              this.store.close();
                                              this.store.fetch();
                                              this._startSearch(dojo.trim(this.attr('displayedValue')));
                                      }
                      }
              }
          },"fsInstitucionTramite");
    
   
       new dijit.form.ValidationTextBox({
           id             : "objDD.idPersonaInstitucionTramite",
           jsId           : "objDD.idPersonaInstitucionTramite",
           name           : "objDD.idPersonaInstitucion",
           maxLength : "80",
           required       : true,
           uppercase : true,
           style          : "width:300px;",
           invalidMessage : "Ingrese los nombres de la persona"
           
        }, "fsPersonaInstitucionTramite");
   
         new dijit.form.ValidationTextBox({
         //Siged.Forms.combobox("fsCargoPersonaInstitucionTramite", {
            id             : "objDD.codCargoPersonaInstitucionTramite",
            jsId           : "objDD.codCargoPersonaInstitucionTramite",
            name           : "objDD.codCargoPersonaInstitucion",
            maxLength : "80",
            uppercase : true,
            required       : true,
            style          : "width:300px;",
            invalidMessage : "Ingrese el cargo del remitente" 
         }, "fsCargoPersonaInstitucionTramite");
        
        
    /////////////////////  PERSONA NATURAL /////////////////////////// 
    
   new dijit.form.FilteringSelect({
      id:   "objDD.idPersonaNaturalTramite",
      jsId: "objDD.idPersonaNaturalTramite",
      name: "objDD.idPersonaNatural",
      autoComplete: false,
      queryExpr: "*${0}*",
      searchAttr: "label",
      hasDownArrow: false,
      highlightMatch : "all",
      required       : true,
      style          : "width:300px;",
    //  onChange    : fillDataCargoPersonaNatural,
      postCreate: function() {
                this.connect(this, "onKeyUp", "_onSuggest");
		this._timeKey = null;
      },
      _onSuggest: function() {
	          if(this.attr('displayedValue').length > 2) {
                                if(this.store) {
                                	var url = 'autocompletarFiltroPersonas.action?desPersona=' + this.attr('displayedValue');
                                	this.store= new dojo.data.ItemFileWriteStore({url: url}); 
                			this.store.close();
                			this.store.fetch();
                                        this._startSearch(dojo.trim(this.attr('displayedValue')));
				}
		}
	}
    },"fsPersonaNaturalTramite");
    
    
    new dijit.form.ValidationTextBox({
    //Siged.Forms.combobox("fsCargoPersonaNaturalTramite", {
        id             : "objDD.codCargoPersonaNaturalTramite",
        jsId           : "objDD.codCargoPersonaNaturalTramite",
        name           : "objDD.codCargoPersonaNatural",
        maxLength : "80",
        uppercase : true,
        required       : true,
        style          : "width:300px;",
        invalidMessage : "Ingrese el cargo del remitente"//,
        //storeUrl       : "autocompletarCargo.action"
     }, "fsCargoPersonaNaturalTramite");
    
     
   
   
   

   if(TIPO_TRANSACCION+"" == "A"){
       llenarCamposReferencia();
   }else{
         if(TIPO_TRANSACCION+"" == "M"){
           llenarCampos();
         }
         if(TIPO_TRANSACCION+"" == "N"){
            mostrarTipoCliente("0"); 
            dijit.byId("objDD.prioridadTramite").attr("value",PRIORIDAD_NORMAL); 
         }
         if(TIPO_TRANSACCION+"" == "R"){
           llenarCamposRecepcionVirtual();  
         } 
         if(TIPO_TRANSACCION+"" == "MR"){
           llenarCamposModificarRecepcionVirtual();  
         } 
   }      
   
   //crearBotonAdjuntar(); 
    
  });