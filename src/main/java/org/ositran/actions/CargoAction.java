/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.actions;

import com.btg.ositran.siged.domain.Cargo;
import com.btg.ositran.siged.domain.Datos;
import com.btg.ositran.siged.domain.Envio;
import com.btg.ositran.siged.domain.Mensajeria;
import com.btg.ositran.siged.domain.Usuario;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.ositran.services.ArchivoService;
import org.ositran.services.CargoService;
import org.ositran.services.DatosService;
import org.ositran.services.EnvioService;
import org.ositran.services.MensajeriaService;
import org.ositran.utils.Constantes;
import org.ositran.utils.MensajeriaDatos;

public class CargoAction {

   private static Logger log = Logger.getLogger(CargoAction.class);
   private CargoService SvrCr;
   private Mensajeria obMsj;
   private MensajeriaService svrMj;
   private EnvioService svrEnvio;
   private DatosService svrdatos;
   private ArchivoService archivoService;
   private MensajeriaDatos obMsjdatos;
   private Cargo objCg;
   private String strfecha;
   private String sFechaEntrega;
   private String sFechaCargo;
   private String sFechaDevCargo;
   private String sFechaDevDoc;
   private String sFechaPrimeraVisita;
   private String sFechaSegundaVisita;
   private Integer idDocumento;

   //////////////////////////////////
   // Constructors                 //
   //////////////////////////////////
   public CargoAction(CargoService SvrCr, MensajeriaService svrMj) {
      setSvrCr(SvrCr);
      setSvrMj(svrMj);
   }
   public CargoAction(CargoService SvrCr, EnvioService svrEnvio, DatosService svrdatos, MensajeriaService svrMj) {
	      setSvrCr(SvrCr);
	      setSvrMj(svrMj);
	      setSvrdatos(svrdatos);
	      setSvrEnvio(svrEnvio);
	   }
   //////////////////////////////////
   // Methods                      //
   //////////////////////////////////
   @SuppressWarnings("unused")
   public String savecargo() throws Exception {
	  log.debug("-> [Action] CargoAction - savecargo():String ");
      try {
         //fecha
         SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd HH:mm");
         String Strmensa = ServletActionContext.getRequest().getParameter("idmensajeria");
         String Strgrupo = ServletActionContext.getRequest().getParameter("grupo");
         String Strestado = ServletActionContext.getRequest().getParameter("estadocargo");

         int id = Integer.parseInt(Strmensa);
         setObjCg(getObjCg());

         for (int i = 0; i < 6; i++) {
            String fechaTemp = null;

            try {
               switch (i) {
                  case 0:
                     fechaTemp = sFechaEntrega;
                     break;
                  case 1:
                     fechaTemp = sFechaCargo;
                     break;
                  case 2:
                     fechaTemp = sFechaDevCargo;
                     break;
                  case 3:
                     fechaTemp = sFechaDevDoc;
                     break;
                  case 4:
                     fechaTemp = sFechaPrimeraVisita;
                     break;
                  case 5:
                     fechaTemp = sFechaSegundaVisita;
                     break;
               }

               if (fechaTemp!=null && fechaTemp.length() == 10) {
                  fechaTemp = fechaTemp + " 00:00";
               }

               switch (i) {
                  case 0:
                     objCg.setFechaentrega(formatoDelTexto.parse(fechaTemp));
                     break;
                  case 1:
                     objCg.setFechacargo(formatoDelTexto.parse(fechaTemp));
                     break;
                  case 2:
                     objCg.setFechadevcargo(formatoDelTexto.parse(fechaTemp));
                     break;
                  case 3:
                     objCg.setFechadevdoc(formatoDelTexto.parse(fechaTemp));
                     break;
                  case 4:
                     objCg.setPrimeravisita(formatoDelTexto.parse(fechaTemp));
                     break;
                  case 5:
                     objCg.setSegundavisita(formatoDelTexto.parse(fechaTemp));
                     break;
               }
            } catch (Exception ex) {
               log.error(ex.getMessage(), ex);
            }
         }

         Map<String, Object> mapSession = ActionContext.getContext().getSession();
         String nombrePC = (String) mapSession.get("nombrePC");

         Cargo obCg = SvrCr.saveDatos(Strmensa, Strgrupo, Strestado, objCg);
         Mensajeria obMsj = svrMj.ViewMensaje(id);
         setObMsj(obMsj);
         svrMj.guardarTrazaRecepcionCargo(objCg.getMensajeria().getIddocumento(), objCg.getMensajeria().getIdcliente(), nombrePC);
         //registrarAuditoriaCargo(obCg, Constantes.TA_RegistrarCargo, Constantes.TM_Mensajeria, Constantes.TO_Registrar);
         return "Obmejcar";

      } catch (Exception e) {
         log.error(e.getMessage(), e);

         return Action.ERROR;
      }
   }

   public String findkey() throws Exception {
	   log.debug("-> [Action] CargoAction - findkey():String ");

      try {
         String Strmensa = ServletActionContext.getRequest().getParameter("Idmen");
         int Id = Integer.parseInt(Strmensa);
         Datos Objdato = new Datos();
         Cargo Objcargo = new Cargo();
         Mensajeria Objmenj = new Mensajeria();
         Envio Objenvio = new Envio();
         Objdato = svrdatos.findId(Id);
         Objcargo = SvrCr.findkey(Id);
         Objenvio = svrEnvio.findkey(Id);
         Date fecha1 = new Date();
         SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");

         //Muestra la pantalla deshabilitada
         //cuando ya grabo el Cargo o cuando aun no ha grabado el Envio
         if (Objcargo != null || Objenvio == null || Objdato==null) {
            if (Objcargo!=null)
            	setObjCg(Objcargo);
            else setObjCg(new Cargo());
            return "Objcargox";
         }
         //Muestra la pantalla habilitada
         Objmenj = svrMj.ViewMensaje(Id);
         setObMsj(Objmenj);

         Map<String, Object> mapSession = ActionContext.getContext().getSession();
		 mapSession.put(Constantes.SESSION_UPLOAD_LIST, archivoService.getArchivoListPorDocumento(Objmenj.getIddocumento()));

         idDocumento = Objmenj.getIddocumento();

         strfecha = sdf.format(fecha1);
         return "Objmensj";

      } catch (Exception e) {
         log.error(e.getMessage(), e);

         return Action.ERROR;
      }
   }

   public String findkeyuser() throws Exception {
	   log.debug("-> [Action] CargoAction - findkeyuser():String ");
      try {
         String Strmensa = ServletActionContext.getRequest().getParameter("Idmen");
         log.debug(" idmen :"+Strmensa ) ;
         int Id = Integer.parseInt(Strmensa);
         Cargo Objcargo = new Cargo();
         Mensajeria Objmenj = new Mensajeria();
         Objcargo = null;
         Objcargo = SvrCr.findkey(Id);
         if (Objcargo != null) {

            setObjCg(Objcargo);

            return "Objcargox";
         }
         Objmenj = svrMj.ViewMensaje(Id);
          setObMsj(Objmenj);

            return "Objmensj";
         //return "Objcargox";

      } catch (Exception e) {
         log.error(e.getMessage(), e);

         return Action.ERROR;
      }
   }

   //////////////////////////////////
   // Getters and Setters          //
   //////////////////////////////////
   public void setObMsj(Mensajeria obMsj) {
      this.obMsj = obMsj;
   }

   public Mensajeria getObMsj() {
      return obMsj;
   }

   public void setObMsjdatos(MensajeriaDatos obMsjdatos) {
      this.obMsjdatos = obMsjdatos;
   }

   public MensajeriaDatos getObMsjdatos() {
      return obMsjdatos;
   }

   public void setSvrMj(MensajeriaService svrMj) {
      this.svrMj = svrMj;
   }

   public MensajeriaService getSvrMj() {
      return svrMj;
   }

   public void setSvrCr(CargoService svrCr) {
      SvrCr = svrCr;
   }

   public CargoService getSvrCr() {
      return SvrCr;
   }

   public void setObjCg(Cargo objCg) {
      this.objCg = objCg;
   }

   public Cargo getObjCg() {
      return objCg;
   }

   public void setStrfecha(String strfecha) {
      this.strfecha = strfecha;
   }

   public String getStrfecha() {
      return strfecha;
   }

//	@SuppressWarnings("unchecked")
//	private void registrarAuditoriaCargo(Cargo cargo, String tipoauditoria, String modulo, String opcion) {
//      /*******************************************************/
//      //		Registrando la auditoria
//      /*******************************************************/
//      AuditoriaDAO daoauditoria = (AuditoriaDAO) WebApplicationContextUtils.getWebApplicationContext(ServletActionContext.getRequest().getSession().getServletContext()).getBean("auditoriaDAO");
//      Map<String,Object> session = ActionContext.getContext().getSession();
//      Usuario usuario = (Usuario) session.get(Constantes.SESSION_USUARIO);
//
//      /*******************************************************/
//      Auditoria ObjAuditoria = new Auditoria();
//      ObjAuditoria.setTipoAuditoria(tipoauditoria);
//      ObjAuditoria.setModulo(modulo);
//      ObjAuditoria.setOpcion(opcion);
//      ObjAuditoria.setFechaAudioria(new Date());
//      ObjAuditoria.setUsuario(usuario.getUsuario());
//      ObjAuditoria.setCampo("Peso Doc");
//      ObjAuditoria.setNuevoValor(cargo.getPesodcmto());
//
//      if (cargo.getPesodcmto().equals("") != true) {
//         daoauditoria.SaveAuditoria(ObjAuditoria);
//      }
//      /******************************************************/
//      ObjAuditoria = null;
//      ObjAuditoria = new Auditoria();
//      ObjAuditoria.setTipoAuditoria(tipoauditoria);
//      ObjAuditoria.setModulo(modulo);
//      ObjAuditoria.setOpcion(opcion);
//      ObjAuditoria.setFechaAudioria(new Date());
//      ObjAuditoria.setUsuario(usuario.getUsuario());
//      ObjAuditoria.setCampo("Fecha Entrega");
//      SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss aaa");
//      ObjAuditoria.setNuevoValor(sdf.format(cargo.getFechaentrega()));
//      if (cargo.getFechaentrega() != null) {
//         daoauditoria.SaveAuditoria(ObjAuditoria);
//      }
//
//      /******************************************************/
//      ObjAuditoria = null;
//      ObjAuditoria = new Auditoria();
//      ObjAuditoria.setTipoAuditoria(tipoauditoria);
//      ObjAuditoria.setModulo(modulo);
//      ObjAuditoria.setOpcion(opcion);
//      ObjAuditoria.setFechaAudioria(new Date());
//      ObjAuditoria.setUsuario(usuario.getUsuario());
//      ObjAuditoria.setCampo("Recibido Por");
//      ObjAuditoria.setNuevoValor(cargo.getRecibido());
//      if (cargo.getRecibido().equals("") != true) {
//         daoauditoria.SaveAuditoria(ObjAuditoria);
//      }
//      /******************************************************/
//      ObjAuditoria = null;
//      ObjAuditoria = new Auditoria();
//      ObjAuditoria.setTipoAuditoria(tipoauditoria);
//      ObjAuditoria.setModulo(modulo);
//      ObjAuditoria.setOpcion(opcion);
//      ObjAuditoria.setFechaAudioria(new Date());
//      ObjAuditoria.setUsuario(usuario.getUsuario());
//      ObjAuditoria.setCampo("Fecha Dev Cargo");
//      ObjAuditoria.setNuevoValor(sdf.format(cargo.getFechadevcargo()));
//      if (cargo.getFechadevcargo() != null) {
//         daoauditoria.SaveAuditoria(ObjAuditoria);
//      }
//      /******************************************************/
//      ObjAuditoria = null;
//      ObjAuditoria = new Auditoria();
//      ObjAuditoria.setTipoAuditoria(tipoauditoria);
//      ObjAuditoria.setModulo(modulo);
//      ObjAuditoria.setOpcion(opcion);
//      ObjAuditoria.setFechaAudioria(new Date());
//      ObjAuditoria.setUsuario(usuario.getUsuario());
//      ObjAuditoria.setCampo("1er visita");
//      ObjAuditoria.setNuevoValor(sdf.format(cargo.getPrimeravisita()));
//      if (cargo.getPrimeravisita() != null) {
//         daoauditoria.SaveAuditoria(ObjAuditoria);
//      }
//      /******************************************************/
//      ObjAuditoria = null;
//      ObjAuditoria = new Auditoria();
//      ObjAuditoria.setTipoAuditoria(tipoauditoria);
//      ObjAuditoria.setModulo(modulo);
//      ObjAuditoria.setOpcion(opcion);
//      ObjAuditoria.setFechaAudioria(new Date());
//      ObjAuditoria.setUsuario(usuario.getUsuario());
//      ObjAuditoria.setCampo("Obs 1era Visita");
//      ObjAuditoria.setNuevoValor(cargo.getObs1());
//      if (cargo.getObs1().equals("") != true) {
//         daoauditoria.SaveAuditoria(ObjAuditoria);
//      }
//      /******************************************************/
//      ObjAuditoria = null;
//      ObjAuditoria = new Auditoria();
//      ObjAuditoria.setTipoAuditoria(tipoauditoria);
//      ObjAuditoria.setModulo(modulo);
//      ObjAuditoria.setOpcion(opcion);
//      ObjAuditoria.setFechaAudioria(new Date());
//      ObjAuditoria.setUsuario(usuario.getUsuario());
//      ObjAuditoria.setCampo("2da visita");
//      ObjAuditoria.setNuevoValor(sdf.format(cargo.getSegundavisita()));
//      if (cargo.getSegundavisita() != null) {
//         daoauditoria.SaveAuditoria(ObjAuditoria);
//      }
//      /******************************************************/
//      ObjAuditoria = null;
//      ObjAuditoria = new Auditoria();
//      ObjAuditoria.setTipoAuditoria(tipoauditoria);
//      ObjAuditoria.setModulo(modulo);
//      ObjAuditoria.setOpcion(opcion);
//      ObjAuditoria.setFechaAudioria(new Date());
//      ObjAuditoria.setUsuario(usuario.getUsuario());
//      ObjAuditoria.setCampo("Obs 2da Visita");
//      ObjAuditoria.setNuevoValor(cargo.getObs2());
//      if (cargo.getObs2().equals("") != true) {
//         daoauditoria.SaveAuditoria(ObjAuditoria);
//      }
//      /******************************************************/
//      ObjAuditoria = null;
//      ObjAuditoria = new Auditoria();
//      ObjAuditoria.setTipoAuditoria(tipoauditoria);
//      ObjAuditoria.setModulo(modulo);
//      ObjAuditoria.setOpcion(opcion);
//      ObjAuditoria.setFechaAudioria(new Date());
//      ObjAuditoria.setUsuario(usuario.getUsuario());
//      ObjAuditoria.setCampo("Observaciones");
//      ObjAuditoria.setNuevoValor(cargo.getObservaciones());
//      if (cargo.getObservaciones().equals("") != true) {
//         daoauditoria.SaveAuditoria(ObjAuditoria);
//      }
//      /******************************************************/
//      ObjAuditoria = null;
//      ObjAuditoria = new Auditoria();
//      ObjAuditoria.setTipoAuditoria(tipoauditoria);
//      ObjAuditoria.setModulo(modulo);
//      ObjAuditoria.setOpcion(opcion);
//      ObjAuditoria.setFechaAudioria(new Date());
//      ObjAuditoria.setUsuario(usuario.getUsuario());
//      ObjAuditoria.setCampo("Costo envio");
//      ObjAuditoria.setNuevoValor(cargo.getCostoenvio());
//      if (cargo.getCostoenvio().equals("") != true) {
//         daoauditoria.SaveAuditoria(ObjAuditoria);
//      }
//      /******************************************************/
//      ObjAuditoria = null;
//      ObjAuditoria = new Auditoria();
//      ObjAuditoria.setTipoAuditoria(tipoauditoria);
//      ObjAuditoria.setModulo(modulo);
//      ObjAuditoria.setOpcion(opcion);
//      ObjAuditoria.setFechaAudioria(new Date());
//      ObjAuditoria.setUsuario(usuario.getUsuario());
//      ObjAuditoria.setCampo("Grupo");
//      ObjAuditoria.setNuevoValor(cargo.getGrupo());
//      if (cargo.getGrupo().equals("") != true) {
//         daoauditoria.SaveAuditoria(ObjAuditoria);
//      }
//      /******************************************************/
//      ObjAuditoria = null;
//      ObjAuditoria = new Auditoria();
//      ObjAuditoria.setTipoAuditoria(tipoauditoria);
//      ObjAuditoria.setModulo(modulo);
//      ObjAuditoria.setOpcion(opcion);
//      ObjAuditoria.setFechaAudioria(new Date());
//      ObjAuditoria.setUsuario(usuario.getUsuario());
//      ObjAuditoria.setCampo("Unidad Peso");
//      ObjAuditoria.setNuevoValor(cargo.getUnidadpeso());
//      if (cargo.getUnidadpeso().equals("") != true) {
//         daoauditoria.SaveAuditoria(ObjAuditoria);
//      }
//      /******************************************************/
//      ObjAuditoria = null;
//      ObjAuditoria = new Auditoria();
//      ObjAuditoria.setTipoAuditoria(tipoauditoria);
//      ObjAuditoria.setModulo(modulo);
//      ObjAuditoria.setOpcion(opcion);
//      ObjAuditoria.setFechaAudioria(new Date());
//      ObjAuditoria.setUsuario(usuario.getUsuario());
//      ObjAuditoria.setCampo("Estado");
//      ObjAuditoria.setNuevoValor(cargo.getEstado());
//      if (cargo.getEstado().equals("") != true) {
//         daoauditoria.SaveAuditoria(ObjAuditoria);
//      }
//      /******************************************************/
//      ObjAuditoria = null;
//      ObjAuditoria = new Auditoria();
//      ObjAuditoria.setTipoAuditoria(tipoauditoria);
//      ObjAuditoria.setModulo(modulo);
//      ObjAuditoria.setOpcion(opcion);
//      ObjAuditoria.setFechaAudioria(new Date());
//      ObjAuditoria.setUsuario(usuario.getUsuario());
//      ObjAuditoria.setCampo("Fecha cargo");
//      ObjAuditoria.setNuevoValor(sdf.format(cargo.getFechacargo()));
//      if (cargo.getPrimeravisita() != null) {
//         daoauditoria.SaveAuditoria(ObjAuditoria);
//      }
//   }

   public String getSFechaEntrega() {
      return sFechaEntrega;
   }

   public void setSFechaEntrega(String sFechaEntrega) {
      this.sFechaEntrega = sFechaEntrega;
   }

   public String getSFechaCargo() {
      return sFechaCargo;
   }

   public void setSFechaCargo(String sFechaCargo) {
      this.sFechaCargo = sFechaCargo;
   }

   public String getSFechaDevCargo() {
      return sFechaDevCargo;
   }

   public void setSFechaDevCargo(String sFechaDevCargo) {
      this.sFechaDevCargo = sFechaDevCargo;
   }

   public String getSFechaDevDoc() {
      return sFechaDevDoc;
   }

   public void setSFechaDevDoc(String sFechaDevDoc) {
      this.sFechaDevDoc = sFechaDevDoc;
   }

   public String getSFechaPrimeraVisita() {
      return sFechaPrimeraVisita;
   }

   public void setSFechaPrimeraVisita(String sFechaPrimeraVisita) {
      this.sFechaPrimeraVisita = sFechaPrimeraVisita;
   }

   public String getSFechaSegundaVisita() {
      return sFechaSegundaVisita;
   }

   public void setSFechaSegundaVisita(String sFechaSegundaVisita) {
      this.sFechaSegundaVisita = sFechaSegundaVisita;
   }

	public EnvioService getSvrEnvio() {
		return svrEnvio;
	}

	public void setSvrEnvio(EnvioService svrEnvio) {
		this.svrEnvio = svrEnvio;
	}

	public DatosService getSvrdatos() {
		return svrdatos;
	}

	public void setSvrdatos(DatosService svrdatos) {
		this.svrdatos = svrdatos;
	}
	public Integer getIdDocumento() {
		return idDocumento;
	}
	public void setIdDocumento(Integer idDocumento) {
		this.idDocumento = idDocumento;
	}
	public ArchivoService getArchivoService() {
		return archivoService;
	}
	public void setArchivoService(ArchivoService archivoService) {
		this.archivoService = archivoService;
	}


}
