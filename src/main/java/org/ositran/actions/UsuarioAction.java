/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.actions;
   
import com.btg.ositran.siged.domain.Alerta;
import com.btg.ositran.siged.domain.ArchivoPendiente;
import com.btg.ositran.siged.domain.CarpetaBusqueda;
import com.btg.ositran.siged.domain.Cliente;
import com.btg.ositran.siged.domain.Departamento;
import com.btg.ositran.siged.domain.Distrito;   
import com.btg.ositran.siged.domain.Documento;
import com.btg.ositran.siged.domain.Documentoenviado;
import com.btg.ositran.siged.domain.Expediente;
import com.btg.ositran.siged.domain.Grupoproceso;  
import com.btg.ositran.siged.domain.Lista;
import com.btg.ositran.siged.domain.LogOperacion;
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
import com.btg.ositran.siged.domain.Sede;
import com.btg.ositran.siged.domain.Tipodocumento;
import com.btg.ositran.siged.domain.Unidad;
import com.btg.ositran.siged.domain.Usuario;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import javax.servlet.http.Cookie;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.alfresco.webservice.util.AuthenticationUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import com.btg.ositran.siged.domain.Usuarioxunidadxfuncion;
import com.ositran.pide.WSPideTramite;
import com.ositran.ws.ConsultaTramite;
import com.ositran.ws.RespuestaConsultaTramite;
import com.sun.jndi.ldap.LdapCtxFactory;
import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.directory.DirContext;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.struts2.interceptor.SessionAware;
import org.ositran.cookies.CookieBean;
import org.ositran.cookies.CookieProvider;
import org.ositran.daos.UsuarioxunidadxfuncionDAO;
import org.ositran.services.ArchivopendienteService;
import org.ositran.services.CarpetabusquedaService;
import org.ositran.services.ClienteService;
import org.ositran.services.DepartamentoService;
import org.ositran.services.DistritoService;
import org.ositran.services.DocumentoEnviadoService;
import org.ositran.services.DocumentoService;
import org.ositran.services.ExpedienteService;
import org.ositran.services.GrupoprocesoService;
import org.ositran.services.ListaService;
import org.ositran.services.LogOperacionService;
import org.ositran.services.ModuloService;
import org.ositran.services.NotificacionService;
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
import org.ositran.services.SeguimientoService;
import org.ositran.services.TipodocumentoService;
import org.ositran.services.UnidadService;
import org.ositran.services.UsuarioService;
import org.ositran.utils.Constantes;
import org.ositran.utils.DocumentoList;

public class UsuarioAction implements SessionAware,CookieProvider{
    
        private static DirContext ldapContext;
        private String domainName = "ositranorg.gob.pe";
        private String serverName = "osidc02";
        private String principalName = null;
        private Hashtable<String, String> ldapEnv = new Hashtable<String, String>(11);
	private static Log log=LogFactory.getLog(UsuarioAction.class);
	private ArchivopendienteService srvArchivoPendiente;
	private ClienteService srvCliente;
	private DocumentoService srvDocumento;
	private ExpedienteService srvExpediente;
	private List<Cliente> lstCliente;
	private List<Documento> lstDocumento;
	private List<DocumentoList> lstDL;
	private List<ArchivoPendiente> archivoPendienteList;
	private List<Documentoenviado> documentoEnviadoList;
	private List<Modulo> lstModulo;
	private List<Perfil> lstPerfil;
	private List<Plantilla> lstPlantilla;
	private List<Proceso> lstPL;
	private List<Recurso> lstRecurso;
	private List<Rol> lstRol;
	private List<Sede> lstSede;
	private List<Tipodocumento> lstTipodocumento;
	private List<Unidad> lstUnidad;
	private List<Usuario> lstUL;
	private List<Expediente> lstExpediente;
	private Map<String,Object> mapSession;
	private ModuloService srvModulo;
	private PerfilService srvPerfil;
	private PlantillaService srvPlantilla;
	private ProcesoService srvProceso;
	private RecursoService srvRecurso;
	private RolService srvRol;
	private SedeService srvSede;
	private TipodocumentoService srvTipodocumento;
	private SeguimientoService seguimientoService;
	private UnidadService srvUnidad;
	private DocumentoEnviadoService documentoEnviadoService;
	private NotificacionService srvNotificacion;
	private int nroNotificacionesNL;
	private int nroDocDigitalizados;
	private LogOperacionService logOperacionService;
	private List<LogOperacion> lstLogOperacion;
        private UsuarioxunidadxfuncionDAO usuarioxunidadxfuncionDAO;
        
        public UsuarioxunidadxfuncionDAO getUsuarioxunidadxfuncionDAO() {
            return usuarioxunidadxfuncionDAO;
        }

        public void setUsuarioxunidadxfuncionDAO(UsuarioxunidadxfuncionDAO usuarioxunidadxfuncionDAO) {
            this.usuarioxunidadxfuncionDAO = usuarioxunidadxfuncionDAO;
        }

	public LogOperacionService getLogOperacionService() {
		return logOperacionService;
	}

	public void setLogOperacionService(LogOperacionService logOperacionService) {
		this.logOperacionService = logOperacionService;
	}

	public List<LogOperacion> getLstLogOperacion() {
		return lstLogOperacion;
	}

	public void setLstLogOperacion(List<LogOperacion> lstLogOperacion) {
		this.lstLogOperacion = lstLogOperacion;
	}

	private Integer parametroRol;

	public int getNroDocDigitalizados(){
		return nroDocDigitalizados;
	}

	public void setNroDocDigitalizados(int nroDocDigitalizados){
		this.nroDocDigitalizados=nroDocDigitalizados;
	}

	private int idCarpeta;
	private String sForwardToURL;
	private String sTema;
	private List<Grupoproceso> lstGrupoProceso;
	private GrupoprocesoService srvGrupoProceso;
	private List<Numeracion> lstNumeracion;
	private NumeracionService srvNumeracion;
	private List<Parametro> lstParametro;
	private ParametroService srvParametro;
	private DepartamentoService srvDepartamento;
	private List<Departamento> lstDepartamento;
	private ProvinciaService srvProvincia;
	private List<Provincia> lstProvincia;
	private DistritoService srvDistrito;
	private List<Distrito> lstDistrito;
	private List<Lista> lstLista;
	private ListaService srvLista;
	private List<Reemplazo> lstReemplazo;
	private ReemplazoService srvReemplazo;

	public DocumentoEnviadoService getDocumentoEnviadoService(){
		return documentoEnviadoService;
	}

	public void setDocumentoEnviadoService(DocumentoEnviadoService documentoEnviadoService){
		this.documentoEnviadoService=documentoEnviadoService;
	}

	public NotificacionService getSrvNotificacion(){
		return srvNotificacion;
	}

	public void setSrvNotificacion(NotificacionService srvNotificacion){
		this.srvNotificacion=srvNotificacion;
	}

	public int getNroNotificacionesNL(){
		return nroNotificacionesNL;
	}

	public void setNroNotificacionesNL(int nroNotificacionesNL){
		this.nroNotificacionesNL=nroNotificacionesNL;
	}



	private String sClave;
	private String sOpcion;
	private String sTipoFrame;
	private String sTipoMantenimiento;
	private String sUsuario;
	private UsuarioService srvUsuario;
	private List<CarpetaBusqueda> lisbc;
	private CarpetabusquedaService svrCb;

	// ////////////////////////////////
	// Constructors //
	// ////////////////////////////////
	public UsuarioAction(UsuarioService srvU){
		setSrvUsuario(srvU);
	}

	public String blank(){
		return "blank";
	}
        
        public String remover(){
            getMapSession().remove(Constantes.SESSION_ALFRESCO);
	    getMapSession().remove(Constantes.SESSION_AUDITABLE);
	    getMapSession().remove(Constantes.SESSION_FORWARD_TO_URL);
	    getMapSession().remove(Constantes.SESSION_IDDOCUMENTO);
	    getMapSession().remove(Constantes.SESSION_RECURSO);
	    getMapSession().remove(Constantes.SESSION_UPLOAD_LIST);
	    getMapSession().remove(Constantes.SESSION_USUARIO);
	   // ((org.apache.struts2.dispatcher.SessionMap) getMapSession()).invalidate();
            
            return "";                    
        }
        
        @SuppressWarnings("unused")
	public String loginSession(){
            return Action.SUCCESS;
        }
        
        public boolean validaUsuarioAD(String strUser, String strPassword) {
            
            strUser = strUser.trim().toUpperCase();
            
           /* if(!strUser.matches("[a-zA-Z0-9]+(_|.)?[a-zA-Z0-9]+ ")){
                 return false;
            }*/
            principalName = strUser + "@" + domainName;

            try {
                  if(strPassword == null || strPassword.equals(""))
                        return false;
                  ldapEnv.put(Context.SECURITY_PRINCIPAL, principalName);
                  ldapEnv.put(Context.SECURITY_CREDENTIALS, strPassword);
                  ldapContext = LdapCtxFactory.getLdapCtxInstance("ldap://"
                              + serverName + "." + domainName + '/', ldapEnv);

                  System.out.println("Authentication succeeded!" + strUser);
                  return true;
            } catch (Exception e) {
                  System.out.println("Authentication failed!");
                  System.out.println(" Search error: " + e.toString());
                  return false;
            }
      }


	@SuppressWarnings("unused")
	public String login(){
		log.debug("-> [Action] UsuarioAction - login():String ");

		try{
        Usuario objUsuario=null;

        HttpServletRequest request = ServletActionContext.getRequest();
        mapSession=ActionContext.getContext().getSession();
        
        
        if (mapSession.get(Constantes.SESSION_USUARIO)!=null){
          return "activa";
        }
        
        String captchaInput = request.getParameter("codecp");
        String captcha = (String) mapSession.get("captcha");
        String storage  = request.getParameter("storage");
                
                /*if(captchaInput.equals(captcha)){

		}else{
			return Action.ERROR;
		}*/
                  
          //clave siged
                
        if (!getSrvUsuario().findValidarUsuario(getSUsuario(), getSClave())){
			log.error("No se encontro usuario [" + getSUsuario() + "]");
			return Action.ERROR;
		}
                
            /*
            if (!validaUsuarioAD(getSUsuario(),getSClave())){
               log.error("No se encontro usuario [" + getSUsuario() + "]");
	   return Action.ERROR; 
            }*/
                
                
        if((objUsuario=getSrvUsuario().findByUsuarioClave(getSUsuario(),getSClave())) == null){
            log.error("No se encontro usuario [" + getSUsuario() + "]");
		    return Action.ERROR;
		}
 
        Date hoy=Calendar.getInstance().getTime();
                
		mapSession.put("idusuario",objUsuario.getIdusuario());
		mapSession.put("usuario",objUsuario.getUsuario());
		mapSession.put("clave",objUsuario.getClave());
                
        mapSession.put("context",hoy);
		mapSession.put("nombres",objUsuario.getNombres() + "-" + objUsuario.getApellidos());
               
        mapSession.put(Constantes.SESSION_USUARIO,objUsuario);
        mapSession.put("storage", storage);
                
		String nombrePC = "";

        try {
                InetAddress inetAddress = InetAddress.getByName(request.getRemoteAddr());
                log.debug("IP origen: " + inetAddress);
                nombrePC = inetAddress.getHostName();
                log.debug("Nombre origen: " + nombrePC);
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

        mapSession.put("nombrePC", nombrePC);
        Rol roles= objUsuario.getRolUsuario();
        
        objUsuario.setIdUnidadPerfil(new Integer(objUsuario.getUnidad().getIdunidad()));
        objUsuario.setIdFuncionPerfil(new Integer(objUsuario.getIdfuncion()));
        objUsuario.setIdUsuarioPerfil(new Integer(objUsuario.getIdusuario()));
      
        if (objUsuario.getIdfuncion().toString().equals(Constantes.COD_CARGO_SECRETARIA.toString())){
            List<Usuarioxunidadxfuncion> list = usuarioxunidadxfuncionDAO.getUsuarioByUnidadByFuncionDelegado(objUsuario);
            if (list!=null){
               objUsuario.setIdUnidadPerfil(list.get(0).getIdunidad());
               objUsuario.setIdFuncionPerfil(list.get(0).getIdfuncion());
               objUsuario.setIdUsuarioPerfil(list.get(0).getIdusuariocargo());   
               roles= srvRol.findByIdRol(list.get(0).getIdrol());
            }else{
               objUsuario.setIdUnidadPerfil(new Integer(objUsuario.getUnidad().getIdunidad()));
               objUsuario.setIdFuncionPerfil(new Integer(objUsuario.getIdfuncion()));
               objUsuario.setIdUsuarioPerfil(new Integer(objUsuario.getIdusuario()));
            }
        }    
        else{
            objUsuario.setIdUnidadPerfil(new Integer(objUsuario.getUnidad().getIdunidad()));
            objUsuario.setIdFuncionPerfil(new Integer(objUsuario.getIdfuncion()));
            objUsuario.setIdUsuarioPerfil(new Integer(objUsuario.getIdusuario()));
        } 
        
        objUsuario.setIdRolPerfil(roles.getIdrol());
        mapSession.put(Constantes.SESSION_ROLCARGO, roles.getIdrol().toString());
        Map<String,Integer> recursos;
        
        List<Perfil> perfiles=new ArrayList<Perfil>();
        perfiles.add(roles.getIdperfil());
        log.debug("IdPerfil:"+roles.getIdperfil());
        
        recursos=srvPerfil.getRecursosPorPerfiles(perfiles);
        log.debug("Recurso por perfil:"+recursos == null?0:recursos.size());
        mapSession.put(Constantes.SESSION_RECURSO,recursos);
        String sURL=(String) mapSession.get(Constantes.SESSION_FORWARD_TO_URL);

        List<Parametro> lst =srvParametro.findByTipo("PERMISO_CARGO");
        objUsuario.setPermisoCargo('0');
        if (lst!=null && lst.size()>0){
            for(int i=0;i<lst.size();i++){
                if (objUsuario.getIdusuario().toString().equals(lst.get(i).getValor())){
                    objUsuario.setPermisoCargo('1');
                    break;
                }
            }
        }
                
		if(sURL != null){
            mapSession.remove(Constantes.SESSION_FORWARD_TO_URL);
	        sForwardToURL=sURL;
		    return "forwardToURL";
		}
		
		} catch(Exception e){
            e.printStackTrace();
        }
                
                return Action.SUCCESS;
	}
        
        public String prueba(){
            return Action.LOGIN;
        }

	public String buscarLogOperacion() throws Exception{
		try{
			String nroDocumento = ServletActionContext.getRequest().getParameter("numeroDocumento");
			String tipoDocumento = ServletActionContext.getRequest().getParameter("idTipoDocumento_");
			String usuario = ServletActionContext.getRequest().getParameter("idUsuario_");

			lstLogOperacion = logOperacionService.findLogOperacion(usuario, tipoDocumento, nroDocumento);

			return Action.SUCCESS;
		}
		catch(Exception e){
			log.error(e.getMessage(),e);
			return Action.ERROR;
		}
	}

	@SuppressWarnings("rawtypes")
	public String logout(){
		setMapSession(ActionContext.getContext().getSession());
		Usuario usuario=(Usuario) getMapSession().get(Constantes.SESSION_USUARIO);
                
                if (true){//AuthenticationUtils.getTicket() != null) {
                	try {
                	     AuthenticationUtils.endSession();
                   	} catch (Exception ex) {
                             log.info("Error al terminar session de Alfresco. " + AuthenticationUtils.getTicket());
			}
		}

		if(getMapSession() instanceof org.apache.struts2.dispatcher.SessionMap){
			try{
			      if(usuario != null){
			        Format f1=new SimpleDateFormat("dd/MM/yyyy");
				Format f2=new SimpleDateFormat("HH:mm");
				Date ahora=Calendar.getInstance().getTime();
				usuario=null;
			      }
                              
			      getMapSession().remove(Constantes.SESSION_ALFRESCO);
			      getMapSession().remove(Constantes.SESSION_AUDITABLE);
			      getMapSession().remove(Constantes.SESSION_FORWARD_TO_URL);
			      getMapSession().remove(Constantes.SESSION_IDDOCUMENTO);
			      getMapSession().remove(Constantes.SESSION_RECURSO);
			      getMapSession().remove(Constantes.SESSION_UPLOAD_LIST);
			      getMapSession().remove(Constantes.SESSION_USUARIO);
			      ((org.apache.struts2.dispatcher.SessionMap) getMapSession()).invalidate();
			}
			catch(IllegalStateException e){
			   log.error(e.getMessage(),e);
			}
		}

		return Action.SUCCESS;
	}

	public List<CookieBean> getCookies(){
		List<CookieBean> cookies=new ArrayList<CookieBean>();
		CookieBean cookie=new CookieBean();
		cookie.setCookieName("csd");
		cookie.setCookieValue("1");
		// cookie.setPath("/firstapp");
		cookies.add(cookie);
		return cookies;
	}

	@SuppressWarnings("unchecked")
	public String runBody() throws Exception{
                System.out.println("******************************RUN BODY********************************");
		try{
			log.debug("En runBody");
			setMapSession(ActionContext.getContext().getSession());
			Usuario objUsuario=(Usuario) getMapSession().get(Constantes.SESSION_USUARIO);
			Map<String,Integer> mapRecurso=new HashMap<String,Integer>();
			String sOpcionMenu=(String) getMapSession().get("opcionmenu");
			mapRecurso=(Map<String,Integer>) getMapSession().get(Constantes.SESSION_RECURSO);

			log.debug("Opcion de Menu [" + sOpcionMenu + "]");

			if(mapRecurso.get("MPGrd") == 1 || mapRecurso.get("QASGrd") == 1){
				getMapSession().put("columnas","\"id\",\"Remitente\",\"Asunto\",\"Fecha\"");
				getMapSession().put("nrocolumnas",3);
				List<String> lstCol=new ArrayList<String>();

				lstCol.add("Remitente");
				lstCol.add("Asunto");
				lstCol.add("Fecha");
				getMapSession().put("lstCol",lstCol);
			}
			else if(mapRecurso.get("UsuFinGrd") == 1){
				getMapSession().put("columnas","\"id\",'Sel.',\"<img src='images/bolita.gif' border='0'/>\",\"<img src='images/alta.bmp' border='0'/>\",\"Remitente\",\"Asunto\",\"Concesionario\",\"Documento\",\"Fecha\",\"<img src='images/clic.gif'/>\"");
				getMapSession().put("nrocolumnas",5);
			}
			else if(mapRecurso.get("UsuSasGrd") == 1){
				getMapSession().put("columnas","\"id\",'Sel.',\"<img src='images/bolita.gif' border='0'/>\",\"<img src='images/alta.bmp' border='0'/>\",\"Remitente\",\"Asunto\",\"Accion\",\"Concesionario\",\"Documento\",\"Fecha Envio\",\"<img src='images/clic.gif'/>\"");
				getMapSession().put("nrocolumnas",5);
			}
			else if(mapRecurso.get("DigGrd") == 1){
				getMapSession().put("columnas","\"id\",\"Remitente\",\"Documento\",\"Asunto\",\"Fecha\"");
				getMapSession().put("nrocolumnas",5);
			}

			if(mapRecurso.get("msggrid") == 1){
				// setLstModulo(getSrvModulo().findAll());
				// getMapSession().put("columnas", "\"id\",\"Nombre\"");
				// getMapSession().put("nrocolumnas", 2);

				return "mensajeria";
			}
			if(mapRecurso.get("MantGrdMod") == 1 &&  sOpcionMenu!=null && sOpcionMenu.equals("MantMnuMod")){
				setLstModulo(getSrvModulo().findModulosActivos());
				getMapSession().put("columnas","\"id\",\"Nombre\"");
				getMapSession().put("nrocolumnas",2);

				return "MantMnuMod";
			}
			else if(mapRecurso.get("MantGrdPer") == 1 &&  sOpcionMenu!=null &&  sOpcionMenu.equals("MantMnuPer")){
				setLstPerfil(getSrvPerfil().findAll());
				getMapSession().put("columnas","\"id\",\"Nombre\"");
				getMapSession().put("nrocolumnas",2);

				return "MantMnuPer";
			}
			else if(mapRecurso.get("MantGrdRec") == 1 &&  sOpcionMenu!=null &&  sOpcionMenu.equals("MantMnuRec")){
				setLstRecurso(getSrvRecurso().buscarRecursosActivos());
				getMapSession().put("columnas","\"id\",\"Nombre\",\"Codigo\",\"Action URL\"");
				getMapSession().put("nrocolumnas",4);

				return "MantMnuRec";
			}
			else if(mapRecurso.get("MantGrdUsu") == 1 &&  sOpcionMenu!=null &&  sOpcionMenu.equals("MantMnuUsu")){
                            	setLstUL(getSrvUsuario().getUsuarioList(null));
				getMapSession().put("columnas","\"id\",\"Nombres\",\"Apellidos\",\"Usuario\"");
				getMapSession().put("nrocolumnas",4);
                            	return "MantMnuUsu";
			}
			else if(mapRecurso.get("MantGrdUsu") == 1 &&  sOpcionMenu!=null &&  sOpcionMenu.equals("MantMnuUsuComp")){
				setLstUL(getSrvUsuario().getUsuarioList(null));
				getMapSession().put("columnas","\"id\",\"Nombres\",\"Apellidos\",\"Usuario\"");
				getMapSession().put("nrocolumnas",4);

				return "MantMnuUsuComp";
			}

			else if(mapRecurso.get("MantGrdLogDoc") == 1 &&  sOpcionMenu!=null &&  sOpcionMenu.equals("MantMnuLogDoc")){
				lstLogOperacion = null;
				getMapSession().put("columnas","\"id\",\"Documento\",\"Usuario\",\"Fecha\"");
                                getMapSession().put("nrocolumnas",4);

				return "MantMnuLogDoc";
			}


			else if(mapRecurso.get("MantGrdPro") == 1 &&  sOpcionMenu!=null &&  sOpcionMenu.equals("MantMnuPro")){
				setLstPL(getSrvProceso().getProcesosActivos());
				getMapSession().put("columnas","\"id\",\"Nombre\",\"Tiempo Atencion\",\"Responsable\"");
				getMapSession().put("nrocolumnas",4);

				return "MantMnuPro";
			}
			else if(mapRecurso.get("MantGrdExp") == 1 &&  sOpcionMenu!=null &&  sOpcionMenu.equals("MantMnuExp")){
				setLstExpediente(getSrvExpediente().findAll());
				getMapSession().put("columnas","\"id\",\"Nro Expediente\",\"Proceso\",\"Cliente\",\"Fecha Creacion\"");
				getMapSession().put("nrocolumnas",5);

				return "MantMnuExp";
			}
			else if(mapRecurso.get("MantGrdUni") == 1 &&  sOpcionMenu!=null &&  sOpcionMenu.equals("MantMnuUni")){
				lstUnidad=srvUnidad.obtenerLstTodo();
				getMapSession().put("columnas","\"id\",\"Nombre\",\"Dependencia\",\"Sede\"");
				getMapSession().put("nrocolumnas",4);

				return "MantMnuUni";
			}
			else if(mapRecurso.get("MantGrdRol") == 1 &&  sOpcionMenu!=null &&  sOpcionMenu.equals("MantMnuRol")){
				lstRol=srvRol.obtenerTodo();
				getMapSession().put("columnas","\"id\",\"Nombre\",\"Perfil\",\"Descripcion\"");
				getMapSession().put("nrocolumnas",4);

				return "MantMnuRol";
			}
			else if(mapRecurso.get("MantGrdSed") == 1 &&  sOpcionMenu!=null &&  sOpcionMenu.equals("MantMnuSed")){
				lstSede=srvSede.obtenerTodo();
				getMapSession().put("columnas","\"id\",\"Nombre\"");
				getMapSession().put("nrocolumnas",2);

				return "MantMnuSed";
			}
			else if(mapRecurso.get("MantGrdTdo") == 1 &&  sOpcionMenu!=null &&  sOpcionMenu.equals("MantMnuTdo")){
				lstTipodocumento=srvTipodocumento.findAll(null);
				getMapSession().put("columnas","\"id\",\"Nombre\"");
				getMapSession().put("nrocolumnas",2);

				return "MantMnuTdo";
			}
			else if(mapRecurso.get("MantGrdCli") != null && mapRecurso.get("MantGrdCli") == 1 &&  sOpcionMenu!=null &&  sOpcionMenu.equals("MantMnuCli")){
				lstCliente=srvCliente.findAll();
				getMapSession().put("columnas","\"id\",\"Nombre - Razon Social\",\"Tipo de Identificacion\"");
				getMapSession().put("nrocolumnas",3);

				return "MantMnuCli";
			}
			else if(mapRecurso.get("MantGrdPlt") != null && mapRecurso.get("MantGrdPlt") == 1 &&  sOpcionMenu!=null &&  sOpcionMenu.equals("MantMnuPlt")){
				lstPlantilla=srvPlantilla.findAll();
				getMapSession().put("columnas","\"id\",\"Nombre\",\"Fecha de Creacion\"");
				getMapSession().put("nrocolumnas",3);

				return "MantMnuPlt";
			}
			else if(mapRecurso.get("MantGrdGPr") != null && mapRecurso.get("MantGrdGPr") == 1 &&  sOpcionMenu!=null &&  sOpcionMenu.equals("MantMnuGPr")){
				lstGrupoProceso=srvGrupoProceso.findAll();
				getMapSession().put("columnas","\"id\",\"Nombre\",\"Fecha de Creacion\"");
				getMapSession().put("nrocolumnas",3);

				return "MantMnuGPr";
			}
			else if(mapRecurso.get("MantGrdNum") != null && mapRecurso.get("MantGrdNum") == 1 &&  sOpcionMenu!=null &&  sOpcionMenu.equals("MantMnuNum")){
				lstNumeracion=srvNumeracion.findAll();
				getMapSession().put("columnas","\"idunidad\",\"idtipodocumento\",\"Area\",\"Tipo de Documento\"");
				getMapSession().put("nrocolumnas",4);

				return "MantMnuNum";
			}
			else if(mapRecurso.get("MantGrdPar") != null && mapRecurso.get("MantGrdPar") == 1 &&  sOpcionMenu!=null &&  sOpcionMenu.equals("MantMnuPar")){
				lstParametro=srvParametro.findAll();
				getMapSession().put("columnas","\"idparametro\",\"Tipo\",\"Valor\"");
				getMapSession().put("nrocolumnas",3);

				return "MantMnuPar";
			}
			else if(mapRecurso.get("MantGrdDep") != null && mapRecurso.get("MantGrdDep") == 1 &&  sOpcionMenu!=null &&  sOpcionMenu.equals("MantMnuDep")){
				lstDepartamento=srvDepartamento.findAll();
				getMapSession().put("columnas","\"iddepartamento\",\"Nombre\"");
				getMapSession().put("nrocolumnas",2);

				return "MantMnuDep";
			}
			else if(mapRecurso.get("MantGrdProv") != null && mapRecurso.get("MantGrdProv") == 1 &&  sOpcionMenu!=null &&  sOpcionMenu.equals("MantMnuProv")){
				lstProvincia=srvProvincia.findAll();
				getMapSession().put("columnas","\"idprovincia\",\"Nombre\"");
				getMapSession().put("nrocolumnas",2);

				return "MantMnuProv";
			}
			else if(mapRecurso.get("MantGrdDis") != null && mapRecurso.get("MantGrdDis") == 1 &&  sOpcionMenu!=null &&  sOpcionMenu.equals("MantMnuDis")){
				lstDistrito=srvDistrito.findAll();
				getMapSession().put("columnas","\"iddistrito\",\"Nombre\"");
				getMapSession().put("nrocolumnas",2);

				return "MantMnuDis";
			}
			else if(mapRecurso.get("MantGrdLis") != null && mapRecurso.get("MantGrdLis") == 1 &&  sOpcionMenu!=null &&  sOpcionMenu.equals("MantMnuLis")){
				lstLista=srvLista.findLstBy(Constantes.ESTADO_ACTIVO);
				mapSession.put("columnas","\"idlista\",\"Nombre\"");
				mapSession.put("nrocolumnas",2);

				return "MantMnuLis";
			}
			else if(mapRecurso.get("MantGrdRee") != null && mapRecurso.get("MantGrdRee") == 1 &&  sOpcionMenu!=null &&  sOpcionMenu.equals("MantMnuRee")){
				lstReemplazo=srvReemplazo.findLstBy(Constantes.ESTADO_ACTIVO);
				mapSession.put("columnas","\"idreemplazo\",\"Reemplazado\",\"Reemplazante\",\"Proceso\"");
				mapSession.put("nrocolumnas",4);

				return "MantMnuRee";
			}
			else if(sOpcionMenu!=null && sOpcionMenu.equals("MPMnuDocReg")){
				return "mp";
			}
			else if(sOpcionMenu!=null && sOpcionMenu.equals("DigMnuDocIng")){
				return "dig";
			}
			else if(sOpcionMenu!=null && sOpcionMenu.equals("QASMnuDocDig")){
				return "qas";
			}
			else if(sOpcionMenu!=null && sOpcionMenu.equals("UsuFinMnuDocRec")){
				return "user";
			}
			else if(mapRecurso.get("MantMnuJer") == 1 &&  sOpcionMenu!=null &&  sOpcionMenu.equals("MantMnuJer")){
				return "MantMnuJer";
			}
			else if(mapRecurso.get("MantMnuFer") == 1 &&  sOpcionMenu!=null &&  sOpcionMenu.equals("MantMnuFerIng")){
				return "MantMnuFerIng";
			}
			else if(mapRecurso.get("MantMnuFer") == 1 &&  sOpcionMenu!=null &&  sOpcionMenu.equals("MantMnuFerLis")){
				return "MantMnuFerLis";
			}
			else if(sOpcionMenu!=null && sOpcionMenu.equals("UsuFinMnuMisPro")){
				setLstPL(getSrvProceso().findByIdResponsableOrAsistente(objUsuario.getIdusuario(),Constantes.ESTADO_ACTIVO));
				getMapSession().put("columnas","\"id\",\"Nombre\",\"Tiempo Atencion\",\"Fecha Creacion\"");
				getMapSession().put("nrocolumnas",4);

				return "misprocesos";
			}
			else if(sOpcionMenu!=null && sOpcionMenu.equals("UsuFinMnuMisExp")){
				// buscar los expedientes del que el usuario es reponsable del
				// proceso o el expdiente :O
				lstDocumento=srvDocumento.buscarLstDocumentoPor(Constantes.DOCUMENTO_PRINCIPAL,objUsuario.getIdusuario());
				log.debug(" data size:" + lstDocumento.size());
				getMapSession().put("columnas","\"id\",\"Nro Expediente\",\"Proceso\",\"Cliente\",\"Fecha Creacion\"");
				getMapSession().put("nrocolumnas",5);

				return "UsuFinMnuMisExp";
			}
			else if(sOpcionMenu!=null && sOpcionMenu.equals("UsuFinMnuSupervisor")){
				// buscar los expedientes del que el usuario es reponsable del
				// proceso o el expdiente :O
				// lstDocumento = srvDocumento.buscarLstDocumentoPor(
				// Constantes.DOCUMENTO_PRINCIPAL, objUsuario.getIdusuario());
				log.debug("va hacer algo supervisor 	que cochinada esto ");
				getMapSession().put("columnas","\"id\",\"Nro Expediente\",\"Proceso\",\"Cliente\",\"Fecha Creacion\"");
				getMapSession().put("nrocolumnas",5);

				return "UsuFinMnuSupervisor";
			}

			if(mapRecurso.get("MantMnuAud") == 1 ){
				if(Constantes.ROL_ADMINISTRADOR_SUPERVISOR.equals(objUsuario.getRol().getNombre())){
					setSTipoFrame("super");
					return "inicionormal";
				}
				else if(sOpcionMenu!=null &&  sOpcionMenu.equals("MantMnuAud")){
					return "MantMnuAud";
				}
			}
			
			if(mapRecurso.get("MantMnuRep") == 1 && (Constantes.ROL_ADMINISTRADOR_SUPERVISOR.equals(objUsuario.getRol().getNombre()) ||   (sOpcionMenu!=null && sOpcionMenu.equals("MantMnuRep")) )){
				return "MantMnuRep";
			}else{
				setLstDL(getSrvDocumento().getDocuments(objUsuario,Constantes.ESTADO_ACTIVO));
				return Action.SUCCESS;
			}
		}
		catch(Exception e){
			log.error(e.getMessage(),e);
			
			e.printStackTrace();

			return Action.ERROR;
		}
	}



	@SuppressWarnings("unchecked")
	public String runInicio() /* throws Exception */{
		 try {
            System.out.println("*********************************RUN INICIO***********************************");
			setMapSession(ActionContext.getContext().getSession());
			Usuario usuario=(Usuario) getMapSession().get(Constantes.SESSION_USUARIO);
           
            if(usuario != null){
			Map<String,Integer> mapRecurso=(Map<String,Integer>) getMapSession().get(Constantes.SESSION_RECURSO);
			mapSession=ActionContext.getContext().getSession();
			mapSession.put("showDescargar", "0");
		
            Rol rol=usuario.getRol();
            if(rol != null && rol.getNombre().equals(Constantes.ROL_ADMINISTRADOR)){
                log.debug("Tipo de mantenimiento a pintar [" + getSTipoMantenimiento() + "]");

				if(getSTipoMantenimiento() != null){
                  setSOpcion(getSTipoMantenimiento());
				}
				else{
                  setSOpcion("MantMnuUsu");
				}
            }else{
            	log.debug("mapRecurso:"+mapRecurso);
                if(getSOpcion() == null){
                  if (rol == null){
                	 log.debug("UsuFinGrd:"+mapRecurso.get("UsuFinGrd"));
                    if((Integer) mapRecurso.get("UsuFinGrd") == 1){
                       setSOpcion("UsuFinMnuDocRec");
					}
                  }
                }
            }

			log.debug("Opcion de Menu a pintar [" + getSOpcion() + "]");
			getMapSession().put("opcionmenu",getSOpcion());
			log.debug("Tipo de Frame a cargar [" + getSTipoFrame() + "]");

			if(rol == null || rol.getNombre().startsWith(Constantes.ROL_MESA_PARTES)
					|| rol.getNombre().startsWith(Constantes.ROL_QAS)
					|| rol.getNombre().startsWith(Constantes.ROL_DIGITALIZADOR)
					|| rol.getNombre().startsWith(Constantes.ROL_MENSAJERIA)){
                                return Action.SUCCESS;
			}
			else{
				if(Constantes.ROL_ADMINISTRADOR_SUPERVISOR.equals(usuario.getRol().getNombre()) && getSTipoFrame().equals("grid")){
					setSTipoFrame("super");
				}

                return "inicionormal";
			}
		}
	
		}catch(Exception e){
			 e.printStackTrace();
		}
		 
        log.error("Se intento acceder al sistema, pero no hay ningun usuario en sesion");
		
        return Action.ERROR;
		
	}



	@SuppressWarnings("unchecked")
	public String runInicioVG() /* throws Exception */{
		// try {
                System.out.println("*****************************RUN INICIO VG****************************");
		getMapSession().put("readVisor", "VG");
		setMapSession(ActionContext.getContext().getSession());
		Usuario usuario=(Usuario) getMapSession().get(Constantes.SESSION_USUARIO);

		if(usuario != null){

			Map<String,Integer> mapRecurso=(Map<String,Integer>) getMapSession().get(Constantes.SESSION_RECURSO);

			List<Alerta> listAlert = seguimientoService.getListaAlerta(usuario.getIdusuario(), getSrvUsuario().findByIdUsuario(usuario.getIdusuario()).getUnidad().getIdunidad(), null);
			getMapSession().put("listAlert",listAlert);

			mapSession=ActionContext.getContext().getSession();
			List<Parametro> listPar = srvParametro.findByTipo("DESCARGAR_PENDIENTES");
			Integer unidad = getSrvUsuario().findByIdUsuario(usuario.getIdusuario()).getUnidad().getIdunidad();
			mapSession.put("showDescargar", "0");
			for(int i=0;i<listPar.size();i++){
			  if (listPar.get(i).getValor().equals(unidad.toString()))
				  mapSession.put("showDescargar", "1");
			}

			// log.debug("Usuario con rol [" + objUsuario.getRol().getNombre() +
			// "] Perfil [" + objUsuario.getRol().getIdperfil().getNombre() +
			// "]");
			Rol rol=usuario.getRol();
			if(rol != null && rol.getNombre().equals(Constantes.ROL_ADMINISTRADOR)){
				log.debug("Tipo de mantenimiento a pintar [" + getSTipoMantenimiento() + "]");

				if(getSTipoMantenimiento() != null){
					setSOpcion(getSTipoMantenimiento());
				}
				else{
					setSOpcion("MantMnuUsu");
				}
			}
			else{
				if(getSOpcion() == null){
					if(rol != null){
						if((Integer) mapRecurso.get("MPGrd") == 1){
							setSOpcion("MPMnuDocReg");
						}
						else if((Integer) mapRecurso.get("DigGrd") == 1){
							setSOpcion("DigMnuDocIng");
						}
						else if((Integer) mapRecurso.get("QASGrd") == 1){
							setSOpcion("QASMnuDocDig");
						}
						else if((Integer) mapRecurso.get("MensGridRec") == 1){
							setSOpcion("MensMnuDocRec");
						}
						else if((Integer) mapRecurso.get("MensGridCer") == 1){
							setSOpcion("MensMnuDocCer");
						}
					}
					else{
						if((Integer) mapRecurso.get("UsuFinGrd") == 1){
							setSOpcion("UsuFinMnuDocRec");
						}
					}
				}
			}

			log.debug("Opcion de Menu a pintar [" + getSOpcion() + "]");
			getMapSession().put("opcionmenu",getSOpcion());
			log.debug("Tipo de Frame a cargar [" + getSTipoFrame() + "]");

			// NUEVA INTERFAZ - DOJO 1.3
			// Antes
			// return Action.SUCCESS;
			// Despues
			if(rol == null || rol.getNombre().startsWith(Constantes.ROL_MESA_PARTES)
					|| rol.getNombre().startsWith(Constantes.ROL_QAS)
					|| rol.getNombre().startsWith(Constantes.ROL_DIGITALIZADOR)
					|| rol.getNombre().startsWith(Constantes.ROL_MENSAJERIA)){
				return Action.SUCCESS;
			}
			else{
				if(Constantes.ROL_ADMINISTRADOR_SUPERVISOR.equals(usuario.getRol().getNombre()) && getSTipoFrame().equals("grid")){
					setSTipoFrame("super");
				}
				return "inicionormal";
			}
			// NUEVA INTERFAZ - DOJO 1.3
		}
		// TODO posiblemente alertar de esto via mail a un administrador.
		// Seria bueno saber quien quiere ingresar, a que hora y desde donde.
		log.error("Se intento acceder al sistema, pero no hay ningun usuario en sesion");
		return Action.ERROR;
		/*
		 * } catch (Exception e) { //e.printStackTrace();
		 * log.error(e.getMessage(), e);
		 *
		 * return Action.ERROR; }
		 */
	}


	@SuppressWarnings("unchecked")
	public String runInicioNO() /* throws Exception */{
		// try {
                System.out.println("*************************RUN INICIO NO**************************************");
		getMapSession().put("readVisor", "NO");
		setMapSession(ActionContext.getContext().getSession());
		Usuario usuario =(Usuario) getMapSession().get(Constantes.SESSION_USUARIO);

		if(usuario != null){
			
                        Map<String,Integer> mapRecurso=(Map<String,Integer>) getMapSession().get(Constantes.SESSION_RECURSO);
			
                        mapSession=ActionContext.getContext().getSession();
			//List<Parametro> listPar = srvParametro.findByTipo("DESCARGAR_PENDIENTES");
			//Integer unidad = getSrvUsuario().findByIdUsuario(usuario.getIdusuario()).getUnidad().getIdunidad();
			mapSession.put("showDescargar", "0");
                        
			/*for(int i=0;i<listPar.size();i++){
			  if (listPar.get(i).getValor().equals(unidad.toString()))
				  mapSession.put("showDescargar", "1");
			}*/

			Rol rol=usuario.getRol();
                        
			if(rol != null && rol.getNombre().equals(Constantes.ROL_ADMINISTRADOR)){
				log.debug("Tipo de mantenimiento a pintar [" + getSTipoMantenimiento() + "]");
                               
				if(getSTipoMantenimiento() != null){
					setSOpcion(getSTipoMantenimiento());
				}
				else{
					setSOpcion("MantMnuUsu");
				}
			}
			else{
                                if(getSOpcion() == null){
                                       if(rol != null){
                                		if((Integer) mapRecurso.get("MPGrd") == 1){
                                                       setSOpcion("MPMnuDocReg");
						}
						else if((Integer) mapRecurso.get("DigGrd") == 1){
                                                       setSOpcion("DigMnuDocIng");
						}
						else if((Integer) mapRecurso.get("QASGrd") == 1){
                                                       setSOpcion("QASMnuDocDig");
						}
						else if((Integer) mapRecurso.get("MensGridRec") == 1){
                                                       setSOpcion("MensMnuDocRec");
						}
						else if((Integer) mapRecurso.get("MensGridCer") == 1){
                                                       setSOpcion("MensMnuDocCer");
						}
					}
					else{
						if((Integer) mapRecurso.get("UsuFinGrd") == 1){
                                                       setSOpcion("UsuFinMnuDocRec");
						}
					}
				}
			}

                        log.debug("Opcion de Menu a pintar [" + getSOpcion() + "]");
			getMapSession().put("opcionmenu",getSOpcion());
			log.debug("Tipo de Frame a cargar [" + getSTipoFrame() + "]");
                        
                        // NUEVA INTERFAZ - DOJO 1.3
			// Antes
			// return Action.SUCCESS;
			// Despues
			if(rol == null || rol.getNombre().startsWith(Constantes.ROL_MESA_PARTES)
					|| rol.getNombre().startsWith(Constantes.ROL_QAS)
					|| rol.getNombre().startsWith(Constantes.ROL_DIGITALIZADOR)
					|| rol.getNombre().startsWith(Constantes.ROL_MENSAJERIA)){
				return Action.SUCCESS;
			}
			else{
				if(Constantes.ROL_ADMINISTRADOR_SUPERVISOR.equals(usuario.getRol().getNombre()) && getSTipoFrame().equals("grid")){
				      setSTipoFrame("super");
				}
                                
                             return "inicionormal";
			}
			// NUEVA INTERFAZ - DOJO 1.3
		}
		// TODO posiblemente alertar de esto via mail a un administrador.
		// Seria bueno saber quien quiere ingresar, a que hora y desde donde.
		log.error("Se intento acceder al sistema, pero no hay ningun usuario en sesion");
                return Action.ERROR;
		
	}

	public String runInicioPendientes() throws Exception{
		try{
                        System.out.println("************************RUN INICIO PENDIENTES*************************");
			setMapSession(ActionContext.getContext().getSession());
			getMapSession().put("mantenimiento",getSTipoMantenimiento());

			return Action.SUCCESS;
		}
		catch(Exception e){
			log.error(e.getMessage(),e);
			return Action.ERROR;
		}
	}

	public String runInicioEnviados() throws Exception{
		try{
                        System.out.println("************************RUN INICIO ENVIADOS*************************");
			setMapSession(ActionContext.getContext().getSession());
			getMapSession().put("mantenimiento",getSTipoMantenimiento());

			return Action.SUCCESS;
		}
		catch(Exception e){
			log.error(e.getMessage(),e);
			return Action.ERROR;
		}
	}

	public String runPendientes() throws Exception{
		try{
                        System.out.println("************************RUN PENDIENTES*************************");
			Integer iIdUsuario=((Usuario) getMapSession().get(Constantes.SESSION_USUARIO)).getIdusuario();
			getMapSession().put("columnas","\"id\",'Sel.',\"<img src='images/bolita.gif' border='0'/>\",\"<img src='images/alta.bmp' border='0'/>\",\"Remitente\",\"Asunto\",\"Concesionario\",\"Documento\",\"Fecha\",\"<img src='images/clic.gif'/>\"");
			getMapSession().put("nrocolumnasPendientes",5);
			setArchivoPendienteList(getSrvArchivoPendiente().findByIdusuario(iIdUsuario));

			return Action.SUCCESS;
		}
		catch(Exception e){
			log.error(e.getMessage(),e);
			return Action.ERROR;
		}
	}

	public String runMenu() throws Exception{
               System.out.println("************************RUN MENU*************************");
		Usuario usuario=(Usuario) getMapSession().get(Constantes.SESSION_USUARIO);
                lisbc=svrCb.buscarLstPor(usuario.getIdusuario());
                nroNotificacionesNL=srvNotificacion.getNroNotificacionesNL(usuario);
                nroDocDigitalizados=srvDocumento.findCantDocumentos(usuario);
                return Action.SUCCESS;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void setSession(Map mapSession){
		this.setMapSession(mapSession);
	}

	public void setSession(String mapSession){
		
	}

	public String updateTemaUsuario() throws Exception{
               Usuario objUsuario=null;

		if(sTema == null){
			log.debug("No se recibio ningun tema");

			return Action.ERROR;
		}

		log.debug("Tema recibido [" + sTema + "]");

                mapSession=ActionContext.getContext().getSession();
		objUsuario=(Usuario) mapSession.get(Constantes.SESSION_USUARIO);
                objUsuario.setTema(sTema);
                objUsuario=srvUsuario.guardarUsuario(objUsuario);
                log.debug("El usuario [" + objUsuario.getUsuario() + "] cuenta con el tema [" + objUsuario.getTema() + "]");
		mapSession.put(Constantes.SESSION_USUARIO,objUsuario);

		return Action.NONE;
	}

	// ////////////////////////////////
	// Getters and Setters //
	// ////////////////////////////////
	public ArchivopendienteService getSrvArchivoPendiente(){
		return srvArchivoPendiente;
	}

	public void setSrvArchivoPendiente(ArchivopendienteService srvArchivoPendiente){
		this.srvArchivoPendiente=srvArchivoPendiente;
	}

	public ClienteService getSrvCliente(){
		return srvCliente;
	}

	public void setSrvCliente(ClienteService srvCliente){
		this.srvCliente=srvCliente;
	}

	public DocumentoService getSrvDocumento(){
		return srvDocumento;
	}

	public void setSrvDocumento(DocumentoService srvDocumento){
		this.srvDocumento=srvDocumento;
	}

	public List<DocumentoList> getLstDL(){
		return lstDL;
	}

	public void setLstDL(List<DocumentoList> lstDL){
		this.lstDL=lstDL;
	}

	public List<ArchivoPendiente> getArchivoPendienteList(){
		return archivoPendienteList;
	}

	public void setArchivoPendienteList(List<ArchivoPendiente> archivoPendienteList){
		this.archivoPendienteList=archivoPendienteList;
	}

	public List<Modulo> getLstModulo(){
		return lstModulo;
	}

	public void setLstModulo(List<Modulo> lstModulo){
		this.lstModulo=lstModulo;
	}

	public List<Perfil> getLstPerfil(){
		return lstPerfil;
	}

	public void setLstPerfil(List<Perfil> lstPerfil){
		this.lstPerfil=lstPerfil;
	}

	public List<Plantilla> getLstPlantilla(){
		return lstPlantilla;
	}

	public void setLstPlantilla(List<Plantilla> lstPlantilla){
		this.lstPlantilla=lstPlantilla;
	}

	public List<Proceso> getLstPL(){
		return lstPL;
	}

	public void setLstPL(List<Proceso> lstPL){
		this.lstPL=lstPL;
	}

	public List<Recurso> getLstRecurso(){
		return lstRecurso;
	}

	public void setLstRecurso(List<Recurso> lstRecurso){
		this.lstRecurso=lstRecurso;
	}

	public List<Rol> getLstRol(){
		return lstRol;
	}

	public void setLstRol(List<Rol> lstRol){
		this.lstRol=lstRol;
	}

	public List<Sede> getLstSede(){
		return lstSede;
	}

	public void setLstSede(List<Sede> lstSede){
		this.lstSede=lstSede;
	}

	public List<Tipodocumento> getLstTipodocumento(){
		return lstTipodocumento;
	}

	public void setLstTipodocumento(List<Tipodocumento> lstTipodocumento){
		this.lstTipodocumento=lstTipodocumento;
	}

	public List<Unidad> getLstUnidad(){
		return lstUnidad;
	}

	public void setLstUnidad(List<Unidad> lstUnidad){
		this.lstUnidad=lstUnidad;
	}

	public List<Usuario> getLstUL(){
		return lstUL;
	}

	public void setLstUL(List<Usuario> lstUL){
		this.lstUL=lstUL;
	}

	public Map<String,Object> getMapSession(){
		return mapSession;
	}

	public void setMapSession(Map<String,Object> mapSession){
		this.mapSession=mapSession;
	}

	public ModuloService getSrvModulo(){
		return srvModulo;
	}

	public void setSrvModulo(ModuloService srvModulo){
		this.srvModulo=srvModulo;
	}

	public PerfilService getSrvPerfil(){
		return srvPerfil;
	}

	public void setSrvPerfil(PerfilService srvPerfil){
		this.srvPerfil=srvPerfil;
	}

	public PlantillaService getSrvPlantilla(){
		return srvPlantilla;
	}

	public void setSrvPlantilla(PlantillaService srvPlantilla){
		this.srvPlantilla=srvPlantilla;
	}

	public ProcesoService getSrvProceso(){
		return srvProceso;
	}

	public void setSrvProceso(ProcesoService srvProceso){
		this.srvProceso=srvProceso;
	}

	public RecursoService getSrvRecurso(){
		return srvRecurso;
	}

	public void setSrvRecurso(RecursoService srvRecurso){
		this.srvRecurso=srvRecurso;
	}

	public RolService getSrvRol(){
		return srvRol;
	}

	public void setSrvRol(RolService srvRol){
		this.srvRol=srvRol;
	}

	public SedeService getSrvSede(){
		return srvSede;
	}

	public void setSrvSede(SedeService srvSede){
		this.srvSede=srvSede;
	}

	public TipodocumentoService getSrvTipodocumento(){
		return srvTipodocumento;
	}

	public void setSrvTipodocumento(TipodocumentoService srvTipodocumento){
		this.srvTipodocumento=srvTipodocumento;
	}

	public UnidadService getSrvUnidad(){
		return srvUnidad;
	}

	public void setSrvUnidad(UnidadService srvUnidad){
		this.srvUnidad=srvUnidad;
	}

	public String getSClave(){
		return sClave;
	}

	public void setSClave(String sClave){
		this.sClave=sClave;
	}

	public String getSOpcion(){
		return sOpcion;
	}

	public void setSOpcion(String sOpcion){
		this.sOpcion=sOpcion;
	}

	public String getSTipoFrame(){
		return sTipoFrame;
	}

	public void setSTipoFrame(String sTipoFrame){
		this.sTipoFrame=sTipoFrame;
	}

	public String getSTipoMantenimiento(){
		return sTipoMantenimiento;
	}

	public void setSTipoMantenimiento(String sTipoMantenimiento){
		this.sTipoMantenimiento=sTipoMantenimiento;
	}

	public String getSUsuario(){
		return sUsuario;
	}

	public void setSUsuario(String sUsuario){
		this.sUsuario=sUsuario;
	}

	public UsuarioService getSrvUsuario(){
		return srvUsuario;
	}

	public void setSrvUsuario(UsuarioService srvUsuario){
		this.srvUsuario=srvUsuario;
	}

	public List<Documentoenviado> getDocumentoEnviadoList(){
		return documentoEnviadoList;
	}

	public void setDocumentoEnviadoList(List<Documentoenviado> documentoEnviadoList){
		this.documentoEnviadoList=documentoEnviadoList;
	}

	public void setLisbc(List<CarpetaBusqueda> lisbc){
		this.lisbc=lisbc;
	}

	public List<CarpetaBusqueda> getLisbc(){
		return lisbc;
	}

	public void setSvrCb(CarpetabusquedaService svrCb){
		this.svrCb=svrCb;
	}

	public CarpetabusquedaService getSvrCb(){
		return svrCb;
	}

	public List<Expediente> getLstExpediente(){
		return lstExpediente;
	}

	public void setLstExpediente(List<Expediente> lstExpediente){
		this.lstExpediente=lstExpediente;
	}

	public ExpedienteService getSrvExpediente(){
		return srvExpediente;
	}

	public void setSrvExpediente(ExpedienteService srvExpediente){
		this.srvExpediente=srvExpediente;
	}

	public List<Cliente> getLstCliente(){
		return lstCliente;
	}

	public void setLstCliente(List<Cliente> lstCliente){
		this.lstCliente=lstCliente;
	}

	public List<Documento> getLstDocumento(){
		return lstDocumento;
	}

	public void setLstDocumento(List<Documento> lstDocumento){
		this.lstDocumento=lstDocumento;
	}

	public int getIdCarpeta(){
		return idCarpeta;
	}

	public void setIdCarpeta(int idCarpeta){
		this.idCarpeta=idCarpeta;
	}

	public String getsForwardToURL(){
		return sForwardToURL;
	}

	public void setsForwardToURL(String sForwardToURL){
		this.sForwardToURL=sForwardToURL;
	}

	public String getsTema(){
		return sTema;
	}

	public void setsTema(String sTema){
		this.sTema=sTema;
	}

	public List<Grupoproceso> getLstGrupoProceso(){
		return lstGrupoProceso;
	}

	public void setLstGrupoProceso(List<Grupoproceso> lstGrupoProceso){
		this.lstGrupoProceso=lstGrupoProceso;
	}

	public GrupoprocesoService getSrvGrupoProceso(){
		return srvGrupoProceso;
	}

	public void setSrvGrupoProceso(GrupoprocesoService srvGrupoProceso){
		this.srvGrupoProceso=srvGrupoProceso;
	}

	public List<Numeracion> getLstNumeracion(){
		return lstNumeracion;
	}

	public void setLstNumeracion(List<Numeracion> lstNumeracion){
		this.lstNumeracion=lstNumeracion;
	}

	public NumeracionService getSrvNumeracion(){
		return srvNumeracion;
	}

	public void setSrvNumeracion(NumeracionService srvNumeracion){
		this.srvNumeracion=srvNumeracion;
	}

	public List<Parametro> getLstParametro(){
		return lstParametro;
	}

	public void setLstParametro(List<Parametro> lstParametro){
		this.lstParametro=lstParametro;
	}

	public ParametroService getSrvParametro(){
		return srvParametro;
	}

	public void setSrvParametro(ParametroService srvParametro){
		this.srvParametro=srvParametro;
	}

	public DepartamentoService getSrvDepartamento(){
		return srvDepartamento;
	}

	public void setSrvDepartamento(DepartamentoService srvDepartamento){
		this.srvDepartamento=srvDepartamento;
	}

	public List<Departamento> getLstDepartamento(){
		return lstDepartamento;
	}

	public void setLstDepartamento(List<Departamento> lstDepartamento){
		this.lstDepartamento=lstDepartamento;
	}

	public ProvinciaService getSrvProvincia(){
		return srvProvincia;
	}

	public void setSrvProvincia(ProvinciaService srvProvincia){
		this.srvProvincia=srvProvincia;
	}

	public List<Provincia> getLstProvincia(){
		return lstProvincia;
	}

	public void setLstProvincia(List<Provincia> lstProvincia){
		this.lstProvincia=lstProvincia;
	}

	public DistritoService getSrvDistrito(){
		return srvDistrito;
	}

	public void setSrvDistrito(DistritoService srvDistrito){
		this.srvDistrito=srvDistrito;
	}

	public List<Distrito> getLstDistrito(){
		return lstDistrito;
	}

	public void setLstDistrito(List<Distrito> lstDistrito){
		this.lstDistrito=lstDistrito;
	}

	public List<Lista> getLstLista(){
		return lstLista;
	}

	public void setLstLista(List<Lista> lstLista){
		this.lstLista=lstLista;
	}

	public ListaService getSrvLista(){
		return srvLista;
	}

	public void setSrvLista(ListaService srvLista){
		this.srvLista=srvLista;
	}

	public List<Reemplazo> getLstReemplazo(){
		return lstReemplazo;
	}

	public void setLstReemplazo(List<Reemplazo> lstReemplazo){
		this.lstReemplazo=lstReemplazo;
	}

	public ReemplazoService getSrvReemplazo(){
		return srvReemplazo;
	}

	public void setSrvReemplazo(ReemplazoService srvReemplazo){
		this.srvReemplazo=srvReemplazo;
	}

	public Integer getParametroRol() {
		return parametroRol;
	}

	public void setParametroRol(Integer parametroRol) {
		this.parametroRol = parametroRol;
	}

	public SeguimientoService getSeguimientoService() {
		return seguimientoService;
	}

	public void setSeguimientoService(SeguimientoService seguimientoService) {
		this.seguimientoService = seguimientoService;
	}
        
}
