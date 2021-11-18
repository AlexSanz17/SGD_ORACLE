/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;

import com.btg.ositran.siged.domain.Unidad;

public interface UnidadDAO {

	public List<Unidad> findAll();
	public List<Unidad> findAllGrupo();

	public Unidad findByIdunidad(int idunidad);
        
        public List<Unidad> findByGrupoUnidad(Integer idunidad);

	public Unidad findDependenciaByIdunidad(int idunidad);

	public Unidad guardarObj(Unidad objUnidad);

	public Unidad encontrarPorNombre(String nombre);

	public List<Unidad> findAllUnidadSAS();

	public List<Unidad> buscarUnidadesFuncionales();

	List<Unidad> findByNombreLike(String like);
}
