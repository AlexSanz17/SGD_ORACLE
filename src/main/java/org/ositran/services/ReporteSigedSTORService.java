package org.ositran.services;

import java.util.List;

import com.btg.ositran.siged.domain.ExpedientesConcluidosStor;
import com.btg.ositran.siged.domain.ExpedientesIngresadosStor;
import com.btg.ositran.siged.domain.ExpedientesPendientesStor;

public interface ReporteSigedSTORService {

	public List<ExpedientesPendientesStor> getTODOPendientes();
	
	public List<ExpedientesIngresadosStor> getTODOIngresados();
	
	public List<ExpedientesConcluidosStor> getTODOConcluidos();
	
	public List<ExpedientesPendientesStor> getListaPendientes(String fechaDesde, String fechaHasta);
	
	public List<ExpedientesIngresadosStor> getListaIngresados(String fechaDesde, String fechaHasta);
	
	public List<ExpedientesConcluidosStor> getListaConcluidos(String fechaDesde, String fechaHasta);
}
