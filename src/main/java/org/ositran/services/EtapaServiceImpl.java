package org.ositran.services;

import java.util.List;

import org.ositran.daos.EtapaDAO;
import org.springframework.transaction.annotation.Transactional;

import com.btg.ositran.siged.domain.Etapa;

public class EtapaServiceImpl implements EtapaService{
	
	private EtapaDAO etapaDAO;

	public EtapaDAO getEtapaDAO() {
		return etapaDAO;
	}

	public void setEtapaDAO(EtapaDAO etapaDAO) {
		this.etapaDAO = etapaDAO;
	}
	
	public List<Etapa> findAll() {
		return etapaDAO.findAll();
	}

	public Etapa findByIdetapa(int Idetapa) {
		return etapaDAO.findByIdetapa(Idetapa);
	}

	public List<Etapa> getEtapaList(String descripcion, Integer duracion,String estado, String codigo) {
		return etapaDAO.getEtapaList(descripcion,duracion,estado,codigo);
	}

	@Transactional
	public void saveObject(Etapa etapa) {
		etapaDAO.saveObject(etapa);
	}

	public List<Etapa> findEtapabyProceso(Integer idProceso) {
		return etapaDAO.findEtapabyProceso(idProceso);
	}
}