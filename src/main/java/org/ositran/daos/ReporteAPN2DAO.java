/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.btg.ositran.siged.domain.ExpedientesPendientesStor;
import com.btg.ositran.siged.domain.FilaReporteAPN2;

public interface ReporteAPN2DAO {
	@PersistenceContext(unitName = "sigedPU")
	public abstract void setEm(EntityManager em);
	
	public abstract List<FilaReporteAPN2> getListaReporteAPN2(String area,String tipodocumento,String prioridad);
	public abstract List<FilaReporteAPN2> getListaReporteAPN3(String area,String tipodocumento,String fechaDesde, String fechaHasta);
	public abstract List<ExpedientesPendientesStor> getListaReporteAPN4(String estado,String usuario);
}
