/*LICENCIA DE USO DEL SGD .TXT*/package org.ositran.services;

import java.util.List;

import org.apache.log4j.Logger;
import org.ositran.daos.DocumentoxclienteDAO;
import org.springframework.transaction.annotation.Transactional;

import com.btg.ositran.siged.domain.Documentoxcliente;

public class DocumentoxclienteServiceImpl implements DocumentoxclienteService {

   private static Logger log = Logger.getLogger(DocumentoxclienteServiceImpl.class);
   private DocumentoxclienteDAO dao;

   //////////////////////////////////
   // Constructors                 //
   //////////////////////////////////
   public DocumentoxclienteServiceImpl(DocumentoxclienteDAO dao) {
      this.dao = dao;
   }

   //////////////////////////////////
   // Methods                      //
   //////////////////////////////////
   @Transactional
   public Documentoxcliente guardarObj(Documentoxcliente objDocXCli) {
      return dao.guardarObj(objDocXCli);
   }
   
   @Override
   public List<Documentoxcliente> findClientesbyIdDocumento(Integer idDocumento) {
   	  return dao.findClientesbyIdDocumento(idDocumento);
   }
   //////////////////////////////////
   // Getters and Setters          //
   //////////////////////////////////
   public DocumentoxclienteDAO getDao() {
      return dao;
   }

   public void setDao(DocumentoxclienteDAO dao) {
      this.dao = dao;
   }


}
