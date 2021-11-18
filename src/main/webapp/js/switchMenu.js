

/*var menuColapsado = "false";*/
var menuColapsado = "false"
var menuComprimido = "true";
var submenuComprimido = "true";
/*
	if (typeof(document.forms[0])!="undefined"){
		if (document.forms[0].estadoDesplazamiento.value!="true" && document.forms[0].estadoDesplazamiento.value!="false"){
			document.forms[0].estadoDesplazamiento.value = "false";
		}else{
			menuColapsado = document.forms[0].estadoDesplazamiento.value;
		}
	}*/
function switchMenu2(){
 	menuColapsado = readCookie('estadoMenu');
	if (menuColapsado == "false") {  // menu expandido
   		document.getElementById("principal").style.display="block";
		document.getElementById("izquierda").width= "190px";
		document.getElementById("derecha").width = "80%";
		
	}
	else {
		document.getElementById("principal").style.display="none";
	    document.getElementById("izquierda").width= "1px";
		document.getElementById("derecha").width = "99%";
	}
}

function switchMenu3() {
        menuColapsado = "true";
        parent.document.getElementById("centro").cols = "7,*";
    	parent.leftFrame.document.getElementById("tablaMenuColapsado").style.display = "block";
        parent.leftFrame.document.getElementById("tablaMenuExpandido").style.display = "none";
}	

function switchMenu() {
	if (menuColapsado == "false") {  // menu expandido
   		menuColapsado = "true";
	    parent.document.getElementById("centro").cols = "12,*";
    	parent.leftFrame.document.getElementById("tablaMenuColapsado").style.display = "block";
		parent.leftFrame.document.getElementById("tablaMenuExpandido").style.display = "none";
	}
	else {
    	menuColapsado = "false";
		parent.document.getElementById("centro").cols = "180,*";
		parent.leftFrame.document.getElementById("tablaMenuExpandido").style.display = "block";
		parent.leftFrame.document.getElementById("tablaMenuColapsado").style.display = "none";
	}
}	

function switchMenuNew() {
	if (menuColapsado == "false") {  // menu expandido
   		menuColapsado = "true";
	    //parent.document.getElementById("centro").cols = "7,*";
    	document.getElementById("tablaMenuColapsado").style.display = "block";
		document.getElementById("tablaMenuExpandido").style.display = "none";
	}
	else {
    	menuColapsado = "false";
		//parent.document.getElementById("centro").cols = "180,*";
		document.getElementById("tablaMenuExpandido").style.display = "block";
		document.getElementById("tablaMenuColapsado").style.display = "none";
	}
}	

	

function showMenu() {
	var menu = document.getElementById("divmenu");
	if (menuComprimido == "true") {  // menu expandido
   		menuComprimido = "false";
    	menu.style.display = "block";
	}
	else {
    	menuComprimido = "true";
		menu.style.display = "none";
	}
	createCookie('divmenu',menuComprimido,0);
}

function showSubmenu(element) {
	var submenu = document.getElementById(element);
	if (submenuComprimido == "true") {  // menu expandido
   		submenuComprimido = "false";
    	submenu.style.display = "block";
    	
	}
	else {
    	submenuComprimido = "true";
		submenu.style.display = "none";
	}
	/*guardamos el estado final con el que queda submenuComprimido, 
	para el div respectivo*/
	createCookie(element,submenuComprimido,0); 
}

function loadMenuStateFromCookies() {
	//obtengo el array de cookies
	var ca = document.cookie.split(';');
	for(var i=0;i < ca.length;i++){
		var c = ca[i]; //ya tengo la cookie, ahora saco su nombre
		//le quito los espacios vacios
		while (c.charAt(0)==' ') c = c.substring(1,c.length);
		index = c.indexOf('='); //obtengo el indice del caracter '='
		cookieName = c.substring(0,index);
		if (cookieName.indexOf('divsubmenu')==0){ 
			//verifico que sea una cookie relacionada al menu y obtengo su valor
			valorSubMenuComprimido = readCookie(cookieName);
			obj = document.getElementById(cookieName); //la cookie tiene el mismo nombre que el elemento html
			if (obj != null){
				if (valorSubMenuComprimido == "true"){
					obj.style.display = "none";
				}else {
					obj.style.display = "block";
				}
			}
		}
	}
	//verifico el estado del menu principal
	valorMenuComprimido = readCookie('divmenu');
	obj2 = document.getElementById('divmenu');
	if (valorMenuComprimido == "true"){
		obj2.style.display = "none";
	}else{
		obj2.style.display = "block";
	}

}
/*function showSubmenu(element) {
	var submenu = document.getElementById(element);
	if (submenuComprimido == "true") {  // menu expandido
   		submenuComprimido = "false";
    	submenu.style.display = "block";
	}
	else {
    	submenuComprimido = "true";
		submenu.style.display = "none";
	}
}
*/