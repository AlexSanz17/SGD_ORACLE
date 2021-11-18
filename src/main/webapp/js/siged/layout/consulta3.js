var serviceApnC3 = new dojo.rpc.JsonService("SMDConsultaAPN.action");
var data;
var arrFecha;

var lytGridConsulta3 = [
{
    field: 'id',
    name: 'ID',
    hidden: true
},
{
    field: 'documento',
    name: 'Documento',
    width: '200px'
},
{
    field: 'asunto',
    name: 'Asunto',
    width: '300px'
},
{
    field: 'area',
    name: 'Area Remitente',
    width: '100px'
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
  width: '150px'
},
{
   field: 'accion',
   name: 'Accion',
   width: '150px'
},
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
    field: 'remitente',
    name: 'Remitente',
    width: '100px'
},
{
    field: 'cliente',
    name: 'Cliente',
    width: '100px'
},
{
    field: 'asuntoExpediente',
    name: 'Asunto Expediente',
    width: '100px',
    hidden: true
},
{
    field: 'idproceso',
    name: 'idProceso',
    hidden: true
},

/*{
    field: 'numeroMesaPartes',
    name: 'Numero Mesa de Partes',
    width: '80px'
},*/

{
    field: 'proceso',
    name: 'Proceso',
    width: '100px',
    hidden: true
},
{
    field: 'concesionario',
    name: 'Concesionario',
    width: '100px',
    hidden: true
},
{
    field: 'estado',
    name: 'Estado',
    width: '50px'
},

	
	
];

var doConsulta3 = function(frmToSend) {
	arrFecha = new Array("","","","");
	data = dijit.byId(frmToSend).attr("value");
	var sWhereToPrint = "showErrorConsulta3" ;//error en Filtro
	var correctoC3 = true;
	var varinnerhtml = "";
    var cargando=document.getElementById("cargando");
    cargando.style.display="";
	var mensajeErrorConsulta3=document.getElementById("mensajeErrorConsulta3");//error en Principal
	mensajeErrorConsulta3.style.display="none";
	
    //get
	arrFecha[0] = dojo.byId("objF.fechaDocumentoDesde").value;
    arrFecha[1] = dojo.byId("objF.fechaDocumentoHasta").value;
    
	//validacion
    if(data.objF.areaOrigen == "" || data.objF.areaOrigen == null){
    	varinnerhtml +="Filtro Area Incorrecto <BR>"; 
    	correctoC3 = false;
    }
    if(data.objF.tipoDocumento == "" || data.objF.tipoDocumento == null){
    	varinnerhtml +="Filtro Tipo Documento Incorrecto <BR>";
    	correctoC3 = false;
    }
    if( (arrFecha[0]== null || arrFecha[0] =="" )  && (arrFecha[1]!= "") ){   
    	varinnerhtml +="Ingrese las dos fechas <BR>";
    	correctoC3 = false;
    }        
     else if((arrFecha[1]== null || arrFecha[1] =="" )  && (arrFecha[0]!= "") ) {
    	varinnerhtml +="Ingrese las dos fechas <BR>";
     	correctoC3 = false;
    }
     	    
    //Imprime errores Filtro
    dojo.byId(sWhereToPrint).innerHTML = varinnerhtml;
    
    //Resultado consulta
    if(correctoC3==true){
    	
    	dijit.byId("titlePaneFiltroConsulta3").attr("open", false);
        dijit.byId("contentPaneDetailConsulta3").attr("content", "");
                
        if(data.objF.areaOrigen == "0"){
        	data.objF.areaOrigen="Todos";
        }
        if(data.objF.tipoDocumento == "0"){
        	data.objF.tipoDocumento="Todos";
        }
        
        data.objF.fechaDocumentoDesde = null;
        data.objF.fechaDocumentoHasta = null;
    	serviceApnC3.consultaDocumentoRecepcionados(data.objF, arrFecha).addCallback(function(objJSON) {
    		console.debug("Creacion objetos");
    		if(objJSON.items!=null){
    			console.debug("Lleno");
    			document.getElementById("gridConsulta3").style.display="block";
    			document.getElementById("idToolbarC3").style.display="block";
    			 var storeC3 = new dojo.data.ItemFileWriteStore({
    				 data: objJSON
    			 });
    			 var gridX = dijit.byId("gridConsulta3");
    			 gridX.setStore(storeC3);
    			 for (var i in gridX.layout.cells) {
    			       if (gridX.layout.cells[i].formato == "formatterDate") {
    			    	   gridX.layout.cells[i].formatter = formatterDate;
    			       }
    			       else if (gridX.layout.cells[i].formato == "formatterImg") {
    			    	   gridX.layout.cells[i].formatter = formatterImg;
    			       }
    			       if(gridX.layout.cells[i].ordenado){
    			          columnaOrdenada=parseInt(i);
    			          ascendente=gridX.layout.cells[i].ascendente;
    			       }
    			    }
    	     }else{
    	    	 console.debug("Vacio");
    	    	 document.getElementById("gridConsulta3").style.display="none"; 
    	    	 document.getElementById("idToolbarC3").style.display="none";
    	    	 //imprime error en Principal
    	         mensajeErrorConsulta3.style.display="block";
    	     }    		
            cargando.style.display="none";
        });
    }	
};

var doImprimirConsulta3 = function() {
	var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=1000, height=700, top=50, left=200";
	var url = "imprimirGridC3.action?tipoDocumento="+data.objF.tipoDocumento+"&areaOrigen="+data.objF.areaOrigen+"&fechaDesde="+arrFecha[0]+"&fechaHasta="+arrFecha[1]+"&nroDocumento="+data.objF.nroDocumento+"&accion="+data.objF.accion+"&asuntoDocumento="+data.objF.asuntoDocumento+"";
	window.open(url, "", opciones);	
};

var resetFormConsulta3 = function(frmToReset) {
    var sWhereToPrint = "showErrorConsulta3" ;
    console.debug("(resetFormConsulta3) Limpiando los campos del formulario [%s]", frmToReset);
    dijit.byId(frmToReset).reset();
    dojo.byId(sWhereToPrint).innerHTML = "";
};

var viewDetailConsulta3 = function(e) {
    console.debug("(viewDetailConsulta3) Grid [%s]. ID seleccionado [%d]", this.id, this.getItem(e.rowIndex).id);    
    dijit.byId("contentPaneDetailConsulta3").setHref("doViewDocUsuarioFinal.action?iIdDoc=" + this.getItem(e.rowIndex).id + "&iIdPro=" + this.getItem(e.rowIndex).idproceso);
    //AQUI NO HAY OTRA, ESTE METIDO EN UN JSP. SOLUCION: REHACER PRUEBAARCHDOCUMENTOS.JSP
    if (dijit.byId("divTreeExpediente")) {
       dijit.byId("divTreeExpediente").destroy();
    }
    dijit.byId("contentPaneDocumentosAdicionalesConsulta3").setHref("doViewDocAdicionales.action?iIdDoc=" + this.getItem(e.rowIndex).id + "&origenDetalleDoc=busquedaAvanzada" +"&iIdPro=" + this.getItem(e.rowIndex).idproceso);
};

dojo.addOnLoad(function(){
    //cuando  esta cargando la pagina     que pinte el efecto de que esta cargando la pagina busquedaAvanzada
    var cargandoConsulta3=document.getElementById("cargandoConsulta3");
    dojo.byId("centralConsulta3").appendChild(cargandoConsulta3);
    
    var structureC3 = null;
    service.getDataGrid(TIPO_GRID_CONSULTA_RECEPCIONADOS).addCallback(function(objJSON){
    	structureC3 = objJSON.structure;
    	var gridX = new dojox.grid.DataGrid({
    		id	:	"gridConsulta3",
    		columnReordering : true,
    	    //headerMenu       : dijit.byId("gridMenu"),
    	    onRowClick       : viewDetailConsulta3,
    	    rowsPerPage      : 50,
    	    //structure		 : structureC3,
    	    structure		 : lytGridConsulta3,
    	    style			 : "width:100%; height:87%;"
    	}, "gridConsulta3");
    	 gridX.startup();
    });    
});