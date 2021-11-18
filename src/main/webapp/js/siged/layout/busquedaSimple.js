var lytGridBusquedaSimple = [
{
   field: 'id',
   name: 'ID',
   hidden: true
},
{
   field: 'nrodocumento',
   name: 'Documento',
   width: '200px'
},
{
   field: 'asuntodocumento',
   name: 'Asunto',
   width: '300px'
},
{
   field: 'fecharecepcion',
   name: 'Fecha Creacion',
   formatter: formatterDate,
   width: '100px'
},
{
   field: 'prioridad',
   name: 'Prioridad',
   width: '100px'
		  
},/*
{
	field: 'accion',
	name: 'Accion',
	width: '150px'
},*/
{
   field: 'expediente',
   name: 'Expediente',
   width: '150px'
},
{
   field: 'fechalimite',
   name: 'Fecha Limite',
   formatter: formatterDate,
   width: '150px'
},
{
   field: 'propietario',
   name: 'Propietario',
   width: '150px'
},
{
	field: 'area',
	name: 'Ubicacion actual',
	width: '100px'
},
{
   field: 'asuntoExpediente',
   name: 'Asunto Expedientexx',
   width: '300px',
   hidden: true
},

{
   field: 'cliente',
   name: 'Cliente',
   width: '100px'
},
{
   field: 'idproceso',
   name: 'idProceso',
   hidden: true
}
];

var viewDetailBusquedaSimple = function(e) {
	console.debug("(viewDetailBusquedaSimple) Grid [%s]. ID seleccionado [%d]", this.id, this.getItem(e.rowIndex).id);

	dijit.byId("contentPaneDetailBusquedaSimple").attr("href", "doViewDocUsuarioFinal.action?iIdDoc=" + this.getItem(e.rowIndex).id + "&iIdPro=" + this.getItem(e.rowIndex).idproceso);
	console.debug(" setting contentPaneDetailBusquedaSimple ..ok");
   //AQUI NO HAY OTRA, ESTE METIDO EN UN JSP. SOLUCION: REHACER PRUEBAARCHDOCUMENTOS.JSP
	if (dijit.byId("divTreeExpediente")) {
		dijit.byId("divTreeExpediente").destroy();
		console.debug(" divTreeExpediente destroy ...ok");
	}
	console.debug(" before setting content Pane "+"doViewDocAdicionales.action?iIdDoc=" + this.getItem(e.rowIndex).id + "&iIdPro=" + this.getItem(e.rowIndex).idproceso);
	dijit.byId("contentPaneDocumentosAdicionalesBusquedaSimple").attr("href", "doViewDocAdicionales.action?iIdDoc=" + this.getItem(e.rowIndex).id + "&iIdPro=" + this.getItem(e.rowIndex).idproceso + "&idGrid=20");
	console.debug(" after setting content Pan "); 
};

dojo.addOnLoad(function(){
	var gridx=document.getElementById("gridBusquedaSimple");
	/*if(gridx!=null){
		gridx.style.display="none";
	}*/
	var cargando=document.getElementById("cargandoSimple");
	cargando.style.display="";
	var mensajeError=document.getElementById("mensajeErrorSimple");
	mensajeError.style.display="none";
	
	service.getDataGridBusquedaSimple(dijit.byId("txtBusquedaSimple").attr("value")).addCallback(function(objJSON) {
		//console.debug("Numero de registros recibidos [%d]", objJSON.items.length);
		if(objJSON.items!=null){
			dijit.byId("gridBusquedaSimple").setStructure(lytGridBusquedaSimple);
			dijit.byId("gridBusquedaSimple").setStore(new dojo.data.ItemFileWriteStore({data:objJSON}));
			//document.getElementById("gridBusquedaSimple").style.display="block";
			dijit.byId("gridBusquedaSimple").startup();
		}
		else{
			//document.getElementById("gridBusquedaSimple").style.display="none";
			document.getElementById("mensajeErrorSimple").style.display="block";
		}
		document.getElementById("cargandoSimple").style.display="none";
	});
});