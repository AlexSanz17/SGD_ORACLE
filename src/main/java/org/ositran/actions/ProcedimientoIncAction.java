/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.ositran.services.ProcedimientoIncumplidosService;

import com.btg.ositran.siged.domain.Procedimientoincumplido;
import com.opensymphony.xwork2.Action;
import org.apache.log4j.Logger;

public class ProcedimientoIncAction {
	//private static Logger log = Logger.getLogger("org.ositran.actions.ProcedimientoIncAction");
	
	private HttpServletRequest request;
	private List<Procedimientoincumplido> procedimientoIncList;
	private ProcedimientoIncumplidosService procedimientoIncumplidosService;
	private String nombre;
	private String descripcion;
	private Character estado;
	private Boolean flag;
	private Boolean flagInd;
	private String idP;
        private static Logger log = Logger.getLogger(ProcedimientoIncAction.class);
	
	public Boolean getFlagInd(){
		return flagInd;
	}

	public void setFlagInd(Boolean flagInd) {
		this.flagInd = flagInd;
	}

	private Procedimientoincumplido procedimientoincumplido;
	
	public Procedimientoincumplido getProcedimientoincumplido() {
		return procedimientoincumplido;
	}

	public void setProcedimientoincumplido(
			Procedimientoincumplido procedimientoincumplido) {
		this.procedimientoincumplido = procedimientoincumplido;
	}

	public String getIdP() {
		return idP;
	}

	public void setIdP(String idP) {
		this.idP = idP;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Character getEstado() {
		return estado;
	}

	public void setEstado(Character estado) {
		this.estado = estado;
	}

	public List<Procedimientoincumplido> getProcedimientoIncList() {
		return procedimientoIncList;
	}

	public void setProcedimientoIncList(
			List<Procedimientoincumplido> procedimientoIncList) {
		this.procedimientoIncList = procedimientoIncList;
	}
	
	public ProcedimientoIncumplidosService getProcedimientoIncumplidosService() {
		return procedimientoIncumplidosService;
	}

	public void setProcedimientoIncumplidosService(
			ProcedimientoIncumplidosService procedimientoIncumplidosService) {
		this.procedimientoIncumplidosService = procedimientoIncumplidosService;
	}

	public String inicio()
	throws Exception{
		
		inicializa();
		procedimientoIncList = procedimientoIncumplidosService.findAll();
		
		return Action.SUCCESS;
	}
	
	private void inicializa() {
		this.nombre = null;
		this.descripcion = null;
		this.estado =  null;
		this.flag = null;
		this.flagInd = null;
	}

	public String nuevo()
	throws Exception{
		
		flagInd = Boolean.TRUE;
		
		return Action.SUCCESS;
	}
	
	public String grabar()
	throws Exception{
		
		try {
			
			Procedimientoincumplido procedimientoincumplido = new Procedimientoincumplido();
			
			procedimientoincumplido.setNombre(nombre);
			procedimientoincumplido.setDescripcion(descripcion);
			procedimientoincumplido.setEstado(estado);
			
			procedimientoIncumplidosService.saveObject(procedimientoincumplido);
			flag =  Boolean.TRUE;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			flag =  Boolean.FALSE;
		}
		
		return Action.SUCCESS;
	}	
	
	public String buscar()
	throws Exception{
		
		try {
			procedimientoIncList = procedimientoIncumplidosService.getProcedimientoIncByFiltro(nombre,descripcion,estado);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return Action.SUCCESS;
	}
	
	public String editar()
	throws Exception{
		
		procedimientoincumplido =  procedimientoIncumplidosService.getProcedimientoById(idP);
		
		flagInd = Boolean.FALSE;
		
		return Action.SUCCESS;
	}
	
	public String actualizar()
	throws Exception{
		try {
			
			procedimientoincumplido =  procedimientoIncumplidosService.getProcedimientoById(idP);
			
			procedimientoincumplido.setNombre(nombre);
			procedimientoincumplido.setDescripcion(descripcion);
			procedimientoincumplido.setEstado(estado);
			
			procedimientoIncumplidosService.updateObject(procedimientoincumplido);
			
			flag =  Boolean.TRUE;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			flag =  Boolean.FALSE;
		}
		return Action.SUCCESS;
	}
	
	public String eliminar()
	throws Exception{
		
		try {
			
			request = ServletActionContext.getRequest();
			
			String[] ids = request.getParameterValues("sel");
			
			for(int i=0 ; i<ids.length;i++){
				procedimientoIncumplidosService.deleteObject(procedimientoIncumplidosService.getProcedimientoById(ids[i]));
			}
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return buscar();
	}
}