<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Insert title here</title>
		<script type="text/javascript">
			var estructura = [
			{
			    field: 'nombre',
			    name: 'Archivo',
			    width: "38em"
			},
			{
			    field: 'idArchivo',
			    name: 'id',
			    hidden: true
			},
			{
			    editable: true,
                            field: "selected",
                            name: " ",
                            noresize: true,
                            type: dojox.grid.cells.Bool,
                            width: "1.7em"
			}];
		
			function eliminarAdjunto(){
				var archivos = new Array();
				var gridAXU = dijit.byId("gridArchivosXUsuario");
				dojo.forEach(gridAXU.store._arrayOfAllItems, function(item) {
				    if (gridAXU.store.getValue(item, "selected")) {
				    	archivos.push(gridAXU.store.getValue(item, "id"));
					}
				});
				
				if(archivos.length){
					if(confirm("Desea eliminar los archivos seleccionados?")){
						dijit.byId("dlgProgresBar").show();
						
						dojo.xhrPost({
							url: "doEliminarArchivo.action",
						    content: {
						    	arrIdArchivos : archivos
							},
						    load: function(data) {
						    	if (sTipoGridActual==TIPO_GRID_FIRMAR){
                                                            var sURL="doViewDocFirmado.action?idfirmados="+"<s:property value='iIdDoc' />";
                                                             dijit.byId("contentPaneDetail").setHref(sURL);
                                                         }else{
                                                             if (sTipoGridActual==TIPO_GRID_MI_RECEPCION_VIRTUAL){
                                                                var sURL="doViewDocUsuarioFinalRecepcion.action?iIdDoc="+"<s:property value='iIdDoc' />";
                                                                dijit.byId("contentPaneDetail").setHref(sURL);
                                                             }else{ 
                                                                if (sTipoGridActual==TIPO_GRID_EXPEDIENTES){  
                                                                  var sURL="doViewDocUsuarioFinal.action?iIdDoc="+"<s:property value='iIdDoc' />" + "&idGrid=0";
                                                                   dijit.byId("contentPaneDetail").setHref(sURL);
                                                                }else{
                                                                      if (sTipoGridActual==TIPO_GRID_RECIBIDOS){  
                                                                         showGridInbox(sTipoGridActual);
                                                                      }
                                                                }    
                                                             }   
                                                         }   
								
						    	dijit.byId("dlgProgresBar").hide() ;
						    	dijit.byId("dlgEliminar").hide();
						    	dijit.byId("dlgEliminar").destroyRecursive();
							}
						});  
					}
				}else{
					alert("Primero debe seleccionar uno o mas archivos");
				}
			}
			
			dojo.addOnLoad(function(){
				service.getArchivosXUsuario("<s:property value='iIdDoc' />").addCallback(function(objJSON){
					
					var storeGrid = new dojo.data.ItemFileWriteStore({
				         data : objJSON
				    });
					
					new dojox.grid.DataGrid({
				         id               : "gridArchivosXUsuario",
				         jsId             : "gridArchivosXUsuario",
				         columnReordering : false,
				         rowsPerPage      : 50,
				         store            : storeGrid,
				         structure        : estructura,
				         style	          : "width:45em; height:15em;"
				      }, "gridArchivosXUsuario").startup();
					
				});
			});
		</script>
	</head>
	<body>
		<div id="gridArchivosXUsuario"></div>
		<div style="text-align:right;">
			<input type="button" dojoType="dijit.form.Button" label="Eliminar" iconClass="dijitEditorIcon dijitEditorIconDelete" onClick="eliminarAdjunto()"/>
		</div>
	</body>
</html>