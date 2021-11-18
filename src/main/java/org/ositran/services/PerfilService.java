package org.ositran.services;

import java.util.List;
import java.util.Map;

import com.btg.ositran.siged.domain.Perfil;
import com.btg.ositran.siged.domain.Rol;

public interface PerfilService {

	public List<Perfil> findAll();

	public Map<String,Integer> getMapRecursoXPerfil(Integer iIdPerfil);

	public Map<Integer,String> obtenerMapTodo();

	public Perfil findByIdPerfil(Integer iIdPerfil);

	public void savePerfil(Perfil objPerfilOld, Perfil objPerfilNew, List<Integer> lstIdRecurso, String sUsuarioSesion, String sTipoAuditoria);
   
	public Map<String,Integer> getRecursosPorPerfiles(List<Perfil> perfiles);
	
	public Map<String,String> getMapRecursosPorRoles(List<Rol> roles,Map<String,Integer> recursosSesion);
}
