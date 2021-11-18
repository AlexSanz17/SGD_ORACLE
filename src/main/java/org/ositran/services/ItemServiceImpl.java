package org.ositran.services;

import java.util.List;
import javax.persistence.NoResultException;
import org.apache.log4j.Logger;
import org.ositran.daos.ItemDAO;
import org.ositran.dojo.BusquedaAvanzada;
import org.ositran.dojo.grid.Item;
import com.btg.ositran.siged.domain.Usuario;


public class ItemServiceImpl implements ItemService {

	private static Logger _log = Logger.getLogger(ItemServiceImpl.class);
	private ItemDAO dao;

	/*
	 * Constructors
	 */
	public ItemServiceImpl(ItemDAO dao) {
		this.dao = dao;
	}

	/*
	 * Methods
	 */
	public List<Item> findLstExpedienteBy(Integer iIdUsuario) {
		_log.debug("-> [Service] ItemServiceImpl - findLstExpedienteBy():List<Item> ");

		if (_log.isDebugEnabled()) {
			_log.debug("iIdUsuario [" + iIdUsuario + "]");
		}

		try {
			return dao.findLstExpedienteBy(iIdUsuario);
		} catch (NoResultException nre) {
			_log.info("No se encontro ningun registro");

			return null;
		}
	}

	public List<Item> findLstInformativoBy(Usuario objUsuario, Character cEstado) {
		_log.debug("-> [Service] ItemServiceImpl - findLstInformativoBy():List<Item> ");
		
		if (_log.isDebugEnabled()) {
			_log.debug("iIdPropietario [" + objUsuario.getIdusuario() + "] cEstado [" + cEstado + "]");
		}

		try {
			return dao.findLstInformativoBy(objUsuario, cEstado);
		} catch (NoResultException nre) {
			_log.info("No se encontro ningun registro");

			return null;
		}
	}

	public List<Item> findLstInformativoBy(Usuario objUsuario, BusquedaAvanzada objFiltro) {
		_log.debug("-> [Service] ItemServiceImpl - findLstInformativoBy():List<Item> ");
		
		if (_log.isDebugEnabled()) {
			_log.debug("iIdPropietario [" + objUsuario.getIdUsuarioPerfil() + "]");
		}

		try {
			return dao.findLstInformativoBy(objUsuario, objFiltro);
		} catch (NoResultException nre) {
			_log.info("No se encontro ningun registro");

			return null;
		}
	}
	
	public List<Item> findLstNotificacionBy(Integer iIdPropietario, Character cEstado) {
		_log.debug("-> [Service] ItemServiceImpl - findLstNotificacionBy():List<Item> ");
		
		if (_log.isDebugEnabled()) {
			_log.debug("iIdPropietario [" + iIdPropietario + "] cEstado [" + cEstado + "]");
		}

		try {
			return dao.findLstNotificacionBy(iIdPropietario, cEstado);
		} catch (NoResultException nre) {
			_log.info("No se encontro ningun registro");

			return null;
		}
	}

	public List<Item> findLstDocumentoBy(Integer iIdExpediente) {
		_log.debug("-> [Service] ItemServiceImpl - findLstDocumentoBy():List<Item> ");
		
		if (_log.isDebugEnabled()) {
			_log.debug("iIdExpediente [" + iIdExpediente + "]");
		}

		try {
			return dao.findLstDocumentoBy(iIdExpediente);
		} catch (NoResultException nre) {
			_log.info("No se encontro ningun registro");

			return null;
		}
	}

	/*
	 * Getters & Setters
	 */
	public ItemDAO getDao() {
		return dao;
	}

	public void setDao(ItemDAO dao) {
		this.dao = dao;
	}
}
