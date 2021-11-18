/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ositran.services;

import com.btg.ositran.siged.domain.LegajoDocumento;
import com.btg.ositran.siged.domain.Usuario;
import java.util.List;

/**
 *
 * @author jbengoa
 */
public interface LegajoDocumentoService {
     public LegajoDocumento saveLegajoDocumento(LegajoDocumento legajoDocumento);
     public LegajoDocumento findLegajoDocumento(LegajoDocumento legajoDocumento);
     public List<LegajoDocumento> findDocumento(LegajoDocumento legajoDocumento, Usuario usuario);
     public List<LegajoDocumento> findAllDocumentos(LegajoDocumento legajoDocumento);
}
