/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import com.btg.ositran.siged.domain.Proveido;

public class ProveidoDAOImpl implements ProveidoDAO {
	
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Proveido> buscarPorCodigo(String codigoProveido) {
		try{
		    String sql = "SELECT p FROM Proveido p WHERE p.idProveido = :codigo ORDER BY p.nombre";
		    return em.createQuery(sql).setParameter("codigo", new Integer(codigoProveido)).getResultList();
		}catch(NoResultException e){
			return null;
		}
	}
        
        public List<Proveido> buscarProveidosActivos() {
		try{
                    String sql = "SELECT p FROM Proveido p WHERE p.estado = 'A' ORDER BY p.nombre";
		    return em.createQuery(sql).getResultList();
		}catch(NoResultException e){
			return null;
		}
	}
	
	public Proveido buscarPorId(Integer id){
               return em.find(Proveido.class, id);
	}
	
	public EntityManager getEm(){
		return em;
	}

	@PersistenceContext(unitName="sigedPU")
	public void setEm(EntityManager em){
		this.em=em;
	}
	
}
