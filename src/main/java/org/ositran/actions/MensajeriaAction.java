/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.actions;

import com.btg.ositran.siged.domain.Datos;
import com.btg.ositran.siged.domain.Documento;
import com.btg.ositran.siged.domain.Empresadestino;
import com.btg.ositran.siged.domain.Mensajeria;
import com.btg.ositran.siged.domain.Tipoenvio;
import com.opensymphony.xwork2.Action;
import java.util.List; 
import java.util.Map;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.ositran.services.DatosService;
import org.ositran.services.DocumentoService;
import org.ositran.services.EmpresadestinoService;
import org.ositran.services.MensajeriaService;
import org.ositran.services.TipoenvioService;
import org.ositran.utils.DocUsuario;
import org.ositran.utils.MensajeriaDatos;
import org.ositran.utils.UsuarioMensajeria;

public class MensajeriaAction implements SessionAware {

	private static Logger _log = Logger.getLogger(MensajeriaAction.class);
	private List<Mensajeria> listMs;
	private Mensajeria ObMsj;
	private String estado;
	private DatosService SvrDt;
	private MensajeriaDatos Objmjda;
	private TipoenvioService SvrTe;
	private EmpresadestinoService SvrEd;
	private List<UsuarioMensajeria> listUsuMsj;
	private MensajeriaService SvrMsj;
	private DocUsuario objdocusu;
	private String idusuario;
	private Documento objDocumento;
	private DocumentoService srvDocumento;
	private Integer iDireccionCliente;
	private Integer iIdDocumento;
	private Map<String,Object> mapSession;
	private Mensajeria objMensajeria;
	private Integer iIdExpediente;

	private List<Documento> lstDocumento;

	//////////////////////////////////
	// Constructors                 //
	//////////////////////////////////
	public MensajeriaAction(DatosService SvrDt, TipoenvioService SvrTe, EmpresadestinoService SvrEd, MensajeriaService SvrMsj) {
		setSvrDt(SvrDt);
		setSvrTe(SvrTe);
		setSvrEd(SvrEd);
		setSvrMsj(SvrMsj);
	}

	//////////////////////////////////
	// Methods                      //
	//////////////////////////////////
	public String findmensajeria() throws Exception {
		_log.debug("-> [Action] MensajeriaAction - findmensajeria():String ");
		setEstado(getEstado());

		return "LtUsumsn";
	}

	public String findIdMsj() throws Exception {
		_log.debug("-> [Action] MensajeriaAction - findIdMsj():String ");
		int id;
		//int iduser;
		String idx;
		//String iduserx;
		Mensajeria msj = new Mensajeria();
		Datos ObjDatos = new Datos();
		Tipoenvio Objtien = new Tipoenvio();
		Empresadestino Objemdes = new Empresadestino();
		MensajeriaDatos Obmgda = new MensajeriaDatos();

		idx = ServletActionContext.getRequest().getParameter("Idmen");


		id = Integer.parseInt(idx);
		msj = getSvrMsj().ViewMensaje(id);

		try {
			if (msj.getEstadoingreso() == '0') {

				setObMsj(msj);
				return "Objmensj";
			} else if (msj.getEstadoingreso() == '1') {
				setObMsj(msj);
				return "Msjdoc";
			} else if (msj.getEstadoingreso() == '2') {
				setObMsj(msj);
				return "Msjdts";
			} else if (msj.getEstado() == 'C') {
				ObjDatos = getSvrDt().findId(id);
				Objtien = getSvrTe().findbyId(msj.getIdtipoenvio().getIdtipoenvio());
				Objemdes = getSvrEd().findkey(ObjDatos.getIdempresadestino().getIdempresadestino());
				Obmgda.setIdmen(ObjDatos.getIdmensajeria());
				Obmgda.setTipocumento(msj.getTipodocumento());
				Obmgda.setNumerodoc(msj.getNumerodocumento());
				Obmgda.setDestinatario(msj.getDestinatario());
				Obmgda.setAsunto(msj.getAsunto());
				Obmgda.setFechaderiva(msj.getFechaderivacion());
				Obmgda.setNuminterno(ObjDatos.getNumerointerno());
				Obmgda.setUsudestino(ObjDatos.getUsuariodestino());
				Obmgda.setTiev2(Objtien.getTipoenvio2());
				Obmgda.setDirec(ObjDatos.getDireccion());
				Obmgda.setDepartamento(ObjDatos.getDepartamento());
				Obmgda.setProvincia(ObjDatos.getProvincia());
				Obmgda.setDistrito(ObjDatos.getDistrito());
				Obmgda.setReferencia(ObjDatos.getReferencia());
				Obmgda.setEmpdescod(Objemdes.getCodigo());
				Obmgda.setEmpdesnom(Objemdes.getNombre());

				setObjmjda(Obmgda);
				return "Msjdatos";
			}

			return "Msjria";

		} catch (Exception e) {
			_log.error(e.getMessage(), e);

			return Action.ERROR;
		}

	}

	public String findusuariomensajeria() throws Exception {
		_log.debug("-> [Action] MensajeriaAction - findusuariomensajeria():String ");
		if (getIdusuario() == null) {
			setEstado(getEstado());

			return "LtUsumsn";
		}
		//int idu = Integer.parseInt(getIdusuario());
		setIdusuario(getIdusuario());
		return "LtUsumsn";
	}

	public String abrirEnvio() throws Exception {
		_log.debug("-> [Action] MensajeriaAction - abrirEnvio():String ");
		
		if (iIdExpediente == null) {
			_log.error("No se recibio ID de Expediente");

			return Action.ERROR;
		}

		try {
			objDocumento = srvDocumento.findDocumentoPrincipal(iIdExpediente);

			if (_log.isDebugEnabled()) {
				_log.debug("ID de Expediente recibido [" + iIdExpediente + "]");
				_log.debug("Documento principal [" + objDocumento.getIdDocumento() + "]");
				_log.debug("ID cliente [" + objDocumento.getExpediente().getCliente().getIdCliente() + "]");
			}
			//this.objDD=new DocumentoDetail();	
			//this.objDD.setsNroExpediente(expedienteService.getMaxReferencia());	
			//int idExpedienteOrigen=Integer.parseInt(ServletActionContext.getRequest().getParameter("idExpediente").toString());
			this.lstDocumento=srvDocumento.getDocumentosPorExpediente(iIdExpediente);
			//String idProceso= ServletActionContext.getRequest().getParameter("idProceso").toString();
			//this.objDD.setIIdProceso(Integer.decode(idProceso));


			return "winEnvio";
		} catch (Exception e) {
			_log.error(e.getMessage(), e);

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
	public void setListMs(List<Mensajeria> listMs) {
		this.listMs = listMs;
	}

	public List<Mensajeria> getListMs() {
		return listMs;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getEstado() {
		return estado;
	}

	public void setObMsj(Mensajeria obMsj) {
		ObMsj = obMsj;
	}

	public Mensajeria getObMsj() {
		return ObMsj;
	}

	public void setSvrDt(DatosService svrDt) {
		SvrDt = svrDt;
	}

	public DatosService getSvrDt() {
		return SvrDt;
	}

	public void setObjmjda(MensajeriaDatos objmjda) {
		Objmjda = objmjda;
	}

	public MensajeriaDatos getObjmjda() {
		return Objmjda;
	}

	public void setSvrTe(TipoenvioService svrTe) {
		SvrTe = svrTe;
	}

	public TipoenvioService getSvrTe() {
		return SvrTe;
	}

	public void setSvrEd(EmpresadestinoService svrEd) {
		SvrEd = svrEd;
	}

	public EmpresadestinoService getSvrEd() {
		return SvrEd;
	}

	public void setListUsuMsj(List<UsuarioMensajeria> listUsuMsj) {
		this.listUsuMsj = listUsuMsj;
	}

	public List<UsuarioMensajeria> getListUsuMsj() {
		return listUsuMsj;
	}

	public void setSvrMsj(MensajeriaService svrMsj) {
		SvrMsj = svrMsj;
	}

	public MensajeriaService getSvrMsj() {
		return SvrMsj;
	}

	public void setObjdocusu(DocUsuario objdocusu) {
		this.objdocusu = objdocusu;
	}

	public DocUsuario getObjdocusu() {
		return objdocusu;
	}

	public void setIdusuario(String idusuario) {
		this.idusuario = idusuario;
	}

	public String getIdusuario() {
		return idusuario;
	}

	public Documento getObjDocumento() {
		return objDocumento;
	}

	public void setObjDocumento(Documento objDocumento) {
		this.objDocumento = objDocumento;
	}

	public DocumentoService getSrvDocumento() {
		return srvDocumento;
	}

	public void setSrvDocumento(DocumentoService srvDocumento) {
		this.srvDocumento = srvDocumento;
	}

	public Integer getIDireccionCliente() {
		return iDireccionCliente;
	}

	public void setIDireccionCliente(Integer iDireccionCliente) {
		this.iDireccionCliente = iDireccionCliente;
	}

	public Integer getIIdDocumento() {
		return iIdDocumento;
	}

	public void setIIdDocumento(Integer iIdDocumento) {
		this.iIdDocumento = iIdDocumento;
	}

	public Map<String,Object> getMapSession() {
		return mapSession;
	}

	public void setMapSession(Map<String,Object> mapSession) {
		this.mapSession = mapSession;
	}

	public Mensajeria getObjMensajeria() {
		return objMensajeria;
	}

	public void setObjMensajeria(Mensajeria objMensajeria) {
		this.objMensajeria = objMensajeria;
	}

	public Integer getiIdExpediente() {
		return iIdExpediente;
	}

	public void setiIdExpediente(Integer iIdExpediente) {
		this.iIdExpediente = iIdExpediente;
	}


	public List<Documento> getLstDocumento() {
		return lstDocumento;
	}

	public void setLstDocumento(List<Documento> lstDocumento) {
		this.lstDocumento = lstDocumento;
	}


}
