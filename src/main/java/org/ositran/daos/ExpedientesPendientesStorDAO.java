/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.btg.ositran.siged.domain.ExpedientesPendientesStor;


public interface ExpedientesPendientesStorDAO {

	@PersistenceContext(unitName = "sigedPU")
	public abstract void setEm(EntityManager em);
	
	public abstract List<ExpedientesPendientesStor> listarTodo();
	
	public abstract List<ExpedientesPendientesStor> generarLista(String fechaDesde, String fechaHasta);
}
