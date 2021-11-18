<?xml version="1.0" encoding="UTF-8" ?>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es">
   <head>
      <title>SIGED - Nuevo Documento Usuario Final</title>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
      <meta http-equiv="cache-control" content="no-cache" />
       <META HTTP-EQUIV="Expires" CONTENT="-1">
      <script type="text/javascript">
	     dojo.require("dijit.Dialog");
	 	 dojo.require("dijit.form.Button");
	 	 dojo.require("dijit.form.DateTextBox");
	 	 dojo.require("dijit.form.FilteringSelect");
	 	 dojo.require("dijit.form.Form");
	 	 dojo.require("dijit.form.TextBox");
	 	 dojo.require("dijit.form.ValidationTextBox");
	 	 dojo.require("dojo.data.ItemFileWriteStore");
	 	 dojo.require("dojo.data.ItemFileReadStore");
	 	 dojo.require("dojo.rpc.JsonService");
	 	 dojo.require("dojox.grid.DataGrid");

	 	 function activarArea(flag){
	 		 if (flag == '0'){
	 			document.getElementById("idUnidadSolicitante").style.display = "block";
	 			document.getElementById("desUnidadSolicitante").style.display = "none";
	 		 }else{
	 			document.getElementById("desUnidadSolicitante").style.display = "block";
	 			document.getElementById("idUnidadSolicitante").style.display = "none";
	 		 }
	 	 }

         function llenarCamposReferencia(){
        	// dijit.byId("sNroDocumentoFedatear").attr("value", "");

         }

         var storeServicioFedatario = new dojo.data.ItemFileReadStore({url: "autocompletarServicioFedatario.action?idOperacion=0"});
         var storeAreaOperacionDocumento = new dojo.data.ItemFileReadStore({url: "autocompletarAreaDestino.action"});
      </script>
     <script type="text/javascript" src="js/busquedaDocumento.js"></script>
       <script type="text/javascript" src="js/siged/NuevoDocumentoFedatear.js"></script>

   </head>
   <body>
      <div class="margen5PX">
         <button dojoType="dijit.form.Button"
                 type="button"
                 id="btnRegistrarDocumentoFedatearTop"
                 jsId="btnRegistrarDocumentoFedatearTop"
                 iconClass="siged16 sigedSave16"
                 onClick="submitFormFedatear"
                 showLabel="true">Registrar</button>
         <div id="divShowFile2"></div>
      </div>


       <form dojoType="dijit.form.Form" id="frmNuevoDocumentoFedatear" jsId="frmNuevoDocumentoFedatear" action="NuevoDocumentoFedatear.action" method="post">

         <table width="100%" border = "0">
            <tr>
               <td>
                  <div id="showErrorDocumento" class="margen5PX" style="color:red;font-weight:bold;">&nbsp;</div>
               </td>
            </tr>
            <tr>
               <td>
                  <div class="marcoForm margen5PX">
                     <table width="100%" border= "0">
                        <tr>
                           <td width="15%" class="titulo" colspan="2">DATOS DEL DOCUMENTO A FEDATEAR</td>
						 <td width="85%" colspan="2"></td>
                        </tr>

                        <tr>
                           <td width="15%">Documento</td>
                           <td class="label" colspan="2" width="85%">
                              <input type="hidden" id="objDF.idDocumentoFedateado" name="objDF.idDocumentoFedateado" />
                              <input id="objDF.nroDocumentoArea" />

                              <button dojoType="dijit.form.Button"
                                      id="btnBusquedaDocumentoFedatear"
                                      jsId="btnBusquedaDocumentoFedatear"
                                      iconClass="siged16 sigedSearch16"
                                      onClick="showBusquedaDocumento"
                                      showLabel="false">B&uacute;squeda de Documento</button>
                           </td>
                        </tr>


                        <tbody id="tbDocumentoFedatear" style="display:none;">

                          <tr>
                              <td>Tipo</td>
                               <td class="label" colspan="2">
                                 <div id="objDF.idTipoDocumentoFedateado"/>
                              </td>
                           </tr>


                           <tr>
                              <td>Nro</td>
                              <td class="label" colspan="2">
                                  <input type="text"
			                         dojoType="dijit.form.ValidationTextBox"
			                         id="objDF.nroDocumento"
			                         name="objDF.nroDocumento"
			                         invalidMessage="Ingrese el Nro del Documento"
			                         required="false"
			                         maxlength="30"
			                         style="width:120px;" />

                               <!--    <div id="sNroDocumentoOperacionFedatear"/> -->
                              </td>
                           </tr>

                           <tr>
                              <td>Asunto</td>
                              <td class="label" colspan="2">

                               <input type="text"
			                         dojoType="dijit.form.ValidationTextBox"
			                         id="objDF.asunto"
			                         name="objDF.asunto"
			                         invalidMessage="Ingrese el Asunto del Documento"
			                         required="false"
			                         maxlength="100"
			                         style="width:250px;" />

                            <!--       <div id="sAsuntoDocumentoOperacionFedatear"/> -->
                              </td>
                           </tr>


 						   <tr>
                              <td>Fecha</td>
                              <td class="label" colspan="2">
                                <input dojoType="dijit.form.DateTextBox"
                                     type="text"
			                         id="objDF.fechaDocumentoRegistro"
			                         jsId="objDF.fechaDocumentoRegistro"
			                         name="objDF.fechaDocumentoRegistro"
			                         invalidMessage="Ingrese la Fecha del Documento (dd/MM/yyyy)"
			                         required="false"
			                          style="width:120px;"
			                         constraints="{datePattern:'dd/MM/yyyy'}"
			                         onChange="dijit.byId('objDF.fechaDocumentoRegistro').constraints.min = this.getValue();" />

 						<!--
                               <input type="text"
			                         dojoType="dijit.form.ValidationTextBox"
			                         id="sFechaDocumentoOperacionFedatear"
			                         name="sFechaDocumentoOperacionFedatear"
			                         invalidMessage="Ingrese la Fecha del Documento"
			                         required="true"
			                         maxlength="10"
			                         style="width:120px;" />

                                 <div id="sFechaDocumentoOperacionFedatear"/> -->
                              </td>
                           </tr>

                           <tr>
                              <td colspan="3">&nbsp;</td>
                           </tr>
                        </tbody>

                        <tbody id="tbformularioFedatear" style="display:none;">
                           <tr>
                              <td colspan="3"><hr /></td>
                           </tr>
                           <tr>
                              <td colspan="3" class="titulo">INFORMACION A FEDATEAR</td>
                           </tr>
                           <tr>
                              <td colspan="3" class="titulo">&nbsp;</td>
                           </tr>



                           <tr>
                               <td>Servicio</td>
                               <td class="label" colspan="1">
                                  <select dojoType="dijit.form.FilteringSelect"
						                id="objDF.servicio"
						                name="objDF.servicio"
						                value="<s:property value='storeServicioFedatario_' />"
						                idAttr="id"
						                labelAttr="label"
						                style="width:250px;"
						                required="true"
										hasDownArrow="true"
						                searchAttr="label"
						                store="storeServicioFedatario"></select>


                               </td>
                           </tr>
                           <tr>
                              <td>Nro de Folios del Documento</td>
                              <td class="label" colspan="1">
                                 <input type="text"
			                         dojoType="dijit.form.ValidationTextBox"
			                         id="objDF.nroFoliosDocumento"
			                         name="objDF.nroFoliosDocumento"
			                         invalidMessage="Ingrese el Nro de Folios del Documento"
			                         required="true"
			                         maxlength="3"
			                         regExp="\d{1,3}"
			                         style="width:50px;" />
                              <!--
                                 <div dojoType="dijit.form.ValidationTextBox"
                                      id="sNroFoliosOperacionFedatear"
                                      jsId="sNroFoliosOperacionFedatear"
                                      name="sNroFoliosOperacionFedatear"
                                      invalidMessage="Ingrese el Nro de Folios del Documento"
                                      regExp=".{1,3}"
                                      required="true"
                                      style="width:50px"
                                      trim="true" />  -->
                              </td>
                           </tr>



                            <tr>
                              <td>Nro de Copias Fedateadas</td>
                              <td class="label" colspan="1">
                                 <input type="text"
			                         dojoType="dijit.form.ValidationTextBox"
			                         id="objDF.nroCopiasFedateadas"
			                         name="objDF.nroCopiasFedateadas"
			                         invalidMessage="Ingrese el Nro de Copias Fedateadas"
			                         required="true"
			                         maxlength="3"
			                         regExp="\d{1,3}"
			                         style="width:50px;" />
                              <!--
                                 <div dojoType="dijit.form.ValidationTextBox"
                                      id="sNroCopiasOperacionFedatear"
                                      jsId="sNroCopiasOperacionFedatear"
                                      name="sNroCopiasOperacionFedatear"
                                      invalidMessage="Ingrese el Nro de Copias Fedateadas"
                                      regExp=".{1,3}"
                                      required="true"
                                      style="width:50px"
                                      trim="true" /> -->
                              </td>
                           </tr>


                           <tr>
                              <td>Solicitante</td>

                              <td style="padding-bottom: 0.25em;" colspan="2">
								<label>Interno</label><input dojoType="dijit.form.RadioButton" name="rad1" id="radInterno"   checked  onClick="activarArea('0');"/>&nbsp;&nbsp;&nbsp;
								<label>Externo</label><input dojoType="dijit.form.RadioButton" name="rad1" id="radExterno"            onClick="activarArea('1');"/>
							 </td>

                              <!--
                              <td class="label" colspan="1">
                                  <select dojoType="dijit.form.FilteringSelect"
						                id="objDF.idUnidadSolicitante"
						                name="objDF.idUnidadSolicitante"
						                value="<s:property value='sAreaDocumentoOperacionFedatear_' />"
						                idAttr="id"
						                labelAttr="label"
						                style="width:250px;"
						                required="true"
										hasDownArrow="true"
						                searchAttr="label"
						                store="storeAreaOperacionDocumento"></select>
                              </td> -->


                           </tr>


                           <tr>
                              <td></td>



                              <td class="label" colspan="1" id="idUnidadSolicitante">
                                  <select dojoType="dijit.form.FilteringSelect"
						                id="objDF.idUnidadSolicitante"
						                name="objDF.idUnidadSolicitante"
						                value="<s:property value='sAreaDocumentoOperacionFedatear_' />"
						                idAttr="id"
						                labelAttr="label"
						                style="width:250px;"
						                required="true"
										hasDownArrow="true"
						                searchAttr="label"
						                invalidMessage="Ingrese el área solicitante"
						                store="storeAreaOperacionDocumento"></select>
                              </td>

                               <td class="label" colspan="1" id="desUnidadSolicitante" style="display:none;">
                                 <input type="text"
			                         dojoType="dijit.form.ValidationTextBox"
			                         id="objDF.desUnidad"
			                         name="objDF.desUnidad"
			                         invalidMessage="Ingrese el área solicitante"
			                         required="true"
			                         maxlength="50"
			                         style="width:250px;" />

                              </td>

                           </tr>
                        </tbody>

                     </table>
                  </div>
               </td>
            </tr>
            <tr>
               <td align="left">
                  <div class="margen5PX">
                     <button dojoType="dijit.form.Button"
                             type="button"
                             id="btnRegistrarDocumentoFedatearBottom"
                             jsId="btnRegistrarDocumentoFedatearBottom"
                             iconClass="siged16 sigedSave16"
                             onClick="submitFormFedatear"
                             showLabel="true">Registrar</button>
                  </div>
               </td>
            </tr>
         </table>
      </form>
       <%@ include file="/pages/NuevoDocumento/busquedaDocumento.jsp" %>

   </body>
</html>
