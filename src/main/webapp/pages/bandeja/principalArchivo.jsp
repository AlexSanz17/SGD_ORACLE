<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.btg.ositran.siged.domain.Archivo" %>
<%@page import="java.util.ArrayList" %>
<%@page import="org.ositran.utils.Constantes" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Insert title here</title>
			<%
     //carga el el idAreaOrigen por Defecto
    String idDoc = (String)request.getAttribute("idDoc");
	Archivo archivo;
	ArrayList listArchivo = (ArrayList)request.getAttribute("listArchivo");
	
    %>
		<script type="text/javascript">
			var estructura = [
			{
				field: 'nombre',
				name: 'Archivo',
				width: "30em"
			},
			{
			    field: 'idArchivo',
			    name: 'id',
			    hidden: true
			},
			{
				editable: true,
	            field: "selected",
	            name: "Principal",
	            noresize: true,
	            type: dojox.grid.cells.Bool,
	            width: "8.5em"
			}];
		
			
			function principalAdjunto(){
				
				var archivos = new Array();
            	var contPara = 0;
                try{
                    //var checkboxes=document.getElementById("listaArchivoForm").listaArchivo;	
                    var checkboxes = document.getElementsByName("listaArchivo");
                    var k;
   				   for(k =0;k<checkboxes.length;k++ ){
   						if(checkboxes[k].checked){
   							archivos.push(checkboxes[k].value);
   							contPara ++;
             	    	}						
   					 }              
                }
              catch(err){  }    
				
				if(archivos.length){
					if(archivos.length<2){
						if(confirm("Desea marcar como principal al archivo que selecciono?")){
							dijit.byId("dlgProgresBar").show();
							
							dojo.xhrPost({
								url: "doSetPrincipalArchivo.action",
							    content: {
							    	arrIdArchivos : archivos
								},
							    load: function(data) {
							    	var sURL="doViewDocUsuarioFinal.action?iIdDoc="+"<s:property value='iIdDoc' />";
							    	dijit.byId("contentPaneDetail").setHref(sURL);
									
							    	dijit.byId("dlgProgresBar").hide();
							    	dijit.byId("dlgAnexar").hide();
							    	dijit.byId("dlgAnexar").destroyRecursive();
								}
							});  
						}
					}else{
						alert("seleccionar solo un archivo");
					}					
					
				}else{
					alert("Primero debe seleccionar un archivo");
				}
			}
			

		</script>
	</head>
	<body>
		<div dojoType="dijit.layout.BorderContainer" region="top" style="height:100%;">
		
		<div dojoType="dojox.layout.ContentPane" style="width:100%; height:100%;">
			
					<div id="divPrincipalArchivo" style="height:12em;">
		
				<%if(!listArchivo.isEmpty()) {%>
				<form id="listaArchivoForm" action="">	
								
					<table width="100%" >					
						<tr >
						    <td>&nbsp;</td>
							<td >Archivo </td>
							<td >es Principal</td>
							
				    	</tr>
						<% 	for (int i=0; i<listArchivo.size(); i++){
						archivo = (Archivo)listArchivo.get(i);
						%>		
                                        <tr>
                                          <td>&nbsp;</td>  
                                          <td class="label" >
                                           <!-- 
                                           <=archivo.getNombreReal() %>  -->  
                                           <%=archivo.getNombre() %>                                     
                                          </td>
                                          <td >
                                        	<div align="center">
                                        	
                                        	<%if(archivo.getPrincipal().equals(Constantes.ARCHIVO_PRINCIPAL)) {%>
                                        	<input type="checkbox" name="listaArchivo" value="<%=archivo.getIdArchivo()%>" checked="checked"  />
                                        	<% }else{%>
											<input type="checkbox" name="listaArchivo" value="<%=archivo.getIdArchivo()%>"   />
											<%} %> 
                                         	</div>  
                                          </td>
                                           
                                        </tr>                        
			      	  <% }%>
			      	  
					</table>
					</form>
			 <% }else{%>
				<table >
		         	<tr>
		            	<td height="100" align="center" class="label">NO TIENE ARCHIVOS ADJUNTOS</td>
		         	</tr>
		        </table>
			<%} %>		
		    </div>
			
		</div>
		

		 </div>
		 
		 <div dojoType="dijit.layout.BorderContainer" region="center" style="height:10%;">
		 <div align="right">
		 <%if(!listArchivo.isEmpty()) {%>
		 <input type="button" dojoType="dijit.form.Button"  label="Principal" onClick="principalAdjunto()"  />
		 <%} %>	
		 </div>		    
		 </div>
		 
	</body>
</html>