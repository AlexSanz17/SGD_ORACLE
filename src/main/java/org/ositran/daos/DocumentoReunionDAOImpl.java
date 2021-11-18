/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;
import java.util.List;
import com.btg.ositran.siged.domain.DocumentoReunion;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author consultor_jti15
 */
public class DocumentoReunionDAOImpl implements DocumentoReunionDAO{
     private EntityManager em;
    
    public EntityManager getEm(){
	 return em;
    }

    @PersistenceContext(unitName="sigedPU")
    public void setEm(EntityManager em){
	this.em=em;
    }
    
    public DocumentoReunion saveDocumentoReunion(DocumentoReunion objDocumentoReunion){
	if(objDocumentoReunion.getIdDocumentoReunion()==null){
	    em.persist(objDocumentoReunion); 
	 }else{
	    em.merge(objDocumentoReunion); // Actualizacion
	 }
        
	 return objDocumentoReunion;		
    }
    
     public List<DocumentoReunion> getDocumentoReunion(DocumentoReunion objDocumentoReunion){
         String sql = "SELECT f FROM DocumentoReunion f WHERE f.idDocumento =" + objDocumentoReunion.getIdDocumento() + " and f.estado = 'A' and f.tipo=:tipo";   
         return em.createQuery(sql).setParameter("tipo", objDocumentoReunion.getTipo()).getResultList();
        
     }
}
