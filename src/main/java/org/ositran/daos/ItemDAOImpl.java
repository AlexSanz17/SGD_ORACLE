/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.axis.utils.StringUtils;
import org.ositran.dojo.BusquedaAvanzada;
import org.ositran.dojo.grid.Item;
import org.ositran.utils.Constantes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import com.btg.ositran.siged.domain.Usuario;

@Repository
public class ItemDAOImpl implements ItemDAO {

	private static Logger log = LoggerFactory.getLogger(ItemDAOImpl.class);
	private EntityManager em;

	/*
	 * Methods
	 */
	@SuppressWarnings("unchecked")
	public List<Item> findLstExpedienteBy(Integer iIdUsuario) {
               log.debug("-> [DAO] ItemDAO - findLstExpedienteBy():List<Item> ");

		String sQuery = "SELECT NEW org.ositran.dojo.grid.Item(" +
		"e.id," +
		"e.nroexpediente," +
		"e.fechacreacion," +
		"e.asunto," +
		"p.nombre," +
		"ti.nombre," +
		"e.clienterazonsocial," +
		"e.clientenombres," +
		"e.clienteapellidopaterno," +
		"e.clienteapellidomaterno," +
		"cl.razonSocial," +
		"cl.nombres," +
		"cl.apellidoPaterno," +
		"cl.apellidoMaterno," +
		"d.fechaLimiteAtencion, " +
		"e.estado, " +
		"u4.nombres, " +
		"u4.apellidos, " +
		"u1.nombres, " +
		"u1.apellidos " +
		") ";

		sQuery += "FROM Expediente e ";
		sQuery += "LEFT JOIN e.documentoList d ";
		sQuery += "LEFT JOIN d.propietario u4 ";
		sQuery += "LEFT JOIN e.idpropietario u1 ";
		sQuery += "LEFT JOIN e.proceso p ";
		sQuery += "LEFT JOIN p.responsable u2 ";
		sQuery += "LEFT JOIN p.idasistente u3 ";
		sQuery += "LEFT JOIN e.cliente cl ";
		sQuery += "LEFT JOIN cl.tipoIdentificacion ti ";
		sQuery += "WHERE (u1.idusuario = :idresponsableexpediente OR u2.idusuario = :idresponsableproceso OR u3.idusuario = :idasistente OR u4.idusuario = :idpropietario) ";
		sQuery += "AND d.principal = :principal AND e.id = d.expediente.id ";
		sQuery += "ORDER BY e.fechacreacion DESC";

		Query qQuery = em.createQuery(sQuery)
		.setParameter("idresponsableexpediente", iIdUsuario)
		.setParameter("idresponsableproceso", iIdUsuario)
		.setParameter("idasistente", iIdUsuario)
		.setParameter("idpropietario", iIdUsuario)
		.setParameter("principal", Constantes.DOCUMENTO_PRINCIPAL);

		return qQuery.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Item> findLstInformativoBy(Usuario objUsuario, Character cEstado) {
		log.debug("-> [DAO] ItemDAO - findLstInformativoBy():List<Item> ");
		
                String sQuery = "SELECT NEW org.ositran.dojo.grid.Item(" +
		"n.idnotificacion," +
		"n.leido," +
		"n.asunto," +
		"n.fechanotificacion," +
		"n.tiponotificacion," +
		"d.idDocumento," +
		"''," +
		"e.nroexpediente," +
		"''," +
		"ti.nombre," +
		"cl.razonSocial," +
		"cl.nombres," +
		"cl.apellidoPaterno," +
		"cl.apellidoMaterno," +
		"td.nombre," +
		"concat(td.nombre, ' ' || d.numeroDocumento)" +
                ",d.estado," +
                "e.asunto," +
                "cl.codtipoinstitucion, d.ID_CODIGO, d.origen" +        
		") ";
                
		sQuery += "FROM Notificacion n ";
		sQuery += "LEFT JOIN n.iddocumento d ";
		sQuery += "LEFT JOIN d.tipoDocumento td ";
		sQuery += "LEFT JOIN n.idusuario u ";
		sQuery += "LEFT JOIN d.expediente e ";
		sQuery += "LEFT JOIN d.cliente cl ";
		sQuery += "LEFT JOIN cl.tipoIdentificacion ti ";
		sQuery += "WHERE u.idusuario = :idpropietario AND n.unidadPropietario = :idunidadpropietario AND n.cargoPropietario= :idcargopropietario AND n.tiponotificacion IN (:informativo1 , :informativo2 , :informativo3 , :informativo4 , :informativo5 , :informativo6 , :informativo7,  :informativo8 ) AND n.estado = :estado AND CURRENT_DATE>=n.fechanotificacion ";
		sQuery += "AND n.fechanotificacion >= :fechaInicio ";
		sQuery += "ORDER BY n.fechanotificacion DESC";
		
                Calendar inicioDia = Calendar.getInstance();
		inicioDia.setTimeInMillis(inicioDia.getTimeInMillis() - 30 * Constantes.MILISEGUNDOS_DIA);
		inicioDia.set(Calendar.HOUR_OF_DAY, 0);
		inicioDia.set(Calendar.MINUTE, 0);
		inicioDia.set(Calendar.SECOND, 0);
		
		Query qQuery = em.createQuery(sQuery)
		.setParameter("idpropietario", objUsuario.getIdUsuarioPerfil())
                .setParameter("idunidadpropietario", objUsuario.getIdUnidadPerfil())
                .setParameter("idcargopropietario", objUsuario.getIdFuncionPerfil())        
		.setParameter("fechaInicio", inicioDia.getTime())
		.setParameter("informativo1", Constantes.TIPO_NOTIFICACION_DERIVACION)
		.setParameter("informativo2", Constantes.TIPO_NOTIFICACION_NUMERACION_DESTINATARIO)
		.setParameter("informativo3", Constantes.TIPO_NOTIFICACION_NUMERACION_DOCUMENTOCONCOPIA)
		.setParameter("informativo4", Constantes.TIPO_NOTIFICACION_DERIVACIONCONCOPIA)
		.setParameter("informativo5", Constantes.TIPO_NOTIFICACION_APROBACION_QAS)
		.setParameter("informativo6", Constantes.TIPO_NOTIFICACION_DUENO_EXPEDIENTE)
		.setParameter("informativo7", Constantes.TIPO_NOTIFICACION_FIN_APOYO)
                .setParameter("informativo8", Constantes.TIPO_NOTIFICACION_DERIVACIONMULTIPLECONCOPIA)        
		.setParameter("estado", cEstado);
                 List<Item> l= qQuery.getResultList();
                 return l;
                
                
	}

	@SuppressWarnings("unchecked")
	public List<Item> findLstInformativoBy(Usuario objUsuario, BusquedaAvanzada objFiltro){
            
            	String sql = "SELECT NEW org.ositran.dojo.grid.Item(" +
					 	"n.idnotificacion," +
					 	"n.leido," +
					 	"n.asunto," +
					 	"n.fechanotificacion," +
					 	"n.tiponotificacion," +
					 	"n.iddocumento.idDocumento," +
					 	"''," +//"n.iddocumento.expediente.cliente.razonSocial," +
					 	"n.iddocumento.expediente.nroexpediente," +
					 	//"n.iddocumento.expediente.proceso.nombre," +
					 	"''," +
                                                "''," +
					 	"cl.razonSocial," +
					 	"cl.nombres," +
					 	"cl.apellidoPaterno," +
					 	"cl.apellidoMaterno," +
					 	"d.tipoDocumento.nombre," +
					 	"d.tipoDocumento.nombre || ' ' ||  n.iddocumento.numeroDocumento" +
                                                ",d.estado," +
                                                "d.asunto," +
                                                "cl.codtipoinstitucion, d.ID_CODIGO, d.origen" + 
					 ") FROM Notificacion n " + 
                                          " LEFT JOIN n.iddocumento d " +
                                          " LEFT JOIN d.cliente cl " +
                                         " WHERE n.idusuario.idusuario = :idpropietario AND n.unidadPropietario = :idunidadpropietario AND n.cargoPropietario= :idcargopropietario and n.tiponotificacion IN (:informativo1 , :informativo2 , :informativo3 , :informativo4 , :informativo5 , :informativo6 , :informativo7, :informativo8 ) AND n.estado = :estado AND CURRENT_DATE>n.fechanotificacion ";
		
		//1
		if(!StringUtils.isEmpty(objFiltro.getNumeroExpediente())){
			sql += "AND LOWER(n.iddocumento.expediente.nroexpediente) LIKE :nroExpediente ";
		}
		//2
		if(!StringUtils.isEmpty(objFiltro.getNumeroDocumento())){
			sql += "AND LOWER(n.iddocumento.numeroDocumento) LIKE :nroDocumento ";
		}
		//3
		if(!StringUtils.isEmpty(objFiltro.getTipoDocumento())){
			sql += "AND n.iddocumento.tipoDocumento.idtipodocumento = :idTipoDocumento ";
		}
		//4
		if(!StringUtils.isEmpty(objFiltro.getCliente())){
			sql += "AND n.iddocumento.expediente.cliente.idCliente = :idCliente ";
		}
		//5
		if(!StringUtils.isEmpty(objFiltro.getAsuntodocumento())){
			sql += "AND LOWER(n.iddocumento.asunto) LIKE :asuntoDocumento ";
		}
		//6
		if(!StringUtils.isEmpty(objFiltro.getAsuntoexpediente())){
			sql += "AND LOWER(n.iddocumento.expediente.asunto) LIKE :asuntoExpediente ";
		}
                
            	//7
		if(!StringUtils.isEmpty(objFiltro.getFechaDesde())){
			sql += "AND n.fechanotificacion >= :fechaDesde ";
		}
		//8
		if(!StringUtils.isEmpty(objFiltro.getFechaHasta())){
			sql += "AND n.fechanotificacion <= :fechaHasta ";
		}
		//9
		if(!StringUtils.isEmpty(objFiltro.getAreaOrigen())){
			sql += "AND n.trazabilidadcopia.remitente.unidad.idunidad = :idUnidad ";
		}
		//10
		if(!StringUtils.isEmpty(objFiltro.getUsuarioRemitente())){
			sql += "AND n.trazabilidadcopia.remitente.idusuario = :idRemitente ";
		}
                
                if(!StringUtils.isEmpty(objFiltro.getNroHT())){
			sql += "AND d.ID_CODIGO = :nroTramite ";
		}
		
		sql += "ORDER BY n.fechanotificacion DESC ";
	
                Query q = em.createQuery(sql)
					.setParameter("idpropietario", objUsuario.getIdUsuarioPerfil())
                                        .setParameter("idunidadpropietario", objUsuario.getIdUnidadPerfil())
                                        .setParameter("idcargopropietario", objUsuario.getIdFuncionPerfil())    
					.setParameter("informativo1", Constantes.TIPO_NOTIFICACION_DERIVACION)
					.setParameter("informativo2", Constantes.TIPO_NOTIFICACION_NUMERACION_DESTINATARIO)
					.setParameter("informativo3", Constantes.TIPO_NOTIFICACION_NUMERACION_DOCUMENTOCONCOPIA)
					.setParameter("informativo4", Constantes.TIPO_NOTIFICACION_DERIVACIONCONCOPIA)
					.setParameter("informativo5", Constantes.TIPO_NOTIFICACION_APROBACION_QAS)
					.setParameter("informativo6", Constantes.TIPO_NOTIFICACION_DUENO_EXPEDIENTE)
					.setParameter("informativo7", Constantes.TIPO_NOTIFICACION_FIN_APOYO)
                                        .setParameter("informativo8", Constantes.TIPO_NOTIFICACION_DERIVACIONMULTIPLECONCOPIA)
					.setParameter("estado", Constantes.ESTADO_ACTIVO);
		
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
			q.setParameter("nroTramite", Integer.parseInt(objFiltro.getNroHT()));
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
		if(!StringUtils.isEmpty(objFiltro.getAreaOrigen())){
			q.setParameter("idUnidad", Integer.parseInt(objFiltro.getAreaOrigen()));
		}
		//10
		if(!StringUtils.isEmpty(objFiltro.getUsuarioRemitente())){
			q.setParameter("idRemitente", Integer.parseInt(objFiltro.getUsuarioRemitente()));
		}
		
		return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Item> findLstNotificacionBy(Integer iIdPropietario, Character cEstado) {
		log.debug("-> [DAO] ItemDAO - findLstNotificacionBy():List<Item> ");
		String sQuery = "SELECT NEW org.ositran.dojo.grid.Item(" +
		"n.idnotificacion," +
		"n.leido," +
		"n.asunto," +
		"n.fechanotificacion," +
		"d.idDocumento," +
		"p.descripcion" +
		") ";

		sQuery += "FROM Notificacion n ";
		sQuery += "LEFT JOIN n.iddocumento d ";
		sQuery += "LEFT JOIN n.idusuario u, ";
		sQuery += "Parametro p ";
		sQuery += "WHERE p.valor = n.tiponotificacion AND p.tipo = :tiponotificacion AND u.idusuario = :idusuario AND n.tiponotificacion NOT IN (:informativo1, :informativo2, :informativo3, :informativo4) AND n.estado = :estado ";
		sQuery += "ORDER BY n.fechanotificacion DESC";

		Query qQuery = em.createQuery(sQuery)
		.setParameter("tiponotificacion", Constantes.PARAMETRO_TIPO_NOTIFICACION)
		.setParameter("idusuario", iIdPropietario)
		.setParameter("informativo1", Constantes.TIPO_NOTIFICACION_DERIVACION)
		.setParameter("informativo2", Constantes.TIPO_NOTIFICACION_NUMERACION_DESTINATARIO)
		.setParameter("informativo3", Constantes.TIPO_NOTIFICACION_NUMERACION_DOCUMENTOCONCOPIA)
		.setParameter("informativo4", Constantes.TIPO_NOTIFICACION_DERIVACIONCONCOPIA)
		.setParameter("estado", cEstado);

		return qQuery.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Item> findLstDocumentoBy(Integer iIdExpediente) {
		log.debug("-> [DAO] ItemDAO - findLstDocumentoBy():List<Item> ");
		
		String sQuery = "SELECT NEW org.ositran.dojo.grid.Item(" +
		"d.idDocumento," +
		"td.nombre," +
		"d.numeroDocumento" +
		") ";

		sQuery += "FROM Documento d ";
		sQuery += "LEFT JOIN d.expediente e ";
		sQuery += "LEFT JOIN d.tipoDocumento td ";
		sQuery += "WHERE e.id = :idexpediente AND d.documentoreferencia is NULL ";
		sQuery += "ORDER BY d.fechaCreacion DESC";

		Query qQuery = em.createQuery(sQuery)
		.setParameter("idexpediente", iIdExpediente);

		return qQuery.getResultList();
	}

	/*
	 * Getters & Setters
	 */
	public EntityManager getEm() {
		return em;
	}

	@PersistenceContext(unitName = "sigedPU")
	public void setEm(EntityManager em) {
		this.em = em;
	}
}
