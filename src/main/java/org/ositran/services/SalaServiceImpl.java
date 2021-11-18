package org.ositran.services;

import java.util.List;

import org.apache.log4j.Logger;
import org.ositran.daos.SalaDAO;

import com.btg.ositran.siged.domain.Sala;

public class SalaServiceImpl implements SalaService {

   private static Logger log = Logger.getLogger(SalaServiceImpl.class);
   private SalaDAO dao;

   //////////////////////////////////
   // Constructors                 //
   //////////////////////////////////
   public SalaServiceImpl(SalaDAO dao) {
      this.dao = dao;
   }

   //////////////////////////////////
   // Methods                      //
   //////////////////////////////////
	public List<Sala> findAll() {
		return getDao().findAll();
	}

	public Sala findByIdSala(Integer iIdSala) {
		return getDao().findByIdSala(iIdSala);
	}
   //////////////////////////////////
   // Getters and Setters          //
   //////////////////////////////////

	public SalaDAO getDao() {
		return dao;
	}

	public void setDao(SalaDAO dao) {
		this.dao = dao;
	}

}
