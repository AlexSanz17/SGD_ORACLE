package org.ositran.services;

import java.util.List;

import org.apache.log4j.Logger;
import org.ositran.daos.VocalDAO;

import com.btg.ositran.siged.domain.Vocal;

public class VocalServiceImpl implements VocalService {

   private static Logger log = Logger.getLogger(VocalServiceImpl.class);
   private VocalDAO dao;

   //////////////////////////////////
   // Constructors                 //
   //////////////////////////////////
   public VocalServiceImpl(VocalDAO dao) {
      this.dao = dao;
   }

   //////////////////////////////////
   // Methods                      //
   //////////////////////////////////
   public Vocal findById(Integer idVocal){
	   return dao.findById(idVocal);
   }
   
   public List<Vocal> findAll(){
	   return dao.findAll();
   }

   //////////////////////////////////
   // Getters and Setters          //
   //////////////////////////////////
   public VocalDAO getDao() {
      return dao;
   }

   public void setDao(VocalDAO dao) {
      this.dao = dao;
   }
}
