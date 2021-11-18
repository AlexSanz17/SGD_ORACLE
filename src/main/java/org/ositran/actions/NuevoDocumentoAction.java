/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.actions;

import com.btg.ositran.siged.domain.Archivo;
import com.btg.ositran.siged.domain.ArchivoPendiente;
import com.btg.ositran.siged.domain.ArchivoTemporal;
import com.btg.ositran.siged.domain.Campo;
import com.btg.ositran.siged.domain.Cliente;
import com.btg.ositran.siged.domain.Documento;
import com.btg.ositran.siged.domain.DocumentoDerivacion;
import com.btg.ositran.siged.domain.DocumentoReunion;
import com.btg.ositran.siged.domain.Expediente;
import com.btg.ositran.siged.domain.IotdtmDocExterno;
import com.btg.ositran.siged.domain.LegajoDocumento;
import com.btg.ositran.siged.domain.Parametro;
import com.btg.ositran.siged.domain.Plantilla;
import com.btg.ositran.siged.domain.Tipodocumento;
import com.btg.ositran.siged.domain.Tipoidentificacion;
import com.btg.ositran.siged.domain.Usuario;   
import com.btg.ositran.siged.domain.UsuarioDerivacion;
import com.btg.ositran.siged.domain.Valorcampo;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.ositran.ws.MigraCarnetdeExtrajeria;
import com.ositran.ws.PeticionConsulta;
import com.ositran.ws.ReniecConsultaDni;
import com.ositran.ws.RespuestaBean;
import com.ositran.ws.ResultadoConsulta;
import com.ositran.ws.SolicitudBean;
import gob.ositran.siged.config.SigedProperties;
import gob.ositran.siged.util.MessagePropertiesEnum;
import gob.ositran.siged.util.SigedMessageSource;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.alfresco.webservice.util.AuthenticationDetails;
import org.alfresco.webservice.util.AuthenticationUtils;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.json.JSONObject;
import org.ositran.common.alfresco.AuthThreadLocalHolder;
import org.ositran.daos.DocumentoDerivacionDAO;
import org.ositran.daos.DocumentoExternoVirtualDAO;
import org.ositran.daos.DocumentoReunionDAO;
import org.ositran.services.AccionService;
import org.ositran.services.ArchivoService;
import org.ositran.services.ArchivopendienteService;
import org.ositran.services.ClienteService;
import org.ositran.services.ConcesionarioService;
import org.ositran.services.DocumentoReferenciaService;
import org.ositran.services.DocumentoService;
import org.ositran.services.ExpedienteService;
import org.ositran.services.LegajoDocumentoService;
import org.ositran.services.ManejoDeEmailService;
import org.ositran.services.NotificacionService;
import org.ositran.services.ParametroService;
import org.ositran.services.PlantillaService;
import org.ositran.services.ProcesoService;
import org.ositran.services.RepositorioService;
import org.ositran.services.RolService;
import org.ositran.services.TipodocumentoService;
import org.ositran.services.TipoidentificacionService;
import org.ositran.services.TrazabilidaddocumentoService;
import org.ositran.services.UsuarioService;
import org.ositran.utils.Constantes;
import org.ositran.utils.DocumentoDetail;
import org.ositran.utils.SigedUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.TaskExecutor;

public class NuevoDocumentoAction extends ActionSupport implements ServletRequestAware, ServletResponseAware {

	private static final long serialVersionUID=1865395431048493367L;
	private static Logger log=LoggerFactory.getLogger(NuevoDocumentoAction.class);
        private List<Documento> listaDocReferenciados;
        private List<DocumentoReunion> listaIntegrantesInternos;
        private List<DocumentoReunion> listaIntegrantesExternos;   
        private List<UsuarioDerivacion> listaDerivacionPara;
        private List<UsuarioDerivacion> listaDerivacionCC;
	private boolean bBandeja;
	private int narchivos;
	private Date fecha ;
	private Integer idcliente;
	private Integer iddestinatario;
	private Integer idcorrentista;
	private Integer idtipoidentificacion;
	private Integer idarchivos[];
	private Integer iIdNotificacion;
        private String tipoTransaccion;
	private String fullFileName;
	private String uploadContentType;// The content type of the file
	private String uploadFileName;// The uploaded file name
	private String fileCaption;// The caption of the file entered by user
	private String tipodocumento;
	private String cargarData;
	private String rutapadre;
        private DocumentoReferenciaService documentoReferenciaService;
        private DocumentoDerivacionDAO documentoDerivacionDAO;
	private String mensaje;
	private String correntista;
	private String strDireccion;
	private String strCorreoConcesionario;
	private String strResponsable;
	private String origenExpediente;
	private String versionar;
	private String tiponumeracion ;
	private String enumerarDocumento ;
	private String origenDerivacion;
	private List<String> condestinatarios;
	private List<String> concopias;
        private IotdtmDocExterno recepcionVirtual;
	private ArchivopendienteService archivoPendienteService;
	private DocumentoService documentoService;
	private PlantillaService plantillaService;
	private RepositorioService repositorioService;
        private LegajoDocumentoService legajoDocumentoService;
	private UsuarioService usuarioService;
	private ArchivoService archivoService;
	private ConcesionarioService concesionarioService;
	private ClienteService clienteService;
	private ProcesoService procesoService;
	private TipodocumentoService tipodocumentoService;
	private TipoidentificacionService tipoidentificacionService;
	private ExpedienteService expedienteService;
	private AccionService accionService;
	private TrazabilidaddocumentoService trazabilidaddocumentoService;
	private ParametroService parametroService;
	private NotificacionService srvNotificacion;
	private TaskExecutor taskExecutor;
	private ManejoDeEmailService mailService;
	private SigedMessageSource messageSource;
	private RolService rolService;
        private DocumentoReunionDAO documentoReunionDAO;
	private Documento objDocumento;
	private Documento objDocumentoPrincipal;
	private Expediente expediente;
	private List<Documento> listaDocumentos;
	private List<Tipoidentificacion> lstRadio;
	private List<Parametro> listaParametros;
	private List<Documento> listReferencias;
	private Integer idINFDocumento;
        private DocumentoExternoVirtualDAO documentoExternoVirtualDAO;
        private DocumentoDetail objDD;
	private Integer idDocumento;
	private Integer idExpediente;
	private Integer idArchivoPendiente;
	private Documento documento;
	private Integer idPlantilla;
	private Integer idproceso;
	private Integer idtipodocumento;
	private String rutaArchivo;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private String cerrar;
	private ArchivoPendiente archivopendiente;
	private Date atenderAntesDe;
	private String asunto;
	private String departamento;
	private Integer iddepartamento;
	private String provincia;
	private Integer idprovincia;
	private String distrito;
	private Integer iddistrito;
	private String strTelefonoCliente;
	private String strCorreoCliente;
	private String proceso;
	private String strUnidad;
	private String ocultar;
	private Integer idtipoid;
	private Boolean paraEnumerarArea;
	private Boolean paraEnumerarGerencia;
	private Boolean paraEnumerarPresidencia;
        private Integer codigoVirtual;
        private String flagVerExpediente;

        public String getFlagVerExpediente() {
            return flagVerExpediente;
        }

        public void setFlagVerExpediente(String flagVerExpediente) {
            this.flagVerExpediente = flagVerExpediente;
        }
        
        public LegajoDocumentoService getLegajoDocumentoService() {
            return legajoDocumentoService;
        }

        public void setLegajoDocumentoService(LegajoDocumentoService legajoDocumentoService) {
            this.legajoDocumentoService = legajoDocumentoService;
        }

        public Integer getCodigoVirtual() {
            return codigoVirtual;
        }

        public void setCodigoVirtual(Integer codigoVirtual) {
            this.codigoVirtual = codigoVirtual;
        }
        
        public IotdtmDocExterno getRecepcionVirtual() {
            return recepcionVirtual;
        }

        public void setRecepcionVirtual(IotdtmDocExterno recepcionVirtual) {
            this.recepcionVirtual = recepcionVirtual;
        }

        public DocumentoExternoVirtualDAO getDocumentoExternoVirtualDAO() {
            return documentoExternoVirtualDAO;
        }

        public void setDocumentoExternoVirtualDAO(DocumentoExternoVirtualDAO documentoExternoVirtualDAO) {
            this.documentoExternoVirtualDAO = documentoExternoVirtualDAO;
        }
        
        public DocumentoDerivacionDAO getDocumentoDerivacionDAO() {
           return documentoDerivacionDAO;
        }

        public void setDocumentoDerivacionDAO(DocumentoDerivacionDAO documentoDerivacionDAO) {
            this.documentoDerivacionDAO = documentoDerivacionDAO;
        }


	public Integer getIdINFDocumento() {
		return idINFDocumento;
	}

	public void setIdINFDocumento(Integer idINFDocumento) {
		this.idINFDocumento = idINFDocumento;
	}

	public List<Documento> getListReferencias() {
		return listReferencias;
	}

	public void setListReferencias(List<Documento> listReferencias) {
		this.listReferencias = listReferencias;
	}
        
        public List<UsuarioDerivacion> getListaDerivacionPara() {
            return listaDerivacionPara;
        }

        public void setListaDerivacionPara(List<UsuarioDerivacion> listaDerivacionPara) {
            this.listaDerivacionPara = listaDerivacionPara;
        }

        public List<UsuarioDerivacion> getListaDerivacionCC() {
            return listaDerivacionCC;
        }

        public void setListaDerivacionCC(List<UsuarioDerivacion> listaDerivacionCC) {
            this.listaDerivacionCC = listaDerivacionCC;
        }


	public NuevoDocumentoAction(ArchivopendienteService archivoPendienteService,DocumentoService documentoService,PlantillaService plantillaService,RepositorioService repositorioService,UsuarioService usuarioService,ArchivoService archivoService,ConcesionarioService concesionarioService,ClienteService clienteService,ProcesoService procesoService,TipodocumentoService tipodocumentoService,TipoidentificacionService tipoidentificacionService,ExpedienteService expedienteService,AccionService accionService,TrazabilidaddocumentoService trazabilidaddocumentoService){
		this.archivoPendienteService=archivoPendienteService;
		this.documentoService=documentoService;
		this.plantillaService=plantillaService;
		this.repositorioService=repositorioService;
		this.usuarioService=usuarioService;
		this.archivoService=archivoService;
		this.concesionarioService=concesionarioService;
		this.clienteService=clienteService;
		this.procesoService=procesoService;
		this.tipodocumentoService=tipodocumentoService;
		this.tipoidentificacionService=tipoidentificacionService;
		this.expedienteService=expedienteService;
		this.accionService=accionService;
		this.trazabilidaddocumentoService=trazabilidaddocumentoService;
	}
	

	public Integer getIdtipoid(){
		return idtipoid;
	}

	public void setIdtipoid(Integer idtipoid){
		this.idtipoid=idtipoid;
	}

	public String getOcultar(){
		return ocultar;
	}

	public void setOcultar(String ocultar){
		this.ocultar=ocultar;
	}

	public String getStrUnidad(){
		return strUnidad;
	}

	public void setStrUnidad(String strUnidad){
		this.strUnidad=strUnidad;
	}

	public String getProceso(){
		return proceso;
	}

	public void setProceso(String proceso){
		this.proceso=proceso;
	}

	public String getStrCorreoCliente(){
		return strCorreoCliente;
	}

	public void setStrCorreoCliente(String strCorreoCliente){
		this.strCorreoCliente=strCorreoCliente;
	}

	public String getStrTelefonoCliente(){
		return strTelefonoCliente;
	}

	public void setStrTelefonoCliente(String strTelefonoCliente){
		this.strTelefonoCliente=strTelefonoCliente;
	}

	public String getDepartamento(){
		return departamento;
	}

	public void setDepartamento(String departamento){
		this.departamento=departamento;
	}

	public String getDistrito(){
		return distrito;
	}

	public void setDistrito(String distrito){
		this.distrito=distrito;
	}

	public Integer getIddepartamento(){
		return iddepartamento;
	}

	public void setIddepartamento(Integer iddepartamento){
		this.iddepartamento=iddepartamento;
	}

	public Integer getIddistrito(){
		return iddistrito;
	}

	public void setIddistrito(Integer iddistrito){
		this.iddistrito=iddistrito;
	}

	public Integer getIdprovincia(){
		return idprovincia;
	}

	public void setIdprovincia(Integer idprovincia){
		this.idprovincia=idprovincia;
	}

	public String getProvincia(){
		return provincia;
	}

	public void setProvincia(String provincia){
		this.provincia=provincia;
	}
	private Integer iIdExp;
	private String tipoidentificacion;
	private String nroidentificacion;
	private String strRazonSocial;
	private String strDireccionPrincipal;
	private String strRUC;

	public String getStrRUC(){
		return strRUC;
	}

	public void setStrRUC(String strRUC){
		this.strRUC=strRUC;
	}

	public String getStrRepresentanteLegal(){
		return strRepresentanteLegal;
	}

	public void setStrRepresentanteLegal(String strRepresentanteLegal){
		this.strRepresentanteLegal=strRepresentanteLegal;
	}
	private String strRepresentanteLegal;

	public String getNroidentificacion(){
		return nroidentificacion;
	}

	public void setNroidentificacion(String nroidentificacion){
		this.nroidentificacion=nroidentificacion;
	}

	public String getStrDireccionPrincipal(){
		return strDireccionPrincipal;
	}

	public void setStrDireccionPrincipal(String strDireccionPrincipal){
		this.strDireccionPrincipal=strDireccionPrincipal;
	}

	public String getStrRazonSocial(){
		return strRazonSocial;
	}

	public void setStrRazonSocial(String strRazonSocial){
		this.strRazonSocial=strRazonSocial;
	}

	public String getTipoidentificacion(){
		return tipoidentificacion;
	}

	public void setTipoidentificacion(String tipoidentificacion){
		this.tipoidentificacion=tipoidentificacion;
	}

	public Integer getIIdExp(){
		return iIdExp;
	}

	public void setIIdExp(Integer iIdExp){
		this.iIdExp=iIdExp;
	}

	// private Map informacion = new HashMap();
	@SuppressWarnings("unchecked")
	public String inicio(){
                Map<String,Object> session=ActionContext.getContext().getSession();
		List<ArchivoTemporal> l=(List<ArchivoTemporal>) session.get("uploaded_list");
		l=new ArrayList<ArchivoTemporal>();
		Map<String,Object> mapUpload=(Map<String,Object>) session.get(Constantes.SESSION_UPLOAD_LIST);
		if(mapUpload!=null){
			mapUpload.remove("upload2");
			mapUpload.put("upload2",l);
			session.remove(Constantes.SESSION_UPLOAD_LIST);
			session.put(Constantes.SESSION_UPLOAD_LIST,mapUpload);
		}
		return "inicio";
	}
        
        public String mostrarVistaRecepcion() throws Exception {
            listaDerivacionPara = new ArrayList<UsuarioDerivacion>(); 
            listaDerivacionCC   = new ArrayList<UsuarioDerivacion>(); 
            origenExpediente = Constantes.ORIGEN_EXPEDIENTE_NUEVO;
            recepcionVirtual = documentoExternoVirtualDAO.buscarDocumentoVirtual(codigoVirtual);
            
            try{
                Map<String,Object> sesion=ActionContext.getContext().getSession();
                Usuario usuario=(Usuario) sesion.get(Constantes.SESSION_USUARIO);
                        
                Cliente cliente =clienteService.findObjectBy(recepcionVirtual.getSidrecext().getVrucentrem(), 'A');
                Tipodocumento tipoDocumento = tipodocumentoService.findByIdTipoDocumentoPIDE(recepcionVirtual.getCcodtipdoc());
                objDD = new DocumentoDetail();
                objDD.setStrTipoDocumento(tipoDocumento ==null? "":tipoDocumento.getIdtipodocumento().toString());
                documento = new Documento();
                documento.setFechaDocumento(recepcionVirtual.getDfecdoc());
                
                
                if (recepcionVirtual.getSidrecext().getCtipdociderem() == '1'){
                   try{  
                      PeticionConsulta peticionConsulta = new PeticionConsulta();
                      peticionConsulta.setNuRucUsuario(parametroService.findByTipoUnico("RUC_OSITRAN").getValor());
                      peticionConsulta.setPassword(usuario.getNroDocumento());
                      peticionConsulta.setNuDniUsuario(usuario.getNroDocumento());
                      peticionConsulta.setNuDniConsulta(recepcionVirtual.getSidrecext().getVnumdociderem());
                      ReniecConsultaDni ws = new ReniecConsultaDni();
                      ResultadoConsulta respuesta = ws.getReniecConsultaDniHttpsSoap11Endpoint().consultar(peticionConsulta);

                      if (respuesta.getCoResultado().equals("0000")){
                            String primerApellido = "";
                            String segundoApellido = "";
                            String nombres = "";

                            if (respuesta.getDatosPersona().getApPrimer()!=null && !respuesta.getDatosPersona().getApPrimer().trim().equals("")) 
                                primerApellido = respuesta.getDatosPersona().getApPrimer();
                            if (respuesta.getDatosPersona().getApSegundo()!=null && !respuesta.getDatosPersona().getApSegundo().equals("")) 
                                segundoApellido = respuesta.getDatosPersona().getApSegundo();
                             if (respuesta.getDatosPersona().getPrenombres()!=null && !respuesta.getDatosPersona().getPrenombres().equals(""))
                                nombres = respuesta.getDatosPersona().getPrenombres();

                            documento.setDesRemitente(nombres + " " + primerApellido + " " + segundoApellido);
                            documento.setDesRemitente("0000" + documento.getDesRemitente().toUpperCase().toString().trim());
                      }else{
                          documento.setDesRemitente(respuesta.getDeResultado());
                      }
                   }catch(Exception  e){
                      e.printStackTrace();
                      documento.setDesRemitente("Se produjo un problema en la comunicación con el servidor de RENIEC");
                   }  
                }  
                 
                if (recepcionVirtual.getSidrecext().getCtipdociderem() == '2'){
                    try{  
                         SolicitudBean solicitudBean = new SolicitudBean();
                         solicitudBean.setStrCodInstitucion(parametroService.findByTipoUnico("COD_MIGRACIONES").getValor());
                         solicitudBean.setStrMac(parametroService.findByTipoUnico("MAC_MIGRACIONES").getValor());
                         solicitudBean.setStrNroIp(parametroService.findByTipoUnico("IP_MIGRACIONES").getValor());
                         solicitudBean.setStrNumDocumento(recepcionVirtual.getSidrecext().getVnumdociderem());
                         solicitudBean.setStrTipoDocumento("CE");
                         MigraCarnetdeExtrajeria ws = new MigraCarnetdeExtrajeria();
                         RespuestaBean respuestaBean = ws.getMigraCarnetdeExtrajeriaHttpsSoap11Endpoint().consultarDocumento(solicitudBean);
                         String primerApellido = "";
                         String segundoApellido = "";
                         String nombres = "";
                         
                         if (respuestaBean.getStrNumRespuesta().equals("0000")){
                              if (respuestaBean.getStrNombres()!=null && !respuestaBean.getStrNombres().trim().equals("")){
                                  nombres = respuestaBean.getStrNombres().trim();
                              }  
                              if (respuestaBean.getStrPrimerApellido()!=null && !respuestaBean.getStrPrimerApellido().trim().equals("")){
                                  primerApellido = respuestaBean.getStrPrimerApellido();
                              }
                              if (respuestaBean.getStrSegundoApellido()!=null && !respuestaBean.getStrSegundoApellido().trim().equals("")){
                                  segundoApellido = respuestaBean.getStrSegundoApellido();
                              }
                              
                              documento.setDesRemitente(nombres + " " + primerApellido + " " + segundoApellido);
                              documento.setDesRemitente("0000" + documento.getDesRemitente().toUpperCase().trim());
                         }else{
                             documento.setDesRemitente(parametroService.findByTipoAndValue("RESPUESTA_MIGRACIONES", respuestaBean.getStrNumRespuesta()).getDescripcion());
                         }     
                    }catch(Exception e){
                       documento.setDesRemitente("Se produjo un problema en la comunicación con el servidor de Migraciones");
                       e.printStackTrace();
                    } 
                }    
                
                if (cliente!=null){
                  objDD.setStrRazonSocial(cliente.getRazonSocial()==null?"":cliente.getRazonSocial());
                  objDD.setIIdCliente(cliente.getIdCliente()==null? -1 :cliente.getIdCliente());
                }else{
                  objDD.setStrRazonSocial("");
                  objDD.setIIdCliente(-1);
                }
            }catch(Exception e){
                objDD.setStrRazonSocial("");
                e.printStackTrace();
            }
            
            return "nuevoRegistroTramite";
        }

	public String mostrarVista() throws Exception {
            log.debug("NuevoDocumentoAction::mostrarVista()");
            this.fecha = new Date();
            flagVerExpediente = "1";
            listaDocReferenciados    = new ArrayList<Documento>();
            listaIntegrantesInternos = new ArrayList<DocumentoReunion>();
            listaIntegrantesExternos = new ArrayList<DocumentoReunion>(); 
            this.origenExpediente = Constantes.ORIGEN_EXPEDIENTE_NUEVO;
            if(idExpediente != null){
                expediente = expedienteService.findByIdExpediente(idExpediente);
                this.origenExpediente = Constantes.ORIGEN_EXPEDIENTE_EXISTENTE;
            }
            
            
            if(idDocumento != null){ 
                documento = documentoService.findByIdDocumento(idDocumento);
                if (tipoTransaccion!=null && tipoTransaccion.equals("M")){
                  DocumentoReunion documentoReunion = new DocumentoReunion();
                  documentoReunion.setIdDocumento(idDocumento);
                  listaDocReferenciados = documentoService.getReferenciaDocumento(idDocumento);
                  documentoReunion.setTipo("0");
                  listaIntegrantesInternos = documentoReunionDAO.getDocumentoReunion(documentoReunion);
                  documentoReunion.setTipo("1");
                  listaIntegrantesExternos = documentoReunionDAO.getDocumentoReunion(documentoReunion);
                }
                if (tipoTransaccion!=null && tipoTransaccion.equals("A")){
                    try{
                        List<LegajoDocumento> lst = null;
                        Documento d = documentoService.findByIdDocumento(idDocumento);

                        LegajoDocumento legajoDocumento = new LegajoDocumento();
                        legajoDocumento.setIdDocumento(d.getDocumentoreferencia()==null?d.getIdDocumento():d.getDocumentoreferencia());
                        Map<String,Object> sesion=ActionContext.getContext().getSession();
                        Usuario usuario=(Usuario) sesion.get(Constantes.SESSION_USUARIO);

                        lst = legajoDocumentoService.findDocumento(legajoDocumento, usuario);

                        if (lst==null || lst.size()==0)
                          flagVerExpediente = "0";
                    }catch(Exception e){
                          e.printStackTrace();
                          flagVerExpediente = "0";
                    }    
                }
            }else{
                if (idINFDocumento!=null && !idINFDocumento.toString().trim().equals("") && tipoTransaccion!=null && tipoTransaccion.equals("A")){
                    documento = documentoService.findByIdDocumento(idINFDocumento);
                    
                    try{
                        List<LegajoDocumento> lst = null;
                        LegajoDocumento legajoDocumento = new LegajoDocumento();
                        legajoDocumento.setIdDocumento(documento.getDocumentoreferencia()==null?documento.getIdDocumento():documento.getDocumentoreferencia());
                        Map<String,Object> sesion=ActionContext.getContext().getSession();
                        Usuario usuario=(Usuario) sesion.get(Constantes.SESSION_USUARIO);
                        lst = legajoDocumentoService.findDocumento(legajoDocumento, usuario);

                        if (lst==null || lst.size()==0)
                          flagVerExpediente = "0";
                    }catch(Exception e){
                          e.printStackTrace();
                          flagVerExpediente = "0";
                    }
                }     
            }

            Map<String, Object> session = ActionContext.getContext().getSession();
            ServletActionContext.getRequest().getSession().setAttribute("UsuarioCompartido",null);
            session.remove(Constantes.SESSION_UPLOAD_LIST);
            return "nuevoDocumento";
        }
        
         public String mostrarVistaTramite() throws Exception {
            log.debug("NuevoDocumentoAction::mostrarVistaTramite()");
            this.fecha = new Date();
            listaDerivacionPara = new ArrayList<UsuarioDerivacion>(); 
            listaDerivacionCC   = new ArrayList<UsuarioDerivacion>(); 

            this.origenExpediente = Constantes.ORIGEN_EXPEDIENTE_NUEVO;
            
            if(idExpediente != null){
                expediente = expedienteService.findByIdExpediente(idExpediente);
                this.origenExpediente = Constantes.ORIGEN_EXPEDIENTE_EXISTENTE;
            }

            if(idDocumento != null){
                documento = documentoService.findByIdDocumento(idDocumento);
                DocumentoDerivacion documentoDerivacion = new DocumentoDerivacion();
                documentoDerivacion.setTipo("P");
                documentoDerivacion.setIddocumento(idDocumento);
                List<DocumentoDerivacion> lst = documentoDerivacionDAO.getUsuarioDerivacion(documentoDerivacion);
              
                if (lst!=null && lst.size()>0){
                    for(int i=0;i<lst.size();i++){
                        UsuarioDerivacion usuarioDerivacion = new UsuarioDerivacion();
                        Usuario usuario = usuarioService.findByIdUsuario(lst.get(i).getIdusuario());
                        usuarioDerivacion.setIdentificador(lst.get(i).getIdusuario() + "-" + lst.get(i).getUnidadpropietario() + "-" + lst.get(i).getCargopropietario());
                        usuarioDerivacion.setNombreUsuario(usuario.getNombres() + " " + usuario.getApellidos());
                        listaDerivacionPara.add(usuarioDerivacion);
                    }
                }
                
                documentoDerivacion.setTipo("C");
                documentoDerivacion.setIddocumento(idDocumento);
                lst = documentoDerivacionDAO.getUsuarioDerivacion(documentoDerivacion);
                
                if (lst!=null && lst.size()>0){
                    for(int i=0;i<lst.size();i++){
                        UsuarioDerivacion usuarioDerivacion = new UsuarioDerivacion();
                        Usuario usuario = usuarioService.findByIdUsuario(lst.get(i).getIdusuario());
                        usuarioDerivacion.setIdentificador(lst.get(i).getIdusuario() + "-" + lst.get(i).getUnidadpropietario() + "-" + lst.get(i).getCargopropietario());
                        usuarioDerivacion.setNombreUsuario(usuario.getNombres() + " " + usuario.getApellidos());
                        listaDerivacionCC.add(usuarioDerivacion);
                    }
                }
                
                if (documento.getNroVirtual()!=null){
                    tipoTransaccion = "MR";
                    recepcionVirtual = documentoExternoVirtualDAO.buscarDocumentoVirtual(documento.getNroVirtual());
                }
            }

            Map<String, Object> session = ActionContext.getContext().getSession();
            ServletActionContext.getRequest().getSession().setAttribute("UsuarioCompartido",null);
            session.remove(Constantes.SESSION_UPLOAD_LIST);
       
            return "nuevoRegistroTramite";
        }

	public String agregarDocumentoMostrarVista() throws Exception {
		  log.debug("NuevoDocumentoAction::agregarDocumentoMostrarVista()");
	      this.fecha = new Date();
	      this.origenExpediente = Constantes.ORIGEN_EXPEDIENTE_NUEVO;

	      if(idExpediente != null){
	    	  expediente = expedienteService.findByIdExpediente(idExpediente);
	    	  this.origenExpediente = Constantes.ORIGEN_EXPEDIENTE_EXISTENTE;
	      }

	      if(idDocumento != null){
	    	  documento = documentoService.findByIdDocumento(idDocumento);
	      }


	      Map<String, Object> session = ActionContext.getContext().getSession();
	      ServletActionContext.getRequest().getSession().setAttribute("UsuarioCompartido",null);
	      session.remove(Constantes.SESSION_UPLOAD_LIST);

	      return "agregarDocumento";
	   }



	public String operacionFedatario() throws Exception {
		  log.debug("NuevoDocumentoAction::operacionFedatario()");
		 // List<Documentofedateado> list = documentoService.buscarLstDocumentoFedateado(3924, null, null);
	      return "nuevaOperacionFedatario";
	   }


	public String mostrarEdicion(){
		log.debug("NuevoDocumentoAction::mostrarEdicion()");
		// this.objDD = new DocumentoDetail();
		log.debug("mostrarEdicion retrieving archivoPendiente : "+this.getIdArchivoPendiente());
		// setLstRadio(getTipoidentificacionService().getTipoIdentificacionMap());
		this.setListaParametros(parametroService.findByTipo(Constantes.NUEVO_DOC_MARCA_DE_AGUA));
		this.setMarcaDeAgua("");
		return "inicioEdicion";
	}

	public String loadExpediente(){
		log.debug("NuevoDocumentoAction::loadExpediente()");
		try{
			Expediente objE=null;
			if(getIIdExp()==null){
				throw new Exception("No existe el Id de expediente ");
			}
			objE=getExpedienteService().findByIdExpediente(getIIdExp());
			if(objE==null){
				throw new Exception("No existe el expediente con el Id "+getIIdExp());
			}
			setObjDD(getDocumentoService().getExpedienteData(getIIdExp()));
			setIdtipoidentificacion(getObjDD().getIIdTipoIdentificacion());
			setTipoidentificacion(getObjDD().getStrTipoIdentificacion());
			setNroidentificacion(getObjDD().getStrNroIdentificacion());
			setStrRazonSocial(getObjDD().getStrRazonSocial());
			setStrRepresentanteLegal(getObjDD().getStrRepresentanteLegal());
			setStrDireccionPrincipal(getObjDD().getStrDireccionPrincipal());
			setIddepartamento(getObjDD().getIIdDepartamento());
			setDepartamento(getObjDD().getStrDepartamento());
			setIdprovincia(getObjDD().getIIdProvincia());
			setProvincia(getObjDD().getStrProvincia());
			setIddistrito(getObjDD().getIIdDistrito());
			setDistrito(getObjDD().getStrDistrito());
			setStrTelefonoCliente(getObjDD().getStrTelefonoCliente());
			setStrCorreoCliente(getObjDD().getStrCorreoCliente());
			setStrRUC(getObjDD().getStrRUC());
			setIdcorrentista(getObjDD().getIIdCorrentista());
			setCorrentista(getObjDD().getStrCorrentista());
			setStrDireccion(getObjDD().getStrDireccionConcesionario());
			setStrCorreoConcesionario(objE.getConcesionario().getCorreo());
			//setIdproceso(objE.getProceso().getIdproceso());
			//setProceso(objE.getProceso().getNombre());
			//setStrUnidad(objE.getProceso().getResponsable().getUnidad().getNombre());
			//setStrResponsable(objE.getProceso().getResponsable().getNombres()+" "+objE.getProceso().getResponsable().getApellidos());
			this.setOcultar("NO");
		}catch(Exception e){
			log.error(e.getMessage(),e);
			mensaje=e.getMessage();
		}
		return "nuevoDocumento";
	}


	@SuppressWarnings("unchecked")
	public String upload() throws Exception{
		setLstRadio(getTipoidentificacionService().getTipoIdentificacionMap());
		// this.setOcultar("NO");
		try{
			Map<String,Object> session=ActionContext.getContext().getSession();
			// AuthenticationDetails objAD = (AuthenticationDetails)
			// session.get("objAD");
			// Documento objDocumento = null;
			Integer iContador=(Integer) session.get("contador");
			// Integer iIdUsuario = (Integer) session.get("idusuario");
			// String sRol = (String) session.get("rol");
			String strTempo=SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.DIRECTORIO_TEMPORAL);
			log.debug("ENTRO AL UPLOAD ["+upload+"]");
			fullFileName=ServletActionContext.getServletContext().getRealPath("/")+strTempo+uploadFileName;
			log.debug("uploadFileName ["+uploadFileName+"]");
			log.debug("fullFileName ["+fullFileName+"]");
			File theFile=new File(fullFileName);
			// copiar a uno temporal ... en este caso sera RealPah/upload/ para
			// aprovechar
			FileUtils.copyFile(upload,theFile);
			org.ositran.utils.ArchivoTemporal at=new org.ositran.utils.ArchivoTemporal(uploadFileName,theFile);
			if(iContador==null){
				iContador=1;
			}else{
				iContador++;
			}
			session.put("contador",iContador);
			List<org.ositran.utils.ArchivoTemporal> l=(List<org.ositran.utils.ArchivoTemporal>) session.get("uploaded_list");
			if(l==null){
				l=new ArrayList<org.ositran.utils.ArchivoTemporal>();
			}
			l.add(at);
			session.put("uploaded_list",l);
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
		return "nuevoDocumento";
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String guardarPendiente(){
		try{
			// /// :::::::: registrar() ::::::::::::::: INICIO /////
			Map<String,Object> session=ActionContext.getContext().getSession();
			Map<String,Object> parameter=ActionContext.getContext().getParameters();
			String[] parss=(String[])parameter.get("suministroCache");
			log.debug(" parss   "+parss);
			if(parss!=null){
				log.debug(" parss length:"+parss.length);
			}
			// // Comienzo
			ArchivoPendiente ap=new ArchivoPendiente();
			ap.setExpediente(this.getObjDD().getIIdExpediente());
			ap.setProceso(procesoService.findByIdProceso(this.getIdproceso()));
			ap.setResponsable(this.getObjDD().getIIdResponsable());
			ap.setTipoDocumento(this.getIdtipodocumento());
			ap.setNumeroDocumento(this.getObjDD().getStrNroDocumento());
			ap.setNumeroFolios(this.getObjDD().getINroFolios());
			ap.setAsunto(this.getObjDD().getStrAsunto());
			ap.setTipoIdentificacion(this.getIdtipoidentificacion());
			log.debug("guardando cliente id :"+this.getIdcliente());
			ap.setCliente(this.getIdcliente());
			ap.setDireccionAlternativa(this.getObjDD().getStrDireccionAlternativa());
			// ap.setConcesionario(concesionarioService.findByIdConcesionario(this
			// .getIdcorrentista()));
			ap.setObservacion(this.getObjDD().getStrObservacion());
			log.debug("Fecha Documento"+objDD.getStrFechaLimiteAtencion());
			try{
				ap.setFechaDocumento(new SimpleDateFormat("dd/MM/yyyy").parse(objDD.getStrFechaDocumento()));
			}catch(Exception e){
                            log.error(e.getMessage(),e);
			}
			Integer iIdUsuario=(Integer) session.get("idusuario");
			Usuario origen=getUsuarioService().findByIdUsuario(iIdUsuario);
			log.debug(" usuario x: "+origen.getClave()+" id:"+origen.getIdusuario());
			ap.setUsuario(origen);
			Map<String,Object> mapUpload=(Map) session.get(Constantes.SESSION_UPLOAD_LIST);
			List<org.ositran.utils.ArchivoTemporal> l=(mapUpload!=null ? ((List<org.ositran.utils.ArchivoTemporal>) mapUpload.get("upload2")) : null);
			List<ArchivoTemporal> lista=new ArrayList<ArchivoTemporal>();
			for(org.ositran.utils.ArchivoTemporal arch : l){
				ArchivoTemporal archdest=new ArchivoTemporal();
				archdest.setRuta(arch.getFArchivo().getPath());
				archdest.setArchivoPendiente(ap);
				lista.add(archdest);
			}
			ap.setArchivosTemporales(lista);
			// //////////// AQUI BASE 64 /////////////////
			List<Campo> campos=plantillaService.listCamposByTipoPlantilla(idtipodocumento);
			Plantilla p=plantillaService.findByIdplantilla(this.idtipodocumento);
			ap.setPlantilla(p);
			List<Valorcampo> valores=new ArrayList<Valorcampo>();
			// Map parameters = ActionContext.getContext().getParameters();
			log.debug("////// enumeration size  "+parameter.size());
			/*
			 * Iterator<String> ee = parameter.keySet().iterator();
			 *
			 * while (ee.hasNext()) { String code = ee.next();
			 * log.debug(code + ":" + parameter.get(code) + "" +
			 * parameter.get(code).toString() + " class :" +
			 * parameter.get(code).getClass()); }
			 */
			for(Campo c : campos){
				String valor=((String[])parameter.get("valor"+c.getIdCampo()))[0];
				c.setValor((valor==null ? "" : valor));
				// log.debug(" !!!!!! valor"+c.getIdcampo()+": '"+c.getValor().toString()+"'");
				if(valor!=null){
					Valorcampo vc=new Valorcampo();
					vc.setCampo(c);
					vc.setArchivopendiente(ap);
					vc.setValor(valor.toString());
					valores.add(vc);
				}
			}
			ap.setValoresCampo(valores);
			ap.setEstado(Constantes.DOC_PENDIENTE_NUEVO_DOCUMENTO);
			log.debug(" before saving all archivoPendiente ... "+ap.getValoresCampo().size());
			this.getArchivoPendienteService().saveArchivopendiente(ap,null,null);
			log.debug(" after  saving all archivoPendiente ... ok  ");
			this.archivopendiente=ap;
		}catch(Exception e){
                    log.error(e.getMessage(),e);
		}
		this.cerrar="ok";
		this.ocultar="NO";
		this.mensaje=messageSource.getMessage(MessagePropertiesEnum.NEW_DOCUMENTO_SAVED);
		log.debug(" mensaje :"+this.mensaje);
		setLstRadio(getTipoidentificacionService().getTipoIdentificacionMap());
		return "nuevoDocumento";
	}

	@SuppressWarnings("unchecked")
	public String mostrarVistaEdicion(){
		Map<String,Object> session=ActionContext.getContext().getSession();
		log.debug(" retrieving archivoPendiente : "+this.getIdArchivoPendiente());
		archivopendiente=archivoPendienteService.findByIdarchivopendiente(this.getIdArchivoPendiente());
		archivopendiente.setStrTipoDocumento(archivopendiente.getPlantilla().getDescripcion());
		log.debug("mostrarVistaEdicion::archivopendiente size:"+archivopendiente.getValoresCampo().size());
		if(this.getObjDD()==null)
			this.setObjDD(new DocumentoDetail());
		this.getObjDD().setIIdExpediente(archivopendiente.getExpediente());
		this.setIdproceso(archivopendiente.getProceso().getIdproceso());
		this.setProceso(archivopendiente.getProceso().getNombre());
		this.getObjDD().setIIdResponsable(archivopendiente.getResponsable());
		this.setIdtipodocumento(archivopendiente.getTipoDocumento());
		Tipodocumento tipo=tipodocumentoService.findByIdTipoDocumento(archivopendiente.getTipoDocumento());
		this.setTipoDocumento((tipo!=null ? tipo.getNombre() : ""));
		this.getObjDD().setStrNroDocumento(archivopendiente.getNumeroDocumento());
		this.getObjDD().setINroFolios(archivopendiente.getNumeroFolios());
		this.getObjDD().setStrAsunto(archivopendiente.getAsunto());
		Date fecha=archivopendiente.getFechaDocumento();
		this.getObjDD().setStrFechaDocumento((fecha!=null ? new SimpleDateFormat("dd/MM/yyyy").format(fecha) : ""));
		this.setIdtipoidentificacion(archivopendiente.getTipoIdentificacion());
		this.setIdtipoid(archivopendiente.getTipoIdentificacion());
		this.setIdcliente(archivopendiente.getCliente());
		log.debug("cliente id :"+this.getIdcliente());
		if(this.getIdcliente()!=null){
			Cliente cli=this.getClienteService().findByIdCliente(archivopendiente.getCliente());
			this.tipoidentificacion=cli.getTipoIdentificacion().getNombre();
			this.nroidentificacion=cli.getNumeroIdentificacion();
			this.setStrRazonSocial(cli.getRazonSocial());
			this.setStrRepresentanteLegal(cli.getRepresentanteLegal());
			this.setStrDireccionPrincipal(cli.getDireccionPrincipal());
			// Distrito d = Distr cli.getUbigeoprincipal().getIddistrito()
		}
		this.setStrRUC(this.archivopendiente.getConcesionario()!=null ? this.archivopendiente.getConcesionario().getRuc() : "");
		this.setTipoidentificacion(tipoidentificacion);
		this.getObjDD().setStrDireccionAlternativa(archivopendiente.getDireccionAlternativa());
		// this.setIdcorrentista(archivopendiente.getConsecionario());
		this.getObjDD().setStrObservacion(archivopendiente.getObservacion());
		setLstRadio(getTipoidentificacionService().getTipoIdentificacionMap());
		Iterator<ArchivoTemporal> i=archivopendiente.getArchivosTemporales().iterator();
		List<org.ositran.utils.ArchivoTemporal> files=new ArrayList<org.ositran.utils.ArchivoTemporal>();
		while(i.hasNext()){
			int iBracket=-1;
			String sRealName=null;
			ArchivoTemporal at=i.next();
			iBracket=at.getRuta().indexOf(Constantes.ARCHIVO_BRACKET_FIN);
			if(iBracket!=-1){
				sRealName=at.getRuta().substring(iBracket+1);
			}
			org.ositran.utils.ArchivoTemporal att=new org.ositran.utils.ArchivoTemporal(sRealName,new File(at.getRuta()));
			files.add(att);
		}
		Map<String,Object> mapUpload=(Map<String,Object>) session.get(Constantes.SESSION_UPLOAD_LIST);
		if(mapUpload!=null){
			mapUpload.remove("upload2");
			mapUpload.put("upload2",files);
			session.remove(Constantes.SESSION_UPLOAD_LIST);
			session.put(Constantes.SESSION_UPLOAD_LIST,mapUpload);
		}else{
			mapUpload=new HashMap<String,Object>();
			mapUpload.put("upload2",files);
			session.put(Constantes.SESSION_UPLOAD_LIST,mapUpload);
		}
		this.setListaParametros(parametroService.findByTipo(Constantes.NUEVO_DOC_MARCA_DE_AGUA));
		this.setMarcaDeAgua("");
		// session.put("uploaded_list", files);
		this.cargarData="ok";
		log.debug("^^ cargardata :"+this.cargarData);
		this.ocultar="NO";
		return "edicionDocumento";
	}

	@SuppressWarnings("unchecked")
	public String actualizarPendiente(){
		try{
			// /// :::::::: registrar() ::::::::::::::: INICIO /////
			Map<String,Object> session=ActionContext.getContext().getSession();
			Map<String,Object> parameter=ActionContext.getContext().getParameters();
			String[] parss=(String[])parameter.get("suministroCache");
			log.debug(" parss   "+parss);
			if(parss!=null){
				log.debug(" parss length:"+parss.length);
			}
			// // Comienzo
			ArchivoPendiente ap=this.getArchivopendiente();
			ap.setExpediente(this.getObjDD().getIIdExpediente());
			ap.setProceso(procesoService.findByIdProceso(this.getIdproceso()));
			ap.setResponsable(this.getObjDD().getIIdResponsable());
			ap.setTipoDocumento(this.getIdtipodocumento());
			ap.setNumeroDocumento(this.getObjDD().getStrNroDocumento());
			ap.setNumeroFolios(this.getObjDD().getINroFolios());
			ap.setAsunto(this.getObjDD().getStrAsunto());
			ap.setTipoIdentificacion(this.getIdtipoidentificacion());
			log.debug("guardando cliente id :"+this.getIdcliente());
			ap.setCliente(this.getIdcliente());
			ap.setDireccionAlternativa(this.getObjDD().getStrDireccionAlternativa());
			ap.setObservacion(this.getObjDD().getStrObservacion());
			log.debug("Fecha Documento"+objDD.getStrFechaLimiteAtencion());
			try{
				ap.setFechaDocumento(new SimpleDateFormat("dd/MM/yyyy").parse(objDD.getStrFechaDocumento()));
			}catch(Exception e){
                            log.error(e.getMessage(),e);
			}
			Integer iIdUsuario=(Integer) session.get("idusuario");
			Usuario origen=getUsuarioService().findByIdUsuario(iIdUsuario);
			log.debug(" usuario x: "+origen.getClave()+" id:"+origen.getIdusuario());
			ap.setUsuario(origen);
			Map<String,Object> mapUpload=(Map<String,Object>) session.get(Constantes.SESSION_UPLOAD_LIST);
			List<org.ositran.utils.ArchivoTemporal> l=(List<org.ositran.utils.ArchivoTemporal>) mapUpload.get("upload2");
			Iterator<org.ositran.utils.ArchivoTemporal> i=(l!=null ? l.iterator() : null);
			List<ArchivoTemporal> lista=new ArrayList<ArchivoTemporal>();
			while(l!=null&&i.hasNext()){
				org.ositran.utils.ArchivoTemporal arch=i.next();
				ArchivoTemporal archdest=new ArchivoTemporal();
				archdest.setRuta(arch.getFArchivo().getPath());
				archdest.setArchivoPendiente(ap);
				lista.add(archdest);
			}
			// /// lo agrego despues de borrar los antiguos
			ap.setArchivosTemporales(lista);
			// //////////// AQUI BASE 64 /////////////////
			List<Campo> campos=plantillaService.listCamposByTipoPlantilla(idtipodocumento);
			Plantilla p=plantillaService.findByIdplantilla(this.idtipodocumento);
			ap.setPlantilla(p);
			List<Valorcampo> valores=new ArrayList<Valorcampo>();
			// Map parameters = ActionContext.getContext().getParameters();
			log.debug("////// enumeration size  "+parameter.size());
			/*
			 * Iterator ee = parameters.keySet().iterator();
			 *
			 * while (ee.hasNext()) { Object code = ee.next();
			 * log.debug(code + ":" + ((String[])
			 * parameters.get(code))[0]); }
			 */
			for(Campo c : campos){
				String valor=((String[])parameter.get("valor"+c.getIdCampo()))[0];
				c.setValor((valor==null ? "" : valor));
				log.debug(" !!!!!! valor"+c.getIdCampo()+": '"+c.getValor().toString()+"'");
				Valorcampo vc=new Valorcampo();
				vc.setCampo(c);
				vc.setArchivopendiente(ap);
				vc.setValor(c.getValor().toString());
				valores.add(vc);
			}
			ap.setValoresCampo(valores);
			ap.setEstado(Constantes.DOC_PENDIENTE_NUEVO_DOCUMENTO);
			log.debug(" before saving all archivoPendiente ... "+ap.getValoresCampo().size());
			this.getArchivoPendienteService().saveArchivopendiente(ap,null,null);
			log.debug(" after  saving all archivoPendiente ... ok  ");
			this.archivopendiente=ap;
		}catch(Exception ex){
                    log.error(ex.getMessage(),ex);
		}
		this.cerrar="ok";
		this.ocultar="NO";
		this.mensaje=messageSource.getMessage(MessagePropertiesEnum.NEW_DOCUMENTO_SAVED);
		setLstRadio(getTipoidentificacionService().getTipoIdentificacionMap());
		return "edicionDocumento";
	}

	/**REN Metodo encargado de abrir la ventana "Adjuntar" ------------------------------------------------------------------*/
	public String verSubirConMetadata(){
		try{
			Map<String,Object> session=ActionContext.getContext().getSession();
			log.debug("Removiendo archivos adjuntos de la sesion");
			session.remove(Constantes.SESSION_UPLOAD_LIST);
			Integer[] arrIdDoc=new Integer[1];
			arrIdDoc[0]=this.idDocumento;
			session.put("arrIdDoc",arrIdDoc);
			this.objDocumento=new Documento();
			this.objDocumento.setFechaDocumento(new Date());
			this.objDocumentoPrincipal=documentoService.findDocExpedienteByIdDocumento(this.idDocumento);

			this.expediente=objDocumentoPrincipal.getExpediente();

			//FIXME esto esta mal
			//this.rutapadre=RepositorioServiceWebservice.RUTA_PADRE_EXPEDIENTE+"/"+this.expediente.getNroexpediente()+"/";
         this.rutapadre = repositorioService.obtenerRutaExpediente(expediente);
			this.narchivos=0;
			if(this.expediente!=null&&this.expediente.getDocumentoList()!=null&&this.expediente.getDocumentoList().size()>0){
				List<Documento> docs=this.expediente.getDocumentoList();
				for(Documento d : docs){
					narchivos+=(d.getArchivos()!=null) ? d.getArchivos().size() : 0;
//					if(d.getArchivos()!=null&&d.getArchivos().size()>0){
//						for(Archivo a : d.getArchivos()){
//							log.debug(" archivo  "+a.getRutaAlfresco());
//						}
//					}
				}
			}
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
		return "subirconmetadata";
	}


	public String verVersionar(){
		try{
			Map<String,Object> session=ActionContext.getContext().getSession();
			log.debug("Removiendo archivos adjuntos de la sesion");
			session.remove(Constantes.SESSION_UPLOAD_LIST);
			Integer[] arrIdDoc=new Integer[1];
			arrIdDoc[0]=this.idDocumento;
			session.put("arrIdDoc",arrIdDoc);
			this.objDocumento=new Documento();
			this.objDocumento.setFechaDocumento(new Date());
			this.objDocumentoPrincipal=documentoService.findByIdDocumento(this.idDocumento);

			this.expediente=objDocumentoPrincipal.getExpediente();
			//FIXME esto esta mal
			//this.rutapadre=RepositorioServiceWebservice.RUTA_PADRE_EXPEDIENTE+"/"+this.expediente.getNroexpediente()+"/";
         this.rutapadre = repositorioService.obtenerRutaExpediente(expediente);
			this.narchivos=0;
			if(this.expediente!=null&&this.expediente.getDocumentoList()!=null&&this.expediente.getDocumentoList().size()>0){
				List<Documento> docs=this.expediente.getDocumentoList();
				for(Documento d : docs){
					narchivos+=(d.getArchivos()!=null) ? d.getArchivos().size() : 0;
					if(d.getArchivos()!=null&&d.getArchivos().size()>0){
						for(Archivo a : d.getArchivos()){
							log.debug(" archivo  "+a.getRutaAlfresco());
						}
					}
				}
			}
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
		return "versionar";
	}

	/**REN se llama cuando se adjunta un documento al expediente mediante el detalle -------------------------------------*/
	@SuppressWarnings("unchecked")
	public String dosubirConMetadata(){
		log.info("origen derivacion : "+this.origenDerivacion);
		Map<String,Object> sesion=ActionContext.getContext().getSession();
			Usuario usuario=(Usuario) sesion.get(Constantes.SESSION_USUARIO);
			Map<String,List<org.ositran.utils.ArchivoTemporal>> upload=(Map<String,List<org.ositran.utils.ArchivoTemporal>>) sesion.get(Constantes.SESSION_UPLOAD_LIST);

			log.debug("### dosubirConMetadata");
			Integer principal=objDocumento.getIdDocumento();
			objDocumento.setTiponumeracion(this.tiponumeracion);
			objDocumento.setCondestinatarios(this.condestinatarios);
			log.debug("Numero de Documento: "+objDocumento.getNumeroDocumento()+"  " );
			objDocumento.setConcopias(this.concopias );
			log.debug("Tipo enumeracion :"+tiponumeracion+"  , "+objDocumento.getTiponumeracion());
			log.debug("Enumerar Documento form:"+enumerarDocumento+"  " );
			objDocumento.setEnumerarDocumento(enumerarDocumento!=null?enumerarDocumento.equals("S"):false);
			log.debug("Enumerar Documento doc:"+objDocumento.getEnumerarDocumento()+"  " );
			try{

				objDocumento=documentoService.subirConMetadata(usuario,upload,idDocumento,objDocumento,objDD,versionar,idarchivos, "ES_nombrePDFprincipal");

			}catch(RuntimeException e){
				log.error("No se pudo subir el archivo a alfresco.",e);
				return Constantes.ERROR_ALFRESCO;
				//return Constantes.ERROR_ALFRESCO;
			}
			this.cerrar="OK";

			if(versionar != null && versionar.equalsIgnoreCase(""+true)){
				this.mensaje="             " + messageSource.getMessage(MessagePropertiesEnum.VERSIONED) + "                     ";
				if(principal!=null&&objDocumento!=null&&objDocumento.getIdDocumento()!=null&&principal.intValue()!=objDocumento.getIdDocumento().intValue()){
					this.mensaje="\\n"+((objDocumento.getNumeroDocumento()==null||objDocumento.getNumeroDocumento().equalsIgnoreCase(Constantes.NRODCUMENTO_SIN_NUMERO) )?  messageSource.getMessage(MessagePropertiesEnum.DOC_ADDED) : messageSource.getMessage(MessagePropertiesEnum.NRODOC_GENERATED, new String[] {((objDocumento.getNumeroDocumento()).replace(".", ""))}) );
				}
			}else{
				this.mensaje=((objDocumento.getNumeroDocumento()==null||objDocumento.getNumeroDocumento().equalsIgnoreCase(Constantes.NRODCUMENTO_SIN_NUMERO) )?  messageSource.getMessage(MessagePropertiesEnum.DOC_ADDED) : messageSource.getMessage(MessagePropertiesEnum.NRODOC_GENERATED, new String[] {((objDocumento.getNumeroDocumento()).replace(".", ""))}) );

			}

			return "subirconmetadata";
	}


	public String verVentanaNumeracion(){
		log.debug("-> [Action] NuevoDocumentoAction - verVentanaNumeracion():String ");

		try{
			Map<String,Object> session=ActionContext.getContext().getSession();
			Usuario logeado=(Usuario) session.get(Constantes.SESSION_USUARIO);
			this.documento=documentoService.findByIdDocumento(this.idDocumento);
			this.expediente=documento.getExpediente();
                    	this.listaDocumentos=documentoService.findDocumentoPorNumerar(logeado,this.documento);
                        paraEnumerarArea = true;  
                        
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
		return "numeracion";
	}


	public String verVentanaFirmar(){

		try{

			Map<String,Object> session=ActionContext.getContext().getSession();
			Usuario logeado=(Usuario) session.get(Constantes.SESSION_USUARIO);
			this.objDocumentoPrincipal=documentoService.findByIdDocumento(this.idDocumento);
			this.expediente=objDocumentoPrincipal.getExpediente();
			this.listaDocumentos=documentoService.findDocumentosPorFirmar(logeado,this.expediente);

		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
		return "firmar";
	}

	/**Numera los documentos seleccionados en el popup "Enumerar documento".
	 */
	public void guardarNumeracionFirma() {
		log.debug("-> [Action] NuevoDocumentoAction - guardarNumeracionFirma():void ");
                Map<String, Object> sesion = ActionContext.getContext().getSession();
                String[] documentosPorEnumerar = this.getRequest().getParameterValues("documentosPorEnumerar");
                Map<Integer, Integer> documentsToModifyA = new HashMap<Integer, Integer>();
                Map<Integer, Integer> documentsToModifyG = new HashMap<Integer, Integer>();
                Map<Integer, Integer> documentsToModifyP = new HashMap<Integer, Integer>();

                String area = "";
                String gerencia = "";
                String presidencia = "";
                if (documentosPorEnumerar != null && documentosPorEnumerar.length > 0) {
                   log.info("Enumerando {} documento(s)", documentosPorEnumerar.length);

                   for (String idTemp : documentosPorEnumerar) {
                           String id =idTemp;
                           String enumerar =idTemp;
                           enumerar = enumerar.substring(0,1);
                           id = id.substring(1);
                           if(enumerar.equals("A")){
                                   area = "A";
                                   documentsToModifyA.put(Integer.valueOf(id), Integer.valueOf(Constantes.DO_ENUMERAR));
                       }
                           if(enumerar.equals("G")){
                                   gerencia = "G";
                                   documentsToModifyG.put(Integer.valueOf(id), Integer.valueOf(Constantes.DO_ENUMERAR));
                       }
                           if(enumerar.equals("P")){
                                   presidencia = "P";
                                   documentsToModifyP.put(Integer.valueOf(id), Integer.valueOf(Constantes.DO_ENUMERAR));
                       }

                   }
                } else {
                   log.info("Ningun documento por enumerar");
                }


                if(area.equals("A")){
                  // obtener detalle de autenticacion de alfresco porque se almacena en ThreadLocal y vamos a cambiar de hilo
                    AuthenticationDetails authDetails = AuthenticationUtils.getAuthenticationDetails();
                    // llamada asincrona
                    taskExecutor.execute(new ModifyDocumentsTask(sesion, documentoService, documentsToModifyA, authDetails,"A"));
                }
                   if(gerencia.equals("G")){
                          // obtener detalle de autenticacion de alfresco porque se almacena en ThreadLocal y vamos a cambiar de hilo
                        AuthenticationDetails authDetails = AuthenticationUtils.getAuthenticationDetails();
                        // llamada asincrona
                        taskExecutor.execute(new ModifyDocumentsTask(sesion, documentoService, documentsToModifyG, authDetails,"G"));
                }
                   if(presidencia.equals("P")){
                          // obtener detalle de autenticacion de alfresco porque se almacena en ThreadLocal y vamos a cambiar de hilo
                        AuthenticationDetails authDetails = AuthenticationUtils.getAuthenticationDetails();
                        // llamada asincrona
                        taskExecutor.execute(new ModifyDocumentsTask(sesion, documentoService, documentsToModifyP, authDetails,"P"));
                }

   }

   public void modificacionEnProceso() {
      try {
         Map<String, Object> sesion = ActionContext.getContext().getSession();
         Boolean enProceso = (Boolean) sesion.get("MODIFICACION_EN_PROCESO");

         if (enProceso == null) {
            enProceso = false;
         }

         String mensaje = (String) sesion.get("MODIFICACION_MENSAJE");
         String numero = (String) sesion.get("NUMERO_GENERADO");
         String jsonString = " { 'enProceso': " + enProceso + ", 'mensaje': '" + mensaje + "', 'numero': '" + numero + "'}";
         HttpServletResponse response = ServletActionContext.getResponse();
         response.setContentType("text/x-json;charset=UTF-8");
         PrintWriter out = response.getWriter();
         out.print(jsonString);
      } catch (IOException ex) {
         log.error(ex.getMessage(), ex);
      }
   }

    /**
     * @return the mailService
     */
    public ManejoDeEmailService getMailService() {
        return mailService;
    }

    /**
     * @param mailService the mailService to set
     */
    public void setMailService(ManejoDeEmailService mailService) {
        this.mailService = mailService;
    }

    public void setMessageSource(SigedMessageSource ms) {
        this.messageSource = ms;
    }

        public String mostrarCambiarFirmante(){
        	Integer iddoc=Integer.parseInt(ServletActionContext.getRequest().getParameter("iddoc"));
        	objDocumento=documentoService.findByIdDocumento(iddoc);
        	return "editFirmante";
        }

        public String validarFirmante(){
        	String firmante=ServletActionContext.getRequest().getParameter("firmante");
        	if(firmante!=""){
	        	mensaje="_1";
        	}
        	else{
        		mensaje="_No selecciono ningun firmante";
        	}
        	return "exito";
        }

        public String guardarFirmante(){
        	String firmante=ServletActionContext.getRequest().getParameter("firmante");
        	String id=ServletActionContext.getRequest().getParameter("iddoc");
        	Integer iddoc=Integer.parseInt(id);
        	Documento doc=documentoService.findByIdDocumento(iddoc);
        	Integer idUsuario=Integer.parseInt(firmante.split("_")[1]);
        	Usuario userFirmante=usuarioService.findByIdUsuario(idUsuario);
        	doc.setFirmante(userFirmante);
        	try{
        	documentoService.guardarDocumento(doc);
        	mensaje="Se registro el nuevo firmante";
        	}catch(Exception e){
        		log.error("Error al guardar Firmante");
        	}
        	return "exito";
        }

	@SuppressWarnings("unused")
	private void procesarEmail(Usuario Origen,Usuario Destino){
		/****** Simular un envio de correo *****/
		String to=Destino.getCorreo();
		String from=Origen.getCorreo();
		String subject="Notificacion de Siged";
		String msgText="Ud Tiene un mensaje en su bandeja de siged por favor revicelo que le envia el Sr:"+Origen.getNombres();
		String hostSmtp="192.168.1.200";
		boolean debug=true;
		getMailService().EnviarEmail(to,from,subject,msgText,hostSmtp,debug);
	}

   private class ModifyDocumentsTask implements Runnable {

      private DocumentoService documentoService;
      private Map<String, Object> sesion;
      private Map<Integer, Integer> documentsToModify;
      private AuthenticationDetails authDetails;
      private String tipoNumeracion;

      public ModifyDocumentsTask(Map<String, Object> sesion, DocumentoService documentoService, Map<Integer, Integer> documentsToModify, AuthenticationDetails authDetails, String tipoNumeracion) {
         this.documentoService = documentoService;
         this.sesion = sesion;
         this.documentsToModify = documentsToModify;
         this.authDetails = authDetails;
         this.tipoNumeracion = tipoNumeracion;
      }

      @Override
      public void run() {
         // setear los detalles de autenticacion de alfresco para que se almacenen en el nuevo hilo
    	  log.debug("-> [Action] NuevoDocumentoAction - run():void ");
         AuthenticationUtils.setAuthenticationDetails(authDetails);
         Usuario usuario = (Usuario) sesion.get(Constantes.SESSION_USUARIO);
         // es necesario setear el usuario en el ThreadLocal para poder usar los webservices de Alfresco
         AuthThreadLocalHolder.setUsuario(usuario);
         sesion.put("MODIFICACION_EN_PROCESO", true);
         List<Documento> listaDocumentos = null;

         try {
            listaDocumentos = documentoService.modifyDocuments(usuario, documentsToModify, tipoNumeracion);
         } catch (Exception e) {
            log.error(e.getMessage(), e);
         }

         StringBuilder cadena = new StringBuilder();
         String numero = "";
         try {
            cadena.append("Lista de Documentos Modificados:");

            if (listaDocumentos != null) {
               for (Documento objDocumento : listaDocumentos) {
                  cadena.append("\\n");
                  cadena.append(objDocumento.getTipoDocumento().getNombre()).append("-").append(objDocumento.getNumeroDocumento());
                  numero = objDocumento.getTipoDocumento().getNombre()+"-"+objDocumento.getNumeroDocumento();
               }
            }
         } catch (Exception e) {
            log.error(e.getMessage(), e);
         }

         sesion.put("MODIFICACION_EN_PROCESO", false);
         sesion.put("MODIFICACION_MENSAJE", cadena.toString());
         sesion.put("NUMERO_GENERADO", numero);
      }
   }

	public ArchivopendienteService getArchivoPendienteService(){
		return archivoPendienteService;
	}

	public void setArchivoPendienteService(ArchivopendienteService archivoPendienteService){
		this.archivoPendienteService=archivoPendienteService;
	}

	public ArchivoService getArchivoService(){
		return archivoService;
	}

	public void setArchivoService(ArchivoService archivoService){
		this.archivoService=archivoService;
	}

	public ArchivoPendiente getArchivopendiente(){
		return archivopendiente;
	}
        
        public List<Documento> getListaDocReferenciados() {
            return listaDocReferenciados;
        }

        public void setListaDocReferenciados(List<Documento> listaDocReferenciados) {
            this.listaDocReferenciados = listaDocReferenciados;
        }

	public void setArchivopendiente(ArchivoPendiente archivopendiente){
		this.archivopendiente=archivopendiente;
	}

	public String getAsunto(){
		return asunto;
	}

	public void setAsunto(String asunto){
		this.asunto=asunto;
	}

	public Date getAtenderAntesDe(){
		return atenderAntesDe;
	}

	public void setAtenderAntesDe(Date atenderAntesDe){
		this.atenderAntesDe=atenderAntesDe;
	}

	public String getCerrar(){
		return cerrar;
	}

	public void setCerrar(String cerrar){
		this.cerrar=cerrar;
	}

	public ClienteService getClienteService(){
		return clienteService;
	}

	public void setClienteService(ClienteService clienteService){
		this.clienteService=clienteService;
	}

	public ConcesionarioService getConcesionarioService(){
		return concesionarioService;
	}

	public void setConcesionarioService(ConcesionarioService concesionarioService){
		this.concesionarioService=concesionarioService;
	}

	public Documento getDocumento(){
		return documento;
	}

	public void setDocumento(Documento documento){
		this.documento=documento;
	}

	public DocumentoService getDocumentoService(){
		return documentoService;
	}

	public void setDocumentoService(DocumentoService documentoService){
		this.documentoService=documentoService;
	}

	public ExpedienteService getExpedienteService(){
		return expedienteService;
	}

	public void setExpedienteService(ExpedienteService expedienteService){
		this.expedienteService=expedienteService;
	}

	public Integer getIdArchivoPendiente(){
		return idArchivoPendiente;
	}

	public void setIdArchivoPendiente(Integer idArchivoPendiente){
		this.idArchivoPendiente=idArchivoPendiente;
	}

	public Integer getIdDocumento(){
		return idDocumento;
	}

	public void setIdDocumento(Integer idDocumento){
		this.idDocumento=idDocumento;
	}

	public Integer getIdPlantilla(){
		return idPlantilla;
	}

	public void setIdPlantilla(Integer idPlantilla){
		this.idPlantilla=idPlantilla;
	}

	public Integer getIdproceso(){
		return idproceso;
	}

	public void setIdproceso(Integer idproceso){
		this.idproceso=idproceso;
	}

	public Integer getIdtipodocumento(){
		return idtipodocumento;
	}

	public void setIdtipodocumento(Integer idtipodocumento){
		this.idtipodocumento=idtipodocumento;
	}

	public DocumentoDetail getObjDD(){
		return objDD;
	}

	public void setObjDD(DocumentoDetail objDD){
		this.objDD=objDD;
	}

	public PlantillaService getPlantillaService(){
		return plantillaService;
	}

	public void setPlantillaService(PlantillaService plantillaService){
		this.plantillaService=plantillaService;
	}

	public ProcesoService getProcesoService(){
		return procesoService;
	}

	public void setProcesoService(ProcesoService procesoService){
		this.procesoService=procesoService;
	}

	public RepositorioService getRepositorioService(){
		return repositorioService;
	}

	public void setRepositorioService(RepositorioService repositorioService){
		this.repositorioService=repositorioService;
	}

	public HttpServletRequest getRequest(){
		return request;
	}

	public void setRequest(HttpServletRequest request){
		this.request=request;
	}

	public HttpServletResponse getResponse(){
		return response;
	}

	public void setResponse(HttpServletResponse response){
		this.response=response;
	}

	public String getRutaArchivo(){
		return rutaArchivo;
	}

	public void setRutaArchivo(String rutaArchivo){
		this.rutaArchivo=rutaArchivo;
	}

	public TipodocumentoService getTipoDocumentoService(){
		return tipodocumentoService;
	}

	public void setTipodocumentoService(TipodocumentoService tipodocumentoService){
		this.tipodocumentoService=tipodocumentoService;
	}

	public TipoidentificacionService getTipoidentificacionService(){
		return tipoidentificacionService;
	}

	public void setTipoidentificacionService(TipoidentificacionService tipoidentificacionService){
		this.tipoidentificacionService=tipoidentificacionService;
	}

	public UsuarioService getUsuarioService(){
		return usuarioService;
	}

	public void setUsuarioService(UsuarioService usuarioService){
		this.usuarioService=usuarioService;
	}

	public List<Tipoidentificacion> getLstRadio(){
		return lstRadio;
	}

	public void setLstRadio(List<Tipoidentificacion> lstRadio){
		this.lstRadio=lstRadio;
	}
        
         public DocumentoReferenciaService getDocumentoReferenciaService() {
            return documentoReferenciaService;
        }

        public void setDocumentoReferenciaService(DocumentoReferenciaService documentoReferenciaService) {
            this.documentoReferenciaService = documentoReferenciaService;
        }

	public Documento getObjDocumento(){
		return objDocumento;
	}

	public void setObjDocumento(Documento objDocumento){
		this.objDocumento=objDocumento;
	}


	public NotificacionService getSrvNotificacion(){
		return srvNotificacion;
	}

	public void setSrvNotificacion(NotificacionService srvNotificacion){
		this.srvNotificacion=srvNotificacion;
	}

	public Documento getObjDocumentoPrincipal(){
		return objDocumentoPrincipal;
	}

	public void setObjDocumentoPrincipal(Documento objDocumentoPrincipal){
		this.objDocumentoPrincipal=objDocumentoPrincipal;
	}

	public Expediente getExpediente(){
		return expediente;
	}

	public void setExpediente(Expediente expediente){
		this.expediente=expediente;
	}

	public String getRutapadre(){
		return rutapadre;
	}

	public void setRutapadre(String rutapadre){
		this.rutapadre=rutapadre;
	}

	public int getNarchivos(){
		return narchivos;
	}

	public void setNarchivos(int narchivos){
		this.narchivos=narchivos;
	}

	public Integer[] getIdarchivos(){
		return idarchivos;
	}

	public void setIdarchivos(Integer[] idarchivos){
		this.idarchivos=idarchivos;
	}

	public String getVersionar(){
		return versionar;
	}

	public void setVersionar(String versionar){
		this.versionar=versionar;
	}

	public String getOrigenExpediente(){
		return origenExpediente;
	}

	public void setOrigenExpediente(String origenExpediente){
		this.origenExpediente=origenExpediente;
	}

	public Integer getIdExpediente(){
		return idExpediente;
	}

	public void setIdExpediente(Integer idExpediente){
		this.idExpediente=idExpediente;
	}

	public String getOrigenDerivacion() {
		return origenDerivacion;
	}

	public void setOrigenDerivacion(String origenDerivacion) {
		this.origenDerivacion = origenDerivacion;
	}

	public Integer getiIdNotificacion() {
		return iIdNotificacion;
	}

	public void setiIdNotificacion(Integer iIdNotificacion) {
		this.iIdNotificacion = iIdNotificacion;
	}

    public TaskExecutor getTaskExecutor() {
        return taskExecutor;
    }

    public void setTaskExecutor(TaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    public String getEnumerarDocumento() {
		return enumerarDocumento;
	}

	public void setEnumerarDocumento(String enumerarDocumento) {
		this.enumerarDocumento = enumerarDocumento;
	}

	public String getTiponumeracion() {
		return tiponumeracion;
	}

	public void setTiponumeracion(String tiponumeracion) {
		this.tiponumeracion = tiponumeracion;
	}

	public List<String> getCondestinatarios() {
		return condestinatarios;
	}

	public void setCondestinatarios(List<String> condestinatarios) {
		this.condestinatarios = condestinatarios;
	}

	public List<String> getConcopias() {
		return concopias;
	}

	public void setConcopias(List<String> concopias) {
		this.concopias = concopias;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public List<Documento> getListaDocumentos(){
		return listaDocumentos;
	}

	public void setListaDocumentos(List<Documento> listaDocumentos){
		this.listaDocumentos=listaDocumentos;
	}

	public List<Parametro> getListaParametros(){
		return listaParametros;
	}

	public void setListaParametros(List<Parametro> listaParametros){
		this.listaParametros=listaParametros;
	}

	public ParametroService getParametroService(){
		return parametroService;
	}

	public void setParametroService(ParametroService parametroService){
		this.parametroService=parametroService;
	}
	private String marcaDeAgua;

	public String getMarcaDeAgua(){
		return marcaDeAgua;
	}

	public void setMarcaDeAgua(String marcaDeAgua){
		this.marcaDeAgua=marcaDeAgua;
	}

	public TrazabilidaddocumentoService getTrazabilidaddocumentoService(){
		return trazabilidaddocumentoService;
	}

	public void setTrazabilidaddocumentoService(TrazabilidaddocumentoService trazabilidaddocumentoService){
		this.trazabilidaddocumentoService=trazabilidaddocumentoService;
	}

	public AccionService getAccionService(){
		return accionService;
	}

	public void setAccionService(AccionService accionService){
		this.accionService=accionService;
	}

	public String getCargarData(){
		return cargarData;
	}

	public void setCargarData(String cargarData){
		this.cargarData=cargarData;
	}

	public String getTipoDocumento(){
		return tipodocumento;
	}

	public void setTipoDocumento(String tipodocumento){
		this.tipodocumento=tipodocumento;
	}

	public String getFileCaption(){
		return fileCaption;
	}

	public void setFileCaption(String fileCaption){
		this.fileCaption=fileCaption;
	}

	public String getUploadContentType(){
		return uploadContentType;
	}

	public void setUploadContentType(String uploadContentType){
		this.uploadContentType=uploadContentType;
	}

	public String getUploadFileName(){
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName){
		this.uploadFileName=uploadFileName;
	}

	public String getFullFileName(){
		return fullFileName;
	}

	public void setFullFileName(String fullFileName){
		this.fullFileName=fullFileName;
	}
	private File upload;

	public File getUpload(){
		return upload;
	}

	public void setUpload(File upload){
		this.upload=upload;
	}

	public boolean isBBandeja(){
		return bBandeja;
	}

	public void setBBandeja(boolean bBandeja){
		this.bBandeja=bBandeja;
	}

	public Integer getIdccdestinatario(){
		return idccdestinatario;
	}

	public void setIdccdestinatario(Integer idccdestinatario){
		this.idccdestinatario=idccdestinatario;
	}
	private Integer idccdestinatario;

	public Integer getIddestinatario(){
		return iddestinatario;
	}

	public void setIddestinatario(Integer iddestinatario){
		this.iddestinatario=iddestinatario;
	}

	public Integer getIdcliente(){
		return idcliente;
	}

	public void setIdcliente(Integer idcliente){
		this.idcliente=idcliente;
	}
	private String strAcc;

	public String getStrAcc(){
		return strAcc;
	}

	public void setStrAcc(String strAcc){
		this.strAcc=strAcc;
	}

	public void setServletRequest(HttpServletRequest request){
		this.request=request;
	}

	public void setServletResponse(HttpServletResponse response){
		this.response=response;
	}

	public String getStrResponsable(){
		return strResponsable;
	}

	public void setStrResponsable(String strResponsable){
		this.strResponsable=strResponsable;
	}

	public String getStrCorreoConcesionario(){
		return strCorreoConcesionario;
	}

	public void setStrCorreoConcesionario(String strCorreoConcesionario){
		this.strCorreoConcesionario=strCorreoConcesionario;
	}

	public String getStrDireccion(){
		return strDireccion;
	}

	public void setStrDireccion(String strDireccion){
		this.strDireccion=strDireccion;
	}

	public String getCorrentista(){
		return correntista;
	}

	public void setCorrentista(String correntista){
		this.correntista=correntista;
	}

	public Integer getIdcorrentista(){
		return idcorrentista;
	}

	public void setIdcorrentista(Integer idcorrentista){
		this.idcorrentista=idcorrentista;
	}

	public Integer getIdtipoidentificacion(){
		return idtipoidentificacion;
	}

	public void setIdtipoidentificacion(Integer idtipoidentificacion){
		this.idtipoidentificacion=idtipoidentificacion;
	}

	public String getMensaje(){
		return mensaje;
	}

	public void setMensaje(String mensaje){
		this.mensaje=mensaje;
	}

	public Boolean getParaEnumerarArea() {
		return paraEnumerarArea;
	}

	public void setParaEnumerarArea(Boolean paraEnumerarArea) {
		this.paraEnumerarArea = paraEnumerarArea;
	}

	public Boolean getParaEnumerarGerencia() {
		return paraEnumerarGerencia;
	}

	public void setParaEnumerarGerencia(Boolean paraEnumerarGerencia) {
		this.paraEnumerarGerencia = paraEnumerarGerencia;
	}

	public Boolean getParaEnumerarPresidencia() {
		return paraEnumerarPresidencia;
	}

	public void setParaEnumerarPresidencia(Boolean paraEnumerarPresidencia) {
		this.paraEnumerarPresidencia = paraEnumerarPresidencia;
	}

	public RolService getRolService() {
		return rolService;
	}

	public void setRolService(RolService rolService) {
		this.rolService = rolService;
	}

         public String getTipoTransaccion() {
            return tipoTransaccion;
        }

        public void setTipoTransaccion(String tipoTransaccion) {
            this.tipoTransaccion = tipoTransaccion;
        }
        
       public List<DocumentoReunion> getListaIntegrantesInternos() {
            return listaIntegrantesInternos;
        }

        public void setListaIntegrantesInternos(List<DocumentoReunion> listaIntegrantesInternos) {
            this.listaIntegrantesInternos = listaIntegrantesInternos;
        }

        public List<DocumentoReunion> getListaIntegrantesExternos() {
            return listaIntegrantesExternos;
        }

        public void setListaIntegrantesExternos(List<DocumentoReunion> listaIntegrantesExternos) {
            this.listaIntegrantesExternos = listaIntegrantesExternos;
        }
        public DocumentoReunionDAO getDocumentoReunionDAO() {
            return documentoReunionDAO;
        }

        public void setDocumentoReunionDAO(DocumentoReunionDAO documentoReunionDAO) {
            this.documentoReunionDAO = documentoReunionDAO;
        }
}
