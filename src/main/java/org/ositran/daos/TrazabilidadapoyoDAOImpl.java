/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.btg.ositran.siged.domain.Trazabilidadapoyo;
import com.btg.ositran.siged.domain.Documento;
import com.btg.ositran.siged.domain.Usuarioxunidadxfuncion;

public class TrazabilidadapoyoDAOImpl implements TrazabilidadapoyoDAO {

	private static final Logger log = LoggerFactory.getLogger(TrazabilidadapoyoDAOImpl.class);
	private EntityManager em;

	@Override
	public Trazabilidadapoyo guardar(Trazabilidadapoyo trazabilidadapoyo) {
		log.debug("Guardar Trazabilidadcopia");
		try {
			if(trazabilidadapoyo.getIdtrazabilidadapoyo() == null){
				em.persist(trazabilidadapoyo);
			}else{
				em.merge(trazabilidadapoyo);
			}
			em.flush();
			em.refresh(trazabilidadapoyo);
			return trazabilidadapoyo;
		} catch (Exception e) {
			log.debug("No guarda Trazabilidadapoyo",e);
          return null;
		}

	}

	@Override
	public Trazabilidadapoyo buscarPorId(Integer idTrazabilidadapoyo) {
		return em.find(Trazabilidadapoyo.class, idTrazabilidadapoyo);
	}

	@Override
	public void eliminar(Integer idTrazabilidadapoyo) {
		log.debug("Eliminar Trazabilidadcopia");
		em.remove(this.buscarPorId(idTrazabilidadapoyo));
		em.flush();
	}
        
        @SuppressWarnings("unchecked")
	@Override
	public Trazabilidadapoyo buscarUltimaDelegacion(Integer idDocumento) {
		log.debug("Buscar ultima Trazabilidadcopia");

		try{
                        String sql = "SELECT t FROM Trazabilidadapoyo t WHERE  t.documento = :idDocumento " +
					"ORDER BY t.fechacreacion DESC";
			                return (Trazabilidadapoyo)((List<Trazabilidadapoyo>)em.createQuery(sql).setParameter("idDocumento", idDocumento)
					.getResultList()).get(0);
		}catch(Exception e){
			//e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Trazabilidadapoyo buscarUltimaDelegacionUsuario(Integer idDestinatario, Integer idDocumento) {
		log.debug("Buscar ultima Trazabilidadcopia");

		try{
                        String sql = "SELECT t FROM Trazabilidadapoyo t WHERE t.destinatario.idusuario = :idDestinatario AND t.documento = :idDocumento " +
					"ORDER BY t.fechacreacion DESC";
			                return (Trazabilidadapoyo)((List<Trazabilidadapoyo>)em.createQuery(sql).setParameter("idDestinatario", idDestinatario)
					.setParameter("idDocumento", idDocumento)
					.getResultList()).get(0);
		}catch(Exception e){
			//e.printStackTrace();
			return null;
		}
	}
        
         public Trazabilidadapoyo buscarUltimaDelegacionPendiente(Trazabilidadapoyo trazabilidadapoyo){
             try{
			String sql = "SELECT t FROM Trazabilidadapoyo t WHERE t.idtrazabilidadapoyo= :idTrazabilidadApoyo " +
					" ORDER BY t.fechacreacion DESC";
			                return (Trazabilidadapoyo)((List<Trazabilidadapoyo>)em.createQuery(sql).setParameter("idTrazabilidadApoyo", trazabilidadapoyo.getIdtrazabilidadapoyo())
					.getResultList()).get(0);
		}catch(Exception e){

			e.printStackTrace();
			return null;
		}
         }
        
       public Trazabilidadapoyo buscarUltimaDelegacionUsuario(Usuarioxunidadxfuncion usuarioxUnidadxFuncion, Integer idDocumento){
            log.debug("Buscar ultima Trazabilidadcopia");

		try{
                        String sql = "SELECT t FROM Trazabilidadapoyo t WHERE t.destinatario.idusuario = :idDestinatario and t.unidaddestinatario = :idUnidadDestinatario and t.cargodestinatario = :idCargoDestinatario AND t.documento = :idDocumento " +
					"ORDER BY t.fechacreacion DESC";
			                return (Trazabilidadapoyo)((List<Trazabilidadapoyo>)em.createQuery(sql).setParameter("idDestinatario", usuarioxUnidadxFuncion.getIdusuario())
					 .setParameter("idUnidadDestinatario", usuarioxUnidadxFuncion.getIdunidad())
                                        .setParameter("idCargoDestinatario", usuarioxUnidadxFuncion.getIdfuncion())
                                        .setParameter("idDocumento", idDocumento)
					.getResultList()).get(0);
		}catch(Exception e){
			return null;
		}
        }
       
        public Trazabilidadapoyo buscarUltimaDelegacionUsuarioAR(Usuarioxunidadxfuncion usuarioxUnidadxFuncion, Integer idDocumento){
            log.debug("Buscar ultima Trazabilidadcopia");

		try{
                        String sql = "SELECT t FROM Trazabilidadapoyo t WHERE t.destinatario.idusuario = :idDestinatario and t.unidaddestinatario = :idUnidadDestinatario and t.cargodestinatario = :idCargoDestinatario AND t.documento = :idDocumento and t.accion.idAccion not in (25,31)" +
					"ORDER BY t.fechacreacion DESC";
			                return (Trazabilidadapoyo)((List<Trazabilidadapoyo>)em.createQuery(sql).setParameter("idDestinatario", usuarioxUnidadxFuncion.getIdusuario())
					 .setParameter("idUnidadDestinatario", usuarioxUnidadxFuncion.getIdunidad())
                                        .setParameter("idCargoDestinatario", usuarioxUnidadxFuncion.getIdfuncion())
                                        .setParameter("idDocumento", idDocumento)
					.getResultList()).get(0);
		}catch(Exception e){
			return null;
		}
        }
        
        @SuppressWarnings("unchecked")
	@Override
	public Trazabilidadapoyo buscarUltimaDelegacionUsuario(Documento d) {
		log.debug("Buscar ultima Trazabilidadcopia");

		try{
                      String sql = "SELECT t FROM Trazabilidadapoyo t WHERE t.destinatario.idusuario = :idDestinatario and t.unidaddestinatario = :idUnidadDestinatario and t.cargodestinatario = :idCargoDestinatario AND t.documento = :idDocumento " +
					"ORDER BY t.fechacreacion DESC";
			                return (Trazabilidadapoyo)((List<Trazabilidadapoyo>)em.createQuery(sql).setParameter("idDestinatario", d.getPropietario().getIdusuario())
					 .setParameter("idUnidadDestinatario", d.getUnidadpropietario())
                                        .setParameter("idCargoDestinatario", d.getCargopropietario())
                                        .setParameter("idDocumento", d.getIdDocumento())
					.getResultList()).get(0);
		}catch(Exception e){

			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Long numeroApoyos(Integer idtrazabilidaddocumento){
		String sql = "SELECT COUNT(t.idtrazabilidadapoyo) FROM Trazabilidadapoyo t WHERE t.trazabilidad.idtrazabilidaddocumento = :idorigen";
		return (Long)em.createQuery(sql).setParameter("idorigen", idtrazabilidaddocumento).getSingleResult();
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Trazabilidadapoyo> buscarDocumentos(Integer idDocumento){
		String sql = "SELECT t FROM Trazabilidadapoyo t WHERE t.documento = :idDocumento";
		return (List<Trazabilidadapoyo>)em.createQuery(sql).setParameter("idDocumento", idDocumento).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Trazabilidadapoyo> buscarPorOrigen(Integer idTrazabilidad){
		String sql = "SELECT t FROM Trazabilidadapoyo t WHERE t.trazabilidad.idtrazabilidaddocumento = :idorigen";
		return (List<Trazabilidadapoyo>)em.createQuery(sql).setParameter("idorigen", idTrazabilidad).getResultList();
	}

	@Override
	public Long numeroApoyosEstado(Integer idtrazabilidaddocumento, String codEstado) {
		String sql = "SELECT COUNT(t.idtrazabilidadapoyo) FROM Trazabilidadapoyo t WHERE t.trazabilidad.idtrazabilidaddocumento = :idorigen AND t.estado.codigo = :estado";
		return (Long)em.createQuery(sql)
					   .setParameter("idorigen", idtrazabilidaddocumento)
					   .setParameter("estado", codEstado)
					   .getSingleResult();
	}

	public EntityManager getEm() {
		return em;
	}

	@PersistenceContext(unitName="sigedPU")
	public void setEm(EntityManager em) {
		this.em = em;
	}

	public static Logger getLog() {
		return log;
	}

	@Override
	public Trazabilidadapoyo findTrabilidadbyId(Integer idtrazabilidadApoyo) {
        log.debug("-> [DAO] TrazabilidadapoyoDAO - findTrabilidadbyId():Trazabilidadapoyo ");

		return (Trazabilidadapoyo)em.createNamedQuery("Trazabilidadapoyo.findByIdtrazabilidadapoyo").setParameter("idtrazabilidadapoyo", idtrazabilidadApoyo).getSingleResult() ;
	}

}
