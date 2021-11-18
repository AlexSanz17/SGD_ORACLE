/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import com.btg.ositran.siged.domain.Unidad;

@Repository
public class UnidadDAOImpl implements UnidadDAO{
	private EntityManager em;

	// ////////////////////////////////
	// Methods //
	// ////////////////////////////////
	@SuppressWarnings("unchecked")
	public List<Unidad> findAllGrupo(){
		return getEm().createNamedQuery("Unidad.findByGrupo").getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Unidad> findAll(){
		return getEm().createNamedQuery("Unidad.findAll").getResultList();
	}

	public Unidad findByIdunidad(int idunidad){
		return (Unidad) getEm().createNamedQuery("Unidad.findByIdunidad").setParameter("idunidad",idunidad).getSingleResult();
	}

	public Unidad findDependenciaByIdunidad(int idunidad){
		return (Unidad) getEm().createNamedQuery("Unidad.findDependenciaByIdunidad").setParameter("idunidad",idunidad).getSingleResult();
	}

	public Unidad guardarObj(Unidad objUnidad){
		if(objUnidad.getIdunidad()==null){
			em.persist(objUnidad);
			em.flush();
			em.refresh(objUnidad);
		}else{
			em.merge(objUnidad);
			em.flush();
		}
		return objUnidad;
	}

	@SuppressWarnings("unchecked")
	public List<Unidad> findAllUnidadSAS(){
		return getEm().createNamedQuery("Unidad.findAllUnidadSAS").getResultList();
	}
        
        public List<Unidad> findByGrupoUnidad(Integer idunidad){
           return em.createNamedQuery("Unidad.findByGrupoUnidad").setParameter("idunidad",idunidad).getResultList();
        }

	@SuppressWarnings("unchecked")
	@Override
	public List<Unidad> buscarUnidadesFuncionales() {
		try{
			return em.createQuery("SELECT u FROM Unidad u WHERE u.dependencia IS NULL").getResultList();
		}catch(NoResultException e){
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Unidad> findByNombreLike(String like){
		if(like == null){
			return new ArrayList<Unidad>();
		}
		String sql = "SELECT u FROM Unidad u WHERE LOWER(u.nombre) LIKE :like";
		return em.createQuery(sql).setParameter("like", "%"+like.toLowerCase()+"%").getResultList();
	}

	// ////////////////////////////////
	// Getters and Setters //
	// ////////////////////////////////
	public EntityManager getEm(){
		return em;
	}

	@PersistenceContext(unitName="sigedPU")
	public void setEm(EntityManager em){
		this.em=em;
	}

	@Override
	public Unidad encontrarPorNombre(String nombre){
		Unidad unidad=null;
		try{
			unidad=(Unidad) em.createNamedQuery("Unidad.findByNombre").setParameter("nombre",nombre).getSingleResult();
		}
		catch(NoResultException e){

		}
		return unidad;
	}

}
