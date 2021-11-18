package org.ositran.services;

import java.util.List;

import org.apache.log4j.Logger;
import org.ositran.daos.TiporesultadoDAO;

import com.btg.ositran.siged.domain.Tiporesultado;

public class TiporesultadoServiceImpl implements TiporesultadoService {

   private static Logger log = Logger.getLogger(TiporesultadoServiceImpl.class);
   private TiporesultadoDAO dao;

   //////////////////////////////////
   // Constructors                 //
   //////////////////////////////////
   public TiporesultadoServiceImpl(TiporesultadoDAO dao) {
      this.dao = dao;
   }

   //////////////////////////////////
   // Methods                      //
   //////////////////////////////////
   public Tiporesultado findByIdTiporesultado(Integer idTiporesultado){
      return dao.findByIdTiporesultado(idTiporesultado);
   }

   public List<Tiporesultado> findAll(){
	   return dao.findAll();
   }
   //////////////////////////////////
   // Getters and Setters          //
   //////////////////////////////////
   public TiporesultadoDAO getDao() {
      return dao;
   }

   public void setDao(TiporesultadoDAO dao) {
      this.dao = dao;
   }
}
