<!-- REN detalle del documento que va debajo de la grilla, se carga cuando se presiona una fila de la grilla ------------------->

<?xml version="1.0" encoding="UTF-8"?>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es">
    <head> 
        <title>Detalle del Documento</title>
        
        <META HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE" >
        <s:if test="deMail||enVentana">
            <style type="text/css">
                @import "css/estilo.css";
            </style>
            <link rel="stylesheet" type="text/css" href="css/estilo.css" />
            <link type="text/css" rel="stylesheet" href="css/busquedaExpediente.css" />
            <style type="text/css">
                @import "css/siged.css";
                @import "css/sigedIconos.css";
                @import "css/transferSelect.css";
                @import "js/dojo/dojo/resources/dojo.css";
                @import "js/dojo/dijit/themes/nihilo/nihilo.css";
                @import "js/dojo/dojox/grid/resources/nihiloGrid.css";
                @import "js/dojo/dijit/themes/soria/soria.css";
                @import "js/dojo/dojox/grid/resources/soriaGrid.css";
                @import "js/dojo/dijit/themes/tundra/tundra.css";
                @import "js/dojo/dojox/grid/resources/tundraGrid.css";
                @import "js/dojo/dojox/layout/resources/ExpandoPane.css";
                @import "js/dojo/dojox/layout/resources/ToggleSplitter.css";
            </style>
        </s:if>
        <s:if test="deMail||(#session._USUARIO.rol != null && #session._USUARIO.rol.nombre != 'Siged - Mesa de Partes')||enVentana">
            <link rel="stylesheet" href="js/dojo/dijit/themes/soria/soria.css" />
            <script type="text/javascript">
                var djConfig = {
                    isDebug: false,
                    parseOnLoad: true
                };

                function verArchivo(url) {
                    var service = new dojo.rpc.JsonService("SMDAction.action");
                    var defered = service.verArchivoAlfresco(url);
                    defered.addCallback(function(url) {
                        console.log("URL Alfresco [" + url + "]");
                        alert(url);
                        window.open(url);
                    });
                }
            </script>
            <script type="text/javascript" src="js/dojo/dojo/dojo.js"></script>
        </s:if>
        <script type="text/javascript">
            
            function verArchivoAnexo(url){
               var opciones = "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes, width=550, height=406, top=20, left=70";
               window.open(url, "Anexos", opciones); 
            }     
            
            function abrirVentanaModificar(){
                var url = "";
                if(dijit.byId("tabNuevoDocumento")){
                    dijit.byId("tabContainerInbox").removeChild(dijit.byId("tabNuevoDocumento"));
                    dijit.byId("tabNuevoDocumento").destroyDescendants();
                    dijit.byId("tabNuevoDocumento").destroy();
                }
                
                if ("<s:property value="documento.ID_CODIGO"/>"!=null && "<s:property value="documento.ID_EXTERNO"/>"== "1"){
                    url = "NuevoDocumento_mostrarVistaTramite.action";
                }else{    
                   url = "NuevoDocumento_mostrarVista.action";
                }
                
                var dialog = dijit.byId("dlgProgresBar");
                dialog.show() ;
                var newTab = new dojox.layout.ContentPane({
                	id : "tabNuevoDocumento",
                	jsId : "tabNuevoDocumento",
                	closable: true,
                	href : url + "?idExpediente="+<s:property value='documento.expediente.id' />+"&idDocumento=" + <s:property value='documento.idDocumento' />+"&tipoTransaccion=M",
                	title: "Modificar Documento",
                	onClose: function(){
                                showGridInbox(sTipoGridActual);
                		return true;
                	}
               	});
                dijit.byId("tabContainerInbox").addChild(newTab);
                dijit.byId("tabContainerInbox").selectChild(newTab);
                dialog.hide();
            }
            
            function abrirHojaFirma(){
            	var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=900, height=650, top=50, left=200";
                var pagina = "ReporteAPN_verHojaFirma.action?idDocumento=" + "<s:property value='iIdDoc' />";
                window.open(pagina,"HojaFirma",opciones);
            }

            function abrirHojaRuta(){
            	var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=900, height=700, top=50, left=200";
                var pagina = "ReporteAPN_verHojaRuta.action?idDocumento=" + "<s:property value='iIdDoc' />";
                window.open(pagina,"HojaRuta",opciones);
            }
            
            function abrirVentanaDerivar(opcion) {
                var principal = false;
                
                <s:if test="#session._UPLOADLIST.upload1 == null || #session._UPLOADLIST.upload1.size()==0">
                   if ("<s:property value='documento.proyecto' />"!="<s:property value='@org.ositran.utils.Constantes@DOCUMENTO_PROYECTO'/>"){ 
                     alert("El documento debe contener un archivo principal.");
                     return;
                   }
                   
                </s:if>
                
                <s:iterator value="#session._UPLOADLIST.upload1">
                    <s:if test="principal.equals('S')">
                         principal = true;
                    </s:if>           
                </s:iterator>

                if (!principal){
                    alert("El documento debe contener un archivo principal.");
                    return; 
                }
                
                var opciones = "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes, width=770, height=485, top=20, left=70";
                var pagina = "goDerivarUSER.action?arrIdDoc=" + <s:property value='documento.idDocumento' /> + "&sTipoDerivacion=normal&sOpcion="+opcion;
                window.open(pagina, "Ventana", opciones);
                
            }
            
            
            function mostrarAdjuntarArchivo(){
                        if ("<s:property value='documento.enumerado' />"=="N" && "<s:property value='documento.proyecto' />"!="<s:property value='@org.ositran.utils.Constantes@DOCUMENTO_PROYECTO'/>"){
                            alert("Para adjuntar un archivo debe enumerar el documento.");
                            return;
                         }

                        dojo.xhrPost({
        			url: "goAdjuntarArchivo.action",
        			content:{
        				iIdDoc : "<s:property value='documento.idDocumento' />"
        			},
        			load: function(data){
                        		console.debug("data");
        				if(!dijit.byId("dlgAnexar")){
	        				new dijit.Dialog({
	        					id: "dlgAnexar",
	                			draggable:"true",
	                			style:"height:220px;width:400px;display:none;",
	                			title:"Adjuntar Archivo",
	        		            onClose: dojo.hitch(this, function(){
	        		            	dijit.byId("dlgAnexar").hide();
	        		        		dijit.byId("dlgAnexar").destroyRecursive();
	        		            })
	                		});
        				}
        				console.debug(dijit.byId("dlgAnexar"));
        				dijit.byId("dlgAnexar").attr("content", data);
        				dijit.byId("dlgAnexar").show();
        			}
        		});
        	}

            function mostrarEliminarArchivo(){
        		dojo.xhrPost({
        			url: "goEliminarArchivo.action",
        			content:{
        				iIdDoc : "<s:property value='documento.idDocumento' />"
        			},
        			load: function(data){
        				console.debug("data");
        				if(!dijit.byId("dlgEliminar")){
	        				new dijit.Dialog({
	        					id: "dlgEliminar",
	                			draggable:"true",
	                			style:"height:220px;width:440px;display:none;",
	                			title:"Eliminar Archivo",
	        		                onClose: dojo.hitch(this, function(){
	        		            	  dijit.byId("dlgEliminar").hide();
	        		        	  dijit.byId("dlgEliminar").destroyRecursive();
	        		            })
	                		});
        				}
        				console.debug(dijit.byId("dlgEliminar"));
        				dijit.byId("dlgEliminar").attr("content", data);
        				dijit.byId("dlgEliminar").show();
        			}
        		});
        	}
            
        </script>
    </head>
    <body class="soria">
        <table width="100%" border="0">
            <tr>
                <td colspan="6" align="left">
                    <div dojoType="dijit.Toolbar">
                        <s:if test="objDD.cEstado=='R' || objDD.cEstado=='O' ">  
                            <div dojoType="dijit.form.Button" onClick="abrirVentanaDerivar('reenviar')" iconClass="dijitEditorIcon dijitEditorIconRedo">Derivar</div>
                            <div dojoType="dijit.form.Button" onClick="abrirVentanaCopiarApoyo()" iconClass="dijitEditorIcon dijitEditorIconIndent">Derivar M&uacute;ltiple</div> 
                        </s:if> 
                        <s:else>
                            <div dojoType="dijit.form.Button"  onClick="abrirVentanaModificar()" iconClass="dijitEditorIcon dijitEditorIconSave">Modificar</div>    
                            <div dojoType="dijit.form.Button"  onClick="mostrarAdjuntarArchivo()" iconClass="siged16 iconoAgregar">Agregar Archivo</div>
                            <div dojoType="dijit.form.Button"  onClick="mostrarEliminarArchivo()" iconClass="siged16 iconoRemover">Eliminar Archivo</div>
                        </s:else>    
                        

                        <div dojoType="dijit.form.Button"  onClick="abrirHojaRuta()" iconClass="siged16 iconoHojaRuta">Hoja de Ruta</div>
                          
                    </div>
                </td>
            </tr>
            <tr>
                <td style="width: 2%;"></td>
                <td colspan="2" style="width: 35%;font-size:12px;" valign="top"> 
                    <s:textfield name="iIdDoc" cssStyle="display:none" />
                    <s:textfield name="objDD.cEstado" cssStyle="display:none" />
                    <s:textfield name="objDD.iIdCliente" cssStyle="display:none" />
                    <br />
                    <b>Asunto:</b> <s:property value="documento.asunto" />
                    <s:if test="documento.prioridad != null && documento.prioridad!=''">
                        <br />  <b>Prioridad:</b> <img src="images/Prioridad_<s:property value="documento.prioridad" />.png" />
                         <s:if test="objDD.strFechaLimiteAtencion != null && objDD.strFechaLimiteAtencion!=''">
                             <br /><b>Fecha Limite:</b> <s:property value="objDD.strFechaLimiteAtencion" />
                             <s:textfield name="strAcc" value="aprobar" cssStyle="display:none" />
                         </s:if>  
                    </s:if>
                    <s:if test="objDD.strAccion != null && objDD.strAccion!=''">
                       <br /> <b>  Remitente:</b> <s:property value='objDD.strRemitente'/>
                    </s:if>          
                </td>
                <td colspan="2" style="width: 25%;" valign="top">
                    
                    <s:if test="#session._UPLOADLIST.upload1 != null">
                        <table>
                            <tr>
                                <td></td>
                                <td height="16" colspan="5" align="left" class="plomo"><br/>Adjuntos:</td>
                            </tr>
                            <tr>
                                <td></td>

                                <td height="16" colspan="5" align="left" class="plomo">
                                    <s:iterator value="#session._UPLOADLIST.upload1">
                                           <s:if test="principal.equals('S')">
                                               <a onclick="verArchivo('<s:property value='rutaAlfresco' />');" alt="Ver Archivo"> <b><font color="<s:property value="@org.ositran.utils.Constantes@COLOR_DOCUMENTO_PRINCIPAL"/>"><s:property value="nombreReal"/> </font></b> [<s:property value="usuarioCarga.usuario"/>]<br />
                                           </s:if>
                                    </s:iterator>
                                    <s:iterator value="#session._UPLOADLIST.upload1">               
                                           <s:if test="principal.equals('C')">
                                               <a onclick="verArchivo('<s:property value='rutaAlfresco' />');" alt="Ver Archivo"><b><font color="<s:property value="@org.ositran.utils.Constantes@COLOR_DOCUMENTO_CARGO"/>"><s:property value="nombreReal"/> </font></b> [<s:property value="usuarioCarga.usuario"/>]<br />
                                           </s:if>
                                    </s:iterator>               
                                    <s:iterator value="#session._UPLOADLIST.upload1">               
                                           <s:if test="principal.equals('N')">
                                               <a onclick="verArchivo('<s:property value='rutaAlfresco' />');" alt="Ver Archivo"><b><font color="<s:property value="@org.ositran.utils.Constantes@COLOR_DOCUMENTO_ANEXO"/>"><s:property value="nombreReal"/></font></b> [<s:property value="usuarioCarga.usuario"/>]<br />
                                           </s:if>          
                                    </s:iterator>
                                    <s:iterator value="#session._UPLOADLIST.upload1">               
                                           <s:if test="principal.equals('Y')">
                                               <a onclick="verArchivo('<s:property value='rutaAlfresco' />');" alt="Ver Archivo"><s:property value="nombreReal"/> [<s:property value="usuarioCarga.usuario"/>]<br />
                                           </s:if>
                                    </s:iterator>  
                                                   
                                    <s:if test="objDD.archivoAnexo != ''">
                                         <a onclick="verArchivoAnexo('<s:property value="objDD.archivoAnexo"/>');" alt="Ver Archivo Anexo"> <s:property value="objDD.archivoAnexo"/> [<s:property value="objDD.cantAnexos"/>]<br /> </a>
                                         <s:iterator value="objDD.listAnexos"> 
                                           &nbsp;&nbsp;  <s:property/><br/>
                                         </s:iterator>    
                                    </s:if>               
                                </td>
                            </tr>
                        </table>
                    </s:if>
                </td>
              
                <td style="width: 25%" align="right" valign="top">
                   <!-- <div id="tabla12" style="width: 100%;text-align:left;"> -->
                        <table cellpadding="1" cellspacing="1" border="0" class="tableForm" >
                            <s:if test="documento.ID_CODIGO!=null && documento.ID_EXTERNO==1">
                                <tr style="height:20px;">
                                    <td style="font-size: 11px;font-weight:normal;background-color:#669BCD;color:#ffffff;vertical-align:center;text-align:center;border: 1px #000 solid;">
                                                                    Nro. Tr&aacute;mite
                                    </td>
                                    <td style="background-color:#ffffff;border: 1px #000 solid;width: 250px;">
                                      <b>  <font color="blue"> <s:property value="documento.ID_CODIGO" /> </font> </b>
                                    </td>
                                </tr>
                            </s:if>
                                
                             <s:if test="documento.ID_CODIGO!=null && documento.ID_EXTERNO==0">
                                <tr style="height:20px;">
                                    <td style="font-size: 11px;font-weight:normal;background-color:#669BCD;color:#ffffff;vertical-align:center;text-align:center;border: 1px #000 solid;">
							           Nro. Tr&aacute;mite
                                    </td>
                                    <td style="background-color:#ffffff;border: 1px #000 solid;width: 250px;">
                                      <b>  <font color="blue"> <s:property value="documento.ID_CODIGO" /> </font> </b>
                                    </td>
                                </tr>
                           </s:if>      
                            
                            
                           <s:if test="documento.confidencial.equals('S')">
                            <tr>
                                <td width="108px;" style="font-size: 10px;font-weight:bold;background-color:#DA1217;color:#ffffff;vertical-align:center;text-align:center;border: 1px #000 solid;" colspan="2">
						Documento Confidencial
                                </td>
                             </tr>
                            </s:if>
                            <tr style="height:20px;">
                                <td width="108px;" style="font-size: 11px;font-weight:normal;background-color:#669BCD;color:#ffffff;vertical-align:center;text-align:center;border: 1px #000 solid;">
								Nro. Documento
                                </td>
                                <td style="background-color:#ffffff;border: 1px #000 solid;width: 250px;">
                                    <s:property value="documento.tipoDocumento.nombre" /> - <s:property value="documento.numeroDocumento" />
                                </td>
                            </tr>
                            <tr style="height:20px;">
                                <td style="font-size: 11px;font-weight:normal;background-color:#669BCD;color:#ffffff;vertical-align:center;text-align:center;border: 1px #000 solid;">
								Nro. Carpeta
                                </td>
                                <td style="background-color:#ffffff;border: 1px #000 solid;width: 250px;">
                                   <s:property value="documento.expediente.nroexpediente" />
                                </td>
                            </tr>
                            
                            <s:if test="objDD.strRazonSocial!=''">
                                <tr style="height:20px;">
                                    <td style="font-size: 11px;font-weight:normal;background-color:#669BCD;color:#ffffff;vertical-align:center;text-align:center;border: 1px #000 solid;">
								Cliente
                                    </td>
                                    <td style="background-color:#ffffff;border: 1px #000 solid;width: 250px;">
                                        <s:property value="objDD.strRazonSocial" />
                                    </td>
                                </tr>
                            </s:if>
                              <s:else>
                                  <tr style="height:20px;">
	                                <td style="font-size: 11px;font-weight:normal;background-color:#669BCD;color:#ffffff;vertical-align:center;text-align:center;border: 1px #000 solid;">
		               		Cliente
			               </td>
			                <td style="background-color:#ffffff;border: 1px #000 solid;width: 250px;">
			               		<s:property value="" />
			               </td>
                                  </tr>      
	                       </s:else>         

                              <s:if test="objDD.desDocumentoOrigen!=null && objDD.desDocumentoOrigen!=''">   
                                 <tr style="height:20px;">
                                    <td style="font-size: 11px;font-weight:normal;background-color:#669BCD;color:#ffffff;vertical-align:center;text-align:center;border: 1px #000 solid;">
								Origen
                                    </td>
                                    <td style="background-color:#ffffff;border: 1px #000 solid;width: 250px;">
                                        <s:property value="objDD.desDocumentoOrigen" />
                                    </td>
                                </tr>
                              </s:if>


                        </table>
                  <!--  </div> -->
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
                                        
                                        
                                    <tr>
                                        <td width="3px"></td>
                                        <td colspan="5">
                                            <s:if test="objDD.strAccion != null && objDD.strAccion!=''">
                                                <b> Acciones:</b> <s:property value='objDD.strAccion'/> <br/>
                                            </s:if>
                                            <s:if test="objDD.strContenido != null && objDD.strContenido!=''">   
                                                <div id="contenido"><b> Proveido:</b> <s:property value='objDD.strContenido' escape='false'/></div>
                                            </s:if>
                                               
                                            <script type="text/javascript">
                                            </script> 
                                            <s:if test="objDD.cEstado == 'A'">  
                                                <div>  
                                                    <p>
                                                        Ud. ha registrado un(a) <font class="negrita"><s:property value="objDD.strTipoDocumento" /></font>
                                                        Nro. <font class="negrita"><s:property value="objDD.strNroDocumento" /></font>
                                                        el dia <font class="negrita"><s:date name="objDD.strFecha" format="dd/MM/yyyy" /></font>
                                                        a las <font class="negrita"><s:date name="objDD.strFecha" format="HH:mm" /></font>
                                                    </p>
                                                </div>  
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
                <td height="14" colspan="6"></td>
            </tr>
        </table>
        <%--
              <div id="external" dojoType="dijit.Dialog" title="My external dialog"
                  href="http://docs.dojocampus.org/HelpContents" style="width: 800px; height: 500px;">
             </div>--%>
    </body>
</html>