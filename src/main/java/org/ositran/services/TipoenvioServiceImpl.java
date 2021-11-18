package org.ositran.services;

import java.util.List;
import org.apache.log4j.Logger;
import org.ositran.daos.TipoenvioDAO;

import com.btg.ositran.siged.domain.Tipoenvio;

public class TipoenvioServiceImpl implements TipoenvioService {

   private static Logger log = Logger.getLogger(TipoenvioServiceImpl.class);
   private TipoenvioDAO dao;

   public Tipoenvio findbyNombre(String nombre){
	   return getDao().findbyNombre(nombre);	   
   }
   
   public Tipoenvio findbyId(int Id){
	   return getDao().findbyId(Id);
   }

   public List<Tipoenvio> findAll(){
	   return getDao().findAll();
   }
   
   //////////////////////
   //getter and setters//
   /////////////////////
   
   public void setDao(TipoenvioDAO dao) {
	   this.dao = dao;
   }

   public TipoenvioDAO getDao() {
	   return dao;
   }
   
}
