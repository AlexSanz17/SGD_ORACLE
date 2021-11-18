var bnd=0;
var tipoBusqueda=0;
var objDocId=0;

var ejecuta=function(){
    alert("Hola mundo");
}

var treeOnClick=function(objNodo,tipoN,myidDocumento){
        indexTreeExp+=1;
        var father = 0;
        dijit.byId("dlgProgresBar").show();
	
        if(objNodo!=null){
           var sinsplit=""+objNodo.id;
	   var tipo=sinsplit.split("-")[0];
	   var id=sinsplit.split("-")[2];
           father=sinsplit.split("-")[1];
           var opcion=sinsplit.split("-")[3];
           
           if (id == father){
              father = 0; 
           }
               
	   objDocId=id;
	   console.debug("(treeOnClick) tipo->" + tipo);
	   console.debug("(treeOnClick) origen->" + origen);		
	}else{
           var tipo =tipoN;
	   var id = myidDocumento;
	   objDocId=id;
           father = 0;
        }	   
        
        if(tipo=="D" || tipo=="DP"){
               $.ajax({
			type: "GET",
			url: "doViewTrazabilidadReferencia.action?indexTreeExp="+ indexTreeExp +"&father=" + parseInt(father) + "&opcionMenu=" + opcion + "&tab=REFERENCIA",
			data: "iIdDocumentoReferencia="+id,
			success: function(msg){ 
                      
                            $("#displayIdDocumento"+myidGrid).html(msg);
			    dijit.byId("dlgProgresBar").hide() ;                                 
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

dojo.addOnLoad(function () {
    console.debug("idGrid [%s] idExpediente [%s] documentoStor [%s] idDocumento [%s]", myidGrid, myidExpediente, mydocumentoStor, myidDocumento);
    //var expediente=$("#idExpediente").val();

    var defered = service.getArbolDocumentoReferencial(myidDocumento, opcionMenu);
    defered.addCallback(function (objJSON) {
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
            autoExpand: true,
            _createTreeNode: function (args) {
                var tnode;
                tnode = new dijit._TreeNode(args); 
                tnode.labelNode.innerHTML = args.label + ((args.item.nroReferencia!=undefined && (args.item.nroReferencia.length>0 && args.item.nroReferencia!="" && args.item.nroReferencia!="0"))?(" &nbsp;<font color='blue'><b>["+args.item.nroReferencia+"]</b></font>"):"");
                
                if (args.indent > 0 && args.item.ver=="S" && (myidGrid==TIPO_GRID_FIRMAR||myidGrid==TIPO_GRID_RECIBIDOS||myidGrid==TIPO_GRID_EXPEDIENTES) ) {
                    /*objJSON aumentar campo estado check*/
                    var cb = new dijit.form.CheckBox({checked: args.item.estado=="A"});
                   // cb.placeAt(tnode.labelNode+(args.item.nroReferencia.length>0 && args.item.nroReferencia!="0"?(" <span style='background:red;'>"+args.item.nroReferencia+"</span>"):""), "first");
                    cb.placeAt(tnode.labelNode, "first");
                    cb.id = args.item.id,
                            dojo.connect(cb, "onClick", function () {
                                if (confirm("Este documento " + (cb.checked ? "podrá ser visualizado." : " será modificado. ") )){
                                   grabarChkPermiso(cb.id, (cb.checked ? "A" : "I"));
                                }
                                else {
                                    cb.active = (!cb.checked);
                                    cb.checked = (!cb.checked);
                                }
                            });
                    tnode.cb = cb;
                }
                return tnode;
            }
        }, document.createElement("div"));
        //var idGrid=$("#idGrid").val();
        dojo.byId("divTreeDocumento" + myidGrid).appendChild(arbolito.domNode);
        arbolito.startup();
        //treeOnClick(null,"D",myidDocumento);
        treeOnClick(null, "DP", myidDocumento);
    });
});

function grabarChkPermiso(id, estado) {
    dijit.byId("dlgProgresBar").show();
    var s = id.toString().split("-");
    var defered = service.updCheckPermiso(s[1], s[2], estado);
    console.log(defered);
    dijit.byId("dlgProgresBar").hide();
}



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

