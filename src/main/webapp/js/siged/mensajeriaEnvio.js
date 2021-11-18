         function tipoEnvio(codigo){

            if(codigo != ""){
               Tipoenvio.viewcodMensajeria(codigo,tipoEnviocallBack);
            }
         }
         function tipoEnviocallBack(data){
            if(data!=null){
               DWRUtil.setValue("envio1",data.tipoenvio1);
        DWRUtil.setValue("envio2",data.tipoenvio2);
    }

         }
         function tipoCourier(codigocu){
            Courier.viewcod(codigocu,couriercallBack);
         }
         function couriercallBack(data){
            DWRUtil.setValue("nomcourierx",data.nombrecourrier);
            DWRUtil.setValue("nomcourier",data.nombrecourrier);
         }

         function validar(e) {
            tecla = (document.all) ? e.keyCode : e.which;
            if (tecla==13) {
               var num1=dijit.byId('codcourierr');
               var num=num1+"0";
               var num=num.charAt(0)+num.charAt(1)+num.charAt(2);
               //alert(num);
               this.tipoCourier(num);
            }
         }
         function Tecla(event)
{
    console.debug("1");
	         if (document.all) var tecla = event.keyCode;
	         
	         else if(document.layers) var tecla = event.which;
	         console.debug("2");
	         if (tecla > 47 && tecla < 58) 	 return true;
	         else
    {
        console.debug("3");
	        	 if (tecla != 8) event.keyCode = 0;
	        	 else return true;
	         }	         
         }
         
         function checkDecimales(numero)
         {
        	 console.debug("checkDecimales");
	         var decimales = 2;
		     if (numero.indexOf('.') == -1) numero += ".";
		       	dectext = numero.substring(numero.indexOf('.')+1, numero.length);
		     if (dectext.length > decimales)
		     {
		    	console.debug("tiene mas decimales");
			    return false;
		     } else {
		    	 console.debug("ok check decimales");
		        return true;
		     }
         }
         
         function validaNumero(idnumero) {
            var numero = document.getElementById(idnumero).value;

            if (numero != "") {
               var patternNumber=/^\d+$/;

               if (numero.search(patternNumber) == -1) {
            	   if(idnumero=="obEnv.pesodcmto"){
            		   var variable = (numero - 0) == numero && numero.length > 0 && numero>0;
            		   if( variable == false ){
	   	               	   	alert("El dato ingresado debe ser numerico");
		            	   	document.getElementById(idnumero).focus();
		            	   	return false;    
            		   } 
            		   if ( checkDecimales(numero)== false){
            			   alert("El dato ingresado debe tener como máximo 2 decimales");
            			   document.getElementById(idnumero).focus();
          			       return false;
            		   }          		              			        		   
            	   }               	   
            	   else{
	               	   	alert("El dato ingresado debe ser numerico");
	            	   	document.getElementById(idnumero).focus();
	            	   	return false;               		   
            	   }
               }

               if (numero < 1 || numero > 100000000000000000000000000000000000000000000) {
                  alert("El dato debe estar entre 1 y 100000000000000000000000000000000000000000000.");
                  document.getElementById(idnumero).focus();
                  return false;
               }
               return true;
            }
            else {
            	alert("Debe completar todos los datos");
            	document.getElementById(idnumero).focus();
            	return false;
            }
         }

         function validarForm() {
 			var courier = dojo.byId("codcourierr").value;
			var unidadpeso = dojo.byId("unidadpeso").value;
			var numeroguia = document.getElementsByName("obEnv.numeroguia")[0].value;
			var fechasalida = dojo.byId("sFechaSalida").value;

    var fechaentrega = dojo.byId("sFechaEntrega").value;
    var fechadevCargo = dojo.byId("sFechaDevCargo").value;
    var fechadevArea = dojo.byId("sFechaDevArea").value;

			console.debug("courier "+courier+" unidadpeso "+unidadpeso+" numeroguia "+numeroguia+" fechasalida "+fechasalida);
    if(courier=="" || unidadpeso=="" || numeroguia=="" || fechasalida=="" || fechaentrega=="" || fechadevCargo=="" || fechadevArea=="" ){
				alert("Debe completar todos los datos");
				return false;
			}
			else{
	        	 //Valida si es nulo y si son números
        //	            if (!validaNumero("obEnv.diasdistribuicion")) {
        //	               return false;
        //	            }
        //	            if (!validaNumero("obEnv.diasdevcargo")) {
        //	               return false;
        //	            }
        //	            if (!validaNumero("obEnv.diasdevolucion")) {
        //	               return false;
        //	            }
        //	            if (!validaNumero("obEnv.pesodcmto")) {
        //	                return false;
        //	            }
	            return true;
			} 
         }

         var creacionCourier = function(){
        	 console.debug("creando courier");
        	 Siged.Forms.combobox("fsCourier", {
                 id             : "codcourierr",
                 jsId           : "codcourierr",
                 storeUrl       : "autocompletarcourrier.action",
                 searchAttr     : "label",
                 searchDelay    : 650,
                 queryExpr      : "*${0}*",
                 autoComplete   : false,
                 hasDownArrow   : true,
                 highlightMatch : "all",
                 promptMessage  : "Seleccione un Courier",
                 required       : true,
                 onChange       : UpdateInfoCourier,
                 size 			: 45
              });       	 
         };
         
         var viewEnvioOnLoad = function() {
        	 console.debug("viewEnvioOnLoad");
        		creacionCourier();
        	};
        	
         dojo.addOnLoad(function() {
        	 console.debug("en el addonload");
        	 //var store = new dojo.data.ItemFileReadStore({url: "autocompletarcourrier.action"});
        	 
        	/* 	var widgetSalida = dijit.byId("sFechaSalida");         	 
				if (widgetSalida){
					console.debug("antes del seteo sfechasalida");
	            	dijit.byId("sFechaSalida").setValue(new Date());
	            	console.debug("seteo sfechasalida");
				}    
				
				var widget = dijit.byId("codcourier");  
				if(widget){
					console.debug("existe");
					dojo.byId("codcourier").destroy();
				}*/
     		new dijit.form.DateTextBox ({
				id : "sFechaSalida",
                jsId : "sFechaSalida",
                name : "sFechaSalida",
        constraints : {
            datePattern:'dd/MM/yyyy'
        },
                invalidMessage : "Ingrese fecha de Salida dd/MM/yyyy",
                required : "true",
                trim : "true",
                value : new Date()
			}, "divFechaSalida" );  
     		

    new dijit.form.DateTextBox ({
        id : "sFechaEntrega",
        jsId : "sFechaEntrega",
        name : "sFechaEntrega",
        constraints : {
            datePattern:'dd/MM/yyyy'
        },
        invalidMessage : "Ingrese fecha de Salida dd/MM/yyyy",
        required : "true",
        trim : "true",
        value : new Date()
    }, "divFechaEntrega" );


    new dijit.form.DateTextBox ({
        id : "sFechaDevCargo",
        jsId : "sFechaDevCargo",
        name : "sFechaDevCargo",
        constraints : {
            datePattern:'dd/MM/yyyy'
        },
        invalidMessage : "Ingrese fecha de Salida dd/MM/yyyy",
        required : "true",
        trim : "true",
        value : new Date()
    }, "divFechaDevCargo" );




    new dijit.form.DateTextBox ({
        id : "sFechaDevArea",
        jsId : "sFechaDevArea",
        name : "sFechaDevArea",
        constraints : {
            datePattern:'dd/MM/yyyy'
        },
        invalidMessage : "Ingrese fecha de Salida dd/MM/yyyy",
        required : "true",
        trim : "true",
        value : new Date()
    }, "divFechaDevArea" );

                        
     		
            Siged.Forms.combobox("fsCourier", {
                id             : "codcourierr",
                jsId           : "codcourierr",
                storeUrl       : "autocompletarcourrier.action",
                searchAttr     : "label",
                searchDelay    : 650,
                queryExpr      : "*${0}*",
                autoComplete   : false,
                hasDownArrow   : true,
                highlightMatch : "all",
                promptMessage  : "Seleccione un Courier",
                required       : true,
                onChange       : UpdateInfoCourier
             });
            
            console.debug("fin del addonload");
         });         