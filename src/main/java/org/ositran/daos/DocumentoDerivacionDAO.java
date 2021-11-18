/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import com.btg.ositran.siged.domain.DocumentoDerivacion;
import java.util.List;

/**
 *
 * @author consultor_jti15
 */
public interface DocumentoDerivacionDAO {
    public DocumentoDerivacion guardar(DocumentoDerivacion documentoDerivacion);
    public List<DocumentoDerivacion> getUsuarioDerivacion(DocumentoDerivacion objDocumentoDerivacion);
}
