/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ositran.services;

import com.btg.ositran.siged.domain.TipoInstitucion;
import java.util.List;
import org.ositran.daos.TipoInstitucionDAO;

/**
 *
 * @author consultor_jti15
 */
public class TipoInstitucionServiceImpl implements TipoInstitucionService{
    private TipoInstitucionDAO tipoInstitucionDAO;
    
    public List<TipoInstitucion> findTipoInstitucion(String tipoPersonaJuridica){
    	return tipoInstitucionDAO.findTipoInstitucion(tipoPersonaJuridica);
    }

    public TipoInstitucionDAO getTipoInstitucionDAO() {
        return tipoInstitucionDAO;
    }

    public void setTipoInstitucionDAO(TipoInstitucionDAO tipoInstitucionDAO) {
        this.tipoInstitucionDAO = tipoInstitucionDAO;
    }
     
    
}
