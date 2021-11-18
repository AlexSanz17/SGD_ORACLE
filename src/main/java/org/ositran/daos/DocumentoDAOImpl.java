/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Blob;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.commons.lang.StringUtils;
import org.ositran.dojo.BusquedaAvanzada;
import org.ositran.dojo.Recurso;
import org.ositran.dojo.grid.Item;
import org.ositran.dojo.grid.ItemUF;
import org.ositran.utils.Constantes;
import org.ositran.utils.DocumentoDetail;
import org.ositran.pojos.jasper.CargoReporte;
import org.ositran.services.AccionService;
import org.ositran.services.ManejoDeEmailService;
import org.ositran.services.ParametroService;
import org.ositran.services.TrazabilidadcopiaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.btg.ositran.siged.domain.Alerta;
import com.btg.ositran.siged.domain.Documento;
import com.btg.ositran.siged.domain.DocumentoAreaFuncional;
import com.btg.ositran.siged.domain.Expediente;
import com.btg.ositran.siged.domain.FilaBandejaUF;
import com.btg.ositran.siged.domain.DocumentoPendiente;
import com.btg.ositran.siged.domain.FilaSeguimiento;
import com.btg.ositran.siged.domain.FilaSeguimientoDestinatario;
import com.btg.ositran.siged.domain.FilaSeguimientoDestinatarioGG;
import com.btg.ositran.siged.domain.FilaSeguimientoRemitente;
import com.btg.ositran.siged.domain.Notificacion;
import com.btg.ositran.siged.domain.NotificacionxEnumerar;
import com.btg.ositran.siged.domain.Numeracion;
import com.btg.ositran.siged.domain.Parametro;
import com.btg.ositran.siged.domain.Tipodocumento;
import com.btg.ositran.siged.domain.TramiteDocumentario;
import com.btg.ositran.siged.domain.Trazabilidadapoyo;
import com.btg.ositran.siged.domain.Trazabilidaddocumento;
import com.btg.ositran.siged.domain.Unidad;
import com.btg.ositran.siged.domain.Usuario;
import com.btg.ositran.siged.domain.Usuarioxunidadxfuncion;
import java.util.LinkedList;
import org.ositran.services.ArchivoService;
import org.ositran.services.UnidadService;
import org.ositran.utils.DocumentoPublicar;

@Repository
public class DocumentoDAOImpl implements DocumentoDAO {

	private static Logger log = LoggerFactory.getLogger(DocumentoDAOImpl.class);
	private EntityManager em;
	private AccionService accionService;
	private TrazabilidadcopiaService trazabilidadcopiaService;
	private TrazabilidadapoyoDAO trazabilidadapoyoDAO;
	private ManejoDeEmailService mailService;
	private UsuarioDAO usuarioDAO;
	private ParametroService parametroService;
        private UnidadService unidadService;
        private ArchivoService srvArchivo;
        private UsuarioxunidadxfuncionDAO usuarioxunidadxfuncionDAO;

        public UsuarioxunidadxfuncionDAO getUsuarioxunidadxfuncionDAO() {
            return usuarioxunidadxfuncionDAO;
        }

        public void setUsuarioxunidadxfuncionDAO(UsuarioxunidadxfuncionDAO usuarioxunidadxfuncionDAO) {
            this.usuarioxunidadxfuncionDAO = usuarioxunidadxfuncionDAO;
        }

        public ArchivoService getSrvArchivo() {
            return srvArchivo;
        }

        public void setSrvArchivo(ArchivoService srvArchivo) {
            this.srvArchivo = srvArchivo;
        }
     
        public UnidadService getUnidadService() {
            return unidadService;
        }

        public void setUnidadService(UnidadService unidadService) {
            this.unidadService = unidadService;
        }
        
	// private Map<String,Object> mapSession;

	// ////////////////////////////////
	// Methods //
	// ////////////////////////////////

	public List<Documento> backup(){

		String sQuery = "SELECT NEW org.ositran.dojo.grid.Item(" +
		"n.idnotificacion," +
		"n.leido," +
		"n.asunto," +
		"n.fechanotificacion," +
		"n.tiponotificacion," +
		"d.idDocumento," +
		"c.razonSocial," +
		"e.nroexpediente," +
		"p.nombre," +
		"ti.nombre," +
		"cl.razonSocial," +
		"cl.nombres," +
		"cl.apellidoPaterno," +
		"cl.apellidoMaterno," +
		"td.nombre," +
		"concat(td.nombre, d.numeroDocumento)" +
		") ";

		sQuery += "FROM Notificacion n ";
		sQuery += "LEFT JOIN n.iddocumento d ";
		sQuery += "LEFT JOIN d.tipoDocumento td ";
		sQuery += "LEFT JOIN n.idusuario u ";
		sQuery += "LEFT JOIN d.expediente e ";
		sQuery += "LEFT JOIN e.concesionario c ";
		sQuery += "LEFT JOIN e.proceso p ";
		sQuery += "LEFT JOIN e.cliente cl ";
		sQuery += "LEFT JOIN cl.tipoIdentificacion ti ";
		sQuery += "WHERE u.idusuario = :idpropietario AND n.tiponotificacion IN (:informativo1 , :informativo2 , :informativo3 , :informativo4 , :informativo5 , :informativo6 , :informativo7 ) AND n.estado = :estado AND CURRENT_DATE>=n.fechanotificacion ";
	    sQuery += "ORDER BY n.fechanotificacion DESC";

		Query qQuery = em.createQuery(sQuery)
		.setParameter("idpropietario", 3717)
		.setParameter("informativo1", Constantes.TIPO_NOTIFICACION_DERIVACION)
		.setParameter("informativo2", Constantes.TIPO_NOTIFICACION_NUMERACION_DESTINATARIO)
		.setParameter("informativo3", Constantes.TIPO_NOTIFICACION_NUMERACION_DOCUMENTOCONCOPIA)
		.setParameter("informativo4", Constantes.TIPO_NOTIFICACION_DERIVACIONCONCOPIA)
		.setParameter("informativo5", Constantes.TIPO_NOTIFICACION_APROBACION_QAS)
		.setParameter("informativo6", Constantes.TIPO_NOTIFICACION_DUENO_EXPEDIENTE)
		.setParameter("informativo7", Constantes.TIPO_NOTIFICACION_FIN_APOYO)
		.setParameter("estado", 'A');

		List<Item> f = qQuery.getResultList();

		List<Documento> l = new ArrayList<Documento>();
		Documento d;

		for(int i=0;i<f.size();i++){
			d = new Documento();
			d.setIdDocumento(new Integer(f.get(i).getIddocumento()));
			d.setFechaDocumento(f.get(i).getFecharecepcion());
			Tipodocumento t = new Tipodocumento();
			t.setNombre(f.get(i).getTipodocumento());
			d.setTipoDocumento(t);

			l.add(d);
		}


		return l;
	}

	 public String getAtendidoTramiteDocumentario(Documento d){
		 String  sql =  "select  iddocumento from td_documento_atendido where id_codigo=" + d.getID_CODIGO(); //+ " order by fechacreacion asc";

			List<TramiteDocumentario> listObj =  null;
			List<Object> res = null;

			try{
				 res = (List<Object>) em.createNativeQuery(sql.toString()).getResultList();

				 if (res!=null && res.size()>0){
					 return "green";
				 }else{
                     return "red";
				 }
			}catch(Exception e){
                return "red";
			}
	 }

	public Integer getNroConsultaTramiteDocumentario(){
		 String sQuery = "SELECT Last_number - 1 FROM user_sequences where sequence_name='CONSULTA_TRAMITE_SEQ'";
		 Query qQuery = em.createNativeQuery(sQuery);

		 return Integer.valueOf(qQuery.getResultList().get(0).toString());
	}


	public Integer getNroSiguienteConsultaTramiteDocumentario(){
		 String sQuery = "SELECT CONSULTA_TRAMITE_SEQ.nextval FROM DUAL";
		 Query qQuery = em.createNativeQuery(sQuery);

		 return Integer.valueOf(qQuery.getResultList().get(0).toString());
	}
        
        public Integer getNroDocumento(){
		 String sQuery = "SELECT DOCUMENTO_SEQ.nextval FROM DUAL";
		 Query qQuery = em.createNativeQuery(sQuery);

		 return Integer.valueOf(qQuery.getResultList().get(0).toString());
	}
        
        
        public Integer getNroTramiteDocumentario(){
                 
		 String sQuery = "SELECT NROMP_SEQ.nextval FROM DUAL";
		 Query qQuery = em.createNativeQuery(sQuery);

		 return Integer.valueOf(qQuery.getResultList().get(0).toString());
	}

	public List<Alerta> findAlertas(Integer idUsuario, Integer idUnidad, boolean banderaDescarga, String orden){
		 String sql = null;
		 List<Alerta> listAlerta = null;
		 Query q = null;

         try{

        	   if (banderaDescarga){
        		     sql =	"SELECT f FROM Alerta f WHERE f.userremitente = :idUsuario or (f.userremitente!= :idUsuario and f.userdestinatario = :idUsuario) " + orden;

				     q= em.createQuery(sql)
				   		 .setParameter("idUsuario", idUsuario);

        	   }else{

        		     List<Parametro> list = parametroService.findByTipo("DESCARGAR_PENDIENTES");
                     String areas = "";

            	     for(int i=0;i<list.size();i++){
        			    if (i==list.size()-1){
        				  areas = areas +   list.get(i).getValor();
        			    }else{
        				  areas = areas +  list.get(i).getValor() + ",";
        			    }
        		     }

					 sql = "SELECT f FROM Alerta f WHERE f.remitente in (" +  areas   + ")   and f.userdestinatario = :idUsuario " + orden;
					 q = em.createQuery(sql).setParameter("idUsuario", idUsuario);
			   }

			   listAlerta = q.getResultList();

         }catch(Exception e){
        	 e.printStackTrace();
         }
		 return listAlerta;
	}
        
         public List<Documento> buscarPendientePorDocumentoMultiple(Documento d){
            String sql = "";
            sql = " SELECT d FROM  Documento d WHERE d.idDocumento = " + d.getIdDocumento() +" and  d.estado not in ('I','N','T') ";
            sql = sql + " union ";
            sql = sql  + " SELECT d FROM  Documento d WHERE d.idDocumento in (select dd.idDocumento from Documento dd where dd.documentoreferencia=" + d.getIdDocumento() + ")  and d.estado not in ('I','N','T') ";
       
            return em.createQuery(sql).getResultList();
        }

	  public List<TramiteDocumentario> buscarTramiteDocumentario(String nroTramitedocumentario){
		   String  sql =  "select  id_codigo, nrodocumento,  iddocumento, remitente, destinatario, fechacreacion, orden, asunto, cliente,estado from tramite_documentario where id_codigo=" + nroTramitedocumentario; //+ " order by fechacreacion asc";

			List<TramiteDocumentario> listObj =  null;
			List<Object> res = null;

			try{
				 res = (List<Object>) em.createNativeQuery(sql.toString()).getResultList();

				 if (res!=null && res.size()>0){
					     listObj =  new ArrayList<TramiteDocumentario>();

						 for (Object obj : res) {
							    TramiteDocumentario t = new TramiteDocumentario();
								Object[] objectArray = (Object[]) obj;
								Object object1 = objectArray[0];
								Object object2 = objectArray[1];
								Object object3 = objectArray[2];
								Object object4 = objectArray[3];
								Object object5 = objectArray[4];
								Object object6 = objectArray[5];
								Object object7 = objectArray[6];
								Object object8 = objectArray[7];
								Object object9 = objectArray[8];
								Object object10 = objectArray[9];

								t.setId_codigo(object1==null?"":object1.toString());
								t.setNrodocumento(object2==null?"":object2.toString());
								t.setIddocumento(object3==null?"":object3.toString());
								t.setRemitente(object4==null?"":object4.toString());
								t.setDestinatario(object5==null?"":object5.toString());
								t.setFechacreacion(object6==null?"":object6.toString());
								t.setOrden(object7==null?"":object7.toString());
								t.setAsunto(object8==null?"":object8.toString());
								t.setCliente(object9==null?"":object9.toString());
								t.setEstado(object10==null?"":object10.toString());
								listObj.add(t);
						 }
				 }





			}catch(Exception e){
				e.printStackTrace();
				listObj = null;
			}


		  return listObj;
	  }

       public List<Documento> consultaDocumentoMultipleAtendido(Integer idDocumento){
            log.debug("-> [DAO] DocumentoDAO - consultaDocumentoMultipleAtendido():Documento "); 
            return em.createNamedQuery("Documento.consultaDocumentoMultipleAtendido")
				.setParameter("iddocumento", idDocumento).getResultList();
        }

	public Documento buscarDocumentoMasAntiguoPor(Integer iIdExpediente) {
		log.debug("-> [DAO] DocumentoDAO - buscarDocumentoMasAntiguoPor():Documento ");

		return (Documento) em
				.createNamedQuery("Documento.buscarDocumentoMasAntiguoPor")
				.setParameter("idexpediente", iIdExpediente).getSingleResult();
	}
        
        public List<Documento> findByIdDocVirtual(Integer codigoVirtual){
            log.debug("-> [DAO] DocumentoDAO - findByIdDocVirtual():Documento "); 
            return em.createNamedQuery("Documento.findByIdDocVirtual")
				.setParameter("nroVirtual", codigoVirtual).getResultList();
        }

        public List<Documento> consultaDocumentoReferencia(Integer idDocumento){
            log.debug("-> [DAO] DocumentoDAO - consultaDocumentoReferencia():Documento "); 
            return em.createNamedQuery("Documento.consultaDocumentoReferencia")
				.setParameter("iddocumento", idDocumento).getResultList();
        }
        
        public List<Documento> consultaDocumentoOrigen(Integer idDocumento){
            log.debug("-> [DAO] DocumentoDAO - consultaDocumentoOrigen():Documento "); 
            return em.createNamedQuery("Documento.findByIdOrigen")
				.setParameter("idOrigen", idDocumento).getResultList();
        }
        
        public List<Documento> consultaDocumentoReferenciaMover(Integer idDocumento){
            log.debug("-> [DAO] DocumentoDAO - consultaDocumentoReferenciaMover():Documento "); 
            return em.createNamedQuery("Documento.consultaDocumentoReferenciaMover")
				.setParameter("iddocumento", idDocumento).getResultList();
        }
        
        public Documento findByIdNroTramite(Integer nroTramite){
            return (Documento) em.createNamedQuery("Documento.findByIdNroTramite")
				.setParameter("nroTramite", nroTramite).getSingleResult();
        }
        
	public Documento findByFechaCreacion(Date datFechaCreacion) {
		log.debug("-> [DAO] DocumentoDAO - findByFechaCreacion():Documento ");

		return (Documento) em.createNamedQuery("Documento.findByFechacreacion")
				.setParameter("fechacreacion", datFechaCreacion)
				.getSingleResult();
	}
        
        
	public Documento findByIdDocumento(Integer iIdDocumento) {
		log.debug("-> [DAO] DocumentoDAO - findByIdDocumento():Documento ");

		Documento objDocumento = null;
		try {
		      objDocumento = em.find(Documento.class, iIdDocumento);
		} catch (EntityNotFoundException e) {
		      log.error(e.getMessage(), e);
		}
		return objDocumento;
	}

	public Documento findPropietarioByIdDocumento(Integer iIdDocumento) {
		log.debug("-> [DAO] DocumentoDAO - findPropietarioByIdDocumento():Documento ");

		String elquery = " SELECT  NEW Documento( "
				+ " d.idDocumento, d.propietario  )" + " from Documento d ";

		Query q;
		q = em.createQuery(elquery + " WHERE d.idDocumento = :iddocumento ");
		q.setParameter("iddocumento", iIdDocumento);
		Documento objDocumento = (Documento) q.getSingleResult();
		return objDocumento;
	}

	public Documento findDocExpedienteByIdDocumento(Integer iIdDocumento) {
		log.debug("-> [DAO] DocumentoDAO - findDocExpedienteByIdDocumento():Documento ");

		String elquery = " SELECT  NEW Documento( "
				+ " d.expediente, d.propietario )"
				+ " from Documento d LEFT JOIN d.expediente ex";

		Query q;
		q = em.createQuery(elquery
				+ " WHERE d.idDocumento = :iddocumento and ex.id = d.expediente.id");
		q.setParameter("iddocumento", iIdDocumento);
		Documento objDocumento = (Documento) q.getSingleResult();
		return objDocumento;

	}
        
        public boolean verificarDuplicidadDocumento(Integer tipo, String nro, Integer idUnidad){
            log.debug("-> [DAO] DocumentoDAO - verificarDuplicidadDocumento():Documento ");
            
            String sQuery = "SELECT d FROM Documento d ";
	    sQuery += "WHERE d.tipoDocumento.idtipodocumento = :idtipodocumento and d.numeroDocumento= :nrodocumento and d.unidadautor= :idUnidad and to_char(fechaCreacion,'YYYY') = TO_CHAR(sysdate,'YYYY')";
            
            Query obj = em.createQuery(sQuery).setParameter("idtipodocumento", tipo).setParameter("nrodocumento", nro).setParameter("idUnidad", idUnidad);
            List list = obj.getResultList();
            
            if (list == null || list.size()==0)
               return false;
            
            return true;
        }

	public Documento findDocumentoPrincipal(Integer iIdExpediente) {
		log.debug("-> [DAO] DocumentoDAO - findDocumentoPrincipal():Documento ");

		return (Documento) em
				.createNamedQuery("Documento.findDocumentoPrincipal")
				.setParameter("idexpediente", iIdExpediente).getSingleResult();
	}

	@SuppressWarnings("unchecked")
	public List<Documento> buscarLstDocumentoPorExpediente(Integer iIdExpediente){
		return  em
				.createNamedQuery("Documento.findByIdExpedienteDocumento")
				.setParameter("idexpediente", iIdExpediente).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Documento> buscarLstPor(Integer iIdExpediente, String sNroExpediente, String sNroDocumento, String sNroCaja) {
		log.debug("-> [DAO] DocumentoDAO - buscarLstPor():List<Documento> ");

		String sQuery = "SELECT d FROM Documento d ";
		sQuery += "WHERE d.expediente.id <> :idexpediente AND LOWER(d.expediente.nroexpediente) LIKE :nroexpediente AND LOWER(d.numeroDocumento) LIKE :nrodocumento AND d.estado= '"
				+ Constantes.ESTADO_ACTIVO + "'";
		if (!sNroCaja.equals("")) {
			sQuery += "AND LOWER(d.numeroCaja) LIKE :nrocaja";
		}
		Query obj1 = em
				.createQuery(sQuery)
				.setParameter("idexpediente", iIdExpediente)
				.setParameter("nroexpediente",
						"%" + sNroExpediente.toLowerCase() + "%")
				.setParameter("nrodocumento",
						"%" + sNroDocumento.toLowerCase() + "%");
		if (!sNroCaja.equals("")) {
			obj1.setParameter("nrocaja", "%" + sNroCaja.toLowerCase() + "%");
		}
		return obj1.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Documento> buscarLstPor(Integer iIdExpediente, String sNroExpediente, String sNroDocumento, String sNroCaja,
			Integer iIdTipoDocumento) {
		log.debug("-> [DAO] DocumentoDAO - buscarLstPor():List<Documento> ");

		String sQuery = "SELECT d FROM Documento d ";
		sQuery += "WHERE d.expediente.id <> :idexpediente AND LOWER(d.expediente.nroexpediente) LIKE :nroexpediente AND LOWER(d.numeroDocumento) LIKE :nrodocumento AND d.tipoDocumento.idtipodocumento = :idtipodocumento ";
		if (!sNroCaja.equals("")) {
			sQuery += "AND LOWER(d.numeroCaja) LIKE :nrocaja";
		}
		Query obj1 = em
				.createQuery(sQuery)
				.setParameter("idexpediente", iIdExpediente)
				.setParameter("nroexpediente",
						"%" + sNroExpediente.toLowerCase() + "%")
				.setParameter("nrodocumento",
						"%" + sNroDocumento.toLowerCase() + "%")
				.setParameter("idtipodocumento", iIdTipoDocumento);
		if (!sNroCaja.equals("")) {
			obj1.setParameter("nrocaja", "%" + sNroCaja.toLowerCase() + "%");
		}
		return obj1.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Documento> buscarLstDocumentoPor(char cEstado, char cPrincipal, Integer iIdPropietario) {
		log.debug("-> [DAO] DocumentoDAO - buscarLstDocumentoPor():List<Documento> ");

		return em
				.createNamedQuery(
						"Documento.buscarLstDocumentoPor_estado_principal_propietario")
				.setParameter("estado", cEstado)
				.setParameter("principal", cPrincipal)
				.setParameter("idpropietario", iIdPropietario).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Documento> buscarLstDocumentoPor(char cPrincipal,Integer idResponsable) {
		log.debug("-> [DAO] DocumentoDAO - buscarLstDocumentoPor():List<Documento> ");

		return em
				.createNamedQuery(
						"Documento.buscarLstDocumentoPor_responsables_Proceso_Expediente")
				.setParameter("principal", cPrincipal)
				.setParameter("idpropietario", idResponsable).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Documento> findByIdExpediente(Integer iIdExpediente) {
		log.debug("-> [DAO] DocumentoDAO - findByIdExpediente():List<Documento> ");

		return em.createNamedQuery("Documento.findByIdExpediente")
				.setParameter("idexpediente", iIdExpediente).getResultList();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Documento> findByIdPropietario(Integer iIdUsuario, Integer iIdRol, String sPerfil, char cEstado, char cPrincipal,
			String sTempo, Integer idSede, int accion, int accion2) {

		log.debug("-> [DAO] DocumentoDAO - findByIdPropietario():List<Documento> ");

		log.debug("parametros findByIdPropietario \n" + " iIdUsuario: "
				+ iIdUsuario + " \n" + " iIdRol:" + iIdRol + " \n"
				+ " sPerfil:" + sPerfil + " \n" + " cEstado:" + cEstado + " \n"
				+ " cPrincipal:" + cPrincipal + " \n" + " sTempo:" + sTempo
				+ " \n" + " idSede:" + idSede + " \n" + " accion:" + accion
				+ " \n" + " accion2:" + accion2 + " \n" + " ");

		String elquery = " SELECT  NEW Documento( "
				+ "  eta.descripcion, "
				+ "  d.idDocumento "
				+ ", d.numeroMesaPartes, ex.historico "
				+ ", d.leido "
				+ // ", (SELECT t.asunto  FROM Trazabilidaddocumento t " +
					// " WHERE t.documento.idDocumento = d.idDocumento " +
					// " AND t.nroregistro = ( SELECT MAX(tt.nroregistro) " +
					// " FROM Trazabilidaddocumento tt " +
					// " WHERE tt.documento.idDocumento = d.idDocumento )) " +
				", d.ultimoAsunto "
				+ ", d.fechaCreacion "
				+ ", d.fechaAccion"
				+ ", d.accion"
				+ ", d.fechaLimiteAtencion "
				+ ", concat( concat(d.tipoDocumento.nombre,' - '), d.numeroDocumento ) "
				+ ", ex.nroexpediente "
				+ ", pro.idproceso "
				+ ", pro.porcentajealertaA "
				+ ", pro.porcentajealertaR "
				+ ", pro.nombre "
				+ ", co.razonSocial"
				+ ", concat ( concat( concat( concat ( cl.nombres ,' ' ), cl.apellidoPaterno ),' ' ) , cl.apellidoMaterno ) "
				+ ", cl.razonSocial "
				+ ", ti.nombre "
				+ // " (SELECT concat( concat(t.remitente.nombres ,' ') ,t.remitente.apellidos ) "+" FROM Trazabilidaddocumento t "
					// +" WHERE t.documento.idDocumento = d.idDocumento " +
					// " AND t.nroregistro = ( SELECT MAX(tt.nroregistro) " +
					// " FROM Trazabilidaddocumento tt " +
					// " WHERE tt.documento.idDocumento = d.idDocumento )) , "+
				", d.remitente "
				+ ", d.estado"
				+ ", ex.actividad)"
				+ " from  Documento d "
				+ // " LEFT JOIN td.documento d " +
					// " LEFT JOIN td.remitente u " +
				" LEFT JOIN d.expediente ex " + " LEFT JOIN ex.proceso pro "
				+ " LEFT JOIN ex.idetapa eta "
				+ " LEFT JOIN ex.concesionario co "
				+ " LEFT JOIN ex.cliente cl "
				+ " LEFT JOIN cl.tipoIdentificacion ti ";
		String sqlMaster = "SELECT NEW Documento(v.etapa, v.iddocumento, v.nromesapartes, v.historico, "
				+ "		v.leido, v.ultimoasunto, v.fechacreacion, v.fechaaccion, (SELECT a FROM Accion a WHERE a.idAccion = v.idaccion), v.fechalimiteatencion, "
				+ "		v.documento, v.nroexpediente, v.idproceso, v.porcentajealerta1, v.porcentajealerta2, v.nombreproceso, "
				+ "		v.concesionario, v.cliente, v.razonsocialcliente, v.tipoidentificacion, v.remitente, v.estadodocumen, v.actividad) FROM VistaGridUF v ";
		String nombre = null;
		// CODIGO TEMPORAL
		if (iIdUsuario != null) {
			if (sTempo == null) {
				nombre = "Documento.findByIdUsuario";
			} else {
				nombre = "Documento.findByIdUsuarioXX";
			}
		} else if (sPerfil != null) {
			if (sTempo == null) {
				nombre = "Documento.findByPerfil";
			} else {
				nombre = "Documento.findByPerfilTodo";
			}
		}
		if (iIdUsuario != null) {
			Query q1 = null;
			Query q2 = null;
			Query q3 = null;

			String sql1 = sqlMaster;
			String sql2 = sqlMaster;
			String sql3 = sqlMaster;
			if (sTempo == null) {
				sql1 += "WHERE  v.principal = :principal AND "
						+ "(v.estadodocumen = :estado AND "
						+ "("
						+ "(v.idpropietario = :idusr and "
						+ "("
						+ "SELECT count(r.idreemplazado) FROM Reemplazo r "
						+ "WHERE r.estado = 'A' AND "
						+ "r.idreemplazado = :idusr and "
						+ "r.idproceso = v.idproceso and "
						+ "CURRENT_DATE > r.fechainicialreemplazo and "
						+ "CURRENT_DATE <r.fechafinalreemplazo ) = 0 "
						+ ") "
						+ "OR "
						+ "("
						+ "v.idpropietario in (SELECT r.idreemplazado FROM Reemplazo r "
						+ "WHERE r.estado = 'A' AND "
						+ "r.idusuario = :idusr and "
						+ "r.idproceso = v.idproceso and "
						+ "CURRENT_DATE > r.fechainicialreemplazo and "
						+ "CURRENT_DATE <r.fechafinalreemplazo )" + ")" + ") "
						+ ") AND " + "v.estadoexpediente <>'T' ";

				sql2 += "WHERE  v.principal = :principal AND "
						+ "(v.estadodocumen = :estado AND "
						+ "("
						+ "v.idpropietario in (SELECT u.idusuario FROM Usuario u WHERE :idusr MEMBER OF u.compartidoxusuario)"
						+

						") AND " + "v.estadoexpediente <>'T') ";

				sql3 += "WHERE  v.principal = :principal AND "
						+ "(v.estadodocumen = :estado AND "
						+ "("
						+ "v.idpropietario in (SELECT r.idreemplazado FROM Reemplazo r "
						+ "WHERE r.estado = 'A' AND "
						+ "r.idusuario in (SELECT u.idusuario FROM Usuario u WHERE :idusr MEMBER OF u.compartidoxusuario) and "
						+ "r.idproceso = v.idproceso and "
						+ "CURRENT_DATE > r.fechainicialreemplazo and "
						+ "CURRENT_DATE <r.fechafinalreemplazo )" +

						") AND " + "v.estadoexpediente <>'T') ";

				q1 = em.createQuery(sql1);
				q2 = em.createQuery(sql2);
				q3 = em.createQuery(sql3);

			} else {
				q1 = em.createNamedQuery(nombre);
			}
			log.info("idUsuario a buscar:" + iIdUsuario);
			q1.setParameter("idusr", iIdUsuario);
			q1.setParameter("estado", cEstado);
			if (sTempo == null) {
				q1.setParameter("principal", cPrincipal);
			}
			// ------------------------------------------
			q2.setParameter("idusr", iIdUsuario);
			q2.setParameter("estado", cEstado);
			if (sTempo == null) {
				q2.setParameter("principal", cPrincipal);
			}
			// ------------------------------------------
			q3.setParameter("idusr", iIdUsuario);
			q3.setParameter("estado", cEstado);
			if (sTempo == null) {
				q3.setParameter("principal", cPrincipal);
			}
			// ------------------------------------------
			List data1 = q1.getResultList();
			List data2 = q2.getResultList();
			List data3 = q3.getResultList();

			return unirData(data1, data2, data3);
		} else if (iIdRol != null) {
			Query q;
			if (accion2 == 0) {
				q = em.createQuery(elquery + " "
						+ " WHERE :idrol in elements(d.propietario.roles) "
						+ "AND d.estado = :estado "
						+ "AND d.accion.idAccion=:accion "
						+ "AND d.propietario.unidad.sede.idsede=:sede "
						+ "ORDER BY d.idDocumento DESC");
				log.debug("query DOcumento 2:" + elquery + " "
						+ " WHERE :idrol in elements(d.propietario.roles) "
						+ "AND d.estado = :estado "
						+ "AND d.accion.idAccion=:accion "
						+ "AND d.propietario.unidad.sede.idsede=:sede "
						+ "ORDER BY d.idDocumento DESC");
				/*
				 * sql +=
				 * "WHERE :idrol in elements(SELECT r FROM Rol r WHERE v.idpropietario in elements(r.usuarios)) "
				 * + "AND v.estadodocumen = :estado AND v.idaccion=:accion " +
				 * "AND v.sede=:sede " + "ORDER BY v.iddocumento DESC"; q =
				 * em.createQuery(sql);
				 */
			} else {
				q = em.createQuery(elquery
						+ " "
						+ " WHERE :idrol in elements(d.propietario.roles) "
						+ " AND d.estado = :estado "
						+ " AND d.propietario.unidad.sede.idsede=:sede "
						+ " AND (d.accion.idAccion=:accion OR d.accion.idAccion=:accion2)"
						+ " ORDER BY d.idDocumento DESC");
				log.debug("query DOcumento 3 :"
						+ elquery
						+ " "
						+ " WHERE :idrol in elements(d.propietario.roles) "
						+ " AND d.estado = :estado "
						+ " AND d.propietario.unidad.sede.idsede=:sede "
						+ " AND (d.accion.idAccion=:accion OR d.accion.idAccion=:accion2)"
						+ " ORDER BY d.idDocumento DESC");
				/*
				 * sql +=
				 * "WHERE :idrol in elements(SELECT r FROM Rol r WHERE v.idpropietario in elements(r.usuarios)) "
				 * +
				 * "AND v.estadodocumen = :estado AND v.sede=:sede AND (v.idaccion=:accion OR v.idaccion=:accion2)"
				 * + "ORDER BY v.iddocumento DESC"; q = em.createQuery(sql);
				 */
				q.setParameter("accion2", accion2);
			}
			q.setParameter("idrol", iIdRol);
			q.setParameter("estado", cEstado);
			// q.setParameter("principal", cPrincipal);
			q.setParameter("accion", accion);
			q.setParameter("sede", idSede);
			return q.getResultList();
		} else if (sPerfil != null) {
			log.debug("sPerfil!=null");
			Query q = em.createNamedQuery(nombre);
			q.setParameter("perfil", sPerfil);
			q.setParameter("estado", cEstado);
			q.setParameter("idsede", idSede);
			if (sTempo == null) {
				q.setParameter("principal", cPrincipal);
			}
			return q.getResultList();
		} else {
			return null;
		}
	}

	public Integer findCantByIdPropietario(Integer iIdUsuario, Integer iIdRol, String sPerfil, char cEstado, char cPrincipal, String sTempo,
			Integer idSede, int accion, int accion2) {
               log.debug("-> [DAO] DocumentoDAO - findCantByIdPropietario():Integer ");

		// (objUsuario.getIdusuario(),null,null,Constantes.ESTADO_ACTIVO,Constantes.DOCUMENTO_PRINCIPAL,null,null,0,0);
		/*log.debug("parametros findByIdPropietario \n" + " iIdUsuario: "
				+ iIdUsuario + " \n" + " iIdRol:" + iIdRol + " \n"
				+ " sPerfil:" + sPerfil + " \n" + " cEstado:" + cEstado + " \n"
				+ " cPrincipal:" + cPrincipal + " \n" + " sTempo:" + sTempo
				+ " \n" + " idSede:" + idSede + " \n" + " accion:" + accion
				+ " \n" + " accion2:" + accion2 + " \n" + " ");

		String elquery = " SELECT  count( "
				+ "  d.idDocumento  ) from  Documento d "
				+ // " LEFT JOIN td.documento d " +
					// " LEFT JOIN td.remitente u " +
				" LEFT JOIN d.expediente ex " + " LEFT JOIN ex.proceso pro "
				+ " LEFT JOIN ex.concesionario co "
				+ " LEFT JOIN ex.cliente cl "
				+ " LEFT JOIN cl.tipoIdentificacion ti ";
		String nombre = null;
		// CODIGO TEMPORAL
		if (iIdUsuario != null) {
			if (sTempo == null) {
				nombre = "Documento.findByIdUsuario";
			} else {
				nombre = "Documento.findByIdUsuarioXX";
			}
		} else if (sPerfil != null) {
			if (sTempo == null) {
				nombre = "Documento.findByPerfil";
			} else {
				nombre = "Documento.findByPerfilTodo";
			}
		}
		if (iIdUsuario != null) {
			Query q;
			if (sTempo == null) {
				q = em.createQuery(elquery
						+ " "
						+ "WHERE d.principal = :principal "
						+ "AND (d.estado = :estado AND ("
						+ "                              (d.propietario.idusuario = :idusr and (SELECT count(r.idreemplazado) FROM Reemplazo r WHERE r.estado = 'A' AND r.idreemplazado = :idusr and r.idproceso = d.expediente.proceso.idproceso and CURRENT_DATE > r.fechainicialreemplazo and CURRENT_DATE <r.fechafinalreemplazo ) = 0 ) "
						+ "					OR d.propietario.idusuario in "
						+ "(SELECT r.idreemplazado FROM Reemplazo r WHERE r.estado = 'A' AND r.idusuario = :idusr and r.idproceso = d.expediente.proceso.idproceso and CURRENT_DATE > r.fechainicialreemplazo and CURRENT_DATE <r.fechafinalreemplazo )"
						+ ") ) AND d.expediente.estado <>'T'  "
						+ "  ORDER BY d.fechaAccion ");

				// .setParameter("iddoc",iIdDoc);
				// (Integer iddocumento, Character leido , String asunto ,
				// Date fechacreacion ,Date fechalimiteatencion ,
				// String tipodocumento , String nroexpediente
				// , String nombreProceso ,String razonsocialConcesionario ,
				// String clienteNombre , String clienteRazonSocial , String
				// clienteTipoIdentificacion )

			} else {
				q = em.createNamedQuery(nombre);
			}

			q.setParameter("idusr", iIdUsuario);
			q.setParameter("estado", cEstado);
			if (sTempo == null) {
				q.setParameter("principal", cPrincipal);
			}
			return Integer.valueOf(((Long) q.getSingleResult()).intValue());
		} else if (iIdRol != null) {
			Query q;
			if (accion2 == 0) {
				q = em.createQuery(elquery + " "
						+ " WHERE :idrol in elements(d.propietario.roles) "
						+ "AND d.estado = :estado "
						+ "AND d.accion.idAccion=:accion "
						+ "AND d.propietario.unidad.sede.idsede=:sede "
						+ "ORDER BY d.idDocumento DESC");
			} else {
				q = em.createQuery(elquery
						+ " "
						+ " WHERE :idrol in elements(d.propietario.roles) "
						+ " AND d.estado = :estado "
						+ " AND d.propietario.unidad.sede.idsede=:sede "
						+ " AND (d.accion.idAccion=:accion OR d.accion.idAccion=:accion2)"
						+ " ORDER BY d.idDocumento DESC");

				q.setParameter("accion2", accion2);
			}
			q.setParameter("idrol", iIdRol);
			q.setParameter("estado", cEstado);
			// q.setParameter("principal", cPrincipal);
			q.setParameter("accion", accion);
			q.setParameter("sede", idSede);
			return Integer.valueOf(((Long) q.getSingleResult()).intValue());
		} else if (sPerfil != null) {
			log.debug("sPerfil!=null");
			Query q = em.createNamedQuery(nombre);
			q.setParameter("perfil", sPerfil);
			q.setParameter("estado", cEstado);
			q.setParameter("idsede", idSede);
			if (sTempo == null) {
				q.setParameter("principal", cPrincipal);
			}
			return new Integer(((Long) q.getSingleResult()).intValue());
		} else {
			return null;
		}*/
		if(iIdUsuario != null){
			String sql = "SELECT COUNT(d.idDocumento) FROM Documento d WHERE d.propietario.idusuario = :idUsuario AND d.estado = :estado AND d.leido = :leido";
			Query q = em.createQuery(sql).setParameter("idUsuario", iIdUsuario).setParameter("estado", Constantes.ESTADO_ACTIVO).setParameter("leido", Constantes.No);
			return (Integer)q.getSingleResult();
		}
		return null;
	}
        
         public Integer findCantFirmas(Usuario usuario){
             log.debug("-> [DAO] DocumentoDAO - findCantFirmas():Integer ");

		if (usuario != null) {
                        boolean bandera = false;
                        Usuario u = new Usuario();
                        u.setIdUnidadPerfil(usuario.getIdUnidadPerfil());
                        u.setIdFuncionPerfil(usuario.getIdFuncionPerfil());
                        u.setIdusuario(usuario.getIdUsuarioPerfil());

                        List<Usuarioxunidadxfuncion> lista = usuarioxunidadxfuncionDAO.getUsuarioByUnidadByFuncionListRol(u);
                        if (lista!=null && lista.size()>0){
                           for (int i=0;i<lista.size();i++){
                              if (lista.get(i).getIdrol().toString().equals(Constantes.COD_ROL_MENSAJERIA.toString())){
                                  bandera = true;
                                  break;
                              }
                           }
                        }
                        
			Query q;
			String sql = "SELECT COUNT(f.id) FROM FilaBandejaUF f ";
			sql += "WHERE f.propietario.idusuario = :idUsuario AND f.unidadpropietario= :idUnidad and f.cargopropietario= :idCargo and f.chrestado in ('A', 'P')  AND f.flagatendido is null AND f.flagmultiple is null AND f.leido = :leido ";
			sql += " AND f.firmado = 'S' and f.estadorecepcionvirtual = '0'";
                        
                        if (bandera){
                            sql += " AND f.despacho = '0' ";
                        }
                        
			q = em.createQuery(sql);
			log.debug(sql);

			q.setParameter("idUsuario", usuario.getIdUsuarioPerfil());
                        q.setParameter("idUnidad", usuario.getIdUnidadPerfil());
                        q.setParameter("idCargo", usuario.getIdFuncionPerfil());
			//q.setParameter("chrEstado", estado);
			q.setParameter("leido", String.valueOf(Constantes.ESTADO_NO_LEIDO));
			
			return Integer.valueOf(((Long) q.getSingleResult()).intValue());
		} else {
			return 0;
		}
         }

        public Integer findCantMisRecepcionVirtualObservados(Usuario usuario, char estado, char docPrincipal) {
               log.debug("-> [DAO] DocumentoDAO - findCantMisRecepcionVirtualObservados():Integer ");
               
               if (usuario != null) {
			Query q;
			String sql = "SELECT COUNT(f.id) FROM FilaBandejaUF f ";
			sql += "WHERE f.propietario.idusuario = :idUsuario AND f.chrestado in ('A', 'P') AND f.leido = :leido ";
			sql += "AND f.autor.idusuario = :idUsuario and f.unidadpropietario= :idUnidad and f.cargopropietario= :idCargo AND f.numeroTrazabilidad = 1  and f.estadorecepcionvirtual != '0'";
			q = em.createQuery(sql);
			q.setParameter("idUsuario", usuario.getIdUsuarioPerfil());
                        q.setParameter("idUnidad", usuario.getIdUnidadPerfil());
                        q.setParameter("idCargo", usuario.getIdFuncionPerfil());
			//q.setParameter("chrEstado", estado);
			q.setParameter("leido", String.valueOf(Constantes.ESTADO_NO_LEIDO));
                        return Integer.valueOf(((Long) q.getSingleResult()).intValue());
		} else {
                        System.out.println("despues..");
			return 0;
		}
	} 
         
	public Integer findCantDocumentosRecibidos(Usuario usuario, char estado, char docPrincipal) {
               
		log.debug("-> [DAO] DocumentoDAO - findCantDocumentosRecibidos():Integer ");

		if (usuario != null) {
                        boolean bandera = false;
                        Usuario u = new Usuario();
                        u.setIdUnidadPerfil(usuario.getIdUnidadPerfil());
                        u.setIdFuncionPerfil(usuario.getIdFuncionPerfil());
                        u.setIdusuario(usuario.getIdUsuarioPerfil());

                        List<Usuarioxunidadxfuncion> lista = usuarioxunidadxfuncionDAO.getUsuarioByUnidadByFuncionListRol(u);
                        if (lista!=null && lista.size()>0){
                           for (int i=0;i<lista.size();i++){
                              if (lista.get(i).getIdrol().toString().equals(Constantes.COD_ROL_MENSAJERIA.toString())){
                                  bandera = true;
                                  break;
                              }
                           }
                        }
                    
			Query q;
			String sql = "SELECT COUNT(f.id) FROM FilaBandejaUF f ";
			sql += "WHERE f.propietario.idusuario = :idUsuario AND f.unidadpropietario= :idUnidad and f.cargopropietario= :idCargo and f.chrestado in ('A', 'P')  AND f.flagatendido is null AND f.flagmultiple is null AND f.leido = :leido ";
			sql += "AND f.firmado = 'N' AND (f.autor.idusuario != :idUsuario OR f.numeroTrazabilidad != 1 ) and f.estadorecepcionvirtual ='0'";
			
                        if (bandera)
                           sql += " AND f.despacho = '0' "; 
                            
                        q = em.createQuery(sql);
			log.debug(sql);

			q.setParameter("idUsuario", usuario.getIdUsuarioPerfil());
                        q.setParameter("idUnidad", usuario.getIdUnidadPerfil());
                        q.setParameter("idCargo", usuario.getIdFuncionPerfil());
			//q.setParameter("chrEstado", estado);
			q.setParameter("leido", String.valueOf(Constantes.ESTADO_NO_LEIDO));
			
			return Integer.valueOf(((Long) q.getSingleResult()).intValue());
		} else {
			return 0;
		}
	}

	public Integer findCantMisExpedientes(Usuario usuario, char estado, char docPrincipal) {
                log.debug("-> [DAO] DocumentoDAO - findCantMisExpedientes():Integer ");

		if (usuario != null) {
			Query q;
			String sql = "SELECT COUNT(f.id) FROM FilaBandejaUF f ";
			sql += "WHERE f.propietario.idusuario = :idUsuario AND f.chrestado in ('A', 'P') AND f.leido = :leido ";
			sql += "AND f.autor.idusuario = :idUsuario and f.unidadpropietario= :idUnidad and f.cargopropietario= :idCargo AND f.numeroTrazabilidad = 1  and f.estadorecepcionvirtual = '0'";
			q = em.createQuery(sql);
			q.setParameter("idUsuario", usuario.getIdUsuarioPerfil());
                        q.setParameter("idUnidad", usuario.getIdUnidadPerfil());
                        q.setParameter("idCargo", usuario.getIdFuncionPerfil());
			//q.setParameter("chrEstado", estado);
			q.setParameter("leido", String.valueOf(Constantes.ESTADO_NO_LEIDO));
			return Integer.valueOf(((Long) q.getSingleResult()).intValue());
		} else {
			return 0;
		}
	}
        
        public Integer findCantMisRecepcionVirtual(Usuario usuario, char estado, char docPrincipal) {
                log.debug("-> [DAO] DocumentoDAO - findCantMisExpedientes():Integer ");

		if (usuario != null) {
			Query q;
			String sql = "SELECT COUNT(f.id) FROM FilaBandejaUF f ";
			sql += "WHERE f.propietario.idusuario = :idUsuario AND f.chrestado in ('A', 'P') AND f.leido = :leido ";
			sql += "AND f.autor.idusuario = :idUsuario and f.unidadpropietario= :idUnidad and f.cargopropietario= :idCargo AND f.numeroTrazabilidad = 1 ";
			q = em.createQuery(sql);
			q.setParameter("idUsuario", usuario.getIdUsuarioPerfil());
                        q.setParameter("idUnidad", usuario.getIdUnidadPerfil());
                        q.setParameter("idCargo", usuario.getIdFuncionPerfil());
			//q.setParameter("chrEstado", estado);
			q.setParameter("leido", String.valueOf(Constantes.ESTADO_NO_LEIDO));
			return Integer.valueOf(((Long) q.getSingleResult()).intValue());
		} else {
			return 0;
		}

	}

        public List<Documento> findDocumentoPorNumerar(Usuario objUsuario, Documento d) {

            log.debug("-> [DAO] DocumentoDAO - findDocumentosPorNumerar():List<Documento> ");

            String elquery = " SELECT  NEW Documento( " +
            "  d.idDocumento " +
            ", d.tipoDocumento" +
            ", d.fechaDocumento " +
            ", d.fechaCreacion " +
            ", d.asunto " + ", d.autor, d.firmado, d.enumerado )" +
            " from Documento d "; 

		Query q;
		q = em.createQuery(elquery + " WHERE d.idDocumento = :iddocumento "
				+ " AND d.enumerado = 'N'   "
				+ " AND d.propietario.idusuario = :idusuario   "
                                + " AND d.unidadpropietario = :idunidad   "
                                + " AND d.cargopropietario = :idcargo   "
				+ " AND d.estado in ('A','P') AND d.documentoreferencia is NULL");
		/* */

                q.setParameter("iddocumento", d.getIdDocumento());
		q.setParameter("idusuario", objUsuario.getIdUsuarioPerfil());
                q.setParameter("idunidad", objUsuario.getIdUnidadPerfil());
                q.setParameter("idcargo", objUsuario.getIdFuncionPerfil());
		List<Documento> result = q.getResultList();
                return result;

	}
        
	@SuppressWarnings("unchecked")
	@Override
    public List<Documento> findDocumentosPorNumerar(Usuario objUsuario, Expediente expediente) {

	log.debug("-> [DAO] DocumentoDAO - findDocumentosPorNumerar():List<Documento> ");

        String elquery = " SELECT  NEW Documento( " +
        "  d.idDocumento " +
        ", d.tipoDocumento" +
        ", d.fechaDocumento " +
        ", d.fechaCreacion " +
        ", d.asunto " + ", d.autor, d.firmado, d.enumerado )" +
        " from Documento d " +
        " LEFT JOIN d.expediente ex ";// +
      
		Query q;
		q = em.createQuery(elquery + " WHERE ex.id = :expediente "
				+ " AND ( d.enumerado = 'N'  ) "
				+ " AND ( d.propietario.idusuario = :idusuario  ) "
                                + " AND ( d.unidadpropietario = :idunidad  ) "
                                + " AND ( d.cargopropietario = :idcargo  ) "
				+ " AND d.estado = :estado AND d.documentoreferencia is NULL");
		/* */

		q.setParameter("expediente", expediente.getIdexpediente());
		q.setParameter("idusuario", objUsuario.getIdUsuarioPerfil());
                q.setParameter("idunidad", objUsuario.getIdUnidadPerfil());
                q.setParameter("idcargo", objUsuario.getIdFuncionPerfil());
		q.setParameter("estado", Constantes.ESTADO_ACTIVO);
		List<Documento> result = q.getResultList();

		return result;

	}

    @SuppressWarnings("unchecked")
	public List<Documento> findDocumentosPorFirmar(Usuario objUsuario, Expediente expediente) {

    	log.debug("-> [DAO] DocumentoDAO - findDocumentosPorFirmar():List<Documento> ");

        String elquery = " SELECT  NEW Documento( " +
        "  d.idDocumento " +
        ", d.tipoDocumento" +
        ", d.fechaDocumento " +
        ", d.fechaCreacion " +
        ", d.asunto " + ", d.autor, d.firmado, d.enumerado )" +
        " from Documento d " +
        " LEFT JOIN d.expediente ex " +
        " LEFT JOIN ex.proceso pro " +
        " LEFT JOIN ex.concesionario co " +
        " LEFT JOIN ex.cliente cl " +
        " LEFT JOIN cl.tipoIdentificacion ti ";

        Query q;
        q = em.createQuery(elquery + " WHERE ex.id = :expediente " +
        		" AND d.firmante.idusuario = :firmante " +
        		" AND ( d.firmado ='N' OR d.enumerado = 'N' ) " +
        		" AND d.estado = :estado AND d.documentoreferencia is NULL");
        /* */

        q.setParameter("expediente", expediente.getIdexpediente());
        q.setParameter("firmante", objUsuario.getIdusuario());
        q.setParameter("estado", Constantes.ESTADO_ACTIVO);
        List<Documento> result = q.getResultList();

        log.debug("findDocumentosPorNumerar size " + (result != null ? "" + result.size() : "null"));
        return result;

    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Documento> obtenerDoc_enumerar(List<Integer> idDocumentos) {

    	log.debug("-> [DAO] DocumentoDAO - obtenerDoc_enumerar():List<Documento> ");

    	if (idDocumentos != null && idDocumentos.size() > 0) {
            String elquery = "SELECT d FROM Documento d WHERE d.idDocumento in (:idDocumentos)";
            Query q = em.createQuery(elquery);
            q.setParameter("idDocumentos", idDocumentos);
            List<Documento> documentos = q.getResultList();
            return documentos;

        }
        return null;
    }
	@Override
	public Documento numerarDocumento(Documento documento) {

		log.debug("-> [DAO] DocumentoDAO - numerarDocumento():Documento ");

		// solo se enumeran los documentos con tipo de numeracion automatica
		if (documento.getTipoDocumento().getTiponumeracion().equals("A")) {
            Unidad unidad = null;//documento.getExpediente().getProceso().getResponsable().getUnidad();
            Integer idTipoDocumento = documento.getTipoDocumento().getIdtipodocumento();
			String numeroDocumento = documento.getNumeroDocumento();
            while (unidad != null && (StringUtils.isBlank(numeroDocumento) || numeroDocumento.equalsIgnoreCase("s/n"))) {
				Integer idUnidad = unidad.getIdunidad();
				try {
                    Numeracion numeracion = (Numeracion) em.createNamedQuery("Numeracion.findByIdnumeracion").setParameter("idunidad", idUnidad).setParameter("idtipodocumento", idTipoDocumento).getSingleResult();
					if (numeracion != null) {
						int numeroActual = numeracion.getNumeroactual();
                        if (numeroDocumento == null || numeroDocumento.equalsIgnoreCase("s/n")) {
							numeroDocumento = numeracion.formatearNumero();
							numeracion.setNumeroactual(numeroActual + 1);
							em.merge(numeracion);
							unidad = null;
						}
					} else {
						unidad = unidad.getDependencia();
					}
				} catch (NoResultException ex) {
                    log.debug("MMMMM no result for numeration idtipodoc:" + idTipoDocumento + " idunidad:" + idUnidad);
					unidad = unidad.getDependencia();
                    log.debug(" switching to unid :" + (unidad != null ? unidad.getNombre() : "null"));
				}
			}
			log.debug("numeroDocumento generado : " + numeroDocumento);
            if (numeroDocumento != null && !numeroDocumento.equals(documento.getNumeroDocumento())) {
                Query q = em.createQuery("UPDATE Documento d set d.numeroDocumento=:numeroDocumento WHERE d.idDocumento=:idDocumento");
				q.setParameter("numeroDocumento", numeroDocumento);
				q.setParameter("idDocumento", documento.getIdDocumento());
				q.executeUpdate();
                log.info("Se modifico el numero de documento para el documento [" + documento.getAsunto() + "] con el valor: " + numeroDocumento);
				documento.setNumeroDocumento(numeroDocumento);
				return documento;
			}
            log.warn("No existe una numeracion para el tipo de documento [" + documento.getTipoDocumento().getNombre() + "]. No se realizo ningun cambio para el documento con id " + documento.getIdDocumento());
		}
		return null;
	}

	/**REN Se llama cuando el usuario final presiona Adjuntar Documento---------------------------*/
	@SuppressWarnings("unused")
   	@Override
	public Documento saveDocumento(Documento objDocumento) {
		log.debug("-> [DAO] DocumentoDAO - saveDocumento():Documento ");
                try{
                    if (objDocumento.getIdDocumento() == null) {
                        if (StringUtils.isBlank(objDocumento.getNumeroDocumento())) 
                           objDocumento.setNumeroDocumento(Constantes.NRODCUMENTO_SIN_NUMERO);
			
                        objDocumento.setDelExpediente(Constantes.DOCUMENTO_DEL_EXPEDIENTE);
			em.persist(objDocumento); // Nuevo
                        em.flush();
                        em.refresh(objDocumento);
                    } else {
                        if (StringUtils.isBlank(objDocumento.getNumeroDocumento())) 
                           objDocumento.setNumeroDocumento(Constantes.NRODCUMENTO_SIN_NUMERO);
		       
                         em.merge(objDocumento); // Actualizacion
                         em.flush(); 
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
		return objDocumento;
	}
        
        @SuppressWarnings("unused")
   	@Override
	public Documento saveDocumentoSequence(Documento objDocumento) {
		log.debug("-> [DAO] DocumentoDAO - saveDocumento():Documento ");
                try{
                        if (StringUtils.isBlank(objDocumento.getNumeroDocumento())) 
                           objDocumento.setNumeroDocumento(Constantes.NRODCUMENTO_SIN_NUMERO);
			
                        objDocumento.setDelExpediente(Constantes.DOCUMENTO_DEL_EXPEDIENTE);
			em.persist(objDocumento); 
                        em.flush();
                        em.refresh(objDocumento);
                }catch(Exception e){
                    e.printStackTrace();
                }
		return objDocumento;
	}

   @SuppressWarnings({ "unchecked", "rawtypes" })
   @Override
   public void sendNotificacionXEnumerar(Documento documento, Usuario remitente, Set informed) {

	   log.debug("-> [DAO] DocumentoDAO - sendNotificacionXEnumerar():void ");

	   List<NotificacionxEnumerar> destinatarios = em.createNamedQuery("NotificacionxEnumerar.findByAll").setParameter("iddocumento", documento.getIdDocumento()).setParameter("tiponotificacion", Constantes.TIPO_NOTIFICACION_NUMERACION_DESTINATARIO).getResultList();

      //aca logica para enviar notificaciones a los destinatarios NotificacionxEnumerarPK
      for (NotificacionxEnumerar d : destinatarios) {
         Usuario destinatario = d.getUsuario();

         if (!informed.add(destinatario)) {
            log.debug("El usuario {} ya ha sido informado previamente, no se enviara nuevo informativo", destinatario.getUsuario());
            continue;
         }

         Notificacion noti = getNotificacionDestinatario(documento, remitente, destinatario, new Date(), Constantes.ESTADO_ACTIVO);
         em.persist(noti);
       //  mailService.ChaskiMail(Constantes.CONFIGNOTIFMAIL_DOCUMENTO_CCREENVIAR, remitente, destinatario, documento);
      }

      List<NotificacionxEnumerar> copias = em.createNamedQuery("NotificacionxEnumerar.findByAll").setParameter("iddocumento", documento.getIdDocumento()).setParameter("tiponotificacion", Constantes.TIPO_NOTIFICACION_NUMERACION_DOCUMENTOCONCOPIA).getResultList();

      // aca logica para enviar notificaciones a los destinatarios
      for (NotificacionxEnumerar d : copias) {
         Usuario destinatario = d.getUsuario();

         if (!informed.add(destinatario)) {
            log.debug("El usuario {} ya ha sido informado previamente, no se enviara nuevo informativo", destinatario.getUsuario());
            continue;
         }

         Notificacion noti = getNotificacionCopia(documento, remitente, destinatario, new Date(), Constantes.ESTADO_ACTIVO);
         em.persist(noti);
       //  mailService.ChaskiMail(Constantes.CONFIGNOTIFMAIL_DOCUMENTO_CCREENVIAR, remitente, destinatario, documento);
      }
   }

	Notificacion getNotificacionDestinatario(Documento objDocumento, Usuario remitente, Usuario destinatario, Date fecha, char estado) {
		Notificacion noti = new Notificacion();
		noti.setEstado(estado);
		noti.setIdusuario(destinatario);
		noti.setIddocumento(objDocumento);
		noti.setFechanotificacion(fecha);
		noti.setTiponotificacion(Constantes.TIPO_NOTIFICACION_NUMERACION_DESTINATARIO);
		noti.setAsunto("Se aprobo el Documento Nro. "+objDocumento.getNumeroDocumento()+" en Control de Calidad");
		noti.setContenido("Le informamos que el usuario " + remitente.getNombres() + " " + remitente.getApellidos() + " le ha enviado una copia del documento " + objDocumento.getTipoDocumento().getNombre() + " - " + objDocumento.getNumeroDocumento() + " perteneciente al expediente " + objDocumento.getExpediente().getNroexpediente());  //", relacionado al proceso " + objDocumento.getExpediente().getProceso().getNombre());
		noti.setLeido(Constantes.ESTADO_NO_LEIDO);

		return noti;
	}

	Notificacion getNotificacionCopia(Documento objDocumento, Usuario remitente, Usuario destinatario, Date fecha, char estado) {
		Notificacion noti = new Notificacion();
		noti.setEstado(estado);
		noti.setIdusuario(destinatario);
		noti.setIddocumento(objDocumento);
		noti.setFechanotificacion(fecha);
		noti.setTiponotificacion(Constantes.TIPO_NOTIFICACION_NUMERACION_DOCUMENTOCONCOPIA);
		noti.setAsunto("Se aprobo el Documento Nro. "+objDocumento.getNumeroDocumento()+" en Control de Calidad");
                noti.setContenido("Le informamos que el usuario " + remitente.getNombres() + " " + remitente.getApellidos() + " le ha enviado una copia del documento " + objDocumento.getTipoDocumento().getNombre() + " - " + objDocumento.getNumeroDocumento() + " perteneciente al expediente " + objDocumento.getExpediente().getNroexpediente() + ""); //, relacionado al proceso " + objDocumento.getExpediente().getProceso().getNombre());
		noti.setLeido(Constantes.ESTADO_NO_LEIDO);

		return noti;
	}
	/**Metodo Antiguo
	@SuppressWarnings("unchecked")
	@Override
	public List<Trazabilidaddocumento> findByfechaLimiteAtencion(Date fechaInicio, Date fechaFin, Integer idUsuario, Integer idProceso, boolean propios) {

		log.debug("-> [DAO] DocumentoDAO - findByfechaLimiteAtencion():List<Trazabilidaddocumento> ");

		String sql = "SELECT DISTINCT td FROM Trazabilidaddocumento td WHERE td.documento.estado = :estado ";
		sql += "AND td.fechalimiteatencion >= :fechainicio AND td.fechalimiteatencion <= :fechafin ";
		sql += "AND td.fechacreacion = (SELECT MAX(td2.fechacreacion) FROM Trazabilidaddocumento td2 where td2.documento.idDocumento = td.documento.idDocumento) ";

		if(idProceso != null && idProceso != 0){
			sql += "AND td.documento.expediente.proceso.idproceso = :idProceso ";
		}

		if(propios){
			/**Son los documentos que tengo en la bandeja --------------------------------------------------------------------------------------*
			sql += "AND td.destinatario.idusuario = :idUsuario ";
		}else{
			/**Son los documentos que he enviado -----------------------------------------------------------------------------------------------*
			sql += "AND (td.remitente.idusuario = :idUsuario OR (td.documento.autor.idusuario = :idUsuario AND td.destinatario.idusuario != :idUsuario)) ";
		}

		sql += "ORDER BY td.fechacreacion DESC";

		Query q = em.createQuery(sql);
		q.setParameter("estado", Constantes.ESTADO_ACTIVO)
		 .setParameter("fechainicio", fechaInicio)
		 .setParameter("fechafin", fechaFin)
		 .setParameter("idUsuario", idUsuario);

		log.debug("\n  verSeguimiento " + Constantes.VER_SEGUIMIENTO_SI);

		if(idProceso != null && idProceso != 0){
			q.setParameter("idProceso", idProceso);
		}

		List<Trazabilidaddocumento> lista = q.getResultList();
		log.debug(" tamagno ff de lista <SeguimientoHoraDia> " + lista.size());
		return lista;
	}
	*/
	@SuppressWarnings("unchecked")
	@Override
	public List<FilaSeguimiento> findByfechaLimiteAtencion(Date fechaInicio, Date fechaFin, Integer idUsuario, Integer idProceso, boolean propios) {
		List<FilaSeguimiento> lista = null;
		boolean bandera = false;
	try{
		log.debug("-> [DAO] DocumentoDAO - findByfechaLimiteAtencion():List<Trazabilidaddocumento> ");
		String sql = null;
        if (propios){
			List <Parametro> listaParametro =   parametroService.findByTipo("SEGUIMIENTO_ROL");
			Usuario usuario = usuarioDAO.findByIdUsuario(idUsuario);

			if (listaParametro!=null){
			   /* for(int i=0;i<listaParametro.size();i++){
				    for(int j=0;j<usuario.getRoles().size();j++)
			    	 if (listaParametro.get(i).getValor().equals(usuario.getRoles().get(j).getIdrol().toString()))
					   bandera = true;
			    }*/
			}

			if (!bandera){
			   sql =	"SELECT f FROM FilaSeguimientoDestinatario f WHERE f.fechaLimite IS NOT NULL AND f.fechaLimite >= :fechainicio AND f.fechaLimite <= :fechafin ";
			}else{
			   sql =	"SELECT f FROM FilaSeguimientoDestinatarioGG f WHERE f.fechaLimite IS NOT NULL AND f.fechaLimite >= :fechainicio AND f.fechaLimite <= :fechafin ";
			}
		}else{
			sql =	"SELECT f FROM FilaSeguimientoRemitente f WHERE f.fechaLimite IS NOT NULL AND f.fechaLimite >= :fechainicio AND f.fechaLimite <= :fechafin  ";
		}

		/*if(idProceso != null && idProceso != 0){
			sql += "AND f.documento.expediente.proceso.idproceso = :idProceso ";
		}*/


		if(propios){
			/**Son los documentos que tengo en la bandeja --------------------------------------------------------------------------------------*/
			sql += "AND f.destinatario = :idUsuario ";
		}else{
			/**Son los documentos que he enviado -----------------------------------------------------------------------------------------------*/
			sql += " AND (f.remitente = :idUsuario OR (f.iddocumento IN (SELECT sxu.idDocumento FROM SeguimientoXUsuario sxu WHERE sxu.idUsuario = :idUsuario))) ";
		}


		sql += " ORDER BY f.fechaLimite ASC";
		Query q = em.createQuery(sql)
		 .setParameter("fechainicio", fechaInicio)
		 .setParameter("fechafin", fechaFin)
		 .setParameter("idUsuario", idUsuario);

		log.debug("\n  verSeguimiento " + Constantes.VER_SEGUIMIENTO_SI);

		/*if(idProceso != null && idProceso != 0){
			q.setParameter("idProceso", idProceso);
		}*/

		List<FilaSeguimientoRemitente> lista_remitente = null;
		List<FilaSeguimientoDestinatario> lista_destinatario = null;
		List<FilaSeguimientoDestinatarioGG> lista_destinatario_gg = null;

		if (propios){
			if (!bandera)
			  lista_destinatario = q.getResultList();
			else
			  lista_destinatario_gg = q.getResultList();

			FilaSeguimiento filaSeguimiento = null;

			if (lista_destinatario != null){
				lista = new ArrayList<FilaSeguimiento>();
				for(int i=0;i<lista_destinatario.size();i++){
					filaSeguimiento = new FilaSeguimiento();
					filaSeguimiento.setNumerodocumento(lista_destinatario.get(i).getNumerodocumento());
					filaSeguimiento.setIddocumento(lista_destinatario.get(i).getIddocumento());
					filaSeguimiento.setFechaLimite(lista_destinatario.get(i).getFechaLimite());
					filaSeguimiento.setResponsable(lista_destinatario.get(i).getResponsable());
					filaSeguimiento.setFechaCreacion(lista_destinatario.get(i).getFechaCreacion());
					filaSeguimiento.setAsunto(lista_destinatario.get(i).getAsunto());
					lista.add(filaSeguimiento);
				}
			}

			if (lista_destinatario_gg != null){
				lista = new ArrayList<FilaSeguimiento>();
				for(int i=0;i<lista_destinatario_gg.size();i++){
					filaSeguimiento = new FilaSeguimiento();
					filaSeguimiento.setNumerodocumento(lista_destinatario_gg.get(i).getNumerodocumento());
					filaSeguimiento.setIddocumento(lista_destinatario_gg.get(i).getIddocumento());
					filaSeguimiento.setFechaLimite(lista_destinatario_gg.get(i).getFechaLimite());
					filaSeguimiento.setResponsable(lista_destinatario_gg.get(i).getResponsable());
					filaSeguimiento.setFechaCreacion(lista_destinatario_gg.get(i).getFechaCreacion());
					filaSeguimiento.setAsunto(lista_destinatario_gg.get(i).getAsunto());
					lista.add(filaSeguimiento);
				}
			}
		}else{
			lista_remitente = q.getResultList();
			FilaSeguimiento filaSeguimiento = null;
			if (lista_remitente != null){
				lista = new ArrayList<FilaSeguimiento>();
				for(int i=0;i<lista_remitente.size();i++){
					filaSeguimiento = new FilaSeguimiento();
					filaSeguimiento.setIddocumento(lista_remitente.get(i).getIddocumento());
					filaSeguimiento.setNumerodocumento(lista_remitente.get(i).getNumerodocumento());
					filaSeguimiento.setFechaLimite(lista_remitente.get(i).getFechaLimite());
					filaSeguimiento.setResponsable(lista_remitente.get(i).getResponsable());
					filaSeguimiento.setFechaCreacion(lista_remitente.get(i).getFechaCreacion());
					filaSeguimiento.setAsunto(lista_remitente.get(i).getAsunto());
					lista.add(filaSeguimiento);
				}
			}
		}

		log.debug(" tamagno ff de lista <Seguimiento> " + lista.size());
     }catch(Exception ex){
    	 ex.printStackTrace();
     }
		return lista;
	}

	/**
	 * INICIO: JOSE ZENTENO
	 *
	 */
	@SuppressWarnings("unchecked")
	public List<Trazabilidaddocumento> devuelveSeguimientoUsuario(Date fechainicio, Date fechafin, Integer idusuario, char cEstado, char cPrincipal) {
		log.debug("-> [DAO] DocumentoDAO - devuelveSeguimientoUsuario():List<Trazabilidaddocumento> ");

		String query = "SELECT distinct d FROM Documento d "
				+ "WHERE d.fechalimiteatencion > :fechainicio "
				+ "AND d.fechalimiteatencion < :fechafin "
				+ "AND d.estado = :estado "
				+ "AND d.principal = :principal "
				+ "AND ( d.propietario.idusuario = :idusuario "
				+ "      OR  d.expediente.proceso.responsable.idusuario = :idusuario "
				+ "	  OR d.expediente.proceso.idasistente.idusuario = :idusuario "
				+ "	  OR d.expediente.idpropietario.idusuario = :idusuario)";
		return em.createQuery(query).setParameter("fechainicio", fechainicio)
				.setParameter("fechafin", fechafin)
				.setParameter("idusuario", idusuario)
				.setParameter("estado", cEstado)
				.setParameter("principal", cPrincipal).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Documento> devuelveSeguimientoUsuarioFiltros(Date fechainicio,Date fechafin, Integer idusuario, char cEstado, char cPrincipal,
			Integer idProceso, boolean todosUsuarios) {

		log.debug("-> [DAO] DocumentoDAO - devuelveSeguimientoUsuarioFiltros():List<Documento> ");

		StringBuffer jql = new StringBuffer();
		jql.append("SELECT distinct d FROM Documento d where 1=1 \n");
		String query = "SELECT distinct d FROM Documento d "
				+ "WHERE d.fechalimiteatencion > :fechainicio "
				+ "AND d.fechalimiteatencion < :fechafin "
				+ "AND d.estado = :estado " + "AND d.principal = :principal ";
		if (idProceso != null) {
			query += "AND d.expediente.proceso.idproceso = :idProceso ";
		}
		query += "AND ( d.propietario.idusuario = :idusuario ";
		if (todosUsuarios) {
			query += "OR d.expediente.proceso.responsable.idusuario = :idusuario "
					+ "OR d.expediente.proceso.idasistente.idusuario = :idusuario "
					+ "OR d.expediente.idpropietario.idusuario = :idusuario";
		}
		query += ")";
		Query queryEM = this.em.createQuery(query)
				.setParameter("fechainicio", fechainicio)
				.setParameter("fechafin", fechafin)
				.setParameter("idusuario", idusuario)
				.setParameter("estado", cEstado)
				.setParameter("principal", cPrincipal)
				.setParameter("todosUsuarios", todosUsuarios);
		if (idProceso != null) {
			queryEM.setParameter("idProceso", idProceso);
		}
		return queryEM.getResultList();
	}

	/**
	 * FIN: JOSE ZENTENO
	 */
	@SuppressWarnings("unchecked")
	public String diasNoLaborables(String fechaInicio, String fechaFinal) {

		log.debug("-> [DAO] DocumentoDAO - diasNoLaborables():String ");

		Object diasLaborables = 0;
		try {
			log.debug("fechaInicio:" + fechaInicio);
			log.debug("fechaFinal:" + fechaFinal);
			// SimpleDateFormat formatoFecha = new
			// SimpleDateFormat("yyyy-MM-dd");
            Query query = em.createNativeQuery("{call simpleproc(?1,?2)}");
			query.setParameter(1, fechaInicio);
			query.setParameter(2, fechaFinal);
			query.executeUpdate();
			List<Object> list = query.getResultList();
			for (int i = 0; i < list.size(); i++) {
				diasLaborables = list.get(i);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);

		}
		log.debug("diasLaborables: " + diasLaborables);
		return diasLaborables.toString();
	}

	@SuppressWarnings("unchecked")
	public List<Documento> obtenciondefechaslimites(Date fechanolaborable) {
		log.debug("-> [DAO] DocumentoDAO - obtenciondefechaslimites():List<Documento> ");

		return em.createNamedQuery("Documento.consultafechafestiva")
				.setParameter("fechanolaborable", fechanolaborable)
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Documento> obtenciondefechasfijas(Date fechanolaborable) {
		log.debug("-> [DAO] DocumentoDAO - obtenciondefechasfijas():List<Documento> ");

		return em.createNamedQuery("Documento.consultafechafija")
				.setParameter("fechanolaborable", fechanolaborable)
				.getResultList();
	}

	@Override
	public String getSiguienteNumeroMesaPartes() {
		log.debug("-> [DAO] DocumentoDAO - getSiguienteNumeroMesaPartes():String ");

		Query q = em.createNativeQuery("select getnextmesapartes from dual");
		// q.setParameter(1,new Integer(3));
		// q.executeUpdate();
		String numero = ((BigDecimal) q.getResultList().get(0)).toString();
		return numero;
	}

	public Documento updateDocumento(Documento documento) {
		log.debug("-> [DAO] DocumentoDAO - updateDocumento():Documento ");

		em.merge(documento);
		em.flush();
		em.refresh(documento);
		return documento;
	}

	public Integer getDocumentosPrincipalesNoLeidos(Integer iIdUsuario) {
		log.debug("-> [DAO] DocumentoDAO - getDocumentosPrincipalesNoLeidos():Integer ");

		return em
				.createNamedQuery(
						"Documento.buscarDocumentosPrincipalesNoLeidos")
				.setParameter("idusuario", iIdUsuario).getResultList().size();
	}

	@PersistenceContext(unitName = "sigedPU")
	public void setEm(EntityManager em) {
		this.em = em;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Documento> getTodos() {
		log.debug("-> [DAO] DocumentoDAO - getTodos():List<Documento> ");

		return em.createNamedQuery("Documento.findAll").getResultList();
	}

	/**
	 * Busca documentos por todos los campos. Generaliza las busquedas simple y
	 * avanzadas. Deberia reemplazar a los demas metodos de busqueda.
	 *
	 * @author German Enriquez
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Documento> busquedaDocumento(List<Integer> idDocumentos,
			String numeroDocumento, String numeroMesaPartes,
			String asuntoDocumento, String numeroExpediente,
			String asuntoExpediente, String estadoExpediente,
			String concesionario, String cliente, String proceso,
			String propietario, String areaDestino, String tipoDocumento,
			Date documentoDesde, Date documentoHasta, Date expedienteDesde,
			Date expedienteHasta, String operador) {
		log.debug("-> [DAO] DocumentoDAO - busquedaDocumento():List<Documento> ");

		log.debug("los campos a buscar: [numeroDocumento:" + numeroDocumento
				+ ", numeroMesaPartes:" + numeroMesaPartes
				+ ", asuntoDocumento:" + asuntoDocumento
				+ ", numeroExpediente:" + numeroExpediente
				+ ", asuntoExpediente:" + asuntoExpediente
				+ ", estadoExpediente:" + estadoExpediente + ", concesionario:"
				+ concesionario + ", cliente:" + cliente + ", proceso:"
				+ proceso + ", propietario:" + propietario + ", areaDestino:"
				+ areaDestino + ", tipoDocumento:" + tipoDocumento
				+ ", documentoDesde:" + documentoDesde + ", documentoHasta:"
				+ documentoHasta + ", expedienteDesde:" + expedienteDesde
				+ ", expedienteHasta:" + expedienteHasta + "]");
		if (operador == null) {
			operador = "AND";
		} else if (!operador.equalsIgnoreCase("OR")) {
			operador = "AND";
		}
		operador = " " + operador + " ";
		boolean encontrado = false;
		StringBuilder sql = new StringBuilder("SELECT d FROM Documento d ");
		// aparentemente es necesario hacer un join para la busqueda del
		// concesionario
		if (concesionario != null) {
			sql.append("LEFT JOIN d.expediente.concesionario c ");
		}
		sql.append("WHERE d.documentoreferencia = NULL AND (");
		// si la busqueda ha sido por contenido, buscamos los id encontrados
		if (idDocumentos != null) {
			int docSize = idDocumentos.size();
			if (docSize > 0) {
				sql.append("d.idDocumento IN (");
				for (int i = 0; i < docSize; i++) {
					if (i > 0) {
						sql.append(",");
					}
					sql.append(idDocumentos.get(i));
				}
				sql.append(")");
				encontrado = true;
			}
		}
		if (numeroDocumento != null) {
			if (encontrado) {
				sql.append(operador);
			}
			sql.append("lower(d.numeroDocumento) LIKE :numeroDocumento");
			encontrado = true;
		}
		if (numeroMesaPartes != null) {
			if (encontrado) {
				sql.append(operador);
			}
			sql.append("lower(d.numeroMesaPartes) LIKE :numeroMesaPartes");
			encontrado = true;
		}
		if (asuntoDocumento != null) {
			if (encontrado) {
				sql.append(operador);
			}
			// verificamos que el asunto contenga espacios
			if (asuntoDocumento.contains(" ")) {
				boolean primero = true;
				// primero quitamos los %
				if (asuntoDocumento.startsWith("%")
						&& asuntoDocumento.endsWith("%")) {
					asuntoDocumento = asuntoDocumento.substring(1,
							asuntoDocumento.length() - 1);
				}
				// luego es necesario separar las palabras y hacer la busqueda
				// por cada palabra
				String[] palabras = asuntoDocumento.split(" ");
				sql.append("(");
				for (String palabra : palabras) {
					if (!primero) {
						sql.append(" AND ");
					}
					sql.append("instr(lower(d.asunto),'");
					sql.append(palabra);
					sql.append("')>0");
					primero = false;
				}
				sql.append(")");
				asuntoDocumento = null;
			} else {
				sql.append("lower(d.asunto) LIKE :asuntoDocumento");
			}
			encontrado = true;
		}
		if (numeroExpediente != null) {
			if (encontrado) {
				sql.append(operador);
			}
			sql.append("lower(d.expediente.nroexpediente) LIKE :numeroExpediente");
			encontrado = true;
		}
		if (asuntoExpediente != null) {
			if (encontrado) {
				sql.append(operador);
			}
			// verificamos que el asunto contenga espacios
			if (asuntoExpediente.contains(" ")) {
				boolean primero = true;
				// primero quitamos los %
				if (asuntoExpediente.startsWith("%")
						&& asuntoExpediente.endsWith("%")) {
					asuntoExpediente = asuntoExpediente.substring(1,
							asuntoExpediente.length() - 1);
				}
				// luego es necesario separar las palabras y hacer la busqueda
				// por cada palabra
				String[] palabras = asuntoExpediente.split(" ");
				sql.append("(");
				for (String palabra : palabras) {
					if (!primero) {
						sql.append(" AND ");
					}
					sql.append("instr(lower(d.expediente.asunto),'");
					sql.append(palabra);
					sql.append("')>0");
					primero = false;
				}
				sql.append(")");
				asuntoExpediente = null;
			} else {
				sql.append("lower(d.expediente.asunto) LIKE :asuntoExpediente");
			}
			encontrado = true;
		}
		if (estadoExpediente != null) {
			if (encontrado) {
				sql.append(operador);
			}
			sql.append("lower(d.expediente.estado) LIKE :estadoExpediente");
			encontrado = true;
		}
		if (concesionario != null) {
			if (encontrado) {
				sql.append(operador);
			}
			// verificamos que el concesionario contenga espacios
			if (concesionario.contains(" ")) {
				boolean primero = true;
				// primero quitamos los %
				if (concesionario.startsWith("%")
						&& concesionario.endsWith("%")) {
					concesionario = concesionario.substring(1,
							concesionario.length() - 1);
				}
				// luego es necesario separar las palabras y hacer la busqueda
				// por cada palabra
				String[] palabras = concesionario.split(" ");
				sql.append("(");
				for (String palabra : palabras) {
					if (!primero) {
						sql.append(" AND ");
					}
					sql.append("instr(lower(c.razonSocial),'");
					sql.append(palabra);
					sql.append("')>0");
					primero = false;
				}
				sql.append(")");
				concesionario = null;
			} else {
				sql.append("lower(c.razonSocial) LIKE :concesionario");
			}
			encontrado = true;
		}
		if (cliente != null) {
			if (encontrado) {
				sql.append(operador);
			}
			// verificamos que el cliente contenga espacios
			if (cliente.contains(" ")) {
				boolean primero = true;
				// primero quitamos los %
				if (cliente.startsWith("%") && cliente.endsWith("%")) {
					cliente = cliente.substring(1, cliente.length() - 1);
				}
				// luego es necesario separar las palabras y hacer la busqueda
				// por cada palabra
				String[] palabras = cliente.split(" ");
				sql.append("(");
				for (String palabra : palabras) {
					if (!primero) {
						sql.append(" AND ");
					}
					sql.append("(instr(lower(d.expediente.cliente.razonSocial),'");
					sql.append(palabra);
					sql.append("')>0 OR instr(lower(d.expediente.cliente.nombres||' '||d.expediente.cliente.apellidoPaterno||' '||d.expediente.cliente.apellidoMaterno),'");
					sql.append(palabra);
					sql.append("')>0)");
					primero = false;
				}
				sql.append(")");
				cliente = null;
			} else {
				sql.append("(lower(d.expediente.cliente.razonSocial) LIKE :cliente OR lower(d.expediente.cliente.nombres) LIKE :cliente OR lower(d.expediente.cliente.apellidoPaterno) LIKE :cliente OR lower(d.expediente.cliente.apellidoMaterno) LIKE :cliente OR lower(d.expediente.cliente.nombres||' '||d.expediente.cliente.apellidoPaterno||' '||d.expediente.cliente.apellidoMaterno) LIKE :cliente)");
			}
			encontrado = true;
		}
		if (proceso != null) {
			if (encontrado) {
				sql.append(operador);
			}
			// verificamos que el proceso contenga espacios
			if (proceso.contains(" ")) {
				boolean primero = true;
				// primero quitamos los %
				if (proceso.startsWith("%") && proceso.endsWith("%")) {
					proceso = proceso.substring(1, proceso.length() - 1);
				}
				// luego es necesario separar las palabras y hacer la busqueda
				// por cada palabra
				String[] palabras = proceso.split(" ");
				sql.append("(");
				for (String palabra : palabras) {
					if (!primero) {
						sql.append(" AND ");
					}
					sql.append("instr(lower(d.expediente.proceso.nombre),'");
					sql.append(palabra);
					sql.append("')>0");
					primero = false;
				}
				sql.append(")");
				proceso = null;
			} else {
				sql.append("lower(d.expediente.proceso.nombre) LIKE :proceso");
			}
			encontrado = true;
		}
		if (propietario != null) {
			if (encontrado) {
				sql.append(operador);
			}
			// verificamos que el cliente contenga espacios
			if (propietario.contains(" ")) {
				boolean primero = true;
				// primero quitamos los %
				if (propietario.startsWith("%") && propietario.endsWith("%")) {
					propietario = propietario.substring(1,
							propietario.length() - 1);
				}
				// luego es necesario separar las palabras y hacer la busqueda
				// por cada palabra
				String[] palabras = propietario.split(" ");
				sql.append("(");
				for (String palabra : palabras) {
					if (!primero) {
						sql.append(" AND ");
					}
					sql.append("instr(lower(d.propietario.nombres||' '||d.propietario.apellidos),'");
					sql.append(palabra);
					sql.append("')>0");
					primero = false;
				}
				sql.append(")");
				propietario = null;
			} else {
				sql.append("(lower(d.propietario.nombres) LIKE :propietario OR lower(d.propietario.apellidos) LIKE :propietario OR lower(d.propietario.nombres||' '||d.propietario.apellidos) LIKE :propietario)");
			}
			encontrado = true;
		}
		if (areaDestino != null) {
			if (encontrado) {
				sql.append(operador);
			}
			sql.append("lower(d.propietario.unidad.nombre) LIKE :unidad");
			encontrado = true;
		}
		if (tipoDocumento != null) {
			if (encontrado) {
				sql.append(operador);
			}
			// verificamos que el cliente contenga espacios
			if (tipoDocumento.contains(" ")) {
				boolean primero = true;
				// primero quitamos los %
				if (tipoDocumento.startsWith("%")
						&& tipoDocumento.endsWith("%")) {
					tipoDocumento = tipoDocumento.substring(1,
							tipoDocumento.length() - 1);
				}
				// luego es necesario separar las palabras y hacer la busqueda
				// por cada palabra
				String[] palabras = tipoDocumento.split(" ");
				sql.append("(");
				for (String palabra : palabras) {
					if (!primero) {
						sql.append(" AND ");
					}
					sql.append("instr(lower(d.tipoDocumento.nombre),'");
					sql.append(palabra);
					sql.append("')>0");
					primero = false;
				}
				sql.append(")");
				tipoDocumento = null;
			} else {
				sql.append("lower(d.tipoDocumento.nombre) LIKE :tipoDocumento");
			}
			encontrado = true;
		}
		if (documentoDesde != null && documentoHasta != null) {
			if (encontrado) {
				sql.append(operador);
			}
			sql.append("(year(d.fechaCreacion)>=year(:documentoDesde) AND month(d.fechaCreacion)>=month(:documentoDesde) AND day(d.fechaCreacion)>=day(:documentoDesde)) AND (year(d.fechaCreacion)<=year(:documentoHasta) AND month(d.fechaCreacion)<=month(:documentoHasta) AND day(d.fechaCreacion)<=day(:documentoHasta))");
			encontrado = true;
		}
		if (expedienteDesde != null && expedienteHasta != null) {
			if (encontrado) {
				sql.append(operador);
			}
			sql.append("(year(d.expediente.fechacreacion)>=year(:expedienteDesde) AND month(d.expediente.fechacreacion)>=month(:expedienteDesde) AND day(d.expediente.fechacreacion)>=day(:expedienteDesde)) AND (year(d.expediente.fechacreacion)<=year(:expedienteHasta) AND month(d.expediente.fechacreacion)<=month(:expedienteHasta) AND day(d.expediente.fechacreacion)<=day(:expedienteHasta))");
			encontrado = true;
		}

		sql.append(") ");

		if (encontrado) {
			// al menos se ha seleccionado un campo a buscar
			log.debug("La query a buscar: " + sql);
			Query q = em.createQuery(sql.toString());
			if (numeroDocumento != null) {
				q.setParameter("numeroDocumento", numeroDocumento);
			}
			if (numeroMesaPartes != null) {
				q.setParameter("numeroMesaPartes", numeroMesaPartes);
			}
			if (asuntoDocumento != null) {
				q.setParameter("asuntoDocumento", asuntoDocumento);
			}
			if (numeroExpediente != null) {
				q.setParameter("numeroExpediente", numeroExpediente);
			}
			if (asuntoExpediente != null) {
				q.setParameter("asuntoExpediente", asuntoExpediente);
			}
			if (estadoExpediente != null) {
				q.setParameter("estadoExpediente", estadoExpediente);
			}
			if (concesionario != null) {
				q.setParameter("concesionario", concesionario);
			}
			if (cliente != null) {
				q.setParameter("cliente", cliente);
			}
			if (proceso != null) {
				q.setParameter("proceso", proceso);
			}
			if (propietario != null) {
				q.setParameter("propietario", propietario);
			}
			if (areaDestino != null) {
				q.setParameter("unidad", areaDestino);
			}
			if (tipoDocumento != null) {
				q.setParameter("tipoDocumento", tipoDocumento);
			}
			if (documentoDesde != null && documentoHasta != null) {
				q.setParameter("documentoDesde", documentoDesde);
				q.setParameter("documentoHasta", documentoHasta);
			}
			if (expedienteDesde != null && expedienteHasta != null) {
				q.setParameter("expedienteDesde", expedienteDesde);
				q.setParameter("expedienteHasta", expedienteHasta);
			}

			return q.getResultList();
		}
		return null;
	}
	/**wcarrasco Usado en la busqueda documento Area Funcional------------------------------------------------------------------------------------*/
	/**
	 * Busca los documentos del area donde fueron creados y/o recepcionados asi tambien en su respectivo area Funcional
	 *
	 * @author Wilber Carrasco
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DocumentoAreaFuncional> busquedaDocumento(
			String asuntoDocumento, String asuntoExpediente,
			Date fechaDesde, Date fechaHasta, Integer idAreaUsuario, Integer idAreaFuncional, String operador) {
		log.debug("-> [DAO] DocumentoDAO - busquedaDocumento():List<DocumentoAreaFuncional> ");

		if (operador == null) {
			operador = "AND";
		}
			operador = " " + operador + " ";

		boolean encontrado = false;
		boolean parentises = true;
		StringBuilder sql = new StringBuilder("SELECT f FROM DocumentoAreaFuncional f WHERE ");

		if (asuntoDocumento != null) {
			if (encontrado) {
				sql.append(operador);
			}
			if(parentises){
				sql.append("((");
				parentises=false;
			}
			sql.append("lower(f.asuntoDocumento) LIKE :asuntoDocumento");
			encontrado = true;
		}
		if (asuntoExpediente != null) {
			if (encontrado) {
				sql.append(operador);
			}
			if(parentises){
				sql.append("((");
				parentises=false;
			}
			sql.append("lower(f.asuntoExpediente) LIKE :asuntoExpediente");
			encontrado = true;
		}

		if(!parentises){
			sql.append(")");
			parentises=false;
		}

        if(!((fechaDesde==null)) && !((fechaHasta==null))){
        	if (encontrado) {
				sql.append(" AND ");
			}
			sql.append("(f.fechaCreacion >= :fechaDesde AND f.fechaCreacion <= :fechaHasta)");
			encontrado = true;
		}

        if(!parentises){
			sql.append(") AND ( ");
			parentises=true;
			encontrado = false;
		}

		if (idAreaUsuario != null) {
			if (encontrado) {
				sql.append(" OR");
			}
			if(parentises){
				sql.append("((");
				parentises=false;
			}
			sql.append("f.idAreaCreacion LIKE :idAreaUsuario");
			encontrado = true;
		}

		if (idAreaFuncional != null) {
			if (encontrado) {
				sql.append(" OR ");
			}
			if(parentises){
				sql.append("((");
				parentises=false;
			}
			sql.append("f.idAreaCreacion LIKE :idAreaFuncional");
			encontrado = true;
		}
		if (idAreaFuncional == null && idAreaUsuario != null) {
			if (encontrado) {
				sql.append(" OR ");
			}
			if(parentises){
				sql.append("((");
				parentises=false;
			}
			sql.append("f.idAreaFuncionalCreacion LIKE :idAreaUsuario");
			encontrado = true;
		}
		if (idAreaFuncional != null) {
			if (encontrado) {
				sql.append(" OR ");
			}
			if(parentises){
				sql.append("((");
				parentises=false;
			}
			sql.append("f.idAreaFuncionalCreacion LIKE :idAreaFuncional");
			encontrado = true;
		}

		if(!parentises){
			sql.append(") AND idremitente = destinatario) OR ");
			parentises=true;
			encontrado = false;
		}

		if (idAreaUsuario != null) {
			if (encontrado) {
				sql.append(" OR ");
			}
			if(parentises){
				sql.append("(");
				parentises=false;
			}
			sql.append("f.idAreaRecepcion LIKE :idAreaUsuario");
			encontrado = true;
		}

		if (idAreaFuncional != null) {
			if (encontrado) {
				sql.append(" OR ");
			}
			if(parentises){
				sql.append("(");
				parentises=false;
			}
			sql.append("f.idAreaFuncionalRecepcion LIKE :idAreaFuncional");
			encontrado = true;
		}
		if (idAreaFuncional == null && idAreaUsuario != null) {
			if (encontrado) {
				sql.append(" OR ");
			}
			if(parentises){
				sql.append("(");
				parentises=false;
			}
			sql.append("f.idAreaFuncionalRecepcion LIKE :idAreaUsuario");
			encontrado = true;
		}
		if (idAreaUsuario != null && idAreaFuncional != null) {
			if (encontrado) {
				sql.append(" OR ");
			}
			if(parentises){
				sql.append("(");
				parentises=false;
			}
			sql.append("f.idAreaRecepcion LIKE :idAreaFuncional");
			encontrado = true;
		}
		if(!parentises){
			sql.append(") )");
		}

		if (encontrado) {

			log.debug("La query a buscar: " + sql);
			//-----------------------------------------------------
			Query q = em.createQuery(sql.toString());
			//-----------------------------------------------------
			if (asuntoDocumento != null) {
				q.setParameter("asuntoDocumento", asuntoDocumento);
			}
			if (asuntoExpediente != null) {
				q.setParameter("asuntoExpediente", asuntoExpediente);
			}
			if(!((fechaDesde==null)) && !((fechaHasta==null))){
				q.setParameter("fechaDesde", fechaDesde);
				q.setParameter("fechaHasta", fechaHasta);
			}
			if (idAreaUsuario != null) {
				q.setParameter("idAreaUsuario", idAreaUsuario);
			}
			if (idAreaFuncional != null) {
				q.setParameter("idAreaFuncional", idAreaFuncional);
			}

			return q.getResultList();
		}

		return null;

	}

	/**REN Usado en la busqueda avanzada ------------------------------------------------------------------------------------*/
	@SuppressWarnings("unchecked")
	@Override
	public List<Documento> busquedaDocumento(List<Integer> idDocumentos,
			String numeroDocumento, String numeroMesaPartes,
			String asuntoDocumento, String numeroExpediente,
			String asuntoExpediente, String estadoExpediente,
			String concesionario, String cliente, String proceso,
			String propietario, String areaDestino, String tipoDocumento,
			Date documentoDesde, Date documentoHasta, Date expedienteDesde,
			Date expedienteHasta, String numeroSuministro, String operador, String areaOrigen, String areaAutor, String sqlQueryDinamico[], String nroHT, String nroRS, String nroLegajo, String tipoLegajo, String tipoConsulta, String unidadUsuario, String cargoUsuario, String autor) {
		log.debug("-> [DAO] DocumentoDAO - busquedaDocumento():List<Documento> ");
 
		SimpleDateFormat fechita = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		log.debug("los campos a buscar: [numeroDocumento:" + numeroDocumento
				+ ", numeroMesaPartes:" + numeroMesaPartes
				+ ", asuntoDocumento:" + asuntoDocumento
				+ ", numeroExpediente:" + numeroExpediente
				+ ", asuntoExpediente:" + asuntoExpediente
				+ ", estadoExpediente:" + estadoExpediente + ", concesionario:"
				+ concesionario + ", cliente:" + cliente + ", proceso:"
				+ proceso + ", propietario:" + propietario + ", areaDestino:"
				+ areaDestino + ", tipoDocumento:" + tipoDocumento
				+ ", documentoDesde:" + documentoDesde + ", documentoHasta:"
				+ documentoHasta + ", expedienteDesde:" + expedienteDesde
				+ ", expedienteHasta:" + expedienteHasta + "]");

		if (operador == null) {
			operador = "AND";
		} else if (!operador.equalsIgnoreCase("OR")) {
			operador = "AND";
		}
                
                
                operador = " " + operador + " ";
		boolean encontrado = false;
		StringBuilder sql = new StringBuilder(
				"SELECT DISTINCT D.* FROM EXPEDIENTESTOR ES ");
		sql.append("RIGHT JOIN EXPEDIENTE E ON ES.IDEXPEDIENTE=E.IDEXPEDIENTE ");
		sql.append("RIGHT JOIN DOCUMENTO D ON E.IDEXPEDIENTE=D.EXPEDIENTE ");
		sql.append("LEFT JOIN CLIENTE CL ON D.ID_CLIENTE=CL.IDCLIENTE ");
                sql.append("LEFT JOIN LEGAJODOCUMENTO XX ON D.IDDOCUMENTO = XX.IDDOCUMENTO "); //JC
                sql.append("LEFT JOIN LEGAJO YY  ON YY.IDLEGAJO= XX.IDLEGAJO "); //JC
		sql.append("INNER JOIN USUARIO U ON D.PROPIETARIO=U.IDUSUARIO ");
		sql.append("INNER JOIN UNIDAD N ON U.IDUNIDAD=N.IDUNIDAD ");
		sql.append("INNER JOIN TIPODOCUMENTO TD ON TD.IDTIPODOCUMENTO=D.TIPODOCUMENTO "); // --
		/*sql.append("INNER JOIN Trazabilidaddocumento T ON D.IDDOCUMENTO=T.DOCUMENTO ");
		sql.append("WHERE d.estado not in ('I') and d.documentoreferencia IS NULL AND (");*/
                if(tipoConsulta.equals("busqueda"))
                {
                    sql.append("INNER JOIN Trazabilidaddocumento T ON D.IDDOCUMENTO=T.DOCUMENTO ");
                    sql.append("WHERE d.estado not in ('I') and d.documentoreferencia IS NULL AND (");
                }
                if(tipoConsulta.equals("reporte"))
                {
                    sql.append("LEFT JOIN Trazabilidaddocumento T ON D.IDDOCUMENTO=T.DOCUMENTO ");
                    sql.append("WHERE d.estado not in ('I') AND (");
                }
                
		// si la busqueda ha sido por contenido, buscamos los id encontrados
		if (idDocumentos != null) {
			int docSize = idDocumentos.size();
			if (docSize > 0) {
				sql.append("d.idDocumento IN (");
				for (int i = 0; i < docSize; i++) {
					if (i > 0) {
						sql.append(",");
					}
					sql.append(idDocumentos.get(i));
				}
				sql.append(")");
				encontrado = true;
			}
		}
		if (numeroDocumento != null) {
			if (encontrado) {
				sql.append(operador);
			}
			sql.append("UPPER(D.NRODOCUMENTO) LIKE :numeroDocumento");
			encontrado = true;
		}
                
                if (nroHT != null) {
			if (encontrado) {
				sql.append(operador);
			}
			sql.append("D.ID_CODIGO = :nroHT  ");
			encontrado = true;
		}
                
		if (numeroMesaPartes != null) {
			if (encontrado) {
				sql.append(operador);
			}
			sql.append("UPPER(D.NROMESAPARTES) LIKE :numeroMesaPartes");
			encontrado = true;
		}
		if (asuntoDocumento != null) {
			if (encontrado) {
				sql.append(operador);
			}
			sql.append("UPPER(D.ASUNTO) LIKE :asuntoDocumento");
			encontrado = true;
		}
		if (numeroExpediente != null) {
			if (encontrado) {
				sql.append(operador);
			}
			sql.append("UPPER(E.NROEXPEDIENTE) LIKE :numeroExpediente");
			encontrado = true;
		}
		if (asuntoExpediente != null) {
			if (encontrado) {
				sql.append(operador);
			}
			sql.append("UPPER(E.ASUNTO) LIKE :asuntoExpediente");
			encontrado = true;
		}
                
                /*if (estadoExpediente != null) {
                       if (encontrado) {
				sql.append(operador);
			}
                        sql.append("D.ESTADO=:estadoExpediente");
			encontrado = true;
		}*/
                if (estadoExpediente != null) {
                    if(tipoConsulta.equals("busqueda"))
                    {
                        if (encontrado) {
                            sql.append(operador);
                        }
                        sql.append("D.ESTADO=:estadoExpediente");
                        encontrado = true;
                    }
                    if(tipoConsulta.equals("reporte"))
                    {
                        if (encontrado) {
                            sql.append(operador);
                        }
                        if(estadoExpediente.equals("A"))
                        {
                            sql.append(" (EXISTS (SELECT dc.iddocumento from documento dc inner join usuario up on up.idusuario = dc.autor where dc.iddocumento = D.iddocumento and dc.unidadautor="+ unidadUsuario +"))");
                        }
                        if(estadoExpediente.equals("P"))
                        {
                            //sql.append(" (EXISTS (SELECT DP.iddocumento from documentopendiente DP inner join usuario up on up.idusuario = dp.idusuario where DP.estado = 'A' and DP.iddocumento = D.iddocumento and DP.unidadpropietario="+ unidadUsuario +"))");
                            sql.append(" (EXISTS (SELECT f.nroTramite,f.propietario FROM vistabandeja f WHERE f.nroTramite = D.ID_CODIGO AND f.propietario = D.propietario AND f.fechaaccion = D.fechaaccion AND f.unidadpropietario = "+unidadUsuario+" AND chrestado IN ('A','P') AND f.flagatendido is null AND f.flagmultiple is null and f.pendiente is not null and f.pendiente = '1' AND f.firmado = 'N' AND (f.idautor != f.propietario OR f.numeroTrazabilidad != 1 )))");
                        }
                        if(estadoExpediente.equals("T"))
                        {
                            //sql.append(" (EXISTS (SELECT DA.iddocumento from documentoatendido DA inner join usuario up on up.idusuario = da.idusuario where DA.estado = 'A' and DA.iddocumento = D.iddocumento and da.unidadpropietario="+ unidadUsuario +"))");
                            if(propietario != null)
                            {
                                sql.append(" (EXISTS (SELECT e.iddocumento FROM vistabandejaatendidos e WHERE e.iddocumento = D.iddocumento and e.unidadPropietario = "+ unidadUsuario +" and e.codEstado = 'A' and e.usuario = :propietario and e.cargoPropietario  ="+ cargoUsuario +"))");
                            }
                            else
                            {
                                sql.append(" (EXISTS (SELECT e.iddocumento FROM vistabandejaatendidos e WHERE e.iddocumento = D.iddocumento and e.unidadPropietario = "+ unidadUsuario +" and e.codEstado = 'A'))");
                            }
                        }
                        if(estadoExpediente.equals("N"))
                        {
                            //sql.append(" (EXISTS (SELECT DA.iddocumento from documentoanulado DA inner join usuario up on up.idusuario = da.idusuario where DA.estado = 'A' and DA.iddocumento = D.iddocumento and and da.unidadpropietario="+ unidadUsuario +"))");
                            sql.append(" (EXISTS (SELECT e.iddocumento FROM vistabandejaanulados e WHERE e.iddocumento = D.iddocumento and e.unidadPropietario = "+ unidadUsuario +" and e.codEstado = 'A'))");
                        }
                        if(estadoExpediente.equals("R"))
                        {
                            sql.append(" (EXISTS (SELECT f.nroTramite FROM vistabandeja f WHERE f.nroTramite = D.ID_CODIGO AND f.propietario = D.propietario AND f.fechaaccion = D.fechaaccion AND f.unidadpropietario = "+ unidadUsuario +" AND chrestado IN ('A','P') AND f.flagatendido is null AND f.flagmultiple is null AND f.firmado = 'N' AND f.numeroTrazabilidad != 1))"); 
                        }
                        encontrado = true;
                   }
                }

		if (concesionario != null) {
			if (encontrado) {
				sql.append(operador);
			}
			sql.append("UPPER(C.RAZONSOCIAL) LIKE :concesionario");
			encontrado = true;
		}
		if (cliente != null) {
			if (encontrado) {
				sql.append(operador);
			}
			sql.append("(UPPER(CL.RAZONSOCIAL) LIKE :cliente OR UPPER(CL.APELLIDOPATERNO || ' ' || CL.APELLIDOMATERNO || ' ' || CL.NOMBRES) LIKE :cliente)");
			encontrado = true;
		}
		if (proceso != null) {
			if (encontrado) {
				sql.append(operador);
			}
			sql.append("E.PROCESO=:proceso");
			encontrado = true;
		}
		/*if (propietario != null) {
			if (encontrado) {
				sql.append(operador);
			}
			sql.append("(UPPER(U.NOMBRES) || ' ' || UPPER(U.APELLIDOS) LIKE :propietario)");
			encontrado = true;
		}*/
                if (propietario != null) {
                    if(tipoConsulta.equals("reporte"))
                    {
                        if(!estadoExpediente.equals("T"))
                        {
                            if (encontrado) {
                                    sql.append(operador);
                            }
                            sql.append("U.IDUSUARIO = :propietario");
                            encontrado = true;
                        }
                    }
                }
                if(autor != null){
                    if (encontrado) {
                        sql.append(operador);
                    }
                    sql.append("D.AUTOR = :autor");
                    encontrado = true;
                }

                /*if (propietario != null) {
                    
                    if(tipoConsulta.equals("busqueda"))
                    {
                        if (encontrado) {
                            sql.append(operador);
                        }
                        sql.append("(UPPER(U.NOMBRES) || ' ' || UPPER(U.APELLIDOS) LIKE :propietario)");
                    }
                    if(tipoConsulta.equals("reporte"))
                    {
                        if(estadoExpediente != null)
                        {
                            if (encontrado) {
                                sql.append(operador);
                            }
                            if(estadoExpediente.equals("A"))
                            {
                                sql.append(" (EXISTS (SELECT dc.iddocumento from documento dc inner join usuario up on up.idusuario = dc.autor where dc.iddocumento = D.iddocumento and dc.unidadautor="+ unidadUsuario +" and (UPPER(UP.NOMBRES) || ' ' || UPPER(UP.APELLIDOS) LIKE :propietario)))");
                            }
                            if(estadoExpediente.equals("P"))
                            {
                                //sql.append(" (EXISTS (SELECT DP.iddocumento from documentopendiente DP inner join usuario up on up.idusuario = dp.idusuario where DP.estado = 'A' and DP.iddocumento = D.iddocumento and DP.unidadpropietario="+ unidadUsuario +" and (UPPER(UP.NOMBRES) || ' ' || UPPER(UP.APELLIDOS) LIKE :propietario)))");
                                sql.append(" (EXISTS (SELECT f.nroTramite FROM vistabandeja f WHERE f.nroTramite = D.ID_CODIGO AND f.unidadpropietario = "+unidadUsuario+" AND chrestado IN ('A','P') AND f.flagatendido is null AND f.flagmultiple is null and f.pendiente is not null and f.pendiente = '1' AND f.firmado = 'N' AND (f.idautor != f.propietario OR f.numeroTrazabilidad != 1 )))");
                            }
                            if(estadoExpediente.equals("T"))
                            {
                                sql.append(" (EXISTS (SELECT DA.iddocumento from documentoatendido DA inner join usuario up on up.idusuario = da.idusuario where DA.estado = 'A' and DA.iddocumento = D.iddocumento and da.unidadpropietario="+ unidadUsuario +" and (UPPER(UP.NOMBRES) || ' ' || UPPER(UP.APELLIDOS) LIKE :propietario)))");
                            }
                            if(estadoExpediente.equals("N"))
                            {
                                sql.append(" (EXISTS (SELECT DA.iddocumento from documentoanulado DA inner join usuario up on up.idusuario = da.idusuario where DA.estado = 'A' and DA.iddocumento = D.iddocumento and and da.unidadpropietario="+ unidadUsuario +" and (UPPER(UP.NOMBRES) || ' ' || UPPER(UP.APELLIDOS) LIKE :propietario)))");
                            }
                        }else{
                            if (encontrado) {
                                sql.append(operador);
                            }
                            sql.append("(UPPER(U.NOMBRES) || ' ' || UPPER(U.APELLIDOS) LIKE :propietario)"); 
                        }
                    }
                    encontrado = true;
                }*/


		
		if (areaDestino != null && areaOrigen != null){
			if (encontrado) {
				sql.append(operador);
			}
			//sql.append(" (T.fechacreacion = (SELECT MAX(TT.fechacreacion) FROM Trazabilidaddocumento TT WHERE TT.documento = D.IDDOCUMENTO AND (SELECT RE.IDUNIDAD FROM USUARIO RE WHERE RE.idusuario = TT.DESTINATARIO) = :unidad) AND (SELECT RE.IDUNIDAD FROM USUARIO RE WHERE RE.idusuario = TT.REMITENTE) = :areaOrigen)) ");
			 sql.append("  (EXISTS ((SELECT 1 FROM Trazabilidaddocumento TT WHERE TT.documento = D.IDDOCUMENTO AND TT.ACCION NOT IN (31,9) AND  TT.UNIDADDESTINATARIO = :unidad AND TT.UNIDADREMITENTE = :areaOrigen) ");
                         sql.append(" union  ");
                         sql.append("(SELECT 1 FROM Trazabilidadapoyo TT WHERE TT.DOCUMENTO IN (SELECT DD.IDDOCUMENTO FROM DOCUMENTO DD WHERE DD.DOCUMENTOREFERENCIA IS NOT NULL AND DD.DOCUMENTOREFERENCIA = D.IDDOCUMENTO) AND TT.ACCION NOT IN (31,9) AND TT.UNIDADDESTINATARIO= :unidad AND TT.UNIDADREMITENTE= :areaOrigen))) ");
                         encontrado = true;
		}else{
			  if (areaDestino != null) {
					if (encontrado) {
						sql.append(operador);
					}
					//sql.append(" (T.fechacreacion = (SELECT MAX(TT.fechacreacion) FROM Trazabilidaddocumento TT WHERE TT.documento = D.IDDOCUMENTO AND (SELECT RE.IDUNIDAD FROM USUARIO RE WHERE RE.idusuario = TT.DESTINATARIO) = :unidad)) ");
					//sql.append(" (T.fechacreacion = (SELECT MAX(TT.fechacreacion) FROM Trazabilidaddocumento TT WHERE TT.documento = D.IDDOCUMENTO AND TT.ACCION NOT IN (31,9) AND TT.UNIDADDESTINATARIO = :unidad)) ");
					sql.append(" (EXISTS ((SELECT 1 FROM Trazabilidaddocumento TT WHERE TT.documento = D.IDDOCUMENTO AND TT.ACCION NOT IN (31,9) AND TT.UNIDADDESTINATARIO = :unidad) ");
					sql.append(" union  ");
                                        sql.append("(SELECT 1 FROM Trazabilidadapoyo TT WHERE TT.DOCUMENTO IN (SELECT DD.IDDOCUMENTO FROM DOCUMENTO DD WHERE DD.DOCUMENTOREFERENCIA IS NOT NULL AND DD.DOCUMENTOREFERENCIA = D.IDDOCUMENTO) AND TT.ACCION NOT IN (31,9) AND TT.UNIDADDESTINATARIO= :unidad))) ");
                                        
                                        encontrado = true;
			  }
			  if(areaOrigen != null){
					if (encontrado) {
						sql.append(operador);
					}
					//sql.append(" (T.fechacreacion = (SELECT MAX(TT.fechacreacion) FROM Trazabilidaddocumento TT WHERE TT.documento = D.IDDOCUMENTO AND (SELECT RE.IDUNIDAD FROM USUARIO RE WHERE RE.idusuario = TT.REMITENTE) = :areaOrigen)) ");
					//sql.append(" (T.fechacreacion = (SELECT MAX(TT.fechacreacion) FROM Trazabilidaddocumento TT WHERE TT.documento = D.IDDOCUMENTO AND TT.ACCION NOT IN (31,9) AND TT.UNIDADREMITENTE = :areaOrigen)) ");
					sql.append(" (EXISTS ((SELECT 1 FROM Trazabilidaddocumento TT WHERE TT.documento = D.IDDOCUMENTO AND TT.ACCION NOT IN (31,9) AND TT.UNIDADREMITENTE = :areaOrigen) ");
					sql.append(" union  ");
                                        sql.append("(SELECT 1 FROM Trazabilidadapoyo TT WHERE TT.DOCUMENTO IN (SELECT DD.IDDOCUMENTO FROM DOCUMENTO DD WHERE DD.DOCUMENTOREFERENCIA IS NOT NULL AND DD.DOCUMENTOREFERENCIA = D.IDDOCUMENTO) AND TT.ACCION NOT IN (31,9) AND TT.UNIDADREMITENTE= :areaOrigen))) ");
                                        
                                        encontrado = true;
			 }
		}


		if(areaAutor != null){
			if (encontrado) {
				sql.append(operador);
			}
			//sql.append("(SELECT aut.idunidad FROM Usuario aut WHERE aut.idusuario = D.AUTOR ) = :areaAutor");
			sql.append("(D.UNIDADAUTOR ) = :areaAutor");
			
                        encontrado = true;
		}
		if (tipoDocumento != null) {
			if (encontrado) {
				sql.append(operador);
			}
			sql.append("D.TIPODOCUMENTO=:tipoDocumento");
			encontrado = true;
		}
		if (numeroSuministro != null) {
			if (encontrado) {
				sql.append(operador);
			}
			sql.append("S.NROSUMINISTRO LIKE :nroSuministro");
			encontrado = true;
		}
                
                if (nroLegajo != null) {
			if (encontrado) {
			    sql.append(operador);
			}
			sql.append(" (YY.NROLEGAJO =:nroLegajo AND XX.ESTADO NOT IN ('I'))");
			encontrado = true;
		}
                
                if (tipoLegajo != null) {
			if (encontrado) {
			    sql.append(operador);
			}
			sql.append("(YY.IDTIPO =:idTipoLegajo AND XX.ESTADO NOT IN ('I'))");
			encontrado = true;
		}
                
		if (documentoDesde != null && documentoHasta != null) {
			if (encontrado) {
				sql.append(operador);
			}
			// sql.append("(year(d.fechaCreacion)>=year(:documentoDesde) AND month(d.fechaCreacion)>=month(:documentoDesde) AND day(d.fechaCreacion)>=day(:documentoDesde)) AND (year(d.fechaCreacion)<=year(:documentoHasta) AND month(d.fechaCreacion)<=month(:documentoHasta) AND day(d.fechaCreacion)<=day(:documentoHasta))");
			sql.append("D.FECHACREACION BETWEEN TO_DATE(:documentoDesde, 'dd-mm-yyyy hh24:MI:ss') AND TO_DATE(:documentoHasta, 'dd-mm-yyyy hh24:MI:ss')");
			encontrado = true;
		}
		if (expedienteDesde != null && expedienteHasta != null) {
			if (encontrado) {
				sql.append(operador);
			}
			// sql.append("(year(d.expediente.fechacreacion)>=year(:expedienteDesde) AND month(d.expediente.fechacreacion)>=month(:expedienteDesde) AND day(d.expediente.fechacreacion)>=day(:expedienteDesde)) AND (year(d.expediente.fechacreacion)<=year(:expedienteHasta) AND month(d.expediente.fechacreacion)<=month(:expedienteHasta) AND day(d.expediente.fechacreacion)<=day(:expedienteHasta))");
			sql.append("E.FECHACREACION BETWEEN TO_DATE(:expedienteDesde, 'dd-mm-yyyy hh24:MI:ss') AND TO_DATE(:expedienteHasta, 'dd-mm-yyyy hh24:MI:ss')");
			encontrado = true;
		}


		//sql.append(") AND D.CONFIDENCIAL != 'S' ");
		sql.append(") order by D.fechaCreacion desc"); //x

		if (encontrado) {
			// al menos se ha seleccionado un campo a buscar
			log.debug("El query a buscar: " + sql);
			sqlQueryDinamico[0] = sql.toString();
			Query q = em.createNativeQuery(sql.toString(), Documento.class);
                        
                        if (nroLegajo != null){
                            q.setParameter("nroLegajo", nroLegajo);
                            sqlQueryDinamico[0] = sqlQueryDinamico[0].replace(":nroLegajo", nroLegajo);
                        }
                        
                         if (tipoLegajo != null){
                            q.setParameter("idTipoLegajo", new Integer(tipoLegajo));
                            sqlQueryDinamico[0] = sqlQueryDinamico[0].replace(":idTipoLegajo", tipoLegajo);
                        }
                        
                        if (nroHT != null){
                            q.setParameter("nroHT", new Integer(nroHT));
                            sqlQueryDinamico[0] = sqlQueryDinamico[0].replace(":nroHT", nroHT);
                        }
                        
                        if (nroRS != null){
                           q.setParameter("nroRS", new Integer(nroRS)); 
                           sqlQueryDinamico[0] = sqlQueryDinamico[0].replace(":nroRS", nroRS);
                        }

			if (numeroDocumento != null) {
				q.setParameter("numeroDocumento", numeroDocumento);
				sqlQueryDinamico[0] = sqlQueryDinamico[0].replace(":numeroDocumento", "'" +  numeroDocumento + "'");
			}

			if (numeroMesaPartes != null) {
				q.setParameter("numeroMesaPartes", numeroMesaPartes);
				sqlQueryDinamico[0] = sqlQueryDinamico[0].replace(":numeroMesaPartes", numeroMesaPartes);
			}
			if (asuntoDocumento != null) {
				q.setParameter("asuntoDocumento", asuntoDocumento);
				sqlQueryDinamico[0] = sqlQueryDinamico[0].replace(":asuntoDocumento", "'" + asuntoDocumento + "'");
			}
			if (numeroExpediente != null) {
				q.setParameter("numeroExpediente", numeroExpediente);
				sqlQueryDinamico[0] = sqlQueryDinamico[0].replace(":numeroExpediente", "'" + numeroExpediente + "'");
			}
			if (asuntoExpediente != null) {
				q.setParameter("asuntoExpediente", asuntoExpediente);
				sqlQueryDinamico[0] = sqlQueryDinamico[0].replace(":asuntoExpediente", "'" + asuntoExpediente + "'");
			}
			/*if (estadoExpediente != null) {
				q.setParameter("estadoExpediente", estadoExpediente);
				sqlQueryDinamico[0] = sqlQueryDinamico[0].replace(":estadoExpediente", "'" + estadoExpediente) + "'";
			}*/
                        if (estadoExpediente != null) {
                            if(tipoConsulta.equals("busqueda"))
                            {
                                q.setParameter("estadoExpediente", estadoExpediente);
                                sqlQueryDinamico[0] = sqlQueryDinamico[0].replace(":estadoExpediente", "'" + estadoExpediente) + "'";
                            }
                        }
			if (concesionario != null) {
				q.setParameter("concesionario", concesionario);
				sqlQueryDinamico[0] = sqlQueryDinamico[0].replace(":concesionario", concesionario);
			}
			if (cliente != null) {
				q.setParameter("cliente", cliente);
				sqlQueryDinamico[0] = sqlQueryDinamico[0].replace(":cliente", cliente);
			}
			if (proceso != null) {
				q.setParameter("proceso", new Integer(proceso));
				sqlQueryDinamico[0] = sqlQueryDinamico[0].replace(":proceso", proceso);
			}
			if (propietario != null) {
                            q.setParameter("propietario", new Integer(propietario));
                            sqlQueryDinamico[0] = sqlQueryDinamico[0].replace(":propietario", propietario);
                        }
                        if (autor != null) {
                            q.setParameter("autor", new Integer(autor));
                            sqlQueryDinamico[0] = sqlQueryDinamico[0].replace(":autor", autor);
                        }
			if (areaDestino != null) {
				q.setParameter("unidad", new Integer(areaDestino));
				sqlQueryDinamico[0] = sqlQueryDinamico[0].replace(":unidad", areaDestino);
			}
			if (areaOrigen != null) {
				q.setParameter("areaOrigen", new Integer(areaOrigen));
				sqlQueryDinamico[0] = sqlQueryDinamico[0].replace(":areaOrigen", areaOrigen);
			}
			if (areaAutor != null) {
				q.setParameter("areaAutor", new Integer(areaAutor));
				sqlQueryDinamico[0] = sqlQueryDinamico[0].replace(":areaAutor", areaAutor);
			}
			if (tipoDocumento != null) {
				q.setParameter("tipoDocumento", new Integer(tipoDocumento));
				sqlQueryDinamico[0] = sqlQueryDinamico[0].replace(":tipoDocumento", tipoDocumento);
			}
			if (numeroSuministro != null) {
				q.setParameter("nroSuministro", numeroSuministro);
				sqlQueryDinamico[0] = sqlQueryDinamico[0].replace(":nroSuministro", numeroSuministro);
			}
			if (documentoDesde != null && documentoHasta != null) {
				String fi = null, ff = null;
				fi = fechita.format(documentoDesde);
				ff = fechita.format(documentoHasta);
				q.setParameter("documentoDesde", fi);
				q.setParameter("documentoHasta", ff);
				sqlQueryDinamico[0] = sqlQueryDinamico[0].replace(":documentoDesde", "'" + fi + "'");
				sqlQueryDinamico[0] = sqlQueryDinamico[0].replace(":documentoHasta", "'" + ff + "'");
			}
			if (expedienteDesde != null && expedienteHasta != null) {
				String fi = null, ff = null;
				fi = fechita.format(expedienteDesde);
				ff = fechita.format(expedienteHasta);
				q.setParameter("expedienteDesde", fi);
				q.setParameter("expedienteHasta", ff);
				sqlQueryDinamico[0] = sqlQueryDinamico[0].replace(":expedienteDesde", "'" + fi + "'");
				sqlQueryDinamico[0] = sqlQueryDinamico[0].replace(":expedienteHasta", "'" + ff + "'");
			}

			return q.getResultList();
		}


		return null;
	}
        
       /* public List<String> buscarPendienteVirtual(String nroTramite){
            log.debug("-> [DAO] DocumentoDAO - buscarPendienteVirtual():List<String> ");
            
             String sql = " SELECT CAST(E.SIDDOCEXT AS VARCHAR2(16)), DP.IDDOCUMENTO FROM IOTDTM_DOC_EXTERNO E, IOTDTC_DESPACHO DP WHERE  ";
             sql = sql + " DP.CFLGEST NOT IN ('X') AND E.SIDEMIEXT= DP.SIDEMIEXT AND DP.VNUMREGSTD = :nroTramite order by DP.dfecreg desc " ;
            
             Query q = em.createNativeQuery(sql.toString());
             q.setParameter("nroTramite", nroTramite);
             List<Object> res = (List<Object>) q.getResultList();
             List<String> lp = new ArrayList<String>();
          
             for (Object obj : res) {     
                Object[] objectArray = (Object[]) obj;
                String valor = (objectArray[0]!=null?objectArray[0].toString():"0");
                lp.add(valor);
             }      
             
             return lp;
            
        }*/
        
        @SuppressWarnings("unchecked")
	@Override
	public List<DocumentoPublicar> getDocumentosPorPublicar(Integer idExpediente, Integer idDocumento) {
		log.debug("-> [DAO] DocumentoDAO - getDocumentosPorPublicar():List<Documento> ");

                String sql =   " SELECT IDREFARC,IDDOCUMENTO, IDDOCUMENTOREF, upper(SUBSTR(nombre, INSTR(nombre,']') + 1, LENGTH(nombre))) AS NOMBRE, IDARCHIVO, ESTADO, ORDEN FROM (" +
                               " SELECT R.IDREFARC,D.IDDOCUMENTO, D.IDDOCUMENTO IDDOCUMENTOREF,  A.NOMBRE, A.IDARCHIVO, DECODE(R.ESTADO, NULL,'I','I','I','A') as estado,1 as orden " +
                               "   FROM DOCUMENTO D, ARCHIVO A , REFERENCIAARCHIVO R  " +
                               "     WHERE " +
                               "       D.IDDOCUMENTO = " + idDocumento + " AND " +
                               "       D.ESTADO NOT IN ('I','N') AND   " +
                               "       D.IDDOCUMENTO = A.DOCUMENTO AND " +
                               "       A.ESTADO = 'A' AND " +
                               "       A.PRINCIPAL = 'S'   AND " +
                               "       A.DOCUMENTO = R.IDDOCUMENTO(+) AND " +
                               "       A.DOCUMENTO = R.IDDOCUMENTOREFERENCIA (+) AND " +
                               "       A.IDARCHIVO = R.IDARCHIVO (+) " + 
                               " UNION " +
                               " SELECT R.IDREFARC,D.IDDOCUMENTO, D.IDDOCUMENTO,  A.NOMBRE, A.IDARCHIVO, DECODE(R.ESTADO, NULL,'I','I','I','A') as estado,2 as orden " +
                               "   FROM DOCUMENTO D, ARCHIVO A, REFERENCIAARCHIVO R   " +
                               "     WHERE " +
                               "       D.IDDOCUMENTO = "  + idDocumento + " AND " +
                               "       D.ESTADO NOT IN ('I','N') AND   " +
                               "       D.IDDOCUMENTO = A.DOCUMENTO AND " + 
                               "       A.ESTADO = 'A' AND " +
                               "       A.PRINCIPAL = 'N' AND " +
                               "       LOWER(SUBSTR(A.nombre,LENGTH(A.nombre) - 2,3)) = 'pdf' AND " + 
                               "       A.DOCUMENTO = R.IDDOCUMENTO(+) AND " +
                               "       A.DOCUMENTO = R.IDDOCUMENTOREFERENCIA (+) AND " +
                               "       A.IDARCHIVO = R.IDARCHIVO (+) " + 
                               " UNION   " +
                               " SELECT F.IDREFARC,R.IDDOCUMENTO, R.IDDOCUMENTOREFERENCIA, A.NOMBRE, A.IDARCHIVO, DECODE(F.ESTADO, null, 'I','A','A','I') as estado,3 as orden " +
                               "   FROM DOCUMENTOREFERENCIA R, (SELECT * FROM REFERENCIAARCHIVO WHERE ESTADO='A') F, ARCHIVO A, DOCUMENTO D " +
                               "     WHERE " +
                               "       R.IDDOCUMENTO = " +  idDocumento + " AND " +
                               "       R.ESTADO = 'A' AND " +
                               "       R.IDDOCUMENTO = F.IDDOCUMENTO (+) AND " +
                               "       R.IDDOCUMENTOREFERENCIA = F.IDDOCUMENTOREFERENCIA(+)  AND " +
                               "       A.DOCUMENTO = (SELECT DECODE(C.DOCUMENTOREFERENCIA,NULL,C.IDDOCUMENTO, C.DOCUMENTOREFERENCIA) FROM DOCUMENTO C WHERE C.IDDOCUMENTO = R.IDDOCUMENTOREFERENCIA) AND " +
                               "       A.ESTADO = 'A' AND " +
                               "       A.DOCUMENTO = D.IDDOCUMENTO AND D.ESTADO NOT IN ('I','N') AND  " +
                               "       A.IDARCHIVO = F.IDARCHIVO(+) AND " +
                               "       LOWER(SUBSTR(A.nombre,LENGTH(A.nombre) - 2,3)) = 'pdf' )" +
                               " order by orden asc, IDDOCUMENTOREF asc";

                
                    Query q = em.createNativeQuery(sql.toString());
                    List<Object> res = (List<Object>) q.getResultList();
                    List<DocumentoPublicar> lp = new ArrayList<DocumentoPublicar>();
                    
                    for (Object obj : res) {     
                        DocumentoPublicar p = new DocumentoPublicar(); 
                         Object[] objectArray = (Object[]) obj;
                         p.setIdrefarc(new Integer(objectArray[0]!=null?objectArray[0].toString():"0"));
                         p.setIdDocumento(new Integer(objectArray[1].toString()));
                         p.setIdDocumentoRef(new Integer(objectArray[2].toString()));
                         p.setNombre(objectArray[3].toString());
                         p.setIdArchivo(new Integer(objectArray[4].toString()));
                         p.setEstado(objectArray[5].toString());
                         p.setOrden(objectArray[6].toString());
                         lp.add(p);
                    }
                    return lp;
	}
        
        public List<Documento> getDocumentosPorLegajo(int idLegajo) {
		log.debug("-> [DAO] DocumentoDAO - getDocumentosPorLegajo():List<Documento> ");

                Query q = em.createNamedQuery("Documento.getDocumentosPorLegajo");
		q.setParameter("idLegajo", idLegajo);
		return q.getResultList();
	}
       
	@SuppressWarnings("unchecked")
	@Override
	public List<Documento> getDocumentosPorExpediente(int idExpediente) {
		log.debug("-> [DAO] DocumentoDAO - getDocumentosPorExpediente():List<Documento> ");

                Query q = em.createNamedQuery("Documento.getDocumentosPorExpediente");
		q.setParameter("idExpediente", idExpediente);
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Documento> getDocumentosNoConfidencialesPorExpediente(int idExpediente){
		log.debug("-> [DAO] DocumentoDAO - getDocumentosNoConfidencialesPorExpediente():List<Documento> ");

		Query q = em.createNamedQuery("Documento.getDocumentosNoConfidencialesPorExpediente");
		q.setParameter("idExpediente", idExpediente);
		return q.getResultList();
	}

	@Override
	public Documento getDocumentoMasReciente(int idExpediente, int idDocumento) {
		log.debug("-> [DAO] DocumentoDAO - getDocumentoMasReciente():Documento ");

		Object obj = null;
		try {
			obj = em.createNamedQuery("Documento.getDocumentoMasReciente")
					.setParameter("idExpediente", idExpediente)
					.setParameter("idDocumento", idDocumento).getSingleResult();
		} catch (Exception e) {
			log.info("Carpturamos el error al obtener el documento reciente:\n"
					+ e.getMessage(), e.fillInStackTrace());
			log.info("Solo para obtener null del obj en caso de q no traiga nada, pero siempre es bueno revisarlo");
		}
		Documento d = null;
		if (obj != null) {
			d = (Documento) obj;
		}
		return d;
	}
        
        public DocumentoDetail findDocumentoDetailByAR(Integer iIdDocumento) {
		log.debug("-> [DAO] DocumentoDAO - findDocumentoDetailByAR():DocumentoDetail ");

               String sQuery = "SELECT NEW org.ositran.utils.DocumentoDetail("
				+ "d.idDocumento,"
				+ "d.fechaAccion,"
				+ "d.fechaCreacion,"
				+ "trz.fechalimiteatencion,"
                               // + "d.fechaLimiteAtencion,"
				+ "d.fechaDocumento,"
				+ "d.numeroMesaPartes,"
				+ "d.numeroFolios,"
				+ "d.numeroCaja,"
				+ "d.numeroDocumento,"
				+ "d.observacion,"
				+ "d.estado,"
				+ "d.principal,"
				+ "d.observacionRechazo,"
				+ "td.idtipodocumento,"
				+ "td.nombre,"
				+ "prod.nombres,"
				+ "prod.apellidos,"
				+ "e.id,"
				+ "e.nroexpediente,"
				+ "e.estaenflujo,"
				+ "e.historico,"
				+ "e.clienterazonsocial,"
				+ "e.clientenombres,"
				+ "e.clienteapellidopaterno,"
				+ "e.clienteapellidomaterno,"
				+ "e.clienterepresentantelegal,"
				+ "e.clientedireccionprincipal,"
				+ "e.clienteubigeoprincipal,"
				+ "e.clientedireccionalternativa,"
				+ "e.clienteubigeoalternativo,"
				+ "e.clientetelefono,"
				+ "e.clientecorreo,"
				+ "proe.nombres,"
				+ "proe.apellidos,"
				//+ "p.idproceso,"
				//+ "p.nombre,"
				//+ "tp.nombre,"
				//+ "resp.idusuario,"
				//+ "resp.nombres,"
				//+ "resp.apellidos,"
				//+ "respu.nombre,"
				+ "cl.idCliente,"
				+ "cl.numeroIdentificacion,"
				+ "cl.razonSocial,"
				+ "cl.nombres,"
				+ "cl.apellidoPaterno,"
				+ "cl.apellidoMaterno,"
				+ "cl.representanteLegal,"
				+ "cl.direccionPrincipal,"
				+ "cl.ubigeoPrincipal,"
				+ "cl.direccionAlternativa,"
				+ "cl.ubigeoAlternativo,"
				+ "cl.telefono,"
				+ "cl.correo,"
				+ "ti.idtipoidentificacion,"
				+ "ti.nombre,"
				//+ "co.idConcesionario,"
				//+ "co.razonSocial,"
				//+ "co.ruc,"
				//+ "co.direccion,"
				//+ "co.correo,"
				//+ "sa.idsala,"
				//+ "sa.nombre,"
				//+ "et.idetapa,"
				+ "d.asunto,"
				+ "trz.contenido,"
                                + " pro.nombre  ," //trz.proveido.nombre
				+ "rem.nombres,"
				+ "rem.apellidos,"
				+ "des.nombres,"
				+ "des.apellidos,"
				+ "rem.idusuario, "
				+ "d.observacionDigitalizador, " 
				//+ "p.codigo,"
				+ "trz.prioridad,"
                                //+ "d.prioridad,"
                                + " d.enumerado, trz.plazo) ";  
		sQuery += "FROM Documento d ";
		sQuery += "LEFT JOIN d.tipoDocumento td ";
		sQuery += "LEFT JOIN d.propietario prod ";
		sQuery += "LEFT JOIN d.expediente e ";
		sQuery += "LEFT JOIN e.idpropietario proe ";
		//sQuery += "LEFT JOIN e.proceso p ";
		//sQuery += "LEFT JOIN p.tipoproceso tp ";
		//sQuery += "LEFT JOIN p.responsable resp ";
		//sQuery += "LEFT JOIN resp.unidad respu ";
		sQuery += "LEFT JOIN e.cliente cl ";
		sQuery += "LEFT JOIN cl.ubigeoPrincipal clup ";
		sQuery += "LEFT JOIN cl.ubigeoAlternativo clua ";
		sQuery += "LEFT JOIN cl.tipoIdentificacion ti, ";
		//sQuery += "LEFT JOIN e.concesionario co ";
		//sQuery += "LEFT JOIN e.expedientestor es ";
		//sQuery += "LEFT JOIN es.sala sa ";
		//sQuery += "LEFT JOIN es.etapa et, ";
		sQuery += "Trazabilidaddocumento trz ";
		sQuery += "LEFT JOIN trz.documento trzd ";
		sQuery += "LEFT JOIN trz.remitente rem ";
		sQuery += "LEFT JOIN trz.destinatario des ";
                sQuery += "LEFT JOIN trz.proveido pro ";
		sQuery += "WHERE d.idDocumento = :iddocumento AND trzd.idDocumento = :iddocumento AND trz.idtrazabilidaddocumento = (SELECT MAX(tt.idtrazabilidaddocumento) FROM Trazabilidaddocumento tt LEFT JOIN tt.documento ttd WHERE ttd.idDocumento = :iddocumento and tt.accion.idAccion not in (25,31)) ";

                
		Query qQuery = em.createQuery(sQuery).setParameter("iddocumento",
				iIdDocumento);

                return (DocumentoDetail) qQuery.getSingleResult();
                 
	}

	public DocumentoDetail findDocumentoDetailBy(Integer iIdDocumento) {
		log.debug("-> [DAO] DocumentoDAO - findDocumentoDetailBy():DocumentoDetail ");

               String sQuery = "SELECT NEW org.ositran.utils.DocumentoDetail("
				+ "d.idDocumento,"
				+ "d.fechaAccion,"
				+ "d.fechaCreacion,"
				+ "trz.fechalimiteatencion,"
                               // + "d.fechaLimiteAtencion,"
				+ "d.fechaDocumento,"
				+ "d.numeroMesaPartes,"
				+ "d.numeroFolios,"
				+ "d.numeroCaja,"
				+ "d.numeroDocumento,"
				+ "d.observacion,"
				+ "d.estado,"
				+ "d.principal,"
				+ "d.observacionRechazo,"
				+ "td.idtipodocumento,"
				+ "td.nombre,"
				+ "prod.nombres,"
				+ "prod.apellidos,"
				+ "e.id,"
				+ "e.nroexpediente,"
				+ "e.estaenflujo,"
				+ "e.historico,"
				+ "e.clienterazonsocial,"
				+ "e.clientenombres,"
				+ "e.clienteapellidopaterno,"
				+ "e.clienteapellidomaterno,"
				+ "e.clienterepresentantelegal,"
				+ "e.clientedireccionprincipal,"
				+ "e.clienteubigeoprincipal,"
				+ "e.clientedireccionalternativa,"
				+ "e.clienteubigeoalternativo,"
				+ "e.clientetelefono,"
				+ "e.clientecorreo,"
				+ "proe.nombres,"
				+ "proe.apellidos,"
				//+ "p.idproceso,"
				//+ "p.nombre,"
				//+ "tp.nombre,"
				//+ "resp.idusuario,"
				//+ "resp.nombres,"
				//+ "resp.apellidos,"
				//+ "respu.nombre,"
				+ "cl.idCliente,"
				+ "cl.numeroIdentificacion,"
				+ "cl.razonSocial,"
				+ "cl.nombres,"
				+ "cl.apellidoPaterno,"
				+ "cl.apellidoMaterno,"
				+ "cl.representanteLegal,"
				+ "cl.direccionPrincipal,"
				+ "cl.ubigeoPrincipal,"
				+ "cl.direccionAlternativa,"
				+ "cl.ubigeoAlternativo,"
				+ "cl.telefono,"
				+ "cl.correo,"
				+ "ti.idtipoidentificacion,"
				+ "ti.nombre,"
				//+ "co.idConcesionario,"
				//+ "co.razonSocial,"
				//+ "co.ruc,"
				//+ "co.direccion,"
				//+ "co.correo,"
				//+ "sa.idsala,"
				//+ "sa.nombre,"
				//+ "et.idetapa,"
				+ "d.asunto,"
				+ "trz.contenido,"
                                + " pro.nombre  ," //trz.proveido.nombre
				+ "rem.nombres,"
				+ "rem.apellidos,"
				+ "des.nombres,"
				+ "des.apellidos,"
				+ "rem.idusuario, "
				+ "d.observacionDigitalizador, " 
				//+ "p.codigo,"
				+ "trz.prioridad,"
                                //+ "d.prioridad,"
                                + " d.enumerado, trz.plazo) ";  
		sQuery += "FROM Documento d ";
		sQuery += "LEFT JOIN d.tipoDocumento td ";
		sQuery += "LEFT JOIN d.propietario prod ";
		sQuery += "LEFT JOIN d.expediente e ";
		sQuery += "LEFT JOIN e.idpropietario proe ";
		//sQuery += "LEFT JOIN e.proceso p ";
		//sQuery += "LEFT JOIN p.tipoproceso tp ";
		//sQuery += "LEFT JOIN p.responsable resp ";
		//sQuery += "LEFT JOIN resp.unidad respu ";
		sQuery += "LEFT JOIN e.cliente cl ";
		sQuery += "LEFT JOIN cl.ubigeoPrincipal clup ";
		sQuery += "LEFT JOIN cl.ubigeoAlternativo clua ";
		sQuery += "LEFT JOIN cl.tipoIdentificacion ti, ";
		//sQuery += "LEFT JOIN e.concesionario co ";
		//sQuery += "LEFT JOIN e.expedientestor es ";
		//sQuery += "LEFT JOIN es.sala sa ";
		//sQuery += "LEFT JOIN es.etapa et, ";
		sQuery += "Trazabilidaddocumento trz ";
		sQuery += "LEFT JOIN trz.documento trzd ";
		sQuery += "LEFT JOIN trz.remitente rem ";
		sQuery += "LEFT JOIN trz.destinatario des ";
                sQuery += "LEFT JOIN trz.proveido pro ";
		sQuery += "WHERE d.idDocumento = :iddocumento AND trzd.idDocumento = :iddocumento AND trz.idtrazabilidaddocumento = (SELECT MAX(tt.idtrazabilidaddocumento) FROM Trazabilidaddocumento tt LEFT JOIN tt.documento ttd WHERE ttd.idDocumento = :iddocumento) ";

                
		Query qQuery = em.createQuery(sQuery).setParameter("iddocumento",
				iIdDocumento);

                return (DocumentoDetail) qQuery.getSingleResult();
                 
	}
        
         public DocumentoDetail findDocumentoDetailByPendiente(DocumentoPendiente documentoPendiente){
             log.debug("-> [DAO] DocumentoDAO - findDocumentoDetailBy():DocumentoDetail ");

                
              String sQuery = "SELECT NEW org.ositran.utils.DocumentoDetail("
				+ "d.idDocumento,"
				+ "d.fechaAccion,"
				+ "d.fechaCreacion,"
				+ "trz.fechalimiteatencion,"
                                //+ "d.fechaLimiteAtencion,"
				+ "d.fechaDocumento,"
				+ "d.numeroMesaPartes,"
				+ "d.numeroFolios,"
				+ "d.numeroCaja,"
				+ "d.numeroDocumento,"
				+ "d.observacion,"
				+ "d.estado,"
				+ "d.principal,"
				+ "d.observacionRechazo,"
				+ "td.idtipodocumento,"
				+ "td.nombre,"
				+ "prod.nombres,"
				+ "prod.apellidos,"
				+ "e.id,"
				+ "e.nroexpediente,"
				+ "e.estaenflujo,"
				+ "e.historico,"
				+ "e.clienterazonsocial,"
				+ "e.clientenombres,"
				+ "e.clienteapellidopaterno,"
				+ "e.clienteapellidomaterno,"
				+ "e.clienterepresentantelegal,"
				+ "e.clientedireccionprincipal,"
				+ "e.clienteubigeoprincipal,"
				+ "e.clientedireccionalternativa,"
				+ "e.clienteubigeoalternativo,"
				+ "e.clientetelefono,"
				+ "e.clientecorreo,"
				+ "proe.nombres,"
				+ "proe.apellidos,"
				//+ "p.idproceso,"
				//+ "p.nombre,"
				//+ "tp.nombre,"
				//+ "resp.idusuario,"
				//+ "resp.nombres,"
				//+ "resp.apellidos,"
				//+ "respu.nombre,"
				+ "cl.idCliente,"
				+ "cl.numeroIdentificacion,"
				+ "cl.razonSocial,"
				+ "cl.nombres,"
				+ "cl.apellidoPaterno,"
				+ "cl.apellidoMaterno,"
				+ "cl.representanteLegal,"
				+ "cl.direccionPrincipal,"
				+ "cl.ubigeoPrincipal,"
				+ "cl.direccionAlternativa,"
				+ "cl.ubigeoAlternativo,"
				+ "cl.telefono,"
				+ "cl.correo,"
				+ "ti.idtipoidentificacion,"
				+ "ti.nombre,"
				+ "d.asunto,"
				+ "trz.contenido,"
                                + " pro.nombre  ," //trz.proveido.nombre
				+ "rem.nombres,"
				+ "rem.apellidos,"
				+ "des.nombres,"
				+ "des.apellidos,"
				+ "rem.idusuario, "
				+ "d.observacionDigitalizador, " 
				//+ "p.codigo,"
				+ "trz.prioridad,"
                                //+ " d.prioridad,"
                                + " d.enumerado, trz.plazo) ";  
		sQuery += "FROM Documento d ";
		sQuery += "LEFT JOIN d.tipoDocumento td ";
		sQuery += "LEFT JOIN d.propietario prod ";
		sQuery += "LEFT JOIN d.expediente e ";
		sQuery += "LEFT JOIN e.idpropietario proe ";
		sQuery += "LEFT JOIN e.cliente cl ";
		sQuery += "LEFT JOIN cl.ubigeoPrincipal clup ";
		sQuery += "LEFT JOIN cl.ubigeoAlternativo clua ";
		sQuery += "LEFT JOIN cl.tipoIdentificacion ti, ";
		sQuery += "Trazabilidaddocumento trz ";
		sQuery += "LEFT JOIN trz.documento trzd ";
		sQuery += "LEFT JOIN trz.remitente rem ";
		sQuery += "LEFT JOIN trz.destinatario des ";
                sQuery += "LEFT JOIN trz.proveido pro ";
		sQuery += "WHERE d.idDocumento = :iddocumento and  trzd.idDocumento = :iddocumento AND trz.idtrazabilidaddocumento = :idTrazabilidad";

               Query qQuery = em.createQuery(sQuery).setParameter("iddocumento",
				documentoPendiente.getIddocumento()).setParameter("idTrazabilidad", documentoPendiente.getIdTrazabilidad());

                DocumentoDetail c = (DocumentoDetail) qQuery.getSingleResult();
                return c;
         }
        
        
        public DocumentoDetail findDocumentoDetailByUser(Integer iIdDocumento, Usuario usuario) {
		log.debug("-> [DAO] DocumentoDAO - findDocumentoDetailBy():DocumentoDetail ");
            
                String sQuery = "SELECT NEW org.ositran.utils.DocumentoDetail("
				+ "d.idDocumento,"
				+ "d.fechaAccion,"
				+ "d.fechaCreacion,"
				//+ "trz.fechalimiteatencion,"
                                + "d.fechaLimiteAtencion,"
				+ "d.fechaDocumento,"
				+ "d.numeroMesaPartes,"
				+ "d.numeroFolios,"
				+ "d.numeroCaja,"
				+ "d.numeroDocumento,"
				+ "d.observacion,"
				+ "d.estado,"
				+ "d.principal,"
				+ "d.observacionRechazo,"
				+ "td.idtipodocumento,"
				+ "td.nombre,"
				+ "prod.nombres,"
				+ "prod.apellidos,"
				+ "e.id,"
				+ "e.nroexpediente,"
				+ "e.estaenflujo,"
				+ "e.historico,"
				+ "e.clienterazonsocial,"
				+ "e.clientenombres,"
				+ "e.clienteapellidopaterno,"
				+ "e.clienteapellidomaterno,"
				+ "e.clienterepresentantelegal,"
				+ "e.clientedireccionprincipal,"
				+ "e.clienteubigeoprincipal,"
				+ "e.clientedireccionalternativa,"
				+ "e.clienteubigeoalternativo,"
				+ "e.clientetelefono,"
				+ "e.clientecorreo,"
				+ "proe.nombres,"
				+ "proe.apellidos,"
				//+ "p.idproceso,"
				//+ "p.nombre,"
				//+ "tp.nombre,"
				//+ "resp.idusuario,"
				//+ "resp.nombres,"
				//+ "resp.apellidos,"
				//+ "respu.nombre,"
				+ "cl.idCliente,"
				+ "cl.numeroIdentificacion,"
				+ "cl.razonSocial,"
				+ "cl.nombres,"
				+ "cl.apellidoPaterno,"
				+ "cl.apellidoMaterno,"
				+ "cl.representanteLegal,"
				+ "cl.direccionPrincipal,"
				+ "cl.ubigeoPrincipal,"
				+ "cl.direccionAlternativa,"
				+ "cl.ubigeoAlternativo,"
				+ "cl.telefono,"
				+ "cl.correo,"
				+ "ti.idtipoidentificacion,"
				+ "ti.nombre,"
				//+ "co.idConcesionario,"
				//+ "co.razonSocial,"
				//+ "co.ruc,"
				//+ "co.direccion,"
				//+ "co.correo,"
				//+ "sa.idsala,"
				//+ "sa.nombre,"
				//+ "et.idetapa,"
				+ "d.asunto,"
				+ "trz.contenido,"
                                + " pro.nombre  ," //trz.proveido.nombre
				+ "rem.nombres,"
				+ "rem.apellidos,"
				+ "des.nombres,"
				+ "des.apellidos,"
				+ "rem.idusuario, "
				+ "d.observacionDigitalizador, " 
				//+ "p.codigo,"
				//+ "trz.prioridad,"
                                + " d.prioridad, "
                                + " d.enumerado, trz.plazo) ";  
		sQuery += "FROM Documento d ";
		sQuery += "LEFT JOIN d.tipoDocumento td ";
		sQuery += "LEFT JOIN d.propietario prod ";
		sQuery += "LEFT JOIN d.expediente e ";
		sQuery += "LEFT JOIN e.idpropietario proe ";
		//sQuery += "LEFT JOIN e.proceso p ";
		//sQuery += "LEFT JOIN p.tipoproceso tp ";
		//sQuery += "LEFT JOIN p.responsable resp ";
		//sQuery += "LEFT JOIN resp.unidad respu ";
		sQuery += "LEFT JOIN e.cliente cl ";
		sQuery += "LEFT JOIN cl.ubigeoPrincipal clup ";
		sQuery += "LEFT JOIN cl.ubigeoAlternativo clua ";
		sQuery += "LEFT JOIN cl.tipoIdentificacion ti, ";
		//sQuery += "LEFT JOIN e.concesionario co ";
		//sQuery += "LEFT JOIN e.expedientestor es ";
		//sQuery += "LEFT JOIN es.sala sa ";
		//sQuery += "LEFT JOIN es.etapa et, ";
		sQuery += "Trazabilidaddocumento trz ";
		sQuery += "LEFT JOIN trz.documento trzd ";
		sQuery += "LEFT JOIN trz.remitente rem ";
		sQuery += "LEFT JOIN trz.destinatario des ";
                sQuery += "LEFT JOIN trz.proveido pro ";
		sQuery += "WHERE d.idDocumento = :iddocumento AND trzd.idDocumento = :iddocumento AND trz.idtrazabilidaddocumento = (SELECT MAX(tt.idtrazabilidaddocumento) FROM Trazabilidaddocumento tt LEFT JOIN tt.documento ttd WHERE ttd.idDocumento = :iddocumento and tt.destinatario.idusuario = :idUsuario and tt.unidaddestinatario = :idUnidad and tt.cargodestinatario = :idCargo) ";
 
                 
          	Query qQuery = em.createQuery(sQuery).setParameter("iddocumento",
				iIdDocumento).setParameter("idUsuario", usuario.getIdUsuarioPerfil()).setParameter("idUnidad", usuario.getIdUnidadPerfil()).setParameter("idCargo", usuario.getIdFuncionPerfil());
                 
                return (DocumentoDetail) qQuery.getSingleResult();
                 
	}

	@Override
	public byte[] getFirmaDigital(Usuario firmante) {
		log.debug("-> [DAO] DocumentoDAO - getFirmaDigital():byte[] ");

		String sQuery = "SELECT img.blob_content FROM usuarios u, firmas f, dcmntos_atchdos_frmas img WHERE u.idcorrentista = f.coroco_idcorrentista AND f.id_firma = img.firmas_id_firma AND img.blob_content IS NOT NULL AND LOWER(u.clave_name) = :usuario AND img.tipo = 'FI'";

		try {
			Blob firmaBlob = (Blob) em
					.createNativeQuery(sQuery)
					.setParameter("usuario",
							firmante.getUsuario().toLowerCase())
					.getSingleResult();

			byte buf[] = new byte[4000];
			int dataSize;
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			InputStream is = firmaBlob.getBinaryStream();

			try {
				while ((dataSize = is.read(buf)) != -1) {
					baos.write(buf, 0, dataSize);
				}
			} finally {
				if (is != null) {
					is.close();
				}
			}
			return baos.toByteArray();
		} catch (NoResultException nre) {
			log.warn("No se encontro firma digital para el usuario ["
					+ firmante.getUsuario() + "]");
		} catch (NonUniqueResultException nure) {
			log.error(nure.getMessage(), nure);
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
		}

		return null;
	}

	//FIXME no deberia estar aqui
	public Usuario findUsuarioById(Integer idUsuario) {
		log.debug("-> [DAO] DocumentoDAO - findUsuarioById():Usuario ");

		return (Usuario) em.createNamedQuery("Usuario.findByIdusuario")
				.setParameter("idusuario", idUsuario).getSingleResult();
	}

	@Override
	public Documento guardarDocumento(Documento documento) {
		log.debug("-> [DAO] DocumentoDAO - guardarDocumento():Documento ");

		if(documento.getAsunto()!=null){
			documento.setAsunto(documento.getAsunto().toUpperCase());
		}
		if (documento.getIdDocumento() == null) {
                        em.persist(documento);
			em.flush();
			em.refresh(documento);
		} else {
			em.merge(documento);
			em.flush();
			em.refresh(documento);
		}
		return documento;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Recurso> getCountDocuments(Usuario usuario) {
		log.debug("-> [DAO] DocumentoDAO - getCountDocuments():List<Recurso> ");

		String sql = "select d.tipodocumento, count(*) from notificacion "
				.concat("n left join documento d on n.iddocumento=d.iddocumento ")
				.concat("where n.idusuario=:propietario and n.estado='A' ")
				.concat("and d.tipodocumento in (2,3,86) AND n.tiponotificacion IN ")
				.concat("(:informativo1, :informativo2, :informativo3, :informativo4)")
				.concat(" group by d.tipodocumento");
		List<Recurso> lista = new ArrayList<Recurso>();
		Query q = em.createNativeQuery(sql);
		q.setParameter("propietario", usuario.getIdusuario());
		q.setParameter("informativo1", Constantes.TIPO_NOTIFICACION_DERIVACION);
		q.setParameter("informativo2",
				Constantes.TIPO_NOTIFICACION_NUMERACION_DESTINATARIO);
		q.setParameter("informativo3",
				Constantes.TIPO_NOTIFICACION_NUMERACION_DOCUMENTOCONCOPIA);
		q.setParameter("informativo4",
				Constantes.TIPO_NOTIFICACION_DERIVACIONCONCOPIA);
		List<Object> res = (List<Object>) q.getResultList();
		for (Object obj : res) {
			Object[] objectArray = (Object[]) obj;
			Object object1 = objectArray[0];
			Object object2 = objectArray[1];
			lista.add(new Recurso(object1.toString(), Integer.valueOf(object2
					.toString())));
		}
		return lista;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List unirData(List d1, List d2, List d3) {
		List data = new ArrayList();
		data.addAll(d1);
		data.addAll(d2);
		data.addAll(d3);
		return data;
	}

	/**
	 * @return the mailService
	 */
	public ManejoDeEmailService getMailService() {
		return mailService;
	}

	/**
	 * @param mailService
	 *            the mailService to set
	 */
	public void setMailService(ManejoDeEmailService mailService) {
		this.mailService = mailService;
	}
        
        
        public List findByPendientesDataUF(Usuario objUsuario, boolean items) {
                    log.debug("-> [DAO] DocumentoDAO - findByPendientesDataUF():List ");
                    List data = new ArrayList();
                    StringBuffer sqlMaster = new StringBuffer();
                    sqlMaster.append("SELECT f FROM FilaBandejaUF f ");

                    StringBuffer sqlCondicion1 = new StringBuffer();
                    sqlCondicion1.append(sqlMaster.toString());
                    StringBuffer sql = new StringBuffer();
                    // ******************************************************************************************
                    // BUSCANDO MIS DOCUMENTOS SIN REEMPLAZOS
                    // ******************************************************************************************
                    sql.append(sqlMaster.toString());
                    //JCCV
                    sql.append("WHERE  f.unidadpropietario = :idUnidadPropietario AND chrestado IN ('A','P') AND f.flagatendido is null AND f.flagmultiple is null and f.pendiente is not null and f.pendiente = '1'");
                    sql.append(" AND f.firmado = 'N' AND (f.autor.idusuario != f.propietario.idusuario OR f.numeroTrazabilidad != 1 )");
                    sql.append(" AND  :fechaDia >= f.fechaaccion  ");
                    sql.append("ORDER BY f.fechaaccion desc");
                    
                    Query q = em.createQuery(sql.toString());
                    q.setParameter("idUnidadPropietario", objUsuario.getIdUnidadPerfil());
                    Calendar fechaDia = Calendar.getInstance();
                    q.setParameter("fechaDia", fechaDia.getTime());

                    if(items == true){
                        try {
                                List<FilaBandejaUF> temp = q.getResultList();
                                if(temp != null && !temp.isEmpty()){
                                    if(true){
                                        for (int i = 0; i < temp.size(); i++) {
                                           FilaBandejaUF fila = temp.get(i);
                                           ItemUF item = llenarItemUF(fila);
                                           item.setExpBtn("-");
                                           data.add(item);
                                        }
                                    }
                                }
                        } catch (Exception e) {
                                e.printStackTrace();
                                log.error("ERROR: no tiene data", e.fillInStackTrace());
                        }

                        return data;

                }else{
                        return q.getResultList();
                }
        }

	/**Busca los expedientes para la grilla de Usuario Final. Se debe especificar el id del usuario y si se va a buscar la bandeja de documentos recibidos o propios
	 * @param iIdUsuario Id del usuario cuya bandeja se va a buscar
	 * @param recibidos Boolean que distingue si la bandeja es "Documentos recibidos" o "Mis Documentos"
	 * */
	public List findByDataUF(Usuario objUsuario, String bandeja, boolean items) {
            log.debug("-> [DAO] DocumentoDAO - findByDataUF():List ");
            
             boolean bandera = false;
             Usuario u = new Usuario();
             u.setIdUnidadPerfil(objUsuario.getIdUnidadPerfil());
             u.setIdFuncionPerfil(objUsuario.getIdFuncionPerfil());
             u.setIdusuario(objUsuario.getIdUsuarioPerfil());
             
             List<Usuarioxunidadxfuncion> lista = usuarioxunidadxfuncionDAO.getUsuarioByUnidadByFuncionListRol(u);
             if (lista!=null && lista.size()>0){
                for (int i=0;i<lista.size();i++){
                   if (lista.get(i).getIdrol().toString().equals(Constantes.COD_ROL_MENSAJERIA.toString())){
                       bandera = true;
                       break;
                   }
                }
             }    
            
            List data = new ArrayList();
            Usuario usuario = usuarioDAO.findByIdUsuario(objUsuario.getIdusuario());
            StringBuffer sqlMaster = new StringBuffer();
            sqlMaster.append("SELECT f FROM FilaBandejaUF f ");

            StringBuffer sqlCondicion1 = new StringBuffer();
            sqlCondicion1.append(sqlMaster.toString());
            StringBuffer sql = new StringBuffer();
            // ******************************************************************************************
            // BUSCANDO MIS DOCUMENTOS SIN REEMPLAZOS
            // ******************************************************************************************
            sql.append(sqlMaster.toString());
            sql.append("WHERE f.propietario.idusuario = :idUsuario AND f.unidadpropietario = :idUnidadPropietario AND f.cargopropietario = :idCargoPropietario  AND chrestado IN ('A','P') AND f.flagatendido is null AND f.flagmultiple is null ");
            
            if(bandeja.equals("R") || bandeja.equals("NT") || bandeja.equals("ND") || bandeja.equals("NU")){
                sql.append(" AND f.estadorecepcionvirtual ='0' and f.firmado = 'N' AND (f.autor.idusuario != :idUsuario OR f.numeroTrazabilidad != 1 )");
            }if (bandeja.equals("M")){
                sql.append(" AND f.estadorecepcionvirtual ='0' and f.autor.idusuario = :idUsuario AND f.numeroTrazabilidad = 1 ");
            }if (bandeja.equals("F")){
                sql.append(" AND f.estadorecepcionvirtual ='0' and f.firmado = 'S' ");
            }if (bandeja.equals("OBR")){
                sql.append(" AND f.estadorecepcionvirtual !='0' AND f.autor.idusuario = :idUsuario AND f.numeroTrazabilidad = 1 ");
            }
            if (bandera){
                sql.append(" AND f.despacho = '0'");
            }
            
            sql.append(" AND  :fechaDia >= f.fechaaccion  ");
            
            if (bandeja.equals("NU")){
              sql.append("ORDER BY f.unidadautor desc, f.fechaaccion desc");  
            }
            
            if (bandeja.equals("NT")){
                 sql.append("ORDER BY f.idtipodocumento desc, f.fechaaccion desc");
            }
            
            if (!bandeja.equals("NT") && !bandeja.equals("NU")){
                 sql.append("ORDER BY f.fechaaccion desc");        
            }
        
            Query q = em.createQuery(sql.toString());
            q.setParameter("idUsuario", objUsuario.getIdUsuarioPerfil());
            q.setParameter("idUnidadPropietario", objUsuario.getIdUnidadPerfil());
            q.setParameter("idCargoPropietario",  objUsuario.getIdFuncionPerfil());
            Calendar fechaDia = Calendar.getInstance();
            q.setParameter("fechaDia", fechaDia.getTime());

            if(items == true){
                try {
                        List<FilaBandejaUF> temp = q.getResultList();
                        if(temp != null && !temp.isEmpty()){
                                if(bandeja.equals("R") || bandeja.equals("F") || bandeja.equals("NT") || bandeja.equals("ND") || bandeja.equals("NU")){
                                        if(usuario.getBandejaAgrupada() != null && usuario.getBandejaAgrupada().equals(Constantes.Si)){
                                                Set<String> expedientesLista = new HashSet<String>();
                                                Map<String, Integer> mapCantidades = new HashMap<String, Integer>();
                                                Map<String, Boolean> mapLeidos = new HashMap<String, Boolean>();

                                                /**Rutina para el primer documento --------------------------------------------------------------------------------------*/
                                                FilaBandejaUF fila = temp.get(0);

                                                expedientesLista.add(fila.getExpediente());
                                                mapCantidades.put(fila.getExpediente(), 1);
                                                mapLeidos.put(fila.getExpediente(), fila.getLeido().equals(Constantes.No.toString()));

                                                data.add(llenarItemUF(fila));

                                                /**Rutina para el resto de documentos------------------------------------------------------------------------------------*/
                                                for (int i = 1; i < temp.size(); i++) {
                                                        fila = temp.get(i);

                                                        if(expedientesLista.contains(fila.getExpediente())){
                                                                mapCantidades.put(fila.getExpediente(), mapCantidades.get(fila.getExpediente())+1);
                                                                mapLeidos.put(fila.getExpediente(), mapLeidos.get(fila.getExpediente()) || fila.getLeido().equals(Constantes.No.toString()));
                                                        }else{
                                                                expedientesLista.add(fila.getExpediente());
                                                                mapCantidades.put(fila.getExpediente(), 1);
                                                                mapLeidos.put(fila.getExpediente(), fila.getLeido().equals(Constantes.No.toString()));

                                                                ItemUF uuf = llenarItemUF(fila);
                                                                data.add(uuf);
                                                        }
                                                }

                                                for(Object f : data){
                                                        ItemUF item = (ItemUF)f;
                                                        item.setExpBtn(item.getExpBtn() + "[" + mapCantidades.get(item.getExpediente()) + "]" + mapLeidos.get(item.getExpediente()));
                                                }
                                        }else{
                                                for (int i = 0; i < temp.size(); i++) {
                                                        FilaBandejaUF fila = temp.get(i);
                                                        ItemUF item = llenarItemUF(fila);
                                                        item.setExpBtn("-");
                                                        data.add(item);

                                                }
                                        }
                                }else{
                                        for (int i = 0; i < temp.size(); i++) {
                                                FilaBandejaUF fila = temp.get(i);
                                                data.add(llenarItemUF(fila));
                                        }
                                }
                        }
                } catch (Exception e) {
                        e.printStackTrace();
                        log.error("ERROR: no tiene data", e.fillInStackTrace());
                }

                return data;

        }else{
                return q.getResultList();
        }
      }


	private ItemUF llenarItemUF(FilaBandejaUF fila){
                ItemUF uuf = new ItemUF();
                Usuario objUsuario = new Usuario();
                
                objUsuario.setIdUsuarioPerfil(fila.getPropietario().getIdusuario());
                objUsuario.setIdUnidadPerfil(fila.getUnidadpropietario());
                objUsuario.setIdFuncionPerfil(fila.getCargopropietario());
                       
                uuf.setNroTramite(fila.getNroTramite());
		uuf.setId(fila.getId());
                uuf.setTipodocumento(fila.getIdtipodocumento().toString());
                uuf.setOrigen(fila.getOrigen());
                uuf.setTipoalerta(fila.getTipoalerta());
                uuf.setRemitente(fila.getRemitente().getNombreCompleto());
                uuf.setIdPropietario(fila.getPropietario().getIdusuario().toString());
                uuf.setPropietario(fila.getPropietario().getNombreCompleto());
                uuf.setArea(fila.getDesunidadremitente());
                uuf.setAreaAutor("");  
                uuf.setIdDestinatario("-1");
                uuf.setIdAreaAutor(fila.getUnidadautor().toString());
                uuf.setDocumentoreferencia(fila.getDocumentoreferencia());
                uuf.setIconoArea("images/ed_blank.gif");
                uuf.setVirtual("X");
                
                if (fila.getVirtual()!=null && !fila.getVirtual().equals("")){
                   if (fila.getVirtual().equals("XX")){
                     uuf.setVirtual("X");
                   }else{
                       if (fila.getVirtual().substring(0, 1).equals("D")){
                         uuf.setVirtual("D");
                       }else{
                          if (fila.getVirtual().substring(1, 2).equals("R")){
                            uuf.setVirtual("R");
                          }   
                       }   
                   }
                   
                }
                
                if (fila.getLlave()!=null && !fila.getLlave().trim().equals("")){
                   String valor = fila.getLlave().trim();
                   String valorAuxiliar = "";
                   try{
                       valor = valor.substring(0, valor.indexOf('|'));
                       valorAuxiliar = fila.getLlave().trim().substring(fila.getLlave().trim().indexOf('|') + 1, fila.getLlave().trim().length());
                       valor = valorAuxiliar.substring(0, valorAuxiliar.indexOf('|'));
                       valor = valorAuxiliar.substring(valorAuxiliar.indexOf('|') + 1, valorAuxiliar.length());
                       uuf.setIconoDocumento(fila.getLlave().trim());
                   }catch(Exception e){
                       uuf.setIconoDocumento("");  
                   }
                }else{
                   uuf.setIconoDocumento("");  
                }  
                
                if (fila.getUnidadautor()!=null){
                  uuf.setAreaAutor(fila.getDesunidadautor());
                  if (fila.getIniciales()!=null && !fila.getIniciales().trim().equals(""))
                     uuf.setIconoArea(fila.getId() + "|" +  fila.getIniciales());
                }
                
                uuf.setAsunto(fila.getAsunto() != null ? fila.getAsunto().replace("*", "") : "");
		uuf.setAsuntoExpediente(fila.getAsuntoExpediente() != null ? fila.getAsuntoExpediente().replace("*", "") : "");
		uuf.setDocumento(fila.getDocumento() != null ? fila.getDocumento().replace("*", "") : "");
		uuf.setConcesionario(fila.getConcesionario());
		uuf.setUrlarchivo("");
		uuf.setFechalimite(fila.getFechalimite());
		uuf.setEstado(fila.getEstado());
                uuf.setEtapa(fila.getEtapa());
		uuf.setExpediente(fila.getExpediente());
		uuf.setCliente(fila.getCliente()==null?"":fila.getCliente());
		uuf.setActividad(fila.getActividad());
		uuf.setNumeroMesaPartes(fila.getNumeromesapartes());
		uuf.setPorcentajealerta1(fila.getPorcentajeA() != null ? Double.parseDouble(fila.getPorcentajeA()) : 0);
		uuf.setPorcentajealerta2(fila.getPorcentajeR() != null ? Double.parseDouble(fila.getPorcentajeR()) : 0);
		uuf.setIdproceso(fila.getProceso() != null ? fila.getProceso().getIdproceso() : 0);
		uuf.setFecharecepcion(fila.getFechaaccion());
		uuf.setFechaAccion(fila.getFechaaccion());
                uuf.setFechacreacion(fila.getFechaaccion());
                uuf.setFirmado(fila.getFirmado());
                uuf.setFirma("images/vacio.png");
                uuf.setExterno(fila.getExterno());
                uuf.setPrioridad("images/ed_blank.gif");
                uuf.setNumeroTrazabilidad(fila.getNumeroTrazabilidad().intValue());
                
                if (uuf.getFirma()!=null && uuf.getFirmado().equals("S")){
                    List<String> lsContarFirmas = srvArchivo.contarArchivosxFirmar(fila.getDocumentoreferencia()==null?fila.getId(): new Integer(fila.getDocumentoreferencia()), objUsuario);

                    if (Integer.parseInt(lsContarFirmas.get(0))>0){
                       if (lsContarFirmas.get(0).equals(lsContarFirmas.get(1)))
                           uuf.setFirma("images/verde.png");
                       else{
                            if (lsContarFirmas.get(1).equals("0"))
                              uuf.setFirma("images/rojo.png");
                            else
                              uuf.setFirma("images/ambar.png");
                           }    
                    }
                }
                
                
		uuf.setFechalimite(fila.getFechalimitetraza());
              
                if (fila.getPrioridadtraza()!=null)
                   uuf.setPrioridad("images/Prioridad_"+fila.getPrioridadtraza()+".png"); 
                

		uuf.setPropietario(fila.getPropietario().getNombreCompleto());
		uuf.setLeido(fila.getLeido() != null ? fila.getLeido() : "N");
		uuf.setExpBtn(fila.getId().toString());
		Date d = uuf.getFechalimite();
		if (d != null) {
			Calendar fechaLimite = Calendar.getInstance();
			Calendar fechaAccion = Calendar.getInstance();
			fechaLimite.setTime(uuf.getFechalimite());
			fechaAccion.setTime(uuf.getFechaAccion());

			Calendar hoy = Calendar.getInstance();
			double index = fechaLimite.get(Calendar.DAY_OF_YEAR) - hoy.get(Calendar.DAY_OF_YEAR);//(double) atencionAccion / atencionHoy;

			if(index < 0){
				uuf.setTipoalerta("images/bolitaRoja.png");
			}else{
                              String valor  =  parametroService.findByTipo(Constantes.DIAS_LIMITE_AMBAR).get(0).getValor();
                              if (index<=Integer.parseInt(valor)){
                                 uuf.setTipoalerta("images/bolitaAmarilla.png"); 
                              }else{
                                 uuf.setTipoalerta("images/bolitaVerde.png");
                              }   
			}
		} else {
			uuf.setTipoalerta("images/bolita.png");
		}

		if(fila.getArchivos() > 0){
			uuf.setArchivos("images/sigedIconos/adjuntar.png");
		}else{
			uuf.setArchivos("images/ed_blank.gif");
		}

		if(String.valueOf(fila.getDespachado()).equals(String.valueOf(Constantes.ESTADO_DESPACHADO))){
			uuf.setDespachado("images/despachadoSI.PNG");
		}else{
			uuf.setDespachado("images/despachadoNO.PNG");
		}

		if(StringUtils.isEmpty(fila.getDocumentoreferencia())){
			uuf.setAccion(fila.getAccion() != null ? fila.getAccion().getDescripcion() : "-");

		}else{
			uuf.setAccion("Envio Multiple");
                        try{
                                            Documento doc = this.findByIdDocumento(Integer.parseInt(fila.getDocumentoreferencia()));
                                            if(doc.getArchivos() != null && !doc.getArchivos().isEmpty()){
                                               uuf.setArchivos("images/sigedIconos/adjuntar.png");
                                            }
                        }catch(Exception e){
                            e.printStackTrace();
                        }
		}

		return uuf;
	}

	/**REN Busca los documentos recibidos por un usuario por un solo Expediente----------------------------------------------*/
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List findFilasDXE(Integer idUsuario, Integer idDocumento, boolean enviados){
		log.debug("-> [DAO] DocumentoDAO - findFilasDXE():List ");

		List data = new ArrayList();

		StringBuffer sqlMaster = new StringBuffer();
		sqlMaster.append("SELECT f FROM FilaBandejaUF f ");

		StringBuffer sql = new StringBuffer();
		// ******************************************************************************************
		// BUSCANDO MIS DOCUMENTOS SIN REEMPLAZOS
		// ******************************************************************************************
		sql.append(sqlMaster.toString());

		sql.append("WHERE chrestado = :chrEstado AND f.propietario.idusuario = :idUsuario AND f.expediente = (SELECT doc.expediente.nroexpediente FROM Documento doc WHERE doc.idDocumento = :idDocumento) ");
		sql.append("AND (SELECT COUNT (r.idreemplazo) from Reemplazo r WHERE r.estado = 'A' AND r.idreemplazado = :idUsuario AND r.idproceso = f.proceso.idproceso AND CURRENT_DATE > r.fechainicialreemplazo and CURRENT_DATE <r.fechafinalreemplazo) = 0 ");
		if(enviados){
			sql.append("AND (f.autor.idusuario != :idUsuario OR f.numeroTrazabilidad != 1 )");
		}else{
			sql.append("AND f.autor.idusuario = :idUsuario AND f.numeroTrazabilidad = 1 ");
		}
		// ******************************************************************************************
		// BUSCANDO MIS DOCUMENTOS CON REEMPLAZOS
		// ******************************************************************************************
		sql.append("UNION ");
		sql.append(sqlMaster.toString());

		sql.append("WHERE chrestado = :chrEstado AND f.propietario.idusuario = :idUsuario AND f.expediente = (SELECT doc.expediente.nroexpediente FROM Documento doc WHERE doc.idDocumento = :idDocumento) ");
		sql.append("AND f.propietario.idusuario IN ELEMENTS(SELECT r.idreemplazado FROM Reemplazo r WHERE WHERE r.estado = 'A' AND r.idusuario = :idUsuario AND r.idproceso = f.proceso.idproceso AND CURRENT_DATE > r.fechainicialreemplazo and CURRENT_DATE <r.fechafinalreemplazo )");
		if(enviados){
			sql.append("AND (f.autor.idusuario != :idUsuario OR f.numeroTrazabilidad != 1) ");
		}else{
			sql.append("AND f.autor.idusuario = :idUsuario AND f.numeroTrazabilidad = 1 ");
		}

		Query q = em.createQuery(sql.toString());
		q.setParameter("chrEstado", Constantes.ESTADO_ACTIVO);
		q.setParameter("idUsuario", idUsuario);
		q.setParameter("idDocumento", idDocumento);

		try {
			List<FilaBandejaUF> temp = q.getResultList();

			for (int i = 0; i < temp.size(); i++) {
				data.add(llenarItemUF(temp.get(i)));
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error("ERROR: no tiene data", e.fillInStackTrace());
		}

		return data;
	}
        
        
        /*
        public List<Usuario> buscarUsuariosDocumentosPendientes(Integer idUnidad){
            List<Usuario> lstUsuario = new ArrayList<Usuario>();
            
            try{
                  //List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
                  StringBuffer sqlMaster = new StringBuffer();
                  sqlMaster.append("SELECT distinct f.propietario as idPropietario, (select nombres || ' ' || apellidos from usuario where idusuario = propietario) as nombres FROM vistabandeja f ");

                  StringBuffer sqlCondicion1 = new StringBuffer();
                  sqlCondicion1.append(sqlMaster.toString());
                  StringBuffer sql = new StringBuffer();
                  sql.append(sqlMaster.toString());
                   
                  sql.append("WHERE  f.unidadpropietario = :idUnidadPropietario AND f.chrestado IN ('A','P') AND f.flagatendido is null AND f.flagmultiple is null AND f.pendiente is not null and f.pendiente = '1' ");
                  sql.append(" AND f.firmado = 'N' AND (f.idautor != f.propietario OR f.numeroTrazabilidad != 1 )");
                  sql.append(" AND  :fechaDia >= f.fechaaccion  ");
                 // sql.append("ORDER BY f.fechaaccion desc");
                    
                  Query q = em.createNativeQuery(sql.toString());
                  q.setParameter("idUnidadPropietario", idUnidad);
                  Calendar fechaDia = Calendar.getInstance();
                  q.setParameter("fechaDia", fechaDia.getTime());
                  List<Object> res = (List<Object>) q.getResultList();
                  
                  for (Object obj : res) {    
                        Object[] objectArray = (Object[]) obj;
                        Usuario usuario = new Usuario();
                        usuario.setIdusuario(new Integer(objectArray[0].toString()));
                        usuario.setNombres(objectArray[1].toString());
                        lstUsuario.add(usuario);
                 }
            }catch(Exception e){
               e.printStackTrace();
             }

             return lstUsuario;
     
        }*/

	private String buscarAreaRemitente(String idRemitente){
		log.debug("-> [DAO] DocumentoDAO - buscarAreaRemitente():String ");

		if(idRemitente == null || idRemitente.equals("")){
			return "";
		}
		try{
			String sql = "SELECT u.nombre FROM Unidad u ";
			sql += "WHERE u.idunidad = (SELECT s.idunidad FROM Usuario s WHERE s.idUsuario = "+idRemitente+")";
			Query q = em.createNativeQuery(sql);
			return (String) q.getSingleResult();
		}catch(Exception e){
			e.printStackTrace();
			return "";
		}
	}

	/**REN Accede a la bd para cargar datos de la grilla Compartida -------------------------------------------------------------------------*/
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List findByDataUFWithoutSharedInbox(Integer iIdUsuario) {
		log.debug("-> [DAO] DocumentoDAO - findByDataUFWithoutSharedInbox():List ");
		List data = new ArrayList();
		Usuario usuario = usuarioDAO.findByIdUsuario(iIdUsuario);

		StringBuffer sqlMaster = new StringBuffer();
		sqlMaster.append("SELECT f FROM FilaBandejaUF f ");

		StringBuffer sqlCondicion1 = new StringBuffer();
		sqlCondicion1.append(sqlMaster.toString());
		StringBuffer sql = new StringBuffer();
		// ******************************************************************************************
		// BUSCANDO MIS DOCUMENTOS SIN REEMPLAZOS
		// ******************************************************************************************
		sql.append(sqlMaster.toString());
		/*
		sql.append("WHERE f.propietario.idusuario = :idUsuario ");
		sql.append("AND (SELECT COUNT (r.idreemplazo) from Reemplazo r WHERE r.estado = 'A' AND r.idreemplazado = :idUsuario AND r.idproceso = f.proceso.idproceso AND CURRENT_DATE > r.fechainicialreemplazo and CURRENT_DATE <r.fechafinalreemplazo) = 0 ");
		*/

		sql.append("WHERE f.propietario.idusuario = :idUsuario AND chrestado = :chrEstado ");
		sql.append("AND (SELECT COUNT (r.idreemplazo) from Reemplazo r WHERE r.estado = 'A' AND r.idreemplazado = :idUsuario AND r.idproceso = f.proceso.idproceso AND CURRENT_DATE > r.fechainicialreemplazo and CURRENT_DATE <r.fechafinalreemplazo) = 0 ");
		//if(recibidos){
			sql.append("AND (f.autor.idusuario != :idUsuario OR f.numeroTrazabilidad != 1 )");
		//}else{
		//	sql.append("AND f.autor.idusuario = :idUsuario AND f.numeroTrazabilidad = 1 ");
		//}

		sql.append("ORDER BY f.expediente, f.fechaaccion ");

		Query q = em.createQuery(sql.toString());
		q.setParameter("idUsuario", iIdUsuario);
		q.setParameter("chrEstado", Constantes.ESTADO_ACTIVO);
		try {
			List<FilaBandejaUF> temp = q.getResultList();
			if(temp != null && !temp.isEmpty()){
				//if(recibidos){
					if(usuario.getBandejaAgrupada() != null && usuario.getBandejaAgrupada().equals(Constantes.Si)){
						Set<String> expedientesLista = new HashSet<String>();
						Map<String, Integer> mapCantidades = new HashMap<String, Integer>();
						Map<String, Boolean> mapLeidos = new HashMap<String, Boolean>();

						/**Rutina para el primer documento --------------------------------------------------------------------------------------*/
						FilaBandejaUF fila = temp.get(0);

						expedientesLista.add(fila.getExpediente());
						mapCantidades.put(fila.getExpediente(), 1);
						mapLeidos.put(fila.getExpediente(), fila.getLeido().equals(Constantes.No.toString()));

						data.add(llenarItemUF(fila));

						/**Rutina para el resto de documentos------------------------------------------------------------------------------------*/
						for (int i = 1; i < temp.size(); i++) {
							fila = temp.get(i);

							if(expedientesLista.contains(fila.getExpediente())){
								mapCantidades.put(fila.getExpediente(), mapCantidades.get(fila.getExpediente())+1);
								mapLeidos.put(fila.getExpediente(), mapLeidos.get(fila.getExpediente()) || fila.getLeido().equals(Constantes.No.toString()));
							}else{
								expedientesLista.add(fila.getExpediente());
								mapCantidades.put(fila.getExpediente(), 1);
								mapLeidos.put(fila.getExpediente(), fila.getLeido().equals(Constantes.No.toString()));

								ItemUF uuf = llenarItemUF(fila);
								data.add(uuf);
							}
						}

						for(Object f : data){
							ItemUF item = (ItemUF)f;
							item.setExpBtn(item.getExpBtn() + "[" + mapCantidades.get(item.getExpediente()) + "]" + mapLeidos.get(item.getExpediente()));
						}
					}else{
						for (int i = 0; i < temp.size(); i++) {
							FilaBandejaUF fila = temp.get(i);
							ItemUF item = llenarItemUF(fila);
							item.setExpBtn("-");
							data.add(item);

						}
					}
				/*}else{
					for (int i = 0; i < temp.size(); i++) {
						FilaBandejaUF fila = temp.get(i);
						data.add(llenarItemUF(fila));
					}
				}*/
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("ERROR: no tiene data", e.fillInStackTrace());
		}

		return data;
	}

	private Date obtenerDate(String strfecha) {

		SimpleDateFormat formatoDelTexto = new SimpleDateFormat(
				"dd/MM/yyyy HH:mm");

		String strFecha = strfecha;
        log.trace("(obtenerDate) --strFecha->" + strFecha);
		Date fecha = null;
		try {
			fecha = formatoDelTexto.parse(strFecha);
            log.trace("(obtenerDate) fecha->" + fecha);
		} catch (ParseException ex) {
			ex.printStackTrace();
		}
		return fecha;
	}

	public CargoReporte obtenerCargo(Integer iddocumento) {
		log.debug("-> [DAO] DocumentoDAO - obtenerCargo():CargoReporte ");

                StringBuffer sql = new StringBuffer();
		sql.append("select "); 
                sql.append("  d.nromesapartes, ");
                sql.append("    td.nombre as tipodocumento, ");
                sql.append("    ex.nroexpediente, ");
                sql.append("     decode(d.id_cliente,null,'', decode(c.codtipoinstitucion,3,  c.nombres || ' ' || c.apellidopaterno|| ' ' || c.apellidomaterno,c.razonsocial)) remitente, ");
                sql.append("    d.asunto,d.observacion,d.nrodocumento, ");
                sql.append("    to_char(d.fechacreacion, 'dd/mm/yyyy HH24:MI'), "); 
                sql.append("    ex.clientedireccionprincipal as direccion,D.ID_CODIGO, D.nroFolios, u.nombres || ' ' || u.apellidos , (select decode(un.nombre,null, '', un.nombre) from documentoderivacion x, UNIDAD un where x.iddocumento = d.iddocumento and x.estado = 'A' AND x.tipo = 'P' and x.unidadpropietario = un.idunidad), (SELECT FNC_COPIA(d.iddocumento) FROM DUAL)  "); 
                sql.append("    from documento d, usuario u ");
                sql.append("     , tipodocumento td, expediente ex ,cliente c  where  td.idtipodocumento=d.tipodocumento "); 
                sql.append("      and ex.idexpediente = d.expediente  and d.propietario = u.idusuario");
               // sql.append("      and ex.idexpediente = d.expediente  and d.usuariocreacion = u.idusuario");
                sql.append("      and c.idcliente (+) =  d.id_cliente ");
                //sql.append("     and d.iddocumento = " + "(SELECT DOCUMENTOREFERENCIA FROM DOCUMENTO WHERE IDDOCUMENTO = " + iddocumento + ")");
                sql.append("     and d.iddocumento = "  + iddocumento + "");


		Query q = em.createNativeQuery(sql.toString());
		Object[] obj = (Object[]) q.getSingleResult();
		CargoReporte cr = null;
                
		if (obj != null) {
			cr = new CargoReporte();
			cr.setNromesapartes(obj[0] != null ? obj[0].toString() : "");
			cr.setTipodocumento(obj[1] != null ? obj[1].toString() : "");
			cr.setNroexpediente(obj[2] != null ? obj[2].toString() : "");
			cr.setRemitente(obj[3] != null ? obj[3].toString() : "");
			cr.setAsunto(obj[4] != null ? obj[4].toString() : "");
			cr.setObservacion(obj[5] != null ? obj[5].toString() : "");
			cr.setNrodocumento(obj[6] != null ? obj[6].toString() : "");
			cr.setFechacreacion(obj[7] != null ? obtenerDate(obj[7].toString()): null);
			cr.setDireccion(obj[8] != null ? obj[8].toString() : "");
                        cr.setNroTramite(obj[9] != null ? obj[9].toString() : "");
                        cr.setNroFolios(obj[10] != null ? obj[10].toString() : "");
                        cr.setUsuarioRegistro(obj[11] != null ? obj[11].toString() : "");
                        cr.setDesUnidad(obj[12] != null ? obj[12].toString() : "");
                        cr.setDesCopia(obj[13] != null ? obj[13].toString() : "");
		}
                
		return cr;
	}

	public AccionService getAccionService() {
		return accionService;
	}

	public void setAccionService(AccionService accionService) {
		this.accionService = accionService;
	}

	public TrazabilidadcopiaService getTrazabilidadcopiaService() {
		return trazabilidadcopiaService;
	}

	public void setTrazabilidadcopiaService(
			TrazabilidadcopiaService trazabilidadcopiaService) {
		this.trazabilidadcopiaService = trazabilidadcopiaService;
	}

	// Expedientes atendidos o archivados de un usuario final
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List findByDataAttendedUF(Usuario objUsuario) {
                log.debug("-> [DAO] DocumentoDAO - findByDataAttendedUF():List ");
                 List data = new ArrayList();

                String sql = "SELECT d FROM Documento d WHERE d.propietario.idusuario = :idUsuario AND d.unidadpropietario= :idUnidadPropietario and d.cargopropietario = :idCargoPropietario and d.estado IN (:estadoArchivado, :estadoAtendido) and d.flagatendido is not null ";
		sql += "AND d.fechaAccion >= :fechaDesde AND d.fechaAccion <= :fechaHasta ";
		sql += "ORDER BY d.fechaAccion DESC ";

		Query q = em.createQuery(sql);
		//q.setParameter("idUsuario", iIdUsuario).setParameter("estadoArchivado", Constantes.ESTADO_CERRADO)
		// .setParameter("estadoAtendido", Constantes.ESTADO_ATENDER);
                
                q.setParameter("idUsuario", objUsuario.getIdUsuarioPerfil()).setParameter("estadoArchivado", Constantes.ESTADO_CERRADO)
		.setParameter("estadoAtendido", Constantes.ESTADO_ATENDER).setParameter("idUnidadPropietario", objUsuario.getIdUnidadPerfil())
                .setParameter("idCargoPropietario", objUsuario.getIdFuncionPerfil());

		Calendar inicioDia = Calendar.getInstance();
		inicioDia.setTimeInMillis(inicioDia.getTimeInMillis() - 90*Constantes.MILISEGUNDOS_DIA);
		inicioDia.set(Calendar.HOUR_OF_DAY, 0);
		inicioDia.set(Calendar.MINUTE, 0);
		inicioDia.set(Calendar.SECOND, 0);

		Calendar finDia = Calendar.getInstance();
		finDia.set(Calendar.HOUR_OF_DAY, 23);
		finDia.set(Calendar.MINUTE, 59);
		finDia.set(Calendar.SECOND, 59);

		q.setParameter("fechaDesde", inicioDia.getTime());
		q.setParameter("fechaHasta", finDia.getTime());

                List<Documento> documentos = (List<Documento>)q.getResultList();
		if(documentos != null && !documentos.isEmpty()){
			for(Documento documento : documentos){
			      ItemUF uuf = new ItemUF();

			       uuf.setId(documento.getIdDocumento());
			       uuf.setAsunto(documento.getAsunto());
			       uuf.setFechadocumento(documento.getFechaDocumento());
			       uuf.setDocumento(documento.getTipoDocumento().getNombre() + " - " + documento.getNumeroDocumento());

			       if(documento.getEstado() == Constantes.ESTADO_CERRADO){
			        uuf.setEstado("Archivado");
			       }else{
			        uuf.setEstado("Atendido");
			       }

			       uuf.setExpediente(documento.getExpediente().getNroexpediente());
                               uuf.setAsuntoExpediente(documento.getExpediente().getAsunto());
                               uuf.setCliente("");
                               uuf.setNroTramite(documento.getID_CODIGO());
			      // uuf.setCliente(documento.getCliente()==null?"":documento.getCliente().getNombreRazon());
		               if (documento.getCliente()!=null){
                                    if (documento.getCliente().getTipoinstitucion().getCod_tipoinstitucion().toString().equals(Constantes.COD_PERSONA_NATURAL)){
                                      String nombres =  documento.getCliente().getNombres()==null?"":documento.getCliente().getNombres();
                                      String paterno =  documento.getCliente().getApellidoPaterno()==null?"":documento.getCliente().getApellidoPaterno();
                                      String materno =  documento.getCliente().getApellidoMaterno()==null?"":documento.getCliente().getApellidoMaterno();
                                      uuf.setCliente(nombres + " " + paterno + " " + materno);
                                    }else{
                                      uuf.setCliente(documento.getCliente().getRazonSocial());
                                    }
                                }
                               
                               uuf.setPropietario(documento.getPropietario().getNombreCompleto());

                               if(documento.getDocumentoreferencia() == null){

					try{
						String sql1 = "SELECT t FROM Trazabilidaddocumento t WHERE t.documento.idDocumento = :idDocumento AND t.idtrazabilidaddocumento = (SELECT MAX(tt.idtrazabilidaddocumento) FROM Trazabilidaddocumento tt WHERE tt.documento.idDocumento = :idDocumento) AND ROWNUM <=1";
						Trazabilidaddocumento traza = (Trazabilidaddocumento) em.createQuery(sql1).setParameter("idDocumento", documento.getIdDocumento()).getSingleResult();
						uuf.setRemitente(traza.getRemitente().getNombreCompleto());
						uuf.setFechaarchivar(traza.getFechacreacion());
						uuf.setObservacionArchivar(traza.getContenido());
					}catch(Exception e){
						e.printStackTrace();
						uuf.setRemitente("-");
						uuf.setFechaarchivar(null);
						uuf.setObservacionArchivar("-");
					}

				}else{

					try{
						String sql1 = "SELECT t FROM Trazabilidadapoyo t WHERE t.documento = :idDocumento AND t.fechacreacion = (SELECT MAX(tt.fechacreacion) FROM Trazabilidadapoyo tt WHERE tt.documento = t.documento) AND ROWNUM <=1";
						Trazabilidadapoyo traza = (Trazabilidadapoyo)em.createQuery(sql1).setParameter("idDocumento", documento.getIdDocumento()).getSingleResult();
						uuf.setRemitente(traza.getRemitente().getNombreCompleto());
						uuf.setFechaarchivar(traza.getFechacreacion());
						uuf.setObservacionArchivar(traza.getTexto());
					}catch(Exception e){
						e.printStackTrace();
						uuf.setRemitente("-");
						uuf.setFechaarchivar(null);
						uuf.setObservacionArchivar("-");
					}
				}

				data.add(uuf);
			}
		}


		return data;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List findByDataAttendedUF(Usuario objUsuario, BusquedaAvanzada objFiltro) {
		log.debug("-> [DAO] DocumentoDAO - findByDataAttendedUF():List ");
        List data = new ArrayList();

		String sql = "SELECT d FROM Documento d WHERE d.propietario.idusuario = :idUsuario AND d.unidadpropietario= :idUnidadPropietario and d.cargopropietario = :idCargoPropietario and d.estado IN (:estadoArchivado, :estadoAtendido) and d.flagatendido is not null ";

		//1
		if(!StringUtils.isEmpty(objFiltro.getNumeroExpediente())){
			sql += "AND LOWER(d.expediente.nroexpediente) LIKE :nroExpediente ";
		}
		//2
		if(!StringUtils.isEmpty(objFiltro.getNumeroDocumento())){
			sql += "AND LOWER(d.numeroDocumento) LIKE :nroDocumento ";
		}
		//3
		if(!StringUtils.isEmpty(objFiltro.getTipoDocumento())){
			sql += "AND d.tipoDocumento.idtipodocumento = :idTipoDocumento ";
		}
		//4
		if(!StringUtils.isEmpty(objFiltro.getCliente())){
			sql += "AND d.cliente.idCliente = :idCliente ";
		}
		//5
		if(!StringUtils.isEmpty(objFiltro.getAsuntodocumento())){
			sql += "AND LOWER(d.asunto) LIKE :asuntoDocumento ";
		}
		//6
		if(!StringUtils.isEmpty(objFiltro.getAsuntoexpediente())){
			sql += "AND LOWER(d.expediente.asunto) LIKE :asuntoExpediente ";
		}
		//7
		if(!StringUtils.isEmpty(objFiltro.getFechaDesde())){
			sql += "AND d.fechaAccion >= :fechaDesde ";
		}
		//8
		if(!StringUtils.isEmpty(objFiltro.getFechaHasta())){
			sql += "AND d.fechaAccion <= :fechaHasta ";
		}

		if(!StringUtils.isEmpty(objFiltro.getFechaDocumentoDesde())){
			sql += "AND d.fechaDocumento >= :fechaDocumentoDesde ";
		}
		//8
		if(!StringUtils.isEmpty(objFiltro.getFechaDocumentoHasta())){
			sql += "AND d.fechaDocumento <= :fechaDocumentoHasta ";
		}

		sql += "ORDER BY d.fechaAccion DESC ";
		Query q = em.createQuery(sql);
		q.setParameter("idUsuario", objUsuario.getIdUsuarioPerfil()).setParameter("estadoArchivado", Constantes.ESTADO_CERRADO).setParameter("idUnidadPropietario", objUsuario.getIdUnidadPerfil())
                .setParameter("idCargoPropietario", objUsuario.getIdFuncionPerfil())
		 .setParameter("estadoAtendido", Constantes.ESTADO_ATENDER);

		//1
		if(!StringUtils.isEmpty(objFiltro.getNumeroExpediente())){
			q.setParameter("nroExpediente", "%"+objFiltro.getNumeroExpediente().toLowerCase()+"%");
		}
		//2
		if(!StringUtils.isEmpty(objFiltro.getNumeroDocumento())){
			q.setParameter("nroDocumento", "%"+objFiltro.getNumeroDocumento().toLowerCase()+"%");
		}
		//3
		if(!StringUtils.isEmpty(objFiltro.getTipoDocumento())){
			q.setParameter("idTipoDocumento", Integer.parseInt(objFiltro.getTipoDocumento()));
		}
		//4
		if(!StringUtils.isEmpty(objFiltro.getCliente())){
			q.setParameter("idCliente", Integer.parseInt(objFiltro.getCliente()));
		}
		//5
		if(!StringUtils.isEmpty(objFiltro.getAsuntodocumento())){
			q.setParameter("asuntoDocumento", "%"+objFiltro.getAsuntodocumento().toLowerCase()+"%");
		}
		//6
		if(!StringUtils.isEmpty(objFiltro.getAsuntoexpediente())){
			q.setParameter("asuntoExpediente", "%"+objFiltro.getAsuntoexpediente().toLowerCase()+"%");
		}
		//7
		if(!StringUtils.isEmpty(objFiltro.getFechaDesde())){
			SimpleDateFormat fechita = new SimpleDateFormat("dd/MM/yyyy");
			Calendar fecha = Calendar.getInstance();
			try {
				fecha.setTime(fechita.parse(objFiltro.getFechaDesde()));
				fecha.set(Calendar.HOUR_OF_DAY, 0);
				fecha.set(Calendar.MINUTE, 0);
				fecha.set(Calendar.SECOND, 0);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			q.setParameter("fechaDesde", fecha.getTime());
		}
		//8
		if(!StringUtils.isEmpty(objFiltro.getFechaHasta())){
			SimpleDateFormat fechita = new SimpleDateFormat("dd/MM/yyyy");
			Calendar fecha = Calendar.getInstance();
			try {
				fecha.setTime(fechita.parse(objFiltro.getFechaHasta()));
				fecha.set(Calendar.HOUR_OF_DAY, 23);
				fecha.set(Calendar.MINUTE, 59);
				fecha.set(Calendar.SECOND, 59);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			q.setParameter("fechaHasta", fecha.getTime());
		}

		if(!StringUtils.isEmpty(objFiltro.getFechaDocumentoDesde())){
			SimpleDateFormat fechita = new SimpleDateFormat("dd/MM/yyyy");
			Calendar fecha = Calendar.getInstance();
			try {
				fecha.setTime(fechita.parse(objFiltro.getFechaDocumentoDesde()));
				fecha.set(Calendar.HOUR_OF_DAY, 0);
				fecha.set(Calendar.MINUTE, 0);
				fecha.set(Calendar.SECOND, 0);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			q.setParameter("fechaDocumentoDesde", fecha.getTime());
		}
		//8
		if(!StringUtils.isEmpty(objFiltro.getFechaDocumentoHasta())){
			SimpleDateFormat fechita = new SimpleDateFormat("dd/MM/yyyy");
			Calendar fecha = Calendar.getInstance();
			try {
				fecha.setTime(fechita.parse(objFiltro.getFechaDocumentoHasta()));
				fecha.set(Calendar.HOUR_OF_DAY, 23);
				fecha.set(Calendar.MINUTE, 59);
				fecha.set(Calendar.SECOND, 59);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			q.setParameter("fechaDocumentoHasta", fecha.getTime());
		}

		List<Documento> documentos = (List<Documento>)q.getResultList();
		if(documentos != null && !documentos.isEmpty()){
			for(Documento documento : documentos){
				ItemUF uuf = new ItemUF();

				uuf.setId(documento.getIdDocumento());
				uuf.setAsunto(documento.getAsunto());
				//uuf.setFecharecepcion(documento.getFechaAccion());
				uuf.setFechadocumento(documento.getFechaDocumento());
				uuf.setDocumento(documento.getTipoDocumento().getNombre() + " - " + documento.getNumeroDocumento());

				if(documento.getEstado() == Constantes.ESTADO_CERRADO){
					uuf.setEstado("Archivado");
				}else{
					uuf.setEstado("Atendido");
				}

				uuf.setExpediente(documento.getExpediente().getNroexpediente());
				//uuf.setProceso(documento.getExpediente().getProceso().getNombre());
				uuf.setCliente(documento.getCliente()==null?"": documento.getCliente().getNombreRazon());
				uuf.setPropietario(documento.getPropietario().getNombreCompleto());

				if(documento.getDocumentoreferencia() == null){
					try{
						String sql1 = "SELECT t FROM Trazabilidaddocumento t WHERE t.documento.idDocumento = :idDocumento AND t.idtrazabilidaddocumento = (SELECT MAX(tt.idtrazabilidaddocumento) FROM Trazabilidaddocumento tt WHERE tt.documento.idDocumento = :idDocumento) AND ROWNUM <=1";
						Trazabilidaddocumento traza = (Trazabilidaddocumento) em.createQuery(sql1).setParameter("idDocumento", documento.getIdDocumento()).getSingleResult();

						uuf.setRemitente(traza.getRemitente().getNombreCompleto());
						uuf.setFechaarchivar(traza.getFechacreacion());
						uuf.setObservacionArchivar(traza.getContenido());
					}catch(Exception e){
						e.printStackTrace();
						uuf.setRemitente("-");
						uuf.setFechaarchivar(null);
						uuf.setObservacionArchivar("-");
					}

				}else{
					try{
						String sql1 = "SELECT t FROM Trazabilidadapoyo t WHERE t.documento = :idDocumento AND t.fechacreacion = (SELECT MAX(tt.fechacreacion) FROM Trazabilidadapoyo tt WHERE tt.documento = t.documento) AND ROWNUM <=1";
						Trazabilidadapoyo traza = (Trazabilidadapoyo)em.createQuery(sql1).setParameter("idDocumento", documento.getIdDocumento()).getSingleResult();

						uuf.setRemitente(traza.getRemitente().getNombreCompleto());
						uuf.setFechaarchivar(traza.getFechacreacion());
						uuf.setObservacionArchivar(traza.getTexto());
					}catch(Exception e){
						e.printStackTrace();
						uuf.setRemitente("-");
						uuf.setFechaarchivar(null);
						uuf.setObservacionArchivar("-");
					}
				}

				data.add(uuf);
			}
		}

		return data;
	}

	@SuppressWarnings("unchecked")
	public List<Documento> getTodosDocumentosPorExpediente(int idExpediente) {
		log.debug("-> [DAO] DocumentoDAO - getTodosDocumentosPorExpediente():List<Documento> ");

		Query q = em
				.createNamedQuery("Documento.getTodosDocumentosPorExpediente");
		q.setParameter("idExpediente", idExpediente);
		return q.getResultList();
	}

	public Documento registrarDocumento(Documento objDoc){
		log.debug("-> [DAO] DocumentoDAO - registrarDocumento():Documento ");

		if(objDoc.getIdDocumento() == null){
			em.persist(objDoc); // Nuevo
			em.flush();
			em.refresh(objDoc);
		}
		else{
			objDoc.getAsunto();
			em.merge(objDoc); // Actualizacion
			em.flush();
		}
		return objDoc;
	}

	public void eliminarDocumento(Integer idDocumento){
		log.debug("-> [DAO] DocumentoDAO - eliminarDocumento():void ");

		em.remove(this.findByIdDocumento(idDocumento));
		em.flush();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Documento> busquedaCarpeta(String sNumeroDocumento,
			String sNumeroMesaPartes, String sAsuntoDocumento,
			String sNumeroExpediente, String sAsuntoExpediente,
			String sEstadoExpediente, String sConcesionario, String sCliente,
			Integer proceso, String sPropietario, Integer areaDestino,
			Integer tipoDocumento, Date fechaDocumentoInicio,
			Date fechaDocumentoFinal, Date fechaExpedienteInicio,
			Date fechaExpedienteFinal, Integer areaOrigen, String remitente, String pendiente) {
		log.debug("-> [DAO] DocumentoDAO - busquedaCarpeta():List<Documento> ");
		log.debug(
			"Campos a buscar \n " +
			"Numero Doc ["+sNumeroDocumento+"]\n"+
			"Numero MP ["+sNumeroMesaPartes+"]\n"+
			"Asunto Doc ["+sAsuntoDocumento+"]\n"+
			"Numero Exp ["+sNumeroExpediente+"]\n"+
			"Asunto Exp ["+sAsuntoExpediente+"]\n"+
			"Estado Exp ["+sEstadoExpediente+"]\n"+
			"Concesionario ["+sConcesionario+"]\n"+
			"Cliente ["+sCliente+"]\n"+
			"Propietario ["+sPropietario+"]\n"+
			"Remitente ["+remitente+"]\n"+
			"Fecha Inicio Doc ["+fechaDocumentoInicio+"]\n"+
			"Fecha Fin Doc ["+fechaDocumentoFinal+"]\n"+
			"Fecha Inicio Exp ["+fechaExpedienteInicio+"]\n"+
			"Fecha Fin Exp ["+fechaExpedienteFinal+"]\n"+
			"Id Proceso ["+proceso+"]\n"+
			"Id Area Destino ["+areaDestino+"]\n"+
			"Id Tipo Doc ["+tipoDocumento+"]\n"+
			"Id Area Origen ["+areaOrigen+"]\n"
		);

		String sql = null;
		if (pendiente == null || pendiente.equals("N"))
		   sql = "SELECT d FROM Documento d WHERE d.estado not in ('I') and d.documentoreferencia IS NULL AND d.enumerado = :enumerado ";
		else
		   if (pendiente.equals("S"))
		      sql =  "SELECT d FROM Documento d, NodoDocReporteAPN4 r WHERE d.estado not in ('I') and d.documentoreferencia IS NULL AND d.enumerado = :enumerado  AND 	d.idDocumento = r.idDocumento and d.tipoDocumento.idtipodocumento = r.idTipoDocumento  ";
		   else
			   sql = "SELECT d FROM Documento d WHERE d.estado not in ('I') and d.documentoreferencia IS NULL AND d.enumerado = :enumerado ";

		if(sNumeroDocumento != null){
			sql += "AND LOWER(d.numeroDocumento) LIKE :numeroDoc ";
		}
		if(sNumeroMesaPartes != null){
			sql += "AND LOWER(d.numeroMesaPartes) LIKE :numeroMP ";
		}
		if(sAsuntoDocumento != null){
			sql += "AND LOWER(d.asunto) LIKE :asuntoDoc ";
		}
		if(sNumeroExpediente != null){
			sql += "AND LOWER(d.expediente.nroexpediente) LIKE :numeroExp ";
		}
		if(sAsuntoExpediente != null){
			sql += "AND LOWER(d.expediente.asunto) LIKE :asuntoExp ";
		}
		if(sEstadoExpediente != null){
			sql += "AND LOWER(d.expediente.estado) LIKE :estadoExp ";
		}
		if(sConcesionario != null){
			sql += "AND LOWER(d.expediente.concesionario.razonSocial) LIKE :concesionario ";
		}
		if(sCliente != null){
			sql += "AND (LOWER(d.expediente.cliente.razonSocial) LIKE :cliente OR LOWER(d.expediente.cliente.apellidoPaterno || ' ' || d.expediente.cliente.apellidoMaterno || ' ' || d.expediente.cliente.nombres) LIKE :cliente) ";
		}
		if(sPropietario != null){
			sql += "AND (LOWER(d.expediente.idpropietario.apellidos || ' ' || d.expediente.idpropietario.nombres) LIKE :propietario OR LOWER(d.expediente.idpropietario.usuario) LIKE :propietario) ";
		}
		if(remitente != null){
			sql += "AND (SELECT LOWER(t1.remitente.usuario) FROM Trazabilidaddocumento t1 WHERE t1.documento.idDocumento = d.idDocumento AND t1.idtrazabilidaddocumento = (SELECT MAX (t2.idtrazabilidaddocumento) FROM Trazabilidaddocumento t2 WHERE t2.documento.idDocumento = d.idDocumento)) LIKE :remitente ";
		}
		if(fechaDocumentoInicio != null){
			sql += "AND d.fechaCreacion >= :fechaDocInicio ";
		}
		if(fechaDocumentoFinal != null){
			sql += "AND d.fechaCreacion <= :fechaDocFin ";
		}
		if(fechaExpedienteInicio != null){
			sql += "AND d.expediente.fechacreacion >= :fechaExpInicio ";
		}
		if(fechaExpedienteFinal != null){
			sql += "AND d.expediente.fechacreacion <= :fechaExpFin ";
		}
		if(proceso != null){
			sql += "AND d.expediente.proceso.idproceso = :idProceso ";
		}
		if(areaDestino != null){
			//sql += "AND (SELECT t1.destinatario.unidad.idunidad FROM Trazabilidaddocumento t1 WHERE t1.documento.idDocumento = d.idDocumento AND t1.remitente = d.autor AND t1.fechacreacion = (SELECT MAX (t2.fechacreacion) FROM Trazabilidaddocumento t2 WHERE t2.documento.idDocumento = d.idDocumento AND t2.remitente = d.autor))= :idAreaDestino ";
                        sql += "AND ((SELECT max(t1.fechacreacion) FROM Trazabilidaddocumento t1 WHERE t1.documento.idDocumento = d.idDocumento) = (SELECT MAX (t2.fechacreacion) FROM Trazabilidaddocumento t2 WHERE t2.documento.idDocumento = d.idDocumento AND t2.unidaddestinatario = :idAreaDestino)) ";
		}
		if(tipoDocumento != null){
			sql += "AND LOWER(d.tipoDocumento.idtipodocumento) = :idTipoDoc ";
		}
		if(areaOrigen != null){
			//sql += "AND d.autor.unidad.idunidad = :idAreaOrigen ";
                         sql += "AND ((SELECT max(t1.fechacreacion) FROM Trazabilidaddocumento t1 WHERE t1.documento.idDocumento = d.idDocumento) = (SELECT MAX (t2.fechacreacion) FROM Trazabilidaddocumento t2 WHERE t2.documento.idDocumento = d.idDocumento AND t2.unidadremitente = :idAreaOrigen)) ";
		}

		sql += " order by d.fechaDocumento desc ";

		Query q = em.createQuery(sql);

		q.setParameter("enumerado", Constantes.Si);

		if(sNumeroDocumento != null){
			q.setParameter("numeroDoc", sNumeroDocumento);
		}
		if(sNumeroMesaPartes != null){
			q.setParameter("numeroMP", sNumeroMesaPartes);
		}
		if(sAsuntoDocumento != null){
			q.setParameter("asuntoDoc", sAsuntoDocumento);
		}
		if(sNumeroExpediente != null){
			q.setParameter("numeroExp", sNumeroExpediente);
		}
		if(sAsuntoExpediente != null){
			q.setParameter("asuntoExp", sAsuntoExpediente);
		}
		if(sEstadoExpediente != null){
			q.setParameter("estadoExp", sEstadoExpediente);
		}
		if(sConcesionario != null){
			q.setParameter("concesionario", sConcesionario);
		}
		if(sCliente != null){
			q.setParameter("cliente", sCliente);
		}
		if(sPropietario != null){
			q.setParameter("propietario", sPropietario);
		}
		if(remitente != null){
			q.setParameter("remitente", remitente);
		}
		if(fechaDocumentoInicio != null){
			q.setParameter("fechaDocInicio", fechaDocumentoInicio);
		}
		if(fechaDocumentoFinal != null){
			q.setParameter("fechaDocFin", fechaDocumentoFinal);
		}
		if(fechaExpedienteInicio != null){
			q.setParameter("fechaExpInicio", fechaExpedienteInicio);
		}
		if(fechaExpedienteFinal != null){
			q.setParameter("fechaExpFin", fechaExpedienteFinal);
		}
		if(proceso != null){
			q.setParameter("idProceso", proceso);
		}
		if(areaDestino != null){
			q.setParameter("idAreaDestino", areaDestino);
		}
		if(tipoDocumento != null){
			q.setParameter("idTipoDoc", tipoDocumento);
		}
		if(areaOrigen != null){
			q.setParameter("idAreaOrigen", areaOrigen);
		}

		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Documento> busquedaRapidaDocumento(String asunto) {
		log.debug("-> [DAO] DocumentoDAO - busquedaRapidaDocumento():List<Documento> ");

		String sql = "SELECT d FROM Documento d WHERE d.documentoreferencia IS NULL AND (LOWER(d.asunto) LIKE :asunto OR LOWER(d.expediente.asunto) LIKE :asunto) ";
		Query q = em.createQuery(sql).setParameter("asunto", asunto);
		return q.getResultList();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Integer> getUsuariosPermitidos(Integer idDocumento){
		log.debug("-> [DAO] DocumentoDAO - getUsuariosPermitidos():List<Integer> ");

		String sql = "select distinct(destinatario) from ("+
				"select td.destinatario from trazabilidaddocumento td where td.documento = :idDocumento "+
				"union all "+
				"select tb.destinatario from trazabilidadcopia tb where tb.documento = :idDocumento " +
				"union all "+
				"select ta.destinatario from trazabilidadapoyo ta where (select d.documentoreferencia from documento d where d.iddocumento = ta.documento) = :idDocumento)";
		List lista = em.createNativeQuery(sql).setParameter("idDocumento", idDocumento).getResultList();
		return (List<Integer>)lista;
	}

	public TrazabilidadapoyoDAO getTrazabilidadapoyoDAO() {
		return trazabilidadapoyoDAO;
	}

	public void setTrazabilidadapoyoDAO(TrazabilidadapoyoDAO trazabilidadapoyoDAO) {
		this.trazabilidadapoyoDAO = trazabilidadapoyoDAO;
	}

	public UsuarioDAO getUsuarioDAO() {
		return usuarioDAO;
	}

	public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}

	@Override
        //jcc
	public List findByDataUFFiltro(Usuario objUsuario, String recibidos,
			BusquedaAvanzada objFiltro) {
		log.debug("-> [DAO] DocumentoDAO - findByDataUF():List ");
                
                boolean bandera = false;
                Usuario u = new Usuario();
                u.setIdUnidadPerfil(objUsuario.getIdUnidadPerfil());
                u.setIdFuncionPerfil(objUsuario.getIdFuncionPerfil());
                u.setIdusuario(objUsuario.getIdUsuarioPerfil());

                List<Usuarioxunidadxfuncion> lista = usuarioxunidadxfuncionDAO.getUsuarioByUnidadByFuncionListRol(u);
                if (lista!=null && lista.size()>0){
                   for (int i=0;i<lista.size();i++){
                      if (lista.get(i).getIdrol().toString().equals(Constantes.COD_ROL_MENSAJERIA.toString())){
                          bandera = true;
                          break;
                      }
                   }
                }    

                List data = new ArrayList();
		Usuario usuario = usuarioDAO.findByIdUsuario(objUsuario.getIdusuario());

		StringBuffer sqlMaster = new StringBuffer();
		sqlMaster.append("SELECT f FROM FilaBandejaUF f ");

		StringBuffer sqlCondicion1 = new StringBuffer();
		sqlCondicion1.append(sqlMaster.toString());
		StringBuffer sql = new StringBuffer();
		// ******************************************************************************************
		// BUSCANDO MIS DOCUMENTOS SIN REEMPLAZOS
		// ******************************************************************************************
		sql.append(sqlMaster.toString());

		sql.append("WHERE f.propietario.idusuario = :idUsuario AND f.unidadpropietario = :idUnidadPropietario AND f.cargopropietario = :idCargoPropietario AND chrestado IN ('A','P') AND f.flagatendido is null AND f.flagmultiple is null ");
		//sql.append("AND (SELECT COUNT (r.idreemplazo) from Reemplazo r WHERE r.estado = 'A' AND r.idreemplazado = :idUsuario AND r.idproceso = f.proceso.idproceso AND CURRENT_DATE > r.fechainicialreemplazo and CURRENT_DATE <r.fechafinalreemplazo) = 0 ");
		if(recibidos.equals("R")){
                    sql.append(" AND f.estadorecepcionvirtual = '0' and f.firmado = 'N' AND (f.autor.idusuario != :idUsuario OR f.numeroTrazabilidad != 1 )");
		}if(recibidos.equals("M")){
                    sql.append(" AND f.estadorecepcionvirtual = '0' and f.autor.idusuario = :idUsuario AND f.numeroTrazabilidad = 1 ");
		}if (recibidos.equals("F")){
                    sql.append(" AND f.estadorecepcionvirtual = '0' and f.firmado = 'S' ");
                }if (bandera){
                    sql.append(" AND  f.despacho = '0' "); 
                }
               
		sql.append(" AND ( :fechaDia >= f.fechaaccion ) ");
                                
				//1
				if(!StringUtils.isEmpty(objFiltro.getNumeroExpediente())){
					sql.append("AND LOWER(f.expediente) LIKE :nroExpediente ");
				}
				//2
				if(!StringUtils.isEmpty(objFiltro.getNumeroDocumento())){
					sql.append("AND LOWER(f.documento) LIKE :nroDocumento ");
				}
				//3
				if(!StringUtils.isEmpty(objFiltro.getTipoDocumento())){
					sql.append("AND f.idtipodocumento = :idTipoDocumento ");
				}
				//4
				if(!StringUtils.isEmpty(objFiltro.getCliente())){
					sql.append("AND f.idcliente = :idCliente ");
				}
				//5
				if(!StringUtils.isEmpty(objFiltro.getAsuntodocumento())){
					sql.append("AND LOWER(f.asunto) LIKE :asuntoDocumento ");
				}
				//6
				if(!StringUtils.isEmpty(objFiltro.getAsuntoexpediente())){
					sql.append("AND LOWER(f.asuntoExpediente) LIKE :asuntoExpediente ");
				}
				//7
				if(!StringUtils.isEmpty(objFiltro.getFechaDesde())){
					sql.append("AND f.fechaaccion >= :fechaDesde ");
				}
				//8
				if(!StringUtils.isEmpty(objFiltro.getFechaHasta())){
					sql.append("AND f.fechaaccion <= :fechaHasta ");
				}
                                
                                if(!StringUtils.isEmpty(objFiltro.getNroHT())){
					 sql.append(" AND f.nroTramite = :nroHT ");
				}
                                
                               
		sql.append("ORDER BY f.fechaaccion DESC ");

		// ******************************************************************************************
		// BUSCANDO MIS DOCUMENTOS CON REEMPLAZOS
		// ******************************************************************************************
		/*sql.append("UNION ");
		sql.append(sqlMaster.toString());

		sql.append("WHERE f.propietario.idusuario = :idUsuario AND chrestado = :chrEstado ");
		sql.append("AND f.propietario.idusuario IN ELEMENTS(SELECT r.idreemplazado FROM Reemplazo r WHERE WHERE r.estado = 'A' AND r.idusuario = :idUsuario AND r.idproceso = f.proceso.idproceso AND CURRENT_DATE > r.fechainicialreemplazo and CURRENT_DATE <r.fechafinalreemplazo )");
		if(recibidos){
			sql.append("AND (f.autor.idusuario != :idUsuario OR f.numeroTrazabilidad != 1) ");
		}else{
			sql.append("AND f.autor.idusuario = :idUsuario AND f.numeroTrazabilidad = 1 ");
		}
		sql.append(" AND ( :fechaDia >= f.fechaaccion ) ");//--

				//1
				if(!StringUtils.isEmpty(objFiltro.getNumeroExpediente())){
					sql.append("AND LOWER(f.expediente) LIKE :nroExpediente ");
				}
				//2
				if(!StringUtils.isEmpty(objFiltro.getNumeroDocumento())){
					sql.append("AND LOWER(f.documento) LIKE :nroDocumento ");
				}
				//3
				if(!StringUtils.isEmpty(objFiltro.getTipoDocumento())){
					sql.append("AND e.trazabilidaddocumento.documento.tipoDocumento.idtipodocumento = :idTipoDocumento ");
				}
				//4
				if(!StringUtils.isEmpty(objFiltro.getCliente())){
					sql.append("AND f.cliente = :idCliente ");
				}
				//5
				if(!StringUtils.isEmpty(objFiltro.getAsuntodocumento())){
					sql.append("AND LOWER(f.asunto) LIKE :asuntoDocumento ");
				}
				//6
				if(!StringUtils.isEmpty(objFiltro.getAsuntoexpediente())){
					sql.append("AND LOWER(f.asuntoExpediente) LIKE :asuntoExpediente ");
				}
				//7
				if(!StringUtils.isEmpty(objFiltro.getFechaDesde())){
					sql.append("AND f.fechaaccion >= :fechaDesde ");
				}
				//8
				if(!StringUtils.isEmpty(objFiltro.getFechaHasta())){
					sql.append("AND f.fechaaccion <= :fechaHasta ");
				}
				
		sql.append("ORDER BY f.expediente, f.fechaaccion DESC ");
                */
                
		Query q = em.createQuery(sql.toString());
		q.setParameter("idUsuario", objUsuario.getIdUsuarioPerfil());
                q.setParameter("idUnidadPropietario", objUsuario.getIdUnidadPerfil());
                q.setParameter("idCargoPropietario",  objUsuario.getIdFuncionPerfil());
		//q.setParameter("chrEstado", Constantes.ESTADO_ACTIVO);

				//1
				if(!StringUtils.isEmpty(objFiltro.getNumeroExpediente())){
					q.setParameter("nroExpediente", "%"+objFiltro.getNumeroExpediente().toLowerCase()+"%");
				}
				//2
				if(!StringUtils.isEmpty(objFiltro.getNumeroDocumento())){
					q.setParameter("nroDocumento", "%"+objFiltro.getNumeroDocumento().toLowerCase()+"%");
				}
				//3
				if(!StringUtils.isEmpty(objFiltro.getTipoDocumento())){
					q.setParameter("idTipoDocumento", Integer.parseInt(objFiltro.getTipoDocumento()));
				}
				//4
				if(!StringUtils.isEmpty(objFiltro.getCliente())){
					q.setParameter("idCliente", Integer.parseInt(objFiltro.getCliente()));
				}
				//5
				if(!StringUtils.isEmpty(objFiltro.getAsuntodocumento())){
					q.setParameter("asuntoDocumento", "%"+objFiltro.getAsuntodocumento().toLowerCase()+"%");
				}
				//6
				if(!StringUtils.isEmpty(objFiltro.getAsuntoexpediente())){
					q.setParameter("asuntoExpediente", "%"+objFiltro.getAsuntoexpediente().toLowerCase()+"%");
				}
                                
                                if(!StringUtils.isEmpty(objFiltro.getNroHT())){
                                        q.setParameter("nroHT", Integer.parseInt(objFiltro.getNroHT()));
                                }
				//7
				if(!StringUtils.isEmpty(objFiltro.getFechaDesde())){
					SimpleDateFormat fechita = new SimpleDateFormat("dd/MM/yyyy");
					Calendar fecha = Calendar.getInstance();
					try {
						fecha.setTime(fechita.parse(objFiltro.getFechaDesde()));
						fecha.set(Calendar.HOUR_OF_DAY, 0);
						fecha.set(Calendar.MINUTE, 0);
						fecha.set(Calendar.SECOND, 0);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					q.setParameter("fechaDesde", fecha.getTime());
				}
				//8
				if(!StringUtils.isEmpty(objFiltro.getFechaHasta())){
					SimpleDateFormat fechita = new SimpleDateFormat("dd/MM/yyyy");
					Calendar fecha = Calendar.getInstance();
					try {
						fecha.setTime(fechita.parse(objFiltro.getFechaHasta()));
						fecha.set(Calendar.HOUR_OF_DAY, 23);
						fecha.set(Calendar.MINUTE, 59);
						fecha.set(Calendar.SECOND, 59);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					q.setParameter("fechaHasta", fecha.getTime());
				}
                                
                                
				
		Calendar fechaDia = Calendar.getInstance();
		/*fechaDia.set(Calendar.HOUR_OF_DAY, 23);
		fechaDia.set(Calendar.MINUTE, 59);
		fechaDia.set(Calendar.SECOND, 59); */
		q.setParameter("fechaDia", fechaDia.getTime());
                
		try {
			List<FilaBandejaUF> temp = q.getResultList();
			if(temp != null && !temp.isEmpty()){
				if(recibidos.equals("R") || recibidos.equals("F")){
					if(usuario.getBandejaAgrupada() != null && usuario.getBandejaAgrupada().equals(Constantes.Si)){
						Set<String> expedientesLista = new HashSet<String>();
						Map<String, Integer> mapCantidades = new HashMap<String, Integer>();
						Map<String, Boolean> mapLeidos = new HashMap<String, Boolean>();

						/**Rutina para el primer documento --------------------------------------------------------------------------------------*/
						FilaBandejaUF fila = temp.get(0);

						expedientesLista.add(fila.getExpediente());
						mapCantidades.put(fila.getExpediente(), 1);
						mapLeidos.put(fila.getExpediente(), fila.getLeido().equals(Constantes.No.toString()));

						data.add(llenarItemUF(fila));

						/**Rutina para el resto de documentos------------------------------------------------------------------------------------*/
						for (int i = 1; i < temp.size(); i++) {
							fila = temp.get(i);

							if(expedientesLista.contains(fila.getExpediente())){
								mapCantidades.put(fila.getExpediente(), mapCantidades.get(fila.getExpediente())+1);
								mapLeidos.put(fila.getExpediente(), mapLeidos.get(fila.getExpediente()) || fila.getLeido().equals(Constantes.No.toString()));
							}else{
								expedientesLista.add(fila.getExpediente());
								mapCantidades.put(fila.getExpediente(), 1);
								mapLeidos.put(fila.getExpediente(), fila.getLeido().equals(Constantes.No.toString()));

								ItemUF uuf = llenarItemUF(fila);
								data.add(uuf);
							}
						}

						for(Object f : data){
							ItemUF item = (ItemUF)f;
							item.setExpBtn(item.getExpBtn() + "[" + mapCantidades.get(item.getExpediente()) + "]" + mapLeidos.get(item.getExpediente()));
						}
					}else{
						for (int i = 0; i < temp.size(); i++) {
							FilaBandejaUF fila = temp.get(i);
							ItemUF item = llenarItemUF(fila);
							item.setExpBtn("-");
							data.add(item);

						}
					}
				}else{
					for (int i = 0; i < temp.size(); i++) {
						FilaBandejaUF fila = temp.get(i);
						data.add(llenarItemUF(fila));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("ERROR: no tiene data", e.fillInStackTrace());
		}

		return data;

	}
        
        public List findByPendientesDataUFFiltro(Usuario objUsuario, 
			BusquedaAvanzada objFiltro) {
                        log.debug("-> [DAO] DocumentoDAO - findByPendientesDataUFFiltro():List ");

                        List data = new ArrayList();
                        //Usuario usuario = usuarioDAO.findByIdUsuario(objUsuario.getIdusuario());

                        StringBuffer sqlMaster = new StringBuffer();
                        sqlMaster.append("SELECT f FROM FilaBandejaUF f ");

                        StringBuffer sqlCondicion1 = new StringBuffer();
                        sqlCondicion1.append(sqlMaster.toString());
                        StringBuffer sql = new StringBuffer();
                        // ******************************************************************************************
                        // BUSCANDO MIS DOCUMENTOS SIN REEMPLAZOS
                        // ******************************************************************************************
                        sql.append(sqlMaster.toString());

                        sql.append("WHERE f.unidadpropietario = :idUnidadPropietario AND chrestado IN ('A','P') AND f.flagatendido is null AND f.flagmultiple is null and f.pendiente is not null and f.pendiente = '1' ");
                        sql.append(" AND f.firmado = 'N' AND (f.autor.idusuario != f.propietario.idusuario OR f.numeroTrazabilidad != 1 )");
                        sql.append(" AND ( :fechaDia >= f.fechaaccion ) ");
                                
                        //1
                        if(!StringUtils.isEmpty(objFiltro.getNumeroExpediente())){
                                sql.append("AND LOWER(f.expediente) LIKE :nroExpediente ");
                        }
                        //2
                        if(!StringUtils.isEmpty(objFiltro.getNumeroDocumento())){
                                sql.append("AND LOWER(f.documento) LIKE :nroDocumento ");
                        }
                        //3
                        if(!StringUtils.isEmpty(objFiltro.getTipoDocumento())){
                                sql.append("AND f.idtipodocumento = :idTipoDocumento ");
                        }
                        //4
                        if(!StringUtils.isEmpty(objFiltro.getCliente())){
                                sql.append("AND f.idcliente = :idCliente ");
                        }
                        
                        if(!StringUtils.isEmpty(objFiltro.getUsuarioRemitente())){
                            sql.append(" AND f.propietario.idusuario = :idUsuario ");
                        }
                        
                        //5
                        if(!StringUtils.isEmpty(objFiltro.getAsuntodocumento())){
                                sql.append("AND LOWER(f.asunto) LIKE :asuntoDocumento ");
                        }
                        //6
                        if(!StringUtils.isEmpty(objFiltro.getAsuntoexpediente())){
                                sql.append("AND LOWER(f.asuntoExpediente) LIKE :asuntoExpediente ");
                        }
                        //7
                        if(!StringUtils.isEmpty(objFiltro.getFechaDesde())){
                                sql.append("AND f.fechaaccion >= :fechaDesde ");
                        }
                        //8
                        if(!StringUtils.isEmpty(objFiltro.getFechaHasta())){
                                sql.append("AND f.fechaaccion <= :fechaHasta ");
                        }

                        if(!StringUtils.isEmpty(objFiltro.getNroHT())){
                                 sql.append(" AND f.nroTramite = :nroHT ");
                        }


                        sql.append("ORDER BY f.fechaaccion DESC ");


                        Query q = em.createQuery(sql.toString());
                        q.setParameter("idUnidadPropietario", objUsuario.getIdUnidadPerfil());
                        //1
                        if(!StringUtils.isEmpty(objFiltro.getNumeroExpediente())){
                                q.setParameter("nroExpediente", "%"+objFiltro.getNumeroExpediente().toLowerCase()+"%");
                        }
                        
                        if(!StringUtils.isEmpty(objFiltro.getUsuarioRemitente())){
                                q.setParameter("idUsuario", Integer.parseInt(objFiltro.getUsuarioRemitente()));
                        }
                        
                        //2
                        if(!StringUtils.isEmpty(objFiltro.getNumeroDocumento())){
                                q.setParameter("nroDocumento", "%"+objFiltro.getNumeroDocumento().toLowerCase()+"%");
                        }
                        //3
                        if(!StringUtils.isEmpty(objFiltro.getTipoDocumento())){
                                q.setParameter("idTipoDocumento", Integer.parseInt(objFiltro.getTipoDocumento()));
                        }
                        //4
                        if(!StringUtils.isEmpty(objFiltro.getCliente())){
                                q.setParameter("idCliente", Integer.parseInt(objFiltro.getCliente()));
                        }
                        //5
                        if(!StringUtils.isEmpty(objFiltro.getAsuntodocumento())){
                                q.setParameter("asuntoDocumento", "%"+objFiltro.getAsuntodocumento().toLowerCase()+"%");
                        }
                        //6
                        if(!StringUtils.isEmpty(objFiltro.getAsuntoexpediente())){
                                q.setParameter("asuntoExpediente", "%"+objFiltro.getAsuntoexpediente().toLowerCase()+"%");
                        }

                        if(!StringUtils.isEmpty(objFiltro.getNroHT())){
                                q.setParameter("nroHT", Integer.parseInt(objFiltro.getNroHT()));
                        }
                        //7
                        if(!StringUtils.isEmpty(objFiltro.getFechaDesde())){
                                SimpleDateFormat fechita = new SimpleDateFormat("dd/MM/yyyy");
                                Calendar fecha = Calendar.getInstance();
                                try {
                                        fecha.setTime(fechita.parse(objFiltro.getFechaDesde()));
                                        fecha.set(Calendar.HOUR_OF_DAY, 0);
                                        fecha.set(Calendar.MINUTE, 0);
                                        fecha.set(Calendar.SECOND, 0);
                                } catch (ParseException e) {
                                        e.printStackTrace();
                                }
                                q.setParameter("fechaDesde", fecha.getTime());
                        }
                        //8
                        if(!StringUtils.isEmpty(objFiltro.getFechaHasta())){
                                SimpleDateFormat fechita = new SimpleDateFormat("dd/MM/yyyy");
                                Calendar fecha = Calendar.getInstance();
                                try {
                                        fecha.setTime(fechita.parse(objFiltro.getFechaHasta()));
                                        fecha.set(Calendar.HOUR_OF_DAY, 23);
                                        fecha.set(Calendar.MINUTE, 59);
                                        fecha.set(Calendar.SECOND, 59);
                                } catch (ParseException e) {
                                        e.printStackTrace();
                                }
                                q.setParameter("fechaHasta", fecha.getTime());
                        }
                                
                                
				
		Calendar fechaDia = Calendar.getInstance();
		q.setParameter("fechaDia", fechaDia.getTime());
                
		try {
			List<FilaBandejaUF> temp = q.getResultList();
			if(temp != null && !temp.isEmpty()){
			   if(true){
					
				for(int i = 0; i < temp.size(); i++) {
                                    FilaBandejaUF fila = temp.get(i);
                                    ItemUF item = llenarItemUF(fila);
                                    item.setExpBtn("-");
                                    data.add(item);
                                }
			    }
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("ERROR: no tiene data", e.fillInStackTrace());
		}

		return data;

	}



	@Override
	public List findByDataUFDocumentosRecibidos(Integer iIdUsuario,
			boolean recibidos) {
		log.debug("-> [DAO] DocumentoDAO - findByDataUFDocumentosRecibidos():List ");

		List data = new ArrayList();
		Usuario usuario = usuarioDAO.findByIdUsuario(iIdUsuario);

		StringBuffer sqlMaster = new StringBuffer();
		sqlMaster.append("SELECT f FROM FilaBandejaUF f ");

		StringBuffer sqlCondicion1 = new StringBuffer();
		sqlCondicion1.append(sqlMaster.toString());
		StringBuffer sql = new StringBuffer();
		// ******************************************************************************************
		// BUSCANDO MIS DOCUMENTOS SIN REEMPLAZOS
		// ******************************************************************************************
		sql.append(sqlMaster.toString());

		sql.append("WHERE f.propietario.idusuario = :idUsuario AND chrestado = :chrEstado AND despachado = :despachado ");
		sql.append("AND (SELECT COUNT (r.idreemplazo) from Reemplazo r WHERE r.estado = 'A' AND r.idreemplazado = :idUsuario AND r.idproceso = f.proceso.idproceso AND CURRENT_DATE > r.fechainicialreemplazo and CURRENT_DATE <r.fechafinalreemplazo) = 0 ");
		if(recibidos){
			sql.append("AND (f.autor.idusuario != :idUsuario OR f.numeroTrazabilidad != 1 )");
		}else{
			sql.append("AND f.autor.idusuario = :idUsuario AND f.numeroTrazabilidad = 1 ");
		}
		sql.append(" AND ( :fechaDia >= f.fechaaccion ) ");
		sql.append("ORDER BY f.fechaaccion DESC ");

		// ******************************************************************************************
		// BUSCANDO MIS DOCUMENTOS CON REEMPLAZOS
		// ******************************************************************************************
		sql.append("UNION ");
		sql.append(sqlMaster.toString());

		sql.append("WHERE f.propietario.idusuario = :idUsuario AND chrestado = :chrEstado AND despachado = :despachado ");
		sql.append("AND f.propietario.idusuario IN ELEMENTS(SELECT r.idreemplazado FROM Reemplazo r WHERE WHERE r.estado = 'A' AND r.idusuario = :idUsuario AND r.idproceso = f.proceso.idproceso AND CURRENT_DATE > r.fechainicialreemplazo and CURRENT_DATE <r.fechafinalreemplazo )");
		if(recibidos){
			sql.append("AND (f.autor.idusuario != :idUsuario OR f.numeroTrazabilidad != 1) ");
		}else{
			sql.append("AND f.autor.idusuario = :idUsuario AND f.numeroTrazabilidad = 1 ");
		}
		sql.append(" AND ( :fechaDia >= f.fechaaccion ) ");//--
		sql.append("ORDER BY f.fechaaccion DESC ");

		Query q = em.createQuery(sql.toString());
		q.setParameter("idUsuario", iIdUsuario);
		q.setParameter("chrEstado", Constantes.ESTADO_ACTIVO);
		q.setParameter("despachado", Constantes.ESTADO_NO_DESPACHADO);

		Calendar fechaDia = Calendar.getInstance();
		fechaDia.set(Calendar.HOUR_OF_DAY, 23);
		fechaDia.set(Calendar.MINUTE, 59);
		fechaDia.set(Calendar.SECOND, 59);
		q.setParameter("fechaDia", fechaDia.getTime());

		//return q.getResultList();

			try {
				List<FilaBandejaUF> temp = q.getResultList();
				if(temp != null && !temp.isEmpty()){
					if(recibidos){
						if(usuario.getBandejaAgrupada() != null && usuario.getBandejaAgrupada().equals(Constantes.Si)){
							Set<String> expedientesLista = new HashSet<String>();

							/**Rutina para el primer documento --------------------------------------------------------------------------------------*/
							FilaBandejaUF fila = temp.get(0);

							expedientesLista.add(fila.getExpediente());

							//data.add(llenarItemUF(fila));

							data.add(fila);

							/**Rutina para el resto de documentos------------------------------------------------------------------------------------*/
							for (int i = 1; i < temp.size(); i++) {
								fila = temp.get(i);

								if(expedientesLista.contains(fila.getExpediente())){

								}else{
									expedientesLista.add(fila.getExpediente());

									//ItemUF uuf = llenarItemUF(fila);
									data.add(fila);
								}
							}

						}else{
							for (int i = 0; i < temp.size(); i++) {
								FilaBandejaUF fila = temp.get(i);
								//ItemUF item = llenarItemUF(fila);
								//item.setExpBtn("-");
								data.add(fila);

							}
						}
					}else{
						for (int i = 0; i < temp.size(); i++) {
							FilaBandejaUF fila = temp.get(i);
							//data.add(llenarItemUF(fila));
							data.add(fila);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("ERROR: no tiene data", e.fillInStackTrace());
			}

			return data;

	}




	@Override
	public List findByDataUFDocumentosRecibidosFiltro(Integer iIdUsuario,
			boolean recibidos, BusquedaAvanzada objFiltro) {

		log.debug("-> [DAO] DocumentoDAO - findByDataUFDocumentosRecibidosFiltro():List ");

		List data = new ArrayList();
		Usuario usuario = usuarioDAO.findByIdUsuario(iIdUsuario);

		StringBuffer sqlMaster = new StringBuffer();
		sqlMaster.append("SELECT NEW FilaBandejaUF (f.id, f.fechaaccion, f.expediente) FROM FilaBandejaUF f ");

		StringBuffer sqlCondicion1 = new StringBuffer();
		sqlCondicion1.append(sqlMaster.toString());
		StringBuffer sql = new StringBuffer();
		// ******************************************************************************************
		// BUSCANDO MIS DOCUMENTOS SIN REEMPLAZOS
		// ******************************************************************************************
		sql.append(sqlMaster.toString());

		sql.append("WHERE f.propietario.idusuario = :idUsuario AND chrestado = :chrEstado and f.despachado = 'N' ");
		sql.append("AND (SELECT COUNT (r.idreemplazo) from Reemplazo r WHERE r.estado = 'A' AND r.idreemplazado = :idUsuario AND r.idproceso = f.proceso.idproceso AND CURRENT_DATE > r.fechainicialreemplazo and CURRENT_DATE <r.fechafinalreemplazo) = 0 ");
		if(recibidos){
			sql.append("AND (f.autor.idusuario != :idUsuario OR f.numeroTrazabilidad != 1 )");
		}else{
			sql.append("AND f.autor.idusuario = :idUsuario AND f.numeroTrazabilidad = 1 ");
		}
		sql.append(" AND ( :fechaDia >= f.fechaaccion ) ");

				//1
				if(!StringUtils.isEmpty(objFiltro.getNumeroExpediente())){
					sql.append("AND LOWER(f.expediente) LIKE :nroExpediente ");
				}
				//2
				if(!StringUtils.isEmpty(objFiltro.getNumeroDocumento())){
					sql.append("AND LOWER(f.documento) LIKE :nroDocumento ");
				}
				//3
				/*if(!StringUtils.isEmpty(objFiltro.getTipoDocumento())){
					sql.append("AND f.idtipodocumento = :idTipoDocumento ");
				}*/
				//4
				if(!StringUtils.isEmpty(objFiltro.getCliente())){
					sql.append("AND f.cliente = :idCliente ");
				}
				//5
				if(!StringUtils.isEmpty(objFiltro.getAsuntodocumento())){
					sql.append("AND LOWER(f.asunto) LIKE :asuntoDocumento ");
				}
				//6
				if(!StringUtils.isEmpty(objFiltro.getAsuntoexpediente())){
					sql.append("AND LOWER(f.asuntoExpediente) LIKE :asuntoExpediente ");
				}
				//7
				/*if(!StringUtils.isEmpty(objFiltro.getAreaOrigen())){
					if(objFiltro.getAreaOrigen().equals("0") || objFiltro.getAreaOrigen().equals("189")){
					}else{
						//sql.append("AND (SELECT un.idunidad FROM Unidad un WHERE un.idunidad = (SELECT us.unidad.idunidad  FROM Usuario us WHERE us.idusuario = f.remitente.idusuario ) ) = :idAreaOrigen ");
						//sql.append("AND f.remitente.idusuario IN ELEMENTS(SELECT us.idusuario FROM Usuario us WHERE us.unidad.idunidad =:idAreaOrigen ) ");
						//sql.append("AND und.unidad.idunidad =:idAreaOrigen  ");
					}
				}*/
				//8
				if(!StringUtils.isEmpty(objFiltro.getPrioridad())){
					if(objFiltro.getPrioridad().equals("-1")){
					}else{
						sql.append("AND LOWER(f.prioridad) LIKE :prioridad ");
					}
				}

		sql.append("ORDER BY f.fechaaccion DESC ");

		// ******************************************************************************************
		// BUSCANDO MIS DOCUMENTOS CON REEMPLAZOS
		// ******************************************************************************************
		sql.append("UNION ");
		sql.append(sqlMaster.toString());

		sql.append("WHERE f.propietario.idusuario = :idUsuario AND chrestado = :chrEstado and f.despachado = 'N' ");
		sql.append("AND f.propietario.idusuario IN ELEMENTS(SELECT r.idreemplazado FROM Reemplazo r WHERE WHERE r.estado = 'A' AND r.idusuario = :idUsuario AND r.idproceso = f.proceso.idproceso AND CURRENT_DATE > r.fechainicialreemplazo and CURRENT_DATE <r.fechafinalreemplazo )");
		if(recibidos){
			sql.append("AND (f.autor.idusuario != :idUsuario OR f.numeroTrazabilidad != 1) ");
		}else{
			sql.append("AND f.autor.idusuario = :idUsuario AND f.numeroTrazabilidad = 1 ");
		}
		sql.append(" AND ( :fechaDia >= f.fechaaccion ) ");//--

				//1
				if(!StringUtils.isEmpty(objFiltro.getNumeroExpediente())){
					sql.append("AND LOWER(f.expediente) LIKE :nroExpediente ");
				}
				//2
				if(!StringUtils.isEmpty(objFiltro.getNumeroDocumento())){
					sql.append("AND LOWER(f.documento) LIKE :nroDocumento ");
				}
				//3
				/*if(!StringUtils.isEmpty(objFiltro.getTipoDocumento())){
					sql.append("AND e.trazabilidaddocumento.documento.tipoDocumento.idtipodocumento = :idTipoDocumento ");
				}*/
				//4
				if(!StringUtils.isEmpty(objFiltro.getCliente())){
					sql.append("AND f.cliente = :idCliente ");
				}
				//5
				if(!StringUtils.isEmpty(objFiltro.getAsuntodocumento())){
					sql.append("AND LOWER(f.asunto) LIKE :asuntoDocumento ");
				}
				//6
				if(!StringUtils.isEmpty(objFiltro.getAsuntoexpediente())){
					sql.append("AND LOWER(f.asuntoExpediente) LIKE :asuntoExpediente ");
				}
				//7
				/*if(!StringUtils.isEmpty(objFiltro.getAreaOrigen())){
					if(objFiltro.getAreaOrigen().equals("0") || objFiltro.getAreaOrigen().equals("189")  ){
					}else{
						//sql.append("AND (SELECT un.idunidad FROM Unidad un WHERE un.idunidad = (SELECT us.idusuario FROM Usuario us WHERE us.idusuario = f.remitente.idusuario ) ) = :idAreaOrigen ");
						//sql.append("AND f.remitente.idusuario IN ELEMENTS(SELECT us.idusuario FROM Usuario us WHERE us.unidad.idunidad =:idAreaOrigen ) ");
						//sql.append("AND und.unidad.idunidad =:idAreaOrigen  ");
					}
				}*/
				//8
				if(!StringUtils.isEmpty(objFiltro.getPrioridad())){
					if(objFiltro.getPrioridad().equals("-1")){
					}else{
						sql.append("AND LOWER(f.prioridad) LIKE :prioridad ");
					}
				}


		sql.append("ORDER f.fechaaccion DESC ");
		Query q = em.createQuery(sql.toString());
		q.setParameter("idUsuario", iIdUsuario);
		q.setParameter("chrEstado", Constantes.ESTADO_ACTIVO);

				//1
				if(!StringUtils.isEmpty(objFiltro.getNumeroExpediente())){
					q.setParameter("nroExpediente", "%"+objFiltro.getNumeroExpediente().toLowerCase()+"%");
				}
				//2
				if(!StringUtils.isEmpty(objFiltro.getNumeroDocumento())){
					q.setParameter("nroDocumento", "%"+objFiltro.getNumeroDocumento().toLowerCase()+"%");
				}
				//3
				/*if(!StringUtils.isEmpty(objFiltro.getTipoDocumento())){
					q.setParameter("idTipoDocumento", Integer.parseInt(objFiltro.getTipoDocumento()));
					System.out.println("VER  idTipoDocumento"+ objFiltro.getTipoDocumento());
				}*/
				//4
				if(!StringUtils.isEmpty(objFiltro.getCliente())){
					q.setParameter("idCliente", Integer.parseInt(objFiltro.getCliente()));
				}
				//5
				if(!StringUtils.isEmpty(objFiltro.getAsuntodocumento())){
					q.setParameter("asuntoDocumento", "%"+objFiltro.getAsuntodocumento().toLowerCase()+"%");
				}
				//6
				if(!StringUtils.isEmpty(objFiltro.getAsuntoexpediente())){
					q.setParameter("asuntoExpediente", "%"+objFiltro.getAsuntoexpediente().toLowerCase()+"%");
				}
				//7
				/*if(!StringUtils.isEmpty(objFiltro.getAreaOrigen())){
					if(objFiltro.getAreaOrigen().equals("0") || objFiltro.getAreaOrigen().equals("189") ){
					}else{
						//q.setParameter("idAreaOrigen", Integer.parseInt(objFiltro.getAreaOrigen()));
					}
				}*/
				//8
				if(!StringUtils.isEmpty(objFiltro.getPrioridad())){
					if(objFiltro.getPrioridad().equals("-1")){
					}else{
						q.setParameter("prioridad", objFiltro.getPrioridad());
					}
				}
				//

		Calendar fechaDia = Calendar.getInstance();
		fechaDia.set(Calendar.HOUR_OF_DAY, 23);
		fechaDia.set(Calendar.MINUTE, 59);
		fechaDia.set(Calendar.SECOND, 59);
		q.setParameter("fechaDia", fechaDia.getTime());

//		return q.getResultList();

		try {
			List<FilaBandejaUF> temp = q.getResultList();
			if(temp != null && !temp.isEmpty()){
				if(recibidos){
					if(usuario.getBandejaAgrupada() != null && usuario.getBandejaAgrupada().equals(Constantes.Si)){
						Set<String> expedientesLista = new HashSet<String>();

						/**Rutina para el primer documento --------------------------------------------------------------------------------------*/
						FilaBandejaUF fila = temp.get(0);

						expedientesLista.add(fila.getExpediente());

						//data.add(llenarItemUF(fila));

						data.add(fila);

						/**Rutina para el resto de documentos------------------------------------------------------------------------------------*/
						for (int i = 1; i < temp.size(); i++) {
							fila = temp.get(i);

							if(expedientesLista.contains(fila.getExpediente())){

							}else{
								expedientesLista.add(fila.getExpediente());

								//ItemUF uuf = llenarItemUF(fila);
								data.add(fila);
							}
						}

					}else{
						for (int i = 0; i < temp.size(); i++) {
							FilaBandejaUF fila = temp.get(i);
							//ItemUF item = llenarItemUF(fila);
							//item.setExpBtn("-");
							data.add(fila);

						}
					}
				}else{
					for (int i = 0; i < temp.size(); i++) {
						FilaBandejaUF fila = temp.get(i);
						//data.add(llenarItemUF(fila));
						data.add(fila);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("ERROR: no tiene data", e.fillInStackTrace());
		}

		return data;

	}

	/**WCARRASCO Busca los documentos recibidos por un usuario por un solo Expediente----------------------------------------------*/
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List findFilasDXETree(Integer idUsuario, Integer idDocumento, boolean enviados){
		log.debug("-> [DAO] DocumentoDAO - findFilasDXETree():List ");

		List data = new ArrayList();

		StringBuffer sqlMaster = new StringBuffer();
		sqlMaster.append("SELECT NEW FilaBandejaUF (f.id)  FROM FilaBandejaUF f ");

		StringBuffer sql = new StringBuffer();
		// ******************************************************************************************
		// BUSCANDO MIS DOCUMENTOS SIN REEMPLAZOS
		// ******************************************************************************************
		sql.append(sqlMaster.toString());

		sql.append("WHERE chrestado = :chrEstado AND f.propietario.idusuario = :idUsuario AND f.expediente = (SELECT doc.expediente.nroexpediente FROM Documento doc WHERE doc.idDocumento = :idDocumento) AND despachado = :despachado ");
		sql.append("AND (SELECT COUNT (r.idreemplazo) from Reemplazo r WHERE r.estado = 'A' AND r.idreemplazado = :idUsuario AND r.idproceso = f.proceso.idproceso AND CURRENT_DATE > r.fechainicialreemplazo and CURRENT_DATE <r.fechafinalreemplazo) = 0 ");
		if(enviados){
			sql.append("AND (f.autor.idusuario != :idUsuario OR f.numeroTrazabilidad != 1 )");
		}else{
			sql.append("AND f.autor.idusuario = :idUsuario AND f.numeroTrazabilidad = 1 ");
		}

		// ******************************************************************************************
		// BUSCANDO MIS DOCUMENTOS CON REEMPLAZOS
		// ******************************************************************************************
		sql.append("UNION ");
		sql.append(sqlMaster.toString());

		sql.append("WHERE chrestado = :chrEstado AND f.propietario.idusuario = :idUsuario AND f.expediente = (SELECT doc.expediente.nroexpediente FROM Documento doc WHERE doc.idDocumento = :idDocumento) AND despachado = :despachado ");
		sql.append("AND f.propietario.idusuario IN ELEMENTS(SELECT r.idreemplazado FROM Reemplazo r WHERE WHERE r.estado = 'A' AND r.idusuario = :idUsuario AND r.idproceso = f.proceso.idproceso AND CURRENT_DATE > r.fechainicialreemplazo and CURRENT_DATE <r.fechafinalreemplazo )");
		if(enviados){
			sql.append("AND (f.autor.idusuario != :idUsuario OR f.numeroTrazabilidad != 1) ");
		}else{
			sql.append("AND f.autor.idusuario = :idUsuario AND f.numeroTrazabilidad = 1 ");
		}

		Query q = em.createQuery(sql.toString());
		q.setParameter("chrEstado", Constantes.ESTADO_ACTIVO);
		q.setParameter("idUsuario", idUsuario);
		q.setParameter("idDocumento", idDocumento);
		q.setParameter("despachado", Constantes.ESTADO_NO_DESPACHADO);

		try {
			List<FilaBandejaUF> temp = q.getResultList();
			FilaBandejaUF fila;
			for (int i = 0; i < temp.size(); i++) {
				fila = temp.get(i);
				data.add(fila);
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error("ERROR: no tiene data", e.fillInStackTrace());
		}

		return data;
	}

	@Override
	public List findFilasDXETreeFiltro(BusquedaAvanzada objFiltro,
			Integer idUsuario, Integer idDocumento, boolean enviados) {
		log.debug("-> [DAO] DocumentoDAO - findFilasDXETree():List ");

		List data = new ArrayList();

		StringBuffer sqlMaster = new StringBuffer();
		sqlMaster.append("SELECT f FROM FilaBandejaUF f ");

		StringBuffer sql = new StringBuffer();
		// ******************************************************************************************
		// BUSCANDO MIS DOCUMENTOS SIN REEMPLAZOS
		// ******************************************************************************************
		sql.append(sqlMaster.toString());

		sql.append("WHERE chrestado = :chrEstado AND f.propietario.idusuario = :idUsuario AND f.expediente = (SELECT doc.expediente.nroexpediente FROM Documento doc WHERE doc.idDocumento = :idDocumento) AND despachado = :despachado ");
		sql.append("AND (SELECT COUNT (r.idreemplazo) from Reemplazo r WHERE r.estado = 'A' AND r.idreemplazado = :idUsuario AND r.idproceso = f.proceso.idproceso AND CURRENT_DATE > r.fechainicialreemplazo and CURRENT_DATE <r.fechafinalreemplazo) = 0 ");
		if(enviados){
			sql.append("AND (f.autor.idusuario != :idUsuario OR f.numeroTrazabilidad != 1 )");
		}else{
			sql.append("AND f.autor.idusuario = :idUsuario AND f.numeroTrazabilidad = 1 ");
		}

		// ******************************************************************************************
		// BUSCANDO MIS DOCUMENTOS CON REEMPLAZOS
		// ******************************************************************************************
		sql.append("UNION ");
		sql.append(sqlMaster.toString());

		sql.append("WHERE chrestado = :chrEstado AND f.propietario.idusuario = :idUsuario AND f.expediente = (SELECT doc.expediente.nroexpediente FROM Documento doc WHERE doc.idDocumento = :idDocumento) AND despachado = :despachado ");
		sql.append("AND f.propietario.idusuario IN ELEMENTS(SELECT r.idreemplazado FROM Reemplazo r WHERE WHERE r.estado = 'A' AND r.idusuario = :idUsuario AND r.idproceso = f.proceso.idproceso AND CURRENT_DATE > r.fechainicialreemplazo and CURRENT_DATE <r.fechafinalreemplazo )");
		if(enviados){
			sql.append("AND (f.autor.idusuario != :idUsuario OR f.numeroTrazabilidad != 1) ");
		}else{
			sql.append("AND f.autor.idusuario = :idUsuario AND f.numeroTrazabilidad = 1 ");
		}

		Query q = em.createQuery(sql.toString());
		q.setParameter("chrEstado", Constantes.ESTADO_ACTIVO);
		q.setParameter("idUsuario", idUsuario);
		q.setParameter("idDocumento", idDocumento);
		q.setParameter("despachado", Constantes.ESTADO_NO_DESPACHADO);

		try {
			List<FilaBandejaUF> temp = q.getResultList();

			for (int i = 0; i < temp.size(); i++) {
				data.add(llenarItemUF(temp.get(i)));
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error("ERROR: no tiene data", e.fillInStackTrace());
		}

		return data;
	}

	@Override
	public List findByDataUFFiltroIG(Integer iIdUsuario, boolean recibidos,
			BusquedaAvanzada objFiltro) {
log.debug("-> [DAO] DocumentoDAO - findByDataUF():List ");

		List data = new ArrayList();
		Usuario usuario = usuarioDAO.findByIdUsuario(iIdUsuario);

		StringBuffer sqlMaster = new StringBuffer();
		sqlMaster.append("SELECT f FROM FilaBandejaUF f ");

		StringBuffer sqlCondicion1 = new StringBuffer();
		sqlCondicion1.append(sqlMaster.toString());
		StringBuffer sql = new StringBuffer();
		// ******************************************************************************************
		// BUSCANDO MIS DOCUMENTOS SIN REEMPLAZOS
		// ******************************************************************************************
		sql.append(sqlMaster.toString());

		sql.append("WHERE f.propietario.idusuario = :idUsuario AND chrestado = :chrEstado and f.despachado = 'N' ");
		sql.append("AND (SELECT COUNT (r.idreemplazo) from Reemplazo r WHERE r.estado = 'A' AND r.idreemplazado = :idUsuario AND r.idproceso = f.proceso.idproceso AND CURRENT_DATE > r.fechainicialreemplazo and CURRENT_DATE <r.fechafinalreemplazo) = 0 ");
		if(recibidos){
			sql.append("AND (f.autor.idusuario != :idUsuario OR f.numeroTrazabilidad != 1 )");
		}else{
			sql.append("AND f.autor.idusuario = :idUsuario AND f.numeroTrazabilidad = 1 ");
		}
		sql.append(" AND ( :fechaDia >= f.fechaaccion ) ");

				//1
				if(!StringUtils.isEmpty(objFiltro.getNumeroExpediente())){
					sql.append("AND LOWER(f.expediente) LIKE :nroExpediente ");
				}
				//2
				if(!StringUtils.isEmpty(objFiltro.getNumeroDocumento())){
					sql.append("AND LOWER(f.documento) LIKE :nroDocumento ");
				}
				//3
				/*if(!StringUtils.isEmpty(objFiltro.getTipoDocumento())){
					sql.append("AND f.idtipodocumento = :idTipoDocumento ");
				}*/
				//4
				if(!StringUtils.isEmpty(objFiltro.getCliente())){
					sql.append("AND f.cliente = :idCliente ");
				}
				//5
				if(!StringUtils.isEmpty(objFiltro.getAsuntodocumento())){
					sql.append("AND LOWER(f.asunto) LIKE :asuntoDocumento ");
				}
				//6
				if(!StringUtils.isEmpty(objFiltro.getAsuntoexpediente())){
					sql.append("AND LOWER(f.asuntoExpediente) LIKE :asuntoExpediente ");
				}
				//7
				if(!StringUtils.isEmpty(objFiltro.getFechaDesde())){
					sql.append("AND f.fechaaccion >= :fechaDesde ");
				}
				//8
				if(!StringUtils.isEmpty(objFiltro.getFechaHasta())){
					sql.append("AND f.fechaaccion <= :fechaHasta ");
				}
				//9
				if(!StringUtils.isEmpty(objFiltro.getPrioridad())){
					if(objFiltro.getPrioridad().equals("-1")){
					log.debug("prioridad TODOS");
					}else{
						log.debug("prioridad DISTINTO TODOS");
						sql.append("AND LOWER(f.prioridad) LIKE :prioridad ");
					}
				}
		sql.append("ORDER BY f.expediente, f.fechaaccion DESC ");

		// ******************************************************************************************
		// BUSCANDO MIS DOCUMENTOS CON REEMPLAZOS
		// ******************************************************************************************
		sql.append("UNION ");
		sql.append(sqlMaster.toString());

		sql.append("WHERE f.propietario.idusuario = :idUsuario AND chrestado = :chrEstado and f.despachado = 'N' ");
		sql.append("AND f.propietario.idusuario IN ELEMENTS(SELECT r.idreemplazado FROM Reemplazo r WHERE WHERE r.estado = 'A' AND r.idusuario = :idUsuario AND r.idproceso = f.proceso.idproceso AND CURRENT_DATE > r.fechainicialreemplazo and CURRENT_DATE <r.fechafinalreemplazo )");
		if(recibidos){
			sql.append("AND (f.autor.idusuario != :idUsuario OR f.numeroTrazabilidad != 1) ");
		}else{
			sql.append("AND f.autor.idusuario = :idUsuario AND f.numeroTrazabilidad = 1 ");
		}
		sql.append(" AND ( :fechaDia >= f.fechaaccion ) ");//--

				//1
				if(!StringUtils.isEmpty(objFiltro.getNumeroExpediente())){
					sql.append("AND LOWER(f.expediente) LIKE :nroExpediente ");
				}
				//2
				if(!StringUtils.isEmpty(objFiltro.getNumeroDocumento())){
					sql.append("AND LOWER(f.documento) LIKE :nroDocumento ");
				}
				//3
				/*if(!StringUtils.isEmpty(objFiltro.getTipoDocumento())){
					sql.append("AND e.trazabilidaddocumento.documento.tipoDocumento.idtipodocumento = :idTipoDocumento ");
				}*/
				//4
				if(!StringUtils.isEmpty(objFiltro.getCliente())){
					sql.append("AND f.cliente = :idCliente ");
				}
				//5
				if(!StringUtils.isEmpty(objFiltro.getAsuntodocumento())){
					sql.append("AND LOWER(f.asunto) LIKE :asuntoDocumento ");
				}
				//6
				if(!StringUtils.isEmpty(objFiltro.getAsuntoexpediente())){
					sql.append("AND LOWER(f.asuntoExpediente) LIKE :asuntoExpediente ");
				}
				//7
				if(!StringUtils.isEmpty(objFiltro.getFechaDesde())){
					sql.append("AND f.fechaaccion >= :fechaDesde ");
				}
				//8
				if(!StringUtils.isEmpty(objFiltro.getFechaHasta())){
					sql.append("AND f.fechaaccion <= :fechaHasta ");
				}
				//9
				if(!StringUtils.isEmpty(objFiltro.getPrioridad())){
					if(objFiltro.getPrioridad().equals("-1")){
						log.debug("prioridad TODOS");
					}else{
						log.debug("prioridad DISTINTO TODOS");
						sql.append("AND LOWER(f.prioridad) LIKE :prioridad ");
					}
				}
		sql.append("ORDER BY f.expediente, f.fechaaccion DESC ");

		Query q = em.createQuery(sql.toString());
		q.setParameter("idUsuario", iIdUsuario);
		q.setParameter("chrEstado", Constantes.ESTADO_ACTIVO);

				//1
				if(!StringUtils.isEmpty(objFiltro.getNumeroExpediente())){
					q.setParameter("nroExpediente", "%"+objFiltro.getNumeroExpediente().toLowerCase()+"%");
				}
				//2
				if(!StringUtils.isEmpty(objFiltro.getNumeroDocumento())){
					q.setParameter("nroDocumento", "%"+objFiltro.getNumeroDocumento().toLowerCase()+"%");
				}
				//3
				if(!StringUtils.isEmpty(objFiltro.getTipoDocumento())){
					//q.setParameter("idTipoDocumento", Integer.parseInt(objFiltro.getTipoDocumento()));
				}
				//4
				if(!StringUtils.isEmpty(objFiltro.getCliente())){
					System.out.println("VER idCliente"+ objFiltro.getCliente());
					q.setParameter("idCliente", Integer.parseInt(objFiltro.getCliente()));
				}
				//5
				if(!StringUtils.isEmpty(objFiltro.getAsuntodocumento())){
					q.setParameter("asuntoDocumento", "%"+objFiltro.getAsuntodocumento().toLowerCase()+"%");
				}
				//6
				if(!StringUtils.isEmpty(objFiltro.getAsuntoexpediente())){
					q.setParameter("asuntoExpediente", "%"+objFiltro.getAsuntoexpediente().toLowerCase()+"%");
				}
				//7
				if(!StringUtils.isEmpty(objFiltro.getFechaDesde())){
					SimpleDateFormat fechita = new SimpleDateFormat("dd/MM/yyyy");
					Calendar fecha = Calendar.getInstance();
					try {
						fecha.setTime(fechita.parse(objFiltro.getFechaDesde()));
						fecha.set(Calendar.HOUR_OF_DAY, 0);
						fecha.set(Calendar.MINUTE, 0);
						fecha.set(Calendar.SECOND, 0);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					q.setParameter("fechaDesde", fecha.getTime());
				}
				//8
				if(!StringUtils.isEmpty(objFiltro.getFechaHasta())){
					SimpleDateFormat fechita = new SimpleDateFormat("dd/MM/yyyy");
					Calendar fecha = Calendar.getInstance();
					try {
						fecha.setTime(fechita.parse(objFiltro.getFechaHasta()));
						fecha.set(Calendar.HOUR_OF_DAY, 23);
						fecha.set(Calendar.MINUTE, 59);
						fecha.set(Calendar.SECOND, 59);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					q.setParameter("fechaHasta", fecha.getTime());
				}
				//9
				if(!StringUtils.isEmpty(objFiltro.getPrioridad())){
					if(objFiltro.getPrioridad().equals("-1")){
						log.debug("prioridad PARAMETRO TODOS");
					}else{
						q.setParameter("prioridad", objFiltro.getPrioridad());
						log.debug("prioridad PARAMETRO DISTINTO TODOS");
					}
				}
				//
		Calendar fechaDia = Calendar.getInstance();
		fechaDia.set(Calendar.HOUR_OF_DAY, 23);
		fechaDia.set(Calendar.MINUTE, 59);
		fechaDia.set(Calendar.SECOND, 59);
		q.setParameter("fechaDia", fechaDia.getTime());

		try {
			List<FilaBandejaUF> temp = q.getResultList();
			if(temp != null && !temp.isEmpty()){
				if(recibidos){
					if(usuario.getBandejaAgrupada() != null && usuario.getBandejaAgrupada().equals(Constantes.Si)){
						Set<String> expedientesLista = new HashSet<String>();
						Map<String, Integer> mapCantidades = new HashMap<String, Integer>();
						Map<String, Boolean> mapLeidos = new HashMap<String, Boolean>();

						/**Rutina para el primer documento --------------------------------------------------------------------------------------*/
						FilaBandejaUF fila = temp.get(0);

						expedientesLista.add(fila.getExpediente());
						mapCantidades.put(fila.getExpediente(), 1);
						mapLeidos.put(fila.getExpediente(), fila.getLeido().equals(Constantes.No.toString()));

						data.add(llenarItemUF(fila));

						/**Rutina para el resto de documentos------------------------------------------------------------------------------------*/
						for (int i = 1; i < temp.size(); i++) {
							fila = temp.get(i);

							if(expedientesLista.contains(fila.getExpediente())){
								mapCantidades.put(fila.getExpediente(), mapCantidades.get(fila.getExpediente())+1);
								mapLeidos.put(fila.getExpediente(), mapLeidos.get(fila.getExpediente()) || fila.getLeido().equals(Constantes.No.toString()));
							}else{
								expedientesLista.add(fila.getExpediente());
								mapCantidades.put(fila.getExpediente(), 1);
								mapLeidos.put(fila.getExpediente(), fila.getLeido().equals(Constantes.No.toString()));

								ItemUF uuf = llenarItemUF(fila);
								data.add(uuf);
							}
						}

						for(Object f : data){
							ItemUF item = (ItemUF)f;
							item.setExpBtn(item.getExpBtn() + "[" + mapCantidades.get(item.getExpediente()) + "]" + mapLeidos.get(item.getExpediente()));
						}
					}else{
						for (int i = 0; i < temp.size(); i++) {
							FilaBandejaUF fila = temp.get(i);
							ItemUF item = llenarItemUF(fila);
							item.setExpBtn("-");
							data.add(item);

						}
					}
				}else{
					for (int i = 0; i < temp.size(); i++) {
						FilaBandejaUF fila = temp.get(i);
						data.add(llenarItemUF(fila));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("ERROR: no tiene data", e.fillInStackTrace());
		}

		return data;
	}

	public ParametroService getParametroService() {
		return parametroService;
	}

	public void setParametroService(ParametroService parametroService) {
		this.parametroService = parametroService;
	}

        ////////////CAMBIO PARA RESULTADO TRIBUTARIO///////////////////////////////////////////////
        public List<Documento> getDocumentoByTipoByUnidad(String tipodocumento, String unidadautor, Integer expediente) {
                               log.debug("-> [DAO] DocumentoDAO - getDocumentoByTipoByUnidad():List<Documento> ");
                
                String sql = "select d.iddocumento, d.nrodocumento, t.descripcion, d.expediente, e.nroexpediente, e.asunto, e.observacion, e.serie"
                        + " from documento d " +
                        "inner join tipodocumento t on t.idtipodocumento = d.tipodocumento " +
                        "inner join expediente e on e.idexpediente = d.expediente " +
                        "where d.tipodocumento = "+tipodocumento+" and d.unidadautor = "+unidadautor+" and d.expediente = "+expediente+" order by d.iddocumento";
                               Query q = em.createNativeQuery(sql);
                
                List<Documento> listaDocuemnto = null;
                List<Object> res = (List<Object>) q.getResultList();
                if (res!=null && res.size()>0){
                    listaDocuemnto =  new ArrayList<Documento>();
                    for (Object obj : res) {
                                               Documento documento = new Documento();
                                               Object[] objectArray = (Object[]) obj;
                        if (objectArray[1]!=null)
                        {
                            if(validarDocumentoRequerimiento(objectArray[1].toString(), expediente))
                            {
                                documento.setIdDocumento(Integer.valueOf(objectArray[0].toString()));
                                String nroDocDes = "";
                                String nroExpe = "";
                                if (objectArray[2]!=null)
                                {
                                    nroDocDes = objectArray[2].toString();
                                    nroExpe = objectArray[2].toString();
                                }
                                else
                                {
                                    nroDocDes = " ";
                                    nroExpe = " ";
                                }
                                if (objectArray[1]!=null)
                                {
                                    nroDocDes = nroDocDes + " - " +objectArray[1].toString();
                                    nroExpe = nroExpe + " /-/ " +objectArray[1].toString();
                                }else
                                {
                                    nroDocDes = nroDocDes + " - " +" ";
                                    nroExpe = nroExpe + " /-/ " +" ";
                                }

                                documento.setNumeroDocumento(nroDocDes);

                                if (objectArray[3]!=null)
                                    nroExpe = nroExpe + " /-/ " +objectArray[3].toString();
                                else
                                    nroExpe = nroExpe + " /-/ " +" ";
                                
                                if (objectArray[4]!=null)
                                    nroExpe = nroExpe + " /-/ " +objectArray[4].toString();
                                else
                                    nroExpe = nroExpe + " /-/ " +" ";
                                
                                if (objectArray[5]!=null)
                                    nroExpe = nroExpe + " /-/ " +objectArray[5].toString();
                                else
                                    nroExpe = nroExpe + " /-/ " +" ";
                                
                                if (objectArray[6]!=null)
                                    nroExpe = nroExpe + " /-/ " +objectArray[6].toString();
                                else
                                    nroExpe = nroExpe + " /-/ " +" ";
                                
                                if (objectArray[7]!=null)
                                    nroExpe = nroExpe + " /-/ " +objectArray[7].toString();
                                else
                                    nroExpe = nroExpe + " /-/ " +" ";
                                
                                documento.setNroexpediente(nroExpe);

                                listaDocuemnto.add(documento);
                            }
                        }
                                               
                    }
                               }               
                               return listaDocuemnto;
                }
        
        private boolean validarDocumentoRequerimiento(String nrodocumento, Integer expediente){
            boolean estado = true;
            String sql = "select * from documento where nrodocumento='"+nrodocumento+"' "
                    + "and tipodocumento='"+parametroService.findByTipoUnico("TIPO_DOCUMENTO_RESULTADO_TRIBUTARIO").getValor()+"' "
                    + "and expediente = "+expediente;
            Query q = em.createNativeQuery(sql);
            if(q.getResultList()!=null && q.getResultList().size() > 0)    
                estado = false;
            return estado;
        }
        
}
