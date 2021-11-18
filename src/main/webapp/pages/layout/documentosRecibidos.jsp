<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@page import="com.btg.ositran.siged.domain.FilaBandejaUF" %>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.List" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Insert title here</title>
	   <style type="text/css">
         
         
         
      </style>
		<script type="text/javascript">		
         dojo.addOnLoad(function() {   
        	 
           //  var divDocumentosRecibidos = dojo.byId("divDocumentosRecibidos");   
        	 //if(dijit.byId("dlgProgresBar")!=null){
        	    	//dijit.byId("dlgProgresBar").show() ;
        	   //  
        	  //   var cargando = document.getElementById("cargandoIG");
               //cargandoIG.style.display="";
               //var cargandoIG = dojo.byId("cargandoIG");
               buildDocumentosRecibidos(null,true);
           
        	   
         });
         
         function cargar() {
        	// dijit.byId("dlgProgresBar").show() ;
			}
      </script>
      		<script type="text/javascript">
			function limpiarCampos() {
				dijit.byId("idDocRecObjFiltro.numeroExpediente").attr("value", "");
				dijit.byId("idDocRecObjFiltro.numeroDocumento").attr("value", "");
				dijit.byId("idDocRecObjFiltro.asuntodocumento").attr("value", "");
				dijit.byId("idDocRecObjFiltro.asuntoexpediente").attr("value", "");
				/*if(dijit.byId("IG_area")){
					dijit.byId("IG_area").attr("value", "");
				}
				if(dijit.byId("IG_prioridad")){
					dijit.byId("IG_prioridad").attr("value", "");
				}
				*/
				
			}
			
			function destroyDiv() {
	        // Destroy Div wvcarrasco 17-02-2012			
	        
				   dijit.byId("idDocRecObjFiltro.numeroExpediente").destroy();
				   dijit.byId("idDocRecObjFiltro.numeroDocumento").destroy();
				   dijit.byId("idDocRecObjFiltro.asuntodocumento").destroy();
				   dijit.byId("idDocRecObjFiltro.asuntoexpediente").destroy();
				   dijit.byId("IG_area").destroy();
				   dijit.byId("IG_prioridad").destroy();
				   dijit.byId("idDocRecFormFiltroBandeja").destroy();
			}
			
		
			function filtrarDocumentosRecibidos(){
				
				var dataForm = dijit.byId("idDocRecFormFiltroBandeja").attr("value");
				 var area = dijit.byId('IG_area').getValue();
				var prioridad = dijit.byId('IG_prioridad').getValue();
				
				// Parametros para busqueda Todos
				if (area == "0" ) {
					dataForm.objFiltro.areaOrigen = "0" ;
				}else{
					dataForm.objFiltro.areaOrigen = area ;
				}
				
				if (prioridad == "-1") {
					dataForm.objFiltro.prioridad = "-1" ;
				}else{
					dataForm.objFiltro.prioridad = prioridad ;
				}
				
				//dataForm.objFiltro.areaOrigen = "0" ;
				//dataForm.objFiltro.prioridad = "-1" ;
				
				//Validacion
				correctoRep1 = true;
				
				if(area==null||area==""){
					correctoRep1 = false;
				}
				
				if(prioridad==null||prioridad==""){
					correctoRep1 = false;
				}
				
				//Mostrar pagina  
				
				if (correctoRep1) {
					/*if(dijit.byId("dlgProgresBar")!=null){
			        	dijit.byId("dlgProgresBar").show() ;
			        }*/
					//dijit.byId("contextMenuDocumentosRecibidos").destroy();
					//dijit.byId("borderContainerDocumentosRecibidos").destroy();
					//var divDocumentosRecibidos = dojo.byId("divDocumentosRecibidos");                        
		            buildDocumentosRecibidos(dataForm.objFiltro,false);
		            dijit.byId("dlgProgresBar").hide() ;
				}else{
					alert("Error en Filtros");
				}	            
			}
			
			dojo.addOnLoad(function(){				

				new dijit.form.FilteringSelect({
					jsId : "area",
					name : "area",
					store : new dojo.data.ItemFileReadStore({
						url : "autocompletarAreaTodos.action"
					}),
					searchAttr : "label",
					labelAttr : "label",
					value: 0,
					autoComplete : true,
					hasDownArrow : true,
					style          : "width:10em;",
					highlightMatch : "all",
					invalidMessage : "Seleccione el Area"
				}, "IG_area");
				
				
				new dijit.form.FilteringSelect({
					jsId : "prioridad",
					name : "prioridad",
					store : new dojo.data.ItemFileReadStore({
						url : "autocompletarPrioridadTodos.action"
					}),
					searchAttr : "label",
					labelAttr : "label",
					value: -1,
					autoComplete : true,
					hasDownArrow : true,
					style          : "width:10em;",
					highlightMatch : "all",
					invalidMessage : "Seleccione prioridad"
				}, "IG_prioridad");		
				
			});
			
		</script>
		
	</head>
	<body style="overflow:auto;"  >
	 <!-- 
	 <div dojoType="dijit.Toolbar" style="height:1.8em;text-align:center;">%=contDocumentosRecibidos%> Documentos</div>
	  -->
	<div id="divCont" style="width:20em;display: none" >Cargando....</div>
	
	<div id="divTreeExpediente" style="width:26em;display: none" ></div>		 
	<br />		 
		
	    
        
	<form dojoType="dijit.form.Form" id="idDocRecFormFiltroBandeja">
		
		
		<div id="idFiltroIG" dojoType="dojox.layout.ContentPane" region="bottom" style="padding:0em;width:100%; display: none" >
			<div dojoType="dijit.Toolbar" style="text-align:center;">Filtros de B&uacute;squeda</div>
			<table style="width:100%">			     
				<tr>
					<td>Nro. Documento</td>
					<td><input id="idDocRecObjFiltro.numeroDocumento" name="objFiltro.numeroDocumento" dojoType="dijit.form.TextBox" style="width:10em;" /></td>
				</tr>
				<tr>
			     	<td>Nro. Expediente</td>
					<td><input id="idDocRecObjFiltro.numeroExpediente" name="objFiltro.numeroExpediente" dojoType="dijit.form.TextBox" style="width:10em;" /></td>
			    </tr>
				
				<tr>
					<td>&Aacute;rea Remitente</td>
					<td><input type="text" id="IG_area" style="width:10em;" /></td>
				</tr> 
				<tr>
					<td>Asunto Doc.</td>
					<td <input id="idDocRecObjFiltro.asuntodocumento" name="objFiltro.asuntodocumento" dojoType="dijit.form.TextBox" style="width:10em;"/></td>
				</tr>
				<tr>
					<td>Asunto Exp.</td>
					<td ><input id="idDocRecObjFiltro.asuntoexpediente" name="objFiltro.asuntoexpediente" dojoType="dijit.form.TextBox" style="width:10em;"/></td>
				</tr>
				
				<tr>
			    	<td>Prioridad:</td>
			    	<td><input type="text" id="IG_prioridad" style="width:10em;" /></td>			 
			 	</tr>			 	
			 	<tr>
			    	<td><button id="btnBuscarDR" dojoType=dijit.form.Button type="button" iconClass="siged16 sigedSearch16" showLabel="true" onClick="filtrarDocumentosRecibidos();">Buscar</button></td>
			    	<td><button id="btnLimpiarDR" dojoType=dijit.form.Button type="button" iconClass="siged16 sigedEraser16" showLabel="true" onClick="limpiarCampos">Limpiar</button></td>			 
			 	</tr>
			</table>
		</div>
		
	</form>	
	     
	</body>
</html>