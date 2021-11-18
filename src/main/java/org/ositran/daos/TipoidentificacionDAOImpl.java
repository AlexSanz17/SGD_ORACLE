/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import com.btg.ositran.siged.domain.Tipoidentificacion;

@Repository
public class TipoidentificacionDAOImpl implements TipoidentificacionDAO {

   private EntityManager em;

   //////////////////////////////////
   // Methods                      //
   //////////////////////////////////
   public List<Tipoidentificacion> findAll() {
      return em.createNamedQuery("Tipoidentificacion.findAll")
               .getResultList();
   }

   public Tipoidentificacion buscarObjPor(String sNombre) {
      return (Tipoidentificacion) em.createNamedQuery("Tipoidentificacion.findByNombre")
                                    .setParameter("nombre", sNombre)
                                    .getSingleResult();
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
