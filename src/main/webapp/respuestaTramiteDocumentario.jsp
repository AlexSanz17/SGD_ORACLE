<!-- REN detalle del documento que va debajo de la grilla, se carga cuando se presiona una fila de la grilla ------------------->

<?xml version="1.0" encoding="UTF-8"?>
<%@page import="java.util.List"%>
<%@page import="com.btg.ositran.siged.domain.TramiteDocumentario"%>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>






<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es">
    <head>
        <title>Detalle del Documento</title>
        <% List<TramiteDocumentario> td = request.getAttribute("tramitedocumentario")==null? null: (List<TramiteDocumentario>)request.getAttribute("tramitedocumentario"); %>
        <% String respuesta = (String)request.getAttribute("capcha"); %>
         <% String nro = request.getAttribute("nroConsultasTD")==null? "": "Número de Consultas: " + (String)request.getAttribute("nroConsultasTD"); %>

        <META HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE" >
 <script src="https://www.google.com/recaptcha/api.js"></script>
		 <script>

		 function cleanFields() {
			 document.forms["frmFiltrarTramite"]["nroTramitedocumentario"].value = "";

		 }

            function SearchExpTramiteDocumentarioSubmit() {

			   valorsito = document.forms["frmFiltrarTramite"]["nroTramitedocumentario"].value;
			   if (valorsito == ''){
           		 alert("Debe ingresar un N° de Trámite. Por favor Verificar");
           		 return
               }

               document.forms["frmFiltrarTramite"].submit();
            }

            function soloNumeros(e){
            	var key = window.Event ? e.which : e.keyCode
            	return (key >= 48 && key <= 57)
            }

         </script>

    </head>
    <body class="soria">
       <form id="frmFiltrarTramite" name="frmFiltrarTramite" action="doViewTramiteDocumentario" method="post">

				        <table style="width:100%;border:1px solid #830713;border-collapse:collapse">
				              <tr>
						            <td height="20px"  width="100%">
						               <table width="100%" border="0" height="60px" align="left">
						                  <tr>
						                     <td bgcolor="#830713" width="100%" align="center" style="color:white;font-size:25px;"><b>Consulta de Tramite Documentario</b></td>
						                  </tr>
						               </table>
						            </td>
				              </tr>

					        <tr>
					            <td>

					                     <table width="100%" border="0">
					                           <tr>
							                   	     <td class="filaR3" width="200px"><b></b></td>
													 <td class="filaR3" align="center"><b>N° del Trámite:</b>  <input type="text" name = "nroTramitedocumentario" id="nroTramitedocumentario" size="20" maxlength="6" onKeyPress="return soloNumeros(event)"/></td>
													  <td class="filaR3" width="200px" align="right"><b><%=nro%></b></td>
											    </tr>

									             <tr>
                                           				<td> </td>
                               					 </tr>

                               					 <tr>
												    <td width="200px"></td>

									                <td colspan="1" align="center">
									                        <img src="images/buscar.bmp" border="0" onClick="SearchExpTramiteDocumentarioSubmit()" alt="Buscar" style="cursor:pointer" />
									                        <img src="images/Limpiar.bmp" border="0" onClick="cleanFields()" alt="Limpiar" style="cursor:pointer" />
									                </td>
				 									 <td class="filaR3" width="200px" ></td>
									            </tr>
				                          </table>

						        </td>
	       				  </tr>
                       </table>
                        <bR>


			 						<table style="width:100%;border:1px solid #830713;border-collapse:collapse" border="0">
											  <%if (td!=null && td.size()>0) {%>

														 <tr>
															<td align="right" width="50%"><b>Nro Documento :</b></td>
															<td width="50%"><%=td.get(0).getNrodocumento()==null? "": td.get(0).getNrodocumento()%></td>
														 </tr>

														  <tr>
															<td align="right" width="50%"><b>Administrado :</b></td>
															<td width="50%"><%=td.get(0).getCliente() == null ? "": td.get(0).getCliente()%></td>
														 </tr>

														 <tr>
															<td align="right" width="50%"><b>Asunto:</b></td>
															<td width="50%"><%=td.get(0).getAsunto() == null? "":td.get(0).getAsunto() %></td>
														 </tr>

														  <tr>
															<td align="right" width="50%"><b>Fecha del Documento :</b></td>
															<td width="50%"><%=td.get(0).getFechacreacion()==null?"": td.get(0).getFechacreacion()%></td>
														 </tr>

														<%if (td.get(td.size()-1).getOrden().equals("1")){%>
															  <tr>
																<td align="right" width="50%"><b>Documento de Respuesta:</b></td>
																<td width="50%"><%=td.get(td.size()-1).getNrodocumento() ==null? "":td.get(td.size()-1).getNrodocumento()%></td>
															 </tr>
			                                            <%}%>




											  <%}else{%>

														<tr>
															<td align = "center" colspan="2"> <b>No Existe el N° de Trámite Ingresado. Por favor Verificar</b></td>
														 </tr>

											  <%}%>

			  					</table>

			  					<br>

			  						<table style="width:100%;border:1px solid #830713;border-collapse:collapse">
											  <%if (td!=null && td.size()>0) {%>
														 <tr bgcolor="#830713" style="color:white;font-size:18px;">
														    <td align="center"><b>Item</b></td>
														    <td align="center"><b>Origen</b></td>
															<td align="center"><b>Destino</b></td>
															<td align="center"><b>Fecha de <br>Recepción</b></td>
															<td align="center"><b>Estado</b></td>

														 </tr>
                                                    <%for(int i=0;i<td.size();i++){%>
														 <tr>
														   <td align="center"><%=i+1%></td>
														    <td align="center"><%= td.get(i)==null?"":td.get(i).getRemitente()%></td>
														   <td align="center"><%= td.get(i)==null?"":td.get(i).getDestinatario()%></td>
														   <td align="center"><%= td.get(i).getFechacreacion() ==null?"":td.get(i).getFechacreacion()%></td>
														   <td align="center"><%= td.get(i).getEstado()%></td>

														 </tr>
												   <%}%>
											  <%}%>

			  					</table>




      </form>
    </body>
</html>