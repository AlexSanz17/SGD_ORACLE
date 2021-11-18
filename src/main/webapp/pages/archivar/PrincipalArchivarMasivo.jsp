<?xml version="1.0" encoding="UTF-8"?>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es">
    <head>
        <title>Trazabilidad del Expediente</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8; no-cache" />
        <META http-equiv=Pragma content=no-cache/>
        <Meta http-equiv="expires" content="0"/>
        <Meta http-equiv="cache-control" content="no-cache"/>
        <link rel="stylesheet" type="text/css" href="css/estilo.css" />
        <link rel="stylesheet" type="text/css" href="css/estilo.css" />
        <link rel="stylesheet" type="text/css" href="js/dojo/dijit/themes/soria/soria.css" />
        <script type="text/javascript" src="js/dojo/dojo/dojo.js" djConfig="parseOnLoad:true, isDebug: false"></script>
        <script type="text/javascript" src="js/dojo/dijit/dijit.js" djConfig="parseOnLoad:true, isDebug: false"></script>
        <script type="text/javascript" src="js/jquery.js"></script>
        <script type="text/javascript">
            dojo.require("dijit.form.FilteringSelect");
            dojo.require("dijit.form.Button");
            dojo.require("dijit.form.Form");
            dojo.require("dojo.data.ItemFileReadStore");
            dojo.require("dijit.form.DateTextBox");
            dojo.require("dijit.form.ValidationTextBox");
            var agregarDestinatario = function(){
                agregar();
            }

            dojo.addOnLoad(function(){                
                new dijit.form.FilteringSelect({
                    id				: "iddestinatarios",
                    jsId				: "iddestinatarios",
                    name				: "iddestinatarios",
                    store			: new dojo.data.ItemFileReadStore({url: "derivarCCMasivo.action"}),
                    searchAttr		: "label",
                    labelAttr		: "label",
                    idAttr			: "ids",
                    required		: false,
                    //                    hasDownArrow	: true,
                    highlightMatch	: "all",                    
                    onChange		: agregarDestinatario
                },"iddestinatarios");                
            });
            
            var agregar = function() {                
                var nombre = document.getElementById("iddestinatarios").value;                
                var id = dijit.byId("iddestinatarios").getValue();                
                if(nombre!=null && nombre!="" && id!=null && id!=""){
                    agregarCopia(nombre, id);
                }
            };
            function refrescar() {
                //                alert("cerrando");
                //window.opener.parent.location.href = "inicio.action?sTipoFrame=grid";
                //                window.opener.showGridInbox("0");
                window.opener.showGridInbox();
                window.opener.refreshTabDXE();
                window.close();
            }
            //            function hideDialogAM() {
            //                var dg=dijit.byId("dlgArchivamientoMasivo");
            //                var filter=dijit.byId("iddestinatarios");
            //                dg.hide();
            //                dg.destroy(false);
            //                filter.destroy(false);
            //            }
            function agregarCopia(nombre, id){
                var existe=false;
                var conCopia=document.getElementsByName("condestinatarios");
                //"#copias span input[name='conCopia']"
                $(conCopia).each(function(){
                    if($(this).val()==id)
                        existe=true;
                });
                if(!existe){
                    var copia="<span class=\"copia\">";
                    copia+="<span>"+nombre+"</span>";
                    copia+="<input type=\"hidden\" name=\"condestinatarios"+"\" value=\""+id+"\" />";
                    copia+="<a href=\"#\" class=\"quitar\"><img src=\"images/eliminar.gif\" alt=\"X\" /></a></span>";
                    $("#destinatarios").append(copia);
                    $(".quitar").click(function(){
                        $(this).parent().remove();
                    });
                }
            }
            function guardar() {
                var users = document.getElementsByName("condestinatarios").length;
                //if(users>0) {
                dojo.xhrPost({
                	form : document.getElementById("frmArchivarMasivamente"),
                	load:function(){
                		refrescar();
                	}
                });
                	//document.getElementById("frmArchivarMasivamente").submit();
                
                //                hideDialogAM();
                /*} else {
                    alert("Debe seleccionar al menos un usuario para notificar");
                }*/
            }            
        </script>
    </head>
    <body class="soria">
        <table width="100%">
            <tr align="center">
                <td colspan="2"  align="center">&nbsp;</td>
            </tr>
            <tr height="18%" >
                <td valign="center" align="center" style="FONT-WEIGHT: bold; FONT-SIZE: 10pt; COLOR: #31619c; FONT-FAMILY: verdana, arial, helvetica, sans-serif">
						Archivo masivo de documentos
                </td>
            </tr>            
        </table>
        <form action="Archivar_guardarArchivamientoMasivo.action" id="frmArchivarMasivamente">
            <table width="10%" style="align:left;margin-left: 100px;" border="0">
                <tr align="center">
                    <td colspan="2"  align="center">&nbsp;</td>
                </tr>
                <tr>
                    <td align="center" >
                        <input type="button" value="Aceptar" class="button" onclick="guardar();"/>
                    </td>
                    <td align="center" >
                        <input type="button" name="selecciona" value="Regresar" class="button" onclick="refrescar();"/>
                    </td>
                </tr>
                <tr align="center">
                    <td colspan="2"  align="center">&nbsp;</td>
                </tr>
            </table>
            <table>
                <tr>
                    <td align="left" style="padding-left: 25px">Notificar a:</td>
                    <td>
                        <input id="iddestinatarios" name="iddestinatarios"/>
                    </td>
                </tr>
                <tr>
                    <td ></td>
                    <td >
                        <div id="destinatarios" name="destinatarios"></div>
                    </td>
                </tr>
                <tr>
                    <td align="left" style="padding-left: 25px">Observación: </td>
                    <td >
                        <s:textarea id="sObservacion" name="sObservacion" cols="50" rows="10" cssClass="cajaMontoTotal" />
                    </td>
                </tr>
            </table>
            <br/>
            <center>
                <div style="overflow:auto;height:250px; width:90%">
                    <table cellpadding="1" cellspacing="1" class="tableForm" width="100%" align="center">
                        <s:if test="documentos.size() > 0">
                            <tr class="header">
                                <td width="10%" class="data">Nº Expediente</td>
                                <td width="10%" class="data">Documento</td>
                                <td width="20%" class="data">Proceso</td>
                                <td width="20%" class="data">Asunto</td>
                                <td width="20%" class="data">Cliente</td>
                                <td width="10%" class="data">Fecha acción</td>
                            </tr>
                            <s:iterator value="documentos">
                                <tr  class="altRow2" >
                                    <td align="center">
                                        <s:property value="expediente.nroexpediente" />
                                    </td>
                                    <td align="center">
                                        <s:property value="tipoDocumento.nombre" /> <s:property value="numeroDocumento" />
                                    </td>
                                    <td align="center">
                                        <s:property value="expediente.proceso.nombre" />
                                    </td>
                                    <td align="left">
                                        <s:property value="ultimoAsunto" />
                                    </td>
                                    <td align="left">
                                        <s:property value="expediente.cliente.nombreRazon" />
                                    </td>
                                    <td align="left">
                                        <s:property value="fechaAccion" />
                                    </td>
                                    <%--<td align="left">
                                        <s:property value="expediente.proceso.responsable.nombres" />
                                    </td>
                                    <td align="left">
                                        <s:property value="expediente.idpropietario.nombres" />
                                    </td>--%>
                                </tr>
                            </s:iterator>
                        </s:if>
                    </table>
                </div>
            </center>
        </form>
    </body>
</html>