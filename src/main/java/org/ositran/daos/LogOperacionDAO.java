/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;

import com.btg.ositran.siged.domain.LogOperacion;

public interface LogOperacionDAO {
	public LogOperacion saveLogOperacion(LogOperacion logOperacion);
	public List<LogOperacion> findLogOperacion(String usuario, String tipoDocumento, String nroDocumento);
}
