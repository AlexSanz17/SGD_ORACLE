package org.ositran.services;

import org.apache.log4j.Logger;
import org.ositran.daos.DocumentoxexpedienteDAO;
import org.springframework.transaction.annotation.Transactional;

import com.btg.ositran.siged.domain.Documentoxexpediente;

public class DocumentoxexpedienteServiceImpl implements DocumentoxexpedienteService {

   private static Logger log = Logger.getLogger(DocumentoxexpedienteServiceImpl.class);
   private DocumentoxexpedienteDAO dao;

   //////////////////////////////////
   // Constructors                 //
   //////////////////////////////////
   public DocumentoxexpedienteServiceImpl(DocumentoxexpedienteDAO dao) {
      this.dao = dao;
   }

   //////////////////////////////////
   // Methods                      //
   //////////////////////////////////
   @Transactional
   public Documentoxexpediente guardarObj(Documentoxexpediente objDocXExp) {
      return dao.guardarObj(objDocXExp);
   }

   //////////////////////////////////
   // Getters and Setters          //
   //////////////////////////////////
   public DocumentoxexpedienteDAO getDao() {
      return dao;
   }

   public void setDao(DocumentoxexpedienteDAO dao) {
      this.dao = dao;
   }
}
