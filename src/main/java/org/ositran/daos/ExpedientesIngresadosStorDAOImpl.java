/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.btg.ositran.siged.domain.ExpedientesIngresadosStor;

public class ExpedientesIngresadosStorDAOImpl implements
		ExpedientesIngresadosStorDAO {
	private EntityManager em;

	@PersistenceContext(unitName="sigedPU")
	public void setEm(EntityManager em) {
		   this.em = em;
	}

	private static Logger _log  =  Logger.getLogger(ExpedientesIngresadosStorDAOImpl.class);
	
	@Override
	public List<ExpedientesIngresadosStor> listarTodo() {
		// TODO Auto-generated method stub
		String sQuery = "SELECT f FROM ExpedientesIngresadosStor f order by f.expediente";
		Query obj = em.createQuery(sQuery);
		return obj.getResultList();
	}
	
	@Override
	public List<ExpedientesIngresadosStor> generarLista(String fechaDesde, String fechaHasta) {
		String sQuery = "SELECT f FROM ExpedientesIngresadosStor f ";
		boolean where = false;
		SimpleDateFormat fechita = new SimpleDateFormat("dd/MM/yyyy");
		//----FECHAS---------------------------------------------------
		if(!fechaDesde.equals("")){
			sQuery += "WHERE f.fechaCreacionExpediente >= :campofechadesde ";
			where = true;
		}
		if(!fechaHasta.equals("")){
			if(!where){
				sQuery += "WHERE f.fechaCreacionExpediente <= :campofechahasta ";
				where = true;				
			}else{
				sQuery += "AND f.fechaCreacionExpediente <= :campofechahasta ";
			}
		}
		//-------------------------------------------------------------
		Query obj = em.createQuery(sQuery);
		//----FECHAS---------------------------------------------------
		if(!fechaDesde.equals("")){
			try{
				Date datFecha = fechita.parse(fechaDesde);
				obj.setParameter("campofechadesde", datFecha);
			}catch(Exception ex){
				_log.error(ex.getMessage(), ex);
			}
		}
		if(!fechaHasta.equals("")){
			try{
				Date datFecha = fechita.parse(fechaHasta);
				datFecha.setHours(24);
				datFecha.setMinutes(0);
				datFecha.setSeconds(0);
				obj.setParameter("campofechahasta", datFecha);
			}catch(Exception ex){
				_log.error(ex.getMessage(), ex);
			}
		}
		//-------------------------------------------------------------
		return obj.getResultList();
	}
}
