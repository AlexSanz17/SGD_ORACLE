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
	   objDocId=id;
	   console.debug("(treeOnClick) tipo->" + tipo);
	   console.debug("(treeOnClick) origen->" + origen);		
	}else{
		var tipo =tipoN;
		var id = myidDocumento;
		objDocId=id;
	}
       if(tipo=="D" || tipo=="DP"){
                $.ajax({
			type: "GET",
			url: "doViewTrazabilidad.action?origenDetalleDoc="+origen + "&indexTreeExp="+ indexTreeExp + "&sinDetalle=S",
			data: "iIdDocumento="+id/*+"<s:if test='avisopermiso==0'>&avisopermiso=0</s:if>"*/,
			success: function(msg){
						$("#displayIdDocumentoRespuesta"+myidGrid).html(msg);
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
	
};

var treeGetIcon=function(objNodo,opened){
	var sinsplit=""+objNodo.id;
	var tipo=sinsplit.split("-")[0];
	var id=sinsplit.split("-")[1];
	if(tipo =="DP" && bnd==0){
		bnd=1;
	}
	if(tipo=="C" || tipo=="DP"){
		return (opened ? "dijitFolderOpened" : "dijitFolderClosed");
	}
	else{
		return  "dijitLeaf";
	}
};

dojo.addOnLoad(function(){
	var defered=service.getArbolDocumentoRespuesta(myidDocumento);
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
		dojo.byId("divTreeDocumentoRespuesta"+myidGrid).appendChild(arbolito.domNode);
		arbolito.startup();
		treeOnClick(null,"DP",myidDocumento);
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

function abrirarchivo(ruta){
        var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes, width=600, height=490, top=400, left=400";
	window.open(ruta,"_blank",opciones);
}

