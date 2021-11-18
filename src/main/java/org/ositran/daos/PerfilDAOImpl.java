/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import com.btg.ositran.siged.domain.Perfil;

@Repository
public class PerfilDAOImpl implements PerfilDAO {

   private EntityManager em;

   //////////////////////////////////
   // Methods                      //
   //////////////////////////////////
   public List<Perfil> findAll() {
      return getEm().createNamedQuery("Perfil.findAll")
             .getResultList();
   }

   public Perfil findByIdPerfil(Integer iIdPerfil) {
      return getEm().find(Perfil.class, iIdPerfil);
   }

   public Perfil savePerfil(Perfil objPerfil) {
      if (objPerfil.getIdperfil() == null) {
         getEm().persist(objPerfil);
         getEm().flush();
         getEm().refresh(objPerfil);
      } else {
         getEm().merge(objPerfil);
         getEm().flush();
      }

      return objPerfil;
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
