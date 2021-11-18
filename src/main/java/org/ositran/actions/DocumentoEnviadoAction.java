/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.actions;

import java.util.List;
import java.util.Map;
import java.text.SimpleDateFormat;
import org.apache.log4j.Logger;
import org.ositran.services.ArchivoService;
import org.ositran.services.DocumentoEnviadoService;
import org.ositran.services.DocumentoReferenciaService;
import org.ositran.services.DocumentoService;
import org.ositran.services.NotificacionService;
import org.ositran.services.TrazabilidadapoyoService;
import org.ositran.services.TrazabilidadcopiaService;
import org.ositran.services.TrazabilidaddocumentoService;
import org.ositran.services.UsuarioService;
import org.ositran.utils.Constantes;
import org.ositran.utils.DocumentoEnviadoTemporal;

import com.btg.ositran.siged.domain.Documento;
import com.btg.ositran.siged.domain.Documentoenviado;
import com.btg.ositran.siged.domain.Notificacion;
import com.btg.ositran.siged.domain.Trazabilidadapoyo;
import com.btg.ositran.siged.domain.Trazabilidadcopia;
import com.btg.ositran.siged.domain.Trazabilidaddocumento;
import com.btg.ositran.siged.domain.Usuario;
import com.opensymphony.xwork2.ActionContext;

public class DocumentoEnviadoAction{

	private static Logger log=Logger.getLogger(DocumentoEnviadoAction.class);
	public DocumentoEnviadoService documentoEnviadoService;
	private UsuarioService usuarioService;

	private ArchivoService archivoService;
	private NotificacionService notificacionService;
	private DocumentoService documentoService;
	private Documento documento;
	private TrazabilidaddocumentoService srvTrazabilidadDocumento;
	private String regresar;
	private Integer idenv;
	private Integer idDocumentoEnviado;
	private Documentoenviado documentoenviado;
	private DocumentoEnviadoTemporal documentoEnviadoTemporal;
	private List<Documentoenviado> documentoEnviadoList;
	private Trazabilidaddocumento trazabilidad ;
	private Trazabilidaddocumento trazabilidaddocumento ;
	private Trazabilidadapoyo trazabilidadapoyo ;
	private TrazabilidaddocumentoService trazabilidadDocumentoService;
	private TrazabilidadapoyoService trazabilidadApoyoService;
	private List<Trazabilidadcopia> lstTrazabilidadCopia;
	private TrazabilidadcopiaService trazabilidadcopiaService;
	private DocumentoReferenciaService documentoReferenciaService;

	public DocumentoReferenciaService getDocumentoReferenciaService() {
		return documentoReferenciaService;
	}

	public void setDocumentoReferenciaService(
			DocumentoReferenciaService documentoReferenciaService) {
		this.documentoReferenciaService = documentoReferenciaService;
	}

	public Trazabilidaddocumento getTrazabilidad() {
		return trazabilidad;
	}

	public void setTrazabilidad(Trazabilidaddocumento trazabilidad) {
		this.trazabilidad = trazabilidad;
	}


	@SuppressWarnings("unused")
	public String verDetalle(){
                log.debug("verDetalle()-->>idDocumentoEnviado:"+idDocumentoEnviado);
                
            	Map<String,Object> session=ActionContext.getContext().getSession();
		session.remove(Constantes.SESSION_UPLOAD_LIST);
		Documentoenviado docEnviado = documentoEnviadoService.findByIddocumentoenviado(idDocumentoEnviado);
		
		if(docEnviado.getTipoEnvio().equals(Constantes.TIPO_ENVIO_TRANSFERIR)){
            	    trazabilidaddocumento = trazabilidadDocumentoService.findByIdTrazabilidadDocumento(docEnviado.getIdTrazabilidadEnvio());
		    documento = documentoService.findByIdDocumento(trazabilidaddocumento.getDocumento().getIdDocumento());
		    session.put(Constantes.SESSION_IDDOCUMENTO, trazabilidaddocumento.getDocumento().getIdDocumento());
		    DocumentoEnviadoTemporal docEnvio = new DocumentoEnviadoTemporal();

		    docEnvio.setIdTrazabilidadEnvio(trazabilidaddocumento.getIdtrazabilidaddocumento());
		    docEnvio.setAsunto(trazabilidaddocumento.getAsunto());
		    docEnvio.setContenido(trazabilidaddocumento.getContenido());
                    docEnvio.setStrAccion(trazabilidaddocumento.getProveido()==null?"":trazabilidaddocumento.getProveido().getNombre());
		    docEnvio.setDestinatarioApellidos(trazabilidaddocumento.getDestinatario().getApellidos());
		    docEnvio.setDestinatarioNombres(trazabilidaddocumento.getDestinatario().getNombres());
		    docEnvio.setRemitenteApellidos(trazabilidaddocumento.getRemitente().getApellidos());
		    docEnvio.setRemitenteNombres(trazabilidaddocumento.getRemitente().getNombres());
		    docEnvio.setNroRegistro(trazabilidaddocumento.getNroregistro());
		    docEnvio.setFechaCreacion(trazabilidaddocumento.getFechacreacion());
                    docEnvio.setPrioridad(trazabilidaddocumento.getPrioridad());
                    docEnvio.setTipoEnvio(docEnviado.getTipoEnvio().charAt(0));
                    //docEnvio.setPrioridad(documento.getPrioridad());
                    //docEnvio.setFechaLimite(documento.getFechaLimiteAtencion()==null?"":new SimpleDateFormat(Constantes.FORMATO_FECHA).format(documento.getFechaLimiteAtencion()));
                    docEnvio.setFechaLimite((trazabilidaddocumento.getFechalimiteatencion() == null) ? "" : new SimpleDateFormat(Constantes.FORMATO_FECHA).format(trazabilidaddocumento.getFechalimiteatencion()));
                    //CTALLEDO
		    this.setDocumentoEnviadoTemporal(docEnvio);
		    lstTrazabilidadCopia = trazabilidadcopiaService.buscarUsuarioCopia(trazabilidaddocumento.getDocumento().getIdDocumento(),docEnviado.getIdTrazabilidadEnvio());
	            StringBuilder cadenaCC = new StringBuilder();
				
                    if(lstTrazabilidadCopia!=null && lstTrazabilidadCopia.size()>0){
			for(int i=0; i<lstTrazabilidadCopia.size(); i++){
			  if(i!=0) cadenaCC.append(", ");
			  cadenaCC.append( lstTrazabilidadCopia.get(i).getDestinatario().getApellidos()+" "+lstTrazabilidadCopia.get(i).getDestinatario().getNombres());
			}
		    }
			
                    docEnviado.setCadenaCC(cadenaCC.toString());
                    
                    if(trazabilidaddocumento!=null){
                       Documento doc=trazabilidaddocumento.getDocumento();
                       session.put(Constantes.SESSION_UPLOAD_LIST,archivoService.findByIdDocumento(doc.getIdDocumento()));
                    }
		}else
                    if(docEnviado.getTipoEnvio().equals(Constantes.TIPO_ENVIO_MULTIPLE)){
                        
                        trazabilidadapoyo = trazabilidadApoyoService.findByIdTrazabilidadApoyo(docEnviado.getIdTrazabilidadEnvio());
                        documento = documentoService.findByIdDocumento(trazabilidadapoyo.getDocumento());
                        session.put(Constantes.SESSION_IDDOCUMENTO, trazabilidadapoyo.getDocumento());
                        DocumentoEnviadoTemporal docEnvio = new DocumentoEnviadoTemporal();

                        docEnvio.setIdTrazabilidadEnvio(trazabilidadapoyo.getIdtrazabilidadapoyo());
                        docEnvio.setAsunto(trazabilidadapoyo.getAsunto());
                        docEnvio.setStrAccion(trazabilidadapoyo.getProveido()==null?"":trazabilidadapoyo.getProveido().getNombre());
                        docEnvio.setContenido(trazabilidadapoyo.getTexto());//consultar si va get texto en contenido???
                        docEnvio.setDestinatarioApellidos(trazabilidadapoyo.getDestinatario().getApellidos());
                        docEnvio.setDestinatarioNombres(trazabilidadapoyo.getDestinatario().getNombres());
                        docEnvio.setRemitenteApellidos(trazabilidadapoyo.getRemitente().getApellidos());
                        docEnvio.setRemitenteNombres(trazabilidadapoyo.getRemitente().getNombres());
                        docEnvio.setNroRegistro(0);// no tiene trazabilidadapoyo que se va a poner
                        docEnvio.setFechaCreacion(trazabilidadapoyo.getFechacreacion());
                        docEnvio.setPrioridad(documento.getPrioridad());
                        docEnvio.setPrioridad(trazabilidadapoyo.getPrioridad());
                        docEnvio.setTipoEnvio(docEnviado.getTipoEnvio().charAt(0));
		        docEnvio.setFechaLimite(trazabilidadapoyo.getFechalimiteatencion() == null ? "" : new SimpleDateFormat(Constantes.FORMATO_FECHA).format(trazabilidadapoyo.getFechalimiteatencion()));			
                        //CTALLEDO
                        docEnvio.setFechaLimite(documento.getFechaLimiteAtencion()==null?"":new SimpleDateFormat(Constantes.FORMATO_FECHA).format(documento.getFechaLimiteAtencion()));
                        this.setDocumentoEnviadoTemporal(docEnvio);
                        docEnviado.setCadenaCC("");
                        if(trazabilidadapoyo!=null){
                            session.put(Constantes.SESSION_UPLOAD_LIST,archivoService.findByIdDocumento(documento.getDocumentoreferencia()));
                        }
                    }else
                        if(docEnviado.getTipoEnvio().equals(Constantes.TIPO_ENVIO_NOTIFICAR)){
                            Notificacion notificacion = null;
                            notificacion = notificacionService.buscarObjPorID(docEnviado.getIdTrazabilidadEnvio());
                            documento = documentoService.findByIdDocumento(notificacion.getIddocumento().getIdDocumento());
                            session.put(Constantes.SESSION_IDDOCUMENTO, notificacion.getIddocumento().getIdDocumento());
                            
                            DocumentoEnviadoTemporal docEnvio = new DocumentoEnviadoTemporal();
                            docEnvio.setIdTrazabilidadEnvio(notificacion.getIdnotificacion());
                            docEnvio.setAsunto(notificacion.getAsunto());
                            docEnvio.setContenido(notificacion.getContenido());
                            docEnvio.setDestinatarioApellidos(notificacion.getIdusuario().getApellidos());
                            docEnvio.setDestinatarioNombres(notificacion.getIdusuario().getNombres());
                            docEnvio.setRemitenteApellidos(docEnviado.getUsuario().getApellidos());
                            docEnvio.setRemitenteNombres(docEnviado.getUsuario().getNombres());
                            docEnvio.setNroRegistro(0);// no tiene notificacion que se va a poner
                            docEnvio.setFechaCreacion(notificacion.getFechanotificacion());
                            docEnvio.setTipoEnvio(docEnviado.getTipoEnvio().charAt(0));
                            docEnvio.setPrioridad(documento.getPrioridad());
                            docEnvio.setFechaLimite(documento.getFechaLimiteAtencion()==null?"":new SimpleDateFormat(Constantes.FORMATO_FECHA).format(documento.getFechaLimiteAtencion()));
                            this.setDocumentoEnviadoTemporal(docEnvio);
                            docEnviado.setCadenaCC("");

                            if(notificacion!=null){
                               session.put(Constantes.SESSION_UPLOAD_LIST,archivoService.findByIdDocumento(notificacion.getIddocumento().getDocumentoreferencia() != null? notificacion.getIddocumento().getDocumentoreferencia() : notificacion.getIddocumento().getIdDocumento()));
                            }
            	        }

		this.setDocumentoenviado(docEnviado);
		return "detalle";
	}

	public String viewBandeja() throws Exception{
		Map<String,Object> session=ActionContext.getContext().getSession();
		Integer idUsuario=((Usuario) session.get(Constantes.SESSION_USUARIO)).getIdusuario();
		setDocumentoEnviadoList(documentoEnviadoService.findActivosByUsuario(idUsuario));
		log.debug(" setDocumentoEnviadoList.size :"+documentoEnviadoList.size());
		return "bandeja";
	}


	public String eliminar(){
		Map<String,Object> request=ActionContext.getContext().getParameters();
		String[] ids=(String[])request.get("id");
		documentoEnviadoService.eliminarDocumentosEnviados(ids);
		Map<String,Object> session=ActionContext.getContext().getSession();
		Integer iIdUsuario=(Integer) session.get("idusuario");
		this.setDocumentoEnviadoList(documentoEnviadoService.findActivosByUsuario(iIdUsuario));
		return "bandeja";
	}

	public void setDocumentoEnviadoService(DocumentoEnviadoService documentoEnviadoService){
		this.documentoEnviadoService=documentoEnviadoService;
	}

	public void setUsuarioService(UsuarioService usuarioService){
		this.usuarioService=usuarioService;
	}

	public void setArchivoService(ArchivoService archivoService){
		this.archivoService=archivoService;
	}

	public Integer getIdDocumentoEnviado(){
		return idDocumentoEnviado;
	}

	public void setIdDocumentoEnviado(Integer idDocumentoEnviado){
		this.idDocumentoEnviado=idDocumentoEnviado;
	}

	public Documentoenviado getDocumentoenviado(){
		return documentoenviado;
	}

	public void setDocumentoenviado(Documentoenviado documentoenviado){
		this.documentoenviado=documentoenviado;
	}

	public String getRegresar(){
		return regresar;
	}

	public void setRegresar(String regresar){
		this.regresar=regresar;
	}

	public Integer getIdenv(){
		return idenv;
	}

	public void setIdenv(Integer idenv){
		this.idenv=idenv;
	}

	public List<Documentoenviado> getDocumentoEnviadoList(){
		return documentoEnviadoList;
	}

	public void setDocumentoEnviadoList(List<Documentoenviado> documentoEnviadoList){
		this.documentoEnviadoList=documentoEnviadoList;
	}

	public NotificacionService getNotificacionService() {
		return notificacionService;
	}

	public void setNotificacionService(NotificacionService notificacionService) {
		this.notificacionService = notificacionService;
	}

	public TrazabilidaddocumentoService getTrazabilidadDocumentoService() {
		return trazabilidadDocumentoService;
	}

	public void setTrazabilidadDocumentoService(
			TrazabilidaddocumentoService trazabilidadDocumentoService) {
		this.trazabilidadDocumentoService = trazabilidadDocumentoService;
	}

	public List<Trazabilidadcopia> getLstTrazabilidadCopia() {
		return lstTrazabilidadCopia;
	}

	public void setLstTrazabilidadCopia(List<Trazabilidadcopia> lstTrazabilidadCopia) {
		this.lstTrazabilidadCopia = lstTrazabilidadCopia;
	}

	public TrazabilidadcopiaService getTrazabilidadcopiaService() {
		return trazabilidadcopiaService;
	}

	public void setTrazabilidadcopiaService(
			TrazabilidadcopiaService trazabilidadcopiaService) {
		this.trazabilidadcopiaService = trazabilidadcopiaService;
	}

	public UsuarioService getUsuarioService() {
		return usuarioService;
	}

	public TrazabilidaddocumentoService getSrvTrazabilidadDocumento() {
		return srvTrazabilidadDocumento;
	}

	public void setSrvTrazabilidadDocumento(
			TrazabilidaddocumentoService srvTrazabilidadDocumento) {
		this.srvTrazabilidadDocumento = srvTrazabilidadDocumento;
	}

	public Trazabilidaddocumento getTrazabilidaddocumento() {
		return trazabilidaddocumento;
	}

	public void setTrazabilidaddocumento(Trazabilidaddocumento trazabilidaddocumento) {
		this.trazabilidaddocumento = trazabilidaddocumento;
	}

	public Trazabilidadapoyo getTrazabilidadapoyo() {
		return trazabilidadapoyo;
	}

	public void setTrazabilidadapoyo(Trazabilidadapoyo trazabilidadapoyo) {
		this.trazabilidadapoyo = trazabilidadapoyo;
	}

	public TrazabilidadapoyoService getTrazabilidadApoyoService() {
		return trazabilidadApoyoService;
	}

	public void setTrazabilidadApoyoService(
			TrazabilidadapoyoService trazabilidadApoyoService) {
		this.trazabilidadApoyoService = trazabilidadApoyoService;
	}

	public DocumentoService getDocumentoService() {
		return documentoService;
	}

	public void setDocumentoService(DocumentoService documentoService) {
		this.documentoService = documentoService;
	}

	public Documento getDocumento() {
		return documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

	public DocumentoEnviadoTemporal getDocumentoEnviadoTemporal() {
		return documentoEnviadoTemporal;
	}

	public void setDocumentoEnviadoTemporal(
			DocumentoEnviadoTemporal documentoEnviadoTemporal) {
		this.documentoEnviadoTemporal = documentoEnviadoTemporal;
	}


}
