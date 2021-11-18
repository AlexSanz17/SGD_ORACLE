/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import com.btg.ositran.siged.domain.FilaBandejaFirmar;
import com.btg.ositran.siged.domain.SeguimientoXFirma;
import com.btg.ositran.siged.domain.Usuario;
import java.util.List;

/**
 *
 * @author consultor_jti15
 */
public interface SeguimientoXFirmaDAO {
     public List<FilaBandejaFirmar> buscarSeguimientoXFirmaXUsuario(Usuario objUsuario); 
     public SeguimientoXFirma guardarSeguimiento(SeguimientoXFirma seguimiento);
     public SeguimientoXFirma findByIdDocumentoFirmado(Integer idDocumentoFirmado);
     public List<SeguimientoXFirma> findByIdDocumento(Integer idDocumento, Usuario usuario);
}
