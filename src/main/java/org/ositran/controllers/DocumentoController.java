/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.controllers;

import org.ositran.services.ProcesoService;
import org.ositran.services.RepositorioService;

import com.btg.ositran.siged.domain.Documento;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;
import org.ositran.services.ClienteService;
import org.ositran.services.DocumentoService;
import org.ositran.services.ExpedienteService;
import org.ositran.services.RolService;
import org.ositran.services.TipodocumentoService;
import org.ositran.services.UsuarioService;



@Namespace("/service")
@Results({
   @Result(name = "success", type = "redirectAction", params = {"actionName", "documento"})
})
public class DocumentoController implements ModelDriven<Documento> {

   private static Logger log = Logger.getLogger(DocumentoController.class);
   private Documento model;
   private String id;
   //Services
   private ClienteService clienteService;
   private DocumentoService documentoService;
   private ExpedienteService expedienteService;
   private ProcesoService procesoService;
   private RepositorioService repositorioService;
   private RolService rolService;
   private TipodocumentoService tipodocumentoService;
   private UsuarioService usuarioService;
   //Parametros
   private Integer documentoNumeroFolios;
   private String asunto;
   private String clienteNumeroIdentificacion;
   private String documentoEnumerar;
   private String documentoFecha;
   private String documentoNumero;
   private String documentoPrincipal;
   private String documentoTipo;
   private String documentoTipoNumeracion;
   private String expedienteNumero;
   private String observacion;
   private String procesoNombre;
   private String usuarioLogin;
   private String usuarioRol;

   /*
    * Methods
    */
   // POST /documento
   //FIXME no usado? --------------------------------------------------------------------------------------------------------//
   /*public HttpHeaders create() {
      if (log.isDebugEnabled()) {
         log.debug("documentoNumeroFolios [" + documentoNumeroFolios + "]");
         log.debug("asunto [" + asunto + "]");
         log.debug("clienteNumeroIdentificacion [" + clienteNumeroIdentificacion + "]");
         log.debug("documentoEnumerar [" + documentoEnumerar + "]");
         log.debug("documentoFecha [" + documentoFecha + "]");
         log.debug("documentoNumero [" + documentoNumero + "]");
         log.debug("documentoPrincipal [" + documentoPrincipal + "]");
         log.debug("documentoTipo [" + documentoTipo + "]");
         log.debug("documentoTipoNumeracion [" + documentoTipoNumeracion + "]");
         log.debug("expedienteNumero [" + expedienteNumero + "]");
         log.debug("observacion [" + observacion + "]");
         log.debug("procesoNombre [" + procesoNombre + "]");
         log.debug("usuarioLogin [" + usuarioLogin + "]");
         log.debug("usuarioRol [" + usuarioRol + "]");
      }

      try {
         Cliente cliente = clienteService.findObjectBy(clienteNumeroIdentificacion, Constantes.ESTADO_ACTIVO);
         DocumentoDetail documentoDetail = new DocumentoDetail();
         Expediente expediente = expedienteService.buscarObjPor(expedienteNumero);
         Proceso proceso = procesoService.findObjectBy(procesoNombre, Constantes.ESTADO_ACTIVO);
         Rol rol = rolService.findByNombre(usuarioRol);
         String rolToWorkWith = Constantes.ROL_USUARIO_FINAL;
         
         Tipodocumento tipoDocumento = tipodocumentoService.findByNombre(documentoTipo);
         Usuario usuario = usuarioService.findObjectBy(usuarioLogin, Constantes.ESTADO_ACTIVO);

         if (rol != null && usuario.getRoles().contains(rol)) {
            usuario.setRol(rol);
            rolToWorkWith = usuario.getRol().getNombre();

            if (log.isDebugEnabled()) {
               log.debug("usuario [" + usuario.getUsuario() + "] rol [" + usuario.getRol() + "]");
            }
         }

         documentoDetail.setIIdExpediente(isEmpty(expedienteNumero) ? null : expediente.getIdexpediente());
         documentoDetail.setIIdProceso(proceso.getIdproceso());
         documentoDetail.setIIdCliente(cliente.getIdCliente());
         documentoDetail.setIIdTipoDocumento(tipoDocumento.getIdtipodocumento());
         documentoDetail.setEnumerarDocumento(isEmpty(documentoEnumerar) || documentoEnumerar.equals("N") ? "N" : documentoEnumerar);
         documentoDetail.setTipoNumeracion(documentoTipoNumeracion);
         documentoDetail.setStrNroDocumento(documentoNumero);
         documentoDetail.setINroFolios(documentoNumeroFolios);
         documentoDetail.setStrAsunto(asunto);
         documentoDetail.setsSumilla(asunto);
         documentoDetail.setStrObservacion(observacion);
         documentoDetail.setCEstado(Constantes.ESTADO_ACTIVO);
         documentoDetail.setCPrincipal((isEmpty(documentoPrincipal) || documentoPrincipal.equalsIgnoreCase("N")) ? Constantes.DOCUMENTO_NO_PRINCIPAL : Constantes.DOCUMENTO_PRINCIPAL);
         documentoDetail.setStrFechaDocumento(documentoFecha);
         documentoDetail.setFechacreacion(new Date());
         documentoDetail.setIdUsuarioLogeado(usuario.getIdusuario());

         documentoDetail = documentoService.saveDocumentTEMPORAL(documentoDetail, Constantes.ACCION_REGISTRAR, 2, usuario, rolToWorkWith);
         Documento documento = documentoDetail.getDoc();
         model = new Documento(documento.getIdDocumento(), documento.getExpediente().getNroexpediente(), documento.getPrincipal(), documento.getNumeroFolios(), documento.getAsunto(), documento.getEstado(), documento.getTipoDocumento(), documento.getNumeroDocumento());

         return new DefaultHttpHeaders("success").setLocationId(model.getIdDocumento());
      } catch (Exception e) {
         log.error(e.getMessage(), e);

         return new DefaultHttpHeaders("error");
      }
   }
*/
   // GET /documento/{idDocumento}
   public HttpHeaders show() {
      Documento documento = null;

      if (log.isDebugEnabled()) {
         log.debug("id [" + id + "]");
      }

      try {
         documento = documentoService.findByIdDocumento(Integer.valueOf(id));
         model = new Documento(documento.getIdDocumento(), documento.getExpediente().getNroexpediente(), documento.getPrincipal(), documento.getNumeroFolios(), documento.getAsunto(), documento.getEstado(), documento.getTipoDocumento(), documento.getNumeroDocumento());
         log.info("documento encontrado [" + model.getTipoDocumento().getNombre() + " - " + model.getNumeroDocumento() + "]");

         return new DefaultHttpHeaders("show");
      } catch (Exception e) {
         log.error(e.getMessage(), e);

         return new DefaultHttpHeaders("error");
      }
   }

   /*
    * Getters & Setters
    */
   public Documento getModel() {
      return model;
   }

   public void setModel(Documento model) {
      this.model = model;
   }

   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public ClienteService getClienteService() {
      return clienteService;
   }

   public void setClienteService(ClienteService clienteService) {
      this.clienteService = clienteService;
   }

   public DocumentoService getDocumentoService() {
      return documentoService;
   }

   public void setDocumentoService(DocumentoService documentoService) {
      this.documentoService = documentoService;
   }

   public ExpedienteService getExpedienteService() {
      return expedienteService;
   }

   public void setExpedienteService(ExpedienteService expedienteService) {
      this.expedienteService = expedienteService;
   }

   public ProcesoService getProcesoService() {
      return procesoService;
   }

   public void setProcesoService(ProcesoService procesoService) {
      this.procesoService = procesoService;
   }

   public RepositorioService getRepositorioService() {
      return repositorioService;
   }

   public void setRepositorioService(RepositorioService repositorioService) {
      this.repositorioService = repositorioService;
   }

   public RolService getRolService() {
      return rolService;
   }

   public void setRolService(RolService rolService) {
      this.rolService = rolService;
   }

   public TipodocumentoService getTipodocumentoService() {
      return tipodocumentoService;
   }

   public void setTipodocumentoService(TipodocumentoService tipodocumentoService) {
      this.tipodocumentoService = tipodocumentoService;
   }

   public UsuarioService getUsuarioService() {
      return usuarioService;
   }

   public void setUsuarioService(UsuarioService usuarioService) {
      this.usuarioService = usuarioService;
   }

   public Integer getDocumentoNumeroFolios() {
      return documentoNumeroFolios;
   }

   public void setDocumentoNumeroFolios(Integer documentoNumeroFolios) {
      this.documentoNumeroFolios = documentoNumeroFolios;
   }

   public String getAsunto() {
      return asunto;
   }

   public void setAsunto(String asunto) {
      this.asunto = asunto;
   }

   public String getClienteNumeroIdentificacion() {
      return clienteNumeroIdentificacion;
   }

   public void setClienteNumeroIdentificacion(String clienteNumeroIdentificacion) {
      this.clienteNumeroIdentificacion = clienteNumeroIdentificacion;
   }

   public String getDocumentoEnumerar() {
      return documentoEnumerar;
   }

   public void setDocumentoEnumerar(String documentoEnumerar) {
      this.documentoEnumerar = documentoEnumerar;
   }

   public String getDocumentoFecha() {
      return documentoFecha;
   }

   public void setDocumentoFecha(String documentoFecha) {
      this.documentoFecha = documentoFecha;
   }

   public String getDocumentoNumero() {
      return documentoNumero;
   }

   public void setDocumentoNumero(String documentoNumero) {
      this.documentoNumero = documentoNumero;
   }

   public String getDocumentoPrincipal() {
      return documentoPrincipal;
   }

   public void setDocumentoPrincipal(String documentoPrincipal) {
      this.documentoPrincipal = documentoPrincipal;
   }

   public String getDocumentoTipo() {
      return documentoTipo;
   }

   public void setDocumentoTipo(String documentoTipo) {
      this.documentoTipo = documentoTipo;
   }

   public String getDocumentoTipoNumeracion() {
      return documentoTipoNumeracion;
   }

   public void setDocumentoTipoNumeracion(String documentoTipoNumeracion) {
      this.documentoTipoNumeracion = documentoTipoNumeracion;
   }

   public String getExpedienteNumero() {
      return expedienteNumero;
   }

   public void setExpedienteNumero(String expedienteNumero) {
      this.expedienteNumero = expedienteNumero;
   }

   public String getObservacion() {
      return observacion;
   }

   public void setObservacion(String observacion) {
      this.observacion = observacion;
   }

   public String getProcesoNombre() {
      return procesoNombre;
   }

   public void setProcesoNombre(String procesoNombre) {
      this.procesoNombre = procesoNombre;
   }

   public String getUsuarioLogin() {
      return usuarioLogin;
   }

   public void setUsuarioLogin(String usuarioLogin) {
      this.usuarioLogin = usuarioLogin;
   }

   public String getUsuarioRol() {
      return usuarioRol;
   }

   public void setUsuarioRol(String usuarioRol) {
      this.usuarioRol = usuarioRol;
   }
}
