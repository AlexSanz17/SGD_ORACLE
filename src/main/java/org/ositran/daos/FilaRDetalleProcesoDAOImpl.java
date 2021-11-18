/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;
/*DUMMY*/
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.btg.ositran.siged.domain.FilaRDetalleProceso;

public class FilaRDetalleProcesoDAOImpl implements FilaRDetalleProcesoDAO {
	private EntityManager em;
	private static Logger _log  =  Logger.getLogger(FilaRDetalleProcesoDAOImpl.class);
	
	/////////////////////////////////
	// Methods                      //
	//////////////////////////////////		
	
	/* (non-Javadoc)
	 * @see org.ositran.daos.FilaRDetalleProcesoDAO#setEm(javax.persistence.EntityManager)
	 */
	@PersistenceContext(unitName="sigedPU")
	public void setEm(EntityManager em) {
		this.em = em;
	}
	
	/* (non-Javadoc)
	 * @see org.ositran.daos.FilaRDetalleProcesoDAO#reporteInicialDetallado()
	 */
	@SuppressWarnings("unchecked")
	public List<FilaRDetalleProceso> reporteInicialDetallado(){
		String sQuery = "SELECT f FROM FilaRDetalleProceso f";
		Query obj = em.createQuery(sQuery);
		return obj.getResultList();
	}
	
	/* (non-Javadoc)
	 * @see org.ositran.daos.FilaRDetalleProcesoDAO#generarReporteDetalle(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<FilaRDetalleProceso> generarReporteDetalle (String procesoClasif, String procesoElegido, String estado, String fechaDesde, String fechaHasta){
		/**
		 * procesoClasif puede tomar los siguientes valores: "grupoproceso", "proceso", cuidado con los nulls 
		 * procesoElegido puede tomar los siguientes valores: "Todos", valor especifico, cuidado con los nulls
		 * estado puede tomar los siguientes valores: "Todos", valor especifico, cuidado con los nulls
		 */
		
		_log.debug("Estado: "+estado);
		_log.debug(procesoElegido);
		SimpleDateFormat formato=new SimpleDateFormat("dd/MM/yyyy");
		String sQuery;
		boolean flagWhere = false;
		sQuery = "SELECT f FROM FilaRDetalleProceso f ";
		_log.debug("Seleccionado: "+ procesoElegido);
		
		if(!((procesoElegido.equals("Todos"))||(procesoElegido==null)||(procesoElegido.isEmpty()))){
			_log.debug("Seleccionado: "+ procesoElegido);
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
		if(!procesoClasif.isEmpty()){
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
		
}
