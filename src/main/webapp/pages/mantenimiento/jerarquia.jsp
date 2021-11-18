<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
   <head>
      <title>Mantenimiento de Usuarios</title>
      <link rel="stylesheet" type="text/css" href="css/estilo.css" />
      <style type="text/css">
         @import "js/dojo/dijit/themes/nihilo/nihilo.css";
         @import "js/dojo/dijit/themes/soria/soria.css";
         @import "js/dojo/dijit/themes/tundra/tundra.css";
      </style>
      <style type="text/css" media="screen">
         #divTreeJerarquia {
            border: 1px solid #669BCD;
            left: 10px;
            height: 750px;
            overflow: scroll;
            position: absolute;
            top: 70px;
            width: 600px;
         }

         #divViewUsuario {
            position: absolute;
            right: 50px;
            top: 70px;
            width: 400px;
         }
      </style>
      <script type="text/javascript" src="js/dojo/dojo/dojo.js" djConfig="parseOnLoad:true, isDebug: false"></script>
      <script type="text/javascript">
         dojo.require("dijit.Tree");
         dojo.require("dojo.data.ItemFileReadStore");
         dojo.require("dojo.rpc.JsonService");

         var treeOnClick = function(objNodo) {
            console.log("Datos del nodo seleccionado id[" + objNodo.id + "] name[" + objNodo.name + "]");

            dojo.xhrPost({
               url: "run_mantenimiento_viewUsuario.action",
               content: {
                  iIdUsuario: objNodo.id
               },
               mimetype: "text/html",
               load: function(data){
                  dojo.byId('divViewUsuario').innerHTML = data;
               }
            });
         }

         dojo.addOnLoad(function(){
            var service = new dojo.rpc.JsonService("SMDAction.action");
            var defered = service.getJerarquia();

            defered.addCallback(function(objJSON) {
               new dijit.Tree({
                  model: new dijit.tree.ForestStoreModel({
                     store: new dojo.data.ItemFileReadStore({data: objJSON}),
                     query: {top: true},
                     rootId: "jerarquia",
                     rootLabel: "Jerarquia",
                     childrenAttrs: ["children"]
                  }),
                  showRoot: false,
                  //openOnClick: true,
                  onClick: treeOnClick
               }, "divTreeJerarquia");
            });
         });
      </script>
   </head>
   <body class="nihilo">
      <table width="100%">
         <tr>
            <td height="4" colspan="6"></td>
         </tr>
         <tr>
            <td height="20" colspan="6" width="99%">
               <table width="99%" border="0" align="center">
                  <td width="0%" height="14" rowspan="2"> </td>
                  <td width="29%" align="left">
                     <font color="669BCD">Bienvenido </font><font color="0099FF"><s:property value="#session.nombres" /></font>
                  </td>
                  <td width="19%" rowspan="2"></td>
                  <td width="34%" rowspan="2" align="right"></td>
                  <td width="16%" rowspan="2" align="right"></td>
                  <td width="2%" rowspan="2"></td>
                  <tr>
                  <td align="left">
                     <font color="0099FF"><a href="<s:url action="doLogout" />" target="_parent">Cerrar Sesi&oacute;n</a></font>
                  </td>
               </table>
            </td>
         </tr>
         <tr>
            <td height="4" colspan="6"></td>
         </tr>
         <tr>
            <td height="20" colspan="6" class="titulo" width="99%">
               <table width="70%" border="0" height="20" align="left">
                  <tr>
                     <td bgcolor="#4481B8" width="30%" align="center" class="tituloRojo">Jerarqu&iacute;a</td>
                     <td></td>
                  </tr>
               </table>
            </td>
         </tr>
         <tr>
            <td colspan="6">
               <div id="divTreeJerarquia"></div>
               <div id="divViewUsuario"></div>
            </td>
         </tr>
      </table>
   </body>
</html>
