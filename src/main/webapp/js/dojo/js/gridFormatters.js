dojo.provide("js.gridFormatters");

document.write("\<script");
document.write(" src='js/siged/date.js'");
document.write("\>");
document.write("\</script\>");

function formatterAlerta() {
    if (sTipoGridActual == TIPO_GRID_RECIBIDOS) {
        //console.debug("formatterAlerta ejecutandose");
/*
        if (storeGrid) {
            //aqui a�adir codigo para que esto solo se ejecute cuando tengamos la columna tipoalerta definida
            storeGrid.fetch({
                query: {
                    id:"*"
                },
                onComplete: onCompleteFetch
            });
        }
        objFechaActual.addSeconds(1);
    */
    }

//grdSiged.setStore(storeGrid);
}

setInterval(formatterAlerta,1000);

var objFechaActual = new Date();
var parametro1 = null;
var parametro2 = null;
var lfechaactual = null ;
//var arrParametros;

var formatterDate = function(fecha) {
    if (fecha == undefined) {
        return "";
    }

    var datFecha = fecha.substring(0,10);

    //console.log("fecha actual [%s]", objFechaActual.toString('yyyy-MM-dd'));

    if (objFechaActual.toString('yyyy-MM-dd') == datFecha) {
        return fecha.substring(11,16);
    } else {
        // Modificado por Germán Enríquez para que se vean las horas con la fecha limite
        return fecha.substring(8,10) + "/" + fecha.substring(5,7) + "/" + fecha.substring(0,4)+" "+fecha.substring(11,16);
    }
}

function formatterLink(url) {
    if (url != null && url != "") {
        return "<a alt='Ver Documento' onclick='verArchivo(\"" + url + "\");'><img src='images/clic.gif' /></a>";
    } else {
        return null;
    }
}

function verArchivo(url) {
}

function verArchivoBusquedaAvanzada(url) {
    var service = new dojo.rpc.JsonService("SMDAction.action");
    var defered = service.verArchivoAlfrescoBusqueda(url);
    defered.addCallback(function(url) {
    	console.info(url);
        window.open(url);
    });

}

var mostrarPDF=function(rutaAlfresco){
	var extension = funcExtension(rutaAlfresco);
	console.debug("es un:"+extension);

	if(recursoIG==1 && vistaIG==true){
		verArchivo(rutaAlfresco);
    }else{
	         if(extension=='pdf' || extension=='PDF'){
	        	var service = new dojo.rpc.JsonService("SMDAction.action");
			    var defered = service.verArchivoAlfresco(rutaAlfresco);
			    defered.addCallback(function(rutaAlfresco) {
			    	var myPDF = new PDFObject({
				    	url: rutaAlfresco
				    }).embed('divPDF');
			    	dijit.byId("dlgProgresBar").hide() ;
			    });
	        }else{
	        	verArchivo(rutaAlfresco);
		    }

}
};

var mostrarPDFVisor=function(rutaAlfresco, busquedaAvanzada){
	var extension = funcExtension(rutaAlfresco);
	console.debug("es un:"+extension);

	if(recursoIG==1 && vistaIG==true){
		verArchivo(rutaAlfresco);
    }else{
             if (busquedaAvanzada=='1'){
            	 verArchivo(rutaAlfresco);
             }else{
    	         if(extension=='pdf' || extension=='PDF'){
    	        	if  (recursoIG==1){
			        	var service = new dojo.rpc.JsonService("SMDAction.action");
					    var defered = service.verArchivoAlfresco(rutaAlfresco);
					    defered.addCallback(function(rutaAlfresco) {
					    	var myPDF = new PDFObject({
						    	url: rutaAlfresco
						    }).embed('divPDF');
					    	dijit.byId("dlgProgresBar").hide() ;
					    });
    	           }else{
    	        	      verArchivo(rutaAlfresco);
    	           }
		        }else{
		        	verArchivo(rutaAlfresco);
			    }
             }
}
};


var mostrarPDFVisorBusquedaAvanzada=function(rutaAlfresco, busquedaAvanzada){
	var extension = funcExtension(rutaAlfresco);
	console.debug("es un:"+extension);

	verArchivoBusquedaAvanzada(rutaAlfresco);
	/*
	if(recursoIG==1 && vistaIG==true){
		verArchivo(rutaAlfresco);
    }else{
             if (busquedaAvanzada=='1'){
            	 verArchivoBusquedaAvanzada(rutaAlfresco);
             }else{
		         if(extension=='pdf' || extension=='PDF'){
		        	var service = new dojo.rpc.JsonService("SMDAction.action");
				    var defered = service.verArchivoAlfresco(rutaAlfresco);
				    defered.addCallback(function(rutaAlfresco) {
				    	var myPDF = new PDFObject({
					    	url: rutaAlfresco
					    }).embed('divPDF');
				    	dijit.byId("dlgProgresBar").hide() ;
				    });
		        }else{
		        	verArchivo(rutaAlfresco);
			    }
             }
      }*/
};

var funcExtension=function(miString){
    var extensionTemp = "";
    var extension= "";
    for (i=miString.length-1;i+1>0;i--) {
        if(miString.charAt(i)=='.'){
        	break;
	    }
        extensionTemp +=miString.charAt(i);
    }
	for (i=extensionTemp.length-1;i+1>0;i--) {
        extension +=extensionTemp.charAt(i);
    }
		return extension;
};

function verRutaArchivoAlfresco(url) {
    var service = new dojo.rpc.JsonService("SMDAction.action");
    service.verArchivoAlfresco(url);
}

function formatterImg(url){
    return "<img src=\""+url+"\" alt=\"\" />";
}

function hoja(id){
    var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=900, height=700, top=50, left=200";
    var pagina = "ReporteAPN_verHojaRuta.action?idDocumento=" + parseInt(id);
    window.open(pagina,"HojaRuta",opciones);
};

function abrirDocumento(idArchivo, url, objectId){
     url = url.replace("|", " ").replace("|"," ").replace("|", " ").replace("|", " ").replace("|"," "); 
     window.open("/SGD/verDocumento.png?idArchivo=" +idArchivo + "&url=" + url + "&objectId="+ objectId  + "&accion=abrirDocumentoTemporal&fecha=" + new Date(), "_blank");
};

var formatterVirtual = function(icono){
    if (icono!='X'){
       if (icono == 'R'){
        return "&nbsp;&nbsp;<img  border='0' title='Recepción Virtual' src='images/recepcion.png' align='middle'/>";
       }
       if (icono == 'D'){
        return "&nbsp;&nbsp;<img border='0' title='Despacho Virtual' src='images/despacho.png' align='middle' />";
       }
    }else{
         return "<img border='0'  title='Despacho Virtual' src='images/ed_blank.gif' />";
    }
}

var formatterCargo = function(icono){
     return "&nbsp;&nbsp;<img  border='0' title='Cargo' src=" + icono + " align='middle'/>";
}

var formatterDerivar = function(icono){
     return "&nbsp;&nbsp;<img  border='0' title='Cargo' src=" + icono + " align='middle'/>";
}

var formatterIntentos = function(icono){
     return "&nbsp;&nbsp;<img  border='0' title='Cargo' src=" + icono + " align='middle'/>";
}

var formatterDocumentos = function(iconoDocumento){
     if (iconoDocumento!=null && iconoDocumento!= ''){ 
        var objectId = "";
        var idarchivo = iconoDocumento.substr(0,iconoDocumento.indexOf("|"));
        var cargo = "0";
        
        iconoDocumento = iconoDocumento.substr(iconoDocumento.indexOf("|") + 1, iconoDocumento.length);
        var url = iconoDocumento.substr(0, iconoDocumento.indexOf("|")).replace(" ", "|").replace(" ","|").replace(" ","|").replace(" ", "|").replace(" ", "|");
        iconoDocumento = iconoDocumento.substr(iconoDocumento.indexOf("|") + 1, iconoDocumento.length);
        var indice = iconoDocumento.indexOf("|");
        if (indice == -1){
          objectId = iconoDocumento; //iconoDocumento.substr(iconoDocumento.indexOf("|") + 1, iconoDocumento.length);
        }else{
          objectId = iconoDocumento.substr(0,iconoDocumento.indexOf("|"));
          cargo =    iconoDocumento.substr(iconoDocumento.indexOf("|") + 1, iconoDocumento.length);
        }  
        
        if (cargo == "0"){
           return "<a href='#'  onclick=abrirDocumento(" + idarchivo +  ",'"  + url + "','" + objectId  +"')>" + "<img border='0' alt='' title='Abrir Documento Principal' src='images/pdf.PNG' /></a>";
       }else{
           return "<a href='#'  onclick=abrirDocumento(" + idarchivo +  ",'"  + url + "','" + objectId  +"')>" + "<img border='0' alt='' title='Abrir Documento Con Cargo' src='images/pdfCargo.png' /></a>";
       }
     }else{
        return ""; 
     }
};

var formatterAreas = function(iconoArea) {
    var id = iconoArea.substr(0,iconoArea.indexOf("|"));  
    iconoArea =  iconoArea.substr(iconoArea.indexOf("|") + 1, iconoArea.length);    
    var sResult = "";
    
    if (iconoArea == "PD"){
      sResult = "<a href='#'  onclick=hoja(" + id + ")>" + "<img border='0' src='" + "images/" + iconoArea +  ".png' alt='' title='Presidencia'/></a>"
    }else{
          if (iconoArea == "GG"){
            sResult = "<a href='#'  onclick=hoja(" + id + ")>" + "<img border='0' src='" + "images/" + iconoArea +  ".png' alt='' title='Gerencia General'/></a>"
          }else{
              if (iconoArea == "GE"){
                sResult = "<a href='#'  onclick=hoja(" + id + ")>" + "<img border='0' src='" + "images/" + iconoArea +  ".png' alt='' title='Gerencia'/></a>"
              }else{
                  if (iconoArea == "MP"){
                    sResult = "<a href='#'  onclick=hoja(" + id + ")>" + "<img border='0' src='" + "images/" + iconoArea +  ".png' alt='' title='Mesa de Parte'/></a>"
                  }else{
                      if (iconoArea == "OF"){
                        sResult = "<a href='#'  onclick=hoja(" + id + ")>" + "<img border='0' src='" + "images/" + iconoArea +  ".png' alt='' title='Oficina'/></a>"
                      }else{
                             if (iconoArea == "PP"){
                               sResult = "<a href='#'  onclick=hoja(" + id + ")>" + "<img border='0' src='" + "images/" + iconoArea +  ".png' alt='' title='Procuradoria'/></a>"
                             }else{
                                  if (iconoArea == "JF"){
                                    sResult = "<a href='#'  onclick=hoja(" + id + ")>" + "<img border='0' src='" + "images/" + iconoArea +  ".png' alt='' title='Jefatura'/></a>"
                                  }else{
                                      if (iconoArea == "CI"){
                                        sResult = "<a href='#'  onclick=hoja(" + id + ")>" + "<img border='0' src='" + "images/" + iconoArea +  ".png' alt='' title='Organo de Control Institucional'/></a>"
                                      }
                                  }
                             }   
                      }
                  }
              }
          }   
    }
    return sResult;
};

var formatterLeido = function(leido) {
    var sResult = null;

    if (leido == 'S') {
        sResult = "<img src='images/sigedIconos/abierto16.png' alt='' title='Old' />";
    } else if (leido == 'N') {
        sResult = "<img src='images/sigedIconos/EmailUnread16.gif' alt='' title='New' />";
    }

    return sResult;
};

var formatterTipoDocumento = function(td) {
    var sResult = null;
    console.debug('TD->' + td);
    if (td == 'MEMORANDUM') {
        sResult = "<img src='images/tiposDocumento/memo.jpg' alt='' title='MEMORANDUM' height='20px;' width='20px;' border='0'/>";
    } else if (td == 'OFICIO') {
        sResult = "<img src='images/tiposDocumento/oficio.jpg' alt='' title='OFICIO' height='16px;' width='16px;' border='0'/>";
    } else if (td == 'INFORME') {
        sResult = "<img src='images/tiposDocumento/informe.jpg' alt='' title='INFORME' height='16px;' width='16px;' border='0'/>";
    } else {
        sResult = "Otro";
    }

    return sResult;
};

var formatterTipoNumeracion = function(sTipo) {
    var sImgResult = null;

    if (sTipo == "666") {
        sImgResult = "<img src='images/sigedIconos/plantilla.png' alt='' title='Expediente con Copia' />";
    } else if (sTipo == "667") {
        sImgResult = "<img src='images/sigedIconos/aprobar.png' alt='' title='Destinatario' />";
    } else if (sTipo == "668") {
        sImgResult = "<img src='images/sigedIconos/rechazar.png' alt='' title='Documento con Copia' />";
    } else if (sTipo == "669") {
        sImgResult = "<img src='images/sigedIconos/rechazar.png' alt='' title='Documento con Copia' />";
    }

    return sImgResult;
};

var prepareAlertaData = function() {
    service.getPorcentajesAlertas().addCallback(function(arrParameters) {
        //      for (var i = 0; i < arrParameters.length; i++) {
        //         console.debug("Parametro [%d] Valor [%s]", i, arrParameters[i]);
        //      }

        //      objFechaActual.setTime(parseInt(arrParameters.length-1)) ;
        //      lfechaactual = parseInt(arrParameters[arrParameters.length-1]);
        lfechaactual = parseInt(arrParameters[0]);
    //arrParametros = arrParameters;
    });
}

var onCompleteFetch = function(items, request) {

    var onSave = function(){
    };

    var onSaveError = function(error) {
        alert("Error occurred: " + error);
    };
/*
    for (var i = 0; i < items.length; i++){
        var item = items[i];
        var ran_number=retornarTipoAlerta(storeGrid.getValue(item,'lfecharecepcion'),storeGrid.getValue(item,'lfechalimite'),storeGrid.getValue(item,'porcentajealertaA'),storeGrid.getValue(item,'porcentajealertaR'));
        var giff = storeGrid.getValue(item, "tipoalerta") ;

        if(ran_number==1){
            storeGrid.setValue(item, "tipoalerta", arrImgBolitas[0].src);
        }
        if(ran_number==2){
            storeGrid.setValue(item, "tipoalerta", arrImgBolitas[1].src);
        }
        if(ran_number==0){
            storeGrid.setValue(item, "tipoalerta", arrImgBolitas[2].src);
        }
    }
*/
    if (storeGrid.isDirty()){
        storeGrid.save({
            onComplete: onSave,
            onError: onSaveError
        });
    }
}


//Define a fetch error handler, just in case.
var onFetchError = function(error, request){
    alert("Fetch failed.  " + error);
}

function retornarTipoAlerta(fecharegistro,fechafinal,porcentajeAmarillo, porcentajeRojo){
    //Formato del objeto de arrParametros   8/0.69/0.99 (idProceso/porcentajeAmarillo/porcentajeRojo)
    var lista;

    //   if (arrParametros == null || arrParametros == undefined) {
    //      return 0;
    //   }

    //	for (var i = 0; i < arrParametros.length; i++){
    //		if(arrParametros[i].split("/",1)==idproceso){
    //			lista = arrParametros[i].split("/");
    //			parametro1 = lista[1];
    //			parametro2 = lista[2];
    //		}
    //	}
    parametro1 = porcentajeAmarillo;
    parametro2 = porcentajeRojo;
    if(fechafinal!=null){
        objFechaRegistro= new Date();
        objFechaFinal=new Date();


        //Creando el objeto Fecha de Registro
        /*
   objFechaRegistro.setFullYear(fecharegistro.substring(0,4), parseInt(fecharegistro.substring(5,7))-1, fecharegistro.substring(8,10));
   objFechaRegistro.setHours(fecharegistro.substring(11,13), fecharegistro.substring(14,16), fecharegistro.substring(17,19));
	alert("fecharegistro:"+fecharegistro.substring(0,4)+'/'+(parseInt(fecharegistro.substring(5,7))-1)+'/'+fecharegistro.substring(8,10));
	 */


        //Creando el objeto Fecha Final
        /*
   objFechaFinal.setFullYear(fechafinal.substring(0,4), parseInt(fechafinal.substring(5,7))-1, fechafinal.substring(8,10));
   objFechaFinal.setHours(fechafinal.substring(11,13), fechafinal.substring(14,16), fechafinal.substring(17,19));
   alert("fechafinal:"+fechafinal);
   alert("fecharegistro:"+fechafinal.substring(0,4)+'/'+(parseInt(fechafinal.substring(5,7))-1)+'/'+fechafinal.substring(8,10));
   */
        //indice=parseFloat((objFechaActual.getTime()-objFechaRegistro.getTime())/(objFechaFinal.getTime()-objFechaRegistro.getTime()));
        indice=parseFloat((lfechaactual-fecharegistro)/(fechafinal-fecharegistro));
        // alert('indice :'+indice);
        // alert('lfechaactual:'+lfechaactual+' fecharegistro:'+fecharegistro+' fechafinal:'+fechafinal) ;

        /* alert(' fa:'+objFechaActual.getFullYear()+'/'+objFechaActual.getMonth()+'/'+objFechaActual.getDay()+'\n') ;
   alert(' fr:'+objFechaRegistro.getFullYear()+'/'+objFechaRegistro.getMonth()+'/'+objFechaRegistro.getDay()+'\n') ;
   alert(' ff:'+objFechaFinal.getFullYear()+'/'+objFechaFinal.getMonth()+'/'+objFechaFinal.getDay()+'\n') ;
     */
        //*alert('indice:'+indice+' \n parametro1:'+parametro1+' \n parametro2:'+parametro2+' \n \n objFechaActual.getTime():'+objFechaActual.getTime()+' objFechaRegistro.getTime():'+objFechaRegistro.getTime()+'  objFechaFinal.getTime():'+objFechaFinal.getTime()+'\n'+ '  resta1: '+(objFechaActual.getTime()-objFechaRegistro.getTime())+'  \n   resta2:'+(objFechaFinal.getTime()-objFechaRegistro.getTime()));
        if(indice<parametro1)
            return 2;//bola verde
        if(indice<=parametro2 && indice>=parametro1){
            return 1;//bola amarilla
        }
        if(indice>parametro2)
            return 0;//bola roja
    }
    return 0;
}

var formatterDateReemplazo = function(fecha) {
    if (fecha == undefined) {
        return "";
    }

    return fecha.substring(8,10) + "/" + fecha.substring(5,7) + "/" + fecha.substring(0,4);
};

var formatterDateLista = function(fecha) {
    if (fecha == undefined) {
        return "";
    }

    return fecha.substring(8,10) + "/" + fecha.substring(5,7) + "/" + fecha.substring(0,4);
};

var formatterHourReemplazo = function(fecha) {
    if (fecha == undefined) {
        return "";
    }

    return fecha.substring(11,13) + ":" + fecha.substring(14,16);
};

var verDocumentosExp = function(item){

	if(dijit.byId("tabDXE")){
		dijit.byId("tabContainerInbox").removeChild(dijit.byId("tabDXE"));
		dijit.byId("tabDXE").destroyRecursive();
	}
	var tabDXE = new dojox.layout.ContentPane({
        id : "tabDXE",
        closable: true,
        href : "mostrarBandejaDXE.action?iIdDocumento="+item,
        title: "Documentos del Expediente",
        onClose: function(){
        	showGridInbox(sTipoGridActual);
        	return true;
        }
	});
	dijit.byId("tabContainerInbox").addChild(tabDXE);
	dijit.byId("tabContainerInbox").selectChild(tabDXE);

	if(recursoIG==1){
		agrupaIG = false;
	   }

};

var formatterButton = function(item){
	if(item == "-"){
		return "-";
	}
	var idDoc = item.substring(0,item.indexOf("["));
	var cantDocs = item.substring(item.indexOf("[") + 1, item.indexOf("]"));
	var leido = item.substring(item.indexOf("]") + 1);
	if(leido == "true"){
		return "<img src='images/sigedIconos/plantilla.png' alt='Relacionados' onClick='verDocumentosExp("+idDoc+")' style='cursor:pointer;'/><strong>("+cantDocs+")</strong>";
	}
	return "<img src='images/sigedIconos/nuevoDocumento16.png' alt='Relacionados' onClick='verDocumentosExp("+idDoc+")' style='cursor:pointer;'/>("+cantDocs+")";
};
/*
var formatterButton = function(item){

};*/
