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

         function tryToCloseWindow() {
            if (closeMe != "") {
               this.window.opener.showGridInbox();
               this.window.close();
            }
         }

         function notificar(){
            if(validarForm()){

               document.getElementById("correoNotificarReporte8").submit();
            }
         }

         function validarForm(){


            return true;
         }



         dojo.addOnLoad(function() {


         });
      </script>
   </head>
   <body class="soria" onload="tryToCloseWindow();">
   <s:if test="sCloseMe == null">
      <table width="100%">
         <tr align="center">
            <td width="1%" align="center"></td>
            <td colspan="4" align="left">
               <img src="images/enviar.bmp" alt="Enviar" style="float: left;" onclick="javascript:notificar();" />
            </td>
         </tr>
         <tr>
            <td height="16" colspan="5" align="left" class="plomo">
               <form id="correoNotificarReporte8" action="doNotificarCorreoReporte8.action" method="post">
                   <input type="hidden" id="urlReporte"  name="urlReporte"  value="<s:property value='urlReporte'/>"/>
                   <table width="100%">
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
