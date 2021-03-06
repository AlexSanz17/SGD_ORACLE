/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.btg.ositran.siged.domain.Auditoria;

@Repository
public class AuditoriaDAOImpl implements AuditoriaDAO{
	
	private static Logger _log=Logger.getLogger(AuditoriaDAOImpl.class);
	
	@PersistenceContext(unitName="sigedPU")
	private EntityManager em;

	public Auditoria SaveAuditoria(Auditoria objAuditoria){
		if(objAuditoria.getIdAuditoria()==null){
			em.persist(objAuditoria); // Nuevo
			em.flush();
			em.refresh(objAuditoria);
		}else{
			em.merge(objAuditoria); // Actualizacion
			em.flush();
		}
		return objAuditoria;
	}

	@SuppressWarnings("unchecked")
	public List<Auditoria> filtrarAuditoria(String sNroExpediente,String sUsuario,String sModulo,String sNroDocumento,String sFechaDesde,String sFechaHasta){
		SimpleDateFormat formato=new SimpleDateFormat("dd/MM/yyyy");
		String sQuery="SELECT a FROM Auditoria a WHERE LOWER(a.usuario) LIKE :usuario AND LOWER(a.modulo) LIKE :modulo ";
		if(!sNroExpediente.isEmpty()){
			sQuery+="AND LOWER(a.expediente.nroexpediente) LIKE :nroexpediente ";
		}
		if(!sNroDocumento.isEmpty()){
			sQuery+="AND LOWER(a.documento.numeroDocumento) LIKE :nrodocumento ";
		}
		if(!sFechaDesde.isEmpty()){
			sQuery+="AND a.fechaAudioria >= :fechadesde ";
		}
		if(!sFechaHasta.isEmpty()){
			sQuery+="AND a.fechaAudioria <= :fechahasta ";
		}
		sQuery+="ORDER BY a.idAuditoria DESC";
		Query obj=em.createQuery(sQuery).setParameter("usuario","%"+sUsuario.toLowerCase()+"%").setParameter("modulo","%"+sModulo.toLowerCase()+"%");
		if(!sNroExpediente.isEmpty()){
			obj.setParameter("nroexpediente","%"+sNroExpediente.toLowerCase()+"%");
		}
		if(!sNroDocumento.isEmpty()){
			obj.setParameter("nrodocumento","%"+sNroDocumento.toLowerCase()+"%");
		}
		if(!sFechaDesde.isEmpty()){
			try{
				Date datFecha=formato.parse(sFechaDesde);
				_log.debug("Fecha Desde ["+datFecha+"]");
				obj.setParameter("fechadesde",datFecha);
			}catch(Exception ex){
				_log.error(ex.getMessage(),ex);
			}
		}
		if(!sFechaHasta.isEmpty()){
			try{
				Date datFecha=formato.parse(sFechaHasta);
				datFecha.setHours(24);
				datFecha.setMinutes(0);
				datFecha.setSeconds(0);
				_log.debug("Fecha Hasta ["+datFecha+"]");
				obj.setParameter("fechahasta",datFecha);
			}catch(Exception ex){
				_log.error(ex.getMessage(),ex);
			}
		}
		return obj.getResultList();
	}

	// ////////////////////////////////
	// Getters and Setters //
	// ////////////////////////////////
	public EntityManager getEm(){
		return em;
	}

	public void setEm(EntityManager em){
		this.em=em;
	}

	@Override
	public List<Auditoria> findAvanced(){
		return null;
	}
}
