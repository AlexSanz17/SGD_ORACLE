/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.btg.ositran.siged.domain.ExpedientesPendientesAYQ;

public class ExpedientesPendientesAYQDAOImpl implements
		ExpedientesPendientesAYQDAO {
	private EntityManager em;
	private static Logger log = Logger.getLogger(ExpedientesPendientesAYQDAOImpl.class);
	
	@PersistenceContext(unitName="sigedPU")
	public void setEm(EntityManager em) {
		this.em = em;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ExpedientesPendientesAYQ> listarTodo() {
		String sQuery = "SELECT f FROM ExpedientesPendientesAYQ f ORDER BY f.expediente";
		Query obj = em.createQuery(sQuery);
		return obj.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ExpedientesPendientesAYQ> generarListaAYQ(String tipoExpediente, String fechaDesde, String fechaHasta, String sala, 
														  String responsable, String analista, String vencimientoDesde, 
														  String vencimientoHasta){
		String sQuery = "SELECT f FROM ExpedientesPendientesAYQ f ";
		boolean flagWhere = false;
		SimpleDateFormat fechita = new SimpleDateFormat("dd/MM/yyyy");
		if(!(tipoExpediente.equals("Todos"))){
			sQuery += "WHERE f.tipodocumento like :campotipoexpediente ";
			flagWhere=true;
		}
		if(!fechaDesde.equals("")){
			if(flagWhere){
				sQuery +="AND f.fechacreacion >= :campofechadesde AND f.fechacreacion <= :campofechahasta ";
			}else{
				sQuery +="WHERE f.fechacreacion >= :campofechadesde AND f.fechacreacion <= :campofechahasta ";
				flagWhere = true;
			}
		}
		if(!(sala.equals("Todas"))){
			if(sala.equals("Sin sala asignada")){
				if(flagWhere){
					sQuery +="AND f.sala is null ";
				}else{
					sQuery +="WHERE f.sala is null ";
					flagWhere = true;
				}
			}else{
				if(flagWhere){
					sQuery +="AND f.sala like :camposala ";
				}else{
					sQuery +="WHERE f.sala like :camposala ";
					flagWhere = true;
				}
			}
		}
		if(!responsable.equals("Todos")){
			if(flagWhere){
				sQuery +="AND f.propietario like :camporesponsable ";
			}else{
				sQuery +="WHERE f.propietario like :camporesponsable ";
				flagWhere = true;
			}
		}
		if(!(analista.equals("Todos"))){
			if(analista.equals("Sin analista asignado")){
				if(flagWhere){
					sQuery +="AND f.analista is null ";
				}else{
					sQuery +="WHERE f.analista is null ";
					flagWhere = true;
				}
			}else{
				if(flagWhere){
					sQuery +="AND f.analista like :campoanalista ";
				}else{
					sQuery +="WHERE f.analista like :campoanalista ";
					flagWhere = true;
				}
			}
		}
		if(!vencimientoDesde.equals("")){
			if(flagWhere){
				sQuery +="AND f.fechavencimiento >= :campovencimientodesde AND f.fechavencimiento <= :campovencimientohasta ";
			}else{
				sQuery +="WHERE f.fechavencimiento >= :campovencimientodesde AND f.fechavencimiento <= :campovencimientohasta ";
				flagWhere = true;
			}
		}
		//-----------------------------------------------------
		Query obj = em.createQuery(sQuery);
		//-----------------------------------------------------
		if(!(tipoExpediente.equals("Todos"))){
			obj.setParameter("campotipoexpediente", tipoExpediente);
		}
		if(!fechaDesde.equals("")){
			try{
				Date datFecha = fechita.parse(fechaDesde);
				obj.setParameter("campofechadesde", datFecha);
				datFecha = fechita.parse(fechaHasta);
				datFecha.setHours(24);
				datFecha.setMinutes(0);
				datFecha.setSeconds(0);
				obj.setParameter("campofechahasta", datFecha);
			}catch(Exception ex){
				log.error(ex.getMessage(), ex);
			}
		}
		if(!sala.equals("Todas")&&!(sala.equals("Sin sala asignada"))){
			obj.setParameter("camposala", sala);
		}
		if(!responsable.equals("Todos")){
			obj.setParameter("camporesponsable", responsable);
		}
		if(!analista.equals("Todos")&&!analista.equals("Sin analista asignado")){
			obj.setParameter("campoanalista", analista);
		}
		if(!vencimientoDesde.equals("")){
			try{
				Date datFecha = fechita.parse(vencimientoDesde);
				obj.setParameter("campovencimientodesde", datFecha);
				datFecha = fechita.parse(vencimientoHasta);
				datFecha.setHours(24);
				datFecha.setMinutes(0);
				datFecha.setSeconds(0);
				obj.setParameter("campovencimientohasta", datFecha);
			}catch(Exception ex){
                                log.error(ex.getMessage(), ex);
			}
		}
		return obj.getResultList();
	}
	
	public EntityManager getEm() {
		return em;
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

}
