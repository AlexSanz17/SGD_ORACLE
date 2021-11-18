var proceso=function(){
	if(!dijit.byId("tabProcesoIntalio")){
		var newTab=new dojox.layout.ContentPane({
			id:			"tabProcesoIntalio",
			jsId:		"tabProcesoIntalio",
	        closable:	true,
	        href:		"proceso.jsp?data="+document.getElementById("urlIntalio").value+"&idDocumento="+document.getElementById("idDocumento").value+"&idExpediente="+document.getElementById("idExpediente").value+"&idProceso="+document.getElementById("idProceso").value+"&rol="+document.getElementById("rol").value+"&tipo="+document.getElementById("tipo").value,        
	        title:		"Proceso"
		});	
		dijit.byId("tabContainerInbox").addChild(newTab);
	}	
	dijit.byId("tabContainerInbox").selectChild(dijit.byId("tabProcesoIntalio"));	
};

var trazabilidad=function(){
	var url="goViewSeguimiento.action?iIdDocumento="+document.getElementById("idDocumento").value;
	var opciones="toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=no,width=650,height=320,top=50,left=200";
	//openDialog(url,"Trazabilidad");
	window.open(url,"",opciones);
};

var plantilla=function(){
	var opciones="toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=yes,width=600,height=550,top=50,left=200";
	var pagina="doPlantilla_verNuevo.action?idDocumento="+document.getElementById("idDocumento").value;
	window.open(pagina,"",opciones);
};

var adjuntar=function(){
	var pagina="SubirConMetadata_verSubirConMetadata.action?idDocumento="+document.getElementById("idDocumento").value;
    //openDialog(pagina,"Nuevo Documento");
	window.open(pagina,"","");
};

var versionar=function(){
	var pagina="SubirConMetadata_verVersionar.action?idDocumento="+document.getElementById("idDocumento").value;
    //openDialog(pagina,"Versionar");
	window.open(pagina,"","");
};

var enumerar=function(){
	var pagina="Numeracion_verVentanaNumeracion.action?idDocumento="+document.getElementById("idDocumento").value;
    //openDialog(pagina,"Enumerar");
	window.open(pagina,"","");
};

var nuevo=function(){
	var pagina="nuevoExpedienteUF.action?idExpediente="+document.getElementById("idExpediente").value+"&idProceso="+document.getElementById("idProceso").value;
    //openDialog(pagina,"Nuevo Expediente");
	window.open(pagina,"","");
};

var archivar=function(){
	var pagina = "Archivar_inicio.action?idDocumento="+document.getElementById("idDocumento").value;
    //openDialog(pagina,"Archivar");
	window.open(pagina,"","");
};

var otros=function(){
	var pagina = "goOpenDocumentSearch.action?iIdDoc="+document.getElementById("idDocumento").value;
    //openDialog(pagina,"Búsqueda de Documentos");
	window.open(pagina,"","");
};

var ayuda=function(){
	showOnlineHelp(document.getElementById("rol").value,document.getElementById("tipo").value);
};

dojo.addOnLoad(function(){
	new dijit.Toolbar({},"toolbar");
	
	new dijit.form.Button({
		onClick:	proceso,
		iconClass:	"iconoProceso"
	},"proceso");
	
	if(document.getElementById("trazabilidad")){
		new dijit.form.Button({
			onClick:	trazabilidad,
			iconClass:	"iconoTrazabilidad"
		},"trazabilidad");
	}
	
	if(document.getElementById("plantilla")){
		new dijit.form.Button({
			onClick:	plantilla,
			iconClass:	"dijitEditorIcon dijitEditorIconBackColor"
		},"plantilla");
	}
	
	if(document.getElementById("adjuntar")){
		new dijit.form.Button({
			onClick:	adjuntar,
			iconClass:	"dijitEditorIcon dijitEditorIconCreateLink"
		},"adjuntar");
	}
	
	if(document.getElementById("versionar")){
		new dijit.form.Button({
			onClick:	versionar,
			iconClass:	"dijitEditorIcon dijitEditorIconInsertUnorderedList"
		},"versionar");
	}
	
	if(document.getElementById("enumerar")){
		new dijit.form.Button({
			onClick:	enumerar,
			iconClass:	"dijitEditorIcon dijitEditorIconInsertOrderedList"
		},"enumerar");
	}
	
	if(document.getElementById("nuevo")){
		new dijit.form.Button({
			onClick:	nuevo,
			iconClass:	"dijitEditorIcon dijitEditorIconSelectAll"
		},"nuevo");
	}
	
	new dijit.form.DropDownButton({label: "Otros"},"otro");
	
	new dijit.Menu({},"menuOtro");
	
	if(document.getElementById("archivar")){
		new dijit.MenuItem({
			onClick:	archivar,
			iconClass:	"dijitEditorIcon dijitEditorIconPaste"
		},"archivar");
	}
	
	if(document.getElementById("otros")){
		new dijit.MenuItem({
			onClick:	otros,
			iconClass:	"dijitEditorIcon dijitEditorIconSelectAll"
		},"otros");
	}
	
	new dijit.MenuItem({
		onClick:	ayuda,
		iconClass:	"dijitEditorIcon dijitEditorIconToggleDir"
	},"ayuda");	
});

function openDialog(varPagina,varTitulo){
    var popupExternal=dijit.byId('external');
    popupExternal.attr("title",varTitulo);
    popupExternal.attr("href",varPagina);           
    popupExternal.show();
}