/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import com.btg.ositran.siged.domain.Envio;

@Repository
public class EnvioDAOImpl implements EnvioDAO {

   private EntityManager em;

   public Envio saveDatos(Envio Obdt){
	   
	   
	         getEm().persist(Obdt); //Nuevo
	         getEm().flush();
	         getEm().refresh(Obdt);
	   

	      return Obdt;
	   
   }
   public Envio findkey(int Id){
	   return getEm().find(Envio.class, Id);
   }
   
   public List<Envio> findall(){
	   
	   return getEm().createNamedQuery("Envio.findAll")
       .getResultList();
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
