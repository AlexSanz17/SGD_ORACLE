/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.actions;

import java.util.List;

import org.ositran.services.AuditoriaService;

import com.btg.ositran.siged.domain.Auditoria;


public class AuditoriaAction {

   //private static Logger log = Logger.getLogger("org.ositran.actions.DiafestivoAction");
   private AuditoriaService srvauditoria;
   private List<Auditoria> listauditor;
   private Auditoria ObjAuditoria;

   public void save()throws Exception{
	   getSrvauditoria().SaveAuditoria(getObjAuditoria());
   }

   /*public void findAvanced()
   {
	   String txtExpediente=ServletActionContext.getRequest().getParameter("txtExpediente");
	   String txtUsuario=ServletActionContext.getRequest().getParameter("txtUsuario");
	   String txtModulo=ServletActionContext.getRequest().getParameter("txtModulo");
	   String txtDocumento=ServletActionContext.getRequest().getParameter("txtDocumento");
	   String fechaDesde=ServletActionContext.getRequest().getParameter("fechaDesde");
	   String fechaHasta=ServletActionContext.getRequest().getParameter("fechaHasta");

   }*/


   public void setListauditor(List<Auditoria> listauditor) {
	   this.listauditor = listauditor;
   }


   public List<Auditoria> getListauditor() {
	   return listauditor;
   }
public void setObjAuditoria(Auditoria objAuditoria) {
	ObjAuditoria = objAuditoria;
}
public Auditoria getObjAuditoria() {
	return ObjAuditoria;
}
public void setSrvauditoria(AuditoriaService srvauditoria) {
	this.srvauditoria = srvauditoria;
}
public AuditoriaService getSrvauditoria() {
	return srvauditoria;
}
}
