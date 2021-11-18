/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.btg.ositran.siged.domain.Carpeta;

@Repository
public class CarpetaDAOImpl implements CarpetaDAO {
	
	  private EntityManager em;
	  private static Logger log = Logger.getLogger(CarpetaDAOImpl.class) ;

	  
	   public List<Carpeta> findbyDocumento (Integer IdDocumento ) {
		   
		   Query query =   getEm().createNamedQuery("Carpeta.findByIdDocumento") ;
		   query.setParameter("idDocumento", IdDocumento);
		   List<Carpeta> lista = query.getResultList();     
		   log.debug(" CarpetaDAOImpl.findbyDocumento list.size :  "+lista.size()) ; 
		   return lista ;
		     
	   }
	   
	   public Carpeta findByIdcarpeta (Integer IdCarpeta) {
		    
		   Query query =   getEm().createNamedQuery("Carpeta.findByIdcarpeta") ;
		   query.setParameter("idcarpeta", IdCarpeta) ; 
		   return  (Carpeta)query.getSingleResult(); 
	   }

	   
	   public Carpeta saveCarpeta(Carpeta carpeta) {
		      if (carpeta.getIdcarpeta() == null) {
		         getEm().persist(carpeta); //Nuevo
		         getEm().flush();
		         getEm().refresh(carpeta);
		      } else {
		         getEm().merge(carpeta); //Actualizacion
		         getEm().flush();
		      } 
		      return carpeta;
		   }
	   
	   public void deleteCarpeta(Carpeta carpeta) {
		       carpeta = this.findByIdcarpeta(carpeta.getIdcarpeta()) ; 
		         getEm().remove(carpeta); 
		         getEm().flush();   
		   }
	   //////////////////////////////////
	   // Getters and Setters          //
	   //////////////////////////////////
	   public EntityManager getEm() {
	      return em;
	   }

	   @PersistenceContext(unitName="sigedPU")
	   public void setEm(EntityManager em) {
	      this.em = em;
	   }
}
