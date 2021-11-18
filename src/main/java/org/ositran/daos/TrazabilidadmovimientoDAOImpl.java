/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.ositran.utils.Constantes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.btg.ositran.siged.domain.TrazabilidadMovimiento;
import com.btg.ositran.siged.domain.Trazabilidadcopia;

public class TrazabilidadmovimientoDAOImpl implements TrazabilidadmovimientoDAO {

   private static final Logger log = LoggerFactory.getLogger(TrazabilidadmovimientoDAOImpl.class);
	private EntityManager em;

   @Override
   public TrazabilidadMovimiento saveObject(TrazabilidadMovimiento trazabilidadMovimiento) {
         em.persist(trazabilidadMovimiento);
         em.flush();
         em.refresh(trazabilidadMovimiento);
      return trazabilidadMovimiento;
   }


	public EntityManager getEm() {
		return em;
	}

	@PersistenceContext(unitName="sigedPU")
	public void setEm(EntityManager em) {
		this.em = em;
	}

}
