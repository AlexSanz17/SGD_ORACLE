package org.ositran.services;


import org.ositran.daos.LogBusquedaAvanzadaDAO;
import org.ositran.dojo.BusquedaAvanzada;
import org.springframework.transaction.annotation.Transactional;

import com.btg.ositran.siged.domain.LogBusquedaAvanzada;


public class LogBusquedaAvanzadaServiceImpl  implements LogBusquedaAvanzadaService{
	private LogBusquedaAvanzadaDAO logBusquedaAvanzadaDAO;

	public LogBusquedaAvanzadaDAO getLogBusquedaAvanzadaDAO() {
		return logBusquedaAvanzadaDAO;
	}


	public void setLogBusquedaAvanzadaDAO(
			LogBusquedaAvanzadaDAO logBusquedaAvanzadaDAO) {
		this.logBusquedaAvanzadaDAO = logBusquedaAvanzadaDAO;
	}



	@Transactional
	public LogBusquedaAvanzada saveLogBusquedaAvanzada(LogBusquedaAvanzada logBusquedaAvanzada){
		return logBusquedaAvanzadaDAO.saveLogBusquedaAvanzada(logBusquedaAvanzada);
	}
}
