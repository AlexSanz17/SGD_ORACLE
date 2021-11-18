/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ositran.services;

import com.btg.ositran.siged.domain.ReferenciaArchivo;
import org.ositran.daos.ReferenciaArchivoDAO;
import org.ositran.daos.ReferenciaArchivoDAOImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jbengoa
 */
public class ReferenciaArchivoServiceImpl implements ReferenciaArchivoService{
     private static Logger log = LoggerFactory.getLogger(ReferenciaArchivoDAOImpl.class);
    private ReferenciaArchivoDAO referenciaArchivoDAO;

    public ReferenciaArchivoDAO getReferenciaArchivoDAO() {
        return referenciaArchivoDAO;
    }

    public void setReferenciaArchivoDAO(ReferenciaArchivoDAO referenciaArchivoDAO) {
        this.referenciaArchivoDAO = referenciaArchivoDAO;
    }
    
    @Transactional(propagation=Propagation.REQUIRED)
    public ReferenciaArchivo saveReferenciaArchivo(ReferenciaArchivo objeto){
        return referenciaArchivoDAO.saveReferenciaArchivo(objeto);
    }
    public ReferenciaArchivo findReferenciaArchivo(Integer id){
        return referenciaArchivoDAO.findReferenciaArchivo(id);
    }

}
