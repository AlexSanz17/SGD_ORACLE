<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//ES" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Datos Expediente</title>
		<style type="text/css">
			table {
				margin-left: 5px;
				margin-top: 1px;
				margin-bottom: 1px;
			}
			.dijitText {
				width: 400px;
			}
			.dijitArea {
				width: 400px;
			}
		</style>
		<script type="text/javascript">
			function EX_Guardar(){
				if(dijit.byId("dlgProgresBar")!=null){
					dijit.byId("dlgProgresBar").show() ;
				}
				
				dojo.xhrPost({
					form : dojo.byId("EX_formulario"),
					load : function(){
						if(dijit.byId("dlgProgresBar")!=null){
							dijit.byId("dlgProgresBar").hide() ;
						}
						
						showGridInbox(sTipoGridActual);
						
						dijit.byId("dlgModificarExp").hide();
                                                dijit.byId("dlgModificarExp").destroyRecursive();
					}
				});
			}
			
			dojo.addOnLoad(function(){
				new dijit.form.FilteringSelect({
				    id             : "idserieExpediente",
				    jsId           : "idserieExpediente",
				    name           : "idserie",
				    searchAttr     : "label",
				    highlightMatch : "all",
				    style          : "width:380px; position:relative;",
				    invalidMessage : "Seleccione una serie",
				    store          : new dojo.data.ItemFileReadStore({url: "autocompletarSerie.action"}),
				    value          : "<s:property value='objDocumento.expediente.serie.idserie'/>"
				}, "idserieExpediente");
			});
		</script>
	</head>
	<body>
		<form id="EX_formulario" action="modificarExpediente.action" method="post">
			<input name="iIdExp" type="hidden" value="<s:property value='objDocumento.expediente.id'/>" />
                        <table border="0">
                            <tr>
                                <td width="150px">Serie Documental</td>
                                <td width="480px" align="left"><input id="idserieExpediente" name="idserie"/></td>
                            </tr>
			
			    <tr>
				<td width="150px">Asunto Carpeta</td>
                                <td width="480px" align="left"><input name="asuntoObservacion" class="dijitText" type="text" dojoType="dijit.form.TextBox"
                                     maxlength="300" value="<s:property value='objDocumento.expediente.asunto'/>" 
                                     style="width: 380px; position:relative; "/></td>
                            </tr>
			
			    <tr>
				<td width="150px">Observaci&oacute;n</td>
                                <td width="480px" align="left"><input name="ultimaObservacion" class="dijitText" type="text" dojoType="dijit.form.TextBox" 
                                      value="<s:property value='objDocumento.expediente.observacion'/>" 
                                      style="width: 380px; position:relative;"/><td>
                            </tr>
                            <tr><td colspan="2">&nbsp;</td></tr>
                            <tr>
                                <td align="left"> 
                                    <div dojoType="dijit.form.Button"
                                        id="EX_Guardar"
                                        iconClass="siged16 sigedSave16"
                                        onClick="EX_Guardar()"
                                        showLabel="true">Guardar</div>
                                </td>    
                            </tr>
                        </table>
		</form>
	</body>
</html>