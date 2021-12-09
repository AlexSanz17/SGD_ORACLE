<?xml version="1.0" encoding="UTF-8" ?>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="com.btg.ositran.siged.domain.Parametro" %>
<%@ page import="com.btg.ositran.siged.domain.UsuarioDerivacion"%>
<%@ page import="com.btg.ositran.siged.domain.DocumentoReferencia" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es"> 
<head>

<s:url id="autoUrl" action="autocompletarProceso1">
	<s:param name="sTipoDerivacion" value="sTipoDerivacion" />
</s:url>
<s:if test="iddestinatario == null || iddestinatario == 0">
	<s:if test="paraAprobar">
		<title>Aprobar Documento</title>
	</s:if>
	<s:else> 
		<title>Derivar Documento</title> 
	</s:else>
</s:if> 
<s:else>
	<title>Devolver Documento</title>
</s:else>
<link type="text/css" rel="stylesheet" href="css/styleSiged.css" />
<link type="text/css" rel="stylesheet" href="js/dojo/css/styleDojo.css" />
<style type="text/css">
              
.fondo {
    margin:0; background:url(../images/gradient.jpg) repeat-x;
}

div.falso {
	position: absolute;
	/*top: -48px;*/
	left: 80px;
	z-index: 0;
	/*width: 14px;
            height: 3px;*/
}

input.file {
	position: relative;
	filter: alpha(opacity :   0);
	opacity: 0;
	z-index: 1;
	/*top: -49px;
            text-align: left;
            left: 80px;*/
	width: 50px;
	left: 10px;
}

.copia {
	margin-right: 5px;
}
</style>

<%

        List<UsuarioDerivacion> lstPara = (List<UsuarioDerivacion>)request.getAttribute("listaDerivacionPara");
        List<UsuarioDerivacion> lstCC =   (List<UsuarioDerivacion>)request.getAttribute("listaDerivacionCC");
        List<DocumentoReferencia> documentosReferencia = (List<DocumentoReferencia>)request.getAttribute("documentosReferencia");
%>

<script type="text/javascript">


			var djConfig = {
				isDebug: false,
				parseOnLoad: true
			};
		</script>
<script type="text/javascript" src="js/dojo/dojo/dojo.js"></script>
<script type="text/javascript" src="js/siged/upload.js"></script>
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/siged/siged.js"></script>
<script type="text/javascript" src="js/siged/siged.forms.js"></script>
<script type="text/javascript" src="js/siged/siged.string.js"></script>
<script type="text/javascript" src="js/siged/derivacion.js"></script>
<script type="text/javascript">
         
         var USUARIOACTIVO = "<s:property value='#session._USUARIO.idUsuarioPerfil'/>-<s:property value='#session._USUARIO.idUnidadPerfil' />-<s:property value='#session._USUARIO.idFuncionPerfil'/>";
         var OPCION = "<s:property value='sOpcion' />";
         var idRemitente = "<s:property value='objDD.iIdRemitente' />";
         var paraAprobar = "<s:property value='paraAprobar'/>";
         var provieneDeMail = "<s:property value='provieneDeMail'/>";

         console.debug("(derivar.jsp) OPCION [%s]", OPCION);
         console.debug("(derivar.jsp) idRemitente [%s]", idRemitente);
         console.debug("(derivar.jsp) paraAprobar [%s]", paraAprobar);
         console.debug("(derivar.jsp) provieneDeMail [%s]", provieneDeMail);
         dojo.require("dijit.Editor");
         dojo.require("dijit._editor.plugins.FontChoice");  // 'fontName','fontSize','formatBlock'
         dojo.require("dijit._editor.plugins.TextColor");
         dojo.require("dijit._editor.plugins.LinkDialog");
         dojo.require("dojo.io.iframe");
         dojo.require("dojo.rpc.JsonService");
         dojo.require("dijit.form.FilteringSelect" );
         dojo.require("dojo.data.ItemFileReadStore");
         dojo.require("dijit.form.TextBox");
         dojo.require("dijit.form.DateTextBox");
         dojo.require("dijit.Dialog");
         dojo.require("dijit.form.Button");
	 dojo.require("dojox.grid.DataGrid");
         dojo.require("dijit.form.NumberTextBox");

         var service = new dojo.rpc.JsonService("SMDAction.action");
         var store = null;
         var storePrioridades = new dojo.data.ItemFileReadStore({url: "Parametro_getPrioridades_.action"});
         var storeProveidos = new dojo.data.ItemFileReadStore({url: "autocompletarProveido.action"});

         if (OPCION == "reenviar") {
            store = new dojo.data.ItemFileReadStore({url: "getFavorito.action"});
         } else {
            store = new dojo.data.ItemFileReadStore({url: "<s:property value='autoUrl' />&sOpcion=<s:property value='sOpcion' />"});
         }

         var strDerivarCC = new dojo.data.ItemFileReadStore({url: "derivarCC.action"});

         function cargarPlazo(){
            
         }    

         function validarForm(){
             
                               if(dijit.byId("iddestinatario").state||dijit.byId("iddestinatario").value==""){
				   alert("Debe especificar Para.");
				   dijit.byId("iddestinatario").focus();
				   return false;
				}
                                
                                if (dijit.byId("strAcc") && dijit.byId("strAcc").attr("value")==""){
                                   alert("Debe especificar la Acción.");
                                   return false;
                                }    
                                
                                if (dijit.byId("objDD.prioridad") && dijit.byId("objDD.prioridad").attr("value")==""){
                                    alert("Debe especificar la Prioridad.");
                                    return false;
                                }
                                
                                var contenido=dijit.byId("contenid").getValue();
				if(contenido.length>4000){
                                     alert("El contenido no debe tener mas de 4000 caracteres");
                                     return false;
				}
                                
 			return true;
 		}

        function ordenarAcciones(){
        	var arrAccT = [];
	        dojo.query("input[name='acciones']").forEach(function(node){
	     		if(node.checked){
	     			arrAccT.push(node.id.substr(4));
	     		}
	     	});
	        return arrAccT;
        }

          var derivando=false;
          
          function derivar(){
             var arrAccT = [];
             
             if(!derivando){
                 if(validarForm()){
                     derivando=true;
                     var service = new dojo.rpc.JsonService("SMDAction.action");
                     var defered = service.validarEnvio("<s:property value='objDD.iIdDocumento' />", "",  dijit.byId("iddestinatario").attr("value") , "");
                     defered.addCallback(function(respuesta){
                         if (respuesta == '-1'){
                           alert("El documento ya fue derivado");    
                           derivando=false;
                           return;
                         }
                         
                         if (respuesta == '-2'){
                           alert("El documento ya fue atendido");
                           derivando=false;
                           return;
                         }
                         
                         if (respuesta == '-3'){
                           alert("El documento ya fue anulado");
                           derivando=false;
                           return;
                         }
                         
                         if (respuesta == '-4'){
                           alert("El documento debe estar Firmado.");
                           derivando=false;
                           return;
                         }
                         
                          if (respuesta == "-5"){
                            derivando=false;  
                            alert("Se ha producido un error inesperado. Intente procesar su solicitud nuevamente."); 
                            return;
                         }
                        
                        document.getElementById("objDD.strTexto").value = dijit.byId("contenid").getValue(false);
                        if(OPCION != "rechazar")
                           document.getElementById("acciones").value = dijit.byId("strAcc").attr("value");//ordenarAccio

                        dojo.query("input[name='docReferencias']").forEach(function(node){
                           if(node.checked){
                             arrAccT.push(node.id);
                           }
                        });
                        
                        dojo.query("input[name='strSeguimiento']").forEach(function(node){
                           if(node.checked){
                             document.getElementById("strSeguimiento").value = "1";
                           }
                        });

                        document.getElementById("referencias").value = arrAccT;
                        dijit.byId("dlgProgresBar").show();
                        document.getElementById("derivar").submit();  
                          
                    });
                 }
	      }
	  }

         function subir() {
            document.forms[0].action = 'doUploadUserManual.action' ;
            document.forms[0].submit();
         }
         
        
         function refrescar() {
           try {
                  if(window.opener.sTipoGridActual != window.opener.TIPO_GRID_SEGUIMIENTO){
                      window.opener.eliminarRegistro();
                      this.window.close();
                  }else{
                     window.opener.refrescarDetalle();
                  }
            }catch(err){
                  this.window.close();
            }
  
            if (provieneDeMail == "true"){
               window.opener.close();
            }
            
         }

  	function clearfecha(){
    	   document.getElementById('fecha').value="";
    	}

         function limpiarCampoWarning(){
            document.getElementById("warningFechaLimite").innerHTML="";
         }

	 function ocultafecha (){
	   $("#fechaLimite").html("");
	 }

         dojo.addOnLoad(function() {
            var idDestinatarioAux = "<s:property value='objDD.iIdRemitente'/>";
	    var iddestinatario = "<s:property value='iddestinatario'/>";
	    var paraAprobar = "<s:property value='paraAprobar'/>";
	    var componente = dijit.byId("iddestinatario");

	    if( (paraAprobar=="true" || (!Siged.String.isEmpty(iddestinatario))) && !Siged.String.isEmpty(componente)){
		dijit.byId("iddestinatario").setValue(idDestinatarioAux);
		dijit.byId("iddestinatario").attr("readonly", "true");
	    }
 
	    if(paraAprobar=="true"){
               validarRemitente(idDestinatarioAux);
	    } else if(!Siged.String.isEmpty(iddestinatario) && !Siged.String.isEmpty(componente)){
               dijit.byId("iddestinatario").attr("value", iddestinatario);
            }
                                 
            dijit.byId("objDD.prioridad").attr("value", "<s:property value='objDD.prioridad'/>");
            
            
            <% if (lstPara!=null && lstPara.size()>0){%>
                <%for(int i=0;i<lstPara.size();i++){%>
                   dijit.byId("iddestinatario").store = new dojo.data.ItemFileReadStore({url: "getFavorito.action?mode=" + "iddestinatario"});
                   dijit.byId("iddestinatario").attr("value", "<%=lstPara.get(i).getIdentificador()%>");
                <%}%> 
            <%}%>
                
            <% if (lstCC!=null && lstCC.size()>0){%>
               dijit.byId("idconcopia").attr("value", "<%=lstCC.get(0).getIdentificador()%>");
               <%for(int j=0; j<lstCC.size();j++){%>
                   var copia="<span class=\"copia\">";
                   copia+="<span>"+"<%=lstCC.get(j).getNombreUsuario()%>"+"</span>";
                   copia+="<input type=\"hidden\" name=\"conCopia\" value=\""+"<%=lstCC.get(j).getIdentificador()%>"+"\" />";
                   copia+="<a href=\"#\" class=\"quitar\"><img src=\"images/eliminar.gif\" alt=\"X\" /></a></span>";
                   $("#copias").append(copia);
                   $(".quitar").click(function(){
                     $(this).parent().remove();
                   });  
                   
               <%}%>  
            <%}%>                                  
         });
         

	</script>
</head>
<body  class="soria" 
       <s:if test='cerrar!=null'>onload="refrescar();" </s:if>>
	<s:if test="cerrar==null">
            
              <table width="100%" border="0">
			<tr>
				<td height="14" colspan="6"
					style='padding: 6px 8px; font-weight: bold'><s:property value="" />
					<!--  	value="fechaEnTexto" />  -->
				</td>
			</tr>
			<tr align="center">
				<td width="6px" align="center"></td>
				<td colspan="4" align="left">
                                       <img src="images/enviar.bmp"
					alt="Enviar" style="float: left;" onclick="javascript:derivar();" />
                                  
					<s:if test="iddestinatario == null || iddestinatario == 0">
						<s:if test="#session._RECURSO.UsuFinBtnUpl">
							<s:form id="frmUploadFile1" action="doUploadFile" method="post"
								enctype="multipart/form-data">
								<div id="divUpload" style="float: left; display: none;">
									<input name="upload" type="file" class="file" size="1"
										onchange="uploadIt(1)" />
									<div class="falso">
										<img src="images/adjunto.bmp" alt="Adjuntar Archivo" />
									</div>
								</div>
							</s:form>
						</s:if>
					</s:if></td>
			</tr>
			<tr>
				<td colspan="5" align="left" class="plomo">
					<div id="divShowFile1">
						<%@include file="/pages/upload/lstUploadWithOutTextArea.jsp"%>
					</div></td>
			</tr>
			<tr>
				<td height="16" colspan="5" align="left" class="plomo">
                                    
					<form id="derivar" action="doDerivarUSER.action" method="post">
                                                <input type="hidden" name="strReferencia" id="referencias"/>
                                                <input type="hidden" name="codigoVirtual"  value="<s:property value='codigoVirtual' />"/>
                                                <input type="hidden" name="objDD.observacionDocumento" id="objDD.observacionDocumento"/>
						<s:hidden name="objDD.iIdDocumento" />
                                                <s:hidden name="objDD.iIdProceso" id="objDD.iIdProceso" />
						<s:hidden name="sTipoDerivacion" />
						<s:hidden name="sOpcion" />
                                                <s:hidden name="destinatarioIgualRemitente" />
						<s:hidden name="objDD.iIdRemitente" />
						<table width="100%" border="0" >
                                                          
							<tr>
								<td></td>
								<td width="90px" height="16" align="left" class="plomo">
                                                                       
                                                                       <s:if test="!paraAprobar && (iddestinatario == null || iddestinatario == 0)">
										<button dojoType="dijit.form.Button" type="button"
											id="btnPara" jsId="btnPara"
											onClick="showGrdPara('iddestinatario')" showLabel="true">Para</button>
									</s:if> <s:else>
                                                                                    Para
                                                                                 </s:else></td>
								<td colspan="1">
									<s:if test=" !paraAprobar && (iddestinatario == null || iddestinatario == 0) ">
                                         <s:hidden name="objDD.strAccion" value="reenviar" />
										<div id="fsPara"></div>
									</s:if>
									<s:else>
                                         <s:if test="objDD.iIdAccion == 4">
											<s:hidden name="objDD.strAccion" value="rechazar" />
										</s:if>
										
										<select dojoType="dijit.form.FilteringSelect" store="store"
											required="false" id="iddestinatario" idAttr="id"
                                                                                        style="width:280px"
											labelAttr="label" searchAttr="label" name="iddestinatario"
											value="<s:property value='iddestinatario' />" size="25"
											readonly="true">
										</select>
									</s:else>
								</td>
                                                                <td width="30px" rowspan="2" valign="top"> </td>
                                                                <td width="320px" rowspan="2" valign="top">
                                                                  <%if (documentosReferencia!=null && documentosReferencia.size()>0){%>  
                                                                        <table border="0">
                                                                          <b>Documento a Atender</b>                                                                         
                                                                           <%for(DocumentoReferencia p : documentosReferencia){ %>                                                                            
                                                                               <tr>   
                                                                                 <td> 
                                                                                   <input dojoType="dijit.form.CheckBox" id="<%=p.getIdDocumentoReferencia()%>"
                                                                                         name="docReferencias"><%=p.getDocumentoReferencia().getTipoDocumento().getNombre() + " " + p.getDocumentoReferencia().getNumeroDocumento()%>
                                                                                 </td>
                                                                               </tr>  
                                                                            <%}%>
                                                                         
                                                                         </table>	
                                                                   <%}%>  
								</td>
							</tr>
							<s:if test="iddestinatario == null || iddestinatario == 0">
								<tr>
									<td>&nbsp;</td>
									<td height="16" align="left" class="plomo">
										<button dojoType="dijit.form.Button" type="button" id="btnCc"
											jsId="btnCc" onClick="showGrdPara('idconcopia')"
											showLabel="true">Cc</button>
									</td>
									<td>
										<div id="iddestinatarios"></div> 

                                                                        </td>
                                                        
								</tr>
								<tr>
									<td colspan="2"></td>
									<td colspan="4">
										<div id="copias"></div></td>
								</tr>
								<tr>
									<td colspan="6">&nbsp;</td>
								</tr>
								
                                                                  <tr>
                                                                      <td>&nbsp;</td>
                                                                         <input type="hidden" name="strAcc" id="acciones"/>
									 <td height="30px" align="left" class="plomo">Acciones:</td>
									 <td colspan="4">
										<select dojoType="dijit.form.FilteringSelect"
                                                                                    id="strAcc"
                                                                                    name="strAcc"
                                                                                    idAttr="id"
                                                                                    labelAttr="label"
                                                                                    style="width:179px;"
                                                                                    required="true"
                                                                                    hasDownArrow="true"
                                                                                    searchAttr="label"
                                                                                    store="storeProveidos"></select> 
                                                                              
                                                                          </td>
                                                                        
                                                                  </tr>  
                                                                  
								
							</s:if>
							
							<tr>
								<td></td>
								<td height="16" align="left" class="plomo">Asunto:</td>
                                                                <td colspan="4"><s:textarea cols="105" rows="4" id="asunto" readonly="true" 
										name="objDD.strAsunto" cssClass="cajaMontoTotal" size="100"
										required="false" maxlength="400" /></td>
							</tr>

							<tr>
							    
								     <td>&nbsp;</td>
									 <td height="30px" align="left" class="plomo">Prioridad:</td>
									 <td colspan="3">
                                                                               <table>
                                                                                   <tr> 
                                                                                       <td>
                                                                                          <select dojoType="dijit.form.FilteringSelect"
												id="objDD.prioridad"  
												name="objDD.prioridad"
												store="storePrioridades"
												required="true"
                                                                                                searchAttr="label"
												size="25px">
										           </select>
                                                                                       </td>
                                                                                       <!--<td id="columnaPlazo" > 
                                                                                        &nbsp;Plazo:  <input
                                                                                           dojoType="dijit.form.NumberTextBox" id="objDD.iPlazoDia" value="<s:property value='objDD.iPlazoDia'/>"
                                                                                           name="objDD.iPlazoDia" style="width: 42px;"
                                                                                           maxlength="2" type="text"></input> día(s) 
                                                                                       </td> -->
                                                                                       
                                                                                        <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Fecha Limite:</td>
                                                                                            <td class="label" colspan="1">
                                                                                               <div dojoType="dijit.form.DateTextBox"
                                                                                                    id="objDD.strFechaLimiteAtencion"
                                                                                                    jsId="objDD.strFechaLimiteAtencion"
                                                                                                    name="objDD.strFechaLimiteAtencion"
                                                                                                    constraints="{datePattern:'dd/MM/yyyy'}" 
                                                                                                    invalidMessage="Ingrese fecha limite dd/MM/yyyy"                                                            
                                                                                                    trim="true">
                                                                                                  <script type="dojo/method" event="postCreate">
                                                                                                     var fechadocumento_year;
                                                                                                     var fechadocumento_month;
                                                                                                     var fechadocumento_day;

                                                                                                     if ("<s:date name='objDD.dateFechaLimiteAtencion' format='dd' />"== null ||
                                                                                                         "<s:date name='objDD.dateFechaLimiteAtencion' format='dd' />"== ""){ 
                                                                                                          this.attr("value", "");
                                                                                                     }else{
                                                                                                         fechadocumento_year = parseInt("<s:date name='objDD.dateFechaLimiteAtencion' format='yyyy' />", 10);
                                                                                                         fechadocumento_month = parseInt("<s:date name='objDD.dateFechaLimiteAtencion' format='MM' />", 10);
                                                                                                         fechadocumento_day = parseInt("<s:date name='objDD.dateFechaLimiteAtencion' format='dd' />", 10);  
                                                                                                         this.attr("value", new Date(fechadocumento_year, fechadocumento_month - 1, fechadocumento_day));
                                                                                                      }    
                                                                                                  </script>
                                                                                               </div>
                                                                                        </td>
                                                                                        <s:if test="iddestinatario == null || iddestinatario == 0">                 
                                                                                            <td> &nbsp;&nbsp;
                                                                                                <input dojoType="dijit.form.CheckBox" id="strSeguimiento"
                                                                                                     name="strSeguimiento">Marcar Para Seguimiento 
                                                                                            </td>   
                                                                                        </s:if>    
                                                                                   </tr>
                                                                               </table>
									 </td>
                                                                         
                                                                                                                 
								 
							</tr>
                                                        <tr>
                                                            <td>&nbsp;</td>
                                                            <td height="28px" align="left" class="plomo">Proveido:</td>
                                                            <td></td>
                                                            <td></td>
                                                            
                                                        </tr>    

							<s:if test="paraAprobar==false">
						<!-- 	<tr>
								<td></td>
								<td height="16" align="left" class="plomo">Adjuntos</td>
								<td colspan="4">
								<s:if test="#session.UPLOADLIST.upload1 != null">
									<s:iterator value="#session.UPLOAD_LIST.upload1">
										<a href="<s:property value="sURL" />" target="_blank"><s:property value="nombreReal" /></a><br />
									</s:iterator>
								</s:if>
								</td>
							</tr>
						-->
								<!--
	                     <tr>
	                        <td></td>
	                        <td height="16" align="left" class="plomo">Mantener en mi bandeja</td>
	                        <td colspan="4">
	                           <s:checkbox name="bBandeja" />
	                        </td>
	                     </tr>
	                      -->

							</s:if>


							<tr>
								<td></td>
								<td colspan="5"  class="normal"><s:hidden
										id="objDD.strTexto" name="objDD.strTexto" /> 
                                                                            <%--<s:hidden id="ta" name="ta" />--%>
										<div dojoType="dijit.Editor" jsid="contenid" id="contenid"
											style="background-color: #FFF;height:15em;width:765px;overflow:auto"></div> <script
											type="text/javascript">

									   var cadena  = document.getElementById("ta").value;
									   //document.getElementById("contenid").innerHTML = cadena;
									   //cadena  =document.getElementById("contenid").innerHTML;
									   //alert("cadena1:"+cadena);
									   cadena = cadena.replace(/<P><\/P>/g,"");
									   //alert("cadena2:"+cadena);
									   document.getElementById("contenid").innerHTML = cadena.substring(0,3999);
									    //cadena=document.getElementById("contenid").innerHTML;
									   //alert(cadena);


									</script>
								</td>
							</tr>
                                                        <tr>
                                                            <td></td>
                                                            <td  colspan="5">
                                                               <input    style="font-weight: bold;font-size:14px;width:765px"
									type="text" name="ta" id="ta"
                                                                        value="<s:property value='ta'  />"> </input>
                                                            </td>
                                                            
                                                        </tr>    
						</table>
					</form></td>
			</tr>
			<tr>
				<td height="14" colspan="6"></td>
			</tr>
		</table>
		<%@ include file="../pages/util/progressBar.jsp"%>
		<%@ include file="derivar-dialogs.jsp"%>
	</s:if>
	<s:else>
		<p style="text-align: center;">Operaci&oacute;n realizada
			satisfactoriamente</p>
	</s:else>
</body>
</html>