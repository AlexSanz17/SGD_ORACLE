/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ositran.services;

import com.btg.ositran.siged.domain.Usuario;
import com.btg.ositran.siged.domain.UsuarioDerivacion;
import com.btg.ositran.siged.domain.Usuarioxunidadxfuncion;
import java.util.List;
import java.util.Map;
import org.ositran.daos.UsuarioxunidadxfuncionDAO;

/**
 *
 * @author consultor_jti15
 */
public class UsuarioxunidadxfuncionServiceImpl implements UsuarioxunidadxfuncionService{
    private UsuarioxunidadxfuncionDAO usuarioxunidadxfuncionDAO;

    @Override
    public List<Usuarioxunidadxfuncion> getUsuarioByUnidadByFuncion(Usuario usuario){
    	return usuarioxunidadxfuncionDAO.getUsuarioByUnidadByFuncion(usuario);
    }
    
    public List<UsuarioDerivacion> getUsuarioDerivacion(){
        return usuarioxunidadxfuncionDAO.getUsuarioDerivacion();
    }
    
    public List<Map<String, String>>  getAllUsuarioUnidadFuncion(Integer idunidad, String idjefe, Integer idfuncion){
        return usuarioxunidadxfuncionDAO.getAllUsuarioUnidadFuncion(idunidad, idjefe, idfuncion);
    }
    
    public List<Map<String, String>>  getAllUsuario(Integer idunidad, String idjefe, Integer idfuncion){
        return usuarioxunidadxfuncionDAO.getAllUsuario(idunidad, idjefe, idfuncion);
    }
     
    public UsuarioxunidadxfuncionDAO getUsuarioxunidadxfuncionDAO() {
        return usuarioxunidadxfuncionDAO;
    }

    public void setUsuarioxunidadxfuncionDAO(UsuarioxunidadxfuncionDAO usuarioxunidadxfuncionDAO) {
        this.usuarioxunidadxfuncionDAO = usuarioxunidadxfuncionDAO;
    }
    
    
}
