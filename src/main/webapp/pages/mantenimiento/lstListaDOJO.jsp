<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
   <head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
      <title>Sistema de Gesti&oacute;n Documentaria - Grid Listas</title>
      <style type="text/css">
         <!--
         html, body {
            width: 100%; height: 100%;
            border: 0; padding: 0; margin: 0;
            overflow: hidden;
         }
         -->
      </style>
      <script type="text/javascript" src="js/siged/layout/lista.js"></script>
   </head>
   <body>
      <div dojoType="dojox.grid.DataGrid"
           id="gridLista"
           jsId="gridLista"
           onRowClick="viewDetailLista"
           style="width:100%;height:50%;"></div>
      <div dojoType="dijit.layout.TabContainer"
           id="tabContainerDetailLista"
           jsId="tabContainerDetailLista"
           splitter="true"
           style="width:100%;height:50%;"
           tabStrip="true">
         <div dojoType="dojox.layout.ContentPane"
              id="contentPaneDetailLista"
              jsId="contentPaneDetailLista"
              title="Informaci&oacute;n de la Lista"></div>
      </div>
   </body>
</html>
