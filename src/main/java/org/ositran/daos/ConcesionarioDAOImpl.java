/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import com.btg.ositran.siged.domain.Concesionario;

@Repository
public class ConcesionarioDAOImpl implements ConcesionarioDAO {

   private EntityManager em;

   public Concesionario findByIdConcesionario(Integer iId) {
      return getEm().find(Concesionario.class, iId);
   }

   public Concesionario findByRUC(String strRUC) {
      return (Concesionario) getEm()
             .createNamedQuery("Concesionario.findByRuc")
             .setParameter("ruc", strRUC)
             .getSingleResult();
   }

   public List<Concesionario> findAll() {
      return getEm().createNamedQuery("Concesionario.findAll")
             .getResultList();
   }

   public EntityManager getEm() {
      return em;
   }

   @PersistenceContext(unitName="sigedPU")
   public void setEm(EntityManager em) {
      this.em = em;
   }
}
