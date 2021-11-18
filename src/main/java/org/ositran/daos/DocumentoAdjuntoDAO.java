/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;
import java.util.List;

import com.btg.ositran.siged.domain.DocumentoAdjunto;
/**
 *
 * @author consultor_jti15
 */
public interface DocumentoAdjuntoDAO {
     public List<DocumentoAdjunto> findByListDocumentoAdjunto(Integer idDocumento);
     public DocumentoAdjunto registrarDocumentoAdjunto(DocumentoAdjunto objDocAdj);
}
