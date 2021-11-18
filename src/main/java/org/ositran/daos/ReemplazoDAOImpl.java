/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.btg.ositran.siged.domain.Reemplazo;

@Repository
public class ReemplazoDAOImpl implements ReemplazoDAO {

	private EntityManager em;
	
	private static Log log=LogFactory.getLog(ReemplazoDAOImpl.class);
	
	@Override
	public List<Reemplazo> findAll() {
		return getEm().createNamedQuery("Reemplazo.findAll")
        .getResultList();
		
	}
	
	public List<Reemplazo> findByIdreemplazadoIdproceso(Integer idproceso, Integer idusuario) {
		return getEm().createNamedQuery("Reemplazo.findByIdreemplazadoIdproceso")
		.setParameter("idproceso", idproceso)
		.setParameter("idreemplazado",idusuario)
        .getResultList();
		
	}
	
	public Reemplazo findByUsuario(Integer idusuario) {
		Reemplazo remplazoTemp=null;
		try
		{
			remplazoTemp=(Reemplazo)getEm().createNamedQuery("Reemplazo.findByUsuario")
												 .setParameter("idusuario",idusuario)
												 .getSingleResult();
	      } catch (NoResultException nre) { }

		return remplazoTemp;
		
	}
	
	
	public int deleteByIdreemplazado(Integer idusuario){
		Query query = em.createQuery("delete from Reemplazo r "
                + "where  r.idreemplazado = :idreemplazado");
                query
        		.setParameter("idreemplazado",idusuario);
		int deleted = query.executeUpdate();

		return deleted;
	}


	@Override
	public Reemplazo findByIdReemplazo(Integer idreemplazo) {
		return getEm().find(Reemplazo.class, idreemplazo);
		
	}
	

	

	@Override
	public Reemplazo saveReemplazo(Reemplazo objReemplazo) {
		if (objReemplazo.getIdreemplazo() == null) {
	         getEm().persist(objReemplazo);
	         getEm().flush();
	         getEm().refresh(objReemplazo);
	      } else {
	         getEm().merge(objReemplazo);
	         getEm().flush();
	      }

	      return objReemplazo;
	}

	public EntityManager getEm() {
		return em;
	}

	@PersistenceContext(unitName="sigedPU")
	public void setEm(EntityManager em) {
		this.em = em;
	}

	@Override
	public Reemplazo encontrarReemplazo(int proceso,int reemplazante,int reemplazado){
		Reemplazo reemplazo=null;
		Query q=em.createNamedQuery("Reemplazo.findByReemplazo");
		q.setParameter("proceso",proceso);
		q.setParameter("reemplazado",reemplazado);
		q.setParameter("reemplazante",reemplazante);
		try{
			reemplazo=(Reemplazo) q.getSingleResult();
		}
		catch(NoResultException e){
			log.warn("No se encontro el Reemplazo buscado.");
		}
		return reemplazo;
	}	

   public List<Reemplazo> findLstBy(Character cEstado) {
      return em.createNamedQuery("Reemplazo.findByEstadoCustom")
               .setParameter("estado", cEstado)
               .getResultList();
   }
}
