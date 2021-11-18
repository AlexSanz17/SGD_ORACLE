<?xml version="1.0" encoding="UTF-8" ?>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="java.util.List"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es">
    <head>

        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <%--<META http-equiv=Pragma content=no-cache/>
        <Meta http-equiv="expires" content="0"/>
        <Meta http-equiv="cache-control" content="no-cache"/>--%>
        <title>Informacion Principal</title>

        <s:if test="enVentana">
            <%@ include file="/pages/util/progressBar.jsp"%>
            <%-- Desarrollo - Estilos Siged --%>
            <link type="text/css" rel="stylesheet" href="css/styleSiged.css" />
            <link type="text/css" rel="stylesheet" href="js/dojo/css/styleDojo.css" />

            <script type="text/javascript">
                var djConfig = {
                    isDebug: false,
                    parseOnLoad: true
                };
            </script>
            <script type="text/javascript" src="js/dojobuild/dojo/dojo.js"></script>
            <%-- Desarrollo - API Siged --%>
            <script type="text/javascript" src="js/dojobuild/js/requires.js"></script>
            <script type="text/javascript" src="js/dojo/js/commons.js"></script>
        </s:if>


        <script type="text/javascript">
            dojo.require("dijit.form.NumberTextBox");
            var mostrarReclamante = "<s:property value="mostrarReclamante" />";
            var mostrarConcesionario = "<s:property value="mostrarConcesionario" />";
//            alert("mostrarrr->" + mostrarConcesionario);
            var STOR_IDEXPEDIENTE = "<s:property value='documento.expediente.idexpediente' />";
            var STOR_PROCESO = "<s:property value='objDD.strProceso' />";
            var STOR_IDPROCESO = "<s:property value='documento.expediente.proceso.idproceso' />";
            var STOR_NROEXPEDIENTE = "<s:property value='objDD.strReferencia' />";
            var RECLAMANTE_DEPARTAMENTO = "<s:if test="desactivado"><s:property value='documento.expediente.cliente.ubigeoPrincipal.provincia.departamento.iddepartamento' /></s:if><s:else><s:property value='documento.expediente.expedientestor.reclamanteUbigeoReal.provincia.departamento.iddepartamento' /></s:else>";
            var RECLAMANTE_PROVINCIA = "<s:if test="desactivado"><s:property value='documento.expediente.cliente.ubigeoPrincipal.provincia.idprovincia' /></s:if><s:else><s:property value='documento.expediente.expedientestor.reclamanteUbigeoReal.provincia.idprovincia' /></s:else>";
            var RECLAMANTE_DISTRITO = "<s:if test="desactivado"><s:property value='documento.expediente.cliente.ubigeoPrincipal.iddistrito' /></s:if><s:else><s:property value='documento.expediente.expedientestor.reclamanteUbigeoReal.iddistrito' /></s:else>";
            var RECLAMANTE_DEPARTAMENTOALTERNATIVO = "<s:if test="desactivado"><s:property value='documento.expediente.cliente.ubigeoAlternativo.provincia.departamento.iddepartamento' /></s:if><s:else><s:property value='documento.expediente.expedientestor.reclamanteUbigeoProcesal.provincia.departamento.iddepartamento' /></s:else>";
            var RECLAMANTE_PROVINCIAALTERNATIVA = "<s:if test="desactivado"><s:property value='documento.expediente.cliente.ubigeoAlternativo.provincia.idprovincia' /></s:if><s:else><s:property value='documento.expediente.expedientestor.reclamanteUbigeoProcesal.provincia.idprovincia' /></s:else>";
            var RECLAMANTE_DISTRITOALTERNATIVO = "<s:if test="desactivado"><s:property value='documento.expediente.cliente.ubigeoAlternativo.iddistrito' /></s:if><s:else><s:property value='documento.expediente.expedientestor.reclamanteUbigeoProcesal.iddistrito' /></s:else>";
            <%--var RECLAMANTE_IDRECLAMANTE = "<s:if test="desactivado"><s:property value='documento.expediente.cliente.numeroIdentificacion' /></s:if><s:else><s:property value='documento.expediente.expedientestor.reclamante.idCliente' /></s:else>";--%>
            var RECLAMANTE_IDRECLAMANTE = "<s:if test="desactivado"><s:property value='documento.expediente.cliente.numeroIdentificacion' /></s:if><s:else><s:property value='documento.expediente.expedientestor.reclamante.numeroIdentificacion' /></s:else>";
            var RECLAMANTE_CODDRECLAMANTE = "<s:if test="desactivado"><s:property value='documento.expediente.cliente.idCliente' /></s:if><s:else><s:property value='documento.expediente.expedientestor.reclamante.numeroIdentificacion' /></s:else>";
            <%-- var RECLAMANTE_CONCESIONARIO = "<s:if test="desactivado"><s:property value='objConcesionario.idConcesionario' /></s:if><s:else><s:property value='documento.expediente.concesionario.idConcesionario' /></s:else>";--%>
            var RECLAMANTE_CONCESIONARIO = "<s:if test="desactivado"><s:property value='objConcesionario.idConcesionario' /></s:if><s:else><s:if test="mostrarConcesionario"><s:property value='documento.expediente.cliente.idCliente' /></s:if><s:else><s:property value='documento.expediente.concesionario.idConcesionario' /></s:else></s:else>";
//                alert("concesionario->" + RECLAMANTE_CONCESIONARIO);
//                alert("reclamante->" + RECLAMANTE_IDRECLAMANTE);
            var DESCTIVAR_MOTIVO_STOR = "<s:property value='desactivado' />";
            var primeraVezStor;
            //            var storeClientes = new dojox.data.QueryReadStore({url: "obtenerReclamantePorNombre.action?mostrarReclamante="+mostrarReclamante});
            var storeClientes = new dojox.data.QueryReadStore({url: "obtenerClientePorNombre.action"});
            var serviceStor = new dojo.rpc.JsonService("SMDAction.action");
            var lytGridMotivoSTOR = [
                {
                    field : 'motivo',
                    name : 'Motivo',
                    width : '200px'
                }, {
                    field : 'submotivo',
                    name : 'Sub Motivo',
                    width : '200px'
                }, {
                    field : 'monto',
                    name : 'Monto',
                    width : '200px'
                }, {
                    editable : true,
                    field : "selected",
                    name : "<input type='checkbox' id='chkHeaderSubmotivo' name='chkHeaderSubmotivo' onclick='checkAllSubmotivo()'>",
                    noresize : true,
                    type : dojox.grid.cells.Bool,
                    width : "20px"
                }, {
                    field : 'codigo',
                    name : 'ID',
                    hidden : true
                }];

            var lytGridSuministroSTOR = [
                {
                    field : 'nroSuministro',
                    name : 'Nº Suministro',
                    width : '200px'
                }, {
                    editable : true,
                    field : "selected",
                    name : "<input type='checkbox' id='chkHeaderSuministro' name='chkHeaderSuministro' onclick='checkAllSuministro()'>",
                    noresize : true,
                    type : dojox.grid.cells.Bool,
                    width : "20px"
                }, {
                    field : 'codigo',
                    name : 'ID',
                    hidden : true
                }];

            var sortMeSTOR = function(iColumn){
                if (iColumn == 4) {
                    return false;
                } else {
                    return true;
                }
            };

            var checkAllSubmotivo = function() {
                var grid = dijit.byId("gridMotivoSTOR");
                dojo.forEach(grid.store._arrayOfAllItems, function(item) {
                    grid.store.setValue(item, "selected", dojo.byId("chkHeaderSubmotivo").checked);
                });
            };

            var checkAllSuministro = function() {
                var grid = dijit.byId("gridSuministros");
                dojo.forEach(grid.store._arrayOfAllItems, function(item) {
                    grid.store.setValue(item, "selected", dojo.byId("chkHeaderSuministro").checked);
                });
            };

            var eliminarSubmotivosSTOR = function(){
                var grid = dijit.byId("gridMotivoSTOR");
                var parameters = new Array();

                dojo.forEach(grid.store._arrayOfAllItems, function(item) {
                    if (grid.store.getValue(item, "selected")) {
                        parameters.push(grid.store.getValue(item, "codigo"));
                    }
                });

                console.debug("Reemplazos a eliminar [%s] [%d]", parameters, parameters.length);

                if (parameters.length) {
                    if (confirm("Desea retirar los submotivos seleccionados?")) {
                        dojo.xhrPost( {
                            url : "doStor_retirarSubmotivos.action",
                            content : {
                                strAEliminar : parameters,
                                campoA		 : STOR_IDEXPEDIENTE
                            },
                            mimetype : "text/html",
                            load : function() {
                                serviceStor.getSubmotivosSTOR(parseInt(STOR_IDEXPEDIENTE)).addCallback(function(objJSON) {
                                    //                                    dijit.byId("gridMotivoSTOR").attr("structure", lytGridMotivoSTOR);
                                    dijit.byId("gridMotivoSTOR").setStore(new dojo.data.ItemFileWriteStore({
                                        data : objJSON
                                    }));
                                });
                                grid.selection.deselectAll();
                            }
                        });
                    }
                } else {
                    alert("Debe seleccionar al menos un submotivo para retirarlo del expediente");
                }
            };

            var eliminarSuministro = function(){
                var grid = dijit.byId("gridSuministros");
                var parameters = new Array();

                dojo.forEach(grid.store._arrayOfAllItems, function(item) {
                    if (grid.store.getValue(item, "selected")) {
                        parameters.push(grid.store.getValue(item, "codigo"));
                    }
                });

                console.debug("Reemplazos a eliminar [%s] [%d]", parameters, parameters.length);

                if (parameters.length) {
                    if (confirm("Desea retirar los suministros seleccionados?")) {
                        dojo.xhrPost( {
                            url : "doStor_retirarSuministros.action",
                            content : {
                                strAEliminar : parameters
                            },
                            mimetype : "text/html",
                            load : function() {
                                serviceStor.getSuministrosSTOR(parseInt(STOR_IDEXPEDIENTE)).addCallback(function(objJSON) {
                                    //                                    dijit.byId("gridSuministros").attr("structure", lytGridSuministroSTOR);
                                    dijit.byId("gridSuministros").setStore(new dojo.data.ItemFileWriteStore({
                                        data : objJSON
                                    }));
                                });
                                grid.selection.deselectAll();
                            }
                        });
                    }
                } else {
                    alert("Debe seleccionar al menos un suministro para retirarlo del expediente");
                }
            };

            var agregarSubmotivosSTOR = function(){
                dijit.byId("dlgAgregarSubmotivo").show();
            };

            var agregarSuministro = function(){
                dijit.byId("dlgAgregarSuministro").show();
            };

            var changeStoreSTOR = function(objFS, sURL) {
                console.log("[changeStoreSTOR] - Cambiando store del objeto [" + objFS + "]");
                console.log("[changeStoreSTOR] - Usando action [" + sURL + "]");

                dijit.byId(objFS).setDisplayedValue("Cargando Data...");
                dijit.byId(objFS).setDisabled(true);
                dijit.byId(objFS).store = new dojo.data.ItemFileReadStore({
                    url: sURL
                });
                dijit.byId(objFS).setValue("");
                dijit.byId(objFS).setDisplayedValue("");
                dijit.byId(objFS).setDisabled(false);
            };

            var updateStoreReclamante = function(){
                var txtRecl = dijit.byId("fs_reclamanteID").getDisplayedValue();
                if(txtRecl != undefined && txtRecl !=""){
                    changeStoreSTOR("fs_reclamanteID", "obtenerClientePorNombre.action?sTipoIdentificacion=" + txtRecl);
                }
            };

            var updateStoreSTOR = function(sParametro, sObjeto) {
                console.log("[updateStoreSTOR] - sParametro [" + sParametro + "] sObjeto [" + sObjeto + "]");
                if (sParametro == undefined || sParametro == "") {
                    return;
                }
                if (sObjeto == "provincia1") {
                    changeStoreSTOR("expediente.expedientestor.reclamanteUbigeoReal.provincia.idprovincia", "listaProvincias.action?idDepartamento=" + sParametro);
                } else if (sObjeto == "distrito1") {
                    changeStoreSTOR("expediente.expedientestor.reclamanteUbigeoReal.iddistrito", "listaDistritos.action?idProvincia=" + sParametro);
                } else if (sObjeto == "provincia2") {
                    changeStoreSTOR("expediente.expedientestor.reclamanteUbigeoProcesal.provincia.idprovincia", "listaProvincias.action?idDepartamento=" + sParametro);
                } else if (sObjeto == "distrito2") {
                    changeStoreSTOR("expediente.expedientestor.reclamanteUbigeoProcesal.iddistrito", "listaDistritos.action?idProvincia=" + sParametro);
                }else if (sObjeto == "submotivos") {
                    changeStoreSTOR("submotivoExpediente.submotivo.idsubmotivo", "listaSubMotivos.action?idDependencia1=" + sParametro);
                }
            };

            var fillDataConcesionario = function(){
                var cConces = dijit.byId("expediente.concesionario.idConcesionario").attr("value");
                if(cConces==null || cConces == "") {
                    cConces = "0";
                }
                serviceStor.SMDCompletarConcesionario(cConces).addCallback(function(json) {
                    dojo.forEach(json.items, function(item) {
                        document.getElementById("txt_conDireccion").innerHTML = item.direccion;
                        document.getElementById("txt_conCorreo").innerHTML = item.correo;
                    });
                });
            };

            var fillDataReclamante = function(){
                if(dijit.byId("expediente.expedientestor.reclamante.numeroIdentificacion").attr("value") != ""){
                    RECLAMANTE_IDRECLAMANTE = dijit.byId("expediente.expedientestor.reclamante.numeroIdentificacion").attr("value");
                    serviceStor.SMDCompletarCliente(RECLAMANTE_IDRECLAMANTE).addCallback(function(json) {
                        dojo.forEach(json.items, function(item) {
                            RECLAMANTE_PROVINCIA = item.provincia;
                            RECLAMANTE_DISTRITO = item.distrito;
                            RECLAMANTE_PROVINCIAALTERNATIVA = item.provinciaAlternativa;
                            RECLAMANTE_DISTRITOALTERNATIVO = item.distritoAlternativo;
                            dijit.byId("expediente.expedientestor.reclamanteUbigeoReal.provincia.departamento.iddepartamento").attr("displayedValue", item.departamento);
                            dijit.byId("expediente.expedientestor.reclamanteUbigeoProcesal.provincia.departamento.iddepartamento").attr("displayedValue", item.departamentoAlternativo);
                            dijit.byId("expediente.expedientestor.reclamanterazonsocial").attr("value", item.nombre);
                            dijit.byId("expediente.expedientestor.reclamanterepresentantelegal").attr("value", item.representante);
                            dijit.byId("expediente.expedientestor.reclamantedireccionreal").attr("value", item.direccion);
                            dijit.byId("expediente.expedientestor.reclamantedireccionprocesal").attr("value", item.direccionAlternativa);
                            dijit.byId("expediente.expedientestor.reclamantecorreo").attr("value", item.correo);
                            dijit.byId("expediente.expedientestor.reclamantetelefono").attr("value", item.telefono);
                            dijit.byId("expediente.expedientestor.reclamantefax").attr("value", "");
                            //                            dijit.byId("expediente.expedientestor.reclamantenrosuministro").attr("value", "");
                        });
                    });
                    dijit.byId("expediente.expedientestor.reclamante.numeroIdentificacion").attr("value", RECLAMANTE_IDRECLAMANTE);
                }
            };

            var nuevoReclamante = function(){
                dijit.byId("dlgNuevoCliente").show();
            };

            var actualizarPrincipalSTOR = function(){
                //                dijit.byId("btnActualizarSTOR").attr("disabled", true);
                var conerrorStor = false;
                var mensajeStor = "Antes de guardar verifique lo siguiente: \n";
                //                if(dijit.byId("expediente.concesionario.idConcesionario").attr("value")==""){
                //                    mensajeStor += "- Debe seleccionar un concesionario \n";
                //                    conerrorStor = true;
                //                }
                //                if(dijit.byId("expediente.expedientestor.nroresolucion").attr("value")==""){
                //                    mensajeStor += "- Debe escribir un numero de resolucion \n";
                //                    conerrorStor = true;
                //                }
                //                if(RECLAMANTE_IDRECLAMANTE==""||RECLAMANTE_IDRECLAMANTE==null){
                //                    mensajeStor += "- Debe seleccionar un reclamante \n";
                //                    conerrorStor = true;
                //                }
                //                if(dijit.byId("expediente.expedientestor.reclamanterazonsocial").attr("value")==""){
                //                    mensajeStor += "- Debe especificar el nombre o razon social del reclamante \n";
                //                    conerrorStor = true;
                //                }
                //                if(dijit.byId("expediente.expedientestor.reclamantenrosuministro").attr("value")==""){
                //                    mensajeStor += "- Debe especificar un numero de suministro \n";
                //                    conerrorStor = true;
                //                }
                //Validar que el ubigeo real del reclamante sea ingresadpo
                var ubigeoReal = dijit.byId("expediente.expedientestor.reclamanteUbigeoReal.iddistrito");
                if(ubigeoReal==null || ubigeoReal==""){
                    mensajeStor += "- Debe seleccionar el ubigeo real del reclamante \n";
                    conerrorStor = true;
                }

                //Validar que en caso se ingrese el Nª de suministro, este debe tener formato numérico
                //                if(dijit.byId("expediente.expedientestor.reclamantenrosuministro").attr("value")!=""){
                //                    var regExp = /^[0-9]+$/ ;
                //                    var exp = dijit.byId("expediente.expedientestor.reclamantenrosuministro").attr("value");
                //                    if (!regExp.test(exp)) {
                //                        mensajeStor += "- El Nª de suministro solo debe tener valores numericos \n";
                //                        conerrorStor = true;
                //                    }
                //                }

                if(conerrorStor){
                    alert(mensajeStor);
                    dijit.byId("btnActualizarSTOR").attr("disabled", false);
                    return;
                }
                dijit.byId('dlgProgresBar').show();
                dojo.xhrPost({
                    url: "doStor_registrarDatosPrincipalesStor.action",
                    content: {
                        campoA : STOR_IDEXPEDIENTE,
                        campoB : RECLAMANTE_IDRECLAMANTE
                    },
                    form: dojo.byId("formPrincipalSTOR"),
                    mimetype: "text/html",
                    load: function(data) {
                        dijit.byId('dlgProgresBar').hide();
                        //                        dijit.byId('contentPaneDatosEnvio').attr("content", data);
                        alert("Se actualizaron correctamente los datos");
                        dijit.byId("tabContainerDetail").selectChild(dijit.byId("contentPaneDatosEnvio"));
                    }
                });
            };

            var init = function() {
                //            function load() {
                if(RECLAMANTE_IDRECLAMANTE==""){
                    RECLAMANTE_IDRECLAMANTE = "-";
                }

                new dijit.form.FilteringSelect({
                    id             	: "expediente.concesionario.idConcesionario",
                    jsId           	: "expediente.concesionario.idConcesionario",
                    name           	: "expediente.concesionario.idConcesionario",
                    store          	: new dojo.data.ItemFileReadStore({
                        url: "autocompletarConcesionario.action"
                    }),
                    searchAttr		: "label",
                    hasDownArrow	: true,
                    highlightMatch 	: "all",
                    invalidMessage 	: "Seleccione un Concesionario",
                    onChange       	: fillDataConcesionario,
                    required       	: true,
                    style          	: "width:300px"
                }, "fsIdConcesionario");

                new dijit.form.FilteringSelect({
                    id             	: "expediente.expedientestor.reclamante.numeroIdentificacion",
                    jsId           	: "expediente.expedientestor.reclamante.numeroIdentificacion",
                    name           	: "expediente.expedientestor.reclamante.numeroIdentificacion",
                    store			: storeClientes,
                    searchAttr	 	: "label",
                    autoComplete	: false,
                    hasDownArrow	: false,
                    highlightMatch 	: "all",
                    invalidMessage 	: "Seleccione un reclamante",
                    onChange	 	: fillDataReclamante,
                    style          	: "width:300px",
                    displayedValue 	: RECLAMANTE_IDRECLAMANTE,
                    _onBlur			: function(){this.displayedValue = RECLAMANTE_IDRECLAMANTE}
                }, "fs_reclamanteID");
                new dijit.form.FilteringSelect({
                    id             	: "expediente.expedientestor.reclamanteUbigeoReal.provincia.departamento.iddepartamento",
                    jsId           	: "expediente.expedientestor.reclamanteUbigeoReal.provincia.departamento.iddepartamento",
                    name           	: "expediente.expedientestor.reclamanteUbigeoReal.provincia.departamento.iddepartamento",
                    store          	: new dojo.data.ItemFileReadStore({
                        url: "listaDepartamentos.action"
                    }),
                    searchAttr     	: "label",
                    autoComplete   	: false,
                    hasDownArrow   	: true,
                    highlightMatch 	: "all",
                    invalidMessage 	: "Seleccione un departamento",
                    onChange       	: function(){
                        if(this.value != ""){
                            updateStoreSTOR(this.value, 'provincia1');
                            dijit.byId("expediente.expedientestor.reclamanteUbigeoReal.provincia.idprovincia").attr("value", RECLAMANTE_PROVINCIA);
                        }
                    },
                    required       	: false
                },"fsDepartamentoRec_R");

                new dijit.form.FilteringSelect({
                    id             	: "expediente.expedientestor.reclamanteUbigeoReal.provincia.idprovincia",
                    jsId           	: "expediente.expedientestor.reclamanteUbigeoReal.provincia.idprovincia",
                    name           	: "expediente.expedientestor.reclamanteUbigeoReal.provincia.idprovincia",
                    searchAttr     	: "label",
                    autoComplete   	: false,
                    hasDownArrow   	: true,
                    highlightMatch 	: "all",
                    invalidMessage 	: "Seleccione una provincia",
                    onChange       	: function(){
                        if(this.value != ""){
                            updateStoreSTOR(this.value, 'distrito1');
                            dijit.byId("expediente.expedientestor.reclamanteUbigeoReal.iddistrito").attr("value", RECLAMANTE_DISTRITO);
                        }
                    },
                    required       	: false
                },"fsProvinciaRec_R");

                new dijit.form.FilteringSelect({
                    id             	: "expediente.expedientestor.reclamanteUbigeoReal.iddistrito",
                    jsId           	: "expediente.expedientestor.reclamanteUbigeoReal.iddistrito",
                    name           	: "expediente.expedientestor.reclamanteUbigeoReal.iddistrito",
                    searchAttr     	: "label",
                    autoComplete   	: false,
                    hasDownArrow   	: true,
                    highlightMatch 	: "all",
                    invalidMessage 	: "Seleccione un distrito",
                    required       	: false
                }, "fsDistritoRec_R");

                new dijit.form.FilteringSelect({
                    id             	: "expediente.expedientestor.reclamanteUbigeoProcesal.provincia.departamento.iddepartamento",
                    jsId           	: "expediente.expedientestor.reclamanteUbigeoProcesal.provincia.departamento.iddepartamento",
                    name           	: "expediente.expedientestor.reclamanteUbigeoProcesal.provincia.departamento.iddepartamento",
                    store          	: new dojo.data.ItemFileReadStore({
                        url: "listaDepartamentos.action"
                    }),
                    searchAttr     	: "label",
                    autoComplete   	: false,
                    hasDownArrow   	: true,
                    highlightMatch 	: "all",
                    invalidMessage 	: "Seleccione un departamento",
                    onChange       	: function(){
                        if(this.value != null){
                            updateStoreSTOR(this.value, 'provincia2');
                            dijit.byId("expediente.expedientestor.reclamanteUbigeoProcesal.provincia.idprovincia").attr("value", RECLAMANTE_PROVINCIAALTERNATIVA);
                        }
                    },
                    required       	: false
                },"fsDepartamentoRec_P");

                new dijit.form.FilteringSelect({
                    id             	: "expediente.expedientestor.reclamanteUbigeoProcesal.provincia.idprovincia",
                    jsId           	: "expediente.expedientestor.reclamanteUbigeoProcesal.provincia.idprovincia",
                    name           	: "expediente.expedientestor.reclamanteUbigeoProcesal.provincia.idprovincia",
                    searchAttr     	: "label",
                    autoComplete   	: false,
                    hasDownArrow   	: true,
                    highlightMatch 	: "all",
                    invalidMessage 	: "Seleccione una provincia",
                    onChange       	: function(){
                        updateStoreSTOR(this.value, 'distrito2');
                        dijit.byId("expediente.expedientestor.reclamanteUbigeoProcesal.iddistrito").attr("value", RECLAMANTE_DISTRITOALTERNATIVO);
                    },
                    required       	: false
                },"fsProvinciaRec_P");

                new dijit.form.FilteringSelect({
                    id             	: "expediente.expedientestor.reclamanteUbigeoProcesal.iddistrito",
                    jsId           	: "expediente.expedientestor.reclamanteUbigeoProcesal.iddistrito",
                    name           	: "expediente.expedientestor.reclamanteUbigeoProcesal.iddistrito",
                    searchAttr     	: "label",
                    autoComplete   	: false,
                    hasDownArrow   	: true,
                    highlightMatch 	: "all",
                    invalidMessage 	: "Seleccione un distrito",
                    required       	: false
                }, "fsDistritoRec_P");

                serviceStor.getSubmotivosSTOR(parseInt(STOR_IDEXPEDIENTE)).addCallback(function(objJSON) {
                    dijit.byId("gridMotivoSTOR").attr("structure", lytGridMotivoSTOR);
                    dijit.byId("gridMotivoSTOR").setStore(new dojo.data.ItemFileWriteStore({
                        data : objJSON
                    }));
                });

                serviceStor.getSuministrosSTOR(parseInt(STOR_IDEXPEDIENTE)).addCallback(function(objJSON) {
                    dijit.byId("gridSuministros").attr("structure", lytGridSuministroSTOR);
                    dijit.byId("gridSuministros").setStore(new dojo.data.ItemFileWriteStore({
                        data : objJSON
                    }));
                });

                dijit.byId("expediente.expedientestor.reclamanteUbigeoReal.provincia.departamento.iddepartamento").attr("value", RECLAMANTE_DEPARTAMENTO);
                dijit.byId("expediente.expedientestor.reclamanteUbigeoProcesal.provincia.departamento.iddepartamento").attr("value", RECLAMANTE_DEPARTAMENTOALTERNATIVO);
                dijit.byId("expediente.concesionario.idConcesionario").attr("value", RECLAMANTE_CONCESIONARIO);
                //                if(DESCTIVAR_MOTIVO_STOR=="true"){
                //                    dijit.byId("btn_AgregarMotivoSTOR").attr("disabled", true);
                //                    dijit.byId("btn_EliminarMotivoSTOR").attr("disabled", true);
                //                }

            }
            dojo.addOnLoad(function() {
                init();
                dijit.byId("expediente.concesionario.idConcesionario").attr("value", RECLAMANTE_CONCESIONARIO);
                limpiarCodigoReclamante();
            });
            function limpiarCodigoReclamante() {
                if(mostrarReclamante == "false") {
                    alert("no muestra");
                    dijit.byId("expediente.expedientestor.reclamante.numeroIdentificacion").attr("displayedValue","");
                    dijit.byId("expediente.expedientestor.reclamante.numeroIdentificacion").attr("value","0");
                }
            }
        </script>
    </head>
    <body class="soria" style="overflow:auto">
        <table width="100%">
            <tr>
                <td height="14" colspan="8"></td>
            </tr>
            <tr>
                <td style="height:14px;" colspan="8">
                    <div dojoType="dijit.form.Form" id="formPrincipalSTOR" jsId="formPrincipalSTOR">
                        <table border="1" align="center" bordercolor="#669BCD" bgcolor="#BFD9F1" width="98%">
                            <tr>
                                <td>
                                    <table>
                                        <tr>
                                            <td style="width: 2%"></td>
                                            <td style="width: 15%"></td>
                                            <td style="width: 15%"></td>
                                            <td style="width: 15%"></td>
                                            <td style="width: 15%"></td>
                                            <td style="width: 15%"></td>
                                            <td style="width: 15%"></td>
                                            <td style="width: 2%"></td>
                                        </tr>
                                        <tr>
                                            <td colspan="8" class="label">DATOS PRINCIPALES</td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td height="25" align="left">Proceso</td>
                                            <td class="label"><s:property value="objDD.strProceso" /></td>
                                            <td height="25" align="left">Nro Expediente</td>
                                            <td colspan="4" align="left" class="label"><s:property value="objDD.strReferencia" /></td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td height="25" align="left">Remitente</td>
                                            <td colspan="6" class="label"><s:property value="objDD.strNroIdentificacion" /> - <s:property value="objDD.strRazonSocial" /></td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td height="25" align="left">Usuario Registro</td>
                                            <td align="left" class="label"><s:property value="usuarioRegistro" /></td>
                                            <td height="25" align="left">Fecha Registro</td>
                                            <td align="left" class="label"><s:property value="objDD.strFechaAccion" /></td>
                                            <td height="25" align="left">Fecha Vencimiento</td>
                                            <td align="left" class="label"><s:property value="objDD.strFechaLimiteAtencion" /></td>
                                            <td></td>
                                        </tr>
                                        <tr>
                                            <td colspan="8" height="15">
                                                <hr />
                                            </td>
                                        </tr>
                                        <tr>
                                            <td colspan="6" height="15" class="label">DATOS DEL CONCESIONARIO</td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td height="25" align="left">Raz&oacute;n Social</td>
                                            <td align="left"><div id="fsIdConcesionario" name="fsIdConcesionario" /></td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td height="25" align="left">Direcci&oacute;n</td>
                                            <td class="label" colspan="3" align="left" id="txt_conDireccion"></td>
                                            <td height="25" align="left">Correo Electr&oacute;nico</td>
                                            <td class="label" align="left" id="txt_conCorreo"></td>
                                            <td></td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td height="25" align="left">Nro. Resoluci&oacute;n</td>
                                            <td align="left"><input dojoType="dijit.form.TextBox"
                                                                    id="expediente.expedientestor.nroresolucion"
                                                                    name="expediente.expedientestor.nroresolucion"
                                                                    maxLength="50"
                                                                    invalidMessage="Ingrese el Número de Resolución"
                                                                    value = "<s:property value='documento.expediente.expedientestor.nroresolucion' />"
                                                                    required="true"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td colspan="8" height="15">
                                                <hr />
                                            </td>
                                        </tr>
                                        <tr>
                                            <td colspan="4" height="15" class="label">DATOS DEL RECLAMANTE</td>
                                            <td colspan="2" height="15" class="label"><div dojoType="dijit.form.Button" onClick="nuevoReclamante()">Nuevo Cliente</div></td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td height="25" align="left">Dni / RUC</td>
                                            <td colspan="6" align="left"><div id="fs_reclamanteID"></div></td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td height="25" align="left">Nombre/Raz&oacute;n Social</td>
                                            <td colspan="6" align="left" class="label">
                                                <s:if test="desactivado">
                                                    <s:if test="mostrarReclamante">
                                                        <input type="text"
                                                               dojoType="dijit.form.TextBox"
                                                               id="expediente.expedientestor.reclamanterazonsocial"
                                                               name="expediente.expedientestor.reclamanterazonsocial"
                                                               value="<s:property value='documento.expediente.cliente.nombreRazon' />"/>
                                                    </s:if>
                                                    <s:else>
                                                        <input type="text"
                                                               dojoType="dijit.form.TextBox"
                                                               id="expediente.expedientestor.reclamanterazonsocial"
                                                               name="expediente.expedientestor.reclamanterazonsocial"
                                                               value="<s:property value='' />"/>
                                                    </s:else>
                                                </s:if>
                                                <s:else>
                                                    <input type="text"
                                                           dojoType="dijit.form.TextBox"
                                                           id="expediente.expedientestor.reclamanterazonsocial"
                                                           name="expediente.expedientestor.reclamanterazonsocial"
                                                           value="<s:property value='documento.expediente.expedientestor.reclamanterazonsocial' />"/>
                                                </s:else>
                                            </td>
                                        </tr>
                                        <tr id="representantelegal">
                                            <td></td>
                                            <td height="25" align="left">Representante Legal</td>
                                            <td colspan="6" align="left" class="label">
                                                <s:if test="desactivado">
                                                    <input  type="text"
                                                            dojoType="dijit.form.TextBox"
                                                            id="expediente.expedientestor.reclamanterepresentantelegal"
                                                            name="expediente.expedientestor.reclamanterepresentantelegal"
                                                            value=""/>
                                                </td>
                                            </s:if>
                                            <s:else>
                                            <input  type="text"
                                                    dojoType="dijit.form.TextBox"
                                                    id="expediente.expedientestor.reclamanterepresentantelegal"
                                                    name="expediente.expedientestor.reclamanterepresentantelegal"
                                                    value="<s:property value='documento.expediente.expedientestor.reclamanterepresentantelegal' />"/></td>
                                        </s:else>
                            </tr>

                            <tr>
                                <td></td>
                                <td height="25" align="left">Direcci&oacute;n Real</td>
                                <td colspan="6" align="left" class="label">
                                    <s:if test="desactivado">
                                        <s:if test="mostrarReclamante">
                                            <input type="text"
                                                   dojoType="dijit.form.TextBox"
                                                   id="expediente.expedientestor.reclamantedireccionreal"
                                                   name="expediente.expedientestor.reclamantedireccionreal"
                                                   value="<s:property value='documento.expediente.cliente.direccionPrincipal' />"/>
                                        </s:if>
                                        <s:else>
                                            <input type="text"
                                                   dojoType="dijit.form.TextBox"
                                                   id="expediente.expedientestor.reclamantedireccionreal"
                                                   name="expediente.expedientestor.reclamantedireccionreal"
                                                   value=""/>
                                        </s:else>
                                    </s:if>
                                    <s:else>
                                        <input type="text"
                                               dojoType="dijit.form.TextBox"
                                               id="expediente.expedientestor.reclamantedireccionreal"
                                               name="expediente.expedientestor.reclamantedireccionreal"
                                               value="<s:property value='documento.expediente.expedientestor.reclamantedireccionreal' />"/>
                                    </s:else>
                                </td>
                            </tr>
                            <tr>
                                <td></td>
                                <td height="25" align="left">Ubigeo Real</td>
                                <td colspan="2">Departamento <div id="fsDepartamentoRec_R" name="fsDepartamentoRec_R" /></td>
                                <td colspan="2">Provincia <div id="fsProvinciaRec_R" name="fsProvinciaRec_R" /></td>
                                <td colspan="2">Distrito <div id="fsDistritoRec_R" name="fsDistritoRec_R" /></td>
                            </tr>
                            <tr>
                                <td></td>
                                <td height="25" align="left">Direcci&oacute;n Procesal</td>
                                <td colspan="6" align="left" class="label">
                                    <s:if test="desactivado">
                                        <input type="text"
                                               dojoType="dijit.form.TextBox"
                                               id="expediente.expedientestor.reclamantedireccionprocesal"
                                               name="expediente.expedientestor.reclamantedireccionprocesal"
                                               value=""/>
                                    </s:if>
                                    <s:else>
                                        <input type="text"
                                               dojoType="dijit.form.TextBox"
                                               id="expediente.expedientestor.reclamantedireccionprocesal"
                                               name="expediente.expedientestor.reclamantedireccionprocesal"
                                               value="<s:property value='documento.expediente.expedientestor.reclamantedireccionprocesal' />"/>
                                    </s:else>
                                </td>
                            </tr>
                            <tr>
                                <td></td>
                                <td height="25" align="left">Ubigeo Procesal</td>
                                <td colspan="2">Departamento <div id="fsDepartamentoRec_P" name="fsDepartamentoRec_P" /></td>
                                <td colspan="2">Provincia <div id="fsProvinciaRec_P" name="fsProvinciaRec_P" /></td>
                                <td colspan="2">Distrito <div id="fsDistritoRec_P" name="fsDistritoRec_P" /></td>
                            </tr>
                            <tr>
                                <td></td>
                                <td height="25" align="left">Correo Electr&oacute;nico</td>
                                <td align="left">
                                    <input type="text"
                                           dojoType="dijit.form.ValidationTextBox"
                                           id="expediente.expedientestor.reclamantecorreo"
                                           name="expediente.expedientestor.reclamantecorreo"
                                           invalidMessage="Ingrese correo"
                                           regExp="[\.a-zA-Z0-9_-]{2,}@[a-zA-Z0-9_-]{2,}\.[a-zA-Z]{2,4}(\.[a-zA-Z]{2,4}){0,1}"
                                           required="false"
                                           style="width:300px;"
                                           value="<s:property value='documento.expediente.expedientestor.reclamantecorreo' />"/>
                                </td>
                                <td height="25" align="left">Fax</td>
                                <td align="left">
                                    <s:if test="desactivado">
                                        <input type="text"
                                               dojoType="dijit.form.TextBox"
                                               id="expediente.expedientestor.reclamantefax"
                                               name="expediente.expedientestor.reclamantefax"
                                               value=""/>
                                    </s:if>
                                    <s:else>
                                        <input type="text"
                                               dojoType="dijit.form.TextBox"
                                               id="expediente.expedientestor.reclamantefax"
                                               name="expediente.expedientestor.reclamantefax"
                                               value="<s:property value='documento.expediente.expedientestor.reclamantefax' />"/>
                                    </s:else>
                                </td>
                                <td height="25" align="left">Tel&eacute;fono</td>
                                <td align="left">
                                    <s:if test="desactivado">
                                        <s:if test="mostrarReclamante">
                                            <input type="text"
                                                   dojoType="dijit.form.TextBox"
                                                   id="expediente.expedientestor.reclamantetelefono"
                                                   name="expediente.expedientestor.reclamantetelefono"
                                                   value="<s:property value='documento.expediente.cliente.telefono' />"/>
                                        </s:if>
                                        <s:else>
                                            <input type="text"
                                                   dojoType="dijit.form.TextBox"
                                                   id="expediente.expedientestor.reclamantetelefono"
                                                   name="expediente.expedientestor.reclamantetelefono"
                                                   value="<s:property value='' />"/>
                                        </s:else>
                                    </s:if>
                                    <s:else>
                                        <input type="text"
                                               dojoType="dijit.form.TextBox"
                                               id="expediente.expedientestor.reclamantetelefono"
                                               name="expediente.expedientestor.reclamantetelefono"
                                               value="<s:property value='documento.expediente.expedientestor.reclamantetelefono' />"/>
                                    </s:else>
                                </td>
                                <td></td>
                            </tr>
                            <%--<tr>
                                <td></td>
                                <td height="25" align="left">Nro. Suministro</td>
                                <td colspan="6" align="left">
                                    <s:if test="desactivado">
                                        <input dojoType="dijit.form.TextBox"
                                               id="expediente.expedientestor.reclamantenrosuministro"
                                               name="expediente.expedientestor.reclamantenrosuministro"
                                               value="" maxlength="15"/>
                                    </s:if>
                                    <s:else>
                                        <input dojoType="dijit.form.TextBox"
                                               id="expediente.expedientestor.reclamantenrosuministro"
                                               name="expediente.expedientestor.reclamantenrosuministro"
                                               maxlength="15"
                                               value="<s:property value='documento.expediente.expedientestor.reclamantenrosuministro' />"/>
                                    </s:else>
                                </td>
                            </tr>--%>
                            <tr>
                                <td colspan="8" height="15">
                                    <hr />
                                </td>
                            </tr>
                            <tr>
                                <td colspan="6" height="15" class="label">SUMINISTROS</td>
                            </tr>
                            <tr>
                                <td colspan="3" height="150">
                                    <div dojoType="dojox.grid.DataGrid"
                                         id="gridSuministros"
                                         jsId="gridSuministros"
                                         rowsPerPage="50"
                                         style="height:90%; width:50%">
                                    </div>
                                </td>
                                <td colspan="5">
                                    <div dojoType="dijit.form.Button" id="btn_AgregarSuministro" jsId="btn_AgregarSuministro" onClick="agregarSuministro()">Agregar</div>
                                    <br /><br />
                                    <div dojoType="dijit.form.Button" id="btn_EliminarSuministro" jsId="btn_EliminarSuministro" onClick="eliminarSuministro()">Eliminar</div>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="6" height="15" class="label">MOTIVOS Y SUBMOTIVOS</td>
                            </tr>
                            <tr>
                                <td colspan="4" height="150">
                                    <div dojoType="dojox.grid.DataGrid"
                                         id="gridMotivoSTOR"
                                         jsId="gridMotivoSTOR"
                                         canSort="sortMeSTOR"
                                         rowsPerPage="50"
                                         style="height:90%; width:90%">
                                    </div>
                                </td>
                                <td colspan="5">
                                    <div dojoType="dijit.form.Button" id="btn_AgregarMotivoSTOR" jsId="btn_AgregarMotivoSTOR" onClick="agregarSubmotivosSTOR()">Agregar</div>
                                    <br /><br />
                                    <div dojoType="dijit.form.Button" id="btn_EliminarMotivoSTOR" jsId="btn_EliminarMotivoSTOR" onClick="eliminarSubmotivosSTOR()">Eliminar</div>
                                </td>
                            </tr>
                        </table>
                </td>
            </tr>
        </table>
    </div>
</td>
</tr>
<tr>
    <td height="14" colspan="6"></td>
</tr>
<tr>
    <td width="100%" colspan="5" align="center">
        <div dojoType="dijit.form.Button" onClick="actualizarPrincipalSTOR()" id="btnActualizarSTOR">Actualizar datos</div>
        <!--                     <img src="images/rechazar.png" style="cursor: pointer;" alt="Rechazar" class="rechazar" />-->
    </td>
</tr>
</table>
<script type="text/javascript" src="js/siged/registroClienteMP.js"></script>
<%@ include file="/pages/tramite/registroCliente.jsp"%>
<%@ include file="/pages/bandeja/agregarSubmotivo.jsp"%>
<%@ include file="/pages/stor/AgregarSuministro.jsp"%>
</body>
</html>