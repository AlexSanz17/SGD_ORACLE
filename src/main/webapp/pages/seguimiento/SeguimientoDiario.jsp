<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es">
<head> 
<title>Seguimiento Diario</title>
     <script type="text/javascript">     
       /*  dojo.require("dijit.form.FilteringSelect");
         dojo.require("dojo.data.ItemFileReadStore"); */ 
         // alert("<s:property value="filtro1"/>");
         
         var storeProceso = new dojo.data.ItemFileReadStore({url: "autocompletarProcesoParticipante.action?filtro1=<s:property value="filtro1"/>"});
              
      </script>

<%-- <link rel="stylesheet" type="text/css" href="css/estilo.css">--%>
<%--<script language="javascript" src="js/form.js"> </script>
<script language="Javascript" src="js/general.js"></script>
<link rel="stylesheet" type="text/css" media="all" href="css/calendar-green.css">
<script type="text/javascript" src="js/calendar.js"></script>
<script type="text/javascript" src="js/calendar-es.js"></script>
<script type="text/javascript" src="js/calendar-setup.js"></script>
--%>



 <script type="text/javascript">
    
    function  abrirPagina (iddoc){
       //alert('hii');
      //  window.open("/siged/Seguimiento_mostrarDocume.action?iIdDoc="+iddoc, "strWindowName", "") ;
       //  parent.location.href="/siged/Seguimiento_mostrarDocumento.action?iIdDoc="+iddoc;
           dijit.byId("titleSeguimiento").attr("open", false);
		   dijit.byId("contentGeneralSeguimiento").attr("content", "");
		   dijit.byId("seguimientoDocumento").attr("content", "");    

           dijit.byId("contentGeneralSeguimiento").setHref("doViewDoc.action?iIdDoc=" + iddoc);

    	   if (dijit.byId("divTreeExpediente")) { 
    	      dijit.byId("divTreeExpediente").destroy();
    	   }

         /*if(dijit.byId("dlgBusquedaExpediente")){
              dijit.byId("dlgBusquedaExpediente").destroy();
            }*/
            if(dijit.byId("sNroExpedienteBE")){
               dijit.byId("sNroExpedienteBE").destroy();
            }
            if(dijit.byId("sAsuntoBE")){
               dijit.byId("sAsuntoBE").destroy();
            }
            if(dijit.byId("procesoBE")){
               dijit.byId("procesoBE").destroy();
            }
            if(dijit.byId("sNroIdentificacionBE")){
               dijit.byId("sNroIdentificacionBE").destroy();
            }
            if(dijit.byId("sRazonSocialBE")){
               dijit.byId("sRazonSocialBE").destroy();
            }
            if(dijit.byId("fechaBE")){
               dijit.byId("fechaBE").destroy();
            }
            if(dijit.byId("grdBusquedaExpediente")){
               dijit.byId("grdBusquedaExpediente").destroy();
            }
      
    	   dijit.byId("seguimientoDocumento").setHref("doViewDocAdicionales.action?iIdDoc="+iddoc+"&idGrid=30");
       
         
    }
    function filtrarSeguimiento() { 
    	 resetTabs(0);

    	 var fecha =  dojo.byId("fechaActualString") ;
         // alert ("fecha "+fecha.value );
     	 var segfecha =  dojo.byId("fechaseguimiento") ; 
          //alert  ("segfecha "+segfecha.value ); 
     	 var partes  = segfecha.value.split("/"); 
     	 fecha.value = ""+partes[2]+""+partes[1]+ ""+partes[0]; 
     	 
        document.forms[0].action = "Seguimiento_mostrarDiario.action";
        dojo.xhrPost({
            form: dojo.byId("formSeguimiento"),
            mimetype: "text/html",  
            load: function(data) {    
	        	dijit.byId("titleSeguimiento").attr("open", true);
	 		    dijit.byId("contentGeneralSeguimiento").attr("content", "");
	 		    dijit.byId("seguimientoDocumento").attr("content", "");                  
            	dijit.byId("titleSeguimiento").setContent(data);                            
            }    
         }); 
        // document.forms[0].submit();   
     }



    function cambiarDia ( cambio ){
    	 resetTabs(0);
       var cad =  document.getElementById("fechaActualString").value ;
       var valor = parseInt(cad) +cambio ;
        
       document.getElementById("fechaActualString").value = valor ;
       document.forms[0].action = "Seguimiento_mostrarDiario.action";
         
    	   dojo.xhrPost({
               form: dojo.byId("formSeguimiento"),
               mimetype: "text/html",  
               load: function(data) { 
    		   dijit.byId("titleSeguimiento").attr("open", true);
	 		    dijit.byId("contentGeneralSeguimiento").attr("content", "");
	 		    dijit.byId("seguimientoDocumento").attr("content", "");                  
           	    dijit.byId("titleSeguimiento").setContent(data);        
               //	dijit.byId("tabSeguimiento").setContent(data);                       
               }    
            }); 
      //  document.forms[0].submit();
    }

    function  mostrarSemana () {    
       document.forms[0].action = "Seguimiento_mostrarSemanal.action"
    	   dojo.xhrPost({
               form: dojo.byId("formSeguimiento"),
               mimetype: "text/html",  
               load: function(data) {     
    		        // alert(data); 
    		        //	dijit.byId("tabSeguimiento").setContent(data);
    		    dijit.byId("titleSeguimiento").attr("open", true);
	 		    dijit.byId("contentGeneralSeguimiento").attr("content", "");
	 		    dijit.byId("seguimientoDocumento").attr("content", "");                  
            	dijit.byId("titleSeguimiento").setContent(data);      
               	//dijit.byId("tabSeguimiento").setContent(data);                        
               }    
            }); 
      // document.forms[0].submit();
    }
    function  mostrarDiario () {
    	 resetTabs(0);
        document.forms[0].action = "Seguimiento_mostrarDiario.action"
        	  dojo.xhrPost({
                  form: dojo.byId("formSeguimiento"),
                  mimetype: "text/html",  
                  load: function(data) {     
        		   dijit.byId("titleSeguimiento").attr("open", true);
  	 		       dijit.byId("contentGeneralSeguimiento").attr("content", "");
  	 		       dijit.byId("seguimientoDocumento").attr("content", "");                  
              	   dijit.byId("titleSeguimiento").setContent(data);    
                  	//dijit.byId("tabSeguimiento").setContent(data);                        
                  }    
               }); 
        // document.forms[0].submit();        
     }

    function  regresar (){
       parent.location.href  = "/siged/inicio.action?sTipoFrame=grid" ; 
       

    } 
	dojo.addOnLoad(function(){    
			new dijit.form.DateTextBox({ 
				name			: "fechaseguimiento",
				constraints		: {datePattern:"dd/MM/yyyy"},
				invalidMessage	: "Ingrese una fecha con el formato dd/mm/yyyy",
				onChangue  : cambiarfecha,  
				required		: true
			},"fechaseguimiento");  
			var a=parseInt(<s:date name='fechaActual' format='yyyy' />);
	        var m=parseInt(<s:date name='fechaActual' format='MM' />);
	        var d=parseInt(<s:date name='fechaActual' format='dd' />);
			var fecha=new Date(a,m-1,d); 
			try{
				dijit.byId("fechaseguimiento").attr("value",fecha);  
				dijit.byId("fechaseguimiento").attr("onchangue",cambiarfecha); 
			}catch(err){
				console.debug("error: "+err); 
			}
     
		}); 
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
    poorman_changeimage('repHeader2_Img', 'images//signo_menos.gif', 'images//signo_mas.gif');
    poorman_toggle('row1');
    poorman_toggle('row2');
    poorman_toggle('row3');
}

function Toggle_repHeader1()
{
    poorman_changeimage('repHeader1_Img', 'images//signo_menos.gif', 'images//signo_mas.gif');
    poorman_toggle('trRow1');
}
function Toggle_repHeader3()
{
    poorman_changeimage('repHeader3_Img', 'images//signo_menos.gif', 'images//signo_mas.gif');
    poorman_toggle('row1');
    poorman_toggle('row2');
    poorman_toggle('row3');
}

function Toggle_repHeader3()
{
    poorman_changeimage('repHeader3_Img', 'images//signo_menos.gif', 'images//signo_mas.gif');
    poorman_toggle('trRow3');
}
function Toggle_repHeader4()
{
    poorman_changeimage('repHeader4_Img', 'images//signo_menos.gif', 'images//signo_mas.gif');
    poorman_toggle('row1');
    poorman_toggle('row2');
    poorman_toggle('row3');
}

function Toggle_repHeader4()
{
    poorman_changeimage('repHeader4_Img', 'images//signo_menos.gif', 'images//signo_mas.gif');
    poorman_toggle('trRow4');
}
function Toggle_repHeader5()
{
    poorman_changeimage('repHeader5_Img', 'images//signo_menos.gif', 'images//signo_mas.gif');
    poorman_toggle('row1');
    poorman_toggle('row2');
    poorman_toggle('row3');
}

function Toggle_repHeader5()
{
    poorman_changeimage('repHeader5_Img', 'images//signo_menos.gif', 'images//signo_mas.gif');
    poorman_toggle('trRow5');
}
function Toggle_repHeader6()
{
    poorman_changeimage('repHeader6_Img', 'images//signo_menos.gif', 'images//signo_mas.gif');
    poorman_toggle('row1');
    poorman_toggle('row2');
    poorman_toggle('row3');
}

function Toggle_repHeader6()
{
    poorman_changeimage('repHeader6_Img', 'images//signo_menos.gif', 'images//signo_mas.gif');
    poorman_toggle('trRow6');
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

-->

</style>

</head>

	<body>
		<form id="formSeguimiento"  name="formSeguimiento" >
     <input type="hidden" id="fechaActualString" name="fechaActualString"  value="<s:date name="fechaActual" format="yyyyMMdd"/>" />
       
<table width="100%" border="0">

  <tr>
		<td height="20"colspan="3" class="titulo">
		<table width="99%" border="0" height="30" align="center" bgcolor="#A2C0DC">
          <tr>
            <td width="100px" align="left" class="titulo"></td>
            <td width="32%" align="right" class="titulo"><s:date name="fechaActual" format="dd"/> de <s:date name="fechaActual" format="MMMM"/> de <s:date name="fechaActual" format="yyyy"/></td>
          </tr>
        </table>
		</td>
	</tr>
<tr>
               <td height="20"colspan="3"  class="titulo">
                  <s:hidden name="bResponsable" />
               <%-- <s:if test="bResponsable">  --%>
                      <table width="99%" border="0" height="30" align="center" bgcolor="#FFFFFF">
                        <tr>
                           <td width="40%" align="left" class="titulo"> 
                              <input type="radio" id="filtro1" name="filtro1" value="mio" <s:if test="filtro1=='mio'">checked</s:if>  />Mis Pendientes
                           </td> 
                           <td width="15%" height="16"  align="right" >Proceso</td>       
                           <td width="35%" class="label">
                              <div dojoType="dijit.form.FilteringSelect"
                                      store="storeProceso"
                                      idAttr="id"
                                      labelAttr="label"
                                      searchAttr="label"
                                      value="<s:property value="iIdProceso"/>" 
                                      name="iIdProceso"
                                      id="iIdProceso"
                                      required="false"
                                      size="80" />
                           </td>         
                           <td width="15%" align="left" class="titulo"></td> 
                                
                        </tr>
                       <tr>
                           <td colspan="2" align="left" class="titulo">   
                              <input type="radio" id="filtro1" name="filtro1" value="todos" <s:if test="filtro1=='todos'">checked</s:if>  />Todos los Usuarios
                           </td> 
                           
                           <td  align="left"  class="titulo">
                                
                           <input id="fechaseguimiento"   type="text" /> 
                           </td>   
                           <td  align="left"  class="titulo">
                              <img src="images/buscar.bmp" border="0" onClick="filtrarSeguimiento()" alt="Buscar" style="cursor:pointer" />
                           </td>     
                          
                        </tr>
                     </table> 
               <%-- </s:if>--%>        
               </td>
            </tr>

	 <tr>
		<td height="20"colspan="3" class="titulo">
				<table width="99%" border="0" height="30" align="center" bgcolor="#FFFFFF">
          <tr>
            <td width="68%" align="left" class="titulo">

          <!-- <img src="images//regresar.bmp" border="0" onClick="regresar()" alt="Regresar"/> -->
			  <img src="images//dia.bmp" border="0" onClick="cambiarDia(0)" alt="Dia"/> 
			  <img src="images//semanal.bmp"  border="0" onClick="mostrarSemana()" alt="Semana Laboral"/>
			   <!--<img src="images//cita.bmp"  border="0" onClick="location.href='../../pages/bandeja/calendario.html'" alt="Nueva Cita"/>--></td>
            <td width="32%" align="right" class="titulo"></td>    
          </tr>
        </table>
		</td>
	</tr>



  <tr>
		<td colspan="3" class="titulo">
		<table width="99%" border="0" align="center" bgcolor="#A2C0DC">
          <tr>
            <td width="75%" align="center" class="titulo">
			<img src="images//anterior.jpg" border="0" onClick="cambiarDia(-1)" alt="Dia"/>
                  <s:date name="fechaActual" format="EEEE"/> , <s:date name="fechaActual" format="dd"/> de <s:date name="fechaActual" format="MMMM"/>
			<img src="images//adelante.jpg" border="0" onClick="cambiarDia(1)" alt="Dia"/></td>
             </tr>
        </table> 
		</td>
	</tr>

	<tr>
		<td height="20"colspan="3" class="titulo">
		<table width="99%" border="0" height="20" align="center" bgcolor="#FFFFFF">
          <tr>
            <td width="68%" align="center" class="titulo"></td>
             </tr>
        </table>
		</td>
	</tr>

 <s:if test="documentosPendiente!=null">
   <s:iterator value="documentosPendiente" status="" >
    <tr>

      <td height="20"colspan="3" class="titulo">
			<table  width="99%" border="1" height="35" align="center" bgcolor="#A2C0DC">  
			  <tr>
				<td style="width:12% " width="5%" align="left" class="titulo" bgcolor="#8DB1D5">  <s:date format="hh:mm aa" name="hora"  /> </td>
				<td style="width:88% "  width="92%" align="left" class="titulo" bgcolor="#D2DFEF"> 
                 <s:iterator value="trazabilidadDocumentos"  >   
                     <s:if test="documento.principal.equals('S')">
                        </s:if> 
                  <a <s:if test="destinatario.idusuario!=#session._USUARIO.idusuario"> class="normal"  </s:if> href="#" onclick="abrirPagina('<s:property value="documento.idDocumento" />')">                                        <%-- --%>
                      <%-- --%>  
                        <s:if test="destinatario.idusuario!=#session._USUARIO.idusuario">
                             [<s:property value="destinatario.usuario"  />] 
                       </s:if>
                     <%--  --%>
                   <s:property value="asunto"  />  </a>  &nbsp;&nbsp;&nbsp;
                
                </s:iterator>
            </td>
			  </tr>
			 </table>
		</td>

	</tr>
   </s:iterator>

   </s:if>
  <tr>
    <td height="14"  colspan="3"></td>
  </tr>


</table>
</form>

</body>
</html>

