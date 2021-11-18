/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.actions;

import com.btg.ositran.siged.domain.CarpetaBusqueda;
import com.btg.ositran.siged.domain.Documento;
import com.btg.ositran.siged.domain.Proceso;
import com.btg.ositran.siged.domain.Usuario;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.ositran.utils.Constantes;
import org.ositran.services.CarpetabusquedaService;
import org.ositran.services.NotificacionService;
import org.ositran.services.ProcesoService;

public class CarpetabusquedaAction {

   private static Logger log = Logger.getLogger(CarpetabusquedaAction.class);
   private CarpetaBusqueda objCB;
   private CarpetabusquedaService SvrCb;
   private List<CarpetaBusqueda> lisbc;
   private String regresar;
   private Integer iIdDoc;
   private ProcesoService Svrproceso;
   private Map<String, Object> mapSession;
   private NotificacionService srvNotificacion;
   private int nroNotificacionesNL;
   private String proceso;
   private CarpetaBusqueda objCarpetaBusqueda;
   private CarpetabusquedaService srvCarpetaBusqueda;
   //////////////////////////////////
   // Constructors                 //
   //////////////////////////////////
   public CarpetabusquedaAction(CarpetabusquedaService SvrCb, ProcesoService Svrproceso) {
      setSvrCb(SvrCb);
      setSvrproceso(Svrproceso);
   }

   public CarpetabusquedaAction() {
   }

   //////////////////////////////////
   // Methods                      //
   //////////////////////////////////

   public String findbuscarpeta() throws IOException, ServletException {
      List<CarpetaBusqueda> data = SvrCb.ViewAll();
      setLisbc(data);
      mapSession = ActionContext.getContext().getSession();
      Usuario usuario=(Usuario) getMapSession().get(Constantes.SESSION_USUARIO);
      log.debug("El usuario: " + usuario);
      nroNotificacionesNL = srvNotificacion.getNroNotificacionesNL(usuario);
      RequestDispatcher rd = ServletActionContext.getServletContext().getRequestDispatcher("/menu.jsp");
      ServletActionContext.getRequest().setAttribute("data", rd);
      rd.forward(ServletActionContext.getRequest(), ServletActionContext.getResponse());

      return Action.NONE;
   }

   @SuppressWarnings({ "rawtypes", "unchecked" })
public String findbuscarpetaid() throws IOException, ServletException {

      String StrIdcarpetaBusqueda = ServletActionContext.getRequest().getParameter("strid");
      int intcarbus = Integer.parseInt(StrIdcarpetaBusqueda);
      List/*<Expedienfindadvance>*/ data = SvrCb.findbyAllid(intcarbus);

      setLisbc(data);
      // 	nroNotificacionesNL=srvNotificacion.getNroNotificacionesNL((Usuario) getMapSession().get(Constantes.SESSION_USUARIO));
      RequestDispatcher rd = ServletActionContext.getServletContext().getRequestDispatcher("/pages/bandeja/docCarpetas.jsp");
      ServletActionContext.getRequest().setAttribute("data", rd);
      rd.forward(ServletActionContext.getRequest(), ServletActionContext.getResponse());

      return Action.NONE;
   }


   public String saveCarpetaBusqueda() throws Exception {
      log.debug("INTENTANDO GRABAR UNA CARPETA DE BUSQUEDA");
      log.debug("Nombre del Proceso [" + objCB.getProceso() + "]");

      try {
         mapSession = ActionContext.getContext().getSession();
         Usuario objUsuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);

         if (getObjCB() == null) {
            //log.error("No hay objeto Carpetabusqueda para grabar");
            return Action.ERROR;
         }

         objCB.setUsuario(objUsuario);
         CarpetaBusqueda c = getObjCB();
         SimpleDateFormat sp = new SimpleDateFormat("dd/MM/yyyy");

         try {
            c.setFechaDocumentoInicio(sp.parse(c.getStrFechaDocumentoInicio()));
         } catch (Exception e) {
         }
         try {
            c.setFechaDocumentoFinal(sp.parse(c.getStrFechaDocumentoFinal()));
         } catch (Exception e) {
         }
         try {
            c.setFechaExpedienteInicio(sp.parse(c.getStrFechaExpedienteInicio()));
         } catch (Exception e) {
         }
         try {
            c.setFechaExpedienteFinal(sp.parse(c.getStrFechaExpedienteFinal()));
         } catch (Exception e) {
         }


         getSvrCb().saveCarpetaBusqueda(getObjCB());

         setRegresar("OK");
         log.debug("ingreso");

         //return Action.SUCCESS;
         return Action.NONE;
      } catch (Exception e) {
         log.error(e.getMessage(), e);

         return Action.ERROR;
      }
   }

   public String modificarCarpeta() throws Exception {
      String StrIdcarpetaBusqueda = ServletActionContext.getRequest().getParameter("Idcarperta");
      log.debug(StrIdcarpetaBusqueda);

      int Id = Integer.parseInt(StrIdcarpetaBusqueda);
      CarpetaBusqueda ObjcarBus = new CarpetaBusqueda();

      ObjcarBus = SvrCb.ViewbyId(Id);

      setObjCB(ObjcarBus);
      ServletActionContext.getRequest().getSession().setAttribute("carpetaBusq", ObjcarBus);
      objCarpetaBusqueda = ObjcarBus;

      log.debug("Se modificara carpeta con ID [" + objCarpetaBusqueda.getIdCarpetaBusqueda() + "] nombre [" + objCarpetaBusqueda.getNombreCarpeta() + "]");

      return "busquedacarpeta";

   }

   public String eliminarCarpetaBusqueda() throws Exception {

      String StrIdcarpetaBusqueda = ServletActionContext.getRequest().getParameter("Idcarperta");
      int Id = Integer.parseInt(StrIdcarpetaBusqueda);
      CarpetaBusqueda ObjcarBus = new CarpetaBusqueda();
      ObjcarBus = SvrCb.ViewbyId(Id);

      getSvrCb().eliminarCarpetabusqueda(ObjcarBus);

      return Action.SUCCESS;
   }


   public String permisosbusquedas() throws Exception {
      Map<String, Object> session = ActionContext.getContext().getSession();
      String StrIddoc = ServletActionContext.getRequest().getParameter("iIdDoc");
      int iIdDoc = Integer.parseInt(StrIddoc);
      String StrIdproce = ServletActionContext.getRequest().getParameter("IdPro");
      int iId = Integer.parseInt(StrIdproce);
      //String StrIduser = ServletActionContext.getRequest().getParameter("IdUser");
      //int iIduser=Integer.parseInt(StrIduser);
      Integer iIdUsuario = (Integer) session.get("idusuario");
      //Usuario user;
      List<Usuario> data;
      Proceso Objproceso = getSvrproceso().findByIdProceso(iId);
      String tipacceso = Objproceso.getIdtipoacceso().getCodigo();


      if (tipacceso.equals("ProcAcc1")) {
         setIIdDoc(iIdDoc);
         return Action.SUCCESS;
      }
      if (tipacceso.equals("ProcAcc2")) {
         int tamlis = Objproceso.getUsuarioList().size();
         data = Objproceso.getUsuarioList();
         for (int i = 0; i < tamlis; i++) {
            if (iIdUsuario.equals(data.get(i).getIdusuario())) {
               setIIdDoc(iIdDoc);
               return Action.SUCCESS;
            }
         }

      }
      if (tipacceso.equals("ProcAcc3")) {
         int tamlis = Objproceso.getUsuarioList1().size();
         data = Objproceso.getUsuarioList();
         for (int i = 0; i < tamlis; i++) {
            if (iIdUsuario.equals(data.get(i).getIdusuario())) {
               setIIdDoc(iIdDoc);
               return Action.SUCCESS;
            }
         }
      }
      if (tipacceso.equals("ProcAcc4")) {
         setIIdDoc(iIdDoc);
      }

      return Action.SUCCESS;
   }

   //////////////////////////////////
   // Getters and Setters          //
   //////////////////////////////////
   public CarpetaBusqueda getObjCB() {
      return objCB;
   }

   public void setObjCB(CarpetaBusqueda objCB) {
      this.objCB = objCB;
   }

   public CarpetabusquedaService getSvrCb() {
      return SvrCb;
   }

   public void setSvrCb(CarpetabusquedaService SvrCb) {
      this.SvrCb = SvrCb;
   }

   public List<CarpetaBusqueda> getLisbc() {
      return lisbc;
   }

   public void setLisbc(List<CarpetaBusqueda> lisbc) {
      this.lisbc = lisbc;
   }

   public String getRegresar() {
      return regresar;
   }

   public void setRegresar(String regresar) {
      this.regresar = regresar;
   }

   public void setIIdDoc(Integer iIdDoc) {
      this.iIdDoc = iIdDoc;
   }

   public Integer getIIdDoc() {
      return iIdDoc;
   }

   public void setSvrproceso(ProcesoService svrproceso) {
      Svrproceso = svrproceso;
   }

   public ProcesoService getSvrproceso() {
      return Svrproceso;
   }

   public void setMapSession(Map<String, Object> mapSession) {
      this.mapSession = mapSession;
   }

   public Map<String, Object> getMapSession() {
      return mapSession;
   }

   public NotificacionService getSrvNotificacion() {
      return srvNotificacion;
   }

   public void setSrvNotificacion(NotificacionService srvNotificacion) {
      this.srvNotificacion = srvNotificacion;
   }

   public int getNroNotificacionesNL() {
      return nroNotificacionesNL;
   }

   public void setNroNotificacionesNL(int nroNotificacionesNL) {
      this.nroNotificacionesNL = nroNotificacionesNL;
   }

   public String getProceso() {
      return proceso;
   }

   public void setProceso(String proceso) {
      this.proceso = proceso;
   }

   public CarpetaBusqueda getObjCarpetaBusqueda() {
      return objCarpetaBusqueda;
   }

   public void setObjCarpetaBusqueda(CarpetaBusqueda objCarpetaBusqueda) {
      this.objCarpetaBusqueda = objCarpetaBusqueda;
   }

public CarpetabusquedaService getSrvCarpetaBusqueda() {
	return srvCarpetaBusqueda;
}

public void setSrvCarpetaBusqueda(CarpetabusquedaService srvCarpetaBusqueda) {
	this.srvCarpetaBusqueda = srvCarpetaBusqueda;
}


}
