/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import com.btg.ositran.siged.domain.Tipodocumento;

@Repository
public class TipodocumentoDAOImpl implements TipodocumentoDAO {

   private static Logger _log = Logger.getLogger(TipodocumentoDAOImpl.class);
   private EntityManager em;

   //////////////////////////////////
   // Methods                      //
   //////////////////////////////////
   
   public List<Tipodocumento> findAllActive(){
       return em.createNamedQuery("Tipodocumento.findAllActive")
               .getResultList();
   }
   
   public List<Tipodocumento> findByAllTipoDocumentoPIDE(){
       return em.createNamedQuery("Tipodocumento.findByAllTipoDocumentoPIDE")
               .getResultList();
   }

   public List<Tipodocumento> findAllGrupoFedatario(Integer grupoPerfil, Integer grupoDocumento){

	   StringBuilder sql = new StringBuilder("select t.* from Tipodocumento t where t.estado = 'A' AND t.idtipodocumento IN (SELECT IDTIPODOCUMENTO FROM perfilxgrupoxdocumento WHERE ESTADO = 'A' AND IDPERFILGRUPODOCUMENTO = :grupoPerfil AND GRUPODOCUMENTO = :grupoDocumento)");
	   Query q = em.createNativeQuery(sql.toString(), Tipodocumento.class);
	   q.setParameter("grupoPerfil", grupoPerfil);
	   q.setParameter("grupoDocumento", grupoDocumento);
	   return q.getResultList();
   }

   public List<Tipodocumento> findAll() {
      return em.createNamedQuery("Tipodocumento.findAll")
               .getResultList();
   }

   public List<Tipodocumento> findAllWithoutStor() {
      return em.createNamedQuery("Tipodocumento.findAllWithoutStor")
               .getResultList();
   }

   public List<Tipodocumento> findAllwithPlantilla() {
      return em.createNamedQuery("Tipodocumento.findAllWithPlantilla")
               .getResultList();
   }

   public List<Tipodocumento> findAllWithoutPlantilla() {
      return em.createNamedQuery("Tipodocumento.findAllWithoutPlantilla")
               .getResultList();
   }

   public Tipodocumento buscarObjPor(String sCodigo) {
      return (Tipodocumento) em.createNamedQuery("Tipodocumento.findByCodigo")
                               .setParameter("codigo", sCodigo.toUpperCase())
                               .getSingleResult();
   }
   
    public Tipodocumento findByIdTipoDocumentoPIDE(String iIdTipodocumentoPIDE){
         return (Tipodocumento) em.createNamedQuery("Tipodocumento.findByTipoDocumentoPIDE")
                               .setParameter("tipoPIDE", iIdTipodocumentoPIDE)
                               .getSingleResult();
    }

   public Tipodocumento findByIdTipoDocumento(Integer iIdTipodocumento) {
      Tipodocumento objTipodocumento = null;

      try {
         objTipodocumento = em.find(Tipodocumento.class, iIdTipodocumento);
         em.refresh(objTipodocumento);
      } catch (EntityNotFoundException e) {
         _log.error(e.getMessage(), e);
      }

      return objTipodocumento;
   }

   public Tipodocumento findByNombre(String sNombre) {
      return (Tipodocumento) em.createNamedQuery("Tipodocumento.findByNombre")
                               .setParameter("nombre", sNombre.toLowerCase())
                               .getSingleResult();
   }

   @SuppressWarnings("unchecked")
   public List<Tipodocumento> findByNombreLike(String like){
	   if(like != null){
		   like = "%"+like+"%";
	   }
	   return em.createNamedQuery("Tipodocumento.findByNombreLike").setParameter("nombre", like.toLowerCase()).getResultList();
   }

   public Tipodocumento guardarObj(Tipodocumento objTipodocumento) {
      if (objTipodocumento.getIdtipodocumento() == null) {
         em.persist(objTipodocumento);
         em.flush();
         em.refresh(objTipodocumento);
      } else {
         em.merge(objTipodocumento);
         em.flush();
      }

      return objTipodocumento;
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
