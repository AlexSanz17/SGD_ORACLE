/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.services;

import com.btg.ositran.siged.domain.DocumentoReferencia;
import com.btg.ositran.siged.domain.Usuario;
import com.btg.ositran.siged.domain.Documento;
import java.util.List;

public interface DocumentoReferenciaService {
      public String existsReferencias(Integer idDocumento);
      public List<DocumentoReferencia> getDocumentoRespuestaSimple(Documento documento);  
      public List<DocumentoReferencia> getDocumentoRespuestaMultiple(Documento documento);   
      public List<DocumentoReferencia> getDocumentoAtenderRespuesta(Integer idDocumento); 
      public List<DocumentoReferencia> getDocumentoDerivarAtender(Usuario usuario, Integer idDocumento);
      public DocumentoReferencia getReferenciaDocumento(Integer idDocumento, Integer idReferencia);
      public DocumentoReferencia saveDocumentoReferencia(DocumentoReferencia objDocumento);
}
