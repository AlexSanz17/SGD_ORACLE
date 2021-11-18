<?xml version="1.0" encoding="UTF-8" ?>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es">
       <head>
            <title>Navegador</title>
             
           
            <style type="text/css">
                @import "css/siged.css";
                @import "css/sigedIconos.css";
                @import "css/transferSelect.css";
                @import "js/dojo/dojo/resources/dojo.css";
                @import "js/dojo/dijit/themes/nihilo/nihilo.css";
                @import "js/dojo/dojox/grid/resources/nihiloGrid.css";
                @import "js/dojo/dijit/themes/soria/soria.css";
                @import "js/dojo/dojox/grid/resources/soriaGrid.css";
                @import "js/dojo/dijit/themes/tundra/tundra.css";
                @import "js/dojo/dojox/grid/resources/tundraGrid.css";
                @import "js/dojo/dojox/layout/resources/ExpandoPane.css";
                @import "js/dojo/dojox/layout/resources/ToggleSplitter.css";
            </style>
            
            <script type="text/javascript">
                var djConfig = {
                    isDebug: false,
                    parseOnLoad: true
                };

             </script>
            
            <script type="text/javascript" src="js/dojo/dojo/dojo.js"></script>	
            <script type="text/javascript" src="js/dojo/dijit/dijit.js"></script>
            <script type="text/javascript" src="js/jquery.js"></script>
            <script type="text/javascript">
                dojo.require("dijit.Dialog");
                dojo.require("dijit.form.Button");
                dojo.require("dijit.form.DateTextBox");
                dojo.require("dijit.form.FilteringSelect");
                dojo.require("dijit.form.Form");
                dojo.require("dijit.form.TextBox");
                dojo.require("dijit.form.ValidationTextBox");
                dojo.require("dojo.data.ItemFileWriteStore");
                dojo.require("dojo.data.ItemFileReadStore");
                dojo.require("dojo.rpc.JsonService");
                dojo.require("dojox.grid.DataGrid"); 
                
              function vistaTipoDocumentos(){
                   location.href="verDocumento.png?accion=cargarBandejaTipoDocumentosNavegador&fecha=" + new Date();
              }
              function vistaUnidad(){
                   location.href="verDocumento.png?accion=cargarBandejaUnidadNavegador&fecha=" + new Date();
              }
              function setearDocumento(){
                top['detalleNavegador'].location.href= "/SGD/documentosNavegador.jsp";
              }
           </script>         
           <script type="text/javascript" src="js/dojo/dojo/dojo.js"></script>	
         
           
             <style type="text/css">
                @import "css/siged.css";
                @import "css/sigedIconos.css";
                @import "css/transferSelect.css";
                @import "js/dojo/dojo/resources/dojo.css";
                @import "js/dojo/dijit/themes/nihilo/nihilo.css";
                @import "js/dojo/dojox/grid/resources/nihiloGrid.css";
                @import "js/dojo/dijit/themes/soria/soria.css";
                @import "js/dojo/dojox/grid/resources/soriaGrid.css";
                @import "js/dojo/dijit/themes/tundra/tundra.css";
                @import "js/dojo/dojox/grid/resources/tundraGrid.css";
                @import "js/dojo/dojox/layout/resources/ExpandoPane.css";
                @import "js/dojo/dojox/layout/resources/ToggleSplitter.css";
            </style>
      </head>
	<body  class="nihilo" onload="setearDocumento();">
                <br/>
                <script type="text/javascript" src="js/documentosNavegador.js"></script> 
                
                <table width="100%" border="0">
                    <tr>
                        <td colspan="6" align="left">
                           &nbsp;&nbsp;&nbsp;&nbsp;<div dojoType="dijit.form.Button"  onClick="vistaTipoDocumentos()" iconClass="siged16 sigedCarpeta16">Vista Por Tipo Documento</div>
                           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<div dojoType="dijit.form.Button"  onClick="vistaUnidad()" iconClass="siged17 sigedVisorUnidad"> Vista Por Area Autor</div>
                        </td>
                    </tr>
                    
                    <tr>
                            <td colspan="4" style="height:1px"> <hr/>  </td>
                    </tr>
                    <tr>   
                            <td width="40px"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src='images/leaf.png'></img> </td>
                            <td colspan="3" align="top">&nbsp;<b>Vista Actual: Por Documento </b>  </td>
                            
                    </tr>
                    <tr>
                            <td colspan="4" style="height:1px"> <hr/>  </td>
                    </tr>
                   
                </table>    
                
                
                
                <div id="divTreeDocumentosNavegadorRecibidos" class="arbol"></div>
		<div id="displayTreeDocumentosNavegadorRecibidos" class="detalleArbol"></div>
                
	  	
	</body>
</html>