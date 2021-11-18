package org.ositran.services;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.NoResultException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ositran.daos.AccionDAO;
import org.ositran.daos.AuditoriaDAO;
import org.ositran.daos.TrazabilidadexpedienteDAO;
import org.ositran.utils.Constantes;
import org.springframework.transaction.annotation.Transactional;

import com.btg.ositran.siged.domain.Accion;
import com.btg.ositran.siged.domain.Expediente;
import com.btg.ositran.siged.domain.Trazabilidadexpediente;
import com.btg.ositran.siged.domain.Usuario;

public class TrazabilidadexpedienteServiceImpl implements TrazabilidadexpedienteService{

	private static Log log=LogFactory.getLog(TrazabilidadexpedienteServiceImpl.class);
	
	private AccionDAO accionDao;
	private AuditoriaDAO daoauditoria;
	private TrazabilidadexpedienteDAO dao;
	

   //////////////////////////////////
   // Constructors                 //
   //////////////////////////////////
   public TrazabilidadexpedienteServiceImpl(TrazabilidadexpedienteDAO dao) {
      setDao(dao);
   }

   //////////////////////////////////
   // Methods                      //
   //////////////////////////////////
   public Trazabilidadexpediente findByMaxNroRegistro(Integer iIdExpediente, String sAccion) throws NoResultException {
      try {
         return getDao().findByMaxNroRegistro(iIdExpediente);
      } catch (NoResultException nre) {
         String sMensaje = "No se encontro ninguna trazabilidad del expediente con ID [" + iIdExpediente + "] accion [" + sAccion + "]";

         if (sAccion.equals(Constantes.ACCION_REGISTRAR)) {
            log.warn(sMensaje);

            return null;
         } else {
            log.error(nre.getMessage(), nre);
            throw new RuntimeException(sMensaje);
         }
      }
   }

	@Transactional
	public Trazabilidadexpediente saveTrazabilidadExpediente(Expediente objExpediente,Usuario objRemitente,Date datFechaRecibido){
		Accion objAccion=accionDao.findByNombre(Constantes.ACCION_REGISTRAR);
		Trazabilidadexpediente objMaxTraza=this.findByMaxNroRegistro(objExpediente.getIdexpediente(),Constantes.ACCION_REGISTRAR);
		Trazabilidadexpediente objTraza=new Trazabilidadexpediente();

		objTraza.setRemitente(objRemitente);
		objTraza.setDestinatario(objExpediente.getIdpropietario());
		objTraza.setExpediente(objExpediente);
		objTraza.setAccion(objAccion);
		objTraza.setNroregistro((objMaxTraza == null) ? 1 : objMaxTraza.getNroregistro() + 1);
		objTraza.setObservacion(objExpediente.getObservacion());
		objTraza.setFechacreacion(new Date());

      if (datFechaRecibido != null) {
         objTraza.setFecharecibido(datFechaRecibido);
      }

      return this.save(objTraza);
   }
	
	@Transactional
	public Trazabilidadexpediente save(Trazabilidadexpediente trazabilidadexpediente){
		return getDao().save(trazabilidadexpediente);
	}

	@Transactional
	public Trazabilidadexpediente saveTrazabilidadExpediente(Expediente expediente,Usuario remitente,Usuario destinatario,Accion accion,String observacion){
		Trazabilidadexpediente objMaxTraza=this.findByMaxNroRegistro(expediente.getIdexpediente(),Constantes.ACCION_REGISTRAR);
		Trazabilidadexpediente trazabilidadexpediente=new Trazabilidadexpediente();

		trazabilidadexpediente.setExpediente(expediente);
		trazabilidadexpediente.setRemitente(remitente);
		trazabilidadexpediente.setDestinatario(destinatario);
		trazabilidadexpediente.setAccion(accion);
		trazabilidadexpediente.setNroregistro((objMaxTraza == null) ? 1 : objMaxTraza.getNroregistro() + 1);
		log.debug("Id Expediente " + expediente.getIdexpediente());
		log.debug("Etapa expediente stor " + expediente.getExpedientestor());
		log.debug("Etapa expediente stor " + expediente.getExpedientestor().getEtapa());
		trazabilidadexpediente.setEtapa(expediente.getExpedientestor().getEtapa());
		//trazabilidadexpediente.setIdproceso(expediente.getProceso());
		trazabilidadexpediente.setObservacion(observacion);
		trazabilidadexpediente.setFechacreacion(new Date());

		return getDao().saveTrazabilidadExpediente(trazabilidadexpediente);
	}

	public List<Trazabilidadexpediente> findByIdExpediente(int idExpediente){
		return getDao().findByIdexpediente(idExpediente);
	}

	public Trazabilidadexpediente findUltimoRegistroByExpediente(Integer idExpediente){
		log.debug("OBTENIENDO ULTIMA TRAZABILIDAD DEL EXPEDIENTE " + idExpediente);
		return getDao().findByMaxNroRegistro(idExpediente);
	}

	public Trazabilidadexpediente findByNroRegistroByExpediente(Integer idExpediente,Integer nroRegistro){
		log.debug("OBTENIENDO LA TRAZABILIDAD CON REGISTRO NRO: " + nroRegistro + " DEL EXPEDIENTE " + idExpediente);
		return getDao().findByNroRegistroByExpediente(idExpediente,nroRegistro);
	}

	public Trazabilidadexpediente findByIdTrazabilidadExpediente(Integer idTrazabilidadExpediente){
		return getDao().findByIdtrazabilidadexpediente(idTrazabilidadExpediente);
	}

	// ////////////////////////////////
	// Getters and Setters //
	// ////////////////////////////////
	public TrazabilidadexpedienteDAO getDao(){
		return dao;
	}

	public void setDao(TrazabilidadexpedienteDAO dao){
		this.dao=dao;
	}

	public void setDaoauditoria(AuditoriaDAO daoauditoria){
		this.daoauditoria=daoauditoria;
	}

	public AuditoriaDAO getDaoauditoria(){
		return daoauditoria;
	}

	@Override
	public Trazabilidadexpediente guardarTrazabilidad(Expediente expediente,Usuario remitente,Usuario destinatario,String accion,String observacion){
		Accion laAccion=accionDao.findByNombre(accion);
		return guardarTrazabilidad(expediente,remitente,destinatario,laAccion,observacion);
	}

	public void setAccionDao(AccionDAO accionDao){
		this.accionDao=accionDao;
	}

	@Override
	public Trazabilidadexpediente guardarTrazabilidad(Expediente expediente,Usuario remitente,Usuario destinatario,Accion accion,String observacion){
		Trazabilidadexpediente ultima=dao.findByMaxNroRegistro(expediente.getIdexpediente());
		Trazabilidadexpediente trazabilidad=new Trazabilidadexpediente();
		trazabilidad.setExpediente(expediente);
		trazabilidad.setRemitente(remitente);
		trazabilidad.setDestinatario(destinatario);
		trazabilidad.setAccion(accion);
		trazabilidad.setFechacreacion(Calendar.getInstance().getTime());
		trazabilidad.setObservacion(observacion);
		Calendar fechaLimite=Calendar.getInstance();
                trazabilidad.setFechalimiteatencion(fechaLimite.getTime());
		trazabilidad.setNroregistro(ultima == null ? 1 : ultima.getNroregistro() + 1);
		return null;
	}

}
