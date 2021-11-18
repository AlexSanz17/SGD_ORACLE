/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ositran.services;

import com.btg.ositran.siged.domain.FirmaArchivo;
import com.btg.ositran.siged.domain.Usuario;
import java.util.List;

/**
 *
 * @author consultor_jti15
 */
public interface FirmaArchivoService {
    public void saveFirmaArchivo(FirmaArchivo firmaArchivo);
    public List<Usuario> findUltimaFirma(Integer idDocumento , String tipo);
}
