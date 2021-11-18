/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import com.btg.ositran.siged.domain.Tipoproceso;

@Repository
public class TipoprocesoDAOImpl implements TipoprocesoDAO {

   private EntityManager em;

   //////////////////////////////////
   // Methods                      //
   //////////////////////////////////
   public List<Tipoproceso> findAll() {
      return getEm().createNamedQuery("Tipoproceso.findAll")
             .getResultList();
   }

   public Tipoproceso buscarObjPor(String sNombre) {
      return (Tipoproceso) em.createNamedQuery("Tipoproceso.findByNombre")
                             .setParameter("nombre", sNombre)
                             .getSingleResult();
   }

   public Tipoproceso findById(Integer iIdTipoProceso) {
      return em.find(Tipoproceso.class, iIdTipoProceso);
   }

   //////////////////////////////////
   // Getters and Setters          //
   //////////////////////////////////
   public EntityManager getEm() {
      return em;
   }

   @PersistenceContext(unitName="sigedPU")
   public void setEm(EntityManager em) {
      this.em = em;
   }
}
