/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import com.btg.ositran.siged.domain.FilaBandejaSeguimiento;
import java.util.ArrayList;
import java.util.List;
import com.btg.ositran.siged.domain.Usuario;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import com.btg.ositran.siged.domain.SeguimientoXUsuario;

public class SeguimientoXUsuarioDAOImpl implements SeguimientoXUsuarioDAO {

	private EntityManager em;

	@Override
	public SeguimientoXUsuario guardarSeguimiento(SeguimientoXUsuario seguimiento) {
          	List<SeguimientoXUsuario> lista = buscarSeguimientoXUsuario(seguimiento);
          
                if(!lista.isEmpty()){
			return lista.get(0);
		}
                
                if(seguimiento.getIdSeguimientoUsuario() == null){
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

	@SuppressWarnings("unchecked")
	public List<SeguimientoXUsuario> buscarSeguimientoXUsuario(SeguimientoXUsuario seguimiento){
                String sql = "SELECT s FROM SeguimientoXUsuario s WHERE s.idDocumento = :idDocumento AND s.idUsuario = :idUsuario and s.unidadPropietario= :idUnidadPropietario and s.cargoPropietario = :idCargoPropietario and s.estado = 'A'";
		return em.createQuery(sql).setParameter("idDocumento", seguimiento.getIdDocumento())
								  .setParameter("idUsuario", seguimiento.getIdUsuario()).setParameter("idUnidadPropietario", seguimiento.getUnidadPropietario()).setParameter("idCargoPropietario", seguimiento.getCargoPropietario())
								  .getResultList();
                
	}
        
        public SeguimientoXUsuario findByIdSeguimiento(Integer idSeguimiento){
         return (SeguimientoXUsuario)em.createNamedQuery("SeguimientoXUsuario.findByIdSeguimiento")
				.setParameter("idSeguimientoUsuario", idSeguimiento).getSingleResult();
    }

	@Override
	public void eliminarSeguimiento(SeguimientoXUsuario seguimiento) {
                List<SeguimientoXUsuario> lista = buscarSeguimientoXUsuario(seguimiento);
		if(lista != null && !lista.isEmpty()){
			for(SeguimientoXUsuario sxu : lista){
				em.remove(sxu);
				em.flush();
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FilaBandejaSeguimiento> buscarGridSeguimiento(Usuario objUsuario){
             
        String sql = "SELECT e FROM FilaBandejaSeguimiento e WHERE e.idUsuario = :idUsuario and e.unidadPropietario = :idUnidadPropietario and e.cargoPropietario  =:idCargoPropietario and e.codEstado = 'A' " +
					 " ORDER BY e.fechaCreacion DESC";

        return em.createQuery(sql).setParameter("idUsuario", objUsuario.getIdUsuarioPerfil())
                                  .setParameter("idUnidadPropietario", objUsuario.getIdUnidadPerfil())
                                  .setParameter("idCargoPropietario", objUsuario.getIdFuncionPerfil())
			          .getResultList();
	}

	public String buscarAreaRemitente(String idRemitente){
                
                if(idRemitente == null || idRemitente.equals("")){
			return "";
		}
		try{
			String sql = "SELECT u.nombre FROM Unidad u ";
			sql += "WHERE u.idunidad = (SELECT s.idunidad FROM Usuario s WHERE s.idUsuario = "+idRemitente+")";
			Query q = em.createNativeQuery(sql);
			return (String) q.getSingleResult();
		}catch(Exception e){
			e.printStackTrace();
			return "";
		}
	}

	public EntityManager getEm() {
		return em;
	}

	@PersistenceContext(unitName = "sigedPU")
	public void setEm(EntityManager em) {
		this.em = em;
	}

}
