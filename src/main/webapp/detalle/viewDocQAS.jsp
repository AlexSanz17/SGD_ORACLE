<?xml version="1.0" encoding="UTF-8" ?>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="java.util.List"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta http-equiv="cache-control" content="no-cache">
        <title>Documento Control de Calidad</title>
        <link rel="stylesheet" type="text/css" href="css/detalle/qas.css" />
        <script type="text/javascript">
            var sDep1 = "<s:property value='documento.expediente.cliente.ubigeoPrincipal.provincia.departamento.iddepartamento' />";
            var sPrv1 = "<s:property value='documento.expediente.cliente.ubigeoPrincipal.provincia.idprovincia' />";
            var sDst1 = "<s:property value='documento.expediente.cliente.ubigeoPrincipal.iddistrito' />";
            var sDep2 = "<s:property value='documento.expediente.cliente.ubigeoAlternativo.provincia.departamento.iddepartamento' />";
            var sPrv2 = "<s:property value='documento.expediente.cliente.ubigeoAlternativo.provincia.idprovincia' />";
            var sDst2 = "<s:property value='documento.expediente.cliente.ubigeoAlternativo.iddistrito' />";
            var QAS_ESTAENFLUJO        = "<s:property value='objDD.estaenflujo' />";
            var QAS_NROIDENTIFCACION   = "<s:property value='objDD.strNroIdentificacion' />";
            var QAS_TIPOIDENTIFICACION = "<s:property value='objDD.strTipoIdentificacion' />";
            var QAS_TIPOPROCESO        = "<s:property value='objDD.strAbreviado' />";
            var QAS_CLIENTE = "<s:property value='nombreCliente' />";
            console.debug("QAS_ESTAENFLUJO [%s]", QAS_ESTAENFLUJO);
            console.debug("QAS_NROIDENTIFCACION [%s]", QAS_NROIDENTIFCACION);
            console.debug("QAS_TIPOIDENTIFICACION [%s]", QAS_TIPOIDENTIFICACION);
            console.debug("QAS_TIPOPROCESO [%s]", QAS_TIPOPROCESO);
        </script>
        <script type="text/javascript" src="js/siged/registroClienteMP.js"></script>
        <script type="text/javascript" src="js/siged/viewDocumentoQAS.js"></script>
        <script type="text/javascript">
            var primeroQAS = true;
            handlerQAS = dojo.connect(dijit.byId("contentPaneDetail"), "onLoad", viewDocumentoQASOnLoad);

            if (dojo.isIE) {
                console.debug("xxxxxxxxxxxx es internet explorer");
                //viewDocumentoQASOnLoad();
                //fillDataCliente(QAS_NROIDENTIFCACION);
                completarComboCliente();
                UpdateInfoCliente(QAS_NROIDENTIFCACION);
            }else{
                console.debug("xxxxxxxxxxxx no es internet explorer");}

            // Carga d  e Stores
            var strProceso       = new dojo.data.ItemFileReadStore({url: "autocompletarProceso.action?sTipoProceso=" + "<s:property value='objDD.strAbreviado' />"});
            //var strCliente       = new dojo.data.ItemFileReadStore({url: "obtenerClientePorTipoIdentificacion.action?sTipoIdentificacion=" + ""});
            var strConcesionario = new dojo.data.ItemFileReadStore({url: "retrieveDataConcesionario.action"});
            var strTipoDocumento = new dojo.data.ItemFileReadStore({url: "autocompletarAllTipoDocumento.action"});
            var strDepartamento  = new dojo.data.ItemFileReadStore({url: "listaDepartamentos.action"});
            var listaMotivos     = new dojo.data.ItemFileReadStore({url: "listaMotivos.action"});

            var fillDataCliente = function(sNroIdentificacion) {
                console.debug("xxxxxxxxxxxxxxxxxxxxxxxfilldatacliente");
                console.debug("sNroIdentificacion:"+sNroIdentificacion);
                if (sNroIdentificacion == undefined || sNroIdentificacion == "") {
                    hideTBODY("tbDataRUC");
                    hideTBODY("tbDataDNI");
                    hideTBODY("tbDataCliente");

                    return;
                }
            
                if(primeroQAS){
                    primeroQAS = false;
                    if (QAS_TIPOIDENTIFICACION == "RUC") {
                        hideTBODY("tbDataDNI");
                    } else if (QAS_TIPOIDENTIFICACION == "DNI" || QAS_TIPOIDENTIFICACION == "Otro") {
                        hideTBODY("tbDataRUC");
                    }
                    return;
                }
            
                if (dijit.byId("objDD.strNroIdentificacion") != null && dijit.byId("objDD.strNroIdentificacion") != undefined) {
                    console.debug("(fillDataCliente) - Cliente selecccionado con Nro de Identificacion [%s]", sNroIdentificacion);
                    var strCliente = dijit.byId("objDD.strNroIdentificacion").store;

                    strCliente.fetchItemByIdentity({
                        identity: sNroIdentificacion,
                        onItem: function(item) {
                            var tipoIdentificacion = strCliente.getValue(item, "tipoidentificacion");
                            dijit.byId("objDD.strNroIdentificacion").attr("value", "<s:property value='objDD.strNroIdentificacion' />");
                            if (tipoIdentificacion == "RUC") {
                                hideTBODY("tbDataDNI");
                                dijit.byId("objDocumento.expediente.cliente.razonSocial").attr("value", "<s:property value='objDD.strRazonSocial' />");
                                dijit.byId("objDocumento.expediente.cliente.representanteLegal").attr("value", "<s:property value='objDD.strRepresentanteLegal' />");
                                showTBODY("tbDataRUC");
                            } else if (tipoIdentificacion == "DNI" || tipoIdentificacion == "Otro") {
                                hideTBODY("tbDataRUC");
                                dijit.byId("objDocumento.expediente.cliente.nombres").attr("value", "<s:property value='objDD.clientenombres' />");
                                dijit.byId("objDocumento.expediente.cliente.apellidoPaterno").attr("value", "<s:property value='objDD.clienteapellidopaterno' />");
                                dijit.byId("objDocumento.expediente.cliente.apellidoMaterno").attr("value", "<s:property value='objDD.clienteapellidomaterno' />");
                                showTBODY("tbDataDNI");
                            }

                            dijit.byId("objDD.iIdCliente").attr("value", strCliente.getValue(  item, "idcliente"));
                            dijit.byId("objDocumento.expediente.cliente.direccionPrincipal").attr("value", "<s:property value='objDD.strDireccionPrincipal' />");
                            sPrv1 = "<s:property value='objDD.iIdProvincia' />";
                            sDst1 = "<s:property value='objDD.iIdDistrito' />";
                            dijit.byId("objDocumento.expediente.cliente.ubigeoPrincipal.provincia.departamento.iddepartamento").attr("value", "<s:property value='objDD.iIdDepartamento' />");
                            console.debug("(fillDataCliente) - Almacenando sPrv1 [%s] sDst1 [  %s]", sPrv1, sDst1);
                            dijit.byId("objDocumento.expediente.cliente.direccionalternativa").attr("value", "<s:property value='objDD.strDireccionAlternativa' />");
                            sPrv2 = "<s:property value='objDD.iIdProvinciaAlt' />";
                            sDst2 = "<s:property value='objDD.iIdDistritoAlt' />";
                            dijit.byId("objDocumento.expediente.cliente.ubigeoAlternativo.provincia.departamento.iddepartamento").attr("value", "<s:property value='objDD.iIdDepartamentoAlt' />");
                            console.debug("(fillDataCliente) - Almacenando sPrv2 [%s] sDst2 [  %s]", sPrv2, sDst2);
                            dijit.byId("objDocumento.expediente.cliente.telefono").attr("value", "<s:property value='objDD.strTelefonoCliente' />");
                            dijit.byId("objDocumento.expediente.cliente.correo").attr("value", "<s:property value='objDD.strCorreoCliente' />");
                            showTBODY("tbDataCliente");
                        }
                    });
                }
            };
         
            dojo.addOnLoad(function(){
                new dijit.form.FilteringSelect({
                    id             	: "objDocumento.expediente.cliente.ubigeoPrincipal.provincia.departamento.iddepartamento",
                    jsId           	: "objDocumento.expediente.cliente.ubigeoPrincipal.provincia.departamento.iddepartamento",
                    name           	: "objDocumento.expediente.cliente.ubigeoPrincipal.provincia.departamento.iddepartamento",
                    store          	: strDepartamento,
                    searchAttr     	: "label",
                    autoComplete   	: false,
                    hasDownArrow   	: true,
                    highlightMatch 	: "all",
                    invalidMessage 	: "Seleccione un departamento",
                    onChange       	: function(){
                        if(this.value!=""){
                            updateStore(this.value,"provincia1");
                            console.debug("EN FSDEPARTAMENTO con valor seteado a [" + this.value + "] sPrv1 "+ sPrv1 + " sDst1 " + sDst1);
                            dijit.byId("objDocumento.expediente.cliente.ubigeoPrincipal.provincia.idprovincia").attr("value", sPrv1);
                        }
                    },
                    required       	: false
                },"objDocumento.expediente.cliente.ubigeoPrincipal.provincia.departamento.iddepartamento");
                new dijit.form.FilteringSelect({
                    id             	: "objDocumento.expediente.cliente.ubigeoPrincipal.provincia.idprovincia",
                    jsId           	: "objDocumento.expediente.cliente.ubigeoPrincipal.provincia.idprovincia",
                    name           	: "objDocumento.expediente.cliente.ubigeoPrincipal.provincia.idprovincia",
                    searchAttr     	: "label",
                    autoComplete   	: false,
                    hasDownArrow   	: true,
                    highlightMatch 	: "all",
                    invalidMessage 	: "Seleccione una provincia",
                    onChange       	: function(){
                        if(this.value!= ""){
                            updateStore(this.value,"distrito1");
                            console.debug("EN FSPROVINCIA con valor seteado a [" + this.value + "] sPrv1 "+ sPrv1 + " sDst1 " + sDst1);
                            dijit.byId("objDocumento.expediente.cliente.ubigeoPrincipal.iddistrito").attr("value", sDst1);
                        }
                    },
                    required       	: false
                },"objDocumento.expediente.cliente.ubigeoPrincipal.provincia.idprovincia");
                new dijit.form.FilteringSelect({
                    id             	: "objDocumento.expediente.cliente.ubigeoPrincipal.iddistrito",
                    jsId           	: "objDocumento.expediente.cliente.ubigeoPrincipal.iddistrito",
                    name           	: "objDocumento.expediente.cliente.ubigeoPrincipal.iddistrito",
                    searchAttr     	: "label",
                    autoComplete   	: false,
                    hasDownArrow   	: true,
                    highlightMatch 	: "all",
                    invalidMessage 	: "Seleccione un distrito",
                    required       	: false
                },"objDocumento.expediente.cliente.ubigeoPrincipal.iddistrito");
                new dijit.form.FilteringSelect({
                    id             	: "objDocumento.expediente.cliente.ubigeoAlternativo.provincia.departamento.iddepartamento",
                    jsId           	: "objDocumento.expediente.cliente.ubigeoAlternativo.provincia.departamento.iddepartamento",
                    name           	: "objDocumento.expediente.cliente.ubigeoAlternativo.provincia.departamento.iddepartamento",
                    store          	: strDepartamento,
                    searchAttr     	: "label",
                    autoComplete   	: false,
                    hasDownArrow   	: true,
                    highlightMatch 	: "all",
                    invalidMessage 	: "Seleccione un departamento",
                    onChange       	: function(){
                        if(this.value!=""){
                            updateStore(this.value,"provincia2");
                            console.debug("EN FSDEPARTAMENTO con valor seteado a [" + this.value + "] sPrv2 "+ sPrv2 + " sDst2 " + sDst2);
                            dijit.byId("objDocumento.expediente.cliente.ubigeoAlternativo.provincia.idprovincia").attr("value", sPrv2);
                        }
                    },
                    required       	: false
                },"objDocumento.expediente.cliente.ubigeoAlternativo.provincia.departamento.iddepartamento");
                new dijit.form.FilteringSelect({
                    id             	: "objDocumento.expediente.cliente.ubigeoAlternativo.provincia.idprovincia",
                    jsId           	: "objDocumento.expediente.cliente.ubigeoAlternativo.provincia.idprovincia",
                    name           	: "objDocumento.expediente.cliente.ubigeoAlternativo.provincia.idprovincia",
                    searchAttr     	: "label",
                    autoComplete   	: false,
                    hasDownArrow   	: true,
                    highlightMatch 	: "all",
                    invalidMessage 	: "Seleccione una provincia",
                    onChange       	: function(){
                        if(this.value!=""){
                            updateStore(this.value,"distrito2");
                            console.debug("EN FSPROVINCIA con valor seteado a [" + this.value + "] sPrv2 "+ sPrv2 + " sDst2 " + sDst2);
                            dijit.byId("objDocumento.expediente.cliente.ubigeoAlternativo.iddistrito").attr("value", sDst2);
                        }
                    },
                    required       	: false
                },"objDocumento.expediente.cliente.ubigeoAlternativo.provincia.idprovincia");
                new dijit.form.FilteringSelect({
                    id             	: "objDocumento.expediente.cliente.ubigeoAlternativo.iddistrito",
                    jsId           	: "objDocumento.expediente.cliente.ubigeoAlternativo.iddistrito",
                    name           	: "objDocumento.expediente.cliente.ubigeoAlternativo.iddistrito",
                    searchAttr     	: "label",
                    autoComplete   	: false,
                    hasDownArrow   	: true,
                    highlightMatch 	: "all",
                    invalidMessage 	: "Seleccione un distrito",
                    required       	: false
                },"objDocumento.expediente.cliente.ubigeoAlternativo.iddistrito");
        	
                dijit.byId("objDocumento.expediente.cliente.ubigeoPrincipal.provincia.departamento.iddepartamento").attr("value", sDep1);
                dijit.byId("objDocumento.expediente.cliente.ubigeoAlternativo.provincia.departamento.iddepartamento").attr("value", sDep2);
                //                                alert("cliente---"+QAS_CLIENTE);
                QAS_CLIENTE = QAS_CLIENTE.toString();
                do{
                    QAS_CLIENTE = QAS_CLIENTE.replace('&amp;', '&');
                }while(QAS_CLIENTE.indexOf('&amp;') >= 0)
//                    alert("cliente---"+QAS_CLIENTE);
                dijit.byId("objDD.strNroIdentificacion").attr("displayedValue",QAS_CLIENTE);
            });
        </script>
    </head>
    <body>
        <s:if test="objDD.cDisponible == 'S'">
            <s:form name="frmQAS" action="doDerivarQAS" method="post">
                <table width="100%">
                    <tr>
                        <td height="14" colspan="6"></td>
                    </tr>
                    <tr>
                        <td width="1%">&nbsp;</td>
                        <td width="99%" colspan="2" align="left">
                            <img id="idbtnAprobar" src="images/aprobar.png" alt="Aprobar" style="cursor: pointer;" class="aprobar" />
                            <img id="idbtnRechazar" src="images/rechazar.png" alt="Rechazar" style="cursor: pointer;" class="rechazar" />
                        </td>
                    </tr>
                    <tr>
                        <td height="14" colspan="6"></td>
                    </tr>
                    <s:if test="objDD.cDisponible == 'S'">
                        <s:if test="#session._UPLOADLIST.upload1 != null">
                            <tr>
                                <td></td>
                                <td colspan="5" class="plomo">Adjuntos:</td>
                            </tr>
                            <tr>
                                <td></td>
                                <td height="16" colspan="4" align="left" class="plomo">
                                    <s:iterator value="#session._UPLOADLIST.upload1" id="arc">
                                        <a href="javascript:abrirArchivo('qasFileDownload.action?idArchivo=<s:property value='idArchivo' />&amp;tipo=Y&amp;iIdDoc=<s:property value='iIdDoc' />');"><s:property	value="nombreReal" /></a>
                                        <br />
                                    </s:iterator>
                                </td>
                            </tr>
                        </s:if>
                    </s:if>
                    <tr>
                        <td height="14" colspan="6"></td>
                    </tr>
                    <tr>
                        <td height="14" colspan="6">
                            <table border="1" align="center" bordercolor="#669BCD" bgcolor="#BFD9F1" width="98%">
                                <tr>
                                    <td>
                                        <table>
                                            <tr>
                                                <td width="2%"></td>
                                                <td width="18%" height="5"></td>
                                                <td width="17%"></td>
                                                <td width="15%"></td>
                                                <td width="45%"></td>
                                                <td width="3%">
                                                    <s:hidden name="objDD.iIdDocumento" id="idDocumento"/>
                                                    <s:hidden name="objDD.iIdExpediente" />
                                                    <s:hidden name="objDD.historico" />
                                                    <s:hidden name="objDD.strAbreviado" />
                                                    <s:hidden name="objDD.cEstado" />
                                                    <s:hidden name="objDD.strNroMesaPartes" />
                                                    <s:hidden name="objDD.cPrincipal" />
                                                    <s:hidden name="strAcc" value="derivar" />
                                                </td>
                                            </tr>
                                            <tr>
                                                <td></td>
                                                <td height="25" align="left">Nro Expediente</td>
                                                <td colspan="3" align="left" class="label">
                                                    <div dojoType="dijit.form.TextBox" id="objDD.strReferencia" jsId="objDD.strReferencia" name="objDD.strReferencia" readOnly="true" style="width:300px" trim="true" value="<s:property value='objDD.strReferencia' />"></div>
                                                </td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td></td>
                                                <td height="25" align="left">Proceso</td>
                                                <td colspan="3" class="label">
                                                    <div dojoType="dijit.form.FilteringSelect" id="objDD.iIdProceso" jsId="objDD.iIdProceso" name="objDD.iIdProceso" store="strProceso" idAttr="id" style="width:300px" labelAttr="label" onChange="UpdateInfoProceso" searchAttr="label" hasDownArrow="false" value="<s:property value='objDD.iIdProceso' />">
                                                        <script type="dojo/method" event="postCreate">
                                                            if(QAS_ESTAENFLUJO=="S"){
                                                            this.attr("readOnly", true);
                                                            }
                                                        </script>
                                                    </div>
                                                </td>
                                                <td></td>
                                            </tr>
                                            <s:if test="!objDD.strProceso.startsWith('OSINERGMIN')">
                                                <tr>
                                                    <td></td>
                                                    <td height="25" align="left">Area Destino</td>
                                                    <td colspan="3" align="left" class="label">
                                                        <div dojoType="dijit.form.TextBox"  id="objDD.strUnidad" jsId="objDD.strUnidad" name="objDD.strUnidad" readOnly="true" style="width:300px" trim="true" value="<s:property value='objDD.strUnidad' />"></div>
                                                    </td>
                                                    <td align="left"></td>
                                                </tr>
                                            </s:if>
                                            <tr>
                                                <td></td>
                                                <td height="25" align="left">Destinatario</td>
                                                <td colspan="3" class="label">
                                                    <div dojoType="dijit.form.TextBox" id="objDD.iIdResponsable" jsId="objDD.iIdResponsable" name="objDD.iIdResponsable" readOnly="true" style="width:300px;display:none;" trim="true" value="<s:property value='objDD.iIdResponsable' />"></div>
                                                    <div dojoType="dijit.form.TextBox" id="objDD.strResponsable" jsId="objDD.strResponsable" name="objDD.strResponsable" readOnly="true" style="width:300px" trim="true" value="<s:property value='objDD.strResponsable' />"></div>
                                                </td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td colspan="6" height="15">
                                                    <hr />
                                                </td>
                                            </tr>
                                            <tr>
                                                <td colspan="6" height="15" class="label">DOCUMENTO</td>
                                            </tr>
                                            <tr>
                                                <td></td>
                                                <td height="25" align="left">Tipo Documento</td>
                                                <td align="left" width="17%" class="label">
                                                    <div dojoType="dijit.form.FilteringSelect" id="objDD.iIdTipoDocumento" jsId="objDD.iIdTipoDocumento" name="objDD.iIdTipoDocumento" invalidMessage="Seleccione un tipo de Documento" required="false" searchAttr="label" store="strTipoDocumento" value="<s:property value='objDD.iIdTipoDocumento' />">
                                                        <script type="dojo/method" event="postCreate">
                                                            if(QAS_TIPOPROCESO=="stor"){
                                                            this.attr("readOnly",true);
                                                            }
                                                        </script>
                                                    </div>
                                                </td>
                                                <td align="left"></td>
                                                <td align="left" width="45%" class="label"></td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td></td>
                                                <td height="25" align="left">Nro. Documento</td>
                                                <td align="left" width="17%" class="label">
                                                    <div dojoType="dijit.form.ValidationTextBox"
                                                         id="objDD.strNroDocumento"
                                                         jsId="objDD.strNroDocumento"
                                                         name="objDD.strNroDocumento"
                                                         value="<s:property value='objDD.strNroDocumento' />"
                                                         invalidMessage="Ingrese un Nro de Documento" regExp=".{1,40}"
                                                         required="true" trim="true" />
                                                </td>
                                                <td align="left"></td>
                                                <td align="left" width="45%" class="label"></td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td></td>
                                                <td height="25" align="left">Nro. Folios</td>
                                                <td align="left" width="17%" class="label">
                                                    <div dojoType="dijit.form.ValidationTextBox"
                                                         id="objDD.iNroFolios" jsId="objDD.iNroFolios"
                                                         name="objDD.iNroFolios"
                                                         value="<s:property value='objDD.iNroFolios' />"
                                                         invalidMessage="Ingrese Nro de Folios" regExp="\d{1,6}"
                                                         required="true" trim="true" />
                                                    <!--                                       <s:textfield name="objDD.iNroFolios" cssClass="cajaMontoTotal" size="20" />-->
                                                </td>
                                                <td align="left"></td>
                                                <td align="left" width="45%" class="label"></td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td></td>
                                                <td height="25" align="left">Asunto</td>
                                                <td colspan="3" align="left" class="label">
                                                    <div dojoType="dijit.form.ValidationTextBox"
                                                         id="objDD.strAsunto"
                                                         jsId="objDD.strAsunto"
                                                         name="objDD.strAsunto"
                                                         value="<s:property value='objDD.strAsunto' />"
                                                         invalidMessage="Ingrese un Asunto" regExp=".{1,300}"
                                                         required="true" style="width: 300px" trim="true">
                                                        <script type="dojo/method" event="onBlur">
               				      if (dijit.byId("objDD.iIdExpediente").attr("value") == "") {
                   				     dijit.byId("objDD.sSumilla").attr("value", this.attr("value"));
                 				    }
                                                        </script>
                                                    </div>
                                                    <!--                                       <s:textfield key="objDD.strAsunto" cssClass="cajaMontoTotal" size="60" />-->
                                                </td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td></td>
                                                <td height="25" align="left">Fecha Documento</td>
                                                <td colspan="3" align="left" class="label">
                                                    <div dojoType="dijit.form.DateTextBox" id="objDD.strFechaDocumento" jsId="objDD.strFechaDocumento" name="objDD.strFechaDocumento" constraints="{datePattern:'dd/MM/yyyy'}" invalidMessage="Ingrese fecha de Documento dd/MM/yyyy" required="true" trim="true">
                                                        <script type="dojo/method" event="postCreate">
                                                            var objFecha="<s:property value='objDD.strFechaDocumento' />";
                                                            var ano=objFecha.substring(6,10);
                                                            var mes=objFecha.substring(3,5);
                                                            var dia=objFecha.substring(0,2);
                                                            this.attr("value",new Date(ano,parseInt(mes-1).toString(),parseInt(dia).toString()));
                                                        </script>
                                                    </div>
                                                </td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td></td>
                                                <td height="25" align="left">N&uacute;mero de Caja</td>
                                                <td class="label">
                                                    <div dojoType="dijit.form.ValidationTextBox"
                                                         id="objDD.strNroCaja" jsId="objDD.strNroCaja"
                                                         name="objDD.strNroCaja"
                                                         value="<s:property value='objDD.strNroCaja' />"
                                                         invalidMessage="Ingrese Nro de Folios" regExp="\d{1,10}"
                                                         required="false" trim="true" />
                                                    <!--                                       <s:textfield name="objDD.strNroCaja" cssClass="cajaMontoTotal" size="10" maxlength="10" />-->
                                                </td>
                                                <td colspan="3"></td>
                                            </tr>
                                            <tr>
                                                <td colspan="6" height="15">
                                                    <hr />
                                                </td>
                                            </tr>
                                            <tr>
                                                <td colspan="6" height="15" class="label">CLIENTE</td>
                                            </tr>
                                            <tr>
                                                <td></td>
                                                <td height="25" align="left">Tipo Doc Identidad</td>
                                                <td align="left" width="17%" class="label" colspan="3">
                                                    <input dojoType="dijit.form.RadioButton" type="radio" id="sTipoIdentificacionRUC" jsId="sTipoIdentificacionRUC" name="sTipoIdentificacion" value="RUC" readonly="readonly"/>RUC
                                                    <input dojoType="dijit.form.RadioButton" type="radio" id="sTipoIdentificacionDNI" jsId="sTipoIdentificacionDNI" name="sTipoIdentificacion" value="DNI" readonly="readonly"/>DNI
                                                    <input dojoType="dijit.form.RadioButton" type="radio" id="sTipoIdentificacionOtros" jsId="sTipoIdentificacionOtros" name="sTipoIdentificacion" value="Otro" readonly="readonly"/>Otro
                                                    <input dojoType="dijit.form.TextBox" type="text" id="objDD.iIdCliente" jsId="objDD.iIdCliente" name="objDD.iIdCliente" style="display:none;" />
                                                    <span id="tbNuevoCliente" style="margin-left:30px;">
                                                        <button dojoType="dijit.form.Button"
                                                                type="button"
                                                                id="btnBusquedaCliente"
                                                                jsId="btnBusquedaCliente"
                                                                iconClass="siged16 sigedSearch16"
                                                                onClick="showGrdBusquedaCliente"
                                                                showLabel="true">Buscar Cliente</button>
                                                        <button dojoType="dijit.form.Button" type="button" id="btnNuevoCliente" jsId="btnNuevoCliente" iconClass="siged16 sigedNew16" onClick="showRegistroClienteQAS" showLabel="true">Nuevo Cliente</button>
                                                    </span>
                                                </td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td></td>
                                                <td height="25" align="left">Nro Doc Identidad</td>
                                                <td align="left" width="17%" class="label">
                                                    <div id="fsNroIdentificacion" name="fsNroIdentificacion" />
            <!--                                       <div dojoType="dijit.form.FilteringSelect" id="objDD.strNroIdentificacion" jsId="objDD.strNroIdentificacion" store="strCliente" searchAttr="label" name="objDD.strNroIdentificacion" required="false" style="width:500px;" value="<s:property value='objDD.strNroIdentificacion' />" onChange="UpdateInfoCliente"  hasDownArrow="false" queryExpr ="*${0}*"></div>-->
                                                </td>
                                                <td align="left"></td>
                                                <td align="left" width="45%" class="label"></td>
                                                <td></td>
                                            </tr>
                                            <tbody id="tbDataRUC" >
                                                <tr>
                                                    <td></td>
                                                    <td height="25" align="left">Nombre/Raz&oacute;n Social</td>
                                                    <td colspan="3" align="left" class="label">
                                                        <div dojoType="dijit.form.TextBox" id="objDocumento.expediente.cliente.razonSocial" jsId="objDocumento.expediente.cliente.razonSocial" name="objDocumento.expediente.cliente.razonSocial" style="width:300px" trim="true"
                                                             value="<s:property value='documento.expediente.cliente.razonSocial'/>"/>
                                                    </td>
                                                    <td></td>
                                                </tr>
                                                <tr id="representantelegal">
                                                    <td></td>
                                                    <td height="25" align="left">Representante Legal</td>
                                                    <td colspan="3" align="left" class="label">
                                                        <div dojoType="dijit.form.TextBox" id="objDocumento.expediente.cliente.representanteLegal" jsId="objDocumento.expediente.cliente.representanteLegal" name="objDocumento.expediente.cliente.representanteLegal" style="width:300px" trim="true"
                                                             value="<s:property value='documento.expediente.cliente.representanteLegal'/>"/>
                                                    </td>
                                                    <td></td>
                                                </tr>
                                            </tbody>
                                            <tbody id="tbDataDNI" >
                                                <tr>
                                                    <td></td>
                                                    <td height="25" align="left">Nombres</td>
                                                    <td colspan="3" align="left" class="label">
                                                        <div dojoType="dijit.form.TextBox" id="objDocumento.expediente.cliente.nombres" jsId="objDocumento.expediente.cliente.nombres" name="objDocumento.expediente.cliente.nombres" style="width:300px" trim="true"
                                                             value="<s:property value='documento.expediente.cliente.nombres'/>"/>
                                                    </td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td height="25" align="left">Apellido Paterno</td>
                                                    <td colspan="3" align="left" class="label">
                                                        <div dojoType="dijit.form.TextBox" id="objDocumento.expediente.cliente.apellidoPaterno" jsId="objDocumento.expediente.cliente.apellidoPaterno" name="objDocumento.expediente.cliente.apellidoPaterno" style="width:300px" trim="true"
                                                             value="<s:property value='documento.expediente.cliente.apellidoPaterno'/>"/>
                                                    </td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td height="25" align="left">Apellido Materno</td>
                                                    <td colspan="3" align="left" class="label">
                                                        <div dojoType="dijit.form.TextBox" id="objDocumento.expediente.cliente.apellidoMaterno" jsId="objDocumento.expediente.cliente.apellidoMaterno" name="objDocumento.expediente.cliente.apellidoMaterno" style="width:300px" trim="true"
                                                             value="<s:property value='documento.expediente.cliente.apellidoMaterno'/>"/>
                                                    </td>
                                                    <td></td>
                                                </tr>
                                            </tbody>
                                            <tbody id="tbDataCliente" >
                                                <tr>
                                                    <td></td>
                                                    <td height="25" align="left">Direcci&oacute;n</td>
                                                    <td colspan="3" align="left" class="label">
                                                        <div dojoType="dijit.form.TextBox" id="objDocumento.expediente.cliente.direccionPrincipal" jsId="objDocumento.expediente.cliente.direccionPrincipal" name="objDocumento.expediente.cliente.direccionPrincipal" style="width:300px" trim="true"
                                                             value="<s:property value='documento.expediente.cliente.direccionPrincipal' />"/>
                                                    </td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td height="25" align="left">Ubigeo</td>
                                                    <td colspan="3" align="left" class="label">
                                                        <span>Departamento
                                                            <div id="objDocumento.expediente.cliente.ubigeoPrincipal.provincia.departamento.iddepartamento"></div>
                                                        </span>
                                                        <span>Provincia
                                                            <div id="objDocumento.expediente.cliente.ubigeoPrincipal.provincia.idprovincia"></div>
                                                        </span>
                                                        <span>Distrito
                                                            <div id="objDocumento.expediente.cliente.ubigeoPrincipal.iddistrito"></div>
                                                        </span>
                                                    </td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td height="25" align="left">Otra Direcci&oacute;n</td>
                                                    <td colspan="3" align="left" class="label">
                                                        <div dojoType="dijit.form.TextBox" id="objDocumento.expediente.cliente.direccionalternativa" jsId="objDocumento.expediente.cliente.direccionalternativa" name="objDocumento.expediente.cliente.direccionalternativa" style="width:300px" trim="true"
                                                             value="<s:property value='documento.expediente.cliente.direccionalternativa' />"></div>
                                                    </td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td height="25" align="left">Ubigeo</td>
                                                    <td colspan="3" align="left" class="label">
                                                        <span>Departamento
                                                            <div id="objDocumento.expediente.cliente.ubigeoAlternativo.provincia.departamento.iddepartamento"></div>
                                                        </span>
                                                        <span>Provincia
                                                            <div id="objDocumento.expediente.cliente.ubigeoAlternativo.provincia.idprovincia"></div>
                                                        </span>
                                                        <span>Distrito
                                                            <div id="objDocumento.expediente.cliente.ubigeoAlternativo.iddistrito"></div>
                                                        </span>
                                                    </td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td height="25" align="left">Teléfono</td>
                                                    <td colspan="3" align="left" class="label">
                                                        <div dojoType="dijit.form.TextBox" id="objDocumento.expediente.cliente.telefono" jsId="objDocumento.expediente.cliente.telefono" name="objDocumento.expediente.cliente.telefono" style="width:300px" trim="true"
                                                             value="<s:property value='documento.expediente.cliente.telefono' />"></div>
                                                    </td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td height="25" align="left">Correo Electrónico</td>
                                                    <td colspan="3" align="left" class="label">
                                                        <div dojoType="dijit.form.TextBox" id="objDocumento.expediente.cliente.correo" jsId="objDocumento.expediente.cliente.correo" name="objDocumento.expediente.cliente.correo" style="width:300px" trim="true"
                                                             value="<s:property value='documento.expediente.cliente.correo' />"></div>
                                                    </td>
                                                    <td></td>
                                                </tr>
                                            </tbody>
                                            <s:if test="objDD.strAbreviado == 'stor' && objDD.cPrincipal == 'S'">
                                                <tr>
                                                    <td colspan="6" height="15">
                                                        <hr />
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td colspan="6" height="15" class="label">CONCESIONARIO</td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td height="16" align="left">RUC</td>
                                                    <td colspan="3" class="label">
                                                        <div dojoType="dijit.form.FilteringSelect" id="objDD.strRUC" jsId="objDD.strRUC" name="objDD.strRUC" idAttr="id" labelAttr="label" searchAttr="label" store="strConcesionario" value="<s:property value='objDD.strRUC' />" onChange="UpdateInfoConcesionario"></div>
                                                    </td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td height="16" align="left">Razón Social</td>
                                                    <td colspan="3" class="label">
                                                        <div dojoType="dijit.form.TextBox" id="objDD.iIdCorrentista" jsId="objDD.iIdCorrentista" name="objDD.iIdCorrentista" readOnly="true" style="width:300px;display:none;" trim="true" value="<s:property value='objDD.iIdCorrentista' />"></div>
                                                        <div dojoType="dijit.form.TextBox" id="objDD.strCorrentista" jsId="objDD.strCorrentista" name="objDD.strCorrentista" readOnly="true" style="width:300px" trim="true" value="<s:property value='objDD.strCorrentista' />"></div>
                                                    </td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td height="16" align="left">Dirección</td>
                                                    <td colspan="3" class="label">
                                                        <div dojoType="dijit.form.TextBox" id="objDD.strDireccionConcesionario" jsId="objDD.strDireccionConcesionario" name="objDD.strDireccionConcesionario" readOnly="true" style="width:300px" trim="true" value="<s:property value='objDD.strDireccionConcesionario' />"></div>
                                                    </td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td height="16" align="left">Correo Electrónico</td>
                                                    <td colspan="3" class="label">
                                                        <div dojoType="dijit.form.TextBox" id="objDD.strCorreoConcesionario" jsId="objDD.strCorreoConcesionario" name="objDD.strCorreoConcesionario" readOnly="true" style="width:300px" trim="true" value="<s:property value='objDD.strCorreoConcesionario' />"></div>
                                                    </td>
                                                    <td></td>
                                                </tr>
                                                <tr>
                                                    <td colspan="6">
                                                        <hr />
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td colspan="6">
                                                        <p class="label">DATOS ADICIONALES</p>
                                                        <s:if test="suministros!=null && suministros.size()>0">
                                                            <fieldset>
                                                                <legend>Lista de Suministros</legend>
                                                                <ul>
                                                                    <s:iterator value="suministros">
                                                                        <li class="suministro">
                                                                            <input type="hidden" name="idSuministros" value="<s:property value='idsuministro' />" />
                                                                            <span><s:property value="nrosuministro" /></span>
                                                                        </li>
                                                                    </s:iterator>
                                                                </ul>
                                                            </fieldset>
                                                        </s:if>
                                                        <s:if test="submotivos!=null && submotivos.size()>0">
                                                            <fieldset>
                                                                <legend>Lista de Motivos</legend>
                                                                <div class="fila">
                                                                    <strong class="motivo">Motivo(s)</strong>
                                                                    <strong class="submotivo">Submotivo(s)</strong>
                                                                </div>
                                                                <s:iterator value="submotivos">
                                                                    <div class="fila">
                                                                        <span class="motivo"><s:property value="motivo.descripcion" /></span>
                                                                        <span class="submotivo"><s:property value="descripcion"/></span>
                                                                        <input type="hidden" name="idSubmotivos" value="<s:property value='idsubmotivo' />" />
                                                                        <img src="images/eliminar.gif" alt="Eliminar" class="eliminar" title="Eliminar" />
                                                                    </div>
                                                                </s:iterator>
                                                            </fieldset>
                                                        </s:if>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td class="titulo">Monto de Reclamo (S/.)</td>
                                                    <td>
                                                        <s:textfield name="objDD.strMontoReclamo" />
                                                    </td>
                                                    <td colspan="2"></td>
                                                </tr>
                                            </s:if>
                                            <tr>
                                                <td></td>
                                                <td height="16" align="left" title="Observación de Mesa de Partes">Observación M.P</td>
                                                <td colspan="3" class="label">
                                                    <div dojoType="dijit.form.TextBox" id="objDD.strObservacionDoc" jsId="objDD.strObservacionDoc"
                                                         name="objDD.strObservacionDoc" readOnly="true" style="width:300px" trim="true"
                                                         value="<s:property value='objDD.observacionDocumento' />"></div>
                                                </td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td></td>
                                                <td height="16" align="left" title="Observación del digitalizador">Observación DIG</td>
                                                <td colspan="3" class="label">
                                                    <div dojoType="dijit.form.TextBox" id="objDD.observacionDigitalizador" jsId="objDD.observacionDigitalizador"
                                                         name="objDD.observacionDigitalizador" readOnly="true" style="width:300px"
                                                         value="<s:property value='objDD.observacionDigitalizador' />"></div>
                                                </td>
                                                <td></td>
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
                    <tr>
                        <td width="1%">&nbsp;</td>
                        <td width="99%" colspan="5" align="left">
                            <img src="images/aprobar.png" style="cursor: pointer;" alt="Aprobar" class="aprobar" />
                            <img src="images/rechazar.png" style="cursor: pointer;" alt="Rechazar" class="rechazar" />
                        </td>
                    </tr>
                </table>
            </s:form>
            <%@ include file="/pages/tramite/registroCliente.jsp"%>
        </s:if>
        <s:else>
            <span>El documento est&aacute; siendo procesado, por favor espere</span>
            <img src="images/cargando.gif" alt="" />
            <a href="javascript:showGridInbox(TIPO_GRID_QAS_DIGITALIZADOS);">Recargar</a>
        </s:else>
    </body>
</html>