<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
   <head>
      <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
      <title>Tramite Documentario</title>
      <style type="text/css">
         @import "js/dojo/dijit/themes/soria/soria.css";
         @import "js/dojo/dojox/grid/resources/soriaGrid.css";
         @import "css/estilo.css";
      </style>
      <script type="text/javascript" src="js/dojo/dojo/dojo.js" djConfig="parseOnLoad:true, isDebug: false"></script>
      <script type="text/javascript">
         dojo.require("dijit.form.DateTextBox");
         dojo.require("dojo.data.ItemFileReadStore");
         dojo.require("dojox.grid.DataGrid");
         
         var storeReporte;
         
      </script>
      <script type="text/javascript">
         function filtrarReporte() {
            var parameters = '';
            var fechadesde = dojo.byId('fechadesde').value;
            var fechahasta = dojo.byId('fechahasta').value;

            parameters += "sFechaDesde=" + fechadesde + "&";
            parameters += "sFechaHasta=" + fechahasta + "&";

            var idProceso = document.getElementById("idProceso").value;
            var idSede = document.getElementById("idSede").value;

            //alert("idProceso "+idProceso+ " idSede "+ idSede);
            console.log('sFechaDesde '+fechadesde+' sFechaHasta '+fechahasta);

            if(fechadesde!="" && fechahasta!=""){
                if(idProceso!="" ){
                	if(idSede !=""){
         				var diferencia = obtieneDiferencia(fechadesde, fechahasta);
                        console.log("La diferencia es de "+ diferencia);
                     	parameters += "sDiasReporte=" + diferencia + "&";
                     	parameters += "idSede=" + idSede + "&";
                     	parameters += "idProceso=" + idProceso;
                        storeReporte = new dojo.data.ItemFileReadStore({url: "enviarGridreporte.action?" + parameters});
                    
                        dijit.byId('grdReporte').setStore(storeReporte);
                    } 
                	else{
                		alert("Debe seleccionar una sede");
                    }
                }
                else{
                	alert("Debe seleccionar un proceso");
                }         
            } else{
				alert("Debe seleccionar el rango de fechas");
            }

         }

         function cleanFields() {
            dojo.byId('fechadesde').value = "";
            dojo.byId('fechahasta').value = "";
            dijit.byId('grdReporte').setStore("");
            storeReporte ="";
         }
         
         function fecha( cadena ) {  
        	   
        	    //Separador para la introduccion de las fechas  
        	    var separador = "/";  
        	   
        	    //Separa por dia, mes y año  
        	    if ( cadena.indexOf( separador ) != -1 ) {  
        	         var posi1 = 0;
        	         var posi2 = cadena.indexOf( separador, posi1 + 1 );
        	         var posi3 = cadena.indexOf( separador, posi2 + 1 );  
        	         this.dia = cadena.substring( posi1, posi2 );  
        	         this.mes = cadena.substring( posi2 + 1, posi3 );  
        	         this.anio = cadena.substring( posi3 + 1, cadena.length );  
        	    } else {  
        	         this.dia = 0 ; 
        	         this.mes = 0 ; 
        	         this.anio = 0 ;    
        	    }  
        	 } 

    	 function obtieneDiferencia(fechadesde, fechahasta){
    		  //Obtiene dia, mes y año  
             var fecha1 = new fecha( fechadesde );     
             var fecha2 = new fecha( fechahasta ); 
               
             //Obtiene objetos Date  
             var miFecha1 = new Date( fecha1.anio, fecha1.mes, fecha1.dia );  
             var miFecha2 = new Date( fecha2.anio, fecha2.mes, fecha2.dia );  
             
             //Resta fechas y redondea  
             var diferencia = miFecha1.getTime() - miFecha2.getTime();  
             var dias = (Math.floor(diferencia / (1000 * 60 * 60 * 24)))*(-1); 
             var segundos = Math.floor(diferencia / 1000);  
			 return 1+(dias);
         }
         
     	function exportar(){
            var parameters = '';
            var fechadesde = dojo.byId('fechadesde').value;
            var fechahasta = dojo.byId('fechahasta').value;
            parameters += "sFechaDesde=" + fechadesde + "&";
            parameters += "sFechaHasta=" + fechahasta + "&";
            if(fechadesde!="" && fechahasta!=""){                
 				var diferencia = obtieneDiferencia(fechadesde, fechahasta);
                console.log("La diferencia es de "+ diferencia);
             	parameters += "sDiasReporte=" + diferencia;
                storeReporte = new dojo.data.ItemFileReadStore({url: "enviarGridreporte.action?" + parameters});               
            }       	
         	if (storeReporte!=""){
        		//var tablas=document.getElementById("reporte");
        		//alert(tablas.innerHTML);
        		document.getElementById("html").value=document.getElementById("reporte").innerHTML;
				//console.log(document.getElementById("reporte").innerHTML);
        		//alert(document.getElementById("html").value);
        		document.form1.submit();
        		//document.getElementById("form1").submit();

        		
        		var tablas = document.getElementById("reporte");
        		document.getElementById("html").value=tablas.innerHTML;
        		//var parameters = '';
        		document.form1.action="exportarExcel.action?" + parameters   ;           
                document.form1.submit();
            }else {
				alert("Debe realizar primero una búsqueda");
            }            
    	}
    	
     	function seleccionarSede(){
     		document.getElementById("idSede").value = dijit.byId("idSedes").getValue();
     		//alert(dijit.byId("idSedes").getValue());
        }
        
     	function seleccionarProceso(){
     		document.getElementById("idProceso").value = dijit.byId("idProcesos").getValue();
     		//alert(dijit.byId("idProcesos").getValue());
        }
             	
        dojo.addOnLoad(function(){
            new dijit.form.FilteringSelect({
               store			: new dojo.data.ItemFileReadStore({url: "listaSedes.action"}),
               searchAttr		: "label",
               labelAttr		: "label",
               idAttr			: "ids",
               required		: false,
               hasDownArrow	: true,
               highlightMatch	: "all",
               onChange		: seleccionarSede
            },"idSedes");           
         });

        dojo.addOnLoad(function(){
            new dijit.form.FilteringSelect({
               store			: new dojo.data.ItemFileReadStore({url: "procesoMesaPartes.action"}),
               searchAttr		: "label",
               labelAttr		: "label",
               idAttr			: "ids",
               required		: false,
               hasDownArrow	: true,
               highlightMatch	: "all",
               onChange		: seleccionarProceso
            },"idProcesos");           
         });
      </script>
   </head>
   <body class="soria">
    <form name="form1" action="" method="POST">  
<!--    <form name="form1" action="exportarExcel.action" method="POST">      -->
      <table width="100%">
         <tr>
            <td height="4"  colspan="6"></td>
         </tr>
         <tr>
            <td height="20" colspan="6" width="99%">
               <table width="99%" border="0" align="center">
                  <td width="0%" height="14" rowspan="2"> </td>
                  <td width="29%" align="left">
                     <font color="669BCD">Bienvenido </font><font color="0099FF"><s:property value="#session.nombres" /></font>
                  </td>
                  <td width="19%" rowspan="2"></td>
                  <td width="34%" rowspan="2" align="right"></td>
                  <td width="16%" rowspan="2" align="right"></td>
                  <td width="2%" rowspan="2"></td>
                  <tr>
                  <td align="left">
                     <font color="0099FF"><a href="<s:url action="doLogout" />" target="_parent">Cerrar Sesi&oacute;n</a></font>
                  </td>
               </table>
            </td>
         </tr>
         <tr>
            <td height="4" colspan="6"></td>
         </tr>
         <tr>
            <td height="20"colspan="6" class="titulo" width="99%">
               <table width="70%" border="0" height="20" align="left">
                  <tr>
                     <td bgcolor="#4481B8" width="30%" align="center" class="tituloRojo">Reporte</td>
                     <td></td>
                  </tr>
               </table>
            </td>
         </tr>
         <tr>
            <td>
               <table width="100%">
                  <tr>
                     <td height="14" colspan="6">
                        <table width="100%" align="left">
                           <tr>
                              <td align="left">
                                 <img src="images/buscar.bmp" border="0" onClick="filtrarReporte()" alt="Buscar" style="cursor:pointer" />
                                 <img src="images/Limpiar.bmp" border="0" onClick="cleanFields()" alt="Limpiar" style="cursor:pointer" />
                                 <img src="images/excel.bmp" border="0" onClick="exportar()" alt="Exportar a Excel" style="cursor:pointer" />
                                 <input id="html" jsId="html" name="html" type="hidden"/>
                                 <input id="idProceso" name="idProceso" type="hidden"/>
                                 <input id="idSede" name="idSede" type="hidden"/>
                              </td>
                           </tr>
                        </table>
                     </td>
                  </tr>
                  <tr>
                     <td height="4" colspan="6" class="header"></td>
                  </tr>
                  <tr>
                     <td height="14" colspan="6">
                        <table width="95%"  border="1" align="left" bordercolor="#669BCD" bgcolor="#BFD9F1">
                           <tr>
                              <td height="100">
                                 <table width="100%" height="97" align="center">
                                    <tr>
                                       <td width="1%"></td>
                                       <td width="19%" height="5"></td>
                                       <td width="15%"></td>
                                       <td width="12%"></td>
                                       <td width="19%"></td>
                                       <td width="14%"></td>
                                       <td width="18%"></td>
                                       <td width="2%"></td>
                                    </tr>

                                    <tr>
                                       <td height="5"></td>
                                    </tr>
                                    <tr>
                                       <td></td>
                                       <td height="16" align="left" class="titulo">Intervalo de Fechas</td>
                                       <td colspan="5"></td>
                                       <td></td>
                                    </tr>
                                    <tr>
                                       <td></td>
                                       <td height="16" align="left">Desde</td>
                                       <td>
                                          <input id="fechadesde"
                                                 name="fechadesde"
                                                 type="text"
                                                 dojoType="dijit.form.DateTextBox"
                                                 required="false"
                                                 promptMessage="dd/MM/yyyy"
                                                 size="20"
                                                 constraints="{datePattern:'dd/MM/yyyy'}"
                                                 onChange="dijit.byId('fechahasta').constraints.min = this.getValue();" />
                                       </td>
                                       <td height="16" align="left">Hasta</td>
                                       <td colspan="2">
                                          <input id="fechahasta"
                                                 name="fechahasta"
                                                 type="text"
                                                 dojoType="dijit.form.DateTextBox"
                                                 required="false"
                                                 promptMessage="dd/MM/yyyy"
                                                 size="20"
                                                 constraints="{datePattern:'dd/MM/yyyy'}"
                                                 onChange="dijit.byId('fechadesde').constraints.max = this.getValue();" />
                                       </td>
                                       <td></td>
                                    </tr>
                                    <tr>
                                       <td></td>
                                       <td height="16" align="left">Sede</td>
                                       <td colspan="4">
                                          <input id="idSedes" />
                                       </td>
                                    </tr>     
                                    <tr>
                                       <td></td>
                                       <td height="16" align="left">Proceso</td>
                                       <td colspan="4">
                                          <input id="idProcesos" />
                                       </td>
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
                     <td height="4" class="header"></td>
                  </tr>
               </table>
            </td>
         </tr>
         <tr>
            <td >
            <div id="reporte">
               <table dojoType='dojox.grid.DataGrid' id='grdReporte' jsid='grdReporte' store='storeReporte' style='width:95%;height:700px;' border="1">
                  <thead>
                     <tr style='text-align:center;'>
                        <th width='5px' style='text-align:center;'></th>
                        <th field='hora' width='150px' editable='false' >Hora</th>
                        <th field='mp' width='150px' editable='false'>Ingresados a MP</th>
                        <th field='dig' width='150px' editable='false'>Digitalizados por DIG</th>
                        <th field='qas' width='150px' editable='false'>Aprobados por QAS</th>
                        <th field='folios' width='120px' editable='false'>Nro. Folios</th>
                     </tr>
                  </thead>
               </table>
            </div>
            </td>
         </tr>
      </table>
      </form>
   </body>
</html>
