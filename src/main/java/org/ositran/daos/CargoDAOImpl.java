/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import com.btg.ositran.siged.domain.Cargo;

@Repository
public class CargoDAOImpl implements CargoDAO {

   private EntityManager em;

   public Cargo saveDatos(Cargo Obdt){
	   
	   
	         getEm().persist(Obdt); //Nuevo
	         getEm().flush();
	         getEm().refresh(Obdt);
	   

	      return Obdt;
	   
   }
   
   public Cargo findkey(int Id){
	   return getEm().find(Cargo.class, Id);
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
