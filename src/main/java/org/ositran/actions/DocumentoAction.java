/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.actions;

import gob.ositran.siged.config.SigedProperties;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpUtils;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.ositran.common.alfresco.AuthThreadLocalHolder;
import org.ositran.pojos.jasper.CargoReporte;
import org.ositran.services.AccionService;
import org.ositran.services.ArchivoService;
import org.ositran.services.ClienteService;
import org.ositran.services.ConcesionarioService;
import org.ositran.services.ControlDeCalidadService;
import org.ositran.services.DocumentoEnviadoService;
import org.ositran.services.DocumentoReferenciaService;
import org.ositran.services.DocumentoService;
import org.ositran.services.EtapaService;
import org.ositran.services.ExpedienteService;
import org.ositran.services.ExpedientestorService;
import org.ositran.services.GestionDocumentos;
import org.ositran.services.ListaService;
import org.ositran.services.LogOperacionService;
import org.ositran.services.ManejoDeEmailService;
import org.ositran.services.NotificacionService;
import org.ositran.services.NotificacionxEnumerarService;
import org.ositran.services.ParametroService;
import org.ositran.services.PlantillaService;
import org.ositran.services.ProcesoService;
import org.ositran.services.ProveidoService;
import org.ositran.services.ResolucionjaruService;
import org.ositran.services.RolService;
import org.ositran.services.SeguimientoService;
import org.ositran.services.SeguimientoXUsuarioService;
import org.ositran.services.TipodocumentoService;
import org.ositran.services.TipoidentificacionService;
import org.ositran.services.TrazabilidadapoyoService;
import org.ositran.services.TrazabilidadcopiaService;
import org.ositran.services.TrazabilidaddocumentoService;
import org.ositran.services.UsuarioService;
import org.ositran.siged.service.AlfrescoWSService;
import org.ositran.utils.ArchivoTemporal;
import org.ositran.utils.Constantes;
import org.ositran.utils.DestinatarioList;
import org.ositran.utils.DocumentoDetail;
import org.ositran.utils.DocumentoTemporal;
import org.ositran.utils.ExpedienteSearch;
import org.ositran.utils.ExpedienteTree;
import org.ositran.utils.FechaLimite;
import org.ositran.utils.StringUtil;
import org.ositran.utils.TrazabilidadEnlace;
import org.ositran.utils.UtilEncrip;
import org.ositran.utils.UtilOsinerg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.btg.ositran.siged.domain.Accion;
import com.btg.ositran.siged.domain.DocumentoPendiente;
import com.btg.ositran.siged.domain.Alerta;
import com.btg.ositran.siged.domain.Parametro;
import com.btg.ositran.siged.domain.Archivo;
import com.btg.ositran.siged.domain.TipoLegajoUnidad;
import com.btg.ositran.siged.domain.DocumentoAdjunto;
import com.btg.ositran.siged.domain.ArchivoPendiente;
import com.btg.ositran.siged.domain.Cliente;
import com.btg.ositran.siged.domain.Concesionario;
import com.btg.ositran.siged.domain.Documento;
import com.btg.ositran.siged.domain.DocumentoAnulado;
import com.btg.ositran.siged.domain.DocumentoDerivacion;
import com.btg.ositran.siged.domain.DocumentoAtendido;
import com.btg.ositran.siged.domain.DocumentoReferencia;
import com.btg.ositran.siged.domain.DocumentoReunion;
import com.btg.ositran.siged.domain.Documentoenviado;
import com.btg.ositran.siged.domain.Documentofedateado;
import com.btg.ositran.siged.domain.Documentoxexpediente;
import com.btg.ositran.siged.domain.Expediente;
import com.btg.ositran.siged.domain.Expedientestor;
import com.btg.ositran.siged.domain.Favorito;
import com.btg.ositran.siged.domain.IotdtmDocExterno;
import com.btg.ositran.siged.domain.Legajo;
import com.btg.ositran.siged.domain.LegajoDocumento;
import com.btg.ositran.siged.domain.LogOperacion;
import com.btg.ositran.siged.domain.Notificacion;
import com.btg.ositran.siged.domain.Proceso;
import com.btg.ositran.siged.domain.Proveido;
import com.btg.ositran.siged.domain.Resolucionjaru;
import com.btg.ositran.siged.domain.Rol;
import com.btg.ositran.siged.domain.SeguimientoXUsuario;
import com.btg.ositran.siged.domain.Serie;
import com.btg.ositran.siged.domain.Submotivo;
import com.btg.ositran.siged.domain.Suministro;
import com.btg.ositran.siged.domain.Tipoidentificacion;
import com.btg.ositran.siged.domain.Trazabilidadapoyo;
import com.btg.ositran.siged.domain.Trazabilidadcopia;
import com.btg.ositran.siged.domain.Trazabilidaddocumento;
import com.btg.ositran.siged.domain.Unidad;
import com.btg.ositran.siged.domain.Usuario;
import com.btg.ositran.siged.domain.UsuarioDerivacion;
import com.btg.ositran.siged.domain.Usuarioxunidadxfuncion;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import java.math.BigDecimal;
import org.ositran.daos.DocumentoAdjuntoDAO;
import org.ositran.daos.DocumentoAnuladoDAO;
import org.ositran.daos.DocumentoAtendidoDAO;
import org.ositran.daos.DocumentoDerivacionDAO;
import org.ositran.daos.DocumentoExternoVirtualDAO;
import org.ositran.daos.DocumentoPendienteDAO;
import org.ositran.daos.DocumentoReunionDAO;
import org.ositran.daos.ProveidoDAO;
import org.ositran.daos.SeguimientoXFirmaDAO;
import org.ositran.daos.TipoLegajoUnidadDAO;
import org.ositran.daos.UnidadDAO;
import org.ositran.services.FavoritoService;
import org.ositran.services.FuncionService;
import org.ositran.services.LegajoDocumentoService;
import org.ositran.services.LegajoService;
import org.ositran.daos.FirmaArchivoDAO;
import org.ositran.dojo.grid.ItemUF;import java.math.BigDecimal;
import org.ositran.daos.DocumentoAdjuntoDAO;
import org.ositran.daos.DocumentoAnuladoDAO;
import org.ositran.daos.DocumentoAtendidoDAO;
import org.ositran.daos.DocumentoDerivacionDAO;
import org.ositran.daos.DocumentoExternoVirtualDAO;
import org.ositran.daos.DocumentoPendienteDAO;
import org.ositran.daos.DocumentoReunionDAO;
import org.ositran.daos.ProveidoDAO;
import org.ositran.daos.SeguimientoXFirmaDAO;
import org.ositran.daos.TipoLegajoUnidadDAO;
import org.ositran.daos.UnidadDAO;
import org.ositran.services.FavoritoService;
import org.ositran.services.FuncionService;
import org.ositran.services.LegajoDocumentoService;
import org.ositran.services.LegajoService;
import org.ositran.daos.FirmaArchivoDAO;
import org.ositran.dojo.grid.ItemUF;

public class DocumentoAction {

	private final Logger log = LoggerFactory.getLogger(DocumentoAction.class);
	private File upload;
	private String idGrid;
	private char tipoBusqueda;
	private Boolean mostrarObservacion;
	private Boolean provieneDeMail;
	private Date fechaSesion;
	private Date fechaDerivacion;
	private Date fechaNotiReclamante;
	private Date fechaNotiConcesionario;
	private Date fecha;
        private String bandeja;
        private String fechaDetalle;
        private DocumentoReunionDAO documentoReunionDAO;
        private DocumentoAdjuntoDAO documentoAdjuntoDAO;
	private String executeAccion;
	private boolean destinatarioIgualRemitente;
        private boolean controlDevolver;
	private boolean paraAprobar;
	private boolean paraAlerta;
	private boolean enVentana;
	private boolean bBandeja;
	private boolean bResponsable;
	private boolean ownerproceso;
        private String strReferencia;
        private String strSeguimiento;
	private boolean documentoStor;
	private boolean mostrarEtapa;
	private boolean desactivado;
        private ProveidoDAO proveidoDAO;
        private UnidadDAO unidadDAO;
	private boolean unico;
        private String accion;
	private boolean flagMensaje;
	private boolean deMail;
	private boolean apelacion;
	private boolean agregar;
        private FuncionService funcionService;
	private String ordenarFechaLimite;
	private ExpedienteSearch objExpedienteSearch;
	private Concesionario objConcesionario = null;
	private GestionDocumentos gestionDocumentos;
	private Documento documento;
	private DocumentoDetail objDD;
	private Documentofedateado objDF;
        private DocumentoPendienteDAO documentoPendienteDAO;
        private DocumentoAtendidoDAO documentoAtendidoDAO;
        private DocumentoAnuladoDAO documentoAnuladoDAO;
        private List<UsuarioDerivacion> listaDerivacionPara;
        private FavoritoService favoritoService;
        private List<UsuarioDerivacion> listaDerivacionCC;
	private Documento objDocumento;
	private Boolean bBotonHabilitado;
	private Expediente objExpediente;
        private Legajo objLegajo;
	private Cliente objCliente;
	private ArchivoPendiente archivopendiente;
	private Expedientestor expedienteStor;
	private ExpedienteTree objExpedienteTree;
	private List<Tipoidentificacion> lstRadio;
	private List<DestinatarioList> lstDL;
	private List<Trazabilidadcopia> lstTrazabilidadCopia;
	private List<Documento> lstDocumento;
	private List<Archivo> archivos;
	private List<Submotivo> submotivos;
	private List<Suministro> suministros;
	private List<Proveido> proveidos;
        private List<Parametro> parametros;
        private List<DocumentoReferencia> documentosReferencia;
        private SeguimientoService seguimientoService;
	private DocumentoReferenciaService documentoReferenciaService;
	private Integer iIdDoc;
        private Integer iIdDocEmail;
	private Integer iIdPro;
	private Integer avisopermiso;
	private Integer iIdExp;
        private Integer iIdLegajo;
	private Integer idtrazabilidaddocumento;
	private Integer idTrazabilidadapoyo;
	private Integer idDocumentoSeleccionado;
	private Integer idtipoidentificacion;
	private Integer idtipodocumento;
	private String iddestinatario;
	private Integer idccdestinatario;
	private Integer idcorrentista;
	private Integer iddepartamento;
	private Integer idprovincia;
	private Integer iddistrito;
	private Integer idproceso;
        private Integer idserie;
	private Integer idconcesionario;
	private Integer idDocumento;
        private Integer idDocumentoLegajo;
	private Integer idmotivo;
	private Integer idsubmotivo;
	private Integer idsala;
	private Integer idanalista;
	private Integer idCumpleRequisito;
	private Integer idcliente;
	private Integer idAreaDerivada;
	private Integer iIdUpload;
	private Integer iIdNotificacion;
	private Integer idExpedienteNuevo;
	private Integer idPlantilla;
	private Integer idArchivoPendiente;
	private Integer iIdDocumento;
	private String sObservacionAnular;
	private Integer idenv;
        private Integer idpendientes;
        private Integer idatendidos;
        private Integer idanulados;
        private Integer idfirmados;
        private Integer idseguimientos;
	private Integer idDocPrincipalExpediente;
	private Integer[] storidsubmotivo;
	private Integer[] arrIdDoc;
	private String[] arrDocumentArea;
	private Integer[] idSubmotivos;
	private Integer[] idSuministros;
	private Integer[] arrIdArchivos;
	private String origenDocumento = null;
	private String claveUsuario;
	private String nombreCliente;
	private String observacionDigitalizador = "";
	private String origenDetalleDoc = null;
	private String strAcc;
	private String digmensaje;
	private String mensaje;
	private String sFechaDocumento;
	private String puedeRechazar = "";
	private String sNombres;
	private String departamento;
	private String provincia;
	private String distrito;
	private String proceso;
	private String correntista;
	private String concesionario;
	private String strUnidad;
	private String strResponsable;
	private String tipodocumento;
	private String tipoidentificacion;
	private String nroidentificacion;
	private String strRazonSocial;
	private String strDireccionPrincipal;
	private String ta;
	private String uploadFileName;
	private String fullFileName;
	private String nrodocumento;
	private String strRUC;
	private String strDireccion;
	private String strCorreoConcesionario;
	private String strRepresentanteLegal;
	private String strTelefonoCliente;
	private String strCorreoCliente;
	private String nmotivo;
	private String submotivo;
	private String sala;
	private Integer idDocumentoAnular;
	private String analista;
	private String cerrar;
	private String ocultar;
	private String sFromBandeja;
	private String sOpcion;
	private String remitenteObservacion;
	private String ultimaObservacion;
	private String otraObservacion;
	private String asuntoObservacion;
	private Character confidencialDocMod;
	private String accionObservacion;
	private String destinatarioObservacion;
	private String etapaActual;
	private String usuarioRegistro;
	private String codSala;
	private String codEstado;
	private String codVocal;
	private String codResultado;
	private String numeroResolucion;
	private String regresar;
	private String sTipoDerivacion;
	private String sTipoIdentificacion;
	private String sMontoReclamo;
	private String fechaEnTexto;
	private String urlIntalio;
	private String usuarioLogueado;
	private String idsDocumentoPorExSeleccionados;
	private String referencia;
	private String[] apoyo;
	private String[] strAcciones;
	private String[] strPrioridades;
	private String[] storsuministro;
        private String   strPrioridad;
        private String   strApoyo;
	private List<String> conCopia;
	private List<String> condestinatarios;
	private List<String> concopias;
	private Map<String, String> detallesDocumentoStor;
	private Map<String, Integer> mapSessionStor;
	private Map<String, Object> mapSession;
	private Map<String, String> url;
	private Map<Integer, Boolean> mapChkDocumento = new HashMap<Integer, Boolean>();
	private Map<Integer, String> mapTipoDocumento = new HashMap<Integer, String>();
	private Map<Integer, String> mapCumpleRequisito = new HashMap<Integer, String>();
	private CargoReporte cargo;
	private ListaService listaService;
	private DocumentoEnviadoService documentoenviadoService;
	private TrazabilidadapoyoService trazabilidadapoyoService;
	private AccionService accionService;
	private ArchivoService archivoService;
	private ClienteService clienteService;
	private ConcesionarioService concesionarioService;
	private DocumentoService documentoService;
	private EtapaService etapaService;
	private ExpedienteService expedienteService;
	private ExpedientestorService expedienteStorService;
	private NotificacionService notificacionService;
	private NotificacionxEnumerarService notificacionxEnumerarService;
	private PlantillaService plantillaService;
	private ProcesoService procesoService;
	private TipodocumentoService tipoDocumentoService;
	private TipoidentificacionService tipoIdentificacionService;
	private TrazabilidaddocumentoService trazabilidadDocumentoService;
	private UsuarioService usuarioService;
	private ParametroService parametroService;
	private FechaLimite fechaLimite;
	private ProveidoService proveidoService;
	private ControlDeCalidadService controlDeCalidadService;
	private ResolucionjaruService resolucionJaruService;
	private ManejoDeEmailService mailService;
	private TrazabilidadcopiaService trazabilidadcopiaService;
	private RolService rolService;
	private LogOperacionService logOperacionService;
        private List<TrazabilidadEnlace> trazabilidadenlace;
        private DocumentoDerivacionDAO documentoDerivacionDAO;
	private String idDoc;
	private String rutaAlfresco;
	private Archivo archivo;
	private String PDF;
	private String asuntoDD;
	private AlfrescoWSService alfrescoWebServiceClient;
	private List<Archivo> listArchivo;
	private DocumentoDetail objDDIG;
	private List<Usuario> listUsuario;
	private Boolean boVisor;
	private String nombrePDFprincipal;
	private Boolean bPDFprincipal;
        private String arrFileFirmar;
	private SeguimientoXUsuarioService seguimientoXUsuarioService;
        private SeguimientoXFirmaDAO seguimientoXFirmaDAO;
        private TipoLegajoUnidadDAO tipoLegajoUnidadDAO;
        private Integer proyecto;
        private char flagBusqueda;
        private char flagExpediente;
        private LegajoService legajoService;
        private LegajoDocumentoService legajoDocumentoService;
        private FirmaArchivoDAO firmaArchivoDAO;
        private DocumentoExternoVirtualDAO documentoExternoVirtualDAO;
        private Integer codigoVirtual;
        
        public String getStrSeguimiento() {
            return strSeguimiento;
        }

        public void setStrSeguimiento(String strSeguimiento) {
            this.strSeguimiento = strSeguimiento;
        }
        
        public String getFechaDetalle() {
            return fechaDetalle;
        }

        public void setFechaDetalle(String fechaDetalle) {
            this.fechaDetalle = fechaDetalle;
        }

        public Integer getCodigoVirtual() {
            return codigoVirtual;
        }

        public void setCodigoVirtual(Integer codigoVirtual) {
            this.codigoVirtual = codigoVirtual;
        }

        public DocumentoExternoVirtualDAO getDocumentoExternoVirtualDAO() {
            return documentoExternoVirtualDAO;
        }

        public void setDocumentoExternoVirtualDAO(DocumentoExternoVirtualDAO documentoExternoVirtualDAO) {
            this.documentoExternoVirtualDAO = documentoExternoVirtualDAO;
        }
        
         public String getBandeja() {
            return bandeja;
        }

        public void setBandeja(String bandeja) {
            this.bandeja = bandeja;
        }
        
        public boolean isControlDevolver() {
            return controlDevolver;
        }

        public void setControlDevolver(boolean controlDevolver) {
            this.controlDevolver = controlDevolver;
        }

        public FirmaArchivoDAO getFirmaArchivoDAO() {
            return firmaArchivoDAO;
        }

        public void setFirmaArchivoDAO(FirmaArchivoDAO firmaArchivoDAO) {
            this.firmaArchivoDAO = firmaArchivoDAO;
        }
        
        public Legajo getObjLegajo() {
            return objLegajo;
        }

        public void setObjLegajo(Legajo objLegajo) {
            this.objLegajo = objLegajo;
        }
        
        public Integer getIdDocumentoLegajo() {
            return idDocumentoLegajo;
        }

        public void setIdDocumentoLegajo(Integer idDocumentoLegajo) {
            this.idDocumentoLegajo = idDocumentoLegajo;
        }

        public LegajoDocumentoService getLegajoDocumentoService() {
            return legajoDocumentoService;
        }

        public void setLegajoDocumentoService(LegajoDocumentoService legajoDocumentoService) {
            this.legajoDocumentoService = legajoDocumentoService;
        }
        
        public Integer getiIdLegajo() {
            return iIdLegajo;
        }

        public void setiIdLegajo(Integer iIdLegajo) {
            this.iIdLegajo = iIdLegajo;
        }

        public LegajoService getLegajoService() {
            return legajoService;
        }

        public void setLegajoService(LegajoService legajoService) {
            this.legajoService = legajoService;
        }
        
        public TipoLegajoUnidadDAO getTipoLegajoUnidadDAO() {
            return tipoLegajoUnidadDAO;
        }

        public void setTipoLegajoUnidadDAO(TipoLegajoUnidadDAO tipoLegajoUnidadDAO) {
            this.tipoLegajoUnidadDAO = tipoLegajoUnidadDAO;
        }

        public char getFlagExpediente() {
            return flagExpediente;
        }

        public void setFlagExpediente(char flagExpediente) {
            this.flagExpediente = flagExpediente;
        }

        public char getFlagBusqueda() {
            return flagBusqueda;
        }

        public void setFlagBusqueda(char flagBusqueda) {
            this.flagBusqueda = flagBusqueda;
        }
        
        public ProveidoDAO getProveidoDAO() {
            return proveidoDAO;
        }

        public void setProveidoDAO(ProveidoDAO proveidoDAO) {
            this.proveidoDAO = proveidoDAO;
        }

        public Integer getProyecto() {
            return proyecto;
        }

        public void setProyecto(Integer proyecto) {
            this.proyecto = proyecto;
        }
        
        public String getArrFileFirmar() {
            return arrFileFirmar;
        }

        public void setArrFileFirmar(String arrFileFirmar) {
            this.arrFileFirmar = arrFileFirmar;
        }
        
        public FavoritoService getFavoritoService() {
          return favoritoService;
        }

        public void setFavoritoService(FavoritoService favoritoService) {
            this.favoritoService = favoritoService;
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
        
        public String getAccion() {
            return accion;
        }

        public void setAccion(String accion) {
            this.accion = accion;
        }

        public void setListaDerivacionCC(List<UsuarioDerivacion> listaDerivacionCC) {
            this.listaDerivacionCC = listaDerivacionCC;
        }
        
       
	public DocumentoReferenciaService getDocumentoReferenciaService() {
		return documentoReferenciaService;
	}

	public void setDocumentoReferenciaService(
			DocumentoReferenciaService documentoReferenciaService) {
		this.documentoReferenciaService = documentoReferenciaService;
	}
        
        public DocumentoAnuladoDAO getDocumentoAnuladoDAO() {
            return documentoAnuladoDAO;
        }

        public void setDocumentoAnuladoDAO(DocumentoAnuladoDAO documentoAnuladoDAO) {
            this.documentoAnuladoDAO = documentoAnuladoDAO;
        }

        public DocumentoAtendidoDAO getDocumentoAtendidoDAO() {
            return documentoAtendidoDAO;
        }

        public void setDocumentoAtendidoDAO(DocumentoAtendidoDAO documentoAtendidoDAO) {
            this.documentoAtendidoDAO = documentoAtendidoDAO;
        }

        
        public Integer getIdAreaDerivada() {
		return idAreaDerivada;
	}

	public Documentofedateado getObjDF() {
		return objDF;
	}

	public void setObjDF(Documentofedateado objDF) {
		this.objDF = objDF;
	}

	public void setIdAreaDerivada(Integer idAreaDerivada) {
		this.idAreaDerivada = idAreaDerivada;
	}

        
        public Integer getIdseguimientos() {
            return idseguimientos;
        }

        public void setIdseguimientos(Integer idseguimientos) {
            this.idseguimientos = idseguimientos;
        }

        public Integer getIdfirmados() {
            return idfirmados;
        }

        public void setIdfirmados(Integer idfirmados) {
            this.idfirmados = idfirmados;
        }
        
         
        public Integer getIdanulados() {
            return idanulados;
        }

        public void setIdanulados(Integer idanulados) {
            this.idanulados = idanulados;
        }
        
        public Integer getIdatendidos() {
            return idatendidos;
        }

        public void setIdatendidos(Integer idatendidos) {
            this.idatendidos = idatendidos;
        }
        
        public String getStrApoyo() {
            return strApoyo;
        }

        public void setStrApoyo(String strApoyo) {
            this.strApoyo = strApoyo;
        }
        
         public DocumentoDerivacionDAO getDocumentoDerivacionDAO() {
            return documentoDerivacionDAO;
        }

        public void setDocumentoDerivacionDAO(DocumentoDerivacionDAO documentoDerivacionDAO) {
            this.documentoDerivacionDAO = documentoDerivacionDAO;
        }


	public List<TrazabilidadEnlace> getTrazabilidadenlace() {
		return trazabilidadenlace;
	}

	public void setTrazabilidadenlace(List<TrazabilidadEnlace> trazabilidadenlace) {
		this.trazabilidadenlace = trazabilidadenlace;
	}

        public SeguimientoXFirmaDAO getSeguimientoXFirmaDAO() {
            return seguimientoXFirmaDAO;
        }

        public void setSeguimientoXFirmaDAO(SeguimientoXFirmaDAO seguimientoXFirmaDAO) {
            this.seguimientoXFirmaDAO = seguimientoXFirmaDAO;
        }

	public DocumentoAction(DocumentoService srvD, ProcesoService srvP, ClienteService srvS, TipodocumentoService srvTD, TipoidentificacionService srvTI, UsuarioService srvU, ExpedientestorService expedienteStorservice) {
		documentoService = srvD;
		procesoService = srvP;
		clienteService = srvS;
		tipoDocumentoService = srvTD;
		tipoIdentificacionService = srvTI;
		usuarioService = srvU;
		expedienteStorService = expedienteStorservice;
	}

	public DocumentoAction() {
		super();
	}

	public String inicioAnular() {
      //  Integer[] arrIdDoc = new Integer[1];
      //  documento = documentoService.findByIdDocumento(idDocumento);
        try {

        } catch (Exception e) {
            log.debug(e.getMessage(), e);
        }

        return "inicioAnular";
    }

	public String goOpenDocumentSearch() {
		log.debug("-> [Action] DocumentoAction - goOpenDocumentSearch():String ");

		if (iIdDoc == null || iIdDoc < 1) {
			log.error("No se recibio ID del documento principal");
			return Action.ERROR;
		}

		mapSession = ActionContext.getContext().getSession();
		mapSession.remove("iIdDocumento");
		mapSession.put("iIdDocumento", iIdDoc);

		log.debug("Se anexaran documentos al documento principal con ID [" + iIdDoc + "]");

		mapTipoDocumento = tipoDocumentoService.getTipoDocumentoMap();

		log.debug("Numero de Tipos de Documentos [" + mapTipoDocumento.size() + "]");

		return Action.SUCCESS;

	}


	public String buscarDocumentoPor() {
		log.debug("-> [Action] DocumentoAction - buscarDocumentoPor():String ");

		if (objExpedienteSearch == null) {
			log.error("No hay filtro de busqueda");
			return Action.ERROR;
		}

		Integer iIdDocumentoPrincipal = null;
		mapSession = ActionContext.getContext().getSession();
		iIdDocumentoPrincipal = (Integer) mapSession.get("iIdDocumento");
		lstDocumento = documentoService.buscarLstPor(iIdDocumentoPrincipal, objExpedienteSearch);
		log.debug("Numero de Documentos encontrados [" + lstDocumento.size() + "]");
		mapTipoDocumento = tipoDocumentoService.getTipoDocumentoMap();
		for (Documento objDoc : lstDocumento) {
			mapChkDocumento.put(objDoc.getIdDocumento(), false);
		}
		return Action.SUCCESS;
	}

	public String alertaDocumento() {
	    Usuario objUsuario = null;
        mapSession = ActionContext.getContext().getSession();
        objUsuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
        String orden = null;

        try{

        	if (executeAccion==null){
        		List<Alerta> listAlert = seguimientoService.getListaAlerta(objUsuario.getIdusuario(), usuarioService.findByIdUsuario(objUsuario.getIdusuario()).getUnidad().getIdunidad(), null);
  			    getMapSession().put("listAlert",listAlert);
        	}else{
        		  if (executeAccion.equals("DESCARGAR")){
        		      documentoService.saveDesactivarAlerta(arrDocumentArea, objUsuario);
        		      List<Alerta> listAlert = seguimientoService.getListaAlerta(objUsuario.getIdusuario(), usuarioService.findByIdUsuario(objUsuario.getIdusuario()).getUnidad().getIdunidad(),null);
        			  getMapSession().put("listAlert",listAlert);
        		  }
        		 if (executeAccion.equals("ORDENARFL")){
        			  if (ordenarFechaLimite.equals("asc")){
        				  ordenarFechaLimite = "desc";
        				  orden = " order by 1 desc";
        			  }else{
        				  ordenarFechaLimite = "asc";
        				  orden = " order by 1 asc";
        			  }


        			  List<Alerta> listAlert = seguimientoService.getListaAlerta(objUsuario.getIdusuario(), usuarioService.findByIdUsuario(objUsuario.getIdusuario()).getUnidad().getIdunidad(), orden);
        			  getMapSession().put("listAlert",listAlert);
        			  ServletActionContext.getRequest().setAttribute("ordenarFechaLimite", ordenarFechaLimite);
        	     }
        	}
        }catch(Exception ex){
        	return Action.ERROR;
        }

		return Action.SUCCESS;
	}


	public String anexarDocumento() {
		log.debug("-> [Action] DocumentoAction - anexarDocumento():String ");

		if (arrIdDoc == null || arrIdDoc.length < 1) {
			log.error("No se selecciono ningun Documento a anexar");
			return Action.ERROR;
		}

		Integer iIdDocumentoPrincipal = null;
		Usuario objUsuario = null;
		mapSession = ActionContext.getContext().getSession();
		iIdDocumentoPrincipal = (Integer) mapSession.get("iIdDocumento");
		objUsuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);

		log.debug("Propietario del expediente [" + objUsuario.getUsuario() + "]");
		log.debug("Documento principal con ID [" + iIdDocumentoPrincipal + "]");

		documentoService.anexarDocumento(iIdDocumentoPrincipal, arrIdDoc);
		mapSession.remove("iIdDocumento");

		return Action.NONE;
	}

	public String goOpenNuevoCliente() throws Exception {
		log.debug("-> [Action] DocumentoAction - goOpenNuevoCliente():String ");

		lstRadio = tipoIdentificacionService.getTipoIdentificacionMap();
		return Action.SUCCESS;
	}


	public String doSaveCliente() {
		log.debug("-> [Action] DocumentoAction - doSaveCliente():String ");

		if (objCliente == null) {
			log.error("No se recibio ningun cliente");
			return Action.ERROR;
		}

		if (clienteService.findByNroIdentificacion(objCliente.getNumeroIdentificacion()) != null) {
			log.info("El cliente con Nro de Identificacion [" + objCliente.getNumeroIdentificacion() + "] ya existe en la Base de Datos");
			return Action.SUCCESS;
		}

		Cliente objClienteNew = null;
		Cliente objClienteOld = null;
		String sTipoAuditoria = null;
		Usuario objUsuarioSesion = null;
		mapSession = ActionContext.getContext().getSession();
		objUsuarioSesion = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
		objClienteOld = new Cliente();
		log.debug("** Creacion de Cliente **");
		objClienteNew = objCliente;
		objClienteNew.setRepresentanteLegal(StringUtil.isEmpty(objClienteNew.getRepresentanteLegal()) ? " " : objClienteNew.getRepresentanteLegal());
		objClienteNew.setDireccionAlternativa(StringUtil.isEmpty(objClienteNew.getDireccionAlternativa()) ? " " : objClienteNew.getDireccionAlternativa());
		objClienteNew.setTelefono(StringUtil.isEmpty(objClienteNew.getTelefono()) ? " " : objClienteNew.getTelefono());
		objClienteNew.setCorreo(StringUtil.isEmpty(objClienteNew.getCorreo()) ? " " : objClienteNew.getCorreo());
		sTipoAuditoria = Constantes.AUDITORIA_TIPO_REGISTRO;
		clienteService.guardarObj(objClienteOld, objClienteNew, objUsuarioSesion.getUsuario(), sTipoAuditoria);

		return Action.SUCCESS;
	}

	public String loadExpediente() {
		log.debug("-> [Action] DocumentoAction - loadExpediente():String ");

		Expediente objE = null;
		if (getIIdExp() == null) {
			return Action.ERROR;
		}
		objE = expedienteService.findByIdExpediente(getIIdExp());
		if (objE == null) {
			return Action.ERROR;
		}
		setObjDD(documentoService.getExpedienteData(getIIdExp()));
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
		setIdcliente(getObjDD().getIIdCliente());
		if (objE.getConcesionario() != null) {
			setStrCorreoConcesionario(objE.getConcesionario().getCorreo());
		}
		//setIdproceso(objE.getProceso().getIdproceso());
		//setProceso(objE.getProceso().getNombre());
		//setStrUnidad(objE.getProceso().getResponsable().getUnidad().getNombre());
		//setStrResponsable(objE.getProceso().getResponsable().getNombres() + " " + objE.getProceso().getResponsable().getApellidos());

		return Action.SUCCESS;
	}

	public String loadNewDoc() throws Exception {
		log.debug("-> [Action] DocumentoAction - loadNewDoc():String ");

		Proceso objP = null;
		setLstRadio(tipoIdentificacionService.getTipoIdentificacionMap());

		if (getIdproceso() == null) {
			return Action.ERROR;
		}

		objP = procesoService.findByIdProceso(getIdproceso());

		if (objP == null) {
			return Action.ERROR;
		}

		setIdproceso(objP.getIdproceso());
		setProceso(objP.getNombre());
		setStrUnidad(objP.getResponsable().getUnidad().getNombre());
		setStrResponsable(objP.getResponsable().getNombres() + " " + objP.getResponsable().getApellidos());
		getObjDD().setStrAbreviado(objP.getTipoproceso().getNombre());

		return Action.SUCCESS;
	}
	/**REN Metodo que llama al adjuntar del detalle de la lista de documentos -----------------------------------------------*/
	public String goAdjuntarArchivo(){
                log.debug("-> [Action] DocumentoAction - goAdjuntarArchivo():String ");
                Documento d = documentoService.findByIdDocumento(iIdDoc);
                proyecto = d.getProyecto();
                codigoVirtual = d.getNroVirtual();
                try{
                     destinatarioIgualRemitente = false;
                     mapSession = ActionContext.getContext().getSession();
		     Usuario objUsuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);

                      if(d.getAutor().getIdusuario().intValue() == objUsuario.getIdUsuarioPerfil().intValue() &&
                         d.getUnidadautor().intValue() == objUsuario.getIdUnidadPerfil())
                         destinatarioIgualRemitente = true; 
			     
                }catch(Exception e){
                      e.printStackTrace();
                      destinatarioIgualRemitente = false;
                }
                
                Map<String,Object> sesion=ActionContext.getContext().getSession();
                sesion.put(Constantes.SESSION_UPLOAD_LIST, new HashMap<String,List<org.ositran.utils.ArchivoTemporal>>());
		return Action.SUCCESS;
	}
	/**REN Metodo que se encarga de adjuntar archivos adicionales a un documento --------------------------------------------*/
	@SuppressWarnings("unchecked")
	public String doAdjuntarArchivo(){
                log.debug("-> [Action] DocumentoAction - doAdjuntarArchivo():String ");
                Map<String,Object> sesion=ActionContext.getContext().getSession();
                Map<String,List<org.ositran.utils.ArchivoTemporal>> upload=(Map<String,List<org.ositran.utils.ArchivoTemporal>>) sesion.get(Constantes.SESSION_UPLOAD_LIST);
                Usuario usuario=(Usuario) sesion.get(Constantes.SESSION_USUARIO);
                documentoService.anexarDocumento(usuario, upload, iIdDoc);
                sesion.put(Constantes.SESSION_UPLOAD_LIST, new HashMap<String,List<org.ositran.utils.ArchivoTemporal>>());
		return Action.SUCCESS;
	}

	/**REN Metodo que llama al eliminar del detalle de la lista de documentos -----------------------------------------------*/
	public String goEliminarArchivo(){
		log.debug("-> [Action] DocumentoAction - goEliminarArchivo():String ");
		return Action.SUCCESS;
	}
        
        public String goFirmarArchivo(){
		log.debug("-> [Action] DocumentoAction - goFirmarArchivo():String ");
		return Action.SUCCESS;
	}

	/**REN Metodo que se encarga de colocar los archivos seleccionados como inactivos----------------------------------------*/
	public void doEliminarArchivo(){
		log.debug("-> [Action] DocumentoAction - doEliminarArchivo():String ");
                
                Map<String,Object> sesion=ActionContext.getContext().getSession();
                Usuario usuario=(Usuario) sesion.get(Constantes.SESSION_USUARIO);

		if(arrIdArchivos != null && arrIdArchivos.length > 0){
			for(Integer idArchivo : arrIdArchivos){
			  archivoService.eliminarArchivo(idArchivo, usuario);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public String upload() {
		log.debug("-> [Action] DocumentoAction - upload():String ");

		if (getIIdDoc() == null) {
			log.error("No se especifico ningun documento");
			return Action.ERROR;
		}

		Map<String, Object> session = ActionContext.getContext().getSession();
		Documento objDocumento = null;
		Documento objNuevoDocumento = null;
		Integer iContador = (Integer) session.get("contador");
		String sRol = (String) session.get("rol");
		String strTempo = SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.DIRECTORIO_TEMPORAL);
		Usuario objUsuario = (Usuario) session.get(Constantes.SESSION_USUARIO);
		log.debug("ENTRO AL UPLOAD [" + upload + "]");
		fullFileName = ServletActionContext.getServletContext().getRealPath("/") + strTempo + uploadFileName;
		log.debug("uploadFileName [" + uploadFileName + "]");
		log.debug("fullFileName [" + fullFileName + "]");
		File theFile = new File(fullFileName);

		// copiar a uno temporal ... en este caso sera RealPah/upload/ para aprovechar

		try {
			FileUtils.copyFile(upload, theFile);
			ArchivoTemporal at = new ArchivoTemporal(uploadFileName, theFile);
			if (sRol.equals(Constantes.ROL_DIGITALIZADOR)) {
				List<ArchivoTemporal> l = (List<ArchivoTemporal>) session.get("uploaded_list");
				if (l == null) {
					l = new ArrayList<ArchivoTemporal>();
				}
				l.add(at);
				session.put("uploaded_list", l);
			} else if (sRol.equals(Constantes.ROL_USUARIO_FINAL) || sRol.equals(Constantes.ROL_USUARIO_FINAL_STOR)) {
				if (iContador == null) {
					iContador = 1;
				} else {
					iContador++;
				}
				session.put("contador", iContador);
				log.debug("Valor del Contador [" + iContador + "]");
				objDocumento = documentoService.findByIdDocumento(getIIdDoc());
				try {
					archivoService.checkInToAlfresco(objUsuario, at, objDocumento, iContador, false);
				} catch (Exception e) {
					log.error(e.getMessage(), e);
					log.error("El archivo no existe ,subiendo nuevo doc ");
					objNuevoDocumento = new Documento();
					objNuevoDocumento.setTipoDocumento(tipoDocumentoService.findByNombre("Otros"));
					objNuevoDocumento.setPropietario(objUsuario);
					objNuevoDocumento.setExpediente(objDocumento.getExpediente());
					objNuevoDocumento.setAccion(accionService.findByNombre("registrar"));
					objNuevoDocumento.setPrincipal(Constantes.DOCUMENTO_NO_PRINCIPAL);
					objNuevoDocumento.setFechaAccion(new Date());
					objNuevoDocumento.setFechaCreacion(new Date());
					objNuevoDocumento.setEstado(Constantes.ESTADO_ACTIVO);
					archivoService.uploadToAlfresco(at, objNuevoDocumento, iContador);
					documentoService.saveDocumento(objNuevoDocumento);
					trazabilidadDocumentoService.saveTrazabilidadDocumento(objNuevoDocumento, objUsuario, false, false);
				}
				session.put("uploaded_list", archivoService.getArchivoList(objDocumento.getExpediente().getIdexpediente(), getIIdDoc(), sRol));
			}
			setObjDD(documentoService.getDocumentDetail(getIIdDoc(), sRol));
		} catch (IOException e) {
			log.error("Ocurrio un error al copiar el archivo", e);
			return Action.ERROR;
		}

		return Action.SUCCESS;
	}



	public String editdocumento() throws Exception {
		log.debug("-> [Action] DocumentoAction - editdocumento():String ");

		if (getIIdDocumento() == null || getIIdDocumento() < 1) {
			log.error("No se recibio ID de Documento a ver");
			return Action.ERROR;
		}
		log.debug("Documento a ver con ID [" + getIIdDocumento() + "]");
		setMapSession(ActionContext.getContext().getSession());
		Usuario objUsuario = (Usuario) getMapSession().get(Constantes.SESSION_USUARIO);
		setObjDocumento(documentoService.findByIdDocumento(getIIdDocumento()));
		setBBotonHabilitado(false);
		getMapSession().put("uploaded_list", archivoService.getArchivoList(getObjDocumento().getExpediente().getIdexpediente(), getObjDocumento().getIdDocumento(), objUsuario.getRol().getNombre()));
		if (archivoService.checkEstadoDigitalizacion(getObjDocumento().getIdDocumento()) > 0) {
			setBBotonHabilitado(true);
		}
		return Action.SUCCESS;
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
       
        /*
        @SuppressWarnings("unused")
	public String viewDocUsuarioFinal() {
             System.out.println("...................................INICIANDO........................................");
             try{
                    OutputStream salida = null;
                    String REPOSITORIO_ID  = SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.ALFRESCO_ROOTID);
                    String USERCONSULTA=SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.ALFRESCO_USUARIOEXTERNO);
                    String USERCONSULTA_CLAVE=SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.ALFRESCO_USUARIOEXTERNO_CLAVE);
                 
                    AlfrescoApiWs alfrescoApiWs;                               
                    String alfrescoHostPublico = SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.ALFRESCO_HOST);
                    String alfrescoHostPort = SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.ALFRESCO_PORT);
                    String alfrescoProtocolo = SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.ALFRESCO_PROTOCOLO);
                    String URL_ALFRESCO = alfrescoProtocolo+"://"+alfrescoHostPublico+":"+alfrescoHostPort+"/alfresco/cmisatom";
                    
                    alfrescoApiWs = new AlfrescoApiWs(URL_ALFRESCO, USERCONSULTA, USERCONSULTA_CLAVE, REPOSITORIO_ID);
                    Session  sesionAlfresco = alfrescoApiWs.getSessionAlfresco();
                    
                    ////////////////////// for/////////////////
                    List<FirmaArchivo> lst = firmaArchivoDAO.findFirmadoUsuario(1045);
                    
                    System.out.println("Tamanito=" + lst.size());
                    for(int i=0;i<lst.size();i++){
                        try{
                                //if (i==20) break;  //TENERE EN CUENTA LOS ARCHIVOS INACTIVOS
                                Archivo x = archivoService.findById(lst.get(i).getIdArchivo());
                                String mes = lst.get(i).getFechaCreacion().toString().substring(5,7) ;
                                String tipo = x.getDocumento().getTipoDocumento().getCodigo().replace("Ã�", "A").replace("Ã‰", "E").replace("Ã�", "I").replace("Ã“", "O").replace("Ãš", "U"); 

                                if (mes.equals("01"))
                                   mes = "ENERO"; 
                                if (mes.equals("02"))
                                   mes = "FEBRERO";
                                if (mes.equals("03"))
                                   mes = "MARZO";

                                Document documento = (Document)sesionAlfresco.getObject(x.getObjectId());//(lstArchivo.get(0).getObjectId());
                                InputStream in = documento.getContentStream().getStream();


                                salida = new FileOutputStream("c:\\RECURSOS\\" + mes + "\\" + tipo + "\\" + x.getRutaAlfresco().substring(x.getRutaAlfresco().lastIndexOf("/") + 1, x.getRutaAlfresco().length()));
                                int data = 999;
                                while (data >= 0) {
                                     data = in.read();
                                     salida.write(data);
                                }

                                salida.flush();
                                if (salida!=null) salida.close();
                                if (in!=null) in.close();
                        }catch(Exception e){
                            e.printStackTrace();
                        }    
                    }     
             }catch(Exception e){
                 e.printStackTrace();
             }              
             return Action.SUCCESS;
        }*/
        
	/**REN Metodo que se activa cuando el usuario final hace click a una fila de la grilla -----------------------------------*/
	
        @SuppressWarnings("unused")
	public String viewDocUsuarioFinal() {
                log.debug("-> [Action] DocumentoAction - viewDocUsuarioFinal():String ");
                
                List<Trazabilidaddocumento> list = null;
                flagBusqueda = 'M';
                
		try{
                        this.paraAprobar = false;
                        
			if (iIdDoc == null || iIdDoc < 1) {
				log.error("No se recibio ningun ID de Documento a ver");
				return Action.ERROR;
			}
			
                        mapSession = ActionContext.getContext().getSession();
			Usuario objUsuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
                        
                        if (objUsuario == null)
                           return "errorsession"; 
                        
                        documento = documentoService.findByIdDocumento(iIdDoc);
                        
                        Integer counttraza= trazabilidadDocumentoService.findByMaxtrazabyIddocumento(iIdDoc).size();
			counttraza = counttraza == null ? 0 : counttraza;

			agregar = false;
                        SeguimientoXUsuario seguimiento = new SeguimientoXUsuario();
                        seguimiento.setIdUsuario(objUsuario.getIdUsuarioPerfil());
                        seguimiento.setIdDocumento(iIdDoc);
                        seguimiento.setUnidadPropietario(objUsuario.getIdUnidadPerfil());
                        seguimiento.setCargoPropietario(objUsuario.getIdFuncionPerfil());
                        
			if(seguimientoXUsuarioService.buscarSeguimiento(seguimiento).isEmpty()){
			  agregar = true;
			}

			try{
                            destinatarioIgualRemitente = false;
                            if(documento.getAutor().getIdusuario().intValue() == objUsuario.getIdUsuarioPerfil().intValue() &&
                               documento.getUnidadautor().intValue() == objUsuario.getIdUnidadPerfil())
                               destinatarioIgualRemitente = true; 
			     
                             controlDevolver = false;
                             Trazabilidaddocumento trazabilidaddocumento = trazabilidadDocumentoService.findByMaxNroRegistro(getIIdDoc(), "", accionService.findByNombre("reenviar").getIdAccion(), objUsuario.getIdUsuarioPerfil(), objUsuario.getIdUnidadPerfil(), objUsuario.getIdFuncionPerfil());
			     
                             if (trazabilidaddocumento!=null){
                                 controlDevolver = false; 
                             }else{
                                 controlDevolver = true;
                             }
			}catch(Exception e){
			  e.printStackTrace();
			  destinatarioIgualRemitente = false;
                          controlDevolver = true;
			}

			Integer traza;
			String creador= (documento.getAutor() != null ? documento.getAutor().getNombreCompleto() : null);

			if(creador==null){
			   creador=Constantes.AUTOR_USUARIO_FINAL;
			}
			
                        list = trazabilidadDocumentoService.findByMaxtrazabyIddocumento(iIdDoc);
                        
                        if(documento.getDocumentoreferencia() != null){
                           list = trazabilidadDocumentoService.findByMaxtrazabyIddocumento(documento.getDocumentoreferencia());
			   traza= list.get(0).getIdtrazabilidaddocumento();
			}else{
                           list = trazabilidadDocumentoService.findByMaxtrazabyIddocumento(iIdDoc);
			   traza= list.get(0).getIdtrazabilidaddocumento();
			}
			
			lstTrazabilidadCopia = trazabilidadcopiaService.buscarUsuarioCopia(iIdDoc,traza);
			StringBuilder cadenaCC = new StringBuilder();
                        
			if(lstTrazabilidadCopia!=null && lstTrazabilidadCopia.size()>0){
			   for(int i=0; i<lstTrazabilidadCopia.size(); i++){
			    if(i!=0) cadenaCC.append(", ");
			       cadenaCC.append(lstTrazabilidadCopia.get(i).getDestinatario().getApellidos()+" "+lstTrazabilidadCopia.get(i).getDestinatario().getNombres());
			    }
			}
		
			Usuario usuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
	
                        if (documento != null) {
                          Expediente expediente = documento.getExpediente();
			  if (expediente != null) {
			        Integer iIdDocumentoSesion = (Integer) mapSession.get(Constantes.SESSION_IDDOCUMENTO);
				String nombreRol = Constantes.ROL_USUARIO_FINAL;
                                
				if (iIdDocumentoSesion != null && iIdDocumentoSesion.intValue() != iIdDoc.intValue()) {
				   log.debug("Removiendo archivos temporales en sesion");
				   mapSession.remove(Constantes.SESSION_UPLOAD_LIST);
				   limpiarCarpetaTemporalxUsuario(usuario.getUsuario());
				}
                                
				iIdUpload = 1;
				mapSession.put(Constantes.SESSION_IDDOCUMENTO, iIdDoc);
                                              
                                if(documento.getDocumentoreferencia() != null){
                                    objDD = documentoService.getDocumentDetailOptimized(documento.getDocumentoreferencia(), nombreRol);
			            objDD.setIdExterno(documento.getID_EXTERNO().toString());
                                    objDD.setDesDocumentoOrigen("");
                                    Usuarioxunidadxfuncion usuarioxunidadxfuncion  = new Usuarioxunidadxfuncion();
                                    usuarioxunidadxfuncion.setIdusuario(objUsuario.getIdUsuarioPerfil());
                                    usuarioxunidadxfuncion.setIdunidad(objUsuario.getIdUnidadPerfil());
                                    usuarioxunidadxfuncion.setIdfuncion(objUsuario.getIdFuncionPerfil());
				    Trazabilidadapoyo tapoyo = trazabilidadapoyoService.buscarUltimaDelegacionUsuarioAR(usuarioxunidadxfuncion, iIdDoc);
							 
                                    if(tapoyo != null){
                                        objDD.setStrAccion(tapoyo.getProveido()==null?"":tapoyo.getProveido().getNombre());
					objDD.setStrContenido(tapoyo.getTexto() != null ? tapoyo.getTexto() : "");
                                        objDD.setStrDestinatario(tapoyo.getDestinatario().getNombres() + " " + tapoyo.getDestinatario().getApellidos());
                                        objDD.setPrioridad(tapoyo.getPrioridad());
                                        objDD.setStrRemitente(tapoyo.getRemitente().getNombres() + " " + tapoyo.getRemitente().getApellidos());
		                        objDD.setStrFechaLimiteAtencion(tapoyo.getFechalimiteatencion() == null ? "" : new SimpleDateFormat(Constantes.FORMATO_FECHA).format(tapoyo.getFechalimiteatencion()));
				    }
                                    
                                    if (documento.getOrigen()!=null){
                                        Documento origen = documentoService.findByIdDocumento(documento.getOrigen());
                                        objDD.setDesDocumentoOrigen(origen.getTipoDocumento().getNombre() + " - " + origen.getNumeroDocumento());
                                    }
				}else{
                                    objDD = documentoService.getDocumentDetailOptimizedAR(getIIdDoc(), nombreRol);
                                    objDD.setDesDocumentoOrigen("");
                                    objDD.setIdExterno(documento.getID_EXTERNO().toString());
                                    if (documento.getOrigen()!=null){
                                        Documento origen = documentoService.findByIdDocumento(documento.getOrigen());
                                        objDD.setDesDocumentoOrigen(origen.getTipoDocumento().getNombre() + " - " + origen.getNumeroDocumento());
                                    }
                                    
                                    if (list!=null && list.size()==1){
                                        objDD.setPrioridad(documento.getPrioridad());
                                        objDD.setDateFechaLimiteAtencion(documento.getFechaLimiteAtencion());
                                     }
                        	}
                                
                                if(documento.getFlagsideco() != null)
                                    objDD.setFlagsideco(documento.getFlagsideco());
                                else
                                    objDD.setFlagsideco("");
                                
                                objDD.setStrRazonSocial("");
                                TipoLegajoUnidad tipoLegajoUnidad = new TipoLegajoUnidad();
                                tipoLegajoUnidad.setAccion("A");
                                tipoLegajoUnidad.setIdTipoLegajoUnidad(usuario.getIdUnidadPerfil());
                                List<TipoLegajoUnidad> lstAgregar = tipoLegajoUnidadDAO.findTipoLegajoUnidad(tipoLegajoUnidad);
                                
                                tipoLegajoUnidad.setAccion("C");
                                List<TipoLegajoUnidad> lstCrear = tipoLegajoUnidadDAO.findTipoLegajoUnidad(tipoLegajoUnidad);
                                
                                if ((lstAgregar==null || lstAgregar.size()==0) && (lstCrear==null || lstCrear.size()==0)){
                                    objDD.setFlagExpediente('-');
                                }
                                if ((lstAgregar==null || lstAgregar.size()==0) && (lstCrear!=null && lstCrear.size()>0)){
                                    objDD.setFlagExpediente('C');
                                }
                                if ((lstAgregar!=null && lstAgregar.size()>0) && (lstCrear==null || lstCrear.size()==0)){
                                    objDD.setFlagExpediente('A');
                                }
                                if ((lstAgregar!=null && lstAgregar.size()>0) && (lstCrear!=null && lstCrear.size()>0)){
                                    objDD.setFlagExpediente('T');
                                }
                                
                                if (documento.getNroVirtual()==null){
                                    objDD.setFlagCodigoVirtual('2');
                                }else{
                                    IotdtmDocExterno recepcion = documentoExternoVirtualDAO.buscarDocumentoVirtual(documento.getNroVirtual());       
                                    if (recepcion!=null && recepcion.getSidrecext()!=null && (recepcion.getSidrecext().getVnumregstd()== null || recepcion.getSidrecext().getVnumregstd().trim().equals(""))){
                                        objDD.setFlagCodigoVirtual('3');
                                    }else{
                                        if (recepcion!=null && (recepcion.getSidrecext().getCflgest() == 'O' || recepcion.getSidrecext().getCflgest() == 'S')){
                                           objDD.setFlagCodigoVirtual('1');
                                        }else{
                                           objDD.setFlagCodigoVirtual('0');
                                        }   
                                    }
                                }        
                                
                                if (documento.getCliente()!=null){
                                    if (documento.getCliente().getTipoinstitucion().getCod_tipoinstitucion().toString().equals(Constantes.COD_PERSONA_NATURAL)){
                                      String nombres =  documento.getCliente().getNombres()==null?"":documento.getCliente().getNombres();
                                      String paterno =  documento.getCliente().getApellidoPaterno()==null?"":documento.getCliente().getApellidoPaterno();
                                      String materno =  documento.getCliente().getApellidoMaterno()==null?"":documento.getCliente().getApellidoMaterno();
                                      objDD.setStrRazonSocial(nombres + " " + paterno + " " + materno);
                                    }else{
                                      objDD.setStrRazonSocial(documento.getCliente().getRazonSocial());
                                    }
                                }
                                    
                        	
                                if(cadenaCC.length()!=0){
				  objDD.setCadenaCC(cadenaCC.toString());
				}
				setLstRadio(tipoIdentificacionService.getTipoIdentificacionMap());
				Usuario usu = new Usuario(((Usuario) mapSession.get(Constantes.SESSION_USUARIO)).getIdUsuarioPerfil());
				
                                if (UtilOsinerg.noEsPropietario(usu, documento.getPropietario())) {
                                   avisopermiso = 1;
			        }
				if (nombreRol.equals(Constantes.ROL_USUARIO_FINAL)){
                                   Integer idDoc = documento.getDocumentoreferencia() != null ? documento.getDocumentoreferencia() : documento.getIdDocumento();
				   mapSession.put(Constantes.SESSION_UPLOAD_LIST, archivoService.getArchivoListPorDocumento(idDoc));
                                   this.puedeRechazar = expedienteService.puedeRechazar(documento.getExpediente().getIdexpediente());
				}
				if (getAvisopermiso() != null && getAvisopermiso() == 2) {
				   setAvisopermiso(1);
				}else{
				   objDD.setIIdDocumento(iIdDoc);
				   
                                   if (origenDocumento!=null){
					avisopermiso = 1;
				   }
                                                   
                                   if (idGrid!=null && idGrid.equals(Constantes.TIPO_GRID_RECEPCION_VIRTUAL_OBSERVADOS)){
                                       return "detalleDocumentoRecObservado";
                                   }
                                   
                                   return Action.SUCCESS;
				}
			    }else{
				log.debug("Excecion nulo");
                            }
				
                            objDD.setIIdDocumento(iIdDoc);

                            if (idGrid!=null && idGrid.equals(Constantes.TIPO_GRID_RECEPCION_VIRTUAL_OBSERVADOS)){
                               return "detalleDocumentoRecObservado";
                            }
                            
			    return Action.SUCCESS;
			}

			if (enVentana != true)
			  enVentana = false;
			
		    }catch(Exception e){
			e.printStackTrace();
		    }
			
                    return Action.ERROR;
	}

         

        @SuppressWarnings("unused")
	public String verDocumento() {
            try{
                HttpServletRequest request = ServletActionContext.getRequest();
                Map<String, Object> mapSession = ActionContext.getContext().getSession();
          	Usuario usuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
          	if (usuario==null)
          	   return Action.ERROR;
          
                documento = documentoService.findByIdDocumento(idDocumento);
		if (documento.getDocumentoreferencia() != null)
                    documento = documentoService.findByIdDocumento(documento.getDocumentoreferencia());
                
                List<Parametro> lstMateria = parametroService.findByTipo(Constantes.COD_SICOR_MATERIA);
                request.setAttribute("parametroMateria", lstMateria);
                
                List<Parametro> lstInfraestructura = parametroService.findByTipo(Constantes.COD_SICOR_INFRAESTRUCTURA);
                request.setAttribute("parametroInfraestructura", lstInfraestructura);
                
                List<Parametro> lstPrioridad = parametroService.findByTipo(Constantes.PARAMETRO_TIPO_PRIORIDAD);
                request.setAttribute("parametroPrioridad", lstPrioridad);
                
                DocumentoReunion documentoReunion = new DocumentoReunion();
                documentoReunion.setTipo("0");
                documentoReunion.setIdDocumento(documento.getIdDocumento());
                request.setAttribute("personalInterno", documentoReunionDAO.getDocumentoReunion(documentoReunion));
                documentoReunion.setTipo("1");
                request.setAttribute("personalExterno", documentoReunionDAO.getDocumentoReunion(documentoReunion));
              
                List<String> lstAdjuntos = new ArrayList<String>();
                List<DocumentoAdjunto> lstDocumentoAdjunto =documentoAdjuntoDAO.findByListDocumentoAdjunto(documento.getIdDocumento());
                List<Parametro> lstTipoAdjunto = parametroService.findByTipo(Constantes.PARAMETRO_TIPOS_DE_ADJUNTOS_MP);
                List<Parametro> lstCopia = parametroService.findByTipo(Constantes.PARAMETRO_ADJUNTO_COPIA_ORIGINAL);
                
                if (lstDocumentoAdjunto!=null){
                  for(int i=0;i<lstDocumentoAdjunto.size();i++){
                     
                     String valor = ""; 
                     for(int j=0;j<lstTipoAdjunto.size();j++){
                       if (lstTipoAdjunto.get(j).getValor().equals(lstDocumentoAdjunto.get(i).getCodTipoAdj())){
                           valor = lstTipoAdjunto.get(j).getDescripcion();
                           for(int k=0;k<lstCopia.size();k++){
                              if (lstCopia.get(k).getValor().equals(lstDocumentoAdjunto.get(i).getCopOrig())){
                                  valor = valor + " " + lstCopia.get(k).getDescripcion() + " (" + lstDocumentoAdjunto.get(i).getNroAdj() + ")";
                                  lstAdjuntos.add(valor);
                                  break;
                              }
                           }
                           break;
                       } 
                     }      
                  }    
                } 
                
                request.setAttribute("adjuntos", lstAdjuntos);
                
            }catch(Exception e){
                return Action.ERROR;
            }
            
            return Action.SUCCESS;
        }

        private boolean verificarPermiso(Trazabilidaddocumento t, Usuario objUsuario){
            try{
                  Usuario usuarioFinal = usuarioService.findByIdUsuario(objUsuario.getIdUsuarioPerfil());
                 
                  if (trazabilidadDocumentoService.contarListTotalTrazabilidadesDocumento(t)==0){
                        boolean encontrado = false;
                        List<Unidad> lstAreasAcceso = new ArrayList();
                        List<Parametro> lstUnidad = parametroService.findByTipo(Constantes.AREAS_ACCESO_GENERAL);
                  
                        for(int i=0;i<lstUnidad.size();i++){
                            lstAreasAcceso.add(new Unidad(Integer.valueOf(lstUnidad.get(i).getValor())));
                            if (lstUnidad.get(i).getValor().toString().equals(objUsuario.getIdUnidadPerfil().toString()))
                               encontrado = true;
                        }

                        if (encontrado){
                            return true;
                        }else{
                            Unidad uf = unidadDAO.findByIdunidad(objUsuario.getIdUnidadPerfil().intValue());
                            List<Unidad> lstJefatura  = new ArrayList();
                            List<Unidad> lstJefaturas = new ArrayList();
                            List<Unidad> lstTodos = new ArrayList();

                            if (objUsuario.getIdFuncionPerfil().toString().equals("3")){
                                 lstTodos = unidadDAO.findByGrupoUnidad(uf.getIdunidad());
                                 if (lstTodos == null) lstTodos = new ArrayList();
                                 lstTodos.add(new Unidad(uf.getIdunidad()));

                                 if (trazabilidadDocumentoService.contarListTotalTrazabilidadesUnidad(t, lstTodos)==0 && trazabilidadDocumentoService.contarListTotalTrazabilidadesUnidad(t, lstAreasAcceso)>0){
                                     return false;
                                 }else{
                                     return true;
                                 }
                            }else{
                                   if (uf.getUnidadgrupo()!=null){
                                        if (uf.getNiveles()!=null && uf.getNiveles().equals("2")){
                                            lstJefatura.add(new Unidad(objUsuario.getIdUnidadPerfil()));
                                            lstJefaturas = unidadDAO.findByGrupoUnidad(objUsuario.getIdUnidadPerfil());
                                            lstTodos =     unidadDAO.findByGrupoUnidad(objUsuario.getIdUnidadPerfil());
                                            lstTodos.add(new Unidad(objUsuario.getIdUnidadPerfil()));  
                                        }else{
                                            lstJefatura.add(new Unidad(objUsuario.getIdUnidadPerfil()));
                                            lstJefaturas = unidadDAO.findByGrupoUnidad(uf.getUnidadgrupo());
                                            lstTodos =     unidadDAO.findByGrupoUnidad(uf.getUnidadgrupo());
                                            lstTodos.add(new Unidad(uf.getUnidadgrupo()));
                                        }    
                                   }else{
                                        lstJefatura.add(new Unidad(objUsuario.getIdUnidadPerfil())); 
                                        lstJefaturas.add(new Unidad(objUsuario.getIdUnidadPerfil()));
                                        lstTodos.add(new Unidad(objUsuario.getIdUnidadPerfil()));
                                    }

                                    if (usuarioFinal.getFlagviewtrazabilidad()!=null){
                                        if (usuarioFinal.getFlagviewtrazabilidad().equals("0")){
                                            if (objUsuario.getIdFuncionPerfil().toString().equals("2") || objUsuario.getIdFuncionPerfil().toString().equals("4")){
                                                if (trazabilidadDocumentoService.contarListTotalTrazabilidadesUnidad(t, lstJefatura)==0){
                                                    return false;
                                                }else{
                                                    return true;
                                                }
                                            }else{
                                                 return false;
                                            }
                                        }

                                        if (usuarioFinal.getFlagviewtrazabilidad().equals("2")){
                                            if (trazabilidadDocumentoService.contarListTotalTrazabilidadesUnidad(t, lstJefatura)==0){
                                                return false;
                                            }else{
                                                return true;
                                            }
                                        }

                                        if (usuarioFinal.getFlagviewtrazabilidad().equals("3")){
                                            if (trazabilidadDocumentoService.contarListTotalTrazabilidadesUnidad(t, lstJefaturas)==0){
                                                 return false;
                                            }else{
                                                 return true;
                                            }
                                        }

                                        if (usuarioFinal.getFlagviewtrazabilidad().equals("4")){
                                            if (trazabilidadDocumentoService.contarListTotalTrazabilidadesUnidad(t, lstTodos)==0){
                                                return false;
                                            }else{
                                                return true;
                                            }       
                                        }
                                    }else{
                                        return false;
                                    }
                            } 
                        }
                  }else{
                     return true; 
                  }
            }catch(Exception e){
                e.printStackTrace();
            }
            
            return false;
        }
        
        /**REN Metodo que se activa cuando el usuario final hace click a una fila de la grilla -----------------------------------*/
	@SuppressWarnings("unused")
	public String viewDocUsuarioFinalBusqueda() {
                boolean conpermiso = true;
                flagBusqueda = 'B';
                try{
                        log.debug("-> [Action] DocumentoAction - viewDocUsuarioFinalBusqueda():String ");
	                
                        if (iIdDoc == null || iIdDoc < 1) {
				log.error("No se recibio ningun ID de Documento a ver");
				return Action.ERROR;
			}
			log.debug("Documento a ver con ID [" + iIdDoc + "]");

			mapSession = ActionContext.getContext().getSession();
                        mapSession.put(Constantes.TAB_BUSQUEDA, String.valueOf(flagBusqueda));
			Usuario objUsuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
                        
                        if (objUsuario == null)
                           return "errorsession"; 
                        
			documento = documentoService.findByIdDocumento(iIdDoc);

	                if(documento.getConfidencial().equals(Constantes.Si)){
			    List<Integer> permitidos = documentoService.getUsuariosPermitidos(documento.getDocumentoreferencia() != null ? documento.getDocumentoreferencia() : documento.getIdDocumento());
		            if(!permitidos.contains(new BigDecimal(objUsuario.getIdUsuarioPerfil()))){
		              conpermiso = false;
		            }
		        }
	        
                        Trazabilidaddocumento t = new Trazabilidaddocumento();
                        Documento d = new Documento();
                        Usuario u = new Usuario();
                        Expediente exp = new Expediente();
                        Usuario propietario = new Usuario();

                        propietario.setIdusuario(documento.getPropietario().getIdusuario());
                        exp.setId(documento.getExpediente().getId());
                        u.setIdusuario(objUsuario.getIdUsuarioPerfil());
                        d.setIdDocumento(iIdDoc);
                        d.setDocumentoreferencia(documento.getDocumentoreferencia());
                        d.setPropietario(propietario);
                        d.setExpediente(exp);
                        t.setDocumento(d);
                        t.setRemitente(u);
                        t.setDestinatario(u);

                        Usuario usuarioFinal = usuarioService.findByIdUsuario(objUsuario.getIdUsuarioPerfil());
                        
                        if (conpermiso){
                            if (usuarioFinal.getFlagviewtrazabilidad()!=null && usuarioFinal.getFlagviewtrazabilidad().equals("1")){
                               conpermiso = true;   
                            }else{
                               conpermiso = verificarPermiso(t, objUsuario);       
                            }      
                        } 

                        Integer counttraza= trazabilidadDocumentoService.findByMaxtrazabyIddocumento(iIdDoc).size();
			counttraza = counttraza == null ? 0 : counttraza;

			agregar = false;

                        SeguimientoXUsuario seguimiento = new SeguimientoXUsuario();
                        seguimiento.setIdUsuario(objUsuario.getIdUsuarioPerfil());
                        seguimiento.setIdDocumento(iIdDoc);
                        seguimiento.setUnidadPropietario(objUsuario.getIdUnidadPerfil());
                        seguimiento.setCargoPropietario(objUsuario.getIdFuncionPerfil());
                        
			if(seguimientoXUsuarioService.buscarSeguimiento(seguimiento).isEmpty()){
				agregar = true;
			}

			try{
			if(documento.getAutor().getIdusuario().intValue() == objUsuario.getIdusuario().intValue() && counttraza == 1){
				destinatarioIgualRemitente = true;
			}else{
				destinatarioIgualRemitente = false;
			}
			}catch(Exception e){
				e.printStackTrace();
				destinatarioIgualRemitente = false;
			}

			if (documento.getAccion().getNombre().equals(Constantes.ACCION_PARA_APROBAR)) {
				this.paraAprobar = true;
			} else {
				this.paraAprobar = false;
			}

			Integer traza;
			String creador= (documento.getAutor() != null ? documento.getAutor().getNombreCompleto() : null);
                       
                        if(creador==null){
			   creador=Constantes.AUTOR_USUARIO_FINAL;
			}
			
                        if(documento.getDocumentoreferencia() != null){
		  	  traza=trazabilidadDocumentoService.findByMaxtrazabyIddocumento(documento.getDocumentoreferencia()).get(0).getIdtrazabilidaddocumento();
			}else{
			  traza=trazabilidadDocumentoService.findByMaxtrazabyIddocumento(iIdDoc).get(0).getIdtrazabilidaddocumento();
			}
                        
			lstTrazabilidadCopia = trazabilidadcopiaService.buscarUsuarioCopia(iIdDoc,traza);

			StringBuilder cadenaCC = new StringBuilder();
			if(lstTrazabilidadCopia!=null && lstTrazabilidadCopia.size()>0){
			    for(int i=0; i<lstTrazabilidadCopia.size(); i++){
				if(i!=0) cadenaCC.append(", ");
		 		 cadenaCC.append( lstTrazabilidadCopia.get(i).getDestinatario().getApellidos()+" "+lstTrazabilidadCopia.get(i).getDestinatario().getNombres());
			    }
			}
			
			Usuario usuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
			log.debug("para verifivar : ENTRO BOVISOR = TRUE");
			boVisor = false;
			usuario = usuarioService.findByIdUsuario(objUsuario.getIdusuario());
			
                        if (documento != null) {
			   Expediente expediente = documento.getExpediente();
			   if (expediente != null) {
			        Integer iIdDocumentoSesion = (Integer) mapSession.get(Constantes.SESSION_IDDOCUMENTO);
                                Rol rol = usuario.getRol();
			        String nombreRol = Constantes.ROL_USUARIO_FINAL;
				if (rol != null) {
				   nombreRol = rol.getNombre();
				}
							
                                if (iIdDocumentoSesion != null && iIdDocumentoSesion.intValue() != iIdDoc.intValue()) {
				    mapSession.remove(Constantes.SESSION_UPLOAD_LIST);
				    limpiarCarpetaTemporalxUsuario(usuario.getUsuario());
				}
                                
				iIdUpload = 1;
				mapSession.put(Constantes.SESSION_IDDOCUMENTO, iIdDoc);

				if(documento.getDocumentoreferencia() != null){
                                  objDD = documentoService.getDocumentDetailOptimized(documento.getDocumentoreferencia(), nombreRol);
				  Usuarioxunidadxfuncion usuarioxunidadxfuncion  = new Usuarioxunidadxfuncion();
                                  usuarioxunidadxfuncion.setIdusuario(objUsuario.getIdUsuarioPerfil());
                                  usuarioxunidadxfuncion.setIdunidad(objUsuario.getIdUnidadPerfil());
                                  usuarioxunidadxfuncion.setIdfuncion(objUsuario.getIdFuncionPerfil());

                                  Trazabilidadapoyo tapoyo = trazabilidadapoyoService.buscarUltimaDelegacionUsuario(usuarioxunidadxfuncion, iIdDoc);
				  objDD.setStrRazonSocial("");
                                  
                                  if (documento.getCliente()!=null){
                                      if (documento.getCliente().getTipoinstitucion().getCod_tipoinstitucion().toString().equals(Constantes.COD_PERSONA_NATURAL)){
                                             String nombres =  documento.getCliente().getNombres()==null?"":documento.getCliente().getNombres();
                                             String paterno =  documento.getCliente().getApellidoPaterno()==null?"":documento.getCliente().getApellidoPaterno();
                                             String materno =  documento.getCliente().getApellidoMaterno()==null?"":documento.getCliente().getApellidoMaterno();
                                             objDD.setStrRazonSocial(nombres + " " + paterno + " " + materno);
                                      }else{
                                             objDD.setStrRazonSocial(documento.getCliente().getRazonSocial());
                                      }
                                  }
                                                                  
                                  if(tapoyo != null){
	 			    objDD.setStrContenido(tapoyo.getTexto() != null ? tapoyo.getTexto() : "");
				    objDD.setStrAsunto(tapoyo.getAsunto() != null ? tapoyo.getAsunto() : objDD.getStrAsunto());
				    objDD.setStrDestinatario(tapoyo.getDestinatario().getNombres() + " " + tapoyo.getDestinatario().getApellidos());
				  }
                                  
                                  if (documento.getFlagatendido()!=null && documento.getFlagatendido().equals("1"))
                                      objDD.setCEstado('T');
                                  
                                   if (documento.getOrigen()!=null){
                                        Documento origen = documentoService.findByIdDocumento(documento.getOrigen());
                                        objDD.setDesDocumentoOrigen(origen.getTipoDocumento().getNombre() + " - " + origen.getNumeroDocumento());
                                    }
				}else{
                                      objDD = documentoService.getDocumentDetailOptimized(getIIdDoc(), nombreRol);
                                      objDD.setStrRazonSocial("");
                                      if (documento.getCliente()!=null){
                                         if (documento.getCliente().getTipoinstitucion().getCod_tipoinstitucion().toString().equals(Constantes.COD_PERSONA_NATURAL)){
                                             String nombres =  documento.getCliente().getNombres()==null?"":documento.getCliente().getNombres();
                                             String paterno =  documento.getCliente().getApellidoPaterno()==null?"":documento.getCliente().getApellidoPaterno();
                                             String materno =  documento.getCliente().getApellidoMaterno()==null?"":documento.getCliente().getApellidoMaterno();
                                             objDD.setStrRazonSocial(nombres + " " + paterno + " " + materno);
                                         }else{
                                             objDD.setStrRazonSocial(documento.getCliente().getRazonSocial());
                                         }
                                      }
                                      
                                      if (documento.getFlagatendido()!=null && documento.getFlagatendido().equals("1")){
                                          objDD.setCEstado('T');
                                      } 
                                      
                                      if (documento.getOrigen()!=null){
                                        Documento origen = documentoService.findByIdDocumento(documento.getOrigen());
                                        objDD.setDesDocumentoOrigen(origen.getTipoDocumento().getNombre() + " - " + origen.getNumeroDocumento());
                                      }
			        }
				// si en caso se tienen usuarios con copia @Danna
				if(cadenaCC.length()!=0){
				  objDD.setCadenaCC(cadenaCC.toString());
				}

				setLstRadio(tipoIdentificacionService.getTipoIdentificacionMap());
				Usuario usu = new Usuario(((Usuario) mapSession.get(Constantes.SESSION_USUARIO)).getIdUsuarioPerfil());
							
				if (UtilOsinerg.noEsPropietario(usu, documento.getPropietario())) {
				  avisopermiso = 1;
				}
				if (nombreRol.equals(Constantes.ROL_USUARIO_FINAL)) {
				   Integer idDoc = documento.getDocumentoreferencia() != null ? documento.getDocumentoreferencia() : documento.getIdDocumento();
				   mapSession.put(Constantes.SESSION_UPLOAD_LIST, archivoService.getArchivoListPorDocumento(idDoc));
                                   this.puedeRechazar = expedienteService.puedeRechazar(documento.getExpediente().getIdexpediente());
			        }
                                if (getAvisopermiso() != null && getAvisopermiso() == 2) {
				  setAvisopermiso(1); 
				} else {
                                  objDD.setIIdDocumento(iIdDoc);

				  if (origenDocumento!=null){                               
				      avisopermiso = 1;
				  }
                                  
                                  if (conpermiso){
                        	    return Action.SUCCESS;
                                  }else{
                                    return "accesolimitado"; 
                                  }  
				}
			    }
                                
                           objDD.setStrAsunto(documento.getExpediente().getAsunto());
			   objDD.setIIdDocumento(iIdDoc);
                           
                           if (conpermiso){
                             return Action.SUCCESS;
                           }else{
                             return "accesolimitado"; 
                           }  
			}

			if (enVentana != true) {
				enVentana = false;
			}
			}catch(Exception e){
				e.printStackTrace();
			}
			return Action.ERROR;
			
	}

        /*
	@SuppressWarnings("unused")
	public String viewDocTramiteDocumentario() throws IOException, ServletException{
		HttpServletRequest request = ServletActionContext.getRequest();
		List<TramiteDocumentario> td = null;
		String capcha = "VERDADERO";

		String gRecaptchaResponse = request
				.getParameter("g-recaptcha-response");

       if (request.getParameter("nroTramitedocumentario")!=null && !request.getParameter("nroTramitedocumentario").toString().trim().equals("")){
			  td = documentoService.buscarTramiteDocumentario(request.getParameter("nroTramitedocumentario"));
			  documentoService.getNroSiguienteConsultaTramiteDocumentario().toString();
       }

        request.setAttribute("nroConsultasTD", documentoService.getNroConsultaTramiteDocumentario().toString());
	    request.setAttribute("tramitedocumentario", td);
	    request.setAttribute("capcha", capcha);
		RequestDispatcher rd = ServletActionContext.getServletContext().getRequestDispatcher("/respuestaTramiteDocumentario.jsp");
		rd.forward(ServletActionContext.getRequest(), ServletActionContext.getResponse());

	    return Action.NONE;
	}*/


/*
	@SuppressWarnings("unused")
	public String viewDocTramite() throws IOException, ServletException{
		HttpServletRequest request = ServletActionContext.getRequest();


        request.setAttribute("nroConsultasTD", documentoService.getNroConsultaTramiteDocumentario().toString());
		RequestDispatcher rd = ServletActionContext.getServletContext().getRequestDispatcher("/detalleTramiteDocumentario.jsp");
	    rd.forward(ServletActionContext.getRequest(), ServletActionContext.getResponse());



	    return Action.SUCCESS;
	}*/


	/**REN Ver detalle del documento desde la grilla de busqueda -------------------------------------------------------------*/
	@SuppressWarnings("unused")
	public String viewDoc() {
		log.debug("-> [Action] DocumentoAction - viewDoc():String ");
                HttpServletRequest request = ServletActionContext.getRequest();
                String key = parametroService.findByTipoUnico(Constantes.KEY_SGD).getValor();
                deMail = true;
                
                try{
                    String id = request.getParameter("idDoc");// URLDecoder.decode(request.getParameter("idDoc"), "UTF-8"); 
                    id = UtilEncrip.decrypt(key, id);
                    iIdDocEmail = new Integer(id);
                    if (iIdDocEmail == null || iIdDocEmail < 1) {
			log.error("No se recibio ningun ID de Documento a ver");
			return Action.ERROR;
		    }
                    
                    log.debug("Documento a ver con ID [" + iIdDocEmail + "]");
                    documento = documentoService.findByIdDocumento(iIdDocEmail);
                    
                    if (documento == null)
                        return Action.ERROR;
                }catch(Exception e){
                    e.printStackTrace();
                    return Action.ERROR;
                }
                
                Usuario usuario = null;
		mapSession = ActionContext.getContext().getSession();
                
                try {
                     
                     String usuarioEnc =  request.getParameter("xxyyxxx");//URLDecoder.decode(request.getParameter("xxyyxxx"), "UTF-8");
                     String usuarioStr = UtilEncrip.decrypt(key, usuarioEnc);
                     usuario = usuarioService.findByUsuario(usuarioStr);
                } catch (Exception e) {
	            e.printStackTrace();
		}

               	if (usuario == null) return Action.ERROR;
	 	AuthThreadLocalHolder.setUsuario(usuario);        
		mapSession.put(Constantes.SESSION_USUARIO_EMAIL, usuario);
		String nombrePC = "";

		try {
                       mapSession.put("sTipoGridEmail", "100");
                       InetAddress inetAddress = InetAddress.getByName(request.getRemoteAddr());
                       nombrePC = inetAddress.getHostName();
                       if (nombrePC.equalsIgnoreCase("localhost")) {
                           nombrePC = java.net.InetAddress.getLocalHost().getCanonicalHostName();
                       }
                } catch (UnknownHostException e) {
                       log.error("No se pudo encontrar el nombre para el ip determinado ", e);
                       nombrePC = request.getRemoteAddr();
                } catch(NullPointerException e){
                       log.error("Hubo un error de puntero nulo al buscar la IP origen", e);
                       nombrePC = "Indeterminado";
               }

                mapSession.put("nombrePCEmail", nombrePC);
		//mapSession.put("provieneDeMail", true );
		suministros = null;
		submotivos = null;
               
                if (documento != null) {
		        Expediente expediente = documento.getExpediente();
			if (expediente != null) {
                            String nombreRol = Constantes.ROL_USUARIO_FINAL;
		  	    log.debug("Removiendo archivos temporales en sesion");
			    mapSession.remove(Constantes.SESSION_UPLOAD_LIST_EMAIL);
			    limpiarCarpetaTemporalxUsuario(usuario.getUsuario());
			    
                            Integer idDoc = documento.getDocumentoreferencia() != null ? documento.getDocumentoreferencia() : documento.getIdDocumento();
			    mapSession.put(Constantes.SESSION_UPLOAD_LIST_EMAIL, archivoService.getArchivoListPorDocumento(idDoc));
			    
                            iIdUpload = 1;
			    mapSession.put(Constantes.SESSION_IDDOCUMENTO_EMAIL, iIdDocEmail);
			    if(documento.getDocumentoreferencia() != null){
			      objDD = documentoService.getDocumentDetailOptimized(documento.getDocumentoreferencia(), nombreRol);
			    }else{
			      objDD = documentoService.getDocumentDetailOptimized(documento.getIdDocumento(), nombreRol);
			    }
                            
                            objDD.setStrRazonSocial("");
                                
                            if (documento.getCliente()!=null){
                                    if (documento.getCliente().getTipoinstitucion().getCod_tipoinstitucion().toString().equals(Constantes.COD_PERSONA_NATURAL)){
                                      String nombres =  documento.getCliente().getNombres()==null?"":documento.getCliente().getNombres();
                                      String paterno =  documento.getCliente().getApellidoPaterno()==null?"":documento.getCliente().getApellidoPaterno();
                                      String materno =  documento.getCliente().getApellidoMaterno()==null?"":documento.getCliente().getApellidoMaterno();
                                      objDD.setStrRazonSocial(nombres + " " + paterno + " " + materno);
                                    }else{
                                      objDD.setStrRazonSocial(documento.getCliente().getRazonSocial());
                                    }
                            }
                              
			}
                        
                        objDD.setStrAsunto(documento.getAsunto());
			return "detalleDocEmail";
		}
                
                return Action.ERROR;
		
	}

        /*
	private String mostrarDocumentoIntalio() {
		log.debug("-> [Action] DocumentoAction - mostrarDocumentoIntalio():String ");

		Usuario usuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
		if (usuario != null) {
			Cliente cliente = documento.getExpediente().getCliente();
			if (cliente.getRazonSocial() != null) {
				nombreCliente = cliente.getRazonSocial();
			} else {
				nombreCliente = cliente.getNombres() + " " + cliente.getApellidoPaterno() + " " + cliente.getApellidoMaterno();
			}
			archivos = archivoService.getArchivoList(documento.getExpediente().getIdexpediente(), documento.getIdDocumento(), null).get("upload1");
			int idExpediente = documento.getExpediente().getIdexpediente();
			urlIntalio = null;// intalioService.getAjaxFormURL(usuario, idExpediente);
			if (urlIntalio != null) {
				return "intalio";
			}
			log.error("No se pudo encontrar la url para el usuario " + usuario + " en el expediente " + documento.getExpediente().getNroexpediente());
		}
		return Action.ERROR;
	}*/

        /*
	private boolean esUsuarioSAS(Usuario usuario) {
		log.debug("-> [Action] DocumentoAction - esUsuarioSAS():boolean ");

		List<Rol> roles = null;//usuario.getRoles();
		for (Rol rol : roles) {
			if (rol.getNombre().equals(Constantes.ROL_USUARIO_SALFE_SAS)
					|| rol.getNombre().equals(Constantes.ROL_USUARIO_ANALISTA_SAS)
					|| rol.getNombre().equals(Constantes.ROL_USUARIO_ASESOR_SAS)
					|| rol.getNombre().equals(Constantes.ROL_USUARIO_SGFE_SAS)
					|| rol.getNombre().equals(Constantes.ROL_USUARIO_JU_AC_SAS)
					|| rol.getNombre().equals(Constantes.ROL_USUARIO_JU_AM_SAS)
					|| rol.getNombre().equals(Constantes.ROL_USUARIO_JU_CA_SAS)
					|| rol.getNombre().equals(Constantes.ROL_USUARIO_JU_CO_SAS)
					|| rol.getNombre().equals(Constantes.ROL_USUARIO_JU_DI_SAS)
					|| rol.getNombre().equals(Constantes.ROL_USUARIO_JU_GA_SAS)
					|| rol.getNombre().equals(Constantes.ROL_USUARIO_JU_GS_SAS)
					|| rol.getNombre().equals(Constantes.ROL_USUARIO_JU_TR_SAS)) {
				return true;
			}
		}
		return false;
	}*/

        public String viewExpUsuarioFinal() {
             Legajo legajo = new Legajo();
             legajo.setIdLegajo(iIdLegajo);
             legajo.setEstado("");
             legajo = legajoService.findByIdLegajo(legajo);
             
             objDD = new DocumentoDetail();
             objDD.setiIdLegajoOrigen(iIdLegajo);
             objDD.setiIdLegajo(iIdLegajo);
             objDD.setLegajo(legajo);
             objDD.setOpcionMenu("");
             return Action.SUCCESS;
        } 

	public String viewDocAdicionales() {
		log.debug("-> [Action] DocumentoAction - viewDocAdicionales():String ");
                
                try {
                                           
		    if (idenv != null) {
			Documentoenviado documentoEnviado = documentoenviadoService.findByIddocumentoenviado(idenv);
              	        if(documentoEnviado.getTipoEnvio().equals(Constantes.TIPO_ENVIO_TRANSFERIR)){
			    Trazabilidaddocumento trazdoc = new Trazabilidaddocumento();
			    trazdoc = trazabilidadDocumentoService.findByIdTrazabilidadDocumento(documentoEnviado.getIdTrazabilidadEnvio());
		            iIdDoc = trazdoc.getDocumento().getIdDocumento();
		        }else if(documentoEnviado.getTipoEnvio().equals(Constantes.TIPO_ENVIO_MULTIPLE)){
                                Trazabilidadapoyo 	trazabilidadapoyo = new Trazabilidadapoyo();
		                trazabilidadapoyo = trazabilidadapoyoService.findByIdTrazabilidadApoyo(documentoEnviado.getIdTrazabilidadEnvio());
			        iIdDoc = trazabilidadapoyo.getDocumento();
		        }else if(documentoEnviado.getTipoEnvio().equals(Constantes.TIPO_ENVIO_NOTIFICAR)){
				Notificacion notificacion = new Notificacion();
			        notificacion = notificacionService.buscarObjPorID(documentoEnviado.getIdTrazabilidadEnvio());
			        iIdDoc = notificacion.getIddocumento().getIdDocumento();
			}
		    }
                        
                    if (idpendientes!=null){
                        DocumentoPendiente documentoPendiente = documentoPendienteDAO.findByIdDocumentoPendiente(idpendientes);
                        iIdDoc = documentoPendiente.getIddocumento();
                    }
                    
                    if (idseguimientos!=null){
                        SeguimientoXUsuario seguimientoXUsuario = seguimientoXUsuarioService.findByIdSeguimiento(idseguimientos);
                        iIdDoc = seguimientoXUsuario.getIdDocumento();
                    }
                    
                    if (idfirmados!=null){
                        //SeguimientoXFirma seguimientoXFirma = seguimientoXFirmaDAO.findByIdDocumentoFirmado(idfirmados);
                        iIdDoc = idfirmados;//seguimientoXFirma.getIdDocumento();
                    }
                    
                    if (idatendidos!=null){
                         DocumentoAtendido documentoAtendido = documentoAtendidoDAO.findByIdDocumentoAtendido(idatendidos);
                         iIdDoc = documentoAtendido.getIddocumento();
                     }
                    
                    if (idanulados!=null){
                         DocumentoAnulado documentoAnulado = documentoAnuladoDAO.findByIdDocumentoAnulado(idanulados);
                         iIdDoc = documentoAnulado.getIddocumento();
                     }

	            if (getIIdNotificacion() != null) {
		       setIIdDoc(notificacionService.buscarObjPorID(getIIdNotificacion()).getIddocumento().getIdDocumento());
		    }
			
		    if (getIIdDoc() == null) {
			log.debug("getIIdDoc()==NULL");
			return Action.ERROR;
		    }
                    
                    mapSession = ActionContext.getContext().getSession();
                    /* JC
                    Usuario objUsuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
                    Usuario u = new Usuario();
                    Trazabilidaddocumento t = new Trazabilidaddocumento();
                    Documento d = documentoService.findByIdDocumento(iIdDoc);
                    u.setIdusuario(objUsuario.getIdUsuarioPerfil());
                    t.setDocumento(d);
                    t.setRemitente(u);
                    t.setDestinatario(u);
                    Usuario usuarioFinal = usuarioService.findByIdUsuario(objUsuario.getIdUsuarioPerfil());
                    
                    if (trazabilidadDocumentoService.contarListTotalTrazabilidadesExpediente(t)==0 && usuarioFinal.getFlagviewtrazabilidad()!=null && usuarioFinal.getFlagviewtrazabilidad().equals("0")){
                       return "sinpermiso";
                    }*/
                        
		    setObjDD(documentoService.getDocumentDetail(getIIdDoc(), null));

		    if (enVentana != true) {
			enVentana = false;
		    }

                    return Action.SUCCESS;

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
			return Action.ERROR;
		}
	}
        
        public String viewDocLegajoBusqueda() {
	       log.debug("-> [Action] DocumentoAction - viewDocLegajoBusqueda():String ");
                
               try { 
                    mapSession = ActionContext.getContext().getSession();
                    Usuario objUsuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
                    Usuario u = new Usuario();
                    Trazabilidaddocumento t = new Trazabilidaddocumento();
                    Documento d = documentoService.findByIdDocumento(iIdDoc);
                    u.setIdusuario(objUsuario.getIdUsuarioPerfil());
                    t.setDocumento(d);
                    t.setRemitente(u);
                    t.setDestinatario(u);
                    Usuario usuarioFinal = usuarioService.findByIdUsuario(objUsuario.getIdUsuarioPerfil());
               
                    if(d.getConfidencial().equals(Constantes.Si)){
	               List<Integer> permitidos = documentoService.getUsuariosPermitidos(d.getDocumentoreferencia() != null ? d.getDocumentoreferencia() : d.getIdDocumento());
		       if(!permitidos.contains(new BigDecimal(objUsuario.getIdUsuarioPerfil()))){
		           return "sinpermiso";
		        }
		    }
                    
                    boolean conpermiso = false;
                    if (usuarioFinal.getFlagviewtrazabilidad()!=null && usuarioFinal.getFlagviewtrazabilidad().equals("1")){
                          conpermiso = true;   
                    }else{
                          conpermiso = verificarPermiso(t, objUsuario);
                          if (!conpermiso)
                             return "sinpermiso"; 
                    }
                    
                        
                    setObjDD(documentoService.getDocumentDetail(getIIdDoc(), null));
                    objDD.setOpcionMenu("B");
                  
                    LegajoDocumento legajoDocumento = new LegajoDocumento();
                    legajoDocumento.setIdDocumento(d.getDocumentoreferencia()==null?d.getIdDocumento():d.getDocumentoreferencia());
                    List<LegajoDocumento> lst = legajoDocumentoService.findDocumento(legajoDocumento, objUsuario);
                    
                    if (lst!=null){
                       objDD.setiIdLegajoOrigen(lst.get(0).getIdLegajo());  
                    }  
                    if (enVentana != true) {
			enVentana = false;
		    }

                    return Action.SUCCESS;

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
			return Action.ERROR;
		}
	}
        
        public String viewDocLegajo() {
		log.debug("-> [Action] DocumentoAction - viewDocLegajo():String ");
                
                try {
                                           
		    if (idenv != null) {
			Documentoenviado documentoEnviado = documentoenviadoService.findByIddocumentoenviado(idenv);
              	        if(documentoEnviado.getTipoEnvio().equals(Constantes.TIPO_ENVIO_TRANSFERIR)){
			    Trazabilidaddocumento trazdoc = new Trazabilidaddocumento();
			    trazdoc = trazabilidadDocumentoService.findByIdTrazabilidadDocumento(documentoEnviado.getIdTrazabilidadEnvio());
		            iIdDoc = trazdoc.getDocumento().getIdDocumento();
		        }else if(documentoEnviado.getTipoEnvio().equals(Constantes.TIPO_ENVIO_MULTIPLE)){
                                Trazabilidadapoyo 	trazabilidadapoyo = new Trazabilidadapoyo();
		                trazabilidadapoyo = trazabilidadapoyoService.findByIdTrazabilidadApoyo(documentoEnviado.getIdTrazabilidadEnvio());
			        iIdDoc = trazabilidadapoyo.getDocumento();
		        }else if(documentoEnviado.getTipoEnvio().equals(Constantes.TIPO_ENVIO_NOTIFICAR)){
				Notificacion notificacion = new Notificacion();
			        notificacion = notificacionService.buscarObjPorID(documentoEnviado.getIdTrazabilidadEnvio());
			        iIdDoc = notificacion.getIddocumento().getIdDocumento();
			}
		    }
                        
                    if (idpendientes!=null){
                        DocumentoPendiente documentoPendiente = documentoPendienteDAO.findByIdDocumentoPendiente(idpendientes);
                        iIdDoc = documentoPendiente.getIddocumento();
                    }
                    
                    if (idseguimientos!=null){
                        SeguimientoXUsuario seguimientoXUsuario = seguimientoXUsuarioService.findByIdSeguimiento(idseguimientos);
                        iIdDoc = seguimientoXUsuario.getIdDocumento();
                    }
                    
                    if (idfirmados!=null){
                        //SeguimientoXFirma seguimientoXFirma = seguimientoXFirmaDAO.findByIdDocumentoFirmado(idfirmados);
                        iIdDoc = idfirmados;//seguimientoXFirma.getIdDocumento();
                    }
                    
                    if (idatendidos!=null){
                         DocumentoAtendido documentoAtendido = documentoAtendidoDAO.findByIdDocumentoAtendido(idatendidos);
                         iIdDoc = documentoAtendido.getIddocumento();
                     }
                    
                    if (idanulados!=null){
                         DocumentoAnulado documentoAnulado = documentoAnuladoDAO.findByIdDocumentoAnulado(idanulados);
                         iIdDoc = documentoAnulado.getIddocumento();
                     }

	            if (getIIdNotificacion() != null) {
		       setIIdDoc(notificacionService.buscarObjPorID(getIIdNotificacion()).getIddocumento().getIdDocumento());
		    }
			
		    if (getIIdDoc() == null) {
			log.debug("getIIdDoc()==NULL");
			return Action.ERROR;
		    }
                    
                    mapSession = ActionContext.getContext().getSession();
                    /* JC
                    Usuario objUsuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
                    Usuario u = new Usuario();
                    Trazabilidaddocumento t = new Trazabilidaddocumento();
                    Documento d = documentoService.findByIdDocumento(iIdDoc);
                    u.setIdusuario(objUsuario.getIdUsuarioPerfil());
                    t.setDocumento(d);
                    t.setRemitente(u);
                    t.setDestinatario(u);
                    Usuario usuarioFinal = usuarioService.findByIdUsuario(objUsuario.getIdUsuarioPerfil());
                    
                    if (trazabilidadDocumentoService.contarListTotalTrazabilidadesExpediente(t)==0 && usuarioFinal.getFlagviewtrazabilidad()!=null && usuarioFinal.getFlagviewtrazabilidad().equals("0")){
                       return "sinpermiso";
                    }*/
                        
                    setObjDD(documentoService.getDocumentDetail(getIIdDoc(), null));
                 
                    mapSession = ActionContext.getContext().getSession();
	            Usuario usuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
                    Documento d = documentoService.findByIdDocumento(getIIdDoc());
                    LegajoDocumento legajoDocumento = new LegajoDocumento();
                    legajoDocumento.setIdDocumento(d.getDocumentoreferencia()==null?d.getIdDocumento():d.getDocumentoreferencia());
                    List<LegajoDocumento> lst = legajoDocumentoService.findDocumento(legajoDocumento, usuario);
                    
                    if (lst!=null && lst.size()>0){
                       objDD.setiIdLegajoOrigen(lst.get(0).getIdLegajo());  
                    }  
                    if (enVentana != true) {
			enVentana = false;
		    }

                    return Action.SUCCESS;

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
			return Action.ERROR;
		}
	}
        
        public String viewDocAdicionalesBusqueda() {
		log.debug("-> [Action] DocumentoAction - viewDocAdicionalesBusqueda():String ");
                
                try {
                                       
                    if (getIIdDoc() == null) {
			log.debug("getIIdDoc()==NULL");
			return Action.ERROR;
		    }
                    
                    mapSession = ActionContext.getContext().getSession();
                    Usuario objUsuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
                    Usuario u = new Usuario();
                    Trazabilidaddocumento t = new Trazabilidaddocumento();
                    Documento d = documentoService.findByIdDocumento(iIdDoc);
                    u.setIdusuario(objUsuario.getIdUsuarioPerfil());
                    t.setDocumento(d);
                    t.setRemitente(u);
                    t.setDestinatario(u);
                    Usuario usuarioFinal = usuarioService.findByIdUsuario(objUsuario.getIdUsuarioPerfil());
                    
                    /*if (trazabilidadDocumentoService.contarListTotalTrazabilidadesExpediente(t)==0 && usuarioFinal.getFlagviewtrazabilidad()!=null && usuarioFinal.getFlagviewtrazabilidad().equals("0")){
                       return "sinpermiso";
                    }*/
                    
                    if(d.getConfidencial().equals(Constantes.Si)){
	               List<Integer> permitidos = documentoService.getUsuariosPermitidos(d.getDocumentoreferencia() != null ? d.getDocumentoreferencia() : d.getIdDocumento());
		       if(!permitidos.contains(new BigDecimal(objUsuario.getIdUsuarioPerfil()))){
		           return "sinpermiso";
		        }
		    }
                    
                    boolean conpermiso = false;
                    if (usuarioFinal.getFlagviewtrazabilidad()!=null && usuarioFinal.getFlagviewtrazabilidad().equals("1")){
                          conpermiso = true;   
                    }else{
                          conpermiso = verificarPermiso(t, objUsuario);
                          if (!conpermiso)
                             return "sinpermiso"; 
                    }
                        
		    setObjDD(documentoService.getDocumentDetail(getIIdDoc(), null));
                    objDD.setOpcionMenu("B");
                                  
                    if (enVentana != true) {
			enVentana = false;
		    }

                    return Action.SUCCESS;

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
			return Action.ERROR;
		}
	}
        
        
        public String viewDocReferenciales() {
		log.debug("-> [Action] DocumentoAction - viewDocReferenciales():String ");
                
		try {
                      if (idenv != null) {
			 Documentoenviado documentoEnviado = documentoenviadoService.findByIddocumentoenviado(idenv);
              	         if(documentoEnviado.getTipoEnvio().equals(Constantes.TIPO_ENVIO_TRANSFERIR)){
	                     Trazabilidaddocumento trazdoc = new Trazabilidaddocumento();
			     trazdoc = trazabilidadDocumentoService.findByIdTrazabilidadDocumento(documentoEnviado.getIdTrazabilidadEnvio());
			     iIdDoc = trazdoc.getDocumento().getIdDocumento();
			 }else if(documentoEnviado.getTipoEnvio().equals(Constantes.TIPO_ENVIO_MULTIPLE)){
                             Trazabilidadapoyo 	trazabilidadapoyo = new Trazabilidadapoyo();
			     trazabilidadapoyo = trazabilidadapoyoService.findByIdTrazabilidadApoyo(documentoEnviado.getIdTrazabilidadEnvio());
			     iIdDoc = trazabilidadapoyo.getDocumento();
			 }else if(documentoEnviado.getTipoEnvio().equals(Constantes.TIPO_ENVIO_NOTIFICAR)){
			     Notificacion notificacion = new Notificacion();
			     notificacion = notificacionService.buscarObjPorID(documentoEnviado.getIdTrazabilidadEnvio());
			     iIdDoc = notificacion.getIddocumento().getIdDocumento();
			 }
		      }

		      if (getIIdNotificacion() != null) {
			 setIIdDoc(notificacionService.buscarObjPorID(getIIdNotificacion()).getIddocumento().getIdDocumento());
		      }
                        
                      if (idpendientes!=null){
                         DocumentoPendiente documentoPendiente = documentoPendienteDAO.findByIdDocumentoPendiente(idpendientes);
                         iIdDoc = documentoPendiente.getIddocumento();
                      }
                      
                      if (idseguimientos!=null){
                        SeguimientoXUsuario seguimientoXUsuario = seguimientoXUsuarioService.findByIdSeguimiento(idseguimientos);
                        iIdDoc = seguimientoXUsuario.getIdDocumento();
                      }
                      
                      if (idfirmados!=null){
                        //SeguimientoXFirma seguimientoXFirma = seguimientoXFirmaDAO.findByIdDocumentoFirmado(idfirmados);
                        iIdDoc =  idfirmados;//seguimientoXFirma.getIdDocumento();
                      }
                      
                      if (idatendidos!=null){
                         DocumentoAtendido documentoAtendido = documentoAtendidoDAO.findByIdDocumentoAtendido(idatendidos);
                         iIdDoc = documentoAtendido.getIddocumento();
                      }
                      
                      if (idanulados!=null){
                         DocumentoAnulado documentoAnulado = documentoAnuladoDAO.findByIdDocumentoAnulado(idanulados);
                         iIdDoc = documentoAnulado.getIddocumento();
                      }
                      
		      if (getIIdDoc() == null) {
			 return Action.ERROR;
		      }
                      
                      
                      mapSession = ActionContext.getContext().getSession();
                      //Usuario objUsuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
                     /* Usuario u = new Usuario(); JC
                      Trazabilidaddocumento t = new Trazabilidaddocumento();
                      Documento d = documentoService.findByIdDocumento(iIdDoc);
                      u.setIdusuario(objUsuario.getIdUsuarioPerfil());
                      t.setDocumento(d);
                      t.setRemitente(u);
                      t.setDestinatario(u);
                      Usuario usuarioFinal = usuarioService.findByIdUsuario(objUsuario.getIdUsuarioPerfil());
                    
                      if (trazabilidadDocumentoService.contarListTotalTrazabilidadesExpediente(t)==0 && usuarioFinal.getFlagviewtrazabilidad()!=null && usuarioFinal.getFlagviewtrazabilidad().equals("0")){
                         return "sinpermiso";
                      }*/
                      
		      setObjDD(documentoService.getDocumentDetail(getIIdDoc(), null));
                     
		      if (enVentana != true) {
			 enVentana = false;
		      }

                      return Action.SUCCESS;

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
			return Action.ERROR;
		}
	}
        
        public String viewDocReferencialesBusqueda() {
		log.debug("-> [Action] DocumentoAction - viewDocReferencialesBusqueda():String ");
                
		try {
                      
		      if (getIIdDoc() == null) {
			 return Action.ERROR;
		      }
                      
                      
                      mapSession = ActionContext.getContext().getSession();
                      Usuario objUsuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
                      Usuario u = new Usuario();
                      Trazabilidaddocumento t = new Trazabilidaddocumento();
                      Documento d = documentoService.findByIdDocumento(iIdDoc);
                      u.setIdusuario(objUsuario.getIdUsuarioPerfil());
                      t.setDocumento(d);
                      t.setRemitente(u);
                      t.setDestinatario(u);
                      Usuario usuarioFinal = usuarioService.findByIdUsuario(objUsuario.getIdUsuarioPerfil());
                      boolean conpermiso = false;
                      /*if (trazabilidadDocumentoService.contarListTotalTrazabilidadesExpediente(t)==0 && usuarioFinal.getFlagviewtrazabilidad()!=null && usuarioFinal.getFlagviewtrazabilidad().equals("0")){
                         return "sinpermiso";
                      }*/
                      
                      if(d.getConfidencial().equals(Constantes.Si)){
	               List<Integer> permitidos = documentoService.getUsuariosPermitidos(d.getDocumentoreferencia() != null ? d.getDocumentoreferencia() : d.getIdDocumento());
		       if(!permitidos.contains(new BigDecimal(objUsuario.getIdUsuarioPerfil()))){
		           return "sinpermiso";
		        }
		     }
                      
                      if (usuarioFinal.getFlagviewtrazabilidad()!=null && usuarioFinal.getFlagviewtrazabilidad().equals("1")){
                          conpermiso = true;   
                      }else{
                          conpermiso = verificarPermiso(t, objUsuario);
                          if (!conpermiso)
                             return "sinpermiso"; 
                      }
                      
		      setObjDD(documentoService.getDocumentDetail(getIIdDoc(), null));
                      objDD.setOpcionMenu("B");
                       
		      if (enVentana != true) {
			 enVentana = false;
		      }

                      return Action.SUCCESS;

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
			return Action.ERROR;
		}
	}
        
        public String viewDocArchivos() {
		log.debug("-> [Action] DocumentoAction - viewDocArchivos():String ");
                
		try {
                      if (idenv != null) {
			 Documentoenviado documentoEnviado = documentoenviadoService.findByIddocumentoenviado(idenv);
              	         if(documentoEnviado.getTipoEnvio().equals(Constantes.TIPO_ENVIO_TRANSFERIR)){
	                     Trazabilidaddocumento trazdoc = new Trazabilidaddocumento();
			     trazdoc = trazabilidadDocumentoService.findByIdTrazabilidadDocumento(documentoEnviado.getIdTrazabilidadEnvio());
			     iIdDoc = trazdoc.getDocumento().getIdDocumento();
			 }else if(documentoEnviado.getTipoEnvio().equals(Constantes.TIPO_ENVIO_MULTIPLE)){
                             Trazabilidadapoyo 	trazabilidadapoyo = new Trazabilidadapoyo();
			     trazabilidadapoyo = trazabilidadapoyoService.findByIdTrazabilidadApoyo(documentoEnviado.getIdTrazabilidadEnvio());
			     iIdDoc = trazabilidadapoyo.getDocumento();
			 }else if(documentoEnviado.getTipoEnvio().equals(Constantes.TIPO_ENVIO_NOTIFICAR)){
			     Notificacion notificacion = new Notificacion();
			     notificacion = notificacionService.buscarObjPorID(documentoEnviado.getIdTrazabilidadEnvio());
			     iIdDoc = notificacion.getIddocumento().getIdDocumento();
			 }
		      }

		      if (getIIdNotificacion() != null) {
			 setIIdDoc(notificacionService.buscarObjPorID(getIIdNotificacion()).getIddocumento().getIdDocumento());
		      }
                        
                      if (idpendientes!=null){
                         DocumentoPendiente documentoPendiente = documentoPendienteDAO.findByIdDocumentoPendiente(idpendientes);
                         iIdDoc = documentoPendiente.getIddocumento();
                      }
                      
                      if (idseguimientos!=null){
                        SeguimientoXUsuario seguimientoXUsuario = seguimientoXUsuarioService.findByIdSeguimiento(idseguimientos);
                        iIdDoc = seguimientoXUsuario.getIdDocumento();
                      }
                      
                      if (idfirmados!=null){
                        //SeguimientoXFirma seguimientoXFirma = seguimientoXFirmaDAO.findByIdDocumentoFirmado(idfirmados);
                        iIdDoc =  idfirmados;//seguimientoXFirma.getIdDocumento();
                      }
                      
                      if (idatendidos!=null){
                         DocumentoAtendido documentoAtendido = documentoAtendidoDAO.findByIdDocumentoAtendido(idatendidos);
                         iIdDoc = documentoAtendido.getIddocumento();
                      }
                      
                      if (idanulados!=null){
                         DocumentoAnulado documentoAnulado = documentoAnuladoDAO.findByIdDocumentoAnulado(idanulados);
                         iIdDoc = documentoAnulado.getIddocumento();
                      }
                      
		      if (getIIdDoc() == null) {
			 return Action.ERROR;
		      }
                      
                      
                      mapSession = ActionContext.getContext().getSession();
                      Usuario objUsuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
                      Usuario u = new Usuario();
                      Trazabilidaddocumento t = new Trazabilidaddocumento();
                      Documento d = documentoService.findByIdDocumento(iIdDoc);
                      u.setIdusuario(objUsuario.getIdUsuarioPerfil());
                      t.setDocumento(d);
                      t.setRemitente(u);
                      t.setDestinatario(u);
                      Usuario usuarioFinal = usuarioService.findByIdUsuario(objUsuario.getIdUsuarioPerfil());
                    
                      if (trazabilidadDocumentoService.contarListTotalTrazabilidadesExpediente(t)==0 && usuarioFinal.getFlagviewtrazabilidad()!=null && usuarioFinal.getFlagviewtrazabilidad().equals("0")){
                         return "sinpermiso";
                      }
                      
		      setObjDD(documentoService.getDocumentDetail(getIIdDoc(), null));

		      if (enVentana != true) {
			 enVentana = false;
		      }

                      return Action.SUCCESS;

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
			return Action.ERROR;
		}
	}
        
        public String viewDocRespuestas() {
		log.debug("-> [Action] DocumentoAction - viewDocRespuestas():String ");
               try {
                     if (idenv != null) {
                         
			 Documentoenviado documentoEnviado = documentoenviadoService.findByIddocumentoenviado(idenv);
              	         if(documentoEnviado.getTipoEnvio().equals(Constantes.TIPO_ENVIO_TRANSFERIR)){
	                     Trazabilidaddocumento trazdoc = new Trazabilidaddocumento();
			     trazdoc = trazabilidadDocumentoService.findByIdTrazabilidadDocumento(documentoEnviado.getIdTrazabilidadEnvio());
			     iIdDoc = trazdoc.getDocumento().getIdDocumento();
                         }else if(documentoEnviado.getTipoEnvio().equals(Constantes.TIPO_ENVIO_MULTIPLE)){
                             Trazabilidadapoyo 	trazabilidadapoyo = new Trazabilidadapoyo();
			     trazabilidadapoyo = trazabilidadapoyoService.findByIdTrazabilidadApoyo(documentoEnviado.getIdTrazabilidadEnvio());
			     iIdDoc = trazabilidadapoyo.getDocumento();
                         }else if(documentoEnviado.getTipoEnvio().equals(Constantes.TIPO_ENVIO_NOTIFICAR)){
			     Notificacion notificacion = new Notificacion();
			     notificacion = notificacionService.buscarObjPorID(documentoEnviado.getIdTrazabilidadEnvio());
			     iIdDoc = notificacion.getIddocumento().getIdDocumento();
                         }
		      }

		      if (getIIdNotificacion() != null) {
			 setIIdDoc(notificacionService.buscarObjPorID(getIIdNotificacion()).getIddocumento().getIdDocumento());
		      }
                        
                      if (idpendientes!=null){
                         DocumentoPendiente documentoPendiente = documentoPendienteDAO.findByIdDocumentoPendiente(idpendientes);
                         iIdDoc = documentoPendiente.getIddocumento();
                      }
                      
                       if (idatendidos!=null){
                         DocumentoAtendido documentoAtendido = documentoAtendidoDAO.findByIdDocumentoAtendido(idatendidos);
                         iIdDoc = documentoAtendido.getIddocumento();
                      }
                      
		      if (getIIdDoc() == null) {
			 return Action.ERROR;
		      }
                      
                      mapSession = ActionContext.getContext().getSession();
                      Usuario objUsuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
                      Usuario u = new Usuario();
                      Trazabilidaddocumento t = new Trazabilidaddocumento();
                      Documento d = documentoService.findByIdDocumento(iIdDoc);
                      u.setIdusuario(objUsuario.getIdUsuarioPerfil());
                      t.setDocumento(d);
                      t.setRemitente(u);
                      t.setDestinatario(u);
                      Usuario usuarioFinal = usuarioService.findByIdUsuario(objUsuario.getIdUsuarioPerfil());
                    
                      if (trazabilidadDocumentoService.contarListTotalTrazabilidadesExpediente(t)==0 && usuarioFinal.getFlagviewtrazabilidad()!=null && usuarioFinal.getFlagviewtrazabilidad().equals("0")){
                           return "sinpermiso";
                      }
                   
		     setObjDD(documentoService.getDocumentDetail(getIIdDoc(), null));

		     if (enVentana != true) {
			enVentana = false;
		     }

			/*desactivado = (!trazabilidadDocumentoService.esPrimeraEtapaExpediente(objDocumento.getExpediente().getId()) || objDocumento.getDocumentoreferencia() != null);
			
                      */
                     return Action.SUCCESS;

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
			return Action.ERROR;
		}
	}

       
	@SuppressWarnings("unchecked")
	public String registrarDIG() throws Exception {
		log.debug("-> [Action] DocumentoAction - registrarDIG():String ");

		if (iIdDocumento == null) {
			log.error("No se recibio ningun ID Documento");

			return Action.ERROR;
		}

		Map<String, List<ArchivoTemporal>> mapUpload = null;
		Usuario objUsuario = null;

		mapSession = ActionContext.getContext().getSession();
		mapUpload = (Map<String, List<ArchivoTemporal>>) mapSession.get(Constantes.SESSION_UPLOAD_LIST);
		objUsuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);

		if (mapUpload == null) {
			log.info("No hay archivos a subir en el registro del Digitalizador");
		}

		documentoService.registrarDIG(iIdDocumento, mapUpload, objUsuario, this.observacionDigitalizador);

		return Action.SUCCESS;
	}

/*
	public String aprobarQAS() {
		log.debug("-> [Action] DocumentoAction - aprobarQAS():String ");

		setMapSession(ActionContext.getContext().getSession());
		if (objDD == null) {
			log.error("No se recogieron datos del formulario. objDD es null");
			return Action.ERROR;
		}

		Usuario usuario = (Usuario) getMapSession().get(Constantes.SESSION_USUARIO);
		if (log.isDebugEnabled()) {
			log.debug("Proceso seleccionado ID [" + getObjDD().getIIdProceso() + "] Nombre [" + getObjDD().getStrUnidad() + "]\nTipo de Documento seleccionado ID [" + objDD.getIIdTipoDocumento() + "]\nCliente seleccionado ID [" + getObjDD().getIIdCliente() + "] Razon Social [" + getObjDD().getStrResponsable() + "] Tipo de Identicacion [" + getObjDD().getIIdTipoIdentificacion() + "]\nAsunto [" + getObjDD().getStrAsunto() + "]\nAccion a tomar [" + getStrAcc() + "]");
		}
		Rol rol = usuario.getRol();
		if (rol != null && rol.getNombre().equals(Constantes.ROL_QAS)) {
			if (objDocumento != null) {
				Expediente expediente = objDocumento.getExpediente();
				if (expediente != null) {
					Cliente cliente = expediente.getCliente();
					if (cliente != null) {
						if (log.isDebugEnabled()) {
							log.debug("Datos del Cliente personalizado");
							log.debug("Razon Social [" + cliente.getRazonSocial() + "]");
							log.debug("Nombres [" + cliente.getNombres() + "]");
							log.debug("Apellido Paterno [" + cliente.getApellidoPaterno() + "]");
							log.debug("Apellido Materno [" + cliente.getApellidoMaterno() + "]");
							log.debug("Representante Legal [" + cliente.getRepresentanteLegal() + "]");
							log.debug("Direccion Principal [" + cliente.getDireccionPrincipal() + "]");
							String direccionAlternativa = cliente.getDireccionAlternativa();
							if (direccionAlternativa != null) {
								log.debug("Direccion Alternativa [" + direccionAlternativa + "]");
								log.debug("Ubigeo Alternativo [" + cliente.getUbigeoAlternativo().getIddistrito() + "]");
							}
							log.debug("Telefono [" + cliente.getTelefono() + "]");
							log.debug("Correo [" + cliente.getCorreo() + "]");
						}
						objDD.setClienterazonsocial(cliente.getRazonSocial());
						objDD.setClientenombres(cliente.getNombres());
						objDD.setClienteapellidopaterno(cliente.getApellidoPaterno());
						objDD.setClienteapellidomaterno(cliente.getApellidoMaterno());
						objDD.setClienterepresentantelegal(cliente.getRepresentanteLegal());
						objDD.setClientedireccionprincipal(cliente.getDireccionPrincipal());
						
						String direccionAlternativa = cliente.getDireccionAlternativa();
						if (direccionAlternativa != null) {
							objDD.setClientedireccionalternativa(direccionAlternativa);
							objDD.setClienteubigeoalternativo(cliente.getUbigeoAlternativo().getIddistrito());
						}
						objDD.setClientetelefono(cliente.getTelefono());
						objDD.setClientecorreo(cliente.getCorreo());
						objDD.setIdSuministros(idSuministros);
						objDD.setIdSubmotivos(idSubmotivos);
						try {
							if (controlDeCalidadService.aprobarDocumento(objDD, usuario, mapSession)) {
								return Action.SUCCESS;
							}
							log.error("Ocurrio un error tratando de aprobar el documento.");
							return Action.ERROR;
						} catch (RemoteException e) {
							e.printStackTrace();
							log.error("Error iniciando proceso en Intalio", e);
							return Action.ERROR;
						} catch (InvalidInputMessageFaultException e) {
							log.error("Parametros pasados al proceso invalidos", e);
							return Action.ERROR;
						} catch (InvalidParticipantTokenFaultException e) {
							log.error("El usuario " + usuario.getUsuario() + " no pudo iniciar sesion en Intalio");
							return Action.ERROR;
						} catch (UnavailableTaskFaultException e) {
							log.error("El proceso seleccionado no se pudo iniciar", e);
							return Action.ERROR;
						} catch (XMLStreamException e) {
							log.error("No se pudo agregar un parametro al iniciar el proceso", e);
							return Action.ERROR;
						}

					}
				}
			}
		}
		log.error("El usuario que intenta aprobar el documento no tiene el rol de Control de Calidad");
		return Action.ERROR;
	}*/



	/**----- Metodo llamado cuando se hace click en Transferir---*/
	public String goderivaruser() throws Exception {
                log.debug("-> [Action] DocumentoAction - goderivaruser():String ");
		setMapSession(ActionContext.getContext().getSession());
                paraAprobar = false;
                Usuario usuarioSesion = (Usuario) getMapSession().get(Constantes.SESSION_USUARIO);
		mostrarEtapa = false;
                
		if (getSOpcion() == null) {
			log.error("NO HAY OPCION");
			return Action.ERROR;
		}
		if (getArrIdDoc() == null || getArrIdDoc().length < 1) {
			log.error("NO HAY DOCUMENTOS A DERIVAR");
			return Action.ERROR;
		}
                
		iIdUpload = 1;
		String strCont = "Remitente:" + usuarioService.findByIdUsuario(usuarioSesion.getIdUsuarioPerfil()).getNombreCompleto() +
			  		" ["+(usuarioSesion.getIdFuncionPerfil() != null ? funcionService.findByIdFuncion(usuarioSesion.getIdFuncionPerfil()).getNombre() : "")+"]";

                
		getMapSession().put("arrIdDoc", getArrIdDoc());
		mapSession.remove(Constantes.SESSION_UPLOAD_LIST);
		parametros = parametroService.findByTipo("PRIORIDAD_DIAS");
               
                if (getSTipoDerivacion().equals(Constantes.DERIVAR_NORMAL)) {
                        Integer iddoc = getArrIdDoc()[0];
			Integer as = documentoService.findPropietarioByIdDocumento(iddoc).getPropietario().getIdusuario();
                        List<String> copia = new ArrayList<String>();
			copia.add("USUARIO_" + as);
			//Integer traza;
			conCopia = copia;
			setIIdDoc(iddoc);
			setObjDD(documentoService.getDocumentDetailOptimized(iddoc, null));
                        Documento doc = documentoService.findByIdDocumento(iddoc);
                        objDD.setStrAccion(doc.getAccion().getNombre());
			objDD.setiIdAccion(doc.getAccion().getIdAccion());
                        
                        List<Trazabilidaddocumento> list =trazabilidadDocumentoService.findByMaxtrazabyIddocumento(iIdDoc); //.get(0).getIdtrazabilidaddocumento();
                        
                        if (list.size()==1 && doc.getDocumentoreferencia()==null){
                             List<Documento> lt = documentoService.consultaDocumentoReferencia(idDocumento);
                             if (lt == null || lt.size()==0){
                                 DocumentoDerivacion documentoDerivacion = new DocumentoDerivacion();
                                 documentoDerivacion.setTipo("P");
                                 documentoDerivacion.setIddocumento(iIdDoc);
                                 List<DocumentoDerivacion> lista = documentoDerivacionDAO.getUsuarioDerivacion(documentoDerivacion);
                        
                                 if (lista!=null && lista.size()>0){
                                    listaDerivacionPara = new ArrayList<UsuarioDerivacion>();
                                    for(int i=0;i<lista.size();i++){
                                        UsuarioDerivacion usuarioDerivacion = new UsuarioDerivacion();
                                        Usuario usuario = usuarioService.findByIdUsuario(lista.get(i).getIdusuario());
                                        usuarioDerivacion.setIdentificador(lista.get(i).getIdusuario() + "-" + lista.get(i).getUnidadpropietario() + "-" + lista.get(i).getCargopropietario());
                                        usuarioDerivacion.setNombreUsuario(usuario.getNombres() + " " + usuario.getApellidos());
                                        listaDerivacionPara.add(usuarioDerivacion);
                                        Favorito existeFavorito = favoritoService.findObjectBy(usuarioSesion.getIdUsuarioPerfil(), Integer.valueOf(lista.get(i).getUnidadpropietario()), Integer.valueOf(lista.get(i).getCargopropietario()), Integer.valueOf(lista.get(i).getIdusuario()), 'U', Constantes.ESTADO_ACTIVO);
                                    
                                        if (existeFavorito == null){
                                            favoritoService.saveFavorito(usuarioSesion.getIdUsuarioPerfil(),Integer.valueOf(lista.get(i).getUnidadpropietario()), Integer.valueOf(lista.get(i).getCargopropietario()), Integer.valueOf(lista.get(i).getIdusuario()), 'U');
                                        }
                                    }
                                }     
                                 
                                documentoDerivacion.setTipo("C");
                                lista = documentoDerivacionDAO.getUsuarioDerivacion(documentoDerivacion);
                                if (lista!=null && lista.size()>0){
                                    listaDerivacionCC = new ArrayList<UsuarioDerivacion>();
                                    for(int i=0;i<lista.size();i++){
                                        UsuarioDerivacion usuarioDerivacion = new UsuarioDerivacion();
                                        Usuario usuario = usuarioService.findByIdUsuario(lista.get(i).getIdusuario());
                                        usuarioDerivacion.setIdentificador(lista.get(i).getIdusuario() + "-" + lista.get(i).getUnidadpropietario() + "-" + lista.get(i).getCargopropietario());
                                        usuarioDerivacion.setNombreUsuario(usuario.getNombres() + " " + usuario.getApellidos());
                                        listaDerivacionCC.add(usuarioDerivacion);
                                        Favorito existeFavorito = favoritoService.findObjectBy(usuarioSesion.getIdUsuarioPerfil(), Integer.valueOf(lista.get(i).getUnidadpropietario()), Integer.valueOf(lista.get(i).getCargopropietario()), Integer.valueOf(lista.get(i).getIdusuario()), 'U', Constantes.ESTADO_ACTIVO);
                                  
                                        if (existeFavorito == null){
                                            favoritoService.saveFavorito(usuarioSesion.getIdUsuarioPerfil(),Integer.valueOf(lista.get(i).getUnidadpropietario()), Integer.valueOf(lista.get(i).getCargopropietario()), Integer.valueOf(lista.get(i).getIdusuario()), 'U');
                                        }    
                                    }
                                }     
                             }
                        }
                        
                        lstTrazabilidadCopia = trazabilidadcopiaService.buscarUsuarioCopia(objDD.getIIdDocumento(),list.get(0).getIdtrazabilidaddocumento());
			StringBuilder cadenaCC = new StringBuilder();
                       
                        if(lstTrazabilidadCopia!=null && lstTrazabilidadCopia.size()>0){
                           for(int i=0; i<lstTrazabilidadCopia.size(); i++){
			    if(i!=0) cadenaCC.append(", ");
			      cadenaCC.append( lstTrazabilidadCopia.get(i).getDestinatario().getApellidos()+" "+lstTrazabilidadCopia.get(i).getDestinatario().getNombres());
			   }
			}

                        objDD.setUnidadRemitente(list.get(0).getUnidadremitente().toString());
                        objDD.setUnidadDestinatario(list.get(0).getUnidaddestinatario().toString());
			objDD.setCadenaCC(cadenaCC.toString());	
		        getObjDD().setStrContenido("");
			setIIdDoc(getObjDD().getIIdDocumento());
			setTa(strCont);
                        Trazabilidaddocumento objTrazabilidad = trazabilidadDocumentoService.findRemitenteAccionByIddoc(iIdDoc);
                        objDD.setiIdRemitente(objTrazabilidad.getRemitente().getIdusuario());
			
                        if (getSOpcion().equals(Constantes.RECHAZAR)) {
                           Trazabilidaddocumento trazabilidaddocumento = trazabilidadDocumentoService.findByMaxNroRegistro(getIIdDoc(), "", accionService.findByNombre("reenviar").getIdAccion(), usuarioSesion.getIdUsuarioPerfil(), usuarioSesion.getIdUnidadPerfil(), usuarioSesion.getIdFuncionPerfil());
			   setIddestinatario(trazabilidaddocumento.getRemitente().getIdusuario() + "-" + trazabilidaddocumento.getUnidadremitente() + "-" + trazabilidaddocumento.getCargoremitente());	
                           mostrarEtapa = false;
			   Accion accion = accionService.findByNombre(sOpcion);
                           
                           objDD.setStrAccion(accion.getNombre());
                           objDD.setiIdAccion(accion.getIdAccion());
                        }else{
                            if (list!=null && list.size()==1){
                               objDD.setPrioridad(doc.getPrioridad());
                               objDD.setDateFechaLimiteAtencion(doc.getFechaLimiteAtencion());
                            }else{
                               if (list==null || list.get(0)==null || list.get(0).getPrioridad() == null){ 
                                  objDD.setPrioridad(doc.getPrioridad()); 
                               }else{    
                                  objDD.setPrioridad(list.get(0).getPrioridad());
                               }        
                               objDD.setDateFechaLimiteAtencion(list.get(0).getFechalimiteatencion());
                            }
                            
                            documentosReferencia = documentoReferenciaService.getDocumentoDerivarAtender(usuarioSesion, doc.getIdDocumento());
                          
                           
                            if (documentosReferencia!=null && documentosReferencia.size()>0){
                               for(int i=0;i<documentosReferencia.size();i++){
                                   if (doc.getBandeja()!=null && !doc.getBandeja().equals("") && !documentosReferencia.get(i).getDocumentoReferencia().getIdDocumento().toString().equals(doc.getBandeja().toString())){
                                       documentosReferencia.remove(i);
                                   }
                               }
                            }
                        }
		} 

		return Action.SUCCESS;
		
	}

	/**REN Llamado cuando un usuario de apoyo hace click en Aprobar o Rechazar. Inicializa el PopUp*/
	public String goDerivarApoyo(){
		log.debug("-> [Action] DocumentoAction - goDerivarApoyo():String ");

		setMapSession(ActionContext.getContext().getSession());
		Usuario usuarioSesion = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
		usuarioSesion = usuarioService.findByIdUsuario(usuarioSesion.getIdusuario());

	        mostrarEtapa = false;

		String strCont = "<strong>Observaciones :</strong> ";
		iIdUpload = 1;

		getMapSession().put("arrIdDoc", getArrIdDoc());
		mapSession.remove(Constantes.SESSION_UPLOAD_LIST);
		sTipoDerivacion = Constantes.DERIVAR_NORMAL;

		iIdDoc = getArrIdDoc()[0];
		objDocumento = documentoService.findByIdDocumento(iIdDoc);
                        
                objDD = documentoService.getDocumentDetailOptimized(objDocumento.getDocumentoreferencia(), null);
                getObjDD().setStrContenido(strCont);
		setTa(strCont);

                Trazabilidadapoyo traza = trazabilidadapoyoService.buscarUltimaDelegacionUsuario(objDocumento);
                iddestinatario = traza.getRemitente().getIdusuario() + "-" + traza.getUnidadremitente() + "-" + traza.getCargoremitente();
		sNombres = traza.getRemitente().getNombres() + " " + traza.getRemitente().getApellidos();

		return Action.SUCCESS;
	}

	/**REN abre la ventana de Enviar Multiple*/
        
        public String goCopiarApoyoCarga(){
            cerrar = "cerrar";
            return Action.SUCCESS;
        }
        
	public String goCopiarApoyo(){
                log.debug("-> [Action] DocumentoAction - goCopiarApoyo():String ");
		setMapSession(ActionContext.getContext().getSession());
                Usuario objUsuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
                parametros = parametroService.findByTipo("PRIORIDAD_DIAS");
                
                ta =  "Remitente:" + usuarioService.findByIdUsuario(objUsuario.getIdUsuarioPerfil()).getNombreCompleto() +
			  		" ["+(objUsuario.getIdFuncionPerfil() != null ? funcionService.findByIdFuncion(objUsuario.getIdFuncionPerfil()).getNombre() : "")+"]";
		
		 
		iIdDoc = getArrIdDoc()[0];
		documento = documentoService.findByIdDocumento(iIdDoc);
		proveidos = proveidoService.buscarProveidosActivos(); 
                
                if (documento.getDocumentoreferencia()!=null){
                     setObjDD(documentoService.getDocumentDetailOptimized(documento.getDocumentoreferencia(), ""));
                }else{
                     setObjDD(documentoService.getDocumentDetailOptimized(documento.getIdDocumento(), ""));
                } 
                 
                documentosReferencia = documentoReferenciaService.getDocumentoDerivarAtender(objUsuario, documento.getIdDocumento());
	        if (documentosReferencia!=null && documentosReferencia.size()>0){
                    for(int i=0;i<documentosReferencia.size();i++){
                      if (documento.getBandeja()!=null && !documento.getBandeja().equals("") && !documentosReferencia.get(i).getDocumentoReferencia().getIdDocumento().toString().equals(documento.getBandeja().toString())){
                            documentosReferencia.remove(i);
                      }
                    }
                 }
                 
                if (documento.getDocumentoreferencia()!=null){
                  Trazabilidadapoyo tapoyo = null;          
                  Usuarioxunidadxfuncion usuarioxunidadxfuncion  = new Usuarioxunidadxfuncion();
                  usuarioxunidadxfuncion.setIdusuario(objUsuario.getIdUsuarioPerfil());
                  usuarioxunidadxfuncion.setIdunidad(objUsuario.getIdUnidadPerfil());
                  usuarioxunidadxfuncion.setIdfuncion(objUsuario.getIdFuncionPerfil());
                 
                  try{
                      tapoyo = trazabilidadapoyoService.buscarUltimaDelegacionUsuario(usuarioxunidadxfuncion, iIdDoc);
                      if (tapoyo!=null){
                         if (tapoyo.getPrioridad()!=null)
                           objDD.setPrioridad(tapoyo.getPrioridad());
                         else
                           objDD.setPrioridad(documento.getPrioridad());  
                   
                         objDD.setDateFechaLimiteAtencion(tapoyo.getFechalimiteatencion());
                      }else{
                         objDD.setPrioridad(new Integer(Constantes.PRIORIDAD_NORMAL)); 
                      }   
                  }catch(Exception e){
                      objDD.setPrioridad(documento.getPrioridad());
                      objDD.setDateFechaLimiteAtencion(documento.getFechaLimiteAtencion());
                  }    
                  
                }else{
                  objDD.setPrioridad(documento.getPrioridad());
                  objDD.setDateFechaLimiteAtencion(documento.getFechaLimiteAtencion());
                }
                
                return Action.SUCCESS;
	}

	/**REN genera las copias de apoyo para los usuarios seleccionados (envia el multiple)*/
	public String copiarApoyo(){
	    try{
                mapSession = ActionContext.getContext().getSession();
		String nombrePC = (String) mapSession.get("nombrePC");
	        ta = ta != null ? ta.trim() : "";
		Documento documento = documentoService.findByIdDocumento(iIdDoc);
		Usuario usuarioSesion = (Usuario) getMapSession().get(Constantes.SESSION_USUARIO);
              
                if(apoyo != null && apoyo.length >0){
		  List<Usuario> usuariosNotificados = null;// new ArrayList<Usuario>();

                  for(String idUsuario : apoyo){
		     String[] datosUsuarios = idUsuario.split("-");
                     usuariosNotificados = new ArrayList<Usuario>();
                             
                     Usuarioxunidadxfuncion usuarioDestinatario = new Usuarioxunidadxfuncion();
                     usuarioDestinatario.setIdusuario(new Integer(datosUsuarios[0]));
                     usuarioDestinatario.setIdunidad(new Integer(datosUsuarios[1]));
                     usuarioDestinatario.setIdfuncion(new Integer(datosUsuarios[2]));
                                        
                     Usuario usuarioNotificado = usuarioService.findByIdUsuario(new Integer(datosUsuarios[0]));
                     usuarioNotificado.setIdUnidadPerfil(new Integer(datosUsuarios[1]));
                     String[] txtAcciones = null;
					
		     if(strAcciones != null){
			for(String fila : strAcciones){
			   String[] lista = StringUtil.stringToArray(fila);
                           if(lista[0] != null && !lista[0].equals("")){
                              if (lista[0].equals(idUsuario)){
                                txtAcciones = lista;
                                Proveido proveido =proveidoService.buscarPorId(new Integer(txtAcciones[1]));
                                usuarioNotificado.setProveido(proveido.getNombre());
                                break;
			      }
			   }
			}
		     }
                    
                     usuariosNotificados.add(usuarioNotificado);
                     documentoService.crearCopiaApoyo(documento, objDD, usuarioSesion, usuarioDestinatario, txtAcciones, Integer.parseInt(strPrioridad), ta, (String)mapSession.get("nombrePC"),fechaLimite.validarHorarioPermitido(usuarioSesion), fechaLimite.validarHorarioPermitidoRecepcion(usuarioDestinatario), usuariosNotificados, codigoVirtual);		    
		   }
                  
                   try{
                        if (strSeguimiento!=null && !strSeguimiento.equals("") && strSeguimiento.equals("1")){
                            SeguimientoXUsuario seguimiento = new SeguimientoXUsuario();
                            seguimiento.setIdDocumento(iIdDoc);
                            seguimiento.setIdUsuario(usuarioSesion.getIdUsuarioPerfil());
                            seguimiento.setUnidadPropietario(usuarioSesion.getIdUnidadPerfil());
                            seguimiento.setCargoPropietario(usuarioSesion.getIdFuncionPerfil());
                            seguimiento.setEstado("A");
                            seguimiento.setFechaCreacion(new Date());
                            seguimiento.setUsuarioCreacion(usuarioSesion.getIdusuario());
                            seguimientoXUsuarioService.guardarSeguimiento(seguimiento);
                        }    
                   }catch(Exception e){
                       e.printStackTrace();
                   }
                   
                   try{
                        if (conCopia != null) {
                            for (String sID : conCopia) {
                             if (!StringUtil.isEmpty(sID)) {
                                 String[] datosCopia = sID.split("-");
                                 Integer iID = Integer.valueOf(datosCopia[0]);
                                 Usuario usuarioReceptor = usuarioService.findByIdUsuario(new Integer(datosCopia[0]));
                                 usuarioReceptor.setIdUnidadPerfil(new Integer(datosCopia[1]));
                                 usuarioReceptor.setIdFuncionPerfil(new Integer(datosCopia[2]));
                                 if (!usuariosNotificados.contains(usuarioReceptor)) {
                                     notificacionService.enviarNotificacion(usuarioSesion, usuarioReceptor, documento, Constantes.TIPO_NOTIFICACION_DERIVACIONMULTIPLECONCOPIA, nombrePC, true,null, ta);
                                     mailService.ChaskiMail(Constantes.CONFIGNOTIFMAIL_DOCUMENTO_CCREENVIAR, usuarioSesion, usuarioReceptor, documento, ta ,"");
                                 }else {
                                     notificacionService.updateTipoNotificacion(documento.getIdDocumento(), usuarioReceptor.getIdusuario(), Constantes.TIPO_NOTIFICACION_DERIVACIONCONCOPIA);
                                 }
                              }
                            }
                         }
                   }catch(Exception e){
                       e.printStackTrace();
                   }
                  
                   try{
                        String[] listaReferencias = StringUtil.stringToArray(strReferencia);
                        if (listaReferencias!=null && listaReferencias.length>0){
                           String[] arreglo = new String[1];
                           for(int i=0;i<listaReferencias.length;i++){
                              Documento d = documentoService.findByIdDocumento(new Integer(listaReferencias[i]));
                              arreglo[0] = documento.getIdDocumento().toString();
                              
                              if (d.getEstado()!='I' && d.getEstado()!='N' && d.getEstado()!='T' && d.getFlagMultiple()==null && d.getFlagatendido() ==null && d.getPropietario().getIdusuario().toString().equals(usuarioSesion.getIdUsuarioPerfil().toString()))
                                  documentoService.atenderDocumento(usuarioSesion, d, null, false, null, nombrePC, arreglo, "", null);
                           }
                        }
                   }catch(Exception e){
                       e.printStackTrace();
                   }  
		}
                
                 cerrar = "cerrar";
		}catch(Exception e){
                    e.printStackTrace();
                    return Action.ERROR;
                }
            
               return Action.SUCCESS;
	}

	/**REN Manda el documento a uno o varios usuarios, maneja solo las remisiones del documento y no puede mandarse copias ----------*


	/**REN Devuelve el documento al usuario original, simplemente elimina la copia y crea una notificacion -------------------*/
	public String finalizarApoyo(){
		log.debug("-> [Action] DocumentoAction - finalizarApoyo():String ");
                mapSession = ActionContext.getContext().getSession();
                Usuario usuarioSesion = (Usuario) getMapSession().get(Constantes.SESSION_USUARIO);
		Documento documento = documentoService.findByIdDocumento(iIdDoc);
		Documento docOriginal = documentoService.findByIdDocumento(documento.getDocumentoreferencia());
		docOriginal.setObservacion(objDD.getStrTexto());
                Map<String, Object> session = ActionContext.getContext().getSession();
		String[] objUsuarioDestinatario = iddestinatario.split("-");
                Usuario usuarioReceptor = usuarioService.findByIdUsuario(new Integer(objUsuarioDestinatario[0]));
                usuarioReceptor.setIdUnidadPerfil(new Integer(objUsuarioDestinatario[1]));
                usuarioReceptor.setIdFuncionPerfil(new Integer(objUsuarioDestinatario[2]));
                Usuario u = new Usuario();
                Unidad u_ = new Unidad(usuarioSesion.getIdUnidadPerfil());
                u.setUnidad(u_);
                notificacionService.enviarNotificacion(usuarioSesion, usuarioReceptor, docOriginal, Constantes.TIPO_NOTIFICACION_FIN_APOYO, (String)session.get("nombrePC"),fechaLimite.validarHorarioPermitido(u),documento, null);
		documentoService.guardarTrazaFinalizarApoyo(documento, Constantes.ESTADO_CODIGO_RESPONDIDO);
     	        documento.setEstado(Constantes.ESTADO_ATENDER);
                documentoService.guardarDocumento(documento);
                cerrar = "S";

		return Action.SUCCESS;
	}

	public String mostrarDerivacionMasiva() throws Exception {
		log.debug("-> [Action] DocumentoAction - mostrarDerivacionMasiva():String ");
               
                mostrarEtapa = false;
		if (sOpcion == null) {
			log.error("NO HAY OPCION");
			return Action.ERROR;
		}
		if (arrIdDoc == null || arrIdDoc.length < 1) {
			log.error("NO HAY DOCUMENTOS A DERIVAR");
			return Action.ERROR;
		}
		iIdUpload = 1;
		setMapSession(ActionContext.getContext().getSession());
		getMapSession().put("arrIdDoc", arrIdDoc);
		mapSession.remove(Constantes.SESSION_UPLOAD_LIST);
		Usuario usuarioSesion = (Usuario) getMapSession().get(Constantes.SESSION_USUARIO);
		
		if (sTipoDerivacion.equals(Constantes.DERIVAR_MASIVO)) {
                    String strCont = "Remitente:" + usuarioService.findByIdUsuario(usuarioSesion.getIdUsuarioPerfil()).getNombreCompleto() +
			  		" ["+(usuarioSesion.getIdFuncionPerfil() != null ? funcionService.findByIdFuncion(usuarioSesion.getIdFuncionPerfil()).getNombre() : "")+"]";
                    setTa(strCont);
                    flagMensaje=true;
		    return "masivo";
		}


		return Action.SUCCESS;
	}
        
        public String mostrarDerivacionMasivaDoc() throws Exception {
		log.debug("-> [Action] DocumentoAction - mostrarDerivacionMasivaDoc():String ");
               
                mostrarEtapa = false;
		if (sOpcion == null) {
                     log.error("NO HAY OPCION");
		     return Action.ERROR;
		}
		if (arrIdDoc == null || arrIdDoc.length < 1) {
			log.error("NO HAY DOCUMENTOS A DERIVAR");
			return Action.ERROR;
		}
              
                iIdUpload = 1;
		setMapSession(ActionContext.getContext().getSession());
		getMapSession().put("arrIdDoc", arrIdDoc);
		mapSession.remove(Constantes.SESSION_UPLOAD_LIST);
		Usuario usuarioSesion = (Usuario) getMapSession().get(Constantes.SESSION_USUARIO);
		
		if (sTipoDerivacion.equals(Constantes.DERIVAR_MASIVO_DOC)) {
                    String strCont = "Remitente:" + usuarioService.findByIdUsuario(usuarioSesion.getIdUsuarioPerfil()).getNombreCompleto() +
			  		" ["+(usuarioSesion.getIdFuncionPerfil() != null ? funcionService.findByIdFuncion(usuarioSesion.getIdFuncionPerfil()).getNombre() : "")+"]";
                    setTa(strCont);
                    flagMensaje=true;
		    return "masivo";
		}


		return Action.SUCCESS;
	}

	public String gorechazarUser() {
		log.debug("-> [Action] DocumentoAction - gorechazarUser():String ");
               
		return Action.SUCCESS;
	
	}

	@SuppressWarnings({ "unchecked", "rawtypes"})
	public String derivaruser() {
                log.debug("-> [Action] DocumentoAction - derivaruser():String ");
		String[] acciones = StringUtil.stringToArray(strAcc);
		String tipoRetorno = Action.SUCCESS;
                String[] objUsuarioDestinatario = getIddestinatario().split("-");
                
                Usuarioxunidadxfuncion usuarioDestinatario = new Usuarioxunidadxfuncion();
                usuarioDestinatario.setIdusuario(new Integer(objUsuarioDestinatario[0]));
                usuarioDestinatario.setIdunidad(new Integer(objUsuarioDestinatario[1]));
                usuarioDestinatario.setIdfuncion(new Integer(objUsuarioDestinatario[2]));
                
                try {
                        mapSession = ActionContext.getContext().getSession();
                        Usuario usuarioSesion = (Usuario) getMapSession().get(Constantes.SESSION_USUARIO);
			arrIdDoc = (Integer[]) mapSession.get("arrIdDoc");
			String nombrePC = (String)mapSession.get("nombrePC");
                        Usuario destinatario = usuarioService.findByIdUsuario(usuarioDestinatario.getIdusuario());
                        
                        if(destinatario == null || destinatario.getEstado().equals(String.valueOf(Constantes.ESTADO_INACTIVO))){
			  return "errorUsuario";
			}
                        
                        if (getSTipoDerivacion().equals(Constantes.DERIVAR_NORMAL)) {
                           if (objDD.getStrAccion().equals(Constantes.ACCION_REENVIAR)) {
                		documento = null;
				if(strAcciones != null && strAcciones[0] != null){
                                    getObjDD().setStrContenido(strAcciones[0] + getObjDD().getStrTexto());
				}else{
                                    getObjDD().setStrContenido(getObjDD().getStrTexto());
				}

				if (this.sOpcion.equals(Constantes.ACCION_REENVIAR)) {
                                    log.debug("Accion::Transferir");
				    Accion objAccion = accionService.findByNombre(objDD.getStrAccion());
				    documento = documentoService.findByIdDocumento(objDD.getIIdDocumento());
				    documento.setAccion(objAccion);
				    documento.setLeido(Constantes.ESTADO_NO_LEIDO);
                                  
                                    Usuario u = new Usuario();
                                    Unidad u_ = new Unidad(usuarioSesion.getIdUnidadPerfil());
                                    u.setUnidad(u_);     
                                    documento = documentoService.derivarDocumento(getObjDD(), usuarioSesion, usuarioDestinatario, sTipoDerivacion, null, conCopia, acciones, documento, nombrePC,fechaLimite.validarHorarioPermitido(u), fechaLimite.validarHorarioPermitidoRecepcion(usuarioDestinatario), codigoVirtual);    
                               
                                    try{
                                        String[] listaReferencias = StringUtil.stringToArray(strReferencia);
                                        if (listaReferencias!=null && listaReferencias.length>0){
                                            String[] arreglo = new String[1];
                                            for(int i=0;i<listaReferencias.length;i++){
                                              Documento d = documentoService.findByIdDocumento(new Integer(listaReferencias[i]));
                                              arreglo[0] = documento.getIdDocumento().toString();
                                              if (d.getEstado()!='I' && d.getEstado()!='N' && d.getEstado()!='T' && d.getFlagMultiple()==null && d.getFlagatendido() ==null && d.getPropietario().getIdusuario().toString().equals(usuarioSesion.getIdUsuarioPerfil().toString()))
                                                 documentoService.atenderDocumento(usuarioSesion, d, null, false, null, nombrePC, arreglo, "", null);
                                            }
                                        }
                                    }catch(Exception e){
                                        e.printStackTrace();
                                    }
                                    
                                    try{
                                         if (strSeguimiento!=null && !strSeguimiento.equals("") && strSeguimiento.equals("1")){
                                             SeguimientoXUsuario seguimiento = new SeguimientoXUsuario();
                                             seguimiento.setIdDocumento(objDD.getIIdDocumento());
                                             seguimiento.setIdUsuario(usuarioSesion.getIdUsuarioPerfil());
                                             seguimiento.setUnidadPropietario(usuarioSesion.getIdUnidadPerfil());
                                             seguimiento.setCargoPropietario(usuarioSesion.getIdFuncionPerfil());
                                             seguimiento.setEstado("A");
                                             seguimiento.setFechaCreacion(new Date());
                                             seguimiento.setUsuarioCreacion(usuarioSesion.getIdusuario());
                                             seguimientoXUsuarioService.guardarSeguimiento(seguimiento);
                                         }    
                                    }catch(Exception e){
                                        e.printStackTrace();
                                    }
                                    
                                    try{
                                        List<Trazabilidaddocumento> lstTraza = trazabilidadDocumentoService.findByIdDocumento(documento.getIdDocumento());
                                                
                                        if (documento.getID_EXTERNO().toString().equals("1") && lstTraza.size()<3){
                                            for(int k=0;k<3;k++){
                                                List<Parametro> lst = null;
                                                List<Parametro> lstusu = null;
                                                int evento = 0;
                                                
                                                if (k==0){
                                                    lst = parametroService.findByTipo("CLIENTE_MP_DOC_MTC");
                                                    lstusu = parametroService.findByTipo("USUARIO_MP_DOC_MTC"); 
                                                    evento = Constantes.CONFIGNOTIFMAIL_DOCUMENTO_PARTICULAR_MTC;
                                                }
                                                if (k==1){
                                                    lst = parametroService.findByTipo("CLIENTE_MP_DOC_CR");
                                                    lstusu = parametroService.findByTipo("USUARIO_MP_DOC_CR");
                                                    evento = Constantes.CONFIGNOTIFMAIL_DOCUMENTO_PARTICULAR_CR;
                                                }
                                                if (k==2){
                                                    lst = parametroService.findByTipo("CLIENTE_MP_DOC_PCM");
                                                    lstusu = parametroService.findByTipo("USUARIO_MP_DOC_PCM");
                                                    evento = Constantes.CONFIGNOTIFMAIL_DOCUMENTO_PARTICULAR_PCM;
                                                }
                                                
                                                
                                                boolean bandera = false;

                                                if (lst!=null){
                                                  for(int i=0;i<lst.size();i++){
                                                     if (documento.getID_CLIENTE()!=null && lst.get(i).getValor().equals(documento.getID_CLIENTE().toString())){
                                                        bandera = true;
                                                        break;
                                                     }    
                                                  }

                                                  if (bandera){
                                                      Proveido p = proveidoDAO.buscarPorId(new Integer(acciones[0]));

                                                      if (lstusu!=null){
                                                         for(int i=0;i<lstusu.size();i++){ 
                                                             Documento d = new Documento();
                                                             Usuario   usuario = new Usuario(new Integer(lstusu.get(i).getValor()));
                                                             usuario = usuarioService.findByIdUsuario(usuario.getIdusuario());
                                                             d.setIdDocumento(documento.getIdDocumento());
                                                             d.setPropietario(usuario);
                                                             d.setAsunto(documento.getAsunto());
                                                             d.setExpediente(documento.getExpediente());
                                                             d.setUnidadpropietario(usuario.getUnidad().getIdunidad());
                                                             d.setTipoDocumento(documento.getTipoDocumento());
                                                             d.setNumeroDocumento(documento.getNumeroDocumento());
                                                             d.setID_CODIGO(documento.getID_CODIGO());
                                                             notificacionService.informarViaNotifAndMail(usuarioSesion, d, evento, Constantes.TIPO_NOTIFICACION_DERIVACION, nombrePC, objDD.getStrContenido(), p.getNombre());   
                                                         }    
                                                      }
                                                  }
                                                }
                                            }    
                                        }
                                    }catch(Exception e){
                                        e.printStackTrace();
                                    }
                                }
			   }else if (objDD.getStrAccion().equals(Constantes.ACCION_RECHAZAR)) {
                		    Documento documento = documentoService.findByIdDocumento(objDD.getIIdDocumento());
				    documento.setLeido(Constantes.ESTADO_NO_LEIDO);
				    if (documento != null) {
                                          Usuario u = new Usuario();
                                          Unidad u_ = new Unidad(usuarioSesion.getIdUnidadPerfil());
                                          u.setUnidad(u_);  
					  documentoService.rechazarDocumento(usuarioSesion, usuarioDestinatario, documento, objDD.getStrAsunto(), objDD.getStrTexto(), Constantes.ACCION_RECHAZAR, nombrePC, fechaLimite.validarHorarioPermitido(u), fechaLimite.validarHorarioPermitidoRecepcion(usuarioDestinatario), objDD, codigoVirtual);
				    }
				}
			} else if (getSTipoDerivacion().equals(Constantes.DERIVAR_MASIVO)) {
				log.debug("DERIVAR_MASIVO");
				setMapSession(ActionContext.getContext().getSession());
				Usuario objUsuario = null;
				objUsuario = (Usuario) getMapSession().get(Constantes.SESSION_USUARIO);
                                getObjDD().setStrContenido(objDD.getStrTexto());
                                Usuario u = new Usuario();
                                Unidad u_ = new Unidad(usuarioSesion.getIdUnidadPerfil());
                                u.setUnidad(u_);
                                documentoService.derivarDocumentoMasivo(arrIdDoc, documento, objUsuario, usuarioDestinatario, getObjDD(), nombrePC , fechaLimite.validarHorarioPermitido(u), fechaLimite.validarHorarioPermitidoRecepcion(usuarioDestinatario), null);		
				tipoRetorno = "masivo";
			} else if (getSTipoDerivacion().equals(Constantes.DERIVAR_MASIVO_DOC)) {
				log.debug("DERIVAR_MASIVO_DOC");
				setMapSession(ActionContext.getContext().getSession());
				Usuario objUsuario = null;
				objUsuario = (Usuario) getMapSession().get(Constantes.SESSION_USUARIO);
                                getObjDD().setStrContenido(objDD.getStrTexto());
                                Usuario u = new Usuario();
                                Unidad u_ = new Unidad(usuarioSesion.getIdUnidadPerfil());
                                u.setUnidad(u_);
                                documentoService.derivarDocumentoMasivo(arrIdDoc, documento, objUsuario, usuarioDestinatario, getObjDD(), nombrePC , fechaLimite.validarHorarioPermitido(u), fechaLimite.validarHorarioPermitidoRecepcion(usuarioDestinatario), strAcc);		
				tipoRetorno = "masivo";
			}
                        
                      
                        provieneDeMail = (Boolean) mapSession.get("provieneDeMail");
                       if (provieneDeMail != null && provieneDeMail) {
				if (mapSession instanceof org.apache.struts2.dispatcher.SessionMap) {
					mapSession.remove(Constantes.SESSION_ALFRESCO);
					mapSession.remove(Constantes.SESSION_AUDITABLE);
					mapSession.remove(Constantes.SESSION_FORWARD_TO_URL);
					mapSession.remove(Constantes.SESSION_IDDOCUMENTO);
					mapSession.remove(Constantes.SESSION_RECURSO);
					mapSession.remove(Constantes.SESSION_UPLOAD_LIST);
					mapSession.remove(Constantes.SESSION_USUARIO);
					mapSession.remove("provieneDeMail");
					((org.apache.struts2.dispatcher.SessionMap) mapSession).invalidate();
				}
			}

                        this.cerrar = "OK";
			return tipoRetorno;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
                        return Action.ERROR;
		}
	}

	@SuppressWarnings("unchecked")
	public String goSubirRepositorio() throws Exception {
		log.debug("-> [Action] DocumentoAction - goSubirRepositorio():String ");

		// log.debug("texto ingresado [" + getObjDD().getStrTexto() + "]");
		//try{
		setMapSession(ActionContext.getContext().getSession());
		log.debug("UPLOAD TO ALFRESCO ");
		// Documento objDocumentoPrincipal=null;
		Map<String, List<ArchivoTemporal>> mapUpload = null;
		Usuario objUsuario = null;
		setMapSession(ActionContext.getContext().getSession());
		mapUpload = (Map<String, List<ArchivoTemporal>>) getMapSession().get(Constantes.SESSION_UPLOAD_LIST);
		objUsuario = (Usuario) getMapSession().get(Constantes.SESSION_USUARIO);
		DocumentoDetail docdetail = new DocumentoDetail();
		docdetail.setIIdDocumento(this.getIIdDocumento());
		// getObjDD().setStrContenido(getTa());
		if (mapUpload == null) {
			log.info("No hay archivos a subir al Alfresco");
			// objDocumentoPrincipal = getSrvD().derivarUSER(getObjDD(),
			// objUsuario, getIddestinatario());
		} else {
			documentoService.crearDocumentoPorArchivo(docdetail, objUsuario, mapUpload, new Boolean(false), true, Constantes.MODULO_USUARIO_FINAL, Constantes.OPCION_SUBIR_REPOSITORIO, false);
			this.iIdDoc = this.iIdDocumento;
			this.cerrar = "OK";
			return Action.SUCCESS;
		}
		return Action.ERROR;
		/*}catch(Exception e){
        log.error(e.getMessage(),e);
        return Action.ERROR;
        }*/
	}

	public String rechazarqas() throws Exception {
		log.debug("-> [Action] DocumentoAction - rechazarqas():String ");

		if (objDD == null) {
			log.debug("No se recibio documento a rechazar");
			return Action.ERROR;
		}
		log.debug("Documento a rechazar con ID [" + objDD.getIIdDocumento() + "]");
		log.debug("Observacion de rechazo [" + objDD.getSObservacionRechazo() + "]");
		// try{
		Usuario objUsuario = null;
		mapSession = ActionContext.getContext().getSession();
		objUsuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
		//documentoService.rechazarQAS(objDD.getIIdDocumento(), objDD.getSObservacionRechazo(), objUsuario);
		cerrar = "OK";
		return Action.SUCCESS;
		
	}

	@SuppressWarnings("unused")
	public String nuevoDocumentoFedatear() throws Exception {
		try{

			mapSession = ActionContext.getContext().getSession();
			Usuario objUsuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);

			objDF.setIdUsuario(objUsuario.getIdusuario());
			objDF.setFechaDocumento(null);
			objDF.setNombrePC((String)mapSession.get("nombrePC"));
			objDF.setEstado("A");

			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date date = null;

			if (objDF.getFechaDocumentoRegistro()!=null && !objDF.getFechaDocumentoRegistro().trim().equals("")){
				date = formatter.parse(objDF.getFechaDocumentoRegistro());
				objDF.setFechaDocumento(date);
			}

			documentoService.registrarDocumentoFedatario(objDF);

			return Action.SUCCESS;
		}catch(Exception e){
			return "error";
		}
	}

	/**REN: Este metodo es el "Guardar" cuando se crea un nuevo Expediente en Usuario Final ----------------------------------*/
	@SuppressWarnings("unused")
	public String enviarArchivo() throws Exception {
                log.debug("-> [Action] DocumentoAction - enviarArchivo():String ");
		mapSession = ActionContext.getContext().getSession();
		Map<String, Object> session = ActionContext.getContext().getSession();

                try {
                        strAcc = Constantes.ACCION_REGISTRAR;
                       
			if(objDD.getConfidencial() == null){
				objDD.setConfidencial(Constantes.No);
			}
                        
                        getObjDD().setIIdTipoDocumento(getIdtipodocumento());
                        getObjDD().setCEstado(Constantes.ESTADO_ACTIVO);
                        getObjDD().setStrTipoDocumento(tipoDocumentoService.findByIdTipoDocumento(getIdtipodocumento()).getNombre());
			objDD.setEnumerarDocumento(StringUtil.isEmpty(objDD.getEnumerarDocumento()) ? "N" : objDD.getEnumerarDocumento());
			
                        if (getObjDD().getIIdExpediente() == null) {
				getObjDD().setCPrincipal(Constantes.DOCUMENTO_PRINCIPAL);
			} else {
				getObjDD().setCPrincipal(Constantes.DOCUMENTO_NO_PRINCIPAL);
			}
			
                        Usuario objUsuario = (Usuario) session.get(Constantes.SESSION_USUARIO);
			objDD.setIdUsuarioLogeado(objUsuario.getIdusuario());
			
                        if (getObjDD() != null && (getObjDD().getTipoTransaccion().equals("N") || getObjDD().getTipoTransaccion().equals("A") || getObjDD().getTipoTransaccion().equals("R"))) {
                            objDD = documentoService.saveNuevoDocumentoUserFinal(objDD, session, iddestinatario, idccdestinatario, strAcc, bBandeja, archivopendiente, (String)session.get("nombrePC"), "ES_nombrePDFprincipal");
			} else {
                             if (getObjDD() != null && (getObjDD().getTipoTransaccion().equals("M") || getObjDD().getTipoTransaccion().equals("MR"))){
                               objDD = documentoService.updateDocumentoUserFinal(objDD, session, iddestinatario, idccdestinatario, strAcc, bBandeja, archivopendiente, (String)session.get("nombrePC"), "ES_nombrePDFprincipal");                     
                             }else{
                               this.mensaje = "No se encontraron Datos";
                             }        
			}
                        
                        this.cerrar = "ok";
			this.ocultar = "NO";

			return Action.SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}


	public String uploadWithForm() throws Exception {
		log.debug("-> [Action] DocumentoAction - uploadWithForm():String ");

		// try{
		setMapSession(ActionContext.getContext().getSession());
		log.debug("Removiendo archivos adjuntos de la sesion");
		getMapSession().remove(Constantes.SESSION_UPLOAD_LIST);
		return Action.SUCCESS;
		/*
		 * }catch(Exception e){ log.error(e.getMessage(),e); return
		 * Action.ERROR; }
		 */
	}


	public String gobandejanuevoExpediente() throws Exception {
		log.debug("-> [Action] DocumentoAction - gobandejanuevoExpediente():String ");

		// try{
		setMapSession(ActionContext.getContext().getSession());
		log.debug("Removiendo archivos adjuntos de la sesion");
		if (getSFromBandeja() != null && getSFromBandeja().equals("si")) {
			getMapSession().remove("documentotemporal");
		}
		return Action.SUCCESS;
		/*
		 * }catch(Exception e){ log.error(e.getMessage(),e); return
		 * Action.ERROR; }
		 */
	}

	@SuppressWarnings("unchecked")
	public String doUploadWithForm() throws Exception {
		log.debug("-> [Action] DocumentoAction - doUploadWithForm():String ");

		// try{
		Map<String, List<ArchivoTemporal>> mapUpload = null;
		List<DocumentoTemporal> lstDocumentoTemporal = null;
		List<ArchivoTemporal> lstArchivoTemporal = null;
		setMapSession(ActionContext.getContext().getSession());
		mapUpload = (Map<String, List<ArchivoTemporal>>) getMapSession().get(Constantes.SESSION_UPLOAD_LIST);
		lstDocumentoTemporal = (List<DocumentoTemporal>) getMapSession().get("documentotemporal");
		if (mapUpload == null) {
			log.info("No se adjunto ningun archivo");
		} else {
			lstArchivoTemporal = mapUpload.get("upload1");
			log.debug("Archivos adjuntos [" + lstArchivoTemporal.size() + "]");
		}
		DocumentoTemporal objDocumentoTemporal = new DocumentoTemporal();
		objDocumentoTemporal.setIIdTipoDocumento(getObjDocumento().getTipoDocumento().getIdtipodocumento());
		objDocumentoTemporal.setSNroDocumento(getObjDocumento().getNumeroDocumento());
		objDocumentoTemporal.setINroFolios(getObjDocumento().getNumeroFolios());
		// objDocumentoTemporal.setSFechaDocumento(getObjDocumento().get);
		objDocumentoTemporal.setLstArchivo(lstArchivoTemporal);
		if (lstDocumentoTemporal == null) {
			lstDocumentoTemporal = new ArrayList<DocumentoTemporal>();
		}
		log.debug("Numero de Documentos Temporales iniciales [" + lstDocumentoTemporal.size() + "]");
		lstDocumentoTemporal.add(objDocumentoTemporal);
		log.debug("Numero de Documentos Temporales finales [" + lstDocumentoTemporal.size() + "]");
		getMapSession().put("documentotemporal", lstDocumentoTemporal);
		this.cerrar = "OK";
		return Action.SUCCESS;
		/*
		 * }catch(Exception e){ log.error(e.getMessage(),e); return
		 * Action.ERROR; }
		 */
	}


	public String doDerivarMasivo() {
		log.debug("-> [Action] DocumentoAction - doDerivarMasivo():String ");

		mapSession=ActionContext.getContext().getSession();

                
		return Action.SUCCESS;
	}


	public String gorechazar() throws Exception {
		log.debug("-> [Action] DocumentoAction - gorechazar():String ");

		// try{
		Usuario objUsuario = null;
		setMapSession(ActionContext.getContext().getSession());
		objUsuario = (Usuario) getMapSession().get(Constantes.SESSION_USUARIO);
		setObjDD(documentoService.getDocumentDetail(getIIdDoc(), objUsuario.getRol().getNombre()));
		return Action.SUCCESS;
		/*
		 * }catch(Exception e){ log.error(e.getMessage(),e); return
		 * Action.ERROR; }
		 */
	}


	public String rechazaruser() throws Exception {
		log.debug("-> [Action] DocumentoAction - rechazaruser():String ");

		Map<String, Object> session = ActionContext.getContext().getSession();
		String strObservacion = objDD.getStrObservacion();
		strAcc = "rechazaruser";

		objDD = documentoService.getDocumentDetail(objDD.getIIdDocumento(), (String) session.get("rol"));
		objDD.setStrObservacion(strObservacion);

		
                return null;
		//return saveDoc();
	}

	public String verDetalleExpediente() throws Exception {
		log.debug("-> [Action] DocumentoAction - verDetalleExpediente():String ");

		// this.mensaje = "";
		try {
			objExpediente = expedienteService.findByIdExpediente(this.iIdExp);
			
		} catch (Exception e) {
			log.error("Error obteniendo los detalles del expediente", e);
			
		}
		return Action.SUCCESS;
	}
        
        public String verDetalleLegajo() throws Exception {
		log.debug("-> [Action] DocumentoAction - verDetalleLegajo():String ");

		try {
                        List<LegajoDocumento> lst = null; 
                        Legajo legajo = new Legajo();
                        
                        if (iIdLegajo == null || iIdLegajo==0){
                           Usuario objUsuario = null;
                           mapSession = ActionContext.getContext().getSession();
                           objUsuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO); 
                           LegajoDocumento legajoDocumento = new LegajoDocumento();
                           Documento d = documentoService.findByIdDocumento(idDocumentoLegajo);
                           legajoDocumento.setIdDocumento(d.getDocumentoreferencia()==null?d.getIdDocumento():d.getDocumentoreferencia());
                           lst = legajoDocumentoService.findDocumento(legajoDocumento, objUsuario);
                           
                           if (lst==null || lst.size()==0)
                             return "sinLegajo";  
                           
                           legajo.setEstado("");
                           legajo.setIdLegajo(lst.get(0).getIdLegajo());
                        }else{
                           LegajoDocumento legajoDocumento = new LegajoDocumento();
                           legajoDocumento.setIdLegajo(iIdLegajo);
                           lst = new ArrayList<LegajoDocumento>();
                           lst.add(legajoDocumento);
                           legajo.setEstado("");
                           legajo.setIdLegajo(lst.get(0).getIdLegajo());
                        }
                        
                        objLegajo = legajoService.findByIdLegajo(legajo);
                        
		} catch (Exception e) {
			log.error("Error obteniendo los detalles del expediente", e);
			
		}
		return Action.SUCCESS;
	}


	public String goAprobarStor() throws Exception {
		log.debug("-> [Action] DocumentoAction - goAprobarStor():String ");

		try {
			Map<String, Object> session = ActionContext.getContext().getSession();
			Usuario usuario = (Usuario) session.get(Constantes.SESSION_USUARIO);
			Rol rol = usuario.getRol();
			String nombreRol = null;
			if (rol != null) {
				nombreRol = rol.getNombre();
			}
			setObjDD(documentoService.getDocumentDetail(getIIdDoc(), nombreRol));
			mapCumpleRequisito.put(1, "SI");
			mapCumpleRequisito.put(2, "NO");
			return Action.SUCCESS;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return Action.ERROR;
		}
	}


	public String aprobarStor() throws Exception {
		log.debug("-> [Action] DocumentoAction - aprobarStor():String ");

		try {
			Map<String, Object> session = ActionContext.getContext().getSession();
			Usuario usuario = (Usuario) session.get(Constantes.SESSION_USUARIO);
			Rol rol = usuario.getRol();
			String nombreRol = null;
			if (rol != null) {
				nombreRol = rol.getNombre();
			}
			if (getIdsala() != null) {
				getObjDD().setIIdSala(getIdsala());
			}
			if (getObjDD() == null) {
				setObjDD(documentoService.getDocumentDetail(getIIdDoc(), nombreRol));
			}
			// documentoService.aplicarProcesoStor(getObjDD(), (String)
			// session.get("usuario"), (String) session.get("clave"));
			return Action.SUCCESS;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return Action.ERROR;
		}
	}

	public String nuevoDocUser() throws Exception {
		log.debug("-> [Action] DocumentoAction - nuevoDocUser():String ");

		try {
			return Action.SUCCESS;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return Action.ERROR;
		}
	}

	public String updateEstadoNotificacion() throws Exception {
		log.debug("-> [Action] DocumentoAction - updateEstadoNotificacion():String ");

		if (iIdNotificacion == null) {
			log.error("No se recibio ID de Notificacion");
			return Action.ERROR;
		}
		try {
			log.debug("Se actualizara Notificacion con ID [" + iIdNotificacion + "]");
			notificacionService.updateEstadoNotificacion(iIdNotificacion, Constantes.ESTADO_LEIDO);
			return Action.NONE;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return Action.ERROR;
		}
	}


	public String getNuevoDocumento() {
		log.debug("-> [Action] DocumentoAction - getNuevoDocumento():String ");

		this.fecha = new Date();
		return Action.SUCCESS;
	}

	/**
	 * Retorna true o false indicando si el documento buscado es el unico perteneciente al expediente.
	 * Recibe el documento y el expediente porque no necesariamente el documento seleccionado pertenece al expediente
	 *
	 * @author German Enriquez
	 */
	public String verificarDocumentoUnico() {
		log.debug("-> [Action] DocumentoAction - verificarDocumentoUnico():String ");

		if (iIdDoc == null) {
			log.error("No se especifico ningun documento, no se puede anular.");
			return Action.ERROR;
		}
		if (iIdExp == null) {
			log.error("No se especifico ningun expediente, no se puede anular.");
			return Action.ERROR;
		}
		Expediente expediente = expedienteService.findByIdExpediente(iIdExp);
		if (expediente != null) {
			Documento documento = documentoService.findByIdDocumento(iIdDoc);
			if (documento != null) {
				boolean unico = false;
				//verificamos que el expediente solo tenga un documento y que sea este
				List<Documento> delExpediente = expediente.getDocumentoList();
				if (delExpediente.size() == 1) {
					//if (delExpediente.get(0).equals(documento)) {
				  unico = true;
					//}
				}
				
				return "" + unico;
			}
			log.error("El id de documento: " + iIdDoc + " no se encontro en la base de datos.");
			return Action.ERROR;
		}
		log.error("El id del expediente: " + iIdExp + " no se encontro en la base de datos.");
		return Action.ERROR;
	}

	public String verificarDocumentoNuevo() {
		log.debug("-> [Action] DocumentoAction - verificarDocumentoNuevo():String ");

		if (iIdDoc == null) {
			log.error("No se especifico ningun documento, no se puede Copiar Referencia.");
			return Action.ERROR;
		}
		if (idExpedienteNuevo == null) {
			log.error("No se especifico ningun expediente nuevo, no se Copiar Referencia.");
			return Action.ERROR;
		}
		Expediente expedienteNuevo = expedienteService.findByIdExpediente(idExpedienteNuevo);
		if (expedienteNuevo != null) {
			Documento documento = documentoService.findByIdDocumento(iIdDoc);
			if (documento != null) {
				boolean unico = false;

				//verificamos que el expediente nuevo no tenga referenciado ya el documento
				List<Documentoxexpediente> referenciados = expedienteNuevo.getDocumentoxexpedienteList();
				for (Documentoxexpediente objDocumentoxExpediente : referenciados) {
					if (objDocumentoxExpediente.getDocumentoxexpedientePK().getIddocumento() == documento.getIdDocumento()) {
						unico = true;
					}
					if (unico == true) {
						return "" + unico;
					}
				}
				return "" + unico;
			}
			log.error("El id de documento: " + iIdDoc + " no se encontro en la base de datos.");
			return Action.ERROR;
		}
		log.error("El id del expediente nuevo: " + idExpedienteNuevo + " no se encontro en la base de datos.");
		return Action.ERROR;
	}

	public String anularDocumento() {
		log.debug("-> [Action] DocumentoAction - anularDocumento():String ");

		mapSession = ActionContext.getContext().getSession();
		String nombrePC = (String) mapSession.get("nombrePC");
		Usuario usuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
		
		if (idDocumentoAnular == null) {
			log.error("No se especifico ningun documento, no se puede anular.");
			return Action.ERROR;
		}

		Documento documento = documentoService.findByIdDocumento(idDocumentoAnular);
		documentoService.anularDocumento(usuario, documento, null, false, null, nombrePC, getsObservacionAnular());

		return "true";
	}


	public String reabrirDocumentoAtendido(){
                log.debug("-> [Action] DocumentoAction - reabrirDocumentoAtendido():String ");

		mapSession = ActionContext.getContext().getSession();
		String nombrePC = (String) mapSession.get("nombrePC");

		Usuario usuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
		
		if (iIdDoc == null) {
			log.error("No se especifico ningun documento, no se puede atender.");
			return Action.ERROR;
		}

		Documento documento = documentoService.findByIdDocumento(iIdDoc);
                documentoService.reabrirDocumentoAtendido(usuario, documento, nombrePC);

		return "true";
	}

	

	/**REN: Mover documento a otro expediente ------------------------------------------------*/
	public String cambiarReferenciaDocumento() {
		log.debug("-> [Action] DocumentoAction - cambiarReferenciaDocumento():String ");

		if (iIdDoc == null) {
			log.error("No se especifico ningun documento, no se puede referenciar.");
			return Action.ERROR;
		}
		if (iIdExp == null) {
			log.error("No se especifico ningun expediente origen, no se puede referenciar.");
			return Action.ERROR;
		}
		if (idExpedienteNuevo == null) {
			log.error("No se especifico ningun expediente destino, no se puede referenciar.");
			return Action.ERROR;
		}
		Expediente expedienteNuevo = expedienteService.findByIdExpediente(idExpedienteNuevo);
		if (expedienteNuevo != null) {
			Expediente expediente = expedienteService.findByIdExpediente(iIdExp);
			if (expediente != null) {
				Documento documento = documentoService.findByIdDocumento(iIdDoc);
				if (documento != null) {
					try {
						log.debug("Tipo de busqueda seleccionada {}", tipoBusqueda);
						if (tipoBusqueda == 'C') {
							mapSession = ActionContext.getContext().getSession();
							Usuario usuario = usuarioService.findByIdUsuario(((Usuario)mapSession.get(Constantes.SESSION_USUARIO)).getIdusuario());
							String nombrePC = (String) mapSession.get("nombrePC");
							documentoService.copiarReferencia(documento, expediente, expedienteNuevo, unico,usuario, nombrePC);
							log.info("El documento " + documento.getNumeroDocumento() + " ha sido copiado al expediente " + expedienteNuevo.getNroexpediente());
						} else {
							mapSession = ActionContext.getContext().getSession();
							//Usuario usuario = usuarioService.findByIdUsuario(((Usuario)mapSession.get(Constantes.SESSION_USUARIO)).getIdusuario());
							String nombrePC = (String) mapSession.get("nombrePC");
							documentoService.cambiarReferencia(documento, expediente, expedienteNuevo, unico,null, nombrePC);
							log.info("El documento " + documento.getNumeroDocumento() + " ha sido referenciado al expediente " + expedienteNuevo.getNroexpediente());
						}
					} catch (RuntimeException e) {
						log.error(e.getMessage(), e);
						return "false";
					}
					//log.info("El documento "+documento.getNumeroDocumento()+" ha sido referenciado al expediente "+expedienteNuevo.getNroexpediente());

					return "true";
				}
				log.error("El id de documento: " + iIdDoc + " no se encontro en la base de datos.");
				return Action.ERROR;
			}
			log.error("El id del expediente: " + iIdExp + " no se encontro en la base de datos.");
			return Action.ERROR;
		}
		log.error("El id del expediente nuevo: " + expedienteNuevo + " no se encontro en la base de datos.");
		return Action.ERROR;
	}

	public String verificarExistenciaDeArchivoParaCambiarReferencia() {
		log.debug("-> [Action] DocumentoAction - verificarExistenciaDeArchivoParaCambiarReferencia():String ");

		if (iIdDoc == null) {
			log.error("No se especifico ningun documento, no se puede referenciar.");
			return Action.ERROR;
		}
		if (idExpedienteNuevo == null) {
			log.error("No se especifico ningun expediente destino, no se puede referenciar.");
			return Action.ERROR;
		}
                
                return "true";
               
	}

	/**
	 * Prepara los valores predeterminados de un expediente partiendo de otro
	 * @author Erik Candela
	 */
	public String loadNuevoExpedienteUF() {
		log.debug("-> [Action] DocumentoAction - loadNuevoExpedienteUF():String ");

		this.objDD = new DocumentoDetail();
		//this.objDD.setsNroExpediente(expedienteService.getMaxReferencia());
		int idExpedienteOrigen = Integer.parseInt(ServletActionContext.getRequest().getParameter("idExpediente").toString());
		this.lstDocumento = documentoService.getDocumentosPorExpediente(idExpedienteOrigen);
		String idProceso = ServletActionContext.getRequest().getParameter("idProceso").toString();
		this.objDD.setIIdProceso(Integer.decode(idProceso));
		return Action.SUCCESS;

	}


	public String viewInfoPrincipal() {
		log.debug("-> [Action] DocumentoAction - viewInfoPrincipal():String ");

		if (iIdDoc == null || iIdDoc < 1) {
			log.error("No se recibio ningun ID de Documento a ver");
			return Action.ERROR;
		}
		log.debug("Documento a ver con ID [" + iIdDoc + "]");

		mapSession = ActionContext.getContext().getSession();
		documento = documentoService.findByIdDocumento(iIdDoc);
		if (documento.getExpediente().getCliente().getTipoIdentificacion().getNombre().compareTo("RUC") == 0) {
			this.objConcesionario = this.concesionarioService.findByRUC(documento.getExpediente().getCliente().getNumeroIdentificacion());
			if (this.objConcesionario == null) {
				this.objConcesionario = new Concesionario();
				this.objConcesionario.setIdConcesionario(0);
			}
		}

		if (documento.getExpediente().getExpedientestor() == null) {
			desactivado = true;
		} else {
			desactivado = false;
		}

		List<Trazabilidaddocumento> listaTrazabilidad = trazabilidadDocumentoService.findByIdDocumento(iIdDoc);

		if (listaTrazabilidad != null && listaTrazabilidad.size() > 0) {
			Usuario objUsuario = listaTrazabilidad.get(0).getRemitente();
			usuarioRegistro = objUsuario.getNombres() + " " + objUsuario.getApellidos();
		}

		Usuario usuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
		Rol rol = usuario.getRol();
		String nombreRol = Constantes.ROL_USUARIO_FINAL;
		if (rol != null) {
			nombreRol = rol.getNombre();
		}

		objDD = documentoService.getDocumentDetailOptimized(getIIdDoc(), nombreRol);
		SimpleDateFormat fechaHora = new SimpleDateFormat("dd/MM/yyy hh:mm aa");
		objDD.setStrFechaAccion(fechaHora.format(documento.getFechaAccion()));

		if (enVentana != true) {
			enVentana = false;
		}

		return Action.SUCCESS;
	}

	public String viewInfoComplementaria() {
		log.debug("-> [Action] DocumentoAction - viewInfoComplementaria():String ");

		if (iIdDoc == null || iIdDoc < 1) {
			log.error("No se recibio ningun ID de Documento a ver");
			return Action.ERROR;
		}
		log.debug("Documento a ver con ID [" + iIdDoc + "]");
		// try{
		mapSession = ActionContext.getContext().getSession();
		documento = documentoService.findByIdDocumento(iIdDoc);

		if (documento.getExpediente().getCliente().getTipoIdentificacion().getNombre().equals(Constantes.TipoDoc_DNI)) {
			StringBuilder nombre = new StringBuilder(documento.getExpediente().getCliente().getNumeroIdentificacion()).append(" - ").append(documento.getExpediente().getCliente().getNombres()).append(" ").append(documento.getExpediente().getCliente().getApellidoPaterno()).append(" ").append(documento.getExpediente().getCliente().getApellidoMaterno());
			remitenteObservacion = nombre.toString();
		} else {
			remitenteObservacion = new StringBuilder(documento.getExpediente().getCliente().getNumeroIdentificacion()).append(" - ").append(documento.getExpediente().getCliente().getRazonSocial()).toString();
		}
		Usuario objUsuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
		usuarioLogueado = objUsuario.getNombres() + " " + objUsuario.getApellidos();

		List<Trazabilidaddocumento> listaTrazabilidad = trazabilidadDocumentoService.findByIdDocumento(iIdDoc);

		if (listaTrazabilidad != null && listaTrazabilidad.size() > 0) {
			objUsuario = listaTrazabilidad.get(0).getRemitente();
			usuarioRegistro = objUsuario.getNombres() + " " + objUsuario.getApellidos();
			fechaDerivacion = listaTrazabilidad.get(listaTrazabilidad.size() - 1).getFechacreacion();
			try {
				etapaActual = listaTrazabilidad.get(listaTrazabilidad.size() - 1).getIdetapa().getDescripcion();
			} catch (Exception e) {
				etapaActual = "";
			}
		}

		Expedientestor objExpedienteStor = expedienteStorService.findByIdExpediente(documento.getExpediente().getIdexpediente());
		if (objExpedienteStor != null) {
			this.codSala = (objExpedienteStor.getSala() != null ? objExpedienteStor.getSala().getIdsala().toString() : "");
			this.codEstado = (objExpedienteStor.getEstado() != null ? objExpedienteStor.getEstado().getIdestado().toString() : "");


			Resolucionjaru objResolucionJaru = resolucionJaruService.findByIdExpedienteStor(objExpedienteStor.getIdexpediente());
			if (objResolucionJaru != null) {
				this.codResultado = (objResolucionJaru.getResultado() != null ? objResolucionJaru.getResultado().getIdtiporesultado().toString() : "");
				this.codVocal = (objResolucionJaru.getVocal() != null ? objResolucionJaru.getVocal().getIdvocal().toString() : "");
				this.fechaSesion = objResolucionJaru.getFechasesion();
				this.fechaNotiReclamante = objResolucionJaru.getFechanotificacionreclamante();
				this.fechaNotiConcesionario = objResolucionJaru.getFechanotificacionconcesionario();
				this.numeroResolucion = objResolucionJaru.getNroresolucion();
			}
		}



		return Action.SUCCESS;
	}
        
       
	public String goModificarExpediente(){
		log.debug("-> [Action] DocumentoAction - goModificarExpediente():String ");
		objDocumento = documentoService.findByIdDocumento(iIdDoc);
                return Action.SUCCESS;
	}
        
        public String goModificarLegajo(){
		log.debug("-> [Action] DocumentoAction - goModificarLegajo():String ");
		//objDocumento = documentoService.findByIdDocumento(iIdDoc);
                return Action.SUCCESS;
	}

	public void doModificarExpediente(){
		log.debug("-> [Action] DocumentoAction - doModificarExpediente():void ");
                mapSession = ActionContext.getContext().getSession();
                Usuario objUsuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
                Serie serie = new Serie();
                serie.setIdserie(idserie);
		objExpediente = expedienteService.findByIdExpediente(iIdExp);
		objExpediente.setFechamodificacion(new Date());
                objExpediente.setUsuariomodificacion(objUsuario.getIdusuario());
                objExpediente.setSerie(serie);
		objExpediente.setObservacion(ultimaObservacion);
		objExpediente.setAsunto(asuntoObservacion);
		expedienteService.saveExpediente(objExpediente);
	}

	public String goModificarDocumento(){
		log.debug("-> [Action] DocumentoAction - goModificarDocumento():String ");
		objDocumento = documentoService.findByIdDocumento(iIdDoc);
		objDD = new DocumentoDetail();
		try{
			objDD.setStrFechaDocumento(new SimpleDateFormat("dd/MM/yyyy").format(objDocumento.getFechaDocumento()));
		}catch(Exception e){
			log.error("No se pudo parsear la fecha "+e.getMessage());
			objDD.setStrFechaDocumento("");
		}
		return Action.SUCCESS;
	}

	public void doModificarDocumento(){
		log.debug("-> [Action] DocumentoAction - doModificarDocumento():void ");
		objDocumento = documentoService.findByIdDocumento(iIdDoc);
		objDocumento.setAsunto(asuntoObservacion);
		objDocumento.setObservacion(ultimaObservacion);
		objDocumento.setReferenciados(referencia);
		objDocumento.setConfidencial(confidencialDocMod==null?Constantes.No:confidencialDocMod);

		try{
			objDocumento.setFechaDocumento(new SimpleDateFormat("yyyy-MM-dd").parse(objDD.getStrFechaDocumento()));
		
		}catch(Exception e){
			log.error("No se pudo parsear la fecha "+e.getMessage());
		}

		documentoService.saveDocumento(objDocumento);
	}

	public String goBandejaDXE(){
		log.debug("-> [Action] DocumentoAction - goBandejaDXE():String ");
		return "bandeja";
	}

	public void guardarSeguimientoUsuario(){
		log.debug("-> [Action] DocumentoAction - guardarSeguimientoUsuario():void ");
		mapSession = ActionContext.getContext().getSession();
		Usuario objUsuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
                SeguimientoXUsuario seguimiento = new SeguimientoXUsuario();
		seguimiento.setIdDocumento(iIdDoc);
                seguimiento.setIdUsuario(objUsuario.getIdUsuarioPerfil());
                seguimiento.setUnidadPropietario(objUsuario.getIdUnidadPerfil());
                seguimiento.setCargoPropietario(objUsuario.getIdFuncionPerfil());
                
		if(agregar){
                        seguimiento.setEstado("A");
                        seguimiento.setFechaCreacion(new Date());
                        seguimiento.setUsuarioCreacion(objUsuario.getIdusuario());
                	seguimientoXUsuarioService.guardarSeguimiento(seguimiento);
                }else{
                        if (idseguimientos!=null && !idseguimientos.equals("")){
                           seguimiento = seguimientoXUsuarioService.findByIdSeguimiento(idseguimientos);
                           seguimiento.setEstado("I");
			   seguimiento.setFechaModificacion(new Date());
                           seguimiento.setUsuarioModificacion(objUsuario.getIdusuario());
                	   seguimientoXUsuarioService.guardarSeguimiento(seguimiento);
                        }else{
                           List<SeguimientoXUsuario> list =  seguimientoXUsuarioService.buscarSeguimientoXUsuario(seguimiento);
                           for(int i=0;i<list.size();i++){
                               list.get(i).setEstado("I");
                               list.get(i).setFechaModificacion(new Date());
                               list.get(i).setUsuarioModificacion(objUsuario.getIdusuario());
                               seguimientoXUsuarioService.guardarSeguimiento(list.get(i));
                           }
                        }
                        
                        
		}
	}

	public String imprimirProveido(){
		log.debug("-> [Action] DocumentoAction - imprimirProveido():String ");

		mapSession = ActionContext.getContext().getSession();
		Usuario objUsuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
		documento = documentoService.findByIdDocumento(iIdDoc);

		Integer traza;

		if(sOpcion != null && sOpcion.equals("enviados") && idtrazabilidaddocumento!=null){
			traza = idtrazabilidaddocumento;
		}else{
			if(documento.getDocumentoreferencia() != null){
				/**Es una copia para trabajo----------------------------------------------------------------------------------*/
				traza=trazabilidadDocumentoService.findByMaxtrazabyIddocumento(documento.getDocumentoreferencia()).get(0).getIdtrazabilidaddocumento();
			}else{
				traza=trazabilidadDocumentoService.findByMaxtrazabyIddocumento(iIdDoc).get(0).getIdtrazabilidaddocumento();
			}
		}

		lstTrazabilidadCopia = trazabilidadcopiaService.buscarUsuarioCopia(iIdDoc,traza);

		StringBuilder cadenaCC = new StringBuilder();
		if(lstTrazabilidadCopia!=null && lstTrazabilidadCopia.size()>0){
			for(int i=0; i<lstTrazabilidadCopia.size(); i++){
				if(i!=0) cadenaCC.append(", ");
				cadenaCC.append( lstTrazabilidadCopia.get(i).getDestinatario().getApellidos()+" "+lstTrazabilidadCopia.get(i).getDestinatario().getNombres());
			}
		}

		suministros = null;
		submotivos = null;
		if (documento != null) {
			Expediente expediente = documento.getExpediente();
			if (expediente != null) {
				//Proceso proceso = expediente.getProceso();
				//if (proceso != null) {
				//	Tipoproceso tipoProceso = proceso.getTipoproceso();
				//	if (tipoProceso != null) {
						Rol rol = objUsuario.getRol();
						String nombreRol = Constantes.ROL_USUARIO_FINAL;
						if (rol != null) {
							nombreRol = rol.getNombre();
						}

						if(documento.getDocumentoreferencia() != null){
							/**Es una copia de apoyo---------------------------------------------------------*/
							objDD = documentoService.getDocumentDetailOptimized(documento.getDocumentoreferencia(), nombreRol);
                                                        
                                                        Usuarioxunidadxfuncion usuarioxunidadxfuncion  = new Usuarioxunidadxfuncion();
                                                        usuarioxunidadxfuncion.setIdusuario(objUsuario.getIdUsuarioPerfil());
                                                        usuarioxunidadxfuncion.setIdunidad(objUsuario.getIdUnidadPerfil());
                                                        usuarioxunidadxfuncion.setIdfuncion(objUsuario.getIdFuncionPerfil());
                                                        
							Trazabilidadapoyo tapoyo = trazabilidadapoyoService.buscarUltimaDelegacionUsuario(usuarioxunidadxfuncion, iIdDoc);
							String comentario = "";
							if(tapoyo != null){
								objDD.setStrAsunto(tapoyo.getAsunto() != null ? tapoyo.getAsunto() : objDD.getStrAsunto());
								objDD.setStrDestinatario(tapoyo.getDestinatario().getNombres() + " " + tapoyo.getDestinatario().getApellidos());
								comentario = tapoyo.getTexto() != null ? tapoyo.getTexto() : "";
							}
							if(objDD.getStrContenido() == null){
								objDD.setStrContenido(comentario);
							}else{
								objDD.setStrContenido(comentario+ "<br />" +objDD.getStrContenido());
							}
						}else{
							objDD = documentoService.getDocumentDetailOptimized(getIIdDoc(), nombreRol);
						}

						if(cadenaCC.length()!=0){
							objDD.setCadenaCC(cadenaCC.toString());
						}

						Integer id_ = documento.getDocumentoreferencia() != null ? documento.getDocumentoreferencia() : documento.getIdDocumento();
						archivos = archivoService.getArchivoListPorDocumento(id_).get("upload1");
						//archivos = archivoService.getArchivoListPorDocumento(iIdDoc).get("upload1");

						if(sOpcion != null && sOpcion.equals("enviados") && idtrazabilidaddocumento!=null){
							Trazabilidaddocumento trazabilidad = trazabilidadDocumentoService.findTrabilidadbyId(idtrazabilidaddocumento);
							objDD.setStrAsunto(trazabilidad.getAsunto());
							objDD.setStrDestinatario(trazabilidad.getDestinatario().getNombreCompleto());
							objDD.setStrContenido(trazabilidad.getContenido());
						}else if(sOpcion != null && sOpcion.equals("informativos") && iIdNotificacion!=null){
							Notificacion notificacion = notificacionService.buscarObjPorID(iIdNotificacion);
							try{
								objDD.setStrAsunto(notificacion.getAsunto());
								Trazabilidadcopia t = trazabilidadcopiaService.buscarPorNotificacion(notificacion.getIdnotificacion());
								Trazabilidaddocumento td = trazabilidadDocumentoService.findByIdTrazabilidadDocumento(t.getIdorigen().getIdtrazabilidaddocumento());
								objDD.setStrDestinatario(td.getDestinatario().getNombreCompleto());
								List<Trazabilidadcopia> list = trazabilidadcopiaService.buscarPorOrigen(t.getIdorigen().getIdtrazabilidaddocumento(), 'D');
								String valor = "";

								for(int i=0;i<list.size();i++){
									if (i==list.size()-1){
										for(int j=0;j<= i-1;j++){
											if (list.get(i).getDestinatario().getIdusuario()==list.get(j).getDestinatario().getIdusuario())
												break;
										}
										valor = valor + list.get(i).getDestinatario().getNombreCompleto();
									}else{
										for(int j=0;j<= i-1;j++){
											if (list.get(i).getDestinatario().getIdusuario()==list.get(j).getDestinatario().getIdusuario())
												break;
										}
	                            	    valor = valor + list.get(i).getDestinatario().getNombreCompleto() + ";";
									}
	                            }

								objDD.setCadenaCC(valor);
								objDD.setStrContenido(notificacion.getContenido());
                            }catch(Exception ex){
                            	objDD.setStrAsunto(notificacion.getAsunto());
    							objDD.setStrDestinatario(notificacion.getIdusuario().getNombreCompleto());
    							objDD.setStrContenido(notificacion.getContenido());
                            }

							/*objDD.setStrAsunto(notificacion.getAsunto());
							objDD.setStrDestinatario(notificacion.getIdusuario().getNombreCompleto());
							objDD.setStrContenido(notificacion.getContenido());*/
						}else if(sOpcion != null && sOpcion.equals("multiples") && idTrazabilidadapoyo!=null){
							Trazabilidadapoyo trazapo = new Trazabilidadapoyo();
			            	trazapo = trazabilidadapoyoService.findByIdTrazabilidadApoyo(idTrazabilidadapoyo);
			            	objDD.setStrAsunto(trazapo.getAsunto());
			            	objDD.setStrDestinatario(trazapo.getDestinatario().getNombreCompleto());
			            	objDD.setStrContenido(trazapo.getTexto());
						}
				//	}
				//}
			} else {
				log.debug("Excecion nulo");

			}
			//objDD.setStrAsunto(documento.getExpediente().getAsunto());



			objDD.setIIdDocumento(iIdDoc);
		}
		return "proveido";
	}

	public String generarCargoTicket(){
		cargo = documentoService.obtenerCargo(idDocumento);
		return "cargo";
	}
        
        public String generarCargo(){
		cargo = documentoService.obtenerCargo(idDocumento);
		return "cargo";
	}

	public String getIdsDocumentoPorExSeleccionados() {
		return idsDocumentoPorExSeleccionados;
	}

	public void setIdsDocumentoPorExSeleccionados(
			String idsDocumentoPorExSeleccionados) {
		this.idsDocumentoPorExSeleccionados = idsDocumentoPorExSeleccionados;
	}

	public Integer getIdDocPrincipalExpediente() {
		return idDocPrincipalExpediente;
	}

	public void setIdDocPrincipalExpediente(Integer idDocPrincipalExpediente) {
		this.idDocPrincipalExpediente = idDocPrincipalExpediente;
	}

	public void setDocumentoService(DocumentoService documentoService) {
		this.documentoService = documentoService;
	}

	public Integer getIIdDoc() {
		return iIdDoc;
	}

	public void setIIdDoc(Integer iIdDoc) {
		this.iIdDoc = iIdDoc;
	}

	public Integer getIIdExp() {
		return iIdExp;
	}

	public void setIIdExp(Integer iIdExp) {
		this.iIdExp = iIdExp;
	}

	public ArchivoPendiente getArchivopendiente() {
		return archivopendiente;
	}

	public void setArchivopendiente(ArchivoPendiente archivopendiente) {
		this.archivopendiente = archivopendiente;
	}

	public List<DestinatarioList> getLstDL() {
		return lstDL;
	}

	public void setLstDL(List<DestinatarioList> lstDL) {
		this.lstDL = lstDL;
	}

	public Map<Integer, Boolean> getMapChkDocumento() {
		return mapChkDocumento;
	}

	public void setMapChkDocumento(Map<Integer, Boolean> mapChkDocumento) {
		this.mapChkDocumento = mapChkDocumento;
	}

	public Map<Integer, String> getMapTipoDocumento() {
		return mapTipoDocumento;
	}

	public void setMapTipoDocumento(Map<Integer, String> mapTipoDocumento) {
		this.mapTipoDocumento = mapTipoDocumento;
	}

	// public Map getMapTipoIdentificacion(){
	// return mapTipoIdentificacion;
	// }
	//
	// public void setMapTipoIdentificacion(Map mapTipoIdentificacion){
	// this.mapTipoIdentificacion=mapTipoIdentificacion;
	// }
	public DocumentoDetail getObjDD() {
		return objDD;
	}

	public void setObjDD(DocumentoDetail objDD) {
		this.objDD = objDD;
	}

	public void setProcesoService(ProcesoService procesoService) {
		this.procesoService = procesoService;
	}

	public void setClienteService(ClienteService clienteService) {
		this.clienteService = clienteService;
	}

	public String getStrAcc() {
		return strAcc;
	}

	public void setStrAcc(String strAcc) {
		this.strAcc = strAcc;
	}

	public void setTipoDocumentoService(TipodocumentoService tipoDocumentoService) {
		this.tipoDocumentoService = tipoDocumentoService;
	}

	public void setTipoIdentificacionService(TipoidentificacionService tipoIdentificacionService) {
		this.tipoIdentificacionService = tipoIdentificacionService;
	}

        /*
	public void setActividadService(ActividadService actividadService) {
		this.actividadService = actividadService;
	}*/

	public void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public Integer getIddepartamento() {
		return iddepartamento;
	}

	public void setIddepartamento(Integer iddepartamento) {
		this.iddepartamento = iddepartamento;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public Integer getIdprovincia() {
		return idprovincia;
	}

	public void setIdprovincia(Integer idprovincia) {
		this.idprovincia = idprovincia;
	}

	public String getDistrito() {
		return distrito;
	}

	public void setDistrito(String distrito) {
		this.distrito = distrito;
	}

	public Integer getIddistrito() {
		return iddistrito;
	}

	public void setIddistrito(Integer iddistrito) {
		this.iddistrito = iddistrito;
	}

	public String getProceso() {
		return proceso;
	}

	public void setProceso(String proceso) {
		this.proceso = proceso;
	}

	public Integer getIdproceso() {
		return idproceso;
	}

	public void setIdproceso(Integer idproceso) {
		this.idproceso = idproceso;
	}

	public String getCorrentista() {
		return correntista;
	}

	public void setCorrentista(String correntista) {
		this.correntista = correntista;
	}

	public Integer getIdcorrentista() {
		return idcorrentista;
	}

	public void setIdcorrentista(Integer idcorrentista) {
		this.idcorrentista = idcorrentista;
	}

	public String getStrUnidad() {
		return strUnidad;
	}

	public void setStrUnidad(String strUnidad) {
		this.strUnidad = strUnidad;
	}

	public String getStrResponsable() {
		return strResponsable;
	}

	public void setStrResponsable(String strResponsable) {
		this.strResponsable = strResponsable;
	}

	public String getTipodocumento() {
		return tipodocumento;
	}

	public void setTipodocumento(String tipodocumento) {
		this.tipodocumento = tipodocumento;
	}

	public Integer getIdArchivoPendiente() {
		return idArchivoPendiente;
	}

	public void setIdArchivoPendiente(Integer idArchivoPendiente) {
		this.idArchivoPendiente = idArchivoPendiente;
	}

	public Integer getIdtipodocumento() {
		return idtipodocumento;
	}

	public void setIdtipodocumento(Integer idtipodocumento) {
		this.idtipodocumento = idtipodocumento;
	}

	public String getTipoidentificacion() {
		return tipoidentificacion;
	}

	public void setTipoidentificacion(String tipoidentificacion) {
		this.tipoidentificacion = tipoidentificacion;
	}

	public Integer getIdtipoidentificacion() {
		return idtipoidentificacion;
	}

	public void setIdtipoidentificacion(Integer idtipoidentificacion) {
		this.idtipoidentificacion = idtipoidentificacion;
	}

	public String getNumeroIdentificacion() {
		return nroidentificacion;
	}

	public void setNroidentificacion(String nroidentificacion) {
		this.nroidentificacion = nroidentificacion;
	}

	public String getStrRazonSocial() {
		return strRazonSocial;
	}

	public void setStrRazonSocial(String strRazonSocial) {
		this.strRazonSocial = strRazonSocial;
	}

	public String getStrDireccionPrincipal() {
		return strDireccionPrincipal;
	}

	public void setStrDireccionPrincipal(String strDireccionPrincipal) {
		this.strDireccionPrincipal = strDireccionPrincipal;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public void setFullFileName(String fullFileName) {
		this.fullFileName = fullFileName;
	}

	public String getIddestinatario() {
		return iddestinatario;
	}

	public void setIddestinatario(String iddestinatario) {
		this.iddestinatario = iddestinatario;
	}

	public Integer getIdccdestinatario() {
		return idccdestinatario;
	}

	public void setIdccdestinatario(Integer idccdestinatario) {
		this.idccdestinatario = idccdestinatario;
	}

	public Integer getIdPlantilla() {
		return idPlantilla;
	}

	public void setIdPlantilla(Integer idPlantilla) {
		this.idPlantilla = idPlantilla;
	}

	public String getTa() {
		return ta;
	}

	public void setTa(String ta) {
		this.ta = ta;
	}

	public Integer getIdDocumento() {
		return idDocumento;
	}

	public void setIdDocumento(Integer idDocumento) {
		this.idDocumento = idDocumento;
	}

	public void setArchivoService(ArchivoService archivoService) {
		this.archivoService = archivoService;
	}

	public void setExpedienteStorService(ExpedientestorService expedienteStorService) {
		this.expedienteStorService = expedienteStorService;
	}

	public void setExpedienteService(ExpedienteService expedienteService) {
		this.expedienteService = expedienteService;
	}

	public String getNrodocumento() {
		return nrodocumento;
	}

	public void setNrodocumento(String nrodocumento) {
		this.nrodocumento = nrodocumento;
	}

	public String getStrRUC() {
		return strRUC;
	}

	public void setStrRUC(String strRUC) {
		this.strRUC = strRUC;
	}

	public String getStrDireccion() {
		return strDireccion;
	}

	public void setStrDireccion(String strDireccion) {
		this.strDireccion = strDireccion;
	}

	public String getStrRepresentanteLegal() {
		return strRepresentanteLegal;
	}

	public void setStrRepresentanteLegal(String strRepresentanteLegal) {
		this.strRepresentanteLegal = strRepresentanteLegal;
	}

	public String getStrCorreoConcesionario() {
		return strCorreoConcesionario;
	}

	public void setStrCorreoConcesionario(String strCorreoConcesionario) {
		this.strCorreoConcesionario = strCorreoConcesionario;
	}

	public String getStrTelefonoCliente() {
		return strTelefonoCliente;
	}

	public void setStrTelefonoCliente(String strTelefonoCliente) {
		this.strTelefonoCliente = strTelefonoCliente;
	}

	public String getStrCorreoCliente() {
		return strCorreoCliente;
	}

	public void setStrCorreoCliente(String strCorreoCliente) {
		this.strCorreoCliente = strCorreoCliente;
	}

	public Integer getIdmotivo() {
		return idmotivo;
	}

	public void setIdmotivo(Integer idmotivo) {
		this.idmotivo = idmotivo;
	}

	public Integer getIdsubmotivo() {
		return idsubmotivo;
	}

	public void setIdsubmotivo(Integer idsubmotivo) {
		this.idsubmotivo = idsubmotivo;
	}

	public String getSubmotivo() {
		return submotivo;
	}

	public void setSubmotivo(String submotivo) {
		this.submotivo = submotivo;
	}

	public Integer getIdsala() {
		return idsala;
	}

	public void setIdsala(Integer idsala) {
		this.idsala = idsala;
	}

	public String getSala() {
		return sala;
	}

	public void setSala(String sala) {
		this.sala = sala;
	}

	public Integer getIdanalista() {
		return idanalista;
	}

	public void setIdanalista(Integer idanalista) {
		this.idanalista = idanalista;
	}

	public String getAnalista() {
		return analista;
	}

	public void setAnalista(String analista) {
		this.analista = analista;
	}

	public ExpedienteTree getObjExpedienteTree() {
		return objExpedienteTree;
	}

	public void setObjExpedienteTree(ExpedienteTree objExpedienteTree) {
		this.objExpedienteTree = objExpedienteTree;
	}

	public boolean isBBandeja() {
		return bBandeja;
	}

	public void setBBandeja(boolean bBandeja) {
		this.bBandeja = bBandeja;
	}

	public Map<Integer, String> getMapCumpleRequisito() {
		return mapCumpleRequisito;
	}

	public void setMapCumpleRequisito(Map<Integer, String> mapCumpleRequisito) {
		this.mapCumpleRequisito = mapCumpleRequisito;
	}

	public Integer getIdCumpleRequisito() {
		return idCumpleRequisito;
	}

	public void setIdCumpleRequisito(Integer idCumpleRequisito) {
		this.idCumpleRequisito = idCumpleRequisito;
	}

	public Integer getIdconcesionario() {
		return idconcesionario;
	}

	public void setIdconcesionario(Integer idconcesionario) {
		this.idconcesionario = idconcesionario;
	}

	public String getConcesionario() {
		return concesionario;
	}

	public void setConcesionario(String concesionario) {
		this.concesionario = concesionario;
	}

	public String getNmotivo() {
		return nmotivo;
	}

	public void setNmotivo(String nmotivo) {
		this.nmotivo = nmotivo;
	}

	public Integer getIdCliente() {
		return idcliente;
	}

	public void setIdcliente(Integer idcliente) {
		this.idcliente = idcliente;
	}

	public List<Tipoidentificacion> getLstRadio() {
		return lstRadio;
	}

	public void setLstRadio(List<Tipoidentificacion> lstRadio) {
		this.lstRadio = lstRadio;
	}

	public String getCerrar() {
		return cerrar;
	}

	public void setCerrar(String cerrar) {
		this.cerrar = cerrar;
	}

	public void setAccionService(AccionService accionService) {
		this.accionService = accionService;
	}

	public PlantillaService getPlantillaService() {
		return plantillaService;
	}

	public void setNotificacionService(NotificacionService notificacionService) {
		this.notificacionService = notificacionService;
	}



	public NotificacionxEnumerarService getNotificacionxEnumerarService() {
		return notificacionxEnumerarService;
	}

	public void setNotificacionxEnumerarService(
			NotificacionxEnumerarService notificacionxEnumerarService) {
		this.notificacionxEnumerarService = notificacionxEnumerarService;
	}

	public void setPlantillaService(PlantillaService plantillaService) {
		this.plantillaService = plantillaService;
	}

	public void setTrazabilidadDocumentoService(TrazabilidaddocumentoService trazabilidadDocumentoService) {
		this.trazabilidadDocumentoService = trazabilidadDocumentoService;
	}

	public boolean isOwnerproceso() {
		return ownerproceso;
	}

	public void setOwnerproceso(boolean ownerproceso) {
		this.ownerproceso = ownerproceso;
	}

	public boolean isBResponsable() {
		return bResponsable;
	}

	public void setBResponsable(boolean responsable) {
		bResponsable = responsable;
	}

	public String[] getStorsuministro() {
		return storsuministro;
	}

	public void setStorsuministro(String[] storsuministro) {
		this.storsuministro = storsuministro;
	}

	public Integer[] getStoridsubmotivo() {
		return storidsubmotivo;
	}

	public void setStoridsubmotivo(Integer[] storidsubmotivo) {
		this.storidsubmotivo = storidsubmotivo;
	}

	public Map<String, Object> getMapSession() {
		return mapSession;
	}

	public void setMapSession(Map<String, Object> mapSession) {
		this.mapSession = mapSession;
	}

	public Integer getIIdDocumento() {
		return iIdDocumento;
	}

	public void setIIdDocumento(Integer iIdDocumento) {
		this.iIdDocumento = iIdDocumento;
	}

	public String getRegresar() {
		return regresar;
	}

	public void setRegresar(String regresar) {
		this.regresar = regresar;
	}

	public Integer getIdenv() {
		return idenv;
	}

	public void setIdenv(Integer idenv) {
		this.idenv = idenv;
	}

	public Integer[] getArrIdDoc() {
		return arrIdDoc;
	}

	public void setArrIdDoc(Integer[] arrIdDoc) {
		this.arrIdDoc = arrIdDoc;
	}

	public String getSTipoDerivacion() {
		return sTipoDerivacion;
	}

	public void setSTipoDerivacion(String sTipoDerivacion) {
		this.sTipoDerivacion = sTipoDerivacion;
	}

	public void setIIdPro(Integer iIdPro) {
		this.iIdPro = iIdPro;
	}

	public Integer getIIdPro() {
		return iIdPro;
	}

	public void setAvisopermiso(Integer avisopermiso) {
		this.avisopermiso = avisopermiso;
	}

	public Integer getAvisopermiso() {
		return avisopermiso;
	}

	public Documento getObjDocumento() {
		return objDocumento;
	}

	public void setObjDocumento(Documento objDocumento) {
		this.objDocumento = objDocumento;
	}

	public Boolean getBBotonHabilitado() {
		return bBotonHabilitado;
	}

	public void setBBotonHabilitado(Boolean bBotonHabilitado) {
		this.bBotonHabilitado = bBotonHabilitado;
	}

	public Expediente getObjExpediente() {
		return objExpediente;
	}

	public void setObjExpediente(Expediente objExpediente) {
		this.objExpediente = objExpediente;
	}

	public String getSFromBandeja() {
		return sFromBandeja;
	}

	public void setSFromBandeja(String sFromBandeja) {
		this.sFromBandeja = sFromBandeja;
	}

	public String getSOpcion() {
		return sOpcion;
	}

	public void setSOpcion(String opcion) {
		sOpcion = opcion;
	}

	public ExpedienteSearch getObjExpedienteSearch() {
		return objExpedienteSearch;
	}

	public void setObjExpedienteSearch(ExpedienteSearch objExpedienteSearch) {
		this.objExpedienteSearch = objExpedienteSearch;
	}

	public List<Documento> getLstDocumento() {
		return lstDocumento;
	}

	public void setLstDocumento(List<Documento> lstDocumento) {
		this.lstDocumento = lstDocumento;
	}

	public Cliente getObjCliente() {
		return objCliente;
	}

	public void setObjCliente(Cliente objCliente) {
		this.objCliente = objCliente;
	}

	/*public void setTrazabilidadExpedienteService(TrazabilidadexpedienteService trazabilidadExpedienteService) {
		this.trazabilidadExpedienteService = trazabilidadExpedienteService;
	}*/

	public Boolean getMostrarObservacion() {
		return mostrarObservacion;
	}

	public void setMostrarObservacion(Boolean mostrarObservacion) {
		this.mostrarObservacion = mostrarObservacion;
	}

	public String getRemitenteObservacion() {
		return remitenteObservacion;
	}

	public void setRemitenteObservacion(String remitenteObservacion) {
		this.remitenteObservacion = remitenteObservacion;
	}

	public String getUltimaObservacion() {
		return ultimaObservacion;
	}

	public void setUltimaObservacion(String ultimaObservacion) {
		this.ultimaObservacion = ultimaObservacion;
	}

	public String getOtraObservacion() {
		return otraObservacion;
	}

	public void setOtraObservacion(String otraObservacion) {
		this.otraObservacion = otraObservacion;
	}

	public String getAsuntoObservacion() {
		return asuntoObservacion;
	}

	public void setAsuntoObservacion(String asuntoObservacion) {
		this.asuntoObservacion = asuntoObservacion;
	}

	public String getAccionObservacion() {
		return accionObservacion;
	}

	public void setAccionObservacion(String accionObservacion) {
		this.accionObservacion = accionObservacion;
	}

	public String getDestinatarioObservacion() {
		return destinatarioObservacion;
	}

	public void setDestinatarioObservacion(String destinatarioObservacion) {
		this.destinatarioObservacion = destinatarioObservacion;
	}

	public Integer getIIdUpload() {
		return iIdUpload;
	}

	public void setIIdUpload(Integer iIdUpload) {
		this.iIdUpload = iIdUpload;
	}

	public String getSTipoIdentificacion() {
		return sTipoIdentificacion;
	}

	public void setSTipoIdentificacion(String sTipoIdentificacion) {
		this.sTipoIdentificacion = sTipoIdentificacion;
	}

	public String getSMontoReclamo() {
		return sMontoReclamo;
	}

	public void setSMontoReclamo(String sMontoReclamo) {
		this.sMontoReclamo = sMontoReclamo;
	}

	public String getSFechaDocumento() {
		return sFechaDocumento;
	}

	public void setSFechaDocumento(String sFechaDocumento) {
		this.sFechaDocumento = sFechaDocumento;
	}

	public Integer getIIdNotificacion() {
		return iIdNotificacion;
	}

	public void setIIdNotificacion(Integer iIdNotificacion) {
		this.iIdNotificacion = iIdNotificacion;
	}

	public boolean isDocumentoStor() {
		return documentoStor;
	}

	public void setDocumentoStor(boolean documentoStor) {
		this.documentoStor = documentoStor;
	}

	public Expedientestor getExpedienteStor() {
		return expedienteStor;
	}

	public void setExpedienteStor(Expedientestor expedienteStor) {
		this.expedienteStor = expedienteStor;
	}

	public Map<String, String> getDetallesDocumentoStor() {
		return detallesDocumentoStor;
	}

	public void setDetallesDocumentoStor(Map<String, String> detallesDocumentoStor) {
		this.detallesDocumentoStor = detallesDocumentoStor;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getFechaEnTexto() {
		return fechaEnTexto;
	}

	public void setFechaEnTexto(String fechaEnTexto) {
		this.fechaEnTexto = fechaEnTexto;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	/*public void setUsuarioStorService(UsuariostorService usuarioStorService) {
		this.usuarioStorService = usuarioStorService;
	}*/

	public boolean isUnico() {
		return unico;
	}

	public void setUnico(boolean unico) {
		this.unico = unico;
	}

	public String getIdGrid() {
		return idGrid;
	}

	public void setIdGrid(String idGrid) {
		this.idGrid = idGrid;
	}

	public boolean isDeMail() {
		return deMail;
	}

	public void setDeMail(boolean deMail) {
		this.deMail = deMail;
	}

	public void setConcesionarioService(ConcesionarioService concesionarioService) {
		this.concesionarioService = concesionarioService;
	}

	public Integer getIdtrazabilidaddocumento() {
		return idtrazabilidaddocumento;
	}

	public void setIdtrazabilidaddocumento(Integer idtrazabilidaddocumento) {
		this.idtrazabilidaddocumento = idtrazabilidaddocumento;
	}

	public ParametroService getParametroService() {
		return parametroService;
	}

	public void setParametroService(ParametroService parametroService) {
		this.parametroService = parametroService;
	}

	public String getDigmensaje() {
		return digmensaje;
	}

	public void setDigmensaje(String digmensaje) {
		this.digmensaje = digmensaje;
	}

	public Integer getIdExpedienteNuevo() {
		return idExpedienteNuevo;
	}

	public void setIdExpedienteNuevo(Integer idExpedienteNuevo) {
		this.idExpedienteNuevo = idExpedienteNuevo;
	}

	public List<String> getConCopia() {
		return conCopia;
	}

	public void setConCopia(List<String> conCopia) {
		this.conCopia = conCopia;
	}

	public void setControlDeCalidadService(ControlDeCalidadService controlDeCalidadService) {
		this.controlDeCalidadService = controlDeCalidadService;
	}

	

	public Documento getDocumento() {
		return documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

	public List<Archivo> getArchivos() {
		return archivos;
	}

	public void setArchivos(List<Archivo> archivos) {
		this.archivos = archivos;
	}

	public String getNombreCliente() {
		return nombreCliente;
	}

	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}

	public List<Submotivo> getSubmotivos() {
		return submotivos;
	}

	public void setSubmotivos(List<Submotivo> submotivos) {
		this.submotivos = submotivos;
	}

	public List<Suministro> getSuministros() {
		return suministros;
	}

	public void setSuministros(List<Suministro> suministros) {
		this.suministros = suministros;
	}

	public Integer[] getIdSubmotivos() {
		return idSubmotivos;
	}

	public void setIdSubmotivos(Integer[] idSubmotivos) {
		this.idSubmotivos = idSubmotivos;
	}

	public Integer[] getIdSuministros() {
		return idSuministros;
	}

	public void setIdSuministros(Integer[] idSuministros) {
		this.idSuministros = idSuministros;
	}

	public String getUrlIntalio() {
		return urlIntalio;
	}

	public void setUrlIntalio(String urlIntalio) {
		this.urlIntalio = urlIntalio;
	}

	public char getTipoBusqueda() {
		return tipoBusqueda;
	}

	public void setTipoBusqueda(char tipoBusqueda) {
		this.tipoBusqueda = tipoBusqueda;
	}

	public boolean isDestinatarioIgualRemitente() {
		return destinatarioIgualRemitente;
	}

	public void setDestinatarioIgualRemitente(boolean destinatarioIgualRemitente) {
		this.destinatarioIgualRemitente = destinatarioIgualRemitente;
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

	public EtapaService getEtapaService() {
		return etapaService;
	}

	public void setEtapaService(EtapaService etapaService) {
		this.etapaService = etapaService;
	}

	public boolean isMostrarEtapa() {
		return mostrarEtapa;
	}

	public void setMostrarEtapa(boolean mostrarEtapa) {
		this.mostrarEtapa = mostrarEtapa;
	}

	public Boolean getProvieneDeMail() {
		return provieneDeMail;
	}

	public void setProvieneDeMail(Boolean provieneDeMail) {
		this.provieneDeMail = provieneDeMail;
	}

	public DocumentoEnviadoService getDocumentoenviadoService() {
		return documentoenviadoService;
	}

	public void setDocumentoenviadoService(DocumentoEnviadoService documentoenviadoService) {
		this.documentoenviadoService = documentoenviadoService;
	}

	public String getUsuarioLogueado() {
		return usuarioLogueado;
	}

	public void setUsuarioLogueado(String usuarioLogueado) {
		this.usuarioLogueado = usuarioLogueado;
	}

	public String getEtapaActual() {
		return etapaActual;
	}

	public void setEtapaActual(String etapaActual) {
		this.etapaActual = etapaActual;
	}

	public Date getFechaDerivacion() {
		return fechaDerivacion;
	}

	public void setFechaDerivacion(Date fechaDerivacion) {
		this.fechaDerivacion = fechaDerivacion;
	}

	public String getUsuarioRegistro() {
		return usuarioRegistro;
	}

	public void setUsuarioRegistro(String usuarioRegistro) {
		this.usuarioRegistro = usuarioRegistro;
	}

	public String getCodSala() {
		return codSala;
	}

	public void setCodSala(String codSala) {
		this.codSala = codSala;
	}

	public String getCodEstado() {
		return codEstado;
	}

	public void setCodEstado(String codEstado) {
		this.codEstado = codEstado;
	}

	public Date getFechaSesion() {
		return fechaSesion;
	}

	public void setFechaSesion(Date fechaSesion) {
		this.fechaSesion = fechaSesion;
	}

	public String getCodVocal() {
		return codVocal;
	}

	public void setCodVocal(String codVocal) {
		this.codVocal = codVocal;
	}

	public String getCodResultado() {
		return codResultado;
	}

	public void setCodResultado(String codResultado) {
		this.codResultado = codResultado;
	}

	public String getNumeroResolucion() {
		return numeroResolucion;
	}

	public void setNumeroResolucion(String numeroResolucion) {
		this.numeroResolucion = numeroResolucion;
	}

	public Date getFechaNotiReclamante() {
		return fechaNotiReclamante;
	}

	public void setFechaNotiReclamante(Date fechaNotiReclamante) {
		this.fechaNotiReclamante = fechaNotiReclamante;
	}

	public Date getFechaNotiConcesionario() {
		return fechaNotiConcesionario;
	}

	public void setFechaNotiConcesionario(Date fechaNotiConcesionario) {
		this.fechaNotiConcesionario = fechaNotiConcesionario;
	}

	public ResolucionjaruService getResolucionJaruService() {
		return resolucionJaruService;
	}

	public void setResolucionJaruService(ResolucionjaruService resolucionJaruService) {
		this.resolucionJaruService = resolucionJaruService;
	}

	public boolean isDesactivado() {
		return desactivado;
	}

	public void setDesactivado(boolean desactivado) {
		this.desactivado = desactivado;
	}

	public boolean isEnVentana() {
		return enVentana;
	}

	public void setEnVentana(boolean enVentana) {
		this.enVentana = enVentana;
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

	public String getObservacionDigitalizador() {
		return observacionDigitalizador;
	}

	public void setObservacionDigitalizador(String observacionDigitalizador) {
		this.observacionDigitalizador = observacionDigitalizador;
	}

	public Concesionario getObjConcesionario() {
		return objConcesionario;
	}

	public void setObjConcesionario(Concesionario objConcesionario) {
		this.objConcesionario = objConcesionario;
	}


	public TrazabilidadcopiaService getTrazabilidadcopiaService() {
		return trazabilidadcopiaService;
	}

	public void setTrazabilidadcopiaService(
			TrazabilidadcopiaService trazabilidadcopiaService) {
		this.trazabilidadcopiaService = trazabilidadcopiaService;
	}

	public List<Trazabilidadcopia> getLstTrazabilidadCopia() {
		return lstTrazabilidadCopia;
	}

	public void setLstTrazabilidadCopia(List<Trazabilidadcopia> lstTrazabilidadCopia) {
		this.lstTrazabilidadCopia = lstTrazabilidadCopia;
	}

	public String getOrigenDetalleDoc() {
		return origenDetalleDoc;
	}

	public void setOrigenDetalleDoc(String origenDetalleDoc) {
		this.origenDetalleDoc = origenDetalleDoc;
	}

	public boolean isFlagMensaje() {
		return flagMensaje;
	}

	public void setFlagMensaje(boolean flagMensaje) {
		this.flagMensaje = flagMensaje;
	}


	public GestionDocumentos getGestionDocumentos() {
		return gestionDocumentos;
	}

	public void setGestionDocumentos(GestionDocumentos gestionDocumentos) {
		this.gestionDocumentos = gestionDocumentos;
	}

	private boolean isProcesoStor(List<Proceso> procesos,Proceso seleccionado){
		boolean forward=false;

		for (Proceso proceso : procesos) {
			if(proceso.getIdproceso()==seleccionado.getIdproceso())
			{
				forward=true;
				break;
			}
		}

		return forward;
	}

	public String[] getApoyo() {
		return apoyo;
	}

	public void setApoyo(String[] apoyo) {
		this.apoyo = apoyo;
	}

	public TrazabilidadapoyoService getTrazabilidadapoyoService() {
		return trazabilidadapoyoService;
	}

	public void setTrazabilidadapoyoService(
			TrazabilidadapoyoService trazabilidadapoyoService) {
		this.trazabilidadapoyoService = trazabilidadapoyoService;
	}

	public String[] getStrAcciones() {
		return strAcciones;
	}

	public void setStrAcciones(String[] strAcciones) {
		this.strAcciones = strAcciones;
	}
	public String getsNombres() {
		return sNombres;
	}

	public void setsNombres(String sNombres) {
		this.sNombres = sNombres;
	}

	public String getPuedeRechazar() {
		return puedeRechazar;
	}

	public void setPuedeRechazar(String puedeRechazar) {
		this.puedeRechazar = puedeRechazar;
	}

	public Map<String, String> getUrl() {
		return url;
	}

	public void setUrl(Map<String, String> url) {
		this.url = url;
	}

	public Integer getIdDocumentoSeleccionado() {
		return idDocumentoSeleccionado;
	}

	public void setIdDocumentoSeleccionado(Integer idDocumentoSeleccionado) {
		this.idDocumentoSeleccionado = idDocumentoSeleccionado;
	}

	public boolean isApelacion() {
		return apelacion;
	}

	public void setApelacion(boolean apelacion) {
		this.apelacion = apelacion;
	}

	public void setIIdDoc(String iIdDoc) {
		try {
			this.iIdDoc = new Integer(iIdDoc);
		} catch (Exception ex) {
			log.debug(" iIdDoc is not a number :" + iIdDoc);
			log.error(ex.getMessage(), ex);
		}
	}

	public boolean isParaAprobar() {
		return paraAprobar;
	}

	public void setParaAprobar(boolean paraAprobar) {
		this.paraAprobar = paraAprobar;
	}

	public Map<String, Integer> getMapSessionStor() {
		return mapSessionStor;
	}

	public void setMapSessionStor(Map<String, Integer> mapSessionStor) {
		this.mapSessionStor = mapSessionStor;
	}

	public String getOcultar() {
		return ocultar;
	}

	public void setOcultar(String ocultar) {
		this.ocultar = ocultar;
	}


	public ListaService getListaService() {
		return listaService;
	}


	public void setListaService(ListaService listaService) {
		this.listaService = listaService;
	}


	public String[] getStrPrioridades() {
		return strPrioridades;
	}


	public void setStrPrioridades(String[] strPrioridades) {
		this.strPrioridades = strPrioridades;
	}


	public Integer getiIdDoc() {
		return iIdDoc;
	}


	public void setiIdDoc(Integer iIdDoc) {
		this.iIdDoc = iIdDoc;
	}


	public Integer getiIdPro() {
		return iIdPro;
	}


	public void setiIdPro(Integer iIdPro) {
		this.iIdPro = iIdPro;
	}


	public Integer getiIdExp() {
		return iIdExp;
	}


	public void setiIdExp(Integer iIdExp) {
		this.iIdExp = iIdExp;
	}


	public Integer getiIdNotificacion() {
		return iIdNotificacion;
	}


	public void setiIdNotificacion(Integer iIdNotificacion) {
		this.iIdNotificacion = iIdNotificacion;
	}


	public Integer getiIdDocumento() {
		return iIdDocumento;
	}


	public void setiIdDocumento(Integer iIdDocumento) {
		this.iIdDocumento = iIdDocumento;
	}


	public Integer getIdcliente() {
		return idcliente;
	}


	public ProveidoService getProveidoService() {
		return proveidoService;
	}


	public void setProveidoService(ProveidoService proveidoService) {
		this.proveidoService = proveidoService;
	}


	public List<Proveido> getProveidos() {
		return proveidos;
	}


	public void setProveidos(List<Proveido> proveidos) {
		this.proveidos = proveidos;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public SeguimientoXUsuarioService getSeguimientoXUsuarioService() {
		return seguimientoXUsuarioService;
	}

	public void setSeguimientoXUsuarioService(
			SeguimientoXUsuarioService seguimientoXUsuarioService) {
		this.seguimientoXUsuarioService = seguimientoXUsuarioService;
	}

	public boolean isAgregar() {
		return agregar;
	}

	public void setAgregar(boolean agregar) {
		this.agregar = agregar;
	}

	public FechaLimite getFechaLimite() {
		return fechaLimite;
	}

	public void setFechaLimite(FechaLimite fechaLimite) {
		this.fechaLimite = fechaLimite;
	}

	public Integer getIdTrazabilidadapoyo() {
		return idTrazabilidadapoyo;
	}

	public void setIdTrazabilidadapoyo(Integer idTrazabilidadapoyo) {
		this.idTrazabilidadapoyo = idTrazabilidadapoyo;
	}

	public Integer[] getArrIdArchivos() {
		return arrIdArchivos;
	}

	public void setArrIdArchivos(Integer[] arrIdArchivos) {
		this.arrIdArchivos = arrIdArchivos;
	}

	public CargoReporte getCargo() {
		return cargo;
	}

	public void setCargo(CargoReporte cargo) {
		this.cargo = cargo;
	}


	/***********
	 * wcarrasco
	 */


  

    

    public String verArchivoAlfresco(String sURL, Integer idDoc) {
		log.debug("-> [Action] DocumentoAction - verArchivoAlfresco():String ");
		mapSession = ActionContext.getContext().getSession();
		HttpServletRequest request = ServletActionContext.getRequest();
		log.debug("Ruta Archivo Alfresco [" + sURL + "]");
		String ipPublica = SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.IP_PUBLICA);
		String dominioPublico = SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.DOMINIO_PUBLICO);
		String alfrescoHostPublico = SigedProperties.getProperty(SigedProperties.SigedPropertyEnum.ALFRESCO_HOST_PUBLICO);
		String forward=alfrescoWebServiceClient.obtenerLinkContenido(sURL);
		try {
			URL urlReq = new URL(HttpUtils.getRequestURL(request).toString());
			log.debug("URL ["+urlReq.toExternalForm()+"] Host ["+urlReq.getHost()+"]");

			URL url = new URL(forward);
			if(urlReq.getHost().equals(ipPublica) || urlReq.getHost().equals(dominioPublico)){
				forward = forward.replace(url.getHost(), alfrescoHostPublico);
				log.debug("Reemplazando IP por ["+alfrescoHostPublico+"]");
			}

			try{
				LogOperacion logOperacion = new LogOperacion();
				logOperacion.setIddocumento((Integer)idDoc);
				logOperacion.setIdusuario(((Usuario) mapSession.get(Constantes.SESSION_USUARIO)).getIdusuario());
				logOperacion.setOpcion((String)mapSession.get("sTipoGrid"));
				logOperacion.setNombrepc((String)mapSession.get("nombrePC"));
				logOperacion.setNombrefile(sURL.substring(sURL.lastIndexOf("/") + 1, sURL.length()));
				logOperacion.setFechaoperacion(new Date());
				logOperacionService.saveLogOperacion(logOperacion);
			}catch(Exception e){
				e.printStackTrace();
			}


		} catch (MalformedURLException e) {
			e.printStackTrace();
		}


		return forward;
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

	public AlfrescoWSService getAlfrescoWebServiceClient() {
		return alfrescoWebServiceClient;
	}

	public void setAlfrescoWebServiceClient(
			AlfrescoWSService alfrescoWebServiceClient) {
		this.alfrescoWebServiceClient = alfrescoWebServiceClient;
	}

	public String getAsuntoDD() {
		return asuntoDD;
	}

	public void setAsuntoDD(String asuntoDD) {
		this.asuntoDD = asuntoDD;
	}

	public List<Archivo> getListArchivo() {
		return listArchivo;
	}

	public void setListArchivo(List<Archivo> listArchivo) {
		this.listArchivo = listArchivo;
	}

	public DocumentoDetail getObjDDIG() {
		return objDDIG;
	}

	public void setObjDDIG(DocumentoDetail objDDIG) {
		this.objDDIG = objDDIG;
	}

	public List<Usuario> getListUsuario() {
		return listUsuario;
	}

	public void setListUsuario(List<Usuario> listUsuario) {
		this.listUsuario = listUsuario;
	}


	/**WCARRASCO Metodo que llama  para establecer el archivo principal  -----------------------------------------------*/
	public String goArchivoPrincipal(){
		log.debug("-> [Action] DocumentoAction - goArchivoPrincipal():String ");
		log.debug("Mostrar iIdDoc"+iIdDoc);
             
                try{
			mapSession=ActionContext.getContext().getSession();

			Usuario objUsuario = null;

			objUsuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);

			if (objUsuario == null) {
				log.error("Expiro la sesion");
				return Action.ERROR;
			}
			if(iIdDoc!=null){

				objDocumento = documentoService.findByIdDocumento(iIdDoc);

				if(objDocumento.getDocumentoreferencia() != null){
					listArchivo = archivoService.buscarArchivoXAutorPDF(objUsuario.getIdusuario(), objDocumento.getDocumentoreferencia());
				}else {
					listArchivo = archivoService.buscarArchivoXAutorPDF(objUsuario.getIdusuario(), iIdDoc);
				}

			}

		}catch(Exception e){
			log.error(e.getMessage(),e);
			return Action.ERROR;
		}

		return Action.SUCCESS;

	}
	/**WCARRASCO Metodo que se encarga de colocar los archivos seleccionados como inactivos----------------------------------------*/
	public void doSetPrincipalArchivo(){
          	log.debug("-> [Action] DocumentoAction - doSetPrincipalArchivo():String ");
		mapSession=ActionContext.getContext().getSession();

		Usuario objUsuario = null;

		objUsuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);

		if (objUsuario == null) {
			log.error("Expiro la sesion");
		}

		if(arrIdArchivos != null && arrIdArchivos.length > 0){
			for(Integer idArchivo : arrIdArchivos){
				archivo = archivoService.findById(idArchivo);
				log.debug("idArchivo"+idArchivo);
				archivoService.updatePrincipalByArchivo(idArchivo, Constantes.ARCHIVO_PRINCIPAL);
				listArchivo = archivoService.findLstByIdDocumento(archivo.getDocumento().getIdDocumento());
				for(int i=0; i<listArchivo.size(); i++){
					Archivo arch = listArchivo.get(i);
					if(arch.getIdArchivo() != idArchivo && arch.getPrincipal()=='S'){
						archivoService.updatePrincipalByArchivo(arch.getIdArchivo(), Constantes.ARCHIVO_NOPRINCIPAL);
					}
				}
			}
		}
	}



	/**
	 * [ACTION]
	 * Metodo que llama a la ventana de cambiar clave de acceso.
	 * @return
	 */
	public String verCambiarClave(){
		log.debug("-> [Action] DocumentoAction - verCambiarClave():String ");
		return "cambiarClave";
	}

	/**
	 * [ACTION]
	 * Metodo que guarda la nueva clave de acceso una vez validada
	 */
	public void actualizarClave(){
		log.debug("-> [Action] DocumentoAction - actualizarClave():void ");
		Usuario objUsuario = null;
		mapSession = ActionContext.getContext().getSession();
		objUsuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
		objUsuario = usuarioService.findByIdUsuario(objUsuario.getIdusuario());
		objUsuario.setClaveSiged(claveUsuario);
		usuarioService.guardarUsuario(objUsuario);
	}
        

	public RolService getRolService() {
		return rolService;
	}

	public void setRolService(RolService rolService) {
		this.rolService = rolService;
	}

	public Boolean getBoVisor() {
		return boVisor;
	}

	public void setBoVisor(Boolean boVisor) {
		this.boVisor = boVisor;
	}

	public String getClaveUsuario() {
		return claveUsuario;
	}

	public void setClaveUsuario(String claveUsuario) {
		this.claveUsuario = claveUsuario;
	}

	public String getNombrePDFprincipal() {
		return nombrePDFprincipal;
	}

	public void setNombrePDFprincipal(String nombrePDFprincipal) {
		this.nombrePDFprincipal = nombrePDFprincipal;
	}

	public Boolean getbPDFprincipal() {
		return bPDFprincipal;
	}

	public void setbPDFprincipal(Boolean bPDFprincipal) {
		this.bPDFprincipal = bPDFprincipal;
	}

	public boolean isParaAlerta() {
		return paraAlerta;
	}

	public void setParaAlerta(boolean paraAlerta) {
		this.paraAlerta = paraAlerta;
	}

	public String[] getArrDocumentArea() {
		return arrDocumentArea;
	}

	public void setArrDocumentArea(String[] arrDocumentArea) {
		this.arrDocumentArea = arrDocumentArea;
	}

	public SeguimientoService getSeguimientoService() {
		return seguimientoService;
	}

	public void setSeguimientoService(SeguimientoService seguimientoService) {
		this.seguimientoService = seguimientoService;
	}

	public String getExecuteAccion() {
		return executeAccion;
	}

	public void setExecuteAccion(String executeAccion) {
		this.executeAccion = executeAccion;
	}

	public String getOrdenarFechaLimite() {
		return ordenarFechaLimite;
	}

	public void setOrdenarFechaLimite(String ordenarFechaLimite) {
		this.ordenarFechaLimite = ordenarFechaLimite;
	}

	public Character getConfidencialDocMod() {
		return confidencialDocMod;
	}

	public void setConfidencialDocMod(Character confidencialDocMod) {
		this.confidencialDocMod = confidencialDocMod;
	}

	public String getsObservacionAnular() {
		return sObservacionAnular;
	}

	public void setsObservacionAnular(String sObservacionAnular) {
		this.sObservacionAnular = sObservacionAnular;
	}

	public Integer getIdDocumentoAnular() {
		return idDocumentoAnular;
	}

	public void setIdDocumentoAnular(Integer idDocumentoAnular) {
		this.idDocumentoAnular = idDocumentoAnular;
	}

	public LogOperacionService getLogOperacionService() {
		return logOperacionService;
	}

	public void setLogOperacionService(LogOperacionService logOperacionService) {
		this.logOperacionService = logOperacionService;
	}
        
        public FuncionService getFuncionService() {
            return funcionService;
        }

        public void setFuncionService(FuncionService funcionService) {
            this.funcionService = funcionService;
        }        
        
        public Integer getIdpendientes() {
            return idpendientes;
        }

        public void setIdpendientes(Integer idpendientes) {
            this.idpendientes = idpendientes;
        }
        
        public DocumentoPendienteDAO getDocumentoPendienteDAO() {
            return documentoPendienteDAO;
        }

        public void setDocumentoPendienteDAO(DocumentoPendienteDAO documentoPendienteDAO) {
            this.documentoPendienteDAO = documentoPendienteDAO;
        }
        
        public String getStrPrioridad() {
         return strPrioridad;
        }

        public void setStrPrioridad(String strPrioridad) {
            this.strPrioridad = strPrioridad;
        }
        
        public String getStrReferencia() {
            return strReferencia;
        }

        public void setStrReferencia(String strReferencia) {
            this.strReferencia = strReferencia;
        }
        
         public Integer getIdserie() {
            return idserie;
        }

        public void setIdserie(Integer idserie) {
            this.idserie = idserie;
        }
       
        
        public List<DocumentoReferencia> getDocumentosReferencia() {
            return documentosReferencia;
        }

        public void setDocumentosReferencia(List<DocumentoReferencia> documentosReferencia) {
            this.documentosReferencia = documentosReferencia;
        }

        public List<Parametro> getParametros() {
            return parametros;
        }

        public void setParametros(List<Parametro> parametros) {
            this.parametros = parametros;
        }
        
         public DocumentoReunionDAO getDocumentoReunionDAO() {
            return documentoReunionDAO;
        }

        public void setDocumentoReunionDAO(DocumentoReunionDAO documentoReunionDAO) {
            this.documentoReunionDAO = documentoReunionDAO;
        }
        
        public DocumentoAdjuntoDAO getDocumentoAdjuntoDAO() {
            return documentoAdjuntoDAO;
        }

        public void setDocumentoAdjuntoDAO(DocumentoAdjuntoDAO documentoAdjuntoDAO) {
            this.documentoAdjuntoDAO = documentoAdjuntoDAO;
        }
        public Integer getiIdDocEmail() {
            return iIdDocEmail;
        }

        public void setiIdDocEmail(Integer iIdDocEmail) {
            this.iIdDocEmail = iIdDocEmail;
        }
        
        public UnidadDAO getUnidadDAO() {
            return unidadDAO;
        }

        public void setUnidadDAO(UnidadDAO unidadDAO) {
            this.unidadDAO = unidadDAO;
        }
}


