package org.ositran.services;

import org.ositran.daos.ResueltoxdocumentoDAO;
import org.springframework.transaction.annotation.Transactional;

import com.btg.ositran.siged.domain.Resueltoxdocumento;

public class ResueltoxdocumentoServiceImpl implements ResueltoxdocumentoService{
	
	private ResueltoxdocumentoDAO resueltoxdocumentoDAO;

	public ResueltoxdocumentoDAO getResueltoxdocumentoDAO() {
		return resueltoxdocumentoDAO;
	}

	public void setResueltoxdocumentoDAO(ResueltoxdocumentoDAO resueltoxdocumentoDAO) {
		this.resueltoxdocumentoDAO = resueltoxdocumentoDAO;
	}

	@Transactional
	public void saveObject(Resueltoxdocumento resueltoxdocumento) {
		resueltoxdocumentoDAO.saveObject(resueltoxdocumento);
	}
}