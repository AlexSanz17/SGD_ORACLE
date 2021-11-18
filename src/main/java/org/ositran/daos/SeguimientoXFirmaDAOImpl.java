/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import com.btg.ositran.siged.domain.FilaBandejaFirmar;
import com.btg.ositran.siged.domain.SeguimientoXFirma;
import com.btg.ositran.siged.domain.Usuario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author consultor_jti15
 */

public class SeguimientoXFirmaDAOImpl implements SeguimientoXFirmaDAO{
    
    private EntityManager em;
     
    @PersistenceContext(unitName="sigedPU")
    public void setEm(EntityManager em){
	this.em=em;
    }
    
    public List<FilaBandejaFirmar> buscarSeguimientoXFirmaXUsuario(Usuario objUsuario) {
        
        String sql = "SELECT e FROM FilaBandejaFirmar e WHERE e.idUsuario = :idUsuario and e.unidadPropietario = :idUnidadPropietario and e.cargoPropietario  =:idCargoPropietario and e.codEstado = 'A' " +
					 " ORDER BY e.fechaRecepcion DESC";

        return em.createQuery(sql).setParameter("idUsuario", objUsuario.getIdUsuarioPerfil())
                                  .setParameter("idUnidadPropietario", objUsuario.getIdUnidadPerfil())
                                  .setParameter("idCargoPropietario", objUsuario.getIdFuncionPerfil())
			          .getResultList();
    }
    
    public SeguimientoXFirma findByIdDocumentoFirmado(Integer idDocumentoFirmado){
         return (SeguimientoXFirma)em.createNamedQuery("SeguimientoXFirma.findByIdDocumentoFirmado")
				.setParameter("idDocumentoFirmado", idDocumentoFirmado).getSingleResult();
    }
    
    public List<SeguimientoXFirma> findByIdDocumento(Integer idDocumento ,Usuario objUsuario){
       String sql = "SELECT e FROM SeguimientoXFirma e WHERE e.idDocumento = :idDocumento and e.idUsuario = :idUsuario and e.unidadPropietario = :idUnidadPropietario and e.cargoPropietario  =:idCargoPropietario and e.estado = 'A' ";
		
       return em.createQuery(sql).setParameter("idDocumento", idDocumento)
                                         .setParameter("idUsuario", objUsuario.getIdUsuarioPerfil())
                                         .setParameter("idUnidadPropietario", objUsuario.getIdUnidadPerfil())
                                         .setParameter("idCargoPropietario", objUsuario.getIdFuncionPerfil())
					 .getResultList();
    }
    

    public SeguimientoXFirma guardarSeguimiento(SeguimientoXFirma seguimiento) {
          	
        if(seguimiento.getIdSeguimientoFirma() == null){
            em.persist(seguimiento);
	    em.flush();
	    em.refresh(seguimiento);
        }else{
	    em.merge(seguimiento);
	    em.flush();
	    em.refresh(seguimiento);
	}
	
        return seguimiento;
    }
}
