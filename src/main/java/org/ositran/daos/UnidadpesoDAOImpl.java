/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.btg.ositran.siged.domain.Unidadpeso;

@Repository
public class UnidadpesoDAOImpl implements UnidadpesoDAO {
   private static Logger log=Logger.getLogger(UnidadpesoDAOImpl.class);

   
   private EntityManager em ;

   public List<Unidadpeso> findAll() {
      return getEm().createNamedQuery("Unidadpeso.findAll")
             .getResultList();
   }

    ////////////////////////
    // Getters and Setters
    ////////////////////////
    
    public EntityManager getEm() {
        return em;
    }
    @PersistenceContext(unitName="sigedPU")
    public void setEm(EntityManager em) {
        this.em = em;
    }




  
   
}
