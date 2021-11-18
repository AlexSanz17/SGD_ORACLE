/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.actions;

import com.btg.ositran.siged.domain.Documento;
import com.btg.ositran.siged.domain.Lista;
import com.btg.ositran.siged.domain.Notificacion;
import com.btg.ositran.siged.domain.SeguimientoXUsuario;
import com.btg.ositran.siged.domain.Usuario;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ositran.services.ArchivoService;
import org.ositran.services.DocumentoService;
import org.ositran.services.ListaService;
import org.ositran.services.ManejoDeEmailService;
import org.ositran.services.NotificacionService;
import org.ositran.services.ParametroService;
import org.ositran.services.SeguimientoXUsuarioService;
import org.ositran.services.UsuarioService;
import org.ositran.utils.Constantes;
import org.ositran.utils.FechaLimite;
import org.ositran.services.FuncionService;
import org.ositran.services.UnidadService;

public class NotificacionAction{

	private static Log log=LogFactory.getLog(NotificacionAction.class);

	private String sCloseMe;

	private boolean agregar;

	private Notificacion objNotificacion;

	private Integer iIdNotificacion;
	private Integer iIdDocumento;
	private Integer iIdDoc;
	private Integer[] arrIdDoc;
	private List<String> conCopia;

	private ArchivoService archivoService;
        private FuncionService funcionService;
	private UsuarioService srvUsuario;
        private UnidadService unidadService;

   
	private DocumentoService srvDocumento;
	private NotificacionService srvNotificacion;
	private SeguimientoXUsuarioService seguimientoXUsuarioService;
	private ParametroService parametroService;
	private ListaService listaService;
	private ManejoDeEmailService mailService;
	private FechaLimite fechaLimite;
	private Map<String,Object> mapSession;

	public NotificacionAction(NotificacionService srvNotificacion){
		this.srvNotificacion=srvNotificacion;
	}

	public String getNotificacionDetail() throws Exception {
           	log.debug("-> [Action] NotificacionAction - getNotificacionDetail():String ");

		if (iIdNotificacion == null) {
			log.error("No se recibio el ID de documento");

			return Action.ERROR;
		}

		try {
			if (log.isDebugEnabled()) {
				log.debug("Notificacion a buscar con ID [" + iIdNotificacion + "]");
			}

			objNotificacion = srvNotificacion.buscarObjPorID(iIdNotificacion);
			objNotificacion.setTiponotificacion(667);
			Map<String, Object> sesion = ActionContext.getContext().getSession();
			Usuario objUsuario = (Usuario) sesion.get(Constantes.SESSION_USUARIO);

			sesion.remove(Constantes.SESSION_UPLOAD_LIST);

			agregar = true;
			iIdDoc = objNotificacion.getIddocumento().getIdDocumento();

                        SeguimientoXUsuario seguimiento = new SeguimientoXUsuario();
                        seguimiento.setIdUsuario(objUsuario.getIdUsuarioPerfil());
                        seguimiento.setIdDocumento(iIdDoc);
                        seguimiento.setUnidadPropietario(objUsuario.getIdUnidadPerfil());
                        seguimiento.setCargoPropietario(objUsuario.getIdFuncionPerfil());
                        
			if(seguimientoXUsuarioService.buscarSeguimiento(seguimiento).isEmpty()){
				agregar = false;
			}

			if (objNotificacion != null) {
				if (log.isDebugEnabled()) {
					log.debug("Se encontro notificacion con asunto [" + objNotificacion.getAsunto() + "]");
				}

				if (objNotificacion.getIddocumento() != null) {
					sesion.put(Constantes.SESSION_UPLOAD_LIST, archivoService.findByIdDocumento(objNotificacion.getIddocumento().getDocumentoreferencia()==null?objNotificacion.getIddocumento().getIdDocumento():objNotificacion.getIddocumento().getDocumentoreferencia()));

					if (log.isDebugEnabled()) {
						log.debug("Colocando en sesion idDocumento [" + objNotificacion.getIddocumento().getIdDocumento() + "]");
					}

					sesion.put(Constantes.SESSION_IDDOCUMENTO, objNotificacion.getIddocumento().getIdDocumento());
				}
			}

			try{
                             if (objNotificacion.getContenido()!=null && !objNotificacion.getContenido().trim().equals(""))
			       objNotificacion.setContenido(objNotificacion.getContenido().replaceAll("\"", "'"));

			}catch(Exception ex) {
                              ex.printStackTrace();
			}

			return Action.SUCCESS;
		} catch (Exception e) {
			log.error(e.getMessage(), e);

			return Action.ERROR;
		}
	}

	public String eliminar(){
		log.debug("-> [Action] NotificacionAction - eliminar():String ");
		mapSession = ActionContext.getContext().getSession();
		String nombrePC = (String)mapSession.get("nombrePC");
		Map<String,Object> request=ActionContext.getContext().getParameters();
		String[] ids=(String[])request.get("id");
		srvNotificacion.eliminarDocumentosEnviados(ids, nombrePC);
		return Action.SUCCESS;
	}

	public String goNotificarUser() throws Exception {
		log.debug("-> [Action] NotificacionAction - goNotificarUser():String ");

                if (arrIdDoc == null || arrIdDoc.length <= 0) {
			log.error("No se recibieron correctamente los parametros");

			return Action.ERROR;
		}

		if (log.isDebugEnabled()) {
			log.debug("iIdNotificacion [" + iIdNotificacion + "]");
			log.debug("Longitud de la variable arrIdDoc [" + arrIdDoc.length + "] Primer dato [" + arrIdDoc[0] + "]");
		}

                mapSession = ActionContext.getContext().getSession();
		mapSession.put("arrIdDoc", arrIdDoc);

                if(iIdNotificacion == null){
			objNotificacion = new Notificacion();
			Documento doc = srvDocumento.findByIdDocumento(arrIdDoc[0]);
			objNotificacion.setAsunto(doc.getAsunto());
			objNotificacion.setIddocumento(doc);
			objNotificacion.setTiponotificacion(Constantes.TIPO_NOTIFICACION_DERIVACIONCONCOPIA);
		}else{
			objNotificacion = srvNotificacion.buscarObjPorID(iIdNotificacion);
		}

                //Usuario usuarioSesion = (Usuario) getMapSession().get(Constantes.SESSION_USUARIO);
		String strCont = "";/* "<br />---------------------------------------------------<br />"+ srvUsuario.findByIdUsuario(usuarioSesion.getIdUsuarioPerfil()).getNombreCompleto() +
			  		" ("+(usuarioSesion.getIdFuncionPerfil() != null ? funcionService.findByIdFuncion(usuarioSesion.getIdFuncionPerfil()).getNombre() : "")+")";
                                   */
                
                objNotificacion.setContenido(strCont);

		return Action.SUCCESS;
	}

	public String doNotificarUser() throws Exception{
		log.debug("-> [Action] NotificacionAction - doNotificarUser():String ");

		if(conCopia == null || conCopia.size() == 0){
			log.error("No se recibio destinatarios CC");
			return Action.SUCCESS;
		}

                mapSession=ActionContext.getContext().getSession();
		Usuario objUsuario=(Usuario) mapSession.get(Constantes.SESSION_USUARIO);

		String nombrePC = (String)mapSession.get("nombrePC");
		Documento objDocumento=srvDocumento.findByIdDocumento(objNotificacion.getIddocumento().getIdDocumento());
		Set<Integer> usuariosNotificados = new HashSet<Integer>();
                
                if(conCopia != null){
			for(String elemento : conCopia){
				if (elemento.startsWith("LISTA")) {
					Integer iID = Integer.valueOf(elemento.substring(elemento.indexOf("_") + 1));
					Lista objLista = listaService.findByIdLista(iID);
                                  	for(Usuario usuario : objLista.getParticipanteListaList()){
						if(usuario != null && usuario.getIdusuario()!=null && !usuariosNotificados.contains(usuario.getIdusuario())){
							srvNotificacion.clonarNotificacion(objUsuario,srvUsuario.findByIdUsuario(usuario.getIdusuario()),objDocumento,objNotificacion.getTiponotificacion(),objNotificacion.getAsunto(),objNotificacion.getContenido(), objNotificacion.getIdnotificacion(), nombrePC,fechaLimite.validarHorarioPermitido(objUsuario));
							//mailService.ChaskiMail(Constantes.CONFIGNOTIFMAIL_DOCUMENTO_CCREENVIAR, objUsuario, srvUsuario.findByIdUsuario(usuario.getIdusuario()), objDocumento);
							usuariosNotificados.add(usuario.getIdusuario());
						}
					}
				}else{
                                         String[] datos = elemento.split("-");
					 Usuario usuarioReceptor = srvUsuario.findByIdUsuario(new Integer(datos[0]));
                                         usuarioReceptor.setIdUnidadPerfil(new Integer(datos[1]));
                                         usuarioReceptor.setIdFuncionPerfil(new Integer(datos[2]));
					 srvNotificacion.clonarNotificacion(objUsuario,usuarioReceptor,objDocumento,objNotificacion.getTiponotificacion(),objNotificacion.getAsunto(),objNotificacion.getContenido(), objNotificacion.getIdnotificacion(), nombrePC,fechaLimite.validarHorarioPermitido(objUsuario));
					 usuariosNotificados.add(new Integer(datos[0]));
                                        
				}
			}
		}

		sCloseMe="TRUE";

		return Action.SUCCESS;
	}

	public Integer getiIdNotificacion(){
		return iIdNotificacion;
	}

	public void setiIdNotificacion(Integer iIdNotificacion){
		this.iIdNotificacion=iIdNotificacion;
	}

	public Notificacion getObjNotificacion(){
		return objNotificacion;
	}

	public void setObjNotificacion(Notificacion objNotificacion){
		this.objNotificacion=objNotificacion;
	}

	public NotificacionService getSrvNotificacion(){
		return srvNotificacion;
	}

	public void setSrvNotificacion(NotificacionService srvNotificacion){
		this.srvNotificacion=srvNotificacion;
	}

	public String getsCloseMe(){
		return sCloseMe;
	}

	public void setsCloseMe(String sCloseMe){
		this.sCloseMe=sCloseMe;
	}

	public List<String> getConCopia(){
		return conCopia;
	}

	public void setConCopia(List<String> conCopia){
		this.conCopia=conCopia;
	}

	public Map<String,Object> getMapSession(){
		return mapSession;
	}

	public void setMapSession(Map<String,Object> mapSession){
		this.mapSession=mapSession;
	}

	public UsuarioService getSrvUsuario(){
		return srvUsuario;
	}

	public void setSrvUsuario(UsuarioService srvUsuario){
		this.srvUsuario=srvUsuario;
	}

	public DocumentoService getSrvDocumento(){
		return srvDocumento;
	}

	public void setSrvDocumento(DocumentoService srvDocumento){
		this.srvDocumento=srvDocumento;
	}

	public void setArchivoService(ArchivoService archivoService){
		this.archivoService=archivoService;
	}

	public Integer getiIdDocumento() {
		return iIdDocumento;
	}

	public void setiIdDocumento(Integer iIdDocumento) {
		this.iIdDocumento = iIdDocumento;
	}
        
         public FuncionService getFuncionService() {
            return funcionService;
        }

        public void setFuncionService(FuncionService funcionService) {
            this.funcionService = funcionService;
        }

	public Integer[] getArrIdDoc() {
		return arrIdDoc;
	}

	public void setArrIdDoc(Integer[] arrIdDoc) {
		this.arrIdDoc = arrIdDoc;
	}

	public boolean isAgregar() {
		return agregar;
	}

	public void setAgregar(boolean agregar) {
		this.agregar = agregar;
	}

	public SeguimientoXUsuarioService getSeguimientoXUsuarioService() {
		return seguimientoXUsuarioService;
	}

	public void setSeguimientoXUsuarioService(
			SeguimientoXUsuarioService seguimientoXUsuarioService) {
		this.seguimientoXUsuarioService = seguimientoXUsuarioService;
	}

	public Integer getiIdDoc() {
		return iIdDoc;
	}

	public void setiIdDoc(Integer iIdDoc) {
		this.iIdDoc = iIdDoc;
	}

	public ParametroService getParametroService() {
		return parametroService;
	}

	public void setParametroService(ParametroService parametroService) {
		this.parametroService = parametroService;
	}

	public FechaLimite getFechaLimite() {
		return fechaLimite;
	}

	public void setFechaLimite(FechaLimite fechaLimite) {
		this.fechaLimite = fechaLimite;
	}

	public ListaService getListaService() {
		return listaService;
	}

	public void setListaService(ListaService listaService) {
		this.listaService = listaService;
	}

	public ManejoDeEmailService getMailService() {
		return mailService;
	}

	public void setMailService(ManejoDeEmailService mailService) {
		this.mailService = mailService;
	}
        
         public UnidadService getUnidadService() {
            return unidadService;
        }

        public void setUnidadService(UnidadService unidadService) {
            this.unidadService = unidadService;
        }

}
