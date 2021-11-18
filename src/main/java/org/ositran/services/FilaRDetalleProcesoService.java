package org.ositran.services;
/*DUMMY*/
import java.util.Date;
import java.util.List;

import org.ositran.dojo.grid.Estructura;

import com.btg.ositran.siged.domain.FilaRDetalleProceso;


public interface FilaRDetalleProcesoService {
	
	public List<FilaRDetalleProceso> getReporteInicio();
	
	public List<FilaRDetalleProceso> generarReporteDetalle (String procesoClasif, String procesoElegido, String estado, String fechaDesde, String fechaHasta);
	
	public  List<Estructura> getStructura (List<FilaRDetalleProceso> estructuras) ;
	
	public  List<Estructura> getStructuraAlt1(String grupoclasificacion);
}
