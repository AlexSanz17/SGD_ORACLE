/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.actions;

import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.apache.struts2.json.annotations.SMDMethod;
import org.ositran.services.IntalioService;
import org.ositran.services.UsuarioService;
import org.ositran.utils.Constantes;
import org.ositran.webservice.clientes.intalio.InvalidInputMessageFaultException;
import org.ositran.webservice.clientes.intalio.InvalidParticipantTokenFaultException;
import org.ositran.webservice.clientes.intalio.UnavailableTaskFaultException;

import com.btg.ositran.siged.domain.Intalio;
import com.btg.ositran.siged.domain.Usuario;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

public class IntalioAction {

	private static Logger log=Logger.getLogger(IntalioAction.class);
	private IntalioService intalioService;
	private UsuarioService usuarioService;
	private List<Intalio> data;
	
	public String execute() throws Exception {
		return Action.SUCCESS;
	}
	
	public UsuarioService getUsuarioService() {
		return usuarioService;
	}

	public void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	
	@SuppressWarnings("rawtypes")
	public String listarProcesos(){
		Map session=ActionContext.getContext().getSession();
		Usuario usuario=(Usuario) session.get(Constantes.SESSION_USUARIO);
		String forward=Action.SUCCESS;
		try {
			data=intalioService.listaProcesos(usuario);
			if(data==null){forward=Action.ERROR;}
			
		} catch (RemoteException ex) {
			log.error(ex.getMessage(), ex);
		} catch (UnsupportedEncodingException ex) {
			log.error(ex.getMessage(), ex);
		} catch (InvalidInputMessageFaultException ex) {
			log.error(ex.getMessage(), ex);
		} catch (InvalidParticipantTokenFaultException ex) {
			log.error(ex.getMessage(), ex);
		} catch (UnavailableTaskFaultException ex) {
			log.error(ex.getMessage(), ex);
		}
		return forward;
	}
	
	@SuppressWarnings("rawtypes")
	@SMDMethod
	public String CrearExpediente(String proceso) throws Exception{
		log.debug("datos recibidos = "+proceso);
		String forward="Error";
		Map session=ActionContext.getContext().getSession();
		Usuario user=(Usuario) session.get(Constantes.SESSION_USUARIO);
		try{
                    Integer id=intalioService.crearExpediente(user, proceso);
                    forward=id.toString();
		}catch(Exception e){
			log.error(e.getMessage(), e);
		}
		
		return forward;
	}	
	
	public IntalioService getIntalioService() {
		return intalioService;
	}

	public void setIntalioService(IntalioService intalioService) {
		this.intalioService = intalioService;
	}

	public List<Intalio> getData() {
		return data;
	}

	public void setData(List<Intalio> data) {
		this.data = data;
	}
	
	
}
