package org.ositran.services;


import java.util.List;

import org.apache.log4j.Logger;
import org.ositran.daos.LogOperacionDAO;
import org.springframework.transaction.annotation.Transactional;

import com.btg.ositran.siged.domain.LogOperacion;



public class LogOperacionServiceImpl  implements LogOperacionService{
	private LogOperacionDAO logOperacionDAO;
	private static Logger log = Logger.getLogger(LogOperacionServiceImpl.class);



	public LogOperacionDAO getLogOperacionDAO() {
		return logOperacionDAO;
	}



	public void setLogOperacionDAO(LogOperacionDAO logOperacionDAO) {
		this.logOperacionDAO = logOperacionDAO;
	}


	@Transactional
	public LogOperacion saveLogOperacion(LogOperacion logOperacion){
		return logOperacionDAO.saveLogOperacion(logOperacion);
	}

	public List<LogOperacion> findLogOperacion(String usuario, String tipoDocumento, String nroDocumento){
		return logOperacionDAO.findLogOperacion(usuario, tipoDocumento, nroDocumento);
	}
}
