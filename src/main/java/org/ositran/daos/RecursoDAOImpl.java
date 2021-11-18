/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.ositran.utils.Constantes;
import org.springframework.stereotype.Repository;

import com.btg.ositran.siged.domain.Recurso;

@Repository
public class RecursoDAOImpl implements RecursoDAO {

   private EntityManager em;

   //////////////////////////////////
   // Methods                      //
   //////////////////////////////////
   public List<Recurso> findAll() {
      return getEm().createNamedQuery("Recurso.findAll")
             .getResultList();
   }

   @Override
   public List<Recurso> buscarRecursosActivos() {
	   return getEm().createNamedQuery("Recurso.findRecusosActivos").setParameter("estado", Constantes.ESTADO_ACTIVO)
       .getResultList();
   }
   
   public Recurso findByIdRecurso(Integer iIdRecurso) {
      return getEm().find(Recurso.class, iIdRecurso);
   }

   public Recurso saveRecurso(Recurso objRecurso) {
      if (objRecurso.getIdrecurso() == null) {
         getEm().persist(objRecurso);
         getEm().flush();
         getEm().refresh(objRecurso);
      } else {
         getEm().merge(objRecurso);
         getEm().flush();
      }

      return objRecurso;
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
