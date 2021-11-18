<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
<title>Tramite Documentario</title>
<link rel="stylesheet" type="text/css" href="css/estilo.css">
<script language="javascript" src="js/form.js"> </script>
<script language="Javascript" src="js/general.js"></script>
<script language="Javascript" src="js/valida.js"></script>
<link rel="stylesheet" type="text/css" media="all" href="css/calendar-green.css">
<script type="text/javascript" src="js/calendar.js"></script>
<script type="text/javascript" src="js/calendar-es.js"></script>
<script type="text/javascript" src="js/calendar-setup.js"></script>

<script language="JavaScript">
function Abrir_ventana (pagina) {
var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=650, height=400, top=50, left=200";
window.open(pagina,"",opciones);
}

function Abrir_ventanaObservacion (pagina) {
var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=no, width=700, height=500, top=50, left=200";
window.open(pagina,"",opciones);
}

function Abrir_pagina (pagina) {
var opciones="location=mainFrame";
window.open(pagina,"",opciones);
}

function putTextOnTextBox(textToPut){
document.all.reciveTheText.value = textToPut;
}


function cambiar(_valor){
  if(_valor=='T'){
	obj=document.getElementById("otrosDatos");
	obj.style.display = "block";
   }else{
		obj=document.getElementById("otrosDatos1");
		obj.style.display = "none";
   }
}

function buscar(){
	f = document.cantAnalProcedForm;

	if(f.fechaHasta.value != "" && f.fechaDesde.value == ""){
		alert("Debe ingresar Fecha Desde");
		f.fechaDesde.focus();
	}else if(f.fechaDesde.value != "" && !isDate(f.fechaDesde.value)){
		alert("Debe corregir Fecha Desde");
		f.fechaDesde.focus();
	}else if(f.fechaHasta.value != "" && !isDate(f.fechaHasta.value)){
		alert("Debe corregir Fecha fechaHasta");
		f.fechaHasta.focus();
	}else if(f.mes.value > 12){
		alert("Debe ingresar un mes valido");
		f.mes.focus();
	}else
		f.submit();
}

</script>

 <script type="text/javascript">

function poorman_toggle(id)
{
	var tr = document.getElementById(id);
	if (tr==null) { return; }
	var bExpand = tr.style.display == '';
	tr.style.display = (bExpand ? 'none' : '');
}
function poorman_changeimage(id, sMinus, sPlus)
{
	var img = document.getElementById(id);
	if (img!=null)
	{
	    var bExpand = img.src.indexOf(sPlus) >= 0;
		if (!bExpand)
			img.src = sPlus;
		else
			img.src = sMinus;
	}
}

function Toggle_repHeader2()
{
    poorman_changeimage('repHeader2_Img', 'images/signo_menos.gif', 'images/signo_mas.gif');
    poorman_toggle('row1');
    poorman_toggle('row2');
    poorman_toggle('row3');
}

function Toggle_repHeader1()
{
    poorman_changeimage('repHeader1_Img', 'images/signo_menos.gif', 'images/signo_mas.gif');
    poorman_toggle('trRow1');
}
function Toggle_repHeader3()
{
    poorman_changeimage('repHeader3_Img', 'images/signo_menos.gif', 'images/signo_mas.gif');
    poorman_toggle('row1');
    poorman_toggle('row2');
    poorman_toggle('row3');
}

function Toggle_repHeader3()
{
    poorman_changeimage('repHeader3_Img', 'images/signo_menos.gif', 'images/signo_mas.gif');
    poorman_toggle('trRow3');
}
function Toggle_repHeader4()
{
    poorman_changeimage('repHeader4_Img', 'images/signo_menos.gif', 'images/signo_mas.gif');
    poorman_toggle('row1');
    poorman_toggle('row2');
    poorman_toggle('row3');
}

function Toggle_repHeader4()
{
    poorman_changeimage('repHeader4_Img', 'images/signo_menos.gif', 'images/signo_mas.gif');
    poorman_toggle('trRow4');
}
function Toggle_repHeader5()
{
    poorman_changeimage('repHeader5_Img', 'images/signo_menos.gif', 'images/signo_mas.gif');
    poorman_toggle('row1');
    poorman_toggle('row2');
    poorman_toggle('row3');
}

function Toggle_repHeader5()
{
    poorman_changeimage('repHeader5_Img', 'images/signo_menos.gif', 'images/signo_mas.gif');
    poorman_toggle('trRow5');
}
function Toggle_repHeader6()
{
    poorman_changeimage('repHeader6_Img', 'images/signo_menos.gif', 'images/signo_mas.gif');
    poorman_toggle('row1');
    poorman_toggle('row2');
    poorman_toggle('row3');
}

function Toggle_repHeader6()
{
    poorman_changeimage('repHeader6_Img', 'images/signo_menos.gif', 'images/signo_mas.gif');
    poorman_toggle('trRow6');
}
</script>

<script language="JavaScript">
var gAutoPrint = true; // Flag for whether or not to automatically call the print function

function printSpecial()
{
	if (document.getElementById != null)
	{
		var html = '<HTML>\n<HEAD>\n';

		if (document.getElementsByTagName != null)
		{
			var headTags = document.getElementsByTagName("head");
			if (headTags.length > 0)
				html += headTags[0].innerHTML;
		}
		
		html += '\n</HE' + 'AD>\n<BODY>\n';
		
		var printReadyElem = document.getElementById("printReady");
		
		if (printReadyElem != null)
		{
				html += printReadyElem.innerHTML;
		}
		else
		{
			alert("Could not find the printReady section in the HTML");
			return;
		}
			
		html += '\n</BO' + 'DY>\n</HT' + 'ML>';
		
		var printWin = window.open("","printSpecial");
		printWin.document.open();
		printWin.document.write(html);
		printWin.document.close();
		if (gAutoPrint)
			printWin.print();
	}
	else
	{
		alert("Sorry, the print ready feature is only available in modern browsers.");
	}
}

</script>

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<style type="text/css">
<!--
body {
	background-color: #ffffff;
}

.barra {
           scrollbar-3dlight-color:#CCCCCC;
           scrollbar-arrow-color:#568BBF;
           scrollbar-base-color:#C3D5E9;
           scrollbar-darkshadow-color:#666666;
           scrollbar-face-color:;
           scrollbar-highlight-color:#669BCD;
           scrollbar-shadow-color:#999999;
		   }
		   
 div.falso {
	position: absolute;
	top: -29px;
	left: 365px;
	z-index: 0;
	width: 14px;
	height: 3px;
 }
 
input.file {
 position: relative; 
 text-align: left;
 filter:alpha(opacity: 0); 
 opacity: 0; 
 z-index: 1; 
 top: -29px;
	left: 335px;
 }
		   
-->

</style>

</head>

<body class="barra" topmargin="0" leftmargin="0" rigthmargin="0" > 
<form name="cantAnalProcedForm" action="doCantAnalProced_buscar.action" method="post">
<table width="100%" id="todo">
  <tr> 
    <td height="14" colspan="3"> </td>
  </tr>

  <tr>
	<td height="20"colspan="3" class="titulo">
		<table width="99%" border="0" height="20" align="center" bgcolor="#A2C0DC">
			<tr>
			   <td align="left" class="titulo">Cantidad de Analistas por procedimiento</td>
			</tr>
		</table>
	</td>
  </tr>
  
  
  
  <tr align="center"> 
    <td width="1%" align="center">&nbsp;</td>
      <td width="99%" colspan="2" align="left"> <img onClick="javascript:buscar()" src="images/buscar.bmp" border="0" alt="Buscar"/> 
        &nbsp;&nbsp; <img onClick="printSpecial()" src="images/impri.bmp" border="0" alt="Imprimir"/> 
        &nbsp;&nbsp; <img src="images/excel.bmp" border="0" onClick="" target="_blank" alt=""/>&nbsp;&nbsp;  
    </tr>
  <tr> 
    <td colspan="3"> 
		
		<table width="95%"  border="1" align="center" bordercolor="#669BCD" bgcolor="#BFD9F1">
        <tr> 
          <td height="45"> 
				<table width="98%" height="47" align="center" >
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
                  <td align="left" class="label">
					<input class="cajaMontoTotal" name="fechaDesde" type="text" id="campo_fecha2" size="14" maxlength="10" value="<s:property value='fechaDesde'/>" onkeypress="return esTeclaFecha(this)"> 
                    <input alt="Calendario" class="cajaFecha" id="lanzador2" value="..." type="button"></td>
                  <td align="left" >Fecha Hasta</td>
                  <td align="left" class="label">
					<input class="cajaMontoTotal" name="fechaHasta" type="text" id="campo_fecha1" size="14" maxlength="10" value="<s:property value='fechaHasta'/>" onkeypress="return esTeclaFecha(this)"> 
                    <input alt="Calendario" class="cajaFecha" id="lanzador1" value="..." type="button"></td>
                  <td></td>
                </tr>
				<tr> 
                  <td></td>
                  <td align="left" >Mes</td>
                  <td align="left" class="label">
					<input class="cajaMontoTotal" name="mes" type="text" size="8" maxlength="20" onkeypress="return esTeclaNumero(this)"> 
                   </td>
                  <td align="left" >A&ntilde;o</td>
                  <td align="left" class="label">
					<input class="cajaMontoTotal" name="anio" type="text" size="8" maxlength="20" onkeypress="return esTeclaNumero(this)"> 
                   </td>
                  <td></td>
                </tr>
               
                <tr> 
                  <td height="5"></td>
                </tr>
              </table>
			</td>
        </tr>
      </table>
	
	</td>
  </tr>
  
  
  <tr> 
    <td height="14"  colspan="3"></td>
  </tr>
  
  <tr id="printReady">
    <td colspan="6">
	<s:if test="!analistasProcedimientoBeanList.isEmpty()">
    <table width="100%" border="1" height="100" cellpadding="0" cellspacing="0">
     <tr>
      <td width="100%" valign="top">
	
	 	<table cellpadding="1" cellspacing="1" width="80%" align="center" class="tableForm" border="0">
            <tr class="header"> 
              <th width="20%" class="data">Analistas</th>
			  <s:iterator value="procedimientosList">
              	<th width="20%" class="data"><s:property value="nombre"/></th>
			  </s:iterator>
            </tr>
			<tbody>
			<s:iterator id="item" value="analistasProcedimientoBeanList">
	            <tr id="f0" class="altRow2"> 
	              <td align="center"><s:property value="analista"/></td>
				  <s:iterator value="procedimientosList">
				  	<td align="center"><s:property/></td>
				  </s:iterator>
	            </tr>
			</s:iterator>
         </table>
	 </td>
  </tr>
	</table>
	</s:if>
</td>
</tr>
</table>
</form>
<script type="text/javascript">
    Calendar.setup({
        inputField     :    "campo_fecha1",      // id del campo de texto
        ifFormat       :    "%d/%m/%Y",       // formato de la fecha, cuando se escriba en el campo de texto
        button         :    "lanzador1"   // el id del botón que lanzará el calendario
    });
	 Calendar.setup({
        inputField     :    "campo_fecha2",      // id del campo de texto
        ifFormat       :    "%d/%m/%Y",       // formato de la fecha, cuando se escriba en el campo de texto
        button         :    "lanzador2"   // el id del botón que lanzará el calendario
    });
		 
</script>
</body>