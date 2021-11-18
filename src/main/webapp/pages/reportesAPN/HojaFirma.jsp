<%@page import="com.btg.ositran.siged.domain.FilaHojaFirma"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>
<%@ page import="org.ositran.utils.Constantes"%>
<%@ page import="com.btg.ositran.siged.domain.Parametro"%>
<%@ page import="com.btg.ositran.siged.domain.Unidad" %>
<%@ page import="com.btg.ositran.siged.domain.Expediente" %>
<%@ page import="com.btg.ositran.siged.domain.Documento" %>
<%@ page import="java.text.SimpleDateFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Hoja de Ruta del Documento</title>
		<link rel="stylesheet" type="text/css" href="css/hojaRuta.css" />
		<%
			Documento documento = (Documento)request.getAttribute("documento");
			Expediente expediente = documento.getExpediente();
			List<FilaHojaFirma> hojaFirma = (List<FilaHojaFirma>)request.getAttribute("hojaFirma");
			SimpleDateFormat formato = new SimpleDateFormat("dd - MM - yyyy");
                        List<Parametro> lstPrioridad = (List<Parametro>)request.getAttribute("lstPrioridad");
                        List<String> lstAdjuntos = ( List<String>)request.getAttribute("lstAdjuntos");
		%>
		<script type="text/javascript">
		function imprimirHF() {
            var ventana = window.open("", "", "");
            var contenido = "<html><head>"+
                "<link rel=\"stylesheet\" type=\"text/css\" href=\"css/hojaRuta.css\" />" +
                "</head>    " +
                "<body onload='window.print();' >" +
                document.getElementById("hojaFirma").innerHTML + "</body></html>";
            ventana.document.open();
            ventana.document.write(contenido);
            ventana.document.close();
        }
		</script>
	</head>
	<body>
		<div id="hojaFirma">
                   <img alt="Logo OSITRAN" src="./images/logo.jpg" width="72px" height="40px" />
		<h1 class="titulo">HOJA DE FIRMA</h1>
		
		<table class="resume">
                        <tr><td colspan="4"><br/><br/></td></tr>
                        <tr>
		            <td colspan="2" class="datos_cajetin">DATOS DE LA CARPETA</td> 
                            
                            <td colspan="2"></td>
                        </tr>  
                        
                        <tr>
                         <td colspan="4" style="height:0px"> <hr/> </td>
                        </tr>   
                    
                        <tr>
                               <td class="datos_cajetin" style="width:10em;">Carpeta:</td>
			       <td><%=expediente.getNroexpediente() %></td>
                               <td class="datos_cajetin" style="width:10em;">Serie Documental:</td>
			       <td><%=expediente.getSerie().getNombre() %></td>
                        </tr>
                        
                        <tr>
                               <td class="datos_cajetin" style="width:10em;">Asunto:</td>
			       <td colspan="3"><%=expediente.getAsunto() %></td>
                        </tr>
                        
                        <tr>
                               <td class="datos_cajetin" style="width:10em;">Observaciones:</td>
			       <td colspan="3"><%=expediente.getObservacion() == null ? "": expediente.getObservacion() %></td>
                        </tr>
                        
                         <tr>
                            <td colspan="4" style="height:1px"></td>
                         </tr>  
                        
                        <tr>
		          <td colspan="2" class="datos_cajetin">DATOS DEL DOCUMENTO</td>
                          <td colspan="2"></td>
                        </tr>  
                        
                         <tr>
                            <td colspan="4" style="height:1px"> <hr/>  </td>
                         </tr>
                         
                         <tr>
				
                                <td class="datos_cajetin">N&ordm; de Tr&aacute;mite:</td>
				<td colspan="3"><%=documento.getID_CODIGO() %></td>
				
			</tr>
                        
                    
			<tr>
				<td class="datos_cajetin">N&ordm; de Documento:</td>
				<td><%=documento.getNumeroDocumento() %></td>
                                <td class="datos_cajetin">Tipo:</td>
				<td><%=documento.getTipoDocumento().getNombre() %></td>
			</tr>
			<tr>
				
                                <td class="datos_cajetin">Asunto:</td>
				<td colspan="3"><%=documento.getAsunto() %></td>
				
			</tr>
                        
                        <tr>
                             <td class="datos_cajetin" style="width:10em;">Fecha Documento:</td>
			     <td><%=formato.format(documento.getFechaDocumento()) %></td> 
                             <td class="datos_cajetin">Estado:</td>
                                <%  if (documento.getEstado() == Constantes.ESTADO_ACTIVO) {%> 
				  <td>Registrado</td> 
				<%}%>
                                <%  if (documento.getEstado() == Constantes.ESTADO_PENDIENTE) {%> 
				  <td>Pendiente</td> 
				<%}%>
                                <%  if (documento.getEstado() == Constantes.ESTADO_ATENDER) {%> 
				  <td>Atendido</td> 
				<%}%>
                                <%  if (documento.getEstado() == Constantes.ESTADO_RECHAZADO) {%> 
				  <td>Rechazado</td> 
				<%}%>
                                <%  if (documento.getEstado() == Constantes.ESTADO_ANULADO) {%> 
				  <td>Anulado</td> 
				<%}%>
                        </tr>
			
                        <tr>
				<td class="datos_cajetin">Fecha Limite:</td>
				<td><%=documento.getFechaLimiteAtencion() == null? "": formato.format(documento.getFechaLimiteAtencion()) %></td>
			        <%if (documento.getPrioridad()!=null){%>
                                    <%for(int i=0;i<lstPrioridad.size();i++) {%>
                                      <%if (lstPrioridad.get(i).getValor().equals(documento.getPrioridad().toString())){%>
                                        <td class="datos_cajetin">Prioridad:</td>  
                                        <td><%=lstPrioridad.get(i).getDescripcion()%></td>	
                                      <%}%>  
                                    <%}%>
                                <%}%>
                        
                        </tr>
                        <tr>

                               <td class="datos_cajetin" style="width:10em;">Observaciones:</td>
			       <td colspan="3"><%=documento.getObservacion() == null ? "": documento.getObservacion() %></td> 
			</tr>
                        
                        <%if (lstAdjuntos!=null && lstAdjuntos.size()>0){%> 
                           <tr>
                              
                              <td class="datos_cajetin" COLSPAN="1">Adjuntos:</td>
                              <td class="datos_cajetin" COLSPAN="3"></td>
		           </tr>
                           <%for(int i=0;i<lstAdjuntos.size();i++){%>
                              <tr>
                                
                                 <td class="datos_cajetin"></td>
                                 <td><%=lstAdjuntos.get(i)%></td>	
                             </tr> 
                           <%}%>
			<%}%>
                        
                        <%if (documento.getTipoDocumento().getIdtipodocumento().toString().equals(Constantes.COD_TIPODOCUMENTO_402) ||
                          documento.getTipoDocumento().getIdtipodocumento().toString().equals(Constantes.COD_TIPODOCUMENTO_404) ||
                          documento.getTipoDocumento().getIdtipodocumento().toString().equals(Constantes.COD_TIPODOCUMENTO_405)){%>
                          
                        <%}else{%>
                               <%if (documento.getID_CLIENTE()!=null){%>
                                  <%if (documento.getID_EXTERNO().toString().equals("0")){%>
                                        <tr>
                                            <td colspan="4" style="height:1px"></td>
                                        </tr> 
                                        <tr>
                                             <td colspan="2" class="datos_cajetin">DATOS DEL DESTINATARIO</td>
                                        </tr>
                                        <tr>
                                            <td colspan="4" style="height:1px"> <hr/> </td>
                                        </tr>  
                                   <%}else{%>
                                        <tr>
                                            <td colspan="4" style="height:1px"></td>
                                        </tr>
                                        <tr>
                                             <td colspan="2" class="datos_cajetin">DATOS DEL REMITENTE</td>
                                        </tr>
                                        <tr>
                                            <td colspan="4" style="height:1px"> <hr/> </td>
                                        </tr>
                                   <%}%>
                                   
                                   <% if (documento.getCodTipoInstitucion()!=null){%>
                                       <%if (documento.getCodTipoInstitucion().toString().equals(Constantes.COD_PERSONA_JURIDICA_INSTITUCION)){%>
                                           
                                           <tr>
                                                <td class="datos_cajetin">Instituci&oacute;n:</td>
                                                <td><%=documento.getCliente().getRazonSocial()==null?"":documento.getCliente().getRazonSocial()%></td>	
                                           </tr>
                                           <% if (documento.getDesRemitente()!=null){%>
                                                <tr>
                                                     <td class="datos_cajetin">Persona:</td>
                                                     <td><%=documento.getDesRemitente()%></td>	
                                                </tr>
                                           <%}%>
                                           <%if (documento.getDesCargoRemitente()!=null){%>
                                                <tr>
                                                    <td class="datos_cajetin">Cargo:</td>
                                                    <td><%=documento.getDesCargoRemitente()%></td>	
                                               </tr>
                                           <%}%>    
                                       <%}%>
                                       <%if (documento.getCodTipoInstitucion().toString().equals(Constantes.COD_PERSONA_JURIDICA_EMPRESA)){%>
                                           
                                           <tr>
                                                <td class="datos_cajetin">Empresa:</td>
                                                <td><%=documento.getCliente().getRazonSocial()==null?"":documento.getCliente().getRazonSocial()%></td>	
                                           </tr>
                                           <% if (documento.getDesRemitente()!=null){%>
                                             <tr>
                                                <td class="datos_cajetin">Persona:</td>
                                                <td><%=documento.getDesRemitente()%></td>	
                                             </tr>
                                           <%}%>
                                           <%if (documento.getDesCargoRemitente()!=null){%>
                                                <tr>
                                                    <td class="datos_cajetin">Cargo:</td>
                                                    <td><%=documento.getDesCargoRemitente()%></td>	
                                               </tr>
                                           <%}%>
                                       <%}%>
                                        <%if (documento.getCodTipoInstitucion().toString().equals(Constantes.COD_PERSONA_NATURAL)){%>
                                          <tr>
                                                <td class="datos_cajetin">Persona Natutal:</td>
                                                <td><%=(documento.getCliente().getNombres()==null?"":documento.getCliente().getNombres()) + " " + (documento.getCliente().getApellidoPaterno()==null?"":documento.getCliente().getApellidoPaterno()) + " " +  (documento.getCliente().getApellidoMaterno()==null?"":documento.getCliente().getApellidoMaterno())%></td>	
                                           </tr>
                                           <%if (documento.getDesCargoRemitente()!=null){%>
                                                <tr>
                                                    <td class="datos_cajetin">Cargo:</td>
                                                    <td><%=documento.getDesCargoRemitente()%></td>	
                                               </tr>
                                           <%}%>
                                        <%}%>   
                                   <%}%>                                                               
                            <%}%>
                        <%}%>
                        
                       

			<%--
			<tr>
				<td><strong>Fecha Registro: </strong><%=formato.format(documento.getFechaCreacion()) %></td>
				<td><strong>Remitente: </strong><%=traza.getRemitente().getNombreCompleto() %></td>
			</tr>
			<tr>
				<td><strong>N&ordm; de Expediente: </strong><%=expediente.getNroexpediente() %></td>
				<td><strong>Cargo: </strong><%=traza.getRemitente().getRoles().get(0).getNombre() %></td>
			</tr>
			<tr>
				<td><strong>Tipo de Documento: </strong><%=documento.getTipoDocumento().getNombre() %></td>
				<td><strong>Cliente: </strong>
					<%if(expediente.getCliente() != null && expediente.getCliente().getIdCliente() != null){
						out.println(expediente.getCliente().getNombreRazon());
					}else{
						out.println("-");
					}%>
				</td>
			</tr>
			<tr>
				<td><strong>N&ordm; de Documento: </strong><%=documento.getNumeroDocumento() %></td>
				<td><strong>Proceso: </strong><%=expediente.getProceso().getNombre() %></td>
			</tr>
			<tr>
				<td colspan="2"><strong>Asunto: </strong><%=traza.getAsunto() %></td>
			</tr>
			--%>
		</table>
		
                        <br/>
		
		<%if(hojaFirma != null && !hojaFirma.isEmpty()){ %>
			<table class="datos">
				<tr>
                                        <td class="datos_subtitulo" style="width:7em;">Fecha/Hora-Firma</td>
					<td class="datos_subtitulo" style="width:20em;">Usuario (Area)</td>
					<td class="datos_subtitulo" style="width:20em;">Archivo</td>
                                        <td class="datos_subtitulo" style="width:20em;">Certificado Digital</td>
					<td class="datos_subtitulo" style="width:20em;">Tipo</td>
				</tr>
				<%for(FilaHojaFirma fila : hojaFirma){ %>
				<tr>
                                        <td class="datos_contenido"><%=new SimpleDateFormat("dd-MM-yyyy HH:mm").format(fila.getFechaFirma())%></td>
					<td class="datos_contenido"><%=fila.getUsuario() %></td>
                                        <%if (fila.getPrincipal().equals("S")){%>
                                        <td class="datos_contenido"><b><font color="blue"><%=fila.getNombreFile()%></font></b></td>             
                                        <%}else{%>
                                          <td class="datos_contenido"><%=fila.getNombreFile() %></td>
                                        <%}%>
                                        <td class="datos_contenido"><%=fila.getAlias()%></td>
                                        <td class="datos_contenido"><%=fila.getTipo().equals("")?"": (fila.getTipo().equals("V")?"Visado":"Firmado") %></td>
				</tr>
				<%} %>
			</table>
		<%}else{ %>
                      <table class="resume">
                                <tr>
                                    <td></td>
                                </tr>    
				<tr>
                                    <td align="center" class="datos_cajetin" style="width:7em;">
                                        No existen registros en la HOJA DE FIRMA para este documento.
                                    </td>
                                </tr>
                      </table>          
		<%} %>
		
		</div>
		<p class="botones">
			<input type="button" onClick="imprimirHF()" value="Imprimir" />
		</p>
	</body>
</html>