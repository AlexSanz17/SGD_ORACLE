<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title>Tramite Documentario - Asignar Reemplazo</title>
<link rel="stylesheet" type="text/css" href="css/estilo.css" />
<style type="text/css">
@import "js/dojo/dijit/themes/soria/soria.css";

@import "js/dojo/dojox/grid/resources/soriaGrid.css";

@import "css/grid.css";
</style>
<script type="text/javascript" src="js/dojo/dojo/dojo.js"
	djConfig="parseOnLoad:true, isDebug: false"></script>
<script type="text/javascript" src="js/dojo/dijit/dijit.js"
	djConfig="parseOnLoad:true, isDebug: false"></script>

<script type="text/javascript" src='js/siged/date-es-PE.js'></script>

<s:url id="autoUrl" action="recibeNotificacionesLogueado" />
<s:url id="listaUsuarios" action="autocompletarUsuarioxProceso" />



<script type="text/javascript">
         dojo.require("dojo.rpc.JsonService");
         dojo.require("dijit.Dialog");
         dojo.require("dijit.form.TextBox");
         dojo.require("dijit.form.Button");
         dojo.require("dojo.io.iframe");


         dojo.require("dojo.data.ItemFileWriteStore");
         dojo.require("dojo.data.ItemFileReadStore");
         dojo.require("dojox.grid.DataGrid");

         dojo.require("dijit.layout.SplitContainer");
         dojo.require("dijit.layout.ContentPane");
         dojo.require("dijit.layout.TabContainer");


         var jsonStore = new dojo.data.ItemFileWriteStore({url: "<s:property value='autoUrl' />"});
         var storeUsuarios;

         var iddocumento;

      </script>


<script type="text/javascript">

         function abrirVentanaSeguimiento(iIdDoc) {
            var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=no, width=650, height=320, top=50, left=200";
            window.open("/siged/goViewSeguimiento.action?iIdDocumento=" + iIdDoc,"",opciones);
         }

         function dialogAlert(txtTitle, txtContent)
         {
            var thisdialog = new dijit.Dialog({ title: txtTitle, content: txtContent });
            dojo.body().appendChild(thisdialog.domNode);
            thisdialog.startup();
            thisdialog.show();
         }


         function refreshNotificacion(e){
            idnotificacion=edGrid.getItem(e.rowIndex).id;

            console.log("idnotificacion ["+idnotificacion+"]");


            jsonStore.fetchItemByIdentity({
               identity: idnotificacion,

               onItem: function(item){

                  iddocumento=jsonStore.getValue(item,"iddocumento");
                  var inner=""

           <s:if test="#session._RECURSO.UsuFinBtnTraExp">
              inner="&nbsp;&nbsp <img onClick='abrirVentanaSeguimiento("+iddocumento+")' src='images/editar.bmp' border='0' alt='Historial'/><br>";
           </s:if>
                         dojo.byId("toBeReplaced").innerHTML = inner+jsonStore.getValue(item,"contenido");
                         dojo.byId("toBeReplaced2").innerHTML = "<iframe  width='100%' height='100%' frameborder='0'  marginheight='0' marginwidth='0'  src='/siged/doViewDocAdicionales.action?iIdDoc="+iddocumento+"&avisopermiso=0' />";
                      }
                   });

                   dojo.xhrPost({
                      url: "doUpdateEstadoNotificacion.action",
                      content: {
                         iIdNotificacion: idnotificacion
                      },
                      mimetype: "text/html",
                      load: function() {
                         console.log("DONE");
                         self.parent.frames["leftFrame"].location.href = "doMenu.action";
                      }
                   });

                }
      </script>

</head>
<body class="soria">
<table width="100%">
	<tr>
		<td height="20" colspan="6" width="99%">
			<table width="98%" align="center">
				<tr>
					<td width="50%" align="left">Bienvenido <span
						style="color: blue"><s:property value="#session.nombres" /></span>
					</td>
					<td width="1%" rowspan="2"></td>

					<td width="24%" rowspan="2" align="right"></td>

					<td width="20%" rowspan="2" align="right"></td>

					<td width="5%" rowspan="2" align="right"></td>

					<td width="1%" rowspan="2"></td>
				</tr>
				<tr>
					<td align="left"><font color="0099FF"><a
						href="<s:url action="doLogout" />" target="_parent">Cerrar
					Sesi&oacute;n</a></font></td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td colspan="6"></td>
	</tr>
	<tr>
		<td bgcolor="#4481B8" width="30%" align="center" class="tituloRojo"
			height="25">Bandeja de Notificaciones</td>
		<td colspan="5"></td>
	</tr>
	<tr>
		<td colspan="6"></td>
	</tr>

</table>
<s:form name="frmAsignaReemplazo" action="enviaGridReemplazo"
	method="POST" target="frmAsignaReemplazo">
	<s:hidden id="idDependencia1" name="idDependencia1" />
	<span style="color: black">

	<div dojoType="dijit.layout.SplitContainer" orientation="vertical"
		sizerWidth="7" activeSizing="true"
		style="border: 1px solid #bfbfbf; float: left; margin-right: 30px; width: 800px; height: 800px;">
	<div dojoType="dijit.layout.ContentPane" sizeMin="15" sizeShare="20">
	<div id="procesosParticipacion">


	<table id='edGrid' jsId='edGrid' dojoType='dojox.grid.DataGrid'
		store='jsonStore' style='height: 200px;'
		onRowClick="refreshNotificacion">
		<thead>
			<tr>
				<th width='20px' cellType='dojox.grid.cells.RowIndex'
					styles='text-align: center;'></th>
				<th field='id' width='20px' editable='false' hidden="true">id</th>
				<th field='label' width='450px' editable='false'>Asunto</th>
				<th field='fechanotificacion' width='120px' editable='false'>Fecha</th>


			</tr>
		</thead>
	</table>


	</div>
	</div>

	<div class="formContainer" dojoType="dijit.layout.TabContainer"
		style="width: 800px; height: 600px" sizeShare="80">
	<div dojoType="dijit.layout.ContentPane" sizeMin="15" sizeShare="80"
		title="General">

	<div id="toBeReplaced"></div>

	</div>
	<div dojoType="dijit.layout.ContentPane" sizeMin="15" sizeShare="80"
		title="Documentos" style="height: 600px">
	<div id="toBeReplaced2"></div>
	</div>
	</div>

	</div>
	</span>
</s:form>



</body>
</html>