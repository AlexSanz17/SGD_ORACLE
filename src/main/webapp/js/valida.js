
//********************************************************************************************//
/*
function esTeclaTexto(e) {
var valid = "0123456789-_:;.,)(abcdefghijklmï¿½nopqrstuvwxyzABCDEFGHIJKLMNï¿½OPQRSTUVWXYZï¿½ï¿½ï¿½ï¿½ï¿½/\{}[]!%&<>*=ï¿½ï¿½ï¿½ ";
var validMay = "abcdefghijklmï¿½nopqrstuvwxyz";
var key = String.fromCharCode(event.keyCode);
if (valid.indexOf("" + key) == "-1")  return false;
else{ 
	if (validMay.indexOf("" + key)!="-1")
	event.keyCode =event.keyCode-32;
	}
return (e);
}
*/
 //Variables globales
 var dtCh= "/";			//Separador de fecha
 var minYear=1900;		//año minimo
 var maxYear=2100;		//año máximo

function esTeclaTexto(e) {
var validMay = "abcdefghijklmï¿½nopqrstuvwxyz";
var key = String.fromCharCode(event.keyCode);
	if (validMay.indexOf("" + key)!="-1")
	event.keyCode =event.keyCode-32;
return (e);
}

//********************************************************************************************//
function esTeclasTodas(e) {
var valid = "abcdefghijklmï¿½nopqrstuvwxyzABCDEFGHIJKLMNï¿½OPQRSTUVWXYZ :.-/()#&0123456789_ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½";
var key = String.fromCharCode(event.keyCode);
	if (valid.indexOf("" + key) == "-1") return false;
}
function esTeclasTodasComodin(e) {
var valid = "abcdefghijklmï¿½nopqrstuvwxyzABCDEFGHIJKLMNï¿½OPQRSTUVWXYZ :.-/()#&0123456789_ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½%";
var key = String.fromCharCode(event.keyCode);
	if (valid.indexOf("" + key) == "-1") return false;
}

//********************************************************************************************//
function esTeclaNumero(e) {
var valid = "0123456789";
var key = String.fromCharCode(event.keyCode);
	if (valid.indexOf("" + key) == "-1") return false;
}

function esTeclaFecha(e) { 
var valid = "0123456789/";
var key = String.fromCharCode(event.keyCode);
	if (valid.indexOf("" + key) == "-1") return false;
}
//********************************************************************************************//

function esNada(e) {
var valid = "";
var key = String.fromCharCode(event.keyCode);
	if (valid.indexOf("" + key) == "-1") return false;
}
//********************************************************************************************//

function esTeclaDecimal(e) {
var valid = "0123456789,.";
var key = String.fromCharCode(event.keyCode);
	if (valid.indexOf("" + key) == "-1") return false;
}
//********************************************************************************************//
function esTeclaRangoNumeros(e) {
var valid = "0123456789,-";
var key = String.fromCharCode(event.keyCode);
	if (valid.indexOf("" + key) == "-1") return false;
}
//********************************************************************************************//
function esEmail(string) //string = cadena que representa al correo electronico
{//valida si la entrada es un correo electronico si es cierto devuelve true
	  if (string.search(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/) != -1)
		  return true;
		else
		  return false;
}
//********************************************************************************************//
function compararFechaMayor(strFecha,strFechaMayor){
	var fecha1 = strFecha.split("/");
	var fecha2 = strFechaMayor.split("/");	

	var strFecha1 = parseFloat(fecha1[2] + fecha1[1] + fecha1[0]);
	var strFecha2 = parseFloat(fecha2[2] + fecha2[1] + fecha2[0]);
		 		 
	if (strFecha1 >= strFecha2) { 
		return false; }
	else { return true; }  
}
//********************************************************************************************//
function esFecha(day,month,year) {

  if ((day.length!=2) || (month.length!=2) || (year.length!=4)) {
     return false;
  }
  if (month>12) return false;
  if ((month==1 || month==3 || month==5 || month==7 || month==8 || month==10 || month==12) && day>=32){
      return false;
  }
  if ((month==4 || month==6 || month==9 || month==11) && day>=31){
      alert("El mes de "+ fcnConvertirMes(month) +" sï¿½lo tiene 30 dias!");
      return false;
  }
  if (month == 2){
      var isleap = (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));
      if (day>29 || (day==29 && !isleap)){
      alert("Febrero de " + year + " no tiene " + day + " dias!");
      return false;
      }
  }
  return true;
}
//*************************************************************************************//
function esVacio(cad) { //retorna true si la cadena esta vacia o tiene espacios en blanco
  var i;
  var blanco = " \n\t" + String.fromCharCode(13); // blancos
  var es_vacio;
  
    if ((cad == null) || (cad.length == 0)) { return true; }
    for(i = 0, es_vacio = true; (i < cad.length) && es_vacio; i++)
      es_vacio = blanco.indexOf(cad.charAt(i)) != - 1;
    return(es_vacio);
}

function Trim(dato) { 
  return String(dato).replace(/[\s]/g,""); 
}
//*************************************************************************************//
// Recibe como parametro un obj de tipo checkbox (arreglo u objeto simple) 
// y devuelve true si alguno esta seleccionado
function estaSeleccionado(obj) { 
   check = false;
   if(isNaN(obj.length)) {
       check = obj.checked;
   }
   else {
   longitud = obj.length;   		
       for(i = 0; i < longitud; i++) {
          if(obj[i].checked == true) {
              check = true;
              break;
          }
       }
   }
   return check;
}

//*************************************************************************************//
function mensaje(){
	obj = document.getElementById("errores"); 
	if(obj.style.display=='none')
		obj.style.display='';
	else
		obj.style.display='none'; 
}
//*************************************************************************************//
function replace(linea, antiguo,nuevo){
   res = eval('linea.replace(/'+antiguo+'/g, \"'+nuevo+'\")'); 
   return res;
}


//*************************************************************************************//
function isEnter(nextTab){
    if(event.keyCode==13){
		if(null!=document.all[nextTab])
			document.all[nextTab].focus()
    }
}
function DaysArray(n) {
	for (var i = 1; i <= n; i++) {
		this[i] = 31
		if (i==4 || i==6 || i==9 || i==11) {this[i] = 30}
		if (i==2) {this[i] = 29}
   } 
   return this
}

function isInteger(s){
	var i;
    for (i = 0; i < s.length; i++){   
        // Check that current character is number.
        var c = s.charAt(i);
        if (((c < "0") || (c > "9"))) return false;
    }
    // All characters are numbers.
    return true;
}

function stripCharsInBag(s, bag){
	var i;
    var returnString = "";
    // Search through string's characters one by one.
    // If character is not in bag, append to returnString.
    for (i = 0; i < s.length; i++){   
        var c = s.charAt(i);
        if (bag.indexOf(c) == -1) returnString += c;
    }
    return returnString;
}

function isDate(dtStr){
	var daysInMonth = DaysArray(12);
	var pos1=dtStr.indexOf(dtCh);
	var pos2=dtStr.indexOf(dtCh,pos1+1);
	var strDay=dtStr.substring(0,pos1);
	var strMonth=dtStr.substring(pos1+1,pos2);
	var strYear=dtStr.substring(pos2+1);
	strYr=strYear;
	if (strDay.charAt(0)=="0" && strDay.length>1) strDay=strDay.substring(1);
	if (strMonth.charAt(0)=="0" && strMonth.length>1) strMonth=strMonth.substring(1);
	for (var i = 1; i <= 3; i++) {
		if (strYr.charAt(0)=="0" && strYr.length>1) strYr=strYr.substring(1);
	} 
	month=parseInt(strMonth);
	day=parseInt(strDay);
	year=parseInt(strYr);
	if (pos1==-1 || pos2==-1){
		alert("El formato de fecha debe ser : dd/mm/yyyy");
		return false;
	}
	if (strMonth.length<1 || month<1 || month>12){
		alert("Por favor ingrese un mes válido");
		return false;
	}
	if (strDay.length<1 || day<1 || day>31 || (month==2 && day>daysInFebruary(year)) || day > daysInMonth[month]){
		alert("Por favor ingrese un dia válido");
		return false;
	}
	if (strYear.length != 4 || year==0 || year<minYear || year>maxYear){
		alert("Por favor ingrese un año de 4 digitos entre "+minYear+" y "+maxYear);
		return false;
	}
	if (dtStr.indexOf(dtCh,pos2+1)!=-1 || isInteger(stripCharsInBag(dtStr, dtCh))==false){
		alert("Por favor ingrese una fecha válida");
		return false;
	}
	return true;
}