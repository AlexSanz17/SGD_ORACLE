/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import com.btg.ositran.siged.domain.Tiporesultado;

/**
 *
 * @author Usuario
 */
@Repository
public class TiporesultadoDAOImpl implements TiporesultadoDAO{
   private EntityManager entityManager;

   public Tiporesultado findByIdTiporesultado(Integer idTiporesultado) {
      return entityManager.find(Tiporesultado.class, idTiporesultado);
   }

   public List<Tiporesultado> findAll() {
      return (List<Tiporesultado>)entityManager.createNamedQuery("Tiporesultado.findAll")
            .getResultList();
   }

   public EntityManager getEntityManager() {
      return entityManager;
   }

   @PersistenceContext(unitName="sigedPU")
   public void setEntityManager(EntityManager entityManager) {
      this.entityManager = entityManager;
   }


}
