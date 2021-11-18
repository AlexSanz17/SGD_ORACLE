/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import com.btg.ositran.siged.domain.LegajoDocumento;
import com.btg.ositran.siged.domain.Usuario;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author jbengoa
 */
public interface LegajoDocumentoDAO {
    public LegajoDocumento saveLegajoDocumento(LegajoDocumento legajoDocumento);
    public LegajoDocumento findLegajoDocumento(LegajoDocumento legajoDocumento);
    public List<LegajoDocumento> findDocumento(LegajoDocumento legajoDocumento, Usuario usuario);
    public List<LegajoDocumento> findAllDocumentos(LegajoDocumento legajoDocumento);
}
