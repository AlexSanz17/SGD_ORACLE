/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import com.btg.ositran.siged.domain.Sala;

@Repository
public class SalaDAOImpl implements SalaDAO {

   private EntityManager em;

   public List<Sala> findAll() {
      return getEm().createNamedQuery("Sala.findAll")
             .getResultList();
   }

   public Sala findByIdSala(Integer iIdSala) {
      return getEm().find(Sala.class, iIdSala);
   }

   public EntityManager getEm() {
      return em;
   }

   @PersistenceContext(unitName="sigedPU")
   public void setEm(EntityManager em) {
      this.em = em;
   }
}
