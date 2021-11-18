	/**REN La mayor parte de este medoto fue comentada para eliminar la necesidad de subir un archivo cuando se crea un documento*/
	function submitForm() {
		var	enviar =  true;
		var camposdelForm =document.getElementById("divShowFile2");
	    /*var filesinput = camposdelForm.getElementsByTagName("a") ;
	    var duplicatedFiles = " Los siguientes archivos ya se encuentran en el expediente: \n" ;
		var cantduplicated = 0 ; 
		var archivos= filesinput.length;
		var idarchivos =  new Array (archivos) ;  
		var files = filesinput  ;
				 
		// Comprobar de que no existen los mismos archivos en el expediente;
		var uploaded = new Array(archivos) ; 
		var ruta0= "" ;
		
		for(var t=0;t<files.length;t++){ 
			var nuploaded = rutapadre+files[t].innerHTML ;      
			nuploaded = nuploaded.toUpperCase() ;
			archivosAlf = document.getElementsByName("rutaAlfresco");
			duplicadosup = document.getElementsByName("idArchivo");				
			for(var u = 0 ; u<archivosAlf.length ;u++){ 
				archivosAlf[u].value = archivosAlf[u].value.toUpperCase(); 
				if(nuploaded==archivosAlf[u].value){  
					duplicatedFiles = duplicatedFiles + files[t].innerHTML +" \n" ; 
					idarchivos[u] =  duplicadosup[u].value ;  
					cantduplicated = cantduplicated + 1 ;  
					ruta0 = "&idarchivos="+duplicadosup[u].value;  
					break ;
				}else { 
					idarchivos [u] = 0  ;    
				}     
			}
		}
		
		var  versionar = false ;
			
		if(cantduplicated >0 ){
			var  versionar = confirm(duplicatedFiles+"\n Desea versionar estos archivos ? ") ;
			if(!versionar){
				enviar = false ;  
			}
		}  
		
		if(archivos<1){
			alert("Es necesario subir al menos un archivo.");
			enviar =  false;
			return;
		}*/
	
		var tipo = document.getElementsByName ("objDocumento.tipoDocumento.idtipodocumento")[0].value; //   $("input[name='objDocumento.tipodocumento.idtipodocumento']").val();
		
		if(tipo==""){   
			alert("Debe ingresar un tipo de documento");
			enviar =  false;
			return;
		}
				
		var asunto  =   document.getElementById("objDocumento.asunto").value ;
		
		if(asunto==""){
			alert("Es necesario ingresar un asunto ");
			enviar =  false;
			return;
		}	
	
		var firmante = dijit.byId("objDocumento.firmante.idusuario").getValue();
		var nrodocumento =dijit.byId("objDocumento.numeroDocumento").getValue();
		var tipodoc = dijit.byId("objDocumento.tipoDocumento.idtipodocumento").getValue();
	
		if(tipo!=""&&firmante!=null&&firmante==firmanteAux){ 
			if(dojo.byId("objDocumento.enumerarDocumento").checked&&tiponumeracionActual!=null&&tiponumeracionActual=="M"&&nrodocumento=="") {
				alert("Debe ingresar un nro de documento");
	    		enviar =  false; 
	    	 }
	    }  
					
	    if(!dojo.byId("objDocumento.enumerarDocumento").checked){
	    	dojo.byId("textonrodoc").value = "" ;       
	    }
	              
		dojo.byId("tiponumeracion").value = tiponumeracionActual ; 
				
		if(enviar){
			var ruta =  document.forms["frmUploadWithForm"].action ;
	 		//ruta = ruta  + "?versionar="+versionar+ruta0;  				
	 		
	 		if(!dojo.byId("objDocumento.enumerarDocumento").checked){
	 			dojo.byId("objDocumento.enumerarDocumento").value = "N"; 
	 	 	}else {
	 	 		dojo.byId("objDocumento.enumerarDocumento").value = "S"; 
	 	 	}
	 		
	 		document.forms["frmUploadWithForm"].action = ruta ;  
	 		dijit.byId("dlgProgresBar").show() ;  
	 		document.forms["frmUploadWithForm"].submit();  
	 	}
	} 
	
	function refrescar() {
		var iddoc = document.getElementById('idDocumento');   
		
		if(origen!="informativo"){
			window.opener.viewDetailCustom2(iddoc.value); 
		}else{
			window.opener.viewDetailInformativo(idNotificacion,iddoc.value); 			    	 
		}
		
		window.close();  
	}          				
	
	/**REN Llamado cuando se elige un tipo documento en el formulario ---------------------------------------------------------*/
	function actualizarFirmante(){
		tipodocumentoActual = dijit.byId("objDocumento.tipoDocumento.idtipodocumento").getValue();
		var params = new Array();
		params['tipodocumento'] = tipodocumentoActual;
		params['unidad'] = unidadActual;
		var store = new dojo.data.ItemFileReadStore({url: "getNumeracion.action?tipodocumento="+tipodocumentoActual+"&unidad="+unidadActual})     ;
		var sortAttributes = [{ attribute: "tiponumeracion", descending: true}];   
	
		function completed(items, findResult){ 
			tiponumeracionActual = store.getValue(items[0], "tiponumeracion"); 
			firmanteActual =store.getValue(items[0], "firmante") ; 
			destinatarioActual = store.getValue(items[0], "destinatario"); 
	
			console.log("iiii:"+0+"Item tiponumeracion: [" + store.getValue(items[0], "tiponumeracion") + "] with firmante: [" + store.getValue(items[0], "firmante") + "] with destinatario: [" + store.getValue(items[0], "destinatario") + "]"   );
			dojo.byId("tiponumeracion").value = tiponumeracionActual;
			mostrarOcultarCampos();  
		}
			
		function error(errData, request){ 
			console.log(errData+" ... Failed in sorting data."+request);
		}
		
		store.fetch({onComplete: completed, onError: error, sort: sortAttributes});
	}

	function mostrarOcultarFirmante (){
		var tipo = document.getElementsByName ("objDocumento.tipoDocumento.idtipodocumento")[0].value;
		var firmante = dijit.byId("objDocumento.firmante.idusuario").getValue();
	
		if(tipo!=""&&firmante!=null&&firmante==firmanteAux){
			dojo.byId("checkenumerar").style.display = "block";
			
			if(tiponumeracionActual=="M" || tiponumeracionActual=="X"){
				dojo.byId("textonrodoc").style.display = "block";
			}else {
				dojo.byId("textonrodoc").style.display = "none";
				dojo.byId("textonrodoc").value = "" ; 
			}
				
		}else {
			dojo.byId("checkenumerar").checked = false;          
			dojo.byId("checkenumerar").value  = "N";
			dojo.byId("checkenumerar").style.display = "none";
			dojo.byId("textonrodoc").style.display = "none";
			dojo.byId("textonrodoc").value = "" ; 			
		}			
	}
	
	
	function mostrarOcultarCampos(){
		var firmante = dijit.byId("objDocumento.firmante.idusuario").getValue();
		console.log("firmante ... "+firmante);
        console.log("tiponumeracionActual [%s]", tiponumeracionActual);
		
        if(tiponumeracionActual=="X"){
			// Ocultar todo
			dojo.byId("dest1").style.display = "none"; 
			dojo.byId("dest2").style.display = "none";
			dojo.byId("dest3").style.display = "none"; 
			dojo.byId("copy1").style.display = "none"; 
			dojo.byId("copy2").style.display = "none"; 
			dojo.byId("copy3").style.display = "none"; 
			dojo.byId("firmante").style.display = "none";
		     
			//limpiar data  
			dojo.byId("destinatarios").childNodes = new Array();
			dojo.byId("copias").childNodes = new Array();
			dojo.byId("tiponumeracion").value = "";
			dijit.byId("objDocumento.firmante.idusuario").setValue(""); 
			limpiar("destinatarios");
			limpiar("copias"); 
		}

		if(tiponumeracionActual=="A"){
			
			dojo.byId("checkenumerar").style.display = "block";
			dojo.byId("textonrodoc").style.display = "none";

			dojo.byId("objDocumento.numeroDocumento").value = "";
			dojo.byId("objDocumento.enumerarDocumento").checked = true;	
		}
		if(tiponumeracionActual=="M"){

			dojo.byId("checkenumerar").style.display = "block";
			dojo.byId("textonrodoc").style.display = "block";

			dojo.byId("objDocumento.numeroDocumento").value = "";
			dojo.byId("objDocumento.enumerarDocumento").checked = false;
		}
		
		if(firmanteActual=="C"){
			dojo.byId("firmante").style.display = "";
			dijit.byId("objDocumento.firmante.idusuario").setValue(firmanteAux); 
		}else{    
			dojo.byId("firmante").style.display = "none";
			dijit.byId("objDocumento.firmante.idusuario").setValue(firmanteAux); 
		}

		try {
			if(destinatarioActual=="E"){				 
				 dijit.byId("iddestinatariosE").attr("value", "");
				 dijit.byId("iddestinatariosE").attr("displayedValue", "");
				 dijit.byId("idcopiasE").attr("value", "");
				 dijit.byId("idcopiasE").attr("displayedValue", "");
			} else {
				 dijit.byId("iddestinatarios").attr("value", "");
				 dijit.byId("iddestinatarios").attr("displayedValue", "");
				 dijit.byId("idcopias").attr("value", "");
				 dijit.byId("idcopias").attr("displayedValue", "");	
			}
			limpiar("destinatarios");
			limpiar("copias");			 
			limpiar("destinatariosE");
			limpiar("copiasE");
			 
		} catch (error) {
			//console.log("error destinatarios:"+error.message);
		}
		
		if(destinatarioActual=="I"){
			dojo.byId("dest2").style.display = "none"; 
			dojo.byId("copy2").style.display = "none";			
			dojo.byId("dest1").style.display = ""; 
			dojo.byId("dest3").style.display = ""; 
			dojo.byId("copy1").style.display = ""; 
			dojo.byId("copy3").style.display = "";
		}else if(destinatarioActual=="E"){ 
			dojo.byId("dest1").style.display = "none"; 
			dojo.byId("copy1").style.display = "none"; 
			dojo.byId("dest2").style.display = ""; 
			dojo.byId("dest3").style.display = ""; 
			dojo.byId("copy2").style.display = "";
			dojo.byId("copy3").style.display = ""; 		
		}else{
			dojo.byId("dest1").style.display = "none"; 
			dojo.byId("dest2").style.display = "none";
			dojo.byId("dest3").style.display = "none"; 
			dojo.byId("copy1").style.display = "none"; 
			dojo.byId("copy2").style.display = "none";
			dojo.byId("copy3").style.display = "none";		
		}	
		chknroDoc();
	}
	

	function limpiar(componente) {
		
		var d = document.getElementById(componente);
		while (!Siged.String.isEmpty(d) && d.hasChildNodes())
			d.removeChild(d.firstChild);

		document.getElementById("id"+componente).value="";
		dijit.byId("id"+componente).attr("value", "");
		dijit.byId("id"+componente).attr("displayedValue", "");	
	}	

		 function  chknroDoc(){			 
			        var chk =    dojo.byId("objDocumento.enumerarDocumento") ; 
			 		var txtnrodoc = 	dijit.byId("objDocumento.numeroDocumento");
			 		//alert("dentro de chk "+chk.checked);
			 		//console.debug((txtnrodoc.attr("disabled")==true)+" .. " +txtnrodoc.attr("disabled")=="true");   

				 	if(chk.checked==true){
				 		//alert("enabling  "+chk.checked);
			 			txtnrodoc.attr("value","" ); 
						txtnrodoc.attr("disabled", false);
						   
				 	}else{ 
				 		//alert("disabling   "+chk.checked);
				 		txtnrodoc.attr("value","" );
						txtnrodoc.attr("disabled", true); 						      
					 }			 		
			 }

		
		var agregar=function(tipo, tipoE, compartido){
			var nombre=document.getElementById("id"+tipoE).value;
			var id=dijit.byId("id"+tipoE).getValue();
					
			//alert("nombre "+nombre+" id "+id);
			if(nombre!=null && nombre!="" && id!=null && id!=""){
				var existe=false;
				var conCopia=document.getElementsByName("con"+tipo);
				//"#copias span input[name='conCopia']"
				$(conCopia).each(function(){
					if($(this).val()==id)
						existe=true;
				}); 
				if(compartido!=""){

					var conCopia2=document.getElementsByName("con"+compartido);
					//"#copias span input[name='conCopia']"
					$(conCopia2).each(function(){
						if($(this).val()==id)
							existe=true;
					}); 
					
					}
				
				
				if(!existe){
					var copia="<span class=\"copia\">";
						copia+="<span>"+nombre+"</span>";
						copia+="<input type=\"hidden\" name=\"con"+tipo+"\" value=\""+id+"\" />";
						copia+="<a href=\"#\" class=\"quitar\"><img src=\"images/eliminar.gif\" alt=\"X\" /></a></span>";
					$("#"+tipo).append(copia);
					$(".quitar").click(function(){
						$(this).parent().remove();
					});
				}
			}
		}; 

		var agregarDestinatario = function(){ 
			agregar("destinatarios", "destinatarios", "copias");
		}

		var agregarCopia = function(){ 
			agregar("copias", "copias", "destinatarios"); 
		}
		function agregarDestinatarioExterno(){
			agregar("destinatarios", "destinatariosE", "copias");
			dijit.byId("iddestinatariosE").attr("displayedValue", "");
		}
		function agregarCopiaExterna(){
			agregar("copias", "copiasE", "destinatarios");
			dijit.byId("idcopiasE").attr("displayedValue", "");
		}	

	 var strDerivarCC = new dojo.data.ItemFileReadStore({url: "derivarCC.action"});
		 
		dojo.addOnLoad(function(){
			new dijit.form.FilteringSelect({
				store			: strDerivarCC,
				searchAttr		: "label",
				labelAttr		: "label",
				idAttr			: "ids",
				required		: false,
				hasDownArrow	: true,
				highlightMatch	: "all",
				style           : "width:300px",
				onChange		: agregarDestinatario
			},"iddestinatarios");
        
			new dijit.form.FilteringSelect({
				store			: strDerivarCC,
				searchAttr		: "label",
				labelAttr		: "label",
				idAttr			: "ids",
				required		: false,
				hasDownArrow	: true,
				highlightMatch	: "all",
				style           : "width:300px",
				onChange		: agregarCopia
			},"idcopias");

		    Siged.Forms.combobox("fsNroIdentificacion1",{
		        id              : "iddestinatariosE",
		        jsId            : "iddestinatariosE",
		        name            : "iddestinatariosE",
		        //storeUrl		: "obtenerClientePorTipoIdentificacion.action",
		        searchAttr      : "label",
		        labelAttr		: "label",
		        queryExpr       : "*${0}*",
		        autoComplete    : false,
		        hasDownArrow    : false,
		        highlightMatch  : "all",
		        invalidMessage  : "Seleccione un Nro de Identificacion",
		        onChange        : agregarDestinatarioExterno,
		        required        : false,
		        style           : "width:300px"
		    });

		    Siged.Forms.combobox("fsNroIdentificacion2",{
		        id              : "idcopiasE",
		        jsId            : "idcopiasE",
		        name            : "idcopiasE",
		        //storeUrl		: "obtenerClientePorTipoIdentificacion.action",
		        searchAttr      : "label",
		        queryExpr       : "*${0}*",
		        autoComplete    : false,
		        searchDelay     : 650,
		        hasDownArrow    : false,
		        highlightMatch  : "all",
		        invalidMessage  : "Seleccione un Nro de Identificacion",
		        onChange        : agregarCopiaExterna,
		        required        : false,
		        style           : "width:350px"
		    });		

		    var storeDestinatarios = new dojo.data.ItemFileReadStore({url: "obtenerClienteOptimizado.action"});
		    var storeCopias = new dojo.data.ItemFileReadStore({url: "obtenerClienteOptimizado.action"});
		    var componente1 = dijit.byId("iddestinatariosE");
		    var componente2 = dijit.byId("idcopiasE");
		    if (!Siged.String.isEmpty(componente1))
		    	dijit.byId("iddestinatariosE").store = storeDestinatarios;
		    if (!Siged.String.isEmpty(componente2))
		    	dijit.byId("idcopiasE").store = storeDestinatarios;    
        }); 

