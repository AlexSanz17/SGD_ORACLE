<%@page import="com.btg.ositran.siged.domain.IotdtcDespacho"%>
<%@page import="org.ositran.utils.Constantes"%>
<%@page import="com.btg.ositran.siged.domain.Parametro"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>
<%@ page import="com.btg.ositran.siged.domain.Unidad" %>
<%@ page import="com.btg.ositran.siged.domain.Expediente" %>
<%@ page import="com.btg.ositran.siged.domain.Documento" %>
<%@ page import="com.btg.ositran.siged.domain.Trazabilidaddocumento" %>
<%@ page import="com.btg.ositran.siged.domain.FilaHojaRuta" %>
<%@ page import="java.text.SimpleDateFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Hoja de Ruta del Documento</title>
		<link rel="stylesheet" type="text/css" href="css/hojaRuta.css" />
		<%
			//Trazabilidaddocumento traza = (Trazabilidaddocumento)request.getAttribute("trazabilidad");
			Documento documento = (Documento)request.getAttribute("documento");
                        List<IotdtcDespacho> lstIotdtcDespacho = (List<IotdtcDespacho>)request.getAttribute("listIotdtcDespacho");
			Expediente expediente = documento.getExpediente();
			List<FilaHojaRuta> hojaRuta = (List<FilaHojaRuta>)request.getAttribute("hojaRuta");
			SimpleDateFormat formato = new SimpleDateFormat("dd - MM - yyyy");
                        List<Parametro> lstPrioridad = (List<Parametro>)request.getAttribute("lstPrioridad");
                        List<String> lstAdjuntos = ( List<String>)request.getAttribute("lstAdjuntos");
		%> 
		<script type="text/javascript">
                    
		function imprimirHR() {
            var ventana = window.open("", "", "");
            var contenido = "<html><head>"+
                "<link rel=\"stylesheet\" type=\"text/css\" href=\"css/hojaRuta.css\" />" +
                "</head>    " +
                "<body onload='window.print();' >" +
                document.getElementById("hojaRuta").innerHTML + "</body></html>";
            ventana.document.open();
            ventana.document.write(contenido);
            ventana.document.close();
        }
		</script>
	</head>
	<body>
		<div id="hojaRuta">
                    <img alt="Logo APN" src="./images/logo.jpg" width="72px" height="40px" />
		<h1 class="titulo">HOJA DE RUTA</h1>
		
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
                        
                        <%if (documento.getNumeroFoliosPIDE()!=null){%>
                            <tr>
                                <td class="datos_cajetin">N&ordm; de Folios PIDE:</td>
                                <td><%=documento.getNumeroFoliosPIDE()>0 ? documento.getNumeroFoliosPIDE():"" %></td>
                                <td class="datos_cajetin"></td>
                                <td></td>

                            </tr>
                        <%}%>
                        <tr>
                            <td class="datos_cajetin">N&ordm; de Folios Totales:</td>
		            <td><%=documento.getNumeroFolios()>0 ? documento.getNumeroFolios():"" %></td>
                            <td class="datos_cajetin"></td>
			    <td></td>
                            
                        </tr>
                        
                        
                        <%if (documento.getNumeroFoliosOriginales()!=null){%>
                            <tr>
                                <td class="datos_cajetin">N&ordm; de Folios Originales:</td>
                                <td><%=documento.getNumeroFoliosOriginales() %></td>
                                <td class="datos_cajetin"></td>
                                <td></td>

                            </tr>
                        <%}%>
                        <%if (documento.getNumeroFoliosCopias()!=null){%>
                            <tr>
                                <td class="datos_cajetin">N&ordm; de Folios Copias:</td>
                                <td><%=documento.getNumeroFoliosCopias() %></td>
                                <td class="datos_cajetin"></td>
                                <td></td>

                            </tr>
                        <%}%>
                        <%if (documento.getImagenesDigitalizadas()!=null){%>
                            <tr>
                                <td class="datos_cajetin">Imagenes Digitalizadas:</td>
                                <td><%=documento.getImagenesDigitalizadas() %></td>
                                <td class="datos_cajetin"></td>
                                <td></td>

                            </tr>
                        <%}%>
     
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
                                                <td colspan="3"><%=documento.getCliente().getRazonSocial()==null?"":documento.getCliente().getRazonSocial()%></td>	
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
                                                <td colspan="3"><%=documento.getCliente().getRazonSocial()==null?"":documento.getCliente().getRazonSocial()%></td>	
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
                        
                         <tr>
                            <td colspan="4" style="height:1px"></td>
                         </tr>  
                         
                         <%  if (documento.getNroVirtual()!=null && documento.getID_EXTERNO()!=null && documento.getID_EXTERNO().toString().equals("1")){%>
                              <tr>
                                 <td colspan="2" class="datos_cajetin"> <font color="#2E2EFE">DATOS DE LA RECEPCION VIRTUAL</font></td>
                                 <td colspan="2"></td>
                              </tr>
                               <tr>
                                    <td colspan="4" style="height:1px"> <hr/> </td>
                                </tr>
                              
                               <tr>				
                                    <td class="datos_cajetin">N&ordm; de CUO:</td>
                                    <td colspan="1"><%=documento.getVcuo()==null?"":documento.getVcuo() %></td>  
                                    
                                    <td class="datos_cajetin">Estado:</td>
                                    <% if (documento.getRecepcionado()!=null && documento.getRecepcionado().equals("R")) {%> 
                                       <td colspan="1">Recepcionado</td> 
                                    <%}%>
                                    <% if (documento.getRecepcionado()!=null && documento.getRecepcionado().equals("O")) {%> 
                                       <td colspan="1">Observado</td> 
                                    <%}%>
                                    <% if (documento.getRecepcionado()!=null && documento.getRecepcionado().equals("S")) {%> 
                                       <td colspan="1">Subsanado</td> 
                                    <%}%>
                                 </tr>
                                 <tr>	
                                    <td class="datos_cajetin">Observaciones:</td>
                                    <td colspan="3"><%=documento.getObservacion()==null?"":documento.getObservacion() %></td>    
                                </tr>
                        <%}%>
                        
                        <%  if (lstIotdtcDespacho!=null && lstIotdtcDespacho.size()>0){%>
                           
                              <tr>
                                  <td colspan="2" class="datos_cajetin"> <font color="#2E2EFE">DATOS DEL CARGO VIRTUAL</font></td>
                                <td colspan="2"></td>
                              </tr>  
                             <%for(int i=0;i<lstIotdtcDespacho.size();i++){ %>
                                    <tr>
                                       <td colspan="4" style="height:1px"> <hr/>  </td>
                                    </tr>
                                    
                                    <tr>
                                         <td class="datos_cajetin" style="width:10em;">N&ordm; de CUO:</td>
                                         <td colspan="3"><%=lstIotdtcDespacho.get(i).getVcuo() == null ? "": lstIotdtcDespacho.get(i).getVcuo() %></td>
                                    </tr>

                                    <tr>				
                                         <td class="datos_cajetin">N&ordm; de Tr&aacute;mite:</td>
                                         <td><%=lstIotdtcDespacho.get(i).getVnumregstdrec() %></td>
                                         <td class="datos_cajetin">Estado:</td>
                                         <% if (lstIotdtcDespacho.get(i).getCflgest() == 'R') {%> 
                                            <td>Recepcionado</td> 
                                         <%}%>
                                         <% if (lstIotdtcDespacho.get(i).getCflgest() == 'O') {%> 
                                            <td>Observado</td> 
                                         <%}%>
                                         <% if (lstIotdtcDespacho.get(i).getCflgest() == 'S') {%> 
                                            <td>Subsanado</td> 
                                         <%}%>
                                     </tr>

                                     <tr>
                                          <td class="datos_cajetin">Unidad Organica:</td>
                                          <td><%=lstIotdtcDespacho.get(i).getVuniorgstdrec() %></td>
                                          <td class="datos_cajetin">Usuario Registro:</td>
                                          <td><%= lstIotdtcDespacho.get(i).getVusuregstdrec() %></td>
                                     </tr>

                                   <tr>
                                         <td class="datos_cajetin" style="width:10em;">Observaciones:</td>
                                         <td colspan="3"><%=lstIotdtcDespacho.get(i).getVobs() == null ? "": lstIotdtcDespacho.get(i).getVobs() %></td>
                                  </tr>
                               <%}%>   
                        <%}%>
                     
		</table>
		
                        
                        
		<p>
		<%if(hojaRuta != null && !hojaRuta.isEmpty()){ %>
			<table class="datos">
				<tr>
					<td class="datos_subtitulo" style="width:7em;">Fecha/Hora</td>
					<td class="datos_subtitulo" style="width:15em;">Remitente (Area)</td>
					<td class="datos_subtitulo" style="width:15em;">Destinatario (Area)</td>
					<td class="datos_subtitulo" style="width:5em;">Movimiento</td>
                                        <td class="datos_subtitulo" style="width:5em;">Acci&oacute;n</td>
                                        <td class="datos_subtitulo" style="width:12em;">Prove&iacute;do</td>
				</tr>
				<%for(FilaHojaRuta fila : hojaRuta){ %>
				<tr>
					<td class="datos_contenido"><%=new SimpleDateFormat("dd-MM-yyyy HH:mm").format(fila.getFechaCreacion())%></td>
					<% if (fila.getCantidadhoja()!=null){%>
                                          <td class="datos_contenido" rowspan= "<%=fila.getCantidadhoja()%>" > <%=fila.getRemitente() %></td>
					<%}%>
                                        <td class="datos_contenido"><%=fila.getDestinatario() %></td>
					<td class="datos_contenido"><%=fila.getAccion() %></td>
                                        <td class="datos_contenido" ><%=fila.getProveido() %></td>
					<td class="datos_contenido" ><%=fila.getContenido() %></td>
                                        
				</tr>
				<%} %>
			</table>
		<%}else{ %>
			No existen registros en la trazabilidad para este documento.
		<%} %>
		</p>
		</div>
		<p class="botones">
			<input type="button" onClick="imprimirHR()" value="Imprimir" />
		</p>
	</body>
</html>