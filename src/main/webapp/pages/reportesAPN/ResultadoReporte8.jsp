<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.List" %>
<%@page import= "java.text.SimpleDateFormat" %>
<%@page import= "java.math.*"%>
<%@page import="java.util.ArrayList" %>
<%@page import="com.btg.ositran.siged.domain.NodoExpReporteAPN8" %>
<%@page import="com.btg.ositran.siged.domain.NodoDocReporteAPN8" %>
<%@page import="org.ositran.utils.DateUtil" %>
<%@page import="org.ositran.utils.Constantes" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<style type="text/css">
			@import "css/estilo-reportes.css";
			td.filaR{
		    	padding-right:0.3em;
		    	padding-left:0.3em;
		        padding-top: 0.3em;
		        padding-bottom: 0.3em;
		        border-color: #00000;
		        border: solid 0.02em;
		    }
		</style>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Documentos registrados en el mes</title>
		<%
			List<NodoExpReporteAPN8> listaReporteAPN8 = (List<NodoExpReporteAPN8>)request.getAttribute("listaReporteAPN8");
			String url  = (String)request.getAttribute("url");
			String path = request.getContextPath() + url;
			String filtroPlazo = (String)request.getAttribute("filtroPlazo");
			Integer size = (Integer)request.getAttribute("size");
			String TituloRepAPN3 = "Reporte de documentos";
		%>
		<script type="text/javascript">

		    function correo() {
              var opciones = "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes, width=750, height=430, top=20, left=70";
              alert("<%=url%>");
              alert("<%=path%>");
              var pagina = "goNotificarCorreoReporte8.action?urlReporte=" + "<%=url%>";
              window.open(pagina, "", opciones);
            }

			function imprimir(){
				var ventana = window.open("", "", "");
		        var contenido = "<html><head>"+
		            "<link rel=\"stylesheet\" type=\"text/css\" href=\"css/reporteImprimir.css\" />" +
		            "</head>    " +
		            "<body onload='window.print();' >" +
		            "<h1 class=\"TxtTitulo\"><%=TituloRepAPN3%></h1>" +
		            document.getElementById("filasImp").innerHTML + "</body></html>";
		        ventana.document.open();
		        ventana.document.write(contenido);
		        ventana.document.close();
			}
			function exportar(){
				//Tablas es un div que contiene todo el bucle de tablas del reporte.
				var tablas=document.getElementById("tablas");
				//alert(tablas.innerHTML);
				document.getElementById("html").value=tablas.innerHTML;
				//El form contiene todo el html de la pagina. Al hacer submit llama al evento ExportarExcel del Action y le pasa el parámetro "html"
				document.getElementById("form1").submit();
			}
		</script>
	</head>
		<body>
			<%if(listaReporteAPN8 != null && !listaReporteAPN8.isEmpty()){ %>
			<form id="form1" name="form1" action="ReporteSiged_exportarExcel.action" method="post">
			<div id="tablas">
				<h1 class="TxtTitulo" style="font-size:20px"><%=TituloRepAPN3 %></h1>
				<input id="html" jsId="html" name="html" type="hidden"/>
				<p style="text-align: right;">
				    <input type="button" onclick="correo()" value="Correo"/>
					<input type="button" onclick="imprimir()" value="Imprimir"/>
					<input type="button" onclick="location.href='<%=path%>'" value="Exportar a Excel"/>
				</p>
				<div id="filasImp">
				<table class="tableForm" align="center" border="1">
					<tr class="cabecera">
						<th style="width:50px; border: solid #000000 0.02em;" rowspan="2">Item</th>
						<th style="width:100px; border: solid #000000 0.02em;" rowspan="2">Nro Expediente</th>
						<th style="width:135px; border: solid #000000 0.02em;" rowspan="2">Fecha de Creaci&oacute;n del Documento</th>
						<th style="width:100px; border: solid #000000 0.02em;" rowspan="2">Documento</th>
						<th style="width:100px; border: solid #000000 0.02em;" rowspan="2">&Aacute;rea</th>
						<th style="width:150px; border: solid #000000 0.02em;" rowspan="2">Prioridad</th>
						<th style="width:150px; border: solid #000000 0.02em;" rowspan="2">Administrado</th>
						<th style="width:200px; border: solid #000000 0.02em;" rowspan="2">Asunto</th>
						<th style="width:100px; border: solid #000000 0.02em;" rowspan="2">Tiempo de <br/>Atenci&oacute;n</th>
						<%for(int i = 1 ; i<=size; i++){ %>
							<th colspan="2" style="border: solid #000000 0.02em; border-right-width: 0.3em; border-left-width: 0.3em;">Último Documento</th>
						<%} %>
						<th style="width:100px; border: solid #000000 0.02em; " rowspan="2">Tiempo Total</th>
						<th style="width:100px; border: solid #000000 0.02em; border-right-width: 0.3em;" rowspan="2">&nbsp;</th>
						<th style="width:100px; border: solid #000000 0.02em; " rowspan="2">Ultimo Estado</th>
						<th style="width:100px; border: solid #000000 0.02em; border-right-width: 0.3em;" rowspan="2">&nbsp;</th>
					</tr>
					<tr class="cabecera">
						<%for(int i = 0 ; i<size; i++){ %>
							<th style="width:100px; border: solid #000000 0.02em; border-left-width: 0.3em;" >Documento</th>
							<th style="width:120px; border: solid #000000 0.02em;">&Aacute;rea</th>
						<!--  	<th style="width:120px; border: solid #000000 0.02em;">F. Creaci&oacute;n</th>
							<th style="width:120px; border: solid #000000 0.02em;">F. Transferencia</th> -->
						<!--  	<th style="width:100px; border: solid #000000 0.02em; border-right-width: 0.3em; ">Tiempo en Trámite</th> -->
						<%} %>
					</tr>
					<%int i = 1; SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy hh:mm");
					for(NodoExpReporteAPN8 fila : listaReporteAPN8){
						long tiempoTramite = 0l;
						long tiempoIntervalo = 0l;
					%>
					<%int j=0;
						List<String> tiempoDias = new ArrayList<String>();
						for(NodoDocReporteAPN8 doc : fila.getDocumentos()){
							String diasTramite = "";
							//if(j == 0){
								if(doc.getFechaTransferencia() != null && doc.getFechaCreacion() != null){
									long tiempo = doc.getDias();//doc.getFechaTransferencia().getTime() - doc.getFechaCreacion().getTime();
									tiempoTramite += tiempo;
									diasTramite = DateUtil.milisegundosADias(tiempo);
								}else{
									diasTramite = "-";
								}

							tiempoDias.add(diasTramite);
							j++;
						}%>
					<%
					     boolean fueraPlazo = false;
					     tiempoIntervalo = fila.getDias();//fila.getFechalimite().getTime() - fila.getFechaCreacionExterna().getTime();
						 if (tiempoTramite > tiempoIntervalo)  //tiempoTramite < fila.getPlazoProceso()*Constantes.MILISEGUNDOS_DIA;
					       fueraPlazo = true;
					%>
					<%if(tiempoIntervalo>0 && (filtroPlazo.equals("T") || (filtroPlazo.equals("D") && !fueraPlazo) || (filtroPlazo.equals("F") && fueraPlazo))){ %>
					<tr class="fila">
						<td class="filaR"><%=i %></td>
						<td class="filaR"><%=fila.getNumeroExpediente()+"&nbsp;" %></td>
						<td class="filaR"><%=f.format(fila.getFechaCreacion()) %></td>
						<td class="filaR"><%=fila.getNroDocumento() %></td>
						<td class="filaR"><%=fila.getDesArea() %></td>
						<td class="filaR"><%=fila.getPrioridad() %></td>
						<td class="filaR"><%=fila.getAdministrado() %></td>
						<td class="filaR"><%=fila.getAsunto() %></td>
						<td class="filaR"><%=DateUtil.milisegundosADias(fila.getDias())%></td>
						<%j=0;
						for(NodoDocReporteAPN8 doc : fila.getDocumentos()){ %>
							<td class="filaR" style="border-left-width: 0.3em;"><%=doc.getDocumento() %></td>
							<td class="filaR"><%=doc.getAreaDestino() %></td>
							<!-- <td class="filaR"></td>
							<td class="filaR"></td>  -->
						<!--  	<td class="filaR" style="border-right-width: 0.3em; "></td> -->
						<%j++;
						}
						while(j<size){%>
							<td class="filaR" style="border-left-width: 0.3em;">&nbsp;</td>
							  <td class="filaR">&nbsp;</td>
							<td class="filaR">&nbsp;</td>
							<td class="filaR">&nbsp;</td>
							<td class="filaR" style="border-right-width: 0.3em; ">&nbsp;</td>
						<%j++;
						}%>
						<td class="filaR" ><%=DateUtil.milisegundosADias(tiempoTramite) + "&nbsp;"%></td>
						<td class="filaR" style="border-right-width: 0.3em; ">
						<%if(fueraPlazo){%>
							<span style="color:red; font-weight:bold;">Fuera del plazo</span>
						<%}else{ %>
							Dentro del plazo
						<%} %>
						</td>

						<td class="filaR"><%= "&nbsp;" + fila.getEstado()  + "&nbsp;"%></td>
                        <td class="filaR" style="border-right-width: 0.3em; ">
					</tr>
						<%i++;
						}
					}%>
				</table>

				</div>
			</div>
			<%if(i == 1){ %>
					<script type="text/javascript">
						document.getElementById("tablas").style.display="none";
					</script>
					<table width="100%" border="0">
			         	<tr>
			            	<td height="100" align="center" class="label">NO HAY COINCIDENCIAS</td>
			         	</tr>
			        </table>
				<%} %>
			</form>
			<%}else{ %>
				<table width="100%" border="0">
		         	<tr>
		            	<td height="100" align="center" class="label">NO HAY COINCIDENCIAS</td>
		         	</tr>
		        </table>
			<%} %>
		</body>
</html>