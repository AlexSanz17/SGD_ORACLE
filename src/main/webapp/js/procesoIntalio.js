var trazabilidad=function(){
	var url="goViewSeguimiento.action?iIdDocumento="+document.getElementById("idDocumento2").value;
	var opciones="toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=no,width=650,height=320,top=50,left=200";
	//openDialog(url,"Trazabilidad");
	window.open(url,"","");
};

var plantilla=function(){
	var opciones="toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=yes,width=600,height=550,top=50,left=200";
	var pagina="doPlantilla_verNuevo.action?idDocumento="+document.getElementById("idDocumento2").value;
	window.open(pagina,"",opciones);
};

var adjuntar=function(){
	var pagina="SubirConMetadata_verSubirConMetadata.action?idDocumento="+document.getElementById("idDocumento2").value;
    //openDialog(pagina,"Nuevo Documento");
	window.open(pagina,"","");
};

var versionar=function(){
	var pagina="SubirConMetadata_verVersionar.action?idDocumento="+document.getElementById("idDocumento2").value;
    //openDialog(pagina,"Versionar");
	window.open(pagina,"","");
};

var enumerar=function(){
	var pagina="Numeracion_verVentanaNumeracion.action?idDocumento="+document.getElementById("idDocumento2").value;
    //openDialog(pagina,"Enumerar");
	window.open(pagina,"","");
};

var archivar=function(){
	var pagina = "Archivar_inicio.action?idDocumento="+document.getElementById("idDocumento2").value;
    //openDialog(pagina,"Archivar");
	window.open(pagina,"","");
};

var otros=function(){
	var pagina = "goOpenDocumentSearch.action?iIdDoc="+document.getElementById("idDocumento2").value;
    //openDialog(pagina,"Búsqueda de Documentos");
	window.open(pagina,"","");
};

var ayuda=function(){
	showOnlineHelp(document.getElementById("rol2").value,document.getElementById("tipo2").value);
};

dojo.addOnLoad(function(){
	new dijit.Toolbar({},"toolbar2");
	
	if(document.getElementById("trazabilidad2")){
		new dijit.form.Button({
			onClick:	trazabilidad,
			iconClass:	"iconoTrazabilidad"
		},"trazabilidad2");
	}
	
	if(document.getElementById("plantilla2")){
		new dijit.form.Button({
			onClick:	plantilla,
			iconClass:	"dijitEditorIcon dijitEditorIconBackColor"
		},"plantilla2");
	}
	
	if(document.getElementById("adjuntar2")){
		new dijit.form.Button({
			onClick:	adjuntar,
			iconClass:	"dijitEditorIcon dijitEditorIconCreateLink"
		},"adjuntar2");
	}
	
	if(document.getElementById("versionar2")){
		new dijit.form.Button({
			onClick:	versionar,
			iconClass:	"dijitEditorIcon dijitEditorIconInsertUnorderedList"
		},"versionar2");
	}
	
	if(document.getElementById("enumerar2")){
		new dijit.form.Button({
			onClick:	enumerar,
			iconClass:	"dijitEditorIcon dijitEditorIconInsertOrderedList"
		},"enumerar2");
	}
	
	new dijit.form.DropDownButton({label: "Otros"},"otro2");
	
	new dijit.Menu({},"menuOtro2");
	
	if(document.getElementById("archivar2")){
		new dijit.MenuItem({
			onClick:	archivar,
			iconClass:	"dijitEditorIcon dijitEditorIconPaste"
		},"archivar2");
	}
	
	if(document.getElementById("otros2")){
		new dijit.MenuItem({
			onClick:	otros,
			iconClass:	"dijitEditorIcon dijitEditorIconSelectAll"
		},"otros2");
	}
	
	new dijit.MenuItem({
		onClick:	ayuda,
		iconClass:	"dijitEditorIcon dijitEditorIconToggleDir"
	},"ayuda2");	
});

function openDialog(varPagina,varTitulo){
    var popupExternal=dijit.byId('external');
    popupExternal.attr("title",varTitulo);
    popupExternal.attr("href",varPagina);           
    popupExternal.show();
}