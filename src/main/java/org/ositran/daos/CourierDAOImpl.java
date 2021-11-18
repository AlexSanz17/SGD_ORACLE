/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import com.btg.ositran.siged.domain.Courier;

@Repository
public class CourierDAOImpl implements CourierDAO {

   private EntityManager em;

   public Courier findCod(String codigo){
	  
	   return (Courier)getEm().createNamedQuery("Courier.findByCodigo")
       .setParameter("codigo", codigo)
       .getSingleResult();
	   
   }
   
   public List<Courier> findAll() {
		List<Courier> emd=getEm().createNamedQuery("Courier.findAll")
       					.getResultList();
		return emd;
	}
   public Courier findkey(int Id){
		
		 return getEm().find(Courier.class, Id);
	}
   
     ////////////////////
    //Getter and Setter/
   ////////////////////
   
   private EntityManager getEm() {
	   return em;
   }
   @PersistenceContext(unitName="sigedPU")
   private void setEm(EntityManager em) {
	   this.em = em;
   }
   
}
