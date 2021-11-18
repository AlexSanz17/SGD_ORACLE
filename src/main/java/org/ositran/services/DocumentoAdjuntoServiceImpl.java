/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.services;

import com.btg.ositran.siged.domain.DocumentoAdjunto;
import java.util.List;
import org.ositran.daos.DocumentoAdjuntoDAO;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author consultor_jti15
 */
public class DocumentoAdjuntoServiceImpl  implements DocumentoAdjuntoService{
    private DocumentoAdjuntoDAO documentoAdjuntoDAO;

   
    
    public List<DocumentoAdjunto> findByListDocumentoAdjunto(Integer idDocumento){
          return documentoAdjuntoDAO.findByListDocumentoAdjunto(idDocumento);
    }
    
    @Transactional(propagation = Propagation.REQUIRED)
    public DocumentoAdjunto registrarDocumentoAdjunto(DocumentoAdjunto objDocAdj){
        return documentoAdjuntoDAO.registrarDocumentoAdjunto(objDocAdj);
    }
    
     public DocumentoAdjuntoDAO getDocumentoAdjuntoDAO() {
        return documentoAdjuntoDAO;
    }

    public void setDocumentoAdjuntoDAO(DocumentoAdjuntoDAO documentoAdjuntoDAO) {
        this.documentoAdjuntoDAO = documentoAdjuntoDAO;
    }
}
