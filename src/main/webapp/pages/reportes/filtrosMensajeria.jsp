<?xml version="1.0" encoding="UTF-8" ?>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Insert title here</title>
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
      </style><script type="text/javascript">

		/*var completarFechaHasta = function() {
        	dijit.byId('fechaHasta').constraints.min = dijit.byId('fechaDesde').getValue();
        };
		var completarFechaDesde = function() {
			dijit.byId('fechaDesde').constraints.max = dijit.byId('fechaHasta').getValue();
        };*/
        
	  var fecha = new Date(); 
	  var hecho = false;
	  dojo.addOnLoad(function(){
			new dijit.form.DateTextBox( {
				id : "sFechaInicio",
				jsId : "sFechaInicio",
				name : "sFechaInicio",
				constraints : {
					datePattern : 'dd/MM/yyyy'
				},
				invalidMessage : "Ingrese fecha de Inicio dd/MM/yyyy",
				required : "true",
				trim : "true",				
				value : new Date(),
				width : "10px"
			}, "divFechaInicio");
			new dijit.form.DateTextBox( {
				id : "sFechaFin",
				jsId : "sFechaFin",
				name : "sFechaFin",
				constraints : {
					datePattern : 'dd/MM/yyyy'
				},
				invalidMessage : "Ingrese fecha Fin dd/MM/yyyy",
				required : "true",
				trim : "true",
				value : new Date()
			}, "divFechaFin");					
			
		}); 
            function Abrir_ventana (pagina) {
                var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=1000, height=700, top=50, left=200";
                var ventana = window.open(pagina,"",opciones);
                ventana.moveTo(60,50);
            }
                 
			function buscar (){		
				var correcto = true;
				
				var fechaDesde=dijit.byId("sFechaInicio").getDisplayedValue(); 
				var fechaHasta=dijit.byId("sFechaFin").getDisplayedValue();

				var horaDesde=dijit.byId("horainicial").getDisplayedValue();
				var horaHasta=dijit.byId("horafinal").getDisplayedValue();
			
				console.debug("Fecha desde: "+fechaDesde+"- Fecha hasta:"+fechaHasta+" Hora desde: "+horaDesde+" Hora hasta"+horaHasta);
				if(fechaHasta==""||fechaDesde==""||horaDesde==""||horaHasta==""){
					alert("Debe ingresar las dos fechas y las dos horas para poder realizar la búsqueda");
					correcto = false;
				} 
					
				
				if(correcto){

					var id = window.open("","","width=600,height=500");
		            id.location.href = "generateReporteMensajeria.action?fechaDesde="+fechaDesde+"&fechaHasta="+fechaHasta+"&horaDesde="+horaDesde+"&horaHasta="+horaHasta;
					
				}
			}
		
      </script>
</head>
<body>
      <table width="95%" border="0" height="30" align="center" bgcolor="#FFFFFF">

                        <tr>
                           <td width="15%" height="16"  align="right" >Fecha desde:  </td>       
                           <td width="15%" class="label">
                              <div id="divFechaInicio" style="width: 10px"></div>   
                           </td>         
                		   <td width="15%" >
			                  <div dojoType="dijit.form.TimeTextBox"
			                       id="horainicial"
			                       jsId="horainicial"
			                       name="horainicial"
			                       required="true"
			                       trim="true">
			                     <script type="dojo/method" event="postCreate">
                     			      this.attr("value", new Date(0, 0, 0, 8, 0));                     			 
                    			 </script>
			                  </div>
                           </td>  	
                           <td width="15%" height="16"  align="right" >Fecha hasta: </td>       
                           <td width="15%" class="label"> 
                              <div id="divFechaFin"></div>   
                           </td>   
                           <td>
			                  <div dojoType="dijit.form.TimeTextBox"
			                       id="horafinal"
			                       jsId="horafinal"
			                       name="horafinal"
			                       required="true"
			                       trim="true">
			                     <script type="dojo/method" event="postCreate">                    			  
                    			       this.attr("value", new Date(0, 0, 0, 19, 0));                   			   
                   			  </script>
			                  </div>                           
                           </td>
                        </tr>
				         <tr>
				            <td height="14" colspan="4"> </td>
				         </tr>                      
                        <tr>   
                                          
                        <td width="15%" height="16"  align="left" colspan="2" style="padding-left: 20px">
                             <button dojoType="dijit.form.Button"
                             type="button"
                             id="btnBuscar"
                             jsId="btnBuscar"
                             iconClass="siged16 sigedSearch16"
                             onClick="buscar"
                             showLabel="true">Ver reporte</button>
                        </td>
					    <td> </td>        
                        <td> </td>  
                        <td>  
                        

                        
                         </td></tr>
                        </table>
</body>
</html>