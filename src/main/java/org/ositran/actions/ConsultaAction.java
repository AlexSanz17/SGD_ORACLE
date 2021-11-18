/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.actions;



import java.util.List;
import java.util.Map;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.json.annotations.SMDMethod;
import org.ositran.dojo.BusquedaAvanzada;
import org.ositran.dojo.Consulta;
import org.ositran.dojo.ObjetoJSON;
import org.ositran.dojo.grid.Item;

import org.ositran.services.ConsultaService;
import org.ositran.services.UsuarioService;
import org.ositran.utils.Constantes;

import com.btg.ositran.siged.domain.ConsultaAPN3;
import com.btg.ositran.siged.domain.Usuario;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

public class ConsultaAction {
	private String accion;
	private ConsultaService consultaService;
	private List<Item> lstConsultaAPN3Grid;
	private List<Item> lstConsultaAPN4Grid;
	private ConsultaAPN3 consultaAPN3;
	private static Log log = LogFactory.getLog(ConsultaAction.class);
	private String tipoDocumento;
	private String areaOrigen;
	private String fechaDesde;
	private String fechaHasta;
	private String nroDocumento;
	private String asuntoDocumento;
	private Map<String, Object> mapSession;
	private UsuarioService usuarioService;

	@SMDMethod
	public ObjetoJSON consultaDocumentoRecepcionados(Consulta objFiltro,String arrFecha[] ){
		log.debug("-> [Action] ConsultaAction - consultaDocumentoRecepcionados():ObjetoJSON ");
		mapSession = ActionContext.getContext().getSession();
		Usuario usuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
		usuario = usuarioService.findByIdUsuario(usuario.getIdusuario());
		Integer idAreaDestino = usuario.getUnidad().getIdunidad();
		log.debug("ID AREA QUE RECEPCIONO EL DOCUMENTO : "+idAreaDestino);
		ObjetoJSON objJSON = new ObjetoJSON();
        objJSON.setItems(consultaService.getItemsDocumentosRecepcionados(objFiltro,arrFecha,idAreaDestino));

		return objJSON;

	}

	public String imprimirGridConsulta3(){

		Consulta objFiltro = new Consulta();
		String arrFecha[]= new String[2];
		mapSession = ActionContext.getContext().getSession();
		Usuario usuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);
		usuario = usuarioService.findByIdUsuario(usuario.getIdusuario());
		Integer idAreaDestino = usuario.getUnidad().getIdunidad();

		arrFecha[0] = fechaDesde;
		arrFecha[1] = fechaHasta;
		objFiltro.setTipoDocumento(tipoDocumento);
		objFiltro.setAreaOrigen(areaOrigen);
		objFiltro.setNroDocumento(nroDocumento);
		objFiltro.setAsuntoDocumento(asuntoDocumento);
		objFiltro.setAccion(accion);

		lstConsultaAPN3Grid = consultaService.getItemsDocumentosRecepcionados(objFiltro,arrFecha,idAreaDestino );

		return "imprimirC3";
	}

	@SMDMethod
	public ObjetoJSON consultaDocumentosEmitidos(){
		log.debug("VER consultaDocumentosEmitidos()");
		Usuario objUsuario = null;
		Map<String, Object> session = ActionContext.getContext().getSession();
		objUsuario = (Usuario) session.get(Constantes.SESSION_USUARIO);
		Usuario usuario = new Usuario();
		usuario = usuarioService.findByUsuario(objUsuario.getUsuario());

		Integer remitente = usuario.getIdusuario();
		log.debug("ID AREA QUE EMITIO DOCUMENTO : "+remitente);
		ObjetoJSON objJSON = new ObjetoJSON();
        objJSON.setItems(consultaService.getItemsDocumentosEmitidos(remitente));

		return objJSON;

	}

	public String imprimirGridConsulta4(){


		Usuario objUsuario = null;
		Map<String, Object> session = ActionContext.getContext().getSession();
		objUsuario = (Usuario) session.get(Constantes.SESSION_USUARIO);
		Usuario usuario = new Usuario();
		usuario = usuarioService.findByUsuario(objUsuario.getUsuario());

		Integer remitente = usuario.getIdusuario();

		lstConsultaAPN4Grid = consultaService.getItemsDocumentosEmitidos(remitente);

		return "imprimirC4";
	}

	@SMDMethod
	public ObjetoJSON consultaMisExpedientes(BusquedaAvanzada objFiltro){
		log.debug("-> [Action] ConsultaAction - consultaMisExpedientes():ObjetoJSON ");
		mapSession = ActionContext.getContext().getSession();
		Usuario usuario = (Usuario) mapSession.get(Constantes.SESSION_USUARIO);

		ObjetoJSON objJSON = new ObjetoJSON();
                objJSON.setItems(consultaService.getItemsMisExpedientes(usuario.getIdusuario(), objFiltro));

		return objJSON;

	}

	public String consultaAPN3(){
		return "consultaAPN3";
	}

	public String consultaAPN4(){
		return "consultaAPN4";
	}

	public String misExpedientes(){
		return "misExpedientes";
	}

	public ConsultaService getConsultaService() {
		return consultaService;
	}

	public void setConsultaService(ConsultaService consultaService) {
		this.consultaService = consultaService;
	}
	public String execute() throws Exception {
		//log.debug("-> [Action] DojoAction - execute():String ");
		return Action.SUCCESS;
	}
	public static Log getLog() {
		return log;
	}
	public static void setLog(Log log) {
		ConsultaAction.log = log;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getAreaOrigen() {
		return areaOrigen;
	}

	public void setAreaOrigen(String areaOrigen) {
		this.areaOrigen = areaOrigen;
	}

	public String getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(String fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	public String getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(String fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

	public String getNroDocumento() {
		return nroDocumento;
	}

	public void setNroDocumento(String nroDocumento) {
		this.nroDocumento = nroDocumento;
	}

	public String getAsuntoDocumento() {
		return asuntoDocumento;
	}

	public void setAsuntoDocumento(String asuntoDocumento) {
		this.asuntoDocumento = asuntoDocumento;
	}

	public List<Item> getLstConsultaAPN3Grid() {
		return lstConsultaAPN3Grid;
	}

	public void setLstConsultaAPN3Grid(List<Item> lstConsultaAPN3Grid) {
		this.lstConsultaAPN3Grid = lstConsultaAPN3Grid;
	}

	public List<Item> getLstConsultaAPN4Grid() {
		return lstConsultaAPN4Grid;
	}

	public void setLstConsultaAPN4Grid(List<Item> lstConsultaAPN4Grid) {
		this.lstConsultaAPN4Grid = lstConsultaAPN4Grid;
	}

	public ConsultaAPN3 getConsultaAPN3() {
		return consultaAPN3;
	}

	public void setConsultaAPN3(ConsultaAPN3 consultaAPN3) {
		this.consultaAPN3 = consultaAPN3;
	}

	public Map<String, Object> getMapSession() {
		return mapSession;
	}

	public void setMapSession(Map<String, Object> mapSession) {
		this.mapSession = mapSession;
	}

	public UsuarioService getUsuarioService() {
		return usuarioService;
	}

	public void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}


}
