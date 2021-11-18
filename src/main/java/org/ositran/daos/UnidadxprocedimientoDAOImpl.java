/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.btg.ositran.siged.domain.Unidadxprocedimiento;

@Repository
public class UnidadxprocedimientoDAOImpl implements UnidadxprocedimientoDAO{
	
	private EntityManager em;

	public EntityManager getEm() {
		return em;
	}
	
	@PersistenceContext(unitName="sigedPU")
	public void setEm(EntityManager em) {
		this.em = em;
	}

	
	public List<Unidadxprocedimiento> getProcedimientosXUnidad(int idUnidad) {
		return em.createQuery("select p from Unidadxprocedimiento p where p.unidadxprocedimientoPK.idunidad = "+idUnidad).getResultList();
	}
}
