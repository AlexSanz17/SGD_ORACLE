/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import com.btg.ositran.siged.domain.Valorcampo;

@Repository
public class ValorcampoDAOImpl implements ValorcampoDAO{
	
	//private Logger log=Logger.getLogger(this.getClass());
	private EntityManager em;

	@SuppressWarnings("unchecked")
	public List<Valorcampo> findAll(){
		return em.createNamedQuery("Valorcampo.findAll").getResultList();
	}

	@PersistenceContext(unitName="sigedPU")
	public void setEm(EntityManager em){
		this.em=em;
	}

	@Override
	public Valorcampo getValorCampo(int idValorCampo){
		return em.find(Valorcampo.class,idValorCampo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Valorcampo> getPorCampo(int idCampo){
		return em.createNamedQuery("ValorCampo.getPorCampo").setParameter("campo",idCampo).getResultList();
	}
}
