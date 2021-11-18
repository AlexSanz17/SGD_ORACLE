dojo.require("dijit.Toolbar");
dojo.require("dijit.form.Button");
var serviceApnC4 = new dojo.rpc.JsonService("SMDConsultaAPN.action");

var structurec4;
var storc4;
var gridXC4;
dojo.addOnLoad(function() {

	toolbar = new dijit.Toolbar({},"toolbarC4");

	var button = new dijit.form.Button({
		label: "Imprimir",
		showLabel: true,
		onClick: function(){doImprimirConsulta4();}
	});
	toolbar.addChild(button);
								
							
	serviceApnC4.consultaDocumentosEmitidos().addCallback(function(objJSON) {
		if(objJSON.items!=null){
			console.debug("Llenado Grid Consulta Documentos Remitidos : objJSON.items!=null");
			storc4 = new dojo.data.ItemFileWriteStore({
				 data: objJSON
			 });
			
			service.getDataGrid(TIPO_GRID_CONSULTA_REMITIDOS).addCallback(function(objJSON){		
				structurec4 = objJSON.structure;
				
		    	gridXC4 = new dojox.grid.DataGrid({
		    		id	:	"gridC4",
		    		columnReordering : true,
		    	    headerMenu       : dijit.byId("gridMenu"),
		    	    onRowClick       : viewDetailConsulta4,
		    	    rowsPerPage      : 50,
		    	    structure		 : structurec4,
		    	    store			 : storc4,
		    	    style			 : "width:100%; height:87%;"
		    	}, "gridC4");
		    	 gridXC4.startup();
		    	 
		    });
			
		}else{
			 console.debug("Llenado Grid Consulta Documentos Remitidos : Vacio");	
		}
	});
	
});

var doImprimirConsulta4 = function() {

	var opcionesC4 = "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=1000, height=700, top=50, left=200";
	var urlC4 = "imprimirGridC4.action?";
	window.open(urlC4, "", opcionesC4);

};

var viewDetailConsulta4 = function(e) {
	dijit.byId("contentPaneDetailConsulta4").setHref(
			"doViewDoc.action?iIdDoc=" + this.getItem(e.rowIndex).documentoReferencia
					+ "&iIdPro=" + this.getItem(e.rowIndex).idproceso);
	dijit.byId("contentPaneDocumentosAdicionalesConsulta4").setHref(
			"doViewDocAdicionales.action?iIdDoc=" + this.getItem(e.rowIndex).documentoReferencia
					+ "&origenDetalleDoc=busquedaAvanzada" + "&iIdPro="
					+ this.getItem(e.rowIndex).idproceso);
};

function mostrarTrazabilidad(hURL) {
	dojo.xhrPost({
		url : hURL,
		load : function(data) {
			dijit.byId("dlgTrazabilidadConsulta4").attr("content", data);
			dijit.byId("dlgTrazabilidadConsulta4").show();
		}
	});
};
