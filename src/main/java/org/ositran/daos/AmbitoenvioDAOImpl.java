/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.btg.ositran.siged.domain.AmbitoEnvio;
import com.btg.ositran.siged.domain.Tipoenvio;

@Repository
public class AmbitoenvioDAOImpl implements AmbitoenvioDAO {

	private EntityManager em;

	public AmbitoEnvio findId(Integer id) {

		AmbitoEnvio data = (AmbitoEnvio) getEm()
									.createNamedQuery("AmbitoEnvio.findByIdambitoenvio")
									.setParameter("idambitoenvio", id)
									.getSingleResult();
		return data;

	}
	
	public List<AmbitoEnvio> findAll() {
		List<AmbitoEnvio> est=getEm().createNamedQuery("AmbitoEnvio.findAll")
							 .getResultList();
		return est;
	}
	
	public Tipoenvio findkey(int Id){
		
		 return getEm().find(Tipoenvio.class, Id);
	}
	
	
	 //////////////////////
	// Getter and Setter//
   //////////////////////
	
	
	private EntityManager getEm() {
		return em;
	}

	@PersistenceContext(unitName="sigedPU")
	private void setEm(EntityManager em) {
		this.em = em;
	}

}
