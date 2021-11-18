//dojo.require("dijit.Tree");
//dojo.require("dojo.data.ItemFileReadStore");
//dojo.require("dojo.rpc.JsonService");
//dojo.require("dojo.parser");
//dojo.require("dijit.Toolbar");
//dojo.require("dijit.form.Button");
var bnd=0;
var tipoBusqueda=0;
var objDocId=0;

var treeOnClick=function(objNodo,tipoN,myidDocumento){
        indexTreeExp+=1;
	dijit.byId("dlgProgresBar").show();
	if(objNodo!=null){		
           var sinsplit=""+objNodo.id;
	   var tipo=sinsplit.split("-")[0];
	   var id=sinsplit.split("-")[1];
           var valor =sinsplit.split("-")[2]; 
	   objDocId=id;	
	}else{
		var tipo =tipoN;
		var id = myidDocumento;
		objDocId=id;
	}	   
        
       if(tipo=="D" || tipo=="U"){ //JC U
                $.ajax({
			type: "GET",
			url: "doViewTrazabilidad.action?origenDetalleDoc="+origen + "&indexTreeExp="+ indexTreeExp + "&opcionMenu=" + valor + "&tab=CARPETA",
			data: "iIdDocumento="+id,
			success: function(msg){
                                                $("#displayIdExpediente"+myidGrid).html(msg);
						 dijit.byId("dlgProgresBar").hide() ;
						 var visible=0;
						 var textoMas=$("#mostrar").text();
						 $("#mostrar").click(function(){
								if(visible){
									$("#idDetalle").hide();
                                                                        $(this).text(textoMas);
                                                                        visible=0;
								}
								else{
                                                                        $("#idDetalle").show();
                                                                        $(this).text("Menos Detalle");
                                                                        visible=1;
								}
							 });
					}
		});
	}
	else if(tipo=="E"){
		$.ajax({
			type: "GET",
			url: "documentosAdicionales_verDetalleExpediente.action?indexTreeExp="+ indexTreeExp,
			data: "iIdExp="+id+"&documentoStor="+mydocumentoStor,
			success: function(msg){
                                                $("#displayIdExpediente"+myidGrid).html(msg);
						var visible=0;
						$("#botonDetalles").click(function(){
							if(visible==0){
								$("#detalle").show("slow");
								$(this).text("Ocultar Detalles");
								visible=1;
							}
							else{
								$("#detalle").hide("slow");
								$(this).text("Mostrar Detalles");
								visible=0;
							}
						});
						 dijit.byId("dlgProgresBar").hide() ;
					}
		});
	}
	else if(tipo=="V"){
    	$.ajax({
			type: "GET",
			url: "doViewTrazabilidad.action?origenDetalleDoc="+origen+ "&indexTreeExp="+ indexTreeExp,
			data: "iIdDocumento="+id,
			success: function(msg){
    						$("#displayIdExpediente"+myidGrid).html(msg);
						 dijit.byId("dlgProgresBar").hide() ;
					}
		});
	}
}; 

var treeGetIcon=function(objNodo,opened){
	var sinsplit=""+objNodo.id;
	var tipo=sinsplit.split("-")[0];
	var id=sinsplit.split("-")[1];
	if(tipo =="E" && bnd==0){
		bnd=1;
	}
	if(tipo=="C" || tipo=="E" ){ //JC U
		return (opened ? "dijitFolderOpened" : "dijitFolderClosed");
	} 
	else{
		return  "dijitLeaf";
	}
};

function referenciar(tipo){
        var defered=service.getDocumentoInicioFlujo($("#idDocumentoMostrado_1").val());
	defered.addCallback(function(respuesta){
           if (respuesta == "1"){ 
             tipoBusqueda=tipo;
	     showBusquedaExpediente();
           }if (respuesta == "0"){
             alert("Sólo puede mover documentos que dan inicio a un flujo documental.");  
           }if (respuesta == "2"){
             alert("Vuelva a intentarlo.");  
           }  
	});
}

dojo.addOnLoad(function(){
  	var defered=service.getArbolDocumentoExpediente(myidExpediente, myidDocumento, opcionMenu);
        defered.addCallback(function(objJSON){
               var arbolito=new dijit.Tree({
			model: new dijit.tree.ForestStoreModel({
				store: new dojo.data.ItemFileReadStore({data: objJSON}),
				query: {top: true},
				rootId: "jerarquia",
				rootLabel: "Jerarquia",
				childrenAttrs: ["children"]
			}),
			showRoot: false,
			getIconClass: treeGetIcon,
			onClick: treeOnClick,
			autoExpand: true
		},document.createElement("div"));
		dojo.byId("divTreeExpediente"+myidGrid).appendChild(arbolito.domNode);
		arbolito.startup();
          	treeOnClick(null,"E",myidExpediente);
	});
	
});

function muestra_oculta(id, url) {
   if (document.getElementById(id)) { //se obtiene el id
      var el = document.getElementById(id); //se define la variable "el" igual a nuestro div
      el.style.display = (el.style.display == 'none') ? '' : 'none'; //damos un atributo display:none que oculta el div
   }
   if(url != null){
	   var service = new dojo.rpc.JsonService("SMDAction.action");
       var defered = service.verArchivoAlfresco(url);

       defered.addCallback(function(url) {
           console.log("URL Alfresco [" + url + "]");
           //alert(url);
           window.open(url);
       });
   }
//$(document.getElementById(id)).toggle();
}


function reloadTree(){
	//var service = new dojo.rpc.JsonService("SMDAction.action");
   var defered = service.getArbolDocumentoExpediente(myidExpediente, myidDocumento);
	defered.addCallback(function(objJSON){
		var arbolito=new dijit.Tree({
			model: new dijit.tree.ForestStoreModel({
				store: new dojo.data.ItemFileReadStore({data: objJSON}),
				query: {top: true},
				rootId: "jerarquia",
				rootLabel: "Jerarquia",
				childrenAttrs: ["children"]
			}),
			showRoot: false,
			getIconClass: treeGetIcon,
			onClick: treeOnClick
		},document.createElement("div"));
		$("#divTreeExpediente"+myidGrid).html(arbolito.domNode);
	});
}


function seleccionarFila(e){
        if (e.rowIndex==undefined) {
		return;
	}
        
        var idExpedienteNuevo= dijit.byId("grdBusquedaExpediente").getItem(e.rowIndex).id; 
        
        if(tipoBusqueda=="C"){
          //POR IMPLEMENTAR	
	}else{
               if(idExpedienteNuevo!=myidExpediente){
                        var numeroExpediente=  dijit.byId("grdBusquedaExpediente").getItem(e.rowIndex).nroexpediente;//grdBusquedaExpediente.getItem(e.rowIndex).nroexpediente;
                        if(confirm("¿Desea referenciar el documento "+$("#nombreDocumento").text()+" a la Carpeta "+numeroExpediente+"?")){
                                dijit.byId("dlgProgresBar").show();
				var idDocumento=$("#idDocumentoMostrado_1").val();
				$.ajax({
					type:	"GET",
					url:	"verificarExistenciaDeArchivoParaCambiarReferencia.action",
					data:	"iIdDoc="+idDocumento+"&idExpedienteNuevo="+idExpedienteNuevo,
					success: function(msg){
                                                               if(msg=="true"){
									$.ajax({
										type: "GET",
										url: "verificarDocumentoUnico.action",
										data: "iIdDoc="+idDocumento+"&iIdExp="+myidExpediente,
										success: function(msg){
                                                                                                        var unico=msg=="true";
													var sePuede=true;
                                                                                                     
													if(unico){             
														dijit.byId("dlgProgresBar").hide();
														sePuede=confirm("El Documento es el unico perteneciente a esta Carpeta, referenciarlo a otra Carpeta implica que esta Carpeta sea anulada. ¿Desea continuar?");
													}
													if(sePuede){
														$.ajax({
															type: "POST",
															url: "cambiarReferenciaDocumento.action",
															data: "iIdDoc="+idDocumento+"&iIdExp="+myidExpediente+"&idExpedienteNuevo="+idExpedienteNuevo+"&unico="+unico,
															success: function(msg){
                                                                                                                                                var exito=msg=="true";
																		if(exito){
                                                                                                                                                        if(!unico){
                                                                                                                                                                reloadTree();
                                                                                                                                                                var idGrid=$("#idGrid").val();
                                                                                                                                                                $("#displayIdExpediente"+idGrid).html("");
                                                                                                                                                                alert("El documento se referencio exitosamente.");
                                                                                                                                                                if (myidGrid== TIPO_GRID_RECIBIDOS)
                                                                                                                                                                  showGridInbox(TIPO_GRID_RECIBIDOS);
                                                                                                                                                                if (myidGrid==TIPO_GRID_EXPEDIENTES)
                                                                                                                                                                  showGridInbox(TIPO_GRID_EXPEDIENTES);
                                                                                                                                                               
                                                                                                                                                                if (myidGrid==TIPO_GRID_DOCUMENTOS_ARCHIVADOS)
                                                                                                                                                                  showGridInbox(TIPO_GRID_DOCUMENTOS_ARCHIVADOS);
                                                                                                                                                                if (myidGrid==TIPO_GRID_PENDIENTES )
                                                                                                                                                                  showGridInbox(TIPO_GRID_PENDIENTES );
                                                                                                                                                                if (myidGrid==TIPO_GRID_ANULADOS)
                                                                                                                                                                  showGridInbox(TIPO_GRID_ANULADOS);
                                                                                                                                                              
                                                                                                                                                                if (myidGrid==TIPO_GRID_ENVIADOS)
                                                                                                                                                                  showGridInbox(TIPO_GRID_ENVIADOS);
                                                                                                                                                                if (myidGrid==TIPO_GRID_INFORMATIVOS )
                                                                                                                                                                  showGridInbox(TIPO_GRID_INFORMATIVOS );
                                                                                                                                                                if (myidGrid==TIPO_GRID_FIRMAR)
                                                                                                                                                                  showGridInbox(TIPO_GRID_FIRMAR);
																			}
																			else{			
                                                                                                                                                                alert("El documento ha sido referenciado y la carpeta se anulo exitosamente");
																				if (myidGrid== TIPO_GRID_RECIBIDOS)
                                                                                                                                                                  showGridInbox(TIPO_GRID_RECIBIDOS);
                                                                                                                                                                if (myidGrid==TIPO_GRID_EXPEDIENTES)
                                                                                                                                                                  showGridInbox(TIPO_GRID_EXPEDIENTES);
                                                                                                                                                              
                                                                                                                                                                if (myidGrid==TIPO_GRID_DOCUMENTOS_ARCHIVADOS)
                                                                                                                                                                  showGridInbox(TIPO_GRID_DOCUMENTOS_ARCHIVADOS);
                                                                                                                                                                if (myidGrid==TIPO_GRID_PENDIENTES )
                                                                                                                                                                  showGridInbox(TIPO_GRID_PENDIENTES );
                                                                                                                                                                if (myidGrid==TIPO_GRID_ANULADOS)
                                                                                                                                                                  showGridInbox(TIPO_GRID_ANULADOS);
                                                                                                                                                              
                                                                                                                                                                if (myidGrid==TIPO_GRID_ENVIADOS)
                                                                                                                                                                  showGridInbox(TIPO_GRID_ENVIADOS);
                                                                                                                                                                if (myidGrid==TIPO_GRID_INFORMATIVOS )
                                                                                                                                                                  showGridInbox(TIPO_GRID_INFORMATIVOS );
                                                                                                                                                                if (myidGrid==TIPO_GRID_FIRMAR)
                                                                                                                                                                  showGridInbox(TIPO_GRID_FIRMAR);
                                                                                                                                                                
																			}
																		}
																		else{
																			alert("Ocurrio un error al momento de referenciar el Documento");
																		}
																		dijit.byId("dlgProgresBar").hide();
																	}
														});
													}												
												}
									});
									dlgBusquedaExpediente.hide();
								}
								else{
									alert("La carpeta a la que quiere referenciar este documento ya cuenta con uno o mas archivos que poseen el mismo nombre que los de este documento. No se puede realizar la referenciacion.");
								}							
							}
				});
				dijit.byId("dlgProgresBar").hide();
			}
		}
		else{
			alert("Seleccione una carpeta diferente al que ya pertenece el documento.");
		}		
		
	}

}