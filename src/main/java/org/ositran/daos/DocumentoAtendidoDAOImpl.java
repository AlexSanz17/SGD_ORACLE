/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import com.btg.ositran.siged.domain.DocumentoAtendido;
import javax.persistence.EntityManager;
import com.btg.ositran.siged.domain.Usuario;
import com.btg.ositran.siged.domain.Documento;
import org.apache.commons.lang.StringUtils;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import com.btg.ositran.siged.domain.FilaBandejaAtendido;
import java.util.List;
import org.ositran.dojo.BusquedaAvanzada;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import org.ositran.utils.Constantes;
/**
 *
 * @author consultor_jti15
 */
public class DocumentoAtendidoDAOImpl implements DocumentoAtendidoDAO {
     private EntityManager em;
     
    @PersistenceContext(unitName="sigedPU")
    public void setEm(EntityManager em){
	this.em=em;
    }
    
      public DocumentoAtendido findByIdDocumentoAtendido(Integer idDocumentoAtendido){
            return (DocumentoAtendido)em.createNamedQuery("DocumentoAtendido.findByIdDocumentoAtendido")
				.setParameter("idDocumentoAtendido", idDocumentoAtendido).getSingleResult();
        }
         
    
    public void saveDocumento(DocumentoAtendido documentoAtendido){
            
		if(documentoAtendido.getIddocumentoatendido()==null){
                   em.persist(documentoAtendido); // Nuevo
                   em.flush();
                   em.refresh(documentoAtendido);
		}else{
	 	   em.merge(documentoAtendido); // Actualizacion
                   em.flush();
		}
	}
    
     public List<FilaBandejaAtendido> buscarAtendidosPorDocumento(Documento d){
         String sql = "SELECT e FROM FilaBandejaAtendido e WHERE e.idDocumento = :idDocumento and e.usuario = :idUsuario and e.unidadPropietario = :idUnidadPropietario and e.cargoPropietario  =:idCargoPropietario and e.codEstado = 'A' ";
					 
          return em.createQuery(sql).setParameter("idDocumento", d.getIdDocumento()).setParameter("idUsuario", d.getPropietario().getIdusuario())
                                          .setParameter("idUnidadPropietario", d.getUnidadpropietario())
                                          .setParameter("idCargoPropietario", d.getCargopropietario())
					  .getResultList();
     }
    
    public List<FilaBandejaAtendido> buscarAtendidosPorUsuario(Usuario objUsuario) {
                String sql = null;
                if (objUsuario.getIdRolPerfil().toString().equals("4")){
                   sql = "SELECT e FROM FilaBandejaAtendido e WHERE e.unidadPropietario = :idUnidadPropietario and e.codEstado = 'A' " +
					 " AND e.fechaCreacion >= :fechaDesde AND e.fechaCreacion <= :fechaHasta ORDER BY e.fechaCreacion DESC"; 
                }else{
                   sql = "SELECT e FROM FilaBandejaAtendido e WHERE e.usuario = :idUsuario and e.unidadPropietario = :idUnidadPropietario and e.cargoPropietario  =:idCargoPropietario and e.codEstado = 'A' " +
					 " AND e.fechaCreacion >= :fechaDesde AND e.fechaCreacion <= :fechaHasta ORDER BY e.fechaCreacion DESC";
                }
                
                Calendar fechaDesde = Calendar.getInstance();
		fechaDesde.setTimeInMillis(fechaDesde.getTimeInMillis() - 30*Constantes.MILISEGUNDOS_DIA);
		fechaDesde.set(Calendar.HOUR_OF_DAY, 0);
		fechaDesde.set(Calendar.MINUTE, 0);
		fechaDesde.set(Calendar.SECOND, 0);

		Calendar fechaHasta = Calendar.getInstance();
		fechaHasta.set(Calendar.HOUR_OF_DAY, 23);
		fechaHasta.set(Calendar.MINUTE, 59);
		fechaHasta.set(Calendar.SECOND, 59);
                
                if (objUsuario.getIdRolPerfil().toString().equals("4")){
                    return em.createQuery(sql)
                                          .setParameter("idUnidadPropietario", objUsuario.getIdUnidadPerfil())
                                          .setParameter("fechaDesde", fechaDesde.getTime())
					  .setParameter("fechaHasta", fechaHasta.getTime())
					  .getResultList();
                }else{
                    return em.createQuery(sql).setParameter("idUsuario", objUsuario.getIdUsuarioPerfil())
                                          .setParameter("idUnidadPropietario", objUsuario.getIdUnidadPerfil())
                                          .setParameter("idCargoPropietario", objUsuario.getIdFuncionPerfil())
                                          .setParameter("fechaDesde", fechaDesde.getTime())
					  .setParameter("fechaHasta", fechaHasta.getTime())
					  .getResultList();
                }
                
               // return null;
	}
        
        public List<FilaBandejaAtendido> buscarAtendidosPorUsuario(Usuario objUsuario, BusquedaAvanzada objFiltro) {
            
                String sql = null;
                
                if (objUsuario.getIdRolPerfil().toString().equals("4")){
                   sql = "SELECT e FROM FilaBandejaAtendido e, Documento d  WHERE  e.idDocumento = d.idDocumento and  e.unidadPropietario = :idUnidadPropietario and e.codEstado = 'A' ";
                }else{
                    sql = "SELECT e FROM FilaBandejaAtendido e, Documento d  WHERE  e.idDocumento = d.idDocumento and  e.usuario = :idUsuario and e.unidadPropietario = :idUnidadPropietario and e.cargoPropietario  =:idCargoPropietario and e.codEstado = 'A' ";
                }
                
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
                
                if(!StringUtils.isEmpty(objFiltro.getUsuarioRemitente())){
		   sql += " AND e.usuario = :idUsuario ";
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

                if (objUsuario.getIdRolPerfil().toString().equals("4")){
                     q.setParameter("idUnidadPropietario", objUsuario.getIdUnidadPerfil());
                }else{
                     q.setParameter("idUsuario", objUsuario.getIdUsuarioPerfil())
                                          .setParameter("idUnidadPropietario", objUsuario.getIdUnidadPerfil())
                                          .setParameter("idCargoPropietario", objUsuario.getIdFuncionPerfil());
                } 
                
                
                if(!StringUtils.isEmpty(objFiltro.getNumeroExpediente())){
			q.setParameter("nroExpediente", "%"+objFiltro.getNumeroExpediente().toLowerCase()+"%");
		}
		if(!StringUtils.isEmpty(objFiltro.getNumeroDocumento())){
			q.setParameter("nroDocumento", "%"+objFiltro.getNumeroDocumento().toLowerCase()+"%");
		}
		if(!StringUtils.isEmpty(objFiltro.getTipoDocumento())){
			q.setParameter("idTipoDocumento", Integer.parseInt(objFiltro.getTipoDocumento()));
		}
                
                if(!StringUtils.isEmpty(objFiltro.getUsuarioRemitente())){
			q.setParameter("idUsuario", Integer.parseInt(objFiltro.getUsuarioRemitente()));
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
                
                return (List<FilaBandejaAtendido>)q.getResultList();
	}
}
