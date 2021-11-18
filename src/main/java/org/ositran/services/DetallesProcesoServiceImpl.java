/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.services;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.ositran.daos.GridXGridColumnaDAO;
import org.ositran.dojo.grid.Estructura;
import org.ositran.utils.Constantes;

import com.btg.ositran.siged.domain.GridColumna;
import com.btg.ositran.siged.domain.GridXGridColumna;

public class DetallesProcesoServiceImpl implements DetallesProcesoService {

	private GridXGridColumnaDAO gridXGridColumnaDao ; 
	private String grupoclasificacion;
        private static Logger log = Logger.getLogger(DetallesProcesoServiceImpl.class);
		
	@Override
	public List<Estructura> getEstructuraAltU(String grupoclasificacion) {
		// TODO Auto-generated method stub
		this.grupoclasificacion=grupoclasificacion;
		log.debug(grupoclasificacion);
		int posi=0;
		List<Estructura> lstEstructura = new ArrayList <Estructura> ();
		try{
			List<GridXGridColumna> columnas = gridXGridColumnaDao.encontrarPorGrid( new Integer(Constantes.TIPO_GRID_REPORTE_DETALLADO ) );
			
			
			for (int i=0; i<columnas.size()-1; i++){
				lstEstructura.add(new Estructura());
			}
			
			for(GridXGridColumna columnax : columnas) {
				GridColumna columna =  columnax.getGridColumna() ; 
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
			log.debug("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		}
		
		return  lstEstructura;
	}
	
	public String hello(){
		return "Hola mundo!";
	}

	public String getGrupoclasificacion() {
		return grupoclasificacion;
	}

	public void setGrupoclasificacion(String grupoclasificacion) {
		this.grupoclasificacion = grupoclasificacion;
	}
}
