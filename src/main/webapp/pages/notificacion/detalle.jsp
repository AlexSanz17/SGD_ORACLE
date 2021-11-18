<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
   <head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
      <title>Sistema de Gesti&oacute;n Documentaria - Detalle de la Notificaci&oacute;n</title>
      <script type="text/javascript">
         function verArchivo(idArchivo, url, objectId){
              var fecha = new Date();
              window.open("<%=request.getContextPath()%>/verDocumento.png?idArchivo=" +idArchivo + "&url=" + url + "&objectId="+ objectId  + "&accion=abrirDocumento&fecha=" + fecha.getTime());
         }   
          
         function abrirVentanaSeguimiento() {
            var opciones = "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=no, width=780, height=320, top=50, left=200";

            window.open("goViewSeguimiento.action?iIdDocumento=" + <s:property value="objNotificacion.iddocumento.idDocumento" />, "", opciones);
         }

         function subirConMetadata() {
             if(dijit.byId("tabNuevoDocumento")){
             	dijit.byId("tabContainerInbox").removeChild(dijit.byId("tabNuevoDocumento"));
             	dijit.byId("tabNuevoDocumento").destroyDescendants();
             	dijit.byId("tabNuevoDocumento").destroy();
             }
				var dialog = dijit.byId("dlgProgresBar");
             dialog.show() ;
             var newTab = new dojox.layout.ContentPane({
             	id : "tabNuevoDocumento",
             	jsId : "tabNuevoDocumento",
             	closable: true,
             	href : "NuevoDocumento_mostrarVista.action?idExpediente="+<s:property value='objNotificacion.iddocumento.expediente.id' /> + "&idINFDocumento=" + <s:property value='objNotificacion.iddocumento.idDocumento'/> +"&tipoTransaccion=A",
             	title: "Nuevo Documento",
             	onClose: function(){
                        accion(2);
             		return true;
             	}
            	});
             dijit.byId("tabContainerInbox").addChild(newTab);
             dijit.byId("tabContainerInbox").selectChild(newTab);
             dialog.hide();
         }

         function abrirVentanaNotificar() {
            var opciones = "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes, width=769, height=528, top=20, left=70";
            var pagina = "goNotificarUSER.action?iIdNotificacion=" + <s:property value='objNotificacion.idnotificacion' /> + "&arrIdDoc=" + <s:property value='objNotificacion.iddocumento.idDocumento' />;

            window.open(pagina, "Ventana", opciones);
         }

         function versionar() {
            var opciones = "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes, width=730, height=350, top=50, left=200";
            var pagina = "SubirConMetadata_verVersionar.action?idDocumento=" + <s:property value='objNotificacion.iddocumento.idDocumento' />;

            window.open(pagina,"",opciones);
         }

         function abrirVentanaEnumerar() {
            var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes, width=500, height=290, top=50, left=200";
            var pagina = "Numeracion_verVentanaNumeracion.action?idDocumento=" + "<s:property value='objNotificacion.iddocumento.idDocumento' />";
            window.open(pagina,"",opciones);
         }
         
         function abrirHojaFirma(){
            	var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=900, height=500, top=50, left=200";
                var pagina = "ReporteAPN_verHojaFirma.action?idDocumento=" + "<s:property value='iIdDoc' />";
                window.open(pagina,"HojaFirma",opciones);
          }

         function abrirHojaRuta(){
         	var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=900, height=700, top=50, left=200";
             var pagina = "ReporteAPN_verHojaRuta.action?idDocumento=" + "<s:property value='iIdDoc' />";
             window.open(pagina,"HojaRuta",opciones);
         }

         function guardarSeguimiento(agregar){
         	var mensaje = "";
         	if(agregar){
         		mensaje="agregar";
         	}else{
         		mensaje="retirar";
         	}
         	if(confirm("¿Desea "+mensaje+" el Documento "+"<s:property value='objDD.strNroDocumento' />"+"a su lista de seguimiento?")){
         		dojo.xhrPost({
         			url : "guardarSeguimientoUsuario.action",
         			content : {
         				iIdDoc : "<s:property value='iIdDoc' />",
         				agregar: agregar
         			},
         			load : function(){
         				if(agregar){
             				mensaje="agregado a";
         				}else{
             				mensaje="retirado de";
         				}
         				alert("El documento "+"<s:property value='objDD.strNroDocumento' />"+" ha sido "+mensaje+" su lista de seguimiento");
         				//showGridInbox(sTipoGridActual);

         			}
         		});
         	}
         }

         //iIdNotificacion

         function imprimirProveido(){
         	var url = "imprimirProveido.action?iIdDoc=" + "<s:property value='iIdDoc' />"+"&sOpcion=informativos&iIdNotificacion=" + "<s:property value='objNotificacion.idnotificacion' />";
         	var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=900, height=700, top=50, left=200";
             window.open(url,"",opciones);
         }
      </script>
   </head>
   <body>
      <table style="width:100%;">
         <tr>
             <td colspan="6" align="left">
               <div dojoType="dijit.Toolbar">
                  <div dojoType="dijit.form.Button" onClick="abrirVentanaNotificar()" iconClass="siged16 iconoNotificar">Notificar</div>  
                  <s:if test="#session._RECURSO.HojaRuta">
                                <div dojoType="dijit.form.Button"  onClick="abrirHojaRuta()" iconClass="siged16 iconoHojaRuta">Hoja de Ruta</div>
                                 <div dojoType="dijit.form.Button"  onClick="abrirHojaFirma()" iconClass="siged16 iconoHojaRuta">Hoja de Firmas</div>
                            </s:if>
                  <s:if test="objNotificacion.tiponotificacion == 666">
                     <div dojoType="dijit.form.Button" onClick="subirConMetadata()" iconClass="siged16 sigedNuevoDoc16">Crear Documento</div>
                     <%-- <div dojoType="dijit.form.Button" onClick="versionar()" iconClass="iconoVersionar">Versionar</div>
                     <div dojoType="dijit.form.Button"  onClick="abrirVentanaEnumerar()" iconClass="iconoNumerar">Enumerar</div>--%>
                  </s:if>
                  <s:if test="objNotificacion.tiponotificacion == 667 || objNotificacion.tiponotificacion == 668">
	                 <div dojoType="dijit.form.Button" onClick="subirConMetadata()" iconClass="siged16 sigedNuevoDoc16">Crear Documento</div>
                     <%-- <div dojoType="dijit.form.Button" onClick="versionar()" iconClass="iconoVersionar">Versionar</div>--%>
                  </s:if>
                  <s:if test="agregar">
                        <div dojoType="dijit.form.Button"  onClick="guardarSeguimiento(false)" iconClass="dijitEditorIcon dijitEditorIconToggleDir">Retirar del Seguimiento</div>
                  </s:if>
                  <s:else>
                  		<div dojoType="dijit.form.Button"  onClick="guardarSeguimiento(true)" iconClass="dijitEditorIcon dijitEditorIconToggleDir">Marcar para Seguimiento</div>
				  </s:else>
				
               </div>
            </td>
         </tr>
         
         
          <tr>
                <td style="width: 2%;"></td>
                <td colspan="2" style="width: 35%;font-size:12px;" > 
                    
                    <b>Asunto: </b><s:property value="objNotificacion.iddocumento.asunto" />
                    <s:if test="objNotificacion.iddocumento.prioridad != null && objNotificacion.iddocumento.prioridad!=''">
                        <br />  <b>Prioridad:</b> <img src="images/Prioridad_<s:property value="objNotificacion.iddocumento.prioridad" />.png" />
                         <s:if test="objNotificacion.iddocumento.fechaLimiteAtencion != null && objNotificacion.iddocumento.fechaLimiteAtencion!=''">
                             <br /><b>Fecha Limite:</b> <s:property value="objNotificacion.iddocumento.fechaLimiteAtencion" />
                             <s:textfield name="strAcc" value="aprobar" cssStyle="display:none" />
                         </s:if>  
                    </s:if>
                </td>
                
                <td colspan="2" style="width: 35%;">
                    <s:if test="#session._UPLOADLIST.upload1 != null">
                        <table>
                            <tr>
                                <td></td>
                                <td height="16" colspan="5" align="left" class="plomo">Adjuntos:</td>
                            </tr>
                            <tr>
                                <td></td>

                                <td height="16" colspan="5" align="left" class="plomo">
                                    <s:iterator value="#session._UPLOADLIST.upload1">
                                        <%--<a href="<s:property value="sURL" />" target="_blank"><s:property value="nombreReal" /></a><br />--%>
                                         <s:if test="boVisor">
                                           <s:if test="principal.equals('S')">
	                                         <a onclick="mostrarPDFVisor('<s:property value='rutaAlfresco' />','<s:property value='#session.busquedaAvanzada'/>');" alt="Ver Archivo">  <s:if test="principal.equals('S')"><b> <font color="blue"><s:property value="nombreReal"/> </font></b></s:if><s:else> <s:property value="nombreReal"/> </s:else></a>
	                                         &nbsp;&nbsp;<a onclick="verArchivo('<s:property value='idArchivo' />','<s:property value='rutaAlfresco' />', '<s:property value='objectId' />');" alt="Ampliar"> [+] </a><br />
                                           </s:if>
                                         </s:if>
                                         <s:else>
                                           <s:if test="principal.equals('S')">
                            	         		<a onclick="verArchivo('<s:property value='idArchivo' />','<s:property value='rutaAlfresco' />', '<s:property value='objectId' />');" alt="Ver Archivo"><s:if test="principal.equals('S')"> <b><font color="blue"><s:property value="nombreReal"/> </font></b></s:if><s:else> <s:property value="nombreReal"/> </s:else></a><br />
                                           </s:if>
                                         </s:else>
                                    </s:iterator>

                                    <s:iterator value="#session._UPLOADLIST.upload1">
                                         <s:if test="boVisor">
                                             <s:if test="!principal.equals('S')">
		                                         <a onclick="mostrarPDFVisor('<s:property value='rutaAlfresco' />','<s:property value='#session.busquedaAvanzada'/>');" alt="Ver Archivo">  <s:if test="principal.equals('S')"><b> <font color="blue"><s:property value="nombreReal"/> </font></b></s:if><s:else> <s:property value="nombreReal"/> </s:else></a>
		                                         &nbsp;&nbsp;<a onclick="verArchivo('<s:property value='idArchivo' />','<s:property value='rutaAlfresco' />', '<s:property value='objectId' />');" alt="Ampliar"> [+] </a><br />
                                             </s:if>
                                         </s:if>
                                         <s:else>
                                             <s:if test="!principal.equals('S')">
                            	         		<a onclick="verArchivo('<s:property value='idArchivo' />','<s:property value='rutaAlfresco' />', '<s:property value='objectId' />');" alt="Ver Archivo"><s:if test="principal.equals('S')"> <b><font color="blue"><s:property value="nombreReal"/> </font></b></s:if><s:else> <s:property value="nombreReal"/> </s:else></a><br />
                                            </s:if>
                                         </s:else>
                                    </s:iterator>

                                </td>
                            </tr>
                        </table>
                    </s:if>
                </td>
                <td style="width: 27%;" align="right" valign="top">
                    <!--<div id="tabla12" style="width: 100%;text-align:left;"> -->
                        <table cellpadding="1" cellspacing="1" class="tableForm">
                            
                            <s:if test="objNotificacion.iddocumento.ID_CODIGO!=null && objNotificacion.iddocumento.ID_EXTERNO==1">
                                <tr style="height:20px;">
                                    <td style="font-size: 11px;font-weight:normal;background-color:#669BCD;color:#ffffff;vertical-align:center;text-align:center;border: 1px #000 solid;">
									   Nro. Tr&aacute;mite
                                    </td>
                                    <td style="background-color:#ffffff;border: 1px #000 solid;width: 250px;">
                                      <b>  <font color="blue"> <s:property value="objNotificacion.iddocumento.ID_CODIGO" /> </font> </b>
                                    </td>
                                </tr>
                            </s:if>
                                
                             <s:if test="objNotificacion.iddocumento.ID_CODIGO!=null && objNotificacion.iddocumento.ID_EXTERNO==0">
                                <tr style="height:20px;">
                                    <td style="font-size: 11px;font-weight:normal;background-color:#669BCD;color:#ffffff;vertical-align:center;text-align:center;border: 1px #000 solid;">
									    Nro. Tr&aacute;mite
                                    </td>
                                    <td style="background-color:#ffffff;border: 1px #000 solid;width: 250px;">
                                      <b>  <font color="blue"> <s:property value="objNotificacion.iddocumento.ID_CODIGO" /> </font> </b>
                                    </td>
                                </tr>
                            </s:if>     
                            
                            <s:if test="objNotificacion.iddocumento.confidencial.equals('S')">
                                <tr style="height:20px;">
                                    <td width="100px" style="font-size: 11px;font-weight:bold;background-color:#DA1217;color:#ffffff;vertical-align:center;text-align:center;border: 1px #000 solid;" colspan="2">
									Documento Confidencial
                                    </td>
                                </tr>
                            </s:if>
                            <tr style="height:20px;">
                                <td width="100px" style="font-size: 11px;font-weight:normal;background-color:#669BCD;color:#ffffff;vertical-align:center;text-align:center;border: 1px #000 solid;">
								Nro. Documento
                                </td>
                                <td style="background-color:#ffffff;border: 1px #000 solid;width: 250px;">
                                    <s:property value="objNotificacion.iddocumento.tipoDocumento.nombre" /> - <s:property value="objNotificacion.iddocumento.numeroDocumento" />
                                </td>
                            </tr>
                            <tr style="height:20px;">
                                <td style="font-size: 11px;font-weight:normal;background-color:#669BCD;color:#ffffff;vertical-align:center;text-align:center;border: 1px #000 solid;">
								Nro. Carpeta
                                </td>
                                <td style="background-color:#ffffff;border: 1px #000 solid;width: 250px;">
                                    <s:property value="objNotificacion.iddocumento.expediente.nroexpediente" />
                                </td>
                            </tr>
                            
                        </table>
                </td>
          </tr>
          
           <tr>
                <td height="50" colspan="6">
                    <table width="100%"  border="1" align="center" bordercolor="#669BCD" bgcolor="#ffffff">
                        <tr>
                            <td>
                                <table width="100%" height="100" align="center" >
                                    <tr>
                                        <td width="2%"></td>
                                        <td width="1%" height="5"></td>
                                        <td width="1%"></td>
                                        <td width="4%"></td>
                                        <td width="1%"></td>
                                        <td width="91%"></td>
                                    </tr>
                                    <tr>
                                        <td width="2%"></td>
                                        <td height="16" colspan="5" align="left" class="asunto">
                                          
                                        </td>
                                    </tr>
                                    
                                    <!--
                                    <s:if test="objDD.cEstado == 'P'">  
                                        <tr>
                                            <td></td>
                                            <td height="16" colspan="4" align="left" class="plomo" >Para:</td>
                                            <td><s:property value="objDD.strDestinatario" /></td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td height="16" colspan="5" align="left" class="plomo" ><hr style="width:100%;color:#9199AC;background-color:#9199AC;border-color:#9199AC;"/></td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td height="16" colspan="4" align="left" class="plomo">Cc: </td>
                                             <td><s:property value="objDD.cadenaCC" /></td>

                                        </tr> 
                                        <tr>
                                            <td height="5" colspan="6" ><hr></td>
                                        </tr> 
                                    </s:if>   
                                    -->
                                   <!--
                                    <tr>
                                        <td></td>
                                        <td colspan="5">
                                           <s:if test="objDD.strAccion != null && objDD.strAccion!=''">
                                               <b> Acciones: </b><s:property value='objDD.strAccion'/>
                                           </s:if>
                                           <s:if test="objDD.strContenido != null">    
                                                  <div id="contenido"><b> Proveido:</b> <s:property value='objDD.strContenido' escape='false'/></div>
                                           </s:if>    
                                           <script type="text/javascript">
                                                  //document.getElementById("contenido").innerHTML = document.getElementById("objDD.strContenido").value;
                                           </script> 
                                        </td>
                                    </tr> -->
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
                <td height="14" colspan="6"></td>
            </tr>
          
          
          
      </table>
   </body>
</html>
