dojo.provide("js.main");
var indexTreeExp=0;
var interfazGerente = false;
var service = new dojo.rpc.JsonService("SMDAction.action");

var arrImgBolitas = new Array();
var buildTop = function(parent) {
   
    new dojox.layout.ContentPane({
        href: "pages/layout/top.jsp",
        region: "top",
        style: "height:50px;overflow:hidden;"
    }).placeAt(parent);
};

var buildMenu = function(parent) {
   
    console.debug("(buildMenu) Parent ID [" + parent.id + "]");

    new dojox.layout.ExpandoPane({
       // href: "pages/layout/menu.jsp",
    	href: "inicioMenu.action",
        region: "left",
        style: "width:10.6%;",
        title: "Men&uacute; de Opciones"
    }).placeAt(parent);
};

var recargargrid = false ;
var buildInboxIG = function(parent) {
    
    console.debug("(buildInbox) Parent ID [" + parent.id + "]");

     var tabContainerInbox = new siged.TabContainer({
         id: "tabContainerInbox",
         jsId: "tabContainerInbox",
         region: "center"
     }).placeAt(parent);

     var borderContainerGeneral = new dijit.layout.BorderContainer({
         id: "borderContainerGeneral",
         jsId: "borderContainerGeneral",
         title: "Bandeja de General",
         region:"center",
         style: "width:100%; overflow:auto;"
     }).placeAt(tabContainerInbox);


     var borderContainerInbox = new dijit.layout.BorderContainer({
         id: "borderContainerInbox",
         jsId: "borderContainerInbox",
         style: "width:50%; overflow:auto;",
         region:"left",
         title: "Bandeja de Entrada "
     }).placeAt(borderContainerGeneral);


     var borderContainerVisor = new dijit.layout.BorderContainer({
         id: "borderContainerVisor",
         jsId: "borderContainerVisor",
         title: "Bandeja de Entrada ",
         region:"center",
         style: "width:49%; overflow:auto;"
     }).placeAt(borderContainerGeneral);

     new dojox.layout.ContentPane({
         id: "contentPaneVisor",
         href: "pages/layout/errorVisor.jsp",
         jsId: "contentPaneVisor",
         style: "width:97%; overflow:auto;",
         region:"center"
     }).placeAt(borderContainerVisor);

     new dijit.Toolbar({
         id: "toolBarInbox",
         jsId: "toolBarInbox",
         region: "top",
         style: "height:40px;"
     }).placeAt(borderContainerInbox);

     dijit.byId("toolBarInbox").addChild(dijit.byId("btnDerivarMasivamente"));
     //dijit.byId("toolBarInbox").addChild(dijit.byId("btnArchivarMasivamente"));
     dijit.byId("toolBarInbox").addChild(dijit.byId("txtFiltrarBandeja"));
     dijit.byId("toolBarInbox").addChild(dijit.byId("lblFiltrarBandeja"));
     //

     //  dijit.byId("toolBarInbox").addChild(dijit.byId("txtFiltrarMensaje"));
     // dijit.byId("toolBarInbox").addChild(dijit.byId("lblFiltrarMensaje"));
     //
     new dojox.layout.ContentPane({
         id: "contentPaneGrid",
         jsId: "contentPaneGrid",
         style: "width:97%; overflow:auto;",
         region:"center"
     }).placeAt(borderContainerInbox);
     //Agregado por German para mostrar feedback mientras carga el grid
     var cargando=document.getElementById("cargando");
     dojo.byId("contentPaneGrid").appendChild(cargando);
     buildDetail(borderContainerInbox);


};

var buildInbox = function(parent) {
   
    console.debug("(buildInbox) Parent ID [" + parent.id + "]");

    var tabContainerInbox = new siged.TabContainer({
        id: "tabContainerInbox",
        jsId: "tabContainerInbox",
        region: "center" 
    }).placeAt(parent);

    var borderContainerGeneral = new dijit.layout.BorderContainer({
        id: "borderContainerGeneral",
        jsId: "borderContainerGeneral",
        title: "Bandeja de General",
        region:"center",
        style: "width:100%; overflow:auto;"
    }).placeAt(tabContainerInbox);

    var borderContainerInbox = new dijit.layout.BorderContainer({
        id: "borderContainerInbox",
        jsId: "borderContainerInbox",
        style: "width:99%; overflow:auto;",
        region:"center",
        title: "Bandeja de Entrada"
    }).placeAt(borderContainerGeneral);


    new dijit.Toolbar({
        id: "toolBarInbox",
        jsId: "toolBarInbox",
        region: "top",
        style: "height:20px;"
    }).placeAt(borderContainerInbox);

    dijit.byId("toolBarInbox").addChild(dijit.byId("btnDerivarMasivamente"));
    dijit.byId("toolBarInbox").addChild(dijit.byId("btnDerivarDocMasivamente"));
    dijit.byId("toolBarInbox").addChild(dijit.byId("idFiltroTipoDocumento"));
    dijit.byId("toolBarInbox").addChild(dijit.byId("idFiltroTipo"));
    //dijit.byId("toolBarInbox").addChild(dijit.byId("btnArchivarMasivamente"));
    dijit.byId("toolBarInbox").addChild(dijit.byId("txtFiltrarBandeja"));
    dijit.byId("toolBarInbox").addChild(dijit.byId("lblFiltrarBandeja"));
    
    new dojox.layout.ContentPane({
        id: "contentPaneGrid",
        jsId: "contentPaneGrid",
        style: "width:99%; overflow:auto;",
        region:"center"
    }).placeAt(borderContainerInbox);
    //Agregado por German para mostrar feedback mientras carga el grid
    var cargando=document.getElementById("cargando");
    dojo.byId("contentPaneGrid").appendChild(cargando);
    
    buildDetail(borderContainerInbox);
};

var buildSharedToolbar = function(idUsuario) {
 
    console.debug("(buildSharedToolbar) idUsuario->" + idUsuario);
  
    if (dijit.byId("btnImprimirBandejaCompartidos" +  idUsuario+ "")){
    	dijit.byId("btnImprimirBandejaCompartidos" +  idUsuario+ "").destroy();
    }

    new dijit.form.Button({
        id: "btnImprimirBandejaCompartidos" + idUsuario+ "",
        jsId: "btnImprimirBandejaCompartidos"  + idUsuario+ "",
        iconClass: "siged16 iconoImprimir",
        label: "Imprimir",
        onClick: function(){
        	 var opciones = "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=1000, height=600, top=20, left=70";
      	     var pagina = "ReporteAPN_reporteDocumentosCompartidos.action?usuarioCompartido=" + idUsuario;
      	     window.open(pagina, "", opciones);
        },//imprimirDocumentosCompartidos,
        showLabel: true
    });


    dijit.byId("toolBarInbox"+idUsuario).addChild(dijit.byId("btnImprimirBandejaCompartidos"+idUsuario));
    console.debug("(buildSharedToolbar) controles agreados correctamente");
};
var buildSharedInbox = function(idUsuario, nombreUsuario) {

    //console.debug("(buildSharedInbox) idUsuario->" + idUsuario);
    //   if (dijit.byId("borderContainerInbox" + idUsuario)) {
    //      dijit.byId("borderContainerInbox" + idUsuario).destroy();
    //      console.debug("(buildSharedInbox) borderContainerInbox destruido");
    //   }

    if (!dijit.byId("borderContainerInbox" + idUsuario)) {
        var borderContainerInbox = new dijit.layout.BorderContainer({
            id: "borderContainerInbox"+idUsuario,
            jsId: "borderContainerInbox"+idUsuario,
            title: nombreUsuario,
            closable: true
        }).placeAt(dijit.byId("tabContainerInbox")).startup();
        //console.debug("(buildSharedInbox) creado borderContainerInbox"+idUsuario);
    }

    if (dijit.byId("toolBarInbox" + idUsuario)) {
        dijit.byId("toolBarInbox" + idUsuario).destroy();
        //console.debug("(buildSharedInbox) toolBarInbox" + idUsuario + " destruido");
    }

    new dijit.Toolbar({
        id: "toolBarInbox"+idUsuario,
        jsId: "toolBarInbox"+idUsuario,
        region: "top",
        style: "height:20px;"
    }).placeAt(dijit.byId("borderContainerInbox"+idUsuario)).startup();


    if (dijit.byId("contentPaneGrid" + idUsuario)) {
        dijit.byId("contentPaneGrid" + idUsuario).destroy();
        console.debug("(buildSharedInbox) contentPaneGrid" + idUsuario + " destruido");
    }
    new dojox.layout.ContentPane({
        id: "contentPaneGrid"+idUsuario,
        jsId: "contentPaneGrid"+idUsuario,
        style: "width:97%;",
        region:"center",
        closable: true
    }).placeAt(dijit.byId("borderContainerInbox"+idUsuario)).startup();
    console.debug("(buildSharedInbox) contentPaneGrid" + idUsuario + " creado");
    //Agregado por German para mostrar feedback mientras carga el grid
    //   var cargando=document.getElementById("cargando");
    //   dojo.byId("contentPaneGrid"+idUsuario).appendChild(cargando);

    buildSharedDetail(dijit.byId("borderContainerInbox"+idUsuario), idUsuario);
};

function Abrir_Detalle (pagina) {
    var opciones="toolbar=no, location=no, directories=no, status=yes, menubar=no, scrollbars=yes, resizable=yes, width=1000, height=700, top=50, left=200";
    var ventana = window.open(pagina,"",opciones);
    ventana.moveTo(60,50);
}

var buildDetail = function(parent) {
    console.debug("(buildDetail) Parent ID [" + parent.id + "]");

    var tabContainerDetail = new dijit.layout.TabContainer({
        id: "tabContainerDetail",
        jsId: "tabContainerDetail",
        region: "bottom",
        splitter: true,
        style: "height:50%;width:97%;",
        //tabPosition: "left-h",
        tabStrip: true,
        onDblClick: function(){
       
        /*Abre la pestaï¿½a en un popup
    	     * var i = 0;
    	  	dojo.forEach(this.getChildren(), function(item) {
    	  		i++;
	    		if(item.attr("selected")&&i!=2){
	    			Abrir_Detalle(item.attr("href")+"&enVentana=true");
	    		}
	    	});*/
        }
    }).placeAt(parent);

    var conTabGeneral=true;
    for (var i = 0; i < arrRecursoFirstGrid.length; i++) {
        var item = arrRecursoFirstGrid[i];
        if (item.valor && (item.codigo=="MensMnuDocRec" || item.codigo=="MensMnuDocCer")) {
            console.debug("para caso de mensajeria no se crea el tab general");
            conTabGeneral = false;
            break;
        }
    }
    
    if (conTabGeneral) {
        new dojox.layout.ContentPane( {
            id : "contentPaneDetail",
            jsId : "contentPaneDetail",
            // iconClass: 'dijitEditorIcon dijitEditorIconCut',
            style : "width:93%;overflow: scroll;" ,
            title : "Detalle"
        }).placeAt(tabContainerDetail);
    }


};

var buildSharedDetail = function(parent,idUsuario) {

    console.debug("(buildSharedDetail) Parent ID [" + parent.id + "]");
    console.debug("(buildSharedDetail) idUsuario->" + idUsuario);

    if (!dijit.byId("tabContainerDetail" + idUsuario)) {
        var tabContainerDetail = new dijit.layout.TabContainer({
            id: "tabContainerDetail"+idUsuario,
            //jsId: "tabContainerDetail"+idUsuario,
            region: "bottom",
            splitter: true,
            style: "height:50%;width:97%;",
            //tabPosition: "left-h",
            tabStrip: true
            /*onDblClick: function(){
            }*/
        }).placeAt(parent).startup();
        console.debug("(buildSharedDetail) Tab Contenedor de detalle agregado->tabContainerDetail"+idUsuario);
    }


    var conTabGeneral=true;
   
    if (conTabGeneral) {
    	if (!dijit.byId("contentPaneDetail" + idUsuario)) {
            var paneBandeja = new dojox.layout.ContentPane( {
                id : "contentPaneDetail"+idUsuario,
                //jsId : "contentPaneDetail"+idUsuario,
                // iconClass: 'dijitEditorIcon dijitEditorIconCut',
                //	style : "width:93%;overflow:scroll;",
                title : "Detalle del Documento"
            });
            dijit.byId("tabContainerDetail"+idUsuario).addChild(paneBandeja);
        }
        console.debug("(buildSharedDetail) Panel Contenedor de detalle agregado->contentPaneDetail"+idUsuario);
    }

};

var buildTabDocumentosAdicionales = function(sURL, id, titulo) {
    var isShowing = false;
    //Agregado para que cada vez que se cree esta pestaÃ±a, destruya la anterior
    if (dijit.byId(id)) {
        dijit.byId("tabContainerDetail").removeChild(dijit.byId(id));
        dijit.byId(id).destroy();
    }
    if(dijit.byId("dlgBusquedaExpediente")){
        dijit.byId("dlgBusquedaExpediente").destroy();
    }
    if(dijit.byId("sNroExpedienteBE")){
        dijit.byId("sNroExpedienteBE").destroy();
    }
    if(dijit.byId("sAsuntoBE")){
        dijit.byId("sAsuntoBE").destroy();
    }
    if(dijit.byId("procesoBE")){
        dijit.byId("procesoBE").destroy();
    }
    if(dijit.byId("sNroIdentificacionBE")){
        dijit.byId("sNroIdentificacionBE").destroy();
    }
    if(dijit.byId("fechaBE")){
        dijit.byId("fechaBE").destroy();
    }
    if(dijit.byId("sAsuntoBE")){
        dijit.byId("sAsuntoBE").destroy();
    }
    if(dijit.byId("sRazonSocialBE")){
        dijit.byId("sRazonSocialBE").destroy();
    }
    
    if(dijit.byId("sNTBE")){
        dijit.byId("sNTBE").destroy();
    }
    
    if(dijit.byId("grdBusquedaExpediente")){
        dijit.byId("grdBusquedaExpediente").destroy();
    }
    new dojox.layout.ContentPane({
        id: id,
        jsId: id,
        //href: sURL,
        _onShow: function() {
            if (!isShowing) {
                this.attr("href", sURL);
                isShowing = true;
            }
        },
        title: titulo
    }).placeAt(dijit.byId("tabContainerDetail"));
};

var buildTabDocumentosAdicionalesByUser = function(sURL, id, titulo, idUsuario) {
    console.debug("(buildTabDocumentosAdicionalesByUser) id->" + id);
    console.debug("(buildTabDocumentosAdicionalesByUser) idUsuario->" + idUsuario);
    var isShowing = false;
    if (!dijit.byId(id+idUsuario)) {
        new dojox.layout.ContentPane({
            id: id+idUsuario,
            jsId: id+idUsuario,
            //href: sURL,
            _onShow: function() {
                if (!isShowing) {
                    this.attr("href", sURL);
                    isShowing = true;
                }
            },
            title: titulo
        }).placeAt(dijit.byId("tabContainerDetail"+idUsuario));
        console.debug("(buildTabDocumentosAdicionalesByUser) " + id + idUsuario + " creado");
    } else {
        dijit.byId(id+idUsuario).attr("href", sURL);
        console.debug("(buildTabDocumentosAdicionalesByUser) " + id + idUsuario + " reutilizado");
    }
    console.debug("(buildTabDocumentosAdicionalesByUser) documentos cargados correctamente");
};

var buildToolBarInboxButtons = function() {

    new dijit.form.TextBox({
        id      : "lblFiltrarBandeja",
        jsId    : "lblFiltrarBandeja",
        value   : "Filtrar:",
        disabled: true,
        style   : "float:right;"
    });
    
    new dijit.form.Button({
        id: "lblFiltrarTipo",
        jsId: "lblFiltrarTipo",
        label: "Tipo",
        showLabel: true,
        disabled: true,
        style   : "float:right;"
    });
    
    new dijit.form.Button({
        id: "lblFiltrarEstado",
        jsId: "lblFiltrarEstado",
        label: "Estado",
        showLabel: true,
        disabled: true,
        style   : "float:right;"
    });
    
    new dijit.form.Button({
        id: "lblFiltrarTipoDocumento",
        jsId: "lblFiltrarTipoDocumento",
        label: "Tipo Documento",
        showLabel: true,
        disabled: true,
        style   : "float:right;"
    });
    
    new dijit.form.Button({
        id: "lblFiltrarMetodo",
        jsId: "lblFiltrarMetodo",
        label: "Metodo",
        showLabel: true,
        disabled: true,
        style   : "float:right;"
    });
    
    new dijit.form.Button({
        id: "lblFiltrarProcedimiento",
        jsId: "lblFiltrarProcedimiento",
        label: "Procedimiento",
        showLabel: true,
        disabled: true,
        style   : "float:right;"
    });
    
    new dijit.form.Button({
        id: "lblFiltrarCUO",
        jsId: "lblFiltrarCUO",
        label: "CUO",
        showLabel: true,
        disabled: true,
        style   : "float:right;"
    });
    
    new dijit.form.Button({
        id: "lblFiltrarAsunto",
        jsId: "lblFiltrarAsunto",
        label: "Asunto",
        showLabel: true,
        disabled: true,
        style   : "float:right;"
    });
    
    new dijit.form.Button({
        id: "lblFiltrarContador",
        jsId: "lblFiltrarContador",
        label: "",
        showLabel: true,
        disabled: true,
        style   : "float:left;"
    });
    
    
     new dijit.form.Button({
        id: "lblFiltrarIdUsuario",
        jsId: "lblFiltrarIdUsuario",
        label: "Usuario",
        showLabel: true,
        disabled: true,
        style   : "float:right;"
    });
    
    
    //////////////////
    
    new dijit.form.FilteringSelect({
      id             : "idFiltroProcedimiento",
      jsId           : "idFiltroProcedimiento",
      name           : "idFiltroProcedimiento",
      searchAttr     : "label",
      autoComplete   : false, 
      hasDownArrow   : true,
      highlightMatch : "all",
      onChange       : function() {  
              if (dijit.byId("gridInbox")) {
                  if (dijit.byId("idFiltroProcedimiento").attr("value")== '-1'){
                     dijit.byId("gridInbox").setQuery(
			{
		   	  idProcedimiento: "*" ,
                          idMetodo: dijit.byId("idFiltroMetodo").attr("value")== '-1'? "*" :dijit.byId("idFiltroMetodo").attr("value"),
                          asuntolegajo: "*" + dijit.byId("idFiltrarAsuntoLegajo").attr("value") + "*"  
			},
			{
			  ignoreCase: true
		        });
                        
                        var dato_ = "";
                         var dato = dijit.byId("borderContainerGeneral").attr("title");
                         var posicion = dato.indexOf("[");
                         var grid = dijit.byId("gridInbox");
                         var contador = 0;
                         grid.store.fetch({query : {idProcedimiento : "*", idMetodo: dijit.byId("idFiltroMetodo").attr("value")== '-1'? "*" :dijit.byId("idFiltroMetodo").attr("value"), asuntolegajo: "*" + dijit.byId("idFiltrarAsuntoLegajo").attr("value") + "*" },
                           onItem : function (item ) {
                              contador ++;
                           }
                         });
                         
                         if (posicion!=-1){
                            dato = dato.substring(0, posicion);    
                         }

                         dato_ = " [" + contador + " documento(s)]"; 
                         dijit.byId("borderContainerGeneral").setTitle(dato + dato_);
                  }else{
                        dijit.byId("gridInbox").setQuery(
			{
		   	  idProcedimiento: this.attr("value"),
                          idMetodo: dijit.byId("idFiltroMetodo").attr("value")== '-1'? "*" :dijit.byId("idFiltroMetodo").attr("value"),
                          asuntolegajo: "*" + dijit.byId("idFiltrarAsuntoLegajo").attr("value") + "*"
			},
			{
			  ignoreCase: true
		        });
                        
                        var dato_ = "";
                         var dato = dijit.byId("borderContainerGeneral").attr("title");
                         var posicion = dato.indexOf("[");
                         var grid = dijit.byId("gridInbox");
                         var contador = 0;
                         grid.store.fetch({query : {idProcedimiento : this.attr("value"), idMetodo: dijit.byId("idFiltroMetodo").attr("value")== '-1'? "*" :dijit.byId("idFiltroMetodo").attr("value"), asuntolegajo: "*" + dijit.byId("idFiltrarAsuntoLegajo").attr("value") + "*"},
                           onItem : function (item ) {
                              contador ++;
                           }
                         });
                         
                         if (posicion!=-1){
                            dato = dato.substring(0, posicion);    
                         }

                         dato_ = " [" + contador + " documento(s)]"; 
                         dijit.byId("borderContainerGeneral").setTitle(dato + dato_);
                  }     		 
              }
             this.focus(); 
             dijit.byId("contentPaneDetail").attr("content", "");
	 },
      store   : new dojo.data.ItemFileReadStore({url: "autocompletarParametroActivo.action?codParametro=PROCEDIMIENTO_LEGAJO&iWithoutStor=1"}),
      style   : "float:right;width:163px;"
   }, "idFiltroProcedimiento");
   
   
   
   ///////////////////////
   
   
   new dijit.form.FilteringSelect({
      id             : "idFiltroMetodo",
      jsId           : "idFiltroMetodo",
      name           : "idFiltroMetodo",
      searchAttr     : "label",
      autoComplete   : false, 
      hasDownArrow   : true,
      highlightMatch : "all",
      onChange       : function() {  
              if (dijit.byId("gridInbox")) {
                  if (dijit.byId("idFiltroMetodo").attr("value")== '-1'){
                     dijit.byId("gridInbox").setQuery(
			{
                          idMetodo: "*" ,  
		   	  idProcedimiento: dijit.byId("idFiltroProcedimiento").attr("value")== '-1'? "*" :dijit.byId("idFiltroProcedimiento").attr("value") ,
                          asuntolegajo: "*" + dijit.byId("idFiltrarAsuntoLegajo").attr("value") + "*"  
			},
			{
			  ignoreCase: true
		        });
                        
                         var dato_ = "";
                         var dato = dijit.byId("borderContainerGeneral").attr("title");
                         var posicion = dato.indexOf("[");
                         var grid = dijit.byId("gridInbox");
                         var contador = 0;
                         grid.store.fetch({query : {idMetodo : "*", idProcedimiento : dijit.byId("idFiltroProcedimiento").attr("value")== '-1'? "*" :dijit.byId("idFiltroProcedimiento").attr("value") , asuntolegajo: "*" + dijit.byId("idFiltrarAsuntoLegajo").attr("value") + "*" },
                           onItem : function (item ) {
                              contador ++;
                           }
                         });
                         
                         if (posicion!=-1){
                            dato = dato.substring(0, posicion);    
                         }

                         dato_ = " [" + contador + " documento(s)]"; 
                         dijit.byId("borderContainerGeneral").setTitle(dato + dato_);
                  }else{
                        dijit.byId("gridInbox").setQuery(
			{
                          idMetodo: this.attr("value"),  
		   	  idProcedimiento: dijit.byId("idFiltroProcedimiento").attr("value")== '-1'? "*" :dijit.byId("idFiltroProcedimiento").attr("value"),
                          asuntolegajo: "*" + dijit.byId("idFiltrarAsuntoLegajo").attr("value") + "*"
			},
			{
			  ignoreCase: true
		        });
                        
                        var dato_ = "";
                         var dato = dijit.byId("borderContainerGeneral").attr("title");
                         var posicion = dato.indexOf("[");
                         var grid = dijit.byId("gridInbox");
                         var contador = 0;
                         grid.store.fetch({query : {idMetodo: this.attr("value"), idProcedimiento : dijit.byId("idFiltroProcedimiento").attr("value")== '-1'? "*" :dijit.byId("idFiltroProcedimiento").attr("value"), asuntolegajo: "*" + dijit.byId("idFiltrarAsuntoLegajo").attr("value") + "*"},
                           onItem : function (item ) {
                              contador ++;
                           }
                         });
                         
                         if (posicion!=-1){
                            dato = dato.substring(0, posicion);    
                         }

                         dato_ = " [" + contador + " documento(s)]"; 
                         dijit.byId("borderContainerGeneral").setTitle(dato + dato_);
                  }     		 
              }
             this.focus(); 
             dijit.byId("contentPaneDetail").attr("content", "");
	 },
      store   : new dojo.data.ItemFileReadStore({url: "autocompletarParametroActivo.action?codParametro=METODO_LEGAJO&iWithoutStor=1"}),
      style   : "float:right;width:163px;"
   }, "idFiltroMetodo");
    
    /////////////////////
    
    new dijit.form.FilteringSelect({
      id             : "idFiltroUsuarioPendiente",
      jsId           : "idFiltroUsuarioPendiente",
      name           : "idFiltroUsuarioPendiente",
      searchAttr     : "label",
     // queryExpr      : "*${0}*",
      autoComplete   : false, 
      hasDownArrow   : true,
      highlightMatch : "all",
      onChange       : function() {  
              if (dijit.byId("gridInbox")) {
                if (dijit.byId("idFiltroUsuarioPendiente").attr("value")== '-1'){
                     dijit.byId("gridInbox").setQuery(
			{
		   	  idPropietario: "*" 
			},
			{
			  ignoreCase: true
		        });
                        
                        var dato_ = "";
                         var dato = dijit.byId("borderContainerGeneral").attr("title");
                         var posicion = dato.indexOf("[");
                         var grid = dijit.byId("gridInbox");
                         var contador = 0;
                         grid.store.fetch({query : {idPropietario : "*"},
                           onItem : function (item ) {
                              contador ++;
                           }
                         });
                         
                         if (posicion!=-1){
                            dato = dato.substring(0, posicion);    
                         }

                         dato_ = " [" + contador + " documento(s)]"; 
                         dijit.byId("borderContainerGeneral").setTitle(dato + dato_);
		}else{
		      dijit.byId("gridInbox").setQuery(
			{
			   idPropietario: this.attr("value") 
			},

			{
			  ignoreCase: true
			 });
                         
                         var dato_ = "";
                         var dato = dijit.byId("borderContainerGeneral").attr("title");
                         var posicion = dato.indexOf("[");
                         var grid = dijit.byId("gridInbox");
                         var contador = 0;
                         grid.store.fetch({query : {idPropietario : this.attr("value")},
                           onItem : function (item ) {
                              contador ++;
                           }
                         });
                         
                         if (posicion!=-1){
                            dato = dato.substring(0, posicion);    
                         }

                         dato_ = " [" + contador + " documento(s)]"; 
                         dijit.byId("borderContainerGeneral").setTitle(dato + dato_);
                }		 
              }
             this.focus(); 
             dijit.byId("contentPaneDetail").attr("content", "");
	 },
      store   : new dojo.data.ItemFileReadStore({url: "autocompletarUsuariosxUnidad.action?iWithoutStor=1"}),
      style   : "float:right;width:250px;"
   }, "idFiltroUsuarioPendiente");
   
   
   new dijit.form.FilteringSelect({
      id             : "idFiltroUsuarioEnviado",
      jsId           : "idFiltroUsuarioEnviado",
      name           : "idFiltroUsuarioEnviado",
      searchAttr     : "label",
     // queryExpr      : "*${0}*",
      autoComplete   : false, 
      hasDownArrow   : true,
      highlightMatch : "all",
      onChange       : function() {  
              if (dijit.byId("gridInbox")) {
                if (dijit.byId("idFiltroUsuarioEnviado").attr("value")== '-1'){
                     dijit.byId("gridInbox").setQuery(
			{
		   	  idDestinatario: "*" ,
                          tipodocumento: dijit.byId("idFiltroTipoDocumentoEnviado").attr("value")== '-1'? "*" :dijit.byId("idFiltroTipoDocumentoEnviado").attr("value")  
			},
			{
			  ignoreCase: true
		        });
                        
                        var dato_ = "";
                         var dato = dijit.byId("borderContainerGeneral").attr("title");
                         var posicion = dato.indexOf("[");
                         var grid = dijit.byId("gridInbox");
                         var contador = 0;
                         grid.store.fetch({query : {idDestinatario : "*", tipodocumento: dijit.byId("idFiltroTipoDocumentoEnviado").attr("value")== '-1'? "*" :dijit.byId("idFiltroTipoDocumentoEnviado").attr("value")  },
                           onItem : function (item ) {
                              contador ++;
                           }
                         });
                         
                         if (posicion!=-1){
                            dato = dato.substring(0, posicion);    
                         }

                         dato_ = " [" + contador + " documento(s)]"; 
                         dijit.byId("borderContainerGeneral").setTitle(dato + dato_);
		}else{
		      dijit.byId("gridInbox").setQuery(
			{
			   idDestinatario: this.attr("value") ,
                           tipodocumento: dijit.byId("idFiltroTipoDocumentoEnviado").attr("value")== '-1'? "*" :dijit.byId("idFiltroTipoDocumentoEnviado").attr("value")  
			},

			{
			  ignoreCase: true
			 });
                         
                         var dato_ = "";
                         var dato = dijit.byId("borderContainerGeneral").attr("title");
                         var posicion = dato.indexOf("[");
                         var grid = dijit.byId("gridInbox");
                         var contador = 0;
                         grid.store.fetch({query : {idDestinatario : this.attr("value"), tipodocumento: dijit.byId("idFiltroTipoDocumentoEnviado").attr("value")== '-1'? "*" :dijit.byId("idFiltroTipoDocumentoEnviado").attr("value")  },
                           onItem : function (item ) {
                              contador ++;
                           }
                         });
                         
                         if (posicion!=-1){
                            dato = dato.substring(0, posicion);    
                         }

                         dato_ = " [" + contador + " documento(s)]"; 
                         dijit.byId("borderContainerGeneral").setTitle(dato + dato_);
                }		 
              }
             this.focus(); 
             dijit.byId("contentPaneDetail").attr("content", "");
	 },
      store   : new dojo.data.ItemFileReadStore({url: "autocompletarEnvioUsuariosDestinatarios.action"}),
      style   : "float:right;width:250px;"
   }, "idFiltroUsuarioEnviado");
   
   
   new dijit.form.FilteringSelect({
      id             : "idFiltroTipoDocumentoEnviado",
      jsId           : "idFiltroTipoDocumentoEnviado",
      name           : "idFiltroTipoDocumentoEnviado",
      searchAttr     : "label",
      queryExpr      : "*${0}*",
      autoComplete   : false, 
      hasDownArrow   : true,
      highlightMatch : "all",
      onChange       : function() {  
              if (dijit.byId("gridInbox")) {
                if (dijit.byId("idFiltroTipoDocumentoEnviado").attr("value")== '-1'){
                     dijit.byId("gridInbox").setQuery(
			{
		   	  tipodocumento: "*" ,
                          idDestinatario: dijit.byId("idFiltroUsuarioEnviado").attr("value")== '-1'? "*" :dijit.byId("idFiltroUsuarioEnviado").attr("value")
			},
			{
			  ignoreCase: true
		        });
                        
                         var dato_ = "";
                         var dato = dijit.byId("borderContainerGeneral").attr("title");
                         var posicion = dato.indexOf("[");
                         var grid = dijit.byId("gridInbox");
                         var contador = 0;
                         grid.store.fetch({query : {tipodocumento : "*",
                                                    idDestinatario: dijit.byId("idFiltroUsuarioEnviado").attr("value")== '-1'? "*" :dijit.byId("idFiltroUsuarioEnviado").attr("value")
                                                   },
                           onItem : function (item ) {
                              contador ++;
                           }
                         });
                         
                         if (posicion!=-1){
                            dato = dato.substring(0, posicion);    
                         }

                         dato_ = " [" + contador + " documento(s)]"; 
                         dijit.byId("borderContainerGeneral").setTitle(dato + dato_);
                         
		}else{
                      dijit.byId("gridInbox").setQuery(
			{
			   tipodocumento: this.attr("value") ,
                           idDestinatario: dijit.byId("idFiltroUsuarioEnviado").attr("value")== '-1'? "*" :dijit.byId("idFiltroUsuarioEnviado").attr("value")
			},

			{
			  ignoreCase: true
			 });
                         
                         var dato_ = "";
                         var dato = dijit.byId("borderContainerGeneral").attr("title");
                         var posicion = dato.indexOf("[");
                         var grid = dijit.byId("gridInbox");
                         var contador = 0;
                         grid.store.fetch({query : {tipodocumento :  this.attr("value"),
                                                    idDestinatario: dijit.byId("idFiltroUsuarioEnviado").attr("value")== '-1'? "*" :dijit.byId("idFiltroUsuarioEnviado").attr("value")
                                                },
                           onItem : function (item ) {
                              contador ++;
                           }
                         });
                         
                         if (posicion!=-1){
                            dato = dato.substring(0, posicion);    
                         }

                         dato_ = " [" + contador + " documento(s)]"; 
                         dijit.byId("borderContainerGeneral").setTitle(dato + dato_);       
                }	
              }
             this.focus(); 
             dijit.byId("contentPaneDetail").attr("content", "");
	 },
      store          : new dojo.data.ItemFileReadStore({url: "autocompletarAllTipoDocumentoFiltro.action?iWithoutStor=1"}),
      style   : "float:right;width:220px;"
   }, "idFiltroTipoDocumentoEnviado");
    
    
    new dijit.form.FilteringSelect({
      id             : "idFiltroMesesEnviado",
      jsId           : "idFiltroMesesEnviado",
     name           : "idFiltroMesesEnviado",
      searchAttr     : "label",
     // queryExpr      : "*${0}*",
      autoComplete   : false, 
      hasDownArrow   : true,
      highlightMatch : "all",
      onChange       : function() {
        var fechaActual = new Date();
        var fechaDesde = new Date();
        var dataForm = {fechaDesde:"", fechaHasta:""};
        var tipoGrid = TIPO_GRID_ENVIADOS;
        if (dijit.byId("idFiltroMesesEnviado").attr("value")== '3'){
            fechaDesde.setMonth(fechaDesde.getMonth() - 3);   
        }
        if (dijit.byId("idFiltroMesesEnviado").attr("value")== '6'){
            fechaDesde.setMonth(fechaDesde.getMonth() - 6);  
        }
        if (dijit.byId("idFiltroMesesEnviado").attr("value")== '9'){
            fechaDesde.setMonth(fechaDesde.getMonth() - 9);  
        }
        if (dijit.byId("idFiltroMesesEnviado").attr("value")== '12'){
            fechaDesde.setMonth(fechaDesde.getMonth() - 12);   
        }
        if (dijit.byId("idFiltroMesesEnviado").attr("value")== '-1'){
            dataForm.fechaDesde = "";
            dataForm.fechaHasta = "";
        }else{
            dataForm.fechaDesde = fechaDesde.getDate() + "/" + (fechaDesde.getMonth()+1) + "/" + fechaDesde.getFullYear();
            dataForm.fechaHasta = fechaActual.getDate() + "/" + (fechaActual.getMonth()+1) + "/" + fechaActual.getFullYear(); 
        }
        service.filtrarBandeja(tipoGrid, dataForm).addCallback(function(objJSON) {

            if (dijit.byId("gridInbox")) {
                dijit.byId("gridInbox").destroy();
            }
                                     
            var grid = new dojox.grid.DataGrid({
                id               : "gridInbox",
                jsId             : "gridInbox",
                canSort          : noSort,
                columnReordering : true,
                               headerMenu       : dijit.byId("gridMenu"),
                               onRowClick       : viewDetail,
                rowsPerPage      : 50,
                               store            : new dojo.data.ItemFileWriteStore({data : objJSON}),
                structure        : objJSON.structure
            }, document.createElement("div"));

            grid.setQuery(
		{
		    tipodocumento: dijit.byId("idFiltroTipoDocumentoEnviado").attr("value")== '-1'? "*" :dijit.byId("idFiltroTipoDocumentoEnviado").attr("value"),
                    idDestinatario: dijit.byId("idFiltroUsuarioEnviado").attr("value")== '-1'? "*" :dijit.byId("idFiltroUsuarioEnviado").attr("value")
		},
		{
                    ignoreCase: true
	    });

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
            }

            dojo.byId("contentPaneGrid").appendChild(grid.domNode);
            grid.startup();
                                    
            var dato = dijit.byId("borderContainerGeneral").attr("title");
            var posicion = dato.indexOf("[");
            var contador = 0;
            grid.store.fetch({query : {
                    tipodocumento: dijit.byId("idFiltroTipoDocumentoEnviado").attr("value")== '-1'? "*" :dijit.byId("idFiltroTipoDocumentoEnviado").attr("value"),
                    idDestinatario: dijit.byId("idFiltroUsuarioEnviado").attr("value")== '-1'? "*" :dijit.byId("idFiltroUsuarioEnviado").attr("value")},
                onItem : function (item ) {
                    contador ++;
                }
            });

            if (posicion!=-1){
                dato = dato.substring(0, posicion);    
            }

            var dato_ = "";
            dato_ = " [" + contador + " documento(s)]"; 
            dijit.byId("borderContainerGeneral").setTitle(dato + dato_);
        });
      },
      store   : new dojo.data.ItemFileReadStore({url: "autocompletarMesesEnviado.action"}),
      style   : "float:right;width:150px;"
   }, "idFiltroMesesEnviado");   
    
    new dijit.form.FilteringSelect({
      id             : "idFiltroUsuarioAtendido",
      jsId           : "idFiltroUsuarioAtendido",
      name           : "idFiltroUsuarioAtendido",
      searchAttr     : "label",
     // queryExpr      : "*${0}*",
      autoComplete   : false, 
      hasDownArrow   : true,
      highlightMatch : "all",
      onChange       : function() {  
              if (dijit.byId("gridInbox")) {
                if (dijit.byId("idFiltroUsuarioAtendido").attr("value")== '-1'){
                     dijit.byId("gridInbox").setQuery(
			{
		   	  idPropietario: "*" 
			},
			{
			  ignoreCase: true
		        });
                        
                        var dato_ = "";
                         var dato = dijit.byId("borderContainerGeneral").attr("title");
                         var posicion = dato.indexOf("[");
                         var grid = dijit.byId("gridInbox");
                         var contador = 0;
                         grid.store.fetch({query : {idPropietario : "*"},
                           onItem : function (item ) {
                              contador ++;
                           }
                         });
                         
                         if (posicion!=-1){
                            dato = dato.substring(0, posicion);    
                         }

                         dato_ = " [" + contador + " documento(s)]"; 
                         dijit.byId("borderContainerGeneral").setTitle(dato + dato_);
		}else{
		      dijit.byId("gridInbox").setQuery(
			{
			   idPropietario: this.attr("value") 
			},

			{
			  ignoreCase: true
			 });
                         
                         var dato_ = "";
                         var dato = dijit.byId("borderContainerGeneral").attr("title");
                         var posicion = dato.indexOf("[");
                         var grid = dijit.byId("gridInbox");
                         var contador = 0;
                         grid.store.fetch({query : {idPropietario : this.attr("value")},
                           onItem : function (item ) {
                              contador ++;
                           }
                         });
                         
                         if (posicion!=-1){
                            dato = dato.substring(0, posicion);    
                         }

                         dato_ = " [" + contador + " documento(s)]"; 
                         dijit.byId("borderContainerGeneral").setTitle(dato + dato_);
                }		 
              }
             this.focus(); 
             dijit.byId("contentPaneDetail").attr("content", "");
	 },
      store   : new dojo.data.ItemFileReadStore({url: "autocompletarUsuariosxUnidad.action?iWithoutStor=1"}),
      style   : "float:right;width:250px;"
   }, "idFiltroUsuarioAtendido");
    
    
    
     new dijit.form.FilteringSelect({
      id             : "idFiltroTipoDocumento",
      jsId           : "idFiltroTipoDocumento",
      name           : "idFiltroTipoDocumento",
      searchAttr     : "label",
      queryExpr      : "*${0}*",
      autoComplete   : false, 
      hasDownArrow   : true,
      highlightMatch : "all",
      onChange       : function() {  
              if (dijit.byId("gridInbox")) {
                if (dijit.byId("idFiltroTipoDocumento").attr("value")== '-1'){
                     dijit.byId("gridInbox").setQuery(
			{
		   	  tipodocumento: "*" ,
                          externo: dijit.byId("idFiltroTipo").attr("value")== '-1'? "*" :dijit.byId("idFiltroTipo").attr("value")  
			},
			{
			  ignoreCase: true
		        });
                        
                         var dato_ = "";
                         var dato = dijit.byId("borderContainerGeneral").attr("title");
                         var posicion = dato.indexOf("[");
                         var grid = dijit.byId("gridInbox");
                         var contador = 0;
                         grid.store.fetch({query : {tipodocumento : "*"},
                           onItem : function (item ) {
                              contador ++;
                           }
                         });
                         
                         if (posicion!=-1){
                            dato = dato.substring(0, posicion);    
                         }

                         dato_ = " [" + contador + " documento(s)]"; 
                         dijit.byId("borderContainerGeneral").setTitle(dato + dato_);
                         
		}else{
		      dijit.byId("gridInbox").setQuery(
			{
			   tipodocumento: this.attr("value") ,
                           externo: dijit.byId("idFiltroTipo").attr("value")== '-1'? "*" :dijit.byId("idFiltroTipo").attr("value")  
			},

			{
			  ignoreCase: true
			 });
                         
                         var dato_ = "";
                         var dato = dijit.byId("borderContainerGeneral").attr("title");
                         var posicion = dato.indexOf("[");
                         var grid = dijit.byId("gridInbox");
                         var contador = 0;
                         grid.store.fetch({query : {tipodocumento :  this.attr("value")  },
                           onItem : function (item ) {
                              contador ++;
                           }
                         });
                         
                         if (posicion!=-1){
                            dato = dato.substring(0, posicion);    
                         }

                         dato_ = " [" + contador + " documento(s)]"; 
                         dijit.byId("borderContainerGeneral").setTitle(dato + dato_);       
                }	
              }
             this.focus(); 
             dijit.byId("contentPaneDetail").attr("content", "");
	 },
      store          : new dojo.data.ItemFileReadStore({url: "autocompletarAllTipoDocumentoFiltro.action?iWithoutStor=1"}),
      style   : "float:right;width:220px;"
   }, "idFiltroTipoDocumento");
   
    
      new dijit.form.FilteringSelect({
      id             : "idFiltroTipo",
      jsId           : "idFiltroTipo",
      name           : "idFiltroTipo",
      searchAttr     : "label",
      queryExpr      : "*${0}*",
      autoComplete   : false, 
      hasDownArrow   : true,
      highlightMatch : "all",
       onChange       : function() {  	        
              if (dijit.byId("gridInbox")) {
                  
                if (dijit.byId("idFiltroTipo").attr("value")== '-1'){
                     dijit.byId("gridInbox").setQuery(
			{
		   	  externo: "*",
                          tipodocumento: dijit.byId("idFiltroTipoDocumento").attr("value")== '-1'? "*" :dijit.byId("idFiltroTipoDocumento").attr("value")  
			},
			{
			  ignoreCase: true
		        });
                        
                        var dato_ = "";
                         var dato = dijit.byId("borderContainerGeneral").attr("title");
                         var posicion = dato.indexOf("[");
                         var grid = dijit.byId("gridInbox");
                         var contador = 0;
                         grid.store.fetch({query : {externo : "*" , tipodocumento: dijit.byId("idFiltroTipoDocumento").attr("value")== '-1'? "*" :dijit.byId("idFiltroTipoDocumento").attr("value")  },
                           onItem : function (item ) {
                              contador ++;
                           }
                         });
                         
                         if (posicion!=-1){
                            dato = dato.substring(0, posicion);    
                         }

                         dato_ = " [" + contador + " documento(s)]"; 
                         dijit.byId("borderContainerGeneral").setTitle(dato + dato_);
		}else{
                        dijit.byId("gridInbox").setQuery(
			{
			   externo:  this.attr("value"),
                           tipodocumento: dijit.byId("idFiltroTipoDocumento").attr("value")== '-1'? "*" :dijit.byId("idFiltroTipoDocumento").attr("value") 
			},
                        
			{
			  ignoreCase: true
			 });
                         
                         var dato_ = "";
                         var dato = dijit.byId("borderContainerGeneral").attr("title");
                         var posicion = dato.indexOf("[");
                         var grid = dijit.byId("gridInbox");
                         var contador = 0;
                         grid.store.fetch({query : {externo :  this.attr("value") , tipodocumento: dijit.byId("idFiltroTipoDocumento").attr("value")== '-1'? "*" :dijit.byId("idFiltroTipoDocumento").attr("value")},
                           onItem : function (item ) {
                              contador ++;
                           }
                         });
                         
                         if (posicion!=-1){
                            dato = dato.substring(0, posicion);    
                         }

                         dato_ = " [" + contador + " documento(s)]"; 
                         dijit.byId("borderContainerGeneral").setTitle(dato + dato_);
                }		 
              }
            
             this.focus(); 
             dijit.byId("contentPaneDetail").attr("content", "");
	 },
      store       : new dojo.data.ItemFileReadStore({data: {
                           identifier: 'value',
                           items:[
                                    {value:'-1', label:'Todos'}, 
                                    {value:'1', label:'Externo'},
                                    {value:'0', label:'Interno'}
                                 ]
                           }}),
      style   : "float:right;width:68px;"
   }, "idFiltroTipo");
   
   
   
   new dijit.form.FilteringSelect({
      id             : "idFiltroEstadoRecepcionVirtual",
      jsId           : "idFiltroEstadoRecepcionVirtual",
      name           : "idFiltroEstadoRecepcionVirtual",
      searchAttr     : "label",
      queryExpr      : "*${0}*",
      //value          : "P" ,
      autoComplete   : false, 
      hasDownArrow   : true,
      highlightMatch : "all",
      onChange       : function() {  
              if (dijit.byId("gridInbox")) {
                     dijit.byId("gridInbox").setQuery(
			{
		   	  idEstado: this.attr("value") ,
                          cuo: "*" + dijit.byId("idFiltrarCUORecepcion").attr("value") + "*"
			},
			{
			  ignoreCase: true
		        });
                        
                        var dato_ = "";
                        var dato = dijit.byId("borderContainerGeneral").attr("title");
                        var posicion = dato.indexOf("[");
                        var grid = dijit.byId("gridInbox");
                        var contador = 0;
                        grid.store.fetch({query : { idEstado: this.attr("value"), cuo: "*" + dijit.byId("idFiltrarCUORecepcion").attr("value") + "*"},
                           onItem : function (item ) {
                              contador ++;
                           }
                        });
                         
                        if (posicion!=-1){
                            dato = dato.substring(0, posicion);    
                        }

                        dato_ = " [" + contador + " documento(s)]"; 
                        dijit.byId("borderContainerGeneral").setTitle(dato + dato_);			 
              }
            
             this.focus(); 
             dijit.byId("contentPaneDetail").attr("content", "");
	 },
      store       : new dojo.data.ItemFileReadStore({data: {
                           identifier: 'value',
                           items:[
                                    {value:'P', label:'Pendiente'}, 
                                    {value:'R', label:'Recepcionado'},
                                    {value:'O', label:'Observado'} //,
                                   // {value:'S', label:'Subsanado'}   
                                 ]
                           }}),
      style   : "float:right;width:160px;"
   }, "idFiltroEstadoRecepcionVirtual");
   
   
   new dijit.form.FilteringSelect({
      id             : "idFiltroEstadoDespachoVirtual",
      jsId           : "idFiltroEstadoDespachoVirtual",
      name           : "idFiltroEstadoDespachoVirtual",
      searchAttr     : "label",
      queryExpr      : "*${0}*",
      //value          : "P" ,
      autoComplete   : false, 
      hasDownArrow   : true,
      highlightMatch : "all",
       onChange       : function() {  
              if (dijit.byId("gridInbox")) {
                     dijit.byId("gridInbox").setQuery(
			{
		   	  idEstado: this.attr("value") ,
                           cuo: "*" + dijit.byId("idFiltrarCUODespacho").attr("value") + "*",
			},
			{
			  ignoreCase: true
		        });
                        
                        var dato_ = "";
                        var dato = dijit.byId("borderContainerGeneral").attr("title");
                        var posicion = dato.indexOf("[");
                        var grid = dijit.byId("gridInbox");
                        var contador = 0;
                        grid.store.fetch({query : { idEstado: this.attr("value"), cuo: "*" + dijit.byId("idFiltrarCUODespacho").attr("value") + "*" },
                           onItem : function (item ) {
                              contador ++;
                           }
                        });
                         
                        if (posicion!=-1){
                            dato = dato.substring(0, posicion);    
                        }

                        dato_ = " [" + contador + " documento(s)]"; 
                        dijit.byId("borderContainerGeneral").setTitle(dato + dato_);			 
              }
             this.focus(); 
             dijit.byId("contentPaneDetail").attr("content", "");
           
	 },
      store       : new dojo.data.ItemFileReadStore({data: {
                           identifier: 'value',
                           items:[
                                    {value:'P', label:'Pendiente'},
                                    {value:'E', label:'Enviado'},
                                    {value:'R', label:'Recepcionado'},
                                    {value:'O', label:'Observado'},
                                    {value:'S', label:'Subsanado'}
                                 ]
                           }}),
      style   : "float:right;width:160px;"
   }, "idFiltroEstadoDespachoVirtual");
   
   
   
      new dijit.form.FilteringSelect({
      id             : "idFiltroMesEnviados",
      jsId           : "idFiltroMesEnviados",
      name           : "idFiltroMesEnviados",
      searchAttr     : "label",
      queryExpr      : "*${0}*",
      autoComplete   : false, 
      hasDownArrow   : true,
      highlightMatch : "all",
       onChange       : function() {  	        
              if (dijit.byId("gridInbox")) {
                 
              }
            
             this.focus(); 
	 },
      store       : new dojo.data.ItemFileReadStore({data: {
                           identifier: 'value',
                           items:[
                                    {value:'1', label:'1 Mes'}, 
                                    {value:'3', label:'3 Meses'},
                                    {value:'6', label:'6 Meses'},
                                    {value:'-1', label:'Todos'}
                                 ]
                           }}),
      style   : "float:right;width:68px;"
   }, "idFiltroMesEnviados");
   
   
   
    new dijit.form.TextBox({
        id      : "txtFiltrarBandeja",
        jsId    : "txtFiltrarBandeja",
        onKeyUp : function() {// despues del evento que sueltas la tecla
            
            if (dijit.byId("gridInbox")) {
                dijit.byId("gridInbox").setQuery(
                {
                    asunto: "*" + this.attr("value") + "*"
                    
                },

                {
                    ignoreCase: true
                });
            }
            this.focus();//que enfoque a si mismo
        //
        },
        style   : "float:right;"
    });

    new dijit.form.TextBox({
        id      : "idFiltrarAsuntoLegajo",
        jsId    : "idFiltrarAsuntoLegajo",
        onKeyUp : function() {
            
            if (dijit.byId("gridInbox")) {
                dijit.byId("gridInbox").setQuery(
                {
                    asuntolegajo: "*" + this.attr("value") + "*",
                    idMetodo: dijit.byId("idFiltroMetodo").attr("value")== '-1'? "*" :dijit.byId("idFiltroMetodo").attr("value"), 
                    idProcedimiento: dijit.byId("idFiltroProcedimiento").attr("value")== '-1'? "*" :dijit.byId("idFiltroProcedimiento").attr("value") 
                },

                {
                    ignoreCase: true
                });
                
                
                var dato_ = "";
                var dato = dijit.byId("borderContainerGeneral").attr("title");
                var posicion = dato.indexOf("[");
                var grid = dijit.byId("gridInbox");
                var contador = 0;
                grid.store.fetch({query : {asuntolegajo: "*" + this.attr("value") + "*", idMetodo: dijit.byId("idFiltroMetodo").attr("value")== '-1'? "*" :dijit.byId("idFiltroMetodo").attr("value"), idProcedimiento: dijit.byId("idFiltroProcedimiento").attr("value")== '-1'? "*" :dijit.byId("idFiltroProcedimiento").attr("value")  },  queryOptions:{ignoreCase:true},
                  onItem : function (item ) {
                     contador ++;
                  }
                });

                if (posicion!=-1){
                   dato = dato.substring(0, posicion);    
                }

                dato_ = " [" + contador + " documento(s)]"; 
                dijit.byId("borderContainerGeneral").setTitle(dato + dato_);
            }
            this.focus();//que enfoque a si mismo
        //
        },
        //style   : "float:right;"
        style   : "float:right;width:163px;"
    });
    
    
    new dijit.form.TextBox({
        id      : "idFiltrarCUORecepcion",
        jsId    : "idFiltrarCUORecepcion",
        onKeyUp : function() {
            if (dijit.byId("gridInbox")) {
                dijit.byId("gridInbox").setQuery(
                {
                    cuo: "*" + this.attr("value") + "*",
                    idEstado: dijit.byId("idFiltroEstadoRecepcionVirtual").attr("value") 
                },

                {
                    ignoreCase: true
                });
                
                var dato_ = "";
                var dato = dijit.byId("borderContainerGeneral").attr("title");
                var posicion = dato.indexOf("[");
                var grid = dijit.byId("gridInbox");
                var contador = 0;
                grid.store.fetch({query : { cuo: "*" + this.attr("value") + "*", idEstado: dijit.byId("idFiltroEstadoRecepcionVirtual").attr("value")},
                   onItem : function (item ) {
                      contador ++;
                   }
                });

                if (posicion!=-1){
                    dato = dato.substring(0, posicion);    
                }

                dato_ = " [" + contador + " documento(s)]"; 
                dijit.byId("borderContainerGeneral").setTitle(dato + dato_);
            }
            this.focus();//que enfoque a si mismo
        //
        },
        style   : "float:right;width:130px;"
    });

    
    new dijit.form.TextBox({
        id      : "idFiltrarCUODespacho",
        jsId    : "idFiltrarCUODespacho",
        onKeyUp : function() {
            
            if (dijit.byId("gridInbox")) {
                dijit.byId("gridInbox").setQuery(
                {
                    cuo: "*" + this.attr("value") + "*",
                    idEstado: dijit.byId("idFiltroEstadoDespachoVirtual").attr("value")
                },

                {
                    ignoreCase: true
                });
                
                var dato_ = "";
                var dato = dijit.byId("borderContainerGeneral").attr("title");
                var posicion = dato.indexOf("[");
                var grid = dijit.byId("gridInbox");
                var contador = 0;
                grid.store.fetch({query : { cuo: "*" + this.attr("value") + "*", idEstado: dijit.byId("idFiltroEstadoDespachoVirtual").attr("value") },
                   onItem : function (item ) {
                      contador ++;
                   }
                });

                if (posicion!=-1){
                    dato = dato.substring(0, posicion);    
                }

                dato_ = " [" + contador + " documento(s)]"; 
                dijit.byId("borderContainerGeneral").setTitle(dato + dato_);
            }
            this.focus();//que enfoque a si mismo
        //
        },
        style   : "float:right;width:130px;"
    });

    new dijit.form.TextBox({
        id      : "txtFiltrarTipo",
        jsId    : "txtFiltrarTipo",
        onKeyUp : function() { alert("dddd");// despues del evento que sueltas la tecla
            if (dijit.byId("gridInbox")) {
               /* if (dijit.byId("idConsultaFiltro").attr("value")== 'NC'){
                    dijit.byId("gridInbox").setQuery(
                    {
                        expediente : "*" + this.attr("value") + "*"
                    },
                    { 
                        ignoreCase: true
                    });
		}	
				
		if (dijit.byId("idConsultaFiltro").attr("value")== 'AD'){
                    dijit.byId("gridInbox").setQuery(
                    {
                        asunto : "*" + this.attr("value") + "*"
                    },
                    {
                        ignoreCase: true
                    });
		}*/	
            }
            this.focus();//que enfoque a si mismo
        },
        style   : "float:right;"
    });


    new dijit.form.Button({
        id: "btnDerivarMasivamente",
        jsId: "btnDerivarMasivamente",
        iconClass: "dijitEditorIcon dijitEditorIconIndent",
        label: "Transferir",
        onClick: derivarMasivamente,
        showLabel: true

    });
    
    new dijit.form.Button({
        id: "btnDerivarDocMasivamente",
        jsId: "btnDerivarDocMasivamente",
        iconClass: "dijitEditorIcon dijitEditorIconIndent",
        label: "Derivar Masivamente",
        onClick: derivarMasivamenteDoc,
        showLabel: true

    });

   new dijit.form.Button({
        id: "btnCambiarNoLeido",
        jsId: "btnCambiarNoLeido",
        iconClass: "siged16 iconoNotificar",
        label: "No Leído",
        onClick: cambiarNoLeido,
        showLabel: true
    });


    new dijit.form.Button({
        id: "btnReabrirMasivamente",
        jsId: "btnReabrirMasivamente",
        iconClass: "dijitEditorIcon dijitEditorIconInsertTable",
        label: "Reabir Masivamente",
        onClick: reabrirMasivamente,
        showLabel: true
    });

    new dijit.form.Button({
        id: "btnCambiarAgrupacion",
        jsId: "btnCambiarAgrupacion",
        iconClass: "siged16 iconoAgrupacion",
        label: "Documentos Agrupados",
        onClick: cambiarAgrupacionBandeja,
        showLabel: true
    });

    new dijit.form.Button({
        id: "btnFiltrarBandeja",
        jsId: "btnFiltrarBandeja",
        iconClass: "siged16 sigedSearch16",
        //iconClass: "dijitEditorIcon dijitEditorIconIndent",
        label: "Filtrar",
        onClick: mostrarFiltrosBandeja,
        showLabel: true
    });
    
    new dijit.form.Button({
        id: "btnFirmar",
        jsId: "btnFirmar",
        iconClass: "siged16 iconoHojaRuta",
        label: "Firmar",
        onClick: firmarDocumentos,
        showLabel: true
    });
    
    new dijit.form.Button({
        id: "btnVisar",
        jsId: "btnVisar",
        iconClass: "siged16 iconoVisar",
        label: "Visar",
        onClick: visarDocumentos,
        showLabel: true
    });
    
    new dijit.form.Button({
        id: "btnRefrescar",
        jsId: "btnRefrescar",
        iconClass: "siged16 sigedRefresh16",
        //iconClass: "dijitEditorIcon dijitEditorIconIndent",
        label: "Refrescar",
        onClick: refrescarPagina,
        showLabel: true
    });


    new dijit.form.Button({
        id: "btnEliminar",
        jsId: "btnEliminar",
        iconClass: "dijitEditorIcon dijitEditorIconDelete",
        label: "Eliminar",
        onClick: eliminarEnviados,
        showLabel: true
    });

    new dijit.form.Button({
        id: "btnEliminarN",
        jsId: "btnEliminarN",
        iconClass: "dijitEditorIcon dijitEditorIconDelete",
        label: "Eliminar",
        onClick: eliminarNotificaciones,
        showLabel: true
    });
    
    new dijit.form.Button({
        id: "btnExportarRecibidos",
        jsId: "btnExportarRecibidos",
        iconClass: "siged16 sigedExcel16",
        label: "Exportar",
        onClick: activarExportarRecibidos,
        showLabel: true
    });
    
    new dijit.form.Button({
        id: "btnNavegadorDocumentos",
        jsId: "btnNavegadorDocumentos",
        iconClass: "siged16 sigedPDF16",
        label: "Navegador",
        onClick: verNavegador,
        showLabel: true
    });
    
    new dijit.form.Button({
        id: "btnExportarPendientes",
        jsId: "btnExportarPendientes",
        iconClass: "siged16 sigedExcel16",
        //iconClass: "dijitEditorIcon dijitEditorIconIndent",
        label: "Exportar",
        onClick: activarExportarPendientes,
        showLabel: true
    });
    
    new dijit.form.Button({
        id: "btnExportarAtendidos",
        jsId: "btnExportarAtendidos",
        iconClass: "siged16 sigedExcel16",
        label: "Exportar",
        onClick: activarExportarAtendidos,
        showLabel: true
    });
    
    new dijit.form.Button({
       id: "btnExportarMisDocumentos",
       jsId: "btnExportarMisDocumentos",
       iconClass: "siged16 sigedExcel16",
       label: "Exportar",
       onClick: activarExportarMisDocumentos,
       showLabel: true
    });
    
    new dijit.form.Button({
       id: "btnExportarEnviados",
       jsId: "btnExportarEnviados",
       iconClass: "siged16 sigedExcel16",
       label: "Exportar",
       onClick: activarExportarEnviados,
       showLabel: true
    });

    new dijit.form.Button({
        id: "lblFiltrarMesesEnviado",
        jsId: "lblFiltrarMesesEnviado",
        label: "Últimos",
        showLabel: true,
        disabled: true,
        style   : "float:right;"
    });

/*
    new dijit.form.Button({
        id: "btnEliminarInformativo",
        jsId: "btnEliminarInformativo",
        iconClass: "dijitEditorIcon dijitEditorIconDelete",
        label: "Eliminar",
        onClick: eliminarInformativo,
        showLabel: true
    });*/

/*
    new dijit.form.Button({
        id: "btnImprimirBandejaPendientes",
        jsId: "btnImprimirBandejaPendientes",
        iconClass: "siged16 iconoImprimir",
        label: "Imprimir",
        onClick: imprimirDocumentosRecibidos,
        showLabel: true
    });
*/

/*
    new dijit.form.Button({
        id: "btnImprimirBandejaInformativos",
        jsId: "btnImprimirBandejaInformativos",
        iconClass: "siged16 iconoImprimir",
        label: "Imprimir",
        onClick: imprimirDocumentosInformativos,
        showLabel: true
    });
*/


};

var changeBodyClass = function(clase) {
    try {
        if (document.body.className != clase) {
            dojo.xhrPost({
                url : "updateTemaUsuario.action",
                content : {
                    sTema : clase
                },
                mimetype : "text/html",
                error : function(response) {
                    console.error("Error en (changeBodyClass) [%s]", response);
                }
            });

            document.body.className = clase;
    
        }
    }catch (error) {
        
        console.debug(error.name+":"+error.message);
    }
};

var resetDetail = function() {
    if (typeof handlerQAS != "undefined") {
        console.debug("handlerQAS [%s]", handlerQAS);
        dojo.disconnect(handlerQAS);
    }
    if (dijit.byId("contentPaneDetail")) {
        dijit.byId("contentPaneDetail").attr("content", "");
    }
    //NO DEBERIA DESTRUIRSE, SOLO OCULTARLO Y CAMBIAR DE TAB... MEJORAR ESTO!!!
    if (dijit.byId("contentPaneDocumentosAdicionales")) {
        dijit.byId("tabContainerDetail").removeChild(dijit.byId("contentPaneDocumentosAdicionales"));
        dijit.byId("contentPaneDocumentosAdicionales").destroy();
    }
    
    if (dijit.byId("contentPaneDocumentosLegajo")) {
        dijit.byId("tabContainerDetail").removeChild(dijit.byId("contentPaneDocumentosLegajo"));
        dijit.byId("contentPaneDocumentosLegajo").destroy();
    }
    
    if (dijit.byId("contentPaneDocumentosReferenciales")) {
        dijit.byId("tabContainerDetail").removeChild(dijit.byId("contentPaneDocumentosReferenciales"));
        dijit.byId("contentPaneDocumentosReferenciales").destroy();
    }
    
    
    if (dijit.byId("contentPaneDocumentosExternos")) {
        dijit.byId("tabContainerDetail").removeChild(dijit.byId("contentPaneDocumentosExternos"));
        dijit.byId("contentPaneDocumentosExternos").destroy();
    }
    
   /* if (dijit.byId("contentPaneDocumentosRespuestas")) {
        dijit.byId("tabContainerDetail").removeChild(dijit.byId("contentPaneDocumentosRespuestas"));
        dijit.byId("contentPaneDocumentosRespuestas").destroy();
    }*/

    if (dijit.byId("contentPaneDatosDocumento")) {
        if(dijit.byId("tabContainerDetail"))
            dijit.byId("tabContainerDetail").removeChild(dijit.byId("contentPaneDatosDocumento"));
        dijit.byId("contentPaneDatosDocumento").destroyRecursive();
    //dijit.byId("contentPaneDatosDocumento").destroy();

    }
    
  
    if (dijit.byId("contentPaneDatosEnvio")) {
        dijit.byId("tabContainerDetail").removeChild(dijit.byId("contentPaneDatosEnvio"));
        dijit.byId("contentPaneDatosEnvio").destroyRecursive();
    //dijit.byId("contentPaneDatosEnvio").destroy();

    }
    
    
    if (dijit.byId("contentPaneDatosCargo")) {
        dijit.byId("tabContainerDetail").removeChild(dijit.byId("contentPaneDatosCargo"));
        dijit.byId("contentPaneDatosCargo").destroyRecursive();
    //dijit.byId("contentPaneDatosCargo").destroy();

    }

    //AQUI NO HAY OTRA, ESTA METIDO EN UN JSP. SOLUCION: REHACER PRUEBAARCHDOCUMENTOS.JSP
    if (dijit.byId("divTreeExpediente")) {
        dijit.byId("divTreeExpediente").destroy();
    }
    
};

var gridResized = function() {
    var grd = dijit.byId("gridInbox");
    if(grd!=null){
        grd._resize();
       // grd.update();
    }
};



dojo.addOnLoad(function() {
	console.debug("main.js --> dojo.addOnLoad    inicio del sistema");
    dojo.connect(dijit.byId("dijit_layout__Splitter_0"), "onclick", gridResized);
   
    console.info("Version de DOJO [%s]", dojo.version);

    if (temaUsuario == "") {
        temaUsuario = "soria";
        //changeBodyClass(temaUsuario);
    }

    changeBodyClass(temaUsuario);
    var divContainerMain = dojo.byId("divContainerMain");
    console.debug("Parent ID [%s]", divContainerMain.id);
    var borderContainerMain = new dijit.layout.BorderContainer({
        id: "borderContainerMain",
        style: "width:100%; height:100%;"
    }).placeAt(divContainerMain);

    buildTop(borderContainerMain);
    buildMenu(borderContainerMain);
    buildToolBarInboxButtons();
    
    if(recursoIG==1){
    	buildInboxIG(borderContainerMain);
    }else{
    	buildInbox(borderContainerMain);
    }

    borderContainerMain.startup();
    
    for (var i = 0; i < arrRecursoFirstGrid.length; i++) {
        var item = arrRecursoFirstGrid[i];
        //   alert(arrRecursoFirstGrid.length + "longitud ");
        //    alert(item+ "iten solo ");
        //    alert(item.valor+"VALOR DEL ITEM");
        //    alert(item.grid+"VALOR DEL grid");
        //    alert(item.codigo+"VALOR DE codigo");
        console.debug("main.js Recurso [%s] disponible [%d]", item.codigo, item.valor);

        if (item.valor) {
            console.debug("Se pintara como grid inicial [%d]", item.grid);
            showGridInbox(item.grid);
            //buildTabsFromToolBarTopMain();//wvcarrasco
            break;
        }
    }

    img1 = new Image();
    img1.src = "images/bolitaAmarilla.gif";
    arrImgBolitas.push(img1);
    img2 = new Image();
    img2.src = "images/bolitaVerde.gif";
    arrImgBolitas.push(img2);
    img3 = new Image();
    img3.src = "images/bolitaRoja.gif";
    arrImgBolitas.push(img3);
    
});

var refrescarDetalle = function(){
	dijit.byId("contentPaneDetail").setHref(dijit.byId("contentPaneDetail").attr("href"));
	dijit.byId("contentPaneDocumentosAdicionales").setHref(dijit.byId("contentPaneDocumentosAdicionales").attr("href"));
};


var buildTabsFromToolBarTopMain = function(recurso) {
		console.debug("Pintar nuevo buidTabs") ;
	   try{

	            var newTab1 = new dojox.layout.ContentPane({
	               id : btnData.id,
	               jsId : btnData.id,
	               closable: true,
	               href : btnData.href,
	               iconClass : btnData.iconClass,
	               title: "hola"
	            });
	            dijit.byId("tabContainerInbox").addChild(newTab1);
	            //dijit.byId(btnData.id).setHref(btnData.href);
	         //dijit.byId("tabContainerInbox").selectChild(dijit.byId(btnData.id));
	         dialog.hide() ;
	   }catch(err) {
	      console.debug( "El error: "+err.description ) ;
	   }
	};

//visorGerencial
	var buildInboxTemp = function(parent) {
	    console.debug("(buildInbox) Parent ID [" + parent.id + "]");

	    var tabContainerInbox = new siged.TabContainer({
	        id: "tabContainerInbox",
	        jsId: "tabContainerInbox",
	        region: "center"
	    }).placeAt(parent);

	    var borderContainerGeneral = new dijit.layout.BorderContainer({
	        id: "borderContainerGeneral",
	        jsId: "borderContainerGeneral",
	        title: "Bandeja de General",
	        region:"center",
	        style: "width:100%; overflow:auto;",
	    }).placeAt(tabContainerInbox);


	    var borderContainerInbox = new dijit.layout.BorderContainer({
	        id: "borderContainerInbox",
	        jsId: "borderContainerInbox",
	        style: "width:99%; overflow:auto;",
	        region:"center",
	        title: "Bandeja de Entrada"
	    }).placeAt(borderContainerGeneral);


	   /* var borderContainerVisor = new dijit.layout.BorderContainer({
	        id: "borderContainerVisor",
	        jsId: "borderContainerVisor",
	        title: "Bandeja de Entrada",
	        region:"center",
	        style: "width:49%; overflow:auto;",
	    }).placeAt(borderContainerGeneral);

	    new dojox.layout.ContentPane({
	        id: "contentPaneVisor",
	        href: "pages/layout/visor.jsp",
	        jsId: "contentPaneVisor",
	        style: "width:97%; overflow:auto;",
	        region:"center"
	    }).placeAt(borderContainerVisor);*/

	    new dijit.Toolbar({
	        id: "toolBarInbox",
	        jsId: "toolBarInbox",
	        region: "top",
	        style: "height:20px;"
	    }).placeAt(borderContainerInbox);

	     dijit.byId("toolBarInbox").addChild(dijit.byId("btnDerivarMasivamente"));
	    //dijit.byId("toolBarInbox").addChild(dijit.byId("btnArchivarMasivamente"));
	    dijit.byId("toolBarInbox").addChild(dijit.byId("txtFiltrarBandeja"));
	    dijit.byId("toolBarInbox").addChild(dijit.byId("lblFiltrarBandeja"));
	    //

	    //  dijit.byId("toolBarInbox").addChild(dijit.byId("txtFiltrarMensaje"));
	    // dijit.byId("toolBarInbox").addChild(dijit.byId("lblFiltrarMensaje"));
	    //
	    new dojox.layout.ContentPane({
	        id: "contentPaneGrid",
	        jsId: "contentPaneGrid",
	        style: "width:97%; overflow:auto;",
	        region:"center"
	    }).placeAt(borderContainerInbox);

	    //var cargando=document.getElementById("cargando");
	    //dojo.byId("contentPaneGrid").appendChild(cargando);
	    buildDetail(borderContainerInbox);
	    vistaIG = true;
	};



	var buildInboxIGTemp = function(parent) {
	    console.debug("(buildInbox) Parent ID [" + parent.id + "]");

	     var tabContainerInbox = new siged.TabContainer({
	         id: "tabContainerInbox",
	         jsId: "tabContainerInbox",
	         region: "center"
	     }).placeAt(parent);

	     var borderContainerGeneral = new dijit.layout.BorderContainer({
	         id: "borderContainerGeneral",
	         jsId: "borderContainerGeneral",
	         title: "Bandeja de General",
	         region:"center",
	         style: "width:100%; overflow:auto;",
	     }).placeAt(tabContainerInbox);


	     var borderContainerInbox = new dijit.layout.BorderContainer({
	         id: "borderContainerInbox",
	         jsId: "borderContainerInbox",
	         style: "width:50%; overflow:auto;",
	         region:"left",
	         title: "Bandeja de Entrada "
	     }).placeAt(borderContainerGeneral);


	     var borderContainerVisor = new dijit.layout.BorderContainer({
	         id: "borderContainerVisor",
	         jsId: "borderContainerVisor",
	         title: "Bandeja de Entrada ",
	         region:"center",
	         style: "width:49%; overflow:auto;",
	     }).placeAt(borderContainerGeneral);

	     new dojox.layout.ContentPane({
	         id: "contentPaneVisor",
	         href: "pages/layout/errorVisor.jsp",
	         jsId: "contentPaneVisor",
	         style: "width:97%; overflow:auto;",
	         region:"center"
	     }).placeAt(borderContainerVisor);

	     new dijit.Toolbar({
	         id: "toolBarInbox",
	         jsId: "toolBarInbox",
	         region: "top",
	         style: "height:40px;"
	     }).placeAt(borderContainerInbox);

	     dijit.byId("toolBarInbox").addChild(dijit.byId("btnDerivarMasivamente"));
	     dijit.byId("toolBarInbox").addChild(dijit.byId("btnArchivarMasivamente"));
	     dijit.byId("toolBarInbox").addChild(dijit.byId("txtFiltrarBandeja"));
	     dijit.byId("toolBarInbox").addChild(dijit.byId("lblFiltrarBandeja"));
	     //

	     //  dijit.byId("toolBarInbox").addChild(dijit.byId("txtFiltrarMensaje"));
	     // dijit.byId("toolBarInbox").addChild(dijit.byId("lblFiltrarMensaje"));
	     //
	     new dojox.layout.ContentPane({
	         id: "contentPaneGrid",
	         jsId: "contentPaneGrid",
	         style: "width:97%; overflow:auto;",
	         region:"center"
	     }).placeAt(borderContainerInbox);

	     //var cargando=document.getElementById("cargando");
	     //dojo.byId("contentPaneGrid").appendChild(cargando);

	     buildDetail(borderContainerInbox);




	}


	;