/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;

import com.btg.ositran.siged.domain.ExpedientesPendientesAYQ;

public interface ExpedientesPendientesAYQDAO {
	public abstract List<ExpedientesPendientesAYQ> listarTodo();
	public abstract List<ExpedientesPendientesAYQ> generarListaAYQ(String tipoExpediente, String fechaDesde, String fechaHasta, String sala, String responsable, String analista, String vencimientoDesde, String vencimientoHasta);
}
