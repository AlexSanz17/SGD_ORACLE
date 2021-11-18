/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import com.btg.ositran.siged.domain.IotdtcRecepcion;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
/**
 *
 * @author Juan Bengoa
 */
public class RecepcionVirtualDAOImpl implements RecepcionVirtualDAO{
    private EntityManager em;
    
    public String findByCantidadesDocumentosVirtuales(){
        String sQuery = "SELECT FN_DOCUMENTOS_VIRTUALES('R') FROM DUAL";
        Query qQuery = em.createNativeQuery(sQuery);

        return qQuery.getResultList().get(0).toString();
     }
     
    @PersistenceContext(unitName="sigedPU")
    public void setEm(EntityManager em){
	this.em=em;
    }
    
     public IotdtcRecepcion findByVcuo(String vcuo){
         return (IotdtcRecepcion)em.createNamedQuery("IotdtcRecepcion.findByVcuo")
				.setParameter("vcuo", vcuo).getSingleResult();
     }
    
  
    public IotdtcRecepcion registrarDocumento(IotdtcRecepcion recepcion){
		
       if(recepcion.getSidrecext() == null){
	    em.persist(recepcion); 
	    em.flush();
	    em.refresh(recepcion);
	}
	else{
	    em.merge(recepcion); 
	    em.flush();
	}
		
       return recepcion;
    }
}
