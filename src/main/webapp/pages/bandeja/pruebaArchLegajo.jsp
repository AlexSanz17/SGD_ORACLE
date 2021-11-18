<?xml version="1.0" encoding="UTF-8" ?>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es">
    <head>
      <title>Documentos</title>		
      <script type="text/javascript">
         var myidGrid = "<s:property value='idGrid' />";
         var myidLegajo = ("<s:property value='objDD.iIdLegajo' />"==""?0:"<s:property value='objDD.iIdLegajo' />");
         var mydocumentoStor = "<s:property value='documentoStor' />";
         var myidDocumento = ("<s:property value='objDD.iIdDocumento' />"==""?null:"<s:property value='objDD.iIdDocumento' />");
         var opcionMenu = "<s:property value='objDD.opcionMenu' />";
         var origen = '<s:property value="origenDetalleDoc"/>';
         
         var idLegajoDinamico = ("<s:property value='objDD.iIdLegajoOrigen' />"==""?0:"<s:property value='objDD.iIdLegajoOrigen' />");
         var idDocumentoDinamico = 0;
      </script>
      <script type="text/javascript" src="js/documentosLegajo.js"></script>
      <script type="text/javascript" src="js/siged/busquedaDocumento.js"></script>
      <script type="text/javascript" src="js/destruirObjetos.js"></script> 
      <script type="text/javascript">  
             destroyWidgetsFromBusquedaDocumento();
             destroyWidgetsFromLegajo();
             
              function actualizarExpediente(){
                 dijit.byId("dlgCrearLegajo").show();
                 document.getElementById("accionLegajo").value = "A";
                 dijit.byId("sAsuntoLegajo").setValue("<s:property value='objDD.legajo.asunto' escape='false'/>");
                 dijit.byId("sObservacionLegajo").setValue("<s:property value='objDD.legajo.observacion' escape='false'/>");
                 dijit.byId("sTipoLegajo").attr("value", "<s:property value='objDD.legajo.tipoLegajo.idTipo' />");
                 dijit.byId("sNota1Legajo").setValue("<s:property value='objDD.legajo.nota1' escape='false'/>");
                 dijit.byId("sNota2Legajo").setValue("<s:property value='objDD.legajo.nota2' escape='false'/>");
                 dijit.byId("sNota3Legajo").setValue("<s:property value='objDD.legajo.nota3' escape='false'/>");
                 dijit.byId("sNota4Legajo").setValue("<s:property value='objDD.legajo.nota4' escape='false'/>");
                 
                 if ("<s:property value='objDD.legajo.tipoLegajo.idTipo' />"== "1"){
                    document.getElementById("tbTipoProcedimiento").style.display = 'none';
                    document.getElementById("tbTipoMetodo").style.display = 'none';
                    document.getElementById("tbNroLegajo").style.display = 'none';
                 }
                 
                 if ("<s:property value='objDD.legajo.tipoLegajo.idTipo' />"== "2"){
                     var nroLegajo = "<s:property value='objDD.legajo.nroLegajo' />";
                     dijit.byId("sTipoProcedimiento").attr("value", "<s:property value='objDD.legajo.idProcedimiento' />");
                     document.getElementById("tbNroLegajo").style.display = '';
                     dijit.byId("sNroTipoLegajo").attr("value",  nroLegajo.substring(4,nroLegajo.length-3) + "-" + nroLegajo.substring(0,4));
                 }
                 
                 if ("<s:property value='objDD.legajo.tipoLegajo.idTipo' />"== "3"){
                     var nroLegajo = "<s:property value='objDD.legajo.nroLegajo' />";
                     dijit.byId("sTipoMetodo").attr("value", "<s:property value='objDD.legajo.idMetodo'/>");
                     document.getElementById("tbNroLegajo").style.display = '';
                     dijit.byId("sNroTipoLegajo").attr("value",  nroLegajo.substring(4,nroLegajo.length-3) + "-" + nroLegajo.substring(0,4));
                 }
             }
             
             function agregarDocumentoExpediente(){   
                if (idLegajoDinamico == 0 || idLegajoDinamico == "0" || idLegajoDinamico == null){
                   alert("Debe seleccionar el expediente donde desea agregar el documento"); 
                }else{
                   document.getElementById("accionBusquedaDocumento").value = "E";  
                   showBusquedaDocumento();
               }   
             }
             
             function quitarDocumentoExpediente(){
                if  (idLegajoDinamico == 0 || idLegajoDinamico == "0" || idDocumentoDinamico == 0 || idDocumentoDinamico == ""){
                    alert("Debe seleccionar el documento que desea quitar del expediente");
                    return;
                }else{
                    var service = new dojo.rpc.JsonService("SMDAction.action");
                    var defered = service.quitarDocumentoToExpediente(parseInt(idLegajoDinamico), parseInt(idDocumentoDinamico));
                    defered.addCallback(function(respuesta){ 

                       if (respuesta == "1"){
                          alert("El documento ha sido quitado satisfactoriamente del expediente"); 
                       }

                       if (respuesta == "0"){
                         alert("Ha ocurrido un error inesperado. Vuelva a intentarlo en un momento");
                         return;
                       }

                       if (respuesta == "2"){
                         alert("El documento ya no se encuentra en el expediente.");
                         return;
                       }
                       
                       if (respuesta == "3"){
                         alert("No puede quitar un documento, el expediente ya se encuentra cerrado");
                         return;
                       }

                       if (sTipoGridActual== TIPO_GRID_RECIBIDOS){
                           viewDetailPersonalizado(myidDocumento);
                       }    
                       if (sTipoGridActual==TIPO_GRID_EXPEDIENTES){
                           viewDetailPersonalizado(myidDocumento);
                       }    
                       if (sTipoGridActual==TIPO_GRID_FIRMAR){
                           viewDetailPersonalizado(myidDocumento);
                       }    
                       if (sTipoGridActual==TIPO_GRID_MI_LEGAJO){
                           viewDetailPersonalizado(myidLegajo);
                       }
                       if (sTipoGridActual==TIPO_GRID_LEGAJO_COMPARTIDO){
                           viewDetailPersonalizado(myidLegajo);
                       }
                   });
               }   
             }
             
             function cerrarExpediente(){
                 if ((idDocumentoDinamico==0 || idDocumentoDinamico=="" || idDocumentoDinamico==null) && idLegajoDinamico!=0){
                    var service = new dojo.rpc.JsonService("SMDAction.action");
                    var defered = service.cerrarLegajo(parseInt(idLegajoDinamico));
                    defered.addCallback(function(respuesta){ 

                       if (respuesta == "1"){
                          alert("El expediente ha sido cerrado satisfactoriamente");
                       }

                       if (respuesta == "0"){
                         alert("Ha ocurrido un error inesperado. Vuelva a intentarlo en un momento");
                         return;
                       }

                       if (respuesta == "3"){
                         alert("El expediente ya se encuentra cerrado.");
                         return;
                       }

                       if (sTipoGridActual== TIPO_GRID_RECIBIDOS){
                           viewDetailPersonalizado(myidDocumento);
                       }    
                       if (sTipoGridActual==TIPO_GRID_EXPEDIENTES){
                           viewDetailPersonalizado(myidDocumento);
                       }    
                       if (sTipoGridActual==TIPO_GRID_FIRMAR){
                           viewDetailPersonalizado(myidDocumento);
                       }    
                       if (sTipoGridActual==TIPO_GRID_MI_LEGAJO){
                           viewDetailPersonalizado(myidLegajo);
                       }
                       if (sTipoGridActual==TIPO_GRID_LEGAJO_COMPARTIDO){
                           viewDetailPersonalizado(myidLegajo);
                       }
                   });
                 }else{
                     alert("Para cerrar un expediente debe seleccionarlo previamente");
                     return;
                 }
             }
      </script>     
    </head>
	<body class="soria">
            <s:if test='idGrid!=null && (idGrid == 30 || idGrid == 31)'>
                <s:if test='objDD.legajo.estado.equals("A")'>
                    <div dojoType="dijit.Toolbar">
                        <div dojoType="dijit.form.Button" onClick="agregarDocumentoExpediente()" iconClass="siged16 iconoAgregar">Agregar Documento</div>
                        <div dojoType="dijit.form.Button" onClick="quitarDocumentoExpediente()" iconClass="siged16 iconoRemover">Quitar Documento</div>
                        <div dojoType="dijit.form.Button" onClick="cerrarExpediente()" iconClass="siged16 iconoCerrado16">Cerrar Expediente</div>
                        <div dojoType="dijit.form.Button" onClick="actualizarExpediente()" iconClass="dijitEditorIcon dijitEditorIconSave">Modificar</div>
                    </div>
                </s:if>
            </s:if>    
            <s:if test='idGrid!=null && (idGrid == 0 || idGrid == 6 || idGrid == 26) '>
                <div dojoType="dijit.Toolbar">
                    <div dojoType="dijit.form.Button" onClick="agregarDocumentoExpediente()" iconClass="siged16 iconoAgregar">Agregar Documento</div>
                    <div dojoType="dijit.form.Button" onClick="quitarDocumentoExpediente()" iconClass="siged16 iconoRemover">Quitar Documento</div>
                    <div dojoType="dijit.form.Button" onClick="cerrarExpediente()" iconClass="siged16 iconoCerrado16">Cerrar Expediente</div>
                </div>    
             </s:if> 
            
	    <div id="divTreeLegajo<s:property value='idGrid' />" class="arbol"></div>
	    <div id="displayIdLegajo<s:property value='idGrid' />" class="detalleArbol"></div>
            <%@include file="/detalle/editarExpediente.jsp" %>      
            <%@ include file="/pages/tramite/busquedaDocumento.jsp" %> 
	</body>
</html>