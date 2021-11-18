/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.services;

import org.apache.log4j.Logger;
import org.ositran.daos.AccionDAO;

import com.btg.ositran.siged.domain.Accion;

public class AccionServiceImpl implements AccionService {

   private static Logger log = Logger.getLogger(AccionServiceImpl.class);
   private AccionDAO dao;

   //////////////////////////////////
   // Constructors                 //
   //////////////////////////////////
   public AccionServiceImpl(AccionDAO dao) {
      this.dao = dao;
   }

   //////////////////////////////////
   // Methods                      //
   //////////////////////////////////
   public Accion findByNombre(String strA) {
      return dao.buscarObjPor(strA);
   }

   //////////////////////////////////
   // Getters and Setters          //
   //////////////////////////////////
   public AccionDAO getDao() {
      return dao;
   }

   public void setDao(AccionDAO dao) {
      this.dao = dao;
   }
}
