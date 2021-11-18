<?xml version="1.0" encoding="UTF-8" ?>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es">
   <head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
      <title>Sistema de Gesti&oacute;n Documentaria - Carpetas de B&uacute;squeda</title>
      <style type="text/css">
         <!--
         html, body, #divCarpetasBusqueda {
            width: 100%; height: 100%;
            border: 0; padding: 0; margin: 0;
            overflow: hidden;
         }
         -->
      </style>


      <script type="text/javascript">
		function imprimirCarpetaBusqueda(){
			var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=1000, height=700, top=50, left=200";
			var url = "imprimirGridCB.action?idCarpetaBusqueda="+iIdCarpetaBusqueda+"";
			window.open(url, "", opciones);
		}
		function exportarCarpetaBusqueda(){
			var service = new dojo.rpc.JsonService("SMDAction.action");
			var defered = service.exportarExcelCarpetaBusqueda(iIdCarpetaBusqueda + "");

            defered.addCallback(function(result) {
            	location.href = result;
            });
		}
         dojo.addOnLoad(function() {
            var divCarpetasBusqueda = dojo.byId("divCarpetasBusqueda");
            new dijit.form.Button({
          	  id: "btnConsultaImprimir",
          	  label : "Imprimir",
          	  onClick : function(){imprimirCarpetaBusqueda();}
            });
            new dijit.form.Button({
            	  id: "btnConsultaExportarExcel",
            	  label : "Exportar Excel",
            	  onClick : function(){exportarCarpetaBusqueda();}
              });
            buildCarpetasBusqueda(divCarpetasBusqueda);
         });
      </script>
   </head>
   <body>
      <div id="divCarpetasBusqueda"></div>
      <div dojoType="dijit.Dialog"
           id="dlgCarpetasBusqueda"
           jsId="dlgCarpetasBusqueda"
           draggable="false"
           style="display:none;width:800px;"
           title="Registro de Carpeta de B&uacute;squeda">
         <%@ include file="../carpetasbusqueda/frmCarpetasBusqueda.jsp" %>
      </div>
   </body>
</html>