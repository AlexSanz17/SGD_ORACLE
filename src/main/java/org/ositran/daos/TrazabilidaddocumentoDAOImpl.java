/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.hibernate.CacheMode;
import org.ositran.utils.Constantes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.btg.ositran.siged.domain.Accion;
import com.btg.ositran.siged.domain.Unidad;
import com.btg.ositran.siged.domain.Documento;
import com.btg.ositran.siged.domain.Proceso;
import com.btg.ositran.siged.domain.Trazabilidaddocumento;

@Repository
public class TrazabilidaddocumentoDAOImpl implements TrazabilidaddocumentoDAO{
	private static Logger log = LoggerFactory.getLogger(TrazabilidaddocumentoDAOImpl.class);
	private EntityManager em;

	// ////////////////////////////////
	// Methods //
	// ////////////////////////////////
	@SuppressWarnings("unchecked")
	public List<Trazabilidaddocumento> buscarLstPor(Integer iIdDocumento){
		log.debug("-> [DAO] TrazabilidaddocumentoDAO - buscarLstPor():List<Trazabilidaddocumento> ");

		return em.createNamedQuery("Trazabilidaddocumento.findByIddocumento").setHint("org.hibernate.cacheMode",CacheMode.REFRESH).setParameter("iddocumento",iIdDocumento).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Trazabilidaddocumento> buscarTrazaCompleta(Integer idExpediente){
		log.debug("-> [DAO] TrazabilidaddocumentoDAO - buscarTrazaCompleta():List<Trazabilidaddocumento> ");
        	return em.createQuery("SELECT t FROM Trazabilidaddocumento t WHERE t.documento.expediente.id = :idExpediente and t.documento.estado not in ('I') ORDER BY t.fechacreacion ASC").setParameter("idExpediente", idExpediente).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Trazabilidaddocumento> buscarMaxTrazaPor(Integer iIdDocumento){
		log.debug("-> [DAO] TrazabilidaddocumentoDAO - buscarMaxTrazaPor():List<Trazabilidaddocumento> ");

		return em.createNamedQuery("Trazabilidaddocumento.findByMaxtrazabyIddocumento").setHint("org.hibernate.cacheMode",CacheMode.REFRESH).setParameter("iddocumento",iIdDocumento).getResultList();
	}

	public Trazabilidaddocumento findByDocumentoDestinatario(Integer iIdDocumento,Integer iIdDestinatario){
		log.debug("-> [DAO] TrazabilidaddocumentoDAO - findByDocumentoDestinatario():Trazabilidaddocumento ");

		return (Trazabilidaddocumento) em.createNamedQuery("Trazabilidaddocumento.findByIddocumentoIddestinatario").setParameter("iddocumento",iIdDocumento).setParameter("iddestinatario",iIdDestinatario).getSingleResult();
	}

	public Trazabilidaddocumento findByMaxNroRegistro(Integer iIdDoc,Integer idaccion,Integer destinatarioId){
		log.debug("-> [DAO] TrazabilidaddocumentoDAO - findByMaxNroRegistro():Trazabilidaddocumento ");
                
		String query="SELECT t FROM Trazabilidaddocumento t WHERE t.documento.idDocumento = :iddoc AND t.idtrazabilidaddocumento = ( SELECT MAX(tt.idtrazabilidaddocumento) FROM Trazabilidaddocumento tt "+"   WHERE tt.documento.idDocumento = :iddoc";
		if(idaccion!=null){
			query+=" and  tt.accion.idAccion = :accid ";
		}
		if(destinatarioId!=null){
			query+=" and  tt.destinatario.idusuario = :remitenteId ";
		}
		query+=" ) ";
		
		query+=" ORDER by t.fechacreacion ";
                
                 
		Query q=em.createQuery(query).setParameter("iddoc",iIdDoc);
                
		if(idaccion!=null){
			q.setParameter("accid",idaccion);
		}
		if(destinatarioId!=null){
			q.setParameter("remitenteId",destinatarioId);
		}
		return (Trazabilidaddocumento) q.getSingleResult();
	}
        
        
        public Trazabilidaddocumento findByMaxNroRegistroAR(Integer iIdDoc,Integer idaccion,Integer destinatarioId, Integer unidad , Integer funcion){
		log.debug("-> [DAO] TrazabilidaddocumentoDAO - findByMaxNroRegistroAR():Trazabilidaddocumento ");
                String query="SELECT t FROM Trazabilidaddocumento t WHERE t.documento.idDocumento = :iddoc AND t.idtrazabilidaddocumento = ( SELECT MAX(tt.idtrazabilidaddocumento) FROM Trazabilidaddocumento tt "+"   WHERE tt.documento.idDocumento = :iddoc and  tt.accion.idAccion not in (25,31) ";
		if(idaccion!=null){
			query+=" and  tt.accion.idAccion = :accid ";
		}
		if(destinatarioId!=null){
			query+=" and  tt.destinatario.idusuario = :remitenteId and tt.unidaddestinatario=:unidaddestinatario and tt.cargodestinatario =:cargodestinatario ";
		}
		query+=" ) ";
		
		query+=" ORDER by t.fechacreacion ";
                
                 
		Query q=em.createQuery(query).setParameter("iddoc",iIdDoc);
		if(idaccion!=null){
			q.setParameter("accid",idaccion);
		}
		if(destinatarioId!=null){
			q.setParameter("remitenteId",destinatarioId).setParameter("unidaddestinatario", unidad).setParameter("cargodestinatario", funcion);
		}
		return (Trazabilidaddocumento) q.getSingleResult();
	}
        
        public Trazabilidaddocumento findByMaxNroRegistro(Integer iIdDoc,Integer idaccion,Integer destinatarioId, Integer unidad , Integer funcion){
		log.debug("-> [DAO] TrazabilidaddocumentoDAO - findByMaxNroRegistro():Trazabilidaddocumento ");
                String query="SELECT t FROM Trazabilidaddocumento t WHERE t.documento.idDocumento = :iddoc AND t.idtrazabilidaddocumento = ( SELECT MAX(tt.idtrazabilidaddocumento) FROM Trazabilidaddocumento tt "+"   WHERE tt.documento.idDocumento = :iddoc";
		if(idaccion!=null){
			query+=" and  tt.accion.idAccion = :accid ";
		}
		if(destinatarioId!=null){
			query+=" and  tt.destinatario.idusuario = :remitenteId and tt.unidaddestinatario=:unidaddestinatario and tt.cargodestinatario =:cargodestinatario ";
		}
		query+=" ) ";
		
		query+=" ORDER by t.fechacreacion ";
                
                 
		Query q=em.createQuery(query).setParameter("iddoc",iIdDoc);
		if(idaccion!=null){
			q.setParameter("accid",idaccion);
		}
		if(destinatarioId!=null){
			q.setParameter("remitenteId",destinatarioId).setParameter("unidaddestinatario", unidad).setParameter("cargodestinatario", funcion);
		}
		return (Trazabilidaddocumento) q.getSingleResult();
	}

	@Override
	public Trazabilidaddocumento findRemitenteAccionByIddoc(Integer idDocumento) {
		log.debug("-> [DAO] TrazabilidaddocumentoDAO - findRemitenteAccionByIddoc():Trazabilidaddocumento ");

		String elquery=" SELECT  NEW Trazabilidaddocumento( " + " t.accion, t.idtrazabilidaddocumento, t.remitente  )" + " from Trazabilidaddocumento t "   ;
		Query q;
		q=em.createQuery(elquery+" WHERE t.documento.idDocumento = :iddocumento AND t.idtrazabilidaddocumento = ( SELECT MAX(tt.idtrazabilidaddocumento) FROM Trazabilidaddocumento tt "+"   WHERE tt.documento.idDocumento = :iddocumento ) ORDER by t.fechacreacion  ");
		q.setParameter("iddocumento",idDocumento);
		Trazabilidaddocumento objTrazabilidadDocumento=(Trazabilidaddocumento) q.getSingleResult();
		return objTrazabilidadDocumento;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Trazabilidaddocumento> getListTrazabilidadExpedienteByIdExpediente(Integer expedienteId){
		log.debug("-> [DAO] TrazabilidaddocumentoDAO - getListTrazabilidadExpedienteByIdExpediente():List<Trazabilidaddocumento> ");

		Integer derivar=findAccionByNombre(Constantes.ACCION_REENVIAR).getIdAccion();
		Integer rechazar=findAccionByNombre(Constantes.ACCION_RECHAZAR).getIdAccion();
		Integer aprobarqas=findAccionByNombre(Constantes.ACCION_APROBAR_QAS).getIdAccion();
		List<Integer> idacciones=new ArrayList();
		idacciones.add(derivar);
		idacciones.add(rechazar);
		idacciones.add(aprobarqas);
		return em.createNamedQuery("Trazabilidaddocumento.findListByIdDocumento").setParameter("idexp",expedienteId).setParameter("verSeguimiento",Constantes.VER_SEGUIMIENTO_SI).setParameter("accionId",idacciones).getResultList();
	}

	public Trazabilidaddocumento saveTrazabilidadDocumento(Trazabilidaddocumento objT){
		log.debug("-> [DAO] TrazabilidaddocumentoDAO - saveTrazabilidadDocumento():Trazabilidaddocumento ");
                
                if(objT.getIdtrazabilidaddocumento()==null && objT.getFechalimiteatencion()==null){
                   objT.setFechalimiteatencion(objT.getDocumento().getFechaLimiteAtencion());
                }
                
                if(objT.getAsunto() != null){
                   objT.setAsunto(objT.getAsunto().toUpperCase());
		}
               
                em.persist(objT); // Nuevo
		em.flush(); 
		em.refresh(objT);
                
		return objT;
	}

	public Trazabilidaddocumento findTrabilidadbyId(Integer  idtrazabilidaddocumento) {
		  log.debug("-> [DAO] TrazabilidaddocumentoDAO - findTrabilidadbyId():Trazabilidaddocumento ");
		  return (Trazabilidaddocumento)em.createNamedQuery("Trazabilidaddocumento.findByIdtrazabilidaddocumento").setParameter("idtrazabilidaddocumento", idtrazabilidaddocumento).getSingleResult() ;
     }

	//FIXME esto no deberia estar aqui
	public Accion findAccionByNombre(String strA){
		log.debug("-> [DAO] TrazabilidaddocumentoDAO - findAccionByNombre():Accion ");

		return (Accion) em.createNamedQuery("Accion.findByNombre").setParameter("nombre",strA.toLowerCase()).getSingleResult();
	}

	@PersistenceContext(unitName="sigedPU")
	public void setEm(EntityManager em){
		this.em=em;
	}

	@Override
	public Trazabilidaddocumento encontrarUltimaTrazabilidadPorDocumento(int idDocumento){
                log.debug("-> [DAO] TrazabilidaddocumentoDAO - encontrarUltimaTrazabilidadPorDocumento():Trazabilidaddocumento ");

		String sql="SELECT t FROM Trazabilidaddocumento t WHERE t.documento.idDocumento = :iddoc AND t.idtrazabilidaddocumento = ( SELECT MAX(tt.idtrazabilidaddocumento) FROM Trazabilidaddocumento tt WHERE tt.documento.idDocumento = :iddoc)";
		Query q=em.createQuery(sql).setParameter("iddoc",idDocumento);
		Trazabilidaddocumento trazabilidad=null;
		trazabilidad=(Trazabilidaddocumento) q.getSingleResult();
		return trazabilidad;
	}

	@Override
	public Trazabilidaddocumento getTrazabilidadAnterior(Trazabilidaddocumento trazabilidad){
		log.debug("-> [DAO] TrazabilidaddocumentoDAO - getTrazabilidadAnterior():Trazabilidaddocumento ");

		String sql="SELECT t from Trazabilidaddocumento t WHERE t.nroregistro=:registro AND t.documento.iddocumento=:documento";
		Query q=em.createQuery(sql);
		q.setParameter("registro",trazabilidad.getNroregistro()-1);
		q.setParameter("documento",trazabilidad.getDocumento().getIdDocumento());
		return (Trazabilidaddocumento) q.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> filtrarReporteDocumentos(List<String> nombreAcciones, String sFechaDesde, String sFechaHasta, Integer idSede, Integer idProceso){
		log.debug("-> [DAO] TrazabilidaddocumentoDAO - filtrarReporteDocumentos():List<Object[]> ");

		List<Integer> idacciones = new ArrayList<Integer>();
		for(int i=0; i<nombreAcciones.size(); i++){
			idacciones.add(this.findAccionByNombre(nombreAcciones.get(i)).getIdAccion());
		}

		List<Object[]> result = new ArrayList<Object[]>();
		SimpleDateFormat formato=new SimpleDateFormat("dd/MM/yyyy");

		StringBuilder sql= new StringBuilder();
		sql.append("SELECT distinct t.documento, t.accion, max(t.fechacreacion), d.nrofolios FROM Trazabilidaddocumento t, Documento d, Expediente e, Proceso p, Usuario usu, Unidad uni, Sede s " );
		sql.append(" WHERE t.accion in ( :accionId ) AND t.documento = d.iddocumento ");
		sql.append(" AND e.id = d.expediente ");
		sql.append(" AND e.proceso = p.idproceso ");
		sql.append(" AND p.responsable = usu.idusuario ");
		sql.append(" AND usu.idunidad = uni.idunidad ");
		sql.append(" AND uni.sede = s.idsede ");

		sql.append(" AND p.idproceso = :idProceso ");
		sql.append(" AND s.idsede = :idSede ");

		if(!sFechaDesde.isEmpty()){
			sql.append("AND t.fechacreacion >= :fechadesde ");
		}
		if(!sFechaHasta.isEmpty()){
			sql.append("AND t.fechacreacion <= :fechahasta ");
		}
		sql.append("GROUP BY t.documento, t.accion, d.nrofolios ORDER BY max(t.fechacreacion) ASC");
		Query q=em.createNativeQuery(sql.toString());

		q.setParameter("accionId",idacciones);
		q.setParameter("idProceso",idProceso);
		q.setParameter("idSede",idSede);
		if(!sFechaDesde.isEmpty()){
			try{
				Date datFecha=formato.parse(sFechaDesde);
				q.setParameter("fechadesde",datFecha);
			}catch(Exception ex){
			//	log.error(ex.getMessage(),ex);
			}
		}
		if(!sFechaHasta.isEmpty()){
			try{
				Date datFecha=formato.parse(sFechaHasta);
				datFecha.setHours(24);
				datFecha.setMinutes(0);
				datFecha.setSeconds(0);
				q.setParameter("fechahasta",datFecha);
			}catch(Exception ex){
			//	log.error(ex.getMessage(),ex);
			}
		}

		result = q.getResultList();
		return result;
	}

	public List<Integer> findLstUsuarioDelProcAcc3(Documento d){
		log.debug("-> [DAO] TrazabilidaddocumentoDAO - findLstUsuarioDelProcAcc3():List<Integer> ");

		String sql= "select DISTINCT u.idusuario, u.nombres, td.documento, d.expediente from trazabilidaddocumento td " +
					"inner join usuario u   on u.idusuario=td.remitente " +
					"inner join documento d on d.iddocumento=td.documento "+
				    "where td.documento in (select dd.iddocumento from documento dd where dd.expediente in (select e.idexpediente from expediente e " +
				    "where e.idexpediente ="+d.getExpediente().getIdexpediente()+")) "+
					"union "+
					"select DISTINCT u.idusuario, u.nombres, td.documento, d.expediente from trazabilidaddocumento td "+
					"inner join usuario u   on u.idusuario=td.remitente " +
					"inner join documento d on d.iddocumento=td.documento "+
					"where td.documento in (select dd.iddocumento from documento dd where dd.expediente in (select e.idexpediente from expediente e "+
					"where e.idexpediente ="+d.getExpediente().getIdexpediente()+"))"+
					"union "+
					"select DISTINCT u.idusuario, u.nombres, tc.documento, d.expediente from trazabilidadcopia tc "+
					"inner join usuario u   on u.idusuario=tc.destinatario "+
					"inner join documento d on d.iddocumento=tc.documento "+
					"where tc.idorigen in (select td.idtrazabilidaddocumento from trazabilidaddocumento td " +
					"where td.documento in (select dd.iddocumento from documento dd " +
					"where dd.expediente in (select e.idexpediente from expediente e where e.idexpediente ="+d.getExpediente().getIdexpediente()+")))";

		Query q=em.createNativeQuery(sql);
		List data=q.getResultList();
		List<Integer> dataforward=new ArrayList<Integer>();
		for(int i=0;i<data.size();i++){
			Object obj[]=(Object[])data.get(i);
			Integer id=Integer.parseInt(obj[0].toString());
			String nombre=String.valueOf(obj[1]);
			String documento=String.valueOf(obj[2]);
			String expediente=String.valueOf(obj[3]);
			//log.info("Id["+id+"]="+nombre+" - documento="+documento+" - expediente="+expediente);
			dataforward.add(id);
		}
		return dataforward;
	}

	public int contarListTotalTrazabilidades(Trazabilidaddocumento t){
		StringBuilder sql=new StringBuilder();
		List<Integer> data = null;

		sql.append("SELECT DOCUMENTO FROM TRAZABILIDADDOCUMENTO WHERE DOCUMENTO = "+ t.getDocumento().getIdDocumento() +" AND (REMITENTE = "+ t.getRemitente().getIdusuario() + " OR DESTINATARIO = " + t.getDestinatario().getIdusuario() + ") ");
		sql.append(" union ");
		sql.append("SELECT DOCUMENTO FROM TRAZABILIDADCOPIA WHERE DOCUMENTO = "+ t.getDocumento().getIdDocumento() +" AND (REMITENTE = "+ t.getRemitente().getIdusuario() + " OR DESTINATARIO = " + t.getDestinatario().getIdusuario() + ") ");
		sql.append(" union ");
		sql.append("SELECT DOCUMENTO FROM TRAZABILIDADAPOYO WHERE DOCUMENTO = "+ t.getDocumento().getIdDocumento() +" AND (REMITENTE = "+ t.getRemitente().getIdusuario() + " OR DESTINATARIO = " + t.getDestinatario().getIdusuario() + ") ");
		sql.append(" union ");
		sql.append("SELECT T.DOCUMENTO FROM TRAZABILIDADAPOYO T WHERE T.DOCUMENTO IN  ( SELECT IDDOCUMENTO FROM DOCUMENTO WHERE DOCUMENTOREFERENCIA = " + t.getDocumento().getIdDocumento() + "  AND PROPIETARIO = " +  t.getRemitente().getIdusuario() + ") AND (T.REMITENTE = "+ t.getRemitente().getIdusuario() + " OR T.DESTINATARIO = " + t.getDestinatario().getIdusuario() + ") ");
		Query q=em.createNativeQuery(sql.toString());
		data=q.getResultList();
		return data.size();
	}

	public int contarListTotalTrazabilidadesExpediente(Trazabilidaddocumento t){
		StringBuilder sql=new StringBuilder();
		List<Integer> data = null;
                
                sql.append("SELECT T.DOCUMENTO FROM TRAZABILIDADDOCUMENTO T, DOCUMENTO D  WHERE D.ESTADO NOT IN ('I') AND D.EXPEDIENTE =" + t.getDocumento().getExpediente().getId() + " AND  T.DOCUMENTO = D.IDDOCUMENTO AND (T.REMITENTE =" + t.getRemitente().getIdusuario() + " OR T.DESTINATARIO =" +  t.getDestinatario().getIdusuario() + " )");
		sql.append(" union ");
		sql.append("SELECT C.DOCUMENTO FROM TRAZABILIDADCOPIA C, DOCUMENTO D WHERE  D.ESTADO NOT IN ('I') AND D.EXPEDIENTE = " + t.getDocumento().getExpediente().getId() + " AND C.DOCUMENTO = D.IDDOCUMENTO AND (C.REMITENTE = " +  t.getRemitente().getIdusuario() +   " OR C.DESTINATARIO = " + t.getDestinatario().getIdusuario() + ")");
		sql.append(" union ");
		sql.append("SELECT A.DOCUMENTO FROM TRAZABILIDADAPOYO A, DOCUMENTO D  WHERE D.ESTADO NOT IN ('I') AND D.EXPEDIENTE = " + t.getDocumento().getExpediente().getId() +  " AND A.DOCUMENTO = D.IDDOCUMENTO AND (A.REMITENTE = " + t.getRemitente().getIdusuario()  +  " OR A.DESTINATARIO = "+  t.getDestinatario().getIdusuario() + ")");
		
		Query q=em.createNativeQuery(sql.toString());
                data=q.getResultList();
		return data.size();
	}
        
        public int contarListTotalTrazabilidadesDocumento(Trazabilidaddocumento t){
		StringBuilder sql=new StringBuilder();
		List<Integer> data = null;
                 
                sql.append("SELECT T.DOCUMENTO FROM TRAZABILIDADDOCUMENTO T, DOCUMENTO D  WHERE D.ESTADO NOT IN ('I') AND D.IDDOCUMENTO =" + t.getDocumento().getIdDocumento() + " AND  T.DOCUMENTO = D.IDDOCUMENTO AND (T.REMITENTE =" + t.getRemitente().getIdusuario() + " OR T.DESTINATARIO =" +  t.getDestinatario().getIdusuario() + " )");
		sql.append(" union ");
		sql.append("SELECT C.DOCUMENTO FROM TRAZABILIDADCOPIA C, DOCUMENTO D WHERE  D.ESTADO NOT IN ('I') AND D.IDDOCUMENTO = " + t.getDocumento().getIdDocumento() + " AND C.DOCUMENTO = D.IDDOCUMENTO AND (C.REMITENTE = " +  t.getRemitente().getIdusuario() +   " OR C.DESTINATARIO = " + t.getDestinatario().getIdusuario() + ")");
		sql.append(" union ");
		sql.append("SELECT A.DOCUMENTO FROM TRAZABILIDADAPOYO A, DOCUMENTO D  WHERE D.ESTADO NOT IN ('I') AND D.IDDOCUMENTO = " + t.getDocumento().getIdDocumento() +  " AND A.DOCUMENTO = D.IDDOCUMENTO AND (A.REMITENTE = " + t.getRemitente().getIdusuario()  +  " OR A.DESTINATARIO = "+  t.getDestinatario().getIdusuario() + ")");
		sql.append(" union ");
		sql.append("SELECT T.DOCUMENTO FROM TRAZABILIDADAPOYO T WHERE T.DOCUMENTO IN  ( SELECT IDDOCUMENTO FROM DOCUMENTO WHERE DOCUMENTOREFERENCIA = " + t.getDocumento().getDocumentoreferencia() + ")  AND (T.REMITENTE = "+ t.getRemitente().getIdusuario() + " OR T.DESTINATARIO = " + t.getDestinatario().getIdusuario() + ") ");
                sql.append(" union ");
		sql.append("SELECT T.DOCUMENTO FROM TRAZABILIDADAPOYO T WHERE T.DOCUMENTO IN  ( SELECT IDDOCUMENTO FROM DOCUMENTO WHERE DOCUMENTOREFERENCIA = " + t.getDocumento().getIdDocumento() + ")  AND (T.REMITENTE = "+ t.getRemitente().getIdusuario() + " OR T.DESTINATARIO = " + t.getDestinatario().getIdusuario() + ") ");
		Query q=em.createNativeQuery(sql.toString());
                data=q.getResultList();
                return data.size();
	}
        
        public int contarListTotalTrazabilidadesUnidad(Trazabilidaddocumento t, List<Unidad> lst){
		StringBuilder sql=new StringBuilder();
		List<Integer> data = null;
                String arreglito = "";
                        
                for(int i=0;i<lst.size();i++){
                    if (lst.size()==i+1)
                      arreglito = arreglito + lst.get(i).getIdunidad() + "";
                    else
                      arreglito = arreglito + lst.get(i).getIdunidad() + ",";  
                }
                
                sql.append("SELECT T.DOCUMENTO FROM TRAZABILIDADDOCUMENTO T, DOCUMENTO D  WHERE D.ESTADO NOT IN ('I') AND D.IDDOCUMENTO =" + t.getDocumento().getIdDocumento() + " AND  T.DOCUMENTO = D.IDDOCUMENTO AND (T.UNIDADREMITENTE in (" + arreglito + ") OR T.UNIDADDESTINATARIO in (" +  arreglito + "))");
		sql.append(" union ");
		sql.append("SELECT C.DOCUMENTO FROM TRAZABILIDADCOPIA C, DOCUMENTO D WHERE  D.ESTADO NOT IN ('I') AND D.IDDOCUMENTO = " + t.getDocumento().getIdDocumento() + " AND C.DOCUMENTO = D.IDDOCUMENTO AND (C.UNIDADREMITENTE in (" +  arreglito +   ") OR C.UNIDADDESTINATARIO in ( " + arreglito + "))");
		sql.append(" union ");
		sql.append("SELECT A.DOCUMENTO FROM TRAZABILIDADAPOYO A, DOCUMENTO D  WHERE D.ESTADO NOT IN ('I') AND D.IDDOCUMENTO = " + t.getDocumento().getIdDocumento() +  " AND A.DOCUMENTO = D.IDDOCUMENTO AND (A.UNIDADREMITENTE in (" + arreglito  +  ") OR A.UNIDADDESTINATARIO in ( "+  arreglito + "))");
		sql.append(" union ");
		sql.append("SELECT T.DOCUMENTO FROM TRAZABILIDADAPOYO T WHERE T.DOCUMENTO IN  ( SELECT IDDOCUMENTO FROM DOCUMENTO WHERE DOCUMENTOREFERENCIA = " + t.getDocumento().getDocumentoreferencia() + ")  AND (T.UNIDADREMITENTE in ("+ arreglito + ") OR T.UNIDADDESTINATARIO in (" + arreglito + ")) ");
                sql.append(" union ");
		sql.append("SELECT T.DOCUMENTO FROM TRAZABILIDADAPOYO T WHERE T.DOCUMENTO IN  ( SELECT IDDOCUMENTO FROM DOCUMENTO WHERE DOCUMENTOREFERENCIA = " + t.getDocumento().getIdDocumento() + ")  AND (T.UNIDADREMITENTE in ("+ arreglito + ") OR T.UNIDADDESTINATARIO in (" + arreglito + ")) ");
		Query q=em.createNativeQuery(sql.toString());
                data=q.getResultList();
                return data.size();
	}

	public List<Integer> findLstUsuarioDelProcAcc2(Proceso p){
		log.debug("-> [DAO] TrazabilidaddocumentoDAO - findLstUsuarioDelProcAcc2():List<Integer> ");

		StringBuilder sql=new StringBuilder();
		/*******************************************************************************/
		//  responzable del proceso.
		/*******************************************************************************/
		sql.append("select u.idusuario, u.nombres, p.idproceso,p.nombre from proceso p ");
		sql.append("inner join usuario u on u.idusuario = p.responsable ");
		sql.append("where p.idproceso = "+p.getIdproceso()+" ");
		sql.append("union ");
		/*******************************************************************************/
		//  reemplazo del responzable del proceso.
		/*******************************************************************************/
		sql.append("select u.idusuario, u.nombres,p.idproceso,p.nombre from reemplazo r ");
		sql.append("inner join usuario u  on u.idusuario = r.idusuario ");
		sql.append("inner join usuario ur on ur.idusuario = r.idreemplazado ");
		sql.append("inner join proceso p  on r.idproceso = p.idproceso ");
		sql.append("where ");
		sql.append("r.idreemplazado in (select u.idusuario from proceso p ");
		sql.append("inner join usuario u on u.idusuario= p.responsable ");
		sql.append("where p.idproceso = "+p.getIdproceso()+") ");
		sql.append("union ");
		/*******************************************************************************/
		//suplente del proceso.
		/*******************************************************************************/
		sql.append("select u.idusuario, u.nombres, p.idproceso,p.nombre from proceso p ");
		sql.append("inner join usuario u on u.idusuario = p.idasistente  ");
		sql.append("where p.idproceso = "+p.getIdproceso()+" ");
		sql.append("union ");
		/*******************************************************************************/
		//  reemplazo del suplente del proceso.
		/*******************************************************************************/
		sql.append("select u.idusuario, u.nombres,p.idproceso,p.nombre from reemplazo r ");
		sql.append("inner join usuario u  on u.idusuario = r.idusuario ");
		sql.append("inner join usuario ur on ur.idusuario = r.idreemplazado ");
		sql.append("inner join proceso p  on r.idproceso = p.idproceso ");
		sql.append("where ");
		sql.append("r.idreemplazado in (select u.idusuario from proceso p ");
		sql.append("inner join usuario u on u.idusuario = p.idasistente ");
		sql.append("where p.idproceso="+p.getIdproceso()+") ");
		sql.append("union ");
		/*******************************************************************************/
		//  los participantes de proceso.
		/*******************************************************************************/
		sql.append("select u.idusuario, u.nombres, p.idproceso,p.nombre from participantexproceso pp ");
		sql.append("inner join usuario u on u.idusuario = pp.idusuario ");
		sql.append("inner join proceso p on p.idproceso = pp.idproceso ");
		sql.append("where p.idproceso="+p.getIdproceso()+" ");
		sql.append("union ");
		/*******************************************************************************/
		//  reemplazo de los participantes de proceso.
		/*******************************************************************************/
		sql.append("select u.idusuario, u.nombres,p.idproceso,p.nombre from reemplazo r ");
		sql.append("inner join usuario u  on u.idusuario = r.idusuario ");
		sql.append("inner join usuario ur on ur.idusuario = r.idreemplazado ");
		sql.append("inner join proceso p  on r.idproceso = p.idproceso ");
		sql.append("where ");
		sql.append("r.idreemplazado in (select u.idusuario from participantexproceso pp ");
		sql.append("inner join usuario u on u.idusuario = pp.idusuario ");
		sql.append("inner join proceso p on p.idproceso = pp.idproceso where pp.idproceso="+p.getIdproceso()+") ");
		sql.append("union ");
		/*******************************************************************************/
		//  Lista de los participantes de proceso.
		/*******************************************************************************/
		sql.append("select u.idusuario, u.nombres, p.idproceso,p.nombre from listaxproceso lp ");
		sql.append("inner join lista l                on l.idlista = lp.idlista ");
		sql.append("inner join proceso p              on p.idproceso = lp.idproceso ");
		sql.append("inner join participantexlista pl  on pl.idlista = l.idlista ");
		sql.append("inner join usuario u              on u.idusuario = pl.idusuario ");
		sql.append("where p.idproceso="+p.getIdproceso()+" ");
		sql.append("union ");
		/*******************************************************************************/
		//  Reemplazo de Lista de los participantes de proceso.
		/*******************************************************************************/
		sql.append("select u.idusuario, u.nombres,p.idproceso,p.nombre from reemplazo r ");
		sql.append("inner join usuario u  on u.idusuario = r.idusuario ");
		sql.append("inner join usuario ur on ur.idusuario = r.idreemplazado ");
		sql.append("inner join proceso p  on r.idproceso = p.idproceso ");
		sql.append("where ");
		sql.append("r.idreemplazado in (select u.idusuario from listaxproceso lp ");
		sql.append("inner join lista l on l.idlista = lp.idlista ");
		sql.append("inner join proceso p on p.idproceso = lp.idproceso ");
		sql.append("inner join participantexlista pl on pl.idlista = l.idlista ");
		sql.append("inner join usuario u on u.idusuario = pl.idusuario ");
		sql.append("where p.idproceso="+p.getIdproceso()+")");

		Query q=em.createNativeQuery(sql.toString());
		List data=q.getResultList();
		List<Integer> dataforward=new ArrayList<Integer>();
		for(int i=0;i<data.size();i++){
			Object obj[]=(Object[])data.get(i);
			Integer id=Integer.parseInt(obj[0].toString());
			String nombre=String.valueOf(obj[1]);
			String idproceso=String.valueOf(obj[2]);
			String nombreProceso=String.valueOf(obj[3]);
			log.debug("id {} nombre {} idProceso {} nombreProceso {}", new Object[]{id, nombre, idproceso, nombreProceso});
			dataforward.add(id);
		}
		return dataforward;
	}

	public Integer totalUsuarioCompartidos(Integer idUsuarioLogeado,Integer idPropietarioDoc){
		log.debug("-> [DAO] TrazabilidaddocumentoDAO - totalUsuarioCompartidos():Integer ");

		int total=0;
		String sql= "select usuario, asignado from compartidoxusuario where asignado="+idUsuarioLogeado+" and usuario="+idPropietarioDoc;
		log.debug("SQL {}", sql);
		Query q=em.createNativeQuery(sql);
		List data=null;
		try{ data=q.getResultList();}catch (Exception e) {}
		if(data!=null){
			total=data.size();
		}
		log.info("Total usuarios Compartidos encontrados para el Usuario Logeado="+idUsuarioLogeado);
		return total;
	}

	public Integer totalUsuarioCompartidos(Integer idUsuarioLogeado){
		log.debug("-> [DAO] TrazabilidaddocumentoDAO - totalUsuarioCompartidos():Integer ");

		int total=0;
		String sql= "select usuario, asignado from compartidoxusuario where asignado="+idUsuarioLogeado;
		Query q=em.createNativeQuery(sql);
		List data=q.getResultList();
		if(data!=null){
			total=data.size();
		}
		return total;
	}

	public Double ratioTrazabilidadDocumento(Integer idExpediente){
		log.debug("-> [DAO] TrazabilidaddocumentoDAO - ratioTrazabilidadDocumento():Double ");

		String sql = "SELECT COUNT(t.idtrazabilidaddocumento) FROM Trazabilidaddocumento t WHERE t.documento.expediente.id = :idExpediente";
		Long trazabilidades = (Long)em.createQuery(sql).setParameter("idExpediente", idExpediente).getSingleResult();
		sql = "SELECT COUNT(d.idDocumento) FROM Documento d WHERE d.expediente.id = :idExpediente AND d.documentoreferencia IS NULL";
		Long documentos = (Long)em.createQuery(sql).setParameter("idExpediente", idExpediente).getSingleResult();
		return trazabilidades == 0 || documentos == 0? 0d : trazabilidades/(documentos*1d);
	}

	@Override
	public Trazabilidaddocumento updateTrazabilidadDocumento(
			Trazabilidaddocumento objT) {
		log.debug("-> [DAO] TrazabilidaddocumentoDAO - updateTrazabilidadDocumento():Trazabilidaddocumento ");
		try {
			em.merge(objT);
			em.flush();
			em.refresh(objT);
			return objT;
		} catch (Exception e) {
			return null;
		}
	}
}
