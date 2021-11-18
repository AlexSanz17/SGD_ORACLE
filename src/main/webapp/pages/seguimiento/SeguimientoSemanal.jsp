<%@page import="org.ositran.utils.DateUtil"%>
<%@page import="java.util.GregorianCalendar"%>
<%@page import="java.util.List" %>
<%@page import="java.util.Date" %>
<%@page import="java.util.Calendar" %>
<%@page import="java.text.SimpleDateFormat" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@page import="com.btg.ositran.siged.domain.FilaSeguimiento" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//ES" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html>
   <head>
      <title>Seguimiento Semanal</title>
      <%
      	List<FilaSeguimiento> seguimiento = (List<FilaSeguimiento>)request.getAttribute("seguimiento");
      	Calendar fechaInicio = new GregorianCalendar();
      	fechaInicio.setTime((Date)request.getAttribute("fechaInicio"));
      	Calendar fechaFin = new GregorianCalendar();
      	fechaFin.setTime((Date)request.getAttribute("fechaFin"));
      	Integer index = 0;
      	Integer index1 = 0;
      	Integer index2 = 0;
      	Integer index3 = 0;
      	Integer index4 = 0;
      	Integer index5 = 0;
      %>
      <script type="text/javascript">
      dojo.require("dijit.form.CheckBox");
         function filtrarSeguimiento() {
        	 resetTabs(0);

             document.forms[0].action = "Seguimiento_mostrarSemanal.action";

             mostrarSemana();

         }

         function  abrirPagina(iddoc) {

		   dijit.byId("titleSeguimiento").attr("open", false);
		   dijit.byId("contentGeneralSeguimiento").attr("content", "");
		   dijit.byId("seguimientoDocumento").attr("content", "");

           dijit.byId("contentGeneralSeguimiento").setHref("doViewDocUsuarioFinal.action?iIdDoc=" + iddoc);

            /*if(dijit.byId("dlgBusquedaExpediente")){
              dijit.byId("dlgBusquedaExpediente").destroy();
            }*/
            if(dijit.byId("sNroExpedienteBE")){
               dijit.byId("sNroExpedienteBE").destroy();
            }
            if(dijit.byId("sAsuntoBE")){
               dijit.byId("sAsuntoBE").destroy();
            }
            if(dijit.byId("procesoBE")){
               dijit.byId("procesoBE").destroy();
            }
            if(dijit.byId("sNroIdentificacionBE")){
               dijit.byId("sNroIdentificacionBE").destroy();
            }
            if(dijit.byId("sRazonSocialBE")){
               dijit.byId("sRazonSocialBE").destroy();
            }
            if(dijit.byId("fechaBE")){
               dijit.byId("fechaBE").destroy();
            }
            if(dijit.byId("grdBusquedaExpediente")){
               dijit.byId("grdBusquedaExpediente").destroy();
            }

      	   dijit.byId("seguimientoDocumento").setHref("doViewDocAdicionales.action?iIdDoc="+iddoc+"&idGrid=30");

         }

         function  regresar (){
            parent.location.href = document.forms[0].action = "inicio.action?sTipoFrame=grid" ;
         }

         function cambiarSemana ( cambio ){
            resetTabs(0);

            var cad = dijit.byId("fechaInicio").attr("value");
            cad.setDate(cad.getDate()+cambio);
            dijit.byId("fechaInicio").attr("value", cad);

            cad = dijit.byId("fechaFin").attr("value");
            cad.setDate(cad.getDate()+cambio);
            dijit.byId("fechaFin").attr("value", cad);
            document.forms[0].action = "Seguimiento_mostrarSemanal.action";

            mostrarSemana();
         }

         function abrirHojaRutasScreen(idDoc){
         	var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=900, height=700, top=50, left=200";
             var pagina = "ReporteAPN_verHojaRuta.action?idDocumento=" + idDoc;
             window.open(pagina,"",opciones);
         }


         function  mostrarSemana () {

        	 dojo.xhrPost({
                 form: dojo.byId("formSeguimiento"),
                 content:{
              	   fechaInicioStr : dijit.byId("fechaInicio").attr("displayedValue"),
              	   fechaFinStr : dijit.byId("fechaFin").attr("displayedValue"),
              	   propios : dijit.byId("radRec").attr("checked")
                 },
                 mimetype: "text/html",
                 load: function(data) {
	             	dijit.byId("titleSeguimiento").attr("open", true);
	 	 		    dijit.byId("contentGeneralSeguimiento").attr("content", "");
	 	 		    dijit.byId("seguimientoDocumento").attr("content", "");
	             	dijit.byId("titleSeguimiento").setContent(data);
                 },
                 error:function (error ){
  				 	console.debug("error : "+error);
                 }
              });
         }
         function  mostrarDiario () {
        	 resetTabs(0);
            document.forms[0].action = "Seguimiento_mostrarDiario.action"
            	  dojo.xhrPost({
                      form: dojo.byId("formSeguimiento"),
                      mimetype: "text/html",
                      load: function(data) {
            		  dijit.byId("titleSeguimiento").attr("open", true);
      	 		    dijit.byId("contentGeneralSeguimiento").attr("content", "");
      	 		    dijit.byId("seguimientoDocumento").attr("content", "");
                  	dijit.byId("titleSeguimiento").setContent(data);
                     // 	dijit.byId("tabSeguimiento").setContent(data);
                      }
                   });
            // document.forms[0].submit();
         }

         function mostrar(id){
            var i = 0;
            var elem ;

            do{

               elem = document.getElementById(id+""+i)   ;
               if(elem!=null){

                  // alert('mostrando '+id+""+i) ;
                  elem.style.display='block';
               }
               i++ ;

               //if(i==3)elem = null ;

            }while(elem!=null) ;
            //alert('mostrando se queda en '+id+""+i) ;
         }

		function cambiarfechaInicio () {
			dijit.byId('fechaFin').constraints.min = dijit.byId('fechaInicio').getValue();
  	 	}

		function cambiarfechaFin () {
			dijit.byId('fechaInicio').constraints.max = dijit.byId('fechaFin').getValue();
  	 	}

 		dojo.addOnLoad(function(){
 				new dijit.form.DateTextBox({
 					//name			: "fechaInicio",
 					constraints		: {datePattern:"dd/MM/yyyy"},
 					invalidMessage	: "Ingrese una fecha con el formato dd/mm/yyyy",
 					onChange  : cambiarfechaInicio,
 					required		: true
 				},"fechaInicio");
 				new dijit.form.DateTextBox({
 					//name			: "fechaFin",
 					constraints		: {datePattern:"dd/MM/yyyy"},
 					invalidMessage	: "Ingrese una fecha con el formato dd/mm/yyyy",
 					onChange  : cambiarfechaFin,
 					required		: true
 				},"fechaFin");
 				var fecha = new Date(parseInt(<s:date name='fechaInicio' format='yyyy' />),parseInt(<s:date name='fechaInicio' format='MM' />)-1,parseInt(<s:date name='fechaInicio' format='dd' />));
 				try{
 					dijit.byId("fechaInicio").attr("value",fecha);
 				}catch(err){
 					console.debug("error: "+err);
 				}
 				fecha = new Date(parseInt(<s:date name='fechaFin' format='yyyy' />),parseInt(<s:date name='fechaFin' format='MM' />)-1,parseInt(<s:date name='fechaFin' format='dd' />));
 				try{
 					dijit.byId("fechaFin").attr("value",fecha);
 				}catch(err){
 					console.debug("error: "+err);
 				}
 			});
      </script>
      <style type="text/css">
         <!--
         body {
            background-color: #ffffff;
         }

         .barra {
            scrollbar-3dlight-color:#CCCCCC;
            scrollbar-arrow-color:#568BBF;
            scrollbar-base-color:#C3D5E9;
            scrollbar-darkshadow-color:#666666;
            scrollbar-face-color:;
            scrollbar-highlight-color:#669BCD;
            scrollbar-shadow-color:#999999;
         }
         -->
      </style>
   </head>

   <body  class="soria" topmargin="0" leftmargin="0" rigthmargin="0">
      <s:form id="formSeguimiento" name="formSeguimiento" action="" method="POST">
         <input type="hidden" id="fechaActualString" name="fechaActualString"  value="<s:date name="fechaActual" format="yyyyMMdd"/>"  />
	     <input type="hidden" id="variacion" name="variacion"  value="0"  />

	  <p>
         <table width="100%" border="0" >
            <tr>
               <td height="20"colspan="3" class="titulo">
                  <s:hidden name="bResponsable" />
					<table width="99%" align="center" style="padding-left: 0.3em;">
						<tr style="padding-bottom: 0.25em;">
							<td style="padding-bottom: 0.25em;"><strong>Documentos Visualizados</strong>
							</td>
							<td style="padding-bottom: 0.25em;" colspan="2">
								<label>Recibidos</label><input dojoType="dijit.form.RadioButton" name="rad1" id="radRec" <s:if test="propios">checked</s:if>/>&nbsp;&nbsp;&nbsp;

								<label>Enviados</label><input dojoType="dijit.form.RadioButton" name="rad1" id="radEnv" <s:if test="!propios">checked</s:if>/>
							</td>
							<td style="padding-bottom: 0.25em; text-align:right;">
								<div dojoType="dijit.form.Button" label="Buscar" onClick="filtrarSeguimiento()" style="font-size:0.9em;" iconClass="siged16 sigedSearch16"></div>
							</td>
						</tr>
						<tr>
							<td style="padding-bottom: 0.25em;"><strong>Periodo de seguimiento</strong></td>
							<td style="padding-bottom: 0.25em; vertical-align: middle;"><em>Fecha Inicio: </em>
								<input id="fechaInicio" type="text" />
								<input dojoType="dijit.form.Button" label="&lt;&lt;" onClick="cambiarSemana(-7)" style="font-size: 0.8em;"/>
							</td>
							<td style="padding-bottom: 0.25em; text-align: right;"><em>Fecha Fin: </em>
								<input id="fechaFin" type="text" />
								<input dojoType="dijit.form.Button" label="&gt;&gt;" onClick="cambiarSemana(7)" style="font-size: 0.8em;"/>
							</td>
						</tr>

						<tr>
							<td style="padding-bottom: 0.25em;"><strong>Estado</strong></td>
							<td colspan="2" style="padding-bottom: 0.25em; vertical-align: middle;">
								<select id="estado" name="estado" dojoType="dijit.form.FilteringSelect">
                                 	<option value="1">Pendiente</option>
                                </select>
							</td>
						</tr>
					</table>
               </td>
            </tr>
            <tr>
				<td>&nbsp;</td>
			</tr>
            <tr>
            	<td height="20" class="titulo">
                	<table width="99%" border="1" height="30">
                	    <tr>
                            <td width="20%" align="center" class="titulo" bgcolor="#A2C0DC"> Fecha Limite
                            </td>
							<td width="20%" align="center" class="titulo" bgcolor="#A2C0DC"> Documento
							</td>
							<td width="35%" align="center" class="titulo" bgcolor="#A2C0DC"> Asunto
							</td>
							<td width="9%" align="center" class="titulo" bgcolor="#A2C0DC"> Responsable
							</td>
							<td width="9%" align="center" class="titulo" bgcolor="#A2C0DC"> Fecha
							</td>
							<td width="7%" align="center" class="titulo" bgcolor="#A2C0DC"> Hoja de Ruta
							</td>

                	    </tr>
                	    <%boolean bandera = false;
                	      Date fecha = new Date();
                	      SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy hh:mm");

                	    %>
						<%while(!fechaInicio.after(fechaFin)){
						     bandera = false;
						%>
							<%while(index3 < seguimiento.size()){
										FilaSeguimiento fila = seguimiento.get(index3);
										if(DateUtil.compararDiasFecha(fila.getFechaLimite(), fechaInicio.getTime())){
											index3++;
											bandera = true;
										}else{
											break;
										}
					        }%>
					        <%if (bandera){%>
					                    <%int contador = 0;
					                      while(index4 < seguimiento.size()){
											FilaSeguimiento fila = seguimiento.get(index4);
											if(DateUtil.compararDiasFecha(fila.getFechaLimite(), fechaInicio.getTime())){
												index4++;
												contador++;
										    }else{
										    	break;
										    }
					                      }%>

										<%boolean bandera1 = true;
										  while(index2 < seguimiento.size()){
											FilaSeguimiento fila = seguimiento.get(index2);
											if(DateUtil.compararDiasFecha(fila.getFechaLimite(), fechaInicio.getTime())){
												index2++;
										%>
										 <tr>

										   <% if (bandera1){%>
											  <td width="20%" rowspan="<%=contador%>" class="titulo" bgcolor="#A2C0DC">
													<%=DateUtil.getFormatoFecha(fechaInicio.getTime()) %>
											  </td>
										   <% bandera1 = false;
										     }
										   %>
										  <td width="20%" class="normal">
											<strong>-</strong><a href="#" class="normal" onclick="abrirPagina('<%=fila.getIddocumento() %>')" >
											<%=fila.getNumerodocumento()%>
											</a>
										  </td>
										  <td width="35%" class="normal">
										     <strong>-</strong>
											  <%=fila.getAsunto()%>
										  </td>
										  <td width="9%" class="normal">
										      <strong>-</strong>
											  <%=fila.getResponsable() %>
										  </td>
										  <td width="9%" class="normal">
										     <strong>-</strong>
											 <%=sdf.format(fila.getFechaCreacion()) %>
										  </td>
										  <td width="7%">
										     <strong>-</strong>
											   <a href="#" onclick="abrirHojaRutasScreen('<%=fila.getIddocumento() %>')" class="normal" >
												HR
											 </a>
										  </td>
										</tr>
										<%
											}else{
												break;
											}
										  }%>


							<%}%>
						<%fechaInicio.add(Calendar.DATE, 1);
						} %>
                  </table>
               </td>
			</tr>
         </table>
	</p>
  </s:form>
   </body>

</html>