<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es">
    <head>
        <title>Firmar Documento</title>
        <link rel="stylesheet" type="text/css" href="css/estilo.css" />
        <script type="text/javascript" src="js/dojo/dojo/dojo.js" djConfig="parseOnLoad:true, isDebug: false"></script>
        <script type="text/javascript">
            var isPosting = false;
            var idIntervalo;
            
            function aceptar () {
            	var cont = 0;
                try{
                    var checkboxes=document.getElementById("firmarForm").documentosPorEnumerar;
              	    for (var x=0; x < checkboxes.length; x++) 
              	    {
          	    	  if (checkboxes[x].checked) {
               	      	cont = cont + 1;
      	        	   }
              	    }
             	    if(checkboxes.checked==true){
             	    	cont=1;
             	    }                
                }
              catch(err){  }         	    

              try{
                    var checkboxes=document.getElementById("firmarForm").documentosPorFirmar;
              	    for (var x=0; x < checkboxes.length; x++)
              	    {
          	    	  if (checkboxes[x].checked) {
               	      	cont = cont + 1;
      	        	   }
              	    }
             	    if(checkboxes.checked==true){
             	    	cont=1;
             	    }
                }
              catch(err){  }

            if(cont>0){
		            if(confirm("Esta seguro que quiere Firmar?")){
		            	
		                if (!isPosting) {
		                    isPosting = true;
		                    document.getElementById("botones").style.display = 'none';
		                    document.getElementById("procesandoDiv").style.display = '';
		                    dojo.xhrPost({
		                        url: "Numeracion_guardarNumeracionFirma.action",
		                        form: dojo.byId("firmarForm"),
		                        mimetype: "text/html",
		                        load: function() {
		                            idIntervalo = setInterval(function() {
		                                dojo.xhrGet({
		                                    url: "Numeracion_modificacionEnProceso.action",
		                                    handleAs: "json",
		                                    load: function(data) {
		                                        if (!data.enProceso) {
		                                            clearInterval(idIntervalo);
		
		                                            var iddoc = document.getElementById('idDocumento');
		                                            //window.opener.viewDetailCustom2(iddoc.value);
		                                            alert(data.mensaje);
		                                            window.close();
		                                        }
		                                    }
		                                });
		                            }, 4000);
		                        }
		                    });
		                }
		            }
            	}else{
            		alert("Debe seleccionar al menos un archivo para firmar.");
            	}
            }
            function cancelar() {
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
      
        </script>

        <meta http-equiv="Content-Type" content="text/html; utf-8" />
    </head>
    <body class="barra">
        <form id="firmarForm" action="">
            <s:hidden name="idDocumento" />
            <s:hidden name="location" />
            <s:hidden name="idExpediente" />

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
														Documentos a Firmar
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
                                                    <tr class="header">
                                                        <th width="12%"class="data">Enumerar</th>
                                                        <th width="12%"class="data">Firmar</th>
                                                        <th width="12%"class="data">Tipo</th>
                                                        <th width="12%"class="data">Fecha Creaci&oacute;n</th>
                                                        <th width="12%"class="data">Asunto</th>
                                                        <th width="12%"class="data">Autor</th>
                                                    </tr>
                                                    <s:iterator  value="listaDocumentos" >
                                                        <tr   class="altRow2" style="cursor:hand" >
                                                            <td>
                                                                <div align="center">
                                                                    <s:if test="enumerado == 'N'">
                                                                      <input type="checkbox" name="documentosPorEnumerar" value="<s:property value="idDocumento"/>" />
                                                                    </s:if>
                                                                </div>
                                                            </td>
                                                            <td>
                                                                <div align="center">
                                                                   <s:if test="firmado == 'N'">
                                                                      <input type="checkbox" name="documentosPorFirmar" value="<s:property value="idDocumento"/>" />
                                                                   </s:if>
                                                                </div>
                                                            </td>
                                                            <td>
                                                                <s:property value="tipoDocumento.nombre" />
                                                            </td>
                                                            <td>
                                                                <s:date name="fechaCreacion" format="dd/MM/yyyy HH:mm:ss"   />
                                                            </td>
                                                            <td>
                                                                <s:property value="asunto" />
                                                            </td>
                                                            <td>
                                                                <s:property value="autor" />
                                                            </td>
                                                        </tr>
                                                    </s:iterator>
                                                </table>
                                            </td>
                                        </tr>
                                        <tr align="center" height="60px">
                                            <td width="100%"  align="center">
                                            </td>
                                        </tr>
                                        <tr align="center">
                                            <td width="100%"  align="center">

                                                <div id="procesandoDiv" style="display: none">
                                                    Procesando...
                                                </div>
                                                <div id="botones">
                                                    <input name="button"  type="button" class="button" onclick="javascript:aceptar()"  value="Aceptar" />&nbsp;
                                                    <input name="button"  type="button" class="button" onclick="javascript:cancelar()"  value="Cancelar" />
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td colspan="2" align="center"></td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </form>
    </body>
</html>