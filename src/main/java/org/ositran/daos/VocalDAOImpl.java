/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.btg.ositran.siged.domain.Vocal;

@Repository
public class VocalDAOImpl implements VocalDAO {

   private static Logger _log = Logger.getLogger(VocalDAOImpl.class);
   private EntityManager em;

   //////////////////////////////////
   // Methods                      //
   //////////////////////////////////
   public Vocal findById(Integer idVocal) {
      Vocal objVocal = null;

      try {
    	  objVocal = (Vocal) em.createNamedQuery("Vocal.findById")
                                .setParameter("idVocal", idVocal)
                                .getSingleResult();
      } catch (NoResultException e) {
         _log.error(e.getMessage(), e);
      }

      return objVocal;
   }

   public List<Vocal> findAll() {
      return (List<Vocal>) em.createNamedQuery("Vocal.findAll")
                        .getResultList();
   }

   //////////////////////////////////
   // Getters and Setters          //
   //////////////////////////////////
   public EntityManager getEm() {
      return em;
   }

   @PersistenceContext(unitName = "sigedPU")
   public void setEm(EntityManager em) {
      this.em = em;
   }
}
