/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.btg.ositran.siged.domain.Motivo;

@Repository
public class MotivoDAOImpl implements MotivoDAO{
	
	private EntityManager em;

	@SuppressWarnings("unchecked")
	public List<Motivo> findAll(){
		return em.createNamedQuery("Motivo.findAll").getResultList();
	}

	public Motivo findByIdmotivo(Integer idmotivo){
		return (Motivo) em.createNamedQuery("Motivo.findByIdmotivo").setParameter("idmotivo",idmotivo).getSingleResult();
	}

	public Motivo saveMotivo(Motivo motivo){
		if(motivo.getIdmotivo()==null){
			em.persist(motivo); // Nuevo
			em.flush();
			em.refresh(motivo);
		}else{
			em.merge(motivo); // Actualizacion
			em.flush();
		}
		return motivo;
	}

	@PersistenceContext(unitName="sigedPU")
	public void setEm(EntityManager em){
		this.em=em;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Motivo> encontrarPorProceso(int idProceso){
		Query q=em.createNamedQuery("Motivo.encontrarPorProceso");
		q.setParameter("proceso",idProceso);		
		return q.getResultList();
	}
}
