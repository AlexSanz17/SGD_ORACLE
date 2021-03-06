/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.btg.ositran.siged.domain.Grid;

@Repository
public class GridDAOImpl implements GridDAO {
	
	private EntityManager em;
	private static Logger _log=Logger.getLogger(GridDAOImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public List<Grid> findAll(){
		return em.createNamedQuery("Grid.findAll").getResultList();
	}

	@Override
	public Grid findByCodigo(String codigo){
		Grid objGrid=null;
		try{
			objGrid=(Grid) em.createNamedQuery("Grid.findByCodigo").setParameter("codigo",codigo).getSingleResult();
			_log.debug("Grid encontrado "+codigo);
		}catch(NoResultException e){
			_log.warn("No se encontrado el Grid con codigo: "+codigo);
		}
		return objGrid;
	}

   public Integer findIDByCodigo(String sCodigo) {
      try {
         return (Integer) em.createNamedQuery("Grid.findByCodigo2")
                            .setParameter("codigo", sCodigo)
                            .getSingleResult();
      } catch(NoResultException e){
			_log.warn("No se encontrado el Grid con codigo: "+sCodigo);
         return null;
		}
   }

	@Override
	public Grid findByDescripcion(String descripcion){
		Grid objGrid=null;
		try{
			objGrid=(Grid) em.createNamedQuery("Grid.findByCodigo").setParameter("descripcion",descripcion).getSingleResult();
			_log.debug("Grid encontrado "+descripcion);
		}catch(NoResultException e){
			_log.warn("No se ha encontrado el Grid con descripcion: "+descripcion);
		}
		return objGrid;
	}

	@Override
	public Grid findByIdgrid(Integer idgrid){
		Grid objGrid=null;
		try{
			objGrid=(Grid) em.createNamedQuery("Grid.findByCodigo").setParameter("idgrid",idgrid).getSingleResult();
			_log.debug("Grid encontrado con id:"+idgrid);
		}catch(NoResultException e){
			_log.warn("No se ha encontrado el Grid con id: "+idgrid);
		}
		return objGrid;
	}

	@Override
	public Grid findByNombre(String nombre){
		Grid objGrid=null;
		try{
			objGrid=(Grid) em.createNamedQuery("Grid.findByCodigo").setParameter("nombre",nombre).getSingleResult();
			_log.debug("Grid encontrado con nombre "+nombre);
		}catch(NoResultException e){
			_log.warn("No se encontrado el Grid con nombre: "+nombre);
		}
		return objGrid;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Grid> findByRol(Integer idRol){
		List<Grid> lstGrid=null;
		try{
			lstGrid=em.createNamedQuery("Grid.findByRol").setParameter("idRol",idRol).getResultList();
			_log.debug("Grids encontrados: "+lstGrid.size()+" para el rol con id: "+idRol);
		}catch(NoResultException e){
			_log.warn("No se ha encontrado el Grid para el rol con id: "+idRol);
		}
		return lstGrid;
	}

	@PersistenceContext(unitName="sigedPU")
	public void setEm(EntityManager em){
		this.em=em;
	}
}
