package org.ositran.services.property;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import org.ositran.services.AccionService;
import org.ositran.services.DocumentoEnviadoService;
import org.ositran.services.DocumentoService;
import org.ositran.services.EtapaService;
import org.ositran.services.ExpedienteService;
import org.ositran.services.GestionDocumentosImpl;
import org.ositran.services.ListaService;
import org.ositran.services.ManejoDeEmailService;
import org.ositran.services.NotificacionService;
import org.ositran.services.ProcesoService;
import org.ositran.services.TrazabilidaddocumentoService;
import org.ositran.services.UsuarioService;
import org.ositran.utils.Constantes;
import org.ositran.utils.DocumentoDetail;
import org.ositran.utils.DocumentoMasivo;
import org.ositran.utils.FechaLimite;
import org.ositran.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.btg.ositran.siged.domain.Accion;
import com.btg.ositran.siged.domain.Documento;
import com.btg.ositran.siged.domain.Documentoenviado;
import com.btg.ositran.siged.domain.Etapa;
import com.btg.ositran.siged.domain.Expediente;
import com.btg.ositran.siged.domain.Lista;
import com.btg.ositran.siged.domain.Trazabilidaddocumento;
import com.btg.ositran.siged.domain.Usuario;

public class GestionDocumentosProperty {
	private final Logger log = LoggerFactory.getLogger(GestionDocumentosImpl.class);
	private EtapaService etapaService;
	private DocumentoService documentoService;
	private AccionService accionService;
	private UsuarioService usuarioService;
	private TrazabilidaddocumentoService trazabilidadDocumentoService;
	private ProcesoService procesoService;
	private ExpedienteService expedienteService;
	private DocumentoEnviadoService documentoEnviadoService;
	private NotificacionService notificacionService;
	private ListaService listaService;
	private ManejoDeEmailService mailService;
	private FechaLimite fechaLimite;
	
	public EtapaService getEtapaService() {
		return etapaService;
	}

	public void setEtapaService(EtapaService etapaService) {
		this.etapaService = etapaService;
	}

	public DocumentoService getDocumentoService() {
		return documentoService;
	}

	public void setDocumentoService(DocumentoService documentoService) {
		this.documentoService = documentoService;
	}

	public AccionService getAccionService() {
		return accionService;
	}

	public void setAccionService(AccionService accionService) {
		this.accionService = accionService;
	}

	public UsuarioService getUsuarioService() {
		return usuarioService;
	}

	public void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	public TrazabilidaddocumentoService getTrazabilidadDocumentoService() {
		return trazabilidadDocumentoService;
	}

	public void setTrazabilidadDocumentoService(
			TrazabilidaddocumentoService trazabilidadDocumentoService) {
		this.trazabilidadDocumentoService = trazabilidadDocumentoService;
	}

	public ProcesoService getProcesoService() {
		return procesoService;
	}

	public void setProcesoService(ProcesoService procesoService) {
		this.procesoService = procesoService;
	}

	public Logger getLog() {
		return log;
	}

	public ExpedienteService getExpedienteService() {
		return expedienteService;
	}

	public void setExpedienteService(ExpedienteService expedienteService) {
		this.expedienteService = expedienteService;
	}
    public DocumentoEnviadoService getDocumentoEnviadoService() {
		return documentoEnviadoService;
	}

	public void setDocumentoEnviadoService(
			DocumentoEnviadoService documentoEnviadoService) {
		this.documentoEnviadoService = documentoEnviadoService;
	}

	public NotificacionService getNotificacionService() {
		return notificacionService;
	}

	public void setNotificacionService(NotificacionService notificacionService) {
		this.notificacionService = notificacionService;
	}

	public ListaService getListaService() {
		return listaService;
	}

	public void setListaService(ListaService listaService) {
		this.listaService = listaService;
	}

	public ManejoDeEmailService getMailService() {
		return mailService;
	}

	public void setMailService(ManejoDeEmailService mailService) {
		this.mailService = mailService;
	}

	public FechaLimite getFechaLimite() {
		return fechaLimite;
	}

	public void setFechaLimite(FechaLimite fechaLimite) {
		this.fechaLimite = fechaLimite;
	}	
}
