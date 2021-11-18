<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd"> 
<html>
   <head>  
      <title>Sistema de Gesti&oacute;n Documentaria</title>  
      <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">  
      <script>
        
      </script> 
   </head>  
   <body>
   <div dojoType="dijit.TitlePane"
           id="titleSeguimiento"
           jsId="titleSeguimiento"
           open="true"
           title="Seguimiento"
           href="Seguimiento_mostrarSemanal.action?filtro1=todos&iIdProceso=0">
           
    </div>
      <div dojoType="dijit.layout.TabContainer"
           id="seguimientoDocumentoDetail"
           jsId="seguimientoDocumentoDetail"
           region="bottom"
           splitter="true"
           style="width:97%;height:95%;overflow: visible " 
           tabStrip="true">
         <div dojoType="dojox.layout.ContentPane"
              id="contentGeneralSeguimiento"
              jsId="contentGeneralSeguimiento" 
              
              title="General"></div>
         <div dojoType="dojox.layout.ContentPane"
              id="seguimientoDocumento"
              jsId="seguimientoDocumento"
               
              title="Documentos"></div>
      </div>
   </body>
</html>