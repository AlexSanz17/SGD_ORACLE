/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;

import com.btg.ositran.siged.domain.ExpedientesConcluidosStor;

public interface ExpedientesConcluidosStorDAO {

	public abstract List<ExpedientesConcluidosStor> listarTodo();

	public abstract List<ExpedientesConcluidosStor> generarLista(String fechaDesde, String fechaHasta);
}
