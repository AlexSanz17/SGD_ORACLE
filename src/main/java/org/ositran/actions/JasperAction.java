/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.actions;

import com.btg.ositran.siged.domain.Documento;
import com.btg.ositran.siged.domain.Mensajeria;
import com.btg.ositran.siged.domain.Usuario;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import net.sf.jasperreports.engine.JasperCompileManager;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.ositran.pojos.jasper.CargoReporte;
import org.ositran.services.DocumentoService;
import org.ositran.services.MensajeriaService;
import org.ositran.services.UsuarioService;
import org.ositran.utils.Constantes;



public class JasperAction extends ActionSupport {
	
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(JasperAction.class);
	private Documento documento;
	private CargoReporte cargo;
	private DocumentoService documentoService;
	private MensajeriaService mensajeriaService;
	private UsuarioService usuarioService;
	private Integer idDocumento;

	private String fechaDesde ;
	private String fechaHasta ;
	private String horaDesde ;
	private String horaHasta ;
	private List<Mensajeria> listaMensajeria= new ArrayList<Mensajeria>();
	private Mensajeria mensajeria ;
	private Map<String,Object> params;
	private Map<String,Object> mapSession;
	
//   @Override
//   public String execute() throws Exception {
//      return Action.SUCCESS;
//   }

   public String generateCargo() throws Exception {
      File jasperFile = null;
      StringBuilder sbJRXML = new StringBuilder(ServletActionContext.getServletContext().getRealPath("/jasper")).append("/").append("cargo.jrxml");
      StringBuilder sbJASPER = new StringBuilder(ServletActionContext.getServletContext().getRealPath("/jasper")).append("/").append("cargo.jasper");

      if (idDocumento == null) {
         log.error("No se recibio idDocumento");

         return Action.ERROR;
      }

      try {
         if (log.isDebugEnabled()) {
            log.debug("Se recibio idDocumento [" + idDocumento + "]");
            log.debug("sbJRXML [" + sbJRXML.toString() + "]");
            log.debug("sbJASPER [" + sbJASPER.toString() + "]");
         }

         //documento = documentoService.findByIdDocumento(idDocumento);
         cargo =documentoService.obtenerCargo(idDocumento);
         if (log.isDebugEnabled()) {
            log.debug("Se encontro documento [" + cargo.getTipodocumento() + " - " + cargo.getNrodocumento() + "]");
         }

         jasperFile = new File(sbJASPER.toString());

         if (log.isDebugEnabled()) {
            log.debug("jasperFile [" + jasperFile.getAbsolutePath() + "]");
         }

         if (!jasperFile.exists()) {
            log.info("Compilando [" + sbJRXML.toString() + "]");
            JasperCompileManager.compileReportToFile(sbJRXML.toString(), sbJASPER.toString());
            
            
         }
      } catch (Exception e) {
    	  e.printStackTrace();
         log.error(e.getMessage(), e);

         return Action.ERROR;
      }

      return Action.SUCCESS;
   }

	private boolean checkErrorFechas(){
		/**
		 * Este error es causado cuando hay un conflicto entre el nombre de variables
		 * que son usadas en el reporte. Para que desaparezca debes revisar el nombre
		 * de las variables de los javascript y JSP.
		 */
		if((fechaDesde.equals("")&&fechaHasta.equals(""))||(fechaDesde==null&&fechaHasta==null)){
			return true;
		}
		SimpleDateFormat fechita = new SimpleDateFormat("dd/MM/yyyy");
		try{
			fechita.parse(fechaDesde);
		}catch(ParseException e){
			log.warn("ERROR!, ha ocurrido un error con el campo FechaDesde y se han desactivado los filtros");
			return false;
		}
		try{
			fechita.parse(fechaHasta);
		}catch(ParseException e){
			log.warn("ERROR!, ha ocurrido un error con el campo FechaHasta y se han desactivado los filtros");
			return false;
		}
		return true;
	}
	
   public String generateReporteMensajeria() throws Exception {
	   
	  	File jasperFile = null;
		StringBuilder sbJRXML = new StringBuilder(ServletActionContext.getServletContext().getRealPath("/jasper")).append("/").append("mensajeria.jrxml");
		StringBuilder sbJASPER = new StringBuilder(ServletActionContext.getServletContext().getRealPath("/jasper")).append("/").append("mensajeria.jasper");
		//Format formatoFecha=new SimpleDateFormat("dd/MM/yyyy"); 
		
	    if (fechaDesde==null || fechaHasta==null || horaDesde==null || horaHasta==null){
		     log.error("No se las horas o fechas correctas");
		     return Action.ERROR;	    	  
	    }	     

	    try {

	        Usuario objUsuario= new Usuario();
	 		if (checkErrorFechas()) {
				mapSession = ActionContext.getContext().getSession();
				objUsuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
				listaMensajeria = mensajeriaService.filtrarCriterios(objUsuario,fechaDesde, fechaHasta, horaDesde, horaHasta);
				
				if(listaMensajeria!=null && listaMensajeria.size()>0){
					for(int i= 0; i<listaMensajeria.size(); i++){
						listaMensajeria.get(i).setTipodocumento(listaMensajeria.get(i).getTipodocumento()+"-"+listaMensajeria.get(i).getNumerodocumento());
					}
				}
				params = new HashMap<String,Object>();				
				params.put("fechaDesde",fechaDesde.toString()+" "+horaDesde.toString());
				params.put("fechaHasta",fechaHasta.toString()+" "+horaHasta.toString());
				StringBuilder remitente = new StringBuilder().append(objUsuario.getNombres()).append(" ").append(objUsuario.getApellidos());
				params.put("remitente",remitente.toString());
				objUsuario = usuarioService.findByIdUsuario(objUsuario.getIdusuario());
				params.put("dependencia",objUsuario.getUnidad()!=null ? objUsuario.getUnidad().getNombre().toString() : "");
				params.put("nrodocumentos", listaMensajeria!=null && listaMensajeria.size()>0 ? String.valueOf(listaMensajeria.size()) : "0");
			}
	 		
	         if (log.isDebugEnabled()) {
	            log.debug("Se encontraron "+ listaMensajeria.size()+" documentos en mensajeria para el usuario " + objUsuario.getNombres());
	         }

	         jasperFile = new File(sbJASPER.toString());

	         if (log.isDebugEnabled()) {
	            log.debug("jasperFile [" + jasperFile.getAbsolutePath() + "]");
	         }

	         if (!jasperFile.exists()) {
	            log.info("Compilando [" + sbJRXML.toString() + "]");
	            JasperCompileManager.compileReportToFile(sbJRXML.toString(), sbJASPER.toString());
	            net.sf.jasperreports.engine.export.JRXlsExporter obj= new net.sf.jasperreports.engine.export.JRXlsExporter();
	            
	         }
	    } catch (Exception e) {
	         log.error(e.getMessage(), e);

	         return Action.ERROR;
	    }

	      return Action.SUCCESS;
	   }   
   /*
    * Getters & Setters
    */
   public Documento getDocumento() {
      return documento;
   }

   public void setDocumento(Documento documento) {
      this.documento = documento;
   }

   public DocumentoService getDocumentoService() {
      return documentoService;
   }

   public void setDocumentoService(DocumentoService documentoService) {
      this.documentoService = documentoService;
   }

   public Integer getIdDocumento() {
      return idDocumento;
   }

   public void setIdDocumento(Integer idDocumento) {
      this.idDocumento = idDocumento;
   }

	public String getFechaDesde() {
		return fechaDesde;
	}
	
	public void setFechaDesde(String fechaDesde) {
		this.fechaDesde = fechaDesde;
	}
	
	public String getFechaHasta() {
		return fechaHasta;
	}
	
	public void setFechaHasta(String fechaHasta) {
		this.fechaHasta = fechaHasta;
	}
	
	public String getHoraDesde() {
		return horaDesde;
	}
	
	public void setHoraDesde(String horaDesde) {
		this.horaDesde = horaDesde;
	}
	
	public String getHoraHasta() {
		return horaHasta;
	}
	
	public void setHoraHasta(String horaHasta) {
		this.horaHasta = horaHasta;
	}

	public MensajeriaService getMensajeriaService() {
		return mensajeriaService;
	}

	public void setMensajeriaService(MensajeriaService mensajeriaService) {
		this.mensajeriaService = mensajeriaService;
	}

	public List<Mensajeria> getListaMensajeria() {
		return listaMensajeria;
	}

	public void setListaMensajeria(List<Mensajeria> listaMensajeria) {
		this.listaMensajeria = listaMensajeria;
	}

	public Mensajeria getMensajeria() {
		return mensajeria;
	}

	public void setMensajeria(Mensajeria mensajeria) {
		this.mensajeria = mensajeria;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public UsuarioService getUsuarioService() {
		return usuarioService;
	}

	public void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	public CargoReporte getCargo() {
		return cargo;
	}

	public void setCargo(CargoReporte cargo) {
		this.cargo = cargo;
	}
   
}
