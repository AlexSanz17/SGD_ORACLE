function checkDecimales(numero) {
	console.debug("checkDecimales");
	var decimales = 2;
	if (numero.indexOf('.') == -1)
		numero += ".";
	dectext = numero.substring(numero.indexOf('.') + 1, numero.length);
	if (dectext.length > decimales) {
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
		var patternNumber = /^\d+$/;
		
		if (numero.search(patternNumber) == -1) {
     	   if(idnumero=="objCg.costoenvio"){
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
	} else {
		alert("Debe completar todos los datos");
		document.getElementById(idnumero).focus();
		return false;
	}
} 


dojo.addOnLoad(function() {
	new dijit.form.DateTextBox( {
		id : "sFechaEntrega",
		jsId : "sFechaEntrega",
		name : "sFechaEntrega",
		constraints : {
			datePattern : 'dd/MM/yyyy'
		},
		invalidMessage : "Ingrese fecha de Entrega dd/MM/yyyy",
		required : "true",
		trim : "true",
		value : new Date()
	}, "divFechaEntrega");
	new dijit.form.DateTextBox( {
		id : "sFechaCargo",
		jsId : "sFechaCargo",
		name : "sFechaCargo",
		constraints : {
			datePattern : 'dd/MM/yyyy'
		},
		invalidMessage : "Ingrese fecha de Cargo dd/MM/yyyy",
		required : "true",
		trim : "true",
		value : new Date()
	}, "divFechaCargo");
	new dijit.form.DateTextBox( {
		id : "sFechaDevCargo",
		jsId : "sFechaDevCargo",
		name : "sFechaDevCargo",
		constraints : {
			datePattern : 'dd/MM/yyyy'
		},
		invalidMessage : "Ingrese fecha de Dev. Cargo dd/MM/yyyy",
		required : "true",
		trim : "true",
		value : new Date()
	}, "divFechaDevCargo");
	new dijit.form.DateTextBox( {
		id : "sFechaDevDoc",
		jsId : "sFechaDevDoc",
		name : "sFechaDevDoc",
		constraints : {
			datePattern : 'dd/MM/yyyy'
		},
		invalidMessage : "Ingrese fecha de Dev. Documento dd/MM/yyyy",
		required : "true",
		trim : "true",
		value : new Date()
	}, "divFechaDevDoc");
	new dijit.form.DateTextBox( {
		id : "sFechaPrimeraVisita",
		jsId : "sFechaPrimeraVisita",
		name : "sFechaPrimeraVisita",
		constraints : {
			datePattern : 'dd/MM/yyyy'
		},
		invalidMessage : "Ingrese fecha de Primera Visita dd/MM/yyyy",
		required : "true",
		trim : "true",
		value : new Date()
	}, "divFechaPrimeraVisita");
	new dijit.form.DateTextBox( {
		id : "sFechaSegundaVisita",
		jsId : "sFechaSegundaVisita",
		name : "sFechaSegundaVisita",
		constraints : {
			datePattern : 'dd/MM/yyyy'
		},
		invalidMessage : "Ingrese fecha de Primera Visita dd/MM/yyyy",
		required : "true",
		trim : "true",
		value : new Date()
	}, "divFechaSegundaVisita");
});