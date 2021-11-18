<?xml version="1.0" encoding="UTF-8" ?>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es">
   <head>
   <title>
   	<s:if test="tipoArchivar == 'archivar'">Archivar Documento
   	</s:if>
   	<s:else>Enviar a OEFA
	</s:else>
    </title>
      <meta http-equiv="Content-Type" content="text/html; utf-8" />
      <link rel="stylesheet" type="text/css" href="css/estilo.css" />
      <link rel="stylesheet" type="text/css" href="js/dojo/dijit/themes/soria/soria.css" />
      <script type="text/javascript" src="js/dojo/dojo/dojo.js"></script>
      <script type="text/javascript" src="js/jquery.js"></script>
      <script type="text/javascript">
         var USUARIO_ROL = "<s:property value='#session._USUARIO.rol' />";

         dojo.require("dijit.form.FilteringSelect");
         dojo.require("dijit.form.Button");
         dojo.require("dijit.form.Form");
         dojo.require("dojo.data.ItemFileReadStore");
         dojo.require("dijit.form.DateTextBox");
         dojo.require("dijit.form.ValidationTextBox");



         function archivar(tipoArchivar) {

             var mensaje="";
             if(tipoArchivar=="archivar")
				mensaje= "Desea Archivar el Documento ";
             else if(tipoArchivar=="oefa")
			    mensaje= "Desea Enviar a OEFA el Expediente ";

             if (confirm(mensaje+"<s:property value='documento.numeroDocumento' /> ?")) {
                 /*document.forms[0].action = "Archivar_guardar.action?tipoArchivar="+tipoArchivar;
                 document.forms[0].submit() ;*/
            	 dojo.xhrPost({
		                url: "Archivar_guardar.action",
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

         var agregarDestinatario = function(){
            agregar();
         }

         dojo.addOnLoad(function(){
            new dijit.form.FilteringSelect({
               id				: "iddestinatarios",
               jsId				: "iddestinatarios",
               name				: "iddestinatarios",
               store			: new dojo.data.ItemFileReadStore({url: "derivarCC.action"}),
               searchAttr		: "label",
               labelAttr		: "label",
               idAttr			: "ids",
               required		: false,
               hasDownArrow	: true,
               highlightMatch	: "all",
               onChange		: agregarDestinatario
            },"iddestinatarios");

            //agregarCopia("<s:property value="nombreResponsableProceso" />", "USUARIO_<s:property value="idResponsableProceso" />");
            //agregarCopia("<s:property value="nombreResponsableExpediente" />", "USUARIO_<s:property value="idResponsableExpediente" />");
         });

         var agregar = function() {
            var nombre = document.getElementById("iddestinatarios").value;
            var id = dijit.byId("iddestinatarios").getValue();

            if(nombre!=null && nombre!="" && id!=null && id!=""){
               agregarCopia(nombre, id);
            }
         };

         function agregarCopia(nombre, id){
            var existe=false;
            var conCopia=document.getElementsByName("condestinatarios");
            //"#copias span input[name='conCopia']"
            $(conCopia).each(function(){
               if($(this).val()==id)
                  existe=true;
            });
            if(!existe){
               var copia="<span class=\"copia\">";
               copia+="<span>"+nombre+"</span>";
               copia+="<input type=\"hidden\" name=\"condestinatarios"+"\" value=\""+id+"\" />";
               copia+="<a href=\"#\" class=\"quitar\"><img src=\"images/eliminar.gif\" alt=\"X\" /></a></span>";
               $("#destinatarios").append(copia);
               $(".quitar").click(function(){
                  $(this).parent().remove();
               });
            }
         }
      </script>
   </head>
   <body  class="soria"
          <s:if test="cerrar!=null">
             onload="refrescar()"
          </s:if>>
      <form id="frmArchivar" name="frmArchivar" action="" method="post">
         <s:hidden name="idDocumento" />
         <s:hidden name="idExpediente" />
         <s:hidden name="idResponsableProceso" />
         <s:hidden name="nombreResponsableProceso" />
         <s:hidden name="idResponsableExpediente" />
         <s:hidden name="nombreResponsableExpediente" />
         <table width="100%">
            <tr>
               <td></td>
            </tr>
            <tr>
               <td height="20"colspan="6" class="titulo" width="99%">
                  <table width="37%" border="0" height="20" align="left">
                     <tr>
                        <td width="2%" align="center">
                           	<s:if test="tipoArchivar == 'archivar'">
                           		<img onclick="javascript:archivar('archivar')"  src="images/archivar.jpg" border="0" alt="Archivar"/>
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
                  <table width="80%"  height="100%" align="center"  cellpadding="0" cellspacing="0" valing="top">
                     <!--DWLayoutTable-->
                     <tr>
                        <td width="65%" style="height:auto" border="3"  borderColor="#6487d4"  valign="top" >
                           <table width="103%">
                              <tr>
                                 <td height="13" colspan="2" class="header" >
                                    <div align="center">
										<s:if test="tipoArchivar == 'archivar'">Archivar Documento <s:property value="documento.numeroDocumento" />
										</s:if>
										<s:else>Enviar a OEFA
										</s:else>
                                    </div>
                                 </td>
                              </tr>
                              <tr>
                                 <td height="13" colspan="2" class="header" ></td>
                              </tr>
                              <%--
                              <tr>
                                 <td colspan="2">
                                    <table cellpadding="1" cellspacing="1" class="tableForm" width="90%" align="center" border="0">
                                       <tr class="header">
                                          <th class="data">EXPEDIENTES</th>
                                       </tr>
                                       <tr  class="altRow2" style="cursor:hand" >
                                          <td colspan="2">
                                             &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                             <img src="images/folder.gif" border="0" alt=""/>
                                             <s:property value="arbol.sDescripcion" />
                                          </td>
                                       </tr>
                                       <s:iterator  value="arbol.lstExpedienteChildren" >
                                          <tr   class="altRow2" style="cursor:hand" >
                                             <td colspan="2">
                                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                <img src="images/page.gif" border="0" alt=""/>
                                                <s:property value="sDescripcion" />
                                             </td>
                                          </tr>
                                       </s:iterator>
                                    </table>
                                 </td>
                              </tr>
                              --%>
                              <tr>
                                 <td colspan="2" align="center"></td>
                              </tr>
                              <tr>
                                 <td colspan="2" align="center"></td>
                              </tr>

                              <tr>
                                 <td align="left" style="padding-left: 25px">Notificar a:</td>
                                 <td>
                                    <input id="iddestinatarios" name="iddestinatarios"/>
                                 </td>
                              </tr>
                              <tr>
                                 <td ></td>
                                 <td >
                                    <div id="destinatarios" name="destinatarios"></div>
                                 </td>
                              </tr>
                              <tr>
                                 <td align="left" style="padding-left: 25px">Observación: </td>
                                 <td >
                                    <s:textarea id="sObservacion" name="sObservacion" cols="50" rows="10" cssClass="cajaMontoTotal" />
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