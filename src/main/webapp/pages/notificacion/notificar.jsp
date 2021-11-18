<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
   <head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
      <title>Enviar Notificaci&oacute;n</title>
      <link type="text/css" rel="stylesheet" href="css/styleSiged.css" />
      <link type="text/css" rel="stylesheet" href="js/dojo/css/styleDojo.css" />
      <script type="text/javascript" src="js/dojo/dojo/dojo.js" djConfig="parseOnLoad:true, isDebug: false"></script>
      <script type="text/javascript" src="js/dojo/dijit/dijit.js" djConfig="parseOnLoad:true, isDebug: false"></script>
      <script type="text/javascript" src="js/siged/siged.js"></script>
      <script type="text/javascript" src="js/siged/siged.forms.js"></script>
		<script type="text/javascript" src="js/siged/siged.string.js"></script>
      <script type="text/javascript" src="js/siged/derivacion.js"></script>
      <script type="text/javascript">
         dojo.require("dijit.form.FilteringSelect");
         dojo.require("dojo.data.ItemFileReadStore");
         //dojo.require("dojox.data.QueryReadStore");
         dojo.require("dojo.rpc.JsonService");
         dojo.require("dijit.Editor");
         dojo.require("dijit._editor.plugins.FontChoice");  // 'fontName','fontSize','formatBlock'
         dojo.require("dijit._editor.plugins.TextColor");
         dojo.require("dijit._editor.plugins.LinkDialog");
      </script>
      <script type="text/javascript" src="js/jquery.js"></script>
      <script type="text/javascript">
         var closeMe = "<s:property value='sCloseMe' />";
         var service = new dojo.rpc.JsonService("SMDAction.action");
          var USUARIOACTIVO = "<s:property value='#session._USUARIO.idUsuarioPerfil'/>-<s:property value='#session._USUARIO.idUnidadPerfil' />-<s:property value='#session._USUARIO.idFuncionPerfil'/>";
          
         function tryToCloseWindow() {



            if (closeMe != "") {
               this.window.opener.showGridInbox();
               this.window.close();
            }
         }

         function notificar(){
            if(validarForm()){
               document.getElementById("objNotificacion.contenido").value = dijit.byId("contenid").getValue(false);
               document.getElementById("notificar").submit();
            }
         }

         function validarForm(){
            var asunto=$("#asunto").val();
            if(asunto.length>100){
               if(confirm("El asunto tiene mas de 100 caracteres, desea que sea truncado a 100 caracteres?")){
                  $("#asunto").val(asunto.substring(0,100));
               }
               else{
                  return false;
               }
            }

            var contenido=dijit.byId("contenid").getValue();
            //alert(contenido);
            if(contenido.length>3990){
               if(confirm("El contenido xx no debe tener mas de 3990 caracteres, desea que sea truncado a 3990 caracteres?")){
                  dijit.byId("contenid").setValue(contenido.substring(0,3990));
               }
               else{
                  return false;
               }
            }

            return true;
         }

         /*var agregarConCopia=function(){
            //var nombre=document.getElementById("iddestinatarios").value;
            //var id=dijit.byId("iddestinatarios").getValue();
            var nombre = dijit.byId("idconcopia").attr("displayedValue");
            var id = dijit.byId("idconcopia").attr("value");

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
         };*/

         dojo.addOnLoad(function() {
            /*var store = new dojo.data.ItemFileReadStore({url: "autocompletarNotificar.action?idDocumento=" + <s:property value='objNotificacion.iddocumento.idDocumento' />});

            store.comparatorMap = {};
            store.comparatorMap["label"] = function(a,b) {
               var ret = 0;
               if (a.toLowerCase() > b.toLowerCase()) {
                  ret = 1;
               }
               if (a.toLowerCase() < b.toLowerCase()) {
                  ret = -1;
               }
               return ret;
            };

            var sortAttributes = [{attribute: "label", descending: false}];

            new dijit.form.FilteringSelect({
               id: "idconcopia",
               jsId: "idconcopia",
               searchAttr		: "label",
               store:store,
               required		: false,
               hasDownArrow	: true,
               highlightMatch	: "all",
               onChange		: agregarConCopia,
               fetchProperties :{sort: sortAttributes}
            },"iddestinatarios");*/


         });
      </script>
   </head>
   <body class="soria" onload="tryToCloseWindow();">
   <s:if test="sCloseMe == null">
      <table width="100%" border="0">
         <tr align="center">
            <td colspan="5" align="left">
               &nbsp;&nbsp;<img src="images/enviar.bmp" alt="Enviar" style="float: left;" onclick="javascript:notificar();" />
            </td>
         </tr>
         <tr>
            <td height="16" colspan="5" align="left" class="plomo">
               <form id="notificar" action="doNotificarUSER.action" method="post">
               	  <input type="hidden" id="objNotificacion.idnotificacion"  name="objNotificacion.idnotificacion"  value="<s:property value='objNotificacion.idnotificacion'/>"/>
                  <table width="100%">
                     <s:if test="iddestinatario == null || iddestinatario == 0">
                        <tr>
                           <td></td>
                           <td height="16" align="left" class="plomo">
                              <button dojoType="dijit.form.Button"
                                   type="button"
                                   id="btnCc"
                                   jsId="btnCc"
                                   onClick="showGrdPara('idconcopia')"
                                   showLabel="true">Cc</button>
                           </td>
                           <td colspan="4">
                              <input id="iddestinatarios" />
                           </td>
                        </tr>
                        <tr>
                           <td colspan="2"></td>
                           <td colspan="4">
                              <div id="copias"></div>
                           </td>
                        </tr>
                     </s:if>
                     <tr>
                        <td></td>
                        <td height="16" align="left" class="plomo">Asunto:</td>
                        <td colspan="4">
                        <input id="asunto"  name="objNotificacion.asunto"  class="cajaMontoTotal" size="80"  maxlength="100" readonly="true" type="text" value="<s:property escape="false"   value="objNotificacion.asunto"/>">
                           <%--
                           <s:textfield id="asunto" name="objNotificacion.asunto" cssClass="cajaMontoTotal" size="80" required="false" maxlength="100" />
                           --%>


                     </td>
                     </tr>
                     <tr>
                        <td></td>
                        <td colspan="5" class="normal">
                     <s:hidden id="objNotificacion.iddocumento.idDocumento" name="objNotificacion.iddocumento.idDocumento" />
                     <s:hidden id="objNotificacion.contenido" name="objNotificacion.contenido" />
                     <s:hidden name="objNotificacion.tiponotificacion" />
                     <div dojoType="dijit.Editor" jsid="contenid"  id="contenid" style="background-color: #FFF;" >
                     </div>
                     <script type="text/javascript">

                        var cadena  = document.getElementById("objNotificacion.contenido").value;
                        //document.getElementById("contenid").innerHTML = cadena;
                        //cadena  =document.getElementById("contenid").innerHTML;
                        //alert("cadena1:"+cadena);
                        //cadena = cadena.replace(/<P><\/P>/g,"");
                        //alert("cadena2:"+cadena);
                        document.getElementById("contenid").innerHTML = cadena.substring(0,3999);
                        //cadena=document.getElementById("contenid").innerHTML;
                        //alert(cadena);


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
      <%@ include file="../../examples/derivar-dialogs.jsp" %>
   </s:if>
</body>
</html>
