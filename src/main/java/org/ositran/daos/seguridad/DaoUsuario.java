/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos.seguridad;

import java.util.List;

import com.btg.ositran.siged.domain.seguridad.UsuarioSeguridad;

public interface DaoUsuario{
	
	public List<UsuarioSeguridad> getTodos();
	
}
