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
	var mensajeErrorSum = "<s:property value='mensajeError' />";
        var guardarSuministro = function(){
            if(dijit.byId("frmAgregarSum").validate()){
                dojo.xhrPost({
                    url: "doStor_agregarSuministros.action",
                    content: {
                        campoA : STOR_IDEXPEDIENTE
                    },
                    form: dojo.byId("frmAgregarSum"),
                    mimetype: "text/html",
                    load: function() {
                        service.getSuministrosSTOR(parseInt(STOR_IDEXPEDIENTE)).addCallback(function(objJSON) {
                            dijit.byId("gridSuministros").attr("structure", lytGridSuministroSTOR);
                            dijit.byId("gridSuministros").setStore(new dojo.data.ItemFileWriteStore({
                                data : objJSON
                            }));
                        });
                        cancelarSuministro();
                    }
                });
            }
        };
        //
        var cancelarSuministro = function(){
            dijit.byId("suministro.nrosuministro").attr("value", "");
            dijit.byId("dlgAgregarSuministro").hide();
        };
        //
        dojo.addOnLoad(function(){
            dojo.byId("txt_nroExpedienteSum").innerHTML = STOR_NROEXPEDIENTE;
        });
    </script>
    <div dojoType="dijit.Dialog" id="dlgAgregarSuministro" jsId="dlgAgregarSuministro"
         draggable="false" style="display:none;width:300px;" title="Agregar Suministro">
        <div dojoType="dijit.form.Form" id="frmAgregarSum" name="frmAgregarSum">
            <table style="width:100%; height:100%">
                <tr>
                    <td>Nro. Expediente</td>
                    <td id="txt_nroExpedienteSum"></td>
                </tr>
                <tr><td colspan="2">&nbsp;</td></tr>
                <tr>
                    <td>Suministro</td>
                    <td>
                        <input dojoType="dijit.form.NumberTextBox" id="suministro.nrosuministro" name="suministro.nrosuministro" maxlength="15"
                               size="10" required="false" constraints="{pattern: '######'}" />
                        <span style="font-size:9px;color:darkred;">Longitud máxima de 15 caracteres</span>
                    </td>
                </tr>
                <tr><td colspan="2">&nbsp;</td></tr>
                <tr>
                    <td><div dojoType="dijit.form.Button" onClick="guardarSuministro()">Agregar</div></td>
                    <td><div dojoType="dijit.form.Button" onClick="cancelarSuministro()">Cancelar</div></td>
                </tr>
            </table>
        </div>
    </div>
</html>