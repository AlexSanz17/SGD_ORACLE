////////////////////////////////////////////////////////////////////////////////
function cancelar() {
   dijit.byId("btnWithoutExpedienteAbierto").attr("disabled", false);
   dijit.byId("btnRegresarAlForm").attr("disabled", false);
}

var anioFiscalHasChanged = function (){
    if (!Siged.String.isEmpty(dijit.byId("objDD.concesionario").attr("value")) && !Siged.String.isEmpty(dijit.byId("objDocumento.tipoDocumento.idtipodocumento").attr("value"))){
       Siged.Documento.Numeracion.loadConcesionario(dijit.byId("objDocumento.tipoDocumento.idtipodocumento").attr("value"), 
                                       dijit.byId("objDD.concesionario").attr("value"),
                                       "objDD.strNroDocumento", "objDD.tipoNumeracion", 
                                       document.getElementById("documentoEnumerado").value,
                                        document.getElementById("nroDocumento").value);
    }
};

var tipoResultadoRequerimientoHasChanged = function (){
    var nrodocumentocompl = dijit.byId("objDD.numeroReqTributario").attr("value");
    var nrodocumento = nrodocumentocompl.split(" /-/ ");
    var auxReferencia = 0;
    
    //////////////////////////////EXPEDIENTE///////////////////////////////////
    dijit.byId("objDD.iIdExpediente").setValue(nrodocumento[3]);
    dijit.byId("sNroExpediente").attr("value", nrodocumento[4]);
    dijit.byId("objDD.asuntoExpediente").setValue(nrodocumento[5]);
    dijit.byId("objDD.observacionExpediente").setValue(nrodocumento[6]);
    dijit.byId("objDD.iIdSerie").attr("value",nrodocumento[7]);
    
    //////////////////////////////REFERENCIADO/////////////////////////////////
    var x = document.getElementById("listaDocReferencias");
    for (var i=0; i<x.options.length;i++){
        if (x.options[i].value==nrodocumento[0]){
            auxReferencia=1;
                }
    }
    
    if(nrodocumentocompl != '')
    {
        if(auxReferencia==0)
        {            
            var texto = nrodocumento[1]+" - "+nrodocumento[2];
            var valor = nrodocumento[0];
            $("#listaDocReferencias").append($("<option></option>")
                .attr("value", valor)
                .text(texto));
        }
    }
   
    /////////////////////////NUMERACION/////////////////////////////////////////   
    dijit.byId("objDD.strNroDocumento").attr("value", nrodocumento[2]);
};


function esVacio(cad) { //retorna true si la cadena esta vacia o tieSubmitne espacios en blanco
   var i;
   var blanco = " \n\t" + String.fromCharCode(13); // blancos
   var es_vacio;

   if ((cad == null) || (cad.length == 0)) {
      return true;
   }
   for(i = 0, es_vacio = true; (i < cad.length) && es_vacio; i++)
      es_vacio = blanco.indexOf(cad.charAt(i)) != - 1;
   return(es_vacio);
}

var campos;

function ocultar(id){
   var i = 0;
   var elem ;

   do{

      elem = document.getElementById(id+""+i)   ;

      if(elem!=null){
         // alert('ocultando '+id+""+i) ;
         elem.style.display='none';

      }

      i++ ;
   // if(i==3)elem = null ;
   }while(elem!=null) ;
//  alert('ocultando se queda en '+id+""+i) ;
}

function enviarArchivo(){
   if (!bIsPosting) {
      bIsPosting = true;
      dojo.xhrPost({
         form: dojo.byId("frmNuevoDocumento"),
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

var Base64 = {

   // private property
   _keyStr : "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=",

   // public method for encoding
   encode : function (input) {
      var output = "";
      var chr1, chr2, chr3, enc1, enc2, enc3, enc4;
      var i = 0;

      input = Base64._utf8_encode(input);

      while (i < input.length) {
         chr1 = input.charCodeAt(i++);
         chr2 = input.charCodeAt(i++);
         chr3 = input.charCodeAt(i++);

         enc1 = chr1 >> 2;
         enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
         enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
         enc4 = chr3 & 63;

         if (isNaN(chr2)) {
            enc3 = enc4 = 64;
         } else if (isNaN(chr3)) {
            enc4 = 64;
         }

         output = output +
         this._keyStr.charAt(enc1) + this._keyStr.charAt(enc2) +
         this._keyStr.charAt(enc3) + this._keyStr.charAt(enc4);

      }
      return output;
   },

   // public method for decoding
   decode : function (input) {
      var output = "";
      var chr1, chr2, chr3;
      var enc1, enc2, enc3, enc4;
      var i = 0;

      input = input.replace(/[^A-Za-z0-9\+\/\=]/g, "");

      while (i < input.length) {

         enc1 = this._keyStr.indexOf(input.charAt(i++));
         enc2 = this._keyStr.indexOf(input.charAt(i++));
         enc3 = this._keyStr.indexOf(input.charAt(i++));
         enc4 = this._keyStr.indexOf(input.charAt(i++));

         chr1 = (enc1 << 2) | (enc2 >> 4);
         chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
         chr3 = ((enc3 & 3) << 6) | enc4;

         output = output + String.fromCharCode(chr1);

         if (enc3 != 64) {
            output = output + String.fromCharCode(chr2);
         }
         if (enc4 != 64) {
            output = output + String.fromCharCode(chr3);
         }

      }

      output = Base64._utf8_decode(output);

      return output;

   },

   // private method for UTF-8 encoding
   _utf8_encode : function (string) {
      var string = string.replace(/\r\n/g,"\n");
      var utftext = "";

      for (var n = 0; n < string.length; n++) {

         var c = string.charCodeAt(n);

         if (c < 128) {
            utftext += String.fromCharCode(c);
         }
         else if((c > 127) && (c < 2048)) {
            utftext += String.fromCharCode((c >> 6) | 192);
            utftext += String.fromCharCode((c & 63) | 128);
         }
         else {
            utftext += String.fromCharCode((c >> 12) | 224);
            utftext += String.fromCharCode(((c >> 6) & 63) | 128);
            utftext += String.fromCharCode((c & 63) | 128);
         }

      }

      return utftext;
   },

   // private method for UTF-8 decoding
   _utf8_decode : function (utftext) {
      var string = "";
      var i = 0;
      var c = c1 = c2 = 0;

      while ( i < utftext.length ) {

         c = utftext.charCodeAt(i);

         if (c < 128) {
            string += String.fromCharCode(c);
            i++;
         }
         else if((c > 191) && (c < 224)) {
            c2 = utftext.charCodeAt(i+1);
            string += String.fromCharCode(((c & 31) << 6) | (c2 & 63));
            i += 2;
         }
         else {
            c2 = utftext.charCodeAt(i+1);
            c3 = utftext.charCodeAt(i+2);
            string += String.fromCharCode(((c & 15) << 12) | ((c2 & 63) << 6) | (c3 & 63));
            i += 3;
         }

      }

      return string;
   }

};


function changeTipoIdentificacion(id){
   dijit.byId("idtipoidentificacion").setValue(id);
}

function removeItemTipo(item){
   var itemFather=item.parentNode;
   itemFather.parentNode.removeChild(itemFather);
}

hideTBODY = function(tbToHide) {
   console.log("[hideTBODY] - tbody a ocultar [" + tbToHide + "]");

   if (dojo.byId(tbToHide)) {
      dojo.byId(tbToHide).style.display = "none";
   } else {
      console.log("[hideTBODY] - [" + tbToHide + "] NO EXISTE");
   }
};

showTBODY = function(tbToShow) {
   
   if (dojo.byId(tbToShow)) {
      dojo.byId(tbToShow).style.display = "";
   } else {
      console.log("[showTBODY] - [" + tbToShow + "] NO EXISTE");
   }
};

changeStore = function(objFS, sURL) {
 
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

function tipoClienteRequerido(tipo , requerido){
    if (tipo == '0'){
        dijit.byId("objDD.idPersonaInstitucion").attr("required", false);
        dijit.byId("objDD.codCargoPersonaInstitucion").attr("required", false);
        dijit.byId("objDD.iIdInstitucion").attr("required", requerido);
    }
    if (tipo == '1'){
        dijit.byId("objDD.idPersonaNatural").attr("required", requerido);
        dijit.byId("objDD.codCargoPersonaNatural").attr("required", false);
    }
    if (tipo == '2'){
        dijit.byId("objDD.codCargoPersonaEmpresa").attr("required", false);
        dijit.byId("objDD.iIdEmpresa").attr("required", requerido); 
        dijit.byId("objDD.idPersonaEmpresa").attr("required", false);
    }
};

 function mostrarInstitucion(valor){
             if (valor == "1"){
                 dojo.byId("tbCliente").style.display = "";
                 dojo.byId("tbInstitucion").style.display = "";
                 dojo.byId("tbEmpresa").style.display = "none";
                 dojo.byId("tbPersonaNatural").style.display = "none";
                 dijit.byId("objDD.desUnidadOrganica").attr("required", false);
                 
                 tipoClienteRequerido('0', true);
                 tipoClienteRequerido('1', false);   
                 tipoClienteRequerido('2', false); 
                 
             }else{
                  dojo.byId("tbCliente").style.display = "none";
                  dojo.byId("tbInstitucion").style.display = "none";
                  dojo.byId("tbEmpresa").style.display = "none";
                  dojo.byId("tbPersonaNatural").style.display = "none";
                  dijit.byId("objDD.desUnidadOrganica").attr("required", false);
                  
                  tipoClienteRequerido('0', false);
                  tipoClienteRequerido('1', false);  
                  tipoClienteRequerido('2', false); 
             }
 }

mostrarTipoCliente = function (valor){
             dojo.byId("tbCliente").style.display = "";
             
             if (valor == "0"){
                 dojo.byId("tbInstitucion").style.display = "";
                 dojo.byId("tbEmpresa").style.display = "none";
                 dojo.byId("tbPersonaNatural").style.display = "none";
                 
                 tipoClienteRequerido('0', false);
                 tipoClienteRequerido('1', false);     
                 tipoClienteRequerido('2', false); 
                 
                 dijit.byId("objDD.iIdEmpresa").attr("value", "");
                 dijit.byId("objDD.idPersonaNatural").attr("value", "");
                 
                 dijit.byId("objDD.codCargoPersonaNatural").attr("value", "");
                 dijit.byId("objDD.idPersonaEmpresa").attr("value", "");
                 dijit.byId("objDD.codCargoPersonaEmpresa").attr("value", "");
             }
             if (valor == "1"){
                 dojo.byId("tbInstitucion").style.display = "none";
                 dojo.byId("tbEmpresa").style.display = "none";
                 dojo.byId("tbPersonaNatural").style.display = "";
                 
                 tipoClienteRequerido('0', false);
                 tipoClienteRequerido('1', true);         
                 tipoClienteRequerido('2', false);
                 
                 dijit.byId("objDD.iIdInstitucion").attr("value", "");
                 dijit.byId("objDD.iIdEmpresa").attr("value", "");
                 
                 dijit.byId("objDD.idPersonaInstitucion").attr("value", "");
                 dijit.byId("objDD.codCargoPersonaInstitucion").attr("value", "");
                 dijit.byId("objDD.idPersonaEmpresa").attr("value", "");
                 dijit.byId("objDD.codCargoPersonaEmpresa").attr("value", "");
            }
            if (valor == "2"){    
                 dojo.byId("tbEmpresa").style.display = "";
                 dojo.byId("tbInstitucion").style.display = "none";
                 dojo.byId("tbPersonaNatural").style.display = "none";
                 
                 tipoClienteRequerido('0', false);
                 tipoClienteRequerido('1', false);       
                 tipoClienteRequerido('2', true); 
                 
                 dijit.byId("objDD.iIdInstitucion").attr("value", "");
                 dijit.byId("objDD.idPersonaNatural").attr("value", "");
                 
                 dijit.byId("objDD.codCargoPersonaNatural").attr("value", "");
                 dijit.byId("objDD.idPersonaInstitucion").attr("value", "");
                 dijit.byId("objDD.codCargoPersonaInstitucion").attr("value", "");
             }
}

fillDataPersona = function(iIdInstitucion){ 
    if (iIdInstitucion!=""){
       var storePersona = new dojo.data.ItemFileWriteStore({url: "autocompletarRemitente.action?iIdCliente=" + parseInt(iIdInstitucion)}); 
       storePersona.close();
       storePersona.fetch();
       
       if (Siged.Forms.radiobuttonGetCheckedByName("objDD.idTipoCliente")=="0"){
         dijit.byId('objDD.idPersonaInstitucion').store=storePersona;
         dijit.byId("objDD.idPersonaInstitucion").attr("value", "");
         dijit.byId('objDD.codCargoPersonaInstitucion').attr("value","");
         dijit.byId("objDD.idPersonaInstitucion").attr("value", document.getElementById("persona").value);
         document.getElementById("persona").value = "";
       }
       
       if (Siged.Forms.radiobuttonGetCheckedByName("objDD.idTipoCliente")=="2"){
         dijit.byId('objDD.idPersonaEmpresa').store=storePersona;
         dijit.byId("objDD.idPersonaEmpresa").attr("value", "");
         dijit.byId('objDD.codCargoPersonaEmpresa').attr("value","");
         dijit.byId("objDD.idPersonaEmpresa").attr("value", document.getElementById("persona").value);
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
        }*/
    } 
};

fillDataTipoInstitucion = function(iIdTipo){ 
     if (iIdTipo!=""){  
        var storeInstitucion = new dojo.data.ItemFileWriteStore({url: "obtenerInstituciones.action?idTipoCliente=" + iIdTipo}); 
        storeInstitucion.close();
        storeInstitucion.fetch();
       
        if (Siged.Forms.radiobuttonGetCheckedByName("objDD.idTipoCliente")=="0"){
            dijit.byId('objDD.iIdInstitucion').store=storeInstitucion;
            dijit.byId('objDD.iIdInstitucion').attr("value", "");
            dijit.byId("objDD.idPersonaInstitucion").attr("value", "");
            dijit.byId('objDD.codCargoPersonaInstitucion').attr("value","");
            dijit.byId("objDD.iIdInstitucion").attr("value", document.getElementById("entidad").value);
            document.getElementById("entidad").value = "";
        }else{
           dijit.byId('objDD.iIdEmpresa').store=storeInstitucion;
            dijit.byId('objDD.iIdEmpresa').attr("value", "");
            dijit.byId("objDD.idPersonaEmpresa").attr("value", "");
            dijit.byId('objDD.codCargoPersonaEmpresa').attr("value","");
            dijit.byId("objDD.iIdEmpresa").attr("value", document.getElementById("entidad").value);
            document.getElementById("entidad").value = "";
        }
     }  
};



/**REN Se ejecuta cuando se selecciona un proceso del autocompletar ----------------------------------------------------------*/
/*fillDataProceso = function(iIdProceso) {
   
   if (fsProcesoFocused) {
       fsProcesoFocused = 0;
   }
   
    dijit.byId("idproceso").setValue(iIdProceso);

};*/

resetfrmNuevoDocumento = function() {
   dijit.byId("sNroExpediente").setValue("");
   dijit.byId("objDD.iIdExpediente").setValue("");
   dijit.byId("objDocumento.tipoDocumento.idtipodocumento").setValue("");
   dijit.byId("objDocumento.tipoDocumento.idtipodocumento").setDisplayedValue("");
   dijit.byId("objDocumento.tipoDocumento.idtipodocumento").attr("readOnly", false);
};

submitForm = function() {
        if(dijit.byId("dlgProgresBar")!=null){
           dijit.byId("dlgProgresBar").show() ;
        }
        
        sTipoGridActual = TIPO_GRID_EXPEDIENTES;
        var myForm = dijit.byId("frmNuevoDocumento");

         var bandera = false;
         for(i=0;i<arrStoreTipoDocumentoPIDE.length;i++){
               if (arrStoreTipoDocumentoPIDE[i]==dijit.byId("idtipodocumento").attr("value")){
                 bandera = true;
                 break;
               }
         }
        
         if (bandera){
           bandera = false; 
           for(i=0;i<arrStoreRUCPIDE.length;i++){
              if (arrStoreRUCPIDE[i]==dijit.byId("objDD.iIdInstitucion").attr("value")){
                  bandera = true;
                  break;
              }
            }    
         }
  
         if (bandera){
             dijit.byId("objDD.idPersonaInstitucion").attr("required", true);
             dijit.byId("objDD.codCargoPersonaInstitucion").attr("required", true);
             dijit.byId("objDD.desUnidadOrganica").attr("required", true);
             dijit.byId("objDD.iNroFolios").attr("required", true);
         }else{
             dijit.byId("objDD.idPersonaInstitucion").attr("required", false);
             dijit.byId("objDD.codCargoPersonaInstitucion").attr("required", false);
             dijit.byId("objDD.desUnidadOrganica").attr("required", false);
             dijit.byId("objDD.iNroFolios").attr("required", false);
         }   
        
        if (!myForm.validate()) {
             if(dijit.byId("dlgProgresBar")!=null){
                 dijit.byId("dlgProgresBar").hide() ;
             }
             return;
        }
        
        var x = document.getElementById("idListaIntegrantesInternos");
        document.getElementById("objDD.listaIntegrantesInternos").value = "";
        
        if (x!=null && x!= "" && x.options.length>0){
           for (var i=0; i<x.options.length;i++){
             if (document.getElementById("objDD.listaIntegrantesInternos").value == ""){
               document.getElementById("objDD.listaIntegrantesInternos").value = x.options[i].text;     
             }else{
               document.getElementById("objDD.listaIntegrantesInternos").value = document.getElementById("objDD.listaIntegrantesInternos").value + "|" + x.options[i].text;       
             }          
           }
        }  
       
        x = document.getElementById("idListaIntegrantesExternos");
        document.getElementById("objDD.listaIntegrantesExternos").value = "";
        
        if (x!=null && x!= "" && x.options.length>0){
           for (var i=0; i<x.options.length;i++){
             if (document.getElementById("objDD.listaIntegrantesExternos").value == ""){ 
                document.getElementById("objDD.listaIntegrantesExternos").value = x.options[i].text;
             }else{
                document.getElementById("objDD.listaIntegrantesExternos").value = document.getElementById("objDD.listaIntegrantesExternos").value + "|" + x.options[i].text; 
             }  
           }
        }
        
        x = document.getElementById("listaDocReferencias");
        document.getElementById("objDD.listReferenciados").value = "";
                   
        if (x!=null && x!= "" && x.options.length>0){
           for (var i=0; i<x.options.length;i++){
             if (document.getElementById("objDD.listReferenciados").value==""){
                document.getElementById("objDD.listReferenciados").value = x.options[i].value;    
             }else{
                 document.getElementById("objDD.listReferenciados").value = document.getElementById("objDD.listReferenciados").value + "|" + x.options[i].value; 
             }
           }  
        }
              
              
        var valor = 0;
        if (dijit.byId("objDD.iIdLegajo")){
            valor = dijit.byId("objDD.iIdLegajo").attr("value");
            if (valor == "" || valor == null){
                 valor = 0;
            }
        }else{
            valor = 0;
        }
        
        if (dijit.byId("objDD.tipoNumeracion").attr("value")!="" && dijit.byId("objDD.tipoNumeracion").attr("value")!=null && dijit.byId("objDD.tipoNumeracion").attr("value")!= "undefined" && dijit.byId("objDD.tipoNumeracion").attr("value")== "M" && TIPO_TRANSACCION != "M"){
              service.verificarDuplicidadDocumento(dijit.byId("objDocumento.tipoDocumento.idtipodocumento").attr("value"),
                                                  dijit.byId("objDD.strNroDocumento").attr("value"), UNIDAD_USUARIO, valor).addCallback(function(respuesta){
            
                    if (respuesta=="1"){
                       alert("El numero de documento ya se encuentra registrado.");
                       if(dijit.byId("dlgProgresBar")!=null){
                            dijit.byId("dlgProgresBar").hide() ;
                        }
                        
                       return;
                    }else{
                        if (respuesta == "2"){
                            alert("El expediente ya se encuentra cerrado.");
                            if(dijit.byId("dlgProgresBar")!=null){
                                dijit.byId("dlgProgresBar").hide() ;
                            }
                            return;
                        }else{
                            enviarArchivo();
                            dijit.byId("btnRegistrarDocumentoTop").attr("disabled", true);
                            dijit.byId("btnRegistrarDocumentoBottom").attr("disabled", true);
                        }    
                    }                                    
           
              });
                       
        }else{
            if (valor == 0){
                if(campos==undefined || campos==""){
                  enviarArchivo();
                  dijit.byId("btnRegistrarDocumentoTop").attr("disabled", true);
                  dijit.byId("btnRegistrarDocumentoBottom").attr("disabled", true);
                }
            }else{
                 service.verificarCerrarLegajo(valor).addCallback(function(respuesta){
                      if (respuesta == "1"){
                          alert("El expediente ya se encuentra cerrado.");
                          if(dijit.byId("dlgProgresBar")!=null){
                            dijit.byId("dlgProgresBar").hide() ;
                          }
                          return;
                      }
                      
                      if (respuesta == "2"){
                          alert("Ha ocurrido un error inesperado. Vuelva a intentarlo en un momento");
                          if(dijit.byId("dlgProgresBar")!=null){
                            dijit.byId("dlgProgresBar").hide() ;
                          }
                          return;
                      }
                      
                      enviarArchivo();
                      dijit.byId("btnRegistrarDocumentoTop").attr("disabled", true);
                      dijit.byId("btnRegistrarDocumentoBottom").attr("disabled", true);
                 });
            }
        }    
};


function tipoDocumentoSicorRequerido(tipo , requerido){
     if (tipo=="402"){    
        dijit.byId("objDD.iIdMateria").attr("required", requerido);
        dijit.byId("objDD.iIdInfraestructura").attr("required", requerido);
     }
     if (tipo=="405"){
        dijit.byId("objDD.strObjetivo").attr("required", requerido);
        if (dijit.byId("objDD.strFechaReunion")){
             dijit.byId("objDD.strFechaReunion").attr("required",requerido);
         }
         if (dijit.byId("objDD.strHoraReunion")){
             dijit.byId("objDD.strHoraReunion").attr("required",requerido);
         }
        dijit.byId("objDD.strLugar").attr("required", requerido);
     }
}

var mostrarSicor = function(idTipoDocumento, flag){
    if (flag=="1"){
        if (idTipoDocumento=="402"){
            dojo.byId("tbSicor").style.display = "";
            dojo.byId("tbSicor_1").style.display = "";
            dojo.byId("tbSicor_2").style.display = "none";
            dojo.byId("tbSicor_3").style.display = "none"; 
            tipoDocumentoSicorRequerido("402", true);
            tipoDocumentoSicorRequerido("405", false);
            dijit.byId("objDD.idInstitucionSicor").attr("required", true);
        }
        if (idTipoDocumento=="405"){
            dojo.byId("tbSicor").style.display = "";
            dojo.byId("tbSicor_2").style.display = "";
            dojo.byId("tbSicor_1").style.display = "none";
            dojo.byId("tbSicor_3").style.display = "none";
            tipoDocumentoSicorRequerido("405", true);
            tipoDocumentoSicorRequerido("402", false);
            dijit.byId("objDD.idInstitucionSicor").attr("required", true);
        }
        if (idTipoDocumento=="404"){
            dojo.byId("tbSicor_3").style.display = "";
            dojo.byId("tbSicor").style.display = "none";
            dojo.byId("tbSicor_1").style.display = "none";
            dojo.byId("tbSicor_2").style.display = "none";
            tipoDocumentoSicorRequerido("405", false);
            tipoDocumentoSicorRequerido("402", false);
            dijit.byId("objDD.idInstitucionSicor").attr("required", false);
        }
    }else{
            dojo.byId("tbSicor").style.display = "none";
            dojo.byId("tbSicor_1").style.display = "none";
            dojo.byId("tbSicor_2").style.display = "none";
            dojo.byId("tbSicor_3").style.display = "none";
            tipoDocumentoSicorRequerido("404", false);
            tipoDocumentoSicorRequerido("402", false);
            tipoDocumentoSicorRequerido("405", false);   
            dijit.byId("objDD.idInstitucionSicor").attr("required", false);
    }
};

var tipoHasChanged = function(){
    var unidadPorDefecto   = UNIDAD_USUARIO;
    
    if (dijit.byId('objDD.proyecto').attr("value") == DOCUMENTO_PROYECTO){
        Siged.Commons.hideWidget("tbNumeracionDocumento");
        Siged.Commons.hideWidget("trNroDocumento");
        Siged.Commons.hideWidget("divChkEnumerarDocumento"); 
        dijit.byId("objDD.strNroDocumento").attr("value", "");
        dijit.byId("chkEnumerarDocumento").attr("checked", false);
        //////// jc ultimo funciona caso manual q no tiene numeracion////
        Siged.Commons.hideWidget("divNroDocumento");
        dijit.byId("objDD.strNroDocumento").attr("required", false);
    }else{
        if (dijit.byId("idtipodocumento").attr("value")!=""){
          Siged.Documento.Numeracion.load(dijit.byId("idtipodocumento").attr("value"), unidadPorDefecto, "objDD.strNroDocumento", "objDD.tipoNumeracion", document.getElementById("documentoEnumerado").value,
                                          document.getElementById("nroDocumento").value);                                    
        }                                    
    }
};

var tipoConcesionarioHasChanged = function (idConcesionario){
    if (!Siged.String.isEmpty(idConcesionario) && !Siged.String.isEmpty(dijit.byId("objDocumento.tipoDocumento.idtipodocumento").attr("value"))){
        var banderaDocumentoResTributario=false;
        for(i=0;i<arrStoreResultadoReqTributario.length;i++){
            if (arrStoreResultadoReqTributario[i]==dijit.byId("objDocumento.tipoDocumento.idtipodocumento").attr("value"))
              banderaDocumentoResTributario = true;  
        }
        if(banderaDocumentoResTributario)
        {
            Siged.Documento.Numeracion.loadConcesionario(dijit.byId("objDocumento.tipoDocumento.idtipodocumento").attr("value"), 
                                       idConcesionario,
                                       "objDD.strNroDocumento", "objDD.tipoNumeracion", 
                                       document.getElementById("documentoEnumerado").value,
                                        document.getElementById("nroDocumento").value);
        }
    }
};

var tipoDocumentoHasChanged = function(idTipoDocumento) {
    var sIdTipoDocumento = new String(idTipoDocumento);
    var unidadPorDefecto   = UNIDAD_USUARIO;
    var banderaDocumentoTributario = false;
    var banderaDocumentoResTributario = false;
    var auxRequerimiento = "0";

    dijit.byId("idtipodocumento").attr("value", idTipoDocumento);
    
    for(i=0;i<arrStoreRequerimientoTributario.length;i++){
        if (arrStoreRequerimientoTributario[i]==idTipoDocumento)
          banderaDocumentoTributario = true;  
    }
    
        /////////////////////////CAMBIOS PARA RESULTADO TRIBUTARIO////////////////
    for(i=0;i<arrStoreResultadoReqTributario.length;i++){
        if (arrStoreResultadoReqTributario[i]==idTipoDocumento)
          banderaDocumentoResTributario = true;  
    }

    
    if (!Siged.String.isEmpty(sIdTipoDocumento) && dijit.byId('objDD.proyecto').attr("value") != DOCUMENTO_PROYECTO) {
       if (banderaDocumentoTributario){
           auxRequerimiento = "1";
           dijit.byId("objDD.anioFiscal").attr("required", true);
           document.getElementById("fieldAnioFiscal").style.display = '';

           dijit.byId("objDD.concesionario").attr("required", true);
           document.getElementById("fieldConcesionario").style.display = '';
           if (dijit.byId("objDD.concesionario").attr("value")!=""){
               Siged.Documento.Numeracion.loadConcesionario(sIdTipoDocumento, 
                                               dijit.byId("objDD.concesionario").attr("value") ,
                                               "objDD.strNroDocumento", "objDD.tipoNumeracion", 
                                               document.getElementById("documentoEnumerado").value,
                                               document.getElementById("nroDocumento").value);
           }else{
               Siged.Commons.hideWidget("tbNumeracionDocumento");  //TODO 
               Siged.Commons.hideWidget("trNroDocumento");  //SIN EL TITULO
               Siged.Commons.hideWidget("divChkEnumerarDocumento"); //CHECK
               Siged.Commons.hideWidget("divNroDocumento"); //CAJA NUMERO
           }
       }else{
          dijit.byId("objDD.anioFiscal").attr("required", false);
          document.getElementById("fieldAnioFiscal").style.display = 'none';
 
          dijit.byId("objDD.concesionario").attr("required", true); 
          document.getElementById("fieldConcesionario").style.display = ''; 
          
          Siged.Documento.Numeracion.load(sIdTipoDocumento, unidadPorDefecto, "objDD.strNroDocumento", "objDD.tipoNumeracion", document.getElementById("documentoEnumerado").value,
                                            document.getElementById("nroDocumento").value);
          if (document.getElementById("documentoEnumerado").value!='' && document.getElementById("documentoEnumerado").value == 'S'){
             dijit.byId("chkEnumerarDocumento").attr("checked", true);
          }   
       } 
       
       
       /////////////
       if(banderaDocumentoResTributario){
           auxRequerimiento = "1";
           dijit.byId("objDD.numeroReqTributario").attr("required", true);
           document.getElementById("fieldRequerimientoTributario").style.display = '';
           dijit.byId("objDD.strNroDocumento").attr("readOnly", true);
           
           dijit.byId("objDD.concesionario").attr("required", false); 
           document.getElementById("fieldConcesionario").style.display = 'none'; 
           
           var nroExpediente = dijit.byId("objDD.iIdExpediente").attr("value");
           var storeResultado = new dojo.data.ItemFileWriteStore({url: "autocompletarNumReqTributario.action?sNroExpediente=" + nroExpediente}); 
           storeResultado.close();
           storeResultado.fetch();
           dijit.byId("objDD.numeroReqTributario").store = storeResultado;
           
       }else{
           dijit.byId("objDD.numeroReqTributario").attr("required", false);
           document.getElementById("fieldRequerimientoTributario").style.display = 'none';
           dijit.byId("objDD.strNroDocumento").attr("readOnly", false);
           
           var storeResultado = new dojo.data.ItemFileWriteStore({url: "autocompletarNumReqTributario.action"}); 
           storeResultado.close();
           storeResultado.fetch();
           dijit.byId("objDD.numeroReqTributario").store = storeResultado;
       }
       
       if(auxRequerimiento == "1")
       {
           dijit.byId("objDD.strUnidad").attr("required", false);
           document.getElementById("fieldUnidad").style.display = 'none';
       }else{
          dijit.byId("objDD.strUnidad").attr("required", true);
          document.getElementById("fieldUnidad").style.display = '';
       }

       ///////////////

    }  
   
    if (dijit.byId('objDD.proyecto').attr("value") == DOCUMENTO_PROYECTO){
        Siged.Commons.hideWidget("tbNumeracionDocumento");
        Siged.Commons.hideWidget("trNroDocumento");
        Siged.Commons.hideWidget("divChkEnumerarDocumento");
        dijit.byId("objDD.strNroDocumento").attr("value", "");
        dijit.byId("chkEnumerarDocumento").attr("checked", false);
    }
    
    if (document.getElementById("tipoDocumento").value==""){
        service.getFlagTipoDocumento(idTipoDocumento).addCallback(function(tipoDocumentoJSon){
           if (tipoDocumentoJSon.externo=="1"){
               mostrarInstitucion("1"); //JC-NO OBLIGATORIO PERSONA
           }else{
               mostrarInstitucion("0");
           }
           
           if (idTipoDocumento=='404' || idTipoDocumento=='402' || idTipoDocumento=='405'){
               mostrarSicor(idTipoDocumento, "1");
           }else{
               mostrarSicor(idTipoDocumento, "0");
           }
        });
    }    
    
    document.getElementById("tipoDocumento").value = "";
};

updateStore = function(sParametro, sObjeto) {
   console.log("[updateStore] - sParametro [" + sParametro + "] sObjeto [" + sObjeto + "]");
   if (sParametro == undefined || sParametro == "" && sObjeto != "cliente") {
      return;
   }
   if (sObjeto == "cliente") {
      changeStore("objDocumento.expediente.cliente.numeroIdentificacion", "obtenerDataClienteCxb.action?sTipoIdentificacion=" + sParametro);
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

var showRegistroClienteFinal = function() {

   resetRegistroCliente();
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
   }

   dijit.byId("dlgNuevoCliente").show();
};


var showWindowBusquedaExpediente = function() {
   resetBusquedaExpediente();
   dijit.byId("dlgBusquedaExpediente").show();
};


var crearBotonAdjuntar = function() {
   dojox.embed.Flash.available = 0;
   if (TIPO_TRANSACCION!="M"){
        var boton=new dijit.form.Button({
           iconClass:	"siged16 iconoAdjuntar",
           type: "button",
           label: "Adjuntar"
        },"btn");

         
        var upload= new dojox.form.FileUploader({
           button:	boton,
           uploadUrl: "doUploadFile.action",
           uploadOnChange: true,
           postData: {

              iIdUpload: "2"
           },
           selectMultipleFiles: false,
           htmlFieldName:"upload"
        });

        
        dojo.connect(upload, "onComplete", function(){
           dojo.xhrPost({

              url: "showUploadFilesDojo2.action",
              content: {
                 iIdUpload: 2
              },
              mimetype: "text/html",
              handle: function(data) {
                 dojo.byId('divShowFile' + 2).innerHTML = data;
              }
           });

           upload.value = "";
             //this.upload = null;
        });
   }

};


var filterPara = function(e) {
   dijit.byId("grdPara").setQuery({nombres : "*" + this.attr("value") + "*"}, {ignoreCase: true});
};

var resetPara = function() {
   dijit.byId("txtFiltroPara").attr("value", "");
   dojo.byId("txtFiltroPara").focus();
   dijit.byId("grdPara").showMessage("");
   dijit.byId("grdPara").setStore(new dojo.data.ItemFileReadStore({url: "buscarPara.action?mode=" + MODE}));
   dijit.byId("grdPara").focus.next();
   dijit.byId("grdPara").render();
};


var selectContactoFromGrdPara = function(e) {
       if (e.rowIndex == undefined) {
		return;
	}

	var id = dijit.byId("grdPara").getItem(e.rowIndex).id;
	var nombre = dijit.byId("grdPara").getItem(e.rowIndex).nombres;

	if(nombre!=null && nombre!="" && id!=null && id!=""){
		var existe=false;

		dojo.query(".apoyo").forEach(function(node){
			if(node.value == id){
				existe = true;
			}
		});

		if(!existe){
			var copia="<span class=\"copia\">";
				copia+="<span>"+nombre+"</span>";
				copia+="<input type=\"hidden\" class=\"apoyo\" name=\"apoyo\" value=\""+id+"\" />";
				copia+="<a href=\"#\" class=\"quitar\"><img src=\"images/eliminar.gif\" alt=\"X\" /></a></span>";
			$("#apoyoNuevoDocUF").append(copia);
			$(".quitar").click(function(){
				$(this).parent().remove();
			});
		}
	}

	dijit.byId("dlgPara").hide();
};



function activarTextNumeracion(){
        if(dijit.byId("objDD.strNroDocumento")){
		if(dijit.byId("chkEnumerarDocumento").attr("checked")){
			dijit.byId("objDD.strNroDocumento").attr("readOnly", false);
		}else{
			dijit.byId("objDD.strNroDocumento").attr("value", "");
			dijit.byId("objDD.strNroDocumento").attr("readOnly", true);
		}
	}
}

dojo.addOnLoad(function() {
       new dijit.form.TextBox({
	    id : "sNroExpediente",
	    jsId : "sNroExpediente",
	    name : "sNroExpediente",
	    readOnly : true,
	    trim : true
	},"sNroExpediente");

        /*
	new dijit.form.TextBox({
		id : "idproceso",
	    jsId : "idproceso",
	    name : "idproceso",
	    style : "display:none;"
	},"idproceso"); */

	new dijit.form.TextBox({
		id : "objDD.iIdExpediente",
		jsId : "objDD.iIdExpediente",
		name : "objDD.iIdExpediente",
		style : "display:none;"
	}, "objDD.iIdExpediente");

	new dijit.form.ValidationTextBox({
	    id : "objDD.asuntoExpediente",
	    jsId : "objDD.asuntoExpediente",
	    name : "objDD.asuntoExpediente",
	    invalidMessage : "Ingrese un Asunto",
	    regExp : ".{1,1500}",
	    required : true,
            maxLength : "1500",
	    uppercase : true,
            style : "width:500px",
	    trim : true
	}, "objDD.asuntoExpediente");
        
        new dijit.form.ValidationTextBox({
             id : "objDD.strAsunto",
             jsId :"objDD.strAsunto",
             name:  "objDD.strAsunto",
             invalidMessage:"Ingrese un Asunto",
             regExp: ".{1,1500}", 
             uppercase : true, 
             required: true,
             style: "width:500px",
             maxLength : "1500",
             trim:"true"
	}, "objDD.strAsunto");
        
         new dijit.form.ValidationTextBox({
             id : "objDD.strLugar",
             jsId :"objDD.strLugar",
             name:  "objDD.strLugar",
             invalidMessage:"Ingrese el Lugar",
             regExp: ".{1,300}", 
             required: true,
             style: "width:500px",
             maxLength : "100",
             trim:"true"
	}, "objDD.strLugar");
        
        new dijit.form.ValidationTextBox({
             id : "strNombres",
             jsId :"strNombres",
             name:  "strNombres",
             invalidMessage:"Ingrese el Nombre",
             regExp: ".{1,300}", 
             required: false,
             style: "width:165px",
             maxLength : "50",
             trim:"true"
	}, "strNombres");
        
          new dijit.form.ValidationTextBox({
             id : "strApellidos",
             jsId :"strApellidos",
             name:  "strApellidos",
             invalidMessage:"Ingrese los Apellidos",
             regExp: ".{1,300}", 
             required: false,
             style: "width:166px",
             maxLength : "50",
             trim:"true"
	}, "strApellidos");
        
         new dijit.form.ValidationTextBox({
             id : "objDD.strObjetivo",
             jsId :"objDD.strObjetivo",
             name:  "objDD.strObjetivo",
             invalidMessage:"Ingrese un Objetivo",
             regExp: ".{1,300}", 
             required: true,
             style: "width:500px",
             maxLength : "100",
             trim:"true"
	}, "objDD.strObjetivo");
        
        
         new dijit.form.ValidationTextBox({
             id : "objDD.strNroDocumento",
             jsId :"objDD.strNroDocumento",
             maxLength : "35",
             name:  "objDD.strNroDocumento",
             invalidMessage:"Ingrese un Nro de Documento",
             regExp: ".{1,40}",
             required: false,
             readOnly : false,
             style: "width:140px",
             trim:"true"
	}, "fsNroDocumento");
    
            new dijit.form.ValidationTextBox({
             id : "objDD.strObservacion",
             jsId :"objDD.strObservacion",
             name:  "objDD.strObservacion",
             regExp: ".{1,300}",
             required: false,
             maxLength : "150",
             style: "width:500px",
             uppercase : true,
             trim:"true"
	}, "fsObservacionesDocumento");
    
    
	new dijit.form.ValidationTextBox({
	    id : "objDD.observacionExpediente",
	    jsId : "objDD.observacionExpediente",
	    name : "objDD.observacionExpediente",
            uppercase : true,
	    regExp : ".{1,300}",
	    required : false,
	    style : "width:500px",
            maxLength : "150",
	    trim : true
	}, "objDD.observacionExpediente");

	new dijit.form.CheckBox({
		id : "chkEnumerarDocumento",
		jsId : "chkEnumerarDocumento",
	    onClick : activarTextNumeracion,
            checked: true,
	    name : "objDD.enumerarDocumento",
	    value : "S"
	}, "chkEnumerarDocumento");
        
         new dijit.form.RadioButton({ 
           checked: true,   
           value: "0",
           name: "objDD.idInternoExterno",
           onClick: function(){mostrarInstitucion(this.value);}
         }, "fsInterno");
      
        new dijit.form.RadioButton({
          value: "1",
          name: "objDD.idInternoExterno",
          onClick: function(){mostrarInstitucion(this.value);}
        },"fsExterno");
         
         new dijit.form.RadioButton({ 
           checked: true,   
           value: "0",
           name: "objDD.idTipoCliente",
           onClick: function(){mostrarTipoCliente(this.value);}
         }, "fsClienteInstitucion");
         
         new dijit.form.RadioButton({   
           value: "2",
           name: "objDD.idTipoCliente",
           onClick: function(){mostrarTipoCliente(this.value);}
         }, "fsClienteEmpresa");
      
      
        new dijit.form.RadioButton({
          value: "1",
          name: "objDD.idTipoCliente",
          onClick: function(){mostrarTipoCliente(this.value);}
        },"fsClientePersona");
         
   Siged.Forms.combobox("fsSerie", {
      id             : "objDD.iIdSerie",
      jsId           : "objDD.iIdSerie",
      name           : "objDD.iIdSerie",
      searchAttr     : "label",
      searchDelay    : 650,
      queryExpr      : "*${0}*",
      autoComplete   : false,
      hasDownArrow   : true,
      highlightMatch : "all",
      required       : true,
      style          : "width:500px;",
      invalidMessage : "Seleccione una serie",
      storeUrl       : "autocompletarSerie.action"
   });
   
   Siged.Forms.combobox("fsRequerimientoTributario", {
      id             : "objDD.concesionario",
      jsId           : "objDD.concesionario",
      name           : "objDD.concesionario",
      searchAttr     : "label",
      searchDelay    : 650,
      queryExpr      : "*${0}*",
      autoComplete   : false,
      hasDownArrow   : true,
      highlightMatch : "all",
      required       : true,
      style          : "width:500px;",
      onChange       : tipoConcesionarioHasChanged,
      invalidMessage : "Seleccione un concesionario",
      storeUrl       : "autocompletarConcesionaria.action?iWithoutStor=0"
   });
   
   Siged.Forms.combobox("fsResultadoTributario", {
      id             : "objDD.numeroReqTributario",
      jsId           : "objDD.numeroReqTributario",
      name           : "objDD.numeroReqTributario",
      searchAttr     : "label",
      searchDelay    : 650,
      queryExpr      : "*${0}*",
      autoComplete   : false,
      hasDownArrow   : true,
      highlightMatch : "all",
      required       : true,
      style          : "width:500px;",
      onChange       : tipoResultadoRequerimientoHasChanged,
      invalidMessage : "Seleccione un número de requerimiento tributario",
      storeUrl       : "autocompletarNumReqTributario.action"
   });
   
   Siged.Forms.combobox("fsAnioFiscal", {
      id             : "objDD.anioFiscal",
      jsId           : "objDD.anioFiscal",
      name           : "objDD.anioFiscal",
      searchAttr     : "label",
      searchDelay    : 650,
      queryExpr      : "*${0}*",
      autoComplete   : false,
      hasDownArrow   : true,
      highlightMatch : "all",
      required       : true,
      style          : "width:165px;",
      onChange       : anioFiscalHasChanged,
      invalidMessage : "Seleccione un año fiscal",
      storeUrl       : "autocompletarAnioFiscal.action"
   });
   
   Siged.Forms.combobox("fsUnidad", {
      id             : "objDD.strUnidad",
      jsId           : "objDD.strUnidad",
      name           : "objDD.strUnidad",
      searchAttr     : "label",
      searchDelay    : 550,
      queryExpr      : "*${0}*",
      autoComplete   : false,
      hasDownArrow   : true,
      highlightMatch : "all",
      required       : true, 
      style          : "width:400px;",
      invalidMessage : "Seleccione una unidad",
      storeUrl       : "obtenerUnidadesEnumerar.action"
   });


   Siged.Forms.combobox("fsTipoDocumento", {
      id             : "objDocumento.tipoDocumento.idtipodocumento",
      jsId           : "objDocumento.tipoDocumento.idtipodocumento",
      name           : "objDocumento.tipoDocumento.idtipodocumento",
      searchAttr     : "label",
      searchDelay    : 650,
      queryExpr      : "*${0}*",
      autoComplete   : false,
      hasDownArrow   : true,
      highlightMatch : "all",
      required       : true,
      style          : "width:403px;",
      invalidMessage : "Seleccione un tipo de Documento",
      onChange       : tipoDocumentoHasChanged,
      storeUrl       : "autocompletarAllTipoDocumento.action?iWithoutStor=0"
   });
   
 //  alert("respuesta=" + DOCUMENTO_LEGAJO);
   
   if(TIPO_TRANSACCION+"" == "A"){ 
        Siged.Forms.combobox("fsExpediente", {
           id             : "objDD.iIdLegajo",
           jsId           : "objDD.iIdLegajo",
           name           : "objDD.iIdLegajo",
           searchAttr     : "label",
           searchDelay    : 650,
           queryExpr      : "*${0}*",
           autoComplete   : false,
           hasDownArrow   : true,
           highlightMatch : "all",
           required       : false,
           style          : "width:403px;",
           invalidMessage : "Seleccione un expediente",
           storeUrl       : "autocompletarExpediente.action?idDocumento=" + DOCUMENTO_ID
        });
    }
    
   Siged.Forms.combobox("fsPrioridad", {
      id             : "objDD.prioridad",
      jsId           : "objDD.prioridad",
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
   
   Siged.Forms.combobox("fsDocumento", {
      id             : "objDD.proyecto",
      jsId           : "objDD.proyecto",
      name           : "objDD.proyecto",
      searchAttr     : "label",
      searchDelay    : 650,
      queryExpr      : "*${0}*",
      autoComplete   : false,
      hasDownArrow   : true,
      onChange       : tipoHasChanged,
      highlightMatch : "all",
      required       : true,
      style          : "width:165px;",
      storeUrl       : "autocompletarParametroActivo.action?codParametro=PROYECTO"
   });
   
             
var dataConfidencial = {
    identifier: 'value',
    items:[
       {value:'S', label:'Confidencial'},
       {value:'N', label:'No Confidencial'}
    ]
  };
  
   Siged.Forms.combobox("fsConfidencial", {
      id             : "objDD.confidencial",
      jsId           : "objDD.confidencial",
      name           : "objDD.confidencial",
      searchAttr     : "label",
      searchDelay    : 650,
      queryExpr      : "*${0}*",
      autoComplete   : false,
      hasDownArrow   : true,
      highlightMatch : "all"
   });
   
    var storeConfidencial = new dojo.data.ItemFileWriteStore({data: dataConfidencial});
    dijit.byId("objDD.confidencial").store = storeConfidencial;
    dijit.byId("objDD.confidencial").attr('value', 'N');
   
    
     /////////////////////EMPRESAS////////////////////////////////    
   
      new dijit.form.FilteringSelect({
            id:   "objDD.iIdEmpresa",
            jsId: "objDD.iIdEmpresa",
            name: "objDD.iIdEmpresa",
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
                                              //var url = 'autocompletarFiltroPersonas.action?desPersona=' + this.attr('displayedValue');
                                              var url = 'autocompletarFiltroInstitucionesxTipo.action?desInstitucion=' + this.attr('displayedValue') + '&idTipoCliente=2';
                                              this.store= new dojo.data.ItemFileWriteStore({url: url}); 
                                              this.store.close();
                                              this.store.fetch();
                                              this._startSearch(dojo.trim(this.attr('displayedValue')));
                                      }
                      }
              }
          },"fsEmpresa");
    
   
   
     new dijit.form.ValidationTextBox({
      id             : "objDD.idPersonaEmpresa",
      jsId           : "objDD.idPersonaEmpresa",
      name           : "objDD.idPersonaEmpresa",
      maxLength : "80",
      uppercase : true,
      required       : true, //JC
      style          : "width:385px;",
      invalidMessage : "Ingrese los nombres de la persona" 
   }, "fsPersonaEmpresa"); 
   
     new dijit.form.ValidationTextBox({
      id             : "objDD.codCargoPersonaEmpresa",
      jsId           : "objDD.codCargoPersonaEmpresa",
      name           : "objDD.codCargoPersonaEmpresa",
      maxLength : "80",
      required       : true, //JC
      uppercase : true,
      style          : "width:385px;",
      invalidMessage : "Ingrese el cargo de la persona" //,
      //storeUrl       : "autocompletarCargo.action"
   }, "fsCargoPersonaEmpresa"); 
   
    /////////////////////ENTIDADES////////////////////////////////    
   
   new dijit.form.FilteringSelect({
            id:   "objDD.iIdInstitucion",
            jsId: "objDD.iIdInstitucion",
            name: "objDD.iIdInstitucion",
            autoComplete: false,
            queryExpr: "*${0}*",
            searchAttr: "label",
            hasDownArrow: false,
            highlightMatch : "all",
            required       : true,
            style          : "width:385px;",
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
              },
              onChange: function(){
                    var bandera = false;
                    for(i=0;i<arrStoreTipoDocumentoPIDE.length;i++){
                          if (arrStoreTipoDocumentoPIDE[i]==dijit.byId("idtipodocumento").attr("value")){
                            bandera = true;
                            break;
                          }
                    }

                    if (bandera){
                      bandera = false; 
                      for(i=0;i<arrStoreRUCPIDE.length;i++){
                         if (arrStoreRUCPIDE[i]==dijit.byId("objDD.iIdInstitucion").attr("value")){
                             bandera = true;
                             break;
                         }
                       }    
                    }

                    if (bandera){
                        dijit.byId("objDD.idPersonaInstitucion").attr("required", true);
                        dijit.byId("objDD.codCargoPersonaInstitucion").attr("required", true);
                        dijit.byId("objDD.desUnidadOrganica").attr("required", true);
                        dijit.byId("objDD.iNroFolios").attr("required", true);
                    }else{
                        dijit.byId("objDD.idPersonaInstitucion").attr("required", false);
                        dijit.byId("objDD.codCargoPersonaInstitucion").attr("required", false);
                        dijit.byId("objDD.desUnidadOrganica").attr("required", false);
                        dijit.byId("objDD.iNroFolios").attr("required", false);
                    }
              }
          },"fsInstitucion");
  
   new dijit.form.ValidationTextBox({
      id             : "objDD.idPersonaInstitucion",
      jsId           : "objDD.idPersonaInstitucion",
      name           : "objDD.idPersonaInstitucion",
      maxLength : "80",
      required       : true, //jc
      style          : "width:385px;",
       uppercase : true,
     // onChange       : fillDataCargoPersona,
      invalidMessage : "Ingrese los nombres de la persona" 
   },"fsPersonaInstitucion"); 
   
     new dijit.form.ValidationTextBox({
      id             : "objDD.codCargoPersonaInstitucion",
      jsId           : "objDD.codCargoPersonaIntitucion",
      name           : "objDD.codCargoPersonaInstitucion",
      maxLength : "80",
      required       : true, //jc
      uppercase : true,
      style          : "width:385px;",
      invalidMessage : "Ingrese el cargo de la persona"
   },"fsCargoPersonaInstitucion"); 
   
    ///////////////////////////// PERSONA NATURAL /////////////////////////////
    new dijit.form.FilteringSelect({
      id:   "objDD.idPersonaNatural",
      jsId: "objDD.idPersonaNatural",
      name: "objDD.idPersonaNatural",
      autoComplete: false,
      queryExpr: "*${0}*",
      searchAttr: "label",
      hasDownArrow: false,
      highlightMatch : "all",
      required       : true,
      style          : "width:385px;",
      //onChange    : fillDataCargoPersonaNatural,
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
    },"fsPersonaNatural");
    
    new dijit.form.FilteringSelect({
      id:   "objDD.idInstitucionSicor",
      jsId: "objDD.idInstitucionSicor",
      name: "objDD.idInstitucionSicor",
      autoComplete: false,
      queryExpr: "*${0}*",
      searchAttr: "label",
      hasDownArrow: false,
      highlightMatch : "all",
      required       : true,
      style          : "width:385px;",
     // onChange    : fillDataCargoPersonaNatural,
      postCreate: function() {
                this.connect(this, "onKeyUp", "_onSuggest");
		this._timeKey = null;
      },
      _onSuggest: function() {
                  if(this.attr('displayedValue').length > 2) {
                                if(this.store) {
                                	var url = 'autocompletarFiltroInstituciones.action?desInstitucion=' + this.attr('displayedValue');
                                	this.store= new dojo.data.ItemFileWriteStore({url: url}); 
                			this.store.close();
                			this.store.fetch();
                                        this._startSearch(dojo.trim(this.attr('displayedValue')));
				}
		}
	}
    },"fsInstitucionSicor");

    new dijit.form.ValidationTextBox({
       id             : "objDD.codCargoPersonaNatural",
        jsId           : "objDD.codCargoPersonaNatural",
        name           : "objDD.codCargoPersonaNatural",
        maxLength : "70",
         uppercase : true,      
        required       : true,
        style          : "width:385px;",
        invalidMessage : "Ingrese el cargo del remitente" 
     }, "fsCargoPersonaNatural");
     
      new dijit.form.ValidationTextBox({
        id             : "objDD.desUnidadOrganica",
        jsId           : "objDD.desUnidadOrganica",
        name           : "objDD.desUnidadOrganica",
        maxLength : "70",
        uppercase : true,      
        required       : false,
        style          : "width:385px;",
        invalidMessage : "Ingrese la únidad orgánica" 
     }, "fsUnidadOrganica");
     
      new dijit.form.ValidationTextBox({
             id : "objDD.iPlazoDia",
             jsId :"objDD.iPlazoDia",
             name:  "objDD.iPlazoDia",
             maxLength : "2",
             required: false,
             regExp:"[0-9]{1,4}",
             style: "width:165px",
             trim:"true",
             onBlur: function(){
                if (dojo.trim(this.value)!=''){
                  service.getFechaLimite(this.value).addCallback(function(valor){
                    dijit.byId('sFechaPlazo').attr("value", valor);    
                  });
                }else{
                    dijit.byId('sFechaPlazo').attr("value", "");
                }
             }
	}, "fsPlazo");
        
        new dijit.form.TextBox({
	    id : "sFechaPlazo",
	    jsId : "sFechaPlazo",
	    name : "sFechaPlazo",
	    readOnly : true,
	    trim : true
	},"sFechaPlazo");
        
       new dijit.form.ValidationTextBox({
             id : "objDD.iNroFolios",
             jsId :"objDD.iNroFolios",
             name:  "objDD.iNroFolios",
             maxLength : "5",
             required: false,
             invalidMessage:"Ingrese nro de folios del documento",
             regExp:"[0-9]{1,5}",
             style: "width:70px",
             trim:"true"
	}, "fsNroFolios");  
     
     Siged.Forms.combobox("fsInfraestructura", {
      id             : "objDD.iIdInfraestructura",
      jsId           : "objDD.iIdInfraestructura",
      name           : "objDD.iIdInfraestructura",
      searchAttr     : "label",
      searchDelay    : 600,
      queryExpr      : "*${0}*",
      autoComplete   : false,
      hasDownArrow   : true,
      highlightMatch : "all",
      required       : true,
      style          : "width:335px;",
      invalidMessage : "Seleccione una infraestructura",
      storeUrl       : "autocompletarParametroActivo.action?codParametro=INFRAESTRUCTURA"
     });
     
      Siged.Forms.combobox("fsMateria", {
      id             : "objDD.iIdMateria",
      jsId           : "objDD.iIdMateria",
      name           : "objDD.iIdMateria",
      searchAttr     : "label",
      searchDelay    : 600,
      queryExpr      : "*${0}*",
      autoComplete   : false,
      hasDownArrow   : true,
      highlightMatch : "all",
      required       : true,
      style          : "width:335px;",
      invalidMessage : "Seleccione una materia",
      storeUrl       :  "autocompletarParametroActivo.action?codParametro=MATERIA"
   });
     
   crearBotonAdjuntar();
   if(TIPO_TRANSACCION+"" == "A"){
     llenarCamposReferencia();
   }else{
     if(TIPO_TRANSACCION+"" == "M"){ 
       llenarCampos();            
     }
     if(TIPO_TRANSACCION+"" == "N"){
        dijit.byId("objDD.prioridad").attr("value",PRIORIDAD_NORMAL);   
        dijit.byId("objDD.proyecto").attr("value",DOCUMENTO_FINAL);
        dijit.byId("objDD.strUnidad").attr("value",UNIDAD_USUARIO);
        dijit.byId("objDD.concesionario").attr("value",CONCESIONARIA_INI);
        dijit.byId("objDD.iIdSerie").attr("value",SERIE_INI);
        dojo.byId("tbExpediente").style.display = "none";
     }
   }      
});
