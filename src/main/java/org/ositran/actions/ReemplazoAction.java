/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.actions;

import com.btg.ositran.siged.domain.Proceso;
import com.btg.ositran.siged.domain.Reemplazo;
import com.btg.ositran.siged.domain.Usuario;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import java.text.SimpleDateFormat;
import java.util.Map;
import org.apache.log4j.Logger;
import org.ositran.services.ProcesoService;
import org.ositran.services.ReemplazoService;
import org.ositran.utils.Constantes;

public class ReemplazoAction {

   private static Logger _log = Logger.getLogger(ReemplazoAction.class);
   private Integer iIdProceso;
   private Integer iIdReemplazo;
   private Map<String, Object> mapSession;
   private Proceso objProceso;
   private ProcesoService srvProceso;
   private Reemplazo objReemplazo;
   private ReemplazoService srvReemplazo;
   private String fechainicialreemplazo;
   private String horainicialreemplazo;
   private String fechafinalreemplazo;
   private String horafinalreemplazo;
   private Integer iIdParameters[];

   //////////////////////////////////
   // Constructors                 //
   //////////////////////////////////
   public ReemplazoAction(ReemplazoService srvReemplazo) {
      this.srvReemplazo = srvReemplazo;
   }

   //////////////////////////////////
   // Methods                      //
   //////////////////////////////////
   public String eliminarReemplazo() throws Exception {
      Reemplazo objToDelete = null;

      if (iIdParameters == null) {
         _log.error("No se recibio ningun reemplazo a eliminar");

         return Action.ERROR;
      }

      _log.debug("Se recibio [" + iIdParameters.length + "] reemplazos a eliminar");

      for (Integer iIdToDelete : iIdParameters) {
         objToDelete = srvReemplazo.findByIdReemplazo(iIdToDelete);
         objToDelete.setEstado(Constantes.ESTADO_INACTIVO);
         srvReemplazo.saveReemplazo(objToDelete);
      }

      return Action.SUCCESS;
   }

   public String saveReemplazo() throws Exception {
      SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
      Usuario objUsuario = null;

      if (objReemplazo == null) {
         _log.error("No se recibio reemplazo a guardar");

         return Action.ERROR;
      }

      mapSession = ActionContext.getContext().getSession();
      objUsuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);

      objReemplazo.setEstado(Constantes.ESTADO_ACTIVO);
      objReemplazo.setIdreemplazado(objUsuario.getIdusuario());

      _log.debug("Fecha inicial de Reemplazo [" + fechainicialreemplazo + "] Hora inicial [" + horainicialreemplazo + "]");
      _log.debug("Fecha final de Reemplazo [" + fechafinalreemplazo + "] Hora final [" + horafinalreemplazo + "]");

      try {
         objReemplazo.setFechainicialreemplazo(formatDate.parse(fechainicialreemplazo + horainicialreemplazo));
         objReemplazo.setFechafinalreemplazo(formatDate.parse(fechafinalreemplazo + horafinalreemplazo));
      } catch (Exception ex) {
         _log.error(ex.getMessage(), ex);
      }

      if (objReemplazo.getIdreemplazo() == null) {
         _log.debug("** CREACION DE REEMPLAZO **");
      } else {
         _log.debug("** MODIFICACION DE REEMPLAZO **");
      }

      srvReemplazo.saveReemplazo(objReemplazo);

      return Action.SUCCESS;
   }

   public String saveMantReemplazo() throws Exception {
      SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

      if (objReemplazo == null) {
         _log.error("No se recibio reemplazo a guardar");

         return Action.ERROR;
      }

      _log.debug("Fecha inicial de Reemplazo [" + fechainicialreemplazo + "] Hora inicial [" + horainicialreemplazo + "]");
      _log.debug("Fecha final de Reemplazo [" + fechafinalreemplazo + "] Hora final [" + horafinalreemplazo + "]");

      try {
         objReemplazo.setFechainicialreemplazo(formatDate.parse(fechainicialreemplazo + horainicialreemplazo));
         objReemplazo.setFechafinalreemplazo(formatDate.parse(fechafinalreemplazo + horafinalreemplazo));
      } catch (Exception ex) {
         _log.error(ex.getMessage(), ex);
      }

      if (objReemplazo.getIdreemplazo() == null) {
         _log.debug("** CREACION DE REEMPLAZO **");
         objReemplazo.setEstado(Constantes.ESTADO_ACTIVO);
      } else {
         _log.debug("** MODIFICACION DE REEMPLAZO con ID [" + objReemplazo.getIdreemplazo() + "] **");
      }

      srvReemplazo.saveReemplazo(objReemplazo);

      return Action.SUCCESS;
   }

   public String viewReemplazo() throws Exception {
      if (iIdProceso == null) {
         _log.error("No se recibio ningun ID proceso asociado al reemplazo");

         return Action.ERROR;
      }

      _log.debug("Reemplazo a ver con ID [" + iIdReemplazo + "]");

      objReemplazo = (iIdReemplazo == null) ? null : srvReemplazo.findByIdReemplazo(iIdReemplazo);
      objProceso = srvProceso.findByIdProceso(iIdProceso);

      if (objReemplazo != null) {
         _log.debug("Se encontro el reemplazo con ID [" + objReemplazo.getIdreemplazo() + "] asociado al proceso [" + objProceso.getNombre() + "]");
      } else {
         _log.debug("No hay reemplazo asociado al proceso [" + objProceso.getNombre() + "]");
      }

      return Action.SUCCESS;
   }

   public String viewMantReemplazo() throws Exception {
      if (iIdReemplazo != null) {
         objReemplazo = srvReemplazo.findByIdReemplazo(iIdReemplazo);

         _log.debug("Reemplazo a ver con con ID [" + objReemplazo.getIdreemplazo() + "] Reemplazado [" + objReemplazo.getIdreemplazado() + "]");
      }

      return Action.SUCCESS;
   }

   //////////////////////////////////
   // Getters and Setters          //
   //////////////////////////////////
   public Integer getiIdProceso() {
      return iIdProceso;
   }

   public void setiIdProceso(Integer iIdProceso) {
      this.iIdProceso = iIdProceso;
   }

   public Integer getiIdReemplazo() {
      return iIdReemplazo;
   }

   public void setiIdReemplazo(Integer iIdReemplazo) {
      this.iIdReemplazo = iIdReemplazo;
   }

   public Map<String, Object> getMapSession() {
      return mapSession;
   }

   public void setMapSession(Map<String, Object> mapSession) {
      this.mapSession = mapSession;
   }

   public Proceso getObjProceso() {
      return objProceso;
   }

   public void setObjProceso(Proceso objProceso) {
      this.objProceso = objProceso;
   }

   public ProcesoService getSrvProceso() {
      return srvProceso;
   }

   public void setSrvProceso(ProcesoService srvProceso) {
      this.srvProceso = srvProceso;
   }

   public Reemplazo getObjReemplazo() {
      return objReemplazo;
   }

   public void setObjReemplazo(Reemplazo objReemplazo) {
      this.objReemplazo = objReemplazo;
   }

   public ReemplazoService getSrvReemplazo() {
      return srvReemplazo;
   }

   public void setSrvReemplazo(ReemplazoService srvReemplazo) {
      this.srvReemplazo = srvReemplazo;
   }

   public String getFechainicialreemplazo() {
      return fechainicialreemplazo;
   }

   public void setFechainicialreemplazo(String fechainicialreemplazo) {
      this.fechainicialreemplazo = fechainicialreemplazo;
   }

   public String getHorainicialreemplazo() {
      return horainicialreemplazo;
   }

   public void setHorainicialreemplazo(String horainicialreemplazo) {
      this.horainicialreemplazo = horainicialreemplazo;
   }

   public String getFechafinalreemplazo() {
      return fechafinalreemplazo;
   }

   public void setFechafinalreemplazo(String fechafinalreemplazo) {
      this.fechafinalreemplazo = fechafinalreemplazo;
   }

   public String getHorafinalreemplazo() {
      return horafinalreemplazo;
   }

   public void setHorafinalreemplazo(String horafinalreemplazo) {
      this.horafinalreemplazo = horafinalreemplazo;
   }

   public Integer[] getiIdParameters() {
      return iIdParameters;
   }

   public void setiIdParameters(Integer[] iIdParameters) {
      this.iIdParameters = iIdParameters;
   }
}
