<%--
    Document   : plantilla.jsp
    Created on : 09/12/2008, 11:23:17 AM
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://ajaxtags.org/tags/ajax" prefix="ajax"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>

<s:head theme="ajax" />
<title>Tramite Documentario</title>

<script type="text/javascript">
			var djConfig = {
				isDebug:true,
				parseOnLoad:true
			};
</script>
		
	<script type="text/javascript" src="js/dojo/dojo/dojo.js"></script>	
<script type="text/javascript">
		   dojo.require("dijit.Dialog");
		   dojo.require("dijit.ProgressBar"); 
</script>
		
	<script type="text/javascript" src="js/dojo/dojo/dojo.js"></script>			
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
<script type='text/javascript' src='./dwr/interface/toolDwr.js'> </script>
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
	top: -29px;
	left: 365px;
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
	top: -29px;
	left: 335px;
}

</style>

<script language="JavaScript">
function Abrir_ventanaBuscar (pagina) {
var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=760, height=530, top=50, left=200";
window.open(pagina,"",opciones);
}

function Abrir_ventanaCargo (pagina) {
/*
var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=760, height=380, top=50, left=200";
window.open(pagina,"",opciones);
*/
//location.href="pages/tramite/cargo.html";
document.forms["formnewDoc"].submit();
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
function enviarArchivo(){

   document.forms["formnewDoc"].action = "/siged/doPlantilla_enviarArchivo.action";
   dijit.byId("dlgProgresBar").show() ;  
   document.forms["formnewDoc"].submit();

}

function sinAccion(){ 
	
	dijit.byId("dlgProgresBar").hide() ;  
}

</script>



<script type="text/javascript">
  <!--

  var keyStr = "ABCDEFGHIJKLMNOP" +
               "QRSTUVWXYZabcdef" +
               "ghijklmnopqrstuv" +
               "wxyz0123456789+/" +
               "=";

  function encode64(input) {
     input = escape(input);
     var output = "";
     var chr1, chr2, chr3 = "";
     var enc1, enc2, enc3, enc4 = "";
     var i = 0;

     do {
        chr1 = input.charCodeAt(i++);
        chr2 = input.charCodeAt(i++);
        chr3 = input.charCodeAt(i++);

        enc1 = chr1 >> 2;
        enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
        enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
        enc4 = chr3 & 63;

        if (isNaN(chr2)) {
           enc3 = enc4 = 64;
        } else if (isNaN(chr3)) {
           enc4 = 64;
        }

        output = output +
           keyStr.charAt(enc1) +
           keyStr.charAt(enc2) +
           keyStr.charAt(enc3) +
           keyStr.charAt(enc4);
        chr1 = chr2 = chr3 = "";
        enc1 = enc2 = enc3 = enc4 = "";
     } while (i < input.length);

     return output;
  }

  function decode64(input) {
     var output = "";
     var chr1, chr2, chr3 = "";
     var enc1, enc2, enc3, enc4 = "";
     var i = 0;

     // remove all characters that are not A-Z, a-z, 0-9, +, /, or =
     var base64test = /[^A-Za-z0-9\+\/\=]/g;
     if (base64test.exec(input)) {
        alert("There were invalid base64 characters in the input text.\n" +
              "Valid base64 characters are A-Z, a-z, 0-9, '+', '/',and '='\n" +
              "Expect errors in decoding.");
     }
     input = input.replace(/[^A-Za-z0-9\+\/\=]/g, "");

     do {
        enc1 = keyStr.indexOf(input.charAt(i++));
        enc2 = keyStr.indexOf(input.charAt(i++));
        enc3 = keyStr.indexOf(input.charAt(i++));
        enc4 = keyStr.indexOf(input.charAt(i++));

        chr1 = (enc1 << 2) | (enc2 >> 4);
        chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
        chr3 = ((enc3 & 3) << 6) | enc4;

        output = output + String.fromCharCode(chr1);

        if (enc3 != 64) {
           output = output + String.fromCharCode(chr2);
        }
        if (enc4 != 64) {
           output = output + String.fromCharCode(chr3);
        }

        chr1 = chr2 = chr3 = "";
        enc1 = enc2 = enc3 = enc4 = "";

     } while (i < input.length);

     return unescape(output);
  }

  //--></script>


<script type="text/javascript">

function poorman_toggle(id)
{
	var tr = document.getElementById(id);
	if (tr==null) { return; }
	var bExpand = tr.style.display == '';
	tr.style.display = (bExpand ? 'none' : '');
}
function poorman_changeimage(id, sMinus, sPlus)
{
	var img = document.getElementById(id);
	if (img!=null)
	{
	    var bExpand = img.src.indexOf(sPlus) >= 0;
		if (!bExpand)
			img.src = sPlus;
		else
			img.src = sMinus;
	}
}

function Toggle_repHeader2()
{
    poorman_changeimage('repHeader2_Img', 'images/signo_menos.gif', 'images/signo_mas.gif');
    poorman_toggle('row1');
    poorman_toggle('row2');
    poorman_toggle('row3');
}

function Toggle_repHeader1()
{
    poorman_changeimage('repHeader1_Img', 'images/signo_menos.gif', 'images/signo_mas.gif');
    poorman_toggle('trRow1');
}
function Toggle_repHeader3()
{
    poorman_changeimage('repHeader3_Img', 'images/signo_menos.gif', 'images/signo_mas.gif');
    poorman_toggle('row1');
    poorman_toggle('row2');
    poorman_toggle('row3');
}

function Toggle_repHeader3()
{
    poorman_changeimage('repHeader3_Img', 'images/signo_menos.gif', 'images/signo_mas.gif');
    poorman_toggle('trRow3');
}
function Toggle_repHeader4()
{
    poorman_changeimage('repHeader4_Img', 'images/signo_menos.gif', 'images/signo_mas.gif');
    poorman_toggle('row1');
    poorman_toggle('row2');
    poorman_toggle('row3');
}

function Toggle_repHeader4()
{
    poorman_changeimage('repHeader4_Img', 'images/signo_menos.gif', 'images/signo_mas.gif');
    poorman_toggle('trRow4');
}
function Toggle_repHeader5()
{
    poorman_changeimage('repHeader5_Img', 'images/signo_menos.gif', 'images/signo_mas.gif');
    poorman_toggle('row1');
    poorman_toggle('row2');
    poorman_toggle('row3');
}

function Toggle_repHeader5()
{
    poorman_changeimage('repHeader5_Img', 'images/signo_menos.gif', 'images/signo_mas.gif');
    poorman_toggle('trRow5');
}
function Toggle_repHeader6()
{
    poorman_changeimage('repHeader6_Img', 'images/signo_menos.gif', 'images/signo_mas.gif');
    poorman_toggle('row1');
    poorman_toggle('row2');
    poorman_toggle('row3');
}

function Toggle_repHeader6()
{
    poorman_changeimage('repHeader6_Img', 'images/signo_menos.gif', 'images/signo_mas.gif');
    poorman_toggle('trRow6');
}


var campos ;
<s:if test="archivopendiente!=null"> 
   toolDwr.ObtenerlistaCampos(<s:property value="archivopendiente.plantilla.idplantilla"/>, function(data){
      campos = data ;
   } );
</s:if>

function  cargarPlantilla(){
      //alert(" 1 ") ;
      var idtipodocumentox = dwr.util.getValue("idtipodocumento");
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
            return (tr.id != "campo"&&tr.id != "plano1" && tr.id != "plano2"&&tr.id != "plano3"&& tr.id != "plano4");
          }});
          // alert("hasta aca");
         /* */
          var cellFuncs =
             [
                function(data) { return ""; } ,
                function(data) { return data.descripcion; } ,
                function(data) {
                   if(data.tipo=="TX"){
                      return "<input type='text' id='valor"+data.idcampo+"' name='valor"+data.idcampo+"' value='' />";
                   }else if (data.tipo=="TA"){
                      return "<textarea id='valor"+data.idcampo+"' name='valor"+data.idcampo+"' cols='50' rows='10' wrap='on'></textarea>  ";
                   }


                } /*,
                function(data) {
                   if (data.tipo=="TA"){
                      return "<input type='button'  onclick='alert(document.getElementById(\"valor"+data.idcampo+"\").value)' />";
                   }
                } */
             ];
           // alert(" 4 ") ;
            var cellFuncs2 =
             [
                function(data) { return data; } ,
                function(data) { return data; }
             ];
             dwr.util.addRows("tabla" , data, cellFuncs ,{ escapeHtml:false } );
             dwr.util.addRows("tabla" , [""], cellFuncs2 ,{ escapeHtml:false } );

           // alert(" 5 ") ;
       }
     );

        }else{
          // alert(" idtipodocumento vacio ") ;
        }

       // alert(" 6 ") ;
}

function prueba (donde ){
   alert ("llamado desde "+donde ) ;
}

var data ;

function Abrir_ventanaId ( pagina ) {
var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes, width=800, height=700, top=50, left=50";

var idtipodocumentox = dwr.util.getValue("idtipodocumento");
 if(!esVacio(idtipodocumentox)) { 

     data ="?idtipodocumento="+idtipodocumentox ;
    <s:if test="archivopendiente.documento.iddocumento!=null">
       data =  data +"&idDocumento="+<s:property value="archivopendiente.documento.iddocumento" /> ;
    </s:if>
    var idprocesox = dwr.util.getValue("idproceso");
    if(! esVacio(idprocesox) ){
       data =  data +"&idproceso="+idprocesox;
    }
    //alert(campos);
    if(campos!=null ) {
        
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

function actualizarPendiente() {  
  //alert('hi12');
 var idtipodocumentox = dwr.util.getValue("idtipodocumento"); 
 if(!esVacio(idtipodocumentox)) {
     //  alert('hi12');
      document.forms[0].action = '/siged/doPlantilla_actualizarPendiente.action' ;
      dijit.byId("dlgProgresBar").show() ;   
      document.forms[0].submit();

 }else {
     alert("debe ingresar un tipo de documento");
 }
 
}

</script>
           

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
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

</style>

</head>

<body class="barra" topmargin="0" leftmargin="0" rigthmargin="0"
    
	<s:if test="cerrar!=null">   
       onload = " alert('<s:property value ="mensaje" />'); window.opener.parent.frames['mainFrame'].location.href  ='/siged/doLista.action' ;  window.close(); "
    </s:if>              
 >    
<s:form name="formnewDoc" action="doRegistrar" method="POST" enctype="multipart/form-data" >
	<table width="100%">
		<tr>
			<td height="14" colspan="3"></td>
		</tr>


    <input type="text" name="idDocumento" value="<s:property value="archivopendiente.documento.iddocumento"/>" id="iddocumento" style="display:none"/>
    <s:textfield name="archivopendiente.idarchivopendiente"  cssStyle="display:none" />

		<tr>
			<td height="20" colspan="3" class="titulo">
				<table width="99%" border="0" height="20" align="center" bgcolor="#A2C0DC">
				<tr>
					<td align="left" class="titulo">Actualizar Documento</td>
				</tr>
			</table>
			</td>
		</tr>
		<tr align="center">
			<td width="1%" align="center">&nbsp;</td>
    <td width="99%" colspan="2" align="left">

     <img id="<s:property value="archivopendiente.documento.iddocumento"/>" alt="Nuevo Documento" onClick="Abrir_ventanaId('/siged/doPlantilla_verPrevio.action')" src="images/xx.bmp" style="cursor:hand"/>

	  <img src="images/guardar.bmp" border="0" onClick=" actualizarPendiente(); " alt="Registrar" style="cursor:hand" />
  <%--
	  <img src="images/impAlta.bmp" border="0" onClick="location.href='../../pages/bandeja/nuevoArch.html'" alt="Importancia Alta"/>&nbsp;&nbsp;
	  <img src="images/impBaja.bmp" border="0" onClick="location.href='../../pages/bandeja/nuevoArch.html'" alt="Importancia Baja"/>&nbsp;&nbsp;
	     <div style="position:relative;">
                      <input name="upload" type="file" class="file" onChange="subir(); " size="1">
                      <div class="falso"> <img src="images/adjunto.bmp" align="left" border="0" alt="Adjunto">
                      </div>
        </div>

   --%>
    </td>
		</tr>
		<tr>
    <td height="14" colspan="3"> <table width="95%"  border="1" align="center" bordercolor="#669BCD" bgcolor="#BFD9F1">
				<tr>
					<td colspan="1" width="50"></td>
               <td height="16" colspan="4" align="left" class="plomo">
                 <%--    
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
					<td height="75">


					<table width="98%" height="97" align="center">
						<tbody id="tabla">
							<tr id="plano1">
								<td></td>
								<td class="titulo"></td>
								<td colspan="4"><s:actionmessage /> <s:actionerror /></td>
							</tr>
							<tr id="plano2">
                  <td width="2%" >        
                     <s:textfield name="objDD.iIdExpediente" cssStyle="display:none" />
                     <s:textfield name="objDD.iIdResponsable" cssStyle="display:none" />
                     <s:textfield name="ruta" value="edit" cssStyle="display:none" /> 
                  </td>

                  <td width="28%" height="5" >
                      Expediento Nro
                  </td>

                  <td width="17%">

                     <s:if test="archivopendiente==null">

                           <s:textfield id="idarchivopendiente" name="archivopendiente.idarchivopendiente" cssStyle="display:none"  size="20" />
									<s:if test="idDocumento!=null">
										<s:property value="documento.expediente.nroexpediente" />
									</s:if>
                     </s:if>
                     <s:else>
                        <s:property value="archivopendiente.documento.expediente.nroexpediente" />
                     </s:else>
                  </td>
								<td width="15%"></td>
								<td width="45%"></td>
								<td width="3%"></td>
							</tr>
							<tr id="plano3">
								<td></td>
								<td height="16" align="left">Tipo Documento</td>
                  <td align="left" width="17%" class="label">

                     <s:if test="archivopendiente==null">
                           <s:textfield id="tipodocumento"  onblur="cargarPlantilla();"  name="strtipodocumento" cssClass="cajaMontoTotal" size="20" />
								</s:if> <s:else>
                        <s:textfield id="tipodocumento"    onblur="cargarPlantilla();"  name="archivopendiente.strtipodocumento" cssClass="cajaMontoTotal" size="20" />
                     </s:else>  

                       
                    <span id="indicatortipoDoc" style="display:none;"> <img src="./images/indicator.gif" /> </span>

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
							</tr>

							<tr id="plano4">
								<td></td>
								<td height="16" align="left">Proceso</td>
                  <td colspan="3" class="label">

                     <s:if test="archivopendiente==null">

									<s:if test="idDocumento!=null">
										<s:property value="documento.expediente.proceso.nombre" />
									</s:if>

									<s:if test="idDocumento==null">

                              <s:textfield id="proceso" name="proceso" cssClass="cajaMontoTotal" size="40" />

                              <span id="indicatorDep" style="display:none;"><img src="./images/indicator.gif"/></span>
                              <s:textfield id="idproceso" name="idproceso"  cssStyle="display:none"/>

                               <ajax:autocomplete
                                source="proceso"
                                target="idproceso"
											baseUrl="./autocompletar.view?metodo=ObtenerProceso"
                                className="autocomplete"
                                indicator="indicatorDep"
											minimumCharacters="1"
											parser="new ResponseXmlToHtmlListParser()" />

									</s:if>

								</s:if><s:else>

                        <s:property value="archivopendiente.documento.expediente.proceso.nombre" />

                    </s:else>

                  </td>
								<td></td>
							</tr>


							<s:if test="archivopendiente!=null">
								<s:iterator value="archivopendiente.valorcampoList" status="">

									<tr style="">
										<td>&nbsp;</td>
										<td>
                        <div id="descripcion"> <s:property value="campo.descripcion" />   </div>
										</td>

                     <td id="valor"  class="titulo" >

                         <s:if test="campo.tipo=='TX'" >
                               <input type='text' id="valor<s:property  value="campo.idcampo" />" name="valor<s:property  value="campo.idcampo" />" value="<s:property value="valor" />" />
                         </s:if>

                         <s:elseif test="campo.tipo=='TA'">
                               <textarea id="valor<s:property value="campo.idcampo" />" name="valor<s:property  value="campo.idcampo" />" cols='50' rows='10' wrap='on'><s:property value="valor" /></textarea>
                         </s:elseif> 

                        </td> 
										<td colspan="4"></td>
									</tr>

								</s:iterator>

							</s:if>



							<tr id="campo" style="display: none;">
								<div id="descripcion">&nbsp;</div>
								<td id="valor" class="titulo"></td>
								<td colspan="4"></td>
							</tr>

							<tr>
								<td></td>
								<td class="titulo">&nbsp;</td>
								<td colspan="4"></td>
							</tr>
						</tbody>
              </table></td>
				</tr>
      </table></td>
		</tr>
		<tr>
			<td height="14" colspan="3"></td>
		</tr>
	</table>
</s:form>
<%--@ include file="../util/progressBar.jsp" --%>    
</body>
</html>