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
      /*DUMMY*/
      var storeProceso = new dojo.data.ItemFileReadStore({url: "autocompletarProcesoParticipante.action"});
      var storeProcesoClasificacion = new dojo.data.ItemFileReadStore({url: "Parametro_ProcesoClasificacion.action"});
      var storeClasificacion = new dojo.data.ItemFileReadStore({url: "Parametro_Clasificacion.action"});
      var storeEstadoReporte = new dojo.data.ItemFileReadStore({url: "Parametro_EstadoReporte.action"});    
      var storeProcesoReporte = new dojo.data.ItemFileReadStore({url: "Parametro_ProcesoReporte.action"});    
      var storeGrupoProcesos = new dojo.data.ItemFileReadStore({url: "Parametro_GrupoProcesos.action"});    

		var llenarProcesos = function () {
			//alert("ggg");  
			var procesoClasificacion  =  dijit.byId("procesoClasificacion").getValue();
			/*El .store indica el contenido del cmb*/
		    console.debug(" llenarProceso "+procesoClasificacion) ; 
			if(procesoClasificacion=="grupoproceso"){
				 dijit.byId("procesoElegido").reset();
				 dijit.byId("procesoElegido").store =  storeGrupoProcesos ;
	 	 	}
			if(procesoClasificacion=="proceso"){
				 dijit.byId("procesoElegido").reset();
				 dijit.byId("procesoElegido").store =  storeProcesoReporte ;
	 	 	} 
		};

		var completarFechaHasta = function() {
        	dijit.byId('fechaHasta').constraints.min = dijit.byId('fechaDesde').getValue();
        }
		var completarFechaDesde = function() {
			dijit.byId('fechaDesde').constraints.max = dijit.byId('fechaHasta').getValue();
        }
        
	  var fecha = new Date(); 
	  var hecho = false;
	  dojo.addOnLoad(function(){
			new dijit.form.DateTextBox({
				name			: "fechaDesde",
				constraints		: {datePattern:"dd/MM/yyyy"},
				invalidMessage	: "Ingrese una fecha con el formato dd/mm/yyyy",
				required		: false,
				onChange		: completarFechaHasta
			},"fechaDesde");
			
			new dijit.form.DateTextBox({
				name			: "fechaHasta",
				constraints		: {datePattern:"dd/MM/yyyy"},
				invalidMessage	: "Ingrese una fecha con el formato dd/mm/yyyy",
				required		: false,
                onChange		: completarFechaDesde
			},"fechaHasta");
			/*Combo proceso clasificacion*/
			new dijit.form.FilteringSelect({
				  jsId 			  :"procesoClasificacion",
			      name           : "procesoClasificacion",
			      store          : storeProcesoClasificacion,
			      searchAttr     : "label",
			      autoComplete   : false,
			      hasDownArrow   : true,
			      highlightMatch : "all",
			      onChange		 : llenarProcesos,
			      invalidMessage : "Seleccione el tipo de proceso a clasificar",
			      required       : true,
			      value			 : "Seleccione"
			   }, "procesoClasificacion");
			/*Combo proceso elegido*/
			new dijit.form.FilteringSelect({
				  jsId 			  :"procesoElegido",
			      name           : "procesoElegido",
			      searchAttr     : "label",
			      autoComplete   : false,
			      hasDownArrow   : true,
			      highlightMatch : "all",
			      invalidMessage : "Seleccione el proceso a encontrar",
			      required       : false,
			      value			 : "Seleccione"
			   }, "procesoElegido");
			
		}); 
            function Abrir_ventana (pagina) {
                var opciones="toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, width=1000, height=700, top=50, left=200";
                var ventana = window.open(pagina,"",opciones);
                ventana.moveTo(60,50);
            }
                 
			function buscar (){		
				var correcto = true;
				var clasificar= dijit.byId("clasificar").getValue();  
				var procesoClasificacion=dijit.byId("procesoClasificacion").getValue(); 
				var procesoElegido=dijit.byId("procesoElegido").getValue();
				var estado=dijit.byId("estado").getValue();
				var fechaDesde=dijit.byId("fechaDesde").getDisplayedValue(); 
				var fechaHasta=dijit.byId("fechaHasta").getDisplayedValue();
				if(clasificar==""){
					correcto = false;
					alert("Debe seleccionar un criterio de clasificación");
				}else{
					if(procesoClasificacion==""&&procesoElegido==""&&estado==""){
						correcto = false;
						alert("Debe seleccionar un filtro para el reporte");
					}else{
						if((fechaHasta!=""&&fechaDesde=="")||(fechaDesde!=""&&fechaHasta=="")){
							alert("Debe ingresar las dos fechas para poder filtrar por fecha");
							correcto = false;
						} 
					}
				}
				if(correcto){
					//alert(procesoElegido);
					var pagina = "SMDReporteSiged1_buscarReporte1.action?clasificar="+clasificar+"&procesoClasificacion="+procesoClasificacion+"&procesoElegido="+procesoElegido+"&estado="+estado+"&fechaDesde="+fechaDesde+"&fechaHasta="+fechaHasta;
					//document.location = "SMDReporteSiged1_buscarReporte1.action?clasificar="+clasificar+"&procesoClasificacion="+procesoClasificacion+"&procesoElegido="+procesoElegido+"&estado="+estado+"&fechaDesde="+fechaDesde+"&fechaHasta="+fechaHasta;
					Abrir_ventana(pagina);
				}
			}
      </script>
</head>
<body>
      <table width="95%" border="0" height="30" align="center" bgcolor="#FFFFFF">
                        <tr>

                           <td width="15%" height="16"  align="right" >Seleccionar</td>       
                           <td width="35%" class="label">
                           <select id="procesoClasificacion"  > </select>
                           <%-- 
                              <select dojoType="dijit.form.FilteringSelect"
                                      store="storeProcesoClasificacion"
                                      idAttr="id"
                                      labelAttr="label"
                                      searchAttr="label"
                                      value="<s:property value="iIdProceso"/>" 
                                      name="iIdProceso"
                                      id="iIdProceso"
                                      onChange="llenarProceso"
                                      required="false"
                                      size="80" /> 
                                 --%>    
                                      
                           </td>           
                
                               <td width="15%" height="16"  align="right" >Seleccionar</td>       
                           <td width="35%" class="label">
                            <select id="procesoElegido"></select>
                            <%-- 
                              <select dojoType="dijit.form.FilteringSelect"
                                      jsId="procesoElegido"
                                      idAttr="id"
                                      labelAttr="label"
                                      searchAttr="label"
                                      value="<s:property value="iIdProceso"/>" 
                                      name="procesoElegido"
                                      id="procesoElegido"
                                      required="false"
                                      size="80" /> 
                                 --%>
                           </td>   
                                
                        </tr>
                        
                        <tr>

                           <td width="15%" height="16"  align="right" >Estado </td>       
                           <td width="35%" class="label">
                              <select dojoType="dijit.form.FilteringSelect"
                                      store="storeEstadoReporte"
                                      idAttr="id"
                                      labelAttr="label"
                                      searchAttr="label"
                                      value="" 
                                      name="estado"
                                      id="estado"
                                      jsId="estado" 
                                      required="false"
                                      size="80" /> <br/>
                           </td>         
                
                           <td width="15%" height="16"  align="right" >Clasificar</td>       
                           <td width="35%" class="label">
                              <select dojoType="dijit.form.FilteringSelect"
                                      store="storeClasificacion"
                                      idAttr="id"
                                      labelAttr="label"
                                      searchAttr="label"
                                      value="" 
                                      name="clasificar"
                                      id="clasificar"
                                      jsId="clasificar" 
                                      required="true"
                                      size="80" /> 
                           </td>   
                                
                        </tr>
                            <tr>
                           <td width="15%" height="16"  align="right" >Fecha desde:  </td>       
                           <td width="35%" class="label">
                              <input id="fechaDesde" type="text" />
                           </td>         
                
                           <td width="15%" height="16"  align="right" >Fecha hasta: </td>       
                           <td width="35%" class="label"> 
                              <input id="fechaHasta" type="text" />
                           </td>   
                        </tr>
                        <tr>   
                        <td> </td>
                        <td> </td>  
                        <td> </td>  
                        <td>  
                        
                          <button dojoType="dijit.form.Button"
                             type="button"
                             id="btnBuscar"
                             jsId="btnBuscar"
                             iconClass="siged16 sigedSearch16"
                             onClick="buscar"
                             showLabel="true">Ver reporte</button>
                        
                         </td></tr>
                        </table>
</body>
</html>