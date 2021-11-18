/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;
/*DUMMY*/
import java.text.SimpleDateFormat;
import java.util.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.btg.ositran.siged.domain.FilaRResumenProceso;

@Repository
public class FilaRResumenProcesoDAOImpl implements FilaRResumenProcesoDAO {
	private EntityManager em;
	
	/* (non-Javadoc)
	 * @see org.ositran.daos.FilaRResumenProcesoDAO#setEm(javax.persistence.EntityManager)
	 */
	/* (non-Javadoc)
	 * @see org.ositran.daos.FilaRResumenProcesoDAO#setEm(javax.persistence.EntityManager)
	 */
	@PersistenceContext(unitName="sigedPU")
	public void setEm(EntityManager em) {
		   this.em = em;
	}

	/////SEPARAR////////////////////////////
	   // Methods                      //
	   //////////////////////////////////
	private static Logger _log  =  Logger.getLogger(FilaRResumenProcesoDAOImpl.class);
	

	@SuppressWarnings("unchecked")
	public List<FilaRResumenProceso> reporteInicialResumen(){
		//f por cada fila de la vista
		String sQuery = "SELECT f FROM FilaRResumenProceso f";
		Query obj = em.createQuery(sQuery);
		return obj.getResultList();
	}
	

	
	/* (non-Javadoc)
	 * @see org.ositran.daos.FilaRResumenProcesoDAO#generarReporteResumen(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	/* (non-Javadoc)
	 * @see org.ositran.daos.FilaRResumenProcesoDAO#generarReporteResumen(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	/* (non-Javadoc)
	 * @see org.ositran.daos.FilaRResumenProcesoDAO#generarReporteResumen(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<FilaRResumenProceso> generarReporteResumen (String procesoClasif, String procesoElegido, String estado, String fechaDesde, String fechaHasta){
		/** 
		 * procesoClasif puede tomar los siguientes valores: "grupoproceso", "proceso", cuidado con los nulls 
		 * procesoElegido puede tomar los siguientes valores: "Todos", valor especifico, cuidado con los nulls
		 * estado puede tomar los siguientes valores: "Todos", valor especifico, cuidado con los nulls
		 */
		SimpleDateFormat formato=new SimpleDateFormat("dd/MM/yyyy");
		String sQuery;
		boolean flagWhere = false;
		sQuery = "SELECT f FROM FilaRResumenProceso f ";
		if(!((procesoElegido.equals("Todos"))||(procesoElegido==null)||(procesoElegido.isEmpty()))){
			//-----------------------------------------------------
			if (procesoClasif.equals("proceso")){
				sQuery += "WHERE LOWER(f.proceso) like :campoproespecif "; 
			}else{
				sQuery += "WHERE LOWER(f.grupoproceso) like :campoproespecif "; 
			}
			flagWhere = true;
			//-----------------------------------------------------
		}
		if(!((estado.equals("Todos"))||(estado==null)||(estado.isEmpty()))){
			if(flagWhere){
				//Ya se puso el where
				sQuery += "AND LOWER(f.estado) like :campoestado "; 
			}else{
				//Aun no tiene where
				sQuery += "WHERE LOWER(f.estado) like :campoestado "; 
				flagWhere = true;
			}
		}
		//Fechas------------------------------------------------------
		if(fechaDesde!=null&&!fechaDesde.isEmpty()){
			if(flagWhere){
				//Ya se puso el where
				sQuery += "AND f.fechaexpediente >= :campofechadesde "; 
			}else{
				//Aun no tiene where
				sQuery += "WHERE f.fechaexpediente >= :campofechadesde "; 
				flagWhere = true;
			}
		} 
		if(fechaHasta!=null&&!fechaHasta.isEmpty()){
			if(flagWhere){
				//Ya se puso el where
				sQuery += "AND f.fechaexpediente <= :campofechahasta "; 
			}else{
				//Aun no tiene where
				sQuery += "WHERE f.fechaexpediente <= :campofechahasta "; 
				flagWhere = true;
			}
		}
		//Fin----Fechas-------------------------------------------------
		sQuery += "ORDER BY :campoproc";
		Query obj = em.createQuery(sQuery);
		if(procesoClasif!=null&&!procesoClasif.isEmpty()){
			obj.setParameter("campoproc","%"+procesoClasif.toLowerCase()+"%");
		}else{
			obj.setParameter("campoproc","%grupoproceso%");
		}
		if(!((procesoElegido.equals("Todos"))||(procesoElegido==null)||(procesoElegido.isEmpty()))){
			obj.setParameter("campoproespecif","%"+procesoElegido.toLowerCase()+"%");
		}
		if(!((estado.equals("Todos"))||(estado==null)||(estado.isEmpty()))){
			obj.setParameter("campoestado","%"+estado.toLowerCase()+"%");
		}
		//Fechas---------------------------------------------------------
		if(fechaDesde!=null&&!fechaDesde.isEmpty()){
			try{
				Date datFecha=formato.parse(fechaDesde);
				_log.debug("Fecha Desde ["+datFecha+"]");
				obj.setParameter("campofechadesde",datFecha);
			}catch(Exception ex){
				_log.error(ex.getMessage(),ex);
			}
		}
		if(fechaHasta!=null&&!fechaHasta.isEmpty()){
			try{
				Date datFecha=formato.parse(fechaHasta);
				datFecha.setHours(24);
				datFecha.setMinutes(0);
				datFecha.setSeconds(0);
				_log.debug("Fecha Hasta ["+datFecha+"]");
				obj.setParameter("campofechahasta",datFecha);
			}catch(Exception ex){
				_log.error(ex.getMessage(),ex);
			}
		}
		//Fin----Fechas--------------------------------------------------
		return obj.getResultList();
	}
	
	/* (non-Javadoc)
	 * @see org.ositran.daos.FilaRResumenProcesoDAO#generarReporteDetalle(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	
}
