/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.btg.ositran.siged.domain.Mes;

@Repository
public class MesDAOImpl implements MesDAO {
   private static Logger log=Logger.getLogger(MesDAOImpl.class);

   @PersistenceContext(unitName="sigedPU")
   private EntityManager em ;

    public List<Mes> findAll() {
      return getEm().createNamedQuery("Mes.findAll")
             .getResultList();
   }

   ////////////////////////
    // Getters and Setters
    ////////////////////////

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }
   
}
