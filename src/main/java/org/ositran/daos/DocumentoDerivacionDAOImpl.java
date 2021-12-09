/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import com.btg.ositran.siged.domain.DocumentoDerivacion;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jfree.util.Log;
/**
 *
 * @author consultor_jti15
 */
public class DocumentoDerivacionDAOImpl implements DocumentoDerivacionDAO{
     private EntityManager em;
     
    @PersistenceContext(unitName="sigedPU")
    public void setEm(EntityManager em){
	this.em=em;
    }
     public DocumentoDerivacion guardar(DocumentoDerivacion documentoDerivacion){
        if(documentoDerivacion.getIddocumentoderivacion() == null){
            em.persist(documentoDerivacion);
	    em.flush();
	    em.refresh(documentoDerivacion);
        }else{
	    em.merge(documentoDerivacion);
	    em.flush();
	    em.refresh(documentoDerivacion);
	}
	
        return documentoDerivacion;
     }
     
      public List<DocumentoDerivacion> getUsuarioDerivacion(DocumentoDerivacion objDocumentoDerivacion){
    	 System.out.println("(getUsuarioDerivacion): iddocumento:"+objDocumentoDerivacion.getIddocumento()+",tipo:"+objDocumentoDerivacion.getTipo());
         String sql = "SELECT f FROM DocumentoDerivacion f WHERE f.iddocumento =" + objDocumentoDerivacion.getIddocumento() + " and f.estado = 'A' and f.tipo=:tipo";   
         
         return  em.createQuery(sql).setParameter("tipo", objDocumentoDerivacion.getTipo()).getResultList();
         
     }
}
