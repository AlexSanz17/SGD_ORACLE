<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://ajaxtags.org/tags/ajax" prefix="ajax"%>
<%@page import="com.btg.ositran.siged.domain.Documento"%>
<%@page import="com.btg.ositran.siged.domain.Expediente"%>
<%@page import="com.btg.ositran.siged.domain.Documentostor"%>
<html>
   <head>
      <!--<s:head theme="ajax" />-->
      <title>Tramite Documentario</title>
      <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
      <style type="text/css">
         @import "js/dojo/dijit/themes/soria/soria.css";
         @import "css/estilo.css";
      </style>
      <!--<script language="javascript" src="js/form.js"> </script>
      <script language="Javascript" src="js/general.js"></script>
      <link rel="stylesheet" type="text/css" media="all" href="css/calendar-green.css">
      <script type="text/javascript" src="js/calendar.js"></script>
      <script type="text/javascript" src="js/calendar-es.js"></script>
      <script type="text/javascript" src="js/calendar-setup.js"></script>

      <script type="text/javascript" src="./js/prototype-1.4.0.js"></script>
      <script type="text/javascript" src="./js/scriptaculous.js"></script>
      <script type="text/javascript" src="./js/overlibmws.js"></script>
      <script type="text/javascript" src="./js/ajaxtags-1.2-beta2.js"></script>
      <link rel="stylesheet" type="text/css" href="./css/ajaxtags.css" />
      <link rel="stylesheet" type="text/css" href="./css/displaytag.css" />-->

      <script type='text/javascript' src='./dwr/engine.js'> </script>
      <script type='text/javascript' src='./dwr/util.js'> </script>
      <script type='text/javascript' src='./dwr/interface/toolDwr.js'> </script>

      <!--Begin Script STOR-->
      
      <script type="text/javascript" src="js/dojo/dojo/dojo.js" djConfig="parseOnLoad:true, isDebug: false"></script>
      <script type="text/javascript" src="js/dojo/dijit/dijit.js" djConfig="parseOnLoad:true, isDebug: false"></script>
      <script type="text/javascript">
      	dojo.require("dijit.form.FilteringSelect" );
      	dojo.require("dojo.data.ItemFileReadStore");
        dojo.require("dojo.parser");
        dojo.require("dijit.Dialog");
        dojo.require("dijit.ProgressBar");

         var storeMotivos = new dojo.data.ItemFileReadStore({url: "listaMotivos.action?idProceso=" + <s:property value="expediente.proceso.idproceso" />});
      </script>
      
      <script language="JavaScript">
         function updateExpediente(){
            if(validarFormulario()){
               document.form1.action="doStor_updateExpediente.action"
               dijit.byId("dlgProgresBar").show();
               document.form1.submit();
               window.close();
            }
            
         }

         function editarMotivoSubmotivo(idsubmotivo){
            //alert("Mostrar Valores");
            var motivo = dwr.util.getValue("motivo"+idsubmotivo);
            //var idMotivo = diji
            var submotivo = dwr.util.getValue("submotivo"+idsubmotivo);
            //alert("M:"+motivo+" SB:"+submotivo);
            /*dwr.util.setValue("editnmotivo",motivo);
            dwr.util.setValue("editnsubmotivo",submotivo);
            dwr.util.setValue("idUpdate",idsubmotivo);*/
            dojo.byId("idUpdate").value=idsubmotivo;
            dijit.byId("editmotivo").setDisplayedValue(motivo);
            //dijit.byId("editsubmotivo").setValue(idsubmotivo);
            //dijit.byId("editsubmotivo").setDisplayedValue(submotivo);
            //dijit.byId("idUpdate").setValue(idsubmotivo);
            //dojo.byId("editsubmotivo").focus();
            //dojo.byId("editmotivo").focus();
            //alert("Termino de editar IdSubmotivo - Campos Listos para Editar");
         }

   function updateMotivoSubmotivo(){
      var idUpdate = dwr.util.getValue("idUpdate");
      //alert("idUpdate: "+idUpdate);
      var motivo = dwr.util.getValue("editnmotivo");
      var submotivo = dwr.util.getValue("editnsubmotivo");
      var newIdSubmotivo = dwr.util.getValue("idsubmotivo");
      //alert("Motivo"+motivo.length+" submotivo"+submotivo.length);
      if(idUpdate==0 && motivo.length!=0 && submotivo.length!=0){
         //alert("Agregar Nuevo Registro");
         dwr.util.cloneNode("myPattern", { idSuffix:newIdSubmotivo});
         dwr.util.setValue("idPattern"+newIdSubmotivo, newIdSubmotivo);
         dwr.util.setValue("motivoPattern"+newIdSubmotivo, motivo);
         dwr.util.setValue("submotivoPattern"+newIdSubmotivo, submotivo);
         $("myPattern"+newIdSubmotivo).style.display = "";         
         clearEdit();
      }else if(idUpdate!=0 && motivo.length!=0 && submotivo.length!=0){
         dwr.util.setValue("motivo"+idUpdate,motivo);
         dwr.util.setValue("submotivo"+idUpdate,submotivo);
         dwr.util.setValue("id"+idUpdate,newIdSubmotivo);
         dwr.util.setValue("idUpdate","0");
         clearEdit();
      }else{
         alert("Debe Ingresar Motivo y SubMotivo");
      }
   }

   function updateMotivoSubMotivo01(){
      var idUpdate = dwr.util.getValue("idUpdate");
      //alert("idUpdate: "+idUpdate);
      /*var motivo = dwr.util.getValue("editnmotivo");
      var submotivo = dwr.util.getValue("editnsubmotivo");*/
      //var newIdSubmotivo = dwr.util.getValue("idsubmotivo");
      var dijitMotivo = dijit.byId("editmotivo");
      var dijitSubmotivo = dijit.byId("editsubmotivo");
      var newIdSubmotivo = dijitSubmotivo.getValue();
      //alert("Motivo"+dijitMotivo.getDisplayedValue().length+" submotivo"+dijitSubmotivo.getDisplayedValue().length);
      if(idUpdate==0 && dijitMotivo.getValue().length!=0 && dijitSubmotivo.getValue().length!=0 && dijitMotivo.getDisplayedValue().length!=0 && dijitSubmotivo.getDisplayedValue().length!=0){

         //alert("Agregar Nuevo Registro:"+dijitMotivo.getDisplayedValue());
         dwr.util.cloneNode("myPattern", { idSuffix:newIdSubmotivo});
         dwr.util.setValue("idPattern"+newIdSubmotivo, newIdSubmotivo);
         dwr.util.setValue("motivoPattern"+newIdSubmotivo, dijitMotivo.getDisplayedValue());
         dwr.util.setValue("submotivoPattern"+newIdSubmotivo, dijitSubmotivo.getDisplayedValue());
         $("myPattern"+newIdSubmotivo).style.display = "";
         //alert("Antes de Limpiar despues de Grabar");
         clearEdit();
         /*dijit.byId("editsubmotivo").setDisplayedValue("");
         dijit.byId("editmotivo").setDisplayedValue("");
         dijit.byId("editsubmotivo").setValue("");
         alert("jijiji");
         dijit.byId("editmotivo").setValue("");*/
         
         //dijit.byId("dwr_idsubmotivo").store= new dojo.data.ItemFileReadStore();
         
      }else if(idUpdate!=0 && dijitMotivo.getValue().length!=0 && dijitSubmotivo.getValue().length!=0 && dijitMotivo.getDisplayedValue().length!=0 && dijitSubmotivo.getDisplayedValue().length!=0){
         dwr.util.setValue("motivo"+idUpdate,dijitMotivo.getDisplayedValue());
         dwr.util.setValue("submotivo"+idUpdate,dijitSubmotivo.getDisplayedValue());
         dwr.util.setValue("id"+idUpdate,newIdSubmotivo);
         dwr.util.setValue("idUpdate","0");
         //alert("Campos Editados");
         clearEdit();
         /*dijit.byId("editmotivo").setValue("");
         dijit.byId("editmotivo").setDisplayedValue("");
         dijit.byId("editsubmotivo").setValue("");
         dijit.byId("editsubmotivo").setDisplayedValue("");*/
         
      }else{
         alert("Debe Ingresar Motivo y SubMotivo");
      }

   }

         function clearEdit(){
            //alert("editar");
                      
            /*alert("limpiando motivo");
            dijit.byId("editmotivo").setValue("");
            alert("Que pasa primero");
            dijit.byId("editmotivo").setDisplayedValue("");

            alert("limpiando submotivo");
            dijit.byId("editsubmotivo").setValue("");
            dijit.byId("editsubmotivo").setDisplayedValue("");*/

            //alert("ahora dwr");
            dwr.util.setValue("editmotivo",null);
            dwr.util.setValue("editsubmotivo",null);
            //dwr.util.setValue("idUpdate","");
         }

         function refrescar(){
            window.opener.parent.location.href ='/siged/inicio.action?sTipoFrame=grid';
            window.opener.parent.frames["mainFrame"].location.href = "/siged/doBody.action";
            window.close();
         }

         function mostrarValores(){
            //alert("Onload");
            alert("EditMotivo: "+dijit.byId("editmotivo").getValue());
            alert("EditMotivo: "+dijit.byId("editsubmotivo").getValue());
         }

         function validarFormulario(){
            //Validar Monto
            var monto =document.getElementById("monto");
            var patternCurrency=/^[0-9]{1,9}\.{0,1}\,(0,1)[0-9]{1,9}$/;
            if(monto.value.search(patternCurrency)==-1){
               alert("El monto debe ser numerico");
               document.getElementById("monto").focus();
               return false;
            }else if(monto.value.length<=20){
               return true;
            }else{
               alert("Ingrese un Monto Valido - Valor Numérico, Máximo 2 decimales");
               document.getElementById("monto").focus();
               return false;
            }

         }

   function validarFormulario(){
      //Validar Monto
      var monto =document.getElementById("monto");
      var patternCurrency=/^[0-9]{1,9}\.{0,1}\,{0,1}[0-9]{1,9}$/;
      if(monto.value.search(patternCurrency)==-1){
         alert("El monto debe ser numerico");
         document.getElementById("monto").focus();
         return false;
      }else if(monto.value.length<=20){
         return true;
      }else{
         alert("Ingrese un Monto Valido - Valor Numérico, Máximo 2 decimales");
         document.getElementById("monto").focus();
         return false;
      }
   }
   function updateListaSubMotivo(){
      //alert("Tratando de actualizar Lista submotivos");
      var motivo = dijit.byId("editmotivo");
      var subMotivo = dijit.byId("editsubmotivo");
      //alert("Motivo seleccionado: "+motivo.getValue());
      //alert("Motivo seleccionado"+motivo.getValue());
      if(motivo.getValue().length!=0){
         //alert("cargar stor dojo")
         subMotivo.store = new dojo.data.ItemFileReadStore({url:  "listaSubMotivos.action?idDependencia1="+motivo.getValue()});
      }
      var idUpdate = dwr.util.getValue("idUpdate");
      //alert("idUpdate: "+idUpdate);
      if(idUpdate!=0){
         //var dataSubmotivo = dijit.byId("editsubmotivo").val;
         if(dojo.byId("editsubmotivo").value.length!=0){
            //alert("jajaja");
            //dijit.byId("editsubmotivo").setValue("");
            //dijit.byId("editsubmotivo").setDisplayedValue("");
         }
         dijit.byId("editsubmotivo").setValue(idUpdate);
         
         //alert("Paso la asignacion - debe regresar a edit submotivo");
      }
   }

      </script>
      <!--End Script STOR-->

   </head>
   <body  class="soria" topmargin="0" leftmargin="0" rigthmargin="0" <s:if test="refrescar!=null">onload="refrescar()"</s:if> >
      <form name="form1" action="" method="POST">
         <s:hidden name="idDocumento" />
         <table width="100%" border="0">
            <tr>
               <td height="14" colspan="3"> </td>
            </tr>
            <tr>
               <td height="20"colspan="3" class="titulo">
                  <table width="99%" height="20" align="center" bgcolor="#A2C0DC">
                     <tr>
                        <td align="left" class="titulo">Datos del Expediente</td>
                     </tr>
                  </table>
               </td>
            </tr>
            <tr align="center">               
               <td width="99%" colspan="2" align="left">
                  <img src="images/xx.bmp" onClick="updateExpediente()" alt="Registrar"/>
                  <img src="images/regresar.bmp" onClick="window.close()" alt="Cancelar"/>
               </td>
               <td width="1%" align="center">&nbsp;</td>
            </tr>
            <tr>
               <td height="14" colspan="3">
                  <table width="95%" align="center" bordercolor="#669BCD" bgcolor="#BFD9F1" border="0">
                     <tr>
                        <td height="75">
                           <table width="100%" align="center" border="0">
                              <tr>
                                 <td width="2%" ></td>
                                 <td width="20%" height="5" ></td>
                                 <td width="20%" ></td>
                                 <td width="15%" ></td>
                                 <td width="40%" ></td>
                                 <td width="3%" ></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td width="20%" height="16" align="left">Referencia</td>
                                 <td colspan="3" align="left" class="label">
                                    <!--<input name="nroexpediente" type="text" class="cajaMontoTotal" value="<s:property value="expediente.getNroexpediente()"/>" size="50" readonly>-->
                                    <s:property value="expediente.getNroexpediente()"/>
                                    <!--<s:textfield name="nroexpediente" cssClass="cajaMontoTotal" size="50" readonly="true"/>-->
                                 </td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td ></td>
                                 <td height="16" align="left">Proceso</td>
                                 <td colspan="3" class="label">
                                    <!--<input name="proceso" type="text" class="cajaMontoTotal" value="<s:property value="expediente.getProceso().getNombre()"/>" size="75" readonly>-->
                                    <s:property value="expediente.getProceso().getNombre()"/>
                                 </td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td colspan="6" height="5"><hr></td>
                              </tr>
                              <tr>
                                 <td colspan="6" height="15" class="label">DOCUMENTO</td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Tipo Documento</td>
                                 <td align="left" width="17%" class="label">
                                    <!--<input name="tipodocumento" type="text" class="cajaMontoTotal" value="<s:property value="documentoStor.getDocumento().getTipodocumento().getNombre()"/>" size="20" readonly>-->
                                    <s:property value="documentoStor.documento.tipoDocumento.nombre"/>
                                 </td>
                                 <td align="left" >&nbsp;</td>
                                 <td align="left" width="45%" class="label"></td>
                                 <td></td>
                              </tr>                              
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Asunto</td>
                                 <td colspan="3" align="left" class="label">
                                    <!--<input name="asunto" type="text" class="cajaMontoTotal" value="<s:property value="documentoStor.getDocumento().getAsunto()" />" size="60" readonly>-->
                                    <s:property value="documentoStor.getDocumento().getAsunto()" />
                                 </td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td colspan="6" height="15"><hr></td>
                              </tr>
                              <tr>
                                 <td colspan="6" height="15" class="label">CLIENTE</td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Tipo Doc Identidad</td>
                                 <td align="left" width="30%" class="label"  colspan="3">
                                    <!--<s:textfield name="tipodocidentidad" cssClass="cajaMontoTotal" readonly="true" />-->
                                    <s:property value="tipodocidentidad" />
                                 </td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Nro Doc Identidad</td>
                                 <td id="sepa" style="display: block" align="left" width="17%" class="label" colspan="3">
                                    <!--<s:textfield name="nrodocidentidad" cssClass="cajaMontoTotal" size="20" readonly="true"/>-->
                                    <s:property value="nrodocidentidad" />
                                 </td>
                                 <td></td>                                 
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Nombre/Raz&oacute;n Social</td>
                                 <td colspan="3" align="left" class="label">
                                    <!--<input name="nombrecliente" type="text" class="cajaMontoTotal" value="<s:property value="expediente.getCliente().getRazonsocial()" />" size="50" readonly>-->
                                    <s:if test="expediente.cliente.tipoIdentificacion.nombre.equalsIgnoreCase('RUC')">
                                       <!--<s:property value="expediente.cliente.razonSocial" />-->
                                       <s:property value="expediente.clienterazonsocial" />
                                    </s:if>
                                    <s:else>
                                       <!--<s:property value="expediente.cliente.nombres" /> <s:property value="expediente.cliente.apellidoPaterno" /> <s:property value="expediente.cliente.apellidoMaterno" />-->
                                       <s:property value="expediente.clientenombres" /> <s:property value="expediente.clienteapellidopaterno" /> <s:property value="expediente.clienteapellidomaterno" />

                                    </s:else>
                                 </td>
                                 <td></td>
                              </tr>                              
                              <tr>
                                 <td colspan="6" height="15"><hr></td>
                              </tr>
                              <tr>
                                 <td colspan="6" height="15" class="label">CONCESIONARIO</td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Nro RUC</td>
                                 <td colspan="3" align="left" class="label">
                                    <!--<input name="nrorucconcesionario" type="text" class="cajaMontoTotal" value="<s:property value="expediente.getConcesionario().getRuc()" />" size="30" readonly>-->
                                    <s:property value="expediente.getConcesionario().getRuc()" />
                                 </td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Nombre/Raz&oacute;n Social</td>
                                 <td colspan="3" align="left" class="label">
                                    <!--<input name="nombreconcesionario" type="text" class="cajaMontoTotal" value="<s:property value="expediente.getConcesionario().getRazonsocial()" />" size="30" readonly>-->
                                    <s:property value="expediente.concesionario.razonSocial" />
                                 </td>
                                 <td></td>
                              </tr>
                              
                              <tr>
                                 <td colspan="6" height="15"><hr></td>
                              </tr>
                              <tr>
                                 <td colspan="6" height="15" class="label">DATOS ADICIONALES</td>
                              </tr>
                              <tr><td colspan="6" height="24" align="left">Lista de Nro Suministros</td></tr>
                              <tr>
                                 <td></td>
                                 <td height="17" align="left"></td>
                                 <td height="17" colspan="3" align="left" class="label">
                                 <table>
                        <s:iterator value="documentoStor.getSuministroList()">
                        <tr>
                           <td height="17">
                              <!--<input type="text" value="<s:property value="getNrosuministro()" />" readonly>-->
                              <s:property value="getNrosuministro()" />
                           </td>
                        </tr>
                        </s:iterator>
                                 </table>
                                 </td>
                              </tr>
                              <tr><td height="16" colspan="6">Lista Motivos SubMotivos</td></tr>
                              <tr>                                 
                                 <td colspan="6" valign="top" >
                                    <table border="0" align="center" width="90%">
                                    <tr><td>
                                    <table border="0" width="100%">
                                       <s:iterator value="listaMotivos">
                                          <tr>
                                             <td style="display:none;"><input type="text" name="<s:property value="getIdmotivo()" />" value="<s:property value="getIdmotivo()" />"></td>
                                             <td style="display:none;"><input type="text" id="motivox<s:property value="getIdmotivo()" />" name="motivo" value="<s:property value="getDescripcion()" />"></td>
                                          </tr>
                                          <tr>
                                             <td colspan="3">
                                                <table width="100%" border="0">
                                                   <s:iterator value="documentoStor.getSubmotivoList()">
                                                      <s:if test="getMotivo().getIdmotivo()==idmotivo">
                                                         <tr>
                                                            <td style="display:none;"><input type="text" id="id<s:property value="getIdsubmotivo()" />" name="listidsubmotivos" value="<s:property value="getIdsubmotivo()" />"></td>
                                                            <td><input type="text" id="motivo<s:property value="getIdsubmotivo()" />" name="motivo" value="<s:property value="getMotivo().getDescripcion()" />" readonly disabled size="40"></td>
                                                            <td><input type="text" id="submotivo<s:property value="getIdsubmotivo()" />" name="submotivo" value="<s:property value="getDescripcion()" />" readonly disabled size="40"></td>
                                                            <td><img id="<s:property value="getIdsubmotivo()" />"src="images/edit.bmp" border="0" onClick="editarMotivoSubmotivo(this.id)" alt="Editar"/></td>
                                                         </tr>
                                                      </s:if>
                                                   </s:iterator>
                                                </table>
                                             </td>
                                          </tr>
                                       </s:iterator>
                                    </table>
                                    </td></tr>
                                    <tr><td>
                                    <label>Agregar Nuevos Motivos - Sub Motivos</label><label id="requiredObservacion" class="textoalerta"><s:property value="requiredObservacion" /></label>
                                    </td></tr>
                                    <tr><td>
                                    <table border="2" width="90%">
                                       <thead>
                                             <tr>
                                                <th>Motivo</th>
                                                <th>SubMotivo</th>
                                                <th></th>
                                             </tr>
                                       </thead>
                                       <tbody id="myTable">
                                          <tr id="myPattern" style="display:none">
                                             <td style="display:none;">
                                                <input type="text" id="idPattern" name="listnuevosidsubmotivos" />
                                             </td>
                                             <td>
                                                <input type="text" id="motivoPattern"  readonly disabled size="40"/>
                                             </td>
                                             <td>
                                                <input type="text" id="submotivoPattern"  readonly disabled size="40"/>
                                             </td>
                                          </tr>
                                       </tbody>
                                    </table>
                                    </td></tr>
                                    <tr><td>
                                    <table border="0">
                                       <!--<tr>
                                          <td>Motivo: </td>
                                          <td>
                                             <s:textfield id="editnmotivo" name="nmotivo" cssClass="cajaMontoTotal" size="30" />
                                             <span id="indicatorMotivo" style="display:none;"><img src="./images/indicator.gif" /></span>
                                             <s:textfield id="editidmotivo" name="idmotivo" cssStyle="display:none"/>

                                             <ajax:autocomplete
                                                source="nmotivo"
                                                target="idmotivo"
                                                baseUrl="./autocompletar.view?metodo=ObtenerMotivo"
                                                className="autocomplete"
                                                indicator="indicatorMotivo"
                                                minimumCharacters="1"
                                                parser="new ResponseXmlToHtmlListParser()" />
                                          </td>
                                       </tr>
                                       <tr>
                                          <td>SubMotivo: </td>
                                          <td>
                                             <s:textfield id="editnsubmotivo" name="nsubmotivo" cssClass="cajaMontoTotal" size="30" />
                                             <span id="indicatorSubMotivo" style="display:none;"><img src="./images/indicator.gif" /></span>
                                             <s:textfield id="editidsubmotivo" name="idsubmotivo" cssStyle="display:none"/>

                                             <ajax:autocomplete
                                                source="nsubmotivo"
                                                target="idsubmotivo"
                                                baseUrl="./autocompletar.view?metodo=ObtenerSubmotivo"
                                                className="autocomplete"
                                                indicator="indicatorSubMotivo"
                                                minimumCharacters="1"
                                                parameters="idmotivo={idmotivo}"
                                                parser="new ResponseXmlToHtmlListParser()" />
                                          </td>
                                       </tr>-->
                                       <tr>
                                          <input type="hidden" id="idUpdate" value="0">
                                          <td align="center"><img src="images/guardar.bmp" border="0" onClick="updateMotivoSubMotivo01()" alt="Actualizar"/></td>
                                          <td align="center"><img src="images/Limpiar.bmp" border="0" onClick="clearEdit()" alt="Limpiar"/></td>
                                       </tr>
                                       <tr>
                                          <td>Motivo: </td>
                                          <td width="303">
                                             <select dojoType="dijit.form.FilteringSelect"
                                                   store="storeMotivos"
                                                   idAttr="id"
                                                   labelAttr="label"
                                                   searchAttr="label"
                                                   name="editmotivo"
                                                   id="editmotivo"
                                                   invalidMessage="Seleccione un Motivo Válido"
                                                   required="true"
                                                   onchange="updateListaSubMotivo()"
                                             />
                                          </td>
                                       </tr>
                                       <tr>
                                          <td>SubMotivo: </td>
                                          <td>
                                             <select dojoType="dijit.form.FilteringSelect"                                                   
                                                   idAttr="id"
                                                   labelAttr="label"
                                                   searchAttr="label"
                                                   name="editsubmotivo"
                                                   id="editsubmotivo"
                                                   invalidMessage="Seleccione un Motivo Válido"
                                                   required="true"
                                             />
                                          </td>
                                       </tr>
                                    </table>
                                    </td></tr>
                                    </table>
                                 </td>
                                 <!--<td></td>-->
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Monto de Reclamo S/.</td>
                                 <td colspan="3" align="left" class="label">
                                    <!--<input name="nombre" type="text" class="cajaMontoTotal" value="" size="30">-->
                                    <s:textfield name="monto" cssClass="cajaMontoTotal" size="30" />
                                 </td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td></td>
                                 <td height="16" align="left">Observaciones</td>
                                 <td colspan="3" align="left" class="label">
                                    <s:textarea name="observacioneditar" cols="75" rows="6" />
                                 </td>
                                 <td></td>
                              </tr>
                              <tr>
                                 <td></td>
                              		<td></td>
                              		<td colspan="4" height="16">
                                       <s:url id="viewexpe" action="doStor_ventanaDatosExpediente" >
                                 		<s:param name="idDocumento"><s:property value="idDocumento"/></s:param>
                                       <s:param name="tipoVentanaDetalle">completo</s:param>
                                       </s:url>
                                       <s:a href="%{viewexpe}">Ver Datos del Expediente - Completo</s:a>
                                 	</td>
                              </tr>
                           </table>
                        </td>
                     </tr>
                  </table>
               </td>
            </tr>
            <tr>
               <td width="99%" colspan="2" align="left">
                  <img src="images/xx.bmp" border="0" onClick="updateExpediente()" alt="Registrar"/>
                  <img src="images/regresar.bmp" onClick="window.close()" alt="Cancelar"/>
               </td>
            </tr>
         </table>
      </form>
      <%@ include file="../util/progressBar.jsp" %>
   </body>
</html>
