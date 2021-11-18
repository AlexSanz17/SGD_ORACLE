package org.ositran.services;

import java.util.List;
import org.apache.log4j.Logger;
import org.ositran.daos.SupervisorDAO;
import org.springframework.transaction.annotation.Transactional;

import com.btg.ositran.siged.domain.Supervisor;

public class SupervisorServiceImpl implements SupervisorService{
	private static Logger log = Logger.getLogger("org.ositran.services.SupervisorServiceImpl");
	private SupervisorDAO supervisorDAO;
	   

	public List<Supervisor> findAll()
	throws RuntimeException {
		return getSupervisorDAO().findAll();
	}
	
	@Override
	public Supervisor findByCriteria(Integer idDoc, String strNombre)
			throws RuntimeException {
		// TODO Auto-generated method stub
		return null;
	}
	

	@Override
	public Supervisor findById(Integer idArchivo) throws RuntimeException {
		// TODO Auto-generated method stub
		return getSupervisorDAO().findById(idArchivo);
	}

	@Transactional
	public Supervisor saveSupervisor(Supervisor objA) throws RuntimeException {
		Supervisor supervisor = new Supervisor();
	      try { 
	    	  objA=getSupervisorDAO().saveSupervisor(objA);
	      }catch(Exception e){
                  log.error(e.getMessage(),e);
	          //log.debug(" ## error supervisor ");
	      }
	      return objA;
	}
	
	public SupervisorDAO getSupervisorDAO() {
		return supervisorDAO;
	}
	
	public void setSupervisorDAO(SupervisorDAO supervisorDAO) {
		this.supervisorDAO = supervisorDAO;
	}

}
