package org.ositran.services;

import java.util.List;

import com.btg.ositran.siged.domain.Supervisor;

public interface SupervisorService {

	public Supervisor saveSupervisor(Supervisor objA) throws RuntimeException;

	public Supervisor findByCriteria(Integer iIdDoc, String strNombre)
			throws RuntimeException;

	public Supervisor findById(Integer idArchivo) throws RuntimeException;

	public List<Supervisor> findAll() throws RuntimeException;
}
