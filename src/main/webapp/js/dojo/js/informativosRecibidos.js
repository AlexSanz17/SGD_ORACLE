dojo.provide("js.informativosRecibidos");
var service = new dojo.rpc.JsonService("SMDAction.action");

var iIdDocumentosRecibidos = 0;
var ctxMenu = null;
var storeProbar;
var buildInformativosRecibidos = function(parent,objFiltro,inicio) {
	console.debug("informativosRecibidos.js -->buildInformativosRecibidos()");
	
	var borderContainerInformativosRecibidos = new dijit.layout.BorderContainer({
      id: "borderContainerInformativosRecibidos",
      jsId: "borderContainerInformativosRecibidos",
      style: "width:100%;height:100%;"      
   }).placeAt(parent);

   var contextMenuInformativosRecibidos = new dijit.Menu({
      id: "contextMenuInformativosRecibidos",
      jsId: "contextMenuInformativosRecibidos",
      style: "display:none;"
   });
   contextMenuInformativosRecibidos.startup();
   createTreeInformativosRecibidos(borderContainerInformativosRecibidos, contextMenuInformativosRecibidos,objFiltro,inicio );
   borderContainerInformativosRecibidos.startup();
};

var createTreeInformativosRecibidos = function(parent, context,objFiltro,inicio) {
	   console.debug("documentosRecibidos.js -->createTreeDocumentosRecibidos()");
	   console.debug("objFiltro"+objFiltro);
    
	   /*var toolBarCarpetaBusqueda = new dijit.Toolbar({
		      region: "top",
		   }).placeAt(parent);
	   
	   var myTextBox = new dijit.form.TextBox({
           name: "firstname",
           value: "",
       }).placeAt(parent);*/
	   
    if (inicio == true) {    		
    	arbolHijo2(parent,null); 
    	 
    }else{
    	arbolHijo2(parent,objFiltro); 	
	}
    	
 	dojo.style("borderContainerInformativosRecibidos", {
		"overflow":"auto",
 	});	
};

var showContentInformativosRecibidos = function(objNodo) {        
	
	
	
	if(dijit.byId("dlgProgresBar")!=null){
    	dijit.byId("dlgProgresBar").show() ;
    }	
   var idD = ""+objNodo.id;
   var idDoc = "";
   //idDoc = idD.substring(2);
   idDoc = idD;
   console.debug("showContentInformativosRecibidos   idDoc"+idDoc);
   
   var objRecurso = new Recurso("","","","");
      	objRecurso.id = "idInterfazGerente";
      	objRecurso.iconClass = "siged16 sigedSave16";
      	objRecurso.href = "interfazInformativos.action?idDoc="+idDoc;
      	objRecurso.title = "Bandeja Entrada";

         //console.debug( "!dijit.byId(btnData.id)  false setting "+objRecurso.id+" .href: "+objRecurso.href );
         dijit.byId("tabContainerInbox").removeChild(dijit.byId("idInterfazGerente"));
         dijit.byId("idInterfazGerente").destroyDescendants();
         dijit.byId("idInterfazGerente").destroy();

         if (dijit.byId("grdBusquedaExpediente")) {
               dijit.byId("grdBusquedaExpediente").destroy();
         }

         var newTabInterfazGerente = new dojox.layout.ContentPane({
            id : objRecurso.id,
            jsId : objRecurso.id,
            closable: false,
            href : objRecurso.href,
            iconClass : objRecurso.iconClass,          
            title: objRecurso.title
         });
         dijit.byId("tabContainerInbox").addChild(newTabInterfazGerente);
         //dijit.byId(objRecurso.id).setHref(objRecurso.href);
         dijit.byId("tabContainerInbox").selectChild(dijit.byId(objRecurso.id));
         
};

var treeGetIcon=function(objNodo,opened){
	var sinsplit=""+objNodo.id;
	var tipo=sinsplit.split("-")[0];
	var id=sinsplit.split("-")[1];
	if(tipo =="E" && bnd==0){
		bnd=1;
	}
	if(tipo=="C" || tipo=="E"){
		return (opened ? "dijitFolderOpened" : "dijitFolderClosed");
	}
	else{
		return  "dijitLeaf";
	}
};

var arbolHijo2 = function(parent, objFiltro){
 //myidGrid=1;
	var defered=service.getDocumentosInformativos(objFiltro);
	defered.addCallback(function(objJSON){
		var arbolitos=new dijit.Tree({
			model: new dijit.tree.ForestStoreModel({
				store: new dojo.data.ItemFileReadStore({data: objJSON}),
				query: {top: true},
				rootId: "jerarquia",
				rootLabel: "Jerarquia",
				childrenAttrs: ["children"]
			}),
			showRoot: false,
			//getIconClass: treeGetIcon,
			onClick: showContentInformativosRecibidos//,			
			//autoExpand: false
			
		}).placeAt(parent);
	});
	
};
