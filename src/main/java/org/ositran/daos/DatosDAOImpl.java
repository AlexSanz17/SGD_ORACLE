/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import com.btg.ositran.siged.domain.Datos;

@Repository
public class DatosDAOImpl implements DatosDAO {

   private EntityManager em;

   public Datos saveDatos(Datos Obdt){
	   
	   
	         getEm().persist(Obdt); //Nuevo
	         getEm().flush();
	         getEm().refresh(Obdt);
	   

	      return Obdt;
	   
   }
   public Datos findId(int Id){
	   
	   return getEm().find(Datos.class , Id);
	   
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
