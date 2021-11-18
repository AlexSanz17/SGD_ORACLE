<?xml version="1.0" encoding="UTF-8" ?>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es">
   <head>
      <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
      <title>Solicitar al Supervisor </title>
      <link rel="stylesheet" type="text/css" href="css/estilo.css" />
      <style type="text/css">
         @import "js/dojo/dijit/themes/soria/soria.css";
         @import "js/dojo/dojox/grid/resources/soriaGrid.css";
         @import "css/grid.css";
      </style>
      <script type="text/javascript" src="js/dojo/dojo/dojo.js" djConfig="parseOnLoad:true, isDebug: false"></script>
      
      <script type="text/javascript" src='js/siged/date-es-PE.js'></script> 
 	 
      <s:url id="autoUrl" action="enviarGridsupervisor" />
      
      <script type="text/javascript">
      dojo.require("dojo.rpc.JsonService");
      dojo.require("dijit.Dialog");
      dojo.require("dijit.form.TextBox");
      dojo.require("dijit.form.Button");
      dojo.require("dijit.form.DateTextBox");
      dojo.require("dijit.form.FilteringSelect" );
      dojo.require("dojo.data.ItemFileWriteStore");
      dojo.require("dojo.data.ItemFileReadStore");
      dojo.require("dojox.grid.DataGrid");
      
      var jsonStore = new dojo.data.ItemFileWriteStore({url: "<s:property value='autoUrl' />"});
      
      var storeUsuarios;
      var idproceso;

      function formaterEditar() {
	      return "<img src='images/clic.gif'/>";
	}

  function dialogAlert(txtTitle, txtContent)
	{
		var thisdialog = new dijit.Dialog({ title: txtTitle, content: txtContent });
		dojo.body().appendChild(thisdialog.domNode);
		thisdialog.startup();
		thisdialog.show();
	}


  function refreshSuperList(e){
		if(e.cellIndex==e.cellIndex){
  	  idproceso=edGrid.getItem(e.rowIndex).id;
  	  jsonStore.fetchItemByIdentity({
  		  identity: idproceso, 
  		  onItem: function(item){
  		  var fechat=jsonStore.getValue(item,"fechaEnt");
  		  var fechao=jsonStore.getValue(item,"fechaEvo");
  		  //alert(fechat+" "+fechao);
  		dijit.byId("dialogProceso").setValue(jsonStore.getValue(item,"label"));
  		jsonStore.setValue(item,"dialogFechaEnt",fechat);
  		jsonStore.setValue(item,"dialogFechaEvo",fechao);
  	  }});
  	  dijit.byId('dialog1').show();
		}
    }
  
  function salvaFila(){
	  jsonStore.fetchItemByIdentity({
		identity: idproceso, 
		onItem: function(item){
     	jsonStore.setValue(item,"fechaEnt",dijit.byId("dialogFechaEnt").getDisplayedValue());
     	jsonStore.setValue(item,"fechaEvo",dijit.byId("dialogFechaEvo").getDisplayedValue());
	  }});
	  document.getElementById("idDependencia1").value=jsonStore._getNewFileContentString(); 
  }

	function enviar(){
		document.getElementById("idDependencia1").value=jsonStore._getNewFileContentString(); 
		var callback = function(str) {
			dijit.byId('dialog1').hide();
			jsonStore = new dojo.data.ItemFileWriteStore({url: "<s:property value='autoUrl' />?valor=2"})
			
			dijit.byId("edGrid").setStore(jsonStore);          
			/*dojo.forEach(dijit.byId("edGrid")._arrayOfAllItems, function(item) {
		    	  edGrid.store.setValue(item, 'selected', chkAll);
		      });*/ 
		 ///	dijit.byId("edGrid").     
		};
		var service = new dojo.rpc.JsonService("SMDAction.action");
		var defered = service.enviaGridSupervisor(""+idproceso,""+dijit.byId("dialogFechaEnt").getDisplayedValue(),""+dijit.byId("dialogFechaEvo").getDisplayedValue());
		defered.addCallback(callback);
		
	}
	
  var chkAll = true;

  noSort = function(inSortInfo) {
	   if (inSortInfo == 8) {
	      dojo.forEach(edGrid.store._arrayOfAllItems, function(item) {
	    	  edGrid.store.setValue(item, 'selected', chkAll);
	      });

	      edGrid.store.save();
	      chkAll = !chkAll;

	      return false;
	   } else {
	      return true;
	   }
	}
	    
  </script>

       <%
		Date fecha=new Date();
      	SimpleDateFormat sdf=new java.text.SimpleDateFormat("yyyy-MM-dd");
      	String strfecha=sdf.format(fecha);
      %>
   </head>
   
<body class="soria">
      <table width="100%">
         <tr>
            <td height="20" colspan="6" width="99%">
                  <table width="98%" align="center">
                     <tr>
                        <td width="50%" align="left">
                           Bienvenido <span style="color:blue"><s:property value="#session.nombres"/></span>
                        </td>
                        <td width="1%" rowspan="2"></td>
                        <td width="24%" rowspan="2" align="right">
                        </td>
                        <td width="20%" rowspan="2" align="right">
                        </td>
                        <td width="5%" rowspan="2" align="right">
                        </td>
                        <td width="1%" rowspan="2"></td>
                     </tr>
                     <tr>
                        <td align="left">
                           <font color="0099FF"><a href="<s:url action="doLogout" />" target="_parent">Cerrar Sesi&oacute;n</a></font>
                        </td>
                     </tr>
                  </table>
            </td>
         </tr>
         <tr>
            <td colspan="6"></td>
         </tr>
         <tr>
            <td bgcolor="#4481B8" width="30%" align="center" class="tituloRojo" height="25">Supervisor. </td>
            <td colspan="5"></td>
         </tr>
         <tr>
            <td colspan="6"></td>
         </tr>
         
      </table>
       
<s:form name="frmAsignaReemplazo" action="" method="POST" target="frmAsignaReemplazo">

<s:hidden id="idDependencia1" name="idDependencia1" />

	<span style="color:black">	
		<div id="procesosParticipacion" >
			<table id='edGrid' jsId='edGrid' dojoType='dojox.grid.DataGrid' store='jsonStore' style='height:250px;' onRowClick="refreshSuperList" canSort="noSort" rowsPerPage="50">
		        <thead>
		          <tr>
		          <th field='id' width='20px' editable='false'>id</th>
		          <th field='label' width='100px' editable='false'>Expediente</th>
		          <th field='documento' width='80px' editable='false'>Documento</th>
		          <th field='usuario' width='100px' editable='false'>Usuario</th>
		          <th field='fechaSol' width='100px' editable='false'>Fecha Solicitud</th>
		          <th field='fechaEnt' width='100px' editable='false'>Fecha Entrega</th>
		          <th field='fechaEvo' width='100px' editable='false'>Fecha Devolucion</th>
		          <th field='caja' width='40px' editable='false'>Caja</th>
		          <th width='20px' editable='false' formatter='formaterEditar' >Editar</th>
		            </tr>
		        </thead>
        	</table>
		</div>
		<img onClick="enviar()" src="images/xx.bmp" border="0" alt="Enviar"/>
		</span>			
      </s:form>

      
<div dojoType="dijit.Dialog" id="dialog1" title="Editar Supervisor" execute="salvaFila">
  <table>
    <tr>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td><label ></label></td>
      <td>
      
      <input dojoType="dijit.form.TextBox" size="0" width="0" type="text" name="dialogProceso" id="dialogProceso"  readonly="true">
      
       </td>
    </tr>
    <tr>
      <td><label >Fecha Entrega: </label></td>
      <td>
         <input id="dialogFechaEnt"
                name="dialogFechaEnt"
                type="text"
                dojoType="dijit.form.DateTextBox"
                required="false"
                promptMessage="dd/MM/yyyy"
                size="20"
                constraints="{datePattern:'dd/MM/yyyy'}"
                onChange="dijit.byId('dialogFechaEvo').constraints.min = this.getValue();" />
      </td>
    </tr>
    <tr>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td><label >Fecha Devolucion: </label></td>
      <td>
         <input id="dialogFechaEvo"
                name="dialogFechaEvo"
                type="text"
                dojoType="dijit.form.DateTextBox"
                required="false"
                promptMessage="dd/MM/yyyy"
                size="20"
                constraints="{datePattern:'dd/MM/yyyy'}"
                onChange="dijit.byId('dialogFechaEnt').constraints.max = this.getValue();" />
      </td>
    </tr>
    
    <tr>
      <td colspan="2" align="center">
        <button dojoType="dijit.form.Button" type="submit">OK</button></td>
    </tr>
  </table>
</div>

</body>
</html>