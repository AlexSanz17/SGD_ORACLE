/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import com.btg.ositran.siged.domain.FirmaArchivo;
import com.btg.ositran.siged.domain.Usuario;
import java.util.List;

/**
 *
 * @author consultor_jti15
 */
public interface FirmaArchivoDAO {
    public void saveFirmaArchivo(FirmaArchivo firmaArchivo);
    public List<FirmaArchivo> findFirmas(Integer idDocumento , Usuario usuario);
    public List<FirmaArchivo> findFirmado(Integer idArchivo);
    public List<FirmaArchivo> findFirmadoUsuario(Integer idUsuario);
    public List<Usuario> findUltimaFirma(Integer idDocumento , String tipo);
}
