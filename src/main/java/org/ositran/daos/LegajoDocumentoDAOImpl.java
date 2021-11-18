/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import com.btg.ositran.siged.domain.LegajoDocumento;
import com.btg.ositran.siged.domain.Usuario;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import java.util.List;
/**
 *
 * @author jbengoa
 */

@Repository
public class LegajoDocumentoDAOImpl implements LegajoDocumentoDAO{
    private EntityManager em;
    
    @PersistenceContext(unitName = "sigedPU")
    public void setEm(EntityManager em) {
	this.em = em;
   }
    
   public LegajoDocumento findLegajoDocumento(LegajoDocumento legajoDocumento) {
        try {
             return (LegajoDocumento) em
		.createNamedQuery("LegajoDocumento.findLegajoDocumento")
		.setParameter("idLegajo", legajoDocumento.getIdLegajo()).setParameter("idDocumento", legajoDocumento.getIdDocumento()).getSingleResult();
         } catch (NoResultException e) {
             return null;
         } 
   } 
   
   public List<LegajoDocumento> findAllDocumentos(LegajoDocumento legajoDocumento){
          try{
                return   em
		.createNamedQuery("LegajoDocumento.findAllDocumentos")
		.setParameter("idLegajo", legajoDocumento.getIdLegajo()).getResultList();
          }catch(NoResultException e) {
             return null;
         } 
   }
   
    public List<LegajoDocumento> findDocumento(LegajoDocumento legajoDocumento, Usuario usuario) {
        try {
             return em
		.createNamedQuery("LegajoDocumento.findDocumento")
		.setParameter("idDocumento", legajoDocumento.getIdDocumento()).setParameter("idUnidad", usuario.getIdUnidadPerfil()).getResultList();
         } catch (NoResultException e) {
             return null;
         } 
   } 
   
   public LegajoDocumento saveLegajoDocumento(LegajoDocumento legajoDocumento) {
            if(legajoDocumento.getIdLegajoDocumento()==null){
                em.persist(legajoDocumento);
                em.flush();
                em.refresh(legajoDocumento);
            }else{
                em.merge(legajoDocumento);
                em.flush();
                em.refresh(legajoDocumento);
            }
      
            return legajoDocumento;
    } 
}
