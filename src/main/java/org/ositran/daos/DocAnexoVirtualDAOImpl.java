/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import com.btg.ositran.siged.domain.IotdtdAnexo;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
/**
 *
 * @author consultor_jti15
 */
public class DocAnexoVirtualDAOImpl implements DocAnexoVirtualDAO{
    private EntityManager em;
     
    @PersistenceContext(unitName="sigedPU")
    public void setEm(EntityManager em){
	this.em=em;
    }
    
    public List<IotdtdAnexo> buscarAnexoVirtualId(Integer idDocExterno){
       String sql = "SELECT e FROM IotdtdAnexo e where e.siddocext =" + idDocExterno;
					 
       return em.createQuery(sql).getResultList(); 
    }
    
    public IotdtdAnexo registrarAnexo(IotdtdAnexo anexo){
		
       if(anexo.getSiddocanx() == null){
	    em.persist(anexo); 
	    em.flush();
	    em.refresh(anexo);
	}
	else{
	    em.merge(anexo); 
	    em.flush();
	}
		
       return anexo;
    }
}
