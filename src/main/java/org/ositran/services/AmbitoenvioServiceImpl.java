/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.services;

import java.util.List;
import org.apache.log4j.Logger;
import org.ositran.daos.AmbitoenvioDAO;

import com.btg.ositran.siged.domain.AmbitoEnvio;

public class AmbitoenvioServiceImpl implements AmbitoenvioService {

   private static Logger log = Logger.getLogger(AmbitoenvioServiceImpl.class);
   private AmbitoenvioDAO dao;
   
   public AmbitoEnvio findId(int Id){
	   return getDao().findId(Id);
   }

   public List<AmbitoEnvio> findAll(){
	   return getDao().findAll();
   }
   
   //////////////////////
   //getter and setters//
   /////////////////////
   
   public void setDao(AmbitoenvioDAO dao) {
	   this.dao = dao;
   }

   public AmbitoenvioDAO getDao() {
	   return dao;
   }
   
}
