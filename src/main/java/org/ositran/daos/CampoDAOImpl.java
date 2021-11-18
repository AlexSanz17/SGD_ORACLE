/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import com.btg.ositran.siged.domain.Campo;

@Repository
public class CampoDAOImpl implements CampoDAO{
	
	//private Logger log=Logger.getLogger(this.getClass());
	private EntityManager em;

	@SuppressWarnings("unchecked")
	public List<Campo> findAll(){
		return getEm().createNamedQuery("Campo.findAll").getResultList();
	}

   public Campo guardarObj(Campo objCampo) {
      if (objCampo.getIdCampo() == null) {
         em.persist(objCampo);
         em.flush();
         em.refresh(objCampo);
      } else {
         em.merge(objCampo);
         em.flush();
      }

      return objCampo;
   }

	// //////////////////////
	// Getters and Setters
	// //////////////////////
	public EntityManager getEm(){
		return em;
	}

	@PersistenceContext(unitName="sigedPU")
	public void setEm(EntityManager em){
		this.em=em;
	}
}
