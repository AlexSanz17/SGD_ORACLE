/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;

import com.btg.ositran.siged.domain.Procedimiento;
import com.btg.ositran.siged.domain.Unidadxprocedimiento;

public interface ProcedimientoDAO {

	public List<Procedimiento> getProcedimientos(List<Unidadxprocedimiento> procedimientos);

	public Procedimiento getProcedimientoById(Integer procedimientos);

}
