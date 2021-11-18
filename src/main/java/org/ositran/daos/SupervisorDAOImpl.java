/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.btg.ositran.siged.domain.Supervisor;

@Repository
public class SupervisorDAOImpl implements SupervisorDAO{

	private static Logger log = Logger.getLogger(SupervisorDAOImpl.class);
	
	@PersistenceContext(unitName="sigedPU")
	private EntityManager em;

	@Override
	public Supervisor findByCriteria(Integer idDoc, String strNombre) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Supervisor findById(Integer idArchivo) {
		// TODO Auto-generated method stub
		return getEm().find(Supervisor.class, idArchivo);
	}

	@Override
	public Supervisor saveSupervisor(Supervisor objA) {
		if (objA.getIdsupervisor() == null) {
			 
			 objA.setFechadsolicitud(new Date());  
	         getEm().persist(objA); //Nuevo
	         getEm().flush();
	         getEm().refresh(objA);
	      } else {
	         getEm().merge(objA); //Actualizacion
	         getEm().flush();
	      }

	      return objA;
	      
	}
	
	public List<Supervisor> findAll(){
	      return getEm().createNamedQuery("Supervisor.findAll").getResultList();
	   }
	
	public EntityManager getEm() {
		return em;
	}
	
	 @PersistenceContext(unitName="sigedPU")
	public void setEm(EntityManager em) {
		this.em = em;
	}

}
