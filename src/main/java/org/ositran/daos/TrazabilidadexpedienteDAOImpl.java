/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.text.SimpleDateFormat;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.CacheMode;
import org.springframework.stereotype.Repository;

import com.btg.ositran.siged.domain.Trazabilidadexpediente;

@Repository
public class TrazabilidadexpedienteDAOImpl implements TrazabilidadexpedienteDAO{
	
	private EntityManager em;
	private static Log log=LogFactory.getLog(TrazabilidadexpedienteDAOImpl.class);

	// ////////////////////////////////
	// Methods //
	// ////////////////////////////////
	public Trazabilidadexpediente findByMaxNroRegistro(Integer iIdExpediente){
		Trazabilidadexpediente data=null;
		try{
			data=(Trazabilidadexpediente) em.createNamedQuery("Trazabilidadexpediente.findByMaxNroRegistro").setParameter("idexpediente",iIdExpediente).getSingleResult();
		}catch(Exception e){
		}
		return data;
	}

	public Trazabilidadexpediente findByNroRegistroByExpediente(Integer idExpediente,Integer nroRegistro){
		String jpql="SELECT t FROM Trazabilidadexpediente t WHERE t.expediente.id = "+idExpediente+"AND t.nroregistro = "+nroRegistro;
		return (Trazabilidadexpediente) em.createQuery(jpql).getSingleResult();
	}

	public Trazabilidadexpediente saveTrazabilidadExpediente(Trazabilidadexpediente objTrazaExpediente){
		em.persist(objTrazaExpediente);
		em.flush();
		em.refresh(objTrazaExpediente);
		return objTrazaExpediente;
	}

	public Trazabilidadexpediente save(Trazabilidadexpediente trazabilidadexpediente){
		SimpleDateFormat sdfMes=new SimpleDateFormat("MM");
		Integer fechacreacionmonth=Integer.parseInt(sdfMes.format(trazabilidadexpediente.getFechacreacion()));
		trazabilidadexpediente.setFechacreacionmonth(fechacreacionmonth);
		SimpleDateFormat sdfAnio=new SimpleDateFormat("yyyy");
		Integer fechacreacionyear=Integer.parseInt(sdfAnio.format(trazabilidadexpediente.getFechacreacion()));
		trazabilidadexpediente.setFechacreacionyear(fechacreacionyear);
		if(trazabilidadexpediente.getFecharecibido()!=null){
			// Integer
			// fechaRecibidonmonth=Integer.parseInt(sdfMes.format(trazabilidadexpediente.getFecharecibido()));
			trazabilidadexpediente.setFecharecibidomonth(fechacreacionmonth);
			// Integer
			// fechaRecibidoyear=Integer.parseInt(sdfAnio.format(trazabilidadexpediente.getFecharecibido()));
			trazabilidadexpediente.setFecharecibidoyear(fechacreacionyear);
		}
		if(trazabilidadexpediente.getIdtrazabilidadexpediente()==null){
			em.persist(trazabilidadexpediente); // Nuevo
			em.flush();
			em.refresh(trazabilidadexpediente);
		}else{
			em.merge(trazabilidadexpediente); // Actualizacion
			em.flush();
		}
		return trazabilidadexpediente;
	}

	public List findTrazabilidadXUsuarioxExp(int remitente,int destinatario,int idexpediente,int etapa){
		List data=em.createQuery("SELECT max(t.idtrazabilidadexpediente) FROM Trazabilidadexpediente t where t.remitente.idusuario = "+remitente+" and t.destinatario.idusuario="+destinatario+" and t.expediente.id = "+idexpediente+" and t.etapa.idetapa="+etapa).getResultList();
		return data;
	}

	public Trazabilidadexpediente findByIdtrazabilidadexpediente(int idTrazabilidadExpediente){
		Trazabilidadexpediente trazabilidad=null;
		Query q=em.createNamedQuery("Trazabilidadexpediente.findByIdtrazabilidadexpediente");
		q.setParameter("idtrazabilidadexpediente",idTrazabilidadExpediente);
		try{
			trazabilidad=(Trazabilidadexpediente) q.getSingleResult();
		}catch(NoResultException e){
			log.warn("No se encontro la TazabilidadExpediente con id="+idTrazabilidadExpediente);
		}
		return trazabilidad;
	}

	@SuppressWarnings("unchecked")
	public List<Trazabilidadexpediente> findByIdexpediente(Integer iIdExpediente){
		return em.createNamedQuery("Trazabilidadexpediente.findByIdexpediente").setHint("org.hibernate.cacheMode",CacheMode.REFRESH).setParameter("idexpediente",iIdExpediente).getResultList();
	}

	@PersistenceContext(unitName="sigedPU")
	public void setEm(EntityManager em){
		this.em=em;
	}
}
