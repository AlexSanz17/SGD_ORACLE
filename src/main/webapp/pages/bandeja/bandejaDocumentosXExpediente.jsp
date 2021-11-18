<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Documentos del Expediente</title>
		<script type="text/javascript">
			function updateLeidoDXE(rowIndex){
				var gridDXE = dijit.byId("gridDXE");
				var row = gridDXE.getItem(rowIndex);
				var sTipoGrid = null;

				console.debug("(updateLeido) Leido [%s]", row.leido);

				if (row.leido == 'N' || row.leido == '' || row.leido == null ) {
				   gridDXE.store.setValue(gridDXE.getItem(rowIndex), "leido", 'S');
				   gridDXE.store.save();

				   //MEJORAR ESTO!!!
				   if (sTipoGridActual == TIPO_GRID_RECIBIDOS || sTipoGridActual == TIPO_GRID_EXPEDIENTES) {
				      sTipoGrid = "UsuFinMnuDocRec";
				   } else if (sTipoGridActual == TIPO_GRID_NOTIFICACIONES) {
				      sTipoGrid = "UsuFinMnuNotif";
				   } else if (sTipoGridActual == TIPO_GRID_INFORMATIVOS) {
				      sTipoGrid = "UsuFinMnuInfo";
					}

					service.updateRead(parseInt(row.id), sTipoGrid).addCallback(function(result) {
						getUnread();
					});
				}
			}
		
			//var derivarMasivamenteDXE = function(){
			function derivarMasivamenteDXE(){
				var gridDXE = dijit.byId("gridDXE");
				var opciones = "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes, width=800, height=600, top=20, left=70";
				var parameters = "";
				var pagina = "goDerivarMasivo.action?sTipoDerivacion=masivo&sOpcion=nose&";

				dojo.forEach(gridDXE.store._arrayOfAllItems, function(item) {
				   if (gridDXE.store.getValue(item, "selected")) {
				      parameters += "arrIdDoc=" + gridDXE.store.getValue(item, "id") + "&";
				   }
				});

				if (parameters != "") {
				   pagina += parameters;
				   console.debug("URL Derivacion Masiva [%s]", pagina);
				   window.open(pagina, "", opciones);
				} else {
				   alert("Primero selecccionar documentos");
				}
			};
		
			function aprobarMasivamenteDXE(){
				var gridDXE = dijit.byId("gridDXE");
				var opciones = "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes, width=800, height=600, top=20, left=70";
				var parameters = "";
				var pagina = "goAprobarMasivo.action?";

				dojo.forEach(gridDXE.store._arrayOfAllItems, function(item) {
				   if (gridDXE.store.getValue(item, "selected")) {
				      parameters += "arrIdDoc=" + gridDXE.store.getValue(item, "id") + "&";
				   }
				});

				if (parameters != "") {
				   pagina += parameters;
				   console.debug("URL Aprobacion Masiva [%s]", pagina);
				   window.open(pagina, "", opciones);
				} else {
				   alert("Primero selecccionar documentos");
				}
			}
			
			function archivarMasivamenteDXE() {
				var gridDXE = dijit.byId("gridDXE");
				var opciones = "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=1000, height=600, top=20, left=70";
				var parameters = "";
				var pagina = "Archivar_inicioMasivo.action?";

				dojo.forEach(gridDXE.store._arrayOfAllItems, function(item) {
				   if (gridDXE.store.getValue(item, "selected")) {
				      parameters += gridDXE.store.getValue(item, "id") + ";";
				   }
				});
				
				if (parameters != "") {
				   service.setAttributeInSession('lista', parameters);
				   console.debug("URL Archivamiento Masivo [%s]", pagina);
				   window.open(pagina, "", opciones);
				} else {
				   alert("Primero selecccionar documentos");
				}
			};
			
			//var selectAllDXE = function (){
			function selectAllDXE(){
				var grid = dijit.byId("gridDXE");
				dojo.byId("chkHeader").checked = !dojo.byId("chkHeader").checked;
				
				dojo.forEach(grid.store._arrayOfAllItems, function(item) {
					grid.store.setValue(item, "selected", dojo.byId("chkHeader").checked);
				});
			};
		
			var noSortDXE = function(inSortInfo){
				if(inSortInfo == 1){
					return false;
				}
				return true;
			};
		
			var verDetalleDXE = function(e){
				rowGridDXEIndex = e.rowIndex;
				updateLeidoDXE(e.rowIndex);
				
				dijit.byId("cpDetailDocumentoDXE").attr("content", "");
				dijit.byId("cpDetailExpedienteDXE").attr("content", "");
				
				dijit.byId("cpDetailDocumentoDXE").setHref("doViewDocUsuarioFinal.action?iIdDoc=" + this.getItem(e.rowIndex).id);
				dijit.byId("cpDetailExpedienteDXE").setHref("doViewDocAdicionales.action?iIdDoc=" + this.getItem(e.rowIndex).id);
				
				this.edit.rowClick(e);
				this.selection.clickSelectEvent(e);
				//visorGerencial
				 /*dijit.byId("bcDXEIG").removeChild(dijit.byId("contentPaneVisorXex"));
		         dijit.byId("contentPaneVisorXex").destroyDescendants();
		         dijit.byId("contentPaneVisorXex").destroy();	*/			
		         
		         
		         if(recursoIG==1){
		        	 dijit.byId('dlgProgresBar').show() ;
		        	 mostrarVisorPDFXEx(this.getItem(e.rowIndex).id);
						   }
		         
		         
				//fin
			};	
		
			dojo.addOnLoad(function(){
				var cargandoDXE = document.getElementById("cargandoDXE");
				
				if (cargandoDXE) {
					cargandoDXE.style.display="";
				}
				
				service.getDGDocumentosXExpediente(sTipoGridActual, <s:property value='iIdDocumento'/>).addCallback(function(objJSON){
					if (objJSON.structure == undefined) {
				    	alert("NO HAY ESTRUCTURA PARA EL GRID SELECCIONADO");
				        return;
					}

				    objJSON.structure.unshift({
				    	editable: true,
				        field: "selected",
				        name: "<input type='checkbox' id='chkHeaderDXE' name='chkHeaderDXE' onclick='selectAllDXE()'>",
				        noresize: true,
				        type: dojox.grid.cells.Bool,
				        width: "20px"
					});
				      
				    if (dijit.byId("gridDXE")) {
				    	dijit.byId("gridDXE").destroy();
				    }
				    
				    storeGrid = new dojo.data.ItemFileWriteStore({
				    	data : objJSON
				    });
						
				    var gridDXE = new dojox.grid.DataGrid({
				    	id               : "gridDXE",
				        jsId             : "gridDXE",
				        columnReordering : false,
				        canSort: noSortDXE,
				        onRowClick       : verDetalleDXE,
				        rowsPerPage      : 50,
				        store            : storeGrid,
				        structure        : objJSON.structure
				    }, "gridDXE");
				      
				      for (var i in gridDXE.layout.cells) {
				         if (gridDXE.layout.cells[i].formato == "formatterDate") {
				            gridDXE.layout.cells[i].formatter = formatterDate;
				         }else if (gridDXE.layout.cells[i].formato == "formatterImg") {
				            gridDXE.layout.cells[i].formatter = formatterImg;
				         }else if (gridDXE.layout.cells[i].formato == "formatterLeido") {
				            gridDXE.layout.cells[i].formatter = formatterLeido;
				         }else if (gridDXE.layout.cells[i].formato == "formatterTipoNumeracion") {
				            gridDXE.layout.cells[i].formatter = formatterTipoNumeracion;
				         }else if (gridDXE.layout.cells[i].formato == "formatterTipoDocumento") {
				            gridDXE.layout.cells[i].formatter = formatterTipoDocumento;
				         }
				         
				         if(gridDXE.layout.cells[i].ordenado){
				            columnaOrdenada=parseInt(i);
				            ascendente=gridDXE.layout.cells[i].ascendente;
				         }
				      }
						
				      if (cargandoDXE) {
				         cargandoDXE.style.display="none";
				      }
				      gridDXE.startup();
				      //addToolBarInbox(sTipoGrid);
				   });
				
				/*if(recursoIG==1){
					dijit.byId("bcDXE").style.width ='49%';
					dijit.byId("bcDXEIG").style.width ='49%';
					   }else{ 
						dijit.byId("bcDXE").style.width ="97%";
						dijit.byId("bcDXEIG").style.width ="1%";
							   }*/
				
				//mostrarVisorPDFXExError();
				
			});
			//visorGerencial
			/*var divVisorDXE = new dojox.layout.ContentPane({
		         id: "contentPaneVisorXex",
		         href:  "pages/layout/errorVisor.jsp",
		         jsId: "contentPaneVisorXex",
		         style: "width:97%; overflow:auto;",
		         region:"center"
		     },"divVisorDXE");
			divVisorDXE.startup();		*/
			if(recursoIG==1){
				mostrarVisorPDFXExError();
				dijit.byId("dlgProgresBar").hide() ;
				   }
			
			//fin
		</script>
	</head>
	<body>
	
        
        <s:if test="#session._RECURSO.UsuFinVisorGerencial">
    <div dojoType="dijit.layout.BorderContainer" id="bcDXET" jsId="bcDXET" style="width:100%;height:98%;" region="center">
		<div dojoType="dijit.layout.BorderContainer" id="bcDXE" jsId="bcDXE" style="width:49%;height:98%;" region="left">
        	<div dojoType="dijit.Toolbar" style="height:4%;" region="top">
        			<input dojoType="dijit.form.Button" iconClass="dijitEditorIcon dijitEditorIconIndent" label="Transferir Masivamente" onClick="derivarMasivamenteDXE()"/>
        			<input dojoType="dijit.form.Button" iconClass="dijitEditorIcon dijitEditorIconIndent" label="Aprobar Masivamente" onClick="aprobarMasivamenteDXE()"/>
        			<input dojoType="dijit.form.Button" iconClass="dijitEditorIcon dijitEditorIconIndent" label="Archivar Masivamente" onClick="archivarMasivamenteDXE()"/>
        		</div>
        	<div dojoType="dijit.layout.BorderContainer" region="center" id="bcCentralDXE" style="width:100%;height:46%;background-color:#FFFFFF; overflow:auto;">
                <div id="gridDXE"></div>
	            <div id="cargandoDXE" style="display: none;" class="cargando">
		            <img src="images/cargandoGrid.gif" alt="Cargando" />
			        <strong>Cargando...</strong>
			    </div>
            </div>
            <div dojoType="dijit.layout.TabContainer" id="tcDetailDXE" jsId="tcDetailDXE" region="bottom" splitter="true" style="width:100%;height:46%;" tabStrip="true">
                <div dojoType="dojox.layout.ContentPane" id="cpDetailDocumentoDXE" jsId="cpDetailDocumentoDXE" title="Detalle del Documento"></div>
                <div dojoType="dojox.layout.ContentPane" id="cpDetailExpedienteDXE" jsId="cpDetailExpedienteDXE" title="Expediente"></div>
            </div>
        </div>
        <div dojoType="dijit.layout.BorderContainer" id="bcDXEIG" jsId="bcDXEIG" style="width:49%;height:98%;" region="center">
        	   <div id="divVisorDXE"></div>
        </div>
        </div>     
        </s:if>        
        <s:else>
    <div dojoType="dijit.layout.BorderContainer" id="bcDXET" jsId="bcDXET" style="width:100%;height:98%;" region="center">
		<div dojoType="dijit.layout.BorderContainer" id="bcDXE" jsId="bcDXE" style="width:97%;height:98%;" region="left">
        	<div dojoType="dijit.Toolbar" style="height:4%;" region="top">
        			<!--<input dojoType="dijit.form.Button" iconClass="dijitEditorIcon dijitEditorIconIndent" label="Transferir Masivamente" onClick="derivarMasivamenteDXE()"/> -->
        			<input dojoType="dijit.form.Button" iconClass="dijitEditorIcon dijitEditorIconIndent" label="Aprobar Masivamente" onClick="aprobarMasivamenteDXE()"/>
        			<input dojoType="dijit.form.Button" iconClass="dijitEditorIcon dijitEditorIconIndent" label="Archivar Masivamente" onClick="archivarMasivamenteDXE()"/>
        		</div>
        	<div dojoType="dijit.layout.BorderContainer" region="center" id="bcCentralDXE" style="width:100%;height:46%;background-color:#FFFFFF; overflow:auto;">
                <div id="gridDXE"></div>
	            <div id="cargandoDXE" style="display: none;" class="cargando">
		            <img src="images/cargandoGrid.gif" alt="Cargando" />
			        <strong>Cargando...</strong>
			    </div>
            </div>
            <div dojoType="dijit.layout.TabContainer" id="tcDetailDXE" jsId="tcDetailDXE" region="bottom" splitter="true" style="width:100%;height:46%;" tabStrip="true">
                <div dojoType="dojox.layout.ContentPane" id="cpDetailDocumentoDXE" jsId="cpDetailDocumentoDXE" title="Detalle del Documento"></div>
                <div dojoType="dojox.layout.ContentPane" id="cpDetailExpedienteDXE" jsId="cpDetailExpedienteDXE" title="Expediente"></div>
            </div>
        </div>
        <div dojoType="dijit.layout.BorderContainer" id="bcDXEIG" jsId="bcDXEIG" style="width:1%;height:98%;" region="center">
        	   <div id="divVisorDXE"></div>
        </div>
        </div>           
        </s:else>
        
                            
	</body>
</html>