<?xml version="1.0" encoding="UTF-8"?>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.btg.ositran.siged.domain.Alerta" %>
<%@page import="java.util.List" %>
<%@page import="java.text.SimpleDateFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html>
<head>
<title>Alerta de Documentos</title>
<% List<Alerta> listAlert = (List<Alerta>)request.getSession().getAttribute("listAlert");
   SimpleDateFormat fecha = new SimpleDateFormat("dd/MM/yyyy HH:mm");
   String showDescargar = (String) request.getSession().getAttribute("showDescargar");
   String ordenFechaLimite = request.getAttribute("ordenarFechaLimite")==null? "asc": (String)request.getAttribute("ordenarFechaLimite");
%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="pragma" content="no-cache">
	<link rel="stylesheet" type="text/css" href="css/estilo.css" />
	<script type="text/javascript" src="js/form.js"> </script>
	<script type="text/javascript" src="js/general.js"></script>
	<link rel="stylesheet" type="text/css" media="all"
		href="css/calendar-green.css" />
	<script type="text/javascript">

	function regresar(){
        window.close();
    }

	function abrirSeguimientoAlerta(idDoc) {
        var opciones = "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=no, width=650, height=500, top=80, left=250";
        var pagina ="goViewSeguimiento.action?iIdDocumento=" + idDoc;
        window.open("goViewSeguimiento.action?iIdDocumento=" + idDoc, "", opciones);
    }

	function abrirHojaRutaAlertas(idDoc){
     	var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=900, height=700, top=50, left=200";
         var pagina = "ReporteAPN_verHojaRuta.action?idDocumento=" + idDoc;
         window.open(pagina,"",opciones);
     }

	  function ordenar(campo){
		  document.getElementById('executeAccion').value = campo;
		  document.getElementById('ordenarFechaLimite').value = '<%=ordenFechaLimite%>';
		  document.forms["frmAlertaDocumento"].submit();
	  }

	  function imprimir(){
		   var opciones = "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=1000, height=600, top=20, left=70";
		   var pagina = "ReporteAPN_reporteDocumentosAlertas.action";
		   window.open(pagina, "", opciones);
	  }

      function descargar(){
    	  var objeto = document.getElementsByName("arrDocumentArea");
          var haychekadito = false;
          for (var i = 0; i < objeto.length; i++) {
             if (objeto[i].checked) {
                haychekadito = true;
                break;
             }
          }

          if (!haychekadito) {
             alert("Debe seleccionar al menos un documento");

             return;
          }

          document.getElementById('executeAccion').value = "DESCARGAR";
          document.forms["frmAlertaDocumento"].submit();
      }
    </script>
</head>
<body>
	<table border="0" width="94%" align="center">
		<tr align="center">
			<td colspan="2" align="center">&nbsp;</td>
		</tr>
		<tr height="18%">
			<td valign="center" align="center"
				style="FONT-WEIGHT: bold; FONT-SIZE: 10pt; COLOR: #31619c; FONT-FAMILY: verdana, arial, helvetica, sans-serif">

				Alerta de Documentos</td>
		</tr>
		<tr height="18%">
			<td valign="center" align="center"
				style="FONT-WEIGHT: bold; FONT-SIZE: 10pt; COLOR: #31619c; FONT-FAMILY: verdana, arial, helvetica, sans-serif">
			</td>
		</tr>
		<tr>
		  <td><br/></td>
		</tr>
		<%if (showDescargar.equals("1")){%>
			<tr>
			  <td align="right">
			    <input type="button"
					name="descarga" value="Descargar" class="button"
					onclick="descargar();" />
			  </td>
			</tr>
		<%}%>
	</table>
    <s:form name="frmAlertaDocumento" id="frmAlertaDocumento" action="doAlertaDocumento" method="POST">
        <s:hidden id="executeAccion" name="executeAccion" />
        <s:hidden id="ordenarFechaLimite" name="ordenarFechaLimite"  value=""/>
		<table border="1" cellpadding="1" cellspacing="1" class="tableForm" width="93%"
			align="center">
			<tr class="header">
				<td width="18%" class="data"><a class="headerLink" href="javascript:ordenar('ORDENARFL');" title="">Fecha Limite</a></td>
				<td width="28%" class="data">Documento</td>
				<td width="28%" class="data">Asunto</td>
				<td width="18%" class="data">Área Destino</td>
				<td width="15%" class="data">Tiempo</td>
				<td width="12%" class="data">HR</td>
				<td width="12%" class="data">TR</td>
				<%if (showDescargar.equals("1")){%>
				  <td width="8%" class="data">&nbsp;&nbsp;</td>
				<%}%>
			</tr>
			 <s:iterator value="#session.listAlert">
					<tr>
					   <td><s:property value="fechalimite" /></td>
					   <td><s:property value="documento" /></td>
					   <td><s:property value="asunto" /></td>
					   <td><s:property value="area" /></td>
					   <td><s:property value="desfase"/>
					        <s:if test="desplazo.equals('F')">
					           <b><font color="red">Fuera de Plazo</font></b>
					        </s:if>
					        <s:else>
					           <font color="black">Dentro del Plazo</font>
					        </s:else>
					   <!--  <font color="<s:property value="color"/>"><s:property value="desplazo"/></font> -->
					   </td>
					   <td><a href="#" onclick="abrirHojaRutaAlertas('<s:property value="iddocumento" />')">HR</a></td>
					   <td><a href="#" onclick="abrirSeguimientoAlerta('<s:property value="iddocumento" />')">TR</a></td>
					   <%if (showDescargar.equals("1")){%>
					   	   <td>
					   	       <s:if test="enabledCheck.equals('X')">
					   	          <s:checkbox  name="arrDocumentArea" fieldValue = "%{iddocumento}-%{userdestinatario}-%{indtable}"/>
					           </s:if>
					           <s:else>
					              &nbsp;
					           </s:else>
					        </td>
					   <%}%>
				   </tr>
			</s:iterator>
		</table>
    </s:form>


    <table width="100%">
		<tr align="center">
			<td colspan="2" align="center">&nbsp;</td>
		</tr>
		<tr>
			<td colspan="2" align="center"><input type="button"
				name="selecciona" value="Regresar" class="button"
				onclick="regresar();" /> &nbsp;&nbsp;&nbsp;
			    <input type="button"
				name="selecciona" value="Imprimir" class="button"
				onclick="imprimir();" /> </td>
		</tr>
		<tr align="center">
			<td colspan="2" align="center">&nbsp;</td>
		</tr>
	</table>

</body>
</html>