/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import com.btg.ositran.siged.domain.DocumentoReferencia;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import com.btg.ositran.siged.domain.CarpetaReferencia;

/**
 *
 * @author jbengoa
 */

@Repository
public class CarpetaReferenciaDAOImpl implements CarpetaReferenciaDAO{
    private static Logger log = LoggerFactory.getLogger(CarpetaReferenciaDAOImpl.class);
	private EntityManager em;

    
	public CarpetaReferencia saveCarpetaReferencia(CarpetaReferencia objeto){
            if (objeto.getIdCarpRef()==null){
     	       em.persist(objeto);
               em.flush();
               em.refresh(objeto);
            }else{
               em.merge(objeto);
               em.flush();
            }   
	   return objeto;
	}

	@PersistenceContext(unitName = "sigedPU")
	public void setEm(EntityManager em) {
  	   this.em = em;
	}

	public EntityManager getEm() {
	    return em;
	}
}
