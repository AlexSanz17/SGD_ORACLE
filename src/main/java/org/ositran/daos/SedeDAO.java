/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;

import com.btg.ositran.siged.domain.Sede;
import com.btg.ositran.siged.domain.Usuario;

public interface SedeDAO{

	public List<Sede> buscarTodo();

	public Sede buscarPorId(Integer iIdSede);

	public Sede guardarObj(Sede objSede);
	
	public Sede getSedePorUuario(Usuario usuario);
}
