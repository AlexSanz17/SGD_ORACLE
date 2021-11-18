<?xml version="1.0" encoding="UTF-8" ?>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.btg.ositran.siged.domain.DocumentoReferencia" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es">
   <head>
   <title>
   	<s:if test="tipoArchivar == 'atender'">Atender Documento
   	</s:if>
   
    </title>
     <%
	//List<DocumentoReferencia> documentosReferencia = (List<DocumentoReferencia>)request.getAttribute("documentosReferencia");
      %> 
       
      <meta http-equiv="Content-Type" content="text/html; utf-8" />
      <script type="text/javascript">

			var djConfig = {
				isDebug: false,
				parseOnLoad: true
			};
		</script>
      
      <link rel="stylesheet" type="text/css" href="css/estilo.css" />
      <link rel="stylesheet" type="text/css" href="js/dojo/dijit/themes/soria/soria.css" />
      <link type="text/css" rel="stylesheet" href="css/styleSiged.css" />
        <link type="text/css" rel="stylesheet" href="js/dojo/css/styleDojo.css" />
      
      <script type="text/javascript" src="js/dojo/dojo/dojo.js"></script>
      <script type="text/javascript" src="js/siged/siged.forms.js"></script>
      <script type="text/javascript" src="js/siged/siged.string.js"></script>
      

        <script type="text/javascript" src="js/siged/siged.js"></script>


      <script type="text/javascript" src="js/jquery.js"></script>
      <script type="text/javascript">
         var USUARIO_ROL = "<s:property value='#session._USUARIO.rol' />";

         dojo.require("dijit.Editor");
         dojo.require("dijit._editor.plugins.FontChoice");  // 'fontName','fontSize','formatBlock'
         dojo.require("dijit._editor.plugins.TextColor");
         dojo.require("dijit._editor.plugins.LinkDialog");
         dojo.require("dijit.form.TextBox");
        
         function archivar(tipoArchivar) {
                var arrAccT = [];
                var mensaje="";
                if(tipoArchivar=="atender")
		  mensaje= "Desea Atender el Documento ";
             
                if (confirm(mensaje+"<s:property value='documento.numeroDocumento' /> ?")) {
                  dojo.query("input[name='docReferencias']").forEach(function(node){
	     		if(node.checked){
	     			arrAccT.push(node.id);
	     		}
	        });
                
                document.getElementById("referencias").value = arrAccT; 
                dojo.xhrPost({
		            url: "Archivar_atender.action",
		            content: {
		               	      tipoArchivar : tipoArchivar
		            },
		            form: dojo.byId("frmArchivar"),
		            load: function() {
			       	window.close();
			        window.opener.showGridInbox();
			        window.opener.refreshTabDXE();
			    }
                });
              }
         }

         function refrescar() {
            if (USUARIO_ROL=="") {
               console.debug("Intentando refrescar grid [%s]", window.opener.sTipoGridActual);
               window.opener.showGridInbox(window.opener.sTipoGridActual);
            } else {
               window.opener.parent.location.href ='inicio.action?sTipoFrame=grid' ;
            }

            window.close();
         }

         function ejecutar(){
            if (confirm("Desea aprobar el expediente EXP001")){
               location.href="blank.html";
            }
         }

         
      </script>
   </head>
   <body  class="soria"
          <s:if test="cerrar!=null">
             onload="refrescar()"
          </s:if>>
      <form id="frmArchivar" name="frmArchivar" action="" method="post">
         <input type="hidden" name="strReferencia" id="referencias"/>
         <s:hidden name="idDocumento" />
         <s:hidden name="idExpediente" />
         <s:hidden name="idResponsableProceso" />
         <s:hidden name="nombreResponsableProceso" />
         <s:hidden name="idResponsableExpediente" />
         <s:hidden name="nombreResponsableExpediente" />
         
         <table width="100%" border="0">
            <tr>
               <td></td>
            </tr>
            <tr>
               <td height="20"colspan="6" class="titulo" width="99%">
                  <table width="37%" border="0" height="20" align="left">
                     <tr>
                        <td width="2%" align="center">
                           	<s:if test="tipoArchivar == 'atender'">
                           		<img onclick="javascript:archivar('atender')"  src="images/archivar.jpg" border="0" alt="Archivar"/>
						   	</s:if>
						   	<s:else>
						   		<img onclick="javascript:archivar('oefa')"  src="images/enviar.bmp" border="0" alt="Enviar a OEFA"/>
							</s:else>

                        </td>
                        <td width="55%" align="center" class="tituloRojo"></td>
                     </tr>
                  </table>
               </td>
            </tr>
            <tr align="center">
               <td width="98%" colspan="6" align="left"></td>
            </tr>
            <tr>
               <td height="400" colspan="6" class="titulo" width="97%"  align="left">
               <s:hidden id="tipoArchivar" name="tipoArchivar" />
                  <table width="85%"  height="100%" align="center" border="0" cellpadding="0" cellspacing="0" valing="top">
                     <!--DWLayoutTable-->
                     <tr>
                        <td width="65%" style="height:auto" border="0"  borderColor="#6487d4"  valign="top" >
                           <table width="103%">
                              <tr>
                                 <td height="13" colspan="2" class="header" >
                                    <div align="center">
										<s:if test="tipoArchivar == 'atender'">Atender Documento <s:property value="documento.numeroDocumento" />
										</s:if>
										
                                    </div>
                                 </td>
                              </tr>
                              <tr>
                                 <td height="13" colspan="2" class="header" ></td>
                              </tr>
                
                              <tr>
                                 <td colspan="2" align="center"></td>
                              </tr>
                              <tr>
                                 <td colspan="2" align="center"></td>
                              </tr>
                              <tr>
                                 <td align="left" style="padding-left: 25px">Observación: </td>
                                 <td >
                                 
                                    <s:textarea id="sObservacion" name="sObservacion" cols="48" rows="10" cssClass="cajaMontoTotal" />
                                 </td>
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