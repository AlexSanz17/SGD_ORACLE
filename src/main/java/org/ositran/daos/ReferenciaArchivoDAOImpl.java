/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import com.btg.ositran.siged.domain.Documento;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.btg.ositran.siged.domain.ReferenciaArchivo;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;

/**
 *
 * @author jbengoa
 */

@Repository
public class ReferenciaArchivoDAOImpl implements ReferenciaArchivoDAO {

    private static Logger log = LoggerFactory.getLogger(ReferenciaArchivoDAOImpl.class);
    private EntityManager em;

    public ReferenciaArchivo saveReferenciaArchivo(ReferenciaArchivo objeto) {
        log.debug("-> antes ReferenciaArchivoDAOImpl saveReferenciaArchivo " + objeto.getIdArchivo() + "----" + objeto.getEstado());
        if (objeto.getIdRefArc() == null) {
            em.persist(objeto);
            em.flush();
            em.refresh(objeto);
        } else {            
            em.merge(objeto);
            em.flush();
        }
        log.debug("-> termino ReferenciaArchivoDAOImpl");
        return objeto;
    }
    public ReferenciaArchivo findReferenciaArchivo(Integer id) {
        
        return (ReferenciaArchivo)em.find(ReferenciaArchivo.class, id);
    }
    

    @PersistenceContext(unitName = "sigedPU")
    public void setEm(EntityManager em) {
        this.em = em;
    }

    public EntityManager getEm() {
        return em;
    }
}
