Alerta<?xml version="1.0" encoding="UTF-8" ?>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es">
	<head>	 
	    <s:url id="autoUrl" action="autocompletarProceso1">
		</s:url>
		
		<title>Adjuntar Documentos</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>  
		<meta HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE" >
		<meta HTTP-EQUIV="Expires" CONTENT="-1">
		 
		<style type="text/css">
			@import "js/dojo/dijit/themes/soria/soria.css";
			@import "css/estilo.css";
			div.falso {
				position: absolute;
				top: -8px;
				left: 15px;
				z-index: 0;
				width: 14px;
				height: 3px;
			}

			input.file {
				position: relative;
				text-align: left;
				filter: alpha(opacity :   0);
				opacity: 0;
				z-index: 1;
				top: -8px; 
				left: -25px;
			}
			img.registrar  {
				position: absolute;
				top: 41px;
				left: 105px;
				z-index: 0;
			
			}      
		</style>
		<script type="text/javascript">
			var djConfig = {
            	isDebug: false,
            	parseOnLoad: true
         	};
		</script>
 		<script type="text/javascript" src="js/dojo/dojo/dojo.js"></script>
		<script type="text/javascript" src="js/siged/upload.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript">  
			dojo.require("dijit.form.DateTextBox");
			dojo.require("dijit.form.FilteringSelect" );
			dojo.require("dojo.data.ItemFileReadStore");
			dojo.require("dijit.form.ValidationTextBox");
			dojo.require("dojo.io.iframe");
			dojo.require("dijit.Dialog");
			dojo.require("dijit.ProgressBar");  		

			var mensajeAlerta = "<s:property escape="false" value ="mensaje" />";
			var storeTipoDocumento = new dojo.data.ItemFileReadStore({url: "autocompletarTipoDocumentoAdjuntar.action"+"?unidad=<s:property value="expediente.proceso.responsable.unidad.idunidad"/>"}); 
			var store = new dojo.data.ItemFileReadStore({url: "<s:property value='autoUrl'/>?sTipoDerivacion=normal&sOpcion=derivar" })     ;

			<s:if test="cerrar==null">
				var a=parseInt(<s:date name='objDocumento.fechaDocumento' format='yyyy' />);
		        var m=parseInt(<s:date name='objDocumento.fechaDocumento' format='MM' />);
		        var d=parseInt(<s:date name='objDocumento.fechaDocumento' format='dd' />);
				var fecha=new Date(a,m-1,d); 
				dojo.addOnLoad(function(){
					new dijit.form.DateTextBox({
						name			: "objDD.strFechaDocumento",
						constraints		: {datePattern:"dd/MM/yyyy"},
						invalidMessage	: "Ingrese una fecha con el formato dd/mm/yyyy",
						required		: true
					},"fechaDocumento");
					try{
						dijit.byId("fechaDocumento").attr("value",fecha);
					}catch(err){
						//console.debug("error: "+err);
					}
				}); 
			</s:if>
       
      		var archivosAlf;
      		var duplicadosup;
	      	var unidadActual = "<s:property value='expediente.proceso.responsable.unidad.idunidad' />";
	      	var tipodocumentoActual = null  ;
	      	var tiponumeracionActual = null ; 
	      	var firmanteActual = null ; 
	      	var destinatarioActual = null ;
			var  cant =0 ;
			
	      	<s:iterator value="expediente.documentoList">
		    	<s:iterator value="archivos">
		        	cant = cant + 1 ;  
		      	</s:iterator>
	      	</s:iterator>     
	
	      	var rutapadre = '<s:property value ="rutapadre" />' ;
	      	var firmanteAux = '<s:property value='#session._USUARIO.idusuario' />';
	      	var origen="<s:property  value ='origenDerivacion' />";
	      	var idNotificacion="<s:property  value ='iIdNotificacion' />";
		</script>
        <script type="text/javascript" src="js/siged/siged.js"></script>
        <script type="text/javascript" src="js/siged/siged.array.js"></script>
        <script type="text/javascript" src="js/siged/siged.commons.js"></script>
        <script type="text/javascript" src="js/siged/siged.documento.js"></script>
        <script type="text/javascript" src="js/siged/siged.documento.numeracion.js"></script>
        <script type="text/javascript" src="js/siged/siged.forms.js"></script>
        <script type="text/javascript" src="js/siged/siged.string.js"></script>
		<script type="text/javascript" src="js/siged/upload.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
 		<script type="text/javascript" src="js/siged/SubirConMetadata.js"></script>	
	</head> 
	<body class="soria" <s:if test="cerrar!=null">onload="refrescar()"</s:if>>   
		<table width="100%" border="0">
			<tr>  
				<td height="14">&nbsp;</td>
			</tr>
	<tr>
				<td height="20" class="titulo">
               <table width="99%" border="0" height="20" align="center" bgcolor="#A2C0DC">
			<tr>
				<td align="left" class="titulo">Nuevo Documento</td>
			</tr>
		</table>
		</td>
	</tr>
			<tr>
				<td style="margin-left:10px;">
            <%--
                   <img border="0" onclick="addhidden('tablaformulario','versionar' , true); " alt="Crear Documento"/>
            --%>      
		        <%--  
               <s:if test="#session._RECURSO.UsuFinBtnUpl">
                  &nbsp;&nbsp;
                  <s:form id="frmUploadFile2" action="doUploadFile" method="POST" enctype="multipart/form-data">
                     <div id="divUpload" style="position:relative;">
                        <input name="upload" type="file" class="file" size="1" onchange="uploadIt(2)" onclick="this.value=''"/>
                        <div class="falso">
                           <img src="images/adjunto.bmp" align="left" border="0" alt="Adjuntar Archivo" />
                        </div>
				</div>
			</s:form>
               </s:if> 
				--%> 
				<!--<img class="registrar" src="images/xx.bmp" border="0" onclick="submitForm();" alt="Crear Documento"/> -->
					<input type="button" dojoType="dijit.form.Button" onclick="submitForm();" label="Registrar Documento"/>&nbsp;
            </td>
	</tr>
		<!--<tr>
				<td height="16"align="left" class="plomo">
		<div id="divShowFile2"></div>
		</td>
	</tr>
	 	-->
	<tr>
				<td height="14">
               <s:form name="frmUploadWithForm" action="SubirConMetadata_dosubirConMetadata" method="post">
					<!--<s:iterator value="expediente.documentoList">
				      <s:iterator value="archivos">
				          <s:hidden name="rutaAlfresco"/> 
				          <s:hidden name="idArchivo" /> 
				      </s:iterator>
			      </s:iterator> 
					-->
				<s:hidden id="idDocumento" name="idDocumento" />  
			    <s:hidden name="origenDerivacion" /> 
			    <s:hidden name="iIdNotificacion" />			    
                  <table id="tablaformulario" width="95%" border="1" align="center" bordercolor="#669BCD" bgcolor="#BFD9F1">
				<tr>
							<td>
					<s:if test="cerrar==null">
					<table id="datosadj" width="98%"   height="97" align="center">
						<tr>
												<td width="1%">&nbsp;</td>
												<td width="30%" height="5">&nbsp;</td>
												<td width="17%">&nbsp;</td>
												<td width="15%">&nbsp;</td>
												<td width="40%">&nbsp;</td>
												<td width="3%">&nbsp;</td>
						</tr>
						<tr>
							<td colspan="6" height="15" class="label">DOCUMENTO</td>
						</tr>
						<tr>
			               <td colspan="6">
													<div id="showErrorDoc" class="margen5PX"
														style="color: red; font-weight: bold;">&nbsp;</div></td>
			            </tr>
						<tr>
							<td></td>
							<td height="16" align="left">Tipo Documento</td>
							<td align="left" width="17%" class="label">
							<div dojoType="dijit.form.FilteringSelect"
														store="storeTipoDocumento" idAttr="id" labelAttr="label"
														searchAttr="label" required="true"
                                         onChange = "actualizarFirmante"
								         name="objDocumento.tipoDocumento.idtipodocumento"
                                         id="objDocumento.tipoDocumento.idtipodocumento"
														style="width: 350px; font-size: 10px" /></td>
							<td align="left" class="label"></td>
							<td align="left" class="label"></td>
							<td></td> 
						</tr>
						
						<tr id="firmante" style="display: none;">
							<td></td> 
							<td height="16" align="left">Firmante </td>
							<td align="left" width="17%" class="label">
													<!--   onChange = "actualizarFirmante"  --> <select
													dojoType="dijit.form.FilteringSelect" store="store"
													required="false" jsId="objDocumento.firmante.idusuario"
									onChange = "mostrarOcultarFirmante"  
													id="objDocumento.firmante.idusuario" idAttr="id"
													style="width: 300px;" label" 
									searchAttr="label" 
									name="objDocumento.firmante.idusuario" 
									value="<s:property  value='#session._USUARIO.idusuario' />"  >
												</select></td>
							<td align="left" class="label"></td>
							<td align="left" class="label"></td>
							<td></td>
						</tr>
						
						<tr>
							<td></td>
							<td height="16" align="left">Autor </td>
							<td align="left" colspan="3" width="17%" class="label">
							<div dojoType="dijit.form.ValidationTextBox"
														id="objDocumento.autor" jsId="objDocumento.autor"
                                  name="objDocumento.autor.nombres"
														invalidMessage="Ingrese un Autor" required="false"
                                  style="width:300px"
                                  value="<s:property  value="objDocumentoPrincipal.propietario.nombres" /> <s:property  value="objDocumentoPrincipal.propietario.apellidos" />"
														trim="true" /></td>
							<td align="left" class="label"></td>
							<td align="left" class="label"></td>
							<td></td>
						</tr>
						
						<tr>
							<td></td>
							<td height="16" align="left">Asunto del Documento</td>
							<td align="left" colspan="3" width="17%" class="label">
							<div dojoType="dijit.form.ValidationTextBox"
														id="objDocumento.asunto" jsId="objDocumento.asunto" name="objDocumento.asunto"
														invalidMessage="Ingrese un Asunto" required="false"
														style="width: 300px" trim="true" /></td>
							<td align="left" class="label"></td>
							<td align="left" class="label"></td>
							<td></td>
						</tr>


						<tr>
							<td></td>
							<td height="16" align="left">Fecha Documento</td>
												<td colspan="3" align="left" class="label"><input
													id="fechaDocumento" type="text" /> <%--input name="objDD.strFechaDocumento" value="<s:date name="objDocumento.fechadocumento" format="dd/MM/yyyy"/>" id="fechadocumento" class="cajaMontoTotal" size="25" />
                                    <input alt="Calendario" class="cajaFecha" id="calfechadocumento" value="..." type="button" /--%>

                                 </td>
							<td></td>
						</tr>
						
						<tr>
							<td></td>
							<td height="16" align="left">Observaciones </td>
							<td align="left" colspan="3" width="17%" class="label">
							<div dojoType="dijit.form.Textarea"
								 id="objDocumento.observacion" jsId="objDocumento.observacion" name="objDocumento.observacion"
								 style="width: 300px" trim="true" /></td>
							<td align="left" class="label"></td>
							<td align="left" class="label"></td>
							<td></td>
						</tr>
						
						<tr>
							<td></td>
							<td class="titulo">&nbsp;</td>
							<td colspan="4"></td>
						</tr>
						
						<tr> 
							<td></td>
							<td height="16" align="left">
							<div id="checkenumerar" style="display: none;"> 
														<input type="checkbox" id="objDocumento.enumerarDocumento"
															onclick="chknroDoc()" name="enumerarDocumento" value="S" checked/>
								Enumerar 
													</div></td>
							<td align="left" colspan="3" width="17%" class="label"> 
							<div id="textonrodoc" style="display: none;" > 
							<div dojoType="dijit.form.ValidationTextBox"
                                  id="objDocumento.numeroDocumento"
                                  jsId="objDocumento.numeroDocumento"
                                  name="objDocumento.numeroDocumento"
															invalidMessage="Ingrese el Autor" required="false"
															style="width: 250px" trim="true" />
													</div></td>
							<td align="left" class="label"></td>
												<td align="left" class="label"><input type="hidden"
													id="tiponumeracion" name="tiponumeracion" /></td>
							<td></td>
						</tr>
						
	                    <%-- <s:if test="iddestinatario == null || iddestinatario == 0"> --%>

							<%-- </s:if> --%>
							<tr id="dest1" style="display: none;">
								<td></td>
												<td height="16" align="left" class="plomo">Destinatarios
													(opcional):</td>
												<td colspan="4"><input id="iddestinatarios" /></td>
								
							</tr>							
							<tr id="dest2" style="display: none;">
								<td></td>
												<td height="16" align="left" class="plomo">Destinatarios
													(opcional):</td>
												<td><div id="fsNroIdentificacion1"
														name="fsNroIdentificacion1" />
												</td>
							</tr>
							<tr  id="dest3" style="display: none;">
								<td colspan="2"></td>
								<td colspan="4">
													<div id="destinatarios"></div></td>
							</tr>	
							<tr id="copy1" style="display: none;">
								<td></td>
												<td height="16" align="left" class="plomo">Con Copia
													A(opcional)</td>
												<td colspan="4"><input id="idcopias" /></td>
							</tr>													
							<tr id="copy2" style="display: none;">
								<td></td>
												<td height="16" align="left" class="plomo">Con Copia
													A(opcional):</td>
												<td><div id="fsNroIdentificacion2"
														name="fsNroIdentificacion2" />
												</td>
							</tr>
							<tr id="copy3" style="display: none;">
								<td colspan="2"></td>
								<td colspan="4">
													<div id="copias"></div></td>
							</tr>
					</table>
					</s:if>
					</td>
				</tr>
			</table>
               </s:form>
            </td> 
	</tr>
	<tr>
				<td height="14">&nbsp;</td>
	</tr>
</table>
      <%@ include file="../util/progressBar.jsp" %>    
</body>
</html>