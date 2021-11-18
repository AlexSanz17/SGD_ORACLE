/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import com.btg.ositran.siged.domain.Serie;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author consultor_jti15
 */
public class SerieDAOImpl implements SerieDAO{
     private EntityManager em;
    
     public EntityManager getEm() {
       return em;
     }

     @PersistenceContext(unitName="sigedPU")
     public void setEm(EntityManager em) {
       this.em = em;
     }
     
     public List<Serie> getListSerie(String estado){
       String sql = "";  
       
       if (estado == null)  
         sql = "SELECT f FROM Serie f ";
       else
        sql = "SELECT f FROM Serie f where estado ='" + estado + "'";
        
       return (List<Serie>)em.createQuery(sql).getResultList();  
     } 
}
