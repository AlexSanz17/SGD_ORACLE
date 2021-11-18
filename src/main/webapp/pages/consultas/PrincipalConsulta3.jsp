<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>Sistema de Gesti&oacute;n Documentaria - B&uacute;squeda Avanzada</title>
        <script type="text/javascript" src="js/siged/layout/consulta3.js"></script>
    </head>
    <body>
        <div dojoType="dijit.TitlePane" id="titlePaneFiltroConsulta3" jsId="titlePaneFiltroConsulta3" open="true" title="Filtro de Documentos Recepcionados">           
			<div dojoType="dojox.layout.ContentPane" id="contentPaneFiltroConsulta3" jsId="contentPaneFiltroConsulta3" href="pages/consultas/FiltrosConsulta3.html" title="Documentos Recepcionados" style="width:100%;height:140px;"></div>
        </div>

        <div dojoType="dijit.layout.BorderContainer" id="borderContainerConsulta3" jsId="borderContainerConsulta3" style="width:100%;height:95%;background-color:#FFFFFF">                        
            <div id="centralConsulta3" dojoType="dijit.layout.ContentPane" style="width:100%;height:50%;overflow:auto;" >
                <div id="idToolbarC3" dojoType="dijit.Toolbar" style="width:100%;display:none;">
                	<button  dojoType=dijit.form.Button type="button" onClick="doImprimirConsulta3()" showLabel="true" >Imprimir</button>
                </div>
                <div id="mensajeErrorConsulta3" style="width: 100%;height: 280px;text-align: center;display:none;">No se encontro ning&uacute;n resultado para la b&uacute;squeda.</div>
                <div id="gridConsulta3" style="width:100%;"></div>     
            </div> 
            <div dojoType="dijit.layout.TabContainer" id="tabContainerDetailConsulta3" jsId="tabContainerDetailConsulta3" region="bottom" splitter="true" style="width:100%;height:50%;"  tabStrip="true">
                <div dojoType="dojox.layout.ContentPane" id="contentPaneDetailConsulta3" jsId="contentPaneDetailConsulta3" title="General"></div>
                <div dojoType="dojox.layout.ContentPane" id="contentPaneDocumentosAdicionalesConsulta3" jsId="contentPaneDocumentosAdicionalesConsulta3" title="Documentos"></div>
            </div>
        </div>
         
        <div dojoType="dijit.Dialog"
             id="dlgTrazabilidadConsulta3"
             jsId="dlgTrazabilidadConsulta3"
             draggable="true"
             style="display:none;width:60%;height:100%;overflow:auto;"
             title="Trazabilidad del expediente">
        </div>
        <!-- creamos la  div con el nombre cargando    le damos los estilos  class="cargando" lo cual tiene una imagen y un texto -->
        <div id="cargandoConsulta3" style="display: none;" class="cargando">
            <img src="images/cargandoGrid.gif" alt="Cargando" />
            <strong>Cargando...</strong>
        </div>
    </body>
</html>