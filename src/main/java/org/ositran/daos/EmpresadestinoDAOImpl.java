/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import com.btg.ositran.siged.domain.Empresadestino;

@Repository
public class EmpresadestinoDAOImpl implements EmpresadestinoDAO {

   private EntityManager em;

   public Empresadestino findCod(String codigo){
	  
	   return (Empresadestino)getEm().createNamedQuery("Empresadestino.findByCodigo")
       .setParameter("codigo", codigo)
       .getSingleResult();
	   
   }
   
   public List<Empresadestino> findAll() {
		List<Empresadestino> emd=getEm().createNamedQuery("Empresadestino.findAll")
       					.getResultList();
		return emd;
	}
   public Empresadestino findkey(int Id){
		
		 return getEm().find(Empresadestino.class, Id);
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
