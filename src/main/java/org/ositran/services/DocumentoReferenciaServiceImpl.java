/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.services;


import com.btg.ositran.siged.domain.DocumentoReferencia;
import com.btg.ositran.siged.domain.Usuario;
import com.btg.ositran.siged.domain.Documento;
import java.util.ArrayList;
import java.util.List;
import org.ositran.daos.DocumentoReferenciaDAO;
import org.springframework.transaction.annotation.Transactional;


public class DocumentoReferenciaServiceImpl implements DocumentoReferenciaService  {
	 private DocumentoReferenciaDAO documentoReferenciaDAO;

	 public DocumentoReferenciaDAO getDocumentoReferenciaDAO() {
		return documentoReferenciaDAO;
	}
         @Transactional
        public DocumentoReferencia saveDocumentoReferencia(DocumentoReferencia objDocumento){
             return documentoReferenciaDAO.saveDocumentoReferencia(objDocumento);
        } 

	public void setDocumentoReferenciaDAO(DocumentoReferenciaDAO documentoReferenciaDAO) {
		this.documentoReferenciaDAO = documentoReferenciaDAO;
	}
        
         public DocumentoReferencia getReferenciaDocumento(Integer idDocumento, Integer idReferencia){
             return documentoReferenciaDAO.getReferenciaDocumento(idDocumento, idReferencia);
         }
        
        public List<DocumentoReferencia> getDocumentoAtenderRespuesta(Integer idDocumento){
             return documentoReferenciaDAO.getDocumentoAtenderRespuesta(idDocumento);
        }
        
         public List<DocumentoReferencia> getDocumentoDerivarAtender(Usuario usuario, Integer idDocumento){
             return documentoReferenciaDAO.getDocumentoDerivarAtender(usuario, idDocumento);
         }

        
        public List<DocumentoReferencia> getDocumentoRespuestaSimple(Documento documento){
            return documentoReferenciaDAO.getDocumentoRespuestaSimple(documento);
        }
        
        public List<DocumentoReferencia> getDocumentoRespuestaMultiple(Documento documento){
            return documentoReferenciaDAO.getDocumentoRespuestaMultiple(documento);
        }
        
        public String existsReferencias(Integer idDocumento){
              return documentoReferenciaDAO.existsReferencias(idDocumento);
        }
   
}
