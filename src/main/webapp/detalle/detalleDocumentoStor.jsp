<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@page import="org.ositran.utils.DocumentoDetail" %>
<%@page import="java.util.Map" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es">
    <head>
        <title>Detalle del Documento STOR</title>
        <style type="text/css">
            @import "js/dojo/dijit/themes/soria/soria.css";
            @import "css/estilo.css";
            @import "css/stor.css";            
        </style>
        
        <script type="text/javascript" src="js/siged/upload.js"></script>
        <script type="text/javascript">
            dojo.require("dijit.Toolbar");
            dojo.require("dijit.form.Button");
            dojo.require("dijit.Menu");
            dojo.require("dijit.ColorPalette");
            dojo.require("dojo.io.iframe");
            dojo.require("dijit.form.Textarea");
        </script>

        <!--Begin Scripts STOR -->
        <script type="text/javascript">

            function abrirVentanaCompletarActividad(){
                var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=no, width=650, height=320, top=50, left=200";
                window.open("/siged/doStor_ventanaCompletarActividad.action?idDocumento="+<s:property value="objDD.iIdDocumento"/>,"",opciones);
            }

            function abrirVentanaRechazarStor(){
                var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes, width=650, height=400, top=50, left=200";
                window.open("/siged/doStor_ventanaRechazarStor.action?idDocumento="+<s:property value="objDD.iIdDocumento"/>,"",opciones);

            }

            function abrirConfirmacionAprobarStor(){
                if (confirm("El Expediente Será Derivado, ¿Está Seguro?")){
                    location.href="/siged/doStor_confirmaAprobar.action?idDocumento="+<s:property value="objDD.iIdDocumento"/>;
                }
            }

            function abrirVentanaAnularExpediente(){
                var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes, width=650, height=250, top=50, left=200";
                window.open("/siged/doStor_ventanaAnularExpediente.action?idDocumento="+<s:property value="objDD.iIdDocumento"/>,"", opciones);
            }

            function abrirVentanaCambioSala(){
                var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes, width=650, height=250, top=50, left=200";
                window.open("/siged/doStor_ventanaCambioSala.action?idDocumento="+<s:property value="objDD.iIdDocumento"/>,"", opciones)

            }

            function abrirVentanaDatos(){
                var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=800, height=500, top=50, left=200";

                window.open("/siged/doStor_ventanaDatosExpediente.action?idDocumento="+<s:property value="objDD.iIdDocumento"/>+"&tipoVentanaDetalle=resumen","", opciones)
            }

            function abrirVentanaReformulacion(){
                var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=no, width=650, height=400, top=50, left=200";
                window.open("/siged/doStor_ventanaCompletarActividad.action?idDocumento="+<s:property value="objDD.iIdDocumento"/>,"",opciones);
            }

            //Agregado por Germán Enríquez
            function abrirVentanaRequerimientos() {
                var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=no, width=450, height=230, top=100, left=250";
                var pagina = "/siged/doStor_ventanaRequerimientos.action?idDocumento="+<s:property value="objDD.iIdDocumento"/>;
                window.open(pagina, "", opciones);
            }

            function abrirVentanaObservacion(){
                var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes, width=650, height=250, top=50, left=200";
                window.open("/siged/doStor_ventanaObservacion.action?idDocumento="+<s:property value="objDD.iIdDocumento"/>,"", opciones);
            }

            function abrirVentanaSeguimiento() {
                var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=no, width=800, height=320, top=50, left=150";
                window.open("/siged/goViewSeguimientoEx.action?iIdDocumento=" + <s:property value="objDD.iIdDocumento"/>,"",opciones);
            }

            function subirConMetadata() { 
                var opciones = "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes, width=730, height=320, top=50, left=200";
                var pagina = "SubirConMetadata_verSubirConMetadata.action?idDocumento=" + <s:property value='objDD.iIdDocumento' />;
                window.open(pagina,"",opciones);
            }
 
            function ajustarTextArea(){               
               var textAreaUO = document.getElementById('ultimaObservacion');
               var textAreaOO = document.getElementById('otraObservacion');
               if(textAreaUO != null){
                  //alert("UltimaObservacion: "+textAreaUO.value);
                  var saltosDeLinea = textAreaUO.value.split("\n").length;
                  //var saltosDelinea =                   
                  var filasUO = Math.floor(textAreaUO.value.length/107)+1;
                  //alert("Filas por Contenido TextArea: "+filasUO+" Por Saltos de Linea: "+saltosDeLinea);
                  textAreaUO.rows=filasUO+saltosDeLinea;
               }
               if(textAreaOO != null){
                  //alert("OtraObservacion: "+textAreaOO.value);
                  var saltosDeLinea = textAreaOO.value.split("\n").length;
                  var filasOO = Math.floor(textAreaOO.value.length/107)+1;
                  textAreaOO.rows=filasOO+saltosDeLinea;
               }               
            }

         function Abrir_ventanaId() {
               var opciones = "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes, width=600, height=550, top=50,left=200";
            var pagina = "doPlantilla_verNuevo.action?idDocumento=" + <s:property value='objDD.iIdDocumento' />;

            window.open(pagina,"",opciones);
         }

         function versionar() {
             var opciones = "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes, width=730, height=350, top=50, left=200";
             var pagina = "SubirConMetadata_verVersionar.action?idDocumento=" + <s:property value='objDD.iIdDocumento' />;

             window.open(pagina,"",opciones);
          }
         function abrirVentanaEnumerar() {
             var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes, width=500, height=290, top=50, left=200";
             var pagina = "Numeracion_verVentanaNumeracion.action?idDocumento=" + "<s:property value='objDD.iIdDocumento' />";
             window.open(pagina,"",opciones);
          }

         function abrirVentanaCambiarPlazo() {
             var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes, width=500, height=290, top=50, left=200";
             var pagina = "Seguimiento_verCambiarPlazo.action?idtrazabilidaddocumento=" + "<s:property value='idtrazabilidaddocumento' />";
             window.open(pagina,"",opciones);
          }

          function abrirVentanaArchivar() {
               var opciones = "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes, width=650, height=440, top=20, left=70";
            var pagina = "Archivar_inicio.action?idDocumento=" + <s:property value='objDD.iIdDocumento' />;

            window.open(pagina, "", opciones);
         }

         function loadVirtualDocumentSearch() {
               var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=800, height=450, top=20, left=70";
            var pagina = "goOpenDocumentSearch.action?iIdDoc=" + <s:property value='objDD.iIdDocumento' />;

            window.open(pagina, "", opciones);
         }
          
            dojo.addOnLoad(ajustarTextArea);
        </script>

        <!--End Scripts STOR -->

      <!-- Dojo - Fin --> 

        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />



    </head>

<body class="soria"   >
        <table width="100%">
            <tr>
                <!--<td width="2%" align="center">&nbsp;</td>-->
                <td></td>
                <td colspan="5" align="left" >
                    <div dojoType="dijit.Toolbar">
                        <s:if test="mapSessionStor.completeTaskStorAprobar">
                            <div dojoType="dijit.form.Button" iconClass="iconoAprobar" onclick="abrirVentanaCompletarActividad()">Aprobar</div>
                        </s:if>
                        <s:else>
                            <s:if test="mapSessionStor.confAprobarStor">
                               <div dojoType="dijit.form.Button" iconClass="iconoPlantilla" onclick="abrirVentanaObservacion()">Aprobar</div>
                            </s:if>
                        </s:else>
                        <s:if test="mapSessionStor.completeTaskStorRechazar">
                            <div dojoType="dijit.form.Button" iconClass="iconoRechazar" onclick="abrirVentanaRechazarStor()">Rechazar</div>
                        </s:if>
                        <s:if test="mapSessionStor.anularExpediente">
                            <div dojoType="dijit.form.Button" iconClass="iconoRechazar" onclick="abrirVentanaAnularExpediente()">Anular</div>
                        </s:if>
                        <s:if test="mapSessionStor.editarExpediente">
                            <div dojoType="dijit.form.Button" iconClass="iconoVersionar" onclick="abrirVentanaDatos()">Datos</div>
                        </s:if>
                        <s:if test="#session._RECURSO.UsuFinBtnTraExp">
                            <div dojoType="dijit.form.Button" iconClass="iconoTrazabilidad" onclick="abrirVentanaSeguimiento()">Trazabilidad</div>
                        </s:if>
                        <s:if test="mapSessionStor.cambioSala">
                            <div dojoType="dijit.form.Button" iconClass="iconoVersionar" onclick="abrirVentanaCambioSala()">Cambio Sala</div>
                        </s:if>
                        <s:if test="mapSessionStor.refTecrefLeg">
                            <div dojoType="dijit.form.Button" iconClass="iconoVersionar" onclick="abrirVentanaReformulacion()">Reformulacion</div>
                        </s:if>
                        <%-- Agregado por Germán Enríquez --%>
                        <s:if test="mapSessionStor.requerimientos">
                            <div dojoType="dijit.form.Button" iconClass="iconoVersionar" onclick="abrirVentanaRequerimientos();">Requerimientos</div>
                        </s:if>
                        <s:if test="#session._RECURSO.UsuFinBtnUpl">
                            <div dojoType="dijit.form.Button" onClick="subirConMetadata()" iconClass="iconoAdjuntar">Adjuntar</div>
                        </s:if>
                        <s:if test="#session._RECURSO.UsuFinBtnNueDocSec">
                           <div dojoType="dijit.form.Button" iconClass="iconoPlantilla" onClick="Abrir_ventanaId()">Plantilla</div>
                        </s:if>
                        <s:if test="#session._RECURSO.UsuFinBtnUpl">
                           <div dojoType="dijit.form.Button" onClick="versionar()" iconClass="iconoVersionar">Versionar</div>
                        </s:if>
                        <s:if test="#session._RECURSO.UsuFinBtnUpl">
                           <div dojoType="dijit.form.Button"  onClick="abrirVentanaEnumerar()" iconClass="iconoNumerar">Enumerar</div>
                        </s:if>
                        <div dojoType="dijit.form.DropDownButton" label="Otros">
                           <div dojoType="dijit.Menu" style="display: none;">
                              <s:if test="#session._RECURSO.UsuFinBtnArc">
                                 <div dojoType="dijit.MenuItem" iconClass="dijitEditorIcon dijitEditorIconCut" onClick="abrirVentanaArchivar()">Archivar</div>
                              </s:if>
                              <s:if test="#session._RECURSO.UsuFinBtnDocVir">
                                 <div dojoType="dijit.MenuItem" iconClass="dijitEditorIcon dijitEditorIconCut" onClick="loadVirtualDocumentSearch()">Otros Expedientes</div>
                              </s:if>
                                 <div dojoType="dijit.MenuItem" iconClass="dijitEditorIcon dijitEditorIconCut" onClick="showOnlineHelp('<s:property value='#session._USUARIO.rol' />', 'stor')">Ayuda en L&iacute;nea</div>
                           </div>
                        </div>
                        <!--<font color="#111111" class="negrita">
                  <div dojoType="dijit.form.DropDownButton" label="Otros" style="color:black">
                     <div dojoType="dijit.Menu" style="display:none;">
                        <s:if test="mapSessionStor.cambioSala">
                           <div dojoType="dijit.MenuItem" iconClass="dijitEditorIcon dijitEditorIconCut" onclick="abrirVentanaCambioSala()">
                              <font color="#111111" class="negrita">Cambio Sala</font>
                           </div>
                        </s:if>
                        <s:if test="mapSessionStor.refTecrefLeg">
                           <div dojoType="dijit.MenuItem" iconClass="dijitEditorIcon dijitEditorIconCut" onclick="abrirVentanaReformulacion()">
                              <font color="#111111" class="negrita">Reformulacion</font>
                           </div>
                        </s:if>
                     </div>
                  </div>
                        </font>-->
                    </div>
                </td>
            </tr>
            <%--tr align="center">
                <td width="2%" align="center">&nbsp;</td>
                <td width="98%" colspan="2" align="left">
                    <s:if test="#session._RECURSO.UsuFinBtnArc">
                        &nbsp;&nbsp;
                        <img id="<s:property value="objDD.iIdDocumento"/>" onClick="abrirVentanaArchivar(this.id)" src="images/archivar.bmp" border="0" alt="Archivar"/>
                    </s:if>
                    <s:if test="#session._RECURSO.UsuFinBtnArc">
                        <s:form id="frmUploadFile1" action="doUploadFile" method="POST" enctype="multipart/form-data">
                            <div style="position:relative;">
                                <input name="upload" type="file" class="file" size="1" onchange="uploadIt(1)" />
                                <div class="falso">
                                    <img src="images/adjunto.bmp" align="left" border="0" alt="Adjuntar Archivo">
                                </div>
                            </div>
                        </s:form>
                    </s:if>
                </td>
            </tr--%>
            <tr>
                <!--<td></td>-->
                <td style="width: 1%;"></td>
                <td colspan="4" align="left" style="width: 60%;">
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
								<a href="<s:property value="sURL" />" target="_blank"><s:property value="nombreReal" /></a><br />
							</s:iterator>	                  
							</td>
						</tr>
					</table>
					</s:if>
                </td>
                <td style="width: 30%;" align="right">
                	<div id="tabla12" style="text-align: left;">
					<table cellpadding="1" cellspacing="1" class="tableForm">
						<tr>
							<td style="font-size: 10px;font-weight:normal;background-color:#669BCD;color:#ffffff;vertical-align:center;text-align:center;border: 1px #000 solid;">
								Nro. Expediente
							</td>
							<td style="background-color:#ffffff;border: 1px #000 solid;width: 250px;">
								<s:property value="objDD.strReferencia" />
							</td>
						</tr>
						<tr>
							<td style="font-size: 10px;font-weight:normal;background-color:#669BCD;color:#ffffff;vertical-align:center;text-align:center;border: 1px #000 solid;">
								Proceso
							</td>
							<td style="background-color:#ffffff;border: 1px #000 solid;width: 250px;">
								<s:property value="objDD.strProceso" />
							</td>
						</tr>
						<s:if test="objDD.strRazonSocial!=null">
						<tr>
							<td style="font-size: 10px;font-weight:normal;background-color:#669BCD;color:#ffffff;vertical-align:center;text-align:center;border: 1px #000 solid;">
								Cliente
							</td>
							<td style="background-color:#ffffff;border: 1px #000 solid;width: 250px;">
								<s:property value="objDD.strRazonSocial" />
							</td>
						</tr>
						</s:if>
						<s:elseif test="objDD.strCorrentista!=null">
						<tr>
							<td style="font-size: 10px;font-weight:normal;background-color:#669BCD;color:#ffffff;vertical-align:center;text-align:center;border: 1px #000 solid;">
								Concesionario
							</td>
							<td style="background-color:#ffffff;border: 1px #000 solid;width: 250px;">
								<s:property value="objDD.strCorrentista" />
							</td>
						</tr>
						</s:elseif>
					</table>
				</div>
                </td>
            </tr>          
            <tr>
                <td height="50" colspan="6">
                    <table width="100%"  border="0" align="center" bordercolor="#669BCD" bgcolor="#ffffff">
                        <tr>
                            <td>
                                <table height="100" align="center" border="0" >
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
                                        <td height="16" colspan="6" align="left" class="asunto">
                                            <s:textfield name="iIdDoc" cssStyle="display:none" />
                                            <s:textfield name="objDD.cEstado" cssStyle="display:none" />
                                            <s:textfield name="objDD.iIdCliente" cssStyle="display:none" />
                                            <s:property value="asuntoObservacion" />
                                        </td>
                                    </tr>
                                    <!--<tr>
                                        <td height="23" colspan="6"></td>
                                    </tr>-->
                                    <tr>
                                       <td></td>
                                        <td height="16" colspan="5" align="left" class="plomo" >Para: <s:property value="destinatarioObservacion" /></td>
                                    </tr>

                                    <tr>
                                        <td height="5" colspan="6" ><hr></td>
                                    </tr>

                                    <s:if test="mostrarObservacion">
                                        <tr>
                                           <td></td>
                                            <td colspan="5">
                                                <s:if test="accionObservacion.equalsIgnoreCase('aprobar')">
                                                   <font color="blue">
                                                      <p>
                                                         El Usuario "<font class="negrita"> <s:property value="remitenteObservacion" /> </font>"
                                                         Aprobó el Expediente "<font class="negrita"> <s:property value="objDD.strReferencia"/> </font>"
                                                         se han registrado las siguientes observaciones:
                                                      </p>
                                                   </font>
                                                </s:if>
                                                <s:elseif test="accionObservacion.equalsIgnoreCase('rechazar') || accionObservacion.equalsIgnoreCase('reformulacion')">
                                                    <font color="blue">
                                                        <p>
                                                            El Usuario "<font class="negrita"> <s:property value="remitenteObservacion" /> </font>"
                                                            Rechazó el Expediente "<font class="negrita"> <s:property value="objDD.strReferencia"/> </font>"
                                                            por el siguiente motivo:
                                                        </p>
                                                    </font>
                                                </s:elseif>
                                                <s:elseif test="accionObservacion.equalsIgnoreCase('solicitar cambio sala')">
                                                    <font color="blue">
                                                        <p>
                                                            El Usuario "<font class="negrita"> <s:property value="remitenteObservacion" /> </font>"
                                                            solicitó cambio de sala para el expediente "<font class="negrita"> <s:property value="objDD.strReferencia"/> </font>"
                                                            por el siguiente motivo:
                                                        </p>
                                                    </font>
                                                </s:elseif>
                                                <font color="blue">
                                                    <!--<p>-->
                                                        <!--<s:property value="ultimaObservacion" />-->
                                                        <s:if test="ultimaObservacion.length()!=0 ">
                                                           <textarea class="noborder" id="ultimaObservacion" rows="2" cols="125" style="color:blue" readonly><s:property value="ultimaObservacion"/></textarea>
                                                        </s:if>
                                                        
                                                    <!--</p>-->
                                                    <p>
                                                       <!--<s:property value="otraObservacion" />-->
                                                       <s:if test="otraObservacion.length()!=0 ">
                                                          <textarea class="noborder" id="otraObservacion" rows="2" cols="125" style="color:blue" readonly><s:property value="otraObservacion"/></textarea>
                                                       </s:if>
                                                    </p>
                                                </font>
                                            </td>
                                        </tr>

                                        <tr>
                                            <td height="5" colspan="6" ><hr></td>
                                        </tr>
                                    </s:if>

                                    <tr>
                                       <td></td>
                                        <td colspan="5">

                                            <p>
                                                Ud. ha recibido un <font class="negrita"><s:property value="objDD.strTipoDocumento" /></font>
                                                Nro. <font class="negrita"><s:property value="objDD.strNroDocumento" /></font>
                                                con Nro. de Folios <font class="negrita"><s:property value="objDD.iNroFolios" /></font>
                                                el dia <font class="negrita"><s:date name="objDD.strFecha" format="dd/MM/yyyy" /></font>
                                                a las <font class="negrita"><s:date name="objDD.strFecha" format="HH:mm" /></font>
                                                de <font class="negrita"><s:property value="objDD.strRemitente" /></font>,
                                                <s:if test='document.expediente.nrointerno != null && document.expediente.nrointerno != " "'>
		                                          se generó el expediente interno <font class="negrita">Nro. <s:property value="documento.expediente.nrointerno"/> </font>, 
		                                        </s:if> 
                                                referente al proceso <font class="negrita"><s:property value="objDD.strProceso" /></font>.
                                            </p> 
                                            <p> 
                                                El cliente <font class="negrita"><s:property value="objDD.strRazonSocial" /></font>
                                                se identifica con <font class="negrita"><s:property value="objDD.strTipoIdentificacion" /></font>:
                                                <font class="negrita"><s:property value="objDD.strNroIdentificacion" /></font>
                                                y dirección domiciliaria <font class="negrita"><s:property value="objDD.strDireccionPrincipal" /></font>.
                                            </p>
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
                <td height="14"  colspan="6"></td>
            </tr>
        </table>
    </body>
</html>