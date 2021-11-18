/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;

import com.btg.ositran.siged.domain.Grid;

public interface GridDAO {

	public List<Grid> findAll();
	
	public Grid findByIdgrid(Integer idgrid);
	
	public Grid findByNombre(String nombre);
	
	public Grid findByCodigo(String codigo);
	
	public Grid findByDescripcion(String descripcion);
	
	public List<Grid> findByRol(Integer idRol);

   public Integer findIDByCodigo(String sCodigo);
}
