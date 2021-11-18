/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import com.btg.ositran.siged.domain.Usuario;
import com.btg.ositran.siged.domain.UsuarioDerivacion;
import com.btg.ositran.siged.domain.Usuarioxunidadxfuncion;
import java.util.List;
import java.util.Map;

/**
 *
 * @author consultor_jti15
 */
public interface UsuarioxunidadxfuncionDAO {
    public List<Usuarioxunidadxfuncion> getUsuarioByUnidadByFuncion(Usuario usuario);
    public List<Map<String, String>> getAllUsuarioUnidadFuncion(Integer idusuario, String idjefe, Integer idfuncion);
    public List<Map<String, String>> getAllUsuario(Integer idusuario, String idjefe, Integer idfuncion);
    public List<Usuarioxunidadxfuncion> getUsuarioByUnidadByFuncionRol(Usuario usuario);
    public List<Usuarioxunidadxfuncion> getUsuarioByUnidadByFuncionDelegado(Usuario usuario);
    public List<Usuarioxunidadxfuncion> getUsuarioByUnidadByFuncionListRol(Usuario usuario);
    public List<UsuarioDerivacion> getUsuarioDerivacion();
}
