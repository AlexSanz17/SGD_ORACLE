<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
   <head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
      <title>Sistema de Gesti&oacute;n Documentaria - Grid Reemplazos</title>
      <style type="text/css">
         <!--
         html, body {
            width: 100%; height: 100%;
            border: 0; padding: 0; margin: 0;
            overflow: hidden;
         }
         -->
      </style>
      <script type="text/javascript" src="js/siged/layout/reemplazo.js"></script>
   </head>
   <body>
      <div dojoType="dijit.Toolbar">
         <div dojoType="dijit.form.Button"
              iconClass="siged16 sigedBack16"
              label="Eliminar"
              onClick="eliminarReemplazo"
              showLabel="true"></div>
      </div>
      <div dojoType="dojox.grid.DataGrid"
           id="gridReemplazo"
           jsId="gridReemplazo"
           canSort="sortMe"
           onRowClick="viewDetailReemplazo"
           style="width:100%;height:50%;"></div>
      <div dojoType="dijit.layout.TabContainer"
           id="tabContainerDetailReemplazo"
           jsId="tabContainerDetailReemplazo"
           splitter="true"
           style="width:100%;height:50%;"
           tabStrip="true">
         <div dojoType="dojox.layout.ContentPane"
              id="contentPaneDetailReemplazo"
              jsId="contentPaneDetailReemplazo"
              title="Informaci&oacute;n del Reemplazo"></div>
      </div>
   </body>
</html>
