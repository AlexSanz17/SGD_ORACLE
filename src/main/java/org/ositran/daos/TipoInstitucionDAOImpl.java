/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.btg.ositran.siged.domain.TipoInstitucion;
import java.util.List;
import org.springframework.stereotype.Repository;
/**
 *
 * @author consultor_jti15
 */

@Repository
public class TipoInstitucionDAOImpl implements TipoInstitucionDAO{
    private EntityManager em;
    
    
    public EntityManager getEm() {
	return em;
    }

    @PersistenceContext(unitName="sigedPU")
    public void setEm(EntityManager em) {
		this.em = em;
    }
    
    public List<TipoInstitucion> findTipoInstitucion(String tipoPersonaJuridica){	 
      return em.createNamedQuery("TipoInstitucion.findTipoInstitucion").setParameter("codTipoPersonaJuridica", tipoPersonaJuridica).getResultList();
   }
}
