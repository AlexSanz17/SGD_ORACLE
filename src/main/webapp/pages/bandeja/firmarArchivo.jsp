<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Insert title here</title>
        <script type="text/javascript" >
            function checkall( trueorfalse) {
                var grid = dijit.byId("gridArchivosXFirmar");
                dojo.forEach(grid.store._arrayOfAllItems, function (item) {
                    grid.store.setValue(item, "selected", trueorfalse);
                });

            }
            var estructuraFirmar = [

                {
                    field: 'nombre',
                    name: 'Archivos',
                    formatter: formatColorsito,
                    width: "45em"
                },
                {
                    field: 'idArchivo',
                    name: 'idArchivo',
                    hidden: true
                },
                {
                    field: 'principal',
                    name: 'principal',
                    hidden: true
                },
                {
                    editable: true,
                    field: "selected",
                    name: " ",
                   noresize: true,
                    type: dojox.grid.cells.Bool,
                    width: "1.8em"
                },
                {
                    field:'recepcionado',
                    name: 'recepcionado',
                    hidden: true
                }
                ];



            function formatColorsito(valor) {
                var indice = valor.indexOf("|");
                var nombre = valor.substring(0, indice);
                var principal = valor.substring(indice + 1, valor.length);
                if (principal == 'S') {
                    return "<font color='blue'><b>" + nombre + "</b></font>";  
                }

                return nombre;
            }

            function procesarAdjunto() {
                var idsDocumentosAlfresco = "";
                var recepcionado = "";
                var gridAXU = dijit.byId("gridArchivosXFirmar");
                dojo.forEach(gridAXU.store._arrayOfAllItems, function (item) {
                    if (gridAXU.store.getValue(item, "selected")) {
                        idsDocumentosAlfresco = idsDocumentosAlfresco + gridAXU.store.getValue(item, "idArchivo") + ",";
                    }
                });
                
                dojo.forEach(gridAXU.store._arrayOfAllItems, function (item) {
                    if (gridAXU.store.getValue(item, "selected")) {
                        recepcionado = gridAXU.store.getValue(item, "recepcionado");
                    }
                });

                if (idsDocumentosAlfresco == '') {
                    alert("Debe seleccionar un elemento a Firmar/Visar");
                } else {
                    dijit.byId("dlgFirmar").hide();
                    dijit.byId("dlgFirmar").destroyRecursive();

                    document.getElementById("documentosId").value = idsDocumentosAlfresco;
                    
                    if ("<s:property value='#session._USUARIO.idUnidadPerfil' />"  == "<s:property value='@org.ositran.utils.Constantes@UNIDAD_TRAMITE' />"){
                        if (recepcionado!=''){
                           if (recepcionado == "R"){ 
                             document.getElementById("tipoFirma").value = "FI";
                           }
                           if (recepcionado == "O"){
                             document.getElementById("tipoFirma").value = "FIO";
                           }
                        }else{
                           alert("Se produjo un error inesperado.");
                           return;
                        }    
                    }else{
                        document.getElementById("tipoFirma").value = "<s:property value='accion' />";
                    }    
                    enviarInformacionFirma();
                }
            }

            dojo.addOnLoad(function () {
                service.getArchivosFirmar("<s:property value='arrFileFirmar' />").addCallback(function (objJSON) {
                    var storeGrid = new dojo.data.ItemFileWriteStore({
                        data: objJSON
                    });

                    new dojox.grid.DataGrid({
                        id: "gridArchivosXFirmar",
                        jsId: "gridArchivosXFirmar",
                        columnReordering: false,
                        rowsPerPage: 50,
                        store: storeGrid,
                        structure: estructuraFirmar,
                        style: "width:55em; height:20em;"
                    }, "gridArchivosXFirmar").startup();
                    
                  
                    var checkBox = new dijit.form.CheckBox({
                        name: "checkBox",                        
                        checked: true,
                        onChange: function (b) {checkall(b);}
                    }, "checkBox");
                    
                    checkall(true);
                });
            });
        </script>
    </head>
    <body>
        <div style="position: relative!important; left:430px!important;"><label for="checkBox"><b>Todos</b></label> <input id="checkBox" > </div>
        <div id="gridArchivosXFirmar"></div>
        <br/><br/>
        <div style="text-align:center;">
            <input type="button" dojoType="dijit.form.Button" label="Procesar" onClick="procesarAdjunto()"
                   style="position: relative!important;top:-15px! important"/>
        </div>
    </body>
</html>
