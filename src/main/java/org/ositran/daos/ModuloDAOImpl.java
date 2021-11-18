/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.ositran.utils.Constantes;
import org.springframework.stereotype.Repository;

import com.btg.ositran.siged.domain.Modulo;

@Repository
public class ModuloDAOImpl implements ModuloDAO {

   private EntityManager em;

   //////////////////////////////////
   // Methods                      //
   //////////////////////////////////
   public List<Modulo> findAll() {
      return getEm().createNamedQuery("Modulo.findAll")
             .getResultList();
   }
   
   public List<Modulo> findModuloActivos(){
	   return getEm().createNamedQuery("Modulo.findModulosActivos").setParameter("estado",Constantes.ESTADO_ACTIVO)
       .getResultList();
   }

   public Modulo findByIdModulo(Integer iIdModulo) {
      return getEm().find(Modulo.class, iIdModulo);
   }

   public Modulo saveModulo(Modulo objModulo) {
      if (objModulo.getIdmodulo() == null) {
         getEm().persist(objModulo); //Nuevo
         getEm().flush();
         getEm().refresh(objModulo);
      } else {
         getEm().merge(objModulo); //Actualizacion
         getEm().flush();
      }

      return objModulo;
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
