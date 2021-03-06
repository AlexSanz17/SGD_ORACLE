/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.actions;

import java.text.SimpleDateFormat;
import java.util.Date;  

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.ositran.services.DatosService;
import org.ositran.services.EnvioService;
import org.ositran.services.MensajeriaService;

import com.btg.ositran.siged.domain.Datos;
import com.btg.ositran.siged.domain.Envio;
import com.btg.ositran.siged.domain.Mensajeria;
import com.opensymphony.xwork2.Action;

public class EnvioAction {

   private static Logger log = Logger.getLogger("org.ositran.actions.DiafestivoAction");
   private EnvioService SvrEn;
   private Envio obEnv;
   private Mensajeria obMsj;
   private MensajeriaService svrMj;
   private Datos obDatos;
   private DatosService svrdatos;
   private String strfecha;
    private String sFechaSalida, sFechaEntrega, sFechaDevCargo, sFechaDevArea;

   //////////////////////////////////
   // Constructors                 //
   //////////////////////////////////
   public EnvioAction(EnvioService SvrEn, MensajeriaService svrMj, DatosService svrdatos) {
      setSvrEn(SvrEn);
      setSvrMj(svrMj);
      setSvrdatos(svrdatos);
   }

   //////////////////////////////////
   // Methods                      //
   //////////////////////////////////
   public String saveenvio() throws Exception {
	   log.debug("-> [Action] EnvioAction - saveenvio():String ");
	   
      try {
         SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd HH:mm");
         String Strmensa = ServletActionContext.getRequest().getParameter("idmensajeria");
         String Strcodcourier = ServletActionContext.getRequest().getParameter("codcourier");
         String Strnomcourier = ServletActionContext.getRequest().getParameter("nomcourierx");
         String Strambitoenvio = ServletActionContext.getRequest().getParameter("ambitoenvio");
         String Strnomenvio2 = ServletActionContext.getRequest().getParameter("envio2");
         String Strunidadpeso = ServletActionContext.getRequest().getParameter("unidadpeso");

         try {
            String fechaTemp = sFechaSalida;
                String  fechaTemp1 = sFechaEntrega;
                String fechaTemp2 = sFechaDevCargo;
                String fechaTemp3 = sFechaDevArea;

            if (fechaTemp.length() == 10) {
               fechaTemp = fechaTemp + " 00:00";
            }

                 if (fechaTemp.length() == 10) {
                    fechaTemp = fechaTemp + " 00:00";
                }
                 if (fechaTemp1.length() == 10) {
                    fechaTemp1 = fechaTemp1 + " 00:00";
                }
                 if (fechaTemp2.length() == 10) {
                    fechaTemp2 = fechaTemp2 + " 00:00";

                }

                 if (fechaTemp3.length() == 10) {
                    fechaTemp3 = fechaTemp3 + " 00:00";
                }

            obEnv.setFechasalida(formatoDelTexto.parse(fechaTemp));
                obEnv.setDiasdistribuicion(formatoDelTexto.parse(fechaTemp1));
                obEnv.setDiasdevcargo(formatoDelTexto.parse(fechaTemp2));
                obEnv.setDiasdevolucion(formatoDelTexto.parse(fechaTemp3));

         } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
         }

         int id = Integer.parseInt(Strmensa);
         getObEnv().setIdmensajeria(id);

         getObEnv().setNumerocourier(Strcodcourier);
         getObEnv().setNombrecourier(Strnomcourier);

         getObEnv().setAmbitoenvio(Strambitoenvio);
         getObEnv().setNombreenvio(Strnomenvio2);
         
         getObEnv().setUnidadpeso(Strunidadpeso);

         /*Envio obenvio = */SvrEn.saveDatos(Strmensa, getObEnv());
     //ojo
         Mensajeria obMsj = svrMj.ViewMensaje(id);

         setObMsj(obMsj);
//ojo
         //setObEnv(obenvio);
         //SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss aaa");
         //registrarAuditoriaDatosEnvio(obEnv.getNumeroguia(), sdf.format(obEnv.getFechasalida()), String.valueOf(obEnv.getDiasdistribuicion()), Strcodcourier,
               //String.valueOf(obEnv.getDiasdevcargo()), String.valueOf(obEnv.getDiasdevolucion()), Constantes.TA_DatosEnvio, Constantes.TM_Mensajeria, Constantes.TO_Registrar);
         return "Obenvio";

      } catch (Exception e) {
         log.error(e.getMessage(), e);

         return Action.ERROR;
      }
   }

   public String findkey() throws Exception {
	   log.debug("-> [Action] EnvioAction - findkey():String ");
      try {
         String Strmensa = ServletActionContext.getRequest().getParameter("Idmen");
         int Id = Integer.parseInt(Strmensa);
         Envio Objenvio = new Envio();
         Datos Objdats = new Datos();
         Mensajeria objmensajeria = new Mensajeria();
         Date fecha1 = new Date();
         SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");

         Objenvio = null;
         Objenvio = SvrEn.findkey(Id);
         Objdats = svrdatos.findId(Id);
         
         //Muestra pantalla habilitada
         objmensajeria = svrMj.ViewMensaje(Id);         
         setObDatos(Objdats);
         setObMsj(objmensajeria);
         
         // Muestra pantalla deshabilitada cuando 
         // ya grabo los datos del Envio o aun no ha grabado los datos de Datos
         if (Objenvio != null || Objdats==null) {
                if (Objenvio != null) {
        		 setObEnv(Objenvio);
                } else {
                    setObEnv(new Envio());
                }
            return "Objenviox";
         }        

         strfecha = sdf.format(fecha1);

         return "Objmensaj";
         
         //return "Objenviox";
      } catch (Exception e) {
         log.error(e.getMessage(), e);

         return Action.ERROR;
      }
   }

   public String findkeyuser() throws Exception {
	   log.debug("-> [Action] EnvioAction - findkeyuser():String ");
      try {
         String Strmensa = ServletActionContext.getRequest().getParameter("Idmen");
         int Id = Integer.parseInt(Strmensa);
         Envio Objenvio = new Envio();
         Mensajeria objmensajeria = new Mensajeria();
         Datos Objdats = new Datos();
         Date fecha1 = new Date();
         SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");

         Objenvio = null;
         Objenvio = SvrEn.findkey(Id);
         objmensajeria = svrMj.ViewMensaje(Id);
         Objdats = svrdatos.findId(Id);
         setObDatos(Objdats);
         setObMsj(objmensajeria);
         
         if (Objenvio != null) {

            setObEnv(Objenvio);
            return "Objenviox";
         }

         strfecha = sdf.format(fecha1);

         return "Objmensaj";
         //return "Objenviox";
      } catch (Exception e) {
         log.error(e.getMessage(), e);

         return Action.ERROR;
      }
   }

   //////////////////////////////////
   // Getters and Setters          //
   //////////////////////////////////
   public void setSvrEn(EnvioService svrEn) {
      SvrEn = svrEn;
   }

   public EnvioService getSvrEn() {
      return SvrEn;
   }

   public void setObEnv(Envio obEnv) {
      this.obEnv = obEnv;
   }

   public Envio getObEnv() {
      return obEnv;
   }

   public void setSvrMj(MensajeriaService svrMj) {
      this.svrMj = svrMj;
   }

   public MensajeriaService getSvrMj() {
      return svrMj;
   }

   public void setObMsj(Mensajeria obMsj) {
      this.obMsj = obMsj;
   }

   public Mensajeria getObMsj() {
      return obMsj;
   }

   public void setObDatos(Datos obDatos) {
      this.obDatos = obDatos;
   }

   public Datos getObDatos() {
      return obDatos;
   }

   public void setSvrdatos(DatosService svrdatos) {
      this.svrdatos = svrdatos;
   }

   public DatosService getSvrdatos() {
      return svrdatos;
   }

   public void setStrfecha(String strfecha) {
      this.strfecha = strfecha;
   }

   public String getStrfecha() {
      return strfecha;
   }

//	@SuppressWarnings("unchecked")
//	private void registrarAuditoriaDatosEnvio(String nroGuia, String fechaSalida, String diasDistrib,
//         String currier, String diasDevCargo, String diasDevDevolucion,
//         String tipoauditoria, String modulo, String opcion) {
//      /*******************************************************/
//      //		Registrando la auditoria del Expediente
//      /*******************************************************/
//      AuditoriaDAO daoauditoria = (AuditoriaDAO) WebApplicationContextUtils.getWebApplicationContext(ServletActionContext.getRequest().getSession().getServletContext()).getBean("auditoriaDAO");
//      Map<String,Object> session = ActionContext.getContext().getSession();
//      Usuario usuario = (Usuario) session.get(Constantes.SESSION_USUARIO);
//
//      /*******************************************************/
//      Auditoria ObjAuditoriaNroGuia = new Auditoria();
//      ObjAuditoriaNroGuia.setTipoAuditoria(tipoauditoria);
//      ObjAuditoriaNroGuia.setModulo(modulo);
//      ObjAuditoriaNroGuia.setOpcion(opcion);
//      ObjAuditoriaNroGuia.setFechaAudioria(new Date());
//      ObjAuditoriaNroGuia.setUsuario(usuario.getUsuario());
//      ObjAuditoriaNroGuia.setCampo("NroGuia");
//      ObjAuditoriaNroGuia.setNuevoValor(nroGuia);
//
//      if (nroGuia.equals("") != true) {
//         daoauditoria.SaveAuditoria(ObjAuditoriaNroGuia);
//      }
//      /*******************************************************/
//      Auditoria ObjAuditoriaFechaSalida = new Auditoria();
//      ObjAuditoriaFechaSalida.setTipoAuditoria(tipoauditoria);
//      ObjAuditoriaFechaSalida.setModulo(modulo);
//      ObjAuditoriaFechaSalida.setOpcion(opcion);
//      ObjAuditoriaFechaSalida.setFechaAudioria(new Date());
//      ObjAuditoriaFechaSalida.setUsuario(usuario.getUsuario());
//      ObjAuditoriaFechaSalida.setCampo("Fecha Salida");
//      ObjAuditoriaFechaSalida.setNuevoValor(fechaSalida);
//      if (fechaSalida.equals("")) {
//         daoauditoria.SaveAuditoria(ObjAuditoriaFechaSalida);
//      }
//
//      /*******************************************************/
//      Auditoria ObjAuditoriaDiasDist = new Auditoria();
//      ObjAuditoriaDiasDist.setTipoAuditoria(tipoauditoria);
//      ObjAuditoriaDiasDist.setModulo(modulo);
//      ObjAuditoriaDiasDist.setOpcion(opcion);
//      ObjAuditoriaDiasDist.setFechaAudioria(new Date());
//      ObjAuditoriaDiasDist.setUsuario(usuario.getUsuario());
//      ObjAuditoriaDiasDist.setCampo("Dias Distribucion");
//      ObjAuditoriaDiasDist.setNuevoValor(diasDistrib);
//      if (diasDistrib.equals("") != true) {
//         daoauditoria.SaveAuditoria(ObjAuditoriaDiasDist);
//      }
//
//      /*******************************************************/
//      Auditoria ObjAuditoriaCurrier = new Auditoria();
//      ObjAuditoriaCurrier.setTipoAuditoria(tipoauditoria);
//      ObjAuditoriaCurrier.setModulo(modulo);
//      ObjAuditoriaCurrier.setOpcion(opcion);
//      ObjAuditoriaCurrier.setFechaAudioria(new Date());
//      ObjAuditoriaCurrier.setUsuario(usuario.getUsuario());
//      ObjAuditoriaCurrier.setCampo("Currier");
//      ObjAuditoriaCurrier.setNuevoValor(currier);
//      if (currier.equals("") != true) {
//         daoauditoria.SaveAuditoria(ObjAuditoriaCurrier);
//      }
//
//
//      /*******************************************************/
//      Auditoria ObjAuditoriaDiasDevCargo = new Auditoria();
//      ObjAuditoriaDiasDevCargo.setTipoAuditoria(tipoauditoria);
//      ObjAuditoriaDiasDevCargo.setModulo(modulo);
//      ObjAuditoriaDiasDevCargo.setOpcion(opcion);
//      ObjAuditoriaDiasDevCargo.setFechaAudioria(new Date());
//      ObjAuditoriaDiasDevCargo.setUsuario(usuario.getUsuario());
//      ObjAuditoriaDiasDevCargo.setCampo("Dias Dev Cargo");
//      ObjAuditoriaDiasDevCargo.setNuevoValor(diasDevCargo);
//      if (diasDevCargo.equals("") != true) {
//         daoauditoria.SaveAuditoria(ObjAuditoriaDiasDevCargo);
//      }
//      /*******************************************************/
//      Auditoria ObjAuditoriaDiasDevDevo = new Auditoria();
//      ObjAuditoriaDiasDevDevo.setTipoAuditoria(tipoauditoria);
//      ObjAuditoriaDiasDevDevo.setModulo(modulo);
//      ObjAuditoriaDiasDevDevo.setOpcion(opcion);
//      ObjAuditoriaDiasDevDevo.setFechaAudioria(new Date());
//      ObjAuditoriaDiasDevDevo.setUsuario(usuario.getUsuario());
//      ObjAuditoriaDiasDevDevo.setCampo("Dias Dev Devolucion");
//      ObjAuditoriaDiasDevDevo.setNuevoValor(diasDevDevolucion);
//      if (diasDevDevolucion.equals("") != true) {
//         daoauditoria.SaveAuditoria(ObjAuditoriaDiasDevDevo);
//      }
//   }
   public String getSFechaSalida() {
      return sFechaSalida;
   }

   public void setSFechaSalida(String sFechaSalida) {
      this.sFechaSalida = sFechaSalida;
   }

    public String getSFechaDevArea() {
        return sFechaDevArea;
    }

    public void setSFechaDevArea(String sFechaDevArea) {
        this.sFechaDevArea = sFechaDevArea;
    }

    public String getSFechaDevCargo() {
        return sFechaDevCargo;
    }

    public void setSFechaDevCargo(String sFechaDevCargo) {
        this.sFechaDevCargo = sFechaDevCargo;
    }

    public String getSFechaEntrega() {
        return sFechaEntrega;
    }

    public void setSFechaEntrega(String sFechaEntrega) {
        this.sFechaEntrega = sFechaEntrega;
    }
}
