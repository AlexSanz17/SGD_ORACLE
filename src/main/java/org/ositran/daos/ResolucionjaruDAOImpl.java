/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import com.btg.ositran.siged.domain.Resolucionjaru;

@Repository
public class ResolucionjaruDAOImpl implements ResolucionjaruDAO {
   //Atrinutos
   private EntityManager entityManager;
   
   //Metodos
   public Resolucionjaru saveOrUpdateResolucionJaru(Resolucionjaru resolucionjaru){
      if(resolucionjaru.getIdresolucion()==null){
         entityManager.persist(resolucionjaru);
         entityManager.flush();
         entityManager.refresh(resolucionjaru);
      }else{
         entityManager.merge(resolucionjaru);
         entityManager.flush();
      }      
      return resolucionjaru;
   }

   public Resolucionjaru findByIdExpedienteStor(Integer idExpediente) throws NoResultException{
	   return (Resolucionjaru) entityManager.createNamedQuery("Resolucionjaru.findByIdExpedienteStor")
	   			.setParameter("idexpediente", idExpediente)
	   			.getSingleResult();
   }

   //Getters and Setters
   public EntityManager getEntityManager() {
      return entityManager;
   }

   @PersistenceContext(unitName="sigedPU")
   public void setEntityManager(EntityManager entityManager) {
      this.entityManager = entityManager;
   }


}
