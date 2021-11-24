/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.actions;

import static org.ositran.utils.StringUtil.isEmpty;
import gob.ositran.siged.config.SigedProperties;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.ositran.daos.DocumentoExternoVirtualDAO;
import org.ositran.services.SeguimientoService;
import java.io.InputStream;
import java.net.MalformedURLException;
import com.btg.ositran.siged.domain.IotdtmDocExterno;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat; 
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpUtils;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jxls.transformer.XLSTransformer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.annotations.SMDMethod;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.ositran.daos.AuditoriaDAO;
import org.ositran.daos.DistritoDAO;
import org.ositran.daos.DocumentoDAO;
import org.ositran.daos.PerfilDAO;
import org.ositran.daos.ReemplazoDAO;
import org.ositran.dojo.BusquedaAvanzada;
import org.ositran.dojo.ClienteBeanJson;
import org.ositran.dojo.ClienteJSon;
import org.ositran.dojo.NumeracionJSON;
import org.ositran.dojo.ObjetoJSON;
import org.ositran.dojo.ProcesoBeanJson;
import org.ositran.dojo.Recurso;
import org.ositran.dojo.grid.Estructura;
import org.ositran.dojo.grid.GridUsuario;
import org.ositran.dojo.grid.Item;
import org.ositran.dojo.grid.ItemDocumento;
import org.ositran.dojo.grid.ItemUF;
import org.ositran.dojo.tree.ReferenciaArbol;
import org.ositran.dojo.tree.SimpleNode;
import org.ositran.dojo.tree.SimpleNode2;
import org.ositran.dojo.tree.SimpleNodeLeaf;
import org.ositran.services.AccionService;
import org.ositran.services.ArchivoService;
import org.ositran.services.CampoService;
import org.ositran.services.CarpetabusquedaService;
import org.ositran.services.ClienteService;
import org.ositran.services.ConcesionarioService;
import org.ositran.services.DocumentoService;
import org.ositran.services.DocumentoxclienteService;
import org.ositran.services.ExpedienteService;
import org.ositran.services.ExpedientestorService;
import org.ositran.services.FavoritoService;
import org.ositran.services.GridcolumnaxusuarioService;
import org.ositran.services.ItemService;
import org.ositran.services.LogBusquedaAvanzadaService;
import org.ositran.services.LogOperacionService;
import org.ositran.services.ManejoDeEmailService;
import org.ositran.services.MensajeriaService;
import org.ositran.services.NotificacionService;
import org.ositran.services.NumeracionService;
import org.ositran.services.ReferenciaArchivoService;
import org.ositran.services.ParametroService;
import org.ositran.services.PlantillaService;
import org.ositran.services.ProcesoService;
import org.ositran.services.RolService;
import org.ositran.services.SupervisorService;
import org.ositran.services.TipodocumentoService;
import org.ositran.services.TipoidentificacionService;
import org.ositran.services.TrazabilidaddocumentoService;
import org.ositran.services.UnidadService;
import org.ositran.services.UsuarioService;
import org.ositran.siged.service.AlfrescoWSService;
import org.ositran.utils.ArchivoTemporal;
import org.ositran.utils.Constantes;
import org.ositran.utils.FechaLimite;
import org.springframework.jms.IllegalStateException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import com.btg.ositran.siged.domain.Alerta;
import com.btg.ositran.siged.domain.TipoLegajoUnidad;
import com.btg.ositran.siged.domain.Usuarioxunidadxfuncion;
import com.btg.ositran.siged.domain.Archivo;
import com.btg.ositran.siged.domain.Auditoria;
import com.btg.ositran.siged.domain.Campo;
import com.btg.ositran.siged.domain.FirmaArchivo;
import com.btg.ositran.siged.domain.CargoAdministrado;
import com.btg.ositran.siged.domain.LegajoDocumento;
import com.btg.ositran.siged.domain.CarpetaBusqueda;
import com.btg.ositran.siged.domain.Cliente;
import com.btg.ositran.siged.domain.Concesionario;
import com.btg.ositran.siged.domain.SeguimientoXFirma;
import com.btg.ositran.siged.domain.DetalleCliente;
import com.btg.ositran.siged.domain.Distrito;
import com.btg.ositran.siged.domain.Documento;
import com.btg.ositran.siged.domain.Legajo;
import com.btg.ositran.siged.domain.DocumentoAdjunto;
import com.btg.ositran.siged.domain.DocumentoAtendido;
import com.btg.ositran.siged.domain.DocumentoPendiente;
import com.btg.ositran.siged.domain.DocumentoReferencia;
import com.btg.ositran.siged.domain.Documentoenviado;
import com.btg.ositran.siged.domain.Expediente;
import com.btg.ositran.siged.domain.Favorito;
import com.btg.ositran.siged.domain.FilaBandejaUF;
import com.btg.ositran.siged.domain.GridXGridColumna;
import com.btg.ositran.siged.domain.Gridcolumnaxusuario;
import com.btg.ositran.siged.domain.LogBusquedaAvanzada;
import com.btg.ositran.siged.domain.Mensajeria;
import com.btg.ositran.siged.domain.Notificacion;
import com.btg.ositran.siged.domain.Numeracion;
import com.btg.ositran.siged.domain.Parametro;
import com.btg.ositran.siged.domain.Perfil;
import com.btg.ositran.siged.domain.Tipodocumento;
import com.btg.ositran.siged.domain.Plantilla;
import com.btg.ositran.siged.domain.Proceso;
import com.btg.ositran.siged.domain.Reemplazo;
import com.btg.ositran.siged.domain.ReferenciaArchivo;
import com.btg.ositran.siged.domain.Rol;
import com.btg.ositran.siged.domain.Supervisor;
import com.btg.ositran.siged.domain.TipoLegajo;
import com.btg.ositran.siged.domain.Tipoidentificacion;
import com.btg.ositran.siged.domain.Trazabilidadapoyo;
import com.btg.ositran.siged.domain.Trazabilidaddocumento;
import com.btg.ositran.siged.domain.Unidad;
import com.btg.ositran.siged.domain.Usuario;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.ositran.cmis.api.AlfrescoApiWs;
import java.math.BigDecimal;
import java.net.URLConnection;
import java.text.DateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.ItemIterable;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.ositran.common.alfresco.AuthThreadLocalHolder;
import org.ositran.daos.DespachoVirtualDAO;
import org.ositran.daos.DocumentoAtendidoDAO;
import org.ositran.daos.DocumentoPendienteDAO;
import org.ositran.daos.RecepcionVirtualDAO;
import org.ositran.daos.SeguimientoXFirmaDAO;
import org.ositran.daos.TipoLegajoDAO;
import org.ositran.daos.TipoLegajoUnidadDAO;
import org.ositran.daos.UsuarioxunidadxfuncionDAO;
import org.ositran.dojo.ArchivoJSON;
import org.ositran.dojo.RemitenteJSON;
import org.ositran.dojo.TipoDocumentoJSon;
import org.ositran.dojo.tree.NodoArbol;
import org.ositran.services.CargoAdministradoService;
import org.ositran.services.DetalleClienteService;
import org.ositran.services.DocumentoAdjuntoService;
import org.ositran.services.DocumentoEnviadoService;
import org.ositran.services.DocumentoReferenciaService;
import org.ositran.services.FirmaArchivoService;
import org.ositran.services.LegajoDocumentoService;
import org.ositran.services.LegajoService;
import org.ositran.services.PerfilService;
import org.ositran.services.SeguimientoXFirmaService;
import org.ositran.services.TrazabilidadapoyoService;
import org.ositran.utils.StringUtil;

public class DojoAction {

        private static String USERCREADOR=SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.ALFRESCO_USUARIOCREADOR);
        private static String USERCREADOR_CLAVE=SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.ALFRESCO_USUARIOCREADOR_CLAVE);
        
	private static Log log = LogFactory.getLog(DojoAction.class);
	private List<Archivo> listArchivo;
	private Integer idCarpetaBusqueda;
	private Integer idTipoGrid;
	private Integer iIdUpload;
	private String idDoc;
	private String rutaAlfresco;
	private String contDocumentosRecibidos;
        private PerfilService srvPerfil;
        private DocumentoAdjuntoService documentoAdjuntoService;
        private DocumentoReferenciaService documentoReferenciaService;
        private DetalleClienteService detalleClienteService;
        private CargoAdministradoService cargoAdministradoService;
        private UsuarioxunidadxfuncionDAO usuarioxunidadxfuncionDAO;
        private Documento objDocumento;
	private Archivo archivo;
	private List<FilaBandejaUF> listDocumentosRecibidos;
	private List<Documento> listExpedienteDocumentos;
        private String archivoPrincipal;
	private String PDF;
	private Boolean paraAprobar;
	private Character agrupacion;
	private String iIdExpediente;
        private CarpetaBusqueda carpetaBusqueda;
	private List<Item> lstDocumentoGrid;
	private Map<String, Object> mapSession;
	private PerfilDAO perfilDao;
	private DistritoDAO distritoDao;
	private ReemplazoDAO reemplazoDao;
	private DocumentoDAO documentoDao;
	private RolService rolService;
	private ItemService srvItem;
	private CampoService srvCampo;
	private UnidadService srvUnidad;
	private ProcesoService srvProceso;
	private ClienteService clienteService;
	private SeguimientoService seguimientoService;
	private ArchivoService archivoService;
	private UsuarioService usuarioService;
	private FavoritoService favoritoService;
	private DocumentoService documentoService;
	private ParametroService parametroService;
	private PlantillaService srvPlantilla;
	private ExpedienteService expedienteService;
	private AlfrescoWSService alfrescoWebServiceClient;
	private SupervisorService supervisorService;
	private MensajeriaService srvMensajeria;
	private NumeracionService srvNumeracion;
	private NotificacionService notificacionService;
	private TipodocumentoService srvTipoDocumento;
	private ConcesionarioService concesionarioService;
	private ExpedientestorService storService;
	private CarpetabusquedaService carpetaBusquedaService;
	private DocumentoxclienteService documentoxclienteService;
	private TipoidentificacionService tipoIdentificacionService;
	private GridcolumnaxusuarioService gridColumnaXUsuarioService;
        private TrazabilidaddocumentoService trazabilidadDocumentoService;
        private LogOperacionService logOperacionService;
        private LogBusquedaAvanzadaService logBusquedaAvanzadaService;
        private Integer iddestinatario;
	private String sTipoDerivacion;
	private String sOpcion;
	private String strAcc;
	private Boolean provieneDeMail;
	private AccionService accionService;
	private ProcesoService procesoService;
	private FechaLimite fechaLimite;
	private ManejoDeEmailService mailService;
        private SeguimientoXFirmaDAO seguimientoXFirmaDAO;
        private SeguimientoXFirmaService seguimientoXFirmaService;
        private ReferenciaArchivoService referenciaArchivoService;
        private LegajoService legajoService;
        private LegajoDocumentoService legajoDocumentoService;
        private TipoLegajoUnidadDAO tipoLegajoUnidadDAO;
        private DocumentoEnviadoService documentoenviadoService;
        private TrazabilidadapoyoService trazabilidadapoyoService;
        private DocumentoPendienteDAO documentoPendienteDAO;
        private DocumentoAtendidoDAO documentoAtendidoDAO;
        private TipoLegajoDAO tipoLegajoDAO;
        private DocumentoExternoVirtualDAO documentoExternoVirtualDAO;
        private RecepcionVirtualDAO recepcionVirtualDAO;
        private DespachoVirtualDAO despachoVirtualDAO;

        public RecepcionVirtualDAO getRecepcionVirtualDAO() {
            return recepcionVirtualDAO;
        }

        public void setRecepcionVirtualDAO(RecepcionVirtualDAO recepcionVirtualDAO) {
            this.recepcionVirtualDAO = recepcionVirtualDAO;
        }

        public DespachoVirtualDAO getDespachoVirtualDAO() {
            return despachoVirtualDAO;
        }

        public void setDespachoVirtualDAO(DespachoVirtualDAO despachoVirtualDAO) {
            this.despachoVirtualDAO = despachoVirtualDAO;
        }

        public DocumentoExternoVirtualDAO getDocumentoExternoVirtualDAO() {
            return documentoExternoVirtualDAO;
        }

        public void setDocumentoExternoVirtualDAO(DocumentoExternoVirtualDAO documentoExternoVirtualDAO) {
            this.documentoExternoVirtualDAO = documentoExternoVirtualDAO;
        }

        public TipoLegajoDAO getTipoLegajoDAO() {
            return tipoLegajoDAO;
                    
        }

        public void setTipoLegajoDAO(TipoLegajoDAO tipoLegajoDAO) {
            this.tipoLegajoDAO = tipoLegajoDAO;
        }

        public DocumentoAtendidoDAO getDocumentoAtendidoDAO() {
            return documentoAtendidoDAO;
        }

        public void setDocumentoAtendidoDAO(DocumentoAtendidoDAO documentoAtendidoDAO) {
            this.documentoAtendidoDAO = documentoAtendidoDAO;
        }

        public DocumentoPendienteDAO getDocumentoPendienteDAO() {
            return documentoPendienteDAO;
        }

        public void setDocumentoPendienteDAO(DocumentoPendienteDAO documentoPendienteDAO) {
            this.documentoPendienteDAO = documentoPendienteDAO;
        }

        public TrazabilidadapoyoService getTrazabilidadapoyoService() {
            return trazabilidadapoyoService;
        }

        public void setTrazabilidadapoyoService(TrazabilidadapoyoService trazabilidadapoyoService) {
            this.trazabilidadapoyoService = trazabilidadapoyoService;
        }

        public DocumentoEnviadoService getDocumentoenviadoService() {
            return documentoenviadoService;
        }

        public void setDocumentoenviadoService(DocumentoEnviadoService documentoenviadoService) {
            this.documentoenviadoService = documentoenviadoService;
        }

        public TipoLegajoUnidadDAO getTipoLegajoUnidadDAO() {
            return tipoLegajoUnidadDAO;
        }

        public void setTipoLegajoUnidadDAO(TipoLegajoUnidadDAO tipoLegajoUnidadDAO) {
            this.tipoLegajoUnidadDAO = tipoLegajoUnidadDAO;
        }

        public LegajoDocumentoService getLegajoDocumentoService() {
            return legajoDocumentoService;
        }

        public void setLegajoDocumentoService(LegajoDocumentoService legajoDocumentoService) {
            this.legajoDocumentoService = legajoDocumentoService;
        }

        public LegajoService getLegajoService() {
            return legajoService;
        }

        public void setLegajoService(LegajoService legajoService) {
            this.legajoService = legajoService;
        }
        
        public ReferenciaArchivoService getReferenciaArchivoService() {
            return referenciaArchivoService;
        }

        public void setReferenciaArchivoService(ReferenciaArchivoService referenciaArchivoService) {
            this.referenciaArchivoService = referenciaArchivoService;
        }
    
        private final String REPOSITORIO_ID  = SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.ALFRESCO_ROOTID);
	private FirmaArchivoService firmaArchivoService;

        public FirmaArchivoService getFirmaArchivoService() {
            return firmaArchivoService;
        }
        
        public SeguimientoXFirmaService getSeguimientoXFirmaService() {
            return seguimientoXFirmaService;
        }

        public void setSeguimientoXFirmaService(SeguimientoXFirmaService seguimientoXFirmaService) {
            this.seguimientoXFirmaService = seguimientoXFirmaService;
        }

        public void setFirmaArchivoService(FirmaArchivoService firmaArchivoService) {
            this.firmaArchivoService = firmaArchivoService;
        }
        public SeguimientoXFirmaDAO getSeguimientoXFirmaDAO() {
            return seguimientoXFirmaDAO;
        }

        public void setSeguimientoXFirmaDAO(SeguimientoXFirmaDAO seguimientoXFirmaDAO) {
            this.seguimientoXFirmaDAO = seguimientoXFirmaDAO;
        }

        public PerfilService getSrvPerfil() {
            return srvPerfil;
        }

        public void setSrvPerfil(PerfilService srvPerfil) {
            this.srvPerfil = srvPerfil;
        }

        public UsuarioxunidadxfuncionDAO getUsuarioxunidadxfuncionDAO() {
            return usuarioxunidadxfuncionDAO;
        }

        public void setUsuarioxunidadxfuncionDAO(UsuarioxunidadxfuncionDAO usuarioxunidadxfuncionDAO) {
            this.usuarioxunidadxfuncionDAO = usuarioxunidadxfuncionDAO;
        }
        
        public CargoAdministradoService getCargoAdministradoService() {
            return cargoAdministradoService;
        }

        public void setCargoAdministradoService(CargoAdministradoService cargoAdministradoService) {
            this.cargoAdministradoService = cargoAdministradoService;
        }


        public DetalleClienteService getDetalleClienteService() {
            return detalleClienteService;
        }

        public void setDetalleClienteService(DetalleClienteService detalleClienteService) {
            this.detalleClienteService = detalleClienteService;
        }

        public DocumentoReferenciaService getDocumentoReferenciaService() {
            return documentoReferenciaService;
        }

        public void setDocumentoReferenciaService(DocumentoReferenciaService documentoReferenciaService) {
            this.documentoReferenciaService = documentoReferenciaService;
        }
   
	public List<Documento> getListExpedienteDocumentos() {
		return listExpedienteDocumentos;
	}

	public void setListExpedienteDocumentos(List<Documento> listExpedienteDocumentos) {
		this.listExpedienteDocumentos = listExpedienteDocumentos;
	}

	public String getiIdExpediente() {
		return iIdExpediente;
	}

	public void setiIdExpediente(String iIdExpediente) {
		this.iIdExpediente = iIdExpediente;
	}

	
	public LogBusquedaAvanzadaService getLogBusquedaAvanzadaService() {
		return logBusquedaAvanzadaService;
	}

	public void setLogBusquedaAvanzadaService(
			LogBusquedaAvanzadaService logBusquedaAvanzadaService) {
		this.logBusquedaAvanzadaService = logBusquedaAvanzadaService;
	}

	public String execute() throws Exception {
		log.debug("-> [Action] DojoAction - execute():String ");
		return Action.SUCCESS;
	}

	@SuppressWarnings("unchecked")
	@SMDMethod
	public ObjetoJSON getListaDocumentosRecibidos(BusquedaAvanzada objFiltro){
		log.debug("-> [Action] DojoAction - getListDocumentosRecibidos():String ");
		Usuario objUsuario = null;
		mapSession = ActionContext.getContext().getSession();
		objUsuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
		listDocumentosRecibidos = documentoService.getContDocUsuarioFinal(objUsuario, true,objFiltro);
		ObjetoJSON objJSON = new ObjetoJSON();
		if(listDocumentosRecibidos!=null){
			log.debug("tiene documentos");
			objJSON.setIdentifier("id");
			objJSON.setLabel("name");
			List<Item> items = new ArrayList<Item>();
			for (FilaBandejaUF filaBandejaUF : listDocumentosRecibidos) {
	            	Item item = new Item();
					item.setId(filaBandejaUF.getId());
					log.debug("DOCUMENTO: "+item.getId());
					items.add(item);
			}
			objJSON.setItems(items);
		}else{
			log.debug("no tine objJSON");
			objJSON = null;
		}

		return objJSON;
	}

	 private Integer compareFechas(Date d1, Date d2){
	    	return d1.compareTo(d2);
	    }

	public String getInicioDocumentos(){
		log.debug("-> [Action] DojoAction - getInicioDocumentos():String ");
		String idExpediente = ServletActionContext.getRequest().getParameter("iIdExpediente").toString();

		Usuario objUsuario = null;
		mapSession = ActionContext.getContext().getSession();
		objUsuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);

		mapSession.put("idExpediente_", idExpediente);

		if (objUsuario == null) {
			log.error("Expiro la sesion");
			return null;
		}

		//List<Documento> listDocumento = documentoService.buscarLstDocumentoExpediente(idExpediente);


		//String[] sqlQueryDinamico = new String[1]; sqlQueryDinamico[0] = "";
		//objJSON.setItems(gridColumnaXUsuarioService.getItems_BusquedaAvanzada(sTipoFiltro, objFiltro, arrFecha, sqlQueryDinamico));


		//contDocumentosRecibidos = String.valueOf(documentoService.getContDocUsuarioFinal(objUsuario, true,null).size());

		//listDocumentosRecibidos = gridColumnaXUsuarioService.getContDocUsuarioFinal(objUsuario, true);

        //log.debug("contDocumentosRecibidos"+contDocumentosRecibidos);
		return "verInicioDocumentos";

	}


	public String getInicioMenu(){
		log.debug("-> [Action] DojoAction - getInicioMenu():String ");

		Usuario objUsuario = null;
		mapSession = ActionContext.getContext().getSession();
		objUsuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);

		if (objUsuario == null) {
			log.error("Expiro la sesion");
			return null;
		}

  	return "verInicioMenu";

	}
        
        @SMDMethod
        public String updateNoLeido(Integer iIdToUpdate){
            log.debug("-> [Action] DojoAction - updateNoLeido():String ");
            
            mapSession = ActionContext.getContext().getSession();
            Documento objDocumento = null;
            
            String nombrePC = (String)mapSession.get("nombrePC");
            objDocumento = documentoService.updateNoLeido(iIdToUpdate, nombrePC);
            log.debug("Documento actualizado con ID [" + objDocumento.getIdDocumento() + "] a estado no leido [" + objDocumento.getLeido() + "]");
            
            return "done";
        }



	public String getInicioMenuDocumentos(){
		log.debug("-> [Action] DojoAction - getInicioMenuDocumentos():String ");

		Usuario objUsuario = null;
		mapSession = ActionContext.getContext().getSession();
		objUsuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
		//String idExpediente = ServletActionContext.getRequest().getParameter("iIdExpediente").toString();

		if (objUsuario == null) {
			log.error("Expiro la sesion");
			return null;
		}

		return "verInicioMenuDocumentos";

	}


	public String getMenuDocumentosRecibidos(){
		log.debug("-> [Action] DojoAction - getMenuDocumentosRecibidos():String ");
		Usuario objUsuario = null;
		mapSession = ActionContext.getContext().getSession();
		objUsuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
		contDocumentosRecibidos = String.valueOf(documentoService.getContDocUsuarioFinal(objUsuario, true,null).size());
		
                if (objUsuario == null) {
			log.error("Expiro la sesion");
			return null;
		}
		//listDocumentosRecibidos = documentoService.getContDocUsuarioFinal(objUsuario, true,null);
		return "verMenuDocRec";

	}

	public String getMenuInformativosRecibidos(){
		log.debug("-> [Action] DojoAction - getMenuInformativosRecibidos():String ");
		Usuario objUsuario = null;
		mapSession = ActionContext.getContext().getSession();
		objUsuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
		contDocumentosRecibidos = String.valueOf(documentoService.getContDocUsuarioFinal(objUsuario, true,null).size());
		if (objUsuario == null) {
			log.error("Expiro la sesion");
			return null;
		}
		//listDocumentosRecibidos = documentoService.getContDocUsuarioFinal(objUsuario, true,null);
		return "verMenuInfRec";

	}


	public void limpiarCarpetaTemporalxUsuario(String usuario) {
		log.debug("-> [Action] DocumentoAction - limpiarCarpetaTemporalxUsuario():void ");

		File carpetaTemporal = new File(SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.UPLOAD_CARPETA_TEMPO));
		if (carpetaTemporal.exists() && carpetaTemporal.isDirectory()) {
			String[] archivosTemporales = carpetaTemporal.list();
			File tempDelete = null;
			for (String archivoTemporal : archivosTemporales) {
				if (archivoTemporal.contains("[" + usuario)) {
					log.debug("Archivo temporal a elminar:" + archivoTemporal);
					tempDelete = new File(SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.UPLOAD_CARPETA_TEMPO), archivoTemporal);
					tempDelete.delete();
				}
			}
		}
	}

	public void descargarArchivo()	throws ServletException, IOException {

		log.debug("-> [Action] DojoAction - exportarExcelCarpetaBusqueda():void ");
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();

	        response.setContentType("text/html;charset=UTF-8");
	        //PrintWriter out = response.getWriter();
	        try {
	        	//http://servicios.apn.gob.pe:8090/alfresco/download/direct/workspace/SpacesStore/ca875f39-57c8-4bd5-8bcb-07a3a5f63a0c/jpgPruebamantenimiento.JPG
	            String archivo =    "http://servicios.apn.gob.pe:8090/alfresco/download/direct/workspace/SpacesStore/ca875f39-57c8-4bd5-8bcb-07a3a5f63a0c/jpgPruebamantenimiento.JPG?ticket=TICKET_1e0f06a47f4ecc3da91add2aaa0ec5fd5e4b2353";       //request.getParameter("archivo");
	            File f = new File(archivo);
	            response.setContentType("application/pdf");//Se define  el tipo de archivo a descargar
	            response.setHeader ("Content-Disposition", "attachment; filename="+ archivo +"");
	            InputStream in =new FileInputStream(f);
	            ServletOutputStream outs = response.getOutputStream();
	            int bit = 256;
	            try{
	                while ((bit) >= 0){
	                    bit = in.read();
	                    outs.write(bit);
	                }
	            }catch (IOException ioe){ioe.printStackTrace(System.out);}
	               outs.flush();
	               outs.close();
	               in.close();
	        } finally {
	            //out.close();
	        }

	}
        
        @SMDMethod
        public String moverSIDECO(String idDocumento){
            log.debug("-> [Action] DojoAction - moverSIDECO():String ");
            
            mapSession = ActionContext.getContext().getSession();
            Documento objDocumento = null;
            
            String nombrePC = (String)mapSession.get("nombrePC");
            objDocumento = documentoService.moverDocumentoSIDECO(new Integer(idDocumento), nombrePC);
            log.debug("Documento actualizado con ID [" + objDocumento.getIdDocumento() + "] creado en el SIDECO [" + objDocumento.getLeido() + "]");
            
            return "0";
        }

	public String imprimirGridCarpetasBusqueda(){
		log.debug("-> [Action] DojoAction - imprimirGrid():String ");
		lstDocumentoGrid = gridColumnaXUsuarioService.convertFromCarpetaBusquedaToItem(idCarpetaBusqueda);
		carpetaBusqueda = carpetaBusquedaService.ViewbyId(idCarpetaBusqueda);
		return "imprimirCB";
	}
        
        
        @SMDMethod
        public TipoDocumentoJSon getFlagTipoDocumento(String idTipoDocumento){
            Tipodocumento tipoDocumento = null;
            TipoDocumentoJSon tipoDocumentoJSon = new TipoDocumentoJSon();
            try{
                if (idTipoDocumento != null)
                  tipoDocumento = srvTipoDocumento.findByIdTipoDocumento(new Integer(idTipoDocumento));

                if (tipoDocumento == null)
                  return null;
                else{
                    tipoDocumentoJSon.setExterno(tipoDocumento.getExterno());
                    //tipoDocumentoJSon.setSicor(tipoDocumento.getSicor());
                }
            
            }catch(Exception e){
                return null;
            }    
            
            return tipoDocumentoJSon;
        }
        
	@SMDMethod
         public String exportarExcelCarpetaBusqueda(String valorsito) throws ServletException, IOException {
		    log.debug("-> [Action] DojoAction - exportarExcelCarpetaBusqueda():void ");
			HttpServletRequest request=ServletActionContext.getRequest();


			mapSession = ActionContext.getContext().getSession();
			String fileName = null;

			try{
					 List<Item> a = gridColumnaXUsuarioService.convertFromCarpetaBusquedaToItem(new Integer(valorsito));
					 carpetaBusqueda = carpetaBusquedaService.ViewbyId(new Integer(valorsito));
					 fileName = "CB_" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "_" +UUID.randomUUID().toString().concat(".xls");
					 List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				     Map<String, Object> m = null;

					 if (a!=null && a.size()>0){
						    for(Item objItem : a){
								 m = new HashMap<String, Object>();
				                 m.put("nroExpediente", objItem.getExpediente());
				                 m.put("nroDocumento", objItem.getDocumento());
				                 m.put("tipoDocumento", objItem.getTipodocumento());
				                 m.put("proceso", objItem.getProceso());
				                 m.put("fecha",  new SimpleDateFormat("dd-MM-yyyy hh:mm").format( objItem.getFecharecepcion()));
				                 m.put("area", objItem.getArea());
				                 m.put("asuntoDocumento",  objItem.getAsunto());
				                 m.put("asuntoExpediente", objItem.getAsuntoExpediente());
				                 m.put("cliente", objItem.getCliente());
				                 m.put("areaDestino", objItem.getAreadestino());
				                 m.put("prioridad", objItem.getExcprioridad());

				                 list.add(m);
							}
					  }


					InputStream reporteStream = getClass().getResourceAsStream("/org/osinerg/utils/ReporteCarpetaBusqueda.jasper");
					HashMap map = new HashMap();
					map.put("nombreCarpeta", carpetaBusqueda.getNombreCarpeta());

					JRXlsExporter exporter = new JRXlsExporter();
					JasperPrint print = JasperFillManager.fillReport(reporteStream,map,new JRBeanCollectionDataSource(list));

					exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
					exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, request.getRealPath("/")+ "export/" + fileName);
				    exporter.exportReport();
			}catch(Exception e){
				e.printStackTrace();
			}

	        return   request.getScheme() + "://" +   request.getServerName() +  ":" + request.getServerPort() + request.getContextPath() + "/export/" + fileName;
		}

         
         @SMDMethod
        public String exportarExcelPendientes() throws ServletException, IOException {
            log.debug("-> [Action] DojoAction - exportarExcelPendientes():void ");
            HttpServletRequest request=ServletActionContext.getRequest();
            ServletContext sc=ServletActionContext.getServletContext();
            mapSession = ActionContext.getContext().getSession();
            String fileName = null;
            String path = sc.getRealPath("/")+File.separator+"jasper"+File.separator;
            String rutaJasper="";
            Usuario objUsuario = null;
            
            try{
                objUsuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
                               if (objUsuario == null) {
                                               log.error("Expiro la sesion");    
                                               return null;
                               }
                BusquedaAvanzada Filtros = new BusquedaAvanzada();              
                List lstItem = gridColumnaXUsuarioService.getItems(Constantes.TIPO_GRID_PENDIENTES, objUsuario, Filtros);
                fileName = "R_Pendientes_" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "_" +UUID.randomUUID().toString().concat(".xls");
                List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
                Map<String, Object> m = null;

                if (lstItem!=null && lstItem.size()>0){
                    for(int i=0;i<lstItem.size();i++)
                    {
                        ItemUF item = (ItemUF) lstItem.get(i);
                        m = new HashMap<String, Object>();
                        m.put("nroTramite", item.getNroTramite());
                        m.put("documento", item.getDocumento());
                        m.put("asunto", item.getAsunto());
                        m.put("estado", item.getEstado());
                        m.put("propietario", item.getPropietario());
                        m.put("fechaRecepcion", item.getFecharecepcion());
                        m.put("expediente", item.getExpediente());
                        m.put("asuntoExpediente", item.getAsuntoExpediente() );
                        m.put("cliente", item.getCliente());
                        String[] tipoDocumento = item.getDocumento().split(" - ");
                        m.put("tipoDocumento", tipoDocumento[0]);

                        
                        list.add(m);
                    }
                }
                
                rutaJasper = path + "ReporteDocumentosPendientes.jasper";
                File f = new File(rutaJasper);
                JasperReport jasperReport = (JasperReport) JRLoader.loadObject(f);
                HashMap map = new HashMap();
                               map.put("TITULO", "REPORTE DE DOCUMENTOS PENDIENTES");
                map.put("IMAGEN", path + "logo_200_100.jpg");

                               JRXlsExporter exporter = new JRXlsExporter();
                               JasperPrint print = JasperFillManager.fillReport(jasperReport,map,new JRBeanCollectionDataSource(list));
                               exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
                               exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, request.getRealPath("/")+ "export/" + fileName);
                               exporter.exportReport();
            }catch(Exception e){
                               e.printStackTrace();
            }
            
            return   request.getScheme() + "://" +   request.getServerName() +  ":" + request.getServerPort() + request.getContextPath() + "/export/" + fileName;
        }

        
        @SMDMethod
        public String exportarExcelRecibidos() throws ServletException, IOException {
            log.debug("-> [Action] DojoAction - exportarExcelRecibidos():void ");
            HttpServletRequest request=ServletActionContext.getRequest();
            ServletContext sc=ServletActionContext.getServletContext();
            mapSession = ActionContext.getContext().getSession();
            String fileName = null;
            String path = sc.getRealPath("/")+File.separator+"jasper"+File.separator;
            String rutaJasper="";
           Usuario objUsuario = null;
            
            try{
                objUsuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
                               if (objUsuario == null) {
                                               log.error("Expiro la sesion");    
                                               return null;
                               }

                BusquedaAvanzada Filtros = new BusquedaAvanzada();
                List lstItem = gridColumnaXUsuarioService.getItems(Constantes.TIPO_GRID_DOCUMENTO, objUsuario, Filtros);
                               fileName = "R_Recibidos_" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "_" +UUID.randomUUID().toString().concat(".xls");
                               List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
                               Map<String, Object> m = null;

                               if (lstItem!=null && lstItem.size()>0){
                    for(int i=0;i<lstItem.size();i++)
                    {
                        ItemUF item = (ItemUF) lstItem.get(i);
                                               m = new HashMap<String, Object>();
                                               m.put("nroTramite", item.getNroTramite());
                                               m.put("documento", item.getDocumento());
                                               m.put("asunto", item.getAsunto());
                        m.put("estado", item.getEstado());
                        m.put("fechaRecepcion", item.getFecharecepcion());
                        m.put("expediente", item.getExpediente());
                        m.put("asuntoExpediente", item.getAsuntoExpediente());
                        m.put("cliente", item.getCliente());
                        m.put("areaRemitente", item.getArea());
                        m.put("remitente", item.getRemitente());
                        m.put("concesionario", item.getConcesionario());
                        String[] tipoDocumento = item.getDocumento().split(" - ");
                        m.put("tipoDocumento", tipoDocumento[0]);
                        
                                               list.add(m);
                    }
                }
                
                rutaJasper = path + "ReporteDocumentosRecibidos.jasper";
                File f = new File(rutaJasper);
                JasperReport jasperReport = (JasperReport) JRLoader.loadObject(f);
                HashMap map = new HashMap();
                               map.put("TITULO", "REPORTE DE DOCUMENTOS RECIBIDOS");
                map.put("IMAGEN", path + "logo_200_100.jpg");

                               JRXlsExporter exporter = new JRXlsExporter();
                               JasperPrint print = JasperFillManager.fillReport(jasperReport,map,new JRBeanCollectionDataSource(list));
                               exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
                               exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, request.getRealPath("/")+ "export/" + fileName);
                               exporter.exportReport();
            }catch(Exception e){
                               e.printStackTrace();
            }
            
            return   request.getScheme() + "://" +   request.getServerName() +  ":" + request.getServerPort() + request.getContextPath() + "/export/" + fileName;
        }
         
        @SMDMethod
        public String exportarExcelAtendidos() throws ServletException, IOException {
            log.debug("-> [Action] DojoAction - exportarExcelAtendidos():void ");
            HttpServletRequest request=ServletActionContext.getRequest();
            ServletContext sc=ServletActionContext.getServletContext();
            mapSession = ActionContext.getContext().getSession();
            String fileName = null;
            String path = sc.getRealPath("/")+File.separator+"jasper"+File.separator;
            String rutaJasper="";
            Usuario objUsuario = null;
            
            try{
                objUsuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
                               if (objUsuario == null) {
                                               log.error("Expiro la sesion");    
                                               return null;
                               }

                BusquedaAvanzada Filtros = new BusquedaAvanzada();
                List<Item> lstItem = gridColumnaXUsuarioService.getItems(Constantes.TIPO_GRID_DOCUMENTOS_ARCHIVADOS, objUsuario, Filtros);
                               fileName = "R_Atendidos_" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "_" +UUID.randomUUID().toString().concat(".xls");
                               List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
                               Map<String, Object> m = null;

                               if (lstItem!=null && lstItem.size()>0){
                    for(Item item : lstItem)
                    {
                        m = new HashMap<String, Object>();
                        m.put("nroTramite", item.getNroTramite());
                        m.put("documento", item.getDocumento());
                        m.put("asunto", item.getAsunto());
                        m.put("fechaAtencion", item.getFechaatender());
                        m.put("expediente", item.getExpediente());
                        m.put("asuntoExpediente", item.getAsuntoExpediente());
                        m.put("cliente", item.getCliente());
                        m.put("propietario", item.getPropietario());
                        String[] tipoDocumento = item.getDocumento().split(" - ");
                        m.put("tipoDocumento", tipoDocumento[0]);
                        
                        list.add(m);
                    }
                }
                
                rutaJasper = path + "ReporteDocumentosAtendidos.jasper";
                File f = new File(rutaJasper);
                JasperReport jasperReport = (JasperReport) JRLoader.loadObject(f);
                HashMap map = new HashMap();
                               map.put("TITULO", "REPORTE DE DOCUMENTOS ATENDIDOS");
                map.put("IMAGEN", path + "logo_200_100.jpg");

                               JRXlsExporter exporter = new JRXlsExporter();
                               JasperPrint print = JasperFillManager.fillReport(jasperReport,map,new JRBeanCollectionDataSource(list));
                               exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
                               exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, request.getRealPath("/")+ "export/" + fileName);
                               exporter.exportReport();
            }catch(Exception e){
                               e.printStackTrace();
            }
            
            return   request.getScheme() + "://" +   request.getServerName() +  ":" + request.getServerPort() + request.getContextPath() + "/export/" + fileName;
        }

        @SMDMethod
        public String exportarExcelEnviados() throws ServletException, IOException {
           log.debug("-> [Action] DojoAction - exportarExcelEnviados():void ");
            HttpServletRequest request=ServletActionContext.getRequest();
            ServletContext sc=ServletActionContext.getServletContext();
            mapSession = ActionContext.getContext().getSession();
            String fileName = null;
            String path = sc.getRealPath("/")+File.separator+"jasper"+File.separator;
            String rutaJasper="";
            Usuario objUsuario = null;
            
            try{
                objUsuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
                if (objUsuario == null) {
                    log.error("Expiro la sesion");    
                    return null;
                }

                BusquedaAvanzada Filtros = new BusquedaAvanzada();
                List<Item> lstItem = gridColumnaXUsuarioService.getItems(Constantes.TIPO_GRID_DOCUMENTOENVIADO, objUsuario, Filtros);
                fileName = "R_Documentos_Enviados_" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "_" +UUID.randomUUID().toString().concat(".xls");
                List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
                Map<String, Object> m = null;

                if (lstItem!=null && lstItem.size()>0){
                    for(Item item : lstItem)
                    {
                        m = new HashMap<String, Object>();
                        m.put("nroTramite", item.getNroTramite());
                        m.put("documento", item.getDocumento());
                        m.put("asunto", item.getAsunto());
                        m.put("estado", item.getEstado());
                        m.put("fechaEnvio", item.getFechaenvio());
                        m.put("expediente", item.getExpediente());
                        m.put("asuntoExpediente", item.getAsuntoExpediente());
                        m.put("cliente", item.getCliente());
                        m.put("destinatario", item.getDestinatario());
                        String[] tipoDocumento = item.getDocumento().split(" - ");
                        m.put("tipoDocumento", tipoDocumento[0]);
                        
                        list.add(m);
                    }
                }
                
                rutaJasper = path + "ReporteDocumentosEnviados.jasper";
                File f = new File(rutaJasper);
                JasperReport jasperReport = (JasperReport) JRLoader.loadObject(f);
                HashMap map = new HashMap();
                map.put("TITULO", "REPORTE DE DOCUMENTOS ENVIADOS");
                map.put("IMAGEN", path + "logo_200_100.jpg");

                JRXlsExporter exporter = new JRXlsExporter();
                JasperPrint print = JasperFillManager.fillReport(jasperReport,map,new JRBeanCollectionDataSource(list));
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
                exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, request.getRealPath("/")+ "export/" + fileName);
                exporter.exportReport();
            }catch(Exception e){
                e.printStackTrace();
            }
            
            return   request.getScheme() + "://" +   request.getServerName() +  ":" + request.getServerPort() + request.getContextPath() + "/export/" + fileName;
        }

        
        @SMDMethod
        public String exportarExcelMisDocumentos() throws ServletException, IOException {
            log.debug("-> [Action] DojoAction - exportarExcelMisDocumentos():void ");
            HttpServletRequest request=ServletActionContext.getRequest();
            ServletContext sc=ServletActionContext.getServletContext();
            mapSession = ActionContext.getContext().getSession();
            String fileName = null;
            String path = sc.getRealPath("/")+File.separator+"jasper"+File.separator;
            String rutaJasper="";
            Usuario objUsuario = null;
            
            try{
                objUsuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
                               if (objUsuario == null) {
                                               log.error("Expiro la sesion");    
                                               return null;
                               }

                BusquedaAvanzada Filtros = new BusquedaAvanzada();
                List lstItem = gridColumnaXUsuarioService.getItems(Constantes.TIPO_GRID_EXPEDIENTE, objUsuario, Filtros);
                               fileName = "R_Mis_Documentos_" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "_" +UUID.randomUUID().toString().concat(".xls");
                               List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
                               Map<String, Object> m = null;

                               if (lstItem!=null && lstItem.size()>0){
                    for(int i=0;i<lstItem.size();i++)
                    {
                        ItemUF item = (ItemUF) lstItem.get(i);
                                               m = new HashMap<String, Object>();
                                               m.put("nroTramite", item.getNroTramite());
                                               m.put("documento", item.getDocumento());
                                               m.put("asunto", item.getAsunto());
                        m.put("estado", item.getEstado());
                        m.put("fechaCreacion", item.getFechacreacion());
                        m.put("expediente", item.getExpediente());
                        m.put("asuntoExpediente", item.getAsuntoExpediente());
                        m.put("cliente", item.getCliente());
                        m.put("concesionario", item.getCliente());
                        String[] tipoDocumento = item.getDocumento().split(" - ");
                        m.put("tipoDocumento", tipoDocumento[0]);
                        
                                               list.add(m);
                    }
                }
                
                rutaJasper = path + "ReporteMisDocumentos.jasper";
                File f = new File(rutaJasper);
                JasperReport jasperReport = (JasperReport) JRLoader.loadObject(f);
                HashMap map = new HashMap();
                               map.put("TITULO", "REPORTE DE MIS DOCUMENTOS");
                map.put("IMAGEN", path + "logo_200_100.jpg");

                               JRXlsExporter exporter = new JRXlsExporter();
                               JasperPrint print = JasperFillManager.fillReport(jasperReport,map,new JRBeanCollectionDataSource(list));
                               exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
                               exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, request.getRealPath("/")+ "export/" + fileName);
                               exporter.exportReport();
            }catch(Exception e){
                               e.printStackTrace();
            }
            
            return   request.getScheme() + "://" +   request.getServerName() +  ":" + request.getServerPort() + request.getContextPath() + "/export/" + fileName;
        }     
        
        @SMDMethod
        public String exportarExcelFiltrosAvanzados (String sTipoFiltro, BusquedaAvanzada objFiltro, String arrFecha[]) {
            log.debug("-> [Action] DojoAction - exportarExcelFiltrosAvanazados():String ");
            HttpServletRequest request=ServletActionContext.getRequest();
            ServletContext sc=ServletActionContext.getServletContext();
            mapSession = ActionContext.getContext().getSession();
            String fileName = null;
            String path = sc.getRealPath("/")+File.separator+"jasper"+File.separator;
            String rutaJasper="";
            Usuario objUsuario = null;
            Usuario objUsuarioFiltro = null;
            String funcionUsuario = "";
            
            try{
                objUsuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
                               if (objUsuario == null) {
                                               log.error("Expiro la sesion");    
                                               return null;
                               }
  
                if (objFiltro.getEstadoexpediente().equals("*")) {
                    objFiltro.setEstadoexpediente("");
                               }
                if(objFiltro.getPropietario() != null)
                {
                    if(!objFiltro.getPropietario().equals(""))
                    {
                        objUsuarioFiltro = usuarioService.findByIdUsuario(new Integer(objFiltro.getPropietario()));
                    }
                }
                if(objUsuarioFiltro != null)
                    funcionUsuario = objUsuarioFiltro.getIdfuncion().toString();
                String[] sqlQueryDinamico = new String[1]; sqlQueryDinamico[0] = "";                
                List<Item> lstItem = gridColumnaXUsuarioService.getItems_BusquedaAvanzada(sTipoFiltro, objFiltro, arrFecha, sqlQueryDinamico, "reporte", objUsuario.getIdUnidadPerfil().toString(), funcionUsuario);
                               fileName = "R_Filtros_Avanzados_" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "_" +UUID.randomUUID().toString().concat(".xls");
                               List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
                               Map<String, Object> m = null;
                
                if (lstItem!=null && lstItem.size()>0){
                    for(Item item : lstItem)
                    {
                        m = new HashMap<String, Object>();
                        m.put("nroTramite", item.getNroTramite());
                        m.put("documento", item.getNrodocumento());
                        m.put("asunto", item.getAsuntodocumento());
                        m.put("estado", item.getEstado());
                        m.put("fechaCreacion", item.getFecharecepcion());
                        m.put("expediente", item.getExpediente());
                        m.put("asuntoExpediente", item.getAsuntoExpediente());
                        m.put("cliente", item.getCliente());
                        m.put("propietario", item.getPropietario());
                        m.put("autor", item.getAutor());
                        
                        list.add(m);
                    }
                }
                
                rutaJasper = path + "ReporteDocumentosFiltros.jasper";
                log.info("rutaJasper:"+rutaJasper);
                File f = new File(rutaJasper);
                JasperReport jasperReport = (JasperReport) JRLoader.loadObject(f);
                HashMap map = new HashMap();
                               map.put("TITULO", "REPORTE DE DOCUMENTOS POR FILTROS");
                map.put("IMAGEN", path + "logo_200_100.jpg");

                               JRXlsExporter exporter = new JRXlsExporter();
                               JasperPrint print = JasperFillManager.fillReport(jasperReport,map,new JRBeanCollectionDataSource(list));
                               exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
                               exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, request.getRealPath("/")+ "export/" + fileName);
                               exporter.exportReport();
            }catch(Exception e){
                               e.printStackTrace();
            }
            
            return   request.getScheme() + "://" +   request.getServerName() +  ":" + request.getServerPort() + request.getContextPath() + "/export/" + fileName;
        }

         
	  @SuppressWarnings({ "unchecked", "rawtypes" })
	  public HSSFWorkbook getCarpetaBusquedaHSSFW(List listResultados, String ruta, String titulo){
			{
				try{
				    Map beans = new HashMap();
			        SimpleDateFormat dateFormat =  new SimpleDateFormat("dd/MM/yyyy");
			        beans.put("row", listResultados);
			        beans.put("titulo", titulo);
			        beans.put("dateFormat", dateFormat);
			        XLSTransformer transformer = new XLSTransformer();
			        File plantilla  = new File (ruta);
			        FileInputStream plantillaStream =  new FileInputStream(plantilla);
			        HSSFWorkbook z = transformer.transformXLS ( plantillaStream , beans ) ;
			        return  z;
				} catch (Exception e) {
					e.printStackTrace();
					return null ;
				}
		    }
		}
          
        @SMDMethod  
        public void cambiarPerfilUsuario(String perfil){
        	try{
            log.debug("-> [Action] DojoAction - cambiarPerfilUsuario():perfil "+perfil);
            perfil = perfil.replace("|", "-");
            String[] parts = perfil.split("-");
            String idunidad = parts[0]; 
            String idfuncion = parts[1]; 
            String idusuarioperfil = parts[2]; 
            
            mapSession = ActionContext.getContext().getSession();
            Usuario objUsuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
            
            objUsuario.setIdUnidadPerfil(new Integer(idunidad));
            objUsuario.setIdFuncionPerfil(new Integer(idfuncion));
            objUsuario.setIdUsuarioPerfil(new Integer(idusuarioperfil));
            
            List<Usuarioxunidadxfuncion> lista =usuarioxunidadxfuncionDAO.getUsuarioByUnidadByFuncionRol(objUsuario);
            if (lista!=null && lista.size()>0){
              Rol rol = rolService.findByIdRol(lista.get(0).getIdrol());
              List<Perfil> perfiles=new ArrayList<Perfil>();
              perfiles.add(rol.getIdperfil());
              Map<String,Integer> recursos =srvPerfil.getRecursosPorPerfiles(perfiles);
              objUsuario.setIdRolPerfil(rol.getIdrol());
              mapSession.put(Constantes.SESSION_ROLCARGO, rol.getIdrol().toString());
              mapSession.put(Constantes.SESSION_RECURSO,recursos);
            }
            
        	}catch(Exception e){
        		e.printStackTrace();
        	}
           
        }  

	public void cambiarAgrupacionUsuario(){
		log.debug("-> [Action] DojoAction - cambiarAgrupacionUsuario():void ");
		mapSession = ActionContext.getContext().getSession();
		Usuario usuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
                Integer u = usuario.getIdUsuarioPerfil();
                Integer u1 = usuario.getIdUnidadPerfil();
                Integer u2 = usuario.getIdFuncionPerfil();
                
		usuario = usuarioService.findByIdUsuario(usuario.getIdusuario());

		if (usuario.getFlagdocumentocf()==null)
			usuario.setFlagdocumentocf("0");

		if(usuario.getBandejaAgrupada() != null && usuario.getBandejaAgrupada().equals(Constantes.Si)){
			usuario.setBandejaAgrupada(Constantes.No);
		}else{
			usuario.setBandejaAgrupada(Constantes.Si);
		}
		usuario = usuarioService.guardarUsuario(usuario);
                usuario.setIdUsuarioPerfil(u);
                usuario.setIdUnidadPerfil(u1);
                usuario.setIdFuncionPerfil(u2);
		mapSession.put(Constantes.SESSION_USUARIO,usuario);
	}

	public String mostrarFiltrosBandeja(){
		return "filtros";
	}

	@SMDMethod
	public String readFile() {
		return UUID.randomUUID().toString();
	}

	@SuppressWarnings("unchecked")
	@SMDMethod
	public int isThereAttachment(Integer idExpediente) {
		log.debug("-> [Action] DojoAction - isThereAttachment():int ");
		try {
			List<ArchivoTemporal> lstArchivo = null;
			Map<String, List<ArchivoTemporal>> mapUpload = null;

			if (idExpediente == null) {
				log.error("No se recibio un idExpediente correcto");

				return -1;
			}

			if (log.isDebugEnabled()) {
				log.debug("Verificando archivos del idExpediente [" + idExpediente + "]");
			}

			mapSession = ActionContext.getContext().getSession();
			mapUpload = (Map<String, List<ArchivoTemporal>>) mapSession.get(Constantes.SESSION_UPLOAD_LIST);

			if (mapUpload == null || mapUpload.values().isEmpty() || ((mapUpload.get(Constantes.UPLOAD_ARCHIVO1) == null || mapUpload.get(Constantes.UPLOAD_ARCHIVO1).isEmpty()) && (mapUpload.get(Constantes.UPLOAD_ARCHIVO2) == null || mapUpload.get(Constantes.UPLOAD_ARCHIVO2).isEmpty()))) {
				log.warn("No hay archivos en el upload temporal");

				return 0;
			}

			lstArchivo = mapUpload.get(Constantes.UPLOAD_ARCHIVO1);

			if (lstArchivo != null && lstArchivo.size() > 0) {
				if (log.isDebugEnabled()) {
					log.debug("Verificando la duplicidad de [" + lstArchivo.size() + "] archivo(s)");
				}

				for (ArchivoTemporal archivoUpload : lstArchivo) {
					List<Archivo> archivosEnDB = archivoService.findLstByExpediente(idExpediente);

					if (archivosEnDB != null && archivosEnDB.size() >= 0) {
						for (Archivo archivo : archivosEnDB) {
							if (log.isDebugEnabled()) {
								log.debug("Verificando archivoUpload [" + archivoUpload.getSNombre() + "] archivoEnDB [" + archivo.getNombreReal() + "]");
							}

							if (archivoUpload.getSNombre().equals(archivo.getNombreReal()) && archivo.getEstadoDigitalizacion() != Constantes.ESTADO_RECHAZADO) {
								log.warn("El archivo que se intenta subir ya existe en el repositorio");

								return 2;
							}
						}
					}
				}
			}

			return 1;
		} catch (Exception e) {
			log.error(e.getMessage(), e);

			return -1;
		}
	}

	@SuppressWarnings("unchecked")
	@SMDMethod
	public ObjetoJSON buildGrid() {
		ObjetoJSON objJSON = new ObjetoJSON();
		try{
		log.debug("-> [Action] DojoAction - buildGrid():ObjetoJSON ");
		Integer iTipoGrid = null;
		List<Estructura> lstEstructura = null;
		List<Gridcolumnaxusuario> lstGridColumnaXUsuario = null;
		List<Item> lstItem = null;
		Map<String, Integer> mapRecurso = null;
		Usuario objUsuario = null;
		setMapSession(ActionContext.getContext().getSession());
		mapRecurso = (Map<String, Integer>) getMapSession().get(Constantes.SESSION_RECURSO);
		objUsuario = (Usuario) getMapSession().get(Constantes.SESSION_USUARIO);
		Rol rol = objUsuario.getRol();
		if (rol == null) {
			rol = rolService.findByNombre(Constantes.ROL_USUARIO_FINAL);
		}
		lstGridColumnaXUsuario = gridColumnaXUsuarioService.findByIdUsuario(objUsuario.getIdusuario());
		// usamos el dao de perfil para evitar problemas de sesion con la base
		// de datos
		List<GridXGridColumna> delPerfil = perfilDao.findByIdPerfil(rol.getIdperfil().getIdperfil()).getColumnas();
		log.debug("Rol [" + rol.getNombre() + "] Perfil [" + rol.getIdperfil().getNombre() + "] numero de columnas [" + delPerfil.size() + "]");
		
		if (mapRecurso.get("MPGrd") == 1 || mapRecurso.get("DigGrd") == 1 || mapRecurso.get("QASGrd") == 1 || mapRecurso.get("UsuFinGrd") == 1) {
			iTipoGrid = 1;
		}
		lstEstructura = gridColumnaXUsuarioService.buildEstructura(lstGridColumnaXUsuario, delPerfil, objUsuario.getIdusuario());
		lstItem = gridColumnaXUsuarioService.buildItems(lstEstructura, objUsuario, iTipoGrid);
		objJSON.setItems(lstItem);
		objJSON.setStructure(lstEstructura);
		objJSON.setHoraservidor("" + System.currentTimeMillis());
		Parametro parametro1 = parametroService.findByTipoUnico(Constantes.PARAMETRO_NOTIFICACION_AMARILLA);
		Parametro parametro2 = parametroService.findByTipoUnico(Constantes.PARAMETRO_NOTIFICACION_ROJA);
		objJSON.setParametroalerta1(parametro1.getValor());
		objJSON.setParametroalerta2(parametro2.getValor());
		
		}catch(Exception e){
			e.printStackTrace();
		}
		return objJSON;
	}

	/**
	 * Guarda las caracteristicas del grid del usuario
	 *
	 * @param grid
	 *            las columnas del grid
	 * @author German Enriquez
	 */

	@SMDMethod
	public void guardarGridUsuario(GridUsuario[] grid) {
		log.debug("-> [Action] DojoAction - guardarGridUsuario():void ");

		// iniciarFactorias();
		setMapSession(ActionContext.getContext().getSession());
		try {
			Usuario usuario = (Usuario) getMapSession().get(Constantes.SESSION_USUARIO);
			if (usuario != null) {
				gridColumnaXUsuarioService.guardarGridUsuario(grid, usuario.getIdusuario());
				log.info("Se guardo el estado de las columnas para el usuario " + usuario.getNombres() + " " + usuario.getApellidos());
			}
		} catch (IllegalStateException e) {
			log.warn("La sesion ya ha sido invalidada, no guardamos nada");
		}
	}

	@SMDMethod
	public ObjetoJSON getJerarquia() {
		log.debug("-> [Action] DojoAction - getJerarquia():ObjetoJSON ");

		ObjetoJSON objJSON = new ObjetoJSON();
		objJSON.setIdentifier("id"); 
		objJSON.setLabel("name");
		objJSON.setItems(usuarioService.getJerarquia());
		return objJSON;
	}
        
        @SMDMethod 
	public String verificarDocumentoPublicar(String idDocumento, String accion) {
             log.debug("-> [Action] DojoAction - verificarDocumentoPublicar():ObjetoJSON ");
             try{
                 String publicar = "N";
                 String expediente = "N";
               
                 
                 if (accion != null && accion.equals("E")) {
			Documentoenviado documentoEnviado = documentoenviadoService.findByIddocumentoenviado(new Integer(idDocumento));
              	        if(documentoEnviado.getTipoEnvio().equals(Constantes.TIPO_ENVIO_TRANSFERIR)){
			    Trazabilidaddocumento trazdoc = new Trazabilidaddocumento();
			    trazdoc = trazabilidadDocumentoService.findByIdTrazabilidadDocumento(documentoEnviado.getIdTrazabilidadEnvio());
		            idDocumento = trazdoc.getDocumento().getIdDocumento().toString();
		        }else if(documentoEnviado.getTipoEnvio().equals(Constantes.TIPO_ENVIO_MULTIPLE)){
                            Trazabilidadapoyo 	trazabilidadapoyo = new Trazabilidadapoyo();
		            trazabilidadapoyo = trazabilidadapoyoService.findByIdTrazabilidadApoyo(documentoEnviado.getIdTrazabilidadEnvio());
		            idDocumento = trazabilidadapoyo.getDocumento().toString();
		        }else if(documentoEnviado.getTipoEnvio().equals(Constantes.TIPO_ENVIO_NOTIFICAR)){
			    Notificacion notificacion = new Notificacion();
			    notificacion = notificacionService.buscarObjPorID(documentoEnviado.getIdTrazabilidadEnvio());
			    idDocumento = notificacion.getIddocumento().getIdDocumento().toString();
			}
		 }else{
                     if (accion!=null && accion.equals("P")){
                         DocumentoPendiente documentoPendiente = documentoPendienteDAO.findByIdDocumentoPendiente(new Integer(idDocumento));
                         idDocumento = documentoPendiente.getIddocumento().toString();
                     }else{
                         if (accion!=null && accion.equals("A")){
                             DocumentoAtendido documentoAtendido = documentoAtendidoDAO.findByIdDocumentoAtendido(new Integer(idDocumento));
                             idDocumento = documentoAtendido.getIddocumento().toString();
                         }
                     }
                 }
                 
                 mapSession = ActionContext.getContext().getSession();
		 Usuario usuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
                
                 Documento d = documentoService.findByIdDocumento(new Integer(idDocumento));
                 
                 if (d.getDocumentoreferencia()!=null)
                    d = documentoService.findByIdDocumento(d.getDocumentoreferencia()); 
                 
                 if (d.getTipoDocumento().getExternoQR()!=null && d.getTipoDocumento().getExternoQR().equals("1") && d.getID_EXTERNO().toString().equals(Constantes.COD_TRAMITE_INTERNO)){
                     publicar = "S";
                 }
                 
                 LegajoDocumento legajoDocumento = new LegajoDocumento();
                 legajoDocumento.setIdDocumento(d.getIdDocumento());
                 List<LegajoDocumento> lst = legajoDocumentoService.findDocumento(legajoDocumento, usuario);
                
                 if (lst!=null && lst.size()>0){
                    expediente = "S"; 
                 }
                 
                 return publicar + expediente;
             }catch(Exception e){
                 e.printStackTrace();
                 return "NN";
             } 
                     
	}
        
        @SMDMethod
	public ObjetoJSON getArbolLegajo(Integer idLegajo, Integer idDocumento, String valor) {
		log.debug("-> [Action] DojoAction - getArbolLegajo():void ");
           try{       
                mapSession = ActionContext.getContext().getSession();
	        Usuario usuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
                List<LegajoDocumento> lst = null; 
                
                if (idLegajo == null || idLegajo==0){
                   Documento d = documentoService.findByIdDocumento(idDocumento);
                   
                   if (d.getDocumentoreferencia()!=null)
                     idDocumento = d.getDocumentoreferencia();   
                       
                   LegajoDocumento legajoDocumento = new LegajoDocumento();
                   legajoDocumento.setIdDocumento(idDocumento);
                   lst = legajoDocumentoService.findDocumento(legajoDocumento, usuario);
                }else{
                   LegajoDocumento legajoDocumento = new LegajoDocumento();
                   legajoDocumento.setIdLegajo(idLegajo);
                   lst = new ArrayList<LegajoDocumento>();
                   lst.add(legajoDocumento);
                }
                
                List<NodoArbol> lstNodo = null;
                List<NodoArbol> lstNodoFinal = new ArrayList<NodoArbol>();
                List<Object> lstArbol = new ArrayList<Object>();
              
                if (lst!=null){
                    for(int i=0;i<lst.size();i++){
                       lstNodo = legajoService.getArbolLegajo(lst.get(i).getIdLegajo(), idDocumento, valor);
                       lstArbol.add(lstNodo);
                    }

                    for(int j=0;j<lstArbol.size();j++){
                        lstNodo = (List<NodoArbol>)lstArbol.get(j);
                        for(int k=0;k<lstNodo.size();k++){
                            NodoArbol nodito = lstNodo.get(k);
                            lstNodoFinal.add(nodito);
                        }
                    }
                }
                
                ObjetoJSON objJSON = new ObjetoJSON();
		objJSON.setIdentifier("id");
		objJSON.setLabel("name");
		objJSON.setItems(lstNodoFinal);
               return objJSON;
            }catch(Exception e){
                e.printStackTrace();
                return new ObjetoJSON();
            }  
	}

	@SMDMethod
	public ObjetoJSON getArbolDocumentoExpediente(Integer idExpediente, Integer idDocumento, String valor) {
		log.debug("-> [Action] DojoAction - getArbolDocumentoExpediente():void ");
		ObjetoJSON objJSON = new ObjetoJSON();
		objJSON.setIdentifier("id");
		objJSON.setLabel("name");
		objJSON.setItems(expedienteService.getDojoExpedienteTree(idExpediente, idDocumento, valor));
               return objJSON;
	}
        
        @SMDMethod
	public String validarDataNavegador() {
            log.debug("-> [Action] DojoAction - validarDataNavegador():void ");
            
            try{
                 mapSession = ActionContext.getContext().getSession();
	         Usuario usuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
                 List<ItemUF> lst = documentoService.findDocumentosUsuarioFinal(usuario, "R");
                 if (lst == null || lst.size()==0)
                     return "0";
                 else{
                    boolean bandera = false;  
                    for (int i=0;i<lst.size();i++){
                         String icono = lst.get(i).getIconoDocumento();
                         if (icono!=null && !icono.equals("")){
                             bandera = true;
                             return "1";
                         }
                    } 
                    
                    if (!bandera)
                        return "0";
                 }
                 
                 return "0";
            }catch(Exception e){
                return "0";
            }
        }
        
        @SMDMethod
	public ObjetoJSON getArbolNavegador(String tipoVista) {
		log.debug("-> [Action] DojoAction - getArboNavegador():void ");
                mapSession = ActionContext.getContext().getSession();
	        Usuario usuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
                
                ObjetoJSON objJSON = new ObjetoJSON();
                objJSON.setIdentifier("id");
		objJSON.setLabel("name");
                
                if (tipoVista.equals("NT"))
		  objJSON.setItems(documentoService.getArbolTipoDocumentosNavegador(usuario, "NT"));
                
                if (tipoVista.equals("NU"))
		  objJSON.setItems(documentoService.getArbolUnidadNavegador(usuario, "NU"));
                
                if (tipoVista.equals("ND"))
		  objJSON.setItems(documentoService.getArbolDocumentosNavegador(usuario, "ND"));
	        return objJSON;
	}
        
        @SMDMethod
	public ObjetoJSON getArbolDocumentoPublicar(Integer idExpediente, Integer idDocumento) {
		log.debug("-> [Action] DojoAction - getArbolDocumentoPublicar():void ");
		ObjetoJSON objJSON = new ObjetoJSON();
		objJSON.setIdentifier("id");
		objJSON.setLabel("name");
		objJSON.setItems(expedienteService.getDojoDocumentoPublicarTree(idExpediente, idDocumento));
               return objJSON;
	}
        
        @SMDMethod
	public String existsReferencias(Integer idDocumento) {
            return documentoReferenciaService.existsReferencias(idDocumento);
        }
        
        @SMDMethod
	public String getDocumentoInicioFlujo(String idDocumento) {
            try{
                
                Documento doc = documentoDao.findByIdDocumento(new Integer(idDocumento));

                if (doc.getOrigen().toString().equals(idDocumento)){
                    return "1";
                }else{
                    return "0";
                }
            }catch(Exception e){
                return "2";
            }    
        }
        
        public String getVerReferencia(Integer padre, Integer hijo){
             mapSession = ActionContext.getContext().getSession();
             Usuario objUsuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
             Documento documento = documentoService.findByIdDocumento(padre);
            
             if(documento.getAutor().getIdusuario().intValue() == objUsuario.getIdUsuarioPerfil().intValue() &&
                documento.getUnidadautor().intValue() == objUsuario.getIdUnidadPerfil().intValue()){
                
                Trazabilidaddocumento t = new Trazabilidaddocumento();
                Documento d = new Documento();
                Usuario u = new Usuario();
                Expediente exp = new Expediente();
                Usuario propietario = new Usuario();
                documento = documentoService.findByIdDocumento(hijo);

                propietario.setIdusuario(documento.getPropietario().getIdusuario());
                exp.setId(documento.getExpediente().getId());
                u.setIdusuario(objUsuario.getIdUsuarioPerfil());
                d.setIdDocumento(documento.getIdDocumento());
                d.setDocumentoreferencia(documento.getDocumentoreferencia());
                d.setPropietario(propietario);
                d.setExpediente(exp);
                t.setDocumento(d);
                t.setRemitente(u);
                t.setDestinatario(u);

                Usuario usuarioFinal = usuarioService.findByIdUsuario(objUsuario.getIdUsuarioPerfil());

                if (trazabilidadDocumentoService.contarListTotalTrazabilidadesExpediente(t)==0 && usuarioFinal.getFlagviewtrazabilidad()!=null && usuarioFinal.getFlagviewtrazabilidad().equals("0")){
                   return "N";
                } 
                
                if(documento.getConfidencial().equals(Constantes.Si)){
                   List<Integer> permitidos = documentoService.getUsuariosPermitidos(documento.getDocumentoreferencia() != null ? documento.getDocumentoreferencia() : documento.getIdDocumento());
                   if(!permitidos.contains(new BigDecimal(objUsuario.getIdUsuarioPerfil())))
                        return "N";
                }
                
                return "S";
                 
             }else{
                return "N";
             }                
             
        }
        
        @SMDMethod
         public ObjetoJSON getArbolDocumentoReferencial(Integer idDocumento, String opcion) {
                log.debug("-> [Action] DojoAction - getArbolDocumentoReferencial():void ");
               
                Documento docReferencia = documentoDao.findByIdDocumento(idDocumento);
                
                if (docReferencia.getDocumentoreferencia()!=null)
                   idDocumento = docReferencia.getDocumentoreferencia(); 
             
                List<Documento> lista = documentoService.getReferenciaDocumento(idDocumento);
                List<NodoArbol> lstTreeDocumento = new ArrayList<NodoArbol>();
                List<NodoArbol> allElements = new ArrayList<NodoArbol>();
              
                if (lista!=null){
                    for (int i=0;i<lista.size();i++){
                        NodoArbol objETDocumento = new NodoArbol(
                        false,
                        "D-" +idDocumento+ "-" +  lista.get(i).getIdDocumento() +  "-" + (opcion==null?"":opcion),
                        lista.get(i).getNumeroDocumento(), 
                        null,
                        lista.get(i).getVER_DOCUMENTO(),
                                getVerReferencia(idDocumento, lista.get(i).getIdDocumento()),
                        lista.get(i).getNroReferencias());
                        lstTreeDocumento.add(objETDocumento);
                    }
                }
    
                Documento d = documentoDao.findByIdDocumento(idDocumento);
                NodoArbol objDocumentoTree = new NodoArbol(true, "DP-" + idDocumento + "-" + idDocumento +"-" + (opcion==null?"":opcion), d.getTipoDocumento().getNombre() + " - " + d.getNumeroDocumento(), lstTreeDocumento,"A","N",""); // new
                allElements.add(objDocumentoTree);
                allElements.addAll(lstTreeDocumento);
        
                ObjetoJSON objJSON = new ObjetoJSON();
                objJSON.setIdentifier("id");
                objJSON.setLabel("name");
                objJSON.setItems(allElements);
                
                return objJSON;
        }
         
        @SMDMethod
        public String updCheckPermiso(Integer iddocumento, Integer iddocref, String estado) {
            log.debug("-> [Action] DojoAction - updCheckPermiso():iddocumento-" + iddocumento+"-iddocref:"+iddocref + "-estado:" + estado);
            Usuario usuario = (Usuario) ActionContext.getContext().getSession().get(Constantes.SESSION_USUARIO);
            DocumentoReferencia referencia = documentoReferenciaService.getReferenciaDocumento(iddocumento,iddocref);

            log.debug("-> [Action] DojoAction - updCheckPermiso():"+referencia.getIdRef()+"***");
            referencia.setFechaModificacion(new Date());
            referencia.setUsuarioModificacion(usuario.getIdusuario());
            referencia.setVerDocumento(estado);
            documentoReferenciaService.saveDocumentoReferencia(referencia);
            return "";

         }

	
        @SMDMethod
	public String enviarDocumento(Integer idExterno){
            try{
                 mapSession = ActionContext.getContext().getSession();
                 Usuario objUsuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
                 return documentoService.enviarDocumentoVirtual(idExterno, objUsuario);
            }catch(Exception e){
              e.printStackTrace();
            }
                
            return "";
        }
        
        @SMDMethod
	public String validarExistRecepcion(Integer codigoVirtual){
             try{
                 List<Documento> lst = documentoService.findByIdDocVirtual(codigoVirtual);
                 if (lst!=null && lst.size()>0){
                     return "1";
                 }
                 
                 return "0";
             }catch(Exception e){
                 return "-1";
             }            
        }
        
        @SMDMethod
        public String getCantidadDocumentosVirtuales(String grid){
            if (grid.equals(Constantes.TIPO_GRID_RECEPCION_VIRTUAL)){
                return recepcionVirtualDAO.findByCantidadesDocumentosVirtuales();    
            }
            
            if (grid.equals(Constantes.TIPO_GRID_DESPACHO_VIRTUAL)){
                return despachoVirtualDAO.findByCantidadesDocumentosVirtuales();
            }
            
            return "";
        }
        
        @SMDMethod
	public String enviarCargo(Integer idExterno, String tipoArchivo, String tipoFirma, String accion){
            if (accion.equals("T")){
                try{
                     IotdtmDocExterno iotdtmDocExterno = documentoExternoVirtualDAO.buscarDocumentoVirtual(idExterno);
                     Documento d = documentoService.findByIdDocumento(iotdtmDocExterno.getSidrecext().getIddocumento());
                     List<Archivo> lst = archivoService.findArchivoTipoFirmardo(d.getDocumentoreferencia()==null?d.getIdDocumento():d.getDocumentoreferencia(), tipoArchivo.charAt(0), "FI");

                     if (lst==null || lst.size()==0){
                       lst = archivoService.findArchivoTipoFirmardo(d.getDocumentoreferencia()==null?d.getIdDocumento():d.getDocumentoreferencia(), tipoArchivo.charAt(0), "FIO");  
                       if (lst==null || lst.size()==0)
                          return "0";
                     } 
                     documentoService.actualizarDatosRecepcionVirtual(idExterno);
                }catch(Exception e){
                     e.printStackTrace();
                     return "2";
                }
            }    
            
            try{
                 return documentoService.enviarCargoRecepcionVirtual(idExterno);
            }catch(Exception e){
                 e.printStackTrace();
                 return "1";
            }
        }

	@SMDMethod
	public String enviaGridSupervisor(String idSupervisor, String fechaEntrega, String fechaEnvio) {
		log.debug("-> [Action] DojoAction - enviaGridSupervisor():String ");

		SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
		Supervisor supervisor = supervisorService.findById(Integer.parseInt(idSupervisor));
		if (supervisor != null) {
			if (log.isDebugEnabled()) {
				log.debug("id=" + idSupervisor);
				log.debug("fechaEntrega=" + fechaEntrega);
				log.debug("fechaEnvio=" + fechaEnvio);
			}
			if (fechaEntrega != null && !fechaEntrega.equals("")) {
				try {
					supervisor.setFechadentrega(formatoFecha.parse(fechaEntrega));

				} catch (ParseException e) {
					return "La fecha ingresada no es valida";
				}
			} else {
				supervisor.setFechadentrega(null);
			}

			if (fechaEnvio != null && !fechaEnvio.equals("")) {
				try {
					supervisor.setFechadevolucion(formatoFecha.parse(fechaEnvio));

				} catch (ParseException e) {
					return "La fecha ingresada no es valida";
				}
			} else {
				supervisor.setFechadevolucion(null);
			}
			supervisorService.saveSupervisor(supervisor);
		} else {
			return "No se pudo guardar el supervisor.";
		}
		return "Cambios guardados Correctamente";
	}

	@SuppressWarnings("unchecked")
	@SMDMethod
	public String enviaGridReemplazo(String str) throws Exception {
		log.debug("-> [Action] DojoAction - enviaGridReemplazo):String ");

		SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		// try{
		// iniciarFactorias();
		Usuario usuario = (Usuario) ActionContext.getContext().getSession().get(Constantes.SESSION_USUARIO);
		// UsuarioService usuarioService=serviceFactory.getUsuarioService();
		// ReemplazoService reemplazoDao=(ReemplazoService)
		// beanFactory.getBean("reemplazoService");
		reemplazoDao.deleteByIdreemplazado(usuario.getIdusuario());
		log.debug(str);
		Object obj = JSONValue.parse(str);
		JSONObject objeto = (JSONObject) obj;
		JSONArray items = (JSONArray) objeto.get("items");
		Iterator<JSONObject> iter = items.iterator();
		while (iter.hasNext()) {
			JSONObject item = iter.next();
			// log.debug(item);
			if (item.size() > 5) { // esta validacion puede ser mejor
				Reemplazo reemplazo = new Reemplazo();
				reemplazo.setEstado('A');
				reemplazo.setFechafinalreemplazo(formatoFecha.parse((String) item.get("fechafin") + " 23:59"));
				reemplazo.setFechainicialreemplazo(formatoFecha.parse((String) item.get("fechainicio") + " 00:00"));
				reemplazo.setIdreemplazado(usuario.getIdusuario());
				// log.debug(item.get("idreemplazo"));
				Usuario usuariotemp = usuarioService.findByIdUsuario(Integer.valueOf(item.get("idreemplazo").toString()));
				reemplazo.setIdusuario(usuariotemp);
				reemplazo.setIdproceso(Integer.valueOf(item.get("id").toString()));
				// log.debug(item);
				reemplazoDao.saveReemplazo(reemplazo);
				if (reemplazo.getIdproceso() != null) {
					Proceso proceso = ((ProcesoService) WebApplicationContextUtils.getWebApplicationContext(ServletActionContext.getRequest().getSession().getServletContext()).getBean("procesoService")).findByIdProceso(reemplazo.getIdproceso());
					try {
						registrarAuditoriaReemplazos(proceso, reemplazo, Constantes.TA_RegistrarReemplazo, Constantes.TM_UserFinal, Constantes.TO_Enviar);
					} catch (Exception ex) {
						log.error(ex.getMessage(), ex);
					}
				}
			} else {
				log.debug("este item(Proceso: " + (String) item.get("label") + ") no tiene reemplazo asignado");
			}
		}
		return "Cambios guardados Correctamente";
		/*
		 * }catch(Exception e){ e.printStackTrace(); return "Ocurrio un Error";
		 * }
		 */
	}

	@SuppressWarnings("unchecked")
	@SMDMethod
	public String enviaGridReemplazoParametro(String str, String idUsuario) throws Exception {
		log.debug("-> [Action] DojoAction - enviaGridReemplazoParametro():String ");

		SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		// try{
		// iniciarFactorias();
		Usuario usuario = usuarioService.findByIdUsuario(Integer.valueOf(idUsuario));
		reemplazoDao.deleteByIdreemplazado(usuario.getIdusuario());
		log.debug(str);
		Object obj = JSONValue.parse(str);
		JSONObject objeto = (JSONObject) obj;
		JSONArray items = (JSONArray) objeto.get("items");
		Iterator<JSONObject> iter = items.iterator();
		while (iter.hasNext()) {
			JSONObject item = iter.next();
			log.debug(item);
			if (item.size() > 5) { // esta validacion puede ser mejor
				Reemplazo reemplazo = new Reemplazo();
				reemplazo.setEstado('A');
				reemplazo.setFechafinalreemplazo(formatoFecha.parse((String) item.get("fechafin") + " 23:59"));
				reemplazo.setFechainicialreemplazo(formatoFecha.parse((String) item.get("fechainicio") + " 00:00"));
				reemplazo.setIdreemplazado(usuario.getIdusuario());
				log.debug(item.get("idreemplazo"));
				Usuario usuariotemp = usuarioService.findByIdUsuario(Integer.valueOf(item.get("idreemplazo").toString()));
				reemplazo.setIdusuario(usuariotemp);
				reemplazo.setIdproceso(Integer.valueOf(item.get("id").toString()));
				log.debug(item);
				reemplazoDao.saveReemplazo(reemplazo);
			} else {
				log.debug("este item(Proceso: " + (String) item.get("label") + ") no tiene reemplazo asignado");
			}
		}
		return "Cambios guardados Correctamente";
		/*
		 * }catch(Exception e){ e.printStackTrace(); return "Ocurrio un Error";
		 * }
		 */
	}

        @SMDMethod
	public String saveRemitente(DetalleCliente objRemitente, Integer idCliente, String idDetalleCliente){
           log.debug("-> [Action] DojoAction - save():String ");
           
           DetalleCliente objDetalleCliente = null; 
           Usuario objUsuario = null;
	   mapSession = ActionContext.getContext().getSession();
	   objUsuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
            
           if (objRemitente == null) {
    	     log.error("No se recibio ningun remitente");
	     return "ERROR";
           }
            
           if (idCliente==null || idCliente.equals("")) {
		log.error("No se recibio ningun cliente");
		return "ERROR";
           }
           
           
           if (idDetalleCliente == null || idDetalleCliente.equals("")){
             objRemitente.setIdCliente(idCliente);
             objRemitente.setFechaCreacion(new Date());
             objRemitente.setIdDetalleCliente(null);
             objRemitente.setUsuarioCreacion(objUsuario.getIdusuario());
             objRemitente.setEstado("A");
           }else{
             objDetalleCliente =  detalleClienteService.findByDetalleClienteId(new Integer(idDetalleCliente));
             objRemitente.setFechaCreacion(objDetalleCliente.getFechaCreacion());
             objRemitente.setUsuarioCreacion(objDetalleCliente.getUsuarioCreacion());
             objRemitente.setEstado(objDetalleCliente.getEstado());
             objRemitente.setIdCliente(idCliente);  
             objRemitente.setIdDetalleCliente(new Integer(idDetalleCliente));
             objRemitente.setFechaModificacion(new Date());
             objRemitente.setUsuarioModificacion(objUsuario.getIdusuario());
           }
                        
           try{
                objRemitente = detalleClienteService.guardarObj(objRemitente);
                return  objRemitente.getIdDetalleCliente().toString();//objRemitente.getIdRemitente().toString();
           }catch(Exception e){
                e.printStackTrace();
                 return "ERROR";
           }
             
        }
        
        
	@SMDMethod
	public String saveCliente(String idCliente,String sTipoIdentificacion, String numeroIdentificacion,
                                  String nombres, String apellidoPaterno, String apellidoMaterno,
                                  String codCargoCliente, String direccionPrincipal,
                                  String telefono, String correo, String iddistrito, String razonSocial, String codTipoInstitucion) {
            
            Cliente objCliente = null;
            //boolean operacion = false; 
            try{
                log.debug("-> [Action] DojoAction - saveCliente():String ");
               
                if (isEmpty(sTipoIdentificacion)) {
		    log.error("No se recibio el tipo de identificacion");
		    return "ERROR";
		}
                
                if (idCliente!=null && !idCliente.trim().equals("")){
                   objCliente = clienteService.findByIdCliente(new Integer(idCliente));
                   if (!isEmpty(numeroIdentificacion)){
                      Cliente c = clienteService.findByNroIdentificacion(numeroIdentificacion.trim()); 
                      if (c!=null && !c.getIdCliente().toString().equals(objCliente.getIdCliente().toString())){
                         return "NotCreated"; 
                      } 
                   }
                   
                   objCliente.setNumeroIdentificacion(numeroIdentificacion);
                }else{
                   objCliente = new Cliente(); 
                   if (!isEmpty(numeroIdentificacion)){
                      Cliente c = clienteService.findByNroIdentificacion(numeroIdentificacion.trim()); 
                      if (c!=null){
                         return "NotCreated"; 
                      } 
                   }
                   
                   objCliente.setNumeroIdentificacion(numeroIdentificacion);
               }    
                
                Cliente objClienteOld = new Cliente();
		String sTipoAuditoria = null;
		Tipoidentificacion objTipoidentificacion = null;
		Usuario objUsuarioSesion = null;
		mapSession = ActionContext.getContext().getSession();
		objUsuarioSesion = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);  
                objTipoidentificacion = tipoIdentificacionService.buscarObjPor(sTipoIdentificacion);
                
                objCliente.setNombres(nombres.toUpperCase().trim());
                objCliente.setApellidoMaterno(apellidoMaterno.toUpperCase().trim());
                objCliente.setApellidoPaterno(apellidoPaterno.toUpperCase().trim());
                objCliente.setRazonSocial(razonSocial.toUpperCase().trim());
                objCliente.setDireccionPrincipal(direccionPrincipal);
                objCliente.setTelefono(telefono);
                objCliente.setCorreo(correo);
                objCliente.setTipoIdentificacion(objTipoidentificacion);
                objCliente.setEstado('A');
                
                if (codTipoInstitucion!=null && !codTipoInstitucion.trim().equals("")){
                    objCliente.setCodtipoinstitucion(new Integer(codTipoInstitucion));
                }else{
                   objCliente.setCodtipoinstitucion(null);
                }  
    
                if (codCargoCliente!=null && !codCargoCliente.trim().equals(""))
                  objCliente.setCodCargoCliente(new Integer(codCargoCliente));
                
                if (iddistrito!=null && !iddistrito.trim().equals(""))
                   objCliente.setUbigeoPrincipal(new Distrito(new Integer(iddistrito)));
                  
                if (idCliente==null || idCliente.equals("")){
                  objCliente.setUsuarioCreacion(objUsuarioSesion.getIdUsuarioPerfil());
		  objCliente.setFechaCreacion(new Date());    
                }else{
                  objCliente.setFechaModificacion(new Date());
		  objCliente.setUsuarioModificacion(objUsuarioSesion.getIdUsuarioPerfil());  
                }
                    
		if (objCliente.getUbigeoPrincipal() != null) {
	           objCliente.setUbigeoPrincipal(distritoDao.findById(new Integer(iddistrito)));
		}
		
                List<Cliente> listCliente =clienteService.findNombreCliente(objCliente);
                if (objCliente.getIdCliente()==null){
                    if (listCliente.size()>0){
                       return "EXISTS"; 
                    }
                }else{
                    if (listCliente!=null && listCliente.size()>0 && !objCliente.getIdCliente().toString().equals(listCliente.get(0).getIdCliente().toString())){
                        return "EXISTS"; 
                    }
                    
                }
                
                sTipoAuditoria = Constantes.AUDITORIA_TIPO_REGISTRO;
		objCliente = clienteService.guardarObj(objClienteOld, objCliente, objUsuarioSesion.getUsuario(), sTipoAuditoria);
        
	    }catch(Exception e){
                e.printStackTrace();
                return "ERROR";
            }
                
            return objCliente.getIdCliente().toString();
		
	}

	@SMDMethod
	public String deleteCliente(Cliente objCliente) {
		log.debug("-> [Action] DojoAction - deleteCliente():String ");

		if (objCliente == null) {
			log.error("No se recibio ningun cliente");
			return null;
		}
		// try{
		log.debug("Cliente a eliminar con ID [" + objCliente.getIdCliente() + "]");
		objCliente = clienteService.findByIdCliente(objCliente.getIdCliente());
		clienteService.deleteCliete(objCliente);
		return "deleted";
		/*
		 * }catch(Exception e){ log.error(e.getMessage(),e); return null; }
		 */
	}

        
        @SMDMethod
	public String validarTieneDerivacion(String id) {
            try{
                mapSession = ActionContext.getContext().getSession();
                Usuario objUsuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
                Documento d = documentoService.findByIdDocumento(new Integer(id));
                
                if (!d.getPropietario().getIdusuario().toString().equals(objUsuario.getIdUsuarioPerfil().toString()) || d.getFlagMultiple()!=null){
                     return "-1";
                }
            }catch(Exception e){
                e.printStackTrace();
            }
            
            return "1";
        }    
        
         @SMDMethod
	public String validarEnvio(String id, String bandeja, String s, String m) {
            try{
               
                String[] objUsuarioDestinatario = null;
                mapSession = ActionContext.getContext().getSession();
                Usuario objUsuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
                Documento d = documentoService.findByIdDocumento(new Integer(id));
                Tipodocumento t = srvTipoDocumento.findByIdTipoDocumento(d.getTipoDocumento().getIdtipodocumento());
                
                if (bandeja!=null && !bandeja.equals("E")){
                  if ((!d.getPropietario().getIdusuario().toString().equals(objUsuario.getIdUsuarioPerfil().toString())) || d.getFlagMultiple()!=null){
                     return "-1";
                  }
                }
                
                if (d.getFlagatendido()!=null && !bandeja.equals("E")){
                     return "-2";
                }
               
                if (d.getEstado()==Constantes.ESTADO_ANULADO){
                    return "-3";
                }
                
                if (s!=null && !s.equals("")){
                    if (d.getID_CLIENTE()!=null && t.getEstadoPIDE()!=null && t.getEstadoPIDE().equals(String.valueOf(Constantes.ESTADO_ACTIVO))){ 
                        boolean bandera = false;
                        List<Usuarioxunidadxfuncion> lista = null;
                        try{
                            objUsuarioDestinatario = s.split("-");
                            Usuario usuarioDestinatario = new Usuario();
                            usuarioDestinatario.setIdusuario(new Integer(objUsuarioDestinatario[0]));
                            usuarioDestinatario.setIdUnidadPerfil(new Integer(objUsuarioDestinatario[1]));
                            usuarioDestinatario.setIdFuncionPerfil(new Integer(objUsuarioDestinatario[2]));    
                            lista = usuarioxunidadxfuncionDAO.getUsuarioByUnidadByFuncionListRol(usuarioDestinatario);
                        }catch(Exception e){
                            e.printStackTrace();
                            return "-5";
                        }

                        if (lista!=null && lista.size()>0){
                            for (int i=0;i<lista.size();i++){
                              if (lista.get(i).getIdrol().toString().equals(Constantes.COD_ROL_MENSAJERIA.toString())){
                                  bandera = true;
                                  break;
                              }
                            }
      
                            if (bandera){
                                try{
                                    Cliente c = clienteService.findByIdCliente(d.getID_CLIENTE());
                                    if (c!=null && c.getFlagPide()!=null && c.getFlagPide().equals("1")){
                                      List<Usuario> lst = firmaArchivoService.findUltimaFirma(d.getIdDocumento(), "F");
                                      if (lst.size()==0){
                                         return "-4";
                                      }
                                    }
                                }catch(Exception e){
                                    e.printStackTrace();
                                    return "-5";
                                }    
                            }
                        }    
                    }
                }
                
                if (m!=null && !m.equals("")){
                    m = m.substring(0, m.length()-1);
                    String[] lstUsuarios = StringUtil.stringToArrayPersonalizado(m,'|');
                    
                    for(int i=0;i<lstUsuarios.length;i++){
                        String[] lstDatos = lstUsuarios[i].split("-"); 
                        if (d.getID_CLIENTE()!=null && t.getEstadoPIDE()!=null && t.getEstadoPIDE().equals(String.valueOf(Constantes.ESTADO_ACTIVO))){ 
                            boolean bandera = false;
                            List<Usuarioxunidadxfuncion> lista = null;
                            
                            try{
                                Usuario usuarioDestinatario = new Usuario();
                                System.out.println(lstDatos[0] + "-" + lstDatos[1] + "-" + lstDatos[2]);
                                usuarioDestinatario.setIdusuario(new Integer(lstDatos[0]));
                                usuarioDestinatario.setIdUnidadPerfil(new Integer(lstDatos[1]));
                                usuarioDestinatario.setIdFuncionPerfil(new Integer(lstDatos[2]));    
                                lista = usuarioxunidadxfuncionDAO.getUsuarioByUnidadByFuncionListRol(usuarioDestinatario);
                            }catch(Exception e){
                                e.printStackTrace();
                                return "-5";
                            }

                            if (lista!=null && lista.size()>0){
                                for (int k=0;k<lista.size();k++){
                                  if (lista.get(k).getIdrol().toString().equals(Constantes.COD_ROL_MENSAJERIA.toString())){
                                      bandera = true;
                                      break;
                                  }
                                }

                                if (bandera){
                                    try{
                                        List<IotdtmDocExterno> l = documentoExternoVirtualDAO.buscarDocumentoDespachoVirtual(d.getID_CODIGO());
                                        if (l!=null && l.size()>0){
                                            return "-6";
                                        }
                                        
                                        Cliente c = clienteService.findByIdCliente(d.getID_CLIENTE());
                                        if (c!=null && c.getFlagPide()!=null && c.getFlagPide().equals("1")){
                                          List<Usuario> lst = firmaArchivoService.findUltimaFirma(d.getDocumentoreferencia()==null?d.getIdDocumento():d.getDocumentoreferencia(), "F");
                                          if (lst.size()==0){
                                             return "-4";
                                          }
                                        }
                                    }catch(Exception e){
                                        e.printStackTrace();
                                        return "-5";
                                    }    
                                }
                            }    
                        }
                    }
                }
                               
            }catch(Exception e){
                e.printStackTrace();
                return "-5";
            }    
               
            return "1";       
        }
        
        @SMDMethod
	public String validarTieneFirmaPrincipal(String id) {
            try{
                mapSession = ActionContext.getContext().getSession();
                Usuario objUsuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
                Documento d = documentoService.findByIdDocumento(new Integer(id));
                
                if (!d.getPropietario().getIdusuario().toString().equals(objUsuario.getIdUsuarioPerfil().toString()) || d.getFlagMultiple()!=null){
                     return "-1";
                }
                
                if (d.getFlagatendido()!=null){
                    return "-2";
                }
                
                if (d.getEstado()==Constantes.ESTADO_ANULADO){
                    return "-3";
                }
                
                if (d.getNroVirtual()!=null){
                    int contador = 0;
                    IotdtmDocExterno recepcion = documentoExternoVirtualDAO.buscarDocumentoVirtual(d.getNroVirtual());
                    if (!recepcion.getSnumanx().toString().equals("0")){
                        if (recepcion.getSnumanx().intValue()>0){
                            List<Archivo> lst = archivoService.findLstByIdDocumento(d.getDocumentoreferencia()==null?d.getIdDocumento():d.getDocumentoreferencia());
                            if (lst!=null && lst.size()>0){
                                for(int i=0;i<lst.size();i++){
                                    if (lst.get(i).getPrincipal()=='N'){
                                       contador++; 
                                    }   
                                }
                                
                                if (recepcion.getSnumanx().intValue()>contador){
                                    return "-4";
                                }    
                            }    
                        }
                    }
                }
                
                if (d.getFirmado()!=null && d.getFirmado() == 'S'){
                    List<Archivo> lst = archivoService.findArchivoPrincipalFirmardo(d.getDocumentoreferencia()==null?d.getIdDocumento():d.getDocumentoreferencia(), objUsuario);
                    if (lst==null || lst.size()==0){
                        return "0";
                    }  
                }
            }catch(Exception e){
                e.printStackTrace();
            }
            return "1";
        }
       
        
        //@SMDMethod              
        public String verIdAlfresco(String sURL) throws IOException {
                AlfrescoApiWs alfrescoApiWs;
                Session sesionAlfresco = null;
                String idDocumento="";
                String forward="";
                String alfrescoHostPublico = SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.ALFRESCO_HOST);
                String alfrescoHostPort = SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.ALFRESCO_PORT);
                String URL_ALFRESCO = "http://"+alfrescoHostPublico+":"+alfrescoHostPort+"/alfresco/cmisatom";
                Usuario usuario = (Usuario) AuthThreadLocalHolder.getUsuario();
                if (usuario == null) {
                    throw new RuntimeException("Se intento iniciar sesion en Alfresco sin tener un usuario AuthThreadLocalHolder.");
                }
                String user = usuario.getUsuario();
                String pass = usuario.getClave();
                log.debug("-> [Action] DojoAction - verArchivoAlfresco():String ");
                mapSession = ActionContext.getContext().getSession();
                HttpServletRequest request = ServletActionContext.getRequest();
                log.debug("Ruta Archivo Alfresco [" + sURL + "]");
                String ipPublica = SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.IP_PUBLICA);
                String dominioPublico = SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.DOMINIO_PUBLICO);
                //String forward=alfrescoWebServiceClient.obtenerLinkContenido(sURL);
                String sruta[] = sURL.split("/");
                log.info("1.............................");
                String ticket = getAlfticket(user, pass, alfrescoHostPublico, alfrescoHostPort);
                log.info("2.............................");
                String rutaCarpeta = "/"+sruta[0]+"/"+sruta[1]+"/"+sruta[2]+"/"+sruta[3];
                
                try {                        
                        log.info("3.............................");
                        alfrescoApiWs = new AlfrescoApiWs(URL_ALFRESCO, user, pass, REPOSITORIO_ID);
                        log.info("4.............................");
                        sesionAlfresco = alfrescoApiWs.getSessionAlfresco();
                        log.info("5.............................");
                        String rutaCarpetaTestSistemas = rutaCarpeta;
                        Folder carpetaTestSistemas = (Folder)sesionAlfresco.getObjectByPath(rutaCarpetaTestSistemas);
                        ItemIterable<CmisObject> objItem = carpetaTestSistemas.getChildren();
                        log.info("6.............................");
                        for(CmisObject objeto : objItem){
                            if( objeto instanceof Document) {
                                    if(objeto.getName().equals(sruta[4]))
                                    {
                                        idDocumento = objeto.getId();
                                    }
                               
                            }
                        }
                        log.info("7.............................");
                        String idDocRuta[] = idDocumento.split("//");
                        String idDocRutaFinal[] = idDocRuta[1].split(";");
                        String idDoc[] = idDocRutaFinal[0].split("/");
                        return idDoc[1];
                         
                } catch (MalformedURLException e) {
                        e.printStackTrace();
                } catch (Exception ex) {
                        Logger.getLogger(DojoAction.class.getName()).log(Level.SEVERE, null, ex);
                }
                       
                return "";
        }
        
        private static String getAlfticket(String usu, String pass, String hostPublico, String hostPort) throws IOException, JSONException { 
            String ticket = ""; 
            String USERNAME = usu;
            String PASSWORD = pass;
            URL url = new URL("http://"+hostPublico+":"+hostPort+"/alfresco/service/api/login?u="+USERNAME+"&pw="+PASSWORD+"&format=json"); 
            URLConnection con = url.openConnection(); 
            InputStream in = con.getInputStream(); 
            String encoding = con.getContentEncoding(); 
            encoding = encoding == null ? "UTF-8" : encoding; 
            String json = IOUtils.toString(in, encoding); 
            org.json.JSONObject getData = new org.json.JSONObject(json); 
            System.out.println(getData.getJSONObject("data").get("ticket") 
                    .toString()); 
            ticket =getData.getJSONObject("data").get("ticket").toString(); 
            return ticket; 
        }
        
	@SMDMethod
	public String menuIG(){
		String result = "ERROR";
		mapSession = ActionContext.getContext().getSession();
		Usuario usuarioSesion = (Usuario) getMapSession().get(Constantes.SESSION_USUARIO);
		usuarioSesion = usuarioService.findByIdUsuario(usuarioSesion.getIdusuario());
		List<Rol> rolList ;
		rolList = rolService.getRolesPorUsuario(usuarioSesion.getUsuario());
		for(int i=0;i<rolList.size(); i++ ){
			Rol r = new Rol();
			r = rolList.get(i);
			if(r.getNombre().equals(Constantes.ROL_INTERFAZ_GERENCIAL)){
				result = "SUCESS";
			}
		}
	return result;
	}


	@SMDMethod
	public Map<String, List<Estructura>> getAllStructures() {
		log.debug("-> [Action] DojoAction - getAllStructures():Map<String, List<Estructura>> ");

		Usuario objUsuario = null;
		mapSession = ActionContext.getContext().getSession();
		objUsuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
		if (objUsuario == null) {
			log.error("Expiro la sesion");
			return null;
		}
		return gridColumnaXUsuarioService.getStructuresByRol(objUsuario);
	}

	/**REN Busca la estructura y el contenido de la grilla-------------------------------------------------------------------*/
	@SMDMethod
	public ObjetoJSON getDGDocumentosXExpediente(String sTipoGrid, Integer idDocumento) {
		log.debug("-> [Action] DojoAction - getDGDocumentosXExpediente():ObjetoJSON ");

		if (sTipoGrid == null) {
			log.error("No se recibio un tipo de grid correcto");
			return null;
		}

		if(!sTipoGrid.equals(Constantes.TIPO_GRID_DOCUMENTO) && !sTipoGrid.equals(Constantes.TIPO_GRID_EXPEDIENTE)){
			log.error("Grid Recibido no valido");
			return null;
		}

		ObjetoJSON objJSON = new ObjetoJSON();
		Usuario objUsuario = null;

		mapSession = ActionContext.getContext().getSession();
		objUsuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);

		if (objUsuario == null) {
			log.error("Expiro la sesion");
			return null;
		}

		if (log.isDebugEnabled()) {
			log.debug("Tipo de grid solicitado [" + sTipoGrid + "]");
		}

		objJSON.setItems(gridColumnaXUsuarioService.getItemsDocumentosXExpediente(sTipoGrid, idDocumento, objUsuario));
		objJSON.setStructure(gridColumnaXUsuarioService.getEstructura(sTipoGrid, objUsuario));

		return objJSON;
	}


        @SMDMethod
        public String obtenerCargoRemitente(Integer iIdCliente, Integer idPersona) throws Exception {
           log.debug("-> [Action] DojoAction - obtenerRemitentes()");
           DetalleCliente cliente =  detalleClienteService.findByDetalleClienteId(idPersona); //jc24detalleClienteService.findByDetalleCliente(iIdCliente, idPersona);
          
           if (cliente==null)
              return "";
           
           return cliente.getIdCargoAdministrado().toString();
           
        }
        
        @SMDMethod
        public String obtenerCargoPersonaNatural(Integer iIdCliente) throws Exception {
           log.debug("-> [Action] DojoAction - obtenerCargoPersonaNatural()");
           Cliente cliente = clienteService.findByIdCliente(iIdCliente);
          
           if (cliente==null)
              return "";
           
           return cliente.getCodCargoCliente().toString();
           
        }


	@SMDMethod
	public ObjetoJSON getMenuDocumentos() {


        List<ItemDocumento> lstItem = new ArrayList<ItemDocumento>();

        ItemDocumento item = new ItemDocumento();
        item.setId(1);
        item.setDocumento("Nro 1");
        item.setUrlarchivo("http://1");
        lstItem.add(item);

        item = new ItemDocumento();
        item.setId(2);
        item.setDocumento("Nro 1");
        item.setUrlarchivo("http://1");
        lstItem.add(item);

		ObjetoJSON objJSON = new ObjetoJSON();
		objJSON.setItems(lstItem);
		return objJSON;
	}


	/**REN Busca los documentos de solo un expediente------------------------------------------------------------------------*/
	@SMDMethod
	public ObjetoJSON getDataGrid(String sTipoGrid) {
                log.debug("-> [Action] DojoAction::getDataGrid():ObjetoJSON ");

		if (sTipoGrid == null) {
			log.error("No se recibio un tipo de grid correcto");

			return null;
		}

		//TIPO_GRID_DIG_DOC_INGRESADOS==15 ,el tipo de grid no existe en bd, pero tiene como equivalente TIPO_GRID_DOCUMENTO
		if (sTipoGrid.equalsIgnoreCase(Constantes.TIPO_GRID_DIG_DOC_INGRESADOS)) {
			sTipoGrid = Constantes.TIPO_GRID_DOCUMENTO;
		}
                
		ObjetoJSON objJSON = new ObjetoJSON();
		Usuario objUsuario = null;
                
		mapSession = ActionContext.getContext().getSession();
		mapSession.put("busquedaAvanzada", "0");
		mapSession.put("sTipoGrid", sTipoGrid);
		objUsuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
                
		if (objUsuario == null) {
			log.error("Expiro la sesion");    
			return null;
		}

		if (log.isDebugEnabled()) {
			log.debug("Tipo de grid solicitado [" + sTipoGrid + "]");
		}

                List lstItem = gridColumnaXUsuarioService.getItems(sTipoGrid, objUsuario, null);
                
		if (sTipoGrid.equalsIgnoreCase(Constantes.TIPO_GRID_DOCUMENTO_RECIBIDO) || sTipoGrid.equalsIgnoreCase(Constantes.TIPO_GRID_DOCUMENTO)) {
			Comparator<ItemUF> cmp = new Comparator<ItemUF>(){
				 public int compare(ItemUF f1, ItemUF f2)
		            {
		                return f1.getFechaAccion().compareTo(f2.getFechaAccion());
		            }
			};

			Collections.sort(lstItem, Collections.reverseOrder(cmp));
		}

		if (sTipoGrid.equalsIgnoreCase(Constantes.TIPO_GRID_INFORMATIVO)) {
			Comparator<Item> cmp = new Comparator<Item>(){
				 public int compare(Item f1, Item f2)
		            {
		                return f1.getFecharecepcion().compareTo(f2.getFecharecepcion());
		            }
			};

			Collections.sort(lstItem, Collections.reverseOrder(cmp));
		}

                objJSON.setItems(lstItem);
                if(sTipoGrid.equals(Constantes.TIPO_GRID_DOCUMENTOS_ARCHIVADOS)){
			mapSession.put("flagBtnReAbrir", true);
		}else{
			mapSession.put("flagBtnReAbrir", false);
		}

                mapSession.put("listRecibidos", lstItem);
                objJSON.setStructure(gridColumnaXUsuarioService.getEstructura(sTipoGrid, objUsuario));
                return objJSON;
	}

	@SMDMethod
	public ObjetoJSON getDataGridByUser(String sTipoGrid, Integer idUsuario) {
		log.debug("-> [Action] DojoAction - getDataGridByUser():ObjetoJSON ");

		if (sTipoGrid == null) {
			log.error("No se recibio un tipo de grid correcto");
			return null;
		}

		ObjetoJSON objJSON = new ObjetoJSON();
		Usuario objUsuario = this.usuarioService.findByIdUsuario(idUsuario);

		mapSession = ActionContext.getContext().getSession();
		mapSession.put("sTipoGrid", "77");

		List lstItem = gridColumnaXUsuarioService.getItemsSinBandejaCompartida(objUsuario);
		objJSON.setItems(lstItem);
		objJSON.setStructure(gridColumnaXUsuarioService.getEstructura(sTipoGrid, objUsuario));
		mapSession.put("listCompartidos" + idUsuario + "" , lstItem);

		return objJSON;
	}

	@SMDMethod
	public ObjetoJSON buildGridCarpetaBusqueda(Integer iIdCarpetaBusqueda) {
		log.debug("-> [Action] DojoAction - buildGridCarpetaBusqueda():ObjetoJSON ");

		ObjetoJSON objJSON = new ObjetoJSON();
		List<Item> a = gridColumnaXUsuarioService.convertFromCarpetaBusquedaToItem(iIdCarpetaBusqueda);
		objJSON.setItems(a);
		return objJSON;
	}

	/*
	public String exportarCarpetaBusqueda(List<Item> a, String nombreCarpeta){
		try {

			HttpServletRequest request=ServletActionContext.getRequest();
			mapSession = ActionContext.getContext().getSession();

			String fileName = "CB_" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + "_" +UUID.randomUUID().toString().concat(".xls");


			 List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		     Map<String, Object> m = null;
			 SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy hh:mm");


			 if (a!=null && a.size()>0){
				    for(Item objItem : a){
						 m = new HashMap<String, Object>();
		                 m.put("nroExpediente", objItem.getExpediente());
		                 m.put("nroDocumento", objItem.getNrodocumento());
		                 m.put("tipoDocumento", objItem.getTipodocumento());
		                 m.put("proceso", objItem.getProceso());
		                 m.put("fecha", objItem.getFecharecepcion().toString());
		                 m.put("area", objItem.getArea());
		                 m.put("asuntoDocumento",  objItem.getAsunto());
		                 m.put("asuntoExpediente", objItem.getAsuntoExpediente());
		                 m.put("cliente", objItem.getCliente());
		                 m.put("areaDestino", objItem.getAreadestino());
		                 m.put("prioridad", objItem.getExcprioridad());

		                 list.add(m);
					}
			  }


			InputStream reporteStream = getClass().getResourceAsStream("/org/osinerg/utils/ReporteCarpetaBusqueda.jasper");
			HashMap map = new HashMap();
			map.put("nombreCarpeta", nombreCarpeta);


			JRXlsExporter exporter = new JRXlsExporter();
			JasperPrint print = JasperFillManager.fillReport(reporteStream,map,new JRBeanCollectionDataSource(list));

			exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
			exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, request.getRealPath("/")+ "export/" + fileName);
		    exporter.exportReport();

		    request.setAttribute("urlCB", "/export/" + fileName);
		    request.getSession().setAttribute("urlCB", "/export/" + fileName);

			}
		catch (Exception e1) {
			e1.printStackTrace();
			log.error(e1.getMessage(),e1);
		}

		return null;
	} */

	@SMDMethod
	public ObjetoJSON getDataGridBusquedaSimple(String sParametroBusqueda) {
		log.debug("-> [Action] DojoAction - getDataGridBusquedaSimple():ObjetoJSON ");

		mapSession = ActionContext.getContext().getSession();
		Usuario usuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
		usuario = usuarioService.findByIdUsuario(usuario.getIdusuario());
		ObjetoJSON objJSON = new ObjetoJSON();
		objJSON.setItems(gridColumnaXUsuarioService.getItems_BusquedaSimple(sParametroBusqueda, usuario));
		return objJSON;
	}


	@SMDMethod
	public ObjetoJSON busquedaAvanzada(String sTipoFiltro, BusquedaAvanzada objFiltro, String arrFecha[]) {
		log.debug("-> [Action] DojoAction - busquedaAvanzada():ObjetoJSON ");

		ObjetoJSON objJSON = new ObjetoJSON();
		if (log.isDebugEnabled()) {
			log.debug("Fecha Documento Desde [" + arrFecha[0] + "]");
			log.debug("Fecha Documento Hasta [" + arrFecha[1] + "]");
			log.debug("Fecha Expediente Desde [" + arrFecha[2] + "]");
			log.debug("Fecha Expediente Hasta [" + arrFecha[3] + "]");
		}
		if (objFiltro.getEstadoexpediente().equals("*")) {
			objFiltro.setEstadoexpediente("");
		}

		mapSession = ActionContext.getContext().getSession();
		mapSession.put("busquedaAvanzada", "1");
		mapSession.put("sTipoGrid", "98");

                String[] sqlQueryDinamico = new String[1]; sqlQueryDinamico[0] = "";
		objJSON.setItems(gridColumnaXUsuarioService.getItems_BusquedaAvanzada(sTipoFiltro, objFiltro, arrFecha, sqlQueryDinamico, "busqueda", null , null));
		if (sqlQueryDinamico[0]!=null && !sqlQueryDinamico[0].trim().equals("")){
			LogBusquedaAvanzada logBusquedaAvanzada = new LogBusquedaAvanzada();
			mapSession = ActionContext.getContext().getSession();
			logBusquedaAvanzada.setIdusuario(((Usuario) mapSession.get(Constantes.SESSION_USUARIO)).getIdusuario());
			logBusquedaAvanzada.setNombrepc((String)mapSession.get("nombrePC"));
			logBusquedaAvanzada.setConsulta(sqlQueryDinamico[0]);
			logBusquedaAvanzada.setFechaoperacion(new Date());
			logBusquedaAvanzadaService.saveLogBusquedaAvanzada(logBusquedaAvanzada);
		}
		return objJSON;
	}

	@SMDMethod
	public ProcesoBeanJson obtenerProcesos(Integer idProceso) {
		log.debug("-> [Action] DojoAction - obtenerProcesos():ProcesoBeanJson ");

		ProcesoBeanJson objJSON = new ProcesoBeanJson();
		Proceso p=srvProceso.getProcesoxId(idProceso);
		objJSON.setUnidad(p.getsUnidad());
		String tipoProcesoPiloto = parametroService.findByTipoUnico(Constantes.TIPO_PROCESO_PILOTO).getValor();
		String tipoProcesoProduccion = parametroService.findByTipoUnico(Constantes.TIPO_PROCESO_PRODUCCION).getValor();
		objJSON.setNombreambiente(p.getProduccion() == null || p.getProduccion().toString().equals("N") ? tipoProcesoPiloto : tipoProcesoProduccion);
		objJSON.setProceso(p.getNombre());
		objJSON.setAmbiente((p.getProduccion() == null || p.getProduccion().toString().equals("N") ? p.getProduccion():' '));
		objJSON.setAreadestino(p.getsUnidad());
		objJSON.setDestinatario(p.getsResNombres() + " " + p.getsResApelidos());
		objJSON.setPermitesumilla(String.valueOf(p.getPermitesumilla()));
		objJSON.setTipoproceso(p.getsTipoProceso().toLowerCase());
		return objJSON;
	}

	@SMDMethod
	public ClienteBeanJson obtenerClientexTI(String tipoIdentificacion,String nroIdentificacion) throws Exception{
		log.debug("-> [Action] DojoAction - obtenerClientexTI():ClienteBeanJson ");

		ClienteBeanJson clienteBeanJson=new ClienteBeanJson();
		Cliente objC=new Cliente();
		if(isEmpty(tipoIdentificacion)){
			objC=clienteService.findByTipoIdentificacionList2(null,null,nroIdentificacion);
		}
		else{
			objC=clienteService.findByTipoIdentificacionList2(null,tipoIdentificacion,nroIdentificacion);
		}

		if(objC != null){

			clienteBeanJson.setTipoidentificacion(objC.getTipoIdentificacion().getNombre());
			clienteBeanJson.setNroIdentificacion(objC.getNumeroIdentificacion());
			if(objC.getTipoIdentificacion().getNombre().equals(Constantes.TIPO_IDENTIFICACION_RUC)){
				clienteBeanJson.setLabel(objC.getTipoIdentificacion().getNombre() + " - " + objC.getNumeroIdentificacion() + " - " + objC.getRazonSocial());
			}
			else if(objC.getTipoIdentificacion().getNombre().equals(Constantes.TIPO_IDENTIFICACION_DNI) || objC.getTipoIdentificacion().getNombre().equals(Constantes.TIPO_IDENTIFICACION_OTRO)){
				String sNombreCompleto=objC.getNombres() + (objC.getApellidoPaterno() == null ? "" : " " + objC.getApellidoPaterno());
				sNombreCompleto+=(objC.getApellidoMaterno() == null ? "" : " " + objC.getApellidoMaterno());
				clienteBeanJson.setLabel(objC.getTipoIdentificacion().getNombre() + " - " + objC.getNumeroIdentificacion() + " - " + sNombreCompleto);
			}
			// log.debug("Nombre del Cliente ["+reply.get("label").toString()+"]");
			clienteBeanJson.setIdcliente(objC.getIdCliente().toString());
			String razonSocial=objC.getRazonSocial();
			clienteBeanJson.setRazonsocial(razonSocial == null ? "" : razonSocial);
			clienteBeanJson.setRepresentantelegal(objC.getRepresentanteLegal() == null ? "" : objC.getRepresentanteLegal());
			String nombres=objC.getNombres();
			clienteBeanJson.setNombres(nombres == null ? "" : nombres);
			String paterno=objC.getApellidoPaterno();
			clienteBeanJson.setApellidopaterno(paterno == null ? "" : paterno);
			String materno=objC.getApellidoMaterno();
			clienteBeanJson.setApellidomaterno(materno == null ? "" : materno);
			clienteBeanJson.setDireccionp(objC.getDireccionPrincipal() == null ? "" : objC.getDireccionPrincipal());

			if(objC.getIdDistritoUP() != null){
				clienteBeanJson.setIddistrito(objC.getIdDistritoUP().toString());
				clienteBeanJson.setDistrito(objC.getSDistritoUP());
				if(objC.getIdProvinciaUP() != null){
					clienteBeanJson.setIdprovincia(objC.getIdProvinciaUP().toString());
					clienteBeanJson.setProvincia(objC.getSProvinciaUP());
					if(objC.getIdDepartamentoUP() != null){
						clienteBeanJson.setIddepartamento(objC.getIdDepartamentoUP().toString());
						clienteBeanJson.setDepartamento(objC.getSDepartamentoUP());
					}
					else{
						clienteBeanJson.setDepartamento("");
					}
				}
				else{
					clienteBeanJson.setProvincia("");
				}
			}
			else{
				clienteBeanJson.setDistrito("");
			}

			clienteBeanJson.setDirecciona(objC.getDireccionAlternativa() == null ? "" : objC.getDireccionAlternativa());

			if(objC.getIdDistritoUA() != null){
				clienteBeanJson.setIddistritoa(objC.getIdDistritoUA().toString());
				clienteBeanJson.setDistritoa(objC.getSDistritoUA());
				if(objC.getIdProvinciaUA() != null){
					clienteBeanJson.setIdprovinciaa(objC.getIdProvinciaUA().toString());
					clienteBeanJson.setProvinciaa(objC.getSProvinciaUA());
					if(objC.getIdDepartamentoUA() != null){
						clienteBeanJson.setIddepartamentoa(objC.getIdDepartamentoUA().toString());
						clienteBeanJson.setDepartamentoa(objC.getSDepartamentoUA());
					}
					else{
						clienteBeanJson.setDepartamentoa("");
					}
				}
				else{
					clienteBeanJson.setProvinciaa("");
				}
			}
			else{
				clienteBeanJson.setDistrito("");
			}
			clienteBeanJson.setTelefono(objC.getTelefono() == null ? "" : objC.getTelefono());
			clienteBeanJson.setCorreo(objC.getCorreo() == null ? "" : objC.getCorreo());
		}
		return clienteBeanJson;
	}
        
       @SMDMethod
    public String saveArbolDocumentoPublicar(Integer idrefarc, Integer iddocumento, Integer iddocref, Integer idarchivo, String estado) {
        log.debug("-> [Action] DojoAction - saveArbolDocumentoPublicar():void idReferenciaArchivo:" + idarchivo + "estado:" + estado);
        Usuario usuario = (Usuario) ActionContext.getContext().getSession().get(Constantes.SESSION_USUARIO);
        ReferenciaArchivo referencia = referenciaArchivoService.findReferenciaArchivo(idrefarc);
        if (null == referencia) {
            referencia = new ReferenciaArchivo(null, iddocumento, iddocref, idarchivo, estado, usuario.getIdusuario(), new Date());
        } else {
            if (null == referencia.getIdRefArc() ) {
                referencia.setEstado("A");
                referencia.setFechaCreacion(new Date());
                referencia.setUsuarioCreacion(usuario.getIdusuario());
            } else {
                referencia.setFechaModificacion(new Date());
                referencia.setUsuarioModificacion(usuario.getIdusuario());
            }
        }

        referencia.setEstado(estado);
        log.info("-> [Action] DojoAction - ReferenciaArchivo.tostring:" + referencia.toString() );
        referenciaArchivoService.saveReferenciaArchivo(referencia);
        return "";

    }



	@SMDMethod
	public String[] getParameters() {
		log.debug("-> [Action] DojoAction - getParameters():String[] ");

		String[] arrParameters = new String[3];
		arrParameters[0] = String.valueOf(System.currentTimeMillis());
		arrParameters[1] = parametroService.findByTipoUnico(Constantes.PARAMETRO_NOTIFICACION_AMARILLA).getValor();
		arrParameters[2] = parametroService.findByTipoUnico(Constantes.PARAMETRO_NOTIFICACION_ROJA).getValor();

		return arrParameters;
	}

	@SMDMethod
	public String[] getPorcentajesAlertas() {
		log.debug("-> [Action] DojoAction - getPorcentajesAlertas():String[] ");

		String[] arrParameters = new String[1];
		arrParameters[0] = String.valueOf(System.currentTimeMillis());
		return arrParameters;
	}


	@SMDMethod
	public ObjetoJSON getCarpetasBusqueda() {
		log.debug("-> [Action] DojoAction - getCarpetasBusqueda():ObjetoJSON ");

		ObjetoJSON objJSON = new ObjetoJSON();
		Usuario objUsuario = null;
		mapSession = ActionContext.getContext().getSession();
		objUsuario = (Usuario) getMapSession().get(Constantes.SESSION_USUARIO);
		log.debug("Carpetas de busqueda a buscar del usuario [" + objUsuario.getUsuario() + "]");
		objJSON.setIdentifier("id");
		objJSON.setLabel("name");
		objJSON.setItems(carpetaBusquedaService.getTreeCarpetaBusqueda(objUsuario.getIdusuario()));
		return objJSON;
	}

        @SMDMethod
    public ObjetoJSON unidadOrgxSession() throws Exception {
        log.debug("-> [Action] DojoAction-> unidadOrgxSession ");        
        mapSession = ActionContext.getContext().getSession();
        Usuario usuario_ = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
       
        ObjetoJSON d=new ObjetoJSON();       
        d.setItems(usuarioService.findUnidadOrgSession(usuario_.getUnidad().getIdunidad()));
        return d;
    }

	@SMDMethod
	public ObjetoJSON getDocumentosRecibidos_old(BusquedaAvanzada objFiltro) {
		log.debug("-> [Action] DojoAction - getDocumentosRecibidos():ObjetoJSON ");

		ObjetoJSON objJSON = new ObjetoJSON();
		Usuario objUsuario = null;
		mapSession = ActionContext.getContext().getSession();
		objUsuario = (Usuario) getMapSession().get(Constantes.SESSION_USUARIO);
		log.debug("Documentos recibidos a buscar del usuario [" + objUsuario.getUsuario() + "]");
		objJSON.setIdentifier("id");
		objJSON.setLabel("name");
		if (objFiltro == null) {
			log.debug("SIN FILTRO");
			objJSON.setItems(documentoService.getTreeDocumentosRecibidos(objUsuario, true));
		}else{
			//objJSON.setItems(documentoService.getTreeDocumentosRecibidos(objUsuario, true));
			objJSON.setItems(documentoService.getTreeDocumentosRecibidosFiltro(objUsuario, true, objFiltro));
		}

		return objJSON;
	}

	@SMDMethod
	public ObjetoJSON getDocumentosRecibidos(BusquedaAvanzada objFiltro, Integer idDocumento) {
		log.debug("-> [Action] DojoAction - getDocumentosRecibidos():void ");

		//estoy aumentadndo
		Integer idExpediente = (documentoDao.findByIdDocumento(idDocumento)).getExpediente().getId().intValue();
		ObjetoJSON objJSON = new ObjetoJSON();
		objJSON.setIdentifier("id");
		objJSON.setLabel("name");

		objJSON.setItems(documentoService.getDojoDocumentoTree(objFiltro, idExpediente, idDocumento));

		return objJSON;

	}

	@SMDMethod
	public String saveCarpetaBusqueda(CarpetaBusqueda objCarpetaBusqueda, String arrFecha[]) {
		log.debug("-> [Action] DojoAction - saveCarpetaBusqueda():String ");

		SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
		Usuario objUsuario = null;

		mapSession = ActionContext.getContext().getSession();
		objUsuario = (Usuario) getMapSession().get(Constantes.SESSION_USUARIO);

		log.debug("Se recibio carpeta de busqueda con ID [" + objCarpetaBusqueda.getIdCarpetaBusqueda() + "] nombre [" + objCarpetaBusqueda.getNombreCarpeta() + "]");
		log.debug("Fecha Documento Desde [" + arrFecha[0] + "]");
		log.debug("Fecha Documento Hasta [" + arrFecha[1] + "]");
		log.debug("Fecha Expediente Desde [" + arrFecha[2] + "]");
		log.debug("Fecha Expediente Hasta [" + arrFecha[3] + "]");

		try {
			if (!arrFecha[0].equals("") && !arrFecha[1].equals("")) {
				objCarpetaBusqueda.setFechaDocumentoInicio(formatDate.parse(arrFecha[0]));
				objCarpetaBusqueda.setFechaDocumentoFinal(formatDate.parse(arrFecha[1]));
			}
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
		}

		try {
			if (!arrFecha[2].equals("") && !arrFecha[3].equals("")) {
				objCarpetaBusqueda.setFechaExpedienteInicio(formatDate.parse(arrFecha[2]));
				objCarpetaBusqueda.setFechaExpedienteFinal(formatDate.parse(arrFecha[3]));
			}
		} catch (ParseException ex) {
			log.error(ex.getMessage(), ex);
		}

		if (objCarpetaBusqueda.getIdCarpetaBusqueda() == null || objCarpetaBusqueda.getIdCarpetaBusqueda() == 0) {
			log.debug("** CREACION DE CARPETA DE BUSQUEDA **");
		} else {
			log.debug("** MODIFICACION DE CARPETA DE BUSQUEDA **");
		}

		objCarpetaBusqueda.setUsuario(objUsuario);
		carpetaBusquedaService.saveCarpetaBusqueda(objCarpetaBusqueda);

		return "Created";
	}


	@SMDMethod
	public ObjetoJSON eliminarCarpetaBusqueda(Integer iIdCarpetaBusqueda) {
		log.debug("-> [Action] DojoAction - eliminarCarpetaBusqueda():ObjetoJSON ");

		if (iIdCarpetaBusqueda == null) {
			log.debug("No se recibio ninguna carpeta de busqueda a eliminar");
			return null;
		}
		CarpetaBusqueda objCarpetaBusqueda = null;
		ObjetoJSON objJSON = new ObjetoJSON();
		Usuario objUsuario = null;
		mapSession = ActionContext.getContext().getSession();
		objUsuario = (Usuario) getMapSession().get(Constantes.SESSION_USUARIO);
		log.debug("Carpeta de busqueda a eliminar con ID [" + iIdCarpetaBusqueda + "] perteneciente al usuario [" + objUsuario.getUsuario() + "]");
		objCarpetaBusqueda = carpetaBusquedaService.ViewbyId(iIdCarpetaBusqueda);
		carpetaBusquedaService.eliminarCarpetabusqueda(objCarpetaBusqueda);
		objJSON.setIdentifier("id");
		objJSON.setLabel("name");
		objJSON.setItems(carpetaBusquedaService.getTreeCarpetaBusqueda(objUsuario.getIdusuario()));
		return objJSON;
	}


	@SMDMethod
	public ObjetoJSON getReemplazo(Boolean iMantReemplazo) {
		log.debug("-> [Action] DojoAction - getReemplazo():ObjetoJSON ");

		ObjetoJSON objJSON = new ObjetoJSON();
		Usuario objUsuario = null;

		if (iMantReemplazo) {
			objJSON.setItems(gridColumnaXUsuarioService.getItems_MantReemplazo());
		} else {
			mapSession = ActionContext.getContext().getSession();
			objUsuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);

			log.debug("Reemplazo a buscar del usuario [" + objUsuario.getUsuario() + "]");
			objJSON.setItems(gridColumnaXUsuarioService.getItems_Reemplazo(objUsuario));
		}

		return objJSON;
	}

	@SMDMethod
	public ObjetoJSON getLista() {
		log.debug("-> [Action] DojoAction - getLista():ObjetoJSON ");

		ObjetoJSON objJSON = new ObjetoJSON();
		Usuario objUsuario = null;

		mapSession = ActionContext.getContext().getSession();
		objUsuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
		objJSON.setItems(gridColumnaXUsuarioService.getItems_Lista(objUsuario));

		return objJSON;
	}

	@SMDMethod
	//public NumeracionJSON getNumeracion (Integer unidadId,Integer tipodocumento) throws Exception {
        public NumeracionJSON getNumeracion (Integer unidadId,String tipodocumento) throws Exception {
                Integer tipo = null;
                NumeracionJSON nj = null;
                boolean bandera = true;
                try{
                    tipo = new Integer(tipodocumento);
                }catch(Exception e){
                    bandera = false;
                }
                
               log.debug("-> [Action] DojoAction - getNumeracion():NumeracionJSON ");
                
                if (bandera){
                    WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(ServletActionContext.getServletContext());
                    NumeracionService numeracionService=(NumeracionService)wac.getBean("numeracionService");
                    UnidadService unidadService =  (UnidadService)wac.getBean("unidadService");
                    Unidad unidad =	unidadService.buscarObjPor(unidadId);
                    Numeracion num =  numeracionService.findByIdbyUnidad( unidad, tipo);
                    
                    if (num == null)
                        return null;
                    
                    nj=new NumeracionJSON();
                    nj.setTipo(num.getTiponumeracion());
                    nj.setFirmante(num.getFirmante());
                    nj.setDestinatario(num.getDestinatario());          
                }    
                
                return nj;
	}
        
        @SMDMethod
	public String moverFirma(String idDocumento) {
           try{  
                mapSession = ActionContext.getContext().getSession();
                Usuario usuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
                Documento d = documentoService.findByIdDocumento(new Integer(idDocumento));
                
                if (d.getFirmado()!=null && d.getFirmado()== 'N'){
                   SeguimientoXFirma seguimientoxfirma = new SeguimientoXFirma();
                   seguimientoxfirma.setIdDocumento(new Integer(idDocumento));
                   seguimientoxfirma.setCargoPropietario(usuario.getIdFuncionPerfil());
                   seguimientoxfirma.setIdUsuario(usuario.getIdUsuarioPerfil());
                   seguimientoxfirma.setUnidadPropietario(usuario.getIdUnidadPerfil());
                   seguimientoxfirma.setEstado("A");
                   seguimientoxfirma.setUsuarioCreacion(usuario.getIdusuario());
                   seguimientoxfirma.setFechaCreacion(new Date());
                   seguimientoxfirma.setIdTipoTrazabilidad("T");
                   seguimientoxfirma.setIdTrazabilidad(null);
                   seguimientoXFirmaService.guardarSeguimiento(seguimientoxfirma);
                   
                   d.setFirmado('S');
                   d.setUsuarioModificacion(usuario.getIdusuario());
                   d.setFechaModificacion(new Date());
                   documentoService.guardarDocumento(d);
                   return "0";
                }else{
                   return "1"; 
                }
           }catch(Exception e){
               e.printStackTrace();
               return "2";
           }     
        }
        
        @SMDMethod
	public String respuestaFirmar(String lstRespuesta, String idProceso, String alias, String accion) {
          String[] respuesta =  StringUtil.stringToArrayPersonalizado(lstRespuesta, ','); 
          mapSession = ActionContext.getContext().getSession();
          Usuario objUsuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
	  SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
          
          for (int i=0;i<respuesta.length;i++){
              try{ 
                    String[] datos =  StringUtil.stringToArrayPersonalizado(respuesta[i], '|');
                    if (datos==null) continue;
                    
                    FirmaArchivo firmaArchivo = new FirmaArchivo();
                    List<Archivo> list = archivoService.buscarArchivosObjectId(datos[0], Integer.valueOf(datos[3]));
                    firmaArchivo.setIdArchivo(list.get(0).getIdArchivo());
                    firmaArchivo.setEstado("N");
                    if (datos[2].equals("1")) firmaArchivo.setEstado("F");
                    if (datos[2].equals("2")) firmaArchivo.setEstado("D");
                    firmaArchivo.setFechaCreacion(new Date());
                    firmaArchivo.setUsuarioCreacion(objUsuario.getIdusuario());
                    firmaArchivo.setIdUsuario(objUsuario.getIdUsuarioPerfil());
                    firmaArchivo.setUnidadPropietario(objUsuario.getIdUnidadPerfil());
                    firmaArchivo.setCargoPropietario(objUsuario.getIdFuncionPerfil());
                    firmaArchivo.setIdProceso(idProceso);
                    firmaArchivo.setAlias(alias);
                    firmaArchivo.setAccion(accion);
                    firmaArchivo.setFechaFirma(datos[1] == null ? null: formatoFecha.parse(datos[1]));
                    firmaArchivoService.saveFirmaArchivo(firmaArchivo);
             }catch(Exception e){
                 e.printStackTrace();
             }      
          }
          
          return "1";
        }
        @SMDMethod
	public ArchivoJSON getArchivosFirmar(String parameters) {
            System.out.println("DojoAction::getArchivosFirmar");
            String[] lista = StringUtil.stringToArray(parameters);
            ArchivoJSON archivosJSON = new ArchivoJSON();
            mapSession = ActionContext.getContext().getSession();
            Usuario objUsuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
            List<Item> items = new ArrayList<Item>();
           
            try{     
                    for(int i=0;i<lista.length;i++){
                      for(int j=0;j<lista.length;j++){
                            if(i!=j){
                              if(lista[i].equals(lista[j])){
                                lista[i]="";
                              }
                            }
                        }
                    }
            
                    for(int i=0;i<lista.length;i++){
                      if (!lista[i].equals("")){  
                        Documento d = documentoService.findByIdDocumento(new Integer(lista[i]));
                        List<Archivo> listArchivos = archivoService.findArchivosxFirmar(d.getDocumentoreferencia()==null?d.getIdDocumento():d.getDocumentoreferencia(), objUsuario);
                    
                        for(int j=0;j<listArchivos.size();j++){
                            
                            if (listArchivos.get(j).getRutaAlfresco().toUpperCase().indexOf(".PDF")!=-1){
                              d =  documentoService.findByIdDocumento(d.getDocumentoreferencia()==null?d.getIdDocumento():d.getDocumentoreferencia());
                              
                              System.out.println("Entrando...");
                              if (d.getID_EXTERNO()!=null && d.getID_EXTERNO().toString().equals("1") && listArchivos.get(j).getPrincipal()=='S')
                                 continue;
                              
                              if (d.getID_EXTERNO()!=null && d.getID_EXTERNO().toString().equals("1") && listArchivos.get(j).getPrincipal()=='C')
                                 continue; 
                              
                              if (d.getID_EXTERNO()!=null && d.getID_EXTERNO().toString().equals("1") && listArchivos.get(j).getPrincipal()=='N')
                                 continue;
                              
                              Tipodocumento t = srvTipoDocumento.findByIdTipoDocumento(d.getTipoDocumento().getIdtipodocumento());
                              Item item = new Item();
			      String datos = listArchivos.get(j).getObjectId() + "|" + d.getID_CODIGO().toString() + "|" + (listArchivos.get(j).getClave()==null?"":listArchivos.get(j).getClave()) + "|";

                              if (t.getExternoQR()!=null && !t.getExternoQR().trim().equals("") && t.getExternoQR().equals("1") && t.getEstado()== 'A' && d.getID_EXTERNO().toString().equals("0") && listArchivos.get(j).getPrincipal()=='S'){
                                 datos = datos + "S";
                              }else{
                                 datos = datos + "N"; 
                              }
                            
                              item.setIdArchivo(datos);
                              item.setRecepcionado(d.getRecepcionado()==null?"":d.getRecepcionado());
                              
                              if (listArchivos.get(j).getPrincipal().toString().equals("S")){
                                item.setNombre(listArchivos.get(j).getNombre().substring(listArchivos.get(j).getNombre().indexOf(']')+1) + "|S" );
                              }else{
                                item.setNombre(listArchivos.get(j).getNombre().substring(listArchivos.get(j).getNombre().indexOf(']')+1) + "|N");  
                              }
                              items.add(item);    
                            }      
                         }
                       } 
                    }
                    
                    archivosJSON.setItems(items);
            }catch(Exception e){
                e.printStackTrace();
            }
            
            return archivosJSON;
        }    
        
	@SuppressWarnings("unchecked")
	@SMDMethod
	public List<Recurso> getUnread() {
        	log.debug("-> [Action] DojoAction - getUnread():List<Recurso> ");
                Integer iEnabled = 0;
		Integer iUnread = 0;
		List<Recurso> lstUnread = new ArrayList<Recurso>();
		Map<String, Integer> mapRecurso = null;
		Usuario objUsuario = null;

		mapSession = ActionContext.getContext().getSession();
		mapRecurso = (Map<String, Integer>) mapSession.get(Constantes.SESSION_RECURSO);
		objUsuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);

		for (String sRecurso : Constantes.arrUnreadMenu) {
                        iEnabled = (Integer) mapRecurso.get(sRecurso);
			log.debug("Recurso [" + sRecurso + "] Habilitado [" + iEnabled + "]");
                        
                        if (iEnabled > 0) {
				iUnread = 0;
                                if (sRecurso.equalsIgnoreCase(Constantes.RECURSO_DOC_REGISTADOS) || sRecurso.equalsIgnoreCase(Constantes.RECURSO_DOC_DIGITALIZADOS) || sRecurso.equalsIgnoreCase(Constantes.RECURSO_DOC_DIGITALIZADOR)) {
				       iUnread = documentoService.findCantDocumentos(objUsuario);
                                } else if (sRecurso.equalsIgnoreCase(Constantes.RECURSO_NOTIFICACIONES)) {   
                                        iUnread = notificacionService.obtenerCantidadNotificacionesxUsuario(objUsuario.getIdusuario());
                                } else if (sRecurso.equalsIgnoreCase(Constantes.RECURSO_INFORMATIVOS)) {
					List<Notificacion> lstNotificacion = null;
                                        lstNotificacion = notificacionService.buscarLstPor(objUsuario, Constantes.TIPO_NOTIFICACION_DERIVACION, Constantes.ESTADO_NO_LEIDO);
					lstNotificacion.addAll(notificacionService.buscarLstPor(objUsuario, Constantes.TIPO_NOTIFICACION_DERIVACIONCONCOPIA, Constantes.ESTADO_NO_LEIDO));
					lstNotificacion.addAll(notificacionService.buscarLstPor(objUsuario, Constantes.TIPO_NOTIFICACION_NUMERACION_DESTINATARIO, Constantes.ESTADO_NO_LEIDO));
					lstNotificacion.addAll(notificacionService.buscarLstPor(objUsuario, Constantes.TIPO_NOTIFICACION_NUMERACION_DOCUMENTOCONCOPIA, Constantes.ESTADO_NO_LEIDO));
                                        lstNotificacion.addAll(notificacionService.buscarLstPor(objUsuario, Constantes.TIPO_NOTIFICACION_DERIVACIONMULTIPLECONCOPIA, Constantes.ESTADO_NO_LEIDO));
					lstNotificacion.addAll(notificacionService.buscarLstPor(objUsuario, Constantes.TIPO_NOTIFICACION_FIN_APOYO, Constantes.ESTADO_NO_LEIDO));
					iUnread = lstNotificacion.size();
				} else if (sRecurso.equalsIgnoreCase(Constantes.RECURSO_DOC_RECIBIDOS)) {  
                                        iUnread = documentoService.findCantDocumentosRecibidos(objUsuario);
				} else if (sRecurso.equalsIgnoreCase(Constantes.RECURSO_MIS_EXPEDIENTES)) {
                                        iUnread = documentoService.findCantMisExpedientes(objUsuario);
				}else if (sRecurso.equalsIgnoreCase(Constantes.RECURSO_FIRMA)) {
                                        iUnread = documentoService.findCantFirmas(objUsuario);
				}else if (sRecurso.equalsIgnoreCase(Constantes.RECURSO_MIS_RECEPCION_VIRTUAL)) { 
                                        iUnread = documentoService.findCantMisRecepcionVirtual(objUsuario);
				}else if (sRecurso.equalsIgnoreCase(Constantes.RECURSO_RECEPCION_VIRTUAL_OBSERVADOS)) { 
                                        iUnread = documentoService.findCantMisRecepcionVirtualObservados(objUsuario);
				} 
                                
                                Recurso objRecurso = new Recurso(sRecurso, iUnread);
				lstUnread.add(objRecurso);	
			}
		}

		return lstUnread;
	}

        @SMDMethod
	public String getFechaLimite(String plazo){
            try{
                 SimpleDateFormat formatFecha = new SimpleDateFormat("dd-MM-yyyy"); 
                 return formatFecha.format(fechaLimite.getFechaLimite(new Date(), Integer.parseInt(plazo))) + "";
            }catch(Exception e){
                e.printStackTrace();
                return "";
            }    
        }   
        
	@SMDMethod
	public List<Recurso> getUsuariosCompartidos() {
		log.debug("-> [Action] DojoAction - getUsuariosCompartidos():List<Recurso> ");

		List<Recurso> usuariosCompartidos = new ArrayList<Recurso>();
		Usuario objUsuario = null;
		mapSession = ActionContext.getContext().getSession();
		objUsuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
		log.debug("Usuario -> " + objUsuario.getIdusuario());
		List<Usuario> tmp = usuarioService.buscarUsuariosCompartidos(objUsuario.getIdusuario());
		for (Usuario u:tmp) {
			usuariosCompartidos.add(new Recurso(u.getNombreCompleto(), u.getIdusuario()));
		}
		log.debug("N usuarios compartidos = " + (usuariosCompartidos != null ? usuariosCompartidos.size(): "Error al obtener la kista de usuarios compartidos"));
		return usuariosCompartidos;
	}


	@SMDMethod
	public List<Recurso> getCountDocuments() {
		log.debug("-> [Action] DojoAction - getCountDocuments():List<Recurso> ");

		Usuario objUsuario = null;

		mapSession = ActionContext.getContext().getSession();
		objUsuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
		return documentoService.getCountDocuments(objUsuario);
	}

	@SMDMethod
	public String updateRead(Integer iIdToUpdate, String sCodigo) {
		log.debug("-> [Action] DojoAction - updateRead():String ");

                mapSession = ActionContext.getContext().getSession();
		Usuario objUsuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
		objUsuario = usuarioService.findByIdUsuario(objUsuario.getIdusuario());

		Documento objDocumento = null;
		Notificacion objNotificacion = null;

		log.debug("Se recibio el ID [" + iIdToUpdate + "]");

               if (sCodigo.equalsIgnoreCase(Constantes.RECURSO_DOC_RECIBIDOS) || sCodigo.equalsIgnoreCase(Constantes.RECURSO_FIRMA) || sCodigo.equalsIgnoreCase(Constantes.RECURSO_MIS_RECEPCION_VIRTUAL)) {
			String nombrePC = (String)mapSession.get("nombrePC");
			objDocumento = documentoService.updateLeido(iIdToUpdate, objUsuario,nombrePC);
			log.debug("Documento actualizado con ID [" + objDocumento.getIdDocumento() + "] a estado leido [" + objDocumento.getLeido() + "]");
		} else if (sCodigo.equalsIgnoreCase(Constantes.RECURSO_NOTIFICACIONES) || sCodigo.equalsIgnoreCase(Constantes.RECURSO_INFORMATIVOS)) {
			objNotificacion = notificacionService.updateLeido(iIdToUpdate);
			log.debug("Notificacion actualizada con ID [" + objNotificacion.getIdnotificacion() + "] a estado leido [" + objNotificacion.getLeido() + "]");
		}else if  (sCodigo.equalsIgnoreCase(Constantes.RECURSO_RECEPCION_VIRTUAL_OBSERVADOS)) {
                	String nombrePC = (String)mapSession.get("nombrePC");
			objDocumento = documentoService.updateLeido(iIdToUpdate, objUsuario,nombrePC);
			log.debug("Documento actualizado con ID [" + objDocumento.getIdDocumento() + "] a estado leido [" + objDocumento.getLeido() + "]");
		} 

		return "done";
	}

	@SMDMethod
	public String verificarProceso(Integer iIdProceso) {
		log.debug("-> [Action] DojoAction - verificarProceso():String ");
		
		log.debug("Se recibio el ID del proceso [" + iIdProceso + "]");
		Proceso objProceso = srvProceso.findByIdProceso(iIdProceso);
		
                if (Constantes.PROCESO_STOR.equalsIgnoreCase(objProceso.getIdgrupoproceso().getNombre())) {
			return Constantes.PROCESO_STOR;
		}

		return "none";
	}

	@SMDMethod
	public ObjetoJSON getCamposPorPlantilla(Integer iTipoPlantilla) {
		log.debug("-> [Action] DojoAction - getCamposPorPlantilla():ObjetoJSON ");

		log.debug("ID de Plantilla [" + iTipoPlantilla + "]");
		ObjetoJSON objJSON = new ObjetoJSON();
		if (iTipoPlantilla > 0) {
			objJSON.setItems(gridColumnaXUsuarioService.getCamposPorPlantilla(iTipoPlantilla));
		} else {
			objJSON.setItems(new ArrayList<Campo>());
		}
		return objJSON;
	}

	@SMDMethod
	public void deletePlantilla(Integer iIdPlantilla) {
		log.debug("-> [Action] DojoAction - deletePlantilla():void ");

		Plantilla objPlantilla = srvPlantilla.findByIdplantilla(iIdPlantilla);
		objPlantilla.setEstado(Constantes.ESTADO_INACTIVO);
		objPlantilla = srvPlantilla.guardarObj(objPlantilla);
		log.debug("Se elimino plantilla con ID [" + objPlantilla.getIdplantilla() + "] Nombre [" + objPlantilla.getDescripcion() + "]");
	}

	@SuppressWarnings("unchecked")
	@SMDMethod
	public void savePlantilla(Plantilla objPlantilla, Integer[] arrCampoToDelete) {
		log.debug("-> [Action] DojoAction - savePlantilla():void ");

		Integer iTipo = null;
		String sDescripcion = null;
		String sCampos = null;
		if (objPlantilla == null) {
			log.error("No se recibio ninguna plantilla");
			return;
		}
		log.debug("Se recibio plantilla con ID [" + objPlantilla.getIdplantilla() + "]");
		sCampos = objPlantilla.getCampos();
		if (objPlantilla.getIdplantilla().intValue() > 0) {
			iTipo = objPlantilla.getTipo();
			sDescripcion = objPlantilla.getDescripcion();
			objPlantilla = srvPlantilla.findByIdplantilla(objPlantilla.getIdplantilla());
			objPlantilla.setTipo(iTipo);
			objPlantilla.setDescripcion(sDescripcion);
		} else {
			objPlantilla.setIdplantilla(null);
			objPlantilla.setFechacreacion(new Date());
			objPlantilla.setEstado(Constantes.ESTADO_ACTIVO);
		}
		Object obj = JSONValue.parse(sCampos);
		JSONObject objeto = (JSONObject) obj;
		JSONArray items = (JSONArray) objeto.get("items");
		Iterator<JSONObject> iter = items.iterator();
		Campo objCampo = null;
		JSONObject item = null;
		while (iter.hasNext()) {
			item = iter.next();
			Integer id = Integer.valueOf(item.get("id").toString());
			String codigo = (String) item.get("codigo");
			String descripcion = (String) item.get("descripcion");
			String tipo = (String) item.get("tipo");
			String valor = (String) item.get("valor");
			log.debug("ID [" + id + "]");
			log.debug("Codigo [" + codigo + "]");
			log.debug("Descripcion [" + descripcion + "]");
			log.debug("Tipo [" + tipo + "]");
			log.debug("Valor por Defecto [" + valor + "]");
			if (id.intValue() > 0) {
				for (int i = 0; i < objPlantilla.getCampoList().size(); i++) {
					if (objPlantilla.getCampoList().get(i).getIdCampo().intValue() == id.intValue()) {
						objPlantilla.getCampoList().get(i).setCodigo(codigo);
						objPlantilla.getCampoList().get(i).setDescripcion(descripcion);
						objPlantilla.getCampoList().get(i).setTipo(tipo.equals("Caja de Texto") ? "TX" : "TA");
						objPlantilla.getCampoList().get(i).setValorDefecto(valor);
						break;
					}
				}
			} else {
				objCampo = new Campo();
				objCampo.setCodigo(codigo);
				objCampo.setDescripcion(descripcion);
				objCampo.setTipo(tipo.equals("Caja de Texto") ? "TX" : "TA");
				objCampo.setValorDefecto(valor);
				objCampo.setEstado(Constantes.ESTADO_ACTIVO);
				objCampo.setPlantilla(objPlantilla);
				if (objPlantilla.getIdplantilla() != null) {
					srvCampo.guardarObj(objCampo);
				} else {
					if (objPlantilla.getCampoList() == null) {
						log.debug("No hay campos asociados a la plantilla");
						objPlantilla.setCampoList(new ArrayList<Campo>());
					}
					objPlantilla.getCampoList().add(objCampo);
				}
			}
		}
		if (arrCampoToDelete != null && objPlantilla.getCampoList() != null) {
			log.debug("Campos a eliminar [" + arrCampoToDelete.length + "]");
			for (Integer iIdC : arrCampoToDelete) {
				for (int i = 0; i < objPlantilla.getCampoList().size(); i++) {
					if (objPlantilla.getCampoList().get(i).getIdCampo().intValue() == iIdC.intValue()) {
						objPlantilla.getCampoList().get(i).setEstado(Constantes.ESTADO_INACTIVO);
						break;
					}
				}
			}
		}
		objPlantilla = srvPlantilla.guardarObj(objPlantilla);
		log.debug("Se guardo plantilla con ID [" + objPlantilla.getIdplantilla() + "] Nombre [" + objPlantilla.getDescripcion() + "]");
	}

	@SMDMethod
	public Integer validarExpedienteHistorico(String sNroExpediente) {
		log.debug("-> [Action] DojoAction - validarExpedienteHistorico():Integer ");

		Expediente objExpediente = expedienteService.buscarObjPor(sNroExpediente);

		if (objExpediente == null) {
			log.debug("No se encontro ningun Expediente con Nro [" + sNroExpediente + "]");
		} else {
			log.debug("Expediente encontrado con ID [" + objExpediente.getIdexpediente() + "] Nro Expediente [" + objExpediente.getNroexpediente() + "]");

			return 1;
		}

		return 0;
	}

	private void registrarAuditoriaReemplazos(Proceso proceso, Reemplazo reemplazo, String tipoauditoria, String modulo, String opcion) {
		log.debug("-> [Action] DojoAction - registrarAuditoriaReemplazos():void ");

		/*******************************************************/
		// Registrando la auditoria del Reemplazo
		/*******************************************************/
		AuditoriaDAO daoauditoria = (AuditoriaDAO) WebApplicationContextUtils.getWebApplicationContext(ServletActionContext.getRequest().getSession().getServletContext()).getBean("auditoriaDAO");
		Usuario usuario = (Usuario) ActionContext.getContext().getSession().get(Constantes.SESSION_USUARIO);
		/*******************************************************/
		// Registrando la auditoria
		/*******************************************************/
		Auditoria ObjAuditoriaReemplazo = new Auditoria();
		ObjAuditoriaReemplazo.setTipoAuditoria(tipoauditoria);
		ObjAuditoriaReemplazo.setModulo(modulo);
		ObjAuditoriaReemplazo.setOpcion(opcion);
		ObjAuditoriaReemplazo.setFechaAudioria(new Date());
		ObjAuditoriaReemplazo.setUsuario(usuario.getUsuario());
		ObjAuditoriaReemplazo.setCampo("Reemplazante");
		ObjAuditoriaReemplazo.setNuevoValor(reemplazo.getIdusuario().getUsuario());
		ObjAuditoriaReemplazo.setProceso(proceso);
		daoauditoria.SaveAuditoria(ObjAuditoriaReemplazo);
	}

	@SMDMethod
	public String saveNumeracion(Numeracion objNumeracion, String sIdUnidad, String sIdTipoDocumento) {
		log.debug("-> [Action] DojoAction - saveNumeracion():String ");

		if (objNumeracion == null) {
			log.error("No se recibio ninguna Numeracion");

			return "error";
		}

		if (sIdUnidad.equals("") && sIdTipoDocumento.equals("")) {
			log.debug("Se recibio unidad con ID [" + objNumeracion.getIdNumeracion().getIdunidad() + "]");
			log.debug("Se recibidio tipo de documento con ID [" + objNumeracion.getIdNumeracion().getIdtipodocumento() + "]");

			if (srvNumeracion.findById(objNumeracion.getIdNumeracion().getIdunidad(), objNumeracion.getIdNumeracion().getIdtipodocumento()) != null) {
				log.debug("Esta Numeracion ya existe en la Base de Datos");

				return "NotCreated";
			}

			objNumeracion.setUnidad(srvUnidad.buscarObjPor(objNumeracion.getIdNumeracion().getIdunidad()));
			objNumeracion.setTipodocumento(srvTipoDocumento.findByIdTipoDocumento(objNumeracion.getIdNumeracion().getIdtipodocumento()));
		}

		objNumeracion.setFormato(objNumeracion.getFormatoleft() + "$NUMERO" + objNumeracion.getFormatoright());

		if (sIdUnidad.equals("") && sIdTipoDocumento.equals("")) {
			objNumeracion = srvNumeracion.guardarObj(objNumeracion);
			log.debug("Numeracion creada");
		} else {
			objNumeracion = srvNumeracion.actualizarObj(objNumeracion);
			log.debug("Numeracion actualizada");
		}

		return "cool";
	}

        
        @SMDMethod
	public ClienteJSon getCliente(Integer idCliente) {
                log.debug("-> [Action] DojoAction - getClientePorCliente():ClienteJSon ");
                ClienteJSon cliente = null;
		Cliente clienteExistente = clienteService.findByIdCliente(idCliente);
		
                String departamento = "";
                String provincia    = "";
                String distrito     = "";
                
                if (clienteExistente.getUbigeoPrincipal()!=null){
                      distrito  = clienteExistente.getUbigeoPrincipal().getIddistrito().toString();
                      provincia =clienteExistente.getUbigeoPrincipal().getProvincia().getIdprovincia().toString();
                      departamento = clienteExistente.getUbigeoPrincipal().getProvincia().getDepartamento().getIddepartamento().toString();
                }
                
                if (clienteExistente!=null){
                     Integer valor = null;
                
                     //if (clienteExistente.getTipoinstitucion() !=null && clienteExistente.getTipoinstitucion().getCod_tipoinstitucion()!=null)
                     valor = clienteExistente.getCodtipoinstitucion();
                     CargoAdministrado cargoAdministrado = null; 
                        
                     if (clienteExistente.getCodCargoCliente()!=null)
                       cargoAdministrado =   cargoAdministradoService.findCargoAdministrado(clienteExistente.getCodCargoCliente());
                        
                     cliente = new ClienteJSon(clienteExistente.getNumeroIdentificacion(),
                                                  clienteExistente.getTipoIdentificacion()==null?"":clienteExistente.getTipoIdentificacion().getNombre().toString(),
                                                  valor == null? null: valor.toString(),
                                                  clienteExistente.getRazonSocial(),
                                                  clienteExistente.getNombres(),
                                                  clienteExistente.getApellidoPaterno(),
                                                  clienteExistente.getApellidoMaterno(),
                                                  departamento,
                                                  provincia,
                                                  distrito,
                                                  clienteExistente.getDireccionPrincipal(),
                                                  clienteExistente.getTelefono(),
                                                  clienteExistente.getCorreo(),
                                                  clienteExistente.getCodCargoCliente() == null? null:clienteExistente.getCodCargoCliente().toString(), "OK",
                                                  cargoAdministrado == null ? "":cargoAdministrado.getDesCargo());
 
                }else{
                    cliente = new ClienteJSon();
                    cliente.setCodRespuesta("ERROR");
                }
                
		return cliente;
	}
        
        @SMDMethod 
	public String verificarCerrarLegajo(Integer idLegajo){
            try{
                   Legajo legajo = new Legajo();
                   legajo.setIdLegajo(idLegajo);
                   legajo.setEstado("C");
                   legajo = legajoService.findByIdLegajo(legajo);
                   
                   if (legajo!=null)
                     return "1"; 
            }catch(Exception e){
                   e.printStackTrace();
                   return "2";    
            }       
            
            return "0";
        }
        
        @SMDMethod 
	public String cerrarLegajo(Integer idLegajo){
            log.debug("-> [Action] DojoAction - cerrarLegajo():String ");
            try{
                   mapSession = ActionContext.getContext().getSession();
                   Usuario objUsuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
                    
                   Legajo legajo = new Legajo();
                   legajo.setIdLegajo(idLegajo);
                   legajo.setEstado("C");
                   legajo = legajoService.findByIdLegajo(legajo);
                
                   if (legajo!=null)
                     return "3"; 
                   
                   legajo = new Legajo();
                   legajo.setIdLegajo(idLegajo);
                   legajo.setEstado("");
                   legajo = legajoService.findByIdLegajo(legajo);
                   legajo.setEstado("C");
                   legajo.setFechaModificacion(new Date());
                   legajo.setUsuarioModificacion(objUsuario.getIdusuario());
                   legajoService.saveLegajo(legajo, null);
                           
                   return "1";      
            }catch(Exception e){
                e.printStackTrace();
                return "0";
            }
        }
        
        @SMDMethod 
	public String saveLegajo(String tipo, String tipoProcedimiento, String tipoMetodo, String sNroLegajo, Integer idDocumento, String asunto, String observacion, String sNota1Legajo, String sNota2Legajo, String sNota3Legajo, String sNota4Legajo) {
            log.debug("-> [Action] DojoAction - saveLegajo():String ");
            try{
                    StringBuilder sbNroLegajo = null;
                    mapSession = ActionContext.getContext().getSession();
                    Usuario objUsuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
                    Documento d = documentoService.findByIdDocumento(idDocumento);
                    TipoLegajo tipoLegajo = new TipoLegajo();
                    tipoLegajo.setIdTipo(new Integer(tipo));
                    
                    Unidad unidad = new Unidad(objUsuario.getIdUnidadPerfil());
                    Legajo legajo = new Legajo();
                    legajo.setAsunto(asunto);
                    legajo.setObservacion(observacion);
                    legajo.setFechaCreacion(new Date());
                    legajo.setEstado("A");
                    legajo.setUsuarioCreacion(objUsuario.getIdusuario());
                    legajo.setTipoLegajo(tipoLegajo);
                    legajo.setUnidad(unidad);
                    legajo.setNota1(sNota1Legajo);
                    legajo.setNota2(sNota2Legajo);
                    legajo.setNota3(sNota3Legajo);
                    legajo.setNota4(sNota4Legajo);
                    
                    LegajoDocumento legajoDocumento = new LegajoDocumento();
                    legajoDocumento.setEstado("A");
                    legajoDocumento.setIdDocumento(d.getDocumentoreferencia()==null?d.getIdDocumento():d.getDocumentoreferencia());
                    legajoDocumento.setFechaCreacion(new Date());
                    legajoDocumento.setUsuarioCreacion(objUsuario.getIdusuario());
                    
                    Date date = new Date();
                    if (tipo.equals("2")){
                       legajo.setIdProcedimiento(tipoProcedimiento);
                       legajo.setIdMetodo(null);
                       TipoLegajo tipoLegajoAux = new TipoLegajo();
                       tipoLegajoAux.setIdTipo(new Integer(tipo));
                       tipoLegajoAux = tipoLegajoDAO.findTipoLegajo(tipoLegajoAux);
                       
                       tipoLegajo = new TipoLegajo();
                       tipoLegajo.setAcronimo(tipoProcedimiento);
                       tipoLegajo.setNumeracion(tipoLegajoAux.getNumeracion());
                       
                       if (tipoProcedimiento.length()==2){
                           tipoLegajo.setAcronimo("-" + tipoProcedimiento);
                       }    
                    }else{
                       if (tipo.equals("3")){ 
                          legajo.setIdMetodo(tipoMetodo);
                          legajo.setIdProcedimiento(null);
                          tipoLegajo = tipoLegajoDAO.findTipoLegajo(tipoLegajo);
                       }else{
                          legajo.setIdMetodo(null);
                          legajo.setIdProcedimiento(null); 
                          tipoLegajo = tipoLegajoDAO.findTipoLegajo(tipoLegajo); 
                       }   
                    }
                    
                    DateFormat formatoAno = new SimpleDateFormat("yyyy");
                    String sPrefijo = Constantes.PARAMETRO_TIPO_PREFIJONROLEGAJOPRODUCCION;
                    
                    if (tipoLegajo.getNumeracion()!=null && tipoLegajo.getNumeracion().equals("M")){
                        String ano = sNroLegajo.substring(sNroLegajo.length()-4, sNroLegajo.length());
                        String sFormato = ano.concat(parametroService.findByTipoUnico(sPrefijo).getValor());
                        sNroLegajo = sNroLegajo.replace("-", "").replaceAll(" ", "");
                        String nro = sNroLegajo.substring(0, sNroLegajo.length()-4);
                        sbNroLegajo = new StringBuilder((StringUtil.isEmpty(sFormato)) ? "" : sFormato);
                        sbNroLegajo.replace(sFormato.length() - nro.length(), sFormato.length(), nro);
                    }else{
                        Integer iIdLegajo = legajoService.generateNroLegajoProduccion(tipoLegajo);
                        String sFormato = formatoAno.format(date).concat(parametroService.findByTipoUnico(sPrefijo).getValor());
                        sbNroLegajo = new StringBuilder((StringUtil.isEmpty(sFormato)) ? "" : sFormato);
                        sbNroLegajo.replace(sFormato.length() - iIdLegajo.toString().length(), sFormato.length(), iIdLegajo.toString());   
                    }
                    
                    Legajo legajoAux = new Legajo();
                    legajoAux.setNroLegajo(sbNroLegajo.toString() + tipoLegajo.getAcronimo());
                    List<Legajo> list = legajoService.findByNroLegajo(legajoAux);
                    
                    if (list!=null && list.size()>0)
                        return "2";
                    
                    legajo.setNroLegajo(sbNroLegajo.toString() + tipoLegajo.getAcronimo());
                    legajoService.saveLegajo(legajo, legajoDocumento);
                    
                    return "1";
              }catch(Exception e){
                    e.printStackTrace();
                    return "0";
              } 
        }
        
         @SMDMethod 
	public String actualizarLegajo(Integer idLegajo, String tipo, String tipoProcedimiento, String tipoMetodo, String sNroLegajo,String asunto, String observacion, String sNota1Legajo, String sNota2Legajo, String sNota3Legajo, String sNota4Legajo) {
            log.debug("-> [Action] DojoAction - actualizarLegajo():String ");
            try{
                    StringBuilder sbNroLegajo = null;
                    String sPrefijo = Constantes.PARAMETRO_TIPO_PREFIJONROLEGAJOPRODUCCION;
                    mapSession = ActionContext.getContext().getSession();
                    Usuario objUsuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
                    TipoLegajo tipoLegajo = new TipoLegajo();
                    
                    Legajo legajo = new Legajo();
                    legajo.setIdLegajo(idLegajo);
                    legajo.setEstado("C");
                    legajo = legajoService.findByIdLegajo(legajo);
                    
                    if (legajo!=null)
                        return "3";
                    
                    legajo = new Legajo();
                    legajo.setIdLegajo(idLegajo);
                    legajo.setEstado("");
                    legajo = legajoService.findByIdLegajo(legajo);
                    tipoLegajo.setIdTipo(new Integer(tipo));
                    tipoLegajo = tipoLegajoDAO.findTipoLegajo(tipoLegajo);
                    
                    if (tipoLegajo.getNumeracion().equals("M")){
                        String ano = sNroLegajo.substring(sNroLegajo.length()-4, sNroLegajo.length());
                        String sFormato = ano.concat(parametroService.findByTipoUnico(sPrefijo).getValor());
                        sNroLegajo = sNroLegajo.replace("-", "").replaceAll(" ", "");
                        String nro = sNroLegajo.substring(0, sNroLegajo.length()-4);
                        sbNroLegajo = new StringBuilder((StringUtil.isEmpty(sFormato)) ? "" : sFormato);
                        sbNroLegajo.replace(sFormato.length() - nro.length(), sFormato.length(), nro);
                        
                        if (tipo.equals("3")){ 
                          legajo.setIdMetodo(tipoMetodo);
                          legajo.setIdProcedimiento(null); 
                        }
                        if (tipo.equals("2")){ 
                          legajo.setIdMetodo(null);
                          legajo.setIdProcedimiento(tipoProcedimiento);
                          tipoLegajo = new TipoLegajo();
                          tipoLegajo.setIdTipo(new Integer(tipo));
                          tipoLegajo.setAcronimo(tipoProcedimiento);
                          if (tipoProcedimiento.length()==2){
                             tipoLegajo.setAcronimo("-" + tipoProcedimiento);
                          }    
                        }
                        
                        legajo.setNroLegajo(sbNroLegajo.toString() + tipoLegajo.getAcronimo());
                        legajo.setTipoLegajo(tipoLegajo);
                    }else{
                         tipoLegajo = new TipoLegajo();
                         tipoLegajo.setIdTipo(legajo.getTipoLegajo().getIdTipo());
                         tipoLegajo = tipoLegajoDAO.findTipoLegajo(tipoLegajo);
                    
                         if (tipoLegajo.getNumeracion().equals("M")){
                             tipoLegajo = new TipoLegajo();
                             tipoLegajo.setIdTipo(new Integer(tipo));
                             tipoLegajo = tipoLegajoDAO.findTipoLegajo(tipoLegajo);
                             Date date = new Date();
                             DateFormat formatoAno = new SimpleDateFormat("yyyy");
                             Integer iIdLegajo = legajoService.generateNroLegajoProduccion(tipoLegajo);
                             String sFormato = formatoAno.format(date).concat(parametroService.findByTipoUnico(sPrefijo).getValor());
                             sbNroLegajo = new StringBuilder((StringUtil.isEmpty(sFormato)) ? "" : sFormato);
                             sbNroLegajo.replace(sFormato.length() - iIdLegajo.toString().length(), sFormato.length(), iIdLegajo.toString());
                             legajo.setNroLegajo(sbNroLegajo.toString() + tipoLegajo.getAcronimo());
                         }
                    
                         tipoLegajo = new TipoLegajo();
                         tipoLegajo.setIdTipo(new Integer(tipo));
                         legajo.setIdMetodo(null);
                         legajo.setIdProcedimiento(null);
                         legajo.setTipoLegajo(tipoLegajo);
                    }
                    
                    Legajo legajoAux = new Legajo();
                    legajoAux.setNroLegajo(legajo.getNroLegajo());
                    List<Legajo> list = legajoService.findByNroLegajo(legajoAux);
                    
                    
                    if (list!=null || list.size()>0){
                        for(int i=0;i<list.size();i++){
                            if (!legajo.getIdLegajo().toString().equals(list.get(i).getIdLegajo().toString()))
                              return "2";  
                        }
                    }
                        
                    legajo.setAsunto(asunto);
                    legajo.setObservacion(observacion);
                    legajo.setFechaModificacion(new Date());
                    legajo.setUsuarioModificacion(objUsuario.getIdusuario());
                    legajo.setNota1(sNota1Legajo);
                    legajo.setNota2(sNota2Legajo);
                    legajo.setNota3(sNota3Legajo);
                    legajo.setNota4(sNota4Legajo);
                    legajoService.saveLegajo(legajo, null);
                    
                    return "1";
              }catch(Exception e){
                    e.printStackTrace();
                    return "0";
              } 
        }
        
        @SMDMethod 
	public String quitarDocumentoToExpediente(Integer expediente, Integer idDocumento) {
             log.debug("-> [Action] DojoAction - quitarDocumentoToExpediente():String ");
            try{
                
                mapSession = ActionContext.getContext().getSession();
                Usuario objUsuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
                Documento d = documentoService.findByIdDocumento(idDocumento);
                
                Legajo legajo = new Legajo();
                legajo.setIdLegajo(expediente);
                legajo.setEstado("C");
                legajo = legajoService.findByIdLegajo(legajo);
                 
                if (legajo!=null)
                   return "3"; 
                
                LegajoDocumento legajoDocumento = new LegajoDocumento();
                legajoDocumento.setIdDocumento(d.getDocumentoreferencia()==null?d.getIdDocumento():d.getDocumentoreferencia());
                legajoDocumento.setIdLegajo(expediente);
                legajoDocumento = legajoDocumentoService.findLegajoDocumento(legajoDocumento);
                 
                if (legajoDocumento == null)
                  return "2";
                
                legajoDocumento.setEstado("I");
                legajoDocumento.setFechaModificacion(new Date());
                legajoDocumento.setUsuarioModificacion(objUsuario.getIdusuario());
                legajoDocumentoService.saveLegajoDocumento(legajoDocumento);
                
                return "1";
            }catch(Exception e){
                e.printStackTrace();
                 return "0";
            }    
        }
        
        @SMDMethod 
	public String agregarDocumentoToExpediente(Integer expediente, Integer idDocumento) {
            try{
                mapSession = ActionContext.getContext().getSession();
                Usuario objUsuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
                Documento d = documentoService.findByIdDocumento(idDocumento);
  
                LegajoDocumento legajoDocumento = new LegajoDocumento();
                legajoDocumento.setEstado("A");
                legajoDocumento.setIdDocumento(d.getDocumentoreferencia()==null?d.getIdDocumento():d.getDocumentoreferencia());
                legajoDocumento.setIdLegajo(expediente);
                legajoDocumento.setFechaCreacion(new Date());
                legajoDocumento.setUsuarioCreacion(objUsuario.getIdusuario());
                
                Legajo legajo = new Legajo();
                legajo.setIdLegajo(expediente);
                legajo.setEstado("C");
                legajo = legajoService.findByIdLegajo(legajo);
                
                if (legajo!=null)
                    return "3"; 
                
                if (legajoDocumentoService.findLegajoDocumento(legajoDocumento)==null) //PASO
                    legajoDocumentoService.saveLegajoDocumento(legajoDocumento);
                else
                    return "2";
              
                return "1";
           
            }catch(Exception e){
                e.printStackTrace();
                return "0";
            }    
        }    
        
        @SMDMethod
	public RemitenteJSON getRemitente(Integer idCliente, Integer idRemitente) {
		log.debug("-> [Action] DojoAction - getRemitente():RemitenteJSon ");

                RemitenteJSON remitente = null;
		DetalleCliente detalleCliente = detalleClienteService.findByDetalleClienteId(idRemitente);
		if (detalleCliente != null) {
		   remitente = new RemitenteJSON( //JC24
                                                 detalleCliente.getIdCargoAdministrado().toString(),
                                                 detalleCliente.getNombres(), detalleCliente.getApellidoPaterno(),
                                                 detalleCliente.getApellidoMaterno(), detalleCliente.getIdDetalleCliente().toString(), "OK", 
                                                 detalleCliente.getCargoAdministrado().getDesCargo()); 	
			
		} else {
	           remitente = new RemitenteJSON();
                   remitente.setCodRespuesta("ERROR");
		}
		return remitente;
	}

        /*
	@SMDMethod
	public List<ClienteJSon> getClientesPorRazonSocial(String razonSocial) {
		log.debug("-> [Action] DojoAction - getClientesPorRazonSocial():List<ClienteJSon> ");

		List<ClienteJSon> salida = new ArrayList<ClienteJSon>();
		List<ClienteJSon> encontrados = clienteService.getClientesPorRazonSocialSunat(razonSocial);
		if (encontrados != null) {
			for (ClienteJSon cliente : encontrados) {
				Cliente existente = clienteService.findByNroIdentificacion(cliente.getIdentificacion());
				if (existente == null) {
					salida.add(cliente);
				}
			}
			return salida;
		}
		return null;
	}*/


	@SuppressWarnings({ "rawtypes", "unchecked" })
	@SMDMethod
	public List ObtenerlistaCampos2(String tipoPlantilla, String idexpediente) {
		log.debug("-> [Action] DojoAction - ObtenerlistaCampos2():List ");

		List<Campo> lstCampo = null;
		Expediente objE = null;

		log.debug("Tipo de Plantilla con ID [" + tipoPlantilla + "]");
		log.debug("Expediente asociado con ID [" + idexpediente + "]");
		lstCampo = srvPlantilla.listCamposByTipoPlantilla(new Integer(tipoPlantilla));

		if (!idexpediente.isEmpty()) {
			objE = expedienteService.findByIdExpediente(Integer.valueOf(idexpediente));
		}

		if (lstCampo == null) {
			lstCampo = new ArrayList();
			log.debug("$$$$$$$ vacio : ");
		} else {
			log.debug("$$$$$$$ tamano : " + lstCampo.size());

			for (int i = 0; i < lstCampo.size(); i++) {
				String sDatoValor = "";

				if (objE != null) {
					if (lstCampo.get(i).getValorDefecto().equals("Propietario del Documento")) {
						sDatoValor = objE.getDocumentoPrincipal().getPropietario().getNombres() + " " + objE.getDocumentoPrincipal().getPropietario().getApellidos();
					} else if (lstCampo.get(i).getValorDefecto().equals("Area del Propietario del Documento")) {
						sDatoValor = objE.getDocumentoPrincipal().getPropietario().getUnidad().getNombre();
					} else if (lstCampo.get(i).getValorDefecto().equals("Cliente")) {
						if (objE.getCliente().getTipoIdentificacion().getNombre().equals(Constantes.TIPO_IDENTIFICACION_RUC)) {
							sDatoValor = objE.getClienterazonsocial();
						} else {
							sDatoValor = objE.getClientenombres() + " " + objE.getClienteapellidopaterno();
						}
					} else if (lstCampo.get(i).getValorDefecto().equals("Concesionario")) {
						if (objE.getConcesionario() != null) {
							sDatoValor = objE.getConcesionario().getRazonSocial();
						}
					} else if (lstCampo.get(i).getValorDefecto().equals("Proceso")) {
						sDatoValor = null;// objE.getProceso().getNombre();
					} else if (lstCampo.get(i).getValorDefecto().equals("Etapa")) {
						if (objE.getIdetapa() != null) {
							sDatoValor = objE.getIdetapa().getDescripcion();
						}
					} else if (lstCampo.get(i).getValorDefecto().equals("Actividad")) {
						if (objE.getIdactividad() != null) {
							sDatoValor = objE.getIdactividad().getNombre();
						}
					} else if (lstCampo.get(i).getValorDefecto().equals("Nro Expediente")) {
						sDatoValor = objE.getNroexpediente();
					} else if (lstCampo.get(i).getValorDefecto().equals("Descripcion")) {
						sDatoValor = objE.getDescripcion();
					} else if (lstCampo.get(i).getValorDefecto().equals("Asunto del Expediente")) {
						sDatoValor = objE.getAsunto();
					} else if (lstCampo.get(i).getValorDefecto().equals("Contenido")) {
						sDatoValor = objE.getContenido();
					} else if (lstCampo.get(i).getValorDefecto().equals("Observacion")) {
						sDatoValor = objE.getObservacion();
					} else if (lstCampo.get(i).getValorDefecto().equals("Fecha Creacion")) {
						sDatoValor = objE.getFechacreacion().toString();
					} else if (lstCampo.get(i).getValorDefecto().equals("Estado")) {
						sDatoValor = String.valueOf(objE.getEstado());
					} else if (lstCampo.get(i).getValorDefecto().equals("Observacion de Rechazo")) {
						sDatoValor = objE.getObservacionrechazo();
					} else if (lstCampo.get(i).getValorDefecto().equals("Nro Interno")) {
						sDatoValor = objE.getNrointerno();
					} else if (lstCampo.get(i).getValorDefecto().equals("Responsable del Expediente")) {
						sDatoValor = objE.getIdpropietario().getNombres() + " " + objE.getIdpropietario().getApellidos();
					} else if (lstCampo.get(i).getValorDefecto().equals("Area del Responsable del Expediente")) {
						sDatoValor = objE.getIdpropietario().getUnidad().getNombre();
					} else if (lstCampo.get(i).getValorDefecto().equals("Responsable del Proceso")) {
						sDatoValor = null;//objE.getProceso().getResponsable().getNombres() + " " + objE.getProceso().getResponsable().getApellidos();
					} else if (lstCampo.get(i).getValorDefecto().equals("Area del Responsable del Proceso")) {
						sDatoValor = null;//objE.getProceso().getResponsable().getUnidad().getNombre();
					}
				}

				lstCampo.get(i).setValor(sDatoValor == null ? "" : sDatoValor);
			}
		}

		return lstCampo;
	}

	@SMDMethod
	public String ObtenerInfoNumeracion(String idProceso, String idtipodoc) {
		log.debug("-> [Action] DojoAction - ObtenerInfoNumeracion():String ");

		try {
			Proceso objP = srvProceso.findByIdProceso(Integer.valueOf(idProceso));
			Numeracion num = srvNumeracion.findByIdNumeracion(objP.getResponsable().getUnidad().getIdunidad(), new Integer(idtipodoc));
			log.debug("Numero actual [" + num.getNumeroactual() + "]");

			return num.getNumeroactual().toString();
		} catch (Exception e) {
			return "";
		}
	}

	@SMDMethod
	public Integer calcularEnFechaTexto(Integer idDoc, String fecha) {
		log.debug("-> [Action] DojoAction - calcularEnFechaTexto():Integer ");

		if (idDoc == null) {
			return 0;
		}
		try {
			SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
			Date fechaTest = fmt.parse(fecha);
			Date fechaLimite = documentoService.getFechaLimiteAtencion(idDoc);
			log.debug("Fecha T:" + fechaTest + " - Fecha L:" + fechaLimite);
			return (fechaTest.getTime() - fechaLimite.getTime()) > 0 ? 1 : -1;
		} catch (ParseException e) {
			return 0;
		}
	}

        /*
	@SMDMethod
	public ObjetoJSON findClientesbyIdDocumento(Integer idDocumento) {
		log.debug("-> [Action] DojoAction - findClientesbyIdDocumento():ObjetoJSON ");

		ObjetoJSON json = new ObjetoJSON();

		List<Documentoxcliente> listaDocxCliente = documentoxclienteService.findClientesbyIdDocumento(idDocumento);
		List<Item> listaItems = new ArrayList<Item>();
		for (Documentoxcliente objdxc : listaDocxCliente) {
			Item objItem = new Item();
			if (Constantes.TIPO_IDENTIFICACION_RUC.equals(objdxc.getCliente().getTipoIdentificacion().getNombre())) {
				objItem.setRazonsocial(objdxc.getCliente().getRazonSocial());
			} else {
				objItem.setRazonsocial(objdxc.getCliente().getNombres() + " " + objdxc.getCliente().getApellidoPaterno() + " " + objdxc.getCliente().getApellidoMaterno());
			}
			objItem.setId(objdxc.getCliente().getIdCliente());
			objItem.setTipo(objdxc.getCliente().getTipoIdentificacion().getNombre());
			objItem.setIdentificacion(objdxc.getCliente().getNumeroIdentificacion());
			objItem.setDireccion(objdxc.getCliente().getDireccionPrincipal());
			// objItem.set
			if (objdxc.getCliente().getUbigeoPrincipal() != null) {
				objItem.setDistrito(objdxc.getCliente().getUbigeoPrincipal().getNombre());
				objItem.setDepartamento(objdxc.getCliente().getUbigeoPrincipal().getProvincia().getDepartamento().getNombre());
				objItem.setProvincia(objdxc.getCliente().getUbigeoPrincipal().getProvincia().getNombre());
			}
			listaItems.add(objItem);
		}

		json.setItems(listaItems);

		return json;
	}
   */
	@SMDMethod
	public ObjetoJSON getDocumentos(Integer iIdExpediente) {
		log.debug("-> [Action] DojoAction - getDocumentos():ObjetoJSON ");

		ObjetoJSON json = new ObjetoJSON();
     	json.setItems(srvItem.findLstDocumentoBy(iIdExpediente));

		return json;
	}

	/**REN Metodo usado cuando se envia a mensajeria -------------------------------------------------------------------------*/
	@SMDMethod
	public String saveMensajeria(Integer tipoEnvio, List<Item> lstClienteCustom, String idsDocumentosSeleccionados, Integer idDocPrincipalExpediente) {
		log.debug("-> [Action] DojoAction - saveMensajeria():String ");

		if (lstClienteCustom == null || lstClienteCustom.size() <= 0) {
			log.error("No se recibio clientes");
			return null;
		}

		if (idDocPrincipalExpediente == null || idDocPrincipalExpediente == 0) {
			log.error("No se recibio documento principal");
			return null;
		}

		Integer[] idsDocumentosMensajeria = new Integer[1];
		if (idsDocumentosSeleccionados != null && !idsDocumentosSeleccionados.equals("")) {

			String[] idsStr = idsDocumentosSeleccionados.split(",");
			idsDocumentosMensajeria = new Integer[idsStr.length+1];
			int i = 0;
			for (String idStr : idsStr) {
				idsDocumentosMensajeria[i] = Integer.parseInt(idStr);
				i++;
			}
		}
		if (log.isDebugEnabled()) {
			log.debug("Tipo Envio [" + tipoEnvio + "]");
			for (Item item : lstClienteCustom) {
				log.debug("Cliente identificacion [" + item.getIdentificacion() + "] razon social [" + item.getRazonsocial() + "] direccion [" + item.getDireccion() + "] departamento [" + item.getDepartamento() + "] provincia [" + item.getProvincia() + "] distrito [" + item.getDistrito() + "]");
			}
			log.debug("Documento Principal [" + idDocPrincipalExpediente + "]");
			for (Integer idDocumento : idsDocumentosMensajeria) {
				log.debug("Documento ID [" + idDocumento + "]");
			}
		}

		try {
			mapSession = ActionContext.getContext().getSession();
			Usuario objUsuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
			String nombrePC = (String) mapSession.get("nombrePC");

			for (Item item : lstClienteCustom) {
				Mensajeria objMensajeria = srvMensajeria.guardarObj(objUsuario, idDocPrincipalExpediente, Constantes.DOCUMENTO_PRINCIPAL, tipoEnvio, item, null);
				for (int i = 0; i < idsDocumentosMensajeria.length-1; i++) {
					srvMensajeria.guardarObj(objUsuario, idsDocumentosMensajeria[i], Constantes.DOCUMENTO_NO_PRINCIPAL, tipoEnvio, item, objMensajeria.getIdmensajeria());
				}
			}
			idsDocumentosMensajeria[idsDocumentosMensajeria.length-1] = idDocPrincipalExpediente;
			srvMensajeria.guardarTrazaEnvioMensajeria(idsDocumentosMensajeria, lstClienteCustom, objUsuario, nombrePC);

		} catch (Exception e) {
			log.error(e.getMessage(), e);

			return null;
		}

		return "";
	}

        @SMDMethod
	public NumeracionJSON findNumeracionDocumentoxConcesionario(Integer iIdTipoDocumento, Integer idCliente, Integer AnioFiscal) {
		log.debug("-> [Action] DojoAction - findNumeracionDocumentoxConcesionario():NumeracionJSON ");
                
                Cliente cliente = clienteService.findByIdCliente(idCliente);
                Integer idUnidad = cliente.getIdUnidad();
                
                if (idUnidad==null)
                   return null; 
                
                Unidad unidad = new Unidad();
                unidad.setIdunidad(idUnidad);
                
		if (iIdTipoDocumento == null || idUnidad == null) {
			log.error("Parametros recibidos incorrectamente");

			return null;
		}

		
		Numeracion numeracion = null;

		if (log.isDebugEnabled()) {
			log.debug("iIdTipoDocumento [" + iIdTipoDocumento + "] idUnidad [" + idUnidad + "]");
		}

		if (idUnidad == null) {
			log.info("No se encontro firmante con idFirmante [" + idUnidad + "]");
			return null;
		}

               /*if ((numeracion = srvNumeracion.findByIdbyUnidad(unidad, iIdTipoDocumento)) == null) {
			return null;
		}*/
               
                if ((numeracion = srvNumeracion.findByIdNumeracionbyUnidadbyAnioFiscal(unidad, iIdTipoDocumento, AnioFiscal)) == null) {
                        return null;
                }


               return new NumeracionJSON(numeracion.getTiponumeracion(), numeracion.getFirmante(), numeracion.getDestinatario());
	}
        
        @SMDMethod
	public String verificarDuplicidadDocumento(Integer iIdTipoDocumento, String numero, Integer idUnidad, Integer nroExpediente) {
            log.debug("-> [Action] DojoAction - verificarDuplicidadDocumento():String ");
            
            try{
                 if (!nroExpediente.toString().equals("0")){
                    Legajo legajo = new Legajo();
                    legajo.setIdLegajo(nroExpediente);
                    legajo.setEstado("C");
                    legajo = legajoService.findByIdLegajo(legajo);
                
                   if (legajo!=null)
                     return "2"; 
                 }
                
                 boolean respuesta = documentoDao.verificarDuplicidadDocumento(iIdTipoDocumento, numero, idUnidad);

                 if (respuesta){
                    return "1"; 
                 }   
            }catch(Exception e){
                return "0";
            }    
            
            return "0";
        }
        
	@SMDMethod
	public NumeracionJSON findNumeracionDocumento(Integer iIdTipoDocumento, Integer idUnidad) {
		log.debug("-> [Action] DojoAction - findNumeracionDocumento():NumeracionJSON ");
                Unidad unidad = new Unidad();
                unidad.setIdunidad(idUnidad);
                
		if (iIdTipoDocumento == null || idUnidad == null) {
			log.error("Parametros recibidos incorrectamente");

			return null;
		}

		
		Numeracion numeracion = null;

		if (log.isDebugEnabled()) {
			log.debug("iIdTipoDocumento [" + iIdTipoDocumento + "] idUnidad [" + idUnidad + "]");
		}

		if (idUnidad == null) {
			log.info("No se encontro firmante con idFirmante [" + idUnidad + "]");
			return null;
		}

               if ((numeracion = srvNumeracion.findByIdbyUnidad(unidad, iIdTipoDocumento)) == null) {
			return null;
		}

               return new NumeracionJSON(numeracion.getTiponumeracion(), numeracion.getFirmante(), numeracion.getDestinatario());
	}

	@SMDMethod
	public String saveFavorito(String idContacto) {
                log.debug("-> [Action] DojoAction - saveFavorito():String ");

		if (idContacto == null) {
			log.error("Parametros recibidos incorrectamente");

			return null;
		}

		if (log.isDebugEnabled()) {
			log.debug("idContacto [" + idContacto + "]");
		}

                Character tipoContacto = Constantes.FAVORITO_TIPOCONTACTO_USUARIO;

                if (idContacto.startsWith("LISTA_")) {
			idContacto = idContacto.split("LISTA_")[1];
			tipoContacto = Constantes.FAVORITO_TIPOCONTACTO_LISTA;
		}

                mapSession = ActionContext.getContext().getSession();
		Usuario usuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
                String[] parts = idContacto.split("-");
		Favorito existeFavorito = favoritoService.findObjectBy(usuario.getIdUsuarioPerfil(), Integer.valueOf(parts[1]), Integer.valueOf(parts[2]), Integer.valueOf(parts[0]), tipoContacto, Constantes.ESTADO_ACTIVO);

                if (existeFavorito != null) {
			log.info("Ya existe el contacto [" + existeFavorito.getContacto() + "] para el propietario [" + existeFavorito.getPropietario() + "]");

			return existeFavorito.getContacto().toString();
		}

                Favorito favorito = favoritoService.saveFavorito(usuario.getIdUsuarioPerfil(),Integer.valueOf(parts[1]), Integer.valueOf(parts[2]), Integer.valueOf(parts[0]), tipoContacto);
		return favorito.getContacto().toString();
	}

	@SMDMethod
	public ObjetoJSON SMDCompletarConcesionario(Integer idConcesionario) {
		log.debug("-> [Action] DojoAction - SMDCompletarConcesionario():ObjetoJSON ");

		ObjetoJSON json = new ObjetoJSON();
		Concesionario concesionario = concesionarioService.findByIdConcesionario(idConcesionario);
		List<Item> listaItems = new ArrayList<Item>();
		Item objItem = new Item();
		objItem.setDireccion((concesionario.getDireccion() == null) ? " - " : concesionario.getDireccion());
		objItem.setCorreo((concesionario.getCorreo() == null) ? " - " : concesionario.getCorreo());
		listaItems.add(objItem);
		json.setItems(listaItems);
		return json;
	}

        
        @SMDMethod
	public ObjetoJSON eliminarAdjunto(String idAdjunto) {
		log.debug("-> [Action] DojoAction - eliminarAdjunto():ObjetoJSON ");
                ObjetoJSON objJSON = new ObjetoJSON();
                mapSession = ActionContext.getContext().getSession();
                List<Item> items = (List<Item>)mapSession.get("AdjuntosDocumento");
                
                for(int i=0;i<items.size();i++){
                    Item item = items.get(i);
                    if (item.getIdId().equals(idAdjunto)){
                        items.remove(i);
                        break;
                    }
                }
                
		objJSON.setItems(items);
                mapSession.put("AdjuntosDocumento", items);
		return objJSON;
        }
        
        @SMDMethod
        public ObjetoJSON leerAdjuntos(String codDocumento){
         ObjetoJSON objJSON = new ObjetoJSON();   
         mapSession = ActionContext.getContext().getSession();
         
         if (codDocumento==null || codDocumento.equals("")){
            mapSession.put("AdjuntosDocumento", new ArrayList<Item>());
         }else{
               List<Item> listaItems = new ArrayList<Item>();
               List<DocumentoAdjunto> ld= documentoAdjuntoService.findByListDocumentoAdjunto(new Integer(codDocumento));
               Item item = null;          
          
               for(int i=0;i<ld.size();i++){
                  item = new Item();
                  item.setIdId(ld.get(i).getIdDocumentoAdjunto().toString());
                  item.setTipo(ld.get(i).getCodTipoAdj());
                  item.setTipoAdjunto(ld.get(i).getCopOrig());
                  item.setNro(ld.get(i).getNroAdj().toString());
                  
                  List<Parametro> lista = parametroService.findByTipo(Constantes.PARAMETRO_ADJUNTO_COPIA_ORIGINAL);
                  for(int j=0;j<lista.size();j++){
                    Parametro p = lista.get(j);
                    if (p.getValor().equals(ld.get(i).getCopOrig())){
                      item.setDesOrigCop(p.getDescripcion());
                      break;
                    }   
                  }
        
                   List<Parametro> listaTipos = parametroService.findByTipo(Constantes.PARAMETRO_TIPOS_DE_ADJUNTOS_MP);
                   for(int k=0;k<listaTipos.size();k++){
                     Parametro p = listaTipos.get(k);
                     if (p.getValor().equals(ld.get(i).getCodTipoAdj())){
                       item.setDesTipo(p.getDescripcion());
                       break;
                     }   
                   }
                  
                  
                  listaItems.add(item);
            }
             
            mapSession.put("AdjuntosDocumento", listaItems); 
            objJSON.setItems(listaItems);
        }
        
           return objJSON;
        }

        
        @SMDMethod
	public ObjetoJSON agregarAdjunto(String tipo, String copia, String nro) {
		log.debug("-> [Action] DojoAction - agregarAdjunto():ObjetoJSON ");

		ObjetoJSON objJSON = new ObjetoJSON();
                mapSession = ActionContext.getContext().getSession(); 
                List<Item> items = (List<Item>)mapSession.get("AdjuntosDocumento");
   
                Item item = new Item();
                item.setIdId(String.valueOf(UUID.randomUUID().toString()));
                item.setTipo(tipo);
                item.setTipoAdjunto(copia);
                item.setNro(nro);
             
                List<Parametro> lista = parametroService.findByTipo(Constantes.PARAMETRO_ADJUNTO_COPIA_ORIGINAL);
                for(int i=0;i<lista.size();i++){
                  Parametro p = lista.get(i);
                  if (p.getValor().equals(copia)){
                     item.setDesOrigCop(p.getDescripcion());
                  }   
                }
        
                List<Parametro> listaTipos = parametroService.findByTipo(Constantes.PARAMETRO_TIPOS_DE_ADJUNTOS_MP);
                for(int i=0;i<listaTipos.size();i++){
                   Parametro p = listaTipos.get(i);
                   if (p.getValor().equals(tipo)){
                      item.setDesTipo(p.getDescripcion());
                   }   
                }
                 
                items.add(item);
		objJSON.setItems(items);
                mapSession.put("AdjuntosDocumento", items);
		return objJSON;
	}

	@SMDMethod
	public ObjetoJSON setAttributeInSession(String key, String o) {
		log.debug("-> [Action] DojoAction - setAttributeInSession():ObjetoJSON ");

		ActionContext.getContext().getSession().put(key, o);
		return new ObjetoJSON();
	}

	@SMDMethod
	public ObjetoJSON getSubmotivosSTOR(Integer idExpediente) {
		log.debug("-> [Action] DojoAction - getSubmotivosSTOR():ObjetoJSON ");

		ObjetoJSON objJSON = new ObjetoJSON();
		objJSON.setItems(storService.getItemSubmotivos(idExpediente));
		return objJSON;
	}

	@SMDMethod
	public ObjetoJSON getSuministrosSTOR(Integer idExpediente) {
		log.debug("-> [Action] DojoAction - getSuministrosSTOR():ObjetoJSON ");

		ObjetoJSON objJSON = new ObjetoJSON();
		objJSON.setItems(storService.getItemSuministros(idExpediente));
               
		return objJSON;
	}

	@SMDMethod
	public ObjetoJSON filtrarBandeja(String sTipoGrid, BusquedaAvanzada objFiltro) {
		//TODO recien empiezo a hacer este metodo
		log.debug("-> [Action] DojoAction - filtrarBandeja():ObjetoJSON ");

		if (sTipoGrid == null) {
			log.error("No se recibio un tipo de grid correcto");
			return null;
		}

		ObjetoJSON objJSON = new ObjetoJSON();
		Usuario objUsuario = null;

		mapSession = ActionContext.getContext().getSession();
		objUsuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);

		if (objUsuario == null) {
			log.error("Expiro la sesion");
			return null;
		}

		log.info("Tipo de grid solicitado [" + sTipoGrid + "]");

		List lstItem = gridColumnaXUsuarioService.getItems(sTipoGrid, objUsuario, objFiltro);


		if (sTipoGrid.equalsIgnoreCase(Constantes.TIPO_GRID_DOCUMENTO_RECIBIDO)) {
			Comparator<ItemUF> cmp = new Comparator<ItemUF>(){
				 public int compare(ItemUF f1, ItemUF f2)
		            {
		                return f1.getFechaAccion().compareTo(f2.getFechaAccion());
		            }
			};

			Collections.sort(lstItem, Collections.reverseOrder(cmp));
		}

		if (sTipoGrid.equalsIgnoreCase(Constantes.TIPO_GRID_INFORMATIVO)) {
			Comparator<Item> cmp = new Comparator<Item>(){
				 public int compare(Item f1, Item f2)
		            {
		                return f1.getFecharecepcion().compareTo(f2.getFecharecepcion());
		            }
			};

			Collections.sort(lstItem, Collections.reverseOrder(cmp));
		}

		objJSON.setItems(lstItem);

                mapSession.put("listRecibidos", lstItem);
		objJSON.setItems(lstItem);
		objJSON.setStructure(gridColumnaXUsuarioService.getEstructura(sTipoGrid, objUsuario));

		return objJSON;
	}

	@SMDMethod
	public ObjetoJSON getArchivosXUsuario(Integer idDocumento) {
		log.debug("-> [Action] DojoAction - getArchivosXUsuario():ObjetoJSON ");

		ObjetoJSON objJSON = new ObjetoJSON();
		Usuario objUsuario = null;

		mapSession = ActionContext.getContext().getSession();
		objUsuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);

               if (objUsuario == null) {
			log.error("Expiro la sesion");
			return null;
		}

		// JBENGOA INICIO
		Documento documento_ = documentoService.findByIdDocumento(idDocumento);

		if (documento_.getDocumentoreferencia()!=null)
			objJSON.setItems(archivoService.buscarItemArchivoXAutor(objUsuario, documento_.getDocumentoreferencia()));
		else
			objJSON.setItems(archivoService.buscarItemArchivoXAutor(objUsuario, idDocumento));
	    // JBENGOA FIN

		return objJSON;
	}
    @SMDMethod
    public ObjetoJSON usuariosxUniOrg(Integer idun) throws Exception {
        log.debug("-> [Action] DojoAction - usuariosxUniOrg():ObjetoJSON ");        
        
        ObjetoJSON d=new ObjetoJSON();
        List<Map<String, String>> s = new ArrayList<Map<String, String>>();
        s=usuarioService.findUsuariosxUniOrg(idun.intValue());
        List<MennuItem> r=new ArrayList<MennuItem>();
        for (Map<String, String> map : s) {
           MennuItem m=new MennuItem();
           m.setId(map.get("id"));
           m.setNombre(map.get("nombres"));
           r.add(m);
        }
        d.setItems(r);
        return d;
    }
	public List<Item> getLstDocumentoGrid() {
		return lstDocumentoGrid;
	}
        
        //	public void setSession(Map mapSession){
	//	this.setMapSession(mapSession);
	//}
        
       // public void setSession(Map mapSession){
	//	this.setMapSession(mapSession);
//	}

	public void setLstDocumentoGrid(List<Item> lstDocumentoGrid) {
		this.lstDocumentoGrid = lstDocumentoGrid;
	}

	public Map<String, Object> getMapSession() {
		return mapSession;
	}

	public void setMapSession(Map<String, Object> mapSession) {
		this.mapSession = mapSession;
	}

	public void setRolService(RolService rolService) {
		this.rolService = rolService;
	}

	public void setDocumentoService(DocumentoService documentoService) {
		this.documentoService = documentoService;
	}

	public void setGridColumnaXUsuarioService(GridcolumnaxusuarioService gridColumnaXUsuarioService) {
		this.gridColumnaXUsuarioService = gridColumnaXUsuarioService;
	}

	public void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	public void setExpedienteService(ExpedienteService expedienteService) {
		this.expedienteService = expedienteService;
	}

	public void setTipoIdentificacionService(TipoidentificacionService tipoIdentificacionService) {
		this.tipoIdentificacionService = tipoIdentificacionService;
	}

	public void setClienteService(ClienteService clienteService) {
		this.clienteService = clienteService;
	}

	public void setParametroService(ParametroService parametroService) {
		this.parametroService = parametroService;
	}

	public void setCarpetaBusquedaService(CarpetabusquedaService carpetaBusquedaService) {
		this.carpetaBusquedaService = carpetaBusquedaService;
	}

	public void setNotificacionService(NotificacionService notificacionService) {
		this.notificacionService = notificacionService;
	}

	public void setSupervisorService(SupervisorService supervisorService) {
		this.supervisorService = supervisorService;
	}

	public void setDistritoDao(DistritoDAO distritoDao) {
		this.distritoDao = distritoDao;
	}

	public void setReemplazoDao(ReemplazoDAO reemplazoDao) {
		this.reemplazoDao = reemplazoDao;
	}

	public PlantillaService getSrvPlantilla() {
		return srvPlantilla;
	}

	public void setSrvPlantilla(PlantillaService srvPlantilla) {
		this.srvPlantilla = srvPlantilla;
	}

	public void setSrvCampo(CampoService srvCampo) {
		this.srvCampo = srvCampo;
	}

	public void setSrvNumeracion(NumeracionService srvNumeracion) {
		this.srvNumeracion = srvNumeracion;
	}

	public void setSrvUnidad(UnidadService srvUnidad) {
		this.srvUnidad = srvUnidad;
	}

	public void setSrvTipoDocumento(TipodocumentoService srvTipoDocumento) {
		this.srvTipoDocumento = srvTipoDocumento;
	}

	public void setPerfilDao(PerfilDAO perfilDao) {
		this.perfilDao = perfilDao;
	}

	public ProcesoService getSrvProceso() {
		return srvProceso;
	}

	public void setSrvProceso(ProcesoService srvProceso) {
		this.srvProceso = srvProceso;
	}

	public ItemService getSrvItem() {
		return srvItem;
	}

	public void setSrvItem(ItemService srvItem) {
		this.srvItem = srvItem;
	}

	public DocumentoxclienteService getDocumentoxclienteService() {
		return documentoxclienteService;
	}

	public void setDocumentoxclienteService(DocumentoxclienteService documentoxclienteService) {
		this.documentoxclienteService = documentoxclienteService;
	}

	public MensajeriaService getSrvMensajeria() {
		return srvMensajeria;
	}

	public void setSrvMensajeria(MensajeriaService srvMensajeria) {
		this.srvMensajeria = srvMensajeria;
	}

	public FavoritoService getFavoritoService() {
		return favoritoService;
	}

	public void setFavoritoService(FavoritoService favoritoService) {
		this.favoritoService = favoritoService;
	}

	public ConcesionarioService getConcesionarioService() {
		return concesionarioService;
	}

	public void setConcesionarioService(ConcesionarioService concesionarioService) {
		this.concesionarioService = concesionarioService;
	}

	public ExpedientestorService getStorService() {
		return storService;
	}

	public void setStorService(ExpedientestorService storService) {
		this.storService = storService;
	}

	public void setArchivoService(ArchivoService archivoService) {
		this.archivoService = archivoService;
	}

	public AlfrescoWSService getAlfrescoWebServiceClient() {
		return alfrescoWebServiceClient;
	}

	public void setAlfrescoWebServiceClient(AlfrescoWSService alfrescoWebServiceClient) {
		this.alfrescoWebServiceClient = alfrescoWebServiceClient;
	}

	public Integer getIdCarpetaBusqueda() {
		return idCarpetaBusqueda;
	}

	public void setIdCarpetaBusqueda(Integer idCarpetaBusqueda) {
		this.idCarpetaBusqueda = idCarpetaBusqueda;
	}

	public CarpetaBusqueda getCarpetaBusqueda() {
		return carpetaBusqueda;
	}

	public void setCarpetaBusqueda(CarpetaBusqueda carpetaBusqueda) {
		this.carpetaBusqueda = carpetaBusqueda;
	}

	public Character getAgrupacion() {
		return agrupacion;
	}

	public void setAgrupacion(Character agrupacion) {
		this.agrupacion = agrupacion;
	}

	public Integer getIdTipoGrid() {
		return idTipoGrid;
	}

	public void setIdTipoGrid(Integer idTipoGrid) {
		this.idTipoGrid = idTipoGrid;
	}

	public String getIdDoc() {
		return idDoc;
	}

	public void setIdDoc(String idDoc) {
		this.idDoc = idDoc;
	}

	public String getRutaAlfresco() {
		return rutaAlfresco;
	}

	public void setRutaAlfresco(String rutaAlfresco) {
		this.rutaAlfresco = rutaAlfresco;
	}

	public String getContDocumentosRecibidos() {
		return contDocumentosRecibidos;
	}

	public void setContDocumentosRecibidos(String contDocumentosRecibidos) {
		this.contDocumentosRecibidos = contDocumentosRecibidos;
	}

	public List<FilaBandejaUF> getListDocumentosRecibidos() {
		log.debug("-> [Action] DojoAction - getListDocumentosRecibidos():String ");
		Usuario objUsuario = null;
		mapSession = ActionContext.getContext().getSession();
		objUsuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);

		if (objUsuario == null) {
			log.error("Expiro la sesion");
			return null;
		}
		listDocumentosRecibidos = documentoService.getContDocUsuarioFinal(objUsuario, true,null);
		return listDocumentosRecibidos;
	}

	public void setListDocumentosRecibidos(
			List<FilaBandejaUF> listDocumentosRecibidos) {
		this.listDocumentosRecibidos = listDocumentosRecibidos;
	}

	public DocumentoDAO getDocumentoDao() {
		return documentoDao;
	}

	public void setDocumentoDao(DocumentoDAO documentoDao) {
		this.documentoDao = documentoDao;
	}

	public Documento getObjDocumento() {
		return objDocumento;
	}

	public void setObjDocumento(Documento objDocumento) {
		this.objDocumento = objDocumento;
	}

	public Integer getiIdUpload() {
		return iIdUpload;
	}

	public void setiIdUpload(Integer iIdUpload) {
		this.iIdUpload = iIdUpload;
	}

	public String getArchivoPrincipal() {
		return archivoPrincipal;
	}

	public void setArchivoPrincipal(String archivoPrincipal) {
		this.archivoPrincipal = archivoPrincipal;
	}

	public Archivo getArchivo() {
		return archivo;
	}

	public void setArchivo(Archivo archivo) {
		this.archivo = archivo;
	}

	public String getPDF() {
		return PDF;
	}

	public void setPDF(String pDF) {
		PDF = pDF;
	}

	public Boolean getParaAprobar() {
		return paraAprobar;
	}

	public void setParaAprobar(Boolean paraAprobar) {
		this.paraAprobar = paraAprobar;
	}


	public String getSTipoDerivacion() {
		return sTipoDerivacion;
	}

	public void setSTipoDerivacion(String sTipoDerivacion) {
		this.sTipoDerivacion = sTipoDerivacion;
	}

	public String getSOpcion() {
		return sOpcion;
	}

	public void setSOpcion(String sOpcion) {
		this.sOpcion = sOpcion;
	}

	public Integer getIddestinatario() {
		return iddestinatario;
	}

	public void setIddestinatario(Integer iddestinatario) {
		this.iddestinatario = iddestinatario;
	}

	public String getStrAcc() {
		return strAcc;
	}

	public void setStrAcc(String strAcc) {
		this.strAcc = strAcc;
	}

	public Boolean getProvieneDeMail() {
		return provieneDeMail;
	}

	public void setProvieneDeMail(Boolean provieneDeMail) {
		this.provieneDeMail = provieneDeMail;
	}

	public AccionService getAccionService() {
		return accionService;
	}

	public void setAccionService(AccionService accionService) {
		this.accionService = accionService;
	}

	public ProcesoService getProcesoService() {
		return procesoService;
	}

	public void setProcesoService(ProcesoService procesoService) {
		this.procesoService = procesoService;
	}

	public FechaLimite getFechaLimite() {
		return fechaLimite;
	}

	public void setFechaLimite(FechaLimite fechaLimite) {
		this.fechaLimite = fechaLimite;
	}

	@SMDMethod
	public ObjetoJSON getArchivosXUsuarioPDF(Integer idDocumento) {
		log.debug("-> [Action] DojoAction - getArchivosXUsuarioPDF():ObjetoJSON ");

		ObjetoJSON objJSON = new ObjetoJSON();
		Usuario objUsuario = null;

		mapSession = ActionContext.getContext().getSession();
		objUsuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);

		if (objUsuario == null) {
			log.error("Expiro la sesion");
			return null;
		}

		objJSON.setItems(archivoService.buscarItemArchivoXAutorPDF(objUsuario.getIdusuario(), idDocumento));

		return objJSON;
	}

	@SMDMethod
	public ObjetoJSON getDocumentosInformativos(BusquedaAvanzada objFiltro) {
		log.debug("-> [Action] DojoAction - getDocumentosRecibidos():void ");
		Usuario objUsuario = null;
		mapSession = ActionContext.getContext().getSession();
		objUsuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
		ObjetoJSON objJSON = new ObjetoJSON();
		objJSON.setIdentifier("id");
		objJSON.setLabel("name");
		objJSON.setItems(notificacionService.getDojoInformativoTree(objFiltro, objUsuario));
		return objJSON;
	}

	/*@SMDMethod
	public Nodo2 getJSon() {
		Nodo2 n = new Nodo2();
		n.setId(1);
		n.setName("primer nodo");
		return n;
	}

	@SMDMethod
	public SimpleNode getJSon2() {
		SimpleNodeLeaf n = new SimpleNodeLeaf();
		n.setId(1);
		n.setName("primer nodo");

		SimpleNodeLeaf n2 = new SimpleNodeLeaf();
		n2.setId(2);
		n2.setName("segundo nodo");

		List <SimpleNodeLeaf> list = new ArrayList <SimpleNodeLeaf>();
		list.add(n);
		list.add(n2);

		SimpleNode m = new SimpleNode();
		m.setId(10);
		m.setName("nodo mayor");
		m.setChildren(list);

		return m;

	}*/


	@SMDMethod
	public List<SimpleNode> getJSon3() {
		SimpleNodeLeaf n = new SimpleNodeLeaf();
		n.setId("1.1");
		//n.setName("primer nodo 1");
		n.setLabel("n 1.1");

		SimpleNodeLeaf n2 = new SimpleNodeLeaf();
		n2.setId("1.2");
		//n2.setName("segundo nodo 1");
		n2.setLabel("n 1.2");

		List <SimpleNodeLeaf> list = new ArrayList <SimpleNodeLeaf>();
		list.add(n);
		list.add(n2);

		SimpleNode m = new SimpleNode();
		m.setId("1");
		m.setLabel("n1");
	//	m.setName("nodo mayor 1");
		m.setChildren(list);

		//
		SimpleNodeLeaf n1 = new SimpleNodeLeaf();
		n1.setId("2.1");
		//n1.setName("primer nodo 2");
		n1.setLabel("n 2.1");

		SimpleNodeLeaf n12 = new SimpleNodeLeaf();
		n12.setId("2.2");
		//n12.setName("segundo nodo 2");
		n12.setLabel("n 2.2");

		List <SimpleNodeLeaf> list2 = new ArrayList <SimpleNodeLeaf>();
		list2.add(n1);
		list2.add(n12);

		SimpleNode m1 = new SimpleNode();
		m1.setId("2");
		m1.setLabel("n 2");
		//m1.setName("nodo mayor 2");
		m1.setChildren(list2);

		List<SimpleNode> nodo3 = new ArrayList<SimpleNode>();
		nodo3.add(m);
		nodo3.add(m1);

		return nodo3;

	}

	@SMDMethod
	public List<SimpleNode> getDocumentosRecibidosnew(BusquedaAvanzada objFiltro) {
		log.debug("-> [Action] DojoAction - getDocumentosRecibidosnew():List<SimpleNode> ");
		List<SimpleNode> nodo3 = new ArrayList<SimpleNode>();
		nodo3 = documentoService.getDojoDocumentoRecibidoTree(objFiltro);
		if(nodo3!= null){
			return nodo3;
		}else{
			return null;
		}


	}


	@SMDMethod
	public ObjetoJSON  getJSon4() {

		List<SimpleNode2> lstsn2 = new ArrayList<SimpleNode2>();


    List<ReferenciaArbol> lisRef = new ArrayList<ReferenciaArbol>() ;

		ReferenciaArbol ref1 = new ReferenciaArbol();
		ref1.set_reference("2");
		lisRef.add(ref1);

		ReferenciaArbol ref2 = new ReferenciaArbol();
		ref2.set_reference("3");
		lisRef.add(ref2);

		ReferenciaArbol ref3 = new ReferenciaArbol();
		ref3.set_reference("4");
		lisRef.add(ref3);

		SimpleNode2 sn2 = new SimpleNode2();
		sn2.setId("1");
		sn2.setTop(true);
		sn2.setName("NODO rrrrrrrrrrrrrrrrrrrrrrrrrrrrrr 1");
		sn2.setChildren(lisRef);

		lstsn2.add(sn2);

		SimpleNode2 n = new SimpleNode2();
		List<ReferenciaArbol> lisRef2 = new ArrayList<ReferenciaArbol>() ;
		n.setId("2");
		n.setName("primer nodo rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr1");
		n.setTop(false);
		n.setChildren(lisRef2);
		lstsn2.add(n);

		SimpleNode2 n2 = new SimpleNode2();
		List<ReferenciaArbol> lisRef3 = new ArrayList<ReferenciaArbol>() ;
		n2.setId("3");
		n2.setName("segundo nodorrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr 1");
		n2.setTop(false);
		n2.setChildren(lisRef3);
		lstsn2.add(n2);

		SimpleNode2 n3 = new SimpleNode2();
		List<ReferenciaArbol> lisRef4 = new ArrayList<ReferenciaArbol>() ;
		n3.setId("4");
		n3.setTop(false);
		n3.setName("segundo nodo 1");
		n3.setChildren(lisRef4);
		lstsn2.add(n3);



		List<ReferenciaArbol> lisRef1 = new ArrayList<ReferenciaArbol>() ;

		ReferenciaArbol ref11 = new ReferenciaArbol();
		ref11.set_reference("5");
		lisRef1.add(ref11);


		SimpleNode2 sn3 = new SimpleNode2();
		sn3.setId("10");
		sn3.setTop(true);
		sn3.setName("NODO                           2");
		sn3.setChildren(lisRef1);

		lstsn2.add(sn3);


		SimpleNode2 n31 = new SimpleNode2();
		List<ReferenciaArbol> lisRef6 = new ArrayList<ReferenciaArbol>() ;
		n31.setId("5");
		n31.setName("primer nodo 5");
		n31.setTop(false);
		n31.setChildren(lisRef6);
		lstsn2.add(n31);




		ObjetoJSON objJSON = new ObjetoJSON();
		objJSON.setIdentifier("id");
		objJSON.setLabel("name");
		objJSON.setItems(lstsn2);
		return objJSON;

	}

	@SMDMethod
	public String ContRecibidos(){
		String result = "ERROR";
		mapSession = ActionContext.getContext().getSession();
		Usuario usuarioSesion = (Usuario) getMapSession().get(Constantes.SESSION_USUARIO);
		usuarioSesion = usuarioService.findByIdUsuario(usuarioSesion.getIdusuario());
		List<FilaBandejaUF> listDocumentosRecibidos = new ArrayList<FilaBandejaUF>();
		listDocumentosRecibidos = documentoService.getContDocUsuarioFinal(usuarioSesion, true,null);
				if(listDocumentosRecibidos != null){
					return result =""+listDocumentosRecibidos.size();
		        }else{
		        	return result ="0";
		        }
	}

	@SMDMethod
	public String getUltimoDocumentoRecibido(){
		String result = "0";
		mapSession = ActionContext.getContext().getSession();
		Usuario usuarioSesion = (Usuario) getMapSession().get(Constantes.SESSION_USUARIO);
		usuarioSesion = usuarioService.findByIdUsuario(usuarioSesion.getIdusuario());
		List<FilaBandejaUF> listDocumentosRecibidos = new ArrayList<FilaBandejaUF>();
		listDocumentosRecibidos = documentoService.getContDocUsuarioFinal(usuarioSesion, true,null);
				if(listDocumentosRecibidos != null){
					for (FilaBandejaUF filaBandejaUF : listDocumentosRecibidos) {
		            	result = ""+ filaBandejaUF.getId(); break;
					}
					return result;

		        }else{
		        	return result ;
		        }
	}

	public ManejoDeEmailService getMailService() {
		return mailService;
	}

	public void setMailService(ManejoDeEmailService mailService) {
		this.mailService = mailService;
	}

	@SMDMethod
	public String verificarExisteAlerta(){
		mapSession = ActionContext.getContext().getSession();
		Usuario objUsuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
		List<Alerta> listAlert = seguimientoService.getListaAlerta(objUsuario.getIdusuario(), usuarioService.findByIdUsuario(objUsuario.getIdusuario()).getUnidad().getIdunidad(), null);
		getMapSession().put("listAlert",listAlert);

		if (listAlert.size()==0)
		  return "0";
	    return "1";
	}

	@SMDMethod
	public String validarArchivosExpediente(String idExpediente_){

		mapSession = ActionContext.getContext().getSession();
		Map<String, List<ArchivoTemporal>> mapUpload = (Map<String, List<ArchivoTemporal>>) mapSession.get(Constantes.SESSION_UPLOAD_LIST);

		if (mapUpload != null && idExpediente_!=null && !idExpediente_.trim().equals("")) {
			List<Archivo> archivosEnDB = archivoService.findLstByExpediente(new Integer(idExpediente_));
			List<ArchivoTemporal> l = mapUpload.get("upload2");
			Iterator<ArchivoTemporal> i = l.iterator();

                        if (archivosEnDB!=null && archivosEnDB.size()>0){
				while (i.hasNext()) {
					ArchivoTemporal arch = i.next();
				
					for(int k=0;k<archivosEnDB.size();k++){
						if (archivosEnDB.get(k).getEstado()=='A'){
							if (archivosEnDB.get(k).getRutaAlfresco().toUpperCase().indexOf(arch.getSNombre().toUpperCase().trim())>0){
								return "El nombre del archivo adjunto " + arch.getSNombre() + " ya existe en el expediente.";
							}
						}
					}
				}
                        }
		}

		return "";
	}

	@SMDMethod
	public String verificarPermiso(Integer idDocumento){
		mapSession = ActionContext.getContext().getSession();
                Map<String,List<org.ositran.utils.ArchivoTemporal>> upload=(Map<String,List<org.ositran.utils.ArchivoTemporal>>) mapSession.get(Constantes.SESSION_UPLOAD_LIST);
		
                List<ArchivoTemporal> lstArchivoTemporal = null;

		if (upload != null) {
		   lstArchivoTemporal = upload.get("upload2");
		}

		if (lstArchivoTemporal != null && lstArchivoTemporal.size() > 0){
                   Usuario objUsuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
		   Documento documento_ = documentoService.findByIdDocumento(idDocumento);

                   for(int i=0;i<lstArchivoTemporal.size();i++){
                      String ruta = documento_.getID_CODIGO().toString() + "_" + lstArchivoTemporal.get(i).getSNombre();
                      List<Archivo> lista = archivoService.buscarArchivosPorRutaDocumento(documento_.getDocumentoreferencia()==null?documento_.getIdDocumento():documento_.getDocumentoreferencia(), ruta.toLowerCase());
                     
                      for(int j=0;j<lista.size();j++){
                        if (lista.get(j).getAutor().getIdusuario().toString().equals(objUsuario.getIdUsuarioPerfil().toString()) &&
                            lista.get(i).getUnidadAutor().toString().equals(objUsuario.getIdUnidadPerfil().toString())){
                            return "";
                        }else{
                            return lstArchivoTemporal.get(i).getSNombre(); 
                        }   
                      }
                   }  
                }     
                  
              return "";    
	}

	@SMDMethod
	public String tienePrincipal(Integer iIdDocAA){
		String result = "";
		objDocumento = documentoService.findByIdDocumento(iIdDocAA);
		if(objDocumento.getDocumentoreferencia() != null){
			archivo = archivoService.getArchivoPrincipalPorDocumento(objDocumento.getDocumentoreferencia().intValue());
		}else {
			archivo = archivoService.getArchivoPrincipalPorDocumento(iIdDocAA);
		}
		if(archivo == null){
			result ="N";
		}else{
			result ="S";
		}
		return result;
	}

	@SMDMethod
	public String verificarFilesAdjuntar(String idDoc, String nameFile) {
		listArchivo = archivoService.getArchivoListPorDocumentoIG(Integer.valueOf(idDoc));
        String respuesta = "false";

        if (listArchivo!=null){
			for(int i=0;i<listArchivo.size();i++){
		       if (listArchivo.get(i).getNombreReal().trim().toUpperCase().equals(nameFile.trim().toUpperCase())){
	        	   respuesta = "true";
	        	   break;
	           }
			}
        }

		return respuesta;
	}

	public LogOperacionService getLogOperacionService() {
		return logOperacionService;
	}

	public void setLogOperacionService(LogOperacionService logOperacionService) {
		this.logOperacionService = logOperacionService;
	}

	public TrazabilidaddocumentoService getTrazabilidadDocumentoService() {
		return trazabilidadDocumentoService;
	}

	public void setTrazabilidadDocumentoService(
			TrazabilidaddocumentoService trazabilidadDocumentoService) {
		this.trazabilidadDocumentoService = trazabilidadDocumentoService;
	}

	public SeguimientoService getSeguimientoService() {
		return seguimientoService;
	}

	public void setSeguimientoService(SeguimientoService seguimientoService) {
		this.seguimientoService = seguimientoService;
	}
        
         public DocumentoAdjuntoService getDocumentoAdjuntoService() {
           return documentoAdjuntoService;
        }

        public void setDocumentoAdjuntoService(DocumentoAdjuntoService documentoAdjuntoService) {
            this.documentoAdjuntoService = documentoAdjuntoService;
        }

}