package org.ositran.services;

import com.btg.ositran.siged.domain.Procedimiento;

public interface ProcedimientoService {
	
	public Object[] getProcedimientos(String idUnidad);

	public Procedimiento getProcedimientoById(Integer procedimientos);
}
