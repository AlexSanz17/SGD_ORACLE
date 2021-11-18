package org.ositran.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;

import org.apache.log4j.Logger;
import org.ositran.daos.TrazabilidadcopiaDAO;
import org.ositran.utils.FechaLimite;
import org.springframework.transaction.annotation.Transactional;

import com.btg.ositran.siged.domain.Accion;
import com.btg.ositran.siged.domain.Documento;
import com.btg.ositran.siged.domain.Etapa;
import com.btg.ositran.siged.domain.Notificacion;
import com.btg.ositran.siged.domain.Trazabilidadcopia;
import com.btg.ositran.siged.domain.Trazabilidaddocumento;
import com.btg.ositran.siged.domain.Usuario;

public class TrazabilidadcopiaServiceImpl implements TrazabilidadcopiaService {
	private static Logger log = Logger.getLogger(TrazabilidadcopiaServiceImpl.class);
	TrazabilidadcopiaDAO trazabilidadcopiaDAO;
	private FechaLimite fechaLimite;
	@Override
	@Transactional
	public void guardarTrazabilidadcopia(Trazabilidaddocumento idorigen, Usuario remitente,
			Usuario destinatario, Documento documento, Accion accion,
			Etapa idetapa, Character tipo, Notificacion notificacion, String nombrePC, Boolean horarioPermitido) {
		
                Usuario obj = new Usuario();
                obj.setIdusuario(remitente.getIdUsuarioPerfil());
                Trazabilidadcopia trazabilidadcopia = new Trazabilidadcopia();
  	  	trazabilidadcopia.setIdorigen(idorigen);
  	  	trazabilidadcopia.setDocumento(documento);
  	  	trazabilidadcopia.setRemitente(obj);
                trazabilidadcopia.setUnidadremitente(remitente.getIdUnidadPerfil());
                trazabilidadcopia.setCargoremitente(remitente.getIdFuncionPerfil());
                trazabilidadcopia.setDestinatario(destinatario);
                trazabilidadcopia.setUnidaddestinatario(destinatario.getIdUnidadPerfil());
                trazabilidadcopia.setCargodestinatario(destinatario.getIdFuncionPerfil());
  	  	trazabilidadcopia.setAccion(accion);
  	  	trazabilidadcopia.setEtapa(idetapa);
  	  	trazabilidadcopia.setTipo(tipo);
                trazabilidadcopia.setUsuariocreacion(remitente.getIdusuario());
  	  	trazabilidadcopia.setFechacreacion(new Date(System.currentTimeMillis()));
  	        Date fechaFueraHorario = new Date();
                
                if(horarioPermitido == false){ //JC-FECHA
                    /*fechaFueraHorario = fechaLimite.fechaFueraHorarioHabil(fechaFueraHorario,destinatario.getIdusuario().intValue());
		    long n = fechaFueraHorario.getTime()-(new Date()).getTime();   
                    long valor = n/60000;
                    fechaFueraHorario = new Date(fechaFueraHorario.getTime() + (8010-valor)); */
                    trazabilidadcopia.setFechacreacion(fechaFueraHorario);
	        }else{
	    	    trazabilidadcopia.setFechacreacion(new Date(System.currentTimeMillis() + 2900));
	        }
  	  	trazabilidadcopia.setNotificacion(notificacion);
  	  	trazabilidadcopia.setNombrePC(nombrePC);
                trazabilidadcopiaDAO.saveObject(trazabilidadcopia);
	}

	@Override
	public List<Trazabilidadcopia> buscarPorOrigen(Integer idOrigen,Character tipoOrigen) {
		log.debug("-> [Service] TrazabilidadcopiaService - buscarPorOrigen():List<Trazabilidadcopia> ");

		try{
			return trazabilidadcopiaDAO.buscarPorOrigen(idOrigen, tipoOrigen);
		}catch(NoResultException e){
			return null;
		}
	}

	@Override
	public Trazabilidadcopia buscarPorNotificacion(Integer idNotificacion) {
		log.debug("-> [Service] TrazabilidadcopiaService - buscarPorNotificacion():Trazabilidadcopia> ");

		return trazabilidadcopiaDAO.buscarPorNotificacion(idNotificacion);
	}

	@Override
	public Long numeroCopias(Integer idOrigen,Character tipoOrigen){
		log.debug("-> [Service] TrazabilidadcopiaService - numeroCopias():Long ");

		return trazabilidadcopiaDAO.numeroCopias(idOrigen, tipoOrigen);
	}

	@Override
	public Trazabilidadcopia buscarPorId(Integer idTrazabilidad) {
		log.debug("-> [Service] TrazabilidadcopiaService - buscarPorId():Trazabilidadcopia ");

		try{
			return trazabilidadcopiaDAO.buscarPorId(idTrazabilidad);
		}catch(NoResultException e){
			return new Trazabilidadcopia();
		}
	}

	public TrazabilidadcopiaDAO getTrazabilidadcopiaDAO() {
		return trazabilidadcopiaDAO;
	}

	public void setTrazabilidadcopiaDAO(TrazabilidadcopiaDAO trazabilidadcopiaDAO) {
		this.trazabilidadcopiaDAO = trazabilidadcopiaDAO;
	}

	public List<Trazabilidadcopia> buscarUsuarioCopia(Integer idDocumento,Integer traza){
		log.debug("-> [Service] TrazabilidadcopiaService - buscarUsuarioCopia():List<Trazabilidadcopia> ");

		try{
			return trazabilidadcopiaDAO.buscarUsuarioCopia(idDocumento,traza);
		}catch(NoResultException e){
			return new ArrayList<Trazabilidadcopia>();
		}
	}

   @Override
   public Trazabilidadcopia saveObject(Trazabilidadcopia trazabilidadCopia) {
	   log.debug("-> [Service] TrazabilidadcopiaService - saveObject():Trazabilidadcopia ");

	   return trazabilidadcopiaDAO.saveObject(trazabilidadCopia);
   }

   @Override
   public List<Trazabilidadcopia> findLstByIdDocumento(Integer idDocumento) {
	   log.debug("-> [Service] TrazabilidadcopiaService - findLstByIdDocumento():List<Trazabilidadcopia> ");

	   return trazabilidadcopiaDAO.findLstByIdDocumento(idDocumento);
   }

public FechaLimite getFechaLimite() {
	return fechaLimite;
}

public void setFechaLimite(FechaLimite fechaLimite) {
	this.fechaLimite = fechaLimite;
}

}
