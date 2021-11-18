Siged.Documento.Numeracion = {};

Siged.Documento.Numeracion.load = function(idTipoDocumento, idUnidad, txtNroDocumento, txtTipoNumeracion, enumerado, valor) {
 
   Siged.Commons.showWidget("tbNumeracionDocumento");  //TODO
   Siged.Commons.showWidget("trNroDocumento");  //SIN EL TITULO
   Siged.Commons.hideWidget("divChkEnumerarDocumento"); //CHECK
   Siged.Commons.hideWidget("divNroDocumento"); //CAJA NUMERO
   service.findNumeracionDocumento(parseInt(idTipoDocumento), parseInt(idUnidad)).addCallback(function(JSON) {
         if (!JSON.tipo || typeof(JSON.tipo) == "undefined"){
         	if (idTipoDocumento == TIPO_INFORME_CONJUNTO || idTipoDocumento == TIPO_MEMORANDO_CONJUNTO){
         		Siged.Documento.Numeracion.validateNroDocumento(txtNroDocumento, true);
                        dijit.byId(txtNroDocumento).attr("value", "");
                        dijit.byId(txtTipoNumeracion).attr("value", "A");
                        
                        if (enumerado!=null && enumerado !="" && enumerado == "S"){
                           dijit.byId("objDocumento.tipoDocumento.idtipodocumento").attr("readOnly", true);
                           dijit.byId("objDD.proyecto").attr("readOnly", true);
                           Siged.Commons.hideWidget("tbNumeracionDocumento");
                           Siged.Commons.hideWidget("trNroDocumento");
                        }else{
                            Siged.Commons.showWidget("divChkEnumerarDocumento");
                            dijit.byId("chkEnumerarDocumento").attr("checked", true); //JC
                        }          
		}else{
                        //JC
                        Siged.Commons.showWidget("divNroDocumento");
			
                        Siged.Documento.Numeracion.validateNroDocumento(txtNroDocumento, false);
                        dijit.byId(txtTipoNumeracion).attr("value", "M");
                        if (enumerado!=null && enumerado !="" && enumerado == "S"){
                          dijit.byId(txtNroDocumento).attr("value", valor);
                          dijit.byId("objDD.proyecto").attr("readOnly", true);
                        }else{
                          dijit.byId(txtNroDocumento).attr("value", "");
                        }   
		}   
	}else{
                if (JSON.tipo == "A"){
                        Siged.Documento.Numeracion.validateNroDocumento(txtNroDocumento, true);
                        dijit.byId(txtNroDocumento).attr("value", "");
                        dijit.byId(txtTipoNumeracion).attr("value", "A");
                        
                        if (enumerado!=null && enumerado !="" && enumerado == "S"){
                           dijit.byId("objDocumento.tipoDocumento.idtipodocumento").attr("readOnly", true);
                           dijit.byId("objDD.proyecto").attr("readOnly", true);
                           Siged.Commons.hideWidget("tbNumeracionDocumento");
                           Siged.Commons.hideWidget("trNroDocumento");
                        }else{
                           Siged.Commons.showWidget("divChkEnumerarDocumento");
                           dijit.byId("chkEnumerarDocumento").attr("checked", true); //JC
                        }
	        }else{
                        Siged.Documento.Numeracion.validateNroDocumento(txtNroDocumento, false); 
                        dijit.byId(txtTipoNumeracion).attr("value", JSON.tipo);
                        Siged.Commons.showWidget("divNroDocumento");
                        if (enumerado!=null && enumerado !="" && enumerado == "S"){
		  	  dijit.byId(txtNroDocumento).attr("value", valor);
                          dijit.byId("objDD.proyecto").attr("readOnly", true);
                        }else{
                          dijit.byId(txtNroDocumento).attr("value", "");  
                        } 
		}
	}	 
   });
};


Siged.Documento.Numeracion.loadConcesionario = function(idTipoDocumento, idConcesionario, txtNroDocumento, txtTipoNumeracion, enumerado, valor) {
 
   var anioFiscal=0; 
   Siged.Commons.showWidget("tbNumeracionDocumento");  //TODO
   Siged.Commons.showWidget("trNroDocumento");  //SIN EL TITULO
   Siged.Commons.hideWidget("divChkEnumerarDocumento"); //CHECK
   Siged.Commons.hideWidget("divNroDocumento"); //CAJA NUMERO
   
   if(dijit.byId("objDD.anioFiscal").attr("value")!="")
   {
       anioFiscal=dijit.byId("objDD.anioFiscal").attr("value");
   }
   
   service.findNumeracionDocumentoxConcesionario(parseInt(idTipoDocumento), parseInt(idConcesionario), anioFiscal).addCallback(function(JSON) {
         if (!JSON.tipo || typeof(JSON.tipo) == "undefined"){
            Siged.Commons.showWidget("divNroDocumento");		
            Siged.Documento.Numeracion.validateNroDocumento(txtNroDocumento, false);
            dijit.byId(txtTipoNumeracion).attr("value", "M");
            if (enumerado!=null && enumerado !="" && enumerado == "S"){
                dijit.byId(txtNroDocumento).attr("value", valor);
                dijit.byId("objDD.proyecto").attr("readOnly", true);
            }else{
                dijit.byId(txtNroDocumento).attr("value", "");
            }  	   
	}else{
                if (JSON.tipo == "A"){
                        Siged.Documento.Numeracion.validateNroDocumento(txtNroDocumento, true);
                        dijit.byId(txtNroDocumento).attr("value", "");
                        dijit.byId(txtTipoNumeracion).attr("value", "A");
                        
                        if (enumerado!=null && enumerado !="" && enumerado == "S"){
                           dijit.byId("objDocumento.tipoDocumento.idtipodocumento").attr("readOnly", true);
                           dijit.byId("objDD.proyecto").attr("readOnly", true);
                           Siged.Commons.hideWidget("tbNumeracionDocumento");
                           Siged.Commons.hideWidget("trNroDocumento");
                        }else{
                           Siged.Commons.showWidget("divChkEnumerarDocumento");
                           dijit.byId("chkEnumerarDocumento").attr("checked", true); //JC
                        }
	        }else{
                        Siged.Documento.Numeracion.validateNroDocumento(txtNroDocumento, false); 
                        dijit.byId(txtTipoNumeracion).attr("value", JSON.tipo);
                        Siged.Commons.showWidget("divNroDocumento");
                        if (enumerado!=null && enumerado !="" && enumerado == "S"){
		  	  dijit.byId(txtNroDocumento).attr("value", valor);
                          dijit.byId("objDD.proyecto").attr("readOnly", true);
                        }else{
                          dijit.byId(txtNroDocumento).attr("value", "");  
                        } 
		}
	}	 
   });
};

function limpiar(componente) {
	
	var d = document.getElementById(componente);
	while ( !Siged.String.isEmpty(d) && d.hasChildNodes())
		d.removeChild(d.firstChild);

	document.getElementById("id"+componente).value="";
	dijit.byId("id"+componente).attr("value", "");
	dijit.byId("id"+componente).attr("displayedValue", "");	
}

Siged.Documento.Numeracion.resetNroDocumento = function(chkEnumerarDocumento, txtNroDocumento) {
   if (!Siged.Commons.thereIsWidget(chkEnumerarDocumento) || !Siged.Commons.thereIsWidget(txtNroDocumento)) {
      return;
   }

   dijit.byId(chkEnumerarDocumento).attr("checked", false);
   dijit.byId(txtNroDocumento).attr("value", "");
}

Siged.Documento.Numeracion.validateNroDocumento = function(txtNroDocumento, upToCheckBox) {
   if (!dijit.byId(txtNroDocumento) || typeof(dijit.byId(txtNroDocumento)) == "undefined") {
      console.error("(Siged.Documento.Numeracion.validateNroDocumento) No existe el widget [%s]", txtNroDocumento);
      return;
   }
   
   dijit.byId(txtNroDocumento).attr("readOnly", (upToCheckBox ? !dijit.byId("chkEnumerarDocumento").attr("checked") : upToCheckBox));
   dijit.byId(txtNroDocumento).attr("required", !upToCheckBox);
 };
