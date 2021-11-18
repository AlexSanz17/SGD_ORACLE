/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.controllers;

import com.btg.ositran.siged.domain.Cliente;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;
import org.ositran.services.ClienteService;

@Namespace("/service")
public class ClienteController implements ModelDriven<Cliente> {

   private static Logger log = Logger.getLogger(ClienteController.class);
   private Cliente model;
   private ClienteService clienteService;
   private String id;

   /*
    * Methods
    */
   public HttpHeaders show() {
      if (log.isDebugEnabled()) {
         log.debug("id [" + id + "]");
      }

      try {
         model = clienteService.findByIdCliente(Integer.valueOf(id));
         log.info("cliente encontrado con nroidentificacion [" + model.getNumeroIdentificacion() + "]");

         return new DefaultHttpHeaders("show");
      } catch (Exception e) {
         log.error(e.getMessage(), e);

         return new DefaultHttpHeaders("error");
      }

   }

   /*
    * Getters && Setters
    */
   public Cliente getModel() {
      return model;
   }

   public ClienteService getClienteService() {
      return clienteService;
   }

   public void setClienteService(ClienteService clienteService) {
      this.clienteService = clienteService;
   }

   public void setId(String id) {
      this.id = id;
   }
}
