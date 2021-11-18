document.write( "\<script" ); 
document.write( "   src='js/siged/date.js'" ); 
document.write( "\>" ); 
document.write( "\</script\>" ); 

setInterval(formatterAlerta,1000);

var dataStore = null;
var objFechaActual = new Date();
var parametro1 = null;
var parametro2 = null;
var lfechaactual = null ; 
var chkAll = true;

noSort = function(inSortInfo) {
   if (inSortInfo == 1 || inSortInfo == 2 || inSortInfo == 9) {
      dojo.forEach(grdSiged.store._arrayOfAllItems, function(item) {
         grdSiged.store.setValue(item, 'selected', chkAll);
      });

      grdSiged.store.save();
      chkAll = !chkAll;

      return false;
   } else {
      return true;
   }
};

viewDoc = function(e) {
   parent.secondFrame.location.href = 'doViewDoc.action?iIdDoc=' + dijit.byId("grdSiged").getItem(e.rowIndex).id;
   this.edit.rowClick(e);   // <- from default onRowClick method
   this.selection.clickSelectEvent(e);  // <- from default onRowClick method
};

function derivarMasivamente() {
   var opciones = "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes, width=650, height=469, top=20, left=70";
   var parameters = "";
   var pagina = "goDerivarUSER.action?sTipoDerivacion=masivo&sOpcion=nose&";
   dojo.forEach(grdSiged.store._arrayOfAllItems, function(item) {
      var selected = grdSiged.store.getValue(item, "selected");
      var iIdDoc = grdSiged.store.getValue(item, "id");

      if (selected) {
         parameters += "arrIdDoc=" + iIdDoc + "&";
      }
   });

   if (parameters != "") {
      pagina += parameters;
      //console.log("URL Derivacion Masiva [" + pagina + "]");
      window.open(pagina, "Ventana", opciones);
   } else {
      alert("Primero selecccionar documentos");
   }
//window.open("", "frmDerivacionMasiva", opciones);
//window.setTimeout("document.frmDerivacionMasiva.submit();",500);
}

dojo.addOnLoad(function(){
   var service = new dojo.rpc.JsonService("SMDAction.action");
   var defered = service.buildGrid();
   
   defered.addCallback(function(objJSON) {
      objJSON.structure.unshift({
         field: 'selected',
         name: "<input type='checkbox' id='chkHeader' jsid='chkHeader' name='chkHeader' />",
         width: '20px',
         editable: true,
         type: dojox.grid.cells.Bool,
         noresize: true
      });

      grdSiged.setStructure(objJSON.structure);

      for(var i in grdSiged.layout.cells){
    	  //alert(grdSiged.layout.cells[i].field+" "+grdSiged.layout.cells[i].formato);
         if (grdSiged.layout.cells[i].formato == 'formatterDate') {
            grdSiged.layout.cells[i].formatter = formatterDate;
         }
         else if (grdSiged.layout.cells[i].formato == 'formatterLink') {
            grdSiged.layout.cells[i].formatter = formatterLink;
         }
         else if(grdSiged.layout.cells[i].formato=="formatterImg"){
             grdSiged.layout.cells[i].formatter = formatterImg;
         }
      }

      dataStore= new dojo.data.ItemFileWriteStore({
         data: objJSON
      });
      grdSiged.setStore(dataStore);

      if (RECURSO_HISTORICO_ACTIVO == "0") {
         grdSiged.setQuery(eval("({historico: \'N\'})"), null);
      }

      objFechaActual.setTime(parseInt(objJSON.horaservidor)) ; //Date.parse(objJSON.horaservidor);
      lfechaactual = parseInt(objJSON.horaservidor);
      console.log(objJSON.horaservidor);  
      parametro1 = parseFloat(objJSON.parametroalerta1);
      parametro2 = parseFloat(objJSON.parametroalerta2);

      dojo.forEach(grdSiged.store._arrayOfAllItems, function(item) {
         grdSiged.store.setValue(item, 'selected', false);
      });

      dojo.connect(dijit.byId("grdSiged"), "onRowClick", viewDoc);
   });
});

//Por German Enriquez
//almacena el estado de las columnas del grid una vez que el usuario deja la pagina
dojo.addOnUnload(window,function(){
	guardarGrid();
});

function guardarGrid(){
	console.debug("(grid.js) guardarGrid");
	var grid=new Array();
	var gridAux;
	for(var k in grdSiged.layout.cells){
		var index=grdSiged.layout.cells[k].index;
		var campo=grdSiged.layout.cells[k].field;
		var id=grdSiged.layout.cells[k].idGridColumnaPorGrid;
		if(campo=="selected"){
			if(gridAux!=null){
				if(gridAux.field!="selected" && gridAux.position==0){
					gridAux.position=index;
					grid[index-1]=gridAux;
				}
			}
		}
		else{
			var oculto=grdSiged.layout.cells[k].hidden;
			if(oculto==false)
				oculto=0;
			else if(oculto==true)
				oculto=1;
			if (campo!=null && campo!="") {
				//alert("Indice:"+index+" Campo:"+campo+" Ancho:"+grdSiged.layout.cells[k].unitWidth+" Oculto: "+oculto);
				if(index==0){
					gridAux={
							idGridXGridColumna: id,
							field: campo,
							width: grdSiged.layout.cells[k].unitWidth,
							hidden: oculto,
							position: index
					};
				}
				else{
					grid[index-1]={
							idGridXGridColumna: id,
							field:		campo,
							width:		grdSiged.layout.cells[k].unitWidth,
							hidden:		oculto,
							position:	index
					};
				}
			}			
		}
	}
	var service=new dojo.rpc.JsonService("SMDAction.action");
    	service.guardarGridUsuario(grid);
}

function formatterDate(fecha) {
	// var datToday = objFechaActual;
  // var datMonth = datToday.getMonth();

  // datMonth++;
   //console.log("Hora Servidor [" + datToday + "]");

  // var datCurrent = datToday.getFullYear() + "-" + (datMonth < 10 ? "0" + datMonth : datMonth) + "-" + datToday.getDate();
	if(fecha!=null){
		var datFecha = fecha.substring(0,10);
		if (objFechaActual.toString('yyyy-MM-dd') == datFecha) {
			return fecha.substring(11,16);
		} else {
			// Modificado por Germán Enríquez para que se vean las horas con la fecha limite
			return fecha.substring(8,10) + "/" + fecha.substring(5,7) + "/" + fecha.substring(0,4)+" "+fecha.substring(11,16);
		}
	}
	return "";
}

function formatterLink(url) {
   alert("x=" + url);
   //console.log("URL Alfresco [" + url + "]");

   if (url != null && url != "") {
      //return "<a alt='Ver Documento' href='" + url + "' target='_blank'><img src='images/clic.gif' /></a>";
      return "<a alt='Ver Documento' onclick='verArchivo(\"" + url + "\");'><img src='images/clic.gif' /></a>";
   } else {
      return null;
   }
}

function formatterImg(url){
	//alert(url);
	return "<img src=\""+url+"\" alt=\"\" />";
}

function verArchivo(url) {
   alert("Hola mundo.."); 
   var service = new dojo.rpc.JsonService("SMDAction.action");
   var defered = service.verArchivoAlfresco(url);
   defered.addCallback(function(url) {
      console.log("URL Alfresco [" + url + "]");
      window.open(url);
   });
}

function formatterAlerta() {
   if(dataStore){
      //aqui a�adir codigo para que esto solo se ejecute cuando tengamos la columna tipoalerta definida
      dataStore.fetch({
         query: {
            id:"*"
         },
         onComplete: onCompleteFetch
      });
   }
   objFechaActual.addSeconds(1);
//grdSiged.setStore(dataStore); 
};



var onCompleteFetch = function(items, request) {
   //Define the save callbacks to use
   var onSave = function(){
   // alert("Save done.");
   };
   var onSaveError = function(error) {
      alert("Error occurred: " + error);
   };

   // Process the items and update attribute 'foo'
   for (var i = 0; i < items.length; i++){
      var item = items[i];
      var ran_unrounded=Math.random()*3;
      var ran_number=retornarTipoAlerta(dataStore.getValue(item,'lfecharecepcion'),dataStore.getValue(item,'lfechalimite'));
    
      var giff = dataStore.getValue(item, "tipoalerta") ;

      if(ran_number==1){  
         dataStore.setValue(item, "tipoalerta", "images/bolitaAmarilla.gif");
      }
      if(ran_number==2){
         dataStore.setValue(item, "tipoalerta", "images/bolitaVerde.gif");
      }
      if(ran_number==0){ 
         dataStore.setValue(item, "tipoalerta", "images/bolitaRoja.gif");
      }
   }
    
   // If the store has modified items (it should), call save with the handlers above.
   if (dataStore.isDirty()){
      dataStore.save({
         onComplete: onSave,
         onError: onSaveError
      });
   }
};


//Define a fetch error handler, just in case.
var onFetchError = function(error, request){
   alert("Fetch failed.  " + error);
};

function retornarTipoAlerta(fecharegistro,fechafinal){

	if(fechafinal!=null){
	objFechaRegistro= new Date();
   objFechaFinal=new Date();
	
	
   //Creando el objeto Fecha de Registro
   /*
   objFechaRegistro.setFullYear(fecharegistro.substring(0,4), parseInt(fecharegistro.substring(5,7))-1, fecharegistro.substring(8,10));
   objFechaRegistro.setHours(fecharegistro.substring(11,13), fecharegistro.substring(14,16), fecharegistro.substring(17,19));
	alert("fecharegistro:"+fecharegistro.substring(0,4)+'/'+(parseInt(fecharegistro.substring(5,7))-1)+'/'+fecharegistro.substring(8,10));
	 */
	
	
   //Creando el objeto Fecha Final
   /*
   objFechaFinal.setFullYear(fechafinal.substring(0,4), parseInt(fechafinal.substring(5,7))-1, fechafinal.substring(8,10));
   objFechaFinal.setHours(fechafinal.substring(11,13), fechafinal.substring(14,16), fechafinal.substring(17,19));
   alert("fechafinal:"+fechafinal);
   alert("fecharegistro:"+fechafinal.substring(0,4)+'/'+(parseInt(fechafinal.substring(5,7))-1)+'/'+fechafinal.substring(8,10));
   */
   //indice=parseFloat((objFechaActual.getTime()-objFechaRegistro.getTime())/(objFechaFinal.getTime()-objFechaRegistro.getTime()));
   indice=parseFloat((lfechaactual-fecharegistro)/(fechafinal-fecharegistro));
   // alert('indice :'+indice);
   // alert('lfechaactual:'+lfechaactual+' fecharegistro:'+fecharegistro+' fechafinal:'+fechafinal) ;  
            
   /* alert(' fa:'+objFechaActual.getFullYear()+'/'+objFechaActual.getMonth()+'/'+objFechaActual.getDay()+'\n') ;
   alert(' fr:'+objFechaRegistro.getFullYear()+'/'+objFechaRegistro.getMonth()+'/'+objFechaRegistro.getDay()+'\n') ;
   alert(' ff:'+objFechaFinal.getFullYear()+'/'+objFechaFinal.getMonth()+'/'+objFechaFinal.getDay()+'\n') ;
     */
   //*alert('indice:'+indice+' \n parametro1:'+parametro1+' \n parametro2:'+parametro2+' \n \n objFechaActual.getTime():'+objFechaActual.getTime()+' objFechaRegistro.getTime():'+objFechaRegistro.getTime()+'  objFechaFinal.getTime():'+objFechaFinal.getTime()+'\n'+ '  resta1: '+(objFechaActual.getTime()-objFechaRegistro.getTime())+'  \n   resta2:'+(objFechaFinal.getTime()-objFechaRegistro.getTime())); 
   if(indice<parametro1)
      return 2;//bola verde   
   if(indice<=parametro2 && indice>=parametro1){
      return 1;//bola amarilla
   }
   if(indice>parametro2)
      return 0;//bola roja
	}
   return 0;
}