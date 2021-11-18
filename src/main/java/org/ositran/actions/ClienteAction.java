/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.actions;

import com.btg.ositran.siged.domain.Cliente;
import com.opensymphony.xwork2.Action;
import org.ositran.services.ClienteService;

public class ClienteAction {
   private Cliente cliente;
   private ClienteService ss;

   public ClienteAction(ClienteService ss) {
      setSs(ss);
   }

   public String save() throws Exception {
      Cliente solRes = new Cliente();

      //solRes = getSs().save(getSolicitante(), Integer.decode(getIdIdentif()));
      this.cliente = new Cliente();
      setCliente(solRes);

      return Action.SUCCESS;
   }

   ////////////////////////
   // Getters and Setters
   ////////////////////////
   public ClienteService getSs() {
      return ss;
   }

   public void setSs(ClienteService ss) {
      this.ss = ss;
   }

   public Cliente getCliente() {
      return cliente;
   }

   public void setCliente(Cliente cliente) {
      this.cliente = cliente;
   }
}
