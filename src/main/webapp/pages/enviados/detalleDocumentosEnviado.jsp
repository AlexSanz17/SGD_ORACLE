<?xml version="1.0" encoding="UTF-8" ?>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@page import="org.ositran.utils.Constantes" %>
<%@page import="org.ositran.utils.DocumentoDetail" %>
<%@page import="org.ositran.utils.DocumentoEnviadoTemporal" %>
<%@page import="com.btg.ositran.siged.domain.Documentoenviado"%>
<%@page import="com.btg.ositran.siged.domain.Trazabilidaddocumento"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es">
   <head>
      <title>Detalle de Documento Enviado</title>
      <META HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE" >
      <%--<script type="text/javascript" src="js/dojo/dojo/dojo.js" djConfig="parseOnLoad:true, isDebug: false"></script>--%>
     
      <script type="text/javascript">
         dojo.require("dojo.io.iframe");
         dojo.require("dijit.Toolbar");
         dojo.require("dijit.form.Button");

         
      </script>

      <script language="JavaScript">
         function abrirVentanaCopiarApoyo(){
              var valor = "";
              <s:if test="documento.documentoreferencia== null">
                   valor = "<s:property value='documento.idDocumento'/>";  
              </s:if>          
              <s:else>
                   valor = "<s:property value='documento.documentoreferencia'/>";        
              </s:else>    
           
              var opciones = "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes, width=810, height=516, top=20, left=70";
              var pagina = "goCopiarApoyo.action?arrIdDoc=" + valor + "&bandeja=E";
              window.open(pagina, "Ventana", opciones);     
         }  
          
          
         function verArchivo(idArchivo, url, objectId){
            var fecha = new Date();
            window.open("<%=request.getContextPath()%>/verDocumento.png?idArchivo=" +idArchivo + "&url=" + url + "&objectId="+ objectId  + "&accion=abrirDocumento&fecha=" + fecha.getTime());
         }     
          
         function Abrir_ventana (pagina) {
            var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=no, width=440, height=350, top=50, left=200";
            window.open(pagina,"",opciones);
         }
         
         function abrirVentanaSeguimiento() {
            var opciones = "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=no, width=850, height=320, top=50, left=200";
            window.open("goViewSeguimiento.action?iIdDocumento=<s:property value='documento.idDocumento'/>","",opciones);
         }
         
     

      
	dojo.addOnLoad(function(){
	    new dijit.Toolbar({
		name	: "toolbar"
	    },"toolbar");                    
	});
	</script>


      <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
      <script>
   
         function abrirHojaRuta(){
         	var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=900, height=700, top=50, left=200";
             var pagina = "ReporteAPN_verHojaRuta.action?idDocumento=" + "<s:property value='documento.idDocumento'/>";
             window.open(pagina,"HojaRuta",opciones);
         }
         
         function abrirHojaFirma(){
         	var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=900, height=650, top=50, left=200";
             var pagina = "ReporteAPN_verHojaFirma.action?idDocumento=" + "<s:property value='documento.idDocumento'/>";
             window.open(pagina,"HojaFirma",opciones);
         }

      </script>

      <%

    Documentoenviado cont = (Documentoenviado) request.getAttribute("documentoenviado");

      %>

   </head>

	<body class="barra">
    	<table width="100%" border="0">
        	<tr align="center">
            	<td width="98%" colspan="4" align="left">
            	<s:if test="#session._RECURSO.datos">
                	&nbsp;&nbsp;
                  	<img id="<s:property value="documento.iddocumento"/>" onClick="abrirVentanaDatos(this.id)" src="images/datos.bmp" border="0" alt="Datos"/>
            	</s:if>
            	<s:if test="#session._RECURSO.UsuFinBtnTraExp">
               		<div id="toolbar">
                          <s:if test="documentoEnviadoTemporal.tipoEnvio=='M' && documento.estado !=  'N'  ">
		                  <div dojoType="dijit.form.Button" onClick="abrirVentanaCopiarApoyo()" iconClass="dijitEditorIcon dijitEditorIconIndent">Derivar M&uacute;ltiple</div>
		          </s:if>
                          <s:if test="#session._RECURSO.HojaRuta">
		               	<div dojoType="dijit.form.Button"  onClick="abrirHojaRuta()" iconClass="siged16 iconoHojaRuta">Hoja de Ruta</div>
                                <div dojoType="dijit.form.Button"  onClick="abrirHojaFirma()" iconClass="siged16 iconoHojaRuta">Hoja de Firmas</div> 
		 	</s:if>
                 	</div>
               	</s:if>
            	</td>
         	</tr>

		<s:if test="#session._UPLOADLIST.upload1!= null">
			   <tr>
			     <td style="width:2%">&nbsp;</td>
                              <td colspan="1" style="width: 39%;font-size:12px;" valign="top"> 
                                  <s:textfield name="iIdDoc" cssStyle="display:none" />
                                  <s:textfield name="objDD.cEstado" cssStyle="display:none" />
                                  <s:textfield name="objDD.iIdCliente" cssStyle="display:none" />
                                  <br/><b>Asunto:</b> <s:property value="documentoEnviadoTemporal.asunto" />
                                  <s:if test="documentoEnviadoTemporal.prioridad != null && documentoEnviadoTemporal.prioridad!=''">
                                      <br /> <b> Prioridad:</b> <img src="images/Prioridad_<s:property value="documentoEnviadoTemporal.prioridad" />.png" />
                                  </s:if>    
                                  <s:if test="documentoEnviadoTemporal.fechaLimite != null && documentoEnviadoTemporal.fechaLimite!=''">
                                           <br /> <b> Fecha Limite:</b> <s:property value="documentoEnviadoTemporal.fechaLimite" />
                                  </s:if>
                                     
                              </td>    
                             <td valign="top">
                                 <table border="0">
                                     <tr>
                                         <td height="12" align="left" class="plomo" > <br/>Adjuntos:</td>   
                                     </tr>
                                     <tr>
                                        <td height="16" align="left" class="plomo">
                                          <div id="divShowFile"></div>
                                                <s:iterator value="#session._UPLOADLIST.upload1">
                                                        <s:if test="principal.equals('S')">
                                                            <a onclick="verArchivo('<s:property value='idArchivo' />','<s:property value='rutaAlfresco' />', '<s:property value='objectId' />');" alt="Ver Archivo"> <b><font color="<s:property value="@org.ositran.utils.Constantes@COLOR_DOCUMENTO_PRINCIPAL"/>"><s:property value="nombreReal"/> </font></b> [<s:property value="usuarioCarga.usuario"/>]<br />
                                                        </s:if>
                                                 </s:iterator>
                                                                
                                                 <s:iterator value="#session._UPLOADLIST.upload1">               
                                                    <s:if test="principal.equals('M')">
                                                        <a onclick="verArchivo('<s:property value='idArchivo' />','<s:property value='rutaAlfresco' />', '<s:property value='objectId' />');" alt="Ver Archivo"><b><font color="<s:property value="@org.ositran.utils.Constantes@COLOR_DOCUMENTO_MP_CARGO"/>"><s:property value="nombreReal"/> </font></b> [<s:property value="usuarioCarga.usuario"/>]<br />
                                                    </s:if>
                                                  </s:iterator>                
                                                                
                                                 <s:iterator value="#session._UPLOADLIST.upload1">               
                                                        <s:if test="principal.equals('N')">
                                                            <a onclick="verArchivo('<s:property value='idArchivo' />','<s:property value='rutaAlfresco' />', '<s:property value='objectId' />');" alt="Ver Archivo"><b><font color="<s:property value="@org.ositran.utils.Constantes@COLOR_DOCUMENTO_ANEXO"/>"><s:property value="nombreReal"/> </font></b> [<s:property value="usuarioCarga.usuario"/>]<br />
                                                        </s:if>          
                                                 </s:iterator>               
                                                                
                                                 <s:iterator value="#session._UPLOADLIST.upload1">               
                                                        <s:if test="principal.equals('C')">
                                                            <a onclick="verArchivo('<s:property value='idArchivo' />','<s:property value='rutaAlfresco' />', '<s:property value='objectId' />');" alt="Ver Archivo"><b><font color="<s:property value="@org.ositran.utils.Constantes@COLOR_DOCUMENTO_CARGO"/>"><s:property value="nombreReal"/> </font></b> [<s:property value="usuarioCarga.usuario"/>] <br />
                                                        </s:if>
                                                 </s:iterator>               
                                                 
                                                  <s:iterator value="#session._UPLOADLIST.upload1">               
                                                        <s:if test="principal.equals('Y')">
                                                            <a onclick="verArchivo('<s:property value='idArchivo' />','<s:property value='rutaAlfresco' />', '<s:property value='objectId' />');" alt="Ver Archivo"><s:property value="nombreReal"/> [<s:property value="usuarioCarga.usuario"/>]<br />
                                                        </s:if>
                                                 </s:iterator>
                                                   
                                                             
                                        </td> 
                                     </tr>  
                                 </table>
                             </td>
		             <td  align="right" valign="top">
                                    <table cellpadding="1" cellspacing="1" class="tableForm" border="1">
                                      <s:if test="documento.ID_CODIGO!=null && documento.ID_EXTERNO==1">
                                        <tr style="height:21px;">
                                            <td style="font-size: 11px;font-weight:normal;background-color:#669BCD;color:#ffffff;vertical-align:center;text-align:center;border: 1px #000 solid;">
                                                                                   Nro. Tr&aacute;mite
                                            </td>
                                            <td style="background-color:#ffffff;border: 1px #000 solid;width: 250px;">
                                              <b>  <font color="blue"> <s:property value="documento.ID_CODIGO" /> </font> </b>
                                            </td>
                                        </tr>

                                       </s:if>

                                       <s:if test="documento.ID_CODIGO!=null && documento.ID_EXTERNO==0">
                                        <tr style="height:21px;">
                                            <td style="font-size: 11px;font-weight:normal;background-color:#669BCD;color:#ffffff;vertical-align:center;text-align:center;border: 1px #000 solid;">
                                                                                    Nro. Tr&aacute;mite
                                            </td>
                                            <td style="background-color:#ffffff;border: 1px #000 solid;width: 250px;">
                                              <b>  <font color="blue"> <s:property value="documento.ID_CODIGO" /> </font> </b>
                                            </td>
                                        </tr>

                                       </s:if>       

                                      <s:if test="documento.confidencial.equals('S')">
                                        <tr style="height:21px;">
                                                <td width="100px" style="font-size: 11px;font-weight:bold;background-color:#DA1217;color:#ffffff;vertical-align:center;text-align:center;border: 1px #000 solid;" colspan="2">
                                                                                        Documento Confidencial
                                                </td>
                                       </tr>
                                      </s:if>
                                       <tr style="height:21px;">
                                                <td width="100px" style="font-size: 11px;font-weight:normal;background-color:#669BCD;color:#ffffff;vertical-align:center;text-align:center;border: 1px #000 solid;">
                                               Nro. Documento
                                               </td>
                                               <td style="background-color:#ffffff;border: 1px #000 solid;width: 250px;">
                                                         <s:property value="documento.tipoDocumento.nombre" /> - <s:property value="documento.numeroDocumento" />
                                               </td>
                                       </tr>             


                                       <tr style="height:21px;">
                                                <td style="font-size: 11px;font-weight:normal;background-color:#669BCD;color:#ffffff;vertical-align:center;text-align:center;border: 1px #000 solid;">
                                               Nro. Carpeta
                                               </td>
                                               <td style="background-color:#ffffff;border: 1px #000 solid;width: 250px;">
                                                        <s:property value="documento.expediente.nroexpediente" />
                                               </td>
                                       </tr>

                                       <s:if test="documento.ID_CLIENTE!=null">
                                        <tr style="height:21px;">
                                                 <td style="font-size: 11px;font-weight:normal;background-color:#669BCD;color:#ffffff;vertical-align:center;text-align:center;border: 1px #000 solid;">
                                                Cliente
                                               </td>
                                                <td style="background-color:#ffffff;border: 1px #000 solid;width: 250px;">
                                                        <s:property value="documento.cliente.nombreRazon" />
                                               </td>
                                        </tr>
                                       </s:if>
                                       <s:else>
                                           <tr style="height:21px;">
                                                <td style="font-size: 11px;font-weight:normal;background-color:#669BCD;color:#ffffff;vertical-align:center;text-align:center;border: 1px #000 solid;">
                                                Cliente
                                               </td>
                                                <td style="background-color:#ffffff;border: 1px #000 solid;width: 250px;">
                                                        <s:property value="" />
                                               </td>
                                           </tr>    
                                       </s:else>        

                                       </table>
				</td>
			</tr>
                              
                                               
                    
         </s:if>
        
	<tr>
            	<td height="50" colspan="4">
               		<table width="100%"  border="1" align="center" bordercolor="#669BCD" bgcolor="#ffffff">
                  		<tr>
                     		<td>
                         		<table width="100%" height="100" align="center" >
                           			<tr>
		                            	<td width="1%"></td>
		                              	<td width="1%" height="5"></td>
		                              	<td width="1%"></td>
		                              	<td width="4%"></td>
		                              	<td width="1%"></td>
		                              	<td width="91%"></td>
		                           	</tr>
                           			<tr>
                              			<td width="2%"></td>
                              			<td height="16" colspan="5" align="left" class="asunto">
                                 		<!--	<s:textfield name="iIdDoc" cssStyle="display:none" />
                                 			<s:textfield name="objDD.cEstado" cssStyle="display:none" />
                                 			<s:textfield name="objDD.iIdCliente" cssStyle="display:none" />
                                 			Asunto: <s:property value="documentoEnviadoTemporal.asunto" />
                                                        <s:if test="documentoEnviadoTemporal.prioridad != null && documentoEnviadoTemporal.prioridad!=''">
                                                             <br />  Prioridad: <img src="images/Prioridad_<s:property value="documentoEnviadoTemporal.prioridad" />.png" />
                                                             <s:if test="documentoEnviadoTemporal.fechaLimite != null && documentoEnviadoTemporal.fechaLimite!=''">
                                                               <br />  Fecha Limite: <s:property value="documentoEnviadoTemporal.fechaLimite" />
                                                             </s:if>
                                                        </s:if>       -->
                              			</td>
                           			</tr>
		                           	<tr>
		                              	
                                                </tr>
		                           	<tr>
		                              	<td></td>
		                              	<td height="16" colspan="4" align="left" class="plomo" >Para:</td>
		                              	<td>
		                              		<s:property value="documentoEnviadoTemporal.destinatarioNombres" />
											<s:property value="documentoEnviadoTemporal.destinatarioApellidos" />
										</td>
		                          	</tr>
                                                   
                                                <tr>
                                                    <td></td>
                                                    <td height="16" colspan="5" align="left" class="plomo" ><hr style="width:100%;color:#9199AC;background-color:#9199AC;border-color:#9199AC;"/></td>
                                                </tr>                                
                           			<tr>
		                              	<td></td>
		                              	<td height="16" colspan="4" align="left" class="plomo">Cc: </td>
		                              	<td><s:property value="documentoenviado.cadenaCC" /></td>
		                              	<td></td>
                           			</tr>
                           			<tr>
                              			<td height="5" colspan="6" ><hr></td>
                           			</tr>
                           			<tr>
                                                    <td></td>    
                              			<td colspan="5">	
                                                    <s:if test="documentoEnviadoTemporal.strAccion != null && documentoEnviadoTemporal.strAccion!=''">
                                                        <b> Acciones: </b><s:property value='documentoEnviadoTemporal.strAccion'/>
                                                    </s:if>   
                                                    
                                                    <s:if test="documentoEnviadoTemporal.contenido != null && documentoEnviadoTemporal.contenido != ''">  
                                                      <!--  <s:hidden id="documentoEnviadoTemporal.contenido" name="documentoEnviadoTemporal.contenido" />  -->
                                                        <br/>  <div id="prov">  <b> Proveido:</b>  <s:property value='documentoEnviadoTemporal.contenido' escape='false'/>  </div> 
                                                
                                                    </s:if>
                                 		
                              			</td>
									</tr>
                           			<tr>
                              			<td height="25" colspan="6" ></td>
                           			</tr>
                        		</table>
                     		</td>
                  		</tr>
               		</table>
            	</td>
         	</tr>
         	<tr>
            	<td height="14"  colspan="2"></td>
         	</tr>
      	</table>

      <!-- Inicio Modificacion Victor Soria punto 12-->
      <div id="tabla12" style="position:absolute; right:30px; top:35px">

               </div>

      <!-- Inicio Modificacion Victor Soria -->

   </body>
</html>
