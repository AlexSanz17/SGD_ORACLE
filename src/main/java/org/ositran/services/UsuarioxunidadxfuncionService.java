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
/**
 *
 * @author consultor_jti15
 */
public interface UsuarioxunidadxfuncionService {
     public List<Usuarioxunidadxfuncion> getUsuarioByUnidadByFuncion(Usuario usuario);
     public List<Map<String, String>>  getAllUsuarioUnidadFuncion(Integer idunidad, String idjefe, Integer idfuncion);
     public List<UsuarioDerivacion> getUsuarioDerivacion();
     public List<Map<String, String>>  getAllUsuario(Integer idunidad, String idjefe, Integer idfuncion);
}
