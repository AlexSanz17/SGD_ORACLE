<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
   <head>
      <s:url id="autoUrl" action="autocompletarProceso1">
         <s:param name="sTipoDerivacion" value="sTipoDerivacion" />
      </s:url>
      <%-- 
      <s:property value="autoUrl" />--%>
      <title>Derivar Masivamente</title>
      <link type="text/css" rel="stylesheet" href="css/styleSiged.css" />
      <link type="text/css" rel="stylesheet" href="js/dojo/css/styleDojo.css" />
      <style type="text/css">
         div.falso {
            position: absolute;
            top: -49px;
            left: 80px;
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
            top: -49px; 
            left: 80px;
         }
      </style>

      <script type="text/javascript" src="js/dojo/dojo/dojo.js" djConfig="parseOnLoad:true, isDebug: false"></script>
      <script type="text/javascript" src="js/dojo/dijit/dijit.js" djConfig="parseOnLoad:true, isDebug: false"></script>
      <script type="text/javascript" src="js/siged/upload.js"></script>

      <script type='text/javascript' src='./dwr/engine.js'> </script>
      <script type='text/javascript' src='./dwr/util.js'> </script>
      <script type='text/javascript' src='./dwr/interface/toolDwr.js'></script>
      <script type="text/javascript" src="js/siged/siged.js"></script>
      <script type="text/javascript" src="js/jquery.js"></script>
      <script type="text/javascript" src="js/siged/siged.forms.js"></script>
      <script type="text/javascript" src="js/siged/masivo.js"></script>

      <script type="text/javascript">
         dojo.require("dijit.form.FilteringSelect" );
         dojo.require("dojo.data.ItemFileReadStore");
         dojo.require("dojo.io.iframe");
         dojo.require("dojo.rpc.JsonService");
         dojo.require("dijit.form.NumberTextBox");
         dojo.require("dijit.form.TextBox");
         dojo.require("dijit.form.TimeTextBox");
         dojo.require("dijit.form.DateTextBox");
         dojo.require("dijit.Editor");
         dojo.require("dijit.form.NumberSpinner");
         dojo.require("dojox.grid.DataGrid");

         //var store = new dojo.data.ItemFileReadStore({url: "<s:property value='autoUrl' />"});
         var service = new dojo.rpc.JsonService("SMDAction.action");
         var store = new dojo.data.ItemFileReadStore({url: "buscarPara.action?mode=iddestinatario"});
         var storeProveidos = new dojo.data.ItemFileReadStore({url: "autocompletarProveido.action"});

         function validarForm(){
            //Validaciones del formulario caso general
            if(dijit.byId("iddestinatario").state||dijit.byId("iddestinatario").value==""){
               alert("Debe especificar Para.");
               dijit.byId("iddestinatario").focus();
               return false;
            }
            return true;
         }
         
         function derivar(){
            if(validarForm()){
               if(confirm("Esta seguro que desea enviar todos los documentos?")){
            	   if(dijit.byId("dlgProgresBar")){
            		   dijit.byId("dlgProgresBar").show();
            	   }
	              document.getElementById("objDD.strTexto").value = dijit.byId("contenid").getValue(false);
	              document.forms["frmDerivManual"].action = 'doDerivarUSER.action';
                      document.forms["frmDerivManual"].submit();
               }
            }
         } 

         function subir() {
            document.forms[0].action = 'doUploadUserManual.action' ;
            document.forms[0].submit();
         }

         function refrescar() {
            try{
                window.opener.showGridInbox();
                window.opener.refreshTabDXE();
                this.window.close();
             }catch(err){
                this.window.close();
             }
         }

         function desabilitar(){
            var valor = document.getElementById("dialimiteatencion").value;
            var valor1 = document.getElementById("fechalimiteatencion").value ;
            if(valor!="") {
               form.fechalimiteatencion.disabled  = true;
            }else{
               form.fechalimiteatencion.disabled  = false;
            }
         }

         function formshowhide(id){
            if(id == "quote"){
               clearfecha();
               document.getElementById('quotef').style.display = "block";
               document.getElementById('otherf').style.display = "none";
               document.getElementById('objDD.strFechaLimiteAtencion').value="";
            }else{
               clear();
               document.getElementById('quotef').style.display = "none";
               document.getElementById('otherf').style.display = "block";
            }
         }
         
         function clear(){
            document.getElementById('dia').value="";
            document.getElementById('hora').value="";
            toolDwr.calculaFechaLimite("","0", function(data){
               dwr.util.setValue("fechaLimete", data);});
         }

         function clearfecha(){
            document.getElementById('fecha').value="";
         }
 			
         function calculaFechaLimete(){
            var dia = dwr.util.getValue("dia");
            var hora = dwr.util.getValue("hora");
            if(hora==""){hora="0";}
            toolDwr.calculaFechaLimite(dia,hora, function(data){
               dwr.util.setValue("fechaLimete", data);
            });
         }
             
      </script>
   </head>
   <body class="soria" <s:if test="cerrar!=null">onload="refrescar();"</s:if>>
   <s:if test="cerrar==null">
      <table width="100%" border ="0">
         <tr>
            <td height="14" colspan="6"></td>
         </tr>
         <tr align="center">
            <td width="1%" align="center">&nbsp;</td>
            <td colspan="5" align="left">
                                         
               <img id="idbtnEnviar"  onClick="derivar()" src="images/enviar.bmp" border="0" alt="Enviar" style="cursor: pointer;" class="aprobar"/>
            </td>
         </tr>
         <tr>
            <td></td>
            <td height="13" colspan="4" align="left" class="plomo">
               <div id="divShowFile1"></div>
            </td>
         </tr>
         <tr>
            <td></td>
            <td height="12" colspan="4" class="plomo">
               <s:form name="frmDerivManual" action="doDerivarUSER" method="POST">
                  <s:hidden name="objDD.iIdDocumento" />
                  <s:hidden id="objDD.iIdProceso" name="objDD.iIdProceso" />
                  <s:hidden name="sTipoDerivacion" />
                  <table width="100%" border="0">
                     <tr>
                        <td></td>
                        <td>
                           Se enviaran masivamente los documentos seleccionados previamente 
                        </td>
                        <td></td>
                     </tr>
                     <tr>
                        <td>
                           <button dojoType="dijit.form.Button"
                                      type="button"
                                      id="btnPara"
                                      jsId="btnPara"
                                      onClick="showGrdPara('iddestinatario')"
                                      showLabel="true">Para</button>
                        </td>
                        
                        
                        
                         <td>
                              <div dojoType="dijit.form.FilteringSelect"
                                   store="store"
                                   id="iddestinatario"
                                   jsId="iddestinatario"
                                   idAttr="id"
                                   required= "true"
                                   labelAttr="label"
                                   searchAttr="label"
                                   name="iddestinatario"
                                   style="width:380px"
                                   size="25"></div>
                             
                                   
                        </td>
                
                        <td></td>
                     </tr> 
                     <tr>
                         <td></td>
                         <td>
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
                        <td></td> 
                     </tr>
			
		     <tr>
			 <td colspan="3"><br/></td>
		     </tr>					 
                     <tr>
                        <td></td>
                        <td>
                           <s:textfield id="objDD.strTexto" name="objDD.strTexto" cssStyle="display:none" />
                           <s:textfield id="ta" name="ta" cssStyle="display:none" />
                           <div dojoType="dijit.Editor" jsid="contenid" id="contenid" style="background-color: #fff;width: 95%;"></div>
                           <script type="text/javascript">
                            //  document.getElementById("contenid").innerHTML = document.getElementById("ta").value;
                           </script>
                        </td>
                        <td><br></td>
                     </tr>
                     
                     <tr>
                            <td></td>
                            <td  colspan="2">
                              <input  size="67"   style="font-weight: bold;font-size:14px;"
					type="text" name="ta" id="ta"
                                        value="<s:property value='ta'  />" /> 
                            </td>                                
                     </tr>    
                     
                  </table>
               </s:form>
            </td>
         </tr>
         <tr>
            <td height="12"  colspan="6"></td>
         </tr>
      </table>
      </s:if>
      <s:else>
           <p style="text-align: center;">Operaci&oacute;n realizada
			satisfactoriamente</p>
            
      </s:else>
      <%@ include file="derivar-dialogs.jsp" %>
      <%@ include file="../pages/util/progressBar.jsp"%>
   </body>
</html>
