/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import com.btg.ositran.siged.domain.Usuario;
import com.btg.ositran.siged.domain.Legajo;
import com.btg.ositran.siged.domain.TipoLegajo;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

/**
 *
 * @author jbengoa
 */
@Repository
public class LegajoDAOImpl implements LegajoDAO{
    private EntityManager em;
    
    @PersistenceContext(unitName = "sigedPU")
    public void setEm(EntityManager em) {
	this.em = em;
   }
    
    public List findLegajosCompartidos(Usuario objUsuario){
        String sql = " SELECT e FROM FilaBandejaLegajo e WHERE e.usuarioCreacion not in (:idUsuario)  and exists (select 1 from TipoLegajoUnidad u where u.estado = 'A' and u.idTipo = e.idTipo and u.unidad.idunidad = :idUnidad)" +
		     " ORDER BY e.fechaCreacion DESC";

       return em.createQuery(sql).setParameter("idUsuario", objUsuario.getIdUsuarioPerfil()).setParameter("idUnidad", objUsuario.getIdUnidadPerfil())
					  .getResultList();
    } 
        
    public List findLegajosUsuarioFinal(Usuario objUsuario){
        String sql = " SELECT e FROM FilaBandejaLegajo e WHERE e.usuarioCreacion = :idUsuario and e.idUnidad =:idUnidad" +
		     " ORDER BY e.fechaCreacion DESC";

       return em.createQuery(sql).setParameter("idUsuario", objUsuario.getIdUsuarioPerfil()).setParameter("idUnidad", objUsuario.getIdUnidadPerfil())
					  .getResultList();
    }
    
    public List<Legajo> findByNroLegajo(Legajo legajo){
        System.out.println("Valores==" + legajo.getNroLegajo());
        String sql = " SELECT e FROM Legajo e WHERE e.nroLegajo =:nroLegajo and e.estado not in ('I') ";
        return em.createQuery(sql).setParameter("nroLegajo", legajo.getNroLegajo()).getResultList();
    }
    
    public Legajo findByIdLegajo(Legajo legajo){
        String sql = " SELECT e FROM Legajo e WHERE e.idLegajo =:idLegajo ";
               
        if (legajo.getEstado()!=null && !legajo.getEstado().equals(""))
           sql = sql + " and e.estado = :idEstado "; 
	else
           sql = sql + " and e.estado not in ('I') ";
        
        sql = sql + " ORDER BY e.fechaCreacion DESC";

        try{
            if (legajo.getEstado()!=null && !legajo.getEstado().equals("")) 
               return (Legajo)em.createQuery(sql).setParameter("idLegajo", legajo.getIdLegajo()).setParameter("idEstado", legajo.getEstado()).getSingleResult();
            else
               return (Legajo)em.createQuery(sql).setParameter("idLegajo", legajo.getIdLegajo()).getSingleResult();   
        }catch(NoResultException e){
             return null;
        }    
    }
    
    /*public List<Legajo> getDocumentosPorLegajo(Legajo legajo){
        String sql = " SELECT e FROM Legajo e WHERE e.idLegajo =:idLegajo and e.estado = 'A' " +
		     " ORDER BY e.fechaCreacion DESC";

       return em.createQuery(sql).setParameter("idLegajo", legajo.getIdLegajo())
					  .getResultList();
    }*/
    
    public Legajo saveLegajo(Legajo legajo){
        if(legajo.getIdLegajo()==null){
            em.persist(legajo);
            em.flush();
	    em.refresh(legajo);
        }else{
            em.merge(legajo);
	    em.flush();
            em.refresh(legajo);
	}
		
        return legajo;
    }
    
    public List findByCriteria(Usuario usuario, String sNroExpediente, String sNroHT, Integer tipoDocumento, String sNroDocumento, String sTipoLegajo){
        Query q = null;
        String where = "";    
        String whereDocumento = "";
        String tabla = "";
        
        try{  
                String query= "SELECT e FROM Legajo e ";
                where = " where e.estado in ('A','C') and exists (select 1 from TipoLegajoUnidad u where u.estado = 'A' and u.idTipo = e.tipoLegajo.idTipo and u.unidad.idunidad = :unidad)   ";
                
                if(sNroExpediente!= null && !StringUtils.isEmpty(sNroExpediente)){
                        where += " AND e.nroLegajo = :nroLegajo ";
                }
                
                if(sTipoLegajo!= null && !StringUtils.isEmpty(sTipoLegajo)){
                        where += " AND e.tipoLegajo.idTipo = :idTipo ";
                }
                
                if(sNroHT!=null &&  !StringUtils.isEmpty(sNroHT)){
                        whereDocumento = " and e.idLegajo = l.idLegajo and l.estado = 'A' and d.idDocumento = l.idDocumento and d.documentoreferencia is NULL ";
                        tabla = ", LegajoDocumento l, Documento d  ";
                        where += "  and d.ID_CODIGO = :nroHT  ";
                }
                
                if(sNroDocumento!=null &&  !StringUtils.isEmpty(sNroDocumento)){
                        whereDocumento = " and e.idLegajo = l.idLegajo and l.estado = 'A' and d.idDocumento = l.idDocumento and d.documentoreferencia is NULL ";
                        tabla = ", LegajoDocumento l, Documento d  ";
                        where += " and d.numeroDocumento like :nroDocumento AND d.tipoDocumento.idtipodocumento = :tipoDocumento";
                }
                
                q=em.createQuery(query + tabla +  where + whereDocumento);
                q.setParameter("unidad", usuario.getIdUnidadPerfil());
               
                if(sNroExpediente != null && !StringUtils.isEmpty(sNroExpediente)){
                        q.setParameter("nroLegajo",sNroExpediente.toUpperCase().trim());
                }
                
                if(sNroHT!=null && !StringUtils.isEmpty(sNroHT)){
                        q.setParameter("nroHT",new Integer(sNroHT));
                }
                
                 if(sTipoLegajo!=null && !StringUtils.isEmpty(sTipoLegajo)){
                        q.setParameter("idTipo",new Integer(sTipoLegajo));
                }
                
                if(sNroDocumento!=null && !StringUtils.isEmpty(sNroDocumento)){
                        q.setParameter("nroDocumento","%" + sNroDocumento.toUpperCase().trim() + "%");
                        q.setParameter("tipoDocumento", tipoDocumento);
                }
                 
                return q.getResultList();
        }catch(Exception e){
            e.printStackTrace();
           
        }       
                 
        return null;
    }
    
    public Integer generateNroLegajoProduccion(TipoLegajo tipoLegajo) {
	  
	   String sQuery = "SELECT   " +  tipoLegajo.getSecuencia()  +".nextval FROM DUAL";
	   Query qQuery = em.createNativeQuery(sQuery);

	   return Integer.valueOf(qQuery.getResultList().get(0).toString());
   }
}
