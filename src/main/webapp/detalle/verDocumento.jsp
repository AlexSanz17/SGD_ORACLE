<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>
<%@page  import="java.util.Date"%>
<%@ page import="com.btg.ositran.siged.domain.Parametro" %>
<%@ page import="org.ositran.utils.Constantes" %>
<%@ page import="com.btg.ositran.siged.domain.Documento" %>
<%@ page import="com.btg.ositran.siged.domain.DocumentoReunion" %>
<%@ page import="java.text.SimpleDateFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Documento</title>
		<link rel="stylesheet" type="text/css" href="css/hojaRuta.css" />
		<%
	            Documento documento = (Documento)request.getAttribute("documento");
                    List<Parametro> lstMateria = (List<Parametro>)request.getAttribute("parametroMateria");
                    List<Parametro> lstInfraestructura = (List<Parametro>)request.getAttribute("parametroInfraestructura");
                    List<Parametro> lstPrioridad = (List<Parametro>)request.getAttribute("parametroPrioridad");
                    List<DocumentoReunion> lstPersonalInterno = ( List<DocumentoReunion>)request.getAttribute("personalInterno");
                    List<DocumentoReunion> lstPersonalExterno = ( List<DocumentoReunion>)request.getAttribute("personalExterno");
                    List<String> lstAdjuntos = ( List<String>)request.getAttribute("adjuntos");
		%>
		<script type="text/javascript">
		function imprimirHR() {
                    var ventana = window.open("", "", "");
                    var contenido = "<html><head>"+
                        "<link rel=\"stylesheet\" type=\"text/css\" href=\"css/hojaRuta.css\" />" +
                        "</head>    " +
                        "<body onload='window.print();' >" +
                        document.getElementById("verDocumento").innerHTML + "</body></html>";
                    ventana.document.open();
                    ventana.document.write(contenido);
                    ventana.document.close();
                }
		</script>
	</head>
	<body>
	    <div id="verDocumento">	
              <img alt="Logo OSITRAN" src="./images/logo.jpg" width="72px" height="40px" />
              <br><br> 
              <p>  
                <table class="verDocumento" border="0" >
                     <tr>
                         <td colspan="3" style="height:10px"></td>
		    </tr>  
                    <tr ><td colspan="3" align="center" class="titulo"><font size="4.5px"><b><%=documento.getTipoDocumento().getNombre() + " " + documento.getNumeroDocumento() %></b></font></td></tr>
                    <tr ><td colspan="3" align="center" class="titulo"><font size="4.5px"><b>NRO TR&Aacute;MITE: <%=documento.getID_CODIGO() %>  [FECHA:<%=new SimpleDateFormat("dd-MM-yyyy").format(documento.getFechaCreacion())%>]</b></font></td></tr>
                    <tr>
                         <td colspan="3" style="height:28px"></td>
		    </tr>  
                
                    <tr>
                        <td width="15px"></td>
		        <td colspan="2" class="datos_cajetin">DATOS DE LA CARPETA</td>
		    </tr>    
                     <tr>
                         <td colspan="3" style="height:5px"></td>
		    </tr>    
		    <tr>
                        <td width="15px"></td>
		        <td class="datos_cajetin" style="width:200px">Nro:</td>
			<td><%=documento.getExpediente().getNroexpediente() %></td>
                        
		    </tr>
                    <tr>
                        <td width="15px"></td>
			<td class="datos_cajetin" style="width:200px">Asunto:</td>
			<td><%=documento.getExpediente().getAsunto() %></td>	
		    </tr>
                    <tr>
                        <td width="15px"></td>
			<td class="datos_cajetin" style="width:200px">Observaciones:</td>
			<td><%=documento.getExpediente().getObservacion() == null ? "": documento.getExpediente().getObservacion()%></td>	
		    </tr>
                    <% if (documento.getExpediente().getSerie()!=null){%>
                        <tr>
                            <td width="15px"></td>
                            <td class="datos_cajetin" style="width:200px">Serie Documental:</td>
                            <td><%=documento.getExpediente().getSerie().getNombre()==null?"":documento.getExpediente().getSerie().getNombre()%></td>	
                        </tr>
                    <%}else{%>
                        <tr>
                            <td width="15px"></td>
                            <td class="datos_cajetin" style="width:200px">Serie Documental:</td>
                            <td></td>	
                        </tr>
                    <%}%>
                    
                     <tr>
                         <td colspan="3" style="height:9px"></td>
		    </tr>  

                   <tr>
                        <td width="15px"></td>
		        <td colspan="2" class="datos_cajetin">DATOS DEL DOCUMENTO</td>
		    </tr>    
                     <tr>
                         <td colspan="3" style="height:5px"></td>
		    </tr>  
                    
                    <tr>
                        <td width="15px"></td>
			<td class="datos_cajetin">Tipo:</td>
			<td><%=documento.getTipoDocumento().getNombre()%></td>	
		    </tr>
                    
                    <tr>
                        <td width="15px"></td>
			<td class="datos_cajetin">Nro:</td>
			<td><%=documento.getNumeroDocumento()%></td>	
		    </tr>
                    
                    
                    <tr>
                        <td width="15px"></td>
                        <td class="datos_cajetin">Asunto:</td>
			<td><%=documento.getAsunto()==null?"":documento.getAsunto()%></td>	
		    </tr>
                    
                    <tr>
                        <td width="15px"></td>
                        <td class="datos_cajetin">Prioridad:</td>
                        <%for(int i=0;i<lstPrioridad.size();i++) {%>
                          <%if (lstPrioridad.get(i).getValor().equals(documento.getPrioridad().toString())){%>
                            <td><%=lstPrioridad.get(i).getDescripcion()%></td>	
                          <%}%>  
                        <%}%>
		    </tr>
                    
                    <tr>
                        <td width="15px"></td>
			<td class="datos_cajetin">Fecha del Documento:</td>
			<td><%=new SimpleDateFormat("dd-MM-yyyy").format(documento.getFechaDocumento())%></td>	
		    </tr>
                    
                    <tr>
                        <td width="15px"></td>
			<td class="datos_cajetin">Fecha Limite Atenciòn:</td>
			<td><%=documento.getFechaLimiteAtencion() ==null?"":new SimpleDateFormat("dd-MM-yyyy").format(documento.getFechaLimiteAtencion())%></td>	
		    </tr>
                    
                    
                    <tr>
                        <td width="15px"></td>
                        <td class="datos_cajetin">Observaci&oacute;n:</td>
			<td><%=documento.getObservacion()==null?"":documento.getObservacion()%></td>	
		    </tr>
                    
                    <%if (documento.getID_EXTERNO().toString().equals("1")) {%>
                        <tr>
                          <td width="15px"></td>
                          <td class="datos_cajetin">Nro Folios:</td>
			  <td><%=documento.getNumeroFolios()%></td>	
		        </tr>
                        
                        <tr>
                          <td width="15px"></td>
                          <td class="datos_cajetin">Documentos Referenciados:</td>
			  <td><%=documento.getReferenciados()==null?"":documento.getReferenciados()%></td>	
		        </tr>
                        
                        <%if (lstAdjuntos!=null){%>
                           <tr>
                              <td width="15px"></td>
                              <td class="datos_cajetin" COLSPAN="2">Adjuntos:</td>
		           </tr>
                           <%for(int i=0;i<lstAdjuntos.size();i++){%>
                              <tr>
                                 <td width="15px"></td>
                                 <td class="datos_cajetin"></td>
                                 <td><%=lstAdjuntos.get(i)%></td>	
                             </tr> 
                           <%}%>
                        <%}%>
                    <%}%>
                    
                     <tr>
                         <td colspan="3" style="height:9px"></td>
		    </tr>  
 
                    <%if (documento.getTipoDocumento().getIdtipodocumento().toString().equals(Constantes.COD_TIPODOCUMENTO_402) ||
                          documento.getTipoDocumento().getIdtipodocumento().toString().equals(Constantes.COD_TIPODOCUMENTO_404) ||
                          documento.getTipoDocumento().getIdtipodocumento().toString().equals(Constantes.COD_TIPODOCUMENTO_405)){%>
                            <tr>
                                 <td width="15px"></td>
                                 <td colspan="2" class="datos_cajetin">DATOS DEL SICOR</td>
                            </tr>
                            <tr>
                                <td colspan="3" style="height:5px"></td>
                            </tr>
                            <%if (documento.getTipoDocumento().getIdtipodocumento().toString().equals(Constantes.COD_TIPODOCUMENTO_402)){%>
                                 <tr>
                                    <td width="15px"></td>
                                    <td class="datos_cajetin">Instituci&oacute;n:</td>
                                    <td><%=documento.getCliente().getRazonSocial()%></td>	
                                </tr>
                                <%if (lstInfraestructura!=null && lstInfraestructura.size()>0){%>
                                  <%for(int i=0;i<lstInfraestructura.size();i++){%>
                                    <%if (lstInfraestructura.get(i).getValor().equals(documento.getCodInfraestructura().toString())) {%>
                                        <tr>
                                             <td width="15px"></td>
                                             <td class="datos_cajetin">Infraestructura:</td>
                                             <td><%=lstInfraestructura.get(i).getDescripcion()%></td>	
                                         </tr>
                                    <%}%>     
                                  <%}%>  
                                <%}%>    
                                <%if (lstMateria!=null && lstMateria.size()>0){%>
                                  <%for(int i=0;i<lstMateria.size();i++){%>
                                    <%if (lstMateria.get(i).getValor().equals(documento.getCodMateria().toString())) {%>
                                        <tr>
                                             <td width="15px"></td>
                                             <td class="datos_cajetin">Materia:</td>
                                             <td><%=lstMateria.get(i).getDescripcion()%></td>	
                                         </tr>
                                    <%}%>     
                                  <%}%>  
                                <%}%>   
                            <%}%>
                            <%if (documento.getTipoDocumento().getIdtipodocumento().toString().equals(Constantes.COD_TIPODOCUMENTO_404)){%>
                                <%if (lstPersonalInterno!=null && lstPersonalInterno.size()>0){%>
                                       <tr>
                                            <td width="15px"></td>
                                            <td colspan="2" class="datos_cajetin">Personal Interno</td>
                                       </tr>   
                                     <%for(int i=0;i<lstPersonalInterno.size();i++){%>
                                         <tr>
                                             <td width="15px"></td>
                                             <td class="datos_cajetin"></td>
                                             <td><%=lstPersonalInterno.get(i).getNombres()%></td>	
                                         </tr>     
                                   <%}%>
                                <%}%>
                                <%if (lstPersonalExterno!=null && lstPersonalExterno.size()>0){%>
                                       <tr>
                                            <td width="15px"></td>
                                            <td colspan="2" class="datos_cajetin">Personal Externo</td>
                                       </tr>   
                                     <%for(int i=0;i<lstPersonalExterno.size();i++){%>
                                         <tr>
                                             <td width="15px"></td>
                                             <td class="datos_cajetin"></td>
                                             <td><%=lstPersonalExterno.get(i).getNombres()%></td>	
                                         </tr>     
                                   <%}%>
                                <%}%>
                             <%}%>   
                            <%if (documento.getTipoDocumento().getIdtipodocumento().toString().equals(Constantes.COD_TIPODOCUMENTO_405)){%>
                                 <tr>
                                    <td width="15px"></td>
                                    <td class="datos_cajetin">Instituci&oacute;n:</td>
                                    <td><%=documento.getCliente().getRazonSocial()%></td>	
                                </tr>
                                
                                <tr>
                                    <td width="15px"></td>
                                    <td class="datos_cajetin">Fecha:</td>
                                    <td><%=new SimpleDateFormat("dd-MM-yyyy hh:mm").format(documento.getFechaReunion())%></td>	
                                </tr>
                                
                                <tr>
                                    <td width="15px"></td>
                                    <td class="datos_cajetin">Lugar:</td>
                                    <td><%=documento.getLugar()%></td>	
                                </tr>
                                <tr>
                                    <td width="15px"></td>
                                    <td class="datos_cajetin">Objetivo:</td>
                                    <td><%=documento.getObjetivo()%></td>	
                                </tr>
                            <%}%>
                    <%}else{%>
                           <%if (documento.getID_CLIENTE()!=null){%>
                                  <%if (documento.getID_EXTERNO().toString().equals("0")){%>
                                        <tr>
                                             <td width="15px"></td>
                                             <td colspan="2" class="datos_cajetin">DATOS DEL DESTINATARIO</td>
                                        </tr>
                                        <tr>
                                            <td colspan="3" style="height:5px"></td>
                                        </tr>  
                                   <%}else{%>
                                        <tr>
                                             <td width="15px"></td>
                                             <td colspan="2" class="datos_cajetin">DATOS DEL REMITENTE</td>
                                        </tr>
                                        <tr>
                                            <td colspan="3" style="height:5px"></td>
                                        </tr>
                                   <%}%>
                                   
                                   <% if (documento.getCodTipoInstitucion()!=null){%>
                                       <%if (documento.getCodTipoInstitucion().toString().equals(Constantes.COD_PERSONA_JURIDICA_INSTITUCION)){%>
                                           
                                           <tr>
                                                <td width="15px"></td>
                                                <td class="datos_cajetin">Instituci&oacute;n:</td>
                                                <td><%=documento.getCliente().getRazonSocial()==null?"":documento.getCliente().getRazonSocial()%></td>	
                                           </tr>
                                           <%if (documento.getDetalleRemitente()!=null){%>
                                                <tr>
                                                     <td width="15px"></td>
                                                     <td class="datos_cajetin">Persona:</td>
                                                     <td><%=(documento.getDetalleRemitente().getNombres()==null?"":documento.getDetalleRemitente().getNombres()) + " " + (documento.getDetalleRemitente().getApellidoPaterno()==null?"":documento.getDetalleRemitente().getApellidoPaterno()) + " " + (documento.getDetalleRemitente().getApellidoMaterno()==null?"":documento.getDetalleRemitente().getApellidoMaterno())%></td>	
                                                </tr>
                                           <%}%>     
                                           <%if (documento.getCargoRemitente()!=null){%>
                                                <tr>
                                                    <td width="15px"></td>
                                                    <td class="datos_cajetin">Cargo:</td>
                                                    <td><%=documento.getCargoRemitente().getDesCargo()==null?"":documento.getCargoRemitente().getDesCargo()%></td>	
                                               </tr>
                                           <%}%>    
                                       <%}%>
                                       <%if (documento.getCodTipoInstitucion().toString().equals(Constantes.COD_PERSONA_JURIDICA_EMPRESA)){%>
                                           
                                           <tr>
                                                <td width="15px"></td>
                                                <td class="datos_cajetin">Empresa:</td>
                                                <td><%=documento.getCliente().getRazonSocial()==null?"":documento.getCliente().getRazonSocial()%></td>	
                                           </tr>
                                             <tr>
                                                <td width="15px"></td>
                                                <td class="datos_cajetin">Persona:</td>
                                                <td><%=(documento.getDetalleRemitente().getNombres()==null?"":documento.getDetalleRemitente().getNombres()) + " " + (documento.getDetalleRemitente().getApellidoPaterno()==null?"":documento.getDetalleRemitente().getApellidoPaterno()) + " " + (documento.getDetalleRemitente().getApellidoMaterno()==null?"":documento.getDetalleRemitente().getApellidoMaterno())%></td>	
                                           </tr>
                                           <%if (documento.getCargoRemitente()!=null){%>
                                                <tr>
                                                    <td width="15px"></td>
                                                    <td class="datos_cajetin">Cargo:</td>
                                                    <td><%=documento.getCargoRemitente().getDesCargo()==null?"":documento.getCargoRemitente().getDesCargo()%></td>	
                                               </tr>
                                           <%}%>
                                       <%}%>
                                        <%if (documento.getCodTipoInstitucion().toString().equals(Constantes.COD_PERSONA_NATURAL)){%>
                                          <tr>
                                                <td width="15px"></td>
                                                <td class="datos_cajetin">Persona Natutal:</td>
                                                <td><%=(documento.getCliente().getNombres()==null?"":documento.getCliente().getNombres()) + " " + (documento.getCliente().getApellidoPaterno()==null?"":documento.getCliente().getApellidoPaterno()) + " " +  (documento.getCliente().getApellidoMaterno()==null?"":documento.getCliente().getApellidoMaterno())%></td>	
                                           </tr>
                                           <%if (documento.getCargoRemitente()!=null){%>
                                                <tr>
                                                    <td width="15px"></td>
                                                    <td class="datos_cajetin">Cargo:</td>
                                                    <td><%=documento.getCargoRemitente().getDesCargo()==null?"":documento.getCargoRemitente().getDesCargo()%></td>	
                                               </tr>
                                           <%}%>
                                        <%}%>   
                                   <%}%>                                         
                                                       
                           <%}%>
                    <%}%>   
                    <tr><td colspan="3" style="height:18px"></td></tr>
		</table>
              </p>      
            </div>
            <div>        
                <p class="botones">
                    <input type="button" onClick="imprimirHR()" value="Imprimir" />
               </p>
            </div>    
	</body>
</html>