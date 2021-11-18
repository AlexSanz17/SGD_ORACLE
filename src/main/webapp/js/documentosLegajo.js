var bnd=0;

var treeOnClick=function(objNodo,tipoN,idExp, idDocumentoLegajo){
        indexTreeExp+=1;
	dijit.byId("dlgProgresBar").show();
     
        if(objNodo!=null){		
           var sinsplit=""+objNodo.id;
	   var tipo=sinsplit.split("-")[0];
	   var id=sinsplit.split("-")[1];
           var valor =sinsplit.split("-")[2]; 
           var nodoEx = sinsplit.split("-")[4];
	}else{
	   var tipo =tipoN;
	   var id = idExp;
	}	   
        
       if(tipo=="D" || tipo=="U"){ //JC U  
                idLegajoDinamico = nodoEx;
                idDocumentoDinamico = id;
               
                $.ajax({
			type: "GET",
			url: "doViewTrazabilidad.action?origenDetalleDoc="+origen + "&indexTreeExp="+ indexTreeExp + "&opcionMenu=" + valor + "&tab=LEGAJO",
			data: "iIdDocumento="+id,
			success: function(msg){
                                                $("#displayIdLegajo"+myidGrid).html(msg);
                                                dijit.byId("dlgProgresBar").hide() ;
                                                $("#idDetalle").hide();
					}
		});
	}
	else if(tipo=="E"){
                idLegajoDinamico = id;
                idDocumentoDinamico = 0;
                
                if (idDocumentoLegajo==null || idDocumentoLegajo == ""){
                    idDocumentoLegajo = 0;
                }
                
                $.ajax({
			type: "GET",
			url: "documentosAdicionales_verDetalleLegajo.action?indexTreeExp="+ indexTreeExp +"&idDocumentoLegajo=" + idDocumentoLegajo,
			data: "iIdLegajo="+ id +"&documentoStor="+mydocumentoStor,
			success: function(msg){
                                                $("#displayIdLegajo"+myidGrid).html(msg);
                                                for(r=0;r<800000000;r++);
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


dojo.addOnLoad(function(){
  	var defered=service.getArbolLegajo(myidLegajo, myidDocumento, opcionMenu);
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
		dojo.byId("divTreeLegajo"+myidGrid).appendChild(arbolito.domNode);
		arbolito.startup();
          	treeOnClick(null,"E",idLegajoDinamico, myidDocumento);
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
}



