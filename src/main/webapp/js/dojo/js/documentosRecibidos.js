dojo.provide("js.documentosRecibidos");
var service = new dojo.rpc.JsonService("SMDAction.action");

var iIdDocumentosRecibidos = 0;
var ctxMenu = null;
var storeProbar;
//var cargandoIG;
var buildDocumentosRecibidos = function(objFiltro,inicio) {
	console.debug("documentosRecibidos.js -->buildDocumentosRecibidos()");
	
	//cargandoIG=document.getElementById("cargandoIG");
   // cargandoIG.style.display="block";
    
	// document.getElementById("cargandoIG").style.display="blok";
	 //dojo.byId("cargandoIG").style.display="block";
	
	//document.getElementById("cargandoIG").style.display="block";
    
	//cargandoIG.style.display="";
	//dijit.byId("dlgProgresBar").show() ;
	/*var borderContainerDocumentosRecibidos = new dijit.layout.BorderContainer({
      id: "borderContainerDocumentosRecibidos",
      jsId: "borderContainerDocumentosRecibidos",
      style: "width:100%;height:100%;"      
   }).placeAt(parent);*/

   /*var contextMenuDocumentosRecibidos = new dijit.Menu({
      id: "contextMenuDocumentosRecibidos",
      jsId: "contextMenuDocumentosRecibidos",
      style: "display:none;"
   });*/
   //contextMenuDocumentosRecibidos.startup();
   //createTreeDocumentosRecibidos(borderContainerDocumentosRecibidos, objFiltro,inicio );
   createTreeDocumentosRecibidos(objFiltro,inicio);
   //dojo.byId("cargandoIG").style.display="none";
   //borderContainerDocumentosRecibidos.startup();
};

var createTreeDocumentosRecibidos = function(objFiltro,inicio) {
	   console.debug("documentosRecibidos.js -->createTreeDocumentosRecibidos()");
    if (inicio) {
    	console.debug("inicio");        
	    service.getDocumentosRecibidosnew(null).addCallback(function(SimpleNode){	
	   		dojo.byId("divCont").innerHTML="";
	   		
	   		service.ContRecibidos().addCallback(function(result){
	   			var cont = new dijit.form.Button({
	             	 label: "["+result+ " Doc(s)]",
	             	 iconClass: "siged16 iconoHojaRuta",
	                  showLabel: true
	              },document.createElement("div3"));
	      		dojo.byId("divCont").appendChild(cont.domNode);
	      		//cont.startup();  
	      		
	   	     });
	   		
	   		var store = new dojo.data.ItemFileReadStore({
	            data: { identifier: "id", label : "label", items: SimpleNode }
	        });
	   		
	        var treeModel = new dijit.tree.ForestStoreModel({ store: store });
	        var treeControl = new dijit.Tree({
	            model: treeModel,
	            showRoot: false,
	            _createTreeNode: function( args){
	                var tnode = new dijit._TreeNode(args);
	                tnode.labelNode.innerHTML = args.label;
	                return tnode;
	            },
	            onClick: showContentDocumentosRecibidos//,
	        },document.createElement("div2"));
			dojo.byId("divTreeExpediente").appendChild(treeControl.domNode);
			//treeControl.startup();   		
	   		
	       });
    }else{
    	/*if(dijit.byId("dlgProgresBar")!=null){
        	dijit.byId("dlgProgresBar").show() ;
        }*/	
    	 var contB = false;
    	 var contTree =-1;
    	
    	dojo.byId("divTreeExpediente").innerHTML="";
    	dojo.byId("divCont").innerHTML="";
	    service.getDocumentosRecibidosnew(objFiltro).addCallback(function(SimpleNode){	 
	    	//contB = true;
	    	//dojo.byId("divCont").innerHTML="";	   		
	   		var store = new dojo.data.ItemFileReadStore({
	            data: { identifier: "id", label : "label", items: SimpleNode }
	        });	   		
	        var treeModel = new dijit.tree.ForestStoreModel({ store: store });
	        var treeControl = new dijit.Tree({
	            model: treeModel,
	            showRoot: false,
	            _createTreeNode: function( args){	
	            	contB =true;
	            	contTree++;
	                var tnode = new dijit._TreeNode(args);
	                tnode.labelNode.innerHTML = args.label;
	                return tnode;
	            },
	            onClick: showContentDocumentosRecibidos//,
	        },document.createElement("div2"));	
	        var cont = new dijit.form.Button({
	        	 label: "["+contTree+ " Doc(s)]",
	        	 iconClass: "siged16 iconoHojaRuta",
	             showLabel: true
	         },document.createElement("div3"));
	 		dojo.byId("divCont").appendChild(cont.domNode);
	 		cont.startup(); 
			dojo.byId("divTreeExpediente").appendChild(treeControl.domNode);
			treeControl.startup(); 	
	       });
	       
	    if(contB){
        	contTree=0;
        	var cont = new dijit.form.Button({
           	 label: "["+contTree+ " Doc(s)]",
           	 iconClass: "siged16 iconoHojaRuta",
                showLabel: true
            },document.createElement("div3"));
    		dojo.byId("divCont").appendChild(cont.domNode);
    		cont.startup(); 
        }
	    //dojo.byId("cargandoIG").style.display="none";
	    //dijit.byId("dlgProgresBar").hide() ;
	    
	    }
    
    dojo.byId("divCont").style.display="block";
    dojo.byId("divTreeExpediente").style.display="block";
    dojo.byId("idFiltroIG").style.display="block";
    
 	
};


var showContentDocumentosRecibidos = function(objNodo) {        
	
	
	
	if(dijit.byId("dlgProgresBar")!=null){
    	dijit.byId("dlgProgresBar").show() ;
    }	
   var idD = ""+objNodo.id;
   var idDoc = ""; 

	var inicio = idD.indexOf('.');
	idDoc = idD.substring(inicio+1, idD.length);
   console.debug("showContentDocumentosRecibidos   idDoc"+idDoc);
   
   var objRecurso = new Recurso("","","","");
      	objRecurso.id = "idInterfazGerente";
      	objRecurso.iconClass = "siged16 sigedSave16";
      	objRecurso.href = "interfazGerente.action?idDoc="+idDoc;
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

dojo.addOnLoad(function(){
    //cuando  esta cargando la pagina     que pinte el efecto de que esta cargando la pagina busquedaAvanzada
    //var cargandoIG = document.getElementById("cargandoIG");
   //buildDocumentosRecibidos(objFiltro,inicio);
   //dojo.byId("divTreeExpediente").appendChild(cargandoIG);
	//dojo.byId("cargandoIG").style.display="block";
	//dojo.byId("divTreeExpediente").appendChild(cargandoIG);
	//cargandoIG.style.display="block";

});
