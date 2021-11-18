/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.btg.ositran.siged.domain.Expedientexprocincumplido;

@Repository
public class ExpedientexprocincumplidoDAOImpl implements ExpedientexprocincumplidoDAO{
	
	 private EntityManager em;

         private static Logger log  =  Logger.getLogger(ExpedientexprocincumplidoDAOImpl.class);

	public EntityManager getEm() {
		return em;
	}
	
	@PersistenceContext(unitName="sigedPU")
	public void setEm(EntityManager em) {
		this.em = em;
	}

	
	public void saveObject(Expedientexprocincumplido expedientexprocincumplido) {
		try {
			em.persist(expedientexprocincumplido);
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
		}
	}
}