function getTrazabilidad() {
    // esta funcion es para mostar la iamgen en  la columna del dataGrid
    return  "<img src='images/sigedIconos/New16.gif' id='imgTrazabilidad' alt='Doble click en la imagen para mostrar la trazabilidad del expediente' title='Doble click en la imagen para mostrar la trazabilidad del expediente'/>";
}

var lytGridBusquedaAvanzada = [
{     //Agregando la columna  que servira de enlace para mostar la trazabilidad  con la imagen   que return  getTrazabilidad.
    name: '',
    id: 'btnTrazabilidad',
    value: 'Trazabilidad',
    width: '20px',
    formatter: getTrazabilidad,
    hidden: true
},
{
    field: 'id',
    name: 'ID',
    hidden: true
},

{
    field: 'nroTramite',
    name: 'Nro Trámite',
    width: '80px'
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
    field: 'estado',
    name: 'Estado',
    width: '50px'
},/*
{
  field: 'prioridad',
  name: 'Prioridad',
  formatter: function(figurita) {
    return "<img src=\"" + figurita + "\" />"
  },
  width: '150px'
},
*/
{
    field: 'fecharecepcion',
    name: 'Fecha Creacion',
    formatter: formatterDate,
    width: '100px'
},
/*{
   field: 'fechalimite',
   name: 'Fecha Limite',
   formatter: formatterDate,
   width: '150px'
},*/
{
    field: 'expediente',
    name: 'Carpeta',
    width: '90px'
},
{
    field: 'asuntoExpediente',
    name: 'Asunto Carpeta',
    width: '200px'
},

{
    field: 'cliente',
    name: 'Cliente',
    width: '100px'
},


/*,
{
    field: 'idproceso',
    name: 'idProceso',
    hidden: true
},*/

/*{
    field: 'numeroMesaPartes',
    name: 'Numero Mesa de Partes',
    width: '80px'
},*/
            
/*{
    field: 'proceso',
    name: 'Proceso',
    width: '100px',
    hidden: true
},*/
{
    field: 'concesionario',
    name: 'Concesionario',
    width: '150px'
}




];

var doBusquedaAvanzada = function(frmToSend) {
    var arrFecha = new Array("","","","");
    var data = dijit.byId(frmToSend).attr("value");
    var sWhereToPrint = "showErrorBusquedaAvanzadaAdicional";
    console.debug("sWhereToPrint :"+sWhereToPrint) ;
    console.debug("(doBusquedaAvanzada) filtro de Busqueda Avanzada [%s]", dojo.toJson(data, true));

    console.debug("!isThereAnyThing(frmToSend):"+(!isThereAnyThing(frmToSend)));
    if(!isThereAnyThing(frmToSend)) {
        dojo.byId(sWhereToPrint).innerHTML = "Ingrese al menos un campo";
        return;
    }
    if(fechasInvalidas(frmToSend)){
        dojo.byId(sWhereToPrint).innerHTML="Si ingresa una fecha de inicio, debe ingresar una de fin. O viceversa.";
        return;
    }

    if(dijit.byId("gridBusquedaAvanzada")){
        //gridx.style.display="none";
    	dijit.byId("gridBusquedaAvanzada").destroy();
    }
    var cargando=document.getElementById("cargando");
    cargando.style.display="";
    var mensajeError=document.getElementById("mensajeError");
    mensajeError.style.display="none";
    dojo.byId(sWhereToPrint).innerHTML = "&nbsp;";
    dijit.byId("titlePaneFiltroBusquedaAvanzada").attr("open", false);
    dijit.byId("contentPaneDetailBusquedaAvanzada").attr("content", "");
    dijit.byId("contentPaneDocumentosAdicionalesBusquedaAvanzada").attr("content", "");
    dijit.byId("contentPaneDocumentosAdicionalesReferenciasBusquedaAvanzada").attr("content", "");
    
    console.debug("frmToSend:"+frmToSend);
    console.debug(" (frmToSend == 'frmBusquedaAvanzadaAdicional'):"+ (frmToSend == "frmBusquedaAvanzadaAdicional"));

    if (frmToSend == "frmBusquedaAvanzadaAdicional") {
        arrFecha[0] = dojo.byId("objFiltro.fechaDocumentoDesde").value;
        arrFecha[1] = dojo.byId("objFiltro.fechaDocumentoHasta").value;
        arrFecha[2] = dojo.byId("objFiltro.fechaExpedienteDesde").value;
        arrFecha[3] = dojo.byId("objFiltro.fechaExpedienteHasta").value;

        data.objFiltro.fechaDocumentoDesde = null;
        data.objFiltro.fechaDocumentoHasta = null;
        data.objFiltro.fechaExpedienteDesde = null;
        data.objFiltro.fechaExpedienteHasta = null;
    }
 
    service.busquedaAvanzada(frmToSend, data.objFiltro, arrFecha).addCallback(function(objJSON) {
    	if(objJSON.items!=null && objJSON.items!='undefined'){
            var storeBA = new dojo.data.ItemFileWriteStore({
                data: objJSON
            });
            var gridx = new dojox.grid.DataGrid({
                id               : "gridBusquedaAvanzada",
                jsId             : "gridBusquedaAvanzada",
                columnReordering : true,
                onRowClick       : viewDetailBusquedaAvanzada,
                rowsPerPage      : 50,
                store            : storeBA,
                structure        : lytGridBusquedaAvanzada,
                style		 : "height:95%;width:98%;"
             }, document.createElement("div"));
            dojo.byId("centralAvanzada").appendChild(gridx.domNode);
            gridx.startup();
        }
        else{
        	console.debug("sin Data");
            //document.getElementById("gridBusquedaAvanzada").style.display="none";
            cargando.style.display="none";
            mensajeError.style.display="block";
        }
        cargando.style.display="none";
    });
    
    
};

function mostrarTrazabilidad(hURL){
    dojo.xhrPost({
        url: hURL,
        load: function(data){
            dijit.byId("dlgTrazabilidad").attr("content", data);
            dijit.byId("dlgTrazabilidad").show();
        }
    });
}

var isThereAnyThing = function(frmToSend) {

    var iThereIsSomething = 0;
    var data = dijit.byId(frmToSend).attr("value");

    //DEBE HABER UNA FORMA MAS OPTIMA DE VALIDAR Q AL MENOS HAYA UN CAMPO LLENO... MEJORAR ESTO!!!
      iThereIsSomething += data.objFiltro.numeroExpediente != "" ? 1 : 0;
      iThereIsSomething += data.objFiltro.numeroDocumento != "" ? 1 : 0;
    //borrar iThereIsSomething += data.objFiltro.numeroMesaPartes != "" ? 1 : 0;
      iThereIsSomething += data.objFiltro.tipoDocumento == "" ? 0 : 1;
    //borrar iThereIsSomething += data.objFiltro.concesionario != "" ? 1 : 0;
      iThereIsSomething += data.objFiltro.cliente != "" ? 1 : 0;
      iThereIsSomething += data.objFiltro.areaDestino != "" ? 1 : 0;
    //borrar iThereIsSomething += data.objFiltro.propietario != "" ? 1 : 0;
      iThereIsSomething += data.objFiltro.proceso != "" ? 1 : 0;
      iThereIsSomething += data.objFiltro.contenido != "" ? 1 : 0;
      iThereIsSomething += data.objFiltro.asuntoexpediente != "" ? 1 : 0;
      iThereIsSomething += data.objFiltro.asuntodocumento != "" ? 1 : 0;
      iThereIsSomething += data.objFiltro.nroSuministro != "" ? 1 : 0;
    //iThereIsSomething += data.objFiltro.estadoexpediente != "" ? 1 : 0;
      iThereIsSomething += dojo.byId("objFiltro.fechaDocumentoDesde").value != "" ? 1 : 0;
      iThereIsSomething += dojo.byId("objFiltro.fechaDocumentoHasta").value != "" ? 1 : 0;
      iThereIsSomething += dojo.byId("objFiltro.fechaExpedienteDesde").value != "" ? 1 : 0;
      iThereIsSomething += dojo.byId("objFiltro.fechaExpedienteHasta").value != "" ? 1 : 0;
      iThereIsSomething += dojo.byId("objFiltro.areaOrigen").value != "" ? 1 : 0;

    /*if (frmToSend == "frmBusquedaAvanzadaAdicional") {
    //  iThereIsSomething += data.objFiltro.documentoIdentidadConcesionario != "" ? 1 : 0;
    //  iThereIsSomething += data.objFiltro.direccionPrincipalcliente != "" ? 1 : 0;
        iThereIsSomething += dojo.byId("objFiltro.fechaDocumentoDesde").value != "" ? 1 : 0;
        iThereIsSomething += dojo.byId("objFiltro.fechaDocumentoHasta").value != "" ? 1 : 0;
        iThereIsSomething += dojo.byId("objFiltro.fechaExpedienteDesde").value != "" ? 1 : 0;
        iThereIsSomething += dojo.byId("objFiltro.fechaExpedienteHasta").value != "" ? 1 : 0;
    //  iThereIsSomething += data.objFiltro.nroSuministro != "" ? 1 : 0;
    }*/

    return iThereIsSomething;
};

var resetFormBusquedaAvanzada = function(frmToReset) {
    var sWhereToPrint = "showErrorBusquedaAvanzadaAdicional";
    console.debug("(resetFormBusquedaAvanzada) Limpiando los campos del formulario [%s]", frmToReset);
    dijit.byId(frmToReset).reset();
    dojo.byId(sWhereToPrint).innerHTML = "";
};

var viewDetailBusquedaAvanzada = function(e) {
    var valorsito = this.getItem(e.rowIndex).id;
    service.verificarDocumentoPublicar(parseInt(valorsito), "B").addCallback(function(result) {
            //console.debug("(viewDetailBusquedaAvanzada) Grid [%s]. ID seleccionado [%d]", this.id, this.getItem(e.rowIndex).id);
            dijit.byId("contentPaneDetailBusquedaAvanzada").setHref("doViewDocUsuarioFinalBusqueda.action?iIdDoc=" + valorsito + "&origenDocumento=busquedaAvanzada");
            //AQUI NO HAY OTRA, ESTE METIDO EN UN JSP. SOLUCION: REHACER PRUEBAARCHDOCUMENTOS.JSP
            if (dijit.byId("divTreeExpediente")) {
                dijit.byId("divTreeExpediente").destroy();
            }

            dijit.byId("contentPaneDocumentosAdicionalesBusquedaAvanzada").setHref("doViewDocAdicionalesBusqueda.action?iIdDoc=" + valorsito + "&origenDetalleDoc=busquedaAvanzada");   
            dijit.byId("contentPaneDocumentosAdicionalesReferenciasBusquedaAvanzada").setHref("doViewDocReferencialesBusqueda.action?iIdDoc=" + valorsito);
            //SOLICITUD CT
            // dijit.byId("contentPaneDocumentosAdicionalesRespuestasBusquedaAvanzada").setHref("doViewDocRespuestas.action?iIdDoc=" + this.getItem(e.rowIndex).id);
          
          if (result.substr(1,2) == "S") {
              dijit.byId("contentPaneDocumentosAdicionalesExpedienteBusquedaAvanzada").setHref("doViewDocLegajoBusqueda.action?iIdDoc=" + valorsito);
         }else{
              dijit.byId("tabContainerDetailBusquedaAvanzada").removeChild(dijit.byId("contentPaneDocumentosAdicionalesExpedienteBusquedaAvanzada"));
              dijit.byId("contentPaneDocumentosAdicionalesExpedienteBusquedaAvanzada").destroyDescendants();
              dijit.byId("contentPaneDocumentosAdicionalesExpedienteBusquedaAvanzada").destroy();
          }
      }); 
};

var viewTrazabilidad = function(e) {
    //    alert('col' + e.cell.index)
    //    alert('row-' + e.rowIndex);
    if (e.cell.index == 0) {
        var url = "goViewSeguimiento.action?sinRol=1&iIdDocumento=" +  dijit.byId("gridBusquedaAvanzada").getItem(e.rowIndex).id;
        //        window.open(url,"myWin", "location=0,status=0,scrollbars=0,width=600,height=300,top=100,left=200");
        mostrarTrazabilidad(url); //en Dialog
    }
};

function fechasInvalidas(form){
    if(form=="frmBusquedaAvanzadaAdicional"){
        var doc=false;
        var exp=false;
        var documentoDesde=dojo.byId("objFiltro.fechaDocumentoDesde").value;
        var documentoHasta=dojo.byId("objFiltro.fechaDocumentoHasta").value;
        if(documentoDesde!=null && documentoDesde!=""){
            doc=documentoHasta==null || documentoHasta=="";
        }
        else if(documentoHasta!=null && documentoHasta!=""){
            doc=true;
        }
        var expedienteDesde=dojo.byId("objFiltro.fechaExpedienteDesde").value;
        var expedienteHasta=dojo.byId("objFiltro.fechaExpedienteHasta").value;
        if(expedienteDesde!=null && expedienteDesde!=""){
            exp=expedienteHasta==null || expedienteHasta=="";
        }
        else if(expedienteHasta!=null && expedienteHasta!=""){
            exp=true;
        }
        return doc || exp;
    }
    return false;
}

dojo.addOnLoad(function(){
    //cuando  esta cargando la pagina     que pinte el efecto de que esta cargando la pagina busquedaAvanzada
    var cargando=document.getElementById("cargando");
    dojo.byId("centralAvanzada").appendChild(cargando);
//  var cargando=document.getElementById("cargandoSimple");
//cargando.style.display="";

});