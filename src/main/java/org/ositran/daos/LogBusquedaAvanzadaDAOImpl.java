/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.springframework.stereotype.Repository;

import com.btg.ositran.siged.domain.LogBusquedaAvanzada;
import com.btg.ositran.siged.domain.LogOperacion;

@Repository
public class LogBusquedaAvanzadaDAOImpl implements LogBusquedaAvanzadaDAO{
	private EntityManager em;
	private static Logger log = LoggerFactory.getLogger(LogBusquedaAvanzadaDAOImpl.class);

	@Override
	public LogBusquedaAvanzada saveLogBusquedaAvanzada(LogBusquedaAvanzada logBusquedaAvanzada){
		//em.createNativeQuery("{call REGISTRAR_LOG_OPERACION(?1,?2,?3,?4,?5)}")
		//		.setParameter(1, new Integer(20)).setParameter(2, new Integer(40)).setParameter(3, "x").setParameter(4, "yy").setParameter(5, "ss").executeUpdate();
		log.debug("-> [DAO] LogBusquedaAvanzadaDAO - saveLogBusquedaAvanzada()");

		  em.persist(logBusquedaAvanzada); //
		  em.flush();
	      em.refresh(logBusquedaAvanzada);

       return logBusquedaAvanzada;
	}

	 // ////////////////////////////////
    // Getters and Setters //
    // ////////////////////////////////
    public EntityManager getEm() {
        return em;
    }

    @PersistenceContext(unitName = "sigedPU")
    public void setEm(EntityManager em) {
        this.em = em;
    }
}
