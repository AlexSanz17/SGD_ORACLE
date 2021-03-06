package org.ositran.services;
/*DUMMY*/
import java.util.ArrayList;
import java.util.List;

import org.ositran.daos.FilaRDetalleProcesoDAO;
import org.ositran.daos.GridXGridColumnaDAO;
import org.ositran.dojo.grid.Estructura;
import org.ositran.utils.Constantes;

import com.btg.ositran.siged.domain.FilaRDetalleProceso;
import com.btg.ositran.siged.domain.GridColumna;
import com.btg.ositran.siged.domain.GridXGridColumna;

public class FilaRDetalleProcesoServiceImpl implements
		FilaRDetalleProcesoService {
	private FilaRDetalleProcesoDAO dao;
	private GridXGridColumnaDAO gridXGridColumnaDao ; 
	private String grupoclasificacion;
	
	public FilaRDetalleProcesoServiceImpl (FilaRDetalleProcesoDAO dao){
		this.dao = dao;
	}
	
	@Override
	public List<FilaRDetalleProceso> getReporteInicio() {
		// TODO Auto-generated method stub
		return dao.reporteInicialDetallado();
	}
	
	public List<FilaRDetalleProceso> generarReporteDetalle (String procesoClasif, String procesoElegido, String estado, String fechaDesde, String fechaHasta){
		
		/***********LLENA LOS POSIBLES CAMPOS VACIOS****************/
		if((procesoClasif==null)||(procesoClasif.equals(""))){
			grupoclasificacion ="grupoproceso" ;
		}else{
			grupoclasificacion = procesoClasif;
		}
		if(estado==null){
			estado="Todos";
		}
		if(procesoElegido==null){
			procesoElegido="Todos";
		}
		
		/*****EL DAO SE ENCARGA DE LA CONSULTA*******/
		return dao.generarReporteDetalle(procesoClasif, procesoElegido, estado, fechaDesde, fechaHasta);
	}
	
	public  List<Estructura> getStructura (List<FilaRDetalleProceso> estructuras)  
	{
		int posi = 0;
		
		/**ENCUENTRA LA ESTRUCTURA DEFINIDA EN LA BASE DE DATOS**/
		List<GridXGridColumna> columnas = gridXGridColumnaDao.encontrarPorGrid( new Integer(Constantes.TIPO_GRID_REPORTE_DETALLADO ) );
		List<Estructura> lstEstructura = new ArrayList <Estructura> ();
		
		/**LLENA LA LISTA DE ESTRUCTURAS. CADA UNA ES UNA COLUMNA**/
		for (int i=0; i<columnas.size()-1; i++){
			lstEstructura.add(new Estructura());
		}
		
		/**LLENA LA LISTA DE ESTRUCTURAS. CADA UNA ES UNA COLUMNA**/
		for(GridXGridColumna columnax : columnas) {
			GridColumna columna =  columnax.getGridColumna() ; 
			
			/*********ESTA PARTE SOLO SIRVE PARA ORDENAR***********/
			if(columna.getField().equals("grupoproceso")){
				if(grupoclasificacion.equals("grupoproceso")){
					posi=0;
				}else{
					posi=-1;
				}
			}
			else if(columna.getField().equals("proceso")){
				if(grupoclasificacion.equals("proceso")){
					posi=0;
				}else{
					posi=-1;
				}
			}
			else if(columna.getField().equals("expediente")){posi=1;}
			else if(columna.getField().equals("fechaexpediente")){posi=2;}
			else if(columna.getField().equals("cliente")){posi=3;}
			else if(columna.getField().equals("asunto")){posi=4;}
			else if(columna.getField().equals("responsable")){posi=5;}
			else if(columna.getField().equals("tiempoatencion")){posi=6;}
			else if(columna.getField().equals("estado")){posi=7;}
			else if(columna.getField().equals("propietario")){posi=8;}
			if(posi>=0){
				lstEstructura.set(posi, new Estructura(Integer.valueOf(columna.getIdgridcolumna() ) , columna.getField(), columna.getName(), columna.getNoresize(), columna.getWidth(), 0, columna.getFormater(),false,false));
			}
		}
		
		/*************RETORNA LA ESTRUCTURA ORDENADA***************/
		return  lstEstructura ; 
	}
	
	/***************Estructura de las grillas adicionales********************************/
	public  List<Estructura> getStructuraAlt1(String grupoclasificacion){
		int posi=0;
		List<Estructura> lstEstructura = new ArrayList <Estructura> ();
		try{
			List<GridXGridColumna> columnas = gridXGridColumnaDao.encontrarPorGrid( new Integer(Constantes.TIPO_GRID_DETALLES_REPORTE ) );			
			for (int i=0; i<columnas.size(); i++){
				lstEstructura.add(new Estructura());
			}
			
			for(GridXGridColumna columnax : columnas) {
				GridColumna columna =  columnax.getGridColumna() ; 
				if(columna.getField().equals("nombre")){posi=0;}
				else if(columna.getField().equals("tiempototal")){posi=1;}
				else if(columna.getField().equals("cantidadexpedientes")){posi=2;}
				else if(columna.getField().equals("cantidadtotalexpedientes")){posi=3;}
				else if(columna.getField().equals("tiempopromedio")){posi=4;}
				else if(columna.getField().equals("tiempopromediototal")){posi=5;}
				if(posi>=0){
					lstEstructura.set(posi, new Estructura(Integer.valueOf(columna.getIdgridcolumna() ) , columna.getField(), columna.getName(), columna.getNoresize(), columna.getWidth(), 0, columna.getFormater(),false,false));
				}
			}
		}catch (NullPointerException e){
		}
		return  lstEstructura;
	}

	public FilaRDetalleProcesoDAO getDao() {
		return dao;
	}

	public void setDao(FilaRDetalleProcesoDAO dao) {
		this.dao = dao;
	}

	public GridXGridColumnaDAO getGridXGridColumnaDao() {
		return gridXGridColumnaDao;
	}

	public void setGridXGridColumnaDao(GridXGridColumnaDAO gridXGridColumnaDao) {
		this.gridXGridColumnaDao = gridXGridColumnaDao;
	} 	
}
