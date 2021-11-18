/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.btg.ositran.siged.domain.Submotivo;

@Repository
public class SubmotivoDAOImpl implements SubmotivoDAO {

   private EntityManager em;

   public Submotivo findByIdSubmotivo(Integer iIdSubmotivo) {
      return (Submotivo) getEm().find(Submotivo.class, iIdSubmotivo);
   }

   public List<Submotivo> findByMotivo(Integer iIdMotivo) {
      return getEm().createNamedQuery("Submotivo.findByMotivo")
             .setParameter("idmotivo", iIdMotivo)
             .getResultList();
   }

   public Submotivo saveMotivo(Submotivo submotivo) {
	      if (submotivo.getMotivo() == null) {
	         getEm().persist(submotivo); //Nuevo
	         getEm().flush();
	         getEm().refresh(submotivo);
	      } else {
	         getEm().merge(submotivo); //Actualizacion
	         getEm().flush();
	      }
	   return submotivo;
}   
   
   public EntityManager getEm() {
      return em;
   }

   @PersistenceContext(unitName="sigedPU")
   public void setEm(EntityManager em) {
      this.em = em;
   }
}
