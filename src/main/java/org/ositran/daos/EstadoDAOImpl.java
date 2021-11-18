/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import org.ositran.utils.Constantes;
import org.springframework.stereotype.Repository;

import com.btg.ositran.siged.domain.Estado;

/**
 *
 * @author ANGEL
 */

@Repository
public class EstadoDAOImpl implements EstadoDAO {
    
    private EntityManager em;
    private static Logger log =  Logger.getLogger(EstadoDAOImpl.class);

    public List<Estado> findAll() {
        return em.createNamedQuery("Estado.findAll")
                .getResultList();
    }
    
    public Estado findByIdestado(Integer idEstado){
        return (Estado) em.createNamedQuery("Estado.findByIdestado")
        .setParameter("idestado", idEstado).getSingleResult();   	
    }
    
    public Estado findByCodigo(String codigoEstado) {
        return (Estado) em.createNamedQuery("Estado.findByCodigo")
                         .setParameter("codigo", codigoEstado).getSingleResult();
    }

    public Estado find(String codigo, String tipo) {
        Query q = em.createQuery("SELECT e FROM Estado e WHERE e.codigo = ?1 and e.tipo=?2");
        q.setParameter(1, codigo);
        q.setParameter(2, tipo);
        return (Estado) q.getSingleResult();
    }

    public List<Estado> findByTipo(String tipoEstado) {
        return em.createNamedQuery("Estado.findByTipo")
                         .setParameter("tipo", tipoEstado).getResultList();
    }

	@Override
	public List<Estado> findByIdProceso(Integer idProceso) {
		   StringBuffer jql = new StringBuffer();
		   
		   jql.append("\nselect es from Estado es where 1 = 1");
		   
		   if(idProceso != null)
			   jql.append("and es.idproceso = "+idProceso+" \n");

		   jql.append("and es.estado = '"+ String.valueOf(Constantes.ESTADO_ACTIVO).charAt(0) +"' \n");
		   jql.append("order by es.idestado asc");
		   
		   
		   return em.createQuery(jql.toString()).getResultList();
	}   
    
	@Override
    public void saveObject(Estado estado) {
	 	   try {
	 		   if(estado.getIdestado() == null)
	 			   em.persist(estado);
	 		   else
	 			   em.merge(estado);
	 	   	} catch (Exception e) {
	 	   		log.error(e.getMessage(), e);
	 	   	}
	}  
    
    public EntityManager getEm() {
        return em;
    }

    @PersistenceContext(unitName="sigedPU")
    public void setEm(EntityManager em) {
        this.em = em;
    }





}
