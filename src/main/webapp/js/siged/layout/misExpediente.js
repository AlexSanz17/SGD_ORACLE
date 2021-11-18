dojo.require("dijit.Toolbar");
dojo.require("dijit.form.Button");
var serviceApnME = new dojo.rpc.JsonService("SMDConsultaAPN.action");
var lytGridConsultaME = [
{
    field: 'expediente',
    name: 'Expediente',
    width: '210px'
},
{
    field: 'id',
    name: 'ID',
    hidden: true
},
{
    field: 'asuntoExpediente',
    name: 'Asunto',
    width: '480px'
},
{
    field: 'fechacreacion',
    name: 'Fecha Creacion',
    formatter: formatterDate,
    width: '180px'
}
]; 

var structureME;
var storME;
var gridXME;

dojo.addOnLoad(function() {
	/*var cargando = document.getElementById("cargandoConsultaME");
	
	if(cargando){
		cargando.style.display = "";
	}*/
	
	toolbar = new dijit.Toolbar({},"toolbarME");

	var button = new dijit.form.Button({
		label: "Imprimir",
		showLabel: true,
		onClick: function(){doImprimirConsultaME();}
	});
	toolbar.addChild(button);
	
	gridXME = new dojox.grid.DataGrid({
		id	:	"gridME",
		columnReordering : true,
	    rowsPerPage      : 50,
	    //structure		 : structureME,
	    structure		 : lytGridConsultaME,
	    style			 : "width:100%; height:95%;"
	}, "gridME");
	 gridXME.startup();
	
	/*
	serviceApnME.consultaMisExpedientes().addCallback(function(objJSON) {
		if(objJSON.items!=null){
			console.debug("Llenado Grid Consulta Documentos Remitidos : objJSON.items!=null");
			storME = new dojo.data.ItemFileWriteStore({
				 data: objJSON
			 });
			
			service.getDataGrid(TIPO_GRID_MIS_EXPEDIENTES).addCallback(function(objJSON){		
				structureME = objJSON.structure;
				
		    	gridXME = new dojox.grid.DataGrid({
		    		id	:	"gridME",
		    		columnReordering : true,
		    	    headerMenu       : dijit.byId("gridMenu"),
		    	    onRowClick       : viewDetailConsultaME,
		    	    rowsPerPage      : 10,
		    	    //structure		 : structureME,
		    	    structure		 : lytGridConsultaME,
		    	    store			 : storME,
		    	    style			 : "width:100%; height:95%;"
		    	}, "gridME");
		    	 gridXME.startup();
		    	 
		    });
			
		}else{
			 console.debug("Llenado Grid Consulta Documentos Remitidos : Vacio");	
		}
	});
	*/
});

/*var doImprimirConsultaME = function() {

	var opcionesME = "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=1000, height=700, top=50, left=200";
	var urlME = "imprimirGridC4.action?";
	window.open(urlME, "", opcionesME);

};*/

var viewDetailConsultaME = function(e) {
         alert("xxxx");
	
	/*dijit.byId("contentPaneDetailConsultaME").setHref(
	"doViewDoc.action?iIdDoc=485" + 
			+ "&iIdPro=434" );*/
dijit.byId("contentPaneDocumentosAdicionalesConsultaME").setHref(
	"doViewDocAdicionales.action?iIdDoc=" + this.getItem(e.rowIndex).id
			+ "&origenDetalleDoc=busquedaAvanzada" + "&iIdPro="+ this.getItem(e.rowIndex).idproceso);

};

function mostrarTrazabilidad(hURL) {
	dojo.xhrPost({
		url : hURL,
		load : function(data) {
			dijit.byId("dlgTrazabilidadConsultaME").attr("content", data);
			dijit.byId("dlgTrazabilidadConsultaME").show();
		}
	});
};

var doConsultaME = function(){
      if(dijit.byId("dlgProgresBar")!=null){
    	  dijit.byId("dlgProgresBar").show();
       }
	
       var dataForm = dijit.byId("frmConsultaME").attr("value");
       serviceApnME.consultaMisExpedientes(dataForm.objME).addCallback(function(objJSON) {
          if(objJSON.items!=null){
		    if(dijit.byId("gridME")){
		    	   dijit.byId("gridME").destroy();
		    }
			
		    var storME = new dojo.data.ItemFileWriteStore({
			 data: objJSON
		    });
			
	     gridXME = new dojox.grid.DataGrid({
                id               : "gridME",
                jsId             : "gridME",
                columnReordering : true,
             //   onRowClick       : viewDetailConsultaME,
                rowsPerPage      : 50,
                store            : storME,
                structure        : lytGridConsultaME,
                style			 : "height:98%;width:98%;"
             }, document.createElement("div"));
             dojo.byId("centralConsultaME").appendChild(gridXME.domNode);
             gridXME.startup();
			
             if(dijit.byId("dlgProgresBar")!=null){
	       	dijit.byId("dlgProgresBar").hide();
	     }
           }else{
		 console.debug("Llenado Grid Consulta Documentos Remitidos : Vacio");	
	   }
	});
};
