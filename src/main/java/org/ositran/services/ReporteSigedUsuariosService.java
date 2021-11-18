package org.ositran.services;

import java.util.List;

import com.btg.ositran.siged.domain.ExpedientesPendientesAYQ;

public interface ReporteSigedUsuariosService {
	/**********PARTES DEL REPORTE AYQ************/
	public abstract List<ExpedientesPendientesAYQ> listarTodoAYQ();
	public abstract List<ExpedientesPendientesAYQ> generarListaAYQ(String tipoExpediente, String fechaDesde, String fechaHasta, String sala, String responsable, String analista, String vencimientoDesde, String vencimientoHasta);
	/********************************************/
}
