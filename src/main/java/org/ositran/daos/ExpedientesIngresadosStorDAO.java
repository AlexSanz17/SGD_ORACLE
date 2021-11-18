/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;

import com.btg.ositran.siged.domain.ExpedientesIngresadosStor;

public interface ExpedientesIngresadosStorDAO {

	public abstract List<ExpedientesIngresadosStor> listarTodo();
	
	public abstract List<ExpedientesIngresadosStor> generarLista(String fechaDesde, String fechaHasta);
}
