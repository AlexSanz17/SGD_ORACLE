/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.btg.ositran.siged.domain.GridColumna;

@Repository
public class GridColumnaDaoImpl implements GridColumnaDao{

	
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<GridColumna> getTodos() {
		return em.createNamedQuery("GridColumna.findAll").getResultList();
	}

	@PersistenceContext(unitName="sigedPU")
	public void setEm(EntityManager em) {
		this.em = em;
	}

}
