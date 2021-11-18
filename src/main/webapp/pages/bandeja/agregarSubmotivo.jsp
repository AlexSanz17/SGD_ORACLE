<?xml version="1.0" encoding="UTF-8" ?>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="java.util.List"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <!--<meta http-equiv="Cache-Control" content="no-store" />-->
        <META http-equiv=Pragma content=no-cache>
        <Meta http-equiv="expires" content="0">
        <Meta http-equiv="cache-control" content="no-cache,
              must-revalidate">
    </head>

    <script type="text/javascript">
	var mensajeError = "<s:property value='mensajeError' />";
        var agregarSubmotivoSub = function(){
            if(dijit.byId("frmAgregarSub").validate()){
                dojo.xhrPost({
                    url: "doStor_agregarSubmotivo.action",
                    content: {
                        campoA : STOR_IDEXPEDIENTE
                    },
                    form: dojo.byId("frmAgregarSub"),
                    mimetype: "text/html",
                    load: function() {
                        service.getSubmotivosSTOR(parseInt(STOR_IDEXPEDIENTE)).addCallback(function(objJSON) {
                            dijit.byId("gridMotivoSTOR").attr("structure", lytGridMotivoSTOR);
                            dijit.byId("gridMotivoSTOR").setStore(new dojo.data.ItemFileWriteStore({
                                data : objJSON
                            }));
                        });
                        cancelarSub();
                    }
//                    handle: function(err, ioArgs){
//                        //  ioArgs es la información del XHR objeto
////                        alert('ERROR: ');
////                        console.error(); // despliega el Error
//                    }
                });
            }
        };
	
        var cancelarSub = function(){
            dijit.byId("submotivoExpediente.submotivo.motivo.idmotivo").attr("value", "");
            dijit.byId("submotivoExpediente.submotivo.idsubmotivo").attr("value", "");
            //dijit.byId("submotivoExpediente.moneda.idparametro").attr("value", "");
            dijit.byId("submotivoExpediente.strMonto").attr("value", "");
            dijit.byId("dlgAgregarSubmotivo").hide();
        };
	
        dojo.addOnLoad(function(){            
            dojo.byId("txt_tipoExpedienteSub").innerHTML = STOR_PROCESO;
            dojo.byId("txt_nroExpedienteSub").innerHTML = STOR_NROEXPEDIENTE;
            new dijit.form.FilteringSelect({
                id             	: "submotivoExpediente.submotivo.motivo.idmotivo",
                jsId           	: "submotivoExpediente.submotivo.motivo.idmotivo",
                name           	: "submotivoExpediente.submotivo.motivo.idmotivo",
                store          	: new dojo.data.ItemFileReadStore({
                    url: "listaMotivos.action?idProceso="+STOR_IDPROCESO
                }),
                searchAttr     	: "label",
                autoComplete   	: false,
                hasDownArrow   	: true,
                highlightMatch 	: "all",
                invalidMessage 	: "Seleccione un motivo",
                onChange       	: function(){
                    if(this.value != null){
                        updateStoreSTOR(this.value, 'submotivos');
                    }
                },
                required       	: true
            },"fs_MotivoSub");
		
            new dijit.form.FilteringSelect({
                id             	: "submotivoExpediente.submotivo.idsubmotivo",
                jsId           	: "submotivoExpediente.submotivo.idsubmotivo",
                name           	: "submotivoExpediente.submotivo.idsubmotivo",
                searchAttr     	: "label",
                autoComplete   	: false,
                hasDownArrow   	: true,
                highlightMatch 	: "all",
                invalidMessage 	: "Seleccione una submotivo",
                required       	: true
            },"fs_SubmotivoSub");
		
            /*new dijit.form.FilteringSelect({
                id             	: "submotivoExpediente.moneda.idparametro",
                jsId           	: "submotivoExpediente.moneda.idparametro",
                name           	: "submotivoExpediente.moneda.idparametro",
                store          	: new dojo.data.ItemFileReadStore({
                    url: "listaMonedas.action"
                }),
                searchAttr     	: "label",
                autoComplete   	: false,
                hasDownArrow   	: true,
                highlightMatch 	: "all",
                invalidMessage 	: "Seleccione una moneda",
                required       	: true
            },"fs_MonedaSub");*/
        });
    </script>
    <div dojoType="dijit.Dialog" id="dlgAgregarSubmotivo" jsId="dlgAgregarSubmotivo"
         draggable="false" style="display:none;width:300px;" title="Agregar Submotivo">
        <div dojoType="dijit.form.Form" id="frmAgregarSub" name="frmAgregarSub">
            <table style="width:100%; height:100%">
                <tr>
                    <td>Tipo de Expediente</td>
                    <td id="txt_tipoExpedienteSub"></td>
                </tr>
                <tr><td colspan="2">&nbsp;</td></tr>
                <tr>
                    <td>Nro. Expediente</td>
                    <td id="txt_nroExpedienteSub"></td>
                </tr>
                <tr><td colspan="2">&nbsp;</td></tr>
                <tr>
                    <td>Motivo de Reclamo</td>
                    <td><div id="fs_MotivoSub"></div></td>
                </tr>
                <tr><td colspan="2">&nbsp;</td></tr>
                <tr>
                    <td>Sub Motivo de Reclamo</td>
                    <td><div id="fs_SubmotivoSub"></div></td>
                </tr>
                <tr><td colspan="2">&nbsp;</td></tr>
                <tr>
                    <td>Monto (S/.)</td>
                    <td>
                        <input dojoType="dijit.form.NumberTextBox" id="submotivoExpediente.strMonto" 
                               name="submotivoExpediente.strMonto" required="false" constraints="{pattern: '#,###,###,###.00'}"/>
                    </td>
                </tr>
                <s:hidden id="submotivoExpediente.moneda.idparametro" name="submotivoExpediente.moneda.idparametro" value="57"/>
                <%--<tr><td colspan="2">&nbsp;</td></tr>
                <tr>
                    <td>Moneda</td>
                    <td><div id="fs_MonedaSub"></div></td>
                </tr>--%>
                <tr><td colspan="2">&nbsp;</td></tr>
                <tr>
                    <td><div dojoType="dijit.form.Button" onClick="agregarSubmotivoSub()">Agregar</div></td>
                    <td><div dojoType="dijit.form.Button" onClick="cancelarSub()">Cancelar</div></td>
                </tr>
            </table>
        </div>
    </div>
</html>