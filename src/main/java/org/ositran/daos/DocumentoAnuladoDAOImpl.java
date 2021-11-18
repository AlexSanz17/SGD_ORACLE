/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import javax.persistence.EntityManager;
import com.btg.ositran.siged.domain.Usuario;
import org.apache.commons.lang.StringUtils;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import com.btg.ositran.siged.domain.DocumentoAnulado;
import com.btg.ositran.siged.domain.FilaBandejaAnulado;

import java.util.List;
import org.ositran.dojo.BusquedaAvanzada;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
/**
 *
 * @author consultor_jti15
 */
public class DocumentoAnuladoDAOImpl implements DocumentoAnuladoDAO {
     private EntityManager em;
     
    @PersistenceContext(unitName="sigedPU")
    public void setEm(EntityManager em){
	this.em=em;
    }
    
      public DocumentoAnulado findByIdDocumentoAnulado(Integer idDocumentoAnulado){
            return (DocumentoAnulado)em.createNamedQuery("DocumentoAnulado.findByIdDocumentoAnulado")
				.setParameter("idDocumentoAnulado", idDocumentoAnulado).getSingleResult();
        }
         
    
    public void saveDocumento(DocumentoAnulado documentoAnulado){
            
		if(documentoAnulado.getIddocumentoanulado()==null){
                   em.persist(documentoAnulado); // Nuevo
                   em.flush();
                   em.refresh(documentoAnulado);
		}else{
	 	   em.merge(documentoAnulado); // Actualizacion
                   em.flush();
		}
	}
    
    public List<FilaBandejaAnulado> buscarAnuladosPorUsuario(Usuario objUsuario) {
            
                String sql = "SELECT e FROM FilaBandejaAnulado e WHERE e.usuario = :idUsuario and e.unidadPropietario = :idUnidadPropietario and e.cargoPropietario  =:idCargoPropietario and e.codEstado = 'A' " +
					 " ORDER BY e.fechaCreacion DESC";

               return em.createQuery(sql).setParameter("idUsuario", objUsuario.getIdUsuarioPerfil())
                                          .setParameter("idUnidadPropietario", objUsuario.getIdUnidadPerfil())
                                          .setParameter("idCargoPropietario", objUsuario.getIdFuncionPerfil())
					  .getResultList();
	}
        
        public List<FilaBandejaAnulado> buscarAnuladosPorUsuario(Usuario objUsuario, BusquedaAvanzada objFiltro) {
            
                String sql = "SELECT e FROM FilaBandejaAnulado e, Documento d  WHERE  e.idDocumento = d.idDocumento and  e.usuario = :idUsuario and e.unidadPropietario = :idUnidadPropietario and e.cargoPropietario  =:idCargoPropietario and e.codEstado = 'A' ";
			
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
                
                return (List<FilaBandejaAnulado>)q.getResultList();
	}
}
