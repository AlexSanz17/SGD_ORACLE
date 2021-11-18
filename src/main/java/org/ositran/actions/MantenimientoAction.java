/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.actions;

import com.btg.ositran.siged.domain.Cliente;
import com.btg.ositran.siged.domain.Departamento;
import com.btg.ositran.siged.domain.Distrito;
import com.btg.ositran.siged.domain.Documento;
import com.btg.ositran.siged.domain.Estado;
import com.btg.ositran.siged.domain.Etapa;
import com.btg.ositran.siged.domain.Expediente;
import com.btg.ositran.siged.domain.Grupoproceso;
import com.btg.ositran.siged.domain.Lista;
import com.btg.ositran.siged.domain.Modulo;
import com.btg.ositran.siged.domain.Numeracion;
import com.btg.ositran.siged.domain.Parametro;
import com.btg.ositran.siged.domain.Perfil;
import com.btg.ositran.siged.domain.Plantilla;
import com.btg.ositran.siged.domain.Proceso;
import com.btg.ositran.siged.domain.Provincia;
import com.btg.ositran.siged.domain.Recurso;
import com.btg.ositran.siged.domain.Reemplazo;
import com.btg.ositran.siged.domain.Rol;
import com.btg.ositran.siged.domain.Sala;
import com.btg.ositran.siged.domain.Sede;
import com.btg.ositran.siged.domain.Tipodocumento;
import com.btg.ositran.siged.domain.Tipoproceso;
import com.btg.ositran.siged.domain.Unidad;
import com.btg.ositran.siged.domain.Usuario;
import com.btg.ositran.siged.domain.UsuarioStor;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import gob.ositran.siged.service.SeguridadService;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.ositran.daos.DistritoDAO;
import org.ositran.daos.EstadoDAO;
import org.ositran.daos.SalaDAO;
import org.ositran.daos.UsuariostorDAO;
import org.ositran.services.ClienteService;
import org.ositran.services.DepartamentoService;
import org.ositran.services.DistritoService;
import org.ositran.services.DocumentoService;
import org.ositran.services.EtapaService;
import org.ositran.services.ExpedienteService;
import org.ositran.services.GrupoprocesoService;
import org.ositran.services.ListaService;
import org.ositran.services.ModuloService;
import org.ositran.services.NumeracionService;
import org.ositran.services.ParametroService;
import org.ositran.services.PerfilService;
import org.ositran.services.PlantillaService;
import org.ositran.services.ProcesoService;
import org.ositran.services.ProvinciaService;
import org.ositran.services.RecursoService;
import org.ositran.services.ReemplazoService;
import org.ositran.services.RolService;
import org.ositran.services.SedeService;
import org.ositran.services.TipoaccesoService;
import org.ositran.services.TipodocumentoService;
import org.ositran.services.TipoprocesoService;
import org.ositran.services.TrazabilidaddocumentoService;
import org.ositran.services.UnidadService;
import org.ositran.services.UsuarioService;
import org.ositran.utils.Constantes;
import org.ositran.utils.DocumentoDetail;
import org.ositran.utils.StringUtil;

public class MantenimientoAction{

	private static Logger log=Logger.getLogger(MantenimientoAction.class);

	private int idSala;
	private int documentoPrincipal;

	private String vencimiento;
	private String usuarioAnterior;
	private String sMensaje;
	private String fechainicialreemplazo;
	private String horainicialreemplazo;
	private String fechafinalreemplazo;
	private String horafinalreemplazo;

	private Boolean bEnvioCorreo;
	private Boolean bPermiteMetaData;
	private Boolean bPermiteSumilla;
	private Boolean bUsuarioFinal;
	private Boolean[] arrConfigNotifMailChecked;
	private Boolean bEsJefe;

	private Integer iIdCliente;
	private Integer iIdModulo;
	private Integer iIdPerfil;
	private Integer iIdPlantilla;
	private Integer iIdProceso;
	private Integer iIdExpediente;
	private Integer iIdRecurso;
	private Integer iIdRol;
	private Integer iIdSede;
	private Integer iIdTipodocumento;
	private Integer iIdUnidad;
	private Integer iIdUsuario;
	private Integer iIdGrupoProceso;
	private Integer iIdDepartamento;
	private Integer iIdProvincia;
	private Integer iIdParametro;
	private Integer iIdDistrito;
	private Integer iIdLista;
	private Integer iIdReemplazo;
	private Integer[] arrConfigNotifMail;
	private List<Integer> lstAdministradorListaRight;
	private List<Integer> lstParticipanteListaRight;
	private List<Integer> lstListaRight;
	private List<Integer> lstRecursoRight;
	private List<Integer> lstUsuarioConfidencialRight;
	private List<Integer> lstUsuarioRight;
	private List<Integer> lstNotificadoRight;
	private List<Integer> rolesUsuario;

	private Map<String,Object> mapSession;
	private Map<Integer,String> mapDependencia;
	private Map<Integer,String> mapGrupoProceso;
	private Map<Integer,String> mapModulo;
	private Map<Integer,String> mapPerfil;
	private Map<Integer,String> mapRecursoLeft;
	private Map<Integer,String> mapRecursoRight;
	private Map<Integer,String> mapResponsable;
	private Map<Integer,String> mapSede;
	private Map<Integer,String> mapRol;
	private Map<Integer,String> mapTipoAcceso;
	private Map<Integer,String> mapTipoProceso;
	private Map<Integer,String> mapUnidad;
	private Map<Integer,String> mapUsuarioConfidencialLeft;
	private Map<Integer,String> mapUsuarioLeft;
	private Map<Integer,String> mapUsuarioConfidencialRight;
	private Map<Integer,String> mapUsuarioRight;
	private Map<Integer,String> mapNotificadoLeft;
	private Map<Integer,String> mapNotificadoRight;
	private Map<Integer,String> mapAdministradorListaLeft;
	private Map<Integer,String> mapAdministradorListaRight;
	private Map<Integer,String> mapParticipanteListaLeft;
	private Map<Integer,String> mapParticipanteListaRight;
	private Map<Integer,String> mapListaLeft;
	private Map<Integer,String> mapListaRight;
	private Map<Integer, String> mapDepartamento;
	private Map<Integer, String> mapProvincia;

	private ClienteService clienteService;
	private DocumentoService documentoService;
	private ExpedienteService expedienteService;
	private EtapaService etapaService;
	private GrupoprocesoService srvGrupoproceso;
	private ModuloService srvModulo;
	private ProcesoService srvP;
	private RecursoService srvRecurso;
	private SedeService srvSede;
	private TipoaccesoService srvTipoAcceso;
	private TipodocumentoService srvTipodocumento;
	private TipoprocesoService srvTP;
	private UnidadService srvUni;
	private UsuarioService usuarioService;
	private PerfilService srvPerfil;
	private PlantillaService srvPlantilla;
	private ListaService srvLista;
	private ReemplazoService srvReemplazo;
	private TrazabilidaddocumentoService trazabilidaddocumentoService;
	private SeguridadService seguridadService;
	private ParametroService srvParametro;
	private ProvinciaService srvProvincia;
	private DistritoService srvDistrito;
	private NumeracionService srvNumeracion;
	private DepartamentoService srvDepartamento;

	private DistritoDAO distritoDAO;
	private EstadoDAO estadoDAO;
	private SalaDAO salaDao;
	private UsuariostorDAO usuarioStorDao;

	private Cliente objCliente;
	private Modulo objModulo;
	private Perfil objPerfil;
	private Plantilla objPlantilla;
	private Proceso objProceso;
	private Expediente objExpediente;
	private Recurso objRecurso;
	private Rol objRol;
	private RolService srvR;
	private Sede objSede;
	private Tipodocumento objTipodocumento;
	private Unidad objUnidad;
	private Usuario objUsuario;
	private Grupoproceso objGrupoProceso;
	private Numeracion objNumeracion;
	private Distrito objDistrito;
	private Reemplazo objReemplazo;
	private Parametro objParametro;
	private Departamento objDepartamento;
	private Provincia objProvincia;
	private Lista objLista;
	private List<Rol> roles;
	private List<Rol> rolesTotales;

	// ////////////////////////////////
	// Constructors //
	// ////////////////////////////////
	public MantenimientoAction(ProcesoService srvP,RolService srvR,UnidadService srvUni,UsuarioService usuarioService){
		setSrvP(srvP);
		setSrvR(srvR);
		setSrvUni(srvUni);
		this.usuarioService=usuarioService;
	}

	// ////////////////////////////////
	// Methods //
	// ////////////////////////////////

	public String editCliente() throws Exception{
		try{
			mapSession=ActionContext.getContext().getSession();
			if(iIdCliente!=null){
				objCliente=clienteService.findByIdCliente(iIdCliente);
				log.debug("Cliente a ver con ID ["+objCliente.getIdCliente()+"]");
				mapSession.put(Constantes.SESSION_AUDITABLE,objCliente);
			}else{
				mapSession.put(Constantes.SESSION_AUDITABLE,new Cliente());
			}
			return Action.SUCCESS;
		}catch(Exception e){
			log.error(e.getMessage(),e);
			return Action.ERROR;
		}
	}

	public String editExpediente() throws Exception{
		try{
			mapSession=ActionContext.getContext().getSession();
			if(iIdExpediente!=null){
				objExpediente=expedienteService.findByIdExpediente(iIdExpediente);
				//log.debug("Expediente a ver con ID ["+objExpediente.getIdexpediente()+"] Nro Expediente ["+objExpediente.getNroexpediente()+"]");
				mapSession.put(Constantes.SESSION_AUDITABLE,objExpediente);
			}else{
				mapSession.put(Constantes.SESSION_AUDITABLE,new Expediente());
			}
			Documento principal=documentoService.findDocumentoPrincipal(objExpediente.getIdexpediente());
			documentoPrincipal=principal.getIdDocumento();
			SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
			vencimiento=sdf.format(principal.getFechaLimiteAtencion());
			log.debug("Documento Principal: "+documentoPrincipal);
			log.debug(" estado exp "+objExpediente.getEstado());
			return Action.SUCCESS;
		}catch(Exception e){
			log.error(e.getMessage(),e);
			return Action.ERROR;
		}
	}

	public String editGrupoProceso() throws Exception {
		try {
			log.debug("iIdGrupoProceso [" + iIdGrupoProceso + "]");

			if (iIdGrupoProceso != null) {
				setObjGrupoProceso(srvGrupoproceso.buscarObjPorId(iIdGrupoProceso));
				log.debug("Grupo de Proceso a ver con ID [" + getObjGrupoProceso().getIdgrupoproceso() + "]");
			}

			return Action.SUCCESS;
		} catch (Exception e) {
			log.error(e.getMessage(), e);

			return Action.ERROR;
		}
	}

	public String editModulo() throws Exception{
		try{
			mapSession=ActionContext.getContext().getSession();
			if(iIdModulo!=null){
				objModulo=srvModulo.findByIdModulo(iIdModulo);
				log.debug("Modulo a ver con ID ["+objModulo.getIdmodulo()+"] Nombre ["+objModulo.getNombre()+"]");
				mapSession.put(Constantes.SESSION_AUDITABLE,objModulo);
			}else{
				mapSession.put(Constantes.SESSION_AUDITABLE,new Modulo());
			}
			return Action.SUCCESS;
		}catch(Exception e){
			log.error(e.getMessage(),e);
			return Action.ERROR;
		}
	}

	public String editNumeracion() throws Exception {
		try {
			log.debug("iIdUnidad recibida [" + iIdUnidad + "]");
			log.debug("iIdTipodocumento recibida [" + iIdTipodocumento + "]");

			if (iIdUnidad != null && iIdTipodocumento != null) {
				objNumeracion = srvNumeracion.findById(iIdUnidad, iIdTipodocumento);
				String sFormato = objNumeracion.getFormato();

				objNumeracion.setFormatoleft(sFormato.indexOf("$NUMERO") == 0 ? "" : sFormato.substring(0, sFormato.indexOf("$NUMERO")));
				objNumeracion.setFormatoright(sFormato.indexOf("$NUMERO") + 7 >= sFormato.length() ? "" : sFormato.substring(sFormato.indexOf("$NUMERO") + 7, sFormato.length()));

				log.debug("Se encontro numeracion para la unidad [" + objNumeracion.getUnidad().getNombre() + "] y tipo de documento [" + objNumeracion.getTipodocumento().getNombre() + "]");
			}

			return Action.SUCCESS;
		} catch (Exception e) {
			log.error(e.getMessage(), e);

			return Action.ERROR;
		}
	}

	public String editPerfil() throws Exception{
		try{
			mapSession=ActionContext.getContext().getSession();
			if(iIdPerfil!=null){
				objPerfil=srvPerfil.findByIdPerfil(iIdPerfil);
				mapRecursoLeft=srvRecurso.findMapRecursoLeft(objPerfil.getRecursoList());
				mapRecursoRight=srvRecurso.findMapRecursoRight(objPerfil.getRecursoList());
				log.debug("Perfil a ver con Id ["+objPerfil.getIdperfil()+"] Nombre ["+objPerfil.getNombre()+"] Recursos disponibles ["+mapRecursoLeft.size()+"] Recursos Definidos ["+mapRecursoRight.size()+"]");
				mapSession.put(Constantes.SESSION_AUDITABLE,objPerfil);
			}else{
				mapRecursoLeft=srvRecurso.findMapAllByIdRecurso();
				log.debug("Mapa de recursos left con size ["+mapRecursoLeft.size()+"]");
				mapSession.put(Constantes.SESSION_AUDITABLE,new Perfil());
			}
			return Action.SUCCESS;
		}catch(Exception e){
			log.error(e.getMessage(),e);
			return Action.ERROR;
		}
	}

	public String editPlantilla() throws Exception {
		try {
			log.debug("iIdPlantilla [" + iIdPlantilla + "]");

			if (iIdPlantilla != null) {
				objPlantilla = srvPlantilla.findByIdplantilla(iIdPlantilla);
			}

			return Action.SUCCESS;
		} catch (Exception e) {
			log.error(e.getMessage(), e);

			return Action.ERROR;
		}
	}

	public String saveExpediente() throws Exception{
		if(objExpediente==null){
			log.error("No hay expediente a guardar");
			return Action.ERROR;
		}
		try{
			Expediente objExpedienteNew=null;
			Expediente objExpedienteOld=null;
			String sTipoAuditoria=null;
			Usuario objUsuarioSesion=null;
			mapSession=ActionContext.getContext().getSession();
			objUsuarioSesion=(Usuario) mapSession.get(Constantes.SESSION_USUARIO);
			objExpedienteOld=(Expediente) mapSession.get(Constantes.SESSION_AUDITABLE);
			mapSession.remove(Constantes.SESSION_AUDITABLE);
			if(objExpediente.getIdexpediente()==null){
				log.debug("** Creacion de Expediente **");
				objExpedienteNew=objExpediente;
				objExpedienteNew.setFechacreacion(new Date());
				objExpedienteNew.setEstado(Constantes.ESTADO_ACTIVO);
				sTipoAuditoria=Constantes.AUDITORIA_TIPO_REGISTRO;
			}else{
				log.debug("** Actualizacion de Expediente con ID ["+objExpediente.getIdexpediente()+"] **");
				objExpedienteNew=expedienteService.findByIdExpediente(objExpediente.getIdexpediente());
				sTipoAuditoria=Constantes.AUDITORIA_TIPO_ACTUALIZACION;
			}

			objExpedienteNew.setAsunto(objExpediente.getAsunto());
			objExpedienteNew.setSumilla(objExpediente.getSumilla());
			//objExpedienteNew.setProceso(srvP.findByIdProceso(objExpediente.getProceso().getIdproceso()));
			objExpedienteNew.setCliente(clienteService.findByIdCliente(objExpediente.getCliente().getIdCliente()));
			DocumentoDetail objDD=new DocumentoDetail();
			objDD.setIIdCliente(objExpediente.getCliente().getIdCliente());
			objDD.setClientenombres(objExpediente.getCliente().getNombres());
			objDD.setClienteapellidopaterno(objExpediente.getCliente().getApellidoPaterno());
			objDD.setClienteapellidomaterno(objExpediente.getCliente().getApellidoMaterno());
			objDD.setClienterazonsocial(objExpediente.getCliente().getRazonSocial());
			objDD.setClienterepresentantelegal(objExpediente.getCliente().getRepresentanteLegal());
			if(!StringUtil.isEmpty(objExpediente.getCliente().getDireccionPrincipal())){
				objDD.setClientedireccionprincipal(objExpediente.getCliente().getDireccionPrincipal());
				//objDD.setClienteubigeoprincipal(objExpediente.getCliente().getUbigeoPrincipal().getIddistrito());
			}
			if(!StringUtil.isEmpty(objExpediente.getCliente().getDireccionAlternativa())){
				objDD.setClientedireccionalternativa(objExpediente.getCliente().getDireccionAlternativa());
				objDD.setClienteubigeoalternativo(objExpediente.getCliente().getUbigeoAlternativo().getIddistrito());
			}
			objDD.setClientetelefono(objExpediente.getCliente().getTelefono());
			objDD.setClientecorreo(objExpediente.getCliente().getCorreo());
			clienteService.updateInfoCliente(objDD);
			objExpedienteNew.setClientenombres(objExpediente.getCliente().getNombres());
			objExpedienteNew.setClienteapellidopaterno(objExpediente.getCliente().getApellidoPaterno());
			objExpedienteNew.setClienteapellidomaterno(objExpediente.getCliente().getApellidoMaterno());
			objExpedienteNew.setClienterazonsocial(objExpediente.getCliente().getRazonSocial());
			objExpedienteNew.setClienterepresentantelegal(objExpediente.getCliente().getRepresentanteLegal());
			objExpedienteNew.setClientedireccionprincipal(objExpediente.getCliente().getDireccionPrincipal());
			/*if(objExpediente.getCliente().getUbigeoPrincipal()!=null){
				objExpedienteNew.setClienteubigeoprincipal(objExpediente.getCliente().getUbigeoPrincipal().getIddistrito());
			}*/
			objExpedienteNew.setClientedireccionalternativa(objExpediente.getCliente().getDireccionAlternativa());
			if(objExpediente.getCliente().getUbigeoAlternativo()!=null){
				objExpedienteNew.setClienteubigeoalternativo(objExpediente.getCliente().getUbigeoAlternativo().getIddistrito());
			}
			objExpedienteNew.setClientetelefono(objExpediente.getCliente().getTelefono());
			objExpedienteNew.setClientecorreo(objExpediente.getCliente().getCorreo());
			expedienteService.registrarExpediente(objExpedienteOld,objExpedienteNew,objUsuarioSesion.getUsuario(),sTipoAuditoria);
			return Action.SUCCESS;
		}catch(Exception e){
			log.error(e.getMessage(),e);
			return Action.ERROR;
		}
	}

        /*
	public String saveExpedienteInactivar(){
	
		if(objExpediente==null){
			log.error("No hay expediente a archivar.");
			return Action.ERROR;
		}
		Map<String,Object> session=ActionContext.getContext().getSession();
		Usuario usuario=(Usuario) session.get(Constantes.SESSION_USUARIO);
		String nombrePC = (String) session.get("nombrePC");

		expedienteService.archivarExpediente(objExpediente.getIdexpediente(),usuario, nombrePC);
		return Action.SUCCESS;
	}*/

        /*
	public String saveExpedienteReabrir() throws Exception{
		try{
			Map<String,Object> session=ActionContext.getContext().getSession();
			Usuario destino=(Usuario) session.get(Constantes.SESSION_USUARIO);
			String nombrePC = (String) session.get("nombrePC");
			// Date datFechaCreacion=null;
			if(getObjExpediente()==null){
				log.error("No hay expediente a guardar ["+getObjExpediente()+"]");
				return Action.ERROR;
			}
			if(getObjExpediente().getIdexpediente()==null){
				log.debug("Creacion de Expediente");
				// datFechaCreacion=new Date();
			}else{
				Expediente objExpedienteTemp=null;
				log.debug("Actualizacion de Expediente ["+getObjExpediente().getNroexpediente()+"] con ID ["+getObjExpediente().getIdexpediente()+"]");
				log.debug("Proceso con ID ["+getObjExpediente().getProceso().getIdproceso()+"]");
				log.debug("Cliente con ID ["+getObjExpediente().getCliente().getIdCliente()+"]");
				objExpedienteTemp=getObjExpediente();
				setObjExpediente(expedienteService.findByIdExpediente(getObjExpediente().getIdexpediente()));
				getObjExpediente().setProceso(objExpedienteTemp.getProceso());
				getObjExpediente().setCliente(objExpedienteTemp.getCliente());
				getObjExpediente().setEstado(Constantes.ESTADO_ACTIVO);
			}
			this.setObjExpediente(expedienteService.saveExpediente(getObjExpediente()));
			List<Documento> docs=getObjExpediente().getDocumentoList();
			for(Documento d : docs){
				d.setEstado(Constantes.ESTADO_ACTIVO);
				d.setPropietario(destino);
				documentoService.saveDocumento(d);
				trazabilidaddocumentoService.guardarTrazabilidad(d,destino, null,Constantes.ACCION_REABRIR, null, null, nombrePC);
			}
			return Action.SUCCESS;
		}catch(Exception e){
			log.error(e.getMessage(),e);
			return Action.ERROR;
		}
	}*/

	public String saveGrupoProceso() throws Exception{
		if(getObjGrupoProceso()==null){
			log.error("No hay grupo de proceso a guardar");
			return Action.ERROR;
		}
		try{
			if(getObjGrupoProceso().getIdgrupoproceso()==null){
				getObjGrupoProceso().setFechacreacion(new Date());
				getObjGrupoProceso().setEstado(Constantes.ESTADO_ACTIVO);
			}else{
				String sNombre=getObjGrupoProceso().getNombre();
				String sCodigo=getObjGrupoProceso().getCodigo();
				String sDescripcion=getObjGrupoProceso().getDescripcion();
				setObjGrupoProceso(srvGrupoproceso.buscarObjPorId(getObjGrupoProceso().getIdgrupoproceso()));
				getObjGrupoProceso().setNombre(sNombre);
				getObjGrupoProceso().setCodigo(sCodigo);
				getObjGrupoProceso().setDescripcion(sDescripcion);
			}
			setObjGrupoProceso(srvGrupoproceso.guardarObj(getObjGrupoProceso()));
			log.debug("Grupo de Proceso creado con ID ["+getObjGrupoProceso().getIdgrupoproceso()+"]");
			return Action.SUCCESS;
		}catch(Exception e){
			log.error(e.getMessage(),e);
			return Action.ERROR;
		}
	}

	public String doEliminarGrupoProceso() throws Exception{
		if(getiIdGrupoProceso()==null){
			log.error("No se recibio ID de Grupo de Proceso");
			return Action.ERROR;
		}
		try{
			log.debug("Se eliminara logicamente el grupo de proceso con ID ["+getiIdGrupoProceso()+"]");
			setObjGrupoProceso(srvGrupoproceso.buscarObjPorId(getiIdGrupoProceso()));
			getObjGrupoProceso().setEstado(Constantes.ESTADO_INACTIVO);
			srvGrupoproceso.guardarObj(getObjGrupoProceso());
			return Action.NONE;
		}catch(Exception e){
			log.error(e.getMessage(),e);
			return Action.ERROR;
		}
	}

	public String saveModulo() throws Exception{
		if(objModulo==null){
			log.error("No hay modulo a grabar");
			return Action.ERROR;
		}
		try{
			Modulo objModuloNew=null;
			Modulo objModuloOld=null;
			String sTipoAuditoria=null;
			Usuario objUsuarioSesion=null;
			mapSession=ActionContext.getContext().getSession();
			objUsuarioSesion=(Usuario) mapSession.get(Constantes.SESSION_USUARIO);
			objModuloOld=(Modulo) mapSession.get(Constantes.SESSION_AUDITABLE);
			mapSession.remove(Constantes.SESSION_AUDITABLE);
			if(objModulo.getIdmodulo()==null){
				log.debug("** Creacion de Modulo **");
				objModuloNew=objModulo;
				objModuloNew.setEstado(Constantes.ESTADO_ACTIVO);
				sTipoAuditoria=Constantes.AUDITORIA_TIPO_REGISTRO;
			}else{
				log.debug("** Actualizacion de Modulo con ID ["+objModulo.getIdmodulo()+"] **");
				objModuloNew=srvModulo.findByIdModulo(objModulo.getIdmodulo());
				objModuloNew.setNombre(objModulo.getNombre());
				sTipoAuditoria=Constantes.AUDITORIA_TIPO_ACTUALIZACION;
			}
			srvModulo.saveModulo(objModuloOld,objModuloNew,objUsuarioSesion.getUsuario(),sTipoAuditoria);
			return Action.SUCCESS;
		}catch(Exception e){
			log.error(e.getMessage(),e);
			return Action.ERROR;
		}
	}

	public String savePerfil() throws Exception{
		if(objPerfil==null){
			log.error("No hay perfil a guardar");
			return Action.ERROR;
		}
		try{
			Perfil objPerfilNew=null;
			Perfil objPerfilOld=null;
			String sTipoAuditoria=null;
			Usuario objUsuarioSesion=null;
			mapSession=ActionContext.getContext().getSession();
			objUsuarioSesion=(Usuario) mapSession.get(Constantes.SESSION_USUARIO);
			objPerfilOld=(Perfil) mapSession.get(Constantes.SESSION_AUDITABLE);
			mapSession.remove(Constantes.SESSION_AUDITABLE);
			if(lstRecursoRight==null){
				this.lstRecursoRight=new ArrayList<Integer>();
			}
			log.debug("Nuevos recursos a guardar con size ["+lstRecursoRight.size()+"]");
			if(objPerfil.getIdperfil()==null){
				log.debug("** Creacion de Perfil **");
				objPerfilNew=objPerfil;
				objPerfilNew.setEstado(Constantes.ESTADO_ACTIVO);
				sTipoAuditoria=Constantes.AUDITORIA_TIPO_REGISTRO;
			}else{
				log.debug("** Actualizacion de perfil con ID ["+objPerfil.getIdperfil()+"] **");
				objPerfilNew=srvPerfil.findByIdPerfil(objPerfil.getIdperfil());
				objPerfilNew.setNombre(objPerfil.getNombre());
				objPerfilNew.setDescripcion(objPerfil.getDescripcion());
				sTipoAuditoria=Constantes.AUDITORIA_TIPO_ACTUALIZACION;
			}
			srvPerfil.savePerfil(objPerfilOld,objPerfilNew,lstRecursoRight,objUsuarioSesion.getUsuario(),sTipoAuditoria);
			return Action.SUCCESS;
		}catch(Exception e){
			log.error(e.getMessage(),e);
			return Action.ERROR;
		}
	}

	public String saveProceso() throws Exception{
		if(objProceso==null){
			log.error("No hay proceso a guardar");
			return Action.ERROR;
		}
		try{
			Proceso objProcesoNew=null;
			Proceso objProcesoOld=null;
			String sTipoAuditoria=null;
			Usuario objUsuarioSesion=null;
			mapSession=ActionContext.getContext().getSession();
			objUsuarioSesion=(Usuario) mapSession.get(Constantes.SESSION_USUARIO);
			objProcesoOld=(Proceso) mapSession.get(Constantes.SESSION_AUDITABLE);
			mapSession.remove(Constantes.SESSION_AUDITABLE);
			if(lstUsuarioRight==null){
				this.lstUsuarioRight=new ArrayList<Integer>();
			}
			// Se procede a agregar al responsable del proceso como participante
			// por defecto
			if(!lstUsuarioRight.contains(objProceso.getResponsable().getIdusuario())){
				lstUsuarioRight.add(objProceso.getResponsable().getIdusuario());
			}
			// Se procede a agregar al asistente del proceso como participante
			// por defecto
			if(objProceso.getIdasistente().getIdusuario()!=null&&objProceso.getIdasistente().getIdusuario()>0){
				if(!lstUsuarioRight.contains(objProceso.getIdasistente().getIdusuario())){
					lstUsuarioRight.add(objProceso.getIdasistente().getIdusuario());
				}
			}
			log.debug("Nuevos participantes a guardar con size ["+lstUsuarioRight.size()+"]");
			log.debug("Tipo de Numeracion ["+objProceso.getTiponumeracion()+"]");
			if(objProceso.getIdproceso()==null){
				log.debug("** Creacion de proceso **");
				objProcesoNew=objProceso;
				objProcesoNew.setFechacreacion(new Date());
				objProcesoNew.setEstado(Constantes.ESTADO_ACTIVO);
				sTipoAuditoria=Constantes.AUDITORIA_TIPO_REGISTRO;
				Tipoproceso objTipoProceso=srvTP.findById(objProceso.getTipoproceso().getIdtipoproceso());
				objProcesoNew.setTipoproceso(objTipoProceso);
				Usuario objResponsable=usuarioService.findByIdUsuario(objProceso.getResponsable().getIdusuario());
				objProcesoNew.setResponsable(objResponsable);
				objProcesoNew.setPorcentajealertaA(Double.valueOf(objProceso.getPorcentajealertaA()).toString());
				objProcesoNew.setPorcentajealertaR(Double.valueOf(objProceso.getPorcentajealertaR()).toString());
			}else{
				log.debug("** Actualizacion de Proceso con ID ["+objProceso.getIdproceso()+"] **");
				objProcesoNew=srvP.findByIdProceso(objProceso.getIdproceso());
				objProcesoNew.setNombre(objProceso.getNombre());
				objProcesoNew.setTipoproceso(srvTP.findById(objProceso.getTipoproceso().getIdtipoproceso()));
				objProcesoNew.setTiponumeracion(objProceso.getTiponumeracion());
				objProcesoNew.setProduccion(objProceso.getProduccion());
				objProcesoNew.setResponsable(usuarioService.findByIdUsuario(objProceso.getResponsable().getIdusuario()));
				objProcesoNew.setTiempoatencion(objProceso.getTiempoatencion());
				objProcesoNew.setDescripcion(objProceso.getDescripcion());
				objProcesoNew.setPorcentajealertaA(Double.valueOf(objProceso.getPorcentajealertaA()).toString());
				objProcesoNew.setPorcentajealertaR(Double.valueOf(objProceso.getPorcentajealertaR()).toString());
				sTipoAuditoria=Constantes.AUDITORIA_TIPO_ACTUALIZACION;
			}
			log.debug("Asistente seleccionado con ID ["+objProceso.getIdasistente().getIdusuario()+"]");
			objProcesoNew.setIdasistente((objProceso.getIdasistente().getIdusuario()==null||objProceso.getIdasistente().getIdusuario()==0) ? null : usuarioService.findByIdUsuario(objProceso.getIdasistente().getIdusuario()));
			log.debug("Tipo de acceso recibido con ID ["+objProceso.getIdtipoacceso().getIdtipoacceso()+"]");
			objProcesoNew.setIdtipoacceso((objProceso.getIdtipoacceso().getIdtipoacceso()==null||objProceso.getIdtipoacceso().getIdtipoacceso()==0) ? srvTipoAcceso.buscarObjPor(Constantes.TIPO_ACCESO_1) : srvTipoAcceso.buscarObjPorID(objProceso.getIdtipoacceso().getIdtipoacceso()));
			log.debug("Tipo de acceso seleccionado con codigo ["+objProcesoNew.getIdtipoacceso().getCodigo()+"]");
			log.debug("Grupo de proceso recibido con ID ["+objProceso.getIdgrupoproceso().getIdgrupoproceso()+"]");

			Grupoproceso gp = null;
			if(objProceso.getIdgrupoproceso().getIdgrupoproceso()==null||objProceso.getIdgrupoproceso().getIdgrupoproceso()==0){
				gp = srvGrupoproceso.buscarObjPor(Constantes.GRUPOPROCESO_ANT);
			}else{
				gp = srvGrupoproceso.buscarObjPorId(objProceso.getIdgrupoproceso().getIdgrupoproceso());
			}
			objProcesoNew.setIdgrupoproceso(gp);
			objProcesoNew.setCodigo(gp.getCodigo());

			log.debug("Grupo de proceso seleccionado con codigo ["+objProcesoNew.getIdgrupoproceso().getCodigo()+"]");

			//objProcesoNew.setIdestado(objProceso.getIdestado().getIdestado()!=null && objProceso.getIdestado().getIdestado()!=0 ? estadoDAO.findByIdestado(objProceso.getIdestado().getIdestado()): null);

			log.debug("bPermiteMetaData ["+bPermiteMetaData+"]");
			objProcesoNew.setPermitemetadata(bPermiteMetaData==true ? Constantes.PROCESO_PERMITE_METADATA_S : Constantes.PROCESO_PERMITE_METADATA_N);

			log.debug("bPermiteSumilla [" + bPermiteSumilla + "]");
			objProcesoNew.setPermitesumilla(bPermiteSumilla == true ? 'S' : 'N');

			int iMask = 0;
			char arrNewMask[] = new char[Constantes.CONFIGNOTIFMAIL_MAX_LENGTH];

			for (int i = 0; i < arrNewMask.length; i++) {
				arrNewMask[i] = '0';
			}

			if (arrConfigNotifMail != null) {
				log.debug("Se marcaron [" + arrConfigNotifMail.length + "] configuracion(es) para el envio de Correo Electronico");

				for (int i = 0; i < arrConfigNotifMail.length; i++) {
					log.debug("Configuracion marcada [" + arrConfigNotifMail[i] + "]");
					iMask |= 1 << arrConfigNotifMail[i];
					arrNewMask[arrNewMask.length - 1 - arrConfigNotifMail[i]] = '1';
				}
			} else {
				log.debug("Ninguna marcacion de configuracion(es) para el envio de Correo Electronico");
			}

			log.debug("Nueva configuracion para el envio de Correo Electronico [" + String.copyValueOf(arrNewMask) + "] [" + iMask + "]");
			objProcesoNew.setConfig_notif_mail(iMask);

			procesarEtapas();
			procesarEstados();
			srvP.saveProceso(objProcesoOld, objProcesoNew, lstUsuarioRight, lstListaRight, lstUsuarioConfidencialRight, lstNotificadoRight, objUsuarioSesion.getUsuario(), sTipoAuditoria);

			return Action.SUCCESS;
		}catch(Exception e){
			log.error(e.getMessage(),e);
			return Action.ERROR;
		}
	}

	@SuppressWarnings("unchecked")
	private void procesarEstados() {
		String estados = objProceso.getEstados();
		Object obj = JSONValue.parse(estados);
		JSONObject objeto=(JSONObject) obj;
		JSONArray items=(JSONArray) objeto.get("items");
		Iterator<JSONObject> iter=items.iterator();
		Estado objEstado=null;
		JSONObject item=null;
		Proceso objProcesoAux = srvP.findByIdProceso(objProceso.getIdproceso());

		while(iter.hasNext()){
			item=iter.next();
			Integer id=Integer.valueOf(item.get("id").toString());
			String descripcion=(String) item.get("descripcion");
			log.debug("ID [" + id + "]");
			log.debug("Descripcion [" + descripcion + "]");

			if(id.intValue() >0 && objProcesoAux.getEstadoList()!=null){
				objProceso.setEstadoList(objProcesoAux.getEstadoList());
				for(int i=0;i < objProceso.getEstadoList().size();i++){
					if(objProceso.getEstadoList().get(i).getIdestado().intValue() == id.intValue()){
						objProceso.getEstadoList().get(i).setDescripcion(descripcion);
						break;
					}
				}
			}
			else{
				objEstado=new Estado();
				objEstado.setDescripcion(descripcion);
				objEstado.setIdproceso(objProceso);
				objEstado.setEstado(Constantes.ESTADO_ACTIVO);
				objEstado.setTipo(Constantes.TIPO_ESTADO_PROCESO);
				if(objProceso.getIdproceso() != null){
					estadoDAO.saveObject(objEstado);
				}
				else{
					if(objProceso.getEstadoList() == null){
						log.debug("No hay estados asociados al proceso");
						objProceso.setEstadoList(new ArrayList<Estado>());
					}
					objProceso.getEstadoList().add(objEstado);
				}
			}
		}
		if(objProceso.getArrEstadoToDelete() != null && objProceso.getEstadoList() != null) {
			log.debug("Campos a eliminar [" + objProceso.getArrEstadoToDelete().length + "]");
			for(Integer iIdC : objProceso.getArrEstadoToDelete()){
				for(int i=0;i < objProceso.getEstadoList().size();i++){
					if(iIdC!=null && objProceso.getEstadoList().get(i).getIdestado().intValue() == iIdC.intValue()){
						objProceso.getEstadoList().get(i).setEstado(Constantes.ESTADO_INACTIVO);
						break;
					}
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void procesarEtapas() {
		String etapas = objProceso.getEtapas();
		Object obj = JSONValue.parse(etapas);
		JSONObject objeto=(JSONObject) obj;
		JSONArray items=(JSONArray) objeto.get("items");
		Iterator<JSONObject> iter=items.iterator();
		Etapa objEtapa=null;
		JSONObject item=null;
		Proceso objProcesoAux = srvP.findByIdProceso(objProceso.getIdproceso());

		while(iter.hasNext()){
			item=iter.next();
			Integer id=Integer.valueOf(item.get("id").toString());
			Integer orden=Integer.valueOf(item.get("orden").toString());
			String descripcion=(String) item.get("descripcion");
			log.debug("ID [" + id + "]");
			log.debug("Orden [" + orden + "]");
			log.debug("Descripcion [" + descripcion + "]");

			if(id.intValue() >0 && objProcesoAux.getEtapaList()!=null){
				objProceso.setEtapaList(objProcesoAux.getEtapaList());
				for(int i=0;i < objProceso.getEtapaList().size();i++){
					if(objProceso.getEtapaList().get(i).getIdetapa().intValue() == id.intValue()){
						objProceso.getEtapaList().get(i).setOrden(orden);
						objProceso.getEtapaList().get(i).setDescripcion(descripcion);
						break;
					}
				}
			}
			else{
				objEtapa=new Etapa();
				objEtapa.setOrden(orden);
				objEtapa.setDescripcion(descripcion);
				objEtapa.setProceso(objProceso);
				objEtapa.setEstado(Constantes.ESTADO_ACTIVO);
				if(objProceso.getIdproceso() != null){
					etapaService.saveObject(objEtapa);
				}
				else{
					if(objProceso.getEtapaList() == null){
						log.debug("No hay etapas asociados al proceso");
						objProceso.setEtapaList(new ArrayList<Etapa>());
					}
					objProceso.getEtapaList().add(objEtapa);
				}
			}
		}
		if(objProceso.getArrEtapaToDelete() != null && objProceso.getEtapaList() != null) {
			log.debug("Campos a eliminar [" + objProceso.getArrEtapaToDelete().length + "]");
			for(Integer iIdC : objProceso.getArrEtapaToDelete()){
				for(int i=0;i < objProceso.getEtapaList().size();i++){
					if(iIdC!=null && objProceso.getEtapaList().get(i).getIdetapa().intValue() == iIdC.intValue()){
						objProceso.getEtapaList().get(i).setEstado(Constantes.ESTADO_INACTIVO);
						break;
					}
				}
			}
		}
	}

	public String saveRecurso() throws Exception{
		if(objRecurso==null){
			log.error("No hay recurso a grabar");
			return Action.ERROR;
		}
		try{
			Recurso objRecursoNew=null;
			Recurso objRecursoOld=null;
			String sTipoAuditoria=null;
			Usuario objUsuarioSesion=null;
			mapSession=ActionContext.getContext().getSession();
			objUsuarioSesion=(Usuario) mapSession.get(Constantes.SESSION_USUARIO);
			objRecursoOld=(Recurso) mapSession.get(Constantes.SESSION_AUDITABLE);
			mapSession.remove(Constantes.SESSION_AUDITABLE);
			if(objRecurso.getIdrecurso()==null){
				log.debug("** Creacion de Recurso **");
				objRecursoNew=objRecurso;
				objRecursoNew.setEstado(Constantes.ESTADO_ACTIVO);
				sTipoAuditoria=Constantes.AUDITORIA_TIPO_REGISTRO;
				objRecursoNew.setIdmodulo(srvModulo.findByIdModulo(objRecurso.getIdmodulo().getIdmodulo()));
			}else{
				log.debug("Actualizacion de Recurso con ID ["+objRecurso.getIdrecurso()+"]");
				objRecursoNew=srvRecurso.findByIdRecurso(objRecurso.getIdrecurso());
				objRecursoNew.setIdmodulo(srvModulo.findByIdModulo(objRecurso.getIdmodulo().getIdmodulo()));
				objRecursoNew.setNombre(objRecurso.getNombre());
				objRecursoNew.setCodigo(objRecurso.getCodigo());
				objRecursoNew.setActionurl(objRecurso.getActionurl());
				objRecursoNew.setDescripcion(objRecurso.getDescripcion());
				sTipoAuditoria=Constantes.AUDITORIA_TIPO_ACTUALIZACION;
			}
			srvRecurso.saveRecurso(objRecursoOld,objRecursoNew,objUsuarioSesion.getUsuario(),sTipoAuditoria);
			return Action.SUCCESS;
		}catch(Exception e){
			log.error(e.getMessage(),e);
			return Action.ERROR;
		}
	}

	public String saveReemplazo() throws Exception {
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

		if (objReemplazo == null) {
			log.error("No se recibio reemplazo a guardar");

			return Action.ERROR;
		}

		log.debug("Fecha inicial de Reemplazo [" + fechainicialreemplazo + "] Hora inicial [" + horainicialreemplazo + "]");
		log.debug("Fecha final de Reemplazo [" + fechafinalreemplazo + "] Hora final [" + horafinalreemplazo + "]");

		try {
			objReemplazo.setFechainicialreemplazo(formatDate.parse(fechainicialreemplazo + horainicialreemplazo));
			objReemplazo.setFechafinalreemplazo(formatDate.parse(fechafinalreemplazo + horafinalreemplazo));
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
		}

		if (objReemplazo.getIdreemplazo() == null) {
			log.debug("** CREACION DE REEMPLAZO **");
			objReemplazo.setEstado(Constantes.ESTADO_ACTIVO);
		} else {
			log.debug("** " + (objReemplazo.getEstado() == 'I' ? "ELIMINACION" : "MODIFICACION") + " DE REEMPLAZO con ID [" + objReemplazo.getIdreemplazo() + "] **");
		}

		srvReemplazo.saveReemplazo(objReemplazo);

		return Action.SUCCESS;
	}

	public String saveTipodocumento() throws Exception{
		if(objTipodocumento==null){
			log.error("No hay Tipo de Documento a grabar");
			return Action.ERROR;
		}
		try{
			if(objTipodocumento.getIdtipodocumento()==null&&srvTipodocumento.findByNombre(objTipodocumento.getNombre())!=null){
				log.info("El Tipo de Documento con Nombre ["+objTipodocumento.getNombre()+"] ya existe en la Base de Datos");
				return Action.SUCCESS;
			}
			log.debug(" objTipodocumento.tiponumeracion: "+objTipodocumento.getTiponumeracion());
			Tipodocumento objTipodocumentoNew=null;
			Tipodocumento objTipodocumentoOld=null;
			String sTipoAuditoria=null;
			Usuario objUsuarioSesion=null;
			mapSession=ActionContext.getContext().getSession();
			objUsuarioSesion=(Usuario) mapSession.get(Constantes.SESSION_USUARIO);
			objTipodocumentoOld=(Tipodocumento) mapSession.get(Constantes.SESSION_AUDITABLE);
			mapSession.remove(Constantes.SESSION_AUDITABLE);
			if(objTipodocumento.getIdtipodocumento()==null){
				log.debug("** Creacion de Tipo de Documento **");
				objTipodocumentoNew=objTipodocumento;
				objTipodocumentoNew.setEstado(Constantes.ESTADO_ACTIVO);
				objTipodocumentoNew.setTiponumeracion(Constantes.NUMERACION_AUTOMATICA);
				sTipoAuditoria=Constantes.AUDITORIA_TIPO_REGISTRO;
			}else{
				log.debug("Actualizacion de Tipo de Documento con ID ["+objTipodocumento.getIdtipodocumento()+"]");
				objTipodocumentoNew=srvTipodocumento.findByIdTipoDocumento(objTipodocumento.getIdtipodocumento());
				objTipodocumentoNew.setNombre(objTipodocumento.getNombre());
				objTipodocumentoNew.setDescripcion(objTipodocumento.getDescripcion());
				objTipodocumentoNew.setTiponumeracion(objTipodocumento.getTiponumeracion());
				sTipoAuditoria=Constantes.AUDITORIA_TIPO_ACTUALIZACION;
			}
			srvTipodocumento.guardarObj(objTipodocumentoOld,objTipodocumentoNew,objUsuarioSesion.getUsuario(),sTipoAuditoria);
			return Action.SUCCESS;
		}catch(Exception e){
			log.error(e.getMessage(),e);
			return Action.ERROR;
		}
	}

	public String saveRol() throws Exception{
		if(objRol==null){
			log.error("No hay rol a guardar");
			return Action.ERROR;
		}
		try{
			Rol objRolNew=null;
			Rol objRolOld=null;
			String sTipoAuditoria=null;
			Usuario objUsuarioSesion=null;
			mapSession=ActionContext.getContext().getSession();
			objUsuarioSesion=(Usuario) mapSession.get(Constantes.SESSION_USUARIO);
			objRolOld=(Rol) mapSession.get(Constantes.SESSION_AUDITABLE);
			mapSession.remove(Constantes.SESSION_AUDITABLE);
			if(objRol.getIdrol()==null){
				log.debug("** Creacion de Rol **");
				objRolNew=objRol;
				objRolNew.setEstado(Constantes.ESTADO_ACTIVO);
				sTipoAuditoria=Constantes.AUDITORIA_TIPO_REGISTRO;
			}else{
				log.debug("** Actualizacion de Rol con ID ["+objRol.getIdrol()+"] **");
				objRolNew=srvR.findByIdRol(objRol.getIdrol());
				objRolNew.setNombre(objRol.getNombre());
				objRolNew.setDescripcion(objRol.getDescripcion());
				sTipoAuditoria=Constantes.AUDITORIA_TIPO_ACTUALIZACION;
			}
			objRolNew.setEsJefe(bEsJefe==true ? Constantes.ROL_ES_JEFE : Constantes.ROL_NO_JEFE);
			log.debug("bEsJefe ["+bEsJefe+"] objRolNew.getEsJefe ["+objRolNew.getEsJefe()+"]");
			// objRolNew.setIdunidad(srvUni.buscarObjPor(objRol.getIdunidad().getIdunidad()));
			objRolNew.setIdperfil(srvPerfil.findByIdPerfil(objRol.getIdperfil().getIdperfil()));
			srvR.guardarObj(objRolOld,objRolNew,objUsuarioSesion.getUsuario(),sTipoAuditoria);
			return Action.SUCCESS;
		}catch(Exception e){
			log.error(e.getMessage(),e);
			return Action.ERROR;
		}
	}

	public String saveSede() throws Exception{
		if (objSede == null) {
			log.error("No hay Sede a guardar");
			return Action.ERROR;
		}
		try {
			Sede objSedeNew = null;
			Sede objSedeOld = null;
			String sTipoAuditoria = null;
			Usuario objUsuarioSesion = null;
			mapSession = ActionContext.getContext().getSession();
			objUsuarioSesion = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
			objSedeOld = (Sede) mapSession.get(Constantes.SESSION_AUDITABLE);
			mapSession.remove(Constantes.SESSION_AUDITABLE);
			if (objSede.getIdsede() == null) {
				log.debug("** Creacion de Sede **");
				objSedeNew = objSede;
				Distrito objUbigeo = distritoDAO.findById(objSede.getUbigeo().getIddistrito());
				objSedeNew.setUbigeo(objUbigeo);
				sTipoAuditoria = Constantes.AUDITORIA_TIPO_REGISTRO;
			} else {
				log.debug("** Actualizacion de Sede con ID [" + objSede.getIdsede() + "] **");
				objSedeNew = srvSede.buscarPorId(objSede.getIdsede());
				objSedeNew.setNombre(objSede.getNombre());
				objSedeNew.setDescripcion(objSede.getDescripcion());
				objSedeNew.setDireccion(objSede.getDireccion());
				Distrito objUbigeo = distritoDAO.findById(objSede.getUbigeo().getIddistrito());
				objSedeNew.setUbigeo(objUbigeo);
				sTipoAuditoria = Constantes.AUDITORIA_TIPO_ACTUALIZACION;
			}
			srvSede.guardarObj(objSedeOld, objSedeNew, objUsuarioSesion.getUsuario(), sTipoAuditoria);
			return Action.SUCCESS;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return Action.ERROR;
		}
	}

	public String editProceso() throws Exception {
		try {
			mapSession = ActionContext.getContext().getSession();

			if (iIdProceso != null) {
				objProceso = srvP.findByIdProceso(iIdProceso);
				mapUsuarioLeft = usuarioService.getMapUsuarioFinalLeft(objProceso.getUsuarioList());
				mapUsuarioRight = usuarioService.getMapUsuarioFinalRight(objProceso.getUsuarioList());
				mapUsuarioConfidencialLeft = usuarioService.getMapUsuarioFinalLeft(objProceso.getUsuarioList1());
				mapUsuarioConfidencialRight = usuarioService.getMapUsuarioFinalRight(objProceso.getUsuarioList1());
				mapNotificadoLeft = usuarioService.getMapUsuarioFinalLeft(objProceso.getNotificadoList());
				mapNotificadoRight = usuarioService.getMapUsuarioFinalRight(objProceso.getNotificadoList());
				mapListaLeft = srvLista.convertFromLstToMap(srvLista.findLstByEstadoExcludedByProceso(Constantes.ESTADO_ACTIVO, objProceso));
				mapListaRight = srvLista.convertFromLstToMap(objProceso.getListaList());
				bPermiteMetaData = objProceso.getPermitemetadata() == Constantes.PROCESO_PERMITE_METADATA_S ? true : false;
				bPermiteSumilla = objProceso.getPermitesumilla() == 'S' ? true : false;
				log.debug("Proceso a ver con ID [" + objProceso.getIdproceso() + "] Nombre [" + objProceso.getNombre() + "] Participantes [" + mapUsuarioRight.size() + "] Participantes Confidenciales [" + mapUsuarioConfidencialRight.size() + "] Notificados [" + mapNotificadoRight.size() + "]");
				mapSession.put(Constantes.SESSION_AUDITABLE, objProceso);
			} else {
				mapUsuarioLeft = usuarioService.getMapByUsuarioFinal(Constantes.FLAG_USUARIO_FINAL_S);
				mapUsuarioConfidencialLeft = usuarioService.getMapByUsuarioFinal(Constantes.FLAG_USUARIO_FINAL_S);
				mapUsuarioRight = new HashMap<Integer, String>();
				mapUsuarioConfidencialRight = new HashMap<Integer, String>();
				mapNotificadoLeft = usuarioService.getMapByUsuarioFinal(Constantes.FLAG_USUARIO_FINAL_S);
				mapNotificadoRight = new HashMap<Integer, String>();
				mapListaLeft = srvLista.convertFromLstToMap(srvLista.findLstBy(Constantes.ESTADO_ACTIVO));
				mapListaRight = new HashMap<Integer, String>();
				log.debug("Usuarios Finales disponibles [" + mapUsuarioLeft.size() + "]");
				mapSession.put(Constantes.SESSION_AUDITABLE, new Proceso());
			}

			mapResponsable = usuarioService.getMapByUsuarioFinal(Constantes.FLAG_USUARIO_FINAL_S);
			mapTipoProceso = srvTP.getTipoProcesoMap();
			mapTipoAcceso = srvTipoAcceso.getMapTipoAcceso();
			mapGrupoProceso = srvGrupoproceso.getMapAll();

			int iMask = 1;
			char arrActualMask[];

			arrConfigNotifMail = new Integer[Constantes.CONFIGNOTIFMAIL_MAX_LENGTH];
			arrConfigNotifMailChecked = new Boolean[arrConfigNotifMail.length];
			arrActualMask = new char[arrConfigNotifMail.length];

			for (int i = 0; i < arrConfigNotifMail.length; i++) {
				arrConfigNotifMail[i] = i;

				if (objProceso != null && (objProceso.getConfig_notif_mail() & iMask) > 0) {
					arrConfigNotifMailChecked[i] = true;
					arrActualMask[arrConfigNotifMail.length - 1 - i] = '1';
				} else {
					arrConfigNotifMailChecked[i] = false;
					arrActualMask[arrConfigNotifMail.length - 1 - i] = '0';
				}

				iMask <<= 1;
			}

			log.debug("Configuracion para el envio de Notificaciones y Correo Electronico actual [" + String.valueOf(arrActualMask) + "]");

			return Action.SUCCESS;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return Action.ERROR;
		}
	}

	public String doEliminarProceso() throws Exception{
		if(iIdProceso==null){
			log.error("No se recibio ID de Proceso");
			return Action.ERROR;
		}
		try{
			log.debug("Se eliminara logicamente el Proceso con ID ["+iIdProceso+"]");
			srvP.updateEstadoProceso(iIdProceso,Constantes.ESTADO_INACTIVO);
			return Action.NONE;
		}catch(Exception e){
			log.error(e.getMessage(),e);
			return Action.ERROR;
		}
	}

	public String doEliminarRecurso() throws Exception{
		if(iIdRecurso==null){
			log.error("No se recibio ID Recurso");
			return Action.ERROR;
		}
		try{
			log.debug("Se eliminara logicamente el Recurso con ID ["+iIdRecurso+"]");
			srvRecurso.updateEstadoRecurso(iIdRecurso,Constantes.ESTADO_INACTIVO);
			return Action.NONE;
		}catch(Exception e){
			log.error(e.getMessage(),e);
			return Action.ERROR;
		}
	}

	public String doEliminarModulo() throws Exception{
		if(iIdModulo==null){
			log.error("No se recibio ID Modulo");
			return Action.ERROR;
		}
		try{
			log.debug("Se eliminara logicamente el Modulo con ID ["+iIdModulo+"]");
			srvModulo.updateEstadoModulo(iIdModulo,Constantes.ESTADO_INACTIVO);
			return Action.NONE;
		}catch(Exception e){
			log.error(e.getMessage(),e);
			return Action.ERROR;
		}
	}

	public String editRol() throws Exception{
		try{
			mapSession=ActionContext.getContext().getSession();
			if(iIdRol!=null){
				objRol=srvR.findByIdRol(iIdRol);
				log.debug("Rol a ver con ID ["+objRol.getIdrol()+"] Nombre ["+objRol.getNombre()+"]"+ " Es Jefe ["+objRol.getEsJefe()+"]");
				bEsJefe=objRol.getEsJefe().equals(Constantes.ROL_ES_JEFE) ? true : false;
				log.debug("bEsJefe : "+bEsJefe);
				mapSession.put(Constantes.SESSION_AUDITABLE,objRol);
			}else{
				mapSession.put(Constantes.SESSION_AUDITABLE,new Rol());
			}
			mapUnidad=srvUni.getUnidadMap();
			mapPerfil=srvPerfil.obtenerMapTodo();
			return Action.SUCCESS;
		}catch(Exception e){
			log.error(e.getMessage(),e);
			return Action.ERROR;
		}
	}

	public String editSede() throws Exception{
		try{
			mapSession=ActionContext.getContext().getSession();
			if(iIdSede!=null){
				objSede=srvSede.buscarPorId(iIdSede);
				log.debug("Sede a ver con ID ["+objSede.getIdsede()+"] Nombre ["+objSede.getNombre()+"]");
				log.debug("Departamento ["+objSede.getUbigeo().getProvincia().getDepartamento().getNombre()+"] Provincia ["+objSede.getUbigeo().getProvincia().getNombre()+"] Distrito ["+objSede.getUbigeo().getNombre()+"]");
				mapSession.put(Constantes.SESSION_AUDITABLE,objSede);
			}else{
				mapSession.put(Constantes.SESSION_AUDITABLE,new Sede());
			}
			return Action.SUCCESS;
		}catch(Exception e){
			log.error(e.getMessage(),e);
			return Action.ERROR;
		}
	}

	public String editUnidad() throws Exception{
		try{
			mapSession=ActionContext.getContext().getSession();
			if(iIdUnidad!=null){
				objUnidad=srvUni.buscarObjPor(iIdUnidad);
				log.debug("Unidad a ver con ID ["+objUnidad.getIdunidad()+"] Nombre ["+objUnidad.getNombre()+"]");
				mapSession.put(Constantes.SESSION_AUDITABLE,objUnidad);
			}else{
				mapSession.put(Constantes.SESSION_AUDITABLE,new Unidad());
			}
			mapDependencia=srvUni.getUnidadMap();
			mapSede=srvSede.obtenerMapTodo();
			return Action.SUCCESS;
		}catch(Exception e){
			log.error(e.getMessage(),e);
			return Action.ERROR;
		}
	}

	public String editParametro() throws Exception {
		try {
			if (iIdParametro != null) {
				objParametro = srvParametro.findObjById(iIdParametro);
				log.debug("Parametro a ver con ID [" + objParametro.getIdparametro() + "] Tipo [" + objParametro.getTipo() + "]");
			}

			return Action.SUCCESS;
		} catch (Exception e) {
			log.error(e.getMessage(), e);

			return Action.ERROR;
		}
	}

	public String editDepartamento() throws Exception {
		try {
			if (iIdDepartamento != null) {
				objDepartamento = srvDepartamento.findObjById(iIdDepartamento);
				log.debug("Departamento a ver con ID [" + objDepartamento.getIddepartamento() + "] Nombre [" + objDepartamento.getNombre() + "]");
			}

			return Action.SUCCESS;
		} catch (Exception e) {
			log.error(e.getMessage(), e);

			return Action.ERROR;
		}
	}

	public String editProvincia() throws Exception {
		try {
			if (iIdProvincia != null) {
				objProvincia = srvProvincia.findObjById(iIdProvincia);
				log.debug("Provincia a ver con ID [" + objProvincia.getIdprovincia() + "] Nombre [" + objProvincia.getNombre() + "]");
			}

			mapDepartamento = srvDepartamento.findMapAll();

			return Action.SUCCESS;
		} catch (Exception e) {
			log.error(e.getMessage(), e);

			return Action.ERROR;
		}
	}

	public String editDistrito() throws Exception {
		try {
			if (iIdDistrito != null) {
				objDistrito = srvDistrito.findById(iIdDistrito);
				log.debug("Distrito a ver con ID [" + objDistrito.getIddistrito() + "] Nombre [" + objDistrito.getNombre() + "]");
			}

			mapProvincia = srvProvincia.findMapAll();

			return Action.SUCCESS;
		} catch (Exception e) {
			log.error(e.getMessage(), e);

			return Action.ERROR;
		}
	}

	public String editLista() throws Exception {
		try {
			if (iIdLista != null) {
				objLista = srvLista.findByIdLista(iIdLista);
				mapAdministradorListaLeft = usuarioService.getMapUsuarioFinalLeft(objLista.getAdministradorListaList());
				mapAdministradorListaRight = usuarioService.getMapUsuarioFinalRight(objLista.getAdministradorListaList());
				mapParticipanteListaLeft = usuarioService.getMapUsuarioFinalLeft(objLista.getParticipanteListaList());
				mapParticipanteListaRight = usuarioService.getMapUsuarioFinalRight(objLista.getParticipanteListaList());
				log.debug("Lista a ver con ID [" + objLista.getIdlista() + "] Nombre [" + objLista.getNombre() + "]");
			} else {
				mapAdministradorListaLeft = usuarioService.getMapByUsuarioFinal(Constantes.FLAG_USUARIO_FINAL_S);
				mapAdministradorListaRight = new HashMap<Integer, String>();
				mapParticipanteListaLeft = usuarioService.getMapByUsuarioFinal(Constantes.FLAG_USUARIO_FINAL_S);
				mapParticipanteListaRight = new HashMap<Integer, String>();
			}

			return Action.SUCCESS;
		} catch (Exception e) {
			log.error(e.getMessage(), e);

			return Action.ERROR;
		}
	}

	public String editRecurso() throws Exception{
		try{
			mapSession=ActionContext.getContext().getSession();
			if(iIdRecurso!=null){
				objRecurso=srvRecurso.findByIdRecurso(iIdRecurso);
				log.debug("Recurso a ver con ID ["+objRecurso.getIdrecurso()+"] Nombre ["+objRecurso.getNombre()+"]");
				mapSession.put(Constantes.SESSION_AUDITABLE,objRecurso);
			}else{
				mapSession.put(Constantes.SESSION_AUDITABLE,new Recurso());
			}
			mapModulo=srvModulo.findMapAll();
			return Action.SUCCESS;
		}catch(Exception e){
			log.error(e.getMessage(),e);
			return Action.ERROR;
		}
	}

	public String editTipodocumento() throws Exception{
		try{
			mapSession=ActionContext.getContext().getSession();
			if(iIdTipodocumento!=null){
				objTipodocumento=srvTipodocumento.findByIdTipoDocumento(iIdTipodocumento);
				log.debug("Tipo de Documento a ver con ID ["+objTipodocumento.getIdtipodocumento()+"] Nombre ["+objTipodocumento.getNombre()+"]");
				mapSession.put(Constantes.SESSION_AUDITABLE,objTipodocumento);
			}else{
				mapSession.put(Constantes.SESSION_AUDITABLE,new Tipodocumento());
			}
			return Action.SUCCESS;
		}catch(Exception e){
			log.error(e.getMessage(),e);
			return Action.ERROR;
		}
	}

	public String editUsuario() throws Exception{
		try{
                        mapSession=ActionContext.getContext().getSession();
			if(iIdUsuario!=null){
				objUsuario=usuarioService.findByIdUsuario(iIdUsuario);
				objUsuario.setClave(seguridadService.desencriptar(objUsuario.getUsuario(), objUsuario.getClave()));
				log.debug("Usuario a ver con ID ["+objUsuario.getIdusuario()+"] Usuario ["+objUsuario.getUsuario()+"]");
				bEnvioCorreo=objUsuario.getEnviocorreo()==Constantes.FLAG_ENVIO_CORREO_S ? true : false;
				bUsuarioFinal=objUsuario.getUsuariofinal()==Constantes.FLAG_USUARIO_FINAL_S ? true : false;
				usuarioAnterior=objUsuario.getUsuario();
				mapSession.put(Constantes.SESSION_AUDITABLE,objUsuario);
				roles= null;//objUsuario.getRoles();
				rolesTotales=srvR.getRolesMenosUsuario(roles);
				UsuarioStor usuarioStor=usuarioStorDao.encontrarPorUsuario(objUsuario.getUsuario());
				if(usuarioStor!=null){
					idSala=usuarioStor.getSala().getIdsala();
				}
			}else{
				mapSession.put(Constantes.SESSION_AUDITABLE,new Usuario());
				roles = new ArrayList<Rol>();
				rolesTotales=srvR.getRolesMenosUsuario(roles);
			}
			mapRol=srvR.getRolMap();
			mapResponsable=usuarioService.getMapByUsuarioFinal(Constantes.FLAG_USUARIO_FINAL_S);
			return Action.SUCCESS;
		}catch(Exception e){
			log.error(e.getMessage(),e);
			return Action.ERROR;
		}
	}

	public String editReemplazo() throws Exception {
		try {
			if (iIdReemplazo != null) {
				objReemplazo = srvReemplazo.findByIdReemplazo(iIdReemplazo);

				log.debug("Reemplazo a ver con con ID [" + objReemplazo.getIdreemplazo() + "] Reemplazado [" + objReemplazo.getIdreemplazado() + "]");
			}

			return Action.SUCCESS;
		} catch (Exception e) {
			log.error(e.getMessage(), e);

			return Action.ERROR;
		}
	}

	/*
	 * @SuppressWarnings("unchecked") public String saveExpediente() throws
	 * Exception{ if(objExpediente==null){
	 * log.error("No hay expediente a guardar"); return Action.ERROR; } try{
	 * Expediente objExpedienteNew=null; Expediente objExpedienteOld=null;
	 * String sTipoAuditoria=null; Usuario objUsuarioSesion=null;
	 * mapSession=ActionContext.getContext().getSession();
	 * objUsuarioSesion=(Usuario) mapSession.get(Constantes.SESSION_USUARIO);
	 * objExpedienteOld=(Expediente)
	 * mapSession.get(Constantes.SESSION_AUDITABLE);
	 * mapSession.remove(Constantes.SESSION_AUDITABLE);
	 * if(objExpediente.getIdexpediente()==null){
	 * log.debug("** Creacion de Expediente **");
	 * objExpedienteNew=objExpediente; objExpedienteNew.setFechacreacion(new
	 * Date()); objExpedienteNew.setEstado(Constantes.ESTADO_ACTIVO);
	 * sTipoAuditoria=Constantes.AUDITORIA_TIPO_REGISTRO; }else{
	 * log.debug("** Actualizacion de Expediente con ID ["
	 * +objExpediente.getIdexpediente()+"] **");
	 * objExpedienteNew=expedienteService.findByIdExpediente
	 * (objExpediente.getIdexpediente());
	 * sTipoAuditoria=Constantes.AUDITORIA_TIPO_ACTUALIZACION; }
	 * objExpedienteNew
	 * .setProceso(srvP.findByIdProceso(objExpediente.getProceso(
	 * ).getIdproceso()));
	 * objExpedienteNew.setCliente(clienteService.findByIdCliente
	 * (objExpediente.getCliente().getIdCliente()));
	 * expedienteService.registrarExpediente
	 * (objExpedienteOld,objExpedienteNew,objUsuarioSesion
	 * .getUsuario(),sTipoAuditoria); return Action.SUCCESS; }catch(Exception
	 * e){ e.printStackTrace(); log.error(e.getMessage(),e); return
	 * Action.ERROR; } }
	 */
	public String saveUnidad() throws Exception{
		if(objUnidad==null){
			log.error("No hay unidad a guardar");
			return Action.ERROR;
		}
		try{
			String sTipoAuditoria=null;
			Unidad objUnidadNew=null;
			Unidad objUnidadOld=null;
			Usuario objUsuarioSesion=null;
			mapSession=ActionContext.getContext().getSession();
			objUsuarioSesion=(Usuario) mapSession.get(Constantes.SESSION_USUARIO);
			objUnidadOld=(Unidad) mapSession.get(Constantes.SESSION_AUDITABLE);
			mapSession.remove(Constantes.SESSION_AUDITABLE);
			if(objUnidad.getIdunidad()==null){
				log.debug("** Creacion de Unidad **");
				objUnidadNew=objUnidad;
				sTipoAuditoria=Constantes.AUDITORIA_TIPO_REGISTRO;
			}else{
				log.debug("** Actualizacion de Unidad con ID ["+objUnidad.getIdunidad()+"] **");
				objUnidadNew=srvUni.buscarObjPor(objUnidad.getIdunidad());
				objUnidadNew.setNombre(objUnidad.getNombre());
				objUnidadNew.setDescripcion(objUnidad.getDescripcion());
				objUnidadNew.setInicioEnvio(objUnidad.getInicioEnvio());
				objUnidadNew.setFinEnvio(objUnidad.getFinEnvio());
				sTipoAuditoria=Constantes.AUDITORIA_TIPO_ACTUALIZACION;
			}
			objUnidadNew.setDependencia((objUnidad.getDependencia().getIdunidad()==null||objUnidad.getDependencia().getIdunidad()==0) ? null : srvUni.buscarObjPor(objUnidad.getDependencia().getIdunidad()));
			objUnidadNew.setSede(srvSede.buscarPorId(objUnidad.getSede().getIdsede()));
			srvUni.guardarObj(objUnidadOld,objUnidadNew,objUsuarioSesion.getUsuario(),sTipoAuditoria);
			return Action.SUCCESS;
		}catch(Exception e){
			log.error(e.getMessage(),e);
			return Action.ERROR;
		}
	}

	public String saveParametro() throws Exception {
		if (objParametro == null) {
			log.error("No hay parametro a guardar");

			return Action.ERROR;
		}

		try {
			if (objParametro.getIdparametro() == null) {
				log.debug("** Creacion de Parametro **");
			} else {
				log.debug("** Actualizacion de Parametro con ID [" + objParametro.getIdparametro() + "] **");
			}

			objParametro = srvParametro.guardarObj(objParametro);

			return Action.SUCCESS;
		} catch (Exception e) {
			log.error(e.getMessage(), e);

			return Action.ERROR;
		}
	}

	public String saveDepartamento() throws Exception {
		if (objDepartamento == null) {
			log.error("No se recibio Departamento a guardar");

			return Action.ERROR;
		}

		try {
			char cMode = Constantes.OPERACION_CREATE;
			log.debug("iIdDepartamento [" + iIdDepartamento + "]");

			if (iIdDepartamento == null) {
				log.debug("** Creacion de Departamento con ID [" + objDepartamento.getIddepartamento() + "] **");

				if (srvDepartamento.findObjById(objDepartamento.getIddepartamento()) != null) {
					log.warn("Ya existe un Departamento con ID [" + objDepartamento.getIddepartamento() + "]");
					sMensaje = "Ya existe un Departamento con ID [" + objDepartamento.getIddepartamento() + "]";

					return "ya_existe";
				}
			} else {
				log.debug("** Actualizacion de Departamento con ID [" + objDepartamento.getIddepartamento() + "] **");
				cMode = Constantes.OPERACION_UPDATE;
			}

			objDepartamento = srvDepartamento.guardarObj(objDepartamento, cMode);

			return Action.SUCCESS;
		} catch (Exception e) {
			log.error(e.getMessage(), e);

			return Action.ERROR;
		}
	}

	public String saveProvincia() throws Exception {
		if (objProvincia == null) {
			log.error("No se recibio Provincia a guardar");

			return Action.ERROR;
		}

		try {
			char cMode = Constantes.OPERACION_CREATE;
			log.debug("iIdProvincia [" + iIdProvincia + "]");

			if (iIdProvincia == null) {
				log.debug("** Creacion de Provincia con ID [" + objProvincia.getIdprovincia() + "] **");

				if (srvProvincia.findObjById(objProvincia.getIdprovincia()) != null) {
					log.warn("Ya existe una Provincia con ID [" + objProvincia.getIdprovincia() + "]");
					sMensaje = "Ya existe una Provincia con ID [" + objProvincia.getIdprovincia() + "]";
					mapDepartamento = srvDepartamento.findMapAll();

					return "ya_existe";
				}
			} else {
				log.debug("** Actualizacion de Provincia con ID [" + objProvincia.getIdprovincia() + "] **");
				cMode = Constantes.OPERACION_UPDATE;
			}

			objProvincia = srvProvincia.guardarObj(objProvincia, cMode);

			return Action.SUCCESS;
		} catch (Exception e) {
			log.error(e.getMessage(), e);

			return Action.ERROR;
		}
	}

	public String saveDistrito() throws Exception {
		if (objDistrito == null) {
			log.error("No se recibio Distrito a guardar");

			return Action.ERROR;
		}

		try {
			char cMode = Constantes.OPERACION_CREATE;
			log.debug("iIdDistrito [" + iIdDistrito + "]");

			if (iIdDistrito == null) {
				log.debug("** Creacion de Distrito con ID [" + objDistrito.getIddistrito() + "] **");

				if (srvDistrito.findById(objDistrito.getIddistrito()) != null) {
					log.warn("Ya existe un Distrito con ID [" + objDistrito.getIddistrito() + "]");
					sMensaje = "Ya existe un Distrito con ID [" + objDistrito.getIddistrito() + "]";
					mapProvincia = srvProvincia.findMapAll();

					return "ya_existe";
				}
			} else {
				log.debug("** Actualizacion de Distrito con ID [" + objDistrito.getIddistrito() + "] **");
				cMode = Constantes.OPERACION_UPDATE;
			}

			objDistrito = srvDistrito.guardarObj(objDistrito, cMode);

			return Action.SUCCESS;
		} catch (Exception e) {
			log.error(e.getMessage(), e);

			return Action.ERROR;
		}
	}

	public String saveLista() throws Exception {
		if (objLista == null) {
			log.error("No se recibio Lista a guardar");

			return Action.ERROR;
		}

		try {
			Lista objListaNew = null;

			if (objLista.getIdlista() == null) {
				log.debug("** Creacion de Lista **");
				objListaNew = objLista;
				objListaNew.setFechacreacion(new Date());
				objListaNew.setEstado(Constantes.ESTADO_ACTIVO);
			} else {
				log.debug("** Actualizacion de Lista con ID [" + objLista.getIdlista() + "] **");
				objListaNew = srvLista.findByIdLista(objLista.getIdlista());
				objListaNew.setNombre(objLista.getNombre());
			}

			log.debug("Lista de administradores [" + (lstAdministradorListaRight == null ? lstAdministradorListaRight : lstAdministradorListaRight.size()) + "]");
			log.debug("Lista de participantes [" + (lstParticipanteListaRight == null ? lstParticipanteListaRight : lstParticipanteListaRight.size()) + "]");
			srvLista.guardarObj(objListaNew, lstAdministradorListaRight, lstParticipanteListaRight);

			return Action.SUCCESS;
		} catch (Exception e) {
			log.error(e.getMessage(), e);

			return Action.ERROR;
		}
	}

	public String saveUsuario() throws Exception{

		if(objUsuario==null){
			log.error("No hay usuario a guardar");
			return Action.ERROR;
		}
		// try{
		String sTipoAuditoria=null;
		Usuario objUsuarioNew=null;
		Usuario objUsuarioOld=null;
		Usuario objUsuarioSesion=null;
		mapSession=ActionContext.getContext().getSession();
		objUsuarioSesion=(Usuario) getMapSession().get(Constantes.SESSION_USUARIO);
		objUsuarioOld=(Usuario) mapSession.get(Constantes.SESSION_AUDITABLE);
		mapSession.remove(Constantes.SESSION_AUDITABLE);

		if(objUsuario.getIdusuario()==null){
			log.debug("** Creacion de Usuario **");
			objUsuarioNew=objUsuario;
			objUsuarioNew.setEstado(Constantes.ESTADO_ACTIVO);
			sTipoAuditoria=Constantes.AUDITORIA_TIPO_REGISTRO;
			// objUsuarioNew.setRol(srvR.findByIdRol(objUsuario.getRol().getIdrol()));
			objUsuarioNew.setFlagviewtrazabilidad("0");
			objUsuarioNew.setFlagdocumentocf("0");
			objUsuarioNew.setClave(seguridadService.encriptar(objUsuarioNew.getUsuario(), objUsuarioNew.getClaveSiged()));
		}else{
			log.debug("** Actualizacion de Usuario con ID ["+objUsuario.getIdusuario()+"] **");
			objUsuarioNew=usuarioStorDao.encontrarPorUsuario(objUsuario.getUsuario());
			if(objUsuarioNew==null){
				objUsuarioNew=usuarioService.findByIdUsuario(objUsuario.getIdusuario());
			}else{
				Sala sala=salaDao.findByIdSala(idSala);
				if(sala!=null){
					((UsuarioStor) objUsuarioNew).setSala(sala);
				}else if(((UsuarioStor) objUsuarioNew).getSala()!=null){
					((UsuarioStor) objUsuarioNew).setSala(null);
				}
			}
			objUsuarioNew.setUsuario(objUsuario.getUsuario());
			//objUsuarioNew.setClave(objUsuario.getClave());
			objUsuarioNew.setClaveSiged(objUsuario.getClaveSiged());
			objUsuarioNew.setNombres(objUsuario.getNombres());
			objUsuarioNew.setApellidos(objUsuario.getApellidos());
			objUsuarioNew.setCorreo(objUsuario.getCorreo());
			// objUsuarioNew.setRol(srvR.findByIdRol(objUsuario.getRol().getIdrol()));
			sTipoAuditoria=Constantes.AUDITORIA_TIPO_ACTUALIZACION;
		}
		//log.debug("Jefe seleccionado con ID ["+objUsuario.getJefe().getIdusuario()+"]");
		objUsuarioNew.setJefe(   (objUsuario==null||objUsuario.getJefe()==null||(objUsuario.getJefe().getIdusuario()==null||objUsuario.getJefe().getIdusuario()==0)) ? null : usuarioService.findByIdUsuario(objUsuario.getJefe().getIdusuario()));
		objUsuarioNew.setUnidad((objUsuario.getUnidad().getIdunidad()==null||objUsuario.getUnidad().getIdunidad()==0) ? null : srvUni.buscarObjPor(objUsuario.getUnidad().getIdunidad()));
		objUsuarioNew.setUsuariofinal(bUsuarioFinal==true ? Constantes.FLAG_USUARIO_FINAL_S : Constantes.FLAG_USUARIO_FINAL_N);
		log.debug("bUsuarioFinal ["+bUsuarioFinal+"] Usuario.usuariofinal ["+objUsuarioNew.getUsuariofinal()+"]");
		objUsuarioNew.setEnviocorreo(bEnvioCorreo==true ? Constantes.FLAG_ENVIO_CORREO_S : Constantes.FLAG_ENVIO_CORREO_N);
		log.debug("bEnvioCorreo ["+bEnvioCorreo+"] Usuario.enviocorreo ["+getObjUsuario().getEnviocorreo()+"]");
		// objUsuarioNew.setRoles(rolesUsuario);
		if(rolesUsuario==null){
			String[] rolesU=ServletActionContext.getRequest().getParameterValues("rolesUsuario");
			if(rolesU!=null){
				rolesUsuario=new ArrayList<Integer>();
				for(String rol : rolesU){
					rolesUsuario.add(Integer.parseInt(rol));
				}
			}
		}

		objUsuarioNew.setClaveSiged(seguridadService.encriptar(objUsuarioNew.getUsuario(), objUsuarioNew.getClaveSiged()));
		objUsuarioNew.setBandejaAgrupada(Constantes.Si);
		usuarioService.saveUsuario(objUsuarioOld,objUsuarioNew,rolesUsuario,objUsuarioSesion.getUsuario(),sTipoAuditoria,usuarioAnterior);

		return Action.SUCCESS;
		/*
		 * }catch(Exception e){ log.error(e.getMessage(),e);
		 *
		 * return Action.ERROR; }
		 */
	}

	public String viewUsuario() throws Exception{
		if(getIIdUsuario()==null){
			log.error("No se recibio ningun ID de Usuario");
			return Action.ERROR;
		}
		try{
			setObjUsuario(usuarioService.findByIdUsuario(getIIdUsuario()));
			log.debug("Usuario a ver con ID ["+getObjUsuario().getIdusuario()+"] Usuario ["+getObjUsuario().getUsuario()+"]");
			return Action.SUCCESS;
		}catch(Exception e){
			log.error(e.getMessage(),e);
			return Action.ERROR;
		}
	}

	public String viewCompUsuarios(){

		return Action.SUCCESS;
	}

	public String editCompUsuarios(){
		try{
			if (iIdUsuario != null) {

				objUsuario = usuarioService.findByIdUsuario(iIdUsuario);

				mapAdministradorListaLeft = usuarioService.getMapUsuarioFinalLeft(objUsuario.getAdministradorxusuario());
				mapAdministradorListaRight = usuarioService.getMapUsuarioFinalRight(objUsuario.getAdministradorxusuario());
				mapParticipanteListaLeft = usuarioService.getMapUsuarioFinalLeft(objUsuario.getCompartidoxusuario());
				mapParticipanteListaRight = usuarioService.getMapUsuarioFinalRight(objUsuario.getCompartidoxusuario());
				log.debug("Lista a ver con ID [" + objUsuario.getIdusuario() + "] Nombre [" + objUsuario.getNombres() + "]");
			} else {
				mapAdministradorListaLeft = usuarioService.getMapByUsuarioFinal(Constantes.FLAG_USUARIO_FINAL_S);
				mapAdministradorListaRight = new HashMap<Integer, String>();
				mapParticipanteListaLeft = usuarioService.getMapByUsuarioFinal(Constantes.FLAG_USUARIO_FINAL_S);
				mapParticipanteListaRight = new HashMap<Integer, String>();
			}

			return Action.SUCCESS;
		}catch(Exception e){
			log.error(e.getMessage(),e);
			return Action.ERROR;
		}
	}

	public String sincronizarUsuarios(){
		usuarioService.sincronizarUsuariosSeguridad();
		return Action.SUCCESS;
	}

	public String guardarUsuariosLDAP(){
		usuarioService.guardarUsuariosLDAP();
		return Action.SUCCESS;
	}

	public String saveCompartidos(){
		if (iIdUsuario == null) {
			log.error("No se recibio Lista a guardar");
			return Action.ERROR;
		}
		try {
			objUsuario=usuarioService.findByIdUsuario(iIdUsuario);
			log.debug("Lista de administradores [" + (lstAdministradorListaRight == null ? lstAdministradorListaRight : lstAdministradorListaRight.size()) + "]");
			log.debug("Lista de participantes [" + (lstParticipanteListaRight == null ? lstParticipanteListaRight : lstParticipanteListaRight.size()) + "]");
			usuarioService.guardarObj(objUsuario,lstAdministradorListaRight, lstParticipanteListaRight);

			return Action.SUCCESS;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return Action.ERROR;
		}
	}

	// ////////////////////////////////
	// Getters and Setters //
	// ////////////////////////////////
	public Boolean getBEnvioCorreo(){
		return bEnvioCorreo;
	}

	public void setBEnvioCorreo(Boolean bEnvioCorreo){
		this.bEnvioCorreo=bEnvioCorreo;
	}

	public Boolean getBPermiteMetaData(){
		return bPermiteMetaData;
	}

	public void setBPermiteMetaData(Boolean bPermiteMetaData){
		this.bPermiteMetaData=bPermiteMetaData;
	}

	public Boolean getbPermiteSumilla() {
		return bPermiteSumilla;
	}

	public void setbPermiteSumilla(Boolean bPermiteSumilla) {
		this.bPermiteSumilla = bPermiteSumilla;
	}

	public Boolean getBUsuarioFinal(){
		return bUsuarioFinal;
	}

	public void setBUsuarioFinal(Boolean bUsuarioFinal){
		this.bUsuarioFinal=bUsuarioFinal;
	}

	public Integer getiIdCliente(){
		return iIdCliente;
	}

	public void setiIdCliente(Integer iIdCliente){
		this.iIdCliente=iIdCliente;
	}

	public Integer getIIdModulo(){
		return iIdModulo;
	}

	public void setIIdModulo(Integer iIdModulo){
		this.iIdModulo=iIdModulo;
	}

	public Integer getIIdPerfil(){
		return iIdPerfil;
	}

	public void setIIdPerfil(Integer iIdPerfil){
		this.iIdPerfil=iIdPerfil;
	}

	public Integer getiIdPlantilla() {
		return iIdPlantilla;
	}

	public void setiIdPlantilla(Integer iIdPlantilla) {
		this.iIdPlantilla = iIdPlantilla;
	}

	public Integer getIIdProceso(){
		return iIdProceso;
	}

	public void setIIdProceso(Integer iIdProceso){
		this.iIdProceso=iIdProceso;
	}

	public Integer getIIdRecurso(){
		return iIdRecurso;
	}

	public void setIIdRecurso(Integer iIdRecurso){
		this.iIdRecurso=iIdRecurso;
	}

	public Integer getIIdRol(){
		return iIdRol;
	}

	public void setIIdRol(Integer iIdRol){
		this.iIdRol=iIdRol;
	}

	public Integer getIIdSede(){
		return iIdSede;
	}

	public void setIIdSede(Integer iIdSede){
		this.iIdSede=iIdSede;
	}

	public Integer getIIdTipodocumento(){
		return iIdTipodocumento;
	}

	public void setIIdTipodocumento(Integer iIdTipodocumento){
		this.iIdTipodocumento=iIdTipodocumento;
	}

	public Integer getIIdUnidad(){
		return iIdUnidad;
	}

	public void setIIdUnidad(Integer iIdUnidad){
		this.iIdUnidad=iIdUnidad;
	}

	public Integer getIIdUsuario(){
		return iIdUsuario;
	}

	public void setIIdUsuario(Integer iIdUsuario){
		this.iIdUsuario=iIdUsuario;
	}

	public List<Integer> getLstRecursoRight(){
		return lstRecursoRight;
	}

	public void setLstRecursoRight(List<Integer> lstRecursoRight){
		this.lstRecursoRight=lstRecursoRight;
	}

	public List<Integer> getLstUsuarioConfidencialRight(){
		return lstUsuarioConfidencialRight;
	}

	public void setLstUsuarioConfidencialRight(List<Integer> lstUsuarioConfidencialRight){
		this.lstUsuarioConfidencialRight=lstUsuarioConfidencialRight;
	}

	public List<Integer> getLstUsuarioRight(){
		return lstUsuarioRight;
	}

	public void setLstUsuarioRight(List<Integer> lstUsuarioRight){
		this.lstUsuarioRight=lstUsuarioRight;
	}

	public List<Integer> getLstNotificadoRight() {
		return lstNotificadoRight;
	}

	public void setLstNotificadoRight(List<Integer> lstNotificadoRight) {
		this.lstNotificadoRight = lstNotificadoRight;
	}

	public Map<Integer,String> getMapDependencia(){
		return mapDependencia;
	}

	public void setMapDependencia(Map<Integer,String> mapDependencia){
		this.mapDependencia=mapDependencia;
	}

	/*
	 * public Map getMapEstado(){ return mapEstado; }
	 *
	 * public void setMapEstado(Map mapEstado){ this.mapEstado=mapEstado; }
	 *
	 * public Map getMapEtapa(){ return mapEtapa; }
	 *
	 * public void setMapEtapa(Map mapEtapa){ this.mapEtapa=mapEtapa; }
	 */
	public Map<Integer,String> getMapGrupoProceso(){
		return mapGrupoProceso;
	}

	public void setMapGrupoProceso(Map<Integer,String> mapGrupoProceso){
		this.mapGrupoProceso=mapGrupoProceso;
	}

	public Map<Integer,String> getMapModulo(){
		return mapModulo;
	}

	public void setMapModulo(Map<Integer,String> mapModulo){
		this.mapModulo=mapModulo;
	}

	public Map<Integer,String> getMapPerfil(){
		return mapPerfil;
	}

	public void setMapPerfil(Map<Integer,String> mapPerfil){
		this.mapPerfil=mapPerfil;
	}

	public Map<Integer,String> getMapRecursoLeft(){
		return mapRecursoLeft;
	}

	public void setMapRecursoLeft(Map<Integer,String> mapRecursoLeft){
		this.mapRecursoLeft=mapRecursoLeft;
	}

	public Map<Integer,String> getMapRecursoRight(){
		return mapRecursoRight;
	}

	public void setMapRecursoRight(Map<Integer,String> mapRecursoRight){
		this.mapRecursoRight=mapRecursoRight;
	}

	public Map<Integer,String> getMapResponsable(){
		return mapResponsable;
	}

	public void setMapResponsable(Map<Integer,String> mapResponsable){
		this.mapResponsable=mapResponsable;
	}

	public Map<String,Object> getMapSession(){
		return mapSession;
	}

	public void setMapSession(Map<String,Object> mapSession){
		this.mapSession=mapSession;
	}

	public Map<Integer,String> getMapSede(){
		return mapSede;
	}

	public void setMapSede(Map<Integer,String> mapSede){
		this.mapSede=mapSede;
	}

	public Map<Integer,String> getMapRol(){
		return mapRol;
	}

	public void setMapRol(Map<Integer,String> mapRol){
		this.mapRol=mapRol;
	}

	public Map<Integer,String> getMapTipoAcceso(){
		return mapTipoAcceso;
	}

	public void setMapTipoAcceso(Map<Integer,String> mapTipoAcceso){
		this.mapTipoAcceso=mapTipoAcceso;
	}

	public Map<Integer,String> getMapTipoProceso(){
		return mapTipoProceso;
	}

	public void setMapTipoProceso(Map<Integer,String> mapTipoProceso){
		this.mapTipoProceso=mapTipoProceso;
	}

	public Map<Integer,String> getMapUnidad(){
		return mapUnidad;
	}

	public void setMapUnidad(Map<Integer,String> mapUnidad){
		this.mapUnidad=mapUnidad;
	}

	public Map<Integer,String> getMapUsuarioConfidencialLeft(){
		return mapUsuarioConfidencialLeft;
	}

	public void setMapUsuarioConfidencialLeft(Map<Integer,String> mapUsuarioConfidencialLeft){
		this.mapUsuarioConfidencialLeft=mapUsuarioConfidencialLeft;
	}

	public Map<Integer,String> getMapUsuarioLeft(){
		return mapUsuarioLeft;
	}

	public void setMapUsuarioLeft(Map<Integer,String> mapUsuarioLeft){
		this.mapUsuarioLeft=mapUsuarioLeft;
	}

	public Map<Integer,String> getMapUsuarioConfidencialRight(){
		return mapUsuarioConfidencialRight;
	}

	public void setMapUsuarioConfidencialRight(Map<Integer,String> mapUsuarioConfidencialRight){
		this.mapUsuarioConfidencialRight=mapUsuarioConfidencialRight;
	}

	public Map<Integer,String> getMapUsuarioRight(){
		return mapUsuarioRight;
	}

	public void setMapUsuarioRight(Map<Integer,String> mapUsuarioRight){
		this.mapUsuarioRight=mapUsuarioRight;
	}

	public Map<Integer, String> getMapNotificadoLeft() {
		return mapNotificadoLeft;
	}

	public void setMapNotificadoLeft(Map<Integer, String> mapNotificadoLeft) {
		this.mapNotificadoLeft = mapNotificadoLeft;
	}

	public Map<Integer, String> getMapNotificadoRight() {
		return mapNotificadoRight;
	}

	public void setMapNotificadoRight(Map<Integer, String> mapNotificadoRight) {
		this.mapNotificadoRight = mapNotificadoRight;
	}

	public Cliente getObjCliente(){
		return objCliente;
	}

	public void setObjCliente(Cliente objCliente){
		this.objCliente=objCliente;
	}

	public GrupoprocesoService getSrvGrupoproceso(){
		return srvGrupoproceso;
	}

	public void setSrvGrupoproceso(GrupoprocesoService srvGrupoproceso){
		this.srvGrupoproceso=srvGrupoproceso;
	}

	public Modulo getObjModulo(){
		return objModulo;
	}

	public void setObjModulo(Modulo objModulo){
		this.objModulo=objModulo;
	}

	public ModuloService getSrvModulo(){
		return srvModulo;
	}

	public void setSrvModulo(ModuloService srvModulo){
		this.srvModulo=srvModulo;
	}

	public Perfil getObjPerfil(){
		return objPerfil;
	}

	public void setObjPerfil(Perfil objPerfil){
		this.objPerfil=objPerfil;
	}

	public PerfilService getSrvPerfil(){
		return srvPerfil;
	}

	public void setSrvPerfil(PerfilService srvPerfil){
		this.srvPerfil=srvPerfil;
	}

	public Plantilla getObjPlantilla() {
		return objPlantilla;
	}

	public void setObjPlantilla(Plantilla objPlantilla) {
		this.objPlantilla = objPlantilla;
	}

	public PlantillaService getSrvPlantilla() {
		return srvPlantilla;
	}

	public void setSrvPlantilla(PlantillaService srvPlantilla) {
		this.srvPlantilla = srvPlantilla;
	}

	public Proceso getObjProceso(){
		return objProceso;
	}

	public void setObjProceso(Proceso objProceso){
		this.objProceso=objProceso;
	}

	public ProcesoService getSrvP(){
		return srvP;
	}

	public void setSrvP(ProcesoService srvP){
		this.srvP=srvP;
	}

	public Recurso getObjRecurso(){
		return objRecurso;
	}

	public void setObjRecurso(Recurso objRecurso){
		this.objRecurso=objRecurso;
	}

	public RecursoService getSrvRecurso(){
		return srvRecurso;
	}

	public void setSrvRecurso(RecursoService srvRecurso){
		this.srvRecurso=srvRecurso;
	}

	public Rol getObjRol(){
		return objRol;
	}

	public void setObjRol(Rol objRol){
		this.objRol=objRol;
	}

	public RolService getSrvR(){
		return srvR;
	}

	public void setSrvR(RolService srvR){
		this.srvR=srvR;
	}

	public Sede getObjSede(){
		return objSede;
	}

	public void setObjSede(Sede objSede){
		this.objSede=objSede;
	}

	public SedeService getSrvSede(){
		return srvSede;
	}

	public void setSrvSede(SedeService srvSede){
		this.srvSede=srvSede;
	}

	public TipoaccesoService getSrvTipoAcceso(){
		return srvTipoAcceso;
	}

	public void setSrvTipoAcceso(TipoaccesoService srvTipoAcceso){
		this.srvTipoAcceso=srvTipoAcceso;
	}

	public Tipodocumento getObjTipodocumento(){
		return objTipodocumento;
	}

	public void setObjTipodocumento(Tipodocumento objTipodocumento){
		this.objTipodocumento=objTipodocumento;
	}

	public TipodocumentoService getSrvTipodocumento(){
		return srvTipodocumento;
	}

	public void setSrvTipodocumento(TipodocumentoService srvTipodocumento){
		this.srvTipodocumento=srvTipodocumento;
	}

	public TipoprocesoService getSrvTP(){
		return srvTP;
	}

	public void setSrvTP(TipoprocesoService srvTP){
		this.srvTP=srvTP;
	}

	public Unidad getObjUnidad(){
		return objUnidad;
	}

	public void setObjUnidad(Unidad objUnidad){
		this.objUnidad=objUnidad;
	}

	public UnidadService getSrvUni(){
		return srvUni;
	}

	public void setSrvUni(UnidadService srvUni){
		this.srvUni=srvUni;
	}

	public Usuario getObjUsuario(){
		return objUsuario;
	}

	public void setObjUsuario(Usuario objUsuario){
		this.objUsuario=objUsuario;
	}

	public Integer getIIdExpediente(){
		return iIdExpediente;
	}

	public void setIIdExpediente(Integer iIdExpediente){
		this.iIdExpediente=iIdExpediente;
	}

	public Expediente getObjExpediente(){
		return objExpediente;
	}

	public void setObjExpediente(Expediente objExpediente){
		this.objExpediente=objExpediente;
	}

	public DocumentoService getDocumentoService(){
		return documentoService;
	}

	public void setDocumentoService(DocumentoService documentoService){
		this.documentoService=documentoService;
	}

	public DistritoDAO getDistritoDAO(){
		return distritoDAO;
	}

	public void setDistritoDAO(DistritoDAO distritoDAO){
		this.distritoDAO=distritoDAO;
	}

	public int getDocumentoPrincipal(){
		return documentoPrincipal;
	}

	public void setDocumentoPrincipal(int documentoPrincipal){
		this.documentoPrincipal=documentoPrincipal;
	}

	public String getVencimiento(){
		return vencimiento;
	}

	public void setVencimiento(String vencimiento){
		this.vencimiento=vencimiento;
	}

	public String getUsuarioAnterior(){
		return usuarioAnterior;
	}

	public void setUsuarioAnterior(String usuarioAnterior){
		this.usuarioAnterior=usuarioAnterior;
	}

	public List<Rol> getRoles(){
		return roles;
	}

	public void setRoles(List<Rol> roles){
		this.roles=roles;
	}

	public List<Rol> getRolesTotales(){
		return rolesTotales;
	}

	public void setRolesTotales(List<Rol> rolesTotales){
		this.rolesTotales=rolesTotales;
	}

	public List<Integer> getRolesUsuario(){
		return rolesUsuario;
	}

	/*
	 * public void setRolesUsuario(String[] roles){ rolesUsuario=new
	 * ArrayList<Integer>(); for(String rol : roles){
	 * rolesUsuario.add(Integer.parseInt(rol)); } }
	 */
	public void setRolesUsuario(List<Integer> rolesUsuario){
		this.rolesUsuario=rolesUsuario;
	}

	public int getIdSala(){

		return idSala;
	}

	public void setIdSala(int idSala){
		this.idSala=idSala;
	}

	public void setUsuarioStorDao(UsuariostorDAO usuarioStorDao){
		this.usuarioStorDao=usuarioStorDao;
	}

	public void setSalaDao(SalaDAO salaDao){
		this.salaDao=salaDao;
	}

	public void setClienteService(ClienteService clienteService){
		this.clienteService=clienteService;
	}

	public void setExpedienteService(ExpedienteService expedienteService){
		this.expedienteService=expedienteService;
	}

	public void setUsuarioService(UsuarioService usuarioService){
		this.usuarioService=usuarioService;
	}

	public void setSrvNumeracion(NumeracionService srvNumeracion) {
		this.srvNumeracion = srvNumeracion;
	}

	public NumeracionService getSrvNumeracion() {
		return srvNumeracion;
	}

	public void setObjNumeracion(Numeracion objNumeracion) {
		this.objNumeracion = objNumeracion;
	}

	public Numeracion getObjNumeracion() {
		return objNumeracion;
	}

	public void setiIdGrupoProceso(Integer iIdGrupoProceso) {
		this.iIdGrupoProceso = iIdGrupoProceso;
	}

	public Integer getiIdGrupoProceso() {
		return iIdGrupoProceso;
	}

	public void setObjGrupoProceso(Grupoproceso objGrupoProceso) {
		this.objGrupoProceso = objGrupoProceso;
	}

	public Grupoproceso getObjGrupoProceso() {
		return objGrupoProceso;
	}

	public Boolean[] getArrConfigNotifMailChecked() {
		return arrConfigNotifMailChecked;
	}

	public void setArrConfigNotifMailChecked(Boolean[] arrConfigNotifMailChecked) {
		this.arrConfigNotifMailChecked = arrConfigNotifMailChecked;
	}

	public Integer[] getArrConfigNotifMail() {
		return arrConfigNotifMail;
	}

	public void setArrConfigNotifMail(Integer[] arrConfigNotifMail) {
		this.arrConfigNotifMail = arrConfigNotifMail;
	}

	public Integer getiIdParametro() {
		return iIdParametro;
	}

	public void setiIdParametro(Integer iIdParametro) {
		this.iIdParametro = iIdParametro;
	}

	public ParametroService getSrvParametro() {
		return srvParametro;
	}

	public void setSrvParametro(ParametroService srvParametro) {
		this.srvParametro = srvParametro;
	}

	public Parametro getObjParametro() {
		return objParametro;
	}

	public void setObjParametro(Parametro objParametro) {
		this.objParametro = objParametro;
	}

	public Integer getiIdDepartamento() {
		return iIdDepartamento;
	}

	public void setiIdDepartamento(Integer iIdDepartamento) {
		this.iIdDepartamento = iIdDepartamento;
	}

	public Departamento getObjDepartamento() {
		return objDepartamento;
	}

	public void setObjDepartamento(Departamento objDepartamento) {
		this.objDepartamento = objDepartamento;
	}

	public Map<Integer, String> getMapDepartamento() {
		return mapDepartamento;
	}

	public void setMapDepartamento(Map<Integer, String> mapDepartamento) {
		this.mapDepartamento = mapDepartamento;
	}

	public DepartamentoService getSrvDepartamento() {
		return srvDepartamento;
	}

	public void setSrvDepartamento(DepartamentoService srvDepartamento) {
		this.srvDepartamento = srvDepartamento;
	}

	public Integer getiIdProvincia() {
		return iIdProvincia;
	}

	public void setiIdProvincia(Integer iIdProvincia) {
		this.iIdProvincia = iIdProvincia;
	}

	public Provincia getObjProvincia() {
		return objProvincia;
	}

	public void setObjProvincia(Provincia objProvincia) {
		this.objProvincia = objProvincia;
	}

	public Map<Integer, String> getMapProvincia() {
		return mapProvincia;
	}

	public void setMapProvincia(Map<Integer, String> mapProvincia) {
		this.mapProvincia = mapProvincia;
	}

	public ProvinciaService getSrvProvincia() {
		return srvProvincia;
	}

	public void setSrvProvincia(ProvinciaService srvProvincia) {
		this.srvProvincia = srvProvincia;
	}

	public Integer getiIdDistrito() {
		return iIdDistrito;
	}

	public void setiIdDistrito(Integer iIdDistrito) {
		this.iIdDistrito = iIdDistrito;
	}

	public Distrito getObjDistrito() {
		return objDistrito;
	}

	public void setObjDistrito(Distrito objDistrito) {
		this.objDistrito = objDistrito;
	}

	public DistritoService getSrvDistrito() {
		return srvDistrito;
	}

	public void setSrvDistrito(DistritoService srvDistrito) {
		this.srvDistrito = srvDistrito;
	}

	public String getsMensaje() {
		return sMensaje;
	}

	public void setsMensaje(String sMensaje) {
		this.sMensaje = sMensaje;
	}

	public Integer getiIdLista() {
		return iIdLista;
	}

	public void setiIdLista(Integer iIdLista) {
		this.iIdLista = iIdLista;
	}

	public Lista getObjLista() {
		return objLista;
	}

	public void setObjLista(Lista objLista) {
		this.objLista = objLista;
	}

	public ListaService getSrvLista() {
		return srvLista;
	}

	public void setSrvLista(ListaService srvLista) {
		this.srvLista = srvLista;
	}

	public List<Integer> getLstAdministradorListaRight() {
		return lstAdministradorListaRight;
	}

	public void setLstAdministradorListaRight(List<Integer> lstAdministradorListaRight) {
		this.lstAdministradorListaRight = lstAdministradorListaRight;
	}

	public List<Integer> getLstParticipanteListaRight() {
		return lstParticipanteListaRight;
	}

	public void setLstParticipanteListaRight(List<Integer> lstParticipanteListaRight) {
		this.lstParticipanteListaRight = lstParticipanteListaRight;
	}

	public Map<Integer, String> getMapAdministradorListaLeft() {
		return mapAdministradorListaLeft;
	}

	public void setMapAdministradorListaLeft(Map<Integer, String> mapAdministradorListaLeft) {
		this.mapAdministradorListaLeft = mapAdministradorListaLeft;
	}

	public Map<Integer, String> getMapAdministradorListaRight() {
		return mapAdministradorListaRight;
	}

	public void setMapAdministradorListaRight(Map<Integer, String> mapAdministradorListaRight) {
		this.mapAdministradorListaRight = mapAdministradorListaRight;
	}

	public Map<Integer, String> getMapParticipanteListaLeft() {
		return mapParticipanteListaLeft;
	}

	public void setMapParticipanteListaLeft(Map<Integer, String> mapParticipanteListaLeft) {
		this.mapParticipanteListaLeft = mapParticipanteListaLeft;
	}

	public Map<Integer, String> getMapParticipanteListaRight() {
		return mapParticipanteListaRight;
	}

	public void setMapParticipanteListaRight(Map<Integer, String> mapParticipanteListaRight) {
		this.mapParticipanteListaRight = mapParticipanteListaRight;
	}

	public List<Integer> getLstListaRight() {
		return lstListaRight;
	}

	public void setLstListaRight(List<Integer> lstListaRight) {
		this.lstListaRight = lstListaRight;
	}

	public Map<Integer, String> getMapListaLeft() {
		return mapListaLeft;
	}

	public void setMapListaLeft(Map<Integer, String> mapListaLeft) {
		this.mapListaLeft = mapListaLeft;
	}

	public Map<Integer, String> getMapListaRight() {
		return mapListaRight;
	}

	public void setMapListaRight(Map<Integer, String> mapListaRight) {
		this.mapListaRight = mapListaRight;
	}

	public Integer getiIdReemplazo() {
		return iIdReemplazo;
	}

	public void setiIdReemplazo(Integer iIdReemplazo) {
		this.iIdReemplazo = iIdReemplazo;
	}

	public ReemplazoService getSrvReemplazo() {
		return srvReemplazo;
	}

	public void setSrvReemplazo(ReemplazoService srvReemplazo) {
		this.srvReemplazo = srvReemplazo;
	}

	public Reemplazo getObjReemplazo() {
		return objReemplazo;
	}

	public void setObjReemplazo(Reemplazo objReemplazo) {
		this.objReemplazo = objReemplazo;
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

	public EtapaService getEtapaService() {
		return etapaService;
	}

	public void setEtapaService(EtapaService etapaService) {
		this.etapaService = etapaService;
	}

	public EstadoDAO getEstadoDAO() {
		return estadoDAO;
	}

	public void setEstadoDAO(EstadoDAO estadoDAO) {
		this.estadoDAO = estadoDAO;
	}

	public TrazabilidaddocumentoService getTrazabilidaddocumentoService() {
		return trazabilidaddocumentoService;
	}

	public void setTrazabilidaddocumentoService(TrazabilidaddocumentoService trazabilidaddocumentoService) {
		this.trazabilidaddocumentoService = trazabilidaddocumentoService;
	}

	public void setSeguridadService(SeguridadService seguridadService) {
		this.seguridadService = seguridadService;
	}

	public void setIdSala(String idSala){
		try{
			this.idSala = Integer.parseInt(idSala);
		}catch(NumberFormatException e){
			this.idSala = 0;
		}
	}

	public Boolean getbEsJefe() {
		return bEsJefe;
	}

	public void setbEsJefe(Boolean bEsJefe) {
		this.bEsJefe = bEsJefe;
	}


}
