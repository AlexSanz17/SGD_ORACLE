/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.btg.ositran.siged.domain.Funcion;

/**
 *
 * @author consultor_jti15
 */
public class FuncionDAOImpl implements FuncionDAO{
    private EntityManager em;
    
    public EntityManager getEm(){
	 return em;
    }

    @PersistenceContext(unitName="sigedPU")
    public void setEm(EntityManager em){
	this.em=em;
    }
    
    @Override
     public Funcion findByIdFuncion(Integer idFuncion){
          return (Funcion) em.createNamedQuery("Funcion.findByIdFuncion").setParameter("pidfuncion",idFuncion).getSingleResult();
     }
}
