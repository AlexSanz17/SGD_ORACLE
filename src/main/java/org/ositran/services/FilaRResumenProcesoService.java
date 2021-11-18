package org.ositran.services;
/*DUMMY*/
import java.util.List;

import org.ositran.dojo.grid.Estructura;

import com.btg.ositran.siged.domain.FilaRDetalleProceso;
import com.btg.ositran.siged.domain.FilaRResumenProceso;

public interface FilaRResumenProcesoService {

	/* (non-Javadoc)
	 * @see org.ositran.services.FilaRResumenProcesoService#getReporteInicio()
	 */
	public abstract List<FilaRResumenProceso> getReporteInicio();

	/* (non-Javadoc)
	 * @see org.ositran.services.FilaRResumenProcesoService#generarReporteResumen(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public abstract List<FilaRResumenProceso> generarReporteResumen(
			String procesoClasif, String procesoElegido, String estado,
			String fechaDesde, String fechaHasta);
	
	public  List<Estructura> getStructura (List<FilaRResumenProceso> estructuras); 
	

}