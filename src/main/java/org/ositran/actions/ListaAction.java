/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.actions;

import com.btg.ositran.siged.domain.Lista;
import com.btg.ositran.siged.domain.Usuario;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.SessionAware;
import org.ositran.services.ListaService;
import org.ositran.services.UsuarioService;
import org.ositran.utils.Constantes;

public class ListaAction implements SessionAware {

	private static Logger log = Logger.getLogger(ListaAction.class);
	private Integer iIdLista;
	private Map<String,Object> mapSession = new HashMap<String,Object>();
	private Map<Integer,String> mapUsuarioLeft = new HashMap<Integer,String>();
	private Map<Integer,String> mapUsuarioRight = new HashMap<Integer,String>();
	private List<Integer> lstUsuarioRight;
	private List<Lista> lstLista;
	private Lista objLista;
	private ListaService srvLista;
	private String strTipoMant;
	private UsuarioService srvUsuario;

	//////////////////////////////////
	// Constructors                 //
	//////////////////////////////////
	public ListaAction(ListaService srvLista) {
		setSrvLista(srvLista);
	}

	//////////////////////////////////
	// Methods                      //
	//////////////////////////////////
	public String deleteLista() throws Exception {
		try {
			if (getIIdLista() == null) {
				log.error("No hay ninguna lista a eliminar ID [" + getIIdLista() + "]");

				return Action.ERROR;
			}

			log.debug("Lista a eliminar con ID [" + getIIdLista() + "]");
			getSrvLista().deleteLista(getIIdLista());

			return Action.SUCCESS;
		} catch (Exception e) {
			log.error(e.getMessage(), e);

			return Action.ERROR;
		}
	}

	public String loadListaArray() throws Exception {
		try {
			setMapSession(ActionContext.getContext().getSession());
			Usuario objUsuario = (Usuario) getMapSession().get(Constantes.SESSION_USUARIO);

			log.debug("Obteniendo lista de contactos para el usuario con ID [" + objUsuario.getIdusuario() + "] Usuario [" + objUsuario.getUsuario() + "]");

			setLstLista(getSrvLista().findLstBy(objUsuario.getIdusuario()));

			log.debug("Lista de contactos con size [" + getLstLista().size() + "]");

			return Action.SUCCESS;
		} catch (Exception e) {
			log.error(e.getMessage(), e);

			return Action.ERROR;
		}
	}

	public String saveLista() throws Exception {
		if (objLista == null) {
			log.error("No hay lista a guardar");

			return Action.ERROR;
		}

		try {
			Lista objListaNew = null;
			Lista objListaOld = null;
			String sTipoAuditoria = null;
			Usuario objUsuario = null;

			mapSession = ActionContext.getContext().getSession();
			objUsuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
			objListaOld = (Lista) mapSession.get(Constantes.SESSION_AUDITABLE);
			mapSession.remove(Constantes.SESSION_AUDITABLE);

			if (lstUsuarioRight == null) {
				this.lstUsuarioRight = new ArrayList<Integer>();
			}

			log.debug("Nuevos contactos a guardar con size [" + lstUsuarioRight.size() + "]");

			if (objLista.getIdlista() == null) {
				log.debug("** Creacion de lista **");
				objListaNew = objLista;
				objListaNew.setIdpropietario(objUsuario);
				objListaNew.setFechacreacion(new Date());
				objListaNew.setEstado(Constantes.ESTADO_ACTIVO);
				sTipoAuditoria = Constantes.AUDITORIA_TIPO_REGISTRO;
			} else {
				log.debug("** Actualizacion de lista con ID [" + objLista.getIdlista() + "] **");
				objListaNew = srvLista.findByIdLista(objLista.getIdlista());
				objListaNew.setNombre(objLista.getNombre());
				sTipoAuditoria = Constantes.AUDITORIA_TIPO_ACTUALIZACION;
			}

			srvLista.saveLista(objListaOld, objListaNew, lstUsuarioRight, objUsuario.getUsuario(), sTipoAuditoria);

			return Action.SUCCESS;
		} catch (Exception e) {
			log.error(e.getMessage(), e);

			return Action.ERROR;
		}
	}

	public String viewLista() throws Exception {
		try {
			mapSession = ActionContext.getContext().getSession();

			log.debug("iIdLista [" + iIdLista + "]");

			if (iIdLista != null) {
				objLista = srvLista.findByIdLista(iIdLista);
				mapUsuarioLeft = srvUsuario.getMapUsuarioFinalLeft(objLista.getUsuarioList());
				mapUsuarioRight = srvUsuario.getMapUsuarioFinalRight(objLista.getUsuarioList());
				log.debug("Lista Nombre [" + objLista.getNombre() + "] Usuarios disponibles [" + mapUsuarioLeft.size() + "] Contactos [" + mapUsuarioRight.size() + "]");
				mapSession.put(Constantes.SESSION_AUDITABLE, objLista);
			} else {
				mapUsuarioLeft = getSrvUsuario().getMapByUsuarioFinal(Constantes.FLAG_USUARIO_FINAL_S);
				log.debug("Mapa de usuarios finales left con size [" + mapUsuarioLeft.size() + "]");
				mapSession.put(Constantes.SESSION_AUDITABLE, new Lista());
			}

			return Action.SUCCESS;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return Action.ERROR;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void setSession(Map mapSession) {
		this.mapSession = mapSession;
	}

	//////////////////////////////////
	// Getters and Setters          //
	//////////////////////////////////
	public Integer getIIdLista() {
		return iIdLista;
	}

	public void setIIdLista(Integer iIdLista) {
		this.iIdLista = iIdLista;
	}

	public Map<String,Object> getMapSession() {
		return mapSession;
	}

	public void setMapSession(Map<String,Object> mapSession) {
		this.mapSession = mapSession;
	}

	public Map<Integer,String> getMapUsuarioLeft() {
		return mapUsuarioLeft;
	}

	public void setMapUsuarioLeft(Map<Integer,String> mapUsuarioLeft) {
		this.mapUsuarioLeft = mapUsuarioLeft;
	}

	public Map<Integer,String> getMapUsuarioRight() {
		return mapUsuarioRight;
	}

	public void setMapUsuarioRight(Map<Integer,String> mapUsuarioRight) {
		this.mapUsuarioRight = mapUsuarioRight;
	}

	public List<Integer> getLstUsuarioRight() {
		return lstUsuarioRight;
	}

	public void setLstUsuarioRight(List<Integer> lstUsuarioRight) {
		this.lstUsuarioRight = lstUsuarioRight;
	}

	public List<Lista> getLstLista() {
		return lstLista;
	}

	public void setLstLista(List<Lista> lstLista) {
		this.lstLista = lstLista;
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

	public String getStrTipoMant() {
		return strTipoMant;
	}

	public void setStrTipoMant(String strTipoMant) {
		this.strTipoMant = strTipoMant;
	}

	public UsuarioService getSrvUsuario() {
		return srvUsuario;
	}

	public void setSrvUsuario(UsuarioService srvUsuario) {
		this.srvUsuario = srvUsuario;
	}
}
