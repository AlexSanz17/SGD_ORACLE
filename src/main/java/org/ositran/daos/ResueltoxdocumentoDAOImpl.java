/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.btg.ositran.siged.domain.Resueltoxdocumento;

@Repository
public class ResueltoxdocumentoDAOImpl implements ResueltoxdocumentoDAO{
	
	private EntityManager em;

	public EntityManager getEm() {
		return em;
	}
	
	@PersistenceContext(unitName="sigedPU")
	public void setEm(EntityManager em) {
		this.em = em;
	}

	public void saveObject(Resueltoxdocumento resueltoxdocumento) {
		em.persist(resueltoxdocumento);
	}
}