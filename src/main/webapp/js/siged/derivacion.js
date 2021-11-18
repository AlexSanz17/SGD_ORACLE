var MODE = null;
var gDoubleClick = false;
var conCC = false;
var filterPara = function(e) {
   //console.debug("value [%s]", this.attr("value"));
   dijit.byId("grdPara").setQuery({nombres : "*" + this.attr("value") + "*"}, {ignoreCase: true});
};

var resetPara = function() {
   dijit.byId("txtFiltroPara").attr("value", "");
   dojo.byId("txtFiltroPara").focus();
   dijit.byId("grdPara").showMessage("");
   dijit.byId("grdPara").setStore(new dojo.data.ItemFileReadStore({url: "buscarPara.action?mode=" + MODE}));
   dijit.byId("grdPara").focus.next();
   dijit.byId("grdPara").render();
};

var selectContactoFromGrdPara = function(e) {
   if (!gDoubleClick) {
      if (e.rowIndex == undefined) {
         return;
      }
       
      var id = dijit.byId("grdPara").getItem(e.rowIndex).id;
      //var nombres = dijit.byId("grdPara").getItem(e.rowIndex).nombres;
      if (id==USUARIOACTIVO){
        alert("La derivación no puede realizarse al mismo usuario.");
        return;
      }
  
      gDoubleClick = true;
      service.saveFavorito(String(id)).addCallback(function(response) {
         dijit.byId(MODE).store = new dojo.data.ItemFileReadStore({url: "getFavorito.action?mode=" + MODE});
         dijit.byId(MODE).attr("value", id);
      });
   }

   dijit.byId("dlgPara").hide();
};

var showGrdPara = function(mode) {
   MODE = mode;
   gDoubleClick = false;
   console.debug("(showGrdPara) MODE [" + MODE + "] gDoubleClick [" + gDoubleClick + "]");
   dijit.byId("dlgPara").show();
   dijit.byId("txtFiltroPara").attr("value", "");
   dijit.byId("grdPara").showMessage("");
   dijit.byId("grdPara").setStore(new dojo.data.ItemFileReadStore({url: "buscarPara.action?mode=" + MODE}));
   dijit.byId("grdPara").focus.next();
   dijit.byId("grdPara").render();
};

var validarRemitente = function(idUsuarioSeleccionado) {
   console.debug("Usuario remitente [%d]. Usuario seleccionado [%d]", idRemitente, idUsuarioSeleccionado);
   var componente = dojo.byId("tbPlazo");

   if(paraAprobar=="true" && !Siged.String.isEmpty(componente)){
      if (idRemitente == idUsuarioSeleccionado ) {
         //console.debug("Ocultando plazos...");
         dojo.byId("tbPlazo").style.display = "none";
         document.getElementById("destinatarioIgualRemitente").value=true;

      } else  {
         console.debug("Mostrando plazos...");
         dojo.byId("tbPlazo").style.display = "";
         document.getElementById("destinatarioIgualRemitente").value=false;
      }
   }
};

/*function completarAcciones(){
	var text = "El expediente ha sido derivado para que se realicen las siguientes acciones: <br />";
	var conAcc = false;
	dojo.query("input[id*='acciones']").forEach(function(node){
		if(node.checked){
			conAcc = true;
			text += "- "+dojo.byId("txt"+node.id).innerHTML+" <br />";
		}
	});
	if(conAcc){
		text += "<br /><br />";
		return text;
	}
	return "";
}

function agregarNodoAcciones(){
	var textoAcc = "<input type='hidden' name= \"strAcciones\" value='"+completarAcciones()+"' />";
	$("#acciones").append(textoAcc);

	dojo.query("input[id*='acciones']").forEach(function(node){
		dijit.byId(node.id).attr("checked", false);
	});
}*/

var agregarConCopia=function(){
   var nombre = dijit.byId("idconcopia").attr("displayedValue");
   var id = dijit.byId("idconcopia").attr("value");
   
   if(nombre!=null && nombre!="" && id!=null && id!=""){
      var existe=false;
      var conCopia=document.getElementsByName("conCopia");
      $(conCopia).each(function(){
         if($(this).val()==id)
            existe=true;
      });
      if(!existe){
         var copia="<span class=\"copia\">";
         copia+="<span>"+nombre+"</span>";
         copia+="<input type=\"hidden\" name=\"conCopia\" value=\""+id+"\" />";
         copia+="<a href=\"#\" class=\"quitar\"><img src=\"images/eliminar.gif\" alt=\"X\" /></a></span>";
         $("#copias").append(copia);
         $(".quitar").click(function(){
            $(this).parent().remove();
         });
      }
   }
};

dojo.addOnLoad(function() {
   Siged.Forms.combobox("fsPara",{
      id              : "iddestinatario",
      jsId            : "iddestinatario",
      name            : "iddestinatario",
      searchAttr      : "label",
      searchDelay     : 650,
      queryExpr       : "*${0}*",
      autoComplete    : false,
      hasDownArrow    : true,
      highlightMatch  : "all",
      style          : "width:400px;",
      promptMessage  : "Seleccione un destinatario",
      required        : false,
      onChange        : validarRemitente
   });

   if (dijit.byId("iddestinatario")) {
      dijit.byId("iddestinatario").store = store;
   }

   Siged.Forms.combobox("iddestinatarios",{
      id              : "idconcopia",
      jsId            : "idconcopia",
      storeUrl        : "getFavorito.action?mode=idconcopia",
      searchAttr      : "label",
      searchDelay     : 650,
      queryExpr       : "*${0}*",
      autoComplete    : false,
      hasDownArrow    : true,
      highlightMatch  : "all",
      style          : "width:400px;",
      required        : false,
      onChange        : agregarConCopia
   });
   var idProceso;
    if(document.getElementById("objDD.iIdProceso")!=null)
	    idProceso  = document.getElementById("objDD.iIdProceso").value;


	//var storeEtapas = new dojo.data.ItemFileReadStore({url: "autocompletarEtapasporProceso.action?idProceso="+idProceso});
		/*if(storeEtapas){
			dojo.byId("trEtapa").style.display = "none";
		}*/
	Siged.Forms.combobox("fsEtapaProceso", {
	      id             : "objExpediente.idetapa.idetapa",
	      jsId           : "objExpediente.idetapa.idetapa",
	      name           : "objExpediente.idetapa.idetapa",
	      storeUrl       : "autocompletarEtapasporProceso.action?idProceso="+idProceso,
	      searchAttr     : "label",
	      searchDelay    : 650,
	      queryExpr      : "*${0}*",
	      autoComplete   : false,
	      hasDownArrow   : true,
	      highlightMatch : "all",
	      required       : true,
	      style          : "width:180px;",
	      invalidMessage : "Seleccione una Etapa"
	});

	//dijit.byId("objExpediente.idetapa.idetapa").store = storeEtapas;
});