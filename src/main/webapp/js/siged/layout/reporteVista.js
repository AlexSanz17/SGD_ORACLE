var resetFormReporteVista = function(frmToReset) {
    var sWhereToPrint = "showErrorReporteVista";
    console.debug("(resetFormReporteVista) Limpiando los campos del formulario [%s]", frmToReset);
    dijit.byId(frmToReset).reset();
    dojo.byId(sWhereToPrint).innerHTML = "";
};

var doReporteVista = function(frmToSend) {
    var arrFecha = new Array("","","","");
    var data = dijit.byId(frmToSend).attr("value");
    var sWhereToPrint = "showErrorReporteVista";
    console.debug("sWhereToPrint :"+sWhereToPrint) ;
    console.debug("(doReporteVista) filtro para el Reporte", dojo.toJson(data, true));

    console.debug("!isThereAnyThing(frmToSend):"+(!isThereAnyThingReporte(frmToSend)));
    if(!isThereAnyThingReporte(frmToSend)) {
        dojo.byId(sWhereToPrint).innerHTML = "Ingrese el Estado del Documento";
        return;
    }
    if(fechasInvalidas(frmToSend)){
        dojo.byId(sWhereToPrint).innerHTML="Si ingresa una fecha de inicio, debe ingresar una de fin. O viceversa.";
        return;
    }

    var cargando=document.getElementById("cargandoReporte");
    cargando.style.display="";
    var mensajeError=document.getElementById("mensajeError");
    mensajeError.style.display="none";
    dojo.byId(sWhereToPrint).innerHTML = "&nbsp;";
    //dijit.byId("titlePaneFiltroReporteVista").attr("open", false);
     
    console.debug("frmToSend:"+frmToSend);
    console.debug(" (frmToSend == 'frmReporteVista'):"+ (frmToSend == "frmReporteVista"));

    if (frmToSend == "frmReporteVista") {
        arrFecha[0] = dojo.byId("objFiltroRep.fechaDocumentoDesde").value;
        arrFecha[1] = dojo.byId("objFiltroRep.fechaDocumentoHasta").value;

        data.objFiltroRep.fechaDocumentoDesde = null;
        data.objFiltroRep.fechaDocumentoHasta = null;
    }
 
    service.exportarExcelFiltrosAvanzados(frmToSend, data.objFiltroRep, arrFecha).addCallback(function(result) {
        window.open(result,"_blank");
        cargando.style.display="none";
    });
    
    
};

var isThereAnyThingReporte = function(frmToSend) {

    var iThereIsSomething = 0;
    var data = dijit.byId(frmToSend).attr("value");

    iThereIsSomething += data.objFiltroRep.estadoexpediente != "*" ? 1 : 0;

    return iThereIsSomething;
};

function fechasInvalidas(form){
    if(form=="frmReporteVista"){
        var doc=false;
        var documentoDesde=dojo.byId("objFiltroRep.fechaDocumentoDesde").value;
        var documentoHasta=dojo.byId("objFiltroRep.fechaDocumentoHasta").value;
        if(documentoDesde!=null && documentoDesde!=""){
            doc=documentoHasta==null || documentoHasta=="";
        }
        else if(documentoHasta!=null && documentoHasta!=""){
            doc=true;
        }
        return doc;
    }
    return false;
}


dojo.addOnLoad(function(){
    var cargando=document.getElementById("cargandoReporte");
    dojo.byId("centralAvanzadaReporte").appendChild(cargando);
});