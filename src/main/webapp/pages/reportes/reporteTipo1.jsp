<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><s:property value="titulo" /></title>
<link rel="stylesheet" type="text/css" href="css/estilo.css" />
<style type="text/css">
.iframe
{
background-color: transparent;
} 
</style>
<script>
function buscar(){
fechaDesde="'"+dijit.byId('fechaDesde').getDisplayedValue()+"'";
fechaHasta="'"+dijit.byId('fechaHasta').getDisplayedValue()+"'";
anio="'"+dijit.byId('anio').getDisplayedValue()+"'";
mes="'"+dijit.byId('mes').getDisplayedValue()+"'";

//alert("http://localhost/siged/reporteGenerico1.action?outputFormat=html&reportName=${reportName}&fechaDesde="+fechaDesde+"&fechaHasta="+fechaHasta+"&anio="+anio+"&mes="+mes);
dojo.byId("contenidoHTML").src="./reporteGenerico1.action?outputFormat=html&reportName=<s:property value='reportName' />&fechaDesde="+fechaDesde+"&fechaHasta="+fechaHasta+"&anio="+anio+"&mes="+mes;
}

function imprimir(){

window.frames['contenidoHTML'].focus();
window.frames['contenidoHTML'].print(); 
//window.contenidoHTML.focus();
//parent.window.print();
}

function exportarExcel(){
fechaDesde="'"+dijit.byId('fechaDesde').getDisplayedValue()+"'";
fechaHasta="'"+dijit.byId('fechaHasta').getDisplayedValue()+"'";
anio="'"+dijit.byId('anio').getDisplayedValue()+"'";
mes="'"+dijit.byId('mes').getDisplayedValue()+"'";
window.open("./reporteGenerico1.action?outputFormat=xls&reportName=<s:property value='reportName' />&fechaDesde="+fechaDesde+"&fechaHasta="+fechaHasta+"&anio="+anio+"&mes="+mes);

}

</script>

</head>

<body class="soria"> 
<s:form id="printMe" action="reporteGenerico1" method="POST" enctype="multipart/form-data">
<table width="100%" id="todo">
  <tr> 
    <td height="14" colspan="3"> </td>
  </tr>

  <tr>
		<td height="20"colspan="3" class="titulo">
				<table width="99%" border="0" height="20" align="center" bgcolor="#A2C0DC">
					<tr>

					   
            <td align="left" class="titulo"><s:property value="titulo" /></td>
					</tr>
				</table>
		</td>
	</tr>
  
  <tr align="center"> 
    <td width="1%" align="center">&nbsp;</td>
      <td width="99%" colspan="2" align="left"> <img onClick="buscar()" src="images/buscar.bmp" border="0" alt="Buscar"/> 
        &nbsp;&nbsp; <img onClick="imprimir()" src="images/impri.bmp" border="0" alt="Imprimir"/> 
        &nbsp;&nbsp; <img src="images/excel.bmp" border="0" onClick="exportarExcel()" target="_blank" alt=""/>&nbsp;&nbsp; 
    </tr>

  <tr> 
    <td colspan="3"> <table width="95%"  border="1" align="center" bordercolor="#669BCD" bgcolor="#BFD9F1">
        <tr> 
          <td height="45"> <table width="98%" height="47" align="center" >
                <tr> 
                  <td width="2%"></td>
                  <td width="15%"></td>
                  <td width="31%" ></td>
                  <td width="19%" ></td>

                  <td width="31%" ></td>
                  <td width="2%" ></td>
                </tr>
                 <tr> 
                  <td></td>
                  <td align="left" >Fecha Desde</td>
                  <td align="left" >
                   <input id="fechaDesde" type="text"  name="fechaDesde" value=""
									  dojoType="dijit.form.DateTextBox"
									  required="false"
									  promptMessage="dd/mm/aaaa"
									  size="25"
									   constraints="{datePattern:'dd/MM/yyyy'}"
									  invalidMessage="Fecha no valida.  Usar el formato dd/mm/aaaa." />
                    </td>
                  <td align="left" >Fecha Hasta</td>

                  <td align="left"> 
                  	<input id="fechaHasta" type="text"  name="fechaHasta" value=""
									  dojoType="dijit.form.DateTextBox"
									  required="false"
									  promptMessage="dd/mm/aaaa"
									  size="25"
									   constraints="{datePattern:'dd/MM/yyyy'}"
									  invalidMessage="Fecha no valida.  Usar el formato dd/mm/aaaa." />
                  
                    </td>
                  <td></td>
                </tr>
				<tr> 
                  <td></td>
                  <td align="left" ></td>
                  <td align="left"> 
                  	<input type="hidden" id="mes" name="mes" >
                  </td>
                  <td align="left" ></td>
                  <td align="left">
                  	<input type="hidden" id="anio" name="anio"> 
                   </td>
                  <td></td>
                </tr>
                <tr> 
                  <td height="5" ></td>
                </tr>
              </table></td>
        </tr>
      </table></td>

  </tr>
</table>


</s:form>
<table  width="100%">
<tr>
<td align="center" >
<iframe id="contenidoHTML" frameborder="0" scrolling="yes" width="100%" height="450px" AllowTransparency>
</iframe>
</td>
</tr>
</table>

</body>
</html>