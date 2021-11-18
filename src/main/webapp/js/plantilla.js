var g_tipoNumeracion = null;
//dojo.require("dojo.rpc.JsonService");
// esto de aca esta en dojo, pero no parece funcoinar en ie
/*var llenarFormulario=function(){
	dojo.query("#tabla tr").forEach(function(tr){
		if(tr.id==""){
			dojo.query(tr).orphan();
		}
	});
	//var service = new dojo.rpc.JsonService("SMDAction.action");
	//var defered = service.getCamposPorPlantilla(document.getElementsByName("tipoDocumento")[0].value);
	var campos=new dojo.data.ItemFileReadStore({url: "autocompletarCampos.action?tipoDocumento="+document.getElementsByName("tipoDocumento")[0].value});
	defered.addCallback(function(JSON){
		var tabla=document.getElementById("tabla");
		dojo.forEach(JSON.items,function(item){
			//TODO soportar mas tipos de campos
			var input="";
			if(item.tipo=="TX")
				input="<input name=\"valor"+item.idcampo+"\" type=\"text\" class=\"cajaMontoTotal\" />";
			else if(item.tipo=="TA")
				input="<textarea name=\"valor"+item.idcampo+"\" cols=\"50\" rows=\"10\" class=\"cajaMontoTotal\"></textarea>";
			var tr=dojo.create("tr",null,tabla);
			dojo.create("td",null,tr);
			dojo.create("td",{innerHTML: item.descripcion},tr);
			dojo.create("td",{innerHTML: input},tr);
		});
	});
};*/
var storeTipoDocumento = null ;
var llenarFormulario=function(){
	$("#tabla tr").each(function(){
		if(this.id=="")
			$(this).remove();
	});
	var url="autocompletarCampos.action";
	$.getJSON(url,
			{
            tipoPlantilla: $("input[name='tipoDocumento']").val(),
            idDocumento: $("input[name='idDocumento']").val()
         },
			function(data){
            $("#tabla tr").each(function(){
		         if(this.id=="")
			         $(this).remove();
	         });
				$.each(data.items,function(i,item){
					var input="";
               var sValor = null;

               if (item.datodocumento == "Tipo de Documento") {
                  sValor = dijit.byId("tipoDocumento").getDisplayedValue();
               } else if (item.datodocumento == "Firmante") {
                  sValor = dijit.byId("archivopendiente.firmante.idusuario").getDisplayedValue();
               } else if (item.datodocumento == "Fecha del Documento") {
                  sValor = $("input[name='fechadocumentopendiente']").val();
               } else if (item.datodocumento == "Asunto del Documento") {
                  sValor = $("input[name='asunto']").val();
               } else if (item.datodocumento == "Autor") {
                  sValor = $("input[name='autor']").val();
               }

					//TODO soportar mas tipos de campos
					if(item.tipo=="TX"){
                  if (sValor == null) {
                     input="<input name=\"valor"+item.idcampo+"\" type=\"text\" class=\"cajaMontoTotal\" value=\""+item.valor+"\" />";
                  } else {
                     input="<input name=\"valor"+item.idcampo+"\" type=\"text\" class=\"cajaMontoTotal\" value=\"" + sValor + "\" readonly />";
                  }
					}
               else if(item.tipo=="TA"){
                  if (sValor == null) {
                     input="<textarea name=\"valor"+item.idcampo+"\" cols=\"50\" rows=\"10\" class=\"cajaMontoTotal\">"+item.valor+"</textarea>";
                  } else {
                     input="<textarea name=\"valor"+item.idcampo+"\" cols=\"50\" rows=\"10\" class=\"cajaMontoTotal\" readonly>" + sValor + "</textarea>";
                  }
					}
					var texto="<tr>" +
							"<td></td>" +
							"<td>"+item.descripcion+"</td>" +
							"<td>"+input+"</td></tr>";
					$("#tabla").append(texto);
				});
			}
	);

	actualizarFirmante();

};

var replaceAll = function ( text, busca, reemplaza )
{while (text.toString().indexOf(busca) != -1){
	text = text.toString().replace(busca,reemplaza);
	}
return text;
};

var guardar=function(){
	var tipo=$("input[name='tipoDocumento']").val();
	var asunto=$("input[name='asunto']").val();

	var firmante=$("input[name='firmante']").val();
	var autor=$("input[name='autor']").val();
	var nroDocumento=$("input[name='nroDocumento']").val();
	var fechadocumentopendiente=$("input[name='fechadocumentopendiente']").val();
	var  enumerarDocumento = document.getElementById("archivopendiente.enumerarDocumento").checked
	//alert(enumerarDocumento);

	if(tipo!=""){
		//document.forms[0].action="doPlantilla_guardarPendiente.action";
		//window.open(document.forms[0].action,"","");
		//document.forms[0].submit();
		//return true;
		var data ="?tipoDocumento="+tipo;
	    data+="&idDocumento="+$("input[name='idDocumento']").val();
	    var proceso=document.getElementById("idproceso");
	    if(proceso!=null && proceso.value!=""){
	    	data+="&idproceso="+proceso;
	    }
	    dojo.query(".cajaMontoTotal").forEach(function(campo){
	    	if(campo.name.indexOf("valor")>-1 && campo.value!=""){
	    		//campo.value = replaceAll (campo.value , "\n","\\n" ) ;
	    		data+="&"+campo.name+"="+Base64.encode(campo.value);
	    	}
	    });
	    if(asunto!=""){
	      data+="&asunto="+asunto;
	    }
	    if(firmante!=""){
		      data+="&firmante="+firmante;
		    }
	    if(autor!=""){
		      data+="&autor="+autor;
		    }
	    if(nroDocumento!=""){
		      data+="&nroDocumento="+nroDocumento;
		    }
	    if(fechadocumentopendiente!=""){
		      data+="&fechadocumentopendiente="+fechadocumentopendiente;
		    }

	    if(enumerarDocumento){
		      data+="&enumerarDocumento=S";
		    }

	    console.debug("&asunto="+asunto);

	    var pagina="doPlantilla_guardarPendiente.action";
	    var plantilla="";
		if($("#tipoPlantilla")!=null)
			plantilla=$("#tipoPlantilla").val();
		if(plantilla!=null && plantilla!=""){
	    	pagina="doPlantilla_actualizarPendiente.action";
	    	data+="&idArchivoPendiente="+$("input[name='idArchivoPendiente']").val();
	    	alert("data:"+data);
	    	$.post(pagina+data,function(ret){
	    		showGridInbox(1);
	    	});
	    }
		else{
			document.location.href=pagina+data;
		}
	}
	else{
		alert("Debe ingresar un tipo de documento");
	}
	//return false;
};

var registrar=function(){
	var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes, width=800, height=700, top=50, left=50";
	var tipo=$("input[name='tipoDocumento']").val();
	var asunto=$("input[name='asunto']").val();
	//alert("tipo:"+tipo);
	var firmante=$("input[name='firmante']").val();
	var autor=$("input[name='autor']").val();
	var nroDocumento=$("input[name='nroDocumento']").val();
	var fechadocumentopendiente=$("input[name='fechadocumentopendiente']").val();
	var  enumerarDocumento =$("input[name='enumerarDocumento']:checked").length==1;

	var condestinatarios=$("input[name='condestinatarios']").val();
	var concopias=$("input[name='concopias']").val();
	//alert("enumerarDocumento:"+enumerarDocumento);
	if(tipo!=""&&asunto!=""){
		var data ="?tipoDocumento="+tipo;
	    data+="&idDocumento="+$("input[name='idDocumento']").val();
	    var proceso=document.getElementById("idproceso");
	    if(proceso!=null && proceso.value!=""){
	    	data+="&idproceso="+proceso;
	    }
	    if(firmante!=""){
		      data+="&firmante="+firmante;
		    }
	    if(autor!=""){
		      data+="&autor="+autor;
		    }
	    if(nroDocumento!=""){
		      data+="&nrodocumento="+nroDocumento;
		    }
	    if(fechadocumentopendiente!=""){
		      data+="&fechadocumentopendiente="+fechadocumentopendiente;
		    }

	    if(enumerarDocumento){
		      data+="&enumerarDocumento=S";
		    }

	    data+="&concopias="+concopias;
	    data+="&condestinatarios="+condestinatarios;
	    data+="&asunto="+asunto;
	    dojo.query(".cajaMontoTotal").forEach(function(campo){
	    	if(campo.name.indexOf("valor")>-1 && campo.value!=""){
	    		//campo.value = replaceAll (campo.value , "\n","\\n" ) ;
	    		//alert("campo :"+campo.value );


	    		data+="&"+campo.name+"="+Base64.encode( campo.value );
	    	}
	    });
	    //TODO que es esto?
	    //data+="&marcaDeAgua="+dwr.util.getValue("marcaDeAgua");
	    pagina="doPlantilla_verPrevio.action"+data;
	    window.open(pagina,"",opciones);

	 }else {

		 var mensajeError = "Debe ingresar los campos: ";

		 if(tipo==""){

			 mensajeError = mensajeError+ " tipo de documento ";
		 }
		 if(asunto==""){

			 mensajeError = mensajeError+ " asunto  ";
		 }

		 alert(mensajeError);

	 }

};


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
			//string = string.replace(/\r\n/g,"\n");
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

function enviarArchivo(){

	var data ="?tipoDocumento="+$("input[name='tipoDocumento']").val();
	var asunto=$("input[name='asunto']").val();
    data+="&idDocumento="+$("input[name='idDocumento']").val();
    data+="&idArchivoPendiente="+$("input[name='idArchivoPendiente']").val();
    //////

    var firmante=$("input[name='firmante']").val();
	var autor=$("input[name='autor']").val();
	var nroDocumento=$("input[name='nroDocumento']").val();
	var fechadocumentopendiente=$("input[name='fechadocumentopendiente']").val();
	var  enumerarDocumento =$("input[name='enumerarDocumento']:checked").length==1;

   console.debug("enumerarDocumento ["+enumerarDocumento+"]");

	var arrayDestinatarios=new Array();
	var arrayConCopia=new Array();

	$(document.getElementsByName("condestinatarios")).each(function(){
		arrayDestinatarios.push($(this).val());
	});

	$(document.getElementsByName("concopias")).each(function(){
		arrayConCopia.push($(this).val());
	});



	//alert(condestinatarios+"--"+concopias);

	if(firmante!=""){
	      data+="&firmante="+firmante;
	    }

	if(autor!=""){
	      data+="&autor="+autor;
	    }

  	if(nroDocumento!=""){
	      data+="&nrodocumento="+nroDocumento;
	    }

  	if(fechadocumentopendiente!=""){
	      data+="&fechadocumentopendiente="+fechadocumentopendiente;
	    }

  	 if(enumerarDocumento){
	      data+="&enumerardocumento=S";
	    }

   if (g_tipoNumeracion) {
       data+="&tiponumeracion="+g_tipoNumeracion;
   }

	data+="&concopias="+arrayConCopia;
	data+="&condestinatarios="+arrayDestinatarios;

    var proceso=document.getElementById("idproceso");
    if(proceso!=null && proceso.value!=""){
    	data+="&idproceso="+proceso;
    }
    dojo.query(".cajaMontoTotal").forEach(function(campo){
    	if(campo.name.indexOf("valor")>-1 && campo.value!=""){
    		data+="&"+campo.name+"="+campo.value;
    	}
    });
    if(asunto!=""){
    	data+= "&asunto="+asunto ;
    }

    pagina="doPlantilla_enviarArchivo.action";
    var plantilla="";
   // alert("aquiiii");
	if($("#tipoPlantilla")!=null) {
		plantilla=$("#tipoPlantilla").val();
		}
  //  alert("aquiiii2 2");
	if(plantilla!=null && plantilla!=""){
		$.post(pagina+data,function(ret){
			//showGridInbox(1);
			//alert("ok refrescando"); este codigo nunca se ejecuta mm ... ????
			 viewDetailCustom2($("input[name='idDocumento']").val());

			//window.close();
		});
	}
	else{
		document.location.href=pagina+data;
	}
}

var cerrar=function(){
	//alert(" ok  .. ");
	window.close();
};
var actualizarFirmante = function (){
//	alert(val);

	try {
	var tipodoc = $("input[name='tipoDocumento']").val();
	var firmante = $("input[name='firmante']").val();
   console.debug("(actualizarFirmante) tipodoc [%s] firmante [%s]", tipodoc, firmante);

	//alert(tipodoc);
	var tiponumeracion = "";
   var firmanteActual = "";
	if(tipodoc!=""){

		storeTipoDocumento.fetchItemByIdentity({identity:tipodoc
			          ,onItem: function(item) {
					   tiponumeracion=storeTipoDocumento.getValue(item, "tiponumeracion");
                  g_tipoNumeracion = tiponumeracion;
					   firmanteActual =storeTipoDocumento.getValue(item, "firmante");
			           }});

		dojo.byId("dest1").style.display = "";
		dojo.byId("dest2").style.display = "";
		dojo.byId("copy1").style.display = "";
		dojo.byId("copy2").style.display = "";

	}

	dojo.byId("textonrodoc").style.display = "none";
	dojo.byId("checkenumerar").style.display = "none";
	//console.log(firmante+"  "+idusuariosession+"  ... "+(firmante==idusuariosession)+" tiponumeracion "+tiponumeracion+" "+(tiponumeracion=="M"));

	console.log("tipo numeracion "+ tiponumeracion);
	console.log("firmante "+ firmante + " idusuariosession "+idusuariosession);
	if(firmante!=null && firmante==idusuariosession){
		//console.log("tiponumeracion:"+tiponumeracion+" bool: "+(tiponumeracion!=null&&tiponumeracion!=""&&tiponumeracion=="M"));

        if(tiponumeracion=="X"){

			dojo.byId("firmante").style.display = "none";

			//limpiar data
			dijit.byId("archivopendiente.firmante.idusuario").setValue("");
		}

		if(tiponumeracion=="A"){
		}
		if(tiponumeracion=="M"){
		}

      console.debug("firmanteActual [%s]", firmanteActual);
      if(firmanteActual=="C"){
			dojo.byId("firmante").style.display = "";
			dijit.byId("archivopendiente.firmante.idusuario").setValue(iddefaultfirmante);
		}else{
			dojo.byId("firmante").style.display = "none";
			dijit.byId("archivopendiente.firmante.idusuario").setValue(iddefaultfirmante);
		}

		dojo.byId("checkenumerar").style.display = "block";
		if (tiponumeracion!=null && tiponumeracion!="" && tiponumeracion=="M"){
			dojo.byId("textonrodoc").style.display = "block";
			dijit.byId("archivopendiente.nroDocumento").attr("disabled", true);
		}else{
			dojo.byId("textonrodoc").style.display = "none";
			dijit.byId("archivopendiente.nroDocumento").setValue("") ;
		}

	}else {

		dojo.byId("archivopendiente.enumerarDocumento").checked = false ;
		dijit.byId("archivopendiente.nroDocumento").setValue("") ;

		}

	}catch (error){
		console.debug ("error :"+error.name+" mensaje :"+error.message) ;
	}

};

function  chknroDoc(){

    var chk =  dojo.byId("archivopendiente.enumerarDocumento") ;
		var txtnrodoc = 	dijit.byId("archivopendiente.nroDocumento");
		//alert("dentro de chk "+chk.checked);
		//console.debug((txtnrodoc.attr("disabled")==true)+" .. " +txtnrodoc.attr("disabled")=="true");

 	if(chk.checked==true){
 		//alert("enabling  "+chk.checked);
		txtnrodoc.attr("value","" );
		txtnrodoc.attr("disabled", false);

 	}else{
 		//alert("disabling   "+chk.checked);
 		txtnrodoc.attr("value","" );
		txtnrodoc.attr("disabled", true);

	 }

}

function pausecomp(millis)
{
var date = new Date();
var curDate = null;

do {curDate = new Date();}
while(curDate-date < millis);
}



dojo.addOnLoad(function(){

	var cerrar=$("input[name='cerrar']").val();
	if(cerrar!=null && cerrar!=""){

		//alert(iddocumento);
		window.opener.viewDetailCustom2(iddocumento);
		window.close();

	}else{
		var plantilla="";
		if($("#tipoPlantilla")!=null)
			plantilla=$("#tipoPlantilla").val();
		new dijit.form.Form({
			action		: "doPlantilla_guardarPendiente.action",
			method		: "post"
			//onSubmit	: guardar
		},"form");

		try {
		new dijit.form.FilteringSelect({
         id           : "tipoDocumento",
         jsId           : "tipoDocumento",
		      name           : "tipoDocumento",
		      store          : new dojo.data.ItemFileReadStore({url: "autocompletarTipoDocumentoPlantilla.action?iIdUnidad="+unidadActual}) ,
		      searchAttr     : "label",
		      queryExpr      : "*${0}*",
		      autoComplete   : false,
		      hasDownArrow   : true,
		      highlightMatch : "all",
		      onChange		 : llenarFormulario,
		      invalidMessage : "Seleccione un tipo de Documento",
		      required       : true,
		      value			 : plantilla
		   }, "tipoDocumento") ;
		}catch (err){
			console.debug ("error tipoDocumento:"+err.message+" message:" + err.name) ;
		}
		var proceso=document.getElementById("idproceso");
		if(proceso!=null){
			try {
			new dijit.form.FilteringSelect({
			      name           : "idproceso",
			      store          : new dojo.data.ItemFileReadStore({
			         url: "autocompletarProceso.action"
			      }),
			      searchAttr     : "label",
			      queryExpr      : "*${0}*",
			      autoComplete   : false,
			      hasDownArrow   : true,
			      highlightMatch : "all",
			      invalidMessage : "Seleccione un proceso",
			      required       : true,
			      value          : "",
			      style          : "width:300px"
			   }, "idproceso");
			}catch (err){
				console.debug ("error idproceso:"+err.message+" message:" + err.name) ;
			}
		}
		try {
		new dijit.form.FilteringSelect({
		      store          :  new dojo.data.ItemFileReadStore({url: "autocompletarProceso1.action?sTipoDerivacion=normal&sOpcion=derivar"}) ,
		      searchAttr     : "label",
		      queryExpr      : "*${0}*",
		      autoComplete   : false,
		      hasDownArrow   : true,
		      highlightMatch : "all",
		      required       : false,
		      style          : "width:100px" ,
            id           : "archivopendiente.firmante.idusuario",
		      jsId           : "archivopendiente.firmante.idusuario",
		      name           : "firmante",
		      idAttr         :"id",
		      labelAttr      : "label" ,
		      onChange       : actualizarFirmante,
		      value  :  iddefaultfirmante
		   }, "archivopendiente.firmante.idusuario");
		}catch (err){
			console.debug ("error archivopendiente.firmante.idusuario:"+err.message+" message:" + err.name) ;
		}

		/**/
		try {
		new dijit.form.ValidationTextBox({
			  invalidMessage : "Ingrese un Autor valido",
		      required       : false,
		      style          : "width:300px" ,
		      jsId           : "archivopendiente.autor",
		      name           : "autor",
		      idAttr         :"id",
		      labelAttr      : "label" ,
		      searchAttr     :"label" ,
		      trim           :"true",
		      value  :  defaultautor
		   }, "archivopendiente.autor");
		}catch (err){
			console.debug ("error archivopendiente.autor:"+err.message+" message:" + err.name) ;
			//dijit.byId("archivopendiente.autor").setValue(defaultautor);
		}
		try {
		new dijit.form.ValidationTextBox({
			  invalidMessage : "Ingrese un Nro de documento",
		      required       : false,
		      style          : "width:250px" ,
		      jsId           : "archivopendiente.nroDocumento",
		      name           : "nroDocumento",
		      idAttr         :"id",
		      labelAttr      : "label" ,
		      searchAttr     :"label" ,
		      trim           :"true",
		      onLoad  : actualizarFirmante,
		      value  :  numero
		   }, "archivopendiente.nroDocumento");
		}catch (err){
			console.debug ("error archivopendiente.nroDocumento:"+err.message+" message:" + err.name) ;
			//dijit.byId("archivopendiente.nroDocumento").setValue(numero);
			}
		/*if(refresh){
			actualizarFirmante();

		}*/

		try {
		new dijit.form.DateTextBox({
			name			: "fechadocumentopendiente",
			constraints		: {datePattern:"dd/MM/yyyy"},
			invalidMessage	: "Ingrese una fecha con el formato dd/mm/yyyy",
			required		: true
		},"fechadocumentopendiente");
		}catch (err){
			console.debug ("error archivopendiente.strFechaDocumento:"+err.message+" message:" + err.name) ;
		}
		try{
		dijit.byId("fechadocumentopendiente").attr("value",fecha);
		}catch(err){
		console.debug("error: "+err);
		}

		/*var regresar=document.getElementById("regresar");
		if(regresar!=null){
			new dijit.form.Button({
				onClick		: cerrar
			},"regresar");
		}*/
		new dijit.form.Button({
			iconClass:	"siged16 sigedSave16",
			type: "button",
			onClick		: registrar
		},"registrar");
		new dijit.form.Button({
			iconClass:	"siged16 sigedSave16",
			//type		: "submit"
			type: "button",
			onClick		: guardar
		},"guardar");

		//dojo.addOnLoad(function(){
		var refrescaFirmante =  function (){
			//alert("antes") ;
			pausecomp(500);
			if(last==1){
				actualizarFirmante();

				}
			last ++ ;
			//alert("desp") ;
		};
		var refrescaTipodocumento =  function (){
			//alert("antes2") ;
			pausecomp(500);
			if(last==1){
			actualizarFirmante();

			}
			last++;
			//alert("desp2") ;
		};
		storeTipoDocumento =  new dojo.data.ItemFileReadStore({
			url: "autocompletarTipoDocumentoPlantilla.action?iIdUnidad="+unidadActual
			});
		storeFirmante =  new dojo.data.ItemFileReadStore({url: "autocompletarProceso1.action?sTipoDerivacion=normal&sOpcion=derivar"
			});
		storeFirmante.fetch({onComplete: refrescaFirmante});
		storeTipoDocumento.fetch({onComplete: refrescaTipodocumento});


		//});


	                                   /**/

	}

});

/*
dojo.addOnLoad(function(){
    if(refresh){
	   dijit.byId("archivopendiente.firmante.idusuario").setValue(firm) ;
      actualizarFirmante() ;
      }
    });
*/


