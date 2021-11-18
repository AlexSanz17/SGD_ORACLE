<?xml version="1.0" encoding="UTF-8" ?>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://ajaxtags.org/tags/ajax" prefix="ajax"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es">
<head>
<meta http-equiv="Pragma" content="no-cache" />
<title>Tramite Documentario</title>
<style type="text/css">
@import "js/dojo/dijit/themes/soria/soria.css";
</style>
      <script type="text/javascript" src="js/dojo/dojo/dojo.js" djConfig="parseOnLoad:true, isDebug: false"></script>
<script type="text/javascript" src="js/siged/upload.js"></script>
<script type="text/javascript">
         dojo.require("dojo.io.iframe");
      </script>
      <link rel="stylesheet" type="text/css" href="css/estilo.css">
<script language="javascript" src="js/form.js"> </script>
<script language="Javascript" src="js/general.js"></script>
      <link rel="stylesheet" type="text/css" media="all" href="css/calendar-green.css">
<script type="text/javascript" src="js/calendar.js"></script>
<script type="text/javascript" src="js/calendar-es.js"></script>
<script type="text/javascript" src="js/calendar-setup.js"></script>

<script type="text/javascript" src="./js/prototype-1.4.0.js"></script>
<script type="text/javascript" src="./js/scriptaculous.js"></script>
<script type="text/javascript" src="./js/overlibmws.js"></script>
<script type="text/javascript" src="./js/ajaxtags-1.2-beta2.js"></script>
<link rel="stylesheet" type="text/css" href="./css/ajaxtags.css" />
<link rel="stylesheet" type="text/css" href="./css/displaytag.css" />

<script type='text/javascript' src='./dwr/engine.js'> </script>
<script type='text/javascript' src='./dwr/util.js'> </script>
<script type='text/javascript' src='./dwr/interface/toolDwr.js'></script>
<script type='text/javascript' src='./dwr/interface/Motivo.js'> </script>
<script type='text/javascript' src='js/submotivo.js'></script>
<script type='text/javascript' src='./dwr/interface/Suministro.js'> </script>
<script type='text/javascript' src='js/suministro.js'></script>


<style type="text/css">

body {
	background-color: #ffffff;
}

.barra {
	scrollbar-3dlight-color: #CCCCCC;
	scrollbar-arrow-color: #568BBF;
	scrollbar-base-color: #C3D5E9;
	scrollbar-darkshadow-color: #666666;
	scrollbar-face-color: ;
	scrollbar-highlight-color: #669BCD;
	scrollbar-shadow-color: #999999;
}

div.falso {
	position: absolute;
	top: -49px;
	left: 245px;
	z-index: 0;
	width: 14px;
	height: 3px;
}

input.file {
	position: relative;
	text-align: left;
	filter: alpha(opacity :   0);
	opacity: 0;
	z-index: 1;
	top: -49px;
	left: 225px;
}

</style>
<script type="text/javascript">
                 
         var gIdNroIdentif    <s:if test="cargarData=='ok'">= <s:property value="idtipoid" /></s:if>
         ;

         function UpdateInfoProcess()
         {
            var idproceso = dwr.util.getValue("idproceso");

            toolDwr.ObtenerInfoProcess(idproceso, function(data)
            {
               dwr.util.setValue("strResponsable", data.resposable);
               dwr.util.setValue("strUnidad", data.unidad);
            }
         );


            document.forms[1].ocultar.value = "NO"
         }

         function UpdateInfoCliente()
         {
            // alert(" holaa 3") ;

            var nroidentificacion = dwr.util.getValue("nroidentificacion"); 
			   //alert("nroidentificacion:"+nroidentificacion+" gIdNroIdentif:"+gIdNroIdentif);
            toolDwr.ObtenerInfoCliente(nroidentificacion, gIdNroIdentif, function(data)
            {
                // alert("ObtenerInfoCliente : "+data.idcliente) ;  
               dwr.util.setValue("idcliente", data.idcliente);
               dwr.util.setValue("strRazonSocial", data.razonsocial);
               dwr.util.setValue("strRepresentanteLegal", data.representantelegal);
               dwr.util.setValue("strDireccionPrincipal", data.direccionp);
               dwr.util.setValue("iddepartamento", data.iddepartmento);
               dwr.util.setValue("departamento", data.departmento);
               dwr.util.setValue("idprovincia", data.idprovincia);
               dwr.util.setValue("provincia", data.provincia);
               dwr.util.setValue("iddistrito", data.iddistrito);
               dwr.util.setValue("distrito", data.distrito);
               dwr.util.setValue("strTelefonoCliente", data.telefono);
               dwr.util.setValue("strCorreoCliente", data.correo);
            }
         );

            document.getElementById("cliente1").style.display = "block";
            if (gIdNroIdentif == 1)
               document.getElementById("cliente2").style.display = "block";
            document.getElementById("cliente3").style.display = "block";
            document.getElementById("cliente4").style.display = "block";
            document.getElementById("cliente5").style.display = "block";
            document.getElementById("cliente6").style.display = "block";
            document.getElementById("cliente7").style.display = "block"; 
            mostrar("cliente");
         }

         function UpdateInfoConcesionario()
         {
            var ruc = dwr.util.getValue("strRUC");

            toolDwr.ObtenerInfoConcesionario(ruc, function(data)
            {
               dwr.util.setValue("idcorrentista", data.idconcesionario);
               dwr.util.setValue("correntista", data.razonsocial);
               dwr.util.setValue("strDireccion", data.direccion);
               dwr.util.setValue("strCorreoConcesionario", data.correo);
            }
         );
         }
      </script>

<script type="text/javascript">
         function limpiarProvDist() {
            document.getElementById("provincia").value="";
            document.getElementById("idprovincia").value="";
            document.getElementById("distrito").value="";
            document.getElementById("iddistrito").value="";
         }

         function limpiarDist() {
            document.getElementById("distrito").value="";
            document.getElementById("iddistrito").value="";
         }

         function limpiarSubmot() {
            document.getElementById("nsubmotivo").value="";
            document.getElementById("idsubmotivo").value="";
         }
      </script>


<script language="JavaScript">
         function Abrir_ventanaBuscar (pagina) {
            var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=760, height=530, top=50, left=200";
            window.open(pagina,"",opciones);
         }

         function Abrir_pagina (pagina) {
            var opciones="location=mainFrame";
            window.open(pagina,"",opciones);
         }

         function putTextOnTextBox(textToPut){
            document.all.reciveTheText.value = textToPut;
         }
         function regresar(){
            history.back();
         }

         function openBoxNroIdentif(id) {
            gIdNroIdentif = id;
            document.getElementById("nroidentificacion").value = "";
            document.getElementById("cliente1").style.display = "none";
            document.getElementById("cliente2").style.display = "none";
            document.getElementById("cliente3").style.display = "none";
            document.getElementById("cliente4").style.display = "none";
            document.getElementById("cliente5").style.display = "none";
            document.getElementById("cliente6").style.display = "none";
            document.getElementById("cliente7").style.display = "none";
         }
      </script>

<script>
         function mostrarOcultarTablas(id){
         mostrado=0;
         elem = document.getElementById(id);
         if(elem.style.display=='block')mostrado=1;
         elem.style.display='none';
         if(mostrado!=1)elem.style.display='block';
         }

        function mostrar(id){
         var i = 0;
         var elem ;

            do{

                elem = document.getElementById(id+""+i)   ;
                if(elem!=null){

               // alert('mostrando '+id+""+i) ;
                elem.style.display='block';
                }
                i++ ;

                //if(i==3)elem = null ;

            }while(elem!=null) ;
              //alert('mostrando se queda en '+id+""+i) ;
         }

         function ocultar(id){
         var i = 0;
         var elem ;

            do{

                elem = document.getElementById(id+""+i)   ;

                if(elem!=null){
                  // alert('ocultando '+id+""+i) ; 
                   elem.style.display='none'; 

                }

                i++ ;
               // if(i==3)elem = null ;
            }while(elem!=null) ;
          //  alert('ocultando se queda en '+id+""+i) ;
         }


         function pasar(i){  
            document.location.href="/siged/NuevoDocumento_loadExpediente.action?iIdExp="+i; 
           
          }

 var campos ;
  
function  cargarPlantilla(){
      //alert(" 1 ") ;
      var idtipodocumentox = dwr.util.getValue("idtipodocumento");
      var asunto = dwr.util.getValue("asunto");
      if(!esVacio(asunto)){
         alert('Debe ingresar un Asunto');
         return ;
      }
      if(!esVacio(idtipodocumentox)){
     // alert(" 2 ") ;
      toolDwr.ObtenerlistaCampos(idtipodocumentox, function(data)
       {
         //  data.razonsocial;
         //alert("length : "+data.length );
         campos = data ;
          //
         //  alert(" 3 ") ;
         dwr.util.removeAllRows("tabla", { filter:function(tr) { 
            return true ; //(tr.id != "campo"&&tr.id != "plano1" && tr.id != "plano2"&&tr.id != "plano3"&& tr.id != "plano4"&& tr.id != "plano5"&& tr.id != "plano6");
          }});
          // alert("hasta aca");
         /* */
          var cellFuncs =
             [
               
                function(data) { return data.descripcion; } ,
                function(data) {
                   if(data.tipo=="TX"){
                      return "<input type='text' class='cajaMontoTotal' id='valor"+data.idcampo+"' name='valor"+data.idcampo+"' value='' />";
                   }else if (data.tipo=="TA"){  
                      return "<textarea  class='cajaMontoTotal' id='valor"+data.idcampo+"' name='valor"+data.idcampo+"' cols='50' rows='10' wrap='on'></textarea>  ";
                   }


                } /*,
                function(data) {
                   if (data.tipo=="TA"){
                      return "<input type='button'  onclick='alert(document.getElementById(\"valor"+data.idcampo+"\").value)' />";
                   }
                } */
             ];
           // alert(" 4 ") ;
           // var cellFuncs2 =
           //  [
           //     function(data) { return data; } ,
           //     function(data) { return data; }
           //  ];


                var cellFunction =  function(options) {
                   var td = document.createElement("td");
                   //alert("afuera  "+options.cellNum+" con data"+options.data  ); 
                   if(options.cellNum==0){ 
                        //alert("entro  "+options.cellNum+" con data"+options.data  );
                      td.width=83;
                   }
                   //var index = 255 - (options.rowIndex * 50);
                   //td.style.backgroundColor = "rgb(" + index + ",255,255)";
                   //td.style.fontWeight = "bold"; 
                   return td;
                 } ;  

               
             dwr.util.addRows("tabla" , data, cellFuncs ,{  cellCreator:cellFunction  , escapeHtml:false  } );
            // dwr.util.addRows("tabla" , [""], cellFuncs2 ,{ escapeHtml:false } );

           // alert(" 5 ") ;
       }
     );

      toolDwr.ObtenerInfoNumeracion(idproceso, idtipodocumentox, function (data){		 

    	  dwr.util.setValue("nroDocumento", data.numero ); 
    	      
      });

        }else{
          // alert(" idtipodocumento vacio ") ;
        }

       // alert(" 6 ") ;
}

function  cargarCamposPlantilla(){
      //alert(" 1 ") ;
      var idtipodocumentox = dwr.util.getValue("idtipodocumento");
      var asunto = dwr.util.getValue("asunto");
      if(!esVacio(asunto)){
         alert('Debe ingresar un Asunto');
         return ;
      }
      if(!esVacio(idtipodocumentox)){
     // alert(" 2 ") ;
      toolDwr.ObtenerlistaCampos(idtipodocumentox, function(data)
       {
         //  data.razonsocial;
         //alert("length : "+data.length );
         campos = data ;});

      }  


}

function enviarArchivo(){
    
    document.forms[1].action = "/siged/NuevoDocumento_enviarArchivo.action";
    document.forms[1].submit();
     
}



 

function Abrir_ventanaId ( pagina ) {
var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes, width=800, height=700, top=50, left=50";

var idtipodocumentox = dwr.util.getValue("idtipodocumento");
 if(!esVacio(idtipodocumentox)) {

     data ="?idtipodocumento="+idtipodocumentox ;
    <s:if test="idDocumento!=null">
       data =  data +"&idDocumento="+<s:property value="idDocumento" /> ;
    </s:if>
    var idprocesox = dwr.util.getValue("idproceso");
    if(! esVacio(idprocesox) ){
       data =  data +"&idproceso="+idprocesox;
    }

    if(campos!=null ) {
        // alert(campos.length);
       for(var x=0;x<campos.length;x++  ){
          var campo = campos[x] ;
          var valor = document.getElementById("valor"+campo.idcampo).value ;
          if(!esVacio(valor)){
             data =data+ "&valor"+campo.idcampo+"="+valor ;
          }
              
       }
    }

     // alert(data);
    // decomentar para base 64
    //data =  encode64(data) ;
    //pagina = pagina+'?data='+data ;
    pagina= pagina+data ;
    window.open(pagina,"",opciones);
 }else {
     alert("debe ingresar un tipo de documento");
 }

}


function subir() {

           // alert(document.forms[0].file)
            document.forms[1].action = '/siged/NuevoDocumento_upload.action' ;
            document.forms[1].submit();
         }

function refrescar () {   
	alert('<s:property value ="mensaje" />'); 
   window.parent.location.href =  '/siged/pendientes.action' ;  

}

  
function guardarPendiente () {

	var idtipodoc = document.getElementById("idtipodocumento") ;    
//	alert("idtipodoc:"+idtipodoc.value);  
    document.forms[1].action = '/siged/NuevoDocumento_actualizarPendiente.action' ;
    document.forms[1].submit(); 

}
          
</script>




<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<style type="text/css">
<!--
body {
	background-color: #ffffff;
}

.barra {
	scrollbar-3dlight-color: #CCCCCC;
	scrollbar-arrow-color: #568BBF;
	scrollbar-base-color: #C3D5E9;
	scrollbar-darkshadow-color: #666666;
	scrollbar-highlight-color: #669BCD;
	scrollbar-shadow-color: #999999;
}
 
 
         -->
 
      
          <%--
         <sdfgdf:if test="map('RC1')">
         <tabla datos usuario>
     
         sdfkjsdh fkjsdfhl skdjfh sldkfj

         <tabla datos usuario>
         </dfgdfgs:if>
            
          <sdfgdfg:if test="map('RC2') " >
         <tabla data informacion doicumento >
           
         sdfkjsdh fkjsdfhl skdjfh sldkfj
       
         <tabla datos usuario>
         </sdfgdfg:if>

            RECUSOS    RC1   Recurso del mantenimienot tal tal 1
            RECUSOS    RC2   Recurso del mantenimienot tal tal 2
                                  
            usuario      rol     privilegio
            mp           mp       rc1
rc2
           --%>

           
</style>

</head>

<body class="barra"
	onload="
         showUploadFiles(2);
         // alert('nocargardatawwwwwwww ');
   <s:if test="ocultar!='NO'">    
      ocultar('dato');   
   </s:if>
   <s:if test="not (cargarData=='ok')">
     // alert('nocargardata');
   </s:if>
   <s:if test="cargarData=='ok'">
        cargarCamposPlantilla() ;
      // alert('cargardata');        
       var idtdoc = document.getElementById('idtipodocumento') ;
 		//alert(idtdoc.value);     
       if(idtdoc!=null&&idtdoc.value!=''){
 			//cargarPlantilla();  
		}

		var idclient = document.getElementById('idcliente') ; 
		 //alert('holaaa'+idclient+idclient.value);
		if(idclient!=null&&idclient.value!=''){
         //alert('holaaa 222');
 			UpdateInfoCliente(); 
		}

      /*
		var rucc = document.getElementById('strRUC') ; 
		if(rucc!=null&&rucc.value!=''){
 			UpdateInfoConcesionario(); 
		}
		 */
		var idprocc = document.getElementById('idproceso') ; 
		if(idprocc!=null&&idprocc.value!=''){   
 			UpdateInfoProcess(); 
		}
		
     
   </s:if>

   <s:if test="cerrar=='ok'">
      refrescar();
   </s:if>
    "
    >         

<table width="100%">
	<tr>
		<td height="14" colspan="3"></td>
	</tr>
	<tr>
		<td height="20" colspan="3" class="titulo">
                  <table width="99%" border="0" height="20" align="center" bgcolor="#A2C0DC">
			<tr>
				<td align="left" class="titulo">Edicion Documento</td>
			</tr>
		</table>
		</td>
	</tr>
	<tr align="center">
		<td width="1%" align="center">&nbsp;</td>
               <td width="99%" colspan="2" align="left">

    	         <img src="images//regresar.bmp" border="0" onClick="regresar()" alt="Regresar"/>
    	         
                 <img id="<s:property value="objDD.iIdDocumento"/>" alt="Nuevo Documento" onClick="Abrir_ventanaId('/siged/doPlantilla_verPrevio.action')" src="images/xx.bmp" style="cursor:hand"/>

                 <img src="images/guardar.bmp" border="0" onClick=" guardarPendiente(); " alt="Registrar" style="cursor:hand" />
                <%-- NO DEFINIDO
                 <img src="images/impAlta.bmp" border="0" onClick="location.href='../../pages/bandeja/nuevoArch.html'" alt="Importancia Alta"/>&nbsp;&nbsp;
                 <img src="images/impBaja.bmp" border="0" onClick="location.href='../../pages/bandeja/nuevoArch.html'" alt="Importancia Baja"/>&nbsp;&nbsp;
                 --%>
  

                 <s:form id="frmUploadFile2" action="doUploadFile" method="POST" enctype="multipart/form-data">
                   <div style="position:relative;">
                      <input name="upload" type="file" class="file" size="1" onchange="uploadIt(2)" />
                      <div class="falso">
                         <img src="images/adjunto.bmp" align="left" border="0" alt="Adjuntar Archivo">
                      </div>
			</div>
                </s:form>     

                   <%-- FILE ANTIGUO
                    <div style="position:relative;">
                      <input name="upload" type="file" class="file" onChange="subir(); " size="1">
                      <div class="falso"> <img src="images/adjunto.bmp" align="left" border="0" alt="Adjunto">
                      </div>
                    </div> 
                    --%>

             </td>
	</tr>


	<tr>
		<td colspan="1" width="50"></td>
		<td height="16" colspan="4" align="left" class="plomo">

		<div id="divShowFile2"></div>

		<%--  LISTA ANTIGUO
                  <s:if test="#session.uploaded_list != null">
                  <s:iterator value="#session.uploaded_list" id="arc">
                     <s:if test="#arc.estadodigitalizacion == 'Y'">
                        <s:url action="qasFileDownload" id="lnkArchivo" includeParams="get">
                              <s:param name="idArchivo">${arc.idarchivo}</s:param>
                              <s:param name="tipo">Y</s:param>
                           </s:url>
                           <a href="javascript:Abrir_pagina('${lnkArchivo}')"><s:property value="sNombreReal"/></a>
                     </s:if>
                     <s:else>
                        <a href='upload/<s:property value="#arc.nombre"/>' target='_blank'><s:property value="#arc.nombre"/></a><br>
                     </s:else>
                  </s:iterator>
               </s:if> 

                --%>
               </td>
	</tr>
	<tr>
		<td></td>
               <td height="16" colspan="4" align="left" class="plomo">
                </td>
	</tr>
     <s:form action="doRegistrar" method="POST" name="frmNewDoc"  enctype="multipart/form-data"  >

		<s:textfield name="ocultar" cssStyle="display:none" />

        <input type="text" name="idDocumento" value="<s:property value="archivopendiente.documento.iddocumento"/>"   style="display:none"/>
        <s:textfield name="archivopendiente.idarchivopendiente"  cssStyle="display:none" />

		<tr>
			<td height="14" colspan="3">
                  <table width="95%"  border="1" align="center" bordercolor="#669BCD" bgcolor="#BFD9F1">
				<tr>
					<td height="75">
					<table width="98%" height="97" align="center" border="0">
						<tr>
                                 <td width="2%" >
                                    <s:textfield name="objDD.iIdExpediente" cssStyle="display:none" />
                                    <s:textfield name="objDD.iIdResponsable" cssStyle="display:none" />
                                 </td>
							<td width="18%" height="5"></td>
							<td width="17%"></td>
							<td width="15%"></td>
							<td width="45%"></td>
							<td width="3%"></td>
						</tr>
						<tr>
							<td></td>
							<td align="left">Referencia</td>
                                 <td><s:textfield name="objDD.strReferencia" cssClass="cajaMontoTotal" size="50" /></td>
                                 <td><img onClick="javascript:Abrir_ventanaBuscar('/siged/doLoadSearchExpediente.action')" src="images/memos.gif" border="0"/></td>
							<td></td>
						</tr>
						<tr>
							<td></td>
							<td height="16" align="left">Proceso</td>
                                 <td colspan="3" class="label">
                                    <s:textfield id="proceso" name="proceso" cssClass="cajaMontoTotal" size="75" onblur="UpdateInfoProcess() ; mostrar('dato') "/>
                                    <span id="indicatorProc" style="display:none;"><img src="./images/indicator.gif"/></span>
                                    <s:textfield id="idproceso" name="idproceso" cssStyle="display:none"/>
                                    <ajax:autocomplete
                                       source="proceso"
                                       target="idproceso"
								baseUrl="./autocompletar.view?metodo=ObtenerProceso"
                                       className="autocomplete"
                                       indicator="indicatorProc"
                                       minimumCharacters="1"
                                       parser="new ResponseXmlToHtmlListParser()" />

							</td>
							<td></td>
						</tr>
						<tr id="dato0">
							<td></td>
							<td height="16" align="left">Area Destino</td>
                                 <td colspan="3" align="left" class="label">
                                    <s:textfield name="strUnidad" cssClass="cajaMontoTotal" size="50" />
                                 </td>
							<td align="left"></td>
						</tr>
						<tr id="dato1">
							<td></td>
							<td height="16" align="left">Destinatario</td>
                                 <td colspan="3" class="label">
                                    <s:textfield name="strResponsable" cssClass="cajaMontoTotal" size="35" />
                                 </td>
							<td></td>
						</tr>
						<tr id="dato2">
                                 <td colspan="6" height="15"><hr></td>  
						</tr>
						<tr id="dato3">
							<td colspan="6" height="15" class="label">DOCUMENTO</td>
						</tr>
						<tr id="dato16">
							<td height="15" class="label"></td>
							<td colspan="10" height="15" class="label" style="display: none">
							<s:iterator value="listaParametros">
                                         <input  type="radio" name="marcaDeAgua"  value="<s:property value="valor"/>" <s:if test="valor==marcaDeAgua" > checked="checked" </s:if> > <s:property value="descripcion"/>
                                     </s:iterator>

                                 </td>
						</tr>
						<tr id="dato4">
							<td></td>
							<td height="16" align="left">Tipo Documento</td>
                                 <td align="left" width="17%" class="label">
                                    <s:textfield id="tipodocumento" name="tipodocumento" onblur="cargarPlantilla();"  cssClass="cajaMontoTotal" size="20" />
                                    <span id="indicatortipoDoc" style="display:none;"><img src="./images/indicator.gif" /></span>


				 					<s:if test="archivopendiente==null">  
				                       <s:textfield id="idtipodocumento"  name="idtipodocumento" value="" cssStyle="display:none" />
				                    </s:if>
				                    <s:else> 

				                    <input type="text" name="idtipodocumento" value="<s:property value="archivopendiente.plantilla.idplantilla"/>" id="idtipodocumento" style="display:none"/>
				                      </s:else>

                                             
                                    <ajax:autocomplete 
                                       source="tipodocumento"
								target="idtipodocumento"
								baseUrl="./autocompletar.view?metodo=ObtenerTipoDoc"
                                       className="autocomplete"
                                       indicator="indicatortipoDoc"
                                       minimumCharacters="1"
                                       parser="new ResponseXmlToHtmlListParser()" />
							</td>
							<td align="left" class="label"></td>
							<td align="left" class="label"></td>
							<td></td>
                              </tr> <%--   
                              <tr  id="dato5">
                                 <td></td>
                                 <td height="16" align="left">Nro. Documento</td>
                                 <td align="left" width="17%" class="label">
                                    <s:textfield  id="nroDocumento"  name="objDD.strNroDocumento" cssClass="cajaMontoTotal" size="20"  onkeypress="return false;" onfocus="return false" />
                                 </td>
                                 <td align="left" class="label"></td>
                                 <td align="left" class="label"></td>
                                 <td></td>
                              </tr>
                              <tr  id="dato6" >
                                 <td></td>
                                 <td height="16" align="left">Nro Folios</td>
                                 <td align="left" width="17%" class="label">  
                                    <s:textfield name="objDD.iNroFolios" value="1" cssClass="cajaMontoTotal"  onkeypress="return false;" onfocus="return false"  size="20" />
                                 </td>
                                 <td align="left" ></td>
                                 <td align="left" width="45%" class="label"></td>
                                 <td></td>
                              </tr>
                              --%>
						<tr id="dato7">
							<td></td>
							<td height="16" align="left">Asunto</td>
                                 <td colspan="3" align="left" class="label">
                                    <s:textfield name="objDD.strAsunto" cssClass="cajaMontoTotal" size="60" />
                                 </td>
							<td></td>
						</tr>
						<tr id="dato8">
							<td></td>
							<td height="16" align="left">Fecha Documento</td>
                                 <td colspan="3" align="left" class="label">
                                    <s:textfield name="objDD.strFechaDocumento" id="fechadocumento" cssClass="cajaMontoTotal" size="25" />
                                    <input alt="Calendario" class="cajaFecha" id="calfechadocumento" value="..." type="button">
                                 </td>
							<td></td>
						</tr>

						<tr id="dato21">

							<td colspan="6">


							<table width="100%" align="center" border="0">
								<tbody id="tabla">



									<s:if test="archivopendiente!=null">


                <s:iterator value="archivopendiente.valorcampoList" status="" id="idcampo" >

											<tr style="">

												<td width="83">
						<div id="descripcion"> <s:property value="campo.descripcion" />  </div>
												</td>

                     <td id="valor"  class="titulo"  > 
                         <s:if test="campo.tipo=='TX'" >
                               <input class="cajaMontoTotal" type="text" id="valor<s:property value="campo.idcampo" />" name="valor<s:property  value="campo.idcampo" />"  value="<s:property value="valor" />" />
                         </s:if>          

                         <s:elseif test="campo.tipo=='TA'">          
                               <textarea class="cajaMontoTotal" id="valor<s:property value="campo.idcampo" />" name="valor<s:property  value="campo.idcampo" />"   cols='50' rows='10' wrap='on'><s:property value="valor" /></textarea>
                         </s:elseif>

                        </td>
												<td colspan="4"></td>
											</tr>

										</s:iterator>
									</s:if>



								</tbody>
							</table>



							</td>
						</tr>


						<tr id="dato9">
                                 <td colspan="6" height="15"><hr></td>
						</tr>
						<tr id="dato10">
							<td colspan="6" height="15" class="label">CLIENTE</td>
						</tr>
						<tr id="dato11">
							<td></td>
							<td height="16" align="left" valign="top">Tipo Doc Identidad</td>
                                 <td align="left" class="label" colspan="5">
                                    <s:if test="objDD.iIdExpediente != null">
								<s:property value="tipoidentificacion" />
								<s:textfield name="idtipoidentificacion" cssStyle="display:none" />
                                    </s:if>
                                    <s:else>
								<s:iterator value="lstRadio">
                                          <input  type="radio" value="<s:property value="idtipoidentificacion"/>" 
												 name="idtipoidentificacion" onclick="openBoxNroIdentif(this.value)" 
												<s:if test="idtipoidentificacion==idtipoid">checked="checked"</s:if> 
												  >
									<s:property value="nombre" />
								</s:iterator>
								<!--<s:radio  labelposition="top" list="lstRadio" key="idtipoidentificacion" label="nombre" name="idtipoidentificacion" requiredposition="no"  />-->

                                    </s:else>
                                 </td>
							<td></td>
						</tr>
						<tr id="dato12">
							<td></td>
							<td height="16" align="left" valign="top">Nro Doc Identidad</td>
							<td id="nroidentif" colspan="3" align="left" class="label">
                                    <s:textfield id="nroidentificacion" name="nroidentificacion" cssClass="cajaMontoTotal" size="20" onblur="UpdateInfoCliente()" />
                                    <s:textfield name="idcliente" cssStyle="display:none" />
                                 </td>
							<td></td>
						</tr>

						<s:if test="objDD.iIdExpediente != null">
							<tr id="cliente1">
						</s:if>
						<s:else>
							<tr id="cliente1" style="display: none">
						</s:else>
						<td></td>
						<td height="16" align="left">Nombre/Raz&oacute;n Social</td>
                                 <td colspan="3" align="left" class="label">
                                    <s:textfield name="strRazonSocial" cssClass="cajaMontoTotal" size="50" />
                                 </td>
						<td></td>

						<s:if test="objDD.iIdExpediente != null">
							<tr id="cliente2">
						</s:if>
						<s:else>
							<tr id="cliente2" style="display: none">
						</s:else>
						<td></td>
						<td height="16" align="left">Representante Legal</td>
                                 <td colspan="3" align="left" class="label">
                                    <s:textfield name="strRepresentanteLegal" cssClass="cajaMontoTotal" size="50" />
						</td>
						<td></td>
						</tr>
						<s:if test="objDD.iIdExpediente != null">
							<tr id="cliente3">
						</s:if>
						<s:else>
							<tr id="cliente3" style="display: none">
						</s:else>
						<td></td>
						<td height="16" align="left">Direcci&oacute;n</td>
                                 <td colspan="3" align="left" class="label">
                                    <s:textfield name="strDireccionPrincipal" cssClass="cajaMontoTotal" size="50" />
						</td>
						<td></td>
						</tr>
						<s:if test="objDD.iIdExpediente != null">
							<tr id="cliente4">
						</s:if>
						<s:else>
							<tr id="cliente4" style="display: none">
						</s:else>
						<td></td>
						<td height="16" align="left">Ubigeo</td>
                                 <td colspan="3" align="left" class="label">
                                    Departamento:
                                    <s:textfield id="departamento" name="departamento" cssClass="cajaMontoTotal" size="20" onkeypress="limpiarProvDist()" />
                                    <span id="indicatorDep" style="display:none;"><img src="./images/indicator.gif" /></span>
                                    <s:textfield id="iddepartamento" name="iddepartamento" cssStyle="display:none" />
                                    <ajax:autocomplete
                                       source="departamento"
                                       target="iddepartamento"
							baseUrl="./autocompletar.view?metodo=ObtenerDepartamento"
                                       className="autocomplete"
                                       indicator="indicatorDep"
                                       minimumCharacters="1"
                                       parser="new ResponseXmlToHtmlListParser()" />

                                    Provincia:
                                    <s:textfield id="provincia" name="provincia" cssClass="cajaMontoTotal" size="20" onkeypress="limpiarDist()" />
                                    <span id="indicatorProv" style="display:none;"><img src="./images/indicator.gif" /></span>
                                    <s:textfield id="idprovincia" name="idprovincia" cssStyle="display:none" />
                                    <ajax:autocomplete
                                       source="provincia"
                                       target="idprovincia"
							baseUrl="./autocompletar.view?metodo=ObtenerProvincia"
                                       className="autocomplete"
                                       indicator="indicatorProv"
							minimumCharacters="1"
							parameters="iddepartamento={iddepartamento}"
                                       parser="new ResponseXmlToHtmlListParser()" />

                                    Distrito:
                                    <s:textfield id="distrito" name="distrito" cssClass="cajaMontoTotal" size="20" />
                                    <span id="indicatorDist" style="display:none;"><img src="./images/indicator.gif" /></span>
                                    <s:textfield id="iddistrito" name="iddistrito" cssStyle="display:none" />
                                    <ajax:autocomplete
                                       source="distrito"
                                       target="iddistrito"
							baseUrl="./autocompletar.view?metodo=ObtenerDistrito"
                                       className="autocomplete"
                                       indicator="indicatorDist"
                                       minimumCharacters="1"
                                       parameters="idprovincia={idprovincia}"
                                       parser="new ResponseXmlToHtmlListParser()" />
                                 </td>
						<td></td>
						</tr>
						<s:if test="objDD.iIdExpediente != null">
							<tr id="cliente5">
						</s:if>
						<s:else>
							<tr id="cliente5" style="display: none">
						</s:else>
						<td></td>
						<td height="16" align="left">Direcci&oacute;n Procesal</td>
                                 <td colspan="3" align="left" class="label">
                                    <s:textfield name="objDD.strDireccionAlternativa" cssClass="cajaMontoTotal" size="50" />
                                 </td>
						<td></td>
						</tr>
						<s:if test="objDD.iIdExpediente != null">
							<tr id="cliente6">
						</s:if>
						<s:else>
							<tr id="cliente6" style="display: none">
						</s:else>
						<td></td>
                                 <td height="16" align="left">TelÃ©fono</td>
                                 <td colspan="3" align="left" class="label">
                                    <s:textfield name="strTelefonoCliente" cssClass="cajaMontoTotal" size="20" />
						</td>
						<td></td>
						</tr>
						<s:if test="objDD.iIdExpediente != null">
							<tr id="cliente7">
						</s:if>
						<s:else>
							<tr id="cliente7" style="display: none">
						</s:else>
						<td></td>
                                 <td height="16" align="left">Correo ElectrÃ³nico</td>
                                 <td colspan="3" align="left" class="label">
                                    <s:textfield name="strCorreoCliente" cssClass="cajaMontoTotal" size="50" />
                                 </td>
						<td></td>
						</tr>
						<tr id="dato13">
							<td colspan="6" height="15"></td>
						</tr>

						<%--
                              <tr id="dato14">
                                 <td colspan="6" height="15" class="label">CONCESIONARIO</td>
                              </tr>
                              <tr id="dato15" >
                                 <td></td>
                                 <td height="16" align="left">RUC</td>
                                 <td colspan="3" align="left" class="label">
                                    <s:textfield id="strRUC" name="strRUC" cssClass="cajaMontoTotal" size="20" onblur="UpdateInfoConcesionario()" />
                                 </td>
                                 <td></td>
                              </tr>
                              <tr id="dato16" >
                                 <td ></td>
                                 <td height="16" align="left">RazÃ³n Social</td>
                                 <td colspan="3" class="label">
                                    <s:textfield id="correntista" name="correntista" cssClass="cajaMontoTotal" size="20" />
                                    <s:textfield id="idcorrentista" name="idcorrentista" cssStyle="display:none" />
                                 </td>
                                 <td></td>
                              </tr>
                              <tr  id="dato17" >
                                 <td></td>
                                 <td height="16" align="left">DirecciÃ³n</td>
                                 <td colspan="3" align="left" class="label">
                                    <s:textfield name="strDireccion" cssClass="cajaMontoTotal" size="50" />
                                 </td>
                                 <td></td>      
                              </tr>
                              <tr  id="dato18" >
                                 <td></td>
                                 <td height="16" align="left">Correo ElectrÃ³nico</td>
                                 <td colspan="3" align="left" class="label">
                                    <s:textfield name="strCorreoConcesionario" cssClass="cajaMontoTotal" size="50" />
                                 </td>
                                 <td></td>
                              </tr>
                              --%>
						<tr id="dato14">
                                 <td colspan="6" height="15"><hr></td>
						</tr>
						<tr id="dato15">
							<td></td>
							<td height="16" align="left" valign="top">Observaciones</td>
                                 <td colspan="3" align="left" class="label">
                                    <s:textarea name="objDD.strObservacion" cssClass="cajaMontoTotal" rows="6" cols="75"/>
                                 </td>
							<td></td>
						</tr>

						<tr>
							<td></td>
							<td class="titulo">&nbsp;</td>
							<td colspan="4"></td>
						</tr>
					</table>
					</td>
				</tr>
			</table>
			</td>
		</tr>
		<tr align="center">
			<td width="1%" align="center">&nbsp;</td>
               <td width="99%" colspan="2" align="left"> 

                 <img id="<s:property value="objDD.iIdDocumento"/>" alt="Nuevo Documento" onClick="Abrir_ventanaId('/siged/doPlantilla_verPrevio.action')" src="images/xx.bmp" style="cursor:hand"/>

                 <img src="images/guardar.bmp" border="0" onClick=" guardarPendiente(); " alt="Registrar" style="cursor:hand" />
                                       
                   
             </td>
		</tr>
		<tr>
			<td height="14" colspan="3"></td>
		</tr>
            </s:form>               
            
</table>

<script type="text/javascript">
         Calendar.setup({
            inputField     :    "fechadocumento", //id del campo de texto
            ifFormat       :    "%d/%m/%Y",    //formato de la fecha, cuando se escriba en el campo de texto
            button         :    "calfechadocumento"    //el id del botÃ³n que lanzarÃ¡ el calendario
         });
      </script>
</body>
</html>
