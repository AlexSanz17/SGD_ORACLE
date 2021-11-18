/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import com.btg.ositran.siged.domain.LegajoDocumento;
import com.btg.ositran.siged.domain.TipoLegajoUnidad;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 *
 * @author jbengoa
 */

@Repository
public class TipoLegajoUnidadDAOImpl implements TipoLegajoUnidadDAO{
    private EntityManager em;
    
    @PersistenceContext(unitName = "sigedPU")
    public void setEm(EntityManager em) {
	this.em = em;
   }
    
   public List<TipoLegajoUnidad> findTipoLegajoUnidad(TipoLegajoUnidad tipoLegajoUnidad) {
        try {
              return em.createQuery("SELECT d FROM TipoLegajoUnidad d where d.estado = 'A' and d.unidad.idunidad =:idUnidad and d.accion = :accion").setParameter("idUnidad", tipoLegajoUnidad.getIdTipoLegajoUnidad()).setParameter("accion", tipoLegajoUnidad.getAccion()).getResultList();   
         } catch (NoResultException e) {
             return null;
         } 
   }  
}
