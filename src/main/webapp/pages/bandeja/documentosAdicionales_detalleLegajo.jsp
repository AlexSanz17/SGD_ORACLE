<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="org.ositran.utils.DocumentoDetail"%>
	<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>Expediente</title>
	<%-- <script type="text/javascript" src="js/dojo/dojo/dojo.js" djConfig="parseOnLoad:true, isDebug: false"></script>
	      <script type="text/javascript" src="js/siged/upload.js"></script>
	      <script type="text/javascript">
	         dojo.require("dojo.io.iframe");
	      </script>

	      <link rel="stylesheet" type="text/css" href="/siged/css/estilo.css">

	     <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">--%>
	<style type="text/css">
		table.detalleExp{
			border: 0.02em solid;
			border-collapse: true;
			width:95%;
		}
                
                table.tableForm{
			border: 0.02em solid;
			border-collapse: true;
		}
                
                td.detalleExp{
			border: 0.02em solid;
			padding-left: 0.2em;
		}
                label{
			font-family:verdana,arial,helvetica,sans-serif;
			font-size:10pt;
			color:#31619C;
			font-weight:bold
		}
	</style>
	</head> 

	<body topmargin="0" leftmargin="0" rigthmargin="0">
          <p><label>Informaci&oacute;n del Expediente</label></p>

		<p>
		<div>
			<table class="detalleExp" align="center" border="0">
				<tr>
					<td class="detalleExp"><label>Nro. Expediente</label></td>
					<td class="detalleExp"><s:property value="objLegajo.nroLegajo" /></td>
				</tr>
				
				<tr>
					<td class="detalleExp"><label>Asunto del Expediente</label></td>
					<td class="detalleExp"><s:property value="objLegajo.asunto"/></td>
				</tr>
                                
                                <tr>
					<td class="detalleExp"><label>Tipo del Expediente</label></td>
					<td class="detalleExp"><s:property value="objLegajo.tipoLegajo.descripcion"/></td>
				</tr>
                                
                                 <tr>
					<td class="detalleExp"><label>Unidad</label></td>
					<td class="detalleExp"><s:property value="objLegajo.unidad.nombre"/></td>
				</tr>
			
				
				<tr>
					<td class="detalleExp"><label>Observaciones</label></td>
					<td class="detalleExp"><s:property value="objLegajo.observacion"/></td>
				</tr>
                                
                                <tr>
					<td class="detalleExp"><label>Estado</label></td>
                                        <s:if test='objLegajo.estado.equals("A")'>
					  <td class="detalleExp">En Proceso</td>
                                        </s:if>
                                        <s:if test='objLegajo.estado.equals("C")'>
					  <td class="detalleExp">Cerrado</td>
                                        </s:if>   
                                          
                                          
				</tr>
                                
                                <tr>
					<td class="detalleExp"><label>Fecha de Creaci&oacute;n</label></td>
					<td class="detalleExp"><s:date name="objLegajo.fechaCreacion" format="dd/MM/yyyy HH:mm" /></td>
				</tr> 
			</table>
		</div>
		</p>
		
   </body>
</html>