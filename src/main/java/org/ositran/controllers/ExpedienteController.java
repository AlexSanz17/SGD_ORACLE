/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.controllers;

import com.btg.ositran.siged.domain.Cliente;
import com.btg.ositran.siged.domain.Expediente;
import com.btg.ositran.siged.domain.Proceso;
import com.btg.ositran.siged.domain.Usuario;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;
import org.ositran.services.ClienteService;
import org.ositran.services.ExpedienteService;
import org.ositran.services.ProcesoService;
import org.ositran.services.RepositorioService;
import org.ositran.services.UsuarioService;
import org.ositran.utils.Constantes;
import org.ositran.utils.DocumentoDetail;

@Namespace("/service")
@Results({
   @Result(name = "success", type = "redirectAction", params = {"actionName", "expediente"})
})
public class ExpedienteController implements ModelDriven<Expediente> {

   private static Logger log = Logger.getLogger(ExpedienteController.class);
   private Expediente model;
   private String id;
   //Services
   private ClienteService clienteService;
   private ExpedienteService expedienteService;
   private ProcesoService procesoService;
   private RepositorioService repositorioService;
   private UsuarioService usuarioService;
   //Parameters
   private String asunto;
   private String clienteNumeroIdentificacion;
   private String procesoNombre;
   private String usuarioLogin;

   /*
    * Methods
    */
   // POST /expediente
   public HttpHeaders create() {
      if (log.isDebugEnabled()) {
         log.debug("asunto [" + asunto + "]");
         log.debug("clienteNumeroIdentificacion [" + clienteNumeroIdentificacion + "]");
         log.debug("usuarioLogin [" + usuarioLogin + "]");
         log.debug("proceso [" + procesoNombre + "]");
      }

      try {
         Cliente cliente = clienteService.findObjectBy(clienteNumeroIdentificacion, Constantes.ESTADO_ACTIVO);
         DocumentoDetail documentoDetail = new DocumentoDetail();
         Expediente expediente = null;
         Proceso proceso = procesoService.findObjectBy(procesoNombre, Constantes.ESTADO_ACTIVO);
         Usuario usuario = usuarioService.findObjectBy(usuarioLogin, Constantes.ESTADO_ACTIVO);

         documentoDetail.setIIdCliente(cliente.getIdCliente());
         documentoDetail.setIIdProceso(proceso.getIdproceso());
         documentoDetail.setStrAsunto(asunto);
         documentoDetail.setsSumilla(asunto);

        // expediente = expedienteService.prepareExpediente(documentoDetail, usuario);
         model = new Expediente(expediente.getIdexpediente(), expediente.getNroexpediente(), expediente.getFechacreacion(), expediente.getEstado());

         return new DefaultHttpHeaders("success").setLocationId(model.getIdexpediente());
      } catch (Exception e) {
         log.error(e.getMessage(), e);

         return new DefaultHttpHeaders("error");
      }
   }

   // GET /expediente/{idExpediente}
   public HttpHeaders show() {
      Expediente expediente = null;

      if (log.isDebugEnabled()) {
         log.debug("id [" + id + "]");
      }

      try {
         expediente = expedienteService.findByIdExpediente(Integer.valueOf(id));
         model = new Expediente(expediente.getIdexpediente(), expediente.getNroexpediente(), expediente.getFechacreacion(), expediente.getEstado());
         log.info("expediente encontrado con nroExpediente [" + model.getNroexpediente() + "]");

         return new DefaultHttpHeaders("show");
      } catch (Exception e) {
         log.error(e.getMessage(), e);

         return new DefaultHttpHeaders("error");
      }
   }

   /*
    * Getters & Setters
    */
   public Expediente getModel() {
      return model;
   }

   public void setModel(Expediente model) {
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

   public UsuarioService getUsuarioService() {
      return usuarioService;
   }

   public void setUsuarioService(UsuarioService usuarioService) {
      this.usuarioService = usuarioService;
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
}
