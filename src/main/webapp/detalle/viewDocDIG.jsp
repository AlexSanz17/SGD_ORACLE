<?xml version="1.0" encoding="UTF-8"?>
<%@ taglib prefix="s" uri="/struts-tags"  %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
         <meta http-equiv="cache-control" content="no-cache">
        <title>Documento en Digitalizaci&oacute;n</title>
        <script type="text/javascript" src="js/siged/upload.js"></script>
        <script type="text/javascript">
            if(dijit.byId("dlgProgresBar")){
                dijit.byId("dlgProgresBar").destroy();
            }

            if(dijit.byId("progresBar")){
                dijit.byId("progresBar").destroy();
            }

            var registrando=false;
            function registrarDocumento(idExpediente, iIdDocumento, sNroDocumento) {
                if(!registrando){
                    registrando=true;
                    $(".boton").fadeTo(0, 0.45).css("cursor","wait");
                    var service = new dojo.rpc.JsonService("SMDAction.action");
                    var filesUpload = service.isThereAttachment(idExpediente);
                    var od = document.getElementById("observacionDigitalizador").value;     
                    
                    filesUpload.addCallback(function(data) {
                       var thereIsAttachment = parseInt(data);
                       var applySubmit = false;

                       switch (thereIsAttachment) {
                          case -1 :
                             alert("Ocurrió un error en el sistema");
                             break;
                          case 0 :
                             alert("Ud. debe adjuntar al menos un archivo");
                             break;
                          case 1 :
                             applySubmit = confirm("Desea registrar el documento " + sNroDocumento + "?");
                             break;
                          case 2 :
                             alert("Los archivos que intenta registrar ya existen en el repositorio.");
                             break;
                       }

                        if (applySubmit) {
                            dojo.xhrPost({
                                url: "doRegistrarDIG.action",
                                content: {
                                    iIdDocumento: iIdDocumento,
                                    observacionDigitalizador : od
                                },
                                form: dojo.byId("frmRegistrarDIG"),
                                mimetype: "text/html",
                                load: function() {
                                    self.parent.location.href = "inicio.action?sTipoFrame=grid";
                                }
                            });
                        } else {
                            registrando=false;
                            $(".boton").fadeTo("slow",1).css("cursor","pointer");
                        }
                    });
                }
            }

            function limpiarText(){
                var idFieldUpload=document.getElementById("idFieldUpload");
                var frmUploadFile1=document.getElementById("frmUploadFile1");
                idFieldUpload.setAttribute("value","");
                frmUploadFile1.reset();
            }

            function abrirArchivo(pagina) {
                var opciones = "location=mainFrame";

                window.open(pagina,"",opciones);
            }

            function crearBotonAdjuntar(){
                var boton=new dijit.form.Button({
                    iconClass: "siged16 sigedSave16",
                    //iconClass: "uploadBtn",
                    type: "button"
                },"btn");
                //alert("jojo 0") ;
                var upload= new dojox.form.FileUploader({
                    //id:"bt",
                    //jsId :"bt",
                    //button: dijit.byId("btn"),
                    button: boton,
                    //degradable:true,
                    uploadUrl: "doUploadFile.action",
                    uploadOnChange: true,
                    postData: {
                        iIdUpload: "1"
                    },
                    //hoverClass:"btnHover",
                    //activeClass:"uploadBtn",
                    //disabledClass:"btnDisable",
                    selectMultipleFiles: false,
                    //isDebug: false,
                    //activeClass:"siged66 sigedSave16",
                    //iconClass:"siged16 sigedSave16",
                    htmlFieldName:"upload"
                });
                //alert("jojo 1") ;
                //alert("jojo 1.0") ;
                // dijit.byId("btn").attr("iconClass", "siged16 sigedSave16" );




                // dijit.byId("btn").
                // alert("jojo 1") ;
                dojo.connect(upload, "onComplete", function(){
                    dojo.xhrPost({
                        url: "showUploadFilesDojo2.action",
                        content: {
                            iIdUpload: 1
                        },
                        mimetype: "text/html",
                        handle: function(data) {
                            dojo.byId('divShowFile' + 1).innerHTML = data;
                        }
                    });
                    upload.value = "";
                });

            }
            dojo.addOnLoad(function() {
                dojox.embed.Flash.available = 0;
                crearBotonAdjuntar();
            });

            function abrirVentanaSeguimiento() {
              var opciones = "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=no, width=650, height=320, top=50  , left=200";
                var pagina ="goViewSeguimiento.action?iIdDocumento=" + <s:property value='objDD.iIdDocumento' />;
              //openDialog(pagina,"Tra  zabilidad");
                window.open("goViewSeguimiento.action?mode=documento&iIdDocumento=" + <s:property value='objDD.iIdDocumento' />, "", opciones);
            }
         
        </script>
    </head>
    <body>
        <table border="0" width="100%">
            <tr align="center" >
                <td width="1%" align="center">&nbsp;</td>
                <td width="99%" align="left">
                    <button id="btn">Adjuntar</button>
                    <button dojoType="dijit.form.Button"
                            type="button"
                            iconClass="siged16 sigedSave16"
                            onClick="registrarDocumento(<s:property value='objDD.iIdExpediente'/>, <s:property value='objDD.iIdDocumento'/>,'<s:property value='objDD.strNroDocumento'/>');"
                            showLabel="true">Registrar Documento</button>
                    &nbsp;<span style="color:red;font-weight:bold;">Capacidad M&aacute;xima por Archivo <s:property value="digmensaje" /></span>

                    <div dojoType="dijit.form.Button" onClick="abrirVentanaSeguimiento()" iconClass="dijitEditorIcon dijitEditorIconInsertTable" label="Trazabilidad">
                    </div>
                </td>
            </tr>
            <tr>
                <td></td>
                <td height="16" align="left" class="plomo">Adjuntos :</td>
            </tr>
            <tr>
                <td></td>
                <td height="16" align="left" class="plomo">
                    <div id="divShowFile2">
                        <%@include file="/pages/upload/lstUploadWithOutTextArea.jsp" %>
                    </div>
                    <div id="divShowFile1"></div>
                </td>
            </tr>
            <tr>
                <td></td>
                <td height="14">
                    <table width="95%"  border="1" align="center" bordercolor="#669BCD" bgcolor="#BFD9F1">
                        <tr>
                            <td height="75">
                                <s:form name="frmRegistrarDIG" action="doRegistrarDIG" method="POST">
                                    <table width="98%" height="97" align="center" >
                                        <tr>
                                            <td width="2%"></td>
                                            <td width="15%" height="5"></td>
                                            <td width="17%"></td>
                                            <td width="15%"></td>
                                            <td width="45%"></td>
                                            <td width="3%">
                                            </td>
                                        </tr>
                                        <s:if test="objDD.sObservacionRechazo != null">
                                            <tr>
                                                <td></td>
                                                <td height="16" align="left">Observaci&oacute;n de Rechazo</td>
                                                <td colspan="3" align="left" class="label"><s:property value="objDD.sObservacionRechazo" /></td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td colspan="6" height="15"><hr></td>
                                            </tr>
                                        </s:if>
                                        <tr>
                                            <td></td>
                                            <td height="16" align="left">Referencia</td>
                                            <td colspan="3" align="left" class="label"><s:property value="objDD.strReferencia" /></td>
                                            <td></td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td height="16" align="left">Proceso</td>
                                            <td colspan="3" align="left" class="label"><s:property value="objDD.strProceso" /></td>
                                            <td></td>
                                        </tr>
                                        <s:if test="!objDD.strProceso.startsWith('OSINERGMIN')">
                                            <tr>
                                                <td></td>
                                                <td height="16" align="left">Area Destino</td>
                                                <td colspan="3" align="left" class="label"><s:property value="objDD.strUnidad" /></td>
                                                <td align="left"></td>
                                            </tr>
                                        </s:if>
                                        <tr>
                                            <td ></td>
                                            <td height="16" align="left">Destinatario</td>
                                            <td colspan="3" class="label"><s:property value="objDD.strResponsable" /></td>
                                            <td></td>
                                        </tr>
                                        <tr>
                                            <td ></td>
                                            <td height="16" align="left">Observacion DIG.</td>
                                            <td colspan="3"><s:textfield name="observacionDigitalizador" id="observacionDigitalizador"
                                                         size="70" maxLength="500" cssStyle="border-style:solid;border-color:#336699;border-width:1px;"/></td>
                                            <td></td>
                                        </tr>
                                        <tr>
                                            <td colspan="6" height="15"><hr></td>
                                        </tr>
                                        <tr>
                                            <td colspan="6" height="15" class="label">DOCUMENTO</td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td height="16" align="left">Tipo Documento</td>
                                            <td align="left" width="17%" class="label"><s:property value="objDD.strTipoDocumento" /></td>
                                            <td align="left"></td>
                                            <td align="left" width="45%" class="label"></td>
                                            <td></td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td height="16" align="left">Nro Documento</td>
                                            <td align="left" width="17%" class="label"><s:property value="objDD.strNroDocumento" /></td>
                                            <td align="left" ></td>
                                            <td align="left" width="45%" class="label"></td>
                                            <td></td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td height="16" align="left">Nro Folios</td>
                                            <td align="left" width="17%" class="label"><s:property value="objDD.iNroFolios" /></td>
                                            <td align="left" ></td>
                                            <td align="left" width="45%" class="label"></td>
                                            <td></td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td height="16" align="left">Asunto</td>
                                            <td colspan="3" align="left" class="label"><s:property value="objDD.strAsunto" /></td>
                                            <td></td>
                                        </tr>
                                        <tr>
                                            <td colspan="6" height="15"><hr></td>
                                        </tr>
                                        <tr>
                                            <td colspan="6" height="15" class="label">CLIENTE</td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td height="16" align="left">Tipo Doc Identidad</td>
                                            <td align="left" width="17%" class="label">
                                                <s:property value="objDD.strTipoIdentificacion" />
                                            </td>
                                            <td align="left" class="label" ></td>
                                            <td align="left" class="label" ></td>
                                            <td></td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td height="16" align="left">Nro. Doc Identidad</td>
                                            <td align="left" width="17%" class="label"><s:property value="objDD.strNroIdentificacion" /></td>
                                            <td align="left" ></td>
                                            <td align="left" width="45%" class="label"></td>
                                            <td></td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td height="16" align="left">Nombre/Raz&oacute;n Social</td>
                                            <td colspan="3" align="left" class="label"><s:property value="objDD.strRazonSocial" /></td>
                                            <td></td>
                                        </tr>
                                        <s:if test="objDD.strTipoIdentificacion.equals('RUC')">
                                            <tr>
                                                <td></td>
                                                <td height="16" align="left">Representante Legal</td>
                                                <td colspan="3" align="left" class="label"><s:property value="documento.expediente.cliente.representantelegal" /></td>
                                                <td></td>
                                            </tr>
                                        </s:if>
                                        <tr>
                                            <td></td>
                                            <td height="16" align="left">Direcci&oacute;n</td>
                                            <td colspan="3" align="left" class="label"><s:property value="objDD.strDireccionPrincipal" /></td>
                                            <td></td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td height="16" align="left">Otra Direcci&oacute;n</td>
                                            <td colspan="3" align="left" class="label"><s:property value="objDD.strDireccionAlternativa" /></td>
                                            <td></td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td height="16" align="left">Teléfono</td>
                                            <td colspan="3" align="left" class="label"><s:property value="objDD.strTelefonoCliente" /></td>
                                            <td></td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td height="16" align="left">Correo Electrónico</td>
                                            <td colspan="3" align="left" class="label"><s:property value="objDD.strCorreoCliente" /></td>
                                            <td></td>
                                        </tr>
                                        <s:if test="objDD.strAbreviado == 'stor'">
                                            <tr>
                                                <td colspan="6" height="15"><hr></td>
                                            </tr>
                                            <tr>
                                                <td colspan="6" height="15" class="label">CONCESIONARIO</td>
                                            </tr>
                                            <tr>
                                                <td></td>
                                                <td height="16" align="left">RUC</td>
                                                <td colspan="3" class="label"><s:property value="objDD.strRUC" /></td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td></td>
                                                <td height="16" align="left">Razón Social</td>
                                                <td colspan="3" class="label"><s:property value="objDD.strCorrentista" /></td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td></td>
                                                <td height="16" align="left">Dirección</td>
                                                <td colspan="3" class="label"><s:property value="objDD.strDireccionConcesionario" /></td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td></td>
                                                <td height="16" align="left">Correo Electrónico</td>
                                                <td colspan="3" class="label"><s:property value="objDD.strCorreoConcesionario" /></td>
                                                <td></td>
                                            </tr>
                                        </s:if>
                                        <tr>
                                            <td></td>
                                            <td height="16" align="left">Observación</td>
                                            <td colspan="3" class="label"><s:property value="objDD.observacionDocumento" /></td>
                                            <td></td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td colspan="5" height="16" align="left" class="label">&nbsp;</td>
                                        </tr>
                                    </table>
                                </s:form>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr align="center">
                <td width="1%" align="center">&nbsp;</td>
                <td width="99%" colspan="2" align="left">
                    <button dojoType="dijit.form.Button"
                            type="button"
                            iconClass="siged16 sigedSave16"
                            onClick="registrarDocumento(<s:property value='objDD.iIdExpediente'/>, <s:property value='objDD.iIdDocumento'/>,'<s:property value='objDD.strNroDocumento'/>');"
                            showLabel="true">Registrar Documento</button>
                </td>
            </tr>
            <tr>
                <td height="14" colspan="2"></td>
            </tr>
        </table>
        <%@ include file="../pages/util/progressBar.jsp" %>
    </body>
</html>
