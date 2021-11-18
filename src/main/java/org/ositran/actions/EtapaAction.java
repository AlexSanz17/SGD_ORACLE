/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.actions;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.ositran.services.EtapaService;

import com.btg.ositran.siged.domain.Etapa;

public class EtapaAction {
	
	private static Logger log = Logger.getLogger(EtapaAction.class);
	
	private EtapaService etapaService;
	
	private List<Etapa> etapaList;
	private String descripcion;
	private Integer duracion;
	private String codigo;
	private String estado;
	private String idP;
	private Etapa etapa;

	private Boolean flagInd;

	private Boolean flag;
	
	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	public Etapa getEtapa() {
		return etapa;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public void setEtapa(Etapa etapa) {
		this.etapa = etapa;
	}

	public Boolean getFlagInd() {
		return flagInd;
	}

	public void setFlagInd(Boolean flagInd) {
		this.flagInd = flagInd;
	}

	public String getIdP() {
		return idP;
	}

	public void setIdP(String idP) {
		this.idP = idP;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getDuracion() {
		return duracion;
	}

	public void setDuracion(Integer duracion) {
		this.duracion = duracion;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public List<Etapa> getEtapaList() {
		return etapaList;
	}

	public void setEtapaList(List<Etapa> etapaList) {
		this.etapaList = etapaList;
	}

	public EtapaService getEtapaService() {
		return etapaService;
	}

	public void setEtapaService(EtapaService etapaService) {
		this.etapaService = etapaService;
	}

	public String inicio()
	throws Exception{
		
		log.debug("EtapaAction :: inicio");
		
		this.etapaList = etapaService.findAll();
		
		return "search";
	}
	
	public String editar()
	throws Exception{
		
		log.debug("EtapaAction :: editar");
		
		this.etapa = etapaService.findByIdetapa(Integer.parseInt(this.idP));
		
		this.flagInd = Boolean.FALSE;
		
		return "detail";
	}
	
	public String buscar()
	throws Exception{
		
		log.debug("EtapaAction :: buscar");
		
		this.etapaList = etapaService.getEtapaList(this.descripcion,this.duracion,this.estado,this.codigo);
		this.estado = ServletActionContext.getRequest().getParameter("estado");
		log.debug("this.estado: "+this.estado);
		return "search";
	}
	
	public String nuevo()
	throws Exception{
		
		log.debug("EtapaAction :: nuevo");
		
		this.flagInd = Boolean.TRUE;
		
		return "detail";
	}
	
	public String grabar()
	throws Exception{
		
		log.debug("EtapaAction :: grabar");
		
		try {
			
			Etapa etapa = new Etapa();
			
			etapa.setDescripcion(this.descripcion);
			etapa.setEstado(this.estado.charAt(0));
			etapa.setCodigo(this.codigo);
			etapa.setDuracion(this.duracion);
			
			etapaService.saveObject(etapa);
			
			this.flag =  Boolean.TRUE;
			
		} catch (Exception e) {
                        log.error(e.getMessage(),e);
			this.flag =  Boolean.FALSE;
		}
		
		return "detail";
	}
	
	public String actualizar()
	throws Exception{
		
		log.debug("EtapaAction :: actualizar");
		
		try {
			
			Etapa etapa = etapaService.findByIdetapa(Integer.parseInt(this.idP));
			
			etapa.setCodigo(this.codigo);
			etapa.setDescripcion(this.descripcion);
			etapa.setDuracion(this.duracion);
			etapa.setEstado(this.estado.charAt(0));
			
			etapaService.saveObject(etapa);
			
			flag =  Boolean.TRUE;
			
		} catch (Exception e) {
                        log.error(e.getMessage(),e);
			this.flag =  Boolean.FALSE;
		}
		
		return "detail";
	}
}