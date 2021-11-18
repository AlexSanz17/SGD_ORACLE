package org.ositran.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.ositran.daos.NotificacionDAO;
import org.ositran.daos.NotificacionxEnumerarDAO;
import org.ositran.daos.TrazabilidaddocumentoDAO;
import org.ositran.utils.Constantes;
import org.springframework.transaction.annotation.Transactional;

import com.btg.ositran.siged.domain.Accion;
import com.btg.ositran.siged.domain.Documento;
import com.btg.ositran.siged.domain.Etapa;
import com.btg.ositran.siged.domain.Expediente;
import com.btg.ositran.siged.domain.Notificacion;
import com.btg.ositran.siged.domain.NotificacionxEnumerar;
import com.btg.ositran.siged.domain.Trazabilidadcopia;
import com.btg.ositran.siged.domain.Trazabilidaddocumento;
import com.btg.ositran.siged.domain.Usuario;

public class NotificacionxEnumerarServiceImpl implements NotificacionxEnumerarService{
	
	private static Logger log=Logger.getLogger(NotificacionxEnumerarServiceImpl.class);
	private NotificacionxEnumerarDAO dao;
    private TrazabilidaddocumentoService srvTrazDoc;
	private TrazabilidaddocumentoDAO trazabilidaddocumentoDAO ;
	private List<Usuario> listaUsuariosYaNotificados;
	private List<Usuario> listaUsuariosYaNotificadosNoti;
	private List<Usuario> listaUsuariosYaNotificadosMail;
    private DocumentoService documentoService;
   	private TrazabilidadcopiaService trazabilidadcopiaService;
    private AccionService accionService;
    private ManejoDeEmailService mailService;
   	
	// ////////////////////////////////
	// Constructors //
	// ////////////////////////////////
	public NotificacionxEnumerarServiceImpl(NotificacionxEnumerarDAO dao){
		this.dao=dao;
	}

	// ////////////////////////////////
	// Methods //
	// ////////////////////////////////

   public List<NotificacionxEnumerar> buscarLastNotificacionesbyDocumento(Integer idDocumento, Integer tipoNotificacion) {
		return dao.buscarLastNotificacionesbyDocumento(idDocumento, tipoNotificacion);
	}
	
	
   // ////////////////////////////////
	// Getters and Setters //
	// ////////////////////////////////
	public NotificacionxEnumerarDAO getDao(){
		return dao;
	}

	public void setDao(NotificacionxEnumerarDAO dao){
		this.dao=dao;
	}

   public TrazabilidaddocumentoService getSrvTrazDoc() {
      return srvTrazDoc;
   }

   public void setSrvTrazDoc(TrazabilidaddocumentoService srvTrazDoc) {
      this.srvTrazDoc = srvTrazDoc;
   }

	public TrazabilidaddocumentoDAO getTrazabilidaddocumentoDAO() {
		return trazabilidaddocumentoDAO;
	}

	public void setTrazabilidaddocumentoDAO(
			TrazabilidaddocumentoDAO trazabilidaddocumentoDAO) {
		this.trazabilidaddocumentoDAO = trazabilidaddocumentoDAO;
	}

   public DocumentoService getDocumentoService() {
      return documentoService;
   }

   public void setDocumentoService(DocumentoService documentoService) {
      this.documentoService = documentoService;
   }

public TrazabilidadcopiaService getTrazabilidadcopiaService() {
	return trazabilidadcopiaService;
}

public void setTrazabilidadcopiaService(
		TrazabilidadcopiaService trazabilidadcopiaService) {
	this.trazabilidadcopiaService = trazabilidadcopiaService;
}

public AccionService getAccionService() {
	return accionService;
}

public void setAccionService(AccionService accionService) {
	this.accionService = accionService;
}

    /**
     * @return the mailService
     */
    public ManejoDeEmailService getMailService() {
        return mailService;
    }

    /**
     * @param mailService the mailService to set
     */
    public void setMailService(ManejoDeEmailService mailService) {
        this.mailService = mailService;
    }


}
