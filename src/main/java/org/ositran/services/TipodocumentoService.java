package org.ositran.services;

import java.util.List;
import java.util.Map;

import com.btg.ositran.siged.domain.Tipodocumento;

public interface TipodocumentoService {

	public Map<Integer,String> getTipoDocumentoMap();
        
        public List<Tipodocumento> findByAllTipoDocumentoPIDE();

	public Tipodocumento buscarObjPor(String sCodigo);

	public Tipodocumento findByIdTipoDocumento(Integer iIdTipodocumento);

	public Tipodocumento findByNombre(String sNombre);

	List<Tipodocumento> findByNombreLike(String like);

	public Tipodocumento guardarObj(Tipodocumento objTipodocumentoOld, Tipodocumento objTipodocumentoNew, String sUsuarioSesion, String sTipoAuditoria);

	public List<Tipodocumento> findAll(Integer iWithoutStor);

	public List<Tipodocumento> findAllGrupoFedatario(Integer grupoPerfil, Integer grupoDocumento);

	public List<Tipodocumento> getTiposPlantilla();

        public List<Tipodocumento> findAllLstWithoutPlantilla(Boolean bIsEdit, Integer iIdTipoDocumento);
        
        public Tipodocumento findByIdTipoDocumentoPIDE(String iIdTipodocumentoPIDE);
}
