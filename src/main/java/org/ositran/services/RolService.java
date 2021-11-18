package org.ositran.services;

import java.util.List;
import java.util.Map;

import com.btg.ositran.siged.domain.Rol;

public interface RolService {

   public List<Rol> obtenerTodo();

   public Map<Integer,String> getRolMap();

   public Rol findByIdRol(Integer iIdR);

   public Rol findByNombre(String strN);

   public void guardarObj(Rol objRolOld, Rol objRolNew, String sUsuarioSesion, String sTipoAuditoria);
   
   public List<Rol> getRolesMenosUsuario(List<Rol> delUsuario);
   
   public List<Rol> getRolesPorUsuario(String usuario);
}
