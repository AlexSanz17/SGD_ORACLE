package org.ositran.services;

import java.util.List;
import org.apache.log4j.Logger;

import org.ositran.daos.ExpedientesConcluidosStorDAO;
import org.ositran.daos.ExpedientesIngresadosStorDAO;
import org.ositran.daos.ExpedientesPendientesStorDAO;

import com.btg.ositran.siged.domain.ExpedientesConcluidosStor;
import com.btg.ositran.siged.domain.ExpedientesIngresadosStor;
import com.btg.ositran.siged.domain.ExpedientesPendientesStor;

public class ReporteSigedSTORServiceImpl implements ReporteSigedSTORService {
	private ExpedientesPendientesStorDAO daoPendientes;
	private ExpedientesIngresadosStorDAO daoIngresados;
	private ExpedientesConcluidosStorDAO daoConcluidos;
	private String fechaDesde;
	private String fechaHasta;
	private static Logger log  =  Logger.getLogger(ReporteSigedSTORServiceImpl.class);
	
	public ReporteSigedSTORServiceImpl(ExpedientesPendientesStorDAO daoPendientes, ExpedientesIngresadosStorDAO daoIngresados,
										ExpedientesConcluidosStorDAO daoConcluidos){
		this.daoPendientes=daoPendientes;
		this.daoIngresados=daoIngresados;
		this.daoConcluidos=daoConcluidos;
	}
	
	@Override
	public List<ExpedientesPendientesStor> getTODOPendientes() {
		return daoPendientes.listarTodo();
	}
	@Override
	public List<ExpedientesPendientesStor> getListaPendientes(String fechaDesde, String fechaHasta){
		if((fechaDesde==null)||(fechaDesde.isEmpty())){
			this.fechaDesde="";
		}else{
			this.fechaDesde=fechaDesde;
		}
		if((fechaHasta==null)||(fechaHasta.isEmpty())){
			this.fechaHasta="";
		}else{
			this.fechaHasta=fechaHasta;
		}
		return daoPendientes.generarLista(this.fechaDesde, this.fechaHasta);
	}
	
	@Override
	public List<ExpedientesIngresadosStor> getTODOIngresados() {
		return daoIngresados.listarTodo();
	}
	
	@Override
	public List<ExpedientesIngresadosStor> getListaIngresados(String fechaDesde, String fechaHasta){
		if((fechaDesde==null)||(fechaDesde.isEmpty())){
			this.fechaDesde="";
		}else{
			this.fechaDesde=fechaDesde;
		}
		if((fechaHasta==null)||(fechaHasta.isEmpty())){
			this.fechaHasta="";
		}else{
			this.fechaHasta=fechaHasta;
		}
		return daoIngresados.generarLista(this.fechaDesde, this.fechaHasta);
	}
	
	@Override
	public List<ExpedientesConcluidosStor> getTODOConcluidos() {
		return daoConcluidos.listarTodo();
	}
	
	@Override
	public List<ExpedientesConcluidosStor> getListaConcluidos(String fechaDesde, String fechaHasta){
		if((fechaDesde==null)||(fechaDesde.isEmpty())){
			this.fechaDesde="";
		}else{
			this.fechaDesde=fechaDesde;
		}
		if((fechaHasta==null)||(fechaHasta.isEmpty())){
			this.fechaHasta="";
		}else{
			this.fechaHasta=fechaHasta;
		}
		return daoConcluidos.generarLista(this.fechaDesde, this.fechaHasta);
	}
	
	public ExpedientesPendientesStorDAO getDaoPendientes() {
		return daoPendientes;
	}

	public void setDaoPendientes(ExpedientesPendientesStorDAO daoPendientes) {
		this.daoPendientes = daoPendientes;
	}

	public ExpedientesIngresadosStorDAO getDaoIngresados() {
		return daoIngresados;
	}

	public void setDaoIngresados(ExpedientesIngresadosStorDAO daoIngresados) {
		this.daoIngresados = daoIngresados;
	}

	public ExpedientesConcluidosStorDAO getDaoConcluidos() {
		return daoConcluidos;
	}

	public void setDaoConcluidos(ExpedientesConcluidosStorDAO daoConcluidos) {
		this.daoConcluidos = daoConcluidos;
	}
	

}
