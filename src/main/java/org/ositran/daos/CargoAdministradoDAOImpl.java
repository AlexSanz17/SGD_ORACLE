/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import com.btg.ositran.siged.domain.CargoAdministrado;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author consultor_jti15
 */
public class CargoAdministradoDAOImpl implements CargoAdministradoDAO{
     private EntityManager em;
    
    
    public EntityManager getEm() {
	return em;
    }

    @PersistenceContext(unitName="sigedPU")
    public void setEm(EntityManager em) {
		this.em = em;
    }
    
    public List<CargoAdministrado> findAllCargoAdministrado(){
    	 return em.createNamedQuery("CargoAdministrado.findAllCargoAdministrado").getResultList();
    }
    
    public CargoAdministrado findCargoAdministrado(Integer idCargoAdministrado){
    	 return (CargoAdministrado)em.createNamedQuery("CargoAdministrado.findCargoAdministrado").setParameter("idCargoAdministrado",idCargoAdministrado).getSingleResult();
    }
    
     public List<CargoAdministrado> findCargoFiltroAdministrado(String desCargo){
         String sql = "SELECT c FROM CargoAdministrado c WHERE c.estado = 'A' and upper(c.desCargo) like :desCargo";
         Query q = em.createQuery(sql);
         q.setParameter("desCargo", '%' + desCargo.trim().toUpperCase() + '%');
         return q.getResultList();
         
     }
}
