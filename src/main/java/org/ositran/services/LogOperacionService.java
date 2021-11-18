package org.ositran.services;


import java.util.List;

import com.btg.ositran.siged.domain.LogOperacion;


public interface LogOperacionService {
	public LogOperacion saveLogOperacion(LogOperacion logOperacion);
	public List<LogOperacion> findLogOperacion(String usuario, String tipoDocumento, String nroDocumento);
}
