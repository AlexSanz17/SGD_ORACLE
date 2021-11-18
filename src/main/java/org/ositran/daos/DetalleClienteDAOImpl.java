/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import com.btg.ositran.siged.domain.Cliente;
import com.btg.ositran.siged.domain.DetalleCliente;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.log4j.Logger;
import org.ositran.utils.Constantes;
import org.springframework.stereotype.Repository;

/**
 *
 * @author consultor_jti15
 */

@Repository
public class DetalleClienteDAOImpl implements DetalleClienteDAO{
    private static Logger _log=Logger.getLogger(DetalleClienteDAOImpl.class);
    private EntityManager em;
    
    public List<DetalleCliente> findByDetalleCliente(Integer idCliente){
          return em.createNamedQuery("DetalleCliente.findByDetalleCliente").setParameter("idCliente", idCliente).
	  setParameter("estado", String.valueOf(Constantes.ESTADO_ACTIVO)).getResultList();
	}
    
    public DetalleCliente findByDetalleClienteId(Integer idDetalleCliente){
          return (DetalleCliente) em.createNamedQuery("DetalleCliente.findByDetalleClienteId").setParameter("idDetalleCliente", idDetalleCliente).getSingleResult();
	}
    
    /*
    public DetalleCliente findByDetalleCliente(Integer idCliente, Integer idRemitente){
          
         return (DetalleCliente)em.createNamedQuery("DetalleCliente.findByDetalleClienteRemitente").setParameter("idCliente", idCliente)
                                                                           .setParameter("idRemitente", idRemitente)
	                                                                   .setParameter("estado", String.valueOf(Constantes.ESTADO_ACTIVO)).getSingleResult();
        
    }*/
    
    public DetalleCliente guardarObj(DetalleCliente objRemitente) {
        try{
         
        if (objRemitente.getIdDetalleCliente() == null) {
            em.persist(objRemitente);
            em.flush();
            em.refresh(objRemitente);
        } else {
            em.merge(objRemitente);
            em.flush();
            em.refresh(objRemitente);
        }
        }catch(Exception e){
            e.printStackTrace();
        }
            
            
        return objRemitente;
    }
    /*
    public Integer findByMaxRemitente(DetalleCliente objRemitente){
        return (Integer) em.createNamedQuery("DetalleCliente.findByMaxRemitente").setParameter("idCliente", objRemitente.getIdCliente()) .getSingleResult();
    }*/
    
     public EntityManager getEm() {
        return em;
    }
    
    @PersistenceContext(unitName="sigedPU")
    public void setEm(EntityManager em){
	  this.em=em;
    }
}
