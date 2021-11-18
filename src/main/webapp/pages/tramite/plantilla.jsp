<?xml version="1.0" encoding="UTF-8" ?>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es">
	<head>
		<meta http-equiv="Pragma" content="no-cache" />
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>  
		<link rel="stylesheet" type="text/css" href="css/estilo.css" />
		<link rel="stylesheet" type="text/css" href="css/sigedIconos.css" />
		<s:if test="archivopendiente==null">
		<title>Nuevo Documento - Plantilla</title>		
		<link rel="stylesheet" type="text/css" href="js/dojo/dijit/themes/soria/soria.css" />
		<script type="text/javascript" src="js/dojo/dojo/dojo.js"></script>
		<script type="text/javascript">
			var djConfig = {
				isDebug:false,
				parseOnLoad:false
			};
			dojo.require("dijit.form.FilteringSelect");
			dojo.require("dijit.form.Button");
			dojo.require("dijit.form.Form");
			dojo.require("dojo.data.ItemFileReadStore");
			dojo.require("dijit.form.DateTextBox");
			dojo.require("dijit.form.ValidationTextBox");
			 
		</script>
	
		</s:if>
		<s:else>
		<title>Modificar Documento - Plantilla</title>
		
		</s:else>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/plantilla.js"></script>
		<script type="text/javascript" > 
		    var unidadActual = "<s:property value='documento.expediente.proceso.responsable.unidad.idunidad' />" ;
          console.debug("iIdUnidad [%s]", unidadActual);
		    var numero = "" ;
	        <s:if test="archivopendiente==null">
			 var refresh = false ;
			 var iddocumento = <s:property value ="idDocumento" /> ;
			 var iddefaultfirmante  =<s:property  value='#session._USUARIO.idusuario' /> ;
			 var idusuariosession  =<s:property  value='#session._USUARIO.idusuario' /> ;
			 var defaultautor  ="<s:property  value="#session._USUARIO.nombres" escape="false" /> <s:property  value="#session._USUARIO.apellidos" escape="false" />" ;
			 var last = -1 ;
			  
			</s:if>
			<s:else>
			 var refresh = true ; 
			 var firm = "<s:property value="archivopendiente.firmante.idusuario"/>" ; 
			 var idusuariosession  =<s:property  value='#session._USUARIO.idusuario' /> ; 
			 var iddefaultfirmante  =<s:property  value="archivopendiente.firmante.idusuario" /> ;
			 var defaultautor  = '<s:property  value="archivopendiente.autor" escape="false" />' ;
			 numero ="<s:property  value="archivopendiente.numeroDocumento"/>";
			 var enumer =   "<s:property value="archivopendiente.enumerarDocumento"/>" ;
			// alert(numero);         
			 if(enumer){
				  document.getElementById("archivopendiente.enumerarDocumento").checked = true;
			 }
			// var tipnum = "<s:property value="archivopendiente.tiponu"/>" ; 
			 var last = 0 ;
			</s:else>	  
				
	         var a=parseInt(0<s:date name='archivopendiente.fechaDocumento' format='yyyy' />);
		     var m=parseInt(0<s:date name='archivopendiente.fechaDocumento' format='MM' />);
		     var d=parseInt(0<s:date name='archivopendiente.fechaDocumento' format='dd' />);
		     var fecha = null  ;
		     
		     
		     if(a!=0&&m!=0&&d!=0){
			     
		    	  fecha=new Date(a,m-1,d); 
		    	  
			 }else {
				 
				  fecha =  new Date () ; 
				   
			 }


		     var agregar=function(tipo, compartido){
					var nombre=document.getElementById("id"+tipo).value;
					var id=dijit.byId("id"+tipo).getValue();
							
					if(nombre!=null && nombre!="" && id!=null && id!=""){
						var existe=false;
						var conCopia=document.getElementsByName("con"+tipo);
						//"#copias span input[name='conCopia']"
						$(conCopia).each(function(){
							if($(this).val()==id)
								existe=true;
						}); 
						if(compartido!=""){

							var conCopia2=document.getElementsByName("con"+compartido);
							//"#copias span input[name='conCopia']"
							$(conCopia2).each(function(){
								if($(this).val()==id)
									existe=true;
							}); 
							
							}
						
						
						if(!existe){
							var copia="<span class=\"copia\">";
								copia+="<span>"+nombre+"</span>";
								copia+="<input type=\"hidden\" name=\"con"+tipo+"\" value=\""+id+"\" />";
								copia+="<a href=\"#\" class=\"quitar\"><img src=\"images/eliminar.gif\" alt=\"X\" /></a></span>";
							$("#"+tipo).append(copia);
							$(".quitar").click(function(){
								$(this).parent().remove();
							});
						}
					}
				}; 
			 

		     var agregarDestinatario = function(){ 
					agregar("destinatarios","copias");
				}

				var agregarCopia = function(){ 
					agregar("copias","destinatarios"); 
				}
		     
		     var strDerivarCC = new dojo.data.ItemFileReadStore({url: "derivarCC.action"});
		 	
		 	dojo.addOnLoad(function(){
				new dijit.form.FilteringSelect({
					store			: strDerivarCC,
					searchAttr		: "label",
					labelAttr		: "label",
					idAttr			: "ids",
					required		: false,
					hasDownArrow	: true,
					highlightMatch	: "all",
					onChange		: agregarDestinatario
				},"iddestinatarios");
	        }); 
	        
			dojo.addOnLoad(function(){
				new dijit.form.FilteringSelect({
					store			: strDerivarCC,
					searchAttr		: "label",
					labelAttr		: "label",
					idAttr			: "ids",
					required		: false,
					hasDownArrow	: true,
					highlightMatch	: "all",
					onChange		: agregarCopia
				},"idcopias");
	        }); 
			 
		</script>
	</head>

	<body class="soria">
	<%--s:if test="cerrar!=null">   
      onload="  if('<s:property value ="mensaje" />'!=''){  alert('<s:property value ="mensaje" />');  }  window.opener.location.href ='/siged/doViewDoc.action?iIdDoc='+<s:property value="idDocumento"/> ; window.close(); "
    </s:if--%>
		<form id="form" action="doPlantilla_guardarPendiente.action" method="post">
			<table style="width: 100%;">
				<tr>
					<td colspan="3" style="height: 14px;"><s:hidden name="idDocumento" /></td>
				</tr>    
				<tr>
					<td height="20" colspan="3" class="titulo">
						<table width="99%" border="0" height="20" align="center" bgcolor="#A2C0DC">
							<tr>
								<s:if test="archivopendiente==null">
								<td align="left" class="titulo">Nuevo Documento</td>
								</s:if>
								<s:else>
								<td align="left" class="titulo">Modificar Documento</td>
								</s:else>								
							</tr>
						</table>
					</td>
				</tr>
				<tr align="left">
					<td width="1%"></td>
					<td width="99%" colspan="2" align="left" style="text-align: left;">
						<button id="registrar">Registrar</button>
						<button id="guardar">Guardar</button> 
						
					</td>
				</tr>
				<tr>
					<td colspan="1" width="5"></td>
					<td height="16" colspan="4" align="left" class="plomo"></td>
				</tr>
				<tr>
					<td height="14" colspan="3">
						<table width="95%"  border="1" align="center" bordercolor="#669BCD" bgcolor="#BFD9F1">
							<tr>
								<td height="75">
									<table width="98%" height="97" align="center">
										<tbody id="tabla">
											<tr id="plano9">
												<td></td>
												<td class="titulo"></td>
												<td colspan="4"></td>
											</tr>
											<tr id="plano8">
												<td width="2%">
													<s:hidden name="objDD.iIdExpediente" />
													<s:hidden name="objDD.iIdResponsable" />
													<s:hidden name="idDocumento" />
													<s:hidden name="idArchivoPendiente" />
													<s:hidden name="cerrar" />
												</td>    
												<td width="28%" height="5">
												<s:if test="idDocumento!=null">
													Expediento Nro.
												</s:if>
												</td>
												<td width="17%">
												<s:if test="archivopendiente==null" >
													<s:if test="idDocumento!=null">
													<s:property value="documento.expediente.nroexpediente" />
													</s:if>
												</s:if>
												<s:else>
													<s:property value="archivopendiente.documento.expediente.nroexpediente" />
												</s:else>
												</td>
												<td colspan="2" height="15" class="label"></td>
												<td width="3%"></td>
												<td width="3%"></td>
											</tr>											
											<tr id="plano84">
												<td></td>
												<td height="16" align="left">Proceso</td>
												<td colspan="3">
												<s:if test="archivopendiente==null">
													<s:if test="idDocumento!=null">
													<s:property value="documento.expediente.proceso.nombre" />
													</s:if>
													<s:else>
													<select id="idproceso"></select>
													<%--s:textfield id="proceso" name="proceso" cssClass="cajaMontoTotal" size="40" />
													<span id="indicatorDep" style="display:none;"><img src="./images/indicator.gif"/></span>
													<s:textfield id="idproceso" name="idproceso"  cssStyle="display:none"/>
													<ajax:autocomplete source="proceso" target="idproceso" baseUrl="./autocompletar.view?metodo=ObtenerProceso" className="autocomplete" indicator="indicatorDep" minimumCharacters="1" parser="new ResponseXmlToHtmlListParser()" /--%>
													</s:else>
												</s:if>
												<s:else>
													<s:property value="archivopendiente.documento.expediente.proceso.nombre" />
												</s:else>
												</td>
												<td></td>
											</tr>
											<tr id="plano52">
												<td width="2%" ></td>
												<td colspan="7" height="15" class="label" style="display: none">
												<s:iterator value="listaParametros">
													<input type="radio" name="marcaDeAgua"  value="<s:property value="valor"/>" <s:if test="valor==marcaDeAgua" > checked="checked" </s:if> /> <s:property value="descripcion"/>
												</s:iterator>
												</td>
												<td width="3%"></td>
												<td width="3%"></td>
											</tr>
											<tr id="plano52">
											    <td></td>
												<td height="16" align="left">
												     Asunto:      
												</td>
												<td width="3%">
												<s:textfield id="asunto"    name="asunto" cssClass="cajaMontoTotal" size="20"  maxlength="500"/>
												</td>     
												<td width="3%"></td>
											</tr>
											<tr id="plano25">
												<td></td>
												<td height="16" align="left">Tipo Documento</td>
												<td align="left" width="17%"  colspan="3"  class="label">
													<select id="tipoDocumento"></select>
													<s:if test="archivopendiente!=null">
													<s:hidden id="tipoPlantilla" name="archivopendiente.plantilla.idplantilla" />
													</s:if>
												<%--s:if test="archivopendiente==null" >
													<s:textfield id="tipodocumento"  onblur="cargarPlantilla();"  name="tipodocumento" cssClass="cajaMontoTotal" size="20" />
												</s:if>
												<s:else>
													<s:textfield id="tipodocumento" value="archivopendiente.plantilla.descripcion"  onblur="cargarPlantilla();"  name="tipodocumento" cssClass="cajaMontoTotal" size="20" />
												</s:else>
													<span id="indicatortipoDoc" style="display:none;"><img src="./images/indicator.gif" /></span>
												<s:if test="archivopendiente==null">
													<s:hidden id="idtipodocumento" name="idtipodocumento" />
												</s:if>
												<s:else>
													<s:textfield id="idtipodocumento" value="archivopendiente.idplantilla.idplantilla" name="idtipodocumento" cssStyle="display:none" />
												</s:else>
													<ajax:autocomplete source="tipodocumento" target="idtipodocumento" baseUrl="./autocompletar.view?metodo=ObtenerTipoDoc" className="autocomplete" indicator="indicatortipoDoc" minimumCharacters="1" parser="new ResponseXmlToHtmlListParser()" /--%>
												</td>
												<td align="left" class="label"></td>
												<td align="left" class="label"></td>
												<td></td>
											</tr>											
										
										 
										   <tr id="firmante" style="display:none;">
												<td></td>
												<td height="16" align="left">Firmante </td>
												<td align="left" width="17%" colspan="3" class="label">
												  
												<div  id="archivopendiente.firmante.idusuario" />
												
												    
												</td>
												<td align="left" class="label"></td>
												<td align="left" class="label"></td>
												<td></td>
											</tr>
											
											
											<tr id="plano64">
												<td></td>
												<td height="16" align="left">Autor </td>
												<td align="left" colspan="3" width="17%" class="label">
												<div id="archivopendiente.autor"/>   
												</td>
												<td align="left" class="label"></td>
												<td align="left" class="label"></td>
												<td></td>
											</tr>
											
											
					                      
											<tr id="plano65">
												<td></td>
												<td height="16" align="left">Fecha Documento</td>
					                                 <td colspan="3" align="left" class="label">
														<input id="fechadocumentopendiente" type="text" />
														<%--input name="objDD.strFechaDocumento" value="<s:date name="objDocumento.fechadocumento" format="dd/MM/yyyy"/>" id="fechadocumento" class="cajaMontoTotal" size="25" />
					                                    <input alt="Calendario" class="cajaFecha" id="calfechadocumento" value="..." type="button" /--%>
					 							
					                                 </td> 
					                                 
												<td></td>
											</tr>
											
											<tr id="plano66"> 
												<td></td>
												<td height="16" align="left">
												<div id="checkenumerar" style="display: none"> 
												<input type="checkbox"  id="archivopendiente.enumerarDocumento" name="enumerarDocumento"  onclick="chknroDoc()"/>
												Enumerar  
												</div> 
												</td>   
												<td align="left" colspan="3" width="17%" class="label"> 
												<div id="textonrodoc" style="display: none;" >   
												
					                             <input id="archivopendiente.nroDocumento" type="text" maxlength="50"  value=numero />   
					                            </div>
												</td>  
												<td align="left" class="label"></td>
												<td align="left" class="label"></td>
												<td></td>
											</tr> 
											
											<s:if test="archivopendiente!=null">
											<s:iterator value="archivopendiente.valoresCampo">
											<tr>
												<td></td>
												<td><span id="descripcion"><s:property value="campo.descripcion" /></span></td>
												<td id="valor" class="titulo">
												<s:if test="campo.tipo=='TX'" >
													<input type="text" name="valor<s:property value="campo.idCampo" />" value="<s:property value="valor" />" class="cajaMontoTotal" />
												</s:if>
												<s:elseif test="campo.tipo=='TA'">
													<textarea name="valor<s:property value="campo.idCampo" />" cols="50" rows="10"  class="cajaMontoTotal"><s:property value="valor" /></textarea>
												</s:elseif>
												</td>
											</tr>
											</s:iterator>
										</s:if>
											<%--tr id="campo" style="display: none;">
												<td><span id="descripcion"></span></td>
												<td id="valor" class="titulo"></td>
												<td colspan="3"></td>
											</tr>
											<tr>
												<td></td>
												<td class="titulo"></td>
												<td colspan="4"></td>
											</tr--%>
											
							<tr id="dest1" style="display: none;">
								<td></td>
								<td height="16" align="left" class="plomo">Destinatarios (opcional):</td>
								<td colspan="4">
									<input id="iddestinatarios" />								
	                 			</td>
								
							</tr>
							
							<tr  id="dest2"  style="display: none;">
								<td></td>
								<td ></td>
								<td >
									<div id="destinatarios"></div>
								</td>
							</tr>
							
							<tr id="copy1"   style="display: none;">
								<td></td>
								<td height="16" align="left" class="plomo">Con Copia A(opcional)</td>
								<td colspan="4">
									<input id="idcopias" />									
	                 
								</td>
								
							</tr>
							<tr  id="copy2"  style="display: none;">
								<td></td>
								<td></td>
								<td>
									<div id="copias"></div>
								</td>
							</tr>										
											
										</tbody>
									</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</form>
		<script type="">
		
		</script>
	</body>
</html>