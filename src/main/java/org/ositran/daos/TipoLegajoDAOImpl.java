/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import com.btg.ositran.siged.domain.TipoLegajo;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

/**
 *
 * @author jbengoa
 */

@Repository
public class TipoLegajoDAOImpl implements TipoLegajoDAO{
    private EntityManager em;
    
    public EntityManager getEm() {
	return em;
    }

    @PersistenceContext(unitName="sigedPU")
    public void setEm(EntityManager em) {
		this.em = em;
    }
    
    public List<TipoLegajo> findTipoLegajoBusqueda(){
        return em.createQuery("SELECT d FROM TipoLegajo d where d.estado='A' ").getResultList();   
    }
    
    public TipoLegajo findTipoLegajo(TipoLegajo tipoLegajo){
        return (TipoLegajo)em.createQuery("SELECT d FROM TipoLegajo d where d.idTipo=:idTipoLegajo").setParameter("idTipoLegajo", tipoLegajo.getIdTipo()).getSingleResult();
    }
    
    public List<TipoLegajo> findAllTipoLegajoActive(Integer idUnidad){
        return em.createQuery("SELECT d FROM TipoLegajo d where d.estado='A' and  exists (select 1 from TipoLegajoUnidad t where t.idTipo = d.idTipo and t.estado = 'A' and t.unidad.idunidad =:idUnidad)").setParameter("idUnidad", idUnidad).getResultList();   
    }
     
     public List<TipoLegajo> findAllTipoLegajo(Integer idUnidad){
        return em.createNamedQuery("SELECT d, TipoLegajoUnidad t FROM TipoLegajo d where d.idTipo = t.idTipo and t.unidad.idunidad =:idUnidad").setParameter("idUnidad", idUnidad).getResultList();
     }
}
