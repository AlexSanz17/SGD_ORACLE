<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<%@ page import="org.ositran.utils.Constantes" %>
<%@ page import="java.text.SimpleDateFormat"%> 

<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html>
    <head>
        <title>Detalle del Documento de Despacho Virtual</title>

        <META HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE" >
        <script type="text/javascript">
            
          if ("<s:property value='objDD.flagCodigoVirtual' />" == "3"){
             alert("El documento ya se encuentra recepcionado en la entidad receptora. En breves minutos un proceso actualizara el estado del documento."); 
          }
          
          if ("<s:property value='objDD.flagCodigoVirtual' />" == "4"){
             alert("Error en el servicio de la entidad receptora."); 
          }
          
          if ("<s:property value='objDD.flagCodigoVirtual' />" == "5"){
             alert("Error en la comunicación con la entidad receptora."); 
          }
            
          function abrirVentanaRechazar() {
              var opciones = "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes, width=770, height=390, top=20, left=70";
              var pagina = "goDerivarUSER.action?arrIdDoc=" + <s:property value='objDD.iIdDocumento' /> + "&sTipoDerivacion=normal&sOpcion=rechazar" + "&codigoVirtual="+ <s:property value='objDD.idCodigo' />;
              window.open(pagina, "Ventana", opciones);
          }  
           
          function enviarDocumento(idExterno){
              if (!confirm("¿Desea Enviar el Documento Virtual?")){ 
                    return;
              }
              
              dijit.byId("dlgProgresBar").show();
              var service = new dojo.rpc.JsonService("SMDAction.action");
                    var defered = service.enviarDocumento(idExterno);
                    defered.addCallback(function(respuesta){
                         dijit.byId("dlgProgresBar").hide();
                         alert(respuesta);
                         showGridInbox(sTipoGridActual); 
                    })
          }
          
           function atenderDocumento(tipoArchivar){ 
               var opciones = "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes, width=750, height=440, top=20, left=70";
               var pagina = "Archivar_inicio1.action?idDocumento=" + <s:property value='objDD.iIdDocumento' /> + "&tipoArchivar="+tipoArchivar + "&codigoVirtual="+ <s:property value='objDD.idCodigo' />;
               window.open(pagina, "Ventana", opciones);
           }
           
           function abrirVentanaCopiarApoyo(){
               var opciones = "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes, width=810, height=516, top=20, left=70";
               var pagina = "goCopiarApoyo.action?arrIdDoc=" + <s:property value='objDD.iIdDocumento' /> + "&codigoVirtual="+ <s:property value='objDD.idCodigo' />;
               window.open(pagina, "Ventana", opciones);   
           }
          
          function abrirVentanaDerivar(opcion) {
              var opciones = "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes, width=770, height=485, top=20, left=70";
              var pagina = "goDerivarUSER.action?arrIdDoc=" + <s:property value='objDD.iIdDocumento' /> + "&sTipoDerivacion=normal&sOpcion="+opcion + "&codigoVirtual="+ <s:property value='objDD.idCodigo' />;
              window.open(pagina, "Ventana", opciones);
          }
          
          function verArchivoPrincipal(codigo){
              var fecha = new Date();
              window.open("<%=request.getContextPath()%>/verDocumento.png?idCodigo=" +codigo + "&accion=abrirPrincipalVirtual&fecha=" + fecha );
          }     
          
          function verArchivoAnexo(url){
               window.open(url, "_blank"); 
          } 
          
          function abrirHojaRuta(){
            	var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=900, height=700, top=50, left=200";
                var pagina = "ReporteAPN_verHojaRuta.action?idDocumento=" + "<s:property value='objDD.iIdDocumento' />" + "&codigoVirtual="+ <s:property value='objDD.idCodigo' />;;
                window.open(pagina,"HojaRuta",opciones);
          }
          
          function verArchivoCargo(codigo){
              var fecha = new Date();
              window.open("<%=request.getContextPath()%>/verDocumento.png?idCodigo=" +codigo + "&accion=abrirCargoDespachoVirtual&fecha=" + fecha );
          }     
          
          
        </script>
    </head>
    <body class="soria">
        
          <table width="100%">
            <tr>
                
                <td colspan="6" align="left">
                    <div dojoType="dijit.Toolbar"> 
                       <s:if test="objDD.flagCodigoVirtual == '2' || objDD.flagCodigoVirtual == '1'"> 
                           <s:if test="objDD.flagCodigoVirtual == '1'"> 
                              <div dojoType="dijit.form.Button"  onClick="enviarDocumento('<s:property value="objDD.idCodigo" />')" iconClass="siged16 iconoOk"> Enviar Documento</div>    
                           </s:if>
                           <s:if test="objDD.iIdDocumentoReferencia == null">
                              <div dojoType="dijit.form.Button" onClick="abrirVentanaRechazar()" iconClass="dijitEditorIcon dijitEditorIconUndo">Devolver</div>              
                           </s:if> 
                           <div dojoType="dijit.form.Button" onClick="abrirVentanaCopiarApoyo()" iconClass="dijitEditorIcon dijitEditorIconIndent">Derivar M&uacute;ltiple</div>
                           <s:if test="objDD.flagCodigoVirtual == '1'"> 
                              <div dojoType="dijit.form.Button"  onClick="atenderDocumento('atender')" iconClass="siged16 iconoOk">Atender</div>    
                           </s:if>   
                      </s:if>
                      <div dojoType="dijit.form.Button"  onClick="abrirHojaRuta()" iconClass="siged16 iconoHojaRuta">Hoja de Ruta</div>        
                   </div>    
                </td>
            </tr>
              
            <tr>
                <td style="width: 2%;"></td>
                <td colspan="2" style="width: 35%;font-size:12px;" valign="top"> 
                    <br/><b>Asunto: </b><s:property value="objDD.strAsunto" />
                </td>  
                
                <td colspan="2" style="width: 35%;" valign="top">
                        <table width="100%">
                            <tr>
                                <td></td>
                                <td height="16" colspan="5" align="left" class="plomo"><br/>Adjuntos:</td>
                            </tr>
                            
                             <tr>
                                <td></td>
                                <td height="16" colspan="5" align="left" class="plomo">
                                    <a onclick="verArchivoPrincipal('<s:property value='objDD.idCodigo' />');" alt="Ver Archivo"> <b><font color="<s:property value="@org.ositran.utils.Constantes@COLOR_DOCUMENTO_PRINCIPAL"/>"><s:property value="objDD.archivoPrincipal"/> </font></b><br /> </a>
                                    <s:if test="objDD.archivoCargo !=null && objDD.archivoCargo!=''"> 
                                      <a onclick="verArchivoCargo('<s:property value='objDD.idCodigo' />');" alt="Ver Archivo"> <b><font color="<s:property value="@org.ositran.utils.Constantes@COLOR_DOCUMENTO_MP_CARGO"/>"><s:property value="objDD.archivoCargo"/> </font></b><br /> </a> 
                                    </s:if>
                                    <s:if test="objDD.archivoAnexo != ''">
                                          Anexos:<br/> 
                                         <a onclick="verArchivoAnexo('<s:property value="objDD.archivoAnexo"/>');" alt="Ver Archivo Anexo"> <s:property value="objDD.archivoAnexo"/> [<s:property value="objDD.cantAnexos"/>]<br /> </a>
                                         <s:iterator value="objDD.listAnexos"> 
                                           &nbsp;&nbsp;  <s:property/><br/>
                                         </s:iterator>    
                                    </s:if>
                                </td>
                             </tr>       
                        </table>
                </td>
                
                <td style="width: 27%;" align="right" valign="top">
                    <!--<div id="tabla12" style="width: 100%;text-align:left;"> -->
                        <table cellpadding="1" cellspacing="1" class="tableForm">
                           <tr style="height:20px;">
                                <td width="100px" style="font-size: 11px;font-weight:normal;background-color:#669BCD;color:#ffffff;vertical-align:center;text-align:center;border: 1px #000 solid;">
								Nro. Documento
                                </td>
                                <td style="background-color:#ffffff;border: 1px #000 solid;width: 250px;">
                                    <s:property value="objDD.strNroDocumento" /> 
                                </td>
                            </tr>
                                
                            <tr style="height:20px;">
                                    <td style="font-size: 11px;font-weight:normal;background-color:#669BCD;color:#ffffff;vertical-align:center;text-align:center;border: 1px #000 solid;">
								Cliente
                                    </td>
                                    <td style="background-color:#ffffff;border: 1px #000 solid;width: 250px;">
                                        <s:property value="objDD.strRazonSocial" />
                                    </td>
                             </tr>   
                             
                             <tr style="height:20px;">
                                    <td style="font-size: 11px;font-weight:normal;background-color:#669BCD;color:#ffffff;vertical-align:center;text-align:center;border: 1px #000 solid;">
								CUO
                                    </td>
                                    <td style="background-color:#ffffff;border: 1px #000 solid;width: 250px;">
                                        <s:property value="objDD.cuo" />
                                    </td>
                              </tr>             
                        </table>
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
                                        <td width="1px"></td>
                                        <td colspan="5">
                                            <div>  
                                                    <p>
                                                        Ud. ha recibido un Documento Virtual con
                                                        Nro. <font class="negrita"><s:property value="objDD.strNroDocumento" /></font>
                                                        el dia <font class="negrita"><s:date name="objDD.strFecha" format="dd/MM/yyyy" /></font>
                                                        a las <font class="negrita"><s:date name="objDD.strFecha" format="HH:mm" /></font>
                                                    </p>
                                            </div>  
                                        </td>
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
    </body>
</html>