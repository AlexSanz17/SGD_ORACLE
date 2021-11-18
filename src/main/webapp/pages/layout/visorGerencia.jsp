<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.btg.ositran.siged.domain.Documento" %>
<%@page import="com.btg.ositran.siged.domain.Archivo" %>
<%@page import="com.btg.ositran.siged.domain.Usuario" %>
<%@page import="java.util.ArrayList" %>
<%@page import="org.ositran.utils.DocumentoDetail" %>
<%@page import="java.text.SimpleDateFormat" %>
<%@page import="java.io.FileInputStream" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Insert title here</title>
	<%
     //carga el el idAreaOrigen por Defecto
    String idDoc = (String)request.getAttribute("idDoc");
	Integer iIdDoc = Integer.parseInt(idDoc);
	String rutaAlfresco = (String)request.getAttribute("rutaAlfresco");
	Documento objDocumento = (Documento)request.getAttribute("objDocumento");
	
	SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy hh:mm");
	//Integer Iddestinatario = objDocumento.get
	String PDF = (String)request.getAttribute("PDF");	
	DocumentoDetail objDD = (DocumentoDetail)request.getAttribute("objDD");
	Integer iddestinatario = (Integer)request.getAttribute("iddestinatario");	
	Integer objDDiIdRemitente = objDD.getiIdRemitente();
	String asuntoDD = (String)request.getAttribute("asuntoDD");
	
	Archivo archivo;
	ArrayList listArchivo = (ArrayList)request.getAttribute("listArchivo");
	DocumentoDetail objDDIG = (DocumentoDetail)request.getAttribute("objDDIG");
	
	Usuario usuario;
	ArrayList listUsuario = (ArrayList)request.getAttribute("listUsuario");
	Integer n = listUsuario.size(); 
	
	Integer idProceso = objDocumento.getExpediente().getProceso().getIdproceso();
	System.out.println("VERIFICAR iIdDoc:(desde visor)"+idDoc);
	

	
    %>
    
    

	<script type="text/javascript" src="js/pdfViewer/pdfobject.js"></script>

	<script type="text/javascript">
	
	if('<%=PDF%>'=='S'){
		var myPDF = new PDFObject({ 
	    	url: '<%=rutaAlfresco%>'
	    }).embed('divPDF');
		dijit.byId("dlgProgresBar").hide() ;
    }else{
    	dijit.byId("dlgProgresBar").hide() ;
    	
    	/*new dijit.form.TextBox({
            id      : "lblErrorVisor",
            jsId    : "lblErrorVisor",
            value   : "El documento no contiene archivos pdf adjuntos para mostrar",
            disabled: true,
            style   : "float:right;border:0;width:50px;background:transparent;"
        });    	
    	dijit.byId("divPDF").add(dijit.byId("lblErrorVisor"));*/
    	
	
    }
		
	/*var mostrarPDF=function(rutaAlfresco){
		var extension = funcExtension(rutaAlfresco);
		console.debug("es un:"+extension);			
		if(recursoIG==1 && vistaIG==true){ 
			verArchivo(rutaAlfresco);	  
	    }else{	 
	         if(extension=='pdf' || extension=='PDF'){ */
				/*var service = new dojo.rpc.JsonService("SMDAction.action");
			    var defered = service.verArchivoAlfresco(rutaAlfresco);
			    
			    defered.addCallback(function(rutaAlfresco) { 
			    	var myPDF = new PDFObject({ 
				    	url: rutaAlfresco
				    }).embed('divPDF');   
			    	dijit.byId("dlgProgresBar").hide() ;
			    });			*/	    
	        /*}else{	 
	        	verArchivo(rutaAlfresco);
		    }
	}	*/
		

		
		
		var funcExtension=function(miString){			
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
		 
		var contParaVal = 0;
		
        function validarForm(){
        	
			var contenido=dijit.byId("contenid").getValue();
			//alert(contenido);
			if(contenido.length>3990){
				if(confirm("El contenido no debe tener mas de 3990 caracteres, desea que sea truncado a 3990 caracteres?")){
					dijit.byId("contenid").setValue(contenido.substring(0,3990));
				}
				else{
					return false;
				}
			}
         
			return true;
		}
        
       function validarForm2(){
        	
			var contenido=dijit.byId("contenidEnvio").getValue();
			//alert(contenido);
			if(contenido.length>3990){
				if(confirm("El contenido no debe tener mas de 3990 caracteres, desea que sea truncado a 3990 caracteres?")){
					dijit.byId("contenidEnvio").setValue(contenido.substring(0,3990));
				}
				else{
					return false;
				}
			}
         
			return true;
		}
		
		var validar= function(){
          var cont = 0;
          try{
              var checkboxes=document.getElementById("listaDestinatarioForm").paraListaUsuario;	                    
              var k;
				   for(k =0;k<checkboxes.length;k++ ){
						if(checkboxes[k].checked){
							cont ++;							
						}						
					 }
				   <s:if test="objDocumento.documentoreferencia == null">
				   if(cont==0){
					   disable_false();					
					}else{
						if(cont>1){
							   disable_false();					
							}else{						
							   disable_true();		
							}	
					}
				   
				   
				   </s:if>
          }
        catch(err){  }
        
		};
		
		function disable_false(){
			   var i;			   
			   for (i=0;i<document.getElementById("listaDestinatarioForm").ccListaUsuario.length;i++){
				   document.getElementById("listaDestinatarioForm").ccListaUsuario[i].checked = 0;
				   var puntero=document.getElementById("listaDestinatarioForm").ccListaUsuario[i];
			           puntero.removeAttribute('readonly');
			           puntero.setAttribute('disabled','disabled');
	         }
		}
		function disable_true(){
			   var i;			   
			   for (i=0;i<document.getElementById("listaDestinatarioForm").ccListaUsuario.length;i++){
				   var puntero=document.getElementById("listaDestinatarioForm").ccListaUsuario[i];
		           puntero.removeAttribute('disabled');
		           puntero.setAttribute('readonly','readonly');		           
			   }
	    }
		
		function abrirVentanaSeguimientoIG() {
            var opciones = "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=no, width=650, height=500, top=50, left=200";
            var pagina ="goViewSeguimiento.action?iIdDocumento=" + <%=idDoc%>;
            window.open("goViewSeguimiento.action?iIdDocumento=" + <%=idDoc%>, "", opciones);
        }
		
		function imprimirProveidoIG(){
        	var url = "imprimirProveido.action?iIdDoc=" + <%=iIdDoc%>;
        	var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=900, height=700, top=50, left=200";                
            window.open(url,"",opciones);
        }
		 function abrirHojaRutaIG(){
         	var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=900, height=700, top=50, left=200";
             var pagina = "ReporteAPN_verHojaRuta.action?idDocumento=" + <%=iIdDoc%>;
             window.open(pagina,"",opciones);
         }
		function refrescar() {
            //window.opener.parent.location.href = "inicio.action?sTipoFrame=grid";
            console.debug(window.opener.sTipoGridActual);
            console.debug(window.opener.TIPO_GRID_SEGUIMIENTO);
            
        	 try {
        		 if(window.opener.sTipoGridActual != window.opener.TIPO_GRID_SEGUIMIENTO){
        			 window.opener.eliminarRegistro();
                  	 window.opener.filtrarSeguimiento();
        		 }else{
        			window.opener.refrescarDetalle(); 
        		 }
        	}catch(err){ 
     			  //console.debug("errror : "+err);
     			  this.window.close();
             }

            if (provieneDeMail == "true") {
               window.opener.close();
            }
            
            this.window.close();
         }
		
			function derivar(){
				  var texto = "";
				  <s:if test="objDocumento.documentoreferencia == null">
				    document.getElementById("objDD.strTexto").value = dijit.byId("contenid").getValue(false);
					texto = document.getElementById("objDD.strTexto").value;
	              </s:if>
						
						texto = nombreArchivoIG(texto);
						console.debug("verrrr2"+texto);
						var objDDiIdDocumento = <%=idDoc%>;
						var sTipoDerivacion = "normal"; 
						var sOpcion ="paraaprobar"; 
						var objDDiIdRemitente =<%=objDDiIdRemitente%> ;
						var iddestinatario =<%=iddestinatario%>;       
						var objDDstrAccion ="aprobar";
						var objDDstrTexto = texto;
						if(validarForm()){
							petincion(objDDiIdDocumento,objDDiIdRemitente,iddestinatario, sTipoDerivacion,sOpcion,objDDstrAccion,objDDstrTexto, null, null, 0);	
			            }
										
			}
			
			function rechazar(){
				 var texto = "";
				    <s:if test="objDocumento.documentoreferencia == null">
				     document.getElementById("objDD.strTexto").value = dijit.byId("contenid").getValue(false);
					 texto = document.getElementById("objDD.strTexto").value;
	                </s:if>
						
						texto = nombreArchivoIG(texto);
						var objDDiIdDocumento = <%=iIdDoc%>;
						var sTipoDerivacion = "normal";
						var sOpcion ="rechazar"; 
						var objDDiIdRemitente =<%=objDDiIdRemitente%> ;
						var iddestinatario =<%=iddestinatario%>; 
						var objDDstrAccion ="rechazar";  
						var objDDstrTexto = texto;
						if(validarForm()){
							petincion(objDDiIdDocumento,objDDiIdRemitente,iddestinatario, sTipoDerivacion,sOpcion,objDDstrAccion,objDDstrTexto, null, null, 0);	
			            }
			}
			
			var nombreArchivoIG= function(textDivCadena) {
				console.debug("estamos contruyendo texto IG Inicio : "+textDivCadena);
				var textDiv = "";
				textDiv = textDivCadena;
				
				var fin = textDiv.lastIndexOf('<br');
				//var inicio = textDiv.lastIndexOf('>',fin);
				nombre = textDiv.substring(0, fin);
				console.debug("estamos contruyendo texto IG Fin : "+nombre);
				return nombre;				
			};
			
			
			
			function enviar(){	
				
				   
					var checkboxesPara = new Array();
					var checkboxesCC = new Array();
					
	            	var contPara = 0;
	                try{
	                    var checkboxes=document.getElementById("listaDestinatarioForm").paraListaUsuario;	                    
	                    var k;
	   				   for(k =0;k<checkboxes.length;k++ ){
	   						if(checkboxes[k].checked){
	   							checkboxesPara[contPara]=checkboxes[k].value
	   							contPara ++;
	             	    	}						
	   					 }              
	                }
	              catch(err){  }         	    
	              
	              <s:if test="objDocumento.documentoreferencia == null">
	              var contCC = 0;
	              try{
	                    var checkboxes=document.getElementById("listaDestinatarioForm").ccListaUsuario;	                    
	                    var k;
		   				   for(k =0;k<checkboxes.length;k++ ){
		   						if(checkboxes[k].checked){
		   							checkboxesCC[contCC]=checkboxes[k].value
		   							contCC ++;
		             	    	}						
		   					 }		   				   
	                }
	              catch(err){  }
	              </s:if>
	              
	              
	              
	    				document.getElementById("objDD.strTexto").value = dijit.byId("contenidEnvio").getValue(false);
						var texto = document.getElementById("objDD.strTexto").value;
						texto = nombreArchivoIG(texto);
						var objDDiIdDocumento = <%=iIdDoc%>;
						var sTipoDerivacion = "normal";
						 
						var objDDiIdRemitente =<%=objDDiIdRemitente%>;
						var iddestinatario =<%=iddestinatario%>; 
						var objDDstrAccion ="reenviar";  
						var objDDstrTexto = texto;	
						var objDDiIdProceso =<%=idProceso%>; 
						var sOpcion;
						if(contPara>1){  
							sOpcion ="multiple";	
						}else{  
							sOpcion ="transferir";	
						}
						<s:if test="objDocumento.documentoreferencia != null">
						     sOpcion ="multiple";
		               </s:if>
						
						if(validarForm2()){
							if(contPara>0){  
								petincion(objDDiIdDocumento,objDDiIdRemitente,iddestinatario, sTipoDerivacion,sOpcion,objDDstrAccion,objDDstrTexto,checkboxesPara,checkboxesCC,objDDiIdProceso);	
							}else{ 
								alert("Debe seleccionar al menos un Destinatario");
							}	
			            }
						
						
						
						
	              
			}
			
			function petincion(objDDiIdDocumento,objDDiIdRemitente,iddestinatario, sTipoDerivacion,sOpcion,objDDstrAccion,objDDstrTexto,checkboxesPara,checkboxesCC,objDDiIdProceso){
				console.debug("objDDiIdDocumento"+objDDiIdDocumento+"objDDiIdRemitente"+objDDiIdRemitente+ "iddestinatario"+iddestinatario + "sTipoDerivacion"+ sTipoDerivacion +"sOpcion"+sOpcion+"objDDstrAccion"+objDDstrAccion+"objDDstrTexto"+objDDstrTexto+"checkboxesPara"+checkboxesPara+"checkboxesCC"+checkboxesCC+"objDDiIdProceso"+objDDiIdProceso);
				if(dijit.byId("dlgProgresBar")!=null){
		        	dijit.byId("dlgProgresBar").show() ;
		        }
				var service = new dojo.rpc.JsonService("SMDAction.action");
			    var deferedIG = service.derivarIG(objDDiIdDocumento,objDDiIdRemitente,iddestinatario, sTipoDerivacion,sOpcion,objDDstrAccion,objDDstrTexto ,checkboxesPara,checkboxesCC,objDDiIdProceso);
			    
			    deferedIG.addCallback(function(result) {	
			    	 console.debug("RESULTADO derivarIG"+result);
			    	 showGridInbox(sTipoGridActual);
			    	 dijit.byId("dlgProgresBar").hide() ;
			    });
			    
			}
			
	</script>
	

	<style type="text/css">
        	td.itemDocAdicPrincGG{
        		padding:0.3em;
        		cursor: pointer;
        		background-color:#94B9EF;
        		font-weight:bold;
        	}
        	td.itemDocAdicGG{
        		padding:0.3em;
        		cursor: pointer;
        		background-color:#FFFFFF;
        	}
        	td.itemDocAdicGG:HOVER{
        		background-color:#D4E3FB;
        		color:#0066D1;
        	}
        	td.itemDocAdicGG:FOCUS{
        		background-color:#D4E3FB;
        	}
        	td.centerGG{
        		text-align:center;
        		padding: 0.3em;
        	}
        	td.itemDestGG{
        		padding: 0.3em;
        	}
        </style>
</head>
<body >
	<s:hidden id="objDD.strTexto" name="objDD.strTexto" /> 

	<div dojoType="dijit.layout.BorderContainer" region="top" style="height:67%;">
		<div id="divPDF" style="width:99%; height:99%;"></div>
	</div>
	
	
	
	
	<div dojoType="dijit.layout.BorderContainer" region="center" style="height:33%;">
	
	
	<div dojoType="dijit.layout.TabContainer" id="tabContainerDetailConsulta3IG" jsId="tabContainerDetailConsulta3IG" region="bottom" splitter="true" style="width:100%;height:98%;"  tabStrip="true">
                <s:if test="objDocumento.documentoreferencia == null">
                <div dojoType="dojox.layout.ContentPane" id="contentPaneDetailConsulta3IG" jsId="contentPaneDetailConsulta3IG" title="Responder Documento">
                
	<div dojoType="dijit.layout.BorderContainer" region="center" style="height:100%;">
		<div dojoType="dojox.layout.ContentPane" region="left" style="width:97%; height:95%;margin:0em;padding:0em;">

		<table width="100%">
			<tr>
				 <td>
			     <div dojoType="dijit.Toolbar" style="height:1.8em;">
			
			               <s:if test="paraAprobar">
                                <s:if test="#session._RECURSO.UsuFinBtnDer && objDocumento.documentoreferencia == null">
                                    <div dojoType="dijit.form.Button" onClick="javascript:derivar();" iconClass="dijitEditorIcon dijitEditorIconRedo">Aprobar</div>
                                </s:if>
                            </s:if>
                            
							<s:if test="destinatarioIgualRemitente == false">
	                            <s:if test="#session._RECURSO.UsuFinBtnRec && objDocumento.documentoreferencia == null">
	                                <div dojoType="dijit.form.Button" onClick="javascript:rechazar()" iconClass="dijitEditorIcon dijitEditorIconUndo">Rechazar</div>
	                            </s:if>
                            </s:if>
                            <!-- 
                            <div dojoType="dijit.form.Button" onClick="javascript:despachar()" iconClass="dijitEditorIcon dijitEditorIconRedo">Despachar</div>
                            
                            <s:if test="#session._RECURSO.UsuFinBtnTraExp">
                                <div dojoType="dijit.form.Button" onClick="abrirVentanaSeguimientoIG()" iconClass="dijitEditorIcon dijitEditorIconInsertTable" label="Trazabilidad">
                                </div>
                            </s:if>
                            <s:if test="#session._RECURSO.HojaRuta">
                                <div dojoType="dijit.form.Button"  onClick="abrirHojaRutaIG()" iconClass="siged16 iconoHojaRuta">Hoja de Ruta</div>
                            </s:if>
                            <s:if test="#session._RECURSO.BtnImprimirProveido">
                            	<div dojoType="dijit.form.Button"  onClick="imprimirProveidoIG()" iconClass="siged16 iconoImprimir">Imprimir prove&iacute;do</div>
                            </s:if>
                             -->
			    </div>
				</td>
			</tr>
			<tr>
				<td >
								
								
										<div dojoType="dijit.Editor" jsid="contenid" id="contenid"
											style="background-color: #FFF;"></div> 
											<script
											type="text/javascript">
									  
									   var cadena  = "";  
									   cadena = cadena.replace(/<P><\/P>/g,"");
									   document.getElementById("contenid").innerHTML = cadena.substring(0,3999);
									   
									</script>
				</td>
			</tr>
		</table>
        
		</div>
		
		
		<!-- 
		<div dojoType="dojox.layout.ContentPane" region="right" style="width:30%;height:95%;padding:0em;">
			<div dojoType="dijit.Toolbar" style="height:1.8em;text-align:center;">Archivos</div>      


                        
                    
		     </div>		 -->
	</div>
	
                
                </div>
                </s:if>
                <div dojoType="dojox.layout.ContentPane" id="contentPaneDocumentosAdicionalesConsulta3IG" jsId="contentPaneDocumentosAdicionalesConsulta3IG" title="Transferencia r&aacute;pida">
                
                
                <div dojoType="dijit.layout.BorderContainer" region="center" style="height:100%;">
		<div dojoType="dojox.layout.ContentPane" region="left" style="width:62%; height:95%;margin:0em;padding:0em;">

		<table width="100%">
			<tr>
				 <td>
			     <div dojoType="dijit.Toolbar" style="height:1.8em;">
                            
                            <div dojoType="dijit.form.Button" onClick="javascript:enviar()" iconClass="dijitEditorIcon dijitEditorIconRedo">Enviar</div>
                            
			    </div>
				</td>
			</tr>
			<tr>
				<td >
								
									<div dojoType="dijit.Editor" jsid="contenidEnvio" id="contenidEnvio"
											style="background-color: #FFF;"></div> 
								    <script
											type="text/javascript">
									  
									   var cadenaEnvio  = "";  
									   cadenaEnvio = cadenaEnvio.replace(/<P><\/P>/g,"");      
									   
									   document.getElementById("contenidEnvio").innerHTML = cadenaEnvio.substring(0,3999);
									   
									</script>
				</td>
			</tr>
		</table>
        
		</div>
		
		
		
		<div dojoType="dojox.layout.ContentPane" region="right" style="width:35%;height:95%;padding:0em;">
			<div dojoType="dijit.Toolbar" style="height:1.8em;text-align:center;">Destinatarios</div>
				<%if(!listUsuario.isEmpty()) {%>
				<form id="listaDestinatarioForm" action="">	
								
					<table height="16" colspan="5" align="left">					
						<tr>
							<td>&nbsp;</td>
							<td class="centerGG" style="font-size:bold;">Para</td>
							<s:if test="objDocumento.documentoreferencia == null">
							<td class="centerGG" style="font-size:bold;">CC</td>
							</s:if>
				    	</tr>
						<% 	for (int i=0; i<listUsuario.size(); i++){
						usuario = (Usuario)listUsuario.get(i);
						%>		
                                        <tr>  
                                          <td class="label" >
                                          <%=usuario.getNombreCompleto() %>                                      
                                          </td>
                                          
                                          <td class="centerGG">
                                        	<div align="center">
                                             <input type="checkbox" name="paraListaUsuario"  value="<%=usuario.getIdusuario()%>" onchange="validar()"  />
                                         	</div>  
                                          	</td>
                                          	
                                          	<s:if test="objDocumento.documentoreferencia == null">
                                          	<td class="centerGG">
                                            <div align="center">
                                             <input type="checkbox" name="ccListaUsuario"  value="<%=usuario.getIdusuario()%>"  disabled="disabled"/>										  
										  	</div>  
										  	</td>   
                                           </s:if>
                                           
                                        </tr>                        
			      	  <% }%>
			      	  
					</table>
					</form>
			 <% }else{%>
				<table width="100%" border="0">
		         	 <tr>
		            	<td height="100" align="center" class="label">NO TIENE DESTINATARIOS POR DEFECTO</td>
		         	 </tr>
		        </table>
			<%} %>
		</div>		
	</div>
                </div>
    </div>
            
            
		
	</div>

</body>
</html>