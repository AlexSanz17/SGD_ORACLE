<?xml version="1.0" encoding="UTF-8" ?>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<input type="hidden" value="" />
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>Bandeja de Usuario</title>
		<style type="text/css">
		@import "js/dojo/dijit/themes/tundra/tundra.css";
		@import "js/dojo/dojox/grid/resources/tundraGrid.css";
		@import "css/grid.css";
		@import "css/estilo.css";
		.empty {
			color: #A0A0A0;
		}
		</style>
		<script type="text/javascript" src="js/dojo/dojo/dojo.js" djConfig="parseOnLoad:true, isDebug: false"></script>
		<script type="text/javascript" src="js/dojo/dojox/widget/PlaceholderMenuItem.js"></script>
		<script type="text/javascript">
			dojo.require("dojo.data.ItemFileWriteStore");
			dojo.require("dojo.rpc.JsonService");
			dojo.require("dojox.grid.DataGrid");
			dojo.require("dijit.Dialog");
			dojo.require("dijit.ProgressBar");
		</script>
		<script type="text/javascript" src="js/siged/grid.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/jquery-defaultvalue.js"></script>
		<script type="text/javascript">
         var showOnlineHelp = function(sRol) {
            var sURL = "";

            console.debug("Rol del usuario logeado [%s]", sRol);

            if (sRol != undefined && sRol != "") {
               if (sRol == "Siged - Mesa de Partes") {
                  sURL = "ayuda/Mesa de Partes/Mesa de Partes.htm";
               } else if (sRol == "Siged - Digitalizador") {
                  sURL = "ayuda/Digitalizador/Digitalizador.htm";
               } else if (sRol == "Siged - Control de Calidad") {
                  sURL = "ayuda/Control de Calidad/Control de Calidad.htm";
               }
            } else {
               sURL = "ayuda/User Final/User Final.htm";
            }

            console.debug("URL general [%s]", sURL);
            window.open(sURL, "", "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=800, height=600, top=180, left=200");
         };

         var RECURSO_HISTORICO_ACTIVO = "<s:property value='#session._RECURSO.ExpHisRead' />";

			function nuevoDoc() {
				parent.location.href = "NuevoDocumento_inicio.action";
			}
		
			function verSeguimiento() {
				parent.location.href = "Seguimiento_mostrarInicio.action";
			}
		
			function nuevoDocSas() {
				parent.location.href = "CargarframeSAS.action";
			}
		
			$(document).ready(function() {
				$("input[name='sParametroBusqueda']").defaultValue("Buscar...");
				$("#doSearchinner").submit(function() {
		
					var val = $(".empty").length <= 0;
					//alert('jojo 1'); 
						if (val) {
							//alert('jojo 2') ; 
							dijit.byId('dlgProgresBar').show();
						}
						return val;
					});
			});
		</script>
	</head>
	<body class="tundra">
		<table width="99%">
			<tr>
				<td colspan="6">
					<table>
						<tr>
							<td align="left" style="width:220px;">
								<span>Bienvenido</span>
								<span style="color: blue;"><s:property value="#session._USUARIO.nombres" /> <s:property value="#session._USUARIO.apellidos" /></span>
							</td>
							<td rowspan="2" align="right" style="width:400px;">
                        <a href="javascript:guardarGrid();">Guardar Configuraci&oacute;n</a>
							<s:if test="#session._RECURSO.UsuFinBtnDerMas == 1">
								<img alt="Derivaci&oacute;n Masiva" onclick="derivarMasivamente();"	src="images/derivar.GIF" style="cursor: hand; cursor: pointer;" />
							</s:if>
							<s:if test="#session._RECURSO.UsuFinBtnLis == 1">
								<a href="<s:url action="goFrameLoadListaArray" />" target="_parent">
								<img alt="Listas" src="images/lista.gif" /> </a>
							</s:if>
							<s:if test="#session._RECURSO.UsuFinBtnSeg == 1">
								<img onclick="verSeguimiento();" alt="Seguimiento" id="campo_fecha1" src="images/calendario.bmp" style="cursor: pointer" />
							</s:if>
							<s:if test="#session._RECURSO.UsuFinBtnNueDocPri == 1">
								<img onclick="nuevoDoc();" src="images/botonNuevo.gif" style="cursor: pointer" alt="Nuevo Documento" />
							</s:if>
							<s:if test="#session._RECURSO.nvodocsas == 1">
								<img onclick="nuevoDocSas();" src="images/botonNuevo.gif" style="cursor: pointer" alt="Nuevo Documento" />
							</s:if>
							<s:if test="#session._RECURSO.MPBtnNueDocPri == 1">
								<a href="<s:url action="inicio" ><s:param name="sTipoFrame">nuevodoc</s:param></s:url>"	target="_parent">
									<img alt="Crear Documento" src="images/botonNuevo.gif" />
								</a>
							</s:if>
							</td>
							<td rowspan="1" align="center" valign="middle" style="width: 180px;">
							<s:if test="#session._RECURSO.UsuFinBtnBus == 1">
								<s:form action="doSearchinner" method="post">
									<fieldset style="border: 0; float: left; padding: 0; margin: 0;">
									<s:textfield name="sParametroBusqueda" size="12" />
									<button type="submit" title="Buscar">
										<img src="images/buscar1.png" alt="Buscar" title="Buscar" />
									</button>
									</fieldset>
								</s:form>
								<%-- 
								<a href="pages/tramite/docRecibidosBusq.jsp"> 
								B&uacute;squeda Avanzada 
								</a>
								  
								<img alt="B&uacute;squeda Avanzada"
									title="B&uacute;squeda Avanzada"
									onclick="javascript:location.href='pages/tramite/docRecibidosBusq.jsp';"
									src="images/search.gif"
									style="cursor: pointer; float: left; margin-top: 3px;" />
									--%>
							</s:if>
							</td>
						</tr>
						<tr>
							<td align="left" colspan="3">
								<a style="color: #0099FF;"	href="<s:url action="doLogout" />" target="_parent"	onclick="guardarGrid();">Cerrar Sesi&oacute;n</a>
                        &nbsp;<a href="javascript:showOnlineHelp('<s:property value='#session._USUARIO.rol' />')">Ayuda</a>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td colspan="6" height="10"></td>
			</tr>
		</table>
		<table width="99%">
			<tr>
				<td><%--s:form name="frmDerivacionMasiva" action="doDerivarMasivamente" method="post" target="frmDerivacionMasiva"--%>
					<div dojoType="dijit.Menu" jsid="gridMenu" id="gridMenu" style="display: none;">
						<div dojoType="dojox.widget.PlaceholderMenuItem" label="GridColumns"></div>
					</div>
               <div class="partsContainer">
                  <div class="gridContainer">
                     <table dojoType="dojox.grid.DataGrid" id="grdSiged" jsId="grdSiged" canSort="noSort" headerMenu="gridMenu" rowsPerPage="10" selectionMode="single" columnReordering="true"></table>
                  </div>
               </div>
				<%--/s:form--%>
				</td>
			</tr>
		</table>
		<%--@ include file="../util/progressBar.jsp"--%>
	</body>
</html>