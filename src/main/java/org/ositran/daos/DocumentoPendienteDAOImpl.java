/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;


import com.btg.ositran.siged.domain.DocumentoPendiente;
import com.btg.ositran.siged.domain.FilaBandejaPendientesRespuesta;
import com.btg.ositran.siged.domain.Usuario;
import com.btg.ositran.siged.domain.Documento;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.commons.lang.StringUtils;
import org.ositran.dojo.BusquedaAvanzada;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.text.ParseException;
/**
 *
 * @author consultor_jti15
 */
public class DocumentoPendienteDAOImpl implements  DocumentoPendienteDAO{
        private EntityManager em;
        
         public DocumentoPendiente findByIdDocumentoPendiente(Integer idDocumentoPendiente){
            return (DocumentoPendiente)em.createNamedQuery("DocumentoPendiente.findByIdDocumentoPendiente")
				.setParameter("idDocumentoPendiente", idDocumentoPendiente).getSingleResult();
        }
         
        public List<DocumentoPendiente> buscarPendientePorDocumentoSimple(Documento d){
            String sql = "";
           sql = "SELECT e FROM DocumentoPendiente e, Documento d WHERE d.idDocumento = " + d.getIdDocumento() +" and  d.idDocumento = e.iddocumento and d.estado not in ('I','N') AND e.estado = 'A' ";
       
            return em.createQuery(sql).getResultList();
        }
        
        public List<DocumentoPendiente> buscarPendientePorDocumentoMultiple(Documento d){
            String sql = "";
            sql = " SELECT e FROM DocumentoPendiente e, Documento d WHERE d.idDocumento = " + d.getIdDocumento() +" and  d.idDocumento = e.iddocumento and d.estado not in ('I','N') AND e.estado = 'A' ";
            sql = sql + " union ";
            sql = sql  + " SELECT e FROM DocumentoPendiente e, Documento d WHERE d.idDocumento in (select dd.idDocumento from Documento dd where dd.documentoreferencia=" + d.getIdDocumento() + ") and d.idDocumento = e.iddocumento and d.estado not in ('I','N') AND e.estado = 'A' ";
       
            return em.createQuery(sql).getResultList();
        }
         
	public List buscarPendientePorUsuario(Usuario objUsuario) {
            
               // String sql = "SELECT e FROM FilaBandejaPendientesRespuesta e WHERE e.usuario = :idUsuario and e.unidadPropietario = :idUnidadPropietario and e.cargoPropietario  =:idCargoPropietario and e.codEstado = 'A' and :fechaDia >= e.fechaAccion  " +
	       //				 " ORDER BY e.fechaCreacion DESC";

               String sql  = "SELECT f FROM FilaBandejaUF f WHERE f.propietario.idusuario = :idUsuario AND f.unidadpropietario = :idUnidadPropietario AND f.cargopropietario = :idCargoPropietario  AND chrestado IN ('A','P') AND f.flagatendido is null AND f.flagmultiple is null ";
               sql = sql + " AND f.firmado = 'N' AND (f.autor.idusuario != :idUsuario OR f.numeroTrazabilidad != 1 )";
                
               Calendar fechaDia = Calendar.getInstance(); 
               return em.createQuery(sql)//setParameter("idUsuario", objUsuario.getIdUsuarioPerfil())
                                          .setParameter("idUnidadPropietario", objUsuario.getIdUnidadPerfil())
                                         // .setParameter("idCargoPropietario", objUsuario.getIdFuncionPerfil())
                                          .setParameter("fechaDia", fechaDia.getTime())
					  .getResultList();
	}
        
        public List buscarPendientePorUsuario(Usuario objUsuario, BusquedaAvanzada objFiltro) {
                 return null;
                /*
                String sql = "SELECT e FROM FilaBandejaPendientesRespuesta e, Documento d  WHERE  e.idDocumento = d.idDocumento and  e.usuario = :idUsuario and e.unidadPropietario = :idUnidadPropietario and e.cargoPropietario  =:idCargoPropietario and e.codEstado = 'A' and :fechaDia >= e.fechaAccion  ";
			
                if(!StringUtils.isEmpty(objFiltro.getNumeroExpediente())){
		   sql += " AND LOWER(d.expediente.nroexpediente) LIKE :nroExpediente ";
		}
		//2
		if(!StringUtils.isEmpty(objFiltro.getNumeroDocumento())){
		   sql += " AND LOWER(d.numeroDocumento) LIKE :nroDocumento ";
		}
		//3
		if(!StringUtils.isEmpty(objFiltro.getTipoDocumento())){
		   sql += " AND d.tipoDocumento.idtipodocumento = :idTipoDocumento ";
		}
		//4
		if(!StringUtils.isEmpty(objFiltro.getCliente())){
		   sql += " AND d.cliente.idCliente = :idCliente ";
		}
		//5
		if(!StringUtils.isEmpty(objFiltro.getAsuntodocumento())){
		   sql += " AND LOWER(d.asunto) LIKE :asuntoDocumento ";
		}
		//6
		if(!StringUtils.isEmpty(objFiltro.getAsuntoexpediente())){
		   sql += " AND LOWER(d.expediente.asunto) LIKE :asuntoExpediente ";
		}		 
                
                if(!StringUtils.isEmpty(objFiltro.getNroHT())){
		   sql += " AND d.ID_CODIGO = :nroHT ";
		}
                
                if(!StringUtils.isEmpty(objFiltro.getFechaDesde())){
		   sql += "AND e.fechaCreacion >= :fechaDesde ";
		}
		if(!StringUtils.isEmpty(objFiltro.getFechaHasta())){
		   sql += " AND e.fechaCreacion <= :fechaHasta ";
		}
                        
                sql +=  " ORDER BY e.fechaCreacion DESC ";
                
                Query q = em.createQuery(sql);
                
                Calendar fechaDia = Calendar.getInstance(); 

                q.setParameter("idUsuario", objUsuario.getIdUsuarioPerfil())
                                          .setParameter("idUnidadPropietario", objUsuario.getIdUnidadPerfil())
                                          .setParameter("idCargoPropietario", objUsuario.getIdFuncionPerfil())
                                          .setParameter("fechaDia", fechaDia.getTime());
                 
                if(!StringUtils.isEmpty(objFiltro.getNumeroExpediente())){
			q.setParameter("nroExpediente", "%"+objFiltro.getNumeroExpediente().toLowerCase()+"%");
		}
		if(!StringUtils.isEmpty(objFiltro.getNumeroDocumento())){
			q.setParameter("nroDocumento", "%"+objFiltro.getNumeroDocumento().toLowerCase()+"%");
		}
		if(!StringUtils.isEmpty(objFiltro.getTipoDocumento())){
			q.setParameter("idTipoDocumento", Integer.parseInt(objFiltro.getTipoDocumento()));
		}
		if(!StringUtils.isEmpty(objFiltro.getCliente())){
			q.setParameter("idCliente", Integer.parseInt(objFiltro.getCliente()));
		}
		if(!StringUtils.isEmpty(objFiltro.getAsuntodocumento())){
			q.setParameter("asuntoDocumento", "%"+objFiltro.getAsuntodocumento().toLowerCase()+"%");
		}
		if(!StringUtils.isEmpty(objFiltro.getAsuntoexpediente())){
			q.setParameter("asuntoExpediente", "%"+objFiltro.getAsuntoexpediente().toLowerCase()+"%");
		}
                if(!StringUtils.isEmpty(objFiltro.getNroHT())){
			q.setParameter("nroHT", Integer.parseInt(objFiltro.getNroHT()));
		}
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
                
                return (List<FilaBandejaPendientesRespuesta>)q.getResultList();*/
	}
        /*
        public List<FilaBandejaPendientesRespuesta> buscarAtendidosPorUsuario(Usuario objUsuario) {
            
                String sql = "SELECT e FROM FilaBandejaPendientesRespuesta e WHERE e.usuario = :idUsuario and e.unidadPropietario = :idUnidadPropietario and e.cargoPropietario  =:idCargoPropietario and e.codEstado = 'T'  " +
					 " ORDER BY e.fechaCreacion DESC";

               return em.createQuery(sql).setParameter("idUsuario", objUsuario.getIdUsuarioPerfil())
                                          .setParameter("idUnidadPropietario", objUsuario.getIdUnidadPerfil())
                                          .setParameter("idCargoPropietario", objUsuario.getIdFuncionPerfil())
					  .getResultList();
	}
        
        public List<FilaBandejaPendientesRespuesta> buscarAtendidosPorUsuario(Usuario objUsuario,  BusquedaAvanzada objFiltro) {
            
                String sql = "SELECT e FROM FilaBandejaPendientesRespuesta e, Documento d WHERE e.idDocumento = d.idDocumento and e.usuario = :idUsuario and e.unidadPropietario = :idUnidadPropietario and e.cargoPropietario  =:idCargoPropietario and e.codEstado = 'T'  ";
					
                if(!StringUtils.isEmpty(objFiltro.getNumeroExpediente())){
		   sql += " AND LOWER(d.expediente.nroexpediente) LIKE :nroExpediente ";
		}
		//2
		if(!StringUtils.isEmpty(objFiltro.getNumeroDocumento())){
		   sql += " AND LOWER(d.numeroDocumento) LIKE :nroDocumento ";
		}
		//3
		if(!StringUtils.isEmpty(objFiltro.getTipoDocumento())){
		   sql += " AND d.tipoDocumento.idtipodocumento = :idTipoDocumento ";
		}
		//4
		if(!StringUtils.isEmpty(objFiltro.getCliente())){
		   sql += " AND d.cliente.idCliente = :idCliente ";
		}
		//5
		if(!StringUtils.isEmpty(objFiltro.getAsuntodocumento())){
		   sql += " AND LOWER(d.asunto) LIKE :asuntoDocumento ";
		}
		//6
		if(!StringUtils.isEmpty(objFiltro.getAsuntoexpediente())){
		   sql += " AND LOWER(d.expediente.asunto) LIKE :asuntoExpediente ";
		}		
                if(!StringUtils.isEmpty(objFiltro.getNroHT())){
		   sql += " AND d.ID_CODIGO = :nroHT ";
		}
                
                if(!StringUtils.isEmpty(objFiltro.getFechaDesde())){
		   sql += "AND e.fechaModificacion >= :fechaDesde ";
		}
		if(!StringUtils.isEmpty(objFiltro.getFechaHasta())){
		   sql += " AND e.fechaModificacion <= :fechaHasta ";
		}
                        
                sql +=  " ORDER BY e.fechaModificacion DESC ";
                
                Query q = em.createQuery(sql);        
                        
                q.setParameter("idUsuario", objUsuario.getIdUsuarioPerfil())
                 .setParameter("idUnidadPropietario", objUsuario.getIdUnidadPerfil())
                 .setParameter("idCargoPropietario", objUsuario.getIdFuncionPerfil());
                
                 if(!StringUtils.isEmpty(objFiltro.getNumeroExpediente())){
		   q.setParameter("nroExpediente", "%"+objFiltro.getNumeroExpediente().toLowerCase()+"%");
		}
		if(!StringUtils.isEmpty(objFiltro.getNumeroDocumento())){
		   q.setParameter("nroDocumento", "%"+objFiltro.getNumeroDocumento().toLowerCase()+"%");
		}
		if(!StringUtils.isEmpty(objFiltro.getTipoDocumento())){
		   q.setParameter("idTipoDocumento", Integer.parseInt(objFiltro.getTipoDocumento()));
		}
		if(!StringUtils.isEmpty(objFiltro.getCliente())){
		   q.setParameter("idCliente", Integer.parseInt(objFiltro.getCliente()));
		}
		if(!StringUtils.isEmpty(objFiltro.getAsuntodocumento())){
		   q.setParameter("asuntoDocumento", "%"+objFiltro.getAsuntodocumento().toLowerCase()+"%");
		}
		if(!StringUtils.isEmpty(objFiltro.getAsuntoexpediente())){
		   q.setParameter("asuntoExpediente", "%"+objFiltro.getAsuntoexpediente().toLowerCase()+"%");
		}
                if(!StringUtils.isEmpty(objFiltro.getNroHT())){
		   q.setParameter("nroHT", Integer.parseInt(objFiltro.getNroHT()));
		}
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
                
                return (List<FilaBandejaPendientesRespuesta>)q.getResultList();
	}
        */
        
        public void saveDocumento(DocumentoPendiente documentoPendiente){
            
		if(documentoPendiente.getIddocumentopendiente()==null){
                   em.persist(documentoPendiente); // Nuevo
                   em.flush();
                   em.refresh(documentoPendiente);
		}else{
	 	   em.merge(documentoPendiente); // Actualizacion
                   em.flush();
		}
	}
        
          public List<DocumentoPendiente> findByIdDocumento(Integer idDocumento, Usuario objUsuario, String estado){
               String sql = "SELECT e FROM DocumentoPendiente e WHERE e.iddocumento = :idDocumento and e.idusuario = :idUsuario and e.unidadpropietario = :idUnidadPropietario and e.cargopropietario  =:idCargoPropietario ";
		
               if (estado !=null){
                 sql = sql + " and estado = '" + estado + "'";
               }
               
               return em.createQuery(sql).setParameter("idDocumento", idDocumento)
                                         .setParameter("idUsuario", objUsuario.getIdUsuarioPerfil())
                                         .setParameter("idUnidadPropietario", objUsuario.getIdUnidadPerfil())
                                         .setParameter("idCargoPropietario", objUsuario.getIdFuncionPerfil())
					 .getResultList();
          }
        
        @PersistenceContext(unitName="sigedPU")
	public void setEm(EntityManager em){
		this.em=em;
	}
}
