/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;
import com.btg.ositran.siged.domain.DocumentoReferencia;
import com.btg.ositran.siged.domain.Usuario;
import com.btg.ositran.siged.domain.Documento;

public interface DocumentoReferenciaDAO {
	  public DocumentoReferencia saveDocumentoReferencia(DocumentoReferencia objDocumento);
	  public DocumentoReferencia updateDocumentoReferencia(DocumentoReferencia objDocumento);
	  public List<DocumentoReferencia> getReferenciaDocumento(Integer idDocumento);
          public List<DocumentoReferencia> getAllReferenciaDocumento(Integer idDocumento);
          public List<DocumentoReferencia> getDocumentoRespuestaSimple(Documento documento);
          public List<DocumentoReferencia> getDocumentoRespuestaMultiple(Documento documento);
          public List<DocumentoReferencia> getDocumentoAtenderRespuesta(Integer idDocumento);
          public List<DocumentoReferencia> getDocumentoDerivarAtender(Usuario usuario, Integer idDocumento);
          public DocumentoReferencia getReferenciaDocumento(Integer idDocumento, Integer idReferencia);
         // public List<DocumentoReferencia> getReferencia(Integer idDocumento);
          public String existsReferencias(Integer idDocumento);
}
