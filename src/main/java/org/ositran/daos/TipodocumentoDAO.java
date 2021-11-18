/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;

import com.btg.ositran.siged.domain.Tipodocumento;

public interface TipodocumentoDAO {

   public List<Tipodocumento> findAll();
   
    public List<Tipodocumento> findAllActive();
   
   public List<Tipodocumento> findAllGrupoFedatario(Integer grupoPerfil, Integer grupoDocumento);

   public List<Tipodocumento> findAllWithoutStor();

   public Tipodocumento buscarObjPor(String sCodigo);

   public Tipodocumento findByIdTipoDocumentoPIDE(String iIdTipodocumentoPIDE);
   
   public Tipodocumento findByIdTipoDocumento(Integer iIdTipodocumento);

   public Tipodocumento findByNombre(String sNombre);

   public List<Tipodocumento> findByNombreLike(String like);

   public Tipodocumento guardarObj(Tipodocumento objTipodocumento);

   public List<Tipodocumento> findAllwithPlantilla();

   public List<Tipodocumento> findAllWithoutPlantilla();
   
   public List<Tipodocumento> findByAllTipoDocumentoPIDE();
}
