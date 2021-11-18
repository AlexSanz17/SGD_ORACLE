<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
        <div 
	        dojoType="dojo.data.ItemFileReadStore" 
	        jsId="continentStore" 
	        data="{ identifier: 'name',
						  label:'name',
						  items: [
						          { name:'Oficios', type:'continent', 
						         		children:[
						         					{_reference:'Oficio X Procedimiento'},
						         		   			{_reference:'Oficios D/F de plazo'} 
						         		   		]},
							     		{ name:'Oficio X Procedimiento', type:'reporte', url:'CantOficioProc' },
							     		{ name:'Oficios D/F de plazo', type:'reporte', url:'CantOficioDFplazo' },
							     		
							     { name:'Descargos', type:'continent',
							         	children:[
							         				{_reference:'Recibidos D/F Plazo'},
						         		   			{_reference:'Evaluacion de Descargo X UT'}
						         		  		]},
							     		{ name:'Recibidos D/F Plazo', type:'reporte', url:'CantDescargoFPlazo' },
							     		{ name:'Evaluacion de Descargo X UT', type:'reporte', url:'CantInfEvalDescXUT' },
							     		
								 { name:'Resoluciones', type:'continent', 
						         		children:[
						         					{_reference:'Proyecto Resolucion GFE-GL'}
						         		   		]},
							     		{ name:'Proyecto Resolucion GFE-GL', type:'reporte', url:'ProyResGFE-GL' },
								 { name:'Reconsideraciones', type:'continent', 
						         		children:[
						         					{_reference:'Rec. D/F Plazo'},
						         					{_reference:'Rec. X Proced.'},
						         					{_reference:'Proy - Rec. Pendiente'}
						         		   		]},
							     		{ name:'Rec. D/F Plazo', type:'reporte', url:'RecDFPlazo' },
							     		{ name:'Rec. X Proced.', type:'reporte', url:'RecXProced' },
							     		{ name:'Proy - Rec. Pendiente', type:'reporte', url:'ProyRecXPendiente' },
								 { name:'Apelaciones', type:'continent', 
						         		children:[
						         					{_reference:'Recibido. D/F Plazo'},
						         					{_reference:'Expediente Apelacion'}
						         		   		]},
							     		{ name:'Recibido. D/F Plazo', type:'reporte', url:'ApelDFPlazo' },
							     		{ name:'Expediente Apelacion', type:'reporte', url:'ExpApel' },
								 { name:'Recursos', type:'continent', 
						         		children:[
						         					{_reference:'Proc. x Analista'},
						         					{_reference:'Rec. x Procedimiento'},
						         					{_reference:'Arch - Amon - Multa'},
						         					{_reference:'Res. x Procedimiento'},
						         					{_reference:'Fund - Infun - Inproc'}
						         		   		]},
							     		{ name:'Proc. x Analista', type:'reporte', url:'CantAnalistaxProc' },
							     		{ name:'Rec. x Procedimiento', type:'reporte', url:'CantRecxProc' },
							     		{ name:'Arch - Amon - Multa', type:'reporte', url:'CantRecxAAM' },
							     		{ name:'Res. x Procedimiento', type:'reporte', url:'CantResxProc' },
							     		{ name:'Fund - Infun - Inproc', type:'reporte', url:'CantRecxFII' }
						       ]
				}
	        ">
        </div>
        <div 
	        dojoType="dijit.tree.ForestStoreModel" 
	        jsId="continentModel" 
	        store="continentStore"
	        query="{type:'continent'}" 
	        rootId="continentRoot" 
	        rootLabel="Reportes"
	        childrenAttrs="children">
        </div>
        <div 
        	dojoType="dijit.Tree" 
        	id="mytree" 
        	model="continentModel" 
        	openOnClick="true">
        	
            <script 
		            type="dojo/method" 
		            event="onClick" 
		            args="item">
						  javascript:buildTabsFromToolBarTop(continentStore.getValue(item, "url"),null)
            </script>
        </div>
    <script type="text/javascript">
        dojo.require("dojo.data.ItemFileReadStore");
        dojo.require("dijit.Tree");
    </script>
    <script type="text/javascript">
        dojo.addOnLoad(function() {
            if (window.pub) {
                window.pub();
            }
        });
    </script>
    </body>
</html>