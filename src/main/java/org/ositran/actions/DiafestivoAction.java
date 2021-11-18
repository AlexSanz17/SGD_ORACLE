/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.actions;

import com.btg.ositran.siged.domain.DiaFestivo;
import com.btg.ositran.siged.domain.Usuario;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.ositran.services.DiafestivoService;
import org.ositran.utils.Busdiafestivo;
import org.ositran.utils.Constantes;

public class DiafestivoAction {

   private static Logger log = Logger.getLogger("org.ositran.actions.DiafestivoAction");
   private DiaFestivo objDf;
   private DiafestivoService SvrDf;
   private List<DiaFestivo> lisDf;
   private List<Busdiafestivo> lisBDf;
   private Integer idmes;
   private String aviso;
   private Map<String,Object> mapSession;

   //////////////////////////////////
   // Constructors                 //
   //////////////////////////////////
   public DiafestivoAction(DiafestivoService SvrDf) {

      setSvrDf(SvrDf);

   }

   public DiafestivoAction() {
   }

   //////////////////////////////////
   // Methods                      //
   //////////////////////////////////
   public String finddiafestivo() throws IOException, ServletException {
      List<DiaFestivo> data = SvrDf.ViewAll();
      setLisDf(data);

      RequestDispatcher rd = ServletActionContext.getServletContext().getRequestDispatcher("/pages/ingreso/fecha_feriado.jsp");
      ServletActionContext.getRequest().setAttribute("data", rd);
      rd.forward(ServletActionContext.getRequest(), ServletActionContext.getResponse());

      return Action.NONE;
   }

   public String findlisdiafestivo() throws IOException, ServletException {

      String Strnomregion = ServletActionContext.getRequest().getParameter("region");

      List<Busdiafestivo> data = SvrDf.findlisdiafestivo(Strnomregion);
      setLisBDf(data);

      RequestDispatcher rd = ServletActionContext.getServletContext().getRequestDispatcher("/pages/ingreso/fecha_reporte.jsp");
      ServletActionContext.getRequest().setAttribute("data", rd);
      rd.forward(ServletActionContext.getRequest(), ServletActionContext.getResponse());

      return Action.NONE;
   }

	
	public String saveDiafestivo() throws Exception {
      try {
         Usuario objUsuarioSesion = null;

         String Strregion = ServletActionContext.getRequest().getParameter("region");
         String Stranio = ServletActionContext.getRequest().getParameter("anio");
         String Strmes = ServletActionContext.getRequest().getParameter("mes");
         String Strfechdf = ServletActionContext.getRequest().getParameter("fechainicial");
         String Strmotivo = ServletActionContext.getRequest().getParameter("motivo");
         String staviso = "";

         mapSession = ActionContext.getContext().getSession();
         objUsuarioSesion = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);

         log.debug("Region seleccionada [" + Strregion + "]");
         Strregion = Strregion.equals("TODAS") ? "" : Strregion;

         staviso = SvrDf.saveDiafestivo(Strregion, Stranio, Strmes, Strfechdf, Strmotivo, getIdmes(), objUsuarioSesion.getUsuario());

         setAviso(staviso);

         return Action.SUCCESS;

      } catch (Exception e) {
         log.error(e.getMessage(), e);
         return Action.ERROR;
      }
   }

   public String eliminarDiafestivo() throws Exception {
      String Striddiafestivo = ServletActionContext.getRequest().getParameter("iddiafestivo");
      String Strnomregion = ServletActionContext.getRequest().getParameter("nomregion");
      int iddia = Integer.parseInt(Striddiafestivo);


      SvrDf.deleteDiafestivo(iddia);

      List<Busdiafestivo> data = SvrDf.findlisdiafestivo(Strnomregion);
      setLisBDf(data);

      return Action.SUCCESS;
   }

   public Date StrtoDate(String pformat, String pdatestr){
      Date date = null;
      SimpleDateFormat df = new SimpleDateFormat(pformat);
      try {
         date = df.parse(pdatestr);
      } catch (java.text.ParseException ex) {
        log.error(ex.getMessage(), ex);
      }
      return date;
   }

   //////////////////////////////////
   // Getters and Setters          //
   //////////////////////////////////
   public DiaFestivo getObjDf() {
      return objDf;
   }

   public void setObjDf(DiaFestivo objDf) {
      this.objDf = objDf;
   }

   public DiafestivoService getSvrDf() {
      return SvrDf;
   }

   public void setSvrDf(DiafestivoService SvrDf) {
      this.SvrDf = SvrDf;
   }

   public List<DiaFestivo> getLisDf() {
      return lisDf;
   }

   public void setLisDf(List<DiaFestivo> lisDf) {
      this.lisDf = lisDf;
   }

   /**
    * @return the lisBDf
    */
   public List<Busdiafestivo> getLisBDf() {
      return lisBDf;
   }

   /**
    * @param lisBDf the lisBDf to set
    */
   public void setLisBDf(List<Busdiafestivo> lisBDf) {
      this.lisBDf = lisBDf;
   }

   public Integer getIdmes() {
      return idmes;
   }

   public void setIdmes(Integer idmes) {
      this.idmes = idmes;
   }

   public void setAviso(String aviso) {
      this.aviso = aviso;
   }

   public String getAviso() {
      return aviso;
   }
}
