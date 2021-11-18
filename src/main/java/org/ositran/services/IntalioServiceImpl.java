package org.ositran.services;

import gob.ositran.siged.service.SeguridadService;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.xml.stream.XMLStreamException;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.impl.llom.util.AXIOMUtil;
import org.apache.axis2.AxisFault;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ositran.daos.DocumentoDAO;
import org.ositran.daos.ExpedienteDAO;
import org.ositran.daos.ProcesoDAO;
import org.ositran.daos.TipodocumentoDAO;
import org.ositran.utils.Constantes;
import org.ositran.webservice.clientes.intalio.InvalidInputMessageFaultException;
import org.ositran.webservice.clientes.intalio.InvalidParticipantTokenFaultException;
import org.ositran.webservice.clientes.intalio.TaskManagementServicesStub;
import org.ositran.webservice.clientes.intalio.TokenServiceStub;
import org.ositran.webservice.clientes.intalio.UnavailableTaskFaultException;
import org.ositran.webservice.clientes.intalio.TaskManagementServicesStub.GetTaskListRequest;
import org.ositran.webservice.clientes.intalio.TaskManagementServicesStub.GetTaskListResponse;
import org.ositran.webservice.clientes.intalio.TaskManagementServicesStub.GetTaskRequest;
import org.ositran.webservice.clientes.intalio.TaskManagementServicesStub.GetTaskResponse;
import org.ositran.webservice.clientes.intalio.TaskManagementServicesStub.InitRequest;
import org.ositran.webservice.clientes.intalio.TaskManagementServicesStub.Input_type0;
import org.ositran.webservice.clientes.intalio.TaskManagementServicesStub.Input_type1;
import org.ositran.webservice.clientes.intalio.TaskManagementServicesStub.Task;
import org.ositran.webservice.clientes.intalio.TaskManagementServicesStub.TaskMetadata;
import org.ositran.webservice.clientes.intalio.TokenServiceStub.AuthenticateUser;
import org.springframework.transaction.annotation.Transactional;

import com.btg.ositran.siged.domain.Accion;
import com.btg.ositran.siged.domain.Cliente;
import com.btg.ositran.siged.domain.Documento;
import com.btg.ositran.siged.domain.Expediente;
import com.btg.ositran.siged.domain.Intalio;
import com.btg.ositran.siged.domain.Proceso;
import com.btg.ositran.siged.domain.Tipodocumento;
import com.btg.ositran.siged.domain.Usuario;

/**
 * @author Istvan
 *
 */
public class IntalioServiceImpl implements IntalioService{
	
	Log log=LogFactory.getLog(IntalioServiceImpl.class);

	private ExpedienteDAO expedientedao;
	private ExpedienteService expedienteService;
	private ProcesoDAO procesoDAO;
	private RepositorioService repositorioService;
	private TipodocumentoDAO tipodocumentodao;
	private DocumentoDAO documentodao;
	private AccionService accionService;
	private ClienteService clienteService;
   private SeguridadService seguridadService;
	
	@SuppressWarnings("unchecked")
	@Override
	public String getAjaxFormURL(Usuario usuario,int idExpediente){
		TaskManagementServicesStub taskService;
		try{
			taskService=new TaskManagementServicesStub();
			String token=iniciarSesion(usuario);
			if(token!=null){
				TaskMetadata[] tareas=getTareas(taskService,token,"ACTIVITY");
				if(tareas!=null){
					String url=null;
					for(TaskMetadata tarea : tareas){
					if(tarea.getTaskState()!=null){	
						if((tarea.getTaskState().toString().equals("READY")||tarea.getTaskState().toString().equals("CLAIMED")) && tarea.getTaskType().toString().equals("ACTIVITY")){
							String idTarea=tarea.getTaskId();
							GetTaskRequest peticion=new GetTaskRequest();
							peticion.setParticipantToken(token);
							peticion.setTaskId(idTarea);
							GetTaskResponse respuesta=taskService.getTask(peticion);
							Task encontrada=respuesta.getTask();
							if(encontrada!=null){
								Input_type1 input=encontrada.getInput();
								if(input!=null){
									OMElement[] elementos=input.getExtraElement();
									for(OMElement elemento : elementos){
										// la raiz deberia ser un FormModel
										Iterator<OMElement> hijos=elemento.getChildrenWithLocalName("campoExpediente");
										while(hijos.hasNext()){
											OMElement campo=hijos.next();
											String nombreCampo=campo.getLocalName();
											if(nombreCampo.equals("campoExpediente")){
												Iterator<OMElement> expedientes=campo.getChildrenWithLocalName("idExpediente");
												while(expedientes.hasNext()){
													OMElement expediente=expedientes.next();
													String nombre=expediente.getLocalName();
													if(nombre.equals("idExpediente")){
														boolean res=expediente.getText().equals(new String(""+idExpediente));
														log.info("if("+expediente.getText()+"=="+idExpediente+")="+res);
														if(res){
															url=URLDecoder.decode(tarea.getFormUrl().toString(),"UTF-8");
															url+="?id="+tarea.getTaskId();
															url+="&url="+tarea.getFormUrl().toString();
															url+="&token="+token;
															url+="&user="+usuario.getUsuario();
														}
													}
												}
											}
										}
									}
								}
							}
						}
					   }
					}
					return url;
				}
				log.warn("No se encontraron tareas para el usuario "+usuario.getUsuario());
				return null;
			}
			log.error("El usuario "+usuario.getUsuario()+" no pudo iniciar sesion en Intalio");
			return null;
		}
		catch(AxisFault e){
			log.error("Ocurrio un error al obtener la url",e);
			return null;
		}
		catch(RemoteException e){
			log.error("No se pudo conectar al webservice",e);
			return null;
		}
		catch(InvalidInputMessageFaultException e){
			log.error("Parametros ingresados no validos",e);
			return null;
		}
		catch(InvalidParticipantTokenFaultException e){
			log.error("Error al iniciar sesion en intalio",e);
			return null;
		}
		catch(UnsupportedEncodingException e){
			log.error("No se pudo generar la url",e);
			return null;
		} catch (UnavailableTaskFaultException e) {
			log.error("La tarea buscada no ha sido encontrada",e);
			return null;
		}
	}

	@Override
	public boolean iniciarProceso(Usuario iniciante,Usuario receptor,String proceso,int idExpediente) throws RemoteException, InvalidInputMessageFaultException, InvalidParticipantTokenFaultException, UnavailableTaskFaultException, XMLStreamException{
		TaskManagementServicesStub taskService=new TaskManagementServicesStub();
		String token=iniciarSesion(iniciante);
		if(token!=null){
			TaskMetadata[] tareas=getTareas(taskService,token,"INIT");
			if(tareas!=null){
				for(TaskMetadata tarea : tareas){
					String tipo=tarea.getTaskType().toString();
					String descripcion=tarea.getDescription();
					if(tipo.equals("INIT") && descripcion.contains(proceso)){
						InitRequest peticion=new InitRequest();
						peticion.setParticipantToken(token);
						peticion.setTaskId(tarea.getTaskId());
						Input_type0 input=new Input_type0();
						String elemento="<FormModel xmlns=\""+tarea.getInitMessageNamespaceURI().toString()+"\"><campoExpediente xmlns=\"\"><idExpediente>"+idExpediente+"</idExpediente></campoExpediente></FormModel>";
						//OMFactory fac=OMAbstractFactory.getOMFactory();
					    //OMNamespace omNs=fac.createOMNamespace(InitRequest.MY_QNAME.getNamespaceURI().toString(),"");
						//OMElement expediente=fac.createOMElement("idExpediente",omNs);
						//expediente.setText(""+idExpediente);
						OMElement expediente=AXIOMUtil.stringToOM(elemento);
						input.addExtraElement(expediente);
						peticion.setInput(input);
						taskService.initProcess(peticion);
						return true;
					}
				}
				log.error("El usuario "+iniciante.getUsuario()+" no posee ninguna tarea de inicializacion de proceso en Intalio.");
				return false;
			}
			log.error("El usuario "+iniciante.getUsuario()+" no posee ninguna tarea en Intalio");
			return false;
		}
		log.error("El usuario "+iniciante.getUsuario()+" no pudo iniciar sesion en Intalio");
		return false;
	}
	
	private String iniciarSesion(Usuario usuario) throws RemoteException{
		String nombre=usuario.getUsuario();
		String password=usuario.getClave();
		TokenServiceStub tokenService=new TokenServiceStub();
		AuthenticateUser au=new AuthenticateUser();
		au.setUser(nombre);
		au.setPassword(seguridadService.desencriptar(nombre, password));
		return tokenService.authenticateUser(au).getToken();
	}
	
	private TaskMetadata[] getTareas(TaskManagementServicesStub taskManagerService,String token,String tipo) throws RemoteException, InvalidInputMessageFaultException, InvalidParticipantTokenFaultException{
		/*GetAvailableTasksRequest peticion=new GetAvailableTasksRequest();
		peticion.setParticipantToken(token);
		peticion.setTaskType(tipo);
		peticion.setSubQuery("T._state = TaskState.READY");*/
		GetTaskListResponse respuesta=taskManagerService.getTaskList(new GetTaskListRequest(token));
		if(respuesta!=null){
			return respuesta.getTask();
		}
		return null;
	}
	
	public List<Intalio> listaProcesos(Usuario usuario) throws RemoteException, InvalidInputMessageFaultException, InvalidParticipantTokenFaultException, UnavailableTaskFaultException, UnsupportedEncodingException{
		String token=iniciarSesion(usuario);
		TaskManagementServicesStub taskService=new TaskManagementServicesStub();
		TaskMetadata[] data=getTareas(taskService,token,"INIT");
		String url="";
		Intalio proceso=null;
		List<Intalio> dataProceso=null;
		if(data!=null){
			 dataProceso=new ArrayList<Intalio>();
			log.debug("Se encontraron proceso = "+data.length);
			for(TaskMetadata tarea: data){
				String tipo=tarea.getTaskType().toString();
				if(tipo.equals("INIT")){
				proceso=new Intalio();
				// Armamos la url			
				url = URLDecoder.decode(tarea.getFormUrl().toString(),"UTF-8");
				url+="?id="+tarea.getTaskId();
				url+="&url="+tarea.getFormUrl().toString();
				url+="&token="+token;
				url+="&user="+usuario.getUsuario();
				// almacenando lista
				proceso.setIdProceso(tarea.getTaskId());
				proceso.setProceso(tarea.getDescription());
				proceso.setUrl(url);
				proceso.setEstado(tarea.getTaskType().toString());
				dataProceso.add(proceso);
				}
			}
		}
		return dataProceso;
	}
	
	@Transactional
	public Integer crearExpediente(Usuario usuario,String proceso) throws Exception {

		/***********************************************************************************************************/
		// REGISTRAR EL EXPEDIENTE Y EXPEDIENTE SAS
		/***********************************************************************************************************/
		Proceso p=procesoDAO.findName(proceso);
		Expediente objExp = new Expediente();
		objExp.setNroexpediente(expedienteService.getMaxReferencia());
		objExp.setFechacreacion(new Date());
		//objExp.setProceso(p);
		Cliente c=clienteService.findByNroIdentificacion(Constantes.RUC_OSINERG1);
		objExp.setCliente(c);
		objExp.setIdpropietario(usuario);
		objExp.setEstado(Constantes.ESTADO_INACTIVO);
		objExp.setAsunto(proceso);
		objExp.setFechacreacion(new Date());
		Expediente returnExp = expedientedao.saveExpediente(objExp);
		/***********************************************************************************************************/
		// CREAR EL EXPEDIENTE EN ALFRESCO
		/***********************************************************************************************************/
		//if (!repositorioService.crearEstructuraExpediente(usuario.getUsuario(),usuario.getClave(),returnExp.getIdexpediente()))
		//{
		//	throw new RuntimeException();
		//}
		/***********************************************************************************************************/
		// REGISTRAR DOCUMENTOS
		/***********************************************************************************************************/
		Tipodocumento tipo = tipodocumentodao.findByNombre(Constantes.TIPO_DOCUMENTO_TICKET);
		Documento ticket = new Documento();
		//ticket.setProceso(p);
		ticket.setAsunto(proceso);
		ticket.setEstado(Constantes.ESTADO_INACTIVO);
		ticket.setExpediente(returnExp);
		ticket.setFechaAccion(new Date());
		ticket.setFechaCreacion(new Date());
		ticket.setNumeroDocumento(Constantes.SIN_NUMERO);
		ticket.setNumeroFolios(0);
		ticket.setPrincipal(Constantes.DOCUMENTO_PRINCIPAL);
		ticket.setPropietario(usuario);
		ticket.setPlazo(p.getTiempoatencion());
		ticket.setEstadoplazo(Constantes.DOCUMENTO_DENTRO_PLAZO);
		ticket.setTipoDocumento(tipo);
		ticket.setFirmante(usuario);
		Accion accion=accionService.findByNombre(Constantes.ACCION_REGISTRAR);
		ticket.setAccion(accion);
		
		Documento ticketTemp= documentodao.saveDocumento(ticket);		
		
		return returnExp.getIdexpediente();
	}


	public ExpedienteDAO getExpedientedao() {
		return expedientedao;
	}

	public void setExpedientedao(ExpedienteDAO expedientedao) {
		this.expedientedao = expedientedao;
	}

	public ExpedienteService getExpedienteService() {
		return expedienteService;
	}

	public void setExpedienteService(ExpedienteService expedienteService) {
		this.expedienteService = expedienteService;
	}

	public ProcesoDAO getProcesoDAO() {
		return procesoDAO;
	}

	public void setProcesoDAO(ProcesoDAO procesoDAO) {
		this.procesoDAO = procesoDAO;
	}

	public RepositorioService getRepositorioService() {
		return repositorioService;
	}

	public void setRepositorioService(RepositorioService repositorioService) {
		this.repositorioService = repositorioService;
	}

	public TipodocumentoDAO getTipodocumentodao() {
		return tipodocumentodao;
	}

	public void setTipodocumentodao(TipodocumentoDAO tipodocumentodao) {
		this.tipodocumentodao = tipodocumentodao;
	}

	public DocumentoDAO getDocumentodao() {
		return documentodao;
	}

	public void setDocumentodao(DocumentoDAO documentodao) {
		this.documentodao = documentodao;
	}

	public AccionService getAccionService() {
		return accionService;
	}

	public void setAccionService(AccionService accionService) {
		this.accionService = accionService;
	}

	public ClienteService getClienteService() {
		return clienteService;
	}

	public void setClienteService(ClienteService clienteService) {
		this.clienteService = clienteService;
	}

    public void setSeguridadService(SeguridadService seguridadService) {
        this.seguridadService = seguridadService;
    }
	
}
