<?xml version="1.0" encoding="UTF-8" ?>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es"> 
	<head>
		<title>Cambiar Plazo</title>
		<link rel="stylesheet" type="text/css" href="css/estilo.css" />
		<style type="text/css">
			@import "js/dojo/dijit/themes/soria/soria.css";
			@import "css/estilo.css";
		</style>
		
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
		   
		 var storeTipoDocumento = new dojo.data.ItemFileReadStore({url: "autocompletarAllTipoDocumento.action"}); 
		 var store = new dojo.data.ItemFileReadStore({url: "<s:property value='autoUrl'/>?sTipoDerivacion=normal&sOpcion=derivar" })     ;

		
		dojo.addOnLoad(function(){
				new dijit.form.DateTextBox({ 
					name			: "nuevaFechaLimite",
					constraints		: {datePattern:"dd/MM/yyyy"},
					invalidMessage	: "Ingrese una fecha con el formato dd/mm/yyyy",
					required		: true
				},"fechadocumentox");
 
	          /*
				try{
				dijit.byId("fechadocumento").attr("value",fecha);
				}catch(err){
				console.debug("error: "+err); 
				}*/
			}); 
			
		
		

      function aceptar () {   
          
            document.forms[0].action = "Seguimiento_cambiarPlazo.action";
            document.forms[0].submit() ;

      }
       
      function cancelar() {   
		    window.close();
      }

      function refrescar (){     
    	    window.opener.filtrarSeguimiento() ;  
	        window.opener.abrirPagina('<s:property value="trazabilidad.documento.iddocumento"/>?avisopermiso=2&idtrazabilidaddocumento=<s:property value="trazabilidad.idtrazabilidaddocumento"/>');
            window.close();            
      }

      function seleccionar_Todo(){
         for (i=0;i<document.forms[0].elements.length;i++)
            if(document.forms[0].elements[i].type == "checkbox")
               document.forms[0].elements[i].checked=1;
      }
      function deseleccionar_Todo(){
         for (i=0;i<document.forms[0].elements.length;i++)
            if(document.forms[0].elements[i].type == "checkbox")
               document.forms[0].elements[i].checked=0;
      }
      function Abrir_ventana (pagina) {
         var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=440, height=350, top=50, left=200";
         window.open(pagina,"",opciones);
      }

      function Abrir_pagina (pagina) {
         var opciones="location=mainFrame";
         window.open(pagina,"",opciones);
      }
      function Abrir_ventanaDerivar (pagina) {
         var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=no, width=900, height=640, top=20, left=70";
         window.open(pagina,"",opciones);
      }
      function Abrir_ventanaSeguimiento (pagina) {
         var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=no, width=800, height=400, top=50, left=100";
         window.open(pagina,"",opciones);
      }

      function Abrir_ventanaObservacion (pagina) {
         var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=no, width=650, height=400, top=50, left=200";
         window.open(pagina,"",opciones);
      }

      function ejecAprob(){
         if (confirm("Desea aprobar el expediente EXP001")){
            location.href="aprobar.html";
         }
      }
      function ejecRech(){
         if (confirm("Desea rechazar el expediente EXP001")){
            location.href="rechazar.html";
         }
      }

      function ejecutar(){
         if (confirm("Desea aprobar el expediente EXP001")){
            location.href="blank.html";
         }
      }
   
      function poorman_toggle(id)
      {
         var tr = document.getElementById(id);
         if (tr==null) { return; }
         var bExpand = tr.style.display == '';
         tr.style.display = (bExpand ? 'none' : '');
      }

      function poorman_changeimage(id, sMinus, sPlus)
      {
         var img = document.getElementById(id);
         if (img!=null)
         {
            var bExpand = img.src.indexOf(sPlus) >= 0;
            if (!bExpand)
               img.src = sPlus;
            else
               img.src = sMinus;
         }
      }

      function Toggle_repHeader2()
      {
         poorman_changeimage('repHeader2_Img', 'images/signo_menos.gif', 'images/signo_mas.gif');
         poorman_toggle('row1');
         poorman_toggle('row2');
         poorman_toggle('row3');
      }

      function Toggle_repHeader1()
      {
         poorman_changeimage('repHeader1_Img', 'images/signo_menos.gif', 'images/signo_mas.gif');
         poorman_toggle('trRow1');
      }
      function Toggle_repHeader3()
      {
         poorman_changeimage('repHeader3_Img', 'images/signo_menos.gif', 'images/signo_mas.gif');
         poorman_toggle('row1');
         poorman_toggle('row2');
         poorman_toggle('row3');
      }

      function Toggle_repHeader3()
      {
         poorman_changeimage('repHeader3_Img', 'images/signo_menos.gif', 'images/signo_mas.gif');
         poorman_toggle('trRow3');
      }
   
      <%--function treeNodeSelected(iIdDoc) {

         // alert(iIdDoc.node.widgetId);

         dojo.io.bind({
            url: "/siged/doViewTrazabilidad.action",
            content: {
            iIdDocumento: iIdDoc.node.widgetId
            },
            load: function(type, data, evt) {
               var displayDiv = dojo.byId("displayId");
               displayDiv.innerHTML = data;
            },
            mimeType: "text/html"
         });
      };

      dojo.event.topic.subscribe("treeSelected", this, "treeNodeSelected");--%>
		</script>

		<meta http-equiv="Content-Type" content="text/html; utf-8" />
		<style type="text/css">
      <!--
      body {
         background-color: #ffffff;
      }

      .barra {
         scrollbar-3dlight-color:#CCCCCC;
         scrollbar-arrow-color:#568BBF;
         scrollbar-base-color:#C3D5E9;
         scrollbar-darkshadow-color:#666666;
         scrollbar-face-color:;
         scrollbar-highlight-color:#669BCD;
         scrollbar-shadow-color:#999999;
      }

      div.falso {
         position: absolute;
         top: -29px;
         left: 335px;
         z-index: 0;
         width: 14px;
         height: 3px;
      }

      input.file {
         position: relative;
         text-align: left;
         filter:alpha(opacity: 0);
         opacity: 0;
         z-index: 1;
         top: -29px;
         left: 305px;
      }
      -->
		</style>
	</head>
	<body  class="soria"
	 <s:if test="cerrar!=null">  
	      onload="refrescar()"
	 </s:if>>
		<form action="">           
			<s:hidden name="idDocumento" />   
			<s:hidden name="location" /> 
			<s:hidden name="idExpediente" /> 
			<s:textfield name="idtrazabilidaddocumento" cssStyle="display:none" />
			
			<table width="100%">
				<tr>
					<td></td>
				</tr>
					
					<tr align="center">
						
						<td width="98%" colspan="6" align="left"></td>
					</tr>
					<tr>  
					
						<td height="400" colspan="6" class="titulo" width="97%"  align="left">
							<table width="80%"  height="100%" align="center"  cellpadding="0" cellspacing="0" valing="top">
								<!--DWLayoutTable-->
								<tr>
									<td width="65%" style="height:auto" border="3"  borderColor="#6487d4"  valign="top" >

										<table width="103%">
											<tr> 
												<td height="13" colspan="2" class="header" >
													<div align="center">
														Cambiar Plazo
													</div>
												</td>
											</tr>
											<tr> 
												<td height="13" colspan="2" class="header" >

												</td>
											</tr>
											<tr>
												<td colspan="2">
													<table cellpadding="1" cellspacing="1" class="tableForm" width="90%" align="center" border="0">

														
														<tr   class="altRow2" style="cursor:hand" >
														       
															<td> 
															  
																Fecha Limite
															</td>
															<td> 
																   <s:date name="trazabilidad.fechalimiteatencion"  format="dd/MM/yyyy"   /> 
																 
															</td>
														</tr>
														
														<tr   class="altRow2" style="cursor:hand" >
														   
															<td> 
															     Nueva Fecha Limite
															</td>
															<td> 
																  <input id="fechadocumentox" type="text" />
																 
															</td>
														</tr>
														
													</table>
												</td>
											</tr> 
											<tr align="center" height="60px"> 
												<td width="100%"  align="center">	
											</td>
											</tr>
											<tr align="center"> 
												<td width="100%"  align="center">
												     
													<input name="button"  type="button" class="button" onClick="javascript:aceptar()"  value="Aceptar" />&nbsp;
													<input name="button"  type="button" class="button" onClick="javascript:cancelar()"  value="Cancelar" />
												</td>
											</tr>
											<tr> 
												<td colspan="2" align="center"></td>
											</tr>
										</table>
									<!-- fin trazabilidad-->
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</form> 
	</body>
</html>