/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;

import com.btg.ositran.siged.domain.Rol;

public interface RolDAO {

	public List<Rol> findAll();

	public Rol findByIdRol(Integer iIdRol);

	public Rol guardarObj(Rol objRol);
   
	public Rol encontrarPorNombre(String nombre);
   
}