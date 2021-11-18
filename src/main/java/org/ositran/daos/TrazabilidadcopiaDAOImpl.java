/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.ositran.utils.Constantes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.btg.ositran.siged.domain.Trazabilidadcopia;

public class TrazabilidadcopiaDAOImpl implements TrazabilidadcopiaDAO {

   private static final Logger log = LoggerFactory.getLogger(TrazabilidadcopiaDAOImpl.class);
	private EntityManager em;

	/*@Override
	public void guardarTrazabilidadcopia(Trazabilidadcopia trazabilidadcopia) {
		em.persist(trazabilidadcopia);
		em.flush();
	}*/

	@SuppressWarnings("unchecked")
	@Override
	public List<Trazabilidadcopia> buscarPorOrigen(Integer idOrigen, Character tipoOrigen) throws NoResultException{
		return em.createNamedQuery("Trazabilidadcopia.buscarPorOrigen").setParameter("idorigen", idOrigen)
				.setParameter("tipo", tipoOrigen).getResultList();
	}

	@Override
	public Trazabilidadcopia buscarPorNotificacion(Integer idNotificacion) {
		return (Trazabilidadcopia)(em.createNamedQuery("Trazabilidadcopia.buscarPorNotificacion").setParameter("idnotificacion", idNotificacion).getSingleResult());
	}

	public Long numeroCopias(Integer idOrigen, Character tipoOrigen) {
		try{
			return (Long)(em.createNamedQuery("Trazabilidadcopia.numeroCopias").setParameter("idorigen", idOrigen)
			.setParameter("tipo", tipoOrigen).getSingleResult());
		}catch(NoResultException e){
			return 0L;
		}
	}

	@Override
	public Trazabilidadcopia buscarPorId(Integer idTrazabilidadcopia) {
		return (Trazabilidadcopia)em.createNamedQuery("Trazabilidadcopia.buscarPorId").setParameter("ididtrazabilidadcopia", idTrazabilidadcopia).getSingleResult();
	}

	   public List<Trazabilidadcopia> buscarUsuarioCopia(Integer idDocumento, Integer traza) {
		     String sQuery = "SELECT NEW Trazabilidadcopia(" +
                            "n.destinatario" +
                            ")";
                        sQuery += "FROM Trazabilidadcopia n ";
                        sQuery += "WHERE n.idorigen.idtrazabilidaddocumento  = :idtraza ";
                        sQuery += "AND n.tipo != :tipoCopia ";
                        Query qQuery = em.createQuery(sQuery)
                        .setParameter("idtraza", traza)
                        .setParameter("tipoCopia", Constantes.TIPO_ORIGEN_TRAZACOPIA);
                        return qQuery.getResultList();
           }


   @Override
   public Trazabilidadcopia saveObject(Trazabilidadcopia trazabilidadCopia) {
      if (trazabilidadCopia.getIdtrazabilidadcopia() == null) {
         em.persist(trazabilidadCopia);
         em.flush();
         em.refresh(trazabilidadCopia);
      } else {
         em.merge(trazabilidadCopia);
         em.flush();
      }

      return trazabilidadCopia;
   }

   @Override
   public List<Trazabilidadcopia> findLstByIdDocumento(Integer idDocumento) {
      try {
         return em.createNamedQuery("Trazabilidadcopia.buscarPorDocumento").setParameter("iddocumento", idDocumento).getResultList();
      } catch (NoResultException nre) {
         log.warn("No se encontraron trazabilidadCopias para el documento con ID {}", idDocumento);
         return null;
      }
   }

	public EntityManager getEm() {
		return em;
	}

	@PersistenceContext(unitName="sigedPU")
	public void setEm(EntityManager em) {
		this.em = em;
	}

}
