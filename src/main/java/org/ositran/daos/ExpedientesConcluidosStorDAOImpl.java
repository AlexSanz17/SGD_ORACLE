/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.btg.ositran.siged.domain.ExpedientesConcluidosStor;

public class ExpedientesConcluidosStorDAOImpl implements
		ExpedientesConcluidosStorDAO {

	private EntityManager em;

	@PersistenceContext(unitName="sigedPU")
	public void setEm(EntityManager em) {
		   this.em = em;
	}

	private static Logger _log  =  Logger.getLogger(ExpedientesConcluidosStorDAOImpl.class);
	
	@Override
	public List<ExpedientesConcluidosStor> listarTodo() {
		// TODO Auto-generated method stub
		String sQuery = "SELECT f FROM ExpedientesConcluidosStor f order by f.expediente";
		Query obj = em.createQuery(sQuery);
		return obj.getResultList();
	}

	@Override
	public List<ExpedientesConcluidosStor> generarLista(String fechaDesde, String fechaHasta){
		boolean where = false;
		String sQuery = "SELECT f FROM ExpedientesConcluidosStor f ";
		SimpleDateFormat fechita = new SimpleDateFormat("dd/MM/yyyy");
		//----FECHAS------------------------------------------------------------
		if(!fechaDesde.equals("")){
			sQuery += "WHERE f.fechaConcluido >= :campofechadesde ";
			where = true;
		}
		if(!fechaHasta.equals("")){
			if(!where){
				sQuery += "WHERE f.fechaConcluido <= :campofechahasta ";
				where = true;
			}else{
				sQuery += "AND f.fechaConcluido <= :campofechahasta ";
			}
		}
		//----------------------------------------------------------------------
		Query obj = em.createQuery(sQuery);
		//----FECHAS------------------------------------------------------------
		if(!fechaDesde.equals("")){
			try{
				Date datFecha = fechita.parse(fechaDesde);
				obj.setParameter("campofechadesde", datFecha);
			}catch(Exception e){
				_log.error(e.getMessage(), e);
			}
		}
		if(!fechaHasta.equals("")){
			try{
				Date datFecha = fechita.parse(fechaHasta);
				datFecha.setHours(24);
				datFecha.setMinutes(0);
				datFecha.setSeconds(0);
				obj.setParameter("campofechahasta", datFecha);
			}catch(Exception e){
				_log.error(e.getMessage(), e);
			}
		}
		//----------------------------------------------------------------------
		return obj.getResultList();
	}
}
