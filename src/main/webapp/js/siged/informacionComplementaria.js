		dojo.addOnLoad(function() {
	
			new dijit.form.DateTextBox( {
				id : "documento.expediente.fechacreacion",
				jsId : "documento.expediente.fechacreacion",
				name : "documento.expediente.fechacreacion",
				constraints : {
					datePattern : 'dd/MM/yyyy'
				},	
				readOnly : "true"
			}, "divFechaRegistro");
			new dijit.form.DateTextBox( {
				id : "documento.fechaLimiteAtencion",
				jsId : "documento.fechaLimiteAtencion",
				name : "documento.fechaLimiteAtencion",
				constraints : {
					datePattern : 'dd/MM/yyyy'
				},		
				readOnly : "true",
				value : new Date()	
			}, "divFechaVencimiento");
			new dijit.form.DateTextBox( {
				id : "fechaDerivacion",
				jsId : "fechaDerivacion",
				name : "fechaDerivacion",
				constraints : {
					datePattern : 'dd/MM/yyyy'
				},	
				readOnly : "true"
			}, "divFechaDerivacion");
			new dijit.form.DateTextBox( {
				id : "fechaSesion",
				jsId : "fechaSesion",
				name : "fechaSesion",
				constraints : {
					datePattern : 'dd/MM/yyyy'
				},
				required : "true"
			}, "divFechaSesion");
			new dijit.form.DateTextBox( {
				id : "fechaNotiReclamante",
				jsId : "fechaNotiReclamante",
				name : "fechaNotiReclamante",
				constraints : {
					datePattern : 'dd/MM/yyyy'
				},
				required : "true"
			}, "divFechaNotiReclamante");
			new dijit.form.DateTextBox( {
				id : "fechaNotiConcesionario",
				jsId : "fechaNotiConcesionario",
				name : "fechaNotiConcesionario",
				constraints : {
					datePattern : 'dd/MM/yyyy'
				},
				required : "true"
			}, "divFechaNotiConcesionario");			

	      						
			}); 