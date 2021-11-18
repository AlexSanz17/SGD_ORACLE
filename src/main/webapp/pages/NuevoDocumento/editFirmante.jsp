<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<title>Editar Firmante</title>
<script type="text/javascript">
var strFirmante=null;
var firmadores="<s:property value='objDocumento.firmante.idusuario'/>";
var doc="<s:property value='objDocumento.idDocumento'/>";

dojo.addOnLoad(function() {
	strFirmante= new dojo.data.ItemFileReadStore({url: "obtenerUsuariosCC.action"});
	document.getElementById("iddoc").value=doc;
});

function guardar(){
	document.getElementById("form1").action="Numeracion_validarFirmante.action";
	dojo.xhrPost({
			form: dojo.byId("form1"),
			load: function(data1){
				console.debug("Data1:"+data1);
				var elem = data1.split('_');
				var cad  = elem[1];
				console.debug("Data1:"+cad);
			if(cad!="1"){	
				alert(cad);
			}
			else{
					    if(confirm("Desea Modificar Firmante?") == true)
					    {
					    	document.getElementById("form1").action="Numeracion_guardarFirmante.action";
					 		dojo.xhrPost({
									form: dojo.byId("form1"),
									load: function(data2){
									alert(data2);
									objDLGClass.hide();
									objDLGClass.destroyRecursive();
									refreshFirmante();
							 }}); 
		
					    }
			}
	 }});
	
	
}
</script>
</head>
<body>
	<form id="form1" name="form1" action="Numeracion_guardarFirmante.action">
		<br/>
		<table align="center">
				<tr>
					<td>Firmante</td>
					<td>
                     <div dojoType="dijit.form.FilteringSelect"
                             store="strFirmante"
                             idAttr="id"
                             labelAttr="label"
                             searchAttr="label"
                             name="firmante"
                             size="1">
					 </div>
					</td>
				</tr>
				<tr>
					<td colspan="2">
									<input id="iddoc" name="iddoc" type="hidden" value=""> 
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
				     <br/>    					
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
				         <button dojoType="dijit.form.Button"
				                 type="button"
				                 iconClass="siged16 sigedSave16"
				                 onClick="guardar()"
				                 showLabel="true">Modificar Firmante</button>						
					</td>
				</tr>
		</table>
	</form>		
		<br/>
		<br/>
		<br/>
		<br/>
</body>
</html>