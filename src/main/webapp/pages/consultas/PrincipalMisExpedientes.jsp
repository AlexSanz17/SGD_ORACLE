<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Sistema de Gesti&oacute;n Documentaria - Carpetas de B&uacute;squeda</title>
		<style type="text/css">
		<!--
		html,body,#divConsulta4 {
			width: 100%;
			height: 100%;
			border: 0;
			padding: 0;
			margin: 0;
			overflow: hidden;
		}
		-->
		</style>
		<script type="text/javascript" src="js/siged/layout/misExpediente.js"></script>
	</head>
	<body>
		<!--<span id="toolbarME">
	        </span>  -->
		<%--<script type="text/javascript">
	            dojo.addOnLoad(function() {
	                if (document.pub) {
	                    document.pub();
	                }
	            });
	        </script>
	         --%>
	    <div dojoType="dijit.TitlePane" id="titlePaneFiltroMisExpedientes" open="true" title="Filtro">
             <div dojoType="dojox.layout.ContentPane" id="contentPaneFiltroBusquedaAvanzadaMisExpedientes" jsId="contentPaneFiltroBusquedaAvanzadaMisExpedientes" href="pages/consultas/FiltrosMisExpedientes.html" title="Datos B&aacute;sicos" style="width:100%;height:70px;"></div>
        </div>
		<div dojoType="dijit.layout.BorderContainer" id="borderContainerConsultaME" jsId="borderContainerConsultaME" style="width: 100%; height: 100%;">
			<div dojoType="dojox.layout.ContentPane" region="center" id="centralConsultaME" style="height: 45;">
				<div id="gridME" style="width: 100%; height: 87%;">
					<!-- creamos la  div con el nombre cargando    le damos los estilos  class="cargando" lo cual tiene una imagen y un texto -->
					<div id="cargandoConsultaME" style="display: none;" class="cargando">
						<img src="images/cargandoGrid.gif" alt="Cargando" /> <strong>Cargando...</strong>
					</div>
				</div>
			</div>
			<!--<div dojoType="dijit.layout.TabContainer" id="tabContainerDetailConsultaME" jsId="tabContainerDetailConsultaME" region="bottom" splitter="true" style="width: 100%; height: 50%;" tabStrip="true">
				<div dojoType="dojox.layout.ContentPane" id="contentPaneDocumentosAdicionalesConsultaME" jsId="contentPaneDocumentosAdicionalesConsultaME" title="Documentos"></div>
			</div> -->
		</div>
		<!--<div dojoType="dijit.Dialog" id="dlgTrazabilidadConsultaME" jsId="dlgTrazabilidadConsultaME" draggable="true" style="display: none; width: 60%; height: 100%; overflow: auto;" title="Trazabilidad del expediente"></div> -->
	</body>
</html>





