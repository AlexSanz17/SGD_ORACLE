/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.btg.ositran.siged.domain.Accion;

@Repository
public class AccionDAOImpl implements AccionDAO {

   private static Logger _log = Logger.getLogger(AccionDAOImpl.class);
   private EntityManager em;

   //////////////////////////////////
   // Methods                      //
   //////////////////////////////////
   public Accion buscarObjPor(String sNombre) {
      Accion objAccion = null;

      try {
         objAccion = (Accion) em.createNamedQuery("Accion.findByNombre")
                                .setParameter("nombre", sNombre)
                                .getSingleResult();
      } catch (NoResultException e) {
         _log.error(e.getMessage(), e);
      }

      return objAccion;
   }

   public Accion findByNombre(String strA) {
	   _log.debug("Accion ["+strA+"]");
      return (Accion) em.createNamedQuery("Accion.findByNombre")
                        .setParameter("nombre", strA.toLowerCase())
                        .getSingleResult();
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
