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
    Documento documento = (Documento)request.getAttribute("objDocumento");
    String indexTreeExp = (String)request.getAttribute("indexTreeExp");
%>
<script>
	var iddoc="<s:property value='objDocumento.idDocumento'/>";
	var  objDLGClass=DLGClass.getInstance();
	
	function mostrarCambiarFirmante(){
		dojo.xhrPost({
			url: "Numeracion_mostrarCambiarFirmante.action?iddoc="+iddoc,
			load: function(data){
			objDLGClass.attr("content", data);
			objDLGClass.attr("title", "Editar Firmante");
			objDLGClass.show();
		}});   			
	}
	
	function abrirHojaRutaTE(){
            var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=900, height=700, top=50, left=200";
            var pagina = "ReporteAPN_verHojaRuta.action?idDocumento=" + iddoc;
            window.open(pagina,"",opciones);
        };
        
        function abrirHojaFirmaTE(){
            var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=900, height=700, top=50, left=200";
            var pagina = "ReporteAPN_verHojaFirma.action?idDocumento=" + iddoc;
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
      
       
    function abrirVentanaSeguimientoTE() {
           var opciones = "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=no, width=650, height=500, top=50, left=200";
           var pagina ="goViewSeguimiento.action?iIdDocumento=" +iddoc;
           window.open(pagina, "", opciones);
    };
	
</script>
<script type="text/javascript">
            dojo.require("dijit.Toolbar");
            dojo.require("dijit.form.Button");
            var index="<s:property value='objDocumento.idDocumento'/>";
            var divtoolbar = "divtoolbar"+<%=indexTreeExp%>;
            var toolbar;
            
            dojo.addOnLoad(function() {
               if(dojo.byId(divtoolbar)){
                	toolbar = new dijit.Toolbar({},
                		divtoolbar);
                       
                    
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
	<s:if test="mostrarToolbar">
        <span id="divtoolbar<%=indexTreeExp%>">
        </span>
    </s:if> 
        <!--
        <div  dojoType="dijit.Toolbar">
			                <s:if test="#session._RECURSO.UsuFinBtnTraExp">                              
                               <div  dojoType="dijit.form.Button" onClick="abrirVentanaSeguimiento()" iconClass="dijitEditorIcon dijitEditorIconInsertTable">Trazabilidad</div>                               
                               </s:if>       
                            <s:if test="#session._RECURSO.HojaRuta">                                
                                 <div  dojoType="dijit.form.Button"  onClick="abrirHojaRuta()" iconClass="siged16 iconoHojaRuta">Hoja de Ruta</div>                                
                            </s:if>
          </div>                                              
		--> 	
		<table width="100%" border="0" align="left">	   
			<tr>					
				<td><s:hidden name="objDocumento.idDocumento" id="idDocumentoMostrado_1" /></td>
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
                              <td><s:property value="objDocumento.ID_CODIGO" /></td>
                           </tr> 
                            
                           <tr align="left">
                              <td class="header" width="50%" >Asunto del Documento</td>
                              <td><s:property value="objDocumento.asunto" /></td>
                           </tr>
                           <tr align="left">
                              <td class="header" width="50%" >Tipo de Documento</td>
                              <td><s:property value="objDocumento.tipoDocumento.nombre" /></td>
                           </tr>
                           <tr align="left">
                              <td class="header" >Documento</td>
                              <td id="nombreDocumento"><s:property value="objDocumento.tipoDocumento.nombre" />-<s:property value="objDocumento.numeroDocumento" /></td>
                           </tr >
                           <tr align="left">
                              <td class="header" >Estado</td>
                              <td>
                              	<s:if test="objDocumento.estado == @org.ositran.utils.Constantes@ESTADO_ACTIVO">
                              		Registrado
                              	</s:if>
                              	<s:elseif test="objDocumento.estado == @org.ositran.utils.Constantes@ESTADO_CERRADO">
                              		Archivado
                              	</s:elseif>
                                <s:elseif test="objDocumento.estado == @org.ositran.utils.Constantes@ESTADO_PENDIENTE">
                              		Pendiente
                              	</s:elseif>
                                <s:elseif test="objDocumento.estado == @org.ositran.utils.Constantes@ESTADO_ATENDER">
                              		Atendido
                              	</s:elseif>         
                                        
                              	<s:elseif test="objDocumento.estado == @org.ositran.utils.Constantes@ESTADO_ANULADO">
                              		Anulado
                              	</s:elseif>
                              	<s:else>
                              		Inactivo
                              	</s:else>
                              </td>
                           </tr >
                           <%-- 
                           <tr align="left">
                              <td class="header" >Principal</td>
                              <td >
                                 <s:if test="objDocumento.principal == 'S'">
                                    <img src="images/aprobarrr.gif" border="0" />
                                 </s:if>
                                 <s:else>
                                    <img src="images/desaprobar.gif" border="0" />
                                 </s:else>
                              </td>
                           </tr>
                           --%>
                         
                           <!-- Deprecado Req 28-09-2011 por wcarrasco
                           <tr align="left">
                              <td class="header" >Nro. Mesa de Partes</td>
                              <td >s:property value="objDocumento.numeroMesaPartes" /></td>
                           </tr>
                           -->
                           <tr align="left">
                              <td class="header" >Creador</td>
                              <td ><s:property value="creador" /></td>
                           </tr>
                           <tr align="left">
                              <td class="header">Fecha de Creaci&oacute;n</td>
                              <td ><s:date name="objDocumento.fechaCreacion" format="dd/MM/yyyy HH:mm" /></td>
                           </tr>
                                              
			   <s:if test="mostrarDetalle">
                                <tr align="left">							
                                   <td class="header"><a href="#" id="mostrar">M&aacute;s Detalle</a></td>
                                   <td ></td>
                                </tr>
			   </s:if>
                        </table>
                     </td>
                  </tr>
                  <tr>
                     <td colspan="1">
                        <div id="idDetalle" style="display:none;">
                           <table cellpadding="2" cellspacing="1" border="1" class="tableForm" align="left" width="100%">
                              
								<s:if test="puedeReferenciar">
								   
								<tr>
									<td class="header" style="width:380px;">Mover a otra Carpeta</td>
									<td>
										<input type="button" value="Mover" onclick="referenciar();" />										
									</td>
								</tr>
								</s:if>
                              
                              <tr>
                                 <td class="header" >Nro. Folios</td>
                                 <td ><s:property value="objDocumento.numeroFolios" /></td>
                              </tr>
                            
                              <tr>
                                 <td class="header" >Observaci&oacute;n</td>
                                 <td ><%=documento.getObservacion() %></td>
                              </tr>
                              <tr>
                                 <td class="header" >Fecha Documento</td>
                                 <td ><s:date name="objDocumento.fechaDocumento" format="dd/MM/yyyy"  /></td>
                              </tr>
                              <tr>
                                 <td class="header" >Fecha L&iacute;mite Atenci&oacute;n</td>
                                 <td ><s:date name="objDocumento.fechaLimiteAtencion" format="dd/MM/yyyy" /></td>
                              </tr>
                              <!-- Deprecado Req 28-09-2011 por wcarrasco
                              <tr>
                                 <td class="header">Pertenece al Expediente</td>
                                 <td>
                                    <s:if test='objDocumento.delExpediente.equals("S")'>
                                       <span>S&iacute;</span>
                                    </s:if>
                                    <s:else>
                                       <span>No</span>
                                    </s:else>
                                 </td>
                              </tr>
                              -->
                              <%--<s:if test="puedeReferenciar">
                              <tr>
                                 <td class="header">Modificar Datos</td>
                                 <td>
                                    <input type="button" onclick="modificarDDoc()" value="Modificar"/>
                                 </td>
                              </tr>
                              </s:if> --%>
                           </table>
                        </div>
                     </td>
                  </tr>

                  <tr>
                     <td height="13" colspan="2" class="header" ></td>
                  </tr>
                  <tr>
                     <td height="13" colspan="2" class="header" ></td>
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
                  <%--<tr align="center">
                     <td colspan="2"  align="center">
                        <table cellspacing="1" border="1" class="tableForm" align="left">
                           <tr>
                              <td>Datos Para la Mensajer&iacute;a</td>
                           </tr>
                           <tr>
                              <td>
                                 <input type="button" value="Enviar documento a Mensajeria" onclick="javascript:openMensajeriaWindow()" />
                              </td>
                           </tr>
                        </table>
                     </td>
                  </tr>--%>
               </table>
            </td>
            <td >
            </td>
         </tr>
      </table>
 </body>
</html>     		