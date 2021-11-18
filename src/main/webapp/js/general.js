
//*************************************************************************************//

/*
function calendario(nom_campo,formato) {
var matParam = new Array(3);
matParam[0] = window;
matParam[1] = nom_campo;
window.showModalDialog('images/pl_calendario.htm',matParam,'dialogHeight:210px; dialogWidth:280px;center:no; help:no; resizable:no; status:no');
}
*/

function esEmail(string) //string = cadena que representa al correo electronico
{//valida si la entrada es un correo electronico si es cierto devuelve true
	  if (string.search(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/) != -1)
		  return true;
		else
		  return false;
}


//**********************************************

function maximize(){
if (window.screen) {
    var aw = screen.availWidth;
    var ah = screen.availHeight;
    window.moveTo(0, 0);
    window.resizeTo(aw, ah);
  }
}

//**********************************************
function ir_pagina(objeto, tipo_pagina, pagina, ruta) {
	with(objeto) {
         objeto.target="";
		if (tipo_pagina=="S") 	hid_pagina.value = pagina
		if (tipo_pagina=="A") 	hid_pagina.value = pagina
		if (tipo_pagina=="N") 	hid_pagina.value = cmb_paginacion.value;
		action = ruta;
		submit();		
	}
}

//***********************************************
function check_all(form)  {
	for(i=0 ; i<form.elements.length; i++)	{ 
		if(form.elements[i].type == "checkbox")	
             form.elements[i].checked = (form.elements[i].checked) ? false : true;
	}
}
//***********************************************
 function valida_numero(xinput,tipval)
  {
    var xkey=event.keyCode;
	if(tipval=="int")
		if ((xkey < 48) || (xkey > 57)) event.returnValue = false;
	if(tipval=="dec")
		{ if ((xkey < 46) || (xkey > 57)) event.returnValue = false;
                  }  

    
                 
	if(tipval=="str")
		if (((xkey != 32) && (xkey < 65)) || ((xkey > 90) && (xkey < 97))) event.returnValue = false;
	if(tipval=="tlf")
		if (((xkey != 32) && (xkey < 45)) || (xkey > 57)) event.returnValue = false;
		
	if (tipval=="afn")	
	 { if (  ((xkey != 32) && (xkey < 45))  || (xkey > 57)   ||
	           ((xkey < 48) || (xkey > 57))   )   event.returnValue = false;
 	  }
		
  }

