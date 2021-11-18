<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es">
   <head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
      <title>Sistema de Gesti&oacute;n Documentaria - Carpetas de B&uacute;squeda</title> 
      <style type="text/css">
         <!--
         html, body, #divConsulta4 {
            width: 100%; height: 100%;
            border: 0; padding: 0; margin: 0;
            overflow: hidden;
         }
         -->
      </style>        
      <script type="text/javascript" src="js/siged/layout/consulta4.js"></script>
   </head>
   <body>
   <span id="toolbarC4">
        </span>
        <%--<script type="text/javascript">
            dojo.addOnLoad(function() {
                if (document.pub) {
                    document.pub();
                }
            });
        </script>
         --%>
         <div dojoType="dijit.layout.BorderContainer"  id="borderContainerConsulta4" jsId="borderContainerConsulta4" style="width:100%;height:100%;">                        

            <div dojoType="dojox.layout.ContentPane" region="center" id="centralConsulta4" style="height:280px;">             
             
             <div id="gridC4" style="width:100%; height:87%;"></div>    
                
            </div> 
            <div dojoType="dijit.layout.TabContainer" id="tabContainerDetailConsulta4" jsId="tabContainerDetailConsulta4" region="bottom" splitter="true" style="width:100%;height:50%;" tabStrip="true">
                <div dojoType="dojox.layout.ContentPane" id="contentPaneDetailConsulta4" jsId="contentPaneDetailConsulta4" title="General"></div>
                <div dojoType="dojox.layout.ContentPane" id="contentPaneDocumentosAdicionalesConsulta4" jsId="contentPaneDocumentosAdicionalesConsulta4" title="Documentos"></div>
            </div>
        </div>

        <div dojoType="dijit.Dialog"
             id="dlgTrazabilidadConsulta4"
             jsId="dlgTrazabilidadConsulta4"
             draggable="true"
             style="display:none;width:60%;height:100%;overflow:auto;"
             title="Trazabilidad del expediente">
        </div>
        
        <!-- creamos la  div con el nombre cargando    le damos los estilos  class="cargando" lo cual tiene una imagen y un texto -->
        <div id="cargandoConsulta4" style="display: none;" class="cargando">
            <img src="images/cargandoGrid.gif" alt="Cargando" />
            <strong>Cargando...</strong>
        </div>
       
   </body>
</html>



       
        
        