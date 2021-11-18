package org.ositran.services;

import java.util.List;

import org.ositran.daos.SubmotivoDAO;

import com.btg.ositran.siged.domain.Submotivo;

public class SubMotivoServiceImpl implements SubMotivoService{

	private SubmotivoDAO submotivoDAO;
		
	public Object[] findAllAjax(String idMotivo)
	{
		List<Submotivo> listaSubMotivos = submotivoDAO.findByMotivo(Integer.parseInt(idMotivo));
		return listaSubMotivos.toArray();
	}

	public SubmotivoDAO getSubmotivoDAO() {
		return submotivoDAO;
	}

	public void setSubmotivoDAO(SubmotivoDAO submotivoDAO) {
		this.submotivoDAO = submotivoDAO;
	}
	
	
}
