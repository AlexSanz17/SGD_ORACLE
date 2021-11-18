/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.btg.ositran.siged.domain.Usuario;
import com.btg.ositran.siged.domain.DocumentoReferencia;
import java.math.BigDecimal;
import com.btg.ositran.siged.domain.Documento;

@Repository
public class DocumentoReferenciaDAOImpl implements DocumentoReferenciaDAO{
	private static Logger log = LoggerFactory.getLogger(DocumentoReferenciaDAOImpl.class);
	private EntityManager em;

    
	public DocumentoReferencia saveDocumentoReferencia(DocumentoReferencia objDocumento){
            if (objDocumento.getIdRef()==null){
     	       em.persist(objDocumento);
               em.flush();
               em.refresh(objDocumento);
            }else{
               em.merge(objDocumento);
               em.flush();
            }   
	   return objDocumento;
	}

	public DocumentoReferencia updateDocumentoReferencia(DocumentoReferencia objDocumento){
	   em.merge(objDocumento);
	   return objDocumento;
    }

	@PersistenceContext(unitName = "sigedPU")
	public void setEm(EntityManager em) {
  	   this.em = em;
	}

	public EntityManager getEm() {
	    return em;
	}
        
        //********************************* ATENDER - BANDEJA PENDIENTES/RECIBIDOS *************************************
        public List<DocumentoReferencia> getDocumentoAtenderRespuesta(Integer idDocumento){
            log.debug("DocumentoReferenciaDAOImpl::getDocumentoAtenderRespuesta.");
            
            //JC MEJORA
            //String sql = "SELECT f FROM DocumentoReferencia f WHERE f.idDocumentoReferencia =" + idDocumento + " and f.documento.estado not in ('I','N') and f.estado = 'A'";   
            String sql = "SELECT f FROM DocumentoReferencia f WHERE f.idDocumentoReferencia =" + idDocumento + " and f.documento.estado not in ('I','N') and f.estado = 'A' and f.respondido is null and exists (select 1 from Documento d where d.idDocumento = f.idDocumento and d.bandeja = f.idDocumentoReferencia)"; //JCULTIMO   
            
            return em.createQuery(sql).getResultList();  
	} 
        
        //********************************* REFERENCIAS EN LA OPCION CARGAR DERIVAR *************************************
        public List<DocumentoReferencia> getDocumentoDerivarAtender(Usuario usuario, Integer idDocumento){
            log.debug("DocumentoReferenciaDAOImpl::getDocumentoDerivarAtender");
            String sql = "SELECT f FROM DocumentoReferencia f, Documento p WHERE f.idDocumento =" + idDocumento + " and f.documento.estado not in ('I','N') and f.estado = 'A' and f.respondido is null and f.idDocumentoReferencia = p.idDocumento and p.estado not in ('I','N')  and p.propietario.idusuario=" + usuario.getIdUsuarioPerfil() + " and p.unidadpropietario = " + usuario.getIdUnidadPerfil() + " and p.cargopropietario = " + usuario.getIdFuncionPerfil() + " and p.flagMultiple is null and p.flagatendido is null " ; //jc check   
            return em.createQuery(sql).getResultList();  
	}

         //********************************** PESTAÑA RESPUESTA ****************************
        /*public List<DocumentoReferencia> getDocumentoRespuestaSimple(Documento d){
            log.debug("DocumentoReferenciaDAOImpl::getDocumentoRespuestaSimple");
            String sql = "SELECT f FROM DocumentoReferencia f WHERE f.idDocumentoReferencia =" + d.getIdDocumento() + " and f.documento.estado not in ('I','N') and f.estado = 'A' and f.respondido = 'R'";     
            return em.createQuery(sql).getResultList();
	}
        
        public List<DocumentoReferencia> getDocumentoRespuestaMultiple(Documento d){
            log.debug("DocumentoReferenciaDAOImpl::getDocumentoRespuestaMultiple");
            String sql = "SELECT f FROM DocumentoReferencia f WHERE f.idDocumentoReferencia in (select m.iddocumento from documento m where m.documentoreferencia=" + d.getIdDocumento() + ") and f.documento.estado not in ('I','N') and f.estado = 'A' and f.respondido = 'R'";
            sql = sql + " union ";
            sql = sql + " SELECT f FROM DocumentoReferencia f WHERE f.idDocumentoReferencia =" + d.getIdDocumento() + " and f.documento.estado not in ('I','N') and f.estado = 'A' and f.respondido = 'R'";
	    
            return em.createQuery(sql).getResultList();
        }*/
        
        public List<DocumentoReferencia> getDocumentoRespuestaSimple(Documento d){
            log.debug("DocumentoReferenciaDAOImpl::getDocumentoRespuestaSimple");
            String sql = "SELECT f FROM DocumentoReferencia f WHERE f.idDocumentoReferencia =" + d.getIdDocumento() + " and f.documento.estado not in ('I','N') and f.estado = 'A'";     
            return em.createQuery(sql).getResultList();
	}
        
        public List<DocumentoReferencia> getDocumentoRespuestaMultiple(Documento d){
            log.debug("DocumentoReferenciaDAOImpl::getDocumentoRespuestaMultiple");
            String sql = "SELECT f FROM DocumentoReferencia f WHERE f.idDocumentoReferencia in (select m.idDocumento from Documento m where m.documentoreferencia=" + d.getIdDocumento() + ") and f.documento.estado not in ('I','N') and f.estado = 'A' ";
            sql = sql + " union ";
            sql = sql + " SELECT f FROM DocumentoReferencia f WHERE f.idDocumentoReferencia =" + d.getIdDocumento() + " and f.documento.estado not in ('I','N') and f.estado = 'A' ";
	    
            return em.createQuery(sql).getResultList();
        }
        
        
        //********************************** PESTAÑA REFERENCIAS ****************************
       
        public List<DocumentoReferencia> getReferenciaDocumento(Integer idDocumento){
            log.debug("DocumentoReferenciaDAOImpl::getReferenciaDocumento-idDocumento");            
	    String sql = "SELECT f FROM DocumentoReferencia f WHERE f.idDocumento =" + idDocumento + " and f.estado = 'A' and f.documentoReferencia.estado not in ('I','N')";
	    return em.createQuery(sql).getResultList();
	}
        
        public List<DocumentoReferencia> getAllReferenciaDocumento(Integer idDocumento){
            log.debug("DocumentoReferenciaDAOImpl::getAllReferenciaDocumento-idDocumento");
	    String sql = "SELECT f FROM DocumentoReferencia f WHERE f.idDocumento =" + idDocumento;
	    return em.createQuery(sql).getResultList();
	}
        
        //****************************** PARA ATENER DOCUMENTO AL PROCESAR DERIVAR ****************************************
        public DocumentoReferencia getReferenciaDocumento(Integer idDocumento, Integer idReferencia){
            log.debug("DocumentoReferenciaDAOImpl::getReferenciaDocumento-idDocumento-idReferencia");
            String sql = "SELECT f FROM DocumentoReferencia f WHERE f.idDocumento =" + idDocumento + " and f.idDocumentoReferencia="  + idReferencia +   "  and f.documento.estado not in ('I','N') and f.estado = 'A'";
	    return (DocumentoReferencia)em.createQuery(sql).getSingleResult();
	}

        public String existsReferencias(Integer idDocumento){
            String sql = "SELECT count(1) FROM DocumentoReferencia f WHERE f.idDocumento =" + idDocumento + " and f.documento.estado not in ('I','N') and f.estado = 'A'";
            Integer cantidad = ((BigDecimal)em.createNativeQuery(sql).getSingleResult()).intValue();
            return cantidad.toString();
        } 
}
