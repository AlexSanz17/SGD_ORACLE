package org.ositran.services;

import javax.persistence.NoResultException;

import org.apache.log4j.Logger;
import org.ositran.daos.ResolucionjaruDAO;
import org.springframework.transaction.annotation.Transactional;

import com.btg.ositran.siged.domain.Resolucionjaru;

public class ResolucionjaruServiceImpl implements ResolucionjaruService {

   private static Logger log = Logger.getLogger(ResolucionjaruServiceImpl.class);
   private ResolucionjaruDAO dao;

   //////////////////////////////////
   // Constructors                 //
   //////////////////////////////////
   public ResolucionjaruServiceImpl(ResolucionjaruDAO dao) {
      this.dao = dao;
   }

   //////////////////////////////////
   // Methods                      //
   //////////////////////////////////
   
   @Transactional
   public Resolucionjaru saveOrUpdateResolucionJaru(Resolucionjaru resolucionjaru){
	   return dao.saveOrUpdateResolucionJaru(resolucionjaru);
   }

   public Resolucionjaru findByIdExpedienteStor(Integer idExpediente){
	   try{
		   return dao.findByIdExpedienteStor(idExpediente);
	   }catch(Exception e){
		   return null;
	   }
   }
   //////////////////////////////////
   // Getters and Setters          //
   //////////////////////////////////
   public ResolucionjaruDAO getDao() {
      return dao;
   }

   public void setDao(ResolucionjaruDAO dao) {
      this.dao = dao;
   }
}
