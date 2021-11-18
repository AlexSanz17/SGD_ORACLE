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
		<%     
         String contDocumentosRecibidos = (String)request.getAttribute("contDocumentosRecibidos");
         %>
	   <style type="text/css">
         
         html, body, #divInformativosRecibidos {
            width: 100%; height: 68%;
            border: 0; padding: 0; margin: 0;
           
         }
         
      </style>
		<script type="text/javascript">		
         dojo.addOnLoad(function() {   
            var divInformativosRecibidos = dojo.byId("divInformativosRecibidos");                       
            buildInformativosRecibidos(divInformativosRecibidos,null,true);
           
         });
      </script>
      		<script type="text/javascript">
			function limpiarCampos() {
				dijit.byId("idDocInfObjFiltro.numeroExpediente").attr("value", "");
				dijit.byId("idDocInfObjFiltro.numeroDocumento").attr("value", "");
				dijit.byId("idDocInfObjFiltro.asuntodocumento").attr("value", "");
				dijit.byId("idDocInfObjFiltro.asuntoexpediente").attr("value", "");
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
	        
				   dijit.byId("idDocInfObjFiltro.numeroExpediente").destroy();
				   dijit.byId("idDocInfObjFiltro.numeroDocumento").destroy();
				   dijit.byId("idDocInfObjFiltro.asuntodocumento").destroy();
				   dijit.byId("idDocInfObjFiltro.asuntoexpediente").destroy();
				   dijit.byId("II_area").destroy();
				   //dijit.byId("II_prioridad").destroy();
				   dijit.byId("idDocInfFormFiltroBandeja").destroy();
			}
			
		
			function filtrarInformativosRecibidos(){
				
				var dataForm = dijit.byId("idDocInfFormFiltroBandeja").attr("value");
				var area = dijit.byId('II_area').getValue();
				///var prioridad = dijit.byId('II_prioridad').getValue();
				
				//Parametros para busqueda Todos
				if (area == "0" ) {
					dataForm.objFiltro.areaOrigen = "0" ;
				}else{
					dataForm.objFiltro.areaOrigen = area ;
				}
				
				/*if (prioridad == "-1") {
					dataForm.objFiltro.prioridad = "0" ;
				}else{
					dataForm.objFiltro.prioridad = prioridad ;
				}
				*/
				
				//Validacion
				correctoRep1 = true;
				
				if(area==null||area==""){
					correctoRep1 = false;
				}
				/*
				if(prioridad==null||prioridad==""){
					correctoRep1 = false;
				}				*/
				
				//Mostrar pagina  
				
				if (correctoRep1) {
					if(dijit.byId("dlgProgresBar")!=null){
			        	dijit.byId("dlgProgresBar").show() ;
			        }
					dijit.byId("contextMenuInformativosRecibidos").destroy();
					dijit.byId("borderContainerInformativosRecibidos").destroy();
					var divInformativosRecibidos = dojo.byId("divInformativosRecibidos");                        
					buildInformativosRecibidos(divInformativosRecibidos,dataForm.objFiltro,false);
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
				}, "II_area");
				
				
					
				
			});
		</script>
		
	</head>
	<body style="overflow:auto;" >
	 <!-- 
	 <div dojoType="dijit.Toolbar" style="height:1.8em;text-align:center;"> Documentos</div>
	  -->
	<div id="divInformativosRecibidos" ></div>
	<br />	 
		 
	<form dojoType="dijit.form.Form" id="idDocInfFormFiltroBandeja">
		
		
		<div dojoType="dojox.layout.ContentPane" region="bottom" style="padding:0em;width:100%;">
			<div dojoType="dijit.Toolbar" style="text-align:center;">Filtros de B&uacute;squeda</div>
			<table style="width:100%">			     
				<tr>
					<td>Nro. Documento</td>
					<td><input id="idDocInfObjFiltro.numeroDocumento" name="objFiltro.numeroDocumento" dojoType="dijit.form.TextBox" style="width:10em;" /></td>
				</tr>
				<tr>
			     	<td>Nro. Expediente</td>
					<td><input id="idDocInfObjFiltro.numeroExpediente" name="objFiltro.numeroExpediente" dojoType="dijit.form.TextBox" style="width:10em;" /></td>
			    </tr>
				
				<tr>
					<td>&Aacute;rea Remitente</td>
					<td><input type="text" id="II_area" style="width:10em;" /></td>
				</tr>
				<tr>
					<td>Asunto Doc.</td>
					<td <input id="idDocInfObjFiltro.asuntodocumento" name="objFiltro.asuntodocumento" dojoType="dijit.form.TextBox" style="width:10em;"/></td>
				</tr>
				<tr>
					<td>Asunto Exp.</td>
					<td ><input id="idDocInfObjFiltro.asuntoexpediente" name="objFiltro.asuntoexpediente" dojoType="dijit.form.TextBox" style="width:10em;"/></td>
				</tr>
						 	
			 	<tr>
			    	<td><button id="btnBuscarDI" dojoType=dijit.form.Button type="button" iconClass="siged16 sigedSearch16" showLabel="true" onClick="filtrarInformativosRecibidos();">Buscar</button></td>
			    	<td><button id="btnLimpiarDI" dojoType=dijit.form.Button type="button" iconClass="siged16 sigedEraser16" showLabel="true" onClick="limpiarCampos">Limpiar</button></td>			 
			 	</tr>
			</table>
			
		</div>
	</form>
	</body>
</html>