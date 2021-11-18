<?xml version="1.0" encoding="UTF-8" ?>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@page import="com.btg.ositran.siged.domain.Documento" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Lista de documentos </title>
<META HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE" >
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
            
            
<script>
   
	//Definiendo clase
	function DLGClass(){
		this.names=null;		
	}
	
	DLGClass.instance = null;
	
	//creando singleton
	DLGClass.getInstance = function() {
	        if (DLGClass.instance == null) {
	            DLGClass.instance = new dijit.Dialog({
					draggable:"true",
					style:"height:120px;width:400px;display:none;",
				        title:"Editar Firmante",
				        onClose: dojo.hitch(this, function(){
				           this.destroyRecursive();
				        })									        			    
		    });
	        }
	        return DLGClass.instance;
	};

	// fin de la clase	
</script>
<%
    String indexTreeExp = (String)request.getAttribute("indexTreeExp");
%>
<script>
	var iddoc_1="<s:property value='objDocumentoReferencia.idDocumento'/>";
        var  objDLGClass=DLGClass.getInstance();
	
	function abrirHojaRutaTE(){
            var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=900, height=700, top=50, left=200";
               var pagina = "ReporteAPN_verHojaRuta.action?idDocumento=" + iddoc_1;
               window.open(pagina,"",opciones);
        };
        
        function abrirHojaFirmaTE(){
            var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=900, height=700, top=50, left=200";
               var pagina = "ReporteAPN_verHojaFirma.action?idDocumento=" + iddoc_1;
               window.open(pagina,"",opciones);
        };
        
        function muestra_oculta(id, idArchivo, url, objectId) {
            if (document.getElementById(id)) { //se obtiene el id
               var el = document.getElementById(id); //se define la variable "el" igual a nuestro div
               el.style.display = (el.style.display == 'none') ? '' : 'none'; //damos un atributo display:none que oculta el div
            }
            if(url != null){
               var fecha = new Date();
               window.open("<%=request.getContextPath()%>/verDocumento.png?idArchivo=" +idArchivo + "&url=" + url + "&objectId="+ objectId  + "&accion=abrirDocumento&fecha=" + fecha.getTime());
            }
        }
      

	
</script>
<script type="text/javascript">
            dojo.require("dijit.Toolbar");
            dojo.require("dijit.form.Button");
            var index="<s:property value='objDocumentoReferencia.idDocumento'/>";
            var divtoolbar_1 = "divtoolbar_1"+<%=indexTreeExp%>;
            var toolbar;
            
            dojo.addOnLoad(function() {
               if(dojo.byId(divtoolbar_1)){
                	toolbar = new dijit.Toolbar({},
                		divtoolbar_1);
                       
                    
                    <s:if test="#session._RECURSO.HojaRuta">   
                    var buttonHRu = new dijit.form.Button({
                        label: "Hoja de Ruta",
                        showLabel: true,
                        iconClass: "siged16 iconoHojaRuta",
                        onClick:abrirHojaRutaTE
                    });
                    
                    var buttonHF = new dijit.form.Button({
                        label: "Hoja de Firma",
                        showLabel: true,
                        iconClass: "siged16 iconoHojaRuta",
                        onClick:abrirHojaFirmaTE
                    });
                    </s:if>
                    
                    toolbar.addChild(buttonHRu);
                    toolbar.addChild(buttonHF);
               }
                
            });
            
        </script>
</head>
<body>                      
	
        <span id="divtoolbar_1<%=indexTreeExp%>">
        </span>
  
		<table width="100%" border="0" align="left">	   
			<tr>					
				<td><s:hidden name="objDocumentoReferencia.idDocumento" id="idDocumentoMostrado" /></td>
            <td>
               <table width="100%" border="0" align="left">
                  <tr>
                     <td height="13" colspan="2" class="header">Informaci&oacute;n del Documento</td>
                  </tr>
                  <tr>
                     <td height="13" colspan="2" class="header" ></td>
                  </tr>
                  <tr>
                     <td colspan="2">
                        <table cellpadding="2" cellspacing="1" border="1" class="tableForm" align="left" width="100%">
                          <tr align="left">
                              <td class="header" width="50%" >Nro Trámite</td>
                              <td><s:property value="objDocumentoReferencia.ID_CODIGO" /></td>
                           </tr> 
                            
                           <tr align="left">
                              <td class="header" width="50%" >Asunto del Documento</td>
                              <td><s:property value="objDocumentoReferencia.asunto" /></td>
                           </tr>
                           <tr align="left">
                              <td class="header" width="50%" >Tipo de Documento</td>
                              <td><s:property value="objDocumentoReferencia.tipoDocumento.nombre" /></td>
                           </tr>
                           <tr align="left">
                              <td class="header" >Documento</td>
                              <td id="nombreDocumento"><s:property value="objDocumentoReferencia.tipoDocumento.nombre" />-<s:property value="objDocumentoReferencia.numeroDocumento" /></td>
                           </tr >
                           <tr align="left">
                              <td class="header" >Estado</td>
                              <td>
                              	<s:if test="objDocumentoReferencia.estado == @org.ositran.utils.Constantes@ESTADO_ACTIVO">
                              		Registrado
                              	</s:if>
                              	<s:elseif test="objDocumentoReferencia.estado == @org.ositran.utils.Constantes@ESTADO_CERRADO">
                              		Archivado
                              	</s:elseif>
                                <s:elseif test="objDocumentoReferencia.estado == @org.ositran.utils.Constantes@ESTADO_PENDIENTE">
                              		Pendiente
                              	</s:elseif>
                                <s:elseif test="objDocumentoReferencia.estado == @org.ositran.utils.Constantes@ESTADO_ATENDER">
                              		Atendido
                              	</s:elseif>         
                                        
                              	<s:elseif test="objDocumentoReferencia.estado == @org.ositran.utils.Constantes@ESTADO_ANULADO">
                              		Anulado
                              	</s:elseif>
                              	<s:else>
                              		Inactivo
                              	</s:else>
                              </td>
                           </tr >
            
                           <tr align="left">
                              <td class="header" >Creador</td>
                              <td ><s:property value="creador" /></td>
                           </tr>
                           <tr align="left">
                              <td class="header">Fecha de Creaci&oacute;n</td>
                              <td ><s:date name="objDocumentoReferencia.fechaCreacion" format="dd/MM/yyyy HH:mm" /></td>
                           </tr>
                          
                        </table>
                     </td>
                  </tr>
                  

                  <tr>
                     <td height="13" colspan="2" class="header" ></td>
                  </tr>
                  <tr>
                     <td height="13" colspan="2" class="header" ></td>
                  </tr>
                
                         
                        </table>
                     </td>
                  </tr>
                 
                  <tr align="center">
                     <td colspan="2"  align="center">&nbsp;</td>
                  </tr>
                  <tr>
                     <td colspan="2" align="center">
                     </td>
                  </tr>
				<s:if test="versionamiento.size() > 0 && mostrarAdjuntos">
                  <tr>
                     <td height="13" colspan="2" class="header">
                        
                           Adjuntos
                        
                     </td>
                  </tr>
                  <tr>
                     <td height="13" colspan="2" class="header" ></td>
                  </tr>

                  
                     <s:iterator value="versionamiento" >
                         
                        <tr>
                           <td height="13" colspan="2" class="header" >
                              <a onclick="muestra_oculta('<s:property value="archivo.nombreArchivo" />', '<s:property value='archivo.idArchivo' />','<s:property value='archivo.rutaAlfresco' />', '<s:property value='archivo.objectId' />')" > <s:property value="archivo.nombreArchivo" />  </a>
                           </td>
                        </tr>

                        <tr  style="display:none;"  align="center" id="<s:property value="archivo.nombreArchivo" />"   >
                           <td colspan="2"  align="center">&nbsp;

                           </td>
                        </tr>
                        <tr align="center">
                           <td colspan="2"  align="center">&nbsp;</td>
                        </tr>
                     </s:iterator>
                  </s:if>
                  <s:if test="noHayAlfresco">
                  	<strong style="color: red;">No se puede mostrar el versionamiento debido a que no no se puede acceder al administrador de contenidos.</strong>
                  </s:if>
                 
               </table>
            </td>
            <td >
            </td>
         </tr>
      </table>
 </body>
</html>     		