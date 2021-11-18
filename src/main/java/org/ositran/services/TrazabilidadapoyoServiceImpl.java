package org.ositran.services;

import java.util.List;

import javax.persistence.NoResultException;

import org.apache.log4j.Logger;
import org.ositran.daos.TrazabilidadapoyoDAO;
import org.ositran.utils.Constantes;
import com.btg.ositran.siged.domain.Documento;
import com.btg.ositran.siged.domain.Usuarioxunidadxfuncion;
import com.btg.ositran.siged.domain.Trazabilidadapoyo;

public class TrazabilidadapoyoServiceImpl implements TrazabilidadapoyoService {

	private static Logger log=Logger.getLogger(TrazabilidadapoyoServiceImpl.class);
	private TrazabilidadapoyoDAO trazabilidadapoyoDAO;

	@Override
	public List<Trazabilidadapoyo> buscarDocumentos(Integer idDocumento) {
		return trazabilidadapoyoDAO.buscarDocumentos(idDocumento);
	}

	@Override
	public Trazabilidadapoyo guardar(Trazabilidadapoyo trazabilidadapoyo) {
		return trazabilidadapoyoDAO.guardar(trazabilidadapoyo);
	}
        
        public Trazabilidadapoyo buscarUltimaDelegacionPendiente(Trazabilidadapoyo trazabilidadapoyo){
               return trazabilidadapoyoDAO.buscarUltimaDelegacionPendiente(trazabilidadapoyo);
        }

	@Override
	public void eliminar(Integer idTrazabilidadapoyo) {
		trazabilidadapoyoDAO.eliminar(idTrazabilidadapoyo);
	}
        
        public Trazabilidadapoyo buscarUltimaDelegacionUsuario(Usuarioxunidadxfuncion usuarioxUnidadxFuncion, Integer idDocumento){
            return trazabilidadapoyoDAO.buscarUltimaDelegacionUsuario(usuarioxUnidadxFuncion, idDocumento);
        }
        
        public Trazabilidadapoyo buscarUltimaDelegacionUsuarioAR(Usuarioxunidadxfuncion usuarioxUnidadxFuncion, Integer idDocumento){
            return trazabilidadapoyoDAO.buscarUltimaDelegacionUsuarioAR(usuarioxUnidadxFuncion, idDocumento);
        }

	/*@Override
	public Trazabilidadapoyo buscarUltimaDelegacionUsuario(Integer idDestinatario, Integer idDocumento) {
		return trazabilidadapoyoDAO.buscarUltimaDelegacionUsuario(idDestinatario, idDocumento);
	}*/
        
        @Override
	public Trazabilidadapoyo buscarUltimaDelegacionUsuario(Documento d) {
		return trazabilidadapoyoDAO.buscarUltimaDelegacionUsuario(d);
	}

	@Override
	public Long numeroApoyos(Integer idtrazabilidaddocumento){
		return trazabilidadapoyoDAO.numeroApoyos(idtrazabilidaddocumento);
	}

	@Override
	public List<Trazabilidadapoyo> buscarPorOrigen(Integer idTrazabilidad){
		try{
			return trazabilidadapoyoDAO.buscarPorOrigen(idTrazabilidad);
		}catch(NoResultException e){
			return null;
		}
	}

	@Override
	public Long numeroApoyosPendientes(Integer idtrazabilidaddocumento){
		return trazabilidadapoyoDAO.numeroApoyosEstado(idtrazabilidaddocumento, Constantes.ESTADO_CODIGO_PENDIENTE);
	}

	public TrazabilidadapoyoDAO getTrazabilidadapoyoDAO() {
		return trazabilidadapoyoDAO;
	}

	public void setTrazabilidadapoyoDAO(TrazabilidadapoyoDAO trazabilidadapoyoDAO) {
		this.trazabilidadapoyoDAO = trazabilidadapoyoDAO;
	}

	@Override
	public Trazabilidadapoyo findByIdTrazabilidadApoyo(
			Integer iIdTrazabilidadApoyo) {
		log.debug("-> [Service] TrazabilidadapoyoService - findByIdTrazabilidadapoyo():Trazabilidadapoyo ");
		return trazabilidadapoyoDAO.findTrabilidadbyId(iIdTrazabilidadApoyo);
	}

	public static Logger getLog() {
		return log;
	}


}
