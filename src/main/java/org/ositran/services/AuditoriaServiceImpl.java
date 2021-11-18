/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.services;

import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.ositran.daos.AuditoriaDAO;
import org.ositran.utils.AuditoriaUtil;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.btg.ositran.siged.domain.Auditable;
import com.btg.ositran.siged.domain.AuditableCampo;
import com.btg.ositran.siged.domain.Auditoria;

public class AuditoriaServiceImpl implements AuditoriaService {

   private static Logger _log = Logger.getLogger(AuditoriaServiceImpl.class);
   private AuditoriaDAO dao;

   //////////////////////////////////
   // Methods                      //
   //////////////////////////////////
   public void aplicarAuditoria(Auditable objOld, Auditable objNew, String sResponsable, String sOpcion, String sTipoAuditoria) throws ClassNotFoundException {
      _log.debug("Nombre de la clase a auditar [" + objOld.getClass().getSimpleName() + "]");

      for (AuditableCampo objCampoAuditable : objOld.getCamposAuditables()) {
         _log.debug("Campo [" + objCampoAuditable.getCampo() + "] Titulo [" + objCampoAuditable.getTitulo() + "]");

         Object oldValue = AuditoriaUtil.obtenerValor(objOld, objCampoAuditable.getCampo());
         Object newValue = AuditoriaUtil.obtenerValor(objNew, objCampoAuditable.getCampo());

         if (oldValue == null) {
            oldValue = new String("");
         }

         if (newValue == null) {
            newValue = new String("");
         }

         _log.debug("Old [" + oldValue + "] New [" + newValue + "]");

         if (!oldValue.equals(newValue)) {
            this.registrarAuditoria(objOld.getClass().getSimpleName(), sOpcion, objCampoAuditable.getTitulo(), oldValue.toString(), newValue.toString(), sResponsable, sTipoAuditoria);
         }
      }
   }

   @Transactional(propagation = Propagation.REQUIRED)
	public void registrarAuditoria(String sModulo, String sOpcion, String sCampo, String sOld, String sNew, String sUsuario, String sTipoAuditoria) {
      Auditoria objAuditoria = new Auditoria();

      objAuditoria.setTipoAuditoria(sTipoAuditoria);
      objAuditoria.setModulo(sModulo);
      objAuditoria.setOpcion(sOpcion);
      objAuditoria.setCampo(sCampo);
      objAuditoria.setAntiguoValor(sOld);
      objAuditoria.setNuevoValor(sNew);
      objAuditoria.setFechaAudioria(new Date());
      objAuditoria.setUsuario(sUsuario);



      if (sOld.length()>3450){
    	  objAuditoria.setAntiguoValor(sOld.substring(0,3450));
    	  objAuditoria.setAntiguoValor_(sOld.substring(3450,sOld.length()));
      }

      if (sNew.length()>3450){
    	  objAuditoria.setNuevoValor(sNew.substring(0, 3450));
    	  objAuditoria.setNuevoValor_(sNew.substring(3450, sNew.length()));
      }

      objAuditoria = this.SaveAuditoria(objAuditoria);

      _log.debug("** Registro de Auditoria **");
      _log.debug("ID [" + objAuditoria.getIdAuditoria() + "]");
      _log.debug("Tipo [" + objAuditoria.getTipoAuditoria() + "]");
      _log.debug("Modulo [" + objAuditoria.getModulo() + "]");
      _log.debug("Opcion [" + objAuditoria.getOpcion() + "]");
      _log.debug("Campo [" + objAuditoria.getCampo() + "]");
      _log.debug("Antiguo valor [" + objAuditoria.getAntiguoValor() + "]");
      _log.debug("Nuevo valor [" + objAuditoria.getNuevoValor() + "]");
      _log.debug("Fecha [" + objAuditoria.getFechaAudioria() + "]");
      _log.debug("Responsable [" + objAuditoria.getUsuario() + "]");
      _log.debug("***************************");
   }

	@Transactional(propagation = Propagation.REQUIRED)
	public Auditoria SaveAuditoria(Auditoria ObjAuditoria) {
		return dao.SaveAuditoria(ObjAuditoria);
	}

   public List<Auditoria> filtrarAuditoria(String sNroExpediente, String sUsuario, String sModulo, String sNroDocumento, String sFechaDesde, String sFechaHasta) {
      _log.debug("** Filtro de Auditoria **");
      _log.debug("Nro Expediente [" + sNroExpediente + "]");
      _log.debug("Usuario [" + sUsuario + "]");
      _log.debug("Modulo [" + sModulo + "]");
      _log.debug("Documento [" + sNroDocumento + "]");
      _log.debug("Fecha Desde [" + sFechaDesde + "]");
      _log.debug("Fecha Hasta [" + sFechaHasta + "]");

      sNroExpediente = (sNroExpediente == null) ? new String() : sNroExpediente;
      sUsuario = (sUsuario == null) ? new String() : sUsuario;
      sModulo = (sModulo == null) ? new String() : sModulo;
      sNroDocumento = (sNroDocumento == null) ? new String() : sNroDocumento;
      sFechaDesde = (sFechaDesde == null) ? new String() : sFechaDesde;
      sFechaHasta = (sFechaHasta == null) ? new String() : sFechaHasta;

      return dao.filtrarAuditoria(sNroExpediente, sUsuario, sModulo, sNroDocumento, sFechaDesde, sFechaHasta);
   }

   //////////////////////////////////
   // Getters and Setters          //
   //////////////////////////////////
   public void setDao(AuditoriaDAO dao) {
      this.dao = dao;
   }

   public AuditoriaDAO getDao() {
      return dao;
   }
}
