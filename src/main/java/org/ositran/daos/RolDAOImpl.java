/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.btg.ositran.siged.domain.Rol;

@Repository
public class RolDAOImpl implements RolDAO{

	private EntityManager em;

	private static Log log=LogFactory.getLog(RolDAOImpl.class);

	// ////////////////////////////////
	// Methods //
	// ////////////////////////////////
	@SuppressWarnings("unchecked")
	public List<Rol> findAll(){
		return getEm().createNamedQuery("Rol.findAll").getResultList();
	}

	public Rol findByIdRol(Integer iIdRol){
		return getEm().find(Rol.class,iIdRol);
	}

	public Rol guardarObj(Rol objRol){
		if(objRol.getIdrol()==null){
			em.persist(objRol);
			em.flush();
			em.refresh(objRol);
		}else{
			em.merge(objRol);
			em.flush();
		}

		return objRol;
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
	public Rol encontrarPorNombre(String nombre){
		Rol rol=null;
		if(nombre!=null){
			try{
				rol=(Rol) em.createNamedQuery("Rol.findByNombre").setParameter("nombre",nombre).getSingleResult();
			}catch(NonUniqueResultException e){
				log.warn("El rol buscado ("+nombre+") tiene mas de una coincidencia en la base de datos");
			}
			catch(NoResultException ne){
				log.warn("El rol buscado "+nombre+" no se encontro en la base de datos");
			}
		}
		return rol;
	}
}
