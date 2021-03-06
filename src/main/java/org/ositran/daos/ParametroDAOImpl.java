/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.ositran.utils.Constantes;
import org.springframework.stereotype.Repository;

import com.btg.ositran.siged.domain.Parametro;

@Repository
public class ParametroDAOImpl implements ParametroDAO {
	
	private EntityManager em;

	@SuppressWarnings("unchecked")
	public List<Parametro> findAll() {
		return getEm().createNamedQuery("Parametro.findAll").getResultList();
	}

	@Override
	public List<Parametro> findAllWithoutStor() {
		return getEm().createNamedQuery("Parametro.findAllWithoutStor").getResultList();
	}
	
        public Parametro findObjById(Integer iIdParametro) {
		return em.find(Parametro.class, iIdParametro);
	}

	public EntityManager getEm() {
		return em;
	}

	@PersistenceContext(unitName="sigedPU")
	public void setEm(EntityManager em) {
		this.em = em;
	}
	
	@SuppressWarnings("unchecked")
	public List<Parametro> findByTipo (String tipo){
		return getEm().createNamedQuery("Parametro.findByTipo").setParameter("tipo", tipo).getResultList();
	}
        
        public List<Parametro> findByTipoActivo(String tipo){
               return getEm().createNamedQuery("Parametro.findByTipoActivo").setParameter("tipo", tipo).getResultList();
        }

        public Parametro guardarObj(Parametro objParametro) {
           if (objParametro.getIdparametro() == null) {
              em.persist(objParametro);
              em.flush();
              em.refresh(objParametro);
           } else {
              em.merge(objParametro);
              em.flush();
           }

           return objParametro;
        }

	@Override
	public Parametro findByTipoUnico(String tipo) {
		List<Parametro> parametros=findByTipo(tipo);
		if(parametros!=null && parametros.size()==1){
			return parametros.get(0);
		}		
		return null;
	}
	@SuppressWarnings("unchecked")
	public Parametro findByTipoAndValue(String tipo, String value) {
		Parametro resultado =(Parametro)getEm().createNamedQuery("Parametro.findByTipoAndValor")
		.setParameter("tipo", tipo)
		.setParameter("valor", value)
		.getSingleResult();
		return resultado;
		}
	
	public List<Parametro> getEstados(){
		//f por cada fila de la vista
		String sQuery = "SELECT new Parametro ( f.estado, f.estado) FROM FilaRResumenProceso f group by f.estado ";
		Query obj = em.createQuery(sQuery);
		return obj.getResultList();
	}
	
	public List<Parametro> getProceso(){
		//f por cada fila de la vista
		String sQuery = "SELECT new Parametro ( f.proceso, f.proceso) FROM FilaRResumenProceso f group by f.proceso ";
		Query obj = em.createQuery(sQuery);
		return obj.getResultList();
	}
	
	public List<Parametro> getGrupoProceso(){
		//f por cada fila de la vista
		String sQuery = "SELECT new Parametro (g.idgrupoproceso, g.nombre) FROM Grupoproceso g ORDER BY g.nombre ";
		Query obj = em.createQuery(sQuery);
		return obj.getResultList();  
	}
	
	public List<Parametro> getTipoExpediente(){
		String sQuery = "SELECT new Parametro (f.tipodocumento, f.tipodocumento) FROM ExpedientesPendientesAYQ f group by f.tipodocumento";
		Query obj = em.createQuery(sQuery);
		return obj.getResultList(); 
	}
	
	public List<Parametro> getSalaAYQ(){
		String sQuery = "SELECT new Parametro (f.sala, f.sala) FROM ExpedientesPendientesAYQ f group by f.sala";
		Query obj = em.createQuery(sQuery);
		return obj.getResultList(); 
	}
	
	public List<Parametro> getResponsableAYQ(){
		String sQuery = "SELECT new Parametro (f.propietario, f.propietario) FROM ExpedientesPendientesAYQ f group by f.propietario";
		Query obj = em.createQuery(sQuery);
		return obj.getResultList(); 
	}
	
	public List<Parametro> getAnalistaAYQ(){
		String sQuery = "SELECT new Parametro (f.analista, f.analista) FROM ExpedientesPendientesAYQ f group by f.analista";
		Query obj = em.createQuery(sQuery);
		return obj.getResultList(); 
	}

	@Override
	public List<Parametro> getPrioridades() {
		String sQuery = "SELECT new Parametro (p.valor, p.descripcion) FROM Parametro p WHERE p.tipo = :tipo";
		Query obj = em.createQuery(sQuery).setParameter("tipo", Constantes.PARAMETRO_TIPO_PRIORIDAD);
		return obj.getResultList();
	}
	
}
