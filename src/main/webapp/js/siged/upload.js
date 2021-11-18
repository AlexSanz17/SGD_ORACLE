
function deleteIt(iIdUpload, sNombre) {
   if (iIdUpload == null) {
      iIdUpload = 1;
   }

   dojo.xhrPost({
      url: "doDeleteFile.action",
      content: {
         iIdUpload: iIdUpload,
         sNombre: sNombre
      },
      load: function(data) {
         if(dojo.byId('divShowFile' + iIdUpload)){
        	 dojo.byId('divShowFile' + iIdUpload).innerHTML = data; 
         }else if(dojo.byId("AA_archivos")){
        	 dojo.byId("AA_archivos").innerHTML = divPanel2(data);
         }
         
         if (dojo.byId('divUpload')) {
            dojo.byId('divUpload').innerHTML = dojo.byId('divUpload').innerHTML;
         }
         
      }
   });
}

// Para evitar errores de compatibilidad
function uploadIt(id){
   uploadIt(id,false);
}

function uploadIt(iIdUpload,dig) {
 //  console.log("iIdUpload [" + iIdUpload + "]");

   if (iIdUpload == null)
      iIdUpload = 1;
   // Modificado por Germán Enríquez para que valide el nombre de archivo
   if(dig){
      var archivo=document.getElementsByName("upload")[0].value;
      var separador=archivo.lastIndexOf("\\");
      if(separador>-1){
         archivo=archivo.substring(separador+1);
      }
      if(archivo.indexOf(" ")>-1){
         alert("Por favor, introduzca un archivo que no contenga espacios en su nombre.");
         return;
      }

   }

   if(dijit.byId("dlgProgresBar")!=null){
      dijit.byId("dlgProgresBar").show() ;
   }
   dojo.io.iframe.send({
      handleAs: "text/html",
      form: dojo.byId("frmUploadFile" + iIdUpload),
      content: {
         iIdUpload: iIdUpload
      },
      handle: function(data) {
         console.log("data resultado [" + data + "]");
         dojo.byId('divShowFile' + iIdUpload).innerHTML = data;

         if(dijit.byId("dlgProgresBar")!=null){
            dijit.byId("dlgProgresBar").hide() ;
         }
      //dojo.byId('divUpload').innerHTML = dojo.byId('divUpload').innerHTML;
      }
   });
}

function showUploadFiles(iIdUpload) {
   dojo.byId("frmUploadFile" + iIdUpload).action = "showUploadFiles.action" ;
   if (iIdUpload == null)
      iIdUpload = 1;

   dojo.io.iframe.send({
      handleAs: "text/html",
      form: dojo.byId("frmUploadFile" + iIdUpload),
      content: {
         iIdUpload: iIdUpload
      },
      handle: function(data) {
         dojo.byId('divShowFile' + iIdUpload).innerHTML = data;
      }
   });

   dojo.byId("frmUploadFile" + iIdUpload).action = "doUploadFile.action" ;
}

var divPanel2 = function(data){
        var dataTemp ="";
	var n = 0;
	 n = contador2(data);
	 dataTemp =  data;				
	
        var inicioN = 0;
	var finN = 0;
	var inicioI = 0;
	var finI = 0;
	var finP =0;
	var nombre ="";
	var divA ="";
	var divI ="";
	var extension ="";	
	var inner= "";
	var check="";
	var i = 0;
	var divPanelArchivos = "";
        
	for(i=0; i<n ;i++){
		inicioN = dataTemp.indexOf('>');
		finN = dataTemp.indexOf('</a>');
		inicioI = dataTemp.indexOf('<input');
                //alert(dataTemp);
		finI = dataTemp.indexOf('/>');
		finP = dataTemp.indexOf('<br />');
                //finP = dataTemp.indexOf('</td>');
		nombre = dataTemp.substring(inicioN+1, finN);
		divA = dataTemp.substring(0,finN+4);
		divI = dataTemp.substring(inicioI,finI+2);
		//alert("nombre:"+nombre);
		//alert("divA: "+divA);
		//alert("divI: "+divI);
		inner= "";
		extension = funcExtension2(nombre);
                
                if(extension=='pdf'|| extension=='PDF'){
                        //check = '<input type="checkbox" name="listaAApdf"  value="'+nombre+'" /> ';
                        //inner = '<td>' +divA+ divI+ check+'</td>' ;
                        inner = "<tr><td width='250px' style='font-size: 11px;'>" + divA+ divI +"</td>";
                }else{
                        inner = "<tr><td width='250px' style='font-size: 11px;'>" + divA+ divI+ " </td> ";			    		
                }
		divPanelArchivos+=inner;

		dataTemp = dataTemp.substring(finP+6);
    }
    
    
        if (divPanelArchivos!=''){
           divPanelArchivos = "<table width='380px' border=1 cellspacing=0 cellpadding=2 bordercolor='#EAEFF5'><tr style='background-color:#E9E9E9'><td><strong>Archivo</strong></td><td align='center'></td>"  + divPanelArchivos +  "</table>";
            
        }
            
        
	return divPanelArchivos;
	
};

var contador2 = function(String){
var i = 0;
var counter = 0;
while (i != -1)
{
var i = String.indexOf('</a>',i);
if (i != -1)
{
i++;
counter++;
}
}
return counter;			
};


var funcExtension2=function(miString){	
    var extensionTemp = "";
    var extension= "";
    for (i=miString.length-1;i+1>0;i--) {
        if(miString.charAt(i)=='.'){	     
        	break;
	    }
        extensionTemp +=miString.charAt(i); 
    }			
	for (i=extensionTemp.length-1;i+1>0;i--) {
        extension +=extensionTemp.charAt(i); 
    }			     
		return extension;
};
