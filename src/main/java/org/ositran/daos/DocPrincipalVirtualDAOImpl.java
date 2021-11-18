/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import com.btg.ositran.siged.domain.IotdtdDocPrincipal;
import com.btg.ositran.siged.domain.Usuarioxunidadxfuncion;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author consultor_jti15
 */
public class DocPrincipalVirtualDAOImpl implements  DocPrincipalVirtualDAO{
    private EntityManager em;
     
    @PersistenceContext(unitName="sigedPU")
    public void setEm(EntityManager em){
	this.em=em;
    }
    
    public List<IotdtdDocPrincipal> buscarPrincipalVirtualId(Integer idDocExterno){
       String sql = "SELECT e FROM IotdtdDocPrincipal e where e.siddocext =" + idDocExterno;
       return em.createQuery(sql).getResultList();
    }
    
    public IotdtdDocPrincipal buscarPrincipaByDocExterno(Integer idDocExterno){
         String  sql = "SELECT vnomdoc from IOTDTD_DOC_PRINCIPAL where siddocext = " + idDocExterno;
         IotdtdDocPrincipal iotdtdDocPrincipal = new IotdtdDocPrincipal();
         try{
               Query q = em.createNativeQuery(sql.toString());
               iotdtdDocPrincipal.setVnomdoc(q.getResultList().get(0).toString());
         }catch(Exception e){
             e.printStackTrace();
             //throw e;
         }
            
         return iotdtdDocPrincipal;
    }
    
    public IotdtdDocPrincipal registrarPrincipal(IotdtdDocPrincipal principal){
        if(principal.getSiddocpri() == null){
	    em.persist(principal); 
	    em.flush();
	    em.refresh(principal);
	}
	else{
	    em.merge(principal); 
	    em.flush();
	}
		
       return principal;
    }
}
