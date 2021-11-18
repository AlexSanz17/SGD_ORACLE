<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Filtrar Bandeja</title>
		<style type="text/css">
			td{
				padding-top: 0.1em;
				padding-bottom: 0.1em;
			}
		</style>
		<script type="text/javascript">
                       function limpiarCampos() {
                                dijit.byId("iDobjFiltro.numeroTramite").attr("value", "");
			        dijit.byId("iDobjFiltro.numeroExpediente").attr("value", "");
				dijit.byId("iDobjFiltro.numeroDocumento").attr("value", "");
				dijit.byId("iDobjFiltro.asuntodocumento").attr("value", "");
				dijit.byId("iDobjFiltro.asuntoexpediente").attr("value", "");
				dijit.byId("iDobjFiltro.tipoDocumento").attr("value", "");
				dijit.byId("iDobjFiltro.cliente").attr("value", "");

				if(dijit.byId("iDobjFiltro.fechaDesde")){
					dijit.byId("iDobjFiltro.fechaDesde").attr("value", "");
					dijit.byId("iDobjFiltro.fechaDesde").attr("displayedValue", "");
				}

				if(dijit.byId("iDobjFiltro.fechaHasta")){
					dijit.byId("iDobjFiltro.fechaHasta").attr("value", "");
					dijit.byId("iDobjFiltro.fechaHasta").attr("displayedValue", "");
				}


				if(dijit.byId("iDobjFiltro.fechaDocumentoDesde")){
					dijit.byId("iDobjFiltro.fechaDocumentoDesde").attr("value", "");
					dijit.byId("iDobjFiltro.fechaDocumentoDesde").attr("displayedValue", "");
				}

				if(dijit.byId("iDobjFiltro.fechaDocumentoHasta")){
					dijit.byId("iDobjFiltro.fechaDocumentoHasta").attr("value", "");
					dijit.byId("iDobjFiltro.fechaDocumentoHasta").attr("displayedValue", "");
				}

				if(dijit.byId("iDobjFiltro.areaOrigen")){
					dijit.byId("iDobjFiltro.areaOrigen").attr("value", "");
				}

				if(dijit.byId("iDobjFiltro.usuarioRemitente")){
					dijit.byId("iDobjFiltro.usuarioRemitente").attr("value", "");
				}

				if(dijit.byId("iDobjFiltro.usuarioDestinatario")){
					dijit.byId("iDobjFiltro.usuarioDestinatario").attr("value", "");
				}
			}

			function destroyDiv() {
	       			  var tipoGrid = "<s:property value='idTipoGrid'/>";

                                   dijit.byId("iDobjFiltro.numeroExpediente").destroy();
				   dijit.byId("iDobjFiltro.numeroDocumento").destroy();
				   dijit.byId("iDobjFiltro.asuntodocumento").destroy();
				   dijit.byId("iDobjFiltro.asuntoexpediente").destroy();
				   dijit.byId("iDobjFiltro.tipoDocumento").destroy();
				   dijit.byId("iDobjFiltro.cliente").destroy();
                                   dijit.byId("iDobjFiltro.numeroTramite").destroy();   
                                   dijit.byId("iDobjFiltro.fechaDesde").destroy();
			           dijit.byId("iDobjFiltro.fechaHasta").destroy();
                                   
                                   if(tipoGrid==9){
                                   	   dijit.byId("iDobjFiltro.areaOrigen").destroy();
					   dijit.byId("iDobjFiltro.usuarioRemitente").destroy();
                                   }
				   if(tipoGrid==18){
                                           if ("<s:property value='#session._USUARIO.idRolPerfil' />" == "4"){
                                              dijit.byId("iDobjFiltro.usuarioRemitente").destroy();
                                           }   
                                   }
                                   if(tipoGrid==24){
                                           if ("<s:property value='#session._USUARIO.idRolPerfil' />" == "4"){
                                              dijit.byId("iDobjFiltro.usuarioRemitente").destroy();
                                            }        
                                   }
                                   if(tipoGrid==2){
                                  	   dijit.byId("iDobjFiltro.usuarioDestinatario").destroy();
                                   }
				   if(tipoGrid==0){
                                         
				   }
				  
                                   dijit.byId("iDformFiltroBandeja").destroy();
                                
			}


			function completarFechaHasta() {
                            dijit.byId('iDobjFiltro.fechaHasta').constraints.min = dijit.byId('iDobjFiltro.fechaDesde').getValue();
                        }

			function completarFechaDesde() {
				dijit.byId('iDobjFiltro.fechaDesde').constraints.max = dijit.byId('iDobjFiltro.fechaHasta').getValue();
                          }


			function completarFechaDocumentoHasta() {
                            dijit.byId('iDobjFiltro.fechaDocumentoHasta').constraints.min = dijit.byId('iDobjFiltro.fechaDocumentoDesde').getValue();
                        }

			function completarFechaDcoumentoDesde() {
		    	  dijit.byId('iDobjFiltro.fechaDocumentoDesde').constraints.max = dijit.byId('iDobjFiltro.fechaDocumentoHasta').getValue();
                        }

			function filtrarBandeja(){
                                var myForm = dijit.byId("iDformFiltroBandeja");
                               
                                if (!myForm.validate()) {
                                     return;
                                }
                            
                                if(dijit.byId("dlgProgresBar")!=null){
		        	  dijit.byId("dlgProgresBar").show() ;
		                }
                                
                                dijit.byId("btnBuscarFL").attr("disabled",true);
				dijit.byId("btnLimpiarFL").attr("disabled",true);

				var tipoGrid = "<s:property value='idTipoGrid'/>";
				var dataForm = dijit.byId("iDformFiltroBandeja").attr("value");

				dataForm.objFiltro.tipoDocumento = dijit.byId("iDobjFiltro.tipoDocumento").attr("value");
				dataForm.objFiltro.cliente = dijit.byId("iDobjFiltro.cliente").attr("value");

				if(dijit.byId("iDobjFiltro.fechaDesde")){
					dataForm.objFiltro.fechaDesde = dijit.byId("iDobjFiltro.fechaDesde").attr("displayedValue");
				}

                                if(dijit.byId("iDobjFiltro.fechaHasta")){
					dataForm.objFiltro.fechaHasta = dijit.byId("iDobjFiltro.fechaHasta").attr("displayedValue");
				}
                                
                                if(dijit.byId("iDobjFiltro.usuarioRemitente")){
                                        dataForm.objFiltro.usuarioRemitente = dijit.byId("iDobjFiltro.usuarioRemitente").attr("value");   
                                }    
                                
                                if(dijit.byId("iDobjFiltro.nroHT")){
					dataForm.objFiltro.nroHT = dijit.byId("iDobjFiltro.nroHT").attr("displayedValue");
				}


				if(dijit.byId("iDobjFiltro.fechaDocumentoDesde")){
					dataForm.objFiltro.fechaDocumentoDesde = dijit.byId("iDobjFiltro.fechaDocumentoDesde").attr("displayedValue");
				}

				if(dijit.byId("iDobjFiltro.fechaDocumentoHasta")){
					dataForm.objFiltro.fechaDocumentoHasta = dijit.byId("iDobjFiltro.fechaDocumentoHasta").attr("displayedValue");
				}

                                if(dijit.byId("IGB_area")){
					var area = dijit.byId('IGB_area').getValue();
					if (area == "0" ) {
						dataForm.objFiltro.areaOrigen = "0" ;
					}else{
						dataForm.objFiltro.areaOrigen = area ;
					}
				}
                                
                                if (dijit.byId("iDobjFiltro.usuarioRemitente") && dijit.byId("idFiltroUsuarioAtendido") && dijit.byId("idFiltroUsuarioAtendido").attr("value")!=''){
                                     if (dijit.byId("iDobjFiltro.usuarioRemitente").attr("value")==""){
                                       dijit.byId("idFiltroUsuarioAtendido").attr("value", "-1");
                                     }else{
                                       dijit.byId("idFiltroUsuarioAtendido").attr("value", dijit.byId("iDobjFiltro.usuarioRemitente").attr("value"));    
                                     }    
                                }
                                
                                if (dijit.byId("idFiltroMesesEnviado") && dijit.byId("idFiltroMesesEnviado").attr("value")!=''){
                                     dijit.byId("idFiltroMesesEnviado").attr("value", "3");
                                }
                                
                                if (dijit.byId("idFiltroUsuarioEnviado") && dijit.byId("idFiltroUsuarioEnviado").attr("value")!=''){
                                     dijit.byId("idFiltroUsuarioEnviado").attr("value", "-1");
                                }  
                                
                                if (dijit.byId("idFiltroTipoDocumentoEnviado") && dijit.byId("idFiltroTipoDocumentoEnviado").attr("value")!=''){
                                     dijit.byId("idFiltroTipoDocumentoEnviado").attr("value", "-1");
                                }  
                                
                                
                                if (dijit.byId("iDobjFiltro.usuarioRemitente") && dijit.byId("idFiltroUsuarioPendiente") && dijit.byId("idFiltroUsuarioPendiente").attr("value")!=''){
                                     if (dijit.byId("iDobjFiltro.usuarioRemitente").attr("value")==""){
                                       dijit.byId("idFiltroUsuarioPendiente").attr("value", "-1");
                                     }else{
                                       dijit.byId("idFiltroUsuarioPendiente").attr("value", dijit.byId("iDobjFiltro.usuarioRemitente").attr("value"));    
                                     }    
                                }
                                
                               service.filtrarBandeja(tipoGrid, dataForm.objFiltro).addCallback(function(objJSON) {
                                     if(dijit.byId("dlgProgresBar")!=null){
			        	dijit.byId("dlgProgresBar").hide() ;
			             }

				     dijit.byId("dlgFiltrosBandeja").hide();

				     if (dijit.byId("gridInbox")) {
				        dijit.byId("gridInbox").destroy();
				     }

				     if(tieneDocumentosXExpediente()){
				   	  objJSON.structure.unshift({
				             editable: true,
				             field: "expBtn",
				             name: "Ver",
				             formatter: formatterButton,
				             width: "40px"
				         });
				     }

                                     if(tieneCheck()) {
				   	  objJSON.structure.unshift({
				           editable: true,
				           field: "selected",
				           name: "<input type='checkbox' id='chkHeader' name='chkHeader' onclick='selectAll()'>",
				           noresize: true,
				           type: dojox.grid.cells.Bool,
				           width: "20px"
				        });
				     }
				     var grid = new dojox.grid.DataGrid({
				        id               : "gridInbox",
				        jsId             : "gridInbox",
				        canSort          : noSort,
				        columnReordering : true,
				        headerMenu       : dijit.byId("gridMenu"),
				        onRowClick       : viewDetail,
				        rowsPerPage      : 50,
				        store            : new dojo.data.ItemFileWriteStore({
					        data : objJSON
					     }),
				        structure        : objJSON.structure
				     }, document.createElement("div"));

                                     for (var i in grid.layout.cells) {
				        if (grid.layout.cells[i].formato == "formatterDate") {
				           grid.layout.cells[i].formatter = formatterDate;
				        }
				        else if (grid.layout.cells[i].formato == "formatterImg") {
				           grid.layout.cells[i].formatter = formatterImg;
				        }
				        else if (grid.layout.cells[i].formato == "formatterLeido") {
				           grid.layout.cells[i].formatter = formatterLeido;
				        }
                                        else if (grid.layout.cells[i].formato == "formatterAreas") {
                                            grid.layout.cells[i].formatter = formatterAreas;
                                         }
				        else if (grid.layout.cells[i].formato == "formatterTipoNumeracion") {
				           grid.layout.cells[i].formatter = formatterTipoNumeracion;
				        }
				        else if (grid.layout.cells[i].formato == "formatterTipoDocumento") {
				           grid.layout.cells[i].formatter = formatterTipoDocumento;
				        }
                                        else if (grid.layout.cells[i].formato == "formatterDocumentos") {
                                            grid.layout.cells[i].formatter = formatterDocumentos;
                                         }
				        if(grid.layout.cells[i].ordenado){
				           columnaOrdenada=parseInt(i);
				           ascendente=grid.layout.cells[i].ascendente;
				        }
				     }

                                    dojo.byId("contentPaneGrid").appendChild(grid.domNode);
                                    grid.startup();
                                    
                                    var dato = dijit.byId("borderContainerGeneral").attr("title");
                                    var posicion = dato.indexOf("[");

                                    if (posicion!=-1){
                                     dato = dato.substring(0, posicion);    
                                    }

                                    var dato_ = "";
                                    dato_ = " [" + grid.store._arrayOfAllItems.length + " documento(s)]"; 
                                    dijit.byId("borderContainerGeneral").setTitle(dato + dato_);
                                    
                                    dijit.byId("idFiltroTipo").attr("value", "-1");
                                    dijit.byId("idFiltroTipoDocumento").attr("value", '-1');
                                    
                                   // grid.setSortIndex(columnaOrdenada,ascendente);
                                    dijit.byId("dlgFiltrosBandeja").destroyRecursive();
			    });
                            
                            destroyDiv();
			}

			dojo.addOnLoad(function(){
                                var tipoGrid = "<s:property value='idTipoGrid'/>";
                                
				new dijit.form.FilteringSelect({
				      id             : "iDobjFiltro.tipoDocumento",
				      jsId           : "iDobjFiltro.tipoDocumento",
				      name           : "objFiltro.tipoDocumento",
				      searchAttr     : "label",
				      autoComplete   : false,
                                     // queryExpr:	"*${0}*",
				      hasDownArrow   : false,
				      highlightMatch : "all",
				      required       : false,
				      style          : "width:15em",
				      searchDelay    : 500,
				      _onBlur		 : function(){ this.value=dijit.byId("iDobjFiltro.tipoDocumento");},
				      store	     : new dojox.data.QueryReadStore({url: "QRS_autocompletarQRSTipoDocumento.action"})
				}, "iDobjFiltro.tipoDocumento");

				new dijit.form.FilteringSelect({
				      id             : "iDobjFiltro.cliente",
				      jsId           : "iDobjFiltro.cliente",
				      name           : "objFiltro.cliente",
				      searchAttr     : "label",
				      autoComplete   : false,
				      hasDownArrow   : false,
				      highlightMatch : "all",
				      required       : false,
				      style          : "width:52.4em;",
				      searchDelay    : 500,
				      _onBlur		 : function(){this.value=dijit.byId("iDobjFiltro.cliente");},
				      store		     : new dojox.data.QueryReadStore({url: "QRS_autocompletarQRSCliente.action"})
				}, "iDobjFiltro.cliente");

                                if(tipoGrid==24 || tipoGrid==18){
                                    if ("<s:property value='#session._USUARIO.idRolPerfil' />" == "4"){
                                        new dijit.form.FilteringSelect({
                                                   id             : "iDobjFiltro.usuarioRemitente",
                                                   jsId           : "iDobjFiltro.usuarioRemitente",
                                                   name           : "objFiltro.usuarioRemitente",
                                                   searchAttr     : "label",
                                                  // autoComplete   : false,
                                                   hasDownArrow   : false,
                                                   highlightMatch : "all",
                                                   required       : false,
                                                   style          : "width:15em",
                                                   searchDelay    : 500,
                                                   //_onBlur        : function(){this.value=dijit.byId("iDobjFiltro.usuarioRemitente");},
                                                   //store          : new dojox.data.QueryReadStore({url: "autocompletarUsuariosxUnidad.action"})
                                                   store: new dojo.data.ItemFileReadStore({url: "autocompletarUsuariosxUnidad.action?iWithoutStor=1"})   
                                           }, "iDobjFiltro.usuarioRemitente");
                                     }
                                  }      
				
                                     new dijit.form.FilteringSelect({
					jsId : "area",
					name : "area",
					store : new dojo.data.ItemFileReadStore({
						url : "autocompletarAreaTodos.action"
					}),
					searchAttr : "label",
					labelAttr : "label",
					value: 0,
					autoComplete : true,
					hasDownArrow : true,
					style          : "width:20em;",
					highlightMatch : "all",
					invalidMessage : "Seleccione el Area"
				}, "IGB_area");
                                
                                

			});
		</script>
	</head>
	<body style="overflow:auto;">
		<form dojoType="dijit.form.Form" id="iDformFiltroBandeja" jsId="iDformFiltroBandeja">
			<table style="width:100%;">
				<tr>
                                        <td>Nro. de Trámite</td>
					<td><input id="iDobjFiltro.numeroTramite" maxLength="12"  regExp="\d{1,99999999999}" name="objFiltro.nroHT" dojoType="dijit.form.ValidationTextBox" /></td>
					<td>Nro. de Carpeta</td>
					<td><input id="iDobjFiltro.numeroExpediente" maxLength="12"  regExp="\d{1,99999999999}"  name="objFiltro.numeroExpediente" dojoType="dijit.form.ValidationTextBox" /></td>
					
				</tr>
				<tr>
					<td>Tipo de Documento</td>
					<td><div id="iDobjFiltro.tipoDocumento"/></td>
                                        <td>Nro. de Documento</td>
					<td><input id="iDobjFiltro.numeroDocumento" name="objFiltro.numeroDocumento" dojoType="dijit.form.TextBox" /></td>
				</tr>
				<tr>
					<td>Cliente</td>
					<td colspan="3"><input id="iDobjFiltro.cliente"/></td>
				</tr>
				<tr>
					<td>Asunto del Documento</td>
					<td colspan="3"><input id="iDobjFiltro.asuntodocumento" maxLength="50" name="objFiltro.asuntodocumento" dojoType="dijit.form.TextBox" style="width:52.4em;"/></td>
				</tr>
				<tr>
					<td>Asunto de la Carpeta</td>
					<td colspan="3"><input id="iDobjFiltro.asuntoexpediente" maxLength="50" name="objFiltro.asuntoexpediente" dojoType="dijit.form.TextBox" style="width:52.4em;"/></td>
				</tr>
                                
                                <tr>
					<td>Fecha Desde</td>
					<td><input id="iDobjFiltro.fechaDesde" name="objFiltro.fechaDesde" dojoType="dijit.form.DateTextBox" constraints="{datePattern:'dd/MM/yyyy'}" onChange="completarFechaHasta()" /></td>
					<td>Fecha Hasta</td>
					<td><input id="iDobjFiltro.fechaHasta" name="objFiltro.fechaHasta" dojoType="dijit.form.DateTextBox" constraints="{datePattern:'dd/MM/yyyy'}" onChange="completarFechaDesde()" /></td>
				</tr>
                                <s:if test="#session._USUARIO.idRolPerfil==4 && (idTipoGrid == 18 || idTipoGrid == 24)">
                                     <tr>
					<td>Usuario</td>
                                        <td><div id="iDobjFiltro.usuarioRemitente"/></td>
					<td colspan="2"></td>
				     <tr>
                                </s:if>    
                        
			</table>
		</form>
		<p>
			<button id="btnBuscarFL" dojoType=dijit.form.Button type="button" iconClass="siged16 sigedSearch16" showLabel="true" onClick="filtrarBandeja();">Buscar</button>
            <button id="btnLimpiarFL" dojoType=dijit.form.Button type="button" iconClass="siged16 sigedEraser16" showLabel="true" onClick="limpiarCampos">Limpiar</button>
		</p>
	</body>
</html>