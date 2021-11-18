

function creacionGridEtapaProceso() {
	var gridEtapaProceso = dijit.byId("gridEtapaProceso");
	console.debug("Idproceso [%d]", idProceso);
	gridEtapaProceso.setStructure(lytGridCampo);

	var url = "autocompletarGridEtapaProceso.action";
	$.getJSON(url, {
		iIdProceso : $("input[name='objProceso.idproceso']").val()
	}, function(data) {

		gridEtapaProceso.setStore(new dojo.data.ItemFileWriteStore( {
			data : data
		}));
	});
}

function creacionGridEstadoProceso() {
	var gridEstadoProceso = dijit.byId("gridEstadoProceso");
	console.debug("Idproceso [%d]", idProceso);
	gridEstadoProceso.setStructure(lytGridEstado);

	var url = "autocompletarGridEstadoProceso.action";
	$.getJSON(url, {
		iIdProceso : $("input[name='objProceso.idproceso']").val()
	}, function(data) {

		gridEstadoProceso.setStore(new dojo.data.ItemFileWriteStore( {
			data : data
		}));
	});
}






var viewEditProcesoOnLoad = function() {
	creacionGridEtapaProceso();
	creacionGridEstadoProceso();
};

function verTipo() {
    for (var i = 0; i < radioObj.length; i++) {
       radioObj[i].checked = false;       
       if (radioObj[i].value == tipoNumeracion) {
          radioObj[i].checked = true;
       }
    }
    var radioAmbiente = document.getElementsByName("objProceso.produccion");

    for (var i = 0; i < radioAmbiente.length; i++) {
       radioAmbiente[i].checked = (radioAmbiente[i].value == ambiente) ? true : false;
    }
 }

 function refreshTipoAcceso(iIdTipoAcceso) {
    if (iIdTipoAcceso == 4) {
       document.getElementById("pconfidencial").style.display = "";
    } else {
       document.getElementById("pconfidencial").style.display = "none";
    }
 }


function eliminarProceso(id){
    if(confirm("Seguro que desea eliminar el proceso")){
       dojo.xhrPost({
          url: "doEliminarProceso.action",
          content: {
             iIdProceso: id
          },
          mimetype: "text/html",
          load: function() {
             self.parent.frames["mainFrame"].location.href = "doBody.action";
             self.parent.frames["secondFrame"].location.href = "secondFrame.jsp";
          }
       });
    }
 }

$(document).ready(function(){
    $("#participantes .der").click(function(){
       $("#participantes .izquierda option:selected").each(function(){
          $("#participantes .derecha").append($(this).clone(true));
          $(this).remove();
       });
    });

    $("#participantes .izq").click(function(){
       $("#participantes .derecha option:selected").each(function(){
          $("#participantes .izquierda").append($(this).clone(true));
          $(this).remove();
       });
    });

    $("form").submit(function(){
       $("#participantes .derecha option").attr("selected","selected");
       //alert($("#rolDerecha option:selected").length);
       //return false;
    });
	

    $("#participantesConfidenciales .der").click(function(){
       $("#participantesConfidenciales .izquierda option:selected").each(function(){
          $("#participantesConfidenciales .derecha").append($(this).clone(true));
          $(this).remove();
       });
    });

    $("#participantesConfidenciales .izq").click(function(){
       $("#participantesConfidenciales .derecha option:selected").each(function(){
          $("#participantesConfidenciales .izquierda").append($(this).clone(true));
          $(this).remove();
       });
    });

    $("form").submit(function(){
       $("#participantesConfidenciales .derecha option").attr("selected","selected");
       //alert($("#rolDerecha option:selected").length);
       //return false;
    });

    $("#notificados .der").click(function(){
       $("#notificados .izquierda option:selected").each(function(){
          $("#notificados .derecha").append($(this).clone(true));
          $(this).remove();
       });
    });

    $("#notificados .izq").click(function(){
       $("#notificados .derecha option:selected").each(function(){
          $("#notificados .izquierda").append($(this).clone(true));
          $(this).remove();
       });
    });

    $("form").submit(function(){
       $("#notificados .derecha option").attr("selected","selected");
    });

    $("#listas .der").click(function(){
       $("#listas .izquierda option:selected").each(function(){
          $("#listas .derecha").append($(this).clone(true));
          $(this).remove();
       });
    });

    $("#listas .izq").click(function(){
       $("#listas .derecha option:selected").each(function(){
          $("#listas .izquierda").append($(this).clone(true));
          $(this).remove();
       });
    });

    $("form").submit(function(){
       $("#listas .derecha option").attr("selected","selected");
    });

    $("#doSaveProceso_objProceso_tipo").change(function(){
			$("#procesoIntalio").parent().parent().hide();
			$("#procesoIntalio").val("");
			if($(this).val()!=0){
				$("#doSaveProceso_objProceso_tipo option:selected").each(function(){
					var tipo=$(this).text();
					//FIXME hardcode!!!
					if(tipo=="Intalio"){								
						$("#procesoIntalio").parent().parent().show();
					}
				});
			}
		});
 });

function validaFloat(numero){
    if (!/^([0-9])*[.]?[0-9]*$/.test(numero))
    //alert("El valor " + numero + " no es un número");
        return false;
    else return true;
}





var addCampo = function() {
	   var gridEtapaProceso = dijit.byId("gridEtapaProceso");
	   gridEtapaProceso.store.newItem({
	      id          : indice--,
	      orden      : "Orden",
	      descripcion : "Ingrese Descripcion"
	   });  
};

var deleteCampo = function() {
	//alert(dojo.byId("objProceso.etapas").value);
	   var gridEtapaProceso = dijit.byId("gridEtapaProceso");
	   var items = gridEtapaProceso.selection.getSelected();
	   if (items.length) {
	      dojo.forEach(items, function(selectedItem) {
	         if (selectedItem !== null) {
	            console.debug("ID seleccionado [%d]", gridEtapaProceso.store.getValue(selectedItem, "id"));
	            arrEtapaToDelete[iCount++] = gridEtapaProceso.store.getValue(selectedItem, "id");
	         }
	      });
	   }
	   gridEtapaProceso.removeSelectedRows();
};   
var addCampoEstado = function() {
	   var gridEstadoProceso = dijit.byId("gridEstadoProceso");
	  gridEstadoProceso.store.newItem({
	      id          : indiceEstado--,
	      descripcion : "Ingrese Descripcion"
	   });  
};

var deleteCampoEstado = function() {
	//alert(dojo.byId("objProceso.etapas").value);
	   var gridEstadoProceso = dijit.byId("gridEstadoProceso");
	   var items = gridEstadoProceso.selection.getSelected();
	   if (items.length) {
	      dojo.forEach(items, function(selectedItem) {
	         if (selectedItem !== null) {
	            console.debug("ID seleccionado [%d]", gridEstadoProceso.store.getValue(selectedItem, "id"));
	           arrEstadoToDelete[iCountEstado++] = gridEstadoProceso.store.getValue(selectedItem, "id");
	         }
	      });
	   }
	   gridEstadoProceso.removeSelectedRows();
};     

var viewProcesoOnLoad = function() {
	completar();
};

function completar() {
	var gridEtapaProceso = dijit.byId("gridEtapaProceso");
	console.debug("Idproceso [%d]", idProceso);
	gridEtapaProceso.setStructure(lytGridCampo);

	var url = "autocompletarGridEtapaProceso.action";
	$.getJSON(url, {
		iIdProceso : $("input[name='objProceso.idproceso']").val()
	}, function(data) {

		gridEtapaProceso.setStore(new dojo.data.ItemFileWriteStore( {
			data : data
		}));
	});

	var gridEstadoProceso = dijit.byId("gridEstadoProceso");
	console.debug("Idproceso [%d]", idProceso);
	gridEstadoProceso.setStructure(lytGridEstado);

	var url = "autocompletarGridEstadoProceso.action";
	$.getJSON(url, {
		iIdProceso : $("input[name='objProceso.idproceso']").val()
	}, function(data) {

		gridEstadoProceso.setStore(new dojo.data.ItemFileWriteStore( {
			data : data
		}));
	});
}
