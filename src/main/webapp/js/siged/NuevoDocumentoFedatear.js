
dojo.addOnLoad(function() {

	new dijit.form.TextBox({
		id : "objDF.nroDocumentoArea",
	    jsId : "objDF.nroDocumentoArea",
	    name : "objDF.nroDocumentoArea",
	    readOnly : true,
	    trim : true,
	    style : "width:250px"
	},"objDF.nroDocumentoArea");


	try {
		new dijit.form.FilteringSelect({
			id:			"objDF.idTipoDocumentoFedateado",
			jsId:		"objDF.idTipoDocumentoFedateado",
			name:		"objDF.idTipoDocumentoFedateado",
			store:		new dojo.data.ItemFileReadStore({
							url: "autocompletarTipoDocumentoFedatario.action"
						}),
			searchAttr:	"label",
			queryExpr:	"*${0}*",
			autoComplete:	false,
			hasDownArrow:	true,
			highlightMatch:	"all",
			style : "width:250px;",
			required:	false
		},"objDF.idTipoDocumentoFedateado");
		}catch(error) {
			console.log(error+" error registering : fedatario") ;
		}
/*
	new dijit.form.TextBox({
		id : "sNroDocumentoOperacionFedatear",
	    jsId : "sNroDocumentoOperacionFedatear",
	    name : "sNroDocumentoOperacionFedatear",
	    readOnly : false,
	    trim : true,
	    style : "width:165px"
	},"sNroDocumentoOperacionFedatear");


	new dijit.form.TextBox({
		id : "sAsuntoDocumentoOperacionFedatear",
	    jsId : "sAsuntoDocumentoOperacionFedatear",
	    name : "sAsuntoDocumentoOperacionFedatear",
	    readOnly : false,
	    trim : true,
	    style : "width:220px"
	},"sAsuntoDocumentoOperacionFedatear");


	new dijit.form.TextBox({
		id : "sFechaDocumentoOperacionFedatear",
	    jsId : "sFechaDocumentoOperacionFedatear",
	    name : "sFechaDocumentoOperacionFedatear",
	    readOnly : false,
	    trim : true,
	    style : "width:165px"
	},"sFechaDocumentoOperacionFedatear");

 */


});


submitFormFedatear = function() {
	if(dijit.byId("dlgProgresBar")!=null){
		dijit.byId("dlgProgresBar").show() ;
	}

	sTipoGridActual = TIPO_GRID_EXPEDIENTES;
    var myForm = dijit.byId("frmNuevoDocumentoFedatear");


    if (dijit.byId("objDF.nroDocumentoArea").attr("value")==''){
    	dijit.byId("objDF.idTipoDocumentoFedateado").attr("required", true);
    	dijit.byId("objDF.nroDocumento").attr("required", true);
    	dijit.byId("objDF.asunto").attr("required", true);
    	dijit.byId("objDF.fechaDocumentoRegistro").attr("required", true);
    }else{
    	dijit.byId("objDF.idTipoDocumentoFedateado").attr("required", false);
    	dijit.byId("objDF.nroDocumento").attr("required", false);
    	dijit.byId("objDF.asunto").attr("required", false);
    	dijit.byId("objDF.fechaDocumentoRegistro").attr("required", false);
    }



    if (dijit.byId("radInterno").attr("value") == 'on'){
    	dijit.byId("objDF.desUnidad").attr("required", false);
    	dijit.byId("objDF.idUnidadSolicitante").attr("required", true);
    }else{
    	dijit.byId("objDF.idUnidadSolicitante").attr("required", false);
    	dijit.byId("objDF.desUnidad").attr("required", true);
    }



   if (!myForm.validate()) {
      dojo.byId("showErrorDocumento").innerHTML = "&nbsp;";
      if(dijit.byId("dlgProgresBar")!=null){
    	  dijit.byId("dlgProgresBar").hide() ;
	  }
      return;
   }

   dojo.xhrPost({
       form: dojo.byId("frmNuevoDocumentoFedatear"),
       mimetype: "text/html",
       load: function(data) {
          dijit.byId("tabOperacionFedatario").attr("content", data);
          if(dijit.byId("dlgProgresBar")!=null){
				dijit.byId("dlgProgresBar").hide() ;
			}
       }
    });

   dijit.byId("btnRegistrarDocumentoFedatearTop").attr("disabled", true);
   dijit.byId("btnRegistrarDocumentoFedatearBottom").attr("disabled", true);

};





