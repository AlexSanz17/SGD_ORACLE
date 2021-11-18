/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import com.btg.ositran.siged.domain.Documento;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.btg.ositran.siged.domain.DocumentoAdjunto;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author consultor_jti15
 */
@Repository
public class DocumentoAdjuntoDAOImpl implements  DocumentoAdjuntoDAO{
    private EntityManager em;
    
    public EntityManager getEm(){
	 return em;
    }

    @PersistenceContext(unitName="sigedPU")
    public void setEm(EntityManager em){
	 this.em=em;
    }
    
     public List<DocumentoAdjunto> findByListDocumentoAdjunto(Integer idDocumento){
          try{ 
              return em.createNamedQuery("DocumentoAdjunto.findByListDocumentoAdjunto").setParameter("idDocumento",idDocumento).getResultList();
          }catch(Exception e){
              e.printStackTrace();
          }
          return null;
          }
     
     public DocumentoAdjunto registrarDocumentoAdjunto(DocumentoAdjunto objDocAdj){
		
	  if(objDocAdj.getIdDocumentoAdjunto()==null){
	    em.persist(objDocAdj); // Nuevo
            em.flush();
            em.refresh(objDocAdj);
	  }else{
	    em.merge(objDocAdj); // Actualizacion
            em.flush();
            //em.refresh(objDocAdj);
	  }
	    return objDocAdj;
	}
}
