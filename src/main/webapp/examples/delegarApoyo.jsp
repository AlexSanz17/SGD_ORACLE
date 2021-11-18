<?xml version="1.0" encoding="UTF-8" ?>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es">
	<head>
	  <title>Enviar M&uacute;ltiple</title>
      <link type="text/css" rel="stylesheet" href="css/styleSiged.css" />
      <link type="text/css" rel="stylesheet" href="js/dojo/css/styleDojo.css" />
		<style type="text/css">
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
            filter:alpha(opacity: 0);
            opacity: 0;
            z-index: 1;
            /*top: -49px; 
            text-align: left;
            left: 80px;*/
            width: 50px;
            left: 10px;
         }
         .copia{
         	margin-right: 5px;
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
		<script type="text/javascript" src="js/siged/siged.js"></script>
      	<script type="text/javascript" src="js/siged/siged.forms.js"></script>
		<script type="text/javascript" src="js/siged/siged.string.js"></script>
      	
		<script type="text/javascript">
			dojo.require("dijit.Editor");
	        dojo.require("dijit._editor.plugins.FontChoice");  
	        dojo.require("dijit._editor.plugins.TextColor");  
	        dojo.require("dijit._editor.plugins.LinkDialog");
	        dojo.require("dojo.io.iframe");
	        dojo.require("dojo.rpc.JsonService");
	        dojo.require("dijit.form.TextBox");
	        dojo.require("dijit.form.Button");	
			
         	var enviando=false;
         	
			function delegar(){
				if(!enviando){
					/*if(dojo.byId("iddestinatario").value == ""){
						alert("Debe seleccionar un destinatario");
						return;
					}*/
					enviando=true;
					document.getElementById("objDD.strTexto").value = dijit.byId("contenid").getValue(false);
					dijit.byId("dlgProgresBar").show();
					document.getElementById("delegar").submit();
				}
			}
			
			var filterPara = function(e) {
			   	dijit.byId("grdPara").setQuery({nombres : "*" + this.attr("value") + "*"}, {ignoreCase: true});
			};

			var resetPara = function() {
				dijit.byId("txtFiltroPara").attr("value", "");
			   	dojo.byId("txtFiltroPara").focus();
			   	dijit.byId("grdPara").showMessage("");
			   	dijit.byId("grdPara").setStore(new dojo.data.ItemFileReadStore({url: "buscarPara.action?mode=" + MODE}));
			   	dijit.byId("grdPara").focus.next();
			   	dijit.byId("grdPara").render();
			};

			var selectContactoFromGrdPara = function(e) {
				if (!gDoubleClick) {
			    	if (e.rowIndex == undefined) {
			        	return;
			      	}
			    	
			    	var id = dijit.byId("grdPara").getItem(e.rowIndex).id;
			      	var nombre = dijit.byId("grdPara").getItem(e.rowIndex).nombres;
			      	
			      	if(nombre!=null && nombre!="" && id!=null && id!=""){
			            var existe=false;
			            var conCopia=document.getElementsByName("conCopia");
			            //"#copias span input[name='conCopia']"
			            $(conCopia).each(function(){
			               if($(this).val()==id)
			                  existe=true;
			            });
			            if(!existe){
			               var copia="<span class=\"copia\">";
			               copia+="<span>"+nombre+"</span>";
			               copia+="<input type=\"hidden\" name=\"conCopia\" value=\""+id+"\" />";
			               copia+="<a href=\"#\" class=\"quitar\"><img src=\"images/eliminar.gif\" alt=\"X\" /></a></span>";
			               $("#copias").append(copia);
			               $(".quitar").click(function(){
			                  $(this).parent().remove();
			               });
			            }
			         }
			      	
			      	gDoubleClick = true;
				}
			   	dijit.byId("dlgPara").hide();
			};
			
			var showGrdPara = function() {
				gDoubleClick = false;
			   	dijit.byId("dlgPara").show();
			   	dijit.byId("txtFiltroPara").attr("value", "");
			   	dijit.byId("grdPara").showMessage("");
			   	dijit.byId("grdPara").setStore(new dojo.data.ItemFileReadStore({url: "buscarPara.action?mode=delegar"}));
			   	dijit.byId("grdPara").focus.next();
			   	dijit.byId("grdPara").render();
			}; 
			
			function refrescar() {
	        	try {
					window.opener.eliminarRegistro();
					window.opener.filtrarSeguimiento(); 
	          	}catch(err){ 
	     			this.window.close();
	           	}
				if (provieneDeMail == "true") {
	               	window.opener.close();
	            }
	           	this.window.close();
	     	}
		</script>
	</head>
	<body class="soria" <s:if test='cerrar!=null'>onload="refrescar();"</s:if>>
		<s:if test="cerrar==null">
			<table width="100%">
				<tr>
					<td height="14" colspan="6" style='padding:6px 8px;font-weight:bold'><s:property value="fechaEnTexto"/></td>
				</tr>
				<tr align="center">
					<td width="1%" align="center"></td>
					<td colspan="4" align="left">
						<img src="images/enviar.bmp" alt="Enviar" style="float: left;" onclick="javascript:delegar();" />
				</tr>
				<tr>
					<td height="16" colspan="5" align="left" class="plomo">
						<form id="delegar" action="delegarApoyo.action" method="post">
							<s:hidden name="iIdDoc" />
							<s:hidden name="sOpcion" />
	                  		<s:hidden name="objDD.iIdRemitente" />
							<table width="100%">
								<tr>
									<td></td>
									<td width="27%" height="16" align="left" class="plomo">
										<button dojoType="dijit.form.Button" onClick="showGrdPara">Para</button>
									</td>
									<td colspan="2">
	                              		<%--<input type="hidden" id="iddestinatario" name="iddestinatario" />
	                              		<input type="text" id="nombredestinatario" dojoType="dijit.form.TextBox" readonly="true" /> --%>
	                              		<div id="copias"></div>
									</td>
								</tr>
								<tr>
									<td></td>
									<td height="16" align="left" class="plomo">Asunto : </td>
									<td colspan="4">
										<input type="text" dojoType="dijit.form.TextBox" id="asunto" name="objDD.strAsunto" style="width:400px;" maxlength="300" value="<s:property value='objDD.strAsunto'/>"/>
									</td>
								</tr>
								<tr><td>&nbsp;</td></tr>
								<tr>
									<td>&nbsp;</td>
									<td colspan="5" class="normal">
										<s:hidden id="objDD.strTexto" name="objDD.strTexto" />
	                          			<input type="text" name="ta" id="ta" value="<s:property value='ta' escape='false' />" style="display:none;">
										<div dojoType="dijit.Editor" jsid="contenid"  id="contenid" style="background-color: #FFF;" >
										</div>
										<script type="text/javascript">
										   var cadena  = document.getElementById("ta").value;
										   cadena = cadena.replace(/<P><\/P>/g,"");
										   document.getElementById("contenid").innerHTML = cadena.substring(0,3999);
										</script> 
									</td>
								</tr>
							</table>
						</form>
					</td>
				</tr>
				<tr>
					<td height="14" colspan="6"></td>
				</tr>
			</table>
		</s:if>
		<s:else>
			<p style="text-align: center;">Operaci&oacute;n realizada satisfactoriamente</p>
		</s:else>
		<%@ include file="../pages/util/progressBar.jsp"%>
		<%@ include file="derivar-dialogs.jsp" %>
	</body>
</html>