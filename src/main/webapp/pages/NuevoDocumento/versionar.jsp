<?xml version="1.0" encoding="UTF-8" ?>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es">
	<head>	
	    <s:url id="autoUrl" action="autocompletarProceso1">
		</s:url>
		
		<title>Versionar Documentos</title>
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>  
      <%--link rel="stylesheet" type="text/css" media="all" href="css/calendar-green.css" />
<script type="text/javascript" src="js/calendar.js"></script>
<script type="text/javascript" src="js/calendar-es.js"></script>
<script type="text/javascript" src="js/calendar-setup.js"></script--%>
		<style type="text/css">
			@import "js/dojo/dijit/themes/soria/soria.css";
			@import "css/estilo.css";
		</style>
		<script type="text/javascript">
			var djConfig = {
            	isDebug: false,
            	parseOnLoad: true
         	};
		</script>
		<script type="text/javascript" src="js/dojo/dojo/dojo.js"></script>
		<script type="text/javascript" src="js/siged/upload.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript">
			dojo.require("dijit.form.DateTextBox");
			dojo.require("dijit.form.FilteringSelect" );
			dojo.require("dojo.data.ItemFileReadStore");
			dojo.require("dijit.form.ValidationTextBox");
			dojo.require("dojo.io.iframe");
			dojo.require("dijit.Dialog");
			dojo.require("dijit.ProgressBar");  

		function  verificar (numero, ruta) {
			 {
				 //alert("ruta:"+ruta);
				 ruta  = ruta.split("\\");
				 //alert("ruta:"+ruta.length); 
				 ruta =  ruta [ruta.length-1] ;  
				// alert("ruta:"+ruta);
				 
					var	enviar =  true;

			            var camposdelForm =document.getElementById("divShowFile2");
			            var filesinput = camposdelForm.getElementsByTagName("a") ;
			             //  alert("files:" +filesinput.length); 

			          
			            var duplicatedFiles = " Los siguientes archivos ya se encuentran en el expediente: \n" ;
						var cantduplicated = 0 ; 
						var archivos= filesinput.length;
						var idarchivos =  new Array (archivos) ;  
						
						var files = filesinput  ;
						 // Comprobar de que no existen los mismos archivos en el expediente;
						var rutapadre = '<s:property value ="rutapadre" />' ;
						var uploaded = new Array(archivos) ; 

						// alert("inicio for :" );  
						//alert("rutapadre:"+rutapadre);   
						var ruta0= "" ;
						var estaEnElRepositorio = false ;
						//for(var t=0;t<files.length;t++){ 
							 
							var nuploaded = rutapadre+ruta ;   
							//alert("1 nuploaded:"+nuploaded+" estaEnElRepositorio:"+estaEnElRepositorio);   
							nuploaded = nuploaded.toUpperCase() ;  

							archivosAlf = document.getElementsByName("rutaAlfresco");
							duplicadosup = document.getElementsByName("idArchivo");
                             
							//alert("archivosAlf:"+archivosAlf.length);
							
							for(var u = 0 ; u<archivosAlf.length ;u++){ 
								 
								 archivosAlf[u].value = archivosAlf[u].value.toUpperCase(); 
	                            // alert("2 nup: "+nuploaded+" alf "+archivosAlf[u].value+" comp " +(nuploaded==archivosAlf[u].value +" estaEnElRepositorio:"+estaEnElRepositorio));  
								if(nuploaded==archivosAlf[u].value){  
									estaEnElRepositorio = true ;
									break ;
								}else { 
									estaEnElRepositorio = false ;   
								}     
							}

						//	alert("3 estaEnElRepositorio:"+estaEnElRepositorio); 
						if(estaEnElRepositorio){
			 				 //alert("se va a enviar ")
							uploadIt(2) ;
			 				
					    } else {

						    alert("Debe de subir un archivo que se encuentre en el Expediente");
						     // alert("no se va a enviar "); 
						}
					 

			      } 

		      
		

			
	      }	
		   
		 var storeTipoDocumento = new dojo.data.ItemFileReadStore({url: "autocompletarAllTipoDocumento.action"}); 
		 var store = new dojo.data.ItemFileReadStore({url: "<s:property value='autoUrl'/>?sTipoDerivacion=normal&sOpcion=derivar" })     ;
		  
		<s:if test="cerrar==null">
		
			var a=parseInt(<s:date name='objDocumento.fechaDocumento' format='yyyy' />);
	        var m=parseInt(<s:date name='objDocumento.fechaDocumento' format='MM' />);
	        var d=parseInt(<s:date name='objDocumento.fechaDocumento' format='dd' />);
			var fecha=new Date(a,m-1,d); 
			dojo.addOnLoad(function(){
				new dijit.form.DateTextBox({
					name			: "objDD.strFechaDocumento",
					constraints		: {datePattern:"dd/MM/yyyy"},
					invalidMessage	: "Ingrese una fecha con el formato dd/mm/yyyy",
					required		: true
				},"fechadocumento");
				try{
				dijit.byId("fechadocumento").attr("value",fecha);
				}catch(err){
				console.debug("error: "+err); 
				}
			}); 
			
		</s:if>
       
      var archivosAlf  ;
      var duplicadosup  ;
      
      var  cant =0 ;
      <s:iterator value="expediente.documentoList">
	      <s:iterator value="archivoList">
	         cant = cant + 1 ;  
	      </s:iterator>
      </s:iterator>
              


      function submitForm() {
		var	enviar =  true;

            var camposdelForm =document.getElementById("divShowFile2");
            var filesinput = camposdelForm.getElementsByTagName("a") ;
             //  alert("files:" +filesinput.length); 

          
            var duplicatedFiles = " Los siguientes archivos ya se encuentran en el expediente: \n" ;
			var cantduplicated = 0 ; 
			var archivos= filesinput.length;
			var idarchivos =  new Array (archivos) ;  
			
			var files = filesinput  ;
			 // Comprobar de que no existen los mismos archivos en el expediente;
			var rutapadre = '<s:property value ="rutapadre" />' ;
			var uploaded = new Array(archivos) ; 

			// alert("inicio for :" );  
			//alert("rutapadre:"+rutapadre);   
			var ruta0= "" ;
			for(var t=0;t<files.length;t++){ 
				 
				var nuploaded = rutapadre+files[t].innerHTML ;      
				nuploaded = nuploaded.toUpperCase() ;  

				archivosAlf = document.getElementsByName("rutaAlfresco");
				duplicadosup = document.getElementsByName("idArchivo");
				//alert("archivos.length:"+archivos);   
				for(var u = 0 ; u<archivosAlf.length ;u++){  
					 archivosAlf[u].value = archivosAlf[u].value.toUpperCase(); 
					// alert(" nuploaded:"+nuploaded+"  , archivosAlf[u]:"+archivosAlf[u].value+ "  , comparacion: "+(nuploaded==archivosAlf[u].value)); 
					// alert( "convertido:  "+convert(archivosAlf[u])+" el otro: "+convert(nuploaded)+" comp:") ;  
					// alert("(archivosAlf[u])==convert(nuploaded)"+(archivosAlf[u])==convert(nuploaded));         
					if(nuploaded==archivosAlf[u].value){  
						   
						duplicatedFiles = duplicatedFiles + files[t].innerHTML +" \n" ; 
						idarchivos[u] =  duplicadosup[u].value ;  
						cantduplicated = cantduplicated + 1 ;  

						//frmUploadWithForm
						// addhidden("tablaformulario","idarchivos" , duplicadosup[u] ) ; 
						ruta0 = "&idarchivos="+duplicadosup[u].value;  
						//$(this).append('<input type="hidden" name="idarchivos" value ="'+duplicadosup+'"> ') ; 
						
						break ;
						
					}else { 
						idarchivos [u] = 0  ;    
					}     
				}
			}
			// alert("hii2:" );/*                     
			var  versionar = false ; 
			// alert("cantduplicated:"+cantduplicated );  
			if(cantduplicated >0 ){
				//   alert("antes del confirm:" );  
				var  versionar = confirm(duplicatedFiles+"\n Desea versionar estos archivos ? ") ;
				//alert('versionar:'+versionar) ; 
				if(!versionar){
					enviar = false ;  
				}
     
				//alert("despues del confirm:" );  
			}   
			
		    //alert("versionar:"+versionar );  	    
			// addhidden("tablaformulario","versionar" , versionar) ; 
			//$(this).append('<input type="hidden" name="versionar" value ="'+versionar+'"> ') ; 
			//var asunto  =   document.getElementById("objDocumento.asunto").value ;   
			
			///confirm("asunto "+asunto+""+(asunto==""));
			  
			//enviar =  false;  /*
			/*if(asunto==""){
				alert("Es necesario ingresar un asunto ");
				enviar =  false; 
			}	*/
				
			if(archivos<1){
				alert("Es necesario subir al menos un archivo.");
				enviar =  false;
			}
			
			//var tipo = document.getElementsByName ("objDocumento.tipodocumento.idtipodocumento") [0].value; //   $("input[name='objDocumento.tipodocumento.idtipodocumento']").val();

			/*if(tipo==""){   
				alert("Debe ingresar un tipo de documento");
				enviar =  false;
			}*/
			
			/*var tiponumeracion = ""; 
			var nrodocumento =dijit.byId("objDocumento.nrodocumento").getValue();
			var tipodoc = dijit.byId("objDocumento.tipodocumento.idtipodocumento").getValue(); 
			
			if(tipo!=""){

			            
			storeTipoDocumento.fetchItemByIdentity({identity:tipodoc
				          ,onItem: function(item) {    
						   tiponumeracion=storeTipoDocumento.getValue(item, "tiponumeracion");
				           } });
	        
				if(dojo.byId("objDocumento.enumerarDocumento").checked&&tiponumeracion!=null&&tiponumeracion=="M"&&nrodocumento=="") {
					alert("Debe ingresar un nro de documento");
					enviar =  false; 
	 	      	}
			}  
			*/
			if(enviar){
 				 //alert("se va a enviar ")
 				var ruta =  document.forms["frmUploadWithForm"].action ;
 				ruta = ruta  + "?versionar="+versionar+ruta0;  				
 				//if(!dojo.byId("objDocumento.enumerarDocumento").checked){
 				//	dojo.byId("objDocumento.enumerarDocumento").value = ""; 
 	 			//}  
 				document.forms["frmUploadWithForm"].action = ruta ;   
 				dijit.byId("dlgProgresBar").show() ;  
 				document.forms["frmUploadWithForm"].submit();  
 				
		    } else {
			     // alert("no se va a enviar "); 
			}
		 

      } 

     /*
      function addhidden(formulario, nombre , value ){
    	  myNewRow = document.getElementById(formulario).insertRow(-1); 
    	  myNewRow.id=nombre+value; 
    	  myNewCell=myNewRow.insertCell(-1);   
    	  myNewCell.innerHTML="<td style='display:none' > <input  type='hidden' name='"+nombre+"' value='"+value+"'  >";   	
    	  myNewCell.style.display  = "none" ;         
    	       
    	 }
     */      

/*			$(document).ready(function(){
				
				$("#SubirConMetadata_dosubirConMetadata").submit(function(){
					var duplicatedFiles = " Los siguientes archivos ya se encuentran en el repositorio: \n" ;
					var cantduplicated = 0 ; 
					var archivos=$("#divShowFile2 input").length;
					var idarchivos =  new Array (archivos) ;  
					
					var files = $("#divShowFile2 input")  ;
					 // Comprobar de que no existen los mismos archivos en el expediente;
					var rutapadre = '<s:property value ="rutapadre" />' ;
					var uploaded = new Array(archivos) ; 
					
					for(var t=0;t<files.length;t++){
						var nuploaded = rutapadre+files.id ; 
						nuploaded = nuploaded.toUpperCase() ;  
						for(var u = 0 ; u<archivos.length;t++){
							if(nuploaded==archivos){
								duplicatedFiles = duplicatedFiles + files.id+" \n" ; 
								idarchivos [u] =  duplicadosup[u] ; 
								cantduplicated = cantduplicated + 1 ;  
								
								$(this).append('<input type="hidden" name="idarchivos" value ="'+duplicadosup+'"> ') ; 
								
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
								return false ;  
						}
					}  
					 
					$(this).append('<input type="hidden" name="versionar" value ="'+versionar+'"> ') ; 
						
					if(archivos<1){
						alert("Es necesario subir al menos un archivo.");
						return false;
					}
					var tipo=$("input[name='objDocumento.tipodocumento.idtipodocumento']").val();
					if(tipo==""){
						alert("Debe ingresar un tipo de documento");
						return false;
					}

					
					return true;
					
				});
				
			});
			*/

		
		      function refrescar() {

				 alert('<s:property  escape="false"  value ="mensaje" />');
		          var iddoc = document.getElementById('idDocumento');   
		         //alert("iddoc: "+iddoc.value) ; 
		         window.opener.viewDetailCustom2(iddoc.value); 
		         window.close();  
		      }          				
			
		function actualizarFirmante(){
 
		var tipodoc = dijit.byId("objDocumento.tipodocumento.idtipodocumento").getValue();
		var firmante = dijit.byId("objDocumento.firmante.idusuario").getValue();
		
		//alert(tipodoc); 	
		var tiponumeracion = ""; 
		if(tipodoc!=""){

		storeTipoDocumento.fetchItemByIdentity({identity:tipodoc
			          ,onItem: function(item) {    
					   tiponumeracion=storeTipoDocumento.getValue(item, "tiponumeracion");
			           } });
		}
		
		dojo.byId("textonrodoc").style.display = "none"; 
		dojo.byId("checkenumerar").style.display = "none"; 
		
		if(firmante!=null&&firmante=="<s:property value='#session._USUARIO.idusuario' />"){
			
			dojo.byId("checkenumerar").style.display = "block";
			if (tiponumeracion!=null&&tiponumeracion!=""&&tiponumeracion=="M"){ 
				dojo.byId("textonrodoc").style.display = "block"; 	
			}else{
				dojo.byId("textonrodoc").style.display = "none"; 
				dijit.byId("objDocumento.nrodocumento").setValue("") ;  
			}	
			
		}else {
			 
			dojo.byId("objDocumento.enumerarDocumento").checked = false ;
			dijit.byId("objDocumento.nrodocumento").setValue("") ;  
			
			}
		
		}
		

		
		//textonrodoc.
		
		</script>
		
		<style type="text/css">
			div.falso {
				position: absolute;
				top: -48px;
				left: 85px;
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
				top: -48px; 
				left: 55px;
			}
		</style>
	</head>
	<body class="soria" <s:if test="cerrar!=null">onload="refrescar()"</s:if>>   
		<table width="100%">
			<tr>  
				<td height="14" colspan="3"></td>
			</tr>
	<tr>
		<td height="20" colspan="3" class="titulo">
               <table width="99%" border="0" height="20" align="center" bgcolor="#A2C0DC">
			<tr>
				<td align="left" class="titulo">Nuevo Documento</td>
			</tr>
		</table>
		</td>
	</tr>
	 
	<tr align="center">
		<td width="1%" align="center">&nbsp;</td>
            <td width="99%" colspan="2" align="left"> 
            <%--
                   <img border="0" onclick="addhidden('tablaformulario','versionar' , true); " alt="Crear Documento"/>
              --%>      
               <img src="images/xx.bmp" border="0" onclick="submitForm();" alt="Crear Documento"/>
               <s:if test="#session._RECURSO.UsuFinBtnUpl">
                  &nbsp;&nbsp;
                  <s:form id="frmUploadFile2" action="doUploadFile" method="POST" enctype="multipart/form-data">
                     <div id="divUpload" style="position:relative;">
                        <input name="upload" type="file" class="file" size="1" onchange="verificar(2, this.value)" onclick="this.value=''"/>
                        <div class="falso">
                           <img src="images/adjunto.bmp" align="left" border="0" alt="Adjuntar Archivo" />
                        </div>
				</div>
			</s:form>
               </s:if>
            </td>
	</tr>
	<tr>
		<td></td>
		<td height="16" colspan="2" align="left" class="plomo">
		<div id="divShowFile2"></div>
		</td>
	</tr>
	<tr>
            <td height="14" colspan="3"> 
               <s:form name="frmUploadWithForm" action="SubirConMetadata_dosubirConMetadata" method="post">
               
                  <s:iterator value="expediente.documentoList">
				      <s:iterator value="archivos">
				          <s:hidden name="rutaAlfresco"/> 
				          <s:hidden name="idArchivo" /> 
				      </s:iterator>
			      </s:iterator>
				<s:hidden id="idDocumento" name="idDocumento" /> 
                  <table id="tablaformulario" width="95%" border="1" align="center" bordercolor="#669BCD" bgcolor="#BFD9F1">
				<tr>
					<td height="75">
					<s:if test="cerrar==null">
					<table id="datosadj" width="98%"   height="97" align="center">
						<tr>
							<td width="1%"></td>
							<td width="20%" height="5"></td>
							<td width="17%"></td>
							<td width="15%"></td>
							<td width="45%"></td>
							<td width="3%"></td>
						</tr>
						<tr>
							<td colspan="6" height="15" class="label">Archivos en el Expediente</td>
						</tr>
						<tr>
			               <td colspan="6">
			                  <div id="showErrorDoc" class="margen5PX" style="color:red;font-weight:bold;">&nbsp;</div>
			               </td>
			            </tr>
			            
			            <s:iterator value="expediente.documentoList">
					      <s:iterator value="archivos">
					        <tr>
								<td></td>
								<td colspan="2" height="16" align="left"><s:property value="tipoDocumento.nombre"/> <s:property value="numeroDocumento"/> </td>
								<td colspan="4" align="left" width="17%" class="label">
						           <s:property value="rutaAlfresco"/> 
						        </td>

								<td></td> 
						    </tr> 
					      </s:iterator>
				      </s:iterator>
				      
				      <s:textfield cssStyle="display: none ; " name="objDocumento.idDocumento"></s:textfield>
			            <%-- 
						<tr>
							<td></td>
							<td height="16" align="left">Tipo Documento</td>
							<td align="left" width="17%" class="label">
							<div dojoType="dijit.form.FilteringSelect"
                                         store="storeTipoDocumento"
                                         idAttr="id" 
                                         labelAttr="label"
                                         searchAttr="label"
                                         required ="true"    
                                         onChange = "actualizarFirmante"
								         name="objDocumento.tipodocumento.idtipodocumento"
                                         id="objDocumento.tipodocumento.idtipodocumento"
                                         size="80" 
                                         style="350px"/>
							</td>
							<td align="left" class="label"></td>
							<td align="left" class="label"></td>
							<td></td> 
						</tr>
					
						<tr>
							<td></td>
							<td height="16" align="left">Firmante </td>
							<td align="left" width="17%" class="label">
							  
							<select dojoType="dijit.form.FilteringSelect" store="store" required="false"  onChange = "actualizarFirmante"  jsId="objDocumento.firmante.idusuario"   id="objDocumento.firmante.idusuario" idAttr="id" labelAttr="label" searchAttr="label" name="objDocumento.firmante.idusuario" value="<s:property  value='#session._USUARIO.idusuario' />"  >
							</select>
							    
							</td>
							<td align="left" class="label"></td>
							<td align="left" class="label"></td>
							<td></td>
						</tr>
						
						<tr>
							<td></td>
							<td height="16" align="left">Autor </td>
							<td align="left" colspan="3" width="17%" class="label">
							<div dojoType="dijit.form.ValidationTextBox"
                                  id="objDocumento.autor"
                                  jsId="objDocumento.autor"
                                  name="objDocumento.autor"
                                  invalidMessage="Ingrese un Autor"
                                  required="false" 
                                  style="width:300px"
                                  value="<s:property  value="#session._USUARIO.nombres" /> <s:property  value="#session._USUARIO.apellidos" />"
                                  trim="true" />  
							</td>
							<td align="left" class="label"></td>
							<td align="left" class="label"></td>
							<td></td>
						</tr>
						
						<tr>
							<td></td>
							<td height="16" align="left">Asunto </td>
							<td align="left" colspan="3" width="17%" class="label">
							<div dojoType="dijit.form.ValidationTextBox"
                                  id="objDocumento.asunto"
                                  jsId="objDocumento.asunto"
                                  name="objDocumento.asunto"
                                  invalidMessage="Ingrese un Asunto"
                                  required="false" 
                                  style="width:300px"
                                  trim="true" />  
							</td>
							<td align="left" class="label"></td>
							<td align="left" class="label"></td>
							<td></td>
						</tr>


						<tr>
							<td></td>
							<td height="16" align="left">Fecha Documento</td>
                                 <td colspan="3" align="left" class="label">
									<input id="fechadocumento" type="text" />
					
                                 </td>
							<td></td>
						</tr>
						<tr>
							<td></td>
							<td class="titulo">&nbsp;</td>
							<td colspan="4"></td>
						</tr>
						
						<tr> 
							<td></td>
							<td height="16" align="left">
							<div id="checkenumerar" style="display: none;"> 
							<input type="checkbox" id="objDocumento.enumerarDocumento" name="objDocumento.enumerarDocumento" value="S" />
							Enumerar 
							</div> 
							</td>   
							<td align="left" colspan="3" width="17%" class="label"> 
							<div id="textonrodoc" style="display: none;" > 
							<div dojoType="dijit.form.ValidationTextBox"
                                  id="objDocumento.nrodocumento"
                                  jsId="objDocumento.nrodocumento"
                                  name="objDocumento.nrodocumento"
                                  invalidMessage="Ingrese el Autor"
                                  required="false" 
                                  style="width:250px"
                                  trim="true" />  
                                  
                            </div>
							</td>  
							<td align="left" class="label"></td>
							<td align="left" class="label"></td>
							<td></td>
						</tr>
						 --%>
					</table>
					
					</s:if>
					</td>
				</tr>
				
			</table>
			          
               </s:form>
               
            </td> 
	</tr>
	<tr>
		<td height="14" colspan="3"></td>
	</tr>
</table>
      <%@ include file="../util/progressBar.jsp" %>    
</body>
</html>