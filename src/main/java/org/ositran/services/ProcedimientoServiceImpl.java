package org.ositran.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.ositran.daos.ProcedimientoDAO;
import org.ositran.daos.UnidadxprocedimientoDAO;

import com.btg.ositran.siged.domain.Procedimiento;
import com.btg.ositran.siged.domain.Unidadxprocedimiento;

public class ProcedimientoServiceImpl implements ProcedimientoService{
	
	private static Logger log = Logger.getLogger(ProcedimientoServiceImpl.class);
	
	private ProcedimientoDAO procedimientoDAO;
	private UnidadxprocedimientoDAO unidadxprocedimientoDAO;
	
	public UnidadxprocedimientoDAO getUnidadxprocedimientoDAO() {
		return unidadxprocedimientoDAO;
	}

	public void setUnidadxprocedimientoDAO(
			UnidadxprocedimientoDAO unidadxprocedimientoDAO) {
		this.unidadxprocedimientoDAO = unidadxprocedimientoDAO;
	}

	public ProcedimientoDAO getProcedimientoDAO() {
		return procedimientoDAO;
	}

	public void setProcedimientoDAO(ProcedimientoDAO procedimientoDAO) {
		this.procedimientoDAO = procedimientoDAO;
	}
	
	public Object[] getProcedimientos(String idUnidad){
		
		List<Procedimiento> list = new ArrayList<Procedimiento>(); 
		
		try {
			List<Unidadxprocedimiento> procedimientos = unidadxprocedimientoDAO.getProcedimientosXUnidad(Integer.parseInt(idUnidad));
			list = procedimientoDAO.getProcedimientos(procedimientos);
			
		} catch (Exception e) {
                    log.error(e.getMessage(),e);
		}
		
		return list.toArray();
	}

	public Procedimiento getProcedimientoById(Integer procedimientos) {
		return procedimientoDAO.getProcedimientoById(procedimientos);
	}
}