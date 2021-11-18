<%@page import="java.util.*"%>
<%@page import="org.ositran.utils.DocumentoList"%>
<%@page import="com.btg.ositran.siged.domain.ArchivoPendiente"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.text.DateFormat"%><html>
<head>
<title>Tramite Documentario</title>
<link rel="stylesheet" type="text/css" href="css/estilo.css">
<script language="javascript" src="js/switchMenu.js"></script>
<script language="javascript" src="js/form.js"> </script>
<script language="Javascript" src="js/general.js"></script>
<link rel="stylesheet" type="text/css" media="all"
	href="css/calendar-green.css">
<script type="text/javascript" src="js/calendar.js"></script>
<script type="text/javascript" src="js/calendar-es.js"></script>
<script type="text/javascript" src="js/calendar-setup.js"></script>
<script type="text/javascript" src="js/si.files.js"></script>
<script type="text/javascript" src="js/sorttable.js"></script>
<script type="text/javascript" src="./runtime/lib/aw.js"></script>
<link rel="stylesheet" href="./runtime/styles/system/aw.css">

<!-- ----------------------------------------- Librerias y configuracion de Grid --------------------- -->
<%
      List data =(List)request.getAttribute("archivoPendienteList"); 
      String rol=(String)request.getSession().getAttribute("rol");
      String col=(String)request.getSession().getAttribute("columnas");
       
      System.out.print(" fff : "+col); 
	%>

<!-- fix box model in firefox/safari/opera -->
<style type="text/css">
.aw-quirks * {
	box-sizing: border-box;
	-moz-box-sizing: border-box;
	-webkit-box-sizing: border-box;
}

body {
	font: 12px Tahoma
}
</style>


<style type="text/css">
#myGrid {
	height: 350px;
	width: 1000px;
}

#myGrid .aw-row-selector {
	text-align: center
}

#myGrid .aw-column-0 {
	width: 10px;
}

#myGrid .aw-column-1 {
	width: 20px;
}

#myGrid .aw-column-2 {
	width: 20px;
	text-align: center;
}

#myGrid .aw-column-3 {
	width: 20px;
	text-align: center;
}

#myGrid .aw-column-4 {
	width: 150px;
	text-align: center;
}

#myGrid .aw-column-5 {
	width: 150px;
	text-align: center;
}

#myGrid .aw-column-6 {
	width: 150px;
	text-align: center;
}

#myGrid .aw-column-7 {
	width: 150px;
	text-align: center;
}

/* #myGrid .aw-column-5 {width: 150px;text-align: center;}*/
#myGrid .aw-grid-cell {
	border-right: 1px solid threedlightshadow;
}

#myGrid .aw-grid-row {
	border-bottom: 1px solid threedlightshadow;
}

/* box model fix for strict doctypes, safari */
.aw-strict #myGrid .aw-grid-cell {
	padding-right: 3px;
}

.aw-strict #myGrid .aw-grid-row {
	padding-bottom: 3px;
}
</style>



<!-- Llenedo de la grid data -->
<script type="text/javascript">


      var myColumns;
		var mydata=[<%

            ArchivoPendiente dl;
		    Date datFecha;
		    Date datHoy = Calendar.getInstance().getTime();
		    DateFormat formatFecha = DateFormat.getDateInstance(DateFormat.SHORT);
		    DateFormat formatHora = DateFormat.getTimeInstance(DateFormat.SHORT);
		    String strFechaBD="";
		
				for(int i=0;i<data.size();i++)
				{ 
                  dl=(ArchivoPendiente)data.get(i);
                  
                  datFecha = dl.getFechaDocumento(); 
                                                    
	                  if(dl.getEstado().equals("ND")&&datFecha!=null){  
	                	  if (formatFecha.format(datFecha).equals(formatFecha.format(datHoy))) {
	                          strFechaBD = formatHora.format(datFecha);
	                       } else {
	                          strFechaBD = formatFecha.format(datFecha);
	                       }
                	  
                   %>["<%=dl.getIdArchivoPendiente()%>", "","<%=dl.getEstado()%>","<img src='images/alta.bmp' border='0'/>","<%=dl.getUsuario().getUsuario()%>","<%= dl.getPlantilla().getDescripcion()+":"+ dl.getAsunto()%>","<%=(dl.getConcesionario()!=null?dl.getConcesionario().getRazonSocial():"")   %>","<%= dl.getNumeroDocumento()%>","<%=strFechaBD%>","<img src='images/clic.gif'/>","<%=dl.getProceso().getTipoproceso().getNombre()%>"],<%
 	  
                  }else if (dl.getEstado().equals("ED")){
                	                                                   
                  %>["<%=dl.getIdArchivoPendiente()%>", "","<%=dl.getEstado()%>","<img src='images/alta.bmp' border='0'/>","<%=dl.getUsuario().getUsuario()%>","<%= dl.getPlantilla().getDescripcion() %>","<%// dl.getConcesionario().getRazonsocial()%>","<% // dl.getNrodocumento()%>","<%//strFechaBD%>","<img src='images/clic.gif'/>","<%=dl.getDocumento().getExpediente().getProceso().getTipoproceso().getNombre()%>"],<%	  
	                           
                  } 
				}
			  %>]
     
           myColumns=[<%=col%>]

                      <%-- 
                      
                       %>["<%=dl.getIIdDocumento()%>","","<img src='images/bolita.gif' border='0'/>","<img src='images/alta.bmp' border='0'/>","<%=dl.getStrRemitente()%>","<%= dl.getStrAsunto()%>","<%= dl.getStrCorrentista()%>","<%= dl.getStrNroDocumento()%>","<%=strFechaBD%>","<img src='images/clic.gif'/>","<%=dl.getExpediente().getProceso().getTipoproceso().getNombre()%> "],<%
 
                      
                      --%>



</script>

<!-- Construtyendo el objeto de la datagrid -->

<script type="text/javascript">

	var idDocumento =0;
	//	create ActiveWidgets Grid javascript object
	var obj = new AW.UI.Grid;
	obj.setId("myGrid");
	
	//	define data formats
	var str = new AW.Formats.String;
	var num = new AW.Formats.Number;
	var dat = new AW.Formats.Date;
	
	obj.setCellFormat([num,str,str,str,str,dat]);
	
	//	provide cells and headers text
	
	obj.setCellText(mydata);
	obj.setHeaderText(myColumns);
	
	//	set number of rows/columns
	obj.setRowCount(<%=data.size()%>);
	obj.setColumnCount(5);
	
	//Definiendo que columnas es visible
	var chk = new AW.Templates.Checkbox;

     //chk.setEvent("onclick", myClickHandler);

    obj.setCellTemplate(chk, 1); // column-1 as checkbox
    obj.setColumnIndices([1,3,4,5,6,7,8,9]);       

	//	enable row selectors
    obj.setSelectorVisible(true);
    obj.setSelectorText(function(i){return this.getRowPosition(i)+1});

    //	set headers width/height
    obj.setSelectorWidth(28);
    obj.setHeaderHeight(20);

    //	set row selection
    obj.setSelectionMode("single-row");         

	//	set click action handler
	obj.onCellClicked = function(event, col, row)
	{
      var id=this.getCellText(0, row);
        //Sirve para atrapar el evento de un templete de una columna
       //var  t=this.getCellTemplate(1,row).getControlProperty("value");
       // alert(t);
       var rol='<%=rol%>';

       // alert(col+" "+rol);

      if(col!=1)  
       {
           //alert("id:"+idDocumento);
           idDocumento = id;    
           var estadoArchivoPendiente=this.getCellText(2, row);
           
           if(estadoArchivoPendiente=="ND"){             
                
        	   parent.location.href='/siged/NuevoDocumento_mostrarEdicion.action'+'?idArchivoPendiente='+idDocumento;
        	   
           }else  if(estadoArchivoPendiente=="ED"){
	
        	   B('#e1f3ff',id,'/siged/doPlantilla_verEdicion.action'); 
        	   
            }
             
            
            
       }
       else if(col==1)
       {   
            // alert("Aca toy 3");
            var estadochk=this.getCellTemplate(1,row).getControlProperty("value");
             
            var tipoProceso=this.getCellText(10, row);

            if(estadochk==true && tipoProceso=="<s:property value='@org.ositran.utils.Constantes@TIPO_PROCESO_ANTIFLUJO' />")
            {
                this.getCellTemplate(1,row).setControlProperty("value",false);
            }

           //alert("Aca toy 3");
           idDocumento = 0 ; 
           parent.frames["secondFrame"].location.href="secondFrame.jsp";       
            
       }
	}; 

 //   function myClickHandler(event){
 //        alert(row);
 //    }

    function GuardarMasivamente()
    {
        var total=<%=data.size()%>;

        for(i=0; i<total;i++)
        {
            if(this.getCellText(10, i)=="Flujo")
            {

            }
        }
    }

 //   function nuevoDoc () { parent.location.href="/siged/doPlantilla_inicio.action"   ; }

    function SeleccionarTodo()
    {
        var total=<%=data.size()%>;
        for(i=0;i<total;i++)
        {
            if(obj.getCellText(10, i)=="Flujo")
            {
                obj.getCellTemplate(1,i).setControlProperty("value",true); 
            }

        }
    }

    function DeseleccionarTodo()
    {
        var total=<%=data.size()%>;
        for(i=0;i<total;i++){  obj.getCellTemplate(1,i).setControlProperty("value",false); }
    }

    
</script>
<!-- ----------------------------------------- --------------------- -->


<script language="JavaScript">

       function Abrir_ventanaId ( pagina ) {
            var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes, width=600, height=550, top=50, left=200";
            window.open(pagina,"",opciones);
       }

       function Abrir_ventana (pagina) { 
              var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes, width=600, height=550, top=50, left=200";
            window.open(pagina,"",opciones);
    }

       function Abrir_pagina (pagina) {
          var opciones="location=mainFrame";
          window.open(pagina,"",opciones);
       }

       var ObjAnt="row";

       function A(c,i,page)
       {
         //alert(c + " " + i + " " + page);
         // if (ObjAnt == "row")
         //{ObjAnt = i;}
         // document.getElementById(ObjAnt).style.backgroundColor="#ffffff";
         //document.getElementById(i).style.backgroundColor=c;
         // ObjAnt=i;
          //interno.location.href=page+'?iIdDoc='+i;
          parent.frames["secondFrame"].location.href=page+'?iIdDoc='+i;
       }

       function B(c,i,page)
       {
          Abrir_ventana(page+'?idArchivoPendiente='+i);
       }


       /*function A(c,i)
{
document.getElementById(i).style.backgroundColor=c;
}

function overmouse()
{
color: #D3E1EE;
font-family: Arial, Helvetica, sans-serif;
font-size: 11px;
TEXT-INDENT: 0px;
}
function outMouse()
{
color: #FFFFFF;
font-family: Arial, Helvetica, sans-serif;
font-size: 11px;
TEXT-INDENT: 0px;

}*/

    </script>

<script>
          
        function CargarPopup()
        {
           var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=50, height=200, top=400, left=700";
           window.open("./pages/tramite/popupOcultarCol.jsp", "", opciones);

        }
    </script>

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<style type="text/css">
<!--
body {
	background-color: #ffffff;
}
-->
</style>
<STYLE TYPE="text/css">
<!--
.barra {
	scrollbar-3dlight-color: #CCCCCC;
	scrollbar-arrow-color: #568BBF;
	scrollbar-base-color: #C3D5E9;
	scrollbar-darkshadow-color: #666666;
	scrollbar-face-color: ;
	scrollbar-highlight-color: #669BCD;
	scrollbar-shadow-color: #999999;
}
-->
</STYLE>
<style type="text/css" title="text/css">
/* <![CDATA[ */
.SI-FILES-STYLIZED label.cabinet {
	width: 16px;
	height: 16px;
	background: url(images/adjuntos.bmp) 0 0 no-repeat;
	display: block;
	overflow: hidden;
	cursor: pointer;
}

.SI-FILES-STYLIZED label.cabinet input.file {
	position: relative;
	height: 100%;
	width: auto;
	opacity: 0;
	-moz-opacity: 0;
	filter: progid : DXImageTransform.Microsoft.Alpha ( opacity = 0 );
}
/* ]]> */
</style>

<style type="text/css">
<!--
.style1 {
	color: #FFFFFF
}

.arrastrable {
	width: 100px;
	overflow: hidden;
}
-->
</style>
<script type="text/javascript">
         function nuevoDoc() {
            parent.location.href="NuevoDocumento_inicio.action";
         }

         function verSeguimiento() {
            parent.location.href="Seguimiento_mostrarInicio.action";
         }

         function nuevoDocSas() {
            parent.location.href = "CargarframeSAS.action";
         }
      </script>
</head>
<body class="barra">
<table width="100%">
	<tr>
		<td height="4" colspan="6"></td>
	</tr>
	<tr>
		<td height="20" colspan="6" width="99%">
		<table width="98%" border="0" align="center">
			<td width="0%" height="14" rowspan="2"></td>
			<td width="29%" align="left"><font color="669BCD">Bienvenido
			</font><font color="0099FF"><s:property value="#session.nombres" /></font>
			</td>
			<td width="11%" rowspan="2"></td>
			<td width="40%" rowspan="2" align="right"><%--  <s:if test="#session.rol=='user'">
				 
                 <img onClick="location.href='pages/bandeja/prueba.html'" src="images/botonNuevoExp.gif" style="cursor:hand"/>&nbsp;&nbsp;
                   
                 <img onClick="location.href='pages/bandeja/crearLista.html'" src="images/lista.gif" style="cursor:hand"/>&nbsp;&nbsp;
                 <img onClick="location.href='pages/bandeja/calendario.html'" name="date" id="campo_fecha1" src="images/calendario.bmp" style="cursor:hand"/>&nbsp;&nbsp;
                 <img onClick="nuevoDoc()" src="images/botonNuevo.gif"style="cursor:hand"/>
              </s:if>  --%> <s:if
				test="#session._RECURSO.UsuFinBtnNueExp == 1">
				<%--           ss
                                    <a href="<s:url action="inicio"><s:param name="sTipoFrame">nuevoexp</s:param></s:url>" target="_parent"><img alt="Crear Expediente" border="0" src="images/botonNuevoExp.gif" /></a>
                                 --%>
			</s:if> <s:if test="#session._RECURSO.UsuFinBtnLis == 1">
				<a href="<s:url action="goFrameLoadListaArray" />" target="_parent"><img
					alt="Listas" border="0" src="images/lista.gif" /></a>
			</s:if> <s:if test="#session._RECURSO.UsuFinBtnSeg == 1">
				<img onClick="verSeguimiento()" alt="Seguimiento" name="date"
					id="campo_fecha1" src="images/calendario.bmp"
					style="cursor: pointer" />&nbsp;&nbsp;
                           </s:if> <s:if
				test="#session._RECURSO.UsuFinBtnNueDocPri == 1">
				<img onClick="nuevoDoc()" src="images/botonNuevo.gif"
					style="cursor: pointer" alt="Nuevo Documento" />
			</s:if> <s:if test="#session._RECURSO.nvodocsas == 1">
				<img onClick="nuevoDocSas()" src="images/botonNuevo.gif"
					style="cursor: pointer" alt="Nuevo Documento" />
			</s:if></td>
			<!--td width="16%" rowspan="2" align="right"><input name="nombre" type="text" class="cajaMontoTotal" value="" size="20">
            <img  align="absmiddle" src="images/search.gif" border="0"/></td-->

			<td width="17%" rowspan="2" align="right"><s:if
				test="#session.rol=='user'">
				<s:form action="doSearchinner" method="post">
					<s:textfield name="objEfa.exp_referencia" size="12" />
					<s:submit value="Buscar" cssClass="button" />
				</s:form>
			</s:if></td>
			<td width="15%" rowspan="2" align="right"><s:elseif
				test="#session.rol=='mp'">
				<a href="<s:url action="goFrameNewDoc" />" target="_parent"><img
					alt="Crear Documento" border="0" src="images/botonNuevo.gif" /></a>
			</s:elseif></td>
			<td width="2%" rowspan="2"></td>
			<tr>
				<td align="left"><font color="0099FF"><a
					href="<s:url action="doLogout" />" target="_parent">Cerrar
				Sesi&oacute;n</a></font></td>
		</table>
		</td>
	</tr>
	<tr>
		<td height="4" colspan="6"></td>
	</tr>
	<tr>
		<td height="20" colspan="6" class="titulo" width="99%">
		<table width="70%" border="0" height="20" align="left">
			<tr>
				<td bgcolor="#4481B8" width="30%" align="center" class="tituloRojo">
				<s:if test="#session.rol=='user'">
                  Documentos Pendientes
                </s:if></td>
				<td><!--
                &nbsp;&nbsp;<input type="button" value="Control de columnas" onclick="CargarPopup()" class="button">
                --></td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td colspan="6">
		<script type="text/javascript">
              // Escribimos la grid del active W.
              document.write(obj);
           </script></td>
	</tr>
</table>
</body>
</html>
