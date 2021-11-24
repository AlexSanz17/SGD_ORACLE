/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.ositran.dojo.BusquedaAvanzada;
import org.ositran.services.ParametroService;


import com.btg.ositran.siged.domain.Expediente;
import com.btg.ositran.siged.domain.FilaHojaFirma;
import com.btg.ositran.siged.domain.FilaHojaRuta;
import com.btg.ositran.siged.domain.FilaHojaRutaPK;
import com.btg.ositran.siged.domain.FilaReporteAPN2;
import com.btg.ositran.siged.domain.NodoDocConsolidadoReporteAPN4;
import com.btg.ositran.siged.domain.NodoDocReporteAPN3;
import com.btg.ositran.siged.domain.NodoDocReporteAPN4;
import com.btg.ositran.siged.domain.NodoDocReporteAPN8;
import com.btg.ositran.siged.domain.NodoExpReporteAPN3;
import com.btg.ositran.siged.domain.NodoExpReporteAPN4;
import com.btg.ositran.siged.domain.NodoExpReporteAPN8;
import com.btg.ositran.siged.domain.ReporteAPN1;
import com.btg.ositran.siged.domain.ReporteAPN9;
import com.btg.ositran.siged.domain.ReporteAPN10;
import com.btg.ositran.siged.domain.TrazabilidadDocumentaria;
import org.ositran.utils.ObjetoCodigoValor;

public class ReporteAPNDAOImpl implements ReporteAPNDAO {

	private static Logger log = Logger.getLogger(ReporteAPNDAOImpl.class);
	private EntityManager em;
	private ParametroService parametroService;


	public EntityManager getEm() {
		return em;
	}

	public ParametroService getParametroService() {
		return parametroService;
	}

	public void setParametroService(ParametroService parametroService) {
		this.parametroService = parametroService;
	}

	@PersistenceContext(unitName = "sigedPU")
	public void setEm(EntityManager em) {
		this.em = em;
	}

	public static Logger getLog() {
		return log;
	}

	public static void setLog(Logger log) {
		ReporteAPNDAOImpl.log = log;
	}

	@SuppressWarnings("unchecked")
	public List<NodoDocConsolidadoReporteAPN4> getListaConsolidadoReporteAPN4(String idArea,Integer unidad){
		String sql = "SELECT f.id, f.anndocumento, f.area, f.codArea, f.grupo,f.cantidad,f.minanno,f.maxanno,f.nroregorden FROM consolidadoreporteapn4 f ";

		if(((idArea.toString().trim()).equals("Todos"))){
			sql = sql + " INNER JOIN Parametro p ON f.codArea = substr (p.valor, 7, 3)  where p.tipo='DOCUMENTOS_PENDIENTES_GRUPO_AREA' and substr(f.grupo,0,3) = '"   +  String.valueOf(unidad.intValue()) +  "' order by f.grupo desc, f.nroregorden asc  ";
		}else{
			sql = sql + " WHERE f.codArea = " + Integer.parseInt(idArea);
		}

		Query q=em.createNativeQuery(sql.toString());
		List data=q.getResultList();
		List<NodoDocConsolidadoReporteAPN4> dataforward=new ArrayList<NodoDocConsolidadoReporteAPN4>();

		NodoDocConsolidadoReporteAPN4 n = null;

		for(int i=0;i<data.size();i++){
			Object obj[]=(Object[])data.get(i);
			n = new NodoDocConsolidadoReporteAPN4();
			n.setId(Integer.parseInt(obj[0].toString()));
			n.setAnndocumento(String.valueOf(obj[1]));
			n.setArea(String.valueOf(obj[2]));
			n.setCodArea(Integer.parseInt(obj[3].toString()));
			n.setGrupo(String.valueOf(obj[4]));
			n.setCantidad(Integer.parseInt(obj[5].toString()));
			n.setMinanno(Integer.parseInt(obj[6].toString()));
			n.setMaxanno(Integer.parseInt(obj[7].toString()));
			n.setNroregorden(Integer.parseInt(obj[8].toString()));

			dataforward.add(n);
		}

		
		return dataforward;



	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public List<NodoDocReporteAPN4> getListaDocReporteAPN4(String idArea) {
		String sql = "SELECT f FROM NodoDocReporteAPN4 f WHERE 1=1 ";


		if(!((idArea.toString().trim()).equals("Todos"))){
			sql = sql + " and f.codArea = :idCodArea";
		}

		Query obj = em.createQuery(sql);

		if(!((idArea.toString().trim()).equals("Todos"))){
		  obj.setParameter("idCodArea", Integer.parseInt(idArea));
		}

		List<NodoDocReporteAPN4> x = obj.getResultList();
		return obj.getResultList();
	}



	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public List<ReporteAPN9> getListaReporteAPN9(String area, String ano, String mes, String tipodocumento){//, String idareadestino) {

		    Query obj = null;
			String sQuery = "SELECT f FROM ReporteAPN9 f ";

			sQuery += "WHERE f.idAreaPropietario = :idAreaPropietario  ";
            sQuery +="AND  f.idTipoDocumento = :idTipoDocumento";
            sQuery +="AND to_char(f.fechaCreacion,'YYYYMM') = :fechaDesde  ";
			sQuery += "ORDER BY f.fechaCreacion ASC ";
			//-----------------------------------------------------
			obj = em.createQuery(sQuery);
			//----------------------------------------------------
			obj.setParameter("idTipoDocumento", Integer.parseInt(tipodocumento));
			obj.setParameter("idAreaPropietario", Integer.parseInt(area));
			obj.setParameter("fechaDesde", ano + (mes.length()==1? "0" + mes:mes));

		    return obj.getResultList();
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public List<ReporteAPN10> getListaReporteAPN10(String idAreaOrigen,
			String fechaDesde, String fechaHasta){

	 List<ReporteAPN10> l = null;

	 try{

		  Date fechaDesde_ = null;
		  Date fechaHasta_ = null;
		  SimpleDateFormat fechita = new SimpleDateFormat("dd/MM/yyyy");

		  try{
			  fechaDesde_= fechita.parse(fechaDesde);
	          fechaHasta_= fechita.parse(fechaHasta);
	      	  fechaHasta_.setHours(23);
			  fechaHasta_.setMinutes(59);
			  fechaHasta_.setSeconds(59);
		  }catch(Exception e){
			  e.printStackTrace();
		  }

		  String sQuery = "SELECT f FROM ReporteAPN10 f ";

		  sQuery += " WHERE f.idUnidadOrigen =" + idAreaOrigen;
		  sQuery +=  " and f.fechaCreacion>= :fechaDesde and f.fechaCreacion<= :fechaHasta order by fechacreacion desc";

		  Query obj = null;
		  obj = em.createQuery(sQuery);
		  obj.setParameter("fechaDesde", fechaDesde_);
		  obj.setParameter("fechaHasta", fechaHasta_);
		  l = obj.getResultList();
      }catch(Exception e){
    	  e.printStackTrace();
      }
		  return l;

	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public List<ReporteAPN1> getListaReporteAPN1(String idAreaOrigen,
			String idTipoDocumento, String idPrioridad,String fechaDesde, String fechaHasta, String idareadestino){//, String idareadestino) {
		    Query obj = null;
			Integer idAreaOrigen_=0;
			Integer idAreaDestino_=0;
			Integer idTipoDocumento_ =0;
			Date fechaDesde_ = null;
			Date fechaHasta_ = null;
			SimpleDateFormat fechita = new SimpleDateFormat("dd/MM/yyyy");
			String sQuery = "SELECT f FROM ReporteAPN1 f ";
			boolean flagWhere = false;

                     
			if(!((idAreaOrigen.toString().trim()).equals("Todos"))){
				idAreaOrigen_= Integer.parseInt(idAreaOrigen.toString().trim());
				sQuery += "WHERE f.idAreaOrigen like :idAreaOrigen ";
				flagWhere=true;
			}

                     
			if(!((idareadestino.toString().trim()).equals("Todos"))){
				idAreaDestino_= Integer.parseInt(idareadestino.toString().trim());
				if(flagWhere){
				   sQuery += " and f.idAreaDestino like :idAreaDestino ";
				}else{
					sQuery +="  WHERE f.idAreaDestino like :idAreaDestino ";
					flagWhere = true;
				}
			}

                         
			if(!((idTipoDocumento.toString().trim()).equals("Todos"))){
				idTipoDocumento_= Integer.parseInt(idTipoDocumento.toString().trim());
				if(flagWhere){
					sQuery +="AND f.idTipoDocumento like :idTipoDocumento ";
				}else{
					sQuery +="WHERE f.idTipoDocumento like :idTipoDocumento ";
					flagWhere = true;
				}
			}

			if(!((idPrioridad.toString().trim()).equals("Todos"))){
				if(flagWhere){
					sQuery +="AND f.idPrioridad like :idPrioridad ";
				}else{
					sQuery +="WHERE f.idPrioridad like :idPrioridad ";
					flagWhere = true;
				}
			}

			if(!((fechaDesde.toString().trim()).equals("Todos"))){
				try {
					fechaDesde_= fechita.parse(fechaDesde);

					fechaHasta_= fechita.parse(fechaHasta);
					fechaHasta_.setHours(24);
					fechaHasta_.setMinutes(0);
					fechaHasta_.setSeconds(0);

				} catch (ParseException e) {
					e.printStackTrace();
				}
				if(flagWhere){
					sQuery +="AND f.fechaCreacion >= :fechaDesde AND f.fechaCreacion <= :fechaHasta ";
				}else{
					sQuery +="WHERE f.fechaCreacion >= :fechaDesde AND f.fechaCreacion <= :fechaHasta ";
					flagWhere = true;
				}
			}

			sQuery += "ORDER BY f.fechaCreacion ASC ";
                      
			//-----------------------------------------------------
			obj = em.createQuery(sQuery);

			//-----------------------------------------------------
			if(!((idAreaOrigen.toString().trim()).equals("Todos"))){
				obj.setParameter("idAreaOrigen", idAreaOrigen_);
			}
			if(!((idTipoDocumento.toString().trim()).equals("Todos"))){
				obj.setParameter("idTipoDocumento", idTipoDocumento_);
			}
			if(!((idPrioridad.toString().trim()).equals("Todos"))){
				obj.setParameter("idPrioridad", idPrioridad);
			}
			if(!((fechaDesde.toString().trim()).equals("Todos"))){
				obj.setParameter("fechaDesde", fechaDesde_);
			}
			if(!((fechaHasta.toString().trim()).equals("Todos"))){
				obj.setParameter("fechaHasta", fechaHasta_);
			}

                        if(!((idareadestino.toString().trim()).equals("Todos"))){
				obj.setParameter("idAreaDestino", idAreaDestino_);
			}

                    
		    return obj.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<FilaReporteAPN2> getListaReporteAPN2(BusquedaAvanzada objFiltro){
		String sql = "SELECT f FROM FilaReporteAPN2 f WHERE 1=1 ";

		//1
		if(!StringUtils.isEmpty(objFiltro.getAreaOrigen()) && !objFiltro.getAreaOrigen().equals("0")){
			sql += "AND f.idAreaRegistro = :idAreaOrigen ";
		}
		//2
		if(!StringUtils.isEmpty(objFiltro.getAreaDestino()) && !objFiltro.getAreaDestino().equals("0")){
			sql += "AND f.idAreaDestino = :idAreaDestino ";
		}
		//3
		if(!StringUtils.isEmpty(objFiltro.getTipoDocumento()) && !objFiltro.getTipoDocumento().equals("0")){
			sql += "AND f.idTipoDocumento = :idTipoDocumento ";
		}
		//4
		if(!StringUtils.isEmpty(objFiltro.getGrupoProceso()) && !objFiltro.getGrupoProceso().equals("0")){
			sql += "AND f.idGrupoProceso = :idGrupoProceso ";
		}
		//5
		if(!StringUtils.isEmpty(objFiltro.getProceso()) && !objFiltro.getProceso().equals("0")){
			sql += "AND f.idProceso = :idProceso ";
		}
		//6
		if(!StringUtils.isEmpty(objFiltro.getFechaDesde())){
			sql += "AND f.fechaCreacion >= :fechaDesde ";
		}
		//7
		if(!StringUtils.isEmpty(objFiltro.getFechaHasta())){
			sql += "AND f.fechaCreacion <= :fechaHasta ";
		}

		sql += "ORDER BY f.fechaCreacion ASC ";
		Query q = em.createQuery(sql);

		//1
		if(!StringUtils.isEmpty(objFiltro.getAreaOrigen()) && !objFiltro.getAreaOrigen().equals("0")){
			q.setParameter("idAreaOrigen", Integer.parseInt(objFiltro.getAreaOrigen()));
		}
		//2
		if(!StringUtils.isEmpty(objFiltro.getAreaDestino()) && !objFiltro.getAreaDestino().equals("0")){
			q.setParameter("idAreaDestino", Integer.parseInt(objFiltro.getAreaDestino()));
		}
		//3
		if(!StringUtils.isEmpty(objFiltro.getTipoDocumento()) && !objFiltro.getTipoDocumento().equals("0")){
			q.setParameter("idTipoDocumento", Integer.parseInt(objFiltro.getTipoDocumento()));
		}
		//4
		if(!StringUtils.isEmpty(objFiltro.getGrupoProceso()) && !objFiltro.getGrupoProceso().equals("0")){
			q.setParameter("idGrupoProceso", Integer.parseInt(objFiltro.getGrupoProceso()));
		}
		//5
		if(!StringUtils.isEmpty(objFiltro.getProceso()) && !objFiltro.getProceso().equals("0")){
			q.setParameter("idProceso", Integer.parseInt(objFiltro.getProceso()));
		}
		//6
		if(!StringUtils.isEmpty(objFiltro.getFechaDesde())){
			try {
				SimpleDateFormat fechita = new SimpleDateFormat("yyyy-MM-dd");

				Calendar fechaDesde = Calendar.getInstance();
				fechaDesde.setTime(fechita.parse(objFiltro.getFechaDesde()));
				fechaDesde.set(Calendar.HOUR, 0);
				fechaDesde.set(Calendar.MINUTE, 0);
				fechaDesde.set(Calendar.SECOND, 0);

				q.setParameter("fechaDesde", fechaDesde.getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		//7
		if(!StringUtils.isEmpty(objFiltro.getFechaHasta())){
			try {
				SimpleDateFormat fechita = new SimpleDateFormat("yyyy-MM-dd");

				Calendar fechaHasta = Calendar.getInstance();
				fechaHasta.setTime(fechita.parse(objFiltro.getFechaHasta()));
				fechaHasta.set(Calendar.HOUR, 23);
				fechaHasta.set(Calendar.MINUTE, 59);
				fechaHasta.set(Calendar.SECOND, 59);

				q.setParameter("fechaHasta", fechaHasta.getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<NodoExpReporteAPN3> getListaNodosExpedienteReporteAPN3(BusquedaAvanzada objFiltro){

		List<NodoExpReporteAPN3> lista = new ArrayList<NodoExpReporteAPN3>();
        try{
        	String sql = "SELECT new Expediente(ex.id, ex.proceso, ex.nroexpediente, ex.cliente, ex.fechacreacion, (SELECT d.asunto FROM Documento d WHERE d.expediente.id = ex.id AND d.idDocumento = (SELECT MIN(dd.idDocumento) FROM Documento dd WHERE dd.expediente.id = ex.id))) FROM Expediente ex WHERE 1 = 1   ";

    		//1


    		if(!StringUtils.isEmpty(objFiltro.getProceso()) && !objFiltro.getProceso().equals("0")){
    			sql += "AND ex.proceso.idproceso = :idProceso ";
    		}

    		//2
    		if(!StringUtils.isEmpty(objFiltro.getFechaDesde())){
    			sql += "AND ex.fechacreacion >= :fechaDesde ";
    		}
    		//3
    		if(!StringUtils.isEmpty(objFiltro.getFechaHasta())){
    			sql += "AND ex.fechacreacion <= :fechaHasta ";
    		}

    		//4


    		if(!StringUtils.isEmpty(objFiltro.getCliente())){
    			//sql += "AND ex.cliente.idCliente = :idCliente ";
    			sql += " AND FNC_CLIENTE_EXISTS(" + Integer.parseInt(objFiltro.getCliente()) + ", ex.id) = 'VERDADERO' ";
    		}

    		sql += "ORDER BY ex.fechacreacion ASC ";
    		Query q = em.createQuery(sql);

    		//1
    		if(!StringUtils.isEmpty(objFiltro.getProceso()) && !objFiltro.getProceso().equals("0")){
    			q.setParameter("idProceso", Integer.parseInt(objFiltro.getProceso()));
    		}
    		//2

    		if(!StringUtils.isEmpty(objFiltro.getFechaDesde())){
    			try {
    				SimpleDateFormat fechita = new SimpleDateFormat("yyyy-MM-dd");

    				Calendar fechaDesde = Calendar.getInstance();
    				fechaDesde.setTime(fechita.parse(objFiltro.getFechaDesde()));
    				fechaDesde.set(Calendar.HOUR, 0);
    				fechaDesde.set(Calendar.MINUTE, 0);
    				fechaDesde.set(Calendar.SECOND, 0);

    				q.setParameter("fechaDesde", fechaDesde.getTime());
    			} catch (ParseException e) {
    				e.printStackTrace();
    			}
    		}
    		//3
    		if(!StringUtils.isEmpty(objFiltro.getFechaHasta())){
    			try {
    				SimpleDateFormat fechita = new SimpleDateFormat("yyyy-MM-dd");

    				Calendar fechaHasta = Calendar.getInstance();
    				fechaHasta.setTime(fechita.parse(objFiltro.getFechaHasta()));
    				fechaHasta.set(Calendar.HOUR, 23);
    				fechaHasta.set(Calendar.MINUTE, 59);
    				fechaHasta.set(Calendar.SECOND, 59);

    				q.setParameter("fechaHasta", fechaHasta.getTime());
    			} catch (ParseException e) {
    				e.printStackTrace();
    			}
    		}

    		//4  JBENGOA COMENTADO
    		/*if(!StringUtils.isEmpty(objFiltro.getCliente())){
    			q.setParameter("idCliente", Integer.parseInt(objFiltro.getCliente()));
    		}*/

    		List<Expediente> expedientes = (List<Expediente>)q.getResultList();
            String sql_ = null;
            Query q_ = null;

    		if(expedientes != null && !expedientes.isEmpty()){
    			for(Expediente expediente : expedientes){
    				sql_ = "select FNC_CLIENTE_DOC("+ expediente.getId() + ") from dual";
    				q_ = em.createNativeQuery(sql_);
    				NodoExpReporteAPN3 nodo = null;// new NodoExpReporteAPN3(expediente.getId(), expediente.getProceso().getTiempoatencion(), expediente.getNroexpediente(), q_.getResultList().get(0).toString(), expediente.getAsunto(), expediente.getProceso().getIdgrupoproceso().getIdgrupoproceso(), expediente.getFechacreacion());
    				lista.add(nodo);
    			}
    		}
        }catch(Exception ex){
        	ex.printStackTrace();
        }
		return lista;
	}



	@SuppressWarnings("unchecked")
	public List<NodoExpReporteAPN8> getListaNodosExpedienteReporteAPN8(BusquedaAvanzada objFiltro){

		List<NodoExpReporteAPN8> lista = new ArrayList<NodoExpReporteAPN8>();
        try{
        	String sql = "SELECT new Expediente(ex.id, ex.proceso, ex.nroexpediente, ex.cliente, ex.fechacreacion, (SELECT d.asunto FROM Documento d WHERE d.expediente.id = ex.id AND d.idDocumento = (SELECT MIN(dd.idDocumento) FROM Documento dd WHERE dd.expediente.id = ex.id))) FROM Expediente ex WHERE 1 = 1   ";

    		//1


    		//if(!StringUtils.isEmpty(objFiltro.getProceso()) && !objFiltro.getProceso().equals("0")){
    			sql += "AND ex.proceso.idproceso = 855 ";
    		//}

    		//2
    		if(!StringUtils.isEmpty(objFiltro.getFechaDesde())){
    			sql += "AND ex.fechacreacion >= :fechaDesde ";
    		}
    		//3
    		if(!StringUtils.isEmpty(objFiltro.getFechaHasta())){
    			sql += "AND ex.fechacreacion <= :fechaHasta ";
    		}

    		//4

    	//	System.out.println("ingreso al criterio existe111=......" + objFiltro.getCliente());

    		if(!StringUtils.isEmpty(objFiltro.getCliente())){
    			//sql += "AND ex.cliente.idCliente = :idCliente ";
    			sql += " AND FNC_CLIENTE_EXISTS(" + Integer.parseInt(objFiltro.getCliente()) + ", ex.id) = 'VERDADERO' ";
    		}

    		sql += "ORDER BY ex.fechacreacion ASC ";
    		Query q = em.createQuery(sql);

    		//1
    		//if(!StringUtils.isEmpty(objFiltro.getProceso()) && !objFiltro.getProceso().equals("0")){
    			//q.setParameter("idProceso", Integer.parseInt(objFiltro.getProceso()));
    		//}
    		//2



    		if(!StringUtils.isEmpty(objFiltro.getFechaDesde())){
    			try {
    				SimpleDateFormat fechita = new SimpleDateFormat("yyyy-MM-dd");

    				Calendar fechaDesde = Calendar.getInstance();
    				fechaDesde.setTime(fechita.parse(objFiltro.getFechaDesde()));
    				fechaDesde.set(Calendar.HOUR, 0);
    				fechaDesde.set(Calendar.MINUTE, 0);
    				fechaDesde.set(Calendar.SECOND, 0);

    				q.setParameter("fechaDesde", fechaDesde.getTime());
    			} catch (ParseException e) {
    				e.printStackTrace();
    			}
    		}


    		//3
    		if(!StringUtils.isEmpty(objFiltro.getFechaHasta())){
    			try {
    				SimpleDateFormat fechita = new SimpleDateFormat("yyyy-MM-dd");

    				Calendar fechaHasta = Calendar.getInstance();
    				fechaHasta.setTime(fechita.parse(objFiltro.getFechaHasta()));
    				fechaHasta.set(Calendar.HOUR, 23);
    				fechaHasta.set(Calendar.MINUTE, 59);
    				fechaHasta.set(Calendar.SECOND, 59);

    				q.setParameter("fechaHasta", fechaHasta.getTime());
    			} catch (ParseException e) {
    				e.printStackTrace();
    			}
    		}


    		//4  JBENGOA COMENTADO
    		/*if(!StringUtils.isEmpty(objFiltro.getCliente())){
    			q.setParameter("idCliente", Integer.parseInt(objFiltro.getCliente()));
    		}*/

    		List<Expediente> expedientes = (List<Expediente>)q.getResultList();
            String sql_ = null;
            Query q_ = null;

    		if(expedientes != null && !expedientes.isEmpty()){
    			for(Expediente expediente : expedientes){
    			//	if (expediente.getProceso().getIdproceso().toString().equals("855")){
	    				sql_ = "select FNC_CLIENTE_DOC("+ expediente.getId() + ") from dual";
	    				q_ = em.createNativeQuery(sql_);
	    				NodoExpReporteAPN8 nodo = null;// new NodoExpReporteAPN8(expediente.getId(), expediente.getProceso().getTiempoatencion(), expediente.getNroexpediente(), q_.getResultList().get(0).toString(), expediente.getAsunto(), expediente.getProceso().getIdgrupoproceso().getIdgrupoproceso(), expediente.getFechacreacion());
	    				lista.add(nodo);
    			//	}
    			}
    		}


        }catch(Exception ex){
        	ex.printStackTrace();
        }
		return lista;
	}


	@SuppressWarnings("unchecked")
	public List<NodoDocReporteAPN3> getListaNodosDocumentoReporteAPN3(Integer idExpediente){
		String sql = "SELECT n FROM NodoDocReporteAPN3 n WHERE n.idExpediente = :idExpediente";
		return em.createQuery(sql).setParameter("idExpediente", idExpediente).getResultList();
	}


	@SuppressWarnings("unchecked")
	public List<NodoDocReporteAPN8> getListaNodosDocumentoReporteAPN8(Integer idExpediente){
		String sql = "SELECT n FROM NodoDocReporteAPN8 n WHERE n.idExpediente = :idExpediente";
		return em.createQuery(sql).setParameter("idExpediente", idExpediente).getResultList();
	}



	@SuppressWarnings("unchecked")
	public List<TrazabilidadDocumentaria> getTramiteDocumentario(Integer grupo){
		String  sql =  "select  grupo, origen, destino, estado from trazabilidad_documentaria where grupo=" + grupo;

		//Query q = em.createNativeQuery(sql.toString());
		log.info("SQL:" + sql.toString());
		List<TrazabilidadDocumentaria> listObj =  null;
		List<Object> res = null;

		try{
			 res = (List<Object>) em.createNativeQuery(sql.toString()).getResultList();

			 if (res!=null && res.size()>0){
				     listObj =  new ArrayList<TrazabilidadDocumentaria>();

					 for (Object obj : res) {
						    TrazabilidadDocumentaria t = new TrazabilidadDocumentaria();
							Object[] objectArray = (Object[]) obj;
							Object object1 = objectArray[0];
							Object object2 = objectArray[1];
							Object object3 = objectArray[2];
							Object object4 = objectArray[3];

							t.setOrigen(object2==null?"":object2.toString());
						    t.setDestino(object3==null?"":object3.toString());
						    t.setAccion(object4==null?"":object4.toString());
							listObj.add(t);
					 }
			 }



		}catch(Exception e){
			e.printStackTrace();
			listObj = null;
		}

		return listObj;
	}

        public List<FilaHojaFirma> getHojaFirma(Integer idDocumento){
           List<FilaHojaFirma> dataforward=new ArrayList<FilaHojaFirma>();
           try{ 
            StringBuilder sql=new StringBuilder();
            sql.append(" SELECT * FROM (");
            sql.append("SELECT SUBSTR(A.NOMBRE, INSTR(A.nombre,']') +1, LENGTH(A.NOMBRE)), ");
            sql.append(" (SELECT us.nombres || ' '    || us.apellidos  FROM usuario us WHERE  ");
            sql.append(" us.idusuario = F.IDUSUARIO) || ' <br/>Area:'");
            sql.append("       || (SELECT u.nombre     FROM unidad u   ");
            sql.append("           WHERE u.idunidad = F.unidadPropietario), F.fechaCreacion, F.alias, F.fechaFirma, F.accion, A.principal  ");
	    sql.append(" FROM ARCHIVO A, FIRMAARCHIVO F WHERE A.DOCUMENTO =  ");
            sql.append(idDocumento);
            sql.append(" and A.ESTADO = 'A' AND A.PRINCIPAL='S' AND A.IDARCHIVO = F.IDARCHIVO AND F.ESTADO = 'F'  ");
            sql.append(" UNION ");
            sql.append("SELECT SUBSTR(A.NOMBRE, INSTR(A.nombre,']') +1, LENGTH(A.NOMBRE)), ");
            sql.append(" (SELECT us.nombres || ' '    || us.apellidos  FROM usuario us WHERE  ");
            sql.append(" us.idusuario = F.IDUSUARIO) || ' <br/>Area:'");
            sql.append("       || (SELECT u.nombre     FROM unidad u   ");
            sql.append("           WHERE u.idunidad = F.unidadPropietario), F.fechaCreacion, F.alias, F.fechaFirma, F.accion, A.principal  ");
	    sql.append(" FROM ARCHIVO A, FIRMAARCHIVO F WHERE A.DOCUMENTO =  ");
            sql.append(idDocumento);
            sql.append(" and A.ESTADO = 'A' AND A.PRINCIPAL = 'N' AND A.IDARCHIVO = F.IDARCHIVO AND F.ESTADO = 'F'  ");
            sql.append(" UNION ");
            sql.append("SELECT SUBSTR(A.NOMBRE, INSTR(A.nombre,']') +1, LENGTH(A.NOMBRE)), ");
            sql.append(" (SELECT us.nombres || ' '    || us.apellidos  FROM usuario us WHERE  ");
            sql.append(" us.idusuario = F.IDUSUARIO) || ' <br/>Area:'");
            sql.append("       || (SELECT u.nombre     FROM unidad u   ");
            sql.append("           WHERE u.idunidad = F.unidadPropietario), F.fechaCreacion, F.alias, F.fechaFirma, F.accion, A.principal  ");
	    sql.append(" FROM ARCHIVO A, FIRMAARCHIVO F WHERE A.DOCUMENTO =  ");
            sql.append(idDocumento);
            sql.append(" and A.ESTADO = 'A' AND A.PRINCIPAL = 'M' AND A.IDARCHIVO = F.IDARCHIVO AND F.ESTADO = 'F'  ");
            sql.append(" ) ORDER BY  to_char(fechacreacion,'YYYYMMDDHH24MI') desc, principal desc");
            Query q=em.createNativeQuery(sql.toString());
            List data=q.getResultList();
	    
	    for(int i=0;i<data.size();i++){
	       try{
                   FilaHojaFirma filaHojaFirma = new FilaHojaFirma();
		   Object obj[]=(Object[])data.get(i);   
                   SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                   String nombreFile = String.valueOf(obj[0]);
		   String remitente = String.valueOf(obj[1]);
                   Date fechaCreacion = formato.parse(obj[2].toString());
                   String alias = String.valueOf(obj[3]);
                   Date fechaFirma = formato.parse(obj[4].toString());
		   filaHojaFirma.setNombreFile(nombreFile);
                   filaHojaFirma.setUsuario(remitente);
                   filaHojaFirma.setFechaCreacion(fechaCreacion);
                   filaHojaFirma.setAlias(alias);
                   filaHojaFirma.setFechaFirma(fechaFirma);
                   filaHojaFirma.setTipo(obj[5]==null?"":String.valueOf(obj[5]));
                   filaHojaFirma.setPrincipal(obj[6]==null?"N":String.valueOf(obj[6]));
                   dataforward.add(filaHojaFirma);
               }catch(Exception e){
                   e.printStackTrace();
               }     
            }
           }catch(Exception e){
               e.printStackTrace();
           } 
            return dataforward;
        }
        
	@SuppressWarnings("unchecked")
	public List<FilaHojaRuta> getHojaRuta(Integer idDocumento){
                 StringBuilder sql=new StringBuilder();
		 sql.append("SELECT   ID, NRODOCUMENTO, FECHACREACION, REMITENTE, ");
		 sql.append("        DESTINATARIO, ACCION, CONTENIDO, DOCUMENTO, TIPO,     ");
		 sql.append("           PROVEIDO, ESTADO, IDREMITENTE, IDUNIDADREMITENTE ");
		 sql.append("      FROM (SELECT td.idtrazabilidaddocumento AS ID,                             ");
		 sql.append("                   (SELECT    (SELECT td.nombre                                  ");
		 sql.append("                                 FROM tipodocumento td                           ");
		 sql.append("                                WHERE td.idtipodocumento =                       ");
		 sql.append("                                             d.tipodocumento)                    ");
		 sql.append("                           || ' - '                                              ");
		 sql.append("                           || d.nrodocumento                                     ");
		 sql.append("                      FROM documento d                                           ");
		 sql.append("                     WHERE d.iddocumento = td.documento) AS nrodocumento,        ");
		 sql.append("                   td.fechacreacion,                                             ");
		 sql.append("                      ((SELECT us.nombres || ' '                                 ");
		 sql.append("                               || us.apellidos                                   ");
		 sql.append("                          FROM usuario us                                        ");
		 sql.append("                         WHERE us.idusuario = td.remitente) || ' [' ||           ");
                 
                 sql.append("                       (SELECT us.USUARIO                                        ");
		 sql.append("                          FROM usuario us                                        ");
		 sql.append("                         WHERE us.idusuario = td.usuariocreacion)) ||    '] '     ");
                 
                 
		 sql.append("                   || ' <br/>Area: '                                             ");
		 sql.append("                   || (SELECT u.nombre                                           ");
		 sql.append("                         FROM unidad u                                           ");
		 sql.append("                        WHERE u.idunidad = td.unidadremitente)                   ");
		 sql.append("                                                                AS remitente,    ");
		 sql.append("                      ((SELECT    us.nombres                                     ");
		 sql.append("                               || ' '                                            ");
		 sql.append("                               || us.apellidos                                   ");
		 sql.append("                          FROM usuario us                                        ");
		 sql.append("                         WHERE us.idusuario = td.destinatario))                  ");
		 sql.append("                   || ' <br/>Area: '                                             ");
		 sql.append("                   || (SELECT u.nombre                                           ");
		 sql.append("                         FROM unidad u                                           ");
		 sql.append("                        WHERE u.idunidad = td.unidaddestinatario)                ");
		 sql.append("                                                             AS destinatario,    ");
		 sql.append("                   (SELECT ac.descripcion                                        ");
		 sql.append("                      FROM accion ac                                             ");
		 sql.append("                     WHERE ac.idaccion = td.accion) AS accion, TO_CHAR(td.contenido) as contenido,    ");
		 sql.append("                   td.documento, 'Transferencia' AS tipo,                        ");
		// sql.append("                   td.nombrepc AS nombrepc,                                      ");
         sql.append("nvl((select p.nombre from proveido p where p.idproveido = td.idproveido),'') proveido,");        
		 sql.append("                   (SELECT CASE (d.estado)                                       ");
		 sql.append("                              WHEN 'A'                                           ");
		 sql.append("                                 THEN 'Registrado'                               ");
		 sql.append("                              WHEN 'C'                                           ");
		 sql.append("                                 THEN 'Archivado'                                ");
         sql.append("                              WHEN 'P'                                           ");
		 sql.append("                                 THEN 'Pendiente'                                ");
         sql.append("                              WHEN 'T'                                           ");
		 sql.append("                                 THEN 'Atendido'                                ");
		 sql.append("                              WHEN 'N'                                           ");
		 sql.append("                                 THEN 'Anulado'                                  ");
		 sql.append("                              WHEN 'I'                                           ");
		 sql.append("                                 THEN 'Inactivo'                                 ");
		 sql.append("                              ELSE '-'                                           ");
		 sql.append("                           END                                                   ");
		 sql.append("                      FROM documento d                                           ");
		 sql.append("                     WHERE td.documento = d.iddocumento) AS estado,              ");
		 sql.append("                   1 opcion ,td.remitente as idremitente ,  td.unidadremitente as idunidadremitente       ");
		 sql.append("              FROM trazabilidaddocumento td where td.documento=                  ");
		 sql.append(idDocumento);
		 sql.append("            UNION ALL                                                            ");
		 sql.append("            SELECT ta.idtrazabilidadapoyo AS ID,                                 ");
		 sql.append("                   (SELECT    (SELECT td.nombre                                  ");
		 sql.append("                                 FROM tipodocumento td                           ");
		 sql.append("                                WHERE td.idtipodocumento =                       ");
		 sql.append("                                             d.tipodocumento)                    ");
		 sql.append("                           || ' - '                                              ");
		 sql.append("                           || d.nrodocumento                                     ");
		 sql.append("                      FROM documento d                                           ");
		 sql.append("                     WHERE d.iddocumento =                                       ");
		 sql.append("                                       (SELECT dc.documentoreferencia            ");
		 sql.append("                                          FROM documento dc                      ");
		 sql.append("                                         WHERE dc.iddocumento = ta.documento))   ");
		 sql.append("                                                             AS nrodocumento,    ");
		 sql.append("                   ta.fechacreacion,                                             ");
		 sql.append("                      ((SELECT us.nombres || ' '                                 ");
		 sql.append("                               || us.apellidos                                   ");
		 sql.append("                          FROM usuario us                                        ");
		 sql.append("                         WHERE us.idusuario = ta.remitente) || ' [' ||           ");
                 
          sql.append("                       (SELECT us.USUARIO                                        ");
		 sql.append("                          FROM usuario us                                        ");
		 sql.append("                         WHERE us.idusuario = ta.usuariocreacion)) ||    '] '     ");
                 
                 
		 sql.append("                   || ' <br/>Area: '                                             ");
		 sql.append("                   || (SELECT u.nombre                                           ");
		 sql.append("                         FROM unidad u                                           ");
		 sql.append("                        WHERE u.idunidad = ta.unidadremitente)                   ");
		 sql.append("                                                                AS remitente,    ");
		 sql.append("                      ((SELECT    us.nombres                                     ");
		 sql.append("                               || ' '                                            ");
		 sql.append("                               || us.apellidos                                   ");
		 sql.append("                          FROM usuario us                                        ");
		 sql.append("                         WHERE us.idusuario = ta.destinatario))                  ");
		 sql.append("                   || ' <br/>Area: '                                             ");
		 sql.append("                   || (SELECT u.nombre                                           ");
		 sql.append("                         FROM unidad u                                           ");
		 sql.append("                        WHERE u.idunidad = ta.unidaddestinatario)                ");
		 sql.append("                                                             AS destinatario,    ");
		 sql.append("                   (SELECT ac.descripcion                                        ");
		 sql.append("                      FROM accion ac                                             ");
		 sql.append("                     WHERE ac.idaccion = ta.accion) AS accion, ta.texto,         ");
		 sql.append("                   (SELECT d.documentoreferencia                                 ");
		 sql.append("                      FROM documento d                                           ");
		 sql.append("                     WHERE d.iddocumento = ta.documento),                        ");
		 sql.append("                   'Envio Multiple' AS tipo,                                     ");
         sql.append("nvl((select p.nombre from proveido p where p.idproveido= ta.idproveido),'') proveido,");                    
		 sql.append("                   (SELECT e.descripcion                                         ");
		 sql.append("                      FROM estado e                                              ");
		 sql.append("                     WHERE e.idestado = ta.estado) AS estado, 2 ,ta.remitente as idremitente,  ta.unidadremitente as idunidadremitente ");
		 sql.append("              FROM trazabilidadapoyo ta                                          ");
		 sql.append("            UNION ALL                                                            ");
		 sql.append("            SELECT tc.idtrazabilidadcopia AS ID,                                 ");
		 sql.append("                   (SELECT    (SELECT td.nombre                                  ");
		 sql.append("                                 FROM tipodocumento td                           ");
		 sql.append("                                WHERE td.idtipodocumento =                       ");
		 sql.append("                                             d.tipodocumento)                    ");
		 sql.append("                           || ' - '                                              ");
		 sql.append("                           || d.nrodocumento                                     ");
		 sql.append("                      FROM documento d                                           ");
		 sql.append("                     WHERE d.iddocumento = tc.documento) AS nrodocumento,        ");
		 sql.append("                   tc.fechacreacion,                                             ");
		 sql.append("                      ((SELECT us.nombres || ' '                                 ");
		 sql.append("                               || us.apellidos                                   ");
		 sql.append("                          FROM usuario us                                        ");
		 sql.append("                         WHERE us.idusuario = tc.remitente) || ' [' ||           ");
                 
                 
         sql.append("                       (SELECT us.USUARIO                                        ");
		 sql.append("                          FROM usuario us                                        ");
		 sql.append("                         WHERE us.idusuario = tc.usuariocreacion)) ||    '] '     ");
                 
                 
		 sql.append("                   || ' <br/>Area: '                                             ");
		 sql.append("                   || (SELECT u.nombre                                           ");
		 sql.append("                         FROM unidad u                                           ");
		 sql.append("                        WHERE u.idunidad = tc.unidadremitente)                   ");
		 sql.append("                                                                AS remitente,    ");
		 sql.append("                      ((SELECT    us.nombres                                     ");
		 sql.append("                               || ' '                                            ");
		 sql.append("                               || us.apellidos                                   ");
		 sql.append("                          FROM usuario us                                        ");
		 sql.append("                         WHERE us.idusuario = tc.destinatario))                  ");
		 sql.append("                   || ' <br/>Area: '                                             ");
		 sql.append("                   || (SELECT u.nombre                                           ");
		 sql.append("                         FROM unidad u                                           ");
		 sql.append("                        WHERE u.idunidad =  tc.unidaddestinatario)               ");
		 sql.append("                                                             AS destinatario,    ");
		 sql.append("                   (SELECT ac.descripcion                                        ");
		 sql.append("                      FROM accion ac                                             ");
		 sql.append("                     WHERE ac.idaccion = tc.accion) AS accion,                   ");
		 sql.append("                   (SELECT nc.contenido                                          ");
		 sql.append("                      FROM notificacion nc                                       ");
		 sql.append("                     WHERE nc.idnotificacion = tc.idnotificacion),               ");
		 sql.append("                   tc.documento, 'Copia' AS tipo, '' proveido,                   ");
		 sql.append("                   (SELECT CASE (d.estado)                                       ");
		 sql.append("                              WHEN 'A'                                           ");
		 sql.append("                                 THEN 'Registrado'                               ");
		 sql.append("                              WHEN 'C'                                           ");
		 sql.append("                                 THEN 'Archivado'                                ");
                 sql.append("                              WHEN 'P'                                           ");
		 sql.append("                                 THEN 'Pendiente'                                ");
                 sql.append("                              WHEN 'T'                                           ");
		 sql.append("                                 THEN 'Atendido'                                 ");
		 sql.append("                              WHEN 'N'                                           ");
		 sql.append("                                 THEN 'Anulado'                                  ");
		 sql.append("                              WHEN 'I'                                           ");
		 sql.append("                                 THEN 'Inactivo'                                 ");
		 sql.append("                              ELSE '-'                                           ");
		 sql.append("                           END                                                   ");
		 sql.append("                      FROM documento d                                           ");
		 sql.append("                     WHERE tc.documento = d.iddocumento) AS estado,              ");
		 sql.append("                   3  , tc.remitente idremitente,  tc.unidadremitente as idunidadremitente ");
		 sql.append("              FROM trazabilidadcopia tc where tc.documento =                     ");
		 sql.append(idDocumento);
                 sql.append("            UNION ALL                                                            ");
                 sql.append("            SELECT tc.idtrazabilidadcopia AS ID,                                 ");
		 sql.append("                   (SELECT    (SELECT td.nombre                                  ");
		 sql.append("                                 FROM tipodocumento td                           ");
		 sql.append("                                WHERE td.idtipodocumento =                       ");
		 sql.append("                                             d.tipodocumento)                    ");
		 sql.append("                           || ' - '                                              ");
		 sql.append("                           || d.nrodocumento                                     ");
		 sql.append("                      FROM documento d                                           ");
		 sql.append("                     WHERE d.iddocumento = tc.documento) AS nrodocumento,        ");
		 sql.append("                   tc.fechacreacion,                                             ");
		 sql.append("                      ((SELECT us.nombres || ' '                                 ");
		 sql.append("                               || us.apellidos                                   ");
		 sql.append("                          FROM usuario us                                        ");
		 sql.append("                         WHERE us.idusuario = tc.remitente)  || ' [' ||          ");
                 
                 
                 sql.append("                       (SELECT us.USUARIO                                        ");
		 sql.append("                          FROM usuario us                                        ");
		 sql.append("                         WHERE us.idusuario = tc.usuariocreacion)) ||    '] '     ");
                 
                 
		 sql.append("                   || ' <br/>Area: '                                             ");
		 sql.append("                   || (SELECT u.nombre                                           ");
		 sql.append("                         FROM unidad u                                           ");
		 sql.append("                        WHERE u.idunidad = tc.unidadremitente)                   ");
		 sql.append("                                                                AS remitente,    ");
		 sql.append("                      ((SELECT    us.nombres                                     ");
		 sql.append("                               || ' '                                            ");
		 sql.append("                               || us.apellidos                                   ");
		 sql.append("                          FROM usuario us                                        ");
		 sql.append("                         WHERE us.idusuario = tc.destinatario))                  ");
		 sql.append("                   || ' <br/>Area: '                                             ");
		 sql.append("                   || (SELECT u.nombre                                           ");
		 sql.append("                         FROM unidad u                                           ");
		 sql.append("                        WHERE u.idunidad =  tc.unidaddestinatario)               ");
		 sql.append("                                                             AS destinatario,    ");
		 sql.append("                   (SELECT ac.descripcion                                        ");
		 sql.append("                      FROM accion ac                                             ");
		 sql.append("                     WHERE ac.idaccion = tc.accion) AS accion,                   ");
		 sql.append("                   (SELECT nc.contenido                                          ");
		 sql.append("                      FROM notificacion nc                                       ");
		 sql.append("                     WHERE nc.idnotificacion = tc.idnotificacion),               ");
                 sql.append("                     d1.documentoreferencia,                                     ");
		 sql.append("                    'Copia' AS tipo, '' proveido ,                    ");
		 sql.append("                   (SELECT CASE (d.estado)                                       ");
		 sql.append("                              WHEN 'A'                                           ");
		 sql.append("                                 THEN 'Registrado'                               ");
		 sql.append("                              WHEN 'P'                                           ");
		 sql.append("                                 THEN 'Pendiente'                                ");
                 sql.append("                              WHEN 'C'                                           ");
		 sql.append("                                 THEN 'Archivado'                                ");
                 sql.append("                              WHEN 'T'                                           ");
		 sql.append("                                 THEN 'Atendido'                                 ");
		 sql.append("                              WHEN 'N'                                           ");
		 sql.append("                                 THEN 'Anulado'                                  ");
		 sql.append("                              WHEN 'I'                                           ");
		 sql.append("                                 THEN 'Inactivo'                                 ");
		 sql.append("                              ELSE '-'                                           ");
		 sql.append("                           END                                                   ");
		 sql.append("                      FROM documento d                                           ");
		 sql.append("                     WHERE tc.documento = d.iddocumento) AS estado,              ");
		 sql.append("                   3     ,tc.remitente idremitente,  tc.unidadremitente as idunidadremitente                      ");
		 sql.append("              FROM trazabilidadcopia tc, documento d1 where tc.documento = d1.iddocumento and d1.documentoreferencia is not null and d1.documentoreferencia = ").append(idDocumento);
                 
                  
                  //jbengoa fin
		 sql.append(") ");
		 sql.append("  where documento= ");
		 sql.append(idDocumento);
		 sql.append(" and estado not in ('Inactivo')");
		 sql.append("  ORDER BY fechacreacion, opcion");
		 
                 
         Query q=em.createNativeQuery(sql.toString());
		 List data=q.getResultList();
		 List<FilaHojaRuta> dataforward=new ArrayList<FilaHojaRuta>();
                 
		 for(int i=0;i<data.size();i++){
		   try{
			Object obj[]=(Object[])data.get(i);
		        Integer id=Integer.parseInt(obj[0].toString());
		        String nroDocumento = 	String.valueOf(obj[1]);
                SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date fechaCreacion = formato.parse(obj[2].toString());
		        String remitente = String.valueOf(obj[3]);
		        String destinatario =  String.valueOf(obj[4]);
		        String accion = String.valueOf(obj[5]);
		        String contenido = String.valueOf(obj[6] == null?"":obj[6]);
		        String documento = String.valueOf(obj[7]);
		        String tipo = String.valueOf(obj[8]);
		        String proveido = String.valueOf(obj[9] == null?"":obj[9]);
		        String estado = String.valueOf(obj[10]);
                String idremitente = String.valueOf(obj[11]);
                String idunidadremitente = String.valueOf(obj[12]);
                        
		        FilaHojaRuta f = new FilaHojaRuta();
		        FilaHojaRutaPK pk = new FilaHojaRutaPK();
		        f.setNumeroDocumento(nroDocumento);
		        f.setFechaCreacion(fechaCreacion);
		        f.setRemitente(remitente.equals("null")?"":remitente);
		        f.setDestinatario(destinatario.equals("null")?"":destinatario);
		        f.setAccion(accion.equals("null")?"":accion);
		        f.setContenido(contenido.equals("null")?"":contenido);
		        f.setDocumento(Integer.parseInt(documento));
                        f.setIdremitente(idremitente.equals("null")?"":idremitente);
                        f.setIdunidadremitente(idunidadremitente.equals("null")?"":idunidadremitente);
                        //f.setNombrePC(nombrePC.equals("null")?"":nombrePC);
                        f.setProveido(proveido.equals("null")?"":proveido);
                        f.setEstado(estado);
                        
                        pk.setTipo(tipo);
                        pk.setId(id);
                        f.setPk(pk);
                        dataforward.add(f);
		     }catch(Exception ex){
		        ex.printStackTrace();
		     }
		   }
                 
                   String temporal = "";
                   String dato = "";
                   int contador = 1;
                   int indice = 0;
                   
                   for(int i=0;i<dataforward.size();i++){
                        dato = dataforward.get(i).getIdremitente() + "-" + dataforward.get(i).getIdunidadremitente();
                        if (!dato.equals(temporal)){
                            if (!temporal.equals(""))
                                dataforward.get(indice).setCantidadhoja(contador+"");
                            
                            temporal = dato;
                            contador = 1;
                            indice = i;
                        }else{
                          contador ++;
                        }
                   }
                   
                   dataforward.get(indice).setCantidadhoja(contador+"");
                   
		   return dataforward;
	}

	@SuppressWarnings("unchecked")
	public List<NodoExpReporteAPN4> getListaReporteAPN4(BusquedaAvanzada objFiltro){
		 StringBuilder sql=new StringBuilder();
		 sql.append("select * from( ");
		 sql.append("select (select t.nombre from tipodocumento t  ");
		 sql.append("                where t.idtipodocumento = d.tipodocumento) tipodocumento,  ");
		 sql.append("        nrodocumento,    ");
		 sql.append("       d.asunto,   ");
		 sql.append("       (select nroexpediente from expediente e     ");
		 sql.append("               where e.idexpediente = d.expediente) nroexpediente,    ");
		 sql.append("       CAST(t.fechacreacion AS DATE) fecharecepcion,      ");
		 sql.append("       (select u.nombre from unidad u, usuario s        ");
		 sql.append("               where u.idunidad = s.idunidad and s.idusuario = t.remitente) unidad,  ");
		 sql.append("       (select a.descripcion from accion a where a.idaccion = t.accion) accion,  ");
		 sql.append("       (select t1.contenido from trazabilidaddocumento t1  ");
		 sql.append("               where t1.documento = d.iddocumento and t1.nroregistro = t.nroregistro + 1  AND rownum<2) proveido, ");
		 sql.append("       (select a1.descripcion from trazabilidaddocumento t1, accion a1 ");
		 sql.append("               where t1.documento = d.iddocumento and t1.nroregistro = t.nroregistro + 1   ");
		 sql.append("               and   t1.accion = a1.idaccion and rownum<2) accionprov,      ");
		 sql.append("       (select trim(u1.nombres || ' ' || u1.apellidos) from trazabilidaddocumento t1, usuario u1   ");
		 sql.append("               where t1.documento = d.iddocumento and t1.nroregistro = t.nroregistro + 1   ");
		 sql.append("               and   t1.destinatario = u1.idusuario and rownum<2) remitido,    ");
		 sql.append("        (select nrodocumento from documento d2     ");
		 sql.append("               where d2.iddocumento = (select min(iddocumento) from documento dt  ");
		 sql.append("                                  where dt.expediente = d.expediente   ");
		 sql.append("                                  and   dt.iddocumento > d.iddocumento  ");
		 sql.append("                                  and   dt.autor = t.destinatario)) documentorespuesta  ");
		 sql.append("from trazabilidaddocumento t,       ");
		 sql.append("     documento d    ");
		 sql.append("where t.destinatario = ");
		 sql.append(objFiltro.getUsuarioDestinatario());
		 sql.append(" and   to_char(t.fechacreacion,'YYYYMMDD')>='");
		 sql.append(objFiltro.getFechaDesde() + "' ");
		 sql.append(" and   to_char(t.fechacreacion,'YYYYMMDD')<='");
		 sql.append(objFiltro.getFechaHasta() + "' ");
		 sql.append(" and   t.remitente <> t.destinatario  ");
		 sql.append(" and   d.iddocumento = t.documento  ");
		 sql.append(" union        ");
		 sql.append("select (select t.nombre from tipodocumento t  ");
		 sql.append("                where t.idtipodocumento = d.tipodocumento) tipodocumento,   ");
		 sql.append("        nrodocumento,    ");
		 sql.append("       d.asunto,  ");
		 sql.append("       (select nroexpediente from expediente e     ");
		 sql.append("               where e.idexpediente = d.expediente) nroexpediente,  ");
		 sql.append("       CAST(t.fechacreacion AS DATE),  ");
		 sql.append("       (select u.nombre from unidad u, usuario s  ");
		 sql.append("               where u.idunidad = s.idunidad and s.idusuario = t.remitente) unidad,   ");
		 sql.append("       (select a.descripcion from accion a where a.idaccion = t.accion) accion,  ");
		 sql.append("       (select texto from trazabilidadapoyo y1   ");
		 sql.append("       where y1.idtrazabilidadapoyo = decode(    ");
		 sql.append("                                 (select min(idtrazabilidadapoyo)     ");
		 sql.append("                                  from trazabilidadapoyo y, documento n     ");
		 sql.append("                                  where y.documento = n.iddocumento     ");
		 sql.append("                                  and   n.documentoreferencia = d.documentoreferencia    ");
		 sql.append("                                  and   n.iddocumento > d.iddocumento     ");
		 sql.append("                                  and   y.remitente = d.propietario), null,    ");
		 sql.append("                                 (select max(idtrazabilidadapoyo)      ");
		 sql.append("                                  from trazabilidadapoyo y, documento n    ");
		 sql.append("                                  where y.documento = n.iddocumento     ");
		 sql.append("                                  and   n.documentoreferencia = d.documentoreferencia    ");
		 sql.append("                                  and   n.iddocumento = d.iddocumento   ");
		 sql.append("                                  and   y.remitente = d.propietario),  ");
		 sql.append("                                 (select min(idtrazabilidadapoyo)  ");
		 sql.append("                                  from trazabilidadapoyo y, documento n    ");
		 sql.append("                                  where y.documento = n.iddocumento        ");
		 sql.append("                                  and   n.documentoreferencia = d.documentoreferencia   ");
		 sql.append("                                  and   n.iddocumento > d.iddocumento      ");
		 sql.append("                                  and   y.remitente = d.propietario ))) c_asunto,    ");
		 sql.append("       (select a.descripcion from trazabilidadapoyo y1, accion a      ");
		 sql.append("       where y1.idtrazabilidadapoyo = decode(   ");
		 sql.append("                                 (select min(idtrazabilidadapoyo)         ");
		 sql.append("                                  from trazabilidadapoyo y, documento n      ");
		 sql.append("                                  where y.documento = n.iddocumento       ");
		 sql.append("                                  and   n.documentoreferencia = d.documentoreferencia   ");
		 sql.append("                                  and   n.iddocumento > d.iddocumento        ");
		 sql.append("                                  and   y.remitente = d.propietario), null,      ");
		 sql.append("                                 (select max(idtrazabilidadapoyo)              ");
		 sql.append("                                  from trazabilidadapoyo y, documento n       ");
		 sql.append("                                  where y.documento = n.iddocumento         ");
		 sql.append("                                  and   n.documentoreferencia = d.documentoreferencia            ");
		 sql.append("                                  and   n.iddocumento = d.iddocumento           ");
		 sql.append("                                  and   y.remitente = d.propietario),            ");
		 sql.append("                                 (select min(idtrazabilidadapoyo)          ");
		 sql.append("                                  from trazabilidadapoyo y, documento n   ");
		 sql.append("                                  where y.documento = n.iddocumento    ");
		 sql.append("                                  and   n.documentoreferencia = d.documentoreferencia   ");
		 sql.append("                                  and   n.iddocumento > d.iddocumento  ");
		 sql.append("                                  and   y.remitente = d.propietario ))    ");
		 sql.append("       and    y1.accion = a.idaccion) accionprov,       ");
		 sql.append("        (select trim(u1.nombres || ' ' || u1.apellidos) from trazabilidadapoyo y1, usuario u1     ");
		 sql.append("       where y1.idtrazabilidadapoyo = (      ");
		 sql.append("                                  select min(idtrazabilidadapoyo)     ");
		 sql.append("                                  from trazabilidadapoyo y, documento n        ");
		 sql.append("                                  where y.documento = n.iddocumento    ");
		 sql.append("                                  and   n.documentoreferencia = d.documentoreferencia   ");
		 sql.append("                                  and   n.iddocumento > d.iddocumento    ");
		 sql.append("                                  and   y.remitente = d.propietario )      ");
		 sql.append("        and    y1.destinatario = u1.idusuario) remitido,    ");
		 sql.append("        (select nrodocumento from documento d2    ");
		 sql.append("               where d2.iddocumento = (select min(iddocumento) from documento dt ");
		 sql.append("                                  where dt.expediente = d.expediente  ");
		 sql.append("                                  and   dt.iddocumento > d.iddocumento  ");
		 sql.append("                                  and   dt.autor = t.destinatario  ");
		 sql.append("                                  and   dt.nrodocumento <> d.nrodocumento)) documentorespuesta   ");
		 sql.append("from trazabilidadapoyo t, ");
		 sql.append("     documento d  ");
		 sql.append("where t.destinatario =  ");
		 sql.append(objFiltro.getUsuarioDestinatario());
		 sql.append(" and   to_char(t.fechacreacion,'YYYYMMDD')>='");
		 sql.append(objFiltro.getFechaDesde() + "' ");
		 sql.append(" and   to_char(t.fechacreacion,'YYYYMMDD')<='");
		 sql.append(objFiltro.getFechaHasta() + "' ");
		 sql.append(" and   t.remitente <> t.destinatario ");
		 sql.append(" and   d.iddocumento = t.documento ");
		 sql.append(" and   t.accion <> 32  ");

		 sql.append(" union        ");
		 sql.append("select (select t.nombre from tipodocumento t  ");
		 sql.append("                where t.idtipodocumento = d.tipodocumento) tipodocumento,   ");
		 sql.append("        nrodocumento,    ");
		 sql.append("       d.asunto,  ");
		 sql.append("       (select nroexpediente from expediente e     ");
		 sql.append("               where e.idexpediente = d.expediente) nroexpediente,  ");
		 sql.append("       CAST(t.fechacreacion AS DATE),  ");
		 sql.append("       (select u.nombre from unidad u, usuario s  ");
		 sql.append("               where u.idunidad = s.idunidad and s.idusuario = t.remitente) unidad,   ");
		 sql.append("       (select a.descripcion from accion a where a.idaccion = t.accion) accion,  ");
		 sql.append("       (select decode(y1.contenido,null,'',y1.contenido) from notificacion y1   ");
		 sql.append("       where y1.idnotificacion = t.idnotificacion) c_asunto,    ");
		 sql.append("       (select a.descripcion from trazabilidadcopia y1, accion a      ");
		 sql.append("       where y1.idtrazabilidadcopia = decode(   ");
		 sql.append("                                 (select min(idtrazabilidadcopia)         ");
		 sql.append("                                  from trazabilidadcopia y, documento n      ");
		 sql.append("                                  where y.documento = n.iddocumento       ");
		 sql.append("                                  and   n.documentoreferencia = d.documentoreferencia   ");
		 sql.append("                                  and   n.iddocumento > d.iddocumento        ");
		 sql.append("                                  and   y.remitente = d.propietario), null,      ");
		 sql.append("                                 (select max(idtrazabilidadcopia)              ");
		 sql.append("                                  from trazabilidadcopia y, documento n       ");
		 sql.append("                                  where y.documento = n.iddocumento         ");
		 sql.append("                                  and   n.documentoreferencia = d.documentoreferencia            ");
		 sql.append("                                  and   n.iddocumento = d.iddocumento           ");
		 sql.append("                                  and   y.remitente = d.propietario),            ");
		 sql.append("                                 (select min(idtrazabilidadcopia)          ");
		 sql.append("                                  from trazabilidadcopia y, documento n   ");
		 sql.append("                                  where y.documento = n.iddocumento    ");
		 sql.append("                                  and   n.documentoreferencia = d.documentoreferencia   ");
		 sql.append("                                  and   n.iddocumento > d.iddocumento  ");
		 sql.append("                                  and   y.remitente = d.propietario ))    ");
		 sql.append("       and    y1.accion = a.idaccion) accionprov,       ");
		 sql.append("        (select trim(u1.nombres || ' ' || u1.apellidos) from trazabilidadcopia y1, usuario u1     ");
		 sql.append("       where y1.idtrazabilidadcopia = (      ");
		 sql.append("                                  select min(idtrazabilidadcopia)     ");
		 sql.append("                                  from trazabilidadcopia y, documento n        ");
		 sql.append("                                  where y.documento = n.iddocumento    ");
		 sql.append("                                  and   n.documentoreferencia = d.documentoreferencia   ");
		 sql.append("                                  and   n.iddocumento > d.iddocumento    ");
		 sql.append("                                  and   y.remitente = d.propietario )      ");
		 sql.append("        and    y1.destinatario = u1.idusuario) remitido,    ");
		 sql.append("        (select nrodocumento from documento d2    ");
		 sql.append("               where d2.iddocumento = (select min(iddocumento) from documento dt ");
		 sql.append("                                  where dt.expediente = d.expediente  ");
		 sql.append("                                  and   dt.iddocumento > d.iddocumento  ");
		 sql.append("                                  and   dt.autor = t.destinatario  ");
		 sql.append("                                  and   dt.nrodocumento <> d.nrodocumento)) documentorespuesta   ");
		 sql.append("from trazabilidadcopia t, ");
		 sql.append("     documento d  ");
		 sql.append("where t.destinatario =  ");
		 sql.append(objFiltro.getUsuarioDestinatario());
		 sql.append(" and   to_char(t.fechacreacion,'YYYYMMDD')>='");
		 sql.append(objFiltro.getFechaDesde() + "' ");
		 sql.append(" and   to_char(t.fechacreacion,'YYYYMMDD')<='");
		 sql.append(objFiltro.getFechaHasta() + "' ");
		 sql.append(" and   t.remitente <> t.destinatario ");
		 sql.append(" and   d.iddocumento = t.documento ");
		 sql.append(" and   t.accion <> 32  ");
		 sql.append(" ) order by fecharecepcion asc ");

		 
		 Query q=em.createNativeQuery(sql.toString());
			List data=q.getResultList();
			List<NodoExpReporteAPN4> dataforward=new ArrayList<NodoExpReporteAPN4>();
			for(int i=0;i<data.size();i++){
			 try{
				Object obj[]=(Object[])data.get(i);
				String tipoDocumento = String.valueOf(obj[0]);
				String nroDocumento = String.valueOf(obj[1]);
				String asunto = String.valueOf(obj[2]);
				String expediente = String.valueOf(obj[3]);
				String fechaRecepcion = String.valueOf(obj[4]);
				String unidad = String.valueOf(obj[5]);
				String accion  = String.valueOf(obj[6]);
				String proveido = String.valueOf(obj[7]);
				String accionProveido = String.valueOf(obj[8]);
				String remitido = String.valueOf(obj[9]);
				String documentoRespuesta = String.valueOf(obj[10]);

				NodoExpReporteAPN4 f = new NodoExpReporteAPN4();
				f.setTipoDocumento(tipoDocumento);
				f.setNroDocumento(nroDocumento);
				f.setAsunto(asunto);
				f.setExpediente(expediente);
				f.setFechaRecepcion(fechaRecepcion);
				f.setUnidad(unidad);
				f.setAccion(accion);
				f.setProveido(proveido.equals("null")?"":proveido);
				f.setAccionProveido(accionProveido.equals("null")?"":accionProveido);
				f.setRemitido(remitido.equals("null")?"":remitido);
				f.setDocumentoRespuesta(documentoRespuesta.equals("null")?"":documentoRespuesta);
			    dataforward.add(f);
			  }catch(Exception ex){
				  ex.printStackTrace();

			  }

			}
			return dataforward;

	}

	@SuppressWarnings("unchecked")
	public List<FilaHojaRuta> getHojaRutaExpediente(Integer idExpediente){
        StringBuilder sql=new StringBuilder();
		sql.append("SELECT ID,NRODOCUMENTO,FECHACREACION,REMITENTE,DESTINATARIO,ACCION,CONTENIDO,DOCUMENTO,TIPO,PROVEIDO,ESTADO from (");
		sql.append("SELECT td.idtrazabilidaddocumento AS id, FNC_NRODOCUMENTO(td.documento,1) AS NRODOCUMENTO, td.fechacreacion,  ");
		sql.append("   FNC_USUARIO_AREA(td.remitente, 1, td.unidadremitente) AS remitente, FNC_USUARIO_AREA(td.destinatario, 1, td.destinatario) AS destinatario, ");
		sql.append("   FNC_ACCION(td.accion) AS accion, TO_CHAR(td.contenido) AS contenido, td.documento,   ");
		sql.append("   'Derivar' AS tipo, nvl((select p.nombre from proveido p where p.idproveido= td.idproveido),'') proveido, FNC_ESTADO(td.documento,1) AS estado,   ");
		sql.append("   1 opcion    ");
		sql.append("  FROM trazabilidaddocumento td  ");
		sql.append("WHERE td.documento IN (SELECT iddocumento FROM documento WHERE expediente=" + idExpediente + " and estado not in ('I')) ");
		sql.append("UNION ALL    ");
		sql.append("SELECT ta.idtrazabilidadapoyo AS id, FNC_NRODOCUMENTO(ta.documento,2) AS NRODOCUMENTO, ta.fechacreacion, ");
		sql.append("   FNC_USUARIO_AREA (ta.remitente, 1,ta.unidadremitente) AS remitente, FNC_USUARIO_AREA (ta.destinatario, 1,ta.unidaddestinatario) AS destinatario,");
		sql.append("   FNC_ACCION(ta.accion) AS accion, ta.texto AS contenido,   ");
		sql.append("   (SELECT d.documentoreferencia FROM documento d WHERE d.iddocumento = ta.documento) AS documento, ");
		sql.append("   'Derivar Múltiple' AS tipo, nvl((select p.nombre from proveido p where p.idproveido= ta.idproveido),'') proveido, FNC_ESTADO(ta.estado,2) AS estado,           ");
		sql.append("   2 opcion        ");
		sql.append("  FROM trazabilidadapoyo ta   ");
		sql.append("WHERE ta.documento IN (SELECT iddocumento FROM documento WHERE expediente="+ idExpediente +" and estado not in ('I')) ");
		sql.append("UNION ALL       ");
		sql.append("SELECT tc.idtrazabilidadcopia AS id, FNC_NRODOCUMENTO(tc.documento,1) AS NRODOCUMENTO, tc.fechacreacion,          ");
		sql.append("   FNC_USUARIO_AREA (tc.remitente, 2, tc.unidadremitente) AS remitente, FNC_USUARIO_AREA (tc.destinatario, 1, tc.unidaddestinatario) AS destinatario,        ");
		sql.append("   FNC_ACCION(tc.accion) AS accion, FNC_NOTIFICACION(tc.idnotificacion) AS contenido, tc.documento,               ");
		sql.append("   'Copia' AS tipo, '' proveido, FNC_ESTADO(tc.documento,1) AS estado,   ");
		sql.append("   3 opcion      ");
		sql.append("  FROM trazabilidadcopia tc      ");
		sql.append("WHERE tc.documento IN (SELECT iddocumento FROM documento WHERE expediente="+ idExpediente + " and estado not in ('I')) ");
		sql.append("ORDER BY fechacreacion, opcion)");
                
                Query q=em.createNativeQuery(sql.toString());
	        List data=q.getResultList();
                List<FilaHojaRuta> dataforward=new ArrayList<FilaHojaRuta>();
               
                for(int i=0;i<data.size();i++){
		    try{
                	Object obj[]=(Object[])data.get(i);
			FilaHojaRuta f = new FilaHojaRuta();
			FilaHojaRutaPK pk = new FilaHojaRutaPK();
			Integer id=Integer.parseInt(obj[0].toString());
		        String nroDocumento = String.valueOf(obj[1]);
		        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date fechaCreacion = formato.parse(obj[2].toString());
                        String remitente =  String.valueOf(obj[3]);
                        String destinatario =   String.valueOf(obj[4]);
                        String accion =  String.valueOf(obj[5]);
                        String contenido =  String.valueOf(obj[6]==null?"":obj[6]);
                        Integer documento =   Integer.parseInt(obj[7].toString());
                        String tipo =  obj[8].toString();
                        String proveido =  String.valueOf(obj[9]==null?"":obj[9]);
                        String estado =  String.valueOf(obj[10]);
                        f.setNumeroDocumento(nroDocumento);
                        f.setRemitente(remitente);
                        f.setDestinatario(destinatario);
                        f.setAccion(accion);
                        f.setContenido(contenido);
                        f.setDocumento(documento);
                        f.setProveido(proveido);
                        f.setEstado(estado);
                        f.setFechaCreacion(fechaCreacion);
                        pk.setTipo(tipo);
                        pk.setId(id);
                        f.setPk(pk);
                        dataforward.add(f);
                    }catch(Exception ex){
			  ex.printStackTrace();
		    }
		}

		return dataforward;
	}
}