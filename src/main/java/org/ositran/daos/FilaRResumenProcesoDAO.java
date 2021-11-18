/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;
/*DUMMY*/
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.btg.ositran.siged.domain.FilaRResumenProceso;

public interface FilaRResumenProcesoDAO {

	/* (non-Javadoc)
	 * @see org.ositran.daos.FilaRResumenProcesoDAO#setEm(javax.persistence.EntityManager)
	 */
	@PersistenceContext(unitName = "sigedPU")
	public abstract void setEm(EntityManager em);

	/* (non-Javadoc)
	 * @see org.ositran.daos.FilaRResumenProcesoDAO#reporteInicialResumen()
	 */
	public abstract List<FilaRResumenProceso> reporteInicialResumen();

	/* (non-Javadoc)
	 * @see org.ositran.daos.FilaRResumenProcesoDAO#generarReporteResumen(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	/* (non-Javadoc)
	 * @see org.ositran.daos.FilaRResumenProcesoDAO#generarReporteResumen(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public abstract List<FilaRResumenProceso> generarReporteResumen(
			String procesoClasif, String procesoElegido, String estado,
			String fechaDesde, String fechaHasta);
}