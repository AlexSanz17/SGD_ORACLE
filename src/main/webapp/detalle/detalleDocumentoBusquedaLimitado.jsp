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

            </script>
            <script type="text/javascript" src="js/dojo/dojo/dojo.js"></script>
        </s:if>
        <script type="text/javascript">
            
             function verArchivo(idArchivo, url, objectId){
              var fecha = new Date();
              window.open("<%=request.getContextPath()%>/verDocumento.png?idArchivo=" +idArchivo + "&url=" + url + "&objectId="+ objectId  + "&accion=abrirDocumento&fecha=" + fecha.getTime());
            }    

            function openDialog(varPagina,varTitulo){
                //alert(varPagina);
                var popupExternal=dijit.byId('external');
                popupExternal.attr("title",varTitulo);
                popupExternal.attr("href",varPagina);
                popupExternal.show();
            }

            function Abrir_ventana (pagina) {
                var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=no, width=440, height=350, top=50, left=200";
                window.open(pagina,"",opciones);
            }

           
             function verDocumento(){
               
            	var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=900, height=700, top=50, left=200";
                var pagina = "verDocumento.action?idDocumento=" + "<s:property value='iIdDoc' />";
                window.open(pagina,"",opciones);
            }

            function abrirHojaRuta(){
            	var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=900, height=700, top=50, left=200";
                var pagina = "ReporteAPN_verHojaRuta.action?idDocumento=" + "<s:property value='iIdDoc' />";
                window.open(pagina,"",opciones);
            }

            function imprimirProveido(){
            	var url = "imprimirProveido.action?iIdDoc=" + "<s:property value='iIdDoc' />";
            	var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=900, height=700, top=50, left=200";
                window.open(url,"",opciones);
            }

            
            function guardarSeguimiento(agregar){
            	var mensaje = "";
            	if(agregar){
            		mensaje="agregar";
            	}else{
            		mensaje="retirar";
            	}
            	if(confirm("¿Desea "+mensaje+" el Documento"+"<s:property value='objDD.strNroDocumento' escape='false' />"+" a su lista de seguimiento?")){
            		if(dijit.byId("dlgProgresBar")!=null){
    					dijit.byId("dlgProgresBar").show() ;
    				}
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
            				if(dijit.byId("dlgProgresBar")!=null){
    				        	dijit.byId("dlgProgresBar").hide() ;
    				        }
            				alert("El documento "+"<s:property value='objDD.strNroDocumento' escape='false'/>"+" ha sido "+mensaje+" su lista de seguimiento");
            				//showGridInbox(sTipoGridActual);
            				if(sTipoGridActual == TIPO_GRID_SEGUIMIENTO){
            					eliminarRegistro();
            					//filtrarSeguimiento();
            				}
            				refrescarDetalle();
            			}
            		});
            	}
            }

        </script>
    </head>
    <body class="soria">
        <table width="100%" border="0">
            <tr>
                <td colspan="6" align="left">
                    <div dojoType="dijit.Toolbar">
                        <s:if test="avisopermiso || objDD.historico=='S' || documento.estado == 'C' || documento.expediente.estado == 'N' || documento.estado == 'T' || #session.busquedaAvanzada == 1">
                            <s:if test="#session._RECURSO.UsuFinBtnTraExp">
                                <!--
                                <div dojoType="dijit.form.Button" onClick="abrirVentanaSeguimiento()" iconClass="dijitEditorIcon dijitEditorIconInsertTable" label="Trazabilidad">
                                </div> -->
                            </s:if>
                            <s:if test="#session._RECURSO.HojaRuta">
                                <div dojoType="dijit.form.Button"  onClick="abrirHojaRuta()" iconClass="siged16 iconoHojaRuta">Hoja de Ruta</div>
                            </s:if>
                        </s:if>
                    </div>
                </td>
            </tr>
           
            <tr>
                <td style="width: 1%;"></td>
                <td colspan="4" style="width: 60%;font-size:12px;" valign="top"> 
                     <br/>
                     <s:textfield name="iIdDoc" cssStyle="display:none" />
                     <s:textfield name="objDD.cEstado" cssStyle="display:none" />
                     <s:textfield name="objDD.iIdCliente" cssStyle="display:none" />
                     <b>Asunto:</b> <s:property value="documento.asunto" />
                     
                     <!--
                     <s:if test="documento.prioridad != null && documento.prioridad!=''">
                         <br /> <b> Prioridad:</b> <img src="images/Prioridad_<s:property value="documento.prioridad" />.png" />
                         <s:if test="objDD.strFechaLimiteAtencion != null && objDD.strFechaLimiteAtencion!=''">
                             <br /> <b> Fecha Limite:</b> <s:property value="objDD.strFechaLimiteAtencion" />
                            <s:textfield name="strAcc" value="aprobar" cssStyle="display:none" />
                         </s:if>  
                      </s:if> -->
                      <!--       
                      <s:if test="objDD.strAccion != null && objDD.strAccion!=''">
                          <br /> <b>  Remitente:</b> <s:property value='objDD.strRemitente'/>
                      </s:if> -->       
                </td>     
                <td style="width: 39%;" align="right" valign="top">
                    <div id="tabla12" style="width: 100%;text-align:left;">
                        <table cellpadding="1" align="right" cellspacing="1" border="1" class="tableForm">
                            
                            <s:if test="documento.ID_CODIGO!=null && documento.ID_EXTERNO==1">
                                <tr style="height:20px;">
                                    <td style="font-size: 11px;font-weight:normal;background-color:#669BCD;color:#ffffff;vertical-align:center;text-align:center;border: 1px #000 solid;">
									   Nro. Tr&aacute;mite
                                    </td>
                                    <td style="background-color:#ffffff;border: 1px #000 solid;width: 250px;">
                                      <b>  <font color="blue"> <s:property value="documento.ID_CODIGO" />  </font> </b>
                                    </td>
                                </tr>
                            </s:if>
                                
                            <s:if test="documento.ID_CODIGO!=null && documento.ID_EXTERNO==0">
                                <tr style="height:20px;">
                                    <td style="font-size: 11px;font-weight:normal;background-color:#669BCD;color:#ffffff;vertical-align:center;text-align:center;border: 1px #000 solid;">
									   Nro. Tr&aacute;mite
                                    </td>
                                    <td style="background-color:#ffffff;border: 1px #000 solid;width: 250px;">
                                      <b>  <font color="blue"> <s:property value="documento.ID_CODIGO" />  </font> </b>
                                    </td>
                                </tr>
                            </s:if>
                            
                            <s:if test="documento.confidencial.equals('S')">
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
                           
                            <s:if test="objDD.strRazonSocial!=null">
                                <tr style="height:20px;">
                                    <td style="font-size: 11px;font-weight:normal;background-color:#669BCD;color:#ffffff;vertical-align:center;text-align:center;border: 1px #000 solid;">
								Cliente
                                    </td>
                                    <td style="background-color:#ffffff;border: 1px #000 solid;width: 250px;">
                                        <s:property value="objDD.strRazonSocial" />
                                    </td>
                                </tr>
                            </s:if>
                                
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
                    </div>
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