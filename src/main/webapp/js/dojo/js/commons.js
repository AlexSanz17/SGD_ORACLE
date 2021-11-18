dojo.provide("js.commons");

var changeStore = function(sObjeto, sURL) {
    console.log("(changeStore) - Cambiando store del objeto [%s] usando URL [%s]", sObjeto, sURL);

    if (dijit.byId(sObjeto)) {
        dijit.byId(sObjeto).setDisplayedValue("Cargando Data...");
        dijit.byId(sObjeto).setDisabled(true);
        dijit.byId(sObjeto).store = new dojo.data.ItemFileReadStore({
            url: sURL
        });
        dijit.byId(sObjeto).setValue("");
        dijit.byId(sObjeto).setDisplayedValue("");
        dijit.byId(sObjeto).setDisabled(false);
    } else {
        console.error("(changeStore) - Objeto [%s] NO existe", sObjeto);
    }
};

var hideTBODY = function(tbToHide) {
    console.log("(hideTBODY) - tbody a ocultar [%s]", tbToHide);

    if (dojo.byId(tbToHide)) {
        dojo.byId(tbToHide).style.display = "none";
    } else {
        console.log("(hideTBODY) - [%s] NO existe", tbToHide);
    }
};

var showTBODY = function(tbToShow) {
    console.log("(showTBODY) - tbody a mostrar [%s]", tbToShow);

    if (dojo.byId(tbToShow)) {
        //dojo.byId(tbToShow).style.display = "table-row-group";
        dojo.byId(tbToShow).style.display = "";
    } else {
        console.log("(showTBODY) - [%s] NO existe", tbToShow);
    }
};

var updateStore = function(sParametro, sObjeto) {
    console.log("(updateStore) - Actualizando store del objeto [%s] con parametros [%s]", sObjeto, sParametro);

    if (sObjeto == "cliente") {
        //changeStore("objDocumento.expediente.cliente.numeroIdentificacion", "obtenerClientePorTipoIdentificacion.action?sTipoIdentificacion=" + sParametro);
        changeStore("objDocumento.expediente.cliente.numeroIdentificacion", "obtenerDataClienteCxb.action?sTipoIdentificacion=" + sParametro);
    } else if (sObjeto == "cliente_qas") {
        changeStore("objDD.strNroIdentificacion", "obtenerClientePorTipoIdentificacion.action?sTipoIdentificacion=" + sParametro);
    } else if (sObjeto == "provinciaNC1") {
        changeStore("objCliente.ubigeoPrincipal.provincia.idprovincia", "listaProvincias.action?idDepartamento=" + sParametro);
    } else if (sObjeto == "distritoNC1") {
        changeStore("objCliente.ubigeoPrincipal.iddistrito", "listaDistritos.action?idProvincia=" + sParametro);
    } else if (sObjeto == "provinciaNC2") {
        changeStore("objCliente.ubigeoAlternativo.provincia.idprovincia", "listaProvincias.action?idDepartamento=" + sParametro);
    } else if (sObjeto == "distritoNC2") {
        changeStore("objCliente.ubigeoAlternativo.iddistrito", "listaDistritos.action?idProvincia=" + sParametro);
    } else if (sObjeto == "provinciaNC1Stor") {
        changeStore("objClienteStor.ubigeoPrincipal.provincia.idprovincia", "listaProvincias.action?idDepartamento=" + sParametro);
    } else if (sObjeto == "distritoNC1Stor") {
        changeStore("objClienteStor.ubigeoPrincipal.iddistrito", "listaDistritos.action?idProvincia=" + sParametro);
    } else if (sObjeto == "provinciaNC2Stor") {
        changeStore("objClienteStor.ubigeoAlternativo.provincia.idprovincia", "listaProvincias.action?idDepartamento=" + sParametro);
    } else if (sObjeto == "distritoNC2Stor") {
        changeStore("objClienteStor.ubigeoAlternativo.iddistrito", "listaDistritos.action?idProvincia=" + sParametro);
    } else if (sObjeto == "provincia1") {
        changeStore("objDocumento.expediente.cliente.ubigeoPrincipal.provincia.idprovincia", "listaProvincias.action?idDepartamento=" + sParametro);
    } else if (sObjeto == "distrito1") {
        changeStore("objDocumento.expediente.cliente.ubigeoPrincipal.iddistrito", "listaDistritos.action?idProvincia=" + sParametro);
    } else if (sObjeto == "provincia2") {
        changeStore("objDocumento.expediente.cliente.ubigeoAlternativo.provincia.idprovincia", "listaProvincias.action?idDepartamento=" + sParametro);
    } else if (sObjeto == "distrito2") {
        changeStore("objDocumento.expediente.cliente.ubigeoAlternativo.iddistrito", "listaDistritos.action?idProvincia=" + sParametro);
    } else if (sObjeto == "tipodocumentosinstor") {
        changeStore("objDocumento.tipoDocumento.idtipodocumento", "autocompletarAllTipoDocumento.action?iWithoutStor=" + sParametro);
    }
};
