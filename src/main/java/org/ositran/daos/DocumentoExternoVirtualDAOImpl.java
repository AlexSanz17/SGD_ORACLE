/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import com.btg.ositran.siged.domain.IotdtmDocExterno;
import com.btg.ositran.siged.domain.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

/**
 *
 * @author jbengoa (JUAN CARLOS BENGOA)
 */
@Repository
public class DocumentoExternoVirtualDAOImpl implements DocumentoExternoVirtualDAO{
    private EntityManager em;
     
    @PersistenceContext(unitName="sigedPU")
    public void setEm(EntityManager em){
        this.em=em;
    }
    
    public EntityManager getEm() {
        return em;
    }
    
    public List<IotdtmDocExterno> buscarDocumentosEnviadosPendientesMigrarCargo(){
        String  sql = " SELECT e FROM IotdtmDocExterno e where e.sidemiext.cflgest in ('R') and e.sidemiext.bcarstdrec is not null and (select count(d.idDocumento) from Documento d, Archivo a where d.idDocumento = e.sidemiext.iddocumento and decode(d.documentoreferencia, null,d.idDocumento, d.documentoreferencia) = a.documento and a.estado = 'A' and a.principal = 'M') = 0 "; 
        return em.createQuery(sql).getResultList();  
    }
    
    public List<IotdtmDocExterno> buscarDocumentosEnviadosPendientesAtencion(){
        String  sql = " SELECT e FROM IotdtmDocExterno e, Documento d where e.sidemiext.cflgest in ('R') and e.sidemiext.bcarstdrec is not null and e.sidemiext.iddocumento = d.idDocumento and d.estado not in ('T') "; 
        return em.createQuery(sql).getResultList();  
    }
    
    public List<IotdtmDocExterno> buscarDocumentosEnviadosPendientesCargo(){
        String  sql = " SELECT e FROM IotdtmDocExterno e where e.sidemiext.cflgest = 'E' ";
        sql = sql + " union  SELECT e FROM IotdtmDocExterno e where e.sidemiext.cflgest = 'P' and e.sidemiext.cflgenv = 'E'";
        return em.createQuery(sql).getResultList();  
    }
    
    public List<IotdtmDocExterno> buscarDocumentosDespachoPendientesSubsanacion(){
        String  sql = " SELECT e FROM IotdtmDocExterno e where e.sidemiext.cflgest in ('E', 'R','O') AND e.sidemiext.vcuoref is not null AND (select count(x.sidemiext.vcuo) from IotdtmDocExterno x where x.sidemiext.vcuo = e.sidemiext.vcuoref and x.sidemiext.cflgest = 'O')>0";
        return em.createQuery(sql).getResultList();  
    }
    
    public List<IotdtmDocExterno> buscarRecepcionVirtual(Usuario objUsuario){
       String sql = "SELECT e FROM IotdtmDocExterno e where e.sidrecext.sidrecext is not null and e.sidrecext.cflgest not in ('X')  order by e.sidrecext.dfecreg desc";
					 
       return em.createQuery(sql).getResultList();
    }
    
    //////////////////////////////////////////////////
    public List<IotdtmDocExterno> buscarDocumentosRecepcionPendientesSubsanacion(){
       String sql = "SELECT e FROM IotdtmDocExterno e where e.sidrecext.sidrecext is not null and e.sidrecext.cflgest = 'O' and e.sidrecext.vcuo in (select x.sidrecext.vcuoref from IotdtmDocExterno x where x.sidrecext.vcuoref is not null) ";
       return em.createQuery(sql).getResultList();
    }
    
    public List<IotdtmDocExterno> buscarDespachoVirtual(Usuario objUsuario){
       try{ 
           String sql = "SELECT e FROM IotdtmDocExterno e where e.sidemiext.sidemiext is not null and e.sidemiext.cflgest not in ('X') order by e.sidemiext.dfecreg desc";
           return em.createQuery(sql).getResultList();
           
       }catch(Exception e){
           e.printStackTrace();
       }
       return null;
       //return em.createQuery(sql).getResultList();
    }
     
    
    public IotdtmDocExterno buscarDocumentoVirtual(Integer nroVirtual){
       String sql = "SELECT e FROM IotdtmDocExterno e where e.siddocext =" + nroVirtual;
       try{				
            return (IotdtmDocExterno)em.createQuery(sql).getSingleResult(); 
       }catch(EntityNotFoundException e){
           return null;
       }catch(Exception e){
           return null;
       }     
    }
    
    public List<IotdtmDocExterno> buscarDocumentoDespachoVirtual(Integer numero){
       String sql = "SELECT e FROM IotdtmDocExterno e where e.sidemiext.vnumregstd =" + numero + " and e.sidemiext.cflgest in ('P','E','S','R') order by e.sidemiext.dfecreg desc ";
       try{				
            return em.createQuery(sql).getResultList();
       }catch(EntityNotFoundException e){
           return null;
       }catch(Exception e){
           return null;
       }     
    }
    
    public List<IotdtmDocExterno> buscarDocumentoProcesadoDespachoVirtual(Integer numero){
       String sql = "SELECT e FROM IotdtmDocExterno e where e.sidemiext.vnumregstd =" + numero + " and e.sidemiext.cflgest in ('R','O','S') order by e.sidemiext.dfecreg desc ";
       try{				
            return em.createQuery(sql).getResultList();
       }catch(EntityNotFoundException e){
           return null;
       }catch(Exception e){
           return null;
       }     
    }
    
    
    public IotdtmDocExterno registrarDocumento(IotdtmDocExterno docExterno){
		
       if(docExterno.getSiddocext() == null){
	    em.persist(docExterno); 
	    em.flush();
	    em.refresh(docExterno);
	}
	else{
	    em.merge(docExterno); 
	    em.flush();
	}
		
       return docExterno;
    }
    
    public List<String> buscarTramiteVirtual(String nroTramite){
        String sql = " SELECT CAST(E.SIDDOCEXT AS VARCHAR2(16)), DP.IDDOCUMENTO FROM IOTDTM_DOC_EXTERNO E, IOTDTC_DESPACHO DP WHERE  ";
        sql = sql + " DP.CFLGEST NOT IN ('X') AND E.SIDEMIEXT= DP.SIDEMIEXT AND DP.VNUMREGSTD = :nroTramite order by DP.dfecreg desc " ;

        Query q = em.createNativeQuery(sql.toString());
        q.setParameter("nroTramite", nroTramite);
        List<Object> res = (List<Object>) q.getResultList();
        List<String> lp = new ArrayList<String>();

        for (Object obj : res) {     
           Object[] objectArray = (Object[]) obj;
           String valor = (objectArray[0]!=null?objectArray[0].toString():"0");
           lp.add(valor);
        }      

        return lp;   
    }
}
