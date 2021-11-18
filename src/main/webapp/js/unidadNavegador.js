var bnd=0;

var treeGetIcon=function(objNodo,opened){
        var sinsplit=""+objNodo.id;
	var tipo=sinsplit.split("|")[0];
	var icono=sinsplit.split("|")[1];
	
        if(tipo =="E" && bnd==0){
		bnd=1;
	}
	if(tipo=="C" || tipo=="E" ){ //JC U
           return "dijitUnidadNavegador";//(opened ? "dijitFolderOpened" : "dijitFolderClosed");
	}else{ 
                if (tipo=="D"){
                   if (icono == "S"){ 
                     return "dijitPDF";   
                   }else{
                      return "dijitANX"; 
                   }
                }else{
                   return  "dijitLeaf";
                }
        }
};

var treeOnClick=function(objNodo){
     var sinsplit=""+objNodo.id;
     var tipo=sinsplit.split("|")[0];
     
     if (tipo == 'D'){
       var idFile = sinsplit.split("|")[3];
       var url = sinsplit.split("|")[4];
       var objectId = sinsplit.split("|")[5];
       
       top['detalleNavegador'].location.href= "/SGD/verDocumento.png?idArchivo=" +idFile + "&objectId=" + objectId + "&url=" + url + "&accion=abrirDocumento&fecha=" + new Date();
     }
     
     if (tipo== 'U'){
       var id = sinsplit.split("|")[1];  
       var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=900, height=700, top=50, left=200";
       var pagina = "ReporteAPN_verHojaRuta.action?idDocumento=" + id;
       window.open(pagina,"HojaRuta",opciones);
     }
};

dojo.addOnLoad(function(){
        var service = new dojo.rpc.JsonService("SMDAction.action");
  	var defered=service.getArbolNavegador("NU");//getArbolTipoDocumentosNavegador();
        defered.addCallback(function(objJSON){
               var arbolito = new dijit.Tree({
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
                dojo.byId("divTreeUnidadNavegadorRecibidos").appendChild(arbolito.domNode);
		arbolito.startup();
	});
});

