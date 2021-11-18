/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.actions.dojo;

import org.ositran.daos.UnidadDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.struts2.ServletActionContext;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.ositran.common.action.GenericAction;
import org.ositran.daos.CourierDAO;
import org.ositran.daos.EmpresadestinoDAO;
import org.ositran.daos.EstadoDAO;
import org.ositran.daos.MesDAO;
import org.ositran.daos.NotificacionDAO;
import org.ositran.daos.ReemplazoDAO;
import org.ositran.daos.SalaDAO;
import org.ositran.daos.SubmotivoDAO;
import org.ositran.daos.UsuariostorDAO;
import org.ositran.services.AmbitoenvioService;
import org.ositran.services.ConcesionarioService;
import org.ositran.services.AuditoriaService;
import org.ositran.services.DepartamentoService;
import org.ositran.services.DocumentoService;
import org.ositran.services.DocumentoxclienteService;
import org.ositran.services.EtapaService;
import org.ositran.services.ListaService;
import org.ositran.services.MensajeriaService;
import org.ositran.services.MotivoService;
import org.ositran.services.NumeracionService;
import org.ositran.services.ParametroService;
import org.ositran.services.PlantillaService;
import org.ositran.services.ProcesoService;
import org.ositran.services.SalaService;
import org.ositran.services.SedeService;
import org.ositran.services.SupervisorService;
import org.ositran.services.TipodocumentoService;
import org.ositran.services.TiporesultadoService;
import org.ositran.services.TrazabilidaddocumentoService;
import org.ositran.services.TipoenvioService;
import org.ositran.services.UnidadService;
import org.ositran.services.UsuarioService;
import org.ositran.services.VocalService;
import org.ositran.utils.Constantes;

import com.btg.ositran.siged.domain.AmbitoEnvio;
import com.btg.ositran.siged.domain.Auditoria;
import com.btg.ositran.siged.domain.Campo;
import com.btg.ositran.siged.domain.Proveido;
import com.btg.ositran.siged.domain.Cliente;
import com.btg.ositran.siged.domain.Concesionario;
import com.btg.ositran.siged.domain.Courier;
import com.btg.ositran.siged.domain.Serie;
import com.btg.ositran.siged.domain.LegajoDocumento;
import com.btg.ositran.siged.domain.Departamento;
import com.btg.ositran.siged.domain.Distrito;
import com.btg.ositran.siged.domain.CargoAdministrado;
import com.btg.ositran.siged.domain.TipoInstitucion;
import com.btg.ositran.siged.domain.Documento;
import com.btg.ositran.siged.domain.Documentoxcliente;
import com.btg.ositran.siged.domain.Empresadestino;
import com.btg.ositran.siged.domain.Estado;
import com.btg.ositran.siged.domain.Estadocargo;
import com.btg.ositran.siged.domain.Etapa;
import com.btg.ositran.siged.domain.Expediente;
import com.btg.ositran.siged.domain.Favorito;
import com.btg.ositran.siged.domain.Lista;
import com.btg.ositran.siged.domain.Mensajeria;
import com.btg.ositran.siged.domain.Mes;
import com.btg.ositran.siged.domain.Motivo;
import com.btg.ositran.siged.domain.Notificacion;
import com.btg.ositran.siged.domain.Numeracion;
import com.btg.ositran.siged.domain.Parametro;
import com.btg.ositran.siged.domain.Plantilla;
import com.btg.ositran.siged.domain.Proceso;
import com.btg.ositran.siged.domain.Provincia;
import com.btg.ositran.siged.domain.Reemplazo;
import com.btg.ositran.siged.domain.Rol;
import com.btg.ositran.siged.domain.Sala;
import com.btg.ositran.siged.domain.Sede;
import com.btg.ositran.siged.domain.Submotivo;
import com.btg.ositran.siged.domain.Supervisor;
import com.btg.ositran.siged.domain.Tipodocumento;
import com.btg.ositran.siged.domain.Tipoenvio;
import com.btg.ositran.siged.domain.Tiporesultado;
import com.btg.ositran.siged.domain.Unidad;
import com.btg.ositran.siged.domain.Unidadpeso;
import com.btg.ositran.siged.domain.DetalleCliente;
import com.btg.ositran.siged.domain.Legajo;
import com.btg.ositran.siged.domain.TipoLegajo;
import com.btg.ositran.siged.domain.Usuario;
import com.btg.ositran.siged.domain.UsuarioDerivacion;
import com.btg.ositran.siged.domain.Usuarioxunidadxfuncion;
import com.btg.ositran.siged.domain.Vocal;
import com.opensymphony.xwork2.ActionContext;
import org.ositran.daos.TiporesultadoDAO;
import java.sql.Timestamp;
import org.ositran.daos.EstadocargoDAO;
import org.ositran.daos.ExpedienteDAO;
import org.ositran.daos.ProveidoDAO;
import org.ositran.daos.SerieDAO;
import org.ositran.daos.UnidadpesoDAO;
import org.ositran.dojo.grid.Item;
import org.ositran.services.CargoAdministradoService;
import org.ositran.services.ClienteService;
import org.ositran.services.DetalleClienteService;
import org.ositran.services.DistritoService;
import org.ositran.services.DocumentoAdjuntoService;
import org.ositran.services.DocumentoEnviadoService;
import org.ositran.services.FavoritoService;
import org.ositran.services.FuncionService;
import org.ositran.services.LegajoDocumentoService;
import org.ositran.services.LegajoService;
import org.ositran.services.ProvinciaService;
import org.ositran.services.RolService;
import org.ositran.services.TipoInstitucionService;
import org.ositran.services.TipoLegajoService;
import org.ositran.services.TipoidentificacionService;
import org.ositran.services.UsuarioxunidadxfuncionService;
import org.ositran.siged.dto.ProcesoDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

import static org.ositran.utils.StringUtil.isEmpty;

public class AutoCompletarAction extends GenericAction {

    public String getTipoPersonaJuridica() {
        return tipoPersonaJuridica;
    }

    public void setTipoPersonaJuridica(String tipoPersonaJuridica) {
        this.tipoPersonaJuridica = tipoPersonaJuridica;
    }

    private static Logger log = LoggerFactory.getLogger(AutoCompletarAction.class);
    private Map<String, Object> mapSession;
    private String tipoPersonaJuridica;
    private int idProceso;
    private String desCargo;
    private Integer codCargo;
    private String sNroRS;
    private String sNroHT;
    private int idSede;
    private int tipoPlantilla;
    private UsuarioxunidadxfuncionService usuarioxunidadxfuncionService;
    private FuncionService funcionService;
    private Integer iIdExp;
    private Integer iIdDoc;
    private Integer iWithoutStor;
    private Integer iIdCliente;
    private Integer idTipoCliente;
    private Integer iIdPlantilla;
    private Integer idDocumento;
    private Integer iIdProceso;
    private Integer iIdUsuario;
    private Integer iIdUsuarioToOmit;
    private Integer iIdUnidad;
    private Integer idCliente;
    private CargoAdministradoService cargoAdministradoService;
    private String idTipo;
    private String idNro;
    private String idCopia;
    private String idAdjunto;
    private String sTipoProceso;
    private String sOpcion;
    private String origenExpediente;
    private String fecha;
    private String tipoBusqueda;
    private String sFiltroBusqueda;
    private String mode;
    private String sTipoDerivacion;
    private String estado;
    private String idusuario;
    private String sNroExpediente;
    private String sNroLegajo;
    private String sUsuario;
    private String sModulo;
    private String sNroDocumento;
    private String sFechaDesde;
    private String sFechaHasta;
    private String sDiasReporte;
    private String filtro1;
    private String sNroIdentificacion;
    private String sRazonSocial;
    private String sAsunto;
    private String sTipoIdentificacion;
    private String idDependencia1;
    private String idDependencia2;
    private Integer idPersona;
    private String desPersona;
    private String desInstitucion;
    private String codParametro;
    private String idDepartamento;
    private String idProvincia;
    private EstadoDAO estadoDao;
    private NotificacionDAO notificacionDao;
    private ReemplazoDAO reemplazoDao;
    private TiporesultadoDAO tiporesultadoDao;
    private ExpedienteDAO expedienteDao;
    private UnidadDAO unidadDAO; 
    private FavoritoService favoritoService;
    private ParametroService srvParametro;
    private UnidadService srvUnidad;
    private TrazabilidaddocumentoService srvTrazabilidadDocumento;
    private TipoInstitucionService tipoInstitucionService;
    private DocumentoService srvDocumento;
    private PlantillaService plantillaService;
    private UsuarioService srvUsuario;
    private NumeracionService srvNumeracion;
    private DocumentoAdjuntoService documentoAdjuntoService;
    private ProcesoService srvProceso;
    private SerieDAO serieDAO;
    private TipodocumentoService tipoDocumentoService;
    private DepartamentoService departamentoService;
    private TipoidentificacionService tipoIdentificacionService;
    private DocumentoxclienteService documentoxclienteService;
    private ClienteService clienteService;
    private ProvinciaService provinciaService;
    private ProveidoDAO proveidoDAO;
    private DistritoService distritoService;
    private SalaService salaService;
    private SedeService sedeService;
    private EtapaService etapaService;
    private ListaService listaService;
    private TipoenvioService tipoEnvioService;
    private AmbitoenvioService ambitoService;
    private TiporesultadoService tipoResultadoService;
    private DetalleClienteService detalleClienteService;
    private LegajoService legajoService;
    private TipoLegajoService tipoLegajoService;
    private VocalService vocalService;
    private RolService srvRol;
    private AuditoriaService srvAuditoria;
    private ConcesionarioService srvConcesionario;
    private Integer tipoDocumento;
    private String sTipoLegajo;
    private LegajoDocumentoService legajoDocumentoService;
    private DocumentoEnviadoService documentoenviadoService;

    public DocumentoEnviadoService getDocumentoenviadoService() {
        return documentoenviadoService;
    }

    public void setDocumentoenviadoService(DocumentoEnviadoService documentoenviadoService) {
        this.documentoenviadoService = documentoenviadoService;
    }

    public LegajoDocumentoService getLegajoDocumentoService() {
        return legajoDocumentoService;
    }

    public void setLegajoDocumentoService(LegajoDocumentoService legajoDocumentoService) {
        this.legajoDocumentoService = legajoDocumentoService;
    }

    public String getsTipoLegajo() {
        return sTipoLegajo;
    }

    public void setsTipoLegajo(String sTipoLegajo) {
        this.sTipoLegajo = sTipoLegajo;
    }

    
    public String getsNroLegajo() {
        return sNroLegajo;
    }

    public void setsNroLegajo(String sNroLegajo) {
        this.sNroLegajo = sNroLegajo;
    }

    public TipoLegajoService getTipoLegajoService() {
        return tipoLegajoService;
    }

    public void setTipoLegajoService(TipoLegajoService tipoLegajoService) {
        this.tipoLegajoService = tipoLegajoService;
    }

    public LegajoService getLegajoService() {
        return legajoService;
    }

    public void setLegajoService(LegajoService legajoService) {
        this.legajoService = legajoService;
    }
    
    public String getsNroRS() {
        return sNroRS;
    }

    public void setsNroRS(String sNroRS) {
        this.sNroRS = sNroRS;
    }

    public String getsNroHT() {
        return sNroHT;
    }

    public void setsNroHT(String sNroHT) {
        this.sNroHT = sNroHT;
    }
            
    public String getCodParametro() {
        return codParametro;
    }

    public void setCodParametro(String codParametro) {
        this.codParametro = codParametro;
    }

    public String getDesInstitucion() {
        return desInstitucion;
    }

    public void setDesInstitucion(String desInstitucion) {
        this.desInstitucion = desInstitucion;
    }

    public String getDesPersona() {
        return desPersona;
    }

    public void setDesPersona(String desPersona) {
        this.desPersona = desPersona;
    }        

    public DetalleClienteService getDetalleClienteService() {
        return detalleClienteService;
    }

    public void setDetalleClienteService(DetalleClienteService detalleClienteService) {
        this.detalleClienteService = detalleClienteService;
    }
    
    public Integer getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(Integer tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}


	public Integer getIdUnidad() {
		return idUnidad;
	}


	public void setIdUnidad(Integer idUnidad) {
		this.idUnidad = idUnidad;
	}


	private Integer idUnidad;


    public void autocompletarRol() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - autocompletarRol():enviaDatos() ");

        List<Rol> lstRol = srvRol.obtenerTodo();
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
        for (Rol objRol : lstRol) {
            Map<String, String> datos = new HashMap<String, String>();
            datos.put("id", String.valueOf(objRol.getIdrol().intValue()));
            datos.put("label", objRol.getNombre());
            l1.add(datos);
        }
        enviaDatos(l1);
    }


    public void autocompletarUsuario() throws Exception {
       Usuario usuario_ = null;
       boolean isJefe = false;
       
       try{
            mapSession = ActionContext.getContext().getSession();
            usuario_ = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);

            List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
            List<Usuario> lstUsuario = srvUsuario.getUsuarios();  //srvUsuario.getUsuariosByUnidad( usuario_.getUnidad().getIdunidad().toString());

            for (Usuario objUsuario : lstUsuario) {
                    if (objUsuario.getJefe()!=null  &&  objUsuario.getJefe().getIdusuario()!=null && objUsuario.getJefe().getIdusuario().toString().equals(usuario_.getIdusuario().toString())){
                        isJefe = true;
                            break;
                    }
            }

            if (!isJefe){
                    lstUsuario = srvUsuario.getUsuariosByUnidad( usuario_.getUnidad().getIdunidad().toString());
                    for (Usuario objUsuario : lstUsuario) {
                            Map<String, String> datos = new HashMap<String, String>();
                        if (objUsuario.getUsuariofinal()=='S'){
                                    datos.put("id", String.valueOf(objUsuario.getIdusuario().intValue()));
                                datos.put("label", objUsuario.getNombres() + " " + objUsuario.getApellidos());
                                l1.add(datos);
                        }
                    }
            }else{
                     for (Usuario objUsuario : lstUsuario) {
                             Map<String, String> datos = new HashMap<String, String>();

                             if ((objUsuario.getJefe()!=null  && objUsuario.getJefe().getIdusuario()!=null && usuario_.getEstado().equals("A") &&  objUsuario.getUsuariofinal()=='S' && objUsuario.getJefe().getIdusuario().toString().equals(usuario_.getIdusuario().toString())) ||
                                 (usuario_.getEstado().equals("A") && objUsuario.getIdusuario().toString().equals(usuario_.getIdusuario().toString()))){
                               datos.put("id", String.valueOf(objUsuario.getIdusuario().intValue()));
                               datos.put("label", objUsuario.getNombres() + " " + objUsuario.getApellidos());
                               l1.add(datos);
                             }
                     }
            }

            enviaDatos(l1);
       }catch(Exception ex){
    	   ex.printStackTrace();
       }
    }

    public void autocompletarUsuarioFinal() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - autocompletarUsuarioFinal():enviaDatos() ");

        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
        List<Usuario> lstUsuario = srvUsuario.getUsuarios();

        for (Usuario objUsuario : lstUsuario) {
            Map<String, String> datos = new HashMap<String, String>();

            datos.put("id", String.valueOf(objUsuario.getIdusuario().intValue()));
            datos.put("label", objUsuario.getNombres() + " " + objUsuario.getApellidos());

            l1.add(datos);
        }

        enviaDatos(l1);
    }
    
    public void obtenerLegajo()  {
    	log.debug("-> [Action] AutoCompletarAction - obtenerLegajo():enviaDatos() ");

        mapSession = ActionContext.getContext().getSession();
        Usuario usuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
        List<LegajoDocumento> lst = null;

        if (idDocumento!=null){
            Documento d = srvDocumento.findByIdDocumento(idDocumento);
            if (d.getDocumentoreferencia()!=null)
               idDocumento = d.getDocumentoreferencia();

            LegajoDocumento legajoDocumento = new LegajoDocumento();
            legajoDocumento.setIdDocumento(idDocumento);
            lst = legajoDocumentoService.findDocumento(legajoDocumento, usuario);
        }
        
        Map<String, String> datos = new HashMap<String, String>();
        datos.put("id", "0");
        datos.put("label", "No Aplica");
        l1.add(datos);
            
        if (lst!=null && lst.size()>0){        
            for (LegajoDocumento legajoDoc : lst) {
                datos = new HashMap<String, String>();

                Legajo legajo = new Legajo();
                legajo.setEstado("");
                legajo.setIdLegajo(legajoDoc.getIdLegajo());
                legajo = legajoService.findByIdLegajo(legajo);

                datos.put("id", String.valueOf(legajo.getIdLegajo()));
                datos.put("label", legajo.getNroLegajo() + " - " + legajo.getAsunto());
                l1.add(datos);
            }
        }

        enviaDatos(l1);
    }

    public void autocompletarUsuarioDatosFinal() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - autocompletarUsuarioDatosFinal():enviaDatos() ");

        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
        List<Usuario> lstUsuario = srvUsuario.getUsuarios();

        for (Usuario objUsuario : lstUsuario) {
            Map<String, String> datos = new HashMap<String, String>();
            datos.put("id", String.valueOf(objUsuario.getIdusuario().intValue()));
            datos.put("label", objUsuario.getNombres() + " " + objUsuario.getApellidos() + " - " + objUsuario.getUsuario());
            l1.add(datos);
        }

        enviaDatos(l1);
    }
    
    public void autocompletarProveido() throws Exception {
        log.debug("-> [Action] AutoCompletarAction - autocompletarProveido():enviaDatos() ");
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
        List<Proveido> listProveido = proveidoDAO.buscarProveidosActivos();
      
        for(Proveido proveido : listProveido){
            Map<String, String> datos = new HashMap<String, String>();
            datos.put("id", proveido.getIdProveido().toString());
            datos.put("label", proveido.getNombre());
            l1.add(datos);
        }
        
        enviaDatos(l1);
    }

    public void autocompletarAreaDatosFinal() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - autocompletarAreaDatosFinal():enviaDatos() ");

        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
        List<Numeracion> lstNumeracion = srvNumeracion.findAll();

        for (Numeracion objNumeracion : lstNumeracion) {
            Map<String, String> datos = new HashMap<String, String>();
            datos.put("id", String.valueOf(objNumeracion.getUnidad().getIdunidad() + "-" + objNumeracion.getTipodocumento().getIdtipodocumento()));
            datos.put("label", objNumeracion.getUnidad().getNombre() + " - " + objNumeracion.getTipodocumento().getNombre());
            l1.add(datos);
        }

        enviaDatos(l1);
    }

    public void procesoConcesionario() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - procesoConcesionario():enviaDatos() ");

        List<Concesionario> lstConcesionario = srvConcesionario.findAll();
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
        for (Concesionario objConcesionario : lstConcesionario) {
            Map<String, String> datos = new HashMap<String, String>();
            datos.put("id", String.valueOf(objConcesionario.getIdConcesionario().intValue()));
            datos.put("label", objConcesionario.getRazonSocial());
            l1.add(datos);
        }
        enviaDatos(l1);
    }
    
     public void procesoSerie() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - procesoSerie():enviaDatos() ");
        List<Serie> lstSerie = serieDAO.getListSerie("A");
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
        
        for (Serie serie : lstSerie) {
            Map<String, String> datos = new HashMap<String, String>();
            datos.put("id", String.valueOf(serie.getIdserie().intValue()));
            datos.put("label", serie.getNombre());
            l1.add(datos);
        }
        enviaDatos(l1);
    }
     
     
      public void obtenerUnidadesEnumerar() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - obtenerUnidadesEnumerar():enviaDatos() ");
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
        
        try{
            Usuario usuarioLogeado = null;
            mapSession = ActionContext.getContext().getSession();
            usuarioLogeado = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
            Unidad unidad = unidadDAO.findByIdunidad(usuarioLogeado.getIdUnidadPerfil());

            Map<String, String> datos = new HashMap<String, String>();
            datos.put("id", String.valueOf(unidad.getIdunidad()));
            datos.put("label", unidad.getNombre());
            l1.add(datos);

            if (unidad.getUnidadgrupo()!=null){
                unidad = unidadDAO.findByIdunidad(unidad.getUnidadgrupo());
                datos = new HashMap<String, String>();
                datos.put("id", String.valueOf(unidad.getIdunidad()));
                datos.put("label", unidad.getNombre());
                l1.add(datos);
            }
        }catch(Exception e){
            e.printStackTrace();
        } 
        
        enviaDatos(l1);
    }


    public void obtenerProcesosVigentesN2() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - obtenerProcesosVigentesN2():enviaDatos() ");

        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
		mapSession = ActionContext.getContext().getSession();
		Usuario objUsuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
		objUsuario = srvUsuario.findByIdUsuario(objUsuario.getIdusuario());
        try {
        	List<ProcesoDTO> listaProcesos = srvProceso.buscarProcesosDTOxUsuarioParticipanteN2(objUsuario);

            String tipoProcesoPiloto = srvParametro.findByTipoUnico(Constantes.TIPO_PROCESO_PILOTO).getValor();
            String tipoProcesoProduccion = srvParametro.findByTipoUnico(Constantes.TIPO_PROCESO_PRODUCCION).getValor();


            for (ProcesoDTO proceso : listaProcesos) {
                Map<String, String> datos = new HashMap<String, String>();

                datos.put("id", (proceso.getIdProceso() == null ? "" : proceso.getIdProceso().toString()) );
                datos.put("label", proceso.getNombre());
                datos.put("proceso", proceso.getNombre());
                datos.put("areadestino", proceso.getUnidadNombre());
                datos.put("destinatario", proceso.getResponsableNombres() + " " + proceso.getResponsableApellidos());
                datos.put("idresponsable", (proceso.getResponsableIdUsuario() == null ? "" : proceso.getResponsableIdUsuario().toString()) );
                datos.put("tipoproceso", proceso.getTipoNombre());
                datos.put("permitesumilla", (proceso.getPermiteSumilla() ==null ? "" : proceso.getPermiteSumilla().toString()) );
                datos.put("unidad", objUsuario.getUnidad().getIdunidad().toString() );
                datos.put("ambiente", (proceso.getProduccion() == null ? "N" : proceso.getProduccion().toString()) );
                datos.put("nombreambiente", ( (proceso.getProduccion() == null || proceso.getProduccion().equals('N')) ? tipoProcesoPiloto : tipoProcesoProduccion) );
                datos.put("codigo", proceso.getCodigo());

                if (log.isDebugEnabled()) {
                    log.debug("proceso [" + datos.get("proceso") + "] ambiente [" + datos.get("ambiente") + "] real [" + proceso.getProduccion() + "]");
                }

                l1.add(datos);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error al armar lista de procesos para autocompletar.", e);
        }
        
        enviaDatos(l1);
    }

    public void obtenerProcesosVigentes() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - obtenerProcesosVigentes ");

        List<Proceso> listaProcesos = srvProceso.getLstAutoCompleteVigente();
        String tipoProcesoPiloto = srvParametro.findByTipoUnico(Constantes.TIPO_PROCESO_PILOTO).getValor();
        String tipoProcesoProduccion = srvParametro.findByTipoUnico(Constantes.TIPO_PROCESO_PRODUCCION).getValor();

        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();

        Map<String, String> datosT = new HashMap<String, String>();
    	datosT.put("id", Constantes.ID_AREA);
    	datosT.put("label", Constantes.TODOS_AREA);
    	l1.add(datosT);

        for (Proceso objProceso : listaProcesos) {
            Map<String, String> datos = new HashMap<String, String>();
            datos.put("id", String.valueOf(objProceso.getIdproceso().intValue()));
            datos.put("label", objProceso.getNombre());
            datos.put("proceso", objProceso.getNombre());
            datos.put("areadestino", objProceso.getsUnidad());
            datos.put("destinatario", objProceso.getsResNombres() + " " + objProceso.getsResApelidos());
            datos.put("idresponsable", objProceso.getiIdResponsable().toString());
            datos.put("tipoproceso", objProceso.getsTipoProceso().toLowerCase());
            datos.put("permitesumilla", String.valueOf(objProceso.getPermitesumilla()));
            datos.put("ambiente", String.valueOf((objProceso.getProduccion() == null) ? 'N' : objProceso.getProduccion()));
            datos.put("nombreambiente", objProceso.getProduccion() == null || objProceso.getProduccion().toString().equals("N") ? tipoProcesoPiloto : tipoProcesoProduccion);

            if (objProceso.getsTipoProceso().toLowerCase().equals(Constantes.PROCESO_STOR)) {
                String sTipoDocumento = null;

                if (objProceso.getCodigo().toLowerCase().equals(Constantes.PROCESO_STOR_CODIGO_APELACION)) {
                    sTipoDocumento = Constantes.TIPO_DOCUMENTO_STOR_APELACION;
                } else if (objProceso.getCodigo().toLowerCase().equals(Constantes.PROCESO_STOR_CODIGO_QUEJA)) {
                    sTipoDocumento = Constantes.TIPO_DOCUMENTO_STOR_QUEJA;
                } else if (objProceso.getCodigo().toLowerCase().equals(Constantes.PROCESO_STOR_CODIGO_MEDIDACAUTELAR)) {
                    sTipoDocumento = Constantes.TIPO_DOCUMENTO_STOR_MEDIDACAUTELAR;
                }
                // log.debug("Codigo de Proceso ["+objProceso.getCodigo()+"] Tipo de Documento ["+sTipoDocumento+"]");
                if (sTipoDocumento != null) {
                    Tipodocumento tipoDocumento = tipoDocumentoService.buscarObjPor(sTipoDocumento);
                    if (tipoDocumento != null) {
                        datos.put("idtipodocumento", tipoDocumento.getIdtipodocumento().toString());
                    }

                }
            }
            l1.add(datos);
        }
        enviaDatos(l1);
     
    }


    public void obtenerProcesosMP() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - obtenerProcesosMP():enviaDatos() ");

        List<Proceso> listaProcesos = srvProceso.getLstAutoComplete();
        log.debug("Se encontraron " + listaProcesos.size() + " procesos.");

        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();

        if(mode!= null && mode.equals("todos")){
        	Map<String, String> datosT = new HashMap<String, String>();
        	datosT.put("id", Constantes.ID_AREA);
        	datosT.put("label", Constantes.TODOS_AREA);
        	l1.add(datosT);
        }

        for (Proceso objProceso : listaProcesos) {
            Map<String, String> datos = new HashMap<String, String>();
            datos.put("id", String.valueOf(objProceso.getIdproceso().intValue()));
            datos.put("label", objProceso.getNombre());
            l1.add(datos);
        }
        enviaDatos(l1);
    }

    public void procesoMesaPartes() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - procesoMesaPartes():enviaDatos() ");

        List<Proceso> listaProcesos = srvProceso.getLstAutoComplete();
        log.debug("Se encontraron " + listaProcesos.size() + " procesos.");

        String tipoProcesoPiloto = srvParametro.findByTipoUnico(Constantes.TIPO_PROCESO_PILOTO).getValor();
        String tipoProcesoProduccion = srvParametro.findByTipoUnico(Constantes.TIPO_PROCESO_PRODUCCION).getValor();


        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
        for (Proceso objProceso : listaProcesos) {
            Map<String, String> datos = new HashMap<String, String>();
            datos.put("id", String.valueOf(objProceso.getIdproceso().intValue()));
            datos.put("label", objProceso.getNombre());
            datos.put("proceso", objProceso.getNombre());
            datos.put("areadestino", objProceso.getsUnidad());
            datos.put("destinatario", objProceso.getsResNombres() + " " + objProceso.getsResApelidos());
            datos.put("idresponsable", objProceso.getiIdResponsable().toString());
            datos.put("tipoproceso", objProceso.getsTipoProceso().toLowerCase());
            datos.put("permitesumilla", String.valueOf(objProceso.getPermitesumilla()));
            datos.put("ambiente", String.valueOf((objProceso.getProduccion() == null) ? 'N' : objProceso.getProduccion()));
            datos.put("nombreambiente", objProceso.getProduccion() == null || objProceso.getProduccion().toString().equals("N") ? tipoProcesoPiloto : tipoProcesoProduccion);
            log.trace("proceso {} ambiente {} real {}", new Object[]{datos.get("proceso"), datos.get("ambiente"), objProceso.getProduccion()});

            if (objProceso.getsTipoProceso().toLowerCase().equals(Constantes.PROCESO_STOR)) {
                String sTipoDocumento = null;

                if (objProceso.getCodigo().toLowerCase().equals(Constantes.PROCESO_STOR_CODIGO_APELACION)) {
                    sTipoDocumento = Constantes.TIPO_DOCUMENTO_STOR_APELACION;
                } else if (objProceso.getCodigo().toLowerCase().equals(Constantes.PROCESO_STOR_CODIGO_QUEJA)) {
                    sTipoDocumento = Constantes.TIPO_DOCUMENTO_STOR_QUEJA;
                } else if (objProceso.getCodigo().toLowerCase().equals(Constantes.PROCESO_STOR_CODIGO_MEDIDACAUTELAR)) {
                    sTipoDocumento = Constantes.TIPO_DOCUMENTO_STOR_MEDIDACAUTELAR;
                }
                // log.debug("Codigo de Proceso ["+objProceso.getCodigo()+"] Tipo de Documento ["+sTipoDocumento+"]");
                if (sTipoDocumento != null) {
                    Tipodocumento tipoDocumento = tipoDocumentoService.buscarObjPor(sTipoDocumento);
                    if (tipoDocumento != null) {
                        datos.put("idtipodocumento", tipoDocumento.getIdtipodocumento().toString());
                    }

                }
            }
            l1.add(datos);
        }
        enviaDatos(l1);
    }

    public void procesoNuevoDocumentoN() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - procesoNuevoDocumentoN():enviaDatos() ");

        log.debug("tipo de proceso listar [" + this.origenExpediente + "]");

        List<Proceso> listaProcesos = srvProceso.getProcesoListxOrigen(this.origenExpediente);
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();

        for (Proceso objProceso : listaProcesos) {
            Map<String, String> datos = new HashMap<String, String>();
            datos.put("id", String.valueOf(objProceso.getIdproceso().intValue()));
            datos.put("label", objProceso.getNombre());
            datos.put("proceso", objProceso.getNombre());
            datos.put("areadestino", objProceso.getResponsable().getUnidad().getNombre());
            datos.put("destinatario", objProceso.getResponsable().getNombres() + " " + objProceso.getResponsable().getApellidos());
            datos.put("idresponsable", objProceso.getResponsable().getIdusuario().toString());
            datos.put("tipoproceso", objProceso.getTipoproceso().getNombre().toLowerCase());
            l1.add(datos);
        }
        enviaDatos(l1);

    }

    public void buscarLegajo() throws Exception {
        log.debug("-> [Action] AutoCompletarAction - buscarLegajo():enviaDatos() ");
        
        mapSession = ActionContext.getContext().getSession();
        Usuario usuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
      
        sNroExpediente = (sNroExpediente == null) ? "" : sNroExpediente;
        sNroHT  = (sNroHT == null) ? "" : sNroHT;
        tipoDocumento = (tipoDocumento == null) ? null : tipoDocumento;
	sNroDocumento = (sNroDocumento == null) ? "" : sNroDocumento; 
        List<Legajo> lstLegajo = legajoService.findByCriteria(usuario, sNroExpediente, sNroHT, tipoDocumento, sNroDocumento, sTipoLegajo);
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
        
        if (lstLegajo!=null){
            for (Legajo legajo : lstLegajo) {
                Map<String, String> datos = new HashMap<String, String>();
                datos.put("id", String.valueOf(legajo.getIdLegajo().intValue()));
                datos.put("nroLegajo", legajo.getNroLegajo());
                datos.put("tipoLegajo", legajo.getTipoLegajo().getDescripcion());
                datos.put("unidadLegajo", legajo.getUnidad().getNombre());
                datos.put("asuntoLegajo", legajo.getAsunto());
                l1.add(datos);
            }
        }
        
        enviaDatos(l1);
    }

    public void buscarDocumentoReferenciado() throws Exception {
        log.debug("-> [Action] AutoCompletarAction - buscarDocumentoReferenciado():enviaDatos() ");
         
        String[] sqlQueryDinamico = new String[1]; sqlQueryDinamico[0] = "";
        List<Documento> lstDocumento = srvDocumento.busquedaDocumentoAvanzada(null,
        		sNroDocumento, null, null,
    			sNroExpediente, null, "",
    			null, null, null, null,
    			null, tipoDocumento==null?null:tipoDocumento.toString(), null,
    			null, null, null,
    			null, null, null, null , sqlQueryDinamico, sNroHT, sNroRS, sNroLegajo,sTipoLegajo, "busqueda", null, null, null);

        if (lstDocumento == null) {
            log.info("No se encontro ningun documento");
            return;
        }
        
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();

        for (Documento documento : lstDocumento) {
            if (documento.getEstado()!='N'){
                Map<String, String> datos = new HashMap<String, String>();
                datos.put("id",  documento.getIdDocumento().toString());
                datos.put("label", documento.getAsunto());
                datos.put("nroDocumento", documento.getTipoDocumento().getNombre() + " - " + documento.getNumeroDocumento());
                datos.put("asuntoDocumento", documento.getAsunto());
                l1.add(datos);
            }
        }

        enviaDatos(l1);
    }

    public void buscarDocumentoFedatario() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - buscarDocumentoFedatario():enviaDatos() ");

    	String[] sqlQueryDinamico = new String[1]; sqlQueryDinamico[0] = "";
        List<Documento> lstDocumento = srvDocumento.busquedaDocumentoAvanzada(null,
        		sNroDocumento, null, null,
    			null, null, "",
    			null, null, null, null,
    			null, tipoDocumento.toString(), null,
    			null, null, null,
    			null, null, null, idUnidad.toString(), sqlQueryDinamico, null,null,null,null, "busqueda", null, null, null);

        if (lstDocumento == null) {
            log.info("No se encontro ningun documento");
            return;
        }

        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();

        for (Documento documento : lstDocumento) {
            Map<String, String> datos = new HashMap<String, String>();
            datos.put("id",  documento.getIdDocumento().toString());
            datos.put("label", documento.getAsunto());
            datos.put("nroexpediente", documento.getExpediente().getNroexpediente());
            datos.put("nrodocumento", documento.getTipoDocumento().getNombre() + " - " + documento.getNumeroDocumento());
            datos.put("asunto", documento.getAsunto());
            datos.put("fecha", documento.getFechaCreacion().toString());

            l1.add(datos);
        }

        enviaDatos(l1);
    }


    public void buscarCliente() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - buscarCliente():enviaDatos() ");

        if (isEmpty(sFiltroBusqueda)) {
            return;
        }

        if (log.isDebugEnabled()) {
            log.debug("filtro de Busqueda [" + sFiltroBusqueda + "]");
        }

        List<Cliente> lstCliente = clienteService.findLstByCriteria(sFiltroBusqueda);

        if (lstCliente == null) {
            log.info("No se encontro ningun cliente");

            return;
        }

        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();

        for (Cliente cliente : lstCliente) {
            Map<String, String> datos = new HashMap<String, String>();

            datos.put("id", cliente.getIdCliente().toString());
            datos.put("label", cliente.getsNombre());
            datos.put("tipoidentificacion", cliente.getsTipoIdentificacion());
            datos.put("numeroidentificacion", cliente.getNumeroIdentificacion());

            l1.add(datos);
        }

        enviaDatos(l1);
    }

    public void buscarPara() throws Exception {
        log.debug("-> [Action] AutoCompletarAction - buscarPara():enviaDatos() ");
        mapSession = ActionContext.getContext().getSession();
        Usuario usuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
        List<Map<String, String>> l1 = usuarioxunidadxfuncionService.getAllUsuarioUnidadFuncion(usuario.getIdUnidadPerfil(), 
                                                                                                funcionService.findByIdFuncion(usuario.getIdFuncionPerfil()).getJefe(),
                                                                                                funcionService.findByIdFuncion(usuario.getIdFuncionPerfil()).getIdfuncion());  
        try{
           
        if (l1 == null || l1.size() <= 0) {
            log.info("No se encontro ningun usuario");
            enviaDatos(new LinkedList<Map<String, String>>());
            return;
        }

        enviaDatos(l1);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void getFavorito() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - getFavorito():enviaDatos() ");

        List<Favorito> lstFavorito = null;
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
        Usuario usuarioLogeado = null;

        mapSession = ActionContext.getContext().getSession();
        usuarioLogeado = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);

        if (log.isDebugEnabled()) {
            log.debug("mode [" + mode + "]");
            log.debug("Buscando favoritos del usuario logeado [" + usuarioLogeado.getUsuario() + "]");
        }

        lstFavorito = favoritoService.findLstBy(usuarioLogeado.getIdUsuarioPerfil(), Constantes.ESTADO_ACTIVO, mode,
                                                usuarioLogeado.getIdUnidadPerfil(),
                                                funcionService.findByIdFuncion(usuarioLogeado.getIdFuncionPerfil()).getJefe(),
                                                funcionService.findByIdFuncion(usuarioLogeado.getIdFuncionPerfil()).getIdfuncion());

        if (lstFavorito != null && lstFavorito.size() > 0) {
            if (log.isDebugEnabled()) {
                log.debug("El usuario [" + usuarioLogeado.getUsuario() + "] tiene [" + lstFavorito.size() + "] favorito(s)");
            }

            for (Favorito favorito : lstFavorito) {
                Map<String, String> mapUsuario = new HashMap<String, String>();
                mapUsuario.put("id", favorito.getId());
                mapUsuario.put("label", favorito.getLabel());
                mapUsuario.put("nombres", favorito.getLabel());
                l1.add(mapUsuario);
            }
        }

        enviaDatos(l1);
    }

    /**REN Busca un expediente para referenciarlo ----------------------------------------------------------------------------*/
    public void buscarExpediente() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - buscarExpediente():enviaDatos() ");
        mapSession = ActionContext.getContext().getSession();
        Usuario usuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
        sNroIdentificacion = (sNroIdentificacion == null) ? "" : sNroIdentificacion;
        sRazonSocial = (sRazonSocial == null) ? "" : sRazonSocial;
        sNroExpediente = (sNroExpediente == null) ? "" : sNroExpediente;
        sAsunto = (sAsunto == null) ? "" : sAsunto;
        sNroHT  = (sNroHT == null) ? "" : sNroHT;
        Date fechaBuscar = null;
        
        if (fecha != null && !fecha.equals("")) {
            fechaBuscar = Constantes.FORAMATEADOR_FECHA.parse(fecha);
        }
       
        tipoBusqueda = (tipoBusqueda == null) ? "" : tipoBusqueda;
        List<Expediente> lstExpediente = expedienteDao.findByCriteria(sNroIdentificacion, sRazonSocial, sNroExpediente, sAsunto, idProceso, fechaBuscar, tipoBusqueda, usuario.getIdusuario(), sNroHT);
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
         
        for (Expediente objExpediente : lstExpediente) {
            Map<String, String> datos = new HashMap<String, String>();
            datos.put("id", objExpediente.getIdexpediente().toString());
            datos.put("label", objExpediente.getNroexpediente());
            datos.put("nroexpediente", objExpediente.getNroexpediente());
            datos.put("tipoidentificacion", objExpediente.getCliente().getTipoIdentificacion().getNombre());
            datos.put("nroidentificacion", objExpediente.getCliente().getNumeroIdentificacion());
            datos.put("asunto", objExpediente.getAsunto());
            datos.put("serie", objExpediente.getSerie().getIdserie().toString());
            datos.put("observacion", objExpediente.getObservacion()==null?"":objExpediente.getObservacion());
            if (objExpediente.getCliente().getRazonSocial()!=null && !objExpediente.getCliente().getRazonSocial().trim().equals("")) { 
                datos.put("razonsocial", objExpediente.getCliente().getRazonSocial());
            }else{
                String nombre  = objExpediente.getCliente().getNombres()==null?"":objExpediente.getCliente().getNombres().trim() + " ";
                String paterno = objExpediente.getCliente().getApellidoPaterno()==null?"":objExpediente.getCliente().getApellidoPaterno().trim() + " ";
                String materno = objExpediente.getCliente().getApellidoMaterno()==null?"":objExpediente.getCliente().getApellidoMaterno().trim();
                datos.put("razonsocial", nombre + paterno + materno);
            }
            
            datos.put("ruc", "");
            datos.put("fecha", new SimpleDateFormat("yyyy-MM-dd").format(objExpediente.getFechacreacion()));
            l1.add(datos);
        }
        enviaDatos(l1);
    }

    public void buscarExpedienteAbierto() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - buscarExpedienteAbierto():enviaDatos() ");
        
        if (iIdCliente == null) {
            log.error("No se recibio ningun cliente");
            return;
        }
        
        try{
            log.debug("Cliente con ID [" + iIdCliente + "]");
            iniciarFactorias();

            List<Map<String, String>> lstExpediente = expedienteDao.buscarLstPorCliente(iIdCliente, Constantes.ESTADO_ACTIVO);

            if (lstExpediente==null ){
              enviaDatos(new LinkedList<Map<String, String>>());  
            }
            else{
              enviaDatos(lstExpediente);
            }
        }catch(Exception e){
              enviaDatos(new LinkedList<Map<String, String>>());
        }
          
    }

    public void gridAuditoria() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - gridAuditoria():enviaDatos() ");

        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss aaa");
        List<Auditoria> listaAuditoria = srvAuditoria.filtrarAuditoria(sNroExpediente, sUsuario, sModulo, sNroDocumento, sFechaDesde, sFechaHasta);
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
        for (Auditoria objAuditoria : listaAuditoria) {
            Map<String, String> datos = new HashMap<String, String>();
            datos.put("id", objAuditoria.getIdAuditoria().toString());
            datos.put("label", objAuditoria.getTipoAuditoria());
            datos.put("modulo", objAuditoria.getModulo());
            datos.put("opcion", objAuditoria.getOpcion());
            datos.put("campo", objAuditoria.getCampo());
            datos.put("valor", objAuditoria.getNuevoValor());
            datos.put("antiguo", objAuditoria.getAntiguoValor());
            datos.put("actual", objAuditoria.getNuevoValor());
            datos.put("fecha", formatoFecha.format(objAuditoria.getFechaAudioria()));
            datos.put("usuario", objAuditoria.getUsuario());
            if (objAuditoria.getExpediente() != null && objAuditoria.getDocumento() == null && objAuditoria.getProceso() == null) {
                datos.put("expediente", objAuditoria.getExpediente().getNroexpediente());
                datos.put("documento", "");
                datos.put("proceso", "");
            } else if (objAuditoria.getExpediente() != null && objAuditoria.getDocumento() != null && objAuditoria.getProceso() == null) {
                datos.put("expediente", objAuditoria.getExpediente().getNroexpediente());
                datos.put("documento", objAuditoria.getDocumento().getNumeroDocumento());
                datos.put("proceso", "");
            } else if (objAuditoria.getExpediente() == null && objAuditoria.getDocumento() == null && objAuditoria.getProceso() == null) {
                datos.put("expediente", "");
                datos.put("documento", "");
                datos.put("proceso", "");
            } else if (objAuditoria.getExpediente() == null && objAuditoria.getDocumento() == null && objAuditoria.getProceso() == null) {
                datos.put("expediente", "");
                datos.put("documento", "");
                datos.put("proceso", "");
            } else if (objAuditoria.getExpediente() == null && objAuditoria.getDocumento() == null && objAuditoria.getProceso() != null) {
                datos.put("expediente", "");
                datos.put("documento", "");
                datos.put("proceso", objAuditoria.getProceso().getNombre());
            }
            l1.add(datos);
        }
        enviaDatos(l1);
    }

    /**REN Llena los datos del proceso en un map, usado para posteriores comprobaciones --------------------------------------*/
    public void procesoNuevoDocumentoN2() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - procesoNuevoDocumentoN2():enviaDatos() ");

        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
        mapSession = ActionContext.getContext().getSession();
	Usuario objUsuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
	objUsuario = srvUsuario.findByIdUsuario(objUsuario.getIdusuario());
        
        try {
                List<ProcesoDTO> listaProcesos = srvProceso.buscarProcesosDTOxUsuarioParticipante(objUsuario);
                //List<Procesos> listaProcesos = srvProceso.getProcesosActivos();
                String tipoProcesoPiloto = srvParametro.findByTipoUnico(Constantes.TIPO_PROCESO_PILOTO).getValor();
                String tipoProcesoProduccion = srvParametro.findByTipoUnico(Constantes.TIPO_PROCESO_PRODUCCION).getValor();

                for (ProcesoDTO proceso : listaProcesos) {
                    Map<String, String> datos = new HashMap<String, String>();
                    datos.put("id", (proceso.getIdProceso() == null ? "" : proceso.getIdProceso().toString()) );
                    datos.put("label", proceso.getNombre());
                    datos.put("proceso", proceso.getNombre());
                    datos.put("areadestino", proceso.getUnidadNombre());
                    datos.put("destinatario", proceso.getResponsableNombres() + " " + proceso.getResponsableApellidos());
                    datos.put("idresponsable", (proceso.getResponsableIdUsuario() == null ? "" : proceso.getResponsableIdUsuario().toString()) );
                    datos.put("tipoproceso", proceso.getTipoNombre());
                    datos.put("permitesumilla", (proceso.getPermiteSumilla() ==null ? "" : proceso.getPermiteSumilla().toString()) );
                    //datos.put("unidad", (proceso.getUnidadId() == null ? "" : proceso.getUnidadId().toString()) );
                    datos.put("unidad", objUsuario.getUnidad().getIdunidad().toString() );
                    datos.put("ambiente", (proceso.getProduccion() == null ? "N" : proceso.getProduccion().toString()) );
                    datos.put("nombreambiente", ( (proceso.getProduccion() == null || proceso.getProduccion().equals('N')) ? tipoProcesoPiloto : tipoProcesoProduccion) );
                    datos.put("codigo", proceso.getCodigo());

                    if (log.isDebugEnabled()) {
                        log.debug("proceso [" + datos.get("proceso") + "] ambiente [" + datos.get("ambiente") + "] real [" + proceso.getProduccion() + "]");
                    }

                    l1.add(datos);
                }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error al armar lista de procesos para autocompletar.", e);
        }

        enviaDatos(l1);
    }
    
    public void setearAdjuntos(){
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
        mapSession = ActionContext.getContext().getSession();
        mapSession.put("AdjuntosDocumento", new ArrayList<Item>());
        enviaDatos(l1);
    }
    

    public void gridSupervisor() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - gridSupervisor():enviaDatos() ");

        iniciarFactorias();
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        SupervisorService supervisorService = serviceFactory.getSupervisorService();
        List<Supervisor> superList = supervisorService.findAll();
        log.debug("Lista de Supervisor con size [" + superList.size() + "]");
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
        for (Supervisor supervisor : superList) {
            Map<String, String> datos = new HashMap<String, String>();
            datos.put("id", String.valueOf(supervisor.getIdsupervisor().intValue()));
            log.debug("ID [" + datos.get("id") + "]");
            // log.debug(String.valueOf(supervisor.getIdsupervisor().intValue()));
            datos.put("label", supervisor.getExpediente().getNroexpediente());
            // log.debug(supervisor.getExpediente().getNroexpediente());
            log.debug("Nro Expediente [" + datos.get("label") + "]");
            datos.put("documento", supervisor.getDocumento().getNumeroDocumento());
            log.debug("Nro Documento [" + datos.get("documento") + "]");
            // log.debug(String.valueOf(supervisor.getDocumento().getIddocumento().intValue()));
            datos.put("usuario", supervisor.getUsuario().getNombres() + " " + supervisor.getUsuario().getApellidos());
            log.debug("Usuario [" + datos.get("usuario") + "]");
            // log.debug(supervisor.getUsuario().getNombres());
            datos.put("fechaSol", ((supervisor.getFechadsolicitud() != null) ? (formatoFecha.format(supervisor.getFechadsolicitud())) : ""));
            log.debug("Fecha Solicitud [" + datos.get("fechaSol") + "]");
            // log.debug("fechaSol: "+
            // ((supervisor!=null&&supervisor.getFechadsolicitud()!=null)?(formatoFecha.format(
            // supervisor.getFechadsolicitud())):"") );
            datos.put("fechaEnt", ((supervisor.getFechadentrega() != null) ? (formatoFecha.format(supervisor.getFechadentrega())) : ""));
            log.debug("Fecha Entrega [" + datos.get("fechaEnt") + "]");
            // log.debug("fechaEnt:"+
            // ((supervisor!=null&&supervisor.getFechadentrega()!=null)?(formatoFecha.format(supervisor.getFechadentrega())):""));
            datos.put("fechaEvo", ((supervisor.getFechadevolucion() != null) ? formatoFecha.format(supervisor.getFechadevolucion()) : ""));
            log.debug("Fecha Devolucion [" + datos.get("fechaEvo") + "]");
            // log.debug("fechaEvo:"+((supervisor!=null&&supervisor.getFechadevolucion()!=null)?formatoFecha.format(supervisor.getFechadevolucion()):""));
            datos.put("caja", supervisor.getCaja());
            log.debug("Nro Caja [" + datos.get("caja") + "]");
            // log.debug(supervisor.getCaja());
            // datos.put("estado",supervisor.getEstado());
            // log.debug(supervisor.getEstado());
            l1.add(datos);
        }
        enviaDatos(l1);
    }

    public void gridMensajeria() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - gridMensajeria():enviaDatos() ");

        iniciarFactorias();
        // int aviso=0;
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        MensajeriaService mensajeriaService = serviceFactory.getMensajeriaService();
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
        if (getEstado() != null) {
            List<Mensajeria> lismensajeria = mensajeriaService.ViewEstado(getEstado());
            for (Mensajeria objMensajeria : lismensajeria) {
                Map<String, String> datos = new HashMap<String, String>();
                datos.put("id", String.valueOf(objMensajeria.getIdmensajeria()));
                datos.put("label", objMensajeria.getIdusuario().getNombres() + " " + objMensajeria.getIdusuario().getApellidos());
                datos.put("numinterno", objMensajeria.getNumerointerno());
                datos.put("tipodoc", objMensajeria.getTipodocumento());
                datos.put("numdoc", objMensajeria.getNumerodocumento());
                datos.put("destinatario", objMensajeria.getDestinatario());
                datos.put("direcciondestino", objMensajeria.getDirecciondestino());
                datos.put("asunto", objMensajeria.getAsunto());
                if (objMensajeria.getEstado() == 'A') {
                    datos.put("estado", "PENDIENTE");
                } else {
                    datos.put("estado", "ENTREGADO");
                }
                if (objMensajeria.getEstadoingreso() == '0') {
                    datos.put("etapa", "MENSAJERIA");
                }
                if (objMensajeria.getEstadoingreso() == '1') {
                    datos.put("etapa", "ENVIADO");
                }
                if (objMensajeria.getEstadoingreso() == '2') {
                    datos.put("etapa", "CARGO RECIBIDO");
                }
                if (objMensajeria.getEstadoingreso() == '3') {
                    datos.put("etapa", "ENTREGADO");
                }
                datos.put("fecha", formatoFecha.format(objMensajeria.getFechaderivacion()));
                datos.put("idusu", objMensajeria.getIdusuario().getIdusuario().toString());
                l1.add(datos);
            }
        }
        if (getIdusuario() != null) {
            int Idusuario = Integer.parseInt(getIdusuario());
            List<Mensajeria> lismensajeria = mensajeriaService.findidusuario(Idusuario);
            for (Mensajeria objMensajeria : lismensajeria) {
                Map<String, String> datos = new HashMap<String, String>();
                datos.put("id", String.valueOf(objMensajeria.getIdmensajeria()));
                datos.put("label", objMensajeria.getIdusuario().getNombres() + " " + objMensajeria.getIdusuario().getApellidos());
                datos.put("numinterno", objMensajeria.getNumerointerno());
                datos.put("tipodoc", objMensajeria.getTipodocumento());
                datos.put("numdoc", objMensajeria.getNumerodocumento());
                datos.put("destinatario", objMensajeria.getDestinatario());
                datos.put("direcciondestino", objMensajeria.getDirecciondestino());
                datos.put("asunto", objMensajeria.getAsunto());
                if (objMensajeria.getEstado() == 'A') {
                    datos.put("estado", "PENDIENTE");
                } else {
                    datos.put("estado", "ENTREGADO");
                }
                if (objMensajeria.getEstadoingreso() == '0') {
                    datos.put("etapa", "MENSAJERIA");
                }
                if (objMensajeria.getEstadoingreso() == '1') {
                    datos.put("etapa", "ENVIADO");
                }
                if (objMensajeria.getEstadoingreso() == '2') {
                    datos.put("etapa", "CARGO RECIBIDO");
                }
                if (objMensajeria.getEstadoingreso() == '3') {
                    datos.put("etapa", "ENTREGADO");
                }
                datos.put("fecha", formatoFecha.format(objMensajeria.getFechaderivacion()));
                datos.put("idusu", objMensajeria.getIdusuario().getIdusuario().toString());
                l1.add(datos);
            }
        }
        enviaDatos(l1);
    }

    public void gridReporte() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - gridReporte():enviaDatos() ");

        List<Item> listaItem = srvTrazabilidadDocumento.filtrarReporteDocumentos(sFechaDesde, sFechaHasta, sDiasReporte, idSede, idProceso);
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
        Integer i = 1;
        for (Item objItem : listaItem) {
            Map<String, String> datos = new HashMap<String, String>();
            datos.put("id", (i++).toString());
            datos.put("label", objItem.getReporteDIG() != null ? objItem.getReporteDIG().toString() : "");
            datos.put("hora", objItem.getHorarioReporte() != null ? objItem.getHorarioReporte().toString() : "");
            datos.put("mp", objItem.getReporteMP() != null ? objItem.getReporteMP().toString() : "");
            datos.put("dig", objItem.getReporteDIG() != null ? objItem.getReporteDIG().toString() : "");
            datos.put("qas", objItem.getReporteQAS() != null ? objItem.getReporteQAS().toString() : "");
            datos.put("folios", objItem.getReporteFolios() != null ? objItem.getReporteFolios().toString() : "");
            l1.add(datos);
        }
        enviaDatos(l1);
    }

    public String exportarExcel() {
    	log.debug("-> [Action] AutoCompletarAction - exportarExcel():String ");

        List<Item> listaItem = srvTrazabilidadDocumento.filtrarReporteDocumentos(sFechaDesde, sFechaHasta, sDiasReporte, idSede, idProceso);
        HttpServletResponse response = ServletActionContext.getResponse();
        StringBuilder html = obtenerExcel(listaItem);
        String content;

        try {
            content = new String(html.toString().getBytes("UTF-8"), "UTF-8");
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Content-Type", "application/vnd.ms-excel");
            response.addHeader("Content-Disposition", "inline; filename=\"ReporteExportacion.xls\"");
            ServletOutputStream out;
            out = response.getOutputStream();
            out.write(content.getBytes());
            out.flush();
        } catch (IOException e1) {
            log.error(e1.getMessage(), e1);
        }

        return null;
    }

    private StringBuilder obtenerExcel(List<Item> listaItem) {
    	log.debug("-> [Action] AutoCompletarAction - obtenerExcel():StringBuilder ");

        StringBuilder cadena = new StringBuilder();
        Integer cabecera = 0;
        cadena.append("<html><table><tr></tr>");
        for (Item objItem : listaItem) {
            if (cabecera == 1) {
                cadena.append("<tr><td WIDTH=15></td><td WIDTH=133>Hora</td><td WIDTH=133>Ingresados a MP</td><td WIDTH=133>Ingresados a DIG</td><td WIDTH=133>Ingresados a QAS</td><td WIDTH=50>Nro Folios</td></tr>");
            }
            cabecera = cabecera + 1;
            cadena.append("<tr><td></td><td>");
            cadena.append(objItem.getHorarioReporte() != null ? objItem.getHorarioReporte().toString() : "");
            cadena.append("</td><td>");
            cadena.append(objItem.getReporteMP() != null ? objItem.getReporteMP().toString() : "");
            cadena.append("</td><td>");
            cadena.append(objItem.getReporteDIG() != null ? objItem.getReporteDIG().toString() : "");
            cadena.append("</td><td>");
            cadena.append(objItem.getReporteQAS() != null ? objItem.getReporteQAS().toString() : "");
            cadena.append("</td><td>");
            cadena.append(objItem.getReporteFolios() != null ? objItem.getReporteFolios().toString() : "");
            cadena.append("</td></tr>");
            if (objItem.getHorarioReporte() != null && objItem.getHorarioReporte().equals("TOTAL")) {
                cadena.append("<tr></tr>");
                cabecera = 0;
            }
        }
        cadena.append("</table></html>");
        return cadena;
    }


    public void dataDerivarTo() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - dataDerivarTo():void ");

        setMapSession(ActionContext.getContext().getSession());
        Integer[] arrIdDoc = (Integer[]) getMapSession().get("arrIdDoc");

        if (getSTipoDerivacion().equals(Constantes.DERIVAR_NORMAL)){
           if (sOpcion.equals(Constantes.RECHAZAR)){
              mapSession = ActionContext.getContext().getSession();
              Usuario usuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
              List<Map<String, String>> l1 = usuarioxunidadxfuncionService.getAllUsuario(usuario.getIdUnidadPerfil(), 
                                                                                       funcionService.findByIdFuncion(usuario.getIdFuncionPerfil()).getJefe(),
                                                                                       funcionService.findByIdFuncion(usuario.getIdFuncionPerfil()).getIdfuncion());      
              enviaDatos(l1);      
           }else{ 
                mapSession = ActionContext.getContext().getSession();
                Usuario usuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
                List<Map<String, String>> l1 = usuarioxunidadxfuncionService.getAllUsuarioUnidadFuncion(usuario.getIdUnidadPerfil(), 
                                                                                                     funcionService.findByIdFuncion(usuario.getIdFuncionPerfil()).getJefe(),
                                                                                                     funcionService.findByIdFuncion(usuario.getIdFuncionPerfil()).getIdfuncion());

                enviaDatos(l1);
           }
        } else if (getSTipoDerivacion().equals(Constantes.DERIVAR_MASIVO)) {
            ArrayList<Object> arrayList = srvProceso.cargaUsuarioMasivo(arrIdDoc);
            loadDataDerivarMasivo(arrayList);
        }
    }

    public void obtenerUsuariosCC() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - obtenerUsuariosCC():enviaDatos() ");

        List<Usuario> lstParticipanteCC = null;
        lstParticipanteCC = srvUsuario.getUsuarios();
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
        
        for (Usuario objUsuario : lstParticipanteCC) {
            Map<String, String> reply = new HashMap<String, String>();
            reply.put("id", "USUARIO_" + objUsuario.getIdusuario().toString());
            String nombre = (objUsuario.getApellidos() != null && !objUsuario.getApellidos().equals("") && !objUsuario.getApellidos().equals(" ") ? objUsuario.getNombres() + " " + objUsuario.getApellidos() : objUsuario.getNombres());
            reply.put("label", nombre);
            l1.add(reply);
        }
        enviaDatos(l1);
    }
    
    public void obtenerFiltroInstituciones() throws Exception {
        log.debug("-> [Action] AutoCompletarAction - obtenerFiltroInstituciones():enviaDatos() ");
        List<Cliente>  lst = clienteService.findClienteFiltroInstituciones(desInstitucion);
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
        
        if (lst!=null){
            for (Cliente objCliente : lst) {
                Map<String, String> datos = new HashMap<String, String>();
                datos.put("id", String.valueOf(objCliente.getIdCliente()));
                datos.put("label", objCliente.getRazonSocial());
                l1.add(datos);
            }
        }
                
        enviaDatos(l1);
    }
    
    public void obtenerFiltroInstitucionesxTipo() throws Exception {
        log.debug("-> [Action] AutoCompletarAction - obtenerFiltroInstituciones():enviaDatos() ");
        
        List<Cliente>  lst = clienteService.findClienteFiltroInstitucionesxTipo(desInstitucion, idTipoCliente);
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
        
        if (lst!=null){
            for (Cliente objCliente : lst) {
                Map<String, String> datos = new HashMap<String, String>();
                datos.put("id", String.valueOf(objCliente.getIdCliente()));
                datos.put("label", objCliente.getRazonSocial());
                l1.add(datos);
            }
        }
                
        enviaDatos(l1);
    }

    public void obtenerInstituciones() throws Exception {
        log.debug("-> [Action] AutoCompletarAction - obtenerInstituciones():enviaDatos() ");

        List<Cliente> lstCliente = clienteService.findClienteInstituciones(idTipoCliente);
        List<Map<String, String>> lista = new LinkedList<Map<String, String>>();
        Map<String, String> reply = null;

        for (int i=0;i<lstCliente.size();i++) {
            reply = new HashMap<String, String>();
            reply.put("id", lstCliente.get(i).getIdCliente().toString());
            reply.put("label", lstCliente.get(i).getRazonSocial());
            lista.add(reply);
        }  
        enviaDatos(lista);
      }
    
    /*
    public void obtenerPersonas() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - obtenerPersona():enviaDatos() ");
         
        List<Cliente> lstCliente = clienteService.findClientePersonas();
        List<Map<String, String>> lista = new LinkedList<Map<String, String>>();
        Map<String, String> reply = null;
       
        for (int i=0;i<lstCliente.size();i++) {
            reply = new HashMap<String, String>();
            reply.put("id", lstCliente.get(i).getIdCliente().toString());
            
            String nombres = lstCliente.get(i).getNombres()==null?"":lstCliente.get(i).getNombres().trim();
            String apPaterno = lstCliente.get(i).getApellidoPaterno()==null?"": lstCliente.get(i).getApellidoPaterno().trim();
            String apMaterno = lstCliente.get(i).getApellidoMaterno() == null? "": lstCliente.get(i).getApellidoMaterno().trim();
            reply.put("label", nombres + " "  + apPaterno + " " + apMaterno);
            lista.add(reply);
            
        }  
        enviaDatos(lista);
    }
    */
    public void obtenerRemitentes() throws Exception {
        log.debug("-> [Action] AutoCompletarAction - obtenerRemitentes():enviaDatos() ");
        List<DetalleCliente> lstCliente = detalleClienteService.findByDetalleCliente(iIdCliente);
        List<Map<String, String>> lista = new LinkedList<Map<String, String>>();
        Map<String, String> reply = null;
        
        for (int i=0;i<lstCliente.size();i++) {
            reply = new HashMap<String, String>();
            reply.put("id", lstCliente.get(i).getIdDetalleCliente().toString());//lstCliente.get(i).getIdRemitente().toString());   //JC24    
            String nombres = lstCliente.get(i).getNombres()==null?"":lstCliente.get(i).getNombres().trim();
            String apPaterno = lstCliente.get(i).getApellidoPaterno()==null?"": lstCliente.get(i).getApellidoPaterno().trim();
            String apMaterno = lstCliente.get(i).getApellidoMaterno() == null? "": lstCliente.get(i).getApellidoMaterno().trim();
            reply.put("label", nombres + " "  + apPaterno + " " + apMaterno);
            lista.add(reply);          
        }  
        enviaDatos(lista);
    }
    
    public void obtenerCargoAdministrado() throws Exception {
        log.debug("-> [Action] AutoCompletarAction - obtenerRemitentes():enviaDatos() ");
        CargoAdministrado cargoAdministrado = cargoAdministradoService.findCargoAdministrado(codCargo);
        List<Map<String, String>> lista = new LinkedList<Map<String, String>>();
        Map<String, String> reply = null;
        
        if (cargoAdministrado!=null){
            reply = new HashMap<String, String>();
            reply.put("id", cargoAdministrado.getIdCargoAdministrado().toString());
            reply.put("label", cargoAdministrado.getDesCargo());
            lista.add(reply);
        }
        
        enviaDatos(lista);
    }
     
      public void obtenerCargoRemitente() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - obtenerCargoRemitente():enviaDatos() ");
        List<Map<String, String>> lista = new LinkedList<Map<String, String>>();
        DetalleCliente cliente = detalleClienteService.findByDetalleClienteId(idPersona);
        Map<String, String> reply = null;
        
        if (cliente!=null){
            reply = new HashMap<String, String>();
            CargoAdministrado cargoAdministrado = cargoAdministradoService.findCargoAdministrado(cliente.getIdCargoAdministrado());
            reply.put("id", cargoAdministrado.getIdCargoAdministrado().toString()); 
            reply.put("label", cargoAdministrado.getDesCargo());
            lista.add(reply);
        }  
        
        enviaDatos(lista);
        
       }
   
     public void obtenerCargoPersonaNatural() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - obtenerCargoPersonaNatural():enviaDatos() ");
        Cliente cliente = clienteService.findByIdCliente(iIdCliente);
        List<Map<String, String>> lista = new LinkedList<Map<String, String>>();
        Map<String, String> reply = null;
     
        if (cliente!=null){
            reply = new HashMap<String, String>();
            CargoAdministrado cargoAdministrado = cargoAdministradoService.findCargoAdministrado(cliente.getCodCargoCliente());
            reply.put("id", cargoAdministrado.getIdCargoAdministrado().toString());
            reply.put("label", cargoAdministrado.getDesCargo());
            lista.add(reply);
        }  
        enviaDatos(lista);
    }
    
       public void usuariosPorUnidadOrganica() throws Exception  {

       
        //try {
            List<Usuario> a = null;//srvUsuario.findUsuariosxUniOrg(75);
            List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
            for (Usuario s : a) {
                if (s != null) {
                    Map<String, String> datos = new HashMap<String, String>();
                    datos.put("idusuario", "" + s.getIdusuario());
                    datos.put("nombrescompletos", s.getNombreCompleto() + s.getNombres() + s.getApellidos());
                    System.out.println(s.getNombres() + s.getApellidos());
                    l1.add(datos);
                }
            }
            enviaDatos(l1);
        /*} catch (Exception e) {
            log.error(e.getMessage(), e);
        }*/

    }
  
     
      public void derivarCC() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - derivarCC():enviaDatos() ");

        mapSession = ActionContext.getContext().getSession();
        Usuario usuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
        
        List<Map<String, String>> l1 = usuarioxunidadxfuncionService.getAllUsuarioUnidadFuncion(usuario.getIdUnidadPerfil(), 
                                                                                                funcionService.findByIdFuncion(usuario.getIdFuncionPerfil()).getJefe(),
                                                                                                funcionService.findByIdFuncion(usuario.getIdFuncionPerfil()).getIdfuncion());
        
        List<Map<String, String>> lista = new LinkedList<Map<String, String>>();
        Map<String, String> reply = null;

        for (int i=0;i<l1.size();i++) {
            reply = new HashMap<String, String>();
            reply.put("id", "USUARIO_" + l1.get(i).get("id"));
            reply.put("label", l1.get(i).get("label"));
            lista.add(reply);
        }
        
        enviaDatos(lista);
    }
     

    @SuppressWarnings("unused")
	public void derivarCCMasivo() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - derivarCCMasivo():enviaDatos() ");
        mapSession = ActionContext.getContext().getSession();
        Usuario usuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
        
        List<Map<String, String>> l1 = usuarioxunidadxfuncionService.getAllUsuarioUnidadFuncion(usuario.getIdUnidadPerfil(), 
                                                                                                funcionService.findByIdFuncion(usuario.getIdFuncionPerfil()).getJefe(),
                                                                                                funcionService.findByIdFuncion(usuario.getIdFuncionPerfil()).getIdfuncion());
        //List<Map<String, String>> l1 = usuarioxunidadxfuncionService.getAllUsuarioUnidadFuncion();
        List<Map<String, String>> lista = new LinkedList<Map<String, String>>();
        Map<String, String> reply = null;

        for (int i=0;i<l1.size();i++) {
            reply = new HashMap<String, String>();
            reply.put("id", "USUARIO_" + l1.get(i).get("id"));
            reply.put("label", l1.get(i).get("label"));
            lista.add(reply);
        }
        
        enviaDatos(lista);
        
    }

    public void dataNotificarTo() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - dataNotificarTo():enviaDatos() ");

        if (log.isDebugEnabled()) {
            log.debug("idDocumento [" + idDocumento + "]");
        }

        Documento documento = srvDocumento.findByIdDocumento(idDocumento);
        Proceso proceso = null;//documento.getExpediente().getProceso();
        List<Usuario> lstParticipante = null;//srvProceso.findByIdProceso(proceso.getIdproceso()).getUsuarioList();
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();

        for (Usuario participante : lstParticipante) {
            Map<String, String> reply = new HashMap<String, String>();

            reply.put("id", participante.getIdusuario().toString());
            reply.put("label", participante.getNombres() + " " + participante.getApellidos());
            l1.add(reply);
        }

        if (proceso.getListaList() != null && proceso.getListaList().size() > 0) {
            if (log.isDebugEnabled()) {
                log.debug("El proceso [" + proceso.getNombre() + "] tiene [" + proceso.getListaList().size() + "] lista(s)");
            }

            for (Lista lista : proceso.getListaList()) {
                if (lista.getParticipanteListaList() == null || lista.getParticipanteListaList().size() <= 0) {
                    continue;
                }

                if (log.isDebugEnabled()) {
                    log.debug("Lista [" + lista.getNombre() + "] tiene [" + lista.getParticipanteListaList().size() + "] participante(s)");
                }

                for (Usuario participanteXLista : lista.getParticipanteListaList()) {
                    boolean existe = false;

                    for (Map<String, String> entry : l1) {
                        Integer id = Integer.valueOf(entry.get("id"));

                        if (participanteXLista.getIdusuario().intValue() == id.intValue()) {
                            existe = true;
                            break;
                        }
                    }

                    if (!existe) {
                        if (log.isDebugEnabled()) {
                            log.debug("Agregando usuario [" + participanteXLista.getUsuario() + "]");
                        }

                        Map<String, String> reply = new HashMap<String, String>();
                        reply.put("id", participanteXLista.getIdusuario().toString());
                        reply.put("label", participanteXLista.getNombres() + " " + participanteXLista.getApellidos());
                        l1.add(reply);
                    }
                }
            }
        }

        enviaDatos(l1);
    }

    private void loadDataDerivarMasivo(ArrayList<Object> lstD) {
    	log.debug("-> [Action] AutoCompletarAction - loadDataDerivarMasivo():enviaDatos() ");

        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
        for (int i = 0; i < lstD.size(); i++) {
            Object[] array = (Object[]) lstD.get(i);
            Map<String, String> reply = new HashMap<String, String>();
            reply.put("id", array[0].toString());
            reply.put("label", array[1] + " " + array[2]);
            l1.add(reply);
        }
        enviaDatos(l1);
    }

    private void loadDataDerivar(List<Usuario> lstD) {
    	log.debug("-> [Action] AutoCompletarAction - loadDataDerivar():enviaDatos() ");

        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
        for (Usuario objC : lstD) {
            Map<String, String> reply = new HashMap<String, String>();
            reply.put("id", objC.getIdusuario().toString());
            String nombre = (objC.getApellidos() != null && !objC.getApellidos().equals("") && !objC.getApellidos().equals(" ") ? objC.getNombres() + " " + objC.getApellidos() : objC.getNombres());
            reply.put("label", nombre);
            l1.add(reply);
        }
        enviaDatos(l1);
    }


    public void tipoDocumentoFedatario() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - tipoDocumentoFedatario():enviaDatos() ");

    	mapSession = ActionContext.getContext().getSession();
        Usuario usuario_ = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);

        List<Tipodocumento> listaTipoDocumentos = tipoDocumentoService.findAllGrupoFedatario(1, usuario_.getGrupoFedatario());
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();

        for (Tipodocumento objTipoDocumento : listaTipoDocumentos) {
            Map<String, String> datos = new HashMap<String, String>();
            datos.put("id", String.valueOf(objTipoDocumento.getIdtipodocumento().intValue()));
            datos.put("label", objTipoDocumento.getNombre());
           
            l1.add(datos);
        }
        enviaDatos(l1);
    }
    
    
    public void obtenerTipoInstitucion() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - obtenerTipoInstitucion():enviaDatos() ");

        List<TipoInstitucion> listaTipoInstitucion = tipoInstitucionService.findTipoInstitucion(tipoPersonaJuridica);
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();

        for (TipoInstitucion objTipo : listaTipoInstitucion) {
            Map<String, String> datos = new HashMap<String, String>();
            datos.put("id", String.valueOf(objTipo.getCod_tipoinstitucion()));
            datos.put("label", objTipo.getDes_tipoinstitucion());
            l1.add(datos);
        }
                
        enviaDatos(l1);
    }
    
    /*
    public void obtenerPersona() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - obtenerPersona():enviaDatos() ");

        List<TipoInstitucion> listaTipoInstitucion = tipoInstitucionService.findTipoInstitucion();
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();

        for (TipoInstitucion objTipo : listaTipoInstitucion) {
            Map<String, String> datos = new HashMap<String, String>();
            datos.put("id", String.valueOf(objTipo.getCod_tipoinstitucion()));
            datos.put("label", objTipo.getDes_tipoinstitucion());
            l1.add(datos);
        }
                
        enviaDatos(l1);
    }*/
    
    public void obtenerFiltroCargos() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - obtenerFiltroCargos():enviaDatos() ");
        List<CargoAdministrado> lstCargoAdministrado = cargoAdministradoService.findCargoFiltroAdministrado(desCargo);
        List<Map<String, String>> lista = new LinkedList<Map<String, String>>();
        Map<String, String> reply = null;
        
        for (int i=0;i<lstCargoAdministrado.size();i++) {
            reply = new HashMap<String, String>();
            reply.put("id", lstCargoAdministrado.get(i).getIdCargoAdministrado().toString()); 
            reply.put("label", lstCargoAdministrado.get(i).getDesCargo());
            lista.add(reply);
            
        }  
        enviaDatos(lista);
    }
    
     public void obtenerCliente() throws Exception {
        log.debug("-> [Action] AutoCompletarAction - obtenercliente():enviaDatos() ");
        Cliente  cliente = clienteService.findByIdCliente(iIdCliente);
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
         
        if (cliente!=null){
                Map<String, String> datos = new HashMap<String, String>();
                if (cliente.getCodtipoinstitucion().toString().equals(Constantes.COD_PERSONA_NATURAL)){
                    String nombres = cliente.getNombres() == null? "": cliente.getNombres();
                    String paterno = cliente.getApellidoPaterno()==null?"":cliente.getApellidoPaterno();
                    String materno = cliente.getApellidoMaterno()==null?"":cliente.getApellidoMaterno();
                    datos.put("id", String.valueOf(cliente.getIdCliente()));
                    datos.put("label", (nombres.trim() + " " + paterno.trim() + " " + materno).trim());
                }else{ 
                    datos.put("id", String.valueOf(cliente.getIdCliente()));
                    datos.put("label", cliente.getRazonSocial()); 
                }
                l1.add(datos);
        }
                
        enviaDatos(l1);
        // idPersona
     }
    
     public void obtenerFiltroPersonas() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - obtenerFiltroPersona():enviaDatos() ");
        List<Cliente>  lst = clienteService.findClienteFiltroPersonas(desPersona); 
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
        
        if (lst!=null){
            for (Cliente objCliente : lst) {
                Map<String, String> datos = new HashMap<String, String>();
                String nombres = objCliente.getNombres() == null? "": objCliente.getNombres();
                String paterno = objCliente.getApellidoPaterno()==null?"":objCliente.getApellidoPaterno();
                String materno = objCliente.getApellidoMaterno()==null?"":objCliente.getApellidoMaterno();
                datos.put("id", String.valueOf(objCliente.getIdCliente()));
                datos.put("label", (nombres + " " + paterno + " " + materno).trim());
                l1.add(datos);
            }
        }
                
        enviaDatos(l1);
    }
     
     public void obtenerTipoLegajoBusqueda() throws Exception {
        log.debug("-> [Action] AutoCompletarAction - obtenerTipoLegajoBusqueda():enviaDatos() ");
        
        List<TipoLegajo> listaTipoLegajo = tipoLegajoService.findTipoLegajoBusqueda();
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
       
        if (listaTipoLegajo != null){
            for (TipoLegajo tipoLegajo : listaTipoLegajo) {
                Map<String, String> datos = new HashMap<String, String>();
                datos.put("id", String.valueOf(tipoLegajo.getIdTipo()));
                datos.put("label", tipoLegajo.getDescripcion());
                l1.add(datos);
            }
        }    
                
        enviaDatos(l1);
    } 
     
    public void obtenerTipoLegajo() throws Exception {
        log.debug("-> [Action] AutoCompletarAction - obtenerTipoLegajo():enviaDatos() ");
        
        mapSession = ActionContext.getContext().getSession();
        Usuario usuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
        List<TipoLegajo> listaTipoLegajo = tipoLegajoService.findTipoLegajo(iWithoutStor, usuario.getIdUnidadPerfil());
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
       
        if (listaTipoLegajo != null){
            for (TipoLegajo tipoLegajo : listaTipoLegajo) {
                Map<String, String> datos = new HashMap<String, String>();
                datos.put("id", String.valueOf(tipoLegajo.getIdTipo()));
                datos.put("label", tipoLegajo.getDescripcion());
                l1.add(datos);
            }
        }    
                
        enviaDatos(l1);
    } 
     
    public void obtenerConcesionaria() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - obtenerConcesionaria():enviaDatos() ");

        List<Cliente> listaCliente = clienteService.findAllConcesionaria(iWithoutStor);
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();

       for (Cliente cliente : listaCliente) {
            Map<String, String> datos = new HashMap<String, String>();
            datos.put("id", String.valueOf(cliente.getIdCliente()));
            datos.put("label", cliente.getRazonSocial());
            l1.add(datos);
        }
                
        enviaDatos(l1);
    } 


    public void tipoDocumentoMesaPartes() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - tipoDocumentoMesaPartes():enviaDatos() ");

        List<Tipodocumento> listaTipoDocumentos = tipoDocumentoService.findAll(iWithoutStor);
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();

        for (Tipodocumento objTipoDocumento : listaTipoDocumentos) {
            Map<String, String> datos = new HashMap<String, String>();
            datos.put("id", String.valueOf(objTipoDocumento.getIdtipodocumento().intValue()));
            datos.put("label", objTipoDocumento.getNombre());
            datos.put("tiponumeracion", (Constantes.SIN_NUMERACION));
            l1.add(datos);
        }
                
        enviaDatos(l1);
    }
    
     public void autocompletarEnvioUsuariosDestinatarios() throws Exception {
    	 log.debug("-> [Action] AutoCompletarAction - autocompletarEnvioUsuariosDestinatarios():enviaDatos() ");
         mapSession = ActionContext.getContext().getSession();
         Usuario usuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
         
         List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
         Map<String, String> datos = new HashMap<String, String>();
         List<Usuario> lst = documentoenviadoService.buscarListDestinatarios(usuario);

         datos.put("id", "-1");
         datos.put("label", "Todos");
         l1.add(datos);
        
         if (lst!=null){
            for (Usuario usu : lst) {
               datos = new HashMap<String, String>();
               datos.put("id", usu.getIdusuario().toString());
               datos.put("label", usu.getNombres());
               l1.add(datos);
            }
         }
                
        enviaDatos(l1);
         
     }
      
     public void autocompletarMesesEnviado() throws Exception {
               log.debug("-> [Action] AutoCompletarAction - autocompletarMesesEnviado():enviaDatos() ");       
             
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
        Map<String, String> datos = new HashMap<String, String>();

        datos.put("id", "3");
        datos.put("label", "3 Meses");
        l1.add(datos);
        
        datos = new HashMap<String, String>();
        datos.put("id", "6");
        datos.put("label", "6 Meses");
        l1.add(datos);
        
        datos = new HashMap<String, String>();
        datos.put("id", "9");
        datos.put("label", "9 Meses");
        l1.add(datos);
        
        datos = new HashMap<String, String>();
        datos.put("id", "12");
        datos.put("label", "12 Meses");
        l1.add(datos);
        
        datos = new HashMap<String, String>();
        datos.put("id", "-1");
        datos.put("label", "Todos");
        l1.add(datos);
        
        enviaDatos(l1);
     }
     
     public void autocompletarUsuariosxUnidad() throws Exception {
    	 log.debug("-> [Action] AutoCompletarAction - autocompletarUsuariosxUnidad():enviaDatos() ");

         mapSession = ActionContext.getContext().getSession();
         Usuario usuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
         List<Usuario> lst = null;
         
         if(iWithoutStor == 1){
             if (usuario.getIdRolPerfil().toString().equals("4")){
                lst = srvUsuario.findUsuariosxUnidad(usuario.getIdUnidadPerfil());
            }else{
                Usuario u = srvUsuario.findByIdUsuario(usuario.getIdUsuarioPerfil());
                lst = new ArrayList<Usuario>();
                lst.add(u);
            }
         }else{
            lst = srvUsuario.findUsuariosxUnidad(usuario.getIdUnidadPerfil()); 
         }           
             
         List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
         Map<String, String> datos = new HashMap<String, String>();

         datos.put("id", "-1");
         datos.put("label", "Todos");
         l1.add(datos);
        
         if (lst!=null){
            for (Usuario usu : lst) {
               datos = new HashMap<String, String>();
               datos.put("id", usu.getIdusuario().toString());
               datos.put("label", usu.getNombres());
               l1.add(datos);
            }
         }
                
        enviaDatos(l1);
    }
     
     /*
      public void autocompletarUsuarioAtendido() throws Exception {
    	 log.debug("-> [Action] AutoCompletarAction - autocompletarUsuarioAtendido():enviaDatos() ");

         mapSession = ActionContext.getContext().getSession();
         Usuario usuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
         System.out.println("Datos.....................................1");
         List<Usuario> lst = documentoAtendidoDAO.buscarUsuariosDocumentosAtendidos(usuario);
         System.out.println("Datos.....................................2");
         List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
         Map<String, String> datos = new HashMap<String, String>();

         datos.put("id", "-1");
         datos.put("label", "Todos");
         l1.add(datos);
          System.out.println("Datos.....................................3");
         if (lst!=null){
            for (Usuario usu : lst) {
               datos = new HashMap<String, String>();
               datos.put("id", usu.getIdusuario().toString());
               datos.put("label", usu.getNombres());
               l1.add(datos);
            }
         }
                
        enviaDatos(l1);
    } */
    
     public void tipoDocumentoMesaPartesFiltro() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - tipoDocumentoMesaPartesFiltro():enviaDatos() ");

        //List<Tipodocumento> listaTipoDocumentos = tipoDocumentoService.findAll(iWithoutStor);
         List<Tipodocumento> listaTipoDocumentos = tipoDocumentoService.findAll(0);
         List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
        
         Map<String, String> datos = new HashMap<String, String>();

         datos.put("id", "-1");
         datos.put("label", "Todos");
         datos.put("tiponumeracion", (Constantes.SIN_NUMERACION));
         l1.add(datos);
        
        for (Tipodocumento objTipoDocumento : listaTipoDocumentos) {
            datos = new HashMap<String, String>();
            datos.put("id", String.valueOf(objTipoDocumento.getIdtipodocumento().intValue()));
            datos.put("label", objTipoDocumento.getNombre());
            datos.put("tiponumeracion", (Constantes.SIN_NUMERACION));
            l1.add(datos);
        }
                
        enviaDatos(l1);
    }
    
     public void perfiles() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - Perfiles():enviaDatos() ");  
        mapSession = ActionContext.getContext().getSession();
        Usuario usuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
        List<Usuarioxunidadxfuncion> lista = usuarioxunidadxfuncionService.getUsuarioByUnidadByFuncion(usuario);
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
        
        for (Usuarioxunidadxfuncion obj : lista) {
            Map<String, String> datos = new HashMap<String, String>();
            datos.put("id", String.valueOf(obj.getIdunidad()) + "|" + String.valueOf(obj.getIdfuncion()) + "|" + obj.getIdusuariocargo());
            datos.put("idFuncion", String.valueOf(obj.getIdfuncion()));
            datos.put("label", " " + obj.getDescfuncion() + " [" + obj.getDescunidad()+  "] "  +  (obj.getDatos().equals("")? "":( "[" + obj.getDatos() + "] ")));
            l1.add(datos);
        } 
        
        enviaDatos(l1);
    }

    public void autocompletarTipoDocumentoTodos() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - autocompletarTipoDocumentoTodos():enviaDatos() ");

        List<Tipodocumento> listaTipoDocumentos = tipoDocumentoService.findAll(iWithoutStor);
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
        Map<String, String> datosT = new HashMap<String, String>();
    	datosT.put("id", Constantes.ID_TIPO_DOCUMENTO);
    	datosT.put("label", Constantes.TODOS_TIPO_DOCUMENTO);
    	datosT.put("tiponumeracion", (Constantes.SIN_NUMERACION));
    	l1.add(datosT);
        for (Tipodocumento objTipoDocumento : listaTipoDocumentos) {
            Map<String, String> datos = new HashMap<String, String>();
            datos.put("id", String.valueOf(objTipoDocumento.getIdtipodocumento().intValue()));
            datos.put("label", objTipoDocumento.getNombre());
            datos.put("tiponumeracion", (Constantes.SIN_NUMERACION));
            l1.add(datos);
        }
        enviaDatos(l1);
    }

    
    public void autocompletarAreaDerivacion() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - autocompletarAreaDerivacion():enviaDatos() ");

        //List<Unidad> lstUnidad = this.unidadDAO.findAll();
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
        List<UsuarioDerivacion> list = usuarioxunidadxfuncionService.getUsuarioDerivacion();
        
        for (UsuarioDerivacion objUsuarioDerivacion : list) {
           Map<String, String> datos = new HashMap<String, String>();
           datos.put("id", String.valueOf(objUsuarioDerivacion.getIdentificador()));
           datos.put("label", objUsuarioDerivacion.getNombreUsuario()); 
           l1.add(datos);
        }
        
        enviaDatos(l1);
    }

    public void autocompletarAreaMesaParte() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - autocompletarAreaMTC():enviaDatos() ");

        List<Unidad> lstUnidad = this.unidadDAO.findAll();
        log.debug("(autocompletarAreaDestino) Nº unidades->" + lstUnidad.size());
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
        List<Parametro> listAreaMTC = srvParametro.findByTipo(Constantes.AREAS_CONSULTA_EXTERNOS_TRAMITE);

        for (Unidad objUnidad : lstUnidad) {
        	for(int i=0;i<listAreaMTC.size();i++){
        		if (objUnidad.getIdunidad().toString().equals(listAreaMTC.get(i).getValor())){
		            Map<String, String> datos = new HashMap<String, String>();
		            datos.put("id", String.valueOf(objUnidad.getIdunidad().intValue()));
		            datos.put("label", objUnidad.getNombre());
		            l1.add(datos);
		            break;
        		}
        	}
        }
        enviaDatos(l1);
    }

    public void autocompletarTipoDocumentoMTC() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - autocompletarTipoDocumentoMTC():enviaDatos() ");

        List<Tipodocumento> listaTipoDocumentos = tipoDocumentoService.findAll(iWithoutStor);
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
      //  Map<String, String> datosT = new HashMap<String, String>();
    //	datosT.put("id", Constantes.ID_TIPO_DOCUMENTO);
    //	datosT.put("label", Constantes.TODOS_TIPO_DOCUMENTO);
    //	datosT.put("tiponumeracion", (Constantes.SIN_NUMERACION));
    //	l1.add(datosT);

    	List<Parametro> listTipDocMTC = srvParametro.findByTipo(Constantes.MTC_TIP_DOCUMENTO);

        for (Tipodocumento objTipoDocumento : listaTipoDocumentos) {
        	for(int i = 0;i<listTipDocMTC.size();i++){
        		if (objTipoDocumento.getIdtipodocumento().toString().equals(listTipDocMTC.get(i).getValor())){
		            Map<String, String> datos = new HashMap<String, String>();
		            datos.put("id", String.valueOf(objTipoDocumento.getIdtipodocumento().intValue()));
		            datos.put("label", objTipoDocumento.getNombre());
		            datos.put("tiponumeracion", (Constantes.SIN_NUMERACION));
		            l1.add(datos);
		            break;
        		}
        	}
        }
        enviaDatos(l1);
    }


    public void autocompletarAno() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - autocompletarAno():enviaDatos() ");

        List<Unidad> lstUnidad = this.unidadDAO.findAll();
        log.debug("(autocompletarAreaDestino) Nº unidades->" + lstUnidad.size());
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
        Date d=new Date();
        String max = DateFormatUtils.format(d, "yyyy");
        String min = "2016";

        for (int i= Integer.parseInt(min); i< Integer.parseInt(max) + 1;i++){
        	Map<String, String> datos = new HashMap<String, String>();
    		datos.put("id",  String.valueOf(i));
    		datos.put("label", String.valueOf(i));
    		l1.add(datos);
        }

        enviaDatos(l1);
    }

    public void autocompletarMes() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - autocompletarAno():enviaDatos() ");

    	List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
    	HttpServletRequest request = ServletActionContext.getRequest();
    	MesDAO msdao = (MesDAO) WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext()).getBean("mesDAO");
        List<Mes> lstMs = msdao.findAll();

        for (int i= 0; i< lstMs.size();i++){
        	Map<String, String> datos = new HashMap<String, String>();
    		datos.put("id",  lstMs.get(i).getIdmes().toString());
    		datos.put("label", lstMs.get(i).getMesferiado());
    		l1.add(datos);
        }

        enviaDatos(l1);
    }

    public void autocompletarAreaMTC() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - autocompletarAreaMTC():enviaDatos() ");

        List<Unidad> lstUnidad = this.unidadDAO.findAll();
        log.debug("(autocompletarAreaDestino) Nº unidades->" + lstUnidad.size());
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
        List<Parametro> listAreaMTC = srvParametro.findByTipo(Constantes.MTC_AREA);

        for (Unidad objUnidad : lstUnidad) {
        	for(int i=0;i<listAreaMTC.size();i++){
        		if (objUnidad.getIdunidad().toString().equals(listAreaMTC.get(i).getValor())){
		            Map<String, String> datos = new HashMap<String, String>();
		            datos.put("id", String.valueOf(objUnidad.getIdunidad().intValue()));
		            datos.put("label", objUnidad.getNombre());
		            l1.add(datos);
		            break;
        		}
        	}
        }
        enviaDatos(l1);
    }

    public void autocompletarPrioridadTodos() throws Exception{
    	log.debug("-> [Action] AutoCompletarAction - autocompletarPrioridadTodos():enviaDatos() ");

    	List<Parametro> listaPrioridades = srvParametro.findAll(iWithoutStor);
    	List<Map<String, String>> l1= new LinkedList<Map<String,String>>();
    	Map<String, String> datosT = new HashMap<String, String>();
    	datosT.put("id", Constantes.ID_PRIORIDAD);
    	datosT.put("label", Constantes.TODOS_PRIORIDAD);
    	l1.add(datosT);
    	for (Parametro objParametro: listaPrioridades) {
    		if((String.valueOf(objParametro.getTipo()).trim()).equals("prioridad")){
    			Map<String, String> datos = new HashMap<String,String>();
    			datos.put("id", String.valueOf(objParametro.getValor()));
    			//datos.put("id", String.valueOf(objParametro.getIdparametro().intValue()));
    			datos.put("label", objParametro.getDescripcion());
    			l1.add(datos);
    		}
    	}
    	enviaDatos(l1);
    }

    public void tipoDocumentoAdjuntarConMetadata() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - tipoDocumentoAdjuntarConMetadata():enviaDatos() ");

        List<Tipodocumento> listaTipoDocumentos = tipoDocumentoService.findAll(iWithoutStor);
        //Usuario usuario=(Usuario) ActionContext.getContext().getSession().get(Constantes.SESSION_USUARIO);

        //Map parametros = ActionContext.getContext().getParameters() ;
        //Integer unidadId  = new Integer ( ((String [])parametros.get("unidad"))[0] ) ;
        //Unidad unidad =	unidadService.buscarObjPor(unidadId);

        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();

        for (Tipodocumento objTipoDocumento : listaTipoDocumentos) {
            Map<String, String> datos = new HashMap<String, String>();
            datos.put("id", String.valueOf(objTipoDocumento.getIdtipodocumento().intValue()));
            datos.put("label", objTipoDocumento.getNombre());
            l1.add(datos);
        }
        enviaDatos(l1);

    }

    /**REN Busca numeraciones del documento. Usado en "Subir con Metadata"*/
    @SuppressWarnings("unchecked")
    public void getNumeracion() throws Exception {
        
    	log.debug("-> [Action] AutoCompletarAction - getNumeracion():enviaDatosJson() ");

    	log.debug("Buscando numeracion para un documento");
        iniciarFactorias();
        NumeracionService numeracionService = serviceFactory.getNumeracionService();
        Map<String, Object> parametros = ActionContext.getContext().getParameters();
        Integer unidadId = new Integer(((String[]) parametros.get("unidad"))[0]);
        Integer tipodocumento = new Integer(((String[]) parametros.get("tipodocumento"))[0]);
        UnidadService unidadService = serviceFactory.getUnidadService();
//		Unidad unidad =	unidadService.buscarObjPor(unidadId);
        Unidad unidad = unidadService.findDependenciaByIdunidad(unidadId);
        Numeracion num = numeracionService.findByIdbyUnidad(unidad, tipodocumento);

        //if(num!=null){
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("tiponumeracion", (num != null ? num.getTiponumeracion().toString() : Constantes.SIN_NUMERACION));
        jsonObj.put("firmante", (num != null ? num.getFirmante().toString() : Constantes.SIN_NUMERACION));
        jsonObj.put("destinatario", (num != null ? num.getDestinatario().toString() : Constantes.SIN_NUMERACION));
        enviaDatosJson(jsonObj.toString());
        //}

    }

    public void tipoDocumentoPlantilla() {
    	log.debug("-> [Action] AutoCompletarAction - tipoDocumentoPlantilla():enviaDatos() ");

        iniciarFactorias();
        TipodocumentoService tipoDocumentoService = serviceFactory.getTipoDocumentoService();
        List<Tipodocumento> listaTipoDocumentos = tipoDocumentoService.getTiposPlantilla();
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
        NumeracionService numeracionService = serviceFactory.getNumeracionService();
        Numeracion num = null;
//      UnidadService unidadService =  serviceFactory.getUnidadService();
//      log.debug("iIdUnidad [" + iIdUnidad + "]");
//      Unidad unidad =	unidadService.buscarObjPor(iIdUnidad);

        Usuario usuario = (Usuario) ActionContext.getContext().getSession().get(Constantes.SESSION_USUARIO);
        usuario = srvUsuario.findByIdUsuario(usuario.getIdusuario());

        for (Tipodocumento objTipoDocumento : listaTipoDocumentos) {
            Map<String, String> datos = new HashMap<String, String>();
            datos.put("id", objTipoDocumento.getIdtipodocumento().toString());
            datos.put("label", objTipoDocumento.getNombre());

            num = numeracionService.findByIdbyUnidad(usuario.getUnidad(), objTipoDocumento.getIdtipodocumento());
            //datos.put("tiponumeracion",objTipoDocumento.getTiponumeracion());
            datos.put("tiponumeracion", num.getTiponumeracion().toString());
            datos.put("firmante", (num != null ? num.getFirmante().toString() : Constantes.SIN_NUMERACION));
            l1.add(datos);
        }
        enviaDatos(l1);
    }

    public void listaSedes() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - listaSedes():enviaDatos() ");

        List<Sede> listaSedes = sedeService.obtenerTodo();
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
        for (Sede objSede : listaSedes) {
            Map<String, String> datos = new HashMap<String, String>();
            datos.put("id", String.valueOf(objSede.getIdsede().intValue()));
            datos.put("label", objSede.getNombre());
            l1.add(datos);
        }
        enviaDatos(l1);
    }

    public void listaDepartamentos() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - listaDepartamentos():enviaDatos() ");
        List<Departamento> listaDepartamentos = departamentoService.findAll();
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
     
        for (Departamento objDepartamento : listaDepartamentos) {
            Map<String, String> datos = new HashMap<String, String>();
            datos.put("id", String.valueOf(objDepartamento.getIddepartamento().intValue()));
            datos.put("label", objDepartamento.getNombre());
            l1.add(datos);
        }
        enviaDatos(l1);
    }

    public void listaProvincias() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - listaProvincias():enviaDatos() ");

        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();

        if (!isEmpty(idDepartamento)) {
            List<Provincia> listaProvincias = provinciaService.findLstBy(Integer.valueOf(idDepartamento));
            for (Provincia obj : listaProvincias) {
                Map<String, String> datos = new HashMap<String, String>();
                datos.put("id", String.valueOf(obj.getIdprovincia().intValue()));
                datos.put("label", obj.getNombre());
                l1.add(datos);
            }
        }
        enviaDatos(l1);
    }

    public void listaDistritos() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - listaDistritos():enviaDatos() ");

        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();

        if (!isEmpty(idProvincia)) {
            List<Distrito> listaDistritos = distritoService.findLstBy(Integer.valueOf(idProvincia));
            for (Distrito obj : listaDistritos) {
                Map<String, String> datos = new HashMap<String, String>();
                datos.put("id", String.valueOf(obj.getIddistrito().intValue()));
                datos.put("label", obj.getNombre());
                l1.add(datos);
            }
        }
        enviaDatos(l1);
    }

    public void listaMotivos() {
    	log.debug("-> [Action] AutoCompletarAction - listaMotivos():enviaDatos() ");

        // FIXME inyectar por spring, por alguna extraña razon no puedo
        iniciarFactorias();
        MotivoService motivoService = (MotivoService) beanFactory.getBean("motivoService");
        List<Motivo> listaMotivos = motivoService.encontrarPorProceso(idProceso);
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
        for (Motivo obj : listaMotivos) {
            Map<String, String> datos = new HashMap<String, String>();
            datos.put("id", String.valueOf(obj.getIdmotivo().intValue()));
            datos.put("label", obj.getDescripcion());
            l1.add(datos);
        }
        enviaDatos(l1);
    }

    public void listaSubMotivos() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - listaSubMotivos():enviaDatos() ");

        iniciarFactorias();
        SubmotivoDAO daoSubMotivo = (SubmotivoDAO) beanFactory.getBean("submotivoDAO");
        List<Submotivo> listaSubMotivos = daoSubMotivo.findByMotivo(Integer.valueOf(idDependencia1));
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
        for (Submotivo obj : listaSubMotivos) {
            Map<String, String> datos = new HashMap<String, String>();
            datos.put("id", String.valueOf(obj.getIdsubmotivo().intValue()));
            datos.put("label", obj.getDescripcion());
            l1.add(datos);
        }
        enviaDatos(l1);
    }

    public void autocompletarResponsableTecnico() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - autocompletarResponsableTecnico():enviaDatos() ");

        iniciarFactorias();
        UsuariostorDAO usDao = (UsuariostorDAO) beanFactory.getBean("usuariostorDAO");
        List<Usuario> lstRevTecs = usDao.getRevisoresTecnicosBySala(idDependencia1);
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
        for (Usuario obj : lstRevTecs) {
            Map<String, String> datos = new HashMap<String, String>();
            datos.put("id", String.valueOf(obj.getIdusuario()));
            datos.put("label", obj.getNombres() + " " + obj.getApellidos());
            l1.add(datos);
        }
        enviaDatos(l1);
    }

    public void autocompletarAnalistaStor() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - autocompletarAnalistaStor():enviaDatos() ");

        iniciarFactorias();
        SalaDAO salaDAO = (SalaDAO) beanFactory.getBean("salaDAO");
        UsuariostorDAO usDao = (UsuariostorDAO) beanFactory.getBean("usuariostorDAO");
        List<Sala> lstSalas = salaDAO.findAll();
        log.info("CANTIDAD DE SALAS: " + lstSalas.size());
        List<Usuario> lstAnalistas = null;
        if (idDependencia2 != null && idDependencia2.equalsIgnoreCase("si")) {
            for (Sala objSala : lstSalas) {
                if (!objSala.getIdsala().toString().equalsIgnoreCase(idDependencia1)) {
                    if (lstAnalistas == null) {
                        lstAnalistas = usDao.getAnalistasBySala(objSala.getIdsala().toString());
                    } else {
                        List<Usuario> lstAnalistasAux = usDao.getAnalistasBySala(objSala.getIdsala().toString());
                        for (int i = 0; i < lstAnalistasAux.size(); i++) {
                            lstAnalistas.add(lstAnalistasAux.get(i));
                        }
                    }
                }
            }
        } else {
            lstAnalistas = usDao.getAnalistasBySala(idDependencia1);
        }
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
        for (Usuario obj : lstAnalistas) {
            Map<String, String> datos = new HashMap<String, String>();
            datos.put("id", String.valueOf(obj.getIdusuario()));
            datos.put("label", obj.getNombres() + " " + obj.getApellidos());
            l1.add(datos);
        }
        enviaDatos(l1);
    }

    public void autocompletarSala() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - autocompletarSala():enviaDatos() ");

        iniciarFactorias();
        SalaDAO daoSal = (SalaDAO) beanFactory.getBean("salaDAO");
        List<Sala> listaSalas = daoSal.findAll();
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
        for (Sala obj : listaSalas) {
            Map<String, String> datos = new HashMap<String, String>();
            datos.put("id", String.valueOf(obj.getIdsala()));
            datos.put("label", obj.getNombre());
            l1.add(datos);
        }
        enviaDatos(l1);
    }

    public void autocompletarAmbitoenvio() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - autocompletarAmbitoenvio():enviaDatos() ");

        iniciarFactorias();
        List<AmbitoEnvio> lstAmbito = ambitoService.findAll();
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
        for (AmbitoEnvio obj : lstAmbito) {
            Map<String, String> datos = new HashMap<String, String>();
            datos.put("id", obj.getIdambitoenvio().toString());
            datos.put("label", obj.getNomambitoenvio());
            l1.add(datos);
        }
        enviaDatos(l1);
    }

    public void autocompletarEmpresadestino() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - autocompletarEmpresadestino():enviaDatos() ");

        iniciarFactorias();
        EmpresadestinoDAO edDao = (EmpresadestinoDAO) beanFactory.getBean("empresadestinoDAO");
        List<Empresadestino> lsempdes = edDao.findAll();
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
        for (Empresadestino obj : lsempdes) {
            Map<String, String> datos = new HashMap<String, String>();
            datos.put("id", obj.getCodigo());
            datos.put("label", obj.getNombre());
            datos.put("nombre", obj.getNombre());
            l1.add(datos);
        }
        enviaDatos(l1);
    }

    public void autocompletarCourrier() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - autocompletarCourrier():enviaDatos() ");

        iniciarFactorias();
        CourierDAO crDao = (CourierDAO) beanFactory.getBean("courierDAO");
        List<Courier> lsempdes = crDao.findAll();
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
        for (Courier obj : lsempdes) {
            Map<String, String> datos = new HashMap<String, String>();
            datos.put("id", obj.getCodigo());
            datos.put("label", obj.getNombreCourrier());
            datos.put("nombre", obj.getNombreCourrier());
            l1.add(datos);
        }
        enviaDatos(l1);
    }

    public void autocompletarUnidadPeso() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - autocompletarUnidadPeso():enviaDatos() ");

        iniciarFactorias();
        UnidadpesoDAO dao = (UnidadpesoDAO) beanFactory.getBean("unidadpesoDAO");
        List<Unidadpeso> lstUnidadPeso = dao.findAll();
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
        for (Unidadpeso obj : lstUnidadPeso) {
            Map<String, String> datos = new HashMap<String, String>();
            datos.put("id", obj.getNombrepeso().trim());
            datos.put("label", obj.getNombrepeso().trim());
            datos.put("nombre", obj.getNombrepeso().trim());
            l1.add(datos);
        }
        enviaDatos(l1);
    }
    
    ///////////////////////////CODIGO PARA LISTAR LOS REQUERIMIENTOS TRIBUTARIOS//////////////////////////////
    public void obtenerNumReqTributario() throws Exception {
        log.debug("-> [Action] AutoCompletarAction - obtenerNumReqTributario():enviaDatos() ");

        Integer idExpediente=0;
        if(!sNroExpediente.equals(""))
        {
            idExpediente = Integer.parseInt(sNroExpediente);
        }
        Usuario usuario = (Usuario) ActionContext.getContext().getSession().get(Constantes.SESSION_USUARIO);
        //usuario = srvUsuario.findByIdUsuario(usuario.getIdusuario());

        List<Documento> listaDocumento = srvDocumento.getDocumentoByTipoByUnidad(srvParametro.findByTipoUnico("TIPO_DOCUMENTO_REQUERIMIENTO_TRIBUTARIO").getValor(), usuario.getIdUnidadPerfil().toString(), idExpediente);
        List<Map<String, String>> ll = new LinkedList<Map<String, String>>();
        for(int i=0; i < listaDocumento.size(); i++){
            Documento documento = listaDocumento.get(i);
            Map<String, String> datos = new HashMap<String, String>();
            datos.put("id", String.valueOf(documento.getIdDocumento())+ " /-/ " + documento.getNroexpediente());
            datos.put("label", documento.getNumeroDocumento());
            ll.add(datos);
        }
        enviaDatos(ll);
    }
    
    public void obtenerAnioFiscal() throws Exception {
        log.debug("-> [Action] AutoCompletarAction - obtenerAnioFiscal():enviaDatos() ");
        
        Calendar cal= Calendar.getInstance();
        int anio= cal.get(Calendar.YEAR);
        List<Map<String, String>> ll = new LinkedList<Map<String, String>>();
        for(int i=0; i < 11; i++){
            Map<String, String> datos = new HashMap<String, String>();
            datos.put("id", String.valueOf(anio-i));
            datos.put("label", String.valueOf(anio-i));
            ll.add(datos);
        }
        enviaDatos(ll);
    }


    public void autocompletarEstadoCargo() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - autocompletarEstadoCargo():enviaDatos() ");

        iniciarFactorias();
        EstadocargoDAO dao = (EstadocargoDAO) beanFactory.getBean("estadocargoDAO");
        List<Estadocargo> lstEstadoCargo = dao.findAll();
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
        for (Estadocargo obj : lstEstadoCargo) {
            Map<String, String> datos = new HashMap<String, String>();
            datos.put("id", obj.getNombreestado().trim());
            datos.put("label", obj.getNombreestado().trim());
            datos.put("nombre", obj.getNombreestado().trim());
            l1.add(datos);
        }
        enviaDatos(l1);
    }

    public void obtenerRevisorLegalStor() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - obtenerRevisorLegalStor():enviaDatos() ");

        iniciarFactorias();
        UsuariostorDAO usDao = (UsuariostorDAO) beanFactory.getBean("usuariostorDAO");
        List<Usuario> lstRevLegs = usDao.getRevisoresLegalesBySala(idDependencia1);
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
        for (Usuario obj : lstRevLegs) {
            Map<String, String> datos = new HashMap<String, String>();
            datos.put("id", String.valueOf(obj.getIdusuario()));
            datos.put("label", obj.getNombres() + " " + obj.getApellidos());
            l1.add(datos);
        }
        enviaDatos(l1);
    }

    public void obtenerTipoResultadoResolucionJaru() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - obtenerTipoResultadoResolucionJaru():enviaDatos() ");

        List<Tiporesultado> listTResultados = tiporesultadoDao.findAll();
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
        for (Tiporesultado objTR : listTResultados) {
            Map<String, String> datos = new HashMap<String, String>();
            datos.put("id", String.valueOf(objTR.getIdtiporesultado()));
            datos.put("label", objTR.getResultado());
            l1.add(datos);
        }
        enviaDatos(l1);
    }

    // Este metodo devuelve todos los procesos donde el usuario logueado esta
    // participando
    // en caso se quisiera para un usuario especifico deberian crear su propio
    // metodo
    // no se modifica los querys en el dao para no quitarle generalidad
    public void autocompletarProcesoParticipante() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - autocompletarProcesoParticipante():void ");

        log.debug("autocomplete filtro1=" + this.filtro1);
        Usuario usuario = (Usuario) ActionContext.getContext().getSession().get(Constantes.SESSION_USUARIO);
        this.autocompletarProcesoParticipantexUsuario(usuario, (this.filtro1 != null && (this.filtro1.equalsIgnoreCase("todos") || this.filtro1.equalsIgnoreCase("mio"))) ? true : false);
    }

    public void autocompletarProcesoParticipantexParametro() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - autocompletarProcesoParticipantexParametro():void ");

        iniciarFactorias();
        Usuario usuario = srvUsuario.findByIdUsuario(Integer.valueOf(idDependencia1));
        this.autocompletarProcesoParticipantexUsuario(usuario, false);
    }

    public void autocompletarProcesoParticipantexUsuario(Usuario usuario, boolean esTodos) {
    	log.debug("-> [Action] AutoCompletarAction - autocompletarProcesoParticipantexUsuario():enviaDatos() ");

        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        List<Proceso> listaProcesos;
        listaProcesos = srvProceso.buscarProcesosxUsuarioParticipante(usuario);
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
        for (Proceso obj : listaProcesos) {
            List<Reemplazo> listaReemplazos = reemplazoDao.findByIdreemplazadoIdproceso(obj.getIdproceso(), usuario.getIdusuario());
            Reemplazo reemplazo = null;
            if (listaReemplazos.size() != 0) {
                reemplazo = listaReemplazos.get(0);
            }
            Map<String, String> datos = new HashMap<String, String>();
            datos.put("id", String.valueOf(obj.getIdproceso()));
            datos.put("label", obj.getNombre());
            datos.put("idresponsable", obj.getResponsable().getIdusuario().toString());
            if (reemplazo != null) {
                if (reemplazo.getFechainicialreemplazo() != null) {
                    datos.put("fechainicio", formatoFecha.format(reemplazo.getFechainicialreemplazo()));
                }
                if (reemplazo.getFechafinalreemplazo() != null) {
                    datos.put("fechafin", formatoFecha.format(reemplazo.getFechafinalreemplazo()));
                }
                datos.put("idreemplazo", reemplazo.getIdusuario().getIdusuario().toString());
                Usuario usuarioTemporal = srvUsuario.findByIdUsuario(reemplazo.getIdusuario().getIdusuario());
                datos.put("reemplazo", usuarioTemporal.getNombres() + " " + usuarioTemporal.getApellidos());
                datos.put("estado", String.valueOf(reemplazo.getEstado()));
            }
            // datos.put("idreemplazante",aca llenamos el posible participante
            // por proceso y por usuario que deberia ser unico);
            l1.add(datos);
        }
        if (esTodos) {
            Map<String, String> datos = new HashMap<String, String>();
            datos.put("id", Constantes.ID_TODOS_MIS_PROCESOS);
            datos.put("label", Constantes.TODOS_MIS_PROCESOS);
            l1.add(datos);
            // this.iIdProceso =3 ;
        }
        enviaDatos(l1);
    }

    public void autocompletarProcesoPorUsuario() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - autocompletarProcesoPorUsuario():enviaDatos() ");

        if (iIdUsuario == null) {
            log.debug("No se recibio ningun usuario");

            return;
        }

        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
        List<Proceso> lstProceso = null;
        Usuario objParticipante = null;

        log.debug("Procesos a buscar del usuario con ID [" + iIdUsuario + "]");

        objParticipante = srvUsuario.findByIdUsuario(iIdUsuario);
        lstProceso = srvProceso.findLstBy(objParticipante);
        log.debug("El usuario [" + objParticipante.getUsuario() + "] participa en [" + (lstProceso == null ? "0" : lstProceso.size()) + "] proceso(s)");

        if (lstProceso != null) {
            for (Proceso objProceso : lstProceso) {
                Map<String, String> datos = new HashMap<String, String>();

                datos.put("id", String.valueOf(objProceso.getIdproceso()));
                datos.put("label", objProceso.getNombre());

                l1.add(datos);
            }
        }

        enviaDatos(l1);
    }

    @SuppressWarnings("unchecked")
    public void enviaGridReemplazo() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - enviaGridReemplazo():void ");

        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Usuario usuario = (Usuario) ActionContext.getContext().getSession().get(Constantes.SESSION_USUARIO);
            reemplazoDao.deleteByIdreemplazado(usuario.getIdusuario());
            log.debug(idDependencia1);
            Object obj = JSONValue.parse(idDependencia1);
            JSONObject objeto = (JSONObject) obj;
            JSONArray items = (JSONArray) objeto.get("items");
            Iterator<JSONObject> iter = items.iterator();
            while (iter.hasNext()) {
                JSONObject item = iter.next();
                log.debug("item {}", item);
                Reemplazo reemplazo = new Reemplazo();
                reemplazo.setEstado('A');
                reemplazo.setFechafinalreemplazo(new Timestamp(formatoFecha.parse((String) item.get("fechafin")).getTime()));
                reemplazo.setFechainicialreemplazo(new Timestamp(formatoFecha.parse((String) item.get("fechainicio")).getTime()));
                reemplazo.setIdreemplazado(usuario.getIdusuario());
                log.debug("item.get('idreemplazo') {}", item.get("idreemplazo"));
                // Usuario
                // usuariotemp=usuarioService.findByIdUsuario(Integer.valueOf(item.get("idreemplazo").toString()));
                reemplazo.setIdproceso(Integer.valueOf(item.get("id").toString()));
                log.debug("item {}", item);
                reemplazoDao.saveReemplazo(reemplazo);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    // Este metodo solo devuelve los usuariosxproceso pero no incluye al usuario
    // logueado
    // Si quieren un metodo que saque todos los usuarios seria cuestion de hacer
    // uno nuevo y comentar
    // la linea que dice remove
    public void autocompletarUsuarioxProceso() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - autocompletarUsuarioxProceso():void ");

        Usuario usuario = (Usuario) ActionContext.getContext().getSession().get(Constantes.SESSION_USUARIO);

        if (iIdUsuarioToOmit != null) {
            usuario = srvUsuario.findByIdUsuario(iIdUsuarioToOmit);

            log.debug("Usuario a omitir [" + usuario.getUsuario() + "]");
        }

        autocompletarUsuarioxProcesoSinUsuario(usuario);
    }

    public void autocompletarUsuarioxProcesoSinUsuarioParametro() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - autocompletarUsuarioxProcesoSinUsuarioParametro():void ");

        Usuario usuario = srvUsuario.findByIdUsuario(Integer.valueOf(idDependencia2));
        autocompletarUsuarioxProcesoSinUsuario(usuario);
    }

    @SuppressWarnings("unused")
	public void autocompletarUsuarioxProcesoSinUsuario(Usuario usuario) throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - autocompletarUsuarioxProcesoSinUsuario():enviaDatos() ");

        Proceso proceso = srvProceso.findByIdProceso(Integer.decode(idDependencia1));
        List<Usuario> listaUsuarios = srvUsuario.getUsuarios();
        listaUsuarios.remove(usuario);
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
        for (Usuario obj : listaUsuarios) {
            Map<String, String> datos = new HashMap<String, String>();
            datos.put("id", String.valueOf(obj.getIdusuario()));
            datos.put("label", obj.getNombres() + " " + obj.getApellidos());
            // XXX que es esto?
            datos.put("fecha", "11/12/2005");
            l1.add(datos);
        }
        enviaDatos(l1);
    }

    //erik
    public void autocompletarUsuarioxProcesoConUsuario() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - autocompletarUsuarioxProcesoConUsuario():enviaDatos() ");

//		Measurement objTime = new Measurement();
//		objTime.start();
        String idProceso = ServletActionContext.getRequest().getParameter("idProceso");
        String multiple = ServletActionContext.getRequest().getParameter("multiple");

        Proceso proceso = srvProceso.findByIdProceso(Integer.decode(idProceso));
        List<Usuario> listaUsuarios = srvUsuario.getUsuarios();
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();

        if (multiple == null) {

            for (Usuario obj : listaUsuarios) {
                Map<String, String> datos = new HashMap<String, String>();
                datos.put("id", String.valueOf(obj.getIdusuario()));
                String nombre = (obj.getApellidos() != null && !obj.getApellidos().equals("") && !obj.getApellidos().equals(" ") ? obj.getNombres() + " " + obj.getApellidos() : obj.getNombres());
                datos.put("label", nombre);
                // XXX que es esto?
                datos.put("fecha", "11/12/2005");
                l1.add(datos);
            }

        } else {
            if (multiple.equals("si")) {

                for (Usuario objUsuario : listaUsuarios) {
                    Map<String, String> reply = new HashMap<String, String>();

                    reply.put("id", "USUARIO_" + objUsuario.getIdusuario().toString());
                    String nombre = (objUsuario.getApellidos() != null && !objUsuario.getApellidos().equals("") && !objUsuario.getApellidos().equals(" ") ? objUsuario.getNombres() + " " + objUsuario.getApellidos() : objUsuario.getNombres());
                    reply.put("label", nombre);

                    boolean contiene = false;
                    for (Map<String, String> entry : l1) {
                        if (entry.containsValue(reply.get("id"))) {
                            contiene = true;
                            break;
                        }
                    }
                    if (!contiene) {
                        l1.add(reply);
                    }
                }

                for (Lista objLista : proceso.getListaList()) {
                    Map<String, String> reply = new HashMap<String, String>();
                    reply.put("id", "LISTA_" + objLista.getIdlista().toString());
                    reply.put("label", objLista.getNombre());

                    boolean contieneLISTA = false;
                    for (Map<String, String> entry : l1) {
                        if (entry.containsValue(reply.get("id"))) {
                            contieneLISTA = true;
                            break;
                        }
                    }
                    if (!contieneLISTA) {
                        l1.add(reply);
                    }
                    Lista objListaAux = listaService.findByIdLista(objLista.getIdlista());
                    for (Usuario usuario : objListaAux.getParticipanteListaList()) {
                        if (usuario.getEstado().equals("A")) {
                            log.debug("idUsuario {} nombres {} estado {}", new Object[]{usuario.getIdusuario(), usuario.getNombres(), usuario.getEstado()});
                            reply = new HashMap<String, String>();
                            reply.put("id", "USUARIO_" + usuario.getIdusuario().toString());
                            String nombre = (usuario.getApellidos() != null && !usuario.getApellidos().equals("") && !usuario.getApellidos().equals(" ") ? usuario.getNombres() + " " + usuario.getApellidos() : usuario.getNombres());
                            reply.put("label", nombre);

                            boolean contieneUSUARIO = false;
                            for (Map<String, String> entry : l1) {
                                if (entry.containsValue(reply.get("id"))) {
                                    contieneUSUARIO = true;
                                    break;
                                }
                            }
                            if (!contieneUSUARIO) {
                                l1.add(reply);
                            }
                        }
                    }
                }
            }
        }

//		objTime.stop();
//		log.debug("Tiempo tomado en autocompletarUsuarioxProcesoConUsuario [" + objTime.getElapsedTime() + "]");

        enviaDatos(l1);
    }

    public void recibeNotificacionesLogueado() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - recibeNotificacionesLogueado():enviaDatos() ");

        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        Usuario usuario = (Usuario) ActionContext.getContext().getSession().get(Constantes.SESSION_USUARIO);
        List<Notificacion> listaNotificaciones = notificacionDao.obtenerNotificacionesxUsuario(usuario.getIdusuario());
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
        for (Notificacion obj : listaNotificaciones) {
            Map<String, String> datos = new HashMap<String, String>();
            if (obj.getIddocumento() != null) {
                datos.put("id", String.valueOf(obj.getIdnotificacion()));
                datos.put("label", obj.getAsunto());
                datos.put("fechanotificacion", formatoFecha.format(obj.getFechanotificacion()));
                datos.put("contenido", obj.getContenido());
                datos.put("iddocumento", obj.getIddocumento().getIdDocumento().toString());
                datos.put("tiponotificacion", String.valueOf(obj.getTiponotificacion()));
                datos.put("estado", String.valueOf(obj.getEstado()));
                l1.add(datos);
            } else {
                datos.put("id", String.valueOf(obj.getIdnotificacion()));
                datos.put("label", obj.getAsunto());
                datos.put("fechanotificacion", formatoFecha.format(obj.getFechanotificacion()));
                datos.put("contenido", obj.getContenido());
                datos.put("iddocumento", "");
                datos.put("tiponotificacion", String.valueOf(obj.getTiponotificacion()));
                datos.put("estado", String.valueOf(obj.getEstado()));
                l1.add(datos);
            }
        }
        enviaDatos(l1);
    }

    public void findClientesbyIdDocumento() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - findClientesbyIdDocumento():enviaDatos() ");

        //Integer idDocumento = this.idDocumento;

        List<Documentoxcliente> listaDocumentoxCliente = documentoxclienteService.findClientesbyIdDocumento(this.idDocumento);
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
        log.debug("sTipoIdentificacion [" + getSTipoIdentificacion() + "]");

        if (listaDocumentoxCliente != null) {
            log.debug("Nro de Documentos por cliente encontrados [" + listaDocumentoxCliente.size() + "]");
            for (Documentoxcliente objDxC : listaDocumentoxCliente) {
                Map<String, String> reply = new HashMap<String, String>();
                reply.put("tipoidentificacion", objDxC.getCliente().getTipoIdentificacion().getNombre());
                reply.put("id", objDxC.getCliente().getNumeroIdentificacion());
                if (objDxC.getCliente().getTipoIdentificacion().getNombre().equals(Constantes.TIPO_IDENTIFICACION_RUC)) {

			        reply.put("label", objDxC.getCliente().getTipoIdentificacion().getNombre() + " - " + objDxC.getCliente().getNumeroIdentificacion() + " - " + objDxC.getCliente().getRazonSocial());
                } else if (objDxC.getCliente().getTipoIdentificacion().getNombre().equals(Constantes.TIPO_IDENTIFICACION_DNI) || objDxC.getCliente().getTipoIdentificacion().getNombre().equals(Constantes.TIPO_IDENTIFICACION_OTRO)) {
                    String sNombreCompleto=objDxC.getCliente().getNombres() + (objDxC.getCliente().getApellidoPaterno()== null ? "" : " " + objDxC.getCliente().getApellidoPaterno());
                    sNombreCompleto += objDxC.getCliente().getApellidoMaterno() == null ? "" : " " + objDxC.getCliente().getApellidoMaterno();
                    reply.put("label", objDxC.getCliente().getTipoIdentificacion().getNombre() + " - " + objDxC.getCliente().getNumeroIdentificacion() + " - " + sNombreCompleto);
                }
                reply.put("idcliente", objDxC.getCliente().getIdCliente().toString());
                String razonSocial = objDxC.getCliente().getRazonSocial();
                reply.put("razonsocial", razonSocial == null ? "" : razonSocial);
                reply.put("direccionp", objDxC.getCliente().getDireccionPrincipal() == null ? "" : objDxC.getCliente().getDireccionPrincipal());

                if (objDxC.getCliente().getIdDistritoUP() != null) {
                    reply.put("iddistrito", objDxC.getCliente().getIdDistritoUP().toString());
                    reply.put("distrito", objDxC.getCliente().getSDistritoUP());
                    if (objDxC.getCliente().getIdProvinciaUP() != null) {
                        reply.put("idprovincia", objDxC.getCliente().getIdProvinciaUP().toString());
                        reply.put("provincia", objDxC.getCliente().getSProvinciaUP());
                        if (objDxC.getCliente().getIdDepartamentoUP() != null) {
                            reply.put("iddepartamento", objDxC.getCliente().getIdDepartamentoUP().toString());
                            reply.put("departamento", objDxC.getCliente().getSDepartamentoUP());
                        } else {
                            reply.put("departamento", "");
                        }
                    } else {
                        reply.put("provincia", "");
                    }
                } else {
                    reply.put("distrito", "");
                }

                l1.add(reply);
            }
        }
        enviaDatos(l1);

    }

    public void obtenerDataClienteCxb() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - obtenerDataClienteCxb():enviaDatos() ");

        List<Cliente> lstCliente = null;
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
        log.debug("sTipoIdentificacion [" + getSTipoIdentificacion() + "]");

        if (isEmpty(getSTipoIdentificacion())) {
            lstCliente = clienteService.findByTipoIdentificacionList(null, null);
        } else {
            lstCliente = clienteService.findByTipoIdentificacionList(null, getSTipoIdentificacion());
        }

        if (lstCliente != null) {
            log.debug("Nro de Clientes encontrados [" + lstCliente.size() + "]");
            for (Cliente objC : lstCliente) {
                Map<String, String> reply = new HashMap<String, String>();
                reply.put("id", objC.getNumeroIdentificacion());

                if (objC.getTipoIdentificacion().getNombre().equals(Constantes.TIPO_IDENTIFICACION_RUC)) {
                    reply.put("label", objC.getTipoIdentificacion().getNombre() + " - " + objC.getNumeroIdentificacion() + " - " + objC.getRazonSocial());
                } else if (objC.getTipoIdentificacion().getNombre().equals(Constantes.TIPO_IDENTIFICACION_DNI) || objC.getTipoIdentificacion().getNombre().equals(Constantes.TIPO_IDENTIFICACION_OTRO)) {
                	String sNombreCompleto=objC.getNombres() + (objC.getApellidoPaterno() == null ? "" : " " + objC.getApellidoPaterno());
			        sNombreCompleto += objC.getApellidoMaterno() == null ? "" : " " + objC.getApellidoMaterno();
                    reply.put("label", objC.getTipoIdentificacion().getNombre() + " - " + objC.getNumeroIdentificacion() + " - " + sNombreCompleto);
                }
                l1.add(reply);
            }
        }
        enviaDatos(l1);
    }

    public void obtenerClienteOptimizado() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - obtenerClienteOptimizado():enviaDatos() ");
        List<Cliente> lstCliente = null;
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
        log.debug("sTipoIdentificacion [" + getSTipoIdentificacion() + "]");
        if (isEmpty(getSTipoIdentificacion())) {
            lstCliente = clienteService.findByTipoIdentificacionList(null, null);
        } else {
            lstCliente = clienteService.findByTipoIdentificacionList(null, getSTipoIdentificacion());
        }
        if (lstCliente != null) {
            log.debug("Nro de Clientes encontrados [" + lstCliente.size() + "]");
            for (Cliente objC : lstCliente) {
                Map<String, String> reply = new HashMap<String, String>();
                reply.put("tipoidentificacion", objC.getTipoIdentificacion().getNombre());
                reply.put("id", objC.getNumeroIdentificacion());
                if (objC.getTipoIdentificacion().getNombre().equals(Constantes.TIPO_IDENTIFICACION_RUC)) {
                    reply.put("label", objC.getTipoIdentificacion().getNombre() + " - " + objC.getNumeroIdentificacion() + " - " + objC.getRazonSocial());
                } else if (objC.getTipoIdentificacion().getNombre().equals(Constantes.TIPO_IDENTIFICACION_DNI) || objC.getTipoIdentificacion().getNombre().equals(Constantes.TIPO_IDENTIFICACION_OTRO)) {
                	String sNombreCompleto=objC.getNombres() + (objC.getApellidoPaterno() == null ? "" : " " + objC.getApellidoPaterno());
                    sNombreCompleto += objC.getApellidoMaterno() == null ? "" : " " + objC.getApellidoMaterno();
                    reply.put("label", objC.getTipoIdentificacion().getNombre() + " - " + objC.getNumeroIdentificacion() + " - " + sNombreCompleto);
                }
                l1.add(reply);
            }
        }
        
        enviaDatos(l1);
    }

    public void obtenerClientePorTipoIdentificacion() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - obtenerClientePorTipoIdentificacion():enviaDatos() ");

        List<Cliente> lstCliente = null;
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
        log.debug("sTipoIdentificacion [" + getSTipoIdentificacion() + "]");

        if (isEmpty(getSTipoIdentificacion())) {
            lstCliente = clienteService.findByTipoIdentificacionList(null, null);
        } else {
            lstCliente = clienteService.findByTipoIdentificacionList(null, getSTipoIdentificacion());
        }

        if (lstCliente != null) {
            log.debug("Nro de Clientes encontrados [" + lstCliente.size() + "]");
            for (Cliente objC : lstCliente) {
                Map<String, String> reply = new HashMap<String, String>();
                reply.put("tipoidentificacion", objC.getTipoIdentificacion().getNombre());
                reply.put("id", objC.getNumeroIdentificacion());
                if (objC.getTipoIdentificacion().getNombre().equals(Constantes.TIPO_IDENTIFICACION_RUC)) {
                    reply.put("label", objC.getTipoIdentificacion().getNombre() + " - " + objC.getNumeroIdentificacion() + " - " + objC.getRazonSocial());
                } else if (objC.getTipoIdentificacion().getNombre().equals(Constantes.TIPO_IDENTIFICACION_DNI) || objC.getTipoIdentificacion().getNombre().equals(Constantes.TIPO_IDENTIFICACION_OTRO)) {
                	String sNombreCompleto=objC.getNombres() + (objC.getApellidoPaterno() == null ? "" : " " + objC.getApellidoPaterno());
                    sNombreCompleto += objC.getApellidoMaterno() == null ? "" : " " + objC.getApellidoMaterno();
                    reply.put("label", objC.getTipoIdentificacion().getNombre() + " - " + objC.getNumeroIdentificacion() + " - " + sNombreCompleto);
                }
                // log.debug("Nombre del Cliente ["+reply.get("label").toString()+"]");
                reply.put("idcliente", objC.getIdCliente().toString());
                String razonSocial = objC.getRazonSocial();
                reply.put("razonsocial", razonSocial == null ? "" : razonSocial);
                reply.put("representantelegal", objC.getRepresentanteLegal() == null ? "" : objC.getRepresentanteLegal());
                String nombres = objC.getNombres();
                reply.put("nombres", nombres == null ? "" : nombres);
                String paterno = objC.getApellidoPaterno();
                reply.put("apellidopaterno", paterno == null ? "" : paterno);
                String materno = objC.getApellidoMaterno();
                reply.put("apellidomaterno", materno == null ? "" : materno);
                reply.put("direccionp", objC.getDireccionPrincipal() == null ? "" : objC.getDireccionPrincipal());

                if (objC.getIdDistritoUP() != null) {
                    reply.put("iddistrito", objC.getIdDistritoUP().toString());
                    reply.put("distrito", objC.getSDistritoUP());
                    if (objC.getIdProvinciaUP() != null) {
                        reply.put("idprovincia", objC.getIdProvinciaUP().toString());
                        reply.put("provincia", objC.getSProvinciaUP());
                        if (objC.getIdDepartamentoUP() != null) {
                            reply.put("iddepartamento", objC.getIdDepartamentoUP().toString());
                            reply.put("departamento", objC.getSDepartamentoUP());
                        } else {
                            reply.put("departamento", "");
                        }
                    } else {
                        reply.put("provincia", "");
                    }
                } else {
                    reply.put("distrito", "");
                }

                reply.put("direcciona", objC.getDireccionAlternativa() == null ? "" : objC.getDireccionAlternativa());

                if (objC.getIdDistritoUA() != null) {
                    reply.put("iddistritoa", objC.getIdDistritoUA().toString());
                    reply.put("distritoa", objC.getSDistritoUA());
                    if (objC.getIdProvinciaUA() != null) {
                        reply.put("idprovinciaa", objC.getIdProvinciaUA().toString());
                        reply.put("provinciaa", objC.getSProvinciaUA());
                        if (objC.getIdDepartamentoUA() != null) {
                            reply.put("iddepartamentoa", objC.getIdDepartamentoUA().toString());
                            reply.put("departamentoa", objC.getSDepartamentoUA());
                        } else {
                            reply.put("departamentoa", "");
                        }
                    } else {
                        reply.put("provinciaa", "");
                    }
                } else {
                    reply.put("distrito", "");
                }
                reply.put("telefono", objC.getTelefono() == null ? "" : objC.getTelefono());
                reply.put("correo", objC.getCorreo() == null ? "" : objC.getCorreo());
                l1.add(reply);
            }
        }
//		objTime.stop();
//		log.debug("Tiempo tomado en obtenerClientePorTipoIdentificacion [" + objTime.getElapsedTime() + "]");

        enviaDatos(l1);
    }

    public void obtenerClientePorNombre() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - obtenerClientePorNombre():enviaDatos() ");

        HttpServletRequest request = ServletActionContext.getRequest();
        String idlike = new String(request.getParameter("label").getBytes("ISO-8859-1"), "UTF-8");
        if (idlike != null && idlike.length() > 1 && idlike.endsWith("*")) {
            idlike = idlike.substring(0, idlike.length() - 1);
        }
        log.debug(idlike);
        List<Cliente> lstCliente = null;
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
        lstCliente = clienteService.findLikeNroIdentificacionOrNombre(idlike);
        if (lstCliente != null) {
            log.debug("Nro de Clientes encontrados [" + lstCliente.size() + "]");
            for (Cliente objC : lstCliente) {
                Map<String, String> reply = new HashMap<String, String>();
                reply.put("id", objC.getNumeroIdentificacion());
                if (objC.getTipoIdentificacion().getNombre().equals(Constantes.TIPO_IDENTIFICACION_RUC)) {
                    reply.put("label", objC.getTipoIdentificacion().getNombre() + " - " + objC.getNumeroIdentificacion() + " - " + objC.getRazonSocial());
                } else if (objC.getTipoIdentificacion().getNombre().equals(Constantes.TIPO_IDENTIFICACION_DNI) || objC.getTipoIdentificacion().getNombre().equals(Constantes.TIPO_IDENTIFICACION_OTRO)) {
                	String sNombreCompleto=objC.getNombres() + (objC.getApellidoPaterno() == null ? "" : " " + objC.getApellidoPaterno());
                    sNombreCompleto += objC.getApellidoMaterno() == null ? "" : " " + objC.getApellidoMaterno();
                    reply.put("label", objC.getTipoIdentificacion().getNombre() + " - " + objC.getNumeroIdentificacion() + " - " + sNombreCompleto);
                }
                l1.add(reply);
            }
        }
        enviaDatos(l1);
    }

    /**
     * Devuelve los campos para un tipo de plantilla dado
     *
     * @author German Enriquez
     */
    public void getCamposPorPlantilla() {
    	log.debug("-> [Action] AutoCompletarAction - getCamposPorPlantilla():enviaDatos() ");

        log.debug("tipo de Plantilla [" + tipoPlantilla + "] idDocumento [" + idDocumento + "]");
        List<Campo> campos = plantillaService.listCamposByTipoPlantilla(tipoPlantilla);
        if (campos != null) {
            log.debug("Se encontraron [" + campos.size() + "] campos");
            List<Map<String, String>> salida = new ArrayList<Map<String, String>>();
            Documento objDoc = srvDocumento.findByIdDocumento(idDocumento);
            log.debug("El documento [" + objDoc.getIdDocumento() + "] es principal [" + objDoc.getPrincipal() + "]");

            for (Campo campo : campos) {
                Map<String, String> dato = new HashMap<String, String>();
                String sDatoValor = "";
                dato.put("idcampo", campo.getIdCampo().toString());
                dato.put("tipo", campo.getTipo());
                dato.put("descripcion", campo.getDescripcion());

                if (campo.getValorDefecto() != null) {
                    if (campo.getValorDefecto().equals("Propietario del Documento")) {
                        sDatoValor = objDoc.getPropietario().getNombres() + " " + objDoc.getPropietario().getApellidos();
                    } else if (campo.getValorDefecto().equals("Area del Propietario del Documento")) {
                        sDatoValor = objDoc.getPropietario().getUnidad().getNombre();
                    } else if (campo.getValorDefecto().equals("Cliente")) {
                        if (objDoc.getExpediente().getCliente().getTipoIdentificacion().getNombre().equals(Constantes.TIPO_IDENTIFICACION_RUC)) {
                            sDatoValor = objDoc.getExpediente().getClienterazonsocial();
                        } else {
                            sDatoValor = objDoc.getExpediente().getClientenombres() + " " + objDoc.getExpediente().getClienteapellidopaterno();
                        }
                    } else if (campo.getValorDefecto().equals("Concesionario")) {
                        if (objDoc.getExpediente().getConcesionario() != null) {
                            sDatoValor = objDoc.getExpediente().getConcesionario().getRazonSocial();
                        }
                    } else if (campo.getValorDefecto().equals("Proceso")) {
                        sDatoValor = null;//objDoc.getExpediente().getProceso().getNombre();
                    } else if (campo.getValorDefecto().equals("Etapa")) {
                        if (objDoc.getExpediente().getIdetapa() != null) {
                            sDatoValor = objDoc.getExpediente().getIdetapa().getDescripcion();
                        }
                    } else if (campo.getValorDefecto().equals("Actividad")) {
                        if (objDoc.getExpediente().getIdactividad() != null) {
                            sDatoValor = objDoc.getExpediente().getIdactividad().getNombre();
                        }
                    } else if (campo.getValorDefecto().equals("Nro Expediente")) {
                        sDatoValor = objDoc.getExpediente().getNroexpediente();
                    } else if (campo.getValorDefecto().equals("Descripcion")) {
                        sDatoValor = objDoc.getExpediente().getDescripcion();
                    } else if (campo.getValorDefecto().equals("Asunto del Expediente")) {
                        sDatoValor = objDoc.getExpediente().getAsunto();
                    } else if (campo.getValorDefecto().equals("Contenido")) {
                        sDatoValor = objDoc.getExpediente().getContenido();
                    } else if (campo.getValorDefecto().equals("Observacion")) {
                        sDatoValor = objDoc.getExpediente().getObservacion();
                    } else if (campo.getValorDefecto().equals("Fecha Creacion")) {
                        sDatoValor = objDoc.getExpediente().getFechacreacion().toString();
                    } else if (campo.getValorDefecto().equals("Estado")) {
                        sDatoValor = String.valueOf(objDoc.getExpediente().getEstado());
                    } else if (campo.getValorDefecto().equals("Observacion de Rechazo")) {
                        sDatoValor = objDoc.getExpediente().getObservacionrechazo();
                    } else if (campo.getValorDefecto().equals("Nro Interno")) {
                        sDatoValor = objDoc.getExpediente().getNrointerno();
                    } else if (campo.getValorDefecto().equals("Responsable del Expediente")) {
                        sDatoValor = objDoc.getExpediente().getIdpropietario().getNombres() + " " + objDoc.getExpediente().getIdpropietario().getApellidos();
                    } else if (campo.getValorDefecto().equals("Area del Responsable del Expediente")) {
                        sDatoValor = objDoc.getExpediente().getIdpropietario().getUnidad().getNombre();
                    } else if (campo.getValorDefecto().equals("Responsable del Proceso")) {
                        sDatoValor = null;//objDoc.getExpediente().getProceso().getResponsable().getNombres() + " " + objDoc.getExpediente().getProceso().getResponsable().getApellidos();
                    } else if (campo.getValorDefecto().equals("Area del Responsable del Proceso")) {
                        sDatoValor = null;//objDoc.getExpediente().getProceso().getResponsable().getUnidad().getNombre();
                    }
                }

                dato.put("valor", sDatoValor == null ? "" : sDatoValor);

                String sDatoDocumento = "";

                if (campo.getValorDefecto() != null) {
                    if (campo.getValorDefecto().equals("Tipo de Documento")
                            || campo.getValorDefecto().equals("Firmante")
                            || campo.getValorDefecto().equals("Fecha del Documento")
                            || campo.getValorDefecto().equals("Asunto del Documento")
                            || campo.getValorDefecto().equals("Autor")) {
                        sDatoDocumento = campo.getValorDefecto();
                    }
                }

                dato.put("datodocumento", sDatoDocumento);

                salida.add(dato);
            }
            enviaDatos(salida);
        }
    }

    public void getCamposGridEtapaProceso() {
    	log.debug("-> [Action] AutoCompletarAction - getCamposGridEtapaProceso():enviaDatos() ");

        log.debug("idProceso [" + idProceso + "]");
        List<Map<String, String>> salida = new ArrayList<Map<String, String>>();
        if (iIdProceso != null) {
            List<Etapa> listaEtapas = etapaService.findEtapabyProceso(iIdProceso);
            if (listaEtapas != null) {
                log.debug("Se encontraron [" + listaEtapas.size() + "] campos");
                for (Etapa etapa : listaEtapas) {
                    Map<String, String> dato = new HashMap<String, String>();
                    dato.put("id", etapa.getIdetapa().toString());
                    dato.put("orden", etapa.getOrden() != null ? etapa.getOrden().toString() : "");
                    dato.put("descripcion", etapa.getDescripcion());
                    salida.add(dato);
                }
            }
        }
        enviaDatos(salida);
    }

    public void getCamposGridEstadoProceso() {
    	log.debug("-> [Action] AutoCompletarAction - getCamposGridEstadoProceso():enviaDatos() ");

        log.debug("idProceso [" + idProceso + "]");
        List<Estado> listaEstados = estadoDao.findByIdProceso(iIdProceso);
        List<Map<String, String>> salida = new ArrayList<Map<String, String>>();
        if (iIdProceso != null) {
            if (listaEstados != null) {
                log.debug("Se encontraron [" + listaEstados.size() + "] campos");
                for (Estado estado : listaEstados) {
                    Map<String, String> dato = new HashMap<String, String>();
                    dato.put("id", estado.getIdestado().toString());
                    dato.put("descripcion", estado.getDescripcion() != null ? estado.getDescripcion().toString() : "");
                    salida.add(dato);
                }
            }
        }
        enviaDatos(salida);
    }

    public void ProcesoClasificacion() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - ProcesoClasificacion():enviaDatos() ");

        List<Parametro> parametros = srvParametro.findByTipo(Constantes.COMBO_PROCESOCLASIFICACION_PARAMETRO);
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
        for (Parametro parametro : parametros) {
            if (parametro != null) {
                Map<String, String> datos = new HashMap<String, String>();
                datos.put("id", parametro.getValor());
                datos.put("label", parametro.getDescripcion());
                l1.add(datos);
            }
        }
        enviaDatos(l1);
    }


    public void servicioFedatario() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - servicioFedatario():enviaDatos() ");

    	 String idOperacion = ServletActionContext.getRequest().getParameter("idOperacion");
    	 List<Parametro> parametros = new ArrayList<Parametro>();

         if (idOperacion.equals("1")){
    		 parametros.add(new Parametro("Todos", "Todos"));
    	 }

    	  parametros.addAll(srvParametro.findByTipo(Constantes.SERVICIO_FEDATARIO));
          List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
          for (Parametro parametro : parametros) {
              if (parametro != null) {
                  Map<String, String> datos = new HashMap<String, String>();
                  datos.put("id", parametro.getValor());
                  datos.put("label", parametro.getDescripcion());
                  l1.add(datos);
              }
          }
          enviaDatos(l1);
    }

    public void tipoExpediente() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - tipoExpediente():enviaDatos() ");

        List<Parametro> parametros = new ArrayList<Parametro>();
        parametros.add(new Parametro("Todos", "Todos"));
        parametros.addAll(srvParametro.getTipoExpediente());
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
        for (Parametro parametro : parametros) {
            if (parametro != null) {
                Map<String, String> datos = new HashMap<String, String>();
                datos.put("id", parametro.getValor());
                datos.put("label", parametro.getDescripcion());
                l1.add(datos);
            }
        }
        enviaDatos(l1);
    }

    public void salaAYQ() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - salaAYQ():enviaDatos() ");

        List<Parametro> parametros = new ArrayList<Parametro>();
        parametros.add(new Parametro("Todas", "Todas"));
        parametros.add(new Parametro("Sin sala asignada", "Sin sala asignada"));
        parametros.addAll(srvParametro.getSalaAYQ());
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
        for (Parametro parametro : parametros) {
            if (parametro != null) {
                Map<String, String> datos = new HashMap<String, String>();
                datos.put("id", parametro.getValor());
                datos.put("label", parametro.getDescripcion());
                l1.add(datos);
            }
        }
        enviaDatos(l1);
    }

    public void responsableAYQ() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - responsableAYQ():enviaDatos() ");

        List<Parametro> parametros = new ArrayList<Parametro>();
        parametros.add(new Parametro("Todos", "Todos"));
        parametros.addAll(srvParametro.getResponsableAYQ());
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
        for (Parametro parametro : parametros) {
            if (parametro != null) {
                Map<String, String> datos = new HashMap<String, String>();
                datos.put("id", parametro.getValor());
                datos.put("label", parametro.getDescripcion());
                l1.add(datos);
            }
        }
        enviaDatos(l1);
    }

    public void analistaAYQ() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - analistaAYQ():enviaDatos() ");

        List<Parametro> parametros = new ArrayList<Parametro>();
        parametros.add(new Parametro("Todos", "Todos"));
        parametros.add(new Parametro("Sin analista asignado", "Sin analista asignado"));
        parametros.addAll(srvParametro.getAnalistaAYQ());
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
        for (Parametro parametro : parametros) {
            if (parametro != null) {
                Map<String, String> datos = new HashMap<String, String>();
                datos.put("id", parametro.getValor());
                datos.put("label", parametro.getDescripcion());
                l1.add(datos);
            }
        }
        enviaDatos(l1);
    }

    public void Clasificacion() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - Clasificacion():enviaDatos() ");

        List<Parametro> parametros = srvParametro.findByTipo(Constantes.COMBO_CLASIFICAR_PARAMETRO);
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
        for (Parametro parametro : parametros) {
            if (parametro != null) {
                Map<String, String> datos = new HashMap<String, String>();
                datos.put("id", parametro.getValor());
                datos.put("label", parametro.getDescripcion());
                l1.add(datos);
            }
        }
        enviaDatos(l1);
    }

    public void EstadoReporte() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - EstadoReporte():enviaDatos() ");

        List<Parametro> parametros = new ArrayList<Parametro>();
        parametros.add(new Parametro("Todos", "Todos"));
        parametros.addAll(srvParametro.getEstados());
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
        for (Parametro parametro : parametros) {
            if (parametro != null) {
                Map<String, String> datos = new HashMap<String, String>();
                datos.put("id", parametro.getValor());

                if (parametro.getValor().equalsIgnoreCase("" + Constantes.ESTADO_ACTIVO)) {

                    datos.put("label", Constantes.EXPEDIENTE_ESTADO_ACTIVO);

                } else if (parametro.getValor().equalsIgnoreCase("" + Constantes.ESTADO_INACTIVO)) {

                    datos.put("label", Constantes.EXPEDIENTE_ESTADO_ARCHIVADO);

                } else if (parametro.getValor().equalsIgnoreCase("" + Constantes.ESTADO_ANULADO)) {

                    datos.put("label", Constantes.EXPEDIENTE_ESTADO_ANULADO);

                } else {

                    datos.put("label", parametro.getDescripcion());
                }
                l1.add(datos);
            }
        }
        enviaDatos(l1);
    }

    public void ProcesoReporte() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - ProcesoReporte():enviaDatos() ");

        List<Parametro> parametros = new ArrayList<Parametro>();
        parametros.add(new Parametro("Todos", "Todos"));
        parametros.addAll(srvParametro.getProceso());
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();


        for (Parametro parametro : parametros) {
            if (parametro != null) {
                Map<String, String> datos = new HashMap<String, String>();
                datos.put("id", parametro.getValor());
                datos.put("label", parametro.getDescripcion());
                l1.add(datos);
            }
        }

        enviaDatos(l1);
    }

    public void criteriosConfidencialidad() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - CriteriosConfidencialidad():enviaDatos() ");

        Map<String, String> datos = new HashMap<String, String>();
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();

        List<Parametro> parametros = srvParametro.findByTipo(Constantes.CRITERIOS_CONFIDENCIAL);

        for (Parametro parametro : parametros) {
            if (parametro != null) {
                 datos = new HashMap<String, String>();
                 datos.put("id", parametro.getValor());
                 datos.put("label", parametro.getDescripcion());
                 l1.add(datos);
            }
        }
        enviaDatos(l1);
    }

    public void GrupoProcesos() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - GrupoProcesos():enviaDatos() ");

        List<Parametro> parametros = new ArrayList<Parametro>();
        parametros.add(new Parametro("0", "TODOS"));
        parametros.addAll(srvParametro.getGrupoProceso());
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
        for (Parametro parametro : parametros) {
            if (parametro != null) {
                Map<String, String> datos = new HashMap<String, String>();
                datos.put("id", parametro.getValor());
                datos.put("label", parametro.getDescripcion());
                l1.add(datos);
            }
        }
        enviaDatos(l1);
    }

    public void autoCompletarUnidad() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - autoCompletarUnidad():enviaDatos() ");

        List<Unidad> unidades = srvUnidad.obtenerLstTodo();
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
        for (Unidad unidad : unidades) {
            if (unidad != null) {
                Map<String, String> datos = new HashMap<String, String>();
                datos.put("id", unidad.getIdunidad().toString());
                datos.put("label", unidad.getNombre());
                l1.add(datos);
            }
        }
        enviaDatos(l1);
    }
    
        public void autocompletarUnidadOrgSession() throws Exception {
                log.debug("-> [Action] AutoCompletarAction - autocompletarUnidadOrgSession():enviaDatos() ");
                mapSession = ActionContext.getContext().getSession();
                Usuario usuario_ = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
                enviaDatos(srvUsuario.findUnidadOrgSession(usuario_.getUnidad().getIdunidad())); 
    }

    private String completaDataJSON(String jsonString) {
        String resultado = " { 'identifier': 'id', " + "    'label': 'label',  " + " 	 'items': " + jsonString + "}";
        return resultado;
    }

    private void enviaDatos(List<Map<String, String>> l1) {
        try {
            String jsonString = JSONValue.toJSONString(l1);
            jsonString = completaDataJSON(jsonString);
            HttpServletResponse response;
            response = ServletActionContext.getResponse();
            response.setHeader("Cache-Control", "no-cache"); // HTTP 1.1
            response.setHeader("Pragma", "no-cache"); // HTTP 1.0
            response.setDateHeader("Expires", 0); // prevents caching at the
            // proxy server
            response.setContentType("text/x-json;charset=UTF-8");
            PrintWriter out;
            out = response.getWriter();
            out.print(jsonString);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    private void enviaDatosJson(String objeto) {
        try {

            String jsonString = " { 'identifier': 'tiponumeracion', 'items':[ " + objeto + "] }";

            HttpServletResponse response;
            response = ServletActionContext.getResponse();
            response.setHeader("Cache-Control", "no-cache"); // HTTP 1.1
            response.setHeader("Pragma", "no-cache"); // HTTP 1.0
            response.setDateHeader("Expires", 0); // prevents caching at the
            // proxy server
            response.setContentType("text/x-json;charset=UTF-8");
            PrintWriter out;
            out = response.getWriter();
            out.print(jsonString);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    public void getTipoDocumentoWithoutPlantilla() {
    	log.debug("-> [Action] AutoCompletarAction - getTipoDocumentoWithoutPlantilla():enviaDatos() ");

        Boolean bIsEdit = false;
        Integer iIdTD = null;
        Plantilla objPlantilla = null;

        if (iIdPlantilla != null) {
            objPlantilla = plantillaService.findByIdplantilla(iIdPlantilla);
            iIdTD = objPlantilla.getTipo();
            bIsEdit = true;
        } else {
            bIsEdit = false;
        }

        iniciarFactorias();

        TipodocumentoService srvTipoDocumento = serviceFactory.getTipoDocumentoService();
        List<Tipodocumento> lstTipoDocumento = srvTipoDocumento.findAllLstWithoutPlantilla(bIsEdit, iIdTD);
        List<Map<String, String>> mapResult = new LinkedList<Map<String, String>>();

        for (Tipodocumento objTipoDocumento : lstTipoDocumento) {
            Map<String, String> datos = new HashMap<String, String>();

            datos.put("id", objTipoDocumento.getIdtipodocumento().toString());
            datos.put("label", objTipoDocumento.getNombre());

            mapResult.add(datos);
        }

        enviaDatos(mapResult);
    }

    public void autocompletarTipoEnvio() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - autocompletarTipoEnvio():enviaDatos() ");

        List<Tipoenvio> lstTipoenvio = tipoEnvioService.findAll();
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
        for (Tipoenvio objTipoenvio : lstTipoenvio) {
            Map<String, String> datos = new HashMap<String, String>();
            datos.put("id", String.valueOf(objTipoenvio.getIdtipoenvio().intValue()));
            datos.put("label", objTipoenvio.getTipoenvio2());
            l1.add(datos);
        }
        enviaDatos(l1);
    }

    public void autocompletarEtapasporProceso() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - autocompletarEtapasporProceso():enviaDatos() ");

        List<Etapa> lstEtapa = etapaService.findEtapabyProceso(idProceso);
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
        for (Etapa objEtapa : lstEtapa) {
            Map<String, String> datos = new HashMap<String, String>();
            datos.put("id", String.valueOf(objEtapa.getIdetapa().intValue()));
            datos.put("label", objEtapa.getDescripcion());
            l1.add(datos);
        }
        enviaDatos(l1);
    }

    /*Autocompletars para la pantalla de Stor*/
    public void autocompletarSalaStor() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - autocompletarSalaStor():enviaDatos() ");

        List<Sala> lstSala = salaService.findAll();
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
        for (Sala objSala : lstSala) {
            Map<String, String> datos = new HashMap<String, String>();
            datos.put("id", String.valueOf(objSala.getIdsala().intValue()));
            datos.put("label", objSala.getNombre());
            l1.add(datos);
        }
        enviaDatos(l1);
    }

    public void autocompletarEstadoStor() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - autocompletarEstadoStor():enviaDatos() ");

        List<Estado> lstEstado = estadoDao.findByTipo(Constantes.PROCESO_STOR);
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
        for (Estado objEstado : lstEstado) {
            Map<String, String> datos = new HashMap<String, String>();
            datos.put("id", String.valueOf(objEstado.getIdestado().intValue()));
            datos.put("label", objEstado.getDescripcion());
            l1.add(datos);
        }
        enviaDatos(l1);
    }

    public void autocompletarAreaDestino() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - autocompletarAreaDestino():enviaDatos() ");

	List<Unidad> lstUnidad = this.unidadDAO.findAll();
        log.debug("(autocompletarAreaDestino) Nº unidades->" + lstUnidad.size());
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
        
        for (Unidad objUnidad : lstUnidad) {
            Map<String, String> datos = new HashMap<String, String>();
            datos.put("id", String.valueOf(objUnidad.getIdunidad().intValue()));
            datos.put("label", objUnidad.getNombre());
            l1.add(datos);
        }
        enviaDatos(l1);
    }

    public void autocompletarAreaTodos() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - autocompletarAreaTodos():enviaDatos() ");

        List<Unidad> lstUnidad = this.unidadDAO.findAll();
        log.debug("(autocompletarAreaDestino) Nº unidades->" + lstUnidad.size());
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();

        Map<String, String> datosT = new HashMap<String, String>();
        datosT.put("id", Constantes.ID_AREA);
        datosT.put("label", Constantes.TODOS_AREA);
        l1.add(datosT);

        for (Unidad objUnidad : lstUnidad) {
            Map<String, String> datos = new HashMap<String, String>();
            datos.put("id", String.valueOf(objUnidad.getIdunidad().intValue()));
            datos.put("label", objUnidad.getNombre());
            l1.add(datos);
        }
        enviaDatos(l1);
    }



    public void autocompletarAreaDocumentosPendientes() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - autocompletarAreaDocumentosPendientes():enviaDatos() ");
    	mapSession = ActionContext.getContext().getSession();
    	Usuario usuario_ = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
        List<Unidad> lstUnidad = this.unidadDAO.findAllGrupo();
        log.debug("(autocompletarAreaDestino) Nº unidades->" + lstUnidad.size());
        List<Parametro> parametros = srvParametro.findByTipo(Constantes.PARAMETRO_TIPO_DESCARGAR_DOCUMENTOS_PENDIENTES_AREA);
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
        boolean viewAllArea = false;


        for(int i=0;i<parametros.size();i++){
        	if (usuario_.getUnidad().getIdunidad().toString().equals(parametros.get(i).getValor())){
        		viewAllArea = true;
        		break;
        	}
        }

        if (viewAllArea){
        	Map<String, String> datosT = new HashMap<String, String>();
        	datosT.put("id", Constantes.ID_AREA);
        	datosT.put("label", Constantes.TODOS_AREA);
        	l1.add(datosT);

        	for (Unidad objUnidad : lstUnidad) {
              Map<String, String> datos = new HashMap<String, String>();
              datos.put("id", String.valueOf(objUnidad.getIdunidad().intValue()));
              datos.put("label", objUnidad.getNombre());
              l1.add(datos);
           }
        }else{
        	for (Unidad objUnidad : lstUnidad) {
                Map<String, String> datos = new HashMap<String, String>();
                if (objUnidad.getIdunidad().toString().equals(usuario_.getUnidad().getUnidadgrupo().toString())){
                  datos.put("id", String.valueOf(objUnidad.getIdunidad().intValue()));
                  datos.put("label", objUnidad.getNombre());
                  l1.add(datos);
                  break;
        	   }
             }
        }


        enviaDatos(l1);
    }

    public void autocompletarResultadoStor() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - autocompletarResultadoStor():enviaDatos() ");

        List<Tiporesultado> lstResultado = tipoResultadoService.findAll();
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
        for (Tiporesultado objResultado : lstResultado) {
            Map<String, String> datos = new HashMap<String, String>();
            datos.put("id", String.valueOf(objResultado.getIdtiporesultado().intValue()));
            datos.put("label", objResultado.getResultado());
            l1.add(datos);
        }
        enviaDatos(l1);
    }

    public void autocompletarVocalStor() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - autocompletarVocalStor():enviaDatos() ");

        List<Vocal> lstVocal = vocalService.findAll();
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
        for (Vocal objVocal : lstVocal) {
            Map<String, String> datos = new HashMap<String, String>();
            datos.put("id", String.valueOf(objVocal.getIdvocal().intValue()));
            StringBuilder nombreVocal = new StringBuilder(objVocal.getNombres()).append(" ").append(objVocal.getApellidopaterno()).append(" ").append(objVocal.getApellidomaterno());
            datos.put("label", nombreVocal.toString());
            l1.add(datos);
        }
        enviaDatos(l1);
    }

    public void listaMonedas() {
    	log.debug("-> [Action] AutoCompletarAction - listaMonedas():enviaDatos() ");

        iniciarFactorias();
        List<Parametro> listaMonedas = srvParametro.findByTipo(Constantes.PARAMETRO_TIPO_MONEDA);
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
        for (Parametro obj : listaMonedas) {
            Map<String, String> datos = new HashMap<String, String>();
            datos.put("id", String.valueOf(obj.getIdparametro().intValue()));
            datos.put("label", obj.getDescripcion());
            l1.add(datos);
        }
        enviaDatos(l1);
    }

    public void getRolesPorUsuario(){
    	log.debug("-> [Action] AutoCompletarAction - getRolesPorUsuario():enviaDatos() ");

		if(sUsuario!=null){
			iniciarFactorias();
			RolService rolService=(RolService) serviceFactory.getBean("rolService");
			List<Rol> roles=rolService.getRolesPorUsuario(sUsuario);
			if(roles!=null){
				List<Map<String,String>> salida=new LinkedList<Map<String,String>>();
				for(Rol rol : roles){
					Map<String,String> data=new HashMap<String,String>();
					data.put("id",rol.getIdrol().toString());
					data.put("label",rol.getDescripcion());
					salida.add(data);
				}
				enviaDatos(salida);
			}
		}
	}

    
    public void getParametro() {
      log.debug("-> [Action] AutoCompletarAction - getParametro():enviaDatos() ");
      List<Parametro> listaParametro = srvParametro.findByTipo(codParametro);//Constantes.PARAMETRO_TIPOS_DE_ADJUNTOS_MP);
      List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
        
      for (Parametro obj : listaParametro) {
            Map<String, String> datos = new HashMap<String, String>();
            datos.put("id", obj.getValor());
            datos.put("label", obj.getDescripcion());
            l1.add(datos);
      }
       
      enviaDatos(l1);
    }
    
     public void getParametroActivo() {
      log.debug("-> [Action] AutoCompletarAction - getParametroActivo():enviaDatos() ");
      List<Parametro> listaParametro = srvParametro.findByTipoActivo(codParametro);//Constantes.PARAMETRO_TIPOS_DE_ADJUNTOS_MP);
      List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
        
      if (iWithoutStor!=null && iWithoutStor == 1){
         Map<String, String> datos = new HashMap<String, String>();
         datos.put("id", "-1");
         datos.put("label", "Todos");
         l1.add(datos);
      }
      
      for (Parametro obj : listaParametro) {
            Map<String, String> datos = new HashMap<String, String>();
            datos.put("id", obj.getValor());
            datos.put("label", obj.getDescripcion());
            l1.add(datos);
      }
       
      enviaDatos(l1);
    }
    
    public void getTipoAdjunto() {
      log.debug("-> [Action] AutoCompletarAction - getTipoAdjunto():enviaDatos() ");
      List<Parametro> listaPrioridades = srvParametro.findByTipo(Constantes.PARAMETRO_TIPOS_DE_ADJUNTOS_MP);
      List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
        
      for (Parametro obj : listaPrioridades) {
            Map<String, String> datos = new HashMap<String, String>();
            datos.put("id", obj.getValor());
            datos.put("label", obj.getDescripcion());
            l1.add(datos);
      }
       
      enviaDatos(l1);
    }
    
    public void getPrioridades() {
    	log.debug("-> [Action] AutoCompletarAction - getPrioridades():enviaDatos() ");

        iniciarFactorias();
        List<Parametro> listaPrioridades = srvParametro.getPrioridades();
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
        for (Parametro obj : listaPrioridades) {
            Map<String, String> datos = new HashMap<String, String>();
            datos.put("id", obj.getValor());
            datos.put("label", obj.getDescripcion());
            l1.add(datos);
        }
        enviaDatos(l1);
    }


    public void getPrioridades_() {
    	log.debug("-> [Action] AutoCompletarAction - getPrioridades():enviaDatos() ");

        iniciarFactorias();
        List<Parametro> listaPrioridades = srvParametro.getPrioridades();
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
        for (Parametro obj : listaPrioridades) {
        	if ( !obj.getValor().equals("0")){
	            Map<String, String> datos = new HashMap<String, String>();
	            datos.put("id", obj.getValor());
	            datos.put("label", obj.getDescripcion());
	            l1.add(datos);
        	}
        }
        enviaDatos(l1);
    }


    /**REN Metodos para las Query Read Store --------------------------------------------------------------------------------*/

    public void autocompletarQRSTipoDocumento() throws Exception{
    	log.debug("-> [Action] AutoCompletarAction - autocompletarQRSTipoDocumento():enviaDatos() ");

        HttpServletRequest request = ServletActionContext.getRequest();
        String like = new String(request.getParameter("label").getBytes("ISO-8859-1"), "UTF-8");
        
        if (like != null && like.length() > 1 && like.endsWith("*")) {
            like = like.substring(0, like.length() - 1);
        }
        
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
        if(like == null || like.trim().equals("")){
        	log.debug("Tipo documento like en blanco");
        }else{
        	List<Tipodocumento> tiposDocumento = tipoDocumentoService.findByNombreLike(like);
                
        	for (Tipodocumento objTipoDocumento : tiposDocumento) {
                    Map<String, String> datos = new HashMap<String, String>();
                    datos.put("id", String.valueOf(objTipoDocumento.getIdtipodocumento().intValue()));
                    datos.put("label", objTipoDocumento.getNombre());
                    l1.add(datos);
                }
        }
    	enviaDatos(l1);
    }

    public void autocompletarQRSCliente() throws Exception{
    	log.debug("-> [Action] AutoCompletarAction - autocompletarQRSTipoDocumento():enviaDatos() ");

    	HttpServletRequest request = ServletActionContext.getRequest();
        String like = new String(request.getParameter("label").getBytes("ISO-8859-1"), "UTF-8");
        
        if (like != null && like.length() > 3 && like.endsWith("*")) {
            like = like.substring(0, like.length() - 1);
        }
        
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
        if(like == null || like.trim().equals("")){
        	log.debug("Cliente like en blanco");
        }else{
        	List<Cliente> lstCliente = clienteService.findByNombreRazonLike(like);
        	if (lstCliente != null) {
        
                for (Cliente objC : lstCliente) {
                    Map<String, String> reply = new HashMap<String, String>();
                    reply.put("id", String.valueOf(objC.getIdCliente()));
                    if (objC.getTipoIdentificacion().getNombre().equals(Constantes.TIPO_IDENTIFICACION_RUC)) {
                        reply.put("label", objC.getRazonSocial());
                    }else{
                    	String sNombreCompleto=(objC.getNombres()==null? "": "" + objC.getNombres()) + (objC.getApellidoPaterno() == null ? "" : " " + objC.getApellidoPaterno());
    			        sNombreCompleto += objC.getApellidoMaterno() == null ? "" : " " + objC.getApellidoMaterno();
                        reply.put("label", objC.getTipoIdentificacion().getNombre() + " - " + objC.getNumeroIdentificacion() + " - " + sNombreCompleto);
                    }
                    l1.add(reply);
                }
            }
        }
        enviaDatos(l1);
    }




    public void autocompletarQRSUsuarios() throws Exception{
    	log.debug("-> [Action] AutoCompletarAction - autocompletarQRSTipoDocumento():enviaDatos() ");

    	HttpServletRequest request = ServletActionContext.getRequest();
        String like = new String(request.getParameter("label").getBytes("ISO-8859-1"), "UTF-8");
        if (like != null && like.length() > 1 && like.endsWith("*")) {
            like = like.substring(0, like.length() - 1);
        }
        log.debug(like);
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
        if(like == null || like.trim().equals("")){
        	log.debug("Usuario like en blanco");
        }else{
        	List<Usuario> lstUsuario = srvUsuario.findByApellidosNombresLike(like);
        	if (lstUsuario != null) {
                log.debug("Nro de Usuarios encontrados [" + lstUsuario.size() + "]");
                for (Usuario objU : lstUsuario) {
                    Map<String, String> reply = new HashMap<String, String>();
                    reply.put("id", String.valueOf(objU.getIdusuario()));
                    reply.put("label", objU.getNombreCompleto());
                    l1.add(reply);
                }
            }
        }
        enviaDatos(l1);
    }

    public void autocompletarQRSAreas() throws Exception{
    	log.debug("-> [Action] AutoCompletarAction - autocompletarQRSTipoDocumento():enviaDatos() ");

    	HttpServletRequest request = ServletActionContext.getRequest();
        String like = new String(request.getParameter("label").getBytes("ISO-8859-1"), "UTF-8");
        if (like != null && like.length() > 1 && like.endsWith("*")) {
            like = like.substring(0, like.length() - 1);
        }
        log.debug(like);
        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
        if(like == null || like.trim().equals("")){
        	log.debug("Usuario like en blanco");
        }else{
        	List<Unidad> lstUnidad = srvUnidad.findByNombreLike(like);

        	if (lstUnidad != null) {
                log.debug("Nro de Unidades encontradas [" + lstUnidad.size() + "]");
                for (Unidad objU : lstUnidad) {
                    Map<String, String> reply = new HashMap<String, String>();
                    reply.put("id", String.valueOf(objU.getIdunidad()));
                    reply.put("label", objU.getNombre());
                    l1.add(reply);
                }
            }
        }
        enviaDatos(l1);
    }

    /*GET SET*/


    public void buscarDocumento() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - buscarDocumento():enviaDatos() ");

        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
        List<Usuario> lstUsuario = srvUsuario.getUsuarios();

        for (Usuario objUsuario : lstUsuario) {
            Map<String, String> datos = new HashMap<String, String>();

            datos.put("id", String.valueOf(objUsuario.getIdusuario().intValue()));
            datos.put("label", objUsuario.getNombres() + " " + objUsuario.getApellidos());

            l1.add(datos);
        }

        enviaDatos(l1);
    }
    
    //JUAN BENGOA
    public void autocompletarRUCPIDE() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - autocompletarRUCPIDE():enviaDatos() ");

        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
        List<Cliente> lst = clienteService.findAllRUCPIDE();

        for (Cliente cliente : lst) {
            Map<String, String> datos = new HashMap<String, String>();

            datos.put("id", cliente.getIdCliente().toString());
            datos.put("label", cliente.getIdCliente().toString());

            l1.add(datos);
        }

        enviaDatos(l1);
    }
    
    
    public void autocompletarTipoDocumentoPIDE() throws Exception {
    	log.debug("-> [Action] AutoCompletarAction - autocompletarTipoDocumentoPIDE():enviaDatos() ");

        List<Map<String, String>> l1 = new LinkedList<Map<String, String>>();
        List<Tipodocumento> lst = tipoDocumentoService.findByAllTipoDocumentoPIDE();

        for (Tipodocumento tipodocumento : lst) {
            Map<String, String> datos = new HashMap<String, String>();

            datos.put("id", tipodocumento.getIdtipodocumento().toString());
            datos.put("label", tipodocumento.getNombre());

            l1.add(datos);
        }

        enviaDatos(l1);
    }


    public String getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(String idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public String getIdProvincia() {
        return idProvincia;
    }

    public void setIdProvincia(String idProvincia) {
        this.idProvincia = idProvincia;
    }

    public String getIdDependencia1() {
        return idDependencia1;
    }

    public void setIdDependencia1(String idDependencia1) {
        this.idDependencia1 = idDependencia1;
    }

    public String getIdDependencia2() {
        return idDependencia2;
    }

    public void setIdDependencia2(String idDependencia2) {
        this.idDependencia2 = idDependencia2;
    }

    public Integer getIIdExp() {
        return iIdExp;
    }

    public void setIIdExp(Integer idExp) {
        iIdExp = idExp;
    }

    public Map<String, Object> getMapSession() {
        return mapSession;
    }

    public void setMapSession(Map<String, Object> mapSession) {
        this.mapSession = mapSession;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEstado() {
        return estado;
    }

    public void setIdusuario(String idusuario) {
        this.idusuario = idusuario;
    }

    public String getIdusuario() {
        return idusuario;
    }

    public static void main(String[] args) {
        /*
         * SimpleDateFormat s1=new SimpleDateFormat("dd/MM/yyyy");
         * SimpleDateFormat s2=new SimpleDateFormat("dd/MM/yyyy HH:mm");
         * SimpleDateFormat s3=new SimpleDateFormat("HH:mm"); SimpleDateFormat
         * s4=new SimpleDateFormat("HH");
         */
        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        calendar.setTime(date);
        /*
         * DateFormat df=DateFormat.getDateInstance(); DateFormat
         * df2=DateFormat.getDateInstance(DateFormat.LONG,Locale.ROOT);
         * log.debug("hour="+df2.format(date));
         * log.debug("hour="+date.getHours());
         * log.debug(""+calendar.HOUR);
         * log.debug(""+calendar.HOUR_OF_DAY);
         * log.debug(""+calendar.AM);
         * log.debug(""+calendar.AM_PM);
         */
    }

    public String getSNroExpediente() {
        return sNroExpediente;
    }

    public void setSNroExpediente(String sNroExpediente) {
        this.sNroExpediente = sNroExpediente;
    }

    public String getSUsuario() {
        return sUsuario;
    }

    public void setSUsuario(String sUsuario) {
        this.sUsuario = sUsuario;
    }

    public String getSModulo() {
        return sModulo;
    }

    public void setSModulo(String sModulo) {
        this.sModulo = sModulo;
    }

    public String getSFechaDesde() {
        return sFechaDesde;
    }

    public void setSFechaDesde(String sFechaDesde) {
        this.sFechaDesde = sFechaDesde;
    }

    public String getSFechaHasta() {
        return sFechaHasta;
    }

    public void setSFechaHasta(String sFechaHasta) {
        this.sFechaHasta = sFechaHasta;
    }

    public String getSNroDocumento() {
        return sNroDocumento;
    }

    public void setSNroDocumento(String sNroDocumento) {
        this.sNroDocumento = sNroDocumento;
    }

    public String getFiltro1() {
        return filtro1;
    }

    public void setFiltro1(String filtro1) {
        this.filtro1 = filtro1;
    }

    public String getSNroIdentificacion() {
        return sNroIdentificacion;
    }

    public void setSNroIdentificacion(String sNroIdentificacion) {
        this.sNroIdentificacion = sNroIdentificacion;
    }

    public String getSRazonSocial() {
        return sRazonSocial;
    }

    public void setSRazonSocial(String sRazonSocial) {
        this.sRazonSocial = sRazonSocial;
    }

    public String getSAsunto() {
        return sAsunto;
    }

    public void setSAsunto(String sAsunto) {
        this.sAsunto = sAsunto;
    }

    public String getSTipoIdentificacion() {
        return sTipoIdentificacion;
    }

    public void setSTipoIdentificacion(String sTipoIdentificacion) {
        this.sTipoIdentificacion = sTipoIdentificacion;
    }

    public Integer getIWithoutStor() {
        return iWithoutStor;
    }

    public void setIWithoutStor(Integer iWithoutStor) {
        this.iWithoutStor = iWithoutStor;
    }

    public String getsTipoProceso() {
        return sTipoProceso;
    }

    public void setsTipoProceso(String sTipoProceso) {
        this.sTipoProceso = sTipoProceso;
    }

    public Integer getiIdCliente() {
        return iIdCliente;
    }

    public void setiIdCliente(Integer iIdCliente) {
        this.iIdCliente = iIdCliente;
    }

    public String getSOpcion() {
        return sOpcion;
    }

    public void setSOpcion(String opcion) {
        sOpcion = opcion;
    }

    public void setPlantillaService(PlantillaService plantillaService) {
        this.plantillaService = plantillaService;
    }

    public UsuarioService getSrvUsuario() {
        return srvUsuario;
    }

    public void setSrvUsuario(UsuarioService srvUsuario) {
        this.srvUsuario = srvUsuario;
    }

    public int getTipoPlantilla() {
        return tipoPlantilla;
    }

    public void setTipoPlantilla(int tipoPlantilla) {
        this.tipoPlantilla = tipoPlantilla;
    }

    public Integer getiIdPlantilla() {
        return iIdPlantilla;
    }

    public void setiIdPlantilla(Integer iIdPlantilla) {
        this.iIdPlantilla = iIdPlantilla;
    }

    public Integer getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(Integer idDocumento) {
        this.idDocumento = idDocumento;
    }

    public DocumentoService getSrvDocumento() {
        return srvDocumento;
    }

    public void setSrvDocumento(DocumentoService srvDocumento) {
        this.srvDocumento = srvDocumento;
    }

    public int getIdProceso() {
        return idProceso;
    }

    public void setIdProceso(int idProceso) {
        this.idProceso = idProceso;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Integer getIIdDoc() {
        return iIdDoc;
    }

    public void setIIdDoc(Integer idDoc) {
        iIdDoc = idDoc;
    }

    public String getSTipoDerivacion() {
        return sTipoDerivacion;
    }

    public void setSTipoDerivacion(String sTipoDerivacion) {
        this.sTipoDerivacion = sTipoDerivacion;
    }

    public void setExpedienteDao(ExpedienteDAO expedienteDao) {
        this.expedienteDao = expedienteDao;
    }

    public Integer getiIdUsuario() {
        return iIdUsuario;
    }

    public void setiIdUsuario(Integer iIdUsuario) {
        this.iIdUsuario = iIdUsuario;
    }

    public ProcesoService getSrvProceso() {
        return srvProceso;
    }

    public void setSrvProceso(ProcesoService srvProceso) {
        this.srvProceso = srvProceso;
    }

    public Integer getiIdUsuarioToOmit() {
        return iIdUsuarioToOmit;
    }

    public void setiIdUsuarioToOmit(Integer iIdUsuarioToOmit) {
        this.iIdUsuarioToOmit = iIdUsuarioToOmit;
    }

    public Integer getiIdProceso() {
        return iIdProceso;
    }

    public void setiIdProceso(Integer iIdProceso) {
        this.iIdProceso = iIdProceso;
    }

    public void setTipoDocumentoService(TipodocumentoService tipoDocumentoService) {
        this.tipoDocumentoService = tipoDocumentoService;
    }

    public void setDepartamentoService(DepartamentoService departamentoService) {
        this.departamentoService = departamentoService;
    }

    public void setTipoIdentificacionService(
            TipoidentificacionService tipoIdentificacionService) {
        this.tipoIdentificacionService = tipoIdentificacionService;
    }

    public void setClienteService(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    public void setProvinciaService(ProvinciaService provinciaService) {
        this.provinciaService = provinciaService;
    }

    public void setDistritoService(DistritoService distritoService) {
        this.distritoService = distritoService;
    }

    public Integer getiIdUnidad() {
        return iIdUnidad;
    }

    public void setiIdUnidad(Integer iIdUnidad) {
        this.iIdUnidad = iIdUnidad;
    }

    public RolService getSrvRol() {
        return srvRol;
    }

    public void setSrvRol(RolService srvRol) {
        this.srvRol = srvRol;
    }

    public ConcesionarioService getSrvConcesionario() {
        return srvConcesionario;
    }

    public void setSrvConcesionario(ConcesionarioService srvConcesionario) {
        this.srvConcesionario = srvConcesionario;
    }

    public AuditoriaService getSrvAuditoria() {
        return srvAuditoria;
    }

    public void setSrvAuditoria(AuditoriaService srvAuditoria) {
        this.srvAuditoria = srvAuditoria;
    }

    public ParametroService getSrvParametro() {
        return srvParametro;
    }

    public void setSrvParametro(ParametroService srvParametro) {
        this.srvParametro = srvParametro;
    }

    public UnidadService getSrvUnidad() {
        return srvUnidad;
    }

    public void setSrvUnidad(UnidadService srvUnidad) {
        this.srvUnidad = srvUnidad;
    }

    public NotificacionDAO getNotificacionDao() {
        return notificacionDao;
    }

    public void setNotificacionDao(NotificacionDAO notificacionDao) {
        this.notificacionDao = notificacionDao;
    }

    public ReemplazoDAO getReemplazoDao() {
        return reemplazoDao;
    }

    public void setReemplazoDao(ReemplazoDAO reemplazoDao) {
        this.reemplazoDao = reemplazoDao;
    }

    public TiporesultadoDAO getTiporesultadoDao() {
        return tiporesultadoDao;
    }

    public void setTiporesultadoDao(TiporesultadoDAO tiporesultadoDao) {
        this.tiporesultadoDao = tiporesultadoDao;
    }

    public TipodocumentoService getTipoDocumentoService() {
        return tipoDocumentoService;
    }

    public TipoidentificacionService getTipoIdentificacionService() {
        return tipoIdentificacionService;
    }

    public TrazabilidaddocumentoService getSrvTrazabilidadDocumento() {
        return srvTrazabilidadDocumento;
    }

    public void setSrvTrazabilidadDocumento(
            TrazabilidaddocumentoService srvTrazabilidadDocumento) {
        this.srvTrazabilidadDocumento = srvTrazabilidadDocumento;
    }

    public PlantillaService getPlantillaService() {
        return plantillaService;
    }

    public ExpedienteDAO getExpedienteDao() {
        return expedienteDao;
    }

    public DepartamentoService getDepartamentoService() {
        return departamentoService;
    }

    public ProvinciaService getProvinciaService() {
        return provinciaService;
    }

    public DistritoService getDistritoService() {
        return distritoService;
    }

    public String getTipoBusqueda() {
        return tipoBusqueda;
    }

    public void setTipoBusqueda(String tipoBusqueda) {
        this.tipoBusqueda = tipoBusqueda;
    }

    public String getsDiasReporte() {
        return sDiasReporte;
    }

    public void setsDiasReporte(String sDiasReporte) {
        this.sDiasReporte = sDiasReporte;
    }

    public String getsFiltroBusqueda() {
        return sFiltroBusqueda;
    }

    public void setsFiltroBusqueda(String sFiltroBusqueda) {
        this.sFiltroBusqueda = sFiltroBusqueda;
    }

    public SedeService getSedeService() {
        return sedeService;
    }

    public void setSedeService(SedeService sedeService) {
        this.sedeService = sedeService;
    }

    public int getIdSede() {
        return idSede;
    }

    public void setIdSede(int idSede) {
        this.idSede = idSede;
    }

    public EtapaService getEtapaService() {
        return etapaService;
    }

    public void setEtapaService(EtapaService etapaService) {
        this.etapaService = etapaService;
    }

    public EstadoDAO getEstadoDao() {
        return estadoDao;
    }

    public void setEstadoDao(EstadoDAO estadoDao) {
        this.estadoDao = estadoDao;
    }

    public ListaService getListaService() {
        return listaService;
    }

    public void setListaService(ListaService listaService) {
        this.listaService = listaService;
    }

    public FavoritoService getFavoritoService() {
        return favoritoService;
    }

    public void setFavoritoService(FavoritoService favoritoService) {
        this.favoritoService = favoritoService;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public TipoenvioService getTipoEnvioService() {
        return tipoEnvioService;
    }

    public void setTipoEnvioService(TipoenvioService tipoEnvioService) {
        this.tipoEnvioService = tipoEnvioService;
    }

    public DocumentoxclienteService getDocumentoxclienteService() {
        return documentoxclienteService;
    }

    public void setDocumentoxclienteService(DocumentoxclienteService documentoxclienteService) {
        this.documentoxclienteService = documentoxclienteService;
    }

    public AmbitoenvioService getAmbitoService() {
        return ambitoService;
    }

    public void setAmbitoService(AmbitoenvioService ambitoService) {
        this.ambitoService = ambitoService;
    }

    public SalaService getSalaService() {
        return salaService;
    }

    public void setSalaService(SalaService salaService) {
        this.salaService = salaService;
    }

    public TiporesultadoService getTipoResultadoService() {
        return tipoResultadoService;
    }

    public void setTipoResultadoService(TiporesultadoService tipoResultadoService) {
        this.tipoResultadoService = tipoResultadoService;
    }

    public VocalService getVocalService() {
        return vocalService;
    }

    public void setVocalService(VocalService vocalService) {
        this.vocalService = vocalService;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public UnidadDAO getUnidadDAO() {
        return unidadDAO;
    }

    public void setUnidadDAO(UnidadDAO unidadDAO) {
        this.unidadDAO = unidadDAO;
    }

    public NumeracionService getSrvNumeracion() {
	return srvNumeracion;
    }


    public void setSrvNumeracion(NumeracionService srvNumeracion) {
		this.srvNumeracion = srvNumeracion;
    }
    
    public UsuarioxunidadxfuncionService getUsuarioxunidadxfuncionService() {
        return usuarioxunidadxfuncionService;
    }

    public void setUsuarioxunidadxfuncionService(UsuarioxunidadxfuncionService usuarioxunidadxfuncionService) {
        this.usuarioxunidadxfuncionService = usuarioxunidadxfuncionService;
    }
    
    public TipoInstitucionService getTipoInstitucionService() {
        return tipoInstitucionService;
    }

    public void setTipoInstitucionService(TipoInstitucionService tipoInstitucionService) {
        this.tipoInstitucionService = tipoInstitucionService;
    }
    
     public String getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(String idTipo) {
        this.idTipo = idTipo;
    }

    public String getIdNro() {
        return idNro;
    }

    public void setIdNro(String idNro) {
        this.idNro = idNro;
    }

    public String getIdCopia() {
        return idCopia;
    }

    public void setIdCopia(String idCopia) {
        this.idCopia = idCopia;
    }
    
     public String getIdAdjunto() {
        return idAdjunto;
    }

    public void setIdAdjunto(String idAdjunto) {
        this.idAdjunto = idAdjunto;
    }
    
    public Integer getIdTipoCliente() {
        return idTipoCliente;
    }

    public void setIdTipoCliente(Integer idTipoCliente) {
        this.idTipoCliente = idTipoCliente;
    }
    
   public CargoAdministradoService getCargoAdministradoService() {
        return cargoAdministradoService;
    }

    public void setCargoAdministradoService(CargoAdministradoService cargoAdministradoService) {
        this.cargoAdministradoService = cargoAdministradoService;
    }
     public DocumentoAdjuntoService getDocumentoAdjuntoService() {
        return documentoAdjuntoService;
    }

    public void setDocumentoAdjuntoService(DocumentoAdjuntoService documentoAdjuntoService) {
        this.documentoAdjuntoService = documentoAdjuntoService;
    }
    
    public FuncionService getFuncionService() {
        return funcionService;
    }

    public void setFuncionService(FuncionService funcionService) {
        this.funcionService = funcionService;
    }
    
    public Integer getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Integer idPersona) {
        this.idPersona = idPersona;
    }
    
     public Integer getCodCargo() {
        return codCargo;
    }

    public void setCodCargo(Integer codCargo) {
        this.codCargo = codCargo;
    }

    public String getDesCargo() {
        return desCargo;
    }

    public void setDesCargo(String desCargo) {
        this.desCargo = desCargo;
    }
     
     public ProveidoDAO getProveidoDAO() {
        return proveidoDAO;
    }

    public void setProveidoDAO(ProveidoDAO proveidoDAO) {
        this.proveidoDAO = proveidoDAO;
    }
    
    public SerieDAO getSerieDAO() {
        return serieDAO;
    }

    public void setSerieDAO(SerieDAO serieDAO) {
        this.serieDAO = serieDAO;
    }

}