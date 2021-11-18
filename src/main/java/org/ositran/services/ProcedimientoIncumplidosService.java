package org.ositran.services;

import java.util.List;

import com.btg.ositran.siged.domain.Procedimientoincumplido;

public interface ProcedimientoIncumplidosService {
	public List<Procedimientoincumplido> findAll();

	public void saveObject(Procedimientoincumplido procedimientoincumplido);

	public List<Procedimientoincumplido> getProcedimientoIncByFiltro(String nombre, String descripcion, Character estado);

	public Procedimientoincumplido getProcedimientoById(String idP);

	public void updateObject(Procedimientoincumplido procedimientoincumplido);

	public void deleteObject(Procedimientoincumplido procedimientoById);
}
