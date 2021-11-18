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
		
		
		var mostrarPDF=function(rutaAlfresco){
			var extension = funcExtension(rutaAlfresco);
			console.debug("es un:"+extension);
			if(extension=='pdf'){
				
				
				var service = new dojo.rpc.JsonService("SMDAction.action");
			    var defered = service.verArchivoAlfresco(rutaAlfresco);
			    
			    defered.addCallback(function(rutaAlfresco) { 
			    	var myPDF = new PDFObject({ 
				    	url: rutaAlfresco
				    }).embed('divPDF');   
			    	dijit.byId("dlgProgresBar").hide() ;
			    });
			    
	        }else{	 
	        	/*var myPDF = new PDFObject({ 
			    	url: '%=rutaAlfresco%>'
			    }).embed('divPDF');*/
	        	verArchivo(rutaAlfresco);
    	    }
			
			
		};
		
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
				   if(cont>1){
					   disable_false();					
					}else{						
					   disable_true();		
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
			function notificar(){		
				 
					var checkboxesPara = new Array();
					
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
	              
	              
	              
	    				document.getElementById("objDD.strTexto").value = dijit.byId("contenidEnvio").getValue(false);
						var texto = document.getElementById("objDD.strTexto").value;
						var objDDiIdDocumento = <%=iIdDoc%>;
						var sTipoDerivacion = "normal";
						 
						var objDDiIdRemitente =<%=objDDiIdRemitente%>;
						var iddestinatario =<%=iddestinatario%>; 
						var objDDstrAccion ="notificar";  
						var objDDstrTexto = texto;	
						var objDDiIdProceso =<%=idProceso%>; 
						var sOpcion="notificar";	
						
						
						if(contPara>0){  
							petincion(objDDiIdDocumento,objDDiIdRemitente,iddestinatario, sTipoDerivacion,sOpcion,objDDstrAccion,objDDstrTexto,checkboxesPara,null,objDDiIdProceso);	
						}else{ 
							alert("Debe seleccionar al menos un Destinatario");
						}
			}
			
			function petincion(objDDiIdDocumento,objDDiIdRemitente,iddestinatario, sTipoDerivacion,sOpcion,objDDstrAccion,objDDstrTexto,checkboxesPara,checkboxesCC,objDDiIdProceso){
				
				var service = new dojo.rpc.JsonService("SMDAction.action");
			    var deferedIG = service.derivarIG(objDDiIdDocumento,objDDiIdRemitente,iddestinatario, sTipoDerivacion,sOpcion,objDDstrAccion,objDDstrTexto ,checkboxesPara,checkboxesCC,objDDiIdProceso);
			    
			    deferedIG.addCallback(function(result) {						    	
			    	
			    	console.debug(result);
			    	
			    	 service.getListaDocumentosRecibidos(null).addCallback(function(objJSON){
			      		if(objJSON.items!=null){
			      			storeProbar = new dojo.data.ItemFileReadStore({
			     				 data: objJSON
			     			 	});
			      			var idDoc;
			      		    function gotItems(items, request){     		    	
			      		        var i;
			      		        for(i = 0; i < items.length; i++){        		           
			      		        	idDoc = storeProbar.getValue(items[i], "id"); break;
			      		        }
			      		      
						    	// Actualiza el arbol
								dijit.byId("contextMenuDocumentosRecibidos").destroy();
								dijit.byId("borderContainerDocumentosRecibidos").destroy();
								var divDocumentosRecibidos = dojo.byId("divDocumentosRecibidos");                       
					            buildDocumentosRecibidos(divDocumentosRecibidos,null,true);

								//reconstruye tab
								var objRecurso = new Recurso("","","","");
						      	objRecurso.id = "idInterfazGerente";
						      	objRecurso.iconClass = "siged16 sigedSave16";
						      	objRecurso.href = "interfazGerente.action?idDoc="+idDoc;
						      	objRecurso.title = "Bandeja Entrada";

						         //destruye la interfaz IG
						         dijit.byId("tabContainerInbox").removeChild(dijit.byId("idInterfazGerente"));
						         dijit.byId("idInterfazGerente").destroyDescendants();
						         dijit.byId("idInterfazGerente").destroy();

						         if (dijit.byId("grdBusquedaExpediente")) {
						               dijit.byId("grdBusquedaExpediente").destroy();
						         }

						         var newTabInterfazGerente = new dojox.layout.ContentPane({
						            id : objRecurso.id,
						            jsId : objRecurso.id,
						            closable: false,
						            href : objRecurso.href,
						            iconClass : objRecurso.iconClass,          
						            title: objRecurso.title
						         });
						         dijit.byId("tabContainerInbox").addChild(newTabInterfazGerente);
						         dijit.byId("tabContainerInbox").selectChild(dijit.byId(objRecurso.id));
						         //refrescar();
			      		          
			      		    }    		    
			      		    function fetchFailed(error, request){
			      		      alert("Error");
			      		      alert(error);
			      		    }    		    
			      		    var sortAttributes = [{attribute: "name", descending: true}, { attribute: "id", descending: true}];
			      		    storeProbar.fetch({query: {},onComplete: gotItems, onError: fetchFailed, queryOptions: {deep:true}, sort: sortAttributes});    			
			      	     }		
			          });
			    	
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
	

	<div dojoType="dijit.layout.BorderContainer" region="top" style="height:67%;">
		<div id="divPDF" style="width:99%; height:99%;"></div>
	</div>
	
	
	
	
	<div dojoType="dijit.layout.BorderContainer" region="center" style="height:33%;">
	
	
	<div dojoType="dijit.layout.TabContainer" id="tabContainerDetailConsulta3" jsId="tabContainerDetailConsulta3" region="bottom" splitter="true" style="width:100%;height:98%;"  tabStrip="true">
                <div dojoType="dojox.layout.ContentPane" id="contentPaneDetailConsulta3" jsId="contentPaneDetailConsulta3" title="Informativo">
                
	<div dojoType="dijit.layout.BorderContainer" region="center" style="height:100%;">
		<div dojoType="dojox.layout.ContentPane" region="left" style="width:69%; height:95%;margin:0em;padding:0em;">

		<div dojoType="dijit.Toolbar" style="height:1.8em;text-align:center;">Contenido</div>
			<table style="width:100%;">			
				
				<tr>
			     	<td class="label">Nro. Exp.:</td>
					<td class="label"><%=objDDIG.getsNroExpediente() %></td>
			    </tr>
				<tr>
					<td class="label">Asunto Doc.:</td>
					<td class="label"><%=objDDIG.getStrAsunto()%></td>
				</tr>
				<tr>
					<td class="label">Fecha:</td>
					<td class="label"><%=formato.format(objDDIG.getFechacreacion()) %></td>
				</tr>
				<tr>
					<td class="label" >Proveido:</td>
					<td></td>
				</tr>
				<tr>					
					<td></td>
					<td class="label">
					 <%=objDDIG.getStrContenido()%>  
					</td>
					
				</tr>
					
			</table>
		
		
        
		</div>
		
		
		
		<div dojoType="dojox.layout.ContentPane" region="right" style="width:30%;height:95%;padding:0em;">
			<div dojoType="dijit.Toolbar" style="height:1.8em;text-align:center;">Archivos</div>      

			  <%if(!listArchivo.isEmpty()) {%>	
					
					<table height="16" colspan="5" align="left">
					
					<% 	for (int i=0; i<listArchivo.size(); i++){
					archivo = (Archivo)listArchivo.get(i);
					if((String.valueOf(archivo.getPrincipal())).equals("S")){
					%>		
                                        <tr>  
                                          <td class="itemDocAdicPrincGG" >                  
                                          <img src="images/pdf.PNG" alt="" />
                                          <a onclick="mostrarPDF('<%=archivo.getRutaAlfresco() %>');" alt="Ver Archivo"> <%=archivo.getNombreReal() %></a>                                      
                                          </td>
                                        </tr>
                         <%} else { %>	
                                        <tr> 
                                          <td class="label" >
                                          <%
                                          String miString = String.valueOf(archivo.getNombreReal());
                                          System.out.println("verrrrrrrrrr"+ miString); 
                                          String  extensionTemp = "";
                           		          String  extension= "";
                           		        for (int k=(miString.length()-1);k+1>0;k--) {
                           		            if(miString.charAt(k)=='.'){	     
                           		            	break;
                           		    	    }
                           		            extensionTemp +=miString.charAt(k); 
                           		        }			
                           				for (int j=(extensionTemp.length()-1);j+1>0;j--) {
                           		            extension +=extensionTemp.charAt(j); 
                           		        }	
                                          
                                          if(extension.equals("pdf")){ %>	
                                          <img src="images/pdf.PNG" alt="" />
                                          <%} else if(extension.equals("doc") || extension.equals("docx")){%>
                                          <img src="images/doc.PNG" alt="" />
                                          <%} else if(extension.equals("xls") || extension.equals("xlsx")){%>
                                          <img src="images/xls.PNG" alt="" />
                                          <%} else {%>
                                          <img src="images/xx.gif" alt="" />
                                          <%}%>	
                                          <a  onclick="mostrarPDF('<%=archivo.getRutaAlfresco() %>');" alt="Ver Archivo"> <%=archivo.getNombreReal() %></a>                                      
                                          </td>
                                        </tr>
                         
					<% }%>	
										<tr> 
                                          <td class="label">----------------------------------------------------------</td>
                                        </tr>
			        <% }%>
					
					</table>
			 <% }else{%>
				<table width="100%" border="0">
		         	<tr>
		            	<td height="100" align="center" class="label">NO TIENE ARCHIVOS ADJUNTOS</td>
		         	</tr>
		        </table>
			<%} %>
                        
                    
		     </div>		
	</div>
	
                
                </div>
                
                <div dojoType="dojox.layout.ContentPane" id="contentPaneDocumentosAdicionalesConsulta3" jsId="contentPaneDocumentosAdicionalesConsulta3" title="Notificar">
                
                
                <div dojoType="dijit.layout.BorderContainer" region="center" style="height:100%;">
		<div dojoType="dojox.layout.ContentPane" region="left" style="width:69%; height:95%;margin:0em;padding:0em;">

		<table width="100%">
			<tr>
				 <td>
			     <div dojoType="dijit.Toolbar" style="height:1.8em;">
                            
                            <div dojoType="dijit.form.Button" onClick="javascript:notificar()" iconClass="dijitEditorIcon dijitEditorIconRedo">Notificar</div>
                            
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
		
				
		
		<div dojoType="dojox.layout.ContentPane" region="right" style="width:30%;height:95%;padding:0em;">
			<div dojoType="dijit.Toolbar" style="height:1.8em;text-align:center;">Destinatarios</div>
				<%if(!listUsuario.isEmpty()) {%>
				<form id="listaDestinatarioForm" action="">	
								
					<table height="16" colspan="5" align="left">					
						<tr>
							<td>&nbsp;</td>
							<td class="centerGG" style="font-size:bold;">Para</td>
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
                                             <input type="checkbox" name="paraListaUsuario"  value="<%=usuario.getIdusuario()%>"   />
                                         	</div>  
                                          	</td>
                                           
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