package org.ositran.services;
/*DUMMY*/
import java.util.ArrayList;
import java.util.List;

import org.ositran.daos.FilaRResumenProcesoDAO;
import org.ositran.daos.GridXGridColumnaDAO;
import org.ositran.dojo.grid.Estructura;
import org.ositran.utils.Constantes;

import com.btg.ositran.siged.domain.FilaRResumenProceso;
import com.btg.ositran.siged.domain.GridColumna;
import com.btg.ositran.siged.domain.GridXGridColumna;

/**
 * @author zenthei
 *
 */
public class FilaRResumenProcesoServiceImpl implements FilaRResumenProcesoService{
	private FilaRResumenProcesoDAO dao;
	private GridXGridColumnaDAO gridXGridColumnaDao ; 
	private String grupoclasificacion;
	
	public FilaRResumenProcesoServiceImpl() {
		
	}
	public FilaRResumenProcesoServiceImpl(FilaRResumenProcesoDAO dao){
		this.dao = dao;
	}
	
	@Override
	public List<FilaRResumenProceso> getReporteInicio() {
		// TODO Auto-generated method stub
		
		return dao.reporteInicialResumen();
	}

	public List<FilaRResumenProceso> generarReporteResumen (String procesoClasif, String procesoElegido, String estado, String fechaDesde, String fechaHasta){
		
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
		return dao.generarReporteResumen(procesoClasif, procesoElegido, estado, fechaDesde, fechaHasta);
	}
	
	public  List<Estructura> getStructura (List<FilaRResumenProceso> estructuras)  
	{
		int posi = 0;
		
		/**ENCUENTRA LA ESTRUCTURA DEFINIDA EN LA BASE DE DATOS**/
		List<GridXGridColumna> columnas = gridXGridColumnaDao.encontrarPorGrid( new Integer(Constantes.TIPO_GRID_REPORTE_RESUMEN ) );
		List<Estructura> lstEstructura = new ArrayList <Estructura> ();
		         
		/**LLENA LA LISTA DE ESTRUCTURAS. CADA UNA ES UNA COLUMNA**/
		for (int i=0; i<columnas.size()-1; i++){
			lstEstructura.add(new Estructura());
		}
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
			else if(columna.getField().equals("estado")){posi=5;}
			else if(columna.getField().equals("propietario")){posi=6;}
			if(posi>=0){
				lstEstructura.set(posi, new Estructura(Integer.valueOf(columna.getIdgridcolumna() ) , columna.getField(), columna.getName(), columna.getNoresize(), columna.getWidth(), 0, columna.getFormater(),false,false));
			}
		}
		/*************RETORNA LA ESTRUCTURA ORDENADA***************/
		return  lstEstructura ; 
          
	}

	public FilaRResumenProcesoDAO getDao() {
		return dao;
	}

	public void setDao(FilaRResumenProcesoDAO dao) {
		this.dao = dao;
	}

	public GridXGridColumnaDAO getGridXGridColumnaDao() {
		return gridXGridColumnaDao;
	}

	public void setGridXGridColumnaDao(GridXGridColumnaDAO gridXGridColumnaDao) {
		this.gridXGridColumnaDao = gridXGridColumnaDao;
	} 
	
	
}
