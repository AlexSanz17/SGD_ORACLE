/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ositran.services;

import com.btg.ositran.siged.domain.FirmaArchivo;
import com.btg.ositran.siged.domain.Usuario;
import java.util.List;
import org.ositran.daos.FirmaArchivoDAO;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author consultor_jti15
 */
public class FirmaArchivoServiceImpl implements FirmaArchivoService{
    private FirmaArchivoDAO firmaArchivoDAO;

    public FirmaArchivoDAO getFirmaArchivoDAO() {
        return firmaArchivoDAO;
    }

    public void setFirmaArchivoDAO(FirmaArchivoDAO firmaArchivoDAO) {
        this.firmaArchivoDAO = firmaArchivoDAO;
    }
    
    @Transactional
    public void saveFirmaArchivo(FirmaArchivo firmaArchivo){
        firmaArchivoDAO.saveFirmaArchivo(firmaArchivo);
    }
    
    public List<Usuario> findUltimaFirma(Integer idDocumento , String tipo){
        return firmaArchivoDAO.findUltimaFirma(idDocumento, tipo);
    }
}
