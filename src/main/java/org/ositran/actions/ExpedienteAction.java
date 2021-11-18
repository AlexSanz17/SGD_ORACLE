/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.actions;

import com.btg.ositran.siged.domain.Documento;
import com.btg.ositran.siged.domain.Expediente;
import com.btg.ositran.siged.domain.Usuario;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.ositran.services.DocumentoService;
import org.ositran.services.ExpedienteService;
import org.ositran.services.RepositorioService;
import org.ositran.services.UsuarioService;
import org.ositran.utils.Constantes;
import org.ositran.utils.Expedienfindadvance;
import org.ositran.utils.ExpedienteList;
import org.ositran.utils.ExpedienteSearch;

public class ExpedienteAction{
	
	private static Logger log=Logger.getLogger(ExpedienteAction.class);
	private Boolean bCerrarVentana;
	private DocumentoService srvDocumento;
	private Expediente objExpediente;
	private ExpedienteService srvE;
	private ExpedienteSearch objES;
	private Expedienfindadvance objEfa;
	private Integer iIdDocumento;
	private List<ExpedienteList> lstEL;
	private List<Expediente> lstBEA;
	private List<Expedienfindadvance> lstinner;
	private List<Expediente> lstExpediente;
	private RepositorioService srvRep;
	private UsuarioService usuarioService;
	private String sParametroBusqueda;
	/** Campos de la busqueda avanzada **/
	// Por German Enriquez
	private String numeroExpediente;
	private String numeroDocumento;
	private String numeroMesaPartes;
	private String tipoDocumento;
	private String concesionario;
	private String cliente;
	private String areaDestino;
	private String propietario;
	private String proceso;
	private String contenido;
	private String tipoBusqueda;
	private String identidadConcesionario;
	private String direccionCliente;
	private String fechaInicioDocumento;
	private String fechaFinDocumento;
	private String fechaInicioExpediente;
	private String fechaFinExpediente;

	// ////////////////////////////////
	// Constructors //
	// ////////////////////////////////
	public ExpedienteAction(ExpedienteService srvE){
		setSrvE(srvE);
	}

	// ////////////////////////////////
	// Methods //
	// ////////////////////////////////
	public String findByCriteria() throws Exception{
		Map<String,Object> session = ActionContext.getContext().getSession();
		Usuario usuario = (Usuario)session.get(Constantes.SESSION_USUARIO);
		
		setLstEL(getSrvE().findByCriteria(getObjES().getStrNroIdentificacion(),getObjES().getStrRazonSocial(),getObjES().getSNroExpediente(),getObjES().getStrAsunto(), usuario.getIdusuario()));
		return Action.SUCCESS;
	}

	public String filtrarExpediente() throws Exception{
		if(objExpediente==null){
			log.debug("No se recibio ningun filtro de busqueda de Expediente");
			return Action.ERROR;
		}
		try{
			lstExpediente=srvE.filtrarExpediente(objExpediente);
			log.debug("Nro de Expedientes filtrados ["+lstExpediente.size()+"]");
			return Action.SUCCESS;
		}catch(Exception e){
			log.error(e.getMessage(),e);
			return Action.ERROR;
		}
	}

	public String findbyNroDoc(){
		String NrDoc=getObjES().getStrNrodocu();
		log.debug("NrDoc:"+NrDoc);
		List<Expediente> data=getSrvE().findbyNroExpediente(NrDoc);
		setLstBEA(data);
		log.debug("Total Data:"+data.size());
		return Action.SUCCESS;
	}

	public String findbyInner(){
		// String qq =
		// ServletActionContext.getRequest().getParameter("busqueda");
		// String NrDoc = getObjEfa().getExp_referencia();
		log.debug("Parametro de Busqueda ["+sParametroBusqueda+"]");
		List<Expedienfindadvance> data2=getSrvE().findbyadvancedinn(sParametroBusqueda);
		if(data2!=null){
			log.debug("Resultado ["+data2.size()+"]");
		}
		setLstinner(data2);
		return Action.SUCCESS;
	}

	public String findbyadvanced(){
		String Varis=getObjEfa().getExp_referencia();
		List<Expedienfindadvance> data=getSrvE().findbyadvancedinn(Varis);
		setLstinner(data);
		return Action.SUCCESS;
	}

	public String findsuperbyadvanced()/* throws IOException, ServletException, ParseException */{
      //Map<String,Object> session = ActionContext.getContext().getSession();

      if(tipoBusqueda==null){
    	  tipoBusqueda="AND";
      }
      //List<Expedienfindadvance> data = getSrvE().findbysuperadvanced(alfresco,numeroExpediente,numeroDocumento,numeroMesaPartes,tipoDocumento,concesionario,cliente,areaDestino,propietario,proceso,contenido,tipoBusqueda);
      List<Expedienfindadvance> data = null;

      setLstinner(data);

      return Action.SUCCESS;
   }
	
	public String findsuperbyadvanced2()/* throws IOException, ServletException */{
		/*
		 * String Strnroexp = numeroExpediente; String Strtipdoc =
		 * tipoDocumento; String Strnrodoc = numeroDocumento; String Strdoccor =
		 * ServletActionContext.getRequest().getParameter("txtdocidecorr");
		 * String Strmparte = numeroMesaPartes; String Strfinidoc =
		 * ServletActionContext.getRequest().getParameter("finidoc"); String
		 * Strffindoc =
		 * ServletActionContext.getRequest().getParameter("ffindoc"); String
		 * Strrzcorr = concesionario; String Strfiniexp =
		 * ServletActionContext.getRequest().getParameter("finiexp"); String
		 * Strffinexp =
		 * ServletActionContext.getRequest().getParameter("ffinexp"); String
		 * Strcliente = cliente; String Strdirclie =
		 * ServletActionContext.getRequest().getParameter("txtdireprinci");
		 * String Strareades = areaDestino; String Strpropiet = propietario;
		 * String Strproceso = proceso; String Strtipbus = tipoBusqueda;
		 * 
		 * 
		 * //extras las de jona
		 * 
		 * String Strcontenido=contenido;
		 * 
		 * //////////////subida de selectores/////////////////////
		 * 
		 * 
		 * String condic[] = new String[20]; int i; if
		 * (Strtipbus.equalsIgnoreCase("and")) { for (i = 0; i < 15; i++) {
		 * condic[i] = "AND"; } } if (Strtipbus.equalsIgnoreCase("or")) { for (i
		 * = 0; i < 15; i++) { condic[i] = "OR"; } }
		 */
		//Map<String,Object> session=ActionContext.getContext().getSession();
		// Integer iIdUsuario = (Integer) session.get("idusuario");
		if(tipoBusqueda==null){
			tipoBusqueda="AND";
		}
		//List<Expedienfindadvance> data=getSrvE().busquedaAvanzadaAdicional(alfresco,numeroExpediente,tipoDocumento,numeroDocumento,identidadConcesionario,numeroMesaPartes,fechaInicioDocumento,fechaFinDocumento,concesionario,fechaInicioExpediente,fechaFinExpediente,cliente,direccionCliente,areaDestino,propietario,proceso,contenido,tipoBusqueda);
      List<Expedienfindadvance> data = null;
		setLstinner(data);
		/*
		 * RequestDispatcher rd =
		 * ServletActionContext.getServletContext().getRequestDispatcher
		 * ("/pages/tramite/docRecibidosBusq_2.jsp");
		 * ServletActionContext.getRequest().setAttribute("data", rd);
		 * rd.forward(ServletActionContext.getRequest(),
		 * ServletActionContext.getResponse());
		 */
		return Action.SUCCESS;
	}

	public String goAnular() throws Exception{
		try{
			Documento objDocumento=null;
			if(getIIdDocumento()==null){
				log.error("El ID del Documento es ["+getIIdDocumento()+"]");
				return Action.ERROR;
			}
			if((objDocumento=getSrvDocumento().findByIdDocumento(getIIdDocumento()))==null){
				log.error("No se encontro documento con ID ["+getIIdDocumento()+"]");
				return Action.ERROR;
			}
			setObjExpediente(objDocumento.getExpediente());
			if(getObjExpediente()==null){
				log.error("No hay expediente asociado al documento con ID ["+getIIdDocumento()+"]");
				return Action.ERROR;
			}
			return Action.SUCCESS;
		}catch(Exception e){
			log.error(e.getMessage(),e);
			return Action.ERROR;
		}
	}

	public String doAnular() throws Exception{
		try{
			if(getObjExpediente()==null){
				log.error("El expediente es ["+getObjExpediente()+"]");
				return Action.ERROR;
			}
			if(getObjExpediente().getIdexpediente()==null){
				log.error("No hay expediente a anular ID ["+getObjExpediente().getIdexpediente()+"]");
				return Action.ERROR;
			}
			getSrvE().anularExpediente(getObjExpediente());
			setBCerrarVentana(Boolean.TRUE);
			return Action.SUCCESS;
		}catch(Exception e){
			log.error(e.getMessage(),e);
			return Action.ERROR;
		}
	}
	
	// ////////////////////////////////
	// Getters and Setters //
	// ////////////////////////////////
	public Boolean getBCerrarVentana(){
		return bCerrarVentana;
	}

	public void setBCerrarVentana(Boolean bCerrarVentana){
		this.bCerrarVentana=bCerrarVentana;
	}

	public DocumentoService getSrvDocumento(){
		return srvDocumento;
	}

	public void setSrvDocumento(DocumentoService srvDocumento){
		this.srvDocumento=srvDocumento;
	}

	public Expediente getObjExpediente(){
		return objExpediente;
	}

	public void setObjExpediente(Expediente objExpediente){
		this.objExpediente=objExpediente;
	}

	public ExpedienteService getSrvE(){
		return srvE;
	}

	public void setSrvE(ExpedienteService srvE){
		this.srvE=srvE;
	}

	public ExpedienteSearch getObjES(){
		return objES;
	}

	public void setObjES(ExpedienteSearch objES){
		this.objES=objES;
	}

	public Expedienfindadvance getObjEfa(){
		return objEfa;
	}

	public void setObjEfa(Expedienfindadvance objEfa){
		this.objEfa=objEfa;
	}

	public Integer getIIdDocumento(){
		return iIdDocumento;
	}

	public void setIIdDocumento(Integer iIdDocumento){
		this.iIdDocumento=iIdDocumento;
	}

	public List<ExpedienteList> getLstEL(){
		return lstEL;
	}

	public void setLstEL(List<ExpedienteList> lstEL){
		this.lstEL=lstEL;
	}

	public List<Expediente> getLstBEA(){
		return lstBEA;
	}

	public void setLstBEA(List<Expediente> lstBEA){
		this.lstBEA=lstBEA;
	}

	public List<Expedienfindadvance> getLstinner(){
		return lstinner;
	}

	public void setLstinner(List<Expedienfindadvance> lstinner){
		this.lstinner=lstinner;
	}

	public RepositorioService getSrvRep(){
		return srvRep;
	}

	public void setSrvRep(RepositorioService srvRep){
		this.srvRep=srvRep;
	}

	public void setUsuarioService(UsuarioService usuarioService){
		this.usuarioService=usuarioService;
	}

	public List<Expediente> getLstExpediente(){
		return lstExpediente;
	}

	public void setLstExpediente(List<Expediente> lstExpediente){
		this.lstExpediente=lstExpediente;
	}

	public String getNumeroExpediente(){
		return numeroExpediente;
	}

	public void setNumeroExpediente(String numeroExpediente){
		this.numeroExpediente=numeroExpediente;
	}

	public String getNumeroDocumento(){
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento){
		this.numeroDocumento=numeroDocumento;
	}

	public String getNumeroMesaPartes(){
		return numeroMesaPartes;
	}

	public void setNumeroMesaPartes(String numeroMesaPartes){
		this.numeroMesaPartes=numeroMesaPartes;
	}

	public String getTipoDocumento(){
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento){
		this.tipoDocumento=tipoDocumento;
	}

	public String getAreaDestino(){
		return areaDestino;
	}

	public void setAreaDestino(String areaDestino){
		this.areaDestino=areaDestino;
	}

	public String getPropietario(){
		return propietario;
	}

	public void setPropietario(String propietario){
		this.propietario=propietario;
	}

	public String getProceso(){
		return proceso;
	}

	public void setProceso(String proceso){
		this.proceso=proceso;
	}

	public String getContenido(){
		return contenido;
	}

	public void setContenido(String contenido){
		this.contenido=contenido;
	}

	public String getTipoBusqueda(){
		return tipoBusqueda;
	}

	public void setTipoBusqueda(String tipoBusqueda){
		this.tipoBusqueda=tipoBusqueda;
	}

	public String getIdentidadConcesionario(){
		return identidadConcesionario;
	}

	public void setIdentidadConcesionario(String identidadConcesionario){
		this.identidadConcesionario=identidadConcesionario;
	}

	public String getDireccionCliente(){
		return direccionCliente;
	}

	public void setDireccionCliente(String direccionCliente){
		this.direccionCliente=direccionCliente;
	}

	public String getFechaInicioDocumento(){
		return fechaInicioDocumento;
	}

	public String getConcesionario(){
		return concesionario;
	}

	public void setConcesionario(String concesionario){
		this.concesionario=concesionario;
	}

	public void setFechaInicioDocumento(String fechaInicioDocumento){
		this.fechaInicioDocumento=fechaInicioDocumento;
	}

	public String getFechaFinDocumento(){
		return fechaFinDocumento;
	}

	public void setFechaFinDocumento(String fechaFinDocumento){
		this.fechaFinDocumento=fechaFinDocumento;
	}

	public String getFechaInicioExpediente(){
		return fechaInicioExpediente;
	}

	public void setFechaInicioExpediente(String fechaInicioExpediente){
		this.fechaInicioExpediente=fechaInicioExpediente;
	}

	public String getFechaFinExpediente(){
		return fechaFinExpediente;
	}

	public void setFechaFinExpediente(String fechaFinExpediente){
		this.fechaFinExpediente=fechaFinExpediente;
	}

	public String getCliente(){
		return cliente;
	}

	public void setCliente(String cliente){
		this.cliente=cliente;
	}

	public String getsParametroBusqueda(){
		return sParametroBusqueda;
	}

	public void setsParametroBusqueda(String sParametroBusqueda){
		this.sParametroBusqueda=sParametroBusqueda;
	}

	public UsuarioService getUsuarioService() {
		return usuarioService;
	}
}
