package org.ositran.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;
import org.apache.log4j.Logger;
import org.ositran.daos.TipodocumentoDAO;
import org.ositran.utils.Constantes;
import org.springframework.transaction.annotation.Transactional;

import com.btg.ositran.siged.domain.Tipodocumento;

public class TipodocumentoServiceImpl implements TipodocumentoService {

   private static Logger _log = Logger.getLogger(TipodocumentoServiceImpl.class);
   private TipodocumentoDAO dao;
   private AuditoriaService srvAuditoria;

   //////////////////////////////////
   // Constructors                 //
   //////////////////////////////////
   public TipodocumentoServiceImpl(TipodocumentoDAO dao) {
      this.dao = dao;
   }

   //////////////////////////////////
   // Methods                      //
   //////////////////////////////////
   public Map<Integer,String> getTipoDocumentoMap() {
      Map<Integer,String> mapAux = new HashMap<Integer,String>();
      List<Tipodocumento> lstTD = getDao().findAll();

      for (Tipodocumento objTD : lstTD) {
         mapAux.put(objTD.getIdtipodocumento(), objTD.getNombre());
      }

      return mapAux;
   }

   public Tipodocumento buscarObjPor(String sCodigo) {
      if (sCodigo != null) {
         try {
            return dao.buscarObjPor(sCodigo);
         } catch (NoResultException nre) {
            _log.info("No se encontro Tipo de Documento con Codigo [" + sCodigo + "]");

            return null;
         }
      } else {
         return null;
      }
   }

   public Tipodocumento findByIdTipoDocumento(Integer iIdTipodocumento) {
      return dao.findByIdTipoDocumento(iIdTipodocumento);
   }
   
    public List<Tipodocumento> findByAllTipoDocumentoPIDE(){
       return dao.findByAllTipoDocumentoPIDE();
   }

   public Tipodocumento findByNombre(String sNombre) {
      try {
         return dao.findByNombre(sNombre);
      } catch (NoResultException nre) {
         _log.info("No se encontro Tipo de Documento [" + sNombre + "]");

         return null;
      }
   }

   public List<Tipodocumento> findByNombreLike(String like) {
	   return dao.findByNombreLike(like);
   }


	@Transactional
	public Tipodocumento guardarObj(Tipodocumento objTipodocumentoOld, Tipodocumento objTipodocumentoNew, String sUsuarioSesion, String sTipoAuditoria) {
      try {
         getSrvAuditoria().aplicarAuditoria(objTipodocumentoOld, objTipodocumentoNew, sUsuarioSesion, Constantes.AUDITORIA_OPCION_GUARDAR, sTipoAuditoria);
      } catch (ClassNotFoundException e) {
         _log.error(e.getMessage(), e);
      }

      objTipodocumentoNew = dao.guardarObj(objTipodocumentoNew);

      _log.debug("Tipo de Documento guardado con ID [" + objTipodocumentoNew.getIdtipodocumento() + "] Nombre [" + objTipodocumentoNew.getNombre() + "]");

      return objTipodocumentoNew;
   }
        

   public List<Tipodocumento> findAll(Integer iWithoutStor) {
      if (iWithoutStor == 0) {
         return dao.findAllActive();
      } else {
         return dao.findAll();
      }
   }

     public Tipodocumento findByIdTipoDocumentoPIDE(String iIdTipodocumentoPIDE){
         return dao.findByIdTipoDocumentoPIDE(iIdTipodocumentoPIDE);
     }
    
     public List<Tipodocumento> findAllGrupoFedatario(Integer grupoPerfil, Integer grupoDocumento){
	  return dao.findAllGrupoFedatario(grupoPerfil, grupoDocumento);
     }

   public List<Tipodocumento> findAllLstWithoutPlantilla(Boolean bIsEdit, Integer iIdTipoDocumento) {
      List<Tipodocumento> lstTipoDocumento = dao.findAllWithoutPlantilla();
      Tipodocumento objTipoDocumento = null;

      _log.debug("Se editara la plantilla? [" + bIsEdit + "]");

      if (bIsEdit && iIdTipoDocumento != null) {
         _log.debug("Se agregara a la lista, el tipo de Documento con ID [" + iIdTipoDocumento + "]");

         objTipoDocumento = this.findByIdTipoDocumento(iIdTipoDocumento);
         lstTipoDocumento.add(0, objTipoDocumento);
      }

      return lstTipoDocumento;
   }

   //////////////////////////////////
   // Getters and Setters          //
   //////////////////////////////////
   public TipodocumentoDAO getDao() {
      return dao;
   }

   public void setDao(TipodocumentoDAO dao) {
      this.dao = dao;
   }

   public AuditoriaService getSrvAuditoria() {
      return srvAuditoria;
   }

   public void setSrvAuditoria(AuditoriaService srvAuditoria) {
      this.srvAuditoria = srvAuditoria;
   }

	@Override
	public List<Tipodocumento> getTiposPlantilla(){
		return dao.findAllwithPlantilla();
	}
}
